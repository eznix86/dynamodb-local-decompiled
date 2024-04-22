/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazon.ion.impl;

import com.amazon.ion.Decimal;
import com.amazon.ion.IntegerSize;
import com.amazon.ion.IonException;
import com.amazon.ion.IonReader;
import com.amazon.ion.IonType;
import com.amazon.ion.SymbolTable;
import com.amazon.ion.SymbolToken;
import com.amazon.ion.Timestamp;
import com.amazon.ion.impl.SymbolTokenImpl;
import com.amazon.ion.impl._Private_Utils;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;

final class SymbolTableReader
implements IonReader {
    static final int S_BOF = 0;
    static final int S_STRUCT = 1;
    static final int S_IN_STRUCT = 2;
    static final int S_NAME = 3;
    static final int S_VERSION = 4;
    static final int S_MAX_ID = 5;
    static final int S_IMPORT_LIST = 6;
    static final int S_IN_IMPORTS = 7;
    static final int S_IMPORT_STRUCT = 8;
    static final int S_IN_IMPORT_STRUCT = 9;
    static final int S_IMPORT_NAME = 10;
    static final int S_IMPORT_VERSION = 11;
    static final int S_IMPORT_MAX_ID = 12;
    static final int S_IMPORT_STRUCT_CLOSE = 13;
    static final int S_IMPORT_LIST_CLOSE = 14;
    static final int S_AFTER_IMPORT_LIST = 15;
    static final int S_SYMBOL_LIST = 16;
    static final int S_IN_SYMBOLS = 17;
    static final int S_SYMBOL = 18;
    static final int S_SYMBOL_LIST_CLOSE = 19;
    static final int S_STRUCT_CLOSE = 20;
    static final int S_EOF = 21;
    private static final int HAS_NAME = 1;
    private static final int HAS_VERSION = 2;
    private static final int HAS_MAX_ID = 4;
    private static final int HAS_IMPORT_LIST = 8;
    private static final int HAS_SYMBOL_LIST = 16;
    private final SymbolTable _symbol_table;
    private final int _maxId;
    int _current_state = 0;
    int _flags;
    String _string_value;
    long _int_value;
    private SymbolTable[] _imported_tables;
    private Iterator<SymbolTable> _import_iterator;
    private SymbolTable _current_import;
    Iterator<String> _local_symbols;
    private static final SymbolToken ION_SYMBOL_TABLE_SYM = _Private_Utils.newSymbolToken("$ion_symbol_table", 3);
    private static final SymbolToken ION_SHARED_SYMBOL_TABLE_SYM = _Private_Utils.newSymbolToken("$ion_shared_symbol_table", 9);

    private static final String get_state_name(int state) {
        switch (state) {
            case 0: {
                return "S_BOF";
            }
            case 1: {
                return "S_STRUCT";
            }
            case 2: {
                return "S_IN_STRUCT";
            }
            case 3: {
                return "S_NAME";
            }
            case 4: {
                return "S_VERSION";
            }
            case 5: {
                return "S_MAX_ID";
            }
            case 6: {
                return "S_IMPORT_LIST";
            }
            case 7: {
                return "S_IN_IMPORTS";
            }
            case 8: {
                return "S_IMPORT_STRUCT";
            }
            case 9: {
                return "S_IN_IMPORT_STRUCT";
            }
            case 10: {
                return "S_IMPORT_NAME";
            }
            case 11: {
                return "S_IMPORT_VERSION";
            }
            case 12: {
                return "S_IMPORT_MAX_ID";
            }
            case 13: {
                return "S_IMPORT_STRUCT_CLOSE";
            }
            case 14: {
                return "S_IMPORT_LIST_CLOSE";
            }
            case 15: {
                return "S_AFTER_IMPORT_LIST";
            }
            case 16: {
                return "S_SYMBOL_LIST";
            }
            case 17: {
                return "S_IN_SYMBOLS";
            }
            case 18: {
                return "S_SYMBOL";
            }
            case 19: {
                return "S_SYMBOL_LIST_CLOSE";
            }
            case 20: {
                return "S_STRUCT_CLOSE";
            }
            case 21: {
                return "S_EOF";
            }
        }
        return "<Unrecognized state: " + state + ">";
    }

    static final IonType stateType(int state) {
        switch (state) {
            case 0: {
                return null;
            }
            case 1: {
                return IonType.STRUCT;
            }
            case 2: {
                return null;
            }
            case 3: {
                return IonType.STRING;
            }
            case 4: {
                return IonType.INT;
            }
            case 5: {
                return IonType.INT;
            }
            case 6: {
                return IonType.LIST;
            }
            case 7: {
                return null;
            }
            case 8: {
                return IonType.STRUCT;
            }
            case 9: {
                return null;
            }
            case 10: {
                return IonType.STRING;
            }
            case 11: {
                return IonType.INT;
            }
            case 12: {
                return IonType.INT;
            }
            case 13: {
                return null;
            }
            case 14: {
                return null;
            }
            case 15: {
                return null;
            }
            case 16: {
                return IonType.LIST;
            }
            case 17: {
                return null;
            }
            case 18: {
                return IonType.STRING;
            }
            case 19: {
                return null;
            }
            case 20: {
                return null;
            }
            case 21: {
                return null;
            }
        }
        SymbolTableReader.throwUnrecognizedState(state);
        return null;
    }

    static final int stateDepth(int state) {
        switch (state) {
            case 0: {
                return 0;
            }
            case 1: {
                return 0;
            }
            case 2: {
                return 1;
            }
            case 3: {
                return 1;
            }
            case 4: {
                return 1;
            }
            case 5: {
                return 1;
            }
            case 6: {
                return 1;
            }
            case 7: {
                return 2;
            }
            case 8: {
                return 2;
            }
            case 9: {
                return 3;
            }
            case 10: {
                return 3;
            }
            case 11: {
                return 3;
            }
            case 12: {
                return 3;
            }
            case 13: {
                return 3;
            }
            case 14: {
                return 2;
            }
            case 15: {
                return 1;
            }
            case 16: {
                return 1;
            }
            case 17: {
                return 2;
            }
            case 18: {
                return 2;
            }
            case 19: {
                return 2;
            }
            case 20: {
                return 1;
            }
            case 21: {
                return 0;
            }
        }
        SymbolTableReader.throwUnrecognizedState(state);
        return -1;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public SymbolTableReader(SymbolTable symbol_table) {
        this._symbol_table = symbol_table;
        SymbolTable symbolTable = symbol_table;
        synchronized (symbolTable) {
            this._maxId = symbol_table.getMaxId();
            this._local_symbols = symbol_table.iterateDeclaredSymbolNames();
        }
        if (!symbol_table.isLocalTable()) {
            this.set_flag(1, true);
            this.set_flag(2, true);
        }
        if (this._maxId > 0) {
            // empty if block
        }
        this._imported_tables = this._symbol_table.getImportedTables();
        if (this._imported_tables != null && this._imported_tables.length != 0) {
            this.set_flag(8, true);
        }
        if (this._symbol_table.getImportedMaxId() < this._maxId) {
            this.set_flag(16, true);
        }
    }

    @Override
    public <T> T asFacet(Class<T> facetType) {
        return null;
    }

    private final void set_flag(int flag_bit, boolean flag_state) {
        this._flags = flag_state ? (this._flags |= flag_bit) : (this._flags &= ~flag_bit);
    }

    private final boolean test_flag(int flag_bit) {
        boolean flag_state = (this._flags & flag_bit) != 0;
        return flag_state;
    }

    final boolean hasName() {
        boolean flag_state = this.test_flag(1);
        return flag_state;
    }

    final boolean hasVersion() {
        boolean flag_state = this.test_flag(2);
        return flag_state;
    }

    final boolean hasMaxId() {
        boolean flag_state = this.test_flag(4);
        return flag_state;
    }

    final boolean hasImports() {
        boolean flag_state = this.test_flag(8);
        return flag_state;
    }

    final boolean hasLocalSymbols() {
        boolean flag_state = this.test_flag(16);
        return flag_state;
    }

    @Override
    public boolean hasNext() {
        boolean has_next = this.has_next_helper();
        return has_next;
    }

    private final boolean has_next_helper() {
        switch (this._current_state) {
            case 0: {
                return true;
            }
            case 1: {
                return false;
            }
            case 2: {
                return this.stateFirstInStruct() != 20;
            }
            case 3: {
                return true;
            }
            case 4: {
                if (this.hasMaxId()) {
                    return true;
                }
                return this.stateFollowingMaxId() != 20;
            }
            case 5: {
                return this.stateFollowingMaxId() != 20;
            }
            case 6: {
                return this.hasLocalSymbols();
            }
            case 7: 
            case 8: {
                boolean more_imports = this._import_iterator.hasNext();
                return more_imports;
            }
            case 9: 
            case 10: {
                return true;
            }
            case 11: {
                return true;
            }
            case 12: 
            case 13: {
                return false;
            }
            case 14: {
                return false;
            }
            case 15: {
                return this.hasLocalSymbols();
            }
            case 16: {
                assert (this.stateFollowingLocalSymbols() == 20);
                return false;
            }
            case 17: 
            case 18: {
                return this._local_symbols.hasNext();
            }
            case 19: 
            case 20: 
            case 21: {
                return false;
            }
        }
        SymbolTableReader.throwUnrecognizedState(this._current_state);
        return false;
    }

    private static final void throwUnrecognizedState(int state) {
        String message = "Internal error: UnifiedSymbolTableReader is in an unrecognized state: " + SymbolTableReader.get_state_name(state);
        throw new IonException(message);
    }

    private final int stateFirstInStruct() {
        int new_state = this.hasName() ? 3 : (this.hasMaxId() ? 5 : (this.hasImports() ? 6 : (this.hasLocalSymbols() ? 16 : 20)));
        return new_state;
    }

    private final int stateFollowingMaxId() {
        int new_state = this.hasImports() ? 6 : (this.hasLocalSymbols() ? 16 : 20);
        return new_state;
    }

    private final int nextImport() {
        int new_state;
        assert (this._import_iterator != null);
        if (this._import_iterator.hasNext()) {
            this._current_import = this._import_iterator.next();
            new_state = 8;
        } else {
            this._current_import = null;
            new_state = 14;
        }
        return new_state;
    }

    private final int stateFollowingImportList(Op op) {
        int new_state = -1;
        if (this.hasLocalSymbols()) {
            switch (op) {
                case NEXT: {
                    new_state = 16;
                    break;
                }
                case STEPOUT: {
                    new_state = 15;
                }
            }
        } else {
            new_state = 20;
        }
        return new_state;
    }

    private final int stateFollowingLocalSymbols() {
        return 20;
    }

    @Override
    public IonType next() {
        int new_state;
        if (!this.has_next_helper()) {
            return null;
        }
        switch (this._current_state) {
            case 0: {
                new_state = 1;
                break;
            }
            case 1: {
                new_state = 21;
                break;
            }
            case 2: {
                new_state = this.stateFirstInStruct();
                this.loadStateData(new_state);
                break;
            }
            case 3: {
                assert (this.hasVersion());
                new_state = 4;
                this.loadStateData(new_state);
                break;
            }
            case 4: {
                if (this.hasMaxId()) {
                    new_state = 5;
                    this.loadStateData(new_state);
                    break;
                }
                new_state = this.stateFollowingMaxId();
                break;
            }
            case 5: {
                new_state = this.stateFollowingMaxId();
                break;
            }
            case 6: {
                new_state = this.stateFollowingImportList(Op.NEXT);
                break;
            }
            case 7: 
            case 8: {
                assert (this._import_iterator != null);
                new_state = this.nextImport();
                break;
            }
            case 9: {
                new_state = 10;
                this.loadStateData(new_state);
                break;
            }
            case 10: {
                new_state = 11;
                this.loadStateData(new_state);
                break;
            }
            case 11: {
                new_state = 12;
                this.loadStateData(new_state);
                break;
            }
            case 12: {
                new_state = 13;
                break;
            }
            case 13: {
                new_state = 13;
                break;
            }
            case 14: {
                new_state = 14;
                break;
            }
            case 15: {
                assert (this._symbol_table.getImportedMaxId() < this._maxId);
                new_state = 16;
                break;
            }
            case 16: {
                assert (this._symbol_table.getImportedMaxId() < this._maxId);
                new_state = this.stateFollowingLocalSymbols();
                break;
            }
            case 17: {
                assert (this._local_symbols != null);
                assert (this._local_symbols.hasNext());
            }
            case 18: {
                if (this._local_symbols.hasNext()) {
                    this._string_value = this._local_symbols.next();
                    new_state = 18;
                    break;
                }
                new_state = 19;
                break;
            }
            case 19: {
                new_state = 19;
                break;
            }
            case 20: {
                new_state = 20;
                break;
            }
            case 21: {
                new_state = 21;
                break;
            }
            default: {
                SymbolTableReader.throwUnrecognizedState(this._current_state);
                new_state = -1;
            }
        }
        this._current_state = new_state;
        return SymbolTableReader.stateType(this._current_state);
    }

    private final void loadStateData(int new_state) {
        switch (new_state) {
            case 3: {
                String name;
                assert (this.hasName());
                this._string_value = name = this._symbol_table.getName();
                assert (this._string_value != null);
                break;
            }
            case 4: {
                int value = this._symbol_table.getVersion();
                this._int_value = value;
                assert (value != 0);
                break;
            }
            case 5: {
                this._int_value = this._maxId;
                break;
            }
            case 6: 
            case 16: {
                break;
            }
            case 10: {
                assert (this._current_import != null);
                this._string_value = this._current_import.getName();
                break;
            }
            case 11: {
                this._string_value = null;
                this._int_value = this._current_import.getVersion();
                break;
            }
            case 12: {
                this._int_value = this._current_import.getMaxId();
                break;
            }
            default: {
                String message = "UnifiedSymbolTableReader in state " + SymbolTableReader.get_state_name(new_state) + " has no state to load.";
                throw new IonException(message);
            }
        }
    }

    @Override
    public void stepIn() {
        int new_state;
        switch (this._current_state) {
            case 1: {
                new_state = 2;
                break;
            }
            case 6: {
                this._import_iterator = Arrays.asList(this._imported_tables).iterator();
                new_state = 7;
                break;
            }
            case 8: {
                assert (this._current_import != null);
                new_state = 9;
                break;
            }
            case 16: {
                new_state = 17;
                break;
            }
            default: {
                throw new IllegalStateException("current value is not a container");
            }
        }
        this._current_state = new_state;
    }

    @Override
    public void stepOut() {
        int new_state = -1;
        switch (this._current_state) {
            case 2: 
            case 3: 
            case 4: 
            case 5: 
            case 6: 
            case 15: 
            case 16: 
            case 20: {
                new_state = 21;
                break;
            }
            case 7: 
            case 8: 
            case 14: {
                this._current_import = null;
                this._import_iterator = null;
                new_state = this.stateFollowingImportList(Op.STEPOUT);
                break;
            }
            case 9: 
            case 10: 
            case 11: 
            case 12: 
            case 13: {
                if (this._import_iterator.hasNext()) {
                    new_state = 8;
                    break;
                }
                new_state = 14;
                break;
            }
            case 17: 
            case 18: 
            case 19: {
                this._string_value = null;
                this._local_symbols = null;
                new_state = this.stateFollowingLocalSymbols();
                break;
            }
            default: {
                throw new IllegalStateException("current value is not in a container");
            }
        }
        this._current_state = new_state;
    }

    @Override
    public int getDepth() {
        return SymbolTableReader.stateDepth(this._current_state);
    }

    @Override
    public SymbolTable getSymbolTable() {
        return null;
    }

    @Override
    public IonType getType() {
        return SymbolTableReader.stateType(this._current_state);
    }

    @Override
    public String[] getTypeAnnotations() {
        if (this._current_state == 1) {
            if (this._symbol_table.isLocalTable() || this._symbol_table.isSystemTable()) {
                return new String[]{"$ion_symbol_table"};
            }
            return new String[]{"$ion_shared_symbol_table"};
        }
        return _Private_Utils.EMPTY_STRING_ARRAY;
    }

    @Override
    public SymbolToken[] getTypeAnnotationSymbols() {
        if (this._current_state == 1) {
            SymbolToken sym = this._symbol_table.isLocalTable() || this._symbol_table.isSystemTable() ? ION_SYMBOL_TABLE_SYM : ION_SHARED_SYMBOL_TABLE_SYM;
            return new SymbolToken[]{sym};
        }
        return SymbolToken.EMPTY_ARRAY;
    }

    @Override
    public Iterator<String> iterateTypeAnnotations() {
        String[] annotations = this.getTypeAnnotations();
        return _Private_Utils.stringIterator(annotations);
    }

    @Override
    public int getFieldId() {
        switch (this._current_state) {
            case 1: 
            case 2: 
            case 7: 
            case 8: 
            case 9: 
            case 13: 
            case 14: 
            case 15: 
            case 17: 
            case 18: 
            case 19: 
            case 20: 
            case 21: {
                return -1;
            }
            case 3: 
            case 10: {
                return 4;
            }
            case 4: 
            case 11: {
                return 5;
            }
            case 5: 
            case 12: {
                return 8;
            }
            case 6: {
                return 6;
            }
            case 16: {
                return 7;
            }
        }
        throw new IonException("Internal error: UnifiedSymbolTableReader is in an unrecognized state: " + this._current_state);
    }

    @Override
    public String getFieldName() {
        switch (this._current_state) {
            case 1: 
            case 2: 
            case 7: 
            case 8: 
            case 9: 
            case 13: 
            case 14: 
            case 15: 
            case 17: 
            case 18: 
            case 19: 
            case 20: 
            case 21: {
                return null;
            }
            case 3: 
            case 10: {
                return "name";
            }
            case 4: 
            case 11: {
                return "version";
            }
            case 5: 
            case 12: {
                return "max_id";
            }
            case 6: {
                return "imports";
            }
            case 16: {
                return "symbols";
            }
        }
        throw new IonException("Internal error: UnifiedSymbolTableReader is in an unrecognized state: " + this._current_state);
    }

    @Override
    public SymbolToken getFieldNameSymbol() {
        switch (this._current_state) {
            case 1: 
            case 2: 
            case 7: 
            case 8: 
            case 9: 
            case 13: 
            case 14: 
            case 15: 
            case 17: 
            case 18: 
            case 19: 
            case 20: 
            case 21: {
                return null;
            }
            case 3: 
            case 10: {
                return new SymbolTokenImpl("name", 4);
            }
            case 4: 
            case 11: {
                return new SymbolTokenImpl("version", 5);
            }
            case 5: 
            case 12: {
                return new SymbolTokenImpl("max_id", 8);
            }
            case 6: {
                return new SymbolTokenImpl("imports", 6);
            }
            case 16: {
                return new SymbolTokenImpl("symbols", 7);
            }
        }
        throw new IonException("Internal error: UnifiedSymbolTableReader is in an unrecognized state: " + this._current_state);
    }

    @Override
    public boolean isNullValue() {
        switch (this._current_state) {
            case 1: 
            case 2: 
            case 3: 
            case 4: 
            case 5: 
            case 6: 
            case 7: 
            case 8: 
            case 9: 
            case 10: 
            case 11: 
            case 12: 
            case 17: 
            case 18: {
                return false;
            }
            case 13: 
            case 14: 
            case 15: 
            case 16: 
            case 19: 
            case 20: 
            case 21: {
                return false;
            }
        }
        throw new IonException("Internal error: UnifiedSymbolTableReader is in an unrecognized state: " + this._current_state);
    }

    @Override
    public boolean isInStruct() {
        switch (this._current_state) {
            case 1: 
            case 7: 
            case 8: 
            case 17: 
            case 18: {
                return false;
            }
            case 2: 
            case 3: 
            case 4: 
            case 5: 
            case 6: 
            case 9: 
            case 10: 
            case 11: 
            case 12: 
            case 15: 
            case 16: {
                return true;
            }
            case 13: 
            case 20: {
                return true;
            }
            case 14: 
            case 19: 
            case 21: {
                return false;
            }
        }
        throw new IonException("Internal error: UnifiedSymbolTableReader is in an unrecognized state: " + this._current_state);
    }

    @Override
    public boolean booleanValue() {
        throw new IllegalStateException("only valid if the value is a boolean");
    }

    @Override
    public int intValue() {
        return (int)this._int_value;
    }

    @Override
    public long longValue() {
        return this._int_value;
    }

    @Override
    public BigInteger bigIntegerValue() {
        String value = Long.toString(this._int_value);
        BigInteger bi = new BigInteger(value);
        return bi;
    }

    @Override
    public double doubleValue() {
        throw new IllegalStateException("only valid if the value is a double");
    }

    @Override
    public BigDecimal bigDecimalValue() {
        throw new IllegalStateException("only valid if the value is a decimal");
    }

    @Override
    public Decimal decimalValue() {
        throw new IllegalStateException("only valid if the value is a decimal");
    }

    @Override
    public Date dateValue() {
        throw new IllegalStateException("only valid if the value is a timestamp");
    }

    @Override
    public Timestamp timestampValue() {
        throw new IllegalStateException("only valid if the value is a timestamp");
    }

    @Override
    public String stringValue() {
        return this._string_value;
    }

    @Override
    public SymbolToken symbolValue() {
        throw new UnsupportedOperationException();
    }

    @Override
    public int getBytes(byte[] buffer, int offset, int len) {
        throw new IllegalStateException("getBytes() is only valid if the reader is on a lob value, not a " + (Object)((Object)SymbolTableReader.stateType(this._current_state)) + " value");
    }

    @Override
    public int byteSize() {
        throw new IllegalStateException("byteSize() is only valid if the reader is on a lob value, not a " + (Object)((Object)SymbolTableReader.stateType(this._current_state)) + " value");
    }

    @Override
    public byte[] newBytes() {
        throw new IllegalStateException("newBytes() is only valid if the reader is on a lob value, not a " + (Object)((Object)SymbolTableReader.stateType(this._current_state)) + " value");
    }

    @Override
    public void close() throws IOException {
        this._current_state = 21;
    }

    @Override
    public IntegerSize getIntegerSize() {
        if (SymbolTableReader.stateType(this._current_state) != IonType.INT) {
            return null;
        }
        return IntegerSize.INT;
    }

    private static enum Op {
        NEXT,
        STEPOUT;

    }
}

