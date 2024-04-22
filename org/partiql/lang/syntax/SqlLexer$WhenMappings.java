/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.partiql.lang.syntax;

import kotlin.Metadata;
import org.partiql.lang.syntax.SqlLexer;
import org.partiql.lang.syntax.TokenType;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=3)
public final class SqlLexer$WhenMappings {
    public static final /* synthetic */ int[] $EnumSwitchMapping$0;
    public static final /* synthetic */ int[] $EnumSwitchMapping$1;

    static {
        $EnumSwitchMapping$0 = new int[SqlLexer.LexType.values().length];
        SqlLexer$WhenMappings.$EnumSwitchMapping$0[SqlLexer.LexType.SQ_STRING.ordinal()] = 1;
        SqlLexer$WhenMappings.$EnumSwitchMapping$0[SqlLexer.LexType.INTEGER.ordinal()] = 2;
        SqlLexer$WhenMappings.$EnumSwitchMapping$0[SqlLexer.LexType.DECIMAL.ordinal()] = 3;
        $EnumSwitchMapping$1 = new int[TokenType.values().length];
        SqlLexer$WhenMappings.$EnumSwitchMapping$1[TokenType.OPERATOR.ordinal()] = 1;
        SqlLexer$WhenMappings.$EnumSwitchMapping$1[TokenType.IDENTIFIER.ordinal()] = 2;
        SqlLexer$WhenMappings.$EnumSwitchMapping$1[TokenType.LITERAL.ordinal()] = 3;
        SqlLexer$WhenMappings.$EnumSwitchMapping$1[TokenType.ION_LITERAL.ordinal()] = 4;
        SqlLexer$WhenMappings.$EnumSwitchMapping$1[TokenType.QUESTION_MARK.ordinal()] = 5;
    }
}

