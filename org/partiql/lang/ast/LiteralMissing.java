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
import org.partiql.lang.ast.MetaContainer;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\t\u0010\f\u001a\u00020\u0003H\u00c6\u0003J\u0013\u0010\r\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u0003H\u00c6\u0001J\u0013\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0011H\u00d6\u0003J\t\u0010\u0012\u001a\u00020\u0013H\u00d6\u0001J\t\u0010\u0014\u001a\u00020\u0015H\u00d6\u0001R\u001a\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b\u00a8\u0006\u0016"}, d2={"Lorg/partiql/lang/ast/LiteralMissing;", "Lorg/partiql/lang/ast/ExprNode;", "metas", "Lorg/partiql/lang/ast/MetaContainer;", "(Lorg/partiql/lang/ast/MetaContainer;)V", "children", "", "Lorg/partiql/lang/ast/AstNode;", "getChildren", "()Ljava/util/List;", "getMetas", "()Lorg/partiql/lang/ast/MetaContainer;", "component1", "copy", "equals", "", "other", "", "hashCode", "", "toString", "", "lang"})
public final class LiteralMissing
extends ExprNode {
    @NotNull
    private final List<AstNode> children;
    @NotNull
    private final MetaContainer metas;

    @Override
    @NotNull
    public List<AstNode> getChildren() {
        return this.children;
    }

    @Override
    @NotNull
    public MetaContainer getMetas() {
        return this.metas;
    }

    public LiteralMissing(@NotNull MetaContainer metas) {
        Intrinsics.checkParameterIsNotNull(metas, "metas");
        super(null);
        this.metas = metas;
        LiteralMissing literalMissing = this;
        boolean bl = false;
        List list = CollectionsKt.emptyList();
        literalMissing.children = list;
    }

    @NotNull
    public final MetaContainer component1() {
        return this.getMetas();
    }

    @Override
    @NotNull
    public final LiteralMissing copy(@NotNull MetaContainer metas) {
        Intrinsics.checkParameterIsNotNull(metas, "metas");
        return new LiteralMissing(metas);
    }

    public static /* synthetic */ LiteralMissing copy$default(LiteralMissing literalMissing, MetaContainer metaContainer, int n, Object object) {
        if ((n & 1) != 0) {
            metaContainer = literalMissing.getMetas();
        }
        return literalMissing.copy(metaContainer);
    }

    @NotNull
    public String toString() {
        return "LiteralMissing(metas=" + this.getMetas() + ")";
    }

    public int hashCode() {
        MetaContainer metaContainer = this.getMetas();
        return metaContainer != null ? metaContainer.hashCode() : 0;
    }

    public boolean equals(@Nullable Object object) {
        block3: {
            block2: {
                if (this == object) break block2;
                if (!(object instanceof LiteralMissing)) break block3;
                LiteralMissing literalMissing = (LiteralMissing)object;
                if (!Intrinsics.areEqual(this.getMetas(), literalMissing.getMetas())) break block3;
            }
            return true;
        }
        return false;
    }
}

