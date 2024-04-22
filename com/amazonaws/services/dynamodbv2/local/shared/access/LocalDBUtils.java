/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  com.amazonaws.services.dynamodbv2.model.AttributeDefinition
 *  com.amazonaws.services.dynamodbv2.model.GlobalSecondaryIndex
 *  com.amazonaws.services.dynamodbv2.model.GlobalSecondaryIndexDescription
 *  com.amazonaws.services.dynamodbv2.model.IndexStatus
 *  com.amazonaws.services.dynamodbv2.model.KeySchemaElement
 *  com.amazonaws.services.dynamodbv2.model.LocalSecondaryIndex
 *  com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput
 *  com.amazonaws.services.dynamodbv2.model.ProvisionedThroughputDescription
 *  org.apache.logging.log4j.Level
 *  org.apache.logging.log4j.Logger
 */
package com.amazonaws.services.dynamodbv2.local.shared.access;

import com.amazonaws.services.dynamodbv2.datamodel.DocumentFactory;
import com.amazonaws.services.dynamodbv2.datamodel.Expression;
import com.amazonaws.services.dynamodbv2.datamodel.ExpressionExecutor;
import com.amazonaws.services.dynamodbv2.datamodel.ProjectionExpression;
import com.amazonaws.services.dynamodbv2.dbenv.DbEnv;
import com.amazonaws.services.dynamodbv2.exceptions.AWSExceptionFactory;
import com.amazonaws.services.dynamodbv2.exceptions.AmazonServiceExceptionType;
import com.amazonaws.services.dynamodbv2.local.shared.access.DDBType;
import com.amazonaws.services.dynamodbv2.local.shared.access.LocalDBValidatorUtils;
import com.amazonaws.services.dynamodbv2.local.shared.access.TableInfo;
import com.amazonaws.services.dynamodbv2.local.shared.access.sqlite.SQLiteIndexElement;
import com.amazonaws.services.dynamodbv2.local.shared.dataaccess.LocalDocumentFactory;
import com.amazonaws.services.dynamodbv2.local.shared.env.LocalDBEnv;
import com.amazonaws.services.dynamodbv2.local.shared.exceptions.ExpressionExecutionException;
import com.amazonaws.services.dynamodbv2.local.shared.exceptions.LocalDBAccessException;
import com.amazonaws.services.dynamodbv2.local.shared.exceptions.LocalDBAccessExceptionType;
import com.amazonaws.services.dynamodbv2.local.shared.exceptions.LocalDBClientException;
import com.amazonaws.services.dynamodbv2.local.shared.exceptions.LocalDBClientExceptionMessage;
import com.amazonaws.services.dynamodbv2.local.shared.exceptions.LocalDBClientExceptionType;
import com.amazonaws.services.dynamodbv2.local.shared.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.local.shared.validate.ErrorFactory;
import com.amazonaws.services.dynamodbv2.model.AttributeDefinition;
import com.amazonaws.services.dynamodbv2.model.GlobalSecondaryIndex;
import com.amazonaws.services.dynamodbv2.model.GlobalSecondaryIndexDescription;
import com.amazonaws.services.dynamodbv2.model.IndexStatus;
import com.amazonaws.services.dynamodbv2.model.KeySchemaElement;
import com.amazonaws.services.dynamodbv2.model.LocalSecondaryIndex;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughputDescription;
import java.lang.invoke.CallSite;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Logger;

