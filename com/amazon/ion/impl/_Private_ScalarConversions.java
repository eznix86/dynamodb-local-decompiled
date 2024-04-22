/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazon.ion.impl;

import com.amazon.ion.Decimal;
import com.amazon.ion.IntegerSize;
import com.amazon.ion.IonException;
import com.amazon.ion.IonType;
import com.amazon.ion.Timestamp;
import com.amazon.ion.impl.IonTokenConstsX;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

public class _Private_ScalarConversions {
    protected static int FNID_no_conversion = -1;
    protected static int FNID_identity = 0;
    protected static final int FNID_FROM_STRING_TO_NULL = 1;
    protected static final int FNID_FROM_STRING_TO_BOOLEAN = 2;
    protected static final int FNID_FROM_STRING_TO_INT = 3;
    protected static final int FNID_FROM_STRING_TO_LONG = 4;
    protected static final int FNID_FROM_STRING_TO_BIGINTEGER = 5;
    protected static final int FNID_FROM_STRING_TO_DECIMAL = 6;
    protected static final int FNID_FROM_STRING_TO_DOUBLE = 7;
    protected static final int FNID_FROM_STRING_TO_DATE = 8;
    protected static final int FNID_FROM_STRING_TO_TIMESTAMP = 9;
    static int[] from_string_conversion = new int[]{FNID_no_conversion, 1, 2, 3, 4, 5, 6, 7, FNID_identity, 8, 9, FNID_no_conversion, FNID_no_conversion};
    protected static final int FNID_FROM_NULL_TO_STRING = 10;
    protected static final int FNID_FROM_BOOLEAN_TO_STRING = 11;
    protected static final int FNID_FROM_INT_TO_STRING = 12;
    protected static final int FNID_FROM_LONG_TO_STRING = 13;
    protected static final int FNID_FROM_BIGINTEGER_TO_STRING = 14;
    protected static final int FNID_FROM_DECIMAL_TO_STRING = 15;
    protected static final int FNID_FROM_DOUBLE_TO_STRING = 16;
    protected static final int FNID_FROM_DATE_TO_STRING = 17;
    protected static final int FNID_FROM_TIMESTAMP_TO_STRING = 18;
    static int[] to_string_conversions = new int[]{FNID_no_conversion, 10, 11, 12, 13, 14, 15, 16, FNID_identity, 17, 18, FNID_no_conversion, FNID_no_conversion};
    protected static final int FNID_FROM_LONG_TO_INT = 19;
    protected static final int FNID_FROM_BIGINTEGER_TO_INT = 20;
    protected static final int FNID_FROM_DECIMAL_TO_INT = 21;
    protected static final int FNID_FROM_DOUBLE_TO_INT = 22;
    static int[] to_int_conversion = new int[]{FNID_no_conversion, FNID_no_conversion, FNID_no_conversion, FNID_identity, 19, 20, 21, 22, 3, FNID_no_conversion, FNID_no_conversion, FNID_no_conversion, FNID_no_conversion};
    protected static final int FNID_FROM_INT_TO_LONG = 23;
    protected static final int FNID_FROM_BIGINTEGER_TO_LONG = 24;
    protected static final int FNID_FROM_DECIMAL_TO_LONG = 25;
    protected static final int FNID_FROM_DOUBLE_TO_LONG = 26;
    static int[] to_long_conversion = new int[]{FNID_no_conversion, FNID_no_conversion, FNID_no_conversion, 23, FNID_identity, 24, 25, 26, 4, FNID_no_conversion, FNID_no_conversion, FNID_no_conversion, FNID_no_conversion};
    protected static final int FNID_FROM_INT_TO_BIGINTEGER = 27;
    protected static final int FNID_FROM_LONG_TO_BIGINTEGER = 28;
    protected static final int FNID_FROM_DECIMAL_TO_BIGINTEGER = 29;
    protected static final int FNID_FROM_DOUBLE_TO_BIGINTEGER = 30;
    static int[] to_bigInteger_conversion = new int[]{FNID_no_conversion, FNID_no_conversion, FNID_no_conversion, 27, 28, FNID_identity, 29, 30, 5, FNID_no_conversion, FNID_no_conversion, FNID_no_conversion, FNID_no_conversion};
    protected static final int FNID_FROM_INT_TO_DECIMAL = 31;
    protected static final int FNID_FROM_LONG_TO_DECIMAL = 32;
    protected static final int FNID_FROM_BIGINTEGER_TO_DECIMAL = 33;
    protected static final int FNID_FROM_DOUBLE_TO_DECIMAL = 34;
    static int[] to_decimal_conversion = new int[]{FNID_no_conversion, FNID_no_conversion, FNID_no_conversion, 31, 32, 33, FNID_identity, 34, 6, FNID_no_conversion, FNID_no_conversion, FNID_no_conversion, FNID_no_conversion};
    protected static final int FNID_FROM_INT_TO_DOUBLE = 35;
    protected static final int FNID_FROM_LONG_TO_DOUBLE = 36;
    protected static final int FNID_FROM_BIGINTEGER_TO_DOUBLE = 37;
    protected static final int FNID_FROM_DECIMAL_TO_DOUBLE = 38;
    static int[] to_double_conversion = new int[]{FNID_no_conversion, FNID_no_conversion, FNID_no_conversion, 35, 36, 37, 38, FNID_identity, 7, FNID_no_conversion, FNID_no_conversion, FNID_no_conversion, FNID_no_conversion};
    protected static final int FNID_FROM_TIMESTAMP_TO_DATE = 39;
    protected static final int FNID_FROM_DATE_TO_TIMESTAMP = 40;

