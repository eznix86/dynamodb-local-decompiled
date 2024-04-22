/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  com.amazonaws.services.dynamodbv2.model.AttributeDefinition
 *  com.amazonaws.services.dynamodbv2.model.GlobalSecondaryIndex
 *  com.amazonaws.services.dynamodbv2.model.GlobalSecondaryIndexDescription
 *  com.amazonaws.services.dynamodbv2.model.IndexStatus
 *  com.amazonaws.services.dynamodbv2.model.KeySchemaElement
 *  com.amazonaws.services.dynamodbv2.model.KeyType
 *  com.amazonaws.services.dynamodbv2.model.LocalSecondaryIndex
 */
package com.amazonaws.services.dynamodbv2.local.shared.access.sqlite;

import com.amazonaws.services.dynamodbv2.local.google.Preconditions;
import com.amazonaws.services.dynamodbv2.local.shared.access.LocalDBUtils;
import com.amazonaws.services.dynamodbv2.local.shared.access.sqlite.SQLiteIndexElement;
import com.amazonaws.services.dynamodbv2.model.AttributeDefinition;
import com.amazonaws.services.dynamodbv2.model.GlobalSecondaryIndex;
import com.amazonaws.services.dynamodbv2.model.GlobalSecondaryIndexDescription;
import com.amazonaws.services.dynamodbv2.model.IndexStatus;
import com.amazonaws.services.dynamodbv2.model.KeySchemaElement;
import com.amazonaws.services.dynamodbv2.model.KeyType;
import com.amazonaws.services.dynamodbv2.model.LocalSecondaryIndex;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@JsonInclude(value=JsonInclude.Include.NON_NULL)
public class TableSchemaInfo {
    private List<AttributeDefinition> attributes = null;
    private List<LocalSecondaryIndex> lsiList = null;
    @Deprecated
    private List<GlobalSecondaryIndex> gsiList = null;
    private List<GlobalSecondaryIndexDescription> gsiDescList = null;
    private Map<String, List<SQLiteIndexElement>> sqliteIndex = null;
    private List<SQLiteIndexElement> uniqueIndexes = null;
    private List<List<SQLiteIndexElement>> uniqueGSIIndexes = null;
    private String timeToLiveAttributeName = null;

    public TableSchemaInfo() {
    }

    @JsonIgnore
    public TableSchemaInfo(AttributeDefinition hashKeyDef, AttributeDefinition rangeKeyDef, List<AttributeDefinition> attributes, List<LocalSecondaryIndex> lsiList, List<GlobalSecondaryIndexDescription> gsiDescList) {
        Preconditions.checkNotNull(attributes, "attribute definition list may not be null");
        this.attributes = attributes;
        this.lsiList = lsiList;
        this.gsiDescList = gsiDescList;
        this.gsiList = LocalDBUtils.getGsiListFrom(gsiDescList);
        this.uniqueIndexes = new ArrayList<SQLiteIndexElement>();
        this.sqliteIndex = new HashMap<String, List<SQLiteIndexElement>>();
        ArrayList<SQLiteIndexElement> primaryIndexes = new ArrayList<SQLiteIndexElement>();
        SQLiteIndexElement hashKeyIndex = new SQLiteIndexElement(KeyType.HASH, hashKeyDef, "hashKey");
        primaryIndexes.add(hashKeyIndex);
        this.uniqueIndexes.add(hashKeyIndex);
        if (rangeKeyDef != null) {
            SQLiteIndexElement rangeKeyIndex = new SQLiteIndexElement(KeyType.RANGE, rangeKeyDef, "rangeKey");
            primaryIndexes.add(rangeKeyIndex);
            this.uniqueIndexes.add(rangeKeyIndex);
        }
        this.sqliteIndex.put("", primaryIndexes);
        this.initializeIndexMappings();
    }

    @JsonProperty(value="Attributes")
    public List<AttributeDefinition> getAttributes() {
        return this.attributes;
    }

    @JsonProperty(value="Attributes")
    public void setAttributes(List<AttributeDefinition> attributes) {
        this.attributes = attributes;
    }

    @JsonProperty(value="LSIList")
    public List<LocalSecondaryIndex> getLsiList() {
        return this.lsiList;
    }

    @JsonProperty(value="LSIList")
    public void setLsiList(List<LocalSecondaryIndex> lsiList) {
        this.lsiList = lsiList;
    }

    @JsonProperty(value="GSIList")
    @Deprecated
    public List<GlobalSecondaryIndex> getGsiList() {
        return this.gsiList;
    }

