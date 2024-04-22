/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  software.amazon.awssdk.services.dynamodb.DynamoDbAsyncClient
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
 *  software.amazon.awssdk.services.dynamodb.paginators.BatchGetItemPublisher
 *  software.amazon.awssdk.services.dynamodb.paginators.ListContributorInsightsPublisher
 *  software.amazon.awssdk.services.dynamodb.paginators.ListExportsPublisher
 *  software.amazon.awssdk.services.dynamodb.paginators.ListTablesPublisher
 *  software.amazon.awssdk.services.dynamodb.paginators.QueryPublisher
 *  software.amazon.awssdk.services.dynamodb.paginators.ScanPublisher
 *  software.amazon.awssdk.services.dynamodb.waiters.DynamoDbAsyncWaiter
 */
package com.amazonaws.services.dynamodbv2.local.shared.access.awssdkv2.client;

import com.amazonaws.services.dynamodbv2.local.shared.access.awssdkv2.client.CompletableFutureProvider;
import com.amazonaws.services.dynamodbv2.local.shared.access.awssdkv2.client.PaginatorUtils;
import java.util.concurrent.CompletableFuture;
import software.amazon.awssdk.services.dynamodb.DynamoDbAsyncClient;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.BatchExecuteStatementRequest;
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
import software.amazon.awssdk.services.dynamodb.model.DeleteTableRequest;
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
import software.amazon.awssdk.services.dynamodb.model.DescribeLimitsRequest;
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
import software.amazon.awssdk.services.dynamodb.model.ExecuteStatementRequest;
import software.amazon.awssdk.services.dynamodb.model.ExecuteStatementResponse;
import software.amazon.awssdk.services.dynamodb.model.ExecuteTransactionRequest;
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
import software.amazon.awssdk.services.dynamodb.model.PutItemRequest;
import software.amazon.awssdk.services.dynamodb.model.PutItemResponse;
import software.amazon.awssdk.services.dynamodb.model.QueryRequest;
import software.amazon.awssdk.services.dynamodb.model.QueryResponse;
import software.amazon.awssdk.services.dynamodb.model.RestoreTableFromBackupRequest;
import software.amazon.awssdk.services.dynamodb.model.RestoreTableFromBackupResponse;
import software.amazon.awssdk.services.dynamodb.model.RestoreTableToPointInTimeRequest;
import software.amazon.awssdk.services.dynamodb.model.RestoreTableToPointInTimeResponse;
import software.amazon.awssdk.services.dynamodb.model.ScanRequest;
import software.amazon.awssdk.services.dynamodb.model.ScanResponse;
import software.amazon.awssdk.services.dynamodb.model.TagResourceRequest;
import software.amazon.awssdk.services.dynamodb.model.TagResourceResponse;
import software.amazon.awssdk.services.dynamodb.model.TransactGetItemsRequest;
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
import software.amazon.awssdk.services.dynamodb.model.UpdateTableRequest;
import software.amazon.awssdk.services.dynamodb.model.UpdateTableResponse;
import software.amazon.awssdk.services.dynamodb.model.UpdateTimeToLiveRequest;
import software.amazon.awssdk.services.dynamodb.model.UpdateTimeToLiveResponse;
import software.amazon.awssdk.services.dynamodb.paginators.BatchGetItemPublisher;
import software.amazon.awssdk.services.dynamodb.paginators.ListContributorInsightsPublisher;
import software.amazon.awssdk.services.dynamodb.paginators.ListExportsPublisher;
import software.amazon.awssdk.services.dynamodb.paginators.ListTablesPublisher;
import software.amazon.awssdk.services.dynamodb.paginators.QueryPublisher;
import software.amazon.awssdk.services.dynamodb.paginators.ScanPublisher;
import software.amazon.awssdk.services.dynamodb.waiters.DynamoDbAsyncWaiter;

