/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  com.amazonaws.services.dynamodbv2.model.AttributeValue
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
 *  com.amazonaws.services.dynamodbv2.model.DescribeStreamRequest
 *  com.amazonaws.services.dynamodbv2.model.DescribeStreamResult
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
 *  com.amazonaws.services.dynamodbv2.model.GetRecordsRequest
 *  com.amazonaws.services.dynamodbv2.model.GetRecordsResult
 *  com.amazonaws.services.dynamodbv2.model.GetShardIteratorRequest
 *  com.amazonaws.services.dynamodbv2.model.GetShardIteratorResult
 *  com.amazonaws.services.dynamodbv2.model.ListStreamsRequest
 *  com.amazonaws.services.dynamodbv2.model.ListStreamsResult
 *  com.amazonaws.services.dynamodbv2.model.ListTablesRequest
 *  com.amazonaws.services.dynamodbv2.model.ListTablesResult
 *  com.amazonaws.services.dynamodbv2.model.ListTagsOfResourceRequest
 *  com.amazonaws.services.dynamodbv2.model.ListTagsOfResourceResult
 *  com.amazonaws.services.dynamodbv2.model.PutItemRequest
 *  com.amazonaws.services.dynamodbv2.model.PutItemResult
 *  com.amazonaws.services.dynamodbv2.model.QueryRequest
 *  com.amazonaws.services.dynamodbv2.model.QueryResult
 *  com.amazonaws.services.dynamodbv2.model.ScanRequest
 *  com.amazonaws.services.dynamodbv2.model.ScanResult
 *  com.amazonaws.services.dynamodbv2.model.TagResourceRequest
 *  com.amazonaws.services.dynamodbv2.model.TagResourceResult
 *  com.amazonaws.services.dynamodbv2.model.TransactGetItemsRequest
 *  com.amazonaws.services.dynamodbv2.model.TransactGetItemsResult
 *  com.amazonaws.services.dynamodbv2.model.TransactWriteItemsRequest
 *  com.amazonaws.services.dynamodbv2.model.TransactWriteItemsResult
 *  com.amazonaws.services.dynamodbv2.model.UntagResourceRequest
 *  com.amazonaws.services.dynamodbv2.model.UntagResourceResult
 *  com.amazonaws.services.dynamodbv2.model.UpdateItemRequest
 *  com.amazonaws.services.dynamodbv2.model.UpdateItemResult
 *  com.amazonaws.services.dynamodbv2.model.UpdateTableRequest
 *  com.amazonaws.services.dynamodbv2.model.UpdateTableResult
 *  com.amazonaws.services.dynamodbv2.model.UpdateTimeToLiveRequest
 *  com.amazonaws.services.dynamodbv2.model.UpdateTimeToLiveResult
 *  org.apache.logging.log4j.Logger
 */
package com.amazonaws.services.dynamodbv2.local.server;

