/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  com.amazonaws.services.dynamodbv2.model.PutItemRequest
 *  com.amazonaws.services.dynamodbv2.model.PutItemResult
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
import com.amazonaws.services.dynamodbv2.local.shared.access.LocalDBUtils;
import com.amazonaws.services.dynamodbv2.local.shared.access.LocalDBValidatorUtils;
import com.amazonaws.services.dynamodbv2.local.shared.access.TableInfo;
import com.amazonaws.services.dynamodbv2.local.shared.access.api.Mutation;
import com.amazonaws.services.dynamodbv2.local.shared.access.api.dp.WriteDataPlaneFunction;
import com.amazonaws.services.dynamodbv2.local.shared.exceptions.LocalDBClientExceptionMessage;
import com.amazonaws.services.dynamodbv2.local.shared.helpers.TransactionsEnabledMode;
import com.amazonaws.services.dynamodbv2.local.shared.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.local.shared.model.Condition;
import com.amazonaws.services.dynamodbv2.local.shared.model.ExpectedAttributeValue;
import com.amazonaws.services.dynamodbv2.model.PutItemRequest;
import com.amazonaws.services.dynamodbv2.model.PutItemResult;
import com.amazonaws.services.dynamodbv2.model.ReturnConsumedCapacity;
import com.amazonaws.services.dynamodbv2.model.ReturnValue;
import com.amazonaws.services.dynamodbv2.model.ReturnValuesOnConditionCheckFailure;
import com.amazonaws.services.dynamodbv2.rr.ExpressionWrapper;
import java.util.Map;

