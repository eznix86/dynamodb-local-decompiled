/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package org.partiql.lang.eval.builtins;

import com.amazon.ion.Timestamp;
import java.math.BigDecimal;
import java.time.DateTimeException;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAccessor;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Pair;
import kotlin.TypeCastException;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.PropertyReference0Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KProperty;
import org.jetbrains.annotations.NotNull;
import org.partiql.lang.errors.ErrorCode;
import org.partiql.lang.errors.Property;
import org.partiql.lang.eval.EvaluationException;
import org.partiql.lang.eval.ExceptionsKt;
import org.partiql.lang.eval.builtins.TimestampParser$Companion$WhenMappings;
import org.partiql.lang.eval.builtins.timestamp.FormatPattern;
import org.partiql.lang.util.PropertyMapHelpersKt;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\b\u0000\u0018\u0000 \u00032\u00020\u0001:\u0001\u0003B\u0005\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0004"}, d2={"Lorg/partiql/lang/eval/builtins/TimestampParser;", "", "()V", "Companion", "lang"})
public final class TimestampParser {
    private static final int TWO_DIGIT_PIVOT_YEAR = 70;
    public static final Companion Companion = new Companion(null);

    static {
        TWO_DIGIT_PIVOT_YEAR = 70;
    }

    @Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0016\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\nJ\u0013\u0010\f\u001a\u0004\u0018\u00010\u0004*\u00020\rH\u0002\u00a2\u0006\u0002\u0010\u000eR\u0014\u0010\u0003\u001a\u00020\u0004X\u0086D\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006\u000f\u00b2\u0006\n\u0010\u0010\u001a\u00020\rX\u008a\u0084\u0002\u00b2\u0006\n\u0010\u0011\u001a\u00020\u0004X\u008a\u0084\u0002"}, d2={"Lorg/partiql/lang/eval/builtins/TimestampParser$Companion;", "", "()V", "TWO_DIGIT_PIVOT_YEAR", "", "getTWO_DIGIT_PIVOT_YEAR", "()I", "parseTimestamp", "Lcom/amazon/ion/Timestamp;", "timestampString", "", "formatPattern", "getLocalOffset", "Ljava/time/temporal/TemporalAccessor;", "(Ljava/time/temporal/TemporalAccessor;)Ljava/lang/Integer;", "lang", "accessor", "year"})
    public static final class Companion {
        static final /* synthetic */ KProperty[] $$delegatedProperties;

        static {
            $$delegatedProperties = new KProperty[]{Reflection.property0(new PropertyReference0Impl(Reflection.getOrCreateKotlinClass(Companion.class), "accessor", "<v#0>")), Reflection.property0(new PropertyReference0Impl(Reflection.getOrCreateKotlinClass(Companion.class), "year", "<v#1>"))};
        }

        public final int getTWO_DIGIT_PIVOT_YEAR() {
            return TWO_DIGIT_PIVOT_YEAR;
        }

        private final Integer getLocalOffset(@NotNull TemporalAccessor $this$getLocalOffset) {
            Integer n;
            if (!$this$getLocalOffset.isSupported(ChronoField.OFFSET_SECONDS)) {
                n = null;
            } else {
                int offsetSeconds = $this$getLocalOffset.get(ChronoField.OFFSET_SECONDS);
                if (offsetSeconds % 60 != 0) {
                    throw (Throwable)new EvaluationException("The parsed timestamp has a UTC offset that not a multiple of 1 minute. This timestamp cannot be parsed accurately because the maximum resolution for an Ion timestamp offset is 1 minute.", ErrorCode.EVALUATOR_PRECISION_LOSS_WHEN_PARSING_TIMESTAMP, null, null, false, 12, null);
                }
                n = offsetSeconds / 60;
            }
            return n;
        }

