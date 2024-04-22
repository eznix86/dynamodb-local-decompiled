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
import org.partiql.lang.ast.OnConflict;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u000e\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B)\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007\u00a2\u0006\u0002\u0010\bJ\t\u0010\u0014\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0015\u001a\u00020\u0003H\u00c6\u0003J\u000b\u0010\u0016\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\u000b\u0010\u0017\u001a\u0004\u0018\u00010\u0007H\u00c6\u0003J5\u0010\u0018\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0007H\u00c6\u0001J\u0013\u0010\u0019\u001a\u00020\u001a2\b\u0010\u001b\u001a\u0004\u0018\u00010\u001cH\u00d6\u0003J\t\u0010\u001d\u001a\u00020\u001eH\u00d6\u0001J\t\u0010\u001f\u001a\u00020 H\u00d6\u0001R\u001a\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\nX\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0013\u0010\u0006\u001a\u0004\u0018\u00010\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0013\u0010\u0005\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u000fR\u0011\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u000f\u00a8\u0006!"}, d2={"Lorg/partiql/lang/ast/InsertValueOp;", "Lorg/partiql/lang/ast/DataManipulationOperation;", "lvalue", "Lorg/partiql/lang/ast/ExprNode;", "value", "position", "onConflict", "Lorg/partiql/lang/ast/OnConflict;", "(Lorg/partiql/lang/ast/ExprNode;Lorg/partiql/lang/ast/ExprNode;Lorg/partiql/lang/ast/ExprNode;Lorg/partiql/lang/ast/OnConflict;)V", "children", "", "Lorg/partiql/lang/ast/AstNode;", "getChildren", "()Ljava/util/List;", "getLvalue", "()Lorg/partiql/lang/ast/ExprNode;", "getOnConflict", "()Lorg/partiql/lang/ast/OnConflict;", "getPosition", "getValue", "component1", "component2", "component3", "component4", "copy", "equals", "", "other", "", "hashCode", "", "toString", "", "lang"})
public final class InsertValueOp
extends DataManipulationOperation {
    @NotNull
    private final List<AstNode> children;
    @NotNull
    private final ExprNode lvalue;
    @NotNull
    private final ExprNode value;
    @Nullable
    private final ExprNode position;
    @Nullable
    private final OnConflict onConflict;

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
    public final ExprNode getValue() {
        return this.value;
    }

    @Nullable
    public final ExprNode getPosition() {
        return this.position;
    }

    @Nullable
    public final OnConflict getOnConflict() {
        return this.onConflict;
    }

    public InsertValueOp(@NotNull ExprNode lvalue, @NotNull ExprNode value, @Nullable ExprNode position, @Nullable OnConflict onConflict) {
        Intrinsics.checkParameterIsNotNull(lvalue, "lvalue");
        Intrinsics.checkParameterIsNotNull(value, "value");
        super("insert_value", null);
        this.lvalue = lvalue;
        this.value = value;
        this.position = position;
        this.onConflict = onConflict;
        this.children = CollectionsKt.listOfNotNull(this.lvalue, this.value, this.position, this.onConflict);
    }

    @NotNull
    public final ExprNode component1() {
        return this.lvalue;
    }

    @NotNull
    public final ExprNode component2() {
        return this.value;
    }

    @Nullable
    public final ExprNode component3() {
        return this.position;
    }

    @Nullable
    public final OnConflict component4() {
        return this.onConflict;
    }

    @NotNull
    public final InsertValueOp copy(@NotNull ExprNode lvalue, @NotNull ExprNode value, @Nullable ExprNode position, @Nullable OnConflict onConflict) {
        Intrinsics.checkParameterIsNotNull(lvalue, "lvalue");
        Intrinsics.checkParameterIsNotNull(value, "value");
        return new InsertValueOp(lvalue, value, position, onConflict);
    }

    public static /* synthetic */ InsertValueOp copy$default(InsertValueOp insertValueOp, ExprNode exprNode, ExprNode exprNode2, ExprNode exprNode3, OnConflict onConflict, int n, Object object) {
        if ((n & 1) != 0) {
            exprNode = insertValueOp.lvalue;
        }
        if ((n & 2) != 0) {
            exprNode2 = insertValueOp.value;
        }
        if ((n & 4) != 0) {
            exprNode3 = insertValueOp.position;
        }
        if ((n & 8) != 0) {
            onConflict = insertValueOp.onConflict;
        }
        return insertValueOp.copy(exprNode, exprNode2, exprNode3, onConflict);
    }

    @NotNull
    public String toString() {
        return "InsertValueOp(lvalue=" + this.lvalue + ", value=" + this.value + ", position=" + this.position + ", onConflict=" + this.onConflict + ")";
    }

    public int hashCode() {
        ExprNode exprNode = this.lvalue;
        ExprNode exprNode2 = this.value;
        ExprNode exprNode3 = this.position;
        OnConflict onConflict = this.onConflict;
        return (((exprNode != null ? exprNode.hashCode() : 0) * 31 + (exprNode2 != null ? exprNode2.hashCode() : 0)) * 31 + (exprNode3 != null ? exprNode3.hashCode() : 0)) * 31 + (onConflict != null ? ((Object)onConflict).hashCode() : 0);
    }

    public boolean equals(@Nullable Object object) {
        block3: {
            block2: {
                if (this == object) break block2;
                if (!(object instanceof InsertValueOp)) break block3;
                InsertValueOp insertValueOp = (InsertValueOp)object;
                if (!Intrinsics.areEqual(this.lvalue, insertValueOp.lvalue) || !Intrinsics.areEqual(this.value, insertValueOp.value) || !Intrinsics.areEqual(this.position, insertValueOp.position) || !Intrinsics.areEqual(this.onConflict, insertValueOp.onConflict)) break block3;
            }
            return true;
        }
        return false;
    }
}

