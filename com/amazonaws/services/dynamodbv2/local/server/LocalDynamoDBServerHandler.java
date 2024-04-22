/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  com.amazonaws.services.dynamodbv2.model.BatchExecuteStatementRequest
 *  com.amazonaws.services.dynamodbv2.model.BatchGetItemRequest
 *  com.amazonaws.services.dynamodbv2.model.BatchWriteItemRequest
 *  com.amazonaws.services.dynamodbv2.model.CreateTableRequest
 *  com.amazonaws.services.dynamodbv2.model.DeleteItemRequest
 *  com.amazonaws.services.dynamodbv2.model.DeleteTableRequest
 *  com.amazonaws.services.dynamodbv2.model.DescribeLimitsRequest
 *  com.amazonaws.services.dynamodbv2.model.DescribeStreamRequest
 *  com.amazonaws.services.dynamodbv2.model.DescribeTableRequest
 *  com.amazonaws.services.dynamodbv2.model.DescribeTimeToLiveRequest
 *  com.amazonaws.services.dynamodbv2.model.ExecuteStatementRequest
 *  com.amazonaws.services.dynamodbv2.model.ExecuteTransactionRequest
 *  com.amazonaws.services.dynamodbv2.model.GetItemRequest
 *  com.amazonaws.services.dynamodbv2.model.GetRecordsRequest
 *  com.amazonaws.services.dynamodbv2.model.GetShardIteratorRequest
 *  com.amazonaws.services.dynamodbv2.model.ListStreamsRequest
 *  com.amazonaws.services.dynamodbv2.model.ListTablesRequest
 *  com.amazonaws.services.dynamodbv2.model.ListTagsOfResourceRequest
 *  com.amazonaws.services.dynamodbv2.model.PutItemRequest
 *  com.amazonaws.services.dynamodbv2.model.QueryRequest
 *  com.amazonaws.services.dynamodbv2.model.ScanRequest
 *  com.amazonaws.services.dynamodbv2.model.TagResourceRequest
 *  com.amazonaws.services.dynamodbv2.model.TransactGetItemsRequest
 *  com.amazonaws.services.dynamodbv2.model.TransactWriteItemsRequest
 *  com.amazonaws.services.dynamodbv2.model.UntagResourceRequest
 *  com.amazonaws.services.dynamodbv2.model.UpdateItemRequest
 *  com.amazonaws.services.dynamodbv2.model.UpdateTableRequest
 *  com.amazonaws.services.dynamodbv2.model.UpdateTimeToLiveRequest
 *  org.apache.logging.log4j.Logger
 *  org.eclipse.jetty.io.Content$Source
 *  org.eclipse.jetty.server.Request
 *  org.eclipse.jetty.server.Response
 *  org.eclipse.jetty.util.Callback
 */
package com.amazonaws.services.dynamodbv2.local.server;

