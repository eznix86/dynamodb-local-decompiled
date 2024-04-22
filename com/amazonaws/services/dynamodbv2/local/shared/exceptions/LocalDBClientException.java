/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazonaws.services.dynamodbv2.local.shared.exceptions;

import com.amazonaws.services.dynamodbv2.local.shared.exceptions.LocalDBClientExceptionType;

public class LocalDBClientException
extends RuntimeException {
    private static final long serialVersionUID = -6231023559635437857L;
    private final LocalDBClientExceptionType type;

    public LocalDBClientException(LocalDBClientExceptionType type) {
        super(type.getMessage());
        this.type = type;
    }

    public LocalDBClientException(LocalDBClientExceptionType type, String message) {
        super(message);
        this.type = type;
    }

    public LocalDBClientExceptionType getType() {
        return this.type;
    }
}

