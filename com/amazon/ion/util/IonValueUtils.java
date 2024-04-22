/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazon.ion.util;

import com.amazon.ion.IonValue;

public class IonValueUtils {
    public static final boolean anyNull(IonValue value) {
        return value == null || value.isNullValue();
    }
}

