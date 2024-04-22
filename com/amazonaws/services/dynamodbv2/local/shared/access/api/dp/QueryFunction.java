/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  com.amazonaws.services.dynamodbv2.model.AttributeDefinition
 *  com.amazonaws.services.dynamodbv2.model.Capacity
 *  com.amazonaws.services.dynamodbv2.model.ComparisonOperator
 *  com.amazonaws.services.dynamodbv2.model.ConditionalOperator
 *  com.amazonaws.services.dynamodbv2.model.ConsumedCapacity
 *  com.amazonaws.services.dynamodbv2.model.KeySchemaElement
 *  com.amazonaws.services.dynamodbv2.model.Projection
 *  com.amazonaws.services.dynamodbv2.model.ProjectionType
 *  com.amazonaws.services.dynamodbv2.model.QueryRequest
 *  com.amazonaws.services.dynamodbv2.model.QueryResult
 *  com.amazonaws.services.dynamodbv2.model.ReturnConsumedCapacity
 *  com.amazonaws.services.dynamodbv2.model.Select
 *  com.amazonaws.util.CollectionUtils
 */
package com.amazonaws.services.dynamodbv2.local.shared.access.api.dp;

import com.amazonaws.services.dynamodbv2.datamodel.DocumentFactory;
import com.amazonaws.services.dynamodbv2.datamodel.Expression;
import com.amazonaws.services.dynamodbv2.datamodel.ProjectionExpression;
import com.amazonaws.services.dynamodbv2.dbenv.DbEnv;
import com.amazonaws.services.dynamodbv2.exceptions.AWSExceptionFactory;
import com.amazonaws.services.dynamodbv2.exceptions.AmazonServiceExceptionType;
import com.amazonaws.services.dynamodbv2.local.shared.access.DDBType;
import com.amazonaws.services.dynamodbv2.local.shared.access.LocalDBAccess;
import com.amazonaws.services.dynamodbv2.local.shared.access.LocalDBComparisonOperator;
import com.amazonaws.services.dynamodbv2.local.shared.access.LocalDBInputConverter;
import com.amazonaws.services.dynamodbv2.local.shared.access.LocalDBOutputConverter;
import com.amazonaws.services.dynamodbv2.local.shared.access.LocalDBUtils;
import com.amazonaws.services.dynamodbv2.local.shared.access.LocalDBValidatorUtils;
import com.amazonaws.services.dynamodbv2.local.shared.access.QueryResultInfo;
import com.amazonaws.services.dynamodbv2.local.shared.access.TableInfo;
import com.amazonaws.services.dynamodbv2.local.shared.access.api.dp.PaginatingFunction;
import com.amazonaws.services.dynamodbv2.local.shared.exceptions.LocalDBClientExceptionMessage;
import com.amazonaws.services.dynamodbv2.local.shared.helpers.ConsumedCapacityUtils;
import com.amazonaws.services.dynamodbv2.local.shared.helpers.ExpressionUtils;
import com.amazonaws.services.dynamodbv2.local.shared.helpers.TransactionsEnabledMode;
import com.amazonaws.services.dynamodbv2.local.shared.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.local.shared.model.Condition;
import com.amazonaws.services.dynamodbv2.local.shared.validate.RangeQueryExpressionsWrapper;
import com.amazonaws.services.dynamodbv2.model.AttributeDefinition;
import com.amazonaws.services.dynamodbv2.model.Capacity;
import com.amazonaws.services.dynamodbv2.model.ComparisonOperator;
import com.amazonaws.services.dynamodbv2.model.ConditionalOperator;
import com.amazonaws.services.dynamodbv2.model.ConsumedCapacity;
import com.amazonaws.services.dynamodbv2.model.KeySchemaElement;
import com.amazonaws.services.dynamodbv2.model.Projection;
import com.amazonaws.services.dynamodbv2.model.ProjectionType;
import com.amazonaws.services.dynamodbv2.model.QueryRequest;
import com.amazonaws.services.dynamodbv2.model.QueryResult;
import com.amazonaws.services.dynamodbv2.model.ReturnConsumedCapacity;
import com.amazonaws.services.dynamodbv2.model.Select;
import com.amazonaws.services.dynamodbv2.rr.ExpressionWrapper;
import com.amazonaws.services.dynamodbv2.rr.ProjectionExpressionWrapper;
import com.amazonaws.util.CollectionUtils;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.commons.lang3.StringUtils;

