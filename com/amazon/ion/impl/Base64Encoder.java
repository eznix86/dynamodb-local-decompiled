/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazon.ion.impl;

import com.amazon.ion.IonException;
import com.amazon.ion.util.IonTextUtils;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

final class Base64Encoder {
    private static final EL[] Base64Alphabet = new EL[]{new EL(-1, '='), new EL(0, 'A'), new EL(17, 'R'), new EL(34, 'i'), new EL(51, 'z'), new EL(1, 'B'), new EL(18, 'S'), new EL(35, 'j'), new EL(52, '0'), new EL(2, 'C'), new EL(19, 'T'), new EL(36, 'k'), new EL(53, '1'), new EL(3, 'D'), new EL(20, 'U'), new EL(37, 'l'), new EL(54, '2'), new EL(4, 'E'), new EL(21, 'V'), new EL(38, 'm'), new EL(55, '3'), new EL(5, 'F'), new EL(22, 'W'), new EL(39, 'n'), new EL(56, '4'), new EL(6, 'G'), new EL(23, 'X'), new EL(40, 'o'), new EL(57, '5'), new EL(7, 'H'), new EL(24, 'Y'), new EL(41, 'p'), new EL(58, '6'), new EL(8, 'I'), new EL(25, 'Z'), new EL(42, 'q'), new EL(59, '7'), new EL(9, 'J'), new EL(26, 'a'), new EL(43, 'r'), new EL(60, '8'), new EL(10, 'K'), new EL(27, 'b'), new EL(44, 's'), new EL(61, '9'), new EL(11, 'L'), new EL(28, 'c'), new EL(45, 't'), new EL(62, '+'), new EL(12, 'M'), new EL(29, 'd'), new EL(46, 'u'), new EL(63, '/'), new EL(13, 'N'), new EL(30, 'e'), new EL(47, 'v'), new EL(14, 'O'), new EL(31, 'f'), new EL(48, 'w'), new EL(15, 'P'), new EL(32, 'g'), new EL(49, 'x'), new EL(16, 'Q'), new EL(33, 'h'), new EL(50, 'y')};
    static final char URLSafe64IntToCharTerminator = Base64Encoder.init64IntToCharTerminator(Base64Alphabet);
    static final int[] URLSafe64IntToChar = Base64Encoder.init64IntToChar(Base64Alphabet);
    static final int[] URLSafe64CharToInt = Base64Encoder.init64CharToInt(Base64Alphabet);
    static final int[] Base64EncodingIntToChar = Base64Encoder.init64IntToChar(Base64Alphabet);
    static final int[] Base64EncodingCharToInt = Base64Encoder.init64CharToInt(Base64Alphabet);
    static final char Base64EncodingTerminator = Base64Encoder.init64IntToCharTerminator(Base64Alphabet);
    static final int BUFSIZE = 1024;
    static final int BUFSIZE_BIN = 384;
    static final int BUFSIZE_TEXT = 512;

    private static char init64IntToCharTerminator(EL[] els) {
        for (EL letter : els) {
            if (letter.value != -1) continue;
            return letter.letter;
        }
        throw new RuntimeException(new IonException("fatal: invalid char map definition - missing terminator"));
    }

    private static int[] init64IntToChar(EL[] els) {
        int[] output = new int[64];
        for (EL letter : els) {
            if (letter.value == -1) continue;
            output[letter.value] = letter.letter;
        }
        return output;
    }

    private static int[] init64CharToInt(EL[] els) {
        int[] output = new int[256];
        for (int ii = 0; ii < 256; ++ii) {
            output[ii] = -1;
        }
        for (EL letter : els) {
            if (letter.letter > '\u00ff') {
                throw new RuntimeException("fatal base 64 encoding static initializer: letter out of bounds");
            }
            if (letter.value < 0) continue;
            output[letter.letter] = letter.value;
        }
        return output;
    }

    static final boolean isBase64Character(int c) {
        if (c < 32 || c > 255) {
            return false;
        }
        return URLSafe64CharToInt[c] >= 0;
    }

    private Base64Encoder() {
    }

