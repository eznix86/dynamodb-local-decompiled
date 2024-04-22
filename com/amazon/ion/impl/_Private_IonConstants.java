/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazon.ion.impl;

import com.amazon.ion.IonException;

public final class _Private_IonConstants {
    public static final int ARRAY_MAXIMUM_SIZE = 0x7FFFFFF7;
    public static final int BB_TOKEN_LEN = 1;
    public static final int BB_VAR_INT32_LEN_MAX = 5;
    public static final int BB_VAR_INT64_LEN_MAX = 10;
    public static final int BB_INT64_LEN_MAX = 8;
    public static final int BB_VAR_LEN_MIN = 1;
    public static final int BB_MAX_7BIT_INT = 127;
    public static final int INT32_SIZE = 4;
    public static final int MAX_LONG_TEXT_SIZE = Math.max(Long.toString(Long.MAX_VALUE).length(), Long.toString(Long.MIN_VALUE).length());
    public static final int high_surrogate_value = 55296;
    public static final int low_surrogate_value = 56320;
    public static final int surrogate_mask = -1024;
    public static final int surrogate_value_mask = 1023;
    public static final int surrogate_utf32_offset = 65536;
    public static final int surrogate_utf32_shift = 10;
    public static final byte[] BINARY_VERSION_MARKER_1_0 = new byte[]{-32, 1, 0, -22};
    public static final int BINARY_VERSION_MARKER_SIZE = BINARY_VERSION_MARKER_1_0.length;
    public static final int tidNull = 0;
    public static final int tidBoolean = 1;
    public static final int tidPosInt = 2;
    public static final int tidNegInt = 3;
    public static final int tidFloat = 4;
    public static final int tidDecimal = 5;
    public static final int tidTimestamp = 6;
    public static final int tidSymbol = 7;
    public static final int tidString = 8;
    public static final int tidClob = 9;
    public static final int tidBlob = 10;
    public static final int tidList = 11;
    public static final int tidSexp = 12;
    public static final int tidStruct = 13;
    public static final int tidTypedecl = 14;
    public static final int tidUnused = 15;
    public static final int tidDATAGRAM = 16;
    public static final int tidNopPad = 99;
    public static final int lnIsNull = 15;
    public static final int lnIsNullAtom = 15;
    public static final int lnIsNullSequence = 15;
    public static final int lnIsNullStruct = 15;
    public static final int lnIsEmptyContainer = 0;
    public static final int lnIsOrderedStruct = 1;
    public static final int lnIsVarLen = 14;
    public static final int lnBooleanTrue = 1;
    public static final int lnBooleanFalse = 0;
    public static final int lnNumericZero = 0;
    public static final int True = _Private_IonConstants.makeTypeDescriptor(1, 1);
    public static final int False = _Private_IonConstants.makeTypeDescriptor(1, 0);
    public static final String UNKNOWN_SYMBOL_TEXT_PREFIX = " -- UNKNOWN SYMBOL TEXT -- $";

    private _Private_IonConstants() {
    }

    public static final int makeUnicodeScalar(int high_surrogate, int low_surrogate) {
        int c = (high_surrogate & 0x3FF) << 10;
        c |= low_surrogate & 0x3FF;
        return c += 65536;
    }

    public static final int makeHighSurrogate(int unicodeScalar) {
        int c = unicodeScalar - 65536;
        c >>>= 10;
        return c |= 0xD800;
    }

    public static final int makeLowSurrogate(int unicodeScalar) {
        int c = unicodeScalar - 65536;
        c &= 0x3FF;
        return c |= 0xDC00;
    }

    public static final boolean isHighSurrogate(int c) {
        boolean is = (c & 0xFFFFFC00) == 55296;
        return is;
    }

    public static final boolean isLowSurrogate(int c) {
        boolean is = (c & 0xFFFFFC00) == 56320;
        return is;
    }

    public static final boolean isSurrogate(int c) {
        boolean is = (c & 0xFFFFFC00) == 55296;
        return is;
    }

    public static final int makeTypeDescriptor(int highNibble, int lowNibble) {
        assert (highNibble == (highNibble & 0xF));
        assert (lowNibble == (lowNibble & 0xF));
        return highNibble << 4 | lowNibble;
    }

    public static final int getTypeCode(int td) {
        assert (td >= 0 && td <= 255);
        return td >> 4;
    }

    public static final int getLowNibble(int td) {
        return td & 0xF;
    }

    public static enum HighNibble {
        hnNull(0, false, false),
        hnBoolean(1, false, false),
        hnPosInt(2, false, false),
        hnNegInt(3, false, false),
        hnFloat(4, false, false),
        hnDecimal(5, false, false),
        hnTimestamp(6, false, false),
        hnSymbol(7, false, false),
        hnString(8, false, false),
        hnClob(9, false, false),
        hnBlob(10, false, false),
        hnList(11, true, true),
        hnSexp(12, true, true),
        hnStruct(13, true, true),
        hnTypedecl(14, false, false),
        hnUnused(15, false, false);

        private int _value;
        private boolean _lengthFollows;
        private boolean _isContainer;

        private HighNibble(int value, boolean lengthFollows, boolean isContainer) {
            if ((value & 0xFFFFFFF0) != 0) {
                throw new IonException("illegal high nibble initialization");
            }
            this._value = value;
            this._lengthFollows = lengthFollows;
            this._isContainer = isContainer;
        }

        public static HighNibble getHighNibble(int hn) {
            switch (hn) {
                case 0: {
                    return hnNull;
                }
                case 1: {
                    return hnBoolean;
                }
                case 2: {
                    return hnPosInt;
                }
                case 3: {
                    return hnNegInt;
                }
                case 4: {
                    return hnFloat;
                }
                case 5: {
                    return hnDecimal;
                }
                case 6: {
                    return hnTimestamp;
                }
                case 7: {
                    return hnSymbol;
                }
                case 8: {
                    return hnString;
                }
                case 9: {
                    return hnClob;
                }
                case 10: {
                    return hnBlob;
                }
                case 11: {
                    return hnList;
                }
                case 12: {
                    return hnSexp;
                }
                case 13: {
                    return hnStruct;
                }
                case 14: {
                    return hnTypedecl;
                }
                case 15: {
                    return hnUnused;
                }
            }
            return null;
        }

        public int value() {
            return this._value;
        }

        public boolean lengthAlwaysFollows() {
            return this._lengthFollows;
        }

        public boolean isContainer() {
            return this._isContainer;
        }
    }
}

