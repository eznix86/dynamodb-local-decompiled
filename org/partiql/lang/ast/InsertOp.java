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
import org.partiql.lang.ast.DataManipulationOperation;
import org.partiql.lang.ast.ExprNode;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0005J\t\u0010\u000e\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u000f\u001a\u00020\u0003H\u00c6\u0003J\u001d\u0010\u0010\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0003H\u00c6\u0001J\u0013\u0010\u0011\u001a\u00020\u00122\b\u0010\u0013\u001a\u0004\u0018\u00010\u0014H\u00d6\u0003J\t\u0010\u0015\u001a\u00020\u0016H\u00d6\u0001J\t\u0010\u0017\u001a\u00020\u0018H\u00d6\u0001R\u001a\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007X\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\f\u00a8\u0006\u0019"}, d2={"Lorg/partiql/lang/ast/InsertOp;", "Lorg/partiql/lang/ast/DataManipulationOperation;", "lvalue", "Lorg/partiql/lang/ast/ExprNode;", "values", "(Lorg/partiql/lang/ast/ExprNode;Lorg/partiql/lang/ast/ExprNode;)V", "children", "", "Lorg/partiql/lang/ast/AstNode;", "getChildren", "()Ljava/util/List;", "getLvalue", "()Lorg/partiql/lang/ast/ExprNode;", "getValues", "component1", "component2", "copy", "equals", "", "other", "", "hashCode", "", "toString", "", "lang"})
public final class InsertOp
extends DataManipulationOperation {
    @NotNull
    private final List<AstNode> children;
    @NotNull
    private final ExprNode lvalue;
    @NotNull
    private final ExprNode values;

    @Override
    @NotNull
    public List<AstNode> getChildren() {
        return this.children;
    }

    @NotNull
    public final ExprNode getLvalue() {
        return this.lvalue;
    }

    @NotNull
    public final ExprNode getValues() {
        return this.values;
    }

    public InsertOp(@NotNull ExprNode lvalue, @NotNull ExprNode values2) {
        Intrinsics.checkParameterIsNotNull(lvalue, "lvalue");
        Intrinsics.checkParameterIsNotNull(values2, "values");
        super("insert", null);
        this.lvalue = lvalue;
        this.values = values2;
        this.children = CollectionsKt.listOf(this.lvalue, this.values);
    }

    @NotNull
    public final ExprNode component1() {
        return this.lvalue;
    }

    @NotNull
    public final ExprNode component2() {
        return this.values;
    }

    @NotNull
    public final InsertOp copy(@NotNull ExprNode lvalue, @NotNull ExprNode values2) {
        Intrinsics.checkParameterIsNotNull(lvalue, "lvalue");
        Intrinsics.checkParameterIsNotNull(values2, "values");
        return new InsertOp(lvalue, values2);
    }

    public static /* synthetic */ InsertOp copy$default(InsertOp insertOp, ExprNode exprNode, ExprNode exprNode2, int n, Object object) {
        if ((n & 1) != 0) {
            exprNode = insertOp.lvalue;
        }
        if ((n & 2) != 0) {
            exprNode2 = insertOp.values;
        }
        return insertOp.copy(exprNode, exprNode2);
    }

    @NotNull
    public String toString() {
        return "InsertOp(lvalue=" + this.lvalue + ", values=" + this.values + ")";
    }

    public int hashCode() {
        ExprNode exprNode = this.lvalue;
        ExprNode exprNode2 = this.values;
        return (exprNode != null ? exprNode.hashCode() : 0) * 31 + (exprNode2 != null ? exprNode2.hashCode() : 0);
    }

    public boolean equals(@Nullable Object object) {
        block3: {
            block2: {
                if (this == object) break block2;
                if (!(object instanceof InsertOp)) break block3;
                InsertOp insertOp = (InsertOp)object;
                if (!Intrinsics.areEqual(this.lvalue, insertOp.lvalue) || !Intrinsics.areEqual(this.values, insertOp.values)) break block3;
            }
            return true;
        }
        return false;
    }
}

