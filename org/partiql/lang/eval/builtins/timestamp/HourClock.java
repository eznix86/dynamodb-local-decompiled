/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.partiql.lang.eval.builtins.timestamp;

import kotlin.Metadata;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0004\b\u0080\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004\u00a8\u0006\u0005"}, d2={"Lorg/partiql/lang/eval/builtins/timestamp/HourClock;", "", "(Ljava/lang/String;I)V", "TwelveHour", "TwentyFourHour", "lang"})
public final class HourClock
extends Enum<HourClock> {
    public static final /* enum */ HourClock TwelveHour;
    public static final /* enum */ HourClock TwentyFourHour;
    private static final /* synthetic */ HourClock[] $VALUES;

    static {
        HourClock[] hourClockArray = new HourClock[2];
        HourClock[] hourClockArray2 = hourClockArray;
        hourClockArray[0] = TwelveHour = new HourClock();
        hourClockArray[1] = TwentyFourHour = new HourClock();
        $VALUES = hourClockArray;
    }

    public static HourClock[] values() {
        return (HourClock[])$VALUES.clone();
    }

    public static HourClock valueOf(String string) {
        return Enum.valueOf(HourClock.class, string);
    }
}

