/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.partiql.lang.eval.builtins;

import com.amazon.ion.Timestamp;
import kotlin.Metadata;
import org.partiql.lang.syntax.DatePart;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=3)
public final class DateAddExprFunction$WhenMappings {
    public static final /* synthetic */ int[] $EnumSwitchMapping$0;
    public static final /* synthetic */ int[] $EnumSwitchMapping$1;

    static {
        $EnumSwitchMapping$0 = new int[Timestamp.Precision.values().length];
        DateAddExprFunction$WhenMappings.$EnumSwitchMapping$0[Timestamp.Precision.YEAR.ordinal()] = 1;
        DateAddExprFunction$WhenMappings.$EnumSwitchMapping$0[Timestamp.Precision.MONTH.ordinal()] = 2;
        DateAddExprFunction$WhenMappings.$EnumSwitchMapping$0[Timestamp.Precision.DAY.ordinal()] = 3;
        DateAddExprFunction$WhenMappings.$EnumSwitchMapping$0[Timestamp.Precision.SECOND.ordinal()] = 4;
        DateAddExprFunction$WhenMappings.$EnumSwitchMapping$0[Timestamp.Precision.MINUTE.ordinal()] = 5;
        $EnumSwitchMapping$1 = new int[DatePart.values().length];
        DateAddExprFunction$WhenMappings.$EnumSwitchMapping$1[DatePart.YEAR.ordinal()] = 1;
        DateAddExprFunction$WhenMappings.$EnumSwitchMapping$1[DatePart.MONTH.ordinal()] = 2;
        DateAddExprFunction$WhenMappings.$EnumSwitchMapping$1[DatePart.DAY.ordinal()] = 3;
        DateAddExprFunction$WhenMappings.$EnumSwitchMapping$1[DatePart.HOUR.ordinal()] = 4;
        DateAddExprFunction$WhenMappings.$EnumSwitchMapping$1[DatePart.MINUTE.ordinal()] = 5;
        DateAddExprFunction$WhenMappings.$EnumSwitchMapping$1[DatePart.SECOND.ordinal()] = 6;
    }
}

