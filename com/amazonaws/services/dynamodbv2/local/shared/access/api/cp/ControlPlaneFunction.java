/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  com.amazonaws.services.dynamodbv2.model.AttributeDefinition
 *  com.amazonaws.services.dynamodbv2.model.BillingMode
 *  com.amazonaws.services.dynamodbv2.model.BillingModeSummary
 *  com.amazonaws.services.dynamodbv2.model.GlobalSecondaryIndexDescription
 *  com.amazonaws.services.dynamodbv2.model.IndexStatus
 *  com.amazonaws.services.dynamodbv2.model.KeySchemaElement
 *  com.amazonaws.services.dynamodbv2.model.KeyType
 *  com.amazonaws.services.dynamodbv2.model.LocalSecondaryIndexDescription
 *  com.amazonaws.services.dynamodbv2.model.Projection
 *  com.amazonaws.services.dynamodbv2.model.ProjectionType
 *  com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput
 *  com.amazonaws.services.dynamodbv2.model.StreamSpecification
 *  com.amazonaws.services.dynamodbv2.model.StreamViewType
 *  com.amazonaws.services.dynamodbv2.model.TableDescription
 *  com.amazonaws.services.dynamodbv2.model.TableStatus
 *  com.amazonaws.util.CollectionUtils
 */
package com.amazonaws.services.dynamodbv2.local.shared.access.api.cp;

import com.amazonaws.services.dynamodbv2.exceptions.AWSExceptionFactory;
import com.amazonaws.services.dynamodbv2.exceptions.AmazonServiceExceptionType;
import com.amazonaws.services.dynamodbv2.local.shared.access.LocalDBAccess;
import com.amazonaws.services.dynamodbv2.local.shared.access.LocalDBUtils;
import com.amazonaws.services.dynamodbv2.local.shared.access.TableInfo;
import com.amazonaws.services.dynamodbv2.local.shared.access.api.DynamoDbApiFunction;
import com.amazonaws.services.dynamodbv2.local.shared.exceptions.LocalDBClientExceptionMessage;
import com.amazonaws.services.dynamodbv2.model.AttributeDefinition;
import com.amazonaws.services.dynamodbv2.model.BillingMode;
import com.amazonaws.services.dynamodbv2.model.BillingModeSummary;
import com.amazonaws.services.dynamodbv2.model.GlobalSecondaryIndexDescription;
import com.amazonaws.services.dynamodbv2.model.IndexStatus;
import com.amazonaws.services.dynamodbv2.model.KeySchemaElement;
import com.amazonaws.services.dynamodbv2.model.KeyType;
import com.amazonaws.services.dynamodbv2.model.LocalSecondaryIndexDescription;
import com.amazonaws.services.dynamodbv2.model.Projection;
import com.amazonaws.services.dynamodbv2.model.ProjectionType;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.model.StreamSpecification;
import com.amazonaws.services.dynamodbv2.model.StreamViewType;
import com.amazonaws.services.dynamodbv2.model.TableDescription;
import com.amazonaws.services.dynamodbv2.model.TableStatus;
import com.amazonaws.util.CollectionUtils;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

