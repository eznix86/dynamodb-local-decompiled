/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazon.ion.impl;

import com.amazon.ion.IonException;
import com.amazon.ion.IonType;
import com.amazon.ion.impl._Private_ScalarConversions;

final class IonTokenConstsX {
    public static final int TOKEN_ERROR = -1;
    public static final int TOKEN_EOF = 0;
    public static final int TOKEN_UNKNOWN_NUMERIC = 1;
    public static final int TOKEN_INT = 2;
    public static final int TOKEN_HEX = 3;
    public static final int TOKEN_DECIMAL = 4;
    public static final int TOKEN_FLOAT = 5;
    public static final int TOKEN_FLOAT_INF = 6;
    public static final int TOKEN_FLOAT_MINUS_INF = 7;
    public static final int TOKEN_TIMESTAMP = 8;
    public static final int TOKEN_SYMBOL_IDENTIFIER = 9;
    public static final int TOKEN_SYMBOL_QUOTED = 10;
    public static final int TOKEN_SYMBOL_OPERATOR = 11;
    public static final int TOKEN_STRING_DOUBLE_QUOTE = 12;
    public static final int TOKEN_STRING_TRIPLE_QUOTE = 13;
    public static final int TOKEN_DOT = 14;
    public static final int TOKEN_COMMA = 15;
    public static final int TOKEN_COLON = 16;
    public static final int TOKEN_DOUBLE_COLON = 17;
    public static final int TOKEN_OPEN_PAREN = 18;
    public static final int TOKEN_CLOSE_PAREN = 19;
    public static final int TOKEN_OPEN_BRACE = 20;
    public static final int TOKEN_CLOSE_BRACE = 21;
    public static final int TOKEN_OPEN_SQUARE = 22;
    public static final int TOKEN_CLOSE_SQUARE = 23;
    public static final int TOKEN_OPEN_DOUBLE_BRACE = 24;
    public static final int TOKEN_CLOSE_DOUBLE_BRACE = 25;
    public static final int TOKEN_BINARY = 26;
    public static final int TOKEN_MAX = 26;
    public static final int TOKEN_count = 27;
    public static final int KEYWORD_unrecognized = -1;
    public static final int KEYWORD_none = 0;
    public static final int KEYWORD_TRUE = 1;
    public static final int KEYWORD_FALSE = 2;
    public static final int KEYWORD_NULL = 3;
    public static final int KEYWORD_BOOL = 4;
    public static final int KEYWORD_INT = 5;
    public static final int KEYWORD_FLOAT = 6;
    public static final int KEYWORD_DECIMAL = 7;
    public static final int KEYWORD_TIMESTAMP = 8;
    public static final int KEYWORD_SYMBOL = 9;
    public static final int KEYWORD_STRING = 10;
    public static final int KEYWORD_BLOB = 11;
    public static final int KEYWORD_CLOB = 12;
    public static final int KEYWORD_LIST = 13;
    public static final int KEYWORD_SEXP = 14;
    public static final int KEYWORD_STRUCT = 15;
    public static final int KEYWORD_NAN = 16;
    public static final int KEYWORD_sid = 17;
    public static final char[] BLOB_TERMINATOR = new char[]{'}', '}'};
    public static final char[] CLOB_DOUBLE_QUOTED_TERMINATOR = new char[]{'\'', '\'', '\''};
    public static final char[] CLOB_TRIPLE_QUOTED_TERMINATOR = new char[]{'\"'};
    public static final boolean[] isBase64Character = IonTokenConstsX.makeBase64Array();
    public static final int base64FillerCharacter = 61;
    public static final int[] hexValue = IonTokenConstsX.makeHexValueArray();
    public static final boolean[] isHexDigit = IonTokenConstsX.makeHexDigitTestArray(hexValue);
    public static final int CLOB_CHARACTER_LIMIT = 255;
    public static final int ESCAPE_LITTLE_U_MINIMUM = 256;
    public static final int ESCAPE_BIG_U_MINIMUM = 65536;
    public static final int ESCAPE_HEX = -16;
    public static final int ESCAPE_BIG_U = -15;
    public static final int ESCAPE_LITTLE_U = -14;
    public static final int ESCAPE_REMOVES_NEWLINE2 = -13;
    public static final int ESCAPE_REMOVES_NEWLINE = -12;
    public static final int ESCAPE_NOT_DEFINED = -11;
    private static final int[] escapeCharactersValues = IonTokenConstsX.makeEscapeCharacterValuesArray();
    private static final String[] escapeCharacterImage = IonTokenConstsX.makeEscapeCharacterImageArray();
    private static final boolean[] invalidTerminatingCharsForInf = IonTokenConstsX.makeInvalidTerminatingCharsForInfArray();
    private static final boolean[] isValidExtendedSymbolCharacter = IonTokenConstsX.makeIsValidExtendedSymbolCharacterArray();
    private static final boolean[] isValidSymbolCharacter = IonTokenConstsX.makeIsValidSymbolCharacterArray();
    private static final boolean[] isValidStartSymbolCharacter = IonTokenConstsX.makeIsValidStartSymbolCharacterArray();
    static final int TN_MAX_NAME_LENGTH = "TIMESTAMP".length() + 1;
    static final int TN_LETTER_MAX_IDX = 19;
    static final int KW_BIT_BLOB = 1;
    static final int KW_BIT_BOOL = 2;
    static final int KW_BIT_CLOB = 4;
    static final int KW_BIT_DECIMAL = 8;
    static final int KW_BIT_FLOAT = 16;
    static final int KW_BIT_INT = 32;
    static final int KW_BIT_LIST = 48;
    static final int KW_BIT_NULL = 128;
    static final int KW_BIT_SEXP = 256;
    static final int KW_BIT_STRING = 512;
    static final int KW_BIT_STRUCT = 1024;
    static final int KW_BIT_SYMBOL = 2048;
    static final int KW_BIT_TIMESTAMP = 4096;
    static final int KW_ALL_BITS = 8191;
    static final int[] typeNameBits = new int[]{1, 2, 4, 8, 16, 32, 48, 128, 256, 512, 1024, 2048, 4096};
    static final String[] typeNameNames = new String[]{"blob", "bool", "clob", "decimal", "float", "int", "list", "null", "sexp", "string", "struct", "symbol", "timestamp"};
    static final int[] typeNameKeyWordIds = new int[]{11, 4, 12, 7, 6, 5, 13, 3, 14, 10, 15, 9, 8};
    static final int[] TypeNameBitIndex = IonTokenConstsX.makeTypeNameBitIndex();

