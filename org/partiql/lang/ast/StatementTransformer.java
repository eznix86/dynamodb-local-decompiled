/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  com.amazon.ionelement.api.AnyElement
 *  com.amazon.ionelement.api.IonUtils
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 *  org.partiql.pig.runtime.LongPrimitive
 *  org.partiql.pig.runtime.SymbolPrimitive
 */
package org.partiql.lang.ast;

import com.amazon.ion.IonBool;
import com.amazon.ion.IonSystem;
import com.amazon.ionelement.api.AnyElement;
import com.amazon.ionelement.api.IonUtils;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.partiql.lang.ast.Assignment;
import org.partiql.lang.ast.AssignmentOp;
import org.partiql.lang.ast.AstKt;
import org.partiql.lang.ast.CallAgg;
import org.partiql.lang.ast.CaseSensitivity;
import org.partiql.lang.ast.ColumnComponent;
import org.partiql.lang.ast.ConflictAction;
import org.partiql.lang.ast.CreateIndex;
import org.partiql.lang.ast.CreateTable;
import org.partiql.lang.ast.DataManipulation;
import org.partiql.lang.ast.DataManipulationOperation;
import org.partiql.lang.ast.DataType;
import org.partiql.lang.ast.DateTimeType;
import org.partiql.lang.ast.DmlOpList;
import org.partiql.lang.ast.DropIndex;
import org.partiql.lang.ast.DropTable;
import org.partiql.lang.ast.Exec;
import org.partiql.lang.ast.ExprNode;
import org.partiql.lang.ast.FromSource;
import org.partiql.lang.ast.FromSourceExpr;
import org.partiql.lang.ast.FromSourceJoin;
import org.partiql.lang.ast.FromSourceUnpivot;
import org.partiql.lang.ast.GroupBy;
import org.partiql.lang.ast.GroupByItem;
import org.partiql.lang.ast.GroupingStrategy;
import org.partiql.lang.ast.InsertOp;
import org.partiql.lang.ast.InsertValueOp;
import org.partiql.lang.ast.JoinOp;
import org.partiql.lang.ast.LetBinding;
import org.partiql.lang.ast.LetSource;
import org.partiql.lang.ast.LetVariables;
import org.partiql.lang.ast.Literal;
import org.partiql.lang.ast.LiteralMissing;
import org.partiql.lang.ast.MetaContainer;
import org.partiql.lang.ast.MetaKt;
import org.partiql.lang.ast.NAry;
import org.partiql.lang.ast.NAryOp;
import org.partiql.lang.ast.OnConflict;
import org.partiql.lang.ast.OrderBy;
import org.partiql.lang.ast.OrderingSpec;
import org.partiql.lang.ast.Parameter;
import org.partiql.lang.ast.Path;
import org.partiql.lang.ast.PathComponent;
import org.partiql.lang.ast.PathComponentExpr;
import org.partiql.lang.ast.PathComponentUnpivot;
import org.partiql.lang.ast.PathComponentWildcard;
import org.partiql.lang.ast.RemoveOp;
import org.partiql.lang.ast.ReturningColumn;
import org.partiql.lang.ast.ReturningElem;
import org.partiql.lang.ast.ReturningExpr;
import org.partiql.lang.ast.ReturningMapping;
import org.partiql.lang.ast.ReturningWildcard;
import org.partiql.lang.ast.ScopeQualifier;
import org.partiql.lang.ast.SearchedCase;
import org.partiql.lang.ast.SearchedCaseWhen;
import org.partiql.lang.ast.Select;
import org.partiql.lang.ast.SelectListItem;
import org.partiql.lang.ast.SelectListItemExpr;
import org.partiql.lang.ast.SelectListItemProjectAll;
import org.partiql.lang.ast.SelectListItemStar;
import org.partiql.lang.ast.SelectProjection;
import org.partiql.lang.ast.SelectProjectionList;
import org.partiql.lang.ast.SelectProjectionPivot;
import org.partiql.lang.ast.SelectProjectionValue;
import org.partiql.lang.ast.Seq;
import org.partiql.lang.ast.SeqType;
import org.partiql.lang.ast.SetQuantifier;
import org.partiql.lang.ast.SimpleCase;
import org.partiql.lang.ast.SimpleCaseWhen;
import org.partiql.lang.ast.SortSpec;
import org.partiql.lang.ast.SqlDataType;
import org.partiql.lang.ast.StatementToExprNodeKt;
import org.partiql.lang.ast.Struct;
import org.partiql.lang.ast.StructField;
import org.partiql.lang.ast.SymbolicName;
import org.partiql.lang.ast.Typed;
import org.partiql.lang.ast.TypedOp;
import org.partiql.lang.ast.VariableReference;
import org.partiql.lang.domains.PartiqlAst;
import org.partiql.lang.util.ThreadInterruptUtilsKt;
import org.partiql.pig.runtime.LongPrimitive;
import org.partiql.pig.runtime.SymbolPrimitive;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000\u00f8\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u000e\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nJ\u000e\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u000bJ\f\u0010\f\u001a\u00020\r*\u00020\u000eH\u0002J\f\u0010\u000f\u001a\u00020\u0010*\u00020\u0011H\u0002J\f\u0010\u0012\u001a\u00020\u0013*\u00020\u0014H\u0002J\f\u0010\u0015\u001a\u00020\u0016*\u00020\u0017H\u0002J\f\u0010\u0018\u001a\u00020\b*\u00020\nH\u0002J\f\u0010\u0018\u001a\u00020\b*\u00020\u0019H\u0002J\f\u0010\u0018\u001a\u00020\b*\u00020\u001aH\u0002J\f\u0010\u0018\u001a\u00020\b*\u00020\u001bH\u0002J\f\u0010\u0018\u001a\u00020\b*\u00020\u001cH\u0002J\u0018\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\b0\u001e*\b\u0012\u0004\u0012\u00020\n0\u001eH\u0002J\f\u0010\u001f\u001a\u00020 *\u00020!H\u0002J\f\u0010\"\u001a\u00020#*\u00020$H\u0002J\f\u0010%\u001a\u00020&*\u00020'H\u0002J\f\u0010(\u001a\u00020)*\u00020*H\u0002J\f\u0010+\u001a\u00020,*\u00020-H\u0002J\f\u0010.\u001a\u00020/*\u000200H\u0002J\f\u00101\u001a\u000202*\u000203H\u0002J\f\u00104\u001a\u000205*\u000206H\u0002J\f\u00107\u001a\u000208*\u000209H\u0002J\u000e\u0010:\u001a\u00020;*\u0004\u0018\u00010<H\u0002J\f\u0010=\u001a\u00020>*\u00020?H\u0002J\f\u0010@\u001a\u00020A*\u00020BH\u0002J\f\u0010C\u001a\u00020D*\u00020EH\u0002J\f\u0010F\u001a\u00020G*\u00020HH\u0002J\f\u0010I\u001a\u00020J*\u00020KH\u0002R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006L"}, d2={"Lorg/partiql/lang/ast/StatementTransformer;", "", "ion", "Lcom/amazon/ion/IonSystem;", "(Lcom/amazon/ion/IonSystem;)V", "getIon", "()Lcom/amazon/ion/IonSystem;", "transform", "Lorg/partiql/lang/ast/ExprNode;", "stmt", "Lorg/partiql/lang/domains/PartiqlAst$Expr;", "Lorg/partiql/lang/domains/PartiqlAst$Statement;", "toCaseSensitivity", "Lorg/partiql/lang/ast/CaseSensitivity;", "Lorg/partiql/lang/domains/PartiqlAst$CaseSensitivity;", "toColumnComponent", "Lorg/partiql/lang/ast/ColumnComponent;", "Lorg/partiql/lang/domains/PartiqlAst$ColumnComponent;", "toDmlOp", "Lorg/partiql/lang/ast/DataManipulationOperation;", "Lorg/partiql/lang/domains/PartiqlAst$DmlOp;", "toDmlOps", "Lorg/partiql/lang/ast/DmlOpList;", "Lorg/partiql/lang/domains/PartiqlAst$DmlOpList;", "toExprNode", "Lorg/partiql/lang/domains/PartiqlAst$Statement$Ddl;", "Lorg/partiql/lang/domains/PartiqlAst$Statement$Dml;", "Lorg/partiql/lang/domains/PartiqlAst$Statement$Exec;", "Lorg/partiql/lang/domains/PartiqlAst$Statement$Query;", "toExprNodeList", "", "toExprNodeReturningMapping", "Lorg/partiql/lang/ast/ReturningMapping;", "Lorg/partiql/lang/domains/PartiqlAst$ReturningMapping;", "toExprNodeType", "Lorg/partiql/lang/ast/DataType;", "Lorg/partiql/lang/domains/PartiqlAst$Type;", "toFromSource", "Lorg/partiql/lang/ast/FromSource;", "Lorg/partiql/lang/domains/PartiqlAst$FromSource;", "toGroupBy", "Lorg/partiql/lang/ast/GroupBy;", "Lorg/partiql/lang/domains/PartiqlAst$GroupBy;", "toGroupingStrategy", "Lorg/partiql/lang/ast/GroupingStrategy;", "Lorg/partiql/lang/domains/PartiqlAst$GroupingStrategy;", "toJoinOp", "Lorg/partiql/lang/ast/JoinOp;", "Lorg/partiql/lang/domains/PartiqlAst$JoinType;", "toLetSource", "Lorg/partiql/lang/ast/LetSource;", "Lorg/partiql/lang/domains/PartiqlAst$Let;", "toOnConflictNode", "Lorg/partiql/lang/ast/OnConflict;", "Lorg/partiql/lang/domains/PartiqlAst$OnConflict;", "toOrderBy", "Lorg/partiql/lang/ast/OrderBy;", "Lorg/partiql/lang/domains/PartiqlAst$OrderBy;", "toOrderSpec", "Lorg/partiql/lang/ast/OrderingSpec;", "Lorg/partiql/lang/domains/PartiqlAst$OrderingSpec;", "toReturningExpr", "Lorg/partiql/lang/ast/ReturningExpr;", "Lorg/partiql/lang/domains/PartiqlAst$ReturningExpr;", "toScopeQualifier", "Lorg/partiql/lang/ast/ScopeQualifier;", "Lorg/partiql/lang/domains/PartiqlAst$ScopeQualifier;", "toSelectProjection", "Lorg/partiql/lang/ast/SelectProjection;", "Lorg/partiql/lang/domains/PartiqlAst$Projection;", "toSetQuantifier", "Lorg/partiql/lang/ast/SetQuantifier;", "Lorg/partiql/lang/domains/PartiqlAst$SetQuantifier;", "toSymbolicName", "Lorg/partiql/lang/ast/SymbolicName;", "Lorg/partiql/pig/runtime/SymbolPrimitive;", "lang"})
final class StatementTransformer {
    @NotNull
    private final IonSystem ion;

