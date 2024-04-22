/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazon.ion.impl.lite;

import com.amazon.ion.Decimal;
import com.amazon.ion.IonDecimal;
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

final class IonDecimalLite
extends IonValueLite
implements IonDecimal {
    private static final int HASH_SIGNATURE = IonType.DECIMAL.toString().hashCode();
    private static final int NEGATIVE_ZERO_HASH_SIGNATURE = "NEGATIVE ZERO".hashCode();
    private BigDecimal _decimal_value;

    public static boolean isNegativeZero(float value) {
        if (value != 0.0f) {
            return false;
        }
        return (Float.floatToRawIntBits(value) & Integer.MIN_VALUE) != 0;
    }

    public static boolean isNegativeZero(double value) {
        if (value != 0.0) {
            return false;
        }
        return (Double.doubleToLongBits(value) & Long.MIN_VALUE) != 0L;
    }

    IonDecimalLite(ContainerlessContext context, boolean isNull) {
        super(context, isNull);
    }

    IonDecimalLite(IonDecimalLite existing, IonContext context) {
        super(existing, context);
        this._decimal_value = existing._decimal_value;
    }

    @Override
    IonValueLite shallowClone(IonContext parentContext) {
        return new IonDecimalLite(this, parentContext);
    }

    @Override
    public IonDecimalLite clone() {
        return (IonDecimalLite)this.shallowClone(ContainerlessContext.wrap(this.getSystem()));
    }

    @Override
    int hashSignature() {
        return HASH_SIGNATURE;
    }

    @Override
    int scalarHashCode() {
        int result = HASH_SIGNATURE;
        Decimal dec = this.decimalValue();
        result ^= dec.hashCode();
        if (dec.isNegativeZero()) {
            result ^= NEGATIVE_ZERO_HASH_SIGNATURE;
        }
        return this.hashTypeAnnotations(result);
    }

    @Override
    public IonType getType() {
        return IonType.DECIMAL;
    }

    @Override
    public float floatValue() throws NullValueException {
        if (this._isNullValue()) {
            throw new NullValueException();
        }
        float f = this._decimal_value.floatValue();
        return f;
    }

    @Override
    public double doubleValue() throws NullValueException {
        if (this._isNullValue()) {
            throw new NullValueException();
        }
        double d = this._decimal_value.doubleValue();
        return d;
    }

    @Override
    public BigDecimal bigDecimalValue() throws NullValueException {
        return Decimal.bigDecimalValue(this._decimal_value);
    }

    @Override
    public Decimal decimalValue() throws NullValueException {
        return Decimal.valueOf(this._decimal_value);
    }

    @Override
    public boolean isNumericValue() {
        return !this.isNullValue();
    }

    @Override
    public void setValue(long value) {
        this.setValue(Decimal.valueOf(value));
    }

    @Override
    public void setValue(float value) {
        this.setValue(Decimal.valueOf(value));
    }

    @Override
    public void setValue(double value) {
        this.setValue(Decimal.valueOf(value));
    }

    @Override
    public void setValue(BigDecimal value) {
        this.checkForLock();
        this._decimal_value = value;
        this._isNullValue(value == null);
    }

    @Override
    final void writeBodyTo(IonWriter writer, _Private_IonValue.SymbolTableProvider symbolTableProvider) throws IOException {
        writer.writeDecimal(this._decimal_value);
    }

    @Override
    public void accept(ValueVisitor visitor2) throws Exception {
        visitor2.visit(this);
    }
}

