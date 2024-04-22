/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  com.almworks.sqlite4java.SQLiteConnection
 *  com.almworks.sqlite4java.SQLiteException
 *  com.almworks.sqlite4java.SQLiteJob
 *  com.almworks.sqlite4java.SQLiteQueue
 *  com.almworks.sqlite4java.SQLiteStatement
 *  com.amazonaws.services.dynamodbv2.model.AttributeDefinition
 *  com.amazonaws.services.dynamodbv2.model.BillingMode
 *  com.amazonaws.services.dynamodbv2.model.ComparisonOperator
 *  com.amazonaws.services.dynamodbv2.model.GlobalSecondaryIndex
 *  com.amazonaws.services.dynamodbv2.model.GlobalSecondaryIndexDescription
 *  com.amazonaws.services.dynamodbv2.model.IndexStatus
 *  com.amazonaws.services.dynamodbv2.model.KeySchemaElement
 *  com.amazonaws.services.dynamodbv2.model.KeyType
 *  com.amazonaws.services.dynamodbv2.model.LocalSecondaryIndex
 *  com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput
 *  com.amazonaws.services.dynamodbv2.model.Record
 *  com.amazonaws.services.dynamodbv2.model.SequenceNumberRange
 *  com.amazonaws.services.dynamodbv2.model.Shard
 *  com.amazonaws.services.dynamodbv2.model.StreamDescription
 *  com.amazonaws.services.dynamodbv2.model.StreamSpecification
 *  com.amazonaws.services.dynamodbv2.model.StreamStatus
 *  com.amazonaws.services.dynamodbv2.model.StreamViewType
 *  com.amazonaws.services.dynamodbv2.model.TimeToLiveSpecification
 *  com.amazonaws.util.StringUtils
 *  org.apache.logging.log4j.Level
 *  org.apache.logging.log4j.Logger
 *  org.apache.logging.log4j.util.Strings
 */
package com.amazonaws.services.dynamodbv2.local.shared.access.sqlite;

import com.almworks.sqlite4java.SQLiteConnection;
import com.almworks.sqlite4java.SQLiteException;
import com.almworks.sqlite4java.SQLiteJob;
import com.almworks.sqlite4java.SQLiteQueue;
import com.almworks.sqlite4java.SQLiteStatement;
import com.amazonaws.services.dynamodbv2.exceptions.AWSExceptionFactory;
import com.amazonaws.services.dynamodbv2.exceptions.AmazonServiceExceptionType;
import com.amazonaws.services.dynamodbv2.exceptions.DynamoDBLocalServiceException;
import com.amazonaws.services.dynamodbv2.local.google.Preconditions;
import com.amazonaws.services.dynamodbv2.local.shared.access.ListTablesResultInfo;
import com.amazonaws.services.dynamodbv2.local.shared.access.LocalDBAccess;
import com.amazonaws.services.dynamodbv2.local.shared.access.LocalDBClient;
import com.amazonaws.services.dynamodbv2.local.shared.access.LocalDBUtils;
import com.amazonaws.services.dynamodbv2.local.shared.access.PaddingNumberEncoder;
import com.amazonaws.services.dynamodbv2.local.shared.access.QueryResultInfo;
import com.amazonaws.services.dynamodbv2.local.shared.access.ShardIterator;
import com.amazonaws.services.dynamodbv2.local.shared.access.StreamInfo;
import com.amazonaws.services.dynamodbv2.local.shared.access.TableInfo;
import com.amazonaws.services.dynamodbv2.local.shared.access.sqlite.AmazonDynamoDBOfflineSQLiteJob;
import com.amazonaws.services.dynamodbv2.local.shared.access.sqlite.RecordInfo;
import com.amazonaws.services.dynamodbv2.local.shared.access.sqlite.SQLiteDBAccessJob;
import com.amazonaws.services.dynamodbv2.local.shared.access.sqlite.SQLiteDBAccessUtils;
import com.amazonaws.services.dynamodbv2.local.shared.access.sqlite.SQLiteIndexElement;
import com.amazonaws.services.dynamodbv2.local.shared.access.sqlite.SQLiteLibraryLoader;
import com.amazonaws.services.dynamodbv2.local.shared.access.sqlite.TableSchemaInfo;
import com.amazonaws.services.dynamodbv2.local.shared.dataaccess.OperationType;
import com.amazonaws.services.dynamodbv2.local.shared.exceptions.LocalDBAccessException;
import com.amazonaws.services.dynamodbv2.local.shared.exceptions.LocalDBAccessExceptionType;
import com.amazonaws.services.dynamodbv2.local.shared.exceptions.LocalDBClientExceptionMessage;
import com.amazonaws.services.dynamodbv2.local.shared.logging.LogManager;
import com.amazonaws.services.dynamodbv2.local.shared.mapper.DynamoDBObjectMapper;
import com.amazonaws.services.dynamodbv2.local.shared.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.local.shared.model.Condition;
import com.amazonaws.services.dynamodbv2.model.AttributeDefinition;
import com.amazonaws.services.dynamodbv2.model.BillingMode;
import com.amazonaws.services.dynamodbv2.model.ComparisonOperator;
import com.amazonaws.services.dynamodbv2.model.GlobalSecondaryIndex;
import com.amazonaws.services.dynamodbv2.model.GlobalSecondaryIndexDescription;
import com.amazonaws.services.dynamodbv2.model.IndexStatus;
import com.amazonaws.services.dynamodbv2.model.KeySchemaElement;
import com.amazonaws.services.dynamodbv2.model.KeyType;
import com.amazonaws.services.dynamodbv2.model.LocalSecondaryIndex;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.model.Record;
import com.amazonaws.services.dynamodbv2.model.SequenceNumberRange;
import com.amazonaws.services.dynamodbv2.model.Shard;
import com.amazonaws.services.dynamodbv2.model.StreamDescription;
import com.amazonaws.services.dynamodbv2.model.StreamSpecification;
import com.amazonaws.services.dynamodbv2.model.StreamStatus;
import com.amazonaws.services.dynamodbv2.model.StreamViewType;
import com.amazonaws.services.dynamodbv2.model.TimeToLiveSpecification;
import com.amazonaws.util.StringUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.io.File;
import java.io.IOException;
import java.lang.invoke.CallSite;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.logging.Logger;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.util.Strings;

