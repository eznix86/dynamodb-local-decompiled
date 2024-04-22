/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazon.ion;

import com.amazon.ion.IonBinaryWriter;
import com.amazon.ion.IonCatalog;
import com.amazon.ion.IonContainer;
import com.amazon.ion.IonDatagram;
import com.amazon.ion.IonLoader;
import com.amazon.ion.IonReader;
import com.amazon.ion.IonTextReader;
import com.amazon.ion.IonTimestamp;
import com.amazon.ion.IonValue;
import com.amazon.ion.IonWriter;
import com.amazon.ion.SymbolTable;
import com.amazon.ion.UnsupportedIonVersionException;
import com.amazon.ion.ValueFactory;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.util.Date;
import java.util.Iterator;

public interface IonSystem
extends ValueFactory {
    public SymbolTable getSystemSymbolTable();

    public SymbolTable getSystemSymbolTable(String var1) throws UnsupportedIonVersionException;

    public IonCatalog getCatalog();

    public SymbolTable newLocalSymbolTable(SymbolTable ... var1);

    public SymbolTable newSharedSymbolTable(String var1, int var2, Iterator<String> var3, SymbolTable ... var4);

    public SymbolTable newSharedSymbolTable(IonReader var1);

    public SymbolTable newSharedSymbolTable(IonReader var1, boolean var2);

    public IonLoader newLoader();

    public IonLoader newLoader(IonCatalog var1);

    public IonLoader getLoader();

    public Iterator<IonValue> iterate(Reader var1);

    @Deprecated
    public Iterator<IonValue> iterate(InputStream var1);

    public Iterator<IonValue> iterate(String var1);

    @Deprecated
    public Iterator<IonValue> iterate(byte[] var1);

    public Iterator<IonValue> iterate(IonReader var1);

    public IonValue singleValue(String var1);

    public IonValue singleValue(byte[] var1);

    public IonValue singleValue(byte[] var1, int var2, int var3);

    public IonTextReader newReader(String var1);

    public IonReader newReader(byte[] var1);

    public IonReader newReader(byte[] var1, int var2, int var3);

    public IonReader newReader(InputStream var1);

    public IonReader newReader(Reader var1);

    public IonReader newReader(IonValue var1);

    public IonWriter newWriter(IonContainer var1);

    public IonWriter newTextWriter(OutputStream var1);

    public IonWriter newTextWriter(Appendable var1);

    public IonWriter newTextWriter(OutputStream var1, SymbolTable ... var2) throws IOException;

    public IonWriter newTextWriter(Appendable var1, SymbolTable ... var2) throws IOException;

    public IonWriter newBinaryWriter(OutputStream var1, SymbolTable ... var2);

    @Deprecated
    public IonBinaryWriter newBinaryWriter();

    @Deprecated
    public IonBinaryWriter newBinaryWriter(SymbolTable ... var1);

    public IonDatagram newDatagram();

    public IonDatagram newDatagram(IonValue var1);

    public IonDatagram newDatagram(SymbolTable ... var1);

    public IonValue newValue(IonReader var1);

    public IonTimestamp newUtcTimestampFromMillis(long var1);

    public IonTimestamp newUtcTimestamp(Date var1);

    public IonTimestamp newCurrentUtcTimestamp();
}

