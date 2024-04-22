/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazon.ion.impl;

import com.amazon.ion.SymbolTable;
import com.amazon.ion.SymbolToken;
import com.amazon.ion.impl.IonReaderContinuableCore;
import java.util.Iterator;

interface IonReaderContinuableApplication
extends IonReaderContinuableCore {
    public SymbolTable getSymbolTable();

    public String[] getTypeAnnotations();

    public Iterator<String> iterateTypeAnnotations();

    public String getFieldName();

    public SymbolToken[] getTypeAnnotationSymbols();

    public SymbolToken getFieldNameSymbol();

    public SymbolToken symbolValue();
}

