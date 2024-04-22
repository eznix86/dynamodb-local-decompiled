/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  com.amazonaws.services.dynamodbv2.model.AttributeDefinition
 *  com.amazonaws.services.dynamodbv2.model.BillingMode
 *  com.amazonaws.services.dynamodbv2.model.GlobalSecondaryIndex
 *  com.amazonaws.services.dynamodbv2.model.GlobalSecondaryIndexDescription
 *  com.amazonaws.services.dynamodbv2.model.KeySchemaElement
 *  com.amazonaws.services.dynamodbv2.model.KeyType
 *  com.amazonaws.services.dynamodbv2.model.LocalSecondaryIndex
 *  com.amazonaws.services.dynamodbv2.model.LocalSecondaryIndexDescription
 *  com.amazonaws.services.dynamodbv2.model.Projection
 *  com.amazonaws.services.dynamodbv2.model.ProjectionType
 *  com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput
 *  com.amazonaws.services.dynamodbv2.model.ProvisionedThroughputDescription
 *  com.amazonaws.services.dynamodbv2.model.ScalarAttributeType
 *  com.amazonaws.services.dynamodbv2.model.StreamSpecification
 *  com.amazonaws.services.dynamodbv2.model.TimeToLiveSpecification
 */
package com.amazonaws.services.dynamodbv2.local.shared.access;

