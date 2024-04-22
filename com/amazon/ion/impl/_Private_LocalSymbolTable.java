/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazon.ion.impl;

import com.amazon.ion.SymbolTable;

interface _Private_LocalSymbolTable
extends SymbolTable {
    public _Private_LocalSymbolTable makeCopy();

    public SymbolTable[] getImportedTablesNoCopy();
}

