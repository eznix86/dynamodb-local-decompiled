/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazon.ion.impl;

import com.amazon.ion.IonCatalog;
import com.amazon.ion.IonWriter;
import java.io.IOException;

public interface _Private_IonWriter
extends IonWriter {
    public IonCatalog getCatalog();

    public boolean isFieldNameSet();

    public int getDepth();

    public void writeIonVersionMarker() throws IOException;

    public boolean isStreamCopyOptimized();
}

