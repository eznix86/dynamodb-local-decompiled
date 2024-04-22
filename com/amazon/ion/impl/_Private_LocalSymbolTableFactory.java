/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazon.ion.impl;

import com.amazon.ion.IonCatalog;
import com.amazon.ion.IonReader;
import com.amazon.ion.SymbolTable;

public interface _Private_LocalSymbolTableFactory {
    public SymbolTable newLocalSymtab(IonCatalog var1, IonReader var2, boolean var3);

    public SymbolTable newLocalSymtab(SymbolTable var1, SymbolTable ... var2);
}

