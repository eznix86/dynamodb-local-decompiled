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
import org.partiql.lang.ast.SearchedCaseWhen;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B%\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0001\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\bJ\u000f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003H\u00c6\u0003J\u000b\u0010\u0013\u001a\u0004\u0018\u00010\u0001H\u00c6\u0003J\t\u0010\u0014\u001a\u00020\u0007H\u00c6\u0003J/\u0010\u0015\u001a\u00020\u00002\u000e\b\u0002\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u00012\b\b\u0002\u0010\u0006\u001a\u00020\u0007H\u00c6\u0001J\u0013\u0010\u0016\u001a\u00020\u00172\b\u0010\u0018\u001a\u0004\u0018\u00010\u0019H\u00d6\u0003J\t\u0010\u001a\u001a\u00020\u001bH\u00d6\u0001J\t\u0010\u001c\u001a\u00020\u001dH\u00d6\u0001R\u001a\u0010\t\u001a\b\u0012\u0004\u0012\u00020\n0\u0003X\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0013\u0010\u0005\u001a\u0004\u0018\u00010\u0001\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0014\u0010\u0006\u001a\u00020\u0007X\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\f\u00a8\u0006\u001e"}, d2={"Lorg/partiql/lang/ast/SearchedCase;", "Lorg/partiql/lang/ast/ExprNode;", "whenClauses", "", "Lorg/partiql/lang/ast/SearchedCaseWhen;", "elseExpr", "metas", "Lorg/partiql/lang/ast/MetaContainer;", "(Ljava/util/List;Lorg/partiql/lang/ast/ExprNode;Lorg/partiql/lang/ast/MetaContainer;)V", "children", "Lorg/partiql/lang/ast/AstNode;", "getChildren", "()Ljava/util/List;", "getElseExpr", "()Lorg/partiql/lang/ast/ExprNode;", "getMetas", "()Lorg/partiql/lang/ast/MetaContainer;", "getWhenClauses", "component1", "component2", "component3", "copy", "equals", "", "other", "", "hashCode", "", "toString", "", "lang"})
public final class SearchedCase
extends ExprNode {
    @NotNull
    private final List<AstNode> children;
    @NotNull
    private final List<SearchedCaseWhen> whenClauses;
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
    public final List<SearchedCaseWhen> getWhenClauses() {
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

    public SearchedCase(@NotNull List<SearchedCaseWhen> whenClauses, @Nullable ExprNode elseExpr, @NotNull MetaContainer metas) {
        Intrinsics.checkParameterIsNotNull(whenClauses, "whenClauses");
        Intrinsics.checkParameterIsNotNull(metas, "metas");
        super(null);
        this.whenClauses = whenClauses;
        this.elseExpr = elseExpr;
        this.metas = metas;
        this.children = CollectionsKt.plus((Collection)this.whenClauses, (Iterable)CollectionsKt.listOfNotNull(this.elseExpr));
    }

    @NotNull
    public final List<SearchedCaseWhen> component1() {
        return this.whenClauses;
    }

    @Nullable
    public final ExprNode component2() {
        return this.elseExpr;
    }

    @NotNull
    public final MetaContainer component3() {
        return this.getMetas();
    }

    @NotNull
    public final SearchedCase copy(@NotNull List<SearchedCaseWhen> whenClauses, @Nullable ExprNode elseExpr, @NotNull MetaContainer metas) {
        Intrinsics.checkParameterIsNotNull(whenClauses, "whenClauses");
        Intrinsics.checkParameterIsNotNull(metas, "metas");
        return new SearchedCase(whenClauses, elseExpr, metas);
    }

    public static /* synthetic */ SearchedCase copy$default(SearchedCase searchedCase, List list, ExprNode exprNode, MetaContainer metaContainer, int n, Object object) {
        if ((n & 1) != 0) {
            list = searchedCase.whenClauses;
        }
        if ((n & 2) != 0) {
            exprNode = searchedCase.elseExpr;
        }
        if ((n & 4) != 0) {
            metaContainer = searchedCase.getMetas();
        }
        return searchedCase.copy(list, exprNode, metaContainer);
    }

    @NotNull
    public String toString() {
        return "SearchedCase(whenClauses=" + this.whenClauses + ", elseExpr=" + this.elseExpr + ", metas=" + this.getMetas() + ")";
    }

    public int hashCode() {
        List<SearchedCaseWhen> list = this.whenClauses;
        ExprNode exprNode = this.elseExpr;
        MetaContainer metaContainer = this.getMetas();
        return ((list != null ? ((Object)list).hashCode() : 0) * 31 + (exprNode != null ? exprNode.hashCode() : 0)) * 31 + (metaContainer != null ? metaContainer.hashCode() : 0);
    }

    public boolean equals(@Nullable Object object) {
        block3: {
            block2: {
                if (this == object) break block2;
                if (!(object instanceof SearchedCase)) break block3;
                SearchedCase searchedCase = (SearchedCase)object;
                if (!Intrinsics.areEqual(this.whenClauses, searchedCase.whenClauses) || !Intrinsics.areEqual(this.elseExpr, searchedCase.elseExpr) || !Intrinsics.areEqual(this.getMetas(), searchedCase.getMetas())) break block3;
            }
            return true;
        }
        return false;
    }
}

