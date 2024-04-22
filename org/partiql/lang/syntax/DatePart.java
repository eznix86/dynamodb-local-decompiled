/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.partiql.lang.syntax;

import kotlin.Metadata;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\n\b\u0080\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005j\u0002\b\u0006j\u0002\b\u0007j\u0002\b\bj\u0002\b\tj\u0002\b\n\u00a8\u0006\u000b"}, d2={"Lorg/partiql/lang/syntax/DatePart;", "", "(Ljava/lang/String;I)V", "YEAR", "MONTH", "DAY", "HOUR", "MINUTE", "SECOND", "TIMEZONE_HOUR", "TIMEZONE_MINUTE", "lang"})
public final class DatePart
extends Enum<DatePart> {
    public static final /* enum */ DatePart YEAR;
    public static final /* enum */ DatePart MONTH;
    public static final /* enum */ DatePart DAY;
    public static final /* enum */ DatePart HOUR;
    public static final /* enum */ DatePart MINUTE;
    public static final /* enum */ DatePart SECOND;
    public static final /* enum */ DatePart TIMEZONE_HOUR;
    public static final /* enum */ DatePart TIMEZONE_MINUTE;
    private static final /* synthetic */ DatePart[] $VALUES;

    static {
        DatePart[] datePartArray = new DatePart[8];
        DatePart[] datePartArray2 = datePartArray;
        datePartArray[0] = YEAR = new DatePart();
        datePartArray[1] = MONTH = new DatePart();
        datePartArray[2] = DAY = new DatePart();
        datePartArray[3] = HOUR = new DatePart();
        datePartArray[4] = MINUTE = new DatePart();
        datePartArray[5] = SECOND = new DatePart();
        datePartArray[6] = TIMEZONE_HOUR = new DatePart();
        datePartArray[7] = TIMEZONE_MINUTE = new DatePart();
        $VALUES = datePartArray;
    }

    public static DatePart[] values() {
        return (DatePart[])$VALUES.clone();
    }

    public static DatePart valueOf(String string) {
        return Enum.valueOf(DatePart.class, string);
    }
}

