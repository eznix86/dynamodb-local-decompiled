/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazon.ion.impl;

import com.amazon.ion.IonType;
import com.amazon.ion.SymbolToken;
import com.amazon.ion.util._Private_FastAppendable;
import java.io.IOException;

public abstract class _Private_MarkupCallback {
    private final _Private_FastAppendable myAppendable;

    public _Private_MarkupCallback(_Private_FastAppendable appendable) {
        this.myAppendable = appendable;
    }

    public final _Private_FastAppendable getAppendable() {
        return this.myAppendable;
    }

    public void beforeValue(IonType iType) throws IOException {
    }

    public void afterValue(IonType iType) throws IOException {
    }

    public void beforeFieldName(IonType iType, SymbolToken name) throws IOException {
    }

    public void afterFieldName(IonType iType, SymbolToken name) throws IOException {
    }

    public void afterStepIn(IonType containerType) throws IOException {
    }

    public void beforeStepOut(IonType containerType) throws IOException {
    }

    public void beforeSeparator(IonType containerType) throws IOException {
    }

    public void afterSeparator(IonType containerType) throws IOException {
    }

    public void beforeAnnotations(IonType iType) throws IOException {
    }

    public void afterAnnotations(IonType iType) throws IOException {
    }

    public void beforeEachAnnotation(IonType iType, SymbolToken annotation) throws IOException {
    }

    public void afterEachAnnotation(IonType iType, SymbolToken annotation) throws IOException {
    }
}