    @NotNull
    public final ExprNode transform(@NotNull PartiqlAst.Statement stmt) {
        ExprNode exprNode;
        Intrinsics.checkParameterIsNotNull(stmt, "stmt");
        PartiqlAst.Statement statement = stmt;
        if (statement instanceof PartiqlAst.Statement.Query) {
            exprNode = this.toExprNode((PartiqlAst.Statement.Query)stmt);
        } else if (statement instanceof PartiqlAst.Statement.Dml) {
            exprNode = this.toExprNode((PartiqlAst.Statement.Dml)stmt);
        } else if (statement instanceof PartiqlAst.Statement.Ddl) {
            exprNode = this.toExprNode((PartiqlAst.Statement.Ddl)stmt);
        } else if (statement instanceof PartiqlAst.Statement.Exec) {
            exprNode = this.toExprNode((PartiqlAst.Statement.Exec)stmt);
        } else {
            throw new NoWhenBranchMatchedException();
        }
        return exprNode;
    }

    @NotNull
    public final ExprNode transform(@NotNull PartiqlAst.Expr stmt) {
        Intrinsics.checkParameterIsNotNull(stmt, "stmt");
        return this.toExprNode(stmt);
    }

    private final ExprNode toExprNode(@NotNull PartiqlAst.Statement.Query $this$toExprNode) {
        return this.toExprNode($this$toExprNode.getExpr());
    }

    /*
     * WARNING - void declaration
     */
    private final List<ExprNode> toExprNodeList(@NotNull List<? extends PartiqlAst.Expr> $this$toExprNodeList) {
        void $this$mapTo$iv$iv;
        Iterable $this$map$iv = $this$toExprNodeList;
        boolean $i$f$map = false;
        Iterable iterable = $this$map$iv;
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
        boolean $i$f$mapTo = false;
        for (Object item$iv$iv : $this$mapTo$iv$iv) {
            void it;
            PartiqlAst.Expr expr = (PartiqlAst.Expr)item$iv$iv;
            Collection collection = destination$iv$iv;
            boolean bl = false;
            ExprNode exprNode = this.toExprNode((PartiqlAst.Expr)it);
            collection.add(exprNode);
        }
        return (List)destination$iv$iv;
    }

