/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazon.ion.impl.lite;

import com.amazon.ion.Decimal;
import com.amazon.ion.IonBlob;
import com.amazon.ion.IonBool;
import com.amazon.ion.IonClob;
import com.amazon.ion.IonDatagram;
import com.amazon.ion.IonDecimal;
import com.amazon.ion.IonException;
import com.amazon.ion.IonFloat;
import com.amazon.ion.IonInt;
import com.amazon.ion.IonList;
import com.amazon.ion.IonSequence;
import com.amazon.ion.IonSexp;
import com.amazon.ion.IonString;
import com.amazon.ion.IonStruct;
import com.amazon.ion.IonSymbol;
import com.amazon.ion.IonSystem;
import com.amazon.ion.IonTimestamp;
import com.amazon.ion.IonValue;
import com.amazon.ion.SymbolTable;
import com.amazon.ion.SymbolToken;
import com.amazon.ion.Timestamp;
import com.amazon.ion.impl._Private_IonConstants;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.ListIterator;

class ReverseBinaryEncoder {
    private static final BigInteger MAX_LONG_VALUE = BigInteger.valueOf(Long.MAX_VALUE);
    private static final int NULL_LENGTH_MASK = 15;
    private static final int TYPE_NULL = 0;
    private static final int TYPE_BOOL = 16;
    private static final int TYPE_POS_INT = 32;
    private static final int TYPE_NEG_INT = 48;
    private static final int TYPE_FLOAT = 64;
    private static final int TYPE_DECIMAL = 80;
    private static final int TYPE_TIMESTAMP = 96;
    private static final int TYPE_SYMBOL = 112;
    private static final int TYPE_STRING = 128;
    private static final int TYPE_CLOB = 144;
    private static final int TYPE_BLOB = 160;
    private static final int TYPE_LIST = 176;
    private static final int TYPE_SEXP = 192;
    private static final int TYPE_STRUCT = 208;
    private static final int TYPE_ANNOTATIONS = 224;
    private byte[] myBuffer;
    private int myOffset;
    private SymbolTable mySymbolTable;
    private IonSystem myIonSystem;
    private static final byte[] negativeZeroBitArray = new byte[]{-128};
    private static final byte[] positiveZeroBitArray = new byte[0];

    ReverseBinaryEncoder(int initialSize) {
        this.myBuffer = new byte[initialSize];
        this.myOffset = initialSize;
    }

    int byteSize() {
        return this.myBuffer.length - this.myOffset;
    }

    byte[] toNewByteArray() {
        int length = this.myBuffer.length - this.myOffset;
        byte[] bytes = new byte[length];
        System.arraycopy(this.myBuffer, this.myOffset, bytes, 0, length);
        return bytes;
    }

    int toNewByteArray(byte[] dst) {
        int length = this.myBuffer.length - this.myOffset;
        System.arraycopy(this.myBuffer, this.myOffset, dst, 0, length);
        return length;
    }

    int toNewByteArray(byte[] dst, int offset) {
        int length = this.myBuffer.length - this.myOffset;
        System.arraycopy(this.myBuffer, this.myOffset, dst, offset, length);
        return length;
    }

    int writeBytes(OutputStream out) throws IOException {
        int length = this.myBuffer.length - this.myOffset;
        byte[] bytes = new byte[length];
        System.arraycopy(this.myBuffer, this.myOffset, bytes, 0, length);
        out.write(bytes);
        return length;
    }

    void serialize(IonDatagram dg) throws IonException {
        this.myIonSystem = dg.getSystem();
        this.mySymbolTable = null;
        this.writeIonValue(dg);
        if (this.mySymbolTable != null && this.mySymbolTable.isLocalTable()) {
            this.writeLocalSymbolTable(this.mySymbolTable);
        }
        this.writeBytes(_Private_IonConstants.BINARY_VERSION_MARKER_1_0);
    }

    void serialize(SymbolTable symTab) throws IonException {
        this.writeLocalSymbolTable(symTab);
    }

