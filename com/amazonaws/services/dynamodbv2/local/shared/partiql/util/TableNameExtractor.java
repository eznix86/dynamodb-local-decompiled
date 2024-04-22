/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazonaws.services.dynamodbv2.local.shared.partiql.util;

import com.amazonaws.services.dynamodbv2.local.shared.dataaccess.DynamoDBLocalSharedOpContext;
import ddb.partiql.shared.dbenv.PartiQLDbEnv;
import ddb.partiql.shared.util.TableNameExtractorBase;

public class TableNameExtractor
extends TableNameExtractorBase<DynamoDBLocalSharedOpContext> {
    public TableNameExtractor(double tableNameMinLength, double tableNameMaxLength, PartiQLDbEnv dbEnv) {
        super(tableNameMinLength, tableNameMaxLength, dbEnv);
    }

    @Override
    protected void addUnsupportedSyntaxCount(DynamoDBLocalSharedOpContext opContext, int count) {
    }
}

