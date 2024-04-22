/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  com.amazonaws.services.dynamodbv2.model.BatchExecuteStatementRequest
 *  com.amazonaws.services.dynamodbv2.model.BatchExecuteStatementResult
 *  com.amazonaws.services.dynamodbv2.model.BatchGetItemRequest
 *  com.amazonaws.services.dynamodbv2.model.BatchGetItemResult
 *  com.amazonaws.services.dynamodbv2.model.BatchWriteItemRequest
 *  com.amazonaws.services.dynamodbv2.model.BatchWriteItemResult
 *  com.amazonaws.services.dynamodbv2.model.CreateTableRequest
 *  com.amazonaws.services.dynamodbv2.model.CreateTableResult
 *  com.amazonaws.services.dynamodbv2.model.DeleteItemRequest
 *  com.amazonaws.services.dynamodbv2.model.DeleteItemResult
 *  com.amazonaws.services.dynamodbv2.model.DeleteTableRequest
 *  com.amazonaws.services.dynamodbv2.model.DeleteTableResult
 *  com.amazonaws.services.dynamodbv2.model.DescribeLimitsRequest
 *  com.amazonaws.services.dynamodbv2.model.DescribeLimitsResult
 *  com.amazonaws.services.dynamodbv2.model.DescribeTableRequest
 *  com.amazonaws.services.dynamodbv2.model.DescribeTableResult
 *  com.amazonaws.services.dynamodbv2.model.DescribeTimeToLiveRequest
 *  com.amazonaws.services.dynamodbv2.model.DescribeTimeToLiveResult
 *  com.amazonaws.services.dynamodbv2.model.ExecuteStatementRequest
 *  com.amazonaws.services.dynamodbv2.model.ExecuteStatementResult
 *  com.amazonaws.services.dynamodbv2.model.ExecuteTransactionRequest
 *  com.amazonaws.services.dynamodbv2.model.ExecuteTransactionResult
 *  com.amazonaws.services.dynamodbv2.model.GetItemRequest
 *  com.amazonaws.services.dynamodbv2.model.GetItemResult
 *  com.amazonaws.services.dynamodbv2.model.ListTablesRequest
 *  com.amazonaws.services.dynamodbv2.model.ListTablesResult
 *  com.amazonaws.services.dynamodbv2.model.PutItemRequest
 *  com.amazonaws.services.dynamodbv2.model.PutItemResult
 *  com.amazonaws.services.dynamodbv2.model.QueryRequest
 *  com.amazonaws.services.dynamodbv2.model.QueryResult
 *  com.amazonaws.services.dynamodbv2.model.ScanRequest
 *  com.amazonaws.services.dynamodbv2.model.ScanResult
 *  com.amazonaws.services.dynamodbv2.model.TransactGetItemsRequest
 *  com.amazonaws.services.dynamodbv2.model.TransactGetItemsResult
 *  com.amazonaws.services.dynamodbv2.model.TransactWriteItemsRequest
 *  com.amazonaws.services.dynamodbv2.model.TransactWriteItemsResult
 *  com.amazonaws.services.dynamodbv2.model.UpdateItemRequest
 *  com.amazonaws.services.dynamodbv2.model.UpdateItemResult
 *  com.amazonaws.services.dynamodbv2.model.UpdateTableRequest
 *  com.amazonaws.services.dynamodbv2.model.UpdateTableResult
 *  com.amazonaws.services.dynamodbv2.model.UpdateTimeToLiveRequest
 *  com.amazonaws.services.dynamodbv2.model.UpdateTimeToLiveResult
 *  software.amazon.awssdk.awscore.exception.AwsServiceException
 *  software.amazon.awssdk.core.exception.SdkClientException
 *  software.amazon.awssdk.services.dynamodb.DynamoDbClient
 *  software.amazon.awssdk.services.dynamodb.model.BatchExecuteStatementRequest
 *  software.amazon.awssdk.services.dynamodb.model.BatchExecuteStatementResponse
 *  software.amazon.awssdk.services.dynamodb.model.BatchGetItemRequest
 *  software.amazon.awssdk.services.dynamodb.model.BatchGetItemResponse
 *  software.amazon.awssdk.services.dynamodb.model.BatchWriteItemRequest
 *  software.amazon.awssdk.services.dynamodb.model.BatchWriteItemResponse
 *  software.amazon.awssdk.services.dynamodb.model.CreateBackupRequest
 *  software.amazon.awssdk.services.dynamodb.model.CreateBackupResponse
 *  software.amazon.awssdk.services.dynamodb.model.CreateGlobalTableRequest
 *  software.amazon.awssdk.services.dynamodb.model.CreateGlobalTableResponse
 *  software.amazon.awssdk.services.dynamodb.model.CreateTableRequest
 *  software.amazon.awssdk.services.dynamodb.model.CreateTableResponse
 *  software.amazon.awssdk.services.dynamodb.model.DeleteBackupRequest
 *  software.amazon.awssdk.services.dynamodb.model.DeleteBackupResponse
 *  software.amazon.awssdk.services.dynamodb.model.DeleteItemRequest
 *  software.amazon.awssdk.services.dynamodb.model.DeleteItemResponse
 *  software.amazon.awssdk.services.dynamodb.model.DeleteTableRequest
 *  software.amazon.awssdk.services.dynamodb.model.DeleteTableResponse
 *  software.amazon.awssdk.services.dynamodb.model.DescribeBackupRequest
 *  software.amazon.awssdk.services.dynamodb.model.DescribeBackupResponse
 *  software.amazon.awssdk.services.dynamodb.model.DescribeContinuousBackupsRequest
 *  software.amazon.awssdk.services.dynamodb.model.DescribeContinuousBackupsResponse
 *  software.amazon.awssdk.services.dynamodb.model.DescribeContributorInsightsRequest
 *  software.amazon.awssdk.services.dynamodb.model.DescribeContributorInsightsResponse
 *  software.amazon.awssdk.services.dynamodb.model.DescribeEndpointsRequest
 *  software.amazon.awssdk.services.dynamodb.model.DescribeEndpointsResponse
 *  software.amazon.awssdk.services.dynamodb.model.DescribeExportRequest
 *  software.amazon.awssdk.services.dynamodb.model.DescribeExportResponse
 *  software.amazon.awssdk.services.dynamodb.model.DescribeGlobalTableRequest
 *  software.amazon.awssdk.services.dynamodb.model.DescribeGlobalTableResponse
 *  software.amazon.awssdk.services.dynamodb.model.DescribeGlobalTableSettingsRequest
 *  software.amazon.awssdk.services.dynamodb.model.DescribeGlobalTableSettingsResponse
 *  software.amazon.awssdk.services.dynamodb.model.DescribeKinesisStreamingDestinationRequest
 *  software.amazon.awssdk.services.dynamodb.model.DescribeKinesisStreamingDestinationResponse
 *  software.amazon.awssdk.services.dynamodb.model.DescribeLimitsRequest
 *  software.amazon.awssdk.services.dynamodb.model.DescribeLimitsResponse
 *  software.amazon.awssdk.services.dynamodb.model.DescribeTableReplicaAutoScalingRequest
 *  software.amazon.awssdk.services.dynamodb.model.DescribeTableReplicaAutoScalingResponse
 *  software.amazon.awssdk.services.dynamodb.model.DescribeTableRequest
 *  software.amazon.awssdk.services.dynamodb.model.DescribeTableResponse
 *  software.amazon.awssdk.services.dynamodb.model.DescribeTimeToLiveRequest
 *  software.amazon.awssdk.services.dynamodb.model.DescribeTimeToLiveResponse
 *  software.amazon.awssdk.services.dynamodb.model.DisableKinesisStreamingDestinationRequest
 *  software.amazon.awssdk.services.dynamodb.model.DisableKinesisStreamingDestinationResponse
 *  software.amazon.awssdk.services.dynamodb.model.EnableKinesisStreamingDestinationRequest
 *  software.amazon.awssdk.services.dynamodb.model.EnableKinesisStreamingDestinationResponse
 *  software.amazon.awssdk.services.dynamodb.model.ExecuteStatementRequest
 *  software.amazon.awssdk.services.dynamodb.model.ExecuteStatementResponse
 *  software.amazon.awssdk.services.dynamodb.model.ExecuteTransactionRequest
 *  software.amazon.awssdk.services.dynamodb.model.ExecuteTransactionResponse
 *  software.amazon.awssdk.services.dynamodb.model.ExportTableToPointInTimeRequest
 *  software.amazon.awssdk.services.dynamodb.model.ExportTableToPointInTimeResponse
 *  software.amazon.awssdk.services.dynamodb.model.GetItemRequest
 *  software.amazon.awssdk.services.dynamodb.model.GetItemResponse
 *  software.amazon.awssdk.services.dynamodb.model.ListBackupsRequest
 *  software.amazon.awssdk.services.dynamodb.model.ListBackupsResponse
 *  software.amazon.awssdk.services.dynamodb.model.ListContributorInsightsRequest
 *  software.amazon.awssdk.services.dynamodb.model.ListContributorInsightsResponse
 *  software.amazon.awssdk.services.dynamodb.model.ListExportsRequest
 *  software.amazon.awssdk.services.dynamodb.model.ListExportsResponse
 *  software.amazon.awssdk.services.dynamodb.model.ListGlobalTablesRequest
 *  software.amazon.awssdk.services.dynamodb.model.ListGlobalTablesResponse
 *  software.amazon.awssdk.services.dynamodb.model.ListTablesRequest
 *  software.amazon.awssdk.services.dynamodb.model.ListTablesResponse
 *  software.amazon.awssdk.services.dynamodb.model.ListTagsOfResourceRequest
 *  software.amazon.awssdk.services.dynamodb.model.ListTagsOfResourceResponse
 *  software.amazon.awssdk.services.dynamodb.model.PutItemRequest
 *  software.amazon.awssdk.services.dynamodb.model.PutItemResponse
 *  software.amazon.awssdk.services.dynamodb.model.QueryRequest
 *  software.amazon.awssdk.services.dynamodb.model.QueryResponse
 *  software.amazon.awssdk.services.dynamodb.model.RestoreTableFromBackupRequest
 *  software.amazon.awssdk.services.dynamodb.model.RestoreTableFromBackupResponse
 *  software.amazon.awssdk.services.dynamodb.model.RestoreTableToPointInTimeRequest
 *  software.amazon.awssdk.services.dynamodb.model.RestoreTableToPointInTimeResponse
 *  software.amazon.awssdk.services.dynamodb.model.ScanRequest
 *  software.amazon.awssdk.services.dynamodb.model.ScanResponse
 *  software.amazon.awssdk.services.dynamodb.model.TagResourceRequest
 *  software.amazon.awssdk.services.dynamodb.model.TagResourceResponse
 *  software.amazon.awssdk.services.dynamodb.model.TransactGetItemsRequest
 *  software.amazon.awssdk.services.dynamodb.model.TransactGetItemsResponse
 *  software.amazon.awssdk.services.dynamodb.model.TransactWriteItemsRequest
 *  software.amazon.awssdk.services.dynamodb.model.TransactWriteItemsResponse
 *  software.amazon.awssdk.services.dynamodb.model.UntagResourceRequest
 *  software.amazon.awssdk.services.dynamodb.model.UntagResourceResponse
 *  software.amazon.awssdk.services.dynamodb.model.UpdateContinuousBackupsRequest
 *  software.amazon.awssdk.services.dynamodb.model.UpdateContinuousBackupsResponse
 *  software.amazon.awssdk.services.dynamodb.model.UpdateContributorInsightsRequest
 *  software.amazon.awssdk.services.dynamodb.model.UpdateContributorInsightsResponse
 *  software.amazon.awssdk.services.dynamodb.model.UpdateGlobalTableRequest
 *  software.amazon.awssdk.services.dynamodb.model.UpdateGlobalTableResponse
 *  software.amazon.awssdk.services.dynamodb.model.UpdateGlobalTableSettingsRequest
 *  software.amazon.awssdk.services.dynamodb.model.UpdateGlobalTableSettingsResponse
 *  software.amazon.awssdk.services.dynamodb.model.UpdateItemRequest
 *  software.amazon.awssdk.services.dynamodb.model.UpdateItemResponse
 *  software.amazon.awssdk.services.dynamodb.model.UpdateTableReplicaAutoScalingRequest
 *  software.amazon.awssdk.services.dynamodb.model.UpdateTableReplicaAutoScalingResponse
 *  software.amazon.awssdk.services.dynamodb.model.UpdateTableRequest
 *  software.amazon.awssdk.services.dynamodb.model.UpdateTableResponse
 *  software.amazon.awssdk.services.dynamodb.model.UpdateTimeToLiveRequest
 *  software.amazon.awssdk.services.dynamodb.model.UpdateTimeToLiveResponse
 *  software.amazon.awssdk.services.dynamodb.paginators.BatchGetItemIterable
 *  software.amazon.awssdk.services.dynamodb.paginators.ListContributorInsightsIterable
 *  software.amazon.awssdk.services.dynamodb.paginators.ListExportsIterable
 *  software.amazon.awssdk.services.dynamodb.paginators.ListTablesIterable
 *  software.amazon.awssdk.services.dynamodb.paginators.QueryIterable
 *  software.amazon.awssdk.services.dynamodb.paginators.ScanIterable
 *  software.amazon.awssdk.services.dynamodb.waiters.DynamoDbWaiter
 *  software.amazon.awssdk.utils.builder.SdkBuilder
 */
