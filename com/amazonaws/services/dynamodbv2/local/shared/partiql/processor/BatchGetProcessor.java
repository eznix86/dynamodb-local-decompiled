/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  com.amazonaws.services.dynamodbv2.model.BatchExecuteStatementResult
 *  com.amazonaws.services.dynamodbv2.model.BatchStatementError
 *  com.amazonaws.services.dynamodbv2.model.BatchStatementResponse
 */
package com.amazonaws.services.dynamodbv2.local.shared.partiql.processor;

import com.amazonaws.services.dynamodbv2.datamodel.DocumentFactory;
import com.amazonaws.services.dynamodbv2.datamodel.ProjectionExpression;
import com.amazonaws.services.dynamodbv2.exceptions.AmazonServiceExceptionType;
import com.amazonaws.services.dynamodbv2.exceptions.DynamoDBLocalServiceException;
import com.amazonaws.services.dynamodbv2.local.google.Sets;
import com.amazonaws.services.dynamodbv2.local.shared.access.LocalDBAccess;
import com.amazonaws.services.dynamodbv2.local.shared.access.LocalDBUtils;
import com.amazonaws.services.dynamodbv2.local.shared.access.api.dp.BatchExecuteStatementFunction;
import com.amazonaws.services.dynamodbv2.local.shared.env.LocalPartiQLDbEnv;
import com.amazonaws.services.dynamodbv2.local.shared.exceptions.LocalDBClientExceptionMessage;
import com.amazonaws.services.dynamodbv2.local.shared.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.local.shared.model.TableNameAndPrimaryKey;
import com.amazonaws.services.dynamodbv2.local.shared.partiql.ParsedPartiQLRequest;
import com.amazonaws.services.dynamodbv2.local.shared.partiql.model.TranslatedPartiQLOperation;
import com.amazonaws.services.dynamodbv2.local.shared.partiql.processor.BatchProcessor;
import com.amazonaws.services.dynamodbv2.local.shared.partiql.util.ObjectExceptionPair;
import com.amazonaws.services.dynamodbv2.model.BatchExecuteStatementResult;
import com.amazonaws.services.dynamodbv2.model.BatchStatementError;
import com.amazonaws.services.dynamodbv2.model.BatchStatementResponse;
import com.amazonaws.services.dynamodbv2.rr.ProjectionExpressionWrapper;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class BatchGetProcessor
extends BatchProcessor {
    private static final String ITEM_UNPROCESSED = "Total returned items size limit exceeded (16777216 ). Request not processed.";

    public BatchGetProcessor(LocalDBAccess dbAccess, LocalPartiQLDbEnv localPartiQLDbEnv, BatchExecuteStatementFunction batchExecuteStatementFunction, DocumentFactory documentFactory) {
        super(dbAccess, localPartiQLDbEnv, batchExecuteStatementFunction, documentFactory);
    }

    @Override
    public BatchExecuteStatementResult execute(List<ObjectExceptionPair<ParsedPartiQLRequest>> parsedPartiQLRequestsExceptionPairs) {
        List<ObjectExceptionPair<TranslatedPartiQLOperation>> translatedPartiQLOperationExceptionPairs = this.performTranslations(parsedPartiQLRequestsExceptionPairs);
        List<BatchStatementResponse> batchStatementResponses = this.partiqlBatchGet(translatedPartiQLOperationExceptionPairs);
        return new BatchExecuteStatementResult().withResponses(batchStatementResponses);
    }

    private List<BatchStatementResponse> partiqlBatchGet(List<ObjectExceptionPair<TranslatedPartiQLOperation>> translatedPartiQLOperationExceptionPairs) {
        long totalSize = 0L;
        ArrayList<BatchStatementResponse> batchStatementResponses = new ArrayList<BatchStatementResponse>();
        boolean returnLimitExceeded = false;
        Set<TableNameAndPrimaryKey> keysInvolved = Sets.newHashSet(new TableNameAndPrimaryKey[0]);
        for (ObjectExceptionPair<TranslatedPartiQLOperation> translatedPartiQLOperationExceptionPair : translatedPartiQLOperationExceptionPairs) {
            if (translatedPartiQLOperationExceptionPair.hasException()) {
                batchStatementResponses.add(this.convertInternalExceptionToBatchStatementResponse(translatedPartiQLOperationExceptionPair.getException(), null));
                continue;
            }
            TranslatedPartiQLOperation translatedPartiQLOperation = translatedPartiQLOperationExceptionPair.getObject();
            String tableName = translatedPartiQLOperation.getTableName();
            if (returnLimitExceeded) {
                batchStatementResponses.add(new BatchStatementResponse().withTableName(tableName).withError(new BatchStatementError().withCode(AmazonServiceExceptionType.REQUEST_TOO_LARGE.getErrorCode()).withMessage(ITEM_UNPROCESSED)));
                continue;
            }
            try {
                Map<String, AttributeValue> key = translatedPartiQLOperation.getItem();
                this.partiQLStatementFunction.validateGetKey((Map)key, this.dbAccess.getTableInfo(tableName));
                Map<String, AttributeValue> record = this.dbAccess.getRecord(tableName, key);
                if ((totalSize += LocalDBUtils.getItemSizeBytes(record)) > 0x1000000L) {
                    returnLimitExceeded = true;
                    batchStatementResponses.add(new BatchStatementResponse().withTableName(tableName).withError(new BatchStatementError().withCode(AmazonServiceExceptionType.REQUEST_TOO_LARGE.getErrorCode()).withMessage(ITEM_UNPROCESSED)));
                    continue;
                }
                ProjectionExpressionWrapper projectionExpressionWrapper = translatedPartiQLOperation.getProjectionExpressionWrapper();
                ProjectionExpression projectionExpression = projectionExpressionWrapper == null ? null : projectionExpressionWrapper.getProjection();
                Map<String, AttributeValue> projectedAttributes = LocalDBUtils.projectAttributes(record, projectionExpression);
                batchStatementResponses.add(new BatchStatementResponse().withTableName(tableName).withItem(this.partiQLStatementFunction.localDBOutputConverter.internalToExternalAttributes(projectedAttributes)));
            } catch (DynamoDBLocalServiceException localServiceException) {
                batchStatementResponses.add(this.convertInternalExceptionToBatchStatementResponse(localServiceException, null));
            }
            if (keysInvolved.add(this.generateTableNameAndPrimaryKey(translatedPartiQLOperation))) continue;
            throw this.localPartiQLDbEnv.createValidationError(LocalDBClientExceptionMessage.DUPLICATE_ITEM_KEY.getMessage());
        }
        return batchStatementResponses;
    }
}

