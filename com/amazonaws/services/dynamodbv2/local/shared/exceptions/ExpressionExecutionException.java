/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazonaws.services.dynamodbv2.local.shared.exceptions;

import com.amazonaws.services.dynamodbv2.dbenv.DbExecutionError;

public class ExpressionExecutionException
extends RuntimeException {
    private final DbExecutionError errorCode;

    public ExpressionExecutionException(DbExecutionError errorCode, String errorMsg) {
        super(errorMsg);
        this.errorCode = errorCode;
    }

    public DbExecutionError getErrorCode() {
        return this.errorCode;
    }
}

