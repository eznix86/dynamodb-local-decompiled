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
import org.partiql.lang.ast.FromSource;
import org.partiql.lang.ast.GroupBy;
import org.partiql.lang.ast.LetSource;
import org.partiql.lang.ast.MetaContainer;
import org.partiql.lang.ast.OrderBy;
import org.partiql.lang.ast.SelectProjection;
import org.partiql.lang.ast.SetQuantifier;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000`\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b \n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001Bo\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\t\u0012\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u0001\u0012\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\f\u0012\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u0001\u0012\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\u000f\u0012\n\b\u0002\u0010\u0010\u001a\u0004\u0018\u00010\u0001\u0012\u0006\u0010\u0011\u001a\u00020\u0012\u00a2\u0006\u0002\u0010\u0013J\t\u0010+\u001a\u00020\u0003H\u00c6\u0003J\t\u0010,\u001a\u00020\u0012H\u00c6\u0003J\t\u0010-\u001a\u00020\u0005H\u00c6\u0003J\t\u0010.\u001a\u00020\u0007H\u00c6\u0003J\u000b\u0010/\u001a\u0004\u0018\u00010\tH\u00c6\u0003J\u000b\u00100\u001a\u0004\u0018\u00010\u0001H\u00c6\u0003J\u000b\u00101\u001a\u0004\u0018\u00010\fH\u00c6\u0003J\u000b\u00102\u001a\u0004\u0018\u00010\u0001H\u00c6\u0003J\u000b\u00103\u001a\u0004\u0018\u00010\u000fH\u00c6\u0003J\u000b\u00104\u001a\u0004\u0018\u00010\u0001H\u00c6\u0003Jy\u00105\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00072\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\t2\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u00012\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\f2\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u00012\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\u000f2\n\b\u0002\u0010\u0010\u001a\u0004\u0018\u00010\u00012\b\b\u0002\u0010\u0011\u001a\u00020\u0012H\u00c6\u0001J\u0013\u00106\u001a\u0002072\b\u00108\u001a\u0004\u0018\u000109H\u00d6\u0003J\t\u0010:\u001a\u00020;H\u00d6\u0001J\t\u0010<\u001a\u00020=H\u00d6\u0001R\u001a\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00160\u0015X\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018R\u0011\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u001aR\u0013\u0010\b\u001a\u0004\u0018\u00010\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u001cR\u0013\u0010\u000b\u001a\u0004\u0018\u00010\f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u001eR\u0013\u0010\r\u001a\u0004\u0018\u00010\u0001\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010 R\u0013\u0010\u0010\u001a\u0004\u0018\u00010\u0001\u00a2\u0006\b\n\u0000\u001a\u0004\b!\u0010 R\u0014\u0010\u0011\u001a\u00020\u0012X\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\"\u0010#R\u0013\u0010\u000e\u001a\u0004\u0018\u00010\u000f\u00a2\u0006\b\n\u0000\u001a\u0004\b$\u0010%R\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b&\u0010'R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b(\u0010)R\u0013\u0010\n\u001a\u0004\u0018\u00010\u0001\u00a2\u0006\b\n\u0000\u001a\u0004\b*\u0010 \u00a8\u0006>"}, d2={"Lorg/partiql/lang/ast/Select;", "Lorg/partiql/lang/ast/ExprNode;", "setQuantifier", "Lorg/partiql/lang/ast/SetQuantifier;", "projection", "Lorg/partiql/lang/ast/SelectProjection;", "from", "Lorg/partiql/lang/ast/FromSource;", "fromLet", "Lorg/partiql/lang/ast/LetSource;", "where", "groupBy", "Lorg/partiql/lang/ast/GroupBy;", "having", "orderBy", "Lorg/partiql/lang/ast/OrderBy;", "limit", "metas", "Lorg/partiql/lang/ast/MetaContainer;", "(Lorg/partiql/lang/ast/SetQuantifier;Lorg/partiql/lang/ast/SelectProjection;Lorg/partiql/lang/ast/FromSource;Lorg/partiql/lang/ast/LetSource;Lorg/partiql/lang/ast/ExprNode;Lorg/partiql/lang/ast/GroupBy;Lorg/partiql/lang/ast/ExprNode;Lorg/partiql/lang/ast/OrderBy;Lorg/partiql/lang/ast/ExprNode;Lorg/partiql/lang/ast/MetaContainer;)V", "children", "", "Lorg/partiql/lang/ast/AstNode;", "getChildren", "()Ljava/util/List;", "getFrom", "()Lorg/partiql/lang/ast/FromSource;", "getFromLet", "()Lorg/partiql/lang/ast/LetSource;", "getGroupBy", "()Lorg/partiql/lang/ast/GroupBy;", "getHaving", "()Lorg/partiql/lang/ast/ExprNode;", "getLimit", "getMetas", "()Lorg/partiql/lang/ast/MetaContainer;", "getOrderBy", "()Lorg/partiql/lang/ast/OrderBy;", "getProjection", "()Lorg/partiql/lang/ast/SelectProjection;", "getSetQuantifier", "()Lorg/partiql/lang/ast/SetQuantifier;", "getWhere", "component1", "component10", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "equals", "", "other", "", "hashCode", "", "toString", "", "lang"})
public final class Select
extends ExprNode {
    @NotNull
    private final List<AstNode> children;
    @NotNull
    private final SetQuantifier setQuantifier;
    @NotNull
    private final SelectProjection projection;
    @NotNull
    private final FromSource from;
    @Nullable
    private final LetSource fromLet;
    @Nullable
    private final ExprNode where;
    @Nullable
    private final GroupBy groupBy;
    @Nullable
    private final ExprNode having;
    @Nullable
    private final OrderBy orderBy;
    @Nullable
    private final ExprNode limit;
    @NotNull
    private final MetaContainer metas;

    @Override
    @NotNull
    public List<AstNode> getChildren() {
        return this.children;
    }

    @NotNull
    public final SetQuantifier getSetQuantifier() {
        return this.setQuantifier;
    }

    @NotNull
    public final SelectProjection getProjection() {
        return this.projection;
    }

    @NotNull
    public final FromSource getFrom() {
        return this.from;
    }

    @Nullable
    public final LetSource getFromLet() {
        return this.fromLet;
    }

    @Nullable
    public final ExprNode getWhere() {
        return this.where;
    }

    @Nullable
    public final GroupBy getGroupBy() {
        return this.groupBy;
    }

    @Nullable
    public final ExprNode getHaving() {
        return this.having;
    }

    @Nullable
    public final OrderBy getOrderBy() {
        return this.orderBy;
    }

    @Nullable
    public final ExprNode getLimit() {
        return this.limit;
    }

    @Override
    @NotNull
    public MetaContainer getMetas() {
        return this.metas;
    }

    public Select(@NotNull SetQuantifier setQuantifier, @NotNull SelectProjection projection, @NotNull FromSource from2, @Nullable LetSource fromLet2, @Nullable ExprNode where2, @Nullable GroupBy groupBy2, @Nullable ExprNode having2, @Nullable OrderBy orderBy, @Nullable ExprNode limit2, @NotNull MetaContainer metas) {
        Intrinsics.checkParameterIsNotNull((Object)setQuantifier, "setQuantifier");
        Intrinsics.checkParameterIsNotNull(projection, "projection");
        Intrinsics.checkParameterIsNotNull(from2, "from");
        Intrinsics.checkParameterIsNotNull(metas, "metas");
        super(null);
        this.setQuantifier = setQuantifier;
        this.projection = projection;
        this.from = from2;
        this.fromLet = fromLet2;
        this.where = where2;
        this.groupBy = groupBy2;
        this.having = having2;
        this.orderBy = orderBy;
        this.limit = limit2;
        this.metas = metas;
        this.children = CollectionsKt.listOfNotNull(this.projection, this.from, this.fromLet, this.where, this.groupBy, this.having, this.orderBy, this.limit);
    }

    public /* synthetic */ Select(SetQuantifier setQuantifier, SelectProjection selectProjection, FromSource fromSource, LetSource letSource, ExprNode exprNode, GroupBy groupBy2, ExprNode exprNode2, OrderBy orderBy, ExprNode exprNode3, MetaContainer metaContainer, int n, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n & 1) != 0) {
            setQuantifier = SetQuantifier.ALL;
        }
        if ((n & 8) != 0) {
            letSource = null;
        }
        if ((n & 0x10) != 0) {
            exprNode = null;
        }
        if ((n & 0x20) != 0) {
            groupBy2 = null;
        }
        if ((n & 0x40) != 0) {
            exprNode2 = null;
        }
        if ((n & 0x80) != 0) {
            orderBy = null;
        }
        if ((n & 0x100) != 0) {
            exprNode3 = null;
        }
        this(setQuantifier, selectProjection, fromSource, letSource, exprNode, groupBy2, exprNode2, orderBy, exprNode3, metaContainer);
    }

    @NotNull
    public final SetQuantifier component1() {
        return this.setQuantifier;
    }

    @NotNull
    public final SelectProjection component2() {
        return this.projection;
    }

    @NotNull
    public final FromSource component3() {
        return this.from;
    }

    @Nullable
    public final LetSource component4() {
        return this.fromLet;
    }

    @Nullable
    public final ExprNode component5() {
        return this.where;
    }

    @Nullable
    public final GroupBy component6() {
        return this.groupBy;
    }

    @Nullable
    public final ExprNode component7() {
        return this.having;
    }

    @Nullable
    public final OrderBy component8() {
        return this.orderBy;
    }

    @Nullable
    public final ExprNode component9() {
        return this.limit;
    }

    @NotNull
    public final MetaContainer component10() {
        return this.getMetas();
    }

    @NotNull
    public final Select copy(@NotNull SetQuantifier setQuantifier, @NotNull SelectProjection projection, @NotNull FromSource from2, @Nullable LetSource fromLet2, @Nullable ExprNode where2, @Nullable GroupBy groupBy2, @Nullable ExprNode having2, @Nullable OrderBy orderBy, @Nullable ExprNode limit2, @NotNull MetaContainer metas) {
        Intrinsics.checkParameterIsNotNull((Object)setQuantifier, "setQuantifier");
        Intrinsics.checkParameterIsNotNull(projection, "projection");
        Intrinsics.checkParameterIsNotNull(from2, "from");
        Intrinsics.checkParameterIsNotNull(metas, "metas");
        return new Select(setQuantifier, projection, from2, fromLet2, where2, groupBy2, having2, orderBy, limit2, metas);
    }

    public static /* synthetic */ Select copy$default(Select select, SetQuantifier setQuantifier, SelectProjection selectProjection, FromSource fromSource, LetSource letSource, ExprNode exprNode, GroupBy groupBy2, ExprNode exprNode2, OrderBy orderBy, ExprNode exprNode3, MetaContainer metaContainer, int n, Object object) {
        if ((n & 1) != 0) {
            setQuantifier = select.setQuantifier;
        }
        if ((n & 2) != 0) {
            selectProjection = select.projection;
        }
        if ((n & 4) != 0) {
            fromSource = select.from;
        }
        if ((n & 8) != 0) {
            letSource = select.fromLet;
        }
        if ((n & 0x10) != 0) {
            exprNode = select.where;
        }
        if ((n & 0x20) != 0) {
            groupBy2 = select.groupBy;
        }
        if ((n & 0x40) != 0) {
            exprNode2 = select.having;
        }
        if ((n & 0x80) != 0) {
            orderBy = select.orderBy;
        }
        if ((n & 0x100) != 0) {
            exprNode3 = select.limit;
        }
        if ((n & 0x200) != 0) {
            metaContainer = select.getMetas();
        }
        return select.copy(setQuantifier, selectProjection, fromSource, letSource, exprNode, groupBy2, exprNode2, orderBy, exprNode3, metaContainer);
    }

    @NotNull
    public String toString() {
        return "Select(setQuantifier=" + (Object)((Object)this.setQuantifier) + ", projection=" + this.projection + ", from=" + this.from + ", fromLet=" + this.fromLet + ", where=" + this.where + ", groupBy=" + this.groupBy + ", having=" + this.having + ", orderBy=" + this.orderBy + ", limit=" + this.limit + ", metas=" + this.getMetas() + ")";
    }

    public int hashCode() {
        SetQuantifier setQuantifier = this.setQuantifier;
        SelectProjection selectProjection = this.projection;
        FromSource fromSource = this.from;
        LetSource letSource = this.fromLet;
        ExprNode exprNode = this.where;
        GroupBy groupBy2 = this.groupBy;
        ExprNode exprNode2 = this.having;
        OrderBy orderBy = this.orderBy;
        ExprNode exprNode3 = this.limit;
        MetaContainer metaContainer = this.getMetas();
        return (((((((((setQuantifier != null ? ((Object)((Object)setQuantifier)).hashCode() : 0) * 31 + (selectProjection != null ? selectProjection.hashCode() : 0)) * 31 + (fromSource != null ? fromSource.hashCode() : 0)) * 31 + (letSource != null ? ((Object)letSource).hashCode() : 0)) * 31 + (exprNode != null ? exprNode.hashCode() : 0)) * 31 + (groupBy2 != null ? ((Object)groupBy2).hashCode() : 0)) * 31 + (exprNode2 != null ? exprNode2.hashCode() : 0)) * 31 + (orderBy != null ? ((Object)orderBy).hashCode() : 0)) * 31 + (exprNode3 != null ? exprNode3.hashCode() : 0)) * 31 + (metaContainer != null ? metaContainer.hashCode() : 0);
    }

    public boolean equals(@Nullable Object object) {
        block3: {
            block2: {
                if (this == object) break block2;
                if (!(object instanceof Select)) break block3;
                Select select = (Select)object;
                if (!Intrinsics.areEqual((Object)this.setQuantifier, (Object)select.setQuantifier) || !Intrinsics.areEqual(this.projection, select.projection) || !Intrinsics.areEqual(this.from, select.from) || !Intrinsics.areEqual(this.fromLet, select.fromLet) || !Intrinsics.areEqual(this.where, select.where) || !Intrinsics.areEqual(this.groupBy, select.groupBy) || !Intrinsics.areEqual(this.having, select.having) || !Intrinsics.areEqual(this.orderBy, select.orderBy) || !Intrinsics.areEqual(this.limit, select.limit) || !Intrinsics.areEqual(this.getMetas(), select.getMetas())) break block3;
            }
            return true;
        }
        return false;
    }
}

