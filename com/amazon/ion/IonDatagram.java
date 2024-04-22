/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazon.ion;

import com.amazon.ion.ContainedValueException;
import com.amazon.ion.IonContainer;
import com.amazon.ion.IonException;
import com.amazon.ion.IonSequence;
import com.amazon.ion.IonValue;
import com.amazon.ion.SymbolTable;
import com.amazon.ion.UnknownSymbolException;
import com.amazon.ion.ValueFactory;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Collection;
import java.util.Iterator;
import java.util.ListIterator;

public interface IonDatagram
extends IonSequence {
    @Override
    public void add(int var1, IonValue var2) throws ContainedValueException, NullPointerException;

    @Override
    public ValueFactory add(int var1) throws ContainedValueException, NullPointerException;

    @Override
    public boolean addAll(int var1, Collection<? extends IonValue> var2);

    @Override
    public IonValue set(int var1, IonValue var2);

    @Override
    public boolean isNullValue();

    @Override
    public IonContainer getContainer();

    @Override
    public int size();

    public int systemSize();

    @Override
    public IonValue get(int var1) throws IndexOutOfBoundsException;

    public IonValue systemGet(int var1) throws IndexOutOfBoundsException;

    @Override
    public Iterator<IonValue> iterator();

    public ListIterator<IonValue> systemIterator();

    public int byteSize() throws IonException;

    @Deprecated
    public byte[] toBytes() throws IonException;

    public byte[] getBytes() throws IonException;

    @Deprecated
    public int getBytes(byte[] var1) throws IonException;

    @Deprecated
    public int getBytes(byte[] var1, int var2) throws IonException;

    public int getBytes(OutputStream var1) throws IOException, IonException;

    @Override
    public SymbolTable getSymbolTable();

    @Override
    public void addTypeAnnotation(String var1);

    @Override
    public void makeNull();

    @Override
    public IonDatagram clone() throws UnknownSymbolException;
}

