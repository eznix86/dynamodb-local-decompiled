/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.partiql.lang.syntax;

import kotlin.Metadata;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b \b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0006\u0010\u0003\u001a\u00020\u0004j\u0002\b\u0005j\u0002\b\u0006j\u0002\b\u0007j\u0002\b\bj\u0002\b\tj\u0002\b\nj\u0002\b\u000bj\u0002\b\fj\u0002\b\rj\u0002\b\u000ej\u0002\b\u000fj\u0002\b\u0010j\u0002\b\u0011j\u0002\b\u0012j\u0002\b\u0013j\u0002\b\u0014j\u0002\b\u0015j\u0002\b\u0016j\u0002\b\u0017j\u0002\b\u0018j\u0002\b\u0019j\u0002\b\u001aj\u0002\b\u001bj\u0002\b\u001cj\u0002\b\u001dj\u0002\b\u001ej\u0002\b\u001fj\u0002\b j\u0002\b!j\u0002\b\"j\u0002\b#\u00a8\u0006$"}, d2={"Lorg/partiql/lang/syntax/TokenType;", "", "(Ljava/lang/String;I)V", "isIdentifier", "", "LEFT_PAREN", "RIGHT_PAREN", "LEFT_BRACKET", "RIGHT_BRACKET", "LEFT_CURLY", "RIGHT_CURLY", "LEFT_DOUBLE_ANGLE_BRACKET", "RIGHT_DOUBLE_ANGLE_BRACKET", "IDENTIFIER", "QUOTED_IDENTIFIER", "OPERATOR", "KEYWORD", "LITERAL", "ION_LITERAL", "DOT", "STAR", "COMMA", "COLON", "SEMICOLON", "QUESTION_MARK", "AS", "FOR", "AT", "BY", "MISSING", "NULL", "ASC", "DESC", "TRIM_SPECIFICATION", "DATE_PART", "EOF", "lang"})
public final class TokenType
extends Enum<TokenType> {
    public static final /* enum */ TokenType LEFT_PAREN;
    public static final /* enum */ TokenType RIGHT_PAREN;
    public static final /* enum */ TokenType LEFT_BRACKET;
    public static final /* enum */ TokenType RIGHT_BRACKET;
    public static final /* enum */ TokenType LEFT_CURLY;
    public static final /* enum */ TokenType RIGHT_CURLY;
    public static final /* enum */ TokenType LEFT_DOUBLE_ANGLE_BRACKET;
    public static final /* enum */ TokenType RIGHT_DOUBLE_ANGLE_BRACKET;
    public static final /* enum */ TokenType IDENTIFIER;
    public static final /* enum */ TokenType QUOTED_IDENTIFIER;
    public static final /* enum */ TokenType OPERATOR;
    public static final /* enum */ TokenType KEYWORD;
    public static final /* enum */ TokenType LITERAL;
    public static final /* enum */ TokenType ION_LITERAL;
    public static final /* enum */ TokenType DOT;
    public static final /* enum */ TokenType STAR;
    public static final /* enum */ TokenType COMMA;
    public static final /* enum */ TokenType COLON;
    public static final /* enum */ TokenType SEMICOLON;
    public static final /* enum */ TokenType QUESTION_MARK;
    public static final /* enum */ TokenType AS;
    public static final /* enum */ TokenType FOR;
    public static final /* enum */ TokenType AT;
    public static final /* enum */ TokenType BY;
    public static final /* enum */ TokenType MISSING;
    public static final /* enum */ TokenType NULL;
    public static final /* enum */ TokenType ASC;
    public static final /* enum */ TokenType DESC;
    public static final /* enum */ TokenType TRIM_SPECIFICATION;
    public static final /* enum */ TokenType DATE_PART;
    public static final /* enum */ TokenType EOF;
    private static final /* synthetic */ TokenType[] $VALUES;

    static {
        TokenType[] tokenTypeArray = new TokenType[31];
        TokenType[] tokenTypeArray2 = tokenTypeArray;
        tokenTypeArray[0] = LEFT_PAREN = new TokenType();
        tokenTypeArray[1] = RIGHT_PAREN = new TokenType();
        tokenTypeArray[2] = LEFT_BRACKET = new TokenType();
        tokenTypeArray[3] = RIGHT_BRACKET = new TokenType();
        tokenTypeArray[4] = LEFT_CURLY = new TokenType();
        tokenTypeArray[5] = RIGHT_CURLY = new TokenType();
        tokenTypeArray[6] = LEFT_DOUBLE_ANGLE_BRACKET = new TokenType();
        tokenTypeArray[7] = RIGHT_DOUBLE_ANGLE_BRACKET = new TokenType();
        tokenTypeArray[8] = IDENTIFIER = new TokenType();
        tokenTypeArray[9] = QUOTED_IDENTIFIER = new TokenType();
        tokenTypeArray[10] = OPERATOR = new TokenType();
        tokenTypeArray[11] = KEYWORD = new TokenType();
        tokenTypeArray[12] = LITERAL = new TokenType();
        tokenTypeArray[13] = ION_LITERAL = new TokenType();
        tokenTypeArray[14] = DOT = new TokenType();
        tokenTypeArray[15] = STAR = new TokenType();
        tokenTypeArray[16] = COMMA = new TokenType();
        tokenTypeArray[17] = COLON = new TokenType();
        tokenTypeArray[18] = SEMICOLON = new TokenType();
        tokenTypeArray[19] = QUESTION_MARK = new TokenType();
        tokenTypeArray[20] = AS = new TokenType();
        tokenTypeArray[21] = FOR = new TokenType();
        tokenTypeArray[22] = AT = new TokenType();
        tokenTypeArray[23] = BY = new TokenType();
        tokenTypeArray[24] = MISSING = new TokenType();
        tokenTypeArray[25] = NULL = new TokenType();
        tokenTypeArray[26] = ASC = new TokenType();
        tokenTypeArray[27] = DESC = new TokenType();
        tokenTypeArray[28] = TRIM_SPECIFICATION = new TokenType();
        tokenTypeArray[29] = DATE_PART = new TokenType();
        tokenTypeArray[30] = EOF = new TokenType();
        $VALUES = tokenTypeArray;
    }

    public final boolean isIdentifier() {
        return this == IDENTIFIER || this == QUOTED_IDENTIFIER;
    }

    public static TokenType[] values() {
        return (TokenType[])$VALUES.clone();
    }

    public static TokenType valueOf(String string) {
        return Enum.valueOf(TokenType.class, string);
    }
}

