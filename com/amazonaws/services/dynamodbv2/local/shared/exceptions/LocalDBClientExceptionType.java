/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazonaws.services.dynamodbv2.local.shared.exceptions;

public enum LocalDBClientExceptionType {
    INVALID_VALUES("Values are not valid"),
    UNEXPECTED_EXCEPTION("An unexpected exception occurred."),
    UNREACHABLE_CODE("Unreachable code has been reached"),
    DDB_REQUEST_SERIALIZATION_EXCEPTION("Unable to serialize request"),
    DDB_RESPONSE_SERIALIZATION_EXCEPTION("Unable to serialize response");

    private final String message;

    private LocalDBClientExceptionType(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
}

