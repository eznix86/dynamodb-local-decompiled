/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazon.ion.impl.bin;

import com.amazon.ion.IonBinaryWriter;
import com.amazon.ion.IonCatalog;
import com.amazon.ion.IonException;
import com.amazon.ion.IonWriter;
import com.amazon.ion.SubstituteSymbolTableException;
import com.amazon.ion.SymbolTable;
import com.amazon.ion.impl.bin.AbstractIonWriter;
import com.amazon.ion.impl.bin.BlockAllocatorProvider;
import com.amazon.ion.impl.bin.BlockAllocatorProviders;
import com.amazon.ion.impl.bin.IonBinaryWriterAdapter;
import com.amazon.ion.impl.bin.IonManagedBinaryWriter;
import com.amazon.ion.impl.bin.IonRawBinaryWriter;
import com.amazon.ion.impl.bin.PooledBlockAllocatorProvider;
import com.amazon.ion.system.SimpleCatalog;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;

public final class _Private_IonManagedBinaryWriterBuilder {
    public static final int DEFAULT_BLOCK_SIZE = 32768;
    final BlockAllocatorProvider provider;
    volatile int symbolsBlockSize;
    volatile int userBlockSize;
    volatile IonRawBinaryWriter.PreallocationMode preallocationMode;
    volatile IonManagedBinaryWriter.ImportedSymbolContext imports;
    volatile IonCatalog catalog;
    volatile AbstractIonWriter.WriteValueOptimization optimization;
    volatile SymbolTable initialSymbolTable;
    volatile boolean isLocalSymbolTableAppendEnabled;
    volatile boolean isFloatBinary32Enabled;

    private _Private_IonManagedBinaryWriterBuilder(BlockAllocatorProvider provider) {
        this.provider = provider;
        this.symbolsBlockSize = 32768;
        this.userBlockSize = 32768;
        this.imports = IonManagedBinaryWriter.ONLY_SYSTEM_IMPORTS;
        this.preallocationMode = IonRawBinaryWriter.PreallocationMode.PREALLOCATE_2;
        this.catalog = new SimpleCatalog();
        this.optimization = AbstractIonWriter.WriteValueOptimization.NONE;
        this.isLocalSymbolTableAppendEnabled = false;
        this.isFloatBinary32Enabled = false;
    }

    private _Private_IonManagedBinaryWriterBuilder(_Private_IonManagedBinaryWriterBuilder other) {
        this.provider = other.provider;
        this.symbolsBlockSize = other.symbolsBlockSize;
        this.userBlockSize = other.userBlockSize;
        this.preallocationMode = other.preallocationMode;
        this.imports = other.imports;
        this.catalog = other.catalog;
        this.optimization = other.optimization;
        this.initialSymbolTable = other.initialSymbolTable;
        this.isLocalSymbolTableAppendEnabled = other.isLocalSymbolTableAppendEnabled;
        this.isFloatBinary32Enabled = other.isFloatBinary32Enabled;
    }

    public _Private_IonManagedBinaryWriterBuilder copy() {
        return new _Private_IonManagedBinaryWriterBuilder(this);
    }

    public _Private_IonManagedBinaryWriterBuilder withSymbolsBlockSize(int blockSize) {
        if (blockSize < 1) {
            throw new IllegalArgumentException("Block size cannot be less than 1: " + blockSize);
        }
        this.symbolsBlockSize = blockSize;
        return this;
    }

    public _Private_IonManagedBinaryWriterBuilder withUserBlockSize(int blockSize) {
        if (blockSize < 1) {
            throw new IllegalArgumentException("Block size cannot be less than 1: " + blockSize);
        }
        this.userBlockSize = blockSize;
        return this;
    }

    public _Private_IonManagedBinaryWriterBuilder withImports(SymbolTable ... tables) {
        if (tables != null) {
            return this.withImports(Arrays.asList(tables));
        }
        return this;
    }

    public _Private_IonManagedBinaryWriterBuilder withImports(List<SymbolTable> tables) {
        return this.withImports(IonManagedBinaryWriter.ImportedSymbolResolverMode.DELEGATE, tables);
    }

    public _Private_IonManagedBinaryWriterBuilder withFlatImports(SymbolTable ... tables) {
        if (tables != null) {
            return this.withFlatImports(Arrays.asList(tables));
        }
        return this;
    }

