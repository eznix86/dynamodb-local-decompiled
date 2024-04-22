/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazonaws.services.dynamodbv2.local.shared.model;

import com.amazonaws.services.dynamodbv2.local.shared.model.AttributeValue;
import java.util.Map;

public class TableNameAndPrimaryKey {
    private final String tableName;
    private final Map<String, AttributeValue> primaryKey;

    public TableNameAndPrimaryKey(String tableName, Map<String, AttributeValue> primaryKey) {
        this.tableName = tableName;
        this.primaryKey = primaryKey;
    }

    public int hashCode() {
        int prime = 31;
        int result = 1;
        result = 31 * result + (this.primaryKey == null ? 0 : this.primaryKey.hashCode());
        result = 31 * result + (this.tableName == null ? 0 : this.tableName.hashCode());
        return result;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        TableNameAndPrimaryKey other = (TableNameAndPrimaryKey)obj;
        if (this.primaryKey == null ? other.primaryKey != null : !this.primaryKey.equals(other.primaryKey)) {
            return false;
        }
        if (this.tableName == null) {
            return other.tableName == null;
        }
        return this.tableName.equals(other.tableName);
    }
}

