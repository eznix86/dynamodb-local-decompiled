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
import org.partiql.lang.ast.ReturningElem;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u0013\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u00a2\u0006\u0002\u0010\u0005J\u000f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003H\u00c6\u0003J\u0019\u0010\u000b\u001a\u00020\u00002\u000e\b\u0002\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003H\u00c6\u0001J\u0013\u0010\f\u001a\u00020\r2\b\u0010\u000e\u001a\u0004\u0018\u00010\u000fH\u00d6\u0003J\t\u0010\u0010\u001a\u00020\u0011H\u00d6\u0001J\t\u0010\u0012\u001a\u00020\u0013H\u00d6\u0001R\u001a\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00010\u0003X\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\b\u00a8\u0006\u0014"}, d2={"Lorg/partiql/lang/ast/ReturningExpr;", "Lorg/partiql/lang/ast/AstNode;", "returningElems", "", "Lorg/partiql/lang/ast/ReturningElem;", "(Ljava/util/List;)V", "children", "getChildren", "()Ljava/util/List;", "getReturningElems", "component1", "copy", "equals", "", "other", "", "hashCode", "", "toString", "", "lang"})
public final class ReturningExpr
extends AstNode {
    @NotNull
    private final List<AstNode> children;
    @NotNull
    private final List<ReturningElem> returningElems;

    @Override
    @NotNull
    public List<AstNode> getChildren() {
        return this.children;
    }

    @NotNull
    public final List<ReturningElem> getReturningElems() {
        return this.returningElems;
    }

    public ReturningExpr(@NotNull List<ReturningElem> returningElems) {
        Intrinsics.checkParameterIsNotNull(returningElems, "returningElems");
        super(null);
        this.returningElems = returningElems;
        this.children = this.returningElems;
    }

    @NotNull
    public final List<ReturningElem> component1() {
        return this.returningElems;
    }

    @NotNull
    public final ReturningExpr copy(@NotNull List<ReturningElem> returningElems) {
        Intrinsics.checkParameterIsNotNull(returningElems, "returningElems");
        return new ReturningExpr(returningElems);
    }

    public static /* synthetic */ ReturningExpr copy$default(ReturningExpr returningExpr, List list, int n, Object object) {
        if ((n & 1) != 0) {
            list = returningExpr.returningElems;
        }
        return returningExpr.copy(list);
    }

    @NotNull
    public String toString() {
        return "ReturningExpr(returningElems=" + this.returningElems + ")";
    }

    public int hashCode() {
        List<ReturningElem> list = this.returningElems;
        return list != null ? ((Object)list).hashCode() : 0;
    }

    public boolean equals(@Nullable Object object) {
        block3: {
            block2: {
                if (this == object) break block2;
                if (!(object instanceof ReturningExpr)) break block3;
                ReturningExpr returningExpr = (ReturningExpr)object;
                if (!Intrinsics.areEqual(this.returningElems, returningExpr.returningElems)) break block3;
            }
            return true;
        }
        return false;
    }
}

