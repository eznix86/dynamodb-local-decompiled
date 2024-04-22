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
import org.partiql.lang.ast.SqlDataType;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000e\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u00012\u00020\u0002B#\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006\u0012\u0006\u0010\b\u001a\u00020\t\u00a2\u0006\u0002\u0010\nJ\t\u0010\u0013\u001a\u00020\u0004H\u00c6\u0003J\u000f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u00c6\u0003J\t\u0010\u0015\u001a\u00020\tH\u00c6\u0003J-\u0010\u0016\u001a\u00020\u00002\b\b\u0002\u0010\u0003\u001a\u00020\u00042\u000e\b\u0002\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u00062\b\b\u0002\u0010\b\u001a\u00020\tH\u00c6\u0001J\u0013\u0010\u0017\u001a\u00020\u00182\b\u0010\u0019\u001a\u0004\u0018\u00010\u001aH\u00d6\u0003J\t\u0010\u001b\u001a\u00020\u001cH\u00d6\u0001J\t\u0010\u001d\u001a\u00020\u001eH\u00d6\u0001R\u0017\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u001a\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00010\u0006X\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\fR\u0014\u0010\b\u001a\u00020\tX\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0011\u0010\u0003\u001a\u00020\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012\u00a8\u0006\u001f"}, d2={"Lorg/partiql/lang/ast/DataType;", "Lorg/partiql/lang/ast/AstNode;", "Lorg/partiql/lang/ast/HasMetas;", "sqlDataType", "Lorg/partiql/lang/ast/SqlDataType;", "args", "", "", "metas", "Lorg/partiql/lang/ast/MetaContainer;", "(Lorg/partiql/lang/ast/SqlDataType;Ljava/util/List;Lorg/partiql/lang/ast/MetaContainer;)V", "getArgs", "()Ljava/util/List;", "children", "getChildren", "getMetas", "()Lorg/partiql/lang/ast/MetaContainer;", "getSqlDataType", "()Lorg/partiql/lang/ast/SqlDataType;", "component1", "component2", "component3", "copy", "equals", "", "other", "", "hashCode", "", "toString", "", "lang"})
public final class DataType
extends AstNode
implements HasMetas {
    @NotNull
    private final List<AstNode> children;
    @NotNull
    private final SqlDataType sqlDataType;
    @NotNull
    private final List<Long> args;
    @NotNull
    private final MetaContainer metas;

    @Override
    @NotNull
    public List<AstNode> getChildren() {
        return this.children;
    }

    @NotNull
    public final SqlDataType getSqlDataType() {
        return this.sqlDataType;
    }

    @NotNull
    public final List<Long> getArgs() {
        return this.args;
    }

    @Override
    @NotNull
    public MetaContainer getMetas() {
        return this.metas;
    }

    public DataType(@NotNull SqlDataType sqlDataType, @NotNull List<Long> args2, @NotNull MetaContainer metas) {
        Intrinsics.checkParameterIsNotNull((Object)sqlDataType, "sqlDataType");
        Intrinsics.checkParameterIsNotNull(args2, "args");
        Intrinsics.checkParameterIsNotNull(metas, "metas");
        super(null);
        this.sqlDataType = sqlDataType;
        this.args = args2;
        this.metas = metas;
        DataType dataType = this;
        boolean bl = false;
        List list = CollectionsKt.emptyList();
        dataType.children = list;
    }

    @NotNull
    public final SqlDataType component1() {
        return this.sqlDataType;
    }

    @NotNull
    public final List<Long> component2() {
        return this.args;
    }

    @NotNull
    public final MetaContainer component3() {
        return this.getMetas();
    }

    @NotNull
    public final DataType copy(@NotNull SqlDataType sqlDataType, @NotNull List<Long> args2, @NotNull MetaContainer metas) {
        Intrinsics.checkParameterIsNotNull((Object)sqlDataType, "sqlDataType");
        Intrinsics.checkParameterIsNotNull(args2, "args");
        Intrinsics.checkParameterIsNotNull(metas, "metas");
        return new DataType(sqlDataType, args2, metas);
    }

    public static /* synthetic */ DataType copy$default(DataType dataType, SqlDataType sqlDataType, List list, MetaContainer metaContainer, int n, Object object) {
        if ((n & 1) != 0) {
            sqlDataType = dataType.sqlDataType;
        }
        if ((n & 2) != 0) {
            list = dataType.args;
        }
        if ((n & 4) != 0) {
            metaContainer = dataType.getMetas();
        }
        return dataType.copy(sqlDataType, list, metaContainer);
    }

    @NotNull
    public String toString() {
        return "DataType(sqlDataType=" + (Object)((Object)this.sqlDataType) + ", args=" + this.args + ", metas=" + this.getMetas() + ")";
    }

    public int hashCode() {
        SqlDataType sqlDataType = this.sqlDataType;
        List<Long> list = this.args;
        MetaContainer metaContainer = this.getMetas();
        return ((sqlDataType != null ? ((Object)((Object)sqlDataType)).hashCode() : 0) * 31 + (list != null ? ((Object)list).hashCode() : 0)) * 31 + (metaContainer != null ? metaContainer.hashCode() : 0);
    }

    public boolean equals(@Nullable Object object) {
        block3: {
            block2: {
                if (this == object) break block2;
                if (!(object instanceof DataType)) break block3;
                DataType dataType = (DataType)object;
                if (!Intrinsics.areEqual((Object)this.sqlDataType, (Object)dataType.sqlDataType) || !Intrinsics.areEqual(this.args, dataType.args) || !Intrinsics.areEqual(this.getMetas(), dataType.getMetas())) break block3;
            }
            return true;
        }
        return false;
    }
}