    /*
     * WARNING - void declaration
     */
    private final ExprNode toExprNode(@NotNull PartiqlAst.Expr $this$toExprNode) {
        ExprNode exprNode;
        ThreadInterruptUtilsKt.checkThreadInterrupted();
        MetaContainer metas = StatementToExprNodeKt.toPartiQlMetaContainer($this$toExprNode.getMetas());
        PartiqlAst.Expr expr = $this$toExprNode;
        if (expr instanceof PartiqlAst.Expr.Missing) {
            exprNode = new LiteralMissing(metas);
        } else if (expr instanceof PartiqlAst.Expr.Lit) {
            exprNode = new Literal(IonUtils.toIonValue((AnyElement)((PartiqlAst.Expr.Lit)$this$toExprNode).getValue().asAnyElement(), (IonSystem)this.ion), metas);
        } else if (expr instanceof PartiqlAst.Expr.Id) {
            exprNode = new VariableReference(((PartiqlAst.Expr.Id)$this$toExprNode).getName().getText(), this.toCaseSensitivity(((PartiqlAst.Expr.Id)$this$toExprNode).getCase()), this.toScopeQualifier(((PartiqlAst.Expr.Id)$this$toExprNode).getQualifier()), metas);
        } else if (expr instanceof PartiqlAst.Expr.Parameter) {
            exprNode = new Parameter((int)((PartiqlAst.Expr.Parameter)$this$toExprNode).getIndex().getValue(), metas);
        } else if (expr instanceof PartiqlAst.Expr.Not) {
            exprNode = new NAry(NAryOp.NOT, CollectionsKt.listOf(this.toExprNode(((PartiqlAst.Expr.Not)$this$toExprNode).getExpr())), metas);
        } else if (expr instanceof PartiqlAst.Expr.Pos) {
            exprNode = this.toExprNode(((PartiqlAst.Expr.Pos)$this$toExprNode).getExpr());
        } else if (expr instanceof PartiqlAst.Expr.Neg) {
            exprNode = new NAry(NAryOp.SUB, CollectionsKt.listOf(this.toExprNode(((PartiqlAst.Expr.Neg)$this$toExprNode).getExpr())), metas);
        } else if (expr instanceof PartiqlAst.Expr.Plus) {
            exprNode = new NAry(NAryOp.ADD, this.toExprNodeList(((PartiqlAst.Expr.Plus)$this$toExprNode).getOperands()), metas);
        } else if (expr instanceof PartiqlAst.Expr.Minus) {
            exprNode = new NAry(NAryOp.SUB, this.toExprNodeList(((PartiqlAst.Expr.Minus)$this$toExprNode).getOperands()), metas);
        } else if (expr instanceof PartiqlAst.Expr.Times) {
            exprNode = new NAry(NAryOp.MUL, this.toExprNodeList(((PartiqlAst.Expr.Times)$this$toExprNode).getOperands()), metas);
        } else if (expr instanceof PartiqlAst.Expr.Divide) {
            exprNode = new NAry(NAryOp.DIV, this.toExprNodeList(((PartiqlAst.Expr.Divide)$this$toExprNode).getOperands()), metas);
        } else if (expr instanceof PartiqlAst.Expr.Modulo) {
            exprNode = new NAry(NAryOp.MOD, this.toExprNodeList(((PartiqlAst.Expr.Modulo)$this$toExprNode).getOperands()), metas);
        } else if (expr instanceof PartiqlAst.Expr.Concat) {
            exprNode = new NAry(NAryOp.STRING_CONCAT, this.toExprNodeList(((PartiqlAst.Expr.Concat)$this$toExprNode).getOperands()), metas);
        } else if (expr instanceof PartiqlAst.Expr.And) {
            exprNode = new NAry(NAryOp.AND, this.toExprNodeList(((PartiqlAst.Expr.And)$this$toExprNode).getOperands()), metas);
        } else if (expr instanceof PartiqlAst.Expr.Or) {
            exprNode = new NAry(NAryOp.OR, this.toExprNodeList(((PartiqlAst.Expr.Or)$this$toExprNode).getOperands()), metas);
        } else if (expr instanceof PartiqlAst.Expr.Eq) {
            exprNode = new NAry(NAryOp.EQ, this.toExprNodeList(((PartiqlAst.Expr.Eq)$this$toExprNode).getOperands()), metas);
        } else if (expr instanceof PartiqlAst.Expr.Ne) {
            exprNode = new NAry(NAryOp.NE, this.toExprNodeList(((PartiqlAst.Expr.Ne)$this$toExprNode).getOperands()), metas);
        } else if (expr instanceof PartiqlAst.Expr.Gt) {
            exprNode = new NAry(NAryOp.GT, this.toExprNodeList(((PartiqlAst.Expr.Gt)$this$toExprNode).getOperands()), metas);
        } else if (expr instanceof PartiqlAst.Expr.Gte) {
            exprNode = new NAry(NAryOp.GTE, this.toExprNodeList(((PartiqlAst.Expr.Gte)$this$toExprNode).getOperands()), metas);
        } else if (expr instanceof PartiqlAst.Expr.Lt) {
            exprNode = new NAry(NAryOp.LT, this.toExprNodeList(((PartiqlAst.Expr.Lt)$this$toExprNode).getOperands()), metas);
        } else if (expr instanceof PartiqlAst.Expr.Lte) {
            exprNode = new NAry(NAryOp.LTE, this.toExprNodeList(((PartiqlAst.Expr.Lte)$this$toExprNode).getOperands()), metas);
        } else if (expr instanceof PartiqlAst.Expr.Union) {
            NAryOp nAryOp;
            PartiqlAst.SetQuantifier setQuantifier = ((PartiqlAst.Expr.Union)$this$toExprNode).getSetq();
            if (setQuantifier instanceof PartiqlAst.SetQuantifier.Distinct) {
                nAryOp = NAryOp.UNION;
            } else if (setQuantifier instanceof PartiqlAst.SetQuantifier.All) {
                nAryOp = NAryOp.UNION_ALL;
            } else {
                throw new NoWhenBranchMatchedException();
            }
            exprNode = new NAry(nAryOp, this.toExprNodeList(((PartiqlAst.Expr.Union)$this$toExprNode).getOperands()), metas);
        } else if (expr instanceof PartiqlAst.Expr.Intersect) {
            NAryOp nAryOp;
            PartiqlAst.SetQuantifier setQuantifier = ((PartiqlAst.Expr.Intersect)$this$toExprNode).getSetq();
            if (setQuantifier instanceof PartiqlAst.SetQuantifier.Distinct) {
                nAryOp = NAryOp.INTERSECT;
            } else if (setQuantifier instanceof PartiqlAst.SetQuantifier.All) {
                nAryOp = NAryOp.INTERSECT_ALL;
            } else {
                throw new NoWhenBranchMatchedException();
            }
            exprNode = new NAry(nAryOp, this.toExprNodeList(((PartiqlAst.Expr.Intersect)$this$toExprNode).getOperands()), metas);
        } else if (expr instanceof PartiqlAst.Expr.Except) {
            NAryOp nAryOp;
            PartiqlAst.SetQuantifier setQuantifier = ((PartiqlAst.Expr.Except)$this$toExprNode).getSetq();
            if (setQuantifier instanceof PartiqlAst.SetQuantifier.Distinct) {
                nAryOp = NAryOp.EXCEPT;
            } else if (setQuantifier instanceof PartiqlAst.SetQuantifier.All) {
                nAryOp = NAryOp.EXCEPT_ALL;
            } else {
                throw new NoWhenBranchMatchedException();
            }
            exprNode = new NAry(nAryOp, this.toExprNodeList(((PartiqlAst.Expr.Except)$this$toExprNode).getOperands()), metas);
        } else if (expr instanceof PartiqlAst.Expr.Like) {
            ExprNode[] exprNodeArray = new ExprNode[3];
            exprNodeArray[0] = this.toExprNode(((PartiqlAst.Expr.Like)$this$toExprNode).getValue());
            exprNodeArray[1] = this.toExprNode(((PartiqlAst.Expr.Like)$this$toExprNode).getPattern());
            PartiqlAst.Expr expr2 = ((PartiqlAst.Expr.Like)$this$toExprNode).getEscape();
            exprNodeArray[2] = expr2 != null ? this.toExprNode(expr2) : null;
            exprNode = new NAry(NAryOp.LIKE, CollectionsKt.listOfNotNull(exprNodeArray), metas);
        } else if (expr instanceof PartiqlAst.Expr.Between) {
            exprNode = new NAry(NAryOp.BETWEEN, CollectionsKt.listOf(this.toExprNode(((PartiqlAst.Expr.Between)$this$toExprNode).getValue()), this.toExprNode(((PartiqlAst.Expr.Between)$this$toExprNode).getFrom()), this.toExprNode(((PartiqlAst.Expr.Between)$this$toExprNode).getTo())), metas);
        } else if (expr instanceof PartiqlAst.Expr.InCollection) {
            exprNode = new NAry(NAryOp.IN, this.toExprNodeList(((PartiqlAst.Expr.InCollection)$this$toExprNode).getOperands()), metas);
        } else if (expr instanceof PartiqlAst.Expr.IsType) {
            exprNode = new Typed(TypedOp.IS, this.toExprNode(((PartiqlAst.Expr.IsType)$this$toExprNode).getValue()), this.toExprNodeType(((PartiqlAst.Expr.IsType)$this$toExprNode).getType()), metas);
        } else if (expr instanceof PartiqlAst.Expr.Cast) {
            exprNode = new Typed(TypedOp.CAST, this.toExprNode(((PartiqlAst.Expr.Cast)$this$toExprNode).getValue()), this.toExprNodeType(((PartiqlAst.Expr.Cast)$this$toExprNode).getAsType()), metas);
        } else if (expr instanceof PartiqlAst.Expr.SimpleCase) {
            Collection<SimpleCaseWhen> collection;
            void $this$mapTo$iv$iv;
            void $this$map$iv;
            Iterable iterable = ((PartiqlAst.Expr.SimpleCase)$this$toExprNode).getCases().getPairs();
            ExprNode exprNode2 = this.toExprNode(((PartiqlAst.Expr.SimpleCase)$this$toExprNode).getExpr());
            boolean $i$f$map = false;
            void var6_22 = $this$map$iv;
            Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
            boolean $i$f$mapTo = false;
            for (Object item$iv$iv : $this$mapTo$iv$iv) {
                void it;
                PartiqlAst.ExprPair exprPair = (PartiqlAst.ExprPair)item$iv$iv;
                collection = destination$iv$iv;
                boolean bl = false;
                SimpleCaseWhen simpleCaseWhen = new SimpleCaseWhen(this.toExprNode(it.getFirst()), this.toExprNode(it.getSecond()));
                collection.add(simpleCaseWhen);
            }
            collection = (List)destination$iv$iv;
            PartiqlAst.Expr expr3 = ((PartiqlAst.Expr.SimpleCase)$this$toExprNode).getDefault();
            MetaContainer metaContainer = metas;
            ExprNode exprNode3 = expr3 != null ? this.toExprNode(expr3) : null;
            List list = collection;
            ExprNode exprNode4 = exprNode2;
            exprNode = new SimpleCase(exprNode4, list, exprNode3, metaContainer);
        } else if (expr instanceof PartiqlAst.Expr.SearchedCase) {
            Collection<SearchedCaseWhen> collection;
            Iterable $this$map$iv = ((PartiqlAst.Expr.SearchedCase)$this$toExprNode).getCases().getPairs();
            boolean $i$f$map = false;
            Iterable $this$mapTo$iv$iv = $this$map$iv;
            Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
            boolean $i$f$mapTo = false;
            for (Object item$iv$iv : $this$mapTo$iv$iv) {
                PartiqlAst.ExprPair it = (PartiqlAst.ExprPair)item$iv$iv;
                collection = destination$iv$iv;
                boolean bl = false;
                SearchedCaseWhen searchedCaseWhen = new SearchedCaseWhen(this.toExprNode(it.getFirst()), this.toExprNode(it.getSecond()));
                collection.add(searchedCaseWhen);
            }
            collection = (List)destination$iv$iv;
            PartiqlAst.Expr expr4 = ((PartiqlAst.Expr.SearchedCase)$this$toExprNode).getDefault();
            MetaContainer metaContainer = metas;
            ExprNode exprNode5 = expr4 != null ? this.toExprNode(expr4) : null;
            List list = collection;
            exprNode = new SearchedCase(list, exprNode5, metaContainer);
        } else if (expr instanceof PartiqlAst.Expr.Struct) {
            Collection<StructField> collection;
            Iterable $this$map$iv = ((PartiqlAst.Expr.Struct)$this$toExprNode).getFields();
            boolean $i$f$map = false;
            Iterable $this$mapTo$iv$iv = $this$map$iv;
            Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
            boolean $i$f$mapTo = false;
            for (Object item$iv$iv : $this$mapTo$iv$iv) {
                PartiqlAst.ExprPair it = (PartiqlAst.ExprPair)item$iv$iv;
                collection = destination$iv$iv;
                boolean bl = false;
                StructField structField = new StructField(this.toExprNode(it.getFirst()), this.toExprNode(it.getSecond()));
                collection.add(structField);
            }
            collection = (List)destination$iv$iv;
            MetaContainer metaContainer = metas;
            List list = collection;
            exprNode = new Struct(list, metaContainer);
        } else if (expr instanceof PartiqlAst.Expr.Bag) {
            exprNode = new Seq(SeqType.BAG, this.toExprNodeList(((PartiqlAst.Expr.Bag)$this$toExprNode).getValues()), metas);
        } else if (expr instanceof PartiqlAst.Expr.List) {
            exprNode = new Seq(SeqType.LIST, this.toExprNodeList(((PartiqlAst.Expr.List)$this$toExprNode).getValues()), metas);
        } else if (expr instanceof PartiqlAst.Expr.Sexp) {
            exprNode = new Seq(SeqType.SEXP, this.toExprNodeList(((PartiqlAst.Expr.Sexp)$this$toExprNode).getValues()), metas);
        } else if (expr instanceof PartiqlAst.Expr.Path) {
            Collection<PathComponent> collection;
            Iterable $this$map$iv = ((PartiqlAst.Expr.Path)$this$toExprNode).getSteps();
            ExprNode exprNode6 = this.toExprNode(((PartiqlAst.Expr.Path)$this$toExprNode).getRoot());
            boolean $i$f$map = false;
            Iterable $this$mapTo$iv$iv = $this$map$iv;
            Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
            boolean $i$f$mapTo = false;
            for (Object item$iv$iv : $this$mapTo$iv$iv) {
                PathComponent pathComponent;
                PartiqlAst.PathStep it = (PartiqlAst.PathStep)item$iv$iv;
                collection = destination$iv$iv;
                boolean bl = false;
                MetaContainer componentMetas = StatementToExprNodeKt.toPartiQlMetaContainer(it.getMetas());
                PartiqlAst.PathStep pathStep = it;
                if (pathStep instanceof PartiqlAst.PathStep.PathExpr) {
                    pathComponent = new PathComponentExpr(this.toExprNode(((PartiqlAst.PathStep.PathExpr)it).getIndex()), this.toCaseSensitivity(((PartiqlAst.PathStep.PathExpr)it).getCase()));
                } else if (pathStep instanceof PartiqlAst.PathStep.PathUnpivot) {
                    pathComponent = new PathComponentUnpivot(componentMetas);
                } else if (pathStep instanceof PartiqlAst.PathStep.PathWildcard) {
                    pathComponent = new PathComponentWildcard(componentMetas);
                } else {
                    throw new NoWhenBranchMatchedException();
                }
                PathComponent pathComponent2 = pathComponent;
                collection.add(pathComponent2);
            }
            collection = (List)destination$iv$iv;
            MetaContainer metaContainer = metas;
            List list = collection;
            ExprNode exprNode7 = exprNode6;
            exprNode = new Path(exprNode7, list, metaContainer);
        } else if (expr instanceof PartiqlAst.Expr.Call) {
            Collection<ExprNode> collection;
            Iterable $this$map$iv = ((PartiqlAst.Expr.Call)$this$toExprNode).getArgs();
            Collection collection2 = CollectionsKt.listOf(new VariableReference(((PartiqlAst.Expr.Call)$this$toExprNode).getFuncName().getText(), CaseSensitivity.INSENSITIVE, ScopeQualifier.UNQUALIFIED, MetaKt.getEmptyMetaContainer()));
            NAryOp nAryOp = NAryOp.CALL;
            boolean $i$f$map = false;
            Iterable $this$mapTo$iv$iv = $this$map$iv;
            Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
            boolean $i$f$mapTo = false;
            for (Object item$iv$iv : $this$mapTo$iv$iv) {
                PartiqlAst.Expr it = (PartiqlAst.Expr)item$iv$iv;
                collection = destination$iv$iv;
                boolean bl = false;
                ExprNode exprNode8 = this.toExprNode(it);
                collection.add(exprNode8);
            }
            collection = (List)destination$iv$iv;
            MetaContainer metaContainer = metas;
            List list = CollectionsKt.plus(collection2, (Iterable)collection);
            NAryOp nAryOp2 = nAryOp;
            exprNode = new NAry(nAryOp2, list, metaContainer);
        } else if (expr instanceof PartiqlAst.Expr.CallAgg) {
            exprNode = new CallAgg(new VariableReference(((PartiqlAst.Expr.CallAgg)$this$toExprNode).getFuncName().getText(), CaseSensitivity.INSENSITIVE, ScopeQualifier.UNQUALIFIED, StatementToExprNodeKt.toPartiQlMetaContainer(((PartiqlAst.Expr.CallAgg)$this$toExprNode).getFuncName().getMetas())), this.toSetQuantifier(((PartiqlAst.Expr.CallAgg)$this$toExprNode).getSetq()), this.toExprNode(((PartiqlAst.Expr.CallAgg)$this$toExprNode).getArg()), metas);
        } else if (expr instanceof PartiqlAst.Expr.Select) {
            Object object = ((PartiqlAst.Expr.Select)$this$toExprNode).getSetq();
            if (object == null || (object = this.toSetQuantifier((PartiqlAst.SetQuantifier)object)) == null) {
                object = SetQuantifier.ALL;
            }
            PartiqlAst.Let let2 = ((PartiqlAst.Expr.Select)$this$toExprNode).getFromLet();
            PartiqlAst.Expr expr5 = ((PartiqlAst.Expr.Select)$this$toExprNode).getWhere();
            PartiqlAst.GroupBy groupBy2 = ((PartiqlAst.Expr.Select)$this$toExprNode).getGroup();
            PartiqlAst.Expr expr6 = ((PartiqlAst.Expr.Select)$this$toExprNode).getHaving();
            PartiqlAst.OrderBy orderBy = ((PartiqlAst.Expr.Select)$this$toExprNode).getOrder();
            PartiqlAst.Expr expr7 = ((PartiqlAst.Expr.Select)$this$toExprNode).getLimit();
            exprNode = new Select((SetQuantifier)((Object)object), this.toSelectProjection(((PartiqlAst.Expr.Select)$this$toExprNode).getProject()), this.toFromSource(((PartiqlAst.Expr.Select)$this$toExprNode).getFrom()), let2 != null ? this.toLetSource(let2) : null, expr5 != null ? this.toExprNode(expr5) : null, groupBy2 != null ? this.toGroupBy(groupBy2) : null, expr6 != null ? this.toExprNode(expr6) : null, orderBy != null ? this.toOrderBy(orderBy) : null, expr7 != null ? this.toExprNode(expr7) : null, metas);
        } else if (expr instanceof PartiqlAst.Expr.Date) {
            exprNode = new DateTimeType.Date((int)((PartiqlAst.Expr.Date)$this$toExprNode).getYear().getValue(), (int)((PartiqlAst.Expr.Date)$this$toExprNode).getMonth().getValue(), (int)((PartiqlAst.Expr.Date)$this$toExprNode).getDay().getValue(), metas);
        } else if (expr instanceof PartiqlAst.Expr.LitTime) {
            LongPrimitive longPrimitive = ((PartiqlAst.Expr.LitTime)$this$toExprNode).getValue().getTzMinutes();
            exprNode = new DateTimeType.Time((int)((PartiqlAst.Expr.LitTime)$this$toExprNode).getValue().getHour().getValue(), (int)((PartiqlAst.Expr.LitTime)$this$toExprNode).getValue().getMinute().getValue(), (int)((PartiqlAst.Expr.LitTime)$this$toExprNode).getValue().getSecond().getValue(), (int)((PartiqlAst.Expr.LitTime)$this$toExprNode).getValue().getNano().getValue(), (int)((PartiqlAst.Expr.LitTime)$this$toExprNode).getValue().getPrecision().getValue(), longPrimitive != null ? Integer.valueOf((int)longPrimitive.getValue()) : null, metas);
        } else {
            throw new NoWhenBranchMatchedException();
        }
        return exprNode;
    }

