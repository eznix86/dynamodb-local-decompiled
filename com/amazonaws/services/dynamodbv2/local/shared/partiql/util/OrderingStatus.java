/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazonaws.services.dynamodbv2.local.shared.partiql.util;

public final class OrderingStatus {
    private final String hashKeyName;
    private final OrderingSpec hashKeyStatus;
    private final OrderingSpec rangeKeyStatus;

    public OrderingStatus(String hashKeyName, OrderingSpec hashKeyStatus, OrderingSpec rangeKeyStatus) {
        this.hashKeyName = hashKeyName;
        this.hashKeyStatus = hashKeyStatus;
        this.rangeKeyStatus = rangeKeyStatus;
    }

    public String getHashKeyName() {
        return this.hashKeyName;
    }

    public OrderingSpec getHashKeyOrder() {
        return this.hashKeyStatus;
    }

    public OrderingSpec getRangeKeyOrder() {
        return this.rangeKeyStatus;
    }

    public static enum OrderingSpec {
        ASC("ASC"),
        DESC("DESC");

        public final String orderingKeyword;

        private OrderingSpec(String orderingKeyword) {
            this.orderingKeyword = orderingKeyword;
        }
    }
}

