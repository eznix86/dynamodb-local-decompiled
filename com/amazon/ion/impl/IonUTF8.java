/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazon.ion.impl;

import com.amazon.ion.IonException;
import java.io.Closeable;
import java.io.IOException;
import java.io.OutputStream;

class IonUTF8 {
    private static final int UNICODE_MAX_ONE_BYTE_SCALAR = 127;
    private static final int UNICODE_MAX_TWO_BYTE_SCALAR = 2047;
    private static final int UNICODE_MAX_THREE_BYTE_SCALAR = 65535;
    private static final int UNICODE_MAX_FOUR_BYTE_SCALAR = 0x10FFFF;
    private static final int UNICODE_THREE_BYTES_OR_FEWER_MASK = -65536;
    private static final int UNICODE_TWO_BYTE_HEADER = 192;
    private static final int UNICODE_THREE_BYTE_HEADER = 224;
    private static final int UNICODE_FOUR_BYTE_HEADER = 240;
    private static final int UNICODE_CONTINUATION_BYTE_HEADER = 128;
    private static final int UNICODE_TWO_BYTE_MASK = 31;
    private static final int UNICODE_THREE_BYTE_MASK = 15;
    private static final int UNICODE_FOUR_BYTE_MASK = 7;
    private static final int UNICODE_CONTINUATION_BYTE_MASK = 63;
    private static final int MAXIMUM_UTF16_1_CHAR_CODE_POINT = 65535;
    private static final int SURROGATE_OFFSET = 65536;
    private static final int SURROGATE_MASK = -1024;
    private static final int HIGH_SURROGATE = 55296;
    private static final int LOW_SURROGATE = 56320;

    IonUTF8() {
    }

    public static final boolean isHighSurrogate(int b) {
        return (b & 0xFFFFFC00) == 55296;
    }

    public static final boolean isLowSurrogate(int b) {
        return (b & 0xFFFFFC00) == 56320;
    }

    public static final boolean isSurrogate(int b) {
        return b >= 55296 && b <= 57343;
    }

    public static final boolean isOneByteUTF8(int b) {
        return (b & 0x80) == 0;
    }

    public static final boolean isTwoByteUTF8(int b) {
        return (b & 0xFFFFFFE0) == 192;
    }

    public static final boolean isThreeByteUTF8(int b) {
        return (b & 0xFFFFFFF0) == 224;
    }

    public static final boolean isFourByteUTF8(int b) {
        return (b & 0xFFFFFFF8) == 240;
    }

    public static final boolean isContinueByteUTF8(int b) {
        return (b & 0xFFFFFFC0) == 128;
    }

    public static final boolean isStartByte(int b) {
        return IonUTF8.isOneByteUTF8(b) || !IonUTF8.isContinueByteUTF8(b);
    }

    public static final char twoByteScalar(int b1, int b2) {
        int c = (b1 & 0x1F) << 6 | b2 & 0x3F;
        return (char)c;
    }

    public static final int threeByteScalar(int b1, int b2, int b3) {
        int c = (b1 & 0xF) << 12 | (b2 & 0x3F) << 6 | b3 & 0x3F;
        return c;
    }

    public static final int fourByteScalar(int b1, int b2, int b3, int b4) {
        int c = (b1 & 7) << 18 | (b2 & 0x3F) << 12 | (b3 & 0x3F) << 6 | b4 & 0x3F;
        return c;
    }

    public static final boolean isOneByteScalar(int unicodeScalar) {
        return unicodeScalar <= 127;
    }

    public static final boolean isTwoByteScalar(int unicodeScalar) {
        return unicodeScalar <= 2047;
    }

    public static final boolean isThreeByteScalar(int unicodeScalar) {
        return unicodeScalar <= 65535;
    }

    public static final boolean isFourByteScalar(int unicodeScalar) {
        return unicodeScalar <= 0x10FFFF;
    }

    public static final int getUTF8ByteCount(int unicodeScalar) {
        if ((unicodeScalar & 0xFFFF0000) != 0) {
            if (unicodeScalar >= 0 && unicodeScalar <= 0x10FFFF) {
                return 4;
            }
            throw new InvalidUnicodeCodePoint();
        }
        if (unicodeScalar <= 127) {
            return 1;
        }
        if (unicodeScalar <= 2047) {
            return 2;
        }
        return 3;
    }

    public static final int getUTF8LengthFromFirstByte(int firstByte) {
        if (IonUTF8.isOneByteUTF8(firstByte &= 0xFF)) {
            return 1;
        }
        if (IonUTF8.isTwoByteUTF8(firstByte)) {
            return 2;
        }
        if (IonUTF8.isThreeByteUTF8(firstByte)) {
            return 3;
        }
        if (IonUTF8.isFourByteUTF8(firstByte)) {
            return 4;
        }
        return -1;
    }

