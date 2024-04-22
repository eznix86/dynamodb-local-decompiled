/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package org.partiql.lang.ast.passes;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.Deprecated;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.TypeCastException;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.partiql.lang.ast.Assignment;
import org.partiql.lang.ast.AssignmentOp;
import org.partiql.lang.ast.AstNode;
import org.partiql.lang.ast.CallAgg;
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
import org.partiql.lang.ast.FromSourceLet;
import org.partiql.lang.ast.FromSourceUnpivot;
import org.partiql.lang.ast.GroupBy;
import org.partiql.lang.ast.GroupByItem;
import org.partiql.lang.ast.GroupingStrategy;
import org.partiql.lang.ast.HasMetas;
import org.partiql.lang.ast.InsertOp;
import org.partiql.lang.ast.InsertValueOp;
import org.partiql.lang.ast.LetBinding;
import org.partiql.lang.ast.LetSource;
import org.partiql.lang.ast.LetVariables;
import org.partiql.lang.ast.Literal;
import org.partiql.lang.ast.LiteralMissing;
import org.partiql.lang.ast.MetaContainer;
import org.partiql.lang.ast.NAry;
import org.partiql.lang.ast.NAryOp;
import org.partiql.lang.ast.OnConflict;
import org.partiql.lang.ast.OrderBy;
import org.partiql.lang.ast.Parameter;
import org.partiql.lang.ast.Path;
import org.partiql.lang.ast.PathComponent;
import org.partiql.lang.ast.PathComponentExpr;
import org.partiql.lang.ast.PathComponentUnpivot;
import org.partiql.lang.ast.PathComponentWildcard;
import org.partiql.lang.ast.RemoveOp;
import org.partiql.lang.ast.ReturningElem;
import org.partiql.lang.ast.ReturningExpr;
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
import org.partiql.lang.ast.SimpleCase;
import org.partiql.lang.ast.SimpleCaseWhen;
import org.partiql.lang.ast.SortSpec;
import org.partiql.lang.ast.Struct;
import org.partiql.lang.ast.StructField;
import org.partiql.lang.ast.SymbolicName;
import org.partiql.lang.ast.Typed;
import org.partiql.lang.ast.VariableReference;
import org.partiql.lang.ast.passes.AstRewriter;
import org.partiql.lang.util.ThreadInterruptUtilsKt;

