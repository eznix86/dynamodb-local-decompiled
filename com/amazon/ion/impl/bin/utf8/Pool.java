/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazon.ion.impl.bin.utf8;

import com.amazon.ion.impl.bin.utf8.Poolable;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;

abstract class Pool<T extends Poolable<?>> {
    private static final int MAX_QUEUE_SIZE = 128;
    private final Queue<T> objectQueue;
    private final AtomicInteger size;
    private final Allocator<T> allocator;

    Pool(Allocator<T> allocator) {
        this.allocator = allocator;
        this.objectQueue = new ConcurrentLinkedQueue<T>();
        this.size = new AtomicInteger(0);
    }

    public T getOrCreate() {
        Poolable object = (Poolable)this.objectQueue.poll();
        if (object == null) {
            object = this.allocator.newInstance(this);
        } else {
            this.size.decrementAndGet();
        }
        return (T)object;
    }

    public void returnToPool(T object) {
        if (this.size.getAndIncrement() < 128) {
            this.objectQueue.offer(object);
        } else {
            this.size.decrementAndGet();
        }
    }

    static interface Allocator<T extends Poolable<?>> {
        public T newInstance(Pool<T> var1);
    }
}