    private int growBuffer(int offset) {
        assert (offset < 0);
        byte[] oldBuf = this.myBuffer;
        int oldLen = oldBuf.length;
        byte[] newBuf = new byte[-offset + oldLen << 1];
        int oldBegin = newBuf.length - oldLen;
        System.arraycopy(oldBuf, 0, newBuf, oldBegin, oldLen);
        this.myBuffer = newBuf;
        this.myOffset += oldBegin;
        return offset + oldBegin;
    }

    private void writeIonValue(IonValue value) throws IonException {
        int valueOffset = this.myBuffer.length - this.myOffset;
        switch (value.getType()) {
            case BLOB: {
                this.writeIonBlobContent((IonBlob)value);
                break;
            }
            case BOOL: {
                this.writeIonBoolContent((IonBool)value);
                break;
            }
            case CLOB: {
                this.writeIonClobContent((IonClob)value);
                break;
            }
            case DECIMAL: {
                this.writeIonDecimalContent((IonDecimal)value);
                break;
            }
            case FLOAT: {
                this.writeIonFloatContent((IonFloat)value);
                break;
            }
            case INT: {
                this.writeIonIntContent((IonInt)value);
                break;
            }
            case NULL: {
                this.writeIonNullContent();
                break;
            }
            case STRING: {
                this.writeIonStringContent((IonString)value);
                break;
            }
            case SYMBOL: {
                this.writeIonSymbolContent((IonSymbol)value);
                break;
            }
            case TIMESTAMP: {
                this.writeIonTimestampContent((IonTimestamp)value);
                break;
            }
            case LIST: {
                this.writeIonListContent((IonList)value);
                break;
            }
            case SEXP: {
                this.writeIonSexpContent((IonSexp)value);
                break;
            }
            case STRUCT: {
                this.writeIonStructContent((IonStruct)value);
                break;
            }
            case DATAGRAM: {
                this.writeIonDatagramContent((IonDatagram)value);
                break;
            }
            default: {
                throw new IonException("IonType is unknown: " + (Object)((Object)value.getType()));
            }
        }
        this.writeAnnotations(value, valueOffset);
    }

    private void writeByte(int b) {
        int offset = this.myOffset;
        if (--offset < 0) {
            offset = this.growBuffer(offset);
        }
        this.myBuffer[offset] = (byte)b;
        this.myOffset = offset;
    }

    private void writeBytes(byte[] bytes) {
        int length = bytes.length;
        int offset = this.myOffset;
        if ((offset -= length) < 0) {
            offset = this.growBuffer(offset);
        }
        System.arraycopy(bytes, 0, this.myBuffer, offset, length);
        this.myOffset = offset;
    }

