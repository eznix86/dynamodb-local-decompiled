/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package org.partiql.lang;

import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.partiql.lang.eval.CompileOptions;
import org.partiql.lang.eval.ExprFunction;
import org.partiql.lang.eval.ExprValueFactory;
import org.partiql.lang.eval.builtins.storedprocedure.StoredProcedure;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000@\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000e\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001BG\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0017\u0010\u0006\u001a\u0013\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\t0\u0007\u00a2\u0006\u0002\b\n\u0012\u0017\u0010\u000b\u001a\u0013\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\f0\u0007\u00a2\u0006\u0002\b\n\u00a2\u0006\u0002\u0010\rJ\t\u0010\u0015\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0016\u001a\u00020\u0005H\u00c6\u0003J\u001a\u0010\u0017\u001a\u0013\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\t0\u0007\u00a2\u0006\u0002\b\nH\u00c6\u0003J\u001a\u0010\u0018\u001a\u0013\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\f0\u0007\u00a2\u0006\u0002\b\nH\u00c6\u0003JS\u0010\u0019\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\u0019\b\u0002\u0010\u0006\u001a\u0013\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\t0\u0007\u00a2\u0006\u0002\b\n2\u0019\b\u0002\u0010\u000b\u001a\u0013\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\f0\u0007\u00a2\u0006\u0002\b\nH\u00c6\u0001J\u0013\u0010\u001a\u001a\u00020\u001b2\b\u0010\u001c\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u001d\u001a\u00020\u001eH\u00d6\u0001J\t\u0010\u001f\u001a\u00020\bH\u00d6\u0001R\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\"\u0010\u0006\u001a\u0013\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\t0\u0007\u00a2\u0006\u0002\b\n\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\"\u0010\u000b\u001a\u0013\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\f0\u0007\u00a2\u0006\u0002\b\n\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0011R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014\u00a8\u0006 "}, d2={"Lorg/partiql/lang/StepContext;", "", "valueFactory", "Lorg/partiql/lang/eval/ExprValueFactory;", "compileOptions", "Lorg/partiql/lang/eval/CompileOptions;", "functions", "", "", "Lorg/partiql/lang/eval/ExprFunction;", "Lkotlin/jvm/JvmSuppressWildcards;", "procedures", "Lorg/partiql/lang/eval/builtins/storedprocedure/StoredProcedure;", "(Lorg/partiql/lang/eval/ExprValueFactory;Lorg/partiql/lang/eval/CompileOptions;Ljava/util/Map;Ljava/util/Map;)V", "getCompileOptions", "()Lorg/partiql/lang/eval/CompileOptions;", "getFunctions", "()Ljava/util/Map;", "getProcedures", "getValueFactory", "()Lorg/partiql/lang/eval/ExprValueFactory;", "component1", "component2", "component3", "component4", "copy", "equals", "", "other", "hashCode", "", "toString", "lang"})
public final class StepContext {
    @NotNull
    private final ExprValueFactory valueFactory;
    @NotNull
    private final CompileOptions compileOptions;
    @NotNull
    private final Map<String, ExprFunction> functions;
    @NotNull
    private final Map<String, StoredProcedure> procedures;

    @NotNull
    public final ExprValueFactory getValueFactory() {
        return this.valueFactory;
    }

    @NotNull
    public final CompileOptions getCompileOptions() {
        return this.compileOptions;
    }

    @NotNull
    public final Map<String, ExprFunction> getFunctions() {
        return this.functions;
    }

    @NotNull
    public final Map<String, StoredProcedure> getProcedures() {
        return this.procedures;
    }

    public StepContext(@NotNull ExprValueFactory valueFactory, @NotNull CompileOptions compileOptions, @NotNull Map<String, ExprFunction> functions, @NotNull Map<String, StoredProcedure> procedures) {
        Intrinsics.checkParameterIsNotNull(valueFactory, "valueFactory");
        Intrinsics.checkParameterIsNotNull(compileOptions, "compileOptions");
        Intrinsics.checkParameterIsNotNull(functions, "functions");
        Intrinsics.checkParameterIsNotNull(procedures, "procedures");
        this.valueFactory = valueFactory;
        this.compileOptions = compileOptions;
        this.functions = functions;
        this.procedures = procedures;
    }

    @NotNull
    public final ExprValueFactory component1() {
        return this.valueFactory;
    }

    @NotNull
    public final CompileOptions component2() {
        return this.compileOptions;
    }

    @NotNull
    public final Map<String, ExprFunction> component3() {
        return this.functions;
    }

    @NotNull
    public final Map<String, StoredProcedure> component4() {
        return this.procedures;
    }

    @NotNull
    public final StepContext copy(@NotNull ExprValueFactory valueFactory, @NotNull CompileOptions compileOptions, @NotNull Map<String, ExprFunction> functions, @NotNull Map<String, StoredProcedure> procedures) {
        Intrinsics.checkParameterIsNotNull(valueFactory, "valueFactory");
        Intrinsics.checkParameterIsNotNull(compileOptions, "compileOptions");
        Intrinsics.checkParameterIsNotNull(functions, "functions");
        Intrinsics.checkParameterIsNotNull(procedures, "procedures");
        return new StepContext(valueFactory, compileOptions, functions, procedures);
    }

    public static /* synthetic */ StepContext copy$default(StepContext stepContext, ExprValueFactory exprValueFactory, CompileOptions compileOptions, Map map2, Map map3, int n, Object object) {
        if ((n & 1) != 0) {
            exprValueFactory = stepContext.valueFactory;
        }
        if ((n & 2) != 0) {
            compileOptions = stepContext.compileOptions;
        }
        if ((n & 4) != 0) {
            map2 = stepContext.functions;
        }
        if ((n & 8) != 0) {
            map3 = stepContext.procedures;
        }
        return stepContext.copy(exprValueFactory, compileOptions, map2, map3);
    }

    @NotNull
    public String toString() {
        return "StepContext(valueFactory=" + this.valueFactory + ", compileOptions=" + this.compileOptions + ", functions=" + this.functions + ", procedures=" + this.procedures + ")";
    }

    public int hashCode() {
        ExprValueFactory exprValueFactory = this.valueFactory;
        CompileOptions compileOptions = this.compileOptions;
        Map<String, ExprFunction> map2 = this.functions;
        Map<String, StoredProcedure> map3 = this.procedures;
        return (((exprValueFactory != null ? exprValueFactory.hashCode() : 0) * 31 + (compileOptions != null ? ((Object)compileOptions).hashCode() : 0)) * 31 + (map2 != null ? ((Object)map2).hashCode() : 0)) * 31 + (map3 != null ? ((Object)map3).hashCode() : 0);
    }

    public boolean equals(@Nullable Object object) {
        block3: {
            block2: {
                if (this == object) break block2;
                if (!(object instanceof StepContext)) break block3;
                StepContext stepContext = (StepContext)object;
                if (!Intrinsics.areEqual(this.valueFactory, stepContext.valueFactory) || !Intrinsics.areEqual(this.compileOptions, stepContext.compileOptions) || !Intrinsics.areEqual(this.functions, stepContext.functions) || !Intrinsics.areEqual(this.procedures, stepContext.procedures)) break block3;
            }
            return true;
        }
        return false;
    }
}

