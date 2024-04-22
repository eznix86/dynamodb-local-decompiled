/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazon.ion.impl;

import com.amazon.ion.impl._Private_ByteTransferSink;
import java.io.IOException;

public interface _Private_ByteTransferReader {
    public void transferCurrentValue(_Private_ByteTransferSink var1) throws IOException;
}

