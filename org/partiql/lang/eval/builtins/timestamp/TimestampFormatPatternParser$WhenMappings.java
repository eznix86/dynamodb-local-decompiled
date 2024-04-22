/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.partiql.lang.eval.builtins.timestamp;

import kotlin.Metadata;
import org.partiql.lang.eval.builtins.timestamp.TokenType;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=3)
public final class TimestampFormatPatternParser$WhenMappings {
    public static final /* synthetic */ int[] $EnumSwitchMapping$0;

    static {
        $EnumSwitchMapping$0 = new int[TokenType.values().length];
        TimestampFormatPatternParser$WhenMappings.$EnumSwitchMapping$0[TokenType.TEXT.ordinal()] = 1;
        TimestampFormatPatternParser$WhenMappings.$EnumSwitchMapping$0[TokenType.PATTERN.ordinal()] = 2;
    }
}

