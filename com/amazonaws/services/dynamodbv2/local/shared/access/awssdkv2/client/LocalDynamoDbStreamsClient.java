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
 *  software.amazon.awssdk.awscore.exception.AwsServiceException
 *  software.amazon.awssdk.core.exception.SdkClientException
 *  software.amazon.awssdk.services.dynamodb.model.DescribeStreamRequest
 *  software.amazon.awssdk.services.dynamodb.model.DescribeStreamResponse
 *  software.amazon.awssdk.services.dynamodb.model.GetRecordsRequest
 *  software.amazon.awssdk.services.dynamodb.model.GetRecordsResponse
 *  software.amazon.awssdk.services.dynamodb.model.GetShardIteratorRequest
 *  software.amazon.awssdk.services.dynamodb.model.GetShardIteratorResponse
 *  software.amazon.awssdk.services.dynamodb.model.ListStreamsRequest
 *  software.amazon.awssdk.services.dynamodb.model.ListStreamsResponse
 *  software.amazon.awssdk.services.dynamodb.streams.DynamoDbStreamsClient
 *  software.amazon.awssdk.utils.builder.SdkBuilder
 */
package com.amazonaws.services.dynamodbv2.local.shared.access.awssdkv2.client;

import com.amazonaws.services.dynamodbv2.local.shared.access.awssdkv1.client.LocalAmazonDynamoDBStreams;
import com.amazonaws.services.dynamodbv2.local.shared.access.awssdkv2.client.LocalDynamoDbStreamsClientBase;
import com.amazonaws.services.dynamodbv2.model.DescribeStreamRequest;
import com.amazonaws.services.dynamodbv2.model.DescribeStreamResult;
import com.amazonaws.services.dynamodbv2.model.GetRecordsResult;
import com.amazonaws.services.dynamodbv2.model.GetShardIteratorResult;
import com.amazonaws.services.dynamodbv2.model.ListStreamsResult;
import software.amazon.awssdk.awscore.exception.AwsServiceException;
import software.amazon.awssdk.core.exception.SdkClientException;
import software.amazon.awssdk.services.dynamodb.model.DescribeStreamResponse;
import software.amazon.awssdk.services.dynamodb.model.GetRecordsRequest;
import software.amazon.awssdk.services.dynamodb.model.GetRecordsResponse;
import software.amazon.awssdk.services.dynamodb.model.GetShardIteratorRequest;
import software.amazon.awssdk.services.dynamodb.model.GetShardIteratorResponse;
import software.amazon.awssdk.services.dynamodb.model.ListStreamsRequest;
import software.amazon.awssdk.services.dynamodb.model.ListStreamsResponse;
import software.amazon.awssdk.services.dynamodb.streams.DynamoDbStreamsClient;
import software.amazon.awssdk.utils.builder.SdkBuilder;

public class LocalDynamoDbStreamsClient
extends LocalDynamoDbStreamsClientBase
implements DynamoDbStreamsClient {
    private final LocalAmazonDynamoDBStreams localAmazonDynamoDBStreams;

    public LocalDynamoDbStreamsClient(LocalAmazonDynamoDBStreams localAmazonDynamoDBStreams) {
        this.localAmazonDynamoDBStreams = localAmazonDynamoDBStreams;
    }

    public DescribeStreamResponse describeStream(software.amazon.awssdk.services.dynamodb.model.DescribeStreamRequest describeStreamRequest) throws AwsServiceException, SdkClientException {
        DescribeStreamResult describeStreamResult = this.localAmazonDynamoDBStreams.describeStream(this.describeStreamRequestConverter.toSdkV1(describeStreamRequest, software.amazon.awssdk.services.dynamodb.model.DescribeStreamRequest::toBuilder, DescribeStreamRequest.class));
        return this.describeStreamResponseConverter.toSdkV2(describeStreamResult, DescribeStreamResponse::serializableBuilderClass, SdkBuilder::build);
    }

    public GetRecordsResponse getRecords(GetRecordsRequest getRecordsRequest) throws AwsServiceException, SdkClientException {
        GetRecordsResult getRecordsResult = this.localAmazonDynamoDBStreams.getRecords(this.getRecordsRequestConverter.toSdkV1(getRecordsRequest, GetRecordsRequest::toBuilder, com.amazonaws.services.dynamodbv2.model.GetRecordsRequest.class));
        return this.getRecordsResponseConverter.toSdkV2(getRecordsResult, GetRecordsResponse::serializableBuilderClass, SdkBuilder::build);
    }

    public GetShardIteratorResponse getShardIterator(GetShardIteratorRequest getShardIteratorRequest) throws AwsServiceException, SdkClientException {
        GetShardIteratorResult getShardIteratorResult = this.localAmazonDynamoDBStreams.getShardIterator(this.getShardIteratorRequestConverter.toSdkV1(getShardIteratorRequest, GetShardIteratorRequest::toBuilder, com.amazonaws.services.dynamodbv2.model.GetShardIteratorRequest.class));
        return this.getShardIteratorResponseConverter.toSdkV2(getShardIteratorResult, GetShardIteratorResponse::serializableBuilderClass, SdkBuilder::build);
    }

    public ListStreamsResponse listStreams(ListStreamsRequest listStreamsRequest) throws AwsServiceException, SdkClientException {
        ListStreamsResult listStreamsResult = this.localAmazonDynamoDBStreams.listStreams(this.listStreamsRequestConverter.toSdkV1(listStreamsRequest, ListStreamsRequest::toBuilder, com.amazonaws.services.dynamodbv2.model.ListStreamsRequest.class));
        return this.listStreamsResponseConverter.toSdkV2(listStreamsResult, ListStreamsResponse::serializableBuilderClass, SdkBuilder::build);
    }

    public String serviceName() {
        return "dynamodb";
    }

    public void close() {
        this.localAmazonDynamoDBStreams.shutdown();
    }
}

