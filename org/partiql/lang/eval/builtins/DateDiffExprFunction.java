/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package org.partiql.lang.eval.builtins;

import com.amazon.ion.Timestamp;
import java.time.Duration;
import java.time.OffsetDateTime;
import java.time.Period;
import java.time.ZoneOffset;
import java.util.List;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.partiql.lang.eval.Environment;
import org.partiql.lang.eval.ExceptionsKt;
import org.partiql.lang.eval.ExprValue;
import org.partiql.lang.eval.ExprValueExtensionsKt;
import org.partiql.lang.eval.ExprValueFactory;
import org.partiql.lang.eval.NullPropagatingExprFunction;
import org.partiql.lang.eval.builtins.DateDiffExprFunction$WhenMappings;
import org.partiql.lang.syntax.DatePart;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0004\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\b\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0018\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\bH\u0002J\u001e\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u000b0\u000fH\u0016J\u0018\u0010\u0010\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\bH\u0002J\u0018\u0010\u0011\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\bH\u0002J\u0018\u0010\u0012\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\bH\u0002J\u0018\u0010\u0013\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\bH\u0002J\u0018\u0010\u0014\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\bH\u0002J\u0014\u0010\u0015\u001a\n \u0016*\u0004\u0018\u00010\b0\b*\u00020\u0017H\u0002\u00a8\u0006\u0018"}, d2={"Lorg/partiql/lang/eval/builtins/DateDiffExprFunction;", "Lorg/partiql/lang/eval/NullPropagatingExprFunction;", "valueFactory", "Lorg/partiql/lang/eval/ExprValueFactory;", "(Lorg/partiql/lang/eval/ExprValueFactory;)V", "daysSince", "", "left", "Ljava/time/OffsetDateTime;", "right", "eval", "Lorg/partiql/lang/eval/ExprValue;", "env", "Lorg/partiql/lang/eval/Environment;", "args", "", "hoursSince", "minutesSince", "monthsSince", "secondsSince", "yearsSince", "toJava", "kotlin.jvm.PlatformType", "Lcom/amazon/ion/Timestamp;", "lang"})
public final class DateDiffExprFunction
extends NullPropagatingExprFunction {
    private final OffsetDateTime toJava(@NotNull Timestamp $this$toJava) {
        Integer n = $this$toJava.getLocalOffset();
        return OffsetDateTime.of($this$toJava.getYear(), $this$toJava.getMonth(), $this$toJava.getDay(), $this$toJava.getHour(), $this$toJava.getMinute(), $this$toJava.getSecond(), 0, ZoneOffset.ofTotalSeconds((n != null ? n : 0) * 60));
    }

    private final Number yearsSince(OffsetDateTime left, OffsetDateTime right) {
        Period period = Period.between(left.toLocalDate(), right.toLocalDate());
        Intrinsics.checkExpressionValueIsNotNull(period, "Period.between(left.toLo\u2026e(), right.toLocalDate())");
        return period.getYears();
    }

    private final Number monthsSince(OffsetDateTime left, OffsetDateTime right) {
        return Period.between(left.toLocalDate(), right.toLocalDate()).toTotalMonths();
    }

    private final Number daysSince(OffsetDateTime left, OffsetDateTime right) {
        return Duration.between(left, right).toDays();
    }

    private final Number hoursSince(OffsetDateTime left, OffsetDateTime right) {
        return Duration.between(left, right).toHours();
    }

    private final Number minutesSince(OffsetDateTime left, OffsetDateTime right) {
        return Duration.between(left, right).toMinutes();
    }

    private final Number secondsSince(OffsetDateTime left, OffsetDateTime right) {
        return Duration.between(left, right).toMillis() / (long)1000;
    }

    @Override
    @NotNull
    public ExprValue eval(@NotNull Environment env, @NotNull List<? extends ExprValue> args2) {
        Number number;
        Intrinsics.checkParameterIsNotNull(env, "env");
        Intrinsics.checkParameterIsNotNull(args2, "args");
        DatePart datePart = ExprValueExtensionsKt.datePartValue(args2.get(0));
        Timestamp left = ExprValueExtensionsKt.timestampValue(args2.get(1));
        Timestamp right = ExprValueExtensionsKt.timestampValue(args2.get(2));
        OffsetDateTime leftAsJava = this.toJava(left);
        OffsetDateTime rightAsJava = this.toJava(right);
        switch (DateDiffExprFunction$WhenMappings.$EnumSwitchMapping$0[datePart.ordinal()]) {
            case 1: {
                OffsetDateTime offsetDateTime = leftAsJava;
                Intrinsics.checkExpressionValueIsNotNull(offsetDateTime, "leftAsJava");
                OffsetDateTime offsetDateTime2 = rightAsJava;
                Intrinsics.checkExpressionValueIsNotNull(offsetDateTime2, "rightAsJava");
                number = this.yearsSince(offsetDateTime, offsetDateTime2);
                break;
            }
            case 2: {
                OffsetDateTime offsetDateTime = leftAsJava;
                Intrinsics.checkExpressionValueIsNotNull(offsetDateTime, "leftAsJava");
                OffsetDateTime offsetDateTime3 = rightAsJava;
                Intrinsics.checkExpressionValueIsNotNull(offsetDateTime3, "rightAsJava");
                number = this.monthsSince(offsetDateTime, offsetDateTime3);
                break;
            }
            case 3: {
                OffsetDateTime offsetDateTime = leftAsJava;
                Intrinsics.checkExpressionValueIsNotNull(offsetDateTime, "leftAsJava");
                OffsetDateTime offsetDateTime4 = rightAsJava;
                Intrinsics.checkExpressionValueIsNotNull(offsetDateTime4, "rightAsJava");
                number = this.daysSince(offsetDateTime, offsetDateTime4);
                break;
            }
            case 4: {
                OffsetDateTime offsetDateTime = leftAsJava;
                Intrinsics.checkExpressionValueIsNotNull(offsetDateTime, "leftAsJava");
                OffsetDateTime offsetDateTime5 = rightAsJava;
                Intrinsics.checkExpressionValueIsNotNull(offsetDateTime5, "rightAsJava");
                number = this.hoursSince(offsetDateTime, offsetDateTime5);
                break;
            }
            case 5: {
                OffsetDateTime offsetDateTime = leftAsJava;
                Intrinsics.checkExpressionValueIsNotNull(offsetDateTime, "leftAsJava");
                OffsetDateTime offsetDateTime6 = rightAsJava;
                Intrinsics.checkExpressionValueIsNotNull(offsetDateTime6, "rightAsJava");
                number = this.minutesSince(offsetDateTime, offsetDateTime6);
                break;
            }
            case 6: {
                OffsetDateTime offsetDateTime = leftAsJava;
                Intrinsics.checkExpressionValueIsNotNull(offsetDateTime, "leftAsJava");
                OffsetDateTime offsetDateTime7 = rightAsJava;
                Intrinsics.checkExpressionValueIsNotNull(offsetDateTime7, "rightAsJava");
                number = this.secondsSince(offsetDateTime, offsetDateTime7);
                break;
            }
            default: {
                String string = datePart.toString();
                StringBuilder stringBuilder = new StringBuilder().append("invalid date part for date_diff: ");
                boolean bl = false;
                String string2 = string;
                if (string2 == null) {
                    throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                }
                String string3 = string2.toLowerCase();
                Intrinsics.checkExpressionValueIsNotNull(string3, "(this as java.lang.String).toLowerCase()");
                String string4 = string3;
                Void void_ = ExceptionsKt.errNoContext(stringBuilder.append(string4).toString(), false);
                throw null;
            }
        }
        Number difference = number;
        return this.getValueFactory().newInt(difference.longValue());
    }

    public DateDiffExprFunction(@NotNull ExprValueFactory valueFactory) {
        Intrinsics.checkParameterIsNotNull(valueFactory, "valueFactory");
        super("date_diff", 3, valueFactory);
    }
}

