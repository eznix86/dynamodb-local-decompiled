/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazon.ion;

import com.amazon.ion.IonText;
import com.amazon.ion.NullValueException;
import com.amazon.ion.SymbolToken;
import com.amazon.ion.UnknownSymbolException;

public interface IonSymbol
extends IonText {
    @Override
    public String stringValue() throws UnknownSymbolException;

    @Deprecated
    public int getSymbolId() throws NullValueException;

    public SymbolToken symbolValue();

    @Override
    public void setValue(String var1);

    @Override
    public IonSymbol clone() throws UnknownSymbolException;
}

