/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazon.ion.impl;

import com.amazon.ion.IonType;

final class IonTypeID {
    private static final int NUMBER_OF_BYTES = 256;
    private static final int BITS_PER_NIBBLE = 4;
    private static final int LOW_NIBBLE_BITMASK = 15;
    private static final int NULL_VALUE_NIBBLE = 15;
    private static final int VARIABLE_LENGTH_NIBBLE = 14;
    private static final int NEGATIVE_INT_TYPE_CODE = 3;
    private static final int TYPE_CODE_INVALID = 15;
    private static final int ANNOTATION_WRAPPER_MIN_LENGTH = 3;
    private static final int ANNOTATION_WRAPPER_MAX_LENGTH = 14;
    static final int ORDERED_STRUCT_NIBBLE = 1;
    static final IonType ION_TYPE_ANNOTATION_WRAPPER = IonType.DATAGRAM;
    static final IonType[] BINARY_TOKEN_TYPES_1_0 = new IonType[]{IonType.NULL, IonType.BOOL, IonType.INT, IonType.INT, IonType.FLOAT, IonType.DECIMAL, IonType.TIMESTAMP, IonType.SYMBOL, IonType.STRING, IonType.CLOB, IonType.BLOB, IonType.LIST, IonType.SEXP, IonType.STRUCT, ION_TYPE_ANNOTATION_WRAPPER, null};
    private static final IonTypeID ALWAYS_INVALID_TYPE_ID = new IonTypeID(-1, 0);
    static final IonTypeID[] TYPE_IDS_NO_IVM = new IonTypeID[256];
    static final IonTypeID[] TYPE_IDS_1_0 = new IonTypeID[256];
    final IonType type;
    final int length;
    final boolean variableLength;
    final boolean isNull;
    final boolean isNopPad;
    final byte lowerNibble;
    final boolean isValid;
    final boolean isNegativeInt;
    final boolean isTemplateInvocation;
    final int templateId;
    final boolean isDelimited;
    final boolean isInlineable;

    private static boolean isValid_1_0(byte upperNibble, byte lowerNibble, IonType type) {
        if (upperNibble == 15) {
            return false;
        }
        if (type == IonType.BOOL) {
            return lowerNibble <= 1 || lowerNibble == 15;
        }
        if (type == IonType.INT && upperNibble == 3) {
            return lowerNibble != 0;
        }
        if (type == IonType.FLOAT) {
            return lowerNibble == 0 || lowerNibble == 4 || lowerNibble == 8 || lowerNibble == 15;
        }
        if (type == IonType.TIMESTAMP) {
            return lowerNibble > 1;
        }
        if (type == ION_TYPE_ANNOTATION_WRAPPER) {
            return lowerNibble >= 3 && lowerNibble <= 14;
        }
        return true;
    }

    private IonTypeID(byte id, int minorVersion) {
        byte length;
        byte upperNibble;
        if (minorVersion == 0) {
            upperNibble = (byte)(id >> 4 & 0xF);
            this.lowerNibble = (byte)(id & 0xF);
            if (upperNibble == 0 && this.lowerNibble != 15) {
                this.isNopPad = true;
                this.type = null;
            } else {
                this.isNopPad = false;
                this.type = BINARY_TOKEN_TYPES_1_0[upperNibble];
            }
            this.isValid = IonTypeID.isValid_1_0(upperNibble, this.lowerNibble, this.type);
            this.isNull = this.lowerNibble == 15;
            length = this.lowerNibble;
            if (this.type == IonType.NULL || this.type == IonType.BOOL || !this.isValid) {
                this.variableLength = false;
                length = 0;
            } else if (this.type == IonType.STRUCT && length == 1) {
                this.variableLength = true;
            } else {
                boolean bl = this.variableLength = length == 14;
            }
            if (this.isNull) {
                length = 0;
            }
        } else {
            throw new IllegalStateException("Only Ion 1.0 is currently supported.");
        }
        this.isNegativeInt = this.type == IonType.INT && upperNibble == 3;
        this.length = length;
        this.isTemplateInvocation = false;
        this.templateId = -1;
        this.isDelimited = false;
        this.isInlineable = false;
    }

    public String toString() {
        return String.format("%s(%s)", new Object[]{this.type, this.length});
    }

    static {
        for (int b = 0; b < 256; ++b) {
            IonTypeID.TYPE_IDS_NO_IVM[b] = ALWAYS_INVALID_TYPE_ID;
            IonTypeID.TYPE_IDS_1_0[b] = new IonTypeID((byte)b, 0);
        }
    }
}

