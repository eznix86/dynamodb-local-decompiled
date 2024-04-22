/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package org.partiql.lang.ast.passes;

import java.util.List;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Unit;
import kotlin.collections.ArraysKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.partiql.lang.ast.Assignment;
import org.partiql.lang.ast.AssignmentOp;
import org.partiql.lang.ast.AstNode;
import org.partiql.lang.ast.CallAgg;
import org.partiql.lang.ast.ColumnComponent;
import org.partiql.lang.ast.ConflictAction;
import org.partiql.lang.ast.CreateIndex;
import org.partiql.lang.ast.CreateTable;
import org.partiql.lang.ast.DataManipulation;
import org.partiql.lang.ast.DataManipulationOperation;
import org.partiql.lang.ast.DataType;
import org.partiql.lang.ast.DateTimeType;
import org.partiql.lang.ast.DeleteOp;
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
import org.partiql.lang.ast.InsertOp;
import org.partiql.lang.ast.InsertValueOp;
import org.partiql.lang.ast.LetSource;
import org.partiql.lang.ast.Literal;
import org.partiql.lang.ast.LiteralMissing;
import org.partiql.lang.ast.NAry;
import org.partiql.lang.ast.OnConflict;
import org.partiql.lang.ast.OrderBy;
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
import org.partiql.lang.ast.ReturningWildcard;
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
import org.partiql.lang.ast.SimpleCase;
import org.partiql.lang.ast.SimpleCaseWhen;
import org.partiql.lang.ast.SortSpec;
import org.partiql.lang.ast.Struct;
import org.partiql.lang.ast.StructField;
import org.partiql.lang.ast.Typed;
import org.partiql.lang.ast.VariableReference;
import org.partiql.lang.ast.passes.AstVisitor;
import org.partiql.lang.util.ThreadInterruptUtilsKt;
import org.partiql.lang.util.WhenAsExpressionHelper;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000V\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0016\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u000e\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bJ\u0010\u0010\t\u001a\u00020\u00062\u0006\u0010\n\u001a\u00020\u000bH\u0002J\u0010\u0010\f\u001a\u00020\u00062\u0006\u0010\r\u001a\u00020\u000eH\u0002J%\u0010\u000f\u001a\u00020\u00062\u0016\u0010\u0010\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010\b0\u0011\"\u0004\u0018\u00010\bH\u0014\u00a2\u0006\u0002\u0010\u0012J\u0010\u0010\u0013\u001a\u00020\u00062\u0006\u0010\u0014\u001a\u00020\u0015H\u0002J\u0012\u0010\u0016\u001a\u00020\u00062\b\u0010\u0017\u001a\u0004\u0018\u00010\u0018H\u0002J\u0010\u0010\u0019\u001a\u00020\u00062\u0006\u0010\u001a\u001a\u00020\u001bH\u0002J\u0010\u0010\u001c\u001a\u00020\u00062\u0006\u0010\u001d\u001a\u00020\u001eH\u0002R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001f"}, d2={"Lorg/partiql/lang/ast/passes/AstWalker;", "", "visitor", "Lorg/partiql/lang/ast/passes/AstVisitor;", "(Lorg/partiql/lang/ast/passes/AstVisitor;)V", "walk", "", "exprNode", "Lorg/partiql/lang/ast/ExprNode;", "walkDmlOperation", "dmlOperation", "Lorg/partiql/lang/ast/DataManipulationOperation;", "walkDmlOperations", "dmlOperations", "Lorg/partiql/lang/ast/DmlOpList;", "walkExprNode", "exprs", "", "([Lorg/partiql/lang/ast/ExprNode;)V", "walkFromSource", "fromSource", "Lorg/partiql/lang/ast/FromSource;", "walkOnConflict", "onConflict", "Lorg/partiql/lang/ast/OnConflict;", "walkPath", "expr", "Lorg/partiql/lang/ast/Path;", "walkSelectProjection", "projection", "Lorg/partiql/lang/ast/SelectProjection;", "lang"})
public class AstWalker {
    private final AstVisitor visitor;

    public final void walk(@NotNull ExprNode exprNode) {
        Intrinsics.checkParameterIsNotNull(exprNode, "exprNode");
        this.walkExprNode(exprNode);
    }

