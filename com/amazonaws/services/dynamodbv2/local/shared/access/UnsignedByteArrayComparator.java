/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazonaws.services.dynamodbv2.local.shared.access;

import java.util.Comparator;

public enum UnsignedByteArrayComparator implements Comparator<byte[]>
{
    SINGLETON;


    private static int compareUnsignedBytes(byte b1, byte b2) {
        return Character.compare((char)b1, (char)b2);
    }

    public static int compareUnsignedByteArrays(byte[] ba1, byte[] ba2) {
        int minByteArrSize = Math.min(ba1.length, ba2.length);
        for (int j = 0; j < minByteArrSize; ++j) {
            int cmp = UnsignedByteArrayComparator.compareUnsignedBytes(ba1[j], ba2[j]);
            if (cmp == 0) continue;
            return cmp;
        }
        return Integer.compare(ba1.length, ba2.length);
    }

    @Override
    public int compare(byte[] ba1, byte[] ba2) {
        return UnsignedByteArrayComparator.compareUnsignedByteArrays(ba1, ba2);
    }
}

