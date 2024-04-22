/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazon.ion;

import com.amazon.ion.IonException;

public class ReadOnlyValueException
extends IonException {
    private static final long serialVersionUID = 1L;

    public ReadOnlyValueException() {
        super("Read-only IonValue cannot be modified");
    }

    public ReadOnlyValueException(Class type) {
        super("Cannot modify read-only instance of " + type);
    }
}