    private void writeUInt(long v) {
        int offset = this.myOffset;
        if (v < 256L) {
            if (--offset < 0) {
                offset = this.growBuffer(offset);
            }
            this.myBuffer[offset] = (byte)v;
        } else if (v < 65536L) {
            if ((offset -= 2) < 0) {
                offset = this.growBuffer(offset);
            }
            this.myBuffer[offset] = (byte)(v >>> 8);
            this.myBuffer[offset + 1] = (byte)v;
        } else if (v < 0x1000000L) {
            if ((offset -= 3) < 0) {
                offset = this.growBuffer(offset);
            }
            this.myBuffer[offset] = (byte)(v >>> 16);
            this.myBuffer[offset + 1] = (byte)(v >>> 8);
            this.myBuffer[offset + 2] = (byte)v;
        } else if (v < 0x100000000L) {
            if ((offset -= 4) < 0) {
                offset = this.growBuffer(offset);
            }
            this.myBuffer[offset] = (byte)(v >>> 24);
            this.myBuffer[offset + 1] = (byte)(v >>> 16);
            this.myBuffer[offset + 2] = (byte)(v >>> 8);
            this.myBuffer[offset + 3] = (byte)v;
        } else if (v < 0x10000000000L) {
            if ((offset -= 5) < 0) {
                offset = this.growBuffer(offset);
            }
            this.myBuffer[offset] = (byte)(v >>> 32);
            this.myBuffer[offset + 1] = (byte)(v >>> 24);
            this.myBuffer[offset + 2] = (byte)(v >>> 16);
            this.myBuffer[offset + 3] = (byte)(v >>> 8);
            this.myBuffer[offset + 4] = (byte)v;
        } else if (v < 0x1000000000000L) {
            if ((offset -= 6) < 0) {
                offset = this.growBuffer(offset);
            }
            this.myBuffer[offset] = (byte)(v >>> 40);
            this.myBuffer[offset + 1] = (byte)(v >>> 32);
            this.myBuffer[offset + 2] = (byte)(v >>> 24);
            this.myBuffer[offset + 3] = (byte)(v >>> 16);
            this.myBuffer[offset + 4] = (byte)(v >>> 8);
            this.myBuffer[offset + 5] = (byte)v;
        } else if (v < 0x100000000000000L) {
            if ((offset -= 7) < 0) {
                offset = this.growBuffer(offset);
            }
            this.myBuffer[offset] = (byte)(v >>> 48);
            this.myBuffer[offset + 1] = (byte)(v >>> 40);
            this.myBuffer[offset + 2] = (byte)(v >>> 32);
            this.myBuffer[offset + 3] = (byte)(v >>> 24);
            this.myBuffer[offset + 4] = (byte)(v >>> 16);
            this.myBuffer[offset + 5] = (byte)(v >>> 8);
            this.myBuffer[offset + 6] = (byte)v;
        } else {
            if ((offset -= 8) < 0) {
                offset = this.growBuffer(offset);
            }
            this.myBuffer[offset] = (byte)(v >>> 56);
            this.myBuffer[offset + 1] = (byte)(v >>> 48);
            this.myBuffer[offset + 2] = (byte)(v >>> 40);
            this.myBuffer[offset + 3] = (byte)(v >>> 32);
            this.myBuffer[offset + 4] = (byte)(v >>> 24);
            this.myBuffer[offset + 5] = (byte)(v >>> 16);
            this.myBuffer[offset + 6] = (byte)(v >>> 8);
            this.myBuffer[offset + 7] = (byte)v;
        }
        this.myOffset = offset;
    }

    private void writeVarUInt(int v) {
        int offset = this.myOffset;
        if (v < 128) {
            if (--offset < 0) {
                offset = this.growBuffer(offset);
            }
            this.myBuffer[offset] = (byte)(v | 0x80);
        } else if (v < 16384) {
            if ((offset -= 2) < 0) {
                offset = this.growBuffer(offset);
            }
            this.myBuffer[offset] = (byte)(v >>> 7);
            this.myBuffer[offset + 1] = (byte)(v | 0x80);
        } else if (v < 0x200000) {
            if ((offset -= 3) < 0) {
                offset = this.growBuffer(offset);
            }
            this.myBuffer[offset] = (byte)(v >>> 14);
            this.myBuffer[offset + 1] = (byte)(v >>> 7 & 0x7F);
            this.myBuffer[offset + 2] = (byte)(v | 0x80);
        } else if (v < 0x10000000) {
            if ((offset -= 4) < 0) {
                offset = this.growBuffer(offset);
            }
            this.myBuffer[offset] = (byte)(v >>> 21);
            this.myBuffer[offset + 1] = (byte)(v >>> 14 & 0x7F);
            this.myBuffer[offset + 2] = (byte)(v >>> 7 & 0x7F);
            this.myBuffer[offset + 3] = (byte)(v | 0x80);
        } else {
            if ((offset -= 5) < 0) {
                offset = this.growBuffer(offset);
            }
            this.myBuffer[offset] = (byte)(v >>> 28);
            this.myBuffer[offset + 1] = (byte)(v >>> 21 & 0x7F);
            this.myBuffer[offset + 2] = (byte)(v >>> 14 & 0x7F);
            this.myBuffer[offset + 3] = (byte)(v >>> 7 & 0x7F);
            this.myBuffer[offset + 4] = (byte)(v | 0x80);
        }
        this.myOffset = offset;
    }

