/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  com.amazonaws.services.dynamodbv2.model.IndexStatus
 */
package com.amazonaws.services.dynamodbv2.local.shared.jobs;

import com.amazonaws.services.dynamodbv2.local.shared.access.LocalDBAccess;
import com.amazonaws.services.dynamodbv2.local.shared.access.LocalDBUtils;
import com.amazonaws.services.dynamodbv2.local.shared.jobs.JobsRegister;
import com.amazonaws.services.dynamodbv2.local.shared.jobs.NamedJob;
import com.amazonaws.services.dynamodbv2.model.IndexStatus;

public class DeleteGSIJob
extends NamedJob {
    private final LocalDBAccess dbAccess;
    private final String tableName;
    private final String indexName;

    public DeleteGSIJob(String tableName, String indexName, LocalDBAccess dbAccess, JobsRegister jobs) {
        super(jobs);
        this.indexName = indexName;
        this.dbAccess = dbAccess;
        this.tableName = tableName;
    }

    public static String deleteGSIThreadName(String tableName, String indexName) {
        return LocalDBUtils.getGsiThreadName(tableName, indexName, IndexStatus.DELETING);
    }

    @Override
    protected void doJob() {
        if (this.jobs.shouldDelayTransientStatuses()) {
            this.sleepFor(LocalDBUtils.LONG_DELAY_TO_HOLD_TRANSIENT_STATUSES);
        }
        new LocalDBAccess.WriteLockWithTimeout(this.dbAccess.getLockForTable(this.tableName), 10){

            @Override
            public void criticalSection() {
                DeleteGSIJob.this.dbAccess.deleteGSI(DeleteGSIJob.this.tableName, DeleteGSIJob.this.indexName);
            }
        }.execute();
    }

    @Override
    public String name() {
        return DeleteGSIJob.deleteGSIThreadName(this.tableName, this.indexName);
    }

    @Override
    public void cancel() {
        String indexStatus = this.dbAccess.getTableInfo(this.tableName).getGSIDescWithName(this.indexName).getIndexStatus();
        while (!IndexStatus.ACTIVE.toString().equals(indexStatus)) {
            this.sleepFor(LocalDBUtils.DELAY_BEFORE_SCHEDULING_JOBS_AGAIN);
        }
    }
}

