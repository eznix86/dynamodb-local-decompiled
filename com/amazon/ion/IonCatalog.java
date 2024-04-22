/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazon.ion;

import com.amazon.ion.SymbolTable;

public interface IonCatalog {
    public SymbolTable getTable(String var1);

    public SymbolTable getTable(String var1, int var2);
}

