/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazon.ion.impl.lite;

import com.amazon.ion.IonSystem;
import com.amazon.ion.SymbolTable;
import com.amazon.ion.impl._Private_IonBinaryWriterBuilder;
import com.amazon.ion.impl.lite.IonSystemLite;
import com.amazon.ion.impl.lite.ReverseBinaryEncoder;
import com.amazon.ion.system.IonReaderBuilder;
import com.amazon.ion.system.IonTextWriterBuilder;

public final class _Private_LiteDomTrampoline {
    public static IonSystem newLiteSystem(IonTextWriterBuilder twb, _Private_IonBinaryWriterBuilder bwb, IonReaderBuilder rb) {
        return new IonSystemLite(twb, bwb, rb);
    }

    public static boolean isLiteSystem(IonSystem system) {
        return system instanceof IonSystemLite;
    }

    public static byte[] reverseEncode(int initialSize, SymbolTable symtab) {
        ReverseBinaryEncoder encoder = new ReverseBinaryEncoder(initialSize);
        encoder.serialize(symtab);
        return encoder.toNewByteArray();
    }
}

