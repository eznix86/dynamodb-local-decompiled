/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package org.partiql.lang.eval.time;

import com.amazon.ion.IonStruct;
import com.amazon.ion.IonSystem;
import com.amazon.ion.IonValue;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.DateTimeException;
import java.time.LocalTime;
import java.time.OffsetTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.jvm.JvmOverloads;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.partiql.lang.errors.ErrorCode;
import org.partiql.lang.eval.EvaluationException;
import org.partiql.lang.eval.ExceptionsKt;
import org.partiql.lang.util.PropertyMapHelpersKt;
import org.partiql.lang.util.TimeExtensionsKt;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000L\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u000e\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\b\u0018\u0000 -2\u00020\u0001:\u0001-B#\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0007\u00a2\u0006\u0002\u0010\bJ\t\u0010\u001c\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u001d\u001a\u00020\u0005H\u00c6\u0003J\u000b\u0010\u001e\u001a\u0004\u0018\u00010\u0007H\u00c6\u0003J)\u0010\u001f\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0007H\u00c6\u0001J\u0013\u0010 \u001a\u00020!2\b\u0010\"\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\b\u0010#\u001a\u00020$H\u0002J\t\u0010%\u001a\u00020\u0005H\u00d6\u0001J\u000e\u0010&\u001a\u00020!2\u0006\u0010\"\u001a\u00020\u0000J\u000e\u0010'\u001a\u00020\u00052\u0006\u0010\"\u001a\u00020\u0000J\u000e\u0010(\u001a\u00020)2\u0006\u0010*\u001a\u00020+J\b\u0010,\u001a\u00020$H\u0016R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0013\u0010\u000b\u001a\u0004\u0018\u00010\f8F\u00a2\u0006\u0006\u001a\u0004\b\r\u0010\u000eR\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0011\u0010\u0011\u001a\u00020\u00128F\u00a2\u0006\u0006\u001a\u0004\b\u0013\u0010\u0014R\u0013\u0010\u0015\u001a\u0004\u0018\u00010\u00058F\u00a2\u0006\u0006\u001a\u0004\b\u0016\u0010\u0017R\u0013\u0010\u0018\u001a\u0004\u0018\u00010\u00058F\u00a2\u0006\u0006\u001a\u0004\b\u0019\u0010\u0017R\u0013\u0010\u0006\u001a\u0004\u0018\u00010\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u001b\u00a8\u0006."}, d2={"Lorg/partiql/lang/eval/time/Time;", "", "localTime", "Ljava/time/LocalTime;", "precision", "", "zoneOffset", "Ljava/time/ZoneOffset;", "(Ljava/time/LocalTime;ILjava/time/ZoneOffset;)V", "getLocalTime", "()Ljava/time/LocalTime;", "offsetTime", "Ljava/time/OffsetTime;", "getOffsetTime", "()Ljava/time/OffsetTime;", "getPrecision", "()I", "secondsWithFractionalPart", "Ljava/math/BigDecimal;", "getSecondsWithFractionalPart", "()Ljava/math/BigDecimal;", "timezoneHour", "getTimezoneHour", "()Ljava/lang/Integer;", "timezoneMinute", "getTimezoneMinute", "getZoneOffset", "()Ljava/time/ZoneOffset;", "component1", "component2", "component3", "copy", "equals", "", "other", "formatterPattern", "", "hashCode", "isDirectlyComparableTo", "naturalOrderCompareTo", "toIonValue", "Lcom/amazon/ion/IonStruct;", "ion", "Lcom/amazon/ion/IonSystem;", "toString", "Companion", "lang"})
public final class Time {
    @NotNull
    private final LocalTime localTime;
    private final int precision;
    @Nullable
    private final ZoneOffset zoneOffset;
    private static final int LESS = -1;
    private static final int MORE = 1;
    public static final Companion Companion = new Companion(null);

