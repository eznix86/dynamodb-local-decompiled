/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazon.ion.impl;

import com.amazon.ion.Decimal;
import com.amazon.ion.IonException;
import com.amazon.ion.Timestamp;
import com.amazon.ion.impl.ByteBuffer;
import com.amazon.ion.impl.ByteReader;
import com.amazon.ion.impl.ByteWriter;
import com.amazon.ion.impl.IonBinary;
import com.amazon.ion.impl._Private_IonConstants;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;

final class SimpleByteBuffer
implements ByteBuffer {
    byte[] _bytes;
    int _start;
    int _eob;
    boolean _is_read_only;

    public SimpleByteBuffer(byte[] bytes) {
        this(bytes, 0, bytes.length, false);
    }

    public SimpleByteBuffer(byte[] bytes, boolean isReadOnly) {
        this(bytes, 0, bytes.length, isReadOnly);
    }

    public SimpleByteBuffer(byte[] bytes, int start, int length) {
        this(bytes, start, length, false);
    }

    public SimpleByteBuffer(byte[] bytes, int start, int length, boolean isReadOnly) {
        if (bytes == null || start < 0 || start > bytes.length || length < 0 || start + length > bytes.length) {
            throw new IllegalArgumentException();
        }
        this._bytes = bytes;
        this._start = start;
        this._eob = start + length;
        this._is_read_only = isReadOnly;
    }

    public int getLength() {
        int length = this._eob - this._start;
        return length;
    }

    @Override
    public byte[] getBytes() {
        int length = this._eob - this._start;
        byte[] copy = new byte[length];
        System.arraycopy(this._bytes, this._start, copy, 0, length);
        return copy;
    }

    @Override
    public int getBytes(byte[] buffer, int offset, int length) {
        if (buffer == null || offset < 0 || offset > buffer.length || length < 0 || offset + length > buffer.length) {
            throw new IllegalArgumentException();
        }
        int datalength = this._eob - this._start;
        if (datalength > length) {
            throw new IllegalArgumentException("insufficient space in destination buffer");
        }
        System.arraycopy(this._bytes, this._start, buffer, offset, datalength);
        return datalength;
    }

    @Override
    public ByteReader getReader() {
        SimpleByteReader reader = new SimpleByteReader(this);
        return reader;
    }

    @Override
    public ByteWriter getWriter() {
        if (this._is_read_only) {
            throw new IllegalStateException("this buffer is read only");
        }
        SimpleByteWriter writer = new SimpleByteWriter(this);
        return writer;
    }

    @Override
    public void writeBytes(OutputStream out) throws IOException {
        int length = this._eob - this._start;
        out.write(this._bytes, this._start, length);
    }

    static final class SimpleByteWriter
    extends OutputStream
    implements ByteWriter {
        private static final int _ib_FLOAT64_LEN = 8;
        private static final Double DOUBLE_POS_ZERO = 0.0;
        SimpleByteBuffer _buffer;
        int _position;

        SimpleByteWriter(SimpleByteBuffer bytebuffer) {
            this._buffer = bytebuffer;
            this._position = bytebuffer._start;
        }

        protected void flushTo(OutputStream userOutput) throws IOException {
            this._buffer.writeBytes(userOutput);
            this._position = 0;
        }

        @Override
        public int position() {
            return this._position - this._buffer._start;
        }

        @Override
        public void position(int newPosition) {
            if (newPosition < 0) {
                throw new IllegalArgumentException("position must be non-negative");
            }
            int pos = newPosition + this._buffer._start;
            if (pos > this._buffer._eob) {
                throw new IllegalArgumentException("position is past end of buffer");
            }
            this._position = pos;
        }

        @Override
        public void insert(int length) {
            if (length < 0) {
                throw new IllegalArgumentException("insert length must be non negative");
            }
            int remaining = this._buffer._eob - this._position;
            System.arraycopy(this._buffer._bytes, this._position, this._buffer._bytes, this._position + length, remaining);
            this._buffer._eob += length;
        }

        @Override
        public void remove(int length) {
            if (length < 0) {
                throw new IllegalArgumentException("remove length must be non negative");
            }
            int remaining = this._buffer._eob - this._position;
            System.arraycopy(this._buffer._bytes, this._position + length, this._buffer._bytes, this._position, remaining);
            this._buffer._eob -= length;
        }

        @Override
        public final void write(int arg0) throws IOException {
            this.write((byte)arg0);
        }

        @Override
        public final void write(byte b) {
            this._buffer._bytes[this._position++] = b;
            if (this._position > this._buffer._eob) {
                this._buffer._eob = this._position;
            }
        }

        @Override
        public void write(byte[] bytes, int start, int len) {
            if (bytes == null || start < 0 || start >= bytes.length || len < 0 || start + len > bytes.length) {
                throw new IllegalArgumentException();
            }
            System.arraycopy(bytes, start, this._buffer._bytes, this._position, len);
            this._position += len;
            if (this._position > this._buffer._eob) {
                this._buffer._eob = this._position;
            }
        }

        @Override
        public void writeTypeDesc(int typeDescByte) {
            this.write((byte)(typeDescByte & 0xFF));
        }

        @Override
        public int writeTypeDescWithLength(int typeid, int lenOfLength, int valueLength) {
            int written_len = 1;
            int td = (typeid & 0xF) << 4;
            if (valueLength >= 14) {
                this.writeTypeDesc(td |= 0xE);
                written_len += this.writeVarUInt(valueLength, lenOfLength, true);
            } else {
                this.writeTypeDesc(td |= valueLength & 0xF);
            }
            return written_len;
        }

        @Override
        public int writeTypeDescWithLength(int typeid, int valueLength) {
            int written_len = 1;
            int td = (typeid & 0xF) << 4;
            if (valueLength >= 14) {
                this.writeTypeDesc(td |= 0xE);
                int lenOfLength = IonBinary.lenVarUInt(valueLength);
                written_len += this.writeVarUInt(valueLength, lenOfLength, true);
            } else {
                this.writeTypeDesc(td |= valueLength & 0xF);
            }
            return written_len;
        }

        @Override
        public int writeVarInt(int value, int len, boolean force_zero_write) {
            if (value != 0) {
                int mask = 127;
                boolean is_negative = false;
                assert (len == IonBinary.lenVarInt(value));
                is_negative = value < 0;
                if (is_negative) {
                    value = -value;
                }
                int b = value >>> 7 * (len - 1) & mask;
                if (is_negative) {
                    b |= 0x40;
                }
                if (len == 1) {
                    b |= 0x80;
                }
                this.write((byte)b);
                switch (len) {
                    case 5: {
                        this.write((byte)(value >> 21 & mask));
                    }
                    case 4: {
                        this.write((byte)(value >> 14 & mask));
                    }
                    case 3: {
                        this.write((byte)(value >> 7 & mask));
                    }
                    case 2: {
                        this.write((byte)(value & mask | 0x80));
                    }
                }
            } else if (force_zero_write) {
                this.write((byte)-128);
                assert (len == 1);
            } else assert (len == 0);
            return len;
        }

        public int writeVarInt(int value, boolean force_zero_write) {
            int len = IonBinary.lenVarInt(value);
            len = this.writeVarInt(value, len, force_zero_write);
            return len;
        }

        @Override
        public int writeVarUInt(int value, int len, boolean force_zero_write) {
            int mask = 127;
            if (value < 0) {
                throw new IllegalArgumentException("signed int where unsigned (>= 0) was expected");
            }
            assert (len == IonBinary.lenVarUInt(value));
            switch (len - 1) {
                case 4: {
                    this.write((byte)(value >> 28 & mask));
                }
                case 3: {
                    this.write((byte)(value >> 21 & mask));
                }
                case 2: {
                    this.write((byte)(value >> 14 & mask));
                }
                case 1: {
                    this.write((byte)(value >> 7 & mask));
                }
                case 0: {
                    this.write((byte)((long)(value & mask) | 0x80L));
                    break;
                }
                case -1: {
                    if (!force_zero_write) break;
                    this.write((byte)-128);
                    len = 1;
                }
            }
            return len;
        }

        public int writeVarUInt(int value, boolean force_zero_write) {
            int len = IonBinary.lenVarUInt(value);
            len = this.writeVarUInt(value, len, force_zero_write);
            return len;
        }

        @Override
        public int writeIonInt(int value, int len) {
            return this.writeIonInt((long)value, len);
        }

        @Override
        public int writeIonInt(long value, int len) {
            boolean is_negative;
            if (value == 0L) {
                assert (len == 0);
                return len;
            }
            long mask = 255L;
            boolean bl = is_negative = value < 0L;
            assert (len == IonBinary.lenIonInt(value));
            if (is_negative) {
                value = -value;
            }
            switch (len) {
                case 8: {
                    this.write((byte)(value >> 56 & mask));
                }
                case 7: {
                    this.write((byte)(value >> 48 & mask));
                }
                case 6: {
                    this.write((byte)(value >> 40 & mask));
                }
                case 5: {
                    this.write((byte)(value >> 32 & mask));
                }
                case 4: {
                    this.write((byte)(value >> 24 & mask));
                }
                case 3: {
                    this.write((byte)(value >> 16 & mask));
                }
                case 2: {
                    this.write((byte)(value >> 8 & mask));
                }
                case 1: {
                    this.write((byte)(value & mask));
                }
            }
            return len;
        }

        @Override
        public int writeVarInt(long value, int len, boolean forceZeroWrite) {
            if (value != 0L) {
                long mask = 127L;
                assert (len == IonBinary.lenInt(value));
                if (value < 0L) {
                    int b;
                    if ((value = -value) == Long.MIN_VALUE) {
                        b = (int)(value >>> 7 * len & mask);
                    } else {
                        b = (byte)(value >>> 7 * len & mask);
                        if (len == 1) {
                            b |= 0x80;
                        }
                    }
                    this.write((byte)(b |= 0x40));
                } else {
                    int b = (int)(value >>> 7 * len & mask);
                    if (len == 1) {
                        b |= 0x80;
                    }
                    this.write((byte)b);
                }
                switch (len - 1) {
                    case 9: {
                        this.write((byte)(value >>> 63 & mask));
                    }
                    case 8: {
                        this.write((byte)(value >> 56 & mask));
                    }
                    case 7: {
                        this.write((byte)(value >> 49 & mask));
                    }
                    case 6: {
                        this.write((byte)(value >> 42 & mask));
                    }
                    case 5: {
                        this.write((byte)(value >> 35 & mask));
                    }
                    case 4: {
                        this.write((byte)(value >> 28 & mask));
                    }
                    case 3: {
                        this.write((byte)(value >> 21 & mask));
                    }
                    case 2: {
                        this.write((byte)(value >> 14 & mask));
                    }
                    case 1: {
                        this.write((byte)(value >> 7 & mask));
                    }
                    case 0: {
                        this.write((byte)(value & mask | 0x80L));
                    }
                }
            } else if (forceZeroWrite) {
                this.write((byte)-128);
                assert (len == 1);
            } else assert (len == 0);
            return len;
        }

        @Override
        public int writeVarUInt(long value, int len, boolean force_zero_write) {
            int mask = 127;
            assert (len == IonBinary.lenVarUInt(value));
            assert (value > 0L);
            switch (len - 1) {
                case 9: {
                    this.write((byte)(value >> 63 & (long)mask));
                }
                case 8: {
                    this.write((byte)(value >> 56 & (long)mask));
                }
                case 7: {
                    this.write((byte)(value >> 49 & (long)mask));
                }
                case 6: {
                    this.write((byte)(value >> 42 & (long)mask));
                }
                case 5: {
                    this.write((byte)(value >> 35 & (long)mask));
                }
                case 4: {
                    this.write((byte)(value >> 28 & (long)mask));
                }
                case 3: {
                    this.write((byte)(value >> 21 & (long)mask));
                }
                case 2: {
                    this.write((byte)(value >> 14 & (long)mask));
                }
                case 1: {
                    this.write((byte)(value >> 7 & (long)mask));
                }
                case 0: {
                    this.write((byte)(value & (long)mask | 0x80L));
                    break;
                }
                case -1: {
                    if (force_zero_write) {
                        this.write((byte)-128);
                        assert (len == 1);
                        break;
                    }
                    assert (len == 0);
                    break;
                }
            }
            return len;
        }

        public int writeULong(long value, int lenToWrite) throws IOException {
            switch (lenToWrite) {
                case 8: {
                    this.write((byte)(value >> 56 & 0xFFL));
                }
                case 7: {
                    this.write((byte)(value >> 48 & 0xFFL));
                }
                case 6: {
                    this.write((byte)(value >> 40 & 0xFFL));
                }
                case 5: {
                    this.write((byte)(value >> 32 & 0xFFL));
                }
                case 4: {
                    this.write((byte)(value >> 24 & 0xFFL));
                }
                case 3: {
                    this.write((byte)(value >> 16 & 0xFFL));
                }
                case 2: {
                    this.write((byte)(value >> 8 & 0xFFL));
                }
                case 1: {
                    this.write((byte)(value >> 0 & 0xFFL));
                }
            }
            return lenToWrite;
        }

        @Override
        public int writeFloat(double value) throws IOException {
            if (Double.valueOf(value).equals(DOUBLE_POS_ZERO)) {
                return 0;
            }
            long dBits = Double.doubleToRawLongBits(value);
            return this.writeULong(dBits, 8);
        }

        @Override
        public int writeDecimal(BigDecimal value) throws IOException {
            int returnlen = this.writeDecimal(value, null);
            return returnlen;
        }

        private int writeDecimal(BigDecimal value, UserByteWriter userWriter) throws IOException {
            int returnlen = 0;
            if (value != null && !BigDecimal.ZERO.equals(value)) {
                boolean needExtraByteForSign;
                boolean isNegative;
                BigInteger mantissa = value.unscaledValue();
                boolean bl = isNegative = mantissa.compareTo(BigInteger.ZERO) < 0;
                if (isNegative) {
                    mantissa = mantissa.negate();
                }
                byte[] bits = mantissa.toByteArray();
                int scale = value.scale();
                int exponent = -scale;
                returnlen = userWriter != null ? (returnlen += userWriter.writeIonInt(exponent, IonBinary.lenVarUInt(exponent))) : (returnlen += this.writeIonInt(exponent, IonBinary.lenVarUInt(exponent)));
                boolean bl2 = needExtraByteForSign = (bits[0] & 0x80) != 0;
                if (needExtraByteForSign) {
                    if (userWriter != null) {
                        userWriter.write((byte)(isNegative ? 128 : 0));
                    } else {
                        this.write((byte)(isNegative ? 128 : 0));
                    }
                    ++returnlen;
                } else if (isNegative) {
                    bits[0] = (byte)(bits[0] | 0x80);
                }
                if (userWriter != null) {
                    userWriter.write(bits, 0, bits.length);
                } else {
                    this.write(bits, 0, bits.length);
                }
                returnlen += bits.length;
            }
            return returnlen;
        }

        public int writeTimestamp(Timestamp di) throws IOException {
            if (di == null) {
                return 0;
            }
            int returnlen = 0;
            Timestamp.Precision precision = di.getPrecision();
            Integer offset = di.getLocalOffset();
            if (offset == null) {
                this.write((byte)-64);
                ++returnlen;
            } else {
                int value = offset;
                returnlen += this.writeVarInt(value, true);
            }
            if (precision.includes(Timestamp.Precision.YEAR)) {
                returnlen += this.writeVarUInt(di.getZYear(), true);
            }
            if (precision.includes(Timestamp.Precision.MONTH)) {
                returnlen += this.writeVarUInt(di.getZMonth(), true);
            }
            if (precision.includes(Timestamp.Precision.DAY)) {
                returnlen += this.writeVarUInt(di.getZDay(), true);
            }
            if (precision.includes(Timestamp.Precision.MINUTE)) {
                returnlen += this.writeVarUInt(di.getZHour(), true);
                returnlen += this.writeVarUInt(di.getZMinute(), true);
            }
            if (precision.includes(Timestamp.Precision.SECOND)) {
                returnlen += this.writeVarUInt(di.getZSecond(), true);
                BigDecimal fraction = di.getZFractionalSecond();
                if (fraction != null) {
                    returnlen += this.writeDecimal(di.getZFractionalSecond());
                }
            }
            return returnlen;
        }

        @Override
        public final int writeString(String value) throws IOException {
            int returnlen = this.writeString(value, null);
            return returnlen;
        }

        private final int writeString(String value, UserByteWriter userWriter) throws IOException {
            int len = 0;
            block0: for (int ii = 0; ii < value.length(); ++ii) {
                int c = value.charAt(ii);
                if (c > 127) {
                    if (c >= 55296 && c <= 57343) {
                        if (_Private_IonConstants.isHighSurrogate(c)) {
                            if (++ii >= value.length()) {
                                throw new IonException("invalid string, unpaired high surrogate character");
                            }
                            char c2 = value.charAt(ii);
                            if (!_Private_IonConstants.isLowSurrogate(c2)) {
                                throw new IonException("invalid string, unpaired high surrogate character");
                            }
                            c = _Private_IonConstants.makeUnicodeScalar(c, c2);
                        } else if (_Private_IonConstants.isLowSurrogate(c)) {
                            throw new IonException("invalid string, unpaired low surrogate character");
                        }
                    }
                    c = IonBinary.makeUTF8IntFromScalar(c);
                }
                if (userWriter == null) {
                    while (true) {
                        this.write((byte)(c & 0xFF));
                        ++len;
                        if ((c & 0xFFFFFF00) == 0) continue block0;
                        c >>>= 8;
                    }
                }
                while (true) {
                    userWriter.write((byte)(c & 0xFF));
                    ++len;
                    if ((c & 0xFFFFFF00) == 0) continue block0;
                    c >>>= 8;
                }
            }
            return len;
        }

        void throwUTF8Exception() throws IOException {
            this.throwException("Invalid UTF-8 character encounter in a string at pos " + this.position());
        }

        void throwException(String msg) throws IOException {
            throw new IOException(msg);
        }
    }

    static final class UserByteWriter
    extends OutputStream
    implements ByteWriter {
        SimpleByteWriter _simple_writer;
        OutputStream _user_stream;
        int _position;
        int _limit;
        int _buffer_size;
        private static final int MAX_UINT7_BINARY_LENGTH = 5;
        private static final int MAX_FLOAT_BINARY_LENGTH = 8;
        private static final int REQUIRED_BUFFER_SPACE = 8;

        UserByteWriter(OutputStream userOuputStream, byte[] buf) {
            if (buf == null || buf.length < 8) {
                throw new IllegalArgumentException("requires a buffer at least 8 bytes long");
            }
            SimpleByteBuffer bytebuffer = new SimpleByteBuffer(buf);
            this._simple_writer = new SimpleByteWriter(bytebuffer);
            this._user_stream = userOuputStream;
            this._limit = this._buffer_size = buf.length;
        }

        private final void checkForSpace(int needed) {
            if (this._position + needed > this._limit) {
                this.flush();
            }
        }

        @Override
        public void flush() {
            if (this._position + this._buffer_size > this._limit) {
                try {
                    this._simple_writer.flushTo(this._user_stream);
                } catch (IOException e) {
                    throw new IonException(e);
                }
                this._limit = this._position + this._buffer_size;
            }
        }

        @Override
        public void insert(int length) {
            throw new UnsupportedOperationException("use a SimpleByteWriter if you need to insert");
        }

        @Override
        public int position() {
            return this._position;
        }

        @Override
        public void position(int newPosition) {
            throw new UnsupportedOperationException("use a SimpleByteWriter if you need to set your position");
        }

        @Override
        public void remove(int length) {
            throw new UnsupportedOperationException("use a SimpleByteWriter if you need to remove bytes");
        }

        @Override
        public void write(int b) throws IOException {
            this.write((byte)b);
        }

        @Override
        public void write(byte b) throws IOException {
            this.checkForSpace(1);
            this._simple_writer.write(b);
            ++this._position;
        }

        @Override
        public int writeDecimal(BigDecimal value) throws IOException {
            int returnlen = this._simple_writer.writeDecimal(value, this);
            return returnlen;
        }

        @Override
        public int writeFloat(double value) throws IOException {
            this.checkForSpace(8);
            int returnlen = this._simple_writer.writeFloat(value);
            return returnlen;
        }

        @Override
        public int writeIonInt(long value, int len) throws IOException {
            this.checkForSpace(len);
            int returnlen = this._simple_writer.writeIonInt(value, len);
            return returnlen;
        }

        @Override
        public int writeIonInt(int value, int len) throws IOException {
            this.checkForSpace(len);
            int returnlen = this._simple_writer.writeIonInt(value, len);
            return returnlen;
        }

        @Override
        public int writeString(String value) throws IOException {
            int returnlen = this._simple_writer.writeString(value, this);
            return returnlen;
        }

        @Override
        public void writeTypeDesc(int typeDescByte) throws IOException {
            this.checkForSpace(1);
            this._simple_writer.writeTypeDesc(typeDescByte);
        }

        @Override
        public int writeTypeDescWithLength(int typeid, int lenOfLength, int valueLength) throws IOException {
            this.checkForSpace(6);
            int returnlen = this._simple_writer.writeTypeDescWithLength(typeid, lenOfLength, valueLength);
            return returnlen;
        }

        @Override
        public int writeTypeDescWithLength(int typeid, int valueLength) throws IOException {
            this.checkForSpace(6);
            int returnlen = this._simple_writer.writeTypeDescWithLength(typeid, valueLength);
            return returnlen;
        }

        @Override
        public int writeVarInt(long value, int len, boolean forceZeroWrite) throws IOException {
            this.checkForSpace(len);
            int returnlen = this._simple_writer.writeVarInt(value, len, forceZeroWrite);
            return returnlen;
        }

        @Override
        public int writeVarInt(int value, int len, boolean forceZeroWrite) throws IOException {
            this.checkForSpace(len);
            int returnlen = this._simple_writer.writeVarInt(value, len, forceZeroWrite);
            return returnlen;
        }

        @Override
        public int writeVarUInt(int value, int len, boolean forceZeroWrite) throws IOException {
            this.checkForSpace(len);
            int returnlen = this._simple_writer.writeVarUInt(value, len, forceZeroWrite);
            return returnlen;
        }

        @Override
        public int writeVarUInt(long value, int len, boolean forceZeroWrite) throws IOException {
            this.checkForSpace(len);
            int returnlen = this._simple_writer.writeVarUInt(value, len, forceZeroWrite);
            return returnlen;
        }
    }

    static final class SimpleByteReader
    implements ByteReader {
        SimpleByteBuffer _buffer;
        int _position;

        SimpleByteReader(SimpleByteBuffer bytebuffer) {
            this._buffer = bytebuffer;
            this._position = bytebuffer._start;
        }

        @Override
        public int position() {
            int pos = this._position - this._buffer._start;
            return pos;
        }

        @Override
        public void position(int newPosition) {
            if (newPosition < 0) {
                throw new IllegalArgumentException("position must be non-negative");
            }
            int pos = newPosition + this._buffer._start;
            if (pos > this._buffer._eob) {
                throw new IllegalArgumentException("position is past end of buffer");
            }
            this._position = pos;
        }

        @Override
        public void skip(int length) {
            if (length < 0) {
                throw new IllegalArgumentException("length to skip must be non-negative");
            }
            int pos = this._position + length;
            if (pos > this._buffer._eob) {
                throw new IllegalArgumentException("skip would skip past end of buffer");
            }
            this._position = pos;
        }

        @Override
        public int read() {
            if (this._position >= this._buffer._eob) {
                return -1;
            }
            byte b = this._buffer._bytes[this._position++];
            return b & 0xFF;
        }

        @Override
        public int read(byte[] dst, int start, int len) {
            if (dst == null || start < 0 || len < 0 || start + len > dst.length) {
                throw new IllegalArgumentException();
            }
            if (this._position >= this._buffer._eob) {
                return 0;
            }
            int readlen = len;
            if (readlen + this._position > this._buffer._eob) {
                readlen = this._buffer._eob - this._position;
            }
            System.arraycopy(this._buffer._bytes, this._position, dst, start, readlen);
            this._position += readlen;
            return readlen;
        }

        @Override
        public int readTypeDesc() {
            return this.read();
        }

        @Override
        public long readULong(int len) throws IOException {
            long retvalue = 0L;
            switch (len) {
                default: {
                    throw new IonException("value too large for Java long");
                }
                case 8: {
                    int b = this.read();
                    if (b < 0) {
                        this.throwUnexpectedEOFException();
                    }
                    retvalue = retvalue << 8 | (long)b;
                }
                case 7: {
                    int b = this.read();
                    if (b < 0) {
                        this.throwUnexpectedEOFException();
                    }
                    retvalue = retvalue << 8 | (long)b;
                }
                case 6: {
                    int b = this.read();
                    if (b < 0) {
                        this.throwUnexpectedEOFException();
                    }
                    retvalue = retvalue << 8 | (long)b;
                }
                case 5: {
                    int b = this.read();
                    if (b < 0) {
                        this.throwUnexpectedEOFException();
                    }
                    retvalue = retvalue << 8 | (long)b;
                }
                case 4: {
                    int b = this.read();
                    if (b < 0) {
                        this.throwUnexpectedEOFException();
                    }
                    retvalue = retvalue << 8 | (long)b;
                }
                case 3: {
                    int b = this.read();
                    if (b < 0) {
                        this.throwUnexpectedEOFException();
                    }
                    retvalue = retvalue << 8 | (long)b;
                }
                case 2: {
                    int b = this.read();
                    if (b < 0) {
                        this.throwUnexpectedEOFException();
                    }
                    retvalue = retvalue << 8 | (long)b;
                }
                case 1: {
                    int b = this.read();
                    if (b < 0) {
                        this.throwUnexpectedEOFException();
                    }
                    retvalue = retvalue << 8 | (long)b;
                }
                case 0: 
            }
            return retvalue;
        }

        @Override
        public int readVarInt() throws IOException {
            int retvalue = 0;
            boolean is_negative = false;
            while (true) {
                int b;
                if ((b = this.read()) < 0) {
                    this.throwUnexpectedEOFException();
                }
                if ((b & 0x40) != 0) {
                    is_negative = true;
                }
                retvalue = b & 0x3F;
                if ((b & 0x80) != 0) break;
                b = this.read();
                if (b < 0) {
                    this.throwUnexpectedEOFException();
                }
                retvalue = retvalue << 7 | b & 0x7F;
                if ((b & 0x80) != 0) break;
                b = this.read();
                if (b < 0) {
                    this.throwUnexpectedEOFException();
                }
                retvalue = retvalue << 7 | b & 0x7F;
                if ((b & 0x80) != 0) break;
                b = this.read();
                if (b < 0) {
                    this.throwUnexpectedEOFException();
                }
                retvalue = retvalue << 7 | b & 0x7F;
                if ((b & 0x80) != 0) break;
                b = this.read();
                if (b < 0) {
                    this.throwUnexpectedEOFException();
                }
                retvalue = retvalue << 7 | b & 0x7F;
                if ((b & 0x80) != 0) break;
                this.throwIntOverflowExeption();
            }
            if (is_negative) {
                retvalue = -retvalue;
            }
            return retvalue;
        }

        @Override
        public long readVarLong() throws IOException {
            long retvalue = 0L;
            boolean is_negative = false;
            int b = this.read();
            if (b < 0) {
                this.throwUnexpectedEOFException();
            }
            if ((b & 0x40) != 0) {
                is_negative = true;
            }
            retvalue = b & 0x3F;
            if ((b & 0x80) == 0) {
                b = this.read();
                if (b < 0) {
                    this.throwUnexpectedEOFException();
                }
                retvalue = retvalue << 7 | (long)(b & 0x7F);
                if ((b & 0x80) == 0) {
                    do {
                        if ((b = this.read()) < 0) {
                            this.throwUnexpectedEOFException();
                        }
                        if ((retvalue & 0xFE00000000000000L) != 0L) {
                            this.throwIntOverflowExeption();
                        }
                        retvalue = retvalue << 7 | (long)(b & 0x7F);
                    } while ((b & 0x80) == 0);
                }
            }
            if (is_negative) {
                retvalue = -retvalue;
            }
            return retvalue;
        }

        public Integer readVarInteger() throws IOException {
            int retvalue = 0;
            boolean is_negative = false;
            while (true) {
                int b;
                if ((b = this.read()) < 0) {
                    this.throwUnexpectedEOFException();
                }
                if ((b & 0x40) != 0) {
                    is_negative = true;
                }
                retvalue = b & 0x3F;
                if ((b & 0x80) != 0) break;
                b = this.read();
                if (b < 0) {
                    this.throwUnexpectedEOFException();
                }
                retvalue = retvalue << 7 | b & 0x7F;
                if ((b & 0x80) != 0) break;
                b = this.read();
                if (b < 0) {
                    this.throwUnexpectedEOFException();
                }
                retvalue = retvalue << 7 | b & 0x7F;
                if ((b & 0x80) != 0) break;
                b = this.read();
                if (b < 0) {
                    this.throwUnexpectedEOFException();
                }
                retvalue = retvalue << 7 | b & 0x7F;
                if ((b & 0x80) != 0) break;
                b = this.read();
                if (b < 0) {
                    this.throwUnexpectedEOFException();
                }
                retvalue = retvalue << 7 | b & 0x7F;
                if ((b & 0x80) != 0) break;
                this.throwIntOverflowExeption();
            }
            Integer retInteger = null;
            if (is_negative) {
                if (retvalue != 0) {
                    retInteger = -retvalue;
                }
            } else {
                retInteger = retvalue;
            }
            return retInteger;
        }

        @Override
        public int readVarUInt() throws IOException {
            int retvalue = 0;
            while (true) {
                int b;
                if ((b = this.read()) < 0) {
                    this.throwUnexpectedEOFException();
                }
                retvalue = retvalue << 7 | b & 0x7F;
                if ((b & 0x80) != 0) break;
                b = this.read();
                if (b < 0) {
                    this.throwUnexpectedEOFException();
                }
                retvalue = retvalue << 7 | b & 0x7F;
                if ((b & 0x80) != 0) break;
                b = this.read();
                if (b < 0) {
                    this.throwUnexpectedEOFException();
                }
                retvalue = retvalue << 7 | b & 0x7F;
                if ((b & 0x80) != 0) break;
                b = this.read();
                if (b < 0) {
                    this.throwUnexpectedEOFException();
                }
                retvalue = retvalue << 7 | b & 0x7F;
                if ((b & 0x80) != 0) break;
                b = this.read();
                if (b < 0) {
                    this.throwUnexpectedEOFException();
                }
                retvalue = retvalue << 7 | b & 0x7F;
                if ((b & 0x80) != 0) break;
                this.throwIntOverflowExeption();
            }
            return retvalue;
        }

        @Override
        public double readFloat(int len) throws IOException {
            if (len == 0) {
                return 0.0;
            }
            if (len != 8) {
                throw new IOException("Length of float read must be 0 or 8");
            }
            long dBits = this.readULong(len);
            return Double.longBitsToDouble(dBits);
        }

        @Override
        public long readVarULong() throws IOException {
            int b;
            long retvalue = 0L;
            do {
                if ((b = this.read()) < 0) {
                    this.throwUnexpectedEOFException();
                }
                if ((retvalue & 0xFE00000000000000L) != 0L) {
                    this.throwIntOverflowExeption();
                }
                retvalue = retvalue << 7 | (long)(b & 0x7F);
            } while ((b & 0x80) == 0);
            return retvalue;
        }

        @Override
        public Decimal readDecimal(int len) throws IOException {
            Decimal bd;
            MathContext mathContext = MathContext.UNLIMITED;
            if (len == 0) {
                bd = Decimal.valueOf(0, mathContext);
            } else {
                BigInteger value;
                int signum;
                int startpos = this.position();
                int exponent = this.readVarInt();
                int bitlen = len - (this.position() - startpos);
                if (bitlen > 0) {
                    byte[] bits = new byte[bitlen];
                    this.read(bits, 0, bitlen);
                    signum = 1;
                    if (bits[0] < 0) {
                        bits[0] = (byte)(bits[0] & 0x7F);
                        signum = -1;
                    }
                    value = new BigInteger(signum, bits);
                } else {
                    signum = 0;
                    value = BigInteger.ZERO;
                }
                int scale = -exponent;
                if (value.signum() == 0 && signum == -1) {
                    assert (value.equals(BigInteger.ZERO));
                    bd = Decimal.negativeZero(scale, mathContext);
                } else {
                    bd = Decimal.valueOf(value, scale, mathContext);
                }
            }
            return bd;
        }

        @Override
        public Timestamp readTimestamp(int len) throws IOException {
            if (len < 1) {
                return null;
            }
            Timestamp.Precision p = null;
            Integer offset = null;
            int year2 = 0;
            int month = 0;
            int day = 0;
            int hour = 0;
            int minute = 0;
            int second = 0;
            Decimal frac = null;
            int end = this.position() + len;
            offset = this.readVarInteger();
            assert (this.position() < end);
            if (this.position() < end) {
                year2 = this.readVarUInt();
                p = Timestamp.Precision.YEAR;
                if (this.position() < end) {
                    month = this.readVarUInt();
                    p = Timestamp.Precision.MONTH;
                    if (this.position() < end) {
                        day = this.readVarUInt();
                        p = Timestamp.Precision.DAY;
                        if (this.position() < end) {
                            hour = this.readVarUInt();
                            minute = this.readVarUInt();
                            p = Timestamp.Precision.MINUTE;
                            if (this.position() < end) {
                                second = this.readVarUInt();
                                p = Timestamp.Precision.SECOND;
                                int remaining = end - this.position();
                                if (remaining > 0) {
                                    frac = this.readDecimal(remaining);
                                }
                            }
                        }
                    }
                }
            }
            Timestamp val = Timestamp.createFromUtcFields(p, year2, month, day, hour, minute, second, frac, offset);
            return val;
        }

        @Override
        public String readString(int len) throws IOException {
            char[] chars = new char[len];
            int ii = 0;
            int endPosition = this.position() + len;
            while (this.position() < endPosition) {
                int c = this.readUnicodeScalar();
                if (c < 0) {
                    this.throwUnexpectedEOFException();
                }
                if (c < 65536) {
                    chars[ii++] = (char)c;
                    continue;
                }
                chars[ii++] = (char)_Private_IonConstants.makeHighSurrogate(c);
                chars[ii++] = (char)_Private_IonConstants.makeLowSurrogate(c);
            }
            if (this.position() < endPosition) {
                this.throwUnexpectedEOFException();
            }
            return new String(chars, 0, ii);
        }

        public final int readUnicodeScalar() throws IOException {
            int b = this.read();
            if (b >= 128) {
                b = this.readUnicodeScalar_helper(b);
            }
            return b;
        }

        private final int readUnicodeScalar_helper(int b) throws IOException {
            int c = -1;
            if ((b & 0xE0) == 192) {
                c = b & 0xFFFFFF1F;
                b = this.read();
                if ((b & 0xC0) != 128) {
                    this.throwUTF8Exception();
                }
                c <<= 6;
                c |= b & 0xFFFFFF7F;
            } else if ((b & 0xF0) == 224) {
                c = b & 0xFFFFFF0F;
                b = this.read();
                if ((b & 0xC0) != 128) {
                    this.throwUTF8Exception();
                }
                c <<= 6;
                c |= b & 0xFFFFFF7F;
                b = this.read();
                if ((b & 0xC0) != 128) {
                    this.throwUTF8Exception();
                }
                c <<= 6;
                if ((c |= b & 0xFFFFFF7F) > 55295 && c < 57344) {
                    throw new IonException("illegal surrogate value encountered in input utf-8 stream");
                }
            } else if ((b & 0xF8) == 240) {
                c = b & 0xFFFFFF07;
                b = this.read();
                if ((b & 0xC0) != 128) {
                    this.throwUTF8Exception();
                }
                c <<= 6;
                c |= b & 0xFFFFFF7F;
                b = this.read();
                if ((b & 0xC0) != 128) {
                    this.throwUTF8Exception();
                }
                c <<= 6;
                c |= b & 0xFFFFFF7F;
                b = this.read();
                if ((b & 0xC0) != 128) {
                    this.throwUTF8Exception();
                }
                c <<= 6;
                if ((c |= b & 0xFFFFFF7F) > 0x10FFFF) {
                    throw new IonException("illegal utf value encountered in input utf-8 stream");
                }
            } else {
                this.throwUTF8Exception();
            }
            return c;
        }

        void throwUTF8Exception() throws IOException {
            throw new IOException("Invalid UTF-8 character encounter in a string at pos " + this.position());
        }

        void throwUnexpectedEOFException() throws IOException {
            throw new IOException("unexpected EOF in value at offset " + this.position());
        }

        void throwIntOverflowExeption() throws IOException {
            throw new IOException("int in stream is too long for a Java int 32 use readLong()");
        }
    }
}

