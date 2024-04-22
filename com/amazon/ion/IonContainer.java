/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazon.ion;

import com.amazon.ion.IonValue;
import com.amazon.ion.NullValueException;
import com.amazon.ion.UnknownSymbolException;
import java.util.Iterator;

public interface IonContainer
extends IonValue,
Iterable<IonValue> {
    public int size();

    @Override
    public Iterator<IonValue> iterator();

    public boolean remove(IonValue var1);

    public boolean isEmpty() throws NullValueException;

    public void clear();

    public void makeNull();

    @Override
    public IonContainer clone() throws UnknownSymbolException;
}