public class LocalDBUtils {
    public static final Charset UTF8 = StandardCharsets.UTF_8;
    public static final int HASH_KEY_LOC = 0;
    public static final int RANGE_KEY_LOC = 1;
    public static final int MIN_EXPONENT = -130;
    public static final int MAX_EXPONENT = 126;
    public static final int MAX_PRECISION = 38;
    public static final int MAX_RETURNED_BATCH_GET = 100;
    public static final int MAX_ITEMS_BATCH_WRITE = 25;
    public static final int MAX_LIST_TABLES_LIMIT = 100;
    public static final long MAX_PROJECTED_ATTRIBUTES = 100L;
    public static final int MAX_ATTRIBUTENAME_SIZE = 65535;
    public static final long MAX_ITEM_SIZE_BYTES = 409600L;
    public static final long MAX_MAX_ITEM_SIZE = 0x100000L;
    public static final long MAX_BATCH_GET_ITEM_SIZE = 0x1000000L;
    public static final long MAX_HASH_KEY_VALUE_SIZE_BYTES = 2048L;
    public static final long MAX_RANGE_KEY_VALUE_SIZE_BYTES = 1024L;
    public static final long MAX_KEY_ATTRIBUTE_NAME_SIZE_BYTES = 65536L;
    public static final int MAX_ALLOWED_DOCUMENT_LEVEL = 32;
    public static final int DOCUMENT_ROOT_LEVEL = 0;
    public static final int MAX_INFLIGHT_CREATE_OR_DELETE_INDEXES_PER_TABLE = 1;
    public static final int MAX_INFLIGHT_UPDATE_TABLE_WITH_CREATE_INDEXES = 5;
    public static final long DELAY_BEFORE_SCHEDULING_JOBS_AGAIN = TimeUnit.SECONDS.toMillis(1L);
    public static final long LONG_DELAY_TO_HOLD_TRANSIENT_STATUSES = TimeUnit.SECONDS.toMillis(15L);
    public static final int LOGICAL_SIZE_OF_EMPTY_DOCUMENT = 3;
    public static final int BASE_LOGICAL_SIZE_OF_NESTED_TYPES = 1;
    public static final int MAX_EXPRESSION_OPERATOR_COUNT = 300;
    public static final int MAX_EXPRESSION_SIZE = 4096;
    public static final int MAX_EXPRESSION_TREE_SIZE = 0x100000;
    public static final int MAX_PARAMETER_MAP_KEY_SIZE = 255;
    public static final int MAX_NUM_OPERANDS_FOR_IN = 100;
    public static final int THREE_BYTES_POS_LIMIT = 0x200000;
    public static final int THREE_BYTES_NEG_LIMIT = -2097153;
    public static final int THREE_BYTES_UNSIGNED_LIMIT = 0x400000;
    public static final String DISABLED_FUNCTIONS = "";
    public static final int MAX_TRANSACT_CLIENT_TOKEN_SIZE = 36;
    public static final int MAX_EXPRESSION_SUBSTITUTION_MAP_SIZE = 0x200000;
    public static final long MB_RETURN_MAX = 0x100000L;
    public static final long MAX_THROUGHPUT_TABLE = 40000L;
    public static final long MAX_THROUGHPUT_ACCOUNT = 80000L;
    public static final int MAX_LSI_PER_TABLE = 5;
    public static final int MAX_GSI_PER_TABLE = 20;
    public static final int MAX_TRANSACT_ITEMS = 100;
    public static final long TRANSACT_REQUEST_MAX_PAYLOAD_SIZE = 0x400000L;
    public static final long TRANSACT_RESPONSE_MAX_PAYLOAD_SIZE = 0x400000L;
    public static final int MAX_TOTAL_SEGMENTS = 1000000;
    public static final int NUMBER_EXPONENT_BOUND_START = -128;
    public static final int NUMBER_EXPONENT_BOUND_END = 126;
    public static final int MAX_NUMBER_OF_DIGITS_TO_ENCODE = 38;
    public static final int MAX_NUMBER_OF_BYTES_WITHOUT_EXPONENT = 20;
    public static final int MAX_NUMBER_OF_BYTES = 21;
    public static final int SHA1_KEY_SIZE = 20;
    public static final int MAX_LIST_STREAMS_LIMIT = 100;
    public static final int MIN_DESCRIBE_STREAM_LIMIT = 1;
    public static final Integer MAX_DESCRIBE_STREAM_LIMIT = 100;
    public static final int MIN_SHARD_ID_LENGTH = 28;
    public static final int MAX_SHARD_ID_LENGTH = 65;
    public static final int MIN_SEQUENCE_NUMBER_LENGTH = 21;
    public static final int MAX_SEQUENCE_NUMBER_LENGTH = 40;
    public static final int FOUR_KB = 4096;
    public static final byte[] MAX_SHA1_BYTES = new byte[20];
    public static final BigInteger MAX_HASH_KEY;
    public static final long ITEM_EXPIRY_DELAY_BETWEEN_DELETION;
    public static final int THREAD_POOL_SIZE = 10;
    protected static final String TABLE_RESOURCE_PATH = "table/";
    protected static final String INDEX_RESOURCE_PATH = "/index/";
    protected static final String STREAM_RESOURCE_PATH = "/stream/";
    private static final int RAW_BOOLEAN_LENGTH_IN_BYTES = 1;
    private static final int RAW_NULL_TYPE_LENGTH_IN_BYTES = 1;
    private static final String STREAM_ARN_FORMAT = "arn:aws:dynamodb:%s:%s:table/%s/stream/%s";
    private static final byte[] SALT;
    private static final ThreadLocal<MessageDigest> m_digest;
    private static final String longToSequenceNumberFormatString = "%021d";
    private static final TimeZone tz;
    private static final DateFormat df;
    private static final ErrorFactory errorFactory;

    public static AttributeDefinition findAttributeDefinition(KeySchemaElement attributeToFind, List<AttributeDefinition> attributes) {
        String attrName = attributeToFind.getAttributeName();
        for (AttributeDefinition curAttr : attributes) {
            String curName = curAttr.getAttributeName();
            if (!curName.equals(attrName)) continue;
            return curAttr;
        }
        return null;
    }