    public _Private_IonManagedBinaryWriterBuilder withFlatImports(List<SymbolTable> tables) {
        return this.withImports(IonManagedBinaryWriter.ImportedSymbolResolverMode.FLAT, tables);
    }

    _Private_IonManagedBinaryWriterBuilder withImports(IonManagedBinaryWriter.ImportedSymbolResolverMode mode, List<SymbolTable> tables) {
        this.imports = new IonManagedBinaryWriter.ImportedSymbolContext(mode, tables);
        return this;
    }

    _Private_IonManagedBinaryWriterBuilder withPreallocationMode(IonRawBinaryWriter.PreallocationMode preallocationMode) {
        this.preallocationMode = preallocationMode;
        return this;
    }

    public _Private_IonManagedBinaryWriterBuilder withPaddedLengthPreallocation(int pad) {
        this.preallocationMode = IonRawBinaryWriter.PreallocationMode.withPadSize(pad);
        return this;
    }

    public _Private_IonManagedBinaryWriterBuilder withCatalog(IonCatalog catalog) {
        this.catalog = catalog;
        return this;
    }

    public _Private_IonManagedBinaryWriterBuilder withStreamCopyOptimization(boolean optimized) {
        this.optimization = optimized ? AbstractIonWriter.WriteValueOptimization.COPY_OPTIMIZED : AbstractIonWriter.WriteValueOptimization.NONE;
        return this;
    }

    public _Private_IonManagedBinaryWriterBuilder withLocalSymbolTableAppendEnabled() {
        this.isLocalSymbolTableAppendEnabled = true;
        return this;
    }

    public _Private_IonManagedBinaryWriterBuilder withLocalSymbolTableAppendDisabled() {
        this.isLocalSymbolTableAppendEnabled = false;
        return this;
    }

    public _Private_IonManagedBinaryWriterBuilder withFloatBinary32Enabled() {
        this.isFloatBinary32Enabled = true;
        return this;
    }

    public _Private_IonManagedBinaryWriterBuilder withFloatBinary32Disabled() {
        this.isFloatBinary32Enabled = false;
        return this;
    }

    public _Private_IonManagedBinaryWriterBuilder withInitialSymbolTable(SymbolTable symbolTable) {
        if (symbolTable != null) {
            if (!symbolTable.isLocalTable() && !symbolTable.isSystemTable()) {
                throw new IllegalArgumentException("Initial symbol table must be local or system");
            }
            if (symbolTable.isSystemTable()) {
                if (symbolTable.getMaxId() != 9) {
                    throw new IllegalArgumentException("Unsupported system symbol table");
                }
                symbolTable = null;
            } else {
                for (SymbolTable st : symbolTable.getImportedTables()) {
                    if (!st.isSubstitute()) continue;
                    throw new SubstituteSymbolTableException("Cannot use initial symbol table with imported substitutes");
                }
            }
        }
        this.initialSymbolTable = symbolTable;
        return this;
    }

    public IonWriter newWriter(OutputStream out) throws IOException {
        return new IonManagedBinaryWriter(this, out);
    }

    public IonBinaryWriter newLegacyWriter() {
        try {
            return new IonBinaryWriterAdapter(new IonBinaryWriterAdapter.Factory(){

                @Override
                public IonWriter create(OutputStream out) throws IOException {
                    return _Private_IonManagedBinaryWriterBuilder.this.newWriter(out);
                }
            });
        } catch (IOException e) {
            throw new IonException("I/O error", e);
        }
    }

    public static _Private_IonManagedBinaryWriterBuilder create(AllocatorMode allocatorMode) {
        return new _Private_IonManagedBinaryWriterBuilder(allocatorMode.createAllocatorProvider());
    }

    public static enum AllocatorMode {
        POOLED{

            @Override
            BlockAllocatorProvider createAllocatorProvider() {
                return PooledBlockAllocatorProvider.getInstance();
            }
        }
        ,
        BASIC{

            @Override
            BlockAllocatorProvider createAllocatorProvider() {
                return BlockAllocatorProviders.basicProvider();
            }
        };


        abstract BlockAllocatorProvider createAllocatorProvider();
    }
}

