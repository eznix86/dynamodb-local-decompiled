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
import org.partiql.lang.ast.ColumnComponent;
import org.partiql.lang.ast.HasMetas;
import org.partiql.lang.ast.MetaContainer;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u00012\u00020\u0002B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u00a2\u0006\u0002\u0010\u0005J\t\u0010\r\u001a\u00020\u0004H\u00c6\u0003J\u0013\u0010\u000e\u001a\u00020\u00002\b\b\u0002\u0010\u0003\u001a\u00020\u0004H\u00c6\u0001J\u0013\u0010\u000f\u001a\u00020\u00102\b\u0010\u0011\u001a\u0004\u0018\u00010\u0012H\u00d6\u0003J\t\u0010\u0013\u001a\u00020\u0014H\u00d6\u0001J\t\u0010\u0015\u001a\u00020\u0016H\u00d6\u0001R\u001a\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007X\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0014\u0010\u0003\u001a\u00020\u0004X\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\f\u00a8\u0006\u0017"}, d2={"Lorg/partiql/lang/ast/ReturningWildcard;", "Lorg/partiql/lang/ast/ColumnComponent;", "Lorg/partiql/lang/ast/HasMetas;", "metas", "Lorg/partiql/lang/ast/MetaContainer;", "(Lorg/partiql/lang/ast/MetaContainer;)V", "children", "", "Lorg/partiql/lang/ast/AstNode;", "getChildren", "()Ljava/util/List;", "getMetas", "()Lorg/partiql/lang/ast/MetaContainer;", "component1", "copy", "equals", "", "other", "", "hashCode", "", "toString", "", "lang"})
public final class ReturningWildcard
extends ColumnComponent
implements HasMetas {
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

    public ReturningWildcard(@NotNull MetaContainer metas) {
        Intrinsics.checkParameterIsNotNull(metas, "metas");
        super(null);
        this.metas = metas;
        ReturningWildcard returningWildcard = this;
        boolean bl = false;
        List list = CollectionsKt.emptyList();
        returningWildcard.children = list;
    }

    @NotNull
    public final MetaContainer component1() {
        return this.getMetas();
    }

    @NotNull
    public final ReturningWildcard copy(@NotNull MetaContainer metas) {
        Intrinsics.checkParameterIsNotNull(metas, "metas");
        return new ReturningWildcard(metas);
    }

    public static /* synthetic */ ReturningWildcard copy$default(ReturningWildcard returningWildcard, MetaContainer metaContainer, int n, Object object) {
        if ((n & 1) != 0) {
            metaContainer = returningWildcard.getMetas();
        }
        return returningWildcard.copy(metaContainer);
    }

    @NotNull
    public String toString() {
        return "ReturningWildcard(metas=" + this.getMetas() + ")";
    }

    public int hashCode() {
        MetaContainer metaContainer = this.getMetas();
        return metaContainer != null ? metaContainer.hashCode() : 0;
    }

    public boolean equals(@Nullable Object object) {
        block3: {
            block2: {
                if (this == object) break block2;
                if (!(object instanceof ReturningWildcard)) break block3;
                ReturningWildcard returningWildcard = (ReturningWildcard)object;
                if (!Intrinsics.areEqual(this.getMetas(), returningWildcard.getMetas())) break block3;
            }
            return true;
        }
        return false;
    }
}

