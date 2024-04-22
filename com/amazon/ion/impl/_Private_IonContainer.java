/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazon.ion.impl;

import com.amazon.ion.IonContainer;
import com.amazon.ion.IonValue;

public interface _Private_IonContainer
extends IonContainer {
    public int get_child_count();

    public IonValue get_child(int var1);
}

