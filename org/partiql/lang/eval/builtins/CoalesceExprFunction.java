/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package org.partiql.lang.eval.builtins;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.partiql.lang.errors.ErrorCode;
import org.partiql.lang.errors.Property;
import org.partiql.lang.errors.PropertyValueMap;
import org.partiql.lang.eval.Environment;
import org.partiql.lang.eval.EvaluationException;
import org.partiql.lang.eval.ExprFunction;
import org.partiql.lang.eval.ExprValue;
import org.partiql.lang.eval.ExprValueFactory;
import org.partiql.lang.eval.ExprValueType;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0010\u0002\n\u0000\b\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u001e\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\n0\u000eH\u0016J\u0016\u0010\u000f\u001a\u00020\u00102\f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\n0\u000eH\u0002R\u0014\u0010\u0005\u001a\u00020\u0006X\u0096D\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0011"}, d2={"Lorg/partiql/lang/eval/builtins/CoalesceExprFunction;", "Lorg/partiql/lang/eval/ExprFunction;", "valueFactory", "Lorg/partiql/lang/eval/ExprValueFactory;", "(Lorg/partiql/lang/eval/ExprValueFactory;)V", "name", "", "getName", "()Ljava/lang/String;", "call", "Lorg/partiql/lang/eval/ExprValue;", "env", "Lorg/partiql/lang/eval/Environment;", "args", "", "checkArity", "", "lang"})
public final class CoalesceExprFunction
implements ExprFunction {
    @NotNull
    private final String name = "coalesce";
    private final ExprValueFactory valueFactory;

    @Override
    @NotNull
    public String getName() {
        return this.name;
    }

    /*
     * WARNING - void declaration
     */
    @Override
    @NotNull
    public ExprValue call(@NotNull Environment env, @NotNull List<? extends ExprValue> args2) {
        void $this$filterNotTo$iv$iv;
        Intrinsics.checkParameterIsNotNull(env, "env");
        Intrinsics.checkParameterIsNotNull(args2, "args");
        this.checkArity(args2);
        Iterable $this$filterNot$iv = args2;
        boolean $i$f$filterNot = false;
        Iterable iterable = $this$filterNot$iv;
        Collection destination$iv$iv = new ArrayList();
        boolean $i$f$filterNotTo = false;
        for (Object element$iv$iv : $this$filterNotTo$iv$iv) {
            ExprValue it = (ExprValue)element$iv$iv;
            boolean bl = false;
            if (it.getType() == ExprValueType.NULL || it.getType() == ExprValueType.MISSING) continue;
            destination$iv$iv.add(element$iv$iv);
        }
        ExprValue exprValue2 = (ExprValue)CollectionsKt.firstOrNull((List)destination$iv$iv);
        if (exprValue2 == null) {
            return this.valueFactory.getNullValue();
        }
        return exprValue2;
    }

    private final void checkArity(List<? extends ExprValue> args2) {
        if (args2.isEmpty()) {
            PropertyValueMap errorContext = new PropertyValueMap(null, 1, null);
            errorContext.set(Property.EXPECTED_ARITY_MIN, 1);
            errorContext.set(Property.EXPECTED_ARITY_MAX, Integer.MAX_VALUE);
            throw (Throwable)new EvaluationException("coalesce requires at least one argument", ErrorCode.EVALUATOR_INCORRECT_NUMBER_OF_ARGUMENTS_TO_FUNC_CALL, errorContext, null, false, 8, null);
        }
    }

    public CoalesceExprFunction(@NotNull ExprValueFactory valueFactory) {
        Intrinsics.checkParameterIsNotNull(valueFactory, "valueFactory");
        this.valueFactory = valueFactory;
        this.name = "coalesce";
    }
}