public class LocalDynamoDbAsyncClient
implements DynamoDbAsyncClient {
    private final DynamoDbClient dynamoDbClient;
    private final CompletableFutureProvider futureProvider;

    public LocalDynamoDbAsyncClient(DynamoDbClient dynamoDbClient) {
        this.dynamoDbClient = dynamoDbClient;
        this.futureProvider = new CompletableFutureProvider();
    }

    public String serviceName() {
        return "dynamodb";
    }

    public void close() {
        this.dynamoDbClient.close();
    }

    public CompletableFuture<BatchExecuteStatementResponse> batchExecuteStatement(BatchExecuteStatementRequest batchExecuteStatementRequest) {
        return this.futureProvider.completableFuture(batchExecuteStatementRequest, arg_0 -> ((DynamoDbClient)this.dynamoDbClient).batchExecuteStatement(arg_0));
    }

    public CompletableFuture<BatchGetItemResponse> batchGetItem(BatchGetItemRequest batchGetItemRequest) {
        return this.futureProvider.completableFuture(batchGetItemRequest, arg_0 -> ((DynamoDbClient)this.dynamoDbClient).batchGetItem(arg_0));
    }

    public BatchGetItemPublisher batchGetItemPaginator(BatchGetItemRequest batchGetItemRequest) {
        return new BatchGetItemPublisher((DynamoDbAsyncClient)this, PaginatorUtils.applyPaginatorUserAgent(batchGetItemRequest));
    }

    public CompletableFuture<BatchWriteItemResponse> batchWriteItem(BatchWriteItemRequest batchWriteItemRequest) {
        return this.futureProvider.completableFuture(batchWriteItemRequest, arg_0 -> ((DynamoDbClient)this.dynamoDbClient).batchWriteItem(arg_0));
    }

    public CompletableFuture<CreateBackupResponse> createBackup(CreateBackupRequest createBackupRequest) {
        return this.futureProvider.completableFuture(createBackupRequest, arg_0 -> ((DynamoDbClient)this.dynamoDbClient).createBackup(arg_0));
    }

    public CompletableFuture<CreateGlobalTableResponse> createGlobalTable(CreateGlobalTableRequest createGlobalTableRequest) {
        return this.futureProvider.completableFuture(createGlobalTableRequest, arg_0 -> ((DynamoDbClient)this.dynamoDbClient).createGlobalTable(arg_0));
    }

    public CompletableFuture<CreateTableResponse> createTable(CreateTableRequest createTableRequest) {
        return this.futureProvider.completableFuture(createTableRequest, arg_0 -> ((DynamoDbClient)this.dynamoDbClient).createTable(arg_0));
    }

    public CompletableFuture<DeleteBackupResponse> deleteBackup(DeleteBackupRequest deleteBackupRequest) {
        return this.futureProvider.completableFuture(deleteBackupRequest, arg_0 -> ((DynamoDbClient)this.dynamoDbClient).deleteBackup(arg_0));
    }

    public CompletableFuture<DeleteItemResponse> deleteItem(DeleteItemRequest deleteItemRequest) {
        return this.futureProvider.completableFuture(deleteItemRequest, arg_0 -> ((DynamoDbClient)this.dynamoDbClient).deleteItem(arg_0));
    }

    public CompletableFuture<DeleteTableResponse> deleteTable(DeleteTableRequest deleteTableRequest) {
        return this.futureProvider.completableFuture(deleteTableRequest, arg_0 -> ((DynamoDbClient)this.dynamoDbClient).deleteTable(arg_0));
    }

    public CompletableFuture<DescribeBackupResponse> describeBackup(DescribeBackupRequest describeBackupRequest) {
        return this.futureProvider.completableFuture(describeBackupRequest, arg_0 -> ((DynamoDbClient)this.dynamoDbClient).describeBackup(arg_0));
    }

    public CompletableFuture<DescribeContinuousBackupsResponse> describeContinuousBackups(DescribeContinuousBackupsRequest describeContinuousBackupsRequest) {
        return this.futureProvider.completableFuture(describeContinuousBackupsRequest, arg_0 -> ((DynamoDbClient)this.dynamoDbClient).describeContinuousBackups(arg_0));
    }

    public CompletableFuture<DescribeContributorInsightsResponse> describeContributorInsights(DescribeContributorInsightsRequest describeContributorInsightsRequest) {
        return this.futureProvider.completableFuture(describeContributorInsightsRequest, arg_0 -> ((DynamoDbClient)this.dynamoDbClient).describeContributorInsights(arg_0));
    }

    public CompletableFuture<DescribeEndpointsResponse> describeEndpoints(DescribeEndpointsRequest describeEndpointsRequest) {
        return this.futureProvider.completableFuture(describeEndpointsRequest, arg_0 -> ((DynamoDbClient)this.dynamoDbClient).describeEndpoints(arg_0));
    }

    public CompletableFuture<DescribeExportResponse> describeExport(DescribeExportRequest describeExportRequest) {
        return this.futureProvider.completableFuture(describeExportRequest, arg_0 -> ((DynamoDbClient)this.dynamoDbClient).describeExport(arg_0));
    }

    public CompletableFuture<DescribeGlobalTableResponse> describeGlobalTable(DescribeGlobalTableRequest describeGlobalTableRequest) {
        return this.futureProvider.completableFuture(describeGlobalTableRequest, arg_0 -> ((DynamoDbClient)this.dynamoDbClient).describeGlobalTable(arg_0));
    }

    public CompletableFuture<DescribeGlobalTableSettingsResponse> describeGlobalTableSettings(DescribeGlobalTableSettingsRequest describeGlobalTableSettingsRequest) {
        return this.futureProvider.completableFuture(describeGlobalTableSettingsRequest, arg_0 -> ((DynamoDbClient)this.dynamoDbClient).describeGlobalTableSettings(arg_0));
    }

    public CompletableFuture<DescribeKinesisStreamingDestinationResponse> describeKinesisStreamingDestination(DescribeKinesisStreamingDestinationRequest describeKinesisStreamingDestinationRequest) {
        return this.futureProvider.completableFuture(describeKinesisStreamingDestinationRequest, arg_0 -> ((DynamoDbClient)this.dynamoDbClient).describeKinesisStreamingDestination(arg_0));
    }

    public CompletableFuture<DescribeLimitsResponse> describeLimits(DescribeLimitsRequest describeLimitsRequest) {
        return this.futureProvider.completableFuture(describeLimitsRequest, arg_0 -> ((DynamoDbClient)this.dynamoDbClient).describeLimits(arg_0));
    }

    public CompletableFuture<DescribeTableResponse> describeTable(DescribeTableRequest describeTableRequest) {
        return this.futureProvider.completableFuture(describeTableRequest, arg_0 -> ((DynamoDbClient)this.dynamoDbClient).describeTable(arg_0));
    }

    public CompletableFuture<DescribeTableReplicaAutoScalingResponse> describeTableReplicaAutoScaling(DescribeTableReplicaAutoScalingRequest describeTableReplicaAutoScalingRequest) {
        return this.futureProvider.completableFuture(describeTableReplicaAutoScalingRequest, arg_0 -> ((DynamoDbClient)this.dynamoDbClient).describeTableReplicaAutoScaling(arg_0));
    }

    public CompletableFuture<DescribeTimeToLiveResponse> describeTimeToLive(DescribeTimeToLiveRequest describeTimeToLiveRequest) {
        return this.futureProvider.completableFuture(describeTimeToLiveRequest, arg_0 -> ((DynamoDbClient)this.dynamoDbClient).describeTimeToLive(arg_0));
    }

    public CompletableFuture<DisableKinesisStreamingDestinationResponse> disableKinesisStreamingDestination(DisableKinesisStreamingDestinationRequest disableKinesisStreamingDestinationRequest) {
        return this.futureProvider.completableFuture(disableKinesisStreamingDestinationRequest, arg_0 -> ((DynamoDbClient)this.dynamoDbClient).disableKinesisStreamingDestination(arg_0));
    }

    public CompletableFuture<EnableKinesisStreamingDestinationResponse> enableKinesisStreamingDestination(EnableKinesisStreamingDestinationRequest enableKinesisStreamingDestinationRequest) {
        return this.futureProvider.completableFuture(enableKinesisStreamingDestinationRequest, arg_0 -> ((DynamoDbClient)this.dynamoDbClient).enableKinesisStreamingDestination(arg_0));
    }

    public CompletableFuture<ExecuteStatementResponse> executeStatement(ExecuteStatementRequest executeStatementRequest) {
        return this.futureProvider.completableFuture(executeStatementRequest, arg_0 -> ((DynamoDbClient)this.dynamoDbClient).executeStatement(arg_0));
    }

    public CompletableFuture<ExecuteTransactionResponse> executeTransaction(ExecuteTransactionRequest executeTransactionRequest) {
        return this.futureProvider.completableFuture(executeTransactionRequest, arg_0 -> ((DynamoDbClient)this.dynamoDbClient).executeTransaction(arg_0));
    }

    public CompletableFuture<ExportTableToPointInTimeResponse> exportTableToPointInTime(ExportTableToPointInTimeRequest exportTableToPointInTimeRequest) {
        return this.futureProvider.completableFuture(exportTableToPointInTimeRequest, arg_0 -> ((DynamoDbClient)this.dynamoDbClient).exportTableToPointInTime(arg_0));
    }

    public CompletableFuture<GetItemResponse> getItem(GetItemRequest getItemRequest) {
        return this.futureProvider.completableFuture(getItemRequest, arg_0 -> ((DynamoDbClient)this.dynamoDbClient).getItem(arg_0));
    }

    public CompletableFuture<ListBackupsResponse> listBackups(ListBackupsRequest listBackupsRequest) {
        return this.futureProvider.completableFuture(listBackupsRequest, arg_0 -> ((DynamoDbClient)this.dynamoDbClient).listBackups(arg_0));
    }

    public CompletableFuture<ListContributorInsightsResponse> listContributorInsights(ListContributorInsightsRequest listContributorInsightsRequest) {
        return this.futureProvider.completableFuture(listContributorInsightsRequest, arg_0 -> ((DynamoDbClient)this.dynamoDbClient).listContributorInsights(arg_0));
    }

    public ListContributorInsightsPublisher listContributorInsightsPaginator(ListContributorInsightsRequest listContributorInsightsRequest) {
        return new ListContributorInsightsPublisher((DynamoDbAsyncClient)this, PaginatorUtils.applyPaginatorUserAgent(listContributorInsightsRequest));
    }

    public CompletableFuture<ListExportsResponse> listExports(ListExportsRequest listExportsRequest) {
        return this.futureProvider.completableFuture(listExportsRequest, arg_0 -> ((DynamoDbClient)this.dynamoDbClient).listExports(arg_0));
    }

    public ListExportsPublisher listExportsPaginator(ListExportsRequest listExportsRequest) {
        return new ListExportsPublisher((DynamoDbAsyncClient)this, PaginatorUtils.applyPaginatorUserAgent(listExportsRequest));
    }

    public CompletableFuture<ListGlobalTablesResponse> listGlobalTables(ListGlobalTablesRequest listGlobalTablesRequest) {
        return this.futureProvider.completableFuture(listGlobalTablesRequest, arg_0 -> ((DynamoDbClient)this.dynamoDbClient).listGlobalTables(arg_0));
    }

    public CompletableFuture<ListTablesResponse> listTables(ListTablesRequest listTablesRequest) {
        return this.futureProvider.completableFuture(listTablesRequest, arg_0 -> ((DynamoDbClient)this.dynamoDbClient).listTables(arg_0));
    }

    public ListTablesPublisher listTablesPaginator(ListTablesRequest listTablesRequest) {
        return new ListTablesPublisher((DynamoDbAsyncClient)this, PaginatorUtils.applyPaginatorUserAgent(listTablesRequest));
    }

    public CompletableFuture<ListTagsOfResourceResponse> listTagsOfResource(ListTagsOfResourceRequest listTagsOfResourceRequest) {
        return this.futureProvider.completableFuture(listTagsOfResourceRequest, arg_0 -> ((DynamoDbClient)this.dynamoDbClient).listTagsOfResource(arg_0));
    }

    public CompletableFuture<PutItemResponse> putItem(PutItemRequest putItemRequest) {
        return this.futureProvider.completableFuture(putItemRequest, arg_0 -> ((DynamoDbClient)this.dynamoDbClient).putItem(arg_0));
    }

    public CompletableFuture<QueryResponse> query(QueryRequest queryRequest) {
        return this.futureProvider.completableFuture(queryRequest, arg_0 -> ((DynamoDbClient)this.dynamoDbClient).query(arg_0));
    }

    public QueryPublisher queryPaginator(QueryRequest queryRequest) {
        return new QueryPublisher((DynamoDbAsyncClient)this, PaginatorUtils.applyPaginatorUserAgent(queryRequest));
    }

    public CompletableFuture<RestoreTableFromBackupResponse> restoreTableFromBackup(RestoreTableFromBackupRequest restoreTableFromBackupRequest) {
        return this.futureProvider.completableFuture(restoreTableFromBackupRequest, arg_0 -> ((DynamoDbClient)this.dynamoDbClient).restoreTableFromBackup(arg_0));
    }

    public CompletableFuture<RestoreTableToPointInTimeResponse> restoreTableToPointInTime(RestoreTableToPointInTimeRequest restoreTableToPointInTimeRequest) {
        return this.futureProvider.completableFuture(restoreTableToPointInTimeRequest, arg_0 -> ((DynamoDbClient)this.dynamoDbClient).restoreTableToPointInTime(arg_0));
    }

    public CompletableFuture<ScanResponse> scan(ScanRequest scanRequest) {
        return this.futureProvider.completableFuture(scanRequest, arg_0 -> ((DynamoDbClient)this.dynamoDbClient).scan(arg_0));
    }

    public ScanPublisher scanPaginator(ScanRequest scanRequest) {
        return new ScanPublisher((DynamoDbAsyncClient)this, PaginatorUtils.applyPaginatorUserAgent(scanRequest));
    }

    public CompletableFuture<TagResourceResponse> tagResource(TagResourceRequest tagResourceRequest) {
        return this.futureProvider.completableFuture(tagResourceRequest, arg_0 -> ((DynamoDbClient)this.dynamoDbClient).tagResource(arg_0));
    }

    public CompletableFuture<TransactGetItemsResponse> transactGetItems(TransactGetItemsRequest transactGetItemsRequest) {
        return this.futureProvider.completableFuture(transactGetItemsRequest, arg_0 -> ((DynamoDbClient)this.dynamoDbClient).transactGetItems(arg_0));
    }

    public CompletableFuture<TransactWriteItemsResponse> transactWriteItems(TransactWriteItemsRequest transactWriteItemsRequest) {
        return this.futureProvider.completableFuture(transactWriteItemsRequest, arg_0 -> ((DynamoDbClient)this.dynamoDbClient).transactWriteItems(arg_0));
    }

    public CompletableFuture<UntagResourceResponse> untagResource(UntagResourceRequest untagResourceRequest) {
        return this.futureProvider.completableFuture(untagResourceRequest, arg_0 -> ((DynamoDbClient)this.dynamoDbClient).untagResource(arg_0));
    }

    public CompletableFuture<UpdateContinuousBackupsResponse> updateContinuousBackups(UpdateContinuousBackupsRequest updateContinuousBackupsRequest) {
        return this.futureProvider.completableFuture(updateContinuousBackupsRequest, arg_0 -> ((DynamoDbClient)this.dynamoDbClient).updateContinuousBackups(arg_0));
    }

    public CompletableFuture<UpdateContributorInsightsResponse> updateContributorInsights(UpdateContributorInsightsRequest updateContributorInsightsRequest) {
        return this.futureProvider.completableFuture(updateContributorInsightsRequest, arg_0 -> ((DynamoDbClient)this.dynamoDbClient).updateContributorInsights(arg_0));
    }

    public CompletableFuture<UpdateGlobalTableResponse> updateGlobalTable(UpdateGlobalTableRequest updateGlobalTableRequest) {
        return this.futureProvider.completableFuture(updateGlobalTableRequest, arg_0 -> ((DynamoDbClient)this.dynamoDbClient).updateGlobalTable(arg_0));
    }

    public CompletableFuture<UpdateGlobalTableSettingsResponse> updateGlobalTableSettings(UpdateGlobalTableSettingsRequest updateGlobalTableSettingsRequest) {
        return this.futureProvider.completableFuture(updateGlobalTableSettingsRequest, arg_0 -> ((DynamoDbClient)this.dynamoDbClient).updateGlobalTableSettings(arg_0));
    }

    public CompletableFuture<UpdateItemResponse> updateItem(UpdateItemRequest updateItemRequest) {
        return this.futureProvider.completableFuture(updateItemRequest, arg_0 -> ((DynamoDbClient)this.dynamoDbClient).updateItem(arg_0));
    }

    public CompletableFuture<UpdateTableResponse> updateTable(UpdateTableRequest updateTableRequest) {
        return this.futureProvider.completableFuture(updateTableRequest, arg_0 -> ((DynamoDbClient)this.dynamoDbClient).updateTable(arg_0));
    }

    public CompletableFuture<UpdateTableReplicaAutoScalingResponse> updateTableReplicaAutoScaling(UpdateTableReplicaAutoScalingRequest updateTableReplicaAutoScalingRequest) {
        return this.futureProvider.completableFuture(updateTableReplicaAutoScalingRequest, arg_0 -> ((DynamoDbClient)this.dynamoDbClient).updateTableReplicaAutoScaling(arg_0));
    }

    public CompletableFuture<UpdateTimeToLiveResponse> updateTimeToLive(UpdateTimeToLiveRequest updateTimeToLiveRequest) {
        return this.futureProvider.completableFuture(updateTimeToLiveRequest, arg_0 -> ((DynamoDbClient)this.dynamoDbClient).updateTimeToLive(arg_0));
    }

    public DynamoDbAsyncWaiter waiter() {
        return DynamoDbAsyncWaiter.builder().client((DynamoDbAsyncClient)this).build();
    }
}

