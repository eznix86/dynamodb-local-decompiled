/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  com.amazonaws.AmazonServiceException
 *  com.amazonaws.services.dynamodbv2.model.CancellationReason
 *  com.amazonaws.services.dynamodbv2.model.ExecuteTransactionResult
 *  com.amazonaws.services.dynamodbv2.model.ReturnValue
 */
package com.amazonaws.services.dynamodbv2.local.shared.partiql.processor;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.dynamodbv2.datamodel.DocumentFactory;
import com.amazonaws.services.dynamodbv2.exceptions.AWSExceptionFactory;
import com.amazonaws.services.dynamodbv2.exceptions.AmazonServiceExceptionType;
import com.amazonaws.services.dynamodbv2.exceptions.DynamoDBLocalServiceException;
import com.amazonaws.services.dynamodbv2.local.shared.access.LocalDBAccess;
import com.amazonaws.services.dynamodbv2.local.shared.access.api.dp.ExecuteTransactionFunction;
import com.amazonaws.services.dynamodbv2.local.shared.env.LocalPartiQLDbEnv;
import com.amazonaws.services.dynamodbv2.local.shared.exceptions.LocalDBClientExceptionMessage;
import com.amazonaws.services.dynamodbv2.local.shared.mapper.DynamoDBObjectMapper;
import com.amazonaws.services.dynamodbv2.local.shared.partiql.ParsedPartiQLRequest;
import com.amazonaws.services.dynamodbv2.local.shared.partiql.model.TranslatedPartiQLOperation;
import com.amazonaws.services.dynamodbv2.local.shared.partiql.processor.PartiQLProcessor;
import com.amazonaws.services.dynamodbv2.local.shared.partiql.translator.SelectStatementTranslator;
import com.amazonaws.services.dynamodbv2.local.shared.validate.TransactionErrorMapper;
import com.amazonaws.services.dynamodbv2.model.CancellationReason;
import com.amazonaws.services.dynamodbv2.model.ExecuteTransactionResult;
import com.amazonaws.services.dynamodbv2.model.ReturnValue;
import ddb.partiql.shared.util.OperationName;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.partiql.lang.ast.DataManipulation;
import org.partiql.lang.ast.Select;

