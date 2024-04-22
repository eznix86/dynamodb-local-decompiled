/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazonaws.services.dynamodbv2.local.shared.access;

import com.amazonaws.services.dynamodbv2.exceptions.AWSExceptionFactory;
import com.amazonaws.services.dynamodbv2.exceptions.AmazonServiceExceptionType;
import com.amazonaws.services.dynamodbv2.local.shared.access.LocalDBUtils;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PaddingNumberEncoder {
    public static final int MAX_PRECISION = 38;
    public static final int MAX_EXPONENT_LENGTH;
    public static final int NUMBER_EXPONENT_BOUNDS_LOW = -128;
    public static final int NUMBER_EXPONENT_BOUNDS_HIGH = 126;
    public static final String NEG_POS = "<<";
    public static final String NEG_ZERO = "<=";
    public static final String NEG_NEG = "<>";
    public static final String ZERO = "=";
    public static final String POS_NEG = "><";
    public static final String POS_ZERO = ">=";
    public static final String POS_POS = ">>";
    public static BigInteger MAX_NUM;

    private static String pad(String s, char with, boolean left, int upTo) {
        StringBuilder ret = new StringBuilder(s);
        while (ret.length() < upTo) {
            if (left) {
                ret.insert(0, with);
                continue;
            }
            ret.append(with);
        }
        return ret.toString();
    }

    public static byte[] encodeBigDecimal(BigDecimal b) {
        Pattern pattern;
        Matcher matcher;
        int exp;
        Object data = b.stripTrailingZeros().toPlainString();
        BigDecimal value = b.abs().stripTrailingZeros();
        if (value.scaleByPowerOfTen(value.scale()).toBigInteger().toString(10).length() > 38) {
            throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, String.format("Number %s exceeds maximum precision (%d).", b.toEngineeringString(), 38));
        }
        int scale = value.scale();
        if ((exp += (exp = value.precision() - scale) % 2 == 0 ? 0 : 1) < -128 || exp > 126) {
            throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, String.format("Number %s exceeds bounds [%d, %d].", b.toEngineeringString(), -128, 126));
        }
        exp = value.precision() - scale - 1;
        if (b.signum() == 0) {
            return ZERO.getBytes(LocalDBUtils.UTF8);
        }
        if (!((String)data).contains(".")) {
            data = (String)data + ".0";
        }
        if ((matcher = (pattern = Pattern.compile("(-?)(0*)(\\d*)\\.(0*)(\\d*)")).matcher((CharSequence)data)).matches()) {
            String expString;
            String prefixString;
            String sign = matcher.group(1);
            String leadingZNumber = matcher.group(2);
            String number = matcher.group(3);
            String leadingZDecimal = matcher.group(4);
            String decimal = matcher.group(5);
            boolean isNegative = sign.equals("-");
            Object numberString = leadingZNumber.length() == 1 && number.isEmpty() ? decimal : number + leadingZDecimal + decimal;
            if (((String)numberString).length() > 38) {
                numberString = ((String)numberString).substring(0, 38);
            }
            if (isNegative) {
                numberString = PaddingNumberEncoder.invertedNumber((String)numberString);
                if (exp < 0) {
                    prefixString = NEG_NEG;
                    expString = PaddingNumberEncoder.pad("" + Math.abs(exp), '0', true, MAX_EXPONENT_LENGTH);
                } else if (exp == 0) {
                    prefixString = NEG_ZERO;
                    expString = "";
                } else {
                    prefixString = NEG_POS;
                    expString = PaddingNumberEncoder.invertedExponent(String.valueOf(exp));
                }
            } else if (exp < 0) {
                prefixString = POS_NEG;
                expString = PaddingNumberEncoder.invertedExponent(String.valueOf(Math.abs(exp)));
            } else if (exp == 0) {
                prefixString = POS_ZERO;
                expString = "";
            } else {
                prefixString = POS_POS;
                expString = PaddingNumberEncoder.pad("" + exp, '0', true, MAX_EXPONENT_LENGTH);
            }
            String ret = prefixString + expString + "E" + PaddingNumberEncoder.pad((String)numberString, '0', false, 38);
            return ret.getBytes(LocalDBUtils.UTF8);
        }
        throw AWSExceptionFactory.buildInternalServerException(String.format("Number pattern (%s) invalid.", data));
    }

    private static String invertedNumber(String value) {
        return PaddingNumberEncoder.invertedValue(value, 38, false);
    }

    private static String invertedExponent(String value) {
        return PaddingNumberEncoder.invertedValue(value, MAX_EXPONENT_LENGTH, true);
    }

    private static String invertedValue(String value, int length, boolean padOperandFromLeft) {
        String max = PaddingNumberEncoder.pad("", '9', false, length);
        BigInteger invertedVal = new BigInteger(max).subtract(new BigInteger(PaddingNumberEncoder.pad(value, '0', padOperandFromLeft, length)));
        return PaddingNumberEncoder.pad(invertedVal.toString(), '0', true, length);
    }

    static {
        MAX_NUM = new BigInteger(PaddingNumberEncoder.pad("", '9', false, 38));
        MAX_EXPONENT_LENGTH = Math.max(String.valueOf(Math.abs(126)).length(), String.valueOf(Math.abs(-128)).length());
    }
}