public class PutItemFunction
extends WriteDataPlaneFunction<PutItemRequest, PutItemResult> {
    public PutItemFunction(LocalDBAccess dbAccess, DbEnv localDBEnv, LocalDBInputConverter inputConverter, LocalDBOutputConverter localDBOutputConverter, AWSExceptionFactory awsExceptionFactory, DocumentFactory documentFactory, TransactionsEnabledMode transactionsEnabledMode) {
        super(dbAccess, localDBEnv, inputConverter, localDBOutputConverter, awsExceptionFactory, documentFactory, transactionsEnabledMode);
    }

    @Override
    public PutItemResult apply(final PutItemRequest putItemRequest) {
        final String tableName = putItemRequest.getTableName();
        this.validateTableName(tableName);
        final TableInfo tableInfo = this.validateTableExists(tableName);
        final ReturnValue returnVals = this.validateReturnType(putItemRequest.getReturnValues(), false);
        LocalDBValidatorUtils.validateExpressions(putItemRequest, this.inputConverter);
        if (putItemRequest.getItem() == null) {
            throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, LocalDBClientExceptionMessage.INVALID_PUT_NULL.getMessage());
        }
        final Map record = (Map)this.inputConverter.externalToInternalAttributes(putItemRequest.getItem());
        final Map key = this.validatePutItem(record, tableInfo);
        final Map<String, ExpectedAttributeValue> expected = this.inputConverter.externalToInternalExpectedAttributes(putItemRequest.getExpected(), 409600);
        final PutItemResult putResult = new PutItemResult();
        String conditionExpressionString = putItemRequest.getConditionExpression();
        final String returnValuesOnConditionCheckFailure = LocalDBValidatorUtils.validateReturnValuesOnConditionCheckFailure(putItemRequest.getReturnValuesOnConditionCheckFailure());
        String conditionalOperatorAsString = putItemRequest.getConditionalOperator();
        this.validateExpectations(expected, conditionalOperatorAsString);
        final ReturnConsumedCapacity returnConsumedCapacity = this.convertReturnConsumedCapacity(putItemRequest.getReturnConsumedCapacity());
        if (conditionExpressionString == null && expected.isEmpty()) {
            new LocalDBAccess.WriteLockWithTimeout(this.dbAccess.getLockForTable(tableName), 10){

                @Override
                public void criticalSection() {
                    PutItemFunction.this.putItemNoCondition(tableName, tableInfo, returnVals, record, key, putResult, returnConsumedCapacity);
                }
            }.execute();
        } else if (conditionExpressionString != null) {
            ExpressionWrapper conditionExpressionWrapper = this.inputConverter.externalToInternalConditionExpression(conditionExpressionString, putItemRequest.getExpressionAttributeNames(), putItemRequest.getExpressionAttributeValues());
            final Expression conditionExpression = conditionExpressionWrapper == null ? null : conditionExpressionWrapper.getExpression();
            LocalDBValidatorUtils.validateNoNestedAccessToKeyAttributeInExpression(tableInfo, conditionExpressionWrapper, this.awsExceptionFactory);
            new LocalDBAccess.WriteLockWithTimeout(this.dbAccess.getLockForTable(tableName), 10){

                @Override
                public void criticalSection() {
                    PutItemFunction.this.putItemWithConditionExpression(tableName, tableInfo, returnVals, record, key, conditionExpression, putResult, returnConsumedCapacity, returnValuesOnConditionCheckFailure);
                }
            }.execute();
        } else {
            new LocalDBAccess.WriteLockWithTimeout(this.dbAccess.getLockForTable(tableName), 10){

                @Override
                public void criticalSection() {
                    PutItemFunction.this.putItemConditionalOperator(putItemRequest, tableName, tableInfo, returnVals, record, key, expected, putResult, returnConsumedCapacity, returnValuesOnConditionCheckFailure);
                }
            }.execute();
        }
        return putResult;
    }

    void putItemWithConditionExpression(String tableName, TableInfo tableInfo, ReturnValue returnVals, Map<String, AttributeValue> record, Map<String, AttributeValue> key, Expression conditionExpression, PutItemResult putResult, ReturnConsumedCapacity returnConsumedCapacity, String returnValuesOnConditionCheckFailure) {
        Map<String, AttributeValue> oldItem = this.dbAccess.getRecord(tableName, key);
        if (conditionExpression != null && !LocalDBUtils.doesItemMatchCondition(oldItem, conditionExpression, this.localDBEnv, this.documentFactory)) {
            if (this.getReturnValuesOnConditionCheckFailure(returnValuesOnConditionCheckFailure).equals((Object)ReturnValuesOnConditionCheckFailure.ALL_OLD)) {
                throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.CONDITIONAL_CHECK_FAILED_EXCEPTION, LocalDBClientExceptionMessage.CONDITIONAL_CHECK_FAILED.getMessage(), oldItem);
            }
            this.awsExceptionFactory.CONDITIONAL_CHECK_FAILED.throwAsException();
        }
        AttributeValue rangeKey = null;
        if (tableInfo != null && tableInfo.hasRangeKey()) {
            rangeKey = key.get(tableInfo.getRangeKey().getAttributeName());
        }
        this.dbAccess.putRecord(tableName, record, key.get(tableInfo.getHashKey().getAttributeName()), rangeKey, false);
        if (returnVals == ReturnValue.ALL_OLD) {
            putResult.setAttributes(this.localDBOutputConverter.internalToExternalAttributes(oldItem));
        }
        putResult.withConsumedCapacity(this.computeWriteCapacity(tableName, new Mutation(oldItem, record), returnConsumedCapacity, this.transactionsMode));
    }

    private void putItemConditionalOperator(PutItemRequest putItemRequest, String tableName, TableInfo tableInfo, ReturnValue returnVals, Map<String, AttributeValue> record, Map<String, AttributeValue> key, Map<String, ExpectedAttributeValue> expected, PutItemResult putResult, ReturnConsumedCapacity returnConsumedCapacity, String returnValuesOnConditionCheckFailure) {
        Map<String, AttributeValue> oldItem = this.dbAccess.getRecord(tableName, key);
        String conditionalOperatorAsString = putItemRequest.getConditionalOperator();
        this.validateExpectations(expected, conditionalOperatorAsString);
        Map<String, Condition> conditions = this.convertToConditions(expected);
        this.validateConditions(conditions, conditionalOperatorAsString);
        if (!this.doesItemMatchConditionalOperator(oldItem, conditions, this.conditionalOperatorFrom(conditionalOperatorAsString))) {
            if (this.getReturnValuesOnConditionCheckFailure(returnValuesOnConditionCheckFailure).equals((Object)ReturnValuesOnConditionCheckFailure.ALL_OLD)) {
                throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.CONDITIONAL_CHECK_FAILED_EXCEPTION, LocalDBClientExceptionMessage.CONDITIONAL_CHECK_FAILED.getMessage(), oldItem);
            }
            throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.CONDITIONAL_CHECK_FAILED_EXCEPTION, LocalDBClientExceptionMessage.CONDITIONAL_CHECK_FAILED.getMessage());
        }
        AttributeValue rangeKey = null;
        if (tableInfo.hasRangeKey()) {
            rangeKey = key.get(tableInfo.getRangeKey().getAttributeName());
        }
        this.dbAccess.putRecord(tableName, record, key.get(tableInfo.getHashKey().getAttributeName()), rangeKey, false);
        if (returnVals == ReturnValue.ALL_OLD) {
            putResult.setAttributes(this.localDBOutputConverter.internalToExternalAttributes(oldItem));
        }
        putResult.withConsumedCapacity(this.computeWriteCapacity(tableName, new Mutation(oldItem, record), returnConsumedCapacity, this.transactionsMode));
    }

    void putItemNoCondition(String tableName, TableInfo tableInfo, ReturnValue returnVals, Map<String, AttributeValue> record, Map<String, AttributeValue> key, PutItemResult putResult, ReturnConsumedCapacity returnConsumedCapacity) {
        Map<String, AttributeValue> oldItem = this.dbAccess.getRecord(tableName, key);
        AttributeValue rangeKey = null;
        if (tableInfo.hasRangeKey()) {
            rangeKey = key.get(tableInfo.getRangeKey().getAttributeName());
        }
        this.dbAccess.putRecord(tableName, record, key.get(tableInfo.getHashKey().getAttributeName()), rangeKey, false);
        if (returnVals == ReturnValue.ALL_OLD) {
            putResult.setAttributes(this.localDBOutputConverter.internalToExternalAttributes(oldItem));
        }
        putResult.withConsumedCapacity(this.computeWriteCapacity(tableName, new Mutation(oldItem, record), returnConsumedCapacity, this.transactionsMode));
    }
}

