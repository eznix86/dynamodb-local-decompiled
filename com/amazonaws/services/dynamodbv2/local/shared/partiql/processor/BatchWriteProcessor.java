/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  com.amazonaws.services.dynamodbv2.model.BatchExecuteStatementResult
 *  com.amazonaws.services.dynamodbv2.model.BatchStatementError
 *  com.amazonaws.services.dynamodbv2.model.BatchStatementResponse
 *  com.amazonaws.services.dynamodbv2.model.ExecuteStatementResult
 */
package com.amazonaws.services.dynamodbv2.local.shared.partiql.processor;

import com.amazonaws.services.dynamodbv2.datamodel.DocumentFactory;
import com.amazonaws.services.dynamodbv2.exceptions.AWSExceptionFactory;
import com.amazonaws.services.dynamodbv2.exceptions.AmazonServiceExceptionType;
import com.amazonaws.services.dynamodbv2.exceptions.DynamoDBLocalServiceException;
import com.amazonaws.services.dynamodbv2.local.google.Sets;
import com.amazonaws.services.dynamodbv2.local.shared.access.LocalDBAccess;
import com.amazonaws.services.dynamodbv2.local.shared.access.LocalDBUtils;
import com.amazonaws.services.dynamodbv2.local.shared.access.api.dp.BatchExecuteStatementFunction;
import com.amazonaws.services.dynamodbv2.local.shared.env.LocalPartiQLDbEnv;
import com.amazonaws.services.dynamodbv2.local.shared.exceptions.LocalDBAccessException;
import com.amazonaws.services.dynamodbv2.local.shared.exceptions.LocalDBClientExceptionMessage;
import com.amazonaws.services.dynamodbv2.local.shared.exceptions.LocalDBClientExceptionType;
import com.amazonaws.services.dynamodbv2.local.shared.model.TableNameAndPrimaryKey;
import com.amazonaws.services.dynamodbv2.local.shared.partiql.ParsedPartiQLRequest;
import com.amazonaws.services.dynamodbv2.local.shared.partiql.model.TranslatedPartiQLOperation;
import com.amazonaws.services.dynamodbv2.local.shared.partiql.processor.BatchProcessor;
import com.amazonaws.services.dynamodbv2.local.shared.partiql.processor.DeleteStatementProcessor;
import com.amazonaws.services.dynamodbv2.local.shared.partiql.processor.InsertStatementProcessor;
import com.amazonaws.services.dynamodbv2.local.shared.partiql.processor.UpdateStatementProcessor;
import com.amazonaws.services.dynamodbv2.local.shared.partiql.util.ObjectExceptionPair;
import com.amazonaws.services.dynamodbv2.model.BatchExecuteStatementResult;
import com.amazonaws.services.dynamodbv2.model.BatchStatementError;
import com.amazonaws.services.dynamodbv2.model.BatchStatementResponse;
import com.amazonaws.services.dynamodbv2.model.ExecuteStatementResult;
import ddb.partiql.shared.util.OperationName;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.partiql.lang.ast.AssignmentOp;
import org.partiql.lang.ast.DeleteOp;
import org.partiql.lang.ast.InsertValueOp;