package com.amazonaws.services.dynamodbv2.local.shared.access.awssdkv2.client;

import com.amazonaws.services.dynamodbv2.exceptions.AWSExceptionFactory;
import com.amazonaws.services.dynamodbv2.exceptions.AmazonServiceExceptionType;
import com.amazonaws.services.dynamodbv2.exceptions.DynamoDBLocalServiceException;
import com.amazonaws.services.dynamodbv2.local.shared.access.awssdkv1.client.LocalAmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.local.shared.access.awssdkv2.client.LocalDynamoDbClientBase;
import com.amazonaws.services.dynamodbv2.local.shared.access.awssdkv2.client.PaginatorUtils;
import com.amazonaws.services.dynamodbv2.local.shared.exceptions.LocalDBClientExceptionMessage;
import com.amazonaws.services.dynamodbv2.model.BatchExecuteStatementRequest;
import com.amazonaws.services.dynamodbv2.model.BatchExecuteStatementResult;
import com.amazonaws.services.dynamodbv2.model.BatchGetItemResult;
import com.amazonaws.services.dynamodbv2.model.BatchWriteItemResult;
import com.amazonaws.services.dynamodbv2.model.CreateTableResult;
import com.amazonaws.services.dynamodbv2.model.DeleteItemResult;
import com.amazonaws.services.dynamodbv2.model.DeleteTableRequest;
import com.amazonaws.services.dynamodbv2.model.DeleteTableResult;
import com.amazonaws.services.dynamodbv2.model.DescribeLimitsRequest;
import com.amazonaws.services.dynamodbv2.model.DescribeLimitsResult;
import com.amazonaws.services.dynamodbv2.model.DescribeTableResult;
import com.amazonaws.services.dynamodbv2.model.DescribeTimeToLiveResult;
import com.amazonaws.services.dynamodbv2.model.ExecuteStatementRequest;
import com.amazonaws.services.dynamodbv2.model.ExecuteStatementResult;
import com.amazonaws.services.dynamodbv2.model.ExecuteTransactionRequest;
import com.amazonaws.services.dynamodbv2.model.ExecuteTransactionResult;
import com.amazonaws.services.dynamodbv2.model.GetItemResult;
import com.amazonaws.services.dynamodbv2.model.ListTablesResult;
import com.amazonaws.services.dynamodbv2.model.PutItemRequest;
import com.amazonaws.services.dynamodbv2.model.PutItemResult;
import com.amazonaws.services.dynamodbv2.model.QueryRequest;
import com.amazonaws.services.dynamodbv2.model.QueryResult;
import com.amazonaws.services.dynamodbv2.model.ScanRequest;
import com.amazonaws.services.dynamodbv2.model.ScanResult;
import com.amazonaws.services.dynamodbv2.model.TransactGetItemsRequest;
import com.amazonaws.services.dynamodbv2.model.TransactGetItemsResult;
import com.amazonaws.services.dynamodbv2.model.TransactWriteItemsResult;
import com.amazonaws.services.dynamodbv2.model.UpdateItemResult;
import com.amazonaws.services.dynamodbv2.model.UpdateTableRequest;
import com.amazonaws.services.dynamodbv2.model.UpdateTableResult;
import com.amazonaws.services.dynamodbv2.model.UpdateTimeToLiveResult;
import software.amazon.awssdk.awscore.exception.AwsServiceException;
import software.amazon.awssdk.core.exception.SdkClientException;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.BatchExecuteStatementResponse;
import software.amazon.awssdk.services.dynamodb.model.BatchGetItemRequest;
import software.amazon.awssdk.services.dynamodb.model.BatchGetItemResponse;
import software.amazon.awssdk.services.dynamodb.model.BatchWriteItemRequest;
import software.amazon.awssdk.services.dynamodb.model.BatchWriteItemResponse;
import software.amazon.awssdk.services.dynamodb.model.CreateBackupRequest;
import software.amazon.awssdk.services.dynamodb.model.CreateBackupResponse;
import software.amazon.awssdk.services.dynamodb.model.CreateGlobalTableRequest;
import software.amazon.awssdk.services.dynamodb.model.CreateGlobalTableResponse;
import software.amazon.awssdk.services.dynamodb.model.CreateTableRequest;
import software.amazon.awssdk.services.dynamodb.model.CreateTableResponse;
import software.amazon.awssdk.services.dynamodb.model.DeleteBackupRequest;
import software.amazon.awssdk.services.dynamodb.model.DeleteBackupResponse;
import software.amazon.awssdk.services.dynamodb.model.DeleteItemRequest;
import software.amazon.awssdk.services.dynamodb.model.DeleteItemResponse;
import software.amazon.awssdk.services.dynamodb.model.DeleteTableResponse;
import software.amazon.awssdk.services.dynamodb.model.DescribeBackupRequest;
import software.amazon.awssdk.services.dynamodb.model.DescribeBackupResponse;
import software.amazon.awssdk.services.dynamodb.model.DescribeContinuousBackupsRequest;
import software.amazon.awssdk.services.dynamodb.model.DescribeContinuousBackupsResponse;
import software.amazon.awssdk.services.dynamodb.model.DescribeContributorInsightsRequest;
import software.amazon.awssdk.services.dynamodb.model.DescribeContributorInsightsResponse;
import software.amazon.awssdk.services.dynamodb.model.DescribeEndpointsRequest;
import software.amazon.awssdk.services.dynamodb.model.DescribeEndpointsResponse;
import software.amazon.awssdk.services.dynamodb.model.DescribeExportRequest;
import software.amazon.awssdk.services.dynamodb.model.DescribeExportResponse;
import software.amazon.awssdk.services.dynamodb.model.DescribeGlobalTableRequest;
import software.amazon.awssdk.services.dynamodb.model.DescribeGlobalTableResponse;
import software.amazon.awssdk.services.dynamodb.model.DescribeGlobalTableSettingsRequest;
import software.amazon.awssdk.services.dynamodb.model.DescribeGlobalTableSettingsResponse;
import software.amazon.awssdk.services.dynamodb.model.DescribeKinesisStreamingDestinationRequest;
import software.amazon.awssdk.services.dynamodb.model.DescribeKinesisStreamingDestinationResponse;
import software.amazon.awssdk.services.dynamodb.model.DescribeLimitsResponse;
import software.amazon.awssdk.services.dynamodb.model.DescribeTableReplicaAutoScalingRequest;
import software.amazon.awssdk.services.dynamodb.model.DescribeTableReplicaAutoScalingResponse;
import software.amazon.awssdk.services.dynamodb.model.DescribeTableRequest;
import software.amazon.awssdk.services.dynamodb.model.DescribeTableResponse;
import software.amazon.awssdk.services.dynamodb.model.DescribeTimeToLiveRequest;
import software.amazon.awssdk.services.dynamodb.model.DescribeTimeToLiveResponse;
import software.amazon.awssdk.services.dynamodb.model.DisableKinesisStreamingDestinationRequest;
import software.amazon.awssdk.services.dynamodb.model.DisableKinesisStreamingDestinationResponse;
import software.amazon.awssdk.services.dynamodb.model.EnableKinesisStreamingDestinationRequest;
import software.amazon.awssdk.services.dynamodb.model.EnableKinesisStreamingDestinationResponse;
import software.amazon.awssdk.services.dynamodb.model.ExecuteStatementResponse;
import software.amazon.awssdk.services.dynamodb.model.ExecuteTransactionResponse;
import software.amazon.awssdk.services.dynamodb.model.ExportTableToPointInTimeRequest;
import software.amazon.awssdk.services.dynamodb.model.ExportTableToPointInTimeResponse;
import software.amazon.awssdk.services.dynamodb.model.GetItemRequest;
import software.amazon.awssdk.services.dynamodb.model.GetItemResponse;
import software.amazon.awssdk.services.dynamodb.model.ListBackupsRequest;
import software.amazon.awssdk.services.dynamodb.model.ListBackupsResponse;
import software.amazon.awssdk.services.dynamodb.model.ListContributorInsightsRequest;
import software.amazon.awssdk.services.dynamodb.model.ListContributorInsightsResponse;
import software.amazon.awssdk.services.dynamodb.model.ListExportsRequest;
import software.amazon.awssdk.services.dynamodb.model.ListExportsResponse;
import software.amazon.awssdk.services.dynamodb.model.ListGlobalTablesRequest;
import software.amazon.awssdk.services.dynamodb.model.ListGlobalTablesResponse;
import software.amazon.awssdk.services.dynamodb.model.ListTablesRequest;
import software.amazon.awssdk.services.dynamodb.model.ListTablesResponse;
import software.amazon.awssdk.services.dynamodb.model.ListTagsOfResourceRequest;
import software.amazon.awssdk.services.dynamodb.model.ListTagsOfResourceResponse;
import software.amazon.awssdk.services.dynamodb.model.PutItemResponse;
import software.amazon.awssdk.services.dynamodb.model.QueryResponse;
import software.amazon.awssdk.services.dynamodb.model.RestoreTableFromBackupRequest;
import software.amazon.awssdk.services.dynamodb.model.RestoreTableFromBackupResponse;
import software.amazon.awssdk.services.dynamodb.model.RestoreTableToPointInTimeRequest;
import software.amazon.awssdk.services.dynamodb.model.RestoreTableToPointInTimeResponse;
import software.amazon.awssdk.services.dynamodb.model.ScanResponse;
import software.amazon.awssdk.services.dynamodb.model.TagResourceRequest;
import software.amazon.awssdk.services.dynamodb.model.TagResourceResponse;
import software.amazon.awssdk.services.dynamodb.model.TransactGetItemsResponse;
import software.amazon.awssdk.services.dynamodb.model.TransactWriteItemsRequest;
import software.amazon.awssdk.services.dynamodb.model.TransactWriteItemsResponse;
import software.amazon.awssdk.services.dynamodb.model.UntagResourceRequest;
import software.amazon.awssdk.services.dynamodb.model.UntagResourceResponse;
import software.amazon.awssdk.services.dynamodb.model.UpdateContinuousBackupsRequest;
import software.amazon.awssdk.services.dynamodb.model.UpdateContinuousBackupsResponse;
import software.amazon.awssdk.services.dynamodb.model.UpdateContributorInsightsRequest;
import software.amazon.awssdk.services.dynamodb.model.UpdateContributorInsightsResponse;
import software.amazon.awssdk.services.dynamodb.model.UpdateGlobalTableRequest;
import software.amazon.awssdk.services.dynamodb.model.UpdateGlobalTableResponse;
import software.amazon.awssdk.services.dynamodb.model.UpdateGlobalTableSettingsRequest;
import software.amazon.awssdk.services.dynamodb.model.UpdateGlobalTableSettingsResponse;
import software.amazon.awssdk.services.dynamodb.model.UpdateItemRequest;
import software.amazon.awssdk.services.dynamodb.model.UpdateItemResponse;
import software.amazon.awssdk.services.dynamodb.model.UpdateTableReplicaAutoScalingRequest;
import software.amazon.awssdk.services.dynamodb.model.UpdateTableReplicaAutoScalingResponse;
import software.amazon.awssdk.services.dynamodb.model.UpdateTableResponse;
import software.amazon.awssdk.services.dynamodb.model.UpdateTimeToLiveRequest;
import software.amazon.awssdk.services.dynamodb.model.UpdateTimeToLiveResponse;
import software.amazon.awssdk.services.dynamodb.paginators.BatchGetItemIterable;
import software.amazon.awssdk.services.dynamodb.paginators.ListContributorInsightsIterable;
import software.amazon.awssdk.services.dynamodb.paginators.ListExportsIterable;
import software.amazon.awssdk.services.dynamodb.paginators.ListTablesIterable;
import software.amazon.awssdk.services.dynamodb.paginators.QueryIterable;
import software.amazon.awssdk.services.dynamodb.paginators.ScanIterable;
import software.amazon.awssdk.services.dynamodb.waiters.DynamoDbWaiter;
import software.amazon.awssdk.utils.builder.SdkBuilder;

