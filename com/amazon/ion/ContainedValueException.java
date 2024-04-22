/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazon.ion;

import com.amazon.ion.IonException;

public class ContainedValueException
extends IonException {
    private static final long serialVersionUID = 1L;

    public ContainedValueException() {
    }

    public ContainedValueException(String message) {
        super(message);
    }
}

