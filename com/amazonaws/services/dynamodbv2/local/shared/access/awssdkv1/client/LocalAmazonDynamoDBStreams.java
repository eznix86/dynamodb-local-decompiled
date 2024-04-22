/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  com.amazonaws.AmazonClientException
 *  com.amazonaws.services.dynamodbv2.AbstractAmazonDynamoDBStreams
 *  com.amazonaws.services.dynamodbv2.model.DescribeStreamRequest
 *  com.amazonaws.services.dynamodbv2.model.DescribeStreamResult
 *  com.amazonaws.services.dynamodbv2.model.GetRecordsRequest
 *  com.amazonaws.services.dynamodbv2.model.GetRecordsResult
 *  com.amazonaws.services.dynamodbv2.model.GetShardIteratorRequest
 *  com.amazonaws.services.dynamodbv2.model.GetShardIteratorResult
 *  com.amazonaws.services.dynamodbv2.model.Identity
 *  com.amazonaws.services.dynamodbv2.model.ListStreamsRequest
 *  com.amazonaws.services.dynamodbv2.model.ListStreamsResult
 *  com.amazonaws.services.dynamodbv2.model.OperationType
 *  com.amazonaws.services.dynamodbv2.model.Record
 *  com.amazonaws.services.dynamodbv2.model.Shard
 *  com.amazonaws.services.dynamodbv2.model.ShardIteratorType
 *  com.amazonaws.services.dynamodbv2.model.Stream
 *  com.amazonaws.services.dynamodbv2.model.StreamDescription
 */
package com.amazonaws.services.dynamodbv2.local.shared.access.awssdkv1.client;

import com.amazonaws.AmazonClientException;
import com.amazonaws.services.dynamodbv2.AbstractAmazonDynamoDBStreams;
import com.amazonaws.services.dynamodbv2.exceptions.AWSExceptionFactory;
import com.amazonaws.services.dynamodbv2.exceptions.AmazonServiceExceptionType;
import com.amazonaws.services.dynamodbv2.local.shared.access.LocalDBAccess;
import com.amazonaws.services.dynamodbv2.local.shared.access.LocalDBClient;
import com.amazonaws.services.dynamodbv2.local.shared.access.LocalDBValidatorUtils;
import com.amazonaws.services.dynamodbv2.local.shared.access.ShardIterator;
import com.amazonaws.services.dynamodbv2.local.shared.dataaccess.OperationType;
import com.amazonaws.services.dynamodbv2.local.shared.exceptions.LocalDBClientExceptionMessage;
import com.amazonaws.services.dynamodbv2.local.shared.jobs.JobsRegister;
import com.amazonaws.services.dynamodbv2.local.shared.jobs.ShardRolloverJob;
import com.amazonaws.services.dynamodbv2.local.shared.jobs.ShardRolloverJobScheduler;
import com.amazonaws.services.dynamodbv2.model.DescribeStreamRequest;
import com.amazonaws.services.dynamodbv2.model.DescribeStreamResult;
import com.amazonaws.services.dynamodbv2.model.GetRecordsRequest;
import com.amazonaws.services.dynamodbv2.model.GetRecordsResult;
import com.amazonaws.services.dynamodbv2.model.GetShardIteratorRequest;
import com.amazonaws.services.dynamodbv2.model.GetShardIteratorResult;
import com.amazonaws.services.dynamodbv2.model.Identity;
import com.amazonaws.services.dynamodbv2.model.ListStreamsRequest;
import com.amazonaws.services.dynamodbv2.model.ListStreamsResult;
import com.amazonaws.services.dynamodbv2.model.Record;
import com.amazonaws.services.dynamodbv2.model.Shard;
import com.amazonaws.services.dynamodbv2.model.ShardIteratorType;
import com.amazonaws.services.dynamodbv2.model.Stream;
import com.amazonaws.services.dynamodbv2.model.StreamDescription;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import org.apache.commons.lang3.StringUtils;

