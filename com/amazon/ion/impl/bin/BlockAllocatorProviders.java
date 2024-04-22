/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazon.ion.impl.bin;

import com.amazon.ion.impl.bin.Block;
import com.amazon.ion.impl.bin.BlockAllocator;
import com.amazon.ion.impl.bin.BlockAllocatorProvider;

final class BlockAllocatorProviders {
    private static final BlockAllocatorProvider BASIC_PROVIDER = new BasicBlockAllocatorProvider();

    private BlockAllocatorProviders() {
    }

    public static BlockAllocatorProvider basicProvider() {
        return BASIC_PROVIDER;
    }

    private static final class BasicBlockAllocatorProvider
    extends BlockAllocatorProvider {
        private BasicBlockAllocatorProvider() {
        }

        @Override
        public BlockAllocator vendAllocator(final int blockSize) {
            return new BlockAllocator(){

                @Override
                public Block allocateBlock() {
                    return new Block(new byte[blockSize]){

                        @Override
                        public void close() {
                        }
                    };
                }

                @Override
                public int getBlockSize() {
                    return blockSize;
                }

                @Override
                public void close() {
                }
            };
        }
    }
}

