/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazon.ion;

import java.util.IdentityHashMap;

public class IonException
extends RuntimeException {
    private static final long serialVersionUID = 5769577011706279252L;

    public IonException() {
    }

    public IonException(String message) {
        super(message);
    }

    public IonException(String message, Throwable cause) {
        super(message, cause);
    }

    public IonException(Throwable cause) {
        super(cause.getMessage(), cause);
    }

    Throwable externalCause() {
        IdentityHashMap<Throwable, Throwable> seen = new IdentityHashMap<Throwable, Throwable>();
        Throwable cause = this.getCause();
        while (cause instanceof IonException) {
            if (seen.put(cause, cause) != null) {
                return null;
            }
            cause = cause.getCause();
        }
        return cause;
    }

    public <T extends Throwable> T causeOfType(Class<T> type) {
        Throwable cause;
        IdentityHashMap<Throwable, Throwable> seen = new IdentityHashMap<Throwable, Throwable>();
        for (cause = this.getCause(); cause != null && !type.isInstance(cause); cause = cause.getCause()) {
            if (seen.put(cause, cause) == null) continue;
            return null;
        }
        return (T)cause;
    }

    private <T extends Throwable> void rethrowCauseOfType(Class<T> type) throws Throwable {
        T cause = this.causeOfType(type);
        if (cause != null) {
            throw cause;
        }
    }
}

