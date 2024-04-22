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
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.partiql.lang.ast.AstNode;
import org.partiql.lang.ast.DataManipulationOperation;
import org.partiql.lang.ast.ReturningExpr;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u001f\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u00a2\u0006\u0002\u0010\u0007J\u000f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003H\u00c6\u0003J\u000b\u0010\u000f\u001a\u0004\u0018\u00010\u0006H\u00c6\u0003J%\u0010\u0010\u001a\u00020\u00002\u000e\b\u0002\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u0006H\u00c6\u0001J\u0013\u0010\u0011\u001a\u00020\u00122\b\u0010\u0013\u001a\u0004\u0018\u00010\u0014H\u00d6\u0003J\t\u0010\u0015\u001a\u00020\u0016H\u00d6\u0001J\t\u0010\u0017\u001a\u00020\u0018H\u00d6\u0001R\u001a\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00010\u00038VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\t\u0010\nR\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\nR\u0013\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\r\u00a8\u0006\u0019"}, d2={"Lorg/partiql/lang/ast/InsertReturning;", "Lorg/partiql/lang/ast/AstNode;", "ops", "", "Lorg/partiql/lang/ast/DataManipulationOperation;", "returning", "Lorg/partiql/lang/ast/ReturningExpr;", "(Ljava/util/List;Lorg/partiql/lang/ast/ReturningExpr;)V", "children", "getChildren", "()Ljava/util/List;", "getOps", "getReturning", "()Lorg/partiql/lang/ast/ReturningExpr;", "component1", "component2", "copy", "equals", "", "other", "", "hashCode", "", "toString", "", "lang"})
public final class InsertReturning
extends AstNode {
    @NotNull
    private final List<DataManipulationOperation> ops;
    @Nullable
    private final ReturningExpr returning;

    @Override
    @NotNull
    public List<AstNode> getChildren() {
        return this.ops;
    }

    @NotNull
    public final List<DataManipulationOperation> getOps() {
        return this.ops;
    }

    @Nullable
    public final ReturningExpr getReturning() {
        return this.returning;
    }

    public InsertReturning(@NotNull List<? extends DataManipulationOperation> ops, @Nullable ReturningExpr returning2) {
        Intrinsics.checkParameterIsNotNull(ops, "ops");
        super(null);
        this.ops = ops;
        this.returning = returning2;
    }

    public /* synthetic */ InsertReturning(List list, ReturningExpr returningExpr, int n, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n & 2) != 0) {
            returningExpr = null;
        }
        this(list, returningExpr);
    }

    @NotNull
    public final List<DataManipulationOperation> component1() {
        return this.ops;
    }

    @Nullable
    public final ReturningExpr component2() {
        return this.returning;
    }

    @NotNull
    public final InsertReturning copy(@NotNull List<? extends DataManipulationOperation> ops, @Nullable ReturningExpr returning2) {
        Intrinsics.checkParameterIsNotNull(ops, "ops");
        return new InsertReturning(ops, returning2);
    }

    public static /* synthetic */ InsertReturning copy$default(InsertReturning insertReturning, List list, ReturningExpr returningExpr, int n, Object object) {
        if ((n & 1) != 0) {
            list = insertReturning.ops;
        }
        if ((n & 2) != 0) {
            returningExpr = insertReturning.returning;
        }
        return insertReturning.copy(list, returningExpr);
    }

    @NotNull
    public String toString() {
        return "InsertReturning(ops=" + this.ops + ", returning=" + this.returning + ")";
    }

    public int hashCode() {
        List<DataManipulationOperation> list = this.ops;
        ReturningExpr returningExpr = this.returning;
        return (list != null ? ((Object)list).hashCode() : 0) * 31 + (returningExpr != null ? ((Object)returningExpr).hashCode() : 0);
    }

    public boolean equals(@Nullable Object object) {
        block3: {
            block2: {
                if (this == object) break block2;
                if (!(object instanceof InsertReturning)) break block3;
                InsertReturning insertReturning = (InsertReturning)object;
                if (!Intrinsics.areEqual(this.ops, insertReturning.ops) || !Intrinsics.areEqual(this.returning, insertReturning.returning)) break block3;
            }
            return true;
        }
        return false;
    }
}

