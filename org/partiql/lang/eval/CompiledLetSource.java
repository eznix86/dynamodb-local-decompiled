/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package org.partiql.lang.eval;

import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.partiql.lang.eval.Environment;
import org.partiql.lang.eval.ExprValue;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0082\b\u0018\u00002\u00020\u0001B%\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0016\u0010\u0004\u001a\u0012\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00070\u0005j\u0002`\b\u00a2\u0006\u0002\u0010\tJ\t\u0010\u000e\u001a\u00020\u0003H\u00c6\u0003J\u0019\u0010\u000f\u001a\u0012\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00070\u0005j\u0002`\bH\u00c6\u0003J-\u0010\u0010\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\u0018\b\u0002\u0010\u0004\u001a\u0012\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00070\u0005j\u0002`\bH\u00c6\u0001J\u0013\u0010\u0011\u001a\u00020\u00122\b\u0010\u0013\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u0014\u001a\u00020\u0015H\u00d6\u0001J\t\u0010\u0016\u001a\u00020\u0003H\u00d6\u0001R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR!\u0010\u0004\u001a\u0012\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00070\u0005j\u0002`\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\r\u00a8\u0006\u0017"}, d2={"Lorg/partiql/lang/eval/CompiledLetSource;", "", "name", "", "thunk", "Lkotlin/Function1;", "Lorg/partiql/lang/eval/Environment;", "Lorg/partiql/lang/eval/ExprValue;", "Lorg/partiql/lang/eval/ThunkEnv;", "(Ljava/lang/String;Lkotlin/jvm/functions/Function1;)V", "getName", "()Ljava/lang/String;", "getThunk", "()Lkotlin/jvm/functions/Function1;", "component1", "component2", "copy", "equals", "", "other", "hashCode", "", "toString", "lang"})
final class CompiledLetSource {
    @NotNull
    private final String name;
    @NotNull
    private final Function1<Environment, ExprValue> thunk;

    @NotNull
    public final String getName() {
        return this.name;
    }

    @NotNull
    public final Function1<Environment, ExprValue> getThunk() {
        return this.thunk;
    }

    public CompiledLetSource(@NotNull String name, @NotNull Function1<? super Environment, ? extends ExprValue> thunk2) {
        Intrinsics.checkParameterIsNotNull(name, "name");
        Intrinsics.checkParameterIsNotNull(thunk2, "thunk");
        this.name = name;
        this.thunk = thunk2;
    }

    @NotNull
    public final String component1() {
        return this.name;
    }

    @NotNull
    public final Function1<Environment, ExprValue> component2() {
        return this.thunk;
    }

    @NotNull
    public final CompiledLetSource copy(@NotNull String name, @NotNull Function1<? super Environment, ? extends ExprValue> thunk2) {
        Intrinsics.checkParameterIsNotNull(name, "name");
        Intrinsics.checkParameterIsNotNull(thunk2, "thunk");
        return new CompiledLetSource(name, thunk2);
    }

    public static /* synthetic */ CompiledLetSource copy$default(CompiledLetSource compiledLetSource, String string, Function1 function1, int n, Object object) {
        if ((n & 1) != 0) {
            string = compiledLetSource.name;
        }
        if ((n & 2) != 0) {
            function1 = compiledLetSource.thunk;
        }
        return compiledLetSource.copy(string, function1);
    }

    @NotNull
    public String toString() {
        return "CompiledLetSource(name=" + this.name + ", thunk=" + this.thunk + ")";
    }

    public int hashCode() {
        String string = this.name;
        Function1<Environment, ExprValue> function1 = this.thunk;
        return (string != null ? string.hashCode() : 0) * 31 + (function1 != null ? function1.hashCode() : 0);
    }

    public boolean equals(@Nullable Object object) {
        block3: {
            block2: {
                if (this == object) break block2;
                if (!(object instanceof CompiledLetSource)) break block3;
                CompiledLetSource compiledLetSource = (CompiledLetSource)object;
                if (!Intrinsics.areEqual(this.name, compiledLetSource.name) || !Intrinsics.areEqual(this.thunk, compiledLetSource.thunk)) break block3;
            }
            return true;
        }
        return false;
    }
}

