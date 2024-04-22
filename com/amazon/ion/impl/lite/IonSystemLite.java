/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazon.ion.impl.lite;

import com.amazon.ion.IntegerSize;
import com.amazon.ion.IonBinaryWriter;
import com.amazon.ion.IonCatalog;
import com.amazon.ion.IonContainer;
import com.amazon.ion.IonDatagram;
import com.amazon.ion.IonException;
import com.amazon.ion.IonLoader;
import com.amazon.ion.IonReader;
import com.amazon.ion.IonStruct;
import com.amazon.ion.IonTextReader;
import com.amazon.ion.IonTimestamp;
import com.amazon.ion.IonType;
import com.amazon.ion.IonValue;
import com.amazon.ion.IonWriter;
import com.amazon.ion.SymbolTable;
import com.amazon.ion.SymbolToken;
import com.amazon.ion.UnexpectedEofException;
import com.amazon.ion.UnsupportedIonVersionException;
import com.amazon.ion.impl._Private_IonBinaryWriterBuilder;
import com.amazon.ion.impl._Private_IonReaderBuilder;
import com.amazon.ion.impl._Private_IonReaderFactory;
import com.amazon.ion.impl._Private_IonSystem;
import com.amazon.ion.impl._Private_IonWriterFactory;
import com.amazon.ion.impl._Private_Utils;
import com.amazon.ion.impl.lite.IonContainerLite;
import com.amazon.ion.impl.lite.IonDatagramLite;
import com.amazon.ion.impl.lite.IonLoaderLite;
import com.amazon.ion.impl.lite.IonSymbolLite;
import com.amazon.ion.impl.lite.IonTimestampLite;
import com.amazon.ion.impl.lite.IonValueLite;
import com.amazon.ion.impl.lite.ValueFactoryLite;
import com.amazon.ion.system.IonReaderBuilder;
import com.amazon.ion.system.IonTextWriterBuilder;
import com.amazon.ion.util.IonTextUtils;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.NoSuchElementException;

