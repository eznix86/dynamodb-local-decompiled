/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazon.ion;

import java.io.Closeable;

public interface IonCursor
extends Closeable {
    public Event nextValue();

    public Event stepIntoContainer();

    public Event stepOutOfContainer();

    public Event fillValue();

    public Event getCurrentEvent();

    public Event endStream();

    public static enum Event {
        NEEDS_DATA,
        NEEDS_INSTRUCTION,
        START_SCALAR,
        VALUE_READY,
        START_CONTAINER,
        END_CONTAINER;

    }
}

