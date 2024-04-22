/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazon.ion.impl.bin.utf8;

import com.amazon.ion.impl.bin.utf8.Pool;
import java.io.Closeable;

abstract class Poolable<T extends Poolable<T>>
implements Closeable {
    private final Pool<T> pool;

    Poolable(Pool<T> pool) {
        this.pool = pool;
    }

    @Override
    public void close() {
        this.pool.returnToPool(this);
    }
}

