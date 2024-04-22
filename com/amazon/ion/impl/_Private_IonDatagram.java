/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazon.ion.impl;

import com.amazon.ion.IonDatagram;
import com.amazon.ion.SymbolTable;
import com.amazon.ion.impl._Private_IonValue;

public interface _Private_IonDatagram
extends IonDatagram,
_Private_IonValue {
    public void appendTrailingSymbolTable(SymbolTable var1);
}

