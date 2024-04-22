/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazon.ion.util;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PushbackInputStream;
import java.util.Arrays;
import java.util.zip.GZIPInputStream;

class GzipOrRawInputStream
extends FilterInputStream {
    static final byte[] GZIP_HEADER = new byte[]{31, -117};

    GzipOrRawInputStream(InputStream raw) throws IOException {
        this(raw, 512);
    }

    GzipOrRawInputStream(InputStream raw, int bufferSize) throws IOException {
        super(null);
        int octet;
        int size;
        byte[] header = new byte[GZIP_HEADER.length];
        PushbackInputStream input = new PushbackInputStream(raw, 2);
        for (size = 0; size < header.length && (octet = input.read()) != -1; ++size) {
            header[size] = (byte)octet;
        }
        assert (size <= 2);
        input.unread(header, 0, size);
        this.in = size == 2 && Arrays.equals(header, GZIP_HEADER) ? new GZIPInputStream((InputStream)input, bufferSize) : input;
    }
}

