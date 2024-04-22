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

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B#\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00010\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\bJ\t\u0010\u0012\u001a\u00020\u0003H\u00c6\u0003J\u000f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00010\u0005H\u00c6\u0003J\t\u0010\u0014\u001a\u00020\u0007H\u00c6\u0003J-\u0010\u0015\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\u000e\b\u0002\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u0007H\u00c6\u0001J\u0013\u0010\u0016\u001a\u00020\u00172\b\u0010\u0018\u001a\u0004\u0018\u00010\u0019H\u00d6\u0003J\t\u0010\u001a\u001a\u00020\u001bH\u00d6\u0001J\t\u0010\u001c\u001a\u00020\u0003H\u00d6\u0001R\u001a\u0010\t\u001a\b\u0012\u0004\u0012\u00020\n0\u00058VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u000b\u0010\fR\u0017\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00010\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\fR\u0014\u0010\u0006\u001a\u00020\u0007X\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011\u00a8\u0006\u001d"}, d2={"Lorg/partiql/lang/ast/CreateIndex;", "Lorg/partiql/lang/ast/ExprNode;", "tableName", "", "keys", "", "metas", "Lorg/partiql/lang/ast/MetaContainer;", "(Ljava/lang/String;Ljava/util/List;Lorg/partiql/lang/ast/MetaContainer;)V", "children", "Lorg/partiql/lang/ast/AstNode;", "getChildren", "()Ljava/util/List;", "getKeys", "getMetas", "()Lorg/partiql/lang/ast/MetaContainer;", "getTableName", "()Ljava/lang/String;", "component1", "component2", "component3", "copy", "equals", "", "other", "", "hashCode", "", "toString", "lang"})
public final class CreateIndex
extends ExprNode {
    @NotNull
    private final String tableName;
    @NotNull
    private final List<ExprNode> keys;
    @NotNull
    private final MetaContainer metas;

    @Override
    @NotNull
    public List<AstNode> getChildren() {
        return this.keys;
    }

    @NotNull
    public final String getTableName() {
        return this.tableName;
    }

    @NotNull
    public final List<ExprNode> getKeys() {
        return this.keys;
    }

    @Override
    @NotNull
    public MetaContainer getMetas() {
        return this.metas;
    }

    public CreateIndex(@NotNull String tableName, @NotNull List<? extends ExprNode> keys2, @NotNull MetaContainer metas) {
        Intrinsics.checkParameterIsNotNull(tableName, "tableName");
        Intrinsics.checkParameterIsNotNull(keys2, "keys");
        Intrinsics.checkParameterIsNotNull(metas, "metas");
        super(null);
        this.tableName = tableName;
        this.keys = keys2;
        this.metas = metas;
    }

    @NotNull
    public final String component1() {
        return this.tableName;
    }

    @NotNull
    public final List<ExprNode> component2() {
        return this.keys;
    }

    @NotNull
    public final MetaContainer component3() {
        return this.getMetas();
    }

    @NotNull
    public final CreateIndex copy(@NotNull String tableName, @NotNull List<? extends ExprNode> keys2, @NotNull MetaContainer metas) {
        Intrinsics.checkParameterIsNotNull(tableName, "tableName");
        Intrinsics.checkParameterIsNotNull(keys2, "keys");
        Intrinsics.checkParameterIsNotNull(metas, "metas");
        return new CreateIndex(tableName, keys2, metas);
    }

    public static /* synthetic */ CreateIndex copy$default(CreateIndex createIndex, String string, List list, MetaContainer metaContainer, int n, Object object) {
        if ((n & 1) != 0) {
            string = createIndex.tableName;
        }
        if ((n & 2) != 0) {
            list = createIndex.keys;
        }
        if ((n & 4) != 0) {
            metaContainer = createIndex.getMetas();
        }
        return createIndex.copy(string, list, metaContainer);
    }

    @NotNull
    public String toString() {
        return "CreateIndex(tableName=" + this.tableName + ", keys=" + this.keys + ", metas=" + this.getMetas() + ")";
    }

    public int hashCode() {
        String string = this.tableName;
        List<ExprNode> list = this.keys;
        MetaContainer metaContainer = this.getMetas();
        return ((string != null ? string.hashCode() : 0) * 31 + (list != null ? ((Object)list).hashCode() : 0)) * 31 + (metaContainer != null ? metaContainer.hashCode() : 0);
    }

    public boolean equals(@Nullable Object object) {
        block3: {
            block2: {
                if (this == object) break block2;
                if (!(object instanceof CreateIndex)) break block3;
                CreateIndex createIndex = (CreateIndex)object;
                if (!Intrinsics.areEqual(this.tableName, createIndex.tableName) || !Intrinsics.areEqual(this.keys, createIndex.keys) || !Intrinsics.areEqual(this.getMetas(), createIndex.getMetas())) break block3;
            }
            return true;
        }
        return false;
    }
}