    IonTokenConstsX() {
    }

    public static final String getTokenName(int t) {
        switch (t) {
            case -1: {
                return "TOKEN_ERROR";
            }
            case 0: {
                return "TOKEN_EOF";
            }
            case 1: {
                return "TOKEN_UNKNOWN_NUMERIC";
            }
            case 2: {
                return "TOKEN_INT";
            }
            case 3: {
                return "TOKEN_HEX";
            }
            case 4: {
                return "TOKEN_DECIMAL";
            }
            case 5: {
                return "TOKEN_FLOAT";
            }
            case 6: {
                return "TOKEN_FLOAT_INF";
            }
            case 7: {
                return "TOKEN_FLOAT_MINUS_INF";
            }
            case 8: {
                return "TOKEN_TIMESTAMP";
            }
            case 9: {
                return "TOKEN_SYMBOL_IDENTIFIER";
            }
            case 10: {
                return "TOKEN_SYMBOL_QUOTED";
            }
            case 11: {
                return "TOKEN_SYMBOL_OPERATOR";
            }
            case 12: {
                return "TOKEN_STRING_DOUBLE_QUOTE";
            }
            case 13: {
                return "TOKEN_STRING_TRIPLE_QUOTE";
            }
            case 14: {
                return "TOKEN_DOT";
            }
            case 15: {
                return "TOKEN_COMMA";
            }
            case 16: {
                return "TOKEN_COLON";
            }
            case 17: {
                return "TOKEN_DOUBLE_COLON";
            }
            case 18: {
                return "TOKEN_OPEN_PAREN";
            }
            case 19: {
                return "TOKEN_CLOSE_PAREN";
            }
            case 20: {
                return "TOKEN_OPEN_BRACE";
            }
            case 21: {
                return "TOKEN_CLOSE_BRACE";
            }
            case 22: {
                return "TOKEN_OPEN_SQUARE";
            }
            case 23: {
                return "TOKEN_CLOSE_SQUARE";
            }
            case 24: {
                return "TOKEN_OPEN_DOUBLE_BRACE";
            }
            case 25: {
                return "TOKEN_CLOSE_DOUBLE_BRACE";
            }
        }
        return "<invalid token " + t + ">";
    }

