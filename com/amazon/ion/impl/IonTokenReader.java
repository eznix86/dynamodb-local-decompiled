/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazon.ion.impl;

import com.amazon.ion.Decimal;
import com.amazon.ion.IonException;
import com.amazon.ion.Timestamp;
import com.amazon.ion.UnexpectedEofException;
import com.amazon.ion.impl.IonCharacterReader;
import com.amazon.ion.impl.IonTokenConstsX;
import com.amazon.ion.impl._Private_IonConstants;
import com.amazon.ion.util.IonTextUtils;
import java.io.IOException;
import java.io.PushbackReader;
import java.io.Reader;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Stack;

final class IonTokenReader {
    public static int isPunctuation = 1;
    public static int isKeyword = 2;
    public static int isTypeName = 4;
    public static int isConstant = 8;
    private static int isPosInt = 16;
    private static int isNegInt = 32;
    public static int isFloat = 64;
    public static int isDecimal = 128;
    public static int isTag = 256;
    static final int EMPTY_ESCAPE_SEQUENCE = -2;
    public Stack<Context> contextStack = new Stack();
    public Context context = Context.NONE;
    private IonCharacterReader in;
    private LocalReader localReader;
    private PushbackReader pushbackReader;
    public boolean inQuotedContent;
    public boolean isIncomplete;
    public boolean isLongString;
    public boolean quotedIdentifier;
    public int embeddedSlash;
    public int endquote;
    public Type t = Type.none;
    public Type keyword = Type.none;
    public StringBuilder value = new StringBuilder();
    public String stringValue;
    public Double doubleValue;
    public BigInteger intValue;
    public Timestamp dateValue;
    public BigDecimal decimalValue;
    public boolean isNegative;
    public NumberType numberType;

    public void pushContext(Context newcontext) {
        this.contextStack.push(newcontext);
        this.context = newcontext;
    }

    public Context popContext() {
        this.context = this.contextStack.pop();
        return this.context;
    }

    public IonTokenReader(Reader r) {
        this.in = new IonCharacterReader(r);
    }

    public long getConsumedAmount() {
        return this.in.getConsumedAmount();
    }

    public int getLineNumber() {
        return this.in.getLineNumber();
    }

    public int getColumn() {
        return this.in.getColumn();
    }

    public String position() {
        return "line " + this.getLineNumber() + " column " + this.getColumn();
    }

    public String getValueString(boolean is_in_expression) throws IOException {
        if (this.isIncomplete) {
            this.finishScanString(is_in_expression);
            this.stringValue = this.value.toString();
            this.inQuotedContent = false;
        } else if (this.stringValue == null) {
            this.stringValue = this.value.toString();
        }
        return this.stringValue;
    }

    void resetValue() {
        this.isIncomplete = false;
        this.stringValue = null;
        this.doubleValue = null;
        this.intValue = null;
        this.dateValue = null;
        this.decimalValue = null;
        this.isNegative = false;
        this.numberType = null;
        this.t = null;
        this.value.setLength(0);
    }

    public PushbackReader getPushbackReader() {
        if (this.localReader == null) {
            this.localReader = new LocalReader(this);
            this.pushbackReader = new PushbackReader(this.localReader, 11);
        }
        this.localReader.reset();
        return this.pushbackReader;
    }

    final int read() throws IOException {
        int ch = this.in.read();
        assert (ch != 13);
        return ch;
    }

    int readIgnoreWhitespace() throws IOException {
        int c;
        assert (!this.inQuotedContent);
        do {
            if ((c = this.read()) != 47) continue;
            int c2 = this.read();
            if (c2 == 47) {
                while (c2 != 10 && c2 != -1) {
                    c2 = this.read();
                }
                c = c2;
                continue;
            }
            if (c2 == 42) {
                while (c2 != -1) {
                    c2 = this.read();
                    if (c2 != 42) continue;
                    c2 = this.read();
                    if (c2 == 47) break;
                    this.unread(c2);
                }
                c = this.read();
                continue;
            }
            this.unread(c2);
        } while (IonTextUtils.isWhitespace(c));
        return c;
    }

    void unread(int c) throws IOException {
        this.in.unread(c);
    }

    public Type next(boolean is_in_expression) throws IOException {
        this.inQuotedContent = false;
        int c = this.readIgnoreWhitespace();
        return this.next(c, is_in_expression);
    }

