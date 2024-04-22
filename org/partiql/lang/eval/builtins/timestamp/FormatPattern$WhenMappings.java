/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.partiql.lang.eval.builtins.timestamp;

import kotlin.Metadata;
import org.partiql.lang.eval.builtins.timestamp.HourClock;
import org.partiql.lang.eval.builtins.timestamp.TimestampField;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=3)
public final class FormatPattern$WhenMappings {
    public static final /* synthetic */ int[] $EnumSwitchMapping$0;
    public static final /* synthetic */ int[] $EnumSwitchMapping$1;

    static {
        $EnumSwitchMapping$0 = new int[HourClock.values().length];
        FormatPattern$WhenMappings.$EnumSwitchMapping$0[HourClock.TwelveHour.ordinal()] = 1;
        FormatPattern$WhenMappings.$EnumSwitchMapping$0[HourClock.TwentyFourHour.ordinal()] = 2;
        $EnumSwitchMapping$1 = new int[TimestampField.values().length];
        FormatPattern$WhenMappings.$EnumSwitchMapping$1[TimestampField.YEAR.ordinal()] = 1;
        FormatPattern$WhenMappings.$EnumSwitchMapping$1[TimestampField.MONTH_OF_YEAR.ordinal()] = 2;
        FormatPattern$WhenMappings.$EnumSwitchMapping$1[TimestampField.DAY_OF_MONTH.ordinal()] = 3;
        FormatPattern$WhenMappings.$EnumSwitchMapping$1[TimestampField.HOUR_OF_DAY.ordinal()] = 4;
        FormatPattern$WhenMappings.$EnumSwitchMapping$1[TimestampField.MINUTE_OF_HOUR.ordinal()] = 5;
        FormatPattern$WhenMappings.$EnumSwitchMapping$1[TimestampField.SECOND_OF_MINUTE.ordinal()] = 6;
        FormatPattern$WhenMappings.$EnumSwitchMapping$1[TimestampField.FRACTION_OF_SECOND.ordinal()] = 7;
        FormatPattern$WhenMappings.$EnumSwitchMapping$1[TimestampField.OFFSET.ordinal()] = 8;
        FormatPattern$WhenMappings.$EnumSwitchMapping$1[TimestampField.AM_PM.ordinal()] = 9;
    }
}

