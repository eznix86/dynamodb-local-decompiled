/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  com.amazonaws.services.dynamodbv2.model.ListTablesRequest
 *  com.amazonaws.services.dynamodbv2.model.ListTablesResult
 */
package com.amazonaws.services.dynamodbv2.local.shared.access.api.cp;

import com.amazonaws.services.dynamodbv2.exceptions.AWSExceptionFactory;
import com.amazonaws.services.dynamodbv2.exceptions.AmazonServiceExceptionType;
import com.amazonaws.services.dynamodbv2.local.shared.access.ListTablesResultInfo;
import com.amazonaws.services.dynamodbv2.local.shared.access.LocalDBAccess;
import com.amazonaws.services.dynamodbv2.local.shared.access.api.cp.ControlPlaneFunction;
import com.amazonaws.services.dynamodbv2.local.shared.exceptions.LocalDBClientExceptionMessage;
import com.amazonaws.services.dynamodbv2.model.ListTablesRequest;
import com.amazonaws.services.dynamodbv2.model.ListTablesResult;

public class ListTablesFunction
extends ControlPlaneFunction<ListTablesRequest, ListTablesResult> {
    public ListTablesFunction(LocalDBAccess dbAccess) {
        super(dbAccess);
    }

    @Override
    public ListTablesResult apply(ListTablesRequest listTablesRequest) {
        long limit2 = this.validateLimitValueListTables(listTablesRequest.getLimit());
        String exclusiveStartTableName = listTablesRequest.getExclusiveStartTableName();
        if (exclusiveStartTableName != null) {
            this.validateTableName(exclusiveStartTableName);
        }
        ListTablesResultInfo initResults = this.dbAccess.listTables(exclusiveStartTableName, limit2);
        return new ListTablesResult().withTableNames(initResults.getTableNames()).withLastEvaluatedTableName(initResults.getLastEvaluatedTableName());
    }

    private long validateLimitValueListTables(Integer limitInit) {
        long limit2 = this.validateLimitValue(limitInit);
        if (limit2 > 100L) {
            throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, LocalDBClientExceptionMessage.INVALID_LIMIT_TOO_BIG.getMessage());
        }
        return limit2 == -1L ? 100L : limit2;
    }
}

