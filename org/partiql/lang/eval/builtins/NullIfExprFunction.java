/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package org.partiql.lang.eval.builtins;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.IntRange;
import org.jetbrains.annotations.NotNull;
import org.partiql.lang.eval.ArityCheckingTrait;
import org.partiql.lang.eval.Environment;
import org.partiql.lang.eval.ExprFunction;
import org.partiql.lang.eval.ExprValue;
import org.partiql.lang.eval.ExprValueExtensionsKt;
import org.partiql.lang.eval.ExprValueFactory;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0000\b\u0000\u0018\u00002\u00020\u00012\u00020\u0002B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u00a2\u0006\u0002\u0010\u0005J\u001e\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u000f0\u0013H\u0016R\u0014\u0010\u0006\u001a\u00020\u0007X\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0014\u0010\n\u001a\u00020\u000bX\u0096D\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0014"}, d2={"Lorg/partiql/lang/eval/builtins/NullIfExprFunction;", "Lorg/partiql/lang/eval/ArityCheckingTrait;", "Lorg/partiql/lang/eval/ExprFunction;", "valueFactory", "Lorg/partiql/lang/eval/ExprValueFactory;", "(Lorg/partiql/lang/eval/ExprValueFactory;)V", "arity", "Lkotlin/ranges/IntRange;", "getArity", "()Lkotlin/ranges/IntRange;", "name", "", "getName", "()Ljava/lang/String;", "call", "Lorg/partiql/lang/eval/ExprValue;", "env", "Lorg/partiql/lang/eval/Environment;", "args", "", "lang"})
public final class NullIfExprFunction
implements ArityCheckingTrait,
ExprFunction {
    @NotNull
    private final String name = "nullif";
    @NotNull
    private final IntRange arity;
    private final ExprValueFactory valueFactory;

    @Override
    @NotNull
    public String getName() {
        return this.name;
    }

    @Override
    @NotNull
    public IntRange getArity() {
        return this.arity;
    }

    @Override
    @NotNull
    public ExprValue call(@NotNull Environment env, @NotNull List<? extends ExprValue> args2) {
        Intrinsics.checkParameterIsNotNull(env, "env");
        Intrinsics.checkParameterIsNotNull(args2, "args");
        this.checkArity(args2);
        return ExprValueExtensionsKt.exprEquals(args2.get(0), args2.get(1)) ? this.valueFactory.getNullValue() : args2.get(0);
    }

    public NullIfExprFunction(@NotNull ExprValueFactory valueFactory) {
        Intrinsics.checkParameterIsNotNull(valueFactory, "valueFactory");
        this.valueFactory = valueFactory;
        this.name = "nullif";
        int n = 2;
        this.arity = new IntRange(n, 2);
    }

    @Override
    public void checkArity(@NotNull List<? extends ExprValue> args2) {
        Intrinsics.checkParameterIsNotNull(args2, "args");
        ArityCheckingTrait.DefaultImpls.checkArity(this, args2);
    }
}

