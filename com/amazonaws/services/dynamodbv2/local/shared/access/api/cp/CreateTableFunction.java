/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  com.amazonaws.services.dynamodbv2.model.AttributeDefinition
 *  com.amazonaws.services.dynamodbv2.model.BillingMode
 *  com.amazonaws.services.dynamodbv2.model.CreateTableRequest
 *  com.amazonaws.services.dynamodbv2.model.CreateTableResult
 *  com.amazonaws.services.dynamodbv2.model.GlobalSecondaryIndex
 *  com.amazonaws.services.dynamodbv2.model.GlobalSecondaryIndexDescription
 *  com.amazonaws.services.dynamodbv2.model.KeySchemaElement
 *  com.amazonaws.services.dynamodbv2.model.KeyType
 *  com.amazonaws.services.dynamodbv2.model.LocalSecondaryIndex
 *  com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput
 *  com.amazonaws.services.dynamodbv2.model.StreamSpecification
 *  com.amazonaws.services.dynamodbv2.model.TableDescription
 *  com.amazonaws.util.CollectionUtils
 */
package com.amazonaws.services.dynamodbv2.local.shared.access.api.cp;

import com.amazonaws.services.dynamodbv2.exceptions.AWSExceptionFactory;
import com.amazonaws.services.dynamodbv2.exceptions.AmazonServiceExceptionType;
import com.amazonaws.services.dynamodbv2.local.shared.access.LocalDBAccess;
import com.amazonaws.services.dynamodbv2.local.shared.access.LocalDBUtils;
import com.amazonaws.services.dynamodbv2.local.shared.access.api.cp.ControlPlaneFunction;
import com.amazonaws.services.dynamodbv2.local.shared.exceptions.LocalDBClientExceptionMessage;
import com.amazonaws.services.dynamodbv2.model.AttributeDefinition;
import com.amazonaws.services.dynamodbv2.model.BillingMode;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.CreateTableResult;
import com.amazonaws.services.dynamodbv2.model.GlobalSecondaryIndex;
import com.amazonaws.services.dynamodbv2.model.GlobalSecondaryIndexDescription;
import com.amazonaws.services.dynamodbv2.model.KeySchemaElement;
import com.amazonaws.services.dynamodbv2.model.KeyType;
import com.amazonaws.services.dynamodbv2.model.LocalSecondaryIndex;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.model.StreamSpecification;
import com.amazonaws.services.dynamodbv2.model.TableDescription;
import com.amazonaws.util.CollectionUtils;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;

