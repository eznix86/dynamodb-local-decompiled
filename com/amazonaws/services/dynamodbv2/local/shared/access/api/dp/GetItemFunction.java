/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  com.amazonaws.services.dynamodbv2.model.ConsumedCapacity
 *  com.amazonaws.services.dynamodbv2.model.GetItemRequest
 *  com.amazonaws.services.dynamodbv2.model.GetItemResult
 *  com.amazonaws.services.dynamodbv2.model.ReturnConsumedCapacity
 */
package com.amazonaws.services.dynamodbv2.local.shared.access.api.dp;

import com.amazonaws.services.dynamodbv2.datamodel.ProjectionExpression;
import com.amazonaws.services.dynamodbv2.dbenv.DbEnv;
import com.amazonaws.services.dynamodbv2.exceptions.AWSExceptionFactory;
import com.amazonaws.services.dynamodbv2.exceptions.AmazonServiceExceptionType;
import com.amazonaws.services.dynamodbv2.local.shared.access.LocalDBAccess;
import com.amazonaws.services.dynamodbv2.local.shared.access.LocalDBInputConverter;
import com.amazonaws.services.dynamodbv2.local.shared.access.LocalDBOutputConverter;
import com.amazonaws.services.dynamodbv2.local.shared.access.LocalDBUtils;
import com.amazonaws.services.dynamodbv2.local.shared.access.LocalDBValidatorUtils;
import com.amazonaws.services.dynamodbv2.local.shared.access.TableInfo;
import com.amazonaws.services.dynamodbv2.local.shared.access.api.dp.ReadDataPlaneFunction;
import com.amazonaws.services.dynamodbv2.local.shared.exceptions.LocalDBClientExceptionMessage;
import com.amazonaws.services.dynamodbv2.local.shared.helpers.ConsumedCapacityUtils;
import com.amazonaws.services.dynamodbv2.local.shared.helpers.TransactionsEnabledMode;
import com.amazonaws.services.dynamodbv2.local.shared.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ConsumedCapacity;
import com.amazonaws.services.dynamodbv2.model.GetItemRequest;
import com.amazonaws.services.dynamodbv2.model.GetItemResult;
import com.amazonaws.services.dynamodbv2.model.ReturnConsumedCapacity;
import com.amazonaws.services.dynamodbv2.rr.ProjectionExpressionWrapper;
import java.util.Collections;
import java.util.Map;

public class GetItemFunction
extends ReadDataPlaneFunction<GetItemRequest, GetItemResult> {
    public GetItemFunction(LocalDBAccess dbAccess, DbEnv localDBEnv, LocalDBInputConverter inputConverter, LocalDBOutputConverter localDBOutputConverter, AWSExceptionFactory awsExceptionFactory, TransactionsEnabledMode transactionsEnabledMode) {
        super(dbAccess, localDBEnv, inputConverter, localDBOutputConverter, awsExceptionFactory, transactionsEnabledMode);
    }

    @Override
    public GetItemResult apply(GetItemRequest getItemRequest) {
        String tableName = getItemRequest.getTableName();
        this.validateTableName(tableName);
        this.validateAttributesToGet(getItemRequest.getAttributesToGet());
        LocalDBValidatorUtils.validateExpressions(getItemRequest, this.inputConverter);
        TableInfo tableInfo = this.validateTableExists(tableName);
        ProjectionExpressionWrapper projectionExpressionWrapper = this.inputConverter.externalToInternalProjectionExpression(getItemRequest.getProjectionExpression(), getItemRequest.getExpressionAttributeNames());
        ProjectionExpression projectionExpression = projectionExpressionWrapper == null ? null : projectionExpressionWrapper.getProjection();
        LocalDBValidatorUtils.validateNoNestedAccessToKeyAttributeInExpression(tableInfo, projectionExpressionWrapper, this.awsExceptionFactory);
        if (getItemRequest.getKey() == null) {
            throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, LocalDBClientExceptionMessage.MISSING_KEY.getMessage());
        }
        Map primaryKey = (Map)this.inputConverter.externalToInternalAttributes(getItemRequest.getKey());
        this.validateGetKey(primaryKey, tableInfo);
        Map<String, AttributeValue> item = this.dbAccess.getRecord(tableName, primaryKey);
        Map<String, AttributeValue> filteredAttributes = null;
        filteredAttributes = getItemRequest.getProjectionExpression() != null ? LocalDBUtils.projectAttributes(item, projectionExpression) : LocalDBUtils.projectAttributes(item, getItemRequest.getAttributesToGet());
        ReturnConsumedCapacity returnConsumedCapacity = this.convertReturnConsumedCapacity(getItemRequest.getReturnConsumedCapacity());
        ConsumedCapacity consumedCapacity = ConsumedCapacityUtils.computeConsumedCapacity(Collections.singletonList(item), false, false, tableName, null, true, getItemRequest.getConsistentRead() != null && getItemRequest.getConsistentRead() != false, this.transactionsMode, returnConsumedCapacity);
        return new GetItemResult().withItem(this.localDBOutputConverter.internalToExternalAttributes(filteredAttributes)).withConsumedCapacity(consumedCapacity);
    }
}

