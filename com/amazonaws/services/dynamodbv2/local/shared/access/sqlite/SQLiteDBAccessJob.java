/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  com.almworks.sqlite4java.SQLiteException
 *  com.almworks.sqlite4java.SQLiteStatement
 *  com.amazonaws.services.dynamodbv2.model.KeySchemaElement
 *  com.amazonaws.services.dynamodbv2.model.SequenceNumberRange
 *  com.amazonaws.services.dynamodbv2.model.Shard
 *  com.amazonaws.services.dynamodbv2.model.StreamDescription
 *  com.amazonaws.services.dynamodbv2.model.StreamStatus
 *  com.amazonaws.services.dynamodbv2.model.StreamViewType
 *  org.apache.logging.log4j.Logger
 */
package com.amazonaws.services.dynamodbv2.local.shared.access.sqlite;

import com.almworks.sqlite4java.SQLiteException;
import com.almworks.sqlite4java.SQLiteStatement;
import com.amazonaws.services.dynamodbv2.exceptions.AWSExceptionFactory;
import com.amazonaws.services.dynamodbv2.local.shared.access.LocalDBClient;
import com.amazonaws.services.dynamodbv2.local.shared.access.LocalDBUtils;
import com.amazonaws.services.dynamodbv2.local.shared.access.StreamInfo;
import com.amazonaws.services.dynamodbv2.local.shared.access.sqlite.AmazonDynamoDBOfflineSQLiteJob;
import com.amazonaws.services.dynamodbv2.local.shared.access.sqlite.RecordInfo;
import com.amazonaws.services.dynamodbv2.local.shared.access.sqlite.SQLiteDBAccessUtils;
import com.amazonaws.services.dynamodbv2.local.shared.access.sqlite.SQLiteIndexElement;
import com.amazonaws.services.dynamodbv2.local.shared.access.sqlite.TableSchemaInfo;
import com.amazonaws.services.dynamodbv2.local.shared.dataaccess.OperationType;
import com.amazonaws.services.dynamodbv2.local.shared.exceptions.LocalDBAccessException;
import com.amazonaws.services.dynamodbv2.local.shared.exceptions.LocalDBAccessExceptionType;
import com.amazonaws.services.dynamodbv2.local.shared.logging.LogManager;
import com.amazonaws.services.dynamodbv2.local.shared.mapper.DynamoDBObjectMapper;
import com.amazonaws.services.dynamodbv2.local.shared.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.KeySchemaElement;
import com.amazonaws.services.dynamodbv2.model.SequenceNumberRange;
import com.amazonaws.services.dynamodbv2.model.Shard;
import com.amazonaws.services.dynamodbv2.model.StreamDescription;
import com.amazonaws.services.dynamodbv2.model.StreamStatus;
import com.amazonaws.services.dynamodbv2.model.StreamViewType;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import org.apache.logging.log4j.Logger;