    private Type next(int c, boolean is_in_expression) throws IOException {
        this.t = Type.none;
        this.isIncomplete = false;
        switch (c) {
            case -1: {
                this.t = Type.eof;
                return this.t;
            }
            case 123: {
                int c2 = this.read();
                if (c2 == 123) {
                    this.t = Type.tOpenDoubleCurly;
                    return this.t;
                }
                this.unread(c2);
                this.t = Type.tOpenCurly;
                return this.t;
            }
            case 125: {
                this.t = Type.tCloseCurly;
                return this.t;
            }
            case 91: {
                this.t = Type.tOpenSquare;
                return this.t;
            }
            case 93: {
                this.t = Type.tCloseSquare;
                return this.t;
            }
            case 40: {
                this.t = Type.tOpenParen;
                return this.t;
            }
            case 41: {
                this.t = Type.tCloseParen;
                return this.t;
            }
            case 44: {
                this.t = Type.tComma;
                return this.t;
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
                return this.readNumber(c);
            }
            case 34: {
                this.inQuotedContent = true;
                this.keyword = Type.none;
                return this.scanString(c, 13);
            }
            case 39: {
                int c2 = this.read();
                if (c2 == 39) {
                    c2 = this.read();
                    if (c2 == 39) {
                        return this.scanLongString();
                    }
                    this.unread(c2);
                    c2 = 39;
                }
                this.unread(c2);
                this.inQuotedContent = true;
                return this.scanIdentifier(c);
            }
            case 43: {
                int c2 = this.read();
                if (c2 == 105) {
                    c2 = this.read();
                    if (c2 == 110) {
                        c2 = this.read();
                        if (c2 == 102) {
                            this.t = Type.kwPosInf;
                            return this.t;
                        }
                        this.unread(c2);
                        c2 = 110;
                    }
                    this.unread(c2);
                    c2 = 105;
                }
                this.unread(c2);
                if (!is_in_expression) break;
                return this.scanOperator(c);
            }
            case 45: {
                int c2 = this.read();
                if (c2 >= 48 && c2 <= 57) {
                    this.unread(c2);
                    return this.readNumber(c);
                }
                if (c2 == 105) {
                    c2 = this.read();
                    if (c2 == 110) {
                        c2 = this.read();
                        if (c2 == 102) {
                            this.t = Type.kwNegInf;
                            return this.t;
                        }
                        this.unread(c2);
                        c2 = 110;
                    }
                    this.unread(c2);
                    c2 = 105;
                }
                this.unread(c2);
                if (!is_in_expression) break;
                return this.scanOperator(c);
            }
            default: {
                if (IonTextUtils.isIdentifierStart(c)) {
                    return this.scanIdentifier(c);
                }
                if (!is_in_expression || !IonTextUtils.isOperatorPart(c)) break;
                return this.scanOperator(c);
            }
        }
        String message = "Unexpected character " + IonTextUtils.printCodePointAsString(c) + " encountered at line " + this.getLineNumber() + " column " + this.getColumn();
        throw new IonException(message);
    }

    public Type scanIdentifier(int c) throws IOException {
        this.resetValue();
        this.t = Type.constSymbol;
        this.keyword = null;
        if (!this.readIdentifierContents(c)) {
            this.keyword = IonTokenReader.matchKeyword(this.value, 0, this.value.length());
            if (this.keyword != null) {
                if (this.keyword == Type.kwNull) {
                    c = this.read();
                    if (c == 46) {
                        int dot = this.value.length();
                        this.value.append((char)c);
                        c = this.read();
                        int added = this.readIdentifierContents(c, IonTokenConstsX.TN_MAX_NAME_LENGTH + 1);
                        int kw = IonTokenConstsX.keyword(this.value, dot + 1, dot + added + 1);
                        switch (kw) {
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
                            case 13: 
                            case 14: 
                            case 15: {
                                this.keyword = this.setNullType(this.value, dot + 1, this.value.length() - dot - 1);
                                break;
                            }
                            default: {
                                int ii = this.value.length();
                                while (ii > dot) {
                                    char uc = this.value.charAt(--ii);
                                    this.unread(uc);
                                }
                                String message = this.position() + ": Expected Ion type after 'null.' but found: " + this.value;
                                throw new IonException(message);
                            }
                        }
                    } else {
                        this.unread(c);
                    }
                }
                this.t = this.keyword;
                return this.t;
            }
        }
        if ((c = this.readIgnoreWhitespace()) != 58) {
            this.unread(c);
        } else {
            c = this.read();
            if (c != 58) {
                this.unread(c);
                this.t = Type.constMemberName;
            } else {
                this.t = Type.constUserTypeDecl;
            }
        }
        return this.t;
    }

    boolean readIdentifierContents(int c) throws IOException {
        int quote = c;
        this.inQuotedContent = quote == 39 || quote == 34;
        this.quotedIdentifier = this.inQuotedContent;
        if (this.quotedIdentifier) {
            while ((c = this.read()) >= 0 && c != quote) {
                if (c == 92) {
                    c = IonTokenReader.readEscapedCharacter(this.in, false);
                }
                if (c == -2) continue;
                this.value.appendCodePoint(c);
            }
            if (c == -1) {
                throw new IonException("end encountered before closing quote '\\" + (char)this.endquote + "'");
            }
            this.inQuotedContent = false;
        } else {
            this.value.append((char)c);
            while (IonTextUtils.isIdentifierPart(c = this.read())) {
                this.value.append((char)c);
            }
            this.unread(c);
        }
        return this.quotedIdentifier;
    }

    int readIdentifierContents(int c, int max_length) throws IOException {
        int count;
        assert (c != 39 && c != 34);
        this.value.append((char)c);
        for (count = 1; count < max_length; ++count) {
            c = this.read();
            if (!IonTextUtils.isIdentifierPart(c)) {
                this.unread(c);
                break;
            }
            this.value.append((char)c);
        }
        return count;
    }