    /*
     * WARNING - void declaration
     */
    protected void walkExprNode(@NotNull ExprNode ... exprs) {
        Intrinsics.checkParameterIsNotNull(exprs, "exprs");
        Iterable $this$forEach$iv = ArraysKt.filterNotNull(exprs);
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            DmlOpList projection;
            Iterable<ExprNode> items;
            AstNode astNode2;
            List<SearchedCaseWhen> valueExpr;
            ExprNode exp;
            ExprNode funcExpr;
            Iterable<AstNode> $this$forEach$iv2;
            Iterable<ExprNode> args2;
            WhenAsExpressionHelper whenAsExpressionHelper;
            boolean $i$f$case;
            ExprNode expr = (ExprNode)element$iv;
            boolean bl = false;
            ThreadInterruptUtilsKt.checkThreadInterrupted();
            this.visitor.visitExprNode(expr);
            ExprNode exprNode = expr;
            if (exprNode instanceof Literal || exprNode instanceof LiteralMissing || exprNode instanceof VariableReference || exprNode instanceof Parameter) {
                $i$f$case = false;
                boolean bl2 = false;
                whenAsExpressionHelper = WhenAsExpressionHelper.Companion.getInstance();
            } else if (exprNode instanceof NAry) {
                $i$f$case = false;
                boolean bl3 = false;
                NAry nAry = (NAry)expr;
                args2 = nAry.component2();
                $this$forEach$iv2 = args2;
                boolean $i$f$forEach2 = false;
                for (AstNode element$iv2 : $this$forEach$iv2) {
                    ExprNode it = (ExprNode)element$iv2;
                    boolean bl4 = false;
                    this.walkExprNode(it);
                }
                whenAsExpressionHelper = WhenAsExpressionHelper.Companion.getInstance();
            } else if (exprNode instanceof CallAgg) {
                $i$f$case = false;
                boolean bl5 = false;
                $this$forEach$iv2 = (CallAgg)expr;
                args2 = ((CallAgg)$this$forEach$iv2).component1();
                ExprNode arg = ((CallAgg)$this$forEach$iv2).component3();
                this.walkExprNode(funcExpr);
                this.walkExprNode(arg);
                whenAsExpressionHelper = WhenAsExpressionHelper.Companion.getInstance();
            } else if (exprNode instanceof Typed) {
                $i$f$case = false;
                boolean bl6 = false;
                $this$forEach$iv2 = (Typed)expr;
                funcExpr = ((Typed)$this$forEach$iv2).component2();
                DataType sqlDataType = ((Typed)$this$forEach$iv2).component3();
                this.walkExprNode(exp);
                this.visitor.visitDataType(sqlDataType);
                whenAsExpressionHelper = WhenAsExpressionHelper.Companion.getInstance();
            } else if (exprNode instanceof Path) {
                $i$f$case = false;
                boolean bl7 = false;
                this.walkPath((Path)expr);
                whenAsExpressionHelper = WhenAsExpressionHelper.Companion.getInstance();
            } else if (exprNode instanceof SimpleCase) {
                void branches;
                $i$f$case = false;
                boolean bl8 = false;
                $this$forEach$iv2 = (SimpleCase)expr;
                exp = ((SimpleCase)$this$forEach$iv2).component1();
                List<SimpleCaseWhen> sqlDataType = ((SimpleCase)$this$forEach$iv2).component2();
                ExprNode elseExpr = ((SimpleCase)$this$forEach$iv2).component3();
                this.walkExprNode(new ExprNode[]{valueExpr});
                $this$forEach$iv2 = (Iterable)branches;
                boolean $i$f$forEach3 = false;
                for (AstNode element$iv3 : $this$forEach$iv2) {
                    void branchValueExpr;
                    SimpleCaseWhen it = (SimpleCaseWhen)element$iv3;
                    boolean bl9 = false;
                    SimpleCaseWhen simpleCaseWhen = it;
                    ExprNode exprNode2 = simpleCaseWhen.component1();
                    ExprNode thenExpr = simpleCaseWhen.component2();
                    this.walkExprNode(new ExprNode[]{branchValueExpr, thenExpr});
                }
                this.walkExprNode(elseExpr);
                whenAsExpressionHelper = WhenAsExpressionHelper.Companion.getInstance();
            } else if (exprNode instanceof SearchedCase) {
                void branches;
                $i$f$case = false;
                boolean bl10 = false;
                $this$forEach$iv2 = (SearchedCase)expr;
                valueExpr = ((SearchedCase)$this$forEach$iv2).component1();
                ExprNode elseExpr = ((SearchedCase)$this$forEach$iv2).component2();
                $this$forEach$iv2 = (Iterable)branches;
                boolean $i$f$forEach4 = false;
                for (AstNode element$iv4 : $this$forEach$iv2) {
                    void conditionExpr;
                    SearchedCaseWhen it = (SearchedCaseWhen)element$iv4;
                    boolean astNode2 = false;
                    SearchedCaseWhen bl9 = it;
                    ExprNode exprNode3 = bl9.component1();
                    ExprNode thenExpr = bl9.component2();
                    this.walkExprNode(new ExprNode[]{conditionExpr, thenExpr});
                }
                this.walkExprNode(elseExpr);
                whenAsExpressionHelper = WhenAsExpressionHelper.Companion.getInstance();
            } else if (exprNode instanceof Struct) {
                $i$f$case = false;
                boolean bl11 = false;
                $this$forEach$iv2 = (Struct)expr;
                List<StructField> fields = ((Struct)$this$forEach$iv2).component1();
                $this$forEach$iv2 = fields;
                boolean $i$f$forEach5 = false;
                for (AstNode element$iv5 : $this$forEach$iv2) {
                    void nameExpr;
                    StructField it = (StructField)element$iv5;
                    boolean bl12 = false;
                    astNode2 = it;
                    ExprNode bl9 = ((StructField)astNode2).component1();
                    ExprNode valueExpr2 = ((StructField)astNode2).component2();
                    this.walkExprNode(new ExprNode[]{nameExpr, valueExpr2});
                }
                whenAsExpressionHelper = WhenAsExpressionHelper.Companion.getInstance();
            } else if (exprNode instanceof Seq) {
                $i$f$case = false;
                boolean bl13 = false;
                $this$forEach$iv2 = (Seq)expr;
                items = ((Seq)$this$forEach$iv2).component2();
                $this$forEach$iv2 = items;
                boolean $i$f$forEach6 = false;
                for (AstNode element$iv6 : $this$forEach$iv2) {
                    ExprNode it2 = (ExprNode)element$iv6;
                    boolean bl14 = false;
                    this.walkExprNode(it2);
                }
                whenAsExpressionHelper = WhenAsExpressionHelper.Companion.getInstance();
            } else if (exprNode instanceof Select) {
                Unit unit;
                void orderBy;
                void having2;
                Unit unit2;
                Iterable $this$forEach$iv3;
                Iterable<AstNode> it;
                boolean thenExpr;
                void groupBy2;
                void where2;
                void from2;
                $i$f$case = false;
                boolean bl15 = false;
                $this$forEach$iv2 = (Select)expr;
                items = ((Select)$this$forEach$iv2).component2();
                FromSource $i$f$forEach6 = ((Select)$this$forEach$iv2).component3();
                LetSource $i$f$forEach4 = ((Select)$this$forEach$iv2).component4();
                ExprNode element$iv6 = ((Select)$this$forEach$iv2).component5();
                GroupBy it2 = ((Select)$this$forEach$iv2).component6();
                ExprNode bl14 = ((Select)$this$forEach$iv2).component7();
                astNode2 = ((Select)$this$forEach$iv2).component8();
                ExprNode limit2 = ((Select)$this$forEach$iv2).component9();
                this.walkSelectProjection((SelectProjection)((Object)projection));
                this.walkFromSource((FromSource)from2);
                this.walkExprNode(new ExprNode[]{where2});
                void v1 = groupBy2;
                if (v1 != null) {
                    $this$forEach$iv2 = v1;
                    boolean valueExpr2 = false;
                    thenExpr = false;
                    it = $this$forEach$iv2;
                    boolean bl16 = false;
                    Iterable<AstNode> iterable = it;
                    List<GroupByItem> groupByItems = ((GroupBy)iterable).component2();
                    $this$forEach$iv3 = groupByItems;
                    boolean $i$f$forEach7 = false;
                    for (Object element$iv7 : $this$forEach$iv3) {
                        GroupByItem gbi = (GroupByItem)element$iv7;
                        boolean bl17 = false;
                        GroupByItem groupByItem = gbi;
                        ExprNode groupExpr = groupByItem.component1();
                        this.walkExprNode(groupExpr);
                    }
                    unit2 = Unit.INSTANCE;
                } else {
                    unit2 = null;
                }
                this.walkExprNode(new ExprNode[]{having2});
                void v3 = orderBy;
                if (v3 != null) {
                    $this$forEach$iv2 = v3;
                    boolean valueExpr2 = false;
                    thenExpr = false;
                    it = $this$forEach$iv2;
                    boolean bl18 = false;
                    $this$forEach$iv3 = ((OrderBy)it).getSortSpecItems();
                    boolean $i$f$forEach8 = false;
                    for (Object element$iv8 : $this$forEach$iv3) {
                        SortSpec ssi = (SortSpec)element$iv8;
                        boolean bl19 = false;
                        this.walkExprNode(ssi.getExpr());
                    }
                    unit = Unit.INSTANCE;
                } else {
                    unit = null;
                }
                this.walkExprNode(limit2);
                whenAsExpressionHelper = WhenAsExpressionHelper.Companion.getInstance();
            } else if (exprNode instanceof DataManipulation) {
                Unit unit;
                void where3;
                void dmlOperation;
                $i$f$case = false;
                boolean bl20 = false;
                $this$forEach$iv2 = (DataManipulation)expr;
                projection = ((DataManipulation)$this$forEach$iv2).component1();
                FromSource from2 = ((DataManipulation)$this$forEach$iv2).component2();
                ExprNode fromLet2 = ((DataManipulation)$this$forEach$iv2).component3();
                ReturningExpr returning2 = ((DataManipulation)$this$forEach$iv2).component4();
                this.walkDmlOperations((DmlOpList)dmlOperation);
                if (from2 != null) {
                    this.walkFromSource(from2);
                }
                this.walkExprNode(new ExprNode[]{where3});
                ReturningExpr returningExpr = returning2;
                if (returningExpr != null) {
                    $this$forEach$iv2 = returningExpr;
                    boolean bl21 = false;
                    boolean bl22 = false;
                    Iterable<AstNode> it = $this$forEach$iv2;
                    boolean bl23 = false;
                    Iterable $this$forEach$iv4 = ((ReturningExpr)it).getReturningElems();
                    boolean $i$f$forEach9 = false;
                    for (Object element$iv9 : $this$forEach$iv4) {
                        boolean $i$f$case2;
                        ReturningElem re = (ReturningElem)element$iv9;
                        boolean bl24 = false;
                        ColumnComponent columnComponent = re.getColumnComponent();
                        if (columnComponent instanceof ReturningColumn) {
                            $i$f$case2 = false;
                            boolean bl25 = false;
                            this.walkExprNode(((ReturningColumn)re.getColumnComponent()).getColumn());
                            WhenAsExpressionHelper.Companion.getInstance();
                            continue;
                        }
                        if (!(columnComponent instanceof ReturningWildcard)) continue;
                        $i$f$case2 = false;
                        boolean bl26 = false;
                        WhenAsExpressionHelper.Companion.getInstance();
                    }
                    unit = Unit.INSTANCE;
                } else {
                    unit = null;
                }
                whenAsExpressionHelper = WhenAsExpressionHelper.Companion.getInstance();
            } else if (exprNode instanceof CreateIndex) {
                $i$f$case = false;
                boolean bl27 = false;
                $this$forEach$iv2 = (CreateIndex)expr;
                List<ExprNode> keys2 = ((CreateIndex)$this$forEach$iv2).component2();
                for (ExprNode key : keys2) {
                    this.walkExprNode(key);
                }
                whenAsExpressionHelper = WhenAsExpressionHelper.Companion.getInstance();
            } else if (exprNode instanceof CreateTable || exprNode instanceof DropTable || exprNode instanceof DropIndex || exprNode instanceof Exec || exprNode instanceof DateTimeType) {
                $i$f$case = false;
                boolean bl28 = false;
                whenAsExpressionHelper = WhenAsExpressionHelper.Companion.getInstance();
            } else {
                throw new NoWhenBranchMatchedException();
            }
            whenAsExpressionHelper.toUnit();
        }
    }

    /*
     * WARNING - void declaration
     */
    private final void walkPath(Path expr) {
        void root;
        Path path = expr;
        ExprNode exprNode = path.component1();
        List<PathComponent> components = path.component2();
        this.walkExprNode(new ExprNode[]{root});
        Iterable $this$forEach$iv = components;
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            WhenAsExpressionHelper whenAsExpressionHelper;
            boolean $i$f$case;
            PathComponent it = (PathComponent)element$iv;
            boolean bl = false;
            this.visitor.visitPathComponent(it);
            PathComponent pathComponent = it;
            if (pathComponent instanceof PathComponentUnpivot || pathComponent instanceof PathComponentWildcard) {
                $i$f$case = false;
                boolean bl2 = false;
                whenAsExpressionHelper = WhenAsExpressionHelper.Companion.getInstance();
            } else if (pathComponent instanceof PathComponentExpr) {
                $i$f$case = false;
                boolean bl3 = false;
                PathComponentExpr pathComponentExpr = (PathComponentExpr)it;
                ExprNode exp = pathComponentExpr.component1();
                this.walkExprNode(exp);
                whenAsExpressionHelper = WhenAsExpressionHelper.Companion.getInstance();
            } else {
                throw new NoWhenBranchMatchedException();
            }
            whenAsExpressionHelper.toUnit();
        }
    }

    /*
     * WARNING - void declaration
     */
    private final void walkFromSource(FromSource fromSource) {
        WhenAsExpressionHelper whenAsExpressionHelper;
        this.visitor.visitFromSource(fromSource);
        FromSource fromSource2 = fromSource;
        if (fromSource2 instanceof FromSourceExpr) {
            boolean $i$f$case = false;
            boolean bl = false;
            FromSourceExpr fromSourceExpr = (FromSourceExpr)fromSource;
            ExprNode exp = fromSourceExpr.component1();
            this.walkExprNode(exp);
            whenAsExpressionHelper = WhenAsExpressionHelper.Companion.getInstance();
        } else if (fromSource2 instanceof FromSourceUnpivot) {
            boolean $i$f$case = false;
            boolean bl = false;
            FromSourceUnpivot fromSourceUnpivot = (FromSourceUnpivot)fromSource;
            ExprNode exp = fromSourceUnpivot.component1();
            this.walkExprNode(exp);
            whenAsExpressionHelper = WhenAsExpressionHelper.Companion.getInstance();
        } else if (fromSource2 instanceof FromSourceJoin) {
            void rightRef;
            void leftRef;
            boolean $i$f$case = false;
            boolean bl = false;
            FromSourceJoin fromSourceJoin = (FromSourceJoin)fromSource;
            FromSource exp = fromSourceJoin.component2();
            FromSource fromSource3 = fromSourceJoin.component3();
            ExprNode condition = fromSourceJoin.component4();
            this.walkFromSource((FromSource)leftRef);
            this.walkFromSource((FromSource)rightRef);
            this.walkExprNode(condition);
            whenAsExpressionHelper = WhenAsExpressionHelper.Companion.getInstance();
        } else {
            throw new NoWhenBranchMatchedException();
        }
        whenAsExpressionHelper.toUnit();
    }

    /*
     * WARNING - void declaration
     */
    private final void walkSelectProjection(SelectProjection projection) {
        WhenAsExpressionHelper whenAsExpressionHelper;
        this.visitor.visitSelectProjection(projection);
        SelectProjection selectProjection = projection;
        if (selectProjection instanceof SelectProjectionValue) {
            boolean $i$f$case = false;
            boolean bl = false;
            SelectProjectionValue selectProjectionValue = (SelectProjectionValue)projection;
            ExprNode valueExpr = selectProjectionValue.component1();
            this.walkExprNode(valueExpr);
            whenAsExpressionHelper = WhenAsExpressionHelper.Companion.getInstance();
        } else if (selectProjection instanceof SelectProjectionPivot) {
            void asExpr;
            boolean $i$f$case = false;
            boolean bl = false;
            SelectProjectionPivot selectProjectionPivot = (SelectProjectionPivot)projection;
            ExprNode valueExpr = selectProjectionPivot.component1();
            ExprNode atExpr = selectProjectionPivot.component2();
            this.walkExprNode(new ExprNode[]{asExpr, atExpr});
            whenAsExpressionHelper = WhenAsExpressionHelper.Companion.getInstance();
        } else if (selectProjection instanceof SelectProjectionList) {
            boolean $i$f$case = false;
            boolean bl = false;
            SelectProjectionList selectProjectionList = (SelectProjectionList)projection;
            List<SelectListItem> items = selectProjectionList.component1();
            Iterable $this$forEach$iv = items;
            boolean $i$f$forEach = false;
            for (Object element$iv : $this$forEach$iv) {
                WhenAsExpressionHelper whenAsExpressionHelper2;
                boolean $i$f$case2;
                SelectListItem it = (SelectListItem)element$iv;
                boolean bl2 = false;
                this.visitor.visitSelectListItem(it);
                SelectListItem selectListItem = it;
                if (selectListItem instanceof SelectListItemStar) {
                    $i$f$case2 = false;
                    boolean bl3 = false;
                    whenAsExpressionHelper2 = WhenAsExpressionHelper.Companion.getInstance();
                } else if (selectListItem instanceof SelectListItemExpr) {
                    $i$f$case2 = false;
                    boolean bl4 = false;
                    this.walkExprNode(((SelectListItemExpr)it).getExpr());
                    whenAsExpressionHelper2 = WhenAsExpressionHelper.Companion.getInstance();
                } else if (selectListItem instanceof SelectListItemProjectAll) {
                    $i$f$case2 = false;
                    boolean bl5 = false;
                    this.walkExprNode(((SelectListItemProjectAll)it).getExpr());
                    whenAsExpressionHelper2 = WhenAsExpressionHelper.Companion.getInstance();
                } else {
                    throw new NoWhenBranchMatchedException();
                }
                whenAsExpressionHelper2.toUnit();
            }
            whenAsExpressionHelper = WhenAsExpressionHelper.Companion.getInstance();
        } else {
            throw new NoWhenBranchMatchedException();
        }
        whenAsExpressionHelper.toUnit();
    }

    private final void walkDmlOperations(DmlOpList dmlOperations) {
        Iterable $this$forEach$iv = dmlOperations.getOps();
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            DataManipulationOperation it = (DataManipulationOperation)element$iv;
            boolean bl = false;
            this.walkDmlOperation(it);
        }
    }

    /*
     * WARNING - void declaration
     */
    private final void walkDmlOperation(DataManipulationOperation dmlOperation) {
        WhenAsExpressionHelper whenAsExpressionHelper;
        DataManipulationOperation dataManipulationOperation = dmlOperation;
        if (dataManipulationOperation instanceof InsertOp) {
            void lValue;
            boolean $i$f$case = false;
            boolean bl = false;
            InsertOp insertOp = (InsertOp)dmlOperation;
            ExprNode exprNode = insertOp.component1();
            ExprNode values2 = insertOp.component2();
            this.walkExprNode(new ExprNode[]{lValue, values2});
            whenAsExpressionHelper = WhenAsExpressionHelper.Companion.getInstance();
        } else if (dataManipulationOperation instanceof InsertValueOp) {
            void position;
            void value;
            void lvalue;
            boolean $i$f$case = false;
            boolean bl = false;
            InsertValueOp insertValueOp = (InsertValueOp)dmlOperation;
            ExprNode lValue = insertValueOp.component1();
            ExprNode values2 = insertValueOp.component2();
            ExprNode exprNode = insertValueOp.component3();
            OnConflict onConflict = insertValueOp.component4();
            this.walkExprNode(new ExprNode[]{lvalue, value, position});
            this.walkOnConflict(onConflict);
            whenAsExpressionHelper = WhenAsExpressionHelper.Companion.getInstance();
        } else if (dataManipulationOperation instanceof AssignmentOp) {
            boolean $i$f$case = false;
            boolean bl = false;
            AssignmentOp assignmentOp = (AssignmentOp)dmlOperation;
            Assignment assignment = assignmentOp.component1();
            this.walkExprNode(assignment.getLvalue());
            this.walkExprNode(assignment.getRvalue());
            whenAsExpressionHelper = WhenAsExpressionHelper.Companion.getInstance();
        } else if (dataManipulationOperation instanceof RemoveOp) {
            boolean $i$f$case = false;
            boolean bl = false;
            RemoveOp removeOp = (RemoveOp)dmlOperation;
            ExprNode lvalue = removeOp.component1();
            this.walkExprNode(lvalue);
            whenAsExpressionHelper = WhenAsExpressionHelper.Companion.getInstance();
        } else if (dataManipulationOperation instanceof DeleteOp) {
            boolean $i$f$case = false;
            boolean bl = false;
            whenAsExpressionHelper = WhenAsExpressionHelper.Companion.getInstance();
        } else {
            throw new NoWhenBranchMatchedException();
        }
        whenAsExpressionHelper.toUnit();
    }

    /*
     * WARNING - void declaration
     */
    private final void walkOnConflict(OnConflict onConflict) {
        if (onConflict != null) {
            void condition;
            this.visitor.visitOnConflict(onConflict);
            OnConflict onConflict2 = onConflict;
            ExprNode exprNode = onConflict2.component1();
            ConflictAction conflictAction = onConflict2.component2();
            this.walkExprNode(new ExprNode[]{condition});
            switch (conflictAction) {
                default: 
            }
        }
    }

    public AstWalker(@NotNull AstVisitor visitor2) {
        Intrinsics.checkParameterIsNotNull(visitor2, "visitor");
        this.visitor = visitor2;
    }
}

