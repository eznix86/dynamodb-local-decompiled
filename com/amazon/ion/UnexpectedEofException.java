/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazon.ion;

import com.amazon.ion.IonException;

public class UnexpectedEofException
extends IonException {
    private static final long serialVersionUID = 1L;

    public UnexpectedEofException() {
    }

    public UnexpectedEofException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnexpectedEofException(String message) {
        super(message);
    }

    public UnexpectedEofException(Throwable cause) {
        super(cause);
    }
}

