/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazonaws.services.dynamodbv2.local.shared.access;

import java.util.List;

public class ListTablesResultInfo {
    private final List<String> tableNames;
    private final String lastEvaluatedTableName;

    public ListTablesResultInfo(List<String> tableNames, String lastEvaluatedTableName) {
        this.tableNames = tableNames;
        this.lastEvaluatedTableName = lastEvaluatedTableName;
    }

    public List<String> getTableNames() {
        return this.tableNames;
    }

    public String getLastEvaluatedTableName() {
        return this.lastEvaluatedTableName;
    }
}

