/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package org.partiql.lang.eval;

import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.partiql.lang.eval.Environment;
import org.partiql.lang.eval.ExprValue;
import org.partiql.lang.eval.ProjectionElement;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u0002\u0018\u00002\u00020\u0001B%\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0016\u0010\u0004\u001a\u0012\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00030\u0005j\u0002`\u0007\u00a2\u0006\u0002\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR!\u0010\u0004\u001a\u0012\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00030\u0005j\u0002`\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\f\u00a8\u0006\r"}, d2={"Lorg/partiql/lang/eval/SingleProjectionElement;", "Lorg/partiql/lang/eval/ProjectionElement;", "name", "Lorg/partiql/lang/eval/ExprValue;", "thunk", "Lkotlin/Function1;", "Lorg/partiql/lang/eval/Environment;", "Lorg/partiql/lang/eval/ThunkEnv;", "(Lorg/partiql/lang/eval/ExprValue;Lkotlin/jvm/functions/Function1;)V", "getName", "()Lorg/partiql/lang/eval/ExprValue;", "getThunk", "()Lkotlin/jvm/functions/Function1;", "lang"})
final class SingleProjectionElement
extends ProjectionElement {
    @NotNull
    private final ExprValue name;
    @NotNull
    private final Function1<Environment, ExprValue> thunk;

    @NotNull
    public final ExprValue getName() {
        return this.name;
    }

    @NotNull
    public final Function1<Environment, ExprValue> getThunk() {
        return this.thunk;
    }

    public SingleProjectionElement(@NotNull ExprValue name, @NotNull Function1<? super Environment, ? extends ExprValue> thunk2) {
        Intrinsics.checkParameterIsNotNull(name, "name");
        Intrinsics.checkParameterIsNotNull(thunk2, "thunk");
        super(null);
        this.name = name;
        this.thunk = thunk2;
    }
}

