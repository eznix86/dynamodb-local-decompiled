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
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.partiql.lang.ast.AstNode;
import org.partiql.lang.ast.ExprNode;
import org.partiql.lang.ast.SelectListItem;
import org.partiql.lang.ast.SymbolicName;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u0019\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u00a2\u0006\u0002\u0010\u0006J\t\u0010\u0010\u001a\u00020\u0003H\u00c6\u0003J\u000b\u0010\u0011\u001a\u0004\u0018\u00010\u0005H\u00c6\u0003J\u001f\u0010\u0012\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005H\u00c6\u0001J\u0013\u0010\u0013\u001a\u00020\u00142\b\u0010\u0015\u001a\u0004\u0018\u00010\u0016H\u00d6\u0003J\t\u0010\u0017\u001a\u00020\u0018H\u00d6\u0001J\t\u0010\u0019\u001a\u00020\u001aH\u00d6\u0001R\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u001a\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\nX\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000f\u00a8\u0006\u001b"}, d2={"Lorg/partiql/lang/ast/SelectListItemExpr;", "Lorg/partiql/lang/ast/SelectListItem;", "expr", "Lorg/partiql/lang/ast/ExprNode;", "asName", "Lorg/partiql/lang/ast/SymbolicName;", "(Lorg/partiql/lang/ast/ExprNode;Lorg/partiql/lang/ast/SymbolicName;)V", "getAsName", "()Lorg/partiql/lang/ast/SymbolicName;", "children", "", "Lorg/partiql/lang/ast/AstNode;", "getChildren", "()Ljava/util/List;", "getExpr", "()Lorg/partiql/lang/ast/ExprNode;", "component1", "component2", "copy", "equals", "", "other", "", "hashCode", "", "toString", "", "lang"})
public final class SelectListItemExpr
extends SelectListItem {
    @NotNull
    private final List<AstNode> children;
    @NotNull
    private final ExprNode expr;
    @Nullable
    private final SymbolicName asName;

    @Override
    @NotNull
    public List<AstNode> getChildren() {
        return this.children;
    }

    @NotNull
    public final ExprNode getExpr() {
        return this.expr;
    }

    @Nullable
    public final SymbolicName getAsName() {
        return this.asName;
    }

    public SelectListItemExpr(@NotNull ExprNode expr, @Nullable SymbolicName asName) {
        Intrinsics.checkParameterIsNotNull(expr, "expr");
        super(null);
        this.expr = expr;
        this.asName = asName;
        this.children = CollectionsKt.listOf(this.expr);
    }

    public /* synthetic */ SelectListItemExpr(ExprNode exprNode, SymbolicName symbolicName, int n, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n & 2) != 0) {
            symbolicName = null;
        }
        this(exprNode, symbolicName);
    }

    @NotNull
    public final ExprNode component1() {
        return this.expr;
    }

    @Nullable
    public final SymbolicName component2() {
        return this.asName;
    }

    @NotNull
    public final SelectListItemExpr copy(@NotNull ExprNode expr, @Nullable SymbolicName asName) {
        Intrinsics.checkParameterIsNotNull(expr, "expr");
        return new SelectListItemExpr(expr, asName);
    }

    public static /* synthetic */ SelectListItemExpr copy$default(SelectListItemExpr selectListItemExpr, ExprNode exprNode, SymbolicName symbolicName, int n, Object object) {
        if ((n & 1) != 0) {
            exprNode = selectListItemExpr.expr;
        }
        if ((n & 2) != 0) {
            symbolicName = selectListItemExpr.asName;
        }
        return selectListItemExpr.copy(exprNode, symbolicName);
    }

    @NotNull
    public String toString() {
        return "SelectListItemExpr(expr=" + this.expr + ", asName=" + this.asName + ")";
    }

    public int hashCode() {
        ExprNode exprNode = this.expr;
        SymbolicName symbolicName = this.asName;
        return (exprNode != null ? exprNode.hashCode() : 0) * 31 + (symbolicName != null ? ((Object)symbolicName).hashCode() : 0);
    }

    public boolean equals(@Nullable Object object) {
        block3: {
            block2: {
                if (this == object) break block2;
                if (!(object instanceof SelectListItemExpr)) break block3;
                SelectListItemExpr selectListItemExpr = (SelectListItemExpr)object;
                if (!Intrinsics.areEqual(this.expr, selectListItemExpr.expr) || !Intrinsics.areEqual(this.asName, selectListItemExpr.asName)) break block3;
            }
            return true;
        }
        return false;
    }
}

