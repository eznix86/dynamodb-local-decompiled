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
import com.amazonaws.services.dynamodbv2.exceptions.AmazonServiceExceptionType;
import com.amazonaws.services.dynamodbv2.exceptions.DynamoDBLocalServiceException;
import com.amazonaws.services.dynamodbv2.local.shared.access.LocalDBAccess;
import com.amazonaws.services.dynamodbv2.local.shared.access.api.dp.BatchExecuteStatementFunction;
import com.amazonaws.services.dynamodbv2.local.shared.env.LocalPartiQLDbEnv;
import com.amazonaws.services.dynamodbv2.local.shared.partiql.ParsedPartiQLRequest;
import com.amazonaws.services.dynamodbv2.local.shared.partiql.model.TranslatedPartiQLOperation;
import com.amazonaws.services.dynamodbv2.local.shared.partiql.processor.PartiQLProcessor;
import com.amazonaws.services.dynamodbv2.local.shared.partiql.translator.SelectStatementTranslator;
import com.amazonaws.services.dynamodbv2.local.shared.partiql.util.ObjectExceptionPair;
import com.amazonaws.services.dynamodbv2.model.BatchExecuteStatementResult;
import com.amazonaws.services.dynamodbv2.model.BatchStatementError;
import com.amazonaws.services.dynamodbv2.model.BatchStatementResponse;
import java.util.ArrayList;
import java.util.List;
import org.partiql.lang.ast.DataManipulation;
import org.partiql.lang.ast.Select;

public abstract class BatchProcessor
extends PartiQLProcessor {
    public static final String PARTIQL_BATCH_PAYLOAD_TOO_LARGE = "BatchExecuteStatement payload size cannot exceed 16MB. Payload Size: %s";
    public static final String RESOURCE_NOT_FOUND_CODE = "ResourceNotFound";

    protected BatchProcessor(LocalDBAccess dbAccess, LocalPartiQLDbEnv localPartiQLDbEnv, BatchExecuteStatementFunction batchExecuteStatementFunction, DocumentFactory documentFactory) {
        super(dbAccess, localPartiQLDbEnv, batchExecuteStatementFunction, documentFactory);
    }

    public abstract BatchExecuteStatementResult execute(List<ObjectExceptionPair<ParsedPartiQLRequest>> var1);

    protected List<ObjectExceptionPair<TranslatedPartiQLOperation>> performTranslations(List<ObjectExceptionPair<ParsedPartiQLRequest>> parsedPartiQLRequestExceptionPairs) {
        ArrayList<ObjectExceptionPair<TranslatedPartiQLOperation>> translatedPartiQLOperationExceptionPairs = new ArrayList<ObjectExceptionPair<TranslatedPartiQLOperation>>();
        for (ObjectExceptionPair<ParsedPartiQLRequest> parsedPartiQLRequestExceptionPair : parsedPartiQLRequestExceptionPairs) {
            try {
                if (parsedPartiQLRequestExceptionPair.hasException()) {
                    throw parsedPartiQLRequestExceptionPair.getException();
                }
                ParsedPartiQLRequest parsedPartiQLRequest = parsedPartiQLRequestExceptionPair.getObject();
                Object exprNode = parsedPartiQLRequest.getExprNode();
                Class<?> opClass = exprNode instanceof DataManipulation ? ((DataManipulation)exprNode).getDmlOperations().getOps().get(0).getClass() : exprNode.getClass();
                TranslatedPartiQLOperation translatedPartiQLOperation = Select.class.equals(opClass) ? ((SelectStatementTranslator)this.partiQLStatementFunction.statementTranslators.get(opClass)).translate(parsedPartiQLRequest, SelectStatementTranslator.SelectUseCase.BATCH) : this.partiQLStatementFunction.statementTranslators.get(opClass).translate(parsedPartiQLRequest);
                translatedPartiQLOperationExceptionPairs.add(new ObjectExceptionPair<TranslatedPartiQLOperation>(translatedPartiQLOperation, null));
            } catch (DynamoDBLocalServiceException e) {
                translatedPartiQLOperationExceptionPairs.add(new ObjectExceptionPair<Object>(null, e));
            }
        }
        return translatedPartiQLOperationExceptionPairs;
    }

    protected BatchStatementResponse convertInternalExceptionToBatchStatementResponse(DynamoDBLocalServiceException localServiceException, String tableName) {
        String code = localServiceException.getErrorCode();
        if (code.equals(AmazonServiceExceptionType.VALIDATION_EXCEPTION.getErrorCode())) {
            code = "ValidationError";
        } else if (code.equals(AmazonServiceExceptionType.CONDITIONAL_CHECK_FAILED_EXCEPTION.getErrorCode())) {
            code = "ConditionalCheckFailed";
        } else if (code.equals(AmazonServiceExceptionType.RESOURCE_NOT_FOUND_EXCEPTION.getErrorCode())) {
            code = RESOURCE_NOT_FOUND_CODE;
        }
        return new BatchStatementResponse().withError(new BatchStatementError().withCode(code).withMessage(localServiceException.getMessage())).withTableName(tableName);
    }
}

