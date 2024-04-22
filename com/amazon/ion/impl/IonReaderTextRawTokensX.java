/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazon.ion.impl;

import com.amazon.ion.IonException;
import com.amazon.ion.IonType;
import com.amazon.ion.UnexpectedEofException;
import com.amazon.ion.impl.Base64Encoder;
import com.amazon.ion.impl.IonTokenConstsX;
import com.amazon.ion.impl.IonUTF8;
import com.amazon.ion.impl.UnifiedInputStreamX;
import com.amazon.ion.impl.UnifiedSavePointManagerX;
import com.amazon.ion.impl._Private_IonConstants;
import com.amazon.ion.util.IonTextUtils;
import java.io.IOException;
import java.util.ArrayList;

final class IonReaderTextRawTokensX {
    static final boolean _debug = false;
    private static final Appendable NULL_APPENDABLE = new Appendable(){

        @Override
        public Appendable append(CharSequence csq) throws IOException {
            return this;
        }

        @Override
        public Appendable append(CharSequence csq, int start, int end) throws IOException {
            return this;
        }

        @Override
        public Appendable append(char c) throws IOException {
            return this;
        }
    };
    static final int BASE64_EOF = 128;
    static final int[] BASE64_CHAR_TO_BIN = Base64Encoder.Base64EncodingCharToInt;
    static final int BASE64_TERMINATOR_CHAR = Base64Encoder.Base64EncodingTerminator;
    private UnifiedInputStreamX _stream = null;
    private int _token = -1;
    private boolean _unfinished_token;
    private long _line_count;
    private long _line_starting_position;
    private boolean _line_count_has_cached = false;
    private long _line_count_cached;
    private long _line_offset_cached;
    private int _base64_prefetch_count;
    private int _base64_prefetch_stack;
    private static final int CONTAINER_STACK_INITIAL_CAPACITY = 16;
    private final ArrayList<Integer> containerSkipTerminatorStack = new ArrayList(16);

    public IonReaderTextRawTokensX(UnifiedInputStreamX iis) {
        this(iis, 1L, 1L);
    }

    public IonReaderTextRawTokensX(UnifiedInputStreamX iis, long starting_line, long starting_column) {
        this._stream = iis;
        this._line_count = starting_line;
        this._line_starting_position = this._stream.getPosition() - starting_column;
    }

    public void close() throws IOException {
        this._stream.close();
    }

    public int getToken() {
        return this._token;
    }

    public long getLineNumber() {
        return this._line_count;
    }

    public long getLineOffset() {
        long stream_position = this._stream.getPosition();
        long offset = stream_position - this._line_starting_position;
        return offset;
    }

    UnifiedInputStreamX getSourceStream() {
        return this._stream;
    }

    public final boolean isBufferedInput() {
        boolean is_buffered = !this._stream._is_stream;
        return is_buffered;
    }

    protected String input_position() {
        String s = " at line " + this.getLineNumber() + " offset " + this.getLineOffset();
        return s;
    }

    public final boolean isUnfinishedToken() {
        return this._unfinished_token;
    }

    public final void tokenIsFinished() {
        this._unfinished_token = false;
        this._base64_prefetch_count = 0;
    }

    protected final int read_char() throws IOException {
        int c = this._stream.read();
        if (c == 13 || c == 10) {
            c = this.line_count(c);
        }
        return c;
    }

    protected final int read_string_char(ProhibitedCharacters prohibitedCharacters) throws IOException {
        int c = this._stream.read();
        if (prohibitedCharacters.includes(c)) {
            this.error("invalid character [" + IonTextUtils.printCodePointAsString(c) + "]");
        }
        if (c == 13 || c == 10 || c == 92) {
            c = this.line_count(c);
        }
        return c;
    }

    private final void unread_char(int c) {
        block11: {
            block10: {
                if (c >= 0) break block10;
                switch (c) {
                    case -4: {
                        this.line_count_unread(c);
                        this._stream.unread(10);
                        break block11;
                    }
                    case -5: {
                        this.line_count_unread(c);
                        this._stream.unread(13);
                        break block11;
                    }
                    case -6: {
                        this.line_count_unread(c);
                        this._stream.unread(10);
                        this._stream.unread(13);
                        break block11;
                    }
                    case -7: {
                        this._stream.unread(10);
                        this._stream.unread(92);
                        break block11;
                    }
                    case -8: {
                        this._stream.unread(13);
                        this._stream.unread(92);
                        break block11;
                    }
                    case -9: {
                        this._stream.unread(10);
                        this._stream.unread(13);
                        this._stream.unread(92);
                        break block11;
                    }
                    case -1: {
                        this._stream.unread(-1);
                        break block11;
                    }
                    default: {
                        assert (false) : "INVALID SPECIAL CHARACTER ENCOUNTERED: " + c;
                        break block11;
                    }
                }
            }
            this._stream.unread(c);
        }
    }

    private final int line_count_unread(int c) {
        assert (c == -4 || c == -5 || c == -6 || c == -7 || c == -8 || c == -9);
        if (this._line_count_has_cached) {
            this._line_count = this._line_count_cached;
            this._line_starting_position = this._line_offset_cached;
            this._line_count_has_cached = false;
        }
        return c;
    }

    private final int line_count(int c) throws IOException {
        block0 : switch (c) {
            case 92: {
                int c2 = this._stream.read();
                switch (c2) {
                    case 13: {
                        int c3 = this._stream.read();
                        if (c3 != 10) {
                            this.unread_char(c3);
                            c = -8;
                            break block0;
                        }
                        c = -9;
                        break block0;
                    }
                    case 10: {
                        c = -7;
                        break block0;
                    }
                }
                this.unread_char(c2);
                return c;
            }
            case 13: {
                int c2 = this._stream.read();
                if (c2 == 10) {
                    c = -6;
                    break;
                }
                this.unread_char(c2);
                c = -5;
                break;
            }
            case 10: {
                c = -4;
                break;
            }
            default: {
                throw new IllegalStateException();
            }
        }
        this._line_count_cached = this._line_count++;
        this._line_offset_cached = this._line_starting_position;
        this._line_count_has_cached = true;
        this._line_starting_position = this._stream.getPosition() - 1L;
        return c;
    }

    public final boolean skipDoubleColon() throws IOException {
        int c = this.skip_over_whitespace();
        if (c != 58) {
            this.unread_char(c);
            return false;
        }
        c = this.read_char();
        if (c != 58) {
            this.unread_char(c);
            this.unread_char(58);
            return false;
        }
        return true;
    }

    public final int peekNullTypeSymbol() throws IOException {
        int kw;
        int c = this.read_char();
        if (c != 46) {
            this.unread_char(c);
            return 0;
        }
        int[] read_ahead = new int[IonTokenConstsX.TN_MAX_NAME_LENGTH + 1];
        int read_count = 0;
        int possible_names = 8191;
        while (read_count < IonTokenConstsX.TN_MAX_NAME_LENGTH + 1) {
            c = this.read_char();
            read_ahead[read_count++] = c;
            int letter_idx = IonTokenConstsX.typeNameLetterIdx(c);
            if (letter_idx < 1) {
                if (IonTokenConstsX.isValidTerminatingCharForInf(c)) break;
                return this.peekNullTypeSymbolUndo(read_ahead, read_count);
            }
            int mask = IonTokenConstsX.typeNamePossibilityMask(read_count - 1, letter_idx);
            if ((possible_names &= mask) != 0) continue;
            return this.peekNullTypeSymbolUndo(read_ahead, read_count);
        }
        if ((kw = IonTokenConstsX.typeNameKeyWordFromMask(possible_names, read_count - 1)) == -1) {
            this.peekNullTypeSymbolUndo(read_ahead, read_count);
        } else {
            this.unread_char(c);
        }
        return kw;
    }

