/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazon.ion.impl;

import com.amazon.ion.impl.AppendableFastAppendable;
import com.amazon.ion.impl.OutputStreamFastAppendable;
import com.amazon.ion.util._Private_FastAppendable;
import java.io.OutputStream;

public final class _Private_FastAppendableTrampoline {
    public static _Private_FastAppendable forAppendable(Appendable appendable) {
        return new AppendableFastAppendable(appendable);
    }

    public static _Private_FastAppendable forOutputStream(OutputStream outputStream) {
        return new OutputStreamFastAppendable(outputStream);
    }
}

