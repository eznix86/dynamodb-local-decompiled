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
package com.amazonaws.services.dynamodbv2.local.embedded;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBStreams;
import com.amazonaws.services.dynamodbv2.local.embedded.DDBExceptionMappingInvocationHandler;
import com.amazonaws.services.dynamodbv2.local.shared.access.AmazonDynamoDBLocal;
import com.amazonaws.services.dynamodbv2.local.shared.access.LocalDBClient;
import com.amazonaws.services.dynamodbv2.local.shared.access.sqlite.SQLiteDBAccess;
import com.amazonaws.services.dynamodbv2.local.shared.access.sqlite.SQLiteLibraryLoader;
import com.amazonaws.services.dynamodbv2.local.shared.jobs.JobsRegister;
import java.io.File;
import java.lang.reflect.Proxy;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
import software.amazon.awssdk.services.dynamodb.DynamoDbAsyncClient;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.streams.DynamoDbStreamsAsyncClient;
import software.amazon.awssdk.services.dynamodb.streams.DynamoDbStreamsClient;

public class DynamoDBEmbedded {
    public static final String SQLITE4JAVA_PKG = "com.almworks.sqlite4java";

    public static AmazonDynamoDBLocal create() {
        return DynamoDBEmbedded.create(null);
    }

    public static AmazonDynamoDBLocal create(File file) {
        JobsRegister jobs = new JobsRegister(Executors.newFixedThreadPool(10), false);
        SQLiteDBAccess dbAccess = file == null ? new SQLiteDBAccess() : new SQLiteDBAccess(file);
        LocalDBClient impl = new LocalDBClient(dbAccess, jobs);
        final DDBExceptionMappingInvocationHandler handler = new DDBExceptionMappingInvocationHandler(impl);
        return new AmazonDynamoDBLocal(){
            final AmazonDynamoDBLocal proxy;
            final AmazonDynamoDB amazonDynamoDBProxy;
            final DynamoDbClient dynamoDbClientProxy;
            final DynamoDbAsyncClient dynamoDbAsyncClientProxy;
            final AmazonDynamoDBStreams amazonDynamoDBStreamsProxy;
            final DynamoDbStreamsClient dynamoDbStreamsClientProxy;
            final DynamoDbStreamsAsyncClient dynamoDbStreamsAsyncClientProxy;
            {
                this.proxy = (AmazonDynamoDBLocal)Proxy.newProxyInstance(AmazonDynamoDBLocal.class.getClassLoader(), new Class[]{AmazonDynamoDBLocal.class}, handler);
                this.amazonDynamoDBProxy = (AmazonDynamoDB)Proxy.newProxyInstance(AmazonDynamoDB.class.getClassLoader(), new Class[]{AmazonDynamoDB.class}, handler);
                this.dynamoDbClientProxy = (DynamoDbClient)Proxy.newProxyInstance(DynamoDbClient.class.getClassLoader(), new Class[]{DynamoDbClient.class}, handler);
                this.dynamoDbAsyncClientProxy = (DynamoDbAsyncClient)Proxy.newProxyInstance(DynamoDbAsyncClient.class.getClassLoader(), new Class[]{DynamoDbAsyncClient.class}, handler);
                this.amazonDynamoDBStreamsProxy = (AmazonDynamoDBStreams)Proxy.newProxyInstance(AmazonDynamoDBStreams.class.getClassLoader(), new Class[]{AmazonDynamoDBStreams.class}, handler);
                this.dynamoDbStreamsClientProxy = (DynamoDbStreamsClient)Proxy.newProxyInstance(DynamoDbStreamsClient.class.getClassLoader(), new Class[]{DynamoDbStreamsClient.class}, handler);
                this.dynamoDbStreamsAsyncClientProxy = (DynamoDbStreamsAsyncClient)Proxy.newProxyInstance(DynamoDbStreamsAsyncClient.class.getClassLoader(), new Class[]{DynamoDbStreamsAsyncClient.class}, handler);
            }

            @Override
            public void triggerShardRollovers() {
                this.proxy.triggerShardRollovers();
            }

            @Override
            public void shutdown() {
                this.proxy.shutdown();
            }

            @Override
            public List<Runnable> shutdownNow() {
                return this.proxy.shutdownNow();
            }

            @Override
            public void dilateEventTimes(long ms) {
                this.proxy.dilateEventTimes(ms);
            }

            @Override
            public AmazonDynamoDB amazonDynamoDB() {
                return this.amazonDynamoDBProxy;
            }

            @Override
            public DynamoDbClient dynamoDbClient() {
                return this.dynamoDbClientProxy;
            }

            @Override
            public DynamoDbAsyncClient dynamoDbAsyncClient() {
                return this.dynamoDbAsyncClientProxy;
            }

            @Override
            public AmazonDynamoDBStreams amazonDynamoDBStreams() {
                return this.amazonDynamoDBStreamsProxy;
            }

            @Override
            public DynamoDbStreamsClient dynamoDbStreamsClient() {
                return this.dynamoDbStreamsClientProxy;
            }

            @Override
            public DynamoDbStreamsAsyncClient dynamoDbStreamsAsyncClient() {
                return this.dynamoDbStreamsAsyncClientProxy;
            }
        };
    }

    static {
        try {
            SQLiteLibraryLoader.initialize();
            Logger.getLogger(SQLITE4JAVA_PKG).setLevel(Level.WARNING);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

