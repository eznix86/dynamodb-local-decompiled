/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.partiql.lang.syntax;

import kotlin.Metadata;
import org.partiql.lang.syntax.TokenType;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=3)
public final class SqlParser$ParseNode$WhenMappings {
    public static final /* synthetic */ int[] $EnumSwitchMapping$0;

    static {
        $EnumSwitchMapping$0 = new int[TokenType.values().length];
        SqlParser$ParseNode$WhenMappings.$EnumSwitchMapping$0[TokenType.LITERAL.ordinal()] = 1;
        SqlParser$ParseNode$WhenMappings.$EnumSwitchMapping$0[TokenType.ION_LITERAL.ordinal()] = 2;
    }
}

