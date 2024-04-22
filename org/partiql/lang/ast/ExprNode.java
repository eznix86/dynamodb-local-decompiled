/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package org.partiql.lang.ast;

import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.internal.DefaultConstructorMarker;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.partiql.lang.ast.AstNode;
import org.partiql.lang.ast.CallAgg;
import org.partiql.lang.ast.CreateIndex;
import org.partiql.lang.ast.CreateTable;
import org.partiql.lang.ast.DataManipulation;
import org.partiql.lang.ast.DateTimeType;
import org.partiql.lang.ast.DropIndex;
import org.partiql.lang.ast.DropTable;
import org.partiql.lang.ast.Exec;
import org.partiql.lang.ast.HasMetas;
import org.partiql.lang.ast.Literal;
import org.partiql.lang.ast.LiteralMissing;
import org.partiql.lang.ast.MetaContainer;
import org.partiql.lang.ast.NAry;
import org.partiql.lang.ast.Parameter;
import org.partiql.lang.ast.Path;
import org.partiql.lang.ast.SearchedCase;
import org.partiql.lang.ast.Select;
import org.partiql.lang.ast.Seq;
import org.partiql.lang.ast.SimpleCase;
import org.partiql.lang.ast.Struct;
import org.partiql.lang.ast.Typed;
import org.partiql.lang.ast.VariableReference;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u00012\u00020\u0002B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0003J\u0012\u0010\u0004\u001a\u00020\u00002\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u0082\u0001\u0014\u0007\b\t\n\u000b\f\r\u000e\u000f\u0010\u0011\u0012\u0013\u0014\u0015\u0016\u0017\u0018\u0019\u001a\u00a8\u0006\u001b"}, d2={"Lorg/partiql/lang/ast/ExprNode;", "Lorg/partiql/lang/ast/AstNode;", "Lorg/partiql/lang/ast/HasMetas;", "()V", "copy", "newMetas", "Lorg/partiql/lang/ast/MetaContainer;", "Lorg/partiql/lang/ast/Literal;", "Lorg/partiql/lang/ast/LiteralMissing;", "Lorg/partiql/lang/ast/VariableReference;", "Lorg/partiql/lang/ast/Parameter;", "Lorg/partiql/lang/ast/NAry;", "Lorg/partiql/lang/ast/CallAgg;", "Lorg/partiql/lang/ast/Typed;", "Lorg/partiql/lang/ast/Exec;", "Lorg/partiql/lang/ast/Path;", "Lorg/partiql/lang/ast/SimpleCase;", "Lorg/partiql/lang/ast/SearchedCase;", "Lorg/partiql/lang/ast/DataManipulation;", "Lorg/partiql/lang/ast/Select;", "Lorg/partiql/lang/ast/CreateTable;", "Lorg/partiql/lang/ast/CreateIndex;", "Lorg/partiql/lang/ast/DropTable;", "Lorg/partiql/lang/ast/DropIndex;", "Lorg/partiql/lang/ast/Struct;", "Lorg/partiql/lang/ast/Seq;", "Lorg/partiql/lang/ast/DateTimeType;", "lang"})
public abstract class ExprNode
extends AstNode
implements HasMetas {
    @NotNull
    public final ExprNode copy(@Nullable MetaContainer newMetas) {
        ExprNode exprNode;
        MetaContainer metaContainer = newMetas;
        if (metaContainer == null) {
            metaContainer = this.getMetas();
        }
        MetaContainer metas = metaContainer;
        ExprNode exprNode2 = this;
        if (exprNode2 instanceof Literal) {
            exprNode = Literal.copy$default((Literal)this, null, metas, 1, null);
        } else if (exprNode2 instanceof LiteralMissing) {
            exprNode = ((LiteralMissing)this).copy(metas);
        } else if (exprNode2 instanceof VariableReference) {
            exprNode = VariableReference.copy$default((VariableReference)this, null, null, null, metas, 7, null);
        } else if (exprNode2 instanceof NAry) {
            exprNode = NAry.copy$default((NAry)this, null, null, metas, 3, null);
        } else if (exprNode2 instanceof CallAgg) {
            exprNode = CallAgg.copy$default((CallAgg)this, null, null, null, metas, 7, null);
        } else if (exprNode2 instanceof Typed) {
            exprNode = Typed.copy$default((Typed)this, null, null, null, metas, 7, null);
        } else if (exprNode2 instanceof Path) {
            exprNode = Path.copy$default((Path)this, null, null, metas, 3, null);
        } else if (exprNode2 instanceof SimpleCase) {
            exprNode = SimpleCase.copy$default((SimpleCase)this, null, null, null, metas, 7, null);
        } else if (exprNode2 instanceof SearchedCase) {
            exprNode = SearchedCase.copy$default((SearchedCase)this, null, null, metas, 3, null);
        } else if (exprNode2 instanceof Select) {
            exprNode = Select.copy$default((Select)this, null, null, null, null, null, null, null, null, null, metas, 511, null);
        } else if (exprNode2 instanceof Struct) {
            exprNode = Struct.copy$default((Struct)this, null, metas, 1, null);
        } else if (exprNode2 instanceof Seq) {
            exprNode = Seq.copy$default((Seq)this, null, null, metas, 3, null);
        } else if (exprNode2 instanceof DataManipulation) {
            exprNode = DataManipulation.copy$default((DataManipulation)this, null, null, null, null, metas, 15, null);
        } else if (exprNode2 instanceof CreateTable) {
            exprNode = CreateTable.copy$default((CreateTable)this, null, metas, 1, null);
        } else if (exprNode2 instanceof CreateIndex) {
            exprNode = CreateIndex.copy$default((CreateIndex)this, null, null, metas, 3, null);
        } else if (exprNode2 instanceof DropTable) {
            exprNode = DropTable.copy$default((DropTable)this, null, metas, 1, null);
        } else if (exprNode2 instanceof DropIndex) {
            exprNode = DropIndex.copy$default((DropIndex)this, null, null, metas, 3, null);
        } else if (exprNode2 instanceof Parameter) {
            exprNode = Parameter.copy$default((Parameter)this, 0, metas, 1, null);
        } else if (exprNode2 instanceof Exec) {
            exprNode = Exec.copy$default((Exec)this, null, null, metas, 3, null);
        } else if (exprNode2 instanceof DateTimeType.Date) {
            exprNode = DateTimeType.Date.copy$default((DateTimeType.Date)this, 0, 0, 0, metas, 7, null);
        } else if (exprNode2 instanceof DateTimeType.Time) {
            exprNode = DateTimeType.Time.copy$default((DateTimeType.Time)this, 0, 0, 0, 0, 0, null, metas, 63, null);
        } else {
            throw new NoWhenBranchMatchedException();
        }
        return exprNode;
    }

    public static /* synthetic */ ExprNode copy$default(ExprNode exprNode, MetaContainer metaContainer, int n, Object object) {
        if (object != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: copy");
        }
        if ((n & 1) != 0) {
            metaContainer = null;
        }
        return exprNode.copy(metaContainer);
    }

    private ExprNode() {
        super(null);
    }

    public /* synthetic */ ExprNode(DefaultConstructorMarker $constructor_marker) {
        this();
    }
}

