/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.apache.commons.cli.ParseException
 */
package com.amazonaws.services.dynamodbv2.local.serverRunner;

import com.amazonaws.services.dynamodbv2.local.main.ServerRunner;
import com.amazonaws.services.dynamodbv2.local.server.DynamoDBProxyServer;
import org.apache.commons.cli.ParseException;

@Deprecated
public class CommandLineInput
extends com.amazonaws.services.dynamodbv2.local.main.CommandLineInput {
    @Deprecated
    public CommandLineInput(String[] arg0) throws ParseException {
        super(arg0);
    }

    @Deprecated
    public DynamoDBProxyServer createServer() {
        return ServerRunner.createServer(this);
    }

    @Override
    public boolean shouldOptimizeDBBeforeStartup() {
        return this.optimizeDBBeforeStartup;
    }

    @Override
    public void setOptimizeDBBeforeStartup(boolean optimizeDBBeforeStartup) {
        this.optimizeDBBeforeStartup = optimizeDBBeforeStartup;
    }

    @Override
    public boolean shouldDelayTransientStatuses() {
        return this.delayTransientStatuses;
    }
}

