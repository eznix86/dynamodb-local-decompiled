/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazon.ion.impl.bin;

import com.amazon.ion.IonWriter;
import java.io.IOException;

@Deprecated
public interface _Private_IonRawWriter
extends IonWriter {
    public void setFieldNameSymbol(int var1);

    public void setTypeAnnotationSymbols(int ... var1);

    public void addTypeAnnotationSymbol(int var1);

    public void writeSymbolToken(int var1) throws IOException;

    public void writeString(byte[] var1, int var2, int var3) throws IOException;
}

