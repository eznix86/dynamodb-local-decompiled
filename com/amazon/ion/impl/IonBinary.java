/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazon.ion.impl;

import com.amazon.ion.Decimal;
import com.amazon.ion.IonException;
import com.amazon.ion.SymbolTable;
import com.amazon.ion.SymbolToken;
import com.amazon.ion.Timestamp;
import com.amazon.ion.UnexpectedEofException;
import com.amazon.ion.impl.BlockedBuffer;
import com.amazon.ion.impl.IonTokenReader;
import com.amazon.ion.impl._Private_IonConstants;
import com.amazon.ion.impl._Private_Utils;
import com.amazon.ion.util.IonStreamUtils;
import com.amazon.ion.util.IonTextUtils;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PushbackReader;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.Stack;

final class IonBinary {
    static boolean debugValidation = false;
    private static final BigInteger MAX_LONG_VALUE = new BigInteger(Long.toString(Long.MAX_VALUE));
    private static final int SIZE_OF_LONG = 8;
    static final int _ib_TOKEN_LEN = 1;
    static final int _ib_VAR_INT32_LEN_MAX = 5;
    static final int _ib_VAR_INT64_LEN_MAX = 10;
    static final int _ib_INT64_LEN_MAX = 8;
    static final int _ib_FLOAT64_LEN = 8;
    private static final Double DOUBLE_POS_ZERO = 0.0;
    static final int ZERO_DECIMAL_TYPEDESC = _Private_IonConstants.makeTypeDescriptor(5, 0);
    static final int NULL_DECIMAL_TYPEDESC = _Private_IonConstants.makeTypeDescriptor(5, 15);

    private IonBinary() {
    }

    public static void verifyBinaryVersionMarker(Reader reader) throws IonException {
        try {
            int pos = reader.position();
            byte[] bvm = new byte[_Private_IonConstants.BINARY_VERSION_MARKER_SIZE];
            int len = _Private_Utils.readFully(reader, bvm);
            if (len < _Private_IonConstants.BINARY_VERSION_MARKER_SIZE) {
                String message = "Binary data is too short: at least " + _Private_IonConstants.BINARY_VERSION_MARKER_SIZE + " bytes are required, but only " + len + " were found.";
                throw new IonException(message);
            }
            if (!IonStreamUtils.isIonBinary(bvm)) {
                StringBuilder buf = new StringBuilder();
                buf.append("Binary data has unrecognized header");
                for (int i = 0; i < bvm.length; ++i) {
                    int b = bvm[i] & 0xFF;
                    buf.append(" 0x");
                    buf.append(Integer.toHexString(b).toUpperCase());
                }
                throw new IonException(buf.toString());
            }
            reader.setPosition(pos);
        } catch (IOException e) {
            throw new IonException(e);
        }
    }

    public static int writeTypeDescWithLength(OutputStream userstream, int typeid, int lenOfLength, int valueLength) throws IOException {
        int written_len = 1;
        int td = (typeid & 0xF) << 4;
        if (valueLength >= 14) {
            userstream.write((byte)((td |= 0xE) & 0xFF));
            written_len += IonBinary.writeVarUInt(userstream, valueLength, lenOfLength, true);
        } else {
            userstream.write((byte)((td |= valueLength & 0xF) & 0xFF));
        }
        return written_len;
    }

    public static int writeTypeDescWithLength(OutputStream userstream, int typeid, int valueLength) throws IOException {
        int written_len = 1;
        int td = (typeid & 0xF) << 4;
        if (valueLength >= 14) {
            userstream.write((byte)((td |= 0xE) & 0xFF));
            int lenOfLength = IonBinary.lenVarUInt(valueLength);
            written_len += IonBinary.writeVarUInt(userstream, valueLength, lenOfLength, true);
        } else {
            userstream.write((byte)((td |= valueLength & 0xF) & 0xFF));
        }
        return written_len;
    }