@Deprecated(message="New rewriters should implement PIG's VisitorTransformBase instead")
@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000\u00c4\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0017\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0004H\u0016J\u0010\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u0007H\u0014J\u0010\u0010\t\u001a\u00020\n2\u0006\u0010\u0005\u001a\u00020\nH\u0016J\u0010\u0010\u000b\u001a\u00020\f2\u0006\u0010\u0005\u001a\u00020\rH\u0016J\u0010\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0005\u001a\u00020\u000fH\u0016J\u0010\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0005\u001a\u00020\u0011H\u0016J\u0010\u0010\u0012\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0004H\u0016J\u0010\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0005\u001a\u00020\u0014H\u0016J\u0010\u0010\u0015\u001a\u00020\u00142\u0006\u0010\u0005\u001a\u00020\u0016H\u0016J\b\u0010\u0017\u001a\u00020\u0014H\u0016J\u0010\u0010\u0018\u001a\u00020\u00142\u0006\u0010\u0005\u001a\u00020\u0019H\u0016J\u0010\u0010\u001a\u001a\u00020\u00142\u0006\u0010\u0005\u001a\u00020\u001bH\u0016J\u0010\u0010\u001c\u001a\u00020\u00142\u0006\u0010\u0005\u001a\u00020\u001dH\u0016J\u0010\u0010\u001e\u001a\u00020\u001f2\u0006\u0010\u0005\u001a\u00020\u001fH\u0016J\u0010\u0010 \u001a\u00020\f2\u0006\u0010\u0005\u001a\u00020\fH\u0016J\u0010\u0010!\u001a\u00020\"2\u0006\u0010#\u001a\u00020\"H\u0016J\u0010\u0010$\u001a\u00020%2\u0006\u0010\u0005\u001a\u00020%H\u0016J\u0010\u0010&\u001a\u00020'2\u0006\u0010\u0005\u001a\u00020'H\u0016J\u0010\u0010(\u001a\u00020)2\u0006\u0010\u0005\u001a\u00020)H\u0016J\u0010\u0010*\u001a\u00020+2\u0006\u0010\u0005\u001a\u00020+H\u0016J\u0010\u0010,\u001a\u00020\f2\u0006\u0010\u0005\u001a\u00020\fH\u0016J\u0010\u0010-\u001a\u00020.2\u0006\u0010/\u001a\u00020.H\u0016J\u0010\u00100\u001a\u0002012\u0006\u0010/\u001a\u000202H\u0016J\u0010\u00103\u001a\u00020.2\u0006\u0010/\u001a\u000204H\u0016J\u0010\u00105\u001a\u0002012\u0006\u00106\u001a\u000201H\u0016J\u0010\u00107\u001a\u0002012\u0006\u0010/\u001a\u000208H\u0016J\u0010\u00109\u001a\u00020\f2\u0006\u0010:\u001a\u00020\fH\u0016J\u0010\u0010;\u001a\u00020<2\u0006\u0010=\u001a\u00020<H\u0016J\u0010\u0010>\u001a\u00020?2\u0006\u0010@\u001a\u00020?H\u0016J\u0010\u0010A\u001a\u00020B2\u0006\u0010C\u001a\u00020BH\u0016J\u0010\u0010D\u001a\u00020E2\u0006\u0010F\u001a\u00020EH\u0016J\u0010\u0010G\u001a\u00020H2\u0006\u0010I\u001a\u00020HH\u0016J\u0010\u0010J\u001a\u00020\f2\u0006\u0010\u0005\u001a\u00020KH\u0016J\u0010\u0010L\u001a\u00020\f2\u0006\u0010\u0005\u001a\u00020MH\u0016J\u0010\u0010N\u001a\u00020O2\u0006\u0010P\u001a\u00020QH\u0016J\u0010\u0010R\u001a\u00020\f2\u0006\u0010\u0005\u001a\u00020SH\u0016J\u000e\u0010T\u001a\u00020U2\u0006\u0010\u0005\u001a\u00020UJ\u0010\u0010V\u001a\u00020W2\u0006\u0010X\u001a\u00020WH\u0016J\u0010\u0010Y\u001a\u00020Z2\u0006\u0010\u0005\u001a\u00020ZH\u0016J\u0010\u0010[\u001a\u00020\f2\u0006\u0010\u0005\u001a\u00020\\H\u0016J\u0010\u0010]\u001a\u00020^2\u0006\u0010_\u001a\u00020^H\u0016J\u0010\u0010`\u001a\u00020^2\u0006\u0010_\u001a\u00020aH\u0016J\u0010\u0010b\u001a\u00020^2\u0006\u0010_\u001a\u00020cH\u0016J\u0010\u0010d\u001a\u00020^2\u0006\u0010_\u001a\u00020eH\u0016J\u0010\u0010f\u001a\u00020g2\u0006\u0010h\u001a\u00020gH\u0016J\u0010\u0010i\u001a\u00020j2\u0006\u0010k\u001a\u00020jH\u0016J\u0010\u0010l\u001a\u00020\f2\u0006\u0010\u0005\u001a\u00020mH\u0016J\u0010\u0010n\u001a\u00020o2\u0006\u0010p\u001a\u00020oH\u0016J\u0010\u0010q\u001a\u00020\f2\u0006\u0010\b\u001a\u00020\u0007H\u0016J\u0010\u0010r\u001a\u00020\f2\u0006\u0010\u0005\u001a\u00020\fH\u0016J\u0010\u0010s\u001a\u00020\f2\u0006\u0010\u0005\u001a\u00020\fH\u0016J\u0010\u0010t\u001a\u00020u2\u0006\u0010@\u001a\u00020uH\u0016J\u0010\u0010v\u001a\u00020u2\u0006\u0010@\u001a\u00020wH\u0016J\u0010\u0010x\u001a\u00020u2\u0006\u0010@\u001a\u00020yH\u0016J\u0010\u0010z\u001a\u00020u2\u0006\u0010@\u001a\u00020{H\u0016J\u0010\u0010|\u001a\u00020O2\u0006\u0010\b\u001a\u00020\u0007H\u0016J\u0010\u0010}\u001a\u00020~2\u0006\u0010\u007f\u001a\u00020~H\u0016J\u0012\u0010\u0080\u0001\u001a\u00020~2\u0007\u0010\u007f\u001a\u00030\u0081\u0001H\u0016J\u0012\u0010\u0082\u0001\u001a\u00020~2\u0007\u0010\u007f\u001a\u00030\u0083\u0001H\u0016J\u0012\u0010\u0084\u0001\u001a\u00020~2\u0007\u0010\u007f\u001a\u00030\u0085\u0001H\u0016J\u0011\u0010\u0086\u0001\u001a\u00020\f2\u0006\u0010\u0005\u001a\u00020\fH\u0016J\u0012\u0010\u0087\u0001\u001a\u00020\f2\u0007\u0010\u0005\u001a\u00030\u0088\u0001H\u0016J\u0014\u0010\u0089\u0001\u001a\u00030\u008a\u00012\b\u0010\u008b\u0001\u001a\u00030\u008a\u0001H\u0016J\u0012\u0010\u008c\u0001\u001a\u00020\f2\u0007\u0010\u0005\u001a\u00030\u008d\u0001H\u0016J\u0013\u0010\u008e\u0001\u001a\u00030\u008f\u00012\u0007\u0010p\u001a\u00030\u008f\u0001H\u0016J\u0014\u0010\u0090\u0001\u001a\u00030\u0091\u00012\b\u0010\u0092\u0001\u001a\u00030\u0091\u0001H\u0016J\u0012\u0010\u0093\u0001\u001a\u00020\f2\u0007\u0010\u0005\u001a\u00030\u0094\u0001H\u0016J\u001e\u0010\u0095\u0001\u001a\u00030\u0096\u00012\b\u0010\u0097\u0001\u001a\u00030\u0096\u00012\b\u0010\u0098\u0001\u001a\u00030\u0099\u0001H\u0016J\u0014\u0010\u009a\u0001\u001a\u00030\u009b\u00012\b\u0010\u009c\u0001\u001a\u00030\u009b\u0001H\u0016J\u0013\u0010\u009d\u0001\u001a\u00030\u009e\u00012\u0007\u0010\u0005\u001a\u00030\u009e\u0001H\u0016J\u0012\u0010\u009f\u0001\u001a\u00020\f2\u0007\u0010\u0005\u001a\u00030\u00a0\u0001H\u0016J\u0012\u0010\u00a1\u0001\u001a\u00020\f2\u0007\u0010\u0005\u001a\u00030\u00a2\u0001H\u0016\u00a8\u0006\u00a3\u0001"}, d2={"Lorg/partiql/lang/ast/passes/AstRewriterBase;", "Lorg/partiql/lang/ast/passes/AstRewriter;", "()V", "innerRewriteDataManipulation", "Lorg/partiql/lang/ast/DataManipulation;", "node", "innerRewriteSelect", "Lorg/partiql/lang/ast/Select;", "selectExpr", "rewriteAssignment", "Lorg/partiql/lang/ast/Assignment;", "rewriteCallAgg", "Lorg/partiql/lang/ast/ExprNode;", "Lorg/partiql/lang/ast/CallAgg;", "rewriteCreateIndex", "Lorg/partiql/lang/ast/CreateIndex;", "rewriteCreateTable", "Lorg/partiql/lang/ast/CreateTable;", "rewriteDataManipulation", "rewriteDataManipulationOperation", "Lorg/partiql/lang/ast/DataManipulationOperation;", "rewriteDataManipulationOperationAssignmentOp", "Lorg/partiql/lang/ast/AssignmentOp;", "rewriteDataManipulationOperationDeleteOp", "rewriteDataManipulationOperationInsertOp", "Lorg/partiql/lang/ast/InsertOp;", "rewriteDataManipulationOperationInsertValueOp", "Lorg/partiql/lang/ast/InsertValueOp;", "rewriteDataManipulationOperationRemoveOp", "Lorg/partiql/lang/ast/RemoveOp;", "rewriteDataManipulationOperations", "Lorg/partiql/lang/ast/DmlOpList;", "rewriteDataManipulationWhere", "rewriteDataType", "Lorg/partiql/lang/ast/DataType;", "dataType", "rewriteDate", "Lorg/partiql/lang/ast/DateTimeType$Date;", "rewriteDropIndex", "Lorg/partiql/lang/ast/DropIndex;", "rewriteDropTable", "Lorg/partiql/lang/ast/DropTable;", "rewriteExec", "Lorg/partiql/lang/ast/Exec;", "rewriteExprNode", "rewriteFromSource", "Lorg/partiql/lang/ast/FromSource;", "fromSource", "rewriteFromSourceExpr", "Lorg/partiql/lang/ast/FromSourceLet;", "Lorg/partiql/lang/ast/FromSourceExpr;", "rewriteFromSourceJoin", "Lorg/partiql/lang/ast/FromSourceJoin;", "rewriteFromSourceLet", "fromSourceLet", "rewriteFromSourceUnpivot", "Lorg/partiql/lang/ast/FromSourceUnpivot;", "rewriteFromSourceValueExpr", "expr", "rewriteGroupBy", "Lorg/partiql/lang/ast/GroupBy;", "groupBy", "rewriteGroupByItem", "Lorg/partiql/lang/ast/GroupByItem;", "item", "rewriteLetBinding", "Lorg/partiql/lang/ast/LetBinding;", "letBinding", "rewriteLetSource", "Lorg/partiql/lang/ast/LetSource;", "letSource", "rewriteLetVariables", "Lorg/partiql/lang/ast/LetVariables;", "variables", "rewriteLiteral", "Lorg/partiql/lang/ast/Literal;", "rewriteLiteralMissing", "Lorg/partiql/lang/ast/LiteralMissing;", "rewriteMetas", "Lorg/partiql/lang/ast/MetaContainer;", "itemWithMetas", "Lorg/partiql/lang/ast/HasMetas;", "rewriteNAry", "Lorg/partiql/lang/ast/NAry;", "rewriteOnConflict", "Lorg/partiql/lang/ast/OnConflict;", "rewriteOrderBy", "Lorg/partiql/lang/ast/OrderBy;", "orderBy", "rewriteParameter", "Lorg/partiql/lang/ast/Parameter;", "rewritePath", "Lorg/partiql/lang/ast/Path;", "rewritePathComponent", "Lorg/partiql/lang/ast/PathComponent;", "pathComponent", "rewritePathComponentExpr", "Lorg/partiql/lang/ast/PathComponentExpr;", "rewritePathComponentUnpivot", "Lorg/partiql/lang/ast/PathComponentUnpivot;", "rewritePathComponentWildcard", "Lorg/partiql/lang/ast/PathComponentWildcard;", "rewriteReturningElem", "Lorg/partiql/lang/ast/ReturningElem;", "returningElem", "rewriteReturningExpr", "Lorg/partiql/lang/ast/ReturningExpr;", "returningExpr", "rewriteSearchedCase", "Lorg/partiql/lang/ast/SearchedCase;", "rewriteSearchedCaseWhen", "Lorg/partiql/lang/ast/SearchedCaseWhen;", "case", "rewriteSelect", "rewriteSelectHaving", "rewriteSelectLimit", "rewriteSelectListItem", "Lorg/partiql/lang/ast/SelectListItem;", "rewriteSelectListItemExpr", "Lorg/partiql/lang/ast/SelectListItemExpr;", "rewriteSelectListItemProjectAll", "Lorg/partiql/lang/ast/SelectListItemProjectAll;", "rewriteSelectListItemStar", "Lorg/partiql/lang/ast/SelectListItemStar;", "rewriteSelectMetas", "rewriteSelectProjection", "Lorg/partiql/lang/ast/SelectProjection;", "projection", "rewriteSelectProjectionList", "Lorg/partiql/lang/ast/SelectProjectionList;", "rewriteSelectProjectionPivot", "Lorg/partiql/lang/ast/SelectProjectionPivot;", "rewriteSelectProjectionValue", "Lorg/partiql/lang/ast/SelectProjectionValue;", "rewriteSelectWhere", "rewriteSeq", "Lorg/partiql/lang/ast/Seq;", "rewriteSeqType", "Lorg/partiql/lang/ast/SeqType;", "type", "rewriteSimpleCase", "Lorg/partiql/lang/ast/SimpleCase;", "rewriteSimpleCaseWhen", "Lorg/partiql/lang/ast/SimpleCaseWhen;", "rewriteSortSpec", "Lorg/partiql/lang/ast/SortSpec;", "sortSpec", "rewriteStruct", "Lorg/partiql/lang/ast/Struct;", "rewriteStructField", "Lorg/partiql/lang/ast/StructField;", "field", "index", "", "rewriteSymbolicName", "Lorg/partiql/lang/ast/SymbolicName;", "symbolicName", "rewriteTime", "Lorg/partiql/lang/ast/DateTimeType$Time;", "rewriteTyped", "Lorg/partiql/lang/ast/Typed;", "rewriteVariableReference", "Lorg/partiql/lang/ast/VariableReference;", "lang"})
public class AstRewriterBase
implements AstRewriter {
    @Override
    @NotNull
    public ExprNode rewriteExprNode(@NotNull ExprNode node) {
        ExprNode exprNode;
        Intrinsics.checkParameterIsNotNull(node, "node");
        ThreadInterruptUtilsKt.checkThreadInterrupted();
        ExprNode exprNode2 = node;
        if (exprNode2 instanceof Literal) {
            exprNode = this.rewriteLiteral((Literal)node);
        } else if (exprNode2 instanceof LiteralMissing) {
            exprNode = this.rewriteLiteralMissing((LiteralMissing)node);
        } else if (exprNode2 instanceof VariableReference) {
            exprNode = this.rewriteVariableReference((VariableReference)node);
        } else if (exprNode2 instanceof NAry) {
            exprNode = this.rewriteNAry((NAry)node);
        } else if (exprNode2 instanceof CallAgg) {
            exprNode = this.rewriteCallAgg((CallAgg)node);
        } else if (exprNode2 instanceof Typed) {
            exprNode = this.rewriteTyped((Typed)node);
        } else if (exprNode2 instanceof Path) {
            exprNode = this.rewritePath((Path)node);
        } else if (exprNode2 instanceof SimpleCase) {
            exprNode = this.rewriteSimpleCase((SimpleCase)node);
        } else if (exprNode2 instanceof SearchedCase) {
            exprNode = this.rewriteSearchedCase((SearchedCase)node);
        } else if (exprNode2 instanceof Struct) {
            exprNode = this.rewriteStruct((Struct)node);
        } else if (exprNode2 instanceof Seq) {
            exprNode = this.rewriteSeq((Seq)node);
        } else if (exprNode2 instanceof Select) {
            exprNode = this.rewriteSelect((Select)node);
        } else if (exprNode2 instanceof Parameter) {
            exprNode = this.rewriteParameter((Parameter)node);
        } else if (exprNode2 instanceof DataManipulation) {
            exprNode = this.rewriteDataManipulation((DataManipulation)node);
        } else if (exprNode2 instanceof CreateTable) {
            exprNode = this.rewriteCreateTable((CreateTable)node);
        } else if (exprNode2 instanceof CreateIndex) {
            exprNode = this.rewriteCreateIndex((CreateIndex)node);
        } else if (exprNode2 instanceof DropTable) {
            exprNode = this.rewriteDropTable((DropTable)node);
        } else if (exprNode2 instanceof DropIndex) {
            exprNode = this.rewriteDropIndex((DropIndex)node);
        } else if (exprNode2 instanceof Exec) {
            exprNode = this.rewriteExec((Exec)node);
        } else if (exprNode2 instanceof DateTimeType.Date) {
            exprNode = this.rewriteDate((DateTimeType.Date)node);
        } else if (exprNode2 instanceof DateTimeType.Time) {
            exprNode = this.rewriteTime((DateTimeType.Time)node);
        } else {
            throw new NoWhenBranchMatchedException();
        }
        return exprNode;
    }

    @NotNull
    public MetaContainer rewriteMetas(@NotNull HasMetas itemWithMetas) {
        Intrinsics.checkParameterIsNotNull(itemWithMetas, "itemWithMetas");
        return itemWithMetas.getMetas();
    }

    @NotNull
    public ExprNode rewriteLiteral(@NotNull Literal node) {
        Intrinsics.checkParameterIsNotNull(node, "node");
        return new Literal(node.getIonValue(), this.rewriteMetas(node));
    }

    @NotNull
    public ExprNode rewriteLiteralMissing(@NotNull LiteralMissing node) {
        Intrinsics.checkParameterIsNotNull(node, "node");
        return new LiteralMissing(this.rewriteMetas(node));
    }

    @NotNull
    public ExprNode rewriteVariableReference(@NotNull VariableReference node) {
        Intrinsics.checkParameterIsNotNull(node, "node");
        return new VariableReference(node.getId(), node.getCase(), node.getScopeQualifier(), this.rewriteMetas(node));
    }

    /*
     * WARNING - void declaration
     */
    @NotNull
    public ExprNode rewriteSeq(@NotNull Seq node) {
        Collection<ExprNode> collection;
        void $this$mapTo$iv$iv;
        void $this$map$iv;
        Intrinsics.checkParameterIsNotNull(node, "node");
        Iterable iterable = node.getValues();
        SeqType seqType = this.rewriteSeqType(node.getType());
        boolean $i$f$map = false;
        void var4_5 = $this$map$iv;
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
        boolean $i$f$mapTo = false;
        for (Object item$iv$iv : $this$mapTo$iv$iv) {
            void it;
            ExprNode exprNode = (ExprNode)item$iv$iv;
            collection = destination$iv$iv;
            boolean bl = false;
            ExprNode exprNode2 = this.rewriteExprNode((ExprNode)it);
            collection.add(exprNode2);
        }
        collection = (List)destination$iv$iv;
        MetaContainer metaContainer = this.rewriteMetas(node);
        List list = collection;
        SeqType seqType2 = seqType;
        return new Seq(seqType2, list, metaContainer);
    }

    @NotNull
    public SeqType rewriteSeqType(@NotNull SeqType type) {
        Intrinsics.checkParameterIsNotNull((Object)type, "type");
        return type;
    }

    /*
     * WARNING - void declaration
     */
    @NotNull
    public ExprNode rewriteStruct(@NotNull Struct node) {
        Collection<StructField> collection;
        void $this$mapIndexedTo$iv$iv;
        Intrinsics.checkParameterIsNotNull(node, "node");
        Iterable $this$mapIndexed$iv = node.getFields();
        boolean $i$f$mapIndexed = false;
        Iterable iterable = $this$mapIndexed$iv;
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$mapIndexed$iv, 10));
        boolean $i$f$mapIndexedTo = false;
        int index$iv$iv = 0;
        for (Object item$iv$iv : $this$mapIndexedTo$iv$iv) {
            void index;
            void field;
            int n = index$iv$iv++;
            Collection collection2 = destination$iv$iv;
            boolean bl = false;
            if (n < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            int n2 = n;
            StructField structField = (StructField)item$iv$iv;
            int n3 = n2;
            collection = collection2;
            boolean bl2 = false;
            StructField structField2 = this.rewriteStructField((StructField)field, (int)index);
            collection.add(structField2);
        }
        collection = (List)destination$iv$iv;
        MetaContainer metaContainer = this.rewriteMetas(node);
        List list = collection;
        return new Struct(list, metaContainer);
    }

    @NotNull
    public StructField rewriteStructField(@NotNull StructField field, int index) {
        Intrinsics.checkParameterIsNotNull(field, "field");
        return new StructField(this.rewriteExprNode(field.getName()), this.rewriteExprNode(field.getExpr()));
    }

    /*
     * WARNING - void declaration
     */
    @NotNull
    public ExprNode rewriteSearchedCase(@NotNull SearchedCase node) {
        AstNode astNode;
        AstNode astNode2;
        Collection<SearchedCaseWhen> collection;
        void $this$mapTo$iv$iv;
        Intrinsics.checkParameterIsNotNull(node, "node");
        Iterable $this$map$iv = node.getWhenClauses();
        boolean $i$f$map = false;
        Iterable iterable = $this$map$iv;
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
        boolean $i$f$mapTo = false;
        for (Object item$iv$iv : $this$mapTo$iv$iv) {
            void it;
            SearchedCaseWhen searchedCaseWhen = (SearchedCaseWhen)item$iv$iv;
            collection = destination$iv$iv;
            boolean bl = false;
            astNode2 = this.rewriteSearchedCaseWhen((SearchedCaseWhen)it);
            collection.add((SearchedCaseWhen)astNode2);
        }
        collection = (List)destination$iv$iv;
        Collection collection2 = collection;
        ExprNode exprNode = node.getElseExpr();
        if (exprNode != null) {
            ExprNode exprNode2 = exprNode;
            collection = collection2;
            boolean bl = false;
            boolean bl2 = false;
            ExprNode it = exprNode2;
            boolean bl3 = false;
            astNode2 = this.rewriteExprNode(it);
            collection2 = collection;
            astNode = astNode2;
        } else {
            astNode = null;
        }
        MetaContainer metaContainer = this.rewriteMetas(node);
        AstNode astNode3 = astNode;
        List list = collection2;
        return new SearchedCase(list, (ExprNode)astNode3, metaContainer);
    }

    /*
     * WARNING - void declaration
     */
    @NotNull
    public ExprNode rewriteSimpleCase(@NotNull SimpleCase node) {
        AstNode astNode;
        AstNode astNode2;
        Collection<SimpleCaseWhen> collection;
        void $this$mapTo$iv$iv;
        void $this$map$iv;
        Intrinsics.checkParameterIsNotNull(node, "node");
        Iterable iterable = node.getWhenClauses();
        ExprNode exprNode = this.rewriteExprNode(node.getValueExpr());
        boolean $i$f$map = false;
        void var4_5 = $this$map$iv;
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
        boolean $i$f$mapTo = false;
        for (Object item$iv$iv : $this$mapTo$iv$iv) {
            void it;
            SimpleCaseWhen simpleCaseWhen = (SimpleCaseWhen)item$iv$iv;
            collection = destination$iv$iv;
            boolean bl = false;
            astNode2 = this.rewriteSimpleCaseWhen((SimpleCaseWhen)it);
            collection.add((SimpleCaseWhen)astNode2);
        }
        collection = (List)destination$iv$iv;
        ExprNode exprNode2 = exprNode;
        Collection collection2 = collection;
        ExprNode exprNode3 = node.getElseExpr();
        if (exprNode3 != null) {
            iterable = exprNode3;
            collection = collection2;
            exprNode = exprNode2;
            boolean bl = false;
            boolean bl2 = false;
            Iterable it = iterable;
            boolean bl3 = false;
            astNode2 = this.rewriteExprNode((ExprNode)it);
            exprNode2 = exprNode;
            collection2 = collection;
            astNode = astNode2;
        } else {
            astNode = null;
        }
        MetaContainer metaContainer = this.rewriteMetas(node);
        AstNode astNode3 = astNode;
        List list = collection2;
        ExprNode exprNode4 = exprNode2;
        return new SimpleCase(exprNode4, list, (ExprNode)astNode3, metaContainer);
    }

    /*
     * WARNING - void declaration
     */
    @NotNull
    public ExprNode rewritePath(@NotNull Path node) {
        Collection<PathComponent> collection;
        void $this$mapTo$iv$iv;
        void $this$map$iv;
        Intrinsics.checkParameterIsNotNull(node, "node");
        Iterable iterable = node.getComponents();
        ExprNode exprNode = this.rewriteExprNode(node.getRoot());
        boolean $i$f$map = false;
        void var4_5 = $this$map$iv;
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
        boolean $i$f$mapTo = false;
        for (Object item$iv$iv : $this$mapTo$iv$iv) {
            void it;
            PathComponent pathComponent = (PathComponent)item$iv$iv;
            collection = destination$iv$iv;
            boolean bl = false;
            PathComponent pathComponent2 = this.rewritePathComponent((PathComponent)it);
            collection.add(pathComponent2);
        }
        collection = (List)destination$iv$iv;
        MetaContainer metaContainer = this.rewriteMetas(node);
        List list = collection;
        ExprNode exprNode2 = exprNode;
        return new Path(exprNode2, list, metaContainer);
    }

    @NotNull
    public ExprNode rewriteTyped(@NotNull Typed node) {
        Intrinsics.checkParameterIsNotNull(node, "node");
        return new Typed(node.getOp(), this.rewriteExprNode(node.getExpr()), this.rewriteDataType(node.getType()), this.rewriteMetas(node));
    }

    @NotNull
    public ExprNode rewriteCallAgg(@NotNull CallAgg node) {
        Intrinsics.checkParameterIsNotNull(node, "node");
        return new CallAgg(this.rewriteExprNode(node.getFuncExpr()), node.getSetQuantifier(), this.rewriteExprNode(node.getArg()), this.rewriteMetas(node));
    }

    /*
     * WARNING - void declaration
     */
    @NotNull
    public ExprNode rewriteNAry(@NotNull NAry node) {
        Collection<ExprNode> collection;
        void $this$mapTo$iv$iv;
        void $this$map$iv;
        Intrinsics.checkParameterIsNotNull(node, "node");
        Iterable iterable = node.getArgs();
        NAryOp nAryOp = node.getOp();
        boolean $i$f$map = false;
        void var4_5 = $this$map$iv;
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
        boolean $i$f$mapTo = false;
        for (Object item$iv$iv : $this$mapTo$iv$iv) {
            void it;
            ExprNode exprNode = (ExprNode)item$iv$iv;
            collection = destination$iv$iv;
            boolean bl = false;
            ExprNode exprNode2 = this.rewriteExprNode((ExprNode)it);
            collection.add(exprNode2);
        }
        collection = (List)destination$iv$iv;
        MetaContainer metaContainer = this.rewriteMetas(node);
        List list = collection;
        NAryOp nAryOp2 = nAryOp;
        return new NAry(nAryOp2, list, metaContainer);
    }

    @NotNull
    public ExprNode rewriteSelect(@NotNull Select selectExpr) {
        Intrinsics.checkParameterIsNotNull(selectExpr, "selectExpr");
        return this.innerRewriteSelect(selectExpr);
    }

    @NotNull
    protected Select innerRewriteSelect(@NotNull Select selectExpr) {
        ExprNode exprNode;
        OrderBy orderBy;
        boolean bl;
        ExprNode exprNode2;
        GroupBy groupBy2;
        ExprNode exprNode3;
        LetSource letSource;
        boolean bl2;
        Intrinsics.checkParameterIsNotNull(selectExpr, "selectExpr");
        FromSource from2 = this.rewriteFromSource(selectExpr.getFrom());
        LetSource letSource2 = selectExpr.getFromLet();
        if (letSource2 != null) {
            LetSource letSource3 = letSource2;
            boolean bl3 = false;
            bl2 = false;
            LetSource it = letSource3;
            boolean bl4 = false;
            letSource = this.rewriteLetSource(it);
        } else {
            letSource = null;
        }
        LetSource fromLet2 = letSource;
        ExprNode exprNode4 = selectExpr.getWhere();
        if (exprNode4 != null) {
            ExprNode exprNode5 = exprNode4;
            bl2 = false;
            boolean it = false;
            ExprNode it2 = exprNode5;
            boolean bl5 = false;
            exprNode3 = this.rewriteSelectWhere(it2);
        } else {
            exprNode3 = null;
        }
        ExprNode where2 = exprNode3;
        GroupBy groupBy3 = selectExpr.getGroupBy();
        if (groupBy3 != null) {
            GroupBy groupBy4 = groupBy3;
            boolean it = false;
            boolean it2 = false;
            GroupBy it3 = groupBy4;
            boolean bl6 = false;
            groupBy2 = this.rewriteGroupBy(it3);
        } else {
            groupBy2 = null;
        }
        GroupBy groupBy5 = groupBy2;
        ExprNode exprNode6 = selectExpr.getHaving();
        if (exprNode6 != null) {
            ExprNode it = exprNode6;
            boolean it2 = false;
            boolean it3 = false;
            ExprNode it4 = it;
            boolean bl7 = false;
            exprNode2 = this.rewriteSelectHaving(it4);
        } else {
            exprNode2 = null;
        }
        ExprNode having2 = exprNode2;
        SelectProjection projection = this.rewriteSelectProjection(selectExpr.getProjection());
        OrderBy orderBy2 = selectExpr.getOrderBy();
        if (orderBy2 != null) {
            OrderBy it3 = orderBy2;
            boolean it4 = false;
            bl = false;
            OrderBy it = it3;
            boolean bl8 = false;
            orderBy = this.rewriteOrderBy(it);
        } else {
            orderBy = null;
        }
        OrderBy orderBy3 = orderBy;
        ExprNode exprNode7 = selectExpr.getLimit();
        if (exprNode7 != null) {
            ExprNode it4 = exprNode7;
            bl = false;
            boolean bl9 = false;
            ExprNode it = it4;
            boolean bl10 = false;
            exprNode = this.rewriteSelectLimit(it);
        } else {
            exprNode = null;
        }
        ExprNode limit2 = exprNode;
        MetaContainer metas = this.rewriteSelectMetas(selectExpr);
        return new Select(selectExpr.getSetQuantifier(), projection, from2, fromLet2, where2, groupBy5, having2, orderBy3, limit2, metas);
    }

    @NotNull
    public ExprNode rewriteSelectWhere(@NotNull ExprNode node) {
        Intrinsics.checkParameterIsNotNull(node, "node");
        return this.rewriteExprNode(node);
    }

    @NotNull
    public ExprNode rewriteSelectHaving(@NotNull ExprNode node) {
        Intrinsics.checkParameterIsNotNull(node, "node");
        return this.rewriteExprNode(node);
    }

    @NotNull
    public ExprNode rewriteSelectLimit(@NotNull ExprNode node) {
        Intrinsics.checkParameterIsNotNull(node, "node");
        return this.rewriteExprNode(node);
    }

    @NotNull
    public MetaContainer rewriteSelectMetas(@NotNull Select selectExpr) {
        Intrinsics.checkParameterIsNotNull(selectExpr, "selectExpr");
        return this.rewriteMetas(selectExpr);
    }

    @NotNull
    public SelectProjection rewriteSelectProjection(@NotNull SelectProjection projection) {
        SelectProjection selectProjection;
        Intrinsics.checkParameterIsNotNull(projection, "projection");
        SelectProjection selectProjection2 = projection;
        if (selectProjection2 instanceof SelectProjectionList) {
            selectProjection = this.rewriteSelectProjectionList((SelectProjectionList)projection);
        } else if (selectProjection2 instanceof SelectProjectionValue) {
            selectProjection = this.rewriteSelectProjectionValue((SelectProjectionValue)projection);
        } else if (selectProjection2 instanceof SelectProjectionPivot) {
            selectProjection = this.rewriteSelectProjectionPivot((SelectProjectionPivot)projection);
        } else {
            throw new NoWhenBranchMatchedException();
        }
        return selectProjection;
    }

    /*
     * WARNING - void declaration
     */
    @NotNull
    public SelectProjection rewriteSelectProjectionList(@NotNull SelectProjectionList projection) {
        Collection<SelectListItem> collection;
        void $this$mapTo$iv$iv;
        Intrinsics.checkParameterIsNotNull(projection, "projection");
        Iterable $this$map$iv = projection.getItems();
        boolean $i$f$map = false;
        Iterable iterable = $this$map$iv;
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
        boolean $i$f$mapTo = false;
        for (Object item$iv$iv : $this$mapTo$iv$iv) {
            void it;
            SelectListItem selectListItem = (SelectListItem)item$iv$iv;
            collection = destination$iv$iv;
            boolean bl = false;
            SelectListItem selectListItem2 = this.rewriteSelectListItem((SelectListItem)it);
            collection.add(selectListItem2);
        }
        collection = (List)destination$iv$iv;
        List list = collection;
        return new SelectProjectionList(list);
    }

    @NotNull
    public SelectProjection rewriteSelectProjectionValue(@NotNull SelectProjectionValue projection) {
        Intrinsics.checkParameterIsNotNull(projection, "projection");
        return new SelectProjectionValue(this.rewriteExprNode(projection.getExpr()));
    }

    @NotNull
    public SelectProjection rewriteSelectProjectionPivot(@NotNull SelectProjectionPivot projection) {
        Intrinsics.checkParameterIsNotNull(projection, "projection");
        return new SelectProjectionPivot(this.rewriteExprNode(projection.getNameExpr()), this.rewriteExprNode(projection.getValueExpr()));
    }

    @NotNull
    public SelectListItem rewriteSelectListItem(@NotNull SelectListItem item) {
        SelectListItem selectListItem;
        Intrinsics.checkParameterIsNotNull(item, "item");
        SelectListItem selectListItem2 = item;
        if (selectListItem2 instanceof SelectListItemStar) {
            selectListItem = this.rewriteSelectListItemStar((SelectListItemStar)item);
        } else if (selectListItem2 instanceof SelectListItemExpr) {
            selectListItem = this.rewriteSelectListItemExpr((SelectListItemExpr)item);
        } else if (selectListItem2 instanceof SelectListItemProjectAll) {
            selectListItem = this.rewriteSelectListItemProjectAll((SelectListItemProjectAll)item);
        } else {
            throw new NoWhenBranchMatchedException();
        }
        return selectListItem;
    }

    @NotNull
    public SelectListItem rewriteSelectListItemProjectAll(@NotNull SelectListItemProjectAll item) {
        Intrinsics.checkParameterIsNotNull(item, "item");
        return new SelectListItemProjectAll(this.rewriteExprNode(item.getExpr()));
    }

    @NotNull
    public SelectListItem rewriteSelectListItemExpr(@NotNull SelectListItemExpr item) {
        SymbolicName symbolicName;
        Intrinsics.checkParameterIsNotNull(item, "item");
        ExprNode exprNode = this.rewriteExprNode(item.getExpr());
        SymbolicName symbolicName2 = item.getAsName();
        if (symbolicName2 != null) {
            SymbolicName symbolicName3 = symbolicName2;
            ExprNode exprNode2 = exprNode;
            boolean bl = false;
            boolean bl2 = false;
            SymbolicName it = symbolicName3;
            boolean bl3 = false;
            SymbolicName symbolicName4 = this.rewriteSymbolicName(it);
            exprNode = exprNode2;
            symbolicName = symbolicName4;
        } else {
            symbolicName = null;
        }
        SymbolicName symbolicName5 = symbolicName;
        ExprNode exprNode3 = exprNode;
        return new SelectListItemExpr(exprNode3, symbolicName5);
    }

    @NotNull
    public SelectListItem rewriteSelectListItemStar(@NotNull SelectListItemStar item) {
        Intrinsics.checkParameterIsNotNull(item, "item");
        return new SelectListItemStar(this.rewriteMetas(item));
    }

    @NotNull
    public PathComponent rewritePathComponent(@NotNull PathComponent pathComponent) {
        PathComponent pathComponent2;
        Intrinsics.checkParameterIsNotNull(pathComponent, "pathComponent");
        PathComponent pathComponent3 = pathComponent;
        if (pathComponent3 instanceof PathComponentUnpivot) {
            pathComponent2 = this.rewritePathComponentUnpivot((PathComponentUnpivot)pathComponent);
        } else if (pathComponent3 instanceof PathComponentWildcard) {
            pathComponent2 = this.rewritePathComponentWildcard((PathComponentWildcard)pathComponent);
        } else if (pathComponent3 instanceof PathComponentExpr) {
            pathComponent2 = this.rewritePathComponentExpr((PathComponentExpr)pathComponent);
        } else {
            throw new NoWhenBranchMatchedException();
        }
        return pathComponent2;
    }

    @NotNull
    public PathComponent rewritePathComponentUnpivot(@NotNull PathComponentUnpivot pathComponent) {
        Intrinsics.checkParameterIsNotNull(pathComponent, "pathComponent");
        return new PathComponentUnpivot(this.rewriteMetas(pathComponent));
    }

    @NotNull
    public PathComponent rewritePathComponentWildcard(@NotNull PathComponentWildcard pathComponent) {
        Intrinsics.checkParameterIsNotNull(pathComponent, "pathComponent");
        return new PathComponentWildcard(this.rewriteMetas(pathComponent));
    }

    @NotNull
    public PathComponent rewritePathComponentExpr(@NotNull PathComponentExpr pathComponent) {
        Intrinsics.checkParameterIsNotNull(pathComponent, "pathComponent");
        return new PathComponentExpr(this.rewriteExprNode(pathComponent.getExpr()), pathComponent.getCase());
    }

    @NotNull
    public FromSource rewriteFromSource(@NotNull FromSource fromSource) {
        FromSource fromSource2;
        Intrinsics.checkParameterIsNotNull(fromSource, "fromSource");
        FromSource fromSource3 = fromSource;
        if (fromSource3 instanceof FromSourceJoin) {
            fromSource2 = this.rewriteFromSourceJoin((FromSourceJoin)fromSource);
        } else if (fromSource3 instanceof FromSourceLet) {
            fromSource2 = this.rewriteFromSourceLet((FromSourceLet)fromSource);
        } else {
            throw new NoWhenBranchMatchedException();
        }
        return fromSource2;
    }

    @NotNull
    public FromSourceLet rewriteFromSourceLet(@NotNull FromSourceLet fromSourceLet) {
        FromSourceLet fromSourceLet2;
        Intrinsics.checkParameterIsNotNull(fromSourceLet, "fromSourceLet");
        FromSourceLet fromSourceLet3 = fromSourceLet;
        if (fromSourceLet3 instanceof FromSourceExpr) {
            fromSourceLet2 = this.rewriteFromSourceExpr((FromSourceExpr)fromSourceLet);
        } else if (fromSourceLet3 instanceof FromSourceUnpivot) {
            fromSourceLet2 = this.rewriteFromSourceUnpivot((FromSourceUnpivot)fromSourceLet);
        } else {
            throw new NoWhenBranchMatchedException();
        }
        return fromSourceLet2;
    }

    @NotNull
    public LetVariables rewriteLetVariables(@NotNull LetVariables variables) {
        SymbolicName symbolicName;
        SymbolicName symbolicName2;
        SymbolicName symbolicName3;
        SymbolicName symbolicName4;
        SymbolicName symbolicName5;
        SymbolicName it;
        boolean bl;
        boolean bl2;
        SymbolicName symbolicName6;
        Intrinsics.checkParameterIsNotNull(variables, "variables");
        SymbolicName symbolicName7 = variables.getAsName();
        if (symbolicName7 != null) {
            symbolicName6 = symbolicName7;
            bl2 = false;
            bl = false;
            it = symbolicName6;
            boolean bl3 = false;
            symbolicName4 = symbolicName5 = this.rewriteSymbolicName(it);
        } else {
            symbolicName4 = null;
        }
        SymbolicName symbolicName8 = variables.getAtName();
        if (symbolicName8 != null) {
            symbolicName6 = symbolicName8;
            symbolicName5 = symbolicName4;
            bl2 = false;
            bl = false;
            it = symbolicName6;
            boolean bl4 = false;
            symbolicName3 = this.rewriteSymbolicName(it);
            symbolicName4 = symbolicName5;
            symbolicName2 = symbolicName3;
        } else {
            symbolicName2 = null;
        }
        SymbolicName symbolicName9 = variables.getByName();
        if (symbolicName9 != null) {
            symbolicName6 = symbolicName9;
            symbolicName3 = symbolicName2;
            symbolicName5 = symbolicName4;
            bl2 = false;
            bl = false;
            it = symbolicName6;
            boolean bl5 = false;
            SymbolicName symbolicName10 = this.rewriteSymbolicName(it);
            symbolicName4 = symbolicName5;
            symbolicName2 = symbolicName3;
            symbolicName = symbolicName10;
        } else {
            symbolicName = null;
        }
        SymbolicName symbolicName11 = symbolicName;
        SymbolicName symbolicName12 = symbolicName2;
        SymbolicName symbolicName13 = symbolicName4;
        return new LetVariables(symbolicName13, symbolicName12, symbolicName11);
    }

    /*
     * WARNING - void declaration
     */
    @NotNull
    public LetSource rewriteLetSource(@NotNull LetSource letSource) {
        Collection<LetBinding> collection;
        void $this$mapTo$iv$iv;
        Intrinsics.checkParameterIsNotNull(letSource, "letSource");
        Iterable $this$map$iv = letSource.getBindings();
        boolean $i$f$map = false;
        Iterable iterable = $this$map$iv;
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
        boolean $i$f$mapTo = false;
        for (Object item$iv$iv : $this$mapTo$iv$iv) {
            void it;
            LetBinding letBinding = (LetBinding)item$iv$iv;
            collection = destination$iv$iv;
            boolean bl = false;
            LetBinding letBinding2 = this.rewriteLetBinding((LetBinding)it);
            collection.add(letBinding2);
        }
        collection = (List)destination$iv$iv;
        List list = collection;
        return new LetSource(list);
    }

    @NotNull
    public LetBinding rewriteLetBinding(@NotNull LetBinding letBinding) {
        Intrinsics.checkParameterIsNotNull(letBinding, "letBinding");
        return new LetBinding(this.rewriteExprNode(letBinding.getExpr()), this.rewriteSymbolicName(letBinding.getName()));
    }

    @NotNull
    public ExprNode rewriteFromSourceValueExpr(@NotNull ExprNode expr) {
        Intrinsics.checkParameterIsNotNull(expr, "expr");
        return this.rewriteExprNode(expr);
    }

    @NotNull
    public FromSourceLet rewriteFromSourceUnpivot(@NotNull FromSourceUnpivot fromSource) {
        Intrinsics.checkParameterIsNotNull(fromSource, "fromSource");
        return new FromSourceUnpivot(this.rewriteFromSourceValueExpr(fromSource.getExpr()), this.rewriteLetVariables(fromSource.getVariables()), this.rewriteMetas(fromSource));
    }

    @NotNull
    public FromSourceLet rewriteFromSourceExpr(@NotNull FromSourceExpr fromSource) {
        Intrinsics.checkParameterIsNotNull(fromSource, "fromSource");
        return new FromSourceExpr(this.rewriteFromSourceValueExpr(fromSource.getExpr()), this.rewriteLetVariables(fromSource.getVariables()));
    }

    @NotNull
    public FromSource rewriteFromSourceJoin(@NotNull FromSourceJoin fromSource) {
        Intrinsics.checkParameterIsNotNull(fromSource, "fromSource");
        return new FromSourceJoin(fromSource.getJoinOp(), this.rewriteFromSource(fromSource.getLeftRef()), this.rewriteFromSource(fromSource.getRightRef()), this.rewriteExprNode(fromSource.getCondition()), this.rewriteMetas(fromSource));
    }

    /*
     * WARNING - void declaration
     */
    @NotNull
    public GroupBy rewriteGroupBy(@NotNull GroupBy groupBy2) {
        AstNode astNode;
        AstNode astNode2;
        Collection<GroupByItem> collection;
        void $this$mapTo$iv$iv;
        void $this$map$iv;
        Intrinsics.checkParameterIsNotNull(groupBy2, "groupBy");
        Iterable iterable = groupBy2.getGroupByItems();
        GroupingStrategy groupingStrategy = groupBy2.getGrouping();
        boolean $i$f$map = false;
        void var4_5 = $this$map$iv;
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
        boolean $i$f$mapTo = false;
        for (Object item$iv$iv : $this$mapTo$iv$iv) {
            void it;
            GroupByItem groupByItem = (GroupByItem)item$iv$iv;
            collection = destination$iv$iv;
            boolean bl = false;
            astNode2 = this.rewriteGroupByItem((GroupByItem)it);
            collection.add((GroupByItem)astNode2);
        }
        collection = (List)destination$iv$iv;
        GroupingStrategy groupingStrategy2 = groupingStrategy;
        Collection collection2 = collection;
        SymbolicName symbolicName = groupBy2.getGroupName();
        if (symbolicName != null) {
            iterable = symbolicName;
            collection = collection2;
            groupingStrategy = groupingStrategy2;
            boolean bl = false;
            boolean bl2 = false;
            Iterable it = iterable;
            boolean bl3 = false;
            astNode2 = this.rewriteSymbolicName((SymbolicName)it);
            groupingStrategy2 = groupingStrategy;
            collection2 = collection;
            astNode = astNode2;
        } else {
            astNode = null;
        }
        AstNode astNode3 = astNode;
        List list = collection2;
        GroupingStrategy groupingStrategy3 = groupingStrategy2;
        return new GroupBy(groupingStrategy3, list, (SymbolicName)astNode3);
    }

    @NotNull
    public GroupByItem rewriteGroupByItem(@NotNull GroupByItem item) {
        SymbolicName symbolicName;
        Intrinsics.checkParameterIsNotNull(item, "item");
        ExprNode exprNode = this.rewriteExprNode(item.getExpr());
        SymbolicName symbolicName2 = item.getAsName();
        if (symbolicName2 != null) {
            SymbolicName symbolicName3 = symbolicName2;
            ExprNode exprNode2 = exprNode;
            boolean bl = false;
            boolean bl2 = false;
            SymbolicName it = symbolicName3;
            boolean bl3 = false;
            SymbolicName symbolicName4 = this.rewriteSymbolicName(it);
            exprNode = exprNode2;
            symbolicName = symbolicName4;
        } else {
            symbolicName = null;
        }
        SymbolicName symbolicName5 = symbolicName;
        ExprNode exprNode3 = exprNode;
        return new GroupByItem(exprNode3, symbolicName5);
    }

    /*
     * WARNING - void declaration
     */
    @NotNull
    public OrderBy rewriteOrderBy(@NotNull OrderBy orderBy) {
        Collection<SortSpec> collection;
        void $this$mapTo$iv$iv;
        Intrinsics.checkParameterIsNotNull(orderBy, "orderBy");
        Iterable $this$map$iv = orderBy.getSortSpecItems();
        boolean $i$f$map = false;
        Iterable iterable = $this$map$iv;
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
        boolean $i$f$mapTo = false;
        for (Object item$iv$iv : $this$mapTo$iv$iv) {
            void it;
            SortSpec sortSpec = (SortSpec)item$iv$iv;
            collection = destination$iv$iv;
            boolean bl = false;
            SortSpec sortSpec2 = this.rewriteSortSpec((SortSpec)it);
            collection.add(sortSpec2);
        }
        collection = (List)destination$iv$iv;
        List list = collection;
        return new OrderBy(list);
    }

    @NotNull
    public SortSpec rewriteSortSpec(@NotNull SortSpec sortSpec) {
        Intrinsics.checkParameterIsNotNull(sortSpec, "sortSpec");
        return new SortSpec(this.rewriteExprNode(sortSpec.getExpr()), sortSpec.getOrderingSpec());
    }

    @NotNull
    public DataType rewriteDataType(@NotNull DataType dataType) {
        Intrinsics.checkParameterIsNotNull(dataType, "dataType");
        return dataType;
    }

    @NotNull
    public SimpleCaseWhen rewriteSimpleCaseWhen(@NotNull SimpleCaseWhen simpleCaseWhen) {
        Intrinsics.checkParameterIsNotNull(simpleCaseWhen, "case");
        return new SimpleCaseWhen(this.rewriteExprNode(simpleCaseWhen.getValueExpr()), this.rewriteExprNode(simpleCaseWhen.getThenExpr()));
    }

    @NotNull
    public SearchedCaseWhen rewriteSearchedCaseWhen(@NotNull SearchedCaseWhen searchedCaseWhen) {
        Intrinsics.checkParameterIsNotNull(searchedCaseWhen, "case");
        return new SearchedCaseWhen(this.rewriteExprNode(searchedCaseWhen.getCondition()), this.rewriteExprNode(searchedCaseWhen.getThenExpr()));
    }

    @NotNull
    public SymbolicName rewriteSymbolicName(@NotNull SymbolicName symbolicName) {
        Intrinsics.checkParameterIsNotNull(symbolicName, "symbolicName");
        return new SymbolicName(symbolicName.getName(), this.rewriteMetas(symbolicName));
    }

    @NotNull
    public Parameter rewriteParameter(@NotNull Parameter node) {
        Intrinsics.checkParameterIsNotNull(node, "node");
        return new Parameter(node.getPosition(), this.rewriteMetas(node));
    }

    @NotNull
    public DataManipulation rewriteDataManipulation(@NotNull DataManipulation node) {
        Intrinsics.checkParameterIsNotNull(node, "node");
        return this.innerRewriteDataManipulation(node);
    }

    @NotNull
    public DataManipulation innerRewriteDataManipulation(@NotNull DataManipulation node) {
        ReturningExpr returningExpr;
        ExprNode exprNode;
        FromSource fromSource;
        boolean bl;
        Intrinsics.checkParameterIsNotNull(node, "node");
        FromSource fromSource2 = node.getFrom();
        if (fromSource2 != null) {
            FromSource fromSource3 = fromSource2;
            boolean bl2 = false;
            bl = false;
            FromSource it = fromSource3;
            boolean bl3 = false;
            fromSource = this.rewriteFromSource(it);
        } else {
            fromSource = null;
        }
        FromSource from2 = fromSource;
        ExprNode exprNode2 = node.getWhere();
        if (exprNode2 != null) {
            ExprNode exprNode3 = exprNode2;
            bl = false;
            boolean it = false;
            ExprNode it2 = exprNode3;
            boolean bl4 = false;
            exprNode = this.rewriteDataManipulationWhere(it2);
        } else {
            exprNode = null;
        }
        ExprNode where2 = exprNode;
        ReturningExpr returningExpr2 = node.getReturning();
        if (returningExpr2 != null) {
            ReturningExpr returningExpr3 = returningExpr2;
            boolean it = false;
            boolean bl5 = false;
            ReturningExpr it3 = returningExpr3;
            boolean bl6 = false;
            returningExpr = this.rewriteReturningExpr(it3);
        } else {
            returningExpr = null;
        }
        ReturningExpr returning2 = returningExpr;
        DmlOpList dmlOperations = this.rewriteDataManipulationOperations(node.getDmlOperations());
        MetaContainer metas = this.rewriteMetas(node);
        return new DataManipulation(dmlOperations, from2, where2, returning2, metas);
    }

    @NotNull
    public ExprNode rewriteDataManipulationWhere(@NotNull ExprNode node) {
        Intrinsics.checkParameterIsNotNull(node, "node");
        return this.rewriteExprNode(node);
    }

    /*
     * WARNING - void declaration
     */
    @NotNull
    public ReturningExpr rewriteReturningExpr(@NotNull ReturningExpr returningExpr) {
        Collection<ReturningElem> collection;
        void $this$mapTo$iv$iv;
        Intrinsics.checkParameterIsNotNull(returningExpr, "returningExpr");
        Iterable $this$map$iv = returningExpr.getReturningElems();
        boolean $i$f$map = false;
        Iterable iterable = $this$map$iv;
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
        boolean $i$f$mapTo = false;
        for (Object item$iv$iv : $this$mapTo$iv$iv) {
            void it;
            ReturningElem returningElem = (ReturningElem)item$iv$iv;
            collection = destination$iv$iv;
            boolean bl = false;
            ReturningElem returningElem2 = this.rewriteReturningElem((ReturningElem)it);
            collection.add(returningElem2);
        }
        collection = (List)destination$iv$iv;
        List list = collection;
        return new ReturningExpr(list);
    }

    @NotNull
    public ReturningElem rewriteReturningElem(@NotNull ReturningElem returningElem) {
        Intrinsics.checkParameterIsNotNull(returningElem, "returningElem");
        return new ReturningElem(returningElem.getReturningMapping(), returningElem.getColumnComponent());
    }

    /*
     * WARNING - void declaration
     */
    @NotNull
    public DmlOpList rewriteDataManipulationOperations(@NotNull DmlOpList node) {
        Collection<DataManipulationOperation> collection;
        void $this$mapTo$iv$iv;
        Intrinsics.checkParameterIsNotNull(node, "node");
        Iterable $this$map$iv = node.getOps();
        boolean $i$f$map = false;
        Iterable iterable = $this$map$iv;
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
        boolean $i$f$mapTo = false;
        for (Object item$iv$iv : $this$mapTo$iv$iv) {
            void it;
            DataManipulationOperation dataManipulationOperation = (DataManipulationOperation)item$iv$iv;
            collection = destination$iv$iv;
            boolean bl = false;
            DataManipulationOperation dataManipulationOperation2 = this.rewriteDataManipulationOperation((DataManipulationOperation)it);
            collection.add(dataManipulationOperation2);
        }
        collection = (List)destination$iv$iv;
        List list = collection;
        return new DmlOpList(list);
    }

    @NotNull
    public DataManipulationOperation rewriteDataManipulationOperation(@NotNull DataManipulationOperation node) {
        DataManipulationOperation dataManipulationOperation;
        Intrinsics.checkParameterIsNotNull(node, "node");
        DataManipulationOperation dataManipulationOperation2 = node;
        if (dataManipulationOperation2 instanceof InsertOp) {
            dataManipulationOperation = this.rewriteDataManipulationOperationInsertOp((InsertOp)node);
        } else if (dataManipulationOperation2 instanceof InsertValueOp) {
            dataManipulationOperation = this.rewriteDataManipulationOperationInsertValueOp((InsertValueOp)node);
        } else if (dataManipulationOperation2 instanceof AssignmentOp) {
            dataManipulationOperation = this.rewriteDataManipulationOperationAssignmentOp((AssignmentOp)node);
        } else if (dataManipulationOperation2 instanceof RemoveOp) {
            dataManipulationOperation = this.rewriteDataManipulationOperationRemoveOp((RemoveOp)node);
        } else if (dataManipulationOperation2 instanceof DeleteOp) {
            dataManipulationOperation = this.rewriteDataManipulationOperationDeleteOp();
        } else {
            throw new NoWhenBranchMatchedException();
        }
        return dataManipulationOperation;
    }

    @NotNull
    public DataManipulationOperation rewriteDataManipulationOperationInsertOp(@NotNull InsertOp node) {
        Intrinsics.checkParameterIsNotNull(node, "node");
        return new InsertOp(this.rewriteExprNode(node.getLvalue()), this.rewriteExprNode(node.getValues()));
    }

    @NotNull
    public DataManipulationOperation rewriteDataManipulationOperationInsertValueOp(@NotNull InsertValueOp node) {
        OnConflict onConflict;
        ExprNode exprNode;
        ExprNode exprNode2;
        AstNode it;
        boolean bl;
        boolean bl2;
        ExprNode exprNode3;
        ExprNode exprNode4;
        AstNode astNode;
        Intrinsics.checkParameterIsNotNull(node, "node");
        ExprNode exprNode5 = this.rewriteExprNode(node.getLvalue());
        ExprNode exprNode6 = this.rewriteExprNode(node.getValue());
        ExprNode exprNode7 = node.getPosition();
        if (exprNode7 != null) {
            astNode = exprNode7;
            exprNode4 = exprNode6;
            exprNode3 = exprNode5;
            bl2 = false;
            bl = false;
            it = astNode;
            boolean bl3 = false;
            exprNode2 = this.rewriteExprNode((ExprNode)it);
            exprNode5 = exprNode3;
            exprNode6 = exprNode4;
            exprNode = exprNode2;
        } else {
            exprNode = null;
        }
        OnConflict onConflict2 = node.getOnConflict();
        if (onConflict2 != null) {
            astNode = onConflict2;
            exprNode2 = exprNode;
            exprNode4 = exprNode6;
            exprNode3 = exprNode5;
            bl2 = false;
            bl = false;
            it = astNode;
            boolean bl4 = false;
            OnConflict onConflict3 = this.rewriteOnConflict((OnConflict)it);
            exprNode5 = exprNode3;
            exprNode6 = exprNode4;
            exprNode = exprNode2;
            onConflict = onConflict3;
        } else {
            onConflict = null;
        }
        OnConflict onConflict4 = onConflict;
        ExprNode exprNode8 = exprNode;
        ExprNode exprNode9 = exprNode6;
        ExprNode exprNode10 = exprNode5;
        return new InsertValueOp(exprNode10, exprNode9, exprNode8, onConflict4);
    }

    @NotNull
    public final OnConflict rewriteOnConflict(@NotNull OnConflict node) {
        Intrinsics.checkParameterIsNotNull(node, "node");
        return new OnConflict(this.rewriteExprNode(node.getCondition()), node.getConflictAction());
    }

    @NotNull
    public DataManipulationOperation rewriteDataManipulationOperationAssignmentOp(@NotNull AssignmentOp node) {
        Intrinsics.checkParameterIsNotNull(node, "node");
        return new AssignmentOp(this.rewriteAssignment(node.getAssignment()));
    }

    @NotNull
    public DataManipulationOperation rewriteDataManipulationOperationRemoveOp(@NotNull RemoveOp node) {
        Intrinsics.checkParameterIsNotNull(node, "node");
        return new RemoveOp(this.rewriteExprNode(node.getLvalue()));
    }

    @NotNull
    public DataManipulationOperation rewriteDataManipulationOperationDeleteOp() {
        return DeleteOp.INSTANCE;
    }

    @NotNull
    public Assignment rewriteAssignment(@NotNull Assignment node) {
        Intrinsics.checkParameterIsNotNull(node, "node");
        return new Assignment(this.rewriteExprNode(node.getLvalue()), this.rewriteExprNode(node.getRvalue()));
    }

    @NotNull
    public CreateTable rewriteCreateTable(@NotNull CreateTable node) {
        Intrinsics.checkParameterIsNotNull(node, "node");
        return new CreateTable(node.getTableName(), this.rewriteMetas(node));
    }

    /*
     * WARNING - void declaration
     */
    @NotNull
    public CreateIndex rewriteCreateIndex(@NotNull CreateIndex node) {
        Collection<ExprNode> collection;
        void $this$mapTo$iv$iv;
        void $this$map$iv;
        Intrinsics.checkParameterIsNotNull(node, "node");
        Iterable iterable = node.getKeys();
        String string = node.getTableName();
        boolean $i$f$map = false;
        void var4_5 = $this$map$iv;
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
        boolean $i$f$mapTo = false;
        for (Object item$iv$iv : $this$mapTo$iv$iv) {
            void it;
            ExprNode exprNode = (ExprNode)item$iv$iv;
            collection = destination$iv$iv;
            boolean bl = false;
            ExprNode exprNode2 = this.rewriteExprNode((ExprNode)it);
            collection.add(exprNode2);
        }
        collection = (List)destination$iv$iv;
        MetaContainer metaContainer = this.rewriteMetas(node);
        List list = collection;
        String string2 = string;
        return new CreateIndex(string2, list, metaContainer);
    }

    @NotNull
    public DropTable rewriteDropTable(@NotNull DropTable node) {
        Intrinsics.checkParameterIsNotNull(node, "node");
        return new DropTable(node.getTableName(), this.rewriteMetas(node));
    }

    @NotNull
    public DropIndex rewriteDropIndex(@NotNull DropIndex node) {
        Intrinsics.checkParameterIsNotNull(node, "node");
        ExprNode exprNode = this.rewriteVariableReference(node.getIdentifier());
        if (exprNode == null) {
            throw new TypeCastException("null cannot be cast to non-null type org.partiql.lang.ast.VariableReference");
        }
        return new DropIndex(node.getTableName(), (VariableReference)exprNode, this.rewriteMetas(node));
    }

    /*
     * WARNING - void declaration
     */
    @NotNull
    public Exec rewriteExec(@NotNull Exec node) {
        Collection<ExprNode> collection;
        void $this$mapTo$iv$iv;
        void $this$map$iv;
        Intrinsics.checkParameterIsNotNull(node, "node");
        Iterable iterable = node.getArgs();
        SymbolicName symbolicName = this.rewriteSymbolicName(node.getProcedureName());
        boolean $i$f$map = false;
        void var4_5 = $this$map$iv;
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
        boolean $i$f$mapTo = false;
        for (Object item$iv$iv : $this$mapTo$iv$iv) {
            void it;
            ExprNode exprNode = (ExprNode)item$iv$iv;
            collection = destination$iv$iv;
            boolean bl = false;
            ExprNode exprNode2 = this.rewriteExprNode((ExprNode)it);
            collection.add(exprNode2);
        }
        collection = (List)destination$iv$iv;
        MetaContainer metaContainer = this.rewriteMetas(node);
        List list = collection;
        SymbolicName symbolicName2 = symbolicName;
        return new Exec(symbolicName2, list, metaContainer);
    }

    @NotNull
    public DateTimeType.Date rewriteDate(@NotNull DateTimeType.Date node) {
        Intrinsics.checkParameterIsNotNull(node, "node");
        return new DateTimeType.Date(node.getYear(), node.getMonth(), node.getDay(), this.rewriteMetas(node));
    }

    @NotNull
    public DateTimeType.Time rewriteTime(@NotNull DateTimeType.Time node) {
        Intrinsics.checkParameterIsNotNull(node, "node");
        return new DateTimeType.Time(node.getHour(), node.getMinute(), node.getSecond(), node.getNano(), node.getPrecision(), node.getTz_minutes(), this.rewriteMetas(node));
    }
}

