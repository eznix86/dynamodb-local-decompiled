/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package org.partiql.lang.ast;

import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.partiql.lang.ast.AstNode;
import org.partiql.lang.ast.ExprNode;
import org.partiql.lang.ast.FromSourceLet;
import org.partiql.lang.ast.HasMetas;
import org.partiql.lang.ast.LetVariables;
import org.partiql.lang.ast.MetaContainer;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000F\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u00012\u00020\u0002B\u001d\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b\u00a2\u0006\u0002\u0010\tJ\t\u0010\u0015\u001a\u00020\u0004H\u00c6\u0003J\t\u0010\u0016\u001a\u00020\u0006H\u00c6\u0003J\t\u0010\u0017\u001a\u00020\bH\u00c6\u0003J'\u0010\u0018\u001a\u00020\u00002\b\b\u0002\u0010\u0003\u001a\u00020\u00042\b\b\u0002\u0010\u0005\u001a\u00020\u00062\b\b\u0002\u0010\u0007\u001a\u00020\bH\u00c6\u0001J\u0013\u0010\u0019\u001a\u00020\u001a2\b\u0010\u001b\u001a\u0004\u0018\u00010\u001cH\u00d6\u0003J\t\u0010\u001d\u001a\u00020\u001eH\u00d6\u0001J\t\u0010\u001f\u001a\u00020 H\u00d6\u0001R\u001a\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000bX\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0014\u0010\u0003\u001a\u00020\u0004X\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0014\u0010\u0007\u001a\u00020\bX\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u0014\u0010\u0005\u001a\u00020\u0006X\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014\u00a8\u0006!"}, d2={"Lorg/partiql/lang/ast/FromSourceUnpivot;", "Lorg/partiql/lang/ast/FromSourceLet;", "Lorg/partiql/lang/ast/HasMetas;", "expr", "Lorg/partiql/lang/ast/ExprNode;", "variables", "Lorg/partiql/lang/ast/LetVariables;", "metas", "Lorg/partiql/lang/ast/MetaContainer;", "(Lorg/partiql/lang/ast/ExprNode;Lorg/partiql/lang/ast/LetVariables;Lorg/partiql/lang/ast/MetaContainer;)V", "children", "", "Lorg/partiql/lang/ast/AstNode;", "getChildren", "()Ljava/util/List;", "getExpr", "()Lorg/partiql/lang/ast/ExprNode;", "getMetas", "()Lorg/partiql/lang/ast/MetaContainer;", "getVariables", "()Lorg/partiql/lang/ast/LetVariables;", "component1", "component2", "component3", "copy", "equals", "", "other", "", "hashCode", "", "toString", "", "lang"})
public final class FromSourceUnpivot
extends FromSourceLet
implements HasMetas {
    @NotNull
    private final List<AstNode> children;
    @NotNull
    private final ExprNode expr;
    @NotNull
    private final LetVariables variables;
    @NotNull
    private final MetaContainer metas;

    @Override
    @NotNull
    public List<AstNode> getChildren() {
        return this.children;
    }

    @Override
    @NotNull
    public ExprNode getExpr() {
        return this.expr;
    }

    @Override
    @NotNull
    public LetVariables getVariables() {
        return this.variables;
    }

    @Override
    @NotNull
    public MetaContainer getMetas() {
        return this.metas;
    }

    public FromSourceUnpivot(@NotNull ExprNode expr, @NotNull LetVariables variables, @NotNull MetaContainer metas) {
        Intrinsics.checkParameterIsNotNull(expr, "expr");
        Intrinsics.checkParameterIsNotNull(variables, "variables");
        Intrinsics.checkParameterIsNotNull(metas, "metas");
        super(null);
        this.expr = expr;
        this.variables = variables;
        this.metas = metas;
        this.children = CollectionsKt.listOf(this.getExpr());
    }

    @NotNull
    public final ExprNode component1() {
        return this.getExpr();
    }

    @NotNull
    public final LetVariables component2() {
        return this.getVariables();
    }

    @NotNull
    public final MetaContainer component3() {
        return this.getMetas();
    }

    @NotNull
    public final FromSourceUnpivot copy(@NotNull ExprNode expr, @NotNull LetVariables variables, @NotNull MetaContainer metas) {
        Intrinsics.checkParameterIsNotNull(expr, "expr");
        Intrinsics.checkParameterIsNotNull(variables, "variables");
        Intrinsics.checkParameterIsNotNull(metas, "metas");
        return new FromSourceUnpivot(expr, variables, metas);
    }

    public static /* synthetic */ FromSourceUnpivot copy$default(FromSourceUnpivot fromSourceUnpivot, ExprNode exprNode, LetVariables letVariables, MetaContainer metaContainer, int n, Object object) {
        if ((n & 1) != 0) {
            exprNode = fromSourceUnpivot.getExpr();
        }
        if ((n & 2) != 0) {
            letVariables = fromSourceUnpivot.getVariables();
        }
        if ((n & 4) != 0) {
            metaContainer = fromSourceUnpivot.getMetas();
        }
        return fromSourceUnpivot.copy(exprNode, letVariables, metaContainer);
    }

    @NotNull
    public String toString() {
        return "FromSourceUnpivot(expr=" + this.getExpr() + ", variables=" + this.getVariables() + ", metas=" + this.getMetas() + ")";
    }

    public int hashCode() {
        ExprNode exprNode = this.getExpr();
        LetVariables letVariables = this.getVariables();
        MetaContainer metaContainer = this.getMetas();
        return ((exprNode != null ? exprNode.hashCode() : 0) * 31 + (letVariables != null ? ((Object)letVariables).hashCode() : 0)) * 31 + (metaContainer != null ? metaContainer.hashCode() : 0);
    }

    public boolean equals(@Nullable Object object) {
        block3: {
            block2: {
                if (this == object) break block2;
                if (!(object instanceof FromSourceUnpivot)) break block3;
                FromSourceUnpivot fromSourceUnpivot = (FromSourceUnpivot)object;
                if (!Intrinsics.areEqual(this.getExpr(), fromSourceUnpivot.getExpr()) || !Intrinsics.areEqual(this.getVariables(), fromSourceUnpivot.getVariables()) || !Intrinsics.areEqual(this.getMetas(), fromSourceUnpivot.getMetas())) break block3;
            }
            return true;
        }
        return false;
    }
}

