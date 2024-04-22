/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package org.partiql.lang.eval;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.IntRange;
import org.jetbrains.annotations.NotNull;
import org.partiql.lang.errors.ErrorCode;
import org.partiql.lang.errors.Property;
import org.partiql.lang.errors.PropertyValueMap;
import org.partiql.lang.eval.EvaluationException;
import org.partiql.lang.eval.ExprValue;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\b`\u0018\u00002\u00020\u0001J\u0010\u0010\n\u001a\u00020\u00072\u0006\u0010\u000b\u001a\u00020\fH\u0002J\u0016\u0010\r\u001a\u00020\u000e2\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00110\u0010H\u0016R\u0012\u0010\u0002\u001a\u00020\u0003X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005R\u0012\u0010\u0006\u001a\u00020\u0007X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\b\u0010\t\u00a8\u0006\u0012"}, d2={"Lorg/partiql/lang/eval/ArityCheckingTrait;", "", "arity", "Lkotlin/ranges/IntRange;", "getArity", "()Lkotlin/ranges/IntRange;", "name", "", "getName", "()Ljava/lang/String;", "arityErrorMessage", "argSize", "", "checkArity", "", "args", "", "Lorg/partiql/lang/eval/ExprValue;", "lang"})
public interface ArityCheckingTrait {
    @NotNull
    public String getName();

    @NotNull
    public IntRange getArity();

    public void checkArity(@NotNull List<? extends ExprValue> var1);

    @Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=3)
    public static final class DefaultImpls {
        private static String arityErrorMessage(ArityCheckingTrait $this, int argSize) {
            return $this.getArity().getFirst() == 1 && $this.getArity().getLast() == 1 ? $this.getName() + " takes a single argument, received: " + argSize : ($this.getArity().getFirst() == $this.getArity().getLast() ? $this.getName() + " takes exactly " + $this.getArity().getFirst() + " arguments, received: " + argSize : $this.getName() + " takes between " + $this.getArity().getFirst() + " and " + $this.getArity().getLast() + " arguments, received: " + argSize);
        }

        public static void checkArity(ArityCheckingTrait $this, @NotNull List<? extends ExprValue> args2) {
            Intrinsics.checkParameterIsNotNull(args2, "args");
            if (!$this.getArity().contains(args2.size())) {
                PropertyValueMap errorContext = new PropertyValueMap(null, 1, null);
                errorContext.set(Property.EXPECTED_ARITY_MIN, $this.getArity().getFirst());
                errorContext.set(Property.EXPECTED_ARITY_MAX, $this.getArity().getLast());
                throw (Throwable)new EvaluationException(DefaultImpls.arityErrorMessage($this, args2.size()), ErrorCode.EVALUATOR_INCORRECT_NUMBER_OF_ARGUMENTS_TO_FUNC_CALL, errorContext, null, false, 8, null);
            }
        }
    }
}