    static Type matchKeyword(StringBuilder sb, int pos, int valuelen) throws IOException {
        Type keyword = null;
        switch (sb.charAt(pos++)) {
            case 'f': {
                if (valuelen != 5 || sb.charAt(pos++) != 'a' || sb.charAt(pos++) != 'l' || sb.charAt(pos++) != 's' || sb.charAt(pos++) != 'e') break;
                keyword = Type.kwFalse;
                break;
            }
            case 'n': {
                if (valuelen == 4 && sb.charAt(pos++) == 'u' && sb.charAt(pos++) == 'l' && sb.charAt(pos++) == 'l') {
                    keyword = Type.kwNull;
                    break;
                }
                if (valuelen != 3 || sb.charAt(pos++) != 'a' || sb.charAt(pos++) != 'n') break;
                keyword = Type.kwNan;
                break;
            }
            case 't': {
                if (valuelen != 4 || sb.charAt(pos++) != 'r' || sb.charAt(pos++) != 'u' || sb.charAt(pos++) != 'e') break;
                keyword = Type.kwTrue;
            }
        }
        return keyword;
    }

    /*
     * Enabled aggressive block sorting
     */
    public Type setNullType(StringBuilder sb, int pos, int valuelen) {
        switch (valuelen) {
            case 3: {
                if (sb.charAt(pos++) != 'i' || sb.charAt(pos++) != 'n' || sb.charAt(pos++) != 't') break;
                return Type.kwNullInt;
            }
            case 4: {
                block8 : switch (sb.charAt(pos++)) {
                    case 'b': {
                        switch (sb.charAt(pos++)) {
                            case 'o': {
                                if (sb.charAt(pos++) == 'o' && sb.charAt(pos++) == 'l') {
                                    return Type.kwNullBoolean;
                                }
                                break block8;
                            }
                            case 'l': {
                                if (sb.charAt(pos++) == 'o' && sb.charAt(pos++) == 'b') {
                                    return Type.kwNullBlob;
                                }
                                break block8;
                            }
                        }
                        break;
                    }
                    case 'l': {
                        if (sb.charAt(pos++) != 'i' || sb.charAt(pos++) != 's' || sb.charAt(pos++) != 't') break;
                        return Type.kwNullList;
                    }
                    case 'n': {
                        if (sb.charAt(pos++) != 'u' || sb.charAt(pos++) != 'l' || sb.charAt(pos++) != 'l') break;
                        return Type.kwNullNull;
                    }
                    case 'c': {
                        if (sb.charAt(pos++) != 'l' || sb.charAt(pos++) != 'o' || sb.charAt(pos++) != 'b') break;
                        return Type.kwNullClob;
                    }
                    case 's': {
                        if (sb.charAt(pos++) != 'e' || sb.charAt(pos++) != 'x' || sb.charAt(pos++) != 'p') break;
                        return Type.kwNullSexp;
                    }
                }
                break;
            }
            case 5: {
                if (sb.charAt(pos++) != 'f' || sb.charAt(pos++) != 'l' || sb.charAt(pos++) != 'o' || sb.charAt(pos++) != 'a' || sb.charAt(pos++) != 't') break;
                return Type.kwNullFloat;
            }
            case 6: {
                switch (sb.charAt(pos++)) {
                    case 's': {
                        block22 : switch (sb.charAt(pos++)) {
                            case 't': {
                                if (sb.charAt(pos++) != 'r') break;
                                switch (sb.charAt(pos++)) {
                                    case 'i': {
                                        if (sb.charAt(pos++) == 'n' && sb.charAt(pos++) == 'g') {
                                            return Type.kwNullString;
                                        }
                                        break block22;
                                    }
                                    case 'u': {
                                        if (sb.charAt(pos++) == 'c' && sb.charAt(pos++) == 't') {
                                            return Type.kwNullStruct;
                                        }
                                        break block22;
                                    }
                                }
                                break;
                            }
                            case 'y': {
                                if (sb.charAt(pos++) != 'm' || sb.charAt(pos++) != 'b' || sb.charAt(pos++) != 'o' || sb.charAt(pos++) != 'l') break;
                                return Type.kwNullSymbol;
                            }
                        }
                        break;
                    }
                }
                break;
            }
            case 7: {
                if (sb.charAt(pos++) != 'd' || sb.charAt(pos++) != 'e' || sb.charAt(pos++) != 'c' || sb.charAt(pos++) != 'i' || sb.charAt(pos++) != 'm' || sb.charAt(pos++) != 'a' || sb.charAt(pos++) != 'l') break;
                return Type.kwNullDecimal;
            }
            case 9: {
                if (sb.charAt(pos++) != 't' || sb.charAt(pos++) != 'i' || sb.charAt(pos++) != 'm' || sb.charAt(pos++) != 'e' || sb.charAt(pos++) != 's' || sb.charAt(pos++) != 't' || sb.charAt(pos++) != 'a' || sb.charAt(pos++) != 'm' || sb.charAt(pos++) != 'p') break;
                return Type.kwNullTimestamp;
            }
        }
        String nullimage = sb.toString();
        throw new IonException("invalid null value '" + nullimage + "' at " + this.position());
    }

