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
import org.partiql.lang.ast.DataType;
import org.partiql.lang.ast.ExprNode;
import org.partiql.lang.ast.MetaContainer;
import org.partiql.lang.ast.TypedOp;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000D\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0010\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B%\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0001\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b\u00a2\u0006\u0002\u0010\tJ\t\u0010\u0017\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0018\u001a\u00020\u0001H\u00c6\u0003J\t\u0010\u0019\u001a\u00020\u0006H\u00c6\u0003J\t\u0010\u001a\u001a\u00020\bH\u00c6\u0003J1\u0010\u001b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00012\b\b\u0002\u0010\u0005\u001a\u00020\u00062\b\b\u0002\u0010\u0007\u001a\u00020\bH\u00c6\u0001J\u0013\u0010\u001c\u001a\u00020\u001d2\b\u0010\u001e\u001a\u0004\u0018\u00010\u001fH\u00d6\u0003J\t\u0010 \u001a\u00020!H\u00d6\u0001J\t\u0010\"\u001a\u00020#H\u00d6\u0001R\u001a\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000bX\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0011\u0010\u0004\u001a\u00020\u0001\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0014\u0010\u0007\u001a\u00020\bX\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u0011\u0010\u0005\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016\u00a8\u0006$"}, d2={"Lorg/partiql/lang/ast/Typed;", "Lorg/partiql/lang/ast/ExprNode;", "op", "Lorg/partiql/lang/ast/TypedOp;", "expr", "type", "Lorg/partiql/lang/ast/DataType;", "metas", "Lorg/partiql/lang/ast/MetaContainer;", "(Lorg/partiql/lang/ast/TypedOp;Lorg/partiql/lang/ast/ExprNode;Lorg/partiql/lang/ast/DataType;Lorg/partiql/lang/ast/MetaContainer;)V", "children", "", "Lorg/partiql/lang/ast/AstNode;", "getChildren", "()Ljava/util/List;", "getExpr", "()Lorg/partiql/lang/ast/ExprNode;", "getMetas", "()Lorg/partiql/lang/ast/MetaContainer;", "getOp", "()Lorg/partiql/lang/ast/TypedOp;", "getType", "()Lorg/partiql/lang/ast/DataType;", "component1", "component2", "component3", "component4", "copy", "equals", "", "other", "", "hashCode", "", "toString", "", "lang"})
public final class Typed
extends ExprNode {
    @NotNull
    private final List<AstNode> children;
    @NotNull
    private final TypedOp op;
    @NotNull
    private final ExprNode expr;
    @NotNull
    private final DataType type;
    @NotNull
    private final MetaContainer metas;

    @Override
    @NotNull
    public List<AstNode> getChildren() {
        return this.children;
    }

    @NotNull
    public final TypedOp getOp() {
        return this.op;
    }

    @NotNull
    public final ExprNode getExpr() {
        return this.expr;
    }

    @NotNull
    public final DataType getType() {
        return this.type;
    }

    @Override
    @NotNull
    public MetaContainer getMetas() {
        return this.metas;
    }

    public Typed(@NotNull TypedOp op, @NotNull ExprNode expr, @NotNull DataType type, @NotNull MetaContainer metas) {
        Intrinsics.checkParameterIsNotNull((Object)op, "op");
        Intrinsics.checkParameterIsNotNull(expr, "expr");
        Intrinsics.checkParameterIsNotNull(type, "type");
        Intrinsics.checkParameterIsNotNull(metas, "metas");
        super(null);
        this.op = op;
        this.expr = expr;
        this.type = type;
        this.metas = metas;
        this.children = CollectionsKt.listOf(this.expr, this.type);
    }

    @NotNull
    public final TypedOp component1() {
        return this.op;
    }

    @NotNull
    public final ExprNode component2() {
        return this.expr;
    }

    @NotNull
    public final DataType component3() {
        return this.type;
    }

    @NotNull
    public final MetaContainer component4() {
        return this.getMetas();
    }

    @NotNull
    public final Typed copy(@NotNull TypedOp op, @NotNull ExprNode expr, @NotNull DataType type, @NotNull MetaContainer metas) {
        Intrinsics.checkParameterIsNotNull((Object)op, "op");
        Intrinsics.checkParameterIsNotNull(expr, "expr");
        Intrinsics.checkParameterIsNotNull(type, "type");
        Intrinsics.checkParameterIsNotNull(metas, "metas");
        return new Typed(op, expr, type, metas);
    }

    public static /* synthetic */ Typed copy$default(Typed typed, TypedOp typedOp, ExprNode exprNode, DataType dataType, MetaContainer metaContainer, int n, Object object) {
        if ((n & 1) != 0) {
            typedOp = typed.op;
        }
        if ((n & 2) != 0) {
            exprNode = typed.expr;
        }
        if ((n & 4) != 0) {
            dataType = typed.type;
        }
        if ((n & 8) != 0) {
            metaContainer = typed.getMetas();
        }
        return typed.copy(typedOp, exprNode, dataType, metaContainer);
    }

    @NotNull
    public String toString() {
        return "Typed(op=" + (Object)((Object)this.op) + ", expr=" + this.expr + ", type=" + this.type + ", metas=" + this.getMetas() + ")";
    }

    public int hashCode() {
        TypedOp typedOp = this.op;
        ExprNode exprNode = this.expr;
        DataType dataType = this.type;
        MetaContainer metaContainer = this.getMetas();
        return (((typedOp != null ? ((Object)((Object)typedOp)).hashCode() : 0) * 31 + (exprNode != null ? exprNode.hashCode() : 0)) * 31 + (dataType != null ? ((Object)dataType).hashCode() : 0)) * 31 + (metaContainer != null ? metaContainer.hashCode() : 0);
    }

    public boolean equals(@Nullable Object object) {
        block3: {
            block2: {
                if (this == object) break block2;
                if (!(object instanceof Typed)) break block3;
                Typed typed = (Typed)object;
                if (!Intrinsics.areEqual((Object)this.op, (Object)typed.op) || !Intrinsics.areEqual(this.expr, typed.expr) || !Intrinsics.areEqual(this.type, typed.type) || !Intrinsics.areEqual(this.getMetas(), typed.getMetas())) break block3;
            }
            return true;
        }
        return false;
    }
}

