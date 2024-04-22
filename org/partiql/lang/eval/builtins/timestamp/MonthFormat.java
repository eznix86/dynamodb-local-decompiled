/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.partiql.lang.eval.builtins.timestamp;

import kotlin.Metadata;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0007\b\u0080\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005j\u0002\b\u0006j\u0002\b\u0007\u00a8\u0006\b"}, d2={"Lorg/partiql/lang/eval/builtins/timestamp/MonthFormat;", "", "(Ljava/lang/String;I)V", "MONTH_NUMBER", "MONTH_NUMBER_ZERO_PADDED", "ABBREVIATED_MONTH_NAME", "FULL_MONTH_NAME", "FIRST_LETTER_OF_MONTH_NAME", "lang"})
public final class MonthFormat
extends Enum<MonthFormat> {
    public static final /* enum */ MonthFormat MONTH_NUMBER;
    public static final /* enum */ MonthFormat MONTH_NUMBER_ZERO_PADDED;
    public static final /* enum */ MonthFormat ABBREVIATED_MONTH_NAME;
    public static final /* enum */ MonthFormat FULL_MONTH_NAME;
    public static final /* enum */ MonthFormat FIRST_LETTER_OF_MONTH_NAME;
    private static final /* synthetic */ MonthFormat[] $VALUES;

    static {
        MonthFormat[] monthFormatArray = new MonthFormat[5];
        MonthFormat[] monthFormatArray2 = monthFormatArray;
        monthFormatArray[0] = MONTH_NUMBER = new MonthFormat();
        monthFormatArray[1] = MONTH_NUMBER_ZERO_PADDED = new MonthFormat();
        monthFormatArray[2] = ABBREVIATED_MONTH_NAME = new MonthFormat();
        monthFormatArray[3] = FULL_MONTH_NAME = new MonthFormat();
        monthFormatArray[4] = FIRST_LETTER_OF_MONTH_NAME = new MonthFormat();
        $VALUES = monthFormatArray;
    }

    public static MonthFormat[] values() {
        return (MonthFormat[])$VALUES.clone();
    }

    public static MonthFormat valueOf(String string) {
        return Enum.valueOf(MonthFormat.class, string);
    }
}

