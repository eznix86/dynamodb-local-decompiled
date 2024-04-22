/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazon.ion.impl;

import com.amazon.ion.IonSymbol;
import com.amazon.ion.SymbolToken;
import com.amazon.ion.impl._Private_IonValue;

public interface _Private_IonSymbol
extends IonSymbol {
    public SymbolToken symbolValue(_Private_IonValue.SymbolTableProvider var1);
}

