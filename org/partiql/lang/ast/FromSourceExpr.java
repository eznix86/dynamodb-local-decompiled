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
import org.partiql.lang.ast.LetVariables;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\t\u0010\u0010\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0011\u001a\u00020\u0005H\u00c6\u0003J\u001d\u0010\u0012\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0005H\u00c6\u0001J\u0013\u0010\u0013\u001a\u00020\u00142\b\u0010\u0015\u001a\u0004\u0018\u00010\u0016H\u00d6\u0003J\t\u0010\u0017\u001a\u00020\u0018H\u00d6\u0001J\t\u0010\u0019\u001a\u00020\u001aH\u00d6\u0001R\u001a\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\n\u0010\u000bR\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0014\u0010\u0004\u001a\u00020\u0005X\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000f\u00a8\u0006\u001b"}, d2={"Lorg/partiql/lang/ast/FromSourceExpr;", "Lorg/partiql/lang/ast/FromSourceLet;", "expr", "Lorg/partiql/lang/ast/ExprNode;", "variables", "Lorg/partiql/lang/ast/LetVariables;", "(Lorg/partiql/lang/ast/ExprNode;Lorg/partiql/lang/ast/LetVariables;)V", "children", "", "Lorg/partiql/lang/ast/AstNode;", "getChildren", "()Ljava/util/List;", "getExpr", "()Lorg/partiql/lang/ast/ExprNode;", "getVariables", "()Lorg/partiql/lang/ast/LetVariables;", "component1", "component2", "copy", "equals", "", "other", "", "hashCode", "", "toString", "", "lang"})
public final class FromSourceExpr
extends FromSourceLet {
    @NotNull
    private final ExprNode expr;
    @NotNull
    private final LetVariables variables;

    @Override
    @NotNull
    public List<AstNode> getChildren() {
        return CollectionsKt.listOf(this.getExpr());
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

    public FromSourceExpr(@NotNull ExprNode expr, @NotNull LetVariables variables) {
        Intrinsics.checkParameterIsNotNull(expr, "expr");
        Intrinsics.checkParameterIsNotNull(variables, "variables");
        super(null);
        this.expr = expr;
        this.variables = variables;
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
    public final FromSourceExpr copy(@NotNull ExprNode expr, @NotNull LetVariables variables) {
        Intrinsics.checkParameterIsNotNull(expr, "expr");
        Intrinsics.checkParameterIsNotNull(variables, "variables");
        return new FromSourceExpr(expr, variables);
    }

    public static /* synthetic */ FromSourceExpr copy$default(FromSourceExpr fromSourceExpr, ExprNode exprNode, LetVariables letVariables, int n, Object object) {
        if ((n & 1) != 0) {
            exprNode = fromSourceExpr.getExpr();
        }
        if ((n & 2) != 0) {
            letVariables = fromSourceExpr.getVariables();
        }
        return fromSourceExpr.copy(exprNode, letVariables);
    }

    @NotNull
    public String toString() {
        return "FromSourceExpr(expr=" + this.getExpr() + ", variables=" + this.getVariables() + ")";
    }

    public int hashCode() {
        ExprNode exprNode = this.getExpr();
        LetVariables letVariables = this.getVariables();
        return (exprNode != null ? exprNode.hashCode() : 0) * 31 + (letVariables != null ? ((Object)letVariables).hashCode() : 0);
    }

    public boolean equals(@Nullable Object object) {
        block3: {
            block2: {
                if (this == object) break block2;
                if (!(object instanceof FromSourceExpr)) break block3;
                FromSourceExpr fromSourceExpr = (FromSourceExpr)object;
                if (!Intrinsics.areEqual(this.getExpr(), fromSourceExpr.getExpr()) || !Intrinsics.areEqual(this.getVariables(), fromSourceExpr.getVariables())) break block3;
            }
            return true;
        }
        return false;
    }
}

