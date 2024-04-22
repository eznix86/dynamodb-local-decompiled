/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazon.ion;

import com.amazon.ion.IonException;

public class NullValueException
extends IonException {
    private static final long serialVersionUID = 1L;

    public NullValueException() {
    }

    public NullValueException(String message, Throwable cause) {
        super(message, cause);
    }

    public NullValueException(String message) {
        super(message);
    }

    public NullValueException(Throwable cause) {
        super(cause);
    }
}