import com.amazonaws.services.dynamodbv2.exceptions.AWSExceptionFactory;
import com.amazonaws.services.dynamodbv2.exceptions.AmazonServiceExceptionType;
import com.amazonaws.services.dynamodbv2.exceptions.DynamoDBLocalServiceException;
import com.amazonaws.services.dynamodbv2.local.server.DynamoDBRequestHandler;
import com.amazonaws.services.dynamodbv2.local.server.TestControlAction;
import com.amazonaws.services.dynamodbv2.local.shared.access.AmazonDynamoDBLocal;
import com.amazonaws.services.dynamodbv2.local.shared.access.LocalDBAccess;
import com.amazonaws.services.dynamodbv2.local.shared.access.LocalDBClient;
import com.amazonaws.services.dynamodbv2.local.shared.access.sqlite.SQLiteDBAccess;
import com.amazonaws.services.dynamodbv2.local.shared.jobs.JobsRegister;
import com.amazonaws.services.dynamodbv2.local.shared.logging.LogManager;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.BatchExecuteStatementRequest;
import com.amazonaws.services.dynamodbv2.model.BatchExecuteStatementResult;
import com.amazonaws.services.dynamodbv2.model.BatchGetItemRequest;
import com.amazonaws.services.dynamodbv2.model.BatchGetItemResult;
import com.amazonaws.services.dynamodbv2.model.BatchWriteItemRequest;
import com.amazonaws.services.dynamodbv2.model.BatchWriteItemResult;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.CreateTableResult;
import com.amazonaws.services.dynamodbv2.model.DeleteItemRequest;
import com.amazonaws.services.dynamodbv2.model.DeleteItemResult;
import com.amazonaws.services.dynamodbv2.model.DeleteTableRequest;
import com.amazonaws.services.dynamodbv2.model.DeleteTableResult;
import com.amazonaws.services.dynamodbv2.model.DescribeLimitsRequest;
import com.amazonaws.services.dynamodbv2.model.DescribeLimitsResult;
import com.amazonaws.services.dynamodbv2.model.DescribeStreamRequest;
import com.amazonaws.services.dynamodbv2.model.DescribeStreamResult;
import com.amazonaws.services.dynamodbv2.model.DescribeTableRequest;
import com.amazonaws.services.dynamodbv2.model.DescribeTableResult;
import com.amazonaws.services.dynamodbv2.model.DescribeTimeToLiveRequest;
import com.amazonaws.services.dynamodbv2.model.DescribeTimeToLiveResult;
import com.amazonaws.services.dynamodbv2.model.ExecuteStatementRequest;
import com.amazonaws.services.dynamodbv2.model.ExecuteStatementResult;
import com.amazonaws.services.dynamodbv2.model.ExecuteTransactionRequest;
import com.amazonaws.services.dynamodbv2.model.ExecuteTransactionResult;
import com.amazonaws.services.dynamodbv2.model.GetItemRequest;
import com.amazonaws.services.dynamodbv2.model.GetItemResult;
import com.amazonaws.services.dynamodbv2.model.GetRecordsRequest;
import com.amazonaws.services.dynamodbv2.model.GetRecordsResult;
import com.amazonaws.services.dynamodbv2.model.GetShardIteratorRequest;
import com.amazonaws.services.dynamodbv2.model.GetShardIteratorResult;
import com.amazonaws.services.dynamodbv2.model.ListStreamsRequest;
import com.amazonaws.services.dynamodbv2.model.ListStreamsResult;
import com.amazonaws.services.dynamodbv2.model.ListTablesRequest;
import com.amazonaws.services.dynamodbv2.model.ListTablesResult;
import com.amazonaws.services.dynamodbv2.model.ListTagsOfResourceRequest;
import com.amazonaws.services.dynamodbv2.model.ListTagsOfResourceResult;
import com.amazonaws.services.dynamodbv2.model.PutItemRequest;
import com.amazonaws.services.dynamodbv2.model.PutItemResult;
import com.amazonaws.services.dynamodbv2.model.QueryRequest;
import com.amazonaws.services.dynamodbv2.model.QueryResult;
import com.amazonaws.services.dynamodbv2.model.ScanRequest;
import com.amazonaws.services.dynamodbv2.model.ScanResult;
import com.amazonaws.services.dynamodbv2.model.TagResourceRequest;
import com.amazonaws.services.dynamodbv2.model.TagResourceResult;
import com.amazonaws.services.dynamodbv2.model.TransactGetItemsRequest;
import com.amazonaws.services.dynamodbv2.model.TransactGetItemsResult;
import com.amazonaws.services.dynamodbv2.model.TransactWriteItemsRequest;
import com.amazonaws.services.dynamodbv2.model.TransactWriteItemsResult;
import com.amazonaws.services.dynamodbv2.model.UntagResourceRequest;
import com.amazonaws.services.dynamodbv2.model.UntagResourceResult;
import com.amazonaws.services.dynamodbv2.model.UpdateItemRequest;
import com.amazonaws.services.dynamodbv2.model.UpdateItemResult;
import com.amazonaws.services.dynamodbv2.model.UpdateTableRequest;
import com.amazonaws.services.dynamodbv2.model.UpdateTableResult;
import com.amazonaws.services.dynamodbv2.model.UpdateTimeToLiveRequest;
import com.amazonaws.services.dynamodbv2.model.UpdateTimeToLiveResult;
import java.io.File;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import org.apache.logging.log4j.Logger;

