/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazonaws.services.dynamodbv2.local.shared.exceptions;

import com.amazonaws.services.dynamodbv2.local.shared.exceptions.LocalDBAccessExceptionType;

public class LocalDBAccessException
extends RuntimeException {
    private static final long serialVersionUID = 5151408033684823183L;
    private final LocalDBAccessExceptionType type;

    public LocalDBAccessException(LocalDBAccessExceptionType type) {
        super(type.getMessage());
        this.type = type;
    }

    public LocalDBAccessException(LocalDBAccessExceptionType type, String message) {
        super(message);
        this.type = type;
    }

    public LocalDBAccessExceptionType getType() {
        return this.type;
    }
}

