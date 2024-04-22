/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazon.ion.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

abstract class UnifiedDataPageX {
    protected PageType _page_type;
    protected int _page_limit;
    protected int _base_offset;
    protected int _unread_count;
    protected long _file_offset;
    protected byte[] _bytes;
    protected char[] _characters;

    public static final UnifiedDataPageX makePage(byte[] bytes, int offset, int length) {
        return new Bytes(bytes, offset, length);
    }

    public static final UnifiedDataPageX makePage(char[] chars, int offset, int length) {
        return new Chars(chars, offset, length);
    }

    public static final UnifiedDataPageX makePage(PageType pageType, int size) {
        if (size < 1) {
            throw new IllegalArgumentException("invalid page size must be > 0");
        }
        switch (pageType) {
            case CHARS: {
                return new Chars(size);
            }
            case BYTES: {
                return new Bytes(size);
            }
        }
        throw new IllegalArgumentException("invalid page type, s/b 1 or 2");
    }

    private UnifiedDataPageX() {
    }

    public abstract int getValue(int var1);

    public abstract void putValue(int var1, int var2);

    public final PageType getPageType() {
        return this._page_type;
    }

    public final char[] getCharBuffer() {
        return this._characters;
    }

    public final byte[] getByteBuffer() {
        return this._bytes;
    }

    private final boolean isBytes() {
        return this._page_type == PageType.BYTES;
    }

    int load(Reader reader, int start_offset, long file_position) throws IOException {
        if (this.isBytes()) {
            throw new UnsupportedOperationException("byte pages can't load characters");
        }
        int read = reader.read(this._characters, start_offset, this._characters.length - start_offset);
        if (read > 0) {
            this._page_limit = start_offset + read;
            this._base_offset = start_offset;
            this._unread_count = 0;
            this.setFilePosition(file_position, start_offset);
        }
        return read;
    }

    int load(InputStream stream, int start_offset, long file_position) throws IOException {
        if (!this.isBytes()) {
            throw new UnsupportedOperationException("character pages can't load bytes");
        }
        int read = stream.read(this._bytes, start_offset, this._bytes.length - start_offset);
        if (read > 0) {
            this._base_offset = start_offset;
            this._unread_count = 0;
            this._page_limit = start_offset + read;
            this.setFilePosition(file_position, start_offset);
        }
        return read;
    }

    public int getBufferLimit() {
        return this._page_limit;
    }

    public int getOriginalStartingOffset() {
        return this._base_offset;
    }

    public int getStartingOffset() {
        return this._base_offset - this._unread_count;
    }

    public int getUnreadCount() {
        return this._unread_count;
    }

    public void inc_unread_count() {
        ++this._unread_count;
    }

    public final void setFilePosition(long fileOffset, int pos) {
        if (fileOffset < 0L) {
            throw new IllegalArgumentException();
        }
        this._file_offset = fileOffset - (long)pos;
    }

    public final long getFilePosition(int pos) {
        return this._file_offset + (long)pos;
    }

    public final int getOffsetOfFilePosition(long filePosition) {
        if (!this.containsOffset(filePosition)) {
            String message = "requested file position [" + Long.toString(filePosition) + "] is not in this page [" + Long.toString(this.getStartingFileOffset()) + "-" + Long.toString(this.getFilePosition(this._page_limit)) + "]";
            throw new IllegalArgumentException(message);
        }
        return (int)(filePosition - this._file_offset);
    }

    public final long getStartingFileOffset() {
        return this._file_offset + (long)this._base_offset;
    }

    public final boolean containsOffset(long filePosition) {
        if (this._file_offset + (long)this._base_offset > filePosition) {
            return false;
        }
        return filePosition < this._file_offset + (long)this._page_limit;
    }

    protected final int getLengthFollowingFilePosition(long filePosition) {
        int pos = this.getOffsetOfFilePosition(filePosition);
        return this._page_limit - pos;
    }

    public final void reset(int baseOffset) {
        this._page_limit = this._base_offset = baseOffset;
    }

    public abstract int readFrom(int var1, byte[] var2, int var3, int var4);

    public abstract int readFrom(int var1, char[] var2, int var3, int var4);

    public static final class Chars
    extends UnifiedDataPageX {
        public Chars(int size) {
            this(new char[size], 0, size);
        }

        public Chars(char[] chars, int offset, int len) {
            this._page_type = PageType.CHARS;
            this._characters = chars;
            this._base_offset = offset;
            this._page_limit = offset + len;
        }

        @Override
        public int getValue(int pageOffset) {
            if (pageOffset < 0 || pageOffset > this._page_limit - this._base_offset) {
                throw new IllegalArgumentException("offset " + pageOffset + " is not contained in page, limit is " + (this._page_limit - this._base_offset));
            }
            return this._characters[pageOffset];
        }

        @Override
        public void putValue(int pageOffset, int c) {
            this._characters[pageOffset] = (char)c;
        }

        @Override
        public final int readFrom(int pageOffset, byte[] bytes, int offset, int length) {
            throw new UnsupportedOperationException("character pages can't read bytes");
        }

        @Override
        public final int readFrom(int pageOffset, char[] chars, int offset, int length) {
            int chars_read = length;
            if (pageOffset >= this._page_limit) {
                return -1;
            }
            if (chars_read > this._page_limit - pageOffset) {
                chars_read = this._page_limit - pageOffset;
            }
            System.arraycopy(this._characters, pageOffset, chars, offset, chars_read);
            return chars_read;
        }
    }

    public static final class Bytes
    extends UnifiedDataPageX {
        public Bytes(int size) {
            this(new byte[size], 0, size);
        }

        public Bytes(byte[] bytes, int offset, int len) {
            this._page_type = PageType.BYTES;
            this._bytes = bytes;
            this._base_offset = offset;
            this._page_limit = offset + len;
        }

        @Override
        public int getValue(int offset) {
            return this._bytes[offset] & 0xFF;
        }

        @Override
        public void putValue(int offset, int b) {
            this._bytes[this._base_offset] = (byte)b;
        }

        @Override
        public final int readFrom(int pageOffset, byte[] bytes, int offset, int length) {
            int bytes_read = length;
            if (pageOffset >= this._page_limit) {
                return -1;
            }
            if (bytes_read > this._page_limit - pageOffset) {
                bytes_read = this._page_limit - pageOffset;
            }
            System.arraycopy(this._bytes, pageOffset, bytes, offset, bytes_read);
            return bytes_read;
        }

        @Override
        public final int readFrom(int pageOffset, char[] chars, int offset, int length) {
            throw new UnsupportedOperationException("byte pages can't read characters");
        }
    }

    public static enum PageType {
        BYTES,
        CHARS;

    }
}

