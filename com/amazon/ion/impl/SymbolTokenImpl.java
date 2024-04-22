/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazon.ion.impl;

import com.amazon.ion.SymbolToken;
import com.amazon.ion.UnknownSymbolException;
import com.amazon.ion.impl._Private_SymbolToken;

final class SymbolTokenImpl
implements _Private_SymbolToken {
    private final String myText;
    private final int mySid;

    SymbolTokenImpl(String text, int sid) {
        assert (text != null || sid >= 0) : "Neither text nor sid is defined";
        this.myText = text;
        this.mySid = sid;
    }

    SymbolTokenImpl(int sid) {
        assert (sid >= 0) : "sid is undefined";
        this.myText = null;
        this.mySid = sid;
    }

    @Override
    public String getText() {
        return this.myText;
    }

    @Override
    public String assumeText() {
        if (this.myText == null) {
            throw new UnknownSymbolException(this.mySid);
        }
        return this.myText;
    }

    @Override
    public int getSid() {
        return this.mySid;
    }

    public String toString() {
        return "SymbolToken::{text:" + this.myText + ",id:" + this.mySid + "}";
    }

    @Override
    public boolean equals(Object o2) {
        if (this == o2) {
            return true;
        }
        if (o2 == null || !(o2 instanceof SymbolToken)) {
            return false;
        }
        SymbolToken other = (SymbolToken)o2;
        if (this.getText() == null || other.getText() == null) {
            return this.getText() == other.getText();
        }
        return this.getText().equals(other.getText());
    }

    @Override
    public int hashCode() {
        if (this.getText() != null) {
            return this.getText().hashCode();
        }
        return 0;
    }
}

