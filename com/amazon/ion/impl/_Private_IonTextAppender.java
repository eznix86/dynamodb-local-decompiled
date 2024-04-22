/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazon.ion.impl;

import com.amazon.ion.Decimal;
import com.amazon.ion.impl.AppendableFastAppendable;
import com.amazon.ion.impl.Base64Encoder;
import com.amazon.ion.impl.OutputStreamFastAppendable;
import com.amazon.ion.impl._Private_IonConstants;
import com.amazon.ion.impl._Private_IonTextWriterBuilder;
import com.amazon.ion.impl._Private_Utils;
import com.amazon.ion.util._Private_FastAppendable;
import java.io.ByteArrayInputStream;
import java.io.Closeable;
import java.io.Flushable;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.CharBuffer;
import java.nio.charset.Charset;

public final class _Private_IonTextAppender
implements Closeable,
Flushable {
    private static final boolean[] IDENTIFIER_START_CHAR_FLAGS;
    private static final boolean[] IDENTIFIER_FOLLOW_CHAR_FLAGS;
    public static final boolean[] OPERATOR_CHAR_FLAGS;
    public static final String[] ZERO_PADDING;
    private static final String[] STRING_ESCAPE_CODES;
    static final String[] LONG_STRING_ESCAPE_CODES;
    static final String[] SYMBOL_ESCAPE_CODES;
    static final String[] JSON_ESCAPE_CODES;
    private static final String HEX_4_PREFIX = "\\u";
    private static final String HEX_8_PREFIX = "\\U";
    private static final String TRIPLE_QUOTES = "'''";
    private final _Private_FastAppendable myAppendable;
    private final boolean escapeNonAscii;
    private final char[] _fixedIntBuffer = new char[_Private_IonConstants.MAX_LONG_TEXT_SIZE];

    private static boolean is8bitValue(int v) {
        return (v & 0xFFFFFF00) == 0;
    }

    private static boolean isDecimalDigit(int codePoint) {
        return codePoint >= 48 && codePoint <= 57;
    }

    public static boolean isIdentifierStart(int codePoint) {
        return IDENTIFIER_START_CHAR_FLAGS[codePoint & 0xFF] && _Private_IonTextAppender.is8bitValue(codePoint);
    }

    public static boolean isIdentifierPart(int codePoint) {
        return IDENTIFIER_FOLLOW_CHAR_FLAGS[codePoint & 0xFF] && _Private_IonTextAppender.is8bitValue(codePoint);
    }

    public static boolean isOperatorPart(int codePoint) {
        return OPERATOR_CHAR_FLAGS[codePoint & 0xFF] && _Private_IonTextAppender.is8bitValue(codePoint);
    }

    _Private_IonTextAppender(_Private_FastAppendable out, boolean escapeNonAscii) {
        this.myAppendable = out;
        this.escapeNonAscii = escapeNonAscii;
    }

    public static _Private_IonTextAppender forFastAppendable(_Private_FastAppendable out, Charset charset) {
        boolean escapeNonAscii = charset.equals(_Private_Utils.ASCII_CHARSET);
        return new _Private_IonTextAppender(out, escapeNonAscii);
    }

    public static _Private_IonTextAppender forAppendable(Appendable out, Charset charset) {
        AppendableFastAppendable fast = new AppendableFastAppendable(out);
        return _Private_IonTextAppender.forFastAppendable(fast, charset);
    }

    public static _Private_IonTextAppender forAppendable(Appendable out) {
        AppendableFastAppendable fast = new AppendableFastAppendable(out);
        boolean escapeNonAscii = false;
        return new _Private_IonTextAppender(fast, escapeNonAscii);
    }

    public static _Private_IonTextAppender forOutputStream(OutputStream out, Charset charset) {
        OutputStreamFastAppendable fast = new OutputStreamFastAppendable(out);
        return _Private_IonTextAppender.forFastAppendable(fast, charset);
    }

    @Override
    public void flush() throws IOException {
        if (this.myAppendable instanceof Flushable) {
            ((Flushable)((Object)this.myAppendable)).flush();
        }
    }

    @Override
    public void close() throws IOException {
        if (this.myAppendable instanceof Closeable) {
            ((Closeable)((Object)this.myAppendable)).close();
        }
    }

    public void appendAscii(char c) throws IOException {
        this.myAppendable.appendAscii(c);
    }

    public void appendAscii(CharSequence csq) throws IOException {
        this.myAppendable.appendAscii(csq);
    }

    public void appendAscii(CharSequence csq, int start, int end) throws IOException {
        this.myAppendable.appendAscii(csq, start, end);
    }

    public void appendUtf16(char c) throws IOException {
        this.myAppendable.appendUtf16(c);
    }

    public void appendUtf16Surrogate(char leadSurrogate, char trailSurrogate) throws IOException {
        this.myAppendable.appendUtf16Surrogate(leadSurrogate, trailSurrogate);
    }

    public final void printString(CharSequence text) throws IOException {
        if (text == null) {
            this.appendAscii("null.string");
        } else {
            this.appendAscii('\"');
            this.printCodePoints(text, STRING_ESCAPE_CODES);
            this.appendAscii('\"');
        }
    }

    public final void printLongString(CharSequence text) throws IOException {
        if (text == null) {
            this.appendAscii("null.string");
        } else {
            this.appendAscii(TRIPLE_QUOTES);
            this.printCodePoints(text, LONG_STRING_ESCAPE_CODES);
            this.appendAscii(TRIPLE_QUOTES);
        }
    }

    public final void printJsonString(CharSequence text) throws IOException {
        if (text == null) {
            this.appendAscii("null");
        } else {
            this.appendAscii('\"');
            this.printCodePoints(text, JSON_ESCAPE_CODES);
            this.appendAscii('\"');
        }
    }

    public static boolean isIdentifierKeyword(CharSequence text) {
        int pos = 0;
        int valuelen = text.length();
        if (valuelen == 0) {
            return false;
        }
        boolean keyword = false;
        switch (text.charAt(pos++)) {
            case '$': {
                if (valuelen == 1) {
                    return false;
                }
                while (pos < valuelen) {
                    char c;
                    if (_Private_IonTextAppender.isDecimalDigit(c = text.charAt(pos++))) continue;
                    return false;
                }
                return true;
            }
            case 'f': {
                if (valuelen != 5 || text.charAt(pos++) != 'a' || text.charAt(pos++) != 'l' || text.charAt(pos++) != 's' || text.charAt(pos++) != 'e') break;
                keyword = true;
                break;
            }
            case 'n': {
                if (valuelen == 4 && text.charAt(pos++) == 'u' && text.charAt(pos++) == 'l' && text.charAt(pos++) == 'l') {
                    keyword = true;
                    break;
                }
                if (valuelen != 3 || text.charAt(pos++) != 'a' || text.charAt(pos++) != 'n') break;
                keyword = true;
                break;
            }
            case 't': {
                if (valuelen != 4 || text.charAt(pos++) != 'r' || text.charAt(pos++) != 'u' || text.charAt(pos++) != 'e') break;
                keyword = true;
            }
        }
        return keyword;
    }

    public static boolean symbolNeedsQuoting(CharSequence symbol, boolean quoteOperators) {
        int length = symbol.length();
        if (length == 0 || _Private_IonTextAppender.isIdentifierKeyword(symbol)) {
            return true;
        }
        char c = symbol.charAt(0);
        if (!quoteOperators && _Private_IonTextAppender.isOperatorPart(c)) {
            for (int ii = 0; ii < length; ++ii) {
                c = symbol.charAt(ii);
                if (_Private_IonTextAppender.isOperatorPart(c)) continue;
                return true;
            }
            return false;
        }
        if (_Private_IonTextAppender.isIdentifierStart(c)) {
            for (int ii = 0; ii < length; ++ii) {
                c = symbol.charAt(ii);
                if (c != '\'' && c >= ' ' && c <= '~' && _Private_IonTextAppender.isIdentifierPart(c)) continue;
                return true;
            }
            return false;
        }
        return true;
    }

    public final void printSymbol(CharSequence text) throws IOException {
        if (text == null) {
            this.appendAscii("null.symbol");
        } else if (_Private_IonTextAppender.symbolNeedsQuoting(text, true)) {
            this.appendAscii('\'');
            this.printCodePoints(text, SYMBOL_ESCAPE_CODES);
            this.appendAscii('\'');
        } else {
            this.appendAscii(text);
        }
    }

    public final void printQuotedSymbol(CharSequence text) throws IOException {
        if (text == null) {
            this.appendAscii("null.symbol");
        } else {
            this.appendAscii('\'');
            this.printCodePoints(text, SYMBOL_ESCAPE_CODES);
            this.appendAscii('\'');
        }
    }

    private final void printCodePoints(CharSequence text, String[] escapes) throws IOException {
        int len = text.length();
        for (int i = 0; i < len; ++i) {
            int j;
            char c = '\u0000';
            for (j = i; j < len; ++j) {
                c = text.charAt(j);
                if (c < '\u0100' && escapes[c] == null) continue;
                if (j <= i) break;
                this.appendAscii(text, i, j);
                i = j;
                break;
            }
            if (j == len) {
                this.appendAscii(text, i, j);
                break;
            }
            if (c < '\u0080') {
                assert (escapes[c] != null);
                this.appendAscii(escapes[c]);
                continue;
            }
            if (c < '\u0100') {
                assert (escapes[c] != null);
                if (this.escapeNonAscii || c <= '\u009f') {
                    this.appendAscii(escapes[c]);
                    continue;
                }
                this.appendUtf16(c);
                continue;
            }
            if (c < '\ud800' || c >= '\ue000') {
                String s = Integer.toHexString(c);
                if (this.escapeNonAscii) {
                    this.appendAscii(HEX_4_PREFIX);
                    this.appendAscii(ZERO_PADDING[4 - s.length()]);
                    this.appendAscii(s);
                    continue;
                }
                this.appendUtf16(c);
                continue;
            }
            if (_Private_IonConstants.isHighSurrogate(c)) {
                char c2;
                if (++i == len || !_Private_IonConstants.isLowSurrogate(c2 = text.charAt(i))) {
                    String message = "text is invalid UTF-16. It contains an unmatched leading surrogate 0x" + Integer.toHexString(c) + " at index " + (i - 1);
                    throw new IllegalArgumentException(message);
                }
                if (this.escapeNonAscii) {
                    int cp = _Private_IonConstants.makeUnicodeScalar(c, c2);
                    String s = Integer.toHexString(cp);
                    this.appendAscii(HEX_8_PREFIX);
                    this.appendAscii(ZERO_PADDING[8 - s.length()]);
                    this.appendAscii(s);
                    continue;
                }
                this.appendUtf16Surrogate(c, c2);
                continue;
            }
            assert (_Private_IonConstants.isLowSurrogate(c));
            String message = "text is invalid UTF-16. It contains an unmatched trailing surrogate 0x" + Integer.toHexString(c) + " at index " + i;
            throw new IllegalArgumentException(message);
        }
    }

    public void printInt(long value) throws IOException {
        int j = this._fixedIntBuffer.length;
        if (value == 0L) {
            this._fixedIntBuffer[--j] = 48;
        } else if (value < 0L) {
            while (value != 0L) {
                this._fixedIntBuffer[--j] = (char)(48L - value % 10L);
                value /= 10L;
            }
            this._fixedIntBuffer[--j] = 45;
        } else {
            while (value != 0L) {
                this._fixedIntBuffer[--j] = (char)(48L + value % 10L);
                value /= 10L;
            }
        }
        this.appendAscii(CharBuffer.wrap(this._fixedIntBuffer), j, this._fixedIntBuffer.length);
    }

    public void printInt(BigInteger value) throws IOException {
        if (value == null) {
            this.appendAscii("null.int");
            return;
        }
        this.appendAscii(this.bigIntegerToString(value));
    }

    public void printDecimal(_Private_IonTextWriterBuilder _options, BigDecimal value) throws IOException {
        if (value == null) {
            this.appendAscii("null.decimal");
            return;
        }
        BigInteger unscaled = value.unscaledValue();
        int signum = value.signum();
        if (signum < 0) {
            this.appendAscii('-');
            unscaled = unscaled.negate();
        } else if (value instanceof Decimal && ((Decimal)value).isNegativeZero()) {
            this.appendAscii('-');
        }
        String unscaledText = this.bigIntegerToString(unscaled);
        int significantDigits = unscaledText.length();
        int scale = value.scale();
        int exponent = -scale;
        if (_options._decimal_as_float) {
            this.appendAscii(unscaledText);
            this.appendAscii('e');
            this.appendAscii(Integer.toString(exponent));
        } else if (exponent == 0) {
            this.appendAscii(unscaledText);
            this.appendAscii('.');
        } else if (exponent < 0) {
            int adjustedExponent = significantDigits - 1 - scale;
            if (adjustedExponent >= 0) {
                int wholeDigits = significantDigits - scale;
                this.appendAscii(unscaledText, 0, wholeDigits);
                this.appendAscii('.');
                this.appendAscii(unscaledText, wholeDigits, significantDigits);
            } else if (adjustedExponent >= -6) {
                this.appendAscii("0.");
                this.appendAscii("00000", 0, scale - significantDigits);
                this.appendAscii(unscaledText);
            } else {
                this.appendAscii(unscaledText);
                this.appendAscii("d-");
                this.appendAscii(Integer.toString(scale));
            }
        } else {
            this.appendAscii(unscaledText);
            this.appendAscii('d');
            this.appendAscii(Integer.toString(exponent));
        }
    }

    public void printFloat(_Private_IonTextWriterBuilder _options, double value) throws IOException {
        if (value == 0.0) {
            if (Double.compare(value, 0.0) == 0) {
                this.appendAscii("0e0");
            } else {
                this.appendAscii("-0e0");
            }
        } else if (Double.isNaN(value)) {
            if (_options._float_nan_and_inf_as_null) {
                this.appendAscii("null");
            } else {
                this.appendAscii("nan");
            }
        } else if (value == Double.POSITIVE_INFINITY) {
            if (_options._float_nan_and_inf_as_null) {
                this.appendAscii("null");
            } else {
                this.appendAscii("+inf");
            }
        } else if (value == Double.NEGATIVE_INFINITY) {
            if (_options._float_nan_and_inf_as_null) {
                this.appendAscii("null");
            } else {
                this.appendAscii("-inf");
            }
        } else {
            String str = Double.toString(value);
            if (str.endsWith(".0")) {
                this.appendAscii(str, 0, str.length() - 2);
                this.appendAscii("e0");
            } else {
                this.appendAscii(str);
                if (str.indexOf(69) == -1) {
                    this.appendAscii("e0");
                }
            }
        }
    }

    public void printFloat(_Private_IonTextWriterBuilder _options, Double value) throws IOException {
        if (value == null) {
            this.appendAscii("null.float");
        } else {
            this.printFloat(_options, (double)value);
        }
    }

    public void printBlob(_Private_IonTextWriterBuilder _options, byte[] value, int start, int len) throws IOException {
        int clen;
        if (value == null) {
            this.appendAscii("null.blob");
            return;
        }
        Base64Encoder.TextStream ts = new Base64Encoder.TextStream(new ByteArrayInputStream(value, start, len));
        char[] buf = new char[_options.isPrettyPrintOn() ? 80 : 400];
        CharBuffer cb = CharBuffer.wrap(buf);
        if (_options._blob_as_string) {
            this.appendAscii('\"');
        } else {
            this.appendAscii("{{");
            if (_options.isPrettyPrintOn()) {
                this.appendAscii(' ');
            }
        }
        while ((clen = ts.read(buf, 0, buf.length)) >= 1) {
            this.appendAscii(cb, 0, clen);
        }
        if (_options._blob_as_string) {
            this.appendAscii('\"');
        } else {
            if (_options.isPrettyPrintOn()) {
                this.appendAscii(' ');
            }
            this.appendAscii("}}");
        }
    }

    private void printClobBytes(byte[] value, int start, int end, String[] escapes) throws IOException {
        for (int i = start; i < end; ++i) {
            char c = (char)(value[i] & 0xFF);
            String escapedByte = escapes[c];
            if (escapedByte != null) {
                this.appendAscii(escapedByte);
                continue;
            }
            this.appendAscii(c);
        }
    }

    public void printClob(_Private_IonTextWriterBuilder _options, byte[] value, int start, int len) throws IOException {
        boolean longString;
        if (value == null) {
            this.appendAscii("null.clob");
            return;
        }
        boolean json = _options._clob_as_string && _options._string_as_json;
        int threshold = _options.getLongStringThreshold();
        boolean bl = longString = 0 < threshold && threshold < value.length;
        if (!_options._clob_as_string) {
            this.appendAscii("{{");
            if (_options.isPrettyPrintOn()) {
                this.appendAscii(' ');
            }
        }
        if (json) {
            this.appendAscii('\"');
            this.printClobBytes(value, start, start + len, JSON_ESCAPE_CODES);
            this.appendAscii('\"');
        } else if (longString) {
            this.appendAscii(TRIPLE_QUOTES);
            this.printClobBytes(value, start, start + len, LONG_STRING_ESCAPE_CODES);
            this.appendAscii(TRIPLE_QUOTES);
        } else {
            this.appendAscii('\"');
            this.printClobBytes(value, start, start + len, STRING_ESCAPE_CODES);
            this.appendAscii('\"');
        }
        if (!_options._clob_as_string) {
            if (_options.isPrettyPrintOn()) {
                this.appendAscii(' ');
            }
            this.appendAscii("}}");
        }
    }

    private String bigIntegerToString(BigInteger value) {
        if (value.bitLength() >= 64) {
            return value.toString();
        }
        return Long.toString(value.longValue());
    }

    static {
        int i;
        int ii;
        IDENTIFIER_START_CHAR_FLAGS = new boolean[256];
        IDENTIFIER_FOLLOW_CHAR_FLAGS = new boolean[256];
        for (ii = 97; ii <= 122; ++ii) {
            _Private_IonTextAppender.IDENTIFIER_START_CHAR_FLAGS[ii] = true;
            _Private_IonTextAppender.IDENTIFIER_FOLLOW_CHAR_FLAGS[ii] = true;
        }
        for (ii = 65; ii <= 90; ++ii) {
            _Private_IonTextAppender.IDENTIFIER_START_CHAR_FLAGS[ii] = true;
            _Private_IonTextAppender.IDENTIFIER_FOLLOW_CHAR_FLAGS[ii] = true;
        }
        _Private_IonTextAppender.IDENTIFIER_START_CHAR_FLAGS[95] = true;
        _Private_IonTextAppender.IDENTIFIER_FOLLOW_CHAR_FLAGS[95] = true;
        _Private_IonTextAppender.IDENTIFIER_START_CHAR_FLAGS[36] = true;
        _Private_IonTextAppender.IDENTIFIER_FOLLOW_CHAR_FLAGS[36] = true;
        for (ii = 48; ii <= 57; ++ii) {
            _Private_IonTextAppender.IDENTIFIER_FOLLOW_CHAR_FLAGS[ii] = true;
        }
        char[] operatorChars = new char[]{'<', '>', '=', '+', '-', '*', '&', '^', '%', '~', '/', '?', '.', ';', '!', '|', '@', '`', '#'};
        OPERATOR_CHAR_FLAGS = new boolean[256];
        for (int ii2 = 0; ii2 < operatorChars.length; ++ii2) {
            char operator = operatorChars[ii2];
            _Private_IonTextAppender.OPERATOR_CHAR_FLAGS[operator] = true;
        }
        ZERO_PADDING = new String[]{"", "0", "00", "000", "0000", "00000", "000000", "0000000"};
        STRING_ESCAPE_CODES = new String[256];
        _Private_IonTextAppender.STRING_ESCAPE_CODES[0] = "\\0";
        _Private_IonTextAppender.STRING_ESCAPE_CODES[7] = "\\a";
        _Private_IonTextAppender.STRING_ESCAPE_CODES[8] = "\\b";
        _Private_IonTextAppender.STRING_ESCAPE_CODES[9] = "\\t";
        _Private_IonTextAppender.STRING_ESCAPE_CODES[10] = "\\n";
        _Private_IonTextAppender.STRING_ESCAPE_CODES[11] = "\\v";
        _Private_IonTextAppender.STRING_ESCAPE_CODES[12] = "\\f";
        _Private_IonTextAppender.STRING_ESCAPE_CODES[13] = "\\r";
        _Private_IonTextAppender.STRING_ESCAPE_CODES[92] = "\\\\";
        _Private_IonTextAppender.STRING_ESCAPE_CODES[34] = "\\\"";
        for (i = 1; i < 32; ++i) {
            if (STRING_ESCAPE_CODES[i] != null) continue;
            String s = Integer.toHexString(i);
            _Private_IonTextAppender.STRING_ESCAPE_CODES[i] = "\\x" + ZERO_PADDING[2 - s.length()] + s;
        }
        for (i = 127; i < 256; ++i) {
            String s = Integer.toHexString(i);
            _Private_IonTextAppender.STRING_ESCAPE_CODES[i] = "\\x" + s;
        }
        LONG_STRING_ESCAPE_CODES = new String[256];
        for (i = 0; i < 256; ++i) {
            _Private_IonTextAppender.LONG_STRING_ESCAPE_CODES[i] = STRING_ESCAPE_CODES[i];
        }
        _Private_IonTextAppender.LONG_STRING_ESCAPE_CODES[10] = null;
        _Private_IonTextAppender.LONG_STRING_ESCAPE_CODES[39] = "\\'";
        _Private_IonTextAppender.LONG_STRING_ESCAPE_CODES[34] = null;
        SYMBOL_ESCAPE_CODES = new String[256];
        for (i = 0; i < 256; ++i) {
            _Private_IonTextAppender.SYMBOL_ESCAPE_CODES[i] = STRING_ESCAPE_CODES[i];
        }
        _Private_IonTextAppender.SYMBOL_ESCAPE_CODES[39] = "\\'";
        _Private_IonTextAppender.SYMBOL_ESCAPE_CODES[34] = null;
        JSON_ESCAPE_CODES = new String[256];
        _Private_IonTextAppender.JSON_ESCAPE_CODES[8] = "\\b";
        _Private_IonTextAppender.JSON_ESCAPE_CODES[9] = "\\t";
        _Private_IonTextAppender.JSON_ESCAPE_CODES[10] = "\\n";
        _Private_IonTextAppender.JSON_ESCAPE_CODES[12] = "\\f";
        _Private_IonTextAppender.JSON_ESCAPE_CODES[13] = "\\r";
        _Private_IonTextAppender.JSON_ESCAPE_CODES[92] = "\\\\";
        _Private_IonTextAppender.JSON_ESCAPE_CODES[34] = "\\\"";
        for (i = 0; i < 32; ++i) {
            if (JSON_ESCAPE_CODES[i] != null) continue;
            String s = Integer.toHexString(i);
            _Private_IonTextAppender.JSON_ESCAPE_CODES[i] = HEX_4_PREFIX + ZERO_PADDING[4 - s.length()] + s;
        }
        for (i = 127; i < 256; ++i) {
            String s = Integer.toHexString(i);
            _Private_IonTextAppender.JSON_ESCAPE_CODES[i] = "\\u00" + s;
        }
    }
}

