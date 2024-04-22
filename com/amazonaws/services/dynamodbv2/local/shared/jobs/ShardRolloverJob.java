/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.apache.logging.log4j.Logger
 */
package com.amazonaws.services.dynamodbv2.local.shared.jobs;

import com.amazonaws.services.dynamodbv2.local.shared.access.ListTablesResultInfo;
import com.amazonaws.services.dynamodbv2.local.shared.access.LocalDBAccess;
import com.amazonaws.services.dynamodbv2.local.shared.access.LocalDBUtils;
import com.amazonaws.services.dynamodbv2.local.shared.access.TableInfo;
import com.amazonaws.services.dynamodbv2.local.shared.jobs.JobsRegister;
import com.amazonaws.services.dynamodbv2.local.shared.jobs.NamedJob;
import com.amazonaws.services.dynamodbv2.local.shared.logging.LogManager;
import java.util.concurrent.atomic.AtomicBoolean;
import org.apache.logging.log4j.Logger;

public class ShardRolloverJob
extends NamedJob {
    private static final Logger LOGGER = LogManager.getLogger(ShardRolloverJob.class);
    private static final String JOB_NAME = "SHARD-ROLLOVER-JOB";
    private final LocalDBAccess dbAccess;
    private final AtomicBoolean workInProgress = new AtomicBoolean();
    private final long shardAgeToRollover;

    public ShardRolloverJob(LocalDBAccess dbAccess, JobsRegister jobs, long shardAgeToRollover) {
        super(jobs);
        this.dbAccess = dbAccess;
        this.shardAgeToRollover = shardAgeToRollover;
    }

    @Override
    protected void doJob() {
        ListTablesResultInfo initResults = this.dbAccess.listTables(null, 100L);
        for (final String tableName : initResults.getTableNames()) {
            TableInfo tableInfo = this.dbAccess.getTableInfo(tableName);
            if (tableInfo == null || !tableInfo.getStreamSpecification().isStreamEnabled().booleanValue()) continue;
            new LocalDBAccess.WriteLockWithTimeout(this.dbAccess.getLockForTable(tableName), 10){

                @Override
                public void criticalSection() {
                    LOGGER.debug("Performing findAndRolloverActiveShards for table {}", (Object)tableName);
                    ShardRolloverJob.this.dbAccess.findAndRolloverActiveShards(tableName, ShardRolloverJob.this.shardAgeToRollover);
                }
            }.execute();
        }
    }

    @Override
    public String name() {
        return JOB_NAME;
    }

    @Override
    public void cancel() {
        while (this.workInProgress.get()) {
            this.sleepFor(LocalDBUtils.DELAY_BEFORE_SCHEDULING_JOBS_AGAIN);
        }
    }
}

