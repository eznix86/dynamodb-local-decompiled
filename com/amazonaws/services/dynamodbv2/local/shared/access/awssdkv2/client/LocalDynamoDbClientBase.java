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
 *  software.amazon.awssdk.services.dynamodb.model.BatchExecuteStatementRequest
 *  software.amazon.awssdk.services.dynamodb.model.BatchExecuteStatementRequest$Builder
 *  software.amazon.awssdk.services.dynamodb.model.BatchExecuteStatementResponse
 *  software.amazon.awssdk.services.dynamodb.model.BatchExecuteStatementResponse$Builder
 *  software.amazon.awssdk.services.dynamodb.model.BatchGetItemRequest
 *  software.amazon.awssdk.services.dynamodb.model.BatchGetItemRequest$Builder
 *  software.amazon.awssdk.services.dynamodb.model.BatchGetItemResponse
 *  software.amazon.awssdk.services.dynamodb.model.BatchGetItemResponse$Builder
 *  software.amazon.awssdk.services.dynamodb.model.BatchWriteItemRequest
 *  software.amazon.awssdk.services.dynamodb.model.BatchWriteItemRequest$Builder
 *  software.amazon.awssdk.services.dynamodb.model.BatchWriteItemResponse
 *  software.amazon.awssdk.services.dynamodb.model.BatchWriteItemResponse$Builder
 *  software.amazon.awssdk.services.dynamodb.model.CreateTableRequest
 *  software.amazon.awssdk.services.dynamodb.model.CreateTableRequest$Builder
 *  software.amazon.awssdk.services.dynamodb.model.CreateTableResponse
 *  software.amazon.awssdk.services.dynamodb.model.CreateTableResponse$Builder
 *  software.amazon.awssdk.services.dynamodb.model.DeleteItemRequest
 *  software.amazon.awssdk.services.dynamodb.model.DeleteItemRequest$Builder
 *  software.amazon.awssdk.services.dynamodb.model.DeleteItemResponse
 *  software.amazon.awssdk.services.dynamodb.model.DeleteItemResponse$Builder
 *  software.amazon.awssdk.services.dynamodb.model.DeleteTableRequest
 *  software.amazon.awssdk.services.dynamodb.model.DeleteTableRequest$Builder
 *  software.amazon.awssdk.services.dynamodb.model.DeleteTableResponse
 *  software.amazon.awssdk.services.dynamodb.model.DeleteTableResponse$Builder
 *  software.amazon.awssdk.services.dynamodb.model.DescribeLimitsRequest
 *  software.amazon.awssdk.services.dynamodb.model.DescribeLimitsRequest$Builder
 *  software.amazon.awssdk.services.dynamodb.model.DescribeLimitsResponse
 *  software.amazon.awssdk.services.dynamodb.model.DescribeLimitsResponse$Builder
 *  software.amazon.awssdk.services.dynamodb.model.DescribeTableRequest
 *  software.amazon.awssdk.services.dynamodb.model.DescribeTableRequest$Builder
 *  software.amazon.awssdk.services.dynamodb.model.DescribeTableResponse
 *  software.amazon.awssdk.services.dynamodb.model.DescribeTableResponse$Builder
 *  software.amazon.awssdk.services.dynamodb.model.DescribeTimeToLiveRequest
 *  software.amazon.awssdk.services.dynamodb.model.DescribeTimeToLiveRequest$Builder
 *  software.amazon.awssdk.services.dynamodb.model.DescribeTimeToLiveResponse
 *  software.amazon.awssdk.services.dynamodb.model.DescribeTimeToLiveResponse$Builder
 *  software.amazon.awssdk.services.dynamodb.model.ExecuteStatementRequest
 *  software.amazon.awssdk.services.dynamodb.model.ExecuteStatementRequest$Builder
 *  software.amazon.awssdk.services.dynamodb.model.ExecuteStatementResponse
 *  software.amazon.awssdk.services.dynamodb.model.ExecuteStatementResponse$Builder
 *  software.amazon.awssdk.services.dynamodb.model.ExecuteTransactionRequest
 *  software.amazon.awssdk.services.dynamodb.model.ExecuteTransactionRequest$Builder
 *  software.amazon.awssdk.services.dynamodb.model.ExecuteTransactionResponse
 *  software.amazon.awssdk.services.dynamodb.model.ExecuteTransactionResponse$Builder
 *  software.amazon.awssdk.services.dynamodb.model.GetItemRequest
 *  software.amazon.awssdk.services.dynamodb.model.GetItemRequest$Builder
 *  software.amazon.awssdk.services.dynamodb.model.GetItemResponse
 *  software.amazon.awssdk.services.dynamodb.model.GetItemResponse$Builder
 *  software.amazon.awssdk.services.dynamodb.model.ListTablesRequest
 *  software.amazon.awssdk.services.dynamodb.model.ListTablesRequest$Builder
 *  software.amazon.awssdk.services.dynamodb.model.ListTablesResponse
 *  software.amazon.awssdk.services.dynamodb.model.ListTablesResponse$Builder
 *  software.amazon.awssdk.services.dynamodb.model.PutItemRequest
 *  software.amazon.awssdk.services.dynamodb.model.PutItemRequest$Builder
 *  software.amazon.awssdk.services.dynamodb.model.PutItemResponse
 *  software.amazon.awssdk.services.dynamodb.model.PutItemResponse$Builder
 *  software.amazon.awssdk.services.dynamodb.model.QueryRequest
 *  software.amazon.awssdk.services.dynamodb.model.QueryRequest$Builder
 *  software.amazon.awssdk.services.dynamodb.model.QueryResponse
 *  software.amazon.awssdk.services.dynamodb.model.QueryResponse$Builder
 *  software.amazon.awssdk.services.dynamodb.model.ScanRequest
 *  software.amazon.awssdk.services.dynamodb.model.ScanRequest$Builder
 *  software.amazon.awssdk.services.dynamodb.model.ScanResponse
 *  software.amazon.awssdk.services.dynamodb.model.ScanResponse$Builder
 *  software.amazon.awssdk.services.dynamodb.model.TransactGetItemsRequest
 *  software.amazon.awssdk.services.dynamodb.model.TransactGetItemsRequest$Builder
 *  software.amazon.awssdk.services.dynamodb.model.TransactGetItemsResponse
 *  software.amazon.awssdk.services.dynamodb.model.TransactGetItemsResponse$Builder
 *  software.amazon.awssdk.services.dynamodb.model.TransactWriteItemsRequest
 *  software.amazon.awssdk.services.dynamodb.model.TransactWriteItemsRequest$Builder
 *  software.amazon.awssdk.services.dynamodb.model.TransactWriteItemsResponse
 *  software.amazon.awssdk.services.dynamodb.model.TransactWriteItemsResponse$Builder
 *  software.amazon.awssdk.services.dynamodb.model.UpdateItemRequest
 *  software.amazon.awssdk.services.dynamodb.model.UpdateItemRequest$Builder
 *  software.amazon.awssdk.services.dynamodb.model.UpdateItemResponse
 *  software.amazon.awssdk.services.dynamodb.model.UpdateItemResponse$Builder
 *  software.amazon.awssdk.services.dynamodb.model.UpdateTableRequest
 *  software.amazon.awssdk.services.dynamodb.model.UpdateTableRequest$Builder
 *  software.amazon.awssdk.services.dynamodb.model.UpdateTableResponse
 *  software.amazon.awssdk.services.dynamodb.model.UpdateTableResponse$Builder
 *  software.amazon.awssdk.services.dynamodb.model.UpdateTimeToLiveRequest
 *  software.amazon.awssdk.services.dynamodb.model.UpdateTimeToLiveRequest$Builder
 *  software.amazon.awssdk.services.dynamodb.model.UpdateTimeToLiveResponse
 *  software.amazon.awssdk.services.dynamodb.model.UpdateTimeToLiveResponse$Builder
 */
