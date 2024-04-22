/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package org.partiql.lang.eval;

import java.util.Set;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.partiql.lang.eval.ExpressionContext;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\"\n\u0002\u0010\u000e\n\u0002\b\u0007\b\u0002\u0018\u00002\u00020\u0001B\u001b\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u00a2\u0006\u0002\u0010\u0007J\u001c\u0010\f\u001a\u00020\u00002\u0006\u0010\u0002\u001a\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0017\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b\u00a8\u0006\r"}, d2={"Lorg/partiql/lang/eval/CompilationContext;", "", "expressionContext", "Lorg/partiql/lang/eval/ExpressionContext;", "fromSourceNames", "", "", "(Lorg/partiql/lang/eval/ExpressionContext;Ljava/util/Set;)V", "getExpressionContext", "()Lorg/partiql/lang/eval/ExpressionContext;", "getFromSourceNames", "()Ljava/util/Set;", "createNested", "lang"})
final class CompilationContext {
    @NotNull
    private final ExpressionContext expressionContext;
    @NotNull
    private final Set<String> fromSourceNames;

    @NotNull
    public final CompilationContext createNested(@NotNull ExpressionContext expressionContext, @NotNull Set<String> fromSourceNames) {
        Intrinsics.checkParameterIsNotNull((Object)expressionContext, "expressionContext");
        Intrinsics.checkParameterIsNotNull(fromSourceNames, "fromSourceNames");
        return new CompilationContext(expressionContext, fromSourceNames);
    }

    @NotNull
    public final ExpressionContext getExpressionContext() {
        return this.expressionContext;
    }

    @NotNull
    public final Set<String> getFromSourceNames() {
        return this.fromSourceNames;
    }

    public CompilationContext(@NotNull ExpressionContext expressionContext, @NotNull Set<String> fromSourceNames) {
        Intrinsics.checkParameterIsNotNull((Object)expressionContext, "expressionContext");
        Intrinsics.checkParameterIsNotNull(fromSourceNames, "fromSourceNames");
        this.expressionContext = expressionContext;
        this.fromSourceNames = fromSourceNames;
    }
}

