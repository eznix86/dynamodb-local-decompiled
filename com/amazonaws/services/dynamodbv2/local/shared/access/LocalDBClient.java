/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  com.amazonaws.services.dynamodbv2.AmazonDynamoDB
 *  com.amazonaws.services.dynamodbv2.AmazonDynamoDBStreams
 *  org.apache.logging.log4j.Logger
 *  software.amazon.awssdk.services.dynamodb.DynamoDbClient
 *  software.amazon.awssdk.services.dynamodb.streams.DynamoDbStreamsAsyncClient
 *  software.amazon.awssdk.services.dynamodb.streams.DynamoDbStreamsClient
 */
package com.amazonaws.services.dynamodbv2.local.shared.access;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBStreams;
import com.amazonaws.services.dynamodbv2.local.shared.access.AmazonDynamoDBLocal;
import com.amazonaws.services.dynamodbv2.local.shared.access.LocalDBAccess;
import com.amazonaws.services.dynamodbv2.local.shared.access.awssdkv1.client.LocalAmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.local.shared.access.awssdkv1.client.LocalAmazonDynamoDBStreams;
import com.amazonaws.services.dynamodbv2.local.shared.access.awssdkv2.client.LocalDynamoDbAsyncClient;
import com.amazonaws.services.dynamodbv2.local.shared.access.awssdkv2.client.LocalDynamoDbClient;
import com.amazonaws.services.dynamodbv2.local.shared.access.awssdkv2.client.LocalDynamoDbStreamsAsyncClient;
import com.amazonaws.services.dynamodbv2.local.shared.access.awssdkv2.client.LocalDynamoDbStreamsClient;
import com.amazonaws.services.dynamodbv2.local.shared.jobs.JobsRegister;
import com.amazonaws.services.dynamodbv2.local.shared.logging.LogManager;
import java.util.List;
import java.util.concurrent.Executors;
import org.apache.logging.log4j.Logger;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.streams.DynamoDbStreamsAsyncClient;
import software.amazon.awssdk.services.dynamodb.streams.DynamoDbStreamsClient;

public class LocalDBClient
implements AmazonDynamoDBLocal {
    private static final Logger logger = LogManager.getLogger(LocalDBClient.class);
    private final LocalDBAccess dbAccess;
    private final JobsRegister jobs;
    private final LocalAmazonDynamoDBStreams amazonDynamoDBStreams;
    private final LocalDynamoDbStreamsClient dynamoDbStreamsClient;
    private final LocalDynamoDbStreamsAsyncClient dynamoDbStreamsAsyncClient;
    private final LocalAmazonDynamoDB amazonDynamoDB;
    private final LocalDynamoDbClient dynamoDbClient;
    private final LocalDynamoDbAsyncClient dynamoDbAsyncClient;

    @Deprecated
    public LocalDBClient(LocalDBAccess dbAccess) {
        this(dbAccess, new JobsRegister(Executors.newFixedThreadPool(10), false));
    }

    public LocalDBClient(LocalDBAccess dbAccess, JobsRegister jobs) {
        this.dbAccess = dbAccess;
        this.jobs = jobs;
        this.amazonDynamoDBStreams = new LocalAmazonDynamoDBStreams(dbAccess, jobs);
        this.dynamoDbStreamsClient = new LocalDynamoDbStreamsClient(this.amazonDynamoDBStreams);
        this.dynamoDbStreamsAsyncClient = new LocalDynamoDbStreamsAsyncClient(this.dynamoDbStreamsClient);
        this.amazonDynamoDB = new LocalAmazonDynamoDB(dbAccess, jobs);
        this.dynamoDbClient = new LocalDynamoDbClient(this.amazonDynamoDB);
        this.dynamoDbAsyncClient = new LocalDynamoDbAsyncClient(this.dynamoDbClient);
    }

    @Override
    public void dilateEventTimes(long ms) {
        this.amazonDynamoDBStreams.dilateEventTimes(ms);
    }

    @Override
    public void triggerShardRollovers() {
        this.amazonDynamoDBStreams.triggerShardRollovers();
    }

    @Override
    public AmazonDynamoDB amazonDynamoDB() {
        return this.amazonDynamoDB;
    }

    @Override
    public AmazonDynamoDBStreams amazonDynamoDBStreams() {
        return this.amazonDynamoDBStreams;
    }

    @Override
    public DynamoDbClient dynamoDbClient() {
        return this.dynamoDbClient;
    }

    @Override
    public LocalDynamoDbAsyncClient dynamoDbAsyncClient() {
        return this.dynamoDbAsyncClient;
    }

    @Override
    public DynamoDbStreamsClient dynamoDbStreamsClient() {
        return this.dynamoDbStreamsClient;
    }

    @Override
    public DynamoDbStreamsAsyncClient dynamoDbStreamsAsyncClient() {
        return this.dynamoDbStreamsAsyncClient;
    }

    @Override
    public void shutdown() {
        logger.info("Shutting down");
        this.jobs.shutdown();
        this.dbAccess.close();
    }

    @Override
    public List<Runnable> shutdownNow() {
        logger.info("Shutting down now  ");
        List<Runnable> runnables = this.jobs.shutdownNow();
        this.dbAccess.close();
        return runnables;
    }
}

