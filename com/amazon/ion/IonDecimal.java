/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazon.ion;

import com.amazon.ion.Decimal;
import com.amazon.ion.IonNumber;
import com.amazon.ion.NullValueException;
import com.amazon.ion.UnknownSymbolException;
import java.math.BigDecimal;

public interface IonDecimal
extends IonNumber {
    public float floatValue() throws NullValueException;

    public double doubleValue() throws NullValueException;

    @Override
    public BigDecimal bigDecimalValue();

    public Decimal decimalValue();

    public void setValue(long var1);

    public void setValue(float var1);

    public void setValue(double var1);

    public void setValue(BigDecimal var1);

    @Override
    public IonDecimal clone() throws UnknownSymbolException;
}

