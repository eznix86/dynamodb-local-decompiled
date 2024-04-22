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

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\t\u0010\u0010\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0011\u001a\u00020\u0005H\u00c6\u0003J\u001d\u0010\u0012\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0005H\u00c6\u0001J\u0013\u0010\u0013\u001a\u00020\u00142\b\u0010\u0015\u001a\u0004\u0018\u00010\u0016H\u00d6\u0003J\t\u0010\u0017\u001a\u00020\u0018H\u00d6\u0001J\t\u0010\u0019\u001a\u00020\u0003H\u00d6\u0001R\u001a\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\n\u0010\u000bR\u0014\u0010\u0004\u001a\u00020\u0005X\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000f\u00a8\u0006\u001a"}, d2={"Lorg/partiql/lang/ast/CreateTable;", "Lorg/partiql/lang/ast/ExprNode;", "tableName", "", "metas", "Lorg/partiql/lang/ast/MetaContainer;", "(Ljava/lang/String;Lorg/partiql/lang/ast/MetaContainer;)V", "children", "", "Lorg/partiql/lang/ast/AstNode;", "getChildren", "()Ljava/util/List;", "getMetas", "()Lorg/partiql/lang/ast/MetaContainer;", "getTableName", "()Ljava/lang/String;", "component1", "component2", "copy", "equals", "", "other", "", "hashCode", "", "toString", "lang"})
public final class CreateTable
extends ExprNode {
    @NotNull
    private final String tableName;
    @NotNull
    private final MetaContainer metas;

    @Override
    @NotNull
    public List<AstNode> getChildren() {
        return CollectionsKt.emptyList();
    }

    @NotNull
    public final String getTableName() {
        return this.tableName;
    }

    @Override
    @NotNull
    public MetaContainer getMetas() {
        return this.metas;
    }

    public CreateTable(@NotNull String tableName, @NotNull MetaContainer metas) {
        Intrinsics.checkParameterIsNotNull(tableName, "tableName");
        Intrinsics.checkParameterIsNotNull(metas, "metas");
        super(null);
        this.tableName = tableName;
        this.metas = metas;
    }

    @NotNull
    public final String component1() {
        return this.tableName;
    }

    @NotNull
    public final MetaContainer component2() {
        return this.getMetas();
    }

    @NotNull
    public final CreateTable copy(@NotNull String tableName, @NotNull MetaContainer metas) {
        Intrinsics.checkParameterIsNotNull(tableName, "tableName");
        Intrinsics.checkParameterIsNotNull(metas, "metas");
        return new CreateTable(tableName, metas);
    }

    public static /* synthetic */ CreateTable copy$default(CreateTable createTable, String string, MetaContainer metaContainer, int n, Object object) {
        if ((n & 1) != 0) {
            string = createTable.tableName;
        }
        if ((n & 2) != 0) {
            metaContainer = createTable.getMetas();
        }
        return createTable.copy(string, metaContainer);
    }

    @NotNull
    public String toString() {
        return "CreateTable(tableName=" + this.tableName + ", metas=" + this.getMetas() + ")";
    }

    public int hashCode() {
        String string = this.tableName;
        MetaContainer metaContainer = this.getMetas();
        return (string != null ? string.hashCode() : 0) * 31 + (metaContainer != null ? metaContainer.hashCode() : 0);
    }

    public boolean equals(@Nullable Object object) {
        block3: {
            block2: {
                if (this == object) break block2;
                if (!(object instanceof CreateTable)) break block3;
                CreateTable createTable = (CreateTable)object;
                if (!Intrinsics.areEqual(this.tableName, createTable.tableName) || !Intrinsics.areEqual(this.getMetas(), createTable.getMetas())) break block3;
            }
            return true;
        }
        return false;
    }
}