    public static final byte getByte1Of2(int unicodeScalar) {
        int b1 = 0xC0 | unicodeScalar >> 6 & 0x1F;
        return (byte)b1;
    }

    public static final byte getByte2Of2(int unicodeScalar) {
        int b2 = 0x80 | unicodeScalar & 0x3F;
        return (byte)b2;
    }

    public static final byte getByte1Of3(int unicodeScalar) {
        int b1 = 0xE0 | unicodeScalar >> 12 & 0xF;
        return (byte)b1;
    }

    public static final byte getByte2Of3(int unicodeScalar) {
        int b2 = 0x80 | unicodeScalar >> 6 & 0x3F;
        return (byte)b2;
    }

    public static final byte getByte3Of3(int unicodeScalar) {
        int b3 = 0x80 | unicodeScalar & 0x3F;
        return (byte)b3;
    }

    public static final byte getByte1Of4(int unicodeScalar) {
        int b1 = 0xF0 | unicodeScalar >> 18 & 7;
        return (byte)b1;
    }

    public static final byte getByte2Of4(int unicodeScalar) {
        int b2 = 0x80 | unicodeScalar >> 12 & 0x3F;
        return (byte)b2;
    }

    public static final byte getByte3Of4(int unicodeScalar) {
        int b3 = 0x80 | unicodeScalar >> 6 & 0x3F;
        return (byte)b3;
    }

    public static final byte getByte4Of4(int unicodeScalar) {
        int b4 = 0x80 | unicodeScalar & 0x3F;
        return (byte)b4;
    }

    public static final int getAs4BytesReversed(int unicodeScalar) {
        switch (IonUTF8.getUTF8ByteCount(unicodeScalar)) {
            case 1: {
                return unicodeScalar;
            }
            case 2: {
                int four_bytes = IonUTF8.getByte1Of2(unicodeScalar);
                return four_bytes |= IonUTF8.getByte2Of2(unicodeScalar) << 8;
            }
            case 3: {
                int four_bytes = IonUTF8.getByte1Of3(unicodeScalar);
                four_bytes |= IonUTF8.getByte2Of3(unicodeScalar) << 8;
                return four_bytes |= IonUTF8.getByte3Of3(unicodeScalar) << 16;
            }
            case 4: {
                int four_bytes = IonUTF8.getByte1Of4(unicodeScalar);
                four_bytes |= IonUTF8.getByte2Of4(unicodeScalar) << 8;
                four_bytes |= IonUTF8.getByte3Of4(unicodeScalar) << 16;
                return four_bytes |= IonUTF8.getByte4Of4(unicodeScalar) << 24;
            }
        }
        throw new InvalidUnicodeCodePoint();
    }

    public static final int convertToUTF8Bytes(int unicodeScalar, byte[] outputBytes, int offset, int maxLength) {
        int dst = offset;
        int end = offset + maxLength;
        switch (IonUTF8.getUTF8ByteCount(unicodeScalar)) {
            case 1: {
                if (dst >= end) {
                    throw new ArrayIndexOutOfBoundsException();
                }
                outputBytes[dst++] = (byte)(unicodeScalar & 0xFF);
                break;
            }
            case 2: {
                if (dst + 1 >= end) {
                    throw new ArrayIndexOutOfBoundsException();
                }
                outputBytes[dst++] = IonUTF8.getByte1Of2(unicodeScalar);
                outputBytes[dst++] = IonUTF8.getByte2Of2(unicodeScalar);
                break;
            }
            case 3: {
                if (dst + 2 >= end) {
                    throw new ArrayIndexOutOfBoundsException();
                }
                outputBytes[dst++] = IonUTF8.getByte1Of3(unicodeScalar);
                outputBytes[dst++] = IonUTF8.getByte2Of3(unicodeScalar);
                outputBytes[dst++] = IonUTF8.getByte3Of3(unicodeScalar);
                break;
            }
            case 4: {
                if (dst + 3 >= end) {
                    throw new ArrayIndexOutOfBoundsException();
                }
                outputBytes[dst++] = IonUTF8.getByte1Of4(unicodeScalar);
                outputBytes[dst++] = IonUTF8.getByte2Of4(unicodeScalar);
                outputBytes[dst++] = IonUTF8.getByte3Of4(unicodeScalar);
                outputBytes[dst++] = IonUTF8.getByte4Of4(unicodeScalar);
            }
        }
        return dst - offset;
    }