    public static IntegerSize getIntegerSize(int authoritative_type) {
        switch (authoritative_type) {
            case 3: {
                return IntegerSize.INT;
            }
            case 4: {
                return IntegerSize.LONG;
            }
            case 5: {
                return IntegerSize.BIG_INTEGER;
            }
        }
        return null;
    }

    public static String getValueTypeName(int value_type) {
        switch (value_type) {
            case 1: {
                return "null";
            }
            case 2: {
                return "boolean";
            }
            case 3: {
                return "int";
            }
            case 4: {
                return "long";
            }
            case 5: {
                return "bigInteger";
            }
            case 6: {
                return "decimal";
            }
            case 7: {
                return "double";
            }
            case 8: {
                return "string";
            }
            case 9: {
                return "date";
            }
            case 10: {
                return "timestamp";
            }
        }
        return "<unrecognized conversion value type: " + Integer.toString(value_type) + ">";
    }

    public static String get_value_type_name(int value_type) {
        switch (value_type) {
            case 1: 
            case 2: 
            case 3: 
            case 4: 
            case 5: 
            case 6: 
            case 7: 
            case 8: 
            case 9: 
            case 10: {
                return _Private_ScalarConversions.getValueTypeName(value_type) + "_value";
            }
        }
        return "<unrecognized conversion value type: " + Integer.toString(value_type) + ">";
    }

    public static final String getAllValueTypeNames(int value_type) {
        StringBuilder sb = new StringBuilder();
        sb.append('(');
        int bit = 1;
        for (int ii = 0; ii < 32; ++ii) {
            if ((value_type & bit) != 0) {
                sb.append(_Private_ScalarConversions.getValueTypeName(bit));
                sb.append(' ');
            }
            bit <<= 1;
        }
        sb.append(')');
        return sb.toString();
    }

    protected static final int getConversionFnid(int authoritative_type, int new_type) {
        if (new_type == authoritative_type) {
            return 0;
        }
        switch (new_type) {
            case 1: {
                assert (authoritative_type == 8);
                return from_string_conversion[1];
            }
            case 2: {
                assert (authoritative_type == 8);
                return from_string_conversion[2];
            }
            case 3: {
                return to_int_conversion[authoritative_type];
            }
            case 4: {
                return to_long_conversion[authoritative_type];
            }
            case 5: {
                return to_bigInteger_conversion[authoritative_type];
            }
            case 6: {
                return to_decimal_conversion[authoritative_type];
            }
            case 7: {
                return to_double_conversion[authoritative_type];
            }
            case 8: {
                return to_string_conversions[authoritative_type];
            }
            case 9: {
                assert (authoritative_type == 10);
                return 39;
            }
            case 10: {
                assert (authoritative_type == 9);
                return 40;
            }
        }
        String message = "can't convert from " + _Private_ScalarConversions.getValueTypeName(authoritative_type) + " to " + _Private_ScalarConversions.getValueTypeName(new_type);
        throw new CantConvertException(message);
    }

