/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazon.ion.impl;

import com.amazon.ion.impl.ByteReader;
import com.amazon.ion.impl.ByteWriter;
import java.io.IOException;
import java.io.OutputStream;

interface ByteBuffer {
    public ByteReader getReader();

    public ByteWriter getWriter();

    public byte[] getBytes();

    public int getBytes(byte[] var1, int var2, int var3);

    public void writeBytes(OutputStream var1) throws IOException;
}

