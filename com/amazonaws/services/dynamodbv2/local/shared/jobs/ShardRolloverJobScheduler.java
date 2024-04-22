/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.apache.logging.log4j.Logger
 */
package com.amazonaws.services.dynamodbv2.local.shared.jobs;

import com.amazonaws.services.dynamodbv2.local.shared.access.LocalDBAccess;
import com.amazonaws.services.dynamodbv2.local.shared.access.LocalDBClient;
import com.amazonaws.services.dynamodbv2.local.shared.jobs.JobsRegister;
import com.amazonaws.services.dynamodbv2.local.shared.jobs.NamedJob;
import com.amazonaws.services.dynamodbv2.local.shared.jobs.ShardRolloverJob;
import com.amazonaws.services.dynamodbv2.local.shared.logging.LogManager;
import java.util.concurrent.atomic.AtomicBoolean;
import org.apache.logging.log4j.Logger;

public class ShardRolloverJobScheduler
extends NamedJob {
    private static final Logger LOGGER = LogManager.getLogger(ShardRolloverJobScheduler.class);
    private static final String JOB_NAME = "SHARD-ROLLOVER-JOB-SCHEDULER";
    private final LocalDBAccess dbAccess;
    private final AtomicBoolean shouldTerminate;
    private final long timeBetweenJobs;

    public ShardRolloverJobScheduler(LocalDBAccess dbAccess, JobsRegister jobs, long timeBetweenJobs) {
        super(jobs);
        this.dbAccess = dbAccess;
        this.shouldTerminate = new AtomicBoolean(false);
        this.timeBetweenJobs = timeBetweenJobs;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    protected void doJob() {
        while (!this.shouldTerminate.get()) {
            LocalDBAccess localDBAccess = this.dbAccess;
            synchronized (localDBAccess) {
                LOGGER.debug("{} scheduling ShardRolloverJob", (Object)JOB_NAME);
                ShardRolloverJob task = new ShardRolloverJob(this.dbAccess, this.jobs, LocalDBClient.SHARD_ROLLOVER_TIME);
                this.jobs.schedule(task);
            }
            this.sleepFor(this.timeBetweenJobs);
        }
    }

    @Override
    public String name() {
        return JOB_NAME;
    }

    @Override
    public void cancel() {
        this.shouldTerminate.set(true);
    }
}

