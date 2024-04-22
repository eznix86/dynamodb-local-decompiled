/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  software.amazon.awssdk.services.dynamodb.model.DescribeStreamRequest
 *  software.amazon.awssdk.services.dynamodb.model.DescribeStreamResponse
 *  software.amazon.awssdk.services.dynamodb.model.GetRecordsRequest
 *  software.amazon.awssdk.services.dynamodb.model.GetRecordsResponse
 *  software.amazon.awssdk.services.dynamodb.model.GetShardIteratorRequest
 *  software.amazon.awssdk.services.dynamodb.model.GetShardIteratorResponse
 *  software.amazon.awssdk.services.dynamodb.model.ListStreamsRequest
 *  software.amazon.awssdk.services.dynamodb.model.ListStreamsResponse
 *  software.amazon.awssdk.services.dynamodb.streams.DynamoDbStreamsAsyncClient
 *  software.amazon.awssdk.services.dynamodb.streams.DynamoDbStreamsClient
 */
package com.amazonaws.services.dynamodbv2.local.shared.access.awssdkv2.client;

import com.amazonaws.services.dynamodbv2.local.shared.access.awssdkv2.client.CompletableFutureProvider;
import java.util.concurrent.CompletableFuture;
import software.amazon.awssdk.services.dynamodb.model.DescribeStreamRequest;
import software.amazon.awssdk.services.dynamodb.model.DescribeStreamResponse;
import software.amazon.awssdk.services.dynamodb.model.GetRecordsRequest;
import software.amazon.awssdk.services.dynamodb.model.GetRecordsResponse;
import software.amazon.awssdk.services.dynamodb.model.GetShardIteratorRequest;
import software.amazon.awssdk.services.dynamodb.model.GetShardIteratorResponse;
import software.amazon.awssdk.services.dynamodb.model.ListStreamsRequest;
import software.amazon.awssdk.services.dynamodb.model.ListStreamsResponse;
import software.amazon.awssdk.services.dynamodb.streams.DynamoDbStreamsAsyncClient;
import software.amazon.awssdk.services.dynamodb.streams.DynamoDbStreamsClient;

public class LocalDynamoDbStreamsAsyncClient
implements DynamoDbStreamsAsyncClient {
    private final DynamoDbStreamsClient dynamoDbStreamsClient;
    private final CompletableFutureProvider futureProvider;

    public LocalDynamoDbStreamsAsyncClient(DynamoDbStreamsClient dynamoDbStreamsClient) {
        this.dynamoDbStreamsClient = dynamoDbStreamsClient;
        this.futureProvider = new CompletableFutureProvider();
    }

    public String serviceName() {
        return "dynamodb";
    }

    public void close() {
        this.dynamoDbStreamsClient.close();
    }

    public CompletableFuture<DescribeStreamResponse> describeStream(DescribeStreamRequest describeStreamRequest) {
        return this.futureProvider.completableFuture(describeStreamRequest, arg_0 -> ((DynamoDbStreamsClient)this.dynamoDbStreamsClient).describeStream(arg_0));
    }

    public CompletableFuture<GetRecordsResponse> getRecords(GetRecordsRequest getRecordsRequest) {
        return this.futureProvider.completableFuture(getRecordsRequest, arg_0 -> ((DynamoDbStreamsClient)this.dynamoDbStreamsClient).getRecords(arg_0));
    }

    public CompletableFuture<GetShardIteratorResponse> getShardIterator(GetShardIteratorRequest getShardIteratorRequest) {
        return this.futureProvider.completableFuture(getShardIteratorRequest, arg_0 -> ((DynamoDbStreamsClient)this.dynamoDbStreamsClient).getShardIterator(arg_0));
    }

    public CompletableFuture<ListStreamsResponse> listStreams(ListStreamsRequest listStreamsRequest) {
        return this.futureProvider.completableFuture(listStreamsRequest, arg_0 -> ((DynamoDbStreamsClient)this.dynamoDbStreamsClient).listStreams(arg_0));
    }
}

