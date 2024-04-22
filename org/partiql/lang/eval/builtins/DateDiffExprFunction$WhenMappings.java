/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.partiql.lang.eval.builtins;

import kotlin.Metadata;
import org.partiql.lang.syntax.DatePart;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=3)
public final class DateDiffExprFunction$WhenMappings {
    public static final /* synthetic */ int[] $EnumSwitchMapping$0;

    static {
        $EnumSwitchMapping$0 = new int[DatePart.values().length];
        DateDiffExprFunction$WhenMappings.$EnumSwitchMapping$0[DatePart.YEAR.ordinal()] = 1;
        DateDiffExprFunction$WhenMappings.$EnumSwitchMapping$0[DatePart.MONTH.ordinal()] = 2;
        DateDiffExprFunction$WhenMappings.$EnumSwitchMapping$0[DatePart.DAY.ordinal()] = 3;
        DateDiffExprFunction$WhenMappings.$EnumSwitchMapping$0[DatePart.HOUR.ordinal()] = 4;
        DateDiffExprFunction$WhenMappings.$EnumSwitchMapping$0[DatePart.MINUTE.ordinal()] = 5;
        DateDiffExprFunction$WhenMappings.$EnumSwitchMapping$0[DatePart.SECOND.ordinal()] = 6;
    }
}

