/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  com.amazonaws.util.Base64
 *  org.apache.logging.log4j.Logger
 */
package com.amazonaws.services.dynamodbv2.local.shared.access;

import com.amazonaws.services.dynamodbv2.exceptions.AWSExceptionFactory;
import com.amazonaws.services.dynamodbv2.exceptions.AmazonServiceExceptionType;
import com.amazonaws.services.dynamodbv2.local.shared.access.LocalDBUtils;
import com.amazonaws.services.dynamodbv2.local.shared.exceptions.LocalDBClientExceptionMessage;
import com.amazonaws.services.dynamodbv2.local.shared.logging.LogManager;
import com.amazonaws.util.Base64;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.logging.log4j.Logger;

public class ShardIterator {
    public static final String SHARD_ITERATOR_SEPARATOR = "|";
    public static final String SHARD_ITERATOR_SEPARATOR_REGEX = "\\|";
    public static final String SERIAL_NO = "001";
    private static final Logger LOGGER = LogManager.getLogger(ShardIterator.class);
    private static final String paddedCreationTimeFormatString = "%030d";
    public final String streamId;
    public final String shardId;
    public final long shardSequenceNumber;
    public final long creationTime;

    @JsonCreator
    public ShardIterator(@JsonProperty(value="StreamId") String streamId, @JsonProperty(value="ShardId") String shardId, @JsonProperty(value="ShardSequenceNumber") long shardSequenceNumber) {
        this(streamId, shardId, shardSequenceNumber, System.currentTimeMillis());
    }

    @JsonCreator
    public ShardIterator(@JsonProperty(value="StreamId") String streamId, @JsonProperty(value="ShardId") String shardId, @JsonProperty(value="ShardSequenceNumber") long shardSequenceNumber, @JsonProperty(value="CreationTime") long creationTime) {
        this.streamId = streamId;
        this.shardId = shardId;
        this.shardSequenceNumber = shardSequenceNumber;
        this.creationTime = creationTime;
    }

    private static String encodeShardIterator(String streamId, String shardIterator) {
        return streamId + "|001|" + new String(Base64.encode((byte[])shardIterator.getBytes(LocalDBUtils.UTF8)), LocalDBUtils.UTF8);
    }

    private static String decodeShardIterator(String encodedShardIterator) {
        return new String(Base64.decode((byte[])encodedShardIterator.getBytes(LocalDBUtils.UTF8)), LocalDBUtils.UTF8);
    }

    public static ShardIterator fromString(String shardIterator) {
        String[] iteratorParts = shardIterator.split(SHARD_ITERATOR_SEPARATOR_REGEX);
        if (iteratorParts[1].equals(SERIAL_NO)) {
            String streamId = iteratorParts[0];
            String encodedPart = iteratorParts[2];
            String[] iteratorSubParts = ShardIterator.decodeShardIterator(encodedPart).split(SHARD_ITERATOR_SEPARATOR_REGEX);
            try {
                LOGGER.debug("{} => streamId={}, shardId={}, shardSeqNum={}, paddedCT={}", (Object)shardIterator, (Object)streamId, (Object)iteratorSubParts[0], (Object)iteratorSubParts[1], (Object)iteratorSubParts[2]);
                return new ShardIterator(streamId, iteratorSubParts[0], Long.parseLong(iteratorSubParts[1]), Long.parseLong(iteratorSubParts[2]));
            } catch (Exception e) {
                LOGGER.info("Exception during ShardIterator fromString: ", (Throwable)e);
                throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, LocalDBClientExceptionMessage.INVALID_SHARD_ITERATOR.getMessage());
            }
        }
        throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, LocalDBClientExceptionMessage.INVALID_SHARD_ITERATOR.getMessage());
    }

    private String getPaddedCreationTime() {
        return String.format(paddedCreationTimeFormatString, this.creationTime);
    }

    public String toString() {
        String iterator2 = this.shardId + SHARD_ITERATOR_SEPARATOR + LocalDBUtils.longToSequenceNumber(this.shardSequenceNumber) + SHARD_ITERATOR_SEPARATOR + this.getPaddedCreationTime();
        String shardIteratorString = ShardIterator.encodeShardIterator(this.streamId, iterator2);
        LOGGER.debug("streamId={}, shardId={}, shardSeqNum={}, paddedCT={} => {}", (Object)this.streamId, (Object)this.shardId, (Object)LocalDBUtils.longToSequenceNumber(this.shardSequenceNumber), (Object)this.getPaddedCreationTime(), (Object)shardIteratorString);
        return shardIteratorString;
    }
}