public class LocalAmazonDynamoDBStreams
extends AbstractAmazonDynamoDBStreams {
    public static final int GET_RECORD_MIN_LIMIT = 1;
    public static final int GET_RECORD_MAX_LIMIT = 1000;
    public static final long STREAM_SURVIVAL_DURATION = TimeUnit.MILLISECONDS.convert(24L, TimeUnit.HOURS);
    public static final long SHARD_SURVIVAL_DURATION = TimeUnit.MILLISECONDS.convert(24L, TimeUnit.HOURS);
    public static final long RECORD_SURVIVAL_DURATION = TimeUnit.MILLISECONDS.convert(24L, TimeUnit.HOURS);
    public static final long SHARD_ROLLOVER_TIME = TimeUnit.MILLISECONDS.convert(4L, TimeUnit.HOURS);
    public static final long SAFETY_SURVIVAL_PADDING = TimeUnit.MILLISECONDS.convert(6L, TimeUnit.HOURS);
    public static final String STREAMS_EVENT_VERSION = "1.1";
    public static final String DEFAULT_ACCOUNT_NUMBER = "000000000000";
    public static final String DEFAULT_REGION = "ddblocal";
    public static final String DEFAULT_EVENT_SOURCE = "aws:dynamodb";
    private static final long SHARDITERATOR_EXPIRATION_TIME = TimeUnit.MILLISECONDS.convert(15L, TimeUnit.MINUTES);
    private static final String DDB_PRINCIPAL_SERVICE_NAME = "dynamodb.amazonaws.com";
    private static final String IDENTIY_SERVICE_TYPE_NAME = "Service";
    private static final Comparator<StreamDescription> STREAM_DESCRIPTION_CREATION_TIME_COMPARATOR = new Comparator<StreamDescription>(){

        @Override
        public int compare(StreamDescription arg0, StreamDescription arg1) {
            return arg1.getCreationRequestDateTime().compareTo(arg0.getCreationRequestDateTime());
        }
    };
    protected final ShardRolloverJobScheduler shardRolloverJobScheduler;
    private final LocalDBAccess dbAccess;
    private final JobsRegister jobs;
    private final AWSExceptionFactory awsExceptionFactory;
    private volatile long lastDilationRequestTime = 0L;

    public LocalAmazonDynamoDBStreams(LocalDBAccess dbAccess, JobsRegister jobs) {
        this.dbAccess = dbAccess;
        this.awsExceptionFactory = new AWSExceptionFactory();
        this.jobs = jobs;
        this.shardRolloverJobScheduler = new ShardRolloverJobScheduler(dbAccess, this.jobs, SHARDITERATOR_EXPIRATION_TIME / 2L);
        this.jobs.schedule(this.shardRolloverJobScheduler);
    }

    public DescribeStreamResult describeStream(DescribeStreamRequest describeStreamRequest) {
        String streamArn;
        Integer limit2 = LocalDBValidatorUtils.validateDescribeStreamLimit(describeStreamRequest.getLimit(), this.awsExceptionFactory);
        String exclusiveStartShardId = describeStreamRequest.getExclusiveStartShardId();
        if (exclusiveStartShardId != null) {
            if (exclusiveStartShardId.length() < 28) {
                throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, "1 validation error detected: Value '" + exclusiveStartShardId + "' at 'exclusiveStartShardId' failed to satisfy constraint: Member must have length greater than or equal to 28");
            }
            if (exclusiveStartShardId.length() > 65) {
                throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, "1 validation error detected: Value '" + exclusiveStartShardId + "' at 'exclusiveStartShardId' failed to satisfy constraint: Member must have length less than or equal to 65");
            }
        }
        if ((streamArn = describeStreamRequest.getStreamArn()) == null) {
            throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, LocalDBClientExceptionMessage.INVALID_STREAM_ARN.getMessage());
        }
        List<StreamDescription> streamDescs = this.dbAccess.getStreamInfo(null, streamArn, 1, null, describeStreamRequest.getExclusiveStartShardId());
        if (streamDescs.size() == 0 && StringUtils.isNotEmpty(describeStreamRequest.getExclusiveStartShardId())) {
            streamDescs = this.dbAccess.getStreamInfo(null, streamArn, 1, null, null);
            streamDescs.forEach(streamDescription -> streamDescription.setShards(Collections.emptyList()));
        }
        if (streamDescs.size() == 0) {
            throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.RESOURCE_NOT_FOUND_EXCEPTION, "Requested resource not found: Stream: " + streamArn + " not found");
        }
        StreamDescription streamDesc = streamDescs.get(0);
        String nextExclusiveStartShardId = null;
        List shards = streamDesc.getShards();
        if (limit2 < shards.size()) {
            nextExclusiveStartShardId = ((Shard)shards.get(limit2 - 1)).getShardId();
        }
        List shardsToReturn = shards.subList(0, Math.min(limit2, shards.size()));
        return new DescribeStreamResult().withStreamDescription(streamDesc.withLastEvaluatedShardId(nextExclusiveStartShardId).withShards(shardsToReturn));
    }

    public ListStreamsResult listStreams(ListStreamsRequest listStreamsRequest) throws AmazonClientException {
        int limit2 = LocalDBValidatorUtils.validateLimitValueListStreams(listStreamsRequest.getLimit(), this.awsExceptionFactory);
        List<StreamDescription> streamInfoList = this.dbAccess.getStreamInfo(listStreamsRequest.getTableName(), null, limit2 + 1, listStreamsRequest.getExclusiveStartStreamArn(), null);
        String lastEvaluatedStreamArn = streamInfoList.size() > limit2 ? streamInfoList.get(limit2 - 1).getStreamArn() : null;
        ArrayList<Stream> streams = new ArrayList<Stream>();
        if (streamInfoList.size() > limit2) {
            streamInfoList = streamInfoList.subList(0, limit2);
        }
        Collections.sort(streamInfoList, STREAM_DESCRIPTION_CREATION_TIME_COMPARATOR);
        for (StreamDescription description : streamInfoList) {
            streams.add(new Stream().withStreamArn(description.getStreamArn()).withStreamLabel(description.getStreamLabel()).withTableName(description.getTableName()));
        }
        return new ListStreamsResult().withStreams(streams).withLastEvaluatedStreamArn(lastEvaluatedStreamArn);
    }

    public GetShardIteratorResult getShardIterator(GetShardIteratorRequest getShardIteratorRequest) throws AmazonClientException {
        List<StreamDescription> descs;
        if (getShardIteratorRequest.getShardId() == null) {
            throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, "No ShardId specified.");
        }
        ShardIteratorType iterType = null;
        try {
            iterType = ShardIteratorType.fromValue((String)getShardIteratorRequest.getShardIteratorType());
        } catch (IllegalArgumentException e) {
            throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, "Invalid ShardIteratorType.");
        }
        String sequenceNumber = getShardIteratorRequest.getSequenceNumber();
        if (sequenceNumber == null && (ShardIteratorType.AFTER_SEQUENCE_NUMBER == iterType || ShardIteratorType.AT_SEQUENCE_NUMBER == iterType)) {
            this.awsExceptionFactory.INVALID_PARAMETER_VALUE.throwAsException(LocalDBClientExceptionMessage.INVALID_STREAM_SEQUENCE_NUMBER);
        } else if (sequenceNumber != null) {
            if (ShardIteratorType.TRIM_HORIZON == iterType || ShardIteratorType.LATEST == iterType) {
                this.awsExceptionFactory.INVALID_PARAMETER_VALUE.throwAsException(LocalDBClientExceptionMessage.STREAM_SEQUENCE_NUMBER_NOT_ALLOWED);
            }
            if (sequenceNumber.length() < 21) {
                throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, "1 validation error detected: Value '" + sequenceNumber + "' at 'sequenceNumber' failed to satisfy constraint: Member must have length greater than or equal to 21");
            }
            if (sequenceNumber.length() > 40) {
                throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, "1 validation error detected: Value '" + sequenceNumber + "' at 'sequenceNumber' failed to satisfy constraint: Member must have length less than or equal to 40");
            }
        }
        if (getShardIteratorRequest.getStreamArn() == null) {
            this.awsExceptionFactory.INVALID_PARAMETER_VALUE.throwAsException(LocalDBClientExceptionMessage.MISSING_STREAM_ARN);
        }
        if ((descs = this.dbAccess.getStreamInfo(null, getShardIteratorRequest.getStreamArn(), 1, null, null)).isEmpty()) {
            throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.RESOURCE_NOT_FOUND_EXCEPTION, LocalDBClientExceptionMessage.STREAM_NOT_FOUND.getMessage());
        }
        StreamDescription desc = descs.get(0);
        for (Shard shard : desc.getShards()) {
            if (!getShardIteratorRequest.getShardId().equals(shard.getShardId())) continue;
            String iterString = this.getShardIterator(getShardIteratorRequest.getStreamArn(), getShardIteratorRequest.getShardId(), ShardIteratorType.fromValue((String)getShardIteratorRequest.getShardIteratorType()), sequenceNumber, shard);
            return new GetShardIteratorResult().withShardIterator(iterString);
        }
        throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.RESOURCE_NOT_FOUND_EXCEPTION, LocalDBClientExceptionMessage.SHARD_NOT_FOUND.getMessage());
    }

    public GetRecordsResult getRecords(GetRecordsRequest getRecordsRequest) throws AmazonClientException {
        boolean shardIsSealed;
        Long earliestNonExpiredSequenceNumber;
        String shardIteratorStr = getRecordsRequest.getShardIterator();
        ShardIterator iter = null;
        try {
            iter = ShardIterator.fromString(shardIteratorStr);
        } catch (Throwable t) {
            throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, "Invalid ShardIterator");
        }
        if (Math.abs(System.currentTimeMillis() - iter.creationTime) > SHARDITERATOR_EXPIRATION_TIME || this.lastDilationRequestTime > iter.creationTime) {
            throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.EXPIRED_SHARD_ITERATOR);
        }
        Integer limit2 = getRecordsRequest.getLimit();
        if (limit2 == null) {
            limit2 = 1000;
        }
        if (limit2 > 1000) {
            throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, "One or more parameter values were invalid: Limit should be less than or equal to 1000");
        }
        if (limit2 < 1) {
            throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, "1 validation error detected: Value '" + limit2 + "' at 'limit' failed to satisfy constraint: Member must have value greater than or equal to 1");
        }
        if (!this.dbAccess.shardIsNotExpired(iter.shardId)) {
            throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.RESOURCE_NOT_FOUND_EXCEPTION, "Invalid ShardId in ShardIterator");
        }
        if (this.dbAccess.getLatestSequenceNumberForShard(iter.shardId) != null && ((earliestNonExpiredSequenceNumber = this.dbAccess.getEarliestNonExpiredSequenceNumberForShard(iter.shardId)) == null || iter.shardSequenceNumber < earliestNonExpiredSequenceNumber)) {
            throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.TRIMMED_DATA_ACCESS_EXCEPTION);
        }
        List<Record> records = this.dbAccess.getStreamRecords(limit2 + 1, iter);
        boolean hasMoreRecords = records.size() > limit2;
        boolean bl = shardIsSealed = this.dbAccess.getDeletionDateTimeForShard(iter.shardId) != 0L;
        if (hasMoreRecords) {
            records.remove(records.size() - 1);
        }
        long nextSequenceNumber = iter.shardSequenceNumber;
        if (records.size() > 0) {
            nextSequenceNumber = Long.parseLong(records.get(records.size() - 1).getDynamodb().getSequenceNumber()) + 1L;
            for (Record r : records) {
                r.setAwsRegion(DEFAULT_REGION);
                r.setEventSource(DEFAULT_EVENT_SOURCE);
                if (!r.getEventName().equals(OperationType.EXPIRE.toString())) continue;
                r.setEventName(com.amazonaws.services.dynamodbv2.model.OperationType.REMOVE.toString());
                r.setUserIdentity(new Identity().withPrincipalId(DDB_PRINCIPAL_SERVICE_NAME).withType(IDENTIY_SERVICE_TYPE_NAME));
            }
        }
        String nextShardIterator = null;
        if (hasMoreRecords || !shardIsSealed) {
            nextShardIterator = new ShardIterator(iter.streamId, iter.shardId, nextSequenceNumber).toString();
        }
        return new GetRecordsResult().withNextShardIterator(nextShardIterator).withRecords(records);
    }

    private String getShardIterator(String streamId, String shardId, ShardIteratorType shardIteratorType, String startingSequenceNumber, Shard shardDescription) {
        Long seqNum = null;
        if (shardIteratorType == ShardIteratorType.TRIM_HORIZON) {
            Long earliest = this.dbAccess.getEarliestNonExpiredSequenceNumberForShard(shardId);
            seqNum = earliest != null ? earliest : Long.parseLong(shardDescription.getSequenceNumberRange().getStartingSequenceNumber());
        } else if (shardIteratorType == ShardIteratorType.LATEST) {
            Long latestSeqNum;
            String endingSequenceNumber = shardDescription.getSequenceNumberRange().getEndingSequenceNumber();
            seqNum = endingSequenceNumber == null ? ((latestSeqNum = this.dbAccess.getLatestSequenceNumberForShard(shardId)) == null ? this.dbAccess.getSequenceNumberStartForShard(shardId) : Long.valueOf(latestSeqNum + 1L)) : Long.valueOf(Long.parseLong(endingSequenceNumber) + 1L);
        } else if (shardIteratorType == ShardIteratorType.AFTER_SEQUENCE_NUMBER || shardIteratorType == ShardIteratorType.AT_SEQUENCE_NUMBER) {
            long requestSequenceNumber = Long.parseLong(startingSequenceNumber);
            String shardEndingSequenceNumber = shardDescription.getSequenceNumberRange().getEndingSequenceNumber();
            if (requestSequenceNumber < Long.parseLong(shardDescription.getSequenceNumberRange().getStartingSequenceNumber()) || shardEndingSequenceNumber != null && Long.parseLong(shardEndingSequenceNumber) < requestSequenceNumber) {
                throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, "Invalid SequenceNumber for the shard");
            }
            seqNum = shardIteratorType == ShardIteratorType.AFTER_SEQUENCE_NUMBER ? Long.valueOf(Long.parseLong(startingSequenceNumber) + 1L) : Long.valueOf(Long.parseLong(startingSequenceNumber));
        }
        return new ShardIterator(streamId, shardId, seqNum).toString();
    }

    public void dilateEventTimes(long ms) {
        if (ms <= 0L) {
            throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, LocalDBClientExceptionMessage.INVALID_DILATE_TIME_MESSAGE.getMessage());
        }
        this.dbAccess.dilateEventTimes(-1L * ms);
        this.lastDilationRequestTime = System.currentTimeMillis();
        try {
            this.jobs.schedule(new ShardRolloverJob(this.dbAccess, this.jobs, LocalDBClient.SHARD_ROLLOVER_TIME)).get();
        } catch (InterruptedException e) {
            throw AWSExceptionFactory.buildInternalServerException(LocalDBClientExceptionMessage.ROLLOVER_INTERRUPTED_ERROR_MESSAGE.getMessage());
        } catch (ExecutionException e) {
            throw AWSExceptionFactory.buildInternalServerException(LocalDBClientExceptionMessage.ROLLOVER_EXECUTION_EXCEPTION_MESSAGE.getMessage());
        }
    }

    public void triggerShardRollovers() {
        try {
            this.jobs.schedule(new ShardRolloverJob(this.dbAccess, this.jobs, 0L)).get();
        } catch (InterruptedException e) {
            throw AWSExceptionFactory.buildInternalServerException(LocalDBClientExceptionMessage.ROLLOVER_INTERRUPTED_ERROR_MESSAGE.getMessage());
        } catch (ExecutionException e) {
            throw AWSExceptionFactory.buildInternalServerException(LocalDBClientExceptionMessage.ROLLOVER_EXECUTION_EXCEPTION_MESSAGE.getMessage());
        }
    }

    public DescribeStreamResult describeStream(String arg0, Integer arg1, String arg2) throws AmazonClientException {
        DescribeStreamRequest request = new DescribeStreamRequest();
        request.setStreamArn(arg0);
        request.setLimit(arg1);
        request.setExclusiveStartShardId(arg2);
        return this.describeStream(request);
    }

    public ListStreamsResult listStreams() {
        ListStreamsRequest request = new ListStreamsRequest().withLimit(Integer.valueOf(100));
        return this.listStreams(request);
    }

    public ListStreamsResult listStreams(String arg0, Integer arg1, String arg2) {
        ListStreamsRequest request = new ListStreamsRequest().withTableName(arg0).withLimit(arg1).withExclusiveStartStreamArn(arg2);
        return this.listStreams(request);
    }

    public void shutdown() {
    }
}