import com.amazonaws.services.dynamodbv2.dataMembers.RequestData;
import com.amazonaws.services.dynamodbv2.dataMembers.ResponseData;
import com.amazonaws.services.dynamodbv2.exceptions.AWSExceptionFactory;
import com.amazonaws.services.dynamodbv2.exceptions.AmazonServiceExceptionType;
import com.amazonaws.services.dynamodbv2.exceptions.DynamoDBLocalServiceException;
import com.amazonaws.services.dynamodbv2.local.dispatchers.BatchExecuteStatementDispatcher;
import com.amazonaws.services.dynamodbv2.local.dispatchers.BatchGetItemDispatcher;
import com.amazonaws.services.dynamodbv2.local.dispatchers.BatchWriteItemDispatcher;
import com.amazonaws.services.dynamodbv2.local.dispatchers.CreateTableDispatcher;
import com.amazonaws.services.dynamodbv2.local.dispatchers.DeleteItemDispatcher;
import com.amazonaws.services.dynamodbv2.local.dispatchers.DeleteTableDispatcher;
import com.amazonaws.services.dynamodbv2.local.dispatchers.DescribeLimitsDispatcher;
import com.amazonaws.services.dynamodbv2.local.dispatchers.DescribeStreamDispatcher;
import com.amazonaws.services.dynamodbv2.local.dispatchers.DescribeTableDispatcher;
import com.amazonaws.services.dynamodbv2.local.dispatchers.DescribeTimeToLiveDispatcher;
import com.amazonaws.services.dynamodbv2.local.dispatchers.Dispatcher;
import com.amazonaws.services.dynamodbv2.local.dispatchers.ExecuteStatementDispatcher;
import com.amazonaws.services.dynamodbv2.local.dispatchers.ExecuteTransactionDispatcher;
import com.amazonaws.services.dynamodbv2.local.dispatchers.GetItemDispatcher;
import com.amazonaws.services.dynamodbv2.local.dispatchers.GetRecordsDispatcher;
import com.amazonaws.services.dynamodbv2.local.dispatchers.GetShardIteratorDispatcher;
import com.amazonaws.services.dynamodbv2.local.dispatchers.ListStreamsDispatcher;
import com.amazonaws.services.dynamodbv2.local.dispatchers.ListTablesDispatcher;
import com.amazonaws.services.dynamodbv2.local.dispatchers.ListTagsOfResourceDispatcher;
import com.amazonaws.services.dynamodbv2.local.dispatchers.PutItemDispatcher;
import com.amazonaws.services.dynamodbv2.local.dispatchers.QueryDispatcher;
import com.amazonaws.services.dynamodbv2.local.dispatchers.ScanDispatcher;
import com.amazonaws.services.dynamodbv2.local.dispatchers.TagResourceDispatcher;
import com.amazonaws.services.dynamodbv2.local.dispatchers.TransactGetItemsDispatcher;
import com.amazonaws.services.dynamodbv2.local.dispatchers.TransactWriteItemsDispatcher;
import com.amazonaws.services.dynamodbv2.local.dispatchers.UntagResourceDispatcher;
import com.amazonaws.services.dynamodbv2.local.dispatchers.UpdateItemDispatcher;
import com.amazonaws.services.dynamodbv2.local.dispatchers.UpdateTableDispatcher;
import com.amazonaws.services.dynamodbv2.local.dispatchers.UpdateTimeToLiveDispatcher;
import com.amazonaws.services.dynamodbv2.local.exceptions.ExceptionBean;
import com.amazonaws.services.dynamodbv2.local.server.AbstractLocalDynamoDBServerHandler;
import com.amazonaws.services.dynamodbv2.local.server.DynamoDBRequestHandler;
import com.amazonaws.services.dynamodbv2.local.shared.logging.LogManager;
import com.amazonaws.services.dynamodbv2.model.BatchExecuteStatementRequest;
import com.amazonaws.services.dynamodbv2.model.BatchGetItemRequest;
import com.amazonaws.services.dynamodbv2.model.BatchWriteItemRequest;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.DeleteItemRequest;
import com.amazonaws.services.dynamodbv2.model.DeleteTableRequest;
import com.amazonaws.services.dynamodbv2.model.DescribeLimitsRequest;
import com.amazonaws.services.dynamodbv2.model.DescribeStreamRequest;
import com.amazonaws.services.dynamodbv2.model.DescribeTableRequest;
import com.amazonaws.services.dynamodbv2.model.DescribeTimeToLiveRequest;
import com.amazonaws.services.dynamodbv2.model.ExecuteStatementRequest;
import com.amazonaws.services.dynamodbv2.model.ExecuteTransactionRequest;
import com.amazonaws.services.dynamodbv2.model.GetItemRequest;
import com.amazonaws.services.dynamodbv2.model.GetRecordsRequest;
import com.amazonaws.services.dynamodbv2.model.GetShardIteratorRequest;
import com.amazonaws.services.dynamodbv2.model.ListStreamsRequest;
import com.amazonaws.services.dynamodbv2.model.ListTablesRequest;
import com.amazonaws.services.dynamodbv2.model.ListTagsOfResourceRequest;
import com.amazonaws.services.dynamodbv2.model.PutItemRequest;
import com.amazonaws.services.dynamodbv2.model.QueryRequest;
import com.amazonaws.services.dynamodbv2.model.ScanRequest;
import com.amazonaws.services.dynamodbv2.model.TagResourceRequest;
import com.amazonaws.services.dynamodbv2.model.TransactGetItemsRequest;
import com.amazonaws.services.dynamodbv2.model.TransactWriteItemsRequest;
import com.amazonaws.services.dynamodbv2.model.UntagResourceRequest;
import com.amazonaws.services.dynamodbv2.model.UpdateItemRequest;
import com.amazonaws.services.dynamodbv2.model.UpdateTableRequest;
import com.amazonaws.services.dynamodbv2.model.UpdateTimeToLiveRequest;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.UUID;
import java.util.zip.CRC32;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.io.Content;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Response;
import org.eclipse.jetty.util.Callback;

