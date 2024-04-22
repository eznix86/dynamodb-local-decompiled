/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  com.amazonaws.AmazonServiceException
 *  com.amazonaws.services.dynamodbv2.model.AttributeAction
 *  com.amazonaws.services.dynamodbv2.model.AttributeDefinition
 *  com.amazonaws.services.dynamodbv2.model.AttributeValue
 *  com.amazonaws.services.dynamodbv2.model.AttributeValueUpdate
 *  com.amazonaws.services.dynamodbv2.model.ReturnValue
 *  com.amazonaws.services.dynamodbv2.model.ReturnValuesOnConditionCheckFailure
 *  com.amazonaws.services.dynamodbv2.model.UpdateItemRequest
 *  com.amazonaws.services.dynamodbv2.model.UpdateItemResult
 */
package com.amazonaws.services.dynamodbv2.local.shared.access.api.dp;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.dynamodbv2.datamodel.DocumentFactory;
import com.amazonaws.services.dynamodbv2.datamodel.DocumentNodeType;
import com.amazonaws.services.dynamodbv2.datamodel.Expression;
import com.amazonaws.services.dynamodbv2.datamodel.UpdateExpression;
import com.amazonaws.services.dynamodbv2.dbenv.DbEnv;
import com.amazonaws.services.dynamodbv2.exceptions.AWSExceptionFactory;
import com.amazonaws.services.dynamodbv2.exceptions.AmazonServiceExceptionType;
import com.amazonaws.services.dynamodbv2.local.shared.access.DDBType;
import com.amazonaws.services.dynamodbv2.local.shared.access.LocalDBAccess;
import com.amazonaws.services.dynamodbv2.local.shared.access.LocalDBInputConverter;
import com.amazonaws.services.dynamodbv2.local.shared.access.LocalDBOutputConverter;
import com.amazonaws.services.dynamodbv2.local.shared.access.LocalDBUtils;
import com.amazonaws.services.dynamodbv2.local.shared.access.LocalDBValidatorUtils;
import com.amazonaws.services.dynamodbv2.local.shared.access.TableInfo;
import com.amazonaws.services.dynamodbv2.local.shared.access.api.Mutation;
import com.amazonaws.services.dynamodbv2.local.shared.access.api.dp.WriteDataPlaneFunction;
import com.amazonaws.services.dynamodbv2.local.shared.exceptions.LocalDBClientExceptionMessage;
import com.amazonaws.services.dynamodbv2.local.shared.exceptions.LocalDBClientExceptionType;
import com.amazonaws.services.dynamodbv2.local.shared.helpers.TransactionsEnabledMode;
import com.amazonaws.services.dynamodbv2.local.shared.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.local.shared.model.Condition;
import com.amazonaws.services.dynamodbv2.local.shared.validate.UpdateItemExpressionsWrapper;
import com.amazonaws.services.dynamodbv2.model.AttributeAction;
import com.amazonaws.services.dynamodbv2.model.AttributeDefinition;
import com.amazonaws.services.dynamodbv2.model.AttributeValueUpdate;
import com.amazonaws.services.dynamodbv2.model.ReturnValue;
import com.amazonaws.services.dynamodbv2.model.ReturnValuesOnConditionCheckFailure;
import com.amazonaws.services.dynamodbv2.model.UpdateItemRequest;
import com.amazonaws.services.dynamodbv2.model.UpdateItemResult;
import com.amazonaws.services.dynamodbv2.rr.ExpressionWrapper;
import com.amazonaws.services.dynamodbv2.rr.UpdateExpressionWrapper;
import java.math.BigDecimal;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UpdateItemFunction
extends WriteDataPlaneFunction<UpdateItemRequest, UpdateItemResult> {
    public UpdateItemFunction(LocalDBAccess dbAccess, DbEnv localDBEnv, LocalDBInputConverter inputConverter, LocalDBOutputConverter localDBOutputConverter, AWSExceptionFactory awsExceptionFactory, DocumentFactory documentFactory, TransactionsEnabledMode transactionsEnabledMode) {
        super(dbAccess, localDBEnv, inputConverter, localDBOutputConverter, awsExceptionFactory, documentFactory, transactionsEnabledMode);
    }

    @Override
    public UpdateItemResult apply(final UpdateItemRequest updateItemRequest) {
        final String tableName = updateItemRequest.getTableName();
        this.validateTableName(tableName);
        final TableInfo tableInfo = this.validateTableExists(tableName);
        final Map expected = this.inputConverter.externalToInternalExpectedAttributes(updateItemRequest.getExpected(), 409600);
        final ReturnValue returnVals = this.validateReturnType(updateItemRequest.getReturnValues(), true);
        LocalDBValidatorUtils.validateExpressions(updateItemRequest, this.inputConverter);
        final Map updatesToMake = updateItemRequest.getAttributeUpdates();
        if (updateItemRequest.getKey() == null) {
            throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, LocalDBClientExceptionMessage.MISSING_KEY.getMessage());
        }
        final Map primaryKey = (Map)this.inputConverter.externalToInternalAttributes(updateItemRequest.getKey());
        this.validateGetKey(primaryKey, tableInfo);
        final UpdateItemResult updateItemResult = new UpdateItemResult();
        new LocalDBAccess.WriteLockWithTimeout(this.dbAccess.getLockForTable(tableName), 10){

            @Override
            public void criticalSection() {
                boolean usingUpdateExpression;
                HashMap<String, AttributeValue> oldItem;
                String conditionExpressionString = updateItemRequest.getConditionExpression();
                String updateExpressionString = updateItemRequest.getUpdateExpression();
                Map expressionAttributeNames = updateItemRequest.getExpressionAttributeNames();
                UpdateItemExpressionsWrapper updateItemExpressionsWrapper = UpdateItemFunction.this.inputConverter.externalToInternalUpdateAndConditionExpressions(updateExpressionString, conditionExpressionString, expressionAttributeNames, updateItemRequest.getExpressionAttributeValues());
                Map<String, AttributeValue> item = UpdateItemFunction.this.dbAccess.getRecord(tableName, primaryKey);
                if (item == null) {
                    oldItem = null;
                    item = new HashMap<String, AttributeValue>(primaryKey);
                } else {
                    oldItem = new HashMap<String, AttributeValue>();
                    for (Map.Entry<String, AttributeValue> entry : item.entrySet()) {
                        oldItem.put(entry.getKey(), entry.getValue());
                    }
                }
                String conditionalOperatorAsString = updateItemRequest.getConditionalOperator();
                Map<String, Condition> conditions = null;
                if (conditionExpressionString == null) {
                    UpdateItemFunction.this.validateExpectations(expected, conditionalOperatorAsString);
                    conditions = UpdateItemFunction.this.convertToConditions(expected);
                    UpdateItemFunction.this.validateConditions(conditions, conditionalOperatorAsString);
                }
                ExpressionWrapper conditionExpressionWrapper = updateItemExpressionsWrapper == null ? null : updateItemExpressionsWrapper.getConditionExpressionWrapper();
                Expression conditionExpression = conditionExpressionWrapper == null ? null : conditionExpressionWrapper.getExpression();
                LocalDBValidatorUtils.validateNoNestedAccessToKeyAttributeInExpression(tableInfo, conditionExpressionWrapper, UpdateItemFunction.this.awsExceptionFactory);
                String returnValuesOnConditionCheckFailure = LocalDBValidatorUtils.validateReturnValuesOnConditionCheckFailure(updateItemRequest.getReturnValuesOnConditionCheckFailure());
                if (!UpdateItemFunction.this.doesItemMatchConditionalOperator(oldItem, conditions, UpdateItemFunction.this.conditionalOperatorFrom(conditionalOperatorAsString)) || !UpdateItemFunction.this.doesItemMatchConditionExpression(oldItem, conditionExpression)) {
                    if (UpdateItemFunction.this.getReturnValuesOnConditionCheckFailure(returnValuesOnConditionCheckFailure).equals((Object)ReturnValuesOnConditionCheckFailure.ALL_OLD)) {
                        throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.CONDITIONAL_CHECK_FAILED_EXCEPTION, LocalDBClientExceptionMessage.CONDITIONAL_CHECK_FAILED.getMessage(), oldItem);
                    }
                    throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.CONDITIONAL_CHECK_FAILED_EXCEPTION, LocalDBClientExceptionMessage.CONDITIONAL_CHECK_FAILED.getMessage());
                }
                HashMap itemChangesForExpr = new HashMap();
                if (updateExpressionString == null) {
                    UpdateItemFunction.this.validateAttributeUpdates(updatesToMake, tableInfo, oldItem);
                    UpdateItemFunction.this.doUpdates(updatesToMake, item);
                    usingUpdateExpression = false;
                } else {
                    UpdateExpressionWrapper updateExpressionWrapper = updateItemExpressionsWrapper == null ? null : updateItemExpressionsWrapper.getUpdateExpressionWrapper();
                    UpdateExpression updateExpression = updateExpressionWrapper == null ? null : updateExpressionWrapper.getUpdateExpr();
                    LocalDBValidatorUtils.validateThatKeyAttributesNotUpdated(tableInfo, updateExpression, UpdateItemFunction.this.awsExceptionFactory);
                    LocalDBValidatorUtils.validateNoNestedAccessToKeyAttributeInExpression(tableInfo, updateExpressionWrapper, UpdateItemFunction.this.awsExceptionFactory);
                    UpdateItemFunction.this.doUpdates(updateExpression, (Map)item, (Map)itemChangesForExpr, (ReturnValue)(ReturnValue.UPDATED_NEW.equals((Object)returnVals) || ReturnValue.UPDATED_OLD.equals((Object)returnVals) ? returnVals : null));
                    usingUpdateExpression = true;
                }
                if (LocalDBUtils.getItemSizeBytes(item) > 409600L) {
                    throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, LocalDBClientExceptionMessage.ITEM_UPD_TOO_LARGE.getMessage());
                }
                Map key = (Map)UpdateItemFunction.this.inputConverter.externalToInternalAttributes(updateItemRequest.getKey());
                AttributeValue rangeKey = null;
                if (tableInfo.hasRangeKey()) {
                    rangeKey = (AttributeValue)key.get(tableInfo.getRangeKey().getAttributeName());
                }
                UpdateItemFunction.this.validateIndexKeyAttributeValuesBeforePuttingFinalRecordToDB(tableInfo, (Map)item, usingUpdateExpression);
                UpdateItemFunction.this.dbAccess.putRecord(tableName, item, (AttributeValue)key.get(tableInfo.getHashKey().getAttributeName()), rangeKey, true);
                updateItemResult.setConsumedCapacity(UpdateItemFunction.this.computeWriteCapacity(tableName, new Mutation(oldItem, item), UpdateItemFunction.this.convertReturnConsumedCapacity(updateItemRequest.getReturnConsumedCapacity()), UpdateItemFunction.this.transactionsMode));
                updateItemResult.setAttributes(UpdateItemFunction.this.localDBOutputConverter.internalToExternalAttributes(UpdateItemFunction.this.getReturnedValsFromUpdate(returnVals, updateExpressionString == null ? updatesToMake : itemChangesForExpr, oldItem, (Map)item)));
            }
        }.execute();
        return updateItemResult;
    }

    private void validateAttributeUpdates(Map<String, AttributeValueUpdate> updates, TableInfo tableInfo, Map<String, AttributeValue> oldItem) {
        if (updates == null) {
            return;
        }
        for (Map.Entry<String, AttributeValueUpdate> entry : updates.entrySet()) {
            AttributeAction curAction;
            String attributeName = this.inputConverter.newAttributeName(entry.getKey());
            if (attributeName.equals(tableInfo.getHashKey().getAttributeName()) || tableInfo.hasRangeKey() && attributeName.equals(tableInfo.getRangeKey().getAttributeName())) {
                throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, String.format(LocalDBClientExceptionMessage.INVALID_PARAMETER_VALUE.getMessage(), "Cannot update attribute " + attributeName + ". This attribute is part of the key"));
            }
            AttributeValueUpdate curUpdate = entry.getValue();
            try {
                if (curUpdate.getAction() == null) {
                    curUpdate.setAction(AttributeAction.PUT);
                }
                curAction = AttributeAction.fromValue((String)curUpdate.getAction());
            } catch (IllegalArgumentException illegalArgs) {
                throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, LocalDBClientExceptionMessage.INVALID_ACTION_TYPE.getMessage());
            }
            AttributeValue curAttr = (AttributeValue)this.inputConverter.externalToInternalAttributeValue(curUpdate.getValue(), true);
            this.validateAttributeUpdate(curAction, curAttr);
            AttributeValue curAttrOld = null;
            if (oldItem != null && oldItem.containsKey(attributeName)) {
                curAttrOld = oldItem.get(attributeName);
            }
            switch (curAction) {
                case DELETE: {
                    this.validateAttributeDelete(curAttr, curAttrOld);
                    break;
                }
                case PUT: {
                    this.validateAttributePut(curAttr);
                    break;
                }
                case ADD: {
                    this.validateAttributeAdd(curAttr, curAttrOld);
                }
            }
            AttributeDefinition lsiKeyDef = tableInfo.getLSIRangeKeyWithAttributeName(attributeName);
            if (lsiKeyDef != null && curAttr != null) {
                try {
                    LocalDBUtils.validateConsistentTypes(lsiKeyDef, curAttr, LocalDBClientExceptionMessage.INCONSISTENT_TYPES);
                } catch (AmazonServiceException serviceException) {
                    throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, LocalDBClientExceptionMessage.INVALID_UPDATE_INDEX_KEY.getMessage());
                }
            }
            if (curAttr == null) continue;
            DDBType type = LocalDBUtils.getDataTypeOfAttributeValue(curAttr);
            if (type == DDBType.N) {
                curUpdate.setValue(new com.amazonaws.services.dynamodbv2.model.AttributeValue().withN(LocalDBUtils.validateNumericValue(curAttr.getN()).toPlainString()));
                continue;
            }
            if (!type.isSet()) continue;
            if (type == DDBType.NS) {
                curUpdate.setValue(new com.amazonaws.services.dynamodbv2.model.AttributeValue().withNS(LocalDBUtils.validateNumberSet(curAttr.getNS())));
                continue;
            }
            LocalDBUtils.validateItemSet(curAttr.getBS(), LocalDBClientExceptionMessage.EMPTY_BINARY_SET.getMessage());
            LocalDBUtils.validateItemSet(curAttr.getSS(), LocalDBClientExceptionMessage.EMPTY_STRING_SET.getMessage());
        }
    }

    private void validateAttributeDelete(AttributeValue curAttr, AttributeValue oldAttr) {
        boolean isSet;
        try {
            isSet = LocalDBUtils.getDataTypeOfAttributeValue(curAttr).isSet();
        } catch (NullPointerException npe) {
            isSet = false;
        }
        if (curAttr != null && !isSet) {
            throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, LocalDBClientExceptionMessage.INVALID_ACTION_DELETE.getMessage());
        }
        if (isSet && oldAttr != null) {
            LocalDBUtils.validateConsistentTypes(curAttr, oldAttr);
        }
    }

    private void validateAttributePutAdd(AttributeValue curAttr) {
        if (curAttr == null) {
            throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, LocalDBClientExceptionMessage.INVALID_ACTION_NO_VALUE.getMessage());
        }
    }

    private void validateAttributePut(AttributeValue curAttr) {
        this.validateAttributePutAdd(curAttr);
        LocalDBUtils.getDataTypeOfAttributeValue(curAttr);
    }

    private void validateAttributeAdd(AttributeValue curAttr, AttributeValue oldAttr) {
        this.validateAttributePutAdd(curAttr);
        DDBType type = LocalDBUtils.getDataTypeOfAttributeValue(curAttr);
        if (type != DDBType.N && !type.isSet() && type != DDBType.L) {
            throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, LocalDBClientExceptionMessage.INVALID_ACTION_ADD.getMessage());
        }
        if (oldAttr != null) {
            LocalDBUtils.validateConsistentTypes(curAttr, oldAttr);
        }
    }

    private void validateAttributeUpdate(AttributeAction action, AttributeValue curAttr) {
        if (curAttr == null && !this.inputConverter.isDelete(action)) {
            this.awsExceptionFactory.INVALID_PARAMETER_VALUE.throwAsException("Only DELETE action is allowed when no attribute value is specified");
        } else {
            DocumentNodeType type;
            DocumentNodeType documentNodeType = type = curAttr != null ? this.inputConverter.getType(curAttr) : null;
            if (this.inputConverter.isDelete(action) && curAttr != null && !this.inputConverter.TypesSupportingAttributeDeleteWithValueUpdate.contains((Object)type)) {
                this.awsExceptionFactory.INVALID_PARAMETER_VALUE.throwAsException("DELETE action with value is not supported for the type " + type);
            } else if (this.inputConverter.isAdd(action) && !this.inputConverter.TypesSupportingAttributeAddUpdate.contains((Object)type)) {
                this.awsExceptionFactory.INVALID_PARAMETER_VALUE.throwAsException("ADD action is not supported for the type " + type);
            }
        }
    }

    private Map<String, AttributeValue> doUpdates(Map<String, AttributeValueUpdate> updatesToMake, Map<String, AttributeValue> item) {
        if (updatesToMake == null) {
            return item;
        }
        for (Map.Entry<String, AttributeValueUpdate> entry : updatesToMake.entrySet()) {
            String attr = entry.getKey();
            AttributeValueUpdate curUpdate = entry.getValue();
            AttributeAction curAction = AttributeAction.valueOf((String)curUpdate.getAction());
            switch (curAction) {
                case ADD: {
                    this.doAdd(attr, curUpdate, item);
                    break;
                }
                case DELETE: {
                    this.doDelete(attr, curUpdate, item);
                    break;
                }
                case PUT: {
                    this.doPut(attr, curUpdate, item);
                }
            }
        }
        return item;
    }

    private void doPut(String attrName, AttributeValueUpdate attrUpdate, Map<String, AttributeValue> item) {
        AttributeValue updateVal = (AttributeValue)this.inputConverter.externalToInternalAttributeValue(attrUpdate.getValue(), false);
        item.put(attrName, updateVal);
    }

    private void doAdd(String attrName, AttributeValueUpdate attrUpdate, Map<String, AttributeValue> item) {
        AttributeValue updateVal = (AttributeValue)this.inputConverter.externalToInternalAttributeValue(attrUpdate.getValue(), false);
        AttributeValue curAttr = item.get(attrName);
        DDBType type = LocalDBUtils.getDataTypeOfAttributeValue(updateVal);
        if (type == DDBType.N) {
            if (curAttr == null) {
                item.put(attrName, updateVal);
            } else {
                BigDecimal curValNum = new BigDecimal(curAttr.getN());
                BigDecimal updateValNum = new BigDecimal(updateVal.getN());
                BigDecimal newValNum = curValNum.add(updateValNum);
                item.put(attrName, new AttributeValue().withN(newValNum.toPlainString()));
            }
        } else if (type.isSet()) {
            if (curAttr == null) {
                item.put(attrName, updateVal);
            } else {
                switch (LocalDBUtils.getDataTypeOfAttributeValue(curAttr)) {
                    case BS: {
                        ArrayList<ByteBuffer> curValBlobSet = new ArrayList<ByteBuffer>(curAttr.getBS());
                        for (ByteBuffer curBuf : updateVal.getBS()) {
                            if (curValBlobSet.contains(curBuf)) continue;
                            curValBlobSet.add(curBuf);
                        }
                        item.put(attrName, new AttributeValue().withBS(curValBlobSet));
                        break;
                    }
                    case NS: {
                        ArrayList<String> curValNumSet = new ArrayList<String>(curAttr.getNS());
                        for (String curNum : updateVal.getNS()) {
                            if (curValNumSet.contains(curNum)) continue;
                            curValNumSet.add(curNum);
                        }
                        item.put(attrName, new AttributeValue().withNS(curValNumSet));
                        break;
                    }
                    case SS: {
                        ArrayList<String> curValStrSet = new ArrayList<String>(curAttr.getSS());
                        for (String curStr : updateVal.getSS()) {
                            if (curValStrSet.contains(curStr)) continue;
                            curValStrSet.add(curStr);
                        }
                        item.put(attrName, new AttributeValue().withSS(curValStrSet));
                        break;
                    }
                }
            }
        } else if (type == DDBType.L) {
            if (curAttr == null) {
                item.put(attrName, updateVal);
            } else if (updateVal.getL() != null && updateVal.getL().size() > 0) {
                AttributeValue resultListAttrVal;
                block22: {
                    ArrayList<AttributeValue> resultList = new ArrayList<AttributeValue>();
                    resultList.addAll(curAttr.getL());
                    resultList.addAll(updateVal.getL());
                    resultListAttrVal = new AttributeValue().withL(resultList);
                    try {
                        LocalDBUtils.setDocumentLevel(1, resultListAttrVal);
                    } catch (Exception ex) {
                        if (!ex.getMessage().equalsIgnoreCase(this.awsExceptionFactory.ITEM_NESTING_LEVELS_LIMIT_EXCEEDED.getMessage())) break block22;
                        this.awsExceptionFactory.ITEM_NESTING_LEVELS_LIMIT_EXCEEDED.throwAsException();
                    }
                }
                item.put(attrName, resultListAttrVal);
            }
        }
    }

    private void doDelete(String attrName, AttributeValueUpdate attrUpdate, Map<String, AttributeValue> item) {
        AttributeValue updateVal = (AttributeValue)this.inputConverter.externalToInternalAttributeValue(attrUpdate.getValue(), true);
        if (updateVal == null) {
            item.remove(attrName);
        } else {
            AttributeValue curAttr = item.get(attrName);
            if (curAttr == null) {
                return;
            }
            DDBType type = LocalDBUtils.getDataTypeOfAttributeValue(curAttr);
            switch (type) {
                case BS: {
                    ArrayList<ByteBuffer> curValBlobSet = new ArrayList<ByteBuffer>(curAttr.getBS());
                    curValBlobSet.removeAll(updateVal.getBS());
                    if (curValBlobSet.isEmpty()) {
                        item.remove(attrName);
                        break;
                    }
                    item.put(attrName, new AttributeValue().withBS(curValBlobSet));
                    break;
                }
                case NS: {
                    ArrayList<String> curValNumSet = new ArrayList<String>(curAttr.getNS());
                    curValNumSet.removeAll(updateVal.getNS());
                    if (curValNumSet.isEmpty()) {
                        item.remove(attrName);
                        break;
                    }
                    item.put(attrName, new AttributeValue().withNS(curValNumSet));
                    break;
                }
                case SS: {
                    ArrayList<String> curValStrSet = new ArrayList<String>(curAttr.getSS());
                    curValStrSet.removeAll(updateVal.getSS());
                    if (curValStrSet.isEmpty()) {
                        item.remove(attrName);
                        break;
                    }
                    item.put(attrName, new AttributeValue().withSS(curValStrSet));
                    break;
                }
                default: {
                    LocalDBUtils.ldClientFail(LocalDBClientExceptionType.UNREACHABLE_CODE);
                }
            }
        }
    }
}

