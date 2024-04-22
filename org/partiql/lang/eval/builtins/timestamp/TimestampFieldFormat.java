/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.partiql.lang.eval.builtins.timestamp;

import kotlin.Metadata;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0004\b\u0080\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004\u00a8\u0006\u0005"}, d2={"Lorg/partiql/lang/eval/builtins/timestamp/TimestampFieldFormat;", "", "(Ljava/lang/String;I)V", "NUMBER", "ZERO_PADDED_NUMBER", "lang"})
public final class TimestampFieldFormat
extends Enum<TimestampFieldFormat> {
    public static final /* enum */ TimestampFieldFormat NUMBER;
    public static final /* enum */ TimestampFieldFormat ZERO_PADDED_NUMBER;
    private static final /* synthetic */ TimestampFieldFormat[] $VALUES;

    static {
        TimestampFieldFormat[] timestampFieldFormatArray = new TimestampFieldFormat[2];
        TimestampFieldFormat[] timestampFieldFormatArray2 = timestampFieldFormatArray;
        timestampFieldFormatArray[0] = NUMBER = new TimestampFieldFormat();
        timestampFieldFormatArray[1] = ZERO_PADDED_NUMBER = new TimestampFieldFormat();
        $VALUES = timestampFieldFormatArray;
    }

    public static TimestampFieldFormat[] values() {
        return (TimestampFieldFormat[])$VALUES.clone();
    }

    public static TimestampFieldFormat valueOf(String string) {
        return Enum.valueOf(TimestampFieldFormat.class, string);
    }
}

