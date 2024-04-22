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
import org.partiql.lang.ast.Assignment;
import org.partiql.lang.ast.AstNode;
import org.partiql.lang.ast.DataManipulationOperation;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\t\u0010\f\u001a\u00020\u0003H\u00c6\u0003J\u0013\u0010\r\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u0003H\u00c6\u0001J\u0013\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0011H\u00d6\u0003J\t\u0010\u0012\u001a\u00020\u0013H\u00d6\u0001J\t\u0010\u0014\u001a\u00020\u0015H\u00d6\u0001R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u001a\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\n\u0010\u000b\u00a8\u0006\u0016"}, d2={"Lorg/partiql/lang/ast/AssignmentOp;", "Lorg/partiql/lang/ast/DataManipulationOperation;", "assignment", "Lorg/partiql/lang/ast/Assignment;", "(Lorg/partiql/lang/ast/Assignment;)V", "getAssignment", "()Lorg/partiql/lang/ast/Assignment;", "children", "", "Lorg/partiql/lang/ast/AstNode;", "getChildren", "()Ljava/util/List;", "component1", "copy", "equals", "", "other", "", "hashCode", "", "toString", "", "lang"})
public final class AssignmentOp
extends DataManipulationOperation {
    @NotNull
    private final Assignment assignment;

    @Override
    @NotNull
    public List<AstNode> getChildren() {
        return CollectionsKt.listOf(this.assignment);
    }

    @NotNull
    public final Assignment getAssignment() {
        return this.assignment;
    }

    public AssignmentOp(@NotNull Assignment assignment) {
        Intrinsics.checkParameterIsNotNull(assignment, "assignment");
        super("set", null);
        this.assignment = assignment;
    }

    @NotNull
    public final Assignment component1() {
        return this.assignment;
    }

    @NotNull
    public final AssignmentOp copy(@NotNull Assignment assignment) {
        Intrinsics.checkParameterIsNotNull(assignment, "assignment");
        return new AssignmentOp(assignment);
    }

    public static /* synthetic */ AssignmentOp copy$default(AssignmentOp assignmentOp, Assignment assignment, int n, Object object) {
        if ((n & 1) != 0) {
            assignment = assignmentOp.assignment;
        }
        return assignmentOp.copy(assignment);
    }

    @NotNull
    public String toString() {
        return "AssignmentOp(assignment=" + this.assignment + ")";
    }

    public int hashCode() {
        Assignment assignment = this.assignment;
        return assignment != null ? ((Object)assignment).hashCode() : 0;
    }

    public boolean equals(@Nullable Object object) {
        block3: {
            block2: {
                if (this == object) break block2;
                if (!(object instanceof AssignmentOp)) break block3;
                AssignmentOp assignmentOp = (AssignmentOp)object;
                if (!Intrinsics.areEqual(this.assignment, assignmentOp.assignment)) break block3;
            }
            return true;
        }
        return false;
    }
}