    /*
     * WARNING - void declaration
     */
    private final SelectProjection toSelectProjection(@NotNull PartiqlAst.Projection $this$toSelectProjection) {
        SelectProjection selectProjection;
        MetaContainer metas = StatementToExprNodeKt.toPartiQlMetaContainer($this$toSelectProjection.getMetas());
        PartiqlAst.Projection projection = $this$toSelectProjection;
        if (projection instanceof PartiqlAst.Projection.ProjectStar) {
            selectProjection = new SelectProjectionList(CollectionsKt.listOf(new SelectListItemStar(metas)));
        } else if (projection instanceof PartiqlAst.Projection.ProjectValue) {
            selectProjection = new SelectProjectionValue(this.toExprNode(((PartiqlAst.Projection.ProjectValue)$this$toSelectProjection).getValue()));
        } else if (projection instanceof PartiqlAst.Projection.ProjectPivot) {
            selectProjection = new SelectProjectionPivot(this.toExprNode(((PartiqlAst.Projection.ProjectPivot)$this$toSelectProjection).getValue()), this.toExprNode(((PartiqlAst.Projection.ProjectPivot)$this$toSelectProjection).getKey()));
        } else if (projection instanceof PartiqlAst.Projection.ProjectList) {
            Collection<SelectListItem> collection;
            void $this$mapTo$iv$iv;
            Iterable $this$map$iv = ((PartiqlAst.Projection.ProjectList)$this$toSelectProjection).getProjectItems();
            boolean $i$f$map = false;
            Iterable iterable = $this$map$iv;
            Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
            boolean $i$f$mapTo = false;
            for (Object item$iv$iv : $this$mapTo$iv$iv) {
                SelectListItem selectListItem;
                void it;
                PartiqlAst.ProjectItem projectItem = (PartiqlAst.ProjectItem)item$iv$iv;
                collection = destination$iv$iv;
                boolean bl = false;
                void var13_13 = it;
                if (var13_13 instanceof PartiqlAst.ProjectItem.ProjectAll) {
                    selectListItem = new SelectListItemProjectAll(this.toExprNode(((PartiqlAst.ProjectItem.ProjectAll)it).getExpr()));
                } else if (var13_13 instanceof PartiqlAst.ProjectItem.ProjectExpr) {
                    SymbolPrimitive symbolPrimitive = ((PartiqlAst.ProjectItem.ProjectExpr)it).getAsAlias();
                    selectListItem = new SelectListItemExpr(this.toExprNode(((PartiqlAst.ProjectItem.ProjectExpr)it).getExpr()), symbolPrimitive != null ? this.toSymbolicName(symbolPrimitive) : null);
                } else {
                    throw new NoWhenBranchMatchedException();
                }
                SelectListItem selectListItem2 = selectListItem;
                collection.add(selectListItem2);
            }
            collection = (List)destination$iv$iv;
            List list = collection;
            selectProjection = new SelectProjectionList(list);
        } else {
            throw new NoWhenBranchMatchedException();
        }
        return selectProjection;
    }