    private final int peekNullTypeSymbolUndo(int[] read_ahead, int read_count) {
        String type_error = "";
        for (int ii = 0; ii < read_count; ++ii) {
            type_error = type_error + (char)read_ahead[ii];
        }
        String message = "invalid type name on a typed null value";
        this.error(message);
        return -1;
    }

    public final int peekLobStartPunctuation() throws IOException {
        int c = this.skip_over_lob_whitespace();
        if (c == 34) {
            return 12;
        }
        if (c != 39) {
            this.unread_char(c);
            return -1;
        }
        c = this.read_char();
        if (c != 39) {
            this.unread_char(c);
            this.unread_char(39);
            return -1;
        }
        c = this.read_char();
        if (c != 39) {
            this.unread_char(c);
            this.unread_char(39);
            this.unread_char(39);
            return -1;
        }
        return 13;
    }

    protected final void skip_clob_close_punctuation() throws IOException {
        int c = this.skip_over_clob_whitespace();
        if (c == 125) {
            c = this.read_char();
            if (c == 125) {
                return;
            }
            this.unread_char(c);
            c = 125;
        }
        this.unread_char(c);
        this.error("invalid closing puctuation for CLOB");
    }

    protected final void finish_token(UnifiedSavePointManagerX.SavePoint sp) throws IOException {
        if (this._unfinished_token) {
            int c = this.skip_to_end(sp);
            this.unread_char(c);
            this._unfinished_token = false;
        }
    }

    private final int skip_to_end(UnifiedSavePointManagerX.SavePoint sp) throws IOException {
        int c;
        switch (this._token) {
            case 1: {
                c = this.skip_over_number(sp);
                break;
            }
            case 2: {
                c = this.skip_over_int(sp);
                break;
            }
            case 3: {
                c = this.skipOverRadix(sp, Radix.HEX);
                break;
            }
            case 26: {
                c = this.skipOverRadix(sp, Radix.BINARY);
                break;
            }
            case 4: {
                c = this.skip_over_decimal(sp);
                break;
            }
            case 5: {
                c = this.skip_over_float(sp);
                break;
            }
            case 8: {
                c = this.skip_over_timestamp(sp);
                break;
            }
            case 9: {
                c = this.skip_over_symbol_identifier(sp);
                break;
            }
            case 10: {
                assert (!this.is_2_single_quotes_helper());
                c = this.skip_single_quoted_string(sp);
                break;
            }
            case 11: {
                c = this.skip_over_symbol_operator(sp);
                break;
            }
            case 12: {
                this.skip_double_quoted_string_helper();
                c = this.skip_over_whitespace();
                break;
            }
            case 13: {
                this.skip_triple_quoted_string(sp);
                c = this.skip_over_whitespace();
                break;
            }
            case 24: {
                this.skip_over_blob(sp);
                c = this.read_char();
                break;
            }
            case 20: {
                assert (sp == null);
                this.skip_over_struct();
                c = this.read_char();
                break;
            }
            case 18: {
                this.skip_over_sexp();
                c = this.read_char();
                break;
            }
            case 22: {
                this.skip_over_list();
                c = this.read_char();
                break;
            }
            default: {
                c = -1;
                this.error("token " + IonTokenConstsX.getTokenName(this._token) + " unexpectedly encounterd as \"unfinished\"");
            }
        }
        if (IonTokenConstsX.isWhitespace(c)) {
            c = this.skip_over_whitespace();
        }
        this._unfinished_token = false;
        return c;
    }

    public final long getStartingOffset() throws IOException {
        int c = this._unfinished_token ? this.skip_to_end(null) : this.skip_over_whitespace();
        this.unread_char(c);
        long pos = this._stream.getPosition();
        return pos;
    }

    public final int nextToken() throws IOException {
        int t = -1;
        int c = this._unfinished_token ? this.skip_to_end(null) : this.skip_over_whitespace();
        this._unfinished_token = true;
        switch (c) {
            case -1: {
                return this.next_token_finish(0, true);
            }
            case 58: {
                int c2 = this.read_char();
                if (c2 != 58) {
                    this.unread_char(c2);
                    return this.next_token_finish(16, true);
                }
                return this.next_token_finish(17, true);
            }
            case 123: {
                int c2 = this.read_char();
                if (c2 != 123) {
                    this.unread_char(c2);
                    return this.next_token_finish(20, true);
                }
                return this.next_token_finish(24, true);
            }
            case 125: {
                return this.next_token_finish(21, false);
            }
            case 91: {
                return this.next_token_finish(22, true);
            }
            case 93: {
                return this.next_token_finish(23, false);
            }
            case 40: {
                return this.next_token_finish(18, true);
            }
            case 41: {
                return this.next_token_finish(19, false);
            }
            case 44: {
                return this.next_token_finish(15, false);
            }
            case 46: {
                int c2 = this.read_char();
                this.unread_char(c2);
                if (IonTokenConstsX.isValidExtendedSymbolCharacter(c2)) {
                    this.unread_char(46);
                    return this.next_token_finish(11, true);
                }
                return this.next_token_finish(14, false);
            }
            case 39: {
                if (this.is_2_single_quotes_helper()) {
                    return this.next_token_finish(13, true);
                }
                return this.next_token_finish(10, true);
            }
            case 43: {
                if (this.peek_inf_helper(c)) {
                    return this.next_token_finish(6, false);
                }
                this.unread_char(c);
                return this.next_token_finish(11, true);
            }
            case 33: 
            case 35: 
            case 37: 
            case 38: 
            case 42: 
            case 47: 
            case 59: 
            case 60: 
            case 61: 
            case 62: 
            case 63: 
            case 64: 
            case 94: 
            case 96: 
            case 124: 
            case 126: {
                this.unread_char(c);
                return this.next_token_finish(11, true);
            }
            case 34: {
                return this.next_token_finish(12, true);
            }
            case 36: 
            case 65: 
            case 66: 
            case 67: 
            case 68: 
            case 69: 
            case 70: 
            case 71: 
            case 72: 
            case 73: 
            case 74: 
            case 75: 
            case 76: 
            case 77: 
            case 78: 
            case 79: 
            case 80: 
            case 81: 
            case 82: 
            case 83: 
            case 84: 
            case 85: 
            case 86: 
            case 87: 
            case 88: 
            case 89: 
            case 90: 
            case 95: 
            case 97: 
            case 98: 
            case 99: 
            case 100: 
            case 101: 
            case 102: 
            case 103: 
            case 104: 
            case 105: 
            case 106: 
            case 107: 
            case 108: 
            case 109: 
            case 110: 
            case 111: 
            case 112: 
            case 113: 
            case 114: 
            case 115: 
            case 116: 
            case 117: 
            case 118: 
            case 119: 
            case 120: 
            case 121: 
            case 122: {
                this.unread_char(c);
                return this.next_token_finish(9, true);
            }
            case 48: 
            case 49: 
            case 50: 
            case 51: 
            case 52: 
            case 53: 
            case 54: 
            case 55: 
            case 56: 
            case 57: {
                t = this.scan_for_numeric_type(c);
                this.unread_char(c);
                return this.next_token_finish(t, true);
            }
            case 45: {
                int c2 = this.read_char();
                this.unread_char(c2);
                if (IonTokenConstsX.isDigit(c2)) {
                    t = this.scan_negative_for_numeric_type(c);
                    this.unread_char(c);
                    return this.next_token_finish(t, true);
                }
                if (this.peek_inf_helper(c)) {
                    return this.next_token_finish(7, false);
                }
                this.unread_char(c);
                return this.next_token_finish(11, true);
            }
        }
        this.bad_token_start(c);
        throw new IonException("invalid state: next token switch shouldn't exit");
    }

    private final int next_token_finish(int token, boolean content_is_waiting) {
        this._token = token;
        this._unfinished_token = content_is_waiting;
        return this._token;
    }

    private final int skip_over_whitespace() throws IOException {
        return this.skip_over_whitespace(CommentStrategy.IGNORE);
    }

