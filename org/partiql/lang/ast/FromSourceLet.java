/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package org.partiql.lang.ast;

import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.partiql.lang.ast.ExprNode;
import org.partiql.lang.ast.FromSource;
import org.partiql.lang.ast.FromSourceExpr;
import org.partiql.lang.ast.FromSourceUnpivot;
import org.partiql.lang.ast.LetVariables;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u000b\u001a\u00020\u00002\u0006\u0010\f\u001a\u00020\bR\u0012\u0010\u0003\u001a\u00020\u0004X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006R\u0012\u0010\u0007\u001a\u00020\bX\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\t\u0010\n\u0082\u0001\u0002\r\u000e\u00a8\u0006\u000f"}, d2={"Lorg/partiql/lang/ast/FromSourceLet;", "Lorg/partiql/lang/ast/FromSource;", "()V", "expr", "Lorg/partiql/lang/ast/ExprNode;", "getExpr", "()Lorg/partiql/lang/ast/ExprNode;", "variables", "Lorg/partiql/lang/ast/LetVariables;", "getVariables", "()Lorg/partiql/lang/ast/LetVariables;", "copy", "newVariables", "Lorg/partiql/lang/ast/FromSourceExpr;", "Lorg/partiql/lang/ast/FromSourceUnpivot;", "lang"})
public abstract class FromSourceLet
extends FromSource {
    @NotNull
    public abstract ExprNode getExpr();

    @NotNull
    public abstract LetVariables getVariables();

    @NotNull
    public final FromSourceLet copy(@NotNull LetVariables newVariables) {
        FromSourceLet fromSourceLet;
        Intrinsics.checkParameterIsNotNull(newVariables, "newVariables");
        FromSourceLet fromSourceLet2 = this;
        if (fromSourceLet2 instanceof FromSourceExpr) {
            fromSourceLet = FromSourceExpr.copy$default((FromSourceExpr)this, null, newVariables, 1, null);
        } else if (fromSourceLet2 instanceof FromSourceUnpivot) {
            fromSourceLet = FromSourceUnpivot.copy$default((FromSourceUnpivot)this, null, newVariables, null, 5, null);
        } else {
            throw new NoWhenBranchMatchedException();
        }
        return fromSourceLet;
    }

    private FromSourceLet() {
        super(null);
    }

    public /* synthetic */ FromSourceLet(DefaultConstructorMarker $constructor_marker) {
        this();
    }
}

