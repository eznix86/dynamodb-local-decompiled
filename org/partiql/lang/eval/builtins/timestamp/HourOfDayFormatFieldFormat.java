/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package org.partiql.lang.eval.builtins.timestamp;

import kotlin.Metadata;
import org.jetbrains.annotations.NotNull;
import org.partiql.lang.eval.builtins.timestamp.HourClock;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\b\u0080\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u000f\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006j\u0002\b\u0007j\u0002\b\bj\u0002\b\tj\u0002\b\n\u00a8\u0006\u000b"}, d2={"Lorg/partiql/lang/eval/builtins/timestamp/HourOfDayFormatFieldFormat;", "", "clock", "Lorg/partiql/lang/eval/builtins/timestamp/HourClock;", "(Ljava/lang/String;ILorg/partiql/lang/eval/builtins/timestamp/HourClock;)V", "getClock", "()Lorg/partiql/lang/eval/builtins/timestamp/HourClock;", "NUMBER_12_HOUR", "ZERO_PADDED_NUMBER_12_HOUR", "NUMBER_24_HOUR", "ZERO_PADDED_NUMBER_24_HOUR", "lang"})
public final class HourOfDayFormatFieldFormat
extends Enum<HourOfDayFormatFieldFormat> {
    public static final /* enum */ HourOfDayFormatFieldFormat NUMBER_12_HOUR;
    public static final /* enum */ HourOfDayFormatFieldFormat ZERO_PADDED_NUMBER_12_HOUR;
    public static final /* enum */ HourOfDayFormatFieldFormat NUMBER_24_HOUR;
    public static final /* enum */ HourOfDayFormatFieldFormat ZERO_PADDED_NUMBER_24_HOUR;
    private static final /* synthetic */ HourOfDayFormatFieldFormat[] $VALUES;
    @NotNull
    private final HourClock clock;

    static {
        HourOfDayFormatFieldFormat[] hourOfDayFormatFieldFormatArray = new HourOfDayFormatFieldFormat[4];
        HourOfDayFormatFieldFormat[] hourOfDayFormatFieldFormatArray2 = hourOfDayFormatFieldFormatArray;
        hourOfDayFormatFieldFormatArray[0] = NUMBER_12_HOUR = new HourOfDayFormatFieldFormat(HourClock.TwelveHour);
        hourOfDayFormatFieldFormatArray[1] = ZERO_PADDED_NUMBER_12_HOUR = new HourOfDayFormatFieldFormat(HourClock.TwelveHour);
        hourOfDayFormatFieldFormatArray[2] = NUMBER_24_HOUR = new HourOfDayFormatFieldFormat(HourClock.TwentyFourHour);
        hourOfDayFormatFieldFormatArray[3] = ZERO_PADDED_NUMBER_24_HOUR = new HourOfDayFormatFieldFormat(HourClock.TwentyFourHour);
        $VALUES = hourOfDayFormatFieldFormatArray;
    }

    @NotNull
    public final HourClock getClock() {
        return this.clock;
    }

    private HourOfDayFormatFieldFormat(HourClock clock) {
        this.clock = clock;
    }

    public static HourOfDayFormatFieldFormat[] values() {
        return (HourOfDayFormatFieldFormat[])$VALUES.clone();
    }

    public static HourOfDayFormatFieldFormat valueOf(String string) {
        return Enum.valueOf(HourOfDayFormatFieldFormat.class, string);
    }
}

