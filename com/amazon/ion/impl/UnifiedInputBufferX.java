/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazon.ion.impl;

import com.amazon.ion.impl.UnifiedDataPageX;

abstract class UnifiedInputBufferX {
    protected int _page_size;
    protected UnifiedDataPageX[] _buffers;
    protected int _buffer_current;
    protected int _buffer_count;
    protected int _locks;

    public static UnifiedInputBufferX makePageBuffer(byte[] bytes, int offset, int length) {
        Bytes buf = new Bytes(bytes, offset, length);
        return buf;
    }

    public static UnifiedInputBufferX makePageBuffer(char[] chars, int offset, int length) {
        Chars buf = new Chars(chars, offset, length);
        return buf;
    }

    public static UnifiedInputBufferX makePageBuffer(CharSequence chars, int offset, int length) {
        char[] char_array = UnifiedInputBufferX.chars_make_char_array(chars, offset, length);
        UnifiedInputBufferX buf = UnifiedInputBufferX.makePageBuffer(char_array, 0, length);
        return buf;
    }

    public static UnifiedInputBufferX makePageBuffer(BufferType bufferType, int initialPageSize) {
        UnifiedInputBufferX buf;
        switch (bufferType) {
            case CHARS: {
                buf = new Chars(initialPageSize);
                break;
            }
            case BYTES: {
                buf = new Bytes(initialPageSize);
                break;
            }
            default: {
                throw new IllegalArgumentException("invalid buffer type");
            }
        }
        return buf;
    }

    protected static final char[] chars_make_char_array(CharSequence chars, int offset, int length) {
        char[] char_array = new char[length];
        for (int ii = offset; ii < length; ++ii) {
            char_array[ii] = chars.charAt(ii);
        }
        return char_array;
    }

    private UnifiedInputBufferX(int initialPageSize) {
        if (initialPageSize < 0) {
            throw new IllegalArgumentException("page size must be > 0");
        }
        if (initialPageSize > 0x7FFFFFF7) {
            throw new IllegalArgumentException("page size must be < 2147483639");
        }
        this._page_size = initialPageSize;
        this._buffers = new UnifiedDataPageX[10];
    }

    public abstract BufferType getType();

    public abstract int maxValue();

    public final void putCharAt(long fileOffset, int c) {
        if (c < 0 || c > this.maxValue()) {
            throw new IllegalArgumentException("value (" + c + ")is out of range (0 to " + this.maxValue() + ")");
        }
        UnifiedDataPageX page = null;
        for (int ii = this._buffer_current; ii >= 0; --ii) {
            if (!this._buffers[ii].containsOffset(fileOffset)) continue;
            page = this._buffers[ii];
            break;
        }
        if (page == null) {
            throw new IllegalArgumentException();
        }
        int offset = (int)(fileOffset - page.getStartingFileOffset());
        page.putValue(offset, c);
    }

    public final UnifiedDataPageX getCurrentPage() {
        return this._buffers[this._buffer_current];
    }

    public final int getCurrentPageIdx() {
        return this._buffer_current;
    }

    public final int getPageCount() {
        return this._buffer_count;
    }

    public final void incLock() {
        ++this._locks;
    }

    public final boolean decLock() {
        --this._locks;
        return this._locks == 0;
    }

    public final UnifiedDataPageX getPage(int pageIdx) {
        if (pageIdx < 0 || pageIdx >= this._buffer_count) {
            throw new IndexOutOfBoundsException();
        }
        return this._buffers[pageIdx];
    }

    protected final int getNextFilledPageIdx() {
        UnifiedDataPageX p;
        int idx = this._buffer_current + 1;
        if (idx < this._buffer_count && (p = this._buffers[idx]) != null) {
            this._buffer_current = idx;
            return idx;
        }
        return -1;
    }

    protected final UnifiedDataPageX getEmptyPageIdx() {
        UnifiedDataPageX next = null;
        if (this._buffer_count < this._buffers.length) {
            next = this._buffers[this._buffer_count];
        }
        if (next == null) {
            next = this.make_page(this._page_size);
        } else assert (this._buffer_count == this._buffer_current + 1);
        return next;
    }

