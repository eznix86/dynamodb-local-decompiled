/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazonaws.services.dynamodbv2.local.shared.jobs;

import com.amazonaws.services.dynamodbv2.exceptions.AWSExceptionFactory;
import com.amazonaws.services.dynamodbv2.exceptions.AmazonServiceExceptionType;
import com.amazonaws.services.dynamodbv2.local.shared.exceptions.LocalDBClientExceptionMessage;
import com.amazonaws.services.dynamodbv2.local.shared.jobs.JobsRegister;

abstract class NamedJob
implements Runnable {
    protected final JobsRegister jobs;

    NamedJob(JobsRegister jobs) {
        this.jobs = jobs;
    }

    protected abstract void doJob();

    public abstract String name();

    public abstract void cancel();

    @Override
    public void run() {
        this.doJob();
        this.jobs.remove(this.name());
    }

    void sleepFor(long millis) {
        if (millis == 0L) {
            return;
        }
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.INTERNAL_SERVER_ERROR, LocalDBClientExceptionMessage.INTERRUPTED_EXCEPTION_DURING_BACKGROUND_JOB.getMessage());
        }
    }
}

