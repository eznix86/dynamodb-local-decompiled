/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazon.ion.util;

import com.amazon.ion.Span;
import com.amazon.ion.SpanProvider;
import com.amazon.ion.facet.Facets;

public final class Spans {
    public static Span currentSpan(Object spanProvider) {
        SpanProvider sp = Facets.asFacet(SpanProvider.class, spanProvider);
        Span span = sp == null ? null : sp.currentSpan();
        return span;
    }

    public static <T> T currentSpan(Class<T> spanFacetType, Object spanProvider) {
        Span span = Spans.currentSpan(spanProvider);
        T spanFacet = Facets.asFacet(spanFacetType, span);
        return spanFacet;
    }
}

