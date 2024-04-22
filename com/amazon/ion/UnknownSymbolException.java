/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazon.ion;

import com.amazon.ion.IonException;

public class UnknownSymbolException
extends IonException {
    private static final long serialVersionUID = 1L;
    private final int mySid;
    private final String myText;

    public UnknownSymbolException(int sid) {
        this.mySid = sid;
        this.myText = null;
    }

    public UnknownSymbolException(String message) {
        this.myText = message;
        this.mySid = 0;
    }

    public int getSid() {
        return this.mySid;
    }

    @Override
    public String getMessage() {
        if (this.myText == null) {
            return "Unknown symbol text for $" + this.mySid;
        }
        return this.myText;
    }
}