    public Type scanOperator(int c) throws IOException {
        this.resetValue();
        this.t = Type.constSymbol;
        this.keyword = null;
        this.value.append((char)c);
        while (IonTextUtils.isOperatorPart(c = this.read())) {
            this.value.append((char)c);
        }
        this.unread(c);
        return this.t;
    }

    public Type scanString(int c, int maxlookahead) throws IOException {
        this.resetValue();
        if (c != 34) {
            throw new IonException("Programmer error! Only a quote should get you here.");
        }
        this.endquote = 34;
        block6: while (maxlookahead-- > 0) {
            c = this.read();
            switch (c) {
                case -1: {
                    break block6;
                }
                case 34: {
                    break block6;
                }
                case 10: {
                    throw new IonException("unexpected line terminator encountered in quoted string");
                }
                case 92: {
                    c = IonTokenReader.readEscapedCharacter(this.in, false);
                    if (c == -2) continue block6;
                    this.value.appendCodePoint(c);
                    continue block6;
                }
                default: {
                    this.value.append((char)c);
                    continue block6;
                }
            }
        }
        if (maxlookahead != -1 && c == 34) {
            this.closeString();
        } else {
            this.leaveOpenString(c, false);
        }
        return Type.constString;
    }

    void leaveOpenString(int c, boolean longstring) {
        if (c == -1) {
            throw new UnexpectedEofException();
        }
        this.isIncomplete = true;
        this.inQuotedContent = true;
        this.isLongString = longstring;
    }

    void finishScanString(boolean is_in_expression) throws IOException {
        assert (this.isIncomplete);
        assert (this.inQuotedContent);
        while (true) {
            int c;
            if ((c = this.read()) == -1) {
                throw new UnexpectedEofException();
            }
            if (c == this.endquote) break;
            if (c == 92) {
                c = IonTokenReader.readEscapedCharacter(this.in, false);
                if (c == -2) continue;
                this.value.appendCodePoint(c);
                continue;
            }
            if (c == 39 && this.isLongString) {
                if (this.twoMoreSingleQuotes()) {
                    this.inQuotedContent = false;
                    c = this.readIgnoreWhitespace();
                    if (c == 39 && this.twoMoreSingleQuotes()) {
                        this.inQuotedContent = true;
                        continue;
                    }
                    this.unread(c);
                    break;
                }
                this.value.append((char)c);
                continue;
            }
            if (!this.isLongString && c == 10) {
                throw new IonException("unexpected line terminator encountered in quoted string");
            }
            this.value.append((char)c);
        }
    }

    private boolean twoMoreSingleQuotes() throws IOException {
        int c = this.read();
        if (c == 39) {
            int c2 = this.read();
            if (c2 == 39) {
                return true;
            }
            this.unread(c2);
        }
        this.unread(c);
        return false;
    }

    void closeString() {
        this.isIncomplete = false;
        this.inQuotedContent = false;
        this.isLongString = false;
    }

    public Type scanLongString() throws IOException {
        this.resetValue();
        this.endquote = -1;
        this.leaveOpenString(-2, true);
        return Type.constString;
    }

    void closeLongString() {
        this.isIncomplete = true;
        this.inQuotedContent = false;
        this.isLongString = true;
    }

    public static int readEscapedCharacter(PushbackReader r, boolean inClob) throws IOException, UnexpectedEofException {
        int c2 = 0;
        int c = r.read();
        switch (c) {
            case -1: {
                throw new UnexpectedEofException();
            }
            case 116: {
                return 9;
            }
            case 110: {
                return 10;
            }
            case 118: {
                return 11;
            }
            case 114: {
                return 13;
            }
            case 102: {
                return 12;
            }
            case 98: {
                return 8;
            }
            case 97: {
                return 7;
            }
            case 92: {
                return 92;
            }
            case 34: {
                return 34;
            }
            case 39: {
                return 39;
            }
            case 47: {
                return 47;
            }
            case 63: {
                return 63;
            }
            case 85: {
                if (inClob) {
                    throw new IonException("Unicode escapes \\U not allowed in clob");
                }
                c = IonTokenReader.readDigit(r, 16, true);
                if (c < 0) break;
                c2 = c << 28;
                c = IonTokenReader.readDigit(r, 16, true);
                if (c < 0) break;
                c2 += c << 24;
                c = IonTokenReader.readDigit(r, 16, true);
                if (c < 0) break;
                c2 += c << 20;
                c = IonTokenReader.readDigit(r, 16, true);
                if (c < 0) break;
                c2 += c << 16;
            }
            case 117: {
                if (inClob) {
                    throw new IonException("Unicode escapes \\u not allowed in clob");
                }
                c = IonTokenReader.readDigit(r, 16, true);
                if (c < 0) break;
                c2 += c << 12;
                c = IonTokenReader.readDigit(r, 16, true);
                if (c < 0) break;
                c2 += c << 8;
            }
            case 120: {
                c = IonTokenReader.readDigit(r, 16, true);
                if (c < 0) break;
                c2 += c << 4;
                c = IonTokenReader.readDigit(r, 16, true);
                if (c < 0) break;
                return c2 + c;
            }
            case 48: {
                return 0;
            }
            case 10: {
                return -2;
            }
        }
        throw new IonException("invalid escape sequence \"\\" + (char)c + "\" [" + c + "]");
    }

