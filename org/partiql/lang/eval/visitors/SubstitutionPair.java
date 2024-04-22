/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package org.partiql.lang.eval.visitors;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.partiql.lang.domains.PartiqlAst;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0005J\t\u0010\t\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\n\u001a\u00020\u0003H\u00c6\u0003J\u001d\u0010\u000b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0003H\u00c6\u0001J\u0013\u0010\f\u001a\u00020\r2\b\u0010\u000e\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u000f\u001a\u00020\u0010H\u00d6\u0001J\t\u0010\u0011\u001a\u00020\u0012H\u00d6\u0001R\u0011\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0007\u00a8\u0006\u0013"}, d2={"Lorg/partiql/lang/eval/visitors/SubstitutionPair;", "", "target", "Lorg/partiql/lang/domains/PartiqlAst$Expr;", "replacement", "(Lorg/partiql/lang/domains/PartiqlAst$Expr;Lorg/partiql/lang/domains/PartiqlAst$Expr;)V", "getReplacement", "()Lorg/partiql/lang/domains/PartiqlAst$Expr;", "getTarget", "component1", "component2", "copy", "equals", "", "other", "hashCode", "", "toString", "", "lang"})
public final class SubstitutionPair {
    @NotNull
    private final PartiqlAst.Expr target;
    @NotNull
    private final PartiqlAst.Expr replacement;

    @NotNull
    public final PartiqlAst.Expr getTarget() {
        return this.target;
    }

    @NotNull
    public final PartiqlAst.Expr getReplacement() {
        return this.replacement;
    }

    public SubstitutionPair(@NotNull PartiqlAst.Expr target, @NotNull PartiqlAst.Expr replacement) {
        Intrinsics.checkParameterIsNotNull(target, "target");
        Intrinsics.checkParameterIsNotNull(replacement, "replacement");
        this.target = target;
        this.replacement = replacement;
    }

    @NotNull
    public final PartiqlAst.Expr component1() {
        return this.target;
    }

    @NotNull
    public final PartiqlAst.Expr component2() {
        return this.replacement;
    }

    @NotNull
    public final SubstitutionPair copy(@NotNull PartiqlAst.Expr target, @NotNull PartiqlAst.Expr replacement) {
        Intrinsics.checkParameterIsNotNull(target, "target");
        Intrinsics.checkParameterIsNotNull(replacement, "replacement");
        return new SubstitutionPair(target, replacement);
    }

    public static /* synthetic */ SubstitutionPair copy$default(SubstitutionPair substitutionPair, PartiqlAst.Expr expr, PartiqlAst.Expr expr2, int n, Object object) {
        if ((n & 1) != 0) {
            expr = substitutionPair.target;
        }
        if ((n & 2) != 0) {
            expr2 = substitutionPair.replacement;
        }
        return substitutionPair.copy(expr, expr2);
    }

    @NotNull
    public String toString() {
        return "SubstitutionPair(target=" + this.target + ", replacement=" + this.replacement + ")";
    }

    public int hashCode() {
        PartiqlAst.Expr expr = this.target;
        PartiqlAst.Expr expr2 = this.replacement;
        return (expr != null ? expr.hashCode() : 0) * 31 + (expr2 != null ? expr2.hashCode() : 0);
    }

    public boolean equals(@Nullable Object object) {
        block3: {
            block2: {
                if (this == object) break block2;
                if (!(object instanceof SubstitutionPair)) break block3;
                SubstitutionPair substitutionPair = (SubstitutionPair)object;
                if (!Intrinsics.areEqual(this.target, substitutionPair.target) || !Intrinsics.areEqual(this.replacement, substitutionPair.replacement)) break block3;
            }
            return true;
        }
        return false;
    }
}