    public static final int packBytesAfter1(int unicodeScalar, int utf8Len) {
        int packed_chars;
        switch (utf8Len) {
            default: {
                throw new IllegalArgumentException("pack requires len > 1");
            }
            case 2: {
                packed_chars = IonUTF8.getByte2Of2(unicodeScalar);
                break;
            }
            case 3: {
                packed_chars = IonUTF8.getByte2Of3(unicodeScalar);
                packed_chars |= IonUTF8.getByte3Of3(unicodeScalar) << 8;
                break;
            }
            case 4: {
                packed_chars = IonUTF8.getByte2Of4(unicodeScalar);
                packed_chars |= IonUTF8.getByte3Of4(unicodeScalar) << 8;
                packed_chars |= IonUTF8.getByte4Of4(unicodeScalar) << 16;
            }
        }
        return packed_chars;
    }

    public static final int getScalarFrom4BytesReversed(int utf8BytesReversed) {
        int c = utf8BytesReversed & 0xFF;
        switch (IonUTF8.getUTF8LengthFromFirstByte(c)) {
            case 1: {
                return c;
            }
            case 2: {
                c = (c & 0x1F) << 6;
                return c |= utf8BytesReversed >> 8 & 0x3F;
            }
            case 3: {
                c = (c & 0xF) << 12;
                c |= utf8BytesReversed >> 2 & 0xFC0;
                return c |= utf8BytesReversed >> 16 & 0x3F;
            }
            case 4: {
                c = (c & 7) << 18;
                c |= utf8BytesReversed << 4 & 0x3F000;
                c |= utf8BytesReversed >> 2 & 0xFC0;
                return c |= utf8BytesReversed >> 24 & 0x3F;
            }
        }
        throw new InvalidUnicodeCodePoint();
    }

    public static final int getScalarReadLengthFromBytes(byte[] bytes, int offset, int maxLength) {
        int c;
        int utf8length;
        int src = offset;
        int end = offset + maxLength;
        if (src >= end) {
            throw new ArrayIndexOutOfBoundsException();
        }
        if (src + (utf8length = IonUTF8.getUTF8LengthFromFirstByte(c = bytes[src++] & 0xFF)) > end) {
            throw new ArrayIndexOutOfBoundsException();
        }
        return utf8length;
    }

    public static final int getScalarFromBytes(byte[] bytes, int offset, int maxLength) {
        int c;
        int utf8length;
        int src = offset;
        int end = offset + maxLength;
        if (src >= end) {
            throw new ArrayIndexOutOfBoundsException();
        }
        if (src + (utf8length = IonUTF8.getUTF8LengthFromFirstByte(c = bytes[src++] & 0xFF)) > end) {
            throw new ArrayIndexOutOfBoundsException();
        }
        switch (utf8length) {
            case 1: {
                break;
            }
            case 2: {
                c &= 0x1F;
                c |= bytes[src++] & 0xFF & 0x3F;
                break;
            }
            case 3: {
                c &= 0xF;
                c |= bytes[src++] & 0xFF & 0x3F;
                c |= bytes[src++] & 0xFF & 0x3F;
                break;
            }
            case 4: {
                c &= 7;
                c |= bytes[src++] & 0xFF & 0x3F;
                c |= bytes[src++] & 0xFF & 0x3F;
                c |= bytes[src++] & 0xFF & 0x3F;
                break;
            }
            default: {
                throw new InvalidUnicodeCodePoint("code point is invalid: " + utf8length);
            }
        }
        return c;
    }

    public static final boolean needsSurrogateEncoding(int unicodeScalar) {
        if (unicodeScalar > 0x10FFFF) {
            throw new IonException("Invalid encoding: encountered non-Unicode character.");
        }
        return unicodeScalar > 65535;
    }

    public static final char highSurrogate(int unicodeScalar) {
        assert (unicodeScalar > 65535);
        assert (unicodeScalar <= 0x10FFFF);
        int c = unicodeScalar - 65536 >> 10;
        return (char)((c | 0xD800) & 0xFFFF);
    }

    public static final char lowSurrogate(int unicodeScalar) {
        assert (unicodeScalar > 65535);
        assert (unicodeScalar <= 0x10FFFF);
        int c = unicodeScalar - 65536 & 0x3FF;
        return (char)((c | 0xDC00) & 0xFFFF);
    }

    public static final int getUnicodeScalarFromSurrogates(int highSurrogate, int lowSurrogate) {
        assert (IonUTF8.isHighSurrogate(highSurrogate));
        assert (IonUTF8.isLowSurrogate(lowSurrogate));
        int scalar2 = lowSurrogate & 0x3FF;
        scalar2 += (highSurrogate & 0x3FF) << 10;
        return scalar2 += 65536;
    }

