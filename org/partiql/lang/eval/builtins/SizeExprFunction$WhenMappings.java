/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.partiql.lang.eval.builtins;

import kotlin.Metadata;
import org.partiql.lang.eval.ExprValueType;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=3)
public final class SizeExprFunction$WhenMappings {
    public static final /* synthetic */ int[] $EnumSwitchMapping$0;

    static {
        $EnumSwitchMapping$0 = new int[ExprValueType.values().length];
        SizeExprFunction$WhenMappings.$EnumSwitchMapping$0[ExprValueType.LIST.ordinal()] = 1;
        SizeExprFunction$WhenMappings.$EnumSwitchMapping$0[ExprValueType.BAG.ordinal()] = 2;
        SizeExprFunction$WhenMappings.$EnumSwitchMapping$0[ExprValueType.STRUCT.ordinal()] = 3;
        SizeExprFunction$WhenMappings.$EnumSwitchMapping$0[ExprValueType.SEXP.ordinal()] = 4;
    }
}

