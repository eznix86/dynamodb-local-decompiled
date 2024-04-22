/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazon.ion.system;

import com.amazon.ion.IonCatalog;
import com.amazon.ion.IonSystem;
import com.amazon.ion.SymbolTable;
import com.amazon.ion.impl._Private_IonBinaryWriterBuilder;
import com.amazon.ion.impl._Private_Utils;
import com.amazon.ion.impl.lite._Private_LiteDomTrampoline;
import com.amazon.ion.system.IonReaderBuilder;
import com.amazon.ion.system.IonTextWriterBuilder;
import com.amazon.ion.system.SimpleCatalog;

public class IonSystemBuilder {
    private static final IonSystemBuilder STANDARD = new IonSystemBuilder();
    IonCatalog myCatalog;
    boolean myStreamCopyOptimized = false;
    IonReaderBuilder readerBuilder;

    public static IonSystemBuilder standard() {
        return STANDARD;
    }

    private IonSystemBuilder() {
    }

    private IonSystemBuilder(IonSystemBuilder that) {
        this.myCatalog = that.myCatalog;
        this.myStreamCopyOptimized = that.myStreamCopyOptimized;
        this.readerBuilder = that.readerBuilder;
    }

    public final IonSystemBuilder copy() {
        return new Mutable(this);
    }

    public IonSystemBuilder immutable() {
        return this;
    }

    public IonSystemBuilder mutable() {
        return this.copy();
    }

    void mutationCheck() {
        throw new UnsupportedOperationException("This builder is immutable");
    }

    public final IonCatalog getCatalog() {
        return this.myCatalog;
    }

    public final void setCatalog(IonCatalog catalog) {
        this.mutationCheck();
        this.myCatalog = catalog;
    }

    public final IonSystemBuilder withCatalog(IonCatalog catalog) {
        IonSystemBuilder b = this.mutable();
        b.setCatalog(catalog);
        return b;
    }

    public final boolean isStreamCopyOptimized() {
        return this.myStreamCopyOptimized;
    }

    public final void setStreamCopyOptimized(boolean optimized) {
        this.mutationCheck();
        this.myStreamCopyOptimized = optimized;
    }

    public final IonSystemBuilder withStreamCopyOptimized(boolean optimized) {
        IonSystemBuilder b = this.mutable();
        b.setStreamCopyOptimized(optimized);
        return b;
    }

    public final IonReaderBuilder getReaderBuilder() {
        return this.readerBuilder;
    }

    public final void setReaderBuilder(IonReaderBuilder builder) {
        this.mutationCheck();
        this.readerBuilder = builder;
    }

    public final IonSystemBuilder withReaderBuilder(IonReaderBuilder builder) {
        IonSystemBuilder b = this.mutable();
        b.setReaderBuilder(builder);
        return b;
    }

    public final IonSystem build() {
        IonCatalog catalog = this.myCatalog != null ? this.myCatalog : new SimpleCatalog();
        IonTextWriterBuilder twb = IonTextWriterBuilder.standard().withCharsetAscii();
        twb.setCatalog(catalog);
        _Private_IonBinaryWriterBuilder bwb = _Private_IonBinaryWriterBuilder.standard();
        bwb.setCatalog(catalog);
        bwb.setStreamCopyOptimized(this.myStreamCopyOptimized);
        SymbolTable systemSymtab = _Private_Utils.systemSymtab(1);
        bwb.setInitialSymbolTable(systemSymtab);
        IonReaderBuilder rb = this.readerBuilder == null ? IonReaderBuilder.standard() : this.readerBuilder;
        rb = rb.withCatalog(catalog);
        return _Private_LiteDomTrampoline.newLiteSystem(twb, bwb, rb);
    }

    private static final class Mutable
    extends IonSystemBuilder {
        private Mutable(IonSystemBuilder that) {
            super(that);
        }

        @Override
        public IonSystemBuilder immutable() {
            return new IonSystemBuilder(this);
        }

        @Override
        public IonSystemBuilder mutable() {
            return this;
        }

        @Override
        void mutationCheck() {
        }
    }
}