    public static final String describeToken(int t) {
        switch (t) {
            case 18: {
                return "(";
            }
            case 19: {
                return ")";
            }
            case 20: {
                return "{";
            }
            case 21: {
                return "}";
            }
            case 22: {
                return "[";
            }
            case 23: {
                return "]";
            }
            case 24: {
                return "{{";
            }
            case 25: {
                return "}}";
            }
        }
        return IonTokenConstsX.getTokenName(t);
    }

    public static final IonType ion_type_of_scalar(int token) {
        switch (token) {
            case 2: {
                return IonType.INT;
            }
            case 26: {
                return IonType.INT;
            }
            case 3: {
                return IonType.INT;
            }
            case 4: {
                return IonType.DECIMAL;
            }
            case 5: {
                return IonType.FLOAT;
            }
            case 8: {
                return IonType.TIMESTAMP;
            }
            case 9: {
                return IonType.SYMBOL;
            }
            case 10: {
                return IonType.SYMBOL;
            }
            case 11: {
                return IonType.SYMBOL;
            }
            case 12: {
                return IonType.STRING;
            }
            case 13: {
                return IonType.STRING;
            }
        }
        return null;
    }

    public static final boolean is8bitValue(int v) {
        return (v & 0xFFFFFF00) == 0;
    }

    public static final boolean is7bitValue(int v) {
        return (v & 0xFFFFFF80) == 0;
    }

    public static final boolean isWhitespace(int c) {
        return c == 32 || c == 9 || c == 10 || c == 13;
    }

    private static boolean[] makeBase64Array() {
        int ii;
        boolean[] base64 = new boolean[256];
        for (ii = 48; ii <= 57; ++ii) {
            base64[ii] = true;
        }
        for (ii = 97; ii <= 122; ++ii) {
            base64[ii] = true;
        }
        for (ii = 65; ii <= 90; ++ii) {
            base64[ii] = true;
        }
        base64[43] = true;
        base64[47] = true;
        return base64;
    }

    private static final int[] makeHexValueArray() {
        int ii;
        int[] hex = new int[256];
        for (ii = 0; ii < 256; ++ii) {
            hex[ii] = -1;
        }
        for (ii = 48; ii <= 57; ++ii) {
            hex[ii] = ii - 48;
        }
        for (ii = 97; ii <= 102; ++ii) {
            hex[ii] = ii - 97 + 10;
        }
        for (ii = 65; ii <= 70; ++ii) {
            hex[ii] = ii - 65 + 10;
        }
        return hex;
    }

    private static final boolean[] makeHexDigitTestArray(int[] hex_characters) {
        boolean[] is_hex = new boolean[hex_characters.length];
        for (int ii = 0; ii < hex_characters.length; ++ii) {
            is_hex[ii] = hex_characters[ii] >= 0;
        }
        return is_hex;
    }

    public static final boolean isBinaryDigit(int c) {
        return c == 48 || c == 49;
    }

    public static final boolean isHexDigit(int c) {
        return isHexDigit[c & 0xFF] && IonTokenConstsX.is8bitValue(c);
    }

    public static final int hexDigitValue(int c) {
        if (!IonTokenConstsX.isHexDigit(c)) {
            IllegalArgumentException e = new IllegalArgumentException("character '" + (char)c + "' is not a hex digit");
            throw new IonException(e);
        }
        return hexValue[c];
    }

    public static final boolean isDigit(int c) {
        return c >= 48 && c <= 57;
    }

    public static final int decimalDigitValue(int c) {
        if (!IonTokenConstsX.isDigit(c)) {
            throw new IllegalArgumentException("character '" + (char)c + "' is not a hex digit");
        }
        return c - 48;
    }

    private static final int[] makeEscapeCharacterValuesArray() {
        int[] values2 = new int[256];
        for (int ii = 0; ii < 256; ++ii) {
            values2[ii] = -11;
        }
        values2[48] = 0;
        values2[97] = 7;
        values2[98] = 8;
        values2[116] = 9;
        values2[110] = 10;
        values2[102] = 12;
        values2[114] = 13;
        values2[118] = 11;
        values2[34] = 34;
        values2[39] = 39;
        values2[63] = 63;
        values2[92] = 92;
        values2[47] = 47;
        values2[10] = -12;
        values2[13] = -13;
        values2[120] = -16;
        values2[117] = -14;
        values2[85] = -15;
        return values2;
    }

