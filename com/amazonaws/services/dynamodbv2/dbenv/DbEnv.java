/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazonaws.services.dynamodbv2.dbenv;

import com.amazonaws.services.dynamodbv2.dbenv.DbConfig;
import com.amazonaws.services.dynamodbv2.dbenv.DbExecutionError;
import com.amazonaws.services.dynamodbv2.dbenv.DbInternalError;
import com.amazonaws.services.dynamodbv2.dbenv.DbValidationError;
import java.util.Collection;

public interface DbEnv {
    public void dbAssert(boolean var1, String var2, String var3, Object ... var4);

    public void logError(String var1, String var2, Object ... var3);

    public int getConfigInt(DbConfig var1);

    public Collection<String> getConfigStringCollection(DbConfig var1);

    public void throwValidationError(DbValidationError var1, Object ... var2);

    public void throwExecutionError(DbExecutionError var1, Object ... var2);

    public void throwInternalError(DbInternalError var1, Object ... var2);
}