        /*
         * Unable to fully structure code
         * Could not resolve type clashes
         */
        @NotNull
        public final Timestamp parseTimestamp(@NotNull String timestampString, @NotNull String formatPattern) {
            Intrinsics.checkParameterIsNotNull(timestampString, "timestampString");
            Intrinsics.checkParameterIsNotNull(formatPattern, "formatPattern");
            pattern = FormatPattern.Companion.fromString(formatPattern);
            pattern.validateForTimestampParsing();
            var5_4 = org.partiql.lang.eval.builtins.TimestampParser$Companion.$$delegatedProperties[0];
            accessor = LazyKt.lazy((Function0)new Function0<TemporalAccessor>(pattern, timestampString){
                final /* synthetic */ FormatPattern $pattern;
                final /* synthetic */ String $timestampString;

                public final TemporalAccessor invoke() {
                    TemporalAccessor temporalAccessor;
                    try {
                        temporalAccessor = new DateTimeFormatterBuilder().parseCaseInsensitive().appendPattern(this.$pattern.getFormatPatternString()).toFormatter().parse(this.$timestampString);
                    } catch (IllegalArgumentException ex) {
                        throw (Throwable)new EvaluationException(ex, ErrorCode.EVALUATOR_INVALID_TIMESTAMP_FORMAT_PATTERN, null, false, 4, null);
                    }
                    return temporalAccessor;
                }
                {
                    this.$pattern = formatPattern;
                    this.$timestampString = string;
                    super(0);
                }
            });
            var7_6 = org.partiql.lang.eval.builtins.TimestampParser$Companion.$$delegatedProperties[1];
            year = LazyKt.lazy((Function0)new Function0<Integer>(accessor, var5_4, pattern){
                final /* synthetic */ Lazy $accessor;
                final /* synthetic */ KProperty $accessor$metadata;
                final /* synthetic */ FormatPattern $pattern;

                public final int invoke() {
                    Lazy lazy = this.$accessor;
                    Object var3_2 = null;
                    KProperty kProperty = this.$accessor$metadata;
                    boolean bl = false;
                    int year2 = ((TemporalAccessor)lazy.getValue()).get(ChronoField.YEAR);
                    return !this.$pattern.getHas2DigitYear() || year2 < TimestampParser.Companion.getTWO_DIGIT_PIVOT_YEAR() + 2000 ? year2 : year2 - 100;
                }
                {
                    this.$accessor = lazy;
                    this.$accessor$metadata = kProperty;
                    this.$pattern = formatPattern;
                    super(0);
                }
            });
            try {
                v0 = pattern.getLeastSignificantField();
                if (v0 == null) ** GOTO lbl-1000
                switch (TimestampParser$Companion$WhenMappings.$EnumSwitchMapping$0[v0.ordinal()]) {
                    case 1: {
                        var9_8 = accessor;
                        var10_11 = null;
                        var11_18 = false;
                        nanoSeconds = BigDecimal.valueOf(((TemporalAccessor)var9_8.getValue()).getLong(ChronoField.NANO_OF_SECOND));
                        secondsFraction = nanoSeconds.scaleByPowerOfTen(-9).stripTrailingZeros();
                        var10_11 = year;
                        var11_19 = null;
                        var12_21 = false;
                        v1 = ((Number)var10_11.getValue()).intValue();
                        var10_11 = accessor;
                        var11_19 = null;
                        var13_22 = v1;
                        var12_21 = false;
                        var14_23 = var10_11.getValue();
                        var10_11 = accessor;
                        var11_19 = null;
                        var14_24 = ((TemporalAccessor)var14_23).get(ChronoField.MONTH_OF_YEAR);
                        var12_21 = false;
                        var15_34 = var10_11.getValue();
                        var10_11 = accessor;
                        var11_19 = null;
                        var15_35 = ((TemporalAccessor)var15_34).get(ChronoField.DAY_OF_MONTH);
                        var12_21 = false;
                        var16_43 = var10_11.getValue();
                        var10_11 = accessor;
                        var11_19 = null;
                        var16_44 = ((TemporalAccessor)var16_43).get(ChronoField.HOUR_OF_DAY);
                        var12_21 = false;
                        var17_48 = var10_11.getValue();
                        var10_11 = accessor;
                        var11_19 = null;
                        var17_49 = ((TemporalAccessor)var17_48).get(ChronoField.MINUTE_OF_HOUR);
                        var12_21 = false;
                        var18_52 /* !! */  = var10_11.getValue();
                        v2 = BigDecimal.valueOf(((TemporalAccessor)var18_52 /* !! */ ).getLong(ChronoField.SECOND_OF_MINUTE)).add(secondsFraction);
                        if (v2 == null) {
                            throw new TypeCastException("null cannot be cast to non-null type java.math.BigDecimal");
                        }
                        var10_11 = accessor;
                        var11_19 = null;
                        var19_57 = this;
                        var18_52 /* !! */  = v2;
                        var12_21 = false;
                        var20_58 = var10_11.getValue();
                        v3 = Timestamp.forSecond(var13_22, var14_24, var15_35, var16_44, var17_49, var18_52 /* !! */ , super.getLocalOffset((TemporalAccessor)var20_58));
                        v4 = v3;
                        Intrinsics.checkExpressionValueIsNotNull(v3, "Timestamp.forSecond(year\u2026ccessor.getLocalOffset())");
                        break;
                    }
                    case 2: {
                        var8_20 = year;
                        secondsFraction = null;
                        var10_12 = false;
                        v5 = ((Number)var8_20.getValue()).intValue();
                        var8_20 = accessor;
                        secondsFraction = null;
                        var13_22 = v5;
                        var10_12 = false;
                        var14_25 = var8_20.getValue();
                        var8_20 = accessor;
                        secondsFraction = null;
                        var14_26 = ((TemporalAccessor)var14_25).get(ChronoField.MONTH_OF_YEAR);
                        var10_12 = false;
                        var15_36 = var8_20.getValue();
                        var8_20 = accessor;
                        secondsFraction = null;
                        var15_37 = ((TemporalAccessor)var15_36).get(ChronoField.DAY_OF_MONTH);
                        var10_12 = false;
                        var16_45 = var8_20.getValue();
                        var8_20 = accessor;
                        secondsFraction = null;
                        var16_44 = ((TemporalAccessor)var16_45).get(ChronoField.HOUR_OF_DAY);
                        var10_12 = false;
                        var17_50 = var8_20.getValue();
                        var8_20 = accessor;
                        secondsFraction = null;
                        var17_49 = ((TemporalAccessor)var17_50).get(ChronoField.MINUTE_OF_HOUR);
                        var10_12 = false;
                        var18_53 = var8_20.getValue();
                        var8_20 = accessor;
                        secondsFraction = null;
                        var19_57 = this;
                        var18_54 = ((TemporalAccessor)var18_53).get(ChronoField.SECOND_OF_MINUTE);
                        var10_12 = false;
                        var20_58 = var8_20.getValue();
                        v6 = Timestamp.forSecond(var13_22, var14_26, var15_37, var16_44, var17_49, var18_54, super.getLocalOffset((TemporalAccessor)var20_58));
                        v4 = v6;
                        Intrinsics.checkExpressionValueIsNotNull(v6, "Timestamp.forSecond(year\u2026ccessor.getLocalOffset())");
                        break;
                    }
                    case 3: {
                        var8_20 = year;
                        secondsFraction = null;
                        var10_13 = false;
                        v7 = ((Number)var8_20.getValue()).intValue();
                        var8_20 = accessor;
                        secondsFraction = null;
                        var13_22 = v7;
                        var10_13 = false;
                        var14_27 = var8_20.getValue();
                        var8_20 = accessor;
                        secondsFraction = null;
                        var14_28 = ((TemporalAccessor)var14_27).get(ChronoField.MONTH_OF_YEAR);
                        var10_13 = false;
                        var15_38 = var8_20.getValue();
                        var8_20 = accessor;
                        secondsFraction = null;
                        var15_39 = ((TemporalAccessor)var15_38).get(ChronoField.DAY_OF_MONTH);
                        var10_13 = false;
                        var16_46 = var8_20.getValue();
                        var8_20 = accessor;
                        secondsFraction = null;
                        var16_44 = ((TemporalAccessor)var16_46).get(ChronoField.HOUR_OF_DAY);
                        var10_13 = false;
                        var17_51 = var8_20.getValue();
                        var8_20 = accessor;
                        secondsFraction = null;
                        var18_55 = this;
                        var17_49 = ((TemporalAccessor)var17_51).get(ChronoField.MINUTE_OF_HOUR);
                        var10_13 = false;
                        var19_57 = var8_20.getValue();
                        v8 = Timestamp.forMinute(var13_22, var14_28, var15_39, var16_44, var17_49, var18_55.getLocalOffset((TemporalAccessor)var19_57));
                        v4 = v8;
                        Intrinsics.checkExpressionValueIsNotNull(v8, "Timestamp.forMinute(year\u2026ccessor.getLocalOffset())");
                        break;
                    }
                    case 4: {
                        var8_20 = year;
                        secondsFraction = null;
                        var10_14 = false;
                        v9 = ((Number)var8_20.getValue()).intValue();
                        var8_20 = accessor;
                        secondsFraction = null;
                        var13_22 = v9;
                        var10_14 = false;
                        var14_29 = var8_20.getValue();
                        var8_20 = accessor;
                        secondsFraction = null;
                        var14_30 = ((TemporalAccessor)var14_29).get(ChronoField.MONTH_OF_YEAR);
                        var10_14 = false;
                        var15_40 = var8_20.getValue();
                        var8_20 = accessor;
                        secondsFraction = null;
                        var15_41 = ((TemporalAccessor)var15_40).get(ChronoField.DAY_OF_MONTH);
                        var10_14 = false;
                        var16_47 = var8_20.getValue();
                        var8_20 = accessor;
                        secondsFraction = null;
                        var18_56 = this;
                        var17_49 = 0;
                        var16_44 = ((TemporalAccessor)var16_47).get(ChronoField.HOUR_OF_DAY);
                        var10_14 = false;
                        var19_57 = var8_20.getValue();
                        v10 = Timestamp.forMinute(var13_22, var14_30, var15_41, var16_44, var17_49, var18_56.getLocalOffset((TemporalAccessor)var19_57));
                        v4 = v10;
                        Intrinsics.checkExpressionValueIsNotNull(v10, "Timestamp.forMinute(year\u2026ccessor.getLocalOffset())");
                        break;
                    }
                    case 5: {
                        var8_20 = year;
                        secondsFraction = null;
                        var10_15 = false;
                        v11 = ((Number)var8_20.getValue()).intValue();
                        var8_20 = accessor;
                        secondsFraction = null;
                        var13_22 = v11;
                        var10_15 = false;
                        var14_31 = var8_20.getValue();
                        var8_20 = accessor;
                        secondsFraction = null;
                        var14_32 = ((TemporalAccessor)var14_31).get(ChronoField.MONTH_OF_YEAR);
                        var10_15 = false;
                        var15_42 = var8_20.getValue();
                        v12 = Timestamp.forDay(var13_22, var14_32, ((TemporalAccessor)var15_42).get(ChronoField.DAY_OF_MONTH));
                        v4 = v12;
                        Intrinsics.checkExpressionValueIsNotNull(v12, "Timestamp.forDay(year,\n \u2026hronoField.DAY_OF_MONTH))");
                        break;
                    }
                    case 6: {
                        var8_20 = year;
                        secondsFraction = null;
                        var10_16 = false;
                        v13 = ((Number)var8_20.getValue()).intValue();
                        var8_20 = accessor;
                        secondsFraction = null;
                        var13_22 = v13;
                        var10_16 = false;
                        var14_33 = var8_20.getValue();
                        v14 = Timestamp.forMonth(var13_22, ((TemporalAccessor)var14_33).get(ChronoField.MONTH_OF_YEAR));
                        v4 = v14;
                        Intrinsics.checkExpressionValueIsNotNull(v14, "Timestamp.forMonth(year,\u2026ronoField.MONTH_OF_YEAR))");
                        break;
                    }
                    case 7: {
                        var8_20 = year;
                        secondsFraction = null;
                        var10_17 = false;
                        v15 = Timestamp.forYear(((Number)var8_20.getValue()).intValue());
                        v4 = v15;
                        Intrinsics.checkExpressionValueIsNotNull(v15, "Timestamp.forYear(year)");
                        break;
                    }
                    case 8: 
                    case 9: lbl-1000:
                    // 2 sources

                    {
                        v16 = ExceptionsKt.errNoContext("This code should be unreachable because AM_PM or OFFSET or nullshould never the value of formatPattern.leastSignificantField by at this point", true);
                        throw null;
                    }
                    default: {
                        throw new NoWhenBranchMatchedException();
                    }
                }
                var8_20 = v4;
            } catch (IllegalArgumentException ex) {
                throw (Throwable)new EvaluationException(ex, ErrorCode.EVALUATOR_CUSTOM_TIMESTAMP_PARSE_FAILURE, PropertyMapHelpersKt.propertyValueMapOf(new Pair[]{PropertyMapHelpersKt.to(Property.TIMESTAMP_FORMAT_PATTERN, formatPattern)}), false);
            } catch (DateTimeException ex) {
                throw (Throwable)new EvaluationException(ex, ErrorCode.EVALUATOR_CUSTOM_TIMESTAMP_PARSE_FAILURE, PropertyMapHelpersKt.propertyValueMapOf(new Pair[]{PropertyMapHelpersKt.to(Property.TIMESTAMP_FORMAT_PATTERN, formatPattern)}), false);
            }
            return var8_20;
        }

        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