    private final FromSource toFromSource(@NotNull PartiqlAst.FromSource $this$toFromSource) {
        FromSource fromSource;
        MetaContainer metas = StatementToExprNodeKt.toPartiQlMetaContainer($this$toFromSource.getMetas());
        PartiqlAst.FromSource fromSource2 = $this$toFromSource;
        if (fromSource2 instanceof PartiqlAst.FromSource.Scan) {
            SymbolPrimitive symbolPrimitive = ((PartiqlAst.FromSource.Scan)$this$toFromSource).getAsAlias();
            SymbolPrimitive symbolPrimitive2 = ((PartiqlAst.FromSource.Scan)$this$toFromSource).getAtAlias();
            SymbolPrimitive symbolPrimitive3 = ((PartiqlAst.FromSource.Scan)$this$toFromSource).getByAlias();
            fromSource = new FromSourceExpr(this.toExprNode(((PartiqlAst.FromSource.Scan)$this$toFromSource).getExpr()), new LetVariables(symbolPrimitive != null ? this.toSymbolicName(symbolPrimitive) : null, symbolPrimitive2 != null ? this.toSymbolicName(symbolPrimitive2) : null, symbolPrimitive3 != null ? this.toSymbolicName(symbolPrimitive3) : null));
        } else if (fromSource2 instanceof PartiqlAst.FromSource.Unpivot) {
            SymbolPrimitive symbolPrimitive = ((PartiqlAst.FromSource.Unpivot)$this$toFromSource).getAsAlias();
            SymbolPrimitive symbolPrimitive4 = ((PartiqlAst.FromSource.Unpivot)$this$toFromSource).getAtAlias();
            SymbolPrimitive symbolPrimitive5 = ((PartiqlAst.FromSource.Unpivot)$this$toFromSource).getByAlias();
            fromSource = new FromSourceUnpivot(this.toExprNode(((PartiqlAst.FromSource.Unpivot)$this$toFromSource).getExpr()), new LetVariables(symbolPrimitive != null ? this.toSymbolicName(symbolPrimitive) : null, symbolPrimitive4 != null ? this.toSymbolicName(symbolPrimitive4) : null, symbolPrimitive5 != null ? this.toSymbolicName(symbolPrimitive5) : null), metas);
        } else if (fromSource2 instanceof PartiqlAst.FromSource.Join) {
            JoinOp joinOp = this.toJoinOp(((PartiqlAst.FromSource.Join)$this$toFromSource).getType());
            FromSource fromSource3 = this.toFromSource(((PartiqlAst.FromSource.Join)$this$toFromSource).getLeft());
            FromSource fromSource4 = this.toFromSource(((PartiqlAst.FromSource.Join)$this$toFromSource).getRight());
            Object object = ((PartiqlAst.FromSource.Join)$this$toFromSource).getPredicate();
            if (object == null || (object = this.toExprNode((PartiqlAst.Expr)object)) == null) {
                IonBool ionBool = this.ion.newBool(true);
                Intrinsics.checkExpressionValueIsNotNull(ionBool, "ion.newBool(true)");
                object = new Literal(ionBool, MetaKt.getEmptyMetaContainer());
            }
            fromSource = new FromSourceJoin(joinOp, fromSource3, fromSource4, (ExprNode)object, metas);
        } else {
            throw new NoWhenBranchMatchedException();
        }
        return fromSource;
    }

    private final JoinOp toJoinOp(@NotNull PartiqlAst.JoinType $this$toJoinOp) {
        JoinOp joinOp;
        PartiqlAst.JoinType joinType = $this$toJoinOp;
        if (joinType instanceof PartiqlAst.JoinType.Inner) {
            joinOp = JoinOp.INNER;
        } else if (joinType instanceof PartiqlAst.JoinType.Left) {
            joinOp = JoinOp.LEFT;
        } else if (joinType instanceof PartiqlAst.JoinType.Right) {
            joinOp = JoinOp.RIGHT;
        } else if (joinType instanceof PartiqlAst.JoinType.Full) {
            joinOp = JoinOp.OUTER;
        } else {
            throw new NoWhenBranchMatchedException();
        }
        return joinOp;
    }

    /*
     * WARNING - void declaration
     */
    private final LetSource toLetSource(@NotNull PartiqlAst.Let $this$toLetSource) {
        Collection<LetBinding> collection;
        void $this$mapTo$iv$iv;
        Iterable $this$map$iv = $this$toLetSource.getLetBindings();
        boolean $i$f$map = false;
        Iterable iterable = $this$map$iv;
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
        boolean $i$f$mapTo = false;
        for (Object item$iv$iv : $this$mapTo$iv$iv) {
            void it;
            PartiqlAst.LetBinding letBinding = (PartiqlAst.LetBinding)item$iv$iv;
            collection = destination$iv$iv;
            boolean bl = false;
            LetBinding letBinding2 = new LetBinding(this.toExprNode(it.getExpr()), this.toSymbolicName(it.getName()));
            collection.add(letBinding2);
        }
        collection = (List)destination$iv$iv;
        List list = collection;
        return new LetSource(list);
    }

    private final SymbolicName toSymbolicName(@NotNull SymbolPrimitive $this$toSymbolicName) {
        return new SymbolicName($this$toSymbolicName.getText(), StatementToExprNodeKt.toPartiQlMetaContainer($this$toSymbolicName.getMetas()));
    }

    /*
     * WARNING - void declaration
     */
    private final OrderBy toOrderBy(@NotNull PartiqlAst.OrderBy $this$toOrderBy) {
        Collection<SortSpec> collection;
        void $this$mapTo$iv$iv;
        Iterable $this$map$iv = $this$toOrderBy.getSortSpecs();
        boolean $i$f$map = false;
        Iterable iterable = $this$map$iv;
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
        boolean $i$f$mapTo = false;
        for (Object item$iv$iv : $this$mapTo$iv$iv) {
            void it;
            PartiqlAst.SortSpec sortSpec = (PartiqlAst.SortSpec)item$iv$iv;
            collection = destination$iv$iv;
            boolean bl = false;
            SortSpec sortSpec2 = new SortSpec(this.toExprNode(it.getExpr()), this.toOrderSpec(it.getOrderingSpec()));
            collection.add(sortSpec2);
        }
        collection = (List)destination$iv$iv;
        List list = collection;
        return new OrderBy(list);
    }

    private final OrderingSpec toOrderSpec(@Nullable PartiqlAst.OrderingSpec $this$toOrderSpec) {
        PartiqlAst.OrderingSpec orderingSpec = $this$toOrderSpec;
        return orderingSpec instanceof PartiqlAst.OrderingSpec.Desc ? OrderingSpec.DESC : OrderingSpec.ASC;
    }

