/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  com.amazonaws.services.dynamodbv2.model.CancellationReason
 *  com.amazonaws.services.dynamodbv2.model.ConditionCheck
 *  com.amazonaws.services.dynamodbv2.model.ConsumedCapacity
 *  com.amazonaws.services.dynamodbv2.model.Delete
 *  com.amazonaws.services.dynamodbv2.model.DeleteItemRequest
 *  com.amazonaws.services.dynamodbv2.model.GetItemRequest
 *  com.amazonaws.services.dynamodbv2.model.GetItemResult
 *  com.amazonaws.services.dynamodbv2.model.Put
 *  com.amazonaws.services.dynamodbv2.model.PutItemRequest
 *  com.amazonaws.services.dynamodbv2.model.ReturnConsumedCapacity
 *  com.amazonaws.services.dynamodbv2.model.ReturnValue
 *  com.amazonaws.services.dynamodbv2.model.ReturnValuesOnConditionCheckFailure
 *  com.amazonaws.services.dynamodbv2.model.TransactWriteItem
 *  com.amazonaws.services.dynamodbv2.model.TransactWriteItemsRequest
 *  com.amazonaws.services.dynamodbv2.model.TransactWriteItemsResult
 *  com.amazonaws.services.dynamodbv2.model.Update
 *  com.amazonaws.services.dynamodbv2.model.UpdateItemRequest
 */
package com.amazonaws.services.dynamodbv2.local.shared.access.api.dp;

import com.amazonaws.services.dynamodbv2.datamodel.DocumentFactory;
import com.amazonaws.services.dynamodbv2.datamodel.Expression;
import com.amazonaws.services.dynamodbv2.dbenv.DbEnv;
import com.amazonaws.services.dynamodbv2.exceptions.AWSExceptionFactory;
import com.amazonaws.services.dynamodbv2.exceptions.AmazonServiceExceptionType;
import com.amazonaws.services.dynamodbv2.exceptions.DynamoDBLocalServiceException;
import com.amazonaws.services.dynamodbv2.local.google.Lists;
import com.amazonaws.services.dynamodbv2.local.google.Sets;
import com.amazonaws.services.dynamodbv2.local.shared.access.LocalDBAccess;
import com.amazonaws.services.dynamodbv2.local.shared.access.LocalDBInputConverter;
import com.amazonaws.services.dynamodbv2.local.shared.access.LocalDBOutputConverter;
import com.amazonaws.services.dynamodbv2.local.shared.access.LocalDBUtils;
import com.amazonaws.services.dynamodbv2.local.shared.access.TableInfo;
import com.amazonaws.services.dynamodbv2.local.shared.access.api.dp.DeleteItemFunction;
import com.amazonaws.services.dynamodbv2.local.shared.access.api.dp.GetItemFunction;
import com.amazonaws.services.dynamodbv2.local.shared.access.api.dp.PutItemFunction;
import com.amazonaws.services.dynamodbv2.local.shared.access.api.dp.UpdateItemFunction;
import com.amazonaws.services.dynamodbv2.local.shared.access.api.dp.WriteDataPlaneFunction;
import com.amazonaws.services.dynamodbv2.local.shared.exceptions.LocalDBClientExceptionMessage;
import com.amazonaws.services.dynamodbv2.local.shared.helpers.ConsumedCapacityUtils;
import com.amazonaws.services.dynamodbv2.local.shared.helpers.MultiTableLock;
import com.amazonaws.services.dynamodbv2.local.shared.helpers.TransactionsEnabledMode;
import com.amazonaws.services.dynamodbv2.local.shared.mapper.DynamoDBObjectMapper;
import com.amazonaws.services.dynamodbv2.local.shared.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.local.shared.model.TableNameAndPrimaryKey;
import com.amazonaws.services.dynamodbv2.local.shared.validate.TransactionErrorMapper;
import com.amazonaws.services.dynamodbv2.local.shared.validate.UpdateItemExpressionsWrapper;
import com.amazonaws.services.dynamodbv2.model.CancellationReason;
import com.amazonaws.services.dynamodbv2.model.ConditionCheck;
import com.amazonaws.services.dynamodbv2.model.ConsumedCapacity;
import com.amazonaws.services.dynamodbv2.model.Delete;
import com.amazonaws.services.dynamodbv2.model.DeleteItemRequest;
import com.amazonaws.services.dynamodbv2.model.GetItemRequest;
import com.amazonaws.services.dynamodbv2.model.GetItemResult;
import com.amazonaws.services.dynamodbv2.model.Put;
import com.amazonaws.services.dynamodbv2.model.PutItemRequest;
import com.amazonaws.services.dynamodbv2.model.ReturnConsumedCapacity;
import com.amazonaws.services.dynamodbv2.model.ReturnValue;
import com.amazonaws.services.dynamodbv2.model.ReturnValuesOnConditionCheckFailure;
import com.amazonaws.services.dynamodbv2.model.TransactWriteItem;
import com.amazonaws.services.dynamodbv2.model.TransactWriteItemsRequest;
import com.amazonaws.services.dynamodbv2.model.TransactWriteItemsResult;
import com.amazonaws.services.dynamodbv2.model.Update;
import com.amazonaws.services.dynamodbv2.model.UpdateItemRequest;
import com.amazonaws.services.dynamodbv2.rr.ExpressionWrapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

