/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazon.ion;

import com.amazon.ion.IonValue;
import com.amazon.ion.UnknownSymbolException;

public interface IonText
extends IonValue {
    public String stringValue();

    public void setValue(String var1);

    @Override
    public IonText clone() throws UnknownSymbolException;
}

