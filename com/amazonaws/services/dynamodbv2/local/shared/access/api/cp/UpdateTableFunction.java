/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  com.amazonaws.services.dynamodbv2.model.AttributeDefinition
 *  com.amazonaws.services.dynamodbv2.model.BillingMode
 *  com.amazonaws.services.dynamodbv2.model.CreateGlobalSecondaryIndexAction
 *  com.amazonaws.services.dynamodbv2.model.DeleteGlobalSecondaryIndexAction
 *  com.amazonaws.services.dynamodbv2.model.GlobalSecondaryIndexDescription
 *  com.amazonaws.services.dynamodbv2.model.GlobalSecondaryIndexUpdate
 *  com.amazonaws.services.dynamodbv2.model.IndexStatus
 *  com.amazonaws.services.dynamodbv2.model.LocalSecondaryIndex
 *  com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput
 *  com.amazonaws.services.dynamodbv2.model.ProvisionedThroughputDescription
 *  com.amazonaws.services.dynamodbv2.model.StreamSpecification
 *  com.amazonaws.services.dynamodbv2.model.TableDescription
 *  com.amazonaws.services.dynamodbv2.model.UpdateGlobalSecondaryIndexAction
 *  com.amazonaws.services.dynamodbv2.model.UpdateTableRequest
 *  com.amazonaws.services.dynamodbv2.model.UpdateTableResult
 */
package com.amazonaws.services.dynamodbv2.local.shared.access.api.cp;

import com.amazonaws.services.dynamodbv2.exceptions.AWSExceptionFactory;
import com.amazonaws.services.dynamodbv2.exceptions.AmazonServiceExceptionType;
import com.amazonaws.services.dynamodbv2.local.shared.access.LocalDBAccess;
import com.amazonaws.services.dynamodbv2.local.shared.access.LocalDBUtils;
import com.amazonaws.services.dynamodbv2.local.shared.access.TableInfo;
import com.amazonaws.services.dynamodbv2.local.shared.access.api.cp.ControlPlaneFunction;
import com.amazonaws.services.dynamodbv2.local.shared.exceptions.LocalDBClientExceptionMessage;
import com.amazonaws.services.dynamodbv2.model.AttributeDefinition;
import com.amazonaws.services.dynamodbv2.model.BillingMode;
import com.amazonaws.services.dynamodbv2.model.CreateGlobalSecondaryIndexAction;
import com.amazonaws.services.dynamodbv2.model.DeleteGlobalSecondaryIndexAction;
import com.amazonaws.services.dynamodbv2.model.GlobalSecondaryIndexDescription;
import com.amazonaws.services.dynamodbv2.model.GlobalSecondaryIndexUpdate;
import com.amazonaws.services.dynamodbv2.model.IndexStatus;
import com.amazonaws.services.dynamodbv2.model.LocalSecondaryIndex;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughputDescription;
import com.amazonaws.services.dynamodbv2.model.StreamSpecification;
import com.amazonaws.services.dynamodbv2.model.TableDescription;
import com.amazonaws.services.dynamodbv2.model.UpdateGlobalSecondaryIndexAction;
import com.amazonaws.services.dynamodbv2.model.UpdateTableRequest;
import com.amazonaws.services.dynamodbv2.model.UpdateTableResult;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.lang3.StringUtils;

