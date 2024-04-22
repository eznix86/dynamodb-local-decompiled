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
import com.amazonaws.services.dynamodbv2.local.shared.jobs.DeleteGSIJob;
import com.amazonaws.services.dynamodbv2.local.shared.jobs.JobsRegister;
import com.amazonaws.services.dynamodbv2.local.shared.jobs.NamedJob;
import com.amazonaws.services.dynamodbv2.model.GlobalSecondaryIndexDescription;
import com.amazonaws.services.dynamodbv2.model.IndexStatus;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

public class DeleteGSIJobScheduler
extends NamedJob {
    private final LocalDBAccess dbAccess;
    private final AtomicBoolean shouldTerminate;

    public DeleteGSIJobScheduler(LocalDBAccess dbAccess, JobsRegister jobs) {
        super(jobs);
        this.dbAccess = dbAccess;
        this.shouldTerminate = new AtomicBoolean(false);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    protected void doJob() {
        while (!this.shouldTerminate.get()) {
            LocalDBAccess localDBAccess = this.dbAccess;
            synchronized (localDBAccess) {
                for (Map.Entry<String, List<GlobalSecondaryIndexDescription>> entry : this.dbAccess.getGSIsByStatusFromAllTables(IndexStatus.DELETING, null).entrySet()) {
                    String tableName = entry.getKey();
                    List<GlobalSecondaryIndexDescription> gsis = entry.getValue();
                    for (GlobalSecondaryIndexDescription gsi : gsis) {
                        String deleteGSIJobName = DeleteGSIJob.deleteGSIThreadName(tableName, gsi.getIndexName());
                        if (this.jobs.isRunning(deleteGSIJobName)) continue;
                        DeleteGSIJob task = new DeleteGSIJob(tableName, gsi.getIndexName(), this.dbAccess, this.jobs);
                        this.jobs.schedule(task);
                    }
                }
            }
            this.sleepFor(LocalDBUtils.DELAY_BEFORE_SCHEDULING_JOBS_AGAIN);
        }
    }

    @Override
    public String name() {
        return "DELETE-GSI-JOBS-SCHEDULER";
    }

    @Override
    public void cancel() {
        this.shouldTerminate.set(true);
    }
}