    public static int readDigit(PushbackReader r, int radix, boolean isRequired) throws IOException {
        int c = r.read();
        if (c < 0) {
            r.unread(c);
            return -1;
        }
        int c2 = Character.digit(c, radix);
        if (c2 < 0) {
            if (isRequired) {
                throw new IonException("bad digit in escaped character '" + (char)c + "'");
            }
            r.unread(c);
            return -1;
        }
        return c2;
    }

    private void checkAndUnreadNumericStopper(int c) throws IOException {
        if (c != -1) {
            if (!this.isValueTerminatingCharacter(c)) {
                String message = this.position() + ": Numeric value followed by invalid character " + IonTextUtils.printCodePointAsString(c);
                throw new IonException(message);
            }
            this.unread(c);
        }
    }

    private final boolean isValueTerminatingCharacter(int c) throws IOException {
        boolean isTerminator;
        if (c == 47) {
            c = this.read();
            this.unread(c);
            isTerminator = c == 47 || c == 42;
        } else {
            isTerminator = IonTextUtils.isNumericStop(c);
        }
        return isTerminator;
    }

    public Type readNumber(int c) throws IOException {
        boolean isZero;
        this.resetValue();
        boolean explicitPlusSign = false;
        switch (c) {
            case 45: {
                this.value.append((char)c);
                c = this.read();
                this.t = Type.constNegInt;
                this.numberType = NumberType.NT_NEGINT;
                this.isNegative = true;
                break;
            }
            case 43: {
                explicitPlusSign = true;
                c = this.read();
                this.t = Type.constPosInt;
                this.isNegative = false;
                this.numberType = NumberType.NT_POSINT;
                break;
            }
            default: {
                this.t = Type.constPosInt;
                this.numberType = NumberType.NT_POSINT;
                this.isNegative = false;
            }
        }
        assert (IonTextUtils.isDigit(c, 10));
        this.value.append((char)c);
        boolean leadingZero = isZero = c == 48;
        c = this.read();
        switch (c) {
            case 88: 
            case 120: {
                if (!isZero) {
                    throw new IonException("badly formed number encountered at " + this.position());
                }
                this.numberType = NumberType.NT_HEX;
                return this.scanHexNumber();
            }
            case 46: 
            case 68: 
            case 100: {
                this.t = Type.constDecimal;
                this.numberType = NumberType.NT_DECIMAL;
                this.value.append((char)c);
                break;
            }
            case 69: 
            case 101: {
                this.t = Type.constFloat;
                this.numberType = NumberType.NT_FLOAT;
                this.value.append((char)c);
                break;
            }
            default: {
                if (!IonTextUtils.isDigit(c, 10)) {
                    this.checkAndUnreadNumericStopper(c);
                    if (isZero && NumberType.NT_NEGINT.equals((Object)this.numberType)) {
                        this.t = Type.constPosInt;
                        this.numberType = NumberType.NT_POSINT;
                    }
                    return this.t;
                }
                this.value.append((char)c);
                isZero &= c == 48;
            }
        }
        if (NumberType.NT_NEGINT.equals((Object)this.numberType) || NumberType.NT_POSINT.equals((Object)this.numberType)) {
            while (IonTextUtils.isDigit(c = this.read(), 10)) {
                this.value.append((char)c);
                isZero &= c == 48;
            }
            switch (c) {
                case 46: 
                case 68: 
                case 100: {
                    if (leadingZero) {
                        throw new IonException(this.position() + ": Invalid leading zero on numeric");
                    }
                    this.t = Type.constDecimal;
                    this.numberType = NumberType.NT_DECIMAL;
                    this.value.append((char)c);
                    break;
                }
                case 69: 
                case 101: {
                    if (leadingZero) {
                        throw new IonException(this.position() + ": Invalid leading zero on numeric");
                    }
                    this.t = Type.constFloat;
                    this.numberType = NumberType.NT_FLOAT;
                    this.value.append((char)c);
                    break;
                }
                case 45: 
                case 84: {
                    if (NumberType.NT_POSINT.equals((Object)this.numberType) && !explicitPlusSign) {
                        return this.scanTimestamp(c);
                    }
                }
                default: {
                    this.checkAndUnreadNumericStopper(c);
                    if (leadingZero && !isZero) {
                        throw new IonException(this.position() + ": Invalid leading zero on numeric");
                    }
                    if (isZero && NumberType.NT_NEGINT.equals((Object)this.numberType)) {
                        this.t = Type.constPosInt;
                        this.numberType = NumberType.NT_POSINT;
                    }
                    return this.t;
                }
            }
        }
        if (NumberType.NT_DECIMAL.equals((Object)this.numberType)) {
            while (IonTextUtils.isDigit(c = this.read(), 10)) {
                this.value.append((char)c);
            }
            switch (c) {
                case 43: 
                case 45: {
                    this.unread(c);
                    break;
                }
                case 69: 
                case 101: {
                    this.t = Type.constFloat;
                    this.numberType = NumberType.NT_FLOAT;
                    this.value.append((char)c);
                    break;
                }
                case 68: 
                case 100: {
                    assert (this.t == Type.constDecimal);
                    assert (NumberType.NT_DECIMAL.equals((Object)this.numberType));
                    this.value.append((char)c);
                    break;
                }
                default: {
                    this.checkAndUnreadNumericStopper(c);
                    return this.t;
                }
            }
        }
        c = this.read();
        switch (c) {
            case 45: {
                this.value.append((char)c);
                break;
            }
            case 43: {
                break;
            }
            default: {
                if (!IonTextUtils.isDigit(c, 10)) {
                    throw new IonException("badly formed number encountered at " + this.position());
                }
                this.unread(c);
            }
        }
        while (IonTextUtils.isDigit(c = this.read(), 10)) {
            this.value.append((char)c);
        }
        this.checkAndUnreadNumericStopper(c);
        return this.t;
    }

