/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazon.ion;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;

public class Decimal
extends BigDecimal {
    private static final long serialVersionUID = 1L;
    public static final Decimal ZERO = new Decimal(0);
    public static final Decimal NEGATIVE_ZERO = new NegativeZero(0);

    public static boolean isNegativeZero(BigDecimal val) {
        return val.getClass() == NegativeZero.class;
    }

    public static BigDecimal bigDecimalValue(BigDecimal val) {
        if (val == null || val.getClass() == BigDecimal.class) {
            return val;
        }
        return new BigDecimal(val.unscaledValue(), val.scale());
    }

    public static boolean equals(BigDecimal v1, BigDecimal v2) {
        return Decimal.isNegativeZero(v1) == Decimal.isNegativeZero(v2) && v1.equals(v2);
    }

    public static Decimal negativeZero(int scale) {
        return new NegativeZero(scale);
    }

    public static Decimal negativeZero(int scale, MathContext mc) {
        return new NegativeZero(scale, mc);
    }

    public static Decimal valueOf(BigInteger unscaledVal, int scale) {
        return new Decimal(unscaledVal, scale);
    }

    public static Decimal valueOf(BigInteger unscaledVal, int scale, MathContext mc) {
        return new Decimal(unscaledVal, scale, mc);
    }

    public static Decimal valueOf(BigInteger val) {
        return new Decimal(val);
    }

    public static Decimal valueOf(BigInteger val, MathContext mc) {
        return new Decimal(val, mc);
    }

    public static Decimal valueOf(int val) {
        return new Decimal(val);
    }

    public static Decimal valueOf(int val, MathContext mc) {
        return new Decimal(val, mc);
    }

    public static Decimal valueOf(long val) {
        return new Decimal(val);
    }

    public static Decimal valueOf(long val, MathContext mc) {
        return new Decimal(val, mc);
    }

    public static Decimal valueOf(double val) {
        if (Double.compare(val, -0.0) == 0) {
            return new NegativeZero(1);
        }
        return new Decimal(Double.toString(val));
    }

    public static Decimal valueOf(double val, MathContext mc) {
        if (Double.compare(val, -0.0) == 0) {
            return new NegativeZero(1, mc);
        }
        return new Decimal(Double.toString(val), mc);
    }

    public static Decimal valueOf(BigDecimal val) {
        if (val == null || val instanceof Decimal) {
            return (Decimal)val;
        }
        return new Decimal(val.unscaledValue(), val.scale());
    }

    public static Decimal valueOf(BigDecimal val, MathContext mc) {
        return new Decimal(val.unscaledValue(), val.scale(), mc);
    }

    public static Decimal valueOf(String val) {
        boolean negative = val.startsWith("-");
        Decimal ibd = new Decimal(val);
        if (negative && ibd.signum() == 0) {
            ibd = new NegativeZero(ibd.scale());
        }
        return ibd;
    }

    public static Decimal valueOf(String val, MathContext mc) {
        boolean negative = val.startsWith("-");
        Decimal ibd = new Decimal(val, mc);
        if (negative && ibd.signum() == 0) {
            ibd = new NegativeZero(ibd.scale(), mc);
        }
        return ibd;
    }

    private Decimal(BigInteger unscaledVal, int scale) {
        super(unscaledVal, scale);
    }

    private Decimal(BigInteger unscaledVal, int scale, MathContext mc) {
        super(unscaledVal, scale, mc);
    }

    private Decimal(BigInteger val) {
        super(val);
    }

    private Decimal(BigInteger val, MathContext mc) {
        super(val, mc);
    }

    private Decimal(int val) {
        super(val);
    }

    private Decimal(int val, MathContext mc) {
        super(val, mc);
    }

    private Decimal(long val) {
        super(val);
    }

    private Decimal(long val, MathContext mc) {
        super(val, mc);
    }

    private Decimal(double val) {
        super(val);
    }

    private Decimal(double val, MathContext mc) {
        super(val, mc);
    }

    private Decimal(char[] in, int offset, int len) {
        super(in, offset, len);
    }

    private Decimal(char[] in, int offset, int len, MathContext mc) {
        super(in, offset, len, mc);
    }

    private Decimal(char[] in) {
        super(in);
    }

    private Decimal(char[] in, MathContext mc) {
        super(in, mc);
    }

    private Decimal(String val) {
        super(val);
    }

    private Decimal(String val, MathContext mc) {
        super(val, mc);
    }

    public final boolean isNegativeZero() {
        return this.getClass() == NegativeZero.class;
    }

    public final BigDecimal bigDecimalValue() {
        return new BigDecimal(this.unscaledValue(), this.scale());
    }

    private static final class NegativeZero
    extends Decimal {
        private static final long serialVersionUID = 1L;

        private NegativeZero(int scale) {
            super(BigInteger.ZERO, scale);
        }

        private NegativeZero(int scale, MathContext mc) {
            super(BigInteger.ZERO, scale, mc);
        }

        @Override
        public float floatValue() {
            float v = super.floatValue();
            if (Float.compare(0.0f, v) <= 0) {
                v = -1.0f * v;
            }
            return v;
        }

        @Override
        public double doubleValue() {
            double v = super.doubleValue();
            if (Double.compare(0.0, v) <= 0) {
                v = -1.0 * v;
            }
            return v;
        }

        @Override
        public BigDecimal abs() {
            return new BigDecimal(this.unscaledValue(), this.scale());
        }

        @Override
        public BigDecimal abs(MathContext mc) {
            return new BigDecimal(this.unscaledValue(), this.scale(), mc);
        }

        @Override
        public String toString() {
            return '-' + super.toString();
        }

        @Override
        public String toEngineeringString() {
            return '-' + super.toEngineeringString();
        }

        @Override
        public String toPlainString() {
            return '-' + super.toPlainString();
        }
    }
}