    static final class TextStream
    extends Reader {
        final InputStream _source;
        final int[] _bintochar;
        final char _padding;
        boolean _ready;
        int _state;
        byte[] _inbuf = new byte[385];
        char[] _outbuf = new char[512];
        int _outBufEnd;
        int _outBufPos;

        TextStream(InputStream input, int[] bintochar, char terminator) {
            this._source = input;
            this._bintochar = bintochar;
            this._padding = terminator;
            this._ready = true;
        }

        public TextStream(InputStream input) {
            this(input, URLSafe64IntToChar, URLSafe64IntToCharTerminator);
        }

        @Override
        public void close() throws IOException {
            if (this._ready) {
                this._ready = false;
                this._source.close();
            }
            this._inbuf = null;
            this._outbuf = null;
        }

        @Override
        public void mark(int readAheadLimit) throws IOException {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean markSupported() {
            return false;
        }

        private void loadNextBuffer() throws IOException {
            this._outBufEnd = 0;
            this._outBufPos = 0;
            int inlen = this._source.read(this._inbuf, 0, 384);
            if (inlen < 0) {
                this._state = 1;
                return;
            }
            int ii = 0;
            while (ii < inlen) {
                int c = this._inbuf[ii++] & 0xFF;
                int convert = c << 16;
                if (ii >= inlen) {
                    this._outbuf[this._outBufEnd++] = (char)this._bintochar[(convert & 0xFC0000) >> 18];
                    this._outbuf[this._outBufEnd++] = (char)this._bintochar[(convert & 0x3F000) >> 12];
                    this._outbuf[this._outBufEnd++] = this._padding;
                    this._outbuf[this._outBufEnd++] = this._padding;
                    break;
                }
                c = this._inbuf[ii++] & 0xFF;
                convert |= c << 8;
                if (ii >= inlen) {
                    this._outbuf[this._outBufEnd++] = (char)this._bintochar[(convert & 0xFC0000) >> 18];
                    this._outbuf[this._outBufEnd++] = (char)this._bintochar[(convert & 0x3F000) >> 12];
                    this._outbuf[this._outBufEnd++] = (char)this._bintochar[(convert & 0xFC0) >> 6];
                    this._outbuf[this._outBufEnd++] = this._padding;
                    break;
                }
                c = this._inbuf[ii++] & 0xFF;
                this._outbuf[this._outBufEnd++] = (char)this._bintochar[((convert |= c << 0) & 0xFC0000) >> 18];
                this._outbuf[this._outBufEnd++] = (char)this._bintochar[(convert & 0x3F000) >> 12];
                this._outbuf[this._outBufEnd++] = (char)this._bintochar[(convert & 0xFC0) >> 6];
                this._outbuf[this._outBufEnd++] = (char)this._bintochar[(convert & 0x3F) >> 0];
            }
        }

        @Override
        public int read() throws IOException {
            int outchar = -1;
            if (!this._ready) {
                throw new IOException(this.getClass().getName() + " is closed");
            }
            if (this._state != 0) {
                return -1;
            }
            if (this._outBufPos >= this._outBufEnd) {
                this.loadNextBuffer();
            }
            if (this._outBufPos < this._outBufEnd) {
                outchar = this._outbuf[this._outBufPos++];
            }
            return outchar;
        }

        @Override
        public int read(char[] cbuf) throws IOException {
            int xfer;
            if (!this._ready) {
                throw new IOException(this.getClass().getName() + " is closed");
            }
            if (this._state != 0) {
                return -1;
            }
            int dstPos = 0;
            for (int needed = cbuf.length; needed > 0; needed -= xfer) {
                if (this._outBufPos >= this._outBufEnd) {
                    this.loadNextBuffer();
                }
                if (this._outBufPos >= this._outBufEnd) break;
                xfer = this._outBufEnd - this._outBufPos;
                if (xfer > needed) {
                    xfer = needed;
                }
                System.arraycopy(this._outbuf, this._outBufPos, cbuf, dstPos, xfer);
                this._outBufPos += xfer;
            }
            return dstPos;
        }

        @Override
        public int read(char[] cbuf, int off, int rlen) throws IOException {
            int xfer;
            if (!this._ready) {
                throw new IOException(this.getClass().getName() + " is closed");
            }
            if (this._state != 0) {
                return -1;
            }
            int dstPos = off;
            for (int needed = rlen; needed > 0; needed -= xfer) {
                if (this._outBufPos >= this._outBufEnd) {
                    this.loadNextBuffer();
                }
                if (this._outBufPos >= this._outBufEnd) break;
                xfer = this._outBufEnd - this._outBufPos;
                if (xfer > needed) {
                    xfer = needed;
                }
                System.arraycopy(this._outbuf, this._outBufPos, cbuf, dstPos, xfer);
                this._outBufPos += xfer;
                dstPos += xfer;
            }
            return dstPos;
        }

        @Override
        public boolean ready() throws IOException {
            return this._ready && this._source.available() > 0;
        }

        @Override
        public void reset() throws IOException {
            throw new IOException("reset not supported");
        }

        @Override
        public long skip(long n) throws IOException {
            if (!this._ready) {
                throw new IOException(this.getClass().getName() + " is closed");
            }
            if (n < 0L) {
                throw new IllegalArgumentException("error skip only support non-negative a values for n");
            }
            long needed = n;
            while (needed > 0L) {
                int available = this._outBufEnd - this._outBufPos;
                if (available < 1) {
                    this.loadNextBuffer();
                    available = this._outBufEnd - this._outBufPos;
                    if (available < 1) break;
                }
                if ((long)available > needed) {
                    this._outBufPos += (int)needed;
                    needed = 0L;
                    break;
                }
                needed -= (long)available;
                this._outBufPos += available;
            }
            return n - needed;
        }
    }

    static final class BinaryStream
    extends Reader {
        boolean _ready;
        Reader _source;
        int[] _chartobin;
        int _terminator;
        int _otherTerminator;
        int _terminatingChar;
        int _state;
        char[] _buffer;
        int _bufEnd;
        int _bufPos;

        BinaryStream(Reader input, int[] chartobin, char terminator, char otherTerminator) {
            this._source = input;
            this._chartobin = chartobin;
            this._terminator = terminator;
            this._otherTerminator = otherTerminator;
            this._terminatingChar = -1;
            this._buffer = new char[5];
            this._ready = true;
        }

        BinaryStream(Reader input, char altterminator) {
            this(input, URLSafe64CharToInt, URLSafe64IntToCharTerminator, altterminator);
        }

        int terminatingChar() {
            return this._terminatingChar;
        }

        private int characterToBinary(int c) throws IOException {
            int result = -1;
            if (c >= 0 && c < this._chartobin.length) {
                result = this._chartobin[c];
            }
            if (result < 0) {
                throw new IonException("invalid base64 character (" + c + ")");
            }
            return result;
        }

        private void loadNextBuffer() throws IOException {
            int inlen = 0;
            int c = -1;
            this._bufEnd = 0;
            this._bufPos = 0;
            if (this._state == 1) {
                return;
            }
            while (inlen < 4) {
                c = this._source.read();
                if (c == -1 || c == 65535 || c == this._terminator || c == this._otherTerminator) {
                    this._terminatingChar = c;
                    break;
                }
                if (IonTextUtils.isWhitespace(c)) continue;
                int cbin = this.characterToBinary(c);
                this._buffer[inlen++] = (char)cbin;
            }
            if (inlen != 4) {
                if (inlen == 0 && c != this._terminator) {
                    this._state = 1;
                    return;
                }
                int templen = inlen;
                while (c == this._terminator) {
                    ++templen;
                    c = this._source.read();
                }
                if (templen != 4) {
                    throw new IonException("base64 character count must be divisible by 4, using '=' for padding");
                }
                if (inlen < 1) {
                    throw new IonException("base64 character count must be divisible by 4, but using no more than 3 '=' chars for padding");
                }
                this._terminatingChar = c;
            }
            int ii = 0;
            c = this._buffer[ii++];
            int convert = c << 18;
            c = this._buffer[ii++];
            convert |= c << 12;
            if (inlen < 3) {
                this._buffer[this._bufEnd++] = (char)((convert & 0xFF0000) >> 16);
                this._state = 1;
            } else {
                c = this._buffer[ii++];
                convert |= c << 6;
                if (inlen < 4) {
                    this._buffer[this._bufEnd++] = (char)((convert & 0xFF0000) >> 16);
                    this._buffer[this._bufEnd++] = (char)((convert & 0xFF00) >> 8);
                    this._state = 1;
                } else {
                    c = this._buffer[ii++];
                    this._buffer[this._bufEnd++] = (char)(((convert |= c << 0) & 0xFF0000) >> 16);
                    this._buffer[this._bufEnd++] = (char)((convert & 0xFF00) >> 8);
                    this._buffer[this._bufEnd++] = (char)((convert & 0xFF) >> 0);
                }
            }
        }

        @Override
        public boolean markSupported() {
            return false;
        }

        @Override
        public void close() throws IOException {
            this._source.close();
        }

        @Override
        public int read() throws IOException {
            int outchar = -1;
            if (!this._ready) {
                throw new IOException(this.getClass().getName() + " is not ready");
            }
            if (this._bufPos >= this._bufEnd) {
                this.loadNextBuffer();
            }
            if (this._bufPos < this._bufEnd) {
                outchar = this._buffer[this._bufPos++];
            }
            return outchar;
        }

        @Override
        public int read(char[] cbuf) throws IOException {
            int c;
            int ii = 0;
            for (ii = 0; ii < cbuf.length && (c = this.read()) != -1; ++ii) {
                cbuf[ii] = (char)c;
            }
            return ii;
        }

        @Override
        public int read(char[] cbuf, int off, int rlen) throws IOException {
            int c;
            int ii = 0;
            for (ii = 0; ii < rlen && (c = this.read()) != -1; ++ii) {
                cbuf[off + ii] = (char)c;
            }
            return ii;
        }
    }

    static class EL {
        public int value;
        public char letter;

        public EL(int v, char l) {
            this.value = v;
            this.letter = l;
        }
    }
}

