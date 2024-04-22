/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  com.amazonaws.AmazonServiceException
 *  com.amazonaws.services.dynamodbv2.model.ConsumedCapacity
 *  com.amazonaws.services.dynamodbv2.model.Get
 *  com.amazonaws.services.dynamodbv2.model.ReturnConsumedCapacity
 *  com.amazonaws.services.dynamodbv2.model.TransactGetItem
 *  com.amazonaws.services.dynamodbv2.model.TransactGetItemsRequest
 *  com.amazonaws.services.dynamodbv2.model.TransactGetItemsResult
 */
package com.amazonaws.services.dynamodbv2.local.shared.access.api.dp;

import com.amazonaws.AmazonServiceException;
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
import com.amazonaws.services.dynamodbv2.local.shared.helpers.MultiTableLock;
import com.amazonaws.services.dynamodbv2.local.shared.helpers.TransactionsEnabledMode;
import com.amazonaws.services.dynamodbv2.local.shared.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.local.shared.model.TableNameAndPrimaryKey;
import com.amazonaws.services.dynamodbv2.model.ConsumedCapacity;
import com.amazonaws.services.dynamodbv2.model.Get;
import com.amazonaws.services.dynamodbv2.model.ReturnConsumedCapacity;
import com.amazonaws.services.dynamodbv2.model.TransactGetItem;
import com.amazonaws.services.dynamodbv2.model.TransactGetItemsRequest;
import com.amazonaws.services.dynamodbv2.model.TransactGetItemsResult;
import com.amazonaws.services.dynamodbv2.rr.ProjectionExpressionWrapper;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class TransactGetItemsFunction
extends ReadDataPlaneFunction<TransactGetItemsRequest, TransactGetItemsResult> {
    public TransactGetItemsFunction(LocalDBAccess access, LocalDBInputConverter inputConverter, LocalDBOutputConverter localDBOutputConverter, AWSExceptionFactory awsExceptionFactory, DbEnv localDBEnv) {
        super(access, localDBEnv, inputConverter, localDBOutputConverter, awsExceptionFactory, TransactionsEnabledMode.TRANSACTIONS_ENABLED);
    }

    @Override
    public TransactGetItemsResult apply(final TransactGetItemsRequest input) {
        this.validateRequest(input);
        TreeSet<String> tableNames = new TreeSet<String>();
        for (TransactGetItem item : input.getTransactItems()) {
            tableNames.add(item.getGet().getTableName());
        }
        MultiTableLock tableLocker = new MultiTableLock(tableNames, this.dbAccess, MultiTableLock.LockMode.READ);
        final ArrayList items = new ArrayList();
        Runnable criticalSection = new Runnable(){

            @Override
            public void run() {
                items.addAll(TransactGetItemsFunction.this.doGetItems(input.getTransactItems()));
            }
        };
        tableLocker.wrapInTableLocks(criticalSection).run();
        HashMap itemsByTable = new HashMap();
        ArrayList<Map<String, AttributeValue>> filteredItems = new ArrayList<Map<String, AttributeValue>>(items.size());
        for (int i = 0; i < input.getTransactItems().size(); ++i) {
            Get get = ((TransactGetItem)input.getTransactItems().get(i)).getGet();
            ProjectionExpressionWrapper projectionExpressionWrapper = this.inputConverter.externalToInternalProjectionExpression(get.getProjectionExpression(), get.getExpressionAttributeNames());
            ProjectionExpression projectionExpression = projectionExpressionWrapper == null ? null : projectionExpressionWrapper.getProjection();
            Map<String, AttributeValue> filteredAttributes = projectionExpression != null ? LocalDBUtils.projectAttributes((Map<String, AttributeValue>)((Map)items.get(i)), projectionExpression) : (Map<String, AttributeValue>)items.get(i);
            filteredItems.add(filteredAttributes);
            if (!itemsByTable.containsKey(get.getTableName())) {
                itemsByTable.put(get.getTableName(), new ArrayList());
            }
            ((List)itemsByTable.get(get.getTableName())).add((Map)items.get(i));
        }
        this.validateResponsePayloadSizeLimit(filteredItems);
        ReturnConsumedCapacity returnConsumedCapacity = this.convertReturnConsumedCapacity(input.getReturnConsumedCapacity());
        ArrayList<ConsumedCapacity> consumedCapacities = new ArrayList<ConsumedCapacity>(itemsByTable.size());
        for (String tableName : itemsByTable.keySet()) {
            ConsumedCapacity consumedCapacity = ConsumedCapacityUtils.computeConsumedCapacity((List)itemsByTable.get(tableName), false, false, tableName, null, true, true, this.transactionsMode, returnConsumedCapacity);
            if (consumedCapacity == null) continue;
            consumedCapacity.setReadCapacityUnits(consumedCapacity.getCapacityUnits());
            consumedCapacities.add(consumedCapacity);
        }
        if (consumedCapacities.isEmpty()) {
            consumedCapacities = null;
        }
        TransactGetItemsResult result = new TransactGetItemsResult().withResponses(this.localDBOutputConverter.internalToExternalTransactGetItemsResponses(filteredItems)).withConsumedCapacity(consumedCapacities);
        return result;
    }

    private long validateResponsePayloadSizeLimit(List<Map<String, AttributeValue>> filteredItems) {
        long transactionResponsePayloadSizeBytes = 0L;
        for (Map<String, AttributeValue> item : filteredItems) {
            transactionResponsePayloadSizeBytes += 4L;
            if ((transactionResponsePayloadSizeBytes += LocalDBUtils.getItemSizeBytes(item)) <= 0x400000L) continue;
            throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, LocalDBClientExceptionMessage.TRANSACT_RESPONSE_PAYLOAD_TOO_LARGE.getMessage() + transactionResponsePayloadSizeBytes);
        }
        return transactionResponsePayloadSizeBytes;
    }

    private void validateRequest(TransactGetItemsRequest request) {
        try {
            if (request.getTransactItems() == null || request.getTransactItems().isEmpty()) {
                throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, LocalDBClientExceptionMessage.TRANSACT_GET_NULL_REQUESTS.getMessage());
            }
            if (request.getTransactItems().size() > 100) {
                throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, LocalDBClientExceptionMessage.TRANSACT_TOO_MANY_REQUESTS.getMessage());
            }
            long transactionPayloadSizeBytes = 0L;
            HashSet<TableNameAndPrimaryKey> keysToGet = new HashSet<TableNameAndPrimaryKey>();
            for (TransactGetItem item : request.getTransactItems()) {
                if (item == null) {
                    throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, LocalDBClientExceptionMessage.TRANSACT_GET_NULL_REQUESTS.getMessage());
                }
                Get get = item.getGet();
                if ((transactionPayloadSizeBytes += this.validateGet(get, keysToGet)) <= 0x400000L) continue;
                throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, LocalDBClientExceptionMessage.TRANSACT_REQUEST_PAYLOAD_TOO_LARGE.getMessage() + transactionPayloadSizeBytes);
            }
        } catch (AmazonServiceException e) {
            if (e.getErrorCode().equals(AmazonServiceExceptionType.VALIDATION_EXCEPTION.getErrorCode())) {
                String msg = e.getErrorMessage();
                if (LocalDBClientExceptionMessage.INCONSISTENT_GET_CONDITION_SIZE.getMessage().equals(msg) || LocalDBClientExceptionMessage.INCONSISTENT_TYPES.getMessage().equals(msg) || LocalDBClientExceptionMessage.INCONSISTENT_PRIMARY_KEY_TYPES.getMessage().equals(msg) || LocalDBClientExceptionMessage.INCONSISTENT_INDEX_KEY_TYPES.getMessage().equals(msg) || LocalDBClientExceptionMessage.NO_SPECIFED_KEY_VALUE.getMessage().equals(msg)) {
                    throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.TRANSACTION_CANCELED_EXCEPTION, e.getErrorMessage());
                }
            }
            throw e;
        }
    }

    private long validateGet(Get get, Set<TableNameAndPrimaryKey> keysToGet) {
        if (get == null) {
            throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, LocalDBClientExceptionMessage.TRANSACT_GET_NULL_REQUESTS.getMessage());
        }
        if (get.getTableName() == null) {
            throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, LocalDBClientExceptionMessage.REQUESTED_RESOURCE_NOT_FOUND.getMessage());
        }
        TableInfo tableInfo = this.validateTableExists(get.getTableName());
        this.inputConverter.validateExpressionAttributeNamesUsedOnlyWithExpressions(get.getProjectionExpression(), null, null, null, null, get.getExpressionAttributeNames());
        ProjectionExpressionWrapper projectionExpressionWrapper = this.inputConverter.externalToInternalProjectionExpression(get.getProjectionExpression(), get.getExpressionAttributeNames());
        LocalDBValidatorUtils.validateNoNestedAccessToKeyAttributeInExpression(tableInfo, projectionExpressionWrapper, this.awsExceptionFactory);
        if (get.getKey() == null) {
            throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, LocalDBClientExceptionMessage.BAD_GET_CONDITION.getMessage());
        }
        Map primaryKey = (Map)this.inputConverter.externalToInternalAttributes(get.getKey());
        this.validateGetKey(primaryKey, tableInfo);
        TableNameAndPrimaryKey keyToGet = new TableNameAndPrimaryKey(get.getTableName(), primaryKey);
        if (keysToGet.contains(keyToGet)) {
            throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, LocalDBClientExceptionMessage.TRANSACT_DUPLICATE_KEY.getMessage());
        }
        keysToGet.add(keyToGet);
        long totalSize = 0L;
        totalSize += LocalDBUtils.getItemSizeBytes(primaryKey);
        return totalSize += projectionExpressionWrapper != null ? (long)projectionExpressionWrapper.getCumulativeSize() : 0L;
    }

    private List<Map<String, AttributeValue>> doGetItems(List<TransactGetItem> itemsToGet) {
        ArrayList<Map<String, AttributeValue>> items = new ArrayList<Map<String, AttributeValue>>(itemsToGet.size());
        for (TransactGetItem item : itemsToGet) {
            Get get = item.getGet();
            Map<String, AttributeValue> result = this.dbAccess.getRecord(get.getTableName(), (Map)this.inputConverter.externalToInternalAttributes(get.getKey()));
            items.add(result);
        }
        return items;
    }
}

