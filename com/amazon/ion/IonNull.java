/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazon.ion;

import com.amazon.ion.IonValue;
import com.amazon.ion.UnknownSymbolException;

public interface IonNull
extends IonValue {
    @Override
    public IonNull clone() throws UnknownSymbolException;
}

