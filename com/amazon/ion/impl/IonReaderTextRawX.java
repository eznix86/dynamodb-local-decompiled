/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazon.ion.impl;

import com.amazon.ion.IonException;
import com.amazon.ion.IonTextReader;
import com.amazon.ion.IonType;
import com.amazon.ion.SymbolTable;
import com.amazon.ion.SymbolToken;
import com.amazon.ion.UnknownSymbolException;
import com.amazon.ion.impl.IonReaderTextRawTokensX;
import com.amazon.ion.impl.IonTokenConstsX;
import com.amazon.ion.impl.SymbolTokenImpl;
import com.amazon.ion.impl.UnifiedInputStreamX;
import com.amazon.ion.impl.UnifiedSavePointManagerX;
import com.amazon.ion.impl._Private_ScalarConversions;
import com.amazon.ion.impl._Private_Utils;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Iterator;

abstract class IonReaderTextRawX
implements IonTextReader {
    static final boolean _debug = false;
    private static final int DEFAULT_STACK_DEPTH = 10;
    protected static final int UNKNOWN_SIZE = -1;
    private static final int DEFAULT_ANNOTATION_COUNT = 5;
    static final int STATE_BEFORE_ANNOTATION_DATAGRAM = 0;
    static final int STATE_BEFORE_ANNOTATION_CONTAINED = 1;
    static final int STATE_BEFORE_ANNOTATION_SEXP = 2;
    static final int STATE_BEFORE_FIELD_NAME = 3;
    static final int STATE_BEFORE_VALUE_CONTENT = 4;
    static final int STATE_BEFORE_VALUE_CONTENT_SEXP = 5;
    static final int STATE_IN_LONG_STRING = 6;
    static final int STATE_IN_CLOB_DOUBLE_QUOTED_CONTENT = 7;
    static final int STATE_IN_CLOB_TRIPLE_QUOTED_CONTENT = 8;
    static final int STATE_IN_BLOB_CONTENT = 9;
    static final int STATE_AFTER_VALUE_CONTENTS = 10;
    static final int STATE_EOF = 11;
    static final int STATE_MAX = 11;
    static final int ACTION_NOT_DEFINED = 0;
    static final int ACTION_LOAD_FIELD_NAME = 1;
    static final int ACTION_LOAD_ANNOTATION = 2;
    static final int ACTION_START_STRUCT = 3;
    static final int ACTION_START_LIST = 4;
    static final int ACTION_START_SEXP = 5;
    static final int ACTION_START_LOB = 6;
    static final int ACTION_LOAD_SCALAR = 8;
    static final int ACTION_PLUS_INF = 9;
    static final int ACTION_MINUS_INF = 10;
    static final int ACTION_EAT_COMMA = 11;
    static final int ACTION_FINISH_CONTAINER = 12;
    static final int ACTION_FINISH_LOB = 13;
    static final int ACTION_FINISH_DATAGRAM = 14;
    static final int ACTION_EOF = 15;
    static final int ACTION_count = 16;
    static final int[][] TransitionActions = IonReaderTextRawX.makeTransitionActionArray();
    static final int[] TransitionActions2 = IonReaderTextRawX.makeTransition2ActionArray();
    IonReaderTextRawTokensX _scanner;
    boolean _eof;
    int _state;
    IonType[] _container_state_stack = new IonType[10];
    int _container_state_top;
    boolean _container_is_struct;
    boolean _container_prohibits_commas;
    boolean _has_next_called;
    IonType _value_type;
    int _value_keyword;
    IonType _null_type;
    String _field_name;
    int _field_name_sid = -1;
    int _annotation_count;
    SymbolToken[] _annotations;
    boolean _current_value_save_point_loaded;
    UnifiedSavePointManagerX.SavePoint _current_value_save_point;
    boolean _current_value_buffer_loaded;
    StringBuilder _current_value_buffer;
    _Private_ScalarConversions.ValueVariant _v = new _Private_ScalarConversions.ValueVariant();
    long _value_start_offset;
    long _value_start_line;
    long _value_start_column;
    IonType _nesting_parent = null;
    boolean _lob_value_set;
    int _lob_token;
    long _lob_value_position;
    LOB_STATE _lob_loaded;
    byte[] _lob_bytes;
    int _lob_actual_len;

    @Override
    public abstract BigInteger bigIntegerValue();

    private final String get_state_name(int state) {
        switch (state) {
            case 0: {
                return "STATE_BEFORE_ANNOTATION_DATAGRAM";
            }
            case 1: {
                return "STATE_BEFORE_ANNOTATION_CONTAINED";
            }
            case 2: {
                return "STATE_BEFORE_ANNOTATION_SEXP";
            }
            case 3: {
                return "STATE_BEFORE_FIELD_NAME";
            }
            case 4: {
                return "STATE_BEFORE_VALUE_CONTENT";
            }
            case 5: {
                return "STATE_BEFORE_VALUE_CONTENT_SEXP";
            }
            case 6: {
                return "STATE_IN_LONG_STRING";
            }
            case 7: {
                return "STATE_IN_CLOB_DOUBLE_QUOTED_CONTENT";
            }
            case 8: {
                return "STATE_IN_CLOB_TRIPLE_QUOTED_CONTENT";
            }
            case 9: {
                return "STATE_IN_BLOB_CONTENT";
            }
            case 10: {
                return "STATE_AFTER_VALUE_CONTENTS";
            }
            case 11: {
                return "STATE_EOF";
            }
        }
        return "<invalid state: " + Integer.toString(state) + ">";
    }

    private final String get_action_name(int action) {
        switch (action) {
            case 0: {
                return "ACTION_DO_NOTHING";
            }
            case 1: {
                return "ACTION_LOAD_FIELD_NAME";
            }
            case 2: {
                return "ACTION_LOAD_ANNOTATION";
            }
            case 3: {
                return "ACTION_START_STRUCT";
            }
            case 4: {
                return "ACTION_START_LIST";
            }
            case 5: {
                return "ACTION_START_SEXP";
            }
            case 6: {
                return "ACTION_START_LOB";
            }
            case 8: {
                return "ACTION_LOAD_SCALAR";
            }
            case 9: {
                return "ACTION_PLUS_INF";
            }
            case 10: {
                return "ACTION_MINUS_INF";
            }
            case 11: {
                return "ACTION_EAT_COMMA";
            }
            case 12: {
                return "ACTION_FINISH_CONTAINER";
            }
            case 13: {
                return "ACTION_FINISH_LOB";
            }
            case 14: {
                return "ACTION_FINISH_DATAGRAM";
            }
            case 15: {
                return "ACTION_EOF";
            }
        }
        return "<unrecognized action: " + Integer.toString(action) + ">";
    }

    static final int[][] makeTransitionActionArray() {
        int ii;
        int[][] actions = new int[12][27];
        actions[0][0] = 14;
        actions[0][1] = 8;
        actions[0][2] = 8;
        actions[0][26] = 8;
        actions[0][3] = 8;
        actions[0][4] = 8;
        actions[0][5] = 8;
        actions[0][6] = 9;
        actions[0][7] = 10;
        actions[0][8] = 8;
        actions[0][12] = 8;
        actions[0][13] = 8;
        actions[0][9] = 2;
        actions[0][10] = 2;
        actions[0][18] = 5;
        actions[0][20] = 3;
        actions[0][22] = 4;
        actions[0][24] = 6;
        for (ii = 0; ii < 27; ++ii) {
            actions[1][ii] = actions[0][ii];
            actions[2][ii] = actions[0][ii];
            actions[4][ii] = actions[0][ii];
            actions[5][ii] = actions[0][ii];
        }
        actions[1][0] = 0;
        actions[1][19] = 12;
        actions[1][21] = 0;
        actions[1][23] = 12;
        actions[2][0] = 0;
        actions[2][11] = 8;
        actions[2][14] = 8;
        actions[2][19] = 12;
        actions[2][21] = 12;
        actions[2][23] = 12;
        actions[4][0] = 0;
        actions[4][9] = 8;
        actions[4][10] = 8;
        actions[5][0] = 0;
        actions[5][9] = 8;
        actions[5][10] = 8;
        actions[5][11] = 8;
        actions[5][14] = 8;
        actions[3][0] = 0;
        actions[3][9] = 1;
        actions[3][10] = 1;
        actions[3][12] = 1;
        actions[3][13] = 1;
        actions[3][19] = 12;
        actions[3][21] = 12;
        actions[3][23] = 12;
        actions[10][15] = 11;
        actions[10][19] = 12;
        actions[10][21] = 12;
        actions[10][23] = 12;
        actions[7][21] = 13;
        actions[8][21] = 13;
        actions[9][21] = 13;
        for (ii = 0; ii < 27; ++ii) {
            actions[11][ii] = 15;
        }
        return actions;
    }

    static int[] makeTransition2ActionArray() {
        int s_count = 12;
        int t_count = 27;
        int[] a = new int[s_count * t_count];
        for (int s = 0; s < s_count; ++s) {
            for (int t = 0; t < t_count; ++t) {
                int ii = s * 27 + t;
                a[ii] = TransitionActions[s][t];
            }
        }
        return a;
    }

    protected IonReaderTextRawX() {
    }

    @Override
    public <T> T asFacet(Class<T> facetType) {
        return null;
    }

    protected final void init_once() {
        this._current_value_buffer = new StringBuilder();
        this._annotations = new SymbolToken[5];
    }

    protected final void init(UnifiedInputStreamX iis, IonType parent) {
        this.init(iis, parent, 1L, 1L);
    }

    protected final void init(UnifiedInputStreamX iis, IonType parent, long start_line, long start_column) {
        assert (parent != null);
        this._scanner = new IonReaderTextRawTokensX(iis, start_line, start_column);
        this._value_start_line = start_line;
        this._value_start_column = start_column;
        this._current_value_save_point = iis.savePointAllocate();
        this._lob_loaded = LOB_STATE.EMPTY;
        int starting_state = this.get_state_at_container_start(parent);
        this.set_state(starting_state);
        this._eof = false;
        this.push_container_state(parent);
    }

    protected final void re_init(UnifiedInputStreamX iis, IonType parent, long start_line, long start_column) {
        this._state = 0;
        this._container_state_top = 0;
        this._container_is_struct = false;
        this._container_prohibits_commas = false;
        this._has_next_called = false;
        this._value_type = null;
        this._value_keyword = 0;
        this._null_type = null;
        this._field_name = null;
        this._field_name_sid = -1;
        this._annotation_count = 0;
        this._current_value_save_point_loaded = false;
        this._current_value_buffer_loaded = false;
        this._value_start_offset = 0L;
        this._lob_value_set = false;
        this._lob_token = 0;
        this._lob_value_position = 0L;
        this._lob_bytes = null;
        this._lob_actual_len = 0;
        this.init(iis, parent, start_line, start_column);
        this._nesting_parent = parent;
        if (IonType.STRUCT.equals((Object)this._nesting_parent)) {
            this._container_is_struct = true;
        }
    }

    @Override
    public void close() throws IOException {
        this._scanner.close();
    }

    private final void set_state(int new_state) {
        this._state = new_state;
    }

    private final int get_state_int() {
        return this._state;
    }

    private final String get_state_name() {
        String name = this.get_state_name(this.get_state_int());
        return name;
    }

    protected final void clear_current_value_buffer() {
        if (this._current_value_buffer_loaded) {
            this._current_value_buffer.setLength(0);
            this._current_value_buffer_loaded = false;
        }
        if (this._current_value_save_point_loaded) {
            this._current_value_save_point.clear();
            this._current_value_save_point_loaded = false;
        }
    }

    private final void current_value_is_null(IonType null_type) {
        this.clear_current_value_buffer();
        this._value_type = this._null_type;
        this._v.setValueToNull(null_type);
        this._v.setAuthoritativeType(1);
    }

    private final void current_value_is_bool(boolean value) {
        this.clear_current_value_buffer();
        this._value_type = IonType.BOOL;
        this._v.setValue(value);
        this._v.setAuthoritativeType(2);
    }

    private final void set_fieldname(SymbolToken sym) {
        this._field_name = sym.getText();
        this._field_name_sid = sym.getSid();
    }

    private final void clear_fieldname() {
        this._field_name = null;
        this._field_name_sid = -1;
    }

    private final void append_annotation(SymbolToken sym) {
        int oldlen = this._annotations.length;
        if (this._annotation_count >= oldlen) {
            int newlen = oldlen * 2;
            SymbolToken[] temp = new SymbolToken[newlen];
            System.arraycopy(this._annotations, 0, temp, 0, oldlen);
            this._annotations = temp;
        }
        this._annotations[this._annotation_count++] = sym;
    }

    private final void clear_annotation_list() {
        this._annotation_count = 0;
    }

    @Override
    public boolean hasNext() {
        boolean has_next = this.has_next_raw_value();
        return has_next;
    }

    protected final boolean has_next_raw_value() {
        if (!this._has_next_called && !this._eof) {
            try {
                this.finish_value(null);
                this.clear_value();
                this.parse_to_next_value();
            } catch (IOException e) {
                throw new IonException(e);
            }
            this._has_next_called = true;
        }
        return !this._eof;
    }

    @Override
    public IonType next() {
        if (!this.hasNext()) {
            return null;
        }
        if (this._value_type == null && this._scanner.isUnfinishedToken()) {
            try {
                this.token_contents_load(this._scanner.getToken());
            } catch (IOException e) {
                throw new IonException(e);
            }
        }
        this._has_next_called = false;
        return this._value_type;
    }

    private final void finish_and_save_value() throws IOException {
        if (!this._current_value_save_point_loaded) {
            this._scanner.save_point_start(this._current_value_save_point);
            this.finish_value(this._current_value_save_point);
            this._current_value_save_point_loaded = true;
        }
    }

    private final void finish_value(UnifiedSavePointManagerX.SavePoint sp) throws IOException {
        if (this._scanner.isUnfinishedToken()) {
            if (sp != null && this._value_type != null) {
                switch (this._value_type) {
                    case STRUCT: 
                    case SEXP: 
                    case LIST: {
                        sp = null;
                        break;
                    }
                }
            }
            this._scanner.finish_token(sp);
            int new_state = this.get_state_after_value();
            this.set_state(new_state);
        }
        this._has_next_called = false;
    }

    private final void clear_value() {
        this._value_type = null;
        this._null_type = null;
        if (this._lob_value_set) {
            this._lob_value_set = false;
            this._lob_value_position = 0L;
        }
        if (!LOB_STATE.EMPTY.equals((Object)this._lob_loaded)) {
            this._lob_actual_len = -1;
            this._lob_bytes = null;
            this._lob_loaded = LOB_STATE.EMPTY;
        }
        this.clear_current_value_buffer();
        this.clear_annotation_list();
        this.clear_fieldname();
        this._v.clear();
        this._value_start_offset = -1L;
    }

    private final void set_container_flags(IonType t) {
        switch (t) {
            case LIST: {
                this._container_is_struct = false;
                this._container_prohibits_commas = false;
                break;
            }
            case SEXP: {
                this._container_is_struct = false;
                this._container_prohibits_commas = true;
                break;
            }
            case STRUCT: {
                this._container_is_struct = true;
                this._container_prohibits_commas = false;
                break;
            }
            case DATAGRAM: {
                this._container_is_struct = false;
                this._container_prohibits_commas = true;
                break;
            }
            default: {
                throw new IllegalArgumentException("type must be a container, not a " + t.toString());
            }
        }
    }

    private int get_state_after_value() {
        int state_after_scalar;
        switch (this.getContainerType()) {
            case STRUCT: 
            case LIST: {
                state_after_scalar = 10;
                break;
            }
            case SEXP: {
                state_after_scalar = 2;
                break;
            }
            case DATAGRAM: {
                state_after_scalar = 0;
                break;
            }
            default: {
                String message = "invalid container type encountered during parsing " + (Object)((Object)this.getContainerType()) + this._scanner.input_position();
                throw new IonException(message);
            }
        }
        if (this._nesting_parent != null && this.getDepth() == 0) {
            state_after_scalar = 11;
        }
        return state_after_scalar;
    }

    private final int get_state_after_annotation() {
        int state_after_annotation;
        block0 : switch (this.get_state_int()) {
            case 10: {
                IonType container = this.top_state();
                switch (container) {
                    case STRUCT: 
                    case LIST: 
                    case DATAGRAM: {
                        state_after_annotation = 4;
                        break block0;
                    }
                    case SEXP: {
                        state_after_annotation = 5;
                        break block0;
                    }
                }
                String message = "invalid container type encountered during parsing " + (Object)((Object)container) + this._scanner.input_position();
                throw new IonException(message);
            }
            case 0: 
            case 1: {
                state_after_annotation = 4;
                break;
            }
            case 2: {
                state_after_annotation = 5;
                break;
            }
            default: {
                String message = "invalid state encountered during parsing before the value " + this.get_state_name() + this._scanner.input_position();
                throw new IonException(message);
            }
        }
        return state_after_annotation;
    }

    private final int get_state_after_container() {
        IonType container = this.top_state();
        int new_state = this.get_state_after_container(container);
        return new_state;
    }

    private final int get_state_after_container(int token) {
        IonType container = this.top_state();
        switch (container) {
            case STRUCT: {
                this.check_container_close(container, 21, token);
                break;
            }
            case LIST: {
                this.check_container_close(container, 23, token);
                break;
            }
            case SEXP: {
                this.check_container_close(container, 19, token);
                break;
            }
            default: {
                String message = "invalid container type encountered during parsing " + (Object)((Object)container) + this._scanner.input_position();
                throw new IonException(message);
            }
        }
        int new_state = this.get_state_after_container(container);
        return new_state;
    }

    private final int get_state_after_container(IonType container) {
        int new_state;
        if (container == null) {
            new_state = 0;
        } else {
            switch (container) {
                case STRUCT: 
                case LIST: {
                    new_state = 10;
                    break;
                }
                case SEXP: {
                    new_state = 2;
                    break;
                }
                case DATAGRAM: {
                    new_state = 0;
                    break;
                }
                default: {
                    String message = "invalid container type encountered during parsing " + (Object)((Object)container) + this._scanner.input_position();
                    throw new IonException(message);
                }
            }
            if (this._nesting_parent != null && this.getDepth() == 0) {
                new_state = 11;
            }
        }
        return new_state;
    }

    private final void check_container_close(IonType container, int expectedToken, int actualToken) {
        if (actualToken != expectedToken) {
            String message = container.toString().toLowerCase() + " closed by " + IonTokenConstsX.describeToken(actualToken) + this._scanner.input_position();
            throw new IonException(message);
        }
    }

    private final int get_state_at_container_start(IonType container) {
        int new_state;
        if (container == null) {
            new_state = 0;
        } else {
            switch (container) {
                case STRUCT: {
                    new_state = 3;
                    break;
                }
                case LIST: {
                    new_state = 1;
                    break;
                }
                case SEXP: {
                    new_state = 2;
                    break;
                }
                case DATAGRAM: {
                    new_state = 0;
                    break;
                }
                default: {
                    String message = "invalid container type encountered during parsing " + (Object)((Object)container) + this._scanner.input_position();
                    throw new IonException(message);
                }
            }
        }
        return new_state;
    }

    private final SymbolToken parseSymbolToken(String context, StringBuilder sb, int t) throws IOException {
        int sid;
        String text;
        if (t == 9) {
            int kw = IonTokenConstsX.keyword(sb, 0, sb.length());
            switch (kw) {
                case 1: 
                case 2: 
                case 3: 
                case 16: {
                    String reason = "Cannot use unquoted keyword " + sb.toString() + " as " + context;
                    this.parse_error(reason);
                }
                case 17: {
                    text = null;
                    sid = IonTokenConstsX.decodeSid(sb);
                    break;
                }
                default: {
                    text = sb.toString();
                    sid = -1;
                    break;
                }
            }
        } else {
            text = sb.toString();
            sid = -1;
        }
        return new SymbolTokenImpl(text, sid);
    }

    protected final void parse_to_next_value() throws IOException {
        boolean trailing_whitespace = false;
        this._value_start_offset = this._scanner.getStartingOffset();
        this._value_start_line = this._scanner.getLineNumber();
        this._value_start_column = this._scanner.getLineOffset();
        int t = this._scanner.nextToken();
        block52: while (true) {
            int idx = this.get_state_int() * 27 + t;
            int action = TransitionActions2[idx];
            switch (action) {
                case 0: {
                    boolean span_eof = false;
                    if (this._nesting_parent != null) {
                        switch (this._nesting_parent) {
                            case LIST: {
                                if (t != 23) break;
                                span_eof = true;
                                break;
                            }
                            case SEXP: {
                                if (t != 19) break;
                                span_eof = true;
                                break;
                            }
                            case STRUCT: {
                                if (t != 21) break;
                                span_eof = true;
                                break;
                            }
                        }
                    }
                    if (!span_eof) {
                        String message = "invalid syntax [state:" + this.get_state_name() + " on token:" + IonTokenConstsX.getTokenName(t) + "]";
                        this.parse_error(message);
                    }
                    this.set_state(11);
                    this._eof = true;
                    return;
                }
                case 15: {
                    this.set_state(11);
                    this._eof = true;
                    return;
                }
                case 1: {
                    if (!this.is_in_struct_internal()) {
                        throw new IllegalStateException("field names have to be in structs");
                    }
                    this.finish_and_save_value();
                    StringBuilder sb = this.token_contents_load(t);
                    SymbolToken sym = this.parseSymbolToken("a field name", sb, t);
                    this.set_fieldname(sym);
                    this.clear_current_value_buffer();
                    t = this._scanner.nextToken();
                    if (t != 16) {
                        String message = "field name must be followed by a colon, not a " + IonTokenConstsX.getTokenName(t);
                        this.parse_error(message);
                    }
                    this._scanner.tokenIsFinished();
                    this.set_state(1);
                    t = this._scanner.nextToken();
                    continue block52;
                }
                case 2: {
                    int temp_state;
                    StringBuilder sb = this.token_contents_load(t);
                    trailing_whitespace = this._scanner.skip_whitespace();
                    if (!this._scanner.skipDoubleColon()) {
                        temp_state = this.get_state_after_annotation();
                        this.set_state(temp_state);
                        continue block52;
                    }
                    SymbolToken sym = this.parseSymbolToken("an annotation", sb, t);
                    this.append_annotation(sym);
                    this.clear_current_value_buffer();
                    t = this._scanner.nextToken();
                    switch (t) {
                        case 9: 
                        case 10: {
                            continue block52;
                        }
                    }
                    temp_state = this.get_state_after_annotation();
                    this.set_state(temp_state);
                    continue block52;
                }
                case 3: {
                    this._value_type = IonType.STRUCT;
                    int temp_state = 3;
                    this.set_state(temp_state);
                    return;
                }
                case 4: {
                    this._value_type = IonType.LIST;
                    int temp_state = 1;
                    this.set_state(temp_state);
                    return;
                }
                case 5: {
                    this._value_type = IonType.SEXP;
                    int temp_state = 2;
                    this.set_state(temp_state);
                    return;
                }
                case 6: {
                    switch (this._scanner.peekLobStartPunctuation()) {
                        case 12: {
                            this.set_state(7);
                            this._lob_token = 12;
                            this._value_type = IonType.CLOB;
                            break;
                        }
                        case 13: {
                            this.set_state(8);
                            this._lob_token = 13;
                            this._value_type = IonType.CLOB;
                            break;
                        }
                        default: {
                            this.set_state(9);
                            this._lob_token = 24;
                            this._value_type = IonType.BLOB;
                        }
                    }
                    return;
                }
                case 8: {
                    StringBuilder sb;
                    if (t == 9) {
                        sb = this.token_contents_load(t);
                        this._value_keyword = IonTokenConstsX.keyword(sb, 0, sb.length());
                        switch (this._value_keyword) {
                            case 3: {
                                int kwt = trailing_whitespace ? 0 : this._scanner.peekNullTypeSymbol();
                                switch (kwt) {
                                    case 3: {
                                        this._null_type = IonType.NULL;
                                        break;
                                    }
                                    case 4: {
                                        this._null_type = IonType.BOOL;
                                        break;
                                    }
                                    case 5: {
                                        this._null_type = IonType.INT;
                                        break;
                                    }
                                    case 6: {
                                        this._null_type = IonType.FLOAT;
                                        break;
                                    }
                                    case 7: {
                                        this._null_type = IonType.DECIMAL;
                                        break;
                                    }
                                    case 8: {
                                        this._null_type = IonType.TIMESTAMP;
                                        break;
                                    }
                                    case 9: {
                                        this._null_type = IonType.SYMBOL;
                                        break;
                                    }
                                    case 10: {
                                        this._null_type = IonType.STRING;
                                        break;
                                    }
                                    case 11: {
                                        this._null_type = IonType.BLOB;
                                        break;
                                    }
                                    case 12: {
                                        this._null_type = IonType.CLOB;
                                        break;
                                    }
                                    case 13: {
                                        this._null_type = IonType.LIST;
                                        break;
                                    }
                                    case 14: {
                                        this._null_type = IonType.SEXP;
                                        break;
                                    }
                                    case 15: {
                                        this._null_type = IonType.STRUCT;
                                        break;
                                    }
                                    case 0: {
                                        this._null_type = IonType.NULL;
                                        break;
                                    }
                                    default: {
                                        this.parse_error("invalid keyword id (" + kwt + ") encountered while parsing a null");
                                    }
                                }
                                this.current_value_is_null(this._null_type);
                                break;
                            }
                            case 1: {
                                this._value_type = IonType.BOOL;
                                this.current_value_is_bool(true);
                                break;
                            }
                            case 2: {
                                this._value_type = IonType.BOOL;
                                this.current_value_is_bool(false);
                                break;
                            }
                            case 16: {
                                this._value_type = IonType.FLOAT;
                                this.clear_current_value_buffer();
                                this._v.setValue(Double.NaN);
                                this._v.setAuthoritativeType(7);
                                break;
                            }
                            case 17: {
                                int sid = IonTokenConstsX.decodeSid(sb);
                                this._v.setValue(sid);
                                this._v.setAuthoritativeType(3);
                            }
                            default: {
                                this._value_type = IonType.SYMBOL;
                                break;
                            }
                        }
                    } else if (t == 14) {
                        this._value_type = IonType.SYMBOL;
                        this.clear_current_value_buffer();
                        this._v.setValue(".");
                        this._v.setAuthoritativeType(8);
                    } else {
                        this._value_type = IonTokenConstsX.ion_type_of_scalar(t);
                    }
                    int state_after_scalar = this.get_state_after_value();
                    this.set_state(state_after_scalar);
                    return;
                }
                case 9: {
                    this._value_type = IonType.FLOAT;
                    this.clear_current_value_buffer();
                    this._v.setValue(Double.POSITIVE_INFINITY);
                    this._v.setAuthoritativeType(7);
                    int state_after_scalar = this.get_state_after_value();
                    this.set_state(state_after_scalar);
                    return;
                }
                case 10: {
                    this._value_type = IonType.FLOAT;
                    this.clear_current_value_buffer();
                    this._v.setValue(Double.NEGATIVE_INFINITY);
                    this._v.setAuthoritativeType(7);
                    int state_after_scalar = this.get_state_after_value();
                    this.set_state(state_after_scalar);
                    return;
                }
                case 11: {
                    if (this._container_prohibits_commas) {
                        this.parse_error("commas aren't used to separate values in " + this.getContainerType().toString());
                    }
                    int new_state = 1;
                    if (this._container_is_struct) {
                        new_state = 3;
                    }
                    this.set_state(new_state);
                    this._scanner.tokenIsFinished();
                    this._value_start_offset = this._scanner.getStartingOffset();
                    t = this._scanner.nextToken();
                    continue block52;
                }
                case 12: {
                    int new_state = this.get_state_after_container(t);
                    this.set_state(new_state);
                    this._eof = true;
                    return;
                }
                case 13: {
                    int state_after_scalar = this.get_state_after_value();
                    this.set_state(state_after_scalar);
                    return;
                }
                case 14: {
                    if (this.getDepth() != 0) {
                        this.parse_error("state failure end of datagram encounterd with a non-container stack");
                    }
                    this.set_state(11);
                    this._eof = true;
                    return;
                }
            }
            this.parse_error("unexpected token encountered: " + IonTokenConstsX.getTokenName(t));
        }
    }

    protected final StringBuilder token_contents_load(int token_type) throws IOException {
        StringBuilder sb = this._current_value_buffer;
        if (this._current_value_buffer_loaded) {
            return sb;
        }
        if (this._current_value_save_point_loaded) {
            assert (!this._scanner.isUnfinishedToken() && !this._current_value_save_point.isClear());
            this._scanner.save_point_activate(this._current_value_save_point);
            switch (token_type) {
                default: {
                    this._scanner.load_raw_characters(sb);
                    break;
                }
                case 9: {
                    this._scanner.load_symbol_identifier(sb);
                    this._value_type = IonType.SYMBOL;
                    break;
                }
                case 11: {
                    this._scanner.load_symbol_operator(sb);
                    this._value_type = IonType.SYMBOL;
                    break;
                }
                case 10: {
                    boolean clob_chars_only = IonType.CLOB == this._value_type;
                    this._scanner.load_single_quoted_string(sb, clob_chars_only);
                    this._value_type = IonType.SYMBOL;
                    break;
                }
                case 12: {
                    boolean clob_chars_only = IonType.CLOB == this._value_type;
                    this._scanner.load_double_quoted_string(sb, clob_chars_only);
                    this._value_type = IonType.STRING;
                    break;
                }
                case 13: {
                    boolean clob_chars_only = IonType.CLOB == this._value_type;
                    this._scanner.load_triple_quoted_string(sb, clob_chars_only);
                    this._value_type = IonType.STRING;
                }
            }
            this._scanner.save_point_deactivate(this._current_value_save_point);
            this._current_value_buffer_loaded = true;
        } else {
            this._scanner.save_point_start(this._current_value_save_point);
            try {
                switch (token_type) {
                    case 1: 
                    case 2: 
                    case 3: 
                    case 4: 
                    case 5: 
                    case 8: 
                    case 26: {
                        this._value_type = this._scanner.load_number(sb);
                        break;
                    }
                    case 9: {
                        this._scanner.load_symbol_identifier(sb);
                        this._value_type = IonType.SYMBOL;
                        break;
                    }
                    case 11: {
                        this._scanner.load_symbol_operator(sb);
                        this._value_type = IonType.SYMBOL;
                        break;
                    }
                    case 10: {
                        boolean clob_chars_only = IonType.CLOB == this._value_type;
                        int c = this._scanner.load_single_quoted_string(sb, clob_chars_only);
                        if (c == -1) {
                            this._scanner.unexpected_eof();
                        }
                        this._value_type = IonType.SYMBOL;
                        break;
                    }
                    case 12: {
                        boolean clob_chars_only = IonType.CLOB == this._value_type;
                        int c = this._scanner.load_double_quoted_string(sb, clob_chars_only);
                        if (c == -1) {
                            this._scanner.unexpected_eof();
                        }
                        this._value_type = IonType.STRING;
                        break;
                    }
                    case 13: {
                        boolean clob_chars_only = IonType.CLOB == this._value_type;
                        int c = this._scanner.load_triple_quoted_string(sb, clob_chars_only);
                        if (c == -1) {
                            this._scanner.unexpected_eof();
                        }
                        this._value_type = IonType.STRING;
                        break;
                    }
                    default: {
                        String message = "unexpected token " + IonTokenConstsX.getTokenName(token_type) + " encountered";
                        throw new IonException(message);
                    }
                }
                this._current_value_save_point.markEnd();
                this._current_value_save_point_loaded = true;
                this._current_value_buffer_loaded = true;
                this.tokenValueIsFinished();
            } catch (IonException e) {
                this._current_value_save_point.clear();
                this._current_value_save_point_loaded = false;
                throw e;
            }
        }
        return sb;
    }

    protected void tokenValueIsFinished() {
        this._scanner.tokenIsFinished();
        if (IonType.BLOB.equals((Object)this._value_type) || IonType.CLOB.equals((Object)this._value_type)) {
            int state_after_scalar = this.get_state_after_value();
            this.set_state(state_after_scalar);
        }
    }

    private final void push_container_state(IonType newContainer) {
        int oldlen = this._container_state_stack.length;
        if (this._container_state_top >= oldlen) {
            int newlen = oldlen * 2;
            IonType[] temp = new IonType[newlen];
            System.arraycopy(this._container_state_stack, 0, temp, 0, oldlen);
            this._container_state_stack = temp;
        }
        this.set_container_flags(newContainer);
        this._container_state_stack[this._container_state_top++] = newContainer;
    }

    private final void pop_container_state() {
        --this._container_state_top;
        this.set_container_flags(this.top_state());
        this._eof = false;
        this._has_next_called = false;
        int new_state = this.get_state_after_container();
        this.set_state(new_state);
    }

    private final IonType top_state() {
        int top = this._container_state_top - 1;
        IonType top_container = this._container_state_stack[top];
        return top_container;
    }

    @Override
    public IonType getType() {
        return this._value_type;
    }

    @Override
    public boolean isInStruct() {
        boolean in_struct = false;
        IonType container = this.getContainerType();
        if (IonType.STRUCT.equals((Object)container)) {
            if (this.getDepth() > 0) {
                in_struct = true;
            } else assert (IonType.STRUCT.equals((Object)this._nesting_parent));
        }
        return in_struct;
    }

    private boolean is_in_struct_internal() {
        boolean in_struct = false;
        IonType container = this.getContainerType();
        if (IonType.STRUCT.equals((Object)container)) {
            in_struct = true;
        }
        return in_struct;
    }

    public IonType getContainerType() {
        if (this._container_state_top == 0) {
            return IonType.DATAGRAM;
        }
        return this._container_state_stack[this._container_state_top - 1];
    }

    @Override
    public int getDepth() {
        int depth = this._container_state_top;
        if (depth > 0) {
            int debugging_depth = depth--;
            IonType top_type = this._container_state_stack[0];
            if (this._nesting_parent == null) {
                if (IonType.DATAGRAM.equals((Object)top_type)) {
                    // empty if block
                }
            } else if (this._nesting_parent.equals((Object)top_type)) {
                --depth;
            }
            if (depth == debugging_depth) {
                System.err.println("so here's a case where we didn't subtract 1");
            }
        }
        return depth;
    }

    @Override
    public String getFieldName() {
        if (this.getDepth() == 0 && this.is_in_struct_internal()) {
            return null;
        }
        String name = this._field_name;
        if (name == null && this._field_name_sid > 0) {
            throw new UnknownSymbolException(this._field_name_sid);
        }
        return name;
    }

    final String getRawFieldName() {
        if (this.getDepth() == 0 && this.is_in_struct_internal()) {
            return null;
        }
        return this._field_name;
    }

    @Override
    public int getFieldId() {
        if (this.getDepth() == 0 && this.is_in_struct_internal()) {
            return -1;
        }
        return this._field_name_sid;
    }

    @Override
    public SymbolToken getFieldNameSymbol() {
        if (this.getDepth() == 0 && this.is_in_struct_internal()) {
            return null;
        }
        String name = this._field_name;
        int sid = this.getFieldId();
        if (name == null && sid == -1) {
            return null;
        }
        return new SymbolTokenImpl(name, sid);
    }

    @Override
    public Iterator<String> iterateTypeAnnotations() {
        return _Private_Utils.stringIterator(this.getTypeAnnotations());
    }

    @Override
    public void stepIn() {
        if (this._value_type == null || this._eof) {
            throw new IllegalStateException();
        }
        switch (this._value_type) {
            case STRUCT: 
            case SEXP: 
            case LIST: {
                break;
            }
            default: {
                throw new IllegalStateException("Unexpected value type: " + (Object)((Object)this._value_type));
            }
        }
        int new_state = this.get_state_at_container_start(this._value_type);
        this.set_state(new_state);
        this.push_container_state(this._value_type);
        this._scanner.tokenIsFinished();
        try {
            this.finish_value(null);
        } catch (IOException e) {
            throw new IonException(e);
        }
        if (this._v.isNull()) {
            this._eof = true;
            this._has_next_called = true;
        }
        this._value_type = null;
    }

    @Override
    public void stepOut() {
        if (this.getDepth() < 1) {
            throw new IllegalStateException("Cannot stepOut any further, already at top level.");
        }
        try {
            this.finish_value(null);
            switch (this.getContainerType()) {
                case STRUCT: {
                    if (!this._eof) {
                        this._scanner.skip_over_struct();
                    }
                    break;
                }
                case LIST: {
                    if (!this._eof) {
                        this._scanner.skip_over_list();
                    }
                    break;
                }
                case SEXP: {
                    if (!this._eof) {
                        this._scanner.skip_over_sexp();
                    }
                    break;
                }
                case DATAGRAM: {
                    break;
                }
                default: {
                    throw new IllegalStateException("Unexpected value type: " + (Object)((Object)this._value_type));
                }
            }
        } catch (IOException e) {
            throw new IonException(e);
        }
        this.pop_container_state();
        this._scanner.tokenIsFinished();
        try {
            this.finish_value(null);
        } catch (IOException e) {
            throw new IonException(e);
        }
        this.clear_value();
    }

    @Override
    public SymbolTable getSymbolTable() {
        return null;
    }

    protected final void parse_error(String reason) {
        String message = "Syntax error" + this._scanner.input_position() + ": " + reason;
        throw new IonReaderTextParsingException(message);
    }

    protected final void parse_error(Exception e) {
        String message = "Syntax error at " + this._scanner.input_position() + ": " + e.getLocalizedMessage();
        throw new IonReaderTextParsingException(message, e);
    }

    public static class IonReaderTextParsingException
    extends IonException {
        private static final long serialVersionUID = 1L;

        IonReaderTextParsingException(String msg) {
            super(msg);
        }

        IonReaderTextParsingException(Exception e) {
            super(e);
        }

        IonReaderTextParsingException(String msg, Exception e) {
            super(msg, e);
        }
    }

    static enum LOB_STATE {
        EMPTY,
        READ,
        FINISHED;

    }
}

