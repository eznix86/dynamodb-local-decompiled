/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  com.amazonaws.services.dynamodbv2.model.CancellationReason
 *  com.amazonaws.services.dynamodbv2.model.ExecuteTransactionResult
 *  com.amazonaws.services.dynamodbv2.model.ItemResponse
 */
package com.amazonaws.services.dynamodbv2.local.shared.partiql.processor;

import com.amazonaws.services.dynamodbv2.datamodel.DocumentFactory;
import com.amazonaws.services.dynamodbv2.datamodel.ProjectionExpression;
import com.amazonaws.services.dynamodbv2.exceptions.AWSExceptionFactory;
import com.amazonaws.services.dynamodbv2.exceptions.AmazonServiceExceptionType;
import com.amazonaws.services.dynamodbv2.exceptions.DynamoDBLocalServiceException;
import com.amazonaws.services.dynamodbv2.local.google.Lists;
import com.amazonaws.services.dynamodbv2.local.google.Sets;
import com.amazonaws.services.dynamodbv2.local.shared.access.LocalDBAccess;
import com.amazonaws.services.dynamodbv2.local.shared.access.LocalDBUtils;
import com.amazonaws.services.dynamodbv2.local.shared.access.api.dp.ExecuteTransactionFunction;
import com.amazonaws.services.dynamodbv2.local.shared.env.LocalPartiQLDbEnv;
import com.amazonaws.services.dynamodbv2.local.shared.exceptions.LocalDBClientExceptionMessage;
import com.amazonaws.services.dynamodbv2.local.shared.helpers.MultiTableLock;
import com.amazonaws.services.dynamodbv2.local.shared.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.local.shared.model.TableNameAndPrimaryKey;
import com.amazonaws.services.dynamodbv2.local.shared.partiql.ParsedPartiQLRequest;
import com.amazonaws.services.dynamodbv2.local.shared.partiql.model.TranslatedPartiQLOperation;
import com.amazonaws.services.dynamodbv2.local.shared.partiql.processor.TransactionProcessor;
import com.amazonaws.services.dynamodbv2.model.CancellationReason;
import com.amazonaws.services.dynamodbv2.model.ExecuteTransactionResult;
import com.amazonaws.services.dynamodbv2.model.ItemResponse;
import com.amazonaws.services.dynamodbv2.rr.ProjectionExpressionWrapper;
import ddb.partiql.shared.util.OperationName;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import org.partiql.lang.ast.DataManipulation;

