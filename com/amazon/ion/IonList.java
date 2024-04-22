/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazon.ion;

import com.amazon.ion.IonSequence;
import com.amazon.ion.IonValue;
import com.amazon.ion.UnknownSymbolException;
import java.util.Collection;

public interface IonList
extends IonSequence,
IonValue,
Collection<IonValue> {
    @Override
    public IonList clone() throws UnknownSymbolException;
}

