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
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.partiql.lang.ast.AstNode;
import org.partiql.lang.ast.DmlOpList;
import org.partiql.lang.ast.ExprNode;
import org.partiql.lang.ast.FromSource;
import org.partiql.lang.ast.MetaContainer;
import org.partiql.lang.ast.ReturningExpr;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000J\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0013\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B9\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0001\u0012\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\b\u0012\u0006\u0010\t\u001a\u00020\n\u00a2\u0006\u0002\u0010\u000bJ\t\u0010\u001b\u001a\u00020\u0003H\u00c6\u0003J\u000b\u0010\u001c\u001a\u0004\u0018\u00010\u0005H\u00c6\u0003J\u000b\u0010\u001d\u001a\u0004\u0018\u00010\u0001H\u00c6\u0003J\u000b\u0010\u001e\u001a\u0004\u0018\u00010\bH\u00c6\u0003J\t\u0010\u001f\u001a\u00020\nH\u00c6\u0003JA\u0010 \u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00052\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u00012\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\b2\b\b\u0002\u0010\t\u001a\u00020\nH\u00c6\u0001J\u0013\u0010!\u001a\u00020\"2\b\u0010#\u001a\u0004\u0018\u00010$H\u00d6\u0003J\t\u0010%\u001a\u00020&H\u00d6\u0001J\t\u0010'\u001a\u00020(H\u00d6\u0001R\u001a\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000e0\rX\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u0014\u0010\t\u001a\u00020\nX\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u0013\u0010\u0007\u001a\u0004\u0018\u00010\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018R\u0013\u0010\u0006\u001a\u0004\u0018\u00010\u0001\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u001a\u00a8\u0006)"}, d2={"Lorg/partiql/lang/ast/DataManipulation;", "Lorg/partiql/lang/ast/ExprNode;", "dmlOperations", "Lorg/partiql/lang/ast/DmlOpList;", "from", "Lorg/partiql/lang/ast/FromSource;", "where", "returning", "Lorg/partiql/lang/ast/ReturningExpr;", "metas", "Lorg/partiql/lang/ast/MetaContainer;", "(Lorg/partiql/lang/ast/DmlOpList;Lorg/partiql/lang/ast/FromSource;Lorg/partiql/lang/ast/ExprNode;Lorg/partiql/lang/ast/ReturningExpr;Lorg/partiql/lang/ast/MetaContainer;)V", "children", "", "Lorg/partiql/lang/ast/AstNode;", "getChildren", "()Ljava/util/List;", "getDmlOperations", "()Lorg/partiql/lang/ast/DmlOpList;", "getFrom", "()Lorg/partiql/lang/ast/FromSource;", "getMetas", "()Lorg/partiql/lang/ast/MetaContainer;", "getReturning", "()Lorg/partiql/lang/ast/ReturningExpr;", "getWhere", "()Lorg/partiql/lang/ast/ExprNode;", "component1", "component2", "component3", "component4", "component5", "copy", "equals", "", "other", "", "hashCode", "", "toString", "", "lang"})
public final class DataManipulation
extends ExprNode {
    @NotNull
    private final List<AstNode> children;
    @NotNull
    private final DmlOpList dmlOperations;
    @Nullable
    private final FromSource from;
    @Nullable
    private final ExprNode where;
    @Nullable
    private final ReturningExpr returning;
    @NotNull
    private final MetaContainer metas;

    @Override
    @NotNull
    public List<AstNode> getChildren() {
        return this.children;
    }

    @NotNull
    public final DmlOpList getDmlOperations() {
        return this.dmlOperations;
    }

    @Nullable
    public final FromSource getFrom() {
        return this.from;
    }

    @Nullable
    public final ExprNode getWhere() {
        return this.where;
    }

    @Nullable
    public final ReturningExpr getReturning() {
        return this.returning;
    }

    @Override
    @NotNull
    public MetaContainer getMetas() {
        return this.metas;
    }

    public DataManipulation(@NotNull DmlOpList dmlOperations, @Nullable FromSource from2, @Nullable ExprNode where2, @Nullable ReturningExpr returning2, @NotNull MetaContainer metas) {
        Intrinsics.checkParameterIsNotNull(dmlOperations, "dmlOperations");
        Intrinsics.checkParameterIsNotNull(metas, "metas");
        super(null);
        this.dmlOperations = dmlOperations;
        this.from = from2;
        this.where = where2;
        this.returning = returning2;
        this.metas = metas;
        this.children = CollectionsKt.plus((Collection)this.dmlOperations.getChildren(), (Iterable)CollectionsKt.listOfNotNull(this.from, this.where, this.returning, this.dmlOperations));
    }

    public /* synthetic */ DataManipulation(DmlOpList dmlOpList, FromSource fromSource, ExprNode exprNode, ReturningExpr returningExpr, MetaContainer metaContainer, int n, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n & 2) != 0) {
            fromSource = null;
        }
        if ((n & 4) != 0) {
            exprNode = null;
        }
        if ((n & 8) != 0) {
            returningExpr = null;
        }
        this(dmlOpList, fromSource, exprNode, returningExpr, metaContainer);
    }

    @NotNull
    public final DmlOpList component1() {
        return this.dmlOperations;
    }

    @Nullable
    public final FromSource component2() {
        return this.from;
    }

    @Nullable
    public final ExprNode component3() {
        return this.where;
    }

    @Nullable
    public final ReturningExpr component4() {
        return this.returning;
    }

    @NotNull
    public final MetaContainer component5() {
        return this.getMetas();
    }

    @NotNull
    public final DataManipulation copy(@NotNull DmlOpList dmlOperations, @Nullable FromSource from2, @Nullable ExprNode where2, @Nullable ReturningExpr returning2, @NotNull MetaContainer metas) {
        Intrinsics.checkParameterIsNotNull(dmlOperations, "dmlOperations");
        Intrinsics.checkParameterIsNotNull(metas, "metas");
        return new DataManipulation(dmlOperations, from2, where2, returning2, metas);
    }

    public static /* synthetic */ DataManipulation copy$default(DataManipulation dataManipulation, DmlOpList dmlOpList, FromSource fromSource, ExprNode exprNode, ReturningExpr returningExpr, MetaContainer metaContainer, int n, Object object) {
        if ((n & 1) != 0) {
            dmlOpList = dataManipulation.dmlOperations;
        }
        if ((n & 2) != 0) {
            fromSource = dataManipulation.from;
        }
        if ((n & 4) != 0) {
            exprNode = dataManipulation.where;
        }
        if ((n & 8) != 0) {
            returningExpr = dataManipulation.returning;
        }
        if ((n & 0x10) != 0) {
            metaContainer = dataManipulation.getMetas();
        }
        return dataManipulation.copy(dmlOpList, fromSource, exprNode, returningExpr, metaContainer);
    }

    @NotNull
    public String toString() {
        return "DataManipulation(dmlOperations=" + this.dmlOperations + ", from=" + this.from + ", where=" + this.where + ", returning=" + this.returning + ", metas=" + this.getMetas() + ")";
    }

    public int hashCode() {
        DmlOpList dmlOpList = this.dmlOperations;
        FromSource fromSource = this.from;
        ExprNode exprNode = this.where;
        ReturningExpr returningExpr = this.returning;
        MetaContainer metaContainer = this.getMetas();
        return ((((dmlOpList != null ? ((Object)dmlOpList).hashCode() : 0) * 31 + (fromSource != null ? fromSource.hashCode() : 0)) * 31 + (exprNode != null ? exprNode.hashCode() : 0)) * 31 + (returningExpr != null ? ((Object)returningExpr).hashCode() : 0)) * 31 + (metaContainer != null ? metaContainer.hashCode() : 0);
    }

    public boolean equals(@Nullable Object object) {
        block3: {
            block2: {
                if (this == object) break block2;
                if (!(object instanceof DataManipulation)) break block3;
                DataManipulation dataManipulation = (DataManipulation)object;
                if (!Intrinsics.areEqual(this.dmlOperations, dataManipulation.dmlOperations) || !Intrinsics.areEqual(this.from, dataManipulation.from) || !Intrinsics.areEqual(this.where, dataManipulation.where) || !Intrinsics.areEqual(this.returning, dataManipulation.returning) || !Intrinsics.areEqual(this.getMetas(), dataManipulation.getMetas())) break block3;
            }
            return true;
        }
        return false;
    }
}

