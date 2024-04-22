/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  com.amazonaws.services.dynamodbv2.model.AttributeDefinition
 *  com.amazonaws.services.dynamodbv2.model.ComparisonOperator
 *  com.amazonaws.services.dynamodbv2.model.ConditionalOperator
 *  com.amazonaws.services.dynamodbv2.model.Projection
 *  com.amazonaws.services.dynamodbv2.model.ProjectionType
 *  com.amazonaws.services.dynamodbv2.model.ReturnConsumedCapacity
 *  com.amazonaws.services.dynamodbv2.model.ReturnValue
 */
package com.amazonaws.services.dynamodbv2.local.shared.access.api.dp;

import com.amazonaws.services.dynamodbv2.datamodel.Expression;
import com.amazonaws.services.dynamodbv2.datamodel.ProjectionExpression;
import com.amazonaws.services.dynamodbv2.dbenv.DbEnv;
import com.amazonaws.services.dynamodbv2.exceptions.AWSExceptionFactory;
import com.amazonaws.services.dynamodbv2.exceptions.AmazonServiceExceptionType;
import com.amazonaws.services.dynamodbv2.local.google.Sets;
import com.amazonaws.services.dynamodbv2.local.shared.access.DDBType;
import com.amazonaws.services.dynamodbv2.local.shared.access.ExceptionHandler;
import com.amazonaws.services.dynamodbv2.local.shared.access.LocalDBAccess;
import com.amazonaws.services.dynamodbv2.local.shared.access.LocalDBComparisonOperator;
import com.amazonaws.services.dynamodbv2.local.shared.access.LocalDBInputConverter;
import com.amazonaws.services.dynamodbv2.local.shared.access.LocalDBOutputConverter;
import com.amazonaws.services.dynamodbv2.local.shared.access.LocalDBUtils;
import com.amazonaws.services.dynamodbv2.local.shared.access.TableInfo;
import com.amazonaws.services.dynamodbv2.local.shared.access.api.DynamoDbApiFunction;
import com.amazonaws.services.dynamodbv2.local.shared.dataaccess.LocalDocumentFactory;
import com.amazonaws.services.dynamodbv2.local.shared.exceptions.ExpressionExecutionException;
import com.amazonaws.services.dynamodbv2.local.shared.exceptions.LocalDBClientExceptionMessage;
import com.amazonaws.services.dynamodbv2.local.shared.exceptions.LocalDBClientExceptionType;
import com.amazonaws.services.dynamodbv2.local.shared.helpers.ExpressionUtils;
import com.amazonaws.services.dynamodbv2.local.shared.helpers.TransactionsEnabledMode;
import com.amazonaws.services.dynamodbv2.local.shared.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.local.shared.model.Condition;
import com.amazonaws.services.dynamodbv2.local.shared.model.ExpectedAttributeValue;
import com.amazonaws.services.dynamodbv2.model.AttributeDefinition;
import com.amazonaws.services.dynamodbv2.model.ComparisonOperator;
import com.amazonaws.services.dynamodbv2.model.ConditionalOperator;
import com.amazonaws.services.dynamodbv2.model.Projection;
import com.amazonaws.services.dynamodbv2.model.ProjectionType;
import com.amazonaws.services.dynamodbv2.model.ReturnConsumedCapacity;
import com.amazonaws.services.dynamodbv2.model.ReturnValue;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.commons.lang3.StringUtils;

