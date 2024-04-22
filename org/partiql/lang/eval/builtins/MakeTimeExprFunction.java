/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package org.partiql.lang.eval.builtins;

import java.math.BigDecimal;
import java.util.List;
import kotlin.Metadata;
import kotlin.TuplesKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.IntRange;
import org.jetbrains.annotations.NotNull;
import org.partiql.lang.errors.ErrorCode;
import org.partiql.lang.errors.Property;
import org.partiql.lang.eval.Environment;
import org.partiql.lang.eval.EvaluationException;
import org.partiql.lang.eval.ExceptionsKt;
import org.partiql.lang.eval.ExprValue;
import org.partiql.lang.eval.ExprValueExtensionsKt;
import org.partiql.lang.eval.ExprValueFactory;
import org.partiql.lang.eval.ExprValueType;
import org.partiql.lang.eval.NullPropagatingExprFunction;
import org.partiql.lang.eval.time.Time;
import org.partiql.lang.util.PropertyMapHelpersKt;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u001e\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00060\nH\u0016J\u0014\u0010\u000b\u001a\u00020\f*\u00020\u00062\u0006\u0010\r\u001a\u00020\u000eH\u0002\u00a8\u0006\u000f"}, d2={"Lorg/partiql/lang/eval/builtins/MakeTimeExprFunction;", "Lorg/partiql/lang/eval/NullPropagatingExprFunction;", "valueFactory", "Lorg/partiql/lang/eval/ExprValueFactory;", "(Lorg/partiql/lang/eval/ExprValueFactory;)V", "eval", "Lorg/partiql/lang/eval/ExprValue;", "env", "Lorg/partiql/lang/eval/Environment;", "args", "", "validateType", "", "exprValueType", "Lorg/partiql/lang/eval/ExprValueType;", "lang"})
public final class MakeTimeExprFunction
extends NullPropagatingExprFunction {
    private final void validateType(@NotNull ExprValue $this$validateType, ExprValueType exprValueType) {
        if ($this$validateType.getType() != exprValueType) {
            Void void_ = ExceptionsKt.err("Invalid argument type for make_time", ErrorCode.EVALUATOR_INCORRECT_TYPE_OF_ARGUMENTS_TO_FUNC_CALL, PropertyMapHelpersKt.propertyValueMapOf(TuplesKt.to(Property.EXPECTED_ARGUMENT_TYPES, exprValueType.name()), TuplesKt.to(Property.FUNCTION_NAME, "make_time"), TuplesKt.to(Property.ACTUAL_ARGUMENT_TYPES, $this$validateType.getType().name())), false);
            throw null;
        }
    }

    @Override
    @NotNull
    public ExprValue eval(@NotNull Environment env, @NotNull List<? extends ExprValue> args2) {
        Integer n;
        Intrinsics.checkParameterIsNotNull(env, "env");
        Intrinsics.checkParameterIsNotNull(args2, "args");
        ExprValue exprValue2 = args2.get(0);
        boolean bl = false;
        boolean bl2 = false;
        ExprValue it222 = exprValue2;
        boolean bl3 = false;
        this.validateType(it222, ExprValueType.INT);
        int hour = ExprValueExtensionsKt.intValue(it222);
        ExprValue exprValue3 = args2.get(1);
        bl2 = false;
        boolean it222 = false;
        ExprValue it = exprValue3;
        boolean bl4 = false;
        this.validateType(it, ExprValueType.INT);
        int minute = ExprValueExtensionsKt.intValue(it);
        ExprValue exprValue4 = args2.get(2);
        it222 = false;
        boolean bl5 = false;
        ExprValue it3 = exprValue4;
        boolean bl6 = false;
        this.validateType(it3, ExprValueType.DECIMAL);
        BigDecimal second = ExprValueExtensionsKt.bigDecimalValue(it3);
        switch (args2.size()) {
            case 4: {
                ExprValue it222 = args2.get(3);
                bl5 = false;
                boolean bl7 = false;
                ExprValue it4 = it222;
                boolean bl8 = false;
                this.validateType(it4, ExprValueType.INT);
                n = ExprValueExtensionsKt.intValue(it4);
                break;
            }
            default: {
                n = null;
            }
        }
        Integer tzMinutes = n;
        try {
            int it222 = 1000000000;
            BigDecimal bigDecimal = second.remainder(BigDecimal.ONE);
            int n2 = second.intValue();
            int n3 = minute;
            int n4 = hour;
            Time.Companion companion = Time.Companion;
            ExprValueFactory exprValueFactory = this.getValueFactory();
            bl5 = false;
            BigDecimal bigDecimal2 = BigDecimal.valueOf(it222);
            Intrinsics.checkExpressionValueIsNotNull(bigDecimal2, "BigDecimal.valueOf(this.toLong())");
            BigDecimal bigDecimal3 = bigDecimal2;
            return exprValueFactory.newTime(companion.of(n4, n3, n2, bigDecimal.multiply(bigDecimal3).intValue(), second.scale(), tzMinutes));
        } catch (EvaluationException e) {
            Void void_ = ExceptionsKt.err(e.getMessage(), ErrorCode.EVALUATOR_TIME_FIELD_OUT_OF_RANGE, null, false);
            throw null;
        }
    }

    public MakeTimeExprFunction(@NotNull ExprValueFactory valueFactory) {
        Intrinsics.checkParameterIsNotNull(valueFactory, "valueFactory");
        int n = 3;
        super("make_time", new IntRange(n, 4), valueFactory);
    }
}