public class TransactionGetProcessor
extends TransactionProcessor {
    public TransactionGetProcessor(LocalDBAccess dbAccess, LocalPartiQLDbEnv localPartiQLDbEnv, ExecuteTransactionFunction executeTransactionFunction, DocumentFactory documentFactory) {
        super(dbAccess, localPartiQLDbEnv, executeTransactionFunction, documentFactory);
    }

    @Override
    public ExecuteTransactionResult execute(String clientRequestToken, List<ParsedPartiQLRequest<DataManipulation>> parsedPartiQLRequests) {
        List<TranslatedPartiQLOperation> translatedPartiQLOperations = this.performTranslations(parsedPartiQLRequests);
        List<ItemResponse> responses = this.partiqlTransactGet(translatedPartiQLOperations);
        return new ExecuteTransactionResult().withResponses(responses);
    }

    private List<ItemResponse> partiqlTransactGet(final List<TranslatedPartiQLOperation> translatedPartiQLOperations) {
        TreeSet<String> tableNames = new TreeSet<String>();
        Set<TableNameAndPrimaryKey> keysInvolved = Sets.newHashSet(new TableNameAndPrimaryKey[0]);
        for (TranslatedPartiQLOperation translatedPartiQLOperation : translatedPartiQLOperations) {
            tableNames.add(translatedPartiQLOperation.getTableName());
            if (keysInvolved.add(this.generateTableNameAndPrimaryKey(translatedPartiQLOperation))) continue;
            throw this.localPartiQLDbEnv.createValidationError(LocalDBClientExceptionMessage.TRANSACT_DUPLICATE_KEY.getMessage());
        }
        MultiTableLock tableLocker = new MultiTableLock(tableNames, this.dbAccess, MultiTableLock.LockMode.READ);
        final ArrayList returnedRecords = new ArrayList();
        Runnable criticalSection = new Runnable(){

            @Override
            public void run() {
                boolean canceled = false;
                List<CancellationReason> cancellationReasons = Lists.newArrayList(new CancellationReason[0]);
                for (int i = 0; i < translatedPartiQLOperations.size(); ++i) {
                    TranslatedPartiQLOperation operation = (TranslatedPartiQLOperation)translatedPartiQLOperations.get(i);
                    try {
                        Map<String, AttributeValue> key = operation.getItem();
                        TransactionGetProcessor.this.partiQLStatementFunction.validateGetKey((Map)key, TransactionGetProcessor.this.dbAccess.getTableInfo(operation.getTableName()));
                        Map<String, AttributeValue> result = TransactionGetProcessor.this.dbAccess.getRecord(operation.getTableName(), operation.getItem());
                        returnedRecords.add(result);
                        cancellationReasons.add(TransactionGetProcessor.this.errorMapper.getEmptyCancellationReason());
                    } catch (DynamoDBLocalServiceException e) {
                        canceled = true;
                        cancellationReasons.add(TransactionGetProcessor.this.mapToCancellationReasonTransactGet(e, operation.getOperationName(), i));
                        for (int remainingOperations = 1; remainingOperations < translatedPartiQLOperations.size() - i; ++remainingOperations) {
                            cancellationReasons.add(TransactionGetProcessor.this.errorMapper.getEmptyCancellationReason());
                        }
                    }
                    if (canceled) break;
                }
                if (canceled) {
                    throw AWSExceptionFactory.buildTransactionCanceledException(cancellationReasons);
                }
            }
        };
        tableLocker.wrapInTableLocks(criticalSection).run();
        ArrayList<ItemResponse> itemResponses = new ArrayList<ItemResponse>();
        long transactionResponsePayloadSizeBytes = 0L;
        for (int i = 0; i < translatedPartiQLOperations.size(); ++i) {
            ProjectionExpressionWrapper projectionExpressionWrapper = translatedPartiQLOperations.get(i).getProjectionExpressionWrapper();
            ProjectionExpression projectionExpression = projectionExpressionWrapper == null ? null : projectionExpressionWrapper.getProjection();
            Map<String, AttributeValue> projectedAttributes = projectionExpression != null ? LocalDBUtils.projectAttributes((Map<String, AttributeValue>)((Map)returnedRecords.get(i)), projectionExpression) : (Map<String, AttributeValue>)returnedRecords.get(i);
            transactionResponsePayloadSizeBytes += 4L;
            if ((transactionResponsePayloadSizeBytes += LocalDBUtils.getItemSizeBytes(projectedAttributes)) > 0x400000L) {
                throw this.localPartiQLDbEnv.createValidationError(LocalDBClientExceptionMessage.TRANSACT_RESPONSE_PAYLOAD_TOO_LARGE.getMessage() + transactionResponsePayloadSizeBytes);
            }
            itemResponses.add(new ItemResponse().withItem(this.partiQLStatementFunction.localDBOutputConverter.internalToExternalAttributes(projectedAttributes)));
        }
        return itemResponses;
    }

    private CancellationReason mapToCancellationReasonTransactGet(DynamoDBLocalServiceException e, OperationName operationName, int memberPosition) {
        String msg;
        if (AmazonServiceExceptionType.VALIDATION_EXCEPTION.getErrorCode().equals(e.getErrorCode()) && ((msg = e.getMessage()).contains(LocalDBClientExceptionMessage.EMPTY_KEY_ATTRIBUTE_VALUE_STRING.getMessage()) || msg.contains(LocalDBClientExceptionMessage.EMPTY_KEY_ATTRIBUTE_VALUE_BINARY.getMessage()))) {
            return new CancellationReason().withCode("ValidationError").withMessage(msg);
        }
        return this.mapToCancellationReasonWhenHandlingTransactionOperationforPartiQL(e, operationName, memberPosition);
    }
}