    public static final class ValueVariant {
        private static final BigInteger min_int_value = BigInteger.valueOf(Integer.MIN_VALUE);
        private static final BigInteger max_int_value = BigInteger.valueOf(Integer.MAX_VALUE);
        private static final BigInteger min_long_value = BigInteger.valueOf(Long.MIN_VALUE);
        private static final BigInteger max_long_value = BigInteger.valueOf(Long.MAX_VALUE);
        private static final BigDecimal min_int_decimal_value = BigDecimal.valueOf(Integer.MIN_VALUE);
        private static final BigDecimal max_int_decimal_value = BigDecimal.valueOf(Integer.MAX_VALUE);
        private static final BigDecimal min_long_decimal_value = BigDecimal.valueOf(Long.MIN_VALUE);
        private static final BigDecimal max_long_decimal_value = BigDecimal.valueOf(Long.MAX_VALUE);
        int _authoritative_type_idx;
        int _types_set;
        boolean _is_null;
        IonType _null_type;
        boolean _boolean_value;
        int _int_value;
        long _long_value;
        double _double_value;
        String _string_value;
        BigInteger _bigInteger_value;
        Decimal _decimal_value;
        Date _date_value;
        Timestamp _timestamp_value;

        public final boolean isEmpty() {
            return this._authoritative_type_idx == 0;
        }

        public final void clear() {
            this._authoritative_type_idx = 0;
            this._types_set = 0;
        }

        public final boolean hasValueOfType(int value_type) {
            return (this._types_set & AS_TYPE.idx_to_bit_mask(value_type)) != 0;
        }

        public final boolean hasNumericType() {
            return (AS_TYPE.numeric_types & this._types_set) != 0;
        }

        public static final boolean isNumericType(int type_idx) {
            int type_flag = AS_TYPE.idx_to_bit_mask(type_idx);
            return (AS_TYPE.numeric_types & type_flag) != 0;
        }

        public final boolean hasDatetimeType() {
            return (AS_TYPE.datetime_types & this._types_set) != 0;
        }

        public final void setAuthoritativeType(int value_type) {
            if (this._authoritative_type_idx == value_type) {
                return;
            }
            if (!this.hasValueOfType(value_type)) {
                String message = "you must set the " + _Private_ScalarConversions.getValueTypeName(value_type) + " value before you can set the authoritative type to " + _Private_ScalarConversions.getValueTypeName(value_type);
                throw new IllegalStateException(message);
            }
            this._authoritative_type_idx = value_type;
        }

        public final void setValueToNull(IonType t) {
            this._is_null = true;
            this._null_type = t;
            this.set_value_type(1);
        }

        public final void setValue(boolean value) {
            this._boolean_value = value;
            this.set_value_type(2);
        }

        public final void setValue(int value) {
            this._int_value = value;
            this.set_value_type(3);
        }

        public final void setValue(long value) {
            this._long_value = value;
            this.set_value_type(4);
        }

        public final void setValue(double value) {
            this._double_value = value;
            this.set_value_type(7);
        }

        public final void setValue(String value) {
            this._string_value = value;
            this.set_value_type(8);
        }

        public final void setValue(BigInteger value) {
            this._bigInteger_value = value;
            this.set_value_type(5);
        }

        public final void setValue(Decimal value) {
            this._decimal_value = value;
            this.set_value_type(6);
        }

        public final void setValue(Date value) {
            this._date_value = value;
            this.set_value_type(9);
        }

        public final void setValue(Timestamp value) {
            this._timestamp_value = value;
            this.set_value_type(10);
        }

        public final void addValueToNull(IonType t) {
            this._is_null = true;
            this._null_type = t;
            this.add_value_type(1);
        }

        public final void addValue(boolean value) {
            this._boolean_value = value;
            this.add_value_type(2);
        }

        public final void addValue(int value) {
            this._int_value = value;
            this.add_value_type(3);
        }

        public final void addValue(long value) {
            this._long_value = value;
            this.add_value_type(4);
        }

        public final void addValue(double value) {
            this._double_value = value;
            this.add_value_type(7);
        }

        public final void addValue(String value) {
            this._string_value = value;
            this.add_value_type(8);
        }

        public final void addValue(BigInteger value) {
            this._bigInteger_value = value;
            this.add_value_type(5);
        }

