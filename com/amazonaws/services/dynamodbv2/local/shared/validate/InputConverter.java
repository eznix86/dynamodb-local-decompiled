/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazonaws.services.dynamodbv2.local.shared.validate;

import com.amazonaws.services.dynamodbv2.datamodel.DocPath;
import com.amazonaws.services.dynamodbv2.datamodel.DocumentFactory;
import com.amazonaws.services.dynamodbv2.datamodel.DocumentNode;
import com.amazonaws.services.dynamodbv2.datamodel.DocumentNodeType;
import com.amazonaws.services.dynamodbv2.dbenv.DbEnv;
import com.amazonaws.services.dynamodbv2.local.shared.helpers.ExpressionUtils;
import com.amazonaws.services.dynamodbv2.local.shared.validate.ErrorFactory;
import com.amazonaws.services.dynamodbv2.local.shared.validate.RangeQueryExpressionsWrapper;
import com.amazonaws.services.dynamodbv2.local.shared.validate.UpdateItemExpressionsWrapper;
import com.amazonaws.services.dynamodbv2.parser.DynamoDbParser;
import com.amazonaws.services.dynamodbv2.rr.ExpressionWrapper;
import com.amazonaws.services.dynamodbv2.rr.ExpressionsWrapperBase;
import com.amazonaws.services.dynamodbv2.rr.ProjectionExpressionWrapper;
import com.amazonaws.services.dynamodbv2.rr.UpdateExpressionWrapper;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public abstract class InputConverter<InternalAttributeValue extends DocumentNode, ExternalAttributeValue, InternalAttributeValueList, InternalAttributeValueMap, InternalAttributeName, InternalAttributeValueUpdate, ExternalAttributeValueUpdate, InternalExpectedAttributeValue, ExternalExpectedAttributeValue, InternalCondition, ExternalCondition, InternalKeysAndAttributes, ExternalKeysAndAttributes, InternalWriteRequest, ExternalWriteRequest, InternalAction, InternalComparisonOperator, InternalConditionalOperator, InternalSelect, InternalReturnConsumedCapacity, InternalTableName> {
    public final Set<DocumentNodeType> TypesSupportingAttributeAddUpdate = Collections.unmodifiableSet(new HashSet<DocumentNodeType>(Arrays.asList(DocumentNodeType.NUMBER, DocumentNodeType.STRING_SET, DocumentNodeType.NUMBER_SET, DocumentNodeType.BINARY_SET, DocumentNodeType.LIST)));
    public final Set<DocumentNodeType> TypesSupportingAttributeDeleteWithValueUpdate = Collections.unmodifiableSet(new HashSet<DocumentNodeType>(Arrays.asList(DocumentNodeType.STRING_SET, DocumentNodeType.NUMBER_SET, DocumentNodeType.BINARY_SET)));
    protected final int maxItemSize;
    protected final boolean isDocumentSupportEnabled;
    protected final DbEnv dbEnv;
    protected final ErrorFactory errorFactory;
    protected final DocumentFactory documentFactory;
    private final int maxKeyAttributeNameSize;
    private final int maxBatchGetRequestCount;
    private final int maxBatchWriteRequestCount;
    private final int maxExpressionSubstitutionMapSize;

    protected InputConverter(DbEnv dbEnv, ErrorFactory errorFactory, DocumentFactory documentFactory, boolean isDocumentSupportEnabled, int maxItemSize, int maxKeyAttributeNameSize, int maxBatchGetRequestCount, int maxBatchWriteRequestCount, int maxExpressionParameterMapSize) {
        this.dbEnv = dbEnv;
        this.maxItemSize = maxItemSize;
        this.isDocumentSupportEnabled = isDocumentSupportEnabled;
        this.maxKeyAttributeNameSize = maxKeyAttributeNameSize;
        this.errorFactory = errorFactory;
        this.documentFactory = documentFactory;
        this.maxBatchGetRequestCount = maxBatchGetRequestCount;
        this.maxBatchWriteRequestCount = maxBatchWriteRequestCount;
        this.maxExpressionSubstitutionMapSize = maxExpressionParameterMapSize;
    }

    private static String convertToString(Collection objs) {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (objs != null && !objs.isEmpty()) {
            Iterator objsIt = objs.iterator();
            sb.append(objsIt.next());
            while (objsIt.hasNext()) {
                sb.append(", ").append(objsIt.next());
            }
            sb.append("}");
        }
        return sb.toString();
    }

    public List<InternalAttributeValue> externalToInternalAttributeValues(List<ExternalAttributeValue> attrVals) {
        if (attrVals == null) {
            return new ArrayList(0);
        }
        ArrayList<InternalAttributeValue> outputAttrs = new ArrayList<InternalAttributeValue>(attrVals.size());
        for (ExternalAttributeValue attrVal : attrVals) {
            outputAttrs.add(this.externalToInternalAttributeValue(attrVal, false));
        }
        return outputAttrs;
    }

    public InternalAttributeValueMap externalToInternalAttributes(Map<String, ExternalAttributeValue> attrs) {
        return this.externalToInternalAttributes(attrs, 0);
    }

    private InternalAttributeValueMap externalToInternalAttributes(Map<String, ExternalAttributeValue> attrs, int depth) {
        if (attrs == null) {
            this.errorFactory.ITEM_CONTAINS_NULL_ATTRVALUE.throwAsException();
        }
        if (!this.isValidNestedLevel(depth)) {
            this.errorFactory.ITEM_NESTING_LEVELS_LIMIT_EXCEEDED.throwAsException("Attributes in the item have nested levels beyond supported limit");
        }
        InternalAttributeValueMap outputAttrs = this.newAttrValueMap(depth, attrs.size());
        int nextDepth = depth + 1;
        int runningItemSizeCount = 0;
        for (String attrName : attrs.keySet()) {
            ExternalAttributeValue attr = attrs.get(attrName);
            if (attr == null) {
                this.errorFactory.ITEM_CONTAINS_NULL_ATTRVALUE.throwAsException(attrName + " is null");
            }
            InternalAttributeValue attributeValue = this.externalToInternalAttributeValue(attrs.get(attrName), nextDepth, false);
            InternalAttributeName attrNameObj = this.newAttributeName(attrName);
            runningItemSizeCount += this.getLengthInUTF8Bytes(attrNameObj);
            if ((runningItemSizeCount += this.getAttributeSizeInBytes(attributeValue)) > this.maxItemSize) {
                this.errorFactory.ITEM_TOO_LARGE.throwAsException();
            }
            this.putAttrValue(outputAttrs, attrNameObj, attributeValue);
        }
        return outputAttrs;
    }

    private InternalAttributeValueList externalToInternalAttributes(List<ExternalAttributeValue> attrs, int depth) {
        if (attrs == null) {
            this.errorFactory.ITEM_CONTAINS_NULL_ATTRVALUE.throwAsException();
        }
        if (!this.isValidNestedLevel(depth)) {
            this.errorFactory.ITEM_NESTING_LEVELS_LIMIT_EXCEEDED.throwAsException("Attributes in the item have nested levels beyond supported limit");
        }
        InternalAttributeValueList internalAttrs = this.newAttrValueList(depth, attrs.size());
        int nextDepth = depth + 1;
        int runningItemSizeCount = 0;
        for (ExternalAttributeValue attr : attrs) {
            InternalAttributeValue internalAttr = this.externalToInternalAttributeValue(attr, nextDepth, false);
            if ((runningItemSizeCount += this.getAttributeSizeInBytes(internalAttr)) > this.maxItemSize) {
                this.errorFactory.ITEM_TOO_LARGE.throwAsException();
            }
            this.addAttrValue(internalAttrs, internalAttr);
        }
        return internalAttrs;
    }

    public Map<InternalAttributeName, InternalAttributeValueUpdate> externalToInternalAttributeUpdates(Map<String, ExternalAttributeValueUpdate> attrUpdates) {
        if (attrUpdates == null) {
            return new HashMap(0);
        }
        HashMap<InternalAttributeName, InternalAttributeValueUpdate> outputUpdates = new HashMap<InternalAttributeName, InternalAttributeValueUpdate>(attrUpdates.size());
        int runningItemSizeCount = 0;
        for (String attrName : attrUpdates.keySet()) {
            InternalAttributeValueUpdate update = this.externalToInternalAttributeValueUpdate(attrName, attrUpdates.get(attrName));
            InternalAttributeName attrNameObj = this.newAttributeName(attrName);
            InternalAttributeValue value = this.getUpdateValueInternal(update);
            if (!this.isDelete(this.getUpdateActionInternal(update))) {
                runningItemSizeCount += this.getLengthInUTF8Bytes(attrNameObj);
                if ((runningItemSizeCount += this.getAttributeSizeInBytes(value)) > this.maxItemSize) {
                    this.errorFactory.ITEM_UPD_TOO_LARGE.throwAsException();
                }
            }
            outputUpdates.put(attrNameObj, update);
        }
        return outputUpdates;
    }

    public Map<InternalAttributeName, InternalExpectedAttributeValue> externalToInternalExpectedAttributes(Map<String, ExternalExpectedAttributeValue> expectedAttrs, int maxItemSize) {
        String TRACE_HEADER = "externalToInternalExpectedAttributes";
        if (expectedAttrs == null) {
            return new HashMap(0);
        }
        int runningItemSizeCount = 0;
        HashMap<InternalAttributeName, InternalExpectedAttributeValue> outputAttrs = new HashMap<InternalAttributeName, InternalExpectedAttributeValue>(expectedAttrs.size());
        for (String attrName : expectedAttrs.keySet()) {
            ExternalExpectedAttributeValue evExternal = expectedAttrs.get(attrName);
            this.validateExpectedAttribute(attrName, evExternal);
            Object av = this.getExpectedValueExternal(evExternal) != null ? (Object)this.externalToInternalAttributeValue(this.getExpectedValueExternal(evExternal), false) : null;
            List<InternalAttributeValue> avList = this.getExpectedAttributeValueList(evExternal) == null ? null : this.externalToInternalAttributeValues(this.getExpectedAttributeValueList(evExternal));
            InternalAttributeName attrNameObj = this.newAttributeName(attrName);
            runningItemSizeCount += this.getLengthInUTF8Bytes(attrNameObj);
            this.dbEnv.dbAssert(av == null || avList == null, "externalToInternalExpectedAttributes", "av and avList cannot both have values", "av", av, "avList", avList);
            if (av != null) {
                runningItemSizeCount += this.getAttributeSizeInBytes(av);
            } else if (avList != null) {
                for (DocumentNode attributeValue : avList) {
                    this.dbEnv.dbAssert(attributeValue != null, "externalToInternalExpectedAttributes", "attributeValue cannot be null", "avList", avList);
                    runningItemSizeCount += this.getAttributeSizeInBytes(attributeValue);
                }
            }
            if (runningItemSizeCount > maxItemSize) {
                this.errorFactory.ITEM_TOO_LARGE.throwAsException();
            }
            InternalComparisonOperator op = null;
            if (this.getExpectedComparisonOperator(evExternal) != null) {
                op = this.newComparisonOperator(this.getExpectedComparisonOperator(evExternal));
                List<Object> comparisonArguments = null;
                comparisonArguments = av != null ? Collections.singletonList(av) : avList;
                this.validateArgumentsForComparisonOperator(op, comparisonArguments);
            }
            Boolean exists = this.isExists(evExternal);
            InternalExpectedAttributeValue ev = this.newExpected(av, exists, avList, op);
            outputAttrs.put(attrNameObj, ev);
        }
        return outputAttrs;
    }

    private InternalCondition externalToInternalCondition(ExternalCondition condition) {
        if (condition == null) {
            return null;
        }
        InternalComparisonOperator comparisonOperator = this.newComparisonOperator(this.getConditionComparisonOperator(condition));
        List<InternalAttributeValue> attributeValues = this.externalToInternalAttributeValues(this.getConditionAttributeValueList(condition));
        this.validateArgumentsForComparisonOperator(comparisonOperator, attributeValues);
        return this.newCondition(comparisonOperator, attributeValues);
    }

    public Map<InternalAttributeName, InternalCondition> externalToInternalConditions(Map<String, ExternalCondition> externalConditions) {
        if (externalConditions == null) {
            return null;
        }
        HashMap<InternalAttributeName, InternalCondition> conditions = new HashMap<InternalAttributeName, InternalCondition>(externalConditions.size());
        for (Map.Entry<String, ExternalCondition> entry : externalConditions.entrySet()) {
            String attributeName = entry.getKey();
            ExternalCondition externalCondition = entry.getValue();
            if (attributeName == null) {
                this.errorFactory.EMPTY_ATTRIBUTE_NAME.throwAsException();
            }
            if (externalCondition == null) {
                this.errorFactory.FILTER_CONDITION_CANNOT_BE_NULL.throwAsException(attributeName);
            }
            InternalComparisonOperator comparisonOperator = this.newComparisonOperator(this.getConditionComparisonOperator(externalCondition));
            List<InternalAttributeValue> attributeValues = this.externalToInternalAttributeValues(this.getConditionAttributeValueList(externalCondition));
            this.validateArgumentsForComparisonOperator(comparisonOperator, attributeValues);
            InternalCondition condition = this.newCondition(comparisonOperator, attributeValues);
            conditions.put(this.newAttributeName(attributeName), condition);
        }
        return conditions;
    }

    private Set<InternalAttributeName> getAttributeNames(List<String> attrNames) {
        if (attrNames == null) {
            return null;
        }
        HashSet<InternalAttributeName> output = new HashSet<InternalAttributeName>(attrNames.size());
        for (String an : attrNames) {
            boolean added = output.add(this.newAttributeName(an));
            if (added) continue;
            this.errorFactory.INVALID_PARAMETER_VALUE.throwAsException("Duplicate value in attribute name: " + an);
        }
        return output;
    }

    private void validateExpectedAttribute(String attributeName, ExternalExpectedAttributeValue expectedAttributeValue) throws RuntimeException {
        if (expectedAttributeValue == null) {
            this.errorFactory.INVALID_PARAMETER_VALUE.throwAsException("ExpectedAttributeValue must not be null for Attribute: " + attributeName);
        }
        if (this.isExists(expectedAttributeValue) != null && this.getExpectedComparisonOperator(expectedAttributeValue) != null) {
            this.errorFactory.INVALID_PARAMETER_VALUE.throwAsException("Exists and ComparisonOperator cannot be used together for Attribute: " + attributeName);
        }
        if (this.getExpectedValueExternal(expectedAttributeValue) != null && this.getExpectedAttributeValueList(expectedAttributeValue) != null) {
            this.errorFactory.INVALID_PARAMETER_VALUE.throwAsException("Value and AttributeValueList cannot be used together for Attribute: " + attributeName);
        }
        if (this.getExpectedComparisonOperator(expectedAttributeValue) == null && this.getExpectedAttributeValueList(expectedAttributeValue) != null) {
            this.errorFactory.INVALID_PARAMETER_VALUE.throwAsException("AttributeValueList can only be used with a ComparisonOperator for Attribute: " + attributeName);
        }
        if (this.isExists(expectedAttributeValue) != null && !this.isExists(expectedAttributeValue).booleanValue() && this.getExpectedValueExternal(expectedAttributeValue) != null) {
            this.errorFactory.INVALID_PARAMETER_VALUE.throwAsException("Value cannot be used when Exists is false for Attribute: " + attributeName);
        }
        if (this.getExpectedValueExternal(expectedAttributeValue) == null && this.getExpectedAttributeValueList(expectedAttributeValue) == null) {
            if (this.getExpectedComparisonOperator(expectedAttributeValue) != null) {
                if (!this.getExpectedComparisonOperator(expectedAttributeValue).equals("NOT_NULL") && !this.getExpectedComparisonOperator(expectedAttributeValue).equals("NULL")) {
                    this.errorFactory.INVALID_PARAMETER_VALUE.throwAsException("Value or AttributeValueList must be used with ComparisonOperator: " + this.getExpectedComparisonOperator(expectedAttributeValue) + " for Attribute: " + attributeName);
                }
            } else if (this.isExists(expectedAttributeValue) == null) {
                this.errorFactory.INVALID_PARAMETER_VALUE.throwAsException("Value must be provided when Exists is null for Attribute: " + attributeName);
            } else if (this.isExists(expectedAttributeValue).booleanValue()) {
                this.errorFactory.INVALID_PARAMETER_VALUE.throwAsException("Value must be provided when Exists is true for Attribute: " + attributeName);
            }
        }
    }

    public InternalAttributeValue externalToInternalAttributeValue(ExternalAttributeValue externalAttributeValue, boolean nullable) {
        return this.externalToInternalAttributeValue(externalAttributeValue, 0, nullable);
    }

    private InternalAttributeValue externalToInternalAttributeValue(ExternalAttributeValue externalAttributeValue, int level, boolean nullable) {
        InternalAttributeValue retVal = null;
        if (externalAttributeValue == null) {
            if (!nullable) {
                this.errorFactory.ITEM_CONTAINS_NULL_ATTRVALUE.throwAsException();
            } else {
                return retVal;
            }
        }
        int elementCount = 0;
        if (this.getS(externalAttributeValue) != null) {
            ++elementCount;
            retVal = this.newS(this.getS(externalAttributeValue));
        }
        if (this.getSS(externalAttributeValue) != null) {
            ++elementCount;
            retVal = this.newSS(this.getSS(externalAttributeValue));
        }
        if (this.getN(externalAttributeValue) != null) {
            ++elementCount;
            retVal = this.newN(this.getN(externalAttributeValue));
        }
        if (this.getNS(externalAttributeValue) != null) {
            ++elementCount;
            retVal = this.newNS(this.getNS(externalAttributeValue));
        }
        if (this.getB(externalAttributeValue) != null) {
            ++elementCount;
            retVal = this.newB(this.getB(externalAttributeValue));
        }
        if (this.getBS(externalAttributeValue) != null) {
            ++elementCount;
            retVal = this.newBS(this.getBS(externalAttributeValue));
        }
        if (this.getM(externalAttributeValue) != null && this.isDocumentSupportEnabled) {
            ++elementCount;
            retVal = this.newM(this.externalToInternalAttributes(this.getM(externalAttributeValue), level));
        }
        if (this.getL(externalAttributeValue) != null && this.isDocumentSupportEnabled) {
            ++elementCount;
            retVal = this.newL(this.externalToInternalAttributes(this.getL(externalAttributeValue), level));
        }
        if (this.isBOOL(externalAttributeValue) != null && this.isDocumentSupportEnabled) {
            ++elementCount;
            retVal = this.newBoolean(this.isBOOL(externalAttributeValue));
        }
        if (this.isNULL(externalAttributeValue) != null && this.isDocumentSupportEnabled) {
            ++elementCount;
            if (this.isNULL(externalAttributeValue).booleanValue()) {
                retVal = this.newNull();
            } else {
                this.errorFactory.INVALID_PARAMETER_VALUE.throwAsException("Null attribute value types must have the value of true");
            }
        }
        if (elementCount == 1) {
            return retVal;
        }
        if (elementCount == 0) {
            this.errorFactory.EMPTY_ATTRIBUTE_VALUE.throwAsException();
        } else {
            this.errorFactory.MULTI_ATTRIBUTE_VALUE.throwAsException();
        }
        return null;
    }

    private InternalAttributeValueUpdate externalToInternalAttributeValueUpdate(String attrName, ExternalAttributeValueUpdate update) {
        String getActionStr = null;
        if (update == null) {
            this.errorFactory.NULL_ATTRIBUTE_UPDATE_VALUE.throwAsException(attrName + " is null");
        }
        getActionStr = this.getUpdateActionExternal(update) == null ? "PUT" : this.getUpdateActionExternal(update);
        InternalAction action = this.newAction(getActionStr);
        InternalAttributeValue av = null;
        if (this.getUpdateValueExternal(update) != null) {
            av = this.externalToInternalAttributeValue(this.getUpdateValueExternal(update), true);
        }
        this.validateAttributeUpdates(action, av);
        return this.newUpdate(av, action);
    }

    private void validateAttributeUpdates(InternalAction action, InternalAttributeValue value) {
        if (value == null && !this.isDelete(action)) {
            this.errorFactory.INVALID_PARAMETER_VALUE.throwAsException("Only DELETE action is allowed when no attribute value is specified");
        } else {
            DocumentNodeType type;
            DocumentNodeType documentNodeType = type = value != null ? this.getType(value) : null;
            if (this.isDelete(action) && value != null && !this.TypesSupportingAttributeDeleteWithValueUpdate.contains((Object)type)) {
                this.errorFactory.INVALID_PARAMETER_VALUE.throwAsException("DELETE action with value is not supported for the type " + type);
            } else if (this.isAdd(action) && !this.TypesSupportingAttributeAddUpdate.contains((Object)type)) {
                this.errorFactory.INVALID_PARAMETER_VALUE.throwAsException("ADD action is not supported for the type " + type);
            }
        }
    }

    public Map<InternalAttributeName, InternalCondition> externalToInternalFilter(Map<String, ExternalCondition> filters) {
        if (filters == null) {
            return null;
        }
        HashMap<InternalAttributeName, InternalCondition> result = new HashMap<InternalAttributeName, InternalCondition>(filters.size());
        for (String attrName : filters.keySet()) {
            InternalAttributeName attributeName = this.newAttributeName(attrName);
            ExternalCondition condition = filters.get(attrName);
            if (condition == null) {
                this.errorFactory.FILTER_CONDITION_CANNOT_BE_NULL.throwAsException(attrName);
            }
            result.put(attributeName, this.externalToInternalCondition(condition));
        }
        return result;
    }

    public InternalSelect externalToInternalSelect(String externalSelect) {
        if (externalSelect != null) {
            return this.newSelect(externalSelect);
        }
        return null;
    }

    public InternalConditionalOperator externalToInternalConditionalOperator(String externalConditionalOperator, int dependencySize) {
        if (externalConditionalOperator == null) {
            return this.newConditionalOperator("AND");
        }
        if (dependencySize == 0) {
            this.errorFactory.COND_OP_WITHOUT_FILTER_OR_EXPECTED.throwAsException();
        }
        if (dependencySize == 1) {
            this.errorFactory.COND_OP_WITH_ONE_ELEMENT.throwAsException();
        }
        return this.newConditionalOperator(externalConditionalOperator);
    }

    public InternalReturnConsumedCapacity externalToInternalRCC(String externalRCC) {
        InternalReturnConsumedCapacity rCC = null;
        rCC = externalRCC != null ? (InternalReturnConsumedCapacity)this.newReturnConsumedCapacity(externalRCC) : (InternalReturnConsumedCapacity)this.newReturnConsumedCapacity("NONE");
        return rCC;
    }

    public ProjectionExpressionWrapper externalToInternalProjectionExpression(String projectionExpressionString, Map<String, String> expressionAttributeNames) {
        ProjectionExpressionWrapper projectionExpressionWrapper = null;
        if (projectionExpressionString != null && this.isDocumentSupportEnabled) {
            this.validateExpressionAttributeNames(expressionAttributeNames);
            Map<String, Integer> expressionAttributeNamesSizes = this.getExpressionAttributeNamesSizes(expressionAttributeNames);
            this.validateSubstitutionMapSizes(expressionAttributeNamesSizes, null);
            projectionExpressionWrapper = this.parseProjectionExpression(projectionExpressionString, expressionAttributeNames);
            this.validateProjectionExpressionCombinedSize(projectionExpressionWrapper, projectionExpressionString.length(), expressionAttributeNamesSizes);
            this.validateAllNameSubstitutionsUsed(expressionAttributeNames, Collections.singletonList(projectionExpressionWrapper.getNameParameterUsage().keySet()));
        }
        return projectionExpressionWrapper;
    }

    public ExpressionWrapper externalToInternalConditionExpression(String conditionExpressionString, Map<String, String> expressionAttributeNames, Map<String, ExternalAttributeValue> expressionAttributeValues) {
        ExpressionWrapper conditionExpressionWrapper = null;
        if (conditionExpressionString != null && this.isDocumentSupportEnabled) {
            this.validateExpressionAttributeNames(expressionAttributeNames);
            this.validateExpressionAttributeValues(expressionAttributeValues);
            Map<String, DocumentNode> internalExpressionAttributeValues = this.externalToInternalExpressionAttributeValues(expressionAttributeValues);
            Map<String, Integer> expressionAttributeNamesSizes = this.getExpressionAttributeNamesSizes(expressionAttributeNames);
            Map<String, Integer> expressionAttributeValuesSizes = this.getExpressionAttributeValuesSizes(internalExpressionAttributeValues);
            this.validateSubstitutionMapSizes(expressionAttributeNamesSizes, expressionAttributeValuesSizes);
            conditionExpressionWrapper = this.parseConditionExpression(conditionExpressionString, expressionAttributeNames, internalExpressionAttributeValues);
            this.validateConditionExpressionCombinedSize(conditionExpressionWrapper, conditionExpressionString.length(), expressionAttributeNamesSizes, expressionAttributeValuesSizes);
            this.validateAllNameSubstitutionsUsed(expressionAttributeNames, Collections.singletonList(conditionExpressionWrapper.getNameParameterUsage().keySet()));
            this.validateAllValueSubstitutionsUsed(internalExpressionAttributeValues, Collections.singletonList(conditionExpressionWrapper.getValueParameterUsage().keySet()));
        }
        return conditionExpressionWrapper;
    }

    public RangeQueryExpressionsWrapper externalToInternalExpressions(String filterExpressionString, String projectionExpressionString, String keyConditionExpressionString, Map<String, String> expressionAttributeNames, Map<String, ExternalAttributeValue> expressionAttributeValues) {
        RangeQueryExpressionsWrapper expressionsWrapper = null;
        if ((filterExpressionString != null || projectionExpressionString != null || keyConditionExpressionString != null) && this.isDocumentSupportEnabled) {
            this.validateExpressionAttributeNames(expressionAttributeNames);
            this.validateExpressionAttributeValues(expressionAttributeValues);
            Map<String, DocumentNode> internalExpressionAttributeValues = this.externalToInternalExpressionAttributeValues(expressionAttributeValues);
            Map<String, Integer> expressionAttributeNamesSizes = this.getExpressionAttributeNamesSizes(expressionAttributeNames);
            Map<String, Integer> expressionAttributeValuesSizes = this.getExpressionAttributeValuesSizes(internalExpressionAttributeValues);
            this.validateSubstitutionMapSizes(expressionAttributeNamesSizes, expressionAttributeValuesSizes);
            expressionsWrapper = new RangeQueryExpressionsWrapper();
            ArrayList<Set<String>> exprAttrNamesUsed = new ArrayList<Set<String>>(2);
            ArrayList<Set<String>> exprAttrValuesUsed = new ArrayList<Set<String>>(2);
            if (filterExpressionString != null) {
                ExpressionWrapper filterExpressionWrapper = this.parseFilterExpression(filterExpressionString, expressionAttributeNames, internalExpressionAttributeValues);
                expressionsWrapper.setFilterExpressionWrapper(filterExpressionWrapper);
                exprAttrNamesUsed.add(filterExpressionWrapper.getNameParameterUsage().keySet());
                exprAttrValuesUsed.add(filterExpressionWrapper.getValueParameterUsage().keySet());
                this.validateFilterExpressionCombinedSize(filterExpressionWrapper, filterExpressionString.length(), expressionAttributeNamesSizes, expressionAttributeValuesSizes);
            }
            if (projectionExpressionString != null) {
                ProjectionExpressionWrapper projectionExpressionWrapper = this.parseProjectionExpression(projectionExpressionString, expressionAttributeNames);
                expressionsWrapper.setProjectionExpressionWrapper(projectionExpressionWrapper);
                exprAttrNamesUsed.add(projectionExpressionWrapper.getNameParameterUsage().keySet());
                this.validateProjectionExpressionCombinedSize(projectionExpressionWrapper, projectionExpressionString.length(), expressionAttributeNamesSizes);
            }
            if (keyConditionExpressionString != null) {
                ExpressionWrapper keyConditionExpressionWrapper = this.parseKeyConditionExpression(keyConditionExpressionString, expressionAttributeNames, internalExpressionAttributeValues);
                expressionsWrapper.setKeyConditionExpressionWrapper(keyConditionExpressionWrapper);
                exprAttrNamesUsed.add(keyConditionExpressionWrapper.getNameParameterUsage().keySet());
                exprAttrValuesUsed.add(keyConditionExpressionWrapper.getValueParameterUsage().keySet());
                this.validateKeyConditionExpressionCombinedSize(keyConditionExpressionWrapper, keyConditionExpressionString.length(), expressionAttributeNamesSizes, expressionAttributeValuesSizes);
            }
            this.validateAllNameSubstitutionsUsed(expressionAttributeNames, exprAttrNamesUsed);
            this.validateAllValueSubstitutionsUsed(internalExpressionAttributeValues, exprAttrValuesUsed);
        }
        return expressionsWrapper;
    }

    public UpdateItemExpressionsWrapper externalToInternalUpdateAndConditionExpressions(String updateExpressionString, String conditionExpressionString, Map<String, String> expressionAttributeNames, Map<String, ExternalAttributeValue> expressionAttributeValues) {
        UpdateItemExpressionsWrapper expressionsWrapper = null;
        if ((updateExpressionString != null || conditionExpressionString != null) && this.isDocumentSupportEnabled) {
            this.validateExpressionAttributeNames(expressionAttributeNames);
            this.validateExpressionAttributeValues(expressionAttributeValues);
            Map<String, DocumentNode> internalExpressionAttributeValues = this.externalToInternalExpressionAttributeValues(expressionAttributeValues);
            Map<String, Integer> expressionAttributeNamesSizes = this.getExpressionAttributeNamesSizes(expressionAttributeNames);
            Map<String, Integer> expressionAttributeValuesSizes = this.getExpressionAttributeValuesSizes(internalExpressionAttributeValues);
            this.validateSubstitutionMapSizes(expressionAttributeNamesSizes, expressionAttributeValuesSizes);
            expressionsWrapper = new UpdateItemExpressionsWrapper();
            UpdateExpressionWrapper updateExpressionWrapper = null;
            ExpressionWrapper conditionExpressionWrapper = null;
            ArrayList<Set<String>> exprAttrNamesUsed = new ArrayList<Set<String>>(2);
            ArrayList<Set<String>> exprAttrValuesUsed = new ArrayList<Set<String>>(2);
            if (updateExpressionString != null) {
                updateExpressionWrapper = this.parseUpdateExpression(updateExpressionString, expressionAttributeNames, internalExpressionAttributeValues);
                expressionsWrapper.setUpdateExpressionWrapper(updateExpressionWrapper);
                exprAttrNamesUsed.add(updateExpressionWrapper.getNameParameterUsage().keySet());
                exprAttrValuesUsed.add(updateExpressionWrapper.getValueParameterUsage().keySet());
                this.validateUpdateExpressionCombinedSize(updateExpressionWrapper, updateExpressionString.length(), expressionAttributeNamesSizes, expressionAttributeValuesSizes);
                this.validateUpdateExpressionItemUpdateSize(updateExpressionWrapper, expressionAttributeValuesSizes);
            }
            if (conditionExpressionString != null) {
                conditionExpressionWrapper = this.parseConditionExpression(conditionExpressionString, expressionAttributeNames, internalExpressionAttributeValues);
                expressionsWrapper.setConditionExpressionWrapper(conditionExpressionWrapper);
                exprAttrNamesUsed.add(conditionExpressionWrapper.getNameParameterUsage().keySet());
                exprAttrValuesUsed.add(conditionExpressionWrapper.getValueParameterUsage().keySet());
                this.validateConditionExpressionCombinedSize(conditionExpressionWrapper, conditionExpressionString.length(), expressionAttributeNamesSizes, expressionAttributeValuesSizes);
            }
            this.validateAllNameSubstitutionsUsed(expressionAttributeNames, exprAttrNamesUsed);
            this.validateAllValueSubstitutionsUsed(internalExpressionAttributeValues, exprAttrValuesUsed);
        }
        return expressionsWrapper;
    }

    private UpdateExpressionWrapper parseUpdateExpression(String updateExpressionString, Map<String, String> expressionAttributeNames, Map<String, DocumentNode> expressionAttributeValues) {
        UpdateExpressionWrapper updateExpression = null;
        if (updateExpressionString != null && this.isDocumentSupportEnabled) {
            try {
                updateExpression = DynamoDbParser.parseUpdateExpression(updateExpressionString, expressionAttributeNames, expressionAttributeValues, this.dbEnv, this.documentFactory);
            } catch (Exception e) {
                this.errorFactory.INVALID_UPDATE_EXPRESSION.throwAsException(e.getMessage());
            }
        }
        return updateExpression;
    }

    private ExpressionWrapper parseFilterExpression(String filterExpressionString, Map<String, String> expressionAttributeNames, Map<String, DocumentNode> expressionAttributeValues) {
        ExpressionWrapper filterExpression = null;
        if (filterExpressionString != null && this.isDocumentSupportEnabled) {
            try {
                filterExpression = DynamoDbParser.parseExpression(filterExpressionString, expressionAttributeNames, expressionAttributeValues, this.dbEnv, this.documentFactory);
            } catch (Exception e) {
                this.errorFactory.INVALID_FILTER_EXPRESSION.throwAsException(e.getMessage());
            }
        }
        return filterExpression;
    }

    private ExpressionWrapper parseConditionExpression(String conditionExpressionString, Map<String, String> expressionAttributeNames, Map<String, DocumentNode> expressionAttributeValues) {
        ExpressionWrapper conditionExpression = null;
        if (conditionExpressionString != null && this.isDocumentSupportEnabled) {
            try {
                conditionExpression = DynamoDbParser.parseExpression(conditionExpressionString, expressionAttributeNames, expressionAttributeValues, this.dbEnv, this.documentFactory);
            } catch (Exception e) {
                this.errorFactory.INVALID_CONDITION_EXPRESSION.throwAsException(e.getMessage());
            }
        }
        return conditionExpression;
    }

    private ProjectionExpressionWrapper parseProjectionExpression(String projectionExpressionString, Map<String, String> expressionAttributeNames) {
        ProjectionExpressionWrapper projectionExpression = null;
        if (projectionExpressionString != null && this.isDocumentSupportEnabled) {
            try {
                projectionExpression = DynamoDbParser.parseProjectionExpression(projectionExpressionString, expressionAttributeNames, this.dbEnv, this.documentFactory);
            } catch (Exception e) {
                this.errorFactory.INVALID_PROJECTION_EXPRESSION.throwAsException(e.getMessage());
            }
        }
        return projectionExpression;
    }

    private ExpressionWrapper parseKeyConditionExpression(String keyConditionExpressionString, Map<String, String> expressionAttributeNames, Map<String, DocumentNode> expressionAttributeValues) {
        ExpressionWrapper keyConditionExpression = null;
        if (keyConditionExpressionString != null && this.isDocumentSupportEnabled) {
            try {
                keyConditionExpression = DynamoDbParser.parseExpression(keyConditionExpressionString, expressionAttributeNames, expressionAttributeValues, this.dbEnv, this.documentFactory);
            } catch (Exception e) {
                this.errorFactory.INVALID_KEY_CONDITION_EXPRESSION.throwAsException(e.getMessage());
            }
        }
        return keyConditionExpression;
    }

    private Map<String, DocumentNode> externalToInternalExpressionAttributeValues(Map<String, ExternalAttributeValue> expressionAttributeValues) {
        HashMap<String, Object> internalExpressionAttributeValues = null;
        if (expressionAttributeValues != null) {
            internalExpressionAttributeValues = new HashMap<String, Object>(expressionAttributeValues.size());
            for (Map.Entry<String, ExternalAttributeValue> entry : expressionAttributeValues.entrySet()) {
                Object value = null;
                try {
                    value = this.externalToInternalAttributeValue(entry.getValue(), false);
                } catch (Exception vex) {
                    this.errorFactory.EXPR_ATTR_VALUES_MAP_INVALID_VALUE.throwAsException(vex.getMessage() + " for key " + entry.getKey());
                }
                internalExpressionAttributeValues.put(entry.getKey(), value);
            }
        }
        return internalExpressionAttributeValues;
    }

    public Map<InternalTableName, InternalKeysAndAttributes> externalToInternalBatchGet(String accountId, Map<String, ExternalKeysAndAttributes> requests) {
        int cumulativeKeyCount = 0;
        HashMap<InternalTableName, InternalKeysAndAttributes> fetchMap = new HashMap<InternalTableName, InternalKeysAndAttributes>(requests.size());
        for (String tableName : requests.keySet()) {
            List<Map<String, ExternalAttributeValue>> keys2;
            ExternalKeysAndAttributes kas = requests.get(tableName);
            List<Map<String, ExternalAttributeValue>> list = keys2 = kas != null ? this.getKeys(kas) : null;
            if (keys2 == null || keys2.size() == 0) {
                this.errorFactory.BATCH_GET_NULL_OR_EMPTY_KAS.throwAsException(tableName + " has empty list");
            }
            HashSet<InternalAttributeValueMap> compositeKeySet = new HashSet<InternalAttributeValueMap>(keys2.size());
            for (Map<String, ExternalAttributeValue> key : keys2) {
                InternalAttributeValueMap compositeKey;
                boolean added;
                if (key == null) {
                    this.errorFactory.BATCH_GET_KAS_CONTAINS_NULL.throwAsException(tableName + " has one or more empty keys");
                }
                if (!(added = compositeKeySet.add(compositeKey = this.externalToInternalAttributes(key)))) {
                    this.errorFactory.DUPLICATE_ITEM_KEY.throwAsException();
                }
                if (cumulativeKeyCount == this.maxBatchGetRequestCount) {
                    this.errorFactory.BATCH_GET_LIMIT_EXCEEDED.throwAsException();
                    continue;
                }
                ++cumulativeKeyCount;
            }
            Set<InternalAttributeName> attrsToGet = this.getAttributeNames(this.getAttributesToGet(kas));
            fetchMap.put(this.newTableName(accountId, tableName), this.newKeysAndAttributes(compositeKeySet, attrsToGet, this.isConsistentRead(kas), this.getProjectionExpression(kas), this.getExpressionAttributeNames(kas)));
        }
        return fetchMap;
    }

    protected abstract InternalKeysAndAttributes newKeysAndAttributes(Set<InternalAttributeValueMap> var1, Set<InternalAttributeName> var2, Boolean var3, String var4, Map<String, String> var5);

    public Map<InternalTableName, List<InternalWriteRequest>> externalToInternalBatchWrite(String accountId, Map<String, List<ExternalWriteRequest>> requests) {
        int cumulativeRequestCount = 0;
        HashMap writeMap = new HashMap(requests.size());
        for (Map.Entry<String, List<ExternalWriteRequest>> entry : requests.entrySet()) {
            List<ExternalWriteRequest> batchWriteItemRequests = entry.getValue();
            if (batchWriteItemRequests == null || batchWriteItemRequests.size() == 0) {
                this.errorFactory.BATCH_WRITE_REQUEST_NULL.throwAsException(entry.getKey());
            }
            ArrayList<InternalWriteRequest> writeRequests = new ArrayList<InternalWriteRequest>(batchWriteItemRequests.size());
            InternalTableName tableName = this.newTableName(accountId, entry.getKey());
            for (ExternalWriteRequest batchWriteItemRequest : batchWriteItemRequests) {
                if (batchWriteItemRequest == null) {
                    this.errorFactory.BATCH_WRITE_REQUEST_NULL.throwAsException();
                }
                boolean isDelete = this.isWriteDelete(batchWriteItemRequest);
                boolean isPut = this.isWritePut(batchWriteItemRequest);
                if (isDelete && isPut || !isDelete && !isPut) {
                    this.errorFactory.MULTI_ATTRIBUTE_VALUE.throwAsException();
                }
                if (++cumulativeRequestCount > this.maxBatchWriteRequestCount) {
                    this.errorFactory.BATCH_WRITE_LIMIT_EXCEEDED.throwAsException();
                }
                InternalWriteRequest writeRequest = isPut ? this.newPutRequest(tableName, this.externalToInternalAttributes(this.getPutRequestItem(batchWriteItemRequest))) : this.newDeleteRequest(tableName, this.externalToInternalAttributes(this.getDeleteRequestKey(batchWriteItemRequest)));
                writeRequests.add(writeRequest);
            }
            writeMap.put(tableName, writeRequests);
        }
        return writeMap;
    }

    private void validateAllNameSubstitutionsUsed(Map<String, String> expressionAttributeNames, Collection<Set<String>> usedNamesSets) {
        if (expressionAttributeNames == null || expressionAttributeNames.isEmpty()) {
            return;
        }
        HashSet<String> nameSubstitutions = new HashSet<String>(expressionAttributeNames.keySet());
        if (usedNamesSets != null) {
            for (Set<String> usedNames : usedNamesSets) {
                nameSubstitutions.removeAll(usedNames);
            }
        }
        if (!nameSubstitutions.isEmpty()) {
            this.errorFactory.EXPR_ATTR_NAMES_MAP_UNUSED_VALUE.throwAsException("keys: " + InputConverter.convertToString(nameSubstitutions));
        }
    }

    private void validateAllValueSubstitutionsUsed(Map<String, DocumentNode> expressionAttributeValues, Collection<Set<String>> usedValuesSets) {
        if (expressionAttributeValues == null || expressionAttributeValues.isEmpty()) {
            return;
        }
        HashSet<String> valueSubstitutions = new HashSet<String>(expressionAttributeValues.keySet());
        if (usedValuesSets != null) {
            for (Set<String> usedValues : usedValuesSets) {
                valueSubstitutions.removeAll(usedValues);
            }
        }
        if (!valueSubstitutions.isEmpty()) {
            this.errorFactory.EXPR_ATTR_VALUES_MAP_UNUSED_VALUE.throwAsException("keys: " + InputConverter.convertToString(valueSubstitutions));
        }
    }

    public void validateExpressionAttributeNamesUsedOnlyWithExpressions(String projectionExpression, String conditionExpression, String filterExpression, String updateExpression, String keyConditionExpression, Map<String, String> expressionAttributeNames) {
        boolean expressionsUsed;
        boolean bl = expressionsUsed = projectionExpression != null || conditionExpression != null || filterExpression != null || updateExpression != null || keyConditionExpression != null;
        if (expressionAttributeNames != null && !expressionsUsed) {
            this.errorFactory.EXPR_ATTR_NAMES_WITHOUT_EXPRESSIONS.throwAsException();
        }
    }

    public void validateMixingOldStyleWithExpressions(Map<String, ExternalAttributeValueUpdate> attributeUpdates, List<String> attributesToGet, Map<String, ExternalExpectedAttributeValue> expected, Map<String, ExternalCondition> queryFilter, Map<String, ExternalCondition> scanFilter, Map<String, ExternalCondition> keyConditions, String conditionalOperator, String updateExpression, String projectionExpression, String conditionExpression, String filterExpression, String keyConditionExpression) {
        ArrayList<String> oldStyleFields = new ArrayList<String>();
        if (attributeUpdates != null) {
            oldStyleFields.add("AttributeUpdates");
        }
        if (attributesToGet != null) {
            oldStyleFields.add("AttributesToGet");
        }
        if (expected != null) {
            oldStyleFields.add("Expected");
        }
        if (queryFilter != null) {
            oldStyleFields.add("QueryFilter");
        }
        if (scanFilter != null) {
            oldStyleFields.add("ScanFilter");
        }
        if (conditionalOperator != null) {
            oldStyleFields.add("ConditionalOperator");
        }
        ArrayList<String> expressionFields = new ArrayList<String>();
        if (updateExpression != null) {
            expressionFields.add("UpdateExpression");
        }
        if (projectionExpression != null) {
            expressionFields.add("ProjectionExpression");
        }
        if (conditionExpression != null) {
            expressionFields.add("ConditionExpression");
        }
        if (filterExpression != null) {
            expressionFields.add("FilterExpression");
        }
        if (keyConditionExpression != null) {
            expressionFields.add("KeyConditionExpression");
            if (keyConditions != null) {
                oldStyleFields.add("KeyConditions");
            }
        }
        if (!oldStyleFields.isEmpty() && !expressionFields.isEmpty()) {
            String errorMessage = "Non-expression parameters: " + InputConverter.convertToString(oldStyleFields) + " Expression parameters: " + InputConverter.convertToString(expressionFields);
            this.errorFactory.EXPRESSIONS_MIXED_WITH_OLD_FIELDS.throwAsException(errorMessage);
        }
    }

    private void validateSubstitutionMapSizes(Map<String, Integer> expressionAttributeNamesSizes, Map<String, Integer> expressionAttributeValuesSizes) {
        int valuesMapSize;
        int namesMapSize = expressionAttributeNamesSizes == null ? 0 : this.validateExpressionAttributeNamesSize(expressionAttributeNamesSizes);
        int n = valuesMapSize = expressionAttributeValuesSizes == null ? 0 : this.validateExpressionAttributeValuesSize(expressionAttributeValuesSizes);
        if (namesMapSize + valuesMapSize > this.maxExpressionSubstitutionMapSize) {
            this.errorFactory.EXPR_ATTR_NAMES_PLUS_VALUES_MAPS_SIZE_EXCEEDED.throwAsException();
        }
    }

    private void validateProjectionExpressionCombinedSize(ProjectionExpressionWrapper wrapper, int expressionSize, Map<String, Integer> expressionAttributeNamesSizes) {
        try {
            wrapper.validateCombinedExpressionSize(expressionSize, expressionAttributeNamesSizes, null, this.dbEnv);
        } catch (Exception e) {
            this.errorFactory.INVALID_PROJECTION_EXPRESSION.throwAsException(e.getMessage());
        }
    }

    private void validateConditionExpressionCombinedSize(ExpressionWrapper wrapper, int expressionSize, Map<String, Integer> expressionAttributeNamesSizes, Map<String, Integer> expressionAttributeValuesSizes) {
        try {
            wrapper.validateCombinedExpressionSize(expressionSize, expressionAttributeNamesSizes, expressionAttributeValuesSizes, this.dbEnv);
        } catch (Exception e) {
            this.errorFactory.INVALID_CONDITION_EXPRESSION.throwAsException(e.getMessage());
        }
    }

    private void validateFilterExpressionCombinedSize(ExpressionWrapper wrapper, int expressionSize, Map<String, Integer> expressionAttributeNamesSizes, Map<String, Integer> expressionAttributeValuesSizes) {
        try {
            wrapper.validateCombinedExpressionSize(expressionSize, expressionAttributeNamesSizes, expressionAttributeValuesSizes, this.dbEnv);
        } catch (Exception e) {
            this.errorFactory.INVALID_FILTER_EXPRESSION.throwAsException(e.getMessage());
        }
    }

    private void validateKeyConditionExpressionCombinedSize(ExpressionWrapper wrapper, int expressionSize, Map<String, Integer> expressionAttributeNamesSizes, Map<String, Integer> expressionAttributeValuesSizes) {
        try {
            wrapper.validateCombinedExpressionSize(expressionSize, expressionAttributeNamesSizes, expressionAttributeValuesSizes, this.dbEnv);
        } catch (Exception e) {
            this.errorFactory.INVALID_KEY_CONDITION_EXPRESSION.throwAsException(e.getMessage());
        }
    }

    private void validateUpdateExpressionCombinedSize(UpdateExpressionWrapper wrapper, int expressionSize, Map<String, Integer> expressionAttributeNamesSizes, Map<String, Integer> expressionAttributeValuesSizes) {
        try {
            wrapper.validateCombinedExpressionSize(expressionSize, expressionAttributeNamesSizes, expressionAttributeValuesSizes, this.dbEnv);
        } catch (Exception e) {
            this.errorFactory.INVALID_UPDATE_EXPRESSION.throwAsException(e.getMessage());
        }
    }

    private void validateUpdateExpressionItemUpdateSize(UpdateExpressionWrapper wrapper, Map<String, Integer> expressionAttributeValuesSizes) {
        int cumulativeSize = 0;
        Set<DocPath> pathsForUpdateSize = wrapper.getPathsForUpdateSize();
        for (DocPath currDocPath : pathsForUpdateSize) {
            Set<String> attributeNames = ExpressionUtils.docPathElementsToAttrNames(currDocPath.getElements());
            for (String currAttributeName : attributeNames) {
                cumulativeSize += this.getLengthInUTF8Bytes(currAttributeName);
            }
        }
        Map<String, Integer> valueParameterUsageForUpdateSize = wrapper.getValueParameterUsageForUpdateSize();
        if ((cumulativeSize += ExpressionsWrapperBase.getParameterUsageSize(valueParameterUsageForUpdateSize, expressionAttributeValuesSizes, this.dbEnv)) > this.maxItemSize) {
            this.errorFactory.ITEM_UPD_TOO_LARGE.throwAsException();
        }
    }

    private int validateExpressionAttributeValuesSize(Map<String, Integer> expressionAttributeValuesSizes) {
        int cumulativeMapSize = 0;
        if (expressionAttributeValuesSizes != null) {
            for (Map.Entry<String, Integer> entry : expressionAttributeValuesSizes.entrySet()) {
                cumulativeMapSize += this.getLengthInUTF8Bytes(entry.getKey());
                if ((cumulativeMapSize += entry.getValue().intValue()) <= this.maxExpressionSubstitutionMapSize) continue;
                this.errorFactory.EXPR_ATTR_VALUES_MAP_SIZE_EXCEEDED.throwAsException();
            }
        }
        return cumulativeMapSize;
    }

    private int validateExpressionAttributeNamesSize(Map<String, Integer> expressionAttributeNamesSizes) {
        int cumulativeMapSize = 0;
        if (expressionAttributeNamesSizes != null) {
            for (Map.Entry<String, Integer> entry : expressionAttributeNamesSizes.entrySet()) {
                cumulativeMapSize += this.getLengthInUTF8Bytes(entry.getKey());
                if ((cumulativeMapSize += entry.getValue().intValue()) <= this.maxExpressionSubstitutionMapSize) continue;
                this.errorFactory.EXPR_ATTR_NAMES_MAP_SIZE_EXCEEDED.throwAsException();
            }
        }
        return cumulativeMapSize;
    }

    private void validateExpressionAttributeNames(Map<String, String> expressionAttributeNames) {
        if (expressionAttributeNames != null) {
            if (expressionAttributeNames.isEmpty()) {
                this.errorFactory.EMPTY_EXPR_ATTR_NAMES_MAP.throwAsException();
            }
            try {
                DynamoDbParser.validateAttributeNameParameter(expressionAttributeNames, this.dbEnv);
            } catch (Exception e) {
                this.errorFactory.EXPR_ATTR_NAMES_MAP_INVALID_KEY.throwAsException(e.getMessage());
            }
        }
    }

    public void validateExpressionAttributeValuesWithConditionExpression(String conditionExpression, Map<String, ExternalAttributeValue> expressionAttributeValues) {
        if (expressionAttributeValues != null && conditionExpression == null) {
            this.errorFactory.EXPR_ATTR_VALUES_WITHOUT_EXPRESSIONS.throwAsException("ConditionExpression is null");
        }
    }

    public void validateExpressionAttributeValuesWithFilterOrKeyConditionExpression(String filterExpression, String keyConditionExpression, Map<String, ExternalAttributeValue> expressionAttributeValues) {
        if (expressionAttributeValues != null && filterExpression == null && keyConditionExpression == null) {
            this.errorFactory.EXPR_ATTR_VALUES_WITHOUT_EXPRESSIONS.throwAsException("FilterExpression and KeyConditionExpression are null");
        }
    }

    private void validateExpressionAttributeValues(Map<String, ExternalAttributeValue> expressionAttributeValues) {
        if (expressionAttributeValues != null) {
            if (expressionAttributeValues.isEmpty()) {
                this.errorFactory.EMPTY_EXPR_ATTR_VALUES_MAP.throwAsException();
            }
            try {
                DynamoDbParser.validateLiteralParametersKeys(expressionAttributeValues.keySet(), this.dbEnv);
            } catch (Exception e) {
                this.errorFactory.EXPR_ATTR_VALUES_MAP_INVALID_KEY.throwAsException(e.getMessage());
            }
        }
    }

    public void validateExpressionAttributeValuesWithFilterExpression(String filterExpression, Map<String, ExternalAttributeValue> expressionAttributeValues) {
        if (expressionAttributeValues != null && filterExpression == null) {
            this.errorFactory.EXPR_ATTR_VALUES_WITHOUT_EXPRESSIONS.throwAsException("FilterExpression is null");
        }
    }

    public void validateExpressionAttributeValuesWithUpdateExpressionOrConditionExpression(String updateExpression, String conditionExpression, Map<String, ExternalAttributeValue> expressionAttributeValues) {
        if (expressionAttributeValues != null && updateExpression == null && conditionExpression == null) {
            this.errorFactory.EXPR_ATTR_VALUES_WITHOUT_EXPRESSIONS.throwAsException("UpdateExpression and ConditionExpression are null");
        }
    }

    private Map<String, Integer> getExpressionAttributeNamesSizes(Map<String, String> expressionAttributeNames) {
        HashMap<String, Integer> expressionAttributeNamesSizes = new HashMap<String, Integer>();
        if (expressionAttributeNames != null) {
            for (Map.Entry<String, String> entry : expressionAttributeNames.entrySet()) {
                int keysize = this.getLengthInUTF8Bytes(entry.getKey());
                if (keysize > this.maxKeyAttributeNameSize) {
                    this.errorFactory.PARAMETER_MAP_KEY_SIZE_EXCEEDED.throwAsException(": key " + entry.getKey() + " was " + keysize + " bytes long");
                }
                try {
                    if (this.getLengthInUTF8Bytes(entry.getValue()) > 65535) {
                        this.errorFactory.ATTRIBUTE_NAME_TOO_LARGE.throwAsException();
                    }
                    expressionAttributeNamesSizes.put(entry.getKey(), this.getLengthInUTF8Bytes(this.newAttributeName(entry.getValue())));
                } catch (Exception e) {
                    if (e instanceof NullPointerException) {
                        this.errorFactory.EXPR_ATTR_NAMES_MAP_INVALID_VALUE.throwAsException("null for key " + entry.getKey());
                    }
                    this.errorFactory.EXPR_ATTR_NAMES_MAP_INVALID_VALUE.throwAsException(e.getMessage() + " for key " + entry.getKey());
                }
            }
        }
        return expressionAttributeNamesSizes;
    }

    private Map<String, Integer> getExpressionAttributeValuesSizes(Map<String, DocumentNode> expressionAttributeValues) {
        HashMap<String, Integer> expressionAttributeValuesSizes = new HashMap<String, Integer>();
        if (expressionAttributeValues != null) {
            for (Map.Entry<String, DocumentNode> entry : expressionAttributeValues.entrySet()) {
                expressionAttributeValuesSizes.put(entry.getKey(), this.getAttributeSizeInBytes(entry.getValue()));
            }
        }
        return expressionAttributeValuesSizes;
    }

    protected abstract InternalWriteRequest newPutRequest(InternalTableName var1, InternalAttributeValueMap var2);

    protected abstract InternalWriteRequest newDeleteRequest(InternalTableName var1, InternalAttributeValueMap var2);

    protected abstract boolean isWriteDelete(ExternalWriteRequest var1);

    protected abstract boolean isWritePut(ExternalWriteRequest var1);

    protected abstract Map<String, ExternalAttributeValue> getPutRequestItem(ExternalWriteRequest var1);

    protected abstract Map<String, ExternalAttributeValue> getDeleteRequestKey(ExternalWriteRequest var1);

    protected abstract List<Map<String, ExternalAttributeValue>> getKeys(ExternalKeysAndAttributes var1);

    protected abstract List<String> getAttributesToGet(ExternalKeysAndAttributes var1);

    protected abstract String getProjectionExpression(ExternalKeysAndAttributes var1);

    protected abstract Boolean isConsistentRead(ExternalKeysAndAttributes var1);

    protected abstract InternalTableName newTableName(String var1, String var2);

    protected abstract InternalAttributeName newAttributeName(String var1);

    protected abstract int getLengthInUTF8Bytes(InternalAttributeName var1);

    protected abstract InternalAttributeValue newS(String var1);

    protected abstract InternalAttributeValue newN(String var1);

    protected abstract InternalAttributeValue newB(ByteBuffer var1);

    protected abstract InternalAttributeValue newSS(List<String> var1);

    protected abstract InternalAttributeValue newNS(List<String> var1);

    protected abstract InternalAttributeValue newBS(List<ByteBuffer> var1);

    protected abstract InternalAttributeValue newBoolean(Boolean var1);

    protected abstract InternalAttributeValue newNull();

    protected abstract InternalAttributeValue newM(InternalAttributeValueMap var1);

    protected abstract InternalAttributeValue newL(InternalAttributeValueList var1);

    protected abstract int getAttributeSizeInBytes(InternalAttributeValue var1);

    protected abstract DocumentNodeType getType(InternalAttributeValue var1);

    protected abstract InternalAttributeValueList newAttrValueList(int var1, int var2);

    protected abstract void addAttrValue(InternalAttributeValueList var1, InternalAttributeValue var2);

    protected abstract InternalAttributeValueMap newAttrValueMap(int var1, int var2);

    protected abstract void putAttrValue(InternalAttributeValueMap var1, InternalAttributeName var2, InternalAttributeValue var3);

    protected abstract boolean isValidNestedLevel(int var1);

    protected abstract String getS(ExternalAttributeValue var1);

    protected abstract String getN(ExternalAttributeValue var1);

    protected abstract ByteBuffer getB(ExternalAttributeValue var1);

    protected abstract Boolean isBOOL(ExternalAttributeValue var1);

    protected abstract Boolean isNULL(ExternalAttributeValue var1);

    protected abstract List<String> getSS(ExternalAttributeValue var1);

    protected abstract List<String> getNS(ExternalAttributeValue var1);

    protected abstract List<ByteBuffer> getBS(ExternalAttributeValue var1);

    protected abstract List<ExternalAttributeValue> getL(ExternalAttributeValue var1);

    protected abstract Map<String, ExternalAttributeValue> getM(ExternalAttributeValue var1);

    protected abstract InternalExpectedAttributeValue newExpected(InternalAttributeValue var1, Boolean var2, List<InternalAttributeValue> var3, InternalComparisonOperator var4);

    protected abstract String getExpectedComparisonOperator(ExternalExpectedAttributeValue var1);

    protected abstract List<ExternalAttributeValue> getExpectedAttributeValueList(ExternalExpectedAttributeValue var1);

    protected abstract ExternalAttributeValue getExpectedValueExternal(ExternalExpectedAttributeValue var1);

    protected abstract Boolean isExists(ExternalExpectedAttributeValue var1);

    protected abstract InternalAttributeValueUpdate newUpdate(InternalAttributeValue var1, InternalAction var2);

    protected abstract InternalAttributeValue getUpdateValueInternal(InternalAttributeValueUpdate var1);

    protected abstract InternalAction getUpdateActionInternal(InternalAttributeValueUpdate var1);

    protected abstract String getUpdateActionExternal(ExternalAttributeValueUpdate var1);

    protected abstract ExternalAttributeValue getUpdateValueExternal(ExternalAttributeValueUpdate var1);

    protected abstract InternalAction newAction(String var1);

    protected abstract boolean isDelete(InternalAction var1);

    protected abstract boolean isAdd(InternalAction var1);

    protected abstract InternalComparisonOperator newComparisonOperator(String var1);

    protected abstract void validateArgumentsForComparisonOperator(InternalComparisonOperator var1, List<InternalAttributeValue> var2);

    protected abstract InternalCondition newCondition(InternalComparisonOperator var1, List<InternalAttributeValue> var2);

    protected abstract String getConditionComparisonOperator(ExternalCondition var1);

    protected abstract List<ExternalAttributeValue> getConditionAttributeValueList(ExternalCondition var1);

    protected abstract InternalConditionalOperator newConditionalOperator(String var1);

    protected abstract InternalSelect newSelect(String var1);

    protected abstract InternalReturnConsumedCapacity newReturnConsumedCapacity(String var1);

    protected abstract Map<String, String> getExpressionAttributeNames(ExternalKeysAndAttributes var1);
}

