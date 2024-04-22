/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazon.ion;

import com.amazon.ion.Span;

@Deprecated
public interface RawValueSpanProvider {
    public Span valueSpan();

    public byte[] buffer();
}