    private void writeVarInt(int v) {
        if (v == 0) {
            this.writeByte(128);
        } else {
            boolean is_negative;
            int offset = this.myOffset;
            boolean bl = is_negative = v < 0;
            if (is_negative) {
                v = -v;
            }
            if (v < 64) {
                if (--offset < 0) {
                    offset = this.growBuffer(offset);
                }
                if (is_negative) {
                    v |= 0x40;
                }
                this.myBuffer[offset] = (byte)(v | 0x80);
            } else if (v < 8192) {
                if ((offset -= 2) < 0) {
                    offset = this.growBuffer(offset);
                }
                if (is_negative) {
                    v |= 0x2000;
                }
                this.myBuffer[offset] = (byte)(v >>> 7);
                this.myBuffer[offset + 1] = (byte)(v | 0x80);
            } else if (v < 0x100000) {
                if ((offset -= 3) < 0) {
                    offset = this.growBuffer(offset);
                }
                if (is_negative) {
                    v |= 0x100000;
                }
                this.myBuffer[offset] = (byte)(v >>> 14);
                this.myBuffer[offset + 1] = (byte)(v >>> 7 & 0x7F);
                this.myBuffer[offset + 2] = (byte)(v | 0x80);
            } else if (v < 0x8000000) {
                if ((offset -= 4) < 0) {
                    offset = this.growBuffer(offset);
                }
                if (is_negative) {
                    v |= 0x8000000;
                }
                this.myBuffer[offset] = (byte)(v >>> 21);
                this.myBuffer[offset + 1] = (byte)(v >>> 14 & 0x7F);
                this.myBuffer[offset + 2] = (byte)(v >>> 7 & 0x7F);
                this.myBuffer[offset + 3] = (byte)(v | 0x80);
            } else {
                if ((offset -= 5) < 0) {
                    offset = this.growBuffer(offset);
                }
                this.myBuffer[offset] = (byte)(v >>> 28 & 0x7F);
                if (is_negative) {
                    int n = offset;
                    this.myBuffer[n] = (byte)(this.myBuffer[n] | 0x40);
                }
                this.myBuffer[offset + 1] = (byte)(v >>> 21 & 0x7F);
                this.myBuffer[offset + 2] = (byte)(v >>> 14 & 0x7F);
                this.myBuffer[offset + 3] = (byte)(v >>> 7 & 0x7F);
                this.myBuffer[offset + 4] = (byte)(v | 0x80);
            }
            this.myOffset = offset;
        }
    }

    private void writePrefix(int type, int length) {
        if (length >= 14) {
            this.writeVarUInt(length);
            length = 14;
        }
        int offset = this.myOffset;
        if (--offset < 0) {
            offset = this.growBuffer(offset);
        }
        this.myBuffer[offset] = (byte)(type | length);
        this.myOffset = offset;
    }

    private void writeAnnotations(IonValue value, int endOfValueOffset) {
        SymbolToken[] annotationSymTokens = value.getTypeAnnotationSymbols();
        if (annotationSymTokens.length > 0) {
            int annotatedValueOffset = this.myBuffer.length - this.myOffset;
            int i = annotationSymTokens.length;
            while (--i >= 0) {
                int sid = this.findSid(annotationSymTokens[i]);
                this.writeVarUInt(sid);
            }
            this.writeVarUInt(this.myBuffer.length - this.myOffset - annotatedValueOffset);
            this.writePrefix(224, this.myBuffer.length - this.myOffset - endOfValueOffset);
        }
    }

    private void writeIonNullContent() {
        int encoded = 15;
        this.writeByte(encoded);
    }

    private void writeIonBoolContent(IonBool val) {
        boolean b;
        int encoded = val.isNullValue() ? 31 : ((b = val.booleanValue()) ? 17 : 16);
        this.writeByte(encoded);
    }

