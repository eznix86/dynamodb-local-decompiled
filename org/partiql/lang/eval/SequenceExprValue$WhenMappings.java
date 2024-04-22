/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.partiql.lang.eval;

import kotlin.Metadata;
import org.partiql.lang.eval.ExprValueType;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=3)
public final class SequenceExprValue$WhenMappings {
    public static final /* synthetic */ int[] $EnumSwitchMapping$0;
    public static final /* synthetic */ int[] $EnumSwitchMapping$1;

    static {
        $EnumSwitchMapping$0 = new int[ExprValueType.values().length];
        SequenceExprValue$WhenMappings.$EnumSwitchMapping$0[ExprValueType.BAG.ordinal()] = 1;
        SequenceExprValue$WhenMappings.$EnumSwitchMapping$0[ExprValueType.LIST.ordinal()] = 2;
        SequenceExprValue$WhenMappings.$EnumSwitchMapping$0[ExprValueType.SEXP.ordinal()] = 3;
        $EnumSwitchMapping$1 = new int[ExprValueType.values().length];
        SequenceExprValue$WhenMappings.$EnumSwitchMapping$1[ExprValueType.BAG.ordinal()] = 1;
    }
}

