/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package org.partiql.lang.ast;

import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.partiql.lang.ast.AstNode;
import org.partiql.lang.ast.ExprNode;
import org.partiql.lang.ast.MetaContainer;
import org.partiql.lang.ast.SimpleCaseWhen;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u000e\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B-\u0012\u0006\u0010\u0002\u001a\u00020\u0001\u0012\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0001\u0012\u0006\u0010\u0007\u001a\u00020\b\u00a2\u0006\u0002\u0010\tJ\t\u0010\u0014\u001a\u00020\u0001H\u00c6\u0003J\u000f\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004H\u00c6\u0003J\u000b\u0010\u0016\u001a\u0004\u0018\u00010\u0001H\u00c6\u0003J\t\u0010\u0017\u001a\u00020\bH\u00c6\u0003J9\u0010\u0018\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00012\u000e\b\u0002\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u00042\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u00012\b\b\u0002\u0010\u0007\u001a\u00020\bH\u00c6\u0001J\u0013\u0010\u0019\u001a\u00020\u001a2\b\u0010\u001b\u001a\u0004\u0018\u00010\u001cH\u00d6\u0003J\t\u0010\u001d\u001a\u00020\u001eH\u00d6\u0001J\t\u0010\u001f\u001a\u00020 H\u00d6\u0001R\u001a\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u000b0\u0004X\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0013\u0010\u0006\u001a\u0004\u0018\u00010\u0001\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0014\u0010\u0007\u001a\u00020\bX\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0011\u0010\u0002\u001a\u00020\u0001\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u000fR\u0017\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\r\u00a8\u0006!"}, d2={"Lorg/partiql/lang/ast/SimpleCase;", "Lorg/partiql/lang/ast/ExprNode;", "valueExpr", "whenClauses", "", "Lorg/partiql/lang/ast/SimpleCaseWhen;", "elseExpr", "metas", "Lorg/partiql/lang/ast/MetaContainer;", "(Lorg/partiql/lang/ast/ExprNode;Ljava/util/List;Lorg/partiql/lang/ast/ExprNode;Lorg/partiql/lang/ast/MetaContainer;)V", "children", "Lorg/partiql/lang/ast/AstNode;", "getChildren", "()Ljava/util/List;", "getElseExpr", "()Lorg/partiql/lang/ast/ExprNode;", "getMetas", "()Lorg/partiql/lang/ast/MetaContainer;", "getValueExpr", "getWhenClauses", "component1", "component2", "component3", "component4", "copy", "equals", "", "other", "", "hashCode", "", "toString", "", "lang"})
public final class SimpleCase
extends ExprNode {
    @NotNull
    private final List<AstNode> children;
    @NotNull
    private final ExprNode valueExpr;
    @NotNull
    private final List<SimpleCaseWhen> whenClauses;
    @Nullable
    private final ExprNode elseExpr;
    @NotNull
    private final MetaContainer metas;

    @Override
    @NotNull
    public List<AstNode> getChildren() {
        return this.children;
    }

    @NotNull
    public final ExprNode getValueExpr() {
        return this.valueExpr;
    }

    @NotNull
    public final List<SimpleCaseWhen> getWhenClauses() {
        return this.whenClauses;
    }

    @Nullable
    public final ExprNode getElseExpr() {
        return this.elseExpr;
    }

    @Override
    @NotNull
    public MetaContainer getMetas() {
        return this.metas;
    }

    public SimpleCase(@NotNull ExprNode valueExpr, @NotNull List<SimpleCaseWhen> whenClauses, @Nullable ExprNode elseExpr, @NotNull MetaContainer metas) {
        Intrinsics.checkParameterIsNotNull(valueExpr, "valueExpr");
        Intrinsics.checkParameterIsNotNull(whenClauses, "whenClauses");
        Intrinsics.checkParameterIsNotNull(metas, "metas");
        super(null);
        this.valueExpr = valueExpr;
        this.whenClauses = whenClauses;
        this.elseExpr = elseExpr;
        this.metas = metas;
        this.children = CollectionsKt.plus((Collection)CollectionsKt.plus((Collection)CollectionsKt.listOf(this.valueExpr), (Iterable)this.whenClauses), (Iterable)CollectionsKt.listOfNotNull(this.elseExpr));
    }

    @NotNull
    public final ExprNode component1() {
        return this.valueExpr;
    }

    @NotNull
    public final List<SimpleCaseWhen> component2() {
        return this.whenClauses;
    }

    @Nullable
    public final ExprNode component3() {
        return this.elseExpr;
    }

    @NotNull
    public final MetaContainer component4() {
        return this.getMetas();
    }

    @NotNull
    public final SimpleCase copy(@NotNull ExprNode valueExpr, @NotNull List<SimpleCaseWhen> whenClauses, @Nullable ExprNode elseExpr, @NotNull MetaContainer metas) {
        Intrinsics.checkParameterIsNotNull(valueExpr, "valueExpr");
        Intrinsics.checkParameterIsNotNull(whenClauses, "whenClauses");
        Intrinsics.checkParameterIsNotNull(metas, "metas");
        return new SimpleCase(valueExpr, whenClauses, elseExpr, metas);
    }

    public static /* synthetic */ SimpleCase copy$default(SimpleCase simpleCase, ExprNode exprNode, List list, ExprNode exprNode2, MetaContainer metaContainer, int n, Object object) {
        if ((n & 1) != 0) {
            exprNode = simpleCase.valueExpr;
        }
        if ((n & 2) != 0) {
            list = simpleCase.whenClauses;
        }
        if ((n & 4) != 0) {
            exprNode2 = simpleCase.elseExpr;
        }
        if ((n & 8) != 0) {
            metaContainer = simpleCase.getMetas();
        }
        return simpleCase.copy(exprNode, list, exprNode2, metaContainer);
    }

    @NotNull
    public String toString() {
        return "SimpleCase(valueExpr=" + this.valueExpr + ", whenClauses=" + this.whenClauses + ", elseExpr=" + this.elseExpr + ", metas=" + this.getMetas() + ")";
    }

    public int hashCode() {
        ExprNode exprNode = this.valueExpr;
        List<SimpleCaseWhen> list = this.whenClauses;
        ExprNode exprNode2 = this.elseExpr;
        MetaContainer metaContainer = this.getMetas();
        return (((exprNode != null ? exprNode.hashCode() : 0) * 31 + (list != null ? ((Object)list).hashCode() : 0)) * 31 + (exprNode2 != null ? exprNode2.hashCode() : 0)) * 31 + (metaContainer != null ? metaContainer.hashCode() : 0);
    }

    public boolean equals(@Nullable Object object) {
        block3: {
            block2: {
                if (this == object) break block2;
                if (!(object instanceof SimpleCase)) break block3;
                SimpleCase simpleCase = (SimpleCase)object;
                if (!Intrinsics.areEqual(this.valueExpr, simpleCase.valueExpr) || !Intrinsics.areEqual(this.whenClauses, simpleCase.whenClauses) || !Intrinsics.areEqual(this.elseExpr, simpleCase.elseExpr) || !Intrinsics.areEqual(this.getMetas(), simpleCase.getMetas())) break block3;
            }
            return true;
        }
        return false;
    }
}

