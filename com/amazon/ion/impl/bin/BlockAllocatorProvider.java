/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazon.ion.impl.bin;

import com.amazon.ion.impl.bin.BlockAllocator;

abstract class BlockAllocatorProvider {
    BlockAllocatorProvider() {
    }

    public abstract BlockAllocator vendAllocator(int var1);
}

