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
import org.partiql.lang.ast.ConflictAction;
import org.partiql.lang.ast.ExprNode;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\n\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\t\u0010\u000f\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0010\u001a\u00020\u0005H\u00c6\u0003J\u001d\u0010\u0011\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0005H\u00c6\u0001J\u0013\u0010\u0012\u001a\u00020\u00132\b\u0010\u0014\u001a\u0004\u0018\u00010\u0015H\u00d6\u0003J\t\u0010\u0016\u001a\u00020\u0017H\u00d6\u0001J\t\u0010\u0018\u001a\u00020\u0019H\u00d6\u0001R\u001a\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00010\bX\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000e\u00a8\u0006\u001a"}, d2={"Lorg/partiql/lang/ast/OnConflict;", "Lorg/partiql/lang/ast/AstNode;", "condition", "Lorg/partiql/lang/ast/ExprNode;", "conflictAction", "Lorg/partiql/lang/ast/ConflictAction;", "(Lorg/partiql/lang/ast/ExprNode;Lorg/partiql/lang/ast/ConflictAction;)V", "children", "", "getChildren", "()Ljava/util/List;", "getCondition", "()Lorg/partiql/lang/ast/ExprNode;", "getConflictAction", "()Lorg/partiql/lang/ast/ConflictAction;", "component1", "component2", "copy", "equals", "", "other", "", "hashCode", "", "toString", "", "lang"})
public final class OnConflict
extends AstNode {
    @NotNull
    private final List<AstNode> children;
    @NotNull
    private final ExprNode condition;
    @NotNull
    private final ConflictAction conflictAction;

    @Override
    @NotNull
    public List<AstNode> getChildren() {
        return this.children;
    }

    @NotNull
    public final ExprNode getCondition() {
        return this.condition;
    }

    @NotNull
    public final ConflictAction getConflictAction() {
        return this.conflictAction;
    }

    public OnConflict(@NotNull ExprNode condition, @NotNull ConflictAction conflictAction) {
        Intrinsics.checkParameterIsNotNull(condition, "condition");
        Intrinsics.checkParameterIsNotNull((Object)conflictAction, "conflictAction");
        super(null);
        this.condition = condition;
        this.conflictAction = conflictAction;
        this.children = CollectionsKt.listOf(this.condition);
    }

    @NotNull
    public final ExprNode component1() {
        return this.condition;
    }

    @NotNull
    public final ConflictAction component2() {
        return this.conflictAction;
    }

    @NotNull
    public final OnConflict copy(@NotNull ExprNode condition, @NotNull ConflictAction conflictAction) {
        Intrinsics.checkParameterIsNotNull(condition, "condition");
        Intrinsics.checkParameterIsNotNull((Object)conflictAction, "conflictAction");
        return new OnConflict(condition, conflictAction);
    }

    public static /* synthetic */ OnConflict copy$default(OnConflict onConflict, ExprNode exprNode, ConflictAction conflictAction, int n, Object object) {
        if ((n & 1) != 0) {
            exprNode = onConflict.condition;
        }
        if ((n & 2) != 0) {
            conflictAction = onConflict.conflictAction;
        }
        return onConflict.copy(exprNode, conflictAction);
    }

    @NotNull
    public String toString() {
        return "OnConflict(condition=" + this.condition + ", conflictAction=" + (Object)((Object)this.conflictAction) + ")";
    }

    public int hashCode() {
        ExprNode exprNode = this.condition;
        ConflictAction conflictAction = this.conflictAction;
        return (exprNode != null ? exprNode.hashCode() : 0) * 31 + (conflictAction != null ? ((Object)((Object)conflictAction)).hashCode() : 0);
    }

    public boolean equals(@Nullable Object object) {
        block3: {
            block2: {
                if (this == object) break block2;
                if (!(object instanceof OnConflict)) break block3;
                OnConflict onConflict = (OnConflict)object;
                if (!Intrinsics.areEqual(this.condition, onConflict.condition) || !Intrinsics.areEqual((Object)this.conflictAction, (Object)onConflict.conflictAction)) break block3;
            }
            return true;
        }
        return false;
    }
}

