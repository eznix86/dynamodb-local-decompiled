/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.Nullable
 */
package org.partiql.lang.eval.builtins.timestamp;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010\b\n\u0002\b\u000e\b\u0080\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0013\b\u0002\u0012\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\u0002\u0010\u0004R\u0015\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\n\n\u0002\u0010\u0007\u001a\u0004\b\u0005\u0010\u0006j\u0002\b\bj\u0002\b\tj\u0002\b\nj\u0002\b\u000bj\u0002\b\fj\u0002\b\rj\u0002\b\u000ej\u0002\b\u000fj\u0002\b\u0010\u00a8\u0006\u0011"}, d2={"Lorg/partiql/lang/eval/builtins/timestamp/TimestampField;", "", "precisionRank", "", "(Ljava/lang/String;ILjava/lang/Integer;)V", "getPrecisionRank", "()Ljava/lang/Integer;", "Ljava/lang/Integer;", "YEAR", "MONTH_OF_YEAR", "DAY_OF_MONTH", "HOUR_OF_DAY", "AM_PM", "MINUTE_OF_HOUR", "SECOND_OF_MINUTE", "FRACTION_OF_SECOND", "OFFSET", "lang"})
public final class TimestampField
extends Enum<TimestampField> {
    public static final /* enum */ TimestampField YEAR;
    public static final /* enum */ TimestampField MONTH_OF_YEAR;
    public static final /* enum */ TimestampField DAY_OF_MONTH;
    public static final /* enum */ TimestampField HOUR_OF_DAY;
    public static final /* enum */ TimestampField AM_PM;
    public static final /* enum */ TimestampField MINUTE_OF_HOUR;
    public static final /* enum */ TimestampField SECOND_OF_MINUTE;
    public static final /* enum */ TimestampField FRACTION_OF_SECOND;
    public static final /* enum */ TimestampField OFFSET;
    private static final /* synthetic */ TimestampField[] $VALUES;
    @Nullable
    private final Integer precisionRank;

    static {
        TimestampField[] timestampFieldArray = new TimestampField[9];
        TimestampField[] timestampFieldArray2 = timestampFieldArray;
        timestampFieldArray[0] = YEAR = new TimestampField(0);
        timestampFieldArray[1] = MONTH_OF_YEAR = new TimestampField(1);
        timestampFieldArray[2] = DAY_OF_MONTH = new TimestampField(2);
        timestampFieldArray[3] = HOUR_OF_DAY = new TimestampField(3);
        timestampFieldArray[4] = AM_PM = new TimestampField("AM_PM", 4, null, 1, null);
        timestampFieldArray[5] = MINUTE_OF_HOUR = new TimestampField(4);
        timestampFieldArray[6] = SECOND_OF_MINUTE = new TimestampField(5);
        timestampFieldArray[7] = FRACTION_OF_SECOND = new TimestampField(6);
        timestampFieldArray[8] = OFFSET = new TimestampField("OFFSET", 8, null, 1, null);
        $VALUES = timestampFieldArray;
    }

    @Nullable
    public final Integer getPrecisionRank() {
        return this.precisionRank;
    }

    private TimestampField(Integer precisionRank) {
        this.precisionRank = precisionRank;
    }

    /* synthetic */ TimestampField(String string, int n, Integer n2, int n3, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n3 & 1) != 0) {
            n2 = null;
        }
        this(n2);
    }

    public static TimestampField[] values() {
        return (TimestampField[])$VALUES.clone();
    }

    public static TimestampField valueOf(String string) {
        return Enum.valueOf(TimestampField.class, string);
    }
}

