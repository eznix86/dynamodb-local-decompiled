/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazonaws.services.dynamodbv2.local.shared.access;

import com.amazonaws.services.dynamodbv2.local.shared.access.LocalDBUtils;
import com.amazonaws.services.dynamodbv2.local.shared.access.UnsignedByteArrayComparator;
import java.math.BigDecimal;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EncoderDecoderUtils {
    private static final Charset m_utf8_charset = StandardCharsets.UTF_8;

    public static byte[] encodeString(String str) {
        return str.getBytes(m_utf8_charset);
    }

    public static String decodeString(byte[] ba) {
        return new String(ba, m_utf8_charset);
    }

    public static ArrayList<byte[]> getRawBytesForStringSet(List<String> stringSet) {
        ArrayList<byte[]> rawSetValue = new ArrayList<byte[]>(stringSet.size());
        for (String str : stringSet) {
            byte[] byteArrayEncodedString = EncoderDecoderUtils.encodeString(str);
            rawSetValue.add(byteArrayEncodedString);
        }
        Collections.sort(rawSetValue, UnsignedByteArrayComparator.SINGLETON);
        return rawSetValue;
    }

    public static ArrayList<byte[]> getRawBytesForNumberSet(List<String> numberSet) {
        ArrayList<byte[]> rawSetValue = new ArrayList<byte[]>(numberSet.size());
        for (String number : numberSet) {
            byte[] byteArrayEncodedBigDecimal = LocalDBUtils.encodeBigDecimal(new BigDecimal(number));
            rawSetValue.add(byteArrayEncodedBigDecimal);
        }
        Collections.sort(rawSetValue, UnsignedByteArrayComparator.SINGLETON);
        return rawSetValue;
    }

    public static List<byte[]> getRawBytesForBinarySet(List<ByteBuffer> byteBufferList) {
        ArrayList<byte[]> rawSetValue = new ArrayList<byte[]>(byteBufferList.size());
        for (ByteBuffer byteBuffer : byteBufferList) {
            byte[] localByteArray = new byte[byteBuffer.capacity()];
            byteBuffer.duplicate().get(localByteArray, 0, byteBuffer.limit());
            rawSetValue.add(localByteArray);
        }
        Collections.sort(rawSetValue, UnsignedByteArrayComparator.SINGLETON);
        return rawSetValue;
    }
}

