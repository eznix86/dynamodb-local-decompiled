/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazon.ion;

import com.amazon.ion.IonNumber;
import com.amazon.ion.NullValueException;
import com.amazon.ion.UnknownSymbolException;
import java.math.BigDecimal;

public interface IonFloat
extends IonNumber {
    public float floatValue() throws NullValueException;

    public double doubleValue() throws NullValueException;

    @Override
    public BigDecimal bigDecimalValue() throws NullValueException;

    public void setValue(float var1);

    public void setValue(double var1);

    public void setValue(BigDecimal var1);

    @Override
    public boolean isNumericValue();

    @Override
    public IonFloat clone() throws UnknownSymbolException;
}