package com.amazonaws.services.dynamodbv2.local.shared.access.awssdkv2.client;

import com.amazonaws.services.dynamodbv2.local.shared.access.awssdkv2.client.ClientSdkV2Base;
import com.amazonaws.services.dynamodbv2.local.shared.access.awssdkv2.converters.DynamoDBRequestConverter;
import com.amazonaws.services.dynamodbv2.local.shared.access.awssdkv2.converters.DynamoDBResponseConverter;
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
import software.amazon.awssdk.services.dynamodb.model.BatchExecuteStatementRequest;
import software.amazon.awssdk.services.dynamodb.model.BatchExecuteStatementResponse;
import software.amazon.awssdk.services.dynamodb.model.BatchGetItemRequest;
import software.amazon.awssdk.services.dynamodb.model.BatchGetItemResponse;
import software.amazon.awssdk.services.dynamodb.model.BatchWriteItemRequest;
import software.amazon.awssdk.services.dynamodb.model.BatchWriteItemResponse;
import software.amazon.awssdk.services.dynamodb.model.CreateTableRequest;
import software.amazon.awssdk.services.dynamodb.model.CreateTableResponse;
import software.amazon.awssdk.services.dynamodb.model.DeleteItemRequest;
import software.amazon.awssdk.services.dynamodb.model.DeleteItemResponse;
import software.amazon.awssdk.services.dynamodb.model.DeleteTableRequest;
import software.amazon.awssdk.services.dynamodb.model.DeleteTableResponse;
import software.amazon.awssdk.services.dynamodb.model.DescribeLimitsRequest;
import software.amazon.awssdk.services.dynamodb.model.DescribeLimitsResponse;
import software.amazon.awssdk.services.dynamodb.model.DescribeTableRequest;
import software.amazon.awssdk.services.dynamodb.model.DescribeTableResponse;
import software.amazon.awssdk.services.dynamodb.model.DescribeTimeToLiveRequest;
import software.amazon.awssdk.services.dynamodb.model.DescribeTimeToLiveResponse;
import software.amazon.awssdk.services.dynamodb.model.ExecuteStatementRequest;
import software.amazon.awssdk.services.dynamodb.model.ExecuteStatementResponse;
import software.amazon.awssdk.services.dynamodb.model.ExecuteTransactionRequest;
import software.amazon.awssdk.services.dynamodb.model.ExecuteTransactionResponse;
import software.amazon.awssdk.services.dynamodb.model.GetItemRequest;
import software.amazon.awssdk.services.dynamodb.model.GetItemResponse;
import software.amazon.awssdk.services.dynamodb.model.ListTablesRequest;
import software.amazon.awssdk.services.dynamodb.model.ListTablesResponse;
import software.amazon.awssdk.services.dynamodb.model.PutItemRequest;
import software.amazon.awssdk.services.dynamodb.model.PutItemResponse;
import software.amazon.awssdk.services.dynamodb.model.QueryRequest;
import software.amazon.awssdk.services.dynamodb.model.QueryResponse;
import software.amazon.awssdk.services.dynamodb.model.ScanRequest;
import software.amazon.awssdk.services.dynamodb.model.ScanResponse;
import software.amazon.awssdk.services.dynamodb.model.TransactGetItemsRequest;
import software.amazon.awssdk.services.dynamodb.model.TransactGetItemsResponse;
import software.amazon.awssdk.services.dynamodb.model.TransactWriteItemsRequest;
import software.amazon.awssdk.services.dynamodb.model.TransactWriteItemsResponse;
import software.amazon.awssdk.services.dynamodb.model.UpdateItemRequest;
import software.amazon.awssdk.services.dynamodb.model.UpdateItemResponse;
import software.amazon.awssdk.services.dynamodb.model.UpdateTableRequest;
import software.amazon.awssdk.services.dynamodb.model.UpdateTableResponse;
import software.amazon.awssdk.services.dynamodb.model.UpdateTimeToLiveRequest;
import software.amazon.awssdk.services.dynamodb.model.UpdateTimeToLiveResponse;

