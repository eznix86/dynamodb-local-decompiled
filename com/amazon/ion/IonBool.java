/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazon.ion;

import com.amazon.ion.IonValue;
import com.amazon.ion.NullValueException;
import com.amazon.ion.UnknownSymbolException;

public interface IonBool
extends IonValue {
    public boolean booleanValue() throws NullValueException;

    public void setValue(boolean var1);

    public void setValue(Boolean var1);

    @Override
    public IonBool clone() throws UnknownSymbolException;
}

