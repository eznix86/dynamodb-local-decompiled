/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazonaws.services.dynamodbv2.local.shared.dataaccess;

public enum OperationType {
    INSERT("INSERT"),
    MODIFY("MODIFY"),
    REMOVE("REMOVE"),
    EXPIRE("EXPIRE");

    private final String value;

    private OperationType(String value) {
        this.value = value;
    }

    public static OperationType fromValue(String value) {
        if (value == null || "".equals(value)) {
            throw new IllegalArgumentException("Value cannot be null or empty!");
        }
        if ("INSERT".equals(value)) {
            return INSERT;
        }
        if ("MODIFY".equals(value)) {
            return MODIFY;
        }
        if ("REMOVE".equals(value)) {
            return REMOVE;
        }
        if ("EXPIRE".equals(value)) {
            return EXPIRE;
        }
        throw new IllegalArgumentException("Cannot create enum from " + value + " value!");
    }

    public String toString() {
        return this.value;
    }
}

