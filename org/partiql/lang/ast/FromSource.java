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
import org.jetbrains.annotations.NotNull;
import org.partiql.lang.ast.AstNode;
import org.partiql.lang.ast.FromSourceExpr;
import org.partiql.lang.ast.FromSourceJoin;
import org.partiql.lang.ast.FromSourceUnpivot;
import org.partiql.lang.ast.MetaContainer;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0006\u0010\u0003\u001a\u00020\u0004\u0082\u0001\u0002\u0005\u0006\u00a8\u0006\u0007"}, d2={"Lorg/partiql/lang/ast/FromSource;", "Lorg/partiql/lang/ast/AstNode;", "()V", "metas", "Lorg/partiql/lang/ast/MetaContainer;", "Lorg/partiql/lang/ast/FromSourceJoin;", "Lorg/partiql/lang/ast/FromSourceLet;", "lang"})
public abstract class FromSource
extends AstNode {
    @NotNull
    public final MetaContainer metas() {
        MetaContainer metaContainer;
        FromSource fromSource = this;
        if (fromSource instanceof FromSourceExpr) {
            metaContainer = ((FromSourceExpr)this).getExpr().getMetas();
        } else if (fromSource instanceof FromSourceJoin) {
            metaContainer = ((FromSourceJoin)this).getMetas();
        } else if (fromSource instanceof FromSourceUnpivot) {
            metaContainer = ((FromSourceUnpivot)this).getExpr().getMetas();
        } else {
            throw new NoWhenBranchMatchedException();
        }
        return metaContainer;
    }

    private FromSource() {
        super(null);
    }

    public /* synthetic */ FromSource(DefaultConstructorMarker $constructor_marker) {
        this();
    }
}