public abstract class TransactionProcessor
extends PartiQLProcessor {
    private static final String KEY_ATTRIBUTE_ABSENT_PREFIX = "Key attribute should be present in the item:";
    private static final String SCHEMA_KEY_MISMATCH_PREFIX = "Key attribute's data type should match its data type in table's schema:";
    public static String RETURNING_NOT_SUPPORTED_TRANSACTIONS = "RETURNING clause is not supported in ExecuteTransaction.";
    public static String VALIDATION_FAILED_TRANSACTIONS = "Validation failed in TransactStatements[%s]: %s";
    protected final DynamoDBObjectMapper mapper = new DynamoDBObjectMapper();
    protected TransactionErrorMapper errorMapper;

    protected TransactionProcessor(LocalDBAccess dbAccess, LocalPartiQLDbEnv localPartiQLDbEnv, ExecuteTransactionFunction executeTransactionFunction, DocumentFactory documentFactory) {
        super(dbAccess, localPartiQLDbEnv, executeTransactionFunction, documentFactory);
        this.errorMapper = new TransactionErrorMapper(executeTransactionFunction.awsExceptionFactory);
    }

    public abstract ExecuteTransactionResult execute(String var1, List<ParsedPartiQLRequest<DataManipulation>> var2);

    protected List<TranslatedPartiQLOperation> performTranslations(List<ParsedPartiQLRequest<DataManipulation>> parsedPartiQLRequests) {
        ArrayList<TranslatedPartiQLOperation> translatedPartiQLOperations = new ArrayList<TranslatedPartiQLOperation>();
        int index = 0;
        for (ParsedPartiQLRequest<DataManipulation> parsedPartiQLRequest : parsedPartiQLRequests) {
            DataManipulation exprNode = parsedPartiQLRequest.getExprNode();
            Class<?> opClass = exprNode instanceof DataManipulation ? exprNode.getDmlOperations().getOps().get(0).getClass() : exprNode.getClass();
            try {
                TranslatedPartiQLOperation translatedPartiQLOperation = Select.class.equals(opClass) ? ((SelectStatementTranslator)this.partiQLStatementFunction.statementTranslators.get(opClass)).translate(parsedPartiQLRequest, SelectStatementTranslator.SelectUseCase.TRANSACTION) : this.partiQLStatementFunction.statementTranslators.get(opClass).translate(parsedPartiQLRequest);
                if (!ReturnValue.NONE.equals((Object)translatedPartiQLOperation.getReturnValue()) && translatedPartiQLOperation.getReturnValue() != null) {
                    throw this.localPartiQLDbEnv.createValidationError(RETURNING_NOT_SUPPORTED_TRANSACTIONS);
                }
                translatedPartiQLOperations.add(translatedPartiQLOperation);
            } catch (Exception e) {
                if (e.getMessage().contains("Where clause does not contain a mandatory equality on all key attributes") || e.getMessage().contains("Multiple conditions on same key")) {
                    throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.TRANSACTION_CANCELED_EXCEPTION, "ValidationError " + e.getMessage());
                }
                if (e.getMessage().contains(KEY_ATTRIBUTE_ABSENT_PREFIX) || e.getMessage().contains(SCHEMA_KEY_MISMATCH_PREFIX)) {
                    this.handleInvalidSchemaException(parsedPartiQLRequests.size(), index, e.getMessage());
                }
                if (e.getMessage().equals(LocalDBClientExceptionMessage.TABLE_DOES_NOT_EXIST.getMessage())) {
                    throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.RESOURCE_NOT_FOUND_EXCEPTION, LocalDBClientExceptionMessage.REQUESTED_RESOURCE_NOT_FOUND.getMessage());
                }
                throw this.localPartiQLDbEnv.createValidationError(String.format(VALIDATION_FAILED_TRANSACTIONS, index, e.getMessage()));
            }
            ++index;
        }
        return translatedPartiQLOperations;
    }

    public CancellationReason mapToCancellationReasonWhenHandlingTransactionOperationforPartiQL(DynamoDBLocalServiceException e, OperationName operationName, int memberPosition) {
        if (AmazonServiceExceptionType.VALIDATION_EXCEPTION.getErrorCode().equals(e.getErrorCode()) || AmazonServiceExceptionType.DUPLICATE_ITEM_EXCEPTION.getErrorCode().equals(e.getErrorCode())) {
            String msg = e.getMessage();
            if (LocalDBClientExceptionMessage.ITEM_CONTAINS_NULL_ATTRVALUE.getMessage().equals(msg)) {
                throw this.buildCoralValidationExceptionForPartiQL("null", operationName.verb, memberPosition, "key", LocalDBClientExceptionMessage.MEMBER_NULL.getMessage());
            }
            if (LocalDBClientExceptionMessage.INCONSISTENT_INDEX_KEY_TYPES.getMessage().equals(msg) || LocalDBClientExceptionMessage.INCONSISTENT_PRIMARY_KEY_TYPES.getMessage().equals(msg) || LocalDBClientExceptionMessage.KEY_VALUE_TOO_BIG.getMessage().equals(msg) || LocalDBClientExceptionMessage.ITEM_UPD_TOO_LARGE.getMessage().equals(msg) || LocalDBClientExceptionMessage.INCONSISTENT_TYPES.getMessage().equals(msg) || LocalDBClientExceptionMessage.ITEM_TOO_BIG.getMessage().equals(msg) || "Duplicate primary key exists in table".equals(msg)) {
                return new CancellationReason().withCode("ValidationError").withMessage(msg);
            }
        }
        if (AmazonServiceExceptionType.CONDITIONAL_CHECK_FAILED_EXCEPTION.getErrorCode().equals(e.getErrorCode())) {
            return new CancellationReason().withCode("ConditionalCheckFailed").withMessage(e.getMessage());
        }
        throw e;
    }

    protected AmazonServiceException buildCoralValidationExceptionForPartiQL(String value, String operationName, int writeItemPosition, String fieldName, String errorDetails) {
        String member = String.format("transactItems.%d.member.%s.%s", writeItemPosition, operationName, fieldName);
        return AWSExceptionFactory.buildCoralValidationException(value, member, errorDetails);
    }

    private void handleInvalidSchemaException(int requestsLength, int exceptionIndex, String exceptionMessage) {
        ArrayList<CancellationReason> cancellationReasons = new ArrayList<CancellationReason>(Collections.nCopies(requestsLength, this.errorMapper.getEmptyCancellationReason()));
        cancellationReasons.set(exceptionIndex, new CancellationReason().withCode("ValidationError").withMessage(exceptionMessage));
        throw AWSExceptionFactory.buildTransactionCanceledException(cancellationReasons);
    }
}

