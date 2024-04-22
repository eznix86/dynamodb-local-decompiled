/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazonaws.services.dynamodbv2.local.shared.helpers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SerializationUtils {
    public static Map<String, Object> cloneKeyToNewKey(Map<String, Object> map2, String keyToCopy, String newKeyName) {
        HashMap<String, Object> mapCopy = new HashMap<String, Object>(map2);
        for (Map.Entry entry : mapCopy.entrySet()) {
            if (((String)entry.getKey()).equals(keyToCopy)) {
                Object value = entry.getValue();
                map2.put(newKeyName, value);
            }
            if (entry.getValue() instanceof Map) {
                SerializationUtils.cloneKeyToNewKey((Map)entry.getValue(), keyToCopy, newKeyName);
            }
            if (!(entry.getValue() instanceof ArrayList)) continue;
            SerializationUtils.findMapInArr((ArrayList)entry.getValue(), keyToCopy, newKeyName);
        }
        return map2;
    }

    public static void findMapInArr(ArrayList array, String keyToCopy, String newKeyName) {
        for (Object value : array) {
            if (value instanceof Map) {
                SerializationUtils.cloneKeyToNewKey((Map)value, keyToCopy, newKeyName);
            }
            if (!(value instanceof ArrayList)) continue;
            SerializationUtils.findMapInArr((ArrayList)value, keyToCopy, newKeyName);
        }
    }
}

