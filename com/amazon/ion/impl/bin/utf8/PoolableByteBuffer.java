/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazon.ion.impl.bin.utf8;

import com.amazon.ion.impl.bin.utf8.Pool;
import com.amazon.ion.impl.bin.utf8.Poolable;
import java.nio.ByteBuffer;

public class PoolableByteBuffer
extends Poolable<PoolableByteBuffer> {
    static final int BUFFER_SIZE_IN_BYTES = 4096;
    private final ByteBuffer buffer = ByteBuffer.allocate(4096);

    PoolableByteBuffer(Pool<PoolableByteBuffer> pool) {
        super(pool);
    }

    public ByteBuffer getBuffer() {
        return this.buffer;
    }
}

