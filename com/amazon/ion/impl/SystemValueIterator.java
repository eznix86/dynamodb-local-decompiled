/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazon.ion.impl;

import com.amazon.ion.IonCatalog;
import com.amazon.ion.IonSystem;
import com.amazon.ion.IonValue;
import com.amazon.ion.SymbolTable;
import com.amazon.ion.impl.IonBinary;
import java.io.Closeable;
import java.io.IOException;
import java.util.Iterator;

interface SystemValueIterator
extends Closeable,
Iterator<IonValue> {
    @Override
    public boolean hasNext();

    @Override
    public IonValue next();

    @Override
    public void remove();

    public IonSystem getSystem();

    public IonCatalog getCatalog();

    public SymbolTable getSymbolTable();

    public SymbolTable getLocalSymbolTable();

    public boolean currentIsHidden();

    public IonBinary.BufferManager getBuffer();

    public void resetBuffer();

    @Override
    public void close() throws IOException;
}

