/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  com.amazonaws.services.dynamodbv2.model.BatchGetItemRequest
 *  com.amazonaws.services.dynamodbv2.model.BatchGetItemResult
 *  com.amazonaws.services.dynamodbv2.model.ConsumedCapacity
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
import com.amazonaws.services.dynamodbv2.local.shared.model.KeysAndAttributes;
import com.amazonaws.services.dynamodbv2.model.BatchGetItemRequest;
import com.amazonaws.services.dynamodbv2.model.BatchGetItemResult;
import com.amazonaws.services.dynamodbv2.model.ConsumedCapacity;
import com.amazonaws.services.dynamodbv2.model.ReturnConsumedCapacity;
import com.amazonaws.services.dynamodbv2.rr.ProjectionExpressionWrapper;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class BatchGetItemFunction
extends ReadDataPlaneFunction<BatchGetItemRequest, BatchGetItemResult> {
    public BatchGetItemFunction(LocalDBAccess access, LocalDBInputConverter inputConverter, LocalDBOutputConverter localDBOutputConverter, AWSExceptionFactory awsExceptionFactory, DbEnv localDBEnv) {
        super(access, localDBEnv, inputConverter, localDBOutputConverter, awsExceptionFactory, TransactionsEnabledMode.TRANSACTIONS_DISABLED);
    }

    @Override
    public BatchGetItemResult apply(BatchGetItemRequest batchGetItemRequest) {
        if (batchGetItemRequest.getRequestItems() == null || batchGetItemRequest.getRequestItems().isEmpty()) {
            throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, LocalDBClientExceptionMessage.BATCH_GET_NULL_REQUESTS.getMessage());
        }
        LocalDBValidatorUtils.validateExprAndAttrToGet(batchGetItemRequest, this.inputConverter);
        Map requestItems = this.inputConverter.externalToInternalBatchGet(null, batchGetItemRequest.getRequestItems());
        int count = 0;
        for (Map.Entry entry : requestItems.entrySet()) {
            String tableName = (String)entry.getKey();
            KeysAndAttributes requests = (KeysAndAttributes)entry.getValue();
            count = this.validateBatchGetEntry(tableName, requests, count);
        }
        ReturnConsumedCapacity returnConsumedCapacity = this.convertReturnConsumedCapacity(batchGetItemRequest.getReturnConsumedCapacity());
        BatchGetItemResult batchGetResult = new BatchGetItemResult();
        HashMap<String, List<Map<String, AttributeValue>>> responses = new HashMap<String, List<Map<String, AttributeValue>>>();
        HashMap<String, List<Map<String, AttributeValue>>> chargeableResponses = new HashMap<String, List<Map<String, AttributeValue>>>();
        HashMap<String, KeysAndAttributes> unprocessedKeys = new HashMap<String, KeysAndAttributes>();
        long totalSize = 0L;
        ArrayList<ConsumedCapacity> consumedCapacities = new ArrayList<ConsumedCapacity>();
        for (Map.Entry entry : requestItems.entrySet()) {
            String tableName = (String)entry.getKey();
            KeysAndAttributes requests = (KeysAndAttributes)entry.getValue();
            totalSize = this.doBatchGet(tableName, requests, responses, chargeableResponses, unprocessedKeys, totalSize);
            if (!COMPUTE_CONSUMED_CAPACITY_TYPES.contains(returnConsumedCapacity)) continue;
            consumedCapacities.add(ConsumedCapacityUtils.computeConsumedCapacity((List)chargeableResponses.get(tableName), false, false, tableName, null, true, requests.getConsistentRead() != null && requests.getConsistentRead() != false, this.transactionsMode, returnConsumedCapacity));
            batchGetResult.withConsumedCapacity(consumedCapacities);
        }
        return batchGetResult.withResponses(this.localDBOutputConverter.internalToExternalBatchGetResponses(responses)).withUnprocessedKeys(this.localDBOutputConverter.internalToExternalBatchGetRequests(unprocessedKeys));
    }

    public long doBatchGet(String tableName, KeysAndAttributes requests, Map<String, List<Map<String, AttributeValue>>> responses, Map<String, List<Map<String, AttributeValue>>> chargeableResponses, Map<String, KeysAndAttributes> unprocessedKeys, long totalSize) {
        ArrayList<Map<String, AttributeValue>> fetchedItems = new ArrayList<Map<String, AttributeValue>>();
        ArrayList<Map<String, AttributeValue>> chargeableFetchedItems = new ArrayList<Map<String, AttributeValue>>();
        responses.put(tableName, fetchedItems);
        List<String> attributesToGet = requests.getAttributesToGet();
        String projectionExpressionString = requests.getProjectionExpression();
        ProjectionExpressionWrapper projectionExpressionWrapper = this.inputConverter.externalToInternalProjectionExpression(projectionExpressionString, requests.getExpressionAttributeNames());
        ProjectionExpression projectionExpression = projectionExpressionWrapper == null ? null : projectionExpressionWrapper.getProjection();
        LocalDBValidatorUtils.validateNoNestedAccessToKeyAttributeInExpression(this.dbAccess.getTableInfo(tableName), projectionExpressionWrapper, this.awsExceptionFactory);
        HashMap<String, KeysAndAttributes> batchInputMap = new HashMap<String, KeysAndAttributes>();
        batchInputMap.put(tableName, requests);
        ArrayList<Map<String, AttributeValue>> keys2 = new ArrayList<Map<String, AttributeValue>>(requests.getKeys());
        while (!keys2.isEmpty()) {
            Map primaryKey = (Map)keys2.remove(0);
            Map<String, AttributeValue> item = this.dbAccess.getRecord(tableName, primaryKey);
            if (item == null) continue;
            if ((totalSize += LocalDBUtils.getItemSizeBytes(item)) > 0x1000000L) {
                keys2.add(0, primaryKey);
                break;
            }
            chargeableFetchedItems.add(item);
            if (projectionExpression != null) {
                fetchedItems.add(LocalDBUtils.projectAttributes(item, projectionExpression));
                continue;
            }
            fetchedItems.add(LocalDBUtils.projectAttributes(item, attributesToGet));
        }
        if (!fetchedItems.isEmpty()) {
            responses.put(tableName, fetchedItems);
            chargeableResponses.put(tableName, chargeableFetchedItems);
        }
        if (!keys2.isEmpty()) {
            KeysAndAttributes tableUnprocessedKeys = new KeysAndAttributes().withAttributesToGet(attributesToGet).withProjectionExpression(projectionExpressionString);
            tableUnprocessedKeys.setExpressionAttributeNames(requests.getExpressionAttributeNames());
            tableUnprocessedKeys.setKeys(keys2);
            tableUnprocessedKeys.setConsistentRead(requests.getConsistentRead());
            unprocessedKeys.put(tableName, tableUnprocessedKeys);
        }
        return totalSize;
    }

    private int validateBatchGetEntry(String tableName, KeysAndAttributes requests, int count) {
        this.validateTableName(tableName);
        TableInfo info = this.validateTableExists(tableName);
        if (requests == null) {
            this.awsExceptionFactory.BATCH_GET_NULL_OR_EMPTY_KAS.throwAsException(tableName + " has empty list");
        }
        this.validateAttributesToGet(requests.getAttributesToGet());
        List<Map<String, AttributeValue>> keys2 = requests.getKeys();
        if (keys2 == null || keys2.isEmpty()) {
            throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, LocalDBClientExceptionMessage.NO_KEYS.getMessage());
        }
        HashSet<Map<String, AttributeValue>> keysSet = new HashSet<Map<String, AttributeValue>>(keys2);
        if (keysSet.size() < keys2.size()) {
            throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, LocalDBClientExceptionMessage.DUPLICATE_ITEM_KEY.getMessage());
        }
        if ((count += keys2.size()) > 100) {
            throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, LocalDBClientExceptionMessage.TOO_MANY_REQUESTED_ITEMS.getMessage());
        }
        for (Map<String, AttributeValue> primaryKey : keys2) {
            if (primaryKey == null) {
                throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, LocalDBClientExceptionMessage.BAD_GET_CONDITION.getMessage());
            }
            this.validateGetKey((Map)primaryKey, info);
        }
        return count;
    }
}

