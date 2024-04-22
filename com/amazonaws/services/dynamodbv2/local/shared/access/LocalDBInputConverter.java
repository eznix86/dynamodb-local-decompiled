/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  com.amazonaws.services.dynamodbv2.model.AttributeAction
 *  com.amazonaws.services.dynamodbv2.model.AttributeValue
 *  com.amazonaws.services.dynamodbv2.model.AttributeValueUpdate
 *  com.amazonaws.services.dynamodbv2.model.ComparisonOperator
 *  com.amazonaws.services.dynamodbv2.model.Condition
 *  com.amazonaws.services.dynamodbv2.model.ConditionalOperator
 *  com.amazonaws.services.dynamodbv2.model.ExpectedAttributeValue
 *  com.amazonaws.services.dynamodbv2.model.KeysAndAttributes
 *  com.amazonaws.services.dynamodbv2.model.ReturnConsumedCapacity
 *  com.amazonaws.services.dynamodbv2.model.Select
 *  com.amazonaws.services.dynamodbv2.model.WriteRequest
 */
package com.amazonaws.services.dynamodbv2.local.shared.access;

import com.amazonaws.services.dynamodbv2.datamodel.DocumentFactory;
import com.amazonaws.services.dynamodbv2.datamodel.DocumentNodeType;
import com.amazonaws.services.dynamodbv2.dbenv.DbEnv;
import com.amazonaws.services.dynamodbv2.local.shared.access.ByteBufferComparator;
import com.amazonaws.services.dynamodbv2.local.shared.access.LocalDBUtils;
import com.amazonaws.services.dynamodbv2.local.shared.access.LocalDBValidatorUtils;
import com.amazonaws.services.dynamodbv2.local.shared.exceptions.LocalDBClientExceptionMessage;
import com.amazonaws.services.dynamodbv2.local.shared.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.local.shared.model.AttributeValueUpdate;
import com.amazonaws.services.dynamodbv2.local.shared.model.Condition;
import com.amazonaws.services.dynamodbv2.local.shared.model.DeleteRequest;
import com.amazonaws.services.dynamodbv2.local.shared.model.KeysAndAttributes;
import com.amazonaws.services.dynamodbv2.local.shared.model.PutRequest;
import com.amazonaws.services.dynamodbv2.local.shared.model.WriteRequest;
import com.amazonaws.services.dynamodbv2.local.shared.validate.ErrorFactory;
import com.amazonaws.services.dynamodbv2.local.shared.validate.InputConverter;
import com.amazonaws.services.dynamodbv2.local.shared.validate.KeyConditionExpressionExtractor;
import com.amazonaws.services.dynamodbv2.model.AttributeAction;
import com.amazonaws.services.dynamodbv2.model.ComparisonOperator;
import com.amazonaws.services.dynamodbv2.model.ConditionalOperator;
import com.amazonaws.services.dynamodbv2.model.ExpectedAttributeValue;
import com.amazonaws.services.dynamodbv2.model.ReturnConsumedCapacity;
import com.amazonaws.services.dynamodbv2.model.Select;
import com.amazonaws.services.dynamodbv2.rr.ExpressionWrapper;
import java.math.BigDecimal;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class LocalDBInputConverter
extends InputConverter<AttributeValue, com.amazonaws.services.dynamodbv2.model.AttributeValue, List<AttributeValue>, Map<String, AttributeValue>, String, AttributeValueUpdate, com.amazonaws.services.dynamodbv2.model.AttributeValueUpdate, com.amazonaws.services.dynamodbv2.local.shared.model.ExpectedAttributeValue, ExpectedAttributeValue, Condition, com.amazonaws.services.dynamodbv2.model.Condition, KeysAndAttributes, com.amazonaws.services.dynamodbv2.model.KeysAndAttributes, WriteRequest, com.amazonaws.services.dynamodbv2.model.WriteRequest, AttributeAction, ComparisonOperator, ConditionalOperator, Select, ReturnConsumedCapacity, String> {
    public LocalDBInputConverter(DbEnv dbEnv, ErrorFactory errorFactory, DocumentFactory documentFactory, int maxKeyAttributeNameSize) {
        super(dbEnv, errorFactory, documentFactory, true, 409600, maxKeyAttributeNameSize, 100, 25, 0x200000);
    }

    @Override
    public String newAttributeName(String val) {
        if (val == null || val.length() == 0) {
            this.errorFactory.EMPTY_ATTRIBUTE_NAME.throwAsException();
        }
        if (this.getLengthInUTF8Bytes(val) > 65535) {
            this.errorFactory.ATTRIBUTE_NAME_TOO_LARGE.throwAsException();
        }
        return val;
    }

    @Override
    protected int getLengthInUTF8Bytes(String val) {
        return val.getBytes(LocalDBUtils.UTF8).length;
    }

    @Override
    protected AttributeValue newS(String val) {
        this.validateStringValue(val);
        AttributeValue ival = new AttributeValue();
        ival.setS(val);
        return ival;
    }

    @Override
    protected AttributeValue newN(String val) {
        LocalDBUtils.validateNumericValue(val);
        AttributeValue ival = new AttributeValue();
        ival.setN(val);
        return ival;
    }

    @Override
    protected AttributeValue newB(ByteBuffer binaryAttribute) {
        this.validateNullByteBuffer(binaryAttribute);
        AttributeValue ival = new AttributeValue();
        ival.setB(binaryAttribute);
        return ival;
    }

    @Override
    protected AttributeValue newSS(List<String> stringSetAttribute) {
        this.validateStringSetValue(stringSetAttribute);
        AttributeValue ival = new AttributeValue();
        Collections.sort(stringSetAttribute);
        ival.setSS(stringSetAttribute);
        return ival;
    }

    @Override
    protected AttributeValue newNS(List<String> numberSetAttribute) {
        LocalDBUtils.validateNumberSet(numberSetAttribute);
        AttributeValue ival = new AttributeValue();
        ArrayList<String> result = null;
        ArrayList<BigDecimal> resultDecimals = new ArrayList<BigDecimal>();
        for (String s : numberSetAttribute) {
            resultDecimals.add(new BigDecimal(s));
        }
        Collections.sort(resultDecimals);
        result = new ArrayList<String>();
        for (BigDecimal decimal : resultDecimals) {
            result.add(decimal.toString());
        }
        ival.setNS(result);
        return ival;
    }

    @Override
    protected AttributeValue newBS(List<ByteBuffer> binarySetAttribute) {
        this.validateNullBinarySet(binarySetAttribute);
        AttributeValue ival = new AttributeValue();
        Collections.sort(binarySetAttribute, ByteBufferComparator.singleton);
        ival.setBS(binarySetAttribute);
        return ival;
    }

    @Override
    protected AttributeValue newBoolean(Boolean val) {
        AttributeValue ival = new AttributeValue();
        ival.setBOOL(val);
        return ival;
    }

    @Override
    protected AttributeValue newNull() {
        AttributeValue ival = new AttributeValue();
        ival.setNULL(true);
        return ival;
    }

    @Override
    protected AttributeValue newM(Map<String, AttributeValue> val) {
        AttributeValue ival = new AttributeValue();
        ival.setM(val);
        return ival;
    }

    @Override
    protected AttributeValue newL(List<AttributeValue> val) {
        AttributeValue ival = new AttributeValue();
        ival.setL(val);
        return ival;
    }

    @Override
    protected int getAttributeSizeInBytes(AttributeValue val) {
        return (int)LocalDBUtils.getAttributeValueSizeBytes(val);
    }

    @Override
    public DocumentNodeType getType(AttributeValue val) {
        return val.getNodeType();
    }

    @Override
    protected List<AttributeValue> newAttrValueList(int depth, int capacity) {
        return new ArrayList<AttributeValue>(capacity);
    }

    @Override
    protected void addAttrValue(List<AttributeValue> list, AttributeValue val) {
        list.add(val);
    }

    @Override
    protected Map<String, AttributeValue> newAttrValueMap(int depth, int capacity) {
        return new HashMap<String, AttributeValue>(capacity);
    }

    @Override
    protected void putAttrValue(Map<String, AttributeValue> map2, String name, AttributeValue val) {
        map2.put(name, val);
    }

    @Override
    protected boolean isValidNestedLevel(int depth) {
        return depth >= 0 && depth < 32;
    }

    @Override
    protected String getS(com.amazonaws.services.dynamodbv2.model.AttributeValue val) {
        return val.getS();
    }

    @Override
    protected String getN(com.amazonaws.services.dynamodbv2.model.AttributeValue val) {
        return val.getN();
    }

    @Override
    protected ByteBuffer getB(com.amazonaws.services.dynamodbv2.model.AttributeValue val) {
        return val.getB();
    }

    @Override
    protected Boolean isBOOL(com.amazonaws.services.dynamodbv2.model.AttributeValue val) {
        return val.getBOOL();
    }

    @Override
    protected Boolean isNULL(com.amazonaws.services.dynamodbv2.model.AttributeValue val) {
        return val.getNULL();
    }

    @Override
    protected List<String> getSS(com.amazonaws.services.dynamodbv2.model.AttributeValue val) {
        return val.getSS();
    }

    @Override
    protected List<String> getNS(com.amazonaws.services.dynamodbv2.model.AttributeValue val) {
        return val.getNS();
    }

    @Override
    protected List<ByteBuffer> getBS(com.amazonaws.services.dynamodbv2.model.AttributeValue val) {
        return val.getBS();
    }

    @Override
    protected List<com.amazonaws.services.dynamodbv2.model.AttributeValue> getL(com.amazonaws.services.dynamodbv2.model.AttributeValue val) {
        return val.getL();
    }

    @Override
    protected Map<String, com.amazonaws.services.dynamodbv2.model.AttributeValue> getM(com.amazonaws.services.dynamodbv2.model.AttributeValue val) {
        return val.getM();
    }

    @Override
    protected com.amazonaws.services.dynamodbv2.local.shared.model.ExpectedAttributeValue newExpected(AttributeValue val, Boolean exists, List<AttributeValue> avList, ComparisonOperator op) {
        com.amazonaws.services.dynamodbv2.local.shared.model.ExpectedAttributeValue expected = new com.amazonaws.services.dynamodbv2.local.shared.model.ExpectedAttributeValue();
        expected.setValue(val);
        expected.setExists(exists);
        expected.setAttributeValueList(avList);
        expected.setComparisonOperator(op);
        return expected;
    }

    @Override
    protected String getExpectedComparisonOperator(ExpectedAttributeValue val) {
        return val.getComparisonOperator();
    }

    @Override
    protected List<com.amazonaws.services.dynamodbv2.model.AttributeValue> getExpectedAttributeValueList(ExpectedAttributeValue val) {
        return val.getAttributeValueList();
    }

    @Override
    protected com.amazonaws.services.dynamodbv2.model.AttributeValue getExpectedValueExternal(ExpectedAttributeValue val) {
        return val.getValue();
    }

    @Override
    protected Boolean isExists(ExpectedAttributeValue val) {
        return val.isExists();
    }

    @Override
    protected AttributeValueUpdate newUpdate(AttributeValue val, AttributeAction action) {
        return new AttributeValueUpdate(val, action);
    }

    @Override
    protected AttributeValue getUpdateValueInternal(AttributeValueUpdate val) {
        return val.getValue();
    }

    @Override
    protected AttributeAction getUpdateActionInternal(AttributeValueUpdate val) {
        return AttributeAction.valueOf((String)val.getAction());
    }

    @Override
    protected String getUpdateActionExternal(com.amazonaws.services.dynamodbv2.model.AttributeValueUpdate val) {
        return val.getAction();
    }

    @Override
    protected com.amazonaws.services.dynamodbv2.model.AttributeValue getUpdateValueExternal(com.amazonaws.services.dynamodbv2.model.AttributeValueUpdate val) {
        return val.getValue();
    }

    @Override
    protected AttributeAction newAction(String val) {
        AttributeAction attributeAction = null;
        try {
            if (val == null) {
                return AttributeAction.PUT;
            }
            attributeAction = AttributeAction.fromValue((String)val);
        } catch (IllegalArgumentException illegalArgs) {
            this.errorFactory.INVALID_ACTION_TYPE.throwAsException();
        }
        return attributeAction;
    }

    @Override
    public boolean isDelete(AttributeAction val) {
        return val == AttributeAction.DELETE;
    }

    @Override
    public boolean isAdd(AttributeAction val) {
        return val == AttributeAction.ADD;
    }

    @Override
    protected ComparisonOperator newComparisonOperator(String val) {
        try {
            return ComparisonOperator.valueOf((String)val);
        } catch (Exception e) {
            this.errorFactory.INVALID_COMPARISON.throwAsException();
            return null;
        }
    }

    @Override
    public void validateArgumentsForComparisonOperator(ComparisonOperator op, List<AttributeValue> attributeValues) {
        LocalDBValidatorUtils.validateArgumentsForComparisonOperator(op, attributeValues, this.errorFactory);
    }

    @Override
    protected Condition newCondition(ComparisonOperator op, List<AttributeValue> val) {
        Condition condition = new Condition();
        condition.setComparisonOperator(op);
        condition.setAttributeValueList(val);
        return condition;
    }

    @Override
    protected String getConditionComparisonOperator(com.amazonaws.services.dynamodbv2.model.Condition val) {
        return val.getComparisonOperator();
    }

    @Override
    protected List<com.amazonaws.services.dynamodbv2.model.AttributeValue> getConditionAttributeValueList(com.amazonaws.services.dynamodbv2.model.Condition val) {
        return val.getAttributeValueList();
    }

    @Override
    protected ConditionalOperator newConditionalOperator(String val) {
        return ConditionalOperator.valueOf((String)val);
    }

    @Override
    protected Select newSelect(String val) {
        return Select.valueOf((String)val);
    }

    @Override
    protected ReturnConsumedCapacity newReturnConsumedCapacity(String val) {
        return ReturnConsumedCapacity.valueOf((String)val);
    }

    @Override
    protected WriteRequest newPutRequest(String tableName, Map<String, AttributeValue> item) {
        return new WriteRequest(new PutRequest(item));
    }

    @Override
    protected WriteRequest newDeleteRequest(String tableName, Map<String, AttributeValue> key) {
        return new WriteRequest(new DeleteRequest(key));
    }

    @Override
    protected boolean isWriteDelete(com.amazonaws.services.dynamodbv2.model.WriteRequest writeRequest) {
        return writeRequest.getDeleteRequest() != null;
    }

    @Override
    protected boolean isWritePut(com.amazonaws.services.dynamodbv2.model.WriteRequest writeRequest) {
        return writeRequest.getPutRequest() != null;
    }

    @Override
    protected Map<String, com.amazonaws.services.dynamodbv2.model.AttributeValue> getPutRequestItem(com.amazonaws.services.dynamodbv2.model.WriteRequest writeRequest) {
        return writeRequest.getPutRequest() != null ? writeRequest.getPutRequest().getItem() : null;
    }

    @Override
    protected Map<String, com.amazonaws.services.dynamodbv2.model.AttributeValue> getDeleteRequestKey(com.amazonaws.services.dynamodbv2.model.WriteRequest writeRequest) {
        return writeRequest.getDeleteRequest() != null ? writeRequest.getDeleteRequest().getKey() : null;
    }

    @Override
    protected KeysAndAttributes newKeysAndAttributes(Set<Map<String, AttributeValue>> compositeKeySet, Set<String> attrsToGet, Boolean isConsistentRead, String projectionExpression, Map<String, String> expressionAttributeNames) {
        KeysAndAttributes kas = new KeysAndAttributes();
        kas.setKeys(compositeKeySet);
        kas.setAttributesToGet(attrsToGet);
        kas.setConsistentRead(isConsistentRead);
        kas.setProjectionExpression(projectionExpression);
        kas.setExpressionAttributeNames(expressionAttributeNames);
        return kas;
    }

    @Override
    protected List<Map<String, com.amazonaws.services.dynamodbv2.model.AttributeValue>> getKeys(com.amazonaws.services.dynamodbv2.model.KeysAndAttributes kas) {
        return kas.getKeys();
    }

    @Override
    protected List<String> getAttributesToGet(com.amazonaws.services.dynamodbv2.model.KeysAndAttributes kas) {
        return kas.getAttributesToGet();
    }

    @Override
    protected String getProjectionExpression(com.amazonaws.services.dynamodbv2.model.KeysAndAttributes kas) {
        return kas.getProjectionExpression();
    }

    @Override
    protected Map<String, String> getExpressionAttributeNames(com.amazonaws.services.dynamodbv2.model.KeysAndAttributes kas) {
        return kas.getExpressionAttributeNames();
    }

    @Override
    protected Boolean isConsistentRead(com.amazonaws.services.dynamodbv2.model.KeysAndAttributes kas) {
        return kas.isConsistentRead();
    }

    @Override
    protected String newTableName(String accountId, String tableName) {
        return tableName;
    }

    private String validateStringValue(String value) {
        if (value == null) {
            this.errorFactory.INVALID_PARAMETER_VALUE.throwAsException("An AttributeValue may not contain a null string");
        }
        this.validateIntValueIsPackable(value.length(), false);
        return value;
    }

    private List<String> validateStringSetValue(List<String> stringSet) {
        HashSet<String> stringSetEntries;
        if (stringSet == null) {
            this.errorFactory.INVALID_PARAMETER_VALUE.throwAsException("An string set cannot be null");
        }
        if (stringSet.size() < 1) {
            this.errorFactory.INVALID_PARAMETER_VALUE.throwAsException(LocalDBClientExceptionMessage.EMPTY_STRING_SET.getMessage());
        }
        if ((stringSetEntries = new HashSet<String>(stringSet)).size() < stringSet.size()) {
            this.errorFactory.INVALID_PARAMETER_VALUE.throwAsException("Input collection " + stringSet + " contains duplicates");
        }
        for (String stringValue : stringSet) {
            if (stringValue != null) continue;
            this.errorFactory.INVALID_PARAMETER_VALUE.throwAsException("An string set may not have a null string as a member");
        }
        return stringSet;
    }

    private void validateNullBinarySet(List<ByteBuffer> binarySetValue) {
        HashSet<ByteBuffer> blobSetEntries;
        if (binarySetValue == null) {
            this.errorFactory.ITEM_CONTAINS_NULL_ATTRVALUE.throwAsException();
        }
        if (binarySetValue.isEmpty()) {
            this.errorFactory.INVALID_PARAMETER_VALUE.throwAsException(LocalDBClientExceptionMessage.EMPTY_BINARY_SET.getMessage());
        }
        if ((blobSetEntries = new HashSet<ByteBuffer>(binarySetValue)).size() < binarySetValue.size()) {
            this.errorFactory.INVALID_PARAMETER_VALUE.throwAsException("Input collection of type BS contains duplicates.");
        }
        for (ByteBuffer byteBuffer : binarySetValue) {
            this.validateNullByteBuffer(byteBuffer);
        }
    }

    private void validateNullByteBuffer(ByteBuffer value) {
        if (value == null) {
            this.errorFactory.ITEM_CONTAINS_NULL_ATTRVALUE.throwAsException();
        }
    }

    private void validateIntValueIsPackable(int value, boolean signed) {
        if (signed) {
            if (value >= 0x200000 || value <= -2097153) {
                this.errorFactory.ITEM_TOO_LARGE.throwAsException();
            }
        } else if (value >= 0x400000 || value < 0) {
            this.errorFactory.ITEM_TOO_LARGE.throwAsException();
        }
    }

    public Map<String, Condition> externalToInternalKeyConditions(Map<String, com.amazonaws.services.dynamodbv2.model.Condition> keyConditions, ExpressionWrapper keyConditionExpressionWrapper) {
        if (keyConditions != null) {
            return this.externalToInternalConditions(keyConditions);
        }
        if (keyConditionExpressionWrapper != null) {
            KeyConditionExpressionExtractor extractor = new KeyConditionExpressionExtractor(this.dbEnv, this.errorFactory);
            return extractor.extractKeyConditions(keyConditionExpressionWrapper.getExpression());
        }
        return null;
    }
}

