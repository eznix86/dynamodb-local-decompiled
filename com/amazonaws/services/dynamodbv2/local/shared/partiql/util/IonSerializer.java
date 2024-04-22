/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazonaws.services.dynamodbv2.local.shared.partiql.util;

import com.amazon.ion.IonSystem;
import com.amazon.ion.system.IonSystemBuilder;

public final class IonSerializer {
    public static final IonSystem ION_SYSTEM = IonSystemBuilder.standard().build();

    private IonSerializer() {
    }
}