    /*
     * WARNING - void declaration
     */
    private final GroupBy toGroupBy(@NotNull PartiqlAst.GroupBy $this$toGroupBy) {
        Collection<GroupByItem> collection;
        void $this$mapTo$iv$iv;
        void $this$map$iv;
        Iterable iterable = $this$toGroupBy.getKeyList().getKeys();
        GroupingStrategy groupingStrategy = this.toGroupingStrategy($this$toGroupBy.getStrategy());
        boolean $i$f$map = false;
        void var4_5 = $this$map$iv;
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
        boolean $i$f$mapTo = false;
        for (Object item$iv$iv : $this$mapTo$iv$iv) {
            void it;
            PartiqlAst.GroupKey groupKey = (PartiqlAst.GroupKey)item$iv$iv;
            collection = destination$iv$iv;
            boolean bl = false;
            SymbolPrimitive symbolPrimitive = it.getAsAlias();
            GroupByItem groupByItem = new GroupByItem(this.toExprNode(it.getExpr()), symbolPrimitive != null ? this.toSymbolicName(symbolPrimitive) : null);
            collection.add(groupByItem);
        }
        collection = (List)destination$iv$iv;
        SymbolPrimitive symbolPrimitive = $this$toGroupBy.getGroupAsAlias();
        SymbolicName symbolicName = symbolPrimitive != null ? this.toSymbolicName(symbolPrimitive) : null;
        List list = collection;
        GroupingStrategy groupingStrategy2 = groupingStrategy;
        return new GroupBy(groupingStrategy2, list, symbolicName);
    }

    private final GroupingStrategy toGroupingStrategy(@NotNull PartiqlAst.GroupingStrategy $this$toGroupingStrategy) {
        GroupingStrategy groupingStrategy;
        PartiqlAst.GroupingStrategy groupingStrategy2 = $this$toGroupingStrategy;
        if (groupingStrategy2 instanceof PartiqlAst.GroupingStrategy.GroupFull) {
            groupingStrategy = GroupingStrategy.FULL;
        } else if (groupingStrategy2 instanceof PartiqlAst.GroupingStrategy.GroupPartial) {
            groupingStrategy = GroupingStrategy.PARTIAL;
        } else {
            throw new NoWhenBranchMatchedException();
        }
        return groupingStrategy;
    }

    private final DataType toExprNodeType(@NotNull PartiqlAst.Type $this$toExprNodeType) {
        DataType dataType;
        MetaContainer metas = StatementToExprNodeKt.toPartiQlMetaContainer($this$toExprNodeType.getMetas());
        PartiqlAst.Type type = $this$toExprNodeType;
        if (type instanceof PartiqlAst.Type.NullType) {
            SqlDataType sqlDataType = SqlDataType.NULL;
            boolean bl = false;
            List<Long> list = CollectionsKt.emptyList();
            MetaContainer metaContainer = metas;
            List<Long> list2 = list;
            SqlDataType sqlDataType2 = sqlDataType;
            dataType = new DataType(sqlDataType2, list2, metaContainer);
        } else if (type instanceof PartiqlAst.Type.MissingType) {
            SqlDataType sqlDataType = SqlDataType.MISSING;
            boolean bl = false;
            List<Long> list = CollectionsKt.emptyList();
            MetaContainer metaContainer = metas;
            List<Long> list3 = list;
            SqlDataType sqlDataType3 = sqlDataType;
            dataType = new DataType(sqlDataType3, list3, metaContainer);
        } else if (type instanceof PartiqlAst.Type.BooleanType) {
            SqlDataType sqlDataType = SqlDataType.BOOLEAN;
            boolean bl = false;
            List<Long> list = CollectionsKt.emptyList();
            MetaContainer metaContainer = metas;
            List<Long> list4 = list;
            SqlDataType sqlDataType4 = sqlDataType;
            dataType = new DataType(sqlDataType4, list4, metaContainer);
        } else if (type instanceof PartiqlAst.Type.IntegerType) {
            SqlDataType sqlDataType = SqlDataType.INTEGER;
            boolean bl = false;
            List<Long> list = CollectionsKt.emptyList();
            MetaContainer metaContainer = metas;
            List<Long> list5 = list;
            SqlDataType sqlDataType5 = sqlDataType;
            dataType = new DataType(sqlDataType5, list5, metaContainer);
        } else if (type instanceof PartiqlAst.Type.SmallintType) {
            SqlDataType sqlDataType = SqlDataType.SMALLINT;
            boolean bl = false;
            List<Long> list = CollectionsKt.emptyList();
            MetaContainer metaContainer = metas;
            List<Long> list6 = list;
            SqlDataType sqlDataType6 = sqlDataType;
            dataType = new DataType(sqlDataType6, list6, metaContainer);
        } else if (type instanceof PartiqlAst.Type.FloatType) {
            LongPrimitive longPrimitive = ((PartiqlAst.Type.FloatType)$this$toExprNodeType).getPrecision();
            dataType = new DataType(SqlDataType.FLOAT, CollectionsKt.listOfNotNull(longPrimitive != null ? Long.valueOf(longPrimitive.getValue()) : null), metas);
        } else if (type instanceof PartiqlAst.Type.RealType) {
            SqlDataType sqlDataType = SqlDataType.REAL;
            boolean bl = false;
            List<Long> list = CollectionsKt.emptyList();
            MetaContainer metaContainer = metas;
            List<Long> list7 = list;
            SqlDataType sqlDataType7 = sqlDataType;
            dataType = new DataType(sqlDataType7, list7, metaContainer);
        } else if (type instanceof PartiqlAst.Type.DoublePrecisionType) {
            SqlDataType sqlDataType = SqlDataType.DOUBLE_PRECISION;
            boolean bl = false;
            List<Long> list = CollectionsKt.emptyList();
            MetaContainer metaContainer = metas;
            List<Long> list8 = list;
            SqlDataType sqlDataType8 = sqlDataType;
            dataType = new DataType(sqlDataType8, list8, metaContainer);
        } else if (type instanceof PartiqlAst.Type.DecimalType) {
            Long[] longArray = new Long[2];
            LongPrimitive longPrimitive = ((PartiqlAst.Type.DecimalType)$this$toExprNodeType).getPrecision();
            longArray[0] = longPrimitive != null ? Long.valueOf(longPrimitive.getValue()) : null;
            LongPrimitive longPrimitive2 = ((PartiqlAst.Type.DecimalType)$this$toExprNodeType).getScale();
            longArray[1] = longPrimitive2 != null ? Long.valueOf(longPrimitive2.getValue()) : null;
            dataType = new DataType(SqlDataType.DECIMAL, CollectionsKt.listOfNotNull(longArray), metas);
        } else if (type instanceof PartiqlAst.Type.NumericType) {
            Long[] longArray = new Long[2];
            LongPrimitive longPrimitive = ((PartiqlAst.Type.NumericType)$this$toExprNodeType).getPrecision();
            longArray[0] = longPrimitive != null ? Long.valueOf(longPrimitive.getValue()) : null;
            LongPrimitive longPrimitive3 = ((PartiqlAst.Type.NumericType)$this$toExprNodeType).getScale();
            longArray[1] = longPrimitive3 != null ? Long.valueOf(longPrimitive3.getValue()) : null;
            dataType = new DataType(SqlDataType.NUMERIC, CollectionsKt.listOfNotNull(longArray), metas);
        } else if (type instanceof PartiqlAst.Type.TimestampType) {
            SqlDataType sqlDataType = SqlDataType.TIMESTAMP;
            boolean bl = false;
            List<Long> list = CollectionsKt.emptyList();
            MetaContainer metaContainer = metas;
            List<Long> list9 = list;
            SqlDataType sqlDataType9 = sqlDataType;
            dataType = new DataType(sqlDataType9, list9, metaContainer);
        } else if (type instanceof PartiqlAst.Type.CharacterType) {
            LongPrimitive longPrimitive = ((PartiqlAst.Type.CharacterType)$this$toExprNodeType).getLength();
            dataType = new DataType(SqlDataType.CHARACTER, CollectionsKt.listOfNotNull(longPrimitive != null ? Long.valueOf(longPrimitive.getValue()) : null), metas);
        } else if (type instanceof PartiqlAst.Type.CharacterVaryingType) {
            LongPrimitive longPrimitive = ((PartiqlAst.Type.CharacterVaryingType)$this$toExprNodeType).getLength();
            dataType = new DataType(SqlDataType.CHARACTER_VARYING, CollectionsKt.listOfNotNull(longPrimitive != null ? Long.valueOf(longPrimitive.getValue()) : null), metas);
        } else if (type instanceof PartiqlAst.Type.StringType) {
            SqlDataType sqlDataType = SqlDataType.STRING;
            boolean bl = false;
            List<Long> list = CollectionsKt.emptyList();
            MetaContainer metaContainer = metas;
            List<Long> list10 = list;
            SqlDataType sqlDataType10 = sqlDataType;
            dataType = new DataType(sqlDataType10, list10, metaContainer);
        } else if (type instanceof PartiqlAst.Type.SymbolType) {
            SqlDataType sqlDataType = SqlDataType.SYMBOL;
            boolean bl = false;
            List<Long> list = CollectionsKt.emptyList();
            MetaContainer metaContainer = metas;
            List<Long> list11 = list;
            SqlDataType sqlDataType11 = sqlDataType;
            dataType = new DataType(sqlDataType11, list11, metaContainer);
        } else if (type instanceof PartiqlAst.Type.BlobType) {
            SqlDataType sqlDataType = SqlDataType.BLOB;
            boolean bl = false;
            List<Long> list = CollectionsKt.emptyList();
            MetaContainer metaContainer = metas;
            List<Long> list12 = list;
            SqlDataType sqlDataType12 = sqlDataType;
            dataType = new DataType(sqlDataType12, list12, metaContainer);
        } else if (type instanceof PartiqlAst.Type.ClobType) {
            SqlDataType sqlDataType = SqlDataType.CLOB;
            boolean bl = false;
            List<Long> list = CollectionsKt.emptyList();
            MetaContainer metaContainer = metas;
            List<Long> list13 = list;
            SqlDataType sqlDataType13 = sqlDataType;
            dataType = new DataType(sqlDataType13, list13, metaContainer);
        } else if (type instanceof PartiqlAst.Type.StructType) {
            SqlDataType sqlDataType = SqlDataType.STRUCT;
            boolean bl = false;
            List<Long> list = CollectionsKt.emptyList();
            MetaContainer metaContainer = metas;
            List<Long> list14 = list;
            SqlDataType sqlDataType14 = sqlDataType;
            dataType = new DataType(sqlDataType14, list14, metaContainer);
        } else if (type instanceof PartiqlAst.Type.TupleType) {
            SqlDataType sqlDataType = SqlDataType.TUPLE;
            boolean bl = false;
            List<Long> list = CollectionsKt.emptyList();
            MetaContainer metaContainer = metas;
            List<Long> list15 = list;
            SqlDataType sqlDataType15 = sqlDataType;
            dataType = new DataType(sqlDataType15, list15, metaContainer);
        } else if (type instanceof PartiqlAst.Type.ListType) {
            SqlDataType sqlDataType = SqlDataType.LIST;
            boolean bl = false;
            List<Long> list = CollectionsKt.emptyList();
            MetaContainer metaContainer = metas;
            List<Long> list16 = list;
            SqlDataType sqlDataType16 = sqlDataType;
            dataType = new DataType(sqlDataType16, list16, metaContainer);
        } else if (type instanceof PartiqlAst.Type.SexpType) {
            SqlDataType sqlDataType = SqlDataType.SEXP;
            boolean bl = false;
            List<Long> list = CollectionsKt.emptyList();
            MetaContainer metaContainer = metas;
            List<Long> list17 = list;
            SqlDataType sqlDataType17 = sqlDataType;
            dataType = new DataType(sqlDataType17, list17, metaContainer);
        } else if (type instanceof PartiqlAst.Type.BagType) {
            SqlDataType sqlDataType = SqlDataType.BAG;
            boolean bl = false;
            List<Long> list = CollectionsKt.emptyList();
            MetaContainer metaContainer = metas;
            List<Long> list18 = list;
            SqlDataType sqlDataType18 = sqlDataType;
            dataType = new DataType(sqlDataType18, list18, metaContainer);
        } else if (type instanceof PartiqlAst.Type.DateType) {
            SqlDataType sqlDataType = SqlDataType.DATE;
            boolean bl = false;
            List<Long> list = CollectionsKt.emptyList();
            MetaContainer metaContainer = metas;
            List<Long> list19 = list;
            SqlDataType sqlDataType19 = sqlDataType;
            dataType = new DataType(sqlDataType19, list19, metaContainer);
        } else if (type instanceof PartiqlAst.Type.TimeType) {
            LongPrimitive longPrimitive = ((PartiqlAst.Type.TimeType)$this$toExprNodeType).getPrecision();
            dataType = new DataType(SqlDataType.TIME, CollectionsKt.listOfNotNull(longPrimitive != null ? Long.valueOf(longPrimitive.getValue()) : null), metas);
        } else if (type instanceof PartiqlAst.Type.TimeWithTimeZoneType) {
            LongPrimitive longPrimitive = ((PartiqlAst.Type.TimeWithTimeZoneType)$this$toExprNodeType).getPrecision();
            dataType = new DataType(SqlDataType.TIME_WITH_TIME_ZONE, CollectionsKt.listOfNotNull(longPrimitive != null ? Long.valueOf(longPrimitive.getValue()) : null), metas);
        } else {
            throw new NoWhenBranchMatchedException();
        }
        return dataType;
    }

