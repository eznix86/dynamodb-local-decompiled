/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazon.ion;

import com.amazon.ion.IonWriter;
import com.amazon.ion.SymbolToken;
import java.io.IOException;
import java.util.Iterator;

public interface SymbolTable {
    public static final int UNKNOWN_SYMBOL_ID = -1;

    public String getName();

    public int getVersion();

    public boolean isLocalTable();

    public boolean isSharedTable();

    public boolean isSubstitute();

    public boolean isSystemTable();

    public boolean isReadOnly();

    public void makeReadOnly();

    public SymbolTable getSystemSymbolTable();

    public String getIonVersionId();

    public SymbolTable[] getImportedTables();

    public int getImportedMaxId();

    public int getMaxId();

    public SymbolToken intern(String var1);

    public SymbolToken find(String var1);

    public int findSymbol(String var1);

    public String findKnownSymbol(int var1);

    public Iterator<String> iterateDeclaredSymbolNames();

    public void writeTo(IonWriter var1) throws IOException;
}

