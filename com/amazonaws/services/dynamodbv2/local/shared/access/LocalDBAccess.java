/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  com.amazonaws.services.dynamodbv2.model.AttributeDefinition
 *  com.amazonaws.services.dynamodbv2.model.BillingMode
 *  com.amazonaws.services.dynamodbv2.model.GlobalSecondaryIndex
 *  com.amazonaws.services.dynamodbv2.model.GlobalSecondaryIndexDescription
 *  com.amazonaws.services.dynamodbv2.model.IndexStatus
 *  com.amazonaws.services.dynamodbv2.model.LocalSecondaryIndex
 *  com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput
 *  com.amazonaws.services.dynamodbv2.model.Record
 *  com.amazonaws.services.dynamodbv2.model.StreamDescription
 *  com.amazonaws.services.dynamodbv2.model.StreamSpecification
 */
package com.amazonaws.services.dynamodbv2.local.shared.access;

import com.amazonaws.services.dynamodbv2.exceptions.AWSExceptionFactory;
import com.amazonaws.services.dynamodbv2.exceptions.AmazonServiceExceptionType;
import com.amazonaws.services.dynamodbv2.local.shared.access.ListTablesResultInfo;
import com.amazonaws.services.dynamodbv2.local.shared.access.QueryResultInfo;
import com.amazonaws.services.dynamodbv2.local.shared.access.ShardIterator;
import com.amazonaws.services.dynamodbv2.local.shared.access.TableInfo;
import com.amazonaws.services.dynamodbv2.local.shared.access.sqlite.TableSchemaInfo;
import com.amazonaws.services.dynamodbv2.local.shared.exceptions.LocalDBClientExceptionMessage;
import com.amazonaws.services.dynamodbv2.local.shared.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.local.shared.model.Condition;
import com.amazonaws.services.dynamodbv2.model.AttributeDefinition;
import com.amazonaws.services.dynamodbv2.model.BillingMode;
import com.amazonaws.services.dynamodbv2.model.GlobalSecondaryIndex;
import com.amazonaws.services.dynamodbv2.model.GlobalSecondaryIndexDescription;
import com.amazonaws.services.dynamodbv2.model.IndexStatus;
import com.amazonaws.services.dynamodbv2.model.LocalSecondaryIndex;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.model.Record;
import com.amazonaws.services.dynamodbv2.model.StreamDescription;
import com.amazonaws.services.dynamodbv2.model.StreamSpecification;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public interface LocalDBAccess {
    public static final int LOCK_WAIT_TIMEOUT_IN_SECONDS = 10;

    public void createTable(String var1, AttributeDefinition var2, AttributeDefinition var3, List<AttributeDefinition> var4, List<LocalSecondaryIndex> var5, List<GlobalSecondaryIndex> var6, ProvisionedThroughput var7, BillingMode var8, Boolean var9, StreamSpecification var10);

    public void deleteTable(String var1);

    public void updateTable(String var1, ProvisionedThroughput var2, BillingMode var3, long var4, List<AttributeDefinition> var6, List<GlobalSecondaryIndexDescription> var7, Boolean var8, StreamSpecification var9);

    public void updateTable(String var1, String var2);

    public Map<String, AttributeValue> getRecord(String var1, Map<String, AttributeValue> var2);

    public boolean deleteRecord(String var1, Map<String, AttributeValue> var2, boolean var3);

    public void putRecord(String var1, Map<String, AttributeValue> var2, AttributeValue var3, AttributeValue var4, boolean var5);

    public QueryResultInfo queryRecords(String var1, String var2, Map<String, Condition> var3, Map<String, AttributeValue> var4, Long var5, boolean var6, byte[] var7, byte[] var8, boolean var9, boolean var10);

    public long getTableItemCount(String var1);

    public long getLSIItemCount(String var1, String var2);

    public long getGSIItemCount(String var1, String var2);

    public TableInfo getTableInfo(String var1);

    public List<StreamDescription> getStreamInfo(String var1, String var2, Integer var3, String var4, String var5);

    public void close();

    public ListTablesResultInfo listTables(String var1, Long var2);

    public long getTableByteSize(String var1);

    public long getLSIByteSize(String var1, String var2);

    public long getGSIByteSize(String var1, String var2);

    public ReentrantReadWriteLock getLockForTable(String var1);

    public Map<String, List<GlobalSecondaryIndexDescription>> getGSIsByStatusFromAllTables(IndexStatus var1, Boolean var2);

    public Map<String, TableSchemaInfo> fetchAllTablesWithTimeToLiveEnabled();

    public void createGSIColumns(String var1, String var2);

    public void backfillGSI(String var1, String var2);

    public void deleteGSI(String var1, String var2);

    public int numberOfSubscriberWideInflightOnlineCreateIndexesOperations();

    public void optimizeDBBeforeStartup();

    public List<Record> getStreamRecords(Integer var1, ShardIterator var2);

    public Long getLatestSequenceNumberForShard(String var1);

    public long getDeletionDateTimeForShard(String var1);

    public Long getEarliestNonExpiredSequenceNumberForShard(String var1);

    public Long getSequenceNumberStartForShard(String var1);

    public boolean shardIsNotExpired(String var1);

    public void dilateEventTimes(long var1);

    public void findAndRolloverActiveShards(String var1, long var2);

    public byte[] beginTransaction(String var1);

    public void commitTransaction(String var1, byte[] var2);

    public void rollbackTransaction();

    public static abstract class WriteLockWithTimeout
    extends LockWithTimeout {
        protected WriteLockWithTimeout(ReentrantReadWriteLock tableLock, int timeoutInSeconds) {
            super(tableLock, timeoutInSeconds);
        }

        public void execute() {
            block7: {
                ReentrantReadWriteLock.WriteLock lock = this.lockForTable.writeLock();
                try {
                    if (lock.tryLock(this.timeOutInSeconds, TimeUnit.SECONDS)) {
                        this.criticalSection();
                        break block7;
                    }
                    throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.INTERNAL_SERVER_ERROR, LocalDBClientExceptionMessage.TIME_OUT_WHILE_ACQUIRING_LOCK.getMessage());
                } catch (InterruptedException e) {
                    throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.INTERNAL_SERVER_ERROR, LocalDBClientExceptionMessage.INTERRUPTED_EXCEPTION_WHILE_ACQUIRING_LOCK.getMessage());
                } finally {
                    if (lock.isHeldByCurrentThread()) {
                        lock.unlock();
                    }
                }
            }
        }
    }

    public static abstract class ReadLockWithTimeout
    extends LockWithTimeout {
        protected ReadLockWithTimeout(ReentrantReadWriteLock tableLock, int timeoutInSeconds) {
            super(tableLock, timeoutInSeconds);
        }

        public void execute() {
            block7: {
                ReentrantReadWriteLock.ReadLock lock = this.lockForTable.readLock();
                boolean lockedByThisThread = false;
                try {
                    if (lock.tryLock(this.timeOutInSeconds, TimeUnit.SECONDS)) {
                        lockedByThisThread = true;
                        this.criticalSection();
                        break block7;
                    }
                    throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.INTERNAL_SERVER_ERROR, LocalDBClientExceptionMessage.TIME_OUT_WHILE_ACQUIRING_LOCK.getMessage());
                } catch (InterruptedException e) {
                    throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.INTERNAL_SERVER_ERROR, LocalDBClientExceptionMessage.INTERRUPTED_EXCEPTION_WHILE_ACQUIRING_LOCK.getMessage());
                } finally {
                    if (lockedByThisThread) {
                        lock.unlock();
                    }
                }
            }
        }
    }

    public static abstract class LockWithTimeout {
        protected final ReentrantReadWriteLock lockForTable;
        protected final int timeOutInSeconds;

        LockWithTimeout(ReentrantReadWriteLock tableLock, int timeoutInSeconds) {
            this.lockForTable = tableLock;
            this.timeOutInSeconds = timeoutInSeconds;
        }

        public abstract void criticalSection();
    }
}