    private final int skip_over_whitespace(CommentStrategy commentStrategy) throws IOException {
        this.skip_whitespace(commentStrategy);
        return this.read_char();
    }

    private final int skip_over_lob_whitespace() throws IOException {
        return this.skip_over_blob_whitespace();
    }

    private final int skip_over_blob_whitespace() throws IOException {
        return this.skip_over_whitespace(CommentStrategy.BREAK);
    }

    private final int skip_over_clob_whitespace() throws IOException {
        return this.skip_over_whitespace(CommentStrategy.ERROR);
    }

    protected final boolean skip_whitespace() throws IOException {
        return this.skip_whitespace(CommentStrategy.IGNORE);
    }

    private final boolean skip_whitespace(CommentStrategy commentStrategy) throws IOException {
        int c;
        boolean any_whitespace = false;
        block5: while (true) {
            c = this.read_char();
            switch (c) {
                case -1: {
                    break block5;
                }
                case -9: 
                case -8: 
                case -7: 
                case -6: 
                case -5: 
                case -4: 
                case 9: 
                case 32: {
                    any_whitespace = true;
                    continue block5;
                }
                case 47: {
                    if (!commentStrategy.onComment(this)) break block5;
                    any_whitespace = true;
                    continue block5;
                }
            }
            break;
        }
        this.unread_char(c);
        return any_whitespace;
    }

    private final void skip_single_line_comment() throws IOException {
        while (true) {
            int c = this.read_char();
            switch (c) {
                case -9: 
                case -8: 
                case -7: 
                case -6: 
                case -5: 
                case -4: {
                    return;
                }
                case -1: {
                    return;
                }
            }
        }
    }

    private final void skip_block_comment() throws IOException {
        while (true) {
            int c = this.read_char();
            switch (c) {
                case 42: {
                    do {
                        if ((c = this.read_char()) != 47) continue;
                        return;
                    } while (c == 42);
                    break;
                }
                case -1: {
                    this.bad_token_start(c);
                }
            }
        }
    }

    private final boolean is_2_single_quotes_helper() throws IOException {
        int c = this.read_char();
        if (c != 39) {
            this.unread_char(c);
            return false;
        }
        c = this.read_char();
        if (c != 39) {
            this.unread_char(c);
            this.unread_char(39);
            return false;
        }
        return true;
    }

    private final boolean peek_inf_helper(int c) throws IOException {
        if (c != 43 && c != 45) {
            return false;
        }
        c = this.read_char();
        if (c == 105) {
            c = this.read_char();
            if (c == 110) {
                c = this.read_char();
                if (c == 102) {
                    c = this.read_char();
                    if (this.is_value_terminating_character(c)) {
                        this.unread_char(c);
                        return true;
                    }
                    this.unread_char(c);
                    c = 102;
                }
                this.unread_char(c);
                c = 110;
            }
            this.unread_char(c);
            c = 105;
        }
        this.unread_char(c);
        return false;
    }

    private final int scan_for_numeric_type(int c1) throws IOException {
        int t = 1;
        int[] read_chars = new int[6];
        int read_char_count = 0;
        if (!IonTokenConstsX.isDigit(c1)) {
            this.error(String.format("Expected digit, got U+%04X", c1));
        }
        int c = this.read_char();
        read_chars[read_char_count++] = c;
        if (c1 == 48) {
            switch (c) {
                case 88: 
                case 120: {
                    t = 3;
                    break;
                }
                case 68: 
                case 100: {
                    t = 4;
                    break;
                }
                case 69: 
                case 101: {
                    t = 5;
                    break;
                }
                case 66: 
                case 98: {
                    t = 26;
                    break;
                }
                case 46: {
                    break;
                }
                default: {
                    if (!this.is_value_terminating_character(c)) break;
                    t = 2;
                }
            }
        }
        if (t == 1 && IonTokenConstsX.isDigit(c)) {
            c = this.read_char();
            read_chars[read_char_count++] = c;
            if (IonTokenConstsX.isDigit(c)) {
                c = this.read_char();
                read_chars[read_char_count++] = c;
                if (IonTokenConstsX.isDigit(c)) {
                    c = this.read_char();
                    read_chars[read_char_count++] = c;
                    if (c == 45 || c == 84) {
                        t = 8;
                    }
                }
            }
        }
        do {
            c = read_chars[--read_char_count];
            this.unread_char(c);
        } while (read_char_count > 0);
        return t;
    }

    private final boolean is_value_terminating_character(int c) throws IOException {
        boolean isTerminator;
        switch (c) {
            case 47: {
                c = this.read_char();
                this.unread_char(c);
                isTerminator = c == 47 || c == 42;
                break;
            }
            case -9: 
            case -8: 
            case -7: 
            case -6: 
            case -5: 
            case -4: {
                isTerminator = true;
                break;
            }
            default: {
                isTerminator = IonTextUtils.isNumericStop(c);
            }
        }
        return isTerminator;
    }

    private final int scan_negative_for_numeric_type(int c) throws IOException {
        assert (c == 45);
        c = this.read_char();
        int t = this.scan_for_numeric_type(c);
        if (t == 8) {
            this.bad_token(c);
        }
        this.unread_char(c);
        return t;
    }

    protected void load_raw_characters(StringBuilder sb) throws IOException {
        int c = this.read_char();
        block4: while (true) {
            c = this.read_char();
            switch (c) {
                case -9: 
                case -8: 
                case -7: {
                    continue block4;
                }
                case -1: {
                    return;
                }
            }
            if (!IonTokenConstsX.is7bitValue(c)) {
                c = this.read_large_char_sequence(c);
            }
            if (IonUTF8.needsSurrogateEncoding(c)) {
                sb.append(IonUTF8.highSurrogate(c));
                c = IonUTF8.lowSurrogate(c);
            }
            sb.append((char)c);
        }
    }

    protected void skip_over_struct() throws IOException {
        this.skip_over_container(125);
    }

    protected void skip_over_list() throws IOException {
        this.skip_over_container(93);
    }

    protected void skip_over_sexp() throws IOException {
        this.skip_over_container(41);
    }

    private void skip_over_container(int terminator) throws IOException {
        assert (terminator == 125 || terminator == 93 || terminator == 41);
        this.containerSkipTerminatorStack.clear();
        block9: while (true) {
            int c = this.skip_over_whitespace();
            switch (c) {
                case -1: {
                    this.unexpected_eof();
                }
                case 41: 
                case 93: 
                case 125: {
                    if (c != terminator) continue block9;
                    if (this.containerSkipTerminatorStack.isEmpty()) {
                        return;
                    }
                    terminator = this.containerSkipTerminatorStack.remove(this.containerSkipTerminatorStack.size() - 1);
                    continue block9;
                }
                case 34: {
                    this.skip_double_quoted_string_helper();
                    continue block9;
                }
                case 39: {
                    if (this.is_2_single_quotes_helper()) {
                        this.skip_triple_quoted_string(null);
                        continue block9;
                    }
                    c = this.skip_single_quoted_string(null);
                    this.unread_char(c);
                    continue block9;
                }
                case 40: {
                    this.containerSkipTerminatorStack.add(terminator);
                    terminator = 41;
                    continue block9;
                }
                case 91: {
                    this.containerSkipTerminatorStack.add(terminator);
                    terminator = 93;
                    continue block9;
                }
                case 123: {
                    c = this.read_char();
                    if (c == 123) {
                        int lobType;
                        c = this.skip_over_lob_whitespace();
                        if (c == 34) {
                            lobType = 12;
                        } else if (c == 39) {
                            if (!this.is_2_single_quotes_helper()) {
                                this.error("invalid single quote in lob content");
                            }
                            lobType = 13;
                        } else {
                            this.unread_char(c);
                            lobType = 24;
                        }
                        this.skip_over_lob(lobType, null);
                        continue block9;
                    }
                    if (c == 125) continue block9;
                    this.unread_char(c);
                    this.containerSkipTerminatorStack.add(terminator);
                    terminator = 125;
                    continue block9;
                }
            }
        }
    }

