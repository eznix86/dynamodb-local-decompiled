/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  com.amazonaws.AmazonClientException
 *  com.amazonaws.AmazonWebServiceRequest
 *  com.amazonaws.ResponseMetadata
 *  com.amazonaws.regions.Region
 *  com.amazonaws.services.dynamodbv2.AbstractAmazonDynamoDB
 *  com.amazonaws.services.dynamodbv2.AmazonDynamoDB
 *  com.amazonaws.services.dynamodbv2.model.AttributeDefinition
 *  com.amazonaws.services.dynamodbv2.model.AttributeValue
 *  com.amazonaws.services.dynamodbv2.model.AttributeValueUpdate
 *  com.amazonaws.services.dynamodbv2.model.BatchExecuteStatementRequest
 *  com.amazonaws.services.dynamodbv2.model.BatchExecuteStatementResult
 *  com.amazonaws.services.dynamodbv2.model.BatchGetItemRequest
 *  com.amazonaws.services.dynamodbv2.model.BatchGetItemResult
 *  com.amazonaws.services.dynamodbv2.model.BatchWriteItemRequest
 *  com.amazonaws.services.dynamodbv2.model.BatchWriteItemResult
 *  com.amazonaws.services.dynamodbv2.model.Condition
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
 *  com.amazonaws.services.dynamodbv2.model.KeySchemaElement
 *  com.amazonaws.services.dynamodbv2.model.KeysAndAttributes
 *  com.amazonaws.services.dynamodbv2.model.ListTablesRequest
 *  com.amazonaws.services.dynamodbv2.model.ListTablesResult
 *  com.amazonaws.services.dynamodbv2.model.ListTagsOfResourceRequest
 *  com.amazonaws.services.dynamodbv2.model.ListTagsOfResourceResult
 *  com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput
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
 *  com.amazonaws.services.dynamodbv2.model.WriteRequest
 *  com.amazonaws.services.dynamodbv2.waiters.AmazonDynamoDBWaiters
 */
