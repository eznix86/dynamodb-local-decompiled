/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazon.ion.impl;

import java.io.IOException;
import java.math.BigDecimal;

interface ByteWriter {
    public void write(byte var1) throws IOException;

    public void write(byte[] var1, int var2, int var3) throws IOException;

    public int position();

    public void position(int var1);

    public void insert(int var1);

    public void remove(int var1);

    public void writeTypeDesc(int var1) throws IOException;

    public int writeTypeDescWithLength(int var1, int var2, int var3) throws IOException;

    public int writeTypeDescWithLength(int var1, int var2) throws IOException;

    public int writeIonInt(long var1, int var3) throws IOException;

    public int writeVarInt(long var1, int var3, boolean var4) throws IOException;

    public int writeVarUInt(long var1, int var3, boolean var4) throws IOException;

    public int writeIonInt(int var1, int var2) throws IOException;

    public int writeVarInt(int var1, int var2, boolean var3) throws IOException;

    public int writeVarUInt(int var1, int var2, boolean var3) throws IOException;

    public int writeFloat(double var1) throws IOException;

    public int writeDecimal(BigDecimal var1) throws IOException;

    public int writeString(String var1) throws IOException;
}

