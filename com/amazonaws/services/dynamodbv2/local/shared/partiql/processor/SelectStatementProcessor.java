/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  com.amazonaws.services.dynamodbv2.model.AttributeDefinition
 *  com.amazonaws.services.dynamodbv2.model.AttributeValue
 *  com.amazonaws.services.dynamodbv2.model.ExecuteStatementResult
 *  com.amazonaws.services.dynamodbv2.model.Projection
 *  com.amazonaws.services.dynamodbv2.model.ProjectionType
 *  com.amazonaws.services.dynamodbv2.model.ScalarAttributeType
 */
package com.amazonaws.services.dynamodbv2.local.shared.partiql.processor;

import com.amazonaws.services.dynamodbv2.datamodel.DocPath;
import com.amazonaws.services.dynamodbv2.datamodel.DocPathElement;
import com.amazonaws.services.dynamodbv2.datamodel.DocumentFactory;
import com.amazonaws.services.dynamodbv2.datamodel.ExprTreeNode;
import com.amazonaws.services.dynamodbv2.datamodel.ExprTreeOpNode;
import com.amazonaws.services.dynamodbv2.datamodel.ExprTreePathNode;
import com.amazonaws.services.dynamodbv2.datamodel.ExprTreeValueNode;
import com.amazonaws.services.dynamodbv2.datamodel.Expression;
import com.amazonaws.services.dynamodbv2.datamodel.Operator;
import com.amazonaws.services.dynamodbv2.datamodel.ProjectionExpression;
import com.amazonaws.services.dynamodbv2.exceptions.AWSExceptionFactory;
import com.amazonaws.services.dynamodbv2.exceptions.AmazonServiceExceptionType;
import com.amazonaws.services.dynamodbv2.local.shared.access.LocalDBAccess;
import com.amazonaws.services.dynamodbv2.local.shared.access.LocalDBUtils;
import com.amazonaws.services.dynamodbv2.local.shared.access.LocalDBValidatorUtils;
import com.amazonaws.services.dynamodbv2.local.shared.access.QueryResultInfo;
import com.amazonaws.services.dynamodbv2.local.shared.access.TableInfo;
import com.amazonaws.services.dynamodbv2.local.shared.access.api.dp.PartiQLStatementFunction;
import com.amazonaws.services.dynamodbv2.local.shared.env.LocalPartiQLDbEnv;
import com.amazonaws.services.dynamodbv2.local.shared.exceptions.LocalDBClientExceptionMessage;
import com.amazonaws.services.dynamodbv2.local.shared.mapper.DynamoDBObjectMapper;
import com.amazonaws.services.dynamodbv2.local.shared.model.Condition;
import com.amazonaws.services.dynamodbv2.local.shared.partiql.ParsedPartiQLRequest;
import com.amazonaws.services.dynamodbv2.local.shared.partiql.model.TranslatedPartiQLOperation;
import com.amazonaws.services.dynamodbv2.local.shared.partiql.processor.StatementProcessor;
import com.amazonaws.services.dynamodbv2.local.shared.partiql.token.ContinuationTokenSerializer;
import com.amazonaws.services.dynamodbv2.local.shared.partiql.translator.SelectStatementTranslator;
import com.amazonaws.services.dynamodbv2.local.shared.partiql.util.OrderingStatus;
import com.amazonaws.services.dynamodbv2.local.shared.partiql.util.SelectProjectionUnnester;
import com.amazonaws.services.dynamodbv2.local.shared.partiql.util.SelectResultsOrderer;
import com.amazonaws.services.dynamodbv2.model.AttributeDefinition;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ExecuteStatementResult;
import com.amazonaws.services.dynamodbv2.model.Projection;
import com.amazonaws.services.dynamodbv2.model.ProjectionType;
import com.amazonaws.services.dynamodbv2.model.ScalarAttributeType;
import com.amazonaws.services.dynamodbv2.rr.ExpressionWrapper;
import com.amazonaws.services.dynamodbv2.rr.ProjectionExpressionWrapper;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.partiql.lang.ast.OrderBy;
import org.partiql.lang.ast.Select;

