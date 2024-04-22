/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazon.ion.impl.bin.utf8;

import com.amazon.ion.impl.bin.utf8.Pool;
import com.amazon.ion.impl.bin.utf8.PoolableByteBuffer;

public class ByteBufferPool
extends Pool<PoolableByteBuffer> {
    private static final ByteBufferPool INSTANCE = new ByteBufferPool();

    private ByteBufferPool() {
        super(new Pool.Allocator<PoolableByteBuffer>(){

            @Override
            public PoolableByteBuffer newInstance(Pool<PoolableByteBuffer> pool) {
                return new PoolableByteBuffer(pool);
            }
        });
    }

    public static ByteBufferPool getInstance() {
        return INSTANCE;
    }
}