package com.amazonaws.services.dynamodbv2.local.shared.access.awssdkv1.client;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonWebServiceRequest;
import com.amazonaws.ResponseMetadata;
import com.amazonaws.regions.Region;
import com.amazonaws.services.dynamodbv2.AbstractAmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodel.DocumentFactory;
import com.amazonaws.services.dynamodbv2.dbenv.DbEnv;
import com.amazonaws.services.dynamodbv2.exceptions.AWSExceptionFactory;
import com.amazonaws.services.dynamodbv2.exceptions.AmazonServiceExceptionType;
import com.amazonaws.services.dynamodbv2.local.shared.access.DDBType;
import com.amazonaws.services.dynamodbv2.local.shared.access.ListTablesResultInfo;
import com.amazonaws.services.dynamodbv2.local.shared.access.LocalDBAccess;
import com.amazonaws.services.dynamodbv2.local.shared.access.LocalDBInputConverter;
import com.amazonaws.services.dynamodbv2.local.shared.access.LocalDBOutputConverter;
import com.amazonaws.services.dynamodbv2.local.shared.access.LocalDBUtils;
import com.amazonaws.services.dynamodbv2.local.shared.access.api.cp.CreateTableFunction;
import com.amazonaws.services.dynamodbv2.local.shared.access.api.cp.DeleteTableFunction;
import com.amazonaws.services.dynamodbv2.local.shared.access.api.cp.DescribeLimitsFunction;
import com.amazonaws.services.dynamodbv2.local.shared.access.api.cp.DescribeTableFunction;
import com.amazonaws.services.dynamodbv2.local.shared.access.api.cp.ListTablesFunction;
import com.amazonaws.services.dynamodbv2.local.shared.access.api.cp.UpdateTableFunction;
import com.amazonaws.services.dynamodbv2.local.shared.access.api.dp.BatchExecuteStatementFunction;
import com.amazonaws.services.dynamodbv2.local.shared.access.api.dp.BatchGetItemFunction;
import com.amazonaws.services.dynamodbv2.local.shared.access.api.dp.BatchWriteItemFunction;
import com.amazonaws.services.dynamodbv2.local.shared.access.api.dp.DeleteItemFunction;
import com.amazonaws.services.dynamodbv2.local.shared.access.api.dp.ExecuteStatementFunction;
import com.amazonaws.services.dynamodbv2.local.shared.access.api.dp.ExecuteTransactionFunction;
import com.amazonaws.services.dynamodbv2.local.shared.access.api.dp.GetItemFunction;
import com.amazonaws.services.dynamodbv2.local.shared.access.api.dp.PutItemFunction;
import com.amazonaws.services.dynamodbv2.local.shared.access.api.dp.QueryFunction;
import com.amazonaws.services.dynamodbv2.local.shared.access.api.dp.ScanFunction;
import com.amazonaws.services.dynamodbv2.local.shared.access.api.dp.TransactGetItemsFunction;
import com.amazonaws.services.dynamodbv2.local.shared.access.api.dp.TransactWriteItemsFunction;
import com.amazonaws.services.dynamodbv2.local.shared.access.api.dp.UpdateItemFunction;
import com.amazonaws.services.dynamodbv2.local.shared.access.api.ttl.DescribeTimeToLiveFunction;
import com.amazonaws.services.dynamodbv2.local.shared.access.api.ttl.UpdateTimeToLiveFunction;
import com.amazonaws.services.dynamodbv2.local.shared.dataaccess.LocalDocumentFactory;
import com.amazonaws.services.dynamodbv2.local.shared.env.LocalDBEnv;
import com.amazonaws.services.dynamodbv2.local.shared.env.LocalPartiQLDbEnv;
import com.amazonaws.services.dynamodbv2.local.shared.exceptions.LocalDBClientExceptionMessage;
import com.amazonaws.services.dynamodbv2.local.shared.helpers.TransactionsEnabledMode;
import com.amazonaws.services.dynamodbv2.local.shared.jobs.CreateGSIJobScheduler;
import com.amazonaws.services.dynamodbv2.local.shared.jobs.DeleteGSIJobScheduler;
import com.amazonaws.services.dynamodbv2.local.shared.jobs.JobsRegister;
import com.amazonaws.services.dynamodbv2.local.shared.jobs.TimeToLiveDeletionJobScheduler;
import com.amazonaws.services.dynamodbv2.local.shared.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.AttributeDefinition;
import com.amazonaws.services.dynamodbv2.model.AttributeValueUpdate;
import com.amazonaws.services.dynamodbv2.model.BatchExecuteStatementRequest;
import com.amazonaws.services.dynamodbv2.model.BatchExecuteStatementResult;
import com.amazonaws.services.dynamodbv2.model.BatchGetItemRequest;
import com.amazonaws.services.dynamodbv2.model.BatchGetItemResult;
import com.amazonaws.services.dynamodbv2.model.BatchWriteItemRequest;
import com.amazonaws.services.dynamodbv2.model.BatchWriteItemResult;
import com.amazonaws.services.dynamodbv2.model.Condition;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.CreateTableResult;
import com.amazonaws.services.dynamodbv2.model.DeleteItemRequest;
import com.amazonaws.services.dynamodbv2.model.DeleteItemResult;
import com.amazonaws.services.dynamodbv2.model.DeleteTableRequest;
import com.amazonaws.services.dynamodbv2.model.DeleteTableResult;
import com.amazonaws.services.dynamodbv2.model.DescribeLimitsRequest;
import com.amazonaws.services.dynamodbv2.model.DescribeLimitsResult;
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
import com.amazonaws.services.dynamodbv2.model.KeySchemaElement;
import com.amazonaws.services.dynamodbv2.model.KeysAndAttributes;
import com.amazonaws.services.dynamodbv2.model.ListTablesRequest;
import com.amazonaws.services.dynamodbv2.model.ListTablesResult;
import com.amazonaws.services.dynamodbv2.model.ListTagsOfResourceRequest;
import com.amazonaws.services.dynamodbv2.model.ListTagsOfResourceResult;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
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
import com.amazonaws.services.dynamodbv2.model.WriteRequest;
import com.amazonaws.services.dynamodbv2.waiters.AmazonDynamoDBWaiters;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;

