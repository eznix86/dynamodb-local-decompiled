/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  com.amazonaws.services.dynamodbv2.model.BatchGetItemRequest
 *  com.amazonaws.services.dynamodbv2.model.ComparisonOperator
 *  com.amazonaws.services.dynamodbv2.model.DeleteItemRequest
 *  com.amazonaws.services.dynamodbv2.model.GetItemRequest
 *  com.amazonaws.services.dynamodbv2.model.GlobalSecondaryIndexDescription
 *  com.amazonaws.services.dynamodbv2.model.KeySchemaElement
 *  com.amazonaws.services.dynamodbv2.model.KeysAndAttributes
 *  com.amazonaws.services.dynamodbv2.model.LocalSecondaryIndexDescription
 *  com.amazonaws.services.dynamodbv2.model.PutItemRequest
 *  com.amazonaws.services.dynamodbv2.model.QueryRequest
 *  com.amazonaws.services.dynamodbv2.model.ReturnValuesOnConditionCheckFailure
 *  com.amazonaws.services.dynamodbv2.model.ScanRequest
 *  com.amazonaws.services.dynamodbv2.model.UpdateItemRequest
 */
package com.amazonaws.services.dynamodbv2.local.shared.access;

import com.amazonaws.services.dynamodbv2.datamodel.DocPathElement;
import com.amazonaws.services.dynamodbv2.datamodel.UpdateExpression;
import com.amazonaws.services.dynamodbv2.datamodel.UpdateTreeNode;
import com.amazonaws.services.dynamodbv2.exceptions.AWSExceptionFactory;
import com.amazonaws.services.dynamodbv2.exceptions.AmazonServiceExceptionType;
import com.amazonaws.services.dynamodbv2.local.shared.access.DDBType;
import com.amazonaws.services.dynamodbv2.local.shared.access.LocalDBComparisonOperator;
import com.amazonaws.services.dynamodbv2.local.shared.access.LocalDBInputConverter;
import com.amazonaws.services.dynamodbv2.local.shared.access.LocalDBUtils;
import com.amazonaws.services.dynamodbv2.local.shared.access.TableInfo;
import com.amazonaws.services.dynamodbv2.local.shared.access.UnsignedByteArrayComparator;
import com.amazonaws.services.dynamodbv2.local.shared.exceptions.LocalDBClientExceptionMessage;
import com.amazonaws.services.dynamodbv2.local.shared.helpers.ExpressionUtils;
import com.amazonaws.services.dynamodbv2.local.shared.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.local.shared.validate.ErrorFactory;
import com.amazonaws.services.dynamodbv2.model.BatchGetItemRequest;
import com.amazonaws.services.dynamodbv2.model.ComparisonOperator;
import com.amazonaws.services.dynamodbv2.model.DeleteItemRequest;
import com.amazonaws.services.dynamodbv2.model.GetItemRequest;
import com.amazonaws.services.dynamodbv2.model.GlobalSecondaryIndexDescription;
import com.amazonaws.services.dynamodbv2.model.KeySchemaElement;
import com.amazonaws.services.dynamodbv2.model.KeysAndAttributes;
import com.amazonaws.services.dynamodbv2.model.LocalSecondaryIndexDescription;
import com.amazonaws.services.dynamodbv2.model.PutItemRequest;
import com.amazonaws.services.dynamodbv2.model.QueryRequest;
import com.amazonaws.services.dynamodbv2.model.ReturnValuesOnConditionCheckFailure;
import com.amazonaws.services.dynamodbv2.model.ScanRequest;
import com.amazonaws.services.dynamodbv2.model.UpdateItemRequest;
import com.amazonaws.services.dynamodbv2.rr.ExpressionsWrapperBase;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class LocalDBValidatorUtils {
    public static void validateArgumentsForComparisonOperator(ComparisonOperator op, List<AttributeValue> attributeValues, ErrorFactory errorFactory) {
        int numberOfAttributes;
        LocalDBComparisonOperator operation = LocalDBComparisonOperator.valueOf(op.toString());
        int n = numberOfAttributes = attributeValues == null ? 0 : attributeValues.size();
        if (!operation.isValidAttributeCount(numberOfAttributes)) {
            errorFactory.INVALID_PARAMETER_VALUE.throwAsException("Invalid number of argument(s) for the " + operation + " ComparisonOperator");
        }
        if (numberOfAttributes > 0) {
            DDBType firstType = null;
            for (AttributeValue val : attributeValues) {
                if (firstType == null) {
                    firstType = val.getType();
                }
                if (!operation.isTypeSupported(val.getType())) {
                    errorFactory.INVALID_PARAMETER_VALUE.throwAsException("ComparisonOperator " + operation + " is not valid for " + val.getType() + " AttributeValue type");
                }
                if (val.getType() == firstType) continue;
                errorFactory.INVALID_PARAMETER_VALUE.throwAsException("AttributeValues inside AttributeValueList must be of same type");
            }
        }
        if (operation == LocalDBComparisonOperator.BETWEEN && UnsignedByteArrayComparator.compareUnsignedByteArrays(attributeValues.get(0).getRawScalarValue(), attributeValues.get(1).getRawScalarValue()) > 0) {
            errorFactory.LBOUND_OF_BETWEEN_BIGGER_THAN_UBOUND.throwAsException();
        }
    }

    public static void validateExpressions(GetItemRequest input, LocalDBInputConverter validator) {
        String projectionExpression = input.getProjectionExpression();
        validator.validateMixingOldStyleWithExpressions(null, input.getAttributesToGet(), null, null, null, null, null, null, projectionExpression, null, null, null);
        validator.validateExpressionAttributeNamesUsedOnlyWithExpressions(projectionExpression, null, null, null, null, input.getExpressionAttributeNames());
    }

    public static void validateExpressions(PutItemRequest input, LocalDBInputConverter validator) {
        String conditionExpression = input.getConditionExpression();
        validator.validateMixingOldStyleWithExpressions(null, null, input.getExpected(), null, null, null, input.getConditionalOperator(), null, null, conditionExpression, null, null);
        validator.validateExpressionAttributeNamesUsedOnlyWithExpressions(null, conditionExpression, null, null, null, input.getExpressionAttributeNames());
        validator.validateExpressionAttributeValuesWithConditionExpression(conditionExpression, input.getExpressionAttributeValues());
    }

    public static void validateExpressions(DeleteItemRequest input, LocalDBInputConverter validator) {
        String conditionExpression = input.getConditionExpression();
        validator.validateMixingOldStyleWithExpressions(null, null, input.getExpected(), null, null, null, input.getConditionalOperator(), null, null, conditionExpression, null, null);
        validator.validateExpressionAttributeNamesUsedOnlyWithExpressions(null, conditionExpression, null, null, null, input.getExpressionAttributeNames());
        validator.validateExpressionAttributeValuesWithConditionExpression(conditionExpression, input.getExpressionAttributeValues());
    }

    public static void validateExpressions(UpdateItemRequest input, LocalDBInputConverter validator) {
        String updateExpression = input.getUpdateExpression();
        String conditionExpression = input.getConditionExpression();
        validator.validateMixingOldStyleWithExpressions(input.getAttributeUpdates(), null, input.getExpected(), null, null, null, input.getConditionalOperator(), updateExpression, null, conditionExpression, null, null);
        validator.validateExpressionAttributeNamesUsedOnlyWithExpressions(null, conditionExpression, null, updateExpression, null, input.getExpressionAttributeNames());
        validator.validateExpressionAttributeValuesWithUpdateExpressionOrConditionExpression(updateExpression, conditionExpression, input.getExpressionAttributeValues());
    }

    public static void validateExprAndAttrToGet(BatchGetItemRequest input, LocalDBInputConverter validator) {
        Map requestItems = input.getRequestItems();
        if (requestItems != null) {
            List attributesToGetAcrossTables = null;
            String projectionExpressionAcrossTables = null;
            for (String tableName : requestItems.keySet()) {
                KeysAndAttributes keysAndAttributes = (KeysAndAttributes)requestItems.get(tableName);
                if (keysAndAttributes == null) continue;
                if (keysAndAttributes.getAttributesToGet() != null) {
                    attributesToGetAcrossTables = keysAndAttributes.getAttributesToGet();
                }
                if (keysAndAttributes.getProjectionExpression() != null) {
                    projectionExpressionAcrossTables = keysAndAttributes.getProjectionExpression();
                }
                if (attributesToGetAcrossTables != null && projectionExpressionAcrossTables != null) {
                    validator.validateMixingOldStyleWithExpressions(null, attributesToGetAcrossTables, null, null, null, null, null, null, projectionExpressionAcrossTables, null, null, null);
                }
                validator.validateExpressionAttributeNamesUsedOnlyWithExpressions(keysAndAttributes.getProjectionExpression(), null, null, null, null, keysAndAttributes.getExpressionAttributeNames());
            }
        }
    }

    public static void validateExpressions(ScanRequest input, LocalDBInputConverter validator) {
        String projectionExpression = input.getProjectionExpression();
        String filterExpression = input.getFilterExpression();
        validator.validateMixingOldStyleWithExpressions(null, input.getAttributesToGet(), null, null, input.getScanFilter(), null, input.getConditionalOperator(), null, projectionExpression, null, filterExpression, null);
        validator.validateExpressionAttributeNamesUsedOnlyWithExpressions(projectionExpression, null, filterExpression, null, null, input.getExpressionAttributeNames());
        validator.validateExpressionAttributeValuesWithFilterExpression(filterExpression, input.getExpressionAttributeValues());
    }

    public static void validateExpressions(QueryRequest input, LocalDBInputConverter validator) {
        validator.validateMixingOldStyleWithExpressions(null, input.getAttributesToGet(), null, input.getQueryFilter(), null, input.getKeyConditions(), input.getConditionalOperator(), null, input.getProjectionExpression(), null, input.getFilterExpression(), input.getKeyConditionExpression());
        validator.validateExpressionAttributeNamesUsedOnlyWithExpressions(input.getProjectionExpression(), null, input.getFilterExpression(), null, input.getKeyConditionExpression(), input.getExpressionAttributeNames());
        validator.validateExpressionAttributeValuesWithFilterOrKeyConditionExpression(input.getFilterExpression(), input.getKeyConditionExpression(), input.getExpressionAttributeValues());
    }

    public static int validateNestedLevel(int level) {
        if (!LocalDBValidatorUtils.isValidNestedLevel(level)) {
            new AWSExceptionFactory().ITEM_NESTING_LEVELS_LIMIT_EXCEEDED.throwAsException();
        }
        return level;
    }

    private static boolean isValidNestedLevel(int level) {
        return level >= 0 && level < 32;
    }

    public static void validateNoNestedAccessToKeyAttributeInExpression(TableInfo tableInfo, ExpressionsWrapperBase expressionWrapper, ErrorFactory errorFactory) {
        if (expressionWrapper == null) {
            return;
        }
        Set<String> topLevelFieldsWithNestedAccess = expressionWrapper.getTopLevelFieldsWithNestedAccess();
        LocalDBValidatorUtils.validateNoNestedAccessToKeyAttributeInExpression(tableInfo.getKeySchema(), topLevelFieldsWithNestedAccess, errorFactory, false);
        if (tableInfo.getLSIIndexes() != null) {
            for (LocalSecondaryIndexDescription localSecondaryIndexDescription : tableInfo.getLSIDescriptions()) {
                LocalDBValidatorUtils.validateNoNestedAccessToKeyAttributeInExpression(localSecondaryIndexDescription.getKeySchema(), topLevelFieldsWithNestedAccess, errorFactory, true);
            }
        }
        if (tableInfo.hasGSIs()) {
            for (GlobalSecondaryIndexDescription globalSecondaryIndexDescription : tableInfo.getGSIDescriptions()) {
                LocalDBValidatorUtils.validateNoNestedAccessToKeyAttributeInExpression(globalSecondaryIndexDescription.getKeySchema(), topLevelFieldsWithNestedAccess, errorFactory, true);
            }
        }
    }

    private static void validateNoNestedAccessToKeyAttributeInExpression(List<KeySchemaElement> keySchema, Set<String> topLevelFieldsWithNestedAccess, ErrorFactory errorFactory, boolean isIndexTable) {
        for (KeySchemaElement keySchemaElement : keySchema) {
            String key = keySchemaElement.getAttributeName();
            if (!topLevelFieldsWithNestedAccess.contains(key)) continue;
            errorFactory.NESTED_ATTRIBUTE_ACCESS_TO_KEY_ATTRIBUTE_IN_EXPRESSION.throwAsException(isIndexTable ? "IndexKey: " + key : "Key: " + key);
        }
    }

    public static Integer validateDescribeStreamLimit(Integer limit2, ErrorFactory errorFactory) {
        if (limit2 == null) {
            return LocalDBUtils.MAX_DESCRIBE_STREAM_LIMIT;
        }
        if (limit2 < 1) {
            throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, "1 validation error detected: Value '" + limit2 + "' at 'limit' failed to satisfy constraint: Member must have value greater than or equal to 1");
        }
        if (limit2 > LocalDBUtils.MAX_DESCRIBE_STREAM_LIMIT) {
            throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, String.format("One or more parameter values were invalid: Limit cannot be greater than the maximum: %d", LocalDBUtils.MAX_DESCRIBE_STREAM_LIMIT));
        }
        return limit2;
    }

    public static int validateLimitValueListStreams(Integer limit2, ErrorFactory errorFactory) {
        if (limit2 == null) {
            return 100;
        }
        if (limit2 < 1) {
            throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, "1 validation error detected: Value '" + limit2 + "' at 'limit' failed to satisfy constraint: Member must have value greater than or equal to 1");
        }
        if (limit2 > 100) {
            throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, "One or more parameter values were invalid: Limit cannot be greater than the maximum allowed size: 100");
        }
        return limit2;
    }

    public static void validateLimitValueExecuteStatement(Integer limit2, ErrorFactory errorFactory) {
        if (limit2 < 1) {
            throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, "1 validation error detected: Value '" + limit2 + "' at 'limit' failed to satisfy constraint: Member must have value greater than or equal to 1");
        }
    }

    public static String validateReturnValuesOnConditionCheckFailure(String returnValuesOnConditionCheckFailure) {
        try {
            if (returnValuesOnConditionCheckFailure != null) {
                ReturnValuesOnConditionCheckFailure.fromValue((String)returnValuesOnConditionCheckFailure);
            }
            return returnValuesOnConditionCheckFailure;
        } catch (IllegalArgumentException ex) {
            throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, "1 validation error detected: Value '" + returnValuesOnConditionCheckFailure + "' at 'returnValuesOnConditionCheckFailure' failed to satisfy constraint: Member must satisfy enum value set: [ALL_OLD, NONE]");
        }
    }

    public static void validateThatKeyAttributesNotUpdated(TableInfo tableInfo, UpdateExpression updateExpression, ErrorFactory errorFactory) {
        UpdateTreeNode treeRoot;
        if (updateExpression != null && (treeRoot = updateExpression.getTreeRoot()) != null) {
            Set<DocPathElement> topLevelDocPaths = treeRoot.getChildMap().keySet();
            Set<String> attrNames = ExpressionUtils.docPathElementsToAttrNames(topLevelDocPaths);
            for (KeySchemaElement keySchemaElement : tableInfo.getKeySchema()) {
                String key = keySchemaElement.getAttributeName();
                if (!attrNames.contains(key)) continue;
                throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, String.format(LocalDBClientExceptionMessage.INVALID_PARAMETER_VALUE.getMessage(), LocalDBClientExceptionMessage.CANNOT_UPDATE_ATTRIBUTE.getMessage() + key + ". " + errorFactory.ATTRIBUTE_PART_OF_KEY.getMessage()));
            }
        }
    }
}

