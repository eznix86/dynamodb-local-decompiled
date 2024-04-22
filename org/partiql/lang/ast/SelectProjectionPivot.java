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

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0005J\t\u0010\u000e\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u000f\u001a\u00020\u0003H\u00c6\u0003J\u001d\u0010\u0010\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0003H\u00c6\u0001J\u0013\u0010\u0011\u001a\u00020\u00122\b\u0010\u0013\u001a\u0004\u0018\u00010\u0014H\u00d6\u0003J\t\u0010\u0015\u001a\u00020\u0016H\u00d6\u0001J\t\u0010\u0017\u001a\u00020\u0018H\u00d6\u0001R\u001a\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007X\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\f\u00a8\u0006\u0019"}, d2={"Lorg/partiql/lang/ast/SelectProjectionPivot;", "Lorg/partiql/lang/ast/SelectProjection;", "nameExpr", "Lorg/partiql/lang/ast/ExprNode;", "valueExpr", "(Lorg/partiql/lang/ast/ExprNode;Lorg/partiql/lang/ast/ExprNode;)V", "children", "", "Lorg/partiql/lang/ast/AstNode;", "getChildren", "()Ljava/util/List;", "getNameExpr", "()Lorg/partiql/lang/ast/ExprNode;", "getValueExpr", "component1", "component2", "copy", "equals", "", "other", "", "hashCode", "", "toString", "", "lang"})
public final class SelectProjectionPivot
extends SelectProjection {
    @NotNull
    private final List<AstNode> children;
    @NotNull
    private final ExprNode nameExpr;
    @NotNull
    private final ExprNode valueExpr;

    @Override
    @NotNull
    public List<AstNode> getChildren() {
        return this.children;
    }

    @NotNull
    public final ExprNode getNameExpr() {
        return this.nameExpr;
    }

    @NotNull
    public final ExprNode getValueExpr() {
        return this.valueExpr;
    }

    public SelectProjectionPivot(@NotNull ExprNode nameExpr, @NotNull ExprNode valueExpr) {
        Intrinsics.checkParameterIsNotNull(nameExpr, "nameExpr");
        Intrinsics.checkParameterIsNotNull(valueExpr, "valueExpr");
        super(null);
        this.nameExpr = nameExpr;
        this.valueExpr = valueExpr;
        this.children = CollectionsKt.listOf(this.nameExpr, this.valueExpr);
    }

    @NotNull
    public final ExprNode component1() {
        return this.nameExpr;
    }

    @NotNull
    public final ExprNode component2() {
        return this.valueExpr;
    }

    @NotNull
    public final SelectProjectionPivot copy(@NotNull ExprNode nameExpr, @NotNull ExprNode valueExpr) {
        Intrinsics.checkParameterIsNotNull(nameExpr, "nameExpr");
        Intrinsics.checkParameterIsNotNull(valueExpr, "valueExpr");
        return new SelectProjectionPivot(nameExpr, valueExpr);
    }

    public static /* synthetic */ SelectProjectionPivot copy$default(SelectProjectionPivot selectProjectionPivot, ExprNode exprNode, ExprNode exprNode2, int n, Object object) {
        if ((n & 1) != 0) {
            exprNode = selectProjectionPivot.nameExpr;
        }
        if ((n & 2) != 0) {
            exprNode2 = selectProjectionPivot.valueExpr;
        }
        return selectProjectionPivot.copy(exprNode, exprNode2);
    }

    @NotNull
    public String toString() {
        return "SelectProjectionPivot(nameExpr=" + this.nameExpr + ", valueExpr=" + this.valueExpr + ")";
    }

    public int hashCode() {
        ExprNode exprNode = this.nameExpr;
        ExprNode exprNode2 = this.valueExpr;
        return (exprNode != null ? exprNode.hashCode() : 0) * 31 + (exprNode2 != null ? exprNode2.hashCode() : 0);
    }

    public boolean equals(@Nullable Object object) {
        block3: {
            block2: {
                if (this == object) break block2;
                if (!(object instanceof SelectProjectionPivot)) break block3;
                SelectProjectionPivot selectProjectionPivot = (SelectProjectionPivot)object;
                if (!Intrinsics.areEqual(this.nameExpr, selectProjectionPivot.nameExpr) || !Intrinsics.areEqual(this.valueExpr, selectProjectionPivot.valueExpr)) break block3;
            }
            return true;
        }
        return false;
    }
}

