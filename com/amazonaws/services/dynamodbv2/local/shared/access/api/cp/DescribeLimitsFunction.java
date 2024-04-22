/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  com.amazonaws.services.dynamodbv2.model.DescribeLimitsRequest
 *  com.amazonaws.services.dynamodbv2.model.DescribeLimitsResult
 */
package com.amazonaws.services.dynamodbv2.local.shared.access.api.cp;

import com.amazonaws.services.dynamodbv2.local.shared.access.api.AbstractDynamoDbApiFunction;
import com.amazonaws.services.dynamodbv2.model.DescribeLimitsRequest;
import com.amazonaws.services.dynamodbv2.model.DescribeLimitsResult;

public class DescribeLimitsFunction
extends AbstractDynamoDbApiFunction<DescribeLimitsRequest, DescribeLimitsResult> {
    public static final Long ACC_MAX_READ_CAPACITY_UNITS = 80000L;
    public static final Long ACC_MAX_WRITE_CAPACITY_UNITS = 80000L;
    public static final Long TABLE_MAX_WRITE_CAPACITY_UNITS = 40000L;
    public static final Long TABLE_MAX_READ_CAPACITY_UNITS = 40000L;

    @Override
    public DescribeLimitsResult apply(DescribeLimitsRequest describeLimitsRequest) {
        DescribeLimitsResult dlr = new DescribeLimitsResult();
        dlr.setAccountMaxWriteCapacityUnits(ACC_MAX_WRITE_CAPACITY_UNITS);
        dlr.setAccountMaxReadCapacityUnits(ACC_MAX_READ_CAPACITY_UNITS);
        dlr.setTableMaxReadCapacityUnits(TABLE_MAX_READ_CAPACITY_UNITS);
        dlr.setTableMaxWriteCapacityUnits(TABLE_MAX_WRITE_CAPACITY_UNITS);
        return dlr;
    }
}

