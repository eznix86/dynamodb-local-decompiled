/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazon.ion;

import com.amazon.ion.IonException;

@Deprecated
public class EmptySymbolException
extends IonException {
    private static final long serialVersionUID = -7801632953459636349L;

    public EmptySymbolException() {
        super("Symbols must contain at least one character.");
    }
}

