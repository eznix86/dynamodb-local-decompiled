/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazon.ion.impl;

import com.amazon.ion.IonContainer;
import com.amazon.ion.IonReader;
import com.amazon.ion.IonStruct;
import com.amazon.ion.IonSystem;
import com.amazon.ion.IonValue;
import com.amazon.ion.IonWriter;
import com.amazon.ion.SymbolTable;
import java.io.InputStream;
import java.io.Reader;
import java.util.Iterator;

public interface _Private_IonSystem
extends IonSystem {
    public SymbolTable newSharedSymbolTable(IonStruct var1);

    public Iterator<IonValue> systemIterate(String var1);

    public Iterator<IonValue> systemIterate(Reader var1);

    public Iterator<IonValue> systemIterate(IonReader var1);

    public IonReader newSystemReader(Reader var1);

    public IonReader newSystemReader(byte[] var1);

    public IonReader newSystemReader(byte[] var1, int var2, int var3);

    public IonReader newSystemReader(String var1);

    public IonReader newSystemReader(InputStream var1);

    public IonReader newSystemReader(IonValue var1);

    public IonWriter newTreeWriter(IonContainer var1);

    public IonWriter newTreeSystemWriter(IonContainer var1);

    public boolean valueIsSharedSymbolTable(IonValue var1);

    public boolean isStreamCopyOptimized();
}

