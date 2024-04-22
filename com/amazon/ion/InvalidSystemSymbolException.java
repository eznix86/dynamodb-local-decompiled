/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazon.ion;

import com.amazon.ion.IonException;

public class InvalidSystemSymbolException
extends IonException {
    private static final long serialVersionUID = 2206499395645594047L;
    private String myBadSymbol;

    public InvalidSystemSymbolException(String badSymbol) {
        super("Invalid system symbol '" + badSymbol + "'");
        this.myBadSymbol = badSymbol;
    }

    public String getBadSymbol() {
        return this.myBadSymbol;
    }
}

