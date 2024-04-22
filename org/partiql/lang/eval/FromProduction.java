/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package org.partiql.lang.eval;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.partiql.lang.eval.Environment;
import org.partiql.lang.eval.ExprValue;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0082\b\u0018\u00002\u00020\u0001B\u001b\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u00a2\u0006\u0002\u0010\u0007J\u000f\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003H\u00c6\u0003J\t\u0010\r\u001a\u00020\u0006H\u00c6\u0003J#\u0010\u000e\u001a\u00020\u00002\u000e\b\u0002\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u0006H\u00c6\u0001J\u0013\u0010\u000f\u001a\u00020\u00102\b\u0010\u0011\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u0012\u001a\u00020\u0013H\u00d6\u0001J\t\u0010\u0014\u001a\u00020\u0015H\u00d6\u0001R\u0011\u0010\u0005\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b\u00a8\u0006\u0016"}, d2={"Lorg/partiql/lang/eval/FromProduction;", "", "values", "", "Lorg/partiql/lang/eval/ExprValue;", "env", "Lorg/partiql/lang/eval/Environment;", "(Ljava/util/List;Lorg/partiql/lang/eval/Environment;)V", "getEnv", "()Lorg/partiql/lang/eval/Environment;", "getValues", "()Ljava/util/List;", "component1", "component2", "copy", "equals", "", "other", "hashCode", "", "toString", "", "lang"})
final class FromProduction {
    @NotNull
    private final List<ExprValue> values;
    @NotNull
    private final Environment env;

    @NotNull
    public final List<ExprValue> getValues() {
        return this.values;
    }

    @NotNull
    public final Environment getEnv() {
        return this.env;
    }

    public FromProduction(@NotNull List<? extends ExprValue> values2, @NotNull Environment env) {
        Intrinsics.checkParameterIsNotNull(values2, "values");
        Intrinsics.checkParameterIsNotNull(env, "env");
        this.values = values2;
        this.env = env;
    }

    @NotNull
    public final List<ExprValue> component1() {
        return this.values;
    }

    @NotNull
    public final Environment component2() {
        return this.env;
    }

    @NotNull
    public final FromProduction copy(@NotNull List<? extends ExprValue> values2, @NotNull Environment env) {
        Intrinsics.checkParameterIsNotNull(values2, "values");
        Intrinsics.checkParameterIsNotNull(env, "env");
        return new FromProduction(values2, env);
    }

    public static /* synthetic */ FromProduction copy$default(FromProduction fromProduction, List list, Environment environment, int n, Object object) {
        if ((n & 1) != 0) {
            list = fromProduction.values;
        }
        if ((n & 2) != 0) {
            environment = fromProduction.env;
        }
        return fromProduction.copy(list, environment);
    }

    @NotNull
    public String toString() {
        return "FromProduction(values=" + this.values + ", env=" + this.env + ")";
    }

    public int hashCode() {
        List<ExprValue> list = this.values;
        Environment environment = this.env;
        return (list != null ? ((Object)list).hashCode() : 0) * 31 + (environment != null ? ((Object)environment).hashCode() : 0);
    }

    public boolean equals(@Nullable Object object) {
        block3: {
            block2: {
                if (this == object) break block2;
                if (!(object instanceof FromProduction)) break block3;
                FromProduction fromProduction = (FromProduction)object;
                if (!Intrinsics.areEqual(this.values, fromProduction.values) || !Intrinsics.areEqual(this.env, fromProduction.env)) break block3;
            }
            return true;
        }
        return false;
    }
}

