/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package org.partiql.lang.eval;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.partiql.lang.eval.Environment;
import org.partiql.lang.eval.ExprValue;
import org.partiql.lang.eval.ProjectionElement;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0002\u0018\u00002\u00020\u0001B#\u0012\u001c\u0010\u0002\u001a\u0018\u0012\u0014\u0012\u0012\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u0004j\u0002`\u00070\u0003\u00a2\u0006\u0002\u0010\bR'\u0010\u0002\u001a\u0018\u0012\u0014\u0012\u0012\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u0004j\u0002`\u00070\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n\u00a8\u0006\u000b"}, d2={"Lorg/partiql/lang/eval/MultipleProjectionElement;", "Lorg/partiql/lang/eval/ProjectionElement;", "thunks", "", "Lkotlin/Function1;", "Lorg/partiql/lang/eval/Environment;", "Lorg/partiql/lang/eval/ExprValue;", "Lorg/partiql/lang/eval/ThunkEnv;", "(Ljava/util/List;)V", "getThunks", "()Ljava/util/List;", "lang"})
final class MultipleProjectionElement
extends ProjectionElement {
    @NotNull
    private final List<Function1<Environment, ExprValue>> thunks;

    @NotNull
    public final List<Function1<Environment, ExprValue>> getThunks() {
        return this.thunks;
    }

    public MultipleProjectionElement(@NotNull List<? extends Function1<? super Environment, ? extends ExprValue>> thunks) {
        Intrinsics.checkParameterIsNotNull(thunks, "thunks");
        super(null);
        this.thunks = thunks;
    }
}

