/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazon.ion.system;

import com.amazon.ion.IonCatalog;
import com.amazon.ion.IonSystem;
import com.amazon.ion.system.IonSystemBuilder;

@Deprecated
public final class SystemFactory {
    @Deprecated
    public static IonSystem newSystem() {
        return IonSystemBuilder.standard().build();
    }

    @Deprecated
    public static IonSystem newSystem(IonCatalog catalog) {
        return IonSystemBuilder.standard().withCatalog(catalog).build();
    }
}

