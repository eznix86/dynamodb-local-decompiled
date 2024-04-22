/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazon.ion;

import com.amazon.ion.IonText;
import com.amazon.ion.UnknownSymbolException;

public interface IonString
extends IonText {
    @Override
    public String stringValue();

    @Override
    public void setValue(String var1);

    @Override
    public IonString clone() throws UnknownSymbolException;
}