public class CreateTableFunction
extends ControlPlaneFunction<CreateTableRequest, CreateTableResult> {
    public CreateTableFunction(LocalDBAccess dbAccess) {
        super(dbAccess);
    }

    @Override
    public CreateTableResult apply(CreateTableRequest createTableRequest) {
        ProvisionedThroughput throughput;
        int maxSize;
        String billingModeString;
        boolean isTheRequestCreatingGSIs;
        AttributeDefinition rangeKey;
        if (createTableRequest == null) {
            throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.INVALID_PARAMETER_VALUE, "createTableRequest was null");
        }
        final String tableName = createTableRequest.getTableName();
        this.validateTableName(tableName);
        final Boolean deletionProtectionEnabled = createTableRequest.getDeletionProtectionEnabled();
        List keySchema = createTableRequest.getKeySchema();
        this.validateKeySchema(keySchema);
        int numKeysOnBaseTable = keySchema.size();
        boolean isHashAndRangeKey = numKeysOnBaseTable == 2;
        final List allAttributes = createTableRequest.getAttributeDefinitions();
        this.validateAttributeDefinitions(allAttributes);
        final AttributeDefinition hashKey = LocalDBUtils.findAttributeDefinition((KeySchemaElement)keySchema.get(0), allAttributes);
        if (hashKey == null) {
            throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, LocalDBClientExceptionMessage.NON_SPECIFIED_HASH_KEY.getMessage());
        }
        AttributeDefinition attributeDefinition = rangeKey = isHashAndRangeKey ? LocalDBUtils.findAttributeDefinition((KeySchemaElement)keySchema.get(1), allAttributes) : null;
        if (isHashAndRangeKey && rangeKey == null) {
            throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, LocalDBClientExceptionMessage.NON_SPECIFIED_RANGE_KEY.getMessage());
        }
        final List lsiIndexes = createTableRequest.getLocalSecondaryIndexes();
        if (!isHashAndRangeKey && lsiIndexes != null && lsiIndexes.size() > 0) {
            throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, LocalDBClientExceptionMessage.NO_LSI_ALLOWED.getMessage());
        }
        HashSet<String> lsiNames = new HashSet<String>();
        ArrayList<String> lsiProjAttr = new ArrayList<String>();
        int numLSIKeys = this.validateLSISchema(lsiIndexes, hashKey.getAttributeName(), allAttributes, rangeKey, lsiNames, lsiProjAttr);
        final List gsiIndexes = createTableRequest.getGlobalSecondaryIndexes();
        boolean bl = isTheRequestCreatingGSIs = gsiIndexes != null;
        if (isTheRequestCreatingGSIs) {
            if (gsiIndexes.isEmpty()) {
                throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, LocalDBClientExceptionMessage.EMPTY_GSI_LIST.getMessage());
            }
            if (gsiIndexes.size() > 20) {
                throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, LocalDBClientExceptionMessage.TOO_MANY_GSI_VALIDATION_EXCEPTION.getMessage());
            }
        }
        final BillingMode billingMode = StringUtils.isNotBlank(billingModeString = createTableRequest.getBillingMode()) && BillingMode.PAY_PER_REQUEST.equals((Object)BillingMode.fromValue((String)billingModeString)) ? BillingMode.PAY_PER_REQUEST : BillingMode.PROVISIONED;
        List<GlobalSecondaryIndexDescription> gsiDescList = LocalDBUtils.getGsiDescListFrom(gsiIndexes);
        int numGSIKeys = this.validateGSISchemas(gsiDescList, hashKey, rangeKey, allAttributes, new ArrayList<String>(lsiNames), lsiProjAttr.size(), billingMode);
        int n = maxSize = isHashAndRangeKey ? 2 + numLSIKeys + numGSIKeys : 1 + numGSIKeys;
        if (allAttributes.size() > maxSize) {
            throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, LocalDBClientExceptionMessage.TOO_MANY_ATTRIBUTES.getMessage());
        }
        final ArrayList modifiedGsiIndexes = new ArrayList();
        if (BillingMode.PROVISIONED.equals((Object)billingMode)) {
            throughput = createTableRequest.getProvisionedThroughput();
            this.validateProvisionedThroughputIncrease(throughput, null);
            this.validateProvisionedThroughputWithGSIs(tableName, throughput, gsiDescList);
        } else {
            throughput = ZERO_PROVISIONED_THROUGHPUT;
            if (!CollectionUtils.isNullOrEmpty((Collection)gsiIndexes)) {
                modifiedGsiIndexes.addAll(gsiIndexes.stream().map(GlobalSecondaryIndex::clone).map(globalSecondaryIndex -> globalSecondaryIndex.withProvisionedThroughput(ZERO_PROVISIONED_THROUGHPUT)).collect(Collectors.toList()));
            }
        }
        final StreamSpecification spec = createTableRequest.getStreamSpecification();
        this.validateStreamSpecification(spec, null, true);
        new LocalDBAccess.WriteLockWithTimeout(this.dbAccess.getLockForTable(tableName), 10){

            @Override
            public void criticalSection() {
                CreateTableFunction.this.validateTableNotExists(tableName);
                CreateTableFunction.this.dbAccess.createTable(tableName, hashKey, rangeKey, allAttributes, lsiIndexes, modifiedGsiIndexes.isEmpty() ? gsiIndexes : modifiedGsiIndexes, throughput, billingMode, deletionProtectionEnabled, spec);
            }
        }.execute();
        TableDescription newTableDesc = this.getTableDescriptionHelper(tableName);
        newTableDesc.setItemCount(Long.valueOf(0L));
        newTableDesc.setTableSizeBytes(Long.valueOf(0L));
        return new CreateTableResult().withTableDescription(newTableDesc);
    }

    private int validateLSISchema(List<LocalSecondaryIndex> lsiList, String hashKeyName, List<AttributeDefinition> allAttributes, AttributeDefinition rangeKeyDef, Set<String> lsiNames, List<String> projAttributes) {
        if (CollectionUtils.isNullOrEmpty(lsiList)) {
            return 0;
        }
        if (lsiList.isEmpty()) {
            throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, LocalDBClientExceptionMessage.EMPTY_LSI_LIST.getMessage());
        }
        if (lsiList.size() > 5) {
            throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, LocalDBClientExceptionMessage.TOO_MANY_LSI.getMessage());
        }
        HashSet<AttributeDefinition> lsiRangeKeys = new HashSet<AttributeDefinition>();
        int totalProjectedAttrs = 0;
        for (LocalSecondaryIndex lsi : lsiList) {
            String lsiName = lsi.getIndexName();
            this.validateTableName(lsiName);
            if (lsiNames.contains(lsiName)) {
                throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, LocalDBClientExceptionMessage.SAME_NAME_LSI.getMessage());
            }
            if ((long)(totalProjectedAttrs += this.validateProjection(lsi.getProjection(), projAttributes)) > 100L) {
                throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, LocalDBClientExceptionMessage.TOO_MANY_PROJECTED.getMessage());
            }
            lsiNames.add(lsiName);
            List lsiSchema = lsi.getKeySchema();
            this.validateKeySchema(lsiSchema);
            if (lsiSchema.size() < 2) {
                throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, LocalDBClientExceptionMessage.INVALID_LSI_NO_RANGE.getMessage());
            }
            KeySchemaElement lsiHashKey = (KeySchemaElement)lsiSchema.get(0);
            if (lsiHashKey == null || !lsiHashKey.getAttributeName().equals(hashKeyName) || !lsiHashKey.getKeyType().equals(KeyType.HASH.toString())) {
                throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, LocalDBClientExceptionMessage.INVALID_LSI.getMessage());
            }
            KeySchemaElement lsiRangeKey = (KeySchemaElement)lsiSchema.get(1);
            if (lsiRangeKey == null || !lsiRangeKey.getKeyType().equals(KeyType.RANGE.toString())) {
                throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, LocalDBClientExceptionMessage.INVALID_LSI_NO_RANGE.getMessage());
            }
            AttributeDefinition lsiRangeKeyDef = LocalDBUtils.findAttributeDefinition(lsiRangeKey, allAttributes);
            if (lsiRangeKeyDef == null) {
                throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, LocalDBClientExceptionMessage.NON_SPECIFIED_LSI_RANGE_KEY.getMessage());
            }
            if (lsiRangeKeyDef.equals((Object)rangeKeyDef)) continue;
            lsiRangeKeys.add(lsiRangeKeyDef);
        }
        return lsiRangeKeys.size();
    }

    private void validateTableNotExists(String tableName) {
        if (this.dbAccess.getTableInfo(tableName) != null) {
            throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.RESOURCE_IN_USE_EXCEPTION, LocalDBClientExceptionMessage.TABLE_ALREADY_EXISTS.getMessage());
        }
    }
}

