/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.partiql.lang.syntax;

import kotlin.Metadata;
import org.partiql.lang.syntax.TokenType;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=3)
public final class Token$WhenMappings {
    public static final /* synthetic */ int[] $EnumSwitchMapping$0;
    public static final /* synthetic */ int[] $EnumSwitchMapping$1;
    public static final /* synthetic */ int[] $EnumSwitchMapping$2;
    public static final /* synthetic */ int[] $EnumSwitchMapping$3;

    static {
        $EnumSwitchMapping$0 = new int[TokenType.values().length];
        Token$WhenMappings.$EnumSwitchMapping$0[TokenType.OPERATOR.ordinal()] = 1;
        Token$WhenMappings.$EnumSwitchMapping$0[TokenType.KEYWORD.ordinal()] = 2;
        Token$WhenMappings.$EnumSwitchMapping$0[TokenType.AS.ordinal()] = 3;
        Token$WhenMappings.$EnumSwitchMapping$0[TokenType.AT.ordinal()] = 4;
        Token$WhenMappings.$EnumSwitchMapping$0[TokenType.MISSING.ordinal()] = 5;
        Token$WhenMappings.$EnumSwitchMapping$0[TokenType.NULL.ordinal()] = 6;
        $EnumSwitchMapping$1 = new int[TokenType.values().length];
        Token$WhenMappings.$EnumSwitchMapping$1[TokenType.OPERATOR.ordinal()] = 1;
        $EnumSwitchMapping$2 = new int[TokenType.values().length];
        Token$WhenMappings.$EnumSwitchMapping$2[TokenType.OPERATOR.ordinal()] = 1;
        Token$WhenMappings.$EnumSwitchMapping$2[TokenType.KEYWORD.ordinal()] = 2;
        Token$WhenMappings.$EnumSwitchMapping$2[TokenType.STAR.ordinal()] = 3;
        $EnumSwitchMapping$3 = new int[TokenType.values().length];
        Token$WhenMappings.$EnumSwitchMapping$3[TokenType.OPERATOR.ordinal()] = 1;
        Token$WhenMappings.$EnumSwitchMapping$3[TokenType.KEYWORD.ordinal()] = 2;
    }
}

