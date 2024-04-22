/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  com.amazonaws.services.dynamodbv2.AmazonDynamoDB
 *  com.amazonaws.services.dynamodbv2.AmazonDynamoDBStreams
 *  software.amazon.awssdk.services.dynamodb.DynamoDbAsyncClient
 *  software.amazon.awssdk.services.dynamodb.DynamoDbClient
 *  software.amazon.awssdk.services.dynamodb.streams.DynamoDbStreamsAsyncClient
 *  software.amazon.awssdk.services.dynamodb.streams.DynamoDbStreamsClient
 */
package com.amazonaws.services.dynamodbv2.local.shared.access;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBStreams;
import java.util.List;
import java.util.concurrent.TimeUnit;
import software.amazon.awssdk.services.dynamodb.DynamoDbAsyncClient;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.streams.DynamoDbStreamsAsyncClient;
import software.amazon.awssdk.services.dynamodb.streams.DynamoDbStreamsClient;

public interface AmazonDynamoDBLocal {
    public static final int GET_RECORD_MIN_LIMIT = 1;
    public static final int GET_RECORD_MAX_LIMIT = 1000;
    public static final long STREAM_SURVIVAL_DURATION = TimeUnit.MILLISECONDS.convert(24L, TimeUnit.HOURS);
    public static final long SHARD_SURVIVAL_DURATION = TimeUnit.MILLISECONDS.convert(24L, TimeUnit.HOURS);
    public static final long RECORD_SURVIVAL_DURATION = TimeUnit.MILLISECONDS.convert(24L, TimeUnit.HOURS);
    public static final long SHARD_ROLLOVER_TIME = TimeUnit.MILLISECONDS.convert(4L, TimeUnit.HOURS);
    public static final long SAFETY_SURVIVAL_PADDING = TimeUnit.MILLISECONDS.convert(6L, TimeUnit.HOURS);
    public static final String STREAMS_EVENT_VERSION = "1.1";
    public static final String DEFAULT_ACCOUNT_NUMBER = "000000000000";
    public static final String DEFAULT_REGION = "ddblocal";
    public static final String DEFAULT_EVENT_SOURCE = "aws:dynamodb";
    public static final Long ACC_MAX_READ_CAPACITY_UNITS = 80000L;
    public static final Long ACC_MAX_WRITE_CAPACITY_UNITS = 80000L;
    public static final Long TABLE_MAX_WRITE_CAPACITY_UNITS = 40000L;
    public static final Long TABLE_MAX_READ_CAPACITY_UNITS = 40000L;
    public static final long TRANSACTION_CLIENT_TOKEN_SURVIVAL_DURATION = TimeUnit.MILLISECONDS.convert(10L, TimeUnit.MINUTES);

    public AmazonDynamoDB amazonDynamoDB();

    public AmazonDynamoDBStreams amazonDynamoDBStreams();

    default public DynamoDbClient dynamoDbClient() {
        throw new UnsupportedOperationException();
    }

    default public DynamoDbAsyncClient dynamoDbAsyncClient() {
        throw new UnsupportedOperationException();
    }

    default public DynamoDbStreamsClient dynamoDbStreamsClient() {
        throw new UnsupportedOperationException();
    }

    default public DynamoDbStreamsAsyncClient dynamoDbStreamsAsyncClient() {
        throw new UnsupportedOperationException();
    }

    public void dilateEventTimes(long var1);

    public void triggerShardRollovers();

    public void shutdown();

    public List<Runnable> shutdownNow();
}