    @JsonProperty(value="GSIList")
    @Deprecated
    public void setGsiList(List<GlobalSecondaryIndex> gsiList) {
        this.setGsiDescList(LocalDBUtils.getGsiDescListFrom(gsiList));
    }

    @JsonProperty(value="GSIDescList")
    public List<GlobalSecondaryIndexDescription> getGsiDescList() {
        return this.gsiDescList;
    }

    @JsonProperty(value="GSIDescList")
    public void setGsiDescList(List<GlobalSecondaryIndexDescription> updatedGSIDescList) {
        this.gsiDescList = updatedGSIDescList;
        this.gsiList = LocalDBUtils.getGsiListFrom(updatedGSIDescList);
    }

    @JsonProperty(value="SQLiteIndex")
    public Map<String, List<SQLiteIndexElement>> getSqliteIndex() {
        return this.sqliteIndex;
    }

    @JsonProperty(value="SQLiteIndex")
    public void setSqliteIndex(Map<String, List<SQLiteIndexElement>> sqliteIndex) {
        this.sqliteIndex = sqliteIndex;
    }

    @JsonProperty(value="UniqueIndexes")
    public List<SQLiteIndexElement> getUniqueIndexes() {
        return this.uniqueIndexes;
    }

    @JsonProperty(value="UniqueIndexes")
    public void setUniqueIndexes(List<SQLiteIndexElement> uniqueIndexes) {
        this.uniqueIndexes = uniqueIndexes;
    }

    @JsonProperty(value="UniqueGSIIndexes")
    public List<List<SQLiteIndexElement>> getUniqueGSIIndexes() {
        return this.uniqueGSIIndexes;
    }

    @JsonProperty(value="UniqueGSIIndexes")
    public void setUniqueGSIIndexes(List<List<SQLiteIndexElement>> uniqueGSIIndexes) {
        this.uniqueGSIIndexes = uniqueGSIIndexes;
    }

    @JsonProperty(value="TimeToLiveAttributeName")
    public String getTimeToLiveAttributeName() {
        return this.timeToLiveAttributeName;
    }

    @JsonProperty(value="TimeToLiveAttributeName")
    public void setTimeToLiveAttributeName(String timeToLiveAttributeName) {
        this.timeToLiveAttributeName = timeToLiveAttributeName;
    }

    private void initializeIndexMappings() {
        int nextColumnIndex = 0;
        Map<String, AttributeDefinition> attributeNameToDefinitions = this.attributeNameToDefinitionsMap();
        SQLiteIndexElement baseTableHashKeyIndex = this.getHashKeyIndex();
        AttributeDefinition baseTableRangeKeyDef = this.getRangeKeyDefinition();
        HashMap<String, Object> nonGSIdDBAttrNameToSQLiteColNameMap = new HashMap<String, Object>();
        nonGSIdDBAttrNameToSQLiteColNameMap.put(baseTableHashKeyIndex.getDynamoDBAttribute().getAttributeName(), "hashKey");
        if (baseTableRangeKeyDef != null) {
            nonGSIdDBAttrNameToSQLiteColNameMap.put(baseTableRangeKeyDef.getAttributeName(), "rangeKey");
        }
        if (this.lsiList != null) {
            for (LocalSecondaryIndex lsi : this.lsiList) {
                AttributeDefinition lsiRangeKeyDef = attributeNameToDefinitions.get(((KeySchemaElement)lsi.getKeySchema().get(1)).getAttributeName());
                boolean isNewSQLiteColumnRequired = !nonGSIdDBAttrNameToSQLiteColNameMap.containsKey(lsiRangeKeyDef.getAttributeName());
                String lsiRangeSQLColName = isNewSQLiteColumnRequired ? "indexKey_" + nextColumnIndex++ : (String)nonGSIdDBAttrNameToSQLiteColNameMap.get(lsiRangeKeyDef.getAttributeName());
                ArrayList<SQLiteIndexElement> indexColumns = new ArrayList<SQLiteIndexElement>();
                indexColumns.add(baseTableHashKeyIndex);
                SQLiteIndexElement lsiRangeKeyIndex = new SQLiteIndexElement(KeyType.RANGE, lsiRangeKeyDef, lsiRangeSQLColName);
                indexColumns.add(lsiRangeKeyIndex);
                if (isNewSQLiteColumnRequired) {
                    this.uniqueIndexes.add(lsiRangeKeyIndex);
                }
                this.sqliteIndex.put(lsi.getIndexName(), indexColumns);
                nonGSIdDBAttrNameToSQLiteColNameMap.put(lsiRangeKeyDef.getAttributeName(), lsiRangeSQLColName);
            }
        }
        if (this.gsiDescList != null) {
            this.uniqueGSIIndexes = new ArrayList<List<SQLiteIndexElement>>();
            HashMap<String, String> dDBAttrNameToSQLiteColNameMap = new HashMap<String, String>(nonGSIdDBAttrNameToSQLiteColNameMap);
            nextColumnIndex = this.addGSIColumnMappings(this.gsiDescList, dDBAttrNameToSQLiteColNameMap, nextColumnIndex);
        }
    }