    private int skip_over_number(UnifiedSavePointManagerX.SavePoint sp) throws IOException {
        int c = this.read_char();
        if (c == 45) {
            c = this.read_char();
        }
        if ((c = this.skip_over_digits(c)) == 46) {
            c = this.read_char();
            c = this.skip_over_digits(c);
        }
        if (c == 100 || c == 68 || c == 101 || c == 69) {
            c = this.read_char();
            if (c == 45 || c == 43) {
                c = this.read_char();
            }
            c = this.skip_over_digits(c);
        }
        if (!this.is_value_terminating_character(c)) {
            this.bad_token(c);
        }
        if (sp != null) {
            sp.markEnd(-1);
        }
        return c;
    }

    private int skip_over_int(UnifiedSavePointManagerX.SavePoint sp) throws IOException {
        int c = this.read_char();
        if (c == 45) {
            c = this.read_char();
        }
        if (!this.is_value_terminating_character(c = this.skip_over_digits(c))) {
            this.bad_token(c);
        }
        if (sp != null) {
            sp.markEnd(-1);
        }
        return c;
    }

    private int skip_over_digits(int c) throws IOException {
        while (IonTokenConstsX.isDigit(c)) {
            c = this.read_char();
        }
        return c;
    }

    private int skipOverRadix(UnifiedSavePointManagerX.SavePoint sp, Radix radix) throws IOException {
        int c = this.read_char();
        if (c == 45) {
            c = this.read_char();
        }
        assert (c == 48);
        c = this.read_char();
        radix.assertPrefix(c);
        c = this.readNumeric(NULL_APPENDABLE, radix);
        if (!this.is_value_terminating_character(c)) {
            this.bad_token(c);
        }
        if (sp != null) {
            sp.markEnd(-1);
        }
        return c;
    }

    private int skip_over_decimal(UnifiedSavePointManagerX.SavePoint sp) throws IOException {
        int c = this.skip_over_number(sp);
        return c;
    }

    private int skip_over_float(UnifiedSavePointManagerX.SavePoint sp) throws IOException {
        int c = this.skip_over_number(sp);
        return c;
    }

    private int skip_over_timestamp(UnifiedSavePointManagerX.SavePoint sp) throws IOException {
        int c = this.skip_timestamp_past_digits(4);
        if (c == 84) {
            if (sp != null) {
                sp.markEnd(0);
            }
            return this.skip_over_whitespace();
        }
        if (c != 45) {
            this.error("invalid timestamp encountered");
        }
        if ((c = this.skip_timestamp_past_digits(2)) == 84) {
            if (sp != null) {
                sp.markEnd(0);
            }
            return this.skip_over_whitespace();
        }
        this.skip_timestamp_validate(c, 45);
        c = this.skip_timestamp_past_digits(2);
        if (c != 84) {
            return this.skip_timestamp_finish(c, sp);
        }
        c = this.read_char();
        if (!IonTokenConstsX.isDigit(c)) {
            return this.skip_timestamp_finish(this.skip_optional_timestamp_offset(c), sp);
        }
        c = this.skip_timestamp_past_digits(1);
        if (c != 58) {
            this.bad_token(c);
        }
        if ((c = this.skip_timestamp_past_digits(2)) != 58) {
            return this.skip_timestamp_offset_or_z(c, sp);
        }
        c = this.skip_timestamp_past_digits(2);
        if (c != 46) {
            return this.skip_timestamp_offset_or_z(c, sp);
        }
        c = this.read_char();
        if (IonTokenConstsX.isDigit(c)) {
            c = this.skip_over_digits(c);
        }
        return this.skip_timestamp_offset_or_z(c, sp);
    }

    private int skip_timestamp_finish(int c, UnifiedSavePointManagerX.SavePoint sp) throws IOException {
        if (!this.is_value_terminating_character(c)) {
            this.bad_token(c);
        }
        if (sp != null) {
            sp.markEnd(-1);
        }
        return c;
    }

    private int skip_optional_timestamp_offset(int c) throws IOException {
        if (c == 45 || c == 43) {
            c = this.skip_timestamp_past_digits(2);
            if (c != 58) {
                this.bad_token(c);
            }
            c = this.skip_timestamp_past_digits(2);
        }
        return c;
    }

    private int skip_timestamp_offset_or_z(int c, UnifiedSavePointManagerX.SavePoint sp) throws IOException {
        if (c == 45 || c == 43) {
            c = this.skip_timestamp_past_digits(2);
            if (c != 58) {
                this.bad_token(c);
            }
            c = this.skip_timestamp_past_digits(2);
        } else if (c == 90 || c == 122) {
            c = this.read_char();
        } else {
            this.bad_token(c);
        }
        return this.skip_timestamp_finish(c, sp);
    }

    private final void skip_timestamp_validate(int c, int expected) {
        if (c != expected) {
            this.error("invalid character '" + (char)c + "' encountered in timestamp (when '" + (char)expected + "' was expected");
        }
    }

    private final int skip_timestamp_past_digits(int len) throws IOException {
        return this.skip_timestamp_past_digits(len, len);
    }

    private final int skip_timestamp_past_digits(int min, int max) throws IOException {
        int c;
        while (min > 0) {
            c = this.read_char();
            if (!IonTokenConstsX.isDigit(c)) {
                this.error("invalid character '" + (char)c + "' encountered in timestamp");
            }
            --min;
            --max;
        }
        while (max > 0) {
            c = this.read_char();
            if (!IonTokenConstsX.isDigit(c)) {
                return c;
            }
            --max;
        }
        return this.read_char();
    }

    protected IonType load_number(StringBuilder sb) throws IOException {
        int t;
        int len;
        boolean starts_with_zero;
        boolean has_sign = false;
        int c = this.read_char();
        boolean bl = has_sign = c == 45 || c == 43;
        if (has_sign) {
            sb.append((char)c);
            c = this.read_char();
        }
        if (!IonTokenConstsX.isDigit(c)) {
            this.bad_token(c);
        }
        boolean bl2 = starts_with_zero = c == 48;
        if (starts_with_zero) {
            int c2 = this.read_char();
            if (Radix.HEX.isPrefix(c2)) {
                sb.append((char)c);
                c = this.loadRadixValue(sb, has_sign, c2, Radix.HEX);
                return this.load_finish_number(sb, c, 3);
            }
            if (Radix.BINARY.isPrefix(c2)) {
                sb.append((char)c);
                c = this.loadRadixValue(sb, has_sign, c2, Radix.BINARY);
                return this.load_finish_number(sb, c, 26);
            }
            this.unread_char(c2);
        }
        if ((c = this.load_digits(sb, c)) == 45 || c == 84) {
            if (has_sign) {
                this.error("Numeric value followed by invalid character: " + sb + (char)c);
            }
            if ((len = sb.length()) != 4) {
                this.error("Numeric value followed by invalid character: " + sb + (char)c);
            }
            IonType tt = this.load_timestamp(sb, c);
            return tt;
        }
        if (starts_with_zero) {
            len = sb.length();
            if (has_sign) {
                --len;
            }
            if (len != 1) {
                this.error("Invalid leading zero in number: " + sb);
            }
        }
        if (c == 46) {
            sb.append((char)c);
            c = this.read_char();
            c = this.load_digits(sb, c);
            t = 4;
        } else {
            t = 2;
        }
        if (c == 101 || c == 69) {
            t = 5;
            sb.append((char)c);
            c = this.load_exponent(sb);
        } else if (c == 100 || c == 68) {
            t = 4;
            sb.append((char)c);
            c = this.load_exponent(sb);
        }
        return this.load_finish_number(sb, c, t);
    }

