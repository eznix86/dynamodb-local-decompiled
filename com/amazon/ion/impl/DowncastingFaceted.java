/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazon.ion.impl;

import com.amazon.ion.facet.Faceted;

abstract class DowncastingFaceted
implements Faceted {
    DowncastingFaceted() {
    }

    @Override
    public final <T> T asFacet(Class<T> type) {
        if (!type.isInstance(this)) {
            return null;
        }
        return type.cast(this);
    }
}

