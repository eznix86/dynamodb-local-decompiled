/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazon.ion;

import com.amazon.ion.IntegerSize;
import com.amazon.ion.IonNumber;
import com.amazon.ion.NullValueException;
import com.amazon.ion.UnknownSymbolException;
import java.math.BigDecimal;
import java.math.BigInteger;

public interface IonInt
extends IonNumber {
    public int intValue() throws NullValueException;

    public long longValue() throws NullValueException;

    public BigInteger bigIntegerValue();

    @Override
    public BigDecimal bigDecimalValue();

    public IntegerSize getIntegerSize();

    public void setValue(int var1);

    public void setValue(long var1);

    public void setValue(Number var1);

    @Override
    public IonInt clone() throws UnknownSymbolException;
}

