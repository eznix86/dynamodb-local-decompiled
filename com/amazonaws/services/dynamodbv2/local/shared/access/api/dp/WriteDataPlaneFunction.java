/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  com.amazonaws.services.dynamodbv2.model.Capacity
 *  com.amazonaws.services.dynamodbv2.model.ConsumedCapacity
 *  com.amazonaws.services.dynamodbv2.model.GlobalSecondaryIndexDescription
 *  com.amazonaws.services.dynamodbv2.model.KeySchemaElement
 *  com.amazonaws.services.dynamodbv2.model.LocalSecondaryIndex
 *  com.amazonaws.services.dynamodbv2.model.Projection
 *  com.amazonaws.services.dynamodbv2.model.ProjectionType
 *  com.amazonaws.services.dynamodbv2.model.ReturnConsumedCapacity
 *  com.amazonaws.services.dynamodbv2.model.ReturnValue
 *  com.amazonaws.services.dynamodbv2.model.ReturnValuesOnConditionCheckFailure
 */
package com.amazonaws.services.dynamodbv2.local.shared.access.api.dp;

import com.amazonaws.services.dynamodbv2.datamodel.DocumentFactory;
import com.amazonaws.services.dynamodbv2.datamodel.Expression;
import com.amazonaws.services.dynamodbv2.datamodel.ExpressionExecutor;
import com.amazonaws.services.dynamodbv2.datamodel.UpdateExpression;
import com.amazonaws.services.dynamodbv2.dbenv.DbEnv;
import com.amazonaws.services.dynamodbv2.exceptions.AWSExceptionFactory;
import com.amazonaws.services.dynamodbv2.exceptions.AmazonServiceExceptionType;
import com.amazonaws.services.dynamodbv2.local.google.Preconditions;
import com.amazonaws.services.dynamodbv2.local.google.Sets;
import com.amazonaws.services.dynamodbv2.local.shared.access.DDBType;
import com.amazonaws.services.dynamodbv2.local.shared.access.ExceptionHandler;
import com.amazonaws.services.dynamodbv2.local.shared.access.LocalDBAccess;
import com.amazonaws.services.dynamodbv2.local.shared.access.LocalDBInputConverter;
import com.amazonaws.services.dynamodbv2.local.shared.access.LocalDBOutputConverter;
import com.amazonaws.services.dynamodbv2.local.shared.access.LocalDBUtils;
import com.amazonaws.services.dynamodbv2.local.shared.access.TableInfo;
import com.amazonaws.services.dynamodbv2.local.shared.access.UnsignedByteArrayComparator;
import com.amazonaws.services.dynamodbv2.local.shared.access.api.Mutation;
import com.amazonaws.services.dynamodbv2.local.shared.access.api.dp.DataPlaneFunction;
import com.amazonaws.services.dynamodbv2.local.shared.exceptions.ExpressionExecutionException;
import com.amazonaws.services.dynamodbv2.local.shared.exceptions.LocalDBClientExceptionMessage;
import com.amazonaws.services.dynamodbv2.local.shared.helpers.TransactionsEnabledMode;
import com.amazonaws.services.dynamodbv2.local.shared.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.local.shared.model.ExpectedAttributeValue;
import com.amazonaws.services.dynamodbv2.model.Capacity;
import com.amazonaws.services.dynamodbv2.model.ConsumedCapacity;
import com.amazonaws.services.dynamodbv2.model.GlobalSecondaryIndexDescription;
import com.amazonaws.services.dynamodbv2.model.KeySchemaElement;
import com.amazonaws.services.dynamodbv2.model.LocalSecondaryIndex;
import com.amazonaws.services.dynamodbv2.model.Projection;
import com.amazonaws.services.dynamodbv2.model.ProjectionType;
import com.amazonaws.services.dynamodbv2.model.ReturnConsumedCapacity;
import com.amazonaws.services.dynamodbv2.model.ReturnValue;
import com.amazonaws.services.dynamodbv2.model.ReturnValuesOnConditionCheckFailure;
import java.math.BigDecimal;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

