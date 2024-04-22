/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazon.ion.impl.lite;

import com.amazon.ion.Decimal;
import com.amazon.ion.IonFloat;
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

final class IonFloatLite
extends IonValueLite
implements IonFloat {
    private static final int HASH_SIGNATURE = IonType.FLOAT.toString().hashCode();
    private Double _float_value;

    IonFloatLite(ContainerlessContext context, boolean isNull) {
        super(context, isNull);
    }

    IonFloatLite(IonFloatLite existing, IonContext context) {
        super(existing, context);
        this._float_value = existing._float_value;
    }

    @Override
    IonValueLite shallowClone(IonContext context) {
        return new IonFloatLite(this, context);
    }

    @Override
    public IonFloatLite clone() {
        return (IonFloatLite)this.shallowClone(ContainerlessContext.wrap(this.getSystem()));
    }

    @Override
    int hashSignature() {
        return HASH_SIGNATURE;
    }

    @Override
    int scalarHashCode() {
        int result = HASH_SIGNATURE;
        long bits = Double.doubleToLongBits(this._float_value);
        return this.hashTypeAnnotations(result ^= (int)(bits >>> 32 ^ bits));
    }

    @Override
    public IonType getType() {
        return IonType.FLOAT;
    }

    @Override
    public float floatValue() throws NullValueException {
        this.validateThisNotNull();
        return this._float_value.floatValue();
    }

    @Override
    public double doubleValue() throws NullValueException {
        this.validateThisNotNull();
        return this._float_value;
    }

    @Override
    public BigDecimal bigDecimalValue() throws NullValueException {
        if (this.isNullValue()) {
            return null;
        }
        return Decimal.valueOf(this._float_value);
    }

    @Override
    public void setValue(float value) {
        this.setValue(Double.valueOf(value));
    }

    @Override
    public void setValue(double value) {
        this.setValue((Double)value);
    }

    @Override
    public void setValue(BigDecimal value) {
        this.checkForLock();
        if (value == null) {
            this._float_value = null;
            this._isNullValue(true);
        } else {
            this.setValue(value.doubleValue());
        }
    }

    public void setValue(Double d) {
        this.checkForLock();
        this._float_value = d;
        this._isNullValue(d == null);
    }

    @Override
    final void writeBodyTo(IonWriter writer, _Private_IonValue.SymbolTableProvider symbolTableProvider) throws IOException {
        writer.writeFloat(this._float_value);
    }

    @Override
    public boolean isNumericValue() {
        return !this.isNullValue() && !this._float_value.isNaN() && !this._float_value.isInfinite();
    }

    @Override
    public void accept(ValueVisitor visitor2) throws Exception {
        visitor2.visit(this);
    }
}