    public static final String[] makeEscapeCharacterImageArray() {
        String[] values2 = new String[256];
        for (int ii = 0; ii < 256; ++ii) {
            values2[ii] = null;
        }
        values2[48] = "\\0";
        values2[97] = "\\a";
        values2[98] = "\\b";
        values2[116] = "\\t";
        values2[110] = "\\n";
        values2[102] = "\\f";
        values2[114] = "\\r";
        values2[118] = "\\v";
        values2[34] = "\\\"";
        values2[39] = "\\'";
        values2[63] = "\\?";
        values2[92] = "\\\\";
        values2[47] = "\\/";
        return values2;
    }

    public static final String getEscapeCharacterImage(int c) {
        if (c < 0 || c > 255) {
            throw new IllegalArgumentException("character is outside escapable range (0-255 inclusive)");
        }
        return escapeCharacterImage[c];
    }

    public static final boolean isValidEscapeStart(int c) {
        return escapeCharactersValues[c & 0xFF] != -11 && IonTokenConstsX.is8bitValue(c);
    }

    public static final int escapeReplacementCharacter(int c) {
        if (!IonTokenConstsX.isValidEscapeStart(c)) {
            throw new IllegalArgumentException("not a valid escape sequence character: " + c);
        }
        return escapeCharactersValues[c];
    }

    public static final boolean needsIonEscape(EscapeType escapeType, int c) {
        switch (escapeType) {
            case ESCAPE_DESTINATION_NONE: {
                return false;
            }
            case ESCAPE_DESTINATION_STRING: {
                return IonTokenConstsX.needsStringEscape(c);
            }
            case ESCAPE_DESTINATION_SYMBOL: {
                return IonTokenConstsX.needsSymbolEscape(c);
            }
            case ESCAPE_DESTINATION_CLOB: {
                return IonTokenConstsX.needsClobEscape(c);
            }
        }
        throw new IllegalArgumentException("escapeType " + (Object)((Object)escapeType) + " is unrecognized");
    }

    public static final boolean needsSymbolEscape(int c) {
        return c < 32 || c == 39 || c == 92;
    }

    public static final boolean needsStringEscape(int c) {
        return c < 32 || c == 34 || c == 92;
    }

    public static final boolean needsClobEscape(int c) {
        return c < 32 || c == 34 || c == 92 || c > 127;
    }

    public static String escapeSequence(int c) {
        if (c >= 0 || c <= 0x10FFFF) {
            if (c < 128) {
                return escapeCharacterImage[c];
            }
            if (c < 65535) {
                String short_hex = Integer.toHexString(c);
                int short_len = short_hex.length();
                if (short_len < 4) {
                    short_hex = "0000".substring(short_len);
                }
                return "\\u" + short_hex;
            }
            if (c < 65535) {
                String long_hex = Integer.toHexString(c);
                int long_len = long_hex.length();
                if (long_len < 4) {
                    long_hex = "00000000".substring(long_len);
                }
                return "\\U" + long_hex;
            }
        }
        throw new IllegalArgumentException("the value " + c + " isn't a valid character");
    }

    private static final boolean[] makeInvalidTerminatingCharsForInfArray() {
        int ii;
        boolean[] values2 = new boolean[256];
        for (ii = 97; ii <= 122; ++ii) {
            values2[ii] = true;
        }
        for (ii = 65; ii <= 90; ++ii) {
            values2[ii] = true;
        }
        for (ii = 48; ii <= 57; ++ii) {
            values2[ii] = true;
        }
        values2[36] = true;
        values2[95] = true;
        return values2;
    }

    public static final boolean isValidTerminatingCharForInf(int c) {
        return !IonTokenConstsX.is8bitValue(c) || !invalidTerminatingCharsForInf[c & 0xFF];
    }

    private static final boolean[] makeIsValidExtendedSymbolCharacterArray() {
        boolean[] values2 = new boolean[256];
        values2[33] = true;
        values2[35] = true;
        values2[37] = true;
        values2[38] = true;
        values2[42] = true;
        values2[43] = true;
        values2[45] = true;
        values2[46] = true;
        values2[47] = true;
        values2[59] = true;
        values2[60] = true;
        values2[61] = true;
        values2[62] = true;
        values2[63] = true;
        values2[64] = true;
        values2[94] = true;
        values2[96] = true;
        values2[124] = true;
        values2[126] = true;
        return values2;
    }

