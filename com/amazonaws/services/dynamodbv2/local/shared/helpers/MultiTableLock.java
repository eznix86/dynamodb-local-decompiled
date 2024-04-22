/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazonaws.services.dynamodbv2.local.shared.helpers;

import com.amazonaws.services.dynamodbv2.local.shared.access.LocalDBAccess;
import java.util.SortedSet;

public class MultiTableLock {
    private final SortedSet<String> tables;
    private final LocalDBAccess dbAccess;
    private final LockMode lockMode;

    public MultiTableLock(SortedSet<String> tables, LocalDBAccess dbAccess, LockMode lockMode) {
        this.tables = tables;
        this.dbAccess = dbAccess;
        this.lockMode = lockMode;
    }

    public Runnable wrapInTableLocks(Runnable criticalSection) {
        Runnable current = criticalSection;
        for (String table2 : this.tables) {
            current = new SingleTableLock(this.dbAccess, table2, current, this.lockMode);
        }
        return current;
    }

    public static enum LockMode {
        READ,
        WRITE;

    }

    private static class SingleTableLock
    implements Runnable {
        private final LocalDBAccess dbAccess;
        private final String tableName;
        private final Runnable previous;
        private final LockMode lockMode;

        public SingleTableLock(LocalDBAccess dbAccess, String tableName, Runnable previous, LockMode lockMode) {
            this.dbAccess = dbAccess;
            this.tableName = tableName;
            this.previous = previous;
            this.lockMode = lockMode;
        }

        @Override
        public void run() {
            if (this.lockMode == LockMode.READ) {
                new LocalDBAccess.ReadLockWithTimeout(this.dbAccess.getLockForTable(this.tableName), 10){

                    @Override
                    public void criticalSection() {
                        previous.run();
                    }
                }.execute();
            } else {
                new LocalDBAccess.WriteLockWithTimeout(this.dbAccess.getLockForTable(this.tableName), 10){

                    @Override
                    public void criticalSection() {
                        previous.run();
                    }
                }.execute();
            }
        }
    }
}

