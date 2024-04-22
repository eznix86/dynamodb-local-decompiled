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
import org.partiql.lang.eval.ArityCheckingTrait;
import org.partiql.lang.eval.Environment;
import org.partiql.lang.eval.ExprFunction;
import org.partiql.lang.eval.ExprValue;
import org.partiql.lang.eval.ExprValueFactory;
import org.partiql.lang.util.CollectionExtensionsKt;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u0002\b&\u0018\u00002\u00020\u00012\u00020\u0002B\u001f\b\u0016\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b\u00a2\u0006\u0002\u0010\tB\u001d\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\n\u0012\u0006\u0010\u0007\u001a\u00020\b\u00a2\u0006\u0002\u0010\u000bJ\u001e\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u00152\f\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00130\u0017H\u0016J\u001e\u0010\u0018\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u00152\f\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00130\u0017H&R\u0014\u0010\u0005\u001a\u00020\nX\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0014\u0010\u0003\u001a\u00020\u0004X\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0011\u0010\u0007\u001a\u00020\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011\u00a8\u0006\u0019"}, d2={"Lorg/partiql/lang/eval/NullPropagatingExprFunction;", "Lorg/partiql/lang/eval/ArityCheckingTrait;", "Lorg/partiql/lang/eval/ExprFunction;", "name", "", "arity", "", "valueFactory", "Lorg/partiql/lang/eval/ExprValueFactory;", "(Ljava/lang/String;ILorg/partiql/lang/eval/ExprValueFactory;)V", "Lkotlin/ranges/IntRange;", "(Ljava/lang/String;Lkotlin/ranges/IntRange;Lorg/partiql/lang/eval/ExprValueFactory;)V", "getArity", "()Lkotlin/ranges/IntRange;", "getName", "()Ljava/lang/String;", "getValueFactory", "()Lorg/partiql/lang/eval/ExprValueFactory;", "call", "Lorg/partiql/lang/eval/ExprValue;", "env", "Lorg/partiql/lang/eval/Environment;", "args", "", "eval", "lang"})
public abstract class NullPropagatingExprFunction
implements ArityCheckingTrait,
ExprFunction {
    @NotNull
    private final String name;
    @NotNull
    private final IntRange arity;
    @NotNull
    private final ExprValueFactory valueFactory;

    @Override
    @NotNull
    public ExprValue call(@NotNull Environment env, @NotNull List<? extends ExprValue> args2) {
        Intrinsics.checkParameterIsNotNull(env, "env");
        Intrinsics.checkParameterIsNotNull(args2, "args");
        this.checkArity(args2);
        return CollectionExtensionsKt.isAnyUnknown((Iterable<? extends ExprValue>)args2) ? this.valueFactory.getNullValue() : this.eval(env, args2);
    }

    @NotNull
    public abstract ExprValue eval(@NotNull Environment var1, @NotNull List<? extends ExprValue> var2);

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

    @NotNull
    public final ExprValueFactory getValueFactory() {
        return this.valueFactory;
    }

    public NullPropagatingExprFunction(@NotNull String name, @NotNull IntRange arity, @NotNull ExprValueFactory valueFactory) {
        Intrinsics.checkParameterIsNotNull(name, "name");
        Intrinsics.checkParameterIsNotNull(arity, "arity");
        Intrinsics.checkParameterIsNotNull(valueFactory, "valueFactory");
        this.name = name;
        this.arity = arity;
        this.valueFactory = valueFactory;
    }

    public NullPropagatingExprFunction(@NotNull String name, int arity, @NotNull ExprValueFactory valueFactory) {
        Intrinsics.checkParameterIsNotNull(name, "name");
        Intrinsics.checkParameterIsNotNull(valueFactory, "valueFactory");
        int n = arity;
        this(name, new IntRange(n, arity), valueFactory);
    }

    @Override
    public void checkArity(@NotNull List<? extends ExprValue> args2) {
        Intrinsics.checkParameterIsNotNull(args2, "args");
        ArityCheckingTrait.DefaultImpls.checkArity(this, args2);
    }
}

