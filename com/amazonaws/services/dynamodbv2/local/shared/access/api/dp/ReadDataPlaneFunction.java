/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazonaws.services.dynamodbv2.local.shared.access.api.dp;

import com.amazonaws.services.dynamodbv2.dbenv.DbEnv;
import com.amazonaws.services.dynamodbv2.exceptions.AWSExceptionFactory;
import com.amazonaws.services.dynamodbv2.local.shared.access.LocalDBAccess;
import com.amazonaws.services.dynamodbv2.local.shared.access.LocalDBInputConverter;
import com.amazonaws.services.dynamodbv2.local.shared.access.LocalDBOutputConverter;
import com.amazonaws.services.dynamodbv2.local.shared.access.api.dp.DataPlaneFunction;
import com.amazonaws.services.dynamodbv2.local.shared.helpers.TransactionsEnabledMode;

public abstract class ReadDataPlaneFunction<I, O>
extends DataPlaneFunction<I, O> {
    protected ReadDataPlaneFunction(LocalDBAccess dbAccess, DbEnv localDBEnv, LocalDBInputConverter inputConverter, LocalDBOutputConverter localDBOutputConverter, AWSExceptionFactory awsExceptionFactory, TransactionsEnabledMode transactionsMode) {
        super(dbAccess, localDBEnv, inputConverter, localDBOutputConverter, awsExceptionFactory, transactionsMode);
    }
}