    private final IonType load_finish_number(CharSequence numericText, int c, int token) throws IOException {
        if (!this.is_value_terminating_character(c)) {
            this.error("Numeric value followed by invalid character: " + numericText + (char)c);
        }
        this.unread_char(c);
        IonType it = IonTokenConstsX.ion_type_of_scalar(token);
        return it;
    }

    private final int load_exponent(StringBuilder sb) throws IOException {
        int c = this.read_char();
        if (c == 45 || c == 43) {
            sb.append((char)c);
            c = this.read_char();
        }
        if ((c = this.load_digits(sb, c)) == 46) {
            sb.append((char)c);
            c = this.read_char();
            c = this.load_digits(sb, c);
        }
        return c;
    }

    private final int load_digits(StringBuilder sb, int c) throws IOException {
        if (!IonTokenConstsX.isDigit(c)) {
            return c;
        }
        sb.append((char)c);
        return this.readNumeric(sb, Radix.DECIMAL, NumericState.DIGIT);
    }

    private final void load_fixed_digits(StringBuilder sb, int len) throws IOException {
        int c;
        switch (len) {
            default: {
                while (len > 4) {
                    c = this.read_char();
                    if (!IonTokenConstsX.isDigit(c)) {
                        this.bad_token(c);
                    }
                    sb.append((char)c);
                    --len;
                }
            }
            case 4: {
                c = this.read_char();
                if (!IonTokenConstsX.isDigit(c)) {
                    this.bad_token(c);
                }
                sb.append((char)c);
            }
            case 3: {
                c = this.read_char();
                if (!IonTokenConstsX.isDigit(c)) {
                    this.bad_token(c);
                }
                sb.append((char)c);
            }
            case 2: {
                c = this.read_char();
                if (!IonTokenConstsX.isDigit(c)) {
                    this.bad_token(c);
                }
                sb.append((char)c);
            }
            case 1: 
        }
        c = this.read_char();
        if (!IonTokenConstsX.isDigit(c)) {
            this.bad_token(c);
        }
        sb.append((char)c);
    }

    private final IonType load_timestamp(StringBuilder sb, int c) throws IOException {
        assert (c == 45 || c == 84);
        sb.append((char)c);
        if (c == 84) {
            c = this.read_char();
            return this.load_finish_number(sb, c, 8);
        }
        this.load_fixed_digits(sb, 2);
        c = this.read_char();
        if (c == 84) {
            sb.append((char)c);
            c = this.read_char();
            return this.load_finish_number(sb, c, 8);
        }
        if (c != 45) {
            this.bad_token(c);
        }
        sb.append((char)c);
        this.load_fixed_digits(sb, 2);
        c = this.read_char();
        if (c != 84) {
            return this.load_finish_number(sb, c, 8);
        }
        sb.append((char)c);
        c = this.read_char();
        if (!IonTokenConstsX.isDigit(c)) {
            return this.load_finish_number(sb, c, 8);
        }
        sb.append((char)c);
        this.load_fixed_digits(sb, 1);
        c = this.read_char();
        if (c != 58) {
            this.bad_token(c);
        }
        sb.append((char)c);
        this.load_fixed_digits(sb, 2);
        c = this.read_char();
        if (c == 58) {
            sb.append((char)c);
            this.load_fixed_digits(sb, 2);
            c = this.read_char();
            if (c == 46) {
                sb.append((char)c);
                c = this.read_char();
                if (!IonTokenConstsX.isDigit(c)) {
                    this.expected_but_found("at least one digit after timestamp's decimal point", c);
                }
                c = this.load_digits(sb, c);
            }
        }
        if (c == 122 || c == 90) {
            sb.append((char)c);
            c = this.read_char();
        } else if (c == 43 || c == 45) {
            sb.append((char)c);
            this.load_fixed_digits(sb, 2);
            c = this.read_char();
            if (c != 58) {
                this.bad_token(c);
            }
            sb.append((char)c);
            this.load_fixed_digits(sb, 2);
            c = this.read_char();
        } else {
            this.bad_token(c);
        }
        return this.load_finish_number(sb, c, 8);
    }

    private final int loadRadixValue(StringBuilder sb, boolean has_sign, int c2, Radix radix) throws IOException {
        radix.assertPrefix(c2);
        sb.append((char)c2);
        return this.readNumeric(sb, radix);
    }

    private final int skip_over_symbol_identifier(UnifiedSavePointManagerX.SavePoint sp) throws IOException {
        int c = this.read_char();
        while (IonTokenConstsX.isValidSymbolCharacter(c)) {
            c = this.read_char();
        }
        if (sp != null) {
            sp.markEnd(0);
        }
        return c;
    }

    protected void load_symbol_identifier(StringBuilder sb) throws IOException {
        int c = this.read_char();
        while (IonTokenConstsX.isValidSymbolCharacter(c)) {
            sb.append((char)c);
            c = this.read_char();
        }
        this.unread_char(c);
    }

    private int skip_over_symbol_operator(UnifiedSavePointManagerX.SavePoint sp) throws IOException {
        int c = this.read_char();
        if (this.peek_inf_helper(c)) {
            c = this.read_char();
        } else {
            assert (IonTokenConstsX.isValidExtendedSymbolCharacter(c));
            while (IonTokenConstsX.isValidExtendedSymbolCharacter(c)) {
                c = this.read_char();
            }
        }
        if (sp != null) {
            sp.markEnd(0);
        }
        return c;
    }

    protected void load_symbol_operator(StringBuilder sb) throws IOException {
        int c = this.read_char();
        if ((c == 43 || c == 45) && this.peek_inf_helper(c)) {
            sb.append((char)c);
            sb.append("inf");
        } else {
            assert (IonTokenConstsX.isValidExtendedSymbolCharacter(c));
            while (IonTokenConstsX.isValidExtendedSymbolCharacter(c)) {
                sb.append((char)c);
                c = this.read_char();
            }
            this.unread_char(c);
        }
    }

    private final int skip_single_quoted_string(UnifiedSavePointManagerX.SavePoint sp) throws IOException {
        while (true) {
            int c = this.read_string_char(ProhibitedCharacters.NONE);
            switch (c) {
                case -1: {
                    this.unexpected_eof();
                }
                case 39: {
                    if (sp != null) {
                        sp.markEnd(-1);
                    }
                    return this.read_char();
                }
                case 92: {
                    c = this.read_char();
                }
            }
        }
    }

    protected int load_single_quoted_string(StringBuilder sb, boolean is_clob) throws IOException {
        boolean expectLowSurrogate = false;
        block6: while (true) {
            int c = this.read_string_char(ProhibitedCharacters.NONE);
            switch (c) {
                case -9: 
                case -8: 
                case -7: {
                    continue block6;
                }
                case -1: 
                case 39: {
                    if (!is_clob) {
                        this.check_for_low_surrogate(c, expectLowSurrogate);
                    }
                    return c;
                }
                case -6: 
                case -5: 
                case -4: {
                    this.bad_token(c);
                }
                case 92: {
                    c = this.read_char();
                    c = this.read_escaped_char_content_helper(c, is_clob);
                    break;
                }
                default: {
                    if (is_clob || IonTokenConstsX.is7bitValue(c)) break;
                    c = this.read_large_char_sequence(c);
                }
            }
            if (!is_clob) {
                expectLowSurrogate = this.check_for_low_surrogate(c, expectLowSurrogate);
                if (IonUTF8.needsSurrogateEncoding(c)) {
                    sb.append(IonUTF8.highSurrogate(c));
                    c = IonUTF8.lowSurrogate(c);
                } else {
                    expectLowSurrogate = IonUTF8.isHighSurrogate(c);
                }
            } else if (IonTokenConstsX.is8bitValue(c)) {
                this.bad_token(c);
            }
            sb.append((char)c);
        }
    }

    private void skip_double_quoted_string(UnifiedSavePointManagerX.SavePoint sp) throws IOException {
        this.skip_double_quoted_string_helper();
        if (sp != null) {
            sp.markEnd(-1);
        }
    }

