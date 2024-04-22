/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazonaws.services.dynamodbv2.local.google;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Sets {
    public static <T> Set<T> newHashSet(T ... items) {
        if (items == null || items.length == 0) {
            return new HashSet();
        }
        HashSet result = new HashSet(items.length);
        Collections.addAll(result, items);
        return result;
    }
}

