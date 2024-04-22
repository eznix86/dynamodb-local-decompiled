/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazon.ion.impl;

import com.amazon.ion.IonException;
import com.amazon.ion.IonType;
import com.amazon.ion.SymbolTable;
import com.amazon.ion.SymbolToken;
import com.amazon.ion.Timestamp;
import com.amazon.ion.impl.IonWriterSystem;
import com.amazon.ion.impl.SymbolTableReader;
import com.amazon.ion.impl._Private_IonTextAppender;
import com.amazon.ion.impl._Private_IonTextWriterBuilder;
import com.amazon.ion.system.IonTextWriterBuilder;
import com.amazon.ion.util.IonTextUtils;
import com.amazon.ion.util._Private_FastAppendable;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;

class IonWriterSystemText
extends IonWriterSystem {
    private final _Private_IonTextWriterBuilder _options;
    private final int _long_string_threshold;
    private final _Private_IonTextAppender _output;
    private boolean _closed;
    boolean _in_struct;
    boolean _pending_separator;
    private boolean _is_writing_ivm;
    private boolean _following_long_string;
    CharSequence _separator_character;
    int _top;
    int[] _stack_parent_type = new int[10];
    boolean[] _stack_pending_comma = new boolean[10];

    protected IonWriterSystemText(SymbolTable defaultSystemSymtab, _Private_IonTextWriterBuilder options, _Private_FastAppendable out) {
        super(defaultSystemSymtab, options.getInitialIvmHandling(), options.getIvmMinimizing(), !options._allow_invalid_sids);
        this._output = _Private_IonTextAppender.forFastAppendable(out, options.getCharset());
        this._options = options;
        this._separator_character = this._options.topLevelSeparator();
        int threshold = this._options.getLongStringThreshold();
        if (threshold < 1) {
            threshold = Integer.MAX_VALUE;
        }
        this._long_string_threshold = threshold;
    }

    _Private_IonTextWriterBuilder getBuilder() {
        return this._options;
    }

    @Override
    public int getDepth() {
        return this._top;
    }

    @Override
    public boolean isInStruct() {
        return this._in_struct;
    }

    protected IonType getContainer() {
        IonType container;
        if (this._top < 1) {
            container = IonType.DATAGRAM;
        } else {
            switch (this._stack_parent_type[this._top - 1]) {
                case 16: {
                    container = IonType.DATAGRAM;
                    break;
                }
                case 12: {
                    container = IonType.SEXP;
                    break;
                }
                case 11: {
                    container = IonType.LIST;
                    break;
                }
                case 13: {
                    container = IonType.STRUCT;
                    break;
                }
                default: {
                    throw new IonException("unexpected container in parent stack: " + this._stack_parent_type[this._top - 1]);
                }
            }
        }
        return container;
    }

    void push(int typeid) {
        if (this._top + 1 == this._stack_parent_type.length) {
            this.growStack();
        }
        this._stack_parent_type[this._top] = typeid;
        this._stack_pending_comma[this._top] = this._pending_separator;
        switch (typeid) {
            case 12: {
                this._separator_character = " ";
                break;
            }
            case 11: 
            case 13: {
                this._separator_character = ",";
                break;
            }
            default: {
                this._separator_character = this._options.lineSeparator();
            }
        }
        ++this._top;
    }

    void growStack() {
        int oldlen = this._stack_parent_type.length;
        int newlen = oldlen * 2;
        int[] temp1 = new int[newlen];
        boolean[] temp3 = new boolean[newlen];
        System.arraycopy(this._stack_parent_type, 0, temp1, 0, oldlen);
        System.arraycopy(this._stack_pending_comma, 0, temp3, 0, oldlen);
        this._stack_parent_type = temp1;
        this._stack_pending_comma = temp3;
    }

    int pop() {
        --this._top;
        int typeid = this._stack_parent_type[this._top];
        int parentid = this._top > 0 ? this._stack_parent_type[this._top - 1] : -1;
        switch (parentid) {
            case -1: {
                this._in_struct = false;
                this._separator_character = this._options.topLevelSeparator();
                break;
            }
            case 12: {
                this._in_struct = false;
                this._separator_character = " ";
                break;
            }
            case 11: {
                this._in_struct = false;
                this._separator_character = ",";
                break;
            }
            case 13: {
                this._in_struct = true;
                this._separator_character = ",";
                break;
            }
            default: {
                this._separator_character = this._options.lineSeparator();
            }
        }
        return typeid;
    }

    int topType() {
        return this._stack_parent_type[this._top - 1];
    }

    boolean topPendingComma() {
        if (this._top == 0) {
            return false;
        }
        return this._stack_pending_comma[this._top - 1];
    }

    private boolean containerIsSexp() {
        if (this._top == 0) {
            return false;
        }
        int topType = this.topType();
        return topType == 12;
    }

    void printLeadingWhiteSpace() throws IOException {
        for (int ii = 0; ii < this._top; ++ii) {
            this._output.appendAscii(' ');
            this._output.appendAscii(' ');
        }
    }

    void closeCollection(char closeChar) throws IOException {
        if (this._options.isPrettyPrintOn()) {
            this._output.appendAscii(this._options.lineSeparator());
            this.printLeadingWhiteSpace();
        }
        this._output.appendAscii(closeChar);
    }

    private void writeSidLiteral(int sid) throws IOException {
        assert (sid >= 0);
        boolean asString2 = this._options._symbol_as_string;
        if (asString2) {
            this._output.appendAscii('\"');
        }
        this._output.appendAscii('$');
        this._output.printInt(sid);
        if (asString2) {
            this._output.appendAscii('\"');
        }
    }

    private void writeSymbolToken(String value) throws IOException {
        if (this._options._symbol_as_string) {
            if (this._options._string_as_json) {
                this._output.printJsonString(value);
            } else {
                this._output.printString(value);
            }
        } else {
            IonTextUtils.SymbolVariant variant = IonTextUtils.symbolVariant(value);
            switch (variant) {
                case IDENTIFIER: {
                    this._output.appendAscii(value);
                    break;
                }
                case OPERATOR: {
                    if (this.containerIsSexp()) {
                        this._output.appendAscii(value);
                        break;
                    }
                }
                case QUOTED: {
                    this._output.printQuotedSymbol(value);
                }
            }
        }
    }

    void writeFieldNameToken(SymbolToken sym) throws IOException {
        String name = sym.getText();
        if (name == null) {
            int sid = sym.getSid();
            this.writeSidLiteral(sid);
        } else {
            this.writeSymbolToken(name);
        }
    }

    void writeAnnotations(SymbolToken[] annotations) throws IOException {
        for (SymbolToken ann : annotations) {
            this.writeAnnotationToken(ann);
            this._output.appendAscii("::");
        }
    }

    void writeAnnotationToken(SymbolToken ann) throws IOException {
        String name = ann.getText();
        if (name == null) {
            this._output.appendAscii('$');
            this._output.appendAscii(Integer.toString(ann.getSid()));
        } else {
            this._output.printSymbol(name);
        }
    }

    boolean writeSeparator(boolean followingLongString) throws IOException {
        if (this._options.isPrettyPrintOn()) {
            if (this._pending_separator && !IonTextUtils.isAllWhitespace(this._separator_character)) {
                this._output.appendAscii(this._separator_character);
                followingLongString = false;
            }
            this._output.appendAscii(this._options.lineSeparator());
            this.printLeadingWhiteSpace();
        } else if (this._pending_separator) {
            this._output.appendAscii(this._separator_character);
            if (!IonTextUtils.isAllWhitespace(this._separator_character)) {
                followingLongString = false;
            }
        }
        return followingLongString;
    }

    @Override
    void startValue() throws IOException {
        super.startValue();
        boolean followingLongString = this._following_long_string;
        followingLongString = this.writeSeparator(followingLongString);
        if (this._in_struct) {
            SymbolToken sym = this.assumeFieldNameSymbol();
            this.writeFieldNameToken(sym);
            this._output.appendAscii(':');
            this.clearFieldName();
            followingLongString = false;
        }
        if (this.hasAnnotations() && !this._is_writing_ivm) {
            if (!this._options._skip_annotations) {
                SymbolToken[] annotations = this.getTypeAnnotationSymbols();
                this.writeAnnotations(annotations);
                followingLongString = false;
            }
            this.clearAnnotations();
        }
        this._following_long_string = followingLongString;
    }

    void closeValue() throws IOException {
        super.endValue();
        this._pending_separator = true;
        this._following_long_string = false;
        if (this.getDepth() == 0) {
            try {
                this.flush();
            } catch (IOException e) {
                throw new IonException(e);
            }
        }
    }

    @Override
    void writeIonVersionMarkerAsIs(SymbolTable systemSymtab) throws IOException {
        this._is_writing_ivm = true;
        this.writeSymbolAsIs(systemSymtab.getIonVersionId());
        this._is_writing_ivm = false;
    }

    @Override
    void writeLocalSymtab(SymbolTable symtab) throws IOException {
        SymbolTable[] imports = symtab.getImportedTables();
        IonTextWriterBuilder.LstMinimizing min = this._options.getLstMinimizing();
        if (min == null) {
            symtab.writeTo(this);
        } else if (min == IonTextWriterBuilder.LstMinimizing.LOCALS && imports.length > 0) {
            SymbolTableReader reader = new SymbolTableReader(symtab);
            IonType t = reader.next();
            assert (IonType.STRUCT.equals((Object)t));
            SymbolToken[] a = reader.getTypeAnnotationSymbols();
            assert (a != null && a.length >= 1);
            this.setTypeAnnotationSymbols(a);
            this.stepIn(IonType.STRUCT);
            reader.stepIn();
            while ((t = reader.next()) != null) {
                String name = reader.getFieldName();
                if ("symbols".equals(name)) continue;
                this.writeValue(reader);
            }
            this.stepOut();
        } else {
            SymbolTable systemSymtab = symtab.getSystemSymbolTable();
            this.writeIonVersionMarker(systemSymtab);
        }
        super.writeLocalSymtab(symtab);
    }

    @Override
    public void stepIn(IonType containerType) throws IOException {
        char opener;
        int tid;
        this.startValue();
        switch (containerType) {
            case SEXP: {
                if (!this._options._sexp_as_list) {
                    tid = 12;
                    this._in_struct = false;
                    opener = '(';
                    break;
                }
            }
            case LIST: {
                tid = 11;
                this._in_struct = false;
                opener = '[';
                break;
            }
            case STRUCT: {
                tid = 13;
                this._in_struct = true;
                opener = '{';
                break;
            }
            default: {
                throw new IllegalArgumentException();
            }
        }
        this.push(tid);
        this._output.appendAscii(opener);
        this._pending_separator = false;
        this._following_long_string = false;
    }

    @Override
    public void stepOut() throws IOException {
        char closer;
        if (this._top < 1) {
            throw new IllegalStateException("Cannot stepOut any further, already at top level.");
        }
        this._pending_separator = this.topPendingComma();
        int tid = this.pop();
        switch (tid) {
            case 11: {
                closer = ']';
                break;
            }
            case 12: {
                closer = ')';
                break;
            }
            case 13: {
                closer = '}';
                break;
            }
            default: {
                throw new IllegalStateException();
            }
        }
        this.closeCollection(closer);
        this.closeValue();
    }

    @Override
    public void writeNull() throws IOException {
        this.startValue();
        this._output.appendAscii("null");
        this.closeValue();
    }

    @Override
    public void writeNull(IonType type) throws IOException {
        String nullimage;
        this.startValue();
        if (this._options._untyped_nulls) {
            nullimage = "null";
        } else {
            switch (type) {
                case NULL: {
                    nullimage = "null";
                    break;
                }
                case BOOL: {
                    nullimage = "null.bool";
                    break;
                }
                case INT: {
                    nullimage = "null.int";
                    break;
                }
                case FLOAT: {
                    nullimage = "null.float";
                    break;
                }
                case DECIMAL: {
                    nullimage = "null.decimal";
                    break;
                }
                case TIMESTAMP: {
                    nullimage = "null.timestamp";
                    break;
                }
                case SYMBOL: {
                    nullimage = "null.symbol";
                    break;
                }
                case STRING: {
                    nullimage = "null.string";
                    break;
                }
                case BLOB: {
                    nullimage = "null.blob";
                    break;
                }
                case CLOB: {
                    nullimage = "null.clob";
                    break;
                }
                case SEXP: {
                    nullimage = "null.sexp";
                    break;
                }
                case LIST: {
                    nullimage = "null.list";
                    break;
                }
                case STRUCT: {
                    nullimage = "null.struct";
                    break;
                }
                default: {
                    throw new IllegalStateException("unexpected type " + (Object)((Object)type));
                }
            }
        }
        this._output.appendAscii(nullimage);
        this.closeValue();
    }

    @Override
    public void writeBool(boolean value) throws IOException {
        this.startValue();
        this._output.appendAscii(value ? "true" : "false");
        this.closeValue();
    }

    @Override
    public void writeInt(long value) throws IOException {
        this.startValue();
        this._output.printInt(value);
        this.closeValue();
    }

    @Override
    public void writeInt(BigInteger value) throws IOException {
        if (value == null) {
            this.writeNull(IonType.INT);
            return;
        }
        this.startValue();
        this._output.printInt(value);
        this.closeValue();
    }

    @Override
    public void writeFloat(double value) throws IOException {
        this.startValue();
        this._output.printFloat(this._options, value);
        this.closeValue();
    }

    @Override
    public void writeDecimal(BigDecimal value) throws IOException {
        if (value == null) {
            this.writeNull(IonType.DECIMAL);
            return;
        }
        this.startValue();
        this._output.printDecimal(this._options, value);
        this.closeValue();
    }

    @Override
    public void writeTimestamp(Timestamp value) throws IOException {
        if (value == null) {
            this.writeNull(IonType.TIMESTAMP);
            return;
        }
        this.startValue();
        if (this._options._timestamp_as_millis) {
            long millis = value.getMillis();
            this._output.appendAscii(Long.toString(millis));
        } else if (this._options._timestamp_as_string) {
            this._output.appendAscii('\"');
            this._output.appendAscii(value.toString());
            this._output.appendAscii('\"');
        } else {
            this._output.appendAscii(value.toString());
        }
        this.closeValue();
    }

    @Override
    public void writeString(String value) throws IOException {
        this.startValue();
        if (value != null && !this._following_long_string && this._long_string_threshold < value.length()) {
            this._output.printLongString(value);
            this.closeValue();
            this._following_long_string = true;
        } else {
            if (this._options._string_as_json) {
                this._output.printJsonString(value);
            } else {
                this._output.printString(value);
            }
            this.closeValue();
        }
    }

    @Override
    void writeSymbolAsIs(int symbolId) throws IOException {
        SymbolTable symtab = this.getSymbolTable();
        String text = symtab.findKnownSymbol(symbolId);
        if (text != null) {
            this.writeSymbolAsIs(text);
        } else {
            this.startValue();
            this.writeSidLiteral(symbolId);
            this.closeValue();
        }
    }

    @Override
    public void writeSymbolAsIs(String value) throws IOException {
        if (value == null) {
            this.writeNull(IonType.SYMBOL);
            return;
        }
        this.startValue();
        this.writeSymbolToken(value);
        this.closeValue();
    }

    @Override
    public void writeBlob(byte[] value, int start, int len) throws IOException {
        if (value == null) {
            this.writeNull(IonType.BLOB);
            return;
        }
        this.startValue();
        this._output.printBlob(this._options, value, start, len);
        this.closeValue();
    }

    @Override
    public void writeClob(byte[] value, int start, int len) throws IOException {
        if (value == null) {
            this.writeNull(IonType.CLOB);
            return;
        }
        this.startValue();
        this._output.printClob(this._options, value, start, len);
        this.closeValue();
    }

    @Override
    public void flush() throws IOException {
        if (!this._closed) {
            this._output.flush();
        }
    }

    @Override
    public void close() throws IOException {
        if (!this._closed) {
            try {
                if (this.getDepth() == 0) {
                    this.finish();
                }
            } finally {
                this._closed = true;
                this._output.close();
            }
        }
    }
}