    private final void skip_double_quoted_string_helper() throws IOException {
        while (true) {
            int c = this.read_string_char(ProhibitedCharacters.NONE);
            switch (c) {
                case -1: {
                    this.unexpected_eof();
                }
                case -6: 
                case -5: 
                case -4: {
                    this.bad_token(c);
                }
                case 34: {
                    return;
                }
                case 92: {
                    c = this.read_char();
                }
            }
        }
    }

    protected int load_double_quoted_string(StringBuilder sb, boolean is_clob) throws IOException {
        boolean expectLowSurrogate = false;
        block6: while (true) {
            int c = this.read_string_char(ProhibitedCharacters.SHORT_CHAR);
            switch (c) {
                case -9: 
                case -8: 
                case -7: {
                    continue block6;
                }
                case -1: 
                case 34: {
                    if (!is_clob) {
                        this.check_for_low_surrogate(c, expectLowSurrogate);
                    }
                    return c;
                }
                case -6: 
                case -5: 
                case -4: {
                    this.bad_token(c);
                }
                case 92: {
                    c = this.read_char_escaped(c, is_clob);
                    break;
                }
                default: {
                    if (is_clob || IonTokenConstsX.is7bitValue(c)) break;
                    c = this.read_large_char_sequence(c);
                }
            }
            if (!is_clob) {
                expectLowSurrogate = this.check_for_low_surrogate(c, expectLowSurrogate);
                if (IonUTF8.needsSurrogateEncoding(c)) {
                    sb.append(IonUTF8.highSurrogate(c));
                    c = IonUTF8.lowSurrogate(c);
                } else {
                    expectLowSurrogate = IonUTF8.isHighSurrogate(c);
                }
            }
            sb.append((char)c);
        }
    }

    private boolean check_for_low_surrogate(int c, boolean expectLowSurrogate) throws IonException {
        if (IonUTF8.isLowSurrogate(c)) {
            if (expectLowSurrogate) {
                return false;
            }
            this.error("unexpected low surrogate " + IonTextUtils.printCodePointAsString(c));
        } else if (expectLowSurrogate) {
            this.expected_but_found("a low surrogate", c);
        }
        return false;
    }

    protected int read_double_quoted_char(boolean is_clob) throws IOException {
        int c = this.read_char();
        if (is_clob && c > 127) {
            throw new IonReaderTextTokenException("non ASCII character in clob: " + c);
        }
        switch (c) {
            case 34: {
                this.unread_char(c);
                c = -2;
                break;
            }
            case -1: {
                break;
            }
            case 92: {
                c = this.read_char_escaped(c, is_clob);
                break;
            }
            default: {
                if (is_clob || IonTokenConstsX.is7bitValue(c)) break;
                c = this.read_large_char_sequence(c);
            }
        }
        return c;
    }

    private void skip_triple_quoted_string(UnifiedSavePointManagerX.SavePoint sp) throws IOException {
        this.skip_triple_quoted_string(sp, CommentStrategy.IGNORE);
    }

    private void skip_triple_quoted_clob_string(UnifiedSavePointManagerX.SavePoint sp) throws IOException {
        this.skip_triple_quoted_string(sp, CommentStrategy.ERROR);
    }

    private void skip_triple_quoted_string(UnifiedSavePointManagerX.SavePoint sp, CommentStrategy commentStrategy) throws IOException {
        block5: while (true) {
            int c = this.read_char();
            switch (c) {
                case -1: {
                    this.unexpected_eof();
                }
                case 39: {
                    c = this.read_char();
                    if (c != 39) break;
                    c = this.read_char();
                    if (sp != null) {
                        sp.markEnd(-3);
                    }
                    if (c != 39 || (c = this.skip_over_whitespace(commentStrategy)) == 39 && this.is_2_single_quotes_helper()) continue block5;
                    this.unread_char(c);
                    return;
                }
                case 92: {
                    c = this.read_char();
                }
            }
        }
    }

    protected int load_triple_quoted_string(StringBuilder sb, boolean is_clob) throws IOException {
        boolean expectLowSurrogate = false;
        block8: while (true) {
            int c = this.read_triple_quoted_char(is_clob);
            switch (c) {
                case -2: 
                case -1: {
                    if (!is_clob) {
                        this.check_for_low_surrogate(c, expectLowSurrogate);
                    }
                    return c;
                }
                case -4: {
                    c = 10;
                    break;
                }
                case -5: {
                    c = 10;
                    break;
                }
                case -6: {
                    c = 10;
                    break;
                }
                case -9: 
                case -8: 
                case -7: {
                    continue block8;
                }
                case -3: {
                    if (is_clob) continue block8;
                    expectLowSurrogate = this.check_for_low_surrogate(c, expectLowSurrogate);
                    continue block8;
                }
            }
            if (!is_clob) {
                expectLowSurrogate = this.check_for_low_surrogate(c, expectLowSurrogate);
                if (IonUTF8.needsSurrogateEncoding(c)) {
                    sb.append(IonUTF8.highSurrogate(c));
                    c = IonUTF8.lowSurrogate(c);
                } else {
                    expectLowSurrogate = IonUTF8.isHighSurrogate(c);
                }
            }
            sb.append((char)c);
        }
    }

    protected int read_triple_quoted_char(boolean is_clob) throws IOException {
        int c = this.read_string_char(ProhibitedCharacters.LONG_CHAR);
        if (is_clob && c > 127) {
            throw new IonReaderTextTokenException("non ASCII character in clob: " + c);
        }
        switch (c) {
            case 39: {
                if (!this.is_2_single_quotes_helper()) break;
                c = this.skip_over_whitespace();
                if (c == 39 && this.is_2_single_quotes_helper()) {
                    return -3;
                }
                this.unread_char(c);
                c = -2;
                break;
            }
            case 92: {
                c = this.read_char_escaped(c, is_clob);
                break;
            }
            case -9: 
            case -8: 
            case -7: 
            case -6: 
            case -5: 
            case -4: {
                break;
            }
            case -1: {
                break;
            }
            default: {
                if (is_clob || IonTokenConstsX.is7bitValue(c)) break;
                c = this.read_large_char_sequence(c);
            }
        }
        return c;
    }

    protected void skip_over_lob(int lobToken, UnifiedSavePointManagerX.SavePoint sp) throws IOException {
        switch (lobToken) {
            case 12: {
                this.skip_double_quoted_string(sp);
                this.skip_clob_close_punctuation();
                break;
            }
            case 13: {
                this.skip_triple_quoted_clob_string(sp);
                this.skip_clob_close_punctuation();
                break;
            }
            case 24: {
                this.skip_over_blob(sp);
                break;
            }
            default: {
                this.error("unexpected token " + IonTokenConstsX.getTokenName(lobToken) + " encountered for lob content");
            }
        }
    }

    protected void load_clob(int lobToken, StringBuilder sb) throws IOException {
        switch (lobToken) {
            case 12: {
                this.load_double_quoted_string(sb, true);
                break;
            }
            case 13: {
                this.load_triple_quoted_string(sb, true);
                break;
            }
            case 24: {
                this.load_blob(sb);
                break;
            }
            default: {
                this.error("unexpected token " + IonTokenConstsX.getTokenName(lobToken) + " encountered for lob content");
            }
        }
    }

    private final int read_char_escaped(int c, boolean is_clob) throws IOException {
        block4: while (true) {
            switch (c) {
                case -9: 
                case -8: 
                case -7: {
                    c = this.read_string_char(ProhibitedCharacters.NONE);
                    continue block4;
                }
                case 92: {
                    c = this.read_char();
                    if (c < 0) {
                        this.unexpected_eof();
                    }
                    if ((c = this.read_escaped_char_content_helper(c, is_clob)) == -7 || c == -8 || c == -9) {
                        c = this.read_string_char(ProhibitedCharacters.NONE);
                        continue block4;
                    }
                    if (c != -11) break block4;
                    this.bad_escape_sequence();
                    break block4;
                }
                default: {
                    if (is_clob || IonTokenConstsX.is7bitValue(c)) break block4;
                    c = this.read_large_char_sequence(c);
                    break block4;
                }
            }
            break;
        }
        if (c == -1) {
            return c;
        }
        if (is_clob && !IonTokenConstsX.is8bitValue(c)) {
            this.error("invalid character [" + IonTextUtils.printCodePointAsString(c) + "] in CLOB");
        }
        return c;
    }