    public static DDBType getDataTypeOfAttributeDefinition(AttributeDefinition attrDef) {
        String datatype = attrDef.getAttributeType();
        if (datatype == null) {
            throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, LocalDBClientExceptionMessage.NO_DATATYPE.getMessage());
        }
        if (datatype.equals("S")) {
            return DDBType.S;
        }
        if (datatype.equals("B")) {
            return DDBType.B;
        }
        if (datatype.equals("N")) {
            return DDBType.N;
        }
        throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, LocalDBClientExceptionMessage.INVALID_ATTRIBUTE_TYPE.getMessage());
    }

    public static List<DDBType> getDataTypesOfAttributeDefinitions(List<AttributeDefinition> attrDefs, boolean simple) {
        ArrayList<DDBType> types = new ArrayList<DDBType>();
        ArrayList<CallSite> errors = new ArrayList<CallSite>();
        int i = 1;
        for (AttributeDefinition attrDef : attrDefs) {
            String string = attrDef.getAttributeType();
            if (string == null) {
                throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, LocalDBClientExceptionMessage.NO_DATATYPE.getMessage());
            }
            if (string.equals("S")) {
                types.add(DDBType.S);
            } else if (string.equals("B")) {
                types.add(DDBType.B);
            } else if (string.equals("N")) {
                types.add(DDBType.N);
            } else if (!simple) {
                errors.add((CallSite)((Object)("Value '" + string + "' at 'attributeDefinitions." + i + ".member.attributeType' failed to satisfy constraint: " + LocalDBClientExceptionMessage.INVALID_ATTRIBUTE_TYPE.getMessage())));
            } else {
                throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, LocalDBClientExceptionMessage.INVALID_ATTRIBUTE_TYPE.getMessage());
            }
            ++i;
        }
        if (!errors.isEmpty()) {
            StringBuilder sb = new StringBuilder().append(errors.size()).append(" validation error");
            if (errors.size() > 1) {
                sb.append("s");
            }
            sb.append(" detected: ");
            i = 0;
            for (String string : errors) {
                (i > 0 ? sb.append("; ") : sb).append(string);
                ++i;
            }
            throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, sb.toString());
        }
        return types;
    }

    public static DDBType getDataTypeOfScalarAttributeValue(AttributeValue attrVal) {
        boolean defined = false;
        DDBType type = null;
        if (attrVal.getB() != null) {
            defined = true;
            type = DDBType.B;
        }
        if (attrVal.getS() != null) {
            if (defined) {
                throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, LocalDBClientExceptionMessage.DOUBLE_DATATYPE.getMessage());
            }
            defined = true;
            type = DDBType.S;
        }
        if (attrVal.getN() != null) {
            if (defined) {
                throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, LocalDBClientExceptionMessage.DOUBLE_DATATYPE.getMessage());
            }
            defined = true;
            type = DDBType.N;
            LocalDBUtils.validateNumericValue(attrVal.getN()).toPlainString();
        }
        if (!defined) {
            throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, LocalDBClientExceptionMessage.INVALID_DATATYPE.getMessage());
        }
        if (attrVal.getNS() != null || attrVal.getSS() != null || attrVal.getBS() != null) {
            throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, LocalDBClientExceptionMessage.DOUBLE_DATATYPE.getMessage());
        }
        return type;
    }

    private static boolean isScalar(AttributeValue attrVal) {
        return attrVal.getB() != null || attrVal.getN() != null || attrVal.getS() != null;
    }

    public static DDBType getDataTypeOfAttributeValue(AttributeValue attrVal) {
        boolean defined = false;
        DDBType type = null;
        if (LocalDBUtils.isScalar(attrVal)) {
            type = LocalDBUtils.getDataTypeOfScalarAttributeValue(attrVal);
            defined = true;
        }
        if (attrVal.getBS() != null) {
            if (defined) {
                throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, LocalDBClientExceptionMessage.DOUBLE_DATATYPE.getMessage());
            }
            LocalDBUtils.validateItemSet(attrVal.getBS(), LocalDBClientExceptionMessage.EMPTY_BINARY_SET.getMessage());
            type = DDBType.BS;
            defined = true;
        }
        if (attrVal.getSS() != null) {
            if (defined) {
                throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, LocalDBClientExceptionMessage.DOUBLE_DATATYPE.getMessage());
            }
            LocalDBUtils.validateItemSet(attrVal.getSS(), LocalDBClientExceptionMessage.EMPTY_STRING_SET.getMessage());
            type = DDBType.SS;
            defined = true;
        }
        if (attrVal.getNS() != null) {
            if (defined) {
                throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, LocalDBClientExceptionMessage.DOUBLE_DATATYPE.getMessage());
            }
            LocalDBUtils.validateNumberSet(attrVal.getNS());
            type = DDBType.NS;
            defined = true;
        }
        if (attrVal.getBOOL() != null) {
            if (defined) {
                throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, LocalDBClientExceptionMessage.DOUBLE_DATATYPE.getMessage());
            }
            type = DDBType.BOOL;
            defined = true;
        }
        if (attrVal.getNULL() != null) {
            if (defined) {
                throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, LocalDBClientExceptionMessage.DOUBLE_DATATYPE.getMessage());
            }
            if (!attrVal.getNULL().booleanValue()) {
                throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, LocalDBClientExceptionMessage.FALSE_NULL_DATATYPE.getMessage());
            }
            type = DDBType.NULL;
            defined = true;
        }
        if (attrVal.getM() != null) {
            if (defined) {
                new AWSExceptionFactory().MULTI_ATTRIBUTE_VALUE.throwAsException();
            }
            type = DDBType.M;
            defined = true;
        }
        if (attrVal.getL() != null) {
            if (defined) {
                new AWSExceptionFactory().MULTI_ATTRIBUTE_VALUE.throwAsException();
            }
            type = DDBType.L;
            defined = true;
        }
        if (!defined) {
            throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, LocalDBClientExceptionMessage.INVALID_DATATYPE.getMessage());
        }
        return type;
    }

    public static BigDecimal validateNumericValue(String numVal) {
        if (numVal == null) {
            throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, LocalDBClientExceptionMessage.NON_NUMERIC_VALUE.getMessage());
        }
        try {
            BigDecimal value = new BigDecimal(numVal);
            int significantDigits = value.stripTrailingZeros().precision();
            if (significantDigits > 38) {
                throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, LocalDBClientExceptionMessage.NUMBER_TOO_PRECISE.getMessage());
            }
            int exponent = value.precision() - value.scale() - 1;
            if (value.compareTo(BigDecimal.ZERO) != 0) {
                if (exponent >= 126) {
                    throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, LocalDBClientExceptionMessage.NUMBER_OVERFLOW.getMessage());
                }
                if (exponent < -130) {
                    throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, LocalDBClientExceptionMessage.NUMBER_UNDERFLOW.getMessage());
                }
            }
            return value.stripTrailingZeros();
        } catch (NumberFormatException nfe) {
            throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, LocalDBClientExceptionMessage.NON_NUMERIC_VALUE.getMessage());
        }
    }

    public static List<String> validateNumberSet(List<String> numberSet) {
        if (numberSet.isEmpty()) {
            LocalDBUtils.errorFactory.INVALID_PARAMETER_VALUE.throwAsException(LocalDBClientExceptionMessage.EMPTY_NUMBER_SET.getMessage());
        }
        ArrayList<String> numberListTrimmed = new ArrayList<String>();
        HashSet<BigDecimal> allNumbers = new HashSet<BigDecimal>();
        for (String curNum : numberSet) {
            BigDecimal curBigDecimal = LocalDBUtils.validateNumericValue(curNum);
            if (allNumbers.contains(curBigDecimal)) {
                throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, LocalDBClientExceptionMessage.INVALID_SET_DUPLICATES.getMessage());
            }
            allNumbers.add(curBigDecimal);
            numberListTrimmed.add(curBigDecimal.toPlainString());
        }
        return numberListTrimmed;
    }

    public static <T> void validateItemSet(List<T> itemSet, String emptySetErrorMessage) {
        if (itemSet == null) {
            return;
        }
        if (itemSet.isEmpty()) {
            LocalDBUtils.errorFactory.INVALID_PARAMETER_VALUE.throwAsException(emptySetErrorMessage);
        }
        HashSet<T> allItems = new HashSet<T>();
        for (T curItem : itemSet) {
            if (allItems.contains(curItem)) {
                throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, LocalDBClientExceptionMessage.INVALID_SET_DUPLICATES.getMessage());
            }
            allItems.add(curItem);
        }
    }

    public static Map<String, AttributeValue> projectAttributes(Map<String, AttributeValue> item, List<String> attributesToGet) {
        if (item == null) {
            return item;
        }
        if (attributesToGet == null) {
            return item;
        }
        HashMap<String, AttributeValue> projectedItem = new HashMap<String, AttributeValue>();
        for (String attrName : attributesToGet) {
            if (!item.containsKey(attrName)) continue;
            projectedItem.put(attrName, item.get(attrName));
        }
        return projectedItem;
    }

    public static Map<String, AttributeValue> projectAttributes(Map<String, AttributeValue> item, ProjectionExpression projectionExpression) {
        if (item == null) {
            return item;
        }
        if (projectionExpression == null) {
            return item;
        }
        AttributeValue itemAttributeValue = new AttributeValue();
        itemAttributeValue.withM(item);
        ExpressionExecutor exprExecutor = new ExpressionExecutor(itemAttributeValue, null, new LocalDBEnv(), new LocalDocumentFactory());
        AttributeValue projectedItem = (AttributeValue)exprExecutor.projectUserDoc(projectionExpression.getTreeRoot());
        if (projectedItem != null) {
            return projectedItem.getM();
        }
        return new LinkedHashMap<String, AttributeValue>();
    }

    public static boolean doesItemMatchCondition(Map<String, AttributeValue> item, Expression conditionExpression, DbEnv dbEnv, DocumentFactory docFactory) throws ExpressionExecutionException {
        if (conditionExpression == null) {
            return true;
        }
        AttributeValue docValue = null;
        docValue = item == null ? new AttributeValue().withM(new HashMap<String, AttributeValue>()) : new AttributeValue().withM(item);
        ExpressionExecutor exprExecutor = new ExpressionExecutor(docValue, null, dbEnv, docFactory);
        AttributeValue booleanValue = (AttributeValue)exprExecutor.evaluateExpression(conditionExpression.getExprTree());
        return booleanValue.getBooleanValue();
    }

    public static List<Map<String, AttributeValue>> projectAttributesList(List<Map<String, AttributeValue>> itemsList, List<String> attributesToGet) {
        if (attributesToGet == null) {
            return itemsList;
        }
        ArrayList<Map<String, AttributeValue>> projectedItems = new ArrayList<Map<String, AttributeValue>>();
        for (Map<String, AttributeValue> item : itemsList) {
            projectedItems.add(LocalDBUtils.projectAttributes(item, attributesToGet));
        }
        return projectedItems;
    }

    public static List<Map<String, AttributeValue>> projectAttributesList(List<Map<String, AttributeValue>> itemsList, ProjectionExpression projectionExpression) {
        if (projectionExpression == null) {
            return itemsList;
        }
        ArrayList<Map<String, AttributeValue>> projectedItems = new ArrayList<Map<String, AttributeValue>>();
        for (Map<String, AttributeValue> item : itemsList) {
            projectedItems.add(LocalDBUtils.projectAttributes(item, projectionExpression));
        }
        return projectedItems;
    }

    private static long getItemSizeBytes(Map<String, AttributeValue> item, boolean isTopLevelMap) {
        long itemSizeBytes = 0L;
        for (String attrName : item.keySet()) {
            itemSizeBytes += (long)attrName.getBytes(UTF8).length;
            itemSizeBytes += LocalDBUtils.getAttributeValueSizeBytes(item.get(attrName));
        }
        if (!isTopLevelMap) {
            itemSizeBytes += (long)(item.size() * 1);
        }
        return itemSizeBytes;
    }

    public static long getItemSizeBytes(Map<String, AttributeValue> item) {
        if (item == null) {
            return 0L;
        }
        return LocalDBUtils.getItemSizeBytes(item, true);
    }

    public static long getAttributeValueSizeBytes(AttributeValue attrVal) {
        long setSizeBytes = 0L;
        DDBType type = LocalDBUtils.getDataTypeOfAttributeValue(attrVal);
        switch (type) {
            case S: {
                return attrVal.getS().getBytes(UTF8).length;
            }
            case N: {
                return LocalDBUtils.encodeBigDecimal(new BigDecimal(attrVal.getN())).length;
            }
            case B: {
                return attrVal.getB().remaining();
            }
            case SS: {
                for (String s : attrVal.getSS()) {
                    setSizeBytes += (long)s.getBytes(UTF8).length;
                }
                return setSizeBytes;
            }
            case NS: {
                for (String n : attrVal.getNS()) {
                    setSizeBytes += (long)LocalDBUtils.encodeBigDecimal(new BigDecimal(n)).length;
                }
                return setSizeBytes;
            }
            case BS: {
                for (ByteBuffer b : attrVal.getBS()) {
                    setSizeBytes += (long)b.remaining();
                }
                return setSizeBytes;
            }
            case BOOL: {
                return 1L;
            }
            case NULL: {
                return 1L;
            }
            case L: {
                for (AttributeValue value : attrVal.getL()) {
                    setSizeBytes += LocalDBUtils.getAttributeValueSizeBytes(value);
                }
                return (setSizeBytes += (long)(attrVal.getL().size() * 1)) + 3L;
            }
            case M: {
                return LocalDBUtils.getItemSizeBytes(attrVal.getM(), false) + 3L;
            }
        }
        LocalDBUtils.ldClientFail(LocalDBClientExceptionType.UNREACHABLE_CODE);
        return 0L;
    }

    public static void ldAccessAssertTrue(boolean condition, LocalDBAccessExceptionType type, String failureMessage, Object ... args2) {
        if (!condition) {
            LocalDBUtils.ldAccessFail(type, failureMessage, args2);
        }
    }

    public static void ldAccessFail(LocalDBAccessExceptionType type, String message, Object ... args2) {
        throw new LocalDBAccessException(type, String.format(message, args2));
    }

    public static void ldClientAssertTrue(boolean condition, LocalDBClientExceptionType type, String failureMessage, Object ... args2) {
        if (!condition) {
            LocalDBUtils.ldClientFail(type, failureMessage, args2);
        }
    }

    private static void ldClientFail(LocalDBClientExceptionType type, String message, Object ... args2) {
        throw new LocalDBClientException(type, String.format(message, args2));
    }

    public static void ldClientFail(LocalDBClientExceptionType type) {
        throw new LocalDBClientException(type, type.getMessage());
    }

    public static DDBType validateConsistentTypes(AttributeDefinition attrDef, AttributeValue expectedVal, LocalDBClientExceptionMessage exceptionMessageToThrow) {
        DDBType expectedType = LocalDBUtils.getDataTypeOfAttributeValue(expectedVal);
        DDBType requestType = LocalDBUtils.getDataTypesOfAttributeDefinitions(Collections.singletonList(attrDef), true).get(0);
        if (requestType != expectedType) {
            throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, exceptionMessageToThrow.getMessage());
        }
        return requestType;
    }

    public static DDBType validateConsistentTypes(AttributeValue attrVal, AttributeValue expectedVal) {
        DDBType expectedType;
        DDBType requestType = LocalDBUtils.getDataTypeOfAttributeValue(expectedVal);
        if (requestType != (expectedType = LocalDBUtils.getDataTypeOfAttributeValue(attrVal))) {
            throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, LocalDBClientExceptionMessage.INCONSISTENT_TYPES.getMessage());
        }
        return requestType;
    }

    public static byte[] getHashValue(AttributeValue attrValue) {
        if (attrValue.getB() != null) {
            return LocalDBUtils.generateSHA1(LocalDBUtils.readAllBytesFromByteBuffer(attrValue.getB()));
        }
        if (attrValue.getN() != null) {
            return LocalDBUtils.generateSHA1(LocalDBUtils.encodeBigDecimal(new BigDecimal(attrValue.getN())));
        }
        if (attrValue.getS() != null) {
            return LocalDBUtils.generateSHA1(attrValue.getS().getBytes(UTF8));
        }
        return null;
    }

    public static byte[] getHashValue(AttributeValue ... attributeValues) {
        ArrayList<byte[]> attributehashValues = new ArrayList<byte[]>();
        for (AttributeValue attrValue : attributeValues) {
            attributehashValues.add(LocalDBUtils.getHashValue(attrValue));
        }
        return LocalDBUtils.generateSHA1(attributehashValues);
    }

    public static GlobalSecondaryIndex getGSIfromGSIDescription(GlobalSecondaryIndexDescription gsiDesc) {
        return new GlobalSecondaryIndex().withIndexName(gsiDesc.getIndexName()).withKeySchema((Collection)gsiDesc.getKeySchema()).withProjection(gsiDesc.getProjection()).withProvisionedThroughput(LocalDBUtils.getPTfromPTDescription(gsiDesc.getProvisionedThroughput()));
    }

    public static ProvisionedThroughput getPTfromPTDescription(ProvisionedThroughputDescription ptd) {
        if (ptd == null) {
            return null;
        }
        return new ProvisionedThroughput().withReadCapacityUnits(ptd.getReadCapacityUnits()).withWriteCapacityUnits(ptd.getWriteCapacityUnits());
    }

    private static byte[] generateSHA1(byte[] ba) {
        MessageDigest md = m_digest.get();
        md.update(SALT);
        return md.digest(ba);
    }

    private static byte[] generateSHA1(List<byte[]> keys2) {
        MessageDigest md = m_digest.get();
        md.update(SALT);
        for (byte[] ba : keys2) {
            md.update(ba);
        }
        return md.digest();
    }

    public static byte[] encodeBigDecimal(BigDecimal value) {
        int signum = value.signum();
        if (signum == 0) {
            return new byte[]{-128};
        }
        value = value.abs().stripTrailingZeros();
        int scale = value.scale();
        int exponent = value.precision() - scale;
        int appendZero = exponent % 2 == 1 || exponent % 2 == -1 ? 1 : 0;
        exponent += appendZero;
        String mantissaStr = value.scaleByPowerOfTen(scale).toBigInteger().toString(10);
        int mantissaStrLength = mantissaStr.length() + appendZero;
        int byteArrayLengthWithoutExponent = (mantissaStrLength + 1) / 2;
        byte[] byteArray = null;
        if (byteArrayLengthWithoutExponent < 20 && signum == -1) {
            byteArray = new byte[byteArrayLengthWithoutExponent + 2];
            byteArray[byteArrayLengthWithoutExponent + 1] = 102;
        } else {
            byteArray = new byte[byteArrayLengthWithoutExponent + 1];
        }
        byteArray[0] = (byte)(192 + exponent / 2);
        if (signum == -1) {
            byteArray[0] = ~byteArray[0];
        }
        boolean appendedZero = false;
        for (int mantissaIndex = 0; mantissaIndex < mantissaStr.length(); ++mantissaIndex) {
            int byteArrayIndex = (mantissaIndex + appendZero) / 2 + 1;
            if (appendZero == 1 && mantissaIndex == 0 && !appendedZero) {
                byteArray[byteArrayIndex] = 0;
                appendedZero = true;
                --mantissaIndex;
            } else if ((mantissaIndex + appendZero) % 2 == 0) {
                byteArray[byteArrayIndex] = (byte)((byte)(mantissaStr.charAt(mantissaIndex) - 48) * 10);
            } else {
                int n = byteArrayIndex;
                byteArray[n] = (byte)(byteArray[n] + (mantissaStr.charAt(mantissaIndex) - 48));
            }
            if ((mantissaIndex + appendZero) % 2 != 1 && mantissaIndex != mantissaStr.length() - 1) continue;
            if (signum == -1) {
                byteArray[byteArrayIndex] = (byte)(101 - byteArray[byteArrayIndex]);
                continue;
            }
            int n = byteArrayIndex;
            byteArray[n] = (byte)(byteArray[n] + 1);
        }
        return byteArray;
    }

    public static BigDecimal decodeBigDecimal(byte[] byteArray) {
        boolean isNegative;
        assert (byteArray.length <= 21);
        if (byteArray.length == 1 && byteArray[0] == -128) {
            return BigDecimal.ZERO;
        }
        assert (byteArray.length >= 2);
        byte exponentByte = byteArray[0];
        boolean bl = isNegative = (exponentByte & 0x80) >> 7 == 0;
        if (isNegative) {
            exponentByte = ~exponentByte;
        }
        assert (exponentByte >= -128);
        int exponent = (exponentByte - 64 - -128) * 2;
        StringBuilder mantissaBuilder = new StringBuilder();
        mantissaBuilder.append("0.");
        for (int i = 1; i < byteArray.length; ++i) {
            boolean isLastCountableByte;
            byte part = byteArray[i];
            boolean bl2 = isLastCountableByte = i == byteArray.length - 1 || isNegative && i == byteArray.length - 2 && byteArray[i + 1] == 102;
            if (isNegative && part == 102) {
                assert (i == byteArray.length - 1);
                break;
            }
            if (isNegative) {
                assert (part <= 101 && part >= 2);
                part = (byte)(101 - part);
            } else {
                assert (part <= 100 && part >= 1);
                part = (byte)(part - 1);
            }
            if (part < 10) {
                mantissaBuilder.append(0);
            }
            if (part % 10 == 0 && isLastCountableByte) {
                part = (byte)(part / 10);
            }
            mantissaBuilder.append(part);
        }
        BigDecimal result = new BigDecimal(mantissaBuilder.toString()).scaleByPowerOfTen(exponent);
        if (isNegative) {
            result = result.negate();
        }
        return result;
    }

    public static int compareUnsignedByteArrays(byte[] ba1, byte[] ba2) {
        int minByteArrSize = Math.min(ba1.length, ba2.length);
        for (int j = 0; j < minByteArrSize; ++j) {
            int cmp = LocalDBUtils.compareUnsignedBytes(ba1[j], ba2[j]);
            if (cmp == 0) continue;
            return cmp;
        }
        if (ba1.length > ba2.length) {
            return 1;
        }
        if (ba1.length < ba2.length) {
            return -1;
        }
        return 0;
    }

    public static void setDocumentLevel(int level, AttributeValue attributeValue) {
        block2: {
            block3: {
                if (!DDBType.DocumentTypes.contains((Object)attributeValue.getType())) break block2;
                LocalDBValidatorUtils.validateNestedLevel(level);
                attributeValue.setLevel(level);
                if (attributeValue.getType() != DDBType.L) break block3;
                for (AttributeValue listValue : attributeValue.getL()) {
                    LocalDBUtils.setDocumentLevel(level + 1, listValue);
                }
                break block2;
            }
            if (attributeValue.getType() != DDBType.M) break block2;
            for (Map.Entry<String, AttributeValue> mapEntry : attributeValue.getM().entrySet()) {
                LocalDBUtils.setDocumentLevel(level + 1, mapEntry.getValue());
            }
        }
    }

    private static int compareUnsignedBytes(byte b1, byte b2) {
        if ((char)b1 > (char)b2) {
            return 1;
        }
        if ((char)b1 < (char)b2) {
            return -1;
        }
        return 0;
    }

    public static AttributeDefinition getBaseTableHashKeyDefinition(TableInfo info) {
        return LocalDBUtils.getHashKeyDefn(info.getKeySchema(), info.getAttributeDefinitions());
    }

    public static AttributeDefinition getLSIHashKeyDefinition(LocalSecondaryIndex lsi, TableInfo info) {
        return LocalDBUtils.getHashKeyDefn(lsi.getKeySchema(), info.getAttributeDefinitions());
    }

    public static AttributeDefinition getGSIHashKeyDefinition(GlobalSecondaryIndexDescription gsi, TableInfo info) {
        return LocalDBUtils.getHashKeyDefn(gsi.getKeySchema(), info.getAttributeDefinitions());
    }

    public static AttributeDefinition getBaseTableRangeKeyDefinition(TableInfo info) {
        return LocalDBUtils.getRangeKeyDefn(info.getKeySchema(), info.getAttributeDefinitions());
    }

    public static AttributeDefinition getLSIRangeKeyDefinition(LocalSecondaryIndex lsi, TableInfo info) {
        return LocalDBUtils.getRangeKeyDefn(lsi.getKeySchema(), info.getAttributeDefinitions());
    }

    public static AttributeDefinition getGSIRangeKeyDefinition(GlobalSecondaryIndexDescription gsi, TableInfo info) {
        return LocalDBUtils.getRangeKeyDefn(gsi.getKeySchema(), info.getAttributeDefinitions());
    }

    private static AttributeDefinition getHashKeyDefn(List<KeySchemaElement> keySchema, List<AttributeDefinition> attributeDefinitions) {
        return LocalDBUtils.findAttributeDefinition(keySchema.get(0), attributeDefinitions);
    }

    private static AttributeDefinition getRangeKeyDefn(List<KeySchemaElement> keySchema, List<AttributeDefinition> allAttributes) {
        int numKeys = keySchema.size();
        boolean hashAndRangeKey = numKeys == 2;
        return hashAndRangeKey ? LocalDBUtils.findAttributeDefinition(keySchema.get(1), allAttributes) : null;
    }

    public static void setLog4jToUtilsLogging(String name) {
    }

    public static String getUUID() {
        return UUID.randomUUID().toString();
    }

    public static List<GlobalSecondaryIndexDescription> getGsiDescListFrom(List<GlobalSecondaryIndex> gsiList) {
        ArrayList<GlobalSecondaryIndexDescription> list = new ArrayList<GlobalSecondaryIndexDescription>();
        if (gsiList != null) {
            for (GlobalSecondaryIndex gsi : gsiList) {
                list.add(LocalDBUtils.getGSIDescFrom(gsi));
            }
        }
        return list;
    }

    private static GlobalSecondaryIndexDescription getGSIDescFrom(GlobalSecondaryIndex gsi) {
        GlobalSecondaryIndexDescription description = new GlobalSecondaryIndexDescription().withIndexName(gsi.getIndexName()).withIndexStatus(IndexStatus.ACTIVE).withKeySchema((Collection)gsi.getKeySchema()).withProjection(gsi.getProjection()).withIndexSizeBytes(Long.valueOf(0L)).withItemCount(Long.valueOf(0L));
        if (gsi.getProvisionedThroughput() != null) {
            description.setProvisionedThroughput(new ProvisionedThroughputDescription().withReadCapacityUnits(gsi.getProvisionedThroughput().getReadCapacityUnits()).withWriteCapacityUnits(gsi.getProvisionedThroughput().getWriteCapacityUnits()));
        }
        return description;
    }

    public static List<GlobalSecondaryIndex> getGsiListFrom(List<GlobalSecondaryIndexDescription> gsiDescList) {
        ArrayList<GlobalSecondaryIndex> list = new ArrayList<GlobalSecondaryIndex>();
        if (gsiDescList != null) {
            for (GlobalSecondaryIndexDescription desc : gsiDescList) {
                list.add(LocalDBUtils.getGSIfromGSIDescription(desc));
            }
        }
        return list;
    }

    public static boolean isEqual(List<KeySchemaElement> oneKeySchema, List<SQLiteIndexElement> anotherKeySchema) {
        boolean isComparingHashAndRangeGSIs;
        if (oneKeySchema.size() != anotherKeySchema.size()) {
            return false;
        }
        String oneHashKeyName = oneKeySchema.get(0).getAttributeName();
        String anotherHashKeyName = anotherKeySchema.get(0).getDynamoDBAttribute().getAttributeName();
        boolean bl = isComparingHashAndRangeGSIs = oneKeySchema.size() == 2;
        if (isComparingHashAndRangeGSIs) {
            String oneRangeKeyName = oneKeySchema.get(1).getAttributeName();
            String anotherRangeKeyName = anotherKeySchema.get(1).getDynamoDBAttribute().getAttributeName();
            return oneHashKeyName.equals(anotherHashKeyName) && oneRangeKeyName.equals(anotherRangeKeyName);
        }
        return oneHashKeyName.equals(anotherHashKeyName);
    }

    public static boolean isKeySchemasEqual(List<KeySchemaElement> oneKeySchema, List<KeySchemaElement> anotherKeySchema) {
        boolean areWeComparingHashAndRangeGSIs;
        if (oneKeySchema.size() != anotherKeySchema.size()) {
            return false;
        }
        String oneHashKeyName = oneKeySchema.get(0).getAttributeName();
        String anotherHashKeyName = anotherKeySchema.get(0).getAttributeName();
        boolean bl = areWeComparingHashAndRangeGSIs = oneKeySchema.size() == 2;
        if (areWeComparingHashAndRangeGSIs) {
            String oneRangeKeyName = oneKeySchema.get(1).getAttributeName();
            String anotherRangeKeyName = anotherKeySchema.get(1).getAttributeName();
            return oneHashKeyName.equals(anotherHashKeyName) && oneRangeKeyName.equals(anotherRangeKeyName);
        }
        return oneHashKeyName.equals(anotherHashKeyName);
    }

    public static boolean isSQLiteIndexElementsEqual(List<SQLiteIndexElement> oneElement, List<SQLiteIndexElement> anotherElement) {
        boolean areWeComparingHashAndRangeGSIs;
        if (oneElement.size() != anotherElement.size()) {
            return false;
        }
        String oneHashKeyName = oneElement.get(0).getDynamoDBAttribute().getAttributeName();
        String anotherHashKeyName = anotherElement.get(0).getDynamoDBAttribute().getAttributeName();
        boolean bl = areWeComparingHashAndRangeGSIs = oneElement.size() == 2;
        if (areWeComparingHashAndRangeGSIs) {
            String oneRangeKeyName = oneElement.get(1).getDynamoDBAttribute().getAttributeName();
            String anotherRangeKeyName = anotherElement.get(1).getDynamoDBAttribute().getAttributeName();
            return oneHashKeyName.equals(anotherHashKeyName) && oneRangeKeyName.equals(anotherRangeKeyName);
        }
        return oneHashKeyName.equals(anotherHashKeyName);
    }

    public static String getTimeToLiveThreadName(String tableName, String timeToLiveAttributeName) {
        return String.format("%s-%s", tableName, timeToLiveAttributeName);
    }

    public static String getGsiThreadName(String tableName, String indexName, IndexStatus status) {
        return String.format("%s-%s-%s", tableName, indexName, status.toString());
    }

    public static List<GlobalSecondaryIndexDescription> getGSIsByIndexStatus(List<GlobalSecondaryIndexDescription> gsiDescList, IndexStatus status) {
        return LocalDBUtils.getGSIsByIndexStatus(gsiDescList, status, null);
    }

    public static List<GlobalSecondaryIndexDescription> getGSIsByIndexStatus(List<GlobalSecondaryIndexDescription> gsiDescList, IndexStatus status, Boolean backfilling) {
        if (IndexStatus.ACTIVE.equals((Object)status) || IndexStatus.DELETING.equals((Object)status)) {
            LocalDBUtils.ldAccessAssertTrue(backfilling == null, LocalDBAccessExceptionType.UNEXPECTED_EXCEPTION, "Invalid GSI status found", new Object[0]);
        }
        if (IndexStatus.CREATING.equals((Object)status)) {
            LocalDBUtils.ldAccessAssertTrue(backfilling != null, LocalDBAccessExceptionType.UNEXPECTED_EXCEPTION, "Invalid GSI status found", new Object[0]);
        }
        if (gsiDescList == null || gsiDescList.size() == 0) {
            return new ArrayList<GlobalSecondaryIndexDescription>();
        }
        ArrayList<GlobalSecondaryIndexDescription> list = new ArrayList<GlobalSecondaryIndexDescription>();
        for (GlobalSecondaryIndexDescription desc : gsiDescList) {
            if (!status.toString().equals(desc.getIndexStatus()) || !LocalDBUtils.compareBackfilling(backfilling, desc.getBackfilling())) continue;
            list.add(desc);
        }
        return list;
    }

    private static boolean compareBackfilling(Boolean one, Boolean another) {
        if (one == null && another == null) {
            return true;
        }
        if (one != null) {
            return one.equals(another);
        }
        return another.equals(one);
    }

    public static String generateShardId() {
        String uuid = LocalDBUtils.getUUID();
        return "shardId-" + String.format("%020d", System.currentTimeMillis()) + "-" + uuid.substring(0, Math.min(8, uuid.length()));
    }

    public static String generateStreamARN(String tableName, String streamLabel) {
        return LocalDBUtils.generateArn(tableName, null, streamLabel);
    }

    public static String longToSequenceNumber(Long l) {
        if (l != null) {
            return String.format(longToSequenceNumberFormatString, l);
        }
        return null;
    }

    public static String millisToISO8601(long timeInMillis) {
        return df.format(new Date(timeInMillis));
    }

    public static String extractStreamLabelFromArn(String streamArn) {
        if (streamArn == null) {
            return null;
        }
        String[] parts = streamArn.split("/");
        return parts[parts.length - 1];
    }

    public static String generateArn(String tableName, String indexName, String streamLabel) {
        StringBuilder arnBuilder = new StringBuilder();
        arnBuilder.append("arn:aws:dynamodb").append(':').append("ddblocal").append(':').append("000000000000").append(':').append(TABLE_RESOURCE_PATH).append(tableName);
        if (indexName != null) {
            arnBuilder.append(INDEX_RESOURCE_PATH).append(indexName);
        }
        if (streamLabel != null) {
            arnBuilder.append(STREAM_RESOURCE_PATH).append(streamLabel);
        }
        return arnBuilder.toString();
    }

    public static void logLongMessage(Logger logger, Level level, String prefix, String message) {
        if (logger == null || level == null || prefix == null || message == null) {
            return;
        }
        if (message.length() > 4096) {
            logger.log(level, prefix + " : <<message greater than 4KB >>");
        } else {
            logger.log(level, prefix + " : " + message);
        }
    }

    public static byte[] readAllBytesFromByteBuffer(ByteBuffer buffer) {
        byte[] bytes;
        if (buffer.hasArray()) {
            bytes = buffer.array();
        } else {
            ByteBuffer clone = buffer.duplicate();
            bytes = new byte[clone.remaining()];
            clone.get(bytes);
        }
        return bytes;
    }

    static {
        Arrays.fill(MAX_SHA1_BYTES, (byte)-1);
        MAX_HASH_KEY = new BigInteger(1, MAX_SHA1_BYTES);
        ITEM_EXPIRY_DELAY_BETWEEN_DELETION = TimeUnit.SECONDS.toMillis(10L);
        SALT = new byte[]{76, 111, 99, 97, 108, 68, 100, 98};
        m_digest = new ThreadLocal<MessageDigest>(){

            @Override
            protected MessageDigest initialValue() {
                try {
                    return MessageDigest.getInstance("SHA1");
                } catch (NoSuchAlgorithmException ex) {
                    return null;
                }
            }
        };
        tz = TimeZone.getTimeZone("UTC");
        df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
        errorFactory = new AWSExceptionFactory();
        df.setTimeZone(tz);
    }
}

