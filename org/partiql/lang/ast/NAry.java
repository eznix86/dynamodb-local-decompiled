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
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.partiql.lang.ast.AstNode;
import org.partiql.lang.ast.ExprNode;
import org.partiql.lang.ast.MetaContainer;
import org.partiql.lang.ast.NAryOp;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B#\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00010\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\bJ\t\u0010\u0012\u001a\u00020\u0003H\u00c6\u0003J\u000f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00010\u0005H\u00c6\u0003J\t\u0010\u0014\u001a\u00020\u0007H\u00c6\u0003J-\u0010\u0015\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\u000e\b\u0002\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u0007H\u00c6\u0001J\u0013\u0010\u0016\u001a\u00020\u00172\b\u0010\u0018\u001a\u0004\u0018\u00010\u0019H\u00d6\u0003J\t\u0010\u001a\u001a\u00020\u001bH\u00d6\u0001J\t\u0010\u001c\u001a\u00020\u001dH\u00d6\u0001R\u0017\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00010\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u001a\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\f0\u0005X\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\nR\u0014\u0010\u0006\u001a\u00020\u0007X\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011\u00a8\u0006\u001e"}, d2={"Lorg/partiql/lang/ast/NAry;", "Lorg/partiql/lang/ast/ExprNode;", "op", "Lorg/partiql/lang/ast/NAryOp;", "args", "", "metas", "Lorg/partiql/lang/ast/MetaContainer;", "(Lorg/partiql/lang/ast/NAryOp;Ljava/util/List;Lorg/partiql/lang/ast/MetaContainer;)V", "getArgs", "()Ljava/util/List;", "children", "Lorg/partiql/lang/ast/AstNode;", "getChildren", "getMetas", "()Lorg/partiql/lang/ast/MetaContainer;", "getOp", "()Lorg/partiql/lang/ast/NAryOp;", "component1", "component2", "component3", "copy", "equals", "", "other", "", "hashCode", "", "toString", "", "lang"})
public final class NAry
extends ExprNode {
    @NotNull
    private final List<AstNode> children;
    @NotNull
    private final NAryOp op;
    @NotNull
    private final List<ExprNode> args;
    @NotNull
    private final MetaContainer metas;

    @Override
    @NotNull
    public List<AstNode> getChildren() {
        return this.children;
    }

    @NotNull
    public final NAryOp getOp() {
        return this.op;
    }

    @NotNull
    public final List<ExprNode> getArgs() {
        return this.args;
    }

    @Override
    @NotNull
    public MetaContainer getMetas() {
        return this.metas;
    }

    public NAry(@NotNull NAryOp op, @NotNull List<? extends ExprNode> args2, @NotNull MetaContainer metas) {
        Intrinsics.checkParameterIsNotNull((Object)op, "op");
        Intrinsics.checkParameterIsNotNull(args2, "args");
        Intrinsics.checkParameterIsNotNull(metas, "metas");
        super(null);
        this.op = op;
        this.args = args2;
        this.metas = metas;
        this.children = this.args;
    }

    @NotNull
    public final NAryOp component1() {
        return this.op;
    }

    @NotNull
    public final List<ExprNode> component2() {
        return this.args;
    }

    @NotNull
    public final MetaContainer component3() {
        return this.getMetas();
    }

    @NotNull
    public final NAry copy(@NotNull NAryOp op, @NotNull List<? extends ExprNode> args2, @NotNull MetaContainer metas) {
        Intrinsics.checkParameterIsNotNull((Object)op, "op");
        Intrinsics.checkParameterIsNotNull(args2, "args");
        Intrinsics.checkParameterIsNotNull(metas, "metas");
        return new NAry(op, args2, metas);
    }

    public static /* synthetic */ NAry copy$default(NAry nAry, NAryOp nAryOp, List list, MetaContainer metaContainer, int n, Object object) {
        if ((n & 1) != 0) {
            nAryOp = nAry.op;
        }
        if ((n & 2) != 0) {
            list = nAry.args;
        }
        if ((n & 4) != 0) {
            metaContainer = nAry.getMetas();
        }
        return nAry.copy(nAryOp, list, metaContainer);
    }

    @NotNull
    public String toString() {
        return "NAry(op=" + (Object)((Object)this.op) + ", args=" + this.args + ", metas=" + this.getMetas() + ")";
    }

    public int hashCode() {
        NAryOp nAryOp = this.op;
        List<ExprNode> list = this.args;
        MetaContainer metaContainer = this.getMetas();
        return ((nAryOp != null ? ((Object)((Object)nAryOp)).hashCode() : 0) * 31 + (list != null ? ((Object)list).hashCode() : 0)) * 31 + (metaContainer != null ? metaContainer.hashCode() : 0);
    }

    public boolean equals(@Nullable Object object) {
        block3: {
            block2: {
                if (this == object) break block2;
                if (!(object instanceof NAry)) break block3;
                NAry nAry = (NAry)object;
                if (!Intrinsics.areEqual((Object)this.op, (Object)nAry.op) || !Intrinsics.areEqual(this.args, nAry.args) || !Intrinsics.areEqual(this.getMetas(), nAry.getMetas())) break block3;
            }
            return true;
        }
        return false;
    }
}