        public final void addValue(BigDecimal value) {
            this._decimal_value = (Decimal)value;
            this.add_value_type(6);
        }

        public final void addValue(Decimal value) {
            this._decimal_value = value;
            this.add_value_type(6);
        }

        public final void addValue(Date value) {
            this._date_value = value;
            this.add_value_type(9);
        }

        public final void addValue(Timestamp value) {
            this._timestamp_value = value;
            this.add_value_type(10);
        }

        public final int getAuthoritativeType() {
            return this._authoritative_type_idx;
        }

        public final boolean isNull() {
            return this.hasValueOfType(1);
        }

        public final IonType getNullType() {
            if (!this.hasValueOfType(1)) {
                throw new ValueNotSetException("null value not set");
            }
            return this._null_type;
        }

        public final boolean getBoolean() {
            if (!this.hasValueOfType(2)) {
                throw new ValueNotSetException("boolean not set");
            }
            return this._boolean_value;
        }

        public final int getInt() {
            if (!this.hasValueOfType(3)) {
                throw new ValueNotSetException("int value not set");
            }
            return this._int_value;
        }

        public final long getLong() {
            if (!this.hasValueOfType(4)) {
                throw new ValueNotSetException("long value not set");
            }
            return this._long_value;
        }

        public final double getDouble() {
            if (!this.hasValueOfType(7)) {
                throw new ValueNotSetException("double value not set");
            }
            return this._double_value;
        }

        public final String getString() {
            if (!this.hasValueOfType(8)) {
                throw new ValueNotSetException("String value not set");
            }
            return this._string_value;
        }

        public final BigInteger getBigInteger() {
            if (!this.hasValueOfType(5)) {
                throw new ValueNotSetException("BigInteger value not set");
            }
            return this._bigInteger_value;
        }

        public final BigDecimal getBigDecimal() {
            if (!this.hasValueOfType(6)) {
                throw new ValueNotSetException("BigDecimal value not set");
            }
            return Decimal.bigDecimalValue(this._decimal_value);
        }

        public final Decimal getDecimal() {
            if (!this.hasValueOfType(6)) {
                throw new ValueNotSetException("BigDecimal value not set");
            }
            return this._decimal_value;
        }

        public final Date getDate() {
            if (!this.hasValueOfType(9)) {
                throw new ValueNotSetException("Date value not set");
            }
            return this._date_value;
        }

        public final Timestamp getTimestamp() {
            if (!this.hasValueOfType(10)) {
                throw new ValueNotSetException("timestamp value not set");
            }
            return this._timestamp_value;
        }

        public final boolean can_convert(int new_type) {
            boolean can = false;
            switch (new_type) {
                case 1: 
                case 2: {
                    can = this._authoritative_type_idx == 8;
                    break;
                }
                case 3: 
                case 4: 
                case 5: 
                case 6: 
                case 7: {
                    can = this.is_numeric_type(this._authoritative_type_idx) || this._authoritative_type_idx == 8;
                    break;
                }
                case 8: {
                    can = true;
                    break;
                }
                case 9: 
                case 10: {
                    can = this.is_datetime_type(this._authoritative_type_idx) || this._authoritative_type_idx == 8;
                    break;
                }
                default: {
                    can = false;
                }
            }
            assert (!can ? this.get_conversion_fnid(new_type) == -1 : this.get_conversion_fnid(new_type) != -1);
            return can;
        }

        public final int get_conversion_fnid(int new_type) {
            return _Private_ScalarConversions.getConversionFnid(this._authoritative_type_idx, new_type);
        }