abstract class WriteDataPlaneFunction<I, O>
extends DataPlaneFunction<I, O> {
    protected static final Map<String, AttributeValue> NULL_IMAGE = null;
    protected static final double ONE_CAPACITY_UNIT = 1.0;
    protected final DocumentFactory documentFactory;

    WriteDataPlaneFunction(LocalDBAccess dbAccess, DbEnv localDBEnv, LocalDBInputConverter inputConverter, LocalDBOutputConverter localDBOutputConverter, AWSExceptionFactory awsExceptionFactory, DocumentFactory documentFactory, TransactionsEnabledMode transactionsMode) {
        super(dbAccess, localDBEnv, inputConverter, localDBOutputConverter, awsExceptionFactory, transactionsMode);
        this.documentFactory = documentFactory;
    }

    public Map<String, AttributeValue> validatePutItem(Map<String, AttributeValue> item, TableInfo tableInfo) {
        long baseItemSize;
        boolean hasGSIs;
        HashMap<String, AttributeValue> nonKeyAttrs = new HashMap<String, AttributeValue>(item);
        HashMap<String, AttributeValue> keyAttrs = new HashMap<String, AttributeValue>();
        this.validateKeyValue(item, tableInfo.getHashKey(), true, 2048L);
        String hashKeyName = tableInfo.getHashKey().getAttributeName();
        nonKeyAttrs.remove(hashKeyName);
        keyAttrs.put(hashKeyName, item.get(hashKeyName));
        if (tableInfo.hasRangeKey()) {
            this.validateKeyValue(item, tableInfo.getRangeKey(), true, 1024L);
            String rangeKeyName = tableInfo.getRangeKey().getAttributeName();
            nonKeyAttrs.remove(rangeKeyName);
            keyAttrs.put(rangeKeyName, item.get(rangeKeyName));
            for (String curIndex : tableInfo.getLSINames()) {
                this.validateKeyValue(item, tableInfo.getLSIRangeKey(curIndex), false, 1024L, true, curIndex);
                nonKeyAttrs.remove(tableInfo.getLSIRangeKey(curIndex).getAttributeName());
            }
        }
        if (hasGSIs = tableInfo.hasGSIs()) {
            for (String curIndex : tableInfo.getGSINames()) {
                this.validateKeyValue(item, tableInfo.getGSIHashKey(curIndex), false, 2048L, hasGSIs, curIndex);
                nonKeyAttrs.remove(tableInfo.getGSIHashKey(curIndex).getAttributeName());
                if (tableInfo.getGSIRangeKey(curIndex) == null) continue;
                this.validateKeyValue(item, tableInfo.getGSIRangeKey(curIndex), false, 1024L, hasGSIs, curIndex);
                nonKeyAttrs.remove(tableInfo.getGSIRangeKey(curIndex).getAttributeName());
            }
        }
        for (String nonKeyAttrName : nonKeyAttrs.keySet()) {
            AttributeValue nonKeyAttr = (AttributeValue)nonKeyAttrs.get(nonKeyAttrName);
            DDBType type = LocalDBUtils.getDataTypeOfAttributeValue(nonKeyAttr);
            if (type == DDBType.N) {
                item.put(nonKeyAttrName, new AttributeValue().withN(LocalDBUtils.validateNumericValue(nonKeyAttr.getN()).toPlainString()));
                continue;
            }
            if (type != DDBType.NS) continue;
            item.put(nonKeyAttrName, new AttributeValue().withNS(LocalDBUtils.validateNumberSet(nonKeyAttr.getNS())));
        }
        long accumulatedSize = baseItemSize = LocalDBUtils.getItemSizeBytes(item);
        List<LocalSecondaryIndex> lsiIndexes = tableInfo.getLSIIndexes();
        if (lsiIndexes != null) {
            for (LocalSecondaryIndex lsi : lsiIndexes) {
                List keySchema = lsi.getKeySchema();
                boolean containsKey = true;
                int lsiSize = 0;
                for (KeySchemaElement keySchemaElement : keySchema) {
                    String attrName = keySchemaElement.getAttributeName();
                    if (!item.containsKey(attrName)) {
                        containsKey = false;
                        break;
                    }
                    lsiSize += attrName.getBytes(LocalDBUtils.UTF8).length;
                    lsiSize = (int)((long)lsiSize + LocalDBUtils.getAttributeValueSizeBytes(item.get(attrName)));
                }
                if (!containsKey) continue;
                if (lsi.getProjection() == null) {
                    accumulatedSize += (long)lsiSize;
                    continue;
                }
                String projectionType = lsi.getProjection().getProjectionType();
                if (ProjectionType.ALL.toString().equals(projectionType)) {
                    accumulatedSize += baseItemSize;
                    continue;
                }
                if (ProjectionType.KEYS_ONLY.toString().equals(projectionType)) {
                    accumulatedSize += (long)lsiSize;
                    continue;
                }
                if (lsi.getProjection().getNonKeyAttributes() == null) continue;
                for (String nonKeyLsi : lsi.getProjection().getNonKeyAttributes()) {
                    if (!item.containsKey(nonKeyLsi)) continue;
                    lsiSize += nonKeyLsi.getBytes(LocalDBUtils.UTF8).length;
                    lsiSize = (int)((long)lsiSize + LocalDBUtils.getAttributeValueSizeBytes(item.get(nonKeyLsi)));
                }
                accumulatedSize += (long)lsiSize;
            }
        }
        if (accumulatedSize > 409600L) {
            throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, LocalDBClientExceptionMessage.ITEM_TOO_BIG.getMessage());
        }
        return keyAttrs;
    }

    void validateExpectedAttribute(String attributeName, ExpectedAttributeValue expectedAttributeValue) {
        if (expectedAttributeValue == null) {
            throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, String.format(LocalDBClientExceptionMessage.INVALID_PARAMETER_VALUE.getMessage(), "ExpectedAttributeValue must not be null for Attribute: " + attributeName));
        }
        if (expectedAttributeValue.isExists() != null && expectedAttributeValue.getComparisonOperator() != null) {
            throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, String.format(LocalDBClientExceptionMessage.INVALID_PARAMETER_VALUE.getMessage(), "Exists and ComparisonOperator cannot be used together for Attribute: " + attributeName));
        }
        if (expectedAttributeValue.getValue() != null && expectedAttributeValue.getAttributeValueList() != null) {
            throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, String.format(LocalDBClientExceptionMessage.INVALID_PARAMETER_VALUE.getMessage(), "Value and AttributeValueList cannot be used together for Attribute: " + attributeName));
        }
        if (expectedAttributeValue.getComparisonOperator() == null && expectedAttributeValue.getAttributeValueList() != null) {
            throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, String.format(LocalDBClientExceptionMessage.INVALID_PARAMETER_VALUE.getMessage(), "AttributeValueList can only be used with a ComparisonOperator for Attribute: " + attributeName));
        }
        if (expectedAttributeValue.isExists() != null && !expectedAttributeValue.isExists().booleanValue() && expectedAttributeValue.getValue() != null) {
            throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, String.format(LocalDBClientExceptionMessage.INVALID_PARAMETER_VALUE.getMessage(), "Value cannot be used when Exists is false for Attribute: " + attributeName));
        }
        if (expectedAttributeValue.getValue() == null && expectedAttributeValue.getAttributeValueList() == null) {
            if (expectedAttributeValue.getComparisonOperator() != null) {
                if (!expectedAttributeValue.getComparisonOperator().equals("NOT_NULL") && !expectedAttributeValue.getComparisonOperator().equals("NULL")) {
                    throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, String.format(LocalDBClientExceptionMessage.INVALID_PARAMETER_VALUE.getMessage(), "Value or AttributeValueList must be used with ComparisonOperator: " + expectedAttributeValue.getComparisonOperator() + " for Attribute: " + attributeName));
                }
            } else {
                if (expectedAttributeValue.isExists() == null) {
                    throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, String.format(LocalDBClientExceptionMessage.INVALID_PARAMETER_VALUE.getMessage(), "Value must be provided when Exists is null for Attribute: " + attributeName));
                }
                if (expectedAttributeValue.isExists().booleanValue()) {
                    throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, String.format(LocalDBClientExceptionMessage.INVALID_PARAMETER_VALUE.getMessage(), "Value must be provided when Exists is true for Attribute: " + attributeName));
                }
            }
        }
    }

    void validateExpectations(Map<String, ExpectedAttributeValue> expected, String conditionalOperatorAsString) {
        if (expected.isEmpty() && conditionalOperatorAsString != null) {
            throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, LocalDBClientExceptionMessage.COND_OP_WITHOUT_FILTER_OR_EXPECTED.getMessage());
        }
        for (Map.Entry<String, ExpectedAttributeValue> entry : expected.entrySet()) {
            this.validateExpectedAttribute(entry.getKey(), entry.getValue());
        }
    }

    public boolean doesItemMatchConditionExpression(Map<String, AttributeValue> item, Expression conditionExpression) {
        if (conditionExpression == null) {
            return true;
        }
        boolean result = false;
        try {
            result = LocalDBUtils.doesItemMatchCondition(item, conditionExpression, this.localDBEnv, this.documentFactory);
        } catch (ExpressionExecutionException eee) {
            ExceptionHandler.processExecutionExceptions(ExceptionHandler.ExpressionExecutionContext.EXPECTED_EXPRESSION, eee, this.awsExceptionFactory);
        }
        return result;
    }

    Mutation createDeleteMutation(Map<String, AttributeValue> oldItem) {
        return oldItem == null ? null : new Mutation(oldItem, NULL_IMAGE);
    }

    ConsumedCapacity computeWriteCapacity(String tableName, Mutation mutation, ReturnConsumedCapacity returnConsumedCapacity, TransactionsEnabledMode transactionsEnabledMode) {
        double singletonUnits;
        if (!COMPUTE_CONSUMED_CAPACITY_TYPES.contains(returnConsumedCapacity)) {
            return null;
        }
        double d = singletonUnits = transactionsEnabledMode == TransactionsEnabledMode.TRANSACTIONS_ENABLED ? 1.0 : 2.0;
        if (mutation == null) {
            ConsumedCapacity singletonCapacity = new ConsumedCapacity().withCapacityUnits(Double.valueOf(singletonUnits)).withTableName(tableName);
            if (returnConsumedCapacity == ReturnConsumedCapacity.INDEXES) {
                singletonCapacity.setTable(new Capacity().withCapacityUnits(Double.valueOf(singletonUnits)));
            }
            return singletonCapacity;
        }
        return this.computeWriteCapacityHelper(tableName, mutation, returnConsumedCapacity, transactionsEnabledMode);
    }

    public void validateIndexKeyAttributeValuesBeforePuttingFinalRecordToDB(TableInfo tableInfo, Map<String, AttributeValue> item, boolean usingUpdateExpression) {
        if (tableInfo.hasGSIs()) {
            List<GlobalSecondaryIndexDescription> gsiDescriptions = tableInfo.getGSIDescriptions();
            for (GlobalSecondaryIndexDescription gsiDescription : gsiDescriptions) {
                this.checkForEmptyAttributeValueInIndexKeysAfterUpdate(item, tableInfo.getGSIHashKey(gsiDescription.getIndexName()), gsiDescription.getIndexName(), usingUpdateExpression);
                if (tableInfo.getGSIRangeKey(gsiDescription.getIndexName()) == null) continue;
                this.checkForEmptyAttributeValueInIndexKeysAfterUpdate(item, tableInfo.getGSIRangeKey(gsiDescription.getIndexName()), gsiDescription.getIndexName(), usingUpdateExpression);
            }
        }
        if (tableInfo.getLSIIndexes() != null && !tableInfo.getLSIIndexes().isEmpty()) {
            List<LocalSecondaryIndex> lsiIndexes = tableInfo.getLSIIndexes();
            for (LocalSecondaryIndex localSecondaryIndex : lsiIndexes) {
                this.checkForEmptyAttributeValueInIndexKeysAfterUpdate(item, tableInfo.getLSIRangeKey(localSecondaryIndex.getIndexName()), localSecondaryIndex.getIndexName(), usingUpdateExpression);
            }
        }
    }

    public void doUpdates(UpdateExpression updateExpression, Map<String, AttributeValue> oldItem, Map<String, AttributeValue> itemChanges, ReturnValue returnValue) {
        if (updateExpression == null) {
            return;
        }
        try {
            AttributeValue existingDocument = new AttributeValue().withM(new LinkedHashMap<String, AttributeValue>(oldItem));
            ExpressionExecutor exprExecutor = new ExpressionExecutor(existingDocument, null, this.localDBEnv, this.documentFactory);
            AttributeValue modifiedDocument = (AttributeValue)exprExecutor.getUserDocNew(updateExpression.getTreeRoot());
            oldItem.clear();
            oldItem.putAll(modifiedDocument.getM());
            AttributeValue updates = null;
            if (ReturnValue.UPDATED_NEW.equals((Object)returnValue)) {
                updates = (AttributeValue)exprExecutor.getUpdatedNewDocument(updateExpression.getTreeRoot(), modifiedDocument);
            } else if (ReturnValue.UPDATED_OLD.equals((Object)returnValue)) {
                ExpressionExecutor exprExecutorOld = new ExpressionExecutor(existingDocument, null, this.localDBEnv, this.documentFactory);
                updates = (AttributeValue)exprExecutorOld.getUpdatedOldDocument(updateExpression.getTreeRoot());
            }
            if (updates != null) {
                itemChanges.putAll(updates.getM());
            }
        } catch (ExpressionExecutionException eee) {
            ExceptionHandler.processExecutionExceptions(ExceptionHandler.ExpressionExecutionContext.UPDATE_EXPRESSION, eee, this.awsExceptionFactory);
        }
    }

    public Map<String, AttributeValue> getReturnedValsFromUpdate(ReturnValue returnVals, Map<String, ?> updatesToMake, Map<String, AttributeValue> oldItem, Map<String, AttributeValue> updatedItem) {
        HashMap<String, AttributeValue> returnedVals = new HashMap<String, AttributeValue>();
        switch (returnVals) {
            case ALL_OLD: {
                if (oldItem == null) {
                    return null;
                }
                returnedVals.putAll(oldItem);
                break;
            }
            case ALL_NEW: {
                returnedVals.putAll(updatedItem);
                break;
            }
            case UPDATED_OLD: {
                if (oldItem == null) {
                    return null;
                }
                for (Map.Entry<String, ?> entry : updatesToMake.entrySet()) {
                    String attr = entry.getKey();
                    if (!oldItem.containsKey(attr)) continue;
                    returnedVals.put(attr, oldItem.get(attr));
                }
                break;
            }
            case UPDATED_NEW: {
                for (Map.Entry<String, ?> entry : updatesToMake.entrySet()) {
                    String attr = entry.getKey();
                    if (!updatedItem.containsKey(attr)) continue;
                    returnedVals.put(attr, updatedItem.get(attr));
                }
                break;
            }
            case NONE: {
                returnedVals = null;
            }
        }
        if (returnedVals != null && returnedVals.isEmpty()) {
            returnedVals = null;
        }
        return returnedVals;
    }

    private ConsumedCapacity computeWriteCapacityHelper(String tableName, Mutation mutation, ReturnConsumedCapacity returnConsumedCapacity, TransactionsEnabledMode transactionsEnabledMode) {
        Map<String, Capacity> gsiCapacities;
        Map<String, Capacity> lsiCapacities;
        TableInfo tableInfo = this.dbAccess.getTableInfo(tableName);
        Map<String, AttributeTransition> transitions = this.createTransitions(mutation);
        if (transitions.containsValue((Object)AttributeTransition.ADDED) || transitions.containsValue((Object)AttributeTransition.DELETED) || transitions.containsValue((Object)AttributeTransition.CHANGED)) {
            lsiCapacities = this.computeLsiCapacity(tableInfo, mutation, transitions, transactionsEnabledMode);
            gsiCapacities = this.computeGsiCapacity(tableInfo, mutation, transitions, transactionsEnabledMode);
        } else {
            lsiCapacities = null;
            gsiCapacities = null;
        }
        double baseTableCapacity = Math.max(this.computeWcu(mutation.getPreImage()), this.computeWcu(mutation.getPostImage()));
        if (TransactionsEnabledMode.TRANSACTIONS_ENABLED == transactionsEnabledMode) {
            baseTableCapacity *= 2.0;
        }
        return this.computeConsumedCapacityGivenIndexCapacities(baseTableCapacity, returnConsumedCapacity, lsiCapacities, gsiCapacities, tableName);
    }

    private Map<String, AttributeTransition> createTransitions(Mutation mutation) {
        Map<String, AttributeValue> preImage = mutation.getPreImage() == null ? Collections.EMPTY_MAP : mutation.getPreImage();
        Map<String, AttributeValue> postImage = mutation.getPostImage() == null ? Collections.EMPTY_MAP : mutation.getPostImage();
        HashSet<String> allKeys = new HashSet<String>(preImage.size() + postImage.size());
        allKeys.addAll(preImage.keySet());
        allKeys.addAll(postImage.keySet());
        HashMap<String, AttributeTransition> result = new HashMap<String, AttributeTransition>(allKeys.size());
        for (String attributeName : allKeys) {
            if (preImage.containsKey(attributeName) && !postImage.containsKey(attributeName)) {
                result.put(attributeName, AttributeTransition.DELETED);
                continue;
            }
            if (!preImage.containsKey(attributeName) && postImage.containsKey(attributeName)) {
                result.put(attributeName, AttributeTransition.ADDED);
                continue;
            }
            Preconditions.checkState(preImage.containsKey(attributeName) && postImage.containsKey(attributeName));
            if (this.attributesEqual(preImage.get(attributeName), postImage.get(attributeName))) {
                result.put(attributeName, AttributeTransition.NONE);
                continue;
            }
            result.put(attributeName, AttributeTransition.CHANGED);
        }
        return result;
    }

    private Map<String, Capacity> computeGsiCapacity(TableInfo tableInfo, Mutation mutation, Map<String, AttributeTransition> transitions, TransactionsEnabledMode transactionsEnabledMode) {
        return this.computeIndexCapacityMap(tableInfo, tableInfo.getGSINames(), mutation, transitions, 0L, transactionsEnabledMode);
    }

    private Map<String, Capacity> computeLsiCapacity(TableInfo tableInfo, Mutation mutation, Map<String, AttributeTransition> transitions, TransactionsEnabledMode transactionsEnabledMode) {
        return this.computeIndexCapacityMap(tableInfo, tableInfo.getLSINames(), mutation, transitions, 100L, transactionsEnabledMode);
    }

    private Map<String, Capacity> computeIndexCapacityMap(TableInfo tableInfo, List<String> indexNames, Mutation mutation, Map<String, AttributeTransition> transitions, long overhead, TransactionsEnabledMode tx) {
        HashMap<String, Capacity> indexCapacity = new HashMap<String, Capacity>(indexNames.size());
        List<String> baseTableKeyNames = tableInfo.getBaseTableKeyNames();
        for (String indexName : indexNames) {
            Double indexConsumedCapacity = this.computeCapacityForIndex(tableInfo.getProjection(indexName), baseTableKeyNames, tableInfo.getIndexKeyNames(indexName), mutation, transitions, overhead);
            if (indexConsumedCapacity == null) continue;
            indexCapacity.put(indexName, new Capacity().withCapacityUnits(Double.valueOf(TransactionsEnabledMode.TRANSACTIONS_ENABLED == tx ? 2.0 * indexConsumedCapacity : indexConsumedCapacity)));
        }
        return indexCapacity.isEmpty() ? null : indexCapacity;
    }

    private Double computeCapacityForIndex(Projection projection, List<String> baseTableKeyNames, List<String> indexKeyNames, Mutation mutation, Map<String, AttributeTransition> transitions, long overhead) {
        Map<String, AttributeTransition> indexTransitions = this.projectTransitions(projection, baseTableKeyNames, indexKeyNames, transitions);
        if (indexTransitions == null) {
            return null;
        }
        ArrayList<String> projectedKeys = new ArrayList<String>(indexTransitions.keySet());
        Map<String, AttributeValue> preImage = LocalDBUtils.projectAttributes(mutation.getPreImage(), projectedKeys);
        Map<String, AttributeValue> postImage = LocalDBUtils.projectAttributes(mutation.getPostImage(), projectedKeys);
        HashSet<AttributeTransition> indexKeyTransitions = new HashSet<AttributeTransition>(indexKeyNames.size());
        for (String string : indexKeyNames) {
            indexKeyTransitions.add(indexTransitions.get(string));
        }
        if (indexKeyTransitions.contains((Object)AttributeTransition.DELETED)) {
            return this.computeWcu(LocalDBUtils.getItemSizeBytes(preImage) + overhead);
        }
        if (indexKeyTransitions.contains((Object)AttributeTransition.ADDED)) {
            return this.computeWcu(LocalDBUtils.getItemSizeBytes(postImage) + overhead);
        }
        if (indexKeyTransitions.contains((Object)AttributeTransition.CHANGED)) {
            return this.computeWcu(LocalDBUtils.getItemSizeBytes(preImage) + overhead) + this.computeWcu(LocalDBUtils.getItemSizeBytes(postImage) + overhead);
        }
        for (Map.Entry entry : indexTransitions.entrySet()) {
            if (!((AttributeTransition)((Object)entry.getValue())).requiresComputingCapacity()) continue;
            Preconditions.checkState(!indexKeyNames.contains(entry.getKey()));
            return Math.max(this.computeWcu(LocalDBUtils.getItemSizeBytes(preImage) + overhead), this.computeWcu(LocalDBUtils.getItemSizeBytes(postImage) + overhead));
        }
        return null;
    }

    private Map<String, AttributeTransition> projectTransitions(Projection projection, List<String> baseTableKeyNames, List<String> indexKeyNames, Map<String, AttributeTransition> transitions) {
        ProjectionType type = ProjectionType.fromValue((String)projection.getProjectionType());
        HashMap<String, AttributeTransition> result = new HashMap<String, AttributeTransition>(transitions.size());
        if (!transitions.keySet().containsAll(indexKeyNames)) {
            return null;
        }
        switch (type) {
            case ALL: {
                return transitions;
            }
            case INCLUDE: {
                for (String nonKeyAttribute : projection.getNonKeyAttributes()) {
                    AttributeTransition transition = transitions.get(nonKeyAttribute);
                    if (transition == null) continue;
                    result.put(nonKeyAttribute, transition);
                }
            }
            case KEYS_ONLY: {
                for (String baseTableKeyName : baseTableKeyNames) {
                    result.put(baseTableKeyName, transitions.get(baseTableKeyName));
                }
                for (String indexKeyName : indexKeyNames) {
                    result.put(indexKeyName, transitions.get(indexKeyName));
                }
                return result;
            }
        }
        throw new IllegalStateException("unreachable");
    }

    private ConsumedCapacity computeConsumedCapacityGivenIndexCapacities(double baseTableWcu, ReturnConsumedCapacity returnConsumedCapacity, Map<String, Capacity> lsiCapacities, Map<String, Capacity> gsiCapacities, String tableName) {
        double indexSum = 0.0;
        if (lsiCapacities != null) {
            for (Capacity lsiCapacityUnits : lsiCapacities.values()) {
                indexSum += lsiCapacityUnits.getCapacityUnits().doubleValue();
            }
        }
        if (gsiCapacities != null) {
            for (Capacity gsiCapacityUnits : gsiCapacities.values()) {
                indexSum += gsiCapacityUnits.getCapacityUnits().doubleValue();
            }
        }
        ConsumedCapacity consumedCapacity = new ConsumedCapacity().withCapacityUnits(Double.valueOf(baseTableWcu + indexSum)).withTableName(tableName);
        if (ReturnConsumedCapacity.INDEXES == returnConsumedCapacity) {
            consumedCapacity.withTable(new Capacity().withCapacityUnits(Double.valueOf(baseTableWcu))).withLocalSecondaryIndexes(lsiCapacities).withGlobalSecondaryIndexes(gsiCapacities);
        }
        return consumedCapacity;
    }

    private double computeWcu(Map<String, AttributeValue> item) {
        return item == null ? 0.0 : this.computeWcu(LocalDBUtils.getItemSizeBytes(item));
    }

    private double computeWcu(long bytes) {
        return Math.max(1.0, Math.ceil((double)bytes / 1024.0));
    }

    boolean itemsEqual(List<String> keys2, Map<String, AttributeValue> preImage, Map<String, AttributeValue> postImage) {
        if (preImage == postImage) {
            return true;
        }
        if (preImage.size() != postImage.size()) {
            return false;
        }
        HashSet<String> allKeys = new HashSet<String>();
        if (keys2 == null || keys2.isEmpty()) {
            allKeys.addAll(preImage.keySet());
            allKeys.addAll(postImage.keySet());
        } else {
            allKeys.addAll(keys2);
        }
        for (String key : allKeys) {
            AttributeValue postAv;
            AttributeValue preAv = preImage.get(key);
            if (this.attributesEqual(preAv, postAv = postImage.get(key))) continue;
            return false;
        }
        return true;
    }

    private boolean attributesEqual(AttributeValue val1, AttributeValue val2) {
        DDBType av2Type;
        if (val1 == val2 || val1 == null && val2 == null) {
            return true;
        }
        if (val1 == null && val2 != null) {
            return false;
        }
        if (val1 != null && val2 == null) {
            return false;
        }
        if (this.attrEmpty(val1) && this.attrEmpty(val2)) {
            return true;
        }
        DDBType av1Type = LocalDBUtils.getDataTypeOfAttributeValue(val1);
        if (av1Type != (av2Type = LocalDBUtils.getDataTypeOfAttributeValue(val2))) {
            return false;
        }
        switch (av1Type) {
            case S: {
                return val1.getS().equals(val2.getS());
            }
            case N: {
                BigDecimal bigDecimal2;
                BigDecimal bigDecimal1;
                try {
                    bigDecimal1 = new BigDecimal(val1.getN());
                    bigDecimal2 = new BigDecimal(val2.getN());
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("one or both of the numbers was malformed", e);
                }
                Preconditions.checkState(bigDecimal1 != null && bigDecimal2 != null);
                return bigDecimal1.compareTo(bigDecimal2) == 0;
            }
            case B: {
                if (val1.getB().hasArray() && !val2.getB().hasArray() || !val1.getB().hasArray() && val2.getB().hasArray()) {
                    return false;
                }
                Preconditions.checkState(val1.getB().hasArray() && val2.getB().hasArray());
                byte[] val1ByteArray = val1.getB().array();
                byte[] val2ByteArray = val2.getB().array();
                return UnsignedByteArrayComparator.compareUnsignedByteArrays(val1ByteArray, val2ByteArray) == 0;
            }
            case NS: {
                if (val1.getNS().size() != val2.getNS().size()) {
                    return false;
                }
                List<BigDecimal> list1 = this.bigDecimals(val1.getNS());
                List<BigDecimal> list2 = this.bigDecimals(val2.getNS());
                Collections.sort(list1);
                Collections.sort(list2);
                for (int i = 0; i < val1.getNS().size(); ++i) {
                    if (list1.get(i).compareTo(list2.get(i)) == 0) continue;
                    return false;
                }
                return true;
            }
            case BS: {
                if (val1.getBS().size() != val2.getBS().size()) {
                    return false;
                }
                List<byte[]> val1List = this.convertByteBufferToByteArrayList(val1.getBS());
                List<byte[]> val2List = this.convertByteBufferToByteArrayList(val2.getBS());
                Collections.sort(val1List, UnsignedByteArrayComparator.SINGLETON);
                Collections.sort(val2List, UnsignedByteArrayComparator.SINGLETON);
                for (int i = 0; i < val1.getBS().size(); ++i) {
                    if (UnsignedByteArrayComparator.SINGLETON.compare(val1List.get(i), val2List.get(i)) == 0) continue;
                    return false;
                }
                return true;
            }
            case SS: {
                return Sets.newHashSet(val1.getSS()).equals(Sets.newHashSet(val2.getSS()));
            }
            case BOOL: {
                return val1.getBOOL().equals(val2.getBOOL());
            }
            case NULL: {
                return val1.getNULL().equals(val2.getNULL());
            }
            case L: {
                List<AttributeValue> list1 = val1.getL();
                List<AttributeValue> list2 = val2.getL();
                if (list1.size() != list2.size()) {
                    return false;
                }
                for (int i = 0; i < list1.size(); ++i) {
                    if (this.attributesEqual(list1.get(i), list2.get(i))) continue;
                    return false;
                }
                return true;
            }
            case M: {
                Map<String, AttributeValue> map1 = val1.getM();
                Map<String, AttributeValue> map2 = val2.getM();
                if (map1.size() != map2.size()) {
                    return false;
                }
                if (!map2.keySet().containsAll(map1.keySet())) {
                    return false;
                }
                for (String key : map1.keySet()) {
                    if (this.attributesEqual(map1.get(key), map2.get(key))) continue;
                    return false;
                }
                return true;
            }
        }
        throw new IllegalStateException("should not get here");
    }

    private List<BigDecimal> bigDecimals(List<String> ns) {
        ArrayList<BigDecimal> result = new ArrayList<BigDecimal>();
        if (ns == null) {
            return result;
        }
        for (String n : ns) {
            try {
                result.add(new BigDecimal(n));
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Invalid number representation encountered", e);
            }
        }
        return result;
    }

    private List<byte[]> convertByteBufferToByteArrayList(Collection<ByteBuffer> bb) {
        if (bb == null) {
            return null;
        }
        ArrayList<byte[]> byteArrayList = new ArrayList<byte[]>(bb.size());
        for (ByteBuffer localbb : bb) {
            if (localbb == null) {
                byteArrayList.add(null);
                continue;
            }
            byteArrayList.add(localbb.array());
        }
        return byteArrayList;
    }

    private boolean attrEmpty(AttributeValue av) {
        return av.getBOOL() == null && av.getNULL() == null && av.getB() == null && av.getBS() == null && av.getL() == null && av.getM() == null && av.getN() == null && av.getNS() == null && av.getS() == null && av.getSS() == null;
    }

    ReturnValuesOnConditionCheckFailure getReturnValuesOnConditionCheckFailure(String returnValuesOnConditionCheckFailure) {
        if (returnValuesOnConditionCheckFailure == null) {
            returnValuesOnConditionCheckFailure = ReturnValuesOnConditionCheckFailure.NONE.toString();
        }
        return ReturnValuesOnConditionCheckFailure.fromValue((String)returnValuesOnConditionCheckFailure);
    }

    private static enum AttributeTransition {
        ADDED(true),
        CHANGED(true),
        DELETED(true),
        NONE(false);

        private final boolean requiresComputingCapacity;

        private AttributeTransition(boolean b) {
            this.requiresComputingCapacity = b;
        }

        public boolean requiresComputingCapacity() {
            return this.requiresComputingCapacity;
        }
    }
}

