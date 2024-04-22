/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazon.ion.impl;

import com.amazon.ion.IonCatalog;
import com.amazon.ion.IonException;
import com.amazon.ion.IonStruct;
import com.amazon.ion.IonType;
import com.amazon.ion.SymbolTable;
import com.amazon.ion.SymbolToken;
import com.amazon.ion.Timestamp;
import com.amazon.ion.ValueFactory;
import com.amazon.ion.impl.IonWriterSystem;
import com.amazon.ion.impl.IonWriterSystemTree;
import com.amazon.ion.impl.LocalSymbolTableAsStruct;
import com.amazon.ion.impl._Private_IonWriter;
import com.amazon.ion.impl._Private_IonWriterBase;
import com.amazon.ion.impl._Private_Utils;
import com.amazon.ion.impl._Private_ValueFactory;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;

class IonWriterUser
extends _Private_IonWriterBase
implements _Private_IonWriter {
    private final ValueFactory _symtab_value_factory;
    private final IonCatalog _catalog;
    final IonWriterSystem _system_writer;
    IonWriterSystem _current_writer;
    private IonStruct _symbol_table_value;

    IonWriterUser(IonCatalog catalog, ValueFactory symtabValueFactory, IonWriterSystem systemWriter, boolean requireSymbolValidation) {
        super(requireSymbolValidation);
        this._symtab_value_factory = symtabValueFactory;
        this._catalog = catalog;
        assert (systemWriter != null);
        this._system_writer = systemWriter;
        this._current_writer = systemWriter;
    }

    IonWriterUser(IonCatalog catalog, ValueFactory symtabValueFactory, IonWriterSystem systemWriter, SymbolTable symtab, boolean requireSymbolValidation) {
        this(catalog, symtabValueFactory, systemWriter, requireSymbolValidation);
        SymbolTable defaultSystemSymtab = systemWriter.getDefaultSystemSymtab();
        if (symtab.isLocalTable() || symtab != defaultSystemSymtab) {
            try {
                this.setSymbolTable(symtab);
            } catch (IOException e) {
                throw new IonException(e);
            }
        }
        assert (this._system_writer == this._current_writer && this._system_writer == systemWriter);
    }

    @Override
    public IonCatalog getCatalog() {
        return this._catalog;
    }

    @Override
    int findAnnotation(String name) {
        return this._current_writer.findAnnotation(name);
    }

    @Override
    public int getDepth() {
        return this._current_writer.getDepth();
    }

    @Override
    public boolean isInStruct() {
        return this._current_writer.isInStruct();
    }

    @Override
    public void flush() throws IOException {
        this._current_writer.flush();
    }

    @Override
    public void close() throws IOException {
        try {
            try {
                if (this.getDepth() == 0) {
                    assert (this._current_writer == this._system_writer);
                    this.finish();
                }
            } finally {
                this._current_writer.close();
            }
        } finally {
            this._system_writer.close();
        }
    }

    @Override
    public final void finish() throws IOException {
        if (this.symbol_table_being_collected()) {
            throw new IllegalStateException("IonWriter.finish() can only be called at top-level.");
        }
        this._system_writer.finish();
    }

    SymbolTable activeSystemSymbolTable() {
        return this.getSymbolTable().getSystemSymbolTable();
    }

    private boolean symbol_table_being_collected() {
        return this._current_writer != this._system_writer;
    }

    private void open_local_symbol_table_copy() {
        assert (!this.symbol_table_being_collected());
        this._symbol_table_value = this._symtab_value_factory.newEmptyStruct();
        SymbolToken[] anns = this._system_writer.getTypeAnnotationSymbols();
        this._system_writer.clearAnnotations();
        this._symbol_table_value.setTypeAnnotationSymbols(anns);
        this._current_writer = new IonWriterSystemTree(this.activeSystemSymbolTable(), this._catalog, this._symbol_table_value, null);
    }

    private void close_local_symbol_table_copy() throws IOException {
        assert (this.symbol_table_being_collected());
        LocalSymbolTableAsStruct.Factory lstFactory = (LocalSymbolTableAsStruct.Factory)((_Private_ValueFactory)this._symtab_value_factory).getLstFactory();
        SymbolTable symtab = lstFactory.newLocalSymtab(this._catalog, this._symbol_table_value);
        this._symbol_table_value = null;
        this._current_writer = this._system_writer;
        this.setSymbolTable(symtab);
    }

    @Override
    public final void setSymbolTable(SymbolTable symbols) throws IOException {
        if (symbols == null || _Private_Utils.symtabIsSharedNotSystem(symbols)) {
            String message = "symbol table must be local or system to be set, or reset";
            throw new IllegalArgumentException(message);
        }
        if (this.getDepth() > 0) {
            String message = "the symbol table cannot be set, or reset, while a container is open";
            throw new IllegalStateException(message);
        }
        if (symbols.isSystemTable()) {
            this.writeIonVersionMarker(symbols);
        } else {
            this._system_writer.writeLocalSymtab(symbols);
        }
    }

    @Override
    public final SymbolTable getSymbolTable() {
        SymbolTable symbols = this._system_writer.getSymbolTable();
        return symbols;
    }

    @Override
    final String assumeKnownSymbol(int sid) {
        return this._system_writer.assumeKnownSymbol(sid);
    }

    @Override
    public final void setFieldName(String name) {
        this._current_writer.setFieldName(name);
    }

    @Override
    public final void setFieldNameSymbol(SymbolToken name) {
        this._current_writer.setFieldNameSymbol(name);
    }

    @Override
    public final boolean isFieldNameSet() {
        return this._current_writer.isFieldNameSet();
    }

    @Override
    public void addTypeAnnotation(String annotation) {
        this._current_writer.addTypeAnnotation(annotation);
    }

    @Override
    public void setTypeAnnotations(String ... annotations) {
        this._current_writer.setTypeAnnotations(annotations);
    }

    @Override
    public void setTypeAnnotationSymbols(SymbolToken ... annotations) {
        this._current_writer.setTypeAnnotationSymbols(annotations);
    }

    @Override
    String[] getTypeAnnotations() {
        return this._current_writer.getTypeAnnotations();
    }

    @Override
    int[] getTypeAnnotationIds() {
        return this._current_writer.getTypeAnnotationIds();
    }

    final SymbolToken[] getTypeAnnotationSymbols() {
        return this._current_writer.getTypeAnnotationSymbols();
    }

    @Override
    public void stepIn(IonType containerType) throws IOException {
        if (containerType == IonType.STRUCT && this._current_writer.getDepth() == 0 && this.findAnnotation("$ion_symbol_table") == 0) {
            this.open_local_symbol_table_copy();
        } else {
            this._current_writer.stepIn(containerType);
        }
    }

    @Override
    public void stepOut() throws IOException {
        if (this.symbol_table_being_collected() && this._current_writer.getDepth() == 1) {
            this.close_local_symbol_table_copy();
        } else {
            this._current_writer.stepOut();
        }
    }

    @Override
    public void writeBlob(byte[] value, int start, int len) throws IOException {
        this._current_writer.writeBlob(value, start, len);
    }

    @Override
    public void writeBool(boolean value) throws IOException {
        this._current_writer.writeBool(value);
    }

    @Override
    public void writeClob(byte[] value, int start, int len) throws IOException {
        this._current_writer.writeClob(value, start, len);
    }

    @Override
    public void writeDecimal(BigDecimal value) throws IOException {
        this._current_writer.writeDecimal(value);
    }

    @Override
    public void writeFloat(double value) throws IOException {
        this._current_writer.writeFloat(value);
    }

    public void writeInt(int value) throws IOException {
        this._current_writer.writeInt(value);
    }

    @Override
    public void writeInt(long value) throws IOException {
        this._current_writer.writeInt(value);
    }

    @Override
    public void writeInt(BigInteger value) throws IOException {
        this._current_writer.writeInt(value);
    }

    @Override
    public void writeNull(IonType type) throws IOException {
        this._current_writer.writeNull(type);
    }

    @Override
    public void writeString(String value) throws IOException {
        this._current_writer.writeString(value);
    }

    @Override
    final void writeSymbol(int symbolId) throws IOException {
        this._current_writer.writeSymbol(symbolId);
    }

    @Override
    public final void writeSymbol(String value) throws IOException {
        this._current_writer.writeSymbol(value);
    }

    final void writeIonVersionMarker(SymbolTable systemSymtab) throws IOException {
        this._current_writer.writeIonVersionMarker(systemSymtab);
    }

    @Override
    public final void writeIonVersionMarker() throws IOException {
        this._current_writer.writeIonVersionMarker();
    }

    @Override
    public void writeTimestamp(Timestamp value) throws IOException {
        this._current_writer.writeTimestamp(value);
    }
}

