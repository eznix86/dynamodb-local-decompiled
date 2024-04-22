/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazon.ion.impl;

import com.amazon.ion.impl.UnifiedDataPageX;
import com.amazon.ion.impl.UnifiedInputBufferX;
import com.amazon.ion.impl.UnifiedInputStreamX;

final class UnifiedSavePointManagerX {
    private static final int FREE_LIST_LIMIT = 20;
    UnifiedInputStreamX _stream;
    UnifiedInputBufferX _buffer;
    SavePoint _inuse;
    SavePoint _free;
    int _free_count;
    SavePoint _active_stack;
    int _open_save_points;

    public UnifiedSavePointManagerX(UnifiedInputStreamX stream) {
        this._stream = stream;
        this._buffer = stream._buffer;
        this._inuse = null;
        this._free = null;
        this._active_stack = null;
    }

    public final boolean isSavePointOpen() {
        return this._open_save_points > 0;
    }

    public final long lengthOf(SavePoint sp) {
        long len;
        int start_idx = sp.getStartIdx();
        int end_idx = sp.getEndIdx();
        if (start_idx == -1 || end_idx == -1) {
            return 0L;
        }
        if (start_idx == end_idx) {
            int start_pos = sp.getStartPos();
            int end_pos = sp.getEndPos();
            len = end_pos - start_pos;
        } else {
            UnifiedDataPageX start = this._buffer.getPage(start_idx);
            UnifiedDataPageX end = this._buffer.getPage(end_idx);
            long start_pos = start.getFilePosition(sp.getStartPos());
            long end_pos = end.getFilePosition(sp.getEndPos());
            len = end_pos - start_pos;
        }
        return len;
    }

    public final SavePoint savePointAllocate() {
        SavePoint sp;
        if (this._free != null) {
            sp = this._free;
            this._free = sp._next;
            --this._free_count;
            sp.clear();
        } else {
            sp = new SavePoint(this);
        }
        sp._next = this._inuse;
        sp._prev = null;
        if (this._inuse != null) {
            this._inuse._prev = sp;
        } else {
            this._inuse = sp;
        }
        return sp;
    }

    public final void savePointFree(SavePoint sp) {
        assert (sp.isClear());
        if (this._free_count >= 20) {
            return;
        }
        if (sp._prev == null) {
            sp._prev = sp._next;
        } else {
            this._inuse = sp._next;
        }
        if (sp._next != null) {
            sp._next._prev = sp._prev;
        }
        sp._next = this._free;
        this._free = sp;
        ++this._free_count;
    }

    public final SavePoint savePointActiveTop() {
        return this._active_stack;
    }

    public final void savePointPushActive(SavePoint sp, long line_number, long line_start) {
        assert (!sp.isActive());
        int idx = this._buffer.getCurrentPageIdx();
        int pos = this._stream._pos;
        int limit2 = this._stream._limit;
        UnifiedDataPageX curr = this._buffer.getPage(idx);
        sp.set_prev_pos(idx, pos, limit2, line_number, line_start);
        sp._next_active = this._active_stack;
        this._active_stack = sp;
        sp.set_active();
        idx = sp.getStartIdx();
        pos = sp.getStartPos();
        curr = this._buffer.getPage(idx);
        limit2 = sp.getEndIdx() != sp.getStartIdx() ? curr.getBufferLimit() : sp.getEndPos();
        this._stream.make_page_current(curr, idx, pos, limit2);
    }

    public final void savePointPopActive(SavePoint sp) {
        if (sp != this._active_stack) {
            throw new IllegalArgumentException("save point being released isn't currently active");
        }
        this._active_stack = sp._next_active;
        sp._next_active = null;
        sp.set_inactive();
        this._stream.save_point_reset_to_prev(sp);
    }

    private void save_point_clear(SavePoint sp) {
        if (sp.isClear()) {
            return;
        }
        int start_idx = sp.getStartIdx();
        int end_idx = sp.getEndIdx();
        if ((end_idx != -1 || start_idx != -1) && start_idx != -1) {
            --this._open_save_points;
            this.save_point_unpin(sp);
        }
    }

    private final void save_point_unpin(SavePoint sp) {
        if (sp.isActive()) {
            throw new IllegalArgumentException("you can't release an active save point");
        }
        assert (sp.isDefined());
        if (this._buffer.decLock() && this._open_save_points == 0) {
            this._buffer.resetToCurrentPage();
        }
    }

    private final SavePoint save_point_start(SavePoint sp, long line_number, long line_start) {
        if (sp.isDefined()) {
            throw new IllegalArgumentException("you can't start an active save point");
        }
        int new_pinned_idx = this._buffer.getCurrentPageIdx();
        this._buffer.incLock();
        sp.set_start_pos(new_pinned_idx, this._stream._pos, line_number, line_start);
        ++this._open_save_points;
        return sp;
    }

