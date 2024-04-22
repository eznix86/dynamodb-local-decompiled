/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  com.almworks.sqlite4java.SQLiteConnection
 *  com.almworks.sqlite4java.SQLiteException
 *  com.almworks.sqlite4java.SQLiteJob
 *  com.almworks.sqlite4java.SQLiteStatement
 */
package com.amazonaws.services.dynamodbv2.local.shared.access.sqlite;

import com.almworks.sqlite4java.SQLiteConnection;
import com.almworks.sqlite4java.SQLiteException;
import com.almworks.sqlite4java.SQLiteJob;
import com.almworks.sqlite4java.SQLiteStatement;
import com.amazonaws.services.dynamodbv2.local.shared.exceptions.LocalDBAccessException;
import com.amazonaws.services.dynamodbv2.local.shared.exceptions.LocalDBAccessExceptionType;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class AmazonDynamoDBOfflineSQLiteJob<T>
extends SQLiteJob<T> {
    protected static final ThreadLocal<Boolean> manualTransactionsEnabled = new ThreadLocal<Boolean>(){

        @Override
        protected Boolean initialValue() {
            return false;
        }
    };
    private final List<SQLiteStatement> preparedStatements = new ArrayList<SQLiteStatement>();
    private SQLiteConnection connection = null;

    public T get() throws LocalDBAccessException {
        try {
            return (T)super.get();
        } catch (ExecutionException ee) {
            Throwable e = ee.getCause();
            if (e instanceof SQLiteException) {
                Pattern pattern = Pattern.compile(".*? \\[(.*?)\\]$");
                Matcher matcher = pattern.matcher(e.getMessage());
                if (matcher.matches()) {
                    String shortMessage = matcher.group(1);
                    if (shortMessage.endsWith("already exists")) {
                        throw new LocalDBAccessException(LocalDBAccessExceptionType.TABLE_ALREADY_EXISTS, shortMessage);
                    }
                    if (shortMessage.startsWith("no such table: ")) {
                        throw new LocalDBAccessException(LocalDBAccessExceptionType.TABLE_NOT_FOUND, shortMessage);
                    }
                    if (shortMessage.endsWith("not found")) {
                        throw new LocalDBAccessException(LocalDBAccessExceptionType.TABLE_NOT_FOUND, shortMessage);
                    }
                    if (shortMessage.endsWith("TableName is not unique")) {
                        throw new LocalDBAccessException(LocalDBAccessExceptionType.TABLE_ALREADY_EXISTS, shortMessage);
                    }
                }
                throw new LocalDBAccessException(LocalDBAccessExceptionType.UNEXPECTED_EXCEPTION, e.getMessage());
            }
            if (e instanceof JsonParseException) {
                throw new LocalDBAccessException(LocalDBAccessExceptionType.DATA_CORRUPTION);
            }
            if (e instanceof JsonMappingException) {
                throw new LocalDBAccessException(LocalDBAccessExceptionType.UNEXPECTED_EXCEPTION, e.getMessage());
            }
            if (e instanceof LocalDBAccessException) {
                throw (LocalDBAccessException)e;
            }
            throw new LocalDBAccessException(LocalDBAccessExceptionType.UNEXPECTED_EXCEPTION, e.getMessage());
        } catch (Exception e) {
            throw new LocalDBAccessException(LocalDBAccessExceptionType.UNEXPECTED_EXCEPTION, e.getMessage());
        }
    }

    protected T job(SQLiteConnection connection) throws Throwable {
        this.connection = connection;
        try {
            if (!manualTransactionsEnabled.get().booleanValue()) {
                connection.exec("BEGIN");
            }
            T ret = this.doWork();
            if (!manualTransactionsEnabled.get().booleanValue()) {
                connection.exec("COMMIT");
            }
            T t = ret;
            return t;
        } catch (Exception e) {
            for (SQLiteStatement statement : this.preparedStatements) {
                statement.cancel();
                statement.dispose();
            }
            if (!manualTransactionsEnabled.get().booleanValue()) {
                connection.exec("ROLLBACK");
            }
            throw e;
        } finally {
            for (SQLiteStatement statement : this.preparedStatements) {
                statement.dispose();
            }
        }
    }

    protected abstract T doWork() throws Throwable;

    protected SQLiteStatement getPreparedStatement(String sql) throws SQLiteException {
        SQLiteStatement preparedStatement = this.connection.prepare(sql);
        this.preparedStatements.add(preparedStatement);
        return preparedStatement;
    }
}