    public void addGSIColumnMappings(List<GlobalSecondaryIndexDescription> gsisToAdd, int nextColumnIndex) {
        this.addGSIColumnMappings(gsisToAdd, this.dDBAttrNameToSQLiteColNameMap(), nextColumnIndex);
    }

    public void removeGSIColumnMappings(List<GlobalSecondaryIndexDescription> gsiToDelete) {
        for (GlobalSecondaryIndexDescription gsi : gsiToDelete) {
            if (!IndexStatus.DELETING.toString().equals(gsi.getIndexStatus())) continue;
            List<SQLiteIndexElement> indexElements = this.sqliteIndex.get(gsi.getIndexName());
            this.sqliteIndex.remove(gsi.getIndexName());
            if (this.numberOfGSIsWithSchema(this.gsiDescList, gsi.getKeySchema()) != 1) continue;
            this.uniqueGSIIndexes = this.removeFrom(this.uniqueGSIIndexes, indexElements);
        }
    }

    private List<List<SQLiteIndexElement>> removeFrom(List<List<SQLiteIndexElement>> list, List<SQLiteIndexElement> elementToRemove) {
        ArrayList<List<SQLiteIndexElement>> result = new ArrayList<List<SQLiteIndexElement>>();
        for (List<SQLiteIndexElement> element : list) {
            if (LocalDBUtils.isSQLiteIndexElementsEqual(element, elementToRemove)) continue;
            result.add(element);
        }
        return result;
    }

    private int numberOfGSIsWithSchema(List<GlobalSecondaryIndexDescription> updatedGSIList, List<KeySchemaElement> keySchema) {
        int count = 0;
        for (GlobalSecondaryIndexDescription desc : updatedGSIList) {
            if (!LocalDBUtils.isKeySchemasEqual(keySchema, desc.getKeySchema())) continue;
            ++count;
        }
        return count;
    }

    private Map<String, String> dDBAttrNameToSQLiteColNameMap() {
        HashMap<String, String> result = new HashMap<String, String>();
        if (this.getSqliteIndex() != null) {
            for (Map.Entry<String, List<SQLiteIndexElement>> entry : this.getSqliteIndex().entrySet()) {
                for (SQLiteIndexElement ele : entry.getValue()) {
                    result.put(ele.getDynamoDBAttribute().getAttributeName(), ele.getSqliteColumnName());
                }
            }
        }
        return result;
    }

    private int addGSIColumnMappings(List<GlobalSecondaryIndexDescription> gsisToAdd, Map<String, String> dDBAttrNameToSQLiteColNameMap, int nextColumnIndex) {
        HashMap<String, Set<String>> gsiHashToRangeMap = new HashMap<String, Set<String>>();
        for (GlobalSecondaryIndexDescription gsi : gsisToAdd) {
            nextColumnIndex = this.addGSIColumnMapping(gsi, dDBAttrNameToSQLiteColNameMap, gsiHashToRangeMap, nextColumnIndex);
        }
        return nextColumnIndex;
    }

    private Map<String, String> nonGSIColumnMappings() {
        HashMap<String, String> result = new HashMap<String, String>();
        result.put(this.getHashKeyIndex().getDynamoDBAttribute().getAttributeName(), "hashKey");
        AttributeDefinition baseTableRangeKeyDef = this.getRangeKeyDefinition();
        if (baseTableRangeKeyDef != null) {
            result.put(baseTableRangeKeyDef.getAttributeName(), "rangeKey");
        }
        if (this.lsiList != null) {
            for (LocalSecondaryIndex lsi : this.lsiList) {
                SQLiteIndexElement lsiSQLElement = this.sqliteIndex.get(lsi.getIndexName()).get(1);
                result.put(((KeySchemaElement)lsi.getKeySchema().get(1)).getAttributeName(), lsiSQLElement.getSqliteColumnName());
            }
        }
        return result;
    }

    private Map<String, AttributeDefinition> attributeNameToDefinitionsMap() {
        HashMap<String, AttributeDefinition> result = new HashMap<String, AttributeDefinition>();
        for (AttributeDefinition attributeDefinition : this.attributes) {
            result.put(attributeDefinition.getAttributeName(), attributeDefinition);
        }
        return result;
    }

