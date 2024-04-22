/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazonaws.services.dynamodbv2.local.shared.access;

import java.nio.ByteBuffer;
import java.util.Comparator;

public class ByteBufferComparator
implements Comparator<ByteBuffer> {
    public static final ByteBufferComparator singleton = new ByteBufferComparator();

    private ByteBufferComparator() {
    }

    private static int compareUnsignedBytes(byte b1, byte b2) {
        return Character.compare((char)b1, (char)b2);
    }

    private static int compareUnsignedByteArrays(ByteBuffer ba1, ByteBuffer ba2) {
        int minByteArrSize = Math.min(ba1.array().length, ba2.array().length);
        for (int j = 0; j < minByteArrSize; ++j) {
            int cmp = ByteBufferComparator.compareUnsignedBytes(ba1.array()[j], ba2.array()[j]);
            if (cmp == 0) continue;
            return cmp;
        }
        return Integer.compare(ba1.array().length, ba2.array().length);
    }

    @Override
    public int compare(ByteBuffer ba1, ByteBuffer ba2) {
        return ByteBufferComparator.compareUnsignedByteArrays(ba1, ba2);
    }
}