    private final SetQuantifier toSetQuantifier(@NotNull PartiqlAst.SetQuantifier $this$toSetQuantifier) {
        SetQuantifier setQuantifier;
        PartiqlAst.SetQuantifier setQuantifier2 = $this$toSetQuantifier;
        if (setQuantifier2 instanceof PartiqlAst.SetQuantifier.All) {
            setQuantifier = SetQuantifier.ALL;
        } else if (setQuantifier2 instanceof PartiqlAst.SetQuantifier.Distinct) {
            setQuantifier = SetQuantifier.DISTINCT;
        } else {
            throw new NoWhenBranchMatchedException();
        }
        return setQuantifier;
    }

    private final ScopeQualifier toScopeQualifier(@NotNull PartiqlAst.ScopeQualifier $this$toScopeQualifier) {
        ScopeQualifier scopeQualifier;
        PartiqlAst.ScopeQualifier scopeQualifier2 = $this$toScopeQualifier;
        if (scopeQualifier2 instanceof PartiqlAst.ScopeQualifier.Unqualified) {
            scopeQualifier = ScopeQualifier.UNQUALIFIED;
        } else if (scopeQualifier2 instanceof PartiqlAst.ScopeQualifier.LocalsFirst) {
            scopeQualifier = ScopeQualifier.LEXICAL;
        } else {
            throw new NoWhenBranchMatchedException();
        }
        return scopeQualifier;
    }

    private final CaseSensitivity toCaseSensitivity(@NotNull PartiqlAst.CaseSensitivity $this$toCaseSensitivity) {
        CaseSensitivity caseSensitivity;
        PartiqlAst.CaseSensitivity caseSensitivity2 = $this$toCaseSensitivity;
        if (caseSensitivity2 instanceof PartiqlAst.CaseSensitivity.CaseSensitive) {
            caseSensitivity = CaseSensitivity.SENSITIVE;
        } else if (caseSensitivity2 instanceof PartiqlAst.CaseSensitivity.CaseInsensitive) {
            caseSensitivity = CaseSensitivity.INSENSITIVE;
        } else {
            throw new NoWhenBranchMatchedException();
        }
        return caseSensitivity;
    }

    private final OnConflict toOnConflictNode(@NotNull PartiqlAst.OnConflict $this$toOnConflictNode) {
        PartiqlAst.ConflictAction conflictAction = $this$toOnConflictNode.getConflictAction();
        if (!(conflictAction instanceof PartiqlAst.ConflictAction.DoNothing)) {
            throw new NoWhenBranchMatchedException();
        }
        return new OnConflict(this.toExprNode($this$toOnConflictNode.getExpr()), ConflictAction.DO_NOTHING);
    }

    private final ExprNode toExprNode(@NotNull PartiqlAst.Statement.Dml $this$toExprNode) {
        PartiqlAst.FromSource fromSource = $this$toExprNode.getFrom();
        FromSource fromSource2 = fromSource != null ? this.toFromSource(fromSource) : null;
        PartiqlAst.Expr expr = $this$toExprNode.getWhere();
        ExprNode where2 = expr != null ? this.toExprNode(expr) : null;
        PartiqlAst.ReturningExpr returningExpr = $this$toExprNode.getReturning();
        ReturningExpr returningExpr2 = returningExpr != null ? this.toReturningExpr(returningExpr) : null;
        PartiqlAst.DmlOpList ops = $this$toExprNode.getOperations();
        DmlOpList dmlOps = this.toDmlOps(ops);
        return new DataManipulation(dmlOps, fromSource2, where2, returningExpr2, StatementToExprNodeKt.toPartiQlMetaContainer($this$toExprNode.getMetas()));
    }

    /*
     * WARNING - void declaration
     */
    private final DmlOpList toDmlOps(@NotNull PartiqlAst.DmlOpList $this$toDmlOps) {
        Collection<DataManipulationOperation> collection;
        void $this$mapTo$iv$iv;
        Iterable $this$map$iv = $this$toDmlOps.getOps();
        boolean $i$f$map = false;
        Iterable iterable = $this$map$iv;
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
        boolean $i$f$mapTo = false;
        for (Object item$iv$iv : $this$mapTo$iv$iv) {
            void it;
            PartiqlAst.DmlOp dmlOp = (PartiqlAst.DmlOp)item$iv$iv;
            collection = destination$iv$iv;
            boolean bl = false;
            DataManipulationOperation dataManipulationOperation = this.toDmlOp((PartiqlAst.DmlOp)it);
            collection.add(dataManipulationOperation);
        }
        collection = (List)destination$iv$iv;
        List list = collection;
        return new DmlOpList(list);
    }

