/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazonaws.services.dynamodbv2.local.shared.partiql.util;

import com.amazonaws.services.dynamodbv2.exceptions.DynamoDBLocalServiceException;

public class ObjectExceptionPair<T> {
    private final T object;
    private final DynamoDBLocalServiceException exception;

    public ObjectExceptionPair(T object, DynamoDBLocalServiceException exception) {
        this.object = object;
        this.exception = exception;
    }

    public T getObject() {
        return this.object;
    }

    public DynamoDBLocalServiceException getException() {
        return this.exception;
    }

    public boolean hasException() {
        return this.exception != null;
    }
}

