/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazon.ion.impl;

import com.amazon.ion.IonBinaryWriter;
import com.amazon.ion.IonCatalog;
import com.amazon.ion.IonException;
import com.amazon.ion.IonSystem;
import com.amazon.ion.IonWriter;
import com.amazon.ion.SubstituteSymbolTableException;
import com.amazon.ion.SymbolTable;
import com.amazon.ion.ValueFactory;
import com.amazon.ion.impl.BlockedBuffer;
import com.amazon.ion.impl.IonWriterSystemBinary;
import com.amazon.ion.impl.LocalSymbolTable;
import com.amazon.ion.impl._Private_IonBinaryWriterImpl;
import com.amazon.ion.impl._Private_LocalSymbolTable;
import com.amazon.ion.impl._Private_Utils;
import com.amazon.ion.impl.bin._Private_IonManagedBinaryWriterBuilder;
import com.amazon.ion.system.IonBinaryWriterBuilder;
import com.amazon.ion.system.IonSystemBuilder;
import java.io.IOException;
import java.io.OutputStream;

public class _Private_IonBinaryWriterBuilder
extends IonBinaryWriterBuilder {
    private final _Private_IonManagedBinaryWriterBuilder myBinaryWriterBuilder;
    private ValueFactory mySymtabValueFactory;
    private SymbolTable myInitialSymbolTable;

    private _Private_IonBinaryWriterBuilder() {
        this.myBinaryWriterBuilder = _Private_IonManagedBinaryWriterBuilder.create(_Private_IonManagedBinaryWriterBuilder.AllocatorMode.POOLED).withPaddedLengthPreallocation(1);
    }

    private _Private_IonBinaryWriterBuilder(_Private_IonBinaryWriterBuilder that) {
        super(that);
        this.mySymtabValueFactory = that.mySymtabValueFactory;
        this.myInitialSymbolTable = that.myInitialSymbolTable;
        this.myBinaryWriterBuilder = that.myBinaryWriterBuilder.copy();
    }

    public static _Private_IonBinaryWriterBuilder standard() {
        return new Mutable();
    }

    @Override
    public final _Private_IonBinaryWriterBuilder copy() {
        return new Mutable(this);
    }

    @Override
    public _Private_IonBinaryWriterBuilder immutable() {
        return this;
    }

    @Override
    public _Private_IonBinaryWriterBuilder mutable() {
        return this.copy();
    }

    public ValueFactory getSymtabValueFactory() {
        return this.mySymtabValueFactory;
    }

    public void setSymtabValueFactory(ValueFactory factory) {
        this.mutationCheck();
        this.mySymtabValueFactory = factory;
    }

    public _Private_IonBinaryWriterBuilder withSymtabValueFactory(ValueFactory factory) {
        _Private_IonBinaryWriterBuilder b = this.mutable();
        b.setSymtabValueFactory(factory);
        return b;
    }

    @Override
    public SymbolTable getInitialSymbolTable() {
        return this.myInitialSymbolTable;
    }

    @Override
    public void setInitialSymbolTable(SymbolTable symtab) {
        this.mutationCheck();
        if (symtab != null) {
            if (symtab.isLocalTable()) {
                SymbolTable[] imports;
                for (SymbolTable imported : imports = ((_Private_LocalSymbolTable)symtab).getImportedTablesNoCopy()) {
                    if (!imported.isSubstitute()) continue;
                    String message = "Cannot encode with substitute symbol table: " + imported.getName();
                    throw new SubstituteSymbolTableException(message);
                }
            } else if (!symtab.isSystemTable()) {
                String message = "symtab must be local or system table";
                throw new IllegalArgumentException(message);
            }
        }
        this.myInitialSymbolTable = symtab;
        this.myBinaryWriterBuilder.withInitialSymbolTable(symtab);
    }

    @Override
    public _Private_IonBinaryWriterBuilder withInitialSymbolTable(SymbolTable symtab) {
        _Private_IonBinaryWriterBuilder b = this.mutable();
        b.setInitialSymbolTable(symtab);
        return b;
    }

    @Override
    public void setLocalSymbolTableAppendEnabled(boolean enabled) {
        this.mutationCheck();
        if (enabled) {
            this.myBinaryWriterBuilder.withLocalSymbolTableAppendEnabled();
        } else {
            this.myBinaryWriterBuilder.withLocalSymbolTableAppendDisabled();
        }
    }

    @Override
    public _Private_IonBinaryWriterBuilder withLocalSymbolTableAppendEnabled() {
        _Private_IonBinaryWriterBuilder b = this.mutable();
        b.setLocalSymbolTableAppendEnabled(true);
        return b;
    }

    @Override
    public _Private_IonBinaryWriterBuilder withLocalSymbolTableAppendDisabled() {
        _Private_IonBinaryWriterBuilder b = this.mutable();
        b.setLocalSymbolTableAppendEnabled(false);
        return b;
    }

    @Override
    public void setIsFloatBinary32Enabled(boolean enabled) {
        this.mutationCheck();
        if (enabled) {
            this.myBinaryWriterBuilder.withFloatBinary32Enabled();
        } else {
            this.myBinaryWriterBuilder.withFloatBinary32Disabled();
        }
    }

    @Override
    public _Private_IonBinaryWriterBuilder withFloatBinary32Enabled() {
        _Private_IonBinaryWriterBuilder b = this.mutable();
        b.setIsFloatBinary32Enabled(true);
        return b;
    }

    @Override
    public _Private_IonBinaryWriterBuilder withFloatBinary32Disabled() {
        _Private_IonBinaryWriterBuilder b = this.mutable();
        b.setIsFloatBinary32Enabled(false);
        return b;
    }

    @Override
    public void setImports(SymbolTable ... imports) {
        super.setImports(imports);
        this.myBinaryWriterBuilder.withImports(imports);
    }

    @Override
    public void setCatalog(IonCatalog catalog) {
        super.setCatalog(catalog);
        this.myBinaryWriterBuilder.withCatalog(catalog);
    }

    @Override
    public void setStreamCopyOptimized(boolean optimized) {
        super.setStreamCopyOptimized(optimized);
        this.myBinaryWriterBuilder.withStreamCopyOptimization(optimized);
    }

    private _Private_IonBinaryWriterBuilder fillDefaults() {
        _Private_IonBinaryWriterBuilder b = this.copy();
        if (b.getSymtabValueFactory() == null) {
            IonSystem system = IonSystemBuilder.standard().build();
            b.setSymtabValueFactory(system);
        }
        return b.immutable();
    }

    private _Private_IonBinaryWriterBuilder fillLegacyDefaults() {
        SymbolTable initialSymtab;
        _Private_IonBinaryWriterBuilder b = this.copy();
        if (b.getSymtabValueFactory() == null) {
            IonSystem system = IonSystemBuilder.standard().build();
            b.setSymtabValueFactory(system);
        }
        if ((initialSymtab = b.getInitialSymbolTable()) == null) {
            initialSymtab = _Private_Utils.initialSymtab(LocalSymbolTable.DEFAULT_LST_FACTORY, _Private_Utils.systemSymtab(1), b.getImports());
            b.setInitialSymbolTable(initialSymtab);
        } else if (initialSymtab.isSystemTable()) {
            initialSymtab = _Private_Utils.initialSymtab(LocalSymbolTable.DEFAULT_LST_FACTORY, initialSymtab, b.getImports());
            b.setInitialSymbolTable(initialSymtab);
        }
        return b.immutable();
    }

    private IonWriterSystemBinary buildSystemWriter(OutputStream out) {
        SymbolTable defaultSystemSymtab = this.myInitialSymbolTable.getSystemSymbolTable();
        return new IonWriterSystemBinary(defaultSystemSymtab, out, false, true);
    }

    SymbolTable buildContextSymbolTable() {
        if (this.myInitialSymbolTable.isReadOnly()) {
            return this.myInitialSymbolTable;
        }
        return ((_Private_LocalSymbolTable)this.myInitialSymbolTable).makeCopy();
    }

    @Override
    public final IonWriter build(OutputStream out) {
        _Private_IonBinaryWriterBuilder b = this.fillDefaults();
        try {
            return b.myBinaryWriterBuilder.newWriter(out);
        } catch (IOException e) {
            throw new IonException("I/O Error", e);
        }
    }

    @Deprecated
    public final IonBinaryWriter buildLegacy() {
        _Private_IonBinaryWriterBuilder b = this.fillLegacyDefaults();
        IonWriterSystemBinary systemWriter = b.buildSystemWriter(new BlockedBuffer.BufferedOutputStream());
        return new _Private_IonBinaryWriterImpl(b, systemWriter);
    }

    private static final class Mutable
    extends _Private_IonBinaryWriterBuilder {
        private Mutable() {
        }

        private Mutable(_Private_IonBinaryWriterBuilder that) {
            super(that);
        }

        @Override
        public _Private_IonBinaryWriterBuilder immutable() {
            return new _Private_IonBinaryWriterBuilder(this);
        }

        @Override
        public _Private_IonBinaryWriterBuilder mutable() {
            return this;
        }

        @Override
        protected void mutationCheck() {
        }
    }
}

