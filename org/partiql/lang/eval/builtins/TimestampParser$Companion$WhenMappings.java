/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.partiql.lang.eval.builtins;

import kotlin.Metadata;
import org.partiql.lang.eval.builtins.timestamp.TimestampField;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=3)
public final class TimestampParser$Companion$WhenMappings {
    public static final /* synthetic */ int[] $EnumSwitchMapping$0;

    static {
        $EnumSwitchMapping$0 = new int[TimestampField.values().length];
        TimestampParser$Companion$WhenMappings.$EnumSwitchMapping$0[TimestampField.FRACTION_OF_SECOND.ordinal()] = 1;
        TimestampParser$Companion$WhenMappings.$EnumSwitchMapping$0[TimestampField.SECOND_OF_MINUTE.ordinal()] = 2;
        TimestampParser$Companion$WhenMappings.$EnumSwitchMapping$0[TimestampField.MINUTE_OF_HOUR.ordinal()] = 3;
        TimestampParser$Companion$WhenMappings.$EnumSwitchMapping$0[TimestampField.HOUR_OF_DAY.ordinal()] = 4;
        TimestampParser$Companion$WhenMappings.$EnumSwitchMapping$0[TimestampField.DAY_OF_MONTH.ordinal()] = 5;
        TimestampParser$Companion$WhenMappings.$EnumSwitchMapping$0[TimestampField.MONTH_OF_YEAR.ordinal()] = 6;
        TimestampParser$Companion$WhenMappings.$EnumSwitchMapping$0[TimestampField.YEAR.ordinal()] = 7;
        TimestampParser$Companion$WhenMappings.$EnumSwitchMapping$0[TimestampField.AM_PM.ordinal()] = 8;
        TimestampParser$Companion$WhenMappings.$EnumSwitchMapping$0[TimestampField.OFFSET.ordinal()] = 9;
    }
}

