/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  com.amazonaws.services.dynamodbv2.model.BatchStatementError
 *  com.amazonaws.services.dynamodbv2.model.BatchStatementResponse
 */
package com.amazonaws.services.dynamodbv2.local.shared.partiql.model;

import com.amazonaws.services.dynamodbv2.local.shared.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.BatchStatementError;
import com.amazonaws.services.dynamodbv2.model.BatchStatementResponse;
import java.util.Map;

public class PartiQLBatchResponseInternal
extends BatchStatementResponse {
    private final Map<String, AttributeValue> item;
    private final String tableName;
    private final BatchStatementError error;

    public PartiQLBatchResponseInternal(Map<String, AttributeValue> item, String tableName, BatchStatementError error) {
        this.item = item;
        this.tableName = tableName;
        this.error = error;
    }

    public Map<String, AttributeValue> getItemInternal() {
        return this.item;
    }

    public String getTableNameInternal() {
        return this.tableName;
    }

    public BatchStatementError getErrorInternal() {
        return this.error;
    }
}