public abstract class SQLiteDBAccessJob<T>
extends AmazonDynamoDBOfflineSQLiteJob<T> {
    private static final String SQL_DEBUG_NULL = "<null>";
    private static final Logger logger = LogManager.getLogger(SQLiteDBAccessJob.class);
    private static final DynamoDBObjectMapper MAPPER = new DynamoDBObjectMapper();
    private static final String openNewStreamForTableStreamMetadataUpdateSQL = String.format("INSERT INTO \"%s\" (\"%s\", \"%s\", \"%s\", \"%s\", \"%s\", \"%s\") VALUES (?,?,?,?,?,?)", "sm", "StreamID", "StreamStatus", "TableName", "StreamInfo", "CreationDateTime", "DeletionDateTime");
    private static final String openNewStreamForTableShardMetadataUpdateSQL = String.format("INSERT INTO \"%s\" (\"%s\", \"%s\", \"%s\", \"%s\", \"%s\", \"%s\", \"%s\") VALUES (?,?,?,?,?,?,?)", "ss", "StreamID", "ShardID", "CreationDateTime", "DeletionDateTime", "InitialSequenceNumberStart", "SequenceNumberEnd", "ParentShardID");
    private static final String closeLatestStreamUpdateStreamMetadataSQL = String.format("UPDATE \"%s\" SET \"%s\"=?, \"%s\"=? WHERE \"%s\"=?;", "sm", "StreamStatus", "DeletionDateTime", "StreamID");
    private static final String closeLatestStreamUpdateShardMetadataSQL = String.format("UPDATE \"%s\" SET \"%s\"=MAX((SELECT %s FROM %s WHERE (\"%s\"=? AND \"%s\"=?)),(COALESCE((SELECT MAX(\"%s\") FROM \"%s\" WHERE (\"%s\"=? AND \"%s\"=?)), -1))), \"%s\"=? WHERE (\"%s\"=? AND \"%s\"=? AND \"%s\" IS NULL);", "ss", "SequenceNumberEnd", "InitialSequenceNumberStart", "ss", "StreamID", "ShardID", "SequenceNumber", "us", "StreamID", "ShardID", "DeletionDateTime", "StreamID", "ShardID", "SequenceNumberEnd");
    private static final String CREATE_AND_INSERT_SHARD_SQL = String.format("INSERT INTO \"%s\" (\"%s\", \"%s\", \"%s\", \"%s\", \"%s\", \"%s\", \"%s\") VALUES (?,?,?,?,?,?,?)", "ss", "StreamID", "ShardID", "CreationDateTime", "DeletionDateTime", "InitialSequenceNumberStart", "SequenceNumberEnd", "ParentShardID");

    TableSchemaInfo getTableSchemaInfo2(String tableName) throws SQLiteException, JsonParseException, JsonMappingException, IOException {
        String sql = String.format("SELECT %s FROM %s WHERE %s = %s;", "TableInfo", "dm", "TableName", SQLiteDBAccessUtils.escapedTableName(tableName));
        SQLiteStatement statement = this.getPreparedStatement(sql);
        if (!statement.step()) {
            throw new LocalDBAccessException(LocalDBAccessExceptionType.TABLE_NOT_FOUND);
        }
        return MAPPER.readValue(statement.columnBlob(0), TableSchemaInfo.class);
    }

    String getTableLatestStreamId(String tableName) throws SQLiteException {
        String sql = String.format("SELECT %s FROM %s WHERE %s = ? AND (%s IS NULL OR %s > ?) ORDER BY %s DESC LIMIT 1;", "StreamID", "sm", "TableName", "DeletionDateTime", "DeletionDateTime", "CreationDateTime");
        logger.debug(sql);
        int i = 1;
        SQLiteStatement statement = this.getPreparedStatement(sql).bind(i++, tableName).bind(i++, System.currentTimeMillis() - LocalDBClient.STREAM_SURVIVAL_DURATION);
        if (statement.step()) {
            return statement.columnString(0);
        }
        return null;
    }

    StreamDescription getStreamDescription(String streamId, Integer limit2) throws JsonParseException, JsonMappingException, SQLiteException, IOException {
        if (streamId == null) {
            return null;
        }
        String sql = String.format("SELECT %s.%s, %s.%s, %s.%s, %s.%s, %s.%s, %s.%s, MAX( COALESCE((SELECT MIN(%s) FROM %s WHERE %s=?), -1), %s.%s), %s.%s, %s.%s, %s.%s FROM %s, %s WHERE %s.%s = %s.%s AND %s.%s = ? AND (%s.%s IS NULL OR %s.%s > ?) ORDER BY %s.%s ASC LIMIT %d;", "sm", "StreamID", "sm", "StreamStatus", "sm", "TableName", "sm", "StreamInfo", "sm", "CreationDateTime", "ss", "ShardID", "SequenceNumber", "us", "StreamID", "ss", "InitialSequenceNumberStart", "ss", "SequenceNumberEnd", "ss", "ParentShardID", "ss", "DeletionDateTime", "sm", "ss", "sm", "StreamID", "ss", "StreamID", "sm", "StreamID", "sm", "DeletionDateTime", "sm", "DeletionDateTime", "sm", "CreationDateTime", limit2 == null ? -1 : limit2);
        logger.debug(sql);
        int i = 1;
        SQLiteStatement statement = this.getPreparedStatement(sql).bind(i++, streamId).bind(i++, streamId).bind(i++, System.currentTimeMillis() - LocalDBClient.STREAM_SURVIVAL_DURATION);
        StreamDescription desc = null;
        if (statement.step()) {
            StreamInfo streamInfo = MAPPER.readValue(statement.columnBlob(3), StreamInfo.class);
            desc = new StreamDescription().withStreamArn(statement.columnString(0)).withStreamStatus(statement.columnString(1)).withTableName(statement.columnString(2)).withStreamViewType(streamInfo.getStreamViewType()).withKeySchema(streamInfo.getKeySchema()).withCreationRequestDateTime(new Date(statement.columnLong(4))).withShards(new Shard[]{new Shard().withShardId(statement.columnString(5)).withSequenceNumberRange(new SequenceNumberRange().withStartingSequenceNumber(LocalDBUtils.longToSequenceNumber(statement.columnLong(6))).withEndingSequenceNumber(statement.columnLong(9) == 0L ? null : LocalDBUtils.longToSequenceNumber(statement.columnLong(7)))).withParentShardId(statement.columnString(8))});
            while (statement.step()) {
                desc.getShards().add(new Shard().withShardId(statement.columnString(5)).withSequenceNumberRange(new SequenceNumberRange().withStartingSequenceNumber(LocalDBUtils.longToSequenceNumber(statement.columnLong(6))).withEndingSequenceNumber(statement.columnLong(9) == 0L ? null : LocalDBUtils.longToSequenceNumber(statement.columnLong(7)))).withParentShardId(statement.columnString(8)));
            }
        }
        return desc;
    }

    Map<String, AttributeValue> getRecordInternal(TableSchemaInfo tableSchemaInfo, String tableName, Map<String, AttributeValue> primaryKey) throws SQLiteException, IOException {
        Map<String, AttributeValue> ret = null;
        List<SQLiteIndexElement> relevantIndexes = tableSchemaInfo.getSqliteIndex().get("");
        StringBuilder sql = new StringBuilder(String.format("SELECT %s FROM %s WHERE ", "ObjectJSON", SQLiteDBAccessUtils.escapedTableName(tableName))).append(SQLiteDBAccessUtils.constructIndexWhereClause(relevantIndexes)).append(";");
        logger.debug(sql.toString());
        SQLiteStatement statement = this.getPreparedStatement(sql.toString());
        SQLiteDBAccessUtils.applyKeyBinds(statement, relevantIndexes, primaryKey);
        if (statement.step()) {
            ret = MAPPER.readValue(statement.columnBlob(0), DynamoDBObjectMapper.ITEM_TYPE);
        }
        if (statement.step()) {
            LocalDBUtils.ldAccessFail(LocalDBAccessExceptionType.DATA_CORRUPTION, "Given key conditions were not unique. Returned: [%s] and [%s].", ret.toString(), MAPPER.readValue(statement.columnBlob(0), DynamoDBObjectMapper.ITEM_TYPE).toString());
        }
        return ret;
    }

    void insertUpdateStreamRecordIfActiveShardPresent(String tableName, Map<String, AttributeValue> primaryKey, Map<String, AttributeValue> oldRecord, Map<String, AttributeValue> newRecord, Long sequenceNumber, Long creationDateTime, OperationType operationType) throws SQLiteException, IOException {
        String latestStreamId = this.getTableLatestStreamId(tableName);
        if (latestStreamId == null) {
            return;
        }
        List<String> latestShardIds = this.getStreamLatestActiveShardId(latestStreamId);
        if (latestShardIds.isEmpty()) {
            return;
        }
        String latestShardId = latestShardIds.get(0);
        StreamDescription desc = this.getStreamDescription(latestStreamId, null);
        RecordInfo rec = new RecordInfo(LocalDBUtils.getUUID(), "1.1", SQLiteDBAccessUtils.constructInternalStreamRecord(primaryKey, oldRecord, newRecord, StreamViewType.fromValue((String)desc.getStreamViewType())));
        String selectRecordSQL = String.format("select * from \"%s\" where %s=\"%s\"", "us", "ShardID", ((Shard)desc.getShards().get(0)).getShardId());
        Long startSequenceDesc = this.getPreparedStatement(selectRecordSQL).step() ? sequenceNumber : Long.valueOf(((Shard)desc.getShards().get(0)).getSequenceNumberRange().getStartingSequenceNumber());
        String insertRecordSQL = String.format("INSERT INTO \"%s\" (\"%s\", \"%s\", \"%s\", \"%s\", \"%s\", \"%s\") VALUES (?,?,?,?,?,?)", "us", "StreamID", "ShardID", "SequenceNumber", "CreationDateTime", "StreamRecord", "OperationType");
        logger.debug(String.format("%s\n\t%s\n\t%s\n\t%s\n\t%s\n\t%s\n\t%s", insertRecordSQL, latestStreamId, latestShardId, startSequenceDesc, creationDateTime, MAPPER.writeValueAsString(rec), operationType.name()));
        int i = 1;
        this.getPreparedStatement(insertRecordSQL).bind(i++, latestStreamId).bind(i++, latestShardId).bind(i++, startSequenceDesc.longValue()).bind(i++, creationDateTime.longValue()).bind(i++, MAPPER.writeValueAsBytes(rec)).bind(i++, operationType.name()).step();
    }

    void openNewStreamForTable(String tableName, List<KeySchemaElement> keySchema, StreamViewType streamViewType, long creationDateTime, AtomicLong sequenceNumber) throws SQLiteException, IOException {
        StreamDescription desc = this.getStreamDescription(this.getTableLatestStreamId(tableName), 1);
        if (desc != null && StreamStatus.fromValue((String)desc.getStreamStatus()) == StreamStatus.ENABLED) {
            LocalDBUtils.ldAccessFail(LocalDBAccessExceptionType.VALIDATION_EXCEPTION, "Must disable existing stream before opening new one.", new Object[0]);
        }
        int i = 1;
        String streamArn = LocalDBUtils.generateStreamARN(tableName, LocalDBUtils.millisToISO8601(creationDateTime));
        String streamInfoString = MAPPER.writeValueAsString(new StreamInfo(streamViewType, keySchema));
        logger.debug(String.format("%s\n\t%s\n\t%s\n\t%s\n\t%s\n\t%s\n\t%s", openNewStreamForTableStreamMetadataUpdateSQL, streamArn, StreamStatus.ENABLED.name(), tableName, streamInfoString, creationDateTime, SQL_DEBUG_NULL));
        this.getPreparedStatement(openNewStreamForTableStreamMetadataUpdateSQL).bind(i++, streamArn).bind(i++, StreamStatus.ENABLED.name()).bind(i++, tableName).bind(i++, MAPPER.writeValueAsBytes(new StreamInfo(streamViewType, keySchema))).bind(i++, creationDateTime).bindNull(i++).step();
        this.createAndInsertShard(streamArn, null, sequenceNumber, creationDateTime);
    }

    void closeLatestStreamForTable(String tableName, long deletionDateTime) throws SQLiteException, IOException {
        String latestStreamId = this.getTableLatestStreamId(tableName);
        if (latestStreamId == null) {
            return;
        }
        StreamDescription desc = this.getStreamDescription(latestStreamId, 1);
        if (StreamStatus.fromValue((String)desc.getStreamStatus()) == StreamStatus.DISABLED) {
            return;
        }
        logger.debug(String.format("%s\n\t%s\n\t%s\n\t%s", closeLatestStreamUpdateStreamMetadataSQL, StreamStatus.DISABLED.name(), deletionDateTime, latestStreamId));
        this.getPreparedStatement(closeLatestStreamUpdateStreamMetadataSQL).bind(1, StreamStatus.DISABLED.name()).bind(2, deletionDateTime).bind(3, latestStreamId).step();
        for (String shardId : this.getStreamLatestActiveShardId(latestStreamId)) {
            this.sealShardForStream(latestStreamId, shardId, deletionDateTime);
        }
    }

    private List<String> getStreamLatestActiveShardId(String streamId, Long shardCreationDateTimeBefore) throws SQLiteException {
        String sql = String.format("SELECT %s FROM %s WHERE %s = ? AND (%s IS NULL) AND (%s <= ?);", "ShardID", "ss", "StreamID", "DeletionDateTime", "CreationDateTime");
        logger.debug(sql);
        int i = 1;
        SQLiteStatement statement = this.getPreparedStatement(sql).bind(i++, streamId).bind(i++, shardCreationDateTimeBefore == null ? Long.MAX_VALUE : shardCreationDateTimeBefore);
        ArrayList<String> shardIds = new ArrayList<String>();
        while (statement.step()) {
            shardIds.add(statement.columnString(0));
        }
        return shardIds;
    }

    List<String> getStreamLatestActiveShardId(String streamId) throws SQLiteException {
        return this.getStreamLatestActiveShardId(streamId, null);
    }

    String rolloverShard(String streamId, String shardId, AtomicLong sequenceNumber) throws IllegalArgumentException, SQLiteException, IOException {
        List<String> childShardIds;
        if (streamId == null) {
            throw new IllegalArgumentException("streamId given to rolloverShard may not be null");
        }
        if (shardId == null) {
            throw new IllegalArgumentException("shardId given to rolloverShard may not be null");
        }
        StreamDescription desc = this.getStreamDescription(streamId, null);
        if (StreamStatus.ENABLED != StreamStatus.fromValue((String)desc.getStreamStatus())) {
            LocalDBUtils.ldAccessFail(LocalDBAccessExceptionType.VALIDATION_EXCEPTION, "Stream " + streamId + " was not active. Cannot rollover.", new Object[0]);
        }
        if (!(childShardIds = this.getChildShardIds(streamId, shardId)).isEmpty()) {
            LocalDBUtils.ldAccessFail(LocalDBAccessExceptionType.VALIDATION_EXCEPTION, "Shard " + shardId + " in stream " + streamId + " had children already. Cannot rollover.", new Object[0]);
        }
        this.sealShardForStream(streamId, shardId, System.currentTimeMillis());
        return this.createAndInsertShard(streamId, shardId, sequenceNumber, System.currentTimeMillis());
    }

    private List<String> getChildShardIds(String streamId, String shardId) throws SQLiteException {
        if (streamId == null) {
            throw new IllegalArgumentException("streamId given to getChildShardIds may not be null");
        }
        if (shardId == null) {
            throw new IllegalArgumentException("shardId given to getChildShardIds may not be null");
        }
        String getChildShardIds = String.format("SELECT %s FROM %s WHERE (%s = ?AND %s = ?) ORDER BY %s ASC;", "ShardID", "ss", "StreamID", "ParentShardID", "InitialSequenceNumberStart");
        logger.debug(getChildShardIds);
        ArrayList<String> childShardIds = new ArrayList<String>();
        SQLiteStatement statement = this.getPreparedStatement(getChildShardIds).bind(1, streamId).bind(2, shardId);
        while (statement.step()) {
            String childShard = statement.columnString(0);
            childShardIds.add(childShard);
        }
        return childShardIds;
    }

    private void sealShardForStream(String streamId, String shardId, long deletionDateTime) throws SQLiteException {
        if (streamId == null) {
            throw new IllegalArgumentException("streamId given to rolloverShard may not be null");
        }
        logger.debug(String.format("%s\n\t%s\n\t%s\n\t%s", closeLatestStreamUpdateShardMetadataSQL, streamId, deletionDateTime, streamId));
        this.getPreparedStatement(closeLatestStreamUpdateShardMetadataSQL).bind(1, streamId).bind(2, shardId).bind(3, streamId).bind(4, shardId).bind(5, deletionDateTime).bind(6, streamId).bind(7, shardId).step();
    }

    private String createAndInsertShard(String streamId, String parentShardId, AtomicLong sequenceNumber, long creationDateTime) throws SQLiteException {
        String generatedShardId = LocalDBUtils.generateShardId();
        logger.debug(String.format("%s\n\t%s\n\t%s\n\t%s\n\t%s\n\t%s\n\t%s\n\t%s", CREATE_AND_INSERT_SHARD_SQL, streamId, generatedShardId, creationDateTime, SQL_DEBUG_NULL, sequenceNumber, SQL_DEBUG_NULL, parentShardId == null ? SQL_DEBUG_NULL : parentShardId));
        int i = 1;
        SQLiteStatement temp = this.getPreparedStatement(CREATE_AND_INSERT_SHARD_SQL).bind(i++, streamId).bind(i++, generatedShardId).bind(i++, creationDateTime).bindNull(i++).bind(i++, sequenceNumber.incrementAndGet()).bindNull(i++);
        if (parentShardId == null) {
            temp.bindNull(i++);
        } else {
            temp.bind(i++, parentShardId);
        }
        temp.step();
        return generatedShardId;
    }

    String rolloverStreamShard(String streamId, long shardAge, AtomicLong sequenceNumberCounter) throws IOException, SQLiteException {
        long shardCreationDateTimeBefore = System.currentTimeMillis() - shardAge;
        List<String> latestActiveShardIds = this.getStreamLatestActiveShardId(streamId, shardCreationDateTimeBefore);
        if (latestActiveShardIds == null || latestActiveShardIds.isEmpty()) {
            return null;
        }
        if (latestActiveShardIds.size() > 1) {
            throw AWSExceptionFactory.buildInternalServerException("Multiple active shards found when there should only have been one.");
        }
        return this.rolloverShard(streamId, latestActiveShardIds.get(0), sequenceNumberCounter);
    }
}