    private int addGSIColumnMapping(GlobalSecondaryIndexDescription gsi, Map<String, String> dDBAttrNameToSQLiteColNameMap, Map<String, Set<String>> gsiHashToRangeMap, int nextColumnIndex) {
        Map<String, AttributeDefinition> attributeNameToDefinitions = this.attributeNameToDefinitionsMap();
        AttributeDefinition gsiHashKeyDef = attributeNameToDefinitions.get(((KeySchemaElement)gsi.getKeySchema().get(0)).getAttributeName());
        AttributeDefinition gsiRangeKeyDef = null;
        if (gsi.getKeySchema().size() == 2) {
            gsiRangeKeyDef = attributeNameToDefinitions.get(((KeySchemaElement)gsi.getKeySchema().get(1)).getAttributeName());
        }
        boolean isNewSQLiteColRequiredForGSIHashKey = !dDBAttrNameToSQLiteColNameMap.containsKey(gsiHashKeyDef.getAttributeName());
        String gsiHashSQLColName = isNewSQLiteColRequiredForGSIHashKey ? "indexKey_" + nextColumnIndex++ : dDBAttrNameToSQLiteColNameMap.get(gsiHashKeyDef.getAttributeName());
        dDBAttrNameToSQLiteColNameMap.put(gsiHashKeyDef.getAttributeName(), gsiHashSQLColName);
        Object gsiRangeSQLColName = null;
        if (gsiRangeKeyDef != null) {
            gsiRangeSQLColName = dDBAttrNameToSQLiteColNameMap.get(gsiRangeKeyDef.getAttributeName());
        }
        if (gsiRangeKeyDef != null) {
            boolean isNewSQLiteColRequiredForGSIRangeKey = !dDBAttrNameToSQLiteColNameMap.containsKey(gsiRangeKeyDef.getAttributeName());
            gsiRangeSQLColName = isNewSQLiteColRequiredForGSIRangeKey ? "indexKey_" + nextColumnIndex++ : dDBAttrNameToSQLiteColNameMap.get(gsiRangeKeyDef.getAttributeName());
            dDBAttrNameToSQLiteColNameMap.put(gsiRangeKeyDef.getAttributeName(), (String)gsiRangeSQLColName);
        }
        List<SQLiteIndexElement> indexColumns = this.gsiIndexColumns(gsiHashSQLColName, (String)gsiRangeSQLColName, gsiHashKeyDef, gsiRangeKeyDef);
        if (this.uniqueGSIIndexes == null) {
            this.uniqueGSIIndexes = new ArrayList<List<SQLiteIndexElement>>();
        }
        if (this.isGSIUniqueIndex(gsiHashKeyDef, gsiRangeKeyDef, gsiHashToRangeMap)) {
            this.uniqueGSIIndexes.add(indexColumns);
            if (gsiHashToRangeMap.containsKey(gsiHashKeyDef.getAttributeName())) {
                if (gsiRangeKeyDef == null) {
                    gsiHashToRangeMap.get(gsiHashKeyDef.getAttributeName()).add(null);
                } else {
                    gsiHashToRangeMap.get(gsiHashKeyDef.getAttributeName()).add(gsiRangeKeyDef.getAttributeName());
                }
            } else {
                HashSet<String> rangeKeys = new HashSet<String>();
                if (gsiRangeKeyDef == null) {
                    rangeKeys.add(null);
                } else {
                    rangeKeys.add(gsiRangeKeyDef.getAttributeName());
                }
                gsiHashToRangeMap.put(gsiHashKeyDef.getAttributeName(), rangeKeys);
            }
        }
        dDBAttrNameToSQLiteColNameMap.put(gsiHashKeyDef.getAttributeName(), gsiHashSQLColName);
        if (gsiRangeKeyDef != null) {
            dDBAttrNameToSQLiteColNameMap.put(gsiRangeKeyDef.getAttributeName(), (String)gsiRangeSQLColName);
        }
        this.sqliteIndex.put(gsi.getIndexName(), indexColumns);
        return nextColumnIndex;
    }

    private List<SQLiteIndexElement> gsiIndexColumns(String gsiHashSQLColName, String gsiRangeSQLColName, AttributeDefinition gsiHashKeyDef, AttributeDefinition gsiRangeKeyDef) {
        ArrayList<SQLiteIndexElement> indexColumns = new ArrayList<SQLiteIndexElement>();
        SQLiteIndexElement gsiHashKeyIndex = new SQLiteIndexElement(KeyType.HASH, gsiHashKeyDef, gsiHashSQLColName);
        indexColumns.add(gsiHashKeyIndex);
        if (gsiRangeKeyDef != null) {
            SQLiteIndexElement gsiRangeKeyIndex = new SQLiteIndexElement(KeyType.RANGE, gsiRangeKeyDef, gsiRangeSQLColName);
            indexColumns.add(gsiRangeKeyIndex);
        }
        return indexColumns;
    }