    private final int read_large_char_sequence(int c) throws IOException {
        if (this._stream._is_byte_data) {
            return this.read_ut8_sequence(c);
        }
        if (_Private_IonConstants.isHighSurrogate(c)) {
            int c2 = this.read_char();
            if (_Private_IonConstants.isLowSurrogate(c2)) {
                c = _Private_IonConstants.makeUnicodeScalar(c, c2);
            } else {
                this.unread_char(c2);
            }
        }
        return c;
    }

    private final int read_ut8_sequence(int c) throws IOException {
        assert (!IonTokenConstsX.is7bitValue(c));
        int len = IonUTF8.getUTF8LengthFromFirstByte(c);
        switch (len) {
            case 1: {
                break;
            }
            case 2: {
                int b2 = this.read_char();
                c = IonUTF8.twoByteScalar(c, b2);
                break;
            }
            case 3: {
                int b2 = this.read_char();
                int b3 = this.read_char();
                c = IonUTF8.threeByteScalar(c, b2, b3);
                break;
            }
            case 4: {
                int b2 = this.read_char();
                int b3 = this.read_char();
                int b4 = this.read_char();
                c = IonUTF8.fourByteScalar(c, b2, b3, b4);
                break;
            }
            default: {
                this.error("invalid UTF8 starting byte");
            }
        }
        return c;
    }

    private void skip_over_blob(UnifiedSavePointManagerX.SavePoint sp) throws IOException {
        int c = this.skip_over_blob_whitespace();
        while (c != -1 && c != 125) {
            c = this.skip_over_blob_whitespace();
        }
        if (sp != null) {
            int offset = c == 125 ? -1 : 0;
            sp.markEnd(offset);
        }
        if (c != 125) {
            this.unexpected_eof();
        }
        if ((c = this.read_char()) < 0) {
            this.unexpected_eof();
        }
        if (c != 125) {
            String message = "improperly closed BLOB, " + IonTextUtils.printCodePointAsString(c) + " encountered when '}' was expected";
            this.error(message);
        }
        if (sp != null) {
            sp.markEnd();
        }
    }

    protected void load_blob(StringBuilder sb) throws IOException {
        int c;
        while ((c = this.read_base64_byte()) != -1) {
            sb.append(c);
        }
        if (this._stream.isEOF()) {
            this.unexpected_eof();
        }
        if ((c = this.read_char()) < 0) {
            this.unexpected_eof();
        }
        if (c != 125) {
            String message = "improperly closed BLOB, " + IonTextUtils.printCodePointAsString(c) + " encountered when '}' was expected";
            this.error(message);
        }
    }

    private final int read_escaped_char_content_helper(int c1, boolean is_clob) throws IOException {
        if (c1 < 0) {
            switch (c1) {
                case -4: {
                    return -7;
                }
                case -5: {
                    return -8;
                }
                case -6: {
                    return -9;
                }
            }
            this.bad_escape_sequence(c1);
        }
        if (!IonTokenConstsX.isValidEscapeStart(c1)) {
            this.bad_escape_sequence(c1);
        }
        int c2 = IonTokenConstsX.escapeReplacementCharacter(c1);
        switch (c2) {
            case -11: {
                assert (false) : "invalid escape start characters (line " + (char)c1 + " should have been removed by isValid";
                break;
            }
            case -14: {
                if (is_clob) {
                    this.bad_escape_sequence(c2);
                }
                c2 = this.read_hex_escape_sequence_value(4);
                break;
            }
            case -15: {
                if (is_clob) {
                    this.bad_escape_sequence(c2);
                }
                c2 = this.read_hex_escape_sequence_value(8);
                break;
            }
            case -16: {
                c2 = this.read_hex_escape_sequence_value(2);
            }
        }
        return c2;
    }

    private final int read_hex_escape_sequence_value(int len) throws IOException {
        int hexchar = 0;
        while (len > 0) {
            int d;
            --len;
            int c = this.read_char();
            if (c < 0) {
                this.unexpected_eof();
            }
            if ((d = IonTokenConstsX.hexDigitValue(c)) < 0) {
                return -1;
            }
            hexchar = (hexchar << 4) + d;
        }
        if (len > 0) {
            String message = "invalid hex digit [" + IonTextUtils.printCodePointAsString(hexchar) + "] in escape sequence";
            this.error(message);
        }
        return hexchar;
    }

    public final int read_base64_byte() throws IOException {
        int b;
        if (this._base64_prefetch_count < 1) {
            b = this.read_base64_byte_helper();
        } else {
            b = this._base64_prefetch_stack & 0xFF;
            this._base64_prefetch_stack >>= 8;
            --this._base64_prefetch_count;
        }
        return b;
    }

    private final int read_base64_byte_helper() throws IOException {
        int c = this.skip_over_blob_whitespace();
        if (c == -1 || c == 125) {
            return -1;
        }
        int c1 = this.read_base64_getchar_helper(c);
        int c2 = this.read_base64_getchar_helper();
        int c3 = this.read_base64_getchar_helper();
        int c4 = this.read_base64_getchar_helper();
        int len = IonReaderTextRawTokensX.decode_base64_length(c1, c2, c3, c4);
        this._base64_prefetch_stack = 0;
        this._base64_prefetch_count = len - 1;
        switch (len) {
            default: {
                String message = "invalid binhex sequence encountered at offset" + this.input_position();
                throw new IonReaderTextTokenException(message);
            }
            case 3: {
                int b3 = IonReaderTextRawTokensX.decode_base64_byte3(c1, c2, c3, c4);
                this._base64_prefetch_stack = b3 << 8 & 0xFF00;
            }
            case 2: {
                int b2 = IonReaderTextRawTokensX.decode_base64_byte2(c1, c2, c3, c4);
                this._base64_prefetch_stack |= b2 & 0xFF;
            }
            case 1: 
        }
        int b1 = IonReaderTextRawTokensX.decode_base64_byte1(c1, c2, c3, c4);
        return b1;
    }

    private final int read_base64_getchar_helper(int c) throws IOException {
        assert (c != -1 && c != 125);
        if (c == -1 || c == 125) {
            return -1;
        }
        if (c == BASE64_TERMINATOR_CHAR) {
            this.error("invalid base64 image - excess terminator characters ['=']");
        }
        return this.read_base64_getchar_helper2(c);
    }

    private final int read_base64_getchar_helper() throws IOException {
        int c = this.skip_over_blob_whitespace();
        if (c == -1 || c == 125) {
            this.error("invalid base64 image - too short");
        }
        return this.read_base64_getchar_helper2(c);
    }

    private final int read_base64_getchar_helper2(int c) throws IOException {
        assert (c != -1 && c != 125);
        if (c == BASE64_TERMINATOR_CHAR) {
            return 128;
        }
        int b = BASE64_CHAR_TO_BIN[c & 0xFF];
        if (b == -1 || !IonTokenConstsX.is8bitValue(c)) {
            String message = "invalid character " + Character.toString((char)c) + " encountered in base64 value at " + this.input_position();
            throw new IonReaderTextTokenException(message);
        }
        return b;
    }

    private static final int decode_base64_length(int c1, int c2, int c3, int c4) {
        int len = 3;
        len = c4 != 128 ? 3 : (c3 != 128 ? 2 : 1);
        return len;
    }

    private static final int decode_base64_byte1(int c1, int c2, int c3, int c4) {
        int b1 = c1 << 2 & 0xFC | c2 >> 4 & 3;
        return b1;
    }

