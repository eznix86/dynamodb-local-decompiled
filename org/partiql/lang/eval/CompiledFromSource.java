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
import org.partiql.lang.eval.JoinExpansion;
import org.partiql.lang.eval.binding.Alias;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000>\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000f\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0082\b\u0018\u00002\u00020\u0001BI\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0016\u0010\u0004\u001a\u0012\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00070\u0005j\u0002`\b\u0012\u0006\u0010\t\u001a\u00020\n\u0012\u001a\u0010\u000b\u001a\u0016\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u0007\u0018\u00010\u0005j\u0004\u0018\u0001`\b\u00a2\u0006\u0002\u0010\fJ\t\u0010\u0014\u001a\u00020\u0003H\u00c6\u0003J\u0019\u0010\u0015\u001a\u0012\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00070\u0005j\u0002`\bH\u00c6\u0003J\t\u0010\u0016\u001a\u00020\nH\u00c6\u0003J\u001d\u0010\u0017\u001a\u0016\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u0007\u0018\u00010\u0005j\u0004\u0018\u0001`\bH\u00c6\u0003JU\u0010\u0018\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\u0018\b\u0002\u0010\u0004\u001a\u0012\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00070\u0005j\u0002`\b2\b\b\u0002\u0010\t\u001a\u00020\n2\u001c\b\u0002\u0010\u000b\u001a\u0016\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u0007\u0018\u00010\u0005j\u0004\u0018\u0001`\bH\u00c6\u0001J\u0013\u0010\u0019\u001a\u00020\u001a2\b\u0010\u001b\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u001c\u001a\u00020\u001dH\u00d6\u0001J\t\u0010\u001e\u001a\u00020\u001fH\u00d6\u0001R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR%\u0010\u000b\u001a\u0016\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u0007\u0018\u00010\u0005j\u0004\u0018\u0001`\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0011\u0010\t\u001a\u00020\n\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R!\u0010\u0004\u001a\u0012\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00070\u0005j\u0002`\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0010\u00a8\u0006 "}, d2={"Lorg/partiql/lang/eval/CompiledFromSource;", "", "alias", "Lorg/partiql/lang/eval/binding/Alias;", "thunk", "Lkotlin/Function1;", "Lorg/partiql/lang/eval/Environment;", "Lorg/partiql/lang/eval/ExprValue;", "Lorg/partiql/lang/eval/ThunkEnv;", "joinExpansion", "Lorg/partiql/lang/eval/JoinExpansion;", "filter", "(Lorg/partiql/lang/eval/binding/Alias;Lkotlin/jvm/functions/Function1;Lorg/partiql/lang/eval/JoinExpansion;Lkotlin/jvm/functions/Function1;)V", "getAlias", "()Lorg/partiql/lang/eval/binding/Alias;", "getFilter", "()Lkotlin/jvm/functions/Function1;", "getJoinExpansion", "()Lorg/partiql/lang/eval/JoinExpansion;", "getThunk", "component1", "component2", "component3", "component4", "copy", "equals", "", "other", "hashCode", "", "toString", "", "lang"})
final class CompiledFromSource {
    @NotNull
    private final Alias alias;
    @NotNull
    private final Function1<Environment, ExprValue> thunk;
    @NotNull
    private final JoinExpansion joinExpansion;
    @Nullable
    private final Function1<Environment, ExprValue> filter;

    @NotNull
    public final Alias getAlias() {
        return this.alias;
    }

    @NotNull
    public final Function1<Environment, ExprValue> getThunk() {
        return this.thunk;
    }

    @NotNull
    public final JoinExpansion getJoinExpansion() {
        return this.joinExpansion;
    }

    @Nullable
    public final Function1<Environment, ExprValue> getFilter() {
        return this.filter;
    }

    public CompiledFromSource(@NotNull Alias alias, @NotNull Function1<? super Environment, ? extends ExprValue> thunk2, @NotNull JoinExpansion joinExpansion, @Nullable Function1<? super Environment, ? extends ExprValue> filter) {
        Intrinsics.checkParameterIsNotNull(alias, "alias");
        Intrinsics.checkParameterIsNotNull(thunk2, "thunk");
        Intrinsics.checkParameterIsNotNull((Object)joinExpansion, "joinExpansion");
        this.alias = alias;
        this.thunk = thunk2;
        this.joinExpansion = joinExpansion;
        this.filter = filter;
    }

    @NotNull
    public final Alias component1() {
        return this.alias;
    }

    @NotNull
    public final Function1<Environment, ExprValue> component2() {
        return this.thunk;
    }

    @NotNull
    public final JoinExpansion component3() {
        return this.joinExpansion;
    }

    @Nullable
    public final Function1<Environment, ExprValue> component4() {
        return this.filter;
    }

    @NotNull
    public final CompiledFromSource copy(@NotNull Alias alias, @NotNull Function1<? super Environment, ? extends ExprValue> thunk2, @NotNull JoinExpansion joinExpansion, @Nullable Function1<? super Environment, ? extends ExprValue> filter) {
        Intrinsics.checkParameterIsNotNull(alias, "alias");
        Intrinsics.checkParameterIsNotNull(thunk2, "thunk");
        Intrinsics.checkParameterIsNotNull((Object)joinExpansion, "joinExpansion");
        return new CompiledFromSource(alias, thunk2, joinExpansion, filter);
    }

    public static /* synthetic */ CompiledFromSource copy$default(CompiledFromSource compiledFromSource, Alias alias, Function1 function1, JoinExpansion joinExpansion, Function1 function12, int n, Object object) {
        if ((n & 1) != 0) {
            alias = compiledFromSource.alias;
        }
        if ((n & 2) != 0) {
            function1 = compiledFromSource.thunk;
        }
        if ((n & 4) != 0) {
            joinExpansion = compiledFromSource.joinExpansion;
        }
        if ((n & 8) != 0) {
            function12 = compiledFromSource.filter;
        }
        return compiledFromSource.copy(alias, function1, joinExpansion, function12);
    }

    @NotNull
    public String toString() {
        return "CompiledFromSource(alias=" + this.alias + ", thunk=" + this.thunk + ", joinExpansion=" + (Object)((Object)this.joinExpansion) + ", filter=" + this.filter + ")";
    }

    public int hashCode() {
        Alias alias = this.alias;
        Function1<Environment, ExprValue> function1 = this.thunk;
        JoinExpansion joinExpansion = this.joinExpansion;
        Function1<Environment, ExprValue> function12 = this.filter;
        return (((alias != null ? ((Object)alias).hashCode() : 0) * 31 + (function1 != null ? function1.hashCode() : 0)) * 31 + (joinExpansion != null ? ((Object)((Object)joinExpansion)).hashCode() : 0)) * 31 + (function12 != null ? function12.hashCode() : 0);
    }

    public boolean equals(@Nullable Object object) {
        block3: {
            block2: {
                if (this == object) break block2;
                if (!(object instanceof CompiledFromSource)) break block3;
                CompiledFromSource compiledFromSource = (CompiledFromSource)object;
                if (!Intrinsics.areEqual(this.alias, compiledFromSource.alias) || !Intrinsics.areEqual(this.thunk, compiledFromSource.thunk) || !Intrinsics.areEqual((Object)this.joinExpansion, (Object)compiledFromSource.joinExpansion) || !Intrinsics.areEqual(this.filter, compiledFromSource.filter)) break block3;
            }
            return true;
        }
        return false;
    }
}

