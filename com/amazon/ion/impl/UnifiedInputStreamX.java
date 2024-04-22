/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazon.ion.impl;

import com.amazon.ion.impl.IonReaderTextRawTokensX;
import com.amazon.ion.impl.IonUTF8;
import com.amazon.ion.impl.UnifiedDataPageX;
import com.amazon.ion.impl.UnifiedInputBufferX;
import com.amazon.ion.impl.UnifiedSavePointManagerX;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

abstract class UnifiedInputStreamX
implements Closeable {
    public static final int EOF = -1;
    private static final boolean _debug = false;
    static final int UNREAD_LIMIT = 10;
    static int DEFAULT_PAGE_SIZE = 32768;
    boolean _eof;
    boolean _is_byte_data;
    boolean _is_stream;
    UnifiedInputBufferX _buffer;
    int _max_char_value;
    int _pos;
    int _limit;
    Reader _reader;
    InputStream _stream;
    byte[] _bytes;
    char[] _chars;
    UnifiedSavePointManagerX _save_points;

    UnifiedInputStreamX() {
    }

    public static UnifiedInputStreamX makeStream(CharSequence chars) {
        return new FromCharArray(chars, 0, chars.length());
    }

    public static UnifiedInputStreamX makeStream(CharSequence chars, int offset, int length) {
        return new FromCharArray(chars, offset, length);
    }

    public static UnifiedInputStreamX makeStream(char[] chars) {
        return new FromCharArray(chars, 0, chars.length);
    }

    public static UnifiedInputStreamX makeStream(char[] chars, int offset, int length) {
        return new FromCharArray(chars, offset, length);
    }

    public static UnifiedInputStreamX makeStream(Reader reader) throws IOException {
        return new FromCharStream(reader);
    }

    public static UnifiedInputStreamX makeStream(byte[] buffer) {
        return new FromByteArray(buffer, 0, buffer.length);
    }

    public static UnifiedInputStreamX makeStream(byte[] buffer, int offset, int length) {
        return new FromByteArray(buffer, offset, length);
    }

    public static UnifiedInputStreamX makeStream(InputStream stream) throws IOException {
        return new FromByteStream(stream);
    }

    public final InputStream getInputStream() {
        return this._stream;
    }

    public final Reader getReader() {
        return this._reader;
    }

    public final byte[] getByteArray() {
        return this._bytes;
    }

    public final char[] getCharArray() {
        return this._chars;
    }

    private final void init() {
        this._eof = false;
        this._max_char_value = this._buffer.maxValue();
        this._save_points = new UnifiedSavePointManagerX(this);
    }

    @Override
    public void close() throws IOException {
        this._eof = true;
        this._buffer.clear();
    }

    public final boolean isEOF() {
        return this._eof;
    }

    public long getPosition() {
        long file_pos = 0L;
        UnifiedDataPageX page = this._buffer.getCurrentPage();
        if (page != null) {
            file_pos = page.getFilePosition(this._pos);
        }
        return file_pos;
    }

    public final UnifiedSavePointManagerX.SavePoint savePointAllocate() {
        UnifiedSavePointManagerX.SavePoint sp = this._save_points.savePointAllocate();
        return sp;
    }

    protected final void save_point_reset_to_prev(UnifiedSavePointManagerX.SavePoint sp) {
        int idx = sp.getPrevIdx();
        UnifiedDataPageX curr = this._buffer.getPage(idx);
        int pos = sp.getPrevPos();
        int limit2 = sp.getPrevLimit();
        this.make_page_current(curr, idx, pos, limit2);
    }

    protected final void make_page_current(UnifiedDataPageX curr, int idx, int pos, int limit2) {
        this._limit = limit2;
        this._pos = pos;
        this._eof = false;
        if (this.is_byte_data()) {
            this._bytes = curr.getByteBuffer();
        } else {
            this._chars = curr.getCharBuffer();
        }
        this._buffer.setCurrentPage(idx, curr);
        if (pos > limit2) {
            this.refill_is_eof();
            return;
        }
    }

    private final boolean is_byte_data() {
        return this._is_byte_data;
    }

    public final void unread(int c) {
        if (c == -1) {
            return;
        }
        if (c < 0 || c > this._max_char_value) {
            throw new IllegalArgumentException();
        }
        if (this._eof) {
            this._eof = false;
            if (this._limit == -1) {
                this._limit = this._pos;
            }
        }
        --this._pos;
        if (this._pos >= 0) {
            UnifiedDataPageX curr = this._buffer.getCurrentPage();
            if (this._pos < curr.getStartingOffset()) {
                curr.inc_unread_count();
                if (this.is_byte_data()) {
                    this._bytes[this._pos] = (byte)c;
                } else {
                    this._chars[this._pos] = (char)c;
                }
            } else {
                this.verify_matched_unread(c);
            }
        } else {
            this._buffer.putCharAt(this.getPosition(), c);
        }
    }

    private final void verify_matched_unread(int c) {
    }

    public final boolean unread_optional_cr() {
        int c;
        boolean did_unread = false;
        UnifiedDataPageX curr = this._buffer.getCurrentPage();
        if (this._pos > curr.getStartingOffset() && (c = this.is_byte_data() ? this._bytes[this._pos - 1] & 0xFF : this._chars[this._pos - 1]) == 13) {
            --this._pos;
        }
        return did_unread;
    }

    public final int read() throws IOException {
        if (this._pos >= this._limit) {
            return this.read_helper();
        }
        assert (this._bytes == null ^ this._chars == null);
        return this._is_byte_data ? this._bytes[this._pos++] & 0xFF : this._chars[this._pos++];
    }

    protected final int read_helper() throws IOException {
        if (this._eof) {
            return -1;
        }
        if (this.refill_helper()) {
            return -1;
        }
        int c = this.is_byte_data() ? this._bytes[this._pos++] & 0xFF : this._chars[this._pos++];
        return c;
    }

    private final boolean refill_helper() throws IOException {
        this._limit = this.refill();
        if (this._pos >= this._limit) {
            this._eof = true;
            return true;
        }
        return false;
    }

    public final void skip(int skipDistance) throws IOException {
        int remaining = this._limit - this._pos;
        if (remaining >= skipDistance) {
            this._pos += skipDistance;
            remaining = 0;
        } else {
            remaining = skipDistance;
            while (remaining > 0) {
                int ready = this._limit - this._pos;
                if (ready > remaining) {
                    ready = remaining;
                }
                this._pos += ready;
                if ((remaining -= ready) <= 0 || !this.refill_helper()) continue;
                break;
            }
        }
        if (remaining > 0) {
            String message = "unexpected EOF encountered during skip of " + skipDistance + " at position " + this.getPosition();
            throw new IOException(message);
        }
    }

    public final int read(byte[] dst, int offset, int length) throws IOException {
        if (!this.is_byte_data()) {
            throw new IOException("byte read is not support over character sources");
        }
        int remaining = length;
        while (remaining > 0 && !this.isEOF()) {
            int ready = this._limit - this._pos;
            if (ready > remaining) {
                ready = remaining;
            }
            System.arraycopy(this._bytes, this._pos, dst, offset, ready);
            this._pos += ready;
            offset += ready;
            if ((remaining -= ready) != 0 && this._pos >= this._limit && !this.refill_helper()) continue;
            break;
        }
        return length - remaining;
    }

    private int read_utf8(int c) throws IOException {
        int len = IonUTF8.getUTF8LengthFromFirstByte(c);
        for (int ii = 1; ii < len; ++ii) {
            int c2 = this.read();
            if (c2 == -1) {
                throw new IonReaderTextRawTokensX.IonReaderTextTokenException("invalid UTF8 sequence encountered in stream");
            }
            c |= c2 << ii * 8;
        }
        c = IonUTF8.getScalarFrom4BytesReversed(c);
        return c;
    }

    protected int refill() throws IOException {
        long file_position;
        UnifiedDataPageX curr = this._buffer.getCurrentPage();
        UnifiedSavePointManagerX.SavePoint sp = this._save_points.savePointActiveTop();
        if (!this.can_fill_new_page()) {
            return this.refill_is_eof();
        }
        if (sp != null && sp.getEndIdx() == this._buffer.getCurrentPageIdx()) {
            return this.refill_is_eof();
        }
        int start_pos = 10;
        if (curr == null) {
            file_position = 0L;
            start_pos = 0;
        } else {
            file_position = curr.getFilePosition(this._pos);
            if (file_position == 0L) {
                start_pos = 0;
            }
        }
        int new_idx = this._buffer.getNextFilledPageIdx();
        if (new_idx < 0) {
            int read;
            curr = this._buffer.getCurrentPage();
            boolean needs_new_page = curr == null;
            new_idx = this._buffer.getCurrentPageIdx();
            if (this._save_points.isSavePointOpen()) {
                ++new_idx;
                needs_new_page = true;
            }
            if (needs_new_page) {
                curr = this._buffer.getEmptyPageIdx();
            }
            if ((read = this.load(curr, start_pos, file_position)) < 1) {
                return this.refill_is_eof();
            }
            assert (curr != null && curr.getOffsetOfFilePosition(file_position) == start_pos);
            this.set_current_page(new_idx, curr, start_pos);
        } else {
            int endidx;
            assert (!this.isEOF());
            if (sp != null && (endidx = sp.getEndIdx()) != -1 && endidx < new_idx) {
                return this.refill_is_eof();
            }
            curr = this._buffer.getPage(new_idx);
            assert (curr.getStartingFileOffset() == file_position);
            this.set_current_page(new_idx, curr, curr.getStartingOffset());
            if (sp != null && sp.getEndIdx() == new_idx) {
                this._limit = sp.getEndPos();
            }
        }
        assert (this.isEOF() ^ this._limit > 0);
        return this._limit;
    }

    private void set_current_page(int new_page_idx, UnifiedDataPageX new_page, int pos) {
        assert (new_page != null && new_page_idx >= 0 && new_page_idx <= this._buffer.getPageCount() + 1);
        UnifiedDataPageX curr = null;
        if (new_page_idx < this._buffer.getPageCount()) {
            curr = this._buffer.getPage(new_page_idx);
        }
        if (new_page != curr) {
            this._buffer.setPage(new_page_idx, new_page, true);
        }
        this.make_page_current(new_page, new_page_idx, pos, new_page.getBufferLimit());
    }

    private int refill_is_eof() {
        this._eof = true;
        this._limit = -1;
        return this._limit;
    }

    private final boolean can_fill_new_page() {
        return this._is_stream;
    }

    protected final int load(UnifiedDataPageX curr, int start_pos, long file_position) throws IOException {
        int read = 0;
        if (this.can_fill_new_page()) {
            read = this.is_byte_data() ? curr.load(this._stream, start_pos, file_position) : curr.load(this._reader, start_pos, file_position);
        }
        return read;
    }

    private static class FromByteStream
    extends UnifiedInputStreamX {
        FromByteStream(InputStream stream) throws IOException {
            this._is_byte_data = true;
            this._is_stream = true;
            this._stream = stream;
            this._buffer = UnifiedInputBufferX.makePageBuffer(UnifiedInputBufferX.BufferType.BYTES, DEFAULT_PAGE_SIZE);
            ((UnifiedInputStreamX)this).init();
            this._limit = this.refill();
        }

        @Override
        public void close() throws IOException {
            super.close();
            this._stream.close();
        }
    }

    static class FromByteArray
    extends UnifiedInputStreamX {
        FromByteArray(byte[] bytes, int offset, int length) {
            this._is_byte_data = true;
            this._is_stream = false;
            this._buffer = UnifiedInputBufferX.makePageBuffer(bytes, offset, length);
            UnifiedDataPageX curr = this._buffer.getCurrentPage();
            this.make_page_current(curr, 0, offset, offset + length);
            ((UnifiedInputStreamX)this).init();
        }
    }

    private static class FromCharStream
    extends UnifiedInputStreamX {
        FromCharStream(Reader reader) throws IOException {
            this._is_byte_data = false;
            this._is_stream = true;
            this._reader = reader;
            this._buffer = UnifiedInputBufferX.makePageBuffer(UnifiedInputBufferX.BufferType.CHARS, DEFAULT_PAGE_SIZE);
            ((UnifiedInputStreamX)this).init();
            this._limit = this.refill();
        }

        @Override
        public void close() throws IOException {
            super.close();
            this._reader.close();
        }
    }

    private static class FromCharArray
    extends UnifiedInputStreamX {
        FromCharArray(CharSequence chars, int offset, int length) {
            this._is_byte_data = false;
            this._is_stream = false;
            this._buffer = UnifiedInputBufferX.makePageBuffer(chars, offset, length);
            UnifiedDataPageX curr = this._buffer.getCurrentPage();
            this.make_page_current(curr, 0, offset, offset + length);
            ((UnifiedInputStreamX)this).init();
        }

        FromCharArray(char[] charArray, int offset, int length) {
            this._is_byte_data = false;
            this._is_stream = false;
            this._buffer = UnifiedInputBufferX.makePageBuffer(charArray, offset, length);
            UnifiedDataPageX curr = this._buffer.getCurrentPage();
            this.make_page_current(curr, 0, offset, offset + length);
            ((UnifiedInputStreamX)this).init();
        }
    }
}

