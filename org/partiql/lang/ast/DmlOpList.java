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
import org.partiql.lang.ast.DataManipulationOperation;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u0013\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u00a2\u0006\u0002\u0010\u0005J\u000f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003H\u00c6\u0003J\u0019\u0010\u000b\u001a\u00020\u00002\u000e\b\u0002\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003H\u00c6\u0001J\u0013\u0010\f\u001a\u00020\r2\b\u0010\u000e\u001a\u0004\u0018\u00010\u000fH\u00d6\u0003J\t\u0010\u0010\u001a\u00020\u0011H\u00d6\u0001J\t\u0010\u0012\u001a\u00020\u0013H\u00d6\u0001R\u001a\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00010\u00038VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0007\u0010\bR\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\b\u00a8\u0006\u0014"}, d2={"Lorg/partiql/lang/ast/DmlOpList;", "Lorg/partiql/lang/ast/AstNode;", "ops", "", "Lorg/partiql/lang/ast/DataManipulationOperation;", "(Ljava/util/List;)V", "children", "getChildren", "()Ljava/util/List;", "getOps", "component1", "copy", "equals", "", "other", "", "hashCode", "", "toString", "", "lang"})
public final class DmlOpList
extends AstNode {
    @NotNull
    private final List<DataManipulationOperation> ops;

    @Override
    @NotNull
    public List<AstNode> getChildren() {
        return this.ops;
    }

    @NotNull
    public final List<DataManipulationOperation> getOps() {
        return this.ops;
    }

    public DmlOpList(@NotNull List<? extends DataManipulationOperation> ops) {
        Intrinsics.checkParameterIsNotNull(ops, "ops");
        super(null);
        this.ops = ops;
    }

    @NotNull
    public final List<DataManipulationOperation> component1() {
        return this.ops;
    }

    @NotNull
    public final DmlOpList copy(@NotNull List<? extends DataManipulationOperation> ops) {
        Intrinsics.checkParameterIsNotNull(ops, "ops");
        return new DmlOpList(ops);
    }

    public static /* synthetic */ DmlOpList copy$default(DmlOpList dmlOpList, List list, int n, Object object) {
        if ((n & 1) != 0) {
            list = dmlOpList.ops;
        }
        return dmlOpList.copy(list);
    }

    @NotNull
    public String toString() {
        return "DmlOpList(ops=" + this.ops + ")";
    }

    public int hashCode() {
        List<DataManipulationOperation> list = this.ops;
        return list != null ? ((Object)list).hashCode() : 0;
    }

    public boolean equals(@Nullable Object object) {
        block3: {
            block2: {
                if (this == object) break block2;
                if (!(object instanceof DmlOpList)) break block3;
                DmlOpList dmlOpList = (DmlOpList)object;
                if (!Intrinsics.areEqual(this.ops, dmlOpList.ops)) break block3;
            }
            return true;
        }
        return false;
    }
}

