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
import org.partiql.lang.ast.passes.AstVisitor;

@Deprecated(message="Use AstNode#iterator() or AstNode#children()")
@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000P\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0017\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0016J\u0010\u0010\u0007\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\tH\u0016J\u0010\u0010\n\u001a\u00020\u00042\u0006\u0010\u000b\u001a\u00020\fH\u0016J\u0010\u0010\r\u001a\u00020\u00042\u0006\u0010\u000e\u001a\u00020\u000fH\u0016J\u0010\u0010\u0010\u001a\u00020\u00042\u0006\u0010\u0011\u001a\u00020\u0012H\u0016J\u0010\u0010\u0013\u001a\u00020\u00042\u0006\u0010\u0014\u001a\u00020\u0015H\u0016J\u0010\u0010\u0016\u001a\u00020\u00042\u0006\u0010\u0017\u001a\u00020\u0018H\u0016J\u0010\u0010\u0019\u001a\u00020\u00042\u0006\u0010\u001a\u001a\u00020\u001bH\u0016\u00a8\u0006\u001c"}, d2={"Lorg/partiql/lang/ast/passes/AstVisitorBase;", "Lorg/partiql/lang/ast/passes/AstVisitor;", "()V", "visitDataManipulationOperation", "", "dmlOp", "Lorg/partiql/lang/ast/DataManipulationOperation;", "visitDataType", "dataType", "Lorg/partiql/lang/ast/DataType;", "visitExprNode", "expr", "Lorg/partiql/lang/ast/ExprNode;", "visitFromSource", "fromSource", "Lorg/partiql/lang/ast/FromSource;", "visitOnConflict", "onConflict", "Lorg/partiql/lang/ast/OnConflict;", "visitPathComponent", "pathComponent", "Lorg/partiql/lang/ast/PathComponent;", "visitSelectListItem", "selectListItem", "Lorg/partiql/lang/ast/SelectListItem;", "visitSelectProjection", "projection", "Lorg/partiql/lang/ast/SelectProjection;", "lang"})
public class AstVisitorBase
implements AstVisitor {
    @Override
    public void visitExprNode(@NotNull ExprNode expr) {
        Intrinsics.checkParameterIsNotNull(expr, "expr");
    }

    @Override
    public void visitSelectProjection(@NotNull SelectProjection projection) {
        Intrinsics.checkParameterIsNotNull(projection, "projection");
    }

    @Override
    public void visitSelectListItem(@NotNull SelectListItem selectListItem) {
        Intrinsics.checkParameterIsNotNull(selectListItem, "selectListItem");
    }

    @Override
    public void visitFromSource(@NotNull FromSource fromSource) {
        Intrinsics.checkParameterIsNotNull(fromSource, "fromSource");
    }

    @Override
    public void visitPathComponent(@NotNull PathComponent pathComponent) {
        Intrinsics.checkParameterIsNotNull(pathComponent, "pathComponent");
    }

    @Override
    public void visitDataType(@NotNull DataType dataType) {
        Intrinsics.checkParameterIsNotNull(dataType, "dataType");
    }

    @Override
    public void visitDataManipulationOperation(@NotNull DataManipulationOperation dmlOp) {
        Intrinsics.checkParameterIsNotNull(dmlOp, "dmlOp");
    }

    @Override
    public void visitOnConflict(@NotNull OnConflict onConflict) {
        Intrinsics.checkParameterIsNotNull(onConflict, "onConflict");
    }
}

