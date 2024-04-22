/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazonaws.services.dynamodbv2.local.shared.access;

import com.amazonaws.services.dynamodbv2.local.shared.model.AttributeValue;
import java.util.List;
import java.util.Map;

public class QueryResultInfo {
    private final List<Map<String, AttributeValue>> returnedRecords;
    private final Map<String, AttributeValue> lastEvaluatedItem;

    public QueryResultInfo(List<Map<String, AttributeValue>> returnedRecords, Map<String, AttributeValue> lastEvaluatedItem) {
        this.returnedRecords = returnedRecords;
        this.lastEvaluatedItem = lastEvaluatedItem;
    }

    public List<Map<String, AttributeValue>> getReturnedRecords() {
        return this.returnedRecords;
    }

    public Map<String, AttributeValue> getLastEvaluatedItem() {
        return this.lastEvaluatedItem;
    }
}

