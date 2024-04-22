/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.partiql.lang.util;

import kotlin.Metadata;
import org.partiql.lang.eval.ExprValueType;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=3)
public final class ConfigurableExprValueFormatter$PrettyFormatter$WhenMappings {
    public static final /* synthetic */ int[] $EnumSwitchMapping$0;

    static {
        $EnumSwitchMapping$0 = new int[ExprValueType.values().length];
        ConfigurableExprValueFormatter$PrettyFormatter$WhenMappings.$EnumSwitchMapping$0[ExprValueType.MISSING.ordinal()] = 1;
        ConfigurableExprValueFormatter$PrettyFormatter$WhenMappings.$EnumSwitchMapping$0[ExprValueType.NULL.ordinal()] = 2;
        ConfigurableExprValueFormatter$PrettyFormatter$WhenMappings.$EnumSwitchMapping$0[ExprValueType.BOOL.ordinal()] = 3;
        ConfigurableExprValueFormatter$PrettyFormatter$WhenMappings.$EnumSwitchMapping$0[ExprValueType.INT.ordinal()] = 4;
        ConfigurableExprValueFormatter$PrettyFormatter$WhenMappings.$EnumSwitchMapping$0[ExprValueType.DECIMAL.ordinal()] = 5;
        ConfigurableExprValueFormatter$PrettyFormatter$WhenMappings.$EnumSwitchMapping$0[ExprValueType.STRING.ordinal()] = 6;
        ConfigurableExprValueFormatter$PrettyFormatter$WhenMappings.$EnumSwitchMapping$0[ExprValueType.DATE.ordinal()] = 7;
        ConfigurableExprValueFormatter$PrettyFormatter$WhenMappings.$EnumSwitchMapping$0[ExprValueType.TIME.ordinal()] = 8;
        ConfigurableExprValueFormatter$PrettyFormatter$WhenMappings.$EnumSwitchMapping$0[ExprValueType.FLOAT.ordinal()] = 9;
        ConfigurableExprValueFormatter$PrettyFormatter$WhenMappings.$EnumSwitchMapping$0[ExprValueType.TIMESTAMP.ordinal()] = 10;
        ConfigurableExprValueFormatter$PrettyFormatter$WhenMappings.$EnumSwitchMapping$0[ExprValueType.SYMBOL.ordinal()] = 11;
        ConfigurableExprValueFormatter$PrettyFormatter$WhenMappings.$EnumSwitchMapping$0[ExprValueType.CLOB.ordinal()] = 12;
        ConfigurableExprValueFormatter$PrettyFormatter$WhenMappings.$EnumSwitchMapping$0[ExprValueType.BLOB.ordinal()] = 13;
        ConfigurableExprValueFormatter$PrettyFormatter$WhenMappings.$EnumSwitchMapping$0[ExprValueType.SEXP.ordinal()] = 14;
        ConfigurableExprValueFormatter$PrettyFormatter$WhenMappings.$EnumSwitchMapping$0[ExprValueType.LIST.ordinal()] = 15;
        ConfigurableExprValueFormatter$PrettyFormatter$WhenMappings.$EnumSwitchMapping$0[ExprValueType.BAG.ordinal()] = 16;
        ConfigurableExprValueFormatter$PrettyFormatter$WhenMappings.$EnumSwitchMapping$0[ExprValueType.STRUCT.ordinal()] = 17;
    }
}