public class QueryFunction
extends PaginatingFunction<QueryRequest, QueryResult> {
    public QueryFunction(LocalDBAccess dbAccess, DbEnv localDBEnv, LocalDBInputConverter inputConverter, LocalDBOutputConverter localDBOutputConverter, AWSExceptionFactory awsExceptionFactory, DocumentFactory documentFactory) {
        super(dbAccess, localDBEnv, inputConverter, localDBOutputConverter, awsExceptionFactory, documentFactory);
    }

    @Override
    public QueryResult apply(QueryRequest queryRequest) {
        List<String> attributesProjectedInIndex;
        Projection indexProjection;
        Select select;
        int keySchemaSize;
        AttributeDefinition hashKeyDef;
        String tableName = queryRequest.getTableName();
        this.validateTableName(tableName);
        TableInfo tableInfo = this.validateTableExists(tableName);
        long limit2 = this.validateLimitValue(queryRequest.getLimit());
        Boolean asc = queryRequest.getScanIndexForward();
        boolean ascending = asc == null || asc != false;
        boolean isGSIIndex = false;
        String indexName = queryRequest.getIndexName();
        if (indexName != null) {
            if (!tableInfo.hasIndex(indexName)) {
                throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, String.format(LocalDBClientExceptionMessage.SECONDARY_INDEXES_NOT_FOUND.getMessage(), indexName));
            }
            if (tableInfo.isGSIIndex(indexName)) {
                isGSIIndex = true;
            }
        }
        boolean isLSIIndex = indexName != null && !isGSIIndex;
        LocalDBValidatorUtils.validateExpressions(queryRequest, this.inputConverter);
        RangeQueryExpressionsWrapper rangeQueryExpressionsWrapper = this.inputConverter.externalToInternalExpressions(queryRequest.getFilterExpression(), queryRequest.getProjectionExpression(), queryRequest.getKeyConditionExpression(), queryRequest.getExpressionAttributeNames(), queryRequest.getExpressionAttributeValues());
        ExpressionWrapper filterExpressionWrapper = rangeQueryExpressionsWrapper == null ? null : rangeQueryExpressionsWrapper.getFilterExpressionWrapper();
        ProjectionExpressionWrapper projectionExpressionWrapper = rangeQueryExpressionsWrapper == null ? null : rangeQueryExpressionsWrapper.getProjectionExpressionWrapper();
        ExpressionWrapper keyConditionExpressionWrapper = rangeQueryExpressionsWrapper == null ? null : rangeQueryExpressionsWrapper.getKeyConditionExpressionWrapper();
        Expression filterExpression = filterExpressionWrapper == null ? null : filterExpressionWrapper.getExpression();
        ProjectionExpression projectionExpression = projectionExpressionWrapper == null ? null : projectionExpressionWrapper.getProjection();
        LocalDBValidatorUtils.validateNoNestedAccessToKeyAttributeInExpression(tableInfo, filterExpressionWrapper, this.awsExceptionFactory);
        LocalDBValidatorUtils.validateNoNestedAccessToKeyAttributeInExpression(tableInfo, projectionExpressionWrapper, this.awsExceptionFactory);
        LocalDBValidatorUtils.validateNoNestedAccessToKeyAttributeInExpression(tableInfo, keyConditionExpressionWrapper, this.awsExceptionFactory);
        Map<String, Condition> conditions = this.inputConverter.externalToInternalKeyConditions(queryRequest.getKeyConditions(), keyConditionExpressionWrapper);
        if (conditions == null) {
            this.awsExceptionFactory.MISSING_KEY_CONDITIONS_AND_EXPRESSION.throwAsException();
        } else if (conditions.size() == 0 || conditions.size() > 2) {
            this.awsExceptionFactory.INVALID_KEY_CONDITIONS_SIZE.throwAsException();
        }
        if (indexName != null && tableInfo.isGSIIndex(indexName)) {
            if (queryRequest.getConsistentRead() != null && queryRequest.getConsistentRead().booleanValue()) {
                throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, LocalDBClientExceptionMessage.CONSISTENT_GSI_QUERY.getMessage());
            }
            hashKeyDef = tableInfo.getGSIHashKey(indexName);
        } else {
            hashKeyDef = tableInfo.getHashKey();
        }
        AttributeDefinition rangeKeyDef = null;
        rangeKeyDef = indexName != null ? (isGSIIndex ? tableInfo.getGSIRangeKey(indexName) : tableInfo.getLSIRangeKey(indexName)) : tableInfo.getRangeKey();
        int n = keySchemaSize = rangeKeyDef == null ? 1 : 2;
        if (conditions.size() > keySchemaSize) {
            this.awsExceptionFactory.UNSUPPORTED_QUERY_KEY_CONDITION_SEQUENCE.throwAsException();
        }
        this.validateHashKeyCondition(hashKeyDef, conditions);
        this.validateKeyConditionForEmptyAttributeValue(hashKeyDef, conditions.get(hashKeyDef.getAttributeName()), indexName != null, indexName);
        if (rangeKeyDef != null) {
            this.validateRangeKeyCondition(rangeKeyDef, conditions);
            this.validateKeyConditionForEmptyAttributeValue(rangeKeyDef, conditions.get(rangeKeyDef.getAttributeName()), indexName != null, indexName);
        }
        Map exclusiveStartKey = null;
        if (queryRequest.getExclusiveStartKey() != null) {
            exclusiveStartKey = (Map)this.inputConverter.externalToInternalAttributes(queryRequest.getExclusiveStartKey());
        }
        List keyDefs = this.getKeyAttributes(tableInfo, indexName);
        this.validateExclusiveStartKeyQuery(exclusiveStartKey, tableInfo, keyDefs, conditions, ascending, tableInfo.getRangeKey(), indexName, isGSIIndex);
        Map<String, Condition> queryFilters = this.inputConverter.externalToInternalConditions(queryRequest.getQueryFilter());
        if (queryFilters == null) {
            queryFilters = new HashMap();
        }
        this.validateConditions(queryFilters, queryRequest.getConditionalOperator());
        this.validateQueryFilterNotOnKey(queryFilters, this.localDBEnv, hashKeyDef, rangeKeyDef);
        this.validateQueryFilterExpressionNotOnKey(filterExpression, this.localDBEnv, hashKeyDef, rangeKeyDef);
        if (indexName != null) {
            this.validateQueryFilterAndFilterExprOnIndex(queryFilters, filterExpression, indexName, tableInfo);
        }
        if (!((select = this.validateSelect(queryRequest.getSelect(), queryRequest.getAttributesToGet(), projectionExpression, indexName, tableInfo)) == Select.COUNT || StringUtils.isEmpty(queryRequest.getProjectionExpression()) && CollectionUtils.isNullOrEmpty((Collection)queryRequest.getAttributesToGet()))) {
            this.validateAttributesToGetAndProjExpr(queryRequest.getAttributesToGet(), projectionExpression, indexName, tableInfo);
        }
        QueryResultInfo results = this.dbAccess.queryRecords(tableName, indexName, conditions, exclusiveStartKey, limit2, ascending, null, null, false, isGSIIndex);
        QueryResult queryResult = new QueryResult();
        int scannedItemCount = 0;
        List<Map<String, AttributeValue>> dbRecords = results.getReturnedRecords();
        Map<String, AttributeValue> lastEvaluatedItem = null;
        long totalSize = 0L;
        ArrayList<Map<String, AttributeValue>> chargeableDbRecords = new ArrayList<Map<String, AttributeValue>>();
        ArrayList<Map<String, AttributeValue>> dbRecordsAfterFiltering = new ArrayList<Map<String, AttributeValue>>();
        ConditionalOperator conditionalOperator = this.conditionalOperatorFrom(queryRequest.getConditionalOperator());
        for (Map<String, AttributeValue> item : dbRecords) {
            ++scannedItemCount;
            lastEvaluatedItem = item;
            chargeableDbRecords.add(item);
            if (!this.doesItemMatchConditionalOperator(item, queryFilters, conditionalOperator) || !this.doesItemMatchFilterExpression((Map)item, filterExpression)) continue;
            dbRecordsAfterFiltering.add(item);
            if ((totalSize += LocalDBUtils.getItemSizeBytes(item)) < 0x100000L) continue;
            break;
        }
        if (isGSIIndex || isLSIIndex) {
            indexProjection = tableInfo.getProjection(indexName);
            attributesProjectedInIndex = this.determineAttributesToGetWhenSelectingAllProjectedAttributes(tableInfo, indexName, indexProjection.getProjectionType(), indexProjection.getNonKeyAttributes());
        } else {
            indexProjection = null;
            attributesProjectedInIndex = null;
        }
        List<Map<String, AttributeValue>> projectedChargeableDbRecords = LocalDBUtils.projectAttributesList(chargeableDbRecords, attributesProjectedInIndex);
        if (scannedItemCount == dbRecords.size()) {
            lastEvaluatedItem = results.getLastEvaluatedItem();
        }
        queryResult.setCount(Integer.valueOf(dbRecordsAfterFiltering.size()));
        queryResult.setScannedCount(Integer.valueOf(scannedItemCount));
        if (select != Select.COUNT) {
            ArrayList<Map<String, AttributeValue>> filteredList = new ArrayList<Map<String, AttributeValue>>();
            List<String> attributesToGet = this.determineAttributesToGetForQuery(queryRequest, tableInfo, indexName, select);
            if (!StringUtils.isEmpty(queryRequest.getProjectionExpression())) {
                filteredList.addAll(LocalDBUtils.projectAttributesList(dbRecordsAfterFiltering, projectionExpression));
            } else {
                filteredList.addAll(LocalDBUtils.projectAttributesList(dbRecordsAfterFiltering, attributesToGet));
            }
            queryResult.setItems(this.localDBOutputConverter.internalToExternalItemList(filteredList));
        }
        ArrayList<String> keyAttrs = new ArrayList<String>();
        for (AttributeDefinition attrDef : keyDefs) {
            keyAttrs.add(attrDef.getAttributeName());
        }
        Map<String, AttributeValue> lastKey = LocalDBUtils.projectAttributes(lastEvaluatedItem, keyAttrs);
        if (lastKey != null && this.exclusiveStartFitsConditions(lastKey, conditions, ascending, tableInfo.getRangeKey())) {
            queryResult.setLastEvaluatedKey(this.localDBOutputConverter.internalToExternalAttributes(lastKey));
        } else {
            queryResult.setLastEvaluatedKey(null);
        }
        if (indexName == null && !tableInfo.hasRangeKey()) {
            queryResult.setLastEvaluatedKey(null);
        }
        boolean stronglyConsistent = queryRequest.getConsistentRead() != null && queryRequest.getConsistentRead() != false;
        ReturnConsumedCapacity returnConsumedCapacity = this.convertReturnConsumedCapacity(queryRequest.getReturnConsumedCapacity());
        ConsumedCapacity consumedCapacity = ConsumedCapacityUtils.computeConsumedCapacity(projectedChargeableDbRecords, isGSIIndex, !isGSIIndex && indexName != null, tableName, indexName, false, stronglyConsistent, this.transactionsMode, returnConsumedCapacity);
        if (!ConsumedCapacityUtils.doNotRequireConsumedCapacity(returnConsumedCapacity) && isLSIIndex && LSI_SELECTS_TO_READ_FROM_BASE_TABLE.contains(select) && !ProjectionType.ALL.equals((Object)ProjectionType.fromValue((String)indexProjection.getProjectionType()))) {
            ConsumedCapacity baseTableConsumedCapacity = ConsumedCapacityUtils.computeConsumedCapacity(chargeableDbRecords, false, false, tableName, null, true, stronglyConsistent, TransactionsEnabledMode.TRANSACTIONS_DISABLED, returnConsumedCapacity);
            if (ReturnConsumedCapacity.INDEXES == returnConsumedCapacity) {
                consumedCapacity.withTable(new Capacity().withCapacityUnits(Double.valueOf(consumedCapacity.getTable().getCapacityUnits() + baseTableConsumedCapacity.getCapacityUnits())));
            }
            consumedCapacity.withCapacityUnits(Double.valueOf(consumedCapacity.getCapacityUnits() + baseTableConsumedCapacity.getCapacityUnits()));
        }
        return queryResult.withConsumedCapacity(consumedCapacity);
    }

    void validateHashKeyCondition(AttributeDefinition hashKeyDef, Map<String, Condition> keyConditions) {
        AttributeValue expectedVal;
        List<AttributeValue> expectedVals;
        this.localDBEnv.dbAssert(keyConditions != null, "validateHashKeyCondition", "keyConditions should not be null", new Object[0]);
        this.localDBEnv.dbAssert(hashKeyDef != null, "validateHashKeyCondition", "table hash key schema should not be null", new Object[0]);
        Condition requestHashCondition = keyConditions.get(hashKeyDef.getAttributeName());
        if (requestHashCondition == null || requestHashCondition.getComparisonOperator() == null) {
            this.awsExceptionFactory.KEY_CONDITIONS_MISSING_KEY.throwAsException();
        }
        if ((expectedVals = requestHashCondition.getAttributeValueList()) == null || expectedVals.isEmpty()) {
            this.awsExceptionFactory.INVALID_HASH_KEY_VALUE.throwAsException();
        }
        if ((expectedVal = requestHashCondition.getAttributeValueList().get(0)) == null) {
            this.awsExceptionFactory.INVALID_HASH_KEY_VALUE.throwAsException();
        }
        LocalDBUtils.validateConsistentTypes(hashKeyDef, expectedVal, LocalDBClientExceptionMessage.INCONSISTENT_CONDITION_PARAMETER);
        if (!requestHashCondition.getComparisonOperator().equals(ComparisonOperator.EQ.toString())) {
            this.awsExceptionFactory.UNSUPPORTED_QUERY_KEY_CONDITION_SEQUENCE.throwAsException();
        }
        if (requestHashCondition.getAttributeValueList().size() > 1) {
            this.awsExceptionFactory.INVALID_FILTER_ARGUMENT_COUNT.throwAsException();
        }
        if (!DDBType.SortableScalarTypeSet.contains((Object)expectedVal.getType())) {
            this.awsExceptionFactory.INVALID_HASH_KEY_VALUE.throwAsException();
        }
    }

    private void validateKeyConditionForEmptyAttributeValue(AttributeDefinition keyDefinition, Condition keyCondition, boolean isIndex, String indexName) {
        block4: {
            block3: {
                if (keyCondition == null) {
                    return;
                }
                if (!"S".equals(keyDefinition.getAttributeType())) break block3;
                for (AttributeValue attributeValue : keyCondition.getAttributeValueList()) {
                    this.validateKeyForEmptyStringValue(attributeValue, keyDefinition.getAttributeName(), isIndex, indexName, null);
                }
                break block4;
            }
            if (!"B".equals(keyDefinition.getAttributeType())) break block4;
            for (AttributeValue attributeValue : keyCondition.getAttributeValueList()) {
                this.validateKeyForEmptyBinaryValue(attributeValue, keyDefinition.getAttributeName(), isIndex, indexName, null);
            }
        }
    }

    private void validateQueryFilterNotOnKey(Map<String, Condition> queryFilter, DbEnv dbEnv, AttributeDefinition hashKeyDef, AttributeDefinition rangeKeyDef) {
        if (queryFilter == null) {
            return;
        }
        if (queryFilter.containsKey(hashKeyDef.getAttributeName())) {
            throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, String.format(LocalDBClientExceptionMessage.QUERY_FILTER_CONTAINS_PRIMARY_KEY_ATTRIBUTES.getMessage(), hashKeyDef.getAttributeName()));
        }
        if (rangeKeyDef != null && queryFilter.containsKey(rangeKeyDef.getAttributeName())) {
            throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, String.format(LocalDBClientExceptionMessage.QUERY_FILTER_CONTAINS_PRIMARY_KEY_ATTRIBUTES.getMessage(), rangeKeyDef.getAttributeName()));
        }
    }

    private void validateRangeKeyCondition(AttributeDefinition rangeKeyDef, Map<String, Condition> keyConditions) {
        Condition requestRangeCondition = keyConditions.get(rangeKeyDef.getAttributeName());
        if (requestRangeCondition == null) {
            if (keyConditions.size() > 1) {
                this.awsExceptionFactory.KEY_CONDITIONS_MISSING_KEY.throwAsException();
            }
        } else {
            int expectedComparisonArguments;
            List<AttributeValue> expectedVals = requestRangeCondition.getAttributeValueList();
            if (expectedVals == null || expectedVals.isEmpty()) {
                this.awsExceptionFactory.INVALID_RANGE_KEY_VALUE.throwAsException();
            }
            for (AttributeValue expectedVal : requestRangeCondition.getAttributeValueList()) {
                if (LocalDBUtils.getAttributeValueSizeBytes(expectedVal) > 1024L) {
                    this.awsExceptionFactory.INVALID_PARAMETER_VALUE.throwAsException("Aggregated size of all range keys has exceeded the size limit of 1024 bytes");
                }
                LocalDBUtils.validateConsistentTypes(rangeKeyDef, expectedVal, LocalDBClientExceptionMessage.INCONSISTENT_CONDITION_PARAMETER);
            }
            ComparisonOperator comparisonOperator = this.validateConditionType(requestRangeCondition);
            LocalDBComparisonOperator localOp = LocalDBComparisonOperator.fromValue(comparisonOperator);
            if (!localOp.isValidForQuery()) {
                this.awsExceptionFactory.NON_INDEXABLE_CONDITION.throwAsException();
            }
            int n = expectedComparisonArguments = comparisonOperator == ComparisonOperator.BETWEEN ? 2 : 1;
            if (requestRangeCondition.getAttributeValueList().size() != expectedComparisonArguments) {
                this.awsExceptionFactory.INVALID_FILTER_ARGUMENT_COUNT.throwAsException();
            }
        }
    }

    private void validateExclusiveStartKeyQuery(Map<String, AttributeValue> exclusiveStartKey, TableInfo tableInfo, List<AttributeDefinition> keyDefs, Map<String, Condition> queryConditions, boolean asc, AttributeDefinition rangeKey, String indexName, boolean isGsiIndex) {
        this.validateExclusiveStartKey((Map)exclusiveStartKey, (List)keyDefs);
        this.validateExclusiveStartKeyForEmptyAttributeValue((Map)exclusiveStartKey, tableInfo, indexName, isGsiIndex);
        if (exclusiveStartKey == null) {
            return;
        }
        if (!this.exclusiveStartFitsConditions(exclusiveStartKey, queryConditions, asc, rangeKey)) {
            throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, LocalDBClientExceptionMessage.INVALID_START_KEY_RANGE.getMessage());
        }
    }

    private void validateQueryFilterExpressionNotOnKey(Expression filterExpression, DbEnv dbEnv, AttributeDefinition hashKeyDef, AttributeDefinition rangeKeyDef) {
        if (filterExpression == null) {
            return;
        }
        Set<String> topLevelAttributes = ExpressionUtils.getConditionExpressionTopLevelAttributes(filterExpression, dbEnv);
        if (topLevelAttributes.contains(hashKeyDef.getAttributeName())) {
            throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, String.format(LocalDBClientExceptionMessage.QUERY_FILTER_EXPRESSION_CONTAINS_PRIMARY_KEY_ATTRIBUTES.getMessage(), hashKeyDef.getAttributeName()));
        }
        if (rangeKeyDef != null && topLevelAttributes.contains(rangeKeyDef.getAttributeName())) {
            throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, String.format(LocalDBClientExceptionMessage.QUERY_FILTER_EXPRESSION_CONTAINS_PRIMARY_KEY_ATTRIBUTES.getMessage(), rangeKeyDef.getAttributeName()));
        }
    }

    private boolean exclusiveStartFitsConditions(Map<String, AttributeValue> exclusiveStartKey, Map<String, Condition> queryConditions, boolean asc, AttributeDefinition rangeKey) {
        for (Map.Entry<String, AttributeValue> entry : exclusiveStartKey.entrySet()) {
            Condition keyCondition = queryConditions.get(entry.getKey());
            AttributeValue val = entry.getValue();
            if (keyCondition == null || !(rangeKey != null && entry.getKey().equals(rangeKey.getAttributeName()) ? !LocalDBComparisonOperator.fromValue(keyCondition.getComparisonOperator()).evaluateExclusive(keyCondition.getAttributeValueList(), val, asc) : !LocalDBComparisonOperator.fromValue(keyCondition.getComparisonOperator()).evaluate(keyCondition.getAttributeValueList(), val))) continue;
            return false;
        }
        return true;
    }

    private void validateQueryFilterAndFilterExprOnIndex(Map<String, Condition> queryFilter, Expression expression, String indexName, TableInfo tableInfo) {
        List<String> attributesThatAreNotProjectedOnIndex = this.getNonProjectedAttributeNames(queryFilter, expression, indexName, tableInfo);
        if (attributesThatAreNotProjectedOnIndex.size() > 0) {
            Collections.sort(attributesThatAreNotProjectedOnIndex);
            throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, String.format(LocalDBClientExceptionMessage.INVALID_PARAMETER_VALUE.getMessage(), "Secondary index " + indexName + " does not project one or more filter attributes: " + attributesThatAreNotProjectedOnIndex));
        }
    }

    private List<String> getNonProjectedAttributeNames(Map<String, Condition> queryFilter, Expression expression, String indexName, TableInfo tableInfo) {
        Projection projection = tableInfo.getProjection(indexName);
        ProjectionType projectionType = ProjectionType.fromValue((String)projection.getProjectionType());
        if (projectionType == ProjectionType.ALL) {
            return new ArrayList<String>();
        }
        ArrayList<String> list = new ArrayList<String>();
        if (queryFilter != null) {
            list.addAll(queryFilter.keySet());
        }
        if (expression != null) {
            list.addAll(ExpressionUtils.getConditionExpressionTopLevelAttributes(expression, this.localDBEnv));
        }
        if (projection.getNonKeyAttributes() != null) {
            list.removeAll(projection.getNonKeyAttributes());
        }
        list.removeAll(this.getKeyAttributeNames(tableInfo));
        return list;
    }

    private List<String> determineAttributesToGetForQuery(QueryRequest queryRequest, TableInfo tableInfo, String indexName, Select select) {
        if (select == Select.SPECIFIC_ATTRIBUTES) {
            return queryRequest.getAttributesToGet();
        }
        if (select == Select.ALL_PROJECTED_ATTRIBUTES) {
            Projection indexProjection = tableInfo.getProjection(indexName);
            return this.determineAttributesToGetWhenSelectingAllProjectedAttributes(tableInfo, indexName, indexProjection.getProjectionType(), indexProjection.getNonKeyAttributes());
        }
        return null;
    }

    private ArrayList<String> getKeyAttributeNames(TableInfo tableInfo) {
        ArrayList<String> list = new ArrayList<String>();
        for (KeySchemaElement keySchemaElement : tableInfo.getKeySchema()) {
            list.add(keySchemaElement.getAttributeName());
        }
        return list;
    }
}