    private void writeIonIntContent(IonInt val) {
        if (val.isNullValue()) {
            this.writeByte(47);
        } else {
            int type;
            BigInteger bigInt = val.bigIntegerValue();
            int signum = bigInt.signum();
            int originalOffset = this.myBuffer.length - this.myOffset;
            if (signum == 0) {
                this.writeByte(32);
                return;
            }
            if (signum < 0) {
                type = 48;
                bigInt = bigInt.negate();
            } else {
                type = 32;
            }
            if (bigInt.compareTo(MAX_LONG_VALUE) < 0) {
                long lvalue = bigInt.longValue();
                this.writeUInt(lvalue);
            } else {
                int offset;
                byte[] bits = bigInt.toByteArray();
                for (offset = 0; offset < bits.length && bits[offset] == 0; ++offset) {
                }
                int actualBitLength = bits.length - offset;
                int bufferOffset = this.myOffset - actualBitLength;
                if (bufferOffset < 0) {
                    bufferOffset = this.growBuffer(bufferOffset);
                }
                System.arraycopy(bits, offset, this.myBuffer, bufferOffset, actualBitLength);
                this.myOffset = bufferOffset;
            }
            this.writePrefix(type, this.myBuffer.length - this.myOffset - originalOffset);
        }
    }

    private void writeIonFloatContent(IonFloat val) {
        if (val.isNullValue()) {
            this.writeByte(79);
        } else {
            long bits = Double.doubleToRawLongBits(val.doubleValue());
            int offset = this.myOffset;
            if ((offset -= 8) < 0) {
                offset = this.growBuffer(offset);
            }
            this.myBuffer[offset] = (byte)(bits >>> 56);
            this.myBuffer[offset + 1] = (byte)(bits >>> 48);
            this.myBuffer[offset + 2] = (byte)(bits >>> 40);
            this.myBuffer[offset + 3] = (byte)(bits >>> 32);
            this.myBuffer[offset + 4] = (byte)(bits >>> 24);
            this.myBuffer[offset + 5] = (byte)(bits >>> 16);
            this.myBuffer[offset + 6] = (byte)(bits >>> 8);
            this.myBuffer[offset + 7] = (byte)bits;
            this.myOffset = offset;
            this.writePrefix(64, 8);
        }
    }

    private void writeIonDecimalContent(BigDecimal bd) {
        byte[] mantissaBits;
        BigInteger mantissa = bd.unscaledValue();
        switch (mantissa.signum()) {
            case 0: {
                if (Decimal.isNegativeZero(bd)) {
                    mantissaBits = negativeZeroBitArray;
                    break;
                }
                mantissaBits = positiveZeroBitArray;
                break;
            }
            case -1: {
                mantissaBits = mantissa.negate().toByteArray();
                mantissaBits[0] = (byte)(mantissaBits[0] | 0x80);
                break;
            }
            case 1: {
                mantissaBits = mantissa.toByteArray();
                break;
            }
            default: {
                throw new IllegalStateException("mantissa signum out of range");
            }
        }
        this.writeBytes(mantissaBits);
        int exponent = -bd.scale();
        this.writeVarInt(exponent);
    }

    private void writeIonDecimalContent(IonDecimal val) {
        if (val.isNullValue()) {
            this.writeByte(95);
        } else {
            int originalOffset = this.myBuffer.length - this.myOffset;
            this.writeIonDecimalContent(val.decimalValue());
            this.writePrefix(80, this.myBuffer.length - this.myOffset - originalOffset);
        }
    }