    Type scanHexNumber() throws IOException {
        int c;
        boolean anydigits = false;
        boolean isZero = true;
        this.value.setLength(0);
        while (IonTextUtils.isDigit(c = this.read(), 16)) {
            anydigits = true;
            isZero &= c == 48;
            this.value.append((char)c);
        }
        if (!anydigits) {
            throw new IonException("badly formed hexadecimal number encountered at " + this.position());
        }
        this.checkAndUnreadNumericStopper(c);
        if (isZero && this.t == Type.constNegInt) {
            this.t = Type.constPosInt;
        }
        return this.t;
    }

    Type scanTimestamp(int c) throws IOException {
        block8: {
            block10: {
                block12: {
                    block11: {
                        block9: {
                            block7: {
                                if (c != 84) break block7;
                                this.value.append((char)c);
                                c = this.read();
                                break block8;
                            }
                            if (c != 45) {
                                throw new IllegalStateException("invalid timestamp, expecting a dash here at " + this.position());
                            }
                            this.value.append((char)c);
                            c = this.readDigits(2, "month");
                            if (c != 84) break block9;
                            this.value.append((char)c);
                            c = this.read();
                            break block8;
                        }
                        if (c != 45) {
                            throw new IonException("invalid timestamp, expecting month at " + this.position());
                        }
                        this.value.append((char)c);
                        c = this.readDigits(2, "day of month");
                        if (c != 84) break block8;
                        this.value.append((char)c);
                        int length_before_reading_hours = this.value.length();
                        c = this.readDigits(2, "hours");
                        if (length_before_reading_hours == this.value.length()) break block10;
                        if (c != 58) {
                            throw new IonException("invalid timestamp, expecting hours at " + this.position());
                        }
                        this.value.append((char)c);
                        c = this.readDigits(2, "minutes");
                        if (c == 58) break block11;
                        if (c != 45 && c != 43 && c != 90) break block8;
                        break block10;
                    }
                    this.value.append((char)c);
                    c = this.readDigits(2, "seconds");
                    if (c == 46) break block12;
                    if (c != 45 && c != 43 && c != 90) break block8;
                    break block10;
                }
                this.value.append((char)c);
                c = this.readDigits(32, "fractional seconds");
            }
            if (c == 45 || c == 43) {
                this.value.append((char)c);
                c = this.readDigits(2, "timezone offset");
                if (c == 58) {
                    this.value.append((char)c);
                    c = this.readDigits(2, "timezone offset");
                }
            } else if (c == 90) {
                this.value.append((char)c);
                c = this.read();
            }
        }
        this.checkAndUnreadNumericStopper(c);
        return Type.constTime;
    }

    int readDigits(int limit2, String field) throws IOException {
        int c;
        int len = 0;
        while (IonTextUtils.isDigit(c = this.read(), 10)) {
            if (++len > limit2) {
                throw new IonException("invalid format " + field + " too long at " + this.position());
            }
            this.value.append((char)c);
        }
        return c;
    }

    public boolean makeValidNumeric(Type castto) throws IOException {
        String s = this.getValueString(false);
        try {
            this.t = castto.setNumericValue(this, s);
        } catch (NumberFormatException e) {
            throw new IonException(this.position() + ": invalid numeric value " + s, e);
        }
        return this.t.isNumeric();
    }

    public static enum Context {
        NONE,
        STRING,
        BLOB,
        CLOB,
        EXPRESSION,
        DATALIST,
        STRUCT;

    }

    static enum NumberType {
        NT_POSINT,
        NT_NEGINT,
        NT_HEX,
        NT_FLOAT,
        NT_DECIMAL,
        NT_DECIMAL_NEGATIVE_ZERO;

    }

