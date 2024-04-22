/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazon.ion.impl;

import com.amazon.ion.IonException;
import com.amazon.ion.IonType;
import com.amazon.ion.SymbolTable;
import com.amazon.ion.SymbolToken;
import com.amazon.ion.UnknownSymbolException;
import com.amazon.ion.impl.LocalSymbolTable;
import com.amazon.ion.impl.SymbolTokenImpl;
import com.amazon.ion.impl._Private_IonWriterBase;
import com.amazon.ion.impl._Private_Utils;
import com.amazon.ion.system.IonWriterBuilder;
import java.io.IOException;

abstract class IonWriterSystem
extends _Private_IonWriterBase {
    final SymbolTable _default_system_symbol_table;
    private IonWriterBuilder.InitialIvmHandling _initial_ivm_handling;
    private final IonWriterBuilder.IvmMinimizing _ivm_minimizing;
    private boolean _previous_value_was_ivm;
    private boolean _anything_written;
    private SymbolTable _symbol_table;
    private IonType _field_name_type;
    private String _field_name;
    private int _field_name_sid = -1;
    private static final int DEFAULT_ANNOTATION_COUNT = 4;
    private int _annotation_count;
    private SymbolToken[] _annotations = new SymbolToken[4];

    IonWriterSystem(SymbolTable defaultSystemSymbolTable, IonWriterBuilder.InitialIvmHandling initialIvmHandling, IonWriterBuilder.IvmMinimizing ivmMinimizing, boolean requireSymbolValidation) {
        super(requireSymbolValidation);
        defaultSystemSymbolTable.getClass();
        this._default_system_symbol_table = defaultSystemSymbolTable;
        this._symbol_table = defaultSystemSymbolTable;
        this._initial_ivm_handling = initialIvmHandling;
        this._ivm_minimizing = ivmMinimizing;
    }

    final SymbolTable getDefaultSystemSymtab() {
        return this._default_system_symbol_table;
    }

    @Override
    public final SymbolTable getSymbolTable() {
        return this._symbol_table;
    }

    @Override
    public final void setSymbolTable(SymbolTable symbols) throws IOException {
        if (symbols == null || _Private_Utils.symtabIsSharedNotSystem(symbols)) {
            throw new IllegalArgumentException("symbol table must be local or system to be set, or reset");
        }
        if (this.getDepth() > 0) {
            throw new IllegalStateException("the symbol table cannot be set, or reset, while a container is open");
        }
        this._symbol_table = symbols;
    }

    boolean shouldWriteIvm() {
        if (this._initial_ivm_handling == IonWriterBuilder.InitialIvmHandling.ENSURE) {
            return true;
        }
        if (this._initial_ivm_handling == IonWriterBuilder.InitialIvmHandling.SUPPRESS) {
            return false;
        }
        if (this._ivm_minimizing == IonWriterBuilder.IvmMinimizing.ADJACENT) {
            return !this._previous_value_was_ivm;
        }
        if (this._ivm_minimizing == IonWriterBuilder.IvmMinimizing.DISTANT) {
            return !this._anything_written;
        }
        return true;
    }

    final void writeIonVersionMarker(SymbolTable systemSymtab) throws IOException {
        if (this.getDepth() != 0) {
            String message = "Ion Version Markers are only valid at the top level of a data stream";
            throw new IllegalStateException(message);
        }
        assert (systemSymtab.isSystemTable());
        if (!"$ion_1_0".equals(systemSymtab.getIonVersionId())) {
            String message = "This library only supports Ion 1.0";
            throw new UnsupportedOperationException(message);
        }
        if (this.shouldWriteIvm()) {
            this._initial_ivm_handling = null;
            this.writeIonVersionMarkerAsIs(systemSymtab);
            this._previous_value_was_ivm = true;
        }
        this._symbol_table = systemSymtab;
    }

    abstract void writeIonVersionMarkerAsIs(SymbolTable var1) throws IOException;

    @Override
    public final void writeIonVersionMarker() throws IOException {
        this.writeIonVersionMarker(this._default_system_symbol_table);
    }

    void writeLocalSymtab(SymbolTable symtab) throws IOException {
        assert (symtab.isLocalTable());
        this._symbol_table = symtab;
    }

    SymbolTable inject_local_symbol_table() throws IOException {
        assert (this._symbol_table.isSystemTable());
        return LocalSymbolTable.DEFAULT_LST_FACTORY.newLocalSymtab(this._symbol_table, new SymbolTable[0]);
    }

    @Override
    final String assumeKnownSymbol(int sid) {
        String text = this._symbol_table.findKnownSymbol(sid);
        if (text == null) {
            throw new UnknownSymbolException(sid);
        }
        return text;
    }

    final int add_symbol(String name) throws IOException {
        int sid;
        if (this._symbol_table.isSystemTable()) {
            sid = this._symbol_table.findSymbol(name);
            if (sid != -1) {
                return sid;
            }
            this._symbol_table = this.inject_local_symbol_table();
        }
        assert (this._symbol_table.isLocalTable());
        sid = this._symbol_table.intern(name).getSid();
        return sid;
    }

    void startValue() throws IOException {
        if (this._initial_ivm_handling == IonWriterBuilder.InitialIvmHandling.ENSURE) {
            this.writeIonVersionMarker(this._default_system_symbol_table);
        }
    }

    void endValue() {
        this._initial_ivm_handling = null;
        this._previous_value_was_ivm = false;
        this._anything_written = true;
    }

    abstract void writeSymbolAsIs(int var1) throws IOException;

    abstract void writeSymbolAsIs(String var1) throws IOException;

    @Override
    final void writeSymbol(int symbolId) throws IOException {
        if (symbolId < 0) {
            throw new IllegalArgumentException("symbol IDs are >= 0.");
        }
        if (symbolId == 2 && this.getDepth() == 0 && this._annotation_count == 0) {
            this.writeIonVersionMarker();
        } else {
            this.writeSymbolAsIs(symbolId);
        }
    }

    @Override
    public final void writeSymbol(String value) throws IOException {
        if ("$ion_1_0".equals(value) && this.getDepth() == 0 && this._annotation_count == 0) {
            this.writeIonVersionMarker();
        } else {
            this.writeSymbolAsIs(value);
        }
    }

    @Override
    public void finish() throws IOException {
        if (this.getDepth() != 0) {
            throw new IllegalStateException("IonWriter.finish() can only be called at top-level.");
        }
        this.flush();
        this._previous_value_was_ivm = false;
        this._initial_ivm_handling = IonWriterBuilder.InitialIvmHandling.ENSURE;
        this._symbol_table = this._default_system_symbol_table;
    }

    @Override
    public final boolean isFieldNameSet() {
        if (this._field_name_type != null) {
            switch (this._field_name_type) {
                case STRING: {
                    return this._field_name != null;
                }
                case INT: {
                    return this._field_name_sid >= 0;
                }
            }
        }
        return false;
    }

    final void clearFieldName() {
        this._field_name_type = null;
        this._field_name = null;
        this._field_name_sid = -1;
    }

    @Override
    public final void setFieldName(String name) {
        if (!this.isInStruct()) {
            throw new IllegalStateException();
        }
        name.getClass();
        this._field_name_type = IonType.STRING;
        this._field_name = name;
        this._field_name_sid = -1;
    }

    @Override
    public final void setFieldNameSymbol(SymbolToken name) {
        if (!this.isInStruct()) {
            throw new IllegalStateException();
        }
        String text = name.getText();
        if (text != null) {
            this._field_name_type = IonType.STRING;
            this._field_name = text;
            this._field_name_sid = -1;
        } else {
            int sid = name.getSid();
            if (sid < 0) {
                throw new IllegalArgumentException();
            }
            this.validateSymbolId(sid);
            this._field_name_type = IonType.INT;
            this._field_name_sid = sid;
            this._field_name = null;
        }
    }

    final int getFieldId() {
        int id;
        if (this._field_name_type == null) {
            throw new IllegalStateException("the field has not be set");
        }
        switch (this._field_name_type) {
            case STRING: {
                try {
                    id = this.add_symbol(this._field_name);
                    break;
                } catch (IOException e) {
                    throw new IonException(e);
                }
            }
            case INT: {
                id = this._field_name_sid;
                break;
            }
            default: {
                throw new IllegalStateException("the field has not be set");
            }
        }
        return id;
    }

    final SymbolToken assumeFieldNameSymbol() {
        if (this._field_name_type == null) {
            throw new IllegalStateException("IonWriter.setFieldName() must be called before writing a value into a struct.");
        }
        assert (this._field_name != null ^ this._field_name_sid >= 0);
        return new SymbolTokenImpl(this._field_name, this._field_name_sid);
    }

    final void ensureAnnotationCapacity(int length) {
        int newlen;
        int oldlen;
        int n = oldlen = this._annotations == null ? 0 : this._annotations.length;
        if (length < oldlen) {
            return;
        }
        int n2 = newlen = this._annotations == null ? 10 : this._annotations.length * 2;
        if (length > newlen) {
            newlen = length;
        }
        SymbolToken[] temp1 = new SymbolToken[newlen];
        if (oldlen > 0) {
            System.arraycopy(this._annotations, 0, temp1, 0, oldlen);
        }
        this._annotations = temp1;
    }

    final int[] internAnnotationsAndGetSids() throws IOException {
        int count = this._annotation_count;
        if (count == 0) {
            return _Private_Utils.EMPTY_INT_ARRAY;
        }
        int[] sids = new int[count];
        for (int i = 0; i < count; ++i) {
            SymbolToken sym = this._annotations[i];
            int sid = sym.getSid();
            if (sid == -1) {
                String text = sym.getText();
                sid = this.add_symbol(text);
                this._annotations[i] = new SymbolTokenImpl(text, sid);
            }
            sids[i] = sid;
        }
        return sids;
    }

    final boolean hasAnnotations() {
        return this._annotation_count != 0;
    }

    final int annotationCount() {
        return this._annotation_count;
    }

    final void clearAnnotations() {
        this._annotation_count = 0;
    }

    @Override
    final int findAnnotation(String name) {
        if (this._annotation_count > 0) {
            for (int ii = 0; ii < this._annotation_count; ++ii) {
                if (!name.equals(this._annotations[ii].getText())) continue;
                return ii;
            }
        }
        return -1;
    }

    final SymbolToken[] getTypeAnnotationSymbols() {
        int count = this._annotation_count;
        if (count == 0) {
            return SymbolToken.EMPTY_ARRAY;
        }
        SymbolToken[] syms = new SymbolToken[count];
        System.arraycopy(this._annotations, 0, syms, 0, count);
        return syms;
    }

    @Override
    public final void setTypeAnnotationSymbols(SymbolToken ... annotations) {
        if (annotations == null || annotations.length == 0) {
            this._annotation_count = 0;
        } else {
            SymbolToken sym;
            int count;
            int annotationsArrayLength = annotations.length;
            this.ensureAnnotationCapacity(annotationsArrayLength);
            SymbolTable symtab = this.getSymbolTable();
            for (count = 0; count < annotationsArrayLength && (sym = annotations[count]) != null; ++count) {
                if (sym.getText() == null) {
                    this.validateSymbolId(sym.getSid());
                }
                this._annotations[count] = sym = _Private_Utils.localize(symtab, sym);
            }
            this._annotation_count = count;
        }
    }

    @Override
    final String[] getTypeAnnotations() {
        return _Private_Utils.toStrings(this._annotations, this._annotation_count);
    }

    @Override
    public final void setTypeAnnotations(String ... annotations) {
        if (annotations == null || annotations.length == 0) {
            this._annotation_count = 0;
        } else {
            SymbolToken[] syms = _Private_Utils.newSymbolTokens(this.getSymbolTable(), annotations);
            int count = syms.length;
            this.ensureAnnotationCapacity(count);
            System.arraycopy(syms, 0, this._annotations, 0, count);
            this._annotation_count = count;
        }
    }

    @Override
    public final void addTypeAnnotation(String annotation) {
        SymbolToken is = _Private_Utils.newSymbolToken(this.getSymbolTable(), annotation);
        this.ensureAnnotationCapacity(this._annotation_count + 1);
        this._annotations[this._annotation_count++] = is;
    }

    @Override
    final int[] getTypeAnnotationIds() {
        return _Private_Utils.toSids(this._annotations, this._annotation_count);
    }

    @Override
    public <T> T asFacet(Class<T> facetType) {
        return null;
    }
}