    public static final boolean isValidExtendedSymbolCharacter(int c) {
        return isValidExtendedSymbolCharacter[c & 0xFF] && IonTokenConstsX.is8bitValue(c);
    }

    private static final boolean[] makeIsValidSymbolCharacterArray() {
        int ii;
        boolean[] values2 = new boolean[256];
        for (ii = 97; ii <= 122; ++ii) {
            values2[ii] = true;
        }
        for (ii = 65; ii <= 90; ++ii) {
            values2[ii] = true;
        }
        for (ii = 48; ii <= 57; ++ii) {
            values2[ii] = true;
        }
        values2[36] = true;
        values2[95] = true;
        return values2;
    }

    public static final boolean isValidSymbolCharacter(int c) {
        return isValidSymbolCharacter[c & 0xFF] && IonTokenConstsX.is8bitValue(c);
    }

    private static final boolean[] makeIsValidStartSymbolCharacterArray() {
        int ii;
        boolean[] values2 = new boolean[256];
        for (ii = 97; ii <= 122; ++ii) {
            values2[ii] = true;
        }
        for (ii = 65; ii <= 90; ++ii) {
            values2[ii] = true;
        }
        values2[36] = true;
        values2[95] = true;
        return values2;
    }

    public static final boolean isValidStartSymbolCharacter(int c) {
        return isValidStartSymbolCharacter[c & 0xFF] && IonTokenConstsX.is8bitValue(c);
    }

    public static int decodeSid(CharSequence sidToken) {
        assert (sidToken.charAt(0) == '$');
        int length = sidToken.length();
        assert (length > 1);
        String digits = sidToken.subSequence(1, length).toString();
        try {
            return Integer.parseInt(digits);
        } catch (Exception e) {
            throw new IonException(String.format("Unable to parse SID %s", digits), e);
        }
    }

    public static int keyword(CharSequence word, int start_word, int end_word) {
        char c = word.charAt(start_word);
        int len = end_word - start_word;
        switch (c) {
            case '$': {
                if (len > 1) {
                    for (int i = start_word + 1; i < end_word; ++i) {
                        if (IonTokenConstsX.isDigit(word.charAt(i))) continue;
                        return -1;
                    }
                    return 17;
                }
                return -1;
            }
            case 'b': {
                if (len == 4) {
                    if (word.charAt(start_word + 1) == 'o' && word.charAt(start_word + 2) == 'o' && word.charAt(start_word + 3) == 'l') {
                        return 4;
                    }
                    if (word.charAt(start_word + 1) == 'l' && word.charAt(start_word + 2) == 'o' && word.charAt(start_word + 3) == 'b') {
                        return 11;
                    }
                }
                return -1;
            }
            case 'c': {
                if (len == 4 && word.charAt(start_word + 1) == 'l' && word.charAt(start_word + 2) == 'o' && word.charAt(start_word + 3) == 'b') {
                    return 12;
                }
                return -1;
            }
            case 'd': {
                if (len == 7 && word.charAt(start_word + 1) == 'e' && word.charAt(start_word + 2) == 'c' && word.charAt(start_word + 3) == 'i' && word.charAt(start_word + 4) == 'm' && word.charAt(start_word + 5) == 'a' && word.charAt(start_word + 6) == 'l') {
                    return 7;
                }
                return -1;
            }
            case 'f': {
                if (len == 5) {
                    if (word.charAt(start_word + 1) == 'a' && word.charAt(start_word + 2) == 'l' && word.charAt(start_word + 3) == 's' && word.charAt(start_word + 4) == 'e') {
                        return 2;
                    }
                    if (word.charAt(start_word + 1) == 'l' && word.charAt(start_word + 2) == 'o' && word.charAt(start_word + 3) == 'a' && word.charAt(start_word + 4) == 't') {
                        return 6;
                    }
                }
                return -1;
            }
            case 'i': {
                if (len == 3 && word.charAt(start_word + 1) == 'n' && word.charAt(start_word + 2) == 't') {
                    return 5;
                }
                return -1;
            }
            case 'l': {
                if (len == 4 && word.charAt(start_word + 1) == 'i' && word.charAt(start_word + 2) == 's' && word.charAt(start_word + 3) == 't') {
                    return 13;
                }
                return -1;
            }
            case 'n': {
                if (len == 4) {
                    if (word.charAt(start_word + 1) == 'u' && word.charAt(start_word + 2) == 'l' && word.charAt(start_word + 3) == 'l') {
                        return 3;
                    }
                } else if (len == 3 && word.charAt(start_word + 1) == 'a' && word.charAt(start_word + 2) == 'n') {
                    return 16;
                }
                return -1;
            }
            case 's': {
                if (len == 4) {
                    if (word.charAt(start_word + 1) == 'e' && word.charAt(start_word + 2) == 'x' && word.charAt(start_word + 3) == 'p') {
                        return 14;
                    }
                } else if (len == 6) {
                    if (word.charAt(start_word + 1) == 't' && word.charAt(start_word + 2) == 'r') {
                        if (word.charAt(start_word + 3) == 'i' && word.charAt(start_word + 4) == 'n' && word.charAt(start_word + 5) == 'g') {
                            return 10;
                        }
                        if (word.charAt(start_word + 3) == 'u' && word.charAt(start_word + 4) == 'c' && word.charAt(start_word + 5) == 't') {
                            return 15;
                        }
                        return -1;
                    }
                    if (word.charAt(start_word + 1) == 'y' && word.charAt(start_word + 2) == 'm' && word.charAt(start_word + 3) == 'b' && word.charAt(start_word + 4) == 'o' && word.charAt(start_word + 5) == 'l') {
                        return 9;
                    }
                }
                return -1;
            }
            case 't': {
                if (len == 4) {
                    if (word.charAt(start_word + 1) == 'r' && word.charAt(start_word + 2) == 'u' && word.charAt(start_word + 3) == 'e') {
                        return 1;
                    }
                } else if (len == 9 && word.charAt(start_word + 1) == 'i' && word.charAt(start_word + 2) == 'm' && word.charAt(start_word + 3) == 'e' && word.charAt(start_word + 4) == 's' && word.charAt(start_word + 5) == 't' && word.charAt(start_word + 6) == 'a' && word.charAt(start_word + 7) == 'm' && word.charAt(start_word + 8) == 'p') {
                    return 8;
                }
                return -1;
            }
        }
        return -1;
    }

