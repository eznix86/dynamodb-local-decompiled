/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazon.ion.impl;

import com.amazon.ion.IonReader;
import com.amazon.ion.IonType;
import com.amazon.ion.IonValue;
import com.amazon.ion.IonWriter;
import com.amazon.ion.SymbolTable;
import com.amazon.ion.SymbolToken;
import com.amazon.ion.Timestamp;
import com.amazon.ion.UnknownSymbolException;
import com.amazon.ion.impl._Private_ReaderWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;

public abstract class _Private_IonWriterBase
implements IonWriter,
_Private_ReaderWriter {
    protected static final String ERROR_MISSING_FIELD_NAME = "IonWriter.setFieldName() must be called before writing a value into a struct.";
    static final String ERROR_FINISH_NOT_AT_TOP_LEVEL = "IonWriter.finish() can only be called at top-level.";
    private final boolean requireSymbolValidation;
    private int _symbol_table_top = 0;
    private SymbolTable[] _symbol_table_stack = new SymbolTable[3];

    public _Private_IonWriterBase(boolean requireSymbolValidation) {
        this.requireSymbolValidation = requireSymbolValidation;
    }

    protected abstract int getDepth();

    abstract void writeIonVersionMarker() throws IOException;

    public abstract void setSymbolTable(SymbolTable var1) throws IOException;

    abstract String assumeKnownSymbol(int var1);

    public abstract boolean isFieldNameSet();

    abstract int findAnnotation(String var1);

    abstract String[] getTypeAnnotations();

    abstract int[] getTypeAnnotationIds();

    abstract void writeSymbol(int var1) throws IOException;

    @Override
    public void writeBlob(byte[] value) throws IOException {
        if (value == null) {
            this.writeNull(IonType.BLOB);
        } else {
            this.writeBlob(value, 0, value.length);
        }
    }

    @Override
    public void writeClob(byte[] value) throws IOException {
        if (value == null) {
            this.writeNull(IonType.CLOB);
        } else {
            this.writeClob(value, 0, value.length);
        }
    }

    @Override
    public abstract void writeDecimal(BigDecimal var1) throws IOException;

    public void writeFloat(float value) throws IOException {
        this.writeFloat((double)value);
    }

    @Override
    public void writeNull() throws IOException {
        this.writeNull(IonType.NULL);
    }

    final void validateSymbolId(int sid) {
        if (this.requireSymbolValidation && sid > this.getSymbolTable().getMaxId()) {
            throw new UnknownSymbolException(sid);
        }
    }

    @Override
    public final void writeSymbolToken(SymbolToken tok) throws IOException {
        if (tok == null) {
            this.writeNull(IonType.SYMBOL);
            return;
        }
        String text = tok.getText();
        if (text != null) {
            this.writeSymbol(text);
        } else {
            int sid = tok.getSid();
            this.validateSymbolId(sid);
            this.writeSymbol(sid);
        }
    }

    @Override
    public void writeTimestampUTC(Date value) throws IOException {
        Timestamp time = Timestamp.forDateZ(value);
        this.writeTimestamp(time);
    }

    @Override
    @Deprecated
    public void writeValue(IonValue value) throws IOException {
        if (value != null) {
            value.writeTo(this);
        }
    }

    @Override
    public void writeValues(IonReader reader) throws IOException {
        if (reader.getDepth() == 0) {
            this.clear_system_value_stack();
        }
        if (reader.getType() == null) {
            reader.next();
        }
        if (this.getDepth() == 0 && reader instanceof _Private_ReaderWriter) {
            _Private_ReaderWriter private_reader = (_Private_ReaderWriter)((Object)reader);
            while (reader.getType() != null) {
                this.transfer_symbol_tables(private_reader);
                this.writeValue(reader);
                reader.next();
            }
        } else {
            while (reader.getType() != null) {
                this.writeValue(reader);
                reader.next();
            }
        }
    }

    private final void transfer_symbol_tables(_Private_ReaderWriter reader) throws IOException {
        SymbolTable reader_symbols = reader.pop_passed_symbol_table();
        if (reader_symbols != null) {
            this.clear_system_value_stack();
            this.setSymbolTable(reader_symbols);
            while (reader_symbols != null) {
                this.push_symbol_table(reader_symbols);
                reader_symbols = reader.pop_passed_symbol_table();
            }
        }
    }

    private final void write_value_field_name_helper(IonReader reader) {
        if (this.isInStruct() && !this.isFieldNameSet()) {
            SymbolToken tok = reader.getFieldNameSymbol();
            if (tok == null) {
                throw new IllegalStateException("Field name not set");
            }
            this.setFieldNameSymbol(tok);
        }
    }

    private final void write_value_annotations_helper(IonReader reader) {
        SymbolToken[] a = reader.getTypeAnnotationSymbols();
        this.setTypeAnnotationSymbols(a);
    }

    public boolean isStreamCopyOptimized() {
        return false;
    }

    @Override
    public void writeValue(IonReader reader) throws IOException {
        this.writeValueRecursively(reader);
    }

    final void writeValueRecursively(IonReader reader) throws IOException {
        block19: {
            IonType type;
            int startingDepth = this.getDepth();
            boolean alreadyProcessedTheStartingValue = false;
            block13: while (true) {
                if (this.getDepth() == startingDepth) {
                    if (alreadyProcessedTheStartingValue) break block19;
                    type = reader.getType();
                    alreadyProcessedTheStartingValue = true;
                } else {
                    type = reader.next();
                }
                if (type == null) {
                    if (this.getDepth() != startingDepth) {
                        reader.stepOut();
                        this.stepOut();
                        continue;
                    }
                    break block19;
                }
                this.write_value_field_name_helper(reader);
                this.write_value_annotations_helper(reader);
                if (reader.isNullValue()) {
                    this.writeNull(type);
                    continue;
                }
                switch (type) {
                    case NULL: {
                        throw new IllegalStateException("isNullValue() was false but IonType was NULL.");
                    }
                    case BOOL: {
                        this.writeBool(reader.booleanValue());
                        continue block13;
                    }
                    case INT: {
                        this.writeInt(reader.bigIntegerValue());
                        continue block13;
                    }
                    case FLOAT: {
                        this.writeFloat(reader.doubleValue());
                        continue block13;
                    }
                    case DECIMAL: {
                        this.writeDecimal(reader.decimalValue());
                        continue block13;
                    }
                    case TIMESTAMP: {
                        this.writeTimestamp(reader.timestampValue());
                        continue block13;
                    }
                    case STRING: {
                        this.writeString(reader.stringValue());
                        continue block13;
                    }
                    case SYMBOL: {
                        this.writeSymbolToken(reader.symbolValue());
                        continue block13;
                    }
                    case BLOB: {
                        this.writeBlob(reader.newBytes());
                        continue block13;
                    }
                    case CLOB: {
                        this.writeClob(reader.newBytes());
                        continue block13;
                    }
                    case STRUCT: 
                    case LIST: 
                    case SEXP: {
                        reader.stepIn();
                        this.stepIn(type);
                        continue block13;
                    }
                }
                break;
            }
            throw new IllegalStateException("Unknown value type: " + (Object)((Object)type));
        }
    }

    private void clear_system_value_stack() {
        while (this._symbol_table_top > 0) {
            --this._symbol_table_top;
            this._symbol_table_stack[this._symbol_table_top] = null;
        }
    }

    private void push_symbol_table(SymbolTable symbols) {
        assert (symbols != null);
        if (this._symbol_table_top >= this._symbol_table_stack.length) {
            int new_len = this._symbol_table_stack.length * 2;
            SymbolTable[] temp = new SymbolTable[new_len];
            System.arraycopy(this._symbol_table_stack, 0, temp, 0, this._symbol_table_stack.length);
            this._symbol_table_stack = temp;
        }
        this._symbol_table_stack[this._symbol_table_top++] = symbols;
    }

    @Override
    public final SymbolTable pop_passed_symbol_table() {
        if (this._symbol_table_top <= 0) {
            return null;
        }
        --this._symbol_table_top;
        SymbolTable symbols = this._symbol_table_stack[this._symbol_table_top];
        this._symbol_table_stack[this._symbol_table_top] = null;
        return symbols;
    }

    @Override
    public <T> T asFacet(Class<T> facetType) {
        return null;
    }
}

