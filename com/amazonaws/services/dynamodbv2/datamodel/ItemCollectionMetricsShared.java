/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazonaws.services.dynamodbv2.datamodel;

import com.amazonaws.services.dynamodbv2.datamodel.AttributeNameShared;
import com.amazonaws.services.dynamodbv2.datamodel.DocumentNode;
import java.util.Map;

public interface ItemCollectionMetricsShared {
    public Map<? extends AttributeNameShared, ? extends DocumentNode> getCollectionKey();

    public double getLowerBound();

    public double getUpperBound();
}

