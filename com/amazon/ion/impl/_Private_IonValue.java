/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazon.ion.impl;

import com.amazon.ion.IonValue;
import com.amazon.ion.SymbolTable;
import com.amazon.ion.SymbolToken;
import java.io.PrintWriter;

public interface _Private_IonValue
extends IonValue {
    public int getElementId();

    public SymbolToken getFieldNameSymbol(SymbolTableProvider var1);

    public SymbolToken[] getTypeAnnotationSymbols(SymbolTableProvider var1);

    public int findTypeAnnotation(String var1);

    public void setSymbolTable(SymbolTable var1);

    public SymbolTable getAssignedSymbolTable();

    public void dump(PrintWriter var1);

    public String validate();

    public static interface SymbolTableProvider {
        public SymbolTable getSymbolTable();
    }
}

