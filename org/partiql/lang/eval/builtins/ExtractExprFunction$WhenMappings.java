/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.partiql.lang.eval.builtins;

import kotlin.Metadata;
import org.partiql.lang.eval.ExprValueType;
import org.partiql.lang.syntax.DatePart;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=3)
public final class ExtractExprFunction$WhenMappings {
    public static final /* synthetic */ int[] $EnumSwitchMapping$0;
    public static final /* synthetic */ int[] $EnumSwitchMapping$1;
    public static final /* synthetic */ int[] $EnumSwitchMapping$2;
    public static final /* synthetic */ int[] $EnumSwitchMapping$3;

    static {
        $EnumSwitchMapping$0 = new int[DatePart.values().length];
        ExtractExprFunction$WhenMappings.$EnumSwitchMapping$0[DatePart.YEAR.ordinal()] = 1;
        ExtractExprFunction$WhenMappings.$EnumSwitchMapping$0[DatePart.MONTH.ordinal()] = 2;
        ExtractExprFunction$WhenMappings.$EnumSwitchMapping$0[DatePart.DAY.ordinal()] = 3;
        ExtractExprFunction$WhenMappings.$EnumSwitchMapping$0[DatePart.HOUR.ordinal()] = 4;
        ExtractExprFunction$WhenMappings.$EnumSwitchMapping$0[DatePart.MINUTE.ordinal()] = 5;
        ExtractExprFunction$WhenMappings.$EnumSwitchMapping$0[DatePart.SECOND.ordinal()] = 6;
        ExtractExprFunction$WhenMappings.$EnumSwitchMapping$0[DatePart.TIMEZONE_HOUR.ordinal()] = 7;
        ExtractExprFunction$WhenMappings.$EnumSwitchMapping$0[DatePart.TIMEZONE_MINUTE.ordinal()] = 8;
        $EnumSwitchMapping$1 = new int[DatePart.values().length];
        ExtractExprFunction$WhenMappings.$EnumSwitchMapping$1[DatePart.YEAR.ordinal()] = 1;
        ExtractExprFunction$WhenMappings.$EnumSwitchMapping$1[DatePart.MONTH.ordinal()] = 2;
        ExtractExprFunction$WhenMappings.$EnumSwitchMapping$1[DatePart.DAY.ordinal()] = 3;
        ExtractExprFunction$WhenMappings.$EnumSwitchMapping$1[DatePart.TIMEZONE_HOUR.ordinal()] = 4;
        ExtractExprFunction$WhenMappings.$EnumSwitchMapping$1[DatePart.TIMEZONE_MINUTE.ordinal()] = 5;
        ExtractExprFunction$WhenMappings.$EnumSwitchMapping$1[DatePart.HOUR.ordinal()] = 6;
        ExtractExprFunction$WhenMappings.$EnumSwitchMapping$1[DatePart.MINUTE.ordinal()] = 7;
        ExtractExprFunction$WhenMappings.$EnumSwitchMapping$1[DatePart.SECOND.ordinal()] = 8;
        $EnumSwitchMapping$2 = new int[DatePart.values().length];
        ExtractExprFunction$WhenMappings.$EnumSwitchMapping$2[DatePart.HOUR.ordinal()] = 1;
        ExtractExprFunction$WhenMappings.$EnumSwitchMapping$2[DatePart.MINUTE.ordinal()] = 2;
        ExtractExprFunction$WhenMappings.$EnumSwitchMapping$2[DatePart.SECOND.ordinal()] = 3;
        ExtractExprFunction$WhenMappings.$EnumSwitchMapping$2[DatePart.TIMEZONE_HOUR.ordinal()] = 4;
        ExtractExprFunction$WhenMappings.$EnumSwitchMapping$2[DatePart.TIMEZONE_MINUTE.ordinal()] = 5;
        ExtractExprFunction$WhenMappings.$EnumSwitchMapping$2[DatePart.YEAR.ordinal()] = 6;
        ExtractExprFunction$WhenMappings.$EnumSwitchMapping$2[DatePart.MONTH.ordinal()] = 7;
        ExtractExprFunction$WhenMappings.$EnumSwitchMapping$2[DatePart.DAY.ordinal()] = 8;
        $EnumSwitchMapping$3 = new int[ExprValueType.values().length];
        ExtractExprFunction$WhenMappings.$EnumSwitchMapping$3[ExprValueType.TIMESTAMP.ordinal()] = 1;
        ExtractExprFunction$WhenMappings.$EnumSwitchMapping$3[ExprValueType.DATE.ordinal()] = 2;
        ExtractExprFunction$WhenMappings.$EnumSwitchMapping$3[ExprValueType.TIME.ordinal()] = 3;
    }
}

