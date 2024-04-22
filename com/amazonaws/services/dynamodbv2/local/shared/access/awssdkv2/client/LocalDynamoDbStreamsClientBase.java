/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  com.amazonaws.services.dynamodbv2.model.DescribeStreamRequest
 *  com.amazonaws.services.dynamodbv2.model.DescribeStreamResult
 *  com.amazonaws.services.dynamodbv2.model.GetRecordsRequest
 *  com.amazonaws.services.dynamodbv2.model.GetRecordsResult
 *  com.amazonaws.services.dynamodbv2.model.GetShardIteratorRequest
 *  com.amazonaws.services.dynamodbv2.model.GetShardIteratorResult
 *  com.amazonaws.services.dynamodbv2.model.ListStreamsRequest
 *  com.amazonaws.services.dynamodbv2.model.ListStreamsResult
 *  software.amazon.awssdk.services.dynamodb.model.DescribeStreamRequest
 *  software.amazon.awssdk.services.dynamodb.model.DescribeStreamRequest$Builder
 *  software.amazon.awssdk.services.dynamodb.model.DescribeStreamResponse
 *  software.amazon.awssdk.services.dynamodb.model.DescribeStreamResponse$Builder
 *  software.amazon.awssdk.services.dynamodb.model.GetRecordsRequest
 *  software.amazon.awssdk.services.dynamodb.model.GetRecordsRequest$Builder
 *  software.amazon.awssdk.services.dynamodb.model.GetRecordsResponse
 *  software.amazon.awssdk.services.dynamodb.model.GetRecordsResponse$Builder
 *  software.amazon.awssdk.services.dynamodb.model.GetShardIteratorRequest
 *  software.amazon.awssdk.services.dynamodb.model.GetShardIteratorRequest$Builder
 *  software.amazon.awssdk.services.dynamodb.model.GetShardIteratorResponse
 *  software.amazon.awssdk.services.dynamodb.model.GetShardIteratorResponse$Builder
 *  software.amazon.awssdk.services.dynamodb.model.ListStreamsRequest
 *  software.amazon.awssdk.services.dynamodb.model.ListStreamsRequest$Builder
 *  software.amazon.awssdk.services.dynamodb.model.ListStreamsResponse
 *  software.amazon.awssdk.services.dynamodb.model.ListStreamsResponse$Builder
 */
package com.amazonaws.services.dynamodbv2.local.shared.access.awssdkv2.client;

import com.amazonaws.services.dynamodbv2.local.shared.access.awssdkv2.client.ClientSdkV2Base;
import com.amazonaws.services.dynamodbv2.local.shared.access.awssdkv2.converters.DynamoDBStreamsRequestConverter;
import com.amazonaws.services.dynamodbv2.local.shared.access.awssdkv2.converters.DynamoDBStreamsResponseConverter;
import com.amazonaws.services.dynamodbv2.model.DescribeStreamRequest;
import com.amazonaws.services.dynamodbv2.model.DescribeStreamResult;
import com.amazonaws.services.dynamodbv2.model.GetRecordsResult;
import com.amazonaws.services.dynamodbv2.model.GetShardIteratorResult;
import com.amazonaws.services.dynamodbv2.model.ListStreamsResult;
import software.amazon.awssdk.services.dynamodb.model.DescribeStreamRequest;
import software.amazon.awssdk.services.dynamodb.model.DescribeStreamResponse;
import software.amazon.awssdk.services.dynamodb.model.GetRecordsRequest;
import software.amazon.awssdk.services.dynamodb.model.GetRecordsResponse;
import software.amazon.awssdk.services.dynamodb.model.GetShardIteratorRequest;
import software.amazon.awssdk.services.dynamodb.model.GetShardIteratorResponse;
import software.amazon.awssdk.services.dynamodb.model.ListStreamsRequest;
import software.amazon.awssdk.services.dynamodb.model.ListStreamsResponse;

public abstract class LocalDynamoDbStreamsClientBase
extends ClientSdkV2Base {
    protected final DynamoDBStreamsRequestConverter<DescribeStreamRequest, software.amazon.awssdk.services.dynamodb.model.DescribeStreamRequest, DescribeStreamRequest.Builder> describeStreamRequestConverter;
    protected final DynamoDBStreamsRequestConverter<com.amazonaws.services.dynamodbv2.model.GetRecordsRequest, GetRecordsRequest, GetRecordsRequest.Builder> getRecordsRequestConverter;
    protected final DynamoDBStreamsRequestConverter<com.amazonaws.services.dynamodbv2.model.GetShardIteratorRequest, GetShardIteratorRequest, GetShardIteratorRequest.Builder> getShardIteratorRequestConverter;
    protected final DynamoDBStreamsRequestConverter<com.amazonaws.services.dynamodbv2.model.ListStreamsRequest, ListStreamsRequest, ListStreamsRequest.Builder> listStreamsRequestConverter;
    protected final DynamoDBStreamsResponseConverter<DescribeStreamResult, DescribeStreamResponse, DescribeStreamResponse.Builder> describeStreamResponseConverter;
    protected final DynamoDBStreamsResponseConverter<GetRecordsResult, GetRecordsResponse, GetRecordsResponse.Builder> getRecordsResponseConverter;
    protected final DynamoDBStreamsResponseConverter<GetShardIteratorResult, GetShardIteratorResponse, GetShardIteratorResponse.Builder> getShardIteratorResponseConverter;
    protected final DynamoDBStreamsResponseConverter<ListStreamsResult, ListStreamsResponse, ListStreamsResponse.Builder> listStreamsResponseConverter;

    public LocalDynamoDbStreamsClientBase() {
        this.describeStreamRequestConverter = new DynamoDBStreamsRequestConverter(this.objectMapper);
        this.getRecordsRequestConverter = new DynamoDBStreamsRequestConverter(this.objectMapper);
        this.getShardIteratorRequestConverter = new DynamoDBStreamsRequestConverter(this.objectMapper);
        this.listStreamsRequestConverter = new DynamoDBStreamsRequestConverter(this.objectMapper);
        this.describeStreamResponseConverter = new DynamoDBStreamsResponseConverter(this.objectMapper);
        this.getRecordsResponseConverter = new DynamoDBStreamsResponseConverter(this.objectMapper);
        this.getShardIteratorResponseConverter = new DynamoDBStreamsResponseConverter(this.objectMapper);
        this.listStreamsResponseConverter = new DynamoDBStreamsResponseConverter(this.objectMapper);
    }
}

