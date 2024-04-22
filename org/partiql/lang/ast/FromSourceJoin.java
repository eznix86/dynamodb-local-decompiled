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
import org.partiql.lang.ast.FromSource;
import org.partiql.lang.ast.HasMetas;
import org.partiql.lang.ast.JoinOp;
import org.partiql.lang.ast.MetaContainer;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000H\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0012\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u00012\u00020\u0002B-\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0001\u0012\u0006\u0010\u0006\u001a\u00020\u0001\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0006\u0010\t\u001a\u00020\n\u00a2\u0006\u0002\u0010\u000bJ\t\u0010\u001a\u001a\u00020\u0004H\u00c6\u0003J\t\u0010\u001b\u001a\u00020\u0001H\u00c6\u0003J\t\u0010\u001c\u001a\u00020\u0001H\u00c6\u0003J\t\u0010\u001d\u001a\u00020\bH\u00c6\u0003J\t\u0010\u001e\u001a\u00020\nH\u00c6\u0003J;\u0010\u001f\u001a\u00020\u00002\b\b\u0002\u0010\u0003\u001a\u00020\u00042\b\b\u0002\u0010\u0005\u001a\u00020\u00012\b\b\u0002\u0010\u0006\u001a\u00020\u00012\b\b\u0002\u0010\u0007\u001a\u00020\b2\b\b\u0002\u0010\t\u001a\u00020\nH\u00c6\u0001J\u0013\u0010 \u001a\u00020!2\b\u0010\"\u001a\u0004\u0018\u00010#H\u00d6\u0003J\t\u0010$\u001a\u00020%H\u00d6\u0001J\t\u0010&\u001a\u00020'H\u00d6\u0001R\u001a\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000e0\rX\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0011\u0010\u0007\u001a\u00020\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u0011\u0010\u0003\u001a\u00020\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u0011\u0010\u0005\u001a\u00020\u0001\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u0014\u0010\t\u001a\u00020\nX\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018R\u0011\u0010\u0006\u001a\u00020\u0001\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u0016\u00a8\u0006("}, d2={"Lorg/partiql/lang/ast/FromSourceJoin;", "Lorg/partiql/lang/ast/FromSource;", "Lorg/partiql/lang/ast/HasMetas;", "joinOp", "Lorg/partiql/lang/ast/JoinOp;", "leftRef", "rightRef", "condition", "Lorg/partiql/lang/ast/ExprNode;", "metas", "Lorg/partiql/lang/ast/MetaContainer;", "(Lorg/partiql/lang/ast/JoinOp;Lorg/partiql/lang/ast/FromSource;Lorg/partiql/lang/ast/FromSource;Lorg/partiql/lang/ast/ExprNode;Lorg/partiql/lang/ast/MetaContainer;)V", "children", "", "Lorg/partiql/lang/ast/AstNode;", "getChildren", "()Ljava/util/List;", "getCondition", "()Lorg/partiql/lang/ast/ExprNode;", "getJoinOp", "()Lorg/partiql/lang/ast/JoinOp;", "getLeftRef", "()Lorg/partiql/lang/ast/FromSource;", "getMetas", "()Lorg/partiql/lang/ast/MetaContainer;", "getRightRef", "component1", "component2", "component3", "component4", "component5", "copy", "equals", "", "other", "", "hashCode", "", "toString", "", "lang"})
public final class FromSourceJoin
extends FromSource
implements HasMetas {
    @NotNull
    private final List<AstNode> children;
    @NotNull
    private final JoinOp joinOp;
    @NotNull
    private final FromSource leftRef;
    @NotNull
    private final FromSource rightRef;
    @NotNull
    private final ExprNode condition;
    @NotNull
    private final MetaContainer metas;

    @Override
    @NotNull
    public List<AstNode> getChildren() {
        return this.children;
    }

    @NotNull
    public final JoinOp getJoinOp() {
        return this.joinOp;
    }

    @NotNull
    public final FromSource getLeftRef() {
        return this.leftRef;
    }

    @NotNull
    public final FromSource getRightRef() {
        return this.rightRef;
    }

    @NotNull
    public final ExprNode getCondition() {
        return this.condition;
    }

    @Override
    @NotNull
    public MetaContainer getMetas() {
        return this.metas;
    }

    public FromSourceJoin(@NotNull JoinOp joinOp, @NotNull FromSource leftRef, @NotNull FromSource rightRef, @NotNull ExprNode condition, @NotNull MetaContainer metas) {
        Intrinsics.checkParameterIsNotNull((Object)joinOp, "joinOp");
        Intrinsics.checkParameterIsNotNull(leftRef, "leftRef");
        Intrinsics.checkParameterIsNotNull(rightRef, "rightRef");
        Intrinsics.checkParameterIsNotNull(condition, "condition");
        Intrinsics.checkParameterIsNotNull(metas, "metas");
        super(null);
        this.joinOp = joinOp;
        this.leftRef = leftRef;
        this.rightRef = rightRef;
        this.condition = condition;
        this.metas = metas;
        this.children = CollectionsKt.listOf(this.leftRef, this.rightRef, this.condition);
    }

    @NotNull
    public final JoinOp component1() {
        return this.joinOp;
    }

    @NotNull
    public final FromSource component2() {
        return this.leftRef;
    }

    @NotNull
    public final FromSource component3() {
        return this.rightRef;
    }

    @NotNull
    public final ExprNode component4() {
        return this.condition;
    }

    @NotNull
    public final MetaContainer component5() {
        return this.getMetas();
    }

    @NotNull
    public final FromSourceJoin copy(@NotNull JoinOp joinOp, @NotNull FromSource leftRef, @NotNull FromSource rightRef, @NotNull ExprNode condition, @NotNull MetaContainer metas) {
        Intrinsics.checkParameterIsNotNull((Object)joinOp, "joinOp");
        Intrinsics.checkParameterIsNotNull(leftRef, "leftRef");
        Intrinsics.checkParameterIsNotNull(rightRef, "rightRef");
        Intrinsics.checkParameterIsNotNull(condition, "condition");
        Intrinsics.checkParameterIsNotNull(metas, "metas");
        return new FromSourceJoin(joinOp, leftRef, rightRef, condition, metas);
    }

    public static /* synthetic */ FromSourceJoin copy$default(FromSourceJoin fromSourceJoin, JoinOp joinOp, FromSource fromSource, FromSource fromSource2, ExprNode exprNode, MetaContainer metaContainer, int n, Object object) {
        if ((n & 1) != 0) {
            joinOp = fromSourceJoin.joinOp;
        }
        if ((n & 2) != 0) {
            fromSource = fromSourceJoin.leftRef;
        }
        if ((n & 4) != 0) {
            fromSource2 = fromSourceJoin.rightRef;
        }
        if ((n & 8) != 0) {
            exprNode = fromSourceJoin.condition;
        }
        if ((n & 0x10) != 0) {
            metaContainer = fromSourceJoin.getMetas();
        }
        return fromSourceJoin.copy(joinOp, fromSource, fromSource2, exprNode, metaContainer);
    }

    @NotNull
    public String toString() {
        return "FromSourceJoin(joinOp=" + (Object)((Object)this.joinOp) + ", leftRef=" + this.leftRef + ", rightRef=" + this.rightRef + ", condition=" + this.condition + ", metas=" + this.getMetas() + ")";
    }

    public int hashCode() {
        JoinOp joinOp = this.joinOp;
        FromSource fromSource = this.leftRef;
        FromSource fromSource2 = this.rightRef;
        ExprNode exprNode = this.condition;
        MetaContainer metaContainer = this.getMetas();
        return ((((joinOp != null ? ((Object)((Object)joinOp)).hashCode() : 0) * 31 + (fromSource != null ? fromSource.hashCode() : 0)) * 31 + (fromSource2 != null ? fromSource2.hashCode() : 0)) * 31 + (exprNode != null ? exprNode.hashCode() : 0)) * 31 + (metaContainer != null ? metaContainer.hashCode() : 0);
    }

    public boolean equals(@Nullable Object object) {
        block3: {
            block2: {
                if (this == object) break block2;
                if (!(object instanceof FromSourceJoin)) break block3;
                FromSourceJoin fromSourceJoin = (FromSourceJoin)object;
                if (!Intrinsics.areEqual((Object)this.joinOp, (Object)fromSourceJoin.joinOp) || !Intrinsics.areEqual(this.leftRef, fromSourceJoin.leftRef) || !Intrinsics.areEqual(this.rightRef, fromSourceJoin.rightRef) || !Intrinsics.areEqual(this.condition, fromSourceJoin.condition) || !Intrinsics.areEqual(this.getMetas(), fromSourceJoin.getMetas())) break block3;
            }
            return true;
        }
        return false;
    }
}

