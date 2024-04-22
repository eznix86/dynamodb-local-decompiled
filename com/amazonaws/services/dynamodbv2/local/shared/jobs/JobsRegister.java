/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazonaws.services.dynamodbv2.local.shared.jobs;

import com.amazonaws.services.dynamodbv2.local.shared.jobs.NamedJob;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class JobsRegister {
    private final ExecutorService threadPool;
    private final boolean delayTransientStatuses;
    private final Map<String, NamedJob> jobs = new ConcurrentHashMap<String, NamedJob>();

    public JobsRegister(ExecutorService executorService, boolean delayTransientStatuses) {
        this.threadPool = executorService;
        this.delayTransientStatuses = delayTransientStatuses;
    }

    public Future<?> schedule(NamedJob job) {
        this.jobs.put(job.name(), job);
        return this.threadPool.submit(job);
    }

    public void remove(String jobName) {
        this.jobs.remove(jobName);
    }

    public boolean isRunning(String jobName) {
        return this.jobs.containsKey(jobName);
    }

    public void shutdown() {
        this.threadPool.shutdown();
        for (NamedJob job : this.jobs.values()) {
            job.cancel();
        }
    }

    public List<Runnable> shutdownNow() {
        return this.threadPool.shutdownNow();
    }

    public boolean shouldDelayTransientStatuses() {
        return this.delayTransientStatuses;
    }
}