    protected abstract UnifiedDataPageX make_page(int var1);

    protected final UnifiedDataPageX setCurrentPage(int idx, UnifiedDataPageX curr) {
        this.setPage(idx, curr, true);
        if (idx != this._buffer_current) {
            this._buffer_current = idx;
            if (idx >= this._buffer_count) {
                this._buffer_count = idx + 1;
            }
        }
        UnifiedDataPageX p = this._buffers[idx];
        return p;
    }

    protected final void setPage(int idx, UnifiedDataPageX curr, boolean recycleOldPage) {
        int oldlen = this._buffers.length;
        if (idx >= oldlen) {
            int newlen = oldlen * 2;
            UnifiedDataPageX[] newbuf = new UnifiedDataPageX[newlen];
            System.arraycopy(this._buffers, 0, newbuf, 0, oldlen);
            this._buffers = newbuf;
        }
        UnifiedDataPageX prev = this._buffers[idx];
        this._buffers[idx] = curr;
        if (idx >= this._buffer_count) {
            this._buffer_count = idx + 1;
        }
        if (recycleOldPage && prev != null && prev != curr && idx + 1 < this._buffers.length) {
            this._buffers[idx + 1] = prev;
        }
    }

    protected final void resetToCurrentPage() {
        int p0_idx = this.getCurrentPageIdx();
        if (p0_idx > 0) {
            this.release_pages_to(p0_idx);
        }
    }

    private final void release_pages_to(int p0_idx) {
        assert (p0_idx > 0);
        UnifiedDataPageX empty_page = this._buffers[0];
        int dst = 0;
        int src = p0_idx;
        while (src < this._buffer_count) {
            this._buffers[dst++] = this._buffers[src++];
        }
        int end = this._buffer_count + 1;
        if (end >= this._buffers.length) {
            end = this._buffers.length;
        }
        while (dst < end) {
            this._buffers[dst++] = null;
        }
        this._buffer_current -= p0_idx;
        this._buffer_count -= p0_idx;
        this._buffers[this._buffer_count] = empty_page;
    }

    protected final void clear() {
        UnifiedDataPageX curr = this.getCurrentPage();
        for (int ii = 0; ii < this._buffers.length; ++ii) {
            this._buffers[ii] = null;
        }
        if (curr != null) {
            this._buffers[0] = curr;
            curr.reset(0);
        }
        this._buffer_count = 0;
        this._buffer_current = 0;
    }

    static class Chars
    extends UnifiedInputBufferX {
        protected Chars(int initialPageSize) {
            super(initialPageSize);
        }

        protected Chars(char[] chars, int offset, int length) {
            super(offset + length);
            this._buffers[0] = new UnifiedDataPageX.Chars(chars, offset, length);
            this._buffer_current = 0;
            this._buffer_count = 1;
        }

        protected Chars(CharSequence chars, int offset, int length) {
            this(Chars.chars_make_char_array(chars, offset, length), 0, length);
        }

        @Override
        public final BufferType getType() {
            return BufferType.CHARS;
        }

        @Override
        protected final UnifiedDataPageX make_page(int page_size) {
            UnifiedDataPageX.Chars p = new UnifiedDataPageX.Chars(page_size);
            return p;
        }

        @Override
        public final int maxValue() {
            return 65535;
        }
    }

    static class Bytes
    extends UnifiedInputBufferX {
        protected Bytes(int initialPageSize) {
            super(initialPageSize);
        }

        protected Bytes(byte[] bytes, int offset, int length) {
            super(length);
            this._buffers[0] = new UnifiedDataPageX.Bytes(bytes, offset, length);
            this._buffer_current = 0;
            this._buffer_count = 1;
        }

        @Override
        public final BufferType getType() {
            return BufferType.BYTES;
        }

        @Override
        protected final UnifiedDataPageX make_page(int page_size) {
            UnifiedDataPageX.Bytes p = new UnifiedDataPageX.Bytes(page_size);
            return p;
        }

        @Override
        public final int maxValue() {
            return 255;
        }
    }

    public static enum BufferType {
        BYTES,
        CHARS;

    }
}

