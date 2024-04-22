/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  com.amazonaws.services.dynamodbv2.model.DescribeTimeToLiveRequest
 *  com.amazonaws.services.dynamodbv2.model.DescribeTimeToLiveResult
 *  com.amazonaws.services.dynamodbv2.model.TimeToLiveDescription
 */
package com.amazonaws.services.dynamodbv2.local.shared.access.api.ttl;

import com.amazonaws.services.dynamodbv2.local.shared.access.LocalDBAccess;
import com.amazonaws.services.dynamodbv2.local.shared.access.TableInfo;
import com.amazonaws.services.dynamodbv2.local.shared.access.api.DynamoDbApiFunction;
import com.amazonaws.services.dynamodbv2.model.DescribeTimeToLiveRequest;
import com.amazonaws.services.dynamodbv2.model.DescribeTimeToLiveResult;
import com.amazonaws.services.dynamodbv2.model.TimeToLiveDescription;

public class DescribeTimeToLiveFunction
extends DynamoDbApiFunction<DescribeTimeToLiveRequest, DescribeTimeToLiveResult> {
    public DescribeTimeToLiveFunction(LocalDBAccess dbAccess) {
        super(dbAccess);
    }

    @Override
    public DescribeTimeToLiveResult apply(DescribeTimeToLiveRequest describeTimeToLiveRequest) {
        DescribeTimeToLiveResult describeTimeToLiveResult = new DescribeTimeToLiveResult();
        String tableName = describeTimeToLiveRequest.getTableName();
        this.validateTableName(tableName);
        TableInfo tableInfo = this.validateTableExists(tableName);
        TimeToLiveDescription timeToLiveDescription = new TimeToLiveDescription();
        timeToLiveDescription.setAttributeName(tableInfo.getTimeToLiveSpecification().getAttributeName());
        String timeToLiveStatus = tableInfo.getTimeToLiveSpecification().getEnabled() != false ? "ENABLED" : "DISABLED";
        timeToLiveDescription.setTimeToLiveStatus(timeToLiveStatus);
        describeTimeToLiveResult.setTimeToLiveDescription(timeToLiveDescription);
        return describeTimeToLiveResult;
    }
}

