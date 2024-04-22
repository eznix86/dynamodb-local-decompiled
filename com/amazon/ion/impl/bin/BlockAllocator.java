/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazon.ion.impl.bin;

import com.amazon.ion.impl.bin.Block;
import java.io.Closeable;

abstract class BlockAllocator
implements Closeable {
    BlockAllocator() {
    }

    public abstract Block allocateBlock();

    public abstract int getBlockSize();

    @Override
    public abstract void close();
}

