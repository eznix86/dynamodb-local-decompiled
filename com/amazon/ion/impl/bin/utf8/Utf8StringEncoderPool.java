/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazon.ion.impl.bin.utf8;

import com.amazon.ion.impl.bin.utf8.Pool;
import com.amazon.ion.impl.bin.utf8.Utf8StringEncoder;

public class Utf8StringEncoderPool
extends Pool<Utf8StringEncoder> {
    private static final Utf8StringEncoderPool INSTANCE = new Utf8StringEncoderPool();

    private Utf8StringEncoderPool() {
        super(new Pool.Allocator<Utf8StringEncoder>(){

            @Override
            public Utf8StringEncoder newInstance(Pool<Utf8StringEncoder> pool) {
                return new Utf8StringEncoder(pool);
            }
        });
    }

    public static Utf8StringEncoderPool getInstance() {
        return INSTANCE;
    }
}

