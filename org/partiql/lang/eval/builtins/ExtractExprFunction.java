/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package org.partiql.lang.eval.builtins;

import com.amazon.ion.Timestamp;
import java.time.LocalDate;
import java.util.List;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.partiql.lang.eval.Environment;
import org.partiql.lang.eval.ExceptionsKt;
import org.partiql.lang.eval.ExprValue;
import org.partiql.lang.eval.ExprValueExtensionsKt;
import org.partiql.lang.eval.ExprValueFactory;
import org.partiql.lang.eval.NullPropagatingExprFunction;
import org.partiql.lang.eval.builtins.ExtractExprFunction$WhenMappings;
import org.partiql.lang.eval.time.Time;
import org.partiql.lang.syntax.DatePart;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000F\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0010\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\b\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u001e\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00060\nH\u0016J\u001e\u0010\u000b\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00060\nH\u0016J\u0014\u0010\f\u001a\u00020\r*\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H\u0002J\u0014\u0010\f\u001a\u00020\r*\u00020\u00112\u0006\u0010\u000f\u001a\u00020\u0010H\u0002J\u0014\u0010\f\u001a\u00020\r*\u00020\u00122\u0006\u0010\u000f\u001a\u00020\u0010H\u0002J\f\u0010\u0013\u001a\u00020\u0014*\u00020\u000eH\u0002J\f\u0010\u0015\u001a\u00020\u0014*\u00020\u000eH\u0002\u00a8\u0006\u0016"}, d2={"Lorg/partiql/lang/eval/builtins/ExtractExprFunction;", "Lorg/partiql/lang/eval/NullPropagatingExprFunction;", "valueFactory", "Lorg/partiql/lang/eval/ExprValueFactory;", "(Lorg/partiql/lang/eval/ExprValueFactory;)V", "call", "Lorg/partiql/lang/eval/ExprValue;", "env", "Lorg/partiql/lang/eval/Environment;", "args", "", "eval", "extractedValue", "", "Lcom/amazon/ion/Timestamp;", "datePart", "Lorg/partiql/lang/syntax/DatePart;", "Ljava/time/LocalDate;", "Lorg/partiql/lang/eval/time/Time;", "hourOffset", "", "minuteOffset", "lang"})
public final class ExtractExprFunction
extends NullPropagatingExprFunction {
    private final int hourOffset(@NotNull Timestamp $this$hourOffset) {
        Integer n = $this$hourOffset.getLocalOffset();
        return (n != null ? n : 0) / 60;
    }

    private final int minuteOffset(@NotNull Timestamp $this$minuteOffset) {
        Integer n = $this$minuteOffset.getLocalOffset();
        return (n != null ? n : 0) % 60;
    }

    private final double extractedValue(@NotNull Timestamp $this$extractedValue, DatePart datePart) {
        int n;
        switch (ExtractExprFunction$WhenMappings.$EnumSwitchMapping$0[datePart.ordinal()]) {
            case 1: {
                n = $this$extractedValue.getYear();
                break;
            }
            case 2: {
                n = $this$extractedValue.getMonth();
                break;
            }
            case 3: {
                n = $this$extractedValue.getDay();
                break;
            }
            case 4: {
                n = $this$extractedValue.getHour();
                break;
            }
            case 5: {
                n = $this$extractedValue.getMinute();
                break;
            }
            case 6: {
                n = $this$extractedValue.getSecond();
                break;
            }
            case 7: {
                n = this.hourOffset($this$extractedValue);
                break;
            }
            case 8: {
                n = this.minuteOffset($this$extractedValue);
                break;
            }
            default: {
                throw new NoWhenBranchMatchedException();
            }
        }
        return n;
    }

    private final double extractedValue(@NotNull LocalDate $this$extractedValue, DatePart datePart) {
        int n;
        switch (ExtractExprFunction$WhenMappings.$EnumSwitchMapping$1[datePart.ordinal()]) {
            case 1: {
                n = $this$extractedValue.getYear();
                break;
            }
            case 2: {
                n = $this$extractedValue.getMonthValue();
                break;
            }
            case 3: {
                n = $this$extractedValue.getDayOfMonth();
                break;
            }
            case 4: 
            case 5: {
                String string = datePart.name();
                StringBuilder stringBuilder = new StringBuilder().append("Timestamp unit ");
                boolean bl = false;
                String string2 = string;
                if (string2 == null) {
                    throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                }
                String string3 = string2.toLowerCase();
                Intrinsics.checkExpressionValueIsNotNull(string3, "(this as java.lang.String).toLowerCase()");
                String string4 = string3;
                Void void_ = ExceptionsKt.errNoContext(stringBuilder.append(string4).append(" not supported for DATE type").toString(), false);
                throw null;
            }
            case 6: 
            case 7: 
            case 8: {
                n = 0;
                break;
            }
            default: {
                throw new NoWhenBranchMatchedException();
            }
        }
        return n;
    }

    private final double extractedValue(@NotNull Time $this$extractedValue, DatePart datePart) {
        double d;
        switch (ExtractExprFunction$WhenMappings.$EnumSwitchMapping$2[datePart.ordinal()]) {
            case 1: {
                d = $this$extractedValue.getLocalTime().getHour();
                break;
            }
            case 2: {
                d = $this$extractedValue.getLocalTime().getMinute();
                break;
            }
            case 3: {
                d = $this$extractedValue.getSecondsWithFractionalPart().doubleValue();
                break;
            }
            case 4: {
                Integer n = $this$extractedValue.getTimezoneHour();
                if (n != null) {
                    d = n.intValue();
                    break;
                }
                String string = datePart.name();
                StringBuilder stringBuilder = new StringBuilder().append("Time unit ");
                boolean bl = false;
                String string2 = string;
                if (string2 == null) {
                    throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                }
                String string3 = string2.toLowerCase();
                Intrinsics.checkExpressionValueIsNotNull(string3, "(this as java.lang.String).toLowerCase()");
                String string4 = string3;
                Void void_ = ExceptionsKt.errNoContext(stringBuilder.append(string4).append(" not supported for TIME type without TIME ZONE").toString(), false);
                throw null;
            }
            case 5: {
                Integer n = $this$extractedValue.getTimezoneMinute();
                if (n != null) {
                    d = n.intValue();
                    break;
                }
                String string = datePart.name();
                StringBuilder stringBuilder = new StringBuilder().append("Time unit ");
                boolean bl = false;
                String string5 = string;
                if (string5 == null) {
                    throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                }
                String string6 = string5.toLowerCase();
                Intrinsics.checkExpressionValueIsNotNull(string6, "(this as java.lang.String).toLowerCase()");
                String string7 = string6;
                Void void_ = ExceptionsKt.errNoContext(stringBuilder.append(string7).append(" not supported for TIME type without TIME ZONE").toString(), false);
                throw null;
            }
            case 6: 
            case 7: 
            case 8: {
                String string = datePart.name();
                StringBuilder stringBuilder = new StringBuilder().append("Time unit ");
                boolean bl = false;
                String string8 = string;
                if (string8 == null) {
                    throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                }
                String string9 = string8.toLowerCase();
                Intrinsics.checkExpressionValueIsNotNull(string9, "(this as java.lang.String).toLowerCase()");
                String string10 = string9;
                Void void_ = ExceptionsKt.errNoContext(stringBuilder.append(string10).append(" not supported for TIME type.").toString(), false);
                throw null;
            }
            default: {
                throw new NoWhenBranchMatchedException();
            }
        }
        return d;
    }

    @Override
    @NotNull
    public ExprValue eval(@NotNull Environment env, @NotNull List<? extends ExprValue> args2) {
        double d;
        Intrinsics.checkParameterIsNotNull(env, "env");
        Intrinsics.checkParameterIsNotNull(args2, "args");
        DatePart datePart = ExprValueExtensionsKt.datePartValue(args2.get(0));
        switch (ExtractExprFunction$WhenMappings.$EnumSwitchMapping$3[args2.get(1).getType().ordinal()]) {
            case 1: {
                d = this.extractedValue(ExprValueExtensionsKt.timestampValue(args2.get(1)), datePart);
                break;
            }
            case 2: {
                d = this.extractedValue(ExprValueExtensionsKt.dateValue(args2.get(1)), datePart);
                break;
            }
            case 3: {
                d = this.extractedValue(ExprValueExtensionsKt.timeValue(args2.get(1)), datePart);
                break;
            }
            default: {
                Void void_ = ExceptionsKt.errNoContext("Expected date or timestamp: " + args2.get(1), false);
                throw null;
            }
        }
        double extractedValue = d;
        return this.getValueFactory().newFloat(extractedValue);
    }

    @Override
    @NotNull
    public ExprValue call(@NotNull Environment env, @NotNull List<? extends ExprValue> args2) {
        Intrinsics.checkParameterIsNotNull(env, "env");
        Intrinsics.checkParameterIsNotNull(args2, "args");
        this.checkArity(args2);
        return ExprValueExtensionsKt.isUnknown(args2.get(1)) ? this.getValueFactory().getNullValue() : this.eval(env, args2);
    }

    public ExtractExprFunction(@NotNull ExprValueFactory valueFactory) {
        Intrinsics.checkParameterIsNotNull(valueFactory, "valueFactory");
        super("extract", 2, valueFactory);
    }
}

