/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazonaws.services.dynamodbv2.local.shared.access.sqlite;

public enum DataTypes {
    TEXT("TEXT"),
    BLOB("BLOB"),
    NUMERIC("BLOB"),
    JSON_BLOB("BLOB"),
    TEXT_SET("TEXT SET"),
    BLOB_SET("BLOB SET"),
    NUMERIC_SET("NUMERIC SET"),
    INTEGER("INTEGER");

    private final String sqliteType;

    private DataTypes(String s) {
        this.sqliteType = s;
    }

    public String getSQLiteType() {
        return this.sqliteType;
    }
}

