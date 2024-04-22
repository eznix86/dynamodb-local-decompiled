/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazon.ion;

import com.amazon.ion.IonCatalog;
import com.amazon.ion.SymbolTable;

public interface IonMutableCatalog
extends IonCatalog {
    public void putTable(SymbolTable var1);
}

