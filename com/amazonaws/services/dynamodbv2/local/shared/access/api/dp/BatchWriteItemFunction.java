/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  com.amazonaws.services.dynamodbv2.model.BatchWriteItemRequest
 *  com.amazonaws.services.dynamodbv2.model.BatchWriteItemResult
 *  com.amazonaws.services.dynamodbv2.model.ConsumedCapacity
 *  com.amazonaws.services.dynamodbv2.model.ReturnConsumedCapacity
 */
package com.amazonaws.services.dynamodbv2.local.shared.access.api.dp;

import com.amazonaws.services.dynamodbv2.datamodel.DocumentFactory;
import com.amazonaws.services.dynamodbv2.dbenv.DbEnv;
import com.amazonaws.services.dynamodbv2.exceptions.AWSExceptionFactory;
import com.amazonaws.services.dynamodbv2.exceptions.AmazonServiceExceptionType;
import com.amazonaws.services.dynamodbv2.local.shared.access.LocalDBAccess;
import com.amazonaws.services.dynamodbv2.local.shared.access.LocalDBInputConverter;
import com.amazonaws.services.dynamodbv2.local.shared.access.LocalDBOutputConverter;
import com.amazonaws.services.dynamodbv2.local.shared.access.LocalDBUtils;
import com.amazonaws.services.dynamodbv2.local.shared.access.TableInfo;
import com.amazonaws.services.dynamodbv2.local.shared.access.api.Mutation;
import com.amazonaws.services.dynamodbv2.local.shared.access.api.dp.WriteDataPlaneFunction;
import com.amazonaws.services.dynamodbv2.local.shared.exceptions.LocalDBAccessException;
import com.amazonaws.services.dynamodbv2.local.shared.exceptions.LocalDBClientExceptionMessage;
import com.amazonaws.services.dynamodbv2.local.shared.helpers.ConsumedCapacityUtils;
import com.amazonaws.services.dynamodbv2.local.shared.helpers.TransactionsEnabledMode;
import com.amazonaws.services.dynamodbv2.local.shared.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.local.shared.model.DeleteRequest;
import com.amazonaws.services.dynamodbv2.local.shared.model.PutRequest;
import com.amazonaws.services.dynamodbv2.local.shared.model.WriteRequest;
import com.amazonaws.services.dynamodbv2.model.BatchWriteItemRequest;
import com.amazonaws.services.dynamodbv2.model.BatchWriteItemResult;
import com.amazonaws.services.dynamodbv2.model.ConsumedCapacity;
import com.amazonaws.services.dynamodbv2.model.ReturnConsumedCapacity;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class BatchWriteItemFunction
extends WriteDataPlaneFunction<BatchWriteItemRequest, BatchWriteItemResult> {
    public BatchWriteItemFunction(LocalDBAccess dbAccess, DbEnv localDBEnv, LocalDBInputConverter inputConverter, LocalDBOutputConverter localDBOutputConverter, AWSExceptionFactory awsExceptionFactory, DocumentFactory documentFactory) {
        super(dbAccess, localDBEnv, inputConverter, localDBOutputConverter, awsExceptionFactory, documentFactory, TransactionsEnabledMode.TRANSACTIONS_DISABLED);
    }

    @Override
    public BatchWriteItemResult apply(BatchWriteItemRequest batchWriteItemRequest) {
        if (batchWriteItemRequest.getRequestItems() == null || batchWriteItemRequest.getRequestItems().isEmpty()) {
            throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, LocalDBClientExceptionMessage.BATCH_WRITE_NULL_REQUESTS.getMessage());
        }
        int count = 0;
        Long totalRequestSize = 0L;
        Map requestItems = this.inputConverter.externalToInternalBatchWrite(null, batchWriteItemRequest.getRequestItems());
        HashMap<String, ArrayList<Map<String, AttributeValue>>> putsToMake = new HashMap<String, ArrayList<Map<String, AttributeValue>>>();
        HashMap<String, ArrayList<Map<String, AttributeValue>>> deletesToMake = new HashMap<String, ArrayList<Map<String, AttributeValue>>>();
        HashMap<String, String> tableNameToHashKeyNameMap = new HashMap<String, String>();
        HashMap<String, String> tableNameToRangeKeyNameMap = new HashMap<String, String>();
        for (Map.Entry entry : requestItems.entrySet()) {
            String tableName = (String)entry.getKey();
            this.validateTableName(tableName);
            TableInfo info = this.validateTableExists(tableName);
            List requests = entry.getValue();
            if (requests == null || requests.isEmpty()) {
                throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, LocalDBClientExceptionMessage.BATCH_WRITE_NULL_REQUEST_ENTRY.getMessage());
            }
            if ((count += requests.size()) > 25) {
                throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, LocalDBClientExceptionMessage.BATCH_WRITE_TOO_MANY_REQUESTS.getMessage());
            }
            ArrayList<Map<String, AttributeValue>> putsList = new ArrayList<Map<String, AttributeValue>>();
            ArrayList<Map<String, AttributeValue>> deletesList = new ArrayList<Map<String, AttributeValue>>();
            HashSet<Map<String, AttributeValue>> keySet = new HashSet<Map<String, AttributeValue>>();
            for (WriteRequest writeRequest : requests) {
                totalRequestSize = this.validateBatchWriteWriteRequest(writeRequest, info, putsList, deletesList, keySet, totalRequestSize);
            }
            putsToMake.put(tableName, putsList);
            deletesToMake.put(tableName, deletesList);
            tableNameToHashKeyNameMap.put(tableName, info.getHashKey().getAttributeName());
            if (!info.hasRangeKey()) continue;
            tableNameToRangeKeyNameMap.put(tableName, info.getRangeKey().getAttributeName());
        }
        HashSet allTableNames = new HashSet(putsToMake.keySet());
        allTableNames.addAll(deletesToMake.keySet());
        BatchWriteItemResult batchWriteResult = new BatchWriteItemResult();
        HashMap<String, List<WriteRequest>> unprocessedItems = new HashMap<String, List<WriteRequest>>();
        final ReturnConsumedCapacity returnConsumedCapacity = this.convertReturnConsumedCapacity(batchWriteItemRequest.getReturnConsumedCapacity());
        final boolean computingCapacity = COMPUTE_CONSUMED_CAPACITY_TYPES.contains(returnConsumedCapacity);
        ArrayList<ConsumedCapacity> consumedCapacities = computingCapacity ? new ArrayList<ConsumedCapacity>() : null;
        for (final String tableName : allTableNames) {
            final String hashKeyName = (String)tableNameToHashKeyNameMap.get(tableName);
            final String rangeKeyName = (String)tableNameToRangeKeyNameMap.get(tableName);
            final ArrayList<ConsumedCapacity> consumedCapacitiesForTable = computingCapacity ? new ArrayList<ConsumedCapacity>() : null;
            final HashMap<String, AttributeValue> primaryKey = new HashMap<String, AttributeValue>(rangeKeyName == null ? 1 : 2);
            List putsToMakeForTable = putsToMake.containsKey(tableName) ? (List)putsToMake.get(tableName) : Collections.emptyList();
            for (final Map curRecord : putsToMakeForTable) {
                primaryKey.put(hashKeyName, (AttributeValue)curRecord.get(hashKeyName));
                if (rangeKeyName != null) {
                    primaryKey.put(rangeKeyName, (AttributeValue)curRecord.get(rangeKeyName));
                }
                try {
                    new LocalDBAccess.ReadLockWithTimeout(this.dbAccess.getLockForTable(tableName), 10){

                        @Override
                        public void criticalSection() {
                            Map<String, AttributeValue> oldItem = BatchWriteItemFunction.this.dbAccess.getRecord(tableName, primaryKey);
                            BatchWriteItemFunction.this.dbAccess.putRecord(tableName, curRecord, (AttributeValue)curRecord.get(hashKeyName), (AttributeValue)curRecord.get(rangeKeyName), false);
                            if (computingCapacity) {
                                consumedCapacitiesForTable.add(BatchWriteItemFunction.this.computeWriteCapacity(tableName, new Mutation(oldItem, curRecord), returnConsumedCapacity, BatchWriteItemFunction.this.transactionsMode));
                            }
                        }
                    }.execute();
                } catch (LocalDBAccessException accessException) {
                    if (accessException.getMessage().equals(LocalDBClientExceptionMessage.TIME_OUT_WHILE_ACQUIRING_LOCK.getMessage())) {
                        throw accessException;
                    }
                    if (!unprocessedItems.containsKey(tableName)) {
                        unprocessedItems.put(tableName, new ArrayList());
                    }
                    ((List)unprocessedItems.get(tableName)).add(new WriteRequest().withPutRequest(new PutRequest().withItem(curRecord)));
                }
                primaryKey.clear();
            }
            List deletesToMakeForTable = deletesToMake.containsKey(tableName) ? (List)deletesToMake.get(tableName) : Collections.emptyList();
            for (final Map curKey : deletesToMakeForTable) {
                try {
                    new LocalDBAccess.ReadLockWithTimeout(this.dbAccess.getLockForTable(tableName), 10){

                        @Override
                        public void criticalSection() {
                            Map<String, AttributeValue> oldItem = BatchWriteItemFunction.this.dbAccess.getRecord(tableName, curKey);
                            BatchWriteItemFunction.this.dbAccess.deleteRecord(tableName, curKey, false);
                            if (computingCapacity) {
                                consumedCapacitiesForTable.add(BatchWriteItemFunction.this.computeWriteCapacity(tableName, BatchWriteItemFunction.this.createDeleteMutation(oldItem), returnConsumedCapacity, BatchWriteItemFunction.this.transactionsMode));
                            }
                        }
                    }.execute();
                } catch (LocalDBAccessException accessException) {
                    if (accessException.getMessage().equals(LocalDBClientExceptionMessage.TIME_OUT_WHILE_ACQUIRING_LOCK.getMessage())) {
                        throw accessException;
                    }
                    if (!unprocessedItems.containsKey(tableName)) {
                        unprocessedItems.put(tableName, new ArrayList());
                    }
                    ((List)unprocessedItems.get(tableName)).add(new WriteRequest().withDeleteRequest(new DeleteRequest().withKey(curKey)));
                }
            }
            if (!computingCapacity) continue;
            consumedCapacities.add(ConsumedCapacityUtils.mergeConsumedCapacities(tableName, consumedCapacitiesForTable, returnConsumedCapacity));
        }
        return batchWriteResult.withUnprocessedItems(this.localDBOutputConverter.internalToExternalBatchWriteRequests(unprocessedItems)).withConsumedCapacity(consumedCapacities);
    }

    private Long validateBatchWriteWriteRequest(WriteRequest writeRequest, TableInfo tableInfo, List<Map<String, AttributeValue>> putsList, List<Map<String, AttributeValue>> deletesList, Set<Map<String, AttributeValue>> keySet, Long totalRequestSize) {
        boolean isDelete;
        if (writeRequest == null) {
            throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, LocalDBClientExceptionMessage.BATCH_WRITE_NULL_INDIVIDUAL_REQUEST.getMessage());
        }
        Map<String, AttributeValue> primaryKey = null;
        boolean bl = isDelete = writeRequest.getDeleteRequest() != null;
        if (isDelete) {
            primaryKey = this.validateBatchWriteDelete(writeRequest, deletesList, tableInfo);
        } else {
            primaryKey = this.validateBatchWritePut(writeRequest, putsList, tableInfo);
            totalRequestSize = totalRequestSize + LocalDBUtils.getItemSizeBytes(putsList.get(putsList.size() - 1));
        }
        if (keySet.contains(primaryKey)) {
            throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, LocalDBClientExceptionMessage.DUPLICATE_ITEM_KEY.getMessage());
        }
        keySet.add(primaryKey);
        return totalRequestSize;
    }

    private Map<String, AttributeValue> validateBatchWriteDelete(WriteRequest writeRequest, List<Map<String, AttributeValue>> deletesList, TableInfo tableInfo) {
        if (writeRequest.getPutRequest() != null) {
            throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, LocalDBClientExceptionMessage.BATCH_WRITE_TWO_IN_ONE.getMessage());
        }
        DeleteRequest deleteRequest = writeRequest.getDeleteRequest();
        if (deleteRequest.getKey() == null) {
            throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, LocalDBClientExceptionMessage.MISSING_KEY.getMessage());
        }
        Map<String, AttributeValue> primaryKey = deleteRequest.getKey();
        this.validateGetKey((Map)primaryKey, tableInfo);
        deletesList.add(primaryKey);
        return primaryKey;
    }

    private Map<String, AttributeValue> validateBatchWritePut(WriteRequest writeRequest, List<Map<String, AttributeValue>> putsList, TableInfo tableInfo) {
        if (writeRequest.getPutRequest() == null) {
            throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, LocalDBClientExceptionMessage.BATCH_WRITE_NO_REQUEST_TYPE.getMessage());
        }
        PutRequest putRequest = writeRequest.getPutRequest();
        if (putRequest.getItem() == null) {
            throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, LocalDBClientExceptionMessage.INVALID_PUT_NULL.getMessage());
        }
        Map<String, AttributeValue> record = putRequest.getItem();
        Map primaryKey = this.validatePutItem((Map)record, tableInfo);
        putsList.add(record);
        return primaryKey;
    }
}

