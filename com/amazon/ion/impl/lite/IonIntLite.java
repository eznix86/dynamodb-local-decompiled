/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazon.ion.impl.lite;

import com.amazon.ion.IntegerSize;
import com.amazon.ion.IonInt;
import com.amazon.ion.IonType;
import com.amazon.ion.IonWriter;
import com.amazon.ion.NullValueException;
import com.amazon.ion.ValueVisitor;
import com.amazon.ion.impl._Private_IonValue;
import com.amazon.ion.impl.lite.ContainerlessContext;
import com.amazon.ion.impl.lite.IonContext;
import com.amazon.ion.impl.lite.IonValueLite;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;

final class IonIntLite
extends IonValueLite
implements IonInt {
    private static final BigInteger LONG_MIN_VALUE = BigInteger.valueOf(Long.MIN_VALUE);
    private static final BigInteger LONG_MAX_VALUE = BigInteger.valueOf(Long.MAX_VALUE);
    private static final int HASH_SIGNATURE = IonType.INT.toString().hashCode();
    private static final int INT_SIZE_MASK = 24;
    private static final int INT_SIZE_SHIFT = 3;
    private static final IntegerSize[] SIZES = IntegerSize.values();
    private long _long_value;
    private BigInteger _big_int_value;

    IonIntLite(ContainerlessContext context, boolean isNull) {
        super(context, isNull);
    }

    IonIntLite(IonIntLite existing, IonContext context) {
        super(existing, context);
        this._long_value = existing._long_value;
        this._big_int_value = existing._big_int_value;
    }

    @Override
    IonValueLite shallowClone(IonContext context) {
        return new IonIntLite(this, context);
    }

    @Override
    public IonIntLite clone() {
        return (IonIntLite)this.shallowClone(ContainerlessContext.wrap(this.getSystem()));
    }

    @Override
    int hashSignature() {
        return HASH_SIGNATURE;
    }

    @Override
    int scalarHashCode() {
        int result = HASH_SIGNATURE;
        if (this._big_int_value == null) {
            result ^= (int)this._long_value;
            int hi_word = (int)(this._long_value >>> 32);
            if (hi_word != 0 && hi_word != -1) {
                result ^= hi_word;
            }
        } else {
            result = this._big_int_value.hashCode();
        }
        return this.hashTypeAnnotations(result);
    }

    @Override
    public IonType getType() {
        return IonType.INT;
    }

    @Override
    public int intValue() throws NullValueException {
        this.validateThisNotNull();
        if (this._big_int_value == null) {
            return (int)this._long_value;
        }
        return this._big_int_value.intValue();
    }

    @Override
    public long longValue() throws NullValueException {
        this.validateThisNotNull();
        if (this._big_int_value == null) {
            return this._long_value;
        }
        return this._big_int_value.longValue();
    }

    @Override
    public BigInteger bigIntegerValue() throws NullValueException {
        if (this.isNullValue()) {
            return null;
        }
        if (this._big_int_value == null) {
            return BigInteger.valueOf(this._long_value);
        }
        return this._big_int_value;
    }

    @Override
    public BigDecimal bigDecimalValue() {
        if (this.isNullValue()) {
            return null;
        }
        if (this._big_int_value == null) {
            return BigDecimal.valueOf(this._long_value);
        }
        return new BigDecimal(this._big_int_value);
    }

    @Override
    public boolean isNumericValue() {
        return !this.isNullValue();
    }

    @Override
    public void setValue(int value) {
        this.setValue((long)value);
    }

    @Override
    public void setValue(long value) {
        this.checkForLock();
        this.doSetValue(value, false);
    }

    @Override
    public void setValue(Number value) {
        this.checkForLock();
        if (value == null) {
            this.doSetValue(0L, true);
        } else if (value instanceof BigInteger) {
            BigInteger big = (BigInteger)value;
            this.doSetValue(big);
        } else if (value instanceof BigDecimal) {
            BigDecimal bd = (BigDecimal)value;
            this.doSetValue(bd.toBigInteger());
        } else {
            this.doSetValue(value.longValue(), false);
        }
    }

    @Override
    final void writeBodyTo(IonWriter writer, _Private_IonValue.SymbolTableProvider symbolTableProvider) throws IOException {
        if (this._big_int_value != null) {
            writer.writeInt(this._big_int_value);
        } else {
            writer.writeInt(this._long_value);
        }
    }

    private void doSetValue(long value, boolean isNull) {
        this._long_value = value;
        this._big_int_value = null;
        this._isNullValue(isNull);
        if (!isNull) {
            if (value < Integer.MIN_VALUE || value > Integer.MAX_VALUE) {
                this.setSize(IntegerSize.LONG);
            } else {
                this.setSize(IntegerSize.INT);
            }
        }
    }

    private void doSetValue(BigInteger value) {
        if (value.compareTo(LONG_MIN_VALUE) < 0 || value.compareTo(LONG_MAX_VALUE) > 0) {
            this.setSize(IntegerSize.BIG_INTEGER);
            this._long_value = 0L;
            this._big_int_value = value;
            this._isNullValue(false);
        } else {
            this.doSetValue(value.longValue(), false);
        }
    }

    private void setSize(IntegerSize size) {
        this._setMetadata(size.ordinal(), 24, 3);
    }

    @Override
    public void accept(ValueVisitor visitor2) throws Exception {
        visitor2.visit(this);
    }

    @Override
    public IntegerSize getIntegerSize() {
        if (this.isNullValue()) {
            return null;
        }
        return SIZES[this._getMetadata(24, 3)];
    }
}

