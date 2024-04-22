/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package org.partiql.lang.ast.passes;

import kotlin.Deprecated;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.partiql.lang.ast.DataManipulationOperation;
import org.partiql.lang.ast.DataType;
import org.partiql.lang.ast.ExprNode;
import org.partiql.lang.ast.FromSource;
import org.partiql.lang.ast.OnConflict;
import org.partiql.lang.ast.PathComponent;
import org.partiql.lang.ast.SelectListItem;
import org.partiql.lang.ast.SelectProjection;

@Deprecated(message="Use org.lang.partiql.domains.PartiqlAst.Visitor instead")
@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000N\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\bg\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u0016J\u0010\u0010\u0006\u001a\u00020\u00032\u0006\u0010\u0007\u001a\u00020\bH\u0016J\u0010\u0010\t\u001a\u00020\u00032\u0006\u0010\n\u001a\u00020\u000bH\u0016J\u0010\u0010\f\u001a\u00020\u00032\u0006\u0010\r\u001a\u00020\u000eH\u0016J\u0010\u0010\u000f\u001a\u00020\u00032\u0006\u0010\u0010\u001a\u00020\u0011H\u0016J\u0010\u0010\u0012\u001a\u00020\u00032\u0006\u0010\u0013\u001a\u00020\u0014H\u0016J\u0010\u0010\u0015\u001a\u00020\u00032\u0006\u0010\u0016\u001a\u00020\u0017H\u0016J\u0010\u0010\u0018\u001a\u00020\u00032\u0006\u0010\u0019\u001a\u00020\u001aH\u0016\u00a8\u0006\u001b"}, d2={"Lorg/partiql/lang/ast/passes/AstVisitor;", "", "visitDataManipulationOperation", "", "dmlOp", "Lorg/partiql/lang/ast/DataManipulationOperation;", "visitDataType", "dataType", "Lorg/partiql/lang/ast/DataType;", "visitExprNode", "expr", "Lorg/partiql/lang/ast/ExprNode;", "visitFromSource", "fromSource", "Lorg/partiql/lang/ast/FromSource;", "visitOnConflict", "onConflict", "Lorg/partiql/lang/ast/OnConflict;", "visitPathComponent", "pathComponent", "Lorg/partiql/lang/ast/PathComponent;", "visitSelectListItem", "selectListItem", "Lorg/partiql/lang/ast/SelectListItem;", "visitSelectProjection", "projection", "Lorg/partiql/lang/ast/SelectProjection;", "lang"})
public interface AstVisitor {
    public void visitExprNode(@NotNull ExprNode var1);

    public void visitSelectProjection(@NotNull SelectProjection var1);

    public void visitSelectListItem(@NotNull SelectListItem var1);

    public void visitFromSource(@NotNull FromSource var1);

    public void visitPathComponent(@NotNull PathComponent var1);

    public void visitDataType(@NotNull DataType var1);

    public void visitDataManipulationOperation(@NotNull DataManipulationOperation var1);

    public void visitOnConflict(@NotNull OnConflict var1);

    @Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=3)
    public static final class DefaultImpls {
        public static void visitExprNode(AstVisitor $this, @NotNull ExprNode expr) {
            Intrinsics.checkParameterIsNotNull(expr, "expr");
        }

        public static void visitSelectProjection(AstVisitor $this, @NotNull SelectProjection projection) {
            Intrinsics.checkParameterIsNotNull(projection, "projection");
        }

        public static void visitSelectListItem(AstVisitor $this, @NotNull SelectListItem selectListItem) {
            Intrinsics.checkParameterIsNotNull(selectListItem, "selectListItem");
        }

        public static void visitFromSource(AstVisitor $this, @NotNull FromSource fromSource) {
            Intrinsics.checkParameterIsNotNull(fromSource, "fromSource");
        }

        public static void visitPathComponent(AstVisitor $this, @NotNull PathComponent pathComponent) {
            Intrinsics.checkParameterIsNotNull(pathComponent, "pathComponent");
        }

        public static void visitDataType(AstVisitor $this, @NotNull DataType dataType) {
            Intrinsics.checkParameterIsNotNull(dataType, "dataType");
        }

        public static void visitDataManipulationOperation(AstVisitor $this, @NotNull DataManipulationOperation dmlOp) {
            Intrinsics.checkParameterIsNotNull(dmlOp, "dmlOp");
        }

        public static void visitOnConflict(AstVisitor $this, @NotNull OnConflict onConflict) {
            Intrinsics.checkParameterIsNotNull(onConflict, "onConflict");
        }
    }
}

