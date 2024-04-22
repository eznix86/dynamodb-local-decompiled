/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazon.ion.system;

import com.amazon.ion.IonCatalog;
import com.amazon.ion.SymbolTable;
import com.amazon.ion.impl._Private_IonBinaryWriterBuilder;
import com.amazon.ion.system.IonWriterBuilder;
import com.amazon.ion.system.IonWriterBuilderBase;

public abstract class IonBinaryWriterBuilder
extends IonWriterBuilderBase<IonBinaryWriterBuilder> {
    private boolean myStreamCopyOptimized;

    protected IonBinaryWriterBuilder() {
    }

    protected IonBinaryWriterBuilder(IonBinaryWriterBuilder that) {
        super(that);
        this.myStreamCopyOptimized = that.myStreamCopyOptimized;
    }

    public static IonBinaryWriterBuilder standard() {
        return _Private_IonBinaryWriterBuilder.standard();
    }

    @Override
    public abstract IonBinaryWriterBuilder copy();

    @Override
    public abstract IonBinaryWriterBuilder immutable();

    @Override
    public abstract IonBinaryWriterBuilder mutable();

    @Override
    public final IonBinaryWriterBuilder withCatalog(IonCatalog catalog) {
        return (IonBinaryWriterBuilder)super.withCatalog(catalog);
    }

    @Override
    public final IonBinaryWriterBuilder withImports(SymbolTable ... imports) {
        return (IonBinaryWriterBuilder)super.withImports(imports);
    }

    @Override
    public IonWriterBuilder.InitialIvmHandling getInitialIvmHandling() {
        return IonWriterBuilder.InitialIvmHandling.ENSURE;
    }

    @Override
    public IonWriterBuilder.IvmMinimizing getIvmMinimizing() {
        return null;
    }

    public abstract SymbolTable getInitialSymbolTable();

    public abstract void setInitialSymbolTable(SymbolTable var1);

    public abstract IonBinaryWriterBuilder withInitialSymbolTable(SymbolTable var1);

    public abstract void setLocalSymbolTableAppendEnabled(boolean var1);

    public abstract IonBinaryWriterBuilder withLocalSymbolTableAppendEnabled();

    public abstract IonBinaryWriterBuilder withLocalSymbolTableAppendDisabled();

    public abstract void setIsFloatBinary32Enabled(boolean var1);

    public abstract IonBinaryWriterBuilder withFloatBinary32Enabled();

    public abstract IonBinaryWriterBuilder withFloatBinary32Disabled();

    public boolean isStreamCopyOptimized() {
        return this.myStreamCopyOptimized;
    }

    public void setStreamCopyOptimized(boolean optimized) {
        this.mutationCheck();
        this.myStreamCopyOptimized = optimized;
    }

    public final IonBinaryWriterBuilder withStreamCopyOptimized(boolean optimized) {
        IonBinaryWriterBuilder b = this.mutable();
        b.setStreamCopyOptimized(optimized);
        return b;
    }
}

