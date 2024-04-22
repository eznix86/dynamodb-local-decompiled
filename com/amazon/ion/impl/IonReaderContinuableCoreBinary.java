/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazon.ion.impl;

import com.amazon.ion.Decimal;
import com.amazon.ion.IntegerSize;
import com.amazon.ion.IonBufferConfiguration;
import com.amazon.ion.IonCursor;
import com.amazon.ion.IonException;
import com.amazon.ion.IonType;
import com.amazon.ion.Timestamp;
import com.amazon.ion.impl.IonCursorBinary;
import com.amazon.ion.impl.IonReaderContinuableCore;
import com.amazon.ion.impl.Marker;
import com.amazon.ion.impl._Private_ScalarConversions;
import com.amazon.ion.impl.bin.IntList;
import com.amazon.ion.impl.bin.utf8.Utf8StringDecoder;
import com.amazon.ion.impl.bin.utf8.Utf8StringDecoderPool;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.util.Date;

class IonReaderContinuableCoreBinary
extends IonCursorBinary
implements IonReaderContinuableCore {
    private static final int HIGHEST_BIT_BITMASK = 128;
    private static final int LOWER_SEVEN_BITS_BITMASK = 127;
    private static final int SINGLE_BYTE_MASK = 255;
    private static final int LOWER_SIX_BITS_BITMASK = 63;
    private static final int VALUE_BITS_PER_UINT_BYTE = 8;
    private static final int VALUE_BITS_PER_VARUINT_BYTE = 7;
    private static final int VAR_INT_NEGATIVE_ZERO = 192;
    private static final int INT_SIZE_IN_BYTES = 4;
    private static final int LONG_SIZE_IN_BYTES = 8;
    private static final int MOST_SIGNIFICANT_BYTE_OF_MIN_INTEGER = 128;
    private static final int MOST_SIGNIFICANT_BYTE_OF_MAX_INTEGER = 127;
    private static final int VAR_INT_SIGN_BITMASK = 64;
    private static final int FLOAT_32_BYTE_LENGTH = 4;
    private static final int ANNOTATIONS_LIST_INITIAL_CAPACITY = 8;
    private final _Private_ScalarConversions.ValueVariant scalarConverter;
    final Utf8StringDecoder utf8Decoder = (Utf8StringDecoder)Utf8StringDecoderPool.getInstance().getOrCreate();
    long peekIndex = -1L;
    private int lobBytesRead = 0;
    private final IntList annotationSids;
    private final byte[][] scratchForSize = new byte[][]{new byte[0], new byte[1], new byte[2], new byte[3], new byte[4], new byte[5], new byte[6], new byte[7], new byte[8], new byte[9], new byte[10], new byte[11], new byte[12]};

    IonReaderContinuableCoreBinary(IonBufferConfiguration configuration, byte[] bytes, int offset, int length) {
        super(configuration, bytes, offset, length);
        this.scalarConverter = new _Private_ScalarConversions.ValueVariant();
        this.annotationSids = new IntList(8);
    }

    IonReaderContinuableCoreBinary(IonBufferConfiguration configuration, InputStream inputStream, byte[] alreadyRead, int alreadyReadOff, int alreadyReadLen) {
        super(configuration, inputStream, alreadyRead, alreadyReadOff, alreadyReadLen);
        this.scalarConverter = new _Private_ScalarConversions.ValueVariant();
        this.annotationSids = new IntList(8);
    }

    private byte[] copyBytesToScratch(long startIndex, int length) {
        byte[] bytes = null;
        if (length < this.scratchForSize.length) {
            bytes = this.scratchForSize[length];
        }
        if (bytes == null) {
            bytes = new byte[length];
        }
        System.arraycopy(this.buffer, (int)startIndex, bytes, 0, bytes.length);
        return bytes;
    }

    int readVarUInt_1_0() {
        int currentByte = 0;
        int result = 0;
        while ((currentByte & 0x80) == 0) {
            if (this.peekIndex >= this.limit) {
                throw new IonException("Malformed data: declared length exceeds the number of bytes remaining in the stream.");
            }
            currentByte = this.buffer[(int)this.peekIndex++];
            result = result << 7 | currentByte & 0x7F;
        }
        return result;
    }

    private int readVarInt_1_0(int firstByte) {
        int currentByte = firstByte;
        int sign = (currentByte & 0x40) == 0 ? 1 : -1;
        int result = currentByte & 0x3F;
        while ((currentByte & 0x80) == 0) {
            if (this.peekIndex >= this.limit) {
                throw new IonException("Malformed data: declared length exceeds the number of bytes remaining in the stream.");
            }
            currentByte = this.buffer[(int)this.peekIndex++];
            result = result << 7 | currentByte & 0x7F;
        }
        return result * sign;
    }

    private int readVarInt_1_0() {
        return this.readVarInt_1_0(this.buffer[(int)this.peekIndex++]);
    }

    private BigInteger readUIntAsBigInteger(boolean isNegative) {
        int length = (int)(this.valueMarker.endIndex - this.valueMarker.startIndex);
        byte[] magnitude = this.copyBytesToScratch(this.valueMarker.startIndex, length);
        int signum = isNegative ? -1 : 1;
        return new BigInteger(signum, magnitude);
    }

    private int getAndClearSignBit_1_0(byte[] intBytes) {
        int signum;
        boolean isNegative = (intBytes[0] & 0x80) != 0;
        int n = signum = isNegative ? -1 : 1;
        if (isNegative) {
            intBytes[0] = (byte)(intBytes[0] & 0x7F);
        }
        return signum;
    }

    private BigInteger readIntAsBigInteger_1_0(int length) {
        BigInteger value;
        if (length > 0) {
            byte[] bytes = this.copyBytesToScratch(this.peekIndex, length);
            value = new BigInteger(this.getAndClearSignBit_1_0(bytes), bytes);
        } else {
            value = BigInteger.ZERO;
        }
        return value;
    }

    private BigDecimal readBigDecimal_1_0() {
        BigDecimal value;
        int scale = -this.readVarInt_1_0();
        int length = (int)(this.valueMarker.endIndex - this.peekIndex);
        if (length < 8) {
            long coefficient = 0L;
            int sign = 1;
            if (this.peekIndex < this.valueMarker.endIndex) {
                int firstByte;
                sign = ((firstByte = this.buffer[(int)this.peekIndex++] & 0xFF) & 0x80) == 0 ? 1 : -1;
                coefficient = firstByte & 0x7F;
            }
            while (this.peekIndex < this.valueMarker.endIndex) {
                coefficient = coefficient << 8 | (long)(this.buffer[(int)this.peekIndex++] & 0xFF);
            }
            value = BigDecimal.valueOf(coefficient * (long)sign, scale);
        } else {
            value = new BigDecimal(this.readIntAsBigInteger_1_0(length), scale);
        }
        return value;
    }

    private Decimal readDecimal_1_0() {
        BigInteger coefficient;
        int scale = -this.readVarInt_1_0();
        int length = (int)(this.valueMarker.endIndex - this.peekIndex);
        if (length > 0) {
            byte[] bits = this.copyBytesToScratch(this.peekIndex, length);
            int signum = this.getAndClearSignBit_1_0(bits);
            coefficient = new BigInteger(signum, bits);
            if (coefficient.signum() == 0 && signum < 0) {
                return Decimal.negativeZero(scale);
            }
        } else {
            coefficient = BigInteger.ZERO;
        }
        return Decimal.valueOf(coefficient, scale);
    }

    private long readLong_1_0() {
        long value = this.readUInt(this.valueMarker.startIndex, this.valueMarker.endIndex);
        if (this.valueTid.isNegativeInt) {
            if (value == 0L) {
                throw new IonException("Int zero may not be negative.");
            }
            value *= -1L;
        }
        return value;
    }

    private BigInteger readBigInteger_1_0() {
        BigInteger value = this.readUIntAsBigInteger(this.valueTid.isNegativeInt);
        if (this.valueTid.isNegativeInt && value.signum() == 0) {
            throw new IonException("Int zero may not be negative.");
        }
        return value;
    }

    private Timestamp readTimestamp_1_0() {
        int firstByte = this.buffer[(int)this.peekIndex++] & 0xFF;
        Integer offset = null;
        if (firstByte != 192) {
            offset = this.readVarInt_1_0(firstByte);
        }
        int year2 = this.readVarUInt_1_0();
        int month = 0;
        int day = 0;
        int hour = 0;
        int minute = 0;
        int second = 0;
        BigDecimal fractionalSecond = null;
        Timestamp.Precision precision = Timestamp.Precision.YEAR;
        if (this.peekIndex < this.valueMarker.endIndex) {
            month = this.readVarUInt_1_0();
            precision = Timestamp.Precision.MONTH;
            if (this.peekIndex < this.valueMarker.endIndex) {
                day = this.readVarUInt_1_0();
                precision = Timestamp.Precision.DAY;
                if (this.peekIndex < this.valueMarker.endIndex) {
                    hour = this.readVarUInt_1_0();
                    if (this.peekIndex >= this.valueMarker.endIndex) {
                        throw new IonException("Timestamps may not specify hour without specifying minute.");
                    }
                    minute = this.readVarUInt_1_0();
                    precision = Timestamp.Precision.MINUTE;
                    if (this.peekIndex < this.valueMarker.endIndex) {
                        second = this.readVarUInt_1_0();
                        precision = Timestamp.Precision.SECOND;
                        if (this.peekIndex < this.valueMarker.endIndex) {
                            fractionalSecond = this.readBigDecimal_1_0();
                        }
                    }
                }
            }
        }
        try {
            return Timestamp.createFromUtcFields(precision, year2, month, day, hour, minute, second, fractionalSecond, offset);
        } catch (IllegalArgumentException e) {
            throw new IonException("Illegal timestamp encoding. ", e);
        }
    }

    private boolean readBoolean_1_0() {
        return this.valueTid.lowerNibble == 1;
    }

    private boolean classifyInteger_1_0() {
        if (this.valueTid.isNegativeInt) {
            int firstByte = this.buffer[(int)this.valueMarker.startIndex] & 0xFF;
            if (firstByte < 128) {
                return true;
            }
            if (firstByte > 128) {
                return false;
            }
            for (long i = this.valueMarker.startIndex + 1L; i < this.valueMarker.endIndex; ++i) {
                if (0 == this.buffer[(int)i]) continue;
                return false;
            }
            return true;
        }
        return (this.buffer[(int)this.valueMarker.startIndex] & 0xFF) <= 127;
    }

    int readVarUInt_1_1() {
        throw new UnsupportedOperationException();
    }

    private int readVarSym_1_1(Marker marker) {
        throw new UnsupportedOperationException();
    }

    private BigDecimal readBigDecimal_1_1() {
        throw new UnsupportedOperationException();
    }

    private Decimal readDecimal_1_1() {
        throw new UnsupportedOperationException();
    }

    private long readLong_1_1() {
        throw new UnsupportedOperationException();
    }

    private BigInteger readBigInteger_1_1() {
        throw new UnsupportedOperationException();
    }

    private Timestamp readTimestamp_1_1() {
        throw new UnsupportedOperationException();
    }

    private boolean readBoolean_1_1() {
        throw new UnsupportedOperationException();
    }

    @Override
    public IonCursor.Event nextValue() {
        this.lobBytesRead = 0;
        return super.nextValue();
    }

    ByteBuffer prepareByteBuffer(long startIndex, long endIndex) {
        this.byteBuffer.limit(this.buffer.length);
        this.byteBuffer.position((int)startIndex);
        this.byteBuffer.limit((int)endIndex);
        return this.byteBuffer;
    }

    private long readUInt(long startIndex, long endIndex) {
        long result = 0L;
        for (long i = startIndex; i < endIndex; ++i) {
            result = result << 8 | (long)(this.buffer[(int)i] & 0xFF);
        }
        return result;
    }

    @Override
    public boolean isNullValue() {
        return this.valueTid != null && this.valueTid.isNull;
    }

    void prepareScalar() {
        if (this.valueMarker.endIndex > this.limit) {
            throw new IonException("Malformed data: declared length exceeds the number of bytes remaining in the stream.");
        }
    }

    @Override
    public IntegerSize getIntegerSize() {
        if (this.valueTid == null || this.valueTid.type != IonType.INT || this.valueTid.isNull) {
            return null;
        }
        this.prepareScalar();
        if (this.valueTid.length < 0) {
            return IntegerSize.BIG_INTEGER;
        }
        if (this.valueTid.length < 4) {
            return IntegerSize.INT;
        }
        if (this.valueTid.length == 4) {
            return this.minorVersion != 0 || this.classifyInteger_1_0() ? IntegerSize.INT : IntegerSize.LONG;
        }
        if (this.valueTid.length < 8) {
            return IntegerSize.LONG;
        }
        if (this.valueTid.length == 8) {
            return this.minorVersion != 0 || this.classifyInteger_1_0() ? IntegerSize.LONG : IntegerSize.BIG_INTEGER;
        }
        return IntegerSize.BIG_INTEGER;
    }

    private void throwDueToInvalidType(IonType type) {
        throw new IllegalStateException(String.format("Invalid type. Required %s but found %s.", new Object[]{type, this.valueTid == null ? null : this.valueTid.type}));
    }

    @Override
    public int byteSize() {
        if (this.valueTid == null || !IonType.isLob(this.valueTid.type) || this.valueTid.isNull) {
            throw new IonException("Reader must be positioned on a blob or clob.");
        }
        this.prepareScalar();
        return (int)(this.valueMarker.endIndex - this.valueMarker.startIndex);
    }

    @Override
    public byte[] newBytes() {
        byte[] bytes = new byte[this.byteSize()];
        System.arraycopy(this.buffer, (int)this.valueMarker.startIndex, bytes, 0, bytes.length);
        return bytes;
    }

    @Override
    public int getBytes(byte[] bytes, int offset, int len) {
        int length = Math.min(len, this.byteSize() - this.lobBytesRead);
        System.arraycopy(this.buffer, (int)(this.valueMarker.startIndex + (long)this.lobBytesRead), bytes, offset, length);
        this.lobBytesRead += length;
        return length;
    }

    private void prepareToConvertIntValue() {
        if (this.getIntegerSize() == IntegerSize.BIG_INTEGER) {
            this.scalarConverter.addValue(this.bigIntegerValue());
            this.scalarConverter.setAuthoritativeType(5);
        } else {
            this.scalarConverter.addValue(this.longValue());
            this.scalarConverter.setAuthoritativeType(4);
        }
    }

    @Override
    public BigDecimal bigDecimalValue() {
        BigDecimal value = null;
        if (this.valueTid.type == IonType.DECIMAL) {
            if (this.valueTid.isNull) {
                return null;
            }
            this.prepareScalar();
            this.peekIndex = this.valueMarker.startIndex;
            value = this.peekIndex >= this.valueMarker.endIndex ? BigDecimal.ZERO : (this.minorVersion == 0 ? this.readBigDecimal_1_0() : this.readBigDecimal_1_1());
        } else if (this.valueTid.type == IonType.INT) {
            if (this.valueTid.isNull) {
                return null;
            }
            this.prepareToConvertIntValue();
            this.scalarConverter.cast(this.scalarConverter.get_conversion_fnid(6));
            value = this.scalarConverter.getBigDecimal();
            this.scalarConverter.clear();
        } else {
            this.throwDueToInvalidType(IonType.DECIMAL);
        }
        return value;
    }

    @Override
    public Decimal decimalValue() {
        Decimal value = null;
        if (this.valueTid.type == IonType.DECIMAL) {
            if (this.valueTid.isNull) {
                return null;
            }
            this.prepareScalar();
            this.peekIndex = this.valueMarker.startIndex;
            value = this.peekIndex >= this.valueMarker.endIndex ? Decimal.ZERO : (this.minorVersion == 0 ? this.readDecimal_1_0() : this.readDecimal_1_1());
        } else if (this.valueTid.type == IonType.INT) {
            if (this.valueTid.isNull) {
                return null;
            }
            this.prepareToConvertIntValue();
            this.scalarConverter.cast(this.scalarConverter.get_conversion_fnid(6));
            value = this.scalarConverter.getDecimal();
            this.scalarConverter.clear();
        } else {
            this.throwDueToInvalidType(IonType.DECIMAL);
        }
        return value;
    }

    @Override
    public long longValue() {
        long value;
        if (this.valueTid.isNull) {
            this.throwDueToInvalidType(IonType.INT);
        }
        if (this.valueTid.type == IonType.INT) {
            if (this.valueTid.length == 0) {
                return 0L;
            }
            this.prepareScalar();
            value = this.minorVersion == 0 ? this.readLong_1_0() : this.readLong_1_1();
        } else if (this.valueTid.type == IonType.FLOAT) {
            this.scalarConverter.addValue(this.doubleValue());
            this.scalarConverter.setAuthoritativeType(7);
            this.scalarConverter.cast(this.scalarConverter.get_conversion_fnid(4));
            value = this.scalarConverter.getLong();
            this.scalarConverter.clear();
        } else if (this.valueTid.type == IonType.DECIMAL) {
            this.scalarConverter.addValue(this.decimalValue());
            this.scalarConverter.setAuthoritativeType(6);
            this.scalarConverter.cast(this.scalarConverter.get_conversion_fnid(4));
            value = this.scalarConverter.getLong();
            this.scalarConverter.clear();
        } else {
            throw new IllegalStateException("longValue() may only be called on non-null values of type int, float, or decimal.");
        }
        return value;
    }

    @Override
    public BigInteger bigIntegerValue() {
        BigInteger value;
        if (this.valueTid.type == IonType.INT) {
            if (this.valueTid.isNull) {
                return null;
            }
            if (this.valueTid.length == 0) {
                return BigInteger.ZERO;
            }
            this.prepareScalar();
            value = this.minorVersion == 0 ? this.readBigInteger_1_0() : this.readBigInteger_1_1();
        } else if (this.valueTid.type == IonType.FLOAT) {
            if (this.valueTid.isNull) {
                value = null;
            } else {
                this.scalarConverter.addValue(this.doubleValue());
                this.scalarConverter.setAuthoritativeType(7);
                this.scalarConverter.cast(this.scalarConverter.get_conversion_fnid(5));
                value = this.scalarConverter.getBigInteger();
                this.scalarConverter.clear();
            }
        } else {
            throw new IllegalStateException("longValue() may only be called on values of type int or float.");
        }
        return value;
    }

    @Override
    public int intValue() {
        return (int)this.longValue();
    }

    @Override
    public double doubleValue() {
        double value;
        if (this.valueTid.isNull) {
            this.throwDueToInvalidType(IonType.FLOAT);
        }
        if (this.valueTid.type == IonType.FLOAT) {
            this.prepareScalar();
            int length = (int)(this.valueMarker.endIndex - this.valueMarker.startIndex);
            if (length == 0) {
                return 0.0;
            }
            ByteBuffer bytes = this.prepareByteBuffer(this.valueMarker.startIndex, this.valueMarker.endIndex);
            value = length == 4 ? (double)bytes.getFloat() : bytes.getDouble();
        } else if (this.valueTid.type == IonType.DECIMAL) {
            this.scalarConverter.addValue(this.decimalValue());
            this.scalarConverter.setAuthoritativeType(6);
            this.scalarConverter.cast(this.scalarConverter.get_conversion_fnid(7));
            value = this.scalarConverter.getDouble();
            this.scalarConverter.clear();
        } else if (this.valueTid.type == IonType.INT) {
            this.prepareToConvertIntValue();
            this.scalarConverter.cast(this.scalarConverter.get_conversion_fnid(7));
            value = this.scalarConverter.getDouble();
            this.scalarConverter.clear();
        } else {
            throw new IllegalStateException("doubleValue() may only be called on non-null values of type float or decimal.");
        }
        return value;
    }

    @Override
    public Timestamp timestampValue() {
        if (this.valueTid == null || IonType.TIMESTAMP != this.valueTid.type) {
            this.throwDueToInvalidType(IonType.TIMESTAMP);
        }
        if (this.valueTid.isNull) {
            return null;
        }
        this.prepareScalar();
        this.peekIndex = this.valueMarker.startIndex;
        return this.minorVersion == 0 ? this.readTimestamp_1_0() : this.readTimestamp_1_1();
    }

    @Override
    public Date dateValue() {
        Timestamp timestamp = this.timestampValue();
        if (timestamp == null) {
            return null;
        }
        return timestamp.dateValue();
    }

    @Override
    public boolean booleanValue() {
        if (this.valueTid == null || IonType.BOOL != this.valueTid.type || this.valueTid.isNull) {
            this.throwDueToInvalidType(IonType.BOOL);
        }
        this.prepareScalar();
        return this.minorVersion == 0 ? this.readBoolean_1_0() : this.readBoolean_1_1();
    }

    @Override
    public String stringValue() {
        if (this.valueTid == null || IonType.STRING != this.valueTid.type) {
            this.throwDueToInvalidType(IonType.STRING);
        }
        if (this.valueTid.isNull) {
            return null;
        }
        this.prepareScalar();
        ByteBuffer utf8InputBuffer = this.prepareByteBuffer(this.valueMarker.startIndex, this.valueMarker.endIndex);
        return this.utf8Decoder.decode(utf8InputBuffer, (int)(this.valueMarker.endIndex - this.valueMarker.startIndex));
    }

    @Override
    public int symbolValueId() {
        if (this.valueTid == null || IonType.SYMBOL != this.valueTid.type) {
            this.throwDueToInvalidType(IonType.SYMBOL);
        }
        if (this.valueTid.isNull) {
            return -1;
        }
        this.prepareScalar();
        return (int)this.readUInt(this.valueMarker.startIndex, this.valueMarker.endIndex);
    }

    IntList getAnnotationSidList() {
        this.annotationSids.clear();
        long savedPeekIndex = this.peekIndex;
        this.peekIndex = this.annotationSequenceMarker.startIndex;
        if (this.minorVersion == 0) {
            while (this.peekIndex < this.annotationSequenceMarker.endIndex) {
                this.annotationSids.add(this.readVarUInt_1_0());
            }
        } else {
            while (this.peekIndex < this.annotationSequenceMarker.endIndex) {
                this.annotationSids.add(this.readVarUInt_1_1());
            }
        }
        this.peekIndex = savedPeekIndex;
        return this.annotationSids;
    }

    @Override
    public int[] getAnnotationIds() {
        this.getAnnotationSidList();
        int[] annotationArray = new int[this.annotationSids.size()];
        for (int i = 0; i < annotationArray.length; ++i) {
            annotationArray[i] = this.annotationSids.get(i);
        }
        return annotationArray;
    }

    @Override
    public int getFieldId() {
        return this.fieldSid;
    }

    @Override
    public boolean isInStruct() {
        return this.parent != null && this.parent.typeId.type == IonType.STRUCT;
    }

    @Override
    public IonType getType() {
        return this.valueTid == null ? null : this.valueTid.type;
    }

    @Override
    public int getDepth() {
        return this.containerIndex + 1;
    }

    @Override
    public void close() {
        this.utf8Decoder.close();
        super.close();
    }
}