    private final DataManipulationOperation toDmlOp(@NotNull PartiqlAst.DmlOp $this$toDmlOp) {
        DataManipulationOperation dataManipulationOperation;
        PartiqlAst.DmlOp dmlOp = $this$toDmlOp;
        if (dmlOp instanceof PartiqlAst.DmlOp.Insert) {
            dataManipulationOperation = new InsertOp(this.toExprNode(((PartiqlAst.DmlOp.Insert)$this$toDmlOp).getTarget()), this.toExprNode(((PartiqlAst.DmlOp.Insert)$this$toDmlOp).getValues()));
        } else if (dmlOp instanceof PartiqlAst.DmlOp.InsertValue) {
            PartiqlAst.Expr expr = ((PartiqlAst.DmlOp.InsertValue)$this$toDmlOp).getIndex();
            PartiqlAst.OnConflict onConflict = ((PartiqlAst.DmlOp.InsertValue)$this$toDmlOp).getOnConflict();
            dataManipulationOperation = new InsertValueOp(this.toExprNode(((PartiqlAst.DmlOp.InsertValue)$this$toDmlOp).getTarget()), this.toExprNode(((PartiqlAst.DmlOp.InsertValue)$this$toDmlOp).getValue()), expr != null ? this.toExprNode(expr) : null, onConflict != null ? this.toOnConflictNode(onConflict) : null);
        } else if (dmlOp instanceof PartiqlAst.DmlOp.Set) {
            dataManipulationOperation = new AssignmentOp(new Assignment(this.toExprNode(((PartiqlAst.DmlOp.Set)$this$toDmlOp).getAssignment().getTarget()), this.toExprNode(((PartiqlAst.DmlOp.Set)$this$toDmlOp).getAssignment().getValue())));
        } else if (dmlOp instanceof PartiqlAst.DmlOp.Remove) {
            dataManipulationOperation = new RemoveOp(this.toExprNode(((PartiqlAst.DmlOp.Remove)$this$toDmlOp).getTarget()));
        } else if (dmlOp instanceof PartiqlAst.DmlOp.Delete) {
            dataManipulationOperation = AstKt.DeleteOp();
        } else {
            throw new NoWhenBranchMatchedException();
        }
        return dataManipulationOperation;
    }

    /*
     * WARNING - void declaration
     */
    private final ReturningExpr toReturningExpr(@NotNull PartiqlAst.ReturningExpr $this$toReturningExpr) {
        Collection<ReturningElem> collection;
        void $this$mapTo$iv$iv;
        Iterable $this$map$iv = $this$toReturningExpr.getElems();
        boolean $i$f$map = false;
        Iterable iterable = $this$map$iv;
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
        boolean $i$f$mapTo = false;
        for (Object item$iv$iv : $this$mapTo$iv$iv) {
            void it;
            PartiqlAst.ReturningElem returningElem = (PartiqlAst.ReturningElem)item$iv$iv;
            collection = destination$iv$iv;
            boolean bl = false;
            ReturningElem returningElem2 = new ReturningElem(this.toExprNodeReturningMapping(it.getMapping()), this.toColumnComponent(it.getColumn()));
            collection.add(returningElem2);
        }
        collection = (List)destination$iv$iv;
        List list = collection;
        return new ReturningExpr(list);
    }

    private final ColumnComponent toColumnComponent(@NotNull PartiqlAst.ColumnComponent $this$toColumnComponent) {
        ColumnComponent columnComponent;
        MetaContainer metas = StatementToExprNodeKt.toPartiQlMetaContainer($this$toColumnComponent.getMetas());
        PartiqlAst.ColumnComponent columnComponent2 = $this$toColumnComponent;
        if (columnComponent2 instanceof PartiqlAst.ColumnComponent.ReturningColumn) {
            columnComponent = new ReturningColumn(this.toExprNode(((PartiqlAst.ColumnComponent.ReturningColumn)$this$toColumnComponent).getExpr()));
        } else if (columnComponent2 instanceof PartiqlAst.ColumnComponent.ReturningWildcard) {
            columnComponent = new ReturningWildcard(metas);
        } else {
            throw new NoWhenBranchMatchedException();
        }
        return columnComponent;
    }

    private final ReturningMapping toExprNodeReturningMapping(@NotNull PartiqlAst.ReturningMapping $this$toExprNodeReturningMapping) {
        ReturningMapping returningMapping;
        PartiqlAst.ReturningMapping returningMapping2 = $this$toExprNodeReturningMapping;
        if (returningMapping2 instanceof PartiqlAst.ReturningMapping.ModifiedOld) {
            returningMapping = ReturningMapping.MODIFIED_OLD;
        } else if (returningMapping2 instanceof PartiqlAst.ReturningMapping.ModifiedNew) {
            returningMapping = ReturningMapping.MODIFIED_NEW;
        } else if (returningMapping2 instanceof PartiqlAst.ReturningMapping.AllOld) {
            returningMapping = ReturningMapping.ALL_OLD;
        } else if (returningMapping2 instanceof PartiqlAst.ReturningMapping.AllNew) {
            returningMapping = ReturningMapping.ALL_NEW;
        } else {
            throw new NoWhenBranchMatchedException();
        }
        return returningMapping;
    }

    /*
     * WARNING - void declaration
     */
    private final ExprNode toExprNode(@NotNull PartiqlAst.Statement.Ddl $this$toExprNode) {
        ExprNode exprNode;
        PartiqlAst.DdlOp op = $this$toExprNode.getOp();
        MetaContainer metas = StatementToExprNodeKt.toPartiQlMetaContainer($this$toExprNode.getMetas());
        PartiqlAst.DdlOp ddlOp = op;
        if (ddlOp instanceof PartiqlAst.DdlOp.CreateTable) {
            exprNode = new CreateTable(((PartiqlAst.DdlOp.CreateTable)op).getTableName().getText(), metas);
        } else if (ddlOp instanceof PartiqlAst.DdlOp.DropTable) {
            exprNode = new DropTable(((PartiqlAst.DdlOp.DropTable)op).getTableName().getName().getText(), metas);
        } else if (ddlOp instanceof PartiqlAst.DdlOp.CreateIndex) {
            Collection<ExprNode> collection;
            void $this$mapTo$iv$iv;
            void $this$map$iv;
            Iterable iterable = ((PartiqlAst.DdlOp.CreateIndex)op).getFields();
            String string = ((PartiqlAst.DdlOp.CreateIndex)op).getIndexName().getName().getText();
            boolean $i$f$map = false;
            void var7_8 = $this$map$iv;
            Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
            boolean $i$f$mapTo = false;
            for (Object item$iv$iv : $this$mapTo$iv$iv) {
                void it;
                PartiqlAst.Expr expr = (PartiqlAst.Expr)item$iv$iv;
                collection = destination$iv$iv;
                boolean bl = false;
                ExprNode exprNode2 = this.toExprNode((PartiqlAst.Expr)it);
                collection.add(exprNode2);
            }
            collection = (List)destination$iv$iv;
            MetaContainer metaContainer = metas;
            List list = collection;
            String string2 = string;
            exprNode = new CreateIndex(string2, list, metaContainer);
        } else if (ddlOp instanceof PartiqlAst.DdlOp.DropIndex) {
            exprNode = new DropIndex(((PartiqlAst.DdlOp.DropIndex)op).getTable().getName().getText(), new VariableReference(((PartiqlAst.DdlOp.DropIndex)op).getKeys().getName().getText(), this.toCaseSensitivity(((PartiqlAst.DdlOp.DropIndex)op).getKeys().getCase()), ScopeQualifier.UNQUALIFIED, MetaKt.getEmptyMetaContainer()), metas);
        } else {
            throw new NoWhenBranchMatchedException();
        }
        return exprNode;
    }

    private final ExprNode toExprNode(@NotNull PartiqlAst.Statement.Exec $this$toExprNode) {
        return new Exec(this.toSymbolicName($this$toExprNode.getProcedureName()), this.toExprNodeList($this$toExprNode.getArgs()), StatementToExprNodeKt.toPartiQlMetaContainer($this$toExprNode.getMetas()));
    }

    @NotNull
    public final IonSystem getIon() {
        return this.ion;
    }

    public StatementTransformer(@NotNull IonSystem ion) {
        Intrinsics.checkParameterIsNotNull(ion, "ion");
        this.ion = ion;
    }
}