    @Nullable
    public final OffsetTime getOffsetTime() {
        OffsetTime offsetTime;
        ZoneOffset zoneOffset = this.zoneOffset;
        if (zoneOffset != null) {
            ZoneOffset zoneOffset2 = zoneOffset;
            boolean bl = false;
            boolean bl2 = false;
            ZoneOffset it = zoneOffset2;
            boolean bl3 = false;
            offsetTime = OffsetTime.of(this.localTime, it);
        } else {
            offsetTime = null;
        }
        return offsetTime;
    }

    @Nullable
    public final Integer getTimezoneHour() {
        ZoneOffset zoneOffset = this.zoneOffset;
        return zoneOffset != null ? Integer.valueOf(zoneOffset.getTotalSeconds() / 3600) : null;
    }

    @Nullable
    public final Integer getTimezoneMinute() {
        ZoneOffset zoneOffset = this.zoneOffset;
        return zoneOffset != null ? Integer.valueOf(zoneOffset.getTotalSeconds() / 60 % 60) : null;
    }

    @NotNull
    public final BigDecimal getSecondsWithFractionalPart() {
        int n = this.localTime.getSecond();
        int n2 = 0;
        BigDecimal bigDecimal = BigDecimal.valueOf(n);
        Intrinsics.checkExpressionValueIsNotNull(bigDecimal, "BigDecimal.valueOf(this.toLong())");
        BigDecimal bigDecimal2 = bigDecimal;
        n2 = this.localTime.getNano();
        boolean bl = false;
        BigDecimal bigDecimal3 = BigDecimal.valueOf(n2);
        Intrinsics.checkExpressionValueIsNotNull(bigDecimal3, "BigDecimal.valueOf(this.toLong())");
        n2 = 1000000000;
        BigDecimal bigDecimal4 = bigDecimal3;
        bl = false;
        BigDecimal bigDecimal5 = BigDecimal.valueOf(n2);
        Intrinsics.checkExpressionValueIsNotNull(bigDecimal5, "BigDecimal.valueOf(this.toLong())");
        BigDecimal bigDecimal6 = bigDecimal5;
        BigDecimal bigDecimal7 = bigDecimal4.divide(bigDecimal6);
        Intrinsics.checkExpressionValueIsNotNull(bigDecimal7, "localTime.nano.toBigDeci\u2026ER_SECOND.toBigDecimal())");
        BigDecimal bigDecimal8 = bigDecimal7;
        bl = false;
        BigDecimal bigDecimal9 = bigDecimal2.add(bigDecimal8);
        Intrinsics.checkExpressionValueIsNotNull(bigDecimal9, "this.add(other)");
        BigDecimal bigDecimal10 = bigDecimal9.setScale(this.precision, RoundingMode.HALF_EVEN);
        Intrinsics.checkExpressionValueIsNotNull(bigDecimal10, "(localTime.second.toBigD\u2026, RoundingMode.HALF_EVEN)");
        return bigDecimal10;
    }

    @NotNull
    public final IonStruct toIonValue(@NotNull IonSystem ion) {
        Intrinsics.checkParameterIsNotNull(ion, "ion");
        IonStruct ionStruct = ion.newEmptyStruct();
        boolean bl = false;
        boolean bl2 = false;
        IonStruct $this$apply = ionStruct;
        boolean bl3 = false;
        $this$apply.add("hour", (IonValue)ion.newInt(this.localTime.getHour()));
        $this$apply.add("minute", (IonValue)ion.newInt(this.localTime.getMinute()));
        $this$apply.add("second", (IonValue)ion.newDecimal(this.getSecondsWithFractionalPart()));
        $this$apply.add("timezone_hour", (IonValue)ion.newInt(this.getTimezoneHour()));
        $this$apply.add("timezone_minute", (IonValue)ion.newInt(this.getTimezoneMinute()));
        $this$apply.addTypeAnnotation("$partiql_time");
        IonStruct ionStruct2 = ionStruct;
        Intrinsics.checkExpressionValueIsNotNull(ionStruct2, "ion.newEmptyStruct().app\u2026IME_ANNOTATION)\n        }");
        return ionStruct2;
    }