import com.amazonaws.services.dynamodbv2.local.google.Lists;
import com.amazonaws.services.dynamodbv2.local.google.Preconditions;
import com.amazonaws.services.dynamodbv2.local.shared.access.LocalDBUtils;
import com.amazonaws.services.dynamodbv2.model.AttributeDefinition;
import com.amazonaws.services.dynamodbv2.model.BillingMode;
import com.amazonaws.services.dynamodbv2.model.GlobalSecondaryIndex;
import com.amazonaws.services.dynamodbv2.model.GlobalSecondaryIndexDescription;
import com.amazonaws.services.dynamodbv2.model.KeySchemaElement;
import com.amazonaws.services.dynamodbv2.model.KeyType;
import com.amazonaws.services.dynamodbv2.model.LocalSecondaryIndex;
import com.amazonaws.services.dynamodbv2.model.LocalSecondaryIndexDescription;
import com.amazonaws.services.dynamodbv2.model.Projection;
import com.amazonaws.services.dynamodbv2.model.ProjectionType;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughputDescription;
import com.amazonaws.services.dynamodbv2.model.ScalarAttributeType;
import com.amazonaws.services.dynamodbv2.model.StreamSpecification;
import com.amazonaws.services.dynamodbv2.model.TimeToLiveSpecification;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TableInfo {
    private final String tableName;
    private final AttributeDefinition hashKey;
    private final AttributeDefinition rangeKey;
    private final List<String> baseTableKeyNames;
    private final Map<String, List<String>> indexKeyNames;
    private final List<AttributeDefinition> allAttributes;
    private final Map<String, ScalarAttributeType> attributeNameToScalarAttributeType;
    private final Map<String, AttributeDefinition> lsiKeys;
    private final Map<String, AttributeDefinition> gsiHashKeys;
    private final Map<String, AttributeDefinition> gsiRangeKeys;
    private final ProvisionedThroughput throughput;
    private final BillingMode billingMode;
    private final Map<String, LocalSecondaryIndex> lsiMap;
    private final Map<String, GlobalSecondaryIndexDescription> gsiDescMap;
    private final StreamSpecification streamSpecification;
    private final TimeToLiveSpecification timeToLiveSpecification;
    private final String latestStreamId;
    private final Boolean deleteProtectionEnabled;
    private long lastUpdateToPayPerRequestDateTime;
    private long creationDateTime;
    private Long lastDecreaseDateTime;
    private Long lastIncreaseDateTime;
    private long numberOfDecreasesToday;

    TableInfo(String tableName, AttributeDefinition hashKey, AttributeDefinition rangeKey, List<AttributeDefinition> allAttributes, List<LocalSecondaryIndex> lsiIndexes, List<GlobalSecondaryIndexDescription> gsiIndexDescs, ProvisionedThroughput throughput, BillingMode billingMode, StreamSpecification streamSpecification, String latestStreamId, TimeToLiveSpecification timeToLiveSpecification, Boolean deleteProtectionEnabled) {
        Preconditions.checkNotNull(allAttributes, "allAttributes may not be null");
        this.tableName = tableName;
        this.hashKey = hashKey;
        this.rangeKey = rangeKey;
        this.allAttributes = allAttributes;
        this.attributeNameToScalarAttributeType = this.createAttributeNameToScalarAttributeTypeMap(allAttributes);
        this.lsiMap = new HashMap<String, LocalSecondaryIndex>();
        this.lsiKeys = new HashMap<String, AttributeDefinition>();
        if (lsiIndexes != null) {
            for (LocalSecondaryIndex curIndex : lsiIndexes) {
                String curIndexName = curIndex.getIndexName();
                KeySchemaElement curIndexRangeElement = (KeySchemaElement)curIndex.getKeySchema().get(1);
                this.lsiKeys.put(curIndexName, LocalDBUtils.findAttributeDefinition(curIndexRangeElement, allAttributes));
                this.lsiMap.put(curIndexName, curIndex);
            }
        }
        this.throughput = throughput;
        this.billingMode = billingMode;
        this.gsiDescMap = new HashMap<String, GlobalSecondaryIndexDescription>();
        this.gsiHashKeys = new HashMap<String, AttributeDefinition>();
        this.gsiRangeKeys = new HashMap<String, AttributeDefinition>();
        this.setGSIDescs(gsiIndexDescs);
        this.streamSpecification = streamSpecification;
        this.latestStreamId = latestStreamId;
        this.timeToLiveSpecification = timeToLiveSpecification;
        this.deleteProtectionEnabled = deleteProtectionEnabled;
        this.baseTableKeyNames = Collections.unmodifiableList(rangeKey == null ? Collections.singletonList(hashKey.getAttributeName()) : Lists.newArrayList(hashKey.getAttributeName(), rangeKey.getAttributeName()));
        this.indexKeyNames = new HashMap<String, List<String>>();
        if (lsiIndexes != null) {
            for (LocalSecondaryIndex lsi : lsiIndexes) {
                this.indexKeyNames.put(lsi.getIndexName(), this.getIndexKeyNames(lsi.getKeySchema()));
            }
        }
        if (gsiIndexDescs != null) {
            for (GlobalSecondaryIndexDescription gsi : gsiIndexDescs) {
                this.indexKeyNames.put(gsi.getIndexName(), this.getIndexKeyNames(gsi.getKeySchema()));
            }
        }
    }

    TableInfo(String tableName, AttributeDefinition hashKey, AttributeDefinition rangeKey, List<AttributeDefinition> allAttributes, List<LocalSecondaryIndex> lsiIndexes, List<GlobalSecondaryIndexDescription> gsiIndexesDesc, ProvisionedThroughput throughput, BillingMode billingMode, StreamSpecification streamSpecification, Boolean deleteProtectionEnabled) {
        this(tableName, hashKey, rangeKey, allAttributes, lsiIndexes, gsiIndexesDesc, throughput, billingMode, streamSpecification, null, null, deleteProtectionEnabled);
    }

    public TableInfo(String tableName, AttributeDefinition hashKey, AttributeDefinition rangeKey, List<AttributeDefinition> allAttributes, List<LocalSecondaryIndex> lsiIndexes, List<GlobalSecondaryIndexDescription> gsiIndexesDesc, ProvisionedThroughput throughput, BillingMode billingMode, String latestStreamId, StreamSpecification spec, TimeToLiveSpecification timeToLiveSpecification, long creationDateTime, Boolean deleteProtectionEnabled) {
        this(tableName, hashKey, rangeKey, allAttributes, lsiIndexes, gsiIndexesDesc, throughput, billingMode, spec, latestStreamId, timeToLiveSpecification, deleteProtectionEnabled);
        this.creationDateTime = creationDateTime;
    }

    public TableInfo(String tableName, AttributeDefinition hashKey, AttributeDefinition rangeKey, List<AttributeDefinition> allAttributes, List<LocalSecondaryIndex> lsiIndexes, List<GlobalSecondaryIndexDescription> gsiIndexesDesc, ProvisionedThroughput throughput, BillingMode billingMode, String latestStreamId, StreamSpecification spec, TimeToLiveSpecification timeToLiveSpecification, long creationDateTime, long lastDecreaseDateTime, long lastIncreaseDateTime, long numDecreasesToday, Boolean deleteProtectionEnabled) {
        this(tableName, hashKey, rangeKey, allAttributes, lsiIndexes, gsiIndexesDesc, throughput, billingMode, latestStreamId, spec, timeToLiveSpecification, creationDateTime, deleteProtectionEnabled);
        this.lastDecreaseDateTime = lastDecreaseDateTime;
        this.lastIncreaseDateTime = lastIncreaseDateTime;
        this.numberOfDecreasesToday = numDecreasesToday;
    }

    public TableInfo(String tableName, AttributeDefinition hashKey, AttributeDefinition rangeKey, List<AttributeDefinition> allAttributes, List<LocalSecondaryIndex> lsiIndexes, List<GlobalSecondaryIndexDescription> gsiIndexesDesc, ProvisionedThroughput throughput, BillingMode billingMode, StreamSpecification streamSpecification, long creationDateTime, Boolean deleteProtectionEnabled) {
        this(tableName, hashKey, rangeKey, allAttributes, lsiIndexes, gsiIndexesDesc, throughput, billingMode, streamSpecification, deleteProtectionEnabled);
        this.creationDateTime = creationDateTime;
        this.lastDecreaseDateTime = null;
        this.lastIncreaseDateTime = null;
        this.numberOfDecreasesToday = 0L;
    }

    private static AttributeDefinition copyAttributeDefinition(AttributeDefinition def) {
        return def == null ? null : new AttributeDefinition().withAttributeName(def.getAttributeName()).withAttributeType(def.getAttributeType());
    }

    private static KeySchemaElement copyKeySchemaElement(KeySchemaElement e) {
        return e == null ? null : new KeySchemaElement().withAttributeName(e.getAttributeName()).withKeyType(e.getKeyType());
    }

    private static Projection copyProjection(Projection p) {
        return p == null ? null : new Projection().withProjectionType(p.getProjectionType()).withNonKeyAttributes((Collection)(p.getNonKeyAttributes() == null ? null : new ArrayList(p.getNonKeyAttributes())));
    }

    private static ProvisionedThroughput copyProvisionedThroughput(ProvisionedThroughput pt) {
        return pt == null ? null : new ProvisionedThroughput().withReadCapacityUnits(pt.getReadCapacityUnits()).withWriteCapacityUnits(pt.getWriteCapacityUnits());
    }

    private static ProvisionedThroughputDescription copyProvisionedThroughputDescription(ProvisionedThroughputDescription pt) {
        return pt == null ? null : new ProvisionedThroughputDescription().withReadCapacityUnits(pt.getReadCapacityUnits()).withWriteCapacityUnits(pt.getWriteCapacityUnits()).withLastIncreaseDateTime(pt.getLastIncreaseDateTime()).withLastDecreaseDateTime(pt.getLastDecreaseDateTime()).withNumberOfDecreasesToday(pt.getNumberOfDecreasesToday());
    }

    private static LocalSecondaryIndex copyLocalSecondaryIndex(LocalSecondaryIndex idx) {
        if (idx == null) {
            return null;
        }
        LocalSecondaryIndex index = new LocalSecondaryIndex().withIndexName(idx.getIndexName());
        index.setKeySchema((Collection)(idx.getKeySchema() == null ? null : new ArrayList()));
        if (index.getKeySchema() != null) {
            for (KeySchemaElement e : idx.getKeySchema()) {
                index.getKeySchema().add(TableInfo.copyKeySchemaElement(e));
            }
        }
        index.setProjection(TableInfo.copyProjection(idx.getProjection()));
        return index;
    }

    private static GlobalSecondaryIndexDescription copyGlobalSecondaryIndexDescription(GlobalSecondaryIndexDescription idx) {
        if (idx == null) {
            return null;
        }
        GlobalSecondaryIndexDescription index = new GlobalSecondaryIndexDescription().withIndexName(idx.getIndexName());
        index.setKeySchema((Collection)(idx.getKeySchema() == null ? null : new ArrayList()));
        if (index.getKeySchema() != null) {
            for (KeySchemaElement e : idx.getKeySchema()) {
                index.getKeySchema().add(TableInfo.copyKeySchemaElement(e));
            }
        }
        index.setProjection(TableInfo.copyProjection(idx.getProjection()));
        index.setProvisionedThroughput(TableInfo.copyProvisionedThroughputDescription(idx.getProvisionedThroughput()));
        index.setBackfilling(idx.getBackfilling());
        index.setIndexStatus(idx.getIndexStatus());
        index.setIndexSizeBytes(idx.getIndexSizeBytes());
        index.setItemCount(idx.getItemCount());
        return index;
    }

    private static StreamSpecification copyStreamSpecification(StreamSpecification spec) {
        return spec == null ? null : new StreamSpecification().withStreamEnabled(spec.getStreamEnabled()).withStreamViewType(spec.getStreamViewType());
    }

    public List<String> getIndexKeyNames(String index) {
        return this.indexKeyNames.get(index);
    }

    private List<String> getIndexKeyNames(List<KeySchemaElement> keySchemata) {
        ArrayList<String> result = new ArrayList<String>(keySchemata.size());
        for (KeySchemaElement key : keySchemata) {
            result.add(key.getAttributeName());
        }
        return result;
    }

    private Map<String, ScalarAttributeType> createAttributeNameToScalarAttributeTypeMap(List<AttributeDefinition> definitions) {
        HashMap<String, ScalarAttributeType> result = new HashMap<String, ScalarAttributeType>();
        Preconditions.checkNotNull(definitions, "attribute definition list was null");
        for (AttributeDefinition attributeDefinition : definitions) {
            if (result.put(attributeDefinition.getAttributeName(), ScalarAttributeType.fromValue((String)attributeDefinition.getAttributeType())) == null) continue;
            throw new IllegalArgumentException("Duplicate attribute name in attribute definition list: " + attributeDefinition.getAttributeName());
        }
        return Collections.unmodifiableMap(result);
    }

    private void setGSIDescs(List<GlobalSecondaryIndexDescription> gsiIndexesDesc) {
        if (gsiIndexesDesc != null) {
            for (GlobalSecondaryIndexDescription globalSecondaryIndexDescription : gsiIndexesDesc) {
                String curIndexDescName = globalSecondaryIndexDescription.getIndexName();
                this.gsiDescMap.put(curIndexDescName, globalSecondaryIndexDescription);
            }
        }
        for (Map.Entry entry : this.gsiDescMap.entrySet()) {
            GlobalSecondaryIndexDescription curIndex = (GlobalSecondaryIndexDescription)entry.getValue();
            String curIndexName = curIndex.getIndexName();
            KeySchemaElement curIndexHashElement = (KeySchemaElement)curIndex.getKeySchema().get(0);
            this.gsiHashKeys.put(curIndexName, LocalDBUtils.findAttributeDefinition(curIndexHashElement, this.allAttributes));
            if (curIndex.getKeySchema().size() != 2) continue;
            KeySchemaElement curIndexRangeElement = (KeySchemaElement)curIndex.getKeySchema().get(1);
            this.gsiRangeKeys.put(curIndexName, LocalDBUtils.findAttributeDefinition(curIndexRangeElement, this.allAttributes));
        }
    }

    public ScalarAttributeType indexedAttributeType(String attributeName) {
        return this.attributeNameToScalarAttributeType.get(attributeName);
    }

    public List<String> getBaseTableKeyNames() {
        return this.baseTableKeyNames;
    }

    public String getTableName() {
        return this.tableName;
    }

    public ProvisionedThroughput getThroughput() {
        return this.throughput;
    }

    public BillingMode getBillingMode() {
        return this.billingMode;
    }

    public long getLastUpdateToPayPerRequestDateTime() {
        return this.lastUpdateToPayPerRequestDateTime;
    }

    public void setLastUpdateToPayPerRequestDateTime(long lastUpdateToPayPerRequestDateTime) {
        this.lastUpdateToPayPerRequestDateTime = lastUpdateToPayPerRequestDateTime;
    }

    public boolean hasRangeKey() {
        return this.rangeKey != null;
    }

    public AttributeDefinition getHashKey() {
        return this.hashKey;
    }

    public AttributeDefinition getRangeKey() {
        return this.rangeKey;
    }

    public AttributeDefinition getLSIRangeKey(String indexName) {
        return this.lsiKeys.get(indexName);
    }

    public AttributeDefinition getGSIHashKey(String indexName) {
        return this.gsiHashKeys.get(indexName);
    }

    public AttributeDefinition getGSIRangeKey(String indexName) {
        return this.gsiRangeKeys.get(indexName);
    }

    public boolean hasIndex(String indexName) {
        return this.lsiKeys.containsKey(indexName) || this.gsiHashKeys.containsKey(indexName);
    }

    public long getCreationDateTime() {
        return this.creationDateTime;
    }

    public void setCreationDateTime(long curTime) {
        this.creationDateTime = curTime;
    }

    public List<String> getLSINames() {
        return new ArrayList<String>(this.lsiMap.keySet());
    }

    public List<String> getGSINames() {
        return new ArrayList<String>(this.gsiDescMap.keySet());
    }

    public List<AttributeDefinition> getAttributeDefinitions() {
        return this.allAttributes;
    }

    public List<KeySchemaElement> getKeySchema() {
        ArrayList<KeySchemaElement> keySchema = new ArrayList<KeySchemaElement>();
        keySchema.add(new KeySchemaElement().withAttributeName(this.hashKey.getAttributeName()).withKeyType(KeyType.HASH));
        if (this.rangeKey != null) {
            keySchema.add(new KeySchemaElement().withAttributeName(this.rangeKey.getAttributeName()).withKeyType(KeyType.RANGE));
        }
        return keySchema;
    }

    public ProvisionedThroughputDescription getThroughputDescription() {
        return new ProvisionedThroughputDescription().withReadCapacityUnits(this.throughput.getReadCapacityUnits()).withWriteCapacityUnits(this.throughput.getWriteCapacityUnits()).withLastDecreaseDateTime(this.lastDecreaseDateTime == null ? null : new Date(this.lastDecreaseDateTime)).withLastIncreaseDateTime(this.lastIncreaseDateTime == null ? null : new Date(this.lastIncreaseDateTime)).withNumberOfDecreasesToday(Long.valueOf(this.numberOfDecreasesToday));
    }

    public ProvisionedThroughputDescription getGSIThroughputDescription(String gsi, Long lastDecreaseDateTime, Long lastIncreaseDateTime, Long numberOfDecreasesToday) {
        return new ProvisionedThroughputDescription().withReadCapacityUnits(this.gsiDescMap.get(gsi).getProvisionedThroughput().getReadCapacityUnits()).withWriteCapacityUnits(this.gsiDescMap.get(gsi).getProvisionedThroughput().getWriteCapacityUnits()).withLastDecreaseDateTime(lastDecreaseDateTime == null ? null : new Date(lastDecreaseDateTime)).withLastIncreaseDateTime(lastIncreaseDateTime == null ? null : new Date(lastIncreaseDateTime)).withNumberOfDecreasesToday(numberOfDecreasesToday);
    }

    public List<LocalSecondaryIndexDescription> getLSIDescriptions() {
        if (this.lsiMap.size() == 0) {
            return null;
        }
        ArrayList<LocalSecondaryIndexDescription> indexDescList = new ArrayList<LocalSecondaryIndexDescription>();
        for (Map.Entry<String, LocalSecondaryIndex> entry : this.lsiMap.entrySet()) {
            LocalSecondaryIndex index = entry.getValue();
            indexDescList.add(new LocalSecondaryIndexDescription().withIndexName(index.getIndexName()).withKeySchema((Collection)index.getKeySchema()).withProjection(index.getProjection()).withIndexSizeBytes(Long.valueOf(0L)).withItemCount(Long.valueOf(0L)).withIndexArn(LocalDBUtils.generateArn(this.tableName, index.getIndexName(), null)));
        }
        return indexDescList;
    }

    public List<GlobalSecondaryIndexDescription> getGSIDescriptions() {
        if (this.gsiDescMap.values().isEmpty()) {
            return null;
        }
        ArrayList<GlobalSecondaryIndexDescription> gsiDescriptions = new ArrayList<GlobalSecondaryIndexDescription>();
        for (GlobalSecondaryIndexDescription gsiDesc : this.gsiDescMap.values()) {
            gsiDescriptions.add(gsiDesc.withIndexArn(LocalDBUtils.generateArn(this.tableName, gsiDesc.getIndexName(), null)));
        }
        return gsiDescriptions;
    }

    public Projection getProjection(String indexName) {
        if (this.lsiMap.containsKey(indexName)) {
            return this.lsiMap.get(indexName).getProjection();
        }
        return this.gsiDescMap.get(indexName).getProjection();
    }

    public AttributeDefinition getLSIRangeKeyWithAttributeName(String attrName) {
        for (Map.Entry<String, AttributeDefinition> entry : this.lsiKeys.entrySet()) {
            AttributeDefinition curDef = entry.getValue();
            if (!curDef.getAttributeName().equals(attrName)) continue;
            return curDef;
        }
        return null;
    }

    public AttributeDefinition getGSIRangeKeyWithAttributeName(String attrName) {
        for (Map.Entry<String, AttributeDefinition> entry : this.gsiRangeKeys.entrySet()) {
            AttributeDefinition curDef = entry.getValue();
            if (!curDef.getAttributeName().equals(attrName)) continue;
            return curDef;
        }
        return null;
    }

    public AttributeDefinition getGSIHashKeyWithAttributeName(String attrName) {
        for (Map.Entry<String, AttributeDefinition> entry : this.gsiHashKeys.entrySet()) {
            AttributeDefinition curDef = entry.getValue();
            if (!curDef.getAttributeName().equals(attrName)) continue;
            return curDef;
        }
        return null;
    }

    public List<LocalSecondaryIndex> getLSIIndexes() {
        if (this.lsiMap.values().isEmpty()) {
            return null;
        }
        return new ArrayList<LocalSecondaryIndex>(this.lsiMap.values());
    }

    @Deprecated
    public List<GlobalSecondaryIndex> getGSIIndexes() {
        return LocalDBUtils.getGsiListFrom(new ArrayList<GlobalSecondaryIndexDescription>(this.gsiDescMap.values()));
    }

    public boolean isLSIIndex(String indexName) {
        return this.lsiMap.containsKey(indexName);
    }

    public boolean isGSIIndex(String indexName) {
        return this.gsiDescMap.containsKey(indexName);
    }

    public GlobalSecondaryIndexDescription getGSIDescWithName(String gsiName) {
        return this.gsiDescMap.get(gsiName);
    }

    public boolean hasGSIs() {
        return this.getGSIDescriptions() != null && this.getGSIDescriptions().size() > 0;
    }

    public int getNumberOfLSIProjectedAttributes() {
        int numberOfLSIProjectedAttrs = 0;
        if (this.getLSIIndexes() != null) {
            for (LocalSecondaryIndex lsi : this.getLSIIndexes()) {
                if (!ProjectionType.INCLUDE.toString().equals(lsi.getProjection().getProjectionType())) continue;
                numberOfLSIProjectedAttrs += lsi.getProjection().getNonKeyAttributes().size();
            }
        }
        return numberOfLSIProjectedAttrs;
    }

    public StreamSpecification getStreamSpecification() {
        return this.streamSpecification;
    }

    public TimeToLiveSpecification getTimeToLiveSpecification() {
        return this.timeToLiveSpecification;
    }

    public String getLatestStreamId() {
        return this.latestStreamId;
    }

    public Boolean getDeleteProtectionEnabled() {
        return this.deleteProtectionEnabled;
    }
}

