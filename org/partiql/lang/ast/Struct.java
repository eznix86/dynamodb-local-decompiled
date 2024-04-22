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
import org.partiql.lang.ast.ExprNode;
import org.partiql.lang.ast.MetaContainer;
import org.partiql.lang.ast.StructField;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u001b\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u00a2\u0006\u0002\u0010\u0007J\u000f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003H\u00c6\u0003J\t\u0010\u0010\u001a\u00020\u0006H\u00c6\u0003J#\u0010\u0011\u001a\u00020\u00002\u000e\b\u0002\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u0006H\u00c6\u0001J\u0013\u0010\u0012\u001a\u00020\u00132\b\u0010\u0014\u001a\u0004\u0018\u00010\u0015H\u00d6\u0003J\t\u0010\u0016\u001a\u00020\u0017H\u00d6\u0001J\t\u0010\u0018\u001a\u00020\u0019H\u00d6\u0001R\u001a\u0010\b\u001a\b\u0012\u0004\u0012\u00020\t0\u0003X\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\u000bR\u0014\u0010\u0005\u001a\u00020\u0006X\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000e\u00a8\u0006\u001a"}, d2={"Lorg/partiql/lang/ast/Struct;", "Lorg/partiql/lang/ast/ExprNode;", "fields", "", "Lorg/partiql/lang/ast/StructField;", "metas", "Lorg/partiql/lang/ast/MetaContainer;", "(Ljava/util/List;Lorg/partiql/lang/ast/MetaContainer;)V", "children", "Lorg/partiql/lang/ast/AstNode;", "getChildren", "()Ljava/util/List;", "getFields", "getMetas", "()Lorg/partiql/lang/ast/MetaContainer;", "component1", "component2", "copy", "equals", "", "other", "", "hashCode", "", "toString", "", "lang"})
public final class Struct
extends ExprNode {
    @NotNull
    private final List<AstNode> children;
    @NotNull
    private final List<StructField> fields;
    @NotNull
    private final MetaContainer metas;

    @Override
    @NotNull
    public List<AstNode> getChildren() {
        return this.children;
    }

    @NotNull
    public final List<StructField> getFields() {
        return this.fields;
    }

    @Override
    @NotNull
    public MetaContainer getMetas() {
        return this.metas;
    }

    public Struct(@NotNull List<StructField> fields, @NotNull MetaContainer metas) {
        Intrinsics.checkParameterIsNotNull(fields, "fields");
        Intrinsics.checkParameterIsNotNull(metas, "metas");
        super(null);
        this.fields = fields;
        this.metas = metas;
        this.children = this.fields;
    }

    @NotNull
    public final List<StructField> component1() {
        return this.fields;
    }

    @NotNull
    public final MetaContainer component2() {
        return this.getMetas();
    }

    @NotNull
    public final Struct copy(@NotNull List<StructField> fields, @NotNull MetaContainer metas) {
        Intrinsics.checkParameterIsNotNull(fields, "fields");
        Intrinsics.checkParameterIsNotNull(metas, "metas");
        return new Struct(fields, metas);
    }

    public static /* synthetic */ Struct copy$default(Struct struct, List list, MetaContainer metaContainer, int n, Object object) {
        if ((n & 1) != 0) {
            list = struct.fields;
        }
        if ((n & 2) != 0) {
            metaContainer = struct.getMetas();
        }
        return struct.copy(list, metaContainer);
    }

    @NotNull
    public String toString() {
        return "Struct(fields=" + this.fields + ", metas=" + this.getMetas() + ")";
    }

    public int hashCode() {
        List<StructField> list = this.fields;
        MetaContainer metaContainer = this.getMetas();
        return (list != null ? ((Object)list).hashCode() : 0) * 31 + (metaContainer != null ? metaContainer.hashCode() : 0);
    }

    public boolean equals(@Nullable Object object) {
        block3: {
            block2: {
                if (this == object) break block2;
                if (!(object instanceof Struct)) break block3;
                Struct struct = (Struct)object;
                if (!Intrinsics.areEqual(this.fields, struct.fields) || !Intrinsics.areEqual(this.getMetas(), struct.getMetas())) break block3;
            }
            return true;
        }
        return false;
    }
}