    private static final int decode_base64_byte2(int c1, int c2, int c3, int c4) {
        int b2 = (c2 << 4 & 0xF0 | c3 >> 2 & 0xF) & 0xFF;
        return b2;
    }

    private static final int decode_base64_byte3(int c1, int c2, int c3, int c4) {
        int b3 = ((c3 & 3) << 6 | c4 & 0x3F) & 0xFF;
        return b3;
    }

    protected void save_point_start(UnifiedSavePointManagerX.SavePoint sp) throws IOException {
        assert (sp != null && sp.isClear());
        long line_number = this._line_count;
        long line_start = this._line_starting_position;
        sp.start(line_number, line_start);
    }

    protected void save_point_activate(UnifiedSavePointManagerX.SavePoint sp) throws IOException {
        assert (sp != null && sp.isDefined());
        long line_number = this._line_count;
        long line_start = this._line_starting_position;
        this._stream._save_points.savePointPushActive(sp, line_number, line_start);
        this._line_count = sp.getStartLineNumber();
        this._line_starting_position = sp.getStartLineStart();
    }

    protected void save_point_deactivate(UnifiedSavePointManagerX.SavePoint sp) throws IOException {
        assert (sp != null && sp.isActive());
        this._stream._save_points.savePointPopActive(sp);
        this._line_count = sp.getPrevLineNumber();
        this._line_starting_position = sp.getPrevLineStart();
    }

    protected final void error(String message) {
        String message2 = message + this.input_position();
        throw new IonReaderTextTokenException(message2);
    }

    protected final void unexpected_eof() {
        String message = "unexpected EOF encountered " + this.input_position();
        throw new UnexpectedEofException(message);
    }

    protected final void bad_escape_sequence() {
        String message = "bad escape character encountered " + this.input_position();
        throw new IonReaderTextTokenException(message);
    }

    protected final void bad_escape_sequence(int c) {
        String message = "bad escape character '" + IonTextUtils.printCodePointAsString(c) + "' encountered " + this.input_position();
        throw new IonReaderTextTokenException(message);
    }

    protected final void bad_token_start(int c) {
        String message = "bad character [" + c + ", " + IonTextUtils.printCodePointAsString(c) + "] encountered where a token was supposed to start " + this.input_position();
        throw new IonReaderTextTokenException(message);
    }

    protected final void bad_token(int c) {
        String charStr = IonTextUtils.printCodePointAsString(c);
        String message = "a bad character " + charStr + " was encountered " + this.input_position();
        throw new IonReaderTextTokenException(message);
    }

    protected final void expected_but_found(String expected, int c) {
        String charStr = IonTextUtils.printCodePointAsString(c);
        String message = "Expected " + expected + " but found " + charStr + this.input_position();
        throw new IonReaderTextTokenException(message);
    }

    private int readNumeric(Appendable buffer, Radix radix) throws IOException {
        return this.readNumeric(buffer, radix, NumericState.START);
    }

    private int readNumeric(Appendable buffer, Radix radix, NumericState startingState) throws IOException {
        NumericState state = startingState;
        while (true) {
            int c = this.read_char();
            switch (state) {
                case START: {
                    if (radix.isValidDigit(c)) {
                        buffer.append(radix.normalizeDigit((char)c));
                        state = NumericState.DIGIT;
                        break;
                    }
                    return c;
                }
                case DIGIT: {
                    if (radix.isValidDigit(c)) {
                        buffer.append(radix.normalizeDigit((char)c));
                        state = NumericState.DIGIT;
                        break;
                    }
                    if (c == 95) {
                        state = NumericState.UNDERSCORE;
                        break;
                    }
                    return c;
                }
                case UNDERSCORE: {
                    if (radix.isValidDigit(c)) {
                        buffer.append(radix.normalizeDigit((char)c));
                        state = NumericState.DIGIT;
                        break;
                    }
                    this.unread_char(c);
                    return 95;
                }
            }
        }
    }

    private static enum NumericState {
        START,
        UNDERSCORE,
        DIGIT;

    }

    private static abstract class Radix
    extends Enum<Radix> {
        public static final /* enum */ Radix BINARY = new Radix(){

            @Override
            boolean isPrefix(int c) {
                return c == 98 || c == 66;
            }

            @Override
            boolean isValidDigit(int c) {
                return IonTokenConstsX.isBinaryDigit(c);
            }

            @Override
            char normalizeDigit(char c) {
                return c;
            }
        };
        public static final /* enum */ Radix DECIMAL = new Radix(){

            @Override
            boolean isPrefix(int c) {
                return false;
            }

            @Override
            boolean isValidDigit(int c) {
                return IonTokenConstsX.isDigit(c);
            }

            @Override
            char normalizeDigit(char c) {
                return c;
            }
        };
        public static final /* enum */ Radix HEX = new Radix(){

            @Override
            boolean isPrefix(int c) {
                return c == 120 || c == 88;
            }

            @Override
            boolean isValidDigit(int c) {
                return IonTokenConstsX.isHexDigit(c);
            }

            @Override
            char normalizeDigit(char c) {
                return Character.toLowerCase(c);
            }
        };
        private static final /* synthetic */ Radix[] $VALUES;

        public static Radix[] values() {
            return (Radix[])$VALUES.clone();
        }

        public static Radix valueOf(String name) {
            return Enum.valueOf(Radix.class, name);
        }

        abstract boolean isPrefix(int var1);

        abstract boolean isValidDigit(int var1);

        abstract char normalizeDigit(char var1);

        void assertPrefix(int c) {
            assert (this.isPrefix(c));
        }

        static {
            $VALUES = new Radix[]{BINARY, DECIMAL, HEX};
        }
    }

    private static enum ProhibitedCharacters {
        SHORT_CHAR{

            @Override
            boolean includes(int c) {
                return ProhibitedCharacters.isControlCharacter(c) && !ProhibitedCharacters.isWhitespace(c);
            }
        }
        ,
        LONG_CHAR{

            @Override
            boolean includes(int c) {
                return ProhibitedCharacters.isControlCharacter(c) && !ProhibitedCharacters.isWhitespace(c) && !ProhibitedCharacters.isNewline(c);
            }
        }
        ,
        NONE{

            @Override
            boolean includes(int c) {
                return false;
            }
        };


        abstract boolean includes(int var1);

        private static boolean isControlCharacter(int c) {
            return c <= 31 && 0 <= c;
        }

        private static boolean isNewline(int c) {
            return c == 10 || c == 13;
        }

        private static boolean isWhitespace(int c) {
            return c == 9 || c == 11 || c == 12 || c == 32;
        }
    }

    public static class IonReaderTextTokenException
    extends IonException {
        private static final long serialVersionUID = 1L;

        IonReaderTextTokenException(String msg) {
            super(msg);
        }
    }

    private static enum CommentStrategy {
        IGNORE{

            @Override
            boolean onComment(IonReaderTextRawTokensX tokenizer) throws IOException {
                int next = tokenizer.read_char();
                switch (next) {
                    case 47: {
                        tokenizer.skip_single_line_comment();
                        return true;
                    }
                    case 42: {
                        tokenizer.skip_block_comment();
                        return true;
                    }
                }
                tokenizer.unread_char(next);
                return false;
            }
        }
        ,
        ERROR{

            @Override
            boolean onComment(IonReaderTextRawTokensX tokenizer) throws IOException {
                int next = tokenizer.read_char();
                if (next == 47 || next == 42) {
                    tokenizer.error("Illegal comment");
                } else {
                    tokenizer.unread_char(next);
                }
                return false;
            }
        }
        ,
        BREAK{

            @Override
            boolean onComment(IonReaderTextRawTokensX tokenizer) throws IOException {
                return false;
            }
        };


        abstract boolean onComment(IonReaderTextRawTokensX var1) throws IOException;
    }
}

