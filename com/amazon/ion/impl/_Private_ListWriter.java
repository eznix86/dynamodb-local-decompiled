/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazon.ion.impl;

import com.amazon.ion.IonWriter;
import java.io.IOException;

public interface _Private_ListWriter
extends IonWriter {
    public void writeBoolList(boolean[] var1) throws IOException;

    public void writeFloatList(float[] var1) throws IOException;

    public void writeFloatList(double[] var1) throws IOException;

    public void writeIntList(byte[] var1) throws IOException;

    public void writeIntList(short[] var1) throws IOException;

    public void writeIntList(int[] var1) throws IOException;

    public void writeIntList(long[] var1) throws IOException;

    public void writeStringList(String[] var1) throws IOException;
}

