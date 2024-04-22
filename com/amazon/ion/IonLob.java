/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazon.ion;

import com.amazon.ion.IonValue;
import com.amazon.ion.UnknownSymbolException;
import java.io.InputStream;

public interface IonLob
extends IonValue {
    public InputStream newInputStream();

    public byte[] getBytes();

    public void setBytes(byte[] var1);

    public void setBytes(byte[] var1, int var2, int var3);

    public int byteSize();

    @Override
    public IonLob clone() throws UnknownSymbolException;
}

