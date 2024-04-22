/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazonaws.services.dynamodbv2.local.shared.env;

import ddb.partiql.shared.dbenv.DataAccessModelFactory;

public class LocalDataAccessModelFactory
implements DataAccessModelFactory {
    public String makeAttributeName(String name) {
        return name;
    }
}

