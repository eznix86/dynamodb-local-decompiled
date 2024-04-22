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
import org.partiql.lang.ast.MetaContainer;
import org.partiql.lang.ast.SetQuantifier;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B%\u0012\u0006\u0010\u0002\u001a\u00020\u0001\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0001\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\bJ\t\u0010\u0015\u001a\u00020\u0001H\u00c6\u0003J\t\u0010\u0016\u001a\u00020\u0004H\u00c6\u0003J\t\u0010\u0017\u001a\u00020\u0001H\u00c6\u0003J\t\u0010\u0018\u001a\u00020\u0007H\u00c6\u0003J1\u0010\u0019\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00012\b\b\u0002\u0010\u0003\u001a\u00020\u00042\b\b\u0002\u0010\u0005\u001a\u00020\u00012\b\b\u0002\u0010\u0006\u001a\u00020\u0007H\u00c6\u0001J\u0013\u0010\u001a\u001a\u00020\u001b2\b\u0010\u001c\u001a\u0004\u0018\u00010\u001dH\u00d6\u0003J\t\u0010\u001e\u001a\u00020\u001fH\u00d6\u0001J\t\u0010 \u001a\u00020!H\u00d6\u0001R\u0011\u0010\u0005\u001a\u00020\u0001\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u001a\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\fX\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0011\u0010\u0002\u001a\u00020\u0001\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\nR\u0014\u0010\u0006\u001a\u00020\u0007X\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u0011\u0010\u0003\u001a\u00020\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014\u00a8\u0006\""}, d2={"Lorg/partiql/lang/ast/CallAgg;", "Lorg/partiql/lang/ast/ExprNode;", "funcExpr", "setQuantifier", "Lorg/partiql/lang/ast/SetQuantifier;", "arg", "metas", "Lorg/partiql/lang/ast/MetaContainer;", "(Lorg/partiql/lang/ast/ExprNode;Lorg/partiql/lang/ast/SetQuantifier;Lorg/partiql/lang/ast/ExprNode;Lorg/partiql/lang/ast/MetaContainer;)V", "getArg", "()Lorg/partiql/lang/ast/ExprNode;", "children", "", "Lorg/partiql/lang/ast/AstNode;", "getChildren", "()Ljava/util/List;", "getFuncExpr", "getMetas", "()Lorg/partiql/lang/ast/MetaContainer;", "getSetQuantifier", "()Lorg/partiql/lang/ast/SetQuantifier;", "component1", "component2", "component3", "component4", "copy", "equals", "", "other", "", "hashCode", "", "toString", "", "lang"})
public final class CallAgg
extends ExprNode {
    @NotNull
    private final List<AstNode> children;
    @NotNull
    private final ExprNode funcExpr;
    @NotNull
    private final SetQuantifier setQuantifier;
    @NotNull
    private final ExprNode arg;
    @NotNull
    private final MetaContainer metas;

    @Override
    @NotNull
    public List<AstNode> getChildren() {
        return this.children;
    }

    @NotNull
    public final ExprNode getFuncExpr() {
        return this.funcExpr;
    }

    @NotNull
    public final SetQuantifier getSetQuantifier() {
        return this.setQuantifier;
    }

    @NotNull
    public final ExprNode getArg() {
        return this.arg;
    }

    @Override
    @NotNull
    public MetaContainer getMetas() {
        return this.metas;
    }

    public CallAgg(@NotNull ExprNode funcExpr, @NotNull SetQuantifier setQuantifier, @NotNull ExprNode arg, @NotNull MetaContainer metas) {
        Intrinsics.checkParameterIsNotNull(funcExpr, "funcExpr");
        Intrinsics.checkParameterIsNotNull((Object)setQuantifier, "setQuantifier");
        Intrinsics.checkParameterIsNotNull(arg, "arg");
        Intrinsics.checkParameterIsNotNull(metas, "metas");
        super(null);
        this.funcExpr = funcExpr;
        this.setQuantifier = setQuantifier;
        this.arg = arg;
        this.metas = metas;
        this.children = CollectionsKt.listOf(this.funcExpr, this.arg);
    }

    @NotNull
    public final ExprNode component1() {
        return this.funcExpr;
    }

    @NotNull
    public final SetQuantifier component2() {
        return this.setQuantifier;
    }

    @NotNull
    public final ExprNode component3() {
        return this.arg;
    }

    @NotNull
    public final MetaContainer component4() {
        return this.getMetas();
    }

    @NotNull
    public final CallAgg copy(@NotNull ExprNode funcExpr, @NotNull SetQuantifier setQuantifier, @NotNull ExprNode arg, @NotNull MetaContainer metas) {
        Intrinsics.checkParameterIsNotNull(funcExpr, "funcExpr");
        Intrinsics.checkParameterIsNotNull((Object)setQuantifier, "setQuantifier");
        Intrinsics.checkParameterIsNotNull(arg, "arg");
        Intrinsics.checkParameterIsNotNull(metas, "metas");
        return new CallAgg(funcExpr, setQuantifier, arg, metas);
    }

    public static /* synthetic */ CallAgg copy$default(CallAgg callAgg, ExprNode exprNode, SetQuantifier setQuantifier, ExprNode exprNode2, MetaContainer metaContainer, int n, Object object) {
        if ((n & 1) != 0) {
            exprNode = callAgg.funcExpr;
        }
        if ((n & 2) != 0) {
            setQuantifier = callAgg.setQuantifier;
        }
        if ((n & 4) != 0) {
            exprNode2 = callAgg.arg;
        }
        if ((n & 8) != 0) {
            metaContainer = callAgg.getMetas();
        }
        return callAgg.copy(exprNode, setQuantifier, exprNode2, metaContainer);
    }

    @NotNull
    public String toString() {
        return "CallAgg(funcExpr=" + this.funcExpr + ", setQuantifier=" + (Object)((Object)this.setQuantifier) + ", arg=" + this.arg + ", metas=" + this.getMetas() + ")";
    }

    public int hashCode() {
        ExprNode exprNode = this.funcExpr;
        SetQuantifier setQuantifier = this.setQuantifier;
        ExprNode exprNode2 = this.arg;
        MetaContainer metaContainer = this.getMetas();
        return (((exprNode != null ? exprNode.hashCode() : 0) * 31 + (setQuantifier != null ? ((Object)((Object)setQuantifier)).hashCode() : 0)) * 31 + (exprNode2 != null ? exprNode2.hashCode() : 0)) * 31 + (metaContainer != null ? metaContainer.hashCode() : 0);
    }

    public boolean equals(@Nullable Object object) {
        block3: {
            block2: {
                if (this == object) break block2;
                if (!(object instanceof CallAgg)) break block3;
                CallAgg callAgg = (CallAgg)object;
                if (!Intrinsics.areEqual(this.funcExpr, callAgg.funcExpr) || !Intrinsics.areEqual((Object)this.setQuantifier, (Object)callAgg.setQuantifier) || !Intrinsics.areEqual(this.arg, callAgg.arg) || !Intrinsics.areEqual(this.getMetas(), callAgg.getMetas())) break block3;
            }
            return true;
        }
        return false;
    }
}

