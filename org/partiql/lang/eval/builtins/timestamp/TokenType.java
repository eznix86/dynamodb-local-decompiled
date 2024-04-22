/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.partiql.lang.eval.builtins.timestamp;

import kotlin.Metadata;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0004\b\u0080\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004\u00a8\u0006\u0005"}, d2={"Lorg/partiql/lang/eval/builtins/timestamp/TokenType;", "", "(Ljava/lang/String;I)V", "PATTERN", "TEXT", "lang"})
public final class TokenType
extends Enum<TokenType> {
    public static final /* enum */ TokenType PATTERN;
    public static final /* enum */ TokenType TEXT;
    private static final /* synthetic */ TokenType[] $VALUES;

    static {
        TokenType[] tokenTypeArray = new TokenType[2];
        TokenType[] tokenTypeArray2 = tokenTypeArray;
        tokenTypeArray[0] = PATTERN = new TokenType();
        tokenTypeArray[1] = TEXT = new TokenType();
        $VALUES = tokenTypeArray;
    }

    public static TokenType[] values() {
        return (TokenType[])$VALUES.clone();
    }

    public static TokenType valueOf(String string) {
        return Enum.valueOf(TokenType.class, string);
    }
}

