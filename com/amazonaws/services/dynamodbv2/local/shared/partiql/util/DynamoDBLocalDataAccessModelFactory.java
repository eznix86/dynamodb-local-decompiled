/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazonaws.services.dynamodbv2.local.shared.partiql.util;

import ddb.partiql.shared.dbenv.DataAccessModelFactory;

public class DynamoDBLocalDataAccessModelFactory
implements DataAccessModelFactory {
    public String makeAttributeName(String name) {
        return name;
    }
}