final class IonSystemLite
extends ValueFactoryLite
implements _Private_IonSystem {
    private final SymbolTable _system_symbol_table;
    private final IonCatalog _catalog;
    private final IonLoader _loader;
    private final IonTextWriterBuilder myTextWriterBuilder;
    private final _Private_IonBinaryWriterBuilder myBinaryWriterBuilder;
    private final IonReaderBuilder myReaderBuilder;

    public IonSystemLite(IonTextWriterBuilder twb, _Private_IonBinaryWriterBuilder bwb, IonReaderBuilder rb) {
        IonCatalog catalog = twb.getCatalog();
        assert (catalog != null);
        assert (catalog == bwb.getCatalog());
        assert (catalog == rb.getCatalog());
        this._catalog = catalog;
        this.myReaderBuilder = ((_Private_IonReaderBuilder)rb).withLstFactory(this._lstFactory).immutable();
        this._loader = new IonLoaderLite(this, catalog);
        this._system_symbol_table = bwb.getInitialSymbolTable();
        assert (this._system_symbol_table.isSystemTable());
        this.myTextWriterBuilder = twb.immutable();
        this.set_system(this);
        bwb.setSymtabValueFactory(this);
        this.myBinaryWriterBuilder = bwb.immutable();
    }

    IonReaderBuilder getReaderBuilder() {
        return this.myReaderBuilder;
    }

    @Override
    public boolean isStreamCopyOptimized() {
        return this.myBinaryWriterBuilder.isStreamCopyOptimized();
    }

    @Override
    public <T extends IonValue> T clone(T value) throws IonException {
        if (value.getSystem() == this) {
            return (T)value.clone();
        }
        if (value instanceof IonDatagram) {
            IonDatagram datagram = this.newDatagram();
            IonWriter writer = _Private_IonWriterFactory.makeWriter(datagram);
            IonReader reader = _Private_IonReaderFactory.makeSystemReaderText(value.getSystem(), value);
            try {
                writer.writeValues(reader);
            } catch (IOException e) {
                throw new IonException(e);
            }
            return (T)datagram;
        }
        IonReader reader = this.newReader(value);
        reader.next();
        return (T)this.newValue(reader);
    }

    @Override
    public IonCatalog getCatalog() {
        return this._catalog;
    }

    @Override
    public IonLoader getLoader() {
        return this._loader;
    }

    @Override
    public IonLoader newLoader() {
        return new IonLoaderLite(this, this._catalog);
    }

    @Override
    public IonLoader newLoader(IonCatalog catalog) {
        if (catalog == null) {
            catalog = this.getCatalog();
        }
        return new IonLoaderLite(this, catalog);
    }

    @Override
    public final SymbolTable getSystemSymbolTable() {
        return this._system_symbol_table;
    }

    @Override
    public SymbolTable getSystemSymbolTable(String ionVersionId) throws UnsupportedIonVersionException {
        if (!"$ion_1_0".equals(ionVersionId)) {
            throw new UnsupportedIonVersionException(ionVersionId);
        }
        return this.getSystemSymbolTable();
    }

    @Override
    public Iterator<IonValue> iterate(Reader ionText) {
        IonReader reader = this.myReaderBuilder.build(ionText);
        ReaderIterator iterator2 = new ReaderIterator(this, reader);
        return iterator2;
    }

    @Override
    public Iterator<IonValue> iterate(InputStream ionData) {
        IonReader reader = this.myReaderBuilder.build(ionData);
        return this.iterate(reader);
    }

    @Override
    public Iterator<IonValue> iterate(String ionText) {
        IonTextReader reader = this.myReaderBuilder.build(ionText);
        ReaderIterator iterator2 = new ReaderIterator(this, reader);
        return iterator2;
    }

    @Override
    public Iterator<IonValue> iterate(byte[] ionData) {
        IonReader reader = this.myReaderBuilder.build(ionData);
        return this.iterate(reader);
    }

    @Override
    public Iterator<IonValue> iterate(IonReader reader) {
        ReaderIterator iterator2 = new ReaderIterator(this, reader);
        return iterator2;
    }

    @Override
    @Deprecated
    public IonBinaryWriter newBinaryWriter() {
        _Private_IonBinaryWriterBuilder b = this.myBinaryWriterBuilder;
        return b.buildLegacy();
    }

    @Override
    @Deprecated
    public IonBinaryWriter newBinaryWriter(SymbolTable ... imports) {
        _Private_IonBinaryWriterBuilder b = (_Private_IonBinaryWriterBuilder)this.myBinaryWriterBuilder.withImports(imports);
        return b.buildLegacy();
    }

    @Override
    public IonWriter newBinaryWriter(OutputStream out, SymbolTable ... imports) {
        return this.myBinaryWriterBuilder.withImports(imports).build(out);
    }

    @Override
    public IonWriter newTextWriter(Appendable out) {
        return this.myTextWriterBuilder.build(out);
    }

    @Override
    public IonWriter newTextWriter(Appendable out, SymbolTable ... imports) throws IOException {
        return this.myTextWriterBuilder.withImports(imports).build(out);
    }

    @Override
    public IonWriter newTextWriter(OutputStream out) {
        return this.myTextWriterBuilder.build(out);
    }

    @Override
    public IonWriter newTextWriter(OutputStream out, SymbolTable ... imports) throws IOException {
        return this.myTextWriterBuilder.withImports(imports).build(out);
    }

    @Override
    public SymbolTable newLocalSymbolTable(SymbolTable ... imports) {
        return this._lstFactory.newLocalSymtab(this.getSystemSymbolTable(), imports);
    }

    @Override
    public SymbolTable newSharedSymbolTable(IonStruct ionRep) {
        return _Private_Utils.newSharedSymtab(ionRep);
    }

    @Override
    public SymbolTable newSharedSymbolTable(IonReader reader) {
        return _Private_Utils.newSharedSymtab(reader, false);
    }

    @Override
    public SymbolTable newSharedSymbolTable(IonReader reader, boolean isOnStruct) {
        return _Private_Utils.newSharedSymtab(reader, isOnStruct);
    }

    @Override
    public SymbolTable newSharedSymbolTable(String name, int version, Iterator<String> newSymbols, SymbolTable ... imports) {
        int priorVersion;
        ArrayList syms = new ArrayList();
        SymbolTable prior = null;
        if (version > 1 && ((prior = this._catalog.getTable(name, priorVersion = version - 1)) == null || prior.getVersion() != priorVersion)) {
            String message = "Catalog does not contain symbol table " + IonTextUtils.printString(name) + " version " + priorVersion + " required to create version " + version;
            throw new IonException(message);
        }
        for (SymbolTable imported : imports) {
            _Private_Utils.addAllNonNull(syms, imported.iterateDeclaredSymbolNames());
        }
        _Private_Utils.addAllNonNull(syms, newSymbols);
        SymbolTable st = _Private_Utils.newSharedSymtab(name, version, prior, syms.iterator());
        return st;
    }

    @Override
    public IonValueLite newValue(IonReader reader) {
        IonValueLite value = this.load_value_helper(reader);
        if (value == null) {
            throw new IonException("No value available");
        }
        return value;
    }

    private IonValueLite load_value_helper(IonReader reader) {
        return new ValueLoader().load(reader);
    }

    IonValueLite newValue(IonType valueType) {
        IonValueLite v;
        if (valueType == null) {
            throw new IllegalArgumentException("the value type must be specified");
        }
        switch (valueType) {
            case NULL: {
                v = this.newNull();
                break;
            }
            case BOOL: {
                v = this.newNullBool();
                break;
            }
            case INT: {
                v = this.newNullInt();
                break;
            }
            case FLOAT: {
                v = this.newNullFloat();
                break;
            }
            case DECIMAL: {
                v = this.newNullDecimal();
                break;
            }
            case TIMESTAMP: {
                v = this.newNullTimestamp();
                break;
            }
            case SYMBOL: {
                v = this.newNullSymbol();
                break;
            }
            case STRING: {
                v = this.newNullString();
                break;
            }
            case CLOB: {
                v = this.newNullClob();
                break;
            }
            case BLOB: {
                v = this.newNullBlob();
                break;
            }
            case LIST: {
                v = this.newEmptyList();
                break;
            }
            case SEXP: {
                v = this.newEmptySexp();
                break;
            }
            case STRUCT: {
                v = this.newEmptyStruct();
                break;
            }
            default: {
                throw new IonException("unexpected type encountered reading value: " + (Object)((Object)valueType));
            }
        }
        return v;
    }

    @Override
    public IonWriter newWriter(IonContainer container) {
        IonWriter writer = _Private_IonWriterFactory.makeWriter(container);
        return writer;
    }

    private IonValue singleValue(Iterator<IonValue> it) {
        IonValue value;
        try {
            value = it.next();
        } catch (NoSuchElementException e) {
            throw new UnexpectedEofException("no value found on input stream");
        }
        if (it.hasNext()) {
            throw new IonException("not a single value");
        }
        return value;
    }

    @Override
    public IonValue singleValue(String ionText) {
        Iterator<IonValue> it = this.iterate(ionText);
        return this.singleValue(it);
    }

    @Override
    public IonValue singleValue(byte[] ionData) {
        return this.singleValue(ionData, 0, ionData.length);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public IonValue singleValue(byte[] ionData, int offset, int len) {
        IonReader reader = this.newReader(ionData, offset, len);
        try {
            Iterator<IonValue> it = this.iterate(reader);
            IonValue ionValue2 = this.singleValue(it);
            return ionValue2;
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                throw new IonException(e);
            }
        }
    }

    protected IonSymbolLite newSystemIdSymbol(String ionVersionMarker) {
        if (!"$ion_1_0".equals(ionVersionMarker)) {
            throw new IllegalArgumentException("name isn't an ion version marker");
        }
        IonSymbolLite ivm = this.newSymbol(ionVersionMarker);
        ivm.setIsIonVersionMarker(true);
        return ivm;
    }

    @Override
    public IonTimestamp newUtcTimestampFromMillis(long millis) {
        IonTimestampLite result = this.newNullTimestamp();
        result.setMillisUtc(millis);
        return result;
    }

    @Override
    public IonTimestamp newUtcTimestamp(Date utcDate) {
        IonTimestampLite result = this.newNullTimestamp();
        if (utcDate != null) {
            result.setMillisUtc(utcDate.getTime());
        }
        return result;
    }

    @Override
    public IonTimestamp newCurrentUtcTimestamp() {
        IonTimestampLite result = super.newNullTimestamp();
        result.setCurrentTimeUtc();
        return result;
    }

    @Override
    public IonDatagram newDatagram() {
        IonCatalog catalog = this.getCatalog();
        IonDatagramLite dg = this.newDatagram(catalog);
        return dg;
    }

    public IonDatagramLite newDatagram(IonCatalog catalog) {
        if (catalog == null) {
            catalog = this.getCatalog();
        }
        IonDatagramLite dg = new IonDatagramLite(this, catalog);
        return dg;
    }

    @Override
    public IonDatagram newDatagram(IonValue initialChild) {
        IonDatagram dg = this.newDatagram(null, initialChild);
        return dg;
    }

    public IonDatagram newDatagram(IonCatalog catalog, IonValue initialChild) {
        IonDatagramLite dg = this.newDatagram(catalog);
        if (initialChild != null) {
            if (initialChild.getSystem() != this) {
                throw new IonException("this Ion system can't mix with instances from other system impl's");
            }
            if (initialChild.getContainer() != null) {
                initialChild = this.clone(initialChild);
            }
            dg.add(initialChild);
        }
        assert (dg.getSystem() == this);
        return dg;
    }

    @Override
    public IonDatagram newDatagram(SymbolTable ... imports) {
        IonDatagram dg = this.newDatagram((IonCatalog)null, imports);
        return dg;
    }

    public IonDatagram newDatagram(IonCatalog catalog, SymbolTable ... imports) {
        SymbolTable defaultSystemSymtab = this.getSystemSymbolTable();
        SymbolTable symbols = _Private_Utils.initialSymtab(this._lstFactory, defaultSystemSymtab, imports);
        IonDatagramLite dg = this.newDatagram(catalog);
        dg.appendTrailingSymbolTable(symbols);
        return dg;
    }

    @Override
    public IonReader newReader(byte[] ionData) {
        return this.myReaderBuilder.build(ionData);
    }

    @Override
    public IonReader newSystemReader(byte[] ionData) {
        return _Private_IonReaderFactory.makeSystemReader(ionData);
    }

    @Override
    public IonReader newReader(byte[] ionData, int offset, int len) {
        return this.myReaderBuilder.build(ionData, offset, len);
    }

    @Override
    public IonReader newSystemReader(byte[] ionData, int offset, int len) {
        return _Private_IonReaderFactory.makeSystemReader(ionData, offset, len);
    }

    @Override
    public IonTextReader newReader(String ionText) {
        return this.myReaderBuilder.build(ionText);
    }

    @Override
    public IonReader newSystemReader(String ionText) {
        return _Private_IonReaderFactory.makeSystemReaderText(ionText);
    }

    @Override
    public IonReader newReader(InputStream ionData) {
        return this.myReaderBuilder.build(ionData);
    }

    @Override
    public IonReader newSystemReader(InputStream ionData) {
        return _Private_IonReaderFactory.makeSystemReaderText(ionData);
    }

    @Override
    public IonReader newReader(Reader ionText) {
        return this.myReaderBuilder.build(ionText);
    }

    @Override
    public IonReader newSystemReader(Reader ionText) {
        return _Private_IonReaderFactory.makeSystemReaderText(ionText);
    }

    @Override
    public IonReader newReader(IonValue value) {
        return this.myReaderBuilder.build(value);
    }

    @Override
    public IonReader newSystemReader(IonValue value) {
        return _Private_IonReaderFactory.makeSystemReaderText(this, value);
    }

    @Override
    public IonWriter newTreeSystemWriter(IonContainer container) {
        IonWriter writer = _Private_IonWriterFactory.makeSystemWriter(container);
        return writer;
    }

    @Override
    public IonWriter newTreeWriter(IonContainer container) {
        IonWriter writer = _Private_IonWriterFactory.makeWriter(container);
        return writer;
    }

    @Override
    public Iterator<IonValue> systemIterate(Reader ionText) {
        IonReader ir = this.newSystemReader(ionText);
        return _Private_Utils.iterate(this, ir);
    }

    @Override
    public Iterator<IonValue> systemIterate(String ionText) {
        IonReader ir = this.newSystemReader(ionText);
        return _Private_Utils.iterate(this, ir);
    }

    @Override
    public Iterator<IonValue> systemIterate(IonReader reader) {
        return _Private_Utils.iterate(this, reader);
    }

    @Override
    public boolean valueIsSharedSymbolTable(IonValue value) {
        return value instanceof IonStruct && value.hasTypeAnnotation("$ion_symbol_table");
    }

    private class ValueLoader {
        private static final int CONTAINER_STACK_INITIAL_CAPACITY = 16;
        private final ArrayList<IonContainerLite> containerStack = new ArrayList(16);
        private IonReader reader = null;

        private IonValueLite shallowLoadCurrentValue() {
            IonType ionType = this.reader.getType();
            if (this.reader.isNullValue()) {
                return IonSystemLite.this.newNull(ionType);
            }
            switch (ionType) {
                case BOOL: {
                    return IonSystemLite.this.newBool(this.reader.booleanValue());
                }
                case INT: {
                    if (this.reader.getIntegerSize().equals((Object)IntegerSize.BIG_INTEGER)) {
                        return IonSystemLite.this.newInt(this.reader.bigIntegerValue());
                    }
                    return IonSystemLite.this.newInt(this.reader.longValue());
                }
                case FLOAT: {
                    return IonSystemLite.this.newFloat(this.reader.doubleValue());
                }
                case DECIMAL: {
                    return IonSystemLite.this.newDecimal(this.reader.decimalValue());
                }
                case TIMESTAMP: {
                    return IonSystemLite.this.newTimestamp(this.reader.timestampValue());
                }
                case SYMBOL: {
                    return IonSystemLite.this.newSymbol(this.reader.symbolValue());
                }
                case STRING: {
                    return IonSystemLite.this.newString(this.reader.stringValue());
                }
                case CLOB: {
                    return IonSystemLite.this.newClob(this.reader.newBytes());
                }
                case BLOB: {
                    return IonSystemLite.this.newBlob(this.reader.newBytes());
                }
                case LIST: {
                    return IonSystemLite.this.newEmptyList();
                }
                case SEXP: {
                    return IonSystemLite.this.newEmptySexp();
                }
                case STRUCT: {
                    return IonSystemLite.this.newEmptyStruct();
                }
            }
            throw new IonException("unexpected type encountered reading value: " + (Object)((Object)ionType));
        }

        private boolean cloneFieldNameIfAny(IonValueLite value) {
            if (this.containerStack.isEmpty() || !this.reader.isInStruct()) {
                return false;
            }
            SymbolToken token = this.reader.getFieldNameSymbol();
            String text = token.getText();
            if (text != null && token.getSid() != -1) {
                token = _Private_Utils.newSymbolToken(text, -1);
            }
            value.setFieldNameSymbol(token);
            return true;
        }

        private boolean cloneAnnotationsIfAny(IonValueLite value) {
            SymbolToken[] annotations = this.reader.getTypeAnnotationSymbols();
            if (annotations.length == 0) {
                return false;
            }
            for (int i = 0; i < annotations.length; ++i) {
                SymbolToken token = annotations[i];
                String text = token.getText();
                if (text == null || token.getSid() == -1) continue;
                annotations[i] = _Private_Utils.newSymbolToken(text, -1);
            }
            value.setTypeAnnotationSymbols(annotations);
            return true;
        }

        private void attachToParent(IonValueLite value) {
            IonContainerLite parent = this.containerStack.get(this.containerStack.size() - 1);
            boolean childSymbolIsPresent = value._isSymbolPresent();
            boolean parentSymbolIsPresent = parent._isSymbolPresent();
            parent._isSymbolPresent(parentSymbolIsPresent | childSymbolIsPresent);
            parent.add(value);
        }

        public IonValueLite load(IonReader reader) {
            this.reader = reader;
            this.containerStack.clear();
            if (null == reader.getType()) {
                return null;
            }
            block0: while (true) {
                IonValueLite value = this.shallowLoadCurrentValue();
                boolean isSymbolPresent = value.getType().equals((Object)IonType.SYMBOL);
                isSymbolPresent |= this.cloneFieldNameIfAny(value);
                value._isSymbolPresent(isSymbolPresent |= this.cloneAnnotationsIfAny(value));
                if (!reader.isNullValue() && IonType.isContainer(reader.getType())) {
                    this.containerStack.add((IonContainerLite)value);
                    reader.stepIn();
                } else {
                    if (this.containerStack.isEmpty()) {
                        return value;
                    }
                    this.attachToParent(value);
                }
                while (true) {
                    if (this.containerStack.isEmpty() || null != reader.next()) continue block0;
                    IonContainerLite completedContainer = this.containerStack.remove(this.containerStack.size() - 1);
                    reader.stepOut();
                    if (this.containerStack.isEmpty()) {
                        return completedContainer;
                    }
                    this.attachToParent(completedContainer);
                }
                break;
            }
        }
    }

    static class ReaderIterator
    implements Closeable,
    Iterator<IonValue> {
        private final IonReader _reader;
        private final IonSystemLite _system;
        private IonType _next;

        protected ReaderIterator(IonSystemLite system, IonReader reader) {
            this._reader = reader;
            this._system = system;
        }

        @Override
        public boolean hasNext() {
            if (this._next == null) {
                this._next = this._reader.next();
            }
            return this._next != null;
        }

        @Override
        public IonValue next() {
            if (!this.hasNext()) {
                throw new NoSuchElementException();
            }
            SymbolTable symtab = this._reader.getSymbolTable();
            IonValueLite value = this._system.newValue(this._reader);
            this._next = null;
            value.setSymbolTable(symtab);
            return value;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

        @Override
        public void close() throws IOException {
        }
    }
}