    private final String formatterPattern() {
        String string;
        StringBuilder stringBuilder = new StringBuilder().append("HH:mm:ss");
        if (this.precision > 0) {
            int n = 9;
            int n2 = this.precision;
            CharSequence charSequence = "S";
            StringBuilder stringBuilder2 = new StringBuilder().append(".");
            StringBuilder stringBuilder3 = stringBuilder;
            boolean bl = false;
            int n3 = Math.min(n, n2);
            stringBuilder = stringBuilder3;
            string = stringBuilder2.append(StringsKt.repeat(charSequence, n3)).toString();
        } else {
            string = "";
        }
        return stringBuilder.append(string).toString();
    }

    @NotNull
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder().append(this.localTime.format(DateTimeFormatter.ofPattern(this.formatterPattern())));
        Object object = this.zoneOffset;
        if (object == null || (object = TimeExtensionsKt.getOffsetHHmm((ZoneOffset)object)) == null) {
            object = "";
        }
        return stringBuilder.append(object).toString();
    }

    public final boolean isDirectlyComparableTo(@NotNull Time other) {
        Intrinsics.checkParameterIsNotNull(other, "other");
        return this.zoneOffset == null && other.zoneOffset == null || this.zoneOffset != null && other.zoneOffset != null;
    }

    public final int naturalOrderCompareTo(@NotNull Time other) {
        int n;
        Intrinsics.checkParameterIsNotNull(other, "other");
        if (this.zoneOffset != null && other.zoneOffset != null) {
            OffsetTime offsetTime = this.getOffsetTime();
            if (offsetTime == null) {
                Intrinsics.throwNpe();
            }
            n = offsetTime.compareTo(other.getOffsetTime());
        } else {
            n = this.zoneOffset == null && other.zoneOffset == null ? this.localTime.compareTo(other.localTime) : (this.zoneOffset == null && other.zoneOffset != null ? -1 : 1);
        }
        return n;
    }

    @NotNull
    public final LocalTime getLocalTime() {
        return this.localTime;
    }

    public final int getPrecision() {
        return this.precision;
    }

    @Nullable
    public final ZoneOffset getZoneOffset() {
        return this.zoneOffset;
    }

    private Time(LocalTime localTime, int precision, ZoneOffset zoneOffset) {
        this.localTime = localTime;
        this.precision = precision;
        this.zoneOffset = zoneOffset;
        if (this.precision < 0 || this.precision > 9) {
            Void void_ = ExceptionsKt.err("Specified precision for TIME should be a non-negative integer between 0 and 9 inclusive", ErrorCode.EVALUATOR_INVALID_PRECISION_FOR_TIME, PropertyMapHelpersKt.propertyValueMapOf(new Pair[0]), false);
            throw null;
        }
    }

    /* synthetic */ Time(LocalTime localTime, int n, ZoneOffset zoneOffset, int n2, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n2 & 4) != 0) {
            zoneOffset = null;
        }
        this(localTime, n, zoneOffset);
    }

    public /* synthetic */ Time(LocalTime localTime, int precision, ZoneOffset zoneOffset, DefaultConstructorMarker $constructor_marker) {
        this(localTime, precision, zoneOffset);
    }

    @NotNull
    public final LocalTime component1() {
        return this.localTime;
    }

    public final int component2() {
        return this.precision;
    }

    @Nullable
    public final ZoneOffset component3() {
        return this.zoneOffset;
    }

    @NotNull
    public final Time copy(@NotNull LocalTime localTime, int precision, @Nullable ZoneOffset zoneOffset) {
        Intrinsics.checkParameterIsNotNull(localTime, "localTime");
        return new Time(localTime, precision, zoneOffset);
    }

    public static /* synthetic */ Time copy$default(Time time, LocalTime localTime, int n, ZoneOffset zoneOffset, int n2, Object object) {
        if ((n2 & 1) != 0) {
            localTime = time.localTime;
        }
        if ((n2 & 2) != 0) {
            n = time.precision;
        }
        if ((n2 & 4) != 0) {
            zoneOffset = time.zoneOffset;
        }
        return time.copy(localTime, n, zoneOffset);
    }

    public int hashCode() {
        LocalTime localTime = this.localTime;
        ZoneOffset zoneOffset = this.zoneOffset;
        return ((localTime != null ? ((Object)localTime).hashCode() : 0) * 31 + Integer.hashCode(this.precision)) * 31 + (zoneOffset != null ? ((Object)zoneOffset).hashCode() : 0);
    }

    public boolean equals(@Nullable Object object) {
        block3: {
            block2: {
                if (this == object) break block2;
                if (!(object instanceof Time)) break block3;
                Time time = (Time)object;
                if (!Intrinsics.areEqual(this.localTime, time.localTime) || this.precision != time.precision || !Intrinsics.areEqual(this.zoneOffset, time.zoneOffset)) break block3;
            }
            return true;
        }
        return false;
    }

    @JvmStatic
    @JvmOverloads
    @NotNull
    public static final Time of(int hour, int minute, int second, int nano, int precision, @Nullable Integer tz_minutes) {
        return Companion.of(hour, minute, second, nano, precision, tz_minutes);
    }

    @JvmStatic
    @JvmOverloads
    @NotNull
    public static final Time of(int hour, int minute, int second, int nano, int precision) {
        return org.partiql.lang.eval.time.Time$Companion.of$default(Companion, hour, minute, second, nano, precision, null, 32, null);
    }

    @JvmStatic
    @JvmOverloads
    @NotNull
    public static final Time of(@NotNull LocalTime localTime, int precision, @Nullable ZoneOffset zoneOffset) {
        return Companion.of(localTime, precision, zoneOffset);
    }

    @JvmStatic
    @JvmOverloads
    @NotNull
    public static final Time of(@NotNull LocalTime localTime, int precision) {
        return org.partiql.lang.eval.time.Time$Companion.of$default(Companion, localTime, precision, null, 4, null);
    }

    @Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J$\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u00042\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\fH\u0007JA\u0010\u0006\u001a\u00020\u00072\u0006\u0010\r\u001a\u00020\u00042\u0006\u0010\u000e\u001a\u00020\u00042\u0006\u0010\u000f\u001a\u00020\u00042\u0006\u0010\u0010\u001a\u00020\u00042\u0006\u0010\n\u001a\u00020\u00042\n\b\u0002\u0010\u0011\u001a\u0004\u0018\u00010\u0004H\u0007\u00a2\u0006\u0002\u0010\u0012R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0013"}, d2={"Lorg/partiql/lang/eval/time/Time$Companion;", "", "()V", "LESS", "", "MORE", "of", "Lorg/partiql/lang/eval/time/Time;", "localTime", "Ljava/time/LocalTime;", "precision", "zoneOffset", "Ljava/time/ZoneOffset;", "hour", "minute", "second", "nano", "tz_minutes", "(IIIIILjava/lang/Integer;)Lorg/partiql/lang/eval/time/Time;", "lang"})
    public static final class Companion {
        @JvmStatic
        @JvmOverloads
        @NotNull
        public final Time of(int hour, int minute, int second, int nano, int precision, @Nullable Integer tz_minutes) {
            ZoneOffset zoneOffset;
            int n;
            boolean bl;
            int n2;
            try {
                ChronoField.HOUR_OF_DAY.checkValidValue(hour);
                ChronoField.MINUTE_OF_HOUR.checkValidValue(minute);
                ChronoField.SECOND_OF_MINUTE.checkValidValue(second);
                ChronoField.NANO_OF_SECOND.checkValidValue(nano);
                Integer n3 = tz_minutes;
                if (n3 != null) {
                    Integer n4 = n3;
                    n2 = 0;
                    bl = false;
                    int it = ((Number)n4).intValue();
                    boolean bl2 = false;
                    ChronoField.OFFSET_SECONDS.checkValidIntValue(it * 60);
                }
            } catch (DateTimeException dte) {
                throw (Throwable)new EvaluationException(dte, ErrorCode.EVALUATOR_TIME_FIELD_OUT_OF_RANGE, PropertyMapHelpersKt.propertyValueMapOf(new Pair[0]), false);
            }
            switch (precision) {
                case 9: {
                    n = nano;
                    break;
                }
                default: {
                    n2 = nano;
                    bl = false;
                    BigDecimal bigDecimal = BigDecimal.valueOf(n2);
                    Intrinsics.checkExpressionValueIsNotNull(bigDecimal, "BigDecimal.valueOf(this.toLong())");
                    n2 = 1000000000;
                    BigDecimal bigDecimal2 = bigDecimal;
                    bl = false;
                    BigDecimal bigDecimal3 = BigDecimal.valueOf(n2);
                    Intrinsics.checkExpressionValueIsNotNull(bigDecimal3, "BigDecimal.valueOf(this.toLong())");
                    BigDecimal bigDecimal4 = bigDecimal3;
                    n2 = 1000000000;
                    bigDecimal2 = bigDecimal2.divide(bigDecimal4).setScale(precision, RoundingMode.HALF_EVEN);
                    bl = false;
                    BigDecimal bigDecimal5 = BigDecimal.valueOf(n2);
                    Intrinsics.checkExpressionValueIsNotNull(bigDecimal5, "BigDecimal.valueOf(this.toLong())");
                    bigDecimal4 = bigDecimal5;
                    n = bigDecimal2.multiply(bigDecimal4).intValue();
                }
            }
            int nanoWithPrecision = n;
            int newNano = nanoWithPrecision % 1000000000;
            int newSecond = second + nanoWithPrecision / 1000000000;
            int newMinute = minute + newSecond / 60;
            int newHour = hour + newMinute / 60;
            LocalTime localTime = LocalTime.of(newHour % 24, newMinute % 60, newSecond % 60, newNano);
            Integer n5 = tz_minutes;
            if (n5 != null) {
                Integer n6 = n5;
                boolean bl3 = false;
                boolean bl4 = false;
                int it = ((Number)n6).intValue();
                boolean bl5 = false;
                zoneOffset = ZoneOffset.ofTotalSeconds(it * 60);
            } else {
                zoneOffset = null;
            }
            ZoneOffset zoneOffset2 = zoneOffset;
            LocalTime localTime2 = localTime;
            Intrinsics.checkExpressionValueIsNotNull(localTime2, "localTime");
            return new Time(localTime2, precision, zoneOffset2, null);
        }

        public static /* synthetic */ Time of$default(Companion companion, int n, int n2, int n3, int n4, int n5, Integer n6, int n7, Object object) {
            if ((n7 & 0x20) != 0) {
                n6 = null;
            }
            return companion.of(n, n2, n3, n4, n5, n6);
        }

        @JvmStatic
        @JvmOverloads
        @NotNull
        public final Time of(int hour, int minute, int second, int nano, int precision) {
            return org.partiql.lang.eval.time.Time$Companion.of$default(this, hour, minute, second, nano, precision, null, 32, null);
        }

        @JvmStatic
        @JvmOverloads
        @NotNull
        public final Time of(@NotNull LocalTime localTime, int precision, @Nullable ZoneOffset zoneOffset) {
            Intrinsics.checkParameterIsNotNull(localTime, "localTime");
            ZoneOffset zoneOffset2 = zoneOffset;
            return Companion.of(localTime.getHour(), localTime.getMinute(), localTime.getSecond(), localTime.getNano(), precision, zoneOffset2 != null ? Integer.valueOf(zoneOffset2.getTotalSeconds() / 60) : null);
        }

        public static /* synthetic */ Time of$default(Companion companion, LocalTime localTime, int n, ZoneOffset zoneOffset, int n2, Object object) {
            if ((n2 & 4) != 0) {
                zoneOffset = null;
            }
            return companion.of(localTime, n, zoneOffset);
        }

        @JvmStatic
        @JvmOverloads
        @NotNull
        public final Time of(@NotNull LocalTime localTime, int precision) {
            return org.partiql.lang.eval.time.Time$Companion.of$default(this, localTime, precision, null, 4, null);
        }

        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

