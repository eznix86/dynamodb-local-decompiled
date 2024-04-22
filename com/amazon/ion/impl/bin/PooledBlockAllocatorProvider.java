/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazon.ion.impl.bin;

import com.amazon.ion.impl.bin.Block;
import com.amazon.ion.impl.bin.BlockAllocator;
import com.amazon.ion.impl.bin.BlockAllocatorProvider;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

final class PooledBlockAllocatorProvider
extends BlockAllocatorProvider {
    private static final PooledBlockAllocatorProvider INSTANCE = new PooledBlockAllocatorProvider();
    private final ConcurrentMap<Integer, BlockAllocator> allocators = new ConcurrentHashMap<Integer, BlockAllocator>();

    private PooledBlockAllocatorProvider() {
    }

    public static PooledBlockAllocatorProvider getInstance() {
        return INSTANCE;
    }

    @Override
    public BlockAllocator vendAllocator(int blockSize) {
        if (blockSize <= 0) {
            throw new IllegalArgumentException("Invalid block size: " + blockSize);
        }
        BlockAllocator allocator = (BlockAllocator)this.allocators.get(blockSize);
        if (allocator == null) {
            allocator = new PooledBlockAllocator(blockSize);
            BlockAllocator existingAllocator = this.allocators.putIfAbsent(blockSize, allocator);
            if (existingAllocator != null) {
                allocator = existingAllocator;
            }
        }
        return allocator;
    }

    private static final class PooledBlockAllocator
    extends BlockAllocator {
        private final int blockSize;
        private final int blockLimit;
        private final ConcurrentLinkedQueue<Block> freeBlocks;
        private final AtomicInteger size = new AtomicInteger(0);
        static final int FREE_CAPACITY = 0x4000000;

        public PooledBlockAllocator(int blockSize) {
            this.blockSize = blockSize;
            this.freeBlocks = new ConcurrentLinkedQueue();
            this.blockLimit = 0x4000000 / blockSize;
        }

        @Override
        public Block allocateBlock() {
            Block block = this.freeBlocks.poll();
            if (block == null) {
                block = new Block(new byte[this.blockSize]){

                    @Override
                    public void close() {
                        if (size.getAndIncrement() < blockLimit) {
                            this.reset();
                            freeBlocks.add(this);
                        } else {
                            size.decrementAndGet();
                        }
                    }
                };
            } else {
                this.size.decrementAndGet();
            }
            return block;
        }

        @Override
        public int getBlockSize() {
            return this.blockSize;
        }

        @Override
        public void close() {
        }
    }
}