public class SQLiteDBAccess
implements LocalDBAccess {
    public static final String SHARD_ITERATOR_SEPARATOR = "/";
    public static final String HASH_VALUE_COLUMN_NAME = "hashValue";
    public static final String RANGE_VALUE_COLUMN_NAME = "rangeValue";
    public static final String HASH_RANGE_VALUE_COLUMN_NAME = "hashRangeValue";
    public static final String HASH_KEY_COLUMN_NAME = "hashKey";
    public static final String RANGE_KEY_COLUMN_NAME = "rangeKey";
    public static final String INDEX_KEY_COLUMN_NAME = "indexKey_";
    public static final String INDEX_ATTR_SQLITE_COLUMN_FORMAT = "indexKey_\\d+";
    public static final String ITEM_SIZE_COLUMN_NAME = "itemSize";
    public static final String OBJECT_COLUMN_NAME = "ObjectJSON";
    public static final String PRIMARY_KEY_INDEX_NAME = "";
    public static final String CONFIG_VERSION_COLUMN_NAME = "version";
    public static final String CURRENT_VERSION = "v2.4.0";
    public static final String CONFIG_TABLE = "cf";
    public static final String METADATA_TABLE_NAME = "dm";
    public static final String STREAM_METADATA_TABLE_NAME = "sm";
    public static final String SHARD_METADATA_TABLE_NAME = "ss";
    public static final String STREAMS_TABLE_NAME = "us";
    public static final String TRANSACTIONS_TABLE_NAME = "tr";
    public static final String TABLE_NAME = "TableName";
    public static final String CREATION_DATE_TIME = "CreationDateTime";
    public static final String LAST_DECREASE_DATE = "LastDecreaseDate";
    public static final String LAST_INCREASE_DATE = "LastIncreaseDate";
    public static final String NUM_DECREASES_TODAY = "NumberOfDecreasesToday";
    public static final String READ_CAPACITY_UNITS = "ReadCapacityUnits";
    public static final String WRITE_CAPACITY_UNITS = "WriteCapacityUnits";
    public static final String BILLING_MODE = "BillingMode";
    public static final String DELETE_PROTECTION_ENABLED = "DeletionProtectionEnabled";
    public static final String DELETE_PROTECTION_ENABLED_UPDATE_TIME = "DeletionProtectionUpdateTime";
    public static final String LAST_SET_TO_PAY_PER_REQUEST_DATE_TIME = "PayPerRequestDateTime";
    public static final String TABLE_INFO = "TableInfo";
    public static final int VERY_LARGE_NUMBER_SQLITE_COLUMNS = 1000;
    public static final String STREAM_ID = "StreamID";
    public static final String STREAM_STATUS = "StreamStatus";
    public static final String STREAM_INFO = "StreamInfo";
    public static final String DELETION_DATE_TIME = "DeletionDateTime";
    public static final String SHARD_ID = "ShardID";
    public static final String INITIAL_SEQUENCE_NUMBER_START = "InitialSequenceNumberStart";
    public static final String SEQUENCE_NUMBER_END = "SequenceNumberEnd";
    public static final String PARENT_SHARD_ID = "ParentShardID";
    public static final String SEQUENCE_NUMBER = "SequenceNumber";
    public static final String UPDATE_RECORD = "StreamRecord";
    public static final String OPERATION_TYPE = "OperationType";
    public static final String TRANSACTION_ID = "TransactionId";
    public static final String TRANSACTION_SIGNATURE = "TransactionSignature";
    private static final String LIST_ALL_TABLE_INFO = "SELECT TableName, TableInfo FROM dm;";
    private static final org.apache.logging.log4j.Logger logger = LogManager.getLogger(SQLiteDBAccess.class);
    private static final String INDEX_DELIMITER = "*";
    private static final String HASH_VALUE_INDEX_NAME_PREFIX = "*HVI";
    private static final char[] BASE_16_UPPER = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
    private static final Set<File> openedFiles = new HashSet<File>();
    private static final DynamoDBObjectMapper MAPPER = new DynamoDBObjectMapper();
    private static final String setProvisionedCapacityMetadataSQL = String.format("UPDATE %s SET %s=?, %s=?, %s=?, %s=" + BillingMode.PROVISIONED.ordinal() + ", %s=? WHERE %s=?;", "dm", "ReadCapacityUnits", "WriteCapacityUnits", "DeletionProtectionEnabled", "BillingMode", "TableInfo", "TableName");
    private static final String setOnDemandCapacityMetadataSQL = String.format("UPDATE %s SET %s=" + BillingMode.PAY_PER_REQUEST.ordinal() + ", %s=?, %s=?, %s=?, %s=?, %s=? WHERE %s=?;", "dm", "BillingMode", "ReadCapacityUnits", "WriteCapacityUnits", "PayPerRequestDateTime", "DeletionProtectionEnabled", "TableInfo", "TableName");
    private static final String updateMetadataWithDeleteProtectionSQL = String.format("UPDATE %s SET %s=?, %s=? WHERE %s=?;", "dm", "DeletionProtectionEnabled", "TableInfo", "TableName");
    private static final String updateMetadataSQL = String.format("UPDATE %s SET %s=? WHERE %s=?;", "dm", "TableInfo", "TableName");
    private static final String getLastSequenceNumberForShardSQL = String.format("SELECT %s FROM %s WHERE %s=? ORDER BY %s DESC LIMIT 1;", "SequenceNumber", "us", "ShardID", "SequenceNumber");
    private static final String getDeletionDateTimeForShardSQL = String.format("SELECT %s FROM %s WHERE %s=?;", "DeletionDateTime", "ss", "ShardID");
    private static final String DILATE_TABLE_CREATION_SQL = String.format("UPDATE %s SET %s = %s + ?;", "dm", "CreationDateTime", "CreationDateTime");
    private static final String DILATE_STREAM_CREATION_DELETION_SQL = String.format("UPDATE %s SET %s = %s + ?, %s = %s + ?;", "sm", "CreationDateTime", "CreationDateTime", "DeletionDateTime", "DeletionDateTime");
    private static final String DILATE_SHARD_CREATION_DELETION_SQL = String.format("UPDATE %s SET %s = %s + ?, %s = %s + ?;", "ss", "CreationDateTime", "CreationDateTime", "DeletionDateTime", "DeletionDateTime");
    private static final String DILATE_RECORD_CREATION_SQL = String.format("UPDATE %s SET %s = %s + ?;", "us", "CreationDateTime", "CreationDateTime");
    private static final String DILATE_TRANSACTION_SQL = String.format("UPDATE %s SET %s = %s + ?;", "tr", "CreationDateTime", "CreationDateTime");
    private static final String GET_EARLIEST_SEQNUM_FOR_SHARD_SQL = String.format("SELECT %s, %s FROM %s WHERE %s = ? AND %s >= ? ORDER BY %s ASC LIMIT 1;", "SequenceNumber", "CreationDateTime", "us", "ShardID", "CreationDateTime", "SequenceNumber");
    private static final String GET_START_SEQNUM_FOR_SHARD_SQL = String.format("SELECT %s FROM %s WHERE %s=?", "InitialSequenceNumberStart", "ss", "ShardID");
    private static final String CHECK_IF_SHARD_UNEXPIRED_SQL = String.format("SELECT %s FROM %s WHERE %s = ? AND (%s IS NULL OR %s >= ?);", "ShardID", "ss", "ShardID", "DeletionDateTime", "DeletionDateTime");
    private final ConcurrentMap<String, ReentrantReadWriteLock> rowLockTable = new ConcurrentHashMap<String, ReentrantReadWriteLock>();
    private final File databaseFile;
    private final AtomicLong sequenceNumberCounter = new AtomicLong(0L);
    private final ReentrantReadWriteLock queueLock = new ReentrantReadWriteLock();
    protected SQLiteQueue queue;

    public SQLiteDBAccess() {
        this((File)null);
    }

    public SQLiteDBAccess(String pathToFile) {
        this(new File(pathToFile));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public SQLiteDBAccess(File databaseFile) {
        this.databaseFile = databaseFile;
        if (this.databaseFile != null) {
            Set<File> set2 = openedFiles;
            synchronized (set2) {
                if (openedFiles.contains(this.databaseFile)) {
                    throw new IllegalArgumentException("Database specified by path already in use: " + this.databaseFile.getAbsolutePath());
                }
                openedFiles.add(this.databaseFile);
            }
        }
        LocalDBUtils.setLog4jToUtilsLogging("com.almworks.sqlite4java");
        LocalDBUtils.setLog4jToUtilsLogging("com.almworks.sqlite4java.Internal");
        Logger.getLogger("com.almworks.sqlite4java").setLevel(java.util.logging.Level.WARNING);
        Logger.getLogger("com.almworks.sqlite4java.Internal").setLevel(java.util.logging.Level.WARNING);
        this.queue = new SQLiteQueue(this.databaseFile);
        this.queue.start();
        this.initializeMetadataTables();
    }

    private static List<GlobalSecondaryIndexDescription> gsiThatIsGoingToCreatingStatusInThisUpdate(List<GlobalSecondaryIndexDescription> oldList, List<GlobalSecondaryIndexDescription> newList2) {
        HashSet<String> oldGSINames = new HashSet<String>();
        if (oldList != null) {
            for (GlobalSecondaryIndexDescription desc : oldList) {
                oldGSINames.add(desc.getIndexName());
            }
        }
        ArrayList<GlobalSecondaryIndexDescription> result = new ArrayList<GlobalSecondaryIndexDescription>();
        for (GlobalSecondaryIndexDescription newGSI : newList2) {
            if (!IndexStatus.CREATING.toString().equals(newGSI.getIndexStatus()) || oldGSINames.contains(newGSI.getIndexName())) continue;
            result.add(newGSI);
        }
        return result;
    }

    private static boolean doesTableExist(String tableName, AmazonDynamoDBOfflineSQLiteJob<?> job) throws SQLiteException {
        String configSQL = "SELECT name FROM sqlite_master WHERE type='table' AND name='" + tableName + "';";
        SQLiteStatement configStatement = job.getPreparedStatement(configSQL);
        return configStatement.step();
    }

    private static String escapedTableName(String s) {
        return "\"" + s + "\"";
    }

    private static String escapedIndexName(String s) {
        return SQLiteDBAccess.escapedTableName(s);
    }

    private static String sqliteIndexNameForLSI(String tableName, String lsiRangeKeyAttrName) {
        return SQLiteDBAccess.escapedIndexName(tableName + "*LSI*" + lsiRangeKeyAttrName);
    }

    private static String sqliteIndexNameForGSI(String tableName, String indexName) {
        return SQLiteDBAccess.escapedIndexName(tableName + INDEX_DELIMITER + indexName);
    }

    public static String encodeHex(byte[] bytes) {
        if (bytes == null) {
            throw new AssertionError((Object)"bytes to be encoded must not be null");
        }
        int len = bytes.length;
        char[] result = new char[len << 1];
        int j = 0;
        for (int i = 0; i < len; ++i) {
            result[j++] = BASE_16_UPPER[(0xF0 & bytes[i]) >>> 4];
            result[j++] = BASE_16_UPPER[0xF & bytes[i]];
        }
        return new String(result);
    }

    private void initializeMetadataTables() {
        final AtomicBoolean isErr = new AtomicBoolean(false);
        final long now = System.currentTimeMillis();
        Long lastStoredSequenceNumber = (Long)(this.queue.execute((SQLiteJob)new SQLiteDBAccessJob<Long>(){

            @Override
            public Long doWork() throws SQLiteException {
                SQLiteStatement statement;
                boolean cfExists = SQLiteDBAccess.doesTableExist(SQLiteDBAccess.CONFIG_TABLE, this);
                boolean mdExists = SQLiteDBAccess.doesTableExist(SQLiteDBAccess.METADATA_TABLE_NAME, this);
                boolean smExists = SQLiteDBAccess.doesTableExist(SQLiteDBAccess.STREAM_METADATA_TABLE_NAME, this);
                boolean ssExists = SQLiteDBAccess.doesTableExist(SQLiteDBAccess.SHARD_METADATA_TABLE_NAME, this);
                boolean usExists = SQLiteDBAccess.doesTableExist(SQLiteDBAccess.STREAMS_TABLE_NAME, this);
                boolean trExists = SQLiteDBAccess.doesTableExist(SQLiteDBAccess.TRANSACTIONS_TABLE_NAME, this);
                Long counterString = null;
                if (cfExists != mdExists || mdExists != smExists || smExists != ssExists || ssExists != usExists) {
                    isErr.set(true);
                    return counterString;
                }
                if (!cfExists) {
                    String configTableCreationSQL = "CREATE TABLE IF NOT EXISTS cf (version TEXT);";
                    this.getPreparedStatement(configTableCreationSQL).step();
                    String insertSQL = "INSERT INTO cf VALUES('v2.4.0');";
                    this.getPreparedStatement(insertSQL).step();
                } else {
                    String expectedMajorVersion;
                    String storedMajorVersion;
                    String getVersionSQL = "SELECT version FROM cf";
                    statement = this.getPreparedStatement(getVersionSQL);
                    if (!statement.step() && !(storedMajorVersion = statement.columnString(0).split("\\.")[0]).equals(expectedMajorVersion = SQLiteDBAccess.CURRENT_VERSION.split("\\.")[0])) {
                        isErr.set(true);
                        return counterString;
                    }
                }
                if (!mdExists) {
                    String metadataTableCreationSQL = "CREATE TABLE IF NOT EXISTS dm (TableName TEXT, CreationDateTime INTEGER, LastDecreaseDate INTEGER, LastIncreaseDate INTEGER, NumberOfDecreasesToday INTEGER, ReadCapacityUnits INTEGER, WriteCapacityUnits INTEGER, TableInfo BLOB, PRIMARY KEY(TableName));";
                    this.getPreparedStatement(metadataTableCreationSQL).step();
                }
                String billingModeColumnsExistSQL = "PRAGMA table_info(dm);";
                statement = this.getPreparedStatement("PRAGMA table_info(dm);");
                boolean containsBillingModeColumns = false;
                boolean containsDeleteProtectionColumns = false;
                while (statement.step()) {
                    if (statement.columnString(1).equals(SQLiteDBAccess.BILLING_MODE)) {
                        containsBillingModeColumns = true;
                    }
                    if (statement.columnString(1).equals(SQLiteDBAccess.DELETE_PROTECTION_ENABLED)) {
                        containsDeleteProtectionColumns = true;
                    }
                    if (!containsDeleteProtectionColumns || !containsBillingModeColumns) continue;
                }
                if (!containsBillingModeColumns) {
                    String metadataTableAddBillingModeSQL = "ALTER TABLE dm ADD COLUMN BillingMode INTEGER DEFAULT " + BillingMode.PROVISIONED.ordinal() + ";";
                    this.getPreparedStatement(metadataTableAddBillingModeSQL).step();
                    String metadataTableAddLastSetToPayPerRequestSQL = "ALTER TABLE dm ADD COLUMN PayPerRequestDateTime INTEGER DEFAULT 0;";
                    this.getPreparedStatement("ALTER TABLE dm ADD COLUMN PayPerRequestDateTime INTEGER DEFAULT 0;").step();
                }
                if (!containsDeleteProtectionColumns) {
                    String metadataTableAddDeleteProtectionEnabledSQL = "ALTER TABLE dm ADD COLUMN DeletionProtectionEnabled INTEGER DEFAULT 0;";
                    this.getPreparedStatement("ALTER TABLE dm ADD COLUMN DeletionProtectionEnabled INTEGER DEFAULT 0;").step();
                    String metadataTableAddDeleteProtectionEnabledUpdateTimeSQL = "ALTER TABLE dm ADD COLUMN DeletionProtectionUpdateTime INTEGER DEFAULT 0;";
                    this.getPreparedStatement("ALTER TABLE dm ADD COLUMN DeletionProtectionUpdateTime INTEGER DEFAULT 0;").step();
                }
                if (!smExists) {
                    String streamMetadataTableCreationSQL = "CREATE TABLE IF NOT EXISTS sm (StreamID TEXT, StreamStatus TEXT, TableName TEXT, StreamInfo BLOB, CreationDateTime INTEGER, DeletionDateTime INTEGER, PRIMARY KEY(StreamID));";
                    this.getPreparedStatement(streamMetadataTableCreationSQL).step();
                } else {
                    String deleteExpiredStreamsSQL = String.format("DELETE FROM %s WHERE %s < ?;", SQLiteDBAccess.STREAM_METADATA_TABLE_NAME, SQLiteDBAccess.DELETION_DATE_TIME);
                    this.getPreparedStatement(deleteExpiredStreamsSQL).bind(1, now - (LocalDBClient.STREAM_SURVIVAL_DURATION + LocalDBClient.SAFETY_SURVIVAL_PADDING)).step();
                }
                if (!ssExists) {
                    String shardMetadataTableCreationSQL = "CREATE TABLE IF NOT EXISTS ss (StreamID TEXT, ShardID TEXT, CreationDateTime INTEGER, DeletionDateTime INTEGER, InitialSequenceNumberStart INTEGER, SequenceNumberEnd INTEGER, ParentShardID TEXT, PRIMARY KEY(ShardID));";
                    this.getPreparedStatement(shardMetadataTableCreationSQL).step();
                } else {
                    String deleteExpiredShardSQL = String.format("DELETE FROM %s WHERE %s < ?;", SQLiteDBAccess.SHARD_METADATA_TABLE_NAME, SQLiteDBAccess.DELETION_DATE_TIME);
                    this.getPreparedStatement(deleteExpiredShardSQL).bind(1, now - (LocalDBClient.SHARD_SURVIVAL_DURATION + LocalDBClient.SAFETY_SURVIVAL_PADDING)).step();
                }
                if (!usExists) {
                    String streamsTableCreationSQL = "CREATE TABLE IF NOT EXISTS us (StreamID TEXT, ShardID TEXT, SequenceNumber INTEGER, CreationDateTime INTEGER, StreamRecord BLOB, OperationType TEXT, PRIMARY KEY(SequenceNumber));";
                    this.getPreparedStatement(streamsTableCreationSQL).step();
                    counterString = 0L;
                } else {
                    String deleteExpiredRecordsSQL = String.format("DELETE FROM %s WHERE %s < ?;", SQLiteDBAccess.STREAMS_TABLE_NAME, SQLiteDBAccess.CREATION_DATE_TIME);
                    this.getPreparedStatement(deleteExpiredRecordsSQL).bind(1, now - (LocalDBClient.RECORD_SURVIVAL_DURATION + LocalDBClient.SAFETY_SURVIVAL_PADDING)).step();
                    String getStartingSequenceNumberSQL = "SELECT MAX(SequenceNumber) FROM us";
                    SQLiteStatement statement2 = this.getPreparedStatement(getStartingSequenceNumberSQL);
                    counterString = statement2.step() ? statement2.columnLong(0) + 1L : 1L;
                }
                if (!trExists) {
                    String transactionsTableCreationSQL = "CREATE TABLE IF NOT EXISTS tr (TransactionId TEXT, TransactionSignature BLOB, CreationDateTime INTEGER, PRIMARY KEY(TransactionId));";
                    this.getPreparedStatement(transactionsTableCreationSQL).step();
                } else {
                    String deleteExpiredTransactionsSQL = String.format("DELETE FROM %s WHERE %s < ?;", SQLiteDBAccess.TRANSACTIONS_TABLE_NAME, SQLiteDBAccess.CREATION_DATE_TIME);
                    this.getPreparedStatement(deleteExpiredTransactionsSQL).bind(1, now - LocalDBClient.TRANSACTION_CLIENT_TOKEN_SURVIVAL_DURATION).step();
                }
                return counterString;
            }
        })).get();
        if (isErr.get()) {
            throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, LocalDBClientExceptionMessage.STALE_DATABASE.getMessage());
        }
        this.sequenceNumberCounter.set(lastStoredSequenceNumber);
    }

    @Override
    public void updateTable(String tableName, ProvisionedThroughput provisionedThroughput, BillingMode billingMode, long lastUpdateToPayPerRequestDateTime, List<AttributeDefinition> updatedAttributeDefinitions, List<GlobalSecondaryIndexDescription> gsiDescList, Boolean deleteProtectionEnabled, StreamSpecification streamSpecification) {
        this.updateMetadataTable(tableName, provisionedThroughput, billingMode, lastUpdateToPayPerRequestDateTime, updatedAttributeDefinitions, gsiDescList, deleteProtectionEnabled, streamSpecification);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void updateTable(final String tableName, String timeToLiveAttributeName) {
        final TableSchemaInfo tableSchema = this.getTableSchemaInfo(tableName);
        tableSchema.setTimeToLiveAttributeName(timeToLiveAttributeName);
        this.queueLock.readLock().lock();
        try {
            (this.queue.execute((SQLiteJob)new SQLiteDBAccessJob<Void>(){

                @Override
                public Void doWork() throws SQLiteException, IOException {
                    this.getPreparedStatement(updateMetadataSQL).bind(1, MAPPER.writeValueAsBytes(tableSchema)).bind(2, tableName).step();
                    return null;
                }
            })).get();
        } finally {
            this.queueLock.readLock().unlock();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void createTable(final String tableName, final AttributeDefinition hashKey, final AttributeDefinition baseTableRangeKey, List<AttributeDefinition> allAttributes, List<LocalSecondaryIndex> lsiIndexes, List<GlobalSecondaryIndex> gsiIndexes, final ProvisionedThroughput throughput, final BillingMode billingMode, final Boolean deletionProtectionEnabled, final StreamSpecification streamSpecification) {
        List<List<SQLiteIndexElement>> uniqueGSIIndexes;
        Preconditions.checkNotNull(allAttributes, "allAttributes may not be null in create table");
        final TableSchemaInfo tableSchema = new TableSchemaInfo(hashKey, baseTableRangeKey, allAttributes, lsiIndexes, LocalDBUtils.getGsiDescListFrom(gsiIndexes));
        StringBuilder createTableSQLBuilder = new StringBuilder("CREATE TABLE " + SQLiteDBAccessUtils.escapedTableName(tableName) + " (");
        List<SQLiteIndexElement> uniqueRangeKeyIndexes = tableSchema.getUniqueIndexes();
        final ArrayList<CallSite> secondaryIndexCreationSQL = new ArrayList<CallSite>();
        HashSet<String> columnsAdded = new HashSet<String>();
        for (SQLiteIndexElement indexElement : uniqueRangeKeyIndexes) {
            createTableSQLBuilder.append(indexElement.getSqliteColumnName()).append(" ").append(indexElement.getSqliteDataType().getSQLiteType()).append(" DEFAULT NULL, ");
            columnsAdded.add(indexElement.getSqliteColumnName());
            if (indexElement.getSqliteColumnName().equals(HASH_KEY_COLUMN_NAME) || indexElement.getSqliteColumnName().equals(RANGE_KEY_COLUMN_NAME)) continue;
            String createIndexSQLForLSI = "CREATE INDEX " + SQLiteDBAccess.sqliteIndexNameForLSI(tableName, indexElement.getDynamoDBAttribute().getAttributeName()) + " ON " + SQLiteDBAccess.escapedTableName(tableName) + " (hashKey, " + indexElement.getSqliteColumnName() + ", rangeValue);";
            secondaryIndexCreationSQL.add((CallSite)((Object)createIndexSQLForLSI));
            logger.debug(createIndexSQLForLSI);
        }
        createTableSQLBuilder.append("hashValue BLOB NOT NULL, ");
        if (baseTableRangeKey != null) {
            createTableSQLBuilder.append("rangeValue BLOB NOT NULL, ");
        }
        createTableSQLBuilder.append("itemSize INTEGER DEFAULT 0, ObjectJSON BLOB NOT NULL, ");
        createTableSQLBuilder.append("PRIMARY KEY(").append(tableSchema.getHashKeyIndex().getSqliteColumnName());
        if (baseTableRangeKey != null) {
            createTableSQLBuilder.append(", ").append(tableSchema.getRangeKeyIndex().getSqliteColumnName());
        }
        createTableSQLBuilder.append("));");
        final String createTableSQL = createTableSQLBuilder.toString();
        logger.debug(createTableSQL);
        String hashKeyIndexSQL = "CREATE INDEX " + SQLiteDBAccess.escapedTableName(tableName + HASH_VALUE_INDEX_NAME_PREFIX) + " ON " + SQLiteDBAccess.escapedTableName(tableName) + " (hashValue);";
        secondaryIndexCreationSQL.add((CallSite)((Object)hashKeyIndexSQL));
        logger.debug("Index on Hash Value: " + hashKeyIndexSQL);
        boolean baseTableRangeKeyExists = baseTableRangeKey != null;
        final ArrayList<Object> sqlStatementsRelatedToGSI = new ArrayList<Object>();
        if (tableSchema.getGsiDescList() != null && tableSchema.getGsiDescList().size() > 0 && baseTableRangeKeyExists && !columnsAdded.contains(HASH_RANGE_VALUE_COLUMN_NAME)) {
            sqlStatementsRelatedToGSI.add("ALTER TABLE " + SQLiteDBAccess.escapedTableName(tableName) + " ADD COLUMN hashRangeValue BLOB NOT NULL DEFAULT 0;");
            columnsAdded.add(HASH_RANGE_VALUE_COLUMN_NAME);
        }
        if ((uniqueGSIIndexes = tableSchema.getUniqueGSIIndexes()) != null) {
            for (List<SQLiteIndexElement> gsiIndexElements : uniqueGSIIndexes) {
                sqlStatementsRelatedToGSI.addAll(this.buildSQLStatementsForGSI(tableName, this.findMatchingGSIDesc(gsiIndexElements, tableSchema.getGsiDescList()).getIndexName(), columnsAdded, gsiIndexElements, baseTableRangeKeyExists));
            }
        }
        final String metadataUpdateSQL = String.format("INSERT INTO \"%s\" (\"%s\", \"%s\", \"%s\", \"%s\", \"%s\", \"%s\", \"%s\", \"%s\", \"%s\", \"%s\", \"%s\", \"%s\") VALUES (?,?,?,?,?,?,?,?,?,?,?,?)", METADATA_TABLE_NAME, TABLE_NAME, CREATION_DATE_TIME, LAST_DECREASE_DATE, LAST_INCREASE_DATE, NUM_DECREASES_TODAY, READ_CAPACITY_UNITS, WRITE_CAPACITY_UNITS, BILLING_MODE, DELETE_PROTECTION_ENABLED, DELETE_PROTECTION_ENABLED_UPDATE_TIME, LAST_SET_TO_PAY_PER_REQUEST_DATE_TIME, TABLE_INFO);
        logger.debug(metadataUpdateSQL);
        this.queueLock.readLock().lock();
        try {
            (this.queue.execute((SQLiteJob)new SQLiteDBAccessJob<Void>(){

                @Override
                public Void doWork() throws SQLiteException, IOException {
                    long now = System.currentTimeMillis();
                    this.getPreparedStatement(createTableSQL).step();
                    for (String sql : sqlStatementsRelatedToGSI) {
                        this.getPreparedStatement(sql).step();
                    }
                    for (String sql : secondaryIndexCreationSQL) {
                        this.getPreparedStatement(sql).step();
                    }
                    int i = 1;
                    this.getPreparedStatement(metadataUpdateSQL).bind(i++, tableName).bind(i++, now).bind(i++, 0).bind(i++, 0).bind(i++, 0).bind(i++, throughput != null ? throughput.getReadCapacityUnits() : 0L).bind(i++, throughput != null ? throughput.getWriteCapacityUnits() : 0L).bind(i++, billingMode.ordinal()).bind(i++, String.valueOf(deletionProtectionEnabled)).bind(i++, now).bind(i++, billingMode.equals((Object)BillingMode.PAY_PER_REQUEST) ? now : 0L).bind(i++, MAPPER.writeValueAsBytes(tableSchema)).step();
                    if (streamSpecification != null && streamSpecification.isStreamEnabled().booleanValue()) {
                        ArrayList<KeySchemaElement> keySchema = new ArrayList<KeySchemaElement>();
                        keySchema.add(new KeySchemaElement().withAttributeName(hashKey.getAttributeName()).withKeyType(KeyType.HASH));
                        if (baseTableRangeKey != null) {
                            keySchema.add(new KeySchemaElement().withAttributeName(baseTableRangeKey.getAttributeName()).withKeyType(KeyType.RANGE));
                        }
                        this.openNewStreamForTable(tableName, keySchema, StreamViewType.fromValue((String)streamSpecification.getStreamViewType()), now, SQLiteDBAccess.this.sequenceNumberCounter);
                    }
                    return null;
                }
            })).get();
        } finally {
            this.queueLock.readLock().unlock();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void createGSIColumns(String tableName, String indexName) {
        TableSchemaInfo tableSchemaInfo = this.getTableSchemaInfo(tableName);
        List<SQLiteIndexElement> gsiIndexElements = tableSchemaInfo.getSqliteIndex().get(indexName);
        if (gsiIndexElements == null) {
            throw new DynamoDBLocalServiceException("Did not find the GSI metadata when attempting to create columns for it");
        }
        Set<String> columnsAdded = this.allSqliteColumnNames(tableName);
        boolean baseTableRangeKeyExists = tableSchemaInfo.getRangeKeyDefinition() != null;
        final ArrayList<Object> sqlStatementsForGSI = new ArrayList<Object>();
        if (tableSchemaInfo.getGsiDescList().size() > 0 && baseTableRangeKeyExists && !columnsAdded.contains(HASH_RANGE_VALUE_COLUMN_NAME)) {
            sqlStatementsForGSI.add("ALTER TABLE " + SQLiteDBAccess.escapedTableName(tableName) + " ADD COLUMN hashRangeValue BLOB NOT NULL DEFAULT 0;");
            columnsAdded.add(HASH_RANGE_VALUE_COLUMN_NAME);
        }
        sqlStatementsForGSI.addAll(this.buildSQLStatementsForGSI(tableName, indexName, columnsAdded, gsiIndexElements, baseTableRangeKeyExists));
        this.queueLock.readLock().lock();
        try {
            (this.queue.execute((SQLiteJob)new SQLiteDBAccessJob<Void>(){

                @Override
                public Void doWork() throws SQLiteException {
                    for (String sql : sqlStatementsForGSI) {
                        this.getPreparedStatement(sql).step();
                    }
                    return null;
                }
            })).get();
        } finally {
            this.queueLock.readLock().unlock();
        }
    }

    private GlobalSecondaryIndexDescription findMatchingGSIDesc(List<SQLiteIndexElement> gsiIndexElements, List<GlobalSecondaryIndexDescription> gsiDescList) {
        if (gsiDescList != null) {
            for (GlobalSecondaryIndexDescription desc : gsiDescList) {
                if (!LocalDBUtils.isEqual(desc.getKeySchema(), gsiIndexElements)) continue;
                return desc;
            }
        }
        return null;
    }

    public Set<String> allSqliteColumnNames(final String tableName) {
        this.queueLock.readLock().lock();
        try {
            Set set2 = (Set)(this.queue.execute((SQLiteJob)new SQLiteDBAccessJob<Set<String>>(){

                @Override
                public Set<String> doWork() throws SQLiteException {
                    HashSet<String> result = new HashSet<String>();
                    SQLiteStatement listColumnsStatement = this.getPreparedStatement("PRAGMA table_info(" + SQLiteDBAccess.escapedTableName(tableName) + ")");
                    while (listColumnsStatement.step()) {
                        result.add(listColumnsStatement.columnString(1));
                    }
                    if (result.size() > 1000) {
                        logger.warn("There are large number of sqlite columns representing a DynamoDB table. It is recommended to run optimizeDBBeforeStartup. Please check help command for more information.");
                    }
                    return result;
                }
            })).get();
            return set2;
        } finally {
            this.queueLock.readLock().unlock();
        }
    }

    private Set<String> allSqliteColumnNamesRelevant(String tableName) {
        TableSchemaInfo tableSchemaInfo = this.getTableSchemaInfo(tableName);
        HashSet<String> columnsToCopyOver = new HashSet<String>();
        for (Map.Entry<String, List<SQLiteIndexElement>> entry : tableSchemaInfo.getSqliteIndex().entrySet()) {
            for (SQLiteIndexElement element : entry.getValue()) {
                columnsToCopyOver.add(element.getSqliteColumnName());
            }
        }
        boolean doesBaseTableRangeKeyExist = tableSchemaInfo.getRangeKeyDefinition() != null;
        boolean areThereAnyGSIs = tableSchemaInfo.getGsiDescList().size() > 0;
        columnsToCopyOver.add(HASH_VALUE_COLUMN_NAME);
        if (doesBaseTableRangeKeyExist) {
            columnsToCopyOver.add(RANGE_VALUE_COLUMN_NAME);
        }
        columnsToCopyOver.add(ITEM_SIZE_COLUMN_NAME);
        columnsToCopyOver.add(OBJECT_COLUMN_NAME);
        if (doesBaseTableRangeKeyExist && areThereAnyGSIs) {
            columnsToCopyOver.add(HASH_RANGE_VALUE_COLUMN_NAME);
        }
        return columnsToCopyOver;
    }

    private List<String> buildSQLStatementsForGSI(String tableName, String indexName, Set<String> columnsAdded, List<SQLiteIndexElement> gsiIndexElements, boolean baseTableRangeKeyExists) {
        ArrayList<String> sqlStatementsRelatedToThisGSI = new ArrayList<String>();
        for (SQLiteIndexElement gsiIndexElement : gsiIndexElements) {
            if (columnsAdded.contains(gsiIndexElement.getSqliteColumnName())) continue;
            String addColumnSQL = "ALTER TABLE " + SQLiteDBAccess.escapedTableName(tableName) + " ADD COLUMN " + gsiIndexElement.getSqliteColumnName() + " " + gsiIndexElement.getSqliteDataType().getSQLiteType() + " DEFAULT NULL;";
            logger.debug(addColumnSQL);
            sqlStatementsRelatedToThisGSI.add(addColumnSQL);
            columnsAdded.add(gsiIndexElement.getSqliteColumnName());
        }
        String indexSql = this.buildCreateIndexSQLForGSI(tableName, gsiIndexElements, indexName, this.getTrailingHashColumnName(baseTableRangeKeyExists, gsiIndexElements));
        sqlStatementsRelatedToThisGSI.add(indexSql);
        logger.debug(indexSql);
        return sqlStatementsRelatedToThisGSI;
    }

    private String buildCreateIndexSQLForGSI(String tableName, List<SQLiteIndexElement> gsiIndexElements, String indexName, String trailingHashColumnName) {
        String gsiHashKeyColumnName = gsiIndexElements.get(0).getSqliteColumnName();
        StringBuilder builder = new StringBuilder("CREATE INDEX " + SQLiteDBAccess.sqliteIndexNameForGSI(tableName, indexName) + " ON " + SQLiteDBAccess.escapedTableName(tableName) + " (" + gsiHashKeyColumnName);
        if (gsiIndexElements.size() == 2) {
            String gsiRangeKeyColumnName = gsiIndexElements.get(1).getSqliteColumnName();
            builder.append(", ").append(gsiRangeKeyColumnName);
        }
        if (trailingHashColumnName != null) {
            builder.append(", ").append(trailingHashColumnName);
        }
        builder.append(");");
        return builder.toString();
    }

    private String getTrailingHashColumnName(boolean baseTableRangeKeyExists, List<SQLiteIndexElement> indexElementList) {
        HashSet<String> indexAttributes = new HashSet<String>();
        for (SQLiteIndexElement indexElement : indexElementList) {
            indexAttributes.add(indexElement.getSqliteColumnName());
        }
        if (indexAttributes.contains(HASH_KEY_COLUMN_NAME)) {
            if (baseTableRangeKeyExists) {
                if (!indexAttributes.contains(RANGE_KEY_COLUMN_NAME)) {
                    return RANGE_VALUE_COLUMN_NAME;
                }
                return null;
            }
        } else {
            if (baseTableRangeKeyExists) {
                if (!indexAttributes.contains(RANGE_KEY_COLUMN_NAME)) {
                    return HASH_RANGE_VALUE_COLUMN_NAME;
                }
                return HASH_VALUE_COLUMN_NAME;
            }
            return HASH_VALUE_COLUMN_NAME;
        }
        return null;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void deleteTable(final String tableName) {
        final String dropTableSQL = "DROP TABLE " + SQLiteDBAccess.escapedTableName(tableName) + ";";
        logger.debug(dropTableSQL);
        final String updateMetadataSQL = "DELETE FROM dm WHERE TableName = " + SQLiteDBAccess.escapedTableName(tableName) + ";";
        logger.debug(updateMetadataSQL);
        this.queueLock.readLock().lock();
        try {
            (this.queue.execute((SQLiteJob)new SQLiteDBAccessJob<Void>(){

                @Override
                public Void doWork() throws SQLiteException, IOException {
                    this.getPreparedStatement(dropTableSQL).step();
                    this.getPreparedStatement(updateMetadataSQL).step();
                    this.closeLatestStreamForTable(tableName, System.currentTimeMillis());
                    return null;
                }
            })).get();
        } finally {
            this.queueLock.readLock().unlock();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private TableSchemaInfo updateMetadataTable(final String tableName, final ProvisionedThroughput provisionedThroughput, final BillingMode billingMode, final long lastUpdateToPayPerRequestDateTime, List<AttributeDefinition> updatedAttributeDefinitions, List<GlobalSecondaryIndexDescription> updatedGSIDescList, final Boolean deleteProtectionEnabled, final StreamSpecification spec) {
        Preconditions.checkNotNull(updatedAttributeDefinitions, "updatedAttributeDefinitions may not be null");
        final TableSchemaInfo tableSchema = this.getTableSchemaInfo(tableName);
        List<GlobalSecondaryIndexDescription> oldGSIList = tableSchema.getGsiDescList();
        tableSchema.setAttributes(updatedAttributeDefinitions);
        tableSchema.setGsiDescList(updatedGSIDescList);
        int nextColumnIndex = this.nextColumnIndex(this.allSqliteColumnNames(tableName), this.collectSQLiteColumnNames(tableSchema.getSqliteIndex()));
        tableSchema.addGSIColumnMappings(SQLiteDBAccess.gsiThatIsGoingToCreatingStatusInThisUpdate(oldGSIList, updatedGSIDescList), nextColumnIndex);
        tableSchema.removeGSIColumnMappings(LocalDBUtils.getGSIsByIndexStatus(updatedGSIDescList, IndexStatus.DELETING));
        this.queueLock.readLock().lock();
        try {
            (this.queue.execute((SQLiteJob)new SQLiteDBAccessJob<Void>(){

                @Override
                public Void doWork() throws SQLiteException, IOException {
                    if (provisionedThroughput == null && billingMode == null && spec == null) {
                        return null;
                    }
                    if (billingMode == BillingMode.PROVISIONED && provisionedThroughput == null) {
                        return null;
                    }
                    if (BillingMode.PROVISIONED.equals((Object)billingMode)) {
                        this.getPreparedStatement(setProvisionedCapacityMetadataSQL).bind(1, provisionedThroughput.getReadCapacityUnits().longValue()).bind(2, provisionedThroughput.getWriteCapacityUnits().longValue()).bind(3, String.valueOf(deleteProtectionEnabled)).bind(4, MAPPER.writeValueAsBytes(tableSchema)).bind(5, tableName).step();
                    } else if (BillingMode.PAY_PER_REQUEST.equals((Object)billingMode)) {
                        this.getPreparedStatement(setOnDemandCapacityMetadataSQL).bind(1, 0L).bind(2, 0L).bind(3, lastUpdateToPayPerRequestDateTime).bind(4, String.valueOf(deleteProtectionEnabled)).bind(5, MAPPER.writeValueAsBytes(tableSchema)).bind(6, tableName).step();
                    } else {
                        this.getPreparedStatement(updateMetadataWithDeleteProtectionSQL).bind(1, String.valueOf(deleteProtectionEnabled)).bind(2, MAPPER.writeValueAsBytes(tableSchema)).bind(3, tableName).step();
                    }
                    if (spec != null) {
                        if (spec.isStreamEnabled().booleanValue()) {
                            ArrayList<KeySchemaElement> keySchema = new ArrayList<KeySchemaElement>();
                            keySchema.add(new KeySchemaElement().withAttributeName(tableSchema.getHashKeyDefinition().getAttributeName()).withKeyType(KeyType.HASH));
                            if (tableSchema.getRangeKeyDefinition() != null) {
                                keySchema.add(new KeySchemaElement().withAttributeName(tableSchema.getRangeKeyDefinition().getAttributeName()).withKeyType(KeyType.RANGE));
                            }
                            this.openNewStreamForTable(tableName, keySchema, StreamViewType.fromValue((String)spec.getStreamViewType()), System.currentTimeMillis(), SQLiteDBAccess.this.sequenceNumberCounter);
                        } else {
                            this.closeLatestStreamForTable(tableName, System.currentTimeMillis());
                        }
                    }
                    return null;
                }
            })).get();
        } finally {
            this.queueLock.readLock().unlock();
        }
        return tableSchema;
    }

    public int nextColumnIndex(Collection<String> persistedSqliteColumnNames, Collection<String> inMemorySqliteColumnNames) {
        int maxIndexAsPerSQLiteTable = this.highestIndexNumberedColumn(persistedSqliteColumnNames);
        int maxIndexAsPerInMemoryMapping = this.highestIndexNumberedColumn(inMemorySqliteColumnNames);
        return Math.max(maxIndexAsPerSQLiteTable, maxIndexAsPerInMemoryMapping) + 1;
    }

    public int highestIndexNumberedColumn(Collection<String> sqliteColumnNames) {
        if (sqliteColumnNames.isEmpty()) {
            return -1;
        }
        String highestIndexColumnName = Collections.max(sqliteColumnNames, new Comparator<String>(){

            @Override
            public int compare(String column1, String column2) {
                return this.getColumnIndex(column1) - this.getColumnIndex(column2);
            }

            private int getColumnIndex(String columnName) {
                if (columnName.matches(SQLiteDBAccess.INDEX_ATTR_SQLITE_COLUMN_FORMAT)) {
                    return Integer.parseInt(columnName.replace(SQLiteDBAccess.INDEX_KEY_COLUMN_NAME, SQLiteDBAccess.PRIMARY_KEY_INDEX_NAME));
                }
                return -1;
            }
        });
        return highestIndexColumnName.matches(INDEX_ATTR_SQLITE_COLUMN_FORMAT) ? Integer.parseInt(highestIndexColumnName.replace(INDEX_KEY_COLUMN_NAME, PRIMARY_KEY_INDEX_NAME)) : -1;
    }

    private List<String> collectSQLiteColumnNames(Map<String, List<SQLiteIndexElement>> sqliteIndexMappings) {
        ArrayList<String> sqliteColumnNames = new ArrayList<String>();
        if (sqliteIndexMappings == null) {
            return sqliteColumnNames;
        }
        for (List<SQLiteIndexElement> elements : sqliteIndexMappings.values()) {
            for (SQLiteIndexElement element : elements) {
                sqliteColumnNames.add(element.getSqliteColumnName());
            }
        }
        return sqliteColumnNames;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public List<StreamDescription> getStreamInfo(final String tableName, final String streamId, Integer limit2, final String exclusiveStartStreamId, final String exclusiveStartShardId) {
        String columnsToSelect = "sm.StreamID, sm.StreamStatus, sm.TableName, sm.StreamInfo, sm.CreationDateTime, ss.ShardID, ss.InitialSequenceNumberStart, ss.SequenceNumberEnd, ss.ParentShardID, ss.DeletionDateTime";
        final String selectStreamAndShardsSQL = String.format("SELECT %s FROM %s, %s WHERE %s.%s = %s.%s AND (%s.%s > ? OR ?) AND %s.%s IN (SELECT DISTINCT %s FROM %s WHERE (%s IS NULL OR %s > ?) AND (%s = ? OR ?) AND (%s = ? OR ?) AND (%s > ? OR ?) ORDER BY %s ASC LIMIT %d) ORDER BY %s.%s, %s.%s ASC;", "sm.StreamID, sm.StreamStatus, sm.TableName, sm.StreamInfo, sm.CreationDateTime, ss.ShardID, ss.InitialSequenceNumberStart, ss.SequenceNumberEnd, ss.ParentShardID, ss.DeletionDateTime", STREAM_METADATA_TABLE_NAME, SHARD_METADATA_TABLE_NAME, STREAM_METADATA_TABLE_NAME, STREAM_ID, SHARD_METADATA_TABLE_NAME, STREAM_ID, SHARD_METADATA_TABLE_NAME, SHARD_ID, STREAM_METADATA_TABLE_NAME, STREAM_ID, STREAM_ID, STREAM_METADATA_TABLE_NAME, DELETION_DATE_TIME, DELETION_DATE_TIME, TABLE_NAME, STREAM_ID, STREAM_ID, STREAM_ID, limit2 == null ? -1 : limit2, STREAM_METADATA_TABLE_NAME, STREAM_ID, SHARD_METADATA_TABLE_NAME, SHARD_ID);
        logger.debug(selectStreamAndShardsSQL);
        this.queueLock.readLock().lock();
        try {
            List list = (List)(this.queue.execute((SQLiteJob)new SQLiteDBAccessJob<List<StreamDescription>>(){

                @Override
                public List<StreamDescription> doWork() throws SQLiteException, IOException {
                    TreeMap<String, StreamDescription> descriptions = new TreeMap<String, StreamDescription>();
                    int i = 1;
                    SQLiteStatement statement = this.getPreparedStatement(selectStreamAndShardsSQL).bind(i++, exclusiveStartShardId).bind(i++, exclusiveStartShardId == null ? 1 : 0).bind(i++, System.currentTimeMillis() - LocalDBClient.STREAM_SURVIVAL_DURATION).bind(i++, tableName).bind(i++, tableName == null ? 1 : 0).bind(i++, streamId).bind(i++, streamId == null ? 1 : 0).bind(i++, exclusiveStartStreamId).bind(i++, exclusiveStartStreamId == null ? 1 : 0);
                    logger.debug(String.format("\tbind: %s\n\tbind: %s\n\tbind: %s\n\tbind: %s\n\tbind: %s\n", exclusiveStartShardId == null ? 1 : 0, System.currentTimeMillis() - LocalDBClient.STREAM_SURVIVAL_DURATION, tableName == null ? 1 : 0, streamId == null ? 1 : 0, exclusiveStartStreamId == null ? 1 : 0));
                    while (statement.step()) {
                        String streamId2 = statement.columnString(0);
                        StreamDescription desc = (StreamDescription)descriptions.get(streamId2);
                        if (desc == null) {
                            StreamInfo streamInfo = MAPPER.readValue(statement.columnBlob(3), StreamInfo.class);
                            desc = new StreamDescription().withStreamArn(statement.columnString(0)).withStreamLabel(LocalDBUtils.extractStreamLabelFromArn(statement.columnString(0))).withStreamStatus(statement.columnString(1)).withTableName(statement.columnString(2)).withStreamViewType(streamInfo.getStreamViewType()).withKeySchema(streamInfo.getKeySchema()).withCreationRequestDateTime(new Date(statement.columnLong(4))).withShards(new Shard[]{new Shard().withShardId(statement.columnString(5)).withSequenceNumberRange(new SequenceNumberRange().withStartingSequenceNumber(LocalDBUtils.longToSequenceNumber(statement.columnLong(6))).withEndingSequenceNumber(statement.columnLong(9) == 0L ? null : LocalDBUtils.longToSequenceNumber(statement.columnLong(7)))).withParentShardId(statement.columnString(8))});
                            descriptions.put(streamId2, desc);
                            continue;
                        }
                        desc.getShards().add(new Shard().withShardId(statement.columnString(5)).withSequenceNumberRange(new SequenceNumberRange().withStartingSequenceNumber(LocalDBUtils.longToSequenceNumber(statement.columnLong(6))).withEndingSequenceNumber(statement.columnLong(9) == 0L ? null : LocalDBUtils.longToSequenceNumber(statement.columnLong(7)))).withParentShardId(statement.columnString(8)));
                        desc.setStreamLabel(LocalDBUtils.extractStreamLabelFromArn(desc.getStreamArn()));
                    }
                    return new ArrayList<StreamDescription>(descriptions.values());
                }
            })).get();
            return list;
        } finally {
            this.queueLock.readLock().unlock();
        }
    }

    @Override
    public Long getLatestSequenceNumberForShard(final String shardId) {
        this.queueLock.readLock().lock();
        try {
            Long l = (Long)(this.queue.execute((SQLiteJob)new SQLiteDBAccessJob<Long>(){

                @Override
                public Long doWork() throws SQLiteException {
                    logger.debug(getLastSequenceNumberForShardSQL);
                    logger.debug("\t1: " + shardId);
                    SQLiteStatement statement = this.getPreparedStatement(getLastSequenceNumberForShardSQL).bind(1, shardId);
                    if (statement.step()) {
                        return statement.columnLong(0);
                    }
                    return null;
                }
            })).get();
            return l;
        } finally {
            this.queueLock.readLock().unlock();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void deleteGSI(final String tableName, final String indexName) {
        final TableSchemaInfo tableSchema = this.getTableSchemaInfo(tableName);
        this.queueLock.readLock().lock();
        try {
            (this.queue.execute((SQLiteJob)new SQLiteDBAccessJob<Void>(){

                @Override
                protected Void doWork() throws Throwable {
                    this.dropIndices();
                    return null;
                }

                private void dropIndices() throws SQLiteException {
                    String sqliteIndexName = this.sqliteIndexNamePerNewConvention();
                    if (this.doesIndexExist(sqliteIndexName)) {
                        this.dropGSISQLiteIndex(sqliteIndexName);
                    } else {
                        String sqliteIndexToBeDeleted = this.sqliteIndexNamePerOldConvention();
                        if (sqliteIndexToBeDeleted != null) {
                            this.dropGSISQLiteIndex(sqliteIndexToBeDeleted);
                        }
                    }
                }

                /*
                 * WARNING - Removed try catching itself - possible behaviour change.
                 */
                private String sqliteIndexNamePerOldConvention() throws SQLiteException {
                    ArrayList<String> indexColumnNames = new ArrayList<String>();
                    List<SQLiteIndexElement> indexElements = tableSchema.getSqliteIndex().get(indexName);
                    if (indexElements != null) {
                        for (SQLiteIndexElement indexElement : indexElements) {
                            indexColumnNames.add(indexElement.getSqliteColumnName());
                        }
                    }
                    String describeIndexSQL = "SELECT * FROM sqlite_master WHERE type = \"index\";";
                    SQLiteStatement describeIndexStatement = this.getPreparedStatement("SELECT * FROM sqlite_master WHERE type = \"index\";");
                    try {
                        logger.debug("SELECT * FROM sqlite_master WHERE type = \"index\";");
                        while (describeIndexStatement.step()) {
                            String indexName2 = describeIndexStatement.columnString(1);
                            int rowLength = describeIndexStatement.columnCount();
                            String createIndexSQLcmd = describeIndexStatement.columnString(rowLength - 1);
                            if (createIndexSQLcmd == null) continue;
                            boolean deleteThisIndex = true;
                            for (String indexColumnName : indexColumnNames) {
                                deleteThisIndex = deleteThisIndex && createIndexSQLcmd.contains(indexColumnName);
                            }
                            if (!deleteThisIndex) continue;
                            String string = indexName2;
                            return string;
                        }
                    } finally {
                        describeIndexStatement.dispose();
                    }
                    return null;
                }

                private void dropGSISQLiteIndex(String indexName2) throws SQLiteException {
                    String dropIndexSQL = "DROP INDEX %s;";
                    logger.debug(String.format(dropIndexSQL, indexName2));
                    this.getPreparedStatement(String.format(dropIndexSQL, indexName2)).step();
                }

                private boolean doesIndexExist(String indexName2) throws SQLiteException {
                    String describeIndexSQL = String.format("SELECT * FROM sqlite_master WHERE type = \"index\" AND  name = %s;", indexName2);
                    SQLiteStatement describeIndexNameStatement = this.getPreparedStatement(describeIndexSQL);
                    describeIndexNameStatement.step();
                    logger.debug(describeIndexSQL);
                    boolean result = describeIndexNameStatement.hasRow();
                    describeIndexNameStatement.dispose();
                    return result;
                }

                private String sqliteIndexNamePerNewConvention() {
                    return SQLiteDBAccess.sqliteIndexNameForGSI(tableName, indexName);
                }
            })).get();
        } finally {
            this.queueLock.readLock().unlock();
        }
        TableInfo tableInfo = this.getTableInfo(tableName);
        ArrayList<GlobalSecondaryIndexDescription> updatedGSIDescriptions = new ArrayList<GlobalSecondaryIndexDescription>();
        for (GlobalSecondaryIndexDescription desc : tableInfo.getGSIDescriptions()) {
            if (desc.getIndexName().equals(indexName)) continue;
            updatedGSIDescriptions.add(desc);
        }
        this.updateTable(tableName, tableInfo.getThroughput(), tableInfo.getBillingMode(), tableInfo.getLastUpdateToPayPerRequestDateTime(), tableInfo.getAttributeDefinitions(), updatedGSIDescriptions, tableInfo.getDeleteProtectionEnabled(), null);
    }

    @Override
    public synchronized int numberOfSubscriberWideInflightOnlineCreateIndexesOperations() {
        int count = 0;
        for (String tableName : this.listTables(null, null).getTableNames()) {
            TableInfo tableInfo = this.getTableInfo(tableName);
            if (!tableInfo.hasGSIs()) continue;
            for (GlobalSecondaryIndexDescription desc : tableInfo.getGSIDescriptions()) {
                if (!IndexStatus.CREATING.toString().equals(desc.getIndexStatus())) continue;
                ++count;
            }
        }
        return count;
    }

    @Override
    public void optimizeDBBeforeStartup() {
        logger.info("Optimize phase starting now");
        for (String tableName : this.listTables(null, null).getTableNames()) {
            logger.info("Optimizing " + tableName + "....");
            this.optimizeTable(tableName);
            logger.info("Optimizing " + tableName + "....Done");
        }
        logger.info("Optimize phase complete!");
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void optimizeTable(String tableName) {
        String newTable = tableName + "=new";
        final String createNewTableSQL = "CREATE TABLE " + SQLiteDBAccess.escapedTableName(newTable) + " AS SELECT " + StringUtils.join((String)", ", (String[])this.allSQLiteColumnNamesThatAreRelevant(tableName)) + " FROM " + SQLiteDBAccess.escapedTableName(tableName);
        final String dropOldTableSQL = "DROP TABLE " + SQLiteDBAccess.escapedIndexName(tableName);
        final String renameTempTableToActualTableSQL = "ALTER TABLE " + SQLiteDBAccess.escapedTableName(newTable) + " RENAME TO " + SQLiteDBAccess.escapedTableName(tableName);
        final List<String> createIndexSQLs = this.sqliteCreateIndexSQLsForTable(tableName);
        this.queueLock.readLock().lock();
        try {
            (this.queue.execute((SQLiteJob)new SQLiteDBAccessJob<Void>(){

                @Override
                public Void doWork() throws SQLiteException {
                    this.getPreparedStatement(createNewTableSQL).step();
                    logger.debug(createNewTableSQL);
                    this.getPreparedStatement(dropOldTableSQL).step();
                    logger.debug(dropOldTableSQL);
                    this.getPreparedStatement(renameTempTableToActualTableSQL).step();
                    logger.debug(renameTempTableToActualTableSQL);
                    for (String createIndexSQL : createIndexSQLs) {
                        this.getPreparedStatement(createIndexSQL).step();
                        logger.debug(createIndexSQL);
                    }
                    return null;
                }
            })).get();
        } finally {
            this.queueLock.readLock().unlock();
        }
    }

    private List<String> sqliteCreateIndexSQLsForTable(final String tableName) {
        this.queueLock.readLock().lock();
        try {
            List list = (List)(this.queue.execute((SQLiteJob)new SQLiteDBAccessJob<List<String>>(){

                @Override
                public List<String> doWork() throws SQLiteException {
                    ArrayList<String> result = new ArrayList<String>();
                    String listIndicesSQL = "SELECT sql FROM sqlite_master WHERE type='index' AND name like '" + tableName + "*%'";
                    SQLiteStatement listIndicesStatement = this.getPreparedStatement(listIndicesSQL);
                    logger.debug(listIndicesSQL);
                    while (listIndicesStatement.step()) {
                        result.add(listIndicesStatement.columnString(0));
                    }
                    return result;
                }
            })).get();
            return list;
        } finally {
            this.queueLock.readLock().unlock();
        }
    }

    private String[] allSQLiteColumnNamesThatAreRelevant(String tableName) {
        Set<String> columnsToCopyOver = this.allSqliteColumnNamesRelevant(tableName);
        return columnsToCopyOver.toArray(new String[0]);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public Map<String, AttributeValue> getRecord(final String tableName, final Map<String, AttributeValue> primaryKey) {
        this.queueLock.readLock().lock();
        try {
            Map map2 = (Map)(this.queue.execute((SQLiteJob)new SQLiteDBAccessJob<Map<String, AttributeValue>>(){

                @Override
                public Map<String, AttributeValue> doWork() throws SQLiteException, IOException {
                    TableSchemaInfo tableSchema = this.getTableSchemaInfo2(tableName);
                    return this.getRecordInternal(tableSchema, tableName, primaryKey);
                }
            })).get();
            return map2;
        } finally {
            this.queueLock.readLock().unlock();
        }
    }

    private Long getAndIncrementNextSequenceNumber() {
        return this.sequenceNumberCounter.getAndIncrement();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public List<Record> getStreamRecords(final Integer limit2, final ShardIterator shardIterator) {
        final String getUpdateStreamRecordsSql = String.format("SELECT %s, %s, %s, %s FROM %s WHERE %s = ? AND %s = ? AND %s >= ? AND %s > ? ORDER BY %s ASC LIMIT ?;", SEQUENCE_NUMBER, UPDATE_RECORD, OPERATION_TYPE, CREATION_DATE_TIME, STREAMS_TABLE_NAME, STREAM_ID, SHARD_ID, SEQUENCE_NUMBER, CREATION_DATE_TIME, SEQUENCE_NUMBER);
        logger.debug(getUpdateStreamRecordsSql);
        this.queueLock.readLock().lock();
        try {
            List list = (List)(this.queue.execute((SQLiteJob)new SQLiteDBAccessJob<List<Record>>(){

                @Override
                protected List<Record> doWork() throws Throwable {
                    StreamDescription desc = this.getStreamDescription(shardIterator.streamId, 1);
                    ArrayList<Record> records = new ArrayList<Record>();
                    long trimCutOff = System.currentTimeMillis() - LocalDBClient.RECORD_SURVIVAL_DURATION;
                    int limitVal = limit2 == null ? -1 : limit2;
                    SQLiteStatement statement = this.getPreparedStatement(getUpdateStreamRecordsSql).bind(1, shardIterator.streamId).bind(2, shardIterator.shardId).bind(3, shardIterator.shardSequenceNumber).bind(4, trimCutOff).bind(5, limitVal);
                    logger.debug("\t1: {}", (Object)shardIterator.streamId);
                    logger.debug("\t2: {}", (Object)shardIterator.shardId);
                    logger.debug("\t3: {}", (Object)shardIterator.shardSequenceNumber);
                    logger.debug("\t4: {}", (Object)trimCutOff);
                    logger.debug("\t5: {}", (Object)limitVal);
                    while (statement.step()) {
                        Long sequenceNumber = statement.columnLong(0);
                        long approximateCreationDateTime = TimeUnit.MILLISECONDS.toMinutes(statement.columnLong(3)) * 60L * 1000L;
                        RecordInfo r = MAPPER.readValue(statement.columnBlob(1), RecordInfo.class);
                        records.add(new Record().withDynamodb(r.getDynamodb().withApproximateCreationDateTime(new Date(approximateCreationDateTime)).withStreamViewType(desc.getStreamViewType()).withSequenceNumber(LocalDBUtils.longToSequenceNumber(sequenceNumber))).withEventID(r.getEventID()).withEventVersion(r.getVersion()).withEventName(statement.columnString(2)));
                    }
                    return records;
                }
            })).get();
            return list;
        } finally {
            this.queueLock.readLock().unlock();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public boolean deleteRecord(final String tableName, final Map<String, AttributeValue> primaryKey, final boolean isSystemDelete) {
        Map<String, AttributeValue> record = this.getRecord(tableName, primaryKey);
        if (record == null) {
            return false;
        }
        this.queueLock.readLock().lock();
        try {
            (this.queue.execute((SQLiteJob)new SQLiteDBAccessJob<Void>(){

                @Override
                public Void doWork() throws SQLiteException, IOException {
                    TableSchemaInfo tableSchema = this.getTableSchemaInfo2(tableName);
                    Map<String, AttributeValue> oldRecord = this.getRecordInternal(tableSchema, tableName, primaryKey);
                    long now = System.currentTimeMillis();
                    List<SQLiteIndexElement> relevantIndexes = tableSchema.getSqliteIndex().get(SQLiteDBAccess.PRIMARY_KEY_INDEX_NAME);
                    StringBuilder sql = new StringBuilder(String.format("DELETE FROM %s WHERE ", SQLiteDBAccess.escapedTableName(tableName)));
                    sql.append(SQLiteDBAccess.this.constructIndexWhereClause(relevantIndexes));
                    sql.append(";");
                    logger.debug(sql.toString());
                    SQLiteStatement statement = this.getPreparedStatement(sql.toString());
                    SQLiteDBAccess.this.applyKeyBinds(statement, relevantIndexes, primaryKey);
                    statement.step();
                    OperationType operationType = isSystemDelete ? OperationType.EXPIRE : OperationType.REMOVE;
                    this.insertUpdateStreamRecordIfActiveShardPresent(tableName, primaryKey, oldRecord, null, SQLiteDBAccess.this.getAndIncrementNextSequenceNumber(), now, operationType);
                    return null;
                }
            })).get();
        } finally {
            this.queueLock.readLock().unlock();
        }
        return true;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void putRecord(final String tableName, final Map<String, AttributeValue> record, final AttributeValue hashKey, final AttributeValue rangeKey, boolean isUpdate) {
        this.queueLock.readLock().lock();
        try {
            (this.queue.execute((SQLiteJob)new PutItemSQLiteDBAccessJob<Void>(){

                @Override
                public Void doWork() throws SQLiteException, IOException {
                    TableSchemaInfo tableSchema = this.getTableSchemaInfo2(tableName);
                    Map<String, AttributeValue> primaryKey = SQLiteDBAccessUtils.constructKey(tableSchema, hashKey, rangeKey);
                    Map<String, AttributeValue> oldRecord = this.getRecordInternal(tableSchema, tableName, primaryKey);
                    long now = System.currentTimeMillis();
                    this.doPutItem(tableName, SQLiteDBAccess.this.sqliteColumnBindingsForAllAttributes(record, this.getTableSchemaInfo2(tableName), hashKey, rangeKey), LocalDBUtils.getItemSizeBytes(record));
                    if (oldRecord == null && record != null || oldRecord != null && !oldRecord.equals(record)) {
                        this.insertUpdateStreamRecordIfActiveShardPresent(tableName, primaryKey, oldRecord, record, SQLiteDBAccess.this.getAndIncrementNextSequenceNumber(), now, oldRecord != null ? OperationType.MODIFY : OperationType.INSERT);
                    }
                    return null;
                }
            })).get();
        } finally {
            this.queueLock.readLock().unlock();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void backfillGSI(final String tableName, final String indexName) {
        final TableSchemaInfo tableSchemaInfo = this.getTableSchemaInfo(tableName);
        final SQLiteIndexElement hashKeyIndex = tableSchemaInfo.getHashKeyIndex();
        final SQLiteIndexElement rangeKeyIndex = tableSchemaInfo.getRangeKeyIndex();
        final ArrayList itemsToBackfill = new ArrayList();
        this.queueLock.readLock().lock();
        try {
            (this.queue.execute((SQLiteJob)new SQLiteDBAccessJob<Void>(){

                @Override
                public Void doWork() throws SQLiteException, IOException {
                    String queryAllRows = "SELECT ObjectJSON FROM " + SQLiteDBAccess.escapedTableName(tableName) + ";";
                    SQLiteStatement allRowsStatement = this.getPreparedStatement(queryAllRows);
                    logger.debug(queryAllRows);
                    while (allRowsStatement.step()) {
                        Map<String, AttributeValue> item = MAPPER.readValue(allRowsStatement.columnBlob(0), DynamoDBObjectMapper.ITEM_TYPE);
                        if (!this.itemHasGsiKeyAttributes(item)) continue;
                        itemsToBackfill.add(item);
                    }
                    return null;
                }

                private boolean itemHasGsiKeyAttributes(Map<String, AttributeValue> item) {
                    boolean gsiHashKeyValueIsValid;
                    String gsiHashIndexName = tableSchemaInfo.getGSIHashIndexElement(indexName).getDynamoDBAttribute().getAttributeName();
                    boolean bl = gsiHashKeyValueIsValid = item.containsKey(gsiHashIndexName) && this.primaryKeyAttributeValueIsNotEmptyOrNull(item.get(gsiHashIndexName));
                    if (tableSchemaInfo.getGSIRangeIndexElement(indexName) != null) {
                        String gsiRangeIndexName = tableSchemaInfo.getGSIRangeIndexElement(indexName).getDynamoDBAttribute().getAttributeName();
                        boolean gsiRangeKeyValueIsValid = item.containsKey(gsiRangeIndexName) && this.primaryKeyAttributeValueIsNotEmptyOrNull(item.get(gsiRangeIndexName));
                        return gsiHashKeyValueIsValid && gsiRangeKeyValueIsValid;
                    }
                    return gsiHashKeyValueIsValid;
                }

                private boolean primaryKeyAttributeValueIsNotEmptyOrNull(AttributeValue attributeValue) {
                    if (attributeValue == null) {
                        return false;
                    }
                    switch (attributeValue.getType()) {
                        case N: {
                            return Strings.isNotBlank((String)attributeValue.getN());
                        }
                        case B: {
                            return attributeValue.getB() != null && attributeValue.getB().hasRemaining();
                        }
                        case S: {
                            return Strings.isNotBlank((String)attributeValue.getS());
                        }
                    }
                    throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, LocalDBClientExceptionMessage.INVALID_DATATYPE.getMessage());
                }
            })).get();
            (this.queue.execute((SQLiteJob)new PutItemSQLiteDBAccessJob<Void>(){

                @Override
                public Void doWork() throws SQLiteException {
                    for (Map item : itemsToBackfill) {
                        this.doBackfillItem(tableName, SQLiteDBAccess.this.sqliteColumnBindingsForGSIAttributes(item, tableSchemaInfo, indexName), item, rangeKeyIndex, hashKeyIndex);
                    }
                    return null;
                }
            })).get();
        } finally {
            this.queueLock.readLock().unlock();
        }
    }

    private String[] repeat(String str, int times) {
        String[] array = new String[times];
        for (int i = 0; i < times; ++i) {
            array[i] = str;
        }
        return array;
    }

    private Map<String, byte[]> sqliteColumnBindingsForAllAttributes(Map<String, AttributeValue> record, TableSchemaInfo tableSchema, AttributeValue hashKey, AttributeValue rangeKey) throws JsonProcessingException {
        HashMap<String, byte[]> result = new HashMap<String, byte[]>(this.getColumnNameToValueMap(record, tableSchema.getUniqueIndexes()));
        if (tableSchema.hasGSIs()) {
            for (List<SQLiteIndexElement> gsiIndex : tableSchema.getUniqueGSIIndexes()) {
                result.putAll(this.getColumnNameToValueMap(record, gsiIndex));
            }
        }
        result.put(HASH_VALUE_COLUMN_NAME, LocalDBUtils.getHashValue(hashKey));
        if (rangeKey != null) {
            result.put(RANGE_VALUE_COLUMN_NAME, LocalDBUtils.getHashValue(rangeKey));
            if (tableSchema.hasGSIs()) {
                result.put(HASH_RANGE_VALUE_COLUMN_NAME, LocalDBUtils.getHashValue(hashKey, rangeKey));
            }
        }
        result.put(OBJECT_COLUMN_NAME, MAPPER.writeValueAsBytes(record));
        return result;
    }

    private Map<String, byte[]> sqliteColumnBindingsForGSIAttributes(Map<String, AttributeValue> item, TableSchemaInfo tableSchemaInfo, String indexName) {
        HashMap<String, byte[]> result = new HashMap<String, byte[]>(this.getColumnNameToValueMap(item, tableSchemaInfo.getSqliteIndex().get(indexName)));
        if (tableSchemaInfo.getRangeKeyDefinition() != null) {
            AttributeValue hashKey = item.get(tableSchemaInfo.getHashKeyDefinition().getAttributeName());
            AttributeValue rangeKey = item.get(tableSchemaInfo.getRangeKeyDefinition().getAttributeName());
            result.put(HASH_RANGE_VALUE_COLUMN_NAME, LocalDBUtils.getHashValue(hashKey, rangeKey));
        }
        return result;
    }

    private Map<String, byte[]> getColumnNameToValueMap(Map<String, AttributeValue> item, List<SQLiteIndexElement> indexElements) {
        HashMap<String, byte[]> result = new HashMap<String, byte[]>();
        for (SQLiteIndexElement indexElement : indexElements) {
            String attributeName = indexElement.getDynamoDBAttribute().getAttributeName();
            if (item.get(attributeName) == null) continue;
            result.put(indexElement.getSqliteColumnName(), this.translateKeyAttributeValue(item.get(attributeName)));
        }
        return result;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public QueryResultInfo queryRecords(final String tableName, final String indexName, final Map<String, Condition> conditions, final Map<String, AttributeValue> exclusiveStartKey, final Long limit2, final boolean ascending, final byte[] beginHash, final byte[] endHash, final boolean isScan, final boolean isGSIIndex) {
        this.queueLock.readLock().lock();
        try {
            QueryResultInfo queryResultInfo = (QueryResultInfo)(this.queue.execute((SQLiteJob)new SQLiteDBAccessJob<QueryResultInfo>(){

                @Override
                public QueryResultInfo doWork() throws IOException, SQLiteException {
                    String querySQL;
                    String index = indexName == null ? SQLiteDBAccess.PRIMARY_KEY_INDEX_NAME : indexName;
                    TableSchemaInfo tableSchema = this.getTableSchemaInfo2(tableName);
                    List<SQLiteIndexElement> indexes = tableSchema.getSqliteIndex().get(index);
                    StringBuilder indexColumns = new StringBuilder();
                    StringBuilder orderByCols = new StringBuilder();
                    String orderString = ascending ? "ASC" : "DESC";
                    StringBuilder indexColumnsNotNullClause = new StringBuilder();
                    StringBuilder indexConditionsWhereClause = new StringBuilder();
                    ArrayList<BindValue> indexConditionsBinds = new ArrayList<BindValue>();
                    boolean conditionAdded = false;
                    for (int i = 0; i < indexes.size(); ++i) {
                        Condition condition;
                        SQLiteIndexElement element = indexes.get(i);
                        String columnName = element.getSqliteColumnName();
                        if (i > 0) {
                            indexColumns.append(", ");
                            orderByCols.append(", ");
                            indexColumnsNotNullClause.append(" AND ");
                        }
                        indexColumns.append(columnName);
                        orderByCols.append(columnName).append(" ").append(orderString);
                        indexColumnsNotNullClause.append(String.format("%s IS NOT NULL", columnName));
                        if (conditions == null || (condition = (Condition)conditions.get(element.getDynamoDBAttribute().getAttributeName())) == null) continue;
                        if (conditionAdded && i > 0) {
                            indexConditionsWhereClause.append(" AND ");
                            conditionAdded = false;
                        }
                        ComparisonOperator comparisonOperator = ComparisonOperator.fromValue((String)condition.getComparisonOperator());
                        StringBuilder operatorClause = new StringBuilder(columnName);
                        BindValue bindValue = new BindValue().withBytes(SQLiteDBAccess.this.translateKeyAttributeValue(condition.getAttributeValueList().get(0)));
                        switch (comparisonOperator) {
                            case EQ: {
                                operatorClause.append(" = ?");
                                break;
                            }
                            case LT: {
                                operatorClause.append(" < ?");
                                break;
                            }
                            case GT: {
                                operatorClause.append(" > ?");
                                break;
                            }
                            case LE: {
                                operatorClause.append(" <= ?");
                                break;
                            }
                            case GE: {
                                operatorClause.append(" >= ?");
                                break;
                            }
                            case BEGINS_WITH: {
                                operatorClause = new StringBuilder("hex(").append(columnName).append(")").append(" LIKE ?");
                                String text = SQLiteDBAccess.encodeHex(bindValue.getBytes()) + "%";
                                bindValue = new BindValue().withText(text);
                                break;
                            }
                            case BETWEEN: {
                                operatorClause.append(" BETWEEN ? AND ?");
                                indexConditionsBinds.add(bindValue);
                                bindValue = new BindValue().withBytes(SQLiteDBAccess.this.translateKeyAttributeValue(condition.getAttributeValueList().get(1)));
                                break;
                            }
                            default: {
                                throw new LocalDBAccessException(LocalDBAccessExceptionType.VALIDATION_EXCEPTION, "Unsupported comparison operator for query: " + comparisonOperator.toString());
                            }
                        }
                        if (indexConditionsWhereClause.length() == 0) {
                            indexConditionsWhereClause = new StringBuilder("WHERE ");
                        }
                        indexConditionsWhereClause.append((CharSequence)operatorClause);
                        indexConditionsBinds.add(bindValue);
                        conditionAdded = true;
                    }
                    String trailingHashColName = null;
                    if (indexName != null && !isGSIIndex) {
                        indexColumns.append(", rangeValue");
                        orderByCols.append(", rangeValue ").append(orderString);
                    } else if (isGSIIndex && (trailingHashColName = SQLiteDBAccess.this.getTrailingHashColumnName(tableSchema.getRangeKeyDefinition() != null, tableSchema.getSqliteIndex().get(indexName))) != null) {
                        indexColumns.append(", ").append(trailingHashColName);
                        orderByCols.append(", ").append(trailingHashColName).append(" ").append(orderString);
                    }
                    String indexColumnsAndObjectData = "ObjectJSON, " + indexColumns;
                    StringBuilder exclusiveStartKeyClause = new StringBuilder();
                    ArrayList<byte[]> exclusiveStartKeyBinds = new ArrayList<byte[]>();
                    if (exclusiveStartKey != null) {
                        if (!isScan) {
                            String direction = ascending ? ">" : "<";
                            AttributeDefinition hashKeyDef = tableSchema.getHashKeyDefinition();
                            if (indexName == null || !isGSIIndex) {
                                exclusiveStartKeyClause.append(String.format("AND (%s %s ?", SQLiteDBAccess.HASH_KEY_COLUMN_NAME, direction));
                                exclusiveStartKeyBinds.add(SQLiteDBAccessUtils.translateKeyAttributeValue((AttributeValue)exclusiveStartKey.get(hashKeyDef.getAttributeName())));
                            }
                            AttributeDefinition rangeKeyDef = tableSchema.getRangeKeyDefinition();
                            if (indexName == null) {
                                if (rangeKeyDef != null) {
                                    exclusiveStartKeyClause.append(String.format(" OR (%s = ? AND %s %s ?)", SQLiteDBAccess.HASH_KEY_COLUMN_NAME, SQLiteDBAccess.RANGE_KEY_COLUMN_NAME, direction));
                                    exclusiveStartKeyBinds.add(SQLiteDBAccessUtils.translateKeyAttributeValue((AttributeValue)exclusiveStartKey.get(hashKeyDef.getAttributeName())));
                                    exclusiveStartKeyBinds.add(SQLiteDBAccessUtils.translateKeyAttributeValue((AttributeValue)exclusiveStartKey.get(rangeKeyDef.getAttributeName())));
                                }
                            } else if (!isGSIIndex) {
                                String indexKeyName = tableSchema.getLSIRangeIndexElement(indexName).getSqliteColumnName();
                                String indexKeyDynamoDBName = SQLiteDBAccessUtils.getLSIIndexKeyDynamoDBName(tableSchema, indexName);
                                exclusiveStartKeyClause.append(String.format(" OR (%s = ? AND %s %s ?)", SQLiteDBAccess.HASH_KEY_COLUMN_NAME, indexKeyName, direction));
                                exclusiveStartKeyBinds.add(SQLiteDBAccessUtils.translateKeyAttributeValue((AttributeValue)exclusiveStartKey.get(hashKeyDef.getAttributeName())));
                                exclusiveStartKeyBinds.add(SQLiteDBAccessUtils.translateKeyAttributeValue((AttributeValue)exclusiveStartKey.get(indexKeyDynamoDBName)));
                                exclusiveStartKeyClause.append(String.format(" OR (%s = ? AND %s = ? AND %s %s ?)", SQLiteDBAccess.HASH_KEY_COLUMN_NAME, indexKeyName, SQLiteDBAccess.RANGE_VALUE_COLUMN_NAME, direction));
                                exclusiveStartKeyBinds.add(SQLiteDBAccessUtils.translateKeyAttributeValue((AttributeValue)exclusiveStartKey.get(hashKeyDef.getAttributeName())));
                                exclusiveStartKeyBinds.add(SQLiteDBAccessUtils.translateKeyAttributeValue((AttributeValue)exclusiveStartKey.get(indexKeyDynamoDBName)));
                                exclusiveStartKeyBinds.add(LocalDBUtils.getHashValue((AttributeValue)exclusiveStartKey.get(rangeKeyDef.getAttributeName())));
                            } else {
                                byte[] trailHash = null;
                                if (trailingHashColName != null) {
                                    trailHash = trailingHashColName.equals(SQLiteDBAccess.HASH_VALUE_COLUMN_NAME) ? LocalDBUtils.getHashValue((AttributeValue)exclusiveStartKey.get(hashKeyDef.getAttributeName())) : (trailingHashColName.equals(SQLiteDBAccess.RANGE_VALUE_COLUMN_NAME) ? LocalDBUtils.getHashValue((AttributeValue)exclusiveStartKey.get(rangeKeyDef.getAttributeName())) : LocalDBUtils.getHashValue((AttributeValue)exclusiveStartKey.get(hashKeyDef.getAttributeName()), (AttributeValue)exclusiveStartKey.get(rangeKeyDef.getAttributeName())));
                                }
                                String gsiHashKey = tableSchema.getGSIHashIndexElement(indexName).getSqliteColumnName();
                                String gsiHashKeyDynamoDBName = SQLiteDBAccessUtils.getGSIKeyDynamoDBName(tableSchema, indexName, KeyType.HASH.toString());
                                exclusiveStartKeyClause.append(String.format(" AND (%s %s ?", gsiHashKey, direction));
                                exclusiveStartKeyBinds.add(SQLiteDBAccessUtils.translateKeyAttributeValue((AttributeValue)exclusiveStartKey.get(gsiHashKeyDynamoDBName)));
                                String gsiRangeKey = null;
                                String gsiRangeKeyDynamoDBName = null;
                                if (tableSchema.getGSIRangeIndexElement(indexName) != null) {
                                    gsiRangeKey = tableSchema.getGSIRangeIndexElement(indexName).getSqliteColumnName();
                                    gsiRangeKeyDynamoDBName = SQLiteDBAccessUtils.getGSIKeyDynamoDBName(tableSchema, indexName, KeyType.RANGE.toString());
                                    exclusiveStartKeyClause.append(String.format(" OR (%s = ? AND %s %s ?)", gsiHashKey, gsiRangeKey, direction));
                                    exclusiveStartKeyBinds.add(SQLiteDBAccessUtils.translateKeyAttributeValue((AttributeValue)exclusiveStartKey.get(gsiHashKeyDynamoDBName)));
                                    exclusiveStartKeyBinds.add(SQLiteDBAccessUtils.translateKeyAttributeValue((AttributeValue)exclusiveStartKey.get(gsiRangeKeyDynamoDBName)));
                                    if (trailingHashColName != null) {
                                        exclusiveStartKeyClause.append(String.format(" OR (%s = ? AND %s = ? AND %s %s ?)", gsiHashKey, gsiRangeKey, trailingHashColName, direction));
                                        exclusiveStartKeyBinds.add(SQLiteDBAccessUtils.translateKeyAttributeValue((AttributeValue)exclusiveStartKey.get(gsiHashKeyDynamoDBName)));
                                        exclusiveStartKeyBinds.add(SQLiteDBAccessUtils.translateKeyAttributeValue((AttributeValue)exclusiveStartKey.get(gsiRangeKeyDynamoDBName)));
                                        exclusiveStartKeyBinds.add(trailHash);
                                    }
                                } else if (trailingHashColName != null) {
                                    exclusiveStartKeyClause.append(String.format(" OR (%s = ? AND %s %s ?)", gsiHashKey, trailingHashColName, direction));
                                    exclusiveStartKeyBinds.add(SQLiteDBAccessUtils.translateKeyAttributeValue((AttributeValue)exclusiveStartKey.get(gsiHashKeyDynamoDBName)));
                                    exclusiveStartKeyBinds.add(trailHash);
                                }
                            }
                            exclusiveStartKeyClause.append(")");
                        } else {
                            if (isScan) {
                                LocalDBUtils.ldAccessAssertTrue(ascending, LocalDBAccessExceptionType.UNEXPECTED_EXCEPTION, "Scan should always use ascending order.", new Object[0]);
                            }
                            SQLiteDBAccess.this.prepareKeyColumnsWithExclusiveStartKey(tableSchema, indexName, exclusiveStartKey, ascending, isGSIIndex, trailingHashColName, exclusiveStartKeyClause, exclusiveStartKeyBinds);
                        }
                    }
                    if (!isScan) {
                        nestedQuerySQL = String.format("SELECT %s FROM %s WHERE %s %s ", indexColumnsAndObjectData, SQLiteDBAccessUtils.escapedTableName(tableName), indexColumnsNotNullClause.toString(), exclusiveStartKeyClause.toString());
                        querySQL = String.format("SELECT %s FROM (%s) %s ORDER BY %s LIMIT ?;", SQLiteDBAccess.OBJECT_COLUMN_NAME, nestedQuerySQL, indexConditionsWhereClause.toString(), orderByCols);
                    } else {
                        String segmentSQL;
                        String segmentWhereClause;
                        String rangeKeyColName = SQLiteDBAccess.PRIMARY_KEY_INDEX_NAME;
                        if (tableSchema.getRangeKeyDefinition() != null) {
                            rangeKeyColName = ", rangeKey";
                        }
                        String string = segmentWhereClause = beginHash == null ? SQLiteDBAccess.PRIMARY_KEY_INDEX_NAME : " WHERE hashValue >= ? AND hashValue <= ?";
                        if (indexName == null) {
                            segmentSQL = String.format("SELECT %s FROM %s %s ORDER BY %s %s", "hashValue, " + indexColumnsAndObjectData, SQLiteDBAccessUtils.escapedTableName(tableName), segmentWhereClause, SQLiteDBAccess.HASH_VALUE_COLUMN_NAME, rangeKeyColName);
                        } else if (!isGSIIndex) {
                            Object indexRangeKeyColName = SQLiteDBAccess.PRIMARY_KEY_INDEX_NAME;
                            SQLiteIndexElement indexElement = tableSchema.getLSIRangeIndexElement(indexName);
                            if (indexElement != null) {
                                indexRangeKeyColName = ", " + indexElement.getSqliteColumnName();
                            }
                            segmentSQL = String.format("SELECT %s FROM %s %s ORDER BY %s %s %s", "hashValue, " + indexColumnsAndObjectData + rangeKeyColName, SQLiteDBAccessUtils.escapedTableName(tableName), segmentWhereClause, SQLiteDBAccess.HASH_VALUE_COLUMN_NAME, indexRangeKeyColName, rangeKeyColName);
                        } else {
                            segmentSQL = String.format("SELECT %s FROM %s %s ORDER BY %s", "hashValue, " + indexColumnsAndObjectData, SQLiteDBAccessUtils.escapedTableName(tableName), segmentWhereClause, orderByCols);
                        }
                        nestedQuerySQL = String.format("SELECT %s FROM (%s) WHERE %s %s", indexColumnsAndObjectData, segmentSQL, indexColumnsNotNullClause.toString(), exclusiveStartKeyClause.toString());
                        querySQL = String.format("SELECT %s FROM (%s) %s LIMIT ?;", SQLiteDBAccess.OBJECT_COLUMN_NAME, nestedQuerySQL, indexConditionsWhereClause.toString());
                    }
                    logger.debug("querySQL: " + querySQL);
                    SQLiteStatement statement = this.getPreparedStatement(querySQL);
                    int i = 1;
                    if (beginHash != null) {
                        statement.bind(i, beginHash);
                        statement.bind(++i, endHash);
                        ++i;
                    }
                    i = SQLiteDBAccessUtils.applyBinds(statement, i, exclusiveStartKeyBinds);
                    i = SQLiteDBAccessUtils.applyBindsWithBindValues(statement, i, indexConditionsBinds);
                    long lim = limit2 == null ? -1L : limit2;
                    statement.bind(i, lim);
                    logger.debug("\tbinding " + i + ":\t" + lim);
                    ++i;
                    ArrayList<Map<String, AttributeValue>> ret = new ArrayList<Map<String, AttributeValue>>();
                    while (statement.step()) {
                        Map<String, AttributeValue> record = MAPPER.readValue(statement.columnBlob(0), DynamoDBObjectMapper.ITEM_TYPE);
                        LocalDBUtils.logLongMessage(logger, Level.DEBUG, "queryRecords", record.toString());
                        ret.add(record);
                    }
                    Map lastEvaluatedItem = null;
                    if (lim > 0L && (long)ret.size() == lim) {
                        lastEvaluatedItem = (Map)ret.get(ret.size() - 1);
                    }
                    return new QueryResultInfo(ret, lastEvaluatedItem);
                }
            })).get();
            return queryResultInfo;
        } finally {
            this.queueLock.readLock().unlock();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public long getTableItemCount(final String tableName) {
        this.queueLock.readLock().lock();
        try {
            long l = (Long)(this.queue.execute((SQLiteJob)new SQLiteDBAccessJob<Long>(){

                @Override
                public Long doWork() throws SQLiteException {
                    String tableSizeSQL = "SELECT COUNT(1) FROM " + SQLiteDBAccessUtils.escapedTableName(tableName) + ";";
                    SQLiteStatement statement = this.getPreparedStatement(tableSizeSQL);
                    statement.step();
                    return statement.columnLong(0);
                }
            })).get();
            return l;
        } finally {
            this.queueLock.readLock().unlock();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public long getLSIItemCount(final String tableName, final String indexName) {
        this.queueLock.readLock().lock();
        try {
            long l = (Long)(this.queue.execute((SQLiteJob)new SQLiteDBAccessJob<Long>(){

                @Override
                public Long doWork() throws SQLiteException, IOException {
                    TableSchemaInfo tableSchema = this.getTableSchemaInfo2(tableName);
                    String columnName = tableSchema.getLSIRangeIndexElement(indexName).getSqliteColumnName();
                    String tableSizeSQL = "SELECT COUNT(" + columnName + ") FROM " + SQLiteDBAccessUtils.escapedTableName(tableName) + ";";
                    SQLiteStatement statement = this.getPreparedStatement(tableSizeSQL);
                    statement.step();
                    return statement.columnLong(0);
                }
            })).get();
            return l;
        } finally {
            this.queueLock.readLock().unlock();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public long getGSIItemCount(final String tableName, final String indexName) {
        this.queueLock.readLock().lock();
        try {
            long l = (Long)(this.queue.execute((SQLiteJob)new SQLiteDBAccessJob<Long>(){

                @Override
                public Long doWork() throws SQLiteException, IOException {
                    TableSchemaInfo tableSchema = this.getTableSchemaInfo2(tableName);
                    String hashKeyColumnName = tableSchema.getGSIHashIndexElement(indexName).getSqliteColumnName();
                    String tableSizeSQL = null;
                    if (tableSchema.getGSIRangeIndexElement(indexName) != null) {
                        String gsiRangeKeyColName = tableSchema.getGSIRangeIndexElement(indexName).getSqliteColumnName();
                        tableSizeSQL = String.format("SELECT COUNT(%s) FROM %s WHERE %s IS NOT NULL AND %s IS NOT NULL;", hashKeyColumnName, SQLiteDBAccess.escapedTableName(tableName), hashKeyColumnName, gsiRangeKeyColName);
                    } else {
                        tableSizeSQL = String.format("SELECT COUNT(%s) FROM %s WHERE %s IS NOT NULL;", hashKeyColumnName, SQLiteDBAccess.escapedTableName(tableName), hashKeyColumnName);
                    }
                    SQLiteStatement statement = this.getPreparedStatement(tableSizeSQL);
                    statement.step();
                    return statement.columnLong(0);
                }
            })).get();
            return l;
        } finally {
            this.queueLock.readLock().unlock();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private TableSchemaInfo getTableSchemaInfo(String tableName) {
        final String metadataSelectSQL = "SELECT TableInfo FROM dm WHERE TableName = " + SQLiteDBAccess.escapedTableName(tableName) + ";";
        logger.debug(metadataSelectSQL);
        this.queueLock.readLock().lock();
        try {
            TableSchemaInfo tableSchemaInfo = (TableSchemaInfo)(this.queue.execute((SQLiteJob)new SQLiteDBAccessJob<TableSchemaInfo>(){

                @Override
                public TableSchemaInfo doWork() throws SQLiteException, IOException {
                    TableSchemaInfo ret = null;
                    SQLiteStatement statement = this.getPreparedStatement(metadataSelectSQL);
                    if (statement.step()) {
                        ret = MAPPER.readValue(statement.columnBlob(0), TableSchemaInfo.class);
                    }
                    return ret;
                }
            })).get();
            return tableSchemaInfo;
        } finally {
            this.queueLock.readLock().unlock();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void close() {
        this.queue.stop(true);
        if (this.databaseFile != null) {
            Set<File> set2 = openedFiles;
            synchronized (set2) {
                openedFiles.remove(this.databaseFile);
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public TableInfo getTableInfo(final String tableName) {
        final String metadataSelectSQL = "SELECT CreationDateTime, LastDecreaseDate, LastIncreaseDate, NumberOfDecreasesToday, ReadCapacityUnits, WriteCapacityUnits, BillingMode, DeletionProtectionEnabled, PayPerRequestDateTime, TableInfo FROM dm WHERE TableName = " + SQLiteDBAccess.escapedTableName(tableName) + ";";
        logger.debug(metadataSelectSQL);
        this.queueLock.readLock().lock();
        try {
            TableInfo tableInfo = (TableInfo)(this.queue.execute((SQLiteJob)new SQLiteDBAccessJob<TableInfo>(){

                @Override
                public TableInfo doWork() throws SQLiteException, IOException {
                    TableInfo ret = null;
                    SQLiteStatement statement = this.getPreparedStatement(metadataSelectSQL);
                    if (statement.step()) {
                        long creationDateTime = statement.columnLong(0);
                        long lastDecreaseDateTime = statement.columnLong(1);
                        long lastIncreaseDateTime = statement.columnLong(2);
                        long numDecreasesToday = statement.columnLong(3);
                        Long readCapacityUnits = statement.columnLong(4);
                        Long writeCapacityUnits = statement.columnLong(5);
                        BillingMode billingMode = BillingMode.values()[statement.columnInt(6)];
                        Boolean deleteProtectionEnabled = Boolean.parseBoolean(statement.columnString(7));
                        long lastSetToPayPerRequestDateTime = statement.columnLong(8);
                        TableSchemaInfo tableSchema = MAPPER.readValue(statement.columnBlob(9), TableSchemaInfo.class);
                        String latestStreamId = this.getTableLatestStreamId(tableName);
                        StreamDescription streamDesc = this.getStreamDescription(latestStreamId, null);
                        StreamSpecification uspec = new StreamSpecification().withStreamEnabled(Boolean.valueOf(false));
                        if (latestStreamId != null && StreamStatus.fromValue((String)streamDesc.getStreamStatus()) == StreamStatus.ENABLED) {
                            uspec = new StreamSpecification().withStreamEnabled(Boolean.valueOf(true)).withStreamViewType(streamDesc.getStreamViewType());
                        }
                        TimeToLiveSpecification timeToLiveSpecification = new TimeToLiveSpecification().withEnabled(Boolean.valueOf(false));
                        String timeToLiveAttributeName = tableSchema.getTimeToLiveAttributeName();
                        if (timeToLiveAttributeName != null) {
                            timeToLiveSpecification.withEnabled(Boolean.valueOf(true)).withAttributeName(timeToLiveAttributeName);
                        }
                        ret = new TableInfo(tableName, tableSchema.getHashKeyDefinition(), tableSchema.getRangeKeyDefinition(), tableSchema.getAttributes(), tableSchema.getLsiList(), tableSchema.getGsiDescList(), new ProvisionedThroughput().withReadCapacityUnits(readCapacityUnits).withWriteCapacityUnits(writeCapacityUnits), billingMode, latestStreamId, uspec, timeToLiveSpecification, creationDateTime, lastDecreaseDateTime, lastIncreaseDateTime, numDecreasesToday, deleteProtectionEnabled);
                        ret.setCreationDateTime(creationDateTime);
                        ret.setLastUpdateToPayPerRequestDateTime(lastSetToPayPerRequestDateTime);
                    }
                    return ret;
                }
            })).get();
            return tableInfo;
        } finally {
            this.queueLock.readLock().unlock();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public ListTablesResultInfo listTables(String exclusiveStartTableName, Long limit2) {
        String excStart = exclusiveStartTableName == null ? PRIMARY_KEY_INDEX_NAME : exclusiveStartTableName;
        final long lim = limit2 == null || limit2 < 0L ? -1L : limit2 + 1L;
        final String sql = String.format("SELECT %s FROM %s WHERE %s > %s ORDER BY %s ASC LIMIT %d;", TABLE_NAME, METADATA_TABLE_NAME, TABLE_NAME, SQLiteDBAccessUtils.escapedTableName(excStart), TABLE_NAME, lim);
        logger.debug(sql);
        this.queueLock.readLock().lock();
        try {
            ListTablesResultInfo listTablesResultInfo = (ListTablesResultInfo)(this.queue.execute((SQLiteJob)new SQLiteDBAccessJob<ListTablesResultInfo>(){

                @Override
                protected ListTablesResultInfo doWork() throws Throwable {
                    SQLiteStatement statement = this.getPreparedStatement(sql);
                    ArrayList<String> ret = new ArrayList<String>();
                    while (statement.step()) {
                        ret.add(statement.columnString(0));
                    }
                    String lastEvaluatedTableName = null;
                    if (lim > 0L && (long)ret.size() == lim) {
                        ret.remove(ret.size() - 1);
                        lastEvaluatedTableName = (String)ret.get(ret.size() - 1);
                    }
                    return new ListTablesResultInfo(ret, lastEvaluatedTableName);
                }
            })).get();
            return listTablesResultInfo;
        } finally {
            this.queueLock.readLock().unlock();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public long getTableByteSize(String tableName) {
        final String sql = String.format("SELECT SUM(%s) FROM %s;", ITEM_SIZE_COLUMN_NAME, SQLiteDBAccessUtils.escapedTableName(tableName));
        this.queueLock.readLock().lock();
        try {
            long l = (Long)(this.queue.execute((SQLiteJob)new SQLiteDBAccessJob<Long>(){

                @Override
                protected Long doWork() throws Throwable {
                    SQLiteStatement statement = this.getPreparedStatement(sql);
                    statement.step();
                    return statement.columnLong(0);
                }
            })).get();
            return l;
        } finally {
            this.queueLock.readLock().unlock();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public long getLSIByteSize(final String tableName, final String indexName) {
        this.queueLock.readLock().lock();
        try {
            long l = (Long)(this.queue.execute((SQLiteJob)new SQLiteDBAccessJob<Long>(){

                @Override
                protected Long doWork() throws Throwable {
                    TableSchemaInfo tableSchema = this.getTableSchemaInfo2(tableName);
                    String indexColumnName = tableSchema.getLSIRangeIndexElement(indexName).getSqliteColumnName();
                    String sql = String.format("SELECT SUM(%s) FROM %s WHERE %s IS NOT NULL;", SQLiteDBAccess.ITEM_SIZE_COLUMN_NAME, SQLiteDBAccessUtils.escapedTableName(tableName), indexColumnName);
                    SQLiteStatement statement = this.getPreparedStatement(sql);
                    statement.step();
                    return statement.columnLong(0);
                }
            })).get();
            return l;
        } finally {
            this.queueLock.readLock().unlock();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public long getGSIByteSize(final String tableName, final String indexName) {
        this.queueLock.readLock().lock();
        try {
            long l = (Long)(this.queue.execute((SQLiteJob)new SQLiteDBAccessJob<Long>(){

                @Override
                protected Long doWork() throws Throwable {
                    TableSchemaInfo tableSchema = this.getTableSchemaInfo2(tableName);
                    String hashColumnName = tableSchema.getGSIHashIndexElement(indexName).getSqliteColumnName();
                    String sql = null;
                    if (tableSchema.getGSIRangeIndexElement(indexName) != null) {
                        String rangeColumnName = tableSchema.getGSIRangeIndexElement(indexName).getSqliteColumnName();
                        sql = String.format("SELECT SUM(%s) FROM %s WHERE %s IS NOT NULL OR %s IS NOT NULL;", SQLiteDBAccess.ITEM_SIZE_COLUMN_NAME, SQLiteDBAccessUtils.escapedTableName(tableName), hashColumnName, rangeColumnName);
                    } else {
                        sql = String.format("SELECT SUM(%s) FROM %s WHERE %s IS NOT NULL;", SQLiteDBAccess.ITEM_SIZE_COLUMN_NAME, SQLiteDBAccessUtils.escapedTableName(tableName), hashColumnName);
                    }
                    SQLiteStatement statement = this.getPreparedStatement(sql);
                    statement.step();
                    return statement.columnLong(0);
                }
            })).get();
            return l;
        } finally {
            this.queueLock.readLock().unlock();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public ReentrantReadWriteLock getLockForTable(String tableName) {
        ConcurrentMap<String, ReentrantReadWriteLock> concurrentMap = this.rowLockTable;
        synchronized (concurrentMap) {
            ReentrantReadWriteLock lock = (ReentrantReadWriteLock)this.rowLockTable.get(tableName);
            if (lock == null) {
                lock = new ReentrantReadWriteLock();
                this.rowLockTable.put(tableName, lock);
            }
            return lock;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public Map<String, List<GlobalSecondaryIndexDescription>> getGSIsByStatusFromAllTables(final IndexStatus status, final Boolean backfilling) {
        logger.debug(LIST_ALL_TABLE_INFO);
        this.queueLock.readLock().lock();
        try {
            Map map2 = (Map)(this.queue.execute((SQLiteJob)new SQLiteDBAccessJob<Map<String, List<GlobalSecondaryIndexDescription>>>(){

                @Override
                public Map<String, List<GlobalSecondaryIndexDescription>> doWork() throws SQLiteException, IOException {
                    HashMap<String, List<GlobalSecondaryIndexDescription>> ret = new HashMap<String, List<GlobalSecondaryIndexDescription>>();
                    SQLiteStatement statement = this.getPreparedStatement(SQLiteDBAccess.LIST_ALL_TABLE_INFO);
                    while (statement.step()) {
                        String tableName = statement.columnString(0);
                        TableSchemaInfo tableSchema = MAPPER.readValue(statement.columnBlob(1), TableSchemaInfo.class);
                        ret.put(tableName, tableSchema.getGSIsByIndexStatus(status, backfilling));
                    }
                    return ret;
                }
            })).get();
            return map2;
        } finally {
            this.queueLock.readLock().unlock();
        }
    }

    @Override
    public Map<String, TableSchemaInfo> fetchAllTablesWithTimeToLiveEnabled() {
        this.queueLock.readLock().lock();
        try {
            Map map2 = (Map)(this.queue.execute((SQLiteJob)new SQLiteDBAccessJob<Map<String, TableSchemaInfo>>(){

                @Override
                public Map<String, TableSchemaInfo> doWork() throws SQLiteException, IOException {
                    HashMap<String, TableSchemaInfo> ret = new HashMap<String, TableSchemaInfo>();
                    SQLiteStatement statement = this.getPreparedStatement(SQLiteDBAccess.LIST_ALL_TABLE_INFO);
                    while (statement.step()) {
                        String tableName = statement.columnString(0);
                        TableSchemaInfo tableSchema = MAPPER.readValue(statement.columnBlob(1), TableSchemaInfo.class);
                        if (tableSchema.getTimeToLiveAttributeName() == null) continue;
                        ret.put(tableName, tableSchema);
                    }
                    return ret;
                }
            })).get();
            return map2;
        } finally {
            this.queueLock.readLock().unlock();
        }
    }

    @Override
    public byte[] beginTransaction(final String transactionId) {
        String lookupTransactionSQL = "SELECT transactionId, CreationDateTime, TransactionSignature FROM tr WHERE transactionId = ? AND CreationDateTime >= ? LIMIT 1;";
        this.queueLock.writeLock().lock();
        byte[] result = (byte[])(this.queue.execute((SQLiteJob)new SQLiteDBAccessJob<byte[]>(){

            @Override
            protected byte[] job(SQLiteConnection connection) throws Throwable {
                manualTransactionsEnabled.set(true);
                connection.exec("BEGIN");
                return (byte[])super.job(connection);
            }

            @Override
            protected byte[] doWork() throws Throwable {
                long cutoff = System.currentTimeMillis() - LocalDBClient.TRANSACTION_CLIENT_TOKEN_SURVIVAL_DURATION;
                logger.debug("SELECT transactionId, CreationDateTime, TransactionSignature FROM tr WHERE transactionId = ? AND CreationDateTime >= ? LIMIT 1;");
                SQLiteStatement statement = this.getPreparedStatement("SELECT transactionId, CreationDateTime, TransactionSignature FROM tr WHERE transactionId = ? AND CreationDateTime >= ? LIMIT 1;").bind(1, transactionId).bind(2, cutoff);
                if (statement.step()) {
                    return statement.columnBlob(2);
                }
                return null;
            }
        })).get();
        return result;
    }

    @Override
    public void commitTransaction(final String transactionId, final byte[] signature) {
        if (!this.queueLock.isWriteLockedByCurrentThread()) {
            throw new IllegalStateException("Transaction committed without being opened.");
        }
        String sql = "INSERT INTO tr(TransactionId, CreationDateTime, TransactionSignature) VALUES (?, ?, ?);";
        this.queue.execute((SQLiteJob)new SQLiteDBAccessJob<Void>(){

            @Override
            protected Void job(SQLiteConnection connection) throws Throwable {
                super.job(connection);
                connection.exec("COMMIT");
                manualTransactionsEnabled.set(false);
                return null;
            }

            @Override
            protected Void doWork() throws Throwable {
                logger.debug("INSERT INTO tr(TransactionId, CreationDateTime, TransactionSignature) VALUES (?, ?, ?);");
                SQLiteStatement statement = this.getPreparedStatement("INSERT INTO tr(TransactionId, CreationDateTime, TransactionSignature) VALUES (?, ?, ?);").bind(1, transactionId).bind(2, System.currentTimeMillis()).bind(3, signature);
                statement.step();
                return null;
            }
        });
        this.queueLock.writeLock().unlock();
    }

    @Override
    public void rollbackTransaction() {
        if (!this.queueLock.isWriteLockedByCurrentThread()) {
            throw new IllegalStateException("Transaction rolled back without being opened. Was it already committed?");
        }
        this.queue.execute((SQLiteJob)new SQLiteDBAccessJob<Void>(){

            @Override
            protected Void job(SQLiteConnection connection) throws Throwable {
                connection.exec("ROLLBACK");
                manualTransactionsEnabled.set(false);
                return null;
            }

            @Override
            protected Void doWork() throws Throwable {
                return null;
            }
        });
        this.queueLock.writeLock().unlock();
    }

    private byte[] translateKeyAttributeValue(AttributeValue attributeValue) {
        if (attributeValue.getB() != null) {
            return attributeValue.getB().array();
        }
        if (attributeValue.getN() != null) {
            return PaddingNumberEncoder.encodeBigDecimal(new BigDecimal(attributeValue.getN()));
        }
        if (attributeValue.getS() != null) {
            return attributeValue.getS().getBytes(LocalDBUtils.UTF8);
        }
        throw new IllegalArgumentException("Unknown AttributeValue type: " + attributeValue.toString());
    }

    private String constructIndexWhereClause(List<SQLiteIndexElement> indexes) {
        StringBuilder ret = new StringBuilder();
        for (int i = 0; i < indexes.size(); ++i) {
            ret.append(indexes.get(i).getSqliteColumnName()).append(" = ?");
            if (i >= indexes.size() - 1) continue;
            ret.append(" AND ");
        }
        return ret.toString();
    }

    private void applyKeyBinds(SQLiteStatement statement, List<SQLiteIndexElement> indexElements, Map<String, AttributeValue> key) throws SQLiteException {
        for (int i = 0; i < indexElements.size(); ++i) {
            String attributeName = indexElements.get(i).getDynamoDBAttribute().getAttributeName();
            byte[] value = this.translateKeyAttributeValue(key.get(attributeName));
            statement.bind(i + 1, value);
        }
    }

    protected int applyBinds(SQLiteStatement statement, int startBind, List<byte[]> bindData) throws SQLiteException {
        int endBind = startBind;
        LocalDBUtils.ldAccessAssertTrue(startBind > 0, LocalDBAccessExceptionType.UNEXPECTED_EXCEPTION, "SQL construction issue, binding at location 0.", new Object[0]);
        LocalDBUtils.ldAccessAssertTrue(endBind + bindData.size() - 1 <= statement.getBindParameterCount(), LocalDBAccessExceptionType.UNEXPECTED_EXCEPTION, "SQL construction issue, invalid number of binds.", new Object[0]);
        int j = 0;
        while (j < bindData.size()) {
            statement.bind(endBind, bindData.get(j));
            ++j;
            ++endBind;
        }
        return endBind;
    }

    private void prepareKeyColumnsWithExclusiveStartKey(TableSchemaInfo tableSchema, String indexName, Map<String, AttributeValue> exclusiveStartKey, boolean ascending, boolean isGSIIndex, String trailingHashColName, StringBuilder exclusiveStartKeyClause, List<byte[]> exclusiveStartKeyBinds) {
        if (exclusiveStartKey == null) {
            return;
        }
        String direction = ascending ? ">" : "<";
        AttributeDefinition hashKeyDef = tableSchema.getHashKeyDefinition();
        AttributeDefinition rangeKeyDef = tableSchema.getRangeKeyDefinition();
        ArrayList<String> keyColumns = new ArrayList<String>();
        ArrayList<byte[]> keyBinds = new ArrayList<byte[]>();
        if (indexName == null) {
            keyColumns.add(HASH_VALUE_COLUMN_NAME);
            keyBinds.add(LocalDBUtils.getHashValue(exclusiveStartKey.get(hashKeyDef.getAttributeName())));
            if (rangeKeyDef != null) {
                keyColumns.add(RANGE_KEY_COLUMN_NAME);
                keyBinds.add(SQLiteDBAccessUtils.translateKeyAttributeValue(exclusiveStartKey.get(rangeKeyDef.getAttributeName())));
            }
        } else if (isGSIIndex) {
            String gsiHashKey = tableSchema.getGSIHashIndexElement(indexName).getSqliteColumnName();
            String gsiHashKeyDynamoDBName = SQLiteDBAccessUtils.getGSIKeyDynamoDBName(tableSchema, indexName, KeyType.HASH.toString());
            keyColumns.add(gsiHashKey);
            keyBinds.add(SQLiteDBAccessUtils.translateKeyAttributeValue(exclusiveStartKey.get(gsiHashKeyDynamoDBName)));
            if (tableSchema.getGSIRangeIndexElement(indexName) != null) {
                String gsiRangeKey = tableSchema.getGSIRangeIndexElement(indexName).getSqliteColumnName();
                String gsiRangeKeyDynamoDBName = SQLiteDBAccessUtils.getGSIKeyDynamoDBName(tableSchema, indexName, KeyType.RANGE.toString());
                keyColumns.add(gsiRangeKey);
                keyBinds.add(SQLiteDBAccessUtils.translateKeyAttributeValue(exclusiveStartKey.get(gsiRangeKeyDynamoDBName)));
            }
            if (trailingHashColName != null) {
                keyColumns.add(trailingHashColName);
                byte[] trailHash = null;
                if (trailingHashColName != null) {
                    trailHash = trailingHashColName.equals(HASH_VALUE_COLUMN_NAME) ? LocalDBUtils.getHashValue(exclusiveStartKey.get(hashKeyDef.getAttributeName())) : (trailingHashColName.equals(RANGE_VALUE_COLUMN_NAME) ? LocalDBUtils.getHashValue(exclusiveStartKey.get(rangeKeyDef.getAttributeName())) : LocalDBUtils.getHashValue(exclusiveStartKey.get(hashKeyDef.getAttributeName()), exclusiveStartKey.get(rangeKeyDef.getAttributeName())));
                }
                keyBinds.add(trailHash);
            }
        } else {
            keyColumns.add(HASH_VALUE_COLUMN_NAME);
            keyBinds.add(LocalDBUtils.getHashValue(exclusiveStartKey.get(hashKeyDef.getAttributeName())));
            keyColumns.add(tableSchema.getLSIRangeIndexElement(indexName).getSqliteColumnName());
            String indexKeyDynamoDBName = SQLiteDBAccessUtils.getLSIIndexKeyDynamoDBName(tableSchema, indexName);
            keyBinds.add(SQLiteDBAccessUtils.translateKeyAttributeValue(exclusiveStartKey.get(indexKeyDynamoDBName)));
            if (rangeKeyDef != null) {
                keyColumns.add(RANGE_KEY_COLUMN_NAME);
                keyBinds.add(SQLiteDBAccessUtils.translateKeyAttributeValue(exclusiveStartKey.get(rangeKeyDef.getAttributeName())));
            }
        }
        LocalDBUtils.ldAccessAssertTrue(keyColumns.size() == keyBinds.size(), LocalDBAccessExceptionType.UNEXPECTED_EXCEPTION, "Key columns size should be the same as key binding size for exclusive start key SQL clause.", new Object[0]);
        exclusiveStartKeyClause.append(" AND (");
        for (int i = 0; i < keyColumns.size(); ++i) {
            if (i != 0) {
                exclusiveStartKeyClause.append(" OR (");
            }
            for (int j = 0; j < i; ++j) {
                exclusiveStartKeyClause.append((String)keyColumns.get(j)).append(" = ? AND ");
                exclusiveStartKeyBinds.add((byte[])keyBinds.get(j));
            }
            exclusiveStartKeyClause.append((String)keyColumns.get(i)).append(" ").append(direction).append(" ?");
            exclusiveStartKeyBinds.add((byte[])keyBinds.get(i));
            if (i == 0) continue;
            exclusiveStartKeyClause.append(") ");
        }
        exclusiveStartKeyClause.append(")");
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public long getDeletionDateTimeForShard(final String shardId) {
        this.queueLock.readLock().lock();
        try {
            long l = (Long)(this.queue.execute((SQLiteJob)new SQLiteDBAccessJob<Long>(){

                @Override
                public Long doWork() throws SQLiteException {
                    logger.debug(getDeletionDateTimeForShardSQL);
                    logger.debug("\t1: " + shardId);
                    SQLiteStatement statement = this.getPreparedStatement(getDeletionDateTimeForShardSQL).bind(1, shardId);
                    if (statement.step()) {
                        return statement.columnLong(0);
                    }
                    return null;
                }
            })).get();
            return l;
        } finally {
            this.queueLock.readLock().unlock();
        }
    }

    @Override
    public void dilateEventTimes(final long millis) {
        logger.debug("Performing timeDilation of {} ms", (Object)millis);
        this.queueLock.readLock().lock();
        try {
            (this.queue.execute((SQLiteJob)new SQLiteDBAccessJob<Void>(){

                @Override
                public Void doWork() throws SQLiteException {
                    logger.debug(DILATE_TABLE_CREATION_SQL);
                    logger.debug("\t1: {}", (Object)millis);
                    this.getPreparedStatement(DILATE_TABLE_CREATION_SQL).bind(1, millis).step();
                    logger.debug(DILATE_STREAM_CREATION_DELETION_SQL);
                    logger.debug("\t1: {}", (Object)millis);
                    this.getPreparedStatement(DILATE_STREAM_CREATION_DELETION_SQL).bind(1, millis).bind(2, millis).step();
                    logger.debug(DILATE_SHARD_CREATION_DELETION_SQL);
                    logger.debug("\t1: {}", (Object)millis);
                    this.getPreparedStatement(DILATE_SHARD_CREATION_DELETION_SQL).bind(1, millis).bind(2, millis).step();
                    logger.debug(DILATE_RECORD_CREATION_SQL);
                    logger.debug("\t1: {}", (Object)millis);
                    this.getPreparedStatement(DILATE_RECORD_CREATION_SQL).bind(1, millis).step();
                    logger.debug(DILATE_TRANSACTION_SQL);
                    logger.debug("\t1: {}", (Object)millis);
                    this.getPreparedStatement(DILATE_TRANSACTION_SQL).bind(1, millis).step();
                    return null;
                }
            })).get();
        } finally {
            this.queueLock.readLock().unlock();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void findAndRolloverActiveShards(String tableName, final long shardAge) {
        TableInfo tableInfo = this.getTableInfo(tableName);
        if (!tableInfo.getStreamSpecification().isStreamEnabled().booleanValue()) {
            return;
        }
        final String latestStreamId = tableInfo.getLatestStreamId();
        this.queueLock.readLock().lock();
        try {
            (this.queue.execute((SQLiteJob)new SQLiteDBAccessJob<Void>(){

                @Override
                public Void doWork() throws SQLiteException, IOException {
                    this.rolloverStreamShard(latestStreamId, shardAge, SQLiteDBAccess.this.sequenceNumberCounter);
                    return null;
                }
            })).get();
        } finally {
            this.queueLock.readLock().unlock();
        }
    }

    @Override
    public Long getEarliestNonExpiredSequenceNumberForShard(final String shardId) {
        this.queueLock.readLock().lock();
        try {
            Long l = (Long)(this.queue.execute((SQLiteJob)new SQLiteDBAccessJob<Long>(){

                @Override
                protected Long doWork() throws Throwable {
                    long trimCutOff = System.currentTimeMillis() - LocalDBClient.RECORD_SURVIVAL_DURATION;
                    SQLiteStatement statement = this.getPreparedStatement(GET_EARLIEST_SEQNUM_FOR_SHARD_SQL).bind(1, shardId).bind(2, trimCutOff);
                    logger.debug(GET_EARLIEST_SEQNUM_FOR_SHARD_SQL);
                    logger.debug("\t1: {}", (Object)shardId);
                    logger.debug("\t2: {}", (Object)trimCutOff);
                    if (statement.step()) {
                        return statement.columnLong(0);
                    }
                    return null;
                }
            })).get();
            return l;
        } finally {
            this.queueLock.readLock().unlock();
        }
    }

    @Override
    public Long getSequenceNumberStartForShard(final String shardId) {
        this.queueLock.readLock().lock();
        try {
            Long l = (Long)(this.queue.execute((SQLiteJob)new SQLiteDBAccessJob<Long>(){

                @Override
                protected Long doWork() throws Throwable {
                    SQLiteStatement statement = this.getPreparedStatement(GET_START_SEQNUM_FOR_SHARD_SQL).bind(1, shardId);
                    logger.debug(GET_START_SEQNUM_FOR_SHARD_SQL);
                    logger.debug("\t1: {}", (Object)shardId);
                    if (statement.step()) {
                        return statement.columnLong(0);
                    }
                    return null;
                }
            })).get();
            return l;
        } finally {
            this.queueLock.readLock().unlock();
        }
    }

    @Override
    public boolean shardIsNotExpired(final String shardId) {
        this.queueLock.readLock().lock();
        try {
            boolean bl = (Boolean)(this.queue.execute((SQLiteJob)new SQLiteDBAccessJob<Boolean>(){

                @Override
                protected Boolean doWork() throws Throwable {
                    long trimCutOff = System.currentTimeMillis() - LocalDBClient.SHARD_SURVIVAL_DURATION;
                    SQLiteStatement statement = this.getPreparedStatement(CHECK_IF_SHARD_UNEXPIRED_SQL).bind(1, shardId).bind(2, trimCutOff);
                    logger.debug(CHECK_IF_SHARD_UNEXPIRED_SQL);
                    logger.debug("\t1: {}", (Object)shardId);
                    logger.debug("\t1: {}", (Object)trimCutOff);
                    return statement.step();
                }
            })).get();
            return bl;
        } finally {
            this.queueLock.readLock().unlock();
        }
    }

    static {
        try {
            SQLiteLibraryLoader.initialize();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private abstract class PutItemSQLiteDBAccessJob<T>
    extends SQLiteDBAccessJob<T> {
        private PutItemSQLiteDBAccessJob() {
        }

        void doPutItem(String tableName, Map<String, byte[]> columnNameToValueMap, long itemSizeBytes) throws SQLiteException {
            String[] listOfSQLiteColumns = columnNameToValueMap.keySet().toArray(new String[0]);
            String sql = this.buildPutRecordSQL(tableName, columnNameToValueMap, listOfSQLiteColumns);
            logger.debug(sql);
            SQLiteStatement statement = this.getPreparedStatement(sql);
            int i = 1;
            for (String columnName : listOfSQLiteColumns) {
                statement.bind(i, columnNameToValueMap.get(columnName));
                ++i;
            }
            statement.bind(i, itemSizeBytes);
            statement.step();
        }

        void doBackfillItem(String tableName, Map<String, byte[]> columnNameToValueMap, Map<String, AttributeValue> item, SQLiteIndexElement rangeKeyIndex, SQLiteIndexElement hashKeyIndex) throws SQLiteException {
            if (columnNameToValueMap.size() == 0) {
                return;
            }
            String[] listOfSQLiteColumns = columnNameToValueMap.keySet().toArray(new String[0]);
            boolean hasRangeKey = rangeKeyIndex != null;
            String sql = "UPDATE " + SQLiteDBAccess.escapedTableName(tableName) + " SET " + StringUtils.join((String)", ", (String[])this.appendEachWith(" = ? ", listOfSQLiteColumns)) + " WHERE " + hashKeyIndex.getSqliteColumnName() + " = ? " + (String)(hasRangeKey ? " AND " + rangeKeyIndex.getSqliteColumnName() + " = ? " : SQLiteDBAccess.PRIMARY_KEY_INDEX_NAME) + ";";
            logger.debug(sql);
            SQLiteStatement statement = this.getPreparedStatement(sql);
            int i = 1;
            for (String columnName : listOfSQLiteColumns) {
                statement.bind(i, columnNameToValueMap.get(columnName));
                ++i;
            }
            statement.bind(i++, SQLiteDBAccess.this.translateKeyAttributeValue(item.get(hashKeyIndex.getDynamoDBAttribute().getAttributeName())));
            if (hasRangeKey) {
                statement.bind(i++, SQLiteDBAccess.this.translateKeyAttributeValue(item.get(rangeKeyIndex.getDynamoDBAttribute().getAttributeName())));
            }
            statement.step();
        }

        private String[] appendEachWith(String suffix, String[] list) {
            String[] result = new String[list.length];
            for (int i = 0; i < list.length; ++i) {
                result[i] = list[i] + suffix;
            }
            return result;
        }

        private String buildPutRecordSQL(String tableName, Map<String, byte[]> columnNameToValueMap, String[] listOfSQLiteColumns) {
            return "INSERT OR REPLACE INTO " + SQLiteDBAccess.escapedTableName(tableName) + " (" + StringUtils.join((String)", ", (String[])listOfSQLiteColumns) + ",itemSize) VALUES (" + StringUtils.join((String)", ", (String[])SQLiteDBAccess.this.repeat("?", columnNameToValueMap.size())) + ",?);";
        }
    }

    public static class BindValue {
        private byte[] bytes;
        private String text;

        public byte[] getBytes() {
            return this.bytes;
        }

        public BindValue withBytes(byte[] bytes) {
            assert (this.text == null) : "BindValue can only hold either bytes or text, not both";
            this.bytes = bytes;
            return this;
        }

        public String getText() {
            return this.text;
        }

        public BindValue withText(String text) {
            assert (this.bytes == null) : "BindValue can only hold either bytes or text, not both";
            this.text = text;
            return this;
        }
    }
}

