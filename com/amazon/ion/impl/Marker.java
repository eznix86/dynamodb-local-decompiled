/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazon.ion.impl;

import com.amazon.ion.impl.IonTypeID;

class Marker {
    IonTypeID typeId = null;
    long startIndex;
    long endIndex;

    Marker(int startIndex, int length) {
        this.startIndex = startIndex;
        this.endIndex = startIndex + length;
    }

    public String toString() {
        return String.format("%s[%d:%d]", this.typeId, this.startIndex, this.endIndex);
    }
}

