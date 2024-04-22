/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package org.partiql.lang.ast;

import com.amazon.ion.IonSexp;
import com.amazon.ion.IonString;
import com.amazon.ion.IonSystem;
import com.amazon.ion.IonValue;
import com.amazon.ion.IonWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Pair;
import kotlin.TypeCastException;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.partiql.lang.ast.Assignment;
import org.partiql.lang.ast.AssignmentOp;
import org.partiql.lang.ast.AstSerializer;
import org.partiql.lang.ast.AstSerializerImpl;
import org.partiql.lang.ast.AstSerializerImpl$WhenMappings;
import org.partiql.lang.ast.AstSerializerImpl$writeFromSourceV0$;
import org.partiql.lang.ast.AstSerializerImpl$writeSelect$;
import org.partiql.lang.ast.AstVersion;
import org.partiql.lang.ast.CallAgg;
import org.partiql.lang.ast.CaseSensitivity;
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
import org.partiql.lang.ast.GroupingStrategy;
import org.partiql.lang.ast.InsertOp;
import org.partiql.lang.ast.InsertValueOp;
import org.partiql.lang.ast.JoinOp;
import org.partiql.lang.ast.LetSource;
import org.partiql.lang.ast.LetVariables;
import org.partiql.lang.ast.Literal;
import org.partiql.lang.ast.LiteralMissing;
import org.partiql.lang.ast.Meta;
import org.partiql.lang.ast.MetaContainer;
import org.partiql.lang.ast.NAry;
import org.partiql.lang.ast.NAryOp;
import org.partiql.lang.ast.OrderBy;
import org.partiql.lang.ast.Parameter;
import org.partiql.lang.ast.Path;
import org.partiql.lang.ast.PathComponent;
import org.partiql.lang.ast.PathComponentExpr;
import org.partiql.lang.ast.PathComponentUnpivot;
import org.partiql.lang.ast.PathComponentWildcard;
import org.partiql.lang.ast.RemoveOp;
import org.partiql.lang.ast.ReturningExpr;
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
import org.partiql.lang.ast.SourceLocationMeta;
import org.partiql.lang.ast.Struct;
import org.partiql.lang.ast.StructField;
import org.partiql.lang.ast.SymbolicName;
import org.partiql.lang.ast.Typed;
import org.partiql.lang.ast.TypedOp;
import org.partiql.lang.ast.VariableReference;
import org.partiql.lang.util.IonValueExtensionsKt;
import org.partiql.lang.util.IonWriterContext;
import org.partiql.lang.util.ThreadInterruptUtilsKt;
import org.partiql.lang.util.WhenAsExpressionHelper;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000\u00cc\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0002\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\u0010\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0002J\u0010\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0012H\u0016J\f\u0010\u0013\u001a\u00020\f*\u00020\u0012H\u0002J\"\u0010\u0014\u001a\u00020\u0015*\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u00182\f\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u00150\u001aH\u0002J\"\u0010\u001b\u001a\u00020\u0015*\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u00182\f\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u00150\u001aH\u0002J\"\u0010\u001c\u001a\u00020\u0015*\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u00182\f\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u00150\u001aH\u0002J/\u0010\u001d\u001a\u00020\u0015*\u00020\u00162\b\u0010\u001e\u001a\u0004\u0018\u00010\u001f2\u0017\u0010\u0019\u001a\u0013\u0012\u0004\u0012\u00020\u0016\u0012\u0004\u0012\u00020\u00150 \u00a2\u0006\u0002\b!H\u0002J\u0014\u0010\"\u001a\u00020\u0015*\u00020\u00162\u0006\u0010#\u001a\u00020$H\u0002J\u0014\u0010%\u001a\u00020\u0015*\u00020\u00162\u0006\u0010#\u001a\u00020&H\u0002J\u0014\u0010'\u001a\u00020\u0015*\u00020\u00162\u0006\u0010#\u001a\u00020(H\u0002J\u001a\u0010)\u001a\u00020\u0015*\u00020\u00162\f\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u00150\u001aH\u0002J\u0014\u0010*\u001a\u00020\u0015*\u00020\u00162\u0006\u0010#\u001a\u00020+H\u0002J\u0014\u0010,\u001a\u00020\u0015*\u00020\u00162\u0006\u0010-\u001a\u00020.H\u0002J\u0012\u0010/\u001a\u00020\u0015*\u00020\u00162\u0006\u00100\u001a\u000201J\u0014\u00102\u001a\u00020\u0015*\u00020\u00162\u0006\u0010#\u001a\u000203H\u0002J\u0014\u00104\u001a\u00020\u0015*\u00020\u00162\u0006\u0010#\u001a\u000205H\u0002J\u0014\u00106\u001a\u00020\u0015*\u00020\u00162\u0006\u0010#\u001a\u00020\u0012H\u0002J\u001c\u00107\u001a\u00020\u0015*\u00020\u00162\u0006\u00108\u001a\u00020\u00122\u0006\u00109\u001a\u00020\u0012H\u0002J&\u0010:\u001a\u00020\u0015*\u00020\u00162\u0018\u0010;\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0012\u0012\u0004\u0012\u00020\u00120=0<H\u0002J\u0014\u0010>\u001a\u00020\u0015*\u00020\u00162\u0006\u0010?\u001a\u00020@H\u0002J\u0014\u0010A\u001a\u00020\u0015*\u00020\u00162\u0006\u0010?\u001a\u00020@H\u0002J\u001c\u0010B\u001a\u00020\u0015*\u00020\u00162\u0006\u0010C\u001a\u00020\f2\u0006\u0010D\u001a\u00020EH\u0002J\u0014\u0010F\u001a\u00020\u0015*\u00020\u00162\u0006\u0010#\u001a\u00020GH\u0002J\u0014\u0010H\u001a\u00020\u0015*\u00020\u00162\u0006\u0010#\u001a\u00020IH\u0002J\u0014\u0010J\u001a\u00020\u0015*\u00020\u00162\u0006\u0010#\u001a\u00020KH\u0002J\u0014\u0010L\u001a\u00020\u0015*\u00020\u00162\u0006\u0010#\u001a\u00020MH\u0002J\u0014\u0010N\u001a\u00020\u0015*\u00020\u00162\u0006\u0010#\u001a\u00020OH\u0002J\u0014\u0010P\u001a\u00020\u0015*\u00020\u00162\u0006\u0010Q\u001a\u00020RH\u0002J\u0014\u0010S\u001a\u00020\u0015*\u00020\u00162\u0006\u0010T\u001a\u00020UH\u0002J\u0014\u0010V\u001a\u00020\u0015*\u00020\u00162\u0006\u0010Q\u001a\u00020WH\u0002J\u001a\u0010X\u001a\u00020\u0015*\u00020\u00162\f\u0010Y\u001a\b\u0012\u0004\u0012\u00020Z0<H\u0002J\u0014\u0010[\u001a\u00020\u0015*\u00020\u00162\u0006\u0010#\u001a\u00020\\H\u0002J\u0014\u0010]\u001a\u00020\u0015*\u00020\u00162\u0006\u0010#\u001a\u00020^H\u0002J\u0014\u0010_\u001a\u00020\u0015*\u00020\u00162\u0006\u0010`\u001a\u00020aH\u0002J\u0014\u0010b\u001a\u00020\u0015*\u00020\u00162\u0006\u0010`\u001a\u00020cH\u0002J\u0014\u0010d\u001a\u00020\u0015*\u00020\u00162\u0006\u0010`\u001a\u00020eH\u0002J\u001c\u0010f\u001a\u00020\u0015*\u00020\u00162\u0006\u0010g\u001a\u00020h2\u0006\u0010i\u001a\u00020jH\u0002J\u001c\u0010k\u001a\u00020\u0015*\u00020\u00162\u0006\u0010g\u001a\u00020l2\u0006\u0010i\u001a\u00020jH\u0002J\u0014\u0010m\u001a\u00020\u0015*\u00020\u00162\u0006\u0010g\u001a\u00020nH\u0002J\u001c\u0010o\u001a\u00020\u0015*\u00020\u00162\u0006\u0010g\u001a\u00020p2\u0006\u0010i\u001a\u00020jH\u0002J\u0014\u0010q\u001a\u00020\u0015*\u00020\u00162\u0006\u0010#\u001a\u00020rH\u0002J\u0014\u0010s\u001a\u00020\u0015*\u00020\u00162\u0006\u0010#\u001a\u00020tH\u0002J\u0014\u0010u\u001a\u00020\u0015*\u00020\u00162\u0006\u0010#\u001a\u00020vH\u0002J\u0016\u0010w\u001a\u00020\u0015*\u00020\u00162\b\u0010C\u001a\u0004\u0018\u00010xH\u0002J\u0014\u0010y\u001a\u00020\u0015*\u00020\u00162\u0006\u0010#\u001a\u00020zH\u0002J\u0014\u0010{\u001a\u00020\u0015*\u00020\u00162\u0006\u0010#\u001a\u00020|H\u0002R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n\u00a8\u0006}"}, d2={"Lorg/partiql/lang/ast/AstSerializerImpl;", "Lorg/partiql/lang/ast/AstSerializer;", "astVersion", "Lorg/partiql/lang/ast/AstVersion;", "ion", "Lcom/amazon/ion/IonSystem;", "(Lorg/partiql/lang/ast/AstVersion;Lcom/amazon/ion/IonSystem;)V", "getAstVersion", "()Lorg/partiql/lang/ast/AstVersion;", "getIon", "()Lcom/amazon/ion/IonSystem;", "getOpSymbol", "", "op", "Lorg/partiql/lang/ast/NAryOp;", "serialize", "Lcom/amazon/ion/IonSexp;", "exprNode", "Lorg/partiql/lang/ast/ExprNode;", "getFuncName", "nestAsAlias", "", "Lorg/partiql/lang/util/IonWriterContext;", "variables", "Lorg/partiql/lang/ast/LetVariables;", "block", "Lkotlin/Function0;", "nestAtAlias", "nestByAlias", "writeAsTerm", "metas", "Lorg/partiql/lang/ast/MetaContainer;", "Lkotlin/Function1;", "Lkotlin/ExtensionFunctionType;", "writeCallAgg", "expr", "Lorg/partiql/lang/ast/CallAgg;", "writeCreateIndex", "Lorg/partiql/lang/ast/CreateIndex;", "writeCreateTable", "Lorg/partiql/lang/ast/CreateTable;", "writeDDL", "writeDataManipulation", "Lorg/partiql/lang/ast/DataManipulation;", "writeDataManipulationOperation", "opList", "Lorg/partiql/lang/ast/DmlOpList;", "writeDataType", "dataType", "Lorg/partiql/lang/ast/DataType;", "writeDropIndex", "Lorg/partiql/lang/ast/DropIndex;", "writeDropTable", "Lorg/partiql/lang/ast/DropTable;", "writeExprNode", "writeExprPair", "first", "second", "writeExprPairList", "exprPairList", "", "Lkotlin/Pair;", "writeFromSource", "fromSource", "Lorg/partiql/lang/ast/FromSource;", "writeFromSourceV0", "writeIdentifier", "name", "caseSensitivity", "Lorg/partiql/lang/ast/CaseSensitivity;", "writeLiteral", "Lorg/partiql/lang/ast/Literal;", "writeLiteralMissing", "Lorg/partiql/lang/ast/LiteralMissing;", "writeNAry", "Lorg/partiql/lang/ast/NAry;", "writeParameter", "Lorg/partiql/lang/ast/Parameter;", "writePath", "Lorg/partiql/lang/ast/Path;", "writePathComponentExprV0", "pathComponent", "Lorg/partiql/lang/ast/PathComponentExpr;", "writePathComponentUnpivotV0", "pathComponentUnpivot", "Lorg/partiql/lang/ast/PathComponentUnpivot;", "writePathComponentWildcardV0", "Lorg/partiql/lang/ast/PathComponentWildcard;", "writePathComponentsV0", "components", "Lorg/partiql/lang/ast/PathComponent;", "writeSearchedCase", "Lorg/partiql/lang/ast/SearchedCase;", "writeSelect", "Lorg/partiql/lang/ast/Select;", "writeSelectListItemExpr", "it", "Lorg/partiql/lang/ast/SelectListItemExpr;", "writeSelectListItemProjectAll", "Lorg/partiql/lang/ast/SelectListItemProjectAll;", "writeSelectListItemStar", "Lorg/partiql/lang/ast/SelectListItemStar;", "writeSelectProjection", "projection", "Lorg/partiql/lang/ast/SelectProjection;", "setQuantifier", "Lorg/partiql/lang/ast/SetQuantifier;", "writeSelectProjectionListV0", "Lorg/partiql/lang/ast/SelectProjectionList;", "writeSelectProjectionPivotV0", "Lorg/partiql/lang/ast/SelectProjectionPivot;", "writeSelectProjectionValueV0", "Lorg/partiql/lang/ast/SelectProjectionValue;", "writeSeq", "Lorg/partiql/lang/ast/Seq;", "writeSimpleCase", "Lorg/partiql/lang/ast/SimpleCase;", "writeStruct", "Lorg/partiql/lang/ast/Struct;", "writeSymbolicName", "Lorg/partiql/lang/ast/SymbolicName;", "writeTyped", "Lorg/partiql/lang/ast/Typed;", "writeVariableReference", "Lorg/partiql/lang/ast/VariableReference;", "lang"})
final class AstSerializerImpl
implements AstSerializer {
    @NotNull
    private final AstVersion astVersion;
    @NotNull
    private final IonSystem ion;

    @Override
    @NotNull
    public IonSexp serialize(@NotNull ExprNode exprNode) {
        IonWriter writer;
        Intrinsics.checkParameterIsNotNull(exprNode, "exprNode");
        IonSexp resultSexp = this.ion.newEmptySexp();
        IonWriter ionWriter = writer = this.ion.newWriter(resultSexp);
        Intrinsics.checkExpressionValueIsNotNull(ionWriter, "writer");
        IonWriterContext ionWriterContext = new IonWriterContext(ionWriter);
        boolean bl = false;
        boolean bl2 = false;
        IonWriterContext $this$apply = ionWriterContext;
        boolean bl3 = false;
        this.writeExprNode($this$apply, exprNode);
        IonValue ionValue2 = resultSexp.get(0);
        Intrinsics.checkExpressionValueIsNotNull(ionValue2, "resultSexp[0]");
        return IonValueExtensionsKt.asIonSexp(ionValue2);
    }

    private final void writeAsTerm(@NotNull IonWriterContext $this$writeAsTerm, MetaContainer metas, Function1<? super IonWriterContext, Unit> block) {
        SourceLocationMeta sloc;
        MetaContainer metaContainer = metas;
        Meta meta = metaContainer != null ? metaContainer.find("$source_location") : null;
        if (!(meta instanceof SourceLocationMeta)) {
            meta = null;
        }
        if ((sloc = (SourceLocationMeta)meta) != null) {
            $this$writeAsTerm.sexp((Function1<? super IonWriterContext, Unit>)new Function1<IonWriterContext, Unit>(block, sloc){
                final /* synthetic */ Function1 $block;
                final /* synthetic */ SourceLocationMeta $sloc;

                public final void invoke(@NotNull IonWriterContext $this$sexp) {
                    Intrinsics.checkParameterIsNotNull($this$sexp, "$receiver");
                    $this$sexp.symbol("meta");
                    this.$block.invoke($this$sexp);
                    $this$sexp.struct((Function1<? super IonWriterContext, Unit>)new Function1<IonWriterContext, Unit>(this){
                        final /* synthetic */ writeAsTerm.1 this$0;

                        public final void invoke(@NotNull IonWriterContext $this$struct) {
                            Intrinsics.checkParameterIsNotNull($this$struct, "$receiver");
                            $this$struct.int("line", this.this$0.$sloc.getLineNum());
                            $this$struct.int("column", this.this$0.$sloc.getCharOffset());
                        }
                        {
                            this.this$0 = var1_1;
                            super(1);
                        }
                    });
                }
                {
                    this.$block = function1;
                    this.$sloc = sourceLocationMeta;
                    super(1);
                }
            });
        } else {
            block.invoke($this$writeAsTerm);
        }
    }

    private final void writeExprNode(@NotNull IonWriterContext $this$writeExprNode, ExprNode expr) {
        this.writeAsTerm($this$writeExprNode, expr.getMetas(), (Function1<? super IonWriterContext, Unit>)new Function1<IonWriterContext, Unit>(this, expr){
            final /* synthetic */ AstSerializerImpl this$0;
            final /* synthetic */ ExprNode $expr;

            public final void invoke(@NotNull IonWriterContext $this$writeAsTerm) {
                Intrinsics.checkParameterIsNotNull($this$writeAsTerm, "$receiver");
                ThreadInterruptUtilsKt.checkThreadInterrupted();
                $this$writeAsTerm.sexp((Function1<? super IonWriterContext, Unit>)new Function1<IonWriterContext, Unit>(this){
                    final /* synthetic */ writeExprNode.1 this$0;

                    public final void invoke(@NotNull IonWriterContext $this$sexp) {
                        WhenAsExpressionHelper whenAsExpressionHelper;
                        Intrinsics.checkParameterIsNotNull($this$sexp, "$receiver");
                        ExprNode exprNode = this.this$0.$expr;
                        if (exprNode instanceof Literal) {
                            boolean $i$f$case = false;
                            boolean bl = false;
                            AstSerializerImpl.access$writeLiteral(this.this$0.this$0, $this$sexp, (Literal)this.this$0.$expr);
                            whenAsExpressionHelper = WhenAsExpressionHelper.Companion.getInstance();
                        } else if (exprNode instanceof LiteralMissing) {
                            boolean $i$f$case = false;
                            boolean bl = false;
                            AstSerializerImpl.access$writeLiteralMissing(this.this$0.this$0, $this$sexp, (LiteralMissing)this.this$0.$expr);
                            whenAsExpressionHelper = WhenAsExpressionHelper.Companion.getInstance();
                        } else if (exprNode instanceof VariableReference) {
                            boolean $i$f$case = false;
                            boolean bl = false;
                            AstSerializerImpl.access$writeVariableReference(this.this$0.this$0, $this$sexp, (VariableReference)this.this$0.$expr);
                            whenAsExpressionHelper = WhenAsExpressionHelper.Companion.getInstance();
                        } else if (exprNode instanceof NAry) {
                            boolean $i$f$case = false;
                            boolean bl = false;
                            AstSerializerImpl.access$writeNAry(this.this$0.this$0, $this$sexp, (NAry)this.this$0.$expr);
                            whenAsExpressionHelper = WhenAsExpressionHelper.Companion.getInstance();
                        } else if (exprNode instanceof CallAgg) {
                            boolean $i$f$case = false;
                            boolean bl = false;
                            AstSerializerImpl.access$writeCallAgg(this.this$0.this$0, $this$sexp, (CallAgg)this.this$0.$expr);
                            whenAsExpressionHelper = WhenAsExpressionHelper.Companion.getInstance();
                        } else if (exprNode instanceof Typed) {
                            boolean $i$f$case = false;
                            boolean bl = false;
                            AstSerializerImpl.access$writeTyped(this.this$0.this$0, $this$sexp, (Typed)this.this$0.$expr);
                            whenAsExpressionHelper = WhenAsExpressionHelper.Companion.getInstance();
                        } else if (exprNode instanceof Path) {
                            boolean $i$f$case = false;
                            boolean bl = false;
                            AstSerializerImpl.access$writePath(this.this$0.this$0, $this$sexp, (Path)this.this$0.$expr);
                            whenAsExpressionHelper = WhenAsExpressionHelper.Companion.getInstance();
                        } else if (exprNode instanceof SimpleCase) {
                            boolean $i$f$case = false;
                            boolean bl = false;
                            AstSerializerImpl.access$writeSimpleCase(this.this$0.this$0, $this$sexp, (SimpleCase)this.this$0.$expr);
                            whenAsExpressionHelper = WhenAsExpressionHelper.Companion.getInstance();
                        } else if (exprNode instanceof SearchedCase) {
                            boolean $i$f$case = false;
                            boolean bl = false;
                            AstSerializerImpl.access$writeSearchedCase(this.this$0.this$0, $this$sexp, (SearchedCase)this.this$0.$expr);
                            whenAsExpressionHelper = WhenAsExpressionHelper.Companion.getInstance();
                        } else if (exprNode instanceof Struct) {
                            boolean $i$f$case = false;
                            boolean bl = false;
                            AstSerializerImpl.access$writeStruct(this.this$0.this$0, $this$sexp, (Struct)this.this$0.$expr);
                            whenAsExpressionHelper = WhenAsExpressionHelper.Companion.getInstance();
                        } else if (exprNode instanceof Seq) {
                            boolean $i$f$case = false;
                            boolean bl = false;
                            AstSerializerImpl.access$writeSeq(this.this$0.this$0, $this$sexp, (Seq)this.this$0.$expr);
                            whenAsExpressionHelper = WhenAsExpressionHelper.Companion.getInstance();
                        } else if (exprNode instanceof Select) {
                            boolean $i$f$case = false;
                            boolean bl = false;
                            AstSerializerImpl.access$writeSelect(this.this$0.this$0, $this$sexp, (Select)this.this$0.$expr);
                            whenAsExpressionHelper = WhenAsExpressionHelper.Companion.getInstance();
                        } else if (exprNode instanceof DataManipulation) {
                            boolean $i$f$case = false;
                            boolean bl = false;
                            AstSerializerImpl.access$writeDataManipulation(this.this$0.this$0, $this$sexp, (DataManipulation)this.this$0.$expr);
                            whenAsExpressionHelper = WhenAsExpressionHelper.Companion.getInstance();
                        } else if (exprNode instanceof CreateTable) {
                            boolean $i$f$case = false;
                            boolean bl = false;
                            AstSerializerImpl.access$writeCreateTable(this.this$0.this$0, $this$sexp, (CreateTable)this.this$0.$expr);
                            whenAsExpressionHelper = WhenAsExpressionHelper.Companion.getInstance();
                        } else if (exprNode instanceof CreateIndex) {
                            boolean $i$f$case = false;
                            boolean bl = false;
                            AstSerializerImpl.access$writeCreateIndex(this.this$0.this$0, $this$sexp, (CreateIndex)this.this$0.$expr);
                            whenAsExpressionHelper = WhenAsExpressionHelper.Companion.getInstance();
                        } else if (exprNode instanceof DropTable) {
                            boolean $i$f$case = false;
                            boolean bl = false;
                            AstSerializerImpl.access$writeDropTable(this.this$0.this$0, $this$sexp, (DropTable)this.this$0.$expr);
                            whenAsExpressionHelper = WhenAsExpressionHelper.Companion.getInstance();
                        } else if (exprNode instanceof DropIndex) {
                            boolean $i$f$case = false;
                            boolean bl = false;
                            AstSerializerImpl.access$writeDropIndex(this.this$0.this$0, $this$sexp, (DropIndex)this.this$0.$expr);
                            whenAsExpressionHelper = WhenAsExpressionHelper.Companion.getInstance();
                        } else if (exprNode instanceof Parameter) {
                            boolean $i$f$case = false;
                            boolean bl = false;
                            AstSerializerImpl.access$writeParameter(this.this$0.this$0, $this$sexp, (Parameter)this.this$0.$expr);
                            whenAsExpressionHelper = WhenAsExpressionHelper.Companion.getInstance();
                        } else {
                            if (exprNode instanceof DateTimeType.Date) {
                                throw (Throwable)new UnsupportedOperationException("DATE literals not supported by the V0 AST");
                            }
                            if (exprNode instanceof DateTimeType.Time) {
                                throw (Throwable)new UnsupportedOperationException("TIME literals not supported by the V0 AST");
                            }
                            if (exprNode instanceof Exec) {
                                throw (Throwable)new UnsupportedOperationException("EXEC clause not supported by the V0 AST");
                            }
                            throw new NoWhenBranchMatchedException();
                        }
                        whenAsExpressionHelper.toUnit();
                    }
                    {
                        this.this$0 = var1_1;
                        super(1);
                    }
                });
            }
            {
                this.this$0 = astSerializerImpl;
                this.$expr = exprNode;
                super(1);
            }
        });
    }

    private final void writeLiteral(@NotNull IonWriterContext $this$writeLiteral, Literal expr) {
        Literal literal = expr;
        IonValue ionValue2 = literal.component1();
        $this$writeLiteral.symbol("lit");
        $this$writeLiteral.value(ionValue2);
    }

    private final void writeLiteralMissing(@NotNull IonWriterContext $this$writeLiteralMissing, LiteralMissing expr) {
        LiteralMissing literalMissing = expr;
        $this$writeLiteralMissing.symbol("missing");
    }

    /*
     * WARNING - void declaration
     */
    private final void writeVariableReference(@NotNull IonWriterContext $this$writeVariableReference, VariableReference expr) {
        VariableReference variableReference = expr;
        String string = variableReference.component1();
        CaseSensitivity caseSensitivity = variableReference.component2();
        ScopeQualifier lookup = variableReference.component3();
        switch (AstSerializerImpl$WhenMappings.$EnumSwitchMapping$0[this.astVersion.ordinal()]) {
            case 1: {
                void sensitivity;
                void id;
                Function0<Unit> $fun$writeVarRef$1 = new Function0<Unit>($this$writeVariableReference, (String)id, (CaseSensitivity)sensitivity){
                    final /* synthetic */ IonWriterContext $this_writeVariableReference;
                    final /* synthetic */ String $id;
                    final /* synthetic */ CaseSensitivity $sensitivity;

                    public final void invoke() {
                        this.$this_writeVariableReference.symbol("id");
                        this.$this_writeVariableReference.symbol(this.$id);
                        this.$this_writeVariableReference.symbol(this.$sensitivity.toSymbol());
                    }
                    {
                        this.$this_writeVariableReference = ionWriterContext;
                        this.$id = string;
                        this.$sensitivity = caseSensitivity;
                        super(0);
                    }
                };
                if (lookup == ScopeQualifier.LEXICAL) {
                    $this$writeVariableReference.symbol("@");
                    $this$writeVariableReference.sexp((Function1<? super IonWriterContext, Unit>)new Function1<IonWriterContext, Unit>($fun$writeVarRef$1){
                        final /* synthetic */ writeVariableReference.1 $writeVarRef$1;

                        public final void invoke(@NotNull IonWriterContext $this$sexp) {
                            Intrinsics.checkParameterIsNotNull($this$sexp, "$receiver");
                            this.$writeVarRef$1.invoke();
                        }
                        {
                            this.$writeVarRef$1 = var1_1;
                            super(1);
                        }
                    });
                    break;
                }
                $fun$writeVarRef$1.invoke();
            }
        }
    }

    /*
     * WARNING - void declaration
     */
    private final void writeCallAgg(@NotNull IonWriterContext $this$writeCallAgg, CallAgg expr) {
        Object object = expr;
        ExprNode exprNode = ((CallAgg)object).component1();
        SetQuantifier setQuantifier = ((CallAgg)object).component2();
        ExprNode exprNode2 = ((CallAgg)object).component3();
        MetaContainer metas = ((CallAgg)object).component4();
        if (metas.hasMeta("$is_count_star")) {
            $this$writeCallAgg.symbol("call_agg_wildcard");
            $this$writeCallAgg.symbol("count");
        } else {
            void arg;
            $this$writeCallAgg.symbol("call_agg");
            switch (AstSerializerImpl$WhenMappings.$EnumSwitchMapping$1[this.astVersion.ordinal()]) {
                case 1: {
                    void setQuantifier2;
                    void funcExpr;
                    $this$writeCallAgg.symbol(this.getFuncName((ExprNode)funcExpr));
                    object = setQuantifier2.toString();
                    IonWriterContext ionWriterContext = $this$writeCallAgg;
                    boolean bl = false;
                    Object object2 = object;
                    if (object2 == null) {
                        throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                    }
                    String string = ((String)object2).toLowerCase();
                    Intrinsics.checkExpressionValueIsNotNull(string, "(this as java.lang.String).toLowerCase()");
                    String string2 = string;
                    ionWriterContext.symbol(string2);
                }
            }
            this.writeExprNode($this$writeCallAgg, (ExprNode)arg);
        }
    }

    /*
     * WARNING - void declaration
     */
    private final void writeTyped(@NotNull IonWriterContext $this$writeTyped, Typed expr) {
        void exp;
        void op;
        Typed typed = expr;
        TypedOp typedOp = typed.component1();
        ExprNode exprNode = typed.component2();
        DataType dataType = typed.component3();
        $this$writeTyped.symbol(op.getText());
        this.writeExprNode($this$writeTyped, (ExprNode)exp);
        this.writeDataType($this$writeTyped, dataType);
    }

    /*
     * WARNING - void declaration
     */
    private final void writeStruct(@NotNull IonWriterContext $this$writeStruct, Struct expr) {
        Struct struct = expr;
        List<StructField> fields = struct.component1();
        $this$writeStruct.symbol("struct");
        Iterable $this$forEach$iv = fields;
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            StructField it = (StructField)element$iv;
            boolean bl = false;
            StructField structField = it;
            ExprNode exprNode = structField.component1();
            ExprNode valueExpr = structField.component2();
            switch (AstSerializerImpl$WhenMappings.$EnumSwitchMapping$2[this.astVersion.ordinal()]) {
                case 1: {
                    void name;
                    this.writeExprNode($this$writeStruct, (ExprNode)name);
                    this.writeExprNode($this$writeStruct, valueExpr);
                }
            }
        }
    }

    public final void writeDataType(@NotNull IonWriterContext $this$writeDataType, @NotNull DataType dataType) {
        Intrinsics.checkParameterIsNotNull($this$writeDataType, "$this$writeDataType");
        Intrinsics.checkParameterIsNotNull(dataType, "dataType");
        this.writeAsTerm($this$writeDataType, dataType.getMetas(), (Function1<? super IonWriterContext, Unit>)new Function1<IonWriterContext, Unit>(this, dataType){
            final /* synthetic */ AstSerializerImpl this$0;
            final /* synthetic */ DataType $dataType;

            public final void invoke(@NotNull IonWriterContext $this$writeAsTerm) {
                Intrinsics.checkParameterIsNotNull($this$writeAsTerm, "$receiver");
                $this$writeAsTerm.sexp((Function1<? super IonWriterContext, Unit>)new Function1<IonWriterContext, Unit>(this){
                    final /* synthetic */ writeDataType.1 this$0;

                    public final void invoke(@NotNull IonWriterContext $this$sexp) {
                        Intrinsics.checkParameterIsNotNull($this$sexp, "$receiver");
                        switch (AstSerializerImpl$WhenMappings.$EnumSwitchMapping$3[this.this$0.this$0.getAstVersion().ordinal()]) {
                            case 1: {
                                $this$sexp.symbol("type");
                                $this$sexp.symbol(this.this$0.$dataType.getSqlDataType().getTypeName());
                                Iterable $this$forEach$iv = this.this$0.$dataType.getArgs();
                                boolean $i$f$forEach = false;
                                for (T element$iv : $this$forEach$iv) {
                                    long it = ((Number)element$iv).longValue();
                                    boolean bl = false;
                                    $this$sexp.int(it);
                                }
                                break;
                            }
                        }
                    }
                    {
                        this.this$0 = var1_1;
                        super(1);
                    }
                });
            }
            {
                this.this$0 = astSerializerImpl;
                this.$dataType = dataType;
                super(1);
            }
        });
    }

    /*
     * WARNING - void declaration
     */
    private final void writeSeq(@NotNull IonWriterContext $this$writeSeq, Seq expr) {
        void type;
        Seq seq2 = expr;
        SeqType seqType = seq2.component1();
        List<ExprNode> items = seq2.component2();
        $this$writeSeq.symbol(type.getTypeName());
        Iterable $this$forEach$iv = items;
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            ExprNode it = (ExprNode)element$iv;
            boolean bl = false;
            this.writeExprNode($this$writeSeq, it);
        }
    }

    /*
     * WARNING - void declaration
     */
    private final void writeSelect(@NotNull IonWriterContext $this$writeSelect, Select expr) {
        block5: {
            void having2;
            void groupBy2;
            Select it;
            boolean bl;
            boolean bl2;
            void where2;
            void from2;
            void setQuantifier;
            void projection;
            void fromLet2;
            void orderBy;
            ExprNode exprNode = expr;
            SetQuantifier setQuantifier2 = exprNode.component1();
            SelectProjection selectProjection = exprNode.component2();
            FromSource fromSource = exprNode.component3();
            LetSource letSource = exprNode.component4();
            ExprNode exprNode2 = exprNode.component5();
            GroupBy groupBy3 = exprNode.component6();
            ExprNode exprNode3 = exprNode.component7();
            OrderBy orderBy2 = exprNode.component8();
            ExprNode limit2 = exprNode.component9();
            if (orderBy != null) {
                throw (Throwable)new UnsupportedOperationException("ORDER BY clause is not supported by the V0 AST");
            }
            if (fromLet2 != null) {
                throw (Throwable)new UnsupportedOperationException("LET clause is not supported by the V0 AST");
            }
            this.writeSelectProjection($this$writeSelect, (SelectProjection)projection, (SetQuantifier)setQuantifier);
            $this$writeSelect.sexp((Function1<? super IonWriterContext, Unit>)new Function1<IonWriterContext, Unit>(this, (FromSource)from2){
                final /* synthetic */ AstSerializerImpl this$0;
                final /* synthetic */ FromSource $from;

                public final void invoke(@NotNull IonWriterContext $this$sexp) {
                    Intrinsics.checkParameterIsNotNull($this$sexp, "$receiver");
                    $this$sexp.symbol("from");
                    AstSerializerImpl.access$writeFromSource(this.this$0, $this$sexp, this.$from);
                }
                {
                    this.this$0 = astSerializerImpl;
                    this.$from = fromSource;
                    super(1);
                }
            });
            void v0 = where2;
            if (v0 != null) {
                exprNode = v0;
                bl2 = false;
                bl = false;
                it = exprNode;
                boolean bl3 = false;
                $this$writeSelect.sexp((Function1<? super IonWriterContext, Unit>)new Function1<IonWriterContext, Unit>((ExprNode)it, this, $this$writeSelect){
                    final /* synthetic */ ExprNode $it;
                    final /* synthetic */ AstSerializerImpl this$0;
                    final /* synthetic */ IonWriterContext $this_writeSelect$inlined;
                    {
                        this.$it = exprNode;
                        this.this$0 = astSerializerImpl;
                        this.$this_writeSelect$inlined = ionWriterContext;
                        super(1);
                    }

                    public final void invoke(@NotNull IonWriterContext $this$sexp) {
                        Intrinsics.checkParameterIsNotNull($this$sexp, "$receiver");
                        $this$sexp.symbol("where");
                        AstSerializerImpl.access$writeExprNode(this.this$0, $this$sexp, this.$it);
                    }
                });
            }
            void v1 = groupBy2;
            if (v1 != null) {
                void groupByItems;
                void grouping;
                exprNode = v1;
                bl2 = false;
                bl = false;
                it = exprNode;
                boolean bl4 = false;
                Select select = it;
                GroupingStrategy groupingStrategy = ((GroupBy)((Object)select)).component1();
                List<GroupByItem> list = ((GroupBy)((Object)select)).component2();
                SymbolicName groupName = ((GroupBy)((Object)select)).component3();
                $this$writeSelect.sexp((Function1<? super IonWriterContext, Unit>)new Function1<IonWriterContext, Unit>((GroupingStrategy)grouping, (List)groupByItems, groupName, this, $this$writeSelect){
                    final /* synthetic */ GroupingStrategy $grouping;
                    final /* synthetic */ List $groupByItems;
                    final /* synthetic */ SymbolicName $groupName;
                    final /* synthetic */ AstSerializerImpl this$0;
                    final /* synthetic */ IonWriterContext $this_writeSelect$inlined;
                    {
                        this.$grouping = groupingStrategy;
                        this.$groupByItems = list;
                        this.$groupName = symbolicName;
                        this.this$0 = astSerializerImpl;
                        this.$this_writeSelect$inlined = ionWriterContext;
                        super(1);
                    }

                    public final void invoke(@NotNull IonWriterContext $this$sexp) {
                        block4: {
                            String string;
                            Intrinsics.checkParameterIsNotNull($this$sexp, "$receiver");
                            switch (AstSerializerImpl$WhenMappings.$EnumSwitchMapping$4[this.$grouping.ordinal()]) {
                                case 1: {
                                    string = "group";
                                    break;
                                }
                                case 2: {
                                    string = "group_partial";
                                    break;
                                }
                                default: {
                                    throw new NoWhenBranchMatchedException();
                                }
                            }
                            $this$sexp.symbol(string);
                            $this$sexp.sexp((Function1<? super IonWriterContext, Unit>)new Function1<IonWriterContext, Unit>(this){
                                final /* synthetic */ writeSelect$$inlined$let$lambda$2 this$0;
                                {
                                    this.this$0 = var1_1;
                                    super(1);
                                }

                                /*
                                 * WARNING - void declaration
                                 */
                                public final void invoke(@NotNull IonWriterContext $this$sexp) {
                                    Intrinsics.checkParameterIsNotNull($this$sexp, "$receiver");
                                    $this$sexp.symbol("by");
                                    Iterable $this$forEach$iv = this.this$0.$groupByItems;
                                    boolean $i$f$forEach = false;
                                    for (T element$iv : $this$forEach$iv) {
                                        void itemExpr;
                                        GroupByItem gbi = (GroupByItem)element$iv;
                                        boolean bl = false;
                                        GroupByItem groupByItem = gbi;
                                        ExprNode exprNode = groupByItem.component1();
                                        SymbolicName asName = groupByItem.component2();
                                        if (asName != null) {
                                            AstSerializerImpl.access$writeAsTerm(this.this$0.this$0, $this$sexp, asName.getMetas(), new Function1<IonWriterContext, Unit>(asName, (ExprNode)itemExpr, this, $this$sexp){
                                                final /* synthetic */ SymbolicName $asName;
                                                final /* synthetic */ ExprNode $itemExpr;
                                                final /* synthetic */ writeSelect$$inlined$let$lambda$2$1 this$0;
                                                final /* synthetic */ IonWriterContext $this_sexp$inlined;
                                                {
                                                    this.$asName = symbolicName;
                                                    this.$itemExpr = exprNode;
                                                    this.this$0 = var3_3;
                                                    this.$this_sexp$inlined = ionWriterContext;
                                                    super(1);
                                                }

                                                public final void invoke(@NotNull IonWriterContext $this$writeAsTerm) {
                                                    Intrinsics.checkParameterIsNotNull($this$writeAsTerm, "$receiver");
                                                    $this$writeAsTerm.sexp((Function1<? super IonWriterContext, Unit>)new Function1<IonWriterContext, Unit>(this){
                                                        final /* synthetic */ writeSelect$$inlined$let$lambda$2$1$1 this$0;
                                                        {
                                                            this.this$0 = var1_1;
                                                            super(1);
                                                        }

                                                        public final void invoke(@NotNull IonWriterContext $this$sexp) {
                                                            Intrinsics.checkParameterIsNotNull($this$sexp, "$receiver");
                                                            $this$sexp.symbol("as");
                                                            $this$sexp.symbol(this.this$0.$asName.getName());
                                                            AstSerializerImpl.access$writeExprNode(this.this$0.this$0.this$0.this$0, $this$sexp, this.this$0.$itemExpr);
                                                        }
                                                    });
                                                }
                                            });
                                            continue;
                                        }
                                        AstSerializerImpl.access$writeExprNode(this.this$0.this$0, $this$sexp, (ExprNode)itemExpr);
                                    }
                                }
                            });
                            SymbolicName symbolicName = this.$groupName;
                            if (symbolicName == null) break block4;
                            SymbolicName symbolicName2 = symbolicName;
                            boolean bl = false;
                            boolean bl2 = false;
                            SymbolicName gn = symbolicName2;
                            boolean bl3 = false;
                            AstSerializerImpl.access$writeAsTerm(this.this$0, $this$sexp, gn.getMetas(), new Function1<IonWriterContext, Unit>(gn){
                                final /* synthetic */ SymbolicName $gn;
                                {
                                    this.$gn = symbolicName;
                                    super(1);
                                }

                                public final void invoke(@NotNull IonWriterContext $this$writeAsTerm) {
                                    Intrinsics.checkParameterIsNotNull($this$writeAsTerm, "$receiver");
                                    $this$writeAsTerm.sexp((Function1<? super IonWriterContext, Unit>)new Function1<IonWriterContext, Unit>(this){
                                        final /* synthetic */ writeSelect$$inlined$let$lambda$2$2 this$0;
                                        {
                                            this.this$0 = var1_1;
                                            super(1);
                                        }

                                        public final void invoke(@NotNull IonWriterContext $this$sexp) {
                                            Intrinsics.checkParameterIsNotNull($this$sexp, "$receiver");
                                            $this$sexp.symbol("name");
                                            $this$sexp.symbol(this.this$0.$gn.getName());
                                        }
                                    });
                                }
                            });
                        }
                    }
                });
            }
            void v2 = having2;
            if (v2 != null) {
                exprNode = v2;
                bl2 = false;
                bl = false;
                it = exprNode;
                boolean bl5 = false;
                $this$writeSelect.sexp((Function1<? super IonWriterContext, Unit>)new Function1<IonWriterContext, Unit>((ExprNode)it, this, $this$writeSelect){
                    final /* synthetic */ ExprNode $it;
                    final /* synthetic */ AstSerializerImpl this$0;
                    final /* synthetic */ IonWriterContext $this_writeSelect$inlined;
                    {
                        this.$it = exprNode;
                        this.this$0 = astSerializerImpl;
                        this.$this_writeSelect$inlined = ionWriterContext;
                        super(1);
                    }

                    public final void invoke(@NotNull IonWriterContext $this$sexp) {
                        Intrinsics.checkParameterIsNotNull($this$sexp, "$receiver");
                        $this$sexp.symbol("having");
                        AstSerializerImpl.access$writeExprNode(this.this$0, $this$sexp, this.$it);
                    }
                });
            }
            ExprNode exprNode4 = limit2;
            if (exprNode4 == null) break block5;
            exprNode = exprNode4;
            bl2 = false;
            bl = false;
            it = exprNode;
            boolean bl6 = false;
            $this$writeSelect.sexp((Function1<? super IonWriterContext, Unit>)new Function1<IonWriterContext, Unit>(this, $this$writeSelect, limit2){
                final /* synthetic */ AstSerializerImpl this$0;
                final /* synthetic */ IonWriterContext $this_writeSelect$inlined;
                final /* synthetic */ ExprNode $limit$inlined;
                {
                    this.this$0 = astSerializerImpl;
                    this.$this_writeSelect$inlined = ionWriterContext;
                    this.$limit$inlined = exprNode;
                    super(1);
                }

                public final void invoke(@NotNull IonWriterContext $this$sexp) {
                    Intrinsics.checkParameterIsNotNull($this$sexp, "$receiver");
                    $this$sexp.symbol("limit");
                    AstSerializerImpl.access$writeExprNode(this.this$0, $this$sexp, this.$limit$inlined);
                }
            });
        }
    }

    /*
     * WARNING - void declaration
     */
    private final void writeDataManipulation(@NotNull IonWriterContext $this$writeDataManipulation, DataManipulation expr) {
        block5: {
            void where2;
            DataManipulation it;
            boolean bl;
            boolean bl2;
            void from2;
            DataManipulation dataManipulation = expr;
            DmlOpList dmlOpList = dataManipulation.component1();
            FromSource fromSource = dataManipulation.component2();
            ExprNode exprNode = dataManipulation.component3();
            ReturningExpr returning2 = dataManipulation.component4();
            if (returning2 != null) {
                throw (Throwable)new UnsupportedOperationException("RETURNING clause not supported by V0 AST.");
            }
            $this$writeDataManipulation.symbol("dml");
            switch (AstSerializerImpl$WhenMappings.$EnumSwitchMapping$5[this.astVersion.ordinal()]) {
                case 1: {
                    void dmlOp;
                    this.writeDataManipulationOperation($this$writeDataManipulation, (DmlOpList)dmlOp);
                }
            }
            void v0 = from2;
            if (v0 != null) {
                dataManipulation = v0;
                bl2 = false;
                bl = false;
                it = dataManipulation;
                boolean bl3 = false;
                $this$writeDataManipulation.sexp((Function1<? super IonWriterContext, Unit>)new Function1<IonWriterContext, Unit>((FromSource)((Object)it), this, $this$writeDataManipulation){
                    final /* synthetic */ FromSource $it;
                    final /* synthetic */ AstSerializerImpl this$0;
                    final /* synthetic */ IonWriterContext $this_writeDataManipulation$inlined;
                    {
                        this.$it = fromSource;
                        this.this$0 = astSerializerImpl;
                        this.$this_writeDataManipulation$inlined = ionWriterContext;
                        super(1);
                    }

                    public final void invoke(@NotNull IonWriterContext $this$sexp) {
                        Intrinsics.checkParameterIsNotNull($this$sexp, "$receiver");
                        $this$sexp.symbol("from");
                        AstSerializerImpl.access$writeFromSource(this.this$0, $this$sexp, this.$it);
                    }
                });
            }
            void v1 = where2;
            if (v1 == null) break block5;
            dataManipulation = v1;
            bl2 = false;
            bl = false;
            it = dataManipulation;
            boolean bl4 = false;
            $this$writeDataManipulation.sexp((Function1<? super IonWriterContext, Unit>)new Function1<IonWriterContext, Unit>((ExprNode)it, this, $this$writeDataManipulation){
                final /* synthetic */ ExprNode $it;
                final /* synthetic */ AstSerializerImpl this$0;
                final /* synthetic */ IonWriterContext $this_writeDataManipulation$inlined;
                {
                    this.$it = exprNode;
                    this.this$0 = astSerializerImpl;
                    this.$this_writeDataManipulation$inlined = ionWriterContext;
                    super(1);
                }

                public final void invoke(@NotNull IonWriterContext $this$sexp) {
                    Intrinsics.checkParameterIsNotNull($this$sexp, "$receiver");
                    $this$sexp.symbol("where");
                    AstSerializerImpl.access$writeExprNode(this.this$0, $this$sexp, this.$it);
                }
            });
        }
    }

    private final void writeDataManipulationOperation(@NotNull IonWriterContext $this$writeDataManipulationOperation, DmlOpList opList) {
        boolean isSetOnly;
        boolean bl;
        boolean bl2;
        block8: {
            Iterable $this$any$iv = opList.getOps();
            boolean $i$f$any = false;
            if ($this$any$iv instanceof Collection && ((Collection)$this$any$iv).isEmpty()) {
                bl2 = false;
            } else {
                for (Object element$iv : $this$any$iv) {
                    DataManipulationOperation it = (DataManipulationOperation)element$iv;
                    boolean bl3 = false;
                    if (!(!(it instanceof AssignmentOp))) continue;
                    bl2 = true;
                    break block8;
                }
                bl2 = false;
            }
        }
        if (!bl2) {
            if (opList.getOps().size() != 1) {
                throw (Throwable)new UnsupportedOperationException("A single DML statement with multiple operations other than SET cannot be represented with the V0 AST");
            }
            bl = true;
        } else {
            bl = isSetOnly = false;
        }
        if (!isSetOnly) {
            DataManipulationOperation dmlOp = CollectionsKt.first(opList.getOps());
            $this$writeDataManipulationOperation.sexp((Function1<? super IonWriterContext, Unit>)new Function1<IonWriterContext, Unit>(this, dmlOp){
                final /* synthetic */ AstSerializerImpl this$0;
                final /* synthetic */ DataManipulationOperation $dmlOp;

                public final void invoke(@NotNull IonWriterContext $this$sexp) {
                    boolean bl;
                    Unit unit;
                    Object object;
                    block11: {
                        block12: {
                            block10: {
                                Intrinsics.checkParameterIsNotNull($this$sexp, "$receiver");
                                $this$sexp.symbol(this.$dmlOp.getName());
                                object = this.$dmlOp;
                                if (!(object instanceof InsertOp)) break block10;
                                AstSerializerImpl.access$writeExprNode(this.this$0, $this$sexp, ((InsertOp)this.$dmlOp).getLvalue());
                                AstSerializerImpl.access$writeExprNode(this.this$0, $this$sexp, ((InsertOp)this.$dmlOp).getValues());
                                unit = Unit.INSTANCE;
                                break block11;
                            }
                            if (!(object instanceof InsertValueOp)) break block12;
                            AstSerializerImpl.access$writeExprNode(this.this$0, $this$sexp, ((InsertValueOp)this.$dmlOp).getLvalue());
                            AstSerializerImpl.access$writeExprNode(this.this$0, $this$sexp, ((InsertValueOp)this.$dmlOp).getValue());
                            switch (AstSerializerImpl$WhenMappings.$EnumSwitchMapping$6[this.this$0.getAstVersion().ordinal()]) {
                                case 1: {
                                    ExprNode exprNode = ((InsertValueOp)this.$dmlOp).getPosition();
                                    if (exprNode != null) {
                                        ExprNode exprNode2 = exprNode;
                                        bl = false;
                                        boolean bl2 = false;
                                        ExprNode it = exprNode2;
                                        boolean bl3 = false;
                                        AstSerializerImpl.access$writeExprNode(this.this$0, $this$sexp, it);
                                        unit = Unit.INSTANCE;
                                    } else {
                                        unit = null;
                                    }
                                    break block11;
                                }
                                default: {
                                    throw new NoWhenBranchMatchedException();
                                }
                            }
                        }
                        if (object instanceof RemoveOp) {
                            AstSerializerImpl.access$writeExprNode(this.this$0, $this$sexp, ((RemoveOp)this.$dmlOp).getLvalue());
                            unit = Unit.INSTANCE;
                        } else if (object instanceof DeleteOp) {
                            unit = Unit.INSTANCE;
                        } else {
                            if (object instanceof AssignmentOp) {
                                String string = "should be handled by else block below";
                                boolean bl4 = false;
                                throw (Throwable)new IllegalStateException(string.toString());
                            }
                            throw new NoWhenBranchMatchedException();
                        }
                    }
                    object = unit;
                    boolean bl5 = false;
                    bl = false;
                    Object it = object;
                    boolean bl6 = false;
                }
                {
                    this.this$0 = astSerializerImpl;
                    this.$dmlOp = dataManipulationOperation;
                    super(1);
                }
            });
        } else {
            $this$writeDataManipulationOperation.sexp((Function1<? super IonWriterContext, Unit>)new Function1<IonWriterContext, Unit>(this, opList){
                final /* synthetic */ AstSerializerImpl this$0;
                final /* synthetic */ DmlOpList $opList;

                /*
                 * WARNING - void declaration
                 */
                public final void invoke(@NotNull IonWriterContext $this$sexp) {
                    void $this$mapTo$iv$iv;
                    Intrinsics.checkParameterIsNotNull($this$sexp, "$receiver");
                    $this$sexp.symbol("set");
                    Iterable $this$map$iv = this.$opList.getOps();
                    boolean $i$f$map = false;
                    Iterable iterable = $this$map$iv;
                    Collection destination$iv$iv = new ArrayList<E>(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
                    boolean $i$f$mapTo = false;
                    for (T item$iv$iv : $this$mapTo$iv$iv) {
                        void it;
                        DataManipulationOperation dataManipulationOperation = (DataManipulationOperation)item$iv$iv;
                        Collection collection = destination$iv$iv;
                        boolean bl = false;
                        void v0 = it;
                        if (v0 == null) {
                            throw new TypeCastException("null cannot be cast to non-null type org.partiql.lang.ast.AssignmentOp");
                        }
                        AssignmentOp assignmentOp = (AssignmentOp)v0;
                        collection.add(assignmentOp);
                    }
                    Iterable $this$forEach$iv = (List)destination$iv$iv;
                    boolean $i$f$forEach = false;
                    for (E element$iv : $this$forEach$iv) {
                        AssignmentOp it = (AssignmentOp)element$iv;
                        boolean bl = false;
                        Assignment assignment = it.getAssignment();
                        $this$sexp.sexp((Function1<? super IonWriterContext, Unit>)new Function1<IonWriterContext, Unit>(assignment, this, $this$sexp){
                            final /* synthetic */ Assignment $assignment;
                            final /* synthetic */ writeDataManipulationOperation.2 this$0;
                            final /* synthetic */ IonWriterContext $this_sexp$inlined;
                            {
                                this.$assignment = assignment;
                                this.this$0 = var2_2;
                                this.$this_sexp$inlined = ionWriterContext;
                                super(1);
                            }

                            public final void invoke(@NotNull IonWriterContext $this$sexp) {
                                Intrinsics.checkParameterIsNotNull($this$sexp, "$receiver");
                                $this$sexp.symbol("assignment");
                                AstSerializerImpl.access$writeExprNode(this.this$0.this$0, $this$sexp, this.$assignment.getLvalue());
                                AstSerializerImpl.access$writeExprNode(this.this$0.this$0, $this$sexp, this.$assignment.getRvalue());
                            }
                        });
                    }
                }
                {
                    this.this$0 = astSerializerImpl;
                    this.$opList = dmlOpList;
                    super(1);
                }
            });
        }
    }

    private final void writeSelectProjection(@NotNull IonWriterContext $this$writeSelectProjection, SelectProjection projection, SetQuantifier setQuantifier) {
        switch (AstSerializerImpl$WhenMappings.$EnumSwitchMapping$7[this.astVersion.ordinal()]) {
            case 1: {
                WhenAsExpressionHelper whenAsExpressionHelper;
                SelectProjection selectProjection = projection;
                if (selectProjection instanceof SelectProjectionValue) {
                    boolean $i$f$case = false;
                    boolean bl = false;
                    this.writeSelectProjectionValueV0($this$writeSelectProjection, (SelectProjectionValue)projection, setQuantifier);
                    whenAsExpressionHelper = WhenAsExpressionHelper.Companion.getInstance();
                } else if (selectProjection instanceof SelectProjectionPivot) {
                    boolean $i$f$case = false;
                    boolean bl = false;
                    this.writeSelectProjectionPivotV0($this$writeSelectProjection, (SelectProjectionPivot)projection);
                    whenAsExpressionHelper = WhenAsExpressionHelper.Companion.getInstance();
                } else if (selectProjection instanceof SelectProjectionList) {
                    boolean $i$f$case = false;
                    boolean bl = false;
                    this.writeSelectProjectionListV0($this$writeSelectProjection, (SelectProjectionList)projection, setQuantifier);
                    whenAsExpressionHelper = WhenAsExpressionHelper.Companion.getInstance();
                } else {
                    throw new NoWhenBranchMatchedException();
                }
                whenAsExpressionHelper.toUnit();
            }
        }
    }

    private final void writeSelectProjectionValueV0(@NotNull IonWriterContext $this$writeSelectProjectionValueV0, SelectProjectionValue projection, SetQuantifier setQuantifier) {
        SelectProjectionValue selectProjectionValue = projection;
        ExprNode valueExpr = selectProjectionValue.component1();
        $this$writeSelectProjectionValueV0.symbol("select");
        $this$writeSelectProjectionValueV0.sexp((Function1<? super IonWriterContext, Unit>)new Function1<IonWriterContext, Unit>(this, setQuantifier, valueExpr){
            final /* synthetic */ AstSerializerImpl this$0;
            final /* synthetic */ SetQuantifier $setQuantifier;
            final /* synthetic */ ExprNode $valueExpr;

            public final void invoke(@NotNull IonWriterContext $this$sexp) {
                String string;
                Intrinsics.checkParameterIsNotNull($this$sexp, "$receiver");
                switch (AstSerializerImpl$WhenMappings.$EnumSwitchMapping$8[this.$setQuantifier.ordinal()]) {
                    case 1: {
                        string = "project";
                        break;
                    }
                    case 2: {
                        string = "project_distinct";
                        break;
                    }
                    default: {
                        throw new NoWhenBranchMatchedException();
                    }
                }
                $this$sexp.symbol(string);
                $this$sexp.sexp((Function1<? super IonWriterContext, Unit>)new Function1<IonWriterContext, Unit>(this){
                    final /* synthetic */ writeSelectProjectionValueV0.1 this$0;

                    public final void invoke(@NotNull IonWriterContext $this$sexp) {
                        Intrinsics.checkParameterIsNotNull($this$sexp, "$receiver");
                        $this$sexp.symbol("value");
                        AstSerializerImpl.access$writeExprNode(this.this$0.this$0, $this$sexp, this.this$0.$valueExpr);
                    }
                    {
                        this.this$0 = var1_1;
                        super(1);
                    }
                });
            }
            {
                this.this$0 = astSerializerImpl;
                this.$setQuantifier = setQuantifier;
                this.$valueExpr = exprNode;
                super(1);
            }
        });
    }

    /*
     * WARNING - void declaration
     */
    private final void writeSelectProjectionPivotV0(@NotNull IonWriterContext $this$writeSelectProjectionPivotV0, SelectProjectionPivot projection) {
        void asExpr;
        SelectProjectionPivot selectProjectionPivot = projection;
        ExprNode exprNode = selectProjectionPivot.component1();
        ExprNode atExpr = selectProjectionPivot.component2();
        $this$writeSelectProjectionPivotV0.symbol("pivot");
        $this$writeSelectProjectionPivotV0.sexp((Function1<? super IonWriterContext, Unit>)new Function1<IonWriterContext, Unit>(this, (ExprNode)asExpr, atExpr){
            final /* synthetic */ AstSerializerImpl this$0;
            final /* synthetic */ ExprNode $asExpr;
            final /* synthetic */ ExprNode $atExpr;

            public final void invoke(@NotNull IonWriterContext $this$sexp) {
                Intrinsics.checkParameterIsNotNull($this$sexp, "$receiver");
                $this$sexp.symbol("member");
                AstSerializerImpl.access$writeExprNode(this.this$0, $this$sexp, this.$asExpr);
                AstSerializerImpl.access$writeExprNode(this.this$0, $this$sexp, this.$atExpr);
            }
            {
                this.this$0 = astSerializerImpl;
                this.$asExpr = exprNode;
                this.$atExpr = exprNode2;
                super(1);
            }
        });
    }

    private final void writeSelectProjectionListV0(@NotNull IonWriterContext $this$writeSelectProjectionListV0, SelectProjectionList projection, SetQuantifier setQuantifier) {
        SelectProjectionList selectProjectionList = projection;
        List<SelectListItem> items = selectProjectionList.component1();
        $this$writeSelectProjectionListV0.symbol("select");
        $this$writeSelectProjectionListV0.sexp((Function1<? super IonWriterContext, Unit>)new Function1<IonWriterContext, Unit>(this, setQuantifier, items){
            final /* synthetic */ AstSerializerImpl this$0;
            final /* synthetic */ SetQuantifier $setQuantifier;
            final /* synthetic */ List $items;

            public final void invoke(@NotNull IonWriterContext $this$sexp) {
                String string;
                Intrinsics.checkParameterIsNotNull($this$sexp, "$receiver");
                switch (AstSerializerImpl$WhenMappings.$EnumSwitchMapping$9[this.$setQuantifier.ordinal()]) {
                    case 1: {
                        string = "project";
                        break;
                    }
                    case 2: {
                        string = "project_distinct";
                        break;
                    }
                    default: {
                        throw new NoWhenBranchMatchedException();
                    }
                }
                $this$sexp.symbol(string);
                $this$sexp.sexp((Function1<? super IonWriterContext, Unit>)new Function1<IonWriterContext, Unit>(this){
                    final /* synthetic */ writeSelectProjectionListV0.1 this$0;

                    public final void invoke(@NotNull IonWriterContext $this$sexp) {
                        Intrinsics.checkParameterIsNotNull($this$sexp, "$receiver");
                        $this$sexp.symbol("list");
                        Iterable $this$forEach$iv = this.this$0.$items;
                        boolean $i$f$forEach = false;
                        for (T element$iv : $this$forEach$iv) {
                            WhenAsExpressionHelper whenAsExpressionHelper;
                            boolean $i$f$case;
                            SelectListItem it = (SelectListItem)element$iv;
                            boolean bl = false;
                            SelectListItem selectListItem = it;
                            if (selectListItem instanceof SelectListItemStar) {
                                $i$f$case = false;
                                boolean bl2 = false;
                                AstSerializerImpl.access$writeSelectListItemStar(this.this$0.this$0, $this$sexp, (SelectListItemStar)it);
                                whenAsExpressionHelper = WhenAsExpressionHelper.Companion.getInstance();
                            } else if (selectListItem instanceof SelectListItemExpr) {
                                $i$f$case = false;
                                boolean bl3 = false;
                                AstSerializerImpl.access$writeSelectListItemExpr(this.this$0.this$0, $this$sexp, (SelectListItemExpr)it);
                                whenAsExpressionHelper = WhenAsExpressionHelper.Companion.getInstance();
                            } else if (selectListItem instanceof SelectListItemProjectAll) {
                                $i$f$case = false;
                                boolean bl4 = false;
                                AstSerializerImpl.access$writeSelectListItemProjectAll(this.this$0.this$0, $this$sexp, (SelectListItemProjectAll)it);
                                whenAsExpressionHelper = WhenAsExpressionHelper.Companion.getInstance();
                            } else {
                                throw new NoWhenBranchMatchedException();
                            }
                            whenAsExpressionHelper.toUnit();
                        }
                    }
                    {
                        this.this$0 = var1_1;
                        super(1);
                    }
                });
            }
            {
                this.this$0 = astSerializerImpl;
                this.$setQuantifier = setQuantifier;
                this.$items = list;
                super(1);
            }
        });
    }

    private final void writeSelectListItemStar(@NotNull IonWriterContext $this$writeSelectListItemStar, SelectListItemStar it) {
        this.writeAsTerm($this$writeSelectListItemStar, it.getMetas(), writeSelectListItemStar.1.INSTANCE);
    }

    /*
     * WARNING - void declaration
     */
    private final void writeSelectListItemExpr(@NotNull IonWriterContext $this$writeSelectListItemExpr, SelectListItemExpr it) {
        void itemExpr;
        SelectListItemExpr selectListItemExpr = it;
        ExprNode exprNode = selectListItemExpr.component1();
        SymbolicName asName = selectListItemExpr.component2();
        if (asName != null) {
            this.writeAsTerm($this$writeSelectListItemExpr, asName.getMetas(), (Function1<? super IonWriterContext, Unit>)new Function1<IonWriterContext, Unit>(this, asName, (ExprNode)itemExpr){
                final /* synthetic */ AstSerializerImpl this$0;
                final /* synthetic */ SymbolicName $asName;
                final /* synthetic */ ExprNode $itemExpr;

                public final void invoke(@NotNull IonWriterContext $this$writeAsTerm) {
                    Intrinsics.checkParameterIsNotNull($this$writeAsTerm, "$receiver");
                    $this$writeAsTerm.sexp((Function1<? super IonWriterContext, Unit>)new Function1<IonWriterContext, Unit>(this){
                        final /* synthetic */ writeSelectListItemExpr.1 this$0;

                        public final void invoke(@NotNull IonWriterContext $this$sexp) {
                            Intrinsics.checkParameterIsNotNull($this$sexp, "$receiver");
                            $this$sexp.symbol("as");
                            $this$sexp.symbol(this.this$0.$asName.getName());
                            AstSerializerImpl.access$writeExprNode(this.this$0.this$0, $this$sexp, this.this$0.$itemExpr);
                        }
                        {
                            this.this$0 = var1_1;
                            super(1);
                        }
                    });
                }
                {
                    this.this$0 = astSerializerImpl;
                    this.$asName = symbolicName;
                    this.$itemExpr = exprNode;
                    super(1);
                }
            });
        } else {
            this.writeExprNode($this$writeSelectListItemExpr, (ExprNode)itemExpr);
        }
    }

    private final void writeSelectListItemProjectAll(@NotNull IonWriterContext $this$writeSelectListItemProjectAll, SelectListItemProjectAll it) {
        SelectListItemProjectAll selectListItemProjectAll = it;
        ExprNode exp = selectListItemProjectAll.component1();
        $this$writeSelectListItemProjectAll.sexp((Function1<? super IonWriterContext, Unit>)new Function1<IonWriterContext, Unit>(this, exp){
            final /* synthetic */ AstSerializerImpl this$0;
            final /* synthetic */ ExprNode $exp;

            public final void invoke(@NotNull IonWriterContext $this$sexp) {
                Intrinsics.checkParameterIsNotNull($this$sexp, "$receiver");
                $this$sexp.symbol("project_all");
                AstSerializerImpl.access$writeExprNode(this.this$0, $this$sexp, this.$exp);
            }
            {
                this.this$0 = astSerializerImpl;
                this.$exp = exprNode;
                super(1);
            }
        });
    }

    private final void writeFromSource(@NotNull IonWriterContext $this$writeFromSource, FromSource fromSource) {
        switch (AstSerializerImpl$WhenMappings.$EnumSwitchMapping$10[this.astVersion.ordinal()]) {
            case 1: {
                this.writeFromSourceV0($this$writeFromSource, fromSource);
                break;
            }
            default: {
                throw new NoWhenBranchMatchedException();
            }
        }
    }

    private final void writeSymbolicName(@NotNull IonWriterContext $this$writeSymbolicName, SymbolicName name) {
        SymbolicName symbolicName = name;
        if (symbolicName == null) {
            $this$writeSymbolicName.untypedNull();
        } else {
            this.writeAsTerm($this$writeSymbolicName, name.getMetas(), (Function1<? super IonWriterContext, Unit>)new Function1<IonWriterContext, Unit>(name){
                final /* synthetic */ SymbolicName $name;

                public final void invoke(@NotNull IonWriterContext $this$writeAsTerm) {
                    Intrinsics.checkParameterIsNotNull($this$writeAsTerm, "$receiver");
                    $this$writeAsTerm.symbol(this.$name.getName());
                }
                {
                    this.$name = symbolicName;
                    super(1);
                }
            });
        }
    }

    /*
     * WARNING - void declaration
     */
    private final void writeFromSourceV0(@NotNull IonWriterContext $this$writeFromSourceV0, FromSource fromSource) {
        WhenAsExpressionHelper whenAsExpressionHelper;
        FromSource fromSource2 = fromSource;
        if (fromSource2 instanceof FromSourceExpr) {
            void exp;
            boolean $i$f$case = false;
            boolean bl = false;
            FromSourceExpr fromSourceExpr = (FromSourceExpr)fromSource;
            ExprNode exprNode = fromSourceExpr.component1();
            LetVariables aliases = fromSourceExpr.component2();
            this.nestByAlias($this$writeFromSourceV0, aliases, new Function0<Unit>((ExprNode)exp, this, $this$writeFromSourceV0, fromSource){
                final /* synthetic */ ExprNode $exp;
                final /* synthetic */ AstSerializerImpl this$0;
                final /* synthetic */ IonWriterContext $this_writeFromSourceV0$inlined;
                final /* synthetic */ FromSource $fromSource$inlined;
                {
                    this.$exp = exprNode;
                    this.this$0 = astSerializerImpl;
                    this.$this_writeFromSourceV0$inlined = ionWriterContext;
                    this.$fromSource$inlined = fromSource;
                    super(0);
                }

                public final void invoke() {
                    AstSerializerImpl.access$writeExprNode(this.this$0, this.$this_writeFromSourceV0$inlined, this.$exp);
                }
            });
            whenAsExpressionHelper = WhenAsExpressionHelper.Companion.getInstance();
        } else if (fromSource2 instanceof FromSourceUnpivot) {
            boolean $i$f$case = false;
            boolean bl = false;
            FromSourceUnpivot fromSourceUnpivot = (FromSourceUnpivot)fromSource;
            ExprNode exp = fromSourceUnpivot.component1();
            LetVariables aliases = fromSourceUnpivot.component2();
            MetaContainer metas = fromSourceUnpivot.component3();
            this.nestByAlias($this$writeFromSourceV0, aliases, new Function0<Unit>(metas, exp, this, $this$writeFromSourceV0, fromSource){
                final /* synthetic */ MetaContainer $metas;
                final /* synthetic */ ExprNode $exp;
                final /* synthetic */ AstSerializerImpl this$0;
                final /* synthetic */ IonWriterContext $this_writeFromSourceV0$inlined;
                final /* synthetic */ FromSource $fromSource$inlined;
                {
                    this.$metas = metaContainer;
                    this.$exp = exprNode;
                    this.this$0 = astSerializerImpl;
                    this.$this_writeFromSourceV0$inlined = ionWriterContext;
                    this.$fromSource$inlined = fromSource;
                    super(0);
                }

                public final void invoke() {
                    AstSerializerImpl.access$writeAsTerm(this.this$0, this.$this_writeFromSourceV0$inlined, this.$metas, new Function1<IonWriterContext, Unit>(this){
                        final /* synthetic */ writeFromSourceV0$$inlined$case$lambda$2 this$0;
                        {
                            this.this$0 = var1_1;
                            super(1);
                        }

                        public final void invoke(@NotNull IonWriterContext $this$writeAsTerm) {
                            Intrinsics.checkParameterIsNotNull($this$writeAsTerm, "$receiver");
                            $this$writeAsTerm.sexp((Function1<? super IonWriterContext, Unit>)new Function1<IonWriterContext, Unit>(this){
                                final /* synthetic */ writeFromSourceV0$$inlined$case$lambda$2$1 this$0;
                                {
                                    this.this$0 = var1_1;
                                    super(1);
                                }

                                public final void invoke(@NotNull IonWriterContext $this$sexp) {
                                    Intrinsics.checkParameterIsNotNull($this$sexp, "$receiver");
                                    $this$sexp.symbol("unpivot");
                                    AstSerializerImpl.access$writeExprNode(this.this$0.this$0.this$0, $this$sexp, this.this$0.this$0.$exp);
                                }
                            });
                        }
                    });
                }
            });
            whenAsExpressionHelper = WhenAsExpressionHelper.Companion.getInstance();
        } else if (fromSource2 instanceof FromSourceJoin) {
            void rightRef;
            void leftRef;
            void op;
            boolean $i$f$case = false;
            boolean bl = false;
            FromSourceJoin fromSourceJoin = (FromSourceJoin)fromSource;
            JoinOp exp = fromSourceJoin.component1();
            FromSource aliases = fromSourceJoin.component2();
            FromSource metas = fromSourceJoin.component3();
            ExprNode condition = fromSourceJoin.component4();
            this.writeAsTerm($this$writeFromSourceV0, ((FromSourceJoin)fromSource).getMetas(), (Function1<? super IonWriterContext, Unit>)new Function1<IonWriterContext, Unit>((JoinOp)op, (FromSource)leftRef, (FromSource)rightRef, condition, this, $this$writeFromSourceV0, fromSource){
                final /* synthetic */ JoinOp $op;
                final /* synthetic */ FromSource $leftRef;
                final /* synthetic */ FromSource $rightRef;
                final /* synthetic */ ExprNode $condition;
                final /* synthetic */ AstSerializerImpl this$0;
                final /* synthetic */ IonWriterContext $this_writeFromSourceV0$inlined;
                final /* synthetic */ FromSource $fromSource$inlined;
                {
                    this.$op = joinOp;
                    this.$leftRef = fromSource;
                    this.$rightRef = fromSource2;
                    this.$condition = exprNode;
                    this.this$0 = astSerializerImpl;
                    this.$this_writeFromSourceV0$inlined = ionWriterContext;
                    this.$fromSource$inlined = fromSource3;
                    super(1);
                }

                public final void invoke(@NotNull IonWriterContext $this$writeAsTerm) {
                    Intrinsics.checkParameterIsNotNull($this$writeAsTerm, "$receiver");
                    $this$writeAsTerm.sexp((Function1<? super IonWriterContext, Unit>)new Function1<IonWriterContext, Unit>(this){
                        final /* synthetic */ writeFromSourceV0$$inlined$case$lambda$3 this$0;
                        {
                            this.this$0 = var1_1;
                            super(1);
                        }

                        public final void invoke(@NotNull IonWriterContext $this$sexp) {
                            String string;
                            Intrinsics.checkParameterIsNotNull($this$sexp, "$receiver");
                            switch (AstSerializerImpl$WhenMappings.$EnumSwitchMapping$11[this.this$0.$op.ordinal()]) {
                                case 1: {
                                    string = "inner_join";
                                    break;
                                }
                                case 2: {
                                    string = "left_join";
                                    break;
                                }
                                case 3: {
                                    string = "right_join";
                                    break;
                                }
                                case 4: {
                                    string = "outer_join";
                                    break;
                                }
                                default: {
                                    throw new NoWhenBranchMatchedException();
                                }
                            }
                            $this$sexp.symbol(string);
                            AstSerializerImpl.access$writeFromSource(this.this$0.this$0, $this$sexp, this.this$0.$leftRef);
                            AstSerializerImpl.access$writeFromSource(this.this$0.this$0, $this$sexp, this.this$0.$rightRef);
                            if (!((FromSourceJoin)this.this$0.$fromSource$inlined).getMetas().hasMeta("$is_implicit_join")) {
                                AstSerializerImpl.access$writeExprNode(this.this$0.this$0, $this$sexp, this.this$0.$condition);
                            }
                        }
                    });
                }
            });
            whenAsExpressionHelper = WhenAsExpressionHelper.Companion.getInstance();
        } else {
            throw new NoWhenBranchMatchedException();
        }
        whenAsExpressionHelper.toUnit();
    }

    /*
     * WARNING - void declaration
     */
    private final void writeSimpleCase(@NotNull IonWriterContext $this$writeSimpleCase, SimpleCase expr) {
        void valueExpr;
        $this$writeSimpleCase.symbol("simple_case");
        ExprNode exprNode = expr;
        ExprNode exprNode2 = exprNode.component1();
        List<SimpleCaseWhen> list = exprNode.component2();
        ExprNode elseExpr = exprNode.component3();
        this.writeExprNode($this$writeSimpleCase, (ExprNode)valueExpr);
        switch (AstSerializerImpl$WhenMappings.$EnumSwitchMapping$12[this.astVersion.ordinal()]) {
            case 1: {
                void whenClauses;
                Iterable $this$forEach$iv = (Iterable)whenClauses;
                boolean $i$f$forEach = false;
                for (Object element$iv : $this$forEach$iv) {
                    void whenValueExpr;
                    SimpleCaseWhen it = (SimpleCaseWhen)element$iv;
                    boolean bl = false;
                    SimpleCaseWhen simpleCaseWhen = it;
                    ExprNode exprNode3 = simpleCaseWhen.component1();
                    ExprNode thenExpr = simpleCaseWhen.component2();
                    $this$writeSimpleCase.sexp((Function1<? super IonWriterContext, Unit>)new Function1<IonWriterContext, Unit>((ExprNode)whenValueExpr, thenExpr, this, $this$writeSimpleCase){
                        final /* synthetic */ ExprNode $whenValueExpr;
                        final /* synthetic */ ExprNode $thenExpr;
                        final /* synthetic */ AstSerializerImpl this$0;
                        final /* synthetic */ IonWriterContext $this_writeSimpleCase$inlined;
                        {
                            this.$whenValueExpr = exprNode;
                            this.$thenExpr = exprNode2;
                            this.this$0 = astSerializerImpl;
                            this.$this_writeSimpleCase$inlined = ionWriterContext;
                            super(1);
                        }

                        public final void invoke(@NotNull IonWriterContext $this$sexp) {
                            Intrinsics.checkParameterIsNotNull($this$sexp, "$receiver");
                            $this$sexp.symbol("when");
                            AstSerializerImpl.access$writeExprNode(this.this$0, $this$sexp, this.$whenValueExpr);
                            AstSerializerImpl.access$writeExprNode(this.this$0, $this$sexp, this.$thenExpr);
                        }
                    });
                }
                ExprNode exprNode4 = elseExpr;
                if (exprNode4 == null) break;
                exprNode = exprNode4;
                boolean bl = false;
                boolean bl2 = false;
                ExprNode it = exprNode;
                boolean bl3 = false;
                $this$writeSimpleCase.sexp((Function1<? super IonWriterContext, Unit>)new Function1<IonWriterContext, Unit>(it, this, $this$writeSimpleCase){
                    final /* synthetic */ ExprNode $it;
                    final /* synthetic */ AstSerializerImpl this$0;
                    final /* synthetic */ IonWriterContext $this_writeSimpleCase$inlined;
                    {
                        this.$it = exprNode;
                        this.this$0 = astSerializerImpl;
                        this.$this_writeSimpleCase$inlined = ionWriterContext;
                        super(1);
                    }

                    public final void invoke(@NotNull IonWriterContext $this$sexp) {
                        Intrinsics.checkParameterIsNotNull($this$sexp, "$receiver");
                        $this$sexp.symbol("else");
                        AstSerializerImpl.access$writeExprNode(this.this$0, $this$sexp, this.$it);
                    }
                });
                break;
            }
        }
    }

    /*
     * WARNING - void declaration
     */
    private final void writeSearchedCase(@NotNull IonWriterContext $this$writeSearchedCase, SearchedCase expr) {
        $this$writeSearchedCase.symbol("searched_case");
        ExprNode exprNode = expr;
        List<SearchedCaseWhen> list = exprNode.component1();
        ExprNode elseExpr = exprNode.component2();
        switch (AstSerializerImpl$WhenMappings.$EnumSwitchMapping$13[this.astVersion.ordinal()]) {
            case 1: {
                void whenClauses;
                Iterable $this$forEach$iv = (Iterable)whenClauses;
                boolean $i$f$forEach = false;
                for (Object element$iv : $this$forEach$iv) {
                    void condition;
                    SearchedCaseWhen it = (SearchedCaseWhen)element$iv;
                    boolean bl = false;
                    SearchedCaseWhen searchedCaseWhen = it;
                    ExprNode exprNode2 = searchedCaseWhen.component1();
                    ExprNode thenExpr = searchedCaseWhen.component2();
                    $this$writeSearchedCase.sexp((Function1<? super IonWriterContext, Unit>)new Function1<IonWriterContext, Unit>((ExprNode)condition, thenExpr, this, $this$writeSearchedCase){
                        final /* synthetic */ ExprNode $condition;
                        final /* synthetic */ ExprNode $thenExpr;
                        final /* synthetic */ AstSerializerImpl this$0;
                        final /* synthetic */ IonWriterContext $this_writeSearchedCase$inlined;
                        {
                            this.$condition = exprNode;
                            this.$thenExpr = exprNode2;
                            this.this$0 = astSerializerImpl;
                            this.$this_writeSearchedCase$inlined = ionWriterContext;
                            super(1);
                        }

                        public final void invoke(@NotNull IonWriterContext $this$sexp) {
                            Intrinsics.checkParameterIsNotNull($this$sexp, "$receiver");
                            $this$sexp.symbol("when");
                            AstSerializerImpl.access$writeExprNode(this.this$0, $this$sexp, this.$condition);
                            AstSerializerImpl.access$writeExprNode(this.this$0, $this$sexp, this.$thenExpr);
                        }
                    });
                }
                ExprNode exprNode3 = elseExpr;
                if (exprNode3 == null) break;
                exprNode = exprNode3;
                boolean bl = false;
                boolean bl2 = false;
                ExprNode it = exprNode;
                boolean bl3 = false;
                $this$writeSearchedCase.sexp((Function1<? super IonWriterContext, Unit>)new Function1<IonWriterContext, Unit>(it, this, $this$writeSearchedCase){
                    final /* synthetic */ ExprNode $it;
                    final /* synthetic */ AstSerializerImpl this$0;
                    final /* synthetic */ IonWriterContext $this_writeSearchedCase$inlined;
                    {
                        this.$it = exprNode;
                        this.this$0 = astSerializerImpl;
                        this.$this_writeSearchedCase$inlined = ionWriterContext;
                        super(1);
                    }

                    public final void invoke(@NotNull IonWriterContext $this$sexp) {
                        Intrinsics.checkParameterIsNotNull($this$sexp, "$receiver");
                        $this$sexp.symbol("else");
                        AstSerializerImpl.access$writeExprNode(this.this$0, $this$sexp, this.$it);
                    }
                });
                break;
            }
        }
    }

    private final void writeExprPair(@NotNull IonWriterContext $this$writeExprPair, ExprNode first, ExprNode second) {
        $this$writeExprPair.sexp((Function1<? super IonWriterContext, Unit>)new Function1<IonWriterContext, Unit>(this, first, second){
            final /* synthetic */ AstSerializerImpl this$0;
            final /* synthetic */ ExprNode $first;
            final /* synthetic */ ExprNode $second;

            public final void invoke(@NotNull IonWriterContext $this$sexp) {
                Intrinsics.checkParameterIsNotNull($this$sexp, "$receiver");
                $this$sexp.symbol("expr_pair");
                AstSerializerImpl.access$writeExprNode(this.this$0, $this$sexp, this.$first);
                AstSerializerImpl.access$writeExprNode(this.this$0, $this$sexp, this.$second);
            }
            {
                this.this$0 = astSerializerImpl;
                this.$first = exprNode;
                this.$second = exprNode2;
                super(1);
            }
        });
    }

    private final void writeExprPairList(@NotNull IonWriterContext $this$writeExprPairList, List<? extends Pair<? extends ExprNode, ? extends ExprNode>> exprPairList) {
        $this$writeExprPairList.sexp((Function1<? super IonWriterContext, Unit>)new Function1<IonWriterContext, Unit>(this, exprPairList){
            final /* synthetic */ AstSerializerImpl this$0;
            final /* synthetic */ List $exprPairList;

            public final void invoke(@NotNull IonWriterContext $this$sexp) {
                Intrinsics.checkParameterIsNotNull($this$sexp, "$receiver");
                $this$sexp.symbol("expr_pair_list");
                Iterable $this$forEach$iv = this.$exprPairList;
                boolean $i$f$forEach = false;
                for (T element$iv : $this$forEach$iv) {
                    Pair it = (Pair)element$iv;
                    boolean bl = false;
                    AstSerializerImpl.access$writeExprPair(this.this$0, $this$sexp, (ExprNode)it.getFirst(), (ExprNode)it.getSecond());
                }
            }
            {
                this.this$0 = astSerializerImpl;
                this.$exprPairList = list;
                super(1);
            }
        });
    }

    /*
     * WARNING - void declaration
     */
    private final void writePath(@NotNull IonWriterContext $this$writePath, Path expr) {
        void root;
        Path path = expr;
        ExprNode exprNode = path.component1();
        List<PathComponent> components = path.component2();
        $this$writePath.symbol("path");
        this.writeExprNode($this$writePath, (ExprNode)root);
        switch (AstSerializerImpl$WhenMappings.$EnumSwitchMapping$14[this.astVersion.ordinal()]) {
            case 1: {
                boolean $i$f$case = false;
                boolean bl = false;
                this.writePathComponentsV0($this$writePath, components);
                WhenAsExpressionHelper.Companion.getInstance();
            }
        }
    }

    private final void writePathComponentsV0(@NotNull IonWriterContext $this$writePathComponentsV0, List<? extends PathComponent> components) {
        Iterable $this$forEach$iv = components;
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            WhenAsExpressionHelper whenAsExpressionHelper;
            boolean $i$f$case;
            PathComponent it = (PathComponent)element$iv;
            boolean bl = false;
            PathComponent pathComponent = it;
            if (pathComponent instanceof PathComponentExpr) {
                $i$f$case = false;
                boolean bl2 = false;
                this.writePathComponentExprV0($this$writePathComponentsV0, (PathComponentExpr)it);
                whenAsExpressionHelper = WhenAsExpressionHelper.Companion.getInstance();
            } else if (pathComponent instanceof PathComponentUnpivot) {
                $i$f$case = false;
                boolean bl3 = false;
                this.writePathComponentUnpivotV0($this$writePathComponentsV0, (PathComponentUnpivot)it);
                whenAsExpressionHelper = WhenAsExpressionHelper.Companion.getInstance();
            } else if (pathComponent instanceof PathComponentWildcard) {
                $i$f$case = false;
                boolean bl4 = false;
                this.writePathComponentWildcardV0($this$writePathComponentsV0, (PathComponentWildcard)it);
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
    private final void writePathComponentExprV0(@NotNull IonWriterContext $this$writePathComponentExprV0, PathComponentExpr pathComponent) {
        void exp;
        PathComponentExpr pathComponentExpr = pathComponent;
        ExprNode exprNode = pathComponentExpr.component1();
        CaseSensitivity caseSensitivity = pathComponentExpr.component2();
        if (exp instanceof VariableReference || exp instanceof Literal && ((Literal)exp).getIonValue() instanceof IonString) {
            $this$writePathComponentExprV0.sexp((Function1<? super IonWriterContext, Unit>)new Function1<IonWriterContext, Unit>(this, caseSensitivity, (ExprNode)exp){
                final /* synthetic */ AstSerializerImpl this$0;
                final /* synthetic */ CaseSensitivity $case;
                final /* synthetic */ ExprNode $exp;

                public final void invoke(@NotNull IonWriterContext $this$sexp) {
                    Intrinsics.checkParameterIsNotNull($this$sexp, "$receiver");
                    $this$sexp.symbol(this.$case.toSymbol());
                    AstSerializerImpl.access$writeExprNode(this.this$0, $this$sexp, this.$exp);
                }
                {
                    this.this$0 = astSerializerImpl;
                    this.$case = caseSensitivity;
                    this.$exp = exprNode;
                    super(1);
                }
            });
        } else {
            this.writeExprNode($this$writePathComponentExprV0, (ExprNode)exp);
        }
    }

    private final void writePathComponentUnpivotV0(@NotNull IonWriterContext $this$writePathComponentUnpivotV0, PathComponentUnpivot pathComponentUnpivot) {
        this.writeAsTerm($this$writePathComponentUnpivotV0, pathComponentUnpivot.getMetas(), writePathComponentUnpivotV0.1.INSTANCE);
    }

    private final void writePathComponentWildcardV0(@NotNull IonWriterContext $this$writePathComponentWildcardV0, PathComponentWildcard pathComponent) {
        this.writeAsTerm($this$writePathComponentWildcardV0, pathComponent.getMetas(), writePathComponentWildcardV0.1.INSTANCE);
    }

    /*
     * WARNING - void declaration
     */
    private final void writeNAry(@NotNull IonWriterContext $this$writeNAry, NAry expr) {
        void op;
        List<ExprNode> args2;
        block15: {
            block17: {
                ExprNode exprNode;
                ExprNode firstArg;
                block16: {
                    void argOp;
                    NAry nAry = expr;
                    NAryOp nAryOp = nAry.component1();
                    args2 = nAry.component2();
                    if (this.astVersion != AstVersion.V0 || op != NAryOp.NOT || !expr.getMetas().hasMeta("$legacy_logical_not")) break block15;
                    firstArg = CollectionsKt.first(args2);
                    exprNode = firstArg;
                    if (!(exprNode instanceof NAry)) break block16;
                    NAry nAry2 = (NAry)firstArg;
                    NAryOp nAryOp2 = nAry2.component1();
                    List<ExprNode> argArgs = nAry2.component2();
                    Function0<Unit> $fun$recurseArgs$1 = new Function0<Unit>(this, $this$writeNAry, argArgs){
                        final /* synthetic */ AstSerializerImpl this$0;
                        final /* synthetic */ IonWriterContext $this_writeNAry;
                        final /* synthetic */ List $argArgs;

                        public final void invoke() {
                            Iterable $this$forEach$iv = this.$argArgs;
                            boolean $i$f$forEach = false;
                            for (T element$iv : $this$forEach$iv) {
                                ExprNode it = (ExprNode)element$iv;
                                boolean bl = false;
                                AstSerializerImpl.access$writeExprNode(this.this$0, this.$this_writeNAry, it);
                            }
                        }
                        {
                            this.this$0 = astSerializerImpl;
                            this.$this_writeNAry = ionWriterContext;
                            this.$argArgs = list;
                            super(0);
                        }
                    };
                    switch (AstSerializerImpl$WhenMappings.$EnumSwitchMapping$15[argOp.ordinal()]) {
                        case 1: {
                            $this$writeNAry.symbol("not_between");
                            $fun$recurseArgs$1.invoke();
                            break block17;
                        }
                        case 2: {
                            $this$writeNAry.symbol("not_like");
                            $fun$recurseArgs$1.invoke();
                            break block17;
                        }
                        case 3: {
                            $this$writeNAry.symbol("not_in");
                            $fun$recurseArgs$1.invoke();
                            break block17;
                        }
                        default: {
                            throw (Throwable)new IllegalStateException("Invalid NAryOp on argument of `(not )` node decorated with LegacyLogicalNotMeta");
                        }
                    }
                }
                if (exprNode instanceof Typed) {
                    if (((Typed)firstArg).getOp() != TypedOp.IS) {
                        throw (Throwable)new IllegalStateException("Invalid TypedOp on argument of `(not )` node decorated with LegacyLogicalNotMeta");
                    }
                    $this$writeNAry.symbol("is_not");
                    this.writeExprNode($this$writeNAry, ((Typed)firstArg).getExpr());
                    this.writeDataType($this$writeNAry, ((Typed)firstArg).getType());
                } else {
                    throw (Throwable)new IllegalStateException("Invalid node type of of `(not )` node decorated with LegacyLogicalNotMeta");
                }
            }
            return;
        }
        String opSymbol = this.getOpSymbol((NAryOp)op);
        $this$writeNAry.symbol(opSymbol);
        Function0<Unit> $fun$writeArgs$2 = new Function0<Unit>(this, $this$writeNAry, args2){
            final /* synthetic */ AstSerializerImpl this$0;
            final /* synthetic */ IonWriterContext $this_writeNAry;
            final /* synthetic */ List $args;

            public final void invoke() {
                Iterable $this$forEach$iv = this.$args;
                boolean $i$f$forEach = false;
                for (T element$iv : $this$forEach$iv) {
                    ExprNode it = (ExprNode)element$iv;
                    boolean bl = false;
                    AstSerializerImpl.access$writeExprNode(this.this$0, this.$this_writeNAry, it);
                }
            }
            {
                this.this$0 = astSerializerImpl;
                this.$this_writeNAry = ionWriterContext;
                this.$args = list;
                super(0);
            }
        };
        switch (AstSerializerImpl$WhenMappings.$EnumSwitchMapping$16[op.ordinal()]) {
            case 1: {
                ExprNode funcExpr = CollectionsKt.first(expr.getArgs());
                String funcName = this.getFuncName(funcExpr);
                $this$writeNAry.symbol(funcName);
                Iterable $this$forEach$iv = CollectionsKt.drop((Iterable)args2, 1);
                boolean $i$f$forEach = false;
                for (Object element$iv : $this$forEach$iv) {
                    ExprNode it = (ExprNode)element$iv;
                    boolean bl = false;
                    this.writeExprNode($this$writeNAry, it);
                }
                break;
            }
            case 2: 
            case 3: 
            case 4: {
                $fun$writeArgs$2.invoke();
                break;
            }
            case 5: 
            case 6: 
            case 7: {
                $fun$writeArgs$2.invoke();
                break;
            }
            case 8: {
                $fun$writeArgs$2.invoke();
                break;
            }
            default: {
                $fun$writeArgs$2.invoke();
            }
        }
    }

    private final String getOpSymbol(NAryOp op) {
        switch (AstSerializerImpl$WhenMappings.$EnumSwitchMapping$17[this.astVersion.ordinal()]) {
            case 1: {
                break;
            }
            default: {
                throw new NoWhenBranchMatchedException();
            }
        }
        return op.getSymbol();
    }

    private final void writeCreateIndex(@NotNull IonWriterContext $this$writeCreateIndex, CreateIndex expr) {
        switch (AstSerializerImpl$WhenMappings.$EnumSwitchMapping$18[this.astVersion.ordinal()]) {
            case 1: {
                $this$writeCreateIndex.symbol("create");
                $this$writeCreateIndex.symbol(null);
                $this$writeCreateIndex.sexp((Function1<? super IonWriterContext, Unit>)new Function1<IonWriterContext, Unit>(this, expr){
                    final /* synthetic */ AstSerializerImpl this$0;
                    final /* synthetic */ CreateIndex $expr;

                    public final void invoke(@NotNull IonWriterContext $this$sexp) {
                        Intrinsics.checkParameterIsNotNull($this$sexp, "$receiver");
                        $this$sexp.symbol("index");
                        $this$sexp.symbol(this.$expr.getTableName());
                        $this$sexp.sexp((Function1<? super IonWriterContext, Unit>)new Function1<IonWriterContext, Unit>(this){
                            final /* synthetic */ writeCreateIndex.1 this$0;

                            public final void invoke(@NotNull IonWriterContext $this$sexp) {
                                Intrinsics.checkParameterIsNotNull($this$sexp, "$receiver");
                                $this$sexp.symbol("keys");
                                Iterable $this$forEach$iv = this.this$0.$expr.getKeys();
                                boolean $i$f$forEach = false;
                                for (T element$iv : $this$forEach$iv) {
                                    ExprNode it = (ExprNode)element$iv;
                                    boolean bl = false;
                                    AstSerializerImpl.access$writeExprNode(this.this$0.this$0, $this$sexp, it);
                                }
                            }
                            {
                                this.this$0 = var1_1;
                                super(1);
                            }
                        });
                    }
                    {
                        this.this$0 = astSerializerImpl;
                        this.$expr = createIndex;
                        super(1);
                    }
                });
            }
        }
    }

    private final void writeCreateTable(@NotNull IonWriterContext $this$writeCreateTable, CreateTable expr) {
        switch (AstSerializerImpl$WhenMappings.$EnumSwitchMapping$19[this.astVersion.ordinal()]) {
            case 1: {
                $this$writeCreateTable.symbol("create");
                $this$writeCreateTable.symbol(expr.getTableName());
                $this$writeCreateTable.sexp(writeCreateTable.1.INSTANCE);
            }
        }
    }

    private final void writeDropTable(@NotNull IonWriterContext $this$writeDropTable, DropTable expr) {
        switch (AstSerializerImpl$WhenMappings.$EnumSwitchMapping$20[this.astVersion.ordinal()]) {
            case 1: {
                $this$writeDropTable.symbol("drop_table");
                $this$writeDropTable.symbol(expr.getTableName());
            }
        }
    }

    private final void writeDropIndex(@NotNull IonWriterContext $this$writeDropIndex, DropIndex expr) {
        switch (AstSerializerImpl$WhenMappings.$EnumSwitchMapping$21[this.astVersion.ordinal()]) {
            case 1: {
                $this$writeDropIndex.symbol("drop_index");
                $this$writeDropIndex.symbol(expr.getTableName());
                this.writeExprNode($this$writeDropIndex, expr.getIdentifier());
            }
        }
    }

    private final void writeDDL(@NotNull IonWriterContext $this$writeDDL, Function0<Unit> block) {
        $this$writeDDL.symbol("ddl");
        $this$writeDDL.sexp((Function1<? super IonWriterContext, Unit>)new Function1<IonWriterContext, Unit>(block){
            final /* synthetic */ Function0 $block;

            public final void invoke(@NotNull IonWriterContext $this$sexp) {
                Intrinsics.checkParameterIsNotNull($this$sexp, "$receiver");
                this.$block.invoke();
            }
            {
                this.$block = function0;
                super(1);
            }
        });
    }

    private final void writeIdentifier(@NotNull IonWriterContext $this$writeIdentifier, String name, CaseSensitivity caseSensitivity) {
        $this$writeIdentifier.sexp((Function1<? super IonWriterContext, Unit>)new Function1<IonWriterContext, Unit>(name, caseSensitivity){
            final /* synthetic */ String $name;
            final /* synthetic */ CaseSensitivity $caseSensitivity;

            public final void invoke(@NotNull IonWriterContext $this$sexp) {
                Intrinsics.checkParameterIsNotNull($this$sexp, "$receiver");
                $this$sexp.symbol("identifier");
                $this$sexp.symbol(this.$name);
                $this$sexp.sexp((Function1<? super IonWriterContext, Unit>)new Function1<IonWriterContext, Unit>(this){
                    final /* synthetic */ writeIdentifier.1 this$0;

                    public final void invoke(@NotNull IonWriterContext $this$sexp) {
                        Intrinsics.checkParameterIsNotNull($this$sexp, "$receiver");
                        $this$sexp.symbol(this.this$0.$caseSensitivity.toSymbol());
                    }
                    {
                        this.this$0 = var1_1;
                        super(1);
                    }
                });
            }
            {
                this.$name = string;
                this.$caseSensitivity = caseSensitivity;
                super(1);
            }
        });
    }

    private final void writeParameter(@NotNull IonWriterContext $this$writeParameter, Parameter expr) {
        $this$writeParameter.symbol("parameter");
        $this$writeParameter.int(expr.getPosition());
    }

    private final void nestByAlias(@NotNull IonWriterContext $this$nestByAlias, LetVariables variables, Function0<Unit> block) {
        if (variables.getByName() != null) {
            this.writeAsTerm($this$nestByAlias, variables.getByName().getMetas(), (Function1<? super IonWriterContext, Unit>)new Function1<IonWriterContext, Unit>(this, variables, block){
                final /* synthetic */ AstSerializerImpl this$0;
                final /* synthetic */ LetVariables $variables;
                final /* synthetic */ Function0 $block;

                public final void invoke(@NotNull IonWriterContext $this$writeAsTerm) {
                    Intrinsics.checkParameterIsNotNull($this$writeAsTerm, "$receiver");
                    $this$writeAsTerm.sexp((Function1<? super IonWriterContext, Unit>)new Function1<IonWriterContext, Unit>(this){
                        final /* synthetic */ nestByAlias.1 this$0;

                        public final void invoke(@NotNull IonWriterContext $this$sexp) {
                            Intrinsics.checkParameterIsNotNull($this$sexp, "$receiver");
                            $this$sexp.symbol("by");
                            $this$sexp.symbol(this.this$0.$variables.getByName().getName());
                            AstSerializerImpl.access$nestAtAlias(this.this$0.this$0, $this$sexp, this.this$0.$variables, this.this$0.$block);
                        }
                        {
                            this.this$0 = var1_1;
                            super(1);
                        }
                    });
                }
                {
                    this.this$0 = astSerializerImpl;
                    this.$variables = letVariables;
                    this.$block = function0;
                    super(1);
                }
            });
        } else {
            this.nestAtAlias($this$nestByAlias, variables, block);
        }
    }

    private final void nestAtAlias(@NotNull IonWriterContext $this$nestAtAlias, LetVariables variables, Function0<Unit> block) {
        if (variables.getAtName() != null) {
            this.writeAsTerm($this$nestAtAlias, variables.getAtName().getMetas(), (Function1<? super IonWriterContext, Unit>)new Function1<IonWriterContext, Unit>(this, variables, block){
                final /* synthetic */ AstSerializerImpl this$0;
                final /* synthetic */ LetVariables $variables;
                final /* synthetic */ Function0 $block;

                public final void invoke(@NotNull IonWriterContext $this$writeAsTerm) {
                    Intrinsics.checkParameterIsNotNull($this$writeAsTerm, "$receiver");
                    $this$writeAsTerm.sexp((Function1<? super IonWriterContext, Unit>)new Function1<IonWriterContext, Unit>(this){
                        final /* synthetic */ nestAtAlias.1 this$0;

                        public final void invoke(@NotNull IonWriterContext $this$sexp) {
                            Intrinsics.checkParameterIsNotNull($this$sexp, "$receiver");
                            $this$sexp.symbol("at");
                            $this$sexp.symbol(this.this$0.$variables.getAtName().getName());
                            AstSerializerImpl.access$nestAsAlias(this.this$0.this$0, $this$sexp, this.this$0.$variables, this.this$0.$block);
                        }
                        {
                            this.this$0 = var1_1;
                            super(1);
                        }
                    });
                }
                {
                    this.this$0 = astSerializerImpl;
                    this.$variables = letVariables;
                    this.$block = function0;
                    super(1);
                }
            });
        } else {
            this.nestAsAlias($this$nestAtAlias, variables, block);
        }
    }

    private final void nestAsAlias(@NotNull IonWriterContext $this$nestAsAlias, LetVariables variables, Function0<Unit> block) {
        if (variables.getAsName() != null) {
            this.writeAsTerm($this$nestAsAlias, variables.getAsName().getMetas(), (Function1<? super IonWriterContext, Unit>)new Function1<IonWriterContext, Unit>(variables, block){
                final /* synthetic */ LetVariables $variables;
                final /* synthetic */ Function0 $block;

                public final void invoke(@NotNull IonWriterContext $this$writeAsTerm) {
                    Intrinsics.checkParameterIsNotNull($this$writeAsTerm, "$receiver");
                    $this$writeAsTerm.sexp((Function1<? super IonWriterContext, Unit>)new Function1<IonWriterContext, Unit>(this){
                        final /* synthetic */ nestAsAlias.1 this$0;

                        public final void invoke(@NotNull IonWriterContext $this$sexp) {
                            Intrinsics.checkParameterIsNotNull($this$sexp, "$receiver");
                            $this$sexp.symbol("as");
                            $this$sexp.symbol(this.this$0.$variables.getAsName().getName());
                            this.this$0.$block.invoke();
                        }
                        {
                            this.this$0 = var1_1;
                            super(1);
                        }
                    });
                }
                {
                    this.$variables = letVariables;
                    this.$block = function0;
                    super(1);
                }
            });
        } else {
            block.invoke();
        }
    }

    private final String getFuncName(@NotNull ExprNode $this$getFuncName) {
        ExprNode exprNode = $this$getFuncName;
        if (!(exprNode instanceof VariableReference)) {
            throw (Throwable)new UnsupportedOperationException("Using arbitrary expressions to identify a function in a call_agg or call node is not supported. Functions must be identified by name only.");
        }
        return ((VariableReference)$this$getFuncName).getId();
    }

    @NotNull
    public final AstVersion getAstVersion() {
        return this.astVersion;
    }

    @NotNull
    public final IonSystem getIon() {
        return this.ion;
    }

    public AstSerializerImpl(@NotNull AstVersion astVersion, @NotNull IonSystem ion) {
        Intrinsics.checkParameterIsNotNull((Object)astVersion, "astVersion");
        Intrinsics.checkParameterIsNotNull(ion, "ion");
        this.astVersion = astVersion;
        this.ion = ion;
    }

    public static final /* synthetic */ void access$writeLiteral(AstSerializerImpl $this, IonWriterContext $this$access_u24writeLiteral, Literal expr) {
        $this.writeLiteral($this$access_u24writeLiteral, expr);
    }

    public static final /* synthetic */ void access$writeLiteralMissing(AstSerializerImpl $this, IonWriterContext $this$access_u24writeLiteralMissing, LiteralMissing expr) {
        $this.writeLiteralMissing($this$access_u24writeLiteralMissing, expr);
    }

    public static final /* synthetic */ void access$writeVariableReference(AstSerializerImpl $this, IonWriterContext $this$access_u24writeVariableReference, VariableReference expr) {
        $this.writeVariableReference($this$access_u24writeVariableReference, expr);
    }

    public static final /* synthetic */ void access$writeNAry(AstSerializerImpl $this, IonWriterContext $this$access_u24writeNAry, NAry expr) {
        $this.writeNAry($this$access_u24writeNAry, expr);
    }

    public static final /* synthetic */ void access$writeCallAgg(AstSerializerImpl $this, IonWriterContext $this$access_u24writeCallAgg, CallAgg expr) {
        $this.writeCallAgg($this$access_u24writeCallAgg, expr);
    }

    public static final /* synthetic */ void access$writeTyped(AstSerializerImpl $this, IonWriterContext $this$access_u24writeTyped, Typed expr) {
        $this.writeTyped($this$access_u24writeTyped, expr);
    }

    public static final /* synthetic */ void access$writePath(AstSerializerImpl $this, IonWriterContext $this$access_u24writePath, Path expr) {
        $this.writePath($this$access_u24writePath, expr);
    }

    public static final /* synthetic */ void access$writeSimpleCase(AstSerializerImpl $this, IonWriterContext $this$access_u24writeSimpleCase, SimpleCase expr) {
        $this.writeSimpleCase($this$access_u24writeSimpleCase, expr);
    }

    public static final /* synthetic */ void access$writeSearchedCase(AstSerializerImpl $this, IonWriterContext $this$access_u24writeSearchedCase, SearchedCase expr) {
        $this.writeSearchedCase($this$access_u24writeSearchedCase, expr);
    }

    public static final /* synthetic */ void access$writeStruct(AstSerializerImpl $this, IonWriterContext $this$access_u24writeStruct, Struct expr) {
        $this.writeStruct($this$access_u24writeStruct, expr);
    }

    public static final /* synthetic */ void access$writeSeq(AstSerializerImpl $this, IonWriterContext $this$access_u24writeSeq, Seq expr) {
        $this.writeSeq($this$access_u24writeSeq, expr);
    }

    public static final /* synthetic */ void access$writeSelect(AstSerializerImpl $this, IonWriterContext $this$access_u24writeSelect, Select expr) {
        $this.writeSelect($this$access_u24writeSelect, expr);
    }

    public static final /* synthetic */ void access$writeDataManipulation(AstSerializerImpl $this, IonWriterContext $this$access_u24writeDataManipulation, DataManipulation expr) {
        $this.writeDataManipulation($this$access_u24writeDataManipulation, expr);
    }

    public static final /* synthetic */ void access$writeCreateTable(AstSerializerImpl $this, IonWriterContext $this$access_u24writeCreateTable, CreateTable expr) {
        $this.writeCreateTable($this$access_u24writeCreateTable, expr);
    }

    public static final /* synthetic */ void access$writeCreateIndex(AstSerializerImpl $this, IonWriterContext $this$access_u24writeCreateIndex, CreateIndex expr) {
        $this.writeCreateIndex($this$access_u24writeCreateIndex, expr);
    }

    public static final /* synthetic */ void access$writeDropTable(AstSerializerImpl $this, IonWriterContext $this$access_u24writeDropTable, DropTable expr) {
        $this.writeDropTable($this$access_u24writeDropTable, expr);
    }

    public static final /* synthetic */ void access$writeDropIndex(AstSerializerImpl $this, IonWriterContext $this$access_u24writeDropIndex, DropIndex expr) {
        $this.writeDropIndex($this$access_u24writeDropIndex, expr);
    }

    public static final /* synthetic */ void access$writeParameter(AstSerializerImpl $this, IonWriterContext $this$access_u24writeParameter, Parameter expr) {
        $this.writeParameter($this$access_u24writeParameter, expr);
    }

    public static final /* synthetic */ void access$writeFromSource(AstSerializerImpl $this, IonWriterContext $this$access_u24writeFromSource, FromSource fromSource) {
        $this.writeFromSource($this$access_u24writeFromSource, fromSource);
    }

    public static final /* synthetic */ void access$writeExprNode(AstSerializerImpl $this, IonWriterContext $this$access_u24writeExprNode, ExprNode expr) {
        $this.writeExprNode($this$access_u24writeExprNode, expr);
    }

    public static final /* synthetic */ void access$writeAsTerm(AstSerializerImpl $this, IonWriterContext $this$access_u24writeAsTerm, MetaContainer metas, Function1 block) {
        $this.writeAsTerm($this$access_u24writeAsTerm, metas, block);
    }

    public static final /* synthetic */ void access$writeSelectListItemStar(AstSerializerImpl $this, IonWriterContext $this$access_u24writeSelectListItemStar, SelectListItemStar it) {
        $this.writeSelectListItemStar($this$access_u24writeSelectListItemStar, it);
    }

    public static final /* synthetic */ void access$writeSelectListItemExpr(AstSerializerImpl $this, IonWriterContext $this$access_u24writeSelectListItemExpr, SelectListItemExpr it) {
        $this.writeSelectListItemExpr($this$access_u24writeSelectListItemExpr, it);
    }

    public static final /* synthetic */ void access$writeSelectListItemProjectAll(AstSerializerImpl $this, IonWriterContext $this$access_u24writeSelectListItemProjectAll, SelectListItemProjectAll it) {
        $this.writeSelectListItemProjectAll($this$access_u24writeSelectListItemProjectAll, it);
    }

    public static final /* synthetic */ void access$writeExprPair(AstSerializerImpl $this, IonWriterContext $this$access_u24writeExprPair, ExprNode first, ExprNode second) {
        $this.writeExprPair($this$access_u24writeExprPair, first, second);
    }

    public static final /* synthetic */ void access$nestAtAlias(AstSerializerImpl $this, IonWriterContext $this$access_u24nestAtAlias, LetVariables variables, Function0 block) {
        $this.nestAtAlias($this$access_u24nestAtAlias, variables, block);
    }

    public static final /* synthetic */ void access$nestAsAlias(AstSerializerImpl $this, IonWriterContext $this$access_u24nestAsAlias, LetVariables variables, Function0 block) {
        $this.nestAsAlias($this$access_u24nestAsAlias, variables, block);
    }
}