    private final void save_point_mark_end(SavePoint sp, int offset) {
        if (sp.isActive()) {
            throw new IllegalArgumentException("you can't start an active save point");
        }
        UnifiedDataPageX curr = this._buffer.getCurrentPage();
        int curr_idx = this._buffer.getCurrentPageIdx();
        int curr_pos = this._stream._pos + offset;
        if (offset != 0) {
            if (curr_pos >= curr.getBufferLimit()) {
                curr_pos -= curr.getOriginalStartingOffset();
                curr = this._buffer.getPage(++curr_idx);
            } else if (curr_pos < curr.getStartingOffset()) {
                int pos_offset = curr_pos - curr.getOriginalStartingOffset();
                curr = this._buffer.getPage(--curr_idx);
                curr_pos = curr.getBufferLimit() - pos_offset;
            }
            if (curr == null || curr_pos >= curr.getBufferLimit() || curr_pos < curr.getStartingOffset()) {
                this.end_point_too_far(curr_idx);
            }
        }
        sp.set_end_pos(curr_idx, curr_pos);
    }

    private final void end_point_too_far(int curr_idx) {
        String message = "end point [" + curr_idx + "] must be within 1 page of current [" + this._buffer.getCurrentPageIdx() + "]";
        throw new IllegalArgumentException(message);
    }

    public static class SavePoint {
        private UnifiedSavePointManagerX _owner;
        private SavePointState _state;
        private int _start_idx;
        private int _start_pos;
        private long _start_line_count;
        private long _start_line_start;
        private int _end_idx;
        private int _end_pos;
        private int _prev_idx;
        private int _prev_pos;
        private int _prev_limit;
        private long _prev_line_count;
        private long _prev_line_start;
        private SavePoint _next;
        private SavePoint _prev;
        private SavePoint _next_active;

        SavePoint(UnifiedSavePointManagerX owner) {
            this.clear();
            this._owner = owner;
        }

        private final void set_start_pos(int idx, int pos, long line_count, long line_start) {
            assert (this._state == SavePointState.CLEAR);
            this._state = SavePointState.DEFINED;
            this._start_idx = idx;
            this._start_pos = pos;
            this._start_line_count = line_count;
            this._start_line_start = line_start;
        }

        private final void set_end_pos(int idx, int pos) {
            assert (this._state == SavePointState.DEFINED);
            this._end_idx = idx;
            this._end_pos = pos;
        }

        private final void set_prev_pos(int idx, int pos, int limit2, long line_count, long line_start) {
            assert (this._state == SavePointState.DEFINED);
            this._prev_idx = idx;
            this._prev_pos = pos;
            this._prev_limit = limit2;
            this._prev_line_count = line_count;
            this._prev_line_start = line_start;
        }

        public final void clear() {
            assert (this._state != SavePointState.ACTIVE);
            if (this.isDefined()) {
                this._owner.save_point_clear(this);
            }
            this._state = SavePointState.CLEAR;
            this._start_idx = -1;
            this._end_idx = -1;
            this._prev_idx = -1;
        }

        public final void start(long line_number, long line_start) {
            this._owner.save_point_start(this, line_number, line_start);
        }

        public final void markEnd() {
            this._owner.save_point_mark_end(this, 0);
        }

        public final void markEnd(int offset) {
            this._owner.save_point_mark_end(this, offset);
        }

        public final void free() {
            this._owner.savePointFree(this);
        }

        public final boolean isClear() {
            return this._state == SavePointState.CLEAR;
        }

        public final boolean isDefined() {
            return this._state == SavePointState.DEFINED || this._state == SavePointState.ACTIVE;
        }

        public final boolean isActive() {
            return this._state == SavePointState.ACTIVE;
        }

        public final void set_active() {
            assert (this._state == SavePointState.DEFINED);
            this._state = SavePointState.ACTIVE;
        }

        public final void set_inactive() {
            assert (this._state == SavePointState.ACTIVE);
            this._state = SavePointState.DEFINED;
        }

        public final long length() {
            if (this._start_idx == -1 || this._end_idx == -1) {
                return 0L;
            }
            return this._owner.lengthOf(this);
        }

        public final int getStartIdx() {
            return this._start_idx;
        }

        public final int getStartPos() {
            assert (this._state != SavePointState.CLEAR);
            return this._start_pos;
        }

        public final long getStartLineNumber() {
            return this._start_line_count;
        }

        public final long getStartLineStart() {
            return this._start_line_start;
        }

        public final long getStartFilePosition() {
            if (this._start_idx == -1) {
                return -1L;
            }
            UnifiedDataPageX p = this._owner._buffer.getPage(this._start_idx);
            return p.getFilePosition(this._start_pos);
        }

        public final int getEndIdx() {
            return this._end_idx;
        }

        public final int getEndPos() {
            assert (this._state != SavePointState.CLEAR);
            return this._end_pos;
        }

        public final long getEndFilePosition() {
            assert (this._state != SavePointState.CLEAR);
            if (this._end_idx == -1) {
                return -1L;
            }
            UnifiedDataPageX p = this._owner._buffer.getPage(this._end_idx);
            return p.getFilePosition(this._end_pos);
        }

        public final int getPrevIdx() {
            return this._prev_idx;
        }

        public final int getPrevPos() {
            assert (this._state != SavePointState.CLEAR);
            return this._prev_pos;
        }

        public final int getPrevLimit() {
            assert (this._state != SavePointState.CLEAR);
            return this._prev_limit;
        }

        public final long getPrevLineNumber() {
            return this._prev_line_count;
        }

        public final long getPrevLineStart() {
            return this._prev_line_start;
        }

        public static enum SavePointState {
            CLEAR,
            DEFINED,
            ACTIVE;

        }
    }
}

