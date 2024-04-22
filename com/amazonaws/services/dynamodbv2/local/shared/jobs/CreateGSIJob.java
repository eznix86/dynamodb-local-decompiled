/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  com.amazonaws.services.dynamodbv2.model.GlobalSecondaryIndexDescription
 *  com.amazonaws.services.dynamodbv2.model.IndexStatus
 */
package com.amazonaws.services.dynamodbv2.local.shared.jobs;

import com.amazonaws.services.dynamodbv2.local.shared.access.LocalDBAccess;
import com.amazonaws.services.dynamodbv2.local.shared.access.LocalDBUtils;
import com.amazonaws.services.dynamodbv2.local.shared.access.TableInfo;
import com.amazonaws.services.dynamodbv2.local.shared.jobs.JobsRegister;
import com.amazonaws.services.dynamodbv2.local.shared.jobs.NamedJob;
import com.amazonaws.services.dynamodbv2.model.GlobalSecondaryIndexDescription;
import com.amazonaws.services.dynamodbv2.model.IndexStatus;
import java.util.List;

public class CreateGSIJob
extends NamedJob {
    private final LocalDBAccess dbAccess;
    private final String tableName;
    private final String indexName;

    public CreateGSIJob(String tableName, String indexName, LocalDBAccess dbAccess, JobsRegister jobs) {
        super(jobs);
        this.dbAccess = dbAccess;
        this.tableName = tableName;
        this.indexName = indexName;
    }

    public static String creatingGSIThreadName(String tableName, String indexName) {
        return LocalDBUtils.getGsiThreadName(tableName, indexName, IndexStatus.CREATING);
    }

    @Override
    protected void doJob() {
        if (this.jobs.shouldDelayTransientStatuses()) {
            this.sleepFor(LocalDBUtils.LONG_DELAY_TO_HOLD_TRANSIENT_STATUSES);
        }
        new LocalDBAccess.WriteLockWithTimeout(this.dbAccess.getLockForTable(this.tableName), 10){

            @Override
            public void criticalSection() {
                CreateGSIJob.this.updateGSIState(CreateGSIJob.this.tableName, CreateGSIJob.this.indexName, IndexStatus.CREATING, true);
                CreateGSIJob.this.dbAccess.createGSIColumns(CreateGSIJob.this.tableName, CreateGSIJob.this.indexName);
                CreateGSIJob.this.dbAccess.backfillGSI(CreateGSIJob.this.tableName, CreateGSIJob.this.indexName);
            }
        }.execute();
        if (this.jobs.shouldDelayTransientStatuses()) {
            this.sleepFor(LocalDBUtils.LONG_DELAY_TO_HOLD_TRANSIENT_STATUSES);
        }
        new LocalDBAccess.WriteLockWithTimeout(this.dbAccess.getLockForTable(this.tableName), 10){

            @Override
            public void criticalSection() {
                CreateGSIJob.this.updateGSIState(CreateGSIJob.this.tableName, CreateGSIJob.this.indexName, IndexStatus.ACTIVE, null);
            }
        }.execute();
    }

    private void updateGSIState(String tableName, String indexName, IndexStatus status, Boolean isBackfilling) {
        TableInfo tableInfo = this.dbAccess.getTableInfo(tableName);
        this.dbAccess.updateTable(tableName, tableInfo.getThroughput(), tableInfo.getBillingMode(), tableInfo.getLastUpdateToPayPerRequestDateTime(), tableInfo.getAttributeDefinitions(), this.updateGSIStateInThisList(tableInfo.getGSIDescriptions(), indexName, status, isBackfilling), tableInfo.getDeleteProtectionEnabled(), null);
    }

    private List<GlobalSecondaryIndexDescription> updateGSIStateInThisList(List<GlobalSecondaryIndexDescription> gsiDescriptions, String indexName, IndexStatus status, Boolean isBackfilling) {
        for (GlobalSecondaryIndexDescription desc : gsiDescriptions) {
            if (!desc.getIndexName().equals(indexName)) continue;
            desc.setIndexStatus(status);
            desc.setBackfilling(isBackfilling);
        }
        return gsiDescriptions;
    }

    @Override
    public String name() {
        return LocalDBUtils.getGsiThreadName(this.tableName, this.indexName, IndexStatus.CREATING);
    }

    @Override
    public void cancel() {
        String indexStatus = this.dbAccess.getTableInfo(this.tableName).getGSIDescWithName(this.indexName).getIndexStatus();
        while (!IndexStatus.ACTIVE.toString().equals(indexStatus)) {
            this.sleepFor(LocalDBUtils.DELAY_BEFORE_SCHEDULING_JOBS_AGAIN);
        }
    }
}

