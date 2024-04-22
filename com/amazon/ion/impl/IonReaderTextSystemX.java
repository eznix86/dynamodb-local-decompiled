/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazon.ion.impl;

import com.amazon.ion.Decimal;
import com.amazon.ion.IntegerSize;
import com.amazon.ion.IonBlob;
import com.amazon.ion.IonClob;
import com.amazon.ion.IonException;
import com.amazon.ion.IonList;
import com.amazon.ion.IonSequence;
import com.amazon.ion.IonSexp;
import com.amazon.ion.IonStruct;
import com.amazon.ion.IonSystem;
import com.amazon.ion.IonTimestamp;
import com.amazon.ion.IonType;
import com.amazon.ion.IonValue;
import com.amazon.ion.SymbolTable;
import com.amazon.ion.SymbolToken;
import com.amazon.ion.Timestamp;
import com.amazon.ion.UnknownSymbolException;
import com.amazon.ion.impl.IonReaderTextRawTokensX;
import com.amazon.ion.impl.IonReaderTextRawX;
import com.amazon.ion.impl.IonTokenConstsX;
import com.amazon.ion.impl.SymbolTokenImpl;
import com.amazon.ion.impl.UnifiedInputStreamX;
import com.amazon.ion.impl._Private_ReaderWriter;
import com.amazon.ion.impl._Private_ScalarConversions;
import com.amazon.ion.impl._Private_Utils;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

