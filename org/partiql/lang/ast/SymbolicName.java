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
import org.partiql.lang.ast.HasMetas;
import org.partiql.lang.ast.MetaContainer;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\n\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u00012\u00020\u0002B\u0015\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u00a2\u0006\u0002\u0010\u0007J\t\u0010\u0010\u001a\u00020\u0004H\u00c6\u0003J\t\u0010\u0011\u001a\u00020\u0006H\u00c6\u0003J\u001d\u0010\u0012\u001a\u00020\u00002\b\b\u0002\u0010\u0003\u001a\u00020\u00042\b\b\u0002\u0010\u0005\u001a\u00020\u0006H\u00c6\u0001J\u0013\u0010\u0013\u001a\u00020\u00142\b\u0010\u0015\u001a\u0004\u0018\u00010\u0016H\u00d6\u0003J\t\u0010\u0017\u001a\u00020\u0018H\u00d6\u0001J\t\u0010\u0019\u001a\u00020\u0004H\u00d6\u0001R\u001a\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00010\tX\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0014\u0010\u0005\u001a\u00020\u0006X\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0011\u0010\u0003\u001a\u00020\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000f\u00a8\u0006\u001a"}, d2={"Lorg/partiql/lang/ast/SymbolicName;", "Lorg/partiql/lang/ast/AstNode;", "Lorg/partiql/lang/ast/HasMetas;", "name", "", "metas", "Lorg/partiql/lang/ast/MetaContainer;", "(Ljava/lang/String;Lorg/partiql/lang/ast/MetaContainer;)V", "children", "", "getChildren", "()Ljava/util/List;", "getMetas", "()Lorg/partiql/lang/ast/MetaContainer;", "getName", "()Ljava/lang/String;", "component1", "component2", "copy", "equals", "", "other", "", "hashCode", "", "toString", "lang"})
public final class SymbolicName
extends AstNode
implements HasMetas {
    @NotNull
    private final List<AstNode> children;
    @NotNull
    private final String name;
    @NotNull
    private final MetaContainer metas;

    @Override
    @NotNull
    public List<AstNode> getChildren() {
        return this.children;
    }

    @NotNull
    public final String getName() {
        return this.name;
    }

    @Override
    @NotNull
    public MetaContainer getMetas() {
        return this.metas;
    }

    public SymbolicName(@NotNull String name, @NotNull MetaContainer metas) {
        Intrinsics.checkParameterIsNotNull(name, "name");
        Intrinsics.checkParameterIsNotNull(metas, "metas");
        super(null);
        this.name = name;
        this.metas = metas;
        SymbolicName symbolicName = this;
        boolean bl = false;
        List list = CollectionsKt.emptyList();
        symbolicName.children = list;
    }

    @NotNull
    public final String component1() {
        return this.name;
    }

    @NotNull
    public final MetaContainer component2() {
        return this.getMetas();
    }

    @NotNull
    public final SymbolicName copy(@NotNull String name, @NotNull MetaContainer metas) {
        Intrinsics.checkParameterIsNotNull(name, "name");
        Intrinsics.checkParameterIsNotNull(metas, "metas");
        return new SymbolicName(name, metas);
    }

    public static /* synthetic */ SymbolicName copy$default(SymbolicName symbolicName, String string, MetaContainer metaContainer, int n, Object object) {
        if ((n & 1) != 0) {
            string = symbolicName.name;
        }
        if ((n & 2) != 0) {
            metaContainer = symbolicName.getMetas();
        }
        return symbolicName.copy(string, metaContainer);
    }

    @NotNull
    public String toString() {
        return "SymbolicName(name=" + this.name + ", metas=" + this.getMetas() + ")";
    }

    public int hashCode() {
        String string = this.name;
        MetaContainer metaContainer = this.getMetas();
        return (string != null ? string.hashCode() : 0) * 31 + (metaContainer != null ? metaContainer.hashCode() : 0);
    }

    public boolean equals(@Nullable Object object) {
        block3: {
            block2: {
                if (this == object) break block2;
                if (!(object instanceof SymbolicName)) break block3;
                SymbolicName symbolicName = (SymbolicName)object;
                if (!Intrinsics.areEqual(this.name, symbolicName.name) || !Intrinsics.areEqual(this.getMetas(), symbolicName.getMetas())) break block3;
            }
            return true;
        }
        return false;
    }
}

