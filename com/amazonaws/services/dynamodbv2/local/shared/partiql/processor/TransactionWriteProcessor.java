/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  com.amazonaws.services.dynamodbv2.model.CancellationReason
 *  com.amazonaws.services.dynamodbv2.model.ExecuteTransactionResult
 */
package com.amazonaws.services.dynamodbv2.local.shared.partiql.processor;

import com.amazonaws.services.dynamodbv2.datamodel.DocumentFactory;
import com.amazonaws.services.dynamodbv2.exceptions.AWSExceptionFactory;
import com.amazonaws.services.dynamodbv2.exceptions.AmazonServiceExceptionType;
import com.amazonaws.services.dynamodbv2.exceptions.DynamoDBLocalServiceException;
import com.amazonaws.services.dynamodbv2.local.google.Lists;
import com.amazonaws.services.dynamodbv2.local.google.Sets;
import com.amazonaws.services.dynamodbv2.local.shared.access.LocalDBAccess;
import com.amazonaws.services.dynamodbv2.local.shared.access.api.dp.ExecuteTransactionFunction;
import com.amazonaws.services.dynamodbv2.local.shared.env.LocalPartiQLDbEnv;
import com.amazonaws.services.dynamodbv2.local.shared.exceptions.LocalDBClientExceptionMessage;
import com.amazonaws.services.dynamodbv2.local.shared.helpers.MultiTableLock;
import com.amazonaws.services.dynamodbv2.local.shared.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.local.shared.model.TableNameAndPrimaryKey;
import com.amazonaws.services.dynamodbv2.local.shared.partiql.ParsedPartiQLRequest;
import com.amazonaws.services.dynamodbv2.local.shared.partiql.model.TranslatedPartiQLOperation;
import com.amazonaws.services.dynamodbv2.local.shared.partiql.processor.DeleteStatementProcessor;
import com.amazonaws.services.dynamodbv2.local.shared.partiql.processor.InsertStatementProcessor;
import com.amazonaws.services.dynamodbv2.local.shared.partiql.processor.TransactionProcessor;
import com.amazonaws.services.dynamodbv2.local.shared.partiql.processor.UpdateStatementProcessor;
import com.amazonaws.services.dynamodbv2.model.CancellationReason;
import com.amazonaws.services.dynamodbv2.model.ExecuteTransactionResult;
import com.fasterxml.jackson.core.JsonProcessingException;
import ddb.partiql.shared.util.OperationName;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.UUID;
import org.partiql.lang.ast.AssignmentOp;
import org.partiql.lang.ast.DataManipulation;
import org.partiql.lang.ast.DeleteOp;
import org.partiql.lang.ast.InsertValueOp;

