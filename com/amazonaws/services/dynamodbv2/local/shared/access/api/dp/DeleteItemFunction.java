/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  com.amazonaws.services.dynamodbv2.model.Capacity
 *  com.amazonaws.services.dynamodbv2.model.ConsumedCapacity
 *  com.amazonaws.services.dynamodbv2.model.DeleteItemRequest
 *  com.amazonaws.services.dynamodbv2.model.DeleteItemResult
 *  com.amazonaws.services.dynamodbv2.model.ReturnConsumedCapacity
 *  com.amazonaws.services.dynamodbv2.model.ReturnValue
 *  com.amazonaws.services.dynamodbv2.model.ReturnValuesOnConditionCheckFailure
 */
package com.amazonaws.services.dynamodbv2.local.shared.access.api.dp;

import com.amazonaws.services.dynamodbv2.datamodel.DocumentFactory;
import com.amazonaws.services.dynamodbv2.datamodel.Expression;
import com.amazonaws.services.dynamodbv2.dbenv.DbEnv;
import com.amazonaws.services.dynamodbv2.exceptions.AWSExceptionFactory;
import com.amazonaws.services.dynamodbv2.exceptions.AmazonServiceExceptionType;
import com.amazonaws.services.dynamodbv2.local.shared.access.LocalDBAccess;
import com.amazonaws.services.dynamodbv2.local.shared.access.LocalDBInputConverter;
import com.amazonaws.services.dynamodbv2.local.shared.access.LocalDBOutputConverter;
import com.amazonaws.services.dynamodbv2.local.shared.access.LocalDBValidatorUtils;
import com.amazonaws.services.dynamodbv2.local.shared.access.TableInfo;
import com.amazonaws.services.dynamodbv2.local.shared.access.api.dp.WriteDataPlaneFunction;
import com.amazonaws.services.dynamodbv2.local.shared.exceptions.LocalDBClientExceptionMessage;
import com.amazonaws.services.dynamodbv2.local.shared.helpers.TransactionsEnabledMode;
import com.amazonaws.services.dynamodbv2.local.shared.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.local.shared.model.Condition;
import com.amazonaws.services.dynamodbv2.local.shared.model.ExpectedAttributeValue;
import com.amazonaws.services.dynamodbv2.model.Capacity;
import com.amazonaws.services.dynamodbv2.model.ConsumedCapacity;
import com.amazonaws.services.dynamodbv2.model.DeleteItemRequest;
import com.amazonaws.services.dynamodbv2.model.DeleteItemResult;
import com.amazonaws.services.dynamodbv2.model.ReturnConsumedCapacity;
import com.amazonaws.services.dynamodbv2.model.ReturnValue;
import com.amazonaws.services.dynamodbv2.model.ReturnValuesOnConditionCheckFailure;
import com.amazonaws.services.dynamodbv2.rr.ExpressionWrapper;
import java.util.Map;