public class LocalAmazonDynamoDB
extends AbstractAmazonDynamoDB {
    private final LocalDBAccess dbAccess;
    private final JobsRegister jobs;
    private final LocalDBInputConverter inputConverter;
    private final DbEnv localDBEnv;
    private final DocumentFactory documentFactory;
    private final LocalDBOutputConverter localDBOutputConverter;
    private final AWSExceptionFactory awsExceptionFactory;
    private final LocalPartiQLDbEnv localPartiQLDbEnv;
    private final CreateTableFunction createTable;
    private final DeleteTableFunction deleteTable;
    private final DescribeTableFunction describeTable;
    private final DescribeLimitsFunction describeLimits;
    private final ListTablesFunction listTables;
    private final UpdateTableFunction updateTable;
    private final DescribeTimeToLiveFunction describeTimeToLive;
    private final UpdateTimeToLiveFunction updateTimeToLive;
    private final BatchGetItemFunction batchGetItem;
    private final BatchWriteItemFunction batchWriteItem;
    private final DeleteItemFunction deleteItem;
    private final GetItemFunction getItem;
    private final PutItemFunction putItem;
    private final QueryFunction query;
    private final ScanFunction scan;
    private final UpdateItemFunction updateItem;
    private final TransactGetItemsFunction transactGetItems;
    private final TransactWriteItemsFunction transactWriteItems;
    private final ExecuteStatementFunction executeStatementFunction;
    private final BatchExecuteStatementFunction batchExecuteStatementFunction;
    private final ExecuteTransactionFunction executeTransactionFunction;
    private volatile AmazonDynamoDBWaiters waiters;

    public LocalAmazonDynamoDB(LocalDBAccess dbAccess, JobsRegister jobs) {
        this.dbAccess = dbAccess;
        this.localDBEnv = new LocalDBEnv();
        this.localPartiQLDbEnv = new LocalPartiQLDbEnv();
        this.documentFactory = new LocalDocumentFactory();
        this.awsExceptionFactory = new AWSExceptionFactory();
        this.localDBOutputConverter = new LocalDBOutputConverter();
        this.inputConverter = new LocalDBInputConverter(this.localDBEnv, this.awsExceptionFactory, this.documentFactory, 65536);
        this.jobs = jobs;
        this.jobs.schedule(new CreateGSIJobScheduler(dbAccess, this.jobs));
        this.jobs.schedule(new DeleteGSIJobScheduler(dbAccess, this.jobs));
        this.jobs.schedule(new TimeToLiveDeletionJobScheduler(dbAccess, this, this.inputConverter, this.jobs));
        this.createTable = new CreateTableFunction(dbAccess);
        this.deleteTable = new DeleteTableFunction(dbAccess);
        this.describeTable = new DescribeTableFunction(dbAccess);
        this.describeLimits = new DescribeLimitsFunction();
        this.listTables = new ListTablesFunction(dbAccess);
        this.updateTable = new UpdateTableFunction(dbAccess);
        this.describeTimeToLive = new DescribeTimeToLiveFunction(dbAccess);
        this.updateTimeToLive = new UpdateTimeToLiveFunction(dbAccess);
        this.batchGetItem = new BatchGetItemFunction(dbAccess, this.inputConverter, this.localDBOutputConverter, this.awsExceptionFactory, this.localDBEnv);
        this.batchWriteItem = new BatchWriteItemFunction(dbAccess, this.localDBEnv, this.inputConverter, this.localDBOutputConverter, this.awsExceptionFactory, this.documentFactory);
        this.deleteItem = new DeleteItemFunction(dbAccess, this.localDBEnv, this.inputConverter, this.localDBOutputConverter, this.awsExceptionFactory, this.documentFactory, TransactionsEnabledMode.TRANSACTIONS_DISABLED);
        this.getItem = new GetItemFunction(dbAccess, this.localDBEnv, this.inputConverter, this.localDBOutputConverter, this.awsExceptionFactory, TransactionsEnabledMode.TRANSACTIONS_DISABLED);
        this.putItem = new PutItemFunction(dbAccess, this.localDBEnv, this.inputConverter, this.localDBOutputConverter, this.awsExceptionFactory, this.documentFactory, TransactionsEnabledMode.TRANSACTIONS_DISABLED);
        this.query = new QueryFunction(dbAccess, this.localDBEnv, this.inputConverter, this.localDBOutputConverter, this.awsExceptionFactory, this.documentFactory);
        this.scan = new ScanFunction(dbAccess, this.localDBEnv, this.inputConverter, this.localDBOutputConverter, this.awsExceptionFactory, this.documentFactory);
        this.updateItem = new UpdateItemFunction(dbAccess, this.localDBEnv, this.inputConverter, this.localDBOutputConverter, this.awsExceptionFactory, this.documentFactory, TransactionsEnabledMode.TRANSACTIONS_DISABLED);
        this.transactGetItems = new TransactGetItemsFunction(dbAccess, this.inputConverter, this.localDBOutputConverter, this.awsExceptionFactory, this.localDBEnv);
        this.transactWriteItems = new TransactWriteItemsFunction(dbAccess, this.inputConverter, this.localDBOutputConverter, this.awsExceptionFactory, this.localDBEnv, this.documentFactory);
        this.executeStatementFunction = new ExecuteStatementFunction(dbAccess, this.localPartiQLDbEnv, this.inputConverter, this.localDBOutputConverter, this.awsExceptionFactory, this.documentFactory, TransactionsEnabledMode.TRANSACTIONS_DISABLED);
        this.batchExecuteStatementFunction = new BatchExecuteStatementFunction(dbAccess, this.localPartiQLDbEnv, this.inputConverter, this.localDBOutputConverter, this.awsExceptionFactory, this.documentFactory, TransactionsEnabledMode.TRANSACTIONS_DISABLED);
        this.executeTransactionFunction = new ExecuteTransactionFunction(dbAccess, this.localPartiQLDbEnv, this.inputConverter, this.localDBOutputConverter, this.awsExceptionFactory, this.documentFactory);
    }

    public BatchGetItemResult batchGetItem(BatchGetItemRequest batchGetItemRequest) throws AmazonClientException {
        return this.batchGetItem.apply(batchGetItemRequest);
    }

    public ListTagsOfResourceResult listTagsOfResource(ListTagsOfResourceRequest listTagsOfResourceRequest) {
        throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.UNKNOWN_OPERATION_EXCEPTION, LocalDBClientExceptionMessage.TAGGING_NOT_SUPPORTED.getMessage());
    }

    public TagResourceResult tagResource(TagResourceRequest tagResourceRequest) {
        throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.UNKNOWN_OPERATION_EXCEPTION, LocalDBClientExceptionMessage.TAGGING_NOT_SUPPORTED.getMessage());
    }

    public UntagResourceResult untagResource(UntagResourceRequest untagResourceRequest) {
        throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.UNKNOWN_OPERATION_EXCEPTION, LocalDBClientExceptionMessage.TAGGING_NOT_SUPPORTED.getMessage());
    }

    public BatchWriteItemResult batchWriteItem(BatchWriteItemRequest batchWriteItemRequest) throws AmazonClientException {
        return this.batchWriteItem.apply(batchWriteItemRequest);
    }

    public CreateTableResult createTable(CreateTableRequest createTableRequest) throws AmazonClientException {
        return this.createTable.apply(createTableRequest);
    }

    public DeleteItemResult deleteItem(DeleteItemRequest deleteItemRequest) throws AmazonClientException {
        return this.deleteItem.apply(deleteItemRequest);
    }

    public DeleteTableResult deleteTable(DeleteTableRequest deleteTableRequest) throws AmazonClientException {
        return this.deleteTable.apply(deleteTableRequest);
    }

    public DescribeTableResult describeTable(DescribeTableRequest describeTableRequest) throws AmazonClientException {
        return this.describeTable.apply(describeTableRequest);
    }

    public DescribeLimitsResult describeLimits(DescribeLimitsRequest describeLimitsRequest) {
        return this.describeLimits.apply(describeLimitsRequest);
    }

    public DescribeTimeToLiveResult describeTimeToLive(DescribeTimeToLiveRequest describeTimeToLiveRequest) {
        return this.describeTimeToLive.apply(describeTimeToLiveRequest);
    }

    public ResponseMetadata getCachedResponseMetadata(AmazonWebServiceRequest request) {
        return null;
    }

    public GetItemResult getItem(GetItemRequest getItemRequest) throws AmazonClientException {
        return this.getItem.apply(getItemRequest);
    }

    public ListTablesResult listTables() throws AmazonClientException {
        ListTablesResultInfo initResults = this.dbAccess.listTables(null, 100L);
        return new ListTablesResult().withTableNames(initResults.getTableNames()).withLastEvaluatedTableName(initResults.getLastEvaluatedTableName());
    }

    public ListTablesResult listTables(ListTablesRequest listTablesRequest) throws AmazonClientException {
        return this.listTables.apply(listTablesRequest);
    }

    public PutItemResult putItem(PutItemRequest putItemRequest) throws AmazonClientException {
        return this.putItem.apply(putItemRequest);
    }

    public QueryResult query(QueryRequest queryRequest) throws AmazonClientException {
        return this.query.apply(queryRequest);
    }

    public ScanResult scan(ScanRequest scanRequest) throws AmazonClientException {
        return this.scan.apply(scanRequest);
    }

    public void setEndpoint(String endpoint) throws IllegalArgumentException {
        throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.INVALID_ACTION, "Regions are not supported in LocalDynamoDB");
    }

    public UpdateItemResult updateItem(UpdateItemRequest updateItemRequest) throws AmazonClientException {
        return this.updateItem.apply(updateItemRequest);
    }

    public UpdateTableResult updateTable(UpdateTableRequest updateTableRequest) throws AmazonClientException {
        return this.updateTable.apply(updateTableRequest);
    }

    public UpdateTimeToLiveResult updateTimeToLive(UpdateTimeToLiveRequest updateTimeToLiveRequest) {
        return this.updateTimeToLive.apply(updateTimeToLiveRequest);
    }

    private void validateTableName(String tableName) {
        if (StringUtils.isEmpty(tableName)) {
            throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, LocalDBClientExceptionMessage.BAD_TABLE_NAME.getMessage());
        }
        if (tableName.length() < 3 || tableName.length() > 255) {
            throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, LocalDBClientExceptionMessage.BAD_TABLE_NAME.getMessage());
        }
        if (!tableName.matches("[-a-zA-Z0-9._]*")) {
            throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, LocalDBClientExceptionMessage.BAD_TABLE_NAME.getMessage());
        }
    }

    private boolean isAttributeOfSetType(AttributeValue expected) {
        DDBType type = LocalDBUtils.getDataTypeOfAttributeValue(expected);
        if (type == null) {
            return false;
        }
        return type.isSet();
    }

    public void setRegion(Region region) throws IllegalArgumentException {
        throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.INVALID_ACTION, "Regions are not supported in LocalDynamoDB");
    }

    public BatchGetItemResult batchGetItem(Map<String, KeysAndAttributes> arg0) throws AmazonClientException {
        BatchGetItemRequest request = new BatchGetItemRequest(arg0);
        return this.batchGetItem(request);
    }

    public BatchGetItemResult batchGetItem(Map<String, KeysAndAttributes> arg0, String arg1) throws AmazonClientException {
        BatchGetItemRequest request = new BatchGetItemRequest(arg0, arg1);
        return this.batchGetItem(request);
    }

    public BatchWriteItemResult batchWriteItem(Map<String, List<WriteRequest>> arg0) throws AmazonClientException {
        BatchWriteItemRequest request = new BatchWriteItemRequest(arg0);
        return this.batchWriteItem(request);
    }

    public TransactGetItemsResult transactGetItems(TransactGetItemsRequest request) {
        return this.transactGetItems.apply(request);
    }

    public TransactWriteItemsResult transactWriteItems(TransactWriteItemsRequest request) {
        return this.transactWriteItems.apply(request);
    }

    public CreateTableResult createTable(List<AttributeDefinition> arg0, String arg1, List<KeySchemaElement> arg2, ProvisionedThroughput arg3) throws AmazonClientException {
        CreateTableRequest request = new CreateTableRequest(arg0, arg1, arg2, arg3);
        return this.createTable(request);
    }

    public DeleteItemResult deleteItem(String arg0, Map<String, com.amazonaws.services.dynamodbv2.model.AttributeValue> arg1) throws AmazonClientException {
        DeleteItemRequest request = new DeleteItemRequest(arg0, arg1);
        return this.deleteItem(request);
    }

    public DeleteItemResult deleteItem(String arg0, Map<String, com.amazonaws.services.dynamodbv2.model.AttributeValue> arg1, String arg2) throws AmazonClientException {
        DeleteItemRequest request = new DeleteItemRequest(arg0, arg1, arg2);
        return this.deleteItem(request);
    }

    public DeleteTableResult deleteTable(String arg0) throws AmazonClientException {
        DeleteTableRequest request = new DeleteTableRequest(arg0);
        return this.deleteTable(request);
    }

    public DescribeTableResult describeTable(String arg0) throws AmazonClientException {
        DescribeTableRequest request = new DescribeTableRequest(arg0);
        return this.describeTable(request);
    }

    public GetItemResult getItem(String arg0, Map<String, com.amazonaws.services.dynamodbv2.model.AttributeValue> arg1) throws AmazonClientException {
        GetItemRequest request = new GetItemRequest(arg0, arg1);
        return this.getItem(request);
    }

    public GetItemResult getItem(String arg0, Map<String, com.amazonaws.services.dynamodbv2.model.AttributeValue> arg1, Boolean arg2) throws AmazonClientException {
        GetItemRequest request = new GetItemRequest(arg0, arg1, arg2);
        return this.getItem(request);
    }

    public ListTablesResult listTables(String arg0) throws AmazonClientException {
        ListTablesRequest request = new ListTablesRequest(arg0);
        return this.listTables(request);
    }

    public ListTablesResult listTables(Integer arg0) throws AmazonClientException {
        ListTablesRequest request = new ListTablesRequest().withLimit(arg0);
        return this.listTables(request);
    }

    public ListTablesResult listTables(String arg0, Integer arg1) throws AmazonClientException {
        ListTablesRequest request = new ListTablesRequest(arg0, arg1);
        return this.listTables(request);
    }

    public PutItemResult putItem(String arg0, Map<String, com.amazonaws.services.dynamodbv2.model.AttributeValue> arg1) throws AmazonClientException {
        PutItemRequest request = new PutItemRequest(arg0, arg1);
        return this.putItem(request);
    }

    public PutItemResult putItem(String arg0, Map<String, com.amazonaws.services.dynamodbv2.model.AttributeValue> arg1, String arg2) throws AmazonClientException {
        PutItemRequest request = new PutItemRequest(arg0, arg1, arg2);
        return this.putItem(request);
    }

    public ScanResult scan(String arg0, List<String> arg1) throws AmazonClientException {
        ScanRequest request = new ScanRequest(arg0).withAttributesToGet(arg1);
        return this.scan(request);
    }

    public ScanResult scan(String arg0, Map<String, Condition> arg1) throws AmazonClientException {
        ScanRequest request = new ScanRequest(arg0).withScanFilter(arg1);
        return this.scan(request);
    }

    public ScanResult scan(String arg0, List<String> arg1, Map<String, Condition> arg2) throws AmazonClientException {
        ScanRequest request = new ScanRequest(arg0).withAttributesToGet(arg1).withScanFilter(arg2);
        return this.scan(request);
    }

    public UpdateItemResult updateItem(String arg0, Map<String, com.amazonaws.services.dynamodbv2.model.AttributeValue> arg1, Map<String, AttributeValueUpdate> arg2) throws AmazonClientException {
        UpdateItemRequest request = new UpdateItemRequest(arg0, arg1, arg2);
        return this.updateItem(request);
    }

    public UpdateItemResult updateItem(String arg0, Map<String, com.amazonaws.services.dynamodbv2.model.AttributeValue> arg1, Map<String, AttributeValueUpdate> arg2, String arg3) throws AmazonClientException {
        UpdateItemRequest request = new UpdateItemRequest(arg0, arg1, arg2, arg3);
        return this.updateItem(request);
    }

    public UpdateTableResult updateTable(String arg0, ProvisionedThroughput arg1) throws AmazonClientException {
        UpdateTableRequest request = new UpdateTableRequest(arg0, arg1);
        return this.updateTable(request);
    }

    public ExecuteStatementResult executeStatement(ExecuteStatementRequest request) {
        return this.executeStatementFunction.apply(request);
    }

    public BatchExecuteStatementResult batchExecuteStatement(BatchExecuteStatementRequest request) {
        return this.batchExecuteStatementFunction.apply(request);
    }

    public ExecuteTransactionResult executeTransaction(ExecuteTransactionRequest request) {
        return this.executeTransactionFunction.apply(request);
    }

    public void shutdown() {
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public AmazonDynamoDBWaiters waiters() {
        if (this.waiters == null) {
            LocalAmazonDynamoDB localAmazonDynamoDB = this;
            synchronized (localAmazonDynamoDB) {
                if (this.waiters == null) {
                    this.waiters = new AmazonDynamoDBWaiters((AmazonDynamoDB)this);
                }
            }
        }
        return this.waiters;
    }
}

