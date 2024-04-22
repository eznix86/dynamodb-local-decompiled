/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  com.amazonaws.services.dynamodbv2.model.DescribeTableRequest
 *  com.amazonaws.services.dynamodbv2.model.DescribeTableResult
 *  com.amazonaws.services.dynamodbv2.model.TableDescription
 */
package com.amazonaws.services.dynamodbv2.local.shared.access.api.cp;

import com.amazonaws.services.dynamodbv2.local.shared.access.LocalDBAccess;
import com.amazonaws.services.dynamodbv2.local.shared.access.api.cp.ControlPlaneFunction;
import com.amazonaws.services.dynamodbv2.model.DescribeTableRequest;
import com.amazonaws.services.dynamodbv2.model.DescribeTableResult;
import com.amazonaws.services.dynamodbv2.model.TableDescription;

public class DescribeTableFunction
extends ControlPlaneFunction<DescribeTableRequest, DescribeTableResult> {
    public DescribeTableFunction(LocalDBAccess dbAccess) {
        super(dbAccess);
    }

    @Override
    public DescribeTableResult apply(DescribeTableRequest describeTableRequest) {
        String tableName = describeTableRequest.getTableName();
        this.validateTableName(tableName);
        TableDescription description = this.getTableDescriptionHelper(tableName);
        description = this.fillDescriptionHelper(description);
        return new DescribeTableResult().withTable(description);
    }
}

