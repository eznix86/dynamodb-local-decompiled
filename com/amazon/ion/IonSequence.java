/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazon.ion;

import com.amazon.ion.ContainedValueException;
import com.amazon.ion.IonContainer;
import com.amazon.ion.IonValue;
import com.amazon.ion.NullValueException;
import com.amazon.ion.UnknownSymbolException;
import com.amazon.ion.ValueFactory;
import java.util.Collection;
import java.util.List;
import java.util.ListIterator;

public interface IonSequence
extends IonContainer,
List<IonValue> {
    @Override
    public IonValue get(int var1) throws NullValueException, IndexOutOfBoundsException;

    @Override
    public boolean add(IonValue var1) throws ContainedValueException, NullPointerException;

    public ValueFactory add();

    @Override
    public void add(int var1, IonValue var2) throws ContainedValueException, NullPointerException;

    public ValueFactory add(int var1);

    @Override
    public IonValue set(int var1, IonValue var2);

    @Override
    public IonValue remove(int var1);

    @Override
    public boolean remove(Object var1);

    @Override
    public boolean removeAll(Collection<?> var1);

    @Override
    public boolean retainAll(Collection<?> var1);

    @Override
    public boolean contains(Object var1);

    @Override
    public boolean containsAll(Collection<?> var1);

    @Override
    public int indexOf(Object var1);

    @Override
    public int lastIndexOf(Object var1);

    @Override
    public boolean addAll(Collection<? extends IonValue> var1);

    @Override
    public boolean addAll(int var1, Collection<? extends IonValue> var2);

    @Override
    public ListIterator<IonValue> listIterator();

    @Override
    public ListIterator<IonValue> listIterator(int var1);

    @Override
    public List<IonValue> subList(int var1, int var2);

    public IonValue[] toArray();

    @Override
    public <T> T[] toArray(T[] var1);

    public <T extends IonValue> T[] extract(Class<T> var1);

    @Override
    public IonSequence clone() throws UnknownSymbolException;
}