abstract class DataPlaneFunction<I, O>
extends DynamoDbApiFunction<I, O> {
    protected static final Set<ReturnConsumedCapacity> COMPUTE_CONSUMED_CAPACITY_TYPES = Sets.newHashSet(ReturnConsumedCapacity.TOTAL, ReturnConsumedCapacity.INDEXES);
    protected static final double TRANSACTION_CAPACITY_FACTOR = 2.0;
    public final LocalDBInputConverter inputConverter;
    public final LocalDBOutputConverter localDBOutputConverter;
    public final AWSExceptionFactory awsExceptionFactory;
    protected final LocalDBAccess dbAccess;
    protected final DbEnv localDBEnv;
    protected final TransactionsEnabledMode transactionsMode;

    DataPlaneFunction(LocalDBAccess dbAccess, DbEnv localDBEnv, LocalDBInputConverter inputConverter, LocalDBOutputConverter localDBOutputConverter, AWSExceptionFactory awsExceptionFactory, TransactionsEnabledMode transactionsMode) {
        super(dbAccess);
        this.dbAccess = dbAccess;
        this.localDBEnv = localDBEnv;
        this.inputConverter = inputConverter;
        this.localDBOutputConverter = localDBOutputConverter;
        this.awsExceptionFactory = awsExceptionFactory;
        this.transactionsMode = transactionsMode;
    }

    public List<String> getAttributeNames(List<AttributeDefinition> attrsDefs) {
        ArrayList<String> attributes = new ArrayList<String>();
        for (AttributeDefinition attrDef : attrsDefs) {
            attributes.add(attrDef.getAttributeName());
        }
        return attributes;
    }

    public List<AttributeDefinition> getKeyAttributes(TableInfo tableInfo, String indexName) {
        ArrayList<AttributeDefinition> attributesToGet = new ArrayList<AttributeDefinition>();
        HashSet<String> attrs = new HashSet<String>();
        attributesToGet.add(tableInfo.getHashKey());
        attrs.add(tableInfo.getHashKey().getAttributeName());
        if (tableInfo.hasRangeKey()) {
            attributesToGet.add(tableInfo.getRangeKey());
            attrs.add(tableInfo.getRangeKey().getAttributeName());
        }
        if (indexName != null) {
            if (tableInfo.isLSIIndex(indexName)) {
                String indexRangeKeyName = tableInfo.getLSIRangeKey(indexName).getAttributeName();
                if (!attrs.contains(indexRangeKeyName)) {
                    attributesToGet.add(tableInfo.getLSIRangeKey(indexName));
                }
            } else {
                String gsiRangeKey;
                String gsiHashKey = tableInfo.getGSIHashKey(indexName).getAttributeName();
                if (!attrs.contains(gsiHashKey)) {
                    attributesToGet.add(tableInfo.getGSIHashKey(indexName));
                }
                if (tableInfo.getGSIRangeKey(indexName) != null && !attrs.contains(gsiRangeKey = tableInfo.getGSIRangeKey(indexName).getAttributeName())) {
                    attributesToGet.add(tableInfo.getGSIRangeKey(indexName));
                }
            }
        }
        return attributesToGet;
    }

    public void validateGetKey(Map<String, AttributeValue> conditions, TableInfo tableInfo) {
        if (conditions == null) {
            throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, LocalDBClientExceptionMessage.MISSING_KEY.getMessage());
        }
        int conditionsSize = conditions.size();
        if (conditionsSize > 2 || conditionsSize == 0 || conditionsSize == 2 && !tableInfo.hasRangeKey() || conditionsSize == 1 && tableInfo.hasRangeKey()) {
            throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, LocalDBClientExceptionMessage.INCONSISTENT_GET_CONDITION_SIZE.getMessage());
        }
        this.validateKeyValue(conditions, tableInfo.getHashKey(), true, 2048L);
        if (tableInfo.hasRangeKey()) {
            this.validateKeyValue(conditions, tableInfo.getRangeKey(), true, 1024L);
        }
    }

    void validateKeyValue(Map<String, AttributeValue> item, AttributeDefinition keyDef, boolean essential, Long maxSize) {
        this.validateKeyValue(item, keyDef, essential, maxSize, false);
    }

    void validateKeyValue(Map<String, AttributeValue> item, AttributeDefinition keyDef, boolean essential, Long maxSize, boolean isIndex) {
        this.validateKeyValue(item, keyDef, essential, maxSize, isIndex, null);
    }

    void checkForEmptyAttributeValueInIndexKeysAfterUpdate(Map<String, AttributeValue> item, AttributeDefinition keyDef, String indexName, boolean usingUpdateExpression) {
        String keyName = keyDef.getAttributeName();
        AttributeValue val = item.get(keyName);
        if (val == null) {
            return;
        }
        DDBType valueType = LocalDBUtils.getDataTypeOfScalarAttributeValue(val);
        if (valueType == DDBType.B) {
            if (!val.getB().hasRemaining()) {
                if (usingUpdateExpression) {
                    throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, LocalDBClientExceptionMessage.UPD_EXPR_INDEX_EMPTY_BINARY_KEY_VALUE_UNSUPPORTED.getMessage());
                }
                String additionalMessage = this.createAdditionalMessageForEmptyAttributeValue(keyName, true, indexName);
                throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, LocalDBClientExceptionMessage.INDEX_EMPTY_BINARY_KEY_VALUE_UNSUPPORTED.getMessage() + additionalMessage);
            }
        } else if (valueType == DDBType.S && val.getS().isEmpty()) {
            if (usingUpdateExpression) {
                throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, LocalDBClientExceptionMessage.UPD_EXPR_INDEX_EMPTY_STRING_KEY_VALUE_UNSUPPORTED.getMessage());
            }
            String additionalMessage = this.createAdditionalMessageForEmptyAttributeValue(keyName, true, indexName);
            throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, LocalDBClientExceptionMessage.INDEX_EMPTY_STR_KEY_VALUE_UNSUPPORTED.getMessage() + additionalMessage);
        }
    }

    void validateKeyValue(Map<String, AttributeValue> item, AttributeDefinition keyDef, boolean essential, Long maxSize, boolean isIndex, String indexName) {
        String keyName = keyDef.getAttributeName();
        AttributeValue val = item.get(keyName);
        DDBType expectedType = LocalDBUtils.getDataTypesOfAttributeDefinitions(Collections.singletonList(keyDef), true).get(0);
        if (val == null) {
            if (essential) {
                throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, LocalDBClientExceptionMessage.NO_SPECIFED_KEY_VALUE.getMessage());
            }
            return;
        }
        DDBType valueType = LocalDBUtils.getDataTypeOfScalarAttributeValue(val);
        if (valueType != expectedType) {
            if (isIndex) {
                throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, LocalDBClientExceptionMessage.INCONSISTENT_INDEX_KEY_TYPES.getMessage());
            }
            throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, LocalDBClientExceptionMessage.INCONSISTENT_PRIMARY_KEY_TYPES.getMessage());
        }
        if (valueType == DDBType.N) {
            item.put(keyName, new AttributeValue().withN(LocalDBUtils.validateNumericValue(val.getN()).toPlainString()));
        } else if (valueType == DDBType.NS) {
            item.put(keyName, new AttributeValue().withNS(LocalDBUtils.validateNumberSet(val.getNS())));
        } else if (valueType == DDBType.B) {
            this.validateKeyForEmptyBinaryValue(val, keyName, isIndex, indexName, null);
        } else if (valueType == DDBType.S) {
            this.validateKeyForEmptyStringValue(val, keyName, isIndex, indexName, null);
        }
        if (maxSize != null && LocalDBUtils.getAttributeValueSizeBytes(val) > maxSize) {
            if (isIndex) {
                throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, String.format(LocalDBClientExceptionMessage.INVALID_PARAMETER_VALUE.getMessage(), "Size limit exceeded"));
            }
            throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, LocalDBClientExceptionMessage.KEY_VALUE_TOO_BIG.getMessage());
        }
    }

    public void validateKeyForEmptyStringValue(AttributeValue attributeValue, String keyAttributeName, boolean isIndex, String indexName, String errorMessagePrefix) {
        if (attributeValue.getS().isEmpty()) {
            String additionalMessage = this.createAdditionalMessageForEmptyAttributeValue(keyAttributeName, isIndex, indexName);
            if (isIndex) {
                String errorMessage = LocalDBClientExceptionMessage.INDEX_EMPTY_STR_KEY_VALUE_UNSUPPORTED.getMessage() + additionalMessage;
                if (errorMessagePrefix != null) {
                    errorMessage = errorMessagePrefix + errorMessage;
                }
                throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, errorMessage);
            }
            String errorMessage = LocalDBClientExceptionMessage.EMPTY_KEY_ATTRIBUTE_VALUE_STRING.getMessage() + additionalMessage;
            if (errorMessagePrefix != null) {
                errorMessage = errorMessagePrefix + errorMessage;
            }
            throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, errorMessage);
        }
    }

    public void validateKeyForEmptyBinaryValue(AttributeValue attributeValue, String keyAttributeName, boolean isIndex, String indexName, String errorMessagePrefix) {
        if (!attributeValue.getB().hasRemaining()) {
            String additionalMessage = this.createAdditionalMessageForEmptyAttributeValue(keyAttributeName, isIndex, indexName);
            if (isIndex) {
                String errorMessage = LocalDBClientExceptionMessage.INDEX_EMPTY_BINARY_KEY_VALUE_UNSUPPORTED.getMessage() + additionalMessage;
                if (errorMessagePrefix != null) {
                    errorMessage = errorMessagePrefix + errorMessage;
                }
                throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, errorMessage);
            }
            String errorMessage = LocalDBClientExceptionMessage.EMPTY_KEY_ATTRIBUTE_VALUE_BINARY.getMessage() + additionalMessage;
            if (errorMessagePrefix != null) {
                errorMessage = errorMessagePrefix + errorMessage;
            }
            throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, errorMessage);
        }
    }

    private String createAdditionalMessageForEmptyAttributeValue(String keyAttributeName, boolean isIndex, String indexName) {
        StringBuilder additionalMessage = new StringBuilder();
        if (isIndex) {
            if (indexName != null && !indexName.isEmpty()) {
                additionalMessage.append(" IndexName: ");
                additionalMessage.append(indexName);
                additionalMessage.append(",");
            }
            additionalMessage.append(" IndexKey: ");
            additionalMessage.append(keyAttributeName);
        } else {
            additionalMessage.append(" Key: ");
            additionalMessage.append(keyAttributeName);
        }
        return additionalMessage.toString();
    }

    public ReturnValue validateReturnType(String returnValsString, boolean update) {
        ReturnValue returnType;
        if (returnValsString == null) {
            return ReturnValue.NONE;
        }
        try {
            returnType = ReturnValue.fromValue((String)returnValsString);
        } catch (IllegalArgumentException illegalArgs) {
            throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, LocalDBClientExceptionMessage.INVALID_RETURN_VALUES_TYPE.getMessage());
        }
        if (!(update || returnType != ReturnValue.ALL_NEW && returnType != ReturnValue.UPDATED_NEW && returnType != ReturnValue.UPDATED_OLD)) {
            throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, LocalDBClientExceptionMessage.INVALID_RETURN_VALUES_TYPE.getMessage());
        }
        return returnType;
    }

    Map<String, Condition> convertToConditions(Map<String, ExpectedAttributeValue> expected) {
        HashMap<String, Condition> conditions = new HashMap<String, Condition>();
        for (Map.Entry<String, ExpectedAttributeValue> entry : expected.entrySet()) {
            String attrName = entry.getKey();
            ExpectedAttributeValue expectedAttributeValue = entry.getValue();
            Condition condition = new Condition().withAttributeValueList(this.getExpectedAttributeValueList(expectedAttributeValue)).withComparisonOperator(this.getExpectedComparisonOperator(expectedAttributeValue));
            conditions.put(attrName, condition);
        }
        return conditions;
    }

    void validateConditions(Map<String, Condition> conditions, String conditionalOperatorAsString) {
        if (conditionalOperatorAsString != null && this.isValidConditionalOperator(conditionalOperatorAsString)) {
            if (conditions.isEmpty()) {
                throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, LocalDBClientExceptionMessage.COND_OP_WITHOUT_FILTER_OR_EXPECTED.getMessage());
            }
            if (conditions.size() == 1) {
                throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, LocalDBClientExceptionMessage.COND_OP_WITH_ONE_ELEMENT.getMessage());
            }
        }
        if (conditions == null) {
            return;
        }
        for (Map.Entry<String, Condition> entry : conditions.entrySet()) {
            Condition requestCondition = entry.getValue();
            ComparisonOperator comparisonOperator = this.validateConditionType(requestCondition);
            List<AttributeValue> expectedVals = requestCondition.getAttributeValueList();
            LocalDBComparisonOperator localOp = LocalDBComparisonOperator.fromValue(comparisonOperator);
            localOp.isValidAttributeList(expectedVals);
        }
    }

    void validateAttributesToGet(List<String> attributesToGet) {
        this.validateAttributesToGetAndProjExpr(attributesToGet, null, null, null);
    }

    public ConditionalOperator conditionalOperatorFrom(String conditionalOperator) {
        return conditionalOperator == null ? ConditionalOperator.AND : ConditionalOperator.fromValue((String)conditionalOperator);
    }

    ComparisonOperator validateConditionType(Condition requestCondition) {
        try {
            return ComparisonOperator.fromValue((String)requestCondition.getComparisonOperator());
        } catch (IllegalArgumentException ie) {
            throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, LocalDBClientExceptionMessage.INVALID_COMPARISON.getMessage());
        }
    }

    public void validateAttributesToGetAndProjExpr(List<String> attributesToGet, ProjectionExpression projectionExpression, String indexName, TableInfo tableInfo) {
        if (attributesToGet != null && attributesToGet.size() == 0) {
            throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, LocalDBClientExceptionMessage.ATTRIBUTES_EMPTY.getMessage());
        }
        Set<Object> attrsToFetch = new HashSet();
        if (projectionExpression != null) {
            attrsToFetch = ExpressionUtils.getProjectionExpressionTopLevelAttributes(projectionExpression, this.localDBEnv);
        } else {
            if (attributesToGet == null) {
                return;
            }
            for (String attr : attributesToGet) {
                if (StringUtils.isEmpty(attr)) {
                    throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, LocalDBClientExceptionMessage.EMPTY_ATTRIBUTE_NAME.getMessage());
                }
                if (attrsToFetch.contains(attr)) {
                    throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, LocalDBClientExceptionMessage.IDENTICAL_ATTRIBUTE_NAMES.getMessage());
                }
                attrsToFetch.add(attr);
            }
        }
        if (indexName != null && tableInfo.isGSIIndex(indexName) && !tableInfo.getProjection(indexName).getProjectionType().equals(ProjectionType.ALL.name())) {
            Projection gsiProj = tableInfo.getProjection(indexName);
            List nonKeyAttributes = gsiProj.getNonKeyAttributes();
            ArrayList<String> keyAndNonKeyAttrs = new ArrayList<String>(this.getAttributeNames(this.getKeyAttributes(tableInfo, indexName)));
            if (nonKeyAttributes != null) {
                keyAndNonKeyAttrs.addAll(nonKeyAttributes);
            }
            if (!keyAndNonKeyAttrs.containsAll(attrsToFetch)) {
                attrsToFetch.removeAll(keyAndNonKeyAttrs);
                throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, String.format(LocalDBClientExceptionMessage.INVALID_PARAMETER_VALUE.getMessage(), "Global secondary index " + indexName + " does not project " + attrsToFetch));
            }
        }
    }

    boolean doesItemMatchConditionalOperator(Map<String, AttributeValue> item, Map<String, Condition> conditions, ConditionalOperator conditionalOperator) {
        if (conditions != null && conditions.size() > 0) {
            switch (conditionalOperator) {
                case AND: {
                    return this.checkANDConditions(item, conditions);
                }
                case OR: {
                    return this.checkORConditions(item, conditions);
                }
            }
            LocalDBUtils.ldClientFail(LocalDBClientExceptionType.UNREACHABLE_CODE);
            return false;
        }
        return true;
    }

    protected ReturnConsumedCapacity convertReturnConsumedCapacity(String returnConsumedCapacityString) {
        ReturnConsumedCapacity returnConsumedCapacity;
        if (returnConsumedCapacityString != null && !returnConsumedCapacityString.isEmpty()) {
            try {
                returnConsumedCapacity = ReturnConsumedCapacity.fromValue((String)returnConsumedCapacityString.toUpperCase());
            } catch (IllegalArgumentException e) {
                throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, LocalDBClientExceptionMessage.MISSING_KEY.getMessage());
            }
        } else {
            returnConsumedCapacity = ReturnConsumedCapacity.NONE;
        }
        return returnConsumedCapacity;
    }

    private boolean doesItemMatchCondition(Map<String, AttributeValue> item, String attributeName, Condition condition) {
        AttributeValue actualValue = item == null ? null : item.get(attributeName);
        return LocalDBComparisonOperator.fromValue(condition.getComparisonOperator()).evaluate(condition.getAttributeValueList(), actualValue);
    }

    private boolean checkANDConditions(Map<String, AttributeValue> item, Map<String, Condition> conditions) {
        for (Map.Entry<String, Condition> entry : conditions.entrySet()) {
            if (this.doesItemMatchCondition(item, entry.getKey(), entry.getValue())) continue;
            return false;
        }
        return true;
    }

    private boolean checkORConditions(Map<String, AttributeValue> item, Map<String, Condition> conditions) {
        for (Map.Entry<String, Condition> entry : conditions.entrySet()) {
            if (!this.doesItemMatchCondition(item, entry.getKey(), entry.getValue())) continue;
            return true;
        }
        return false;
    }

    private boolean isValidConditionalOperator(String conditionalOperatorAsString) {
        try {
            ConditionalOperator.fromValue((String)conditionalOperatorAsString);
        } catch (IllegalArgumentException e) {
            return false;
        }
        return true;
    }

    private List<AttributeValue> getExpectedAttributeValueList(ExpectedAttributeValue expected) {
        return expected.getValue() != null ? Collections.singletonList(expected.getValue()) : expected.getAttributeValueList();
    }

    private ComparisonOperator getExpectedComparisonOperator(ExpectedAttributeValue expected) {
        if (expected.getComparisonOperator() != null) {
            return ComparisonOperator.fromValue((String)expected.getComparisonOperator());
        }
        Boolean exists = expected.getExists();
        if (!Boolean.FALSE.equals(exists)) {
            return ComparisonOperator.EQ;
        }
        return ComparisonOperator.NULL;
    }

    public void validateExclusiveStartKey(Map<String, AttributeValue> exclusiveStartKey, List<AttributeDefinition> keyDefs) {
        if (exclusiveStartKey == null) {
            return;
        }
        if (exclusiveStartKey.size() != keyDefs.size()) {
            throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, LocalDBClientExceptionMessage.INVALID_EXCLUSIVE_START_SIZE.getMessage());
        }
        for (AttributeDefinition keyDef : keyDefs) {
            String keyName = keyDef.getAttributeName();
            AttributeValue keyVal = exclusiveStartKey.get(keyName);
            if (keyVal == null) {
                throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, LocalDBClientExceptionMessage.INVALID_EXCLUSIVE_START.getMessage());
            }
            DDBType keyType = LocalDBUtils.validateConsistentTypes(keyDef, keyVal, LocalDBClientExceptionMessage.INCONSISTENT_TYPES);
            if (keyType != DDBType.N) continue;
            exclusiveStartKey.put(keyName, new AttributeValue().withN(LocalDBUtils.validateNumericValue(keyVal.getN()).toPlainString()));
        }
    }

    public void validateExclusiveStartKeyForEmptyAttributeValue(Map<String, AttributeValue> exclusiveStartKey, TableInfo tableInfo, String indexName, boolean isGsiIndex) {
        if (exclusiveStartKey == null) {
            return;
        }
        ArrayList<AttributeDefinition> tableKeys = new ArrayList<AttributeDefinition>();
        tableKeys.add(tableInfo.getHashKey());
        if (tableInfo.hasRangeKey()) {
            tableKeys.add(tableInfo.getRangeKey());
        }
        this.validateExclusiveStartKeyForEmptyAttributeValue(exclusiveStartKey, tableKeys, false, null);
        if (indexName != null) {
            ArrayList<AttributeDefinition> indexKeys = new ArrayList<AttributeDefinition>();
            if (isGsiIndex) {
                indexKeys.add(tableInfo.getGSIHashKey(indexName));
                if (tableInfo.getGSIRangeKey(indexName) != null) {
                    indexKeys.add(tableInfo.getGSIRangeKey(indexName));
                }
            } else {
                indexKeys.add(tableInfo.getHashKey());
                indexKeys.add(tableInfo.getLSIRangeKey(indexName));
            }
            this.validateExclusiveStartKeyForEmptyAttributeValue(exclusiveStartKey, indexKeys, true, indexName);
        }
    }

    private void validateExclusiveStartKeyForEmptyAttributeValue(Map<String, AttributeValue> exclusiveStartKey, List<AttributeDefinition> keyDefinitions, boolean isIndex, String indexName) {
        for (int i = 0; i < keyDefinitions.size(); ++i) {
            AttributeValue attributeValue;
            AttributeDefinition keyDefinition = keyDefinitions.get(i);
            StringBuilder errorMessagePrefix = new StringBuilder();
            if (i == 0) {
                errorMessagePrefix.append(LocalDBClientExceptionMessage.EMPTY_HASH_EXCLUSIVE_START.getMessage()).append(" ");
            }
            errorMessagePrefix.append(LocalDBClientExceptionMessage.INVALID_EXCLUSIVE_START.getMessage()).append(": ");
            if ("S".equals(keyDefinition.getAttributeType())) {
                attributeValue = exclusiveStartKey.get(keyDefinition.getAttributeName());
                this.validateKeyForEmptyStringValue(attributeValue, keyDefinition.getAttributeName(), isIndex, indexName, errorMessagePrefix.toString());
                continue;
            }
            if (!"B".equals(keyDefinition.getAttributeType())) continue;
            attributeValue = exclusiveStartKey.get(keyDefinition.getAttributeName());
            this.validateKeyForEmptyBinaryValue(attributeValue, keyDefinition.getAttributeName(), isIndex, indexName, errorMessagePrefix.toString());
        }
    }

    public boolean doesItemMatchFilterExpression(Map<String, AttributeValue> item, Expression filterExpression) {
        if (filterExpression == null) {
            return true;
        }
        boolean itemMatchesFilter = false;
        try {
            itemMatchesFilter = LocalDBUtils.doesItemMatchCondition(item, filterExpression, this.localDBEnv, new LocalDocumentFactory());
        } catch (ExpressionExecutionException eee) {
            ExceptionHandler.processFilterExpressionExecutionException(eee, this.awsExceptionFactory);
        }
        return itemMatchesFilter;
    }
}

