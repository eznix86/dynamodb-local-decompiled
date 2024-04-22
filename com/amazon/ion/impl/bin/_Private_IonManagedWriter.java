/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazon.ion.impl.bin;

import com.amazon.ion.IonWriter;
import com.amazon.ion.impl.bin._Private_IonRawWriter;
import java.io.IOException;

@Deprecated
public interface _Private_IonManagedWriter
extends IonWriter {
    public _Private_IonRawWriter getRawWriter();

    public void requireLocalSymbolTable() throws IOException;
}

