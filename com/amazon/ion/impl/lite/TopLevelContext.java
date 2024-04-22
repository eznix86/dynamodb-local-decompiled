/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazon.ion.impl.lite;

import com.amazon.ion.SymbolTable;
import com.amazon.ion.impl.lite.IonContext;
import com.amazon.ion.impl.lite.IonDatagramLite;
import com.amazon.ion.impl.lite.IonSystemLite;

final class TopLevelContext
implements IonContext {
    private final IonDatagramLite _datagram;
    private final SymbolTable _symbols;

    private TopLevelContext(SymbolTable symbols, IonDatagramLite datagram) {
        assert (datagram != null);
        this._symbols = symbols;
        this._datagram = datagram;
    }

    static TopLevelContext wrap(SymbolTable symbols, IonDatagramLite datagram) {
        TopLevelContext context = new TopLevelContext(symbols, datagram);
        return context;
    }

    @Override
    public IonDatagramLite getContextContainer() {
        return this._datagram;
    }

    @Override
    public SymbolTable getContextSymbolTable() {
        return this._symbols;
    }

    @Override
    public IonSystemLite getSystem() {
        return this._datagram.getSystem();
    }
}

