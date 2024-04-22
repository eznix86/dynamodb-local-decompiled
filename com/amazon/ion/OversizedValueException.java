/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazon.ion;

import com.amazon.ion.IonException;

public class OversizedValueException
extends IonException {
    public OversizedValueException() {
        super("Attempted to parse a scalar value that exceeded the reader's maximum buffer size.");
    }
}

