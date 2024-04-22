/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazon.ion.impl.bin;

import com.amazon.ion.IonCatalog;
import com.amazon.ion.IonException;
import com.amazon.ion.IonType;
import com.amazon.ion.SymbolTable;
import com.amazon.ion.SymbolToken;
import com.amazon.ion.Timestamp;
import com.amazon.ion.UnknownSymbolException;
import com.amazon.ion.impl.bin.AbstractIonWriter;
import com.amazon.ion.impl.bin.AbstractSymbolTable;
import com.amazon.ion.impl.bin.IonRawBinaryWriter;
import com.amazon.ion.impl.bin.Symbols;
import com.amazon.ion.impl.bin._Private_IonManagedBinaryWriterBuilder;
import com.amazon.ion.impl.bin._Private_IonManagedWriter;
import com.amazon.ion.impl.bin._Private_IonRawWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

final class IonManagedBinaryWriter
extends AbstractIonWriter
implements _Private_IonManagedWriter {
    static final ImportedSymbolContext ONLY_SYSTEM_IMPORTS = new ImportedSymbolContext(ImportedSymbolResolverMode.FLAT, Collections.emptyList());
    private static final SymbolTable[] EMPTY_SYMBOL_TABLE_ARRAY = new SymbolTable[0];
    private final IonCatalog catalog;
    private final ImportedSymbolContext bootstrapImports;
    private ImportedSymbolContext imports;
    private final Map<String, SymbolToken> locals;
    private boolean localsLocked;
    private SymbolTable localSymbolTableView;
    private final IonRawBinaryWriter symbols;
    private final IonRawBinaryWriter user;
    private UserState userState;
    private SymbolState symbolState;
    private long userSymbolTablePosition;
    private final List<SymbolTable> userImports;
    private final List<String> userSymbols;
    private final ImportDescriptor userCurrentImport;
    private final boolean lstAppendEnabled;
    private boolean isUserLSTAppend;
    private boolean closed;

    IonManagedBinaryWriter(_Private_IonManagedBinaryWriterBuilder builder, OutputStream out) throws IOException {
        super(builder.optimization);
        this.symbols = new IonRawBinaryWriter(builder.provider, builder.symbolsBlockSize, out, AbstractIonWriter.WriteValueOptimization.NONE, IonRawBinaryWriter.StreamCloseMode.NO_CLOSE, IonRawBinaryWriter.StreamFlushMode.NO_FLUSH, builder.preallocationMode, builder.isFloatBinary32Enabled);
        this.user = new IonRawBinaryWriter(builder.provider, builder.userBlockSize, out, AbstractIonWriter.WriteValueOptimization.NONE, IonRawBinaryWriter.StreamCloseMode.CLOSE, IonRawBinaryWriter.StreamFlushMode.FLUSH, builder.preallocationMode, builder.isFloatBinary32Enabled);
        this.catalog = builder.catalog;
        this.bootstrapImports = builder.imports;
        this.locals = new LinkedHashMap<String, SymbolToken>();
        this.localsLocked = false;
        this.localSymbolTableView = new LocalSymbolTableView();
        this.symbolState = SymbolState.SYSTEM_SYMBOLS;
        this.closed = false;
        this.userState = UserState.NORMAL;
        this.userSymbolTablePosition = 0L;
        this.userImports = new ArrayList<SymbolTable>();
        this.userSymbols = new ArrayList<String>();
        this.userCurrentImport = new ImportDescriptor();
        this.lstAppendEnabled = builder.isLocalSymbolTableAppendEnabled;
        this.isUserLSTAppend = false;
        SymbolTable lst = builder.initialSymbolTable;
        if (lst != null) {
            ImportedSymbolContext lstImports;
            List<SymbolTable> lstImportList = Arrays.asList(lst.getImportedTables());
            this.imports = lstImports = new ImportedSymbolContext(ImportedSymbolResolverMode.DELEGATE, lstImportList);
            Iterator<String> symbolIter = lst.iterateDeclaredSymbolNames();
            while (symbolIter.hasNext()) {
                String text = symbolIter.next();
                this.intern(text);
            }
            this.startLocalSymbolTableIfNeeded(true);
        } else {
            this.imports = builder.imports;
        }
    }

    @Override
    public _Private_IonRawWriter getRawWriter() {
        return this.user;
    }

    @Override
    public IonCatalog getCatalog() {
        return this.catalog;
    }

    @Override
    public boolean isFieldNameSet() {
        return this.user.isFieldNameSet();
    }

    @Override
    public void writeIonVersionMarker() throws IOException {
        this.finish();
    }

    @Override
    public int getDepth() {
        return this.user.getDepth();
    }

    private void startLocalSymbolTableIfNeeded(boolean writeIVM) throws IOException {
        boolean isAppend;
        boolean bl = isAppend = this.symbolState == SymbolState.LOCAL_SYMBOLS_FLUSHED && this.lstAppendEnabled;
        if (this.symbolState == SymbolState.SYSTEM_SYMBOLS || isAppend) {
            if (writeIVM && !isAppend) {
                this.symbols.writeIonVersionMarker();
            }
            this.symbols.addTypeAnnotationSymbol(Symbols.systemSymbol(3));
            this.symbols.stepIn(IonType.STRUCT);
            if (isAppend) {
                this.symbols.setFieldNameSymbol(Symbols.systemSymbol(6));
                this.symbols.writeSymbolToken(Symbols.systemSymbol(3));
            } else if (this.imports.parents.size() > 0) {
                this.symbols.setFieldNameSymbol(Symbols.systemSymbol(6));
                this.symbols.stepIn(IonType.LIST);
                for (SymbolTable st : this.imports.parents) {
                    this.symbols.stepIn(IonType.STRUCT);
                    this.symbols.setFieldNameSymbol(Symbols.systemSymbol(4));
                    this.symbols.writeString(st.getName());
                    this.symbols.setFieldNameSymbol(Symbols.systemSymbol(5));
                    this.symbols.writeInt(st.getVersion());
                    this.symbols.setFieldNameSymbol(Symbols.systemSymbol(8));
                    this.symbols.writeInt(st.getMaxId());
                    this.symbols.stepOut();
                }
                this.symbols.stepOut();
            }
            this.symbolState = SymbolState.LOCAL_SYMBOLS_WITH_IMPORTS_ONLY;
        }
    }

    private void startLocalSymbolTableSymbolListIfNeeded() throws IOException {
        if (this.symbolState == SymbolState.LOCAL_SYMBOLS_WITH_IMPORTS_ONLY) {
            this.symbols.setFieldNameSymbol(Symbols.systemSymbol(7));
            this.symbols.stepIn(IonType.LIST);
            this.symbolState = SymbolState.LOCAL_SYMBOLS;
        }
    }

    private SymbolToken intern(String text) {
        if (text == null) {
            return null;
        }
        try {
            SymbolToken token = this.imports.importedSymbols.get(text);
            if (token != null) {
                if (token.getSid() > 9) {
                    this.startLocalSymbolTableIfNeeded(true);
                }
                return token;
            }
            token = this.locals.get(text);
            if (token == null) {
                if (this.localsLocked) {
                    throw new IonException("Local symbol table was locked (made read-only)");
                }
                this.startLocalSymbolTableIfNeeded(true);
                this.startLocalSymbolTableSymbolListIfNeeded();
                token = Symbols.symbol(text, this.imports.localSidStart + this.locals.size());
                this.locals.put(text, token);
                this.symbols.writeString(text);
            }
            return token;
        } catch (IOException e) {
            throw new IonException("Error synthesizing symbols", e);
        }
    }

    private SymbolToken intern(SymbolToken token) {
        if (token == null) {
            return null;
        }
        String text = token.getText();
        if (text != null) {
            return this.intern(text);
        }
        int sid = token.getSid();
        if (sid > this.getSymbolTable().getMaxId()) {
            throw new UnknownSymbolException(sid);
        }
        return token;
    }

    @Override
    public SymbolTable getSymbolTable() {
        if (this.symbolState == SymbolState.SYSTEM_SYMBOLS && this.imports.parents.isEmpty()) {
            return Symbols.systemSymbolTable();
        }
        return this.localSymbolTableView;
    }

    @Override
    public void setFieldName(String name) {
        if (!this.isInStruct()) {
            throw new IllegalStateException("IonWriter.setFieldName() must be called before writing a value into a struct.");
        }
        if (name == null) {
            throw new NullPointerException("Null field name is not allowed.");
        }
        SymbolToken token = this.intern(name);
        this.user.setFieldNameSymbol(token);
    }

    @Override
    public void setFieldNameSymbol(SymbolToken token) {
        token = this.intern(token);
        this.user.setFieldNameSymbol(token);
    }

    @Override
    public void requireLocalSymbolTable() throws IOException {
        this.startLocalSymbolTableIfNeeded(true);
    }

    @Override
    public void setTypeAnnotations(String ... annotations) {
        this.user.setTypeAnnotationSymbols(null);
        if (annotations == null) {
            return;
        }
        for (int i = 0; i < annotations.length; ++i) {
            this.addTypeAnnotation(annotations[i]);
        }
    }

    @Override
    public void setTypeAnnotationSymbols(SymbolToken ... annotations) {
        if (annotations == null) {
            this.user.setTypeAnnotationSymbols(null);
        } else {
            for (int i = 0; i < annotations.length; ++i) {
                annotations[i] = this.intern(annotations[i]);
            }
            this.user.setTypeAnnotationSymbols(annotations);
        }
    }

    @Override
    public void addTypeAnnotation(String annotation) {
        SymbolToken token = this.intern(annotation);
        this.user.addTypeAnnotationSymbol(token);
    }

    @Override
    public void stepIn(IonType containerType) throws IOException {
        this.userState.beforeStepIn(this, containerType);
        this.user.stepIn(containerType);
    }

    @Override
    public void stepOut() throws IOException {
        this.user.stepOut();
        this.userState.afterStepOut(this);
    }

    @Override
    public boolean isInStruct() {
        return this.user.isInStruct();
    }

    @Override
    public void writeNull() throws IOException {
        this.user.writeNull();
    }

    @Override
    public void writeNull(IonType type) throws IOException {
        this.user.writeNull(type);
    }

    @Override
    public void writeBool(boolean value) throws IOException {
        this.user.writeBool(value);
    }

    @Override
    public void writeInt(long value) throws IOException {
        this.userState.writeInt(this, value);
        this.user.writeInt(value);
    }

    @Override
    public void writeInt(BigInteger value) throws IOException {
        this.userState.writeInt(this, value);
        this.user.writeInt(value);
    }

    @Override
    public void writeFloat(double value) throws IOException {
        this.user.writeFloat(value);
    }

    @Override
    public void writeDecimal(BigDecimal value) throws IOException {
        this.user.writeDecimal(value);
    }

    @Override
    public void writeTimestamp(Timestamp value) throws IOException {
        this.user.writeTimestamp(value);
    }

    @Override
    public void writeSymbol(String content) throws IOException {
        this.writeSymbolToken(this.intern(content));
    }

    private boolean handleIVM(int sid) throws IOException {
        if (this.user.isIVM(sid)) {
            if (this.user.hasWrittenValuesSinceFinished()) {
                this.finish();
            }
            return true;
        }
        return false;
    }

    @Override
    public void writeSymbolToken(SymbolToken token) throws IOException {
        if (token != null && this.handleIVM(token.getSid())) {
            return;
        }
        token = this.intern(token);
        this.userState.writeSymbolToken(this, token);
        this.user.writeSymbolToken(token);
    }

    @Override
    public void writeString(String value) throws IOException {
        this.userState.writeString(this, value);
        this.user.writeString(value);
    }

    @Override
    public void writeClob(byte[] data) throws IOException {
        this.user.writeClob(data);
    }

    @Override
    public void writeClob(byte[] data, int offset, int length) throws IOException {
        this.user.writeClob(data, offset, length);
    }

    @Override
    public void writeBlob(byte[] data) throws IOException {
        this.user.writeBlob(data);
    }

    @Override
    public void writeBlob(byte[] data, int offset, int length) throws IOException {
        this.user.writeBlob(data, offset, length);
    }

    @Override
    public void writeString(byte[] data, int offset, int length) throws IOException {
        this.user.writeString(data, offset, length);
    }

    @Override
    public void writeBytes(byte[] data, int off, int len) throws IOException {
        this.startLocalSymbolTableIfNeeded(true);
        this.user.writeBytes(data, off, len);
    }

    @Override
    public void flush() throws IOException {
        if (this.getDepth() == 0 && !this.user.hasAnnotations() && (this.localsLocked || this.lstAppendEnabled)) {
            this.unsafeFlush();
        }
    }

    private void unsafeFlush() throws IOException {
        if (this.user.hasWrittenValuesSinceFinished()) {
            this.symbolState.closeTable(this.symbols);
            this.symbolState = SymbolState.LOCAL_SYMBOLS_FLUSHED;
        }
        this.symbols.finish();
        this.user.finish();
    }

    @Override
    public void finish() throws IOException {
        if (this.getDepth() != 0) {
            throw new IllegalStateException("IonWriter.finish() can only be called at top-level.");
        }
        this.unsafeFlush();
        this.locals.clear();
        this.localsLocked = false;
        this.symbolState = SymbolState.SYSTEM_SYMBOLS;
        this.imports = this.bootstrapImports;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void close() throws IOException {
        if (this.closed) {
            return;
        }
        this.closed = true;
        try {
            this.finish();
        } catch (IllegalStateException illegalStateException) {
            try {
                this.symbols.close();
            } finally {
                this.user.close();
            }
        } finally {
            try {
                this.symbols.close();
            } finally {
                this.user.close();
            }
        }
    }

    private class LocalSymbolTableView
    extends AbstractSymbolTable {
        public LocalSymbolTableView() {
            super(null, 0);
        }

        @Override
        public Iterator<String> iterateDeclaredSymbolNames() {
            return IonManagedBinaryWriter.this.locals.keySet().iterator();
        }

        @Override
        public int getMaxId() {
            return this.getImportedMaxId() + IonManagedBinaryWriter.this.locals.size();
        }

        @Override
        public SymbolTable[] getImportedTables() {
            return ((IonManagedBinaryWriter)IonManagedBinaryWriter.this).imports.parents.toArray(EMPTY_SYMBOL_TABLE_ARRAY);
        }

        @Override
        public int getImportedMaxId() {
            return ((IonManagedBinaryWriter)IonManagedBinaryWriter.this).imports.localSidStart - 1;
        }

        @Override
        public boolean isSystemTable() {
            return false;
        }

        @Override
        public boolean isSubstitute() {
            return false;
        }

        @Override
        public boolean isSharedTable() {
            return false;
        }

        @Override
        public boolean isLocalTable() {
            return true;
        }

        @Override
        public boolean isReadOnly() {
            return IonManagedBinaryWriter.this.localsLocked;
        }

        @Override
        public SymbolTable getSystemSymbolTable() {
            return Symbols.systemSymbolTable();
        }

        @Override
        public SymbolToken intern(String text) {
            SymbolToken token = this.find(text);
            if (token == null) {
                if (IonManagedBinaryWriter.this.localsLocked) {
                    throw new IonException("Cannot intern into locked (read-only) local symbol table");
                }
                token = IonManagedBinaryWriter.this.intern(text);
            }
            return token;
        }

        @Override
        public String findKnownSymbol(int id) {
            for (SymbolTable table2 : ((IonManagedBinaryWriter)IonManagedBinaryWriter.this).imports.parents) {
                String text = table2.findKnownSymbol(id);
                if (text == null) continue;
                return text;
            }
            for (SymbolToken token : IonManagedBinaryWriter.this.locals.values()) {
                if (token.getSid() != id) continue;
                return token.getText();
            }
            return null;
        }

        @Override
        public SymbolToken find(String text) {
            SymbolToken token = ((IonManagedBinaryWriter)IonManagedBinaryWriter.this).imports.importedSymbols.get(text);
            if (token != null) {
                return token;
            }
            return (SymbolToken)IonManagedBinaryWriter.this.locals.get(text);
        }

        @Override
        public void makeReadOnly() {
            IonManagedBinaryWriter.this.localsLocked = true;
        }
    }

    private static enum UserState {
        NORMAL{

            @Override
            public void beforeStepIn(IonManagedBinaryWriter self, IonType type) {
                if (self.user.hasTopLevelSymbolTableAnnotation() && type == IonType.STRUCT) {
                    self.userState = 1.LOCALS_AT_TOP;
                    self.userSymbolTablePosition = self.user.position();
                }
            }

            @Override
            public void afterStepOut(IonManagedBinaryWriter self) {
            }

            @Override
            public void writeInt(IonManagedBinaryWriter self, BigInteger value) {
            }
        }
        ,
        LOCALS_AT_TOP{

            @Override
            public void beforeStepIn(IonManagedBinaryWriter self, IonType type) {
                if (self.user.getDepth() == 1) {
                    switch (self.user.getFieldId()) {
                        case 6: {
                            if (type != IonType.LIST) {
                                throw new IllegalArgumentException("Cannot step into Local Symbol Table 'symbols' field as non-list: " + (Object)((Object)type));
                            }
                            self.userState = 2.LOCALS_AT_IMPORTS;
                            break;
                        }
                        case 7: {
                            if (type != IonType.LIST) {
                                throw new IllegalArgumentException("Cannot step into Local Symbol Table 'symbols' field as non-list: " + (Object)((Object)type));
                            }
                            self.userState = 2.LOCALS_AT_SYMBOLS;
                        }
                    }
                }
            }

            @Override
            public void afterStepOut(IonManagedBinaryWriter self) throws IOException {
                if (self.user.getDepth() == 0) {
                    self.user.truncate(self.userSymbolTablePosition);
                    if (self.isUserLSTAppend) {
                        self.flush();
                    } else {
                        self.finish();
                        self.imports = new ImportedSymbolContext(ImportedSymbolResolverMode.DELEGATE, self.userImports);
                    }
                    self.startLocalSymbolTableIfNeeded(false);
                    for (String text : self.userSymbols) {
                        self.intern(text);
                    }
                    self.userSymbolTablePosition = 0L;
                    self.userCurrentImport.reset();
                    self.userImports.clear();
                    self.userSymbols.clear();
                    self.isUserLSTAppend = false;
                    self.userState = 2.NORMAL;
                }
            }

            @Override
            public void writeSymbolToken(IonManagedBinaryWriter self, SymbolToken value) {
                if (self.user.getDepth() == 1 && self.user.getFieldId() == 6 && value.getSid() == 3) {
                    self.isUserLSTAppend = true;
                    self.userState = 2.LOCALS_AT_TOP;
                }
            }
        }
        ,
        LOCALS_AT_IMPORTS{

            @Override
            public void beforeStepIn(IonManagedBinaryWriter self, IonType type) {
                if (type != IonType.STRUCT) {
                    throw new IllegalArgumentException("Cannot step into non-struct in Local Symbol Table import list: " + (Object)((Object)type));
                }
            }

            @Override
            public void afterStepOut(IonManagedBinaryWriter self) {
                switch (self.user.getDepth()) {
                    case 2: {
                        ImportDescriptor desc = self.userCurrentImport;
                        if (desc.isMalformed()) {
                            throw new IllegalArgumentException("Invalid import: " + desc);
                        }
                        if (!desc.isDefined()) break;
                        SymbolTable symbols = self.catalog.getTable(desc.name, desc.version);
                        if (symbols == null) {
                            if (desc.maxId == -1) {
                                throw new IllegalArgumentException("Import is not in catalog and no max ID provided: " + desc);
                            }
                            symbols = Symbols.unknownSharedSymbolTable(desc.name, desc.version, desc.maxId);
                        }
                        if (desc.maxId != -1 && desc.maxId != symbols.getMaxId()) {
                            throw new IllegalArgumentException("Import doesn't match Max ID: " + desc);
                        }
                        self.userImports.add(symbols);
                        break;
                    }
                    case 1: {
                        self.userState = 3.LOCALS_AT_TOP;
                    }
                }
            }

            @Override
            public void writeString(IonManagedBinaryWriter self, String value) {
                if (self.user.getDepth() == 3 && self.user.getFieldId() == 4) {
                    if (value == null) {
                        throw new NullPointerException("Cannot have null import name");
                    }
                    ((IonManagedBinaryWriter)self).userCurrentImport.name = value;
                }
            }

            @Override
            public void writeInt(IonManagedBinaryWriter self, long value) {
                if (self.user.getDepth() == 3) {
                    if (value > Integer.MAX_VALUE || value < 1L) {
                        throw new IllegalArgumentException("Invalid integer value in import: " + value);
                    }
                    switch (self.user.getFieldId()) {
                        case 5: {
                            ((IonManagedBinaryWriter)self).userCurrentImport.version = (int)value;
                            break;
                        }
                        case 8: {
                            ((IonManagedBinaryWriter)self).userCurrentImport.maxId = (int)value;
                        }
                    }
                }
            }
        }
        ,
        LOCALS_AT_SYMBOLS{

            @Override
            public void beforeStepIn(IonManagedBinaryWriter self, IonType type) {
            }

            @Override
            public void afterStepOut(IonManagedBinaryWriter self) {
                if (self.user.getDepth() == 1) {
                    self.userState = 4.LOCALS_AT_TOP;
                }
            }

            @Override
            public void writeString(IonManagedBinaryWriter self, String value) {
                if (self.user.getDepth() == 2) {
                    self.userSymbols.add(value);
                }
            }
        };


        public abstract void beforeStepIn(IonManagedBinaryWriter var1, IonType var2) throws IOException;

        public abstract void afterStepOut(IonManagedBinaryWriter var1) throws IOException;

        public void writeString(IonManagedBinaryWriter self, String value) throws IOException {
        }

        public void writeSymbolToken(IonManagedBinaryWriter self, SymbolToken value) {
        }

        public void writeInt(IonManagedBinaryWriter self, long value) throws IOException {
        }

        public void writeInt(IonManagedBinaryWriter self, BigInteger value) throws IOException {
            this.writeInt(self, value.longValue());
        }
    }

    private static class ImportDescriptor {
        public String name;
        public int version;
        public int maxId;

        public ImportDescriptor() {
            this.reset();
        }

        public void reset() {
            this.name = null;
            this.version = -1;
            this.maxId = -1;
        }

        public boolean isDefined() {
            return this.name != null && this.version >= 1;
        }

        public boolean isUndefined() {
            return this.name == null && this.version == -1 && this.maxId == -1;
        }

        public boolean isMalformed() {
            return !this.isDefined() && !this.isUndefined();
        }

        public String toString() {
            return "{name: \"" + this.name + "\", version: " + this.version + ", max_id: " + this.maxId + "}";
        }
    }

    private static enum SymbolState {
        SYSTEM_SYMBOLS{

            @Override
            public void closeTable(IonRawBinaryWriter writer) throws IOException {
                writer.writeIonVersionMarker();
            }
        }
        ,
        LOCAL_SYMBOLS_WITH_IMPORTS_ONLY{

            @Override
            public void closeTable(IonRawBinaryWriter writer) throws IOException {
                writer.stepOut();
            }
        }
        ,
        LOCAL_SYMBOLS{

            @Override
            public void closeTable(IonRawBinaryWriter writer) throws IOException {
                writer.stepOut();
                writer.stepOut();
            }
        }
        ,
        LOCAL_SYMBOLS_FLUSHED{

            @Override
            public void closeTable(IonRawBinaryWriter writer) throws IOException {
            }
        };


        public abstract void closeTable(IonRawBinaryWriter var1) throws IOException;
    }

    static final class ImportedSymbolContext {
        public final List<SymbolTable> parents;
        public final SymbolResolver importedSymbols;
        public final int localSidStart;

        ImportedSymbolContext(ImportedSymbolResolverMode mode, List<SymbolTable> imports) {
            ArrayList<SymbolTable> mutableParents = new ArrayList<SymbolTable>(imports.size());
            SymbolResolverBuilder builder = mode.createBuilder();
            int maxSid = 10;
            for (SymbolTable st : imports) {
                if (!st.isSharedTable()) {
                    throw new IonException("Imported symbol table is not shared: " + st);
                }
                if (st.isSystemTable()) continue;
                mutableParents.add(st);
                maxSid = builder.addSymbolTable(st, maxSid);
            }
            this.parents = Collections.unmodifiableList(mutableParents);
            this.importedSymbols = builder.build();
            this.localSidStart = maxSid;
        }
    }

    static enum ImportedSymbolResolverMode {
        FLAT{

            @Override
            SymbolResolverBuilder createBuilder() {
                final HashMap<String, SymbolToken> symbols = new HashMap<String, SymbolToken>();
                for (SymbolToken token : Symbols.systemSymbols()) {
                    symbols.put(token.getText(), token);
                }
                return new SymbolResolverBuilder(){

                    @Override
                    public int addSymbolTable(SymbolTable table2, int startSid) {
                        int maxSid = startSid;
                        Iterator<String> iter = table2.iterateDeclaredSymbolNames();
                        while (iter.hasNext()) {
                            String text = iter.next();
                            if (text != null && !symbols.containsKey(text)) {
                                symbols.put(text, Symbols.symbol(text, maxSid));
                            }
                            ++maxSid;
                        }
                        return maxSid;
                    }

                    @Override
                    public SymbolResolver build() {
                        return new SymbolResolver(){

                            @Override
                            public SymbolToken get(String text) {
                                return (SymbolToken)symbols.get(text);
                            }
                        };
                    }
                };
            }
        }
        ,
        DELEGATE{

            @Override
            SymbolResolverBuilder createBuilder() {
                final ArrayList<ImportTablePosition> imports = new ArrayList<ImportTablePosition>();
                imports.add(new ImportTablePosition(Symbols.systemSymbolTable(), 1));
                return new SymbolResolverBuilder(){

                    @Override
                    public int addSymbolTable(SymbolTable table2, int startId) {
                        imports.add(new ImportTablePosition(table2, startId));
                        return startId + table2.getMaxId();
                    }

                    @Override
                    public SymbolResolver build() {
                        return new SymbolResolver(){

                            @Override
                            public SymbolToken get(String text) {
                                for (ImportTablePosition tableImport : imports) {
                                    SymbolToken token = tableImport.table.find(text);
                                    if (token == null) continue;
                                    return Symbols.symbol(text, token.getSid() + tableImport.startId - 1);
                                }
                                return null;
                            }
                        };
                    }
                };
            }
        };


        abstract SymbolResolverBuilder createBuilder();
    }

    private static final class ImportTablePosition {
        public final SymbolTable table;
        public final int startId;

        public ImportTablePosition(SymbolTable table2, int startId) {
            this.table = table2;
            this.startId = startId;
        }
    }

    private static interface SymbolResolverBuilder {
        public int addSymbolTable(SymbolTable var1, int var2);

        public SymbolResolver build();
    }

    private static interface SymbolResolver {
        public SymbolToken get(String var1);
    }
}

