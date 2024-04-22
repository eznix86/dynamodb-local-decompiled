/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  com.almworks.sqlite4java.SQLiteException
 *  com.almworks.sqlite4java.SQLiteStatement
 *  com.amazonaws.services.dynamodbv2.model.AttributeValue
 *  com.amazonaws.services.dynamodbv2.model.GlobalSecondaryIndex
 *  com.amazonaws.services.dynamodbv2.model.KeySchemaElement
 *  com.amazonaws.services.dynamodbv2.model.KeyType
 *  com.amazonaws.services.dynamodbv2.model.LocalSecondaryIndex
 *  com.amazonaws.services.dynamodbv2.model.StreamRecord
 *  com.amazonaws.services.dynamodbv2.model.StreamViewType
 */
package com.amazonaws.services.dynamodbv2.local.shared.access.sqlite;

import com.almworks.sqlite4java.SQLiteException;
import com.almworks.sqlite4java.SQLiteStatement;
import com.amazonaws.services.dynamodbv2.local.shared.access.LocalDBUtils;
import com.amazonaws.services.dynamodbv2.local.shared.access.PaddingNumberEncoder;
import com.amazonaws.services.dynamodbv2.local.shared.access.sqlite.SQLiteDBAccess;
import com.amazonaws.services.dynamodbv2.local.shared.access.sqlite.SQLiteIndexElement;
import com.amazonaws.services.dynamodbv2.local.shared.access.sqlite.TableSchemaInfo;
import com.amazonaws.services.dynamodbv2.local.shared.exceptions.LocalDBAccessExceptionType;
import com.amazonaws.services.dynamodbv2.local.shared.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.GlobalSecondaryIndex;
import com.amazonaws.services.dynamodbv2.model.KeySchemaElement;
import com.amazonaws.services.dynamodbv2.model.KeyType;
import com.amazonaws.services.dynamodbv2.model.LocalSecondaryIndex;
import com.amazonaws.services.dynamodbv2.model.StreamRecord;
import com.amazonaws.services.dynamodbv2.model.StreamViewType;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SQLiteDBAccessUtils {
    static StreamRecord constructInternalStreamRecord(Map<String, AttributeValue> key, Map<String, AttributeValue> oldRecord, Map<String, AttributeValue> newRecord, StreamViewType streamViewType) {
        StreamRecord ret = new StreamRecord().withKeys(SQLiteDBAccessUtils.convertLocalAttributeValues(key)).withStreamViewType(streamViewType);
        switch (streamViewType) {
            case KEYS_ONLY: {
                ret.withSizeBytes(Long.valueOf(LocalDBUtils.getItemSizeBytes(key)));
                break;
            }
            case NEW_AND_OLD_IMAGES: {
                ret.withNewImage(SQLiteDBAccessUtils.convertLocalAttributeValues(newRecord)).withOldImage(SQLiteDBAccessUtils.convertLocalAttributeValues(oldRecord)).withSizeBytes(Long.valueOf(LocalDBUtils.getItemSizeBytes(key) + LocalDBUtils.getItemSizeBytes(newRecord) + LocalDBUtils.getItemSizeBytes(oldRecord)));
                break;
            }
            case NEW_IMAGE: {
                ret.withNewImage(SQLiteDBAccessUtils.convertLocalAttributeValues(newRecord)).withSizeBytes(Long.valueOf(LocalDBUtils.getItemSizeBytes(key) + LocalDBUtils.getItemSizeBytes(newRecord)));
                break;
            }
            case OLD_IMAGE: {
                ret.withOldImage(SQLiteDBAccessUtils.convertLocalAttributeValues(oldRecord)).withSizeBytes(Long.valueOf(LocalDBUtils.getItemSizeBytes(key) + LocalDBUtils.getItemSizeBytes(oldRecord)));
                break;
            }
        }
        return ret;
    }

    private static Map<String, com.amazonaws.services.dynamodbv2.model.AttributeValue> convertLocalAttributeValues(Map<String, AttributeValue> valueMap) {
        if (valueMap == null) {
            return null;
        }
        HashMap<String, com.amazonaws.services.dynamodbv2.model.AttributeValue> ret = new HashMap<String, com.amazonaws.services.dynamodbv2.model.AttributeValue>();
        for (Map.Entry<String, AttributeValue> entry : valueMap.entrySet()) {
            AttributeValue attributeValue = entry.getValue();
            ret.put(entry.getKey(), SQLiteDBAccessUtils.convertLocalAttributeValue(attributeValue));
        }
        return ret;
    }

    public static com.amazonaws.services.dynamodbv2.model.AttributeValue convertLocalAttributeValue(AttributeValue attributeValue) {
        assert (attributeValue instanceof AttributeValue);
        com.amazonaws.services.dynamodbv2.model.AttributeValue converted = new com.amazonaws.services.dynamodbv2.model.AttributeValue().withB(attributeValue.getB()).withBS(attributeValue.getBS()).withN(attributeValue.getN()).withNS(attributeValue.getNS()).withS(attributeValue.getS()).withSS(attributeValue.getSS()).withBOOL(attributeValue.getBOOL()).withNULL(attributeValue.isNULL()).withM(SQLiteDBAccessUtils.convertLocalAttributeValues(attributeValue.getM()));
        if (attributeValue.getL() != null) {
            ArrayList<com.amazonaws.services.dynamodbv2.model.AttributeValue> list = new ArrayList<com.amazonaws.services.dynamodbv2.model.AttributeValue>();
            for (AttributeValue val : attributeValue.getL()) {
                list.add(SQLiteDBAccessUtils.convertLocalAttributeValue(val));
            }
            converted.setL(list);
        }
        return converted;
    }

    static Map<String, AttributeValue> constructKey(TableSchemaInfo tableSchemaInfo, AttributeValue hashKey, AttributeValue rangeKey) {
        HashMap<String, AttributeValue> key = new HashMap<String, AttributeValue>();
        key.put(tableSchemaInfo.getHashKeyDefinition().getAttributeName(), hashKey);
        if (rangeKey != null) {
            key.put(tableSchemaInfo.getRangeKeyDefinition().getAttributeName(), rangeKey);
        }
        return key;
    }

    static String constructIndexWhereClause(List<SQLiteIndexElement> indexes) {
        StringBuilder ret = new StringBuilder();
        for (int i = 0; i < indexes.size(); ++i) {
            ret.append(indexes.get(i).getSqliteColumnName()).append(" = ?");
            if (i >= indexes.size() - 1) continue;
            ret.append(" AND ");
        }
        return ret.toString();
    }

    static void applyKeyBinds(SQLiteStatement statement, List<SQLiteIndexElement> indexElements, Map<String, AttributeValue> key) throws SQLiteException {
        for (int i = 0; i < indexElements.size(); ++i) {
            String attributeName = indexElements.get(i).getDynamoDBAttribute().getAttributeName();
            byte[] value = SQLiteDBAccessUtils.translateKeyAttributeValue(key.get(attributeName));
            statement.bind(i + 1, value);
        }
    }

    static String escapedTableName(String s) {
        return "\"" + s + "\"";
    }

    static byte[] translateKeyAttributeValue(AttributeValue attributeValue) {
        if (attributeValue.getB() != null) {
            return attributeValue.getB().array();
        }
        if (attributeValue.getN() != null) {
            return PaddingNumberEncoder.encodeBigDecimal(new BigDecimal(attributeValue.getN()));
        }
        if (attributeValue.getS() != null) {
            return attributeValue.getS().getBytes(LocalDBUtils.UTF8);
        }
        throw new IllegalArgumentException("Unknown AttributeValue type: " + attributeValue);
    }

    static String getLSIIndexKeyDynamoDBName(TableSchemaInfo tableSchema, String indexName) {
        List<LocalSecondaryIndex> lsiList = tableSchema.getLsiList();
        for (LocalSecondaryIndex lsi : lsiList) {
            if (!lsi.getIndexName().equals(indexName)) continue;
            List keySchema = lsi.getKeySchema();
            for (KeySchemaElement kse : keySchema) {
                if (!kse.getKeyType().equals(KeyType.RANGE.toString())) continue;
                return kse.getAttributeName();
            }
        }
        return null;
    }

    static String getGSIKeyDynamoDBName(TableSchemaInfo tableSchema, String indexName, String keyType) {
        List<GlobalSecondaryIndex> gsiList = tableSchema.getGsiList();
        for (GlobalSecondaryIndex gsi : gsiList) {
            if (!gsi.getIndexName().equals(indexName)) continue;
            List keySchema = gsi.getKeySchema();
            for (KeySchemaElement kse : keySchema) {
                if (!kse.getKeyType().equals(keyType)) continue;
                return kse.getAttributeName();
            }
        }
        return null;
    }

    public static int applyBinds(SQLiteStatement statement, int startBind, List<byte[]> bindData) throws SQLiteException {
        ArrayList<SQLiteDBAccess.BindValue> bindValues = new ArrayList<SQLiteDBAccess.BindValue>();
        for (byte[] data : bindData) {
            bindValues.add(new SQLiteDBAccess.BindValue().withBytes(data));
        }
        return SQLiteDBAccessUtils.applyBindsWithBindValues(statement, startBind, bindValues);
    }

    public static int applyBindsWithBindValues(SQLiteStatement statement, int startBind, List<SQLiteDBAccess.BindValue> bindValues) throws SQLiteException {
        int endBind = startBind;
        LocalDBUtils.ldAccessAssertTrue(startBind > 0, LocalDBAccessExceptionType.UNEXPECTED_EXCEPTION, "SQL construction issue, binding at location 0.", new Object[0]);
        LocalDBUtils.ldAccessAssertTrue(endBind + bindValues.size() - 1 <= statement.getBindParameterCount(), LocalDBAccessExceptionType.UNEXPECTED_EXCEPTION, "SQL construction issue, invalid number of binds.", new Object[0]);
        int j = 0;
        while (j < bindValues.size()) {
            if (bindValues.get(j).getText() != null) {
                statement.bind(endBind, bindValues.get(j).getText());
            } else {
                statement.bind(endBind, bindValues.get(j).getBytes());
            }
            ++j;
            ++endBind;
        }
        return endBind;
    }
}