        public final void cast(int castfnid) {
            switch (castfnid) {
                case 1: {
                    this.fn_from_string_to_null();
                    break;
                }
                case 2: {
                    this.fn_from_string_to_boolean();
                    break;
                }
                case 3: {
                    this.fn_from_string_to_int();
                    break;
                }
                case 4: {
                    this.fn_from_string_to_long();
                    break;
                }
                case 5: {
                    this.fn_from_string_to_biginteger();
                    break;
                }
                case 6: {
                    this.fn_from_string_to_decimal();
                    break;
                }
                case 7: {
                    this.fn_from_string_to_double();
                    break;
                }
                case 8: {
                    this.fn_from_string_to_date();
                    break;
                }
                case 9: {
                    this.fn_from_string_to_timestamp();
                    break;
                }
                case 10: {
                    this.fn_from_null_to_string();
                    break;
                }
                case 11: {
                    this.fn_from_boolean_to_string();
                    break;
                }
                case 12: {
                    this.fn_from_int_to_string();
                    break;
                }
                case 13: {
                    this.fn_from_long_to_string();
                    break;
                }
                case 14: {
                    this.fn_from_biginteger_to_string();
                    break;
                }
                case 15: {
                    this.fn_from_decimal_to_string();
                    break;
                }
                case 16: {
                    this.fn_from_double_to_string();
                    break;
                }
                case 17: {
                    this.fn_from_date_to_string();
                    break;
                }
                case 18: {
                    this.fn_from_timestamp_to_string();
                    break;
                }
                case 19: {
                    this.fn_from_long_to_int();
                    break;
                }
                case 20: {
                    this.fn_from_biginteger_to_int();
                    break;
                }
                case 21: {
                    this.fn_from_decimal_to_int();
                    break;
                }
                case 22: {
                    this.fn_from_double_to_int();
                    break;
                }
                case 23: {
                    this.fn_from_int_to_long();
                    break;
                }
                case 24: {
                    this.fn_from_biginteger_to_long();
                    break;
                }
                case 25: {
                    this.fn_from_decimal_to_long();
                    break;
                }
                case 26: {
                    this.fn_from_double_to_long();
                    break;
                }
                case 27: {
                    this.fn_from_int_to_biginteger();
                    break;
                }
                case 28: {
                    this.fn_from_long_to_biginteger();
                    break;
                }
                case 29: {
                    this.fn_from_decimal_to_biginteger();
                    break;
                }
                case 30: {
                    this.fn_from_double_to_biginteger();
                    break;
                }
                case 31: {
                    this.fn_from_int_to_decimal();
                    break;
                }
                case 32: {
                    this.fn_from_long_to_decimal();
                    break;
                }
                case 33: {
                    this.fn_from_biginteger_to_decimal();
                    break;
                }
                case 34: {
                    this.fn_from_double_to_decimal();
                    break;
                }
                case 35: {
                    this.fn_from_int_to_double();
                    break;
                }
                case 36: {
                    this.fn_from_long_to_double();
                    break;
                }
                case 37: {
                    this.fn_from_biginteger_to_double();
                    break;
                }
                case 38: {
                    this.fn_from_decimal_to_double();
                    break;
                }
                case 39: {
                    this.fn_from_timestamp_to_date();
                    break;
                }
                case 40: {
                    this.fn_from_date_to_timestamp();
                    break;
                }
                default: {
                    throw new ConversionException("unrecognized conversion fnid [" + castfnid + "]invoked");
                }
            }
        }

        private final void set_value_type(int type_idx) {
            this._types_set = AS_TYPE.idx_to_bit_mask(type_idx);
            this._authoritative_type_idx = type_idx;
        }

        private final void add_value_type(int type_idx) {
            this._types_set |= AS_TYPE.idx_to_bit_mask(type_idx);
        }

        private final boolean is_numeric_type(int type_idx) {
            return (AS_TYPE.numeric_types & AS_TYPE.idx_to_bit_mask(type_idx)) != 0;
        }

        private final boolean is_datetime_type(int type_idx) {
            return (AS_TYPE.datetime_types & AS_TYPE.idx_to_bit_mask(type_idx)) != 0;
        }

        private final void fn_from_string_to_null() {
            this._null_type = IonTokenConstsX.getNullType(this._string_value);
            this._is_null = true;
            this.add_value_type(1);
        }

        private final void fn_from_string_to_boolean() {
            this._boolean_value = Boolean.parseBoolean(this._string_value);
            this.add_value_type(2);
        }

        private final void fn_from_string_to_int() {
            this._int_value = Integer.parseInt(this._string_value);
            this.add_value_type(3);
        }

        private final void fn_from_string_to_long() {
            this._long_value = Long.parseLong(this._string_value);
            this.add_value_type(4);
        }

        private final void fn_from_string_to_biginteger() {
            this._bigInteger_value = new BigInteger(this._string_value);
            this.add_value_type(5);
        }

        private final void fn_from_string_to_decimal() {
            this._decimal_value = Decimal.valueOf(this._string_value);
            this.add_value_type(6);
        }

