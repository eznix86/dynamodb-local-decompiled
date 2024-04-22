/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazon.ion.impl;

import com.amazon.ion.IonCatalog;
import com.amazon.ion.IonCursor;
import com.amazon.ion.IonException;
import com.amazon.ion.IonStruct;
import com.amazon.ion.IonType;
import com.amazon.ion.IonWriter;
import com.amazon.ion.ReadOnlyValueException;
import com.amazon.ion.SymbolTable;
import com.amazon.ion.SymbolToken;
import com.amazon.ion.UnknownSymbolException;
import com.amazon.ion.ValueFactory;
import com.amazon.ion.impl.IonCursorBinary;
import com.amazon.ion.impl.IonReaderContinuableApplication;
import com.amazon.ion.impl.IonReaderContinuableCoreBinary;
import com.amazon.ion.impl.LocalSymbolTable;
import com.amazon.ion.impl.LocalSymbolTableImports;
import com.amazon.ion.impl.SharedSymbolTable;
import com.amazon.ion.impl.SubstituteSymbolTable;
import com.amazon.ion.impl.SymbolTableAsStruct;
import com.amazon.ion.impl.SymbolTableStructCache;
import com.amazon.ion.impl.SymbolTokenImpl;
import com.amazon.ion.impl._Private_LocalSymbolTable;
import com.amazon.ion.impl._Private_Utils;
import com.amazon.ion.impl.bin.IntList;
import com.amazon.ion.system.IonReaderBuilder;
import com.amazon.ion.system.SimpleCatalog;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

