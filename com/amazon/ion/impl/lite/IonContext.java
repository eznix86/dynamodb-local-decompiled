/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazon.ion.impl.lite;

import com.amazon.ion.SymbolTable;
import com.amazon.ion.impl.lite.IonContainerLite;
import com.amazon.ion.impl.lite.IonSystemLite;

interface IonContext {
    public IonContainerLite getContextContainer();

    public IonSystemLite getSystem();

    public SymbolTable getContextSymbolTable();
}

