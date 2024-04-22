/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazon.ion.impl.lite;

import com.amazon.ion.SymbolTable;
import com.amazon.ion.impl.lite.IonContainerLite;
import com.amazon.ion.impl.lite.IonContext;
import com.amazon.ion.impl.lite.IonSystemLite;

class ContainerlessContext
implements IonContext {
    private final IonSystemLite _system;
    private final SymbolTable _symbols;

    public static ContainerlessContext wrap(IonSystemLite system) {
        return new ContainerlessContext(system, null);
    }

    public static ContainerlessContext wrap(IonSystemLite system, SymbolTable symbols) {
        return new ContainerlessContext(system, symbols);
    }

    private ContainerlessContext(IonSystemLite system, SymbolTable symbols) {
        this._system = system;
        this._symbols = symbols;
    }

    @Override
    public IonContainerLite getContextContainer() {
        return null;
    }

    @Override
    public IonSystemLite getSystem() {
        return this._system;
    }

    @Override
    public SymbolTable getContextSymbolTable() {
        return this._symbols;
    }
}

