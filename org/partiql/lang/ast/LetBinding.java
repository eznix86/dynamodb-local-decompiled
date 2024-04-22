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
import org.partiql.lang.ast.SymbolicName;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\n\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\t\u0010\u000f\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0010\u001a\u00020\u0005H\u00c6\u0003J\u001d\u0010\u0011\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0005H\u00c6\u0001J\u0013\u0010\u0012\u001a\u00020\u00132\b\u0010\u0014\u001a\u0004\u0018\u00010\u0015H\u00d6\u0003J\t\u0010\u0016\u001a\u00020\u0017H\u00d6\u0001J\t\u0010\u0018\u001a\u00020\u0019H\u00d6\u0001R\u001a\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00010\bX\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000e\u00a8\u0006\u001a"}, d2={"Lorg/partiql/lang/ast/LetBinding;", "Lorg/partiql/lang/ast/AstNode;", "expr", "Lorg/partiql/lang/ast/ExprNode;", "name", "Lorg/partiql/lang/ast/SymbolicName;", "(Lorg/partiql/lang/ast/ExprNode;Lorg/partiql/lang/ast/SymbolicName;)V", "children", "", "getChildren", "()Ljava/util/List;", "getExpr", "()Lorg/partiql/lang/ast/ExprNode;", "getName", "()Lorg/partiql/lang/ast/SymbolicName;", "component1", "component2", "copy", "equals", "", "other", "", "hashCode", "", "toString", "", "lang"})
public final class LetBinding
extends AstNode {
    @NotNull
    private final List<AstNode> children;
    @NotNull
    private final ExprNode expr;
    @NotNull
    private final SymbolicName name;

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
    public final SymbolicName getName() {
        return this.name;
    }

    public LetBinding(@NotNull ExprNode expr, @NotNull SymbolicName name) {
        Intrinsics.checkParameterIsNotNull(expr, "expr");
        Intrinsics.checkParameterIsNotNull(name, "name");
        super(null);
        this.expr = expr;
        this.name = name;
        this.children = CollectionsKt.listOf(this.expr);
    }

    @NotNull
    public final ExprNode component1() {
        return this.expr;
    }

    @NotNull
    public final SymbolicName component2() {
        return this.name;
    }

    @NotNull
    public final LetBinding copy(@NotNull ExprNode expr, @NotNull SymbolicName name) {
        Intrinsics.checkParameterIsNotNull(expr, "expr");
        Intrinsics.checkParameterIsNotNull(name, "name");
        return new LetBinding(expr, name);
    }

    public static /* synthetic */ LetBinding copy$default(LetBinding letBinding, ExprNode exprNode, SymbolicName symbolicName, int n, Object object) {
        if ((n & 1) != 0) {
            exprNode = letBinding.expr;
        }
        if ((n & 2) != 0) {
            symbolicName = letBinding.name;
        }
        return letBinding.copy(exprNode, symbolicName);
    }

    @NotNull
    public String toString() {
        return "LetBinding(expr=" + this.expr + ", name=" + this.name + ")";
    }

    public int hashCode() {
        ExprNode exprNode = this.expr;
        SymbolicName symbolicName = this.name;
        return (exprNode != null ? exprNode.hashCode() : 0) * 31 + (symbolicName != null ? ((Object)symbolicName).hashCode() : 0);
    }

    public boolean equals(@Nullable Object object) {
        block3: {
            block2: {
                if (this == object) break block2;
                if (!(object instanceof LetBinding)) break block3;
                LetBinding letBinding = (LetBinding)object;
                if (!Intrinsics.areEqual(this.expr, letBinding.expr) || !Intrinsics.areEqual(this.name, letBinding.name)) break block3;
            }
            return true;
        }
        return false;
    }
}