    private void writeIonTimestampContent(IonTimestamp val) {
        if (val.isNullValue()) {
            this.writeByte(111);
        } else {
            int originalOffset = this.myBuffer.length - this.myOffset;
            Timestamp t = val.timestampValue();
            switch (t.getPrecision()) {
                case FRACTION: 
                case SECOND: {
                    BigDecimal fraction = t.getZFractionalSecond();
                    if (fraction != null) {
                        assert (fraction.signum() >= 0 && !fraction.equals(BigDecimal.ZERO)) : "Bad timestamp fraction: " + fraction;
                        this.writeIonDecimalContent(fraction);
                    }
                    this.writeVarUInt(t.getZSecond());
                }
                case MINUTE: {
                    this.writeVarUInt(t.getZMinute());
                    this.writeVarUInt(t.getZHour());
                }
                case DAY: {
                    this.writeVarUInt(t.getZDay());
                }
                case MONTH: {
                    this.writeVarUInt(t.getZMonth());
                }
                case YEAR: {
                    this.writeVarUInt(t.getZYear());
                    break;
                }
                default: {
                    throw new IllegalStateException("unrecognized Timestamp precision: " + (Object)((Object)t.getPrecision()));
                }
            }
            Integer offset = t.getLocalOffset();
            if (offset == null) {
                this.writeByte(-64);
            } else {
                this.writeVarInt(offset);
            }
            this.writePrefix(96, this.myBuffer.length - this.myOffset - originalOffset);
        }
    }

    private void writeIonSymbolContent(IonSymbol val) {
        if (val.isNullValue()) {
            this.writeByte(127);
        } else {
            int originalOffset = this.myBuffer.length - this.myOffset;
            SymbolToken symToken = val.symbolValue();
            int sid = this.findSid(symToken);
            this.writeUInt(sid);
            this.writePrefix(112, this.myBuffer.length - this.myOffset - originalOffset);
        }
    }

    private void writeIonStringContent(IonString val) {
        if (val.isNullValue()) {
            this.writeByte(-113);
        } else {
            this.writeIonStringContent(val.stringValue());
        }
    }

    private void writeIonStringContent(String str) {
        char c;
        int i;
        int strlen = str.length();
        byte[] buffer = this.myBuffer;
        int offset = this.myOffset;
        if ((offset -= strlen) < 0) {
            offset = this.growBuffer(offset);
            buffer = this.myBuffer;
        }
        offset += strlen;
        for (i = strlen - 1; i >= 0 && (c = str.charAt(i)) <= '\u007f'; --i) {
            buffer[--offset] = (byte)c;
        }
        while (i >= 0) {
            c = str.charAt(i);
            if (c <= '\u007f') {
                if (--offset < 0) {
                    offset = this.growBuffer(offset);
                    buffer = this.myBuffer;
                }
                buffer[offset] = (byte)c;
            } else if (c <= '\u07ff') {
                if ((offset -= 2) < 0) {
                    offset = this.growBuffer(offset);
                    buffer = this.myBuffer;
                }
                buffer[offset] = (byte)(0xC0 | c >> 6 & 0x1F);
                buffer[offset + 1] = (byte)(0x80 | c & 0x3F);
            } else if (c >= '\ud800' && c <= '\udfff') {
                char c2;
                if (c <= '\udbff') {
                    throw new IonException("invalid string, unpaired high surrogate character");
                }
                if (i == 0) {
                    throw new IonException("invalid string, unpaired low surrogate character");
                }
                if ((c2 = str.charAt(--i)) < '\ud800' || c2 > '\udbff') {
                    throw new IonException("invalid string, unpaired low surrogate character");
                }
                int codepoint = 65536 + ((c2 & 0x3FF) << 10 | c & 0x3FF);
                if ((offset -= 4) < 0) {
                    offset = this.growBuffer(offset);
                    buffer = this.myBuffer;
                }
                buffer[offset] = (byte)(0xF0 | codepoint >> 18 & 7);
                buffer[offset + 1] = (byte)(0x80 | codepoint >> 12 & 0x3F);
                buffer[offset + 2] = (byte)(0x80 | codepoint >> 6 & 0x3F);
                buffer[offset + 3] = (byte)(0x80 | codepoint >> 0 & 0x3F);
            } else {
                if ((offset -= 3) < 0) {
                    offset = this.growBuffer(offset);
                    buffer = this.myBuffer;
                }
                buffer[offset] = (byte)(0xE0 | c >> 12 & 0xF);
                buffer[offset + 1] = (byte)(0x80 | c >> 6 & 0x3F);
                buffer[offset + 2] = (byte)(0x80 | c & 0x3F);
            }
            --i;
        }
        int length = this.myOffset - offset;
        this.myOffset = offset;
        this.writePrefix(128, length);
    }