    private boolean isGSIUniqueIndex(AttributeDefinition gsiHashKey, AttributeDefinition gsiRangeKeyDef, Map<String, Set<String>> gsiHashToRangeMap) {
        AttributeDefinition baseTableHashKeyDef = this.getHashKeyIndex().getDynamoDBAttribute();
        AttributeDefinition baseTableRangeKeyDef = this.getRangeKeyDefinition();
        String baseTableHashKeyName = baseTableHashKeyDef.getAttributeName();
        String gsiHashKeyName = gsiHashKey.getAttributeName();
        Map<String, String> nonGSIColumnMappings = this.nonGSIColumnMappings();
        if (baseTableRangeKeyDef == null && gsiRangeKeyDef == null && gsiHashKeyName.equals(baseTableHashKeyName)) {
            return false;
        }
        if (baseTableRangeKeyDef != null && gsiRangeKeyDef != null && nonGSIColumnMappings.containsKey(gsiHashKeyName) && nonGSIColumnMappings.containsKey(gsiRangeKeyDef.getAttributeName())) {
            return false;
        }
        if (gsiRangeKeyDef == null && gsiHashToRangeMap.containsKey(gsiHashKeyName) && gsiHashToRangeMap.get(gsiHashKeyName).contains(null)) {
            return false;
        }
        if (gsiRangeKeyDef != null) {
            if (gsiHashToRangeMap.containsKey(gsiHashKeyName) && gsiHashToRangeMap.get(gsiHashKeyName).contains(gsiRangeKeyDef.getAttributeName())) {
                return false;
            }
            return !gsiHashToRangeMap.containsKey(gsiRangeKeyDef.getAttributeName()) || !gsiHashToRangeMap.containsKey(gsiHashKeyName) || !gsiHashToRangeMap.get(gsiHashKeyName).contains(gsiHashKeyName);
        }
        return true;
    }

    @JsonIgnore
    public SQLiteIndexElement getHashKeyIndex() {
        return this.sqliteIndex.get("").get(0);
    }

    @JsonIgnore
    public AttributeDefinition getHashKeyDefinition() {
        return this.getHashKeyIndex().getDynamoDBAttribute();
    }

    @JsonIgnore
    public SQLiteIndexElement getRangeKeyIndex() {
        if (this.sqliteIndex.get("").size() == 2) {
            return this.sqliteIndex.get("").get(1);
        }
        return null;
    }

    @JsonIgnore
    public AttributeDefinition getRangeKeyDefinition() {
        SQLiteIndexElement index = this.getRangeKeyIndex();
        if (index != null) {
            return index.getDynamoDBAttribute();
        }
        return null;
    }

    @JsonIgnore
    public SQLiteIndexElement getLSIRangeIndexElement(String indexName) {
        List<SQLiteIndexElement> indexes = this.sqliteIndex.get(indexName);
        if (indexes == null) {
            return null;
        }
        return indexes.get(1);
    }

    @JsonIgnore
    public SQLiteIndexElement getGSIHashIndexElement(String indexName) {
        List<SQLiteIndexElement> indexes = this.sqliteIndex.get(indexName);
        if (indexes == null) {
            return null;
        }
        return indexes.get(0);
    }

    @JsonIgnore
    public SQLiteIndexElement getGSIRangeIndexElement(String indexName) {
        List<SQLiteIndexElement> indexes = this.sqliteIndex.get(indexName);
        if (indexes == null || indexes.size() < 2) {
            return null;
        }
        return indexes.get(1);
    }

    public String toString() {
        StringBuilder ret = new StringBuilder();
        for (String k : this.sqliteIndex.keySet()) {
            ret.append(k).append("\n");
            for (SQLiteIndexElement e : this.sqliteIndex.get(k)) {
                ret.append("\t").append(e.toString()).append("\n");
            }
        }
        return ret.toString();
    }

    boolean hasGSIs() {
        return this.getGsiDescList() != null && this.getGsiDescList().size() > 0;
    }

    public List<GlobalSecondaryIndexDescription> getGSIsByIndexStatus(IndexStatus status, Boolean backfilling) {
        if (this.hasGSIs()) {
            return LocalDBUtils.getGSIsByIndexStatus(this.getGsiDescList(), status, backfilling);
        }
        return new ArrayList<GlobalSecondaryIndexDescription>();
    }
}

