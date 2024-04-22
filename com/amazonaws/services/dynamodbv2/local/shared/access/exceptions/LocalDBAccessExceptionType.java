/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazonaws.services.dynamodbv2.local.shared.access.exceptions;

public enum LocalDBAccessExceptionType {
    DATA_CORRUPTION("Stored data could not be extracted."),
    TABLE_ALREADY_EXISTS("Tried to create a table that already exists."),
    TABLE_NOT_FOUND("A non-existing table was accessed."),
    UNEXPECTED_EXCEPTION("An unexpected exception occurred.");

    private final String message;

    private LocalDBAccessExceptionType(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
}

