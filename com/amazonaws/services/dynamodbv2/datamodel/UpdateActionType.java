/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazonaws.services.dynamodbv2.datamodel;

public enum UpdateActionType {
    SET(true),
    DELETE(false),
    ADD(true);

    private boolean includeInUpdateRequestSize;

    private UpdateActionType(boolean includeInUpdateRequestSize) {
        this.includeInUpdateRequestSize = includeInUpdateRequestSize;
    }

    public boolean includeInUpdateRequestSize() {
        return this.includeInUpdateRequestSize;
    }
}