public class TransactWriteItemsFunction
extends WriteDataPlaneFunction<TransactWriteItemsRequest, TransactWriteItemsResult> {
    private final PutItemFunction putItem;
    private final UpdateItemFunction updateItem;
    private final DeleteItemFunction deleteItem;
    private final GetItemFunction getItem;
    private final DynamoDBObjectMapper mapper = new DynamoDBObjectMapper();
    private final TransactionErrorMapper errorMapper;

    public TransactWriteItemsFunction(LocalDBAccess access, LocalDBInputConverter inputConverter, LocalDBOutputConverter localDBOutputConverter, AWSExceptionFactory awsExceptionFactory, DbEnv localDBEnv, DocumentFactory documentFactory) {
        super(access, localDBEnv, inputConverter, localDBOutputConverter, awsExceptionFactory, documentFactory, TransactionsEnabledMode.TRANSACTIONS_ENABLED);
        this.putItem = new PutItemFunction(access, localDBEnv, inputConverter, localDBOutputConverter, awsExceptionFactory, documentFactory, TransactionsEnabledMode.TRANSACTIONS_ENABLED);
        this.updateItem = new UpdateItemFunction(access, localDBEnv, inputConverter, localDBOutputConverter, awsExceptionFactory, documentFactory, TransactionsEnabledMode.TRANSACTIONS_ENABLED);
        this.deleteItem = new DeleteItemFunction(access, localDBEnv, inputConverter, localDBOutputConverter, awsExceptionFactory, documentFactory, TransactionsEnabledMode.TRANSACTIONS_ENABLED);
        this.getItem = new GetItemFunction(access, localDBEnv, inputConverter, localDBOutputConverter, awsExceptionFactory, TransactionsEnabledMode.TRANSACTIONS_ENABLED);
        this.errorMapper = new TransactionErrorMapper(awsExceptionFactory);
    }

    @Override
    public TransactWriteItemsResult apply(final TransactWriteItemsRequest input) {
        this.validateRequest(input);
        final String clientToken = input.getClientRequestToken() == null ? UUID.randomUUID().toString() : input.getClientRequestToken();
        TreeSet<String> tableNames = new TreeSet<String>();
        int i = 1;
        for (TransactWriteItem item : input.getTransactItems()) {
            String tableName = this.getTableName(item);
            if (tableName == null) {
                throw this.errorMapper.buildCoralValidationException("null", item, i, "tableName", "Member must not be null");
            }
            tableNames.add(tableName);
            ++i;
        }
        MultiTableLock tableLocker = new MultiTableLock(tableNames, this.dbAccess, MultiTableLock.LockMode.WRITE);
        final ReturnConsumedCapacity returnConsumedCapacity = this.convertReturnConsumedCapacity(input.getReturnConsumedCapacity());
        final AtomicReference consumedCapacities = new AtomicReference();
        Runnable criticalSection = new Runnable(){

            @Override
            public void run() {
                List<ConsumedCapacity> result = TransactWriteItemsFunction.this.doWrite(input.getTransactItems(), clientToken, returnConsumedCapacity);
                consumedCapacities.set(result);
            }
        };
        tableLocker.wrapInTableLocks(criticalSection).run();
        return new TransactWriteItemsResult().withConsumedCapacity(ConsumedCapacityUtils.mergeAllConsumedCapacities((List)consumedCapacities.get(), returnConsumedCapacity));
    }

    private void validateRequest(TransactWriteItemsRequest request) {
        if (request.getTransactItems() == null) {
            throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, LocalDBClientExceptionMessage.TRANSACT_WRITE_NULL_REQUESTS.getMessage());
        }
        if (request.getTransactItems().isEmpty()) {
            throw AWSExceptionFactory.buildCoralValidationException(request.getTransactItems().toString(), "transactItems", LocalDBClientExceptionMessage.TRANSACT_WRITE_EMPTY_REQUESTS.getMessage());
        }
        if (request.getTransactItems().size() > 100) {
            throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, LocalDBClientExceptionMessage.TRANSACT_TOO_MANY_REQUESTS.getMessage());
        }
        if (request.getClientRequestToken() != null && request.getClientRequestToken().length() > 36) {
            throw AWSExceptionFactory.buildCoralValidationException(request.getClientRequestToken(), "clientRequestToken", LocalDBClientExceptionMessage.TRANSACT_IDEMPOTENT_TOKEN_TOO_LARGE.getMessage());
        }
        long transactionPayloadSizeBytes = 0L;
        for (TransactWriteItem writeItem : request.getTransactItems()) {
            this.validateTransactWriteItem(writeItem);
            if ((transactionPayloadSizeBytes += this.getItemPayloadSize(writeItem)) <= 0x400000L) continue;
            throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, LocalDBClientExceptionMessage.TRANSACT_REQUEST_PAYLOAD_TOO_LARGE.getMessage() + transactionPayloadSizeBytes);
        }
    }

    private void validateTransactWriteItem(TransactWriteItem writeItem) {
        int operations2 = 0;
        if (writeItem.getConditionCheck() != null) {
            ++operations2;
        }
        if (writeItem.getDelete() != null) {
            ++operations2;
        }
        if (writeItem.getPut() != null) {
            ++operations2;
        }
        if (writeItem.getUpdate() != null) {
            ++operations2;
        }
        if (operations2 > 1) {
            throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, LocalDBClientExceptionMessage.TRANSACT_WRITE_MULTIPLE_OPERATIONS.getMessage());
        }
        if (operations2 == 0) {
            throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, LocalDBClientExceptionMessage.TRANSACT_WRITE_NO_OPERATIONS.getMessage());
        }
    }

    private String getTableName(TransactWriteItem item) {
        switch (OperationType.get(item)) {
            case CONDITION_CHECK: {
                return item.getConditionCheck().getTableName();
            }
            case DELETE: {
                return item.getDelete().getTableName();
            }
            case PUT: {
                return item.getPut().getTableName();
            }
            case UPDATE: {
                return item.getUpdate().getTableName();
            }
        }
        throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.INTERNAL_SERVER_ERROR);
    }

    private long getItemPayloadSize(TransactWriteItem transactItem) {
        UpdateItemExpressionsWrapper expressionsWrapper = new UpdateItemExpressionsWrapper();
        Map item = null;
        long transactionPayloadSizeBytes = 0L;
        switch (OperationType.get(transactItem)) {
            case CONDITION_CHECK: {
                ConditionCheck conditionCheck = transactItem.getConditionCheck();
                item = conditionCheck.getKey();
                expressionsWrapper.setConditionExpressionWrapper(this.inputConverter.externalToInternalConditionExpression(conditionCheck.getConditionExpression(), conditionCheck.getExpressionAttributeNames(), conditionCheck.getExpressionAttributeValues()));
                break;
            }
            case DELETE: {
                Delete delete = transactItem.getDelete();
                item = delete.getKey();
                expressionsWrapper.setConditionExpressionWrapper(this.inputConverter.externalToInternalConditionExpression(delete.getConditionExpression(), delete.getExpressionAttributeNames(), delete.getExpressionAttributeValues()));
                break;
            }
            case PUT: {
                Put put = transactItem.getPut();
                item = put.getItem();
                expressionsWrapper.setConditionExpressionWrapper(this.inputConverter.externalToInternalConditionExpression(put.getConditionExpression(), put.getExpressionAttributeNames(), put.getExpressionAttributeValues()));
                break;
            }
            case UPDATE: {
                Update update = transactItem.getUpdate();
                item = update.getKey();
                expressionsWrapper = this.inputConverter.externalToInternalUpdateAndConditionExpressions(update.getUpdateExpression(), update.getConditionExpression(), update.getExpressionAttributeNames(), update.getExpressionAttributeValues());
                break;
            }
            default: {
                throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.INTERNAL_SERVER_ERROR);
            }
        }
        if (item != null) {
            transactionPayloadSizeBytes += LocalDBUtils.getItemSizeBytes((Map)this.inputConverter.externalToInternalAttributes(item));
        }
        if (expressionsWrapper != null && expressionsWrapper.getUpdateExpressionWrapper() != null) {
            transactionPayloadSizeBytes += (long)expressionsWrapper.getUpdateExpressionWrapper().getCumulativeSize();
        }
        if (expressionsWrapper != null && expressionsWrapper.getConditionExpressionWrapper() != null) {
            transactionPayloadSizeBytes += (long)expressionsWrapper.getConditionExpressionWrapper().getCumulativeSize();
        }
        return transactionPayloadSizeBytes;
    }

    private List<Map<String, AttributeValue>> collectAllExistingItems(List<TransactWriteItem> writeItems) {
        List<Map<String, AttributeValue>> result = Lists.newArrayList(new Map[0]);
        boolean canceled = false;
        List<CancellationReason> cancellationReasons = Lists.newArrayList(new CancellationReason[0]);
        for (TransactWriteItem writeItem : writeItems) {
            try {
                result.add(this.getExistingItem(writeItem));
                cancellationReasons.add(this.errorMapper.getEmptyCancellationReason());
            } catch (DynamoDBLocalServiceException e) {
                canceled = true;
                cancellationReasons.add(this.errorMapper.mapToCancellationReasonWhenGettingExistingItem(e));
            }
        }
        if (canceled) {
            throw AWSExceptionFactory.buildTransactionCanceledException(cancellationReasons);
        }
        return result;
    }

    private Map<String, AttributeValue> getExistingItem(TransactWriteItem writeItem) {
        String tableName = this.getTableName(writeItem);
        TableInfo tableInfo = this.validateTableExists(tableName);
        Map<String, AttributeValue> primaryKey = this.getKey(writeItem, tableInfo);
        Map<String, AttributeValue> existingItem = this.dbAccess.getRecord(tableName, primaryKey);
        return existingItem;
    }

    private Map<String, AttributeValue> getKey(TransactWriteItem writeItem, TableInfo tableInfo) {
        switch (OperationType.get(writeItem)) {
            case CONDITION_CHECK: {
                return (Map)this.inputConverter.externalToInternalAttributes(writeItem.getConditionCheck().getKey());
            }
            case DELETE: {
                return (Map)this.inputConverter.externalToInternalAttributes(writeItem.getDelete().getKey());
            }
            case PUT: {
                Map item = (Map)this.inputConverter.externalToInternalAttributes(writeItem.getPut().getItem());
                return this.validatePutItem(item, tableInfo);
            }
            case UPDATE: {
                return (Map)this.inputConverter.externalToInternalAttributes(writeItem.getUpdate().getKey());
            }
        }
        throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.INTERNAL_SERVER_ERROR);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private List<ConsumedCapacity> doWrite(List<TransactWriteItem> writeItems, String clientToken, ReturnConsumedCapacity returnConsumedCapacity) {
        List<ConsumedCapacity> result = Lists.newArrayListWithExpectedSize(writeItems.size());
        byte[] transactionSignature = this.calculateTransactionSignature(writeItems);
        byte[] previousSignature = this.dbAccess.beginTransaction(clientToken);
        if (previousSignature != null) {
            try {
                if (!Arrays.equals(transactionSignature, previousSignature)) {
                    throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.IDEMPOTENT_PARAMETER_MISMATCH_EXCEPTION);
                }
                List<Map<String, AttributeValue>> existingItems = this.collectAllExistingItems(writeItems);
                for (int i = 0; i < writeItems.size(); ++i) {
                    ConsumedCapacity consumedCapacity = ConsumedCapacityUtils.computeConsumedCapacity(Collections.singletonList(existingItems.get(i)), false, false, this.getTableName(writeItems.get(i)), null, true, true, this.transactionsMode, returnConsumedCapacity);
                    ConsumedCapacityUtils.copyToReadConsumedCapacity(consumedCapacity);
                    if (consumedCapacity == null) continue;
                    result.add(consumedCapacity);
                }
                List<ConsumedCapacity> i = result;
                return i;
            } finally {
                this.dbAccess.rollbackTransaction();
            }
        }
        try {
            boolean canceled = false;
            Set<TableNameAndPrimaryKey> keysInvolved = Sets.newHashSet(new TableNameAndPrimaryKey[0]);
            List<CancellationReason> cancellationReasons = Lists.newArrayList(new CancellationReason[0]);
            for (int i = 0; i < writeItems.size(); ++i) {
                TransactWriteItem item = writeItems.get(i);
                ConsumedCapacity consumedCapacity = null;
                try {
                    TableInfo tableInfo;
                    Map<String, AttributeValue> primaryKey;
                    String tableName;
                    TableNameAndPrimaryKey tableAndKey;
                    if (item.getPut() != null) {
                        request = this.putToPutItemRequest(item.getPut(), returnConsumedCapacity);
                        try {
                            consumedCapacity = this.putItem.apply(request).getConsumedCapacity();
                        } catch (DynamoDBLocalServiceException e) {
                            throw this.errorMapper.mapToCorrectExceptionForPutRequest(e, item, i);
                        }
                    } else if (item.getUpdate() != null) {
                        request = this.updateToUpdateItemRequest(item.getUpdate(), returnConsumedCapacity);
                        try {
                            consumedCapacity = this.updateItem.apply((UpdateItemRequest)request).getConsumedCapacity();
                        } catch (DynamoDBLocalServiceException e) {
                            throw this.errorMapper.mapToCorrectExceptionForUpdateRequest(e, item, i);
                        }
                    } else if (item.getDelete() != null) {
                        request = this.deleteToDeleteItemRequest(item.getDelete(), returnConsumedCapacity);
                        try {
                            consumedCapacity = this.deleteItem.apply((DeleteItemRequest)request).getConsumedCapacity();
                        } catch (DynamoDBLocalServiceException e) {
                            throw this.errorMapper.mapToCorrectExceptionForKeyedRequest(e, item, i);
                        }
                    } else if (item.getConditionCheck() != null) {
                        consumedCapacity = this.handleConditionCheckRequest(item, returnConsumedCapacity, i);
                    } else {
                        throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.UNKNOWN_OPERATION_EXCEPTION);
                    }
                    ConsumedCapacityUtils.doubleAndCopyToWriteConsumedCapacity(consumedCapacity);
                    if (consumedCapacity != null) {
                        result.add(consumedCapacity);
                    }
                    if (!keysInvolved.add(tableAndKey = new TableNameAndPrimaryKey(tableName = this.getTableName(item), primaryKey = this.getKey(item, tableInfo = this.validateTableExists(tableName))))) {
                        throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, LocalDBClientExceptionMessage.TRANSACT_DUPLICATE_KEY.getMessage());
                    }
                    cancellationReasons.add(this.errorMapper.getEmptyCancellationReason());
                    continue;
                } catch (DynamoDBLocalServiceException e) {
                    canceled = true;
                    cancellationReasons.add(this.errorMapper.mapToCancellationReasonWhenHandlingTransactionOperation(e, item, i));
                }
            }
            if (canceled) {
                long transactionResponsePayloadSizeBytes = 0L;
                for (int i = 0; i < cancellationReasons.size(); ++i) {
                    TransactWriteItem writeItem;
                    ReturnValuesOnConditionCheckFailure returnValues;
                    CancellationReason reason = cancellationReasons.get(i);
                    if (reason.getCode() == "ConditionalCheckFailed" && (returnValues = this.getReturnValuesOnConditionCheckFailure(writeItem = writeItems.get(i))) == ReturnValuesOnConditionCheckFailure.ALL_OLD && reason.getCode() != "None") {
                        Map<String, AttributeValue> existingItem = this.getExistingItem(writeItem);
                        reason.setItem(this.localDBOutputConverter.internalToExternalAttributes(existingItem));
                        transactionResponsePayloadSizeBytes += 4L;
                        transactionResponsePayloadSizeBytes += LocalDBUtils.getItemSizeBytes(existingItem);
                    }
                    if (transactionResponsePayloadSizeBytes <= 0x400000L) continue;
                    throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, LocalDBClientExceptionMessage.TRANSACT_RESPONSE_PAYLOAD_TOO_LARGE.getMessage() + transactionResponsePayloadSizeBytes);
                }
                throw AWSExceptionFactory.buildTransactionCanceledException(cancellationReasons);
            }
            this.dbAccess.commitTransaction(clientToken, transactionSignature);
            return result;
        } catch (RuntimeException e) {
            this.dbAccess.rollbackTransaction();
            throw e;
        }
    }

    public byte[] calculateTransactionSignature(List<TransactWriteItem> writeItems) {
        try {
            List hashes = writeItems.stream().map(TransactWriteItem::hashCode).collect(Collectors.toList());
            byte[] value = this.mapper.writeValueAsBytes(hashes);
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            return md.digest(value);
        } catch (JsonProcessingException | NoSuchAlgorithmException e) {
            throw AWSExceptionFactory.buildInternalServerException(e.getMessage());
        }
    }

    private PutItemRequest putToPutItemRequest(Put put, ReturnConsumedCapacity returnConsumedCapacity) {
        return new PutItemRequest().withTableName(put.getTableName()).withConditionExpression(put.getConditionExpression()).withExpressionAttributeNames(put.getExpressionAttributeNames()).withExpressionAttributeValues(put.getExpressionAttributeValues()).withItem(put.getItem()).withReturnValues(this.returnValue(put.getReturnValuesOnConditionCheckFailure())).withReturnConsumedCapacity(returnConsumedCapacity);
    }

    private UpdateItemRequest updateToUpdateItemRequest(Update update, ReturnConsumedCapacity returnConsumedCapacity) {
        return new UpdateItemRequest().withConditionExpression(update.getConditionExpression()).withExpressionAttributeNames(update.getExpressionAttributeNames()).withExpressionAttributeValues(update.getExpressionAttributeValues()).withKey(update.getKey()).withReturnValues(this.returnValue(update.getReturnValuesOnConditionCheckFailure())).withTableName(update.getTableName()).withUpdateExpression(update.getUpdateExpression()).withReturnConsumedCapacity(returnConsumedCapacity);
    }

    private DeleteItemRequest deleteToDeleteItemRequest(Delete delete, ReturnConsumedCapacity returnConsumedCapacity) {
        return new DeleteItemRequest().withConditionExpression(delete.getConditionExpression()).withExpressionAttributeNames(delete.getExpressionAttributeNames()).withExpressionAttributeValues(delete.getExpressionAttributeValues()).withKey(delete.getKey()).withReturnValues(this.returnValue(delete.getReturnValuesOnConditionCheckFailure())).withTableName(delete.getTableName()).withReturnConsumedCapacity(returnConsumedCapacity);
    }

    private GetItemRequest conditionCheckToGetItemRequest(ConditionCheck conditionCheck, ReturnConsumedCapacity returnConsumedCapacity) {
        return new GetItemRequest().withConsistentRead(Boolean.valueOf(true)).withKey(conditionCheck.getKey()).withReturnConsumedCapacity(returnConsumedCapacity).withTableName(conditionCheck.getTableName());
    }

    private ReturnValuesOnConditionCheckFailure getReturnValuesOnConditionCheckFailure(TransactWriteItem writeItem) {
        String value = null;
        switch (OperationType.get(writeItem)) {
            case CONDITION_CHECK: {
                value = writeItem.getConditionCheck().getReturnValuesOnConditionCheckFailure();
                break;
            }
            case DELETE: {
                value = writeItem.getDelete().getReturnValuesOnConditionCheckFailure();
                break;
            }
            case PUT: {
                value = writeItem.getPut().getReturnValuesOnConditionCheckFailure();
                break;
            }
            case UPDATE: {
                value = writeItem.getUpdate().getReturnValuesOnConditionCheckFailure();
            }
        }
        return this.getReturnValuesOnConditionCheckFailure(value);
    }

    private ReturnValue returnValue(String input) {
        if (input == null) {
            input = "NONE";
        }
        if (Objects.requireNonNull(ReturnValuesOnConditionCheckFailure.fromValue((String)input)) == ReturnValuesOnConditionCheckFailure.ALL_OLD) {
            return ReturnValue.ALL_OLD;
        }
        return ReturnValue.NONE;
    }

    private ConsumedCapacity handleConditionCheckRequest(TransactWriteItem item, ReturnConsumedCapacity returnConsumedCapacity, int position) {
        ConditionCheck conditionCheck = item.getConditionCheck();
        GetItemRequest request = this.conditionCheckToGetItemRequest(conditionCheck, returnConsumedCapacity);
        GetItemResult getItemResult = null;
        try {
            getItemResult = this.getItem.apply(request);
        } catch (DynamoDBLocalServiceException e) {
            throw this.errorMapper.mapToCorrectExceptionForKeyedRequest(e, item, position);
        }
        ExpressionWrapper expressionWrapper = this.inputConverter.externalToInternalConditionExpression(conditionCheck.getConditionExpression(), conditionCheck.getExpressionAttributeNames(), conditionCheck.getExpressionAttributeValues());
        if (expressionWrapper == null) {
            int errorPosition = position + 1;
            throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, "Value null at 'transactItems." + errorPosition + ".member.conditionCheck.conditionExpression' failed to satisfy constraint: Member must not be null");
        }
        Expression expression = expressionWrapper.getExpression();
        Map existingItem = getItemResult.getItem() == null || getItemResult.getItem().isEmpty() ? Collections.emptyMap() : (Map)this.inputConverter.externalToInternalAttributes(getItemResult.getItem());
        if (!LocalDBUtils.doesItemMatchCondition(existingItem, expression, this.localDBEnv, this.documentFactory)) {
            throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.CONDITIONAL_CHECK_FAILED_EXCEPTION);
        }
        return getItemResult.getConsumedCapacity();
    }

    public static enum OperationType {
        CONDITION_CHECK("conditionCheck"),
        DELETE("delete"),
        PUT("put"),
        UPDATE("update");

        private final String apiName;

        private OperationType(String apiName) {
            this.apiName = apiName;
        }

        public static OperationType get(TransactWriteItem item) {
            if (item.getConditionCheck() != null) {
                return CONDITION_CHECK;
            }
            if (item.getDelete() != null) {
                return DELETE;
            }
            if (item.getPut() != null) {
                return PUT;
            }
            if (item.getUpdate() != null) {
                return UPDATE;
            }
            throw new IllegalArgumentException("No operation type.");
        }

        public String getApiName() {
            return this.apiName;
        }
    }
}