    public static int writeIonInt(OutputStream userstream, long value, int len) throws IOException {
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
                userstream.write((byte)(value >> 56 & mask));
            }
            case 7: {
                userstream.write((byte)(value >> 48 & mask));
            }
            case 6: {
                userstream.write((byte)(value >> 40 & mask));
            }
            case 5: {
                userstream.write((byte)(value >> 32 & mask));
            }
            case 4: {
                userstream.write((byte)(value >> 24 & mask));
            }
            case 3: {
                userstream.write((byte)(value >> 16 & mask));
            }
            case 2: {
                userstream.write((byte)(value >> 8 & mask));
            }
            case 1: {
                userstream.write((byte)(value & mask));
            }
        }
        return len;
    }

    public static int writeVarUInt(OutputStream userstream, long value) throws IOException {
        int len = IonBinary.lenVarUInt(value);
        IonBinary.writeVarUInt(userstream, value, len, false);
        return len;
    }

    public static int writeVarUInt(OutputStream userstream, long value, int len, boolean force_zero_write) throws IOException {
        int mask = 127;
        assert (len == IonBinary.lenVarUInt(value));
        assert (value >= 0L);
        switch (len - 1) {
            case 9: {
                userstream.write((byte)(value >> 63 & (long)mask));
            }
            case 8: {
                userstream.write((byte)(value >> 56 & (long)mask));
            }
            case 7: {
                userstream.write((byte)(value >> 49 & (long)mask));
            }
            case 6: {
                userstream.write((byte)(value >> 42 & (long)mask));
            }
            case 5: {
                userstream.write((byte)(value >> 35 & (long)mask));
            }
            case 4: {
                userstream.write((byte)(value >> 28 & (long)mask));
            }
            case 3: {
                userstream.write((byte)(value >> 21 & (long)mask));
            }
            case 2: {
                userstream.write((byte)(value >> 14 & (long)mask));
            }
            case 1: {
                userstream.write((byte)(value >> 7 & (long)mask));
            }
            case 0: {
                userstream.write((byte)(value & (long)mask | 0x80L));
                break;
            }
            case -1: {
                if (force_zero_write) {
                    userstream.write(-128);
                    assert (len == 1);
                    break;
                }
                assert (len == 0);
                break;
            }
        }
        return len;
    }

    public static int writeString(OutputStream userstream, String value) throws IOException {
        int len = 0;
        for (int ii = 0; ii < value.length(); ++ii) {
            int c = value.charAt(ii);
            if (c < 128) {
                userstream.write((byte)c);
                ++len;
                continue;
            }
            if (c >= 55296 && c <= 57343) {
                if (_Private_IonConstants.isHighSurrogate(c)) {
                    if (++ii >= value.length()) {
                        throw new IllegalArgumentException("invalid string, unpaired high surrogate character");
                    }
                    char c2 = value.charAt(ii);
                    if (!_Private_IonConstants.isLowSurrogate(c2)) {
                        throw new IllegalArgumentException("invalid string, unpaired high surrogate character");
                    }
                    c = _Private_IonConstants.makeUnicodeScalar(c, c2);
                } else if (_Private_IonConstants.isLowSurrogate(c)) {
                    throw new IllegalArgumentException("invalid string, unpaired low surrogate character");
                }
            }
            c = IonBinary.makeUTF8IntFromScalar(c);
            while ((c & 0xFFFFFF00) != 0) {
                userstream.write((byte)(c & 0xFF));
                c >>>= 8;
                ++len;
            }
        }
        return len;
    }

    public static final int makeUTF8IntFromScalar(int c) throws IOException {
        int value = 0;
        if (c < 128) {
            value = 0xFF & c;
        } else if (c < 2048) {
            value = 0xFF & (0xC0 | c >> 6);
            value |= (0xFF & (0x80 | c & 0x3F)) << 8;
        } else if (c < 65536) {
            if (c > 55295 && c < 57344) {
                IonBinary.throwUTF8Exception();
            }
            value = 0xFF & (0xE0 | c >> 12);
            value |= (0xFF & (0x80 | c >> 6 & 0x3F)) << 8;
            value |= (0xFF & (0x80 | c & 0x3F)) << 16;
        } else if (c <= 0x10FFFF) {
            value = 0xFF & (0xF0 | c >> 18);
            value |= (0xFF & (0x80 | c >> 12 & 0x3F)) << 8;
            value |= (0xFF & (0x80 | c >> 6 & 0x3F)) << 16;
            value |= (0xFF & (0x80 | c & 0x3F)) << 24;
        } else {
            IonBinary.throwUTF8Exception();
        }
        return value;
    }

    static void throwUTF8Exception() throws IOException {
        throw new IOException("Invalid UTF-8 character encountered");
    }

    public static int lenVarUInt(long longVal) {
        assert (longVal >= 0L);
        if (longVal < 128L) {
            return 1;
        }
        if (longVal < 16384L) {
            return 2;
        }
        if (longVal < 0x200000L) {
            return 3;
        }
        if (longVal < 0x10000000L) {
            return 4;
        }
        if (longVal < 0x800000000L) {
            return 5;
        }
        if (longVal < 0x40000000000L) {
            return 6;
        }
        if (longVal < 0x2000000000000L) {
            return 7;
        }
        if (longVal < 0x100000000000000L) {
            return 8;
        }
        if (longVal < Long.MIN_VALUE) {
            return 9;
        }
        return 10;
    }

    public static int lenVarInt(long longVal) {
        if (longVal == 0L) {
            return 0;
        }
        if (longVal < 0L) {
            longVal = -longVal;
        }
        if (longVal < 64L) {
            return 1;
        }
        if (longVal < 8192L) {
            return 2;
        }
        if (longVal < 0x100000L) {
            return 3;
        }
        if (longVal < 0x8000000L) {
            return 4;
        }
        if (longVal < 0x400000000L) {
            return 5;
        }
        if (longVal < 0x20000000000L) {
            return 6;
        }
        if (longVal < 0x1000000000000L) {
            return 7;
        }
        if (longVal < 0x80000000000000L) {
            return 8;
        }
        if (longVal < 0x4000000000000000L) {
            return 9;
        }
        return 10;
    }

    public static int lenUInt(long longVal) {
        if (longVal == 0L) {
            return 0;
        }
        if (longVal < 0L) {
            throw new BlockedBuffer.BlockedBufferException("fatal signed long where unsigned was promised");
        }
        if (longVal < 256L) {
            return 1;
        }
        if (longVal < 65536L) {
            return 2;
        }
        if (longVal < 0x1000000L) {
            return 3;
        }
        if (longVal < 0x100000000L) {
            return 4;
        }
        if (longVal < 0x10000000000L) {
            return 5;
        }
        if (longVal < 0x1000000000000L) {
            return 6;
        }
        if (longVal < 0x100000000000000L) {
            return 7;
        }
        return 8;
    }

    public static int lenUInt(BigInteger bigVal) {
        if (bigVal.signum() < 0) {
            throw new IllegalArgumentException("lenUInt expects a non-negative a value");
        }
        int bits = bigVal.bitLength();
        int bytes = bits >> 3;
        if ((bits & 7) != 0) {
            ++bytes;
        }
        return bytes;
    }

    public static int lenInt(long longVal) {
        if (longVal != 0L) {
            return 0;
        }
        if (longVal < 0L) {
            longVal = -longVal;
        }
        if (longVal < 128L) {
            return 1;
        }
        if (longVal < 32768L) {
            return 2;
        }
        if (longVal < 0x800000L) {
            return 3;
        }
        if (longVal < 0x80000000L) {
            return 4;
        }
        if (longVal < 0x8000000000L) {
            return 5;
        }
        if (longVal < 0x800000000000L) {
            return 6;
        }
        if (longVal < 0x80000000000000L) {
            return 7;
        }
        if (longVal == Long.MIN_VALUE) {
            return 9;
        }
        return 8;
    }

    public static int lenInt(BigInteger bi, boolean force_zero_writes) {
        int len = bi.abs().bitLength() + 1;
        int bytelen = 0;
        switch (bi.signum()) {
            case 0: {
                bytelen = force_zero_writes ? 1 : 0;
                break;
            }
            case -1: 
            case 1: {
                bytelen = (len - 1) / 8 + 1;
            }
        }
        return bytelen;
    }

    public static int lenIonInt(long v) {
        if (v < 0L) {
            if (v == Long.MIN_VALUE) {
                return 8;
            }
            return IonBinary.lenUInt(-v);
        }
        if (v > 0L) {
            return IonBinary.lenUInt(v);
        }
        return 0;
    }

    public static int lenIonInt(BigInteger v) {
        if (v.signum() < 0) {
            v = v.negate();
        }
        int len = IonBinary.lenUInt(v);
        return len;
    }

    public static int lenLenFieldWithOptionalNibble(int valuelen) {
        if (valuelen < 14) {
            return 0;
        }
        return IonBinary.lenVarUInt(valuelen);
    }

    public static int lenTypeDescWithAppropriateLenField(int type, int valuelen) {
        switch (type) {
            case 0: 
            case 1: {
                return 1;
            }
            case 2: 
            case 3: 
            case 4: 
            case 5: 
            case 6: 
            case 7: 
            case 8: 
            case 9: 
            case 10: 
            case 11: 
            case 12: 
            case 13: 
            case 14: {
                if (valuelen < 14) {
                    return 1;
                }
                return 1 + IonBinary.lenVarUInt(valuelen);
            }
        }
        throw new IonException("invalid type");
    }

    public static int lenIonFloat(double value) {
        if (Double.valueOf(value).equals(DOUBLE_POS_ZERO)) {
            return 0;
        }
        return 8;
    }

    public static boolean isNibbleZero(BigDecimal bd) {
        if (Decimal.isNegativeZero(bd)) {
            return false;
        }
        if (bd.signum() != 0) {
            return false;
        }
        int scale = bd.scale();
        return scale == 0;
    }

    public static int lenIonDecimal(BigDecimal bd) {
        if (bd == null) {
            return 0;
        }
        if (IonBinary.isNibbleZero(bd)) {
            return 0;
        }
        BigInteger mantissa = bd.unscaledValue();
        boolean forceMantissa = Decimal.isNegativeZero(bd);
        int mantissaByteCount = IonBinary.lenInt(mantissa, forceMantissa);
        int scale = bd.scale();
        int exponentByteCount = IonBinary.lenVarInt(scale);
        if (exponentByteCount == 0) {
            exponentByteCount = 1;
        }
        return exponentByteCount + mantissaByteCount;
    }

    public static int lenIonTimestamp(Timestamp di) {
        if (di == null) {
            return 0;
        }
        int len = 0;
        switch (di.getPrecision()) {
            case FRACTION: 
            case SECOND: {
                BigDecimal fraction = di.getFractionalSecond();
                if (fraction != null) {
                    assert (fraction.signum() >= 0 && !fraction.equals(BigDecimal.ZERO)) : "Bad timestamp fraction: " + fraction;
                    int fracLen = IonBinary.lenIonDecimal(fraction);
                    assert (fracLen > 0);
                    len += fracLen;
                }
                ++len;
            }
            case MINUTE: {
                len += 2;
            }
            case DAY: {
                ++len;
            }
            case MONTH: {
                ++len;
            }
            case YEAR: {
                len += IonBinary.lenVarUInt(di.getZYear());
            }
        }
        Integer offset = di.getLocalOffset();
        len = offset == null ? ++len : (offset == 0 ? ++len : (len += IonBinary.lenVarInt(offset.longValue())));
        return len;
    }

    public static int lenIonString(String v) {
        if (v == null) {
            return 0;
        }
        int len = 0;
        for (int ii = 0; ii < v.length(); ++ii) {
            int c = v.charAt(ii);
            if (c < 128) {
                ++len;
                continue;
            }
            if (c >= 55296 && c <= 57343) {
                if (_Private_IonConstants.isHighSurrogate(c)) {
                    if (++ii >= v.length()) {
                        String message = "Text ends with unmatched UTF-16 surrogate " + IonTextUtils.printCodePointAsString(c);
                        throw new IllegalArgumentException(message);
                    }
                    char c2 = v.charAt(ii);
                    if (!_Private_IonConstants.isLowSurrogate(c2)) {
                        String message = "Text contains unmatched UTF-16 high surrogate " + IonTextUtils.printCodePointAsString(c) + " at index " + (ii - 1);
                        throw new IllegalArgumentException(message);
                    }
                    c = _Private_IonConstants.makeUnicodeScalar(c, c2);
                } else if (_Private_IonConstants.isLowSurrogate(c)) {
                    String message = "Text contains unmatched UTF-16 low surrogate " + IonTextUtils.printCodePointAsString(c) + " at index " + ii;
                    throw new IllegalArgumentException(message);
                }
            }
            if (c < 128) {
                ++len;
                continue;
            }
            if (c < 2048) {
                len += 2;
                continue;
            }
            if (c < 65536) {
                len += 3;
                continue;
            }
            if (c <= 0x10FFFF) {
                len += 4;
                continue;
            }
            throw new IllegalArgumentException("invalid string, illegal Unicode scalar (character) encountered");
        }
        return len;
    }

    public static int lenAnnotationListWithLen(String[] annotations, SymbolTable symbolTable) {
        int annotationLen = 0;
        if (annotations != null) {
            for (int ii = 0; ii < annotations.length; ++ii) {
                int symid = symbolTable.findSymbol(annotations[ii]);
                assert (symid > 0);
                annotationLen += IonBinary.lenVarUInt(symid);
            }
            annotationLen += IonBinary.lenVarUInt(annotationLen);
        }
        return annotationLen;
    }

    public static int lenAnnotationListWithLen(ArrayList<Integer> annotations) {
        int annotationLen = 0;
        for (Integer ii : annotations) {
            int symid = ii;
            annotationLen += IonBinary.lenVarUInt(symid);
        }
        annotationLen += IonBinary.lenVarUInt(annotationLen);
        return annotationLen;
    }

    public static int lenIonNullWithTypeDesc() {
        return 1;
    }

    public static int lenIonBooleanWithTypeDesc(Boolean v) {
        return 1;
    }

    public static int lenIonIntWithTypeDesc(Long v) {
        int len = 0;
        if (v != null) {
            long vl = v;
            int vlen = IonBinary.lenIonInt(vl);
            len += vlen;
            len += IonBinary.lenLenFieldWithOptionalNibble(vlen);
        }
        return len + 1;
    }

    public static int lenIonFloatWithTypeDesc(Double v) {
        int len = 0;
        if (v != null) {
            int vlen = IonBinary.lenIonFloat(v);
            len += vlen;
            len += IonBinary.lenLenFieldWithOptionalNibble(vlen);
        }
        return len + 1;
    }

    public static int lenIonDecimalWithTypeDesc(BigDecimal v) {
        int len = 0;
        if (v != null) {
            int vlen = IonBinary.lenIonDecimal(v);
            len += vlen;
            len += IonBinary.lenLenFieldWithOptionalNibble(vlen);
        }
        return len + 1;
    }

    public static int lenIonTimestampWithTypeDesc(Timestamp di) {
        int len = 0;
        if (di != null) {
            int vlen = IonBinary.lenIonTimestamp(di);
            len += vlen;
            len += IonBinary.lenLenFieldWithOptionalNibble(vlen);
        }
        return len + 1;
    }

    public static int lenIonStringWithTypeDesc(String v) {
        int len = 0;
        if (v != null) {
            int vlen = IonBinary.lenIonString(v);
            if (vlen < 0) {
                return -1;
            }
            len += vlen;
            len += IonBinary.lenLenFieldWithOptionalNibble(vlen);
        }
        return len + 1;
    }

    public static int lenIonClobWithTypeDesc(String v) {
        return IonBinary.lenIonStringWithTypeDesc(v);
    }

    public static int lenIonBlobWithTypeDesc(byte[] v) {
        int len = 0;
        if (v != null) {
            int vlen = v.length;
            len += vlen;
            len += IonBinary.lenLenFieldWithOptionalNibble(vlen);
        }
        return len + 1;
    }

    public static BigInteger unsignedLongToBigInteger(int signum, long val) {
        byte[] magnitude = new byte[]{(byte)(val >> 56 & 0xFFL), (byte)(val >> 48 & 0xFFL), (byte)(val >> 40 & 0xFFL), (byte)(val >> 32 & 0xFFL), (byte)(val >> 24 & 0xFFL), (byte)(val >> 16 & 0xFFL), (byte)(val >> 8 & 0xFFL), (byte)(val & 0xFFL)};
        return new BigInteger(signum, magnitude);
    }

    public static void readAll(InputStream in, byte[] buf, int offset, int len) throws IOException {
        int rem = len;
        while (rem > 0) {
            int amount = in.read(buf, offset, rem);
            if (amount <= 0) {
                if (in instanceof Reader) {
                    ((Reader)in).throwUnexpectedEOFException();
                }
                throw new IonException("Unexpected EOF");
            }
            rem -= amount;
            offset += amount;
        }
    }

    public static class PositionMarker {
        int _pos;
        Object _userData;

        public PositionMarker() {
        }

        public PositionMarker(int pos, Object o2) {
            this._pos = pos;
            this._userData = o2;
        }

        public int getPosition() {
            return this._pos;
        }

        public Object getUserData() {
            return this._userData;
        }

        public void setPosition(int pos) {
            this._pos = pos;
        }

        public void setUserData(Object o2) {
            this._userData = o2;
        }
    }

    public static final class Writer
    extends BlockedBuffer.BlockedByteOutputStream {
        Stack<PositionMarker> _pos_stack;
        Stack<Integer> _pending_high_surrogate_stack;
        int _pending_high_surrogate;
        private byte[] numberBuffer = new byte[10];
        byte[] singleCodepointUtf8Buffer = new byte[4];
        static final int stringBufferLen = 128;
        byte[] stringBuffer = new byte[128];
        private static final byte[] negativeZeroBitArray = new byte[]{-128};
        private static final byte[] positiveZeroBitArray = _Private_Utils.EMPTY_BYTE_ARRAY;

        public Writer() {
        }

        public Writer(BlockedBuffer bb) {
            super(bb);
        }

        public Writer(BlockedBuffer bb, int off) {
            super(bb, off);
        }

        public void pushPosition(Object o2) {
            PositionMarker pm = new PositionMarker(this.position(), o2);
            if (this._pos_stack == null) {
                this._pos_stack = new Stack();
                this._pending_high_surrogate_stack = new Stack();
            }
            this._pos_stack.push(pm);
            this._pending_high_surrogate_stack.push(this._pending_high_surrogate);
            this._pending_high_surrogate = 0;
        }

        public PositionMarker popPosition() {
            if (this._pending_high_surrogate != 0) {
                throw new IonException("unmatched high surrogate encountered in input, illegal utf-16 character sequence");
            }
            PositionMarker pm = this._pos_stack.pop();
            this._pending_high_surrogate = this._pending_high_surrogate_stack.pop();
            return pm;
        }

        public void startLongWrite(int hn) throws IOException {
            if (debugValidation) {
                this._validate();
            }
            this.pushLongHeader(hn, 0, false);
            this.writeCommonHeader(hn, 0);
            if (debugValidation) {
                this._validate();
            }
        }

        public void pushLongHeader(int hn, int lownibble, boolean length_follows) {
            lhNode n = new lhNode(hn, lownibble, length_follows);
            this.pushPosition(n);
        }

        public void patchLongHeader(int hn, int lownibble) throws IOException {
            int currpos = this.position();
            if (debugValidation) {
                this._validate();
            }
            PositionMarker pm = this.popPosition();
            lhNode n = (lhNode)pm.getUserData();
            int totallen = currpos - pm.getPosition();
            if (lownibble == -1) {
                lownibble = n._lownibble;
            }
            int writtenValueLen = totallen - 1;
            int len_o_len = IonBinary.lenVarUInt(writtenValueLen);
            if (n._length_follows) {
                assert (hn == 13);
                if (lownibble != 1) {
                    if (writtenValueLen < 14) {
                        lownibble = writtenValueLen;
                        len_o_len = 0;
                    } else {
                        lownibble = 14;
                    }
                    assert (lownibble != 1);
                }
            } else if (writtenValueLen < 14) {
                lownibble = writtenValueLen;
                len_o_len = 0;
            } else {
                lownibble = 14;
            }
            this.setPosition(pm.getPosition());
            int needed = len_o_len;
            if (needed > 0) {
                this.insert(needed);
            }
            this.writeByte(_Private_IonConstants.makeTypeDescriptor(hn, lownibble));
            if (len_o_len > 0) {
                this.writeVarUIntValue(writtenValueLen, true);
            }
            if (needed < 0) {
                this.remove(-needed);
            }
            this.setPosition(currpos + needed);
            if (debugValidation) {
                this._validate();
            }
        }

        public void appendToLongValue(CharSequence chars, boolean onlyByteSizedCharacters) throws IOException {
            if (debugValidation) {
                this._validate();
            }
            int len = chars.length();
            for (int ii = 0; ii < len; ++ii) {
                int c = chars.charAt(ii);
                if (onlyByteSizedCharacters) {
                    if (c > 255) {
                        throw new IonException("escaped character value too large in clob (0 to 255 only)");
                    }
                    this.write((byte)(0xFF & c));
                    continue;
                }
                if (this._pending_high_surrogate != 0) {
                    if ((c & 0xFFFFFC00) != 56320) {
                        throw new IonException("unmatched high surrogate character encountered, invalid utf-16");
                    }
                    c = _Private_IonConstants.makeUnicodeScalar(this._pending_high_surrogate, c);
                    this._pending_high_surrogate = 0;
                } else if ((c & 0xFFFFFC00) == 55296) {
                    if (++ii >= len) {
                        this._pending_high_surrogate = c;
                        break;
                    }
                    char c2 = chars.charAt(ii);
                    if ((c2 & 0xFFFFFC00) != 56320) {
                        throw new IonException("unmatched high surrogate character encountered, invalid utf-16");
                    }
                    c = _Private_IonConstants.makeUnicodeScalar(c, c2);
                } else if ((c & 0xFFFFFC00) == 56320) {
                    throw new IonException("unmatched low surrogate character encountered, invalid utf-16");
                }
                this.writeUnicodeScalarAsUTF8(c);
            }
            if (debugValidation) {
                this._validate();
            }
        }

        final boolean isLongTerminator(int terminator, PushbackReader r) throws IOException {
            int c = r.read();
            if (c != terminator) {
                r.unread(c);
                return false;
            }
            c = r.read();
            if (c != terminator) {
                r.unread(c);
                r.unread(terminator);
                return false;
            }
            return true;
        }

        public void appendToLongValue(int terminator, boolean longstring, boolean onlyByteSizedCharacters, boolean decodeEscapeSequences, PushbackReader r) throws IOException, UnexpectedEofException {
            int c;
            if (debugValidation) {
                if (terminator == -1 && longstring) {
                    throw new IllegalStateException("longstrings have to have a terminator, no eof termination");
                }
                this._validate();
            }
            assert (terminator != 92);
            while (true) {
                if ((c = r.read()) == terminator) {
                    if (!longstring || this.isLongTerminator(terminator, r)) {
                        break;
                    }
                } else {
                    if (c == -1) {
                        throw new UnexpectedEofException();
                    }
                    if (c == 10 || c == 13) {
                        if (terminator != -1 && !longstring) {
                            throw new IonException("unexpected line terminator encountered in quoted string");
                        }
                    } else if (decodeEscapeSequences && c == 92 && (c = IonTokenReader.readEscapedCharacter(r, onlyByteSizedCharacters)) == -2) continue;
                }
                if (onlyByteSizedCharacters) {
                    assert (this._pending_high_surrogate == 0);
                    if ((c & 0xFFFFFF00) != 0) {
                        throw new IonException("escaped character value too large in clob (0 to 255 only)");
                    }
                    this.write((byte)(0xFF & c));
                    continue;
                }
                if (this._pending_high_surrogate != 0) {
                    if ((c & 0xFFFFFC00) != 56320) {
                        String message = "Text contains unmatched UTF-16 high surrogate " + IonTextUtils.printCodePointAsString(this._pending_high_surrogate);
                        throw new IonException(message);
                    }
                    c = _Private_IonConstants.makeUnicodeScalar(this._pending_high_surrogate, c);
                    this._pending_high_surrogate = 0;
                } else if ((c & 0xFFFFFC00) == 55296) {
                    int c2 = r.read();
                    if (c2 == terminator) {
                        if (longstring && this.isLongTerminator(terminator, r)) {
                            this._pending_high_surrogate = c;
                            c = terminator;
                            break;
                        }
                        String message = "Text contains unmatched UTF-16 high surrogate " + IonTextUtils.printCodePointAsString(c);
                        throw new IonException(message);
                    }
                    if (c2 == -1) {
                        throw new UnexpectedEofException();
                    }
                    while (decodeEscapeSequences && c2 == 92 && (c2 = IonTokenReader.readEscapedCharacter(r, onlyByteSizedCharacters)) == -2) {
                        c2 = r.read();
                        if (c2 == terminator) {
                            if (longstring && this.isLongTerminator(terminator, r)) {
                                this._pending_high_surrogate = c;
                                c = c2;
                                break;
                            }
                            String message = "Text contains unmatched UTF-16 high surrogate " + IonTextUtils.printCodePointAsString(c);
                            throw new IonException(message);
                        }
                        if (c2 != -1) continue;
                        throw new UnexpectedEofException();
                    }
                    if (this._pending_high_surrogate != 0) break;
                    if ((c2 & 0xFFFFFC00) != 56320) {
                        String message = "Text contains unmatched UTF-16 high surrogate " + IonTextUtils.printCodePointAsString(c);
                        throw new IonException(message);
                    }
                    c = _Private_IonConstants.makeUnicodeScalar(c, c2);
                } else if ((c & 0xFFFFFC00) == 56320) {
                    String message = "Text contains unmatched UTF-16 low surrogate " + IonTextUtils.printCodePointAsString(c);
                    throw new IonException(message);
                }
                this.writeUnicodeScalarAsUTF8(c);
            }
            if (c != terminator) {
                throw new UnexpectedEofException();
            }
            if (debugValidation) {
                this._validate();
            }
        }

        public int writeVarUIntValue(long value, boolean force_zero_write) throws IOException {
            assert (value >= 0L);
            int len = 0;
            if (value == 0L) {
                if (force_zero_write) {
                    this.write(-128);
                    len = 1;
                }
            } else {
                int i = this.numberBuffer.length;
                while (value > 0L) {
                    this.numberBuffer[--i] = (byte)(value & 0x7FL);
                    value >>>= 7;
                }
                int n = this.numberBuffer.length - 1;
                this.numberBuffer[n] = (byte)(this.numberBuffer[n] | 0x80);
                len = this.numberBuffer.length - i;
                this.write(this.numberBuffer, i, len);
            }
            return len;
        }

        public int writeUIntValue(long value) throws IOException {
            int i = this.numberBuffer.length;
            while (value != 0L) {
                this.numberBuffer[--i] = (byte)(value & 0xFFL);
                value >>>= 8;
            }
            int len = this.numberBuffer.length - i;
            this.write(this.numberBuffer, i, len);
            return len;
        }

        public int writeUIntValue(long value, int len) throws IOException {
            int i = this.numberBuffer.length;
            for (int j = 0; j < len; ++j) {
                this.numberBuffer[--i] = (byte)(value & 0xFFL);
                value >>>= 8;
            }
            this.write(this.numberBuffer, i, len);
            return len;
        }

        public int writeUIntValue(BigInteger value, int len) throws IOException {
            int returnlen = 0;
            int signum = value.signum();
            if (signum != 0) {
                if (signum < 0) {
                    throw new IllegalArgumentException("value must be greater than or equal to 0");
                }
                if (value.compareTo(MAX_LONG_VALUE) == -1) {
                    long lvalue = value.longValue();
                    returnlen = this.writeUIntValue(lvalue, len);
                } else {
                    int offset;
                    assert (signum > 0);
                    byte[] bits = value.toByteArray();
                    for (offset = 0; offset < bits.length && bits[offset] == 0; ++offset) {
                    }
                    int bitlen = bits.length - offset;
                    this.write(bits, offset, bitlen);
                    returnlen += bitlen;
                }
            }
            assert (returnlen == len);
            return len;
        }

        public int writeVarIntValue(long value, boolean force_zero_write) throws IOException {
            int len = 0;
            if (value == 0L) {
                if (force_zero_write) {
                    this.write(-128);
                    len = 1;
                }
            } else {
                boolean negative;
                int i = this.numberBuffer.length;
                boolean bl = negative = value < 0L;
                if (negative) {
                    value = -value;
                }
                while (value > 0L) {
                    this.numberBuffer[--i] = (byte)(value & 0x7FL);
                    value >>>= 7;
                }
                int n = this.numberBuffer.length - 1;
                this.numberBuffer[n] = (byte)(this.numberBuffer[n] | 0x80);
                if ((this.numberBuffer[i] & 0x40) == 64) {
                    this.numberBuffer[--i] = 0;
                }
                if (negative) {
                    int n2 = i;
                    this.numberBuffer[n2] = (byte)(this.numberBuffer[n2] | 0x40);
                }
                len = this.numberBuffer.length - i;
                this.write(this.numberBuffer, i, len);
            }
            return len;
        }

        public int writeIntValue(long value) throws IOException {
            boolean negative;
            int i = this.numberBuffer.length;
            boolean bl = negative = value < 0L;
            if (negative) {
                value = -value;
            }
            while (value > 0L) {
                this.numberBuffer[--i] = (byte)(value & 0xFFL);
                value >>>= 8;
            }
            if ((this.numberBuffer[i] & 0x80) == 64) {
                this.numberBuffer[--i] = 0;
            }
            if (negative) {
                int n = i;
                this.numberBuffer[n] = (byte)(this.numberBuffer[n] | 0x80);
            }
            int len = this.numberBuffer.length - i;
            this.write(this.numberBuffer, i, len);
            return len;
        }

        public int writeFloatValue(double d) throws IOException {
            if (Double.valueOf(d).equals(DOUBLE_POS_ZERO)) {
                return 0;
            }
            long dBits = Double.doubleToRawLongBits(d);
            return this.writeUIntValue(dBits, 8);
        }

        public int writeUnicodeScalarAsUTF8(int c) throws IOException {
            int len;
            if (c < 128) {
                len = 1;
                this.start_write();
                this._write((byte)(c & 0xFF));
                this.end_write();
            } else {
                len = this._writeUnicodeScalarToByteBuffer(c, this.singleCodepointUtf8Buffer, 0);
                this.write(this.singleCodepointUtf8Buffer, 0, len);
            }
            return len;
        }

        private final int _writeUnicodeScalarToByteBuffer(int c, byte[] buffer, int offset) throws IOException {
            int len = -1;
            assert (offset + 4 <= buffer.length);
            if (c < 2048) {
                buffer[offset] = (byte)(0xFF & (0xC0 | c >> 6));
                buffer[++offset] = (byte)(0xFF & (0x80 | c & 0x3F));
                len = 2;
            } else if (c < 65536) {
                if (c > 55295 && c < 57344) {
                    this.throwUTF8Exception();
                }
                buffer[offset] = (byte)(0xFF & (0xE0 | c >> 12));
                buffer[++offset] = (byte)(0xFF & (0x80 | c >> 6 & 0x3F));
                buffer[++offset] = (byte)(0xFF & (0x80 | c & 0x3F));
                len = 3;
            } else if (c <= 0x10FFFF) {
                buffer[offset] = (byte)(0xFF & (0xF0 | c >> 18));
                buffer[++offset] = (byte)(0xFF & (0x80 | c >> 12 & 0x3F));
                buffer[++offset] = (byte)(0xFF & (0x80 | c >> 6 & 0x3F));
                buffer[++offset] = (byte)(0xFF & (0x80 | c & 0x3F));
                len = 4;
            } else {
                this.throwUTF8Exception();
            }
            return len;
        }

        public int writeByte(_Private_IonConstants.HighNibble hn, int len) throws IOException {
            if (len < 0) {
                throw new IonException("negative token length encountered");
            }
            if (len > 13) {
                len = 14;
            }
            int t = _Private_IonConstants.makeTypeDescriptor(hn.value(), len);
            this.write(t);
            return 1;
        }

        public int writeByte(byte b) throws IOException {
            this.write(b);
            return 1;
        }

        public int writeByte(int b) throws IOException {
            this.write(b);
            return 1;
        }

        public int writeAnnotations(SymbolToken[] annotations, SymbolTable symbolTable) throws IOException {
            int sid;
            int ii;
            int startPosition = this.position();
            int annotationLen = 0;
            for (ii = 0; ii < annotations.length; ++ii) {
                sid = annotations[ii].getSid();
                assert (sid != -1);
                annotationLen += IonBinary.lenVarUInt(sid);
            }
            this.writeVarUIntValue(annotationLen, true);
            for (ii = 0; ii < annotations.length; ++ii) {
                sid = annotations[ii].getSid();
                this.writeVarUIntValue(sid, true);
            }
            return this.position() - startPosition;
        }

        public int writeAnnotations(ArrayList<Integer> annotations) throws IOException {
            int startPosition = this.position();
            int annotationLen = 0;
            for (Integer ii : annotations) {
                annotationLen += IonBinary.lenVarUInt(ii.intValue());
            }
            this.writeVarUIntValue(annotationLen, true);
            for (Integer ii : annotations) {
                this.writeVarUIntValue(ii.intValue(), true);
            }
            return this.position() - startPosition;
        }

        public void writeStubStructHeader(int hn, int ln) throws IOException {
            this.writeByte(_Private_IonConstants.makeTypeDescriptor(hn, ln));
        }

        public int writeCommonHeader(int hn, int len) throws IOException {
            int returnlen = 0;
            if (len < 14) {
                returnlen += this.writeByte(_Private_IonConstants.makeTypeDescriptor(hn, len));
            } else {
                returnlen += this.writeByte(_Private_IonConstants.makeTypeDescriptor(hn, 14));
                returnlen += this.writeVarUIntValue(len, false);
            }
            return returnlen;
        }

        public int writeSymbolWithTD(int sid) throws IOException {
            assert (sid > 0);
            int vlen = IonBinary.lenUInt(sid);
            int len = this.writeCommonHeader(7, vlen);
            return len += this.writeUIntValue(sid, vlen);
        }

        public int writeStringWithTD(String s) throws IOException {
            int len = IonBinary.lenIonString(s);
            if (len < 0) {
                this.throwUTF8Exception();
            }
            len += this.writeCommonHeader(8, len);
            this.writeStringData(s);
            return len;
        }

        public int writeStringData(String s) throws IOException {
            int len = 0;
            int bufPos = 0;
            for (int ii = 0; ii < s.length(); ++ii) {
                int c = s.charAt(ii);
                if (bufPos > 124) {
                    this.write(this.stringBuffer, 0, bufPos);
                    bufPos = 0;
                }
                if (c < 128) {
                    this.stringBuffer[bufPos++] = (byte)c;
                    ++len;
                    continue;
                }
                if (c >= 55296 && c <= 57343) {
                    if (_Private_IonConstants.isHighSurrogate(c)) {
                        if (++ii >= s.length()) {
                            throw new IllegalArgumentException("invalid string, unpaired high surrogate character");
                        }
                        char c2 = s.charAt(ii);
                        if (!_Private_IonConstants.isLowSurrogate(c2)) {
                            throw new IllegalArgumentException("invalid string, unpaired high surrogate character");
                        }
                        c = _Private_IonConstants.makeUnicodeScalar(c, c2);
                    } else if (_Private_IonConstants.isLowSurrogate(c)) {
                        throw new IllegalArgumentException("invalid string, unpaired low surrogate character");
                    }
                }
                int utf8len = this._writeUnicodeScalarToByteBuffer(c, this.stringBuffer, bufPos);
                bufPos += utf8len;
                len += utf8len;
            }
            if (bufPos > 0) {
                this.write(this.stringBuffer, 0, bufPos);
            }
            return len;
        }

        public int writeNullWithTD(_Private_IonConstants.HighNibble hn) throws IOException {
            this.writeByte(hn, 15);
            return 1;
        }

        public int writeTimestampWithTD(Timestamp di) throws IOException {
            int returnlen;
            if (di == null) {
                returnlen = this.writeCommonHeader(6, 15);
            } else {
                int vlen = IonBinary.lenIonTimestamp(di);
                returnlen = this.writeCommonHeader(6, vlen);
                int wroteLen = this.writeTimestamp(di);
                assert (wroteLen == vlen);
                returnlen += wroteLen;
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
                this.write(-64);
                ++returnlen;
            } else {
                returnlen += this.writeVarIntValue(offset.intValue(), true);
            }
            if (precision.includes(Timestamp.Precision.YEAR)) {
                returnlen += this.writeVarUIntValue(di.getZYear(), true);
            }
            if (precision.includes(Timestamp.Precision.MONTH)) {
                returnlen += this.writeVarUIntValue(di.getZMonth(), true);
            }
            if (precision.includes(Timestamp.Precision.DAY)) {
                returnlen += this.writeVarUIntValue(di.getZDay(), true);
            }
            if (precision.includes(Timestamp.Precision.MINUTE)) {
                returnlen += this.writeVarUIntValue(di.getZHour(), true);
                returnlen += this.writeVarUIntValue(di.getZMinute(), true);
            }
            if (precision.includes(Timestamp.Precision.SECOND)) {
                returnlen += this.writeVarUIntValue(di.getZSecond(), true);
                BigDecimal fraction = di.getZFractionalSecond();
                if (fraction != null) {
                    assert (!fraction.equals(BigDecimal.ZERO));
                    returnlen += this.writeDecimalContent(fraction);
                }
            }
            return returnlen;
        }

        public int writeDecimalWithTD(BigDecimal bd) throws IOException {
            int returnlen;
            if (bd == null) {
                returnlen = this.writeByte(NULL_DECIMAL_TYPEDESC);
            } else if (IonBinary.isNibbleZero(bd)) {
                returnlen = this.writeByte(ZERO_DECIMAL_TYPEDESC);
            } else {
                int len = IonBinary.lenIonDecimal(bd);
                if (len < 14) {
                    returnlen = this.writeByte(_Private_IonConstants.makeTypeDescriptor(5, len));
                } else {
                    returnlen = this.writeByte(_Private_IonConstants.makeTypeDescriptor(5, 14));
                    this.writeVarIntValue(len, false);
                }
                int wroteDecimalLen = this.writeDecimalContent(bd);
                assert (wroteDecimalLen == len);
                returnlen += wroteDecimalLen;
            }
            return returnlen;
        }

        public int writeDecimalContent(BigDecimal bd) throws IOException {
            byte[] mantissaBits;
            if (bd == null) {
                return 0;
            }
            if (IonBinary.isNibbleZero(bd)) {
                return 0;
            }
            int exponent = -bd.scale();
            int returnlen = this.writeVarIntValue(exponent, true);
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
            this.write(mantissaBits, 0, mantissaBits.length);
            return returnlen += mantissaBits.length;
        }

        void throwUTF8Exception() {
            this.throwException("Invalid UTF-8 character encounter in a string at pos " + this.position());
        }

        void throwException(String s) {
            throw new BlockedBuffer.BlockedBufferException(s);
        }

        static class lhNode {
            int _hn;
            int _lownibble;
            boolean _length_follows;

            lhNode(int hn, int lownibble, boolean length_follows) {
                this._hn = hn;
                this._lownibble = lownibble;
                this._length_follows = length_follows;
            }
        }
    }

    public static final class Reader
    extends BlockedBuffer.BlockedByteInputStream {
        public Reader(BlockedBuffer bb) {
            super(bb);
        }

        public Reader(BlockedBuffer bb, int pos) {
            super(bb, pos);
        }

        public byte[] getBytes() throws IOException {
            if (this._buf == null) {
                return null;
            }
            this.sync();
            this.setPosition(0);
            int len = this._buf.size();
            byte[] buf = new byte[len];
            if (_Private_Utils.readFully(this, buf) != len) {
                throw new UnexpectedEofException();
            }
            return buf;
        }

        public int readToken() throws UnexpectedEofException, IOException {
            int c = this.read();
            if (c < 0) {
                this.throwUnexpectedEOFException();
            }
            return c;
        }

        public int readActualTypeDesc() throws IOException {
            int lownibble;
            int typeid;
            int c = this.read();
            if (c < 0) {
                this.throwUnexpectedEOFException();
            }
            if ((typeid = _Private_IonConstants.getTypeCode(c)) == 14 && (lownibble = _Private_IonConstants.getLowNibble(c)) != 0) {
                this.readLength(typeid, lownibble);
                for (int alen = this.readVarIntAsInt(); alen > 0; --alen) {
                    if (this.read() >= 0) continue;
                    this.throwUnexpectedEOFException();
                }
                c = this.read();
                if (c < 0) {
                    this.throwUnexpectedEOFException();
                }
            }
            return c;
        }

        public int[] readAnnotations() throws IOException {
            int[] annotations = null;
            int annotationLen = this.readVarUIntAsInt();
            int annotationPos = this.position();
            int annotationEnd = annotationPos + annotationLen;
            int annotationCount = 0;
            while (this.position() < annotationEnd) {
                this.readVarUIntAsInt();
                ++annotationCount;
            }
            if (annotationCount > 0) {
                annotations = new int[annotationCount];
                int annotationIdx = 0;
                this.setPosition(annotationPos);
                while (this.position() < annotationEnd) {
                    int sid = this.readVarUIntAsInt();
                    annotations[annotationIdx++] = sid;
                }
            }
            return annotations;
        }

        public int readLength(int td, int ln) throws IOException {
            switch (td) {
                case 0: 
                case 1: {
                    return 0;
                }
                case 2: 
                case 3: 
                case 4: 
                case 5: 
                case 6: 
                case 7: 
                case 8: 
                case 9: 
                case 10: 
                case 11: 
                case 12: 
                case 14: {
                    switch (ln) {
                        case 0: 
                        case 15: {
                            return 0;
                        }
                        case 14: {
                            return this.readVarUIntAsInt();
                        }
                    }
                    return ln;
                }
                case 99: {
                    switch (ln) {
                        case 14: {
                            return this.readVarUIntAsInt();
                        }
                    }
                    return ln;
                }
                case 13: {
                    switch (ln) {
                        case 0: 
                        case 15: {
                            return 0;
                        }
                        case 1: 
                        case 14: {
                            return this.readVarUIntAsInt();
                        }
                    }
                    return ln;
                }
            }
            throw new BlockedBuffer.BlockedBufferException("invalid type id encountered: " + td);
        }

        public long readIntAsLong(int len) throws IOException {
            long retvalue = 0L;
            boolean is_negative = false;
            if (len > 0) {
                int b = this.read();
                if (b < 0) {
                    this.throwUnexpectedEOFException();
                }
                retvalue = b & 0x7F;
                is_negative = (b & 0x80) != 0;
                switch (len - 1) {
                    case 8: {
                        b = this.read();
                        if (b < 0) {
                            this.throwUnexpectedEOFException();
                        }
                        retvalue = retvalue << 8 | (long)b;
                    }
                    case 7: {
                        b = this.read();
                        if (b < 0) {
                            this.throwUnexpectedEOFException();
                        }
                        retvalue = retvalue << 8 | (long)b;
                    }
                    case 6: {
                        b = this.read();
                        if (b < 0) {
                            this.throwUnexpectedEOFException();
                        }
                        retvalue = retvalue << 8 | (long)b;
                    }
                    case 5: {
                        b = this.read();
                        if (b < 0) {
                            this.throwUnexpectedEOFException();
                        }
                        retvalue = retvalue << 8 | (long)b;
                    }
                    case 4: {
                        b = this.read();
                        if (b < 0) {
                            this.throwUnexpectedEOFException();
                        }
                        retvalue = retvalue << 8 | (long)b;
                    }
                    case 3: {
                        b = this.read();
                        if (b < 0) {
                            this.throwUnexpectedEOFException();
                        }
                        retvalue = retvalue << 8 | (long)b;
                    }
                    case 2: {
                        b = this.read();
                        if (b < 0) {
                            this.throwUnexpectedEOFException();
                        }
                        retvalue = retvalue << 8 | (long)b;
                    }
                    case 1: {
                        b = this.read();
                        if (b < 0) {
                            this.throwUnexpectedEOFException();
                        }
                        retvalue = retvalue << 8 | (long)b;
                    }
                }
                if (is_negative) {
                    retvalue = -retvalue;
                }
            }
            return retvalue;
        }

        @Deprecated
        public int readIntAsInt(int len) throws IOException {
            int retvalue = 0;
            boolean is_negative = false;
            if (len > 0) {
                int b = this.read();
                if (b < 0) {
                    this.throwUnexpectedEOFException();
                }
                retvalue = b & 0x7F;
                is_negative = (b & 0x80) != 0;
                switch (len - 1) {
                    case 4: 
                    case 5: 
                    case 6: 
                    case 7: {
                        throw new IonException("overflow attempt to read long value into an int");
                    }
                    case 3: {
                        b = this.read();
                        if (b < 0) {
                            this.throwUnexpectedEOFException();
                        }
                        retvalue = retvalue << 8 | b;
                    }
                    case 2: {
                        b = this.read();
                        if (b < 0) {
                            this.throwUnexpectedEOFException();
                        }
                        retvalue = retvalue << 8 | b;
                        b = this.read();
                        if (b < 0) {
                            this.throwUnexpectedEOFException();
                        }
                        retvalue = retvalue << 8 | b;
                    }
                }
                if (is_negative) {
                    retvalue = -retvalue;
                }
            }
            return retvalue;
        }

        public BigInteger readUIntAsBigInteger(int len, int signum) throws IOException {
            byte[] magnitude = new byte[len];
            for (int i = 0; i < len; ++i) {
                int octet = this.read();
                if (octet < 0) {
                    this.throwUnexpectedEOFException();
                }
                magnitude[i] = (byte)octet;
            }
            return new BigInteger(signum, magnitude);
        }

        public long readUIntAsLong(int len) throws IOException {
            long retvalue = 0L;
            switch (len) {
                default: {
                    throw new IonException("overflow attempt to read long value into an int");
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

        public int readUIntAsInt(int len) throws IOException {
            int retvalue = 0;
            switch (len) {
                default: {
                    throw new IonException("overflow attempt to read long value into an int");
                }
                case 4: {
                    int b = this.read();
                    if (b < 0) {
                        this.throwUnexpectedEOFException();
                    }
                    retvalue = b;
                }
                case 3: {
                    int b = this.read();
                    if (b < 0) {
                        this.throwUnexpectedEOFException();
                    }
                    retvalue = retvalue << 8 | b;
                }
                case 2: {
                    int b = this.read();
                    if (b < 0) {
                        this.throwUnexpectedEOFException();
                    }
                    retvalue = retvalue << 8 | b;
                }
                case 1: {
                    int b = this.read();
                    if (b < 0) {
                        this.throwUnexpectedEOFException();
                    }
                    retvalue = retvalue << 8 | b;
                }
                case 0: 
            }
            return retvalue;
        }

        public long readVarUIntAsLong() throws IOException {
            int b;
            long retvalue = 0L;
            do {
                if ((b = this.read()) < 0) {
                    this.throwUnexpectedEOFException();
                }
                if ((retvalue & 0xFE00000000000000L) != 0L) {
                    throw new IonException("overflow attempt to read long value into a long");
                }
                retvalue = retvalue << 7 | (long)(b & 0x7F);
            } while ((b & 0x80) == 0);
            return retvalue;
        }

        public int readVarUIntAsInt() throws IOException {
            int retvalue = 0;
            int b = this.read();
            if (b < 0) {
                this.throwUnexpectedEOFException();
            }
            retvalue = retvalue << 7 | b & 0x7F;
            if ((b & 0x80) == 0) {
                b = this.read();
                if (b < 0) {
                    this.throwUnexpectedEOFException();
                }
                retvalue = retvalue << 7 | b & 0x7F;
                if ((b & 0x80) == 0) {
                    b = this.read();
                    if (b < 0) {
                        this.throwUnexpectedEOFException();
                    }
                    retvalue = retvalue << 7 | b & 0x7F;
                    if ((b & 0x80) == 0) {
                        b = this.read();
                        if (b < 0) {
                            this.throwUnexpectedEOFException();
                        }
                        retvalue = retvalue << 7 | b & 0x7F;
                        if ((b & 0x80) == 0) {
                            b = this.read();
                            if (b < 0) {
                                this.throwUnexpectedEOFException();
                            }
                            retvalue = retvalue << 7 | b & 0x7F;
                            if ((b & 0x80) == 0) {
                                throw new IonException("var int overflow at: " + this.position());
                            }
                        }
                    }
                }
            }
            return retvalue;
        }

        public long readVarIntAsLong() throws IOException {
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
                            throw new IonException("overflow attempt to read long value into a long");
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

        public int readVarIntAsInt() throws IOException {
            int retvalue = 0;
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
                retvalue = retvalue << 7 | b & 0x7F;
                if ((b & 0x80) == 0) {
                    b = this.read();
                    if (b < 0) {
                        this.throwUnexpectedEOFException();
                    }
                    retvalue = retvalue << 7 | b & 0x7F;
                    if ((b & 0x80) == 0) {
                        b = this.read();
                        if (b < 0) {
                            this.throwUnexpectedEOFException();
                        }
                        retvalue = retvalue << 7 | b & 0x7F;
                        if ((b & 0x80) == 0) {
                            b = this.read();
                            if (b < 0) {
                                this.throwUnexpectedEOFException();
                            }
                            retvalue = retvalue << 7 | b & 0x7F;
                            if ((b & 0x80) == 0) {
                                throw new IonException("var int overflow at: " + this.position());
                            }
                        }
                    }
                }
            }
            if (is_negative) {
                retvalue = -retvalue;
            }
            return retvalue;
        }

        public Integer readVarIntWithNegativeZero() throws IOException {
            int retvalue = 0;
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
                retvalue = retvalue << 7 | b & 0x7F;
                if ((b & 0x80) == 0) {
                    b = this.read();
                    if (b < 0) {
                        this.throwUnexpectedEOFException();
                    }
                    retvalue = retvalue << 7 | b & 0x7F;
                    if ((b & 0x80) == 0) {
                        b = this.read();
                        if (b < 0) {
                            this.throwUnexpectedEOFException();
                        }
                        retvalue = retvalue << 7 | b & 0x7F;
                        if ((b & 0x80) == 0) {
                            b = this.read();
                            if (b < 0) {
                                this.throwUnexpectedEOFException();
                            }
                            retvalue = retvalue << 7 | b & 0x7F;
                            if ((b & 0x80) == 0) {
                                throw new IonException("var int overflow at: " + this.position());
                            }
                        }
                    }
                }
            }
            Integer retInteger = null;
            if (is_negative) {
                if (retvalue != 0) {
                    retvalue = -retvalue;
                    retInteger = retvalue;
                }
            } else {
                retInteger = retvalue;
            }
            return retInteger;
        }

        public double readFloatValue(int len) throws IOException {
            if (len == 0) {
                return 0.0;
            }
            if (len != 8) {
                throw new IonException("Length of float read must be 0 or 8");
            }
            long dBits = this.readUIntAsLong(len);
            return Double.longBitsToDouble(dBits);
        }

        public Decimal readDecimalValue(int len) throws IOException {
            Decimal bd;
            MathContext mathContext = MathContext.UNLIMITED;
            if (len == 0) {
                bd = Decimal.valueOf(0, mathContext);
            } else {
                BigInteger value;
                int signum;
                int startpos = this.position();
                int exponent = this.readVarIntAsInt();
                int bitlen = len - (this.position() - startpos);
                if (bitlen > 0) {
                    byte[] bits = new byte[bitlen];
                    IonBinary.readAll(this, bits, 0, bitlen);
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

        public Timestamp readTimestampValue(int len) throws IOException {
            if (len < 1) {
                return null;
            }
            int year2 = 0;
            int month = 0;
            int day = 0;
            int hour = 0;
            int minute = 0;
            int second = 0;
            Decimal frac = null;
            int end = this.position() + len;
            Integer offset = this.readVarIntWithNegativeZero();
            year2 = this.readVarUIntAsInt();
            Timestamp.Precision p = Timestamp.Precision.YEAR;
            if (this.position() < end) {
                month = this.readVarUIntAsInt();
                p = Timestamp.Precision.MONTH;
                if (this.position() < end) {
                    day = this.readVarUIntAsInt();
                    p = Timestamp.Precision.DAY;
                    if (this.position() < end) {
                        hour = this.readVarUIntAsInt();
                        minute = this.readVarUIntAsInt();
                        p = Timestamp.Precision.MINUTE;
                        if (this.position() < end) {
                            second = this.readVarUIntAsInt();
                            p = Timestamp.Precision.SECOND;
                            int remaining = end - this.position();
                            if (remaining > 0) {
                                frac = this.readDecimalValue(remaining);
                            }
                        }
                    }
                }
            }
            try {
                Timestamp val = Timestamp.createFromUtcFields(p, year2, month, day, hour, minute, second, frac, offset);
                return val;
            } catch (IllegalArgumentException e) {
                throw new IonException(e.getMessage() + " at: " + this.position());
            }
        }

        public String readString(int len) throws IOException {
            char[] cb = new char[len];
            int ii = 0;
            int endPosition = this.position() + len;
            while (this.position() < endPosition) {
                int c = this.readUnicodeScalar();
                if (c < 0) {
                    this.throwUnexpectedEOFException();
                }
                if (c < 65536) {
                    cb[ii++] = (char)c;
                    continue;
                }
                cb[ii++] = (char)_Private_IonConstants.makeHighSurrogate(c);
                cb[ii++] = (char)_Private_IonConstants.makeLowSurrogate(c);
            }
            if (this.position() < endPosition) {
                this.throwUnexpectedEOFException();
            }
            return new String(cb, 0, ii);
        }

        public int readUnicodeScalar() throws IOException {
            int c = -1;
            int b = this.read();
            if (b < 0) {
                return -1;
            }
            if ((b & 0x80) == 0) {
                return b;
            }
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
                    throw new IonException("illegal surrgate value encountered in input utf-8 stream");
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
                    throw new IonException("illegal surrgate value encountered in input utf-8 stream");
                }
            } else {
                this.throwUTF8Exception();
            }
            return c;
        }

        void throwUTF8Exception() {
            throw new IonException("Invalid UTF-8 character encounter in a string at pos " + this.position());
        }

        void throwUnexpectedEOFException() {
            throw new BlockedBuffer.BlockedBufferException("unexpected EOF in value at offset " + this.position());
        }

        public String readString() throws IOException {
            int td = this.read();
            if (_Private_IonConstants.getTypeCode(td) != 8) {
                throw new IonException("readString helper only works for string(7) not " + (td >> 4 & 0xF));
            }
            int len = td & 0xF;
            if (len == 15) {
                return null;
            }
            if (len == 14) {
                len = this.readVarUIntAsInt();
            }
            return this.readString(len);
        }

        boolean skipThroughNopPad() throws IOException {
            int originalPosition = this._pos;
            int c = this.read();
            boolean hasTypedecl = _Private_IonConstants.getTypeCode(c) == 14;
            this.setPosition(originalPosition);
            int typeDesc = this.readActualTypeDesc();
            int tid = _Private_IonConstants.getTypeCode(typeDesc);
            int len = _Private_IonConstants.getLowNibble(typeDesc);
            if (tid == 0 && len != 15) {
                if (hasTypedecl) {
                    throw new IonException("NOP padding is not allowed within annotation wrappers.");
                }
                int toSkip = this.readLength(99, len);
                long skipped = this.skip(toSkip);
                if (toSkip > 0 && (long)toSkip != skipped) {
                    throw new IonException("Nop pad too short declared length: " + toSkip + " pad actual size: " + skipped);
                }
                return true;
            }
            this.setPosition(originalPosition);
            return false;
        }
    }

    public static class BufferManager {
        BlockedBuffer _buf;
        Reader _reader;
        Writer _writer;

        public BufferManager() {
            this._buf = new BlockedBuffer();
            this.openReader();
            this.openWriter();
        }

        public BufferManager(BlockedBuffer buf) {
            this._buf = buf;
            this.openReader();
            this.openWriter();
        }

        public BufferManager(InputStream bytestream) {
            this();
            try {
                this._writer.write(bytestream);
            } catch (IOException e) {
                throw new IonException(e);
            }
        }

        public BufferManager(InputStream bytestream, int len) {
            this();
            try {
                this._writer.write(bytestream, len);
            } catch (IOException e) {
                throw new IonException(e);
            }
        }

        public BufferManager clone() throws CloneNotSupportedException {
            BlockedBuffer buffer_clone = this._buf.clone();
            BufferManager clone = new BufferManager(buffer_clone);
            return clone;
        }

        public Reader openReader() {
            if (this._reader == null) {
                this._reader = new Reader(this._buf);
            }
            return this._reader;
        }

        public Writer openWriter() {
            if (this._writer == null) {
                this._writer = new Writer(this._buf);
            }
            return this._writer;
        }

        public BlockedBuffer buffer() {
            return this._buf;
        }

        public Reader reader() {
            return this._reader;
        }

        public Writer writer() {
            return this._writer;
        }

        public Reader reader(int pos) throws IOException {
            this._reader.setPosition(pos);
            return this._reader;
        }

        public Writer writer(int pos) throws IOException {
            this._writer.setPosition(pos);
            return this._writer;
        }

        public static BufferManager makeReadManager(BlockedBuffer buf) {
            BufferManager bufmngr = new BufferManager(buf);
            bufmngr.openReader();
            return bufmngr;
        }

        public static BufferManager makeReadWriteManager(BlockedBuffer buf) {
            BufferManager bufmngr = new BufferManager(buf);
            bufmngr.openReader();
            bufmngr.openWriter();
            return bufmngr;
        }
    }
}

