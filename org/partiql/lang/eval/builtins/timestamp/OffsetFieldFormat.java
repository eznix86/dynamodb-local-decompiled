/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.partiql.lang.eval.builtins.timestamp;

import kotlin.Metadata;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\b\b\u0080\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005j\u0002\b\u0006j\u0002\b\u0007j\u0002\b\b\u00a8\u0006\t"}, d2={"Lorg/partiql/lang/eval/builtins/timestamp/OffsetFieldFormat;", "", "(Ljava/lang/String;I)V", "ZERO_PADDED_HOUR", "ZERO_PADDED_HOUR_MINUTE", "ZERO_PADDED_HOUR_COLON_MINUTE", "ZERO_PADDED_HOUR_OR_Z", "ZERO_PADDED_HOUR_MINUTE_OR_Z", "ZERO_PADDED_HOUR_COLON_MINUTE_OR_Z", "lang"})
public final class OffsetFieldFormat
extends Enum<OffsetFieldFormat> {
    public static final /* enum */ OffsetFieldFormat ZERO_PADDED_HOUR;
    public static final /* enum */ OffsetFieldFormat ZERO_PADDED_HOUR_MINUTE;
    public static final /* enum */ OffsetFieldFormat ZERO_PADDED_HOUR_COLON_MINUTE;
    public static final /* enum */ OffsetFieldFormat ZERO_PADDED_HOUR_OR_Z;
    public static final /* enum */ OffsetFieldFormat ZERO_PADDED_HOUR_MINUTE_OR_Z;
    public static final /* enum */ OffsetFieldFormat ZERO_PADDED_HOUR_COLON_MINUTE_OR_Z;
    private static final /* synthetic */ OffsetFieldFormat[] $VALUES;

    static {
        OffsetFieldFormat[] offsetFieldFormatArray = new OffsetFieldFormat[6];
        OffsetFieldFormat[] offsetFieldFormatArray2 = offsetFieldFormatArray;
        offsetFieldFormatArray[0] = ZERO_PADDED_HOUR = new OffsetFieldFormat();
        offsetFieldFormatArray[1] = ZERO_PADDED_HOUR_MINUTE = new OffsetFieldFormat();
        offsetFieldFormatArray[2] = ZERO_PADDED_HOUR_COLON_MINUTE = new OffsetFieldFormat();
        offsetFieldFormatArray[3] = ZERO_PADDED_HOUR_OR_Z = new OffsetFieldFormat();
        offsetFieldFormatArray[4] = ZERO_PADDED_HOUR_MINUTE_OR_Z = new OffsetFieldFormat();
        offsetFieldFormatArray[5] = ZERO_PADDED_HOUR_COLON_MINUTE_OR_Z = new OffsetFieldFormat();
        $VALUES = offsetFieldFormatArray;
    }

    public static OffsetFieldFormat[] values() {
        return (OffsetFieldFormat[])$VALUES.clone();
    }

    public static OffsetFieldFormat valueOf(String string) {
        return Enum.valueOf(OffsetFieldFormat.class, string);
    }
}