class IonReaderContinuableApplicationBinary
extends IonReaderContinuableCoreBinary
implements IonReaderContinuableApplication {
    private static final byte[] ION_SYMBOL_TABLE_UTF8 = "$ion_symbol_table".getBytes(StandardCharsets.UTF_8);
    private static final IonCatalog EMPTY_CATALOG = new SimpleCatalog();
    private static final int SYMBOLS_LIST_INITIAL_CAPACITY = 128;
    private static final LocalSymbolTableImports ION_1_0_IMPORTS = new LocalSymbolTableImports(SharedSymbolTable.getSystemSymbolTable(1), new SymbolTable[0]);
    private String[] symbols;
    private int localSymbolMaxOffset = -1;
    private final IonCatalog catalog;
    private final SymbolTableReader symbolTableReader;
    private LocalSymbolTableImports imports = ION_1_0_IMPORTS;
    private int firstLocalSymbolId = this.imports.getMaxId() + 1;
    private SymbolTable cachedReadOnlySymbolTable = null;
    private final AnnotationSequenceIterator annotationIterator = new AnnotationSequenceIterator();
    private State state = State.READING_VALUE;
    private static final Iterator<String> EMPTY_ITERATOR = new Iterator<String>(){

        @Override
        public boolean hasNext() {
            return false;
        }

        @Override
        public String next() {
            throw new NoSuchElementException();
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Cannot remove from an empty iterator.");
        }
    };

    IonReaderContinuableApplicationBinary(IonReaderBuilder builder, byte[] bytes, int offset, int length) {
        super(builder.getBufferConfiguration(), bytes, offset, length);
        this.catalog = builder.getCatalog() == null ? EMPTY_CATALOG : builder.getCatalog();
        this.symbols = new String[128];
        this.symbolTableReader = new SymbolTableReader();
        this.resetImports();
        this.registerIvmNotificationConsumer((x, y) -> {
            this.resetSymbolTable();
            this.resetImports();
        });
    }

    IonReaderContinuableApplicationBinary(IonReaderBuilder builder, InputStream inputStream, byte[] alreadyRead, int alreadyReadOff, int alreadyReadLen) {
        super(builder.getBufferConfiguration(), inputStream, alreadyRead, alreadyReadOff, alreadyReadLen);
        this.catalog = builder.getCatalog() == null ? EMPTY_CATALOG : builder.getCatalog();
        this.symbols = new String[128];
        this.symbolTableReader = new SymbolTableReader();
        this.resetImports();
        this.registerIvmNotificationConsumer((x, y) -> {
            this.resetSymbolTable();
            this.resetImports();
        });
        this.registerOversizedValueHandler(() -> {
            boolean mightBeSymbolTable = true;
            if (this.state == State.READING_VALUE) {
                if (this.parent != null || !this.hasAnnotations) {
                    mightBeSymbolTable = false;
                } else if (this.annotationSequenceMarker.startIndex >= 0L && this.annotationSequenceMarker.endIndex <= this.limit) {
                    IonType type;
                    mightBeSymbolTable = this.startsWithIonSymbolTable() ? (type = super.getType()) == null || type == IonType.STRUCT : false;
                }
            }
            if (mightBeSymbolTable) {
                builder.getBufferConfiguration().getOversizedSymbolTableHandler().onOversizedSymbolTable();
                this.terminate();
            } else {
                builder.getBufferConfiguration().getOversizedValueHandler().onOversizedValue();
            }
        });
    }

    private SymbolTable getSystemSymbolTable() {
        return SharedSymbolTable.getSystemSymbolTable(this.getIonMajorVersion());
    }

    private void resetSymbolTable() {
        Arrays.fill(this.symbols, 0, this.localSymbolMaxOffset + 1, null);
        this.localSymbolMaxOffset = -1;
        this.cachedReadOnlySymbolTable = null;
    }

    private void resetImports() {
        this.imports = ION_1_0_IMPORTS;
        this.firstLocalSymbolId = this.imports.getMaxId() + 1;
    }

    protected void restoreSymbolTable(SymbolTable symbolTable) {
        if (this.cachedReadOnlySymbolTable == symbolTable) {
            return;
        }
        if (symbolTable instanceof LocalSymbolTableSnapshot) {
            LocalSymbolTableSnapshot snapshot = (LocalSymbolTableSnapshot)symbolTable;
            this.cachedReadOnlySymbolTable = snapshot;
            this.imports = snapshot.importedTables;
            this.firstLocalSymbolId = this.imports.getMaxId() + 1;
            this.localSymbolMaxOffset = snapshot.maxId - this.firstLocalSymbolId;
            System.arraycopy(snapshot.idToText, 0, this.symbols, 0, snapshot.idToText.length);
        } else {
            this.resetSymbolTable();
            this.cachedReadOnlySymbolTable = symbolTable;
            this.resetImports();
            this.localSymbolMaxOffset = -1;
        }
    }

    private SymbolTable createImport(String name, int version, int maxId) {
        SymbolTable shared = this.catalog.getTable(name, version);
        if (maxId < 0) {
            if (shared == null || version != shared.getVersion()) {
                String message = "Import of shared table " + name + " lacks a valid max_id field, but an exact match was not found in the catalog";
                if (shared != null) {
                    message = message + " (found version " + shared.getVersion() + ")";
                }
                throw new IonException(message);
            }
            maxId = shared.getMaxId();
        }
        if (shared == null) {
            return new SubstituteSymbolTable(name, version, maxId);
        }
        if (shared.getMaxId() != maxId || shared.getVersion() != version) {
            return new SubstituteSymbolTable(shared, version, maxId);
        }
        return shared;
    }

    private String getSymbolString(int sid, LocalSymbolTableImports importedSymbols, String[] localSymbols) {
        if (sid <= importedSymbols.getMaxId()) {
            return importedSymbols.findKnownSymbol(sid);
        }
        return localSymbols[sid - (importedSymbols.getMaxId() + 1)];
    }

    String getSymbol(int sid) {
        if (sid < this.firstLocalSymbolId) {
            return this.imports.findKnownSymbol(sid);
        }
        int localSymbolOffset = sid - this.firstLocalSymbolId;
        if (localSymbolOffset > this.localSymbolMaxOffset) {
            throw new UnknownSymbolException(sid);
        }
        return this.symbols[localSymbolOffset];
    }

    private SymbolToken getSymbolToken(int sid) {
        int symbolTableSize = this.localSymbolMaxOffset + this.firstLocalSymbolId + 1;
        if (sid >= symbolTableSize) {
            throw new UnknownSymbolException(sid);
        }
        String text = this.getSymbolString(sid, this.imports, this.symbols);
        if (text == null && sid >= this.firstLocalSymbolId) {
            sid = 0;
        }
        return new SymbolTokenImpl(text, sid);
    }

    boolean startsWithIonSymbolTable() {
        long savedPeekIndex = this.peekIndex;
        this.peekIndex = this.annotationSequenceMarker.startIndex;
        int sid = this.minorVersion == 0 ? this.readVarUInt_1_0() : this.readVarUInt_1_1();
        this.peekIndex = savedPeekIndex;
        return 3 == sid;
    }

    private boolean isPositionedOnSymbolTable() {
        return this.hasAnnotations && super.getType() == IonType.STRUCT && this.startsWithIonSymbolTable();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public IonCursor.Event nextValue() {
        if (this.parent != null) {
            if (this.state == State.READING_VALUE) return super.nextValue();
        }
        while (true) {
            if (this.state != State.READING_VALUE) {
                this.symbolTableReader.readSymbolTable();
                if (this.state != State.READING_VALUE) {
                    return IonCursor.Event.NEEDS_DATA;
                }
            }
            IonCursor.Event event = super.nextValue();
            if (this.parent != null) return event;
            if (!this.isPositionedOnSymbolTable()) return event;
            this.cachedReadOnlySymbolTable = null;
            this.symbolTableReader.resetState();
            this.state = State.ON_SYMBOL_TABLE_STRUCT;
        }
    }

    @Override
    public SymbolTable getSymbolTable() {
        if (this.cachedReadOnlySymbolTable == null) {
            this.cachedReadOnlySymbolTable = this.localSymbolMaxOffset < 0 && this.imports == ION_1_0_IMPORTS ? this.imports.getSystemSymbolTable() : new LocalSymbolTableSnapshot();
        }
        return this.cachedReadOnlySymbolTable;
    }

    @Override
    public String stringValue() {
        String value;
        IonType type = super.getType();
        if (type == IonType.STRING) {
            value = super.stringValue();
        } else if (type == IonType.SYMBOL) {
            int sid = this.symbolValueId();
            if (sid < 0) {
                return null;
            }
            value = this.getSymbol(sid);
            if (value == null) {
                throw new UnknownSymbolException(sid);
            }
        } else {
            throw new IllegalStateException("Invalid type requested.");
        }
        return value;
    }

    @Override
    public SymbolToken symbolValue() {
        int sid = this.symbolValueId();
        if (sid < 0) {
            return null;
        }
        return this.getSymbolToken(sid);
    }

    @Override
    public String[] getTypeAnnotations() {
        if (!this.hasAnnotations) {
            return _Private_Utils.EMPTY_STRING_ARRAY;
        }
        IntList annotationSids = this.getAnnotationSidList();
        String[] annotationArray = new String[annotationSids.size()];
        for (int i = 0; i < annotationArray.length; ++i) {
            String symbol = this.getSymbol(annotationSids.get(i));
            if (symbol == null) {
                throw new UnknownSymbolException(annotationSids.get(i));
            }
            annotationArray[i] = symbol;
        }
        return annotationArray;
    }

    @Override
    public SymbolToken[] getTypeAnnotationSymbols() {
        if (!this.hasAnnotations) {
            return SymbolToken.EMPTY_ARRAY;
        }
        IntList annotationSids = this.getAnnotationSidList();
        SymbolToken[] annotationArray = new SymbolToken[annotationSids.size()];
        for (int i = 0; i < annotationArray.length; ++i) {
            annotationArray[i] = this.getSymbolToken(annotationSids.get(i));
        }
        return annotationArray;
    }

    @Override
    public Iterator<String> iterateTypeAnnotations() {
        if (!this.hasAnnotations) {
            return EMPTY_ITERATOR;
        }
        this.annotationIterator.reset();
        return this.annotationIterator;
    }

    @Override
    public String getFieldName() {
        if (this.fieldSid < 0) {
            return null;
        }
        String fieldName = this.getSymbol(this.fieldSid);
        if (fieldName == null) {
            throw new UnknownSymbolException(this.fieldSid);
        }
        return fieldName;
    }

    @Override
    public SymbolToken getFieldNameSymbol() {
        if (this.fieldSid < 0) {
            return null;
        }
        return this.getSymbolToken(this.fieldSid);
    }

    static /* synthetic */ String[] access$502(IonReaderContinuableApplicationBinary x0, String[] x1) {
        x0.symbols = x1;
        return x1;
    }

    private static enum State {
        ON_SYMBOL_TABLE_STRUCT,
        ON_SYMBOL_TABLE_FIELD,
        ON_SYMBOL_TABLE_SYMBOLS,
        READING_SYMBOL_TABLE_SYMBOLS_LIST,
        READING_SYMBOL_TABLE_SYMBOL,
        ON_SYMBOL_TABLE_IMPORTS,
        READING_SYMBOL_TABLE_IMPORTS_LIST,
        READING_SYMBOL_TABLE_IMPORT_STRUCT,
        READING_SYMBOL_TABLE_IMPORT_NAME,
        READING_SYMBOL_TABLE_IMPORT_VERSION,
        READING_SYMBOL_TABLE_IMPORT_MAX_ID,
        READING_VALUE;

    }

    private class SymbolTableReader {
        private boolean hasSeenImports;
        private boolean hasSeenSymbols;
        private String name = null;
        private int version = -1;
        private int maxId = -1;
        private List<SymbolTable> newImports = null;
        private List<String> newSymbols = null;

        private SymbolTableReader() {
        }

        private void resetState() {
            this.hasSeenImports = false;
            this.hasSeenSymbols = false;
            this.newImports = null;
            this.newSymbols = null;
            this.name = null;
            this.version = -1;
            this.maxId = -1;
        }

        private boolean valueUnavailable() {
            IonCursor.Event event = IonReaderContinuableApplicationBinary.this.fillValue();
            return event == IonCursor.Event.NEEDS_DATA || event == IonCursor.Event.NEEDS_INSTRUCTION;
        }

        private void growSymbolsArray(int shortfall) {
            int newSize = IonCursorBinary.nextPowerOfTwo(IonReaderContinuableApplicationBinary.this.symbols.length + shortfall);
            String[] resized = new String[newSize];
            System.arraycopy(IonReaderContinuableApplicationBinary.this.symbols, 0, resized, 0, IonReaderContinuableApplicationBinary.this.localSymbolMaxOffset + 1);
            IonReaderContinuableApplicationBinary.access$502(IonReaderContinuableApplicationBinary.this, resized);
        }

        private void finishReadingSymbolTableStruct() {
            IonReaderContinuableApplicationBinary.this.stepOutOfContainer();
            if (!this.hasSeenImports) {
                IonReaderContinuableApplicationBinary.this.resetSymbolTable();
                IonReaderContinuableApplicationBinary.this.resetImports();
            }
            if (this.newSymbols != null) {
                int numberOfAvailableSlots;
                int numberOfNewSymbols = this.newSymbols.size();
                int shortfall = numberOfNewSymbols - (numberOfAvailableSlots = IonReaderContinuableApplicationBinary.this.symbols.length - (IonReaderContinuableApplicationBinary.this.localSymbolMaxOffset + 1));
                if (shortfall > 0) {
                    this.growSymbolsArray(shortfall);
                }
                int i = IonReaderContinuableApplicationBinary.this.localSymbolMaxOffset;
                for (String newSymbol : this.newSymbols) {
                    ((IonReaderContinuableApplicationBinary)IonReaderContinuableApplicationBinary.this).symbols[++i] = newSymbol;
                }
                IonReaderContinuableApplicationBinary.this.localSymbolMaxOffset = IonReaderContinuableApplicationBinary.this.localSymbolMaxOffset + this.newSymbols.size();
            }
            IonReaderContinuableApplicationBinary.this.state = State.READING_VALUE;
        }

        private void readSymbolTableStructField() {
            if (IonReaderContinuableApplicationBinary.this.fieldSid == 7) {
                IonReaderContinuableApplicationBinary.this.state = State.ON_SYMBOL_TABLE_SYMBOLS;
                if (this.hasSeenSymbols) {
                    throw new IonException("Symbol table contained multiple symbols fields.");
                }
                this.hasSeenSymbols = true;
            } else if (IonReaderContinuableApplicationBinary.this.fieldSid == 6) {
                IonReaderContinuableApplicationBinary.this.state = State.ON_SYMBOL_TABLE_IMPORTS;
                if (this.hasSeenImports) {
                    throw new IonException("Symbol table contained multiple imports fields.");
                }
                this.hasSeenImports = true;
            }
        }

        private void startReadingImportsList() {
            IonReaderContinuableApplicationBinary.this.resetImports();
            IonReaderContinuableApplicationBinary.this.resetSymbolTable();
            this.newImports = new ArrayList<SymbolTable>(3);
            this.newImports.add(IonReaderContinuableApplicationBinary.this.getSystemSymbolTable());
            IonReaderContinuableApplicationBinary.this.state = State.READING_SYMBOL_TABLE_IMPORTS_LIST;
        }

        private void preparePossibleAppend() {
            if (IonReaderContinuableApplicationBinary.this.symbolValueId() != 3) {
                IonReaderContinuableApplicationBinary.this.resetSymbolTable();
            }
            IonReaderContinuableApplicationBinary.this.state = State.ON_SYMBOL_TABLE_FIELD;
        }

        private void finishReadingImportsList() {
            IonReaderContinuableApplicationBinary.this.stepOutOfContainer();
            IonReaderContinuableApplicationBinary.this.imports = new LocalSymbolTableImports(this.newImports);
            IonReaderContinuableApplicationBinary.this.firstLocalSymbolId = IonReaderContinuableApplicationBinary.this.imports.getMaxId() + 1;
            IonReaderContinuableApplicationBinary.this.state = State.ON_SYMBOL_TABLE_FIELD;
        }

        private void startReadingSymbolsList() {
            this.newSymbols = new ArrayList<String>(8);
            IonReaderContinuableApplicationBinary.this.state = State.READING_SYMBOL_TABLE_SYMBOLS_LIST;
        }

        private void startReadingSymbol() {
            if (IonReaderContinuableApplicationBinary.super.getType() == IonType.STRING) {
                IonReaderContinuableApplicationBinary.this.state = State.READING_SYMBOL_TABLE_SYMBOL;
            } else {
                this.newSymbols.add(null);
            }
        }

        private void finishReadingSymbol() {
            this.newSymbols.add(IonReaderContinuableApplicationBinary.this.stringValue());
            IonReaderContinuableApplicationBinary.this.state = State.READING_SYMBOL_TABLE_SYMBOLS_LIST;
        }

        private void finishReadingSymbolsList() {
            IonReaderContinuableApplicationBinary.this.stepOutOfContainer();
            IonReaderContinuableApplicationBinary.this.state = State.ON_SYMBOL_TABLE_FIELD;
        }

        private void startReadingImportStruct() {
            this.name = null;
            this.version = 1;
            this.maxId = -1;
            if (IonReaderContinuableApplicationBinary.super.getType() == IonType.STRUCT) {
                IonReaderContinuableApplicationBinary.this.stepIntoContainer();
                IonReaderContinuableApplicationBinary.this.state = State.READING_SYMBOL_TABLE_IMPORT_STRUCT;
            }
        }

        private void finishReadingImportStruct() {
            IonReaderContinuableApplicationBinary.this.stepOutOfContainer();
            IonReaderContinuableApplicationBinary.this.state = State.READING_SYMBOL_TABLE_IMPORTS_LIST;
            if (this.name == null || this.name.length() == 0 || this.name.equals("$ion")) {
                return;
            }
            this.newImports.add(IonReaderContinuableApplicationBinary.this.createImport(this.name, this.version, this.maxId));
        }

        private void startReadingImportStructField() {
            int fieldId = IonReaderContinuableApplicationBinary.this.getFieldId();
            if (fieldId == 4) {
                IonReaderContinuableApplicationBinary.this.state = State.READING_SYMBOL_TABLE_IMPORT_NAME;
            } else if (fieldId == 5) {
                IonReaderContinuableApplicationBinary.this.state = State.READING_SYMBOL_TABLE_IMPORT_VERSION;
            } else if (fieldId == 8) {
                IonReaderContinuableApplicationBinary.this.state = State.READING_SYMBOL_TABLE_IMPORT_MAX_ID;
            }
        }

        private void readImportName() {
            if (IonReaderContinuableApplicationBinary.super.getType() == IonType.STRING) {
                this.name = IonReaderContinuableApplicationBinary.this.stringValue();
            }
            IonReaderContinuableApplicationBinary.this.state = State.READING_SYMBOL_TABLE_IMPORT_STRUCT;
        }

        private void readImportVersion() {
            if (IonReaderContinuableApplicationBinary.super.getType() == IonType.INT && !IonReaderContinuableApplicationBinary.super.isNullValue()) {
                this.version = Math.max(1, IonReaderContinuableApplicationBinary.this.intValue());
            }
            IonReaderContinuableApplicationBinary.this.state = State.READING_SYMBOL_TABLE_IMPORT_STRUCT;
        }

        private void readImportMaxId() {
            if (IonReaderContinuableApplicationBinary.super.getType() == IonType.INT && !IonReaderContinuableApplicationBinary.super.isNullValue()) {
                this.maxId = IonReaderContinuableApplicationBinary.this.intValue();
            }
            IonReaderContinuableApplicationBinary.this.state = State.READING_SYMBOL_TABLE_IMPORT_STRUCT;
        }

        void readSymbolTable() {
            block13: while (true) {
                switch (IonReaderContinuableApplicationBinary.this.state) {
                    case ON_SYMBOL_TABLE_STRUCT: {
                        if (IonCursor.Event.NEEDS_DATA == IonReaderContinuableApplicationBinary.this.stepIntoContainer()) {
                            return;
                        }
                        IonReaderContinuableApplicationBinary.this.state = State.ON_SYMBOL_TABLE_FIELD;
                        continue block13;
                    }
                    case ON_SYMBOL_TABLE_FIELD: {
                        IonCursor.Event event = IonReaderContinuableApplicationBinary.super.nextValue();
                        if (IonCursor.Event.NEEDS_DATA == event) {
                            return;
                        }
                        if (event == IonCursor.Event.END_CONTAINER) {
                            this.finishReadingSymbolTableStruct();
                            return;
                        }
                        this.readSymbolTableStructField();
                        continue block13;
                    }
                    case ON_SYMBOL_TABLE_SYMBOLS: {
                        if (IonReaderContinuableApplicationBinary.super.getType() == IonType.LIST) {
                            if (IonCursor.Event.NEEDS_DATA == IonReaderContinuableApplicationBinary.this.stepIntoContainer()) {
                                return;
                            }
                            this.startReadingSymbolsList();
                            continue block13;
                        }
                        IonReaderContinuableApplicationBinary.this.state = State.ON_SYMBOL_TABLE_FIELD;
                        continue block13;
                    }
                    case ON_SYMBOL_TABLE_IMPORTS: {
                        if (IonReaderContinuableApplicationBinary.super.getType() == IonType.LIST) {
                            if (IonCursor.Event.NEEDS_DATA == IonReaderContinuableApplicationBinary.this.stepIntoContainer()) {
                                return;
                            }
                            this.startReadingImportsList();
                            continue block13;
                        }
                        if (IonReaderContinuableApplicationBinary.super.getType() == IonType.SYMBOL) {
                            if (this.valueUnavailable()) {
                                return;
                            }
                            this.preparePossibleAppend();
                            continue block13;
                        }
                        IonReaderContinuableApplicationBinary.this.state = State.ON_SYMBOL_TABLE_FIELD;
                        continue block13;
                    }
                    case READING_SYMBOL_TABLE_SYMBOLS_LIST: {
                        IonCursor.Event event = IonReaderContinuableApplicationBinary.super.nextValue();
                        if (event == IonCursor.Event.NEEDS_DATA) {
                            return;
                        }
                        if (event == IonCursor.Event.END_CONTAINER) {
                            this.finishReadingSymbolsList();
                            continue block13;
                        }
                        this.startReadingSymbol();
                        continue block13;
                    }
                    case READING_SYMBOL_TABLE_SYMBOL: {
                        if (this.valueUnavailable()) {
                            return;
                        }
                        this.finishReadingSymbol();
                        continue block13;
                    }
                    case READING_SYMBOL_TABLE_IMPORTS_LIST: {
                        IonCursor.Event event = IonReaderContinuableApplicationBinary.super.nextValue();
                        if (event == IonCursor.Event.NEEDS_DATA) {
                            return;
                        }
                        if (event == IonCursor.Event.END_CONTAINER) {
                            this.finishReadingImportsList();
                            continue block13;
                        }
                        this.startReadingImportStruct();
                        continue block13;
                    }
                    case READING_SYMBOL_TABLE_IMPORT_STRUCT: {
                        IonCursor.Event event = IonReaderContinuableApplicationBinary.super.nextValue();
                        if (event == IonCursor.Event.NEEDS_DATA) {
                            return;
                        }
                        if (event == IonCursor.Event.END_CONTAINER) {
                            this.finishReadingImportStruct();
                            continue block13;
                        }
                        if (event != IonCursor.Event.START_SCALAR) continue block13;
                        this.startReadingImportStructField();
                        continue block13;
                    }
                    case READING_SYMBOL_TABLE_IMPORT_NAME: {
                        if (this.valueUnavailable()) {
                            return;
                        }
                        this.readImportName();
                        continue block13;
                    }
                    case READING_SYMBOL_TABLE_IMPORT_VERSION: {
                        if (this.valueUnavailable()) {
                            return;
                        }
                        this.readImportVersion();
                        continue block13;
                    }
                    case READING_SYMBOL_TABLE_IMPORT_MAX_ID: {
                        if (this.valueUnavailable()) {
                            return;
                        }
                        this.readImportMaxId();
                        continue block13;
                    }
                }
                break;
            }
            throw new IllegalStateException();
        }
    }

    private class LocalSymbolTableSnapshot
    implements SymbolTableAsStruct,
    _Private_LocalSymbolTable {
        private final SymbolTable system;
        private final int maxId;
        private final LocalSymbolTableImports importedTables;
        final Map<String, Integer> textToId;
        final String[] idToText;
        private SymbolTableStructCache structCache;

        LocalSymbolTableSnapshot() {
            this.system = IonReaderContinuableApplicationBinary.this.getSystemSymbolTable();
            this.structCache = null;
            int importsMaxId = IonReaderContinuableApplicationBinary.this.imports.getMaxId();
            int numberOfLocalSymbols = IonReaderContinuableApplicationBinary.this.localSymbolMaxOffset + 1;
            this.importedTables = IonReaderContinuableApplicationBinary.this.imports;
            this.maxId = importsMaxId + numberOfLocalSymbols;
            this.idToText = new String[numberOfLocalSymbols];
            System.arraycopy(IonReaderContinuableApplicationBinary.this.symbols, 0, this.idToText, 0, numberOfLocalSymbols);
            this.textToId = new HashMap<String, Integer>((int)Math.ceil((double)numberOfLocalSymbols / 0.75), 0.75f);
            for (int i = 0; i < numberOfLocalSymbols; ++i) {
                String symbol = this.idToText[i];
                if (symbol == null) continue;
                this.textToId.put(symbol, i + importsMaxId + 1);
            }
        }

        @Override
        public String getName() {
            return null;
        }

        @Override
        public int getVersion() {
            return 0;
        }

        @Override
        public boolean isLocalTable() {
            return true;
        }

        @Override
        public boolean isSharedTable() {
            return false;
        }

        @Override
        public boolean isSubstitute() {
            return false;
        }

        @Override
        public boolean isSystemTable() {
            return false;
        }

        @Override
        public SymbolTable getSystemSymbolTable() {
            return this.system;
        }

        @Override
        public String getIonVersionId() {
            return this.system.getIonVersionId();
        }

        @Override
        public SymbolTable[] getImportedTables() {
            return this.importedTables.getImportedTables();
        }

        @Override
        public int getImportedMaxId() {
            return this.importedTables.getMaxId();
        }

        @Override
        public SymbolToken find(String text) {
            SymbolToken token = this.importedTables.find(text);
            if (token != null) {
                return token;
            }
            Integer sid = this.textToId.get(text);
            if (sid == null) {
                return null;
            }
            return new SymbolTokenImpl(text, sid);
        }

        @Override
        public int findSymbol(String name) {
            Integer sid = this.importedTables.findSymbol(name);
            if (sid > -1) {
                return sid;
            }
            sid = this.textToId.get(name);
            if (sid == null) {
                return -1;
            }
            return sid;
        }

        @Override
        public String findKnownSymbol(int id) {
            if (id < 0) {
                throw new IllegalArgumentException("Symbol IDs must be at least 0.");
            }
            if (id > this.getMaxId()) {
                return null;
            }
            return IonReaderContinuableApplicationBinary.this.getSymbolString(id, this.importedTables, this.idToText);
        }

        @Override
        public Iterator<String> iterateDeclaredSymbolNames() {
            return new Iterator<String>(){
                private int index = 0;

                @Override
                public boolean hasNext() {
                    return this.index < LocalSymbolTableSnapshot.this.idToText.length;
                }

                @Override
                public String next() {
                    if (this.index >= LocalSymbolTableSnapshot.this.idToText.length) {
                        throw new NoSuchElementException();
                    }
                    String symbol = LocalSymbolTableSnapshot.this.idToText[this.index];
                    ++this.index;
                    return symbol;
                }

                @Override
                public void remove() {
                    throw new UnsupportedOperationException("This iterator does not support element removal.");
                }
            };
        }

        @Override
        public SymbolToken intern(String text) {
            SymbolToken token = this.find(text);
            if (token != null) {
                return token;
            }
            throw new ReadOnlyValueException();
        }

        @Override
        public int getMaxId() {
            return this.maxId;
        }

        @Override
        public boolean isReadOnly() {
            return true;
        }

        @Override
        public void makeReadOnly() {
        }

        @Override
        public void writeTo(IonWriter writer) throws IOException {
            com.amazon.ion.impl.SymbolTableReader reader = new com.amazon.ion.impl.SymbolTableReader(this);
            writer.writeValues(reader);
        }

        public String toString() {
            return "(LocalSymbolTable max_id:" + this.getMaxId() + ')';
        }

        @Override
        public IonStruct getIonRepresentation(ValueFactory valueFactory) {
            if (this.structCache == null) {
                this.structCache = new SymbolTableStructCache(this, this.getImportedTables(), null);
            }
            return this.structCache.getIonRepresentation(valueFactory);
        }

        @Override
        public _Private_LocalSymbolTable makeCopy() {
            return new LocalSymbolTable(this.importedTables, Arrays.asList(this.idToText));
        }

        @Override
        public SymbolTable[] getImportedTablesNoCopy() {
            return this.importedTables.getImportedTablesNoCopy();
        }
    }

    private class AnnotationSequenceIterator
    implements Iterator<String> {
        private IntList annotationSids;
        private int index = 0;

        private AnnotationSequenceIterator() {
        }

        void reset() {
            this.index = 0;
            this.annotationSids = IonReaderContinuableApplicationBinary.this.getAnnotationSidList();
        }

        @Override
        public boolean hasNext() {
            return this.index < this.annotationSids.size();
        }

        @Override
        public String next() {
            int sid = this.annotationSids.get(this.index);
            String annotation = IonReaderContinuableApplicationBinary.this.getSymbol(sid);
            if (annotation == null) {
                throw new UnknownSymbolException(sid);
            }
            ++this.index;
            return annotation;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("This iterator does not support element removal.");
        }
    }
}

