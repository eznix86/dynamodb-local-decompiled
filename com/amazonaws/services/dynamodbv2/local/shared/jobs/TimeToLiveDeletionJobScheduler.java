/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazonaws.services.dynamodbv2.local.shared.jobs;

import com.amazonaws.services.dynamodbv2.local.shared.access.LocalDBAccess;
import com.amazonaws.services.dynamodbv2.local.shared.access.LocalDBInputConverter;
import com.amazonaws.services.dynamodbv2.local.shared.access.LocalDBUtils;
import com.amazonaws.services.dynamodbv2.local.shared.access.awssdkv1.client.LocalAmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.local.shared.access.sqlite.TableSchemaInfo;
import com.amazonaws.services.dynamodbv2.local.shared.jobs.JobsRegister;
import com.amazonaws.services.dynamodbv2.local.shared.jobs.NamedJob;
import com.amazonaws.services.dynamodbv2.local.shared.jobs.TimeToLiveDeletionJob;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

public class TimeToLiveDeletionJobScheduler
extends NamedJob {
    private final LocalAmazonDynamoDB localAmazonDynamoDB;
    private final LocalDBAccess dbAccess;
    private final AtomicBoolean shouldTerminate;
    private final LocalDBInputConverter inputConverter;

    public TimeToLiveDeletionJobScheduler(LocalDBAccess dbAccess, LocalAmazonDynamoDB localAmazonDynamoDB, LocalDBInputConverter inputConverter, JobsRegister jobs) {
        super(jobs);
        this.localAmazonDynamoDB = localAmazonDynamoDB;
        this.dbAccess = dbAccess;
        this.inputConverter = inputConverter;
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
                for (Map.Entry<String, TableSchemaInfo> entry : this.dbAccess.fetchAllTablesWithTimeToLiveEnabled().entrySet()) {
                    TableSchemaInfo tableSchemaInfo;
                    String timeToLiveAttributeName;
                    String tableName = entry.getKey();
                    String timeToLiveJobName = TimeToLiveDeletionJob.jobName(tableName, timeToLiveAttributeName = (tableSchemaInfo = entry.getValue()).getTimeToLiveAttributeName());
                    if (this.jobs.isRunning(timeToLiveJobName)) continue;
                    TimeToLiveDeletionJob task = new TimeToLiveDeletionJob(tableName, tableSchemaInfo, timeToLiveAttributeName, this.localAmazonDynamoDB, this.inputConverter, this.dbAccess, this.jobs);
                    this.jobs.schedule(task);
                }
            }
            this.sleepFor(LocalDBUtils.DELAY_BEFORE_SCHEDULING_JOBS_AGAIN);
        }
    }

    @Override
    public String name() {
        return "ITEM-EXPIRY-DELETION-JOBS-SCHEDULER";
    }

    @Override
    public void cancel() {
        this.shouldTerminate.set(true);
    }
}

