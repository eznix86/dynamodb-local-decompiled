/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazon.ion;

import com.amazon.ion.IonWriter;
import java.io.IOException;
import java.io.OutputStream;

@Deprecated
public interface IonBinaryWriter
extends IonWriter {
    @Deprecated
    public int byteSize();

    @Deprecated
    public byte[] getBytes() throws IOException;

    @Deprecated
    public int getBytes(byte[] var1, int var2, int var3) throws IOException;

    @Deprecated
    public int writeBytes(OutputStream var1) throws IOException;
}