public class UpdateTableFunction
extends ControlPlaneFunction<UpdateTableRequest, UpdateTableResult> {
    public UpdateTableFunction(LocalDBAccess dbAccess) {
        super(dbAccess);
    }

    @Override
    public UpdateTableResult apply(final UpdateTableRequest updateTableRequest) {
        final String tableName = updateTableRequest.getTableName();
        this.validateTableName(tableName);
        final TableInfo info = this.validateTableExists(tableName);
        final StreamSpecification newSpec = updateTableRequest.getStreamSpecification();
        new LocalDBAccess.WriteLockWithTimeout(this.dbAccess.getLockForTable(tableName), 10){

            @Override
            public void criticalSection() {
                boolean changeToPayPerUse;
                boolean changeToProvisioned;
                ProvisionedThroughput updatedThroughput;
                boolean doesRequestUpdateBillingMode;
                ProvisionedThroughput newThroughput = updateTableRequest.getProvisionedThroughput();
                List gsiUpdates = updateTableRequest.getGlobalSecondaryIndexUpdates();
                boolean doesRequestUpdateBaseTableIOPS = newThroughput != null;
                boolean doesRequestHaveOnlineGSIRequests = gsiUpdates != null && !gsiUpdates.isEmpty();
                boolean bl = doesRequestUpdateBillingMode = updateTableRequest.getBillingMode() != null;
                if (!(doesRequestUpdateBaseTableIOPS || doesRequestUpdateBillingMode || doesRequestHaveOnlineGSIRequests || newSpec != null || updateTableRequest.getDeletionProtectionEnabled() != null)) {
                    throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, LocalDBClientExceptionMessage.NOTHING_TO_UPDATE.getMessage());
                }
                boolean deleteProtectionEnabled = false;
                if (updateTableRequest.getDeletionProtectionEnabled() != null) {
                    deleteProtectionEnabled = updateTableRequest.getDeletionProtectionEnabled();
                }
                ArrayList<String> indicesCurrentlyCreatingNotBackfilling = new ArrayList<String>();
                ArrayList<String> indicesCurrentlyCreatingBackfilling = new ArrayList<String>();
                ArrayList<String> indicesCurrentlyUpdating = new ArrayList<String>();
                ArrayList<String> indicesCurrentlyDeleting = new ArrayList<String>();
                if (info.hasGSIs()) {
                    for (GlobalSecondaryIndexDescription existingGSI : info.getGSIDescriptions()) {
                        switch (IndexStatus.fromValue((String)existingGSI.getIndexStatus())) {
                            case CREATING: {
                                if (existingGSI.isBackfilling().booleanValue()) {
                                    indicesCurrentlyCreatingBackfilling.add(existingGSI.getIndexName());
                                    break;
                                }
                                indicesCurrentlyCreatingNotBackfilling.add(existingGSI.getIndexName());
                                break;
                            }
                            case UPDATING: {
                                indicesCurrentlyUpdating.add(existingGSI.getIndexName());
                                break;
                            }
                            case DELETING: {
                                indicesCurrentlyDeleting.add(existingGSI.getIndexName());
                                break;
                            }
                        }
                    }
                }
                if (doesRequestUpdateBaseTableIOPS) {
                    String indexName;
                    Iterator<Object> iterator2 = indicesCurrentlyCreatingNotBackfilling.iterator();
                    if (iterator2.hasNext()) {
                        indexName = (String)iterator2.next();
                        throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.RESOURCE_IN_USE_EXCEPTION, String.format(LocalDBClientExceptionMessage.CANT_UPDATE_TABLE_WHEN_INDEX_IS_CREATING_BACKFILLING_FALSE.getMessage(), tableName, indexName));
                    }
                    iterator2 = indicesCurrentlyDeleting.iterator();
                    if (iterator2.hasNext()) {
                        indexName = (String)iterator2.next();
                        throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.RESOURCE_IN_USE_EXCEPTION, String.format(LocalDBClientExceptionMessage.CANT_UPDATE_TABLE_WHEN_INDEX_IS_DELETING.getMessage(), tableName, indexName));
                    }
                }
                List<AttributeDefinition> updatedAllAttributesList = info.getAttributeDefinitions();
                ArrayList<GlobalSecondaryIndexDescription> updatedGSIDescList = info.hasGSIs() ? new ArrayList<GlobalSecondaryIndexDescription>(info.getGSIDescriptions()) : new ArrayList();
                String billingModeString = updateTableRequest.getBillingMode();
                BillingMode calculatedBillingMode = StringUtils.isNotBlank(billingModeString) ? BillingMode.fromValue((String)billingModeString) : info.getBillingMode();
                if (doesRequestHaveOnlineGSIRequests) {
                    boolean doesRequestHaveDeleteGSI;
                    int numberOfCreateGSIRequests = 0;
                    int numberOfUpdateGSIRequests = 0;
                    int numberOfDeleteGSIRequests = 0;
                    for (Object update : gsiUpdates) {
                        if (update == null) continue;
                        if (update.getCreate() != null) {
                            ++numberOfCreateGSIRequests;
                        }
                        if (update.getUpdate() != null) {
                            ++numberOfUpdateGSIRequests;
                        }
                        if (update.getDelete() == null) continue;
                        ++numberOfDeleteGSIRequests;
                    }
                    if (UpdateTableFunction.this.dbAccess.numberOfSubscriberWideInflightOnlineCreateIndexesOperations() + numberOfCreateGSIRequests > 5) {
                        throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.LIMIT_EXCEEDED_EXCEPTION, LocalDBClientExceptionMessage.SUBSCRIBER_WIDE_MAX_INFLIGHT_CREATE_ONLINE_GSI_LIMIT_REACHED.getMessage());
                    }
                    if (UpdateTableFunction.this.areThereMoreThanOneUpdateToSameIndex(gsiUpdates)) {
                        throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, LocalDBClientExceptionMessage.ONLY_ONE_GSI_UPDATE_PER_REQUEST_FOR_AN_INDEX.getMessage());
                    }
                    UpdateTableFunction.this.throwResourceInUseIfIndexIsInFlight(gsiUpdates, indicesCurrentlyCreatingNotBackfilling, indicesCurrentlyCreatingBackfilling, indicesCurrentlyUpdating, indicesCurrentlyDeleting, info);
                    UpdateTableFunction.this.throwLimitExceededExceptionIfThereAreTooManyIndexUpdates(numberOfCreateGSIRequests, numberOfDeleteGSIRequests, indicesCurrentlyCreatingNotBackfilling, indicesCurrentlyCreatingBackfilling, indicesCurrentlyDeleting);
                    ArrayList<String> indicesToDelete = new ArrayList<String>();
                    for (GlobalSecondaryIndexUpdate gsiUpdate : gsiUpdates) {
                        GlobalSecondaryIndexDescription gsi;
                        if (gsiUpdate == null) {
                            throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, LocalDBClientExceptionMessage.NULL_GSI_UPDATE.getMessage());
                        }
                        CreateGlobalSecondaryIndexAction createAction = gsiUpdate.getCreate();
                        UpdateGlobalSecondaryIndexAction updateAction = gsiUpdate.getUpdate();
                        DeleteGlobalSecondaryIndexAction deleteAction = gsiUpdate.getDelete();
                        if (updateAction == null && createAction == null && deleteAction == null) {
                            throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, LocalDBClientExceptionMessage.NULL_GSI_UPDATE_ACTION.getMessage());
                        }
                        if (createAction != null) {
                            UpdateTableFunction.this.validateCreateGSI(info, createAction);
                            GlobalSecondaryIndexDescription gsiDesc = new GlobalSecondaryIndexDescription().withIndexName(createAction.getIndexName()).withIndexStatus(IndexStatus.CREATING).withBackfilling(Boolean.valueOf(false)).withKeySchema((Collection)createAction.getKeySchema()).withProjection(createAction.getProjection());
                            if (BillingMode.PAY_PER_REQUEST == calculatedBillingMode) {
                                gsiDesc.withProvisionedThroughput(new ProvisionedThroughputDescription().withReadCapacityUnits(Long.valueOf(0L)).withWriteCapacityUnits(Long.valueOf(0L)));
                            } else {
                                gsiDesc.withProvisionedThroughput(new ProvisionedThroughputDescription().withReadCapacityUnits(createAction.getProvisionedThroughput().getReadCapacityUnits()).withWriteCapacityUnits(createAction.getProvisionedThroughput().getWriteCapacityUnits()));
                            }
                            updatedGSIDescList.add(gsiDesc);
                        }
                        if (updateAction != null) {
                            UpdateTableFunction.this.validateUpdateGSI(info, updateAction);
                            gsi = info.getGSIDescWithName(updateAction.getIndexName());
                            gsi.setProvisionedThroughput(new ProvisionedThroughputDescription().withReadCapacityUnits(updateAction.getProvisionedThroughput().getReadCapacityUnits()).withWriteCapacityUnits(updateAction.getProvisionedThroughput().getWriteCapacityUnits()).withLastIncreaseDateTime(gsi.getProvisionedThroughput().getLastIncreaseDateTime()).withLastDecreaseDateTime(gsi.getProvisionedThroughput().getLastDecreaseDateTime()).withNumberOfDecreasesToday(gsi.getProvisionedThroughput().getNumberOfDecreasesToday()));
                        }
                        if (deleteAction == null) continue;
                        UpdateTableFunction.this.validateDeleteGSI(info, deleteAction);
                        indicesToDelete.add(deleteAction.getIndexName());
                        gsi = info.getGSIDescWithName(deleteAction.getIndexName());
                        gsi.setIndexStatus(IndexStatus.DELETING);
                        gsi.setBackfilling(null);
                    }
                    List attrDefnsFromNewGSIs = updateTableRequest.getAttributeDefinitions();
                    if (attrDefnsFromNewGSIs != null && !attrDefnsFromNewGSIs.isEmpty()) {
                        UpdateTableFunction.this.validateNewAttributes(attrDefnsFromNewGSIs, info);
                    }
                    boolean doesRequestHaveCreateGSI = numberOfCreateGSIRequests > 0;
                    boolean doesRequestHaveUpdateGSI = numberOfUpdateGSIRequests > 0;
                    boolean bl2 = doesRequestHaveDeleteGSI = numberOfDeleteGSIRequests > 0;
                    if (doesRequestHaveCreateGSI && (attrDefnsFromNewGSIs == null || attrDefnsFromNewGSIs.isEmpty())) {
                        throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, LocalDBClientExceptionMessage.NO_ATTRIBUTE_SCHEMA.getMessage());
                    }
                    updatedAllAttributesList = UpdateTableFunction.this.updateAttributesList(info, attrDefnsFromNewGSIs, indicesToDelete);
                    UpdateTableFunction.this.validateAttributeDefinitions(updatedAllAttributesList);
                    if (doesRequestHaveCreateGSI || doesRequestHaveUpdateGSI || doesRequestHaveDeleteGSI) {
                        if (updatedGSIDescList.size() > 20) {
                            throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.LIMIT_EXCEEDED_EXCEPTION, LocalDBClientExceptionMessage.TOO_MANY_GSI_LIMIT_EXCEEDED_EXCEPTION.getMessage());
                        }
                        UpdateTableFunction.this.validateGSISchemas(updatedGSIDescList, LocalDBUtils.getBaseTableHashKeyDefinition(info), LocalDBUtils.getBaseTableRangeKeyDefinition(info), updatedAllAttributesList, info.getLSINames(), info.getNumberOfLSIProjectedAttributes(), calculatedBillingMode);
                    }
                }
                ProvisionedThroughput curThroughput = info.getThroughput();
                ProvisionedThroughput provisionedThroughput = updatedThroughput = doesRequestUpdateBaseTableIOPS ? newThroughput : curThroughput;
                if (BillingMode.PAY_PER_REQUEST.equals((Object)calculatedBillingMode)) {
                    updatedThroughput = ControlPlaneFunction.ZERO_PROVISIONED_THROUGHPUT;
                }
                boolean bl3 = changeToProvisioned = info.getBillingMode().equals((Object)BillingMode.PAY_PER_REQUEST) && calculatedBillingMode.equals((Object)BillingMode.PROVISIONED);
                if (changeToProvisioned && newThroughput == null) {
                    updatedThroughput = null;
                    throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, LocalDBClientExceptionMessage.INVALID_THROUGHPUT_NULL.getMessage());
                }
                boolean bl4 = changeToPayPerUse = info.getBillingMode().equals((Object)BillingMode.PROVISIONED) && calculatedBillingMode.equals((Object)BillingMode.PAY_PER_REQUEST);
                if (doesRequestUpdateBaseTableIOPS) {
                    UpdateTableFunction.this.validateProvisionedThroughputIncrease(newThroughput, curThroughput);
                }
                if (updatedThroughput != null) {
                    UpdateTableFunction.this.validateProvisionedThroughputWithGSIs(tableName, updatedThroughput, updatedGSIDescList);
                }
                if (updateTableRequest.getProvisionedThroughput() != null && updateTableRequest.getStreamSpecification() != null) {
                    throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, LocalDBClientExceptionMessage.SIMULTANEOUS_PT_AND_STREAM_SPEC_UPDATE_TABLE.getMessage());
                }
                UpdateTableFunction.this.validateStreamSpecification(newSpec, info.getStreamSpecification(), false);
                UpdateTableFunction.this.dbAccess.updateTable(tableName, updatedThroughput, calculatedBillingMode, changeToPayPerUse ? System.currentTimeMillis() : info.getLastUpdateToPayPerRequestDateTime(), updatedAllAttributesList, updatedGSIDescList, deleteProtectionEnabled, newSpec);
            }
        }.execute();
        TableDescription description = this.getTableDescriptionHelper(tableName);
        description = this.fillDescriptionHelper(description);
        return new UpdateTableResult().withTableDescription(description);
    }

    private boolean areThereMoreThanOneUpdateToSameIndex(List<GlobalSecondaryIndexUpdate> gsiUpdates) {
        HashSet<String> indexNamesFromUpdate = new HashSet<String>();
        for (GlobalSecondaryIndexUpdate update : gsiUpdates) {
            String indexName = null;
            if (update.getCreate() != null) {
                indexName = update.getCreate().getIndexName();
            } else if (update.getUpdate() != null) {
                indexName = update.getUpdate().getIndexName();
            } else if (update.getDelete() != null) {
                indexName = update.getDelete().getIndexName();
            }
            if (indexName == null) continue;
            if (indexNamesFromUpdate.contains(indexName)) {
                return true;
            }
            indexNamesFromUpdate.add(indexName);
        }
        return false;
    }

    private void throwResourceInUseIfIndexIsInFlight(List<GlobalSecondaryIndexUpdate> gsiUpdates, List<String> indicesCurrentlyCreatingNotBackfilling, List<String> indicesCurrentlyCreatingBackfilling, List<String> indicesCurrentlyUpdating, List<String> indicesCurrentlyDeleting, TableInfo info) {
        for (GlobalSecondaryIndexUpdate update : gsiUpdates) {
            if (update.getCreate() != null) {
                this.checkForResourceInUse(update.getCreate().getIndexName(), "Create", indicesCurrentlyCreatingNotBackfilling, indicesCurrentlyCreatingBackfilling, indicesCurrentlyUpdating, indicesCurrentlyDeleting, info);
            }
            if (update.getUpdate() != null) {
                this.checkForResourceInUse(update.getUpdate().getIndexName(), "Update", indicesCurrentlyCreatingNotBackfilling, indicesCurrentlyCreatingBackfilling, indicesCurrentlyUpdating, indicesCurrentlyDeleting, info);
            }
            if (update.getDelete() == null) continue;
            this.checkForResourceInUse(update.getDelete().getIndexName(), "Delete", indicesCurrentlyCreatingNotBackfilling, indicesCurrentlyCreatingBackfilling, indicesCurrentlyUpdating, indicesCurrentlyDeleting, info);
        }
    }

    private void checkForResourceInUse(String indexName, String updateType, List<String> indicesCurrentlyCreatingNotBackfilling, List<String> indicesCurrentlyCreatingBackfilling, List<String> indicesCurrentlyUpdating, List<String> indicesCurrentlyDeleting, TableInfo tableInfo) {
        if (indicesCurrentlyDeleting.contains(indexName)) {
            throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.RESOURCE_IN_USE_EXCEPTION, LocalDBClientExceptionMessage.INDEX_IS_BEING_DELETED.getMessage());
        }
        if (indicesCurrentlyUpdating.contains(indexName)) {
            throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.RESOURCE_IN_USE_EXCEPTION, LocalDBClientExceptionMessage.INDEX_IS_BEING_UPDATED.getMessage());
        }
        if (indicesCurrentlyCreatingNotBackfilling.contains(indexName) || indicesCurrentlyCreatingBackfilling.contains(indexName)) {
            if (updateType.equals("Create")) {
                throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.RESOURCE_IN_USE_EXCEPTION, LocalDBClientExceptionMessage.INDEX_IS_BEING_CREATED.getMessage());
            }
            if (updateType.equals("Update")) {
                GlobalSecondaryIndexDescription desc = tableInfo.getGSIDescWithName(indexName);
                if (desc.getBackfilling() == null || !desc.getBackfilling().booleanValue()) {
                    throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.RESOURCE_IN_USE_EXCEPTION, LocalDBClientExceptionMessage.INDEX_IS_BEING_CREATED_BUT_NOT_BACKFILLING.getMessage());
                }
            } else if (updateType.equals("Delete")) {
                throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.RESOURCE_IN_USE_EXCEPTION, LocalDBClientExceptionMessage.INDEX_IS_BEING_CREATED.getMessage());
            }
        }
    }

    private void throwLimitExceededExceptionIfThereAreTooManyIndexUpdates(int numberOfCreateGSIRequests, int numberOfDeleteGSIRequests, List<String> indicesCurrentlyCreatingNotBackfilling, List<String> indicesCurrentlyCreatingBackfilling, List<String> indicesCurrentlyDeleting) {
        if (numberOfCreateGSIRequests + numberOfDeleteGSIRequests + indicesCurrentlyCreatingNotBackfilling.size() + indicesCurrentlyCreatingBackfilling.size() + indicesCurrentlyDeleting.size() > 1) {
            throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.LIMIT_EXCEEDED_EXCEPTION, LocalDBClientExceptionMessage.INFLIGHT_INDEX_LIMIT_EXCEEDED_EXCEPTION.getMessage());
        }
    }

    private void validateCreateGSI(TableInfo info, CreateGlobalSecondaryIndexAction createAction) {
        String indexName = createAction.getIndexName();
        if (StringUtils.isEmpty(indexName)) {
            throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, LocalDBClientExceptionMessage.INVALID_GSI_NAME.getMessage());
        }
        if (info.isLSIIndex(indexName)) {
            throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, LocalDBClientExceptionMessage.CANNOT_CREATE_GSI_WITH_SAME_NAME_AS_ANOTHER_INDEX.getMessage());
        }
        if (info.isGSIIndex(indexName)) {
            GlobalSecondaryIndexDescription anotherGSIWithSameName = info.getGSIDescWithName(createAction.getIndexName());
            switch (IndexStatus.fromValue((String)anotherGSIWithSameName.getIndexStatus())) {
                case ACTIVE: {
                    throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, LocalDBClientExceptionMessage.CANNOT_CREATE_GSI_WITH_SAME_NAME_AS_ANOTHER_INDEX.getMessage());
                }
                case CREATING: 
                case UPDATING: 
                case DELETING: {
                    throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, LocalDBClientExceptionMessage.ONLY_ONE_GSI_UPDATE_PER_REQUEST_FOR_AN_INDEX.getMessage());
                }
            }
        }
    }

    private void validateUpdateGSI(TableInfo info, UpdateGlobalSecondaryIndexAction updateAction) {
        String indexName = updateAction.getIndexName();
        if (StringUtils.isEmpty(indexName)) {
            throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, LocalDBClientExceptionMessage.INVALID_GSI_NAME.getMessage());
        }
        if (!info.isGSIIndex(indexName)) {
            throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, LocalDBClientExceptionMessage.INVALID_GSI_NAME.getMessage());
        }
        GlobalSecondaryIndexDescription oldGSIDesc = info.getGSIDescWithName(updateAction.getIndexName());
        switch (IndexStatus.fromValue((String)oldGSIDesc.getIndexStatus())) {
            case ACTIVE: {
                break;
            }
            case CREATING: {
                if (oldGSIDesc.getBackfilling().booleanValue()) break;
                throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.RESOURCE_IN_USE_EXCEPTION, LocalDBClientExceptionMessage.ONLY_ONE_GSI_UPDATE_PER_REQUEST_FOR_AN_INDEX.getMessage());
            }
            case UPDATING: {
                break;
            }
            case DELETING: {
                throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, LocalDBClientExceptionMessage.INDEX_IS_BEING_DELETED.getMessage());
            }
        }
        ProvisionedThroughput newIndexThroughput = updateAction.getProvisionedThroughput();
        ProvisionedThroughput currentIndexThroughput = LocalDBUtils.getPTfromPTDescription(info.getGSIDescWithName(indexName).getProvisionedThroughput());
        this.validateGSIProvisionedThroughput(newIndexThroughput);
        this.validateProvisionedThroughputIncrease(newIndexThroughput, currentIndexThroughput);
    }

    private void validateDeleteGSI(TableInfo info, DeleteGlobalSecondaryIndexAction deleteAction) {
        String indexName = deleteAction.getIndexName();
        if (StringUtils.isEmpty(indexName)) {
            throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, LocalDBClientExceptionMessage.INVALID_GSI_NAME.getMessage());
        }
        if (info.isLSIIndex(indexName)) {
            throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, LocalDBClientExceptionMessage.CANNOT_DELETE_LSI_VIA_DELETE_GSI_API.getMessage());
        }
        if (info.isGSIIndex(indexName)) {
            GlobalSecondaryIndexDescription oldGSIDesc = info.getGSIDescWithName(indexName);
            switch (IndexStatus.fromValue((String)oldGSIDesc.getIndexStatus())) {
                case CREATING: {
                    if (oldGSIDesc.getBackfilling().booleanValue()) break;
                    throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.RESOURCE_IN_USE_EXCEPTION, LocalDBClientExceptionMessage.ONLY_ONE_GSI_UPDATE_PER_REQUEST_FOR_AN_INDEX.getMessage());
                }
                case UPDATING: {
                    throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.RESOURCE_IN_USE_EXCEPTION, LocalDBClientExceptionMessage.ONLY_ONE_GSI_UPDATE_PER_REQUEST_FOR_AN_INDEX.getMessage());
                }
                case DELETING: {
                    throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.RESOURCE_IN_USE_EXCEPTION, LocalDBClientExceptionMessage.INDEX_IS_BEING_DELETED.getMessage());
                }
            }
        } else {
            throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.RESOURCE_NOT_FOUND_EXCEPTION, LocalDBClientExceptionMessage.REQUESTED_RESOURCE_NOT_FOUND.getMessage());
        }
    }

    private void validateNewAttributes(List<AttributeDefinition> newAttributes, TableInfo info) {
        HashMap<String, AttributeDefinition> existingDataTypes = new HashMap<String, AttributeDefinition>();
        List<AttributeDefinition> curAttributes = info.getAttributeDefinitions();
        if (curAttributes != null) {
            for (AttributeDefinition curAttr : curAttributes) {
                existingDataTypes.put(curAttr.getAttributeName(), curAttr);
            }
        }
        for (AttributeDefinition newAttr : newAttributes) {
            if (!existingDataTypes.containsKey(newAttr.getAttributeName())) continue;
            AttributeDefinition matchingAttrDef = (AttributeDefinition)existingDataTypes.get(newAttr.getAttributeName());
            if (!newAttr.getAttributeType().equals(matchingAttrDef.getAttributeType())) {
                throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, LocalDBClientExceptionMessage.INVALID_ATTRIBUTE_REDEFINITION.getMessage());
            }
            LocalDBUtils.getDataTypeOfAttributeDefinition(newAttr);
        }
    }

    private List<AttributeDefinition> updateAttributesList(TableInfo tableInfo, List<AttributeDefinition> attrDefnsFromNewGSIs, List<String> indicesToBeDeleted) {
        AttributeDefinition rangeKeyDef;
        AttributeDefinition hashKeyDef;
        HashMap<String, AttributeDefinition> newAttrNameToDefnMap = new HashMap<String, AttributeDefinition>();
        newAttrNameToDefnMap.put(tableInfo.getHashKey().getAttributeName(), tableInfo.getHashKey());
        if (tableInfo.getRangeKey() != null) {
            newAttrNameToDefnMap.put(tableInfo.getRangeKey().getAttributeName(), tableInfo.getRangeKey());
        }
        if (tableInfo.getLSIIndexes() != null) {
            for (LocalSecondaryIndex lsi : tableInfo.getLSIIndexes()) {
                hashKeyDef = LocalDBUtils.getLSIHashKeyDefinition(lsi, tableInfo);
                newAttrNameToDefnMap.put(hashKeyDef.getAttributeName(), tableInfo.getHashKey());
                rangeKeyDef = LocalDBUtils.getLSIRangeKeyDefinition(lsi, tableInfo);
                if (rangeKeyDef == null) continue;
                newAttrNameToDefnMap.put(rangeKeyDef.getAttributeName(), rangeKeyDef);
            }
        }
        if (tableInfo.hasGSIs()) {
            for (GlobalSecondaryIndexDescription gsi : tableInfo.getGSIDescriptions()) {
                if (this.isThisIndexGoingToBeDeletedOrIsBeingDeleted(indicesToBeDeleted, gsi)) continue;
                hashKeyDef = LocalDBUtils.getGSIHashKeyDefinition(gsi, tableInfo);
                newAttrNameToDefnMap.put(hashKeyDef.getAttributeName(), hashKeyDef);
                rangeKeyDef = LocalDBUtils.getGSIRangeKeyDefinition(gsi, tableInfo);
                if (rangeKeyDef == null) continue;
                newAttrNameToDefnMap.put(rangeKeyDef.getAttributeName(), rangeKeyDef);
            }
        }
        if (attrDefnsFromNewGSIs != null) {
            for (AttributeDefinition newAttrDef : attrDefnsFromNewGSIs) {
                newAttrNameToDefnMap.put(newAttrDef.getAttributeName(), newAttrDef);
            }
        }
        return new ArrayList<AttributeDefinition>(newAttrNameToDefnMap.values());
    }

    private boolean isThisIndexGoingToBeDeletedOrIsBeingDeleted(List<String> indicesToBeDeleted, GlobalSecondaryIndexDescription gsi) {
        if (indicesToBeDeleted.contains(gsi.getIndexName())) {
            return true;
        }
        return IndexStatus.DELETING.toString().equals(gsi.getIndexStatus());
    }
}

