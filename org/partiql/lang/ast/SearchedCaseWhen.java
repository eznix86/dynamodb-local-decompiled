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

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\b\t\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0005J\t\u0010\r\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u000e\u001a\u00020\u0003H\u00c6\u0003J\u001d\u0010\u000f\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0003H\u00c6\u0001J\u0013\u0010\u0010\u001a\u00020\u00112\b\u0010\u0012\u001a\u0004\u0018\u00010\u0013H\u00d6\u0003J\t\u0010\u0014\u001a\u00020\u0015H\u00d6\u0001J\t\u0010\u0016\u001a\u00020\u0017H\u00d6\u0001R\u001a\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00010\u0007X\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\u000b\u00a8\u0006\u0018"}, d2={"Lorg/partiql/lang/ast/SearchedCaseWhen;", "Lorg/partiql/lang/ast/AstNode;", "condition", "Lorg/partiql/lang/ast/ExprNode;", "thenExpr", "(Lorg/partiql/lang/ast/ExprNode;Lorg/partiql/lang/ast/ExprNode;)V", "children", "", "getChildren", "()Ljava/util/List;", "getCondition", "()Lorg/partiql/lang/ast/ExprNode;", "getThenExpr", "component1", "component2", "copy", "equals", "", "other", "", "hashCode", "", "toString", "", "lang"})
public final class SearchedCaseWhen
extends AstNode {
    @NotNull
    private final List<AstNode> children;
    @NotNull
    private final ExprNode condition;
    @NotNull
    private final ExprNode thenExpr;

    @Override
    @NotNull
    public List<AstNode> getChildren() {
        return this.children;
    }

    @NotNull
    public final ExprNode getCondition() {
        return this.condition;
    }

    @NotNull
    public final ExprNode getThenExpr() {
        return this.thenExpr;
    }

    public SearchedCaseWhen(@NotNull ExprNode condition, @NotNull ExprNode thenExpr) {
        Intrinsics.checkParameterIsNotNull(condition, "condition");
        Intrinsics.checkParameterIsNotNull(thenExpr, "thenExpr");
        super(null);
        this.condition = condition;
        this.thenExpr = thenExpr;
        this.children = CollectionsKt.listOf(this.condition, this.thenExpr);
    }

    @NotNull
    public final ExprNode component1() {
        return this.condition;
    }

    @NotNull
    public final ExprNode component2() {
        return this.thenExpr;
    }

    @NotNull
    public final SearchedCaseWhen copy(@NotNull ExprNode condition, @NotNull ExprNode thenExpr) {
        Intrinsics.checkParameterIsNotNull(condition, "condition");
        Intrinsics.checkParameterIsNotNull(thenExpr, "thenExpr");
        return new SearchedCaseWhen(condition, thenExpr);
    }

    public static /* synthetic */ SearchedCaseWhen copy$default(SearchedCaseWhen searchedCaseWhen, ExprNode exprNode, ExprNode exprNode2, int n, Object object) {
        if ((n & 1) != 0) {
            exprNode = searchedCaseWhen.condition;
        }
        if ((n & 2) != 0) {
            exprNode2 = searchedCaseWhen.thenExpr;
        }
        return searchedCaseWhen.copy(exprNode, exprNode2);
    }

    @NotNull
    public String toString() {
        return "SearchedCaseWhen(condition=" + this.condition + ", thenExpr=" + this.thenExpr + ")";
    }

    public int hashCode() {
        ExprNode exprNode = this.condition;
        ExprNode exprNode2 = this.thenExpr;
        return (exprNode != null ? exprNode.hashCode() : 0) * 31 + (exprNode2 != null ? exprNode2.hashCode() : 0);
    }

    public boolean equals(@Nullable Object object) {
        block3: {
            block2: {
                if (this == object) break block2;
                if (!(object instanceof SearchedCaseWhen)) break block3;
                SearchedCaseWhen searchedCaseWhen = (SearchedCaseWhen)object;
                if (!Intrinsics.areEqual(this.condition, searchedCaseWhen.condition) || !Intrinsics.areEqual(this.thenExpr, searchedCaseWhen.thenExpr)) break block3;
            }
            return true;
        }
        return false;
    }
}