class IonReaderTextSystemX
extends IonReaderTextRawX
implements _Private_ReaderWriter {
    private static int UNSIGNED_BYTE_MAX_VALUE = 255;
    SymbolTable _system_symtab = _Private_Utils.systemSymtab(1);

    protected IonReaderTextSystemX(UnifiedInputStreamX iis) {
        this.init_once();
        this.init(iis, IonType.DATAGRAM);
    }

    @Override
    public IntegerSize getIntegerSize() {
        if (this._value_type != IonType.INT || this._v.isNull()) {
            return null;
        }
        this.load_once();
        return _Private_ScalarConversions.getIntegerSize(this._v.getAuthoritativeType());
    }

    private void load_once() {
        if (this._v.isEmpty()) {
            try {
                this.load_scalar_value();
            } catch (IOException e) {
                throw new IonException(e);
            }
        }
    }

    private final void load_or_cast_cached_value(int value_type) {
        this.load_once();
        if (value_type != 0 && !this._v.hasValueOfType(value_type)) {
            this.cast_cached_value(value_type);
        }
    }

    private final void load_scalar_value() throws IOException {
        switch (this._value_type) {
            case NULL: {
                this._v.setValueToNull(this._null_type);
                this._v.setAuthoritativeType(1);
                return;
            }
            case BOOL: 
            case INT: 
            case FLOAT: 
            case DECIMAL: 
            case TIMESTAMP: 
            case SYMBOL: 
            case STRING: {
                break;
            }
            default: {
                return;
            }
        }
        StringBuilder cs = this.token_contents_load(this._scanner.getToken());
        int token_type = this._scanner.getToken();
        if (this._value_type == IonType.DECIMAL) {
            for (int ii = 0; ii < cs.length(); ++ii) {
                char c = cs.charAt(ii);
                if (c != 'd' && c != 'D') continue;
                cs.setCharAt(ii, 'e');
                break;
            }
        } else if (token_type == 3 || token_type == 26) {
            boolean isNegative = cs.charAt(0) == '-';
            int pos = isNegative ? 1 : 0;
            char caseChar = token_type == 3 ? (char)'x' : 'b';
            if (cs.length() <= (isNegative ? 3 : 2) || Character.toLowerCase(cs.charAt(pos + 1)) != caseChar) {
                this.parse_error("Invalid " + (caseChar == 'x' ? "hexadecimal" : "binary") + " int value.");
            }
            cs.deleteCharAt(pos);
            cs.deleteCharAt(pos);
        }
        int len = cs.length();
        String s = cs.toString();
        this.clear_current_value_buffer();
        block9 : switch (token_type) {
            case 1: {
                switch (this._value_type) {
                    case INT: {
                        if (Radix.DECIMAL.isInt(s, len)) {
                            this._v.setValue(Integer.parseInt(s));
                            break block9;
                        }
                        if (Radix.DECIMAL.isLong(s, len)) {
                            this._v.setValue(Long.parseLong(s));
                            break block9;
                        }
                        this._v.setValue(new BigInteger(s));
                        break block9;
                    }
                    case DECIMAL: {
                        try {
                            this._v.setValue(Decimal.valueOf(s));
                        } catch (NumberFormatException e) {
                            this.parse_error(e);
                        }
                        break block9;
                    }
                    case FLOAT: {
                        try {
                            this._v.setValue(Double.parseDouble(s));
                        } catch (NumberFormatException e) {
                            this.parse_error(e);
                        }
                        break block9;
                    }
                    case TIMESTAMP: {
                        this._v.setValue(Timestamp.valueOf(s));
                        break block9;
                    }
                }
                String message = "unexpected prefectched value type " + this.getType().toString() + " encountered handling an unquoted symbol";
                this.parse_error(message);
                break;
            }
            case 2: {
                if (Radix.DECIMAL.isInt(s, len)) {
                    this._v.setValue(Integer.parseInt(s));
                    break;
                }
                if (Radix.DECIMAL.isLong(s, len)) {
                    this._v.setValue(Long.parseLong(s));
                    break;
                }
                this._v.setValue(new BigInteger(s));
                break;
            }
            case 26: {
                if (Radix.BINARY.isInt(s, len)) {
                    this._v.setValue(Integer.parseInt(s, 2));
                    break;
                }
                if (Radix.BINARY.isLong(s, len)) {
                    this._v.setValue(Long.parseLong(s, 2));
                    break;
                }
                this._v.setValue(new BigInteger(s, 2));
                break;
            }
            case 3: {
                if (Radix.HEX.isInt(s, len)) {
                    int v_int = Integer.parseInt(s, 16);
                    this._v.setValue(v_int);
                    break;
                }
                if (Radix.HEX.isLong(s, len)) {
                    long v_long = Long.parseLong(s, 16);
                    this._v.setValue(v_long);
                    break;
                }
                BigInteger v_big_int = new BigInteger(s, 16);
                this._v.setValue(v_big_int);
                break;
            }
            case 4: {
                try {
                    this._v.setValue(Decimal.valueOf(s));
                } catch (NumberFormatException e) {
                    this.parse_error(e);
                }
                break;
            }
            case 5: {
                try {
                    this._v.setValue(Double.parseDouble(s));
                } catch (NumberFormatException e) {
                    this.parse_error(e);
                }
                break;
            }
            case 8: {
                Timestamp t = null;
                try {
                    t = Timestamp.valueOf(s);
                } catch (IllegalArgumentException e) {
                    this.parse_error(e);
                }
                this._v.setValue(t);
                break;
            }
            case 9: {
                if (this.isNullValue()) {
                    this._v.setValueToNull(this._null_type);
                    break;
                }
                switch (this.getType()) {
                    case SYMBOL: {
                        this._v.setValue(s);
                        break block9;
                    }
                    case FLOAT: {
                        switch (this._value_keyword) {
                            case 16: {
                                this._v.setValue(Double.NaN);
                                break block9;
                            }
                        }
                        String message = "unexpected keyword " + s + " identified as a FLOAT";
                        this.parse_error(message);
                        break block9;
                    }
                    case BOOL: {
                        switch (this._value_keyword) {
                            case 1: {
                                this._v.setValue(true);
                                break block9;
                            }
                            case 2: {
                                this._v.setValue(false);
                                break block9;
                            }
                        }
                        String message = "unexpected keyword " + s + " identified as a BOOL";
                        this.parse_error(message);
                        break block9;
                    }
                }
                String message = "unexpected prefectched value type " + this.getType().toString() + " encountered handling an unquoted symbol";
                this.parse_error(message);
                break;
            }
            case 10: 
            case 11: 
            case 12: {
                this._v.setValue(s);
                break;
            }
            case 13: {
                this._v.setValue(s);
                break;
            }
            default: {
                this.parse_error("scalar token " + IonTokenConstsX.getTokenName(this._scanner.getToken()) + "isn't a recognized type");
            }
        }
    }

    private final void cast_cached_value(int new_type) {
        block8: {
            block7: {
                assert (!this._v.hasValueOfType(new_type));
                if (this._v.isNull()) {
                    return;
                }
                if (!IonType.SYMBOL.equals((Object)this._value_type)) break block7;
                switch (new_type) {
                    case 8: {
                        int sid = this._v.getInt();
                        String sym = this.getSymbolTable().findKnownSymbol(sid);
                        this._v.addValue(sym);
                        break block8;
                    }
                    case 3: {
                        String sym = this._v.getString();
                        int sid = this.getSymbolTable().findSymbol(sym);
                        this._v.addValue(sid);
                        break block8;
                    }
                    default: {
                        String message = "can't cast symbol from " + _Private_ScalarConversions.getValueTypeName(this._v.getAuthoritativeType()) + " to " + _Private_ScalarConversions.getValueTypeName(new_type);
                        throw new _Private_ScalarConversions.CantConvertException(message);
                    }
                }
            }
            if (!this._v.can_convert(new_type)) {
                String message = "can't cast from " + _Private_ScalarConversions.getValueTypeName(this._v.getAuthoritativeType()) + " to " + _Private_ScalarConversions.getValueTypeName(new_type);
                throw new _Private_ScalarConversions.CantConvertException(message);
            }
            int fnid = this._v.get_conversion_fnid(new_type);
            this._v.cast(fnid);
        }
    }

    @Override
    public SymbolToken[] getTypeAnnotationSymbols() {
        int count = this._annotation_count;
        if (count == 0) {
            return SymbolToken.EMPTY_ARRAY;
        }
        this.resolveAnnotationSymbols(count);
        SymbolToken[] result = new SymbolToken[count];
        System.arraycopy(this._annotations, 0, result, 0, count);
        return result;
    }

    @Override
    public String[] getTypeAnnotations() {
        this.resolveAnnotationSymbols(this._annotation_count);
        return _Private_Utils.toStrings(this._annotations, this._annotation_count);
    }

    private void resolveAnnotationSymbols(int count) {
        SymbolTable symbols = this.getSymbolTable();
        for (int i = 0; i < count; ++i) {
            SymbolToken sym = this._annotations[i];
            SymbolToken updated = _Private_Utils.localize(symbols, sym);
            if (updated == sym) continue;
            this._annotations[i] = updated;
        }
    }

    @Override
    public boolean isNullValue() {
        return this._v.isNull();
    }

    @Override
    public boolean booleanValue() {
        this.load_or_cast_cached_value(2);
        return this._v.getBoolean();
    }

    @Override
    public double doubleValue() {
        this.load_or_cast_cached_value(7);
        return this._v.getDouble();
    }

    private void checkIsIntApplicableType() {
        if (this._value_type != IonType.INT && this._value_type != IonType.DECIMAL && this._value_type != IonType.FLOAT) {
            throw new IllegalStateException("Unexpected value type: " + (Object)((Object)this._value_type));
        }
    }

    @Override
    public int intValue() {
        this.checkIsIntApplicableType();
        this.load_or_cast_cached_value(3);
        return this._v.getInt();
    }

    @Override
    public long longValue() {
        this.checkIsIntApplicableType();
        this.load_or_cast_cached_value(4);
        return this._v.getLong();
    }

    private void checkIsBigIntegerApplicableType() {
        if (this._value_type != IonType.INT && this._value_type != IonType.FLOAT) {
            throw new IllegalStateException("Unexpected value type: " + (Object)((Object)this._value_type));
        }
    }

    @Override
    public BigInteger bigIntegerValue() {
        this.checkIsBigIntegerApplicableType();
        this.load_or_cast_cached_value(5);
        if (this._v.isNull()) {
            return null;
        }
        return this._v.getBigInteger();
    }

    @Override
    public BigDecimal bigDecimalValue() {
        this.load_or_cast_cached_value(6);
        if (this._v.isNull()) {
            return null;
        }
        return this._v.getBigDecimal();
    }

    @Override
    public Decimal decimalValue() {
        this.load_or_cast_cached_value(6);
        if (this._v.isNull()) {
            return null;
        }
        return this._v.getDecimal();
    }

    @Override
    public Date dateValue() {
        this.load_or_cast_cached_value(9);
        if (this._v.isNull()) {
            return null;
        }
        return this._v.getDate();
    }

    @Override
    public Timestamp timestampValue() {
        this.load_or_cast_cached_value(10);
        if (this._v.isNull()) {
            return null;
        }
        return this._v.getTimestamp();
    }

    @Override
    public final String stringValue() {
        if (!IonType.isText(this._value_type)) {
            throw new IllegalStateException("Unexpected value type: " + (Object)((Object)this._value_type));
        }
        if (this._v.isNull()) {
            return null;
        }
        this.load_or_cast_cached_value(8);
        String text = this._v.getString();
        if (text == null) {
            int sid = this._v.getInt();
            throw new UnknownSymbolException(sid);
        }
        return text;
    }

    @Override
    public SymbolTable getSymbolTable() {
        SymbolTable symtab = super.getSymbolTable();
        if (symtab == null) {
            symtab = this._system_symtab;
        }
        return symtab;
    }

    @Override
    public final int getFieldId() {
        String fieldname;
        int id = super.getFieldId();
        if (id == -1 && (fieldname = this.getRawFieldName()) != null) {
            SymbolTable symbols = this.getSymbolTable();
            id = symbols.findSymbol(fieldname);
        }
        return id;
    }

    @Override
    public final String getFieldName() {
        SymbolTable symbols;
        int id;
        String text = this.getRawFieldName();
        if (text == null && (id = this.getFieldId()) != -1 && (text = (symbols = this.getSymbolTable()).findKnownSymbol(id)) == null) {
            throw new UnknownSymbolException(id);
        }
        return text;
    }

    @Override
    public SymbolToken getFieldNameSymbol() {
        SymbolToken sym = super.getFieldNameSymbol();
        if (sym != null) {
            sym = _Private_Utils.localize(this.getSymbolTable(), sym);
        }
        return sym;
    }

    @Override
    public SymbolToken symbolValue() {
        if (this._value_type != IonType.SYMBOL) {
            throw new IllegalStateException("Unexpected value type: " + (Object)((Object)this._value_type));
        }
        if (this._v.isNull()) {
            return null;
        }
        this.load_or_cast_cached_value(8);
        if (!this._v.hasValueOfType(3)) {
            this.cast_cached_value(3);
        }
        String text = this._v.getString();
        int sid = this._v.getInt();
        return new SymbolTokenImpl(text, sid);
    }

    @Override
    public int byteSize() {
        long len;
        this.ensureLob("byteSize");
        try {
            len = this.load_lob_contents();
        } catch (IOException e) {
            throw new IonException(e);
        }
        if (len < 0L || len > Integer.MAX_VALUE) {
            this.load_lob_length_overflow_error(len);
        }
        return (int)len;
    }

    private final void load_lob_length_overflow_error(long len) {
        String message = "Size overflow: " + this._value_type.toString() + " size (" + Long.toString(len) + ") exceeds int ";
        throw new IonException(message);
    }

    private final long load_lob_save_point() throws IOException {
        if (this._lob_loaded == IonReaderTextRawX.LOB_STATE.EMPTY) {
            assert (!this._current_value_save_point_loaded && this._current_value_save_point.isClear());
            this._scanner.save_point_start(this._current_value_save_point);
            this._scanner.skip_over_lob(this._lob_token, this._current_value_save_point);
            this._current_value_save_point_loaded = true;
            this.tokenValueIsFinished();
            this._lob_loaded = IonReaderTextRawX.LOB_STATE.READ;
        }
        long size = this._current_value_save_point.length();
        return size;
    }

    private int load_lob_contents() throws IOException {
        if (this._lob_loaded == IonReaderTextRawX.LOB_STATE.EMPTY) {
            this.load_lob_save_point();
        }
        if (this._lob_loaded == IonReaderTextRawX.LOB_STATE.READ) {
            long raw_size = this._current_value_save_point.length();
            if (raw_size < 0L || raw_size > 0x7FFFFFF7L) {
                this.load_lob_length_overflow_error(raw_size);
            }
            this._lob_bytes = new byte[(int)raw_size];
            try {
                assert (this._current_value_save_point_loaded && this._current_value_save_point.isDefined());
                this._scanner.save_point_activate(this._current_value_save_point);
                this._lob_actual_len = this.readBytes(this._lob_bytes, 0, (int)raw_size);
                this._scanner.save_point_deactivate(this._current_value_save_point);
            } catch (IOException e) {
                throw new IonException(e);
            }
            assert ((long)this._lob_actual_len <= raw_size);
            this._lob_loaded = IonReaderTextRawX.LOB_STATE.FINISHED;
        }
        assert (this._lob_loaded == IonReaderTextRawX.LOB_STATE.FINISHED);
        return this._lob_actual_len;
    }

    private void ensureLob(String apiName) {
        switch (this._value_type) {
            case CLOB: 
            case BLOB: {
                break;
            }
            default: {
                String msg = apiName + " is only valid if the reader is on a lob value, not a " + (Object)((Object)this._value_type) + " value";
                throw new IllegalStateException(msg);
            }
        }
    }

    @Override
    public byte[] newBytes() {
        int len;
        this.ensureLob("newBytes");
        try {
            len = this.load_lob_contents();
        } catch (IOException e) {
            throw new IonException(e);
        }
        byte[] bytes = new byte[len];
        System.arraycopy(this._lob_bytes, 0, bytes, 0, len);
        return bytes;
    }

    @Override
    public int getBytes(byte[] buffer, int offset, int len) {
        int len_read;
        this.ensureLob("getBytes");
        if (this._lob_loaded == IonReaderTextRawX.LOB_STATE.READ) {
            try {
                this.load_lob_contents();
            } catch (IOException e) {
                throw new IonException(e);
            }
        }
        if (this._lob_loaded == IonReaderTextRawX.LOB_STATE.FINISHED) {
            len_read = len;
            if (len_read > this._lob_actual_len) {
                len_read = this._lob_actual_len;
            }
            System.arraycopy(this._lob_bytes, 0, buffer, offset, len_read);
        } else {
            try {
                if (this._current_value_save_point_loaded && this._lob_value_position > 0L) {
                    if (this._current_value_save_point.isActive()) {
                        this._scanner.save_point_deactivate(this._current_value_save_point);
                    }
                    this._scanner.save_point_activate(this._current_value_save_point);
                    this._lob_value_position = 0L;
                }
                this._scanner.save_point_activate(this._current_value_save_point);
                len_read = this.readBytes(buffer, offset, len);
                this._scanner.save_point_deactivate(this._current_value_save_point);
            } catch (IOException e) {
                throw new IonException(e);
            }
        }
        return len_read;
    }

    private int readBytes(byte[] buffer, int offset, int len) throws IOException {
        int starting_offset = offset;
        int c = -1;
        block0 : switch (this._lob_token) {
            case 24: {
                while (len-- > 0 && (c = this._scanner.read_base64_byte()) >= 0) {
                    buffer[offset++] = (byte)c;
                }
                break;
            }
            case 12: {
                while (len-- > 0) {
                    c = this._scanner.read_double_quoted_char(true);
                    if (c < 0) {
                        if (c != -7 && c != -8 && c != -9) break block0;
                        continue;
                    }
                    assert (c <= UNSIGNED_BYTE_MAX_VALUE);
                    buffer[offset++] = (byte)c;
                }
                break;
            }
            case 13: {
                while (len-- > 0) {
                    c = this._scanner.read_triple_quoted_char(true);
                    if (c < 0) {
                        if (c == -7 || c == -8 || c == -9 || c == -3) continue;
                        if (c != -4 && c != -5 && c != -6) break block0;
                        buffer[offset++] = 10;
                        continue;
                    }
                    assert (c >= 0 && c <= UNSIGNED_BYTE_MAX_VALUE);
                    buffer[offset++] = (byte)c;
                }
                break;
            }
            default: {
                String message = "invalid type [" + this._value_type.toString() + "] for lob handling";
                throw new IonReaderTextRawTokensX.IonReaderTextTokenException(message);
            }
        }
        if (c == -1) {
            this._scanner.tokenIsFinished();
        }
        int read = offset - starting_offset;
        this._lob_value_position += (long)read;
        return read;
    }

    public IonValue getIonValue(IonSystem sys) {
        if (this.isNullValue()) {
            switch (this._value_type) {
                case NULL: {
                    return sys.newNull();
                }
                case BOOL: {
                    return sys.newNullBool();
                }
                case INT: {
                    return sys.newNullInt();
                }
                case FLOAT: {
                    return sys.newNullFloat();
                }
                case DECIMAL: {
                    return sys.newNullDecimal();
                }
                case TIMESTAMP: {
                    return sys.newNullTimestamp();
                }
                case SYMBOL: {
                    return sys.newNullSymbol();
                }
                case STRING: {
                    return sys.newNullString();
                }
                case CLOB: {
                    return sys.newNullClob();
                }
                case BLOB: {
                    return sys.newNullBlob();
                }
                case LIST: {
                    return sys.newNullList();
                }
                case SEXP: {
                    return sys.newNullSexp();
                }
                case STRUCT: {
                    return sys.newNullString();
                }
            }
            throw new IonException("unrecognized type encountered");
        }
        switch (this._value_type) {
            case NULL: {
                return sys.newNull();
            }
            case BOOL: {
                return sys.newBool(this.booleanValue());
            }
            case INT: {
                return sys.newInt(this.longValue());
            }
            case FLOAT: {
                return sys.newFloat(this.doubleValue());
            }
            case DECIMAL: {
                return sys.newDecimal(this.decimalValue());
            }
            case TIMESTAMP: {
                IonTimestamp t = sys.newNullTimestamp();
                Timestamp ti = this.timestampValue();
                t.setValue(ti);
                return t;
            }
            case SYMBOL: {
                return sys.newSymbol(this.stringValue());
            }
            case STRING: {
                return sys.newString(this.stringValue());
            }
            case CLOB: {
                IonClob clob = sys.newNullClob();
                clob.setBytes(this.newBytes());
                return clob;
            }
            case BLOB: {
                IonBlob blob = sys.newNullBlob();
                blob.setBytes(this.newBytes());
                return blob;
            }
            case LIST: {
                IonList list = sys.newNullList();
                this.fillContainerList(sys, list);
                return list;
            }
            case SEXP: {
                IonSexp sexp = sys.newNullSexp();
                this.fillContainerList(sys, sexp);
                return sexp;
            }
            case STRUCT: {
                IonStruct struct = sys.newNullStruct();
                this.fillContainerStruct(sys, struct);
                return struct;
            }
        }
        throw new IonException("unrecognized type encountered");
    }

    private final void fillContainerList(IonSystem sys, IonSequence list) {
        this.stepIn();
        while (this.next() != null) {
            IonValue v = this.getIonValue(sys);
            list.add(v);
        }
        this.stepOut();
    }

    private final void fillContainerStruct(IonSystem sys, IonStruct struct) {
        this.stepIn();
        while (this.next() != null) {
            String name = this.getFieldName();
            IonValue v = this.getIonValue(sys);
            struct.add(name, v);
        }
        this.stepOut();
    }

    @Override
    public SymbolTable pop_passed_symbol_table() {
        return null;
    }

    static abstract class Radix
    extends Enum<Radix> {
        public static final /* enum */ Radix DECIMAL = new Radix(){

            @Override
            boolean isInt(String image, int len) {
                return Radix.valueWithinBounds(image, len, MIN_INT_IMAGE, MAX_INT_IMAGE);
            }

            @Override
            boolean isLong(String image, int len) {
                return Radix.valueWithinBounds(image, len, MIN_LONG_IMAGE, MAX_LONG_IMAGE);
            }
        };
        public static final /* enum */ Radix HEX = new Radix(){

            @Override
            boolean isInt(String image, int len) {
                return Radix.valueWithinBounds(image, len, MIN_HEX_INT_IMAGE, MAX_HEX_INT_IMAGE);
            }

            @Override
            boolean isLong(String image, int len) {
                return Radix.valueWithinBounds(image, len, MIN_HEX_LONG_IMAGE, MAX_HEX_LONG_IMAGE);
            }
        };
        public static final /* enum */ Radix BINARY = new Radix(){

            @Override
            boolean isInt(String image, int len) {
                return Radix.valueWithinBounds(image, len, MIN_BINARY_INT_IMAGE, MAX_BINARY_INT_IMAGE);
            }

            @Override
            boolean isLong(String image, int len) {
                return Radix.valueWithinBounds(image, len, MIN_BINARY_LONG_IMAGE, MAX_BINARY_LONG_IMAGE);
            }
        };
        private static final char[] MAX_INT_IMAGE;
        private static final char[] MIN_INT_IMAGE;
        private static final char[] MAX_LONG_IMAGE;
        private static final char[] MIN_LONG_IMAGE;
        private static final char[] MAX_BINARY_INT_IMAGE;
        private static final char[] MIN_BINARY_INT_IMAGE;
        private static final char[] MAX_BINARY_LONG_IMAGE;
        private static final char[] MIN_BINARY_LONG_IMAGE;
        private static final char[] MAX_HEX_INT_IMAGE;
        private static final char[] MIN_HEX_INT_IMAGE;
        private static final char[] MAX_HEX_LONG_IMAGE;
        private static final char[] MIN_HEX_LONG_IMAGE;
        private static final /* synthetic */ Radix[] $VALUES;

        public static Radix[] values() {
            return (Radix[])$VALUES.clone();
        }

        public static Radix valueOf(String name) {
            return Enum.valueOf(Radix.class, name);
        }

        abstract boolean isInt(String var1, int var2);

        abstract boolean isLong(String var1, int var2);

        private static boolean valueWithinBounds(String value, int len, char[] minImage, char[] maxImage) {
            boolean negative = value.charAt(0) == '-';
            char[] boundaryImage = negative ? minImage : maxImage;
            int maxImageLength = boundaryImage.length;
            return len < maxImageLength || len == maxImageLength && Radix.magnitudeLessThanOrEqualTo(value, len, boundaryImage);
        }

        private static boolean magnitudeLessThanOrEqualTo(String lhs, int lhsLen, char[] rhs) {
            assert (lhsLen == rhs.length);
            for (int i = lhsLen - 1; i >= 0; --i) {
                if (lhs.charAt(i) <= rhs[i]) continue;
                return false;
            }
            return true;
        }

        static {
            $VALUES = new Radix[]{DECIMAL, HEX, BINARY};
            MAX_INT_IMAGE = Integer.toString(Integer.MAX_VALUE).toCharArray();
            MIN_INT_IMAGE = Integer.toString(Integer.MIN_VALUE).toCharArray();
            MAX_LONG_IMAGE = Long.toString(Long.MAX_VALUE).toCharArray();
            MIN_LONG_IMAGE = Long.toString(Long.MIN_VALUE).toCharArray();
            MAX_BINARY_INT_IMAGE = Integer.toBinaryString(Integer.MAX_VALUE).toCharArray();
            MIN_BINARY_INT_IMAGE = ("-" + Integer.toBinaryString(Integer.MIN_VALUE)).toCharArray();
            MAX_BINARY_LONG_IMAGE = Long.toBinaryString(Long.MAX_VALUE).toCharArray();
            MIN_BINARY_LONG_IMAGE = ("-" + Long.toBinaryString(Long.MIN_VALUE)).toCharArray();
            MAX_HEX_INT_IMAGE = Integer.toHexString(Integer.MAX_VALUE).toCharArray();
            MIN_HEX_INT_IMAGE = ("-" + Integer.toHexString(Integer.MIN_VALUE)).toCharArray();
            MAX_HEX_LONG_IMAGE = Long.toHexString(Long.MAX_VALUE).toCharArray();
            MIN_HEX_LONG_IMAGE = ("-" + Long.toHexString(Long.MIN_VALUE)).toCharArray();
        }
    }
}

