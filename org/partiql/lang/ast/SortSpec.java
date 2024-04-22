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
import org.partiql.lang.ast.OrderingSpec;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\n\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\t\u0010\u000f\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0010\u001a\u00020\u0005H\u00c6\u0003J\u001d\u0010\u0011\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0005H\u00c6\u0001J\u0013\u0010\u0012\u001a\u00020\u00132\b\u0010\u0014\u001a\u0004\u0018\u00010\u0015H\u00d6\u0003J\t\u0010\u0016\u001a\u00020\u0017H\u00d6\u0001J\t\u0010\u0018\u001a\u00020\u0019H\u00d6\u0001R\u001a\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00010\bX\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000e\u00a8\u0006\u001a"}, d2={"Lorg/partiql/lang/ast/SortSpec;", "Lorg/partiql/lang/ast/AstNode;", "expr", "Lorg/partiql/lang/ast/ExprNode;", "orderingSpec", "Lorg/partiql/lang/ast/OrderingSpec;", "(Lorg/partiql/lang/ast/ExprNode;Lorg/partiql/lang/ast/OrderingSpec;)V", "children", "", "getChildren", "()Ljava/util/List;", "getExpr", "()Lorg/partiql/lang/ast/ExprNode;", "getOrderingSpec", "()Lorg/partiql/lang/ast/OrderingSpec;", "component1", "component2", "copy", "equals", "", "other", "", "hashCode", "", "toString", "", "lang"})
public final class SortSpec
extends AstNode {
    @NotNull
    private final List<AstNode> children;
    @NotNull
    private final ExprNode expr;
    @NotNull
    private final OrderingSpec orderingSpec;

    @Override
    @NotNull
    public List<AstNode> getChildren() {
        return this.children;
    }

    @NotNull
    public final ExprNode getExpr() {
        return this.expr;
    }

    @NotNull
    public final OrderingSpec getOrderingSpec() {
        return this.orderingSpec;
    }

    public SortSpec(@NotNull ExprNode expr, @NotNull OrderingSpec orderingSpec) {
        Intrinsics.checkParameterIsNotNull(expr, "expr");
        Intrinsics.checkParameterIsNotNull((Object)orderingSpec, "orderingSpec");
        super(null);
        this.expr = expr;
        this.orderingSpec = orderingSpec;
        this.children = CollectionsKt.listOf(this.expr);
    }

    @NotNull
    public final ExprNode component1() {
        return this.expr;
    }

    @NotNull
    public final OrderingSpec component2() {
        return this.orderingSpec;
    }

    @NotNull
    public final SortSpec copy(@NotNull ExprNode expr, @NotNull OrderingSpec orderingSpec) {
        Intrinsics.checkParameterIsNotNull(expr, "expr");
        Intrinsics.checkParameterIsNotNull((Object)orderingSpec, "orderingSpec");
        return new SortSpec(expr, orderingSpec);
    }

    public static /* synthetic */ SortSpec copy$default(SortSpec sortSpec, ExprNode exprNode, OrderingSpec orderingSpec, int n, Object object) {
        if ((n & 1) != 0) {
            exprNode = sortSpec.expr;
        }
        if ((n & 2) != 0) {
            orderingSpec = sortSpec.orderingSpec;
        }
        return sortSpec.copy(exprNode, orderingSpec);
    }

    @NotNull
    public String toString() {
        return "SortSpec(expr=" + this.expr + ", orderingSpec=" + (Object)((Object)this.orderingSpec) + ")";
    }

    public int hashCode() {
        ExprNode exprNode = this.expr;
        OrderingSpec orderingSpec = this.orderingSpec;
        return (exprNode != null ? exprNode.hashCode() : 0) * 31 + (orderingSpec != null ? ((Object)((Object)orderingSpec)).hashCode() : 0);
    }

    public boolean equals(@Nullable Object object) {
        block3: {
            block2: {
                if (this == object) break block2;
                if (!(object instanceof SortSpec)) break block3;
                SortSpec sortSpec = (SortSpec)object;
                if (!Intrinsics.areEqual(this.expr, sortSpec.expr) || !Intrinsics.areEqual((Object)this.orderingSpec, (Object)sortSpec.orderingSpec)) break block3;
            }
            return true;
        }
        return false;
    }
}