    private void writeIonClobContent(IonClob val) {
        if (val.isNullValue()) {
            this.writeByte(-97);
        } else {
            byte[] lob = val.getBytes();
            this.writeLobContent(lob);
            this.writePrefix(144, lob.length);
        }
    }

    private void writeIonBlobContent(IonBlob val) {
        if (val.isNullValue()) {
            this.writeByte(-81);
        } else {
            byte[] lob = val.getBytes();
            this.writeLobContent(lob);
            this.writePrefix(160, lob.length);
        }
    }

    private void writeLobContent(byte[] lob) {
        int length = lob.length;
        int offset = this.myOffset - length;
        if (offset < 0) {
            offset = this.growBuffer(offset);
        }
        System.arraycopy(lob, 0, this.myBuffer, offset, length);
        this.myOffset = offset;
    }

    private void writeIonListContent(IonList val) {
        if (val.isNullValue()) {
            this.writeByte(-65);
        } else {
            this.writeIonSequenceContent(val);
        }
    }

    private void writeIonSexpContent(IonSexp val) {
        if (val.isNullValue()) {
            this.writeByte(-49);
        } else {
            this.writeIonSequenceContent(val);
        }
    }

    private void writeIonSequenceContent(IonSequence seq2) {
        int originalOffset = this.myBuffer.length - this.myOffset;
        IonValue[] values2 = seq2.toArray();
        int i = values2.length;
        while (--i >= 0) {
            this.writeIonValue(values2[i]);
        }
        switch (seq2.getType()) {
            case LIST: {
                this.writePrefix(176, this.myBuffer.length - this.myOffset - originalOffset);
                break;
            }
            case SEXP: {
                this.writePrefix(192, this.myBuffer.length - this.myOffset - originalOffset);
                break;
            }
            default: {
                throw new IonException("cannot identify instance of IonSequence");
            }
        }
    }

    private void writeIonStructContent(IonStruct val) {
        if (val.isNullValue()) {
            this.writeByte(-33);
        } else {
            int originalOffset = this.myBuffer.length - this.myOffset;
            ArrayList<IonValue> values2 = new ArrayList<IonValue>();
            for (IonValue curr : val) {
                values2.add(curr);
            }
            int i = values2.size();
            while (--i >= 0) {
                IonValue v = (IonValue)values2.get(i);
                SymbolToken symToken = v.getFieldNameSymbol();
                this.writeIonValue(v);
                int sid = this.findSid(symToken);
                this.writeVarUInt(sid);
            }
            this.writePrefix(208, this.myBuffer.length - this.myOffset - originalOffset);
        }
    }

    private void writeIonDatagramContent(IonDatagram dg) {
        ListIterator<IonValue> reverseIter = dg.listIterator(dg.size());
        while (reverseIter.hasPrevious()) {
            IonValue currentTopLevelValue = reverseIter.previous();
            this.checkLocalSymbolTablePlacement(currentTopLevelValue);
            this.writeIonValue(currentTopLevelValue);
        }
    }

    private int findSid(SymbolToken symToken) {
        int sid = symToken.getSid();
        String text = symToken.getText();
        if (sid != -1) {
            assert (text == null || text.equals(this.mySymbolTable.findKnownSymbol(sid)));
        } else {
            if (this.mySymbolTable.isSystemTable()) {
                this.mySymbolTable = this.myIonSystem.newLocalSymbolTable(new SymbolTable[0]);
            }
            sid = this.mySymbolTable.intern(text).getSid();
        }
        return sid;
    }

