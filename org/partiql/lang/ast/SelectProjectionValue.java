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
import org.partiql.lang.ast.SelectProjection;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\t\u0010\f\u001a\u00020\u0003H\u00c6\u0003J\u0013\u0010\r\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u0003H\u00c6\u0001J\u0013\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0011H\u00d6\u0003J\t\u0010\u0012\u001a\u00020\u0013H\u00d6\u0001J\t\u0010\u0014\u001a\u00020\u0015H\u00d6\u0001R\u001a\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b\u00a8\u0006\u0016"}, d2={"Lorg/partiql/lang/ast/SelectProjectionValue;", "Lorg/partiql/lang/ast/SelectProjection;", "expr", "Lorg/partiql/lang/ast/ExprNode;", "(Lorg/partiql/lang/ast/ExprNode;)V", "children", "", "Lorg/partiql/lang/ast/AstNode;", "getChildren", "()Ljava/util/List;", "getExpr", "()Lorg/partiql/lang/ast/ExprNode;", "component1", "copy", "equals", "", "other", "", "hashCode", "", "toString", "", "lang"})
public final class SelectProjectionValue
extends SelectProjection {
    @NotNull
    private final List<AstNode> children;
    @NotNull
    private final ExprNode expr;

    @Override
    @NotNull
    public List<AstNode> getChildren() {
        return this.children;
    }

    @NotNull
    public final ExprNode getExpr() {
        return this.expr;
    }

    public SelectProjectionValue(@NotNull ExprNode expr) {
        Intrinsics.checkParameterIsNotNull(expr, "expr");
        super(null);
        this.expr = expr;
        this.children = CollectionsKt.listOf(this.expr);
    }

    @NotNull
    public final ExprNode component1() {
        return this.expr;
    }

    @NotNull
    public final SelectProjectionValue copy(@NotNull ExprNode expr) {
        Intrinsics.checkParameterIsNotNull(expr, "expr");
        return new SelectProjectionValue(expr);
    }

    public static /* synthetic */ SelectProjectionValue copy$default(SelectProjectionValue selectProjectionValue, ExprNode exprNode, int n, Object object) {
        if ((n & 1) != 0) {
            exprNode = selectProjectionValue.expr;
        }
        return selectProjectionValue.copy(exprNode);
    }

    @NotNull
    public String toString() {
        return "SelectProjectionValue(expr=" + this.expr + ")";
    }

    public int hashCode() {
        ExprNode exprNode = this.expr;
        return exprNode != null ? exprNode.hashCode() : 0;
    }

    public boolean equals(@Nullable Object object) {
        block3: {
            block2: {
                if (this == object) break block2;
                if (!(object instanceof SelectProjectionValue)) break block3;
                SelectProjectionValue selectProjectionValue = (SelectProjectionValue)object;
                if (!Intrinsics.areEqual(this.expr, selectProjectionValue.expr)) break block3;
            }
            return true;
        }
        return false;
    }
}

