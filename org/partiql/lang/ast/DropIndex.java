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
import org.partiql.lang.ast.VariableReference;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\bJ\t\u0010\u0014\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0015\u001a\u00020\u0005H\u00c6\u0003J\t\u0010\u0016\u001a\u00020\u0007H\u00c6\u0003J'\u0010\u0017\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u0007H\u00c6\u0001J\u0013\u0010\u0018\u001a\u00020\u00192\b\u0010\u001a\u001a\u0004\u0018\u00010\u001bH\u00d6\u0003J\t\u0010\u001c\u001a\u00020\u001dH\u00d6\u0001J\t\u0010\u001e\u001a\u00020\u0003H\u00d6\u0001R\u001a\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\n8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\f\u0010\rR\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0014\u0010\u0006\u001a\u00020\u0007X\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013\u00a8\u0006\u001f"}, d2={"Lorg/partiql/lang/ast/DropIndex;", "Lorg/partiql/lang/ast/ExprNode;", "tableName", "", "identifier", "Lorg/partiql/lang/ast/VariableReference;", "metas", "Lorg/partiql/lang/ast/MetaContainer;", "(Ljava/lang/String;Lorg/partiql/lang/ast/VariableReference;Lorg/partiql/lang/ast/MetaContainer;)V", "children", "", "Lorg/partiql/lang/ast/AstNode;", "getChildren", "()Ljava/util/List;", "getIdentifier", "()Lorg/partiql/lang/ast/VariableReference;", "getMetas", "()Lorg/partiql/lang/ast/MetaContainer;", "getTableName", "()Ljava/lang/String;", "component1", "component2", "component3", "copy", "equals", "", "other", "", "hashCode", "", "toString", "lang"})
public final class DropIndex
extends ExprNode {
    @NotNull
    private final String tableName;
    @NotNull
    private final VariableReference identifier;
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

    @NotNull
    public final VariableReference getIdentifier() {
        return this.identifier;
    }

    @Override
    @NotNull
    public MetaContainer getMetas() {
        return this.metas;
    }

    public DropIndex(@NotNull String tableName, @NotNull VariableReference identifier, @NotNull MetaContainer metas) {
        Intrinsics.checkParameterIsNotNull(tableName, "tableName");
        Intrinsics.checkParameterIsNotNull(identifier, "identifier");
        Intrinsics.checkParameterIsNotNull(metas, "metas");
        super(null);
        this.tableName = tableName;
        this.identifier = identifier;
        this.metas = metas;
    }

    @NotNull
    public final String component1() {
        return this.tableName;
    }

    @NotNull
    public final VariableReference component2() {
        return this.identifier;
    }

    @NotNull
    public final MetaContainer component3() {
        return this.getMetas();
    }

    @NotNull
    public final DropIndex copy(@NotNull String tableName, @NotNull VariableReference identifier, @NotNull MetaContainer metas) {
        Intrinsics.checkParameterIsNotNull(tableName, "tableName");
        Intrinsics.checkParameterIsNotNull(identifier, "identifier");
        Intrinsics.checkParameterIsNotNull(metas, "metas");
        return new DropIndex(tableName, identifier, metas);
    }

    public static /* synthetic */ DropIndex copy$default(DropIndex dropIndex, String string, VariableReference variableReference, MetaContainer metaContainer, int n, Object object) {
        if ((n & 1) != 0) {
            string = dropIndex.tableName;
        }
        if ((n & 2) != 0) {
            variableReference = dropIndex.identifier;
        }
        if ((n & 4) != 0) {
            metaContainer = dropIndex.getMetas();
        }
        return dropIndex.copy(string, variableReference, metaContainer);
    }

    @NotNull
    public String toString() {
        return "DropIndex(tableName=" + this.tableName + ", identifier=" + this.identifier + ", metas=" + this.getMetas() + ")";
    }

    public int hashCode() {
        String string = this.tableName;
        VariableReference variableReference = this.identifier;
        MetaContainer metaContainer = this.getMetas();
        return ((string != null ? string.hashCode() : 0) * 31 + (variableReference != null ? ((Object)variableReference).hashCode() : 0)) * 31 + (metaContainer != null ? metaContainer.hashCode() : 0);
    }

    public boolean equals(@Nullable Object object) {
        block3: {
            block2: {
                if (this == object) break block2;
                if (!(object instanceof DropIndex)) break block3;
                DropIndex dropIndex = (DropIndex)object;
                if (!Intrinsics.areEqual(this.tableName, dropIndex.tableName) || !Intrinsics.areEqual(this.identifier, dropIndex.identifier) || !Intrinsics.areEqual(this.getMetas(), dropIndex.getMetas())) break block3;
            }
            return true;
        }
        return false;
    }
}

