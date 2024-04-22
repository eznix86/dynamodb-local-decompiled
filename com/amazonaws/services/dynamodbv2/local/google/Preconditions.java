/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazonaws.services.dynamodbv2.local.google;

public class Preconditions {
    public static void checkArgument(boolean value) {
        if (!value) {
            throw new IllegalArgumentException();
        }
    }

    public static void checkState(boolean value) {
        if (!value) {
            throw new IllegalStateException();
        }
    }

    public static void checkArgument(boolean value, String message) {
        if (!value) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void checkNotNull(Object o2, String message) {
        if (o2 == null) {
            throw new NullPointerException(message);
        }
    }
}