public class TransactionWriteProcessor
extends TransactionProcessor {
    public TransactionWriteProcessor(LocalDBAccess dbAccess, LocalPartiQLDbEnv localPartiQLDbEnv, ExecuteTransactionFunction executeTransactionFunction, DocumentFactory documentFactory) {
        super(dbAccess, localPartiQLDbEnv, executeTransactionFunction, documentFactory);
    }

    @Override
    public ExecuteTransactionResult execute(String clientRequestToken, List<ParsedPartiQLRequest<DataManipulation>> parsedPartiQLRequests) {
        List<TranslatedPartiQLOperation> translatedPartiQLOperations = this.performTranslations(parsedPartiQLRequests);
        this.partiqlTransactWrite(clientRequestToken, translatedPartiQLOperations);
        return new ExecuteTransactionResult().withResponses(Collections.emptyList());
    }

    private void partiqlTransactWrite(String clientRequestToken, final List<TranslatedPartiQLOperation> translatedPartiQLOperations) {
        final String clientToken = clientRequestToken == null ? UUID.randomUUID().toString() : clientRequestToken;
        TreeSet<String> tableNames = new TreeSet<String>();
        long transactionPayloadSizeBytes = 0L;
        for (TranslatedPartiQLOperation translatedPartiQLOperation : translatedPartiQLOperations) {
            tableNames.add(translatedPartiQLOperation.getTableName());
            if ((transactionPayloadSizeBytes += this.getPartiQLOperationPayloadSize(translatedPartiQLOperation)) <= 0x400000L) continue;
            throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, LocalDBClientExceptionMessage.TRANSACT_REQUEST_PAYLOAD_TOO_LARGE.getMessage() + transactionPayloadSizeBytes);
        }
        MultiTableLock tableLocker = new MultiTableLock(tableNames, this.dbAccess, MultiTableLock.LockMode.WRITE);
        Runnable criticalSection = new Runnable(){

            @Override
            public void run() {
                byte[] transactionSignature = TransactionWriteProcessor.this.calculateTransactionSignature(translatedPartiQLOperations);
                byte[] previousSignature = TransactionWriteProcessor.this.dbAccess.beginTransaction(clientToken);
                if (previousSignature != null) {
                    try {
                        if (!Arrays.equals(transactionSignature, previousSignature)) {
                            throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.IDEMPOTENT_PARAMETER_MISMATCH_EXCEPTION, LocalDBClientExceptionMessage.TRANSACT_IDEMPOTENT_MISMATCH.getMessage());
                        }
                        return;
                    } finally {
                        TransactionWriteProcessor.this.dbAccess.rollbackTransaction();
                    }
                }
                try {
                    boolean canceled = false;
                    Set<TableNameAndPrimaryKey> keysInvolved = Sets.newHashSet(new TableNameAndPrimaryKey[0]);
                    List<CancellationReason> cancellationReasons = Lists.newArrayList(new CancellationReason[0]);
                    for (int i = 0; i < translatedPartiQLOperations.size(); ++i) {
                        TranslatedPartiQLOperation translatedPartiQLOperation = (TranslatedPartiQLOperation)translatedPartiQLOperations.get(i);
                        if (!keysInvolved.add(TransactionWriteProcessor.this.generateTableNameAndPrimaryKey(translatedPartiQLOperation))) {
                            throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, LocalDBClientExceptionMessage.TRANSACT_DUPLICATE_KEY.getMessage());
                        }
                        try {
                            switch (translatedPartiQLOperation.getOperationName()) {
                                case INSERT: {
                                    TransactionWriteProcessor.this.processInsertStatement(translatedPartiQLOperation, i);
                                    break;
                                }
                                case UPDATE: {
                                    TransactionWriteProcessor.this.processUpdateStatement(translatedPartiQLOperation, i);
                                    break;
                                }
                                case DELETE: {
                                    TransactionWriteProcessor.this.processDeleteStatement(translatedPartiQLOperation, i);
                                    break;
                                }
                                case CHECK_ITEM: {
                                    TransactionWriteProcessor.this.processConditionCheckStatement(translatedPartiQLOperation);
                                }
                            }
                            cancellationReasons.add(TransactionWriteProcessor.this.errorMapper.getEmptyCancellationReason());
                            continue;
                        } catch (DynamoDBLocalServiceException e) {
                            canceled = true;
                            cancellationReasons.add(TransactionWriteProcessor.this.mapToCancellationReasonWhenHandlingTransactionOperationforPartiQL(e, translatedPartiQLOperation.getOperationName(), i));
                        }
                    }
                    if (canceled) {
                        throw AWSExceptionFactory.buildTransactionCanceledException(cancellationReasons);
                    }
                    TransactionWriteProcessor.this.dbAccess.commitTransaction(clientToken, transactionSignature);
                } catch (RuntimeException e) {
                    TransactionWriteProcessor.this.dbAccess.rollbackTransaction();
                    throw e;
                }
            }
        };
        tableLocker.wrapInTableLocks(criticalSection).run();
    }

    private byte[] calculateTransactionSignature(List<TranslatedPartiQLOperation> translatedPartiQLOperations) {
        try {
            byte[] value = this.mapper.writeValueAsBytes(translatedPartiQLOperations);
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            return md.digest(value);
        } catch (JsonProcessingException | NoSuchAlgorithmException e) {
            throw AWSExceptionFactory.buildInternalServerException(e.getMessage());
        }
    }

    public void mapToCorrectExceptionForKeyedRequest(DynamoDBLocalServiceException e, String operationName, int itemPosition) {
        if (LocalDBClientExceptionMessage.MISSING_KEY.getMessage().equals(e.getMessage())) {
            throw this.buildCoralValidationExceptionForPartiQL("null", operationName, itemPosition + 1, "key", LocalDBClientExceptionMessage.MEMBER_NULL.getMessage());
        }
        throw e;
    }

    private void processInsertStatement(TranslatedPartiQLOperation translatedPartiQLOperation, int index) {
        try {
            ((InsertStatementProcessor)this.partiQLStatementFunction.statementProcessors.get(InsertValueOp.class)).invokePartiqlInsertItem(translatedPartiQLOperation);
        } catch (DynamoDBLocalServiceException e) {
            if (LocalDBClientExceptionMessage.INVALID_PUT_NULL.getMessage().equals(e.getMessage())) {
                throw this.buildCoralValidationExceptionForPartiQL("null", OperationName.INSERT.verb, index + 1, "item", LocalDBClientExceptionMessage.MEMBER_NULL.getMessage());
            }
            throw e;
        }
    }

    private void processUpdateStatement(TranslatedPartiQLOperation translatedPartiQLOperation, int index) {
        try {
            ((UpdateStatementProcessor)this.partiQLStatementFunction.statementProcessors.get(AssignmentOp.class)).invokePartiqlUpdateItem(translatedPartiQLOperation);
        } catch (DynamoDBLocalServiceException e) {
            if (e.getMessage().startsWith(this.partiQLStatementFunction.awsExceptionFactory.EXPR_ATTR_VALUES_WITHOUT_EXPRESSIONS.getMessage())) {
                throw this.buildCoralValidationExceptionForPartiQL("null", OperationName.UPDATE.verb, index + 1, "updateExpression", LocalDBClientExceptionMessage.MEMBER_NULL.getMessage());
            }
            if (e.getMessage().contains(LocalDBClientExceptionMessage.CANNOT_UPDATE_ATTRIBUTE.getMessage()) && e.getMessage().contains(this.partiQLStatementFunction.awsExceptionFactory.ATTRIBUTE_PART_OF_KEY.getMessage())) {
                List<CancellationReason> cancellationReasons = Lists.newArrayList(new CancellationReason[0]);
                CancellationReason cancellationReason = new CancellationReason().withCode("ValidationError").withMessage(e.getMessage());
                cancellationReasons.add(cancellationReason);
                throw AWSExceptionFactory.buildTransactionCanceledException(cancellationReasons);
            }
            this.mapToCorrectExceptionForKeyedRequest(e, OperationName.UPDATE.verb, index);
        }
    }

    private void processDeleteStatement(TranslatedPartiQLOperation translatedPartiQLOperation, int index) {
        try {
            ((DeleteStatementProcessor)this.partiQLStatementFunction.statementProcessors.get(DeleteOp.class)).invokePartiqlDeleteItem(translatedPartiQLOperation);
        } catch (DynamoDBLocalServiceException e) {
            this.mapToCorrectExceptionForKeyedRequest(e, OperationName.DELETE.verb, index);
        }
    }

    private void processConditionCheckStatement(TranslatedPartiQLOperation translatedPartiQLOperation) {
        Map<String, AttributeValue> returnedItem = this.dbAccess.getRecord(translatedPartiQLOperation.getTableName(), translatedPartiQLOperation.getItem());
        if (returnedItem == null || !this.partiQLStatementFunction.doesItemMatchFilterExpression((Map)returnedItem, translatedPartiQLOperation.getConditionExpressionWrapper().getExpression())) {
            throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.CONDITIONAL_CHECK_FAILED_EXCEPTION);
        }
    }
}