public class LocalDynamoDBRequestHandler
extends DynamoDBRequestHandler {
    public static final String DB_FILE_EXTENSION = ".db";
    private static final String SHARED_DB_NAME = "shared-local-instance";
    static Logger logger = LogManager.getLogger(LocalDynamoDBRequestHandler.class);
    private final Map<String, AmazonDynamoDBLocal> dbRequestHandlers;
    private final boolean runInMemory;
    private final String dbFileLocation;
    private final boolean sharedDb;
    private final boolean delayTransientStatuses;
    private final boolean isTestControlEnabled;

    public LocalDynamoDBRequestHandler(int authorityLevel, boolean runInMemory, String dbFileLocation, boolean sharedDb, boolean delayTransientStatuses) throws DynamoDBLocalServiceException {
        this(authorityLevel, runInMemory, dbFileLocation, sharedDb, delayTransientStatuses, false);
    }

    LocalDynamoDBRequestHandler(int authorityLevel, boolean runInMemory, String dbFileLocation, boolean sharedDb, boolean delayTransientStatuses, boolean testControlEnabled) throws DynamoDBLocalServiceException {
        super(authorityLevel);
        this.dbFileLocation = dbFileLocation;
        this.runInMemory = runInMemory;
        this.sharedDb = sharedDb;
        this.delayTransientStatuses = delayTransientStatuses;
        this.isTestControlEnabled = testControlEnabled;
        if (runInMemory && dbFileLocation != null) {
            throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.INTERNAL_FAILURE, "both inMemory and dbFileLocation set");
        }
        this.dbRequestHandlers = new ConcurrentHashMap<String, AmazonDynamoDBLocal>();
    }

    @Deprecated
    public LocalDynamoDBRequestHandler(int authorityLevel, boolean runInMemory, String dbFileLocation, boolean sharedDb) throws DynamoDBLocalServiceException {
        this(authorityLevel, runInMemory, dbFileLocation, sharedDb, false, false);
    }

    public static String generateDbFileRelativePath(String dbFileLocation, String dbName) {
        StringBuilder sb = new StringBuilder();
        return (dbFileLocation != null ? sb.append(dbFileLocation).append(File.separator) : sb).append(dbName).append(DB_FILE_EXTENSION).toString();
    }

    private static LocalDBAccess getDBAccess(String dbName, String dbFileLocation, boolean runInMemory) {
        if (runInMemory) {
            return new SQLiteDBAccess();
        }
        if (dbFileLocation != null) {
            return new SQLiteDBAccess(dbFileLocation + File.separator + dbName + DB_FILE_EXTENSION);
        }
        return new SQLiteDBAccess(dbName + DB_FILE_EXTENSION);
    }

    private synchronized AmazonDynamoDBLocal getHandler(String credentials) {
        String dbName;
        String string = dbName = this.sharedDb ? SHARED_DB_NAME : credentials;
        if (!this.dbRequestHandlers.containsKey(dbName)) {
            JobsRegister jobs = new JobsRegister(Executors.newFixedThreadPool(10), this.delayTransientStatuses);
            LocalDBClient client = new LocalDBClient(LocalDynamoDBRequestHandler.getDBAccess(dbName, this.dbFileLocation, this.runInMemory), jobs);
            this.dbRequestHandlers.put(dbName, client);
        }
        return this.dbRequestHandlers.get(dbName);
    }

    @Override
    public void shutdown() {
        for (AmazonDynamoDBLocal client : this.dbRequestHandlers.values()) {
            client.shutdown();
        }
    }

    @Override
    public BatchGetItemResult batchGetItem(String accessKey, String region, BatchGetItemRequest req) {
        return this.getHandler(this.getCredentialsString(accessKey, region)).amazonDynamoDB().batchGetItem(req);
    }

    @Override
    public BatchWriteItemResult batchWriteItem(String accessKey, String region, BatchWriteItemRequest req) {
        return this.getHandler(this.getCredentialsString(accessKey, region)).amazonDynamoDB().batchWriteItem(req);
    }

    @Override
    public TransactWriteItemsResult transactWriteItems(String accessKey, String region, TransactWriteItemsRequest req) {
        return this.getHandler(this.getCredentialsString(accessKey, region)).amazonDynamoDB().transactWriteItems(req);
    }

    @Override
    public TransactGetItemsResult transactGetItems(String accessKey, String region, TransactGetItemsRequest req) {
        return this.getHandler(this.getCredentialsString(accessKey, region)).amazonDynamoDB().transactGetItems(req);
    }

    @Override
    public CreateTableResult createTable(String accessKey, String region, CreateTableRequest req) {
        return this.getHandler(this.getCredentialsString(accessKey, region)).amazonDynamoDB().createTable(req);
    }

    @Override
    public DeleteItemResult deleteItem(String accessKey, String region, DeleteItemRequest req) {
        return this.getHandler(this.getCredentialsString(accessKey, region)).amazonDynamoDB().deleteItem(req);
    }

    @Override
    public DeleteTableResult deleteTable(String accessKey, String region, DeleteTableRequest req) {
        return this.getHandler(this.getCredentialsString(accessKey, region)).amazonDynamoDB().deleteTable(req);
    }

    @Override
    public DescribeStreamResult describeStream(String accessKey, String region, DescribeStreamRequest req) {
        return this.getHandler(this.getCredentialsString(accessKey, region)).amazonDynamoDBStreams().describeStream(req);
    }

    @Override
    public DescribeTableResult describeTable(String accessKey, String region, DescribeTableRequest req) {
        return this.getHandler(this.getCredentialsString(accessKey, region)).amazonDynamoDB().describeTable(req);
    }

    @Override
    public DescribeTimeToLiveResult describeTimeToLive(String accessKey, String region, DescribeTimeToLiveRequest req) {
        return this.getHandler(this.getCredentialsString(accessKey, region)).amazonDynamoDB().describeTimeToLive(req);
    }

    @Override
    public DescribeLimitsResult describeLimits(String accessKey, String region, DescribeLimitsRequest req) {
        return this.getHandler(this.getCredentialsString(accessKey, region)).amazonDynamoDB().describeLimits(req);
    }

    @Override
    public GetItemResult getItem(String accessKey, String region, GetItemRequest req) {
        return this.getHandler(this.getCredentialsString(accessKey, region)).amazonDynamoDB().getItem(req);
    }

    @Override
    public GetRecordsResult getRecords(String accessKey, String region, GetRecordsRequest req) {
        return this.getHandler(this.getCredentialsString(accessKey, region)).amazonDynamoDBStreams().getRecords(req);
    }

    @Override
    public GetShardIteratorResult getShardIterator(String accessKey, String region, GetShardIteratorRequest req) {
        return this.getHandler(this.getCredentialsString(accessKey, region)).amazonDynamoDBStreams().getShardIterator(req);
    }

    @Override
    public ListStreamsResult listStreams(String accessKey, String region, ListStreamsRequest req) {
        return this.getHandler(this.getCredentialsString(accessKey, region)).amazonDynamoDBStreams().listStreams(req);
    }

    @Override
    public ListTablesResult listTables(String accessKey, String region, ListTablesRequest req) {
        return this.getHandler(this.getCredentialsString(accessKey, region)).amazonDynamoDB().listTables(req);
    }

    @Override
    public ListTagsOfResourceResult listTagsOfResource(String accessKey, String region, ListTagsOfResourceRequest req) {
        return this.getHandler(this.getCredentialsString(accessKey, region)).amazonDynamoDB().listTagsOfResource(req);
    }

    @Override
    public PutItemResult putItem(String accessKey, String region, PutItemRequest req) {
        if (this.isTestControlEnabled && req != null && req.getTableName() != null && req.getTableName().equals("[TEST_CONTROL]")) {
            try {
                TestControlAction tcAction = TestControlAction.valueOf(((AttributeValue)req.getItem().get("ACTION")).getS());
                switch (tcAction) {
                    case DILATE_TIME: {
                        long timeDilation = Long.parseLong(((AttributeValue)req.getItem().get("HOURS")).getS());
                        this.getHandler(this.getCredentialsString(accessKey, region)).dilateEventTimes(TimeUnit.HOURS.toMillis(timeDilation));
                        return new PutItemResult();
                    }
                    case ROLLOVER: {
                        this.getHandler(this.getCredentialsString(accessKey, region)).triggerShardRollovers();
                        return new PutItemResult();
                    }
                }
                throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, "Invalid Test Control Action.");
            } catch (NumberFormatException e) {
                throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, "Invalid parameter supplied.");
            } catch (IllegalArgumentException e) {
                throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, "Invalid Test Control Action.");
            } catch (NullPointerException e) {
                throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, "Test Control is missing parameters.");
            }
        }
        return this.getHandler(this.getCredentialsString(accessKey, region)).amazonDynamoDB().putItem(req);
    }

    @Override
    public QueryResult query(String accessKey, String region, QueryRequest req) {
        return this.getHandler(this.getCredentialsString(accessKey, region)).amazonDynamoDB().query(req);
    }

    @Override
    public ScanResult scan(String accessKey, String region, ScanRequest req) {
        return this.getHandler(this.getCredentialsString(accessKey, region)).amazonDynamoDB().scan(req);
    }

    @Override
    public TagResourceResult tagResource(String accessKey, String region, TagResourceRequest req) {
        return this.getHandler(this.getCredentialsString(accessKey, region)).amazonDynamoDB().tagResource(req);
    }

    @Override
    public UntagResourceResult untagResource(String accessKey, String region, UntagResourceRequest req) {
        return this.getHandler(this.getCredentialsString(accessKey, region)).amazonDynamoDB().untagResource(req);
    }

    @Override
    public UpdateItemResult updateItem(String accessKey, String region, UpdateItemRequest req) {
        return this.getHandler(this.getCredentialsString(accessKey, region)).amazonDynamoDB().updateItem(req);
    }

    @Override
    public UpdateTableResult updateTable(String accessKey, String region, UpdateTableRequest req) {
        return this.getHandler(this.getCredentialsString(accessKey, region)).amazonDynamoDB().updateTable(req);
    }

    @Override
    public UpdateTimeToLiveResult updateTimeToLive(String accessKey, String region, UpdateTimeToLiveRequest req) {
        return this.getHandler(this.getCredentialsString(accessKey, region)).amazonDynamoDB().updateTimeToLive(req);
    }

    @Override
    public ExecuteStatementResult executeStatement(String accessKey, String region, ExecuteStatementRequest req) {
        return this.getHandler(this.getCredentialsString(accessKey, region)).amazonDynamoDB().executeStatement(req);
    }

    @Override
    public BatchExecuteStatementResult batchExecuteStatement(String accessKey, String region, BatchExecuteStatementRequest req) {
        return this.getHandler(this.getCredentialsString(accessKey, region)).amazonDynamoDB().batchExecuteStatement(req);
    }

    @Override
    public ExecuteTransactionResult executeTransaction(String accessKey, String region, ExecuteTransactionRequest req) {
        return this.getHandler(this.getCredentialsString(accessKey, region)).amazonDynamoDB().executeTransaction(req);
    }

    private String getCredentialsString(String accessKey, String region) {
        return accessKey + "_" + region;
    }
}

