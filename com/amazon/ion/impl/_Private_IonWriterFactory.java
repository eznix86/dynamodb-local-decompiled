/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazon.ion.impl;

import com.amazon.ion.IonCatalog;
import com.amazon.ion.IonContainer;
import com.amazon.ion.IonSystem;
import com.amazon.ion.IonWriter;
import com.amazon.ion.SymbolTable;
import com.amazon.ion.impl.IonWriterSystemTree;
import com.amazon.ion.impl.IonWriterUser;
import com.amazon.ion.system.IonWriterBuilder;

public final class _Private_IonWriterFactory {
    public static IonWriter makeWriter(IonContainer container) {
        IonSystem sys = container.getSystem();
        IonCatalog cat = sys.getCatalog();
        IonWriter writer = _Private_IonWriterFactory.makeWriter(cat, container);
        return writer;
    }

    public static IonWriter makeWriter(IonCatalog catalog, IonContainer container) {
        IonSystem sys = container.getSystem();
        SymbolTable defaultSystemSymtab = sys.getSystemSymbolTable();
        IonWriterSystemTree system_writer = new IonWriterSystemTree(defaultSystemSymtab, catalog, container, IonWriterBuilder.InitialIvmHandling.SUPPRESS);
        return new IonWriterUser(catalog, sys, system_writer, true);
    }

    public static IonWriter makeSystemWriter(IonContainer container) {
        IonSystem sys = container.getSystem();
        IonCatalog cat = sys.getCatalog();
        SymbolTable defaultSystemSymtab = sys.getSystemSymbolTable();
        IonWriterSystemTree writer = new IonWriterSystemTree(defaultSystemSymtab, cat, container, null);
        return writer;
    }
}

