/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.partiql.lang.eval.builtins.timestamp;

import kotlin.Metadata;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0005\b\u0080\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005\u00a8\u0006\u0006"}, d2={"Lorg/partiql/lang/eval/builtins/timestamp/YearFormat;", "", "(Ljava/lang/String;I)V", "TWO_DIGIT", "FOUR_DIGIT", "FOUR_DIGIT_ZERO_PADDED", "lang"})
public final class YearFormat
extends Enum<YearFormat> {
    public static final /* enum */ YearFormat TWO_DIGIT;
    public static final /* enum */ YearFormat FOUR_DIGIT;
    public static final /* enum */ YearFormat FOUR_DIGIT_ZERO_PADDED;
    private static final /* synthetic */ YearFormat[] $VALUES;

    static {
        YearFormat[] yearFormatArray = new YearFormat[3];
        YearFormat[] yearFormatArray2 = yearFormatArray;
        yearFormatArray[0] = TWO_DIGIT = new YearFormat();
        yearFormatArray[1] = FOUR_DIGIT = new YearFormat();
        yearFormatArray[2] = FOUR_DIGIT_ZERO_PADDED = new YearFormat();
        $VALUES = yearFormatArray;
    }

    public static YearFormat[] values() {
        return (YearFormat[])$VALUES.clone();
    }

    public static YearFormat valueOf(String string) {
        return Enum.valueOf(YearFormat.class, string);
    }
}

