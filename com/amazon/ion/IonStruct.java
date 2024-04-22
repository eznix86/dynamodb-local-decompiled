/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazon.ion;

import com.amazon.ion.ContainedValueException;
import com.amazon.ion.IonContainer;
import com.amazon.ion.IonValue;
import com.amazon.ion.NullValueException;
import com.amazon.ion.SymbolToken;
import com.amazon.ion.UnknownSymbolException;
import com.amazon.ion.ValueFactory;
import java.util.Map;

public interface IonStruct
extends IonContainer {
    @Override
    public int size() throws NullValueException;

    public boolean containsKey(Object var1);

    public boolean containsValue(Object var1);

    public IonValue get(String var1);

    public void put(String var1, IonValue var2) throws ContainedValueException;

    public ValueFactory put(String var1);

    public void putAll(Map<? extends String, ? extends IonValue> var1);

    public void add(String var1, IonValue var2) throws ContainedValueException;

    public void add(SymbolToken var1, IonValue var2) throws ContainedValueException;

    public ValueFactory add(String var1);

    public IonValue remove(String var1);

    public boolean removeAll(String ... var1);

    public boolean retainAll(String ... var1);

    @Override
    public IonStruct clone() throws UnknownSymbolException;

    public IonStruct cloneAndRemove(String ... var1) throws UnknownSymbolException;

    public IonStruct cloneAndRetain(String ... var1) throws UnknownSymbolException;
}