public class LocalDynamoDBServerHandler
extends AbstractLocalDynamoDBServerHandler {
    public static final String UPDATE_TIME_TO_LIVE = "UpdateTimeToLive";
    public static final String UPDATE_TABLE = "UpdateTable";
    public static final String UPDATE_ITEM = "UpdateItem";
    public static final String UNTAG_RESOURCE = "UntagResource";
    public static final String TAG_RESOURCE = "TagResource";
    public static final String SCAN = "Scan";
    public static final String QUERY = "Query";
    public static final String PUT_ITEM = "PutItem";
    public static final String LIST_TAGS_OF_RESOURCE = "ListTagsOfResource";
    public static final String LIST_TABLES = "ListTables";
    public static final String LIST_STREAMS = "ListStreams";
    public static final String GET_SHARD_ITERATOR = "GetShardIterator";
    public static final String GET_RECORDS = "GetRecords";
    public static final String GET_ITEM = "GetItem";
    public static final String DESCRIBE_TIME_TO_LIVE = "DescribeTimeToLive";
    public static final String DESCRIBE_TABLE = "DescribeTable";
    public static final String DESCRIBE_STREAM = "DescribeStream";
    public static final String DESCRIBE_LIMITS = "DescribeLimits";
    public static final String DELETE_TABLE = "DeleteTable";
    public static final String DELETE_ITEM = "DeleteItem";
    public static final String CREATE_TABLE = "CreateTable";
    public static final String BATCH_WRITE_ITEM = "BatchWriteItem";
    public static final String TRANSACT_WRITE_ITEMS = "TransactWriteItems";
    public static final String BATCH_GET_ITEM = "BatchGetItem";
    public static final String TRANSACT_GET_ITEMS = "TransactGetItems";
    public static final String EXECUTE_STATEMENT = "ExecuteStatement";
    public static final String EXECUTE_TRANSACTION = "ExecuteTransaction";
    public static final String BATCH_EXECUTE_STATEMENT = "BatchExecuteStatement";
    public static final String COMPATIBLE_VERSION_DYNAMO_DB = "DynamoDB_20120810";
    public static final String COMPATIBLE_VERSION_DYNAMO_DB_STREAMS = "DynamoDBStreams_20120810";
    static final Logger logger = LogManager.getLogger(LocalDynamoDBServerHandler.class);
    private static final String AUTHORIZATION_SPLIT = "=|/";
    private static final String TARGET_COMPONENTS_SPLIT = "\\.";
    private static final String VALID_REGION = "^[A-Za-z0-9]+(-[A-Za-z0-9]+)*$";
    private static final String VALID_ACCESS_KEY = "[A-Za-z0-9]+";
    private static final int MAX_REQUEST_SIZE = 0x1000000;
    private static final int BUFFER_SIZE_IN_BYTES = 1024;
    private static final String INCOMPATIBLE_VERSION_0 = "DynamoDB_20110924";
    private static final String INCOMPATIBLE_VERSION_1 = "DynamoDB_20111205";
    private static final byte[] EMPTY_BODY = new byte[0];
    private static final Map<String, Set> ACTION_VERSION_COMPATIBILITY = new HashMap<String, Set>();
    private static Map<String, Dispatcher> dispatchers;
    private static Map<String, Class> classes;
    private final HashSet<String> corsSet = new HashSet();

    public LocalDynamoDBServerHandler(DynamoDBRequestHandler primaryHandler, String corsParam) {
        super(primaryHandler);
        if (corsParam != null) {
            this.setUpCors(corsParam);
        }
        this.init();
    }

    private static byte[] fromStream(InputStream inStream) throws IOException {
        int bytesRead;
        byte[] buffer = new byte[1024];
        int totalBytesRead = 0;
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        while ((bytesRead = inStream.read(buffer)) != -1) {
            if ((totalBytesRead += bytesRead) > 0x1000000) {
                throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.REQUEST_TOO_LARGE);
            }
            output.write(buffer, 0, bytesRead);
        }
        return output.toByteArray();
    }

    private void setUpCors(String corsParam) {
        StringTokenizer st = new StringTokenizer(corsParam, ",");
        while (st.hasMoreTokens()) {
            this.corsSet.add(st.nextToken().trim());
        }
    }

    private void init() {
        dispatchers = new HashMap<String, Dispatcher>();
        dispatchers.put(BATCH_GET_ITEM, new BatchGetItemDispatcher(this.jsonMapper));
        dispatchers.put(TRANSACT_GET_ITEMS, new TransactGetItemsDispatcher(this.jsonMapper));
        dispatchers.put(BATCH_WRITE_ITEM, new BatchWriteItemDispatcher(this.jsonMapper));
        dispatchers.put(TRANSACT_WRITE_ITEMS, new TransactWriteItemsDispatcher(this.jsonMapper));
        dispatchers.put(CREATE_TABLE, new CreateTableDispatcher(this.jsonMapper));
        dispatchers.put(DELETE_ITEM, new DeleteItemDispatcher(this.jsonMapper));
        dispatchers.put(DELETE_TABLE, new DeleteTableDispatcher(this.jsonMapper));
        dispatchers.put(DESCRIBE_STREAM, new DescribeStreamDispatcher(this.jsonMapper));
        dispatchers.put(DESCRIBE_TABLE, new DescribeTableDispatcher(this.jsonMapper));
        dispatchers.put(DESCRIBE_LIMITS, new DescribeLimitsDispatcher(this.jsonMapper));
        dispatchers.put(DESCRIBE_TIME_TO_LIVE, new DescribeTimeToLiveDispatcher(this.jsonMapper));
        dispatchers.put(GET_ITEM, new GetItemDispatcher(this.jsonMapper));
        dispatchers.put(GET_RECORDS, new GetRecordsDispatcher(this.jsonMapper));
        dispatchers.put(GET_SHARD_ITERATOR, new GetShardIteratorDispatcher(this.jsonMapper));
        dispatchers.put(LIST_STREAMS, new ListStreamsDispatcher(this.jsonMapper));
        dispatchers.put(LIST_TABLES, new ListTablesDispatcher(this.jsonMapper));
        dispatchers.put(LIST_TAGS_OF_RESOURCE, new ListTagsOfResourceDispatcher(this.jsonMapper));
        dispatchers.put(PUT_ITEM, new PutItemDispatcher(this.jsonMapper));
        dispatchers.put(QUERY, new QueryDispatcher(this.jsonMapper));
        dispatchers.put(SCAN, new ScanDispatcher(this.jsonMapper));
        dispatchers.put(TAG_RESOURCE, new TagResourceDispatcher(this.jsonMapper));
        dispatchers.put(UNTAG_RESOURCE, new UntagResourceDispatcher(this.jsonMapper));
        dispatchers.put(UPDATE_ITEM, new UpdateItemDispatcher(this.jsonMapper));
        dispatchers.put(UPDATE_TABLE, new UpdateTableDispatcher(this.jsonMapper));
        dispatchers.put(UPDATE_TIME_TO_LIVE, new UpdateTimeToLiveDispatcher(this.jsonMapper));
        dispatchers.put(EXECUTE_STATEMENT, new ExecuteStatementDispatcher(this.jsonMapper));
        dispatchers.put(EXECUTE_TRANSACTION, new ExecuteTransactionDispatcher(this.jsonMapper));
        dispatchers.put(BATCH_EXECUTE_STATEMENT, new BatchExecuteStatementDispatcher(this.jsonMapper));
        classes = new HashMap<String, Class>();
        classes.put(BATCH_GET_ITEM, BatchGetItemRequest.class);
        classes.put(TRANSACT_GET_ITEMS, TransactGetItemsRequest.class);
        classes.put(BATCH_WRITE_ITEM, BatchWriteItemRequest.class);
        classes.put(TRANSACT_WRITE_ITEMS, TransactWriteItemsRequest.class);
        classes.put(CREATE_TABLE, CreateTableRequest.class);
        classes.put(DELETE_ITEM, DeleteItemRequest.class);
        classes.put(DELETE_TABLE, DeleteTableRequest.class);
        classes.put(DESCRIBE_STREAM, DescribeStreamRequest.class);
        classes.put(DESCRIBE_TABLE, DescribeTableRequest.class);
        classes.put(DESCRIBE_LIMITS, DescribeLimitsRequest.class);
        classes.put(DESCRIBE_TIME_TO_LIVE, DescribeTimeToLiveRequest.class);
        classes.put(GET_ITEM, GetItemRequest.class);
        classes.put(GET_RECORDS, GetRecordsRequest.class);
        classes.put(GET_SHARD_ITERATOR, GetShardIteratorRequest.class);
        classes.put(LIST_STREAMS, ListStreamsRequest.class);
        classes.put(LIST_TABLES, ListTablesRequest.class);
        classes.put(LIST_TAGS_OF_RESOURCE, ListTagsOfResourceRequest.class);
        classes.put(PUT_ITEM, PutItemRequest.class);
        classes.put(QUERY, QueryRequest.class);
        classes.put(SCAN, ScanRequest.class);
        classes.put(TAG_RESOURCE, TagResourceRequest.class);
        classes.put(UNTAG_RESOURCE, UntagResourceRequest.class);
        classes.put(UPDATE_ITEM, UpdateItemRequest.class);
        classes.put(UPDATE_TABLE, UpdateTableRequest.class);
        classes.put(UPDATE_TIME_TO_LIVE, UpdateTimeToLiveRequest.class);
        classes.put(EXECUTE_STATEMENT, ExecuteStatementRequest.class);
        classes.put(EXECUTE_TRANSACTION, ExecuteTransactionRequest.class);
        classes.put(BATCH_EXECUTE_STATEMENT, BatchExecuteStatementRequest.class);
    }

    private boolean isPreFlight(RequestData req) {
        if (req.getBaseRequest().getHeaders().get("Origin") != null && req.getBaseRequest().getMethod().equals("OPTIONS")) {
            return req.getBaseRequest().getHeaders().get("Access-Control-Request-Method") != null;
        }
        return false;
    }

    protected String getAccessKey(Request req) throws DynamoDBLocalServiceException {
        String[] auth = req.getHeaders().get("Authorization").split(AUTHORIZATION_SPLIT);
        String accessKeyForCheck = auth[1];
        String accessKey = "";
        if (!accessKeyForCheck.matches(VALID_ACCESS_KEY)) {
            throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.UNRECOGNIZED_CLIENT_EXCEPTION);
        }
        accessKey = accessKeyForCheck;
        return accessKey;
    }

    protected void packageDynamoDBResponse(RequestData req, ResponseData res) throws DynamoDBLocalServiceException {
        String[] amzTargetComponents;
        res.getResponse().getHeaders().put("Content-Type", "application/x-amz-json-1.0");
        if (req.getBaseRequest().getHeaders().get("Authorization") == null) {
            throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.MISSING_AUTHENTICATION_TOKEN);
        }
        String[] auth = req.getBaseRequest().getHeaders().get("Authorization").split(AUTHORIZATION_SPLIT);
        String accessKey = this.getAccessKey(req.getBaseRequest());
        String regionForCheck = auth[3];
        String region = "";
        if (regionForCheck.matches(VALID_REGION)) {
            region = regionForCheck;
        }
        if ((amzTargetComponents = req.getBaseRequest().getHeaders().get("X-Amz-Target").split(TARGET_COMPONENTS_SPLIT))[0].equals(INCOMPATIBLE_VERSION_0) || amzTargetComponents[0].equals(INCOMPATIBLE_VERSION_1)) {
            throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.INVALID_VERSION);
        }
        String version = amzTargetComponents[0];
        String amzTarget = amzTargetComponents[1];
        this.validateTargetActionVersionCompatibility(amzTarget, version);
        logger.info("accessKey: " + accessKey + "\t" + region);
        logger.info("target: " + amzTarget);
        Dispatcher dispatcher = dispatchers.get(amzTarget);
        if (dispatcher == null) {
            throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.INVALID_ACTION);
        }
        Class requestClass = classes.get(amzTarget);
        try {
            res.setResponseBody(dispatcher.enact(accessKey, region, this.jsonMapper.readValue(req.getRequestBody(), requestClass), this.primaryHandler));
        } catch (DynamoDBLocalServiceException e) {
            logger.warn("DynamoDBLocalServiceException exception occured", (Throwable)((Object)e));
            throw e;
        } catch (Exception e) {
            logger.error("Unexpected exception occured", (Throwable)e);
            throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.INTERNAL_FAILURE);
        }
        res.getResponse().setStatus(200);
        for (DynamoDBRequestHandler handler : this.secondaryHandlers.values()) {
            try {
                dispatcher.enact(accessKey, region, this.jsonMapper.readValue(req.getRequestBody(), requestClass), handler);
            } catch (Exception e) {
                logger.error("Secondary Handler failed:", (Throwable)e);
            }
        }
        CRC32 checksum = new CRC32();
        checksum.update(res.getResponseBody(), 0, res.getResponseBody().length);
        res.getResponse().getHeaders().put("x-amz-crc32", "" + checksum.getValue());
    }

    private void validateTargetActionVersionCompatibility(String action, String version) {
        Set compatibleActions = ACTION_VERSION_COMPATIBILITY.get(version);
        if (compatibleActions == null) {
            throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.INVALID_ACTION);
        }
        if (!compatibleActions.contains(action)) {
            throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.UNKNOWN_OPERATION_EXCEPTION);
        }
    }

    private void packagePreFlight(RequestData req, ResponseData res) throws DynamoDBLocalServiceException {
        String reqMethod = req.getBaseRequest().getHeaders().get("Access-Control-Request-Method");
        String reqHeaders = req.getBaseRequest().getHeaders().get("Access-Control-Request-Headers");
        if (reqHeaders == null) {
            throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, "Invalid PreFlight Headers");
        }
        res.getResponse().getHeaders().add("Access-Control-Allow-Methods", reqMethod);
        res.getResponse().getHeaders().add("Access-Control-Allow-Headers", reqHeaders);
        res.getResponse().getHeaders().add("Access-Control-Max-Age", "1728000");
        res.getResponse().setStatus(200);
        res.setResponseBody(EMPTY_BODY);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public boolean handle(Request request, Response response, Callback callback) throws Exception {
        RequestData req = new RequestData(request);
        ResponseData res = new ResponseData(response);
        res.getResponse().getHeaders().put("x-amzn-RequestId", UUID.randomUUID().toString());
        try {
            req.setRequestBody(LocalDynamoDBServerHandler.fromStream(Content.Source.asInputStream((Content.Source)request)));
            if (this.isPreFlight(req)) {
                this.packagePreFlight(req, res);
            } else {
                this.packageDynamoDBResponse(req, res);
            }
        } catch (DynamoDBLocalServiceException e) {
            res.getResponse().setStatus(e.getStatusCode());
            res.setResponseBody(this.jsonMapper.writeValueAsBytes(new ExceptionBean(e.getErrorCode(), e.getMessage(), e.getCancellationReasons(), e.getItem())));
        } catch (Exception e) {
            AmazonServiceExceptionType internalFailure = AmazonServiceExceptionType.INTERNAL_FAILURE;
            res.getResponse().setStatus(internalFailure.getResponseStatus());
            res.setResponseBody(this.jsonMapper.writeValueAsBytes(new ExceptionBean(internalFailure)));
            logger.error("Unknown error", (Throwable)e);
        } finally {
            if (this.corsSet.contains(req.getBaseRequest().getHeaders().get("Origin"))) {
                res.getResponse().getHeaders().add("Access-Control-Allow-Origin", req.getBaseRequest().getHeaders().get("Origin"));
            }
        }
        res.getResponse().write(true, ByteBuffer.wrap(res.getResponseBody()), callback);
        return true;
    }

    static {
        ACTION_VERSION_COMPATIBILITY.put(COMPATIBLE_VERSION_DYNAMO_DB, new HashSet<String>(Arrays.asList(UPDATE_TABLE, UPDATE_ITEM, SCAN, QUERY, PUT_ITEM, LIST_TABLES, GET_ITEM, DESCRIBE_TABLE, DESCRIBE_LIMITS, DELETE_TABLE, DELETE_ITEM, CREATE_TABLE, BATCH_WRITE_ITEM, TRANSACT_WRITE_ITEMS, BATCH_GET_ITEM, TRANSACT_GET_ITEMS, TAG_RESOURCE, UNTAG_RESOURCE, LIST_TAGS_OF_RESOURCE, DESCRIBE_TIME_TO_LIVE, UPDATE_TIME_TO_LIVE, EXECUTE_STATEMENT, EXECUTE_TRANSACTION, BATCH_EXECUTE_STATEMENT)));
        ACTION_VERSION_COMPATIBILITY.put(COMPATIBLE_VERSION_DYNAMO_DB_STREAMS, new HashSet<String>(Arrays.asList(LIST_STREAMS, GET_SHARD_ITERATOR, GET_RECORDS, DESCRIBE_STREAM)));
    }
}