    private void checkLocalSymbolTablePlacement(IonValue nextTopLevelValue) {
        assert (nextTopLevelValue == nextTopLevelValue.topLevelValue());
        SymbolTable nextSymTab = nextTopLevelValue.getSymbolTable();
        if (nextSymTab == null) {
            throw new IllegalStateException("Binary reverse encoder isn't using LiteImpl");
        }
        if (this.mySymbolTable == null) {
            this.mySymbolTable = nextSymTab;
            return;
        }
        assert (nextSymTab.isLocalTable() || nextSymTab.isSystemTable());
        if (nextSymTab.isLocalTable()) {
            if (this.mySymbolTable.isSystemTable()) {
                this.writeBytes(_Private_IonConstants.BINARY_VERSION_MARKER_1_0);
                this.mySymbolTable = nextSymTab;
            } else if (nextSymTab != this.mySymbolTable) {
                this.writeLocalSymbolTable(this.mySymbolTable);
                this.mySymbolTable = nextSymTab;
            }
        } else if (this.mySymbolTable.isSystemTable() && !this.mySymbolTable.getIonVersionId().equals(nextSymTab.getIonVersionId())) {
            this.writeBytes(_Private_IonConstants.BINARY_VERSION_MARKER_1_0);
            this.mySymbolTable = nextSymTab;
        }
    }

    private void writeLocalSymbolTable(SymbolTable symTab) {
        assert (symTab.isLocalTable());
        int originalOffset = this.myBuffer.length - this.myOffset;
        this.writeSymbolsField(symTab);
        this.writeImportsField(symTab);
        this.writePrefix(208, this.myBuffer.length - this.myOffset - originalOffset);
        byte[] ionSymbolTableByteArray = new byte[]{-127, -125};
        this.writeBytes(ionSymbolTableByteArray);
        this.writePrefix(224, this.myBuffer.length - this.myOffset - originalOffset);
    }

    private void writeImport(SymbolTable symTab) {
        assert (symTab.isSharedTable());
        int originalOffset = this.myBuffer.length - this.myOffset;
        int maxId = symTab.getMaxId();
        if (maxId == 0) {
            this.writeByte(32);
        } else {
            this.writeUInt(maxId);
            this.writePrefix(32, this.myBuffer.length - this.myOffset - originalOffset);
        }
        this.writeByte(-120);
        int maxIdOffset = this.myBuffer.length - this.myOffset;
        int version = symTab.getVersion();
        this.writeUInt(version);
        this.writePrefix(32, this.myBuffer.length - this.myOffset - maxIdOffset);
        this.writeByte(-123);
        String name = symTab.getName();
        this.writeIonStringContent(name);
        this.writeByte(-124);
        this.writePrefix(208, this.myBuffer.length - this.myOffset - originalOffset);
    }

    private void writeImportsField(SymbolTable symTab) {
        SymbolTable[] sharedSymTabs = symTab.getImportedTables();
        if (sharedSymTabs.length == 0) {
            return;
        }
        int importsOffset = this.myBuffer.length - this.myOffset;
        int i = sharedSymTabs.length;
        while (--i >= 0) {
            this.writeImport(sharedSymTabs[i]);
        }
        this.writePrefix(176, this.myBuffer.length - this.myOffset - importsOffset);
        this.writeByte(-122);
    }

    private void writeSymbolsField(SymbolTable symTab) {
        int maxId;
        int importedMaxId = symTab.getImportedMaxId();
        if (importedMaxId == (maxId = symTab.getMaxId())) {
            return;
        }
        int originalOffset = this.myBuffer.length - this.myOffset;
        for (int i = maxId; i > importedMaxId; --i) {
            String str = symTab.findKnownSymbol(i);
            if (str == null) {
                this.writeByte(-113);
                continue;
            }
            this.writeIonStringContent(str);
        }
        this.writePrefix(176, this.myBuffer.length - this.myOffset - originalOffset);
        this.writeByte(-121);
    }
}

