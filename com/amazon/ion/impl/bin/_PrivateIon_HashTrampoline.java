/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazon.ion.impl.bin;

import com.amazon.ion.IonWriter;
import com.amazon.ion.impl.bin.AbstractIonWriter;
import com.amazon.ion.impl.bin.IonRawBinaryWriter;
import com.amazon.ion.impl.bin.PooledBlockAllocatorProvider;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Deprecated
public class _PrivateIon_HashTrampoline {
    private static final PooledBlockAllocatorProvider ALLOCATOR_PROVIDER = PooledBlockAllocatorProvider.getInstance();

    public static IonWriter newIonWriter(ByteArrayOutputStream baos) throws IOException {
        return new IonRawBinaryWriter(ALLOCATOR_PROVIDER, 32768, baos, AbstractIonWriter.WriteValueOptimization.NONE, IonRawBinaryWriter.StreamCloseMode.CLOSE, IonRawBinaryWriter.StreamFlushMode.FLUSH, IonRawBinaryWriter.PreallocationMode.PREALLOCATE_0, false);
    }
}