        private final void fn_from_string_to_double() {
            this._double_value = Double.parseDouble(this._string_value);
            this.add_value_type(7);
        }

        private final void fn_from_string_to_date() {
            if (!this.hasValueOfType(10)) {
                this.fn_from_string_to_timestamp();
            }
            this._date_value = new Date(this._timestamp_value.getMillis());
            this.add_value_type(9);
        }

        private final void fn_from_string_to_timestamp() {
            this._timestamp_value = Timestamp.valueOf(this._string_value);
            this.add_value_type(10);
            this.add_value_type(10);
        }

        private final void fn_from_null_to_string() {
            this._string_value = IonTokenConstsX.getNullImage(this._null_type);
            this.add_value_type(8);
        }

        private final void fn_from_boolean_to_string() {
            this._string_value = this._boolean_value ? "true" : "false";
            this.add_value_type(8);
        }

        private final void fn_from_int_to_string() {
            this._string_value = Integer.toString(this._int_value);
            this.add_value_type(8);
        }

        private final void fn_from_long_to_string() {
            this._string_value = Long.toString(this._long_value);
            this.add_value_type(8);
        }

        private final void fn_from_biginteger_to_string() {
            this._string_value = this._bigInteger_value.toString();
            this.add_value_type(8);
        }

        private final void fn_from_decimal_to_string() {
            this._string_value = this._decimal_value.toString();
            this.add_value_type(8);
        }

        private final void fn_from_double_to_string() {
            this._string_value = Double.toString(this._double_value);
            this.add_value_type(8);
        }

        private final void fn_from_date_to_string() {
            if (!this.hasValueOfType(10)) {
                this.fn_from_date_to_timestamp();
            }
            this.fn_from_timestamp_to_string();
        }

        private final void fn_from_timestamp_to_string() {
            this._string_value = this._timestamp_value.toString();
            this.add_value_type(8);
        }

        private final void fn_from_long_to_int() {
            if (this._long_value < Integer.MIN_VALUE || this._long_value > Integer.MAX_VALUE) {
                throw new CantConvertException("long is too large to fit in an int");
            }
            this._int_value = (int)this._long_value;
            this.add_value_type(3);
        }

        private final void fn_from_biginteger_to_int() {
            if (min_int_value.compareTo(this._bigInteger_value) > 0 || max_int_value.compareTo(this._bigInteger_value) < 0) {
                throw new CantConvertException("bigInteger value is too large to fit in an int");
            }
            this._int_value = this._bigInteger_value.intValue();
            this.add_value_type(3);
        }

        private final void fn_from_decimal_to_int() {
            if (min_int_decimal_value.compareTo(this._decimal_value) > 0 || max_int_decimal_value.compareTo(this._decimal_value) < 0) {
                throw new CantConvertException("BigDecimal value is too large to fit in an int");
            }
            this._int_value = this._decimal_value.intValue();
            this.add_value_type(3);
        }

        private final void fn_from_double_to_int() {
            if (this._double_value < -2.147483648E9 || this._double_value > 2.147483647E9) {
                throw new CantConvertException("double is too large to fit in an int");
            }
            this._int_value = (int)this._double_value;
            this.add_value_type(3);
        }

        private final void fn_from_int_to_long() {
            this._long_value = this._int_value;
            this.add_value_type(4);
        }

        private final void fn_from_biginteger_to_long() {
            if (min_long_value.compareTo(this._bigInteger_value) > 0 || max_long_value.compareTo(this._bigInteger_value) < 0) {
                throw new CantConvertException("BigInteger is too large to fit in a long");
            }
            this._long_value = this._bigInteger_value.longValue();
            this.add_value_type(4);
        }

        private final void fn_from_decimal_to_long() {
            if (min_long_decimal_value.compareTo(this._decimal_value) > 0 || max_long_decimal_value.compareTo(this._decimal_value) < 0) {
                throw new CantConvertException("BigDecimal value is too large to fit in a long");
            }
            this._long_value = this._decimal_value.longValue();
            this.add_value_type(4);
        }

        private final void fn_from_double_to_long() {
            if (this._double_value < -9.223372036854776E18 || this._double_value > 9.223372036854776E18) {
                throw new CantConvertException("double is too large to fit in a long");
            }
            this._long_value = (long)this._double_value;
            this.add_value_type(4);
        }

