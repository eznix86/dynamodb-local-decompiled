/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package org.partiql.lang.eval.builtins;

import com.amazon.ion.Timestamp;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.TuplesKt;
import kotlin.TypeCastException;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.partiql.lang.eval.Environment;
import org.partiql.lang.eval.EvaluationException;
import org.partiql.lang.eval.ExceptionsKt;
import org.partiql.lang.eval.ExprValue;
import org.partiql.lang.eval.ExprValueExtensionsKt;
import org.partiql.lang.eval.ExprValueFactory;
import org.partiql.lang.eval.NullPropagatingExprFunction;
import org.partiql.lang.eval.builtins.DateAddExprFunction$WhenMappings;
import org.partiql.lang.syntax.DatePart;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0000\u0018\u0000 \u00132\u00020\u0001:\u0001\u0013B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u001e\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00060\nH\u0016J\u0014\u0010\u000b\u001a\u00020\f*\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0002J\u0014\u0010\u000f\u001a\u00020\u0010*\u00020\f2\u0006\u0010\u0011\u001a\u00020\u0012H\u0002\u00a8\u0006\u0014"}, d2={"Lorg/partiql/lang/eval/builtins/DateAddExprFunction;", "Lorg/partiql/lang/eval/NullPropagatingExprFunction;", "valueFactory", "Lorg/partiql/lang/eval/ExprValueFactory;", "(Lorg/partiql/lang/eval/ExprValueFactory;)V", "eval", "Lorg/partiql/lang/eval/ExprValue;", "env", "Lorg/partiql/lang/eval/Environment;", "args", "", "adjustPrecisionTo", "Lcom/amazon/ion/Timestamp;", "datePart", "Lorg/partiql/lang/syntax/DatePart;", "hasSufficientPrecisionFor", "", "requiredPrecision", "Lcom/amazon/ion/Timestamp$Precision;", "Companion", "lang"})
public final class DateAddExprFunction
extends NullPropagatingExprFunction {
    private static final List<Timestamp.Precision> precisionOrder;
    private static final Map<DatePart, Timestamp.Precision> datePartToPrecision;
    public static final Companion Companion;

    private final boolean hasSufficientPrecisionFor(@NotNull Timestamp $this$hasSufficientPrecisionFor, Timestamp.Precision requiredPrecision) {
        int requiredPrecisionPos = precisionOrder.indexOf((Object)requiredPrecision);
        int precisionPos = precisionOrder.indexOf((Object)$this$hasSufficientPrecisionFor.getPrecision());
        return precisionPos >= requiredPrecisionPos;
    }

    private final Timestamp adjustPrecisionTo(@NotNull Timestamp $this$adjustPrecisionTo, DatePart datePart) {
        Timestamp timestamp;
        Timestamp.Precision requiredPrecision;
        Timestamp.Precision precision = datePartToPrecision.get((Object)datePart);
        if (precision == null) {
            Intrinsics.throwNpe();
        }
        if (this.hasSufficientPrecisionFor($this$adjustPrecisionTo, requiredPrecision = precision)) {
            return $this$adjustPrecisionTo;
        }
        switch (DateAddExprFunction$WhenMappings.$EnumSwitchMapping$0[requiredPrecision.ordinal()]) {
            case 1: {
                Timestamp timestamp2 = Timestamp.forYear($this$adjustPrecisionTo.getYear());
                timestamp = timestamp2;
                Intrinsics.checkExpressionValueIsNotNull(timestamp2, "Timestamp.forYear(this.year)");
                break;
            }
            case 2: {
                Timestamp timestamp3 = Timestamp.forMonth($this$adjustPrecisionTo.getYear(), $this$adjustPrecisionTo.getMonth());
                timestamp = timestamp3;
                Intrinsics.checkExpressionValueIsNotNull(timestamp3, "Timestamp.forMonth(this.year, this.month)");
                break;
            }
            case 3: {
                Timestamp timestamp4 = Timestamp.forDay($this$adjustPrecisionTo.getYear(), $this$adjustPrecisionTo.getMonth(), $this$adjustPrecisionTo.getDay());
                timestamp = timestamp4;
                Intrinsics.checkExpressionValueIsNotNull(timestamp4, "Timestamp.forDay(this.year, this.month, this.day)");
                break;
            }
            case 4: {
                Timestamp timestamp5 = Timestamp.forSecond($this$adjustPrecisionTo.getYear(), $this$adjustPrecisionTo.getMonth(), $this$adjustPrecisionTo.getDay(), $this$adjustPrecisionTo.getHour(), $this$adjustPrecisionTo.getMinute(), $this$adjustPrecisionTo.getSecond(), $this$adjustPrecisionTo.getLocalOffset());
                timestamp = timestamp5;
                Intrinsics.checkExpressionValueIsNotNull(timestamp5, "Timestamp.forSecond(this\u2026        this.localOffset)");
                break;
            }
            case 5: {
                Timestamp timestamp6 = Timestamp.forMinute($this$adjustPrecisionTo.getYear(), $this$adjustPrecisionTo.getMonth(), $this$adjustPrecisionTo.getDay(), $this$adjustPrecisionTo.getHour(), $this$adjustPrecisionTo.getMinute(), $this$adjustPrecisionTo.getLocalOffset());
                timestamp = timestamp6;
                Intrinsics.checkExpressionValueIsNotNull(timestamp6, "Timestamp.forMinute(this\u2026        this.localOffset)");
                break;
            }
            default: {
                String string = datePart.toString();
                StringBuilder stringBuilder = new StringBuilder().append("invalid date part for date_add: ");
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
        return timestamp;
    }

    @Override
    @NotNull
    public ExprValue eval(@NotNull Environment env, @NotNull List<? extends ExprValue> args2) {
        Intrinsics.checkParameterIsNotNull(env, "env");
        Intrinsics.checkParameterIsNotNull(args2, "args");
        DatePart datePart = ExprValueExtensionsKt.datePartValue(args2.get(0));
        int interval = ExprValueExtensionsKt.intValue(args2.get(1));
        Timestamp timestamp = ExprValueExtensionsKt.timestampValue(args2.get(2));
        try {
            Timestamp timestamp2;
            switch (DateAddExprFunction$WhenMappings.$EnumSwitchMapping$1[datePart.ordinal()]) {
                case 1: {
                    timestamp2 = this.adjustPrecisionTo(timestamp, datePart).addYear(interval);
                    break;
                }
                case 2: {
                    timestamp2 = this.adjustPrecisionTo(timestamp, datePart).addMonth(interval);
                    break;
                }
                case 3: {
                    timestamp2 = this.adjustPrecisionTo(timestamp, datePart).addDay(interval);
                    break;
                }
                case 4: {
                    timestamp2 = this.adjustPrecisionTo(timestamp, datePart).addHour(interval);
                    break;
                }
                case 5: {
                    timestamp2 = this.adjustPrecisionTo(timestamp, datePart).addMinute(interval);
                    break;
                }
                case 6: {
                    timestamp2 = this.adjustPrecisionTo(timestamp, datePart).addSecond(interval);
                    break;
                }
                default: {
                    String string = datePart.toString();
                    StringBuilder stringBuilder = new StringBuilder().append("invalid date part for date_add: ");
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
            Timestamp addedTimestamp = timestamp2;
            ExprValueFactory exprValueFactory = this.getValueFactory();
            Timestamp timestamp3 = addedTimestamp;
            Intrinsics.checkExpressionValueIsNotNull(timestamp3, "addedTimestamp");
            return exprValueFactory.newTimestamp(timestamp3);
        } catch (IllegalArgumentException e) {
            throw (Throwable)new EvaluationException(e, null, null, false, 6, null);
        }
    }

    public DateAddExprFunction(@NotNull ExprValueFactory valueFactory) {
        Intrinsics.checkParameterIsNotNull(valueFactory, "valueFactory");
        super("date_add", 3, valueFactory);
    }

    static {
        Companion = new Companion(null);
        precisionOrder = CollectionsKt.listOf(Timestamp.Precision.YEAR, Timestamp.Precision.MONTH, Timestamp.Precision.DAY, Timestamp.Precision.MINUTE, Timestamp.Precision.SECOND);
        datePartToPrecision = MapsKt.mapOf(TuplesKt.to(DatePart.YEAR, Timestamp.Precision.YEAR), TuplesKt.to(DatePart.MONTH, Timestamp.Precision.MONTH), TuplesKt.to(DatePart.DAY, Timestamp.Precision.DAY), TuplesKt.to(DatePart.HOUR, Timestamp.Precision.MINUTE), TuplesKt.to(DatePart.MINUTE, Timestamp.Precision.MINUTE), TuplesKt.to(DatePart.SECOND, Timestamp.Precision.SECOND));
    }

    @Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\"\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u00048\u0002X\u0083\u0004\u00a2\u0006\b\n\u0000\u0012\u0004\b\u0007\u0010\u0002R\u001c\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00060\t8\u0002X\u0083\u0004\u00a2\u0006\b\n\u0000\u0012\u0004\b\n\u0010\u0002\u00a8\u0006\u000b"}, d2={"Lorg/partiql/lang/eval/builtins/DateAddExprFunction$Companion;", "", "()V", "datePartToPrecision", "", "Lorg/partiql/lang/syntax/DatePart;", "Lcom/amazon/ion/Timestamp$Precision;", "datePartToPrecision$annotations", "precisionOrder", "", "precisionOrder$annotations", "lang"})
    public static final class Companion {
        @JvmStatic
        private static /* synthetic */ void precisionOrder$annotations() {
        }

        @JvmStatic
        private static /* synthetic */ void datePartToPrecision$annotations() {
        }

        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