abstract class ControlPlaneFunction<I, O>
extends DynamoDbApiFunction<I, O> {
    protected static final ProvisionedThroughput ZERO_PROVISIONED_THROUGHPUT = new ProvisionedThroughput().withReadCapacityUnits(Long.valueOf(0L)).withWriteCapacityUnits(Long.valueOf(0L));

    ControlPlaneFunction(LocalDBAccess dbAccess) {
        super(dbAccess);
    }

    int validateGSISchemas(List<GlobalSecondaryIndexDescription> gsiDescList, AttributeDefinition hashKeyDef, AttributeDefinition rangeKeyDef, List<AttributeDefinition> allAttributes, List<String> lsiNames, int numberOfProjectedAttrsInLSI, BillingMode billingMode) {
        HashSet<String> gsiNames = new HashSet<String>();
        HashSet<AttributeDefinition> gsiKeys = new HashSet<AttributeDefinition>();
        int totalProjectedAttrs = numberOfProjectedAttrsInLSI;
        for (GlobalSecondaryIndexDescription gsi : gsiDescList) {
            String gsiName = gsi.getIndexName();
            switch (IndexStatus.fromValue((String)gsi.getIndexStatus())) {
                case CREATING: 
                case UPDATING: 
                case ACTIVE: {
                    AttributeDefinition gsiHashKeyDef;
                    if (BillingMode.PROVISIONED == billingMode) {
                        this.validateGSIProvisionedThroughput(LocalDBUtils.getPTfromPTDescription(gsi.getProvisionedThroughput()));
                    }
                    this.validateTableName(gsiName);
                    if (gsiNames.contains(gsiName)) {
                        throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, LocalDBClientExceptionMessage.SAME_NAME_GSI.getMessage());
                    }
                    if (lsiNames.contains(gsiName)) {
                        throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, LocalDBClientExceptionMessage.SAME_NAME_LSI_GSI.getMessage());
                    }
                    if ((long)(totalProjectedAttrs += this.validateProjection(gsi.getProjection(), null)) > 100L) {
                        throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, LocalDBClientExceptionMessage.TOO_MANY_PROJECTED.getMessage());
                    }
                    gsiNames.add(gsiName);
                    List gsiSchema = gsi.getKeySchema();
                    this.validateKeySchema(gsiSchema);
                    KeySchemaElement gsiHashKey = (KeySchemaElement)gsiSchema.get(0);
                    KeySchemaElement gsiRangeKey = null;
                    if (gsiSchema.size() > 1) {
                        gsiRangeKey = (KeySchemaElement)gsiSchema.get(1);
                    }
                    if ((gsiHashKeyDef = LocalDBUtils.findAttributeDefinition(gsiHashKey, allAttributes)) == null) {
                        throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, LocalDBClientExceptionMessage.NON_SPECIFIED_GSI_HASH_KEY.getMessage());
                    }
                    if (gsiRangeKey != null) {
                        AttributeDefinition gsiRangeKeyDef = LocalDBUtils.findAttributeDefinition(gsiRangeKey, allAttributes);
                        if (gsiRangeKeyDef == null) {
                            throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, LocalDBClientExceptionMessage.NON_SPECIFIED_GSI_RANGE_KEY.getMessage());
                        }
                        if (!gsiRangeKeyDef.equals((Object)rangeKeyDef)) {
                            gsiKeys.add(gsiRangeKeyDef);
                        }
                    }
                    if (gsiHashKeyDef.equals((Object)hashKeyDef)) break;
                    gsiKeys.add(gsiHashKeyDef);
                    break;
                }
                case DELETING: {
                    this.validateTableName(gsiName);
                }
            }
        }
        return gsiKeys.size();
    }

    void validateGSIProvisionedThroughput(ProvisionedThroughput throughput) {
        if (throughput == null) {
            throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, LocalDBClientExceptionMessage.INVALID_THROUGHPUT_GSI_NULL.getMessage());
        }
        Long readCapacity = throughput.getReadCapacityUnits();
        Long writeCapacity = throughput.getWriteCapacityUnits();
        if (readCapacity == null || writeCapacity == null) {
            throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, LocalDBClientExceptionMessage.INVALID_THROUGHPUT_VALUES_GSI.getMessage());
        }
        if (readCapacity < 1L || writeCapacity < 1L) {
            throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, LocalDBClientExceptionMessage.INVALID_THROUGHPUT_VALUES_GSI.getMessage());
        }
        if (throughput.getReadCapacityUnits() > 40000L || throughput.getWriteCapacityUnits() > 40000L) {
            throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, LocalDBClientExceptionMessage.TOO_MUCH_THROUGHPUT_GSI.getMessage());
        }
    }

    int validateProjection(Projection curProjection, List<String> projAttributes) {
        if (curProjection == null || curProjection.getProjectionType() == null) {
            throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, LocalDBClientExceptionMessage.INVALID_INDEX_NO_PROJECTION.getMessage());
        }
        int projectedAttrs = 0;
        try {
            ProjectionType curProjectionType = ProjectionType.fromValue((String)curProjection.getProjectionType());
            if (curProjectionType == ProjectionType.INCLUDE) {
                List nonKeyAttrs = curProjection.getNonKeyAttributes();
                if (nonKeyAttrs == null || nonKeyAttrs.size() == 0) {
                    throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, LocalDBClientExceptionMessage.NO_PROJECTED_ATTRS.getMessage());
                }
                HashSet<String> nonKeyNamesSet = new HashSet<String>();
                for (String attrName : nonKeyAttrs) {
                    this.validateAttributeName(attrName);
                    if (nonKeyNamesSet.contains(attrName)) {
                        throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, LocalDBClientExceptionMessage.IDENTICAL_ATTRIBUTE_NAMES.getMessage());
                    }
                    if (projAttributes != null) {
                        projAttributes.add(attrName);
                    }
                    ++projectedAttrs;
                    nonKeyNamesSet.add(attrName);
                }
            } else if (!CollectionUtils.isNullOrEmpty((Collection)curProjection.getNonKeyAttributes())) {
                throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, LocalDBClientExceptionMessage.INVALID_PROJECTED_ATTRS.getMessage());
            }
        } catch (IllegalArgumentException invalidException) {
            throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, LocalDBClientExceptionMessage.INVALID_PROJECTION_TYPE.getMessage());
        }
        return projectedAttrs;
    }

    void validateKeySchema(List<KeySchemaElement> keySchema) {
        if (keySchema == null) {
            throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, LocalDBClientExceptionMessage.NO_KEY_SCHEMA.getMessage());
        }
        int size = keySchema.size();
        if (size > 2) {
            throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, LocalDBClientExceptionMessage.KEY_SCHEMA_TOO_BIG.getMessage());
        }
        int numHashKeys = 0;
        for (KeySchemaElement aKeySchema : keySchema) {
            if (aKeySchema == null) {
                throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, LocalDBClientExceptionMessage.INVALID_KEY_SCHEMA_ELEMENT.getMessage());
            }
            if (aKeySchema.getKeyType() == null) {
                throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, LocalDBClientExceptionMessage.UNSPECIFIED_KEY_TYPE.getMessage());
            }
            if (aKeySchema.getAttributeName() == null) {
                throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, LocalDBClientExceptionMessage.NO_ATTRIBUTE_NAME.getMessage());
            }
            if (!aKeySchema.getKeyType().equals(KeyType.HASH.toString())) continue;
            ++numHashKeys;
        }
        if (numHashKeys == 0) {
            throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, LocalDBClientExceptionMessage.NO_HASH_KEY.getMessage());
        }
        if (numHashKeys == 2) {
            throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, LocalDBClientExceptionMessage.TOO_MANY_HASH_KEYS.getMessage());
        }
        if (size == 2 && keySchema.get(1).getKeyType().equals(KeyType.HASH.toString())) {
            throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, LocalDBClientExceptionMessage.INCORRECT_KEY_SCHEMA_ORDER.getMessage());
        }
        if (size == 2 && keySchema.get(0).getAttributeName().equals(keySchema.get(1).getAttributeName())) {
            throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, LocalDBClientExceptionMessage.IDENTICALLY_NAMED_KEYS.getMessage());
        }
    }

    void validateStreamSpecification(StreamSpecification newSpec, StreamSpecification currentSpec, boolean creatingTable) {
        if (newSpec == null) {
            return;
        }
        if (newSpec.getStreamEnabled() == null) {
            throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, "Stream StreamEnabled was null");
        }
        if (newSpec.getStreamEnabled().booleanValue()) {
            if (newSpec.getStreamViewType() == null || newSpec.getStreamViewType().isEmpty()) {
                throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, LocalDBClientExceptionMessage.INVALID_STREAM_VIEW_TYPE.getMessage() + ": Stream ViewType must not be null or empty when enabling streams.");
            }
            try {
                StreamViewType.fromValue((String)newSpec.getStreamViewType());
            } catch (IllegalArgumentException e) {
                throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, LocalDBClientExceptionMessage.INVALID_STREAM_VIEW_TYPE.getMessage() + ": " + newSpec.getStreamViewType());
            }
            if (currentSpec != null && currentSpec.getStreamEnabled().booleanValue() && newSpec.getStreamEnabled().booleanValue()) {
                throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, LocalDBClientExceptionMessage.INVALID_ENABLE_STREAM_REQUEST.getMessage());
            }
        } else if (!newSpec.getStreamEnabled().booleanValue()) {
            if (newSpec.getStreamViewType() != null) {
                throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, LocalDBClientExceptionMessage.INVALID_STREAM_VIEW_TYPE.getMessage() + ": Stream StreamEnabled was false but a StreamViewType was present.");
            }
            if (!creatingTable && (currentSpec == null || currentSpec != null && currentSpec.getStreamEnabled() != null && !currentSpec.getStreamEnabled().booleanValue())) {
                throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, LocalDBClientExceptionMessage.INVALID_STREAM_REQUEST.getMessage());
            }
        }
    }

    TableDescription getTableDescriptionHelper(String tableName) {
        TableInfo tableInfo = this.validateTableExists(tableName);
        TableDescription tableDescription = new TableDescription().withTableName(tableName).withAttributeDefinitions(tableInfo.getAttributeDefinitions()).withKeySchema(tableInfo.getKeySchema()).withTableStatus(TableStatus.ACTIVE).withCreationDateTime(new Date(tableInfo.getCreationDateTime())).withProvisionedThroughput(tableInfo.getThroughputDescription()).withLocalSecondaryIndexes(tableInfo.getLSIDescriptions()).withGlobalSecondaryIndexes(tableInfo.getGSIDescriptions()).withStreamSpecification(tableInfo.getStreamSpecification()).withLatestStreamArn(tableInfo.getLatestStreamId()).withDeletionProtectionEnabled(tableInfo.getDeleteProtectionEnabled()).withLatestStreamLabel(LocalDBUtils.extractStreamLabelFromArn(tableInfo.getLatestStreamId())).withTableArn(LocalDBUtils.generateArn(tableName, null, null));
        this.insertBillingModeSummaryIfNeeded(tableDescription, tableInfo);
        if (!(tableDescription.getStreamSpecification() == null || tableDescription.getStreamSpecification().isStreamEnabled().booleanValue() && tableDescription.getLatestStreamArn() == null)) {
            AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.INTERNAL_SERVER_ERROR, "StreamARN should not be null if Streams is enabled");
        }
        if (tableDescription.getStreamSpecification() != null && !tableDescription.getStreamSpecification().isStreamEnabled().booleanValue()) {
            tableDescription.setStreamSpecification(null);
        }
        return tableDescription;
    }

    protected void insertBillingModeSummaryIfNeeded(TableDescription tableDescription, TableInfo tableInfo) {
        BillingMode billingMode = tableInfo.getBillingMode();
        if (BillingMode.PROVISIONED.equals((Object)billingMode) && tableInfo.getLastUpdateToPayPerRequestDateTime() > 0L || BillingMode.PAY_PER_REQUEST.equals((Object)billingMode)) {
            BillingModeSummary billingModeSummary = new BillingModeSummary();
            billingModeSummary.setBillingMode(billingMode.toString());
            billingModeSummary.withLastUpdateToPayPerRequestDateTime(new Date(tableInfo.getLastUpdateToPayPerRequestDateTime()));
            tableDescription.withBillingModeSummary(billingModeSummary);
        }
    }

    TableDescription fillDescriptionHelper(TableDescription description) {
        String indexName;
        String tableName = description.getTableName();
        long tableItemCount = this.dbAccess.getTableItemCount(tableName);
        description.setItemCount(Long.valueOf(tableItemCount));
        description.setTableSizeBytes(Long.valueOf(this.dbAccess.getTableByteSize(tableName)));
        if (description.getLocalSecondaryIndexes() != null) {
            for (LocalSecondaryIndexDescription lsiDesc : description.getLocalSecondaryIndexes()) {
                indexName = lsiDesc.getIndexName();
                long indexItemCount = this.dbAccess.getLSIItemCount(tableName, indexName);
                lsiDesc.setItemCount(Long.valueOf(indexItemCount));
                lsiDesc.setIndexSizeBytes(Long.valueOf(this.dbAccess.getLSIByteSize(tableName, indexName)));
            }
        }
        if (description.getGlobalSecondaryIndexes() != null) {
            for (GlobalSecondaryIndexDescription gsiDesc : description.getGlobalSecondaryIndexes()) {
                if (!this.isGSIInAStateToCountItems(gsiDesc)) continue;
                indexName = gsiDesc.getIndexName();
                gsiDesc.setItemCount(Long.valueOf(this.dbAccess.getGSIItemCount(tableName, indexName)));
                gsiDesc.setIndexSizeBytes(Long.valueOf(this.dbAccess.getGSIByteSize(tableName, indexName)));
            }
        }
        return description;
    }

    void validateProvisionedThroughputIncrease(ProvisionedThroughput throughputAfterUpdate, ProvisionedThroughput throughputBeforeUpdate) {
        if (throughputAfterUpdate == null) {
            throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, LocalDBClientExceptionMessage.INVALID_THROUGHPUT_NULL.getMessage());
        }
        Long readCapacity = throughputAfterUpdate.getReadCapacityUnits();
        Long writeCapacity = throughputAfterUpdate.getWriteCapacityUnits();
        if (readCapacity == null || writeCapacity == null) {
            throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, LocalDBClientExceptionMessage.INVALID_THROUGHPUT_VALUES.getMessage());
        }
        if (readCapacity < 1L || writeCapacity < 1L) {
            throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, LocalDBClientExceptionMessage.INVALID_THROUGHPUT_VALUES.getMessage());
        }
        if (throughputBeforeUpdate != null && throughputAfterUpdate.getReadCapacityUnits() == throughputBeforeUpdate.getReadCapacityUnits() && throughputAfterUpdate.getWriteCapacityUnits() == throughputBeforeUpdate.getWriteCapacityUnits()) {
            throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, LocalDBClientExceptionMessage.SAME_THROUGHPUT.getMessage());
        }
    }

    void validateAttributeDefinitions(List<AttributeDefinition> attrDefs) {
        if (attrDefs == null) {
            throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, LocalDBClientExceptionMessage.NO_ATTRIBUTE_SCHEMA.getMessage());
        }
        HashSet<String> validatedAttrNames = new HashSet<String>();
        for (AttributeDefinition curAttr : attrDefs) {
            String curName = curAttr.getAttributeName();
            this.validateAttributeName(curName);
            if (validatedAttrNames.contains(curName)) {
                throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, LocalDBClientExceptionMessage.IDENTICAL_ATTRIBUTE_NAMES.getMessage());
            }
            validatedAttrNames.add(curName);
            LocalDBUtils.getDataTypeOfAttributeDefinition(curAttr);
        }
        LocalDBUtils.getDataTypesOfAttributeDefinitions(attrDefs, false);
    }

    void validateProvisionedThroughputWithGSIs(String tableName, ProvisionedThroughput throughput, List<GlobalSecondaryIndexDescription> updatedGSIList) {
        if (throughput.getReadCapacityUnits() > 40000L || throughput.getWriteCapacityUnits() > 40000L) {
            throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, LocalDBClientExceptionMessage.TOO_MUCH_THROUGHPUT_TABLE.getMessage());
        }
        long totalReadThroughput = 0L;
        long totalWriteThroughput = 0L;
        List<String> allTableNames = this.dbAccess.listTables(null, -1L).getTableNames();
        for (String table2 : allTableNames) {
            if (table2.equals(tableName)) continue;
            TableInfo tableInfo = this.dbAccess.getTableInfo(table2);
            ProvisionedThroughput existingThroughput = tableInfo.getThroughput();
            totalReadThroughput += existingThroughput.getReadCapacityUnits().longValue();
            totalWriteThroughput += existingThroughput.getWriteCapacityUnits().longValue();
            if (!tableInfo.hasGSIs()) continue;
            for (GlobalSecondaryIndexDescription gsi : tableInfo.getGSIDescriptions()) {
                totalReadThroughput += gsi.getProvisionedThroughput().getReadCapacityUnits().longValue();
                totalWriteThroughput += gsi.getProvisionedThroughput().getWriteCapacityUnits().longValue();
            }
        }
        totalReadThroughput += throughput.getReadCapacityUnits().longValue();
        totalWriteThroughput += throughput.getWriteCapacityUnits().longValue();
        if (updatedGSIList != null) {
            for (GlobalSecondaryIndexDescription gsi : updatedGSIList) {
                totalReadThroughput += gsi.getProvisionedThroughput().getReadCapacityUnits().longValue();
                totalWriteThroughput += gsi.getProvisionedThroughput().getWriteCapacityUnits().longValue();
            }
        }
        if (totalReadThroughput > 80000L || totalWriteThroughput > 80000L) {
            throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, LocalDBClientExceptionMessage.TOO_MUCH_THROUGHPUT_ACCOUNT.getMessage());
        }
    }

    private boolean isGSIInAStateToCountItems(GlobalSecondaryIndexDescription description) {
        return IndexStatus.ACTIVE.toString().equals(description.getIndexStatus()) || IndexStatus.CREATING.toString().equals(description.getIndexStatus()) && Boolean.TRUE.equals(description.getBackfilling()) || IndexStatus.UPDATING.toString().equals(description.getIndexStatus()) && Boolean.TRUE.equals(description.getBackfilling());
    }
}