    static class LocalReader
    extends Reader {
        IonTokenReader _tr;
        int _sboffset;
        int _sbavailable;

        LocalReader(IonTokenReader tr) {
            this._tr = tr;
        }

        @Override
        public void close() throws IOException {
            this._tr = null;
        }

        @Override
        public void reset() {
            this._sboffset = 0;
            this._sbavailable = this._tr.value.length();
        }

        @Override
        public int read() throws IOException {
            int c = -1;
            if (this._sbavailable > 0) {
                c = this._tr.value.charAt(this._sboffset++);
                --this._sbavailable;
            } else {
                c = this._tr.read();
            }
            return c;
        }

        @Override
        public int read(char[] dst, int dstoffset, int len) throws IOException {
            int c;
            int needed = len;
            while (needed-- > 0 && (c = this.read()) >= 0) {
                dst[dstoffset++] = (char)c;
            }
            return len - needed;
        }
    }

    public static final class Type
    extends Enum<Type> {
        public static final /* enum */ Type eof = new Type(isPunctuation, "<eof>");
        public static final /* enum */ Type tOpenParen = new Type(isPunctuation, "(");
        public static final /* enum */ Type tCloseParen = new Type(isPunctuation, ")");
        public static final /* enum */ Type tOpenSquare = new Type(isPunctuation, "[");
        public static final /* enum */ Type tCloseSquare = new Type(isPunctuation, "[");
        public static final /* enum */ Type tOpenCurly = new Type(isPunctuation, "{");
        public static final /* enum */ Type tCloseCurly = new Type(isPunctuation, "}");
        public static final /* enum */ Type tOpenDoubleCurly = new Type(isPunctuation, "{{");
        public static final /* enum */ Type tSingleQuote = new Type(isPunctuation, "'");
        public static final /* enum */ Type tDoubleQuote = new Type(isPunctuation, "\"");
        public static final /* enum */ Type tComma = new Type(isPunctuation, ",");
        public static final /* enum */ Type kwTrue = new Type(isConstant + isTag + isKeyword, "true", _Private_IonConstants.HighNibble.hnBoolean);
        public static final /* enum */ Type kwFalse = new Type(isConstant + isTag + isKeyword, "false", _Private_IonConstants.HighNibble.hnBoolean);
        public static final /* enum */ Type kwNull = new Type(isConstant + isTag + isKeyword, "null", _Private_IonConstants.HighNibble.hnNull);
        public static final /* enum */ Type kwNullNull = new Type(isConstant + isTag + isKeyword, "null.null", _Private_IonConstants.HighNibble.hnNull);
        public static final /* enum */ Type kwNullInt = new Type(isConstant + isTag + isKeyword, "null.int", _Private_IonConstants.HighNibble.hnPosInt);
        public static final /* enum */ Type kwNullList = new Type(isConstant + isTag + isKeyword, "null.list", _Private_IonConstants.HighNibble.hnList);
        public static final /* enum */ Type kwNullSexp = new Type(isConstant + isTag + isKeyword, "null.sexp", _Private_IonConstants.HighNibble.hnSexp);
        public static final /* enum */ Type kwNullFloat = new Type(isConstant + isTag + isKeyword, "null.float", _Private_IonConstants.HighNibble.hnFloat);
        public static final /* enum */ Type kwNullBlob = new Type(isConstant + isTag + isKeyword, "null.blob", _Private_IonConstants.HighNibble.hnBlob);
        public static final /* enum */ Type kwNullClob = new Type(isConstant + isTag + isKeyword, "null.clob", _Private_IonConstants.HighNibble.hnClob);
        public static final /* enum */ Type kwNullString = new Type(isConstant + isTag + isKeyword, "null.string", _Private_IonConstants.HighNibble.hnString);
        public static final /* enum */ Type kwNullStruct = new Type(isConstant + isTag + isKeyword, "null.struct", _Private_IonConstants.HighNibble.hnStruct);
        public static final /* enum */ Type kwNullSymbol = new Type(isConstant + isTag + isKeyword, "null.symbol", _Private_IonConstants.HighNibble.hnSymbol);
        public static final /* enum */ Type kwNullBoolean = new Type(isConstant + isTag + isKeyword, "null.bool", _Private_IonConstants.HighNibble.hnBoolean);
        public static final /* enum */ Type kwNullDecimal = new Type(isConstant + isTag + isKeyword, "null.decimal", _Private_IonConstants.HighNibble.hnDecimal);
        public static final /* enum */ Type kwNullTimestamp = new Type(isConstant + isTag + isKeyword, "null.timestamp", _Private_IonConstants.HighNibble.hnTimestamp);
        public static final /* enum */ Type kwNan = new Type(isConstant + isKeyword, "nan", _Private_IonConstants.HighNibble.hnFloat);
        public static final /* enum */ Type kwPosInf = new Type(isConstant + isKeyword, "+inf", _Private_IonConstants.HighNibble.hnFloat);
        public static final /* enum */ Type kwNegInf = new Type(isConstant + isKeyword, "-inf", _Private_IonConstants.HighNibble.hnFloat);
        public static final /* enum */ Type constNegInt = new Type(isConstant + IonTokenReader.access$000(), "cNegInt", _Private_IonConstants.HighNibble.hnNegInt);
        public static final /* enum */ Type constPosInt = new Type(isConstant + IonTokenReader.access$100(), "cPosInt", _Private_IonConstants.HighNibble.hnPosInt);
        public static final /* enum */ Type constFloat = new Type(isConstant + isFloat, "cFloat", _Private_IonConstants.HighNibble.hnFloat);
        public static final /* enum */ Type constDecimal = new Type(isConstant + isDecimal, "cDec", _Private_IonConstants.HighNibble.hnDecimal);
        public static final /* enum */ Type constTime = new Type(isConstant, "cTime", _Private_IonConstants.HighNibble.hnTimestamp);
        public static final /* enum */ Type constString = new Type(isConstant + isTag, "cString", _Private_IonConstants.HighNibble.hnString);
        public static final /* enum */ Type constSymbol = new Type(isConstant + isTag, "cSymbol", _Private_IonConstants.HighNibble.hnSymbol);
        public static final /* enum */ Type constMemberName = new Type(isConstant + isTag, "cMemberName", _Private_IonConstants.HighNibble.hnSymbol);
        public static final /* enum */ Type constUserTypeDecl = new Type(isConstant + isTag, "cUserTypeDecl", _Private_IonConstants.HighNibble.hnSymbol);
        public static final /* enum */ Type none = new Type(0);
        private int flags;
        private String image;
        private _Private_IonConstants.HighNibble highNibble;
        private static final /* synthetic */ Type[] $VALUES;

        public static Type[] values() {
            return (Type[])$VALUES.clone();
        }

        public static Type valueOf(String name) {
            return Enum.valueOf(Type.class, name);
        }

        private Type() {
        }

        private Type(int v) {
            this.flags = v;
        }

        private Type(int v, String name) {
            this.flags = v;
            this.image = name;
        }

        private Type(int v, String name, _Private_IonConstants.HighNibble ln) {
            this.flags = v;
            this.image = name;
            this.highNibble = ln;
        }

        public Type setNumericValue(IonTokenReader tr, String s) {
            switch (this) {
                case kwNan: {
                    tr.doubleValue = Double.NaN;
                    return this;
                }
                case kwPosInf: {
                    tr.doubleValue = Double.POSITIVE_INFINITY;
                    return this;
                }
                case kwNegInf: {
                    tr.doubleValue = Double.NEGATIVE_INFINITY;
                    return this;
                }
                case constNegInt: 
                case constPosInt: {
                    if (NumberType.NT_HEX.equals((Object)tr.numberType)) {
                        tr.intValue = new BigInteger(s, 16);
                        if (this == constNegInt) {
                            tr.intValue = tr.intValue.negate();
                        }
                    } else {
                        tr.intValue = new BigInteger(s, 10);
                        assert (BigInteger.ZERO.equals(tr.intValue) || this == (tr.intValue.signum() < 0 ? constNegInt : constPosInt));
                    }
                    return this;
                }
                case constFloat: {
                    tr.doubleValue = Double.parseDouble(s);
                    return this;
                }
                case constDecimal: {
                    String eFormat = s.replace('d', 'e');
                    if (eFormat == s) {
                        eFormat = s.replace('D', 'e');
                    }
                    tr.decimalValue = Decimal.valueOf(eFormat);
                    return this;
                }
                case constTime: {
                    tr.dateValue = timeinfo.parse(s);
                    return this;
                }
            }
            throw new AssertionError((Object)("Unknown op for numeric case: " + (Object)((Object)this)));
        }

        public boolean isKeyword() {
            return (this.flags & isKeyword) != 0;
        }

        public boolean isConstant() {
            return (this.flags & isConstant) != 0;
        }

        public boolean isNumeric() {
            return (this.flags & isPosInt + isNegInt + isFloat + isDecimal) != 0;
        }

        public String getImage() {
            return this.image == null ? this.name() : this.image;
        }

        public _Private_IonConstants.HighNibble getHighNibble() {
            return this.highNibble;
        }

        public String toString() {
            if (this.getImage() != null) {
                return this.getImage();
            }
            return super.toString();
        }

        static {
            $VALUES = new Type[]{eof, tOpenParen, tCloseParen, tOpenSquare, tCloseSquare, tOpenCurly, tCloseCurly, tOpenDoubleCurly, tSingleQuote, tDoubleQuote, tComma, kwTrue, kwFalse, kwNull, kwNullNull, kwNullInt, kwNullList, kwNullSexp, kwNullFloat, kwNullBlob, kwNullClob, kwNullString, kwNullStruct, kwNullSymbol, kwNullBoolean, kwNullDecimal, kwNullTimestamp, kwNan, kwPosInf, kwNegInf, constNegInt, constPosInt, constFloat, constDecimal, constTime, constString, constSymbol, constMemberName, constUserTypeDecl, none};
        }

        public static class timeinfo {
            public static Timestamp parse(String s) {
                Timestamp t = null;
                s = s.trim();
                try {
                    t = Timestamp.valueOf(s);
                } catch (IllegalArgumentException e) {
                    throw new IonException(e);
                }
                return t;
            }
        }
    }
}