    static final int[] makeTypeNameBitIndex() {
        int[] bits = new int[TN_MAX_NAME_LENGTH * 19];
        for (int tt = 0; tt < typeNameNames.length; ++tt) {
            String typename = typeNameNames[tt];
            int typename_bit = typeNameBits[tt];
            for (int ii = 0; ii < typename.length(); ++ii) {
                char c = typename.charAt(ii);
                int l = IonTokenConstsX.typeNameLetterIdx(c);
                assert (l > 0);
                IonTokenConstsX.typename_set_bit(bits, ii, l, typename_bit);
            }
        }
        return bits;
    }

    private static final void typename_set_bit(int[] bits, int ii, int l, int typename_bit) {
        int idx;
        int n = idx = ii * 19 + l - 1;
        bits[n] = bits[n] | typename_bit;
    }

    public static final int typeNameLetterIdx(int c) {
        switch (c) {
            case 97: {
                return 1;
            }
            case 98: {
                return 2;
            }
            case 99: {
                return 3;
            }
            case 100: {
                return 4;
            }
            case 101: {
                return 5;
            }
            case 102: {
                return 6;
            }
            case 103: {
                return 7;
            }
            case 105: {
                return 8;
            }
            case 108: {
                return 9;
            }
            case 109: {
                return 10;
            }
            case 110: {
                return 11;
            }
            case 111: {
                return 12;
            }
            case 112: {
                return 13;
            }
            case 114: {
                return 14;
            }
            case 115: {
                return 15;
            }
            case 116: {
                return 16;
            }
            case 117: {
                return 17;
            }
            case 120: {
                return 18;
            }
            case 121: {
                return 19;
            }
        }
        return -1;
    }

    public static final int typeNamePossibilityMask(int pos, int letter_idx) {
        int idx = pos * 19 + letter_idx - 1;
        int mask = TypeNameBitIndex[idx];
        return mask;
    }

