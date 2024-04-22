/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazon.ion;

import com.amazon.ion.Span;
import com.amazon.ion.SpanProvider;

public interface SeekableReader
extends SpanProvider {
    public void hoist(Span var1);
}