public class SelectStatementProcessor
extends StatementProcessor<Select> {
    private final SelectStatementTranslator translator;
    private final ContinuationTokenSerializer continuationTokenSerializer;
    private final SelectProjectionUnnester selectProjectionUnnester;
    private final SelectResultsOrderer selectResultsOrderer;

    public SelectStatementProcessor(SelectStatementTranslator translator, LocalDBAccess dbAccess, LocalPartiQLDbEnv localPartiQLDbEnv, PartiQLStatementFunction partiQLStatementFunction, DocumentFactory documentFactory) {
        super(dbAccess, localPartiQLDbEnv, partiQLStatementFunction, documentFactory);
        this.translator = translator;
        this.continuationTokenSerializer = new ContinuationTokenSerializer(new DynamoDBObjectMapper(), localPartiQLDbEnv);
        this.selectProjectionUnnester = new SelectProjectionUnnester(localPartiQLDbEnv);
        this.selectResultsOrderer = new SelectResultsOrderer(localPartiQLDbEnv);
    }

    @Override
    public ExecuteStatementResult execute(ParsedPartiQLRequest<Select> request) {
        TranslatedPartiQLOperation translatedPartiQLOperation = this.translator.translate(request);
        return this.invokePartiqlSelect(request, translatedPartiQLOperation);
    }

    public ExecuteStatementResult invokePartiqlSelect(ParsedPartiQLRequest<Select> request, TranslatedPartiQLOperation translatedPartiQLOperation) {
        Map<String, com.amazonaws.services.dynamodbv2.local.shared.model.AttributeValue> exclusiveStartKey = this.continuationTokenSerializer.deserializeAndConvertContinuationToken(request);
        AbstractMap.SimpleEntry<Map<String, com.amazonaws.services.dynamodbv2.local.shared.model.AttributeValue>, List<Map<String, AttributeValue>>> lastKeyAndReturnedItems = this.partiqlSelectStatement(translatedPartiQLOperation.getTableName(), translatedPartiQLOperation.getIndexName(), translatedPartiQLOperation.getKeyConditions(), translatedPartiQLOperation.getProjectionExpressionWrapper(), translatedPartiQLOperation.getConditionExpressionWrapper(), translatedPartiQLOperation.getOrderBy(), translatedPartiQLOperation.isConsistentRead(), translatedPartiQLOperation.getLimit(), exclusiveStartKey);
        return new ExecuteStatementResult().withItems((Collection)lastKeyAndReturnedItems.getValue()).withNextToken(this.continuationTokenSerializer.createAndSerializeContinuationToken(request, lastKeyAndReturnedItems.getKey()));
    }

    /*
     * WARNING - void declaration
     */
    public AbstractMap.SimpleEntry<Map<String, com.amazonaws.services.dynamodbv2.local.shared.model.AttributeValue>, List<Map<String, AttributeValue>>> partiqlSelectStatement(String tableName, String indexName, Map<String, Condition> keyConditions, ProjectionExpressionWrapper projectionExpressionWrapper, ExpressionWrapper filterExpressionWrapper, OrderBy orderBy, boolean isConsistentRead, Integer limit2, Map<String, com.amazonaws.services.dynamodbv2.local.shared.model.AttributeValue> exclusiveStartKey) {
        OrderingStatus orderingStatus;
        boolean isGsiIndex;
        this.partiQLStatementFunction.validateTableName(tableName);
        TableInfo info = this.partiQLStatementFunction.validateTableExists(tableName);
        boolean hasRangeKey = info.hasRangeKey();
        List indexAttributesToGet = null;
        if (indexName != null) {
            if (!info.hasIndex(indexName)) {
                throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, String.format(LocalDBClientExceptionMessage.SECONDARY_INDEXES_NOT_FOUND.getMessage(), indexName));
            }
            Projection indexProjection = info.getProjection(indexName);
            String projectionType = indexProjection.getProjectionType();
            if (ProjectionType.INCLUDE.toString().equals(projectionType) || ProjectionType.KEYS_ONLY.toString().equals(projectionType)) {
                indexAttributesToGet = this.partiQLStatementFunction.getAttributeNames(this.partiQLStatementFunction.getKeyAttributes(info, indexName));
                if (ProjectionType.INCLUDE.toString().equals(projectionType)) {
                    indexAttributesToGet.addAll(indexProjection.getNonKeyAttributes());
                }
            }
        }
        boolean bl = isGsiIndex = indexName != null && info.isGSIIndex(indexName);
        if (indexName != null && info.isGSIIndex(indexName) && isConsistentRead) {
            throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, LocalDBClientExceptionMessage.CONSISTENT_GSI_SCAN.getMessage());
        }
        Expression filterExpression = filterExpressionWrapper == null ? null : filterExpressionWrapper.getExpression();
        ProjectionExpression projectionExpression = projectionExpressionWrapper == null ? null : projectionExpressionWrapper.getProjection();
        List keyDefs = this.partiQLStatementFunction.getKeyAttributes(info, indexName);
        this.partiQLStatementFunction.validateExclusiveStartKey((Map)exclusiveStartKey, keyDefs);
        this.partiQLStatementFunction.validateExclusiveStartKeyForEmptyAttributeValue((Map)exclusiveStartKey, info, indexName, isGsiIndex);
        LocalDBValidatorUtils.validateNoNestedAccessToKeyAttributeInExpression(info, filterExpressionWrapper, this.partiQLStatementFunction.awsExceptionFactory);
        HashMap<String, Condition> extractedFilterConditions = new HashMap<String, Condition>();
        ArrayList<Map<String, Condition>> keyConditionList = new ArrayList<Map<String, Condition>>();
        if (filterExpression != null) {
            this.extractConditionsFromExprTreeNode(filterExpression.getExprTree(), extractedFilterConditions);
            this.checkForAndValidatePossibleKeyConditions(keyDefs, extractedFilterConditions, hasRangeKey, indexName);
            if (Operator.IN == ((ExprTreeOpNode)filterExpression.getExprTree()).getOperator()) {
                if (indexName != null && info.isGSIIndex(indexName) && extractedFilterConditions.containsKey(info.getGSIHashKey(indexName).getAttributeName())) {
                    for (com.amazonaws.services.dynamodbv2.local.shared.model.AttributeValue attributeValue : ((Condition)extractedFilterConditions.get(info.getGSIHashKey(indexName).getAttributeName())).getAttributeValueList()) {
                        keyConditionList.add(this.filterToKeyCondition(attributeValue, info, indexName));
                    }
                }
                if (extractedFilterConditions.containsKey(info.getHashKey().getAttributeName())) {
                    for (com.amazonaws.services.dynamodbv2.local.shared.model.AttributeValue attributeValue : ((Condition)extractedFilterConditions.get(info.getHashKey().getAttributeName())).getAttributeValueList()) {
                        keyConditionList.add(this.filterToKeyCondition(attributeValue, info, info.getHashKey().getAttributeName()));
                    }
                }
            }
        }
        if (keyConditions != null) {
            keyConditionList.add(keyConditions);
        }
        LocalDBValidatorUtils.validateNoNestedAccessToKeyAttributeInExpression(info, projectionExpressionWrapper, this.partiQLStatementFunction.awsExceptionFactory);
        this.partiQLStatementFunction.validateAttributesToGetAndProjExpr((List)null, projectionExpression, indexName, info);
        ArrayList<QueryResultInfo> results = new ArrayList<QueryResultInfo>();
        if (keyConditionList.isEmpty()) {
            results.add(this.dbAccess.queryRecords(tableName, indexName, null, exclusiveStartKey, -1L, true, null, null, true, isGsiIndex));
        } else {
            for (Map map2 : keyConditionList) {
                if (map2 == null) continue;
                this.checkForAndValidatePossibleKeyConditions(keyDefs, map2, hasRangeKey, indexName);
                results.add(this.dbAccess.queryRecords(tableName, indexName, map2, exclusiveStartKey, -1L, true, null, null, false, isGsiIndex));
            }
        }
        boolean bl2 = false;
        long l = 0L;
        boolean scanLimiter = false;
        List<Map<String, com.amazonaws.services.dynamodbv2.local.shared.model.AttributeValue>> dbRecordsAfterFiltering = new ArrayList<Map<String, com.amazonaws.services.dynamodbv2.local.shared.model.AttributeValue>>();
        Map<Object, Object> lastKey = new HashMap();
        for (QueryResultInfo result : results) {
            Map<String, com.amazonaws.services.dynamodbv2.local.shared.model.AttributeValue> lastItem;
            List<Map<String, com.amazonaws.services.dynamodbv2.local.shared.model.AttributeValue>> dbRecords = result.getReturnedRecords();
            int itemCount = 0;
            for (Map<String, com.amazonaws.services.dynamodbv2.local.shared.model.AttributeValue> item : dbRecords) {
                void var20_27;
                if (this.partiQLStatementFunction.doesItemMatchFilterExpression((Map)item, filterExpression)) {
                    Map<String, com.amazonaws.services.dynamodbv2.local.shared.model.AttributeValue> filteredItem;
                    if (projectionExpression == null && indexAttributesToGet != null) {
                        filteredItem = LocalDBUtils.projectAttributes(item, indexAttributesToGet);
                    } else {
                        filteredItem = LocalDBUtils.projectAttributes(item, projectionExpression);
                        filteredItem = this.selectProjectionUnnester.unnestProjection(filteredItem, projectionExpression);
                    }
                    if (filteredItem != null) {
                        dbRecordsAfterFiltering.add(filteredItem);
                    }
                }
                long itemSize = LocalDBUtils.getItemSizeBytes(item);
                ++itemCount;
                if ((l += itemSize) < 0x100000L && (limit2 == null || ++var20_27 < limit2 || limit2 < 1)) continue;
                scanLimiter = true;
                break;
            }
            if (scanLimiter) {
                lastItem = result.getReturnedRecords().get(itemCount - 1);
                lastKey = LocalDBUtils.projectAttributes(lastItem, this.partiQLStatementFunction.getAttributeNames(keyDefs));
                break;
            }
            lastItem = result.getLastEvaluatedItem();
            lastKey = LocalDBUtils.projectAttributes(lastItem, this.partiQLStatementFunction.getAttributeNames(keyDefs));
        }
        if ((orderingStatus = this.selectResultsOrderer.getOrderingStatus(orderBy, info, indexName)) != null) {
            dbRecordsAfterFiltering = this.selectResultsOrderer.orderRecords(dbRecordsAfterFiltering, orderingStatus);
        }
        return new AbstractMap.SimpleEntry<Map<String, com.amazonaws.services.dynamodbv2.local.shared.model.AttributeValue>, List<Map<String, AttributeValue>>>(lastKey, this.partiQLStatementFunction.localDBOutputConverter.internalToExternalItemList(dbRecordsAfterFiltering));
    }

    private Map<String, Condition> filterToKeyCondition(com.amazonaws.services.dynamodbv2.local.shared.model.AttributeValue attributeValue, TableInfo info, String keyName) {
        Condition condition = new Condition();
        HashMap<String, Condition> conditionMap = new HashMap<String, Condition>();
        condition.setComparisonOperator(Operator.EQ.toString());
        condition.setAttributeValueList(Collections.singleton(attributeValue));
        if (info.isGSIIndex(keyName)) {
            conditionMap.put(info.getGSIHashKey(keyName).getAttributeName(), condition);
        } else {
            conditionMap.put(info.getHashKey().getAttributeName(), condition);
        }
        return conditionMap;
    }

    private void checkForAndValidatePossibleKeyConditions(List<AttributeDefinition> keyDefs, Map<String, Condition> conditions, boolean hasRangeKey, String indexName) {
        boolean isIndexKey = false;
        int index = 0;
        for (AttributeDefinition keyDefinition : keyDefs) {
            Condition keyCondition = conditions.get(keyDefinition.getAttributeName());
            if (keyCondition == null) continue;
            if (index == 1 && !hasRangeKey || index == 2) {
                isIndexKey = true;
            }
            com.amazonaws.services.dynamodbv2.local.shared.model.AttributeValue expectedVal = keyCondition.getAttributeValueList().get(0);
            LocalDBUtils.validateConsistentTypes(keyDefinition, expectedVal, LocalDBClientExceptionMessage.INCONSISTENT_CONDITION_PARAMETER);
            if (ScalarAttributeType.S.toString().equals(keyDefinition.getAttributeType())) {
                for (com.amazonaws.services.dynamodbv2.local.shared.model.AttributeValue attributeValue : keyCondition.getAttributeValueList()) {
                    this.partiQLStatementFunction.validateKeyForEmptyStringValue(attributeValue, keyDefinition.getAttributeName(), isIndexKey, indexName, null);
                }
            } else if (ScalarAttributeType.B.toString().equals(keyDefinition.getAttributeType())) {
                for (com.amazonaws.services.dynamodbv2.local.shared.model.AttributeValue attributeValue : keyCondition.getAttributeValueList()) {
                    this.partiQLStatementFunction.validateKeyForEmptyBinaryValue(attributeValue, keyDefinition.getAttributeName(), isIndexKey, indexName, null);
                }
            }
            ++index;
        }
    }

    private void extractConditionsFromExprTreeNode(ExprTreeNode currentNode, Map<String, Condition> extractedConditions) {
        ExprTreeOpNode currentOpNode = (ExprTreeOpNode)currentNode;
        Operator currentOperator = currentOpNode.getOperator();
        if (Operator.AND == currentOperator || Operator.OR == currentOperator || Operator.NOT == currentOperator) {
            for (ExprTreeNode childExprTreeNode : currentOpNode.getChildren()) {
                this.extractConditionsFromExprTreeNode(childExprTreeNode, extractedConditions);
            }
        } else {
            this.getConditionFromOperatorNodeAndAddToExtractedConditions(currentOpNode, extractedConditions);
        }
    }

    private void getConditionFromOperatorNodeAndAddToExtractedConditions(ExprTreeOpNode currentOpNode, Map<String, Condition> extractedConditions) {
        String attrName = null;
        ArrayList<com.amazonaws.services.dynamodbv2.local.shared.model.AttributeValue> attrValues = new ArrayList<com.amazonaws.services.dynamodbv2.local.shared.model.AttributeValue>();
        for (ExprTreeNode child2 : currentOpNode.getChildren()) {
            if (child2 instanceof ExprTreePathNode) {
                DocPath current = ((ExprTreePathNode)child2).getDocPath();
                DocPathElement element = current.getElements().get(0);
                attrName = element.getFieldName();
                continue;
            }
            if (!(child2 instanceof ExprTreeValueNode)) continue;
            attrValues.add((com.amazonaws.services.dynamodbv2.local.shared.model.AttributeValue)((ExprTreeValueNode)child2).getValue());
        }
        if (attrName != null) {
            Condition condition = new Condition();
            condition.setAttributeValueList(attrValues);
            extractedConditions.put(attrName, condition);
        }
    }
}