        private final void fn_from_int_to_biginteger() {
            this._bigInteger_value = BigInteger.valueOf(this._int_value);
            this.add_value_type(5);
        }

        private final void fn_from_long_to_biginteger() {
            this._bigInteger_value = BigInteger.valueOf(this._long_value);
            this.add_value_type(5);
        }

        private final void fn_from_decimal_to_biginteger() {
            this._bigInteger_value = this._decimal_value.toBigInteger();
            this.add_value_type(5);
        }

        private final void fn_from_double_to_biginteger() {
            this._bigInteger_value = Decimal.valueOf(this._double_value).toBigInteger();
            this.add_value_type(5);
        }

        private final void fn_from_int_to_decimal() {
            this._decimal_value = Decimal.valueOf(this._int_value);
            this.add_value_type(6);
        }

        private final void fn_from_long_to_decimal() {
            this._decimal_value = Decimal.valueOf(this._long_value);
            this.add_value_type(6);
        }

        private final void fn_from_biginteger_to_decimal() {
            this._decimal_value = Decimal.valueOf(this._bigInteger_value);
            this.add_value_type(6);
        }

        private final void fn_from_double_to_decimal() {
            this._decimal_value = Decimal.valueOf(this._double_value);
            this.add_value_type(6);
        }

        private final void fn_from_int_to_double() {
            this._double_value = this._int_value;
            this.add_value_type(7);
        }

        private final void fn_from_long_to_double() {
            this._double_value = this._long_value;
            this.add_value_type(7);
        }

        private final void fn_from_biginteger_to_double() {
            this._double_value = this._bigInteger_value.doubleValue();
            this.add_value_type(7);
        }

        private final void fn_from_decimal_to_double() {
            this._double_value = this._decimal_value.doubleValue();
            this.add_value_type(7);
        }

        private final void fn_from_timestamp_to_date() {
            this._date_value = this._timestamp_value.dateValue();
            this.add_value_type(9);
        }

        private final void fn_from_date_to_timestamp() {
            this._timestamp_value = new Timestamp(this._date_value.getTime(), null);
            this.add_value_type(10);
        }
    }

    public static class ValueNotSetException
    extends ConversionException {
        private static final long serialVersionUID = 1L;

        ValueNotSetException(String msg) {
            super(msg);
        }

        ValueNotSetException(Exception e) {
            super(e);
        }

        ValueNotSetException(String msg, Exception e) {
            super(msg, e);
        }
    }

    public static class CantConvertException
    extends ConversionException {
        private static final long serialVersionUID = 1L;

        CantConvertException(String msg) {
            super(msg);
        }

        CantConvertException(Exception e) {
            super(e);
        }

        CantConvertException(String msg, Exception e) {
            super(msg, e);
        }
    }

    public static class ConversionException
    extends IonException {
        private static final long serialVersionUID = 1L;

        ConversionException(String msg) {
            super(msg);
        }

        ConversionException(Exception e) {
            super(e);
        }

        ConversionException(String msg, Exception e) {
            super(msg, e);
        }
    }

    public static final class AS_TYPE {
        public static final int null_value = 1;
        public static final int boolean_value = 2;
        public static final int int_value = 3;
        public static final int long_value = 4;
        public static final int bigInteger_value = 5;
        public static final int decimal_value = 6;
        public static final int double_value = 7;
        public static final int string_value = 8;
        public static final int date_value = 9;
        public static final int timestamp_value = 10;
        public static final int numeric_types = AS_TYPE.idx_to_bit_mask(3) | AS_TYPE.idx_to_bit_mask(4) | AS_TYPE.idx_to_bit_mask(5) | AS_TYPE.idx_to_bit_mask(6) | AS_TYPE.idx_to_bit_mask(7);
        public static final int datetime_types = AS_TYPE.idx_to_bit_mask(9) | AS_TYPE.idx_to_bit_mask(10);
        public static final int convertable_type = AS_TYPE.idx_to_bit_mask(numeric_types) | AS_TYPE.idx_to_bit_mask(datetime_types) | AS_TYPE.idx_to_bit_mask(2) | AS_TYPE.idx_to_bit_mask(8);

        static final int idx_to_bit_mask(int idx) {
            return 1 << idx - 1;
        }
    }
}

