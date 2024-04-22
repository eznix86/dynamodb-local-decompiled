/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazon.ion.impl.bin.utf8;

import com.amazon.ion.impl.bin.utf8.Pool;
import com.amazon.ion.impl.bin.utf8.Utf8StringDecoder;

public class Utf8StringDecoderPool
extends Pool<Utf8StringDecoder> {
    private static final Utf8StringDecoderPool INSTANCE = new Utf8StringDecoderPool();

    private Utf8StringDecoderPool() {
        super(new Pool.Allocator<Utf8StringDecoder>(){

            @Override
            public Utf8StringDecoder newInstance(Pool<Utf8StringDecoder> pool) {
                return new Utf8StringDecoder(pool);
            }
        });
    }

    public static Utf8StringDecoderPool getInstance() {
        return INSTANCE;
    }
}

