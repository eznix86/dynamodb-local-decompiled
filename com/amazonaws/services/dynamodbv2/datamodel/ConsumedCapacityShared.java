/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazonaws.services.dynamodbv2.datamodel;

import java.util.Map;

public interface ConsumedCapacityShared {
    public Double getTotalCapacityUnits();

    public String getTableName();

    public Double getBaseTableCapacityUnits();

    public Map<String, Double> getLocalSecondaryIndexesCapacityUnits();

    public Map<String, Double> getGlobalSecondaryIndexesCapacityUnits();
}

