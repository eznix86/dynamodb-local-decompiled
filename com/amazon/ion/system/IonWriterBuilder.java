/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazon.ion.system;

import com.amazon.ion.IonWriter;
import java.io.OutputStream;

public abstract class IonWriterBuilder {
    IonWriterBuilder() {
    }

    public abstract InitialIvmHandling getInitialIvmHandling();

    public abstract IvmMinimizing getIvmMinimizing();

    public abstract IonWriter build(OutputStream var1);

    public static enum IvmMinimizing {
        ADJACENT,
        DISTANT;

    }

    public static enum InitialIvmHandling {
        ENSURE,
        SUPPRESS;

    }
}

