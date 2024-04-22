/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazon.ion.impl;

import com.amazon.ion.Decimal;
import com.amazon.ion.Timestamp;
import java.io.IOException;

interface ByteReader {
    public static final int EOF = -1;

    public int position();

    public void position(int var1);

    public void skip(int var1);

    public int read() throws IOException;

    public int read(byte[] var1, int var2, int var3) throws IOException;

    public int readTypeDesc() throws IOException;

    public long readVarLong() throws IOException;

    public long readVarULong() throws IOException;

    public long readULong(int var1) throws IOException;

    public int readVarInt() throws IOException;

    public int readVarUInt() throws IOException;

    public double readFloat(int var1) throws IOException;

    public Decimal readDecimal(int var1) throws IOException;

    public Timestamp readTimestamp(int var1) throws IOException;

    public String readString(int var1) throws IOException;
}