public abstract class LocalDynamoDbClientBase
extends ClientSdkV2Base {
    protected final DynamoDBRequestConverter<com.amazonaws.services.dynamodbv2.model.CreateTableRequest, CreateTableRequest, CreateTableRequest.Builder> createTableReqConverter;
    protected final DynamoDBRequestConverter<com.amazonaws.services.dynamodbv2.model.BatchGetItemRequest, BatchGetItemRequest, BatchGetItemRequest.Builder> batchGetItemRequestRequestConverter;
    protected final DynamoDBRequestConverter<com.amazonaws.services.dynamodbv2.model.BatchWriteItemRequest, BatchWriteItemRequest, BatchWriteItemRequest.Builder> batchWriteItemRequestConverter;
    protected final DynamoDBRequestConverter<com.amazonaws.services.dynamodbv2.model.DeleteItemRequest, DeleteItemRequest, DeleteItemRequest.Builder> deleteItemRequestConverter;
    protected final DynamoDBRequestConverter<DeleteTableRequest, software.amazon.awssdk.services.dynamodb.model.DeleteTableRequest, DeleteTableRequest.Builder> deleteTableRequestConverter;
    protected final DynamoDBRequestConverter<com.amazonaws.services.dynamodbv2.model.DescribeTableRequest, DescribeTableRequest, DescribeTableRequest.Builder> describeTableRequestConverter;
    protected final DynamoDBRequestConverter<DescribeLimitsRequest, software.amazon.awssdk.services.dynamodb.model.DescribeLimitsRequest, DescribeLimitsRequest.Builder> describeLimitsRequestConverter;
    protected final DynamoDBRequestConverter<com.amazonaws.services.dynamodbv2.model.DescribeTimeToLiveRequest, DescribeTimeToLiveRequest, DescribeTimeToLiveRequest.Builder> describeTimeToLiveRequestConverter;
    protected final DynamoDBRequestConverter<com.amazonaws.services.dynamodbv2.model.GetItemRequest, GetItemRequest, GetItemRequest.Builder> getItemRequestConverter;
    protected final DynamoDBRequestConverter<com.amazonaws.services.dynamodbv2.model.ListTablesRequest, ListTablesRequest, ListTablesRequest.Builder> listTablesRequestConverter;
    protected final DynamoDBRequestConverter<PutItemRequest, software.amazon.awssdk.services.dynamodb.model.PutItemRequest, PutItemRequest.Builder> putItemRequestConverter;
    protected final DynamoDBRequestConverter<QueryRequest, software.amazon.awssdk.services.dynamodb.model.QueryRequest, QueryRequest.Builder> queryRequestConverter;
    protected final DynamoDBRequestConverter<ScanRequest, software.amazon.awssdk.services.dynamodb.model.ScanRequest, ScanRequest.Builder> scanRequestConverter;
    protected final DynamoDBRequestConverter<com.amazonaws.services.dynamodbv2.model.UpdateItemRequest, UpdateItemRequest, UpdateItemRequest.Builder> updateItemRequestConverter;
    protected final DynamoDBRequestConverter<UpdateTableRequest, software.amazon.awssdk.services.dynamodb.model.UpdateTableRequest, UpdateTableRequest.Builder> updateTableRequestConverter;
    protected final DynamoDBRequestConverter<com.amazonaws.services.dynamodbv2.model.UpdateTimeToLiveRequest, UpdateTimeToLiveRequest, UpdateTimeToLiveRequest.Builder> updateTimeToLiveRequestConverter;
    protected final DynamoDBRequestConverter<TransactGetItemsRequest, software.amazon.awssdk.services.dynamodb.model.TransactGetItemsRequest, TransactGetItemsRequest.Builder> transactGetItemsRequestConverter;
    protected final DynamoDBRequestConverter<com.amazonaws.services.dynamodbv2.model.TransactWriteItemsRequest, TransactWriteItemsRequest, TransactWriteItemsRequest.Builder> transactWriteItemsRequestConverter;
    protected final DynamoDBRequestConverter<BatchExecuteStatementRequest, software.amazon.awssdk.services.dynamodb.model.BatchExecuteStatementRequest, BatchExecuteStatementRequest.Builder> batchExecuteStatementRequestConverter;
    protected final DynamoDBRequestConverter<ExecuteTransactionRequest, software.amazon.awssdk.services.dynamodb.model.ExecuteTransactionRequest, ExecuteTransactionRequest.Builder> executeTransactionRequestConverter;
    protected final DynamoDBRequestConverter<ExecuteStatementRequest, software.amazon.awssdk.services.dynamodb.model.ExecuteStatementRequest, ExecuteStatementRequest.Builder> executeStatementRequestConverter;
    protected final DynamoDBResponseConverter<CreateTableResult, CreateTableResponse, CreateTableResponse.Builder> createTableResponseConverter;
    protected final DynamoDBResponseConverter<BatchGetItemResult, BatchGetItemResponse, BatchGetItemResponse.Builder> batchGetItemResponseConverter;
    protected final DynamoDBResponseConverter<BatchWriteItemResult, BatchWriteItemResponse, BatchWriteItemResponse.Builder> batchWriteItemResponseConverter;
    protected final DynamoDBResponseConverter<DeleteItemResult, DeleteItemResponse, DeleteItemResponse.Builder> deleteItemResponseConverter;
    protected final DynamoDBResponseConverter<DeleteTableResult, DeleteTableResponse, DeleteTableResponse.Builder> deleteTableResponseConverter;
    protected final DynamoDBResponseConverter<DescribeTableResult, DescribeTableResponse, DescribeTableResponse.Builder> describeTableResponseConverter;
    protected final DynamoDBResponseConverter<DescribeLimitsResult, DescribeLimitsResponse, DescribeLimitsResponse.Builder> describeLimitsResponseConverter;
    protected final DynamoDBResponseConverter<DescribeTimeToLiveResult, DescribeTimeToLiveResponse, DescribeTimeToLiveResponse.Builder> describeTimeToLiveResponseConverter;
    protected final DynamoDBResponseConverter<GetItemResult, GetItemResponse, GetItemResponse.Builder> getItemResponseConverter;
    protected final DynamoDBResponseConverter<ListTablesResult, ListTablesResponse, ListTablesResponse.Builder> listTablesResponseConverter;
    protected final DynamoDBResponseConverter<PutItemResult, PutItemResponse, PutItemResponse.Builder> putItemResponseConverter;
    protected final DynamoDBResponseConverter<QueryResult, QueryResponse, QueryResponse.Builder> queryResponseConverter;
    protected final DynamoDBResponseConverter<ScanResult, ScanResponse, ScanResponse.Builder> scanResponseConverter;
    protected final DynamoDBResponseConverter<UpdateItemResult, UpdateItemResponse, UpdateItemResponse.Builder> updateItemResponseConverter;
    protected final DynamoDBResponseConverter<UpdateTableResult, UpdateTableResponse, UpdateTableResponse.Builder> updateTableResponseConverter;
    protected final DynamoDBResponseConverter<UpdateTimeToLiveResult, UpdateTimeToLiveResponse, UpdateTimeToLiveResponse.Builder> updateTimeToLiveResponseConverter;
    protected final DynamoDBResponseConverter<TransactGetItemsResult, TransactGetItemsResponse, TransactGetItemsResponse.Builder> transactGetItemsResponseConverter;
    protected final DynamoDBResponseConverter<TransactWriteItemsResult, TransactWriteItemsResponse, TransactWriteItemsResponse.Builder> transactWriteItemsResponseConverter;
    protected final DynamoDBResponseConverter<ExecuteStatementResult, ExecuteStatementResponse, ExecuteStatementResponse.Builder> executeStatementResponseConverter;
    protected final DynamoDBResponseConverter<BatchExecuteStatementResult, BatchExecuteStatementResponse, BatchExecuteStatementResponse.Builder> batchExecuteStatementResponseConverter;
    protected final DynamoDBResponseConverter<ExecuteTransactionResult, ExecuteTransactionResponse, ExecuteTransactionResponse.Builder> executeTransactionResponseConverter;

    public LocalDynamoDbClientBase() {
        this.createTableReqConverter = new DynamoDBRequestConverter(this.objectMapper);
        this.batchGetItemRequestRequestConverter = new DynamoDBRequestConverter(this.objectMapper);
        this.batchWriteItemRequestConverter = new DynamoDBRequestConverter(this.objectMapper);
        this.deleteItemRequestConverter = new DynamoDBRequestConverter(this.objectMapper);
        this.deleteTableRequestConverter = new DynamoDBRequestConverter(this.objectMapper);
        this.describeTableRequestConverter = new DynamoDBRequestConverter(this.objectMapper);
        this.describeLimitsRequestConverter = new DynamoDBRequestConverter(this.objectMapper);
        this.describeTimeToLiveRequestConverter = new DynamoDBRequestConverter(this.objectMapper);
        this.getItemRequestConverter = new DynamoDBRequestConverter(this.objectMapper);
        this.listTablesRequestConverter = new DynamoDBRequestConverter(this.objectMapper);
        this.putItemRequestConverter = new DynamoDBRequestConverter(this.objectMapper);
        this.queryRequestConverter = new DynamoDBRequestConverter(this.objectMapper);
        this.scanRequestConverter = new DynamoDBRequestConverter(this.objectMapper);
        this.updateItemRequestConverter = new DynamoDBRequestConverter(this.objectMapper);
        this.updateTableRequestConverter = new DynamoDBRequestConverter(this.objectMapper);
        this.updateTimeToLiveRequestConverter = new DynamoDBRequestConverter(this.objectMapper);
        this.transactGetItemsRequestConverter = new DynamoDBRequestConverter(this.objectMapper);
        this.transactWriteItemsRequestConverter = new DynamoDBRequestConverter(this.objectMapper);
        this.batchExecuteStatementRequestConverter = new DynamoDBRequestConverter(this.objectMapper);
        this.executeTransactionRequestConverter = new DynamoDBRequestConverter(this.objectMapper);
        this.executeStatementRequestConverter = new DynamoDBRequestConverter(this.objectMapper);
        this.createTableResponseConverter = new DynamoDBResponseConverter(this.objectMapper);
        this.batchGetItemResponseConverter = new DynamoDBResponseConverter(this.objectMapper);
        this.batchWriteItemResponseConverter = new DynamoDBResponseConverter(this.objectMapper);
        this.deleteItemResponseConverter = new DynamoDBResponseConverter(this.objectMapper);
        this.deleteTableResponseConverter = new DynamoDBResponseConverter(this.objectMapper);
        this.describeTableResponseConverter = new DynamoDBResponseConverter(this.objectMapper);
        this.describeLimitsResponseConverter = new DynamoDBResponseConverter(this.objectMapper);
        this.describeTimeToLiveResponseConverter = new DynamoDBResponseConverter(this.objectMapper);
        this.getItemResponseConverter = new DynamoDBResponseConverter(this.objectMapper);
        this.listTablesResponseConverter = new DynamoDBResponseConverter(this.objectMapper);
        this.putItemResponseConverter = new DynamoDBResponseConverter(this.objectMapper);
        this.queryResponseConverter = new DynamoDBResponseConverter(this.objectMapper);
        this.scanResponseConverter = new DynamoDBResponseConverter(this.objectMapper);
        this.updateItemResponseConverter = new DynamoDBResponseConverter(this.objectMapper);
        this.updateTableResponseConverter = new DynamoDBResponseConverter(this.objectMapper);
        this.updateTimeToLiveResponseConverter = new DynamoDBResponseConverter(this.objectMapper);
        this.transactGetItemsResponseConverter = new DynamoDBResponseConverter(this.objectMapper);
        this.transactWriteItemsResponseConverter = new DynamoDBResponseConverter(this.objectMapper);
        this.executeStatementResponseConverter = new DynamoDBResponseConverter(this.objectMapper);
        this.batchExecuteStatementResponseConverter = new DynamoDBResponseConverter(this.objectMapper);
        this.executeTransactionResponseConverter = new DynamoDBResponseConverter(this.objectMapper);
    }
}

