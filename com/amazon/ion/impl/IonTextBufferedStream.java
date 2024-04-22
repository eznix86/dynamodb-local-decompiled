/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazon.ion.impl;

import java.io.IOException;
import java.io.InputStream;

abstract class IonTextBufferedStream
extends InputStream {
    IonTextBufferedStream() {
    }

    public static IonTextBufferedStream makeStream(byte[] bytes) {
        return new SimpleBufferStream(bytes);
    }

    public static IonTextBufferedStream makeStream(byte[] bytes, int offset, int len) {
        return new OffsetBufferStream(bytes, offset, len);
    }

    public static IonTextBufferedStream makeStream(String text) {
        return new StringStream(text);
    }

    public abstract int getByte(int var1);

    public abstract int position();

    public abstract IonTextBufferedStream setPosition(int var1);

    static final class OffsetBufferStream
    extends IonTextBufferedStream {
        byte[] _buffer;
        int _start;
        int _end;
        int _pos;

        public OffsetBufferStream(byte[] buffer, int start, int max) {
            this._buffer = buffer;
            this._pos = start;
            this._start = start;
            this._end = start + max;
        }

        @Override
        public final int getByte(int pos) {
            if (pos < 0) {
                return -1;
            }
            if ((pos += this._start) >= this._end) {
                return -1;
            }
            return this._buffer[pos] & 0xFF;
        }

        @Override
        public final int read() throws IOException {
            if (this._pos >= this._end) {
                return -1;
            }
            int c = this._buffer[this._pos++] & 0xFF;
            return c;
        }

        @Override
        public final int read(byte[] bytes, int offset, int len) throws IOException {
            int copied = 0;
            if (offset < 0) {
                throw new IllegalArgumentException();
            }
            copied = len;
            if (this._pos + len >= this._end) {
                copied = this._end - this._pos;
            }
            System.arraycopy(this._buffer, this._pos, bytes, offset, copied);
            this._pos += copied;
            return copied;
        }

        @Override
        public final int position() {
            return this._pos - this._start;
        }

        @Override
        public final OffsetBufferStream setPosition(int pos) {
            if (pos < 0) {
                throw new IllegalArgumentException();
            }
            if ((pos += this._start) > this._end) {
                throw new IllegalArgumentException();
            }
            this._pos = pos;
            return this;
        }

        @Override
        public void close() throws IOException {
            this._pos = this._end;
            super.close();
        }
    }

    static final class StringStream
    extends IonTextBufferedStream {
        String _string;
        int _end;
        int _pos;

        public StringStream(String text) {
            this._string = text;
            this._pos = 0;
            this._end = text.length();
        }

        @Override
        public final int getByte(int pos) {
            if (pos < 0) {
                return -1;
            }
            if (pos >= this._end) {
                return -1;
            }
            return this._string.charAt(pos);
        }

        @Override
        public final int read() throws IOException {
            if (this._pos >= this._end) {
                return -1;
            }
            char c = this._string.charAt(this._pos++);
            return c;
        }

        @Override
        public final int read(byte[] bytes, int offset, int len) throws IOException {
            throw new UnsupportedOperationException();
        }

        @Override
        public final int position() {
            return this._pos;
        }

        @Override
        public final StringStream setPosition(int pos) {
            if (pos < 0) {
                throw new IllegalArgumentException();
            }
            if (pos > this._end) {
                throw new IllegalArgumentException();
            }
            this._pos = pos;
            return this;
        }

        @Override
        public void close() throws IOException {
            this._pos = this._end;
            super.close();
        }
    }

    static final class SimpleBufferStream
    extends IonTextBufferedStream {
        byte[] _buffer;
        int _len;
        int _pos;

        public SimpleBufferStream(byte[] buffer) {
            this._buffer = buffer;
            this._pos = 0;
            this._len = buffer.length;
        }

        @Override
        public final int getByte(int pos) {
            if (pos < 0 || pos >= this._len) {
                return -1;
            }
            return this._buffer[pos] & 0xFF;
        }

        @Override
        public final int read() throws IOException {
            if (this._pos >= this._len) {
                return -1;
            }
            return this._buffer[this._pos++];
        }

        @Override
        public final int read(byte[] bytes, int offset, int len) throws IOException {
            int copied = 0;
            if (offset < 0) {
                throw new IllegalArgumentException();
            }
            copied = len;
            if (this._pos + len >= this._len) {
                copied = this._len - this._pos;
            }
            System.arraycopy(this._buffer, this._pos, bytes, offset, copied);
            this._pos += copied;
            return copied;
        }

        @Override
        public final int position() {
            return this._pos;
        }

        @Override
        public final SimpleBufferStream setPosition(int pos) {
            if (this._pos < 0 || this._pos > this._len) {
                throw new IllegalArgumentException();
            }
            this._pos = pos;
            return this;
        }

        @Override
        public void close() throws IOException {
            this._pos = this._len;
            super.close();
        }
    }
}

