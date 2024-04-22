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
import org.partiql.lang.ast.LetBinding;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u0013\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u00a2\u0006\u0002\u0010\u0005J\u000f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003H\u00c6\u0003J\u0019\u0010\u000b\u001a\u00020\u00002\u000e\b\u0002\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003H\u00c6\u0001J\u0013\u0010\f\u001a\u00020\r2\b\u0010\u000e\u001a\u0004\u0018\u00010\u000fH\u00d6\u0003J\t\u0010\u0010\u001a\u00020\u0011H\u00d6\u0001J\t\u0010\u0012\u001a\u00020\u0013H\u00d6\u0001R\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u001a\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00010\u0003X\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\u0007\u00a8\u0006\u0014"}, d2={"Lorg/partiql/lang/ast/LetSource;", "Lorg/partiql/lang/ast/AstNode;", "bindings", "", "Lorg/partiql/lang/ast/LetBinding;", "(Ljava/util/List;)V", "getBindings", "()Ljava/util/List;", "children", "getChildren", "component1", "copy", "equals", "", "other", "", "hashCode", "", "toString", "", "lang"})
public final class LetSource
extends AstNode {
    @NotNull
    private final List<AstNode> children;
    @NotNull
    private final List<LetBinding> bindings;

    @Override
    @NotNull
    public List<AstNode> getChildren() {
        return this.children;
    }

    @NotNull
    public final List<LetBinding> getBindings() {
        return this.bindings;
    }

    public LetSource(@NotNull List<LetBinding> bindings2) {
        Intrinsics.checkParameterIsNotNull(bindings2, "bindings");
        super(null);
        this.bindings = bindings2;
        this.children = this.bindings;
    }

    @NotNull
    public final List<LetBinding> component1() {
        return this.bindings;
    }

    @NotNull
    public final LetSource copy(@NotNull List<LetBinding> bindings2) {
        Intrinsics.checkParameterIsNotNull(bindings2, "bindings");
        return new LetSource(bindings2);
    }

    public static /* synthetic */ LetSource copy$default(LetSource letSource, List list, int n, Object object) {
        if ((n & 1) != 0) {
            list = letSource.bindings;
        }
        return letSource.copy(list);
    }

    @NotNull
    public String toString() {
        return "LetSource(bindings=" + this.bindings + ")";
    }

    public int hashCode() {
        List<LetBinding> list = this.bindings;
        return list != null ? ((Object)list).hashCode() : 0;
    }

    public boolean equals(@Nullable Object object) {
        block3: {
            block2: {
                if (this == object) break block2;
                if (!(object instanceof LetSource)) break block3;
                LetSource letSource = (LetSource)object;
                if (!Intrinsics.areEqual(this.bindings, letSource.bindings)) break block3;
            }
            return true;
        }
        return false;
    }
}

