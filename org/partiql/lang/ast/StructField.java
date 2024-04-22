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

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\b\t\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0005J\t\u0010\r\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u000e\u001a\u00020\u0003H\u00c6\u0003J\u001d\u0010\u000f\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0003H\u00c6\u0001J\u0013\u0010\u0010\u001a\u00020\u00112\b\u0010\u0012\u001a\u0004\u0018\u00010\u0013H\u00d6\u0003J\t\u0010\u0014\u001a\u00020\u0015H\u00d6\u0001J\t\u0010\u0016\u001a\u00020\u0017H\u00d6\u0001R\u001a\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00010\u0007X\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\u000b\u00a8\u0006\u0018"}, d2={"Lorg/partiql/lang/ast/StructField;", "Lorg/partiql/lang/ast/AstNode;", "name", "Lorg/partiql/lang/ast/ExprNode;", "expr", "(Lorg/partiql/lang/ast/ExprNode;Lorg/partiql/lang/ast/ExprNode;)V", "children", "", "getChildren", "()Ljava/util/List;", "getExpr", "()Lorg/partiql/lang/ast/ExprNode;", "getName", "component1", "component2", "copy", "equals", "", "other", "", "hashCode", "", "toString", "", "lang"})
public final class StructField
extends AstNode {
    @NotNull
    private final List<AstNode> children;
    @NotNull
    private final ExprNode name;
    @NotNull
    private final ExprNode expr;

    @Override
    @NotNull
    public List<AstNode> getChildren() {
        return this.children;
    }

    @NotNull
    public final ExprNode getName() {
        return this.name;
    }

    @NotNull
    public final ExprNode getExpr() {
        return this.expr;
    }

    public StructField(@NotNull ExprNode name, @NotNull ExprNode expr) {
        Intrinsics.checkParameterIsNotNull(name, "name");
        Intrinsics.checkParameterIsNotNull(expr, "expr");
        super(null);
        this.name = name;
        this.expr = expr;
        this.children = CollectionsKt.listOf(this.name, this.expr);
    }

    @NotNull
    public final ExprNode component1() {
        return this.name;
    }

    @NotNull
    public final ExprNode component2() {
        return this.expr;
    }

    @NotNull
    public final StructField copy(@NotNull ExprNode name, @NotNull ExprNode expr) {
        Intrinsics.checkParameterIsNotNull(name, "name");
        Intrinsics.checkParameterIsNotNull(expr, "expr");
        return new StructField(name, expr);
    }

    public static /* synthetic */ StructField copy$default(StructField structField, ExprNode exprNode, ExprNode exprNode2, int n, Object object) {
        if ((n & 1) != 0) {
            exprNode = structField.name;
        }
        if ((n & 2) != 0) {
            exprNode2 = structField.expr;
        }
        return structField.copy(exprNode, exprNode2);
    }

    @NotNull
    public String toString() {
        return "StructField(name=" + this.name + ", expr=" + this.expr + ")";
    }

    public int hashCode() {
        ExprNode exprNode = this.name;
        ExprNode exprNode2 = this.expr;
        return (exprNode != null ? exprNode.hashCode() : 0) * 31 + (exprNode2 != null ? exprNode2.hashCode() : 0);
    }

    public boolean equals(@Nullable Object object) {
        block3: {
            block2: {
                if (this == object) break block2;
                if (!(object instanceof StructField)) break block3;
                StructField structField = (StructField)object;
                if (!Intrinsics.areEqual(this.name, structField.name) || !Intrinsics.areEqual(this.expr, structField.expr)) break block3;
            }
            return true;
        }
        return false;
    }
}

