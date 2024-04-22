/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazon.ion;

public interface SymbolToken {
    public static final SymbolToken[] EMPTY_ARRAY = new SymbolToken[0];

    public String getText();

    public String assumeText();

    public int getSid();
}