    public static final int typeNameKeyWordFromMask(int possible_names, int length) {
        int kw = -1;
        if (possible_names != 8191) {
            for (int ii = 0; ii < typeNameBits.length; ++ii) {
                int tb = typeNameBits[ii];
                if (tb != possible_names) continue;
                if (typeNameNames[ii].length() != length) break;
                kw = typeNameKeyWordIds[ii];
                break;
            }
        }
        return kw;
    }

    public static final String getNullImage(IonType type) {
        String nullimage = null;
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
        return nullimage;
    }

    public static final IonType getNullType(CharSequence s) {
        char c;
        IonType type = null;
        int ii = 0;
        boolean stop = false;
        block26: while (!stop && ii < s.length()) {
            c = s.charAt(ii++);
            switch (c) {
                case '\t': 
                case '\n': 
                case '\r': 
                case ' ': {
                    continue block26;
                }
                case 'n': {
                    stop = true;
                    continue block26;
                }
            }
            IonTokenConstsX.invalid_null_image(s);
        }
        if (ii >= s.length() || s.charAt(ii++) != 'u') {
            IonTokenConstsX.invalid_null_image(s);
        }
        if (ii >= s.length() || s.charAt(ii++) != 'l') {
            IonTokenConstsX.invalid_null_image(s);
        }
        if (ii >= s.length() || s.charAt(ii++) != 'l') {
            IonTokenConstsX.invalid_null_image(s);
        }
        boolean dot = false;
        block27: while (!dot && ii < s.length()) {
            c = s.charAt(ii++);
            switch (c) {
                case '\t': 
                case '\n': 
                case '\r': 
                case ' ': {
                    continue block27;
                }
                case '.': {
                    dot = true;
                    continue block27;
                }
            }
            IonTokenConstsX.invalid_null_image(s);
        }
        if (dot) {
            int kw = IonTokenConstsX.keyword(s, ii, s.length());
            switch (kw) {
                case 3: {
                    type = IonType.NULL;
                    break;
                }
                case 4: {
                    type = IonType.BOOL;
                    break;
                }
                case 5: {
                    type = IonType.INT;
                    break;
                }
                case 6: {
                    type = IonType.FLOAT;
                    break;
                }
                case 7: {
                    type = IonType.DECIMAL;
                    break;
                }
                case 8: {
                    type = IonType.TIMESTAMP;
                    break;
                }
                case 9: {
                    type = IonType.SYMBOL;
                    break;
                }
                case 10: {
                    type = IonType.STRING;
                    break;
                }
                case 12: {
                    type = IonType.CLOB;
                    break;
                }
                case 11: {
                    type = IonType.BLOB;
                    break;
                }
                case 15: {
                    type = IonType.STRUCT;
                    break;
                }
                case 13: {
                    type = IonType.LIST;
                    break;
                }
                case 14: {
                    type = IonType.SEXP;
                    break;
                }
                default: {
                    IonTokenConstsX.invalid_null_image(s);
                }
            }
        }
        block28: while (ii < s.length()) {
            c = s.charAt(ii++);
            switch (c) {
                case '\t': 
                case '\n': 
                case '\r': 
                case ' ': {
                    continue block28;
                }
            }
            IonTokenConstsX.invalid_null_image(s);
        }
        return type;
    }

    private static void invalid_null_image(CharSequence s) {
        throw new _Private_ScalarConversions.CantConvertException("invalid image " + s.toString());
    }

    public static enum EscapeType {
        ESCAPE_DESTINATION_NONE,
        ESCAPE_DESTINATION_STRING,
        ESCAPE_DESTINATION_SYMBOL,
        ESCAPE_DESTINATION_CLOB;

    }

    public static class CharacterSequence {
        public static final int CHAR_SEQ_EOF = -1;
        public static final int CHAR_SEQ_STRING_TERMINATOR = -2;
        public static final int CHAR_SEQ_STRING_NON_TERMINATOR = -3;
        public static final int CHAR_SEQ_NEWLINE_SEQUENCE_1 = -4;
        public static final int CHAR_SEQ_NEWLINE_SEQUENCE_2 = -5;
        public static final int CHAR_SEQ_NEWLINE_SEQUENCE_3 = -6;
        public static final int CHAR_SEQ_ESCAPED_NEWLINE_SEQUENCE_1 = -7;
        public static final int CHAR_SEQ_ESCAPED_NEWLINE_SEQUENCE_2 = -8;
        public static final int CHAR_SEQ_ESCAPED_NEWLINE_SEQUENCE_3 = -9;
    }
}

