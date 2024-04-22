/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazonaws.services.dynamodbv2.local.google;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Lists {
    public static <T> List<T> newArrayList(T ... items) {
        if (items == null || items.length == 0) {
            return new ArrayList();
        }
        ArrayList result = new ArrayList(items.length);
        Collections.addAll(result, items);
        return result;
    }

    public static <T> List<T> newArrayListWithExpectedSize(int size) {
        return new ArrayList(size);
    }
}