public class BatchWriteProcessor
extends BatchProcessor {
    public BatchWriteProcessor(LocalDBAccess dbAccess, LocalPartiQLDbEnv localPartiQLDbEnv, BatchExecuteStatementFunction batchExecuteStatementFunction, DocumentFactory documentFactory) {
        super(dbAccess, localPartiQLDbEnv, batchExecuteStatementFunction, documentFactory);
    }

    @Override
    public BatchExecuteStatementResult execute(List<ObjectExceptionPair<ParsedPartiQLRequest>> parsedPartiQLRequestsExceptionPairs) {
        List<ObjectExceptionPair<TranslatedPartiQLOperation>> translatedPartiQLOperationExceptionPairs = this.performTranslations(parsedPartiQLRequestsExceptionPairs);
        List<BatchStatementResponse> batchStatementResponses = this.partiqlBatchWrite(translatedPartiQLOperationExceptionPairs);
        return new BatchExecuteStatementResult().withResponses(batchStatementResponses);
    }

    private List<BatchStatementResponse> partiqlBatchWrite(List<ObjectExceptionPair<TranslatedPartiQLOperation>> translatedPartiQLOperationExceptionPairs) {
        long totalRequestSize = 0L;
        Set<TableNameAndPrimaryKey> keysInvolved = Sets.newHashSet(new TableNameAndPrimaryKey[0]);
        final ArrayList<BatchStatementResponse> batchStatementResponses = new ArrayList<BatchStatementResponse>();
        for (ObjectExceptionPair<TranslatedPartiQLOperation> translatedPartiQLOperationExceptionPair : translatedPartiQLOperationExceptionPairs) {
            if (translatedPartiQLOperationExceptionPair.hasException()) {
                batchStatementResponses.add(this.convertInternalExceptionToBatchStatementResponse(translatedPartiQLOperationExceptionPair.getException(), null));
                continue;
            }
            final TranslatedPartiQLOperation translatedPartiQLOperation = translatedPartiQLOperationExceptionPair.getObject();
            if ((totalRequestSize += this.getPartiQLOperationPayloadSize(translatedPartiQLOperation)) > 0x1000000L) {
                throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, String.format("BatchExecuteStatement payload size cannot exceed 16MB. Payload Size: %s", totalRequestSize));
            }
            final String tableName = translatedPartiQLOperation.getTableName();
            try {
                if (translatedPartiQLOperation.getOperationName().equals((Object)OperationName.INSERT)) {
                    this.partiQLStatementFunction.validatePutItem((Map)translatedPartiQLOperation.getItem(), this.partiQLStatementFunction.validateTableExists(tableName));
                } else {
                    this.partiQLStatementFunction.validateGetKey((Map)translatedPartiQLOperation.getItem(), this.partiQLStatementFunction.validateTableExists(tableName));
                }
            } catch (DynamoDBLocalServiceException e) {
                batchStatementResponses.add(this.convertInternalExceptionToBatchStatementResponse(e, tableName));
                continue;
            }
            try {
                new LocalDBAccess.WriteLockWithTimeout(this.dbAccess.getLockForTable(translatedPartiQLOperation.getTableName()), 10){

                    @Override
                    public void criticalSection() {
                        ExecuteStatementResult executeStatementResult = null;
                        switch (translatedPartiQLOperation.getOperationName()) {
                            case INSERT: {
                                executeStatementResult = ((InsertStatementProcessor)BatchWriteProcessor.this.partiQLStatementFunction.statementProcessors.get(InsertValueOp.class)).invokePartiqlInsertItem(translatedPartiQLOperation);
                                break;
                            }
                            case UPDATE: {
                                executeStatementResult = ((UpdateStatementProcessor)BatchWriteProcessor.this.partiQLStatementFunction.statementProcessors.get(AssignmentOp.class)).invokePartiqlUpdateItem(translatedPartiQLOperation);
                                break;
                            }
                            case DELETE: {
                                executeStatementResult = ((DeleteStatementProcessor)BatchWriteProcessor.this.partiQLStatementFunction.statementProcessors.get(DeleteOp.class)).invokePartiqlDeleteItem(translatedPartiQLOperation);
                                break;
                            }
                            default: {
                                LocalDBUtils.ldClientFail(LocalDBClientExceptionType.UNREACHABLE_CODE);
                            }
                        }
                        Map returnedAttributes = null;
                        if (executeStatementResult.getItems().size() != 0) {
                            returnedAttributes = (Map)executeStatementResult.getItems().get(0);
                        }
                        batchStatementResponses.add(new BatchStatementResponse().withTableName(tableName).withItem(returnedAttributes));
                    }
                }.execute();
            } catch (LocalDBAccessException accessException) {
                if (accessException.getMessage().equals(LocalDBClientExceptionMessage.TIME_OUT_WHILE_ACQUIRING_LOCK.getMessage())) {
                    throw accessException;
                }
                batchStatementResponses.add(new BatchStatementResponse().withError(new BatchStatementError().withCode(accessException.getType().getMessage()).withMessage(accessException.getMessage())).withTableName(tableName));
            } catch (DynamoDBLocalServiceException localServiceException) {
                batchStatementResponses.add(this.convertInternalExceptionToBatchStatementResponse(localServiceException, tableName));
            }
            if (keysInvolved.add(this.generateTableNameAndPrimaryKey(translatedPartiQLOperation))) continue;
            throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, LocalDBClientExceptionMessage.DUPLICATE_ITEM_KEY.getMessage());
        }
        return batchStatementResponses;
    }
}