public class DeleteItemFunction
extends WriteDataPlaneFunction<DeleteItemRequest, DeleteItemResult> {
    public DeleteItemFunction(LocalDBAccess dbAccess, DbEnv localDBEnv, LocalDBInputConverter inputConverter, LocalDBOutputConverter localDBOutputConverter, AWSExceptionFactory awsExceptionFactory, DocumentFactory documentFactory, TransactionsEnabledMode transactionsEnabledMode) {
        super(dbAccess, localDBEnv, inputConverter, localDBOutputConverter, awsExceptionFactory, documentFactory, transactionsEnabledMode);
    }

    @Override
    public DeleteItemResult apply(DeleteItemRequest deleteItemRequest) {
        String tableName = deleteItemRequest.getTableName();
        this.validateTableName(tableName);
        TableInfo tableInfo = this.validateTableExists(tableName);
        Map<String, ExpectedAttributeValue> expected = this.inputConverter.externalToInternalExpectedAttributes(deleteItemRequest.getExpected(), 409600);
        ReturnValue returnVals = this.validateReturnType(deleteItemRequest.getReturnValues(), false);
        LocalDBValidatorUtils.validateExpressions(deleteItemRequest, this.inputConverter);
        String returnValuesOnConditionCheckFailure = LocalDBValidatorUtils.validateReturnValuesOnConditionCheckFailure(deleteItemRequest.getReturnValuesOnConditionCheckFailure());
        if (deleteItemRequest.getKey() == null) {
            throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, LocalDBClientExceptionMessage.MISSING_KEY.getMessage());
        }
        Map primaryKey = (Map)this.inputConverter.externalToInternalAttributes(deleteItemRequest.getKey());
        this.validateGetKey(primaryKey, tableInfo);
        this.validateExpecations(expected, deleteItemRequest.getConditionalOperator());
        ReturnConsumedCapacity returnConsumedCapacity = this.convertReturnConsumedCapacity(deleteItemRequest.getReturnConsumedCapacity());
        if (deleteItemRequest.getConditionExpression() == null && expected.isEmpty()) {
            return this.itemDeleteNoCondition(tableName, primaryKey, returnVals, returnConsumedCapacity);
        }
        if (deleteItemRequest.getConditionExpression() != null) {
            ExpressionWrapper conditionExpressionWrapper = this.inputConverter.externalToInternalConditionExpression(deleteItemRequest.getConditionExpression(), deleteItemRequest.getExpressionAttributeNames(), deleteItemRequest.getExpressionAttributeValues());
            LocalDBValidatorUtils.validateNoNestedAccessToKeyAttributeInExpression(this.dbAccess.getTableInfo(tableName), conditionExpressionWrapper, this.awsExceptionFactory);
            Expression conditionExpression = conditionExpressionWrapper == null ? null : conditionExpressionWrapper.getExpression();
            return this.itemDeleteWithConditionExpression(tableName, returnVals, returnConsumedCapacity, primaryKey, conditionExpression, returnValuesOnConditionCheckFailure);
        }
        return this.itemDeleteConditionalOperator(tableName, expected, returnVals, returnConsumedCapacity, primaryKey, deleteItemRequest.getConditionalOperator(), returnValuesOnConditionCheckFailure);
    }

    private void validateExpecations(Map<String, ExpectedAttributeValue> expected, String conditionalOperatorAsString) {
        if (expected.isEmpty() && conditionalOperatorAsString != null) {
            throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, LocalDBClientExceptionMessage.COND_OP_WITHOUT_FILTER_OR_EXPECTED.getMessage());
        }
        for (Map.Entry<String, ExpectedAttributeValue> entry : expected.entrySet()) {
            this.validateExpectedAttribute(entry.getKey(), entry.getValue());
        }
    }

    private DeleteItemResult itemDeleteNoCondition(final String tableName, final Map<String, AttributeValue> primaryKey, final ReturnValue returnVals, final ReturnConsumedCapacity returnConsumedCapacity) {
        final DeleteItemResult result = new DeleteItemResult();
        new LocalDBAccess.ReadLockWithTimeout(this.dbAccess.getLockForTable(tableName), 10){

            @Override
            public void criticalSection() {
                Map<String, AttributeValue> oldItem = DeleteItemFunction.this.dbAccess.getRecord(tableName, primaryKey);
                if (oldItem != null) {
                    result.setConsumedCapacity(DeleteItemFunction.this.computeWriteCapacity(tableName, DeleteItemFunction.this.createDeleteMutation(oldItem), returnConsumedCapacity, DeleteItemFunction.this.transactionsMode));
                    if (returnVals == ReturnValue.ALL_OLD || returnVals == ReturnValue.UPDATED_OLD) {
                        result.setAttributes(DeleteItemFunction.this.localDBOutputConverter.internalToExternalAttributes(oldItem));
                    }
                } else {
                    DeleteItemFunction.this.singletonCapacity(result, tableName, returnConsumedCapacity);
                }
                DeleteItemFunction.this.dbAccess.deleteRecord(tableName, primaryKey, false);
            }
        }.execute();
        return result;
    }

    private void computeAndPopulateCapacity(Map<String, AttributeValue> oldItem, String tableName, Map<String, AttributeValue> primaryKey, DeleteItemResult deleteResult, ReturnConsumedCapacity returnConsumedCapacity, ReturnValue returnVals) {
        this.dbAccess.deleteRecord(tableName, primaryKey, false);
        if (oldItem != null) {
            deleteResult.setConsumedCapacity(this.computeWriteCapacity(tableName, this.createDeleteMutation(oldItem), returnConsumedCapacity, this.transactionsMode));
            if (returnVals == ReturnValue.ALL_OLD) {
                deleteResult.setAttributes(this.localDBOutputConverter.internalToExternalAttributes(oldItem));
            }
        } else {
            this.singletonCapacity(deleteResult, tableName, returnConsumedCapacity);
        }
    }

    private DeleteItemResult itemDeleteWithConditionExpression(final String tableName, final ReturnValue returnVals, final ReturnConsumedCapacity returnConsumedCapacity, final Map<String, AttributeValue> primaryKey, final Expression conditionExpression, final String returnValuesOnConditionCheckFailure) {
        final DeleteItemResult deleteResult = new DeleteItemResult();
        new LocalDBAccess.WriteLockWithTimeout(this.dbAccess.getLockForTable(tableName), 10){

            @Override
            public void criticalSection() {
                Map<String, AttributeValue> oldItem = DeleteItemFunction.this.dbAccess.getRecord(tableName, primaryKey);
                if (!DeleteItemFunction.this.doesItemMatchConditionExpression((Map)oldItem, conditionExpression)) {
                    if (DeleteItemFunction.this.getReturnValuesOnConditionCheckFailure(returnValuesOnConditionCheckFailure).equals((Object)ReturnValuesOnConditionCheckFailure.ALL_OLD)) {
                        throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.CONDITIONAL_CHECK_FAILED_EXCEPTION, LocalDBClientExceptionMessage.CONDITIONAL_CHECK_FAILED.getMessage(), oldItem);
                    }
                    DeleteItemFunction.this.awsExceptionFactory.CONDITIONAL_CHECK_FAILED.throwAsException();
                }
                DeleteItemFunction.this.computeAndPopulateCapacity(oldItem, tableName, primaryKey, deleteResult, returnConsumedCapacity, returnVals);
            }
        }.execute();
        return deleteResult;
    }

    private void singletonCapacity(DeleteItemResult deleteResult, String tableName, ReturnConsumedCapacity returnConsumedCapacity) {
        deleteResult.setConsumedCapacity(new ConsumedCapacity().withCapacityUnits(Double.valueOf(1.0)).withTableName(tableName));
        if (ReturnConsumedCapacity.INDEXES == returnConsumedCapacity) {
            deleteResult.getConsumedCapacity().withTable(new Capacity().withCapacityUnits(Double.valueOf(1.0)));
        }
    }

    private DeleteItemResult itemDeleteConditionalOperator(final String tableName, final Map<String, ExpectedAttributeValue> expected, final ReturnValue returnVals, final ReturnConsumedCapacity returnConsumedCapacity, final Map<String, AttributeValue> primaryKey, final String conditionalOperatorAsString, final String returnValuesOnConditionCheckFailure) {
        final DeleteItemResult deleteResult = new DeleteItemResult();
        new LocalDBAccess.WriteLockWithTimeout(this.dbAccess.getLockForTable(tableName), 10){

            @Override
            public void criticalSection() {
                Map<String, AttributeValue> oldItem = DeleteItemFunction.this.dbAccess.getRecord(tableName, primaryKey);
                Map<String, Condition> conditions = DeleteItemFunction.this.convertToConditions(expected);
                DeleteItemFunction.this.validateConditions(conditions, conditionalOperatorAsString);
                if (!DeleteItemFunction.this.doesItemMatchConditionalOperator(oldItem, conditions, DeleteItemFunction.this.conditionalOperatorFrom(conditionalOperatorAsString))) {
                    if (DeleteItemFunction.this.getReturnValuesOnConditionCheckFailure(returnValuesOnConditionCheckFailure).equals((Object)ReturnValuesOnConditionCheckFailure.ALL_OLD)) {
                        throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.CONDITIONAL_CHECK_FAILED_EXCEPTION, LocalDBClientExceptionMessage.CONDITIONAL_CHECK_FAILED.getMessage(), oldItem);
                    }
                    throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.CONDITIONAL_CHECK_FAILED_EXCEPTION, LocalDBClientExceptionMessage.CONDITIONAL_CHECK_FAILED.getMessage());
                }
                DeleteItemFunction.this.computeAndPopulateCapacity(oldItem, tableName, primaryKey, deleteResult, returnConsumedCapacity, returnVals);
            }
        }.execute();
        return deleteResult;
    }
}