public class LocalDynamoDbClient
extends LocalDynamoDbClientBase
implements DynamoDbClient {
    private final LocalAmazonDynamoDB dynamoDBSdkV1;
    private final String SERVICE_NAME = "dynamodb";

    public LocalDynamoDbClient(LocalAmazonDynamoDB dynamoDBSdkV1) {
        this.dynamoDBSdkV1 = dynamoDBSdkV1;
    }

    public String serviceName() {
        return "dynamodb";
    }

    public void close() {
        this.dynamoDBSdkV1.shutdown();
    }

    public BatchExecuteStatementResponse batchExecuteStatement(software.amazon.awssdk.services.dynamodb.model.BatchExecuteStatementRequest batchExecuteStatementRequest) throws AwsServiceException, SdkClientException {
        BatchExecuteStatementResult batchExecuteStatementResult = this.dynamoDBSdkV1.batchExecuteStatement(this.batchExecuteStatementRequestConverter.toSdkV1(batchExecuteStatementRequest, software.amazon.awssdk.services.dynamodb.model.BatchExecuteStatementRequest::toBuilder, BatchExecuteStatementRequest.class));
        return this.batchExecuteStatementResponseConverter.toSdkV2(batchExecuteStatementResult, BatchExecuteStatementResponse::serializableBuilderClass, SdkBuilder::build);
    }

    public BatchGetItemResponse batchGetItem(BatchGetItemRequest batchGetItemRequest) throws AwsServiceException, SdkClientException {
        BatchGetItemResult batchGetItemResult = this.dynamoDBSdkV1.batchGetItem(this.batchGetItemRequestRequestConverter.toSdkV1(batchGetItemRequest, BatchGetItemRequest::toBuilder, com.amazonaws.services.dynamodbv2.model.BatchGetItemRequest.class));
        return this.batchGetItemResponseConverter.toSdkV2(batchGetItemResult, BatchGetItemResponse::serializableBuilderClass, SdkBuilder::build);
    }

    public BatchGetItemIterable batchGetItemPaginator(BatchGetItemRequest batchGetItemRequest) throws AwsServiceException, SdkClientException {
        return new BatchGetItemIterable((DynamoDbClient)this, PaginatorUtils.applyPaginatorUserAgent(batchGetItemRequest));
    }

    public BatchWriteItemResponse batchWriteItem(BatchWriteItemRequest batchWriteItemRequest) throws AwsServiceException, SdkClientException {
        BatchWriteItemResult batchWriteItemResult = this.dynamoDBSdkV1.batchWriteItem(this.batchWriteItemRequestConverter.toSdkV1(batchWriteItemRequest, BatchWriteItemRequest::toBuilder, com.amazonaws.services.dynamodbv2.model.BatchWriteItemRequest.class));
        return this.batchWriteItemResponseConverter.toSdkV2(batchWriteItemResult, BatchWriteItemResponse::serializableBuilderClass, SdkBuilder::build);
    }

    public CreateBackupResponse createBackup(CreateBackupRequest createBackupRequest) throws AwsServiceException, SdkClientException {
        throw this.unknownOperationException(LocalDBClientExceptionMessage.BACKUP_OPERATION_NOT_SUPPORTED);
    }

    public CreateGlobalTableResponse createGlobalTable(CreateGlobalTableRequest createGlobalTableRequest) throws AwsServiceException, SdkClientException {
        throw this.unknownOperationException(LocalDBClientExceptionMessage.GLOBAL_TABLE_OPERATION_NOT_SUPPORTED);
    }

    public CreateTableResponse createTable(CreateTableRequest createTableRequest) throws AwsServiceException, SdkClientException {
        CreateTableResult createTableResult = this.dynamoDBSdkV1.createTable(this.createTableReqConverter.toSdkV1(createTableRequest, CreateTableRequest::toBuilder, com.amazonaws.services.dynamodbv2.model.CreateTableRequest.class));
        return this.createTableResponseConverter.toSdkV2(createTableResult, CreateTableResponse::serializableBuilderClass, SdkBuilder::build);
    }

    public DeleteBackupResponse deleteBackup(DeleteBackupRequest deleteBackupRequest) throws AwsServiceException, SdkClientException {
        throw this.unknownOperationException(LocalDBClientExceptionMessage.BACKUP_OPERATION_NOT_SUPPORTED);
    }

    public DeleteItemResponse deleteItem(DeleteItemRequest deleteItemRequest) throws AwsServiceException, SdkClientException {
        DeleteItemResult deleteItemResult = this.dynamoDBSdkV1.deleteItem(this.deleteItemRequestConverter.toSdkV1(deleteItemRequest, DeleteItemRequest::toBuilder, com.amazonaws.services.dynamodbv2.model.DeleteItemRequest.class));
        return this.deleteItemResponseConverter.toSdkV2(deleteItemResult, DeleteItemResponse::serializableBuilderClass, SdkBuilder::build);
    }

    public DeleteTableResponse deleteTable(software.amazon.awssdk.services.dynamodb.model.DeleteTableRequest deleteTableRequest) throws AwsServiceException, SdkClientException {
        DeleteTableResult deleteTableResult = this.dynamoDBSdkV1.deleteTable(this.deleteTableRequestConverter.toSdkV1(deleteTableRequest, software.amazon.awssdk.services.dynamodb.model.DeleteTableRequest::toBuilder, DeleteTableRequest.class));
        return this.deleteTableResponseConverter.toSdkV2(deleteTableResult, DeleteTableResponse::serializableBuilderClass, SdkBuilder::build);
    }

    public DescribeBackupResponse describeBackup(DescribeBackupRequest describeBackupRequest) throws AwsServiceException, SdkClientException {
        throw this.unknownOperationException(LocalDBClientExceptionMessage.BACKUP_OPERATION_NOT_SUPPORTED);
    }

    public DescribeContinuousBackupsResponse describeContinuousBackups(DescribeContinuousBackupsRequest describeContinuousBackupsRequest) throws AwsServiceException, SdkClientException {
        throw this.unknownOperationException(LocalDBClientExceptionMessage.CONTINUOUS_BACKUP_OPERATION_NOT_SUPPORTED);
    }

    public DescribeContributorInsightsResponse describeContributorInsights(DescribeContributorInsightsRequest describeContributorInsightsRequest) throws AwsServiceException, SdkClientException {
        throw this.unknownOperationException(LocalDBClientExceptionMessage.CONTRIBUTOR_INSIGHTS_OPERATION_NOT_SUPPORTED);
    }

    public DescribeEndpointsResponse describeEndpoints() throws AwsServiceException, SdkClientException {
        throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.INVALID_ACTION, LocalDBClientExceptionMessage.ENDPOINT_OPERATION_NOT_SUPPORTED.getMessage());
    }

    public DescribeEndpointsResponse describeEndpoints(DescribeEndpointsRequest describeEndpointsRequest) throws AwsServiceException, SdkClientException {
        throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.INVALID_ACTION, LocalDBClientExceptionMessage.ENDPOINT_OPERATION_NOT_SUPPORTED.getMessage());
    }

    public DescribeExportResponse describeExport(DescribeExportRequest describeExportRequest) throws AwsServiceException, SdkClientException {
        throw this.unknownOperationException(LocalDBClientExceptionMessage.EXPORT_OPERATION_NOT_SUPPORTED);
    }

    public DescribeGlobalTableResponse describeGlobalTable(DescribeGlobalTableRequest describeGlobalTableRequest) throws AwsServiceException, SdkClientException {
        throw this.unknownOperationException(LocalDBClientExceptionMessage.GLOBAL_TABLE_OPERATION_NOT_SUPPORTED);
    }

    public DescribeGlobalTableSettingsResponse describeGlobalTableSettings(DescribeGlobalTableSettingsRequest describeGlobalTableSettingsRequest) throws AwsServiceException, SdkClientException {
        throw this.unknownOperationException(LocalDBClientExceptionMessage.GLOBAL_TABLE_OPERATION_NOT_SUPPORTED);
    }

    public DescribeKinesisStreamingDestinationResponse describeKinesisStreamingDestination(DescribeKinesisStreamingDestinationRequest describeKinesisStreamingDestinationRequest) throws AwsServiceException, SdkClientException {
        throw this.unknownOperationException(LocalDBClientExceptionMessage.KINESIS_STREAMING_OPERATION_NOT_SUPPORTED);
    }

    public DescribeLimitsResponse describeLimits(software.amazon.awssdk.services.dynamodb.model.DescribeLimitsRequest describeLimitsRequest) throws AwsServiceException, SdkClientException {
        DescribeLimitsResult describeLimitsResult = this.dynamoDBSdkV1.describeLimits(this.describeLimitsRequestConverter.toSdkV1(describeLimitsRequest, software.amazon.awssdk.services.dynamodb.model.DescribeLimitsRequest::toBuilder, DescribeLimitsRequest.class));
        return this.describeLimitsResponseConverter.toSdkV2(describeLimitsResult, DescribeLimitsResponse::serializableBuilderClass, SdkBuilder::build);
    }

    public DescribeTableResponse describeTable(DescribeTableRequest describeTableRequest) throws AwsServiceException, SdkClientException {
        DescribeTableResult describeTableResult = this.dynamoDBSdkV1.describeTable(this.describeTableRequestConverter.toSdkV1(describeTableRequest, DescribeTableRequest::toBuilder, com.amazonaws.services.dynamodbv2.model.DescribeTableRequest.class));
        return this.describeTableResponseConverter.toSdkV2(describeTableResult, DescribeTableResponse::serializableBuilderClass, SdkBuilder::build);
    }

    public DescribeTableReplicaAutoScalingResponse describeTableReplicaAutoScaling(DescribeTableReplicaAutoScalingRequest describeTableReplicaAutoScalingRequest) throws AwsServiceException, SdkClientException {
        throw this.unknownOperationException(LocalDBClientExceptionMessage.TABLE_REPLICA_AUTO_SCALING_OPERATION_NOT_SUPPORTED);
    }

    public DescribeTimeToLiveResponse describeTimeToLive(DescribeTimeToLiveRequest describeTimeToLiveRequest) throws AwsServiceException, SdkClientException {
        DescribeTimeToLiveResult describeTimeToLiveResult = this.dynamoDBSdkV1.describeTimeToLive(this.describeTimeToLiveRequestConverter.toSdkV1(describeTimeToLiveRequest, DescribeTimeToLiveRequest::toBuilder, com.amazonaws.services.dynamodbv2.model.DescribeTimeToLiveRequest.class));
        return this.describeTimeToLiveResponseConverter.toSdkV2(describeTimeToLiveResult, DescribeTimeToLiveResponse::serializableBuilderClass, SdkBuilder::build);
    }

    public DisableKinesisStreamingDestinationResponse disableKinesisStreamingDestination(DisableKinesisStreamingDestinationRequest disableKinesisStreamingDestinationRequest) throws AwsServiceException, SdkClientException {
        throw this.unknownOperationException(LocalDBClientExceptionMessage.KINESIS_STREAMING_OPERATION_NOT_SUPPORTED);
    }

    public EnableKinesisStreamingDestinationResponse enableKinesisStreamingDestination(EnableKinesisStreamingDestinationRequest enableKinesisStreamingDestinationRequest) throws AwsServiceException, SdkClientException {
        throw this.unknownOperationException(LocalDBClientExceptionMessage.KINESIS_STREAMING_OPERATION_NOT_SUPPORTED);
    }

    public ExecuteStatementResponse executeStatement(software.amazon.awssdk.services.dynamodb.model.ExecuteStatementRequest executeStatementRequest) throws AwsServiceException, SdkClientException {
        ExecuteStatementResult executeStatementResult = this.dynamoDBSdkV1.executeStatement(this.executeStatementRequestConverter.toSdkV1(executeStatementRequest, software.amazon.awssdk.services.dynamodb.model.ExecuteStatementRequest::toBuilder, ExecuteStatementRequest.class));
        return this.executeStatementResponseConverter.toSdkV2(executeStatementResult, ExecuteStatementResponse::serializableBuilderClass, SdkBuilder::build);
    }

    public ExecuteTransactionResponse executeTransaction(software.amazon.awssdk.services.dynamodb.model.ExecuteTransactionRequest executeTransactionRequest) throws AwsServiceException, SdkClientException {
        ExecuteTransactionResult executeTransactionResult = this.dynamoDBSdkV1.executeTransaction(this.executeTransactionRequestConverter.toSdkV1(executeTransactionRequest, software.amazon.awssdk.services.dynamodb.model.ExecuteTransactionRequest::toBuilder, ExecuteTransactionRequest.class));
        return this.executeTransactionResponseConverter.toSdkV2(executeTransactionResult, ExecuteTransactionResponse::serializableBuilderClass, SdkBuilder::build);
    }

    public ExportTableToPointInTimeResponse exportTableToPointInTime(ExportTableToPointInTimeRequest exportTableToPointInTimeRequest) throws AwsServiceException, SdkClientException {
        throw this.unknownOperationException(LocalDBClientExceptionMessage.EXPORT_OPERATION_NOT_SUPPORTED);
    }

    public GetItemResponse getItem(GetItemRequest getItemRequest) throws AwsServiceException, SdkClientException {
        GetItemResult getItemResult = this.dynamoDBSdkV1.getItem(this.getItemRequestConverter.toSdkV1(getItemRequest, GetItemRequest::toBuilder, com.amazonaws.services.dynamodbv2.model.GetItemRequest.class));
        return this.getItemResponseConverter.toSdkV2(getItemResult, GetItemResponse::serializableBuilderClass, SdkBuilder::build);
    }

    public ListBackupsResponse listBackups() throws AwsServiceException, SdkClientException {
        throw this.unknownOperationException(LocalDBClientExceptionMessage.BACKUP_OPERATION_NOT_SUPPORTED);
    }

    public ListBackupsResponse listBackups(ListBackupsRequest listBackupsRequest) throws AwsServiceException, SdkClientException {
        throw this.unknownOperationException(LocalDBClientExceptionMessage.BACKUP_OPERATION_NOT_SUPPORTED);
    }

    public ListContributorInsightsResponse listContributorInsights(ListContributorInsightsRequest listContributorInsightsRequest) throws AwsServiceException, SdkClientException {
        throw this.unknownOperationException(LocalDBClientExceptionMessage.CONTRIBUTOR_INSIGHTS_OPERATION_NOT_SUPPORTED);
    }

    public ListContributorInsightsIterable listContributorInsightsPaginator(ListContributorInsightsRequest listContributorInsightsRequest) throws AwsServiceException, SdkClientException {
        throw this.unknownOperationException(LocalDBClientExceptionMessage.CONTRIBUTOR_INSIGHTS_OPERATION_NOT_SUPPORTED);
    }

    public ListExportsResponse listExports(ListExportsRequest listExportsRequest) throws AwsServiceException, SdkClientException {
        throw this.unknownOperationException(LocalDBClientExceptionMessage.EXPORT_OPERATION_NOT_SUPPORTED);
    }

    public ListExportsIterable listExportsPaginator(ListExportsRequest listExportsRequest) throws AwsServiceException, SdkClientException {
        throw this.unknownOperationException(LocalDBClientExceptionMessage.EXPORT_OPERATION_NOT_SUPPORTED);
    }

    public ListGlobalTablesResponse listGlobalTables() throws AwsServiceException, SdkClientException {
        throw this.unknownOperationException(LocalDBClientExceptionMessage.GLOBAL_TABLE_OPERATION_NOT_SUPPORTED);
    }

    public ListGlobalTablesResponse listGlobalTables(ListGlobalTablesRequest listGlobalTablesRequest) throws AwsServiceException, SdkClientException {
        throw this.unknownOperationException(LocalDBClientExceptionMessage.GLOBAL_TABLE_OPERATION_NOT_SUPPORTED);
    }

    public ListTablesResponse listTables() throws AwsServiceException, SdkClientException {
        ListTablesResult listTablesResult = this.dynamoDBSdkV1.listTables();
        return this.listTablesResponseConverter.toSdkV2(listTablesResult, ListTablesResponse::serializableBuilderClass, SdkBuilder::build);
    }

    public ListTablesResponse listTables(ListTablesRequest listTablesRequest) throws AwsServiceException, SdkClientException {
        ListTablesResult listTablesResult = this.dynamoDBSdkV1.listTables(this.listTablesRequestConverter.toSdkV1(listTablesRequest, ListTablesRequest::toBuilder, com.amazonaws.services.dynamodbv2.model.ListTablesRequest.class));
        return this.listTablesResponseConverter.toSdkV2(listTablesResult, ListTablesResponse::serializableBuilderClass, SdkBuilder::build);
    }

    public ListTablesIterable listTablesPaginator(ListTablesRequest listTablesRequest) throws AwsServiceException, SdkClientException {
        return new ListTablesIterable((DynamoDbClient)this, PaginatorUtils.applyPaginatorUserAgent(listTablesRequest));
    }

    public ListTagsOfResourceResponse listTagsOfResource(ListTagsOfResourceRequest listTagsOfResourceRequest) throws AwsServiceException, SdkClientException {
        throw this.unknownOperationException(LocalDBClientExceptionMessage.TAGGING_NOT_SUPPORTED);
    }

    public PutItemResponse putItem(software.amazon.awssdk.services.dynamodb.model.PutItemRequest putItemRequest) throws AwsServiceException, SdkClientException {
        PutItemResult putItemResult = this.dynamoDBSdkV1.putItem(this.putItemRequestConverter.toSdkV1(putItemRequest, software.amazon.awssdk.services.dynamodb.model.PutItemRequest::toBuilder, PutItemRequest.class));
        return this.putItemResponseConverter.toSdkV2(putItemResult, PutItemResponse::serializableBuilderClass, SdkBuilder::build);
    }

    public QueryResponse query(software.amazon.awssdk.services.dynamodb.model.QueryRequest queryRequest) throws AwsServiceException, SdkClientException {
        QueryResult queryResult = this.dynamoDBSdkV1.query(this.queryRequestConverter.toSdkV1(queryRequest, software.amazon.awssdk.services.dynamodb.model.QueryRequest::toBuilder, QueryRequest.class));
        return this.queryResponseConverter.toSdkV2(queryResult, QueryResponse::serializableBuilderClass, SdkBuilder::build);
    }

    public QueryIterable queryPaginator(software.amazon.awssdk.services.dynamodb.model.QueryRequest queryRequest) throws AwsServiceException, SdkClientException {
        return new QueryIterable((DynamoDbClient)this, PaginatorUtils.applyPaginatorUserAgent(queryRequest));
    }

    public RestoreTableFromBackupResponse restoreTableFromBackup(RestoreTableFromBackupRequest restoreTableFromBackupRequest) throws AwsServiceException, SdkClientException {
        throw this.unknownOperationException(LocalDBClientExceptionMessage.RECOVERY_OPERATION_NOT_SUPPORTED);
    }

    public RestoreTableToPointInTimeResponse restoreTableToPointInTime(RestoreTableToPointInTimeRequest restoreTableToPointInTimeRequest) throws AwsServiceException, SdkClientException {
        throw this.unknownOperationException(LocalDBClientExceptionMessage.RECOVERY_OPERATION_NOT_SUPPORTED);
    }

    public ScanResponse scan(software.amazon.awssdk.services.dynamodb.model.ScanRequest scanRequest) throws AwsServiceException, SdkClientException {
        ScanResult scanResult = this.dynamoDBSdkV1.scan(this.scanRequestConverter.toSdkV1(scanRequest, software.amazon.awssdk.services.dynamodb.model.ScanRequest::toBuilder, ScanRequest.class));
        return this.scanResponseConverter.toSdkV2(scanResult, ScanResponse::serializableBuilderClass, SdkBuilder::build);
    }

    public ScanIterable scanPaginator(software.amazon.awssdk.services.dynamodb.model.ScanRequest scanRequest) throws AwsServiceException, SdkClientException {
        return new ScanIterable((DynamoDbClient)this, PaginatorUtils.applyPaginatorUserAgent(scanRequest));
    }

    public TagResourceResponse tagResource(TagResourceRequest tagResourceRequest) throws AwsServiceException, SdkClientException {
        throw this.unknownOperationException(LocalDBClientExceptionMessage.TAGGING_NOT_SUPPORTED);
    }

    public TransactGetItemsResponse transactGetItems(software.amazon.awssdk.services.dynamodb.model.TransactGetItemsRequest transactGetItemsRequest) throws AwsServiceException, SdkClientException {
        TransactGetItemsResult transactGetItemsResult = this.dynamoDBSdkV1.transactGetItems(this.transactGetItemsRequestConverter.toSdkV1(transactGetItemsRequest, software.amazon.awssdk.services.dynamodb.model.TransactGetItemsRequest::toBuilder, TransactGetItemsRequest.class));
        return this.transactGetItemsResponseConverter.toSdkV2(transactGetItemsResult, TransactGetItemsResponse::serializableBuilderClass, SdkBuilder::build);
    }

    public TransactWriteItemsResponse transactWriteItems(TransactWriteItemsRequest transactWriteItemsRequest) throws AwsServiceException, SdkClientException {
        TransactWriteItemsResult transactWriteItemsResult = this.dynamoDBSdkV1.transactWriteItems(this.transactWriteItemsRequestConverter.toSdkV1(transactWriteItemsRequest, TransactWriteItemsRequest::toBuilder, com.amazonaws.services.dynamodbv2.model.TransactWriteItemsRequest.class));
        return this.transactWriteItemsResponseConverter.toSdkV2(transactWriteItemsResult, TransactWriteItemsResponse::serializableBuilderClass, SdkBuilder::build);
    }

    public UntagResourceResponse untagResource(UntagResourceRequest untagResourceRequest) throws AwsServiceException, SdkClientException {
        throw this.unknownOperationException(LocalDBClientExceptionMessage.TAGGING_NOT_SUPPORTED);
    }

    public UpdateContinuousBackupsResponse updateContinuousBackups(UpdateContinuousBackupsRequest updateContinuousBackupsRequest) throws AwsServiceException, SdkClientException {
        throw this.unknownOperationException(LocalDBClientExceptionMessage.CONTINUOUS_BACKUP_OPERATION_NOT_SUPPORTED);
    }

    public UpdateContributorInsightsResponse updateContributorInsights(UpdateContributorInsightsRequest updateContributorInsightsRequest) throws AwsServiceException, SdkClientException {
        throw this.unknownOperationException(LocalDBClientExceptionMessage.CONTRIBUTOR_INSIGHTS_OPERATION_NOT_SUPPORTED);
    }

    public UpdateGlobalTableResponse updateGlobalTable(UpdateGlobalTableRequest updateGlobalTableRequest) throws AwsServiceException, SdkClientException {
        throw this.unknownOperationException(LocalDBClientExceptionMessage.GLOBAL_TABLE_OPERATION_NOT_SUPPORTED);
    }

    public UpdateGlobalTableSettingsResponse updateGlobalTableSettings(UpdateGlobalTableSettingsRequest updateGlobalTableSettingsRequest) throws AwsServiceException, SdkClientException {
        throw this.unknownOperationException(LocalDBClientExceptionMessage.GLOBAL_TABLE_OPERATION_NOT_SUPPORTED);
    }

    public UpdateItemResponse updateItem(UpdateItemRequest updateItemRequest) throws AwsServiceException, SdkClientException {
        UpdateItemResult updateItemResult = this.dynamoDBSdkV1.updateItem(this.updateItemRequestConverter.toSdkV1(updateItemRequest, UpdateItemRequest::toBuilder, com.amazonaws.services.dynamodbv2.model.UpdateItemRequest.class));
        return this.updateItemResponseConverter.toSdkV2(updateItemResult, UpdateItemResponse::serializableBuilderClass, SdkBuilder::build);
    }

    public UpdateTableResponse updateTable(software.amazon.awssdk.services.dynamodb.model.UpdateTableRequest updateTableRequest) throws AwsServiceException, SdkClientException {
        UpdateTableResult updateTableResult = this.dynamoDBSdkV1.updateTable(this.updateTableRequestConverter.toSdkV1(updateTableRequest, software.amazon.awssdk.services.dynamodb.model.UpdateTableRequest::toBuilder, UpdateTableRequest.class));
        return this.updateTableResponseConverter.toSdkV2(updateTableResult, UpdateTableResponse::serializableBuilderClass, SdkBuilder::build);
    }

    public UpdateTableReplicaAutoScalingResponse updateTableReplicaAutoScaling(UpdateTableReplicaAutoScalingRequest updateTableReplicaAutoScalingRequest) throws AwsServiceException, SdkClientException {
        throw this.unknownOperationException(LocalDBClientExceptionMessage.TABLE_REPLICA_AUTO_SCALING_OPERATION_NOT_SUPPORTED);
    }

    public UpdateTimeToLiveResponse updateTimeToLive(UpdateTimeToLiveRequest updateTimeToLiveRequest) throws AwsServiceException, SdkClientException {
        UpdateTimeToLiveResult updateTimeToLiveResult = this.dynamoDBSdkV1.updateTimeToLive(this.updateTimeToLiveRequestConverter.toSdkV1(updateTimeToLiveRequest, UpdateTimeToLiveRequest::toBuilder, com.amazonaws.services.dynamodbv2.model.UpdateTimeToLiveRequest.class));
        return this.updateTimeToLiveResponseConverter.toSdkV2(updateTimeToLiveResult, UpdateTimeToLiveResponse::serializableBuilderClass, SdkBuilder::build);
    }

    public DynamoDbWaiter waiter() {
        return DynamoDbWaiter.builder().client((DynamoDbClient)this).build();
    }

    private DynamoDBLocalServiceException unknownOperationException(LocalDBClientExceptionMessage exceptionMessage) {
        return AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.UNKNOWN_OPERATION_EXCEPTION, exceptionMessage.getMessage());
    }
}