    public static final class UTF8ToChar
    extends OutputStream
    implements Closeable {
        private final Appendable _char_stream;
        private int _expected_count = 0;
        private int _pending_count = 0;
        private int _pending_c1;
        private int _pending_c2;
        private int _pending_c3;

        public UTF8ToChar(Appendable charStream) {
            this._char_stream = charStream;
        }

        public final Appendable getAppendable() {
            return this._char_stream;
        }

        @Override
        public final void close() throws IOException {
            if (this._pending_count > 0) {
                throw new IOException("unfinished utf8 sequence still open");
            }
        }

        @Override
        public final void write(int b) throws IOException {
            b &= 0xFF;
            if (this._expected_count > 0) {
                this.write_helper_continue(b);
            } else if (b > 127) {
                if (!IonUTF8.isStartByte(b)) {
                    throw new IOException("invalid UTF8 sequence: byte > 127 is not a UTF8 leading byte");
                }
                this.write_helper_start_sequence(b);
            } else {
                this._char_stream.append((char)b);
            }
        }

        @Override
        public final void write(byte[] bytes) throws IOException {
            this.write(bytes, 0, bytes.length);
        }

        @Override
        public final void write(byte[] bytes, int off, int len) throws IOException {
            for (int ii = off; ii < off + len; ++ii) {
                int b = bytes[ii] & 0xFF;
                if (this._pending_count == 0 && b < 128) {
                    this._char_stream.append((char)b);
                    continue;
                }
                this.write(b);
            }
        }

        private final void write_helper_start_sequence(int b) throws IOException {
            this._expected_count = IonUTF8.getUTF8LengthFromFirstByte(b);
            this._pending_count = 1;
            this._pending_c1 = b;
        }

        private final void write_helper_continue(int b) throws IOException {
            ++this._pending_count;
            if (this._expected_count == this._pending_count) {
                this.write_helper_write_char(b);
            } else {
                switch (this._pending_count) {
                    case 2: {
                        this._pending_c2 = b;
                        break;
                    }
                    case 3: {
                        this._pending_c3 = b;
                        break;
                    }
                    default: {
                        throw new IOException("invalid state for pending vs expected UTF8 bytes");
                    }
                }
            }
        }

        private final void write_helper_write_char(int b) throws IOException {
            switch (this._pending_count) {
                case 2: {
                    if (!IonUTF8.isContinueByteUTF8(b)) {
                        this.throwBadContinuationByte();
                    }
                    char c = IonUTF8.twoByteScalar(this._pending_c1, b);
                    this._char_stream.append(c);
                    break;
                }
                case 3: {
                    int s;
                    if (!IonUTF8.isContinueByteUTF8(b)) {
                        this.throwBadContinuationByte();
                    }
                    if (!IonUTF8.isContinueByteUTF8(this._pending_c2)) {
                        this.throwBadContinuationByte();
                    }
                    if (IonUTF8.needsSurrogateEncoding(s = IonUTF8.threeByteScalar(this._pending_c1, this._pending_c2, b))) {
                        this._char_stream.append(IonUTF8.lowSurrogate(s));
                        this._char_stream.append(IonUTF8.highSurrogate(s));
                        break;
                    }
                    this._char_stream.append((char)s);
                    break;
                }
                case 4: {
                    int s;
                    if (!IonUTF8.isContinueByteUTF8(b)) {
                        this.throwBadContinuationByte();
                    }
                    if (!IonUTF8.isContinueByteUTF8(this._pending_c2)) {
                        this.throwBadContinuationByte();
                    }
                    if (!IonUTF8.isContinueByteUTF8(this._pending_c3)) {
                        this.throwBadContinuationByte();
                    }
                    if (IonUTF8.needsSurrogateEncoding(s = IonUTF8.fourByteScalar(this._pending_c1, this._pending_c2, this._pending_c3, b))) {
                        this._char_stream.append(IonUTF8.lowSurrogate(s));
                        this._char_stream.append(IonUTF8.highSurrogate(s));
                        break;
                    }
                    this._char_stream.append((char)s);
                    break;
                }
                default: {
                    throw new IOException("invalid state for UTF8 sequence length " + this._pending_count);
                }
            }
        }

        private void throwBadContinuationByte() throws IOException {
            throw new IOException("continuation byte expected");
        }
    }

    public static class InvalidUnicodeCodePoint
    extends IonException {
        private static final long serialVersionUID = -3200811216940328945L;

        public InvalidUnicodeCodePoint() {
            super("invalid UTF8");
        }

        public InvalidUnicodeCodePoint(String msg) {
            super(msg);
        }

        public InvalidUnicodeCodePoint(Exception e) {
            super(e);
        }

        public InvalidUnicodeCodePoint(String msg, Exception e) {
            super(msg, e);
        }
    }
}

