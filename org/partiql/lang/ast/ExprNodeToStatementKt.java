/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  com.amazon.ionelement.api.IonElement
 *  com.amazon.ionelement.api.IonMeta
 *  com.amazon.ionelement.api.IonUtils
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 *  org.partiql.pig.runtime.PrimitiveUtilsKt
 *  org.partiql.pig.runtime.SymbolPrimitive
 */
package org.partiql.lang.ast;

import com.amazon.ion.IonValue;
import com.amazon.ionelement.api.IonElement;
import com.amazon.ionelement.api.IonMeta;
import com.amazon.ionelement.api.IonUtils;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.TypeCastException;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.partiql.lang.ast.AssignmentOp;
import org.partiql.lang.ast.CallAgg;
import org.partiql.lang.ast.CaseSensitivity;
import org.partiql.lang.ast.ColumnComponent;
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
import org.partiql.lang.ast.ExprNodeToStatementKt$WhenMappings;
import org.partiql.lang.ast.FromSource;
import org.partiql.lang.ast.FromSourceExpr;
import org.partiql.lang.ast.FromSourceJoin;
import org.partiql.lang.ast.FromSourceUnpivot;
import org.partiql.lang.ast.GroupBy;
import org.partiql.lang.ast.GroupByItem;
import org.partiql.lang.ast.GroupingStrategy;
import org.partiql.lang.ast.InsertOp;
import org.partiql.lang.ast.InsertValueOp;
import org.partiql.lang.ast.LetBinding;
import org.partiql.lang.ast.LetSource;
import org.partiql.lang.ast.Literal;
import org.partiql.lang.ast.LiteralMissing;
import org.partiql.lang.ast.Meta;
import org.partiql.lang.ast.MetaContainer;
import org.partiql.lang.ast.NAry;
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
import org.partiql.lang.ast.SetQuantifier;
import org.partiql.lang.ast.SimpleCase;
import org.partiql.lang.ast.SimpleCaseWhen;
import org.partiql.lang.ast.SortSpec;
import org.partiql.lang.ast.Struct;
import org.partiql.lang.ast.StructField;
import org.partiql.lang.ast.SymbolicName;
import org.partiql.lang.ast.Typed;
import org.partiql.lang.ast.VariableReference;
import org.partiql.lang.domains.PartiqlAst;
import org.partiql.lang.util.ThreadInterruptUtilsKt;
import org.partiql.pig.runtime.PrimitiveUtilsKt;
import org.partiql.pig.runtime.SymbolPrimitive;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=2, d1={"\u0000\u00f8\u0001\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0010\u000e\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\f\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u0002\u001a\f\u0010\u0003\u001a\u00020\u0004*\u00020\u0005H\u0002\u001a\f\u0010\u0006\u001a\u00020\u0004*\u00020\u0007H\u0002\u001a\u0014\u0010\b\u001a\u00020\t*\u00020\n2\u0006\u0010\u000b\u001a\u00020\u0007H\u0002\u001a\u0014\u0010\f\u001a\u00020\r*\u00020\u000e2\u0006\u0010\u000b\u001a\u00020\u0007H\u0002\u001a\f\u0010\u000f\u001a\u00020\u0004*\u00020\u0005H\u0002\u001a\n\u0010\u0010\u001a\u00020\u0011*\u00020\u0005\u001a\f\u0010\u0012\u001a\u00020\u0013*\u00020\u0014H\u0002\u001a\f\u0010\u0015\u001a\u00020\u0016*\u00020\u0017H\u0002\u001a\f\u0010\u0018\u001a\u00020\u0019*\u00020\u001aH\u0002\u001a\f\u0010\u001b\u001a\u00020\u001c*\u00020\u001dH\u0002\u001a\f\u0010\u001e\u001a\u00020\u001f*\u00020 H\u0002\u001a\f\u0010!\u001a\u00020\"*\u00020#H\u0002\u001a\u000e\u0010$\u001a\u00020%*\u0004\u0018\u00010&H\u0002\u001a\f\u0010'\u001a\u00020(*\u00020)H\u0002\u001a\f\u0010*\u001a\u00020+*\u00020,H\u0002\u001a\f\u0010-\u001a\u00020.*\u00020/H\u0002\u001a\f\u00100\u001a\u000201*\u000202H\u0002\u001a\f\u00103\u001a\u000204*\u000205H\u0002\u001a\n\u00106\u001a\u00020\u0004*\u00020\u0005\u001a\f\u00107\u001a\u000208*\u000209H\u0002\u001a\f\u0010:\u001a\u00020;*\u00020<H\u0002\u001a \u0010=\u001a\u0012\u0012\u0004\u0012\u00020?\u0012\u0004\u0012\u00020@0>j\u0002`A*\u00060Bj\u0002`CH\u0000\u001a\f\u0010D\u001a\u00020E*\u00020FH\u0002\u001a\f\u0010G\u001a\u00020H*\u00020IH\u0002\u001a\f\u0010J\u001a\u00020E*\u00020FH\u0002\u00a8\u0006K"}, d2={"toAstCaseSensitivity", "Lorg/partiql/lang/domains/PartiqlAst$CaseSensitivity;", "Lorg/partiql/lang/ast/CaseSensitivity;", "toAstDdl", "Lorg/partiql/lang/domains/PartiqlAst$Statement;", "Lorg/partiql/lang/ast/ExprNode;", "toAstDml", "Lorg/partiql/lang/ast/DataManipulation;", "toAstDmlOp", "Lorg/partiql/lang/domains/PartiqlAst$DmlOp;", "Lorg/partiql/lang/ast/DataManipulationOperation;", "dml", "toAstDmlOps", "Lorg/partiql/lang/domains/PartiqlAst$DmlOpList;", "Lorg/partiql/lang/ast/DmlOpList;", "toAstExec", "toAstExpr", "Lorg/partiql/lang/domains/PartiqlAst$Expr;", "toAstFromSource", "Lorg/partiql/lang/domains/PartiqlAst$FromSource;", "Lorg/partiql/lang/ast/FromSource;", "toAstGroupSpec", "Lorg/partiql/lang/domains/PartiqlAst$GroupBy;", "Lorg/partiql/lang/ast/GroupBy;", "toAstGroupStrategy", "Lorg/partiql/lang/domains/PartiqlAst$GroupingStrategy;", "Lorg/partiql/lang/ast/GroupingStrategy;", "toAstLetSource", "Lorg/partiql/lang/domains/PartiqlAst$Let;", "Lorg/partiql/lang/ast/LetSource;", "toAstOnConflict", "Lorg/partiql/lang/domains/PartiqlAst$OnConflict;", "Lorg/partiql/lang/ast/OnConflict;", "toAstOrderBySpec", "Lorg/partiql/lang/domains/PartiqlAst$OrderBy;", "Lorg/partiql/lang/ast/OrderBy;", "toAstOrderSpec", "Lorg/partiql/lang/domains/PartiqlAst$OrderingSpec;", "Lorg/partiql/lang/ast/OrderingSpec;", "toAstPathStep", "Lorg/partiql/lang/domains/PartiqlAst$PathStep;", "Lorg/partiql/lang/ast/PathComponent;", "toAstReturningExpr", "Lorg/partiql/lang/domains/PartiqlAst$ReturningExpr;", "Lorg/partiql/lang/ast/ReturningExpr;", "toAstScopeQualifier", "Lorg/partiql/lang/domains/PartiqlAst$ScopeQualifier;", "Lorg/partiql/lang/ast/ScopeQualifier;", "toAstSelectProject", "Lorg/partiql/lang/domains/PartiqlAst$Projection;", "Lorg/partiql/lang/ast/SelectProjection;", "toAstSetQuantifier", "Lorg/partiql/lang/domains/PartiqlAst$SetQuantifier;", "Lorg/partiql/lang/ast/SetQuantifier;", "toAstStatement", "toAstType", "Lorg/partiql/lang/domains/PartiqlAst$Type;", "Lorg/partiql/lang/ast/DataType;", "toColumnComponent", "Lorg/partiql/lang/domains/PartiqlAst$ColumnComponent;", "Lorg/partiql/lang/ast/ColumnComponent;", "toIonElementMetaContainer", "", "", "", "Lorg/partiql/lang/ast/IonElementMetaContainer;", "Lorg/partiql/lang/ast/MetaContainer;", "Lorg/partiql/lang/ast/PartiQlMetaContainer;", "toPrimitive", "Lorg/partiql/pig/runtime/SymbolPrimitive;", "Lorg/partiql/lang/ast/SymbolicName;", "toReturningMapping", "Lorg/partiql/lang/domains/PartiqlAst$ReturningMapping;", "Lorg/partiql/lang/ast/ReturningMapping;", "toSymbolPrimitive", "lang"})
public final class ExprNodeToStatementKt {
    @NotNull
    public static final PartiqlAst.Statement toAstStatement(@NotNull ExprNode $this$toAstStatement) {
        PartiqlAst.Statement statement;
        ExprNode node;
        Intrinsics.checkParameterIsNotNull($this$toAstStatement, "$this$toAstStatement");
        ExprNode exprNode = node = $this$toAstStatement;
        if (exprNode instanceof Literal || exprNode instanceof LiteralMissing || exprNode instanceof VariableReference || exprNode instanceof Parameter || exprNode instanceof NAry || exprNode instanceof CallAgg || exprNode instanceof Typed || exprNode instanceof Path || exprNode instanceof SimpleCase || exprNode instanceof SearchedCase || exprNode instanceof Select || exprNode instanceof Struct || exprNode instanceof DateTimeType || exprNode instanceof Seq) {
            statement = (PartiqlAst.Statement)PartiqlAst.Companion.build((Function1)new Function1<PartiqlAst.Builder, PartiqlAst.Statement.Query>($this$toAstStatement){
                final /* synthetic */ ExprNode $this_toAstStatement;

                @NotNull
                public final PartiqlAst.Statement.Query invoke(@NotNull PartiqlAst.Builder $this$build) {
                    Intrinsics.checkParameterIsNotNull($this$build, "$receiver");
                    return PartiqlAst.Builder.DefaultImpls.query$default($this$build, ExprNodeToStatementKt.toAstExpr(this.$this_toAstStatement), null, 2, null);
                }
                {
                    this.$this_toAstStatement = exprNode;
                    super(1);
                }
            });
        } else if (exprNode instanceof DataManipulation) {
            statement = ExprNodeToStatementKt.toAstDml((DataManipulation)node);
        } else if (exprNode instanceof CreateTable || exprNode instanceof CreateIndex || exprNode instanceof DropTable || exprNode instanceof DropIndex) {
            statement = ExprNodeToStatementKt.toAstDdl($this$toAstStatement);
        } else if (exprNode instanceof Exec) {
            statement = ExprNodeToStatementKt.toAstExec($this$toAstStatement);
        } else {
            throw new NoWhenBranchMatchedException();
        }
        return statement;
    }

    /*
     * WARNING - void declaration
     */
    @NotNull
    public static final Map<String, Object> toIonElementMetaContainer(@NotNull MetaContainer $this$toIonElementMetaContainer) {
        void $this$mapTo$iv$iv;
        Intrinsics.checkParameterIsNotNull($this$toIonElementMetaContainer, "$this$toIonElementMetaContainer");
        Iterable $this$map$iv = $this$toIonElementMetaContainer;
        boolean $i$f$map = false;
        Iterable iterable = $this$map$iv;
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
        boolean $i$f$mapTo = false;
        for (Object item$iv$iv : $this$mapTo$iv$iv) {
            void it;
            Meta meta = (Meta)item$iv$iv;
            Collection collection = destination$iv$iv;
            boolean bl = false;
            Pair<String, void> pair = TuplesKt.to(it.getTag(), it);
            collection.add(pair);
        }
        return IonMeta.metaContainerOf((List)((List)destination$iv$iv));
    }

    private static final SymbolPrimitive toSymbolPrimitive(@NotNull SymbolicName $this$toSymbolPrimitive) {
        return new SymbolPrimitive($this$toSymbolPrimitive.getName(), ExprNodeToStatementKt.toIonElementMetaContainer($this$toSymbolPrimitive.getMetas()));
    }

    private static final PartiqlAst.Statement toAstDdl(@NotNull ExprNode $this$toAstDdl) {
        ExprNode thiz = $this$toAstDdl;
        Map<String, Object> metas = ExprNodeToStatementKt.toIonElementMetaContainer($this$toAstDdl.getMetas());
        return (PartiqlAst.Statement)PartiqlAst.Companion.build((Function1)new Function1<PartiqlAst.Builder, PartiqlAst.Statement>(thiz, metas){
            final /* synthetic */ ExprNode $thiz;
            final /* synthetic */ Map $metas;

            /*
             * WARNING - void declaration
             */
            @NotNull
            public final PartiqlAst.Statement invoke(@NotNull PartiqlAst.Builder $this$build) {
                PartiqlAst.Statement statement;
                Intrinsics.checkParameterIsNotNull($this$build, "$receiver");
                ExprNode exprNode = this.$thiz;
                if (exprNode instanceof Literal || exprNode instanceof LiteralMissing || exprNode instanceof VariableReference || exprNode instanceof Parameter || exprNode instanceof NAry || exprNode instanceof CallAgg || exprNode instanceof Typed || exprNode instanceof Path || exprNode instanceof SimpleCase || exprNode instanceof SearchedCase || exprNode instanceof Select || exprNode instanceof Struct || exprNode instanceof Seq || exprNode instanceof DateTimeType || exprNode instanceof DataManipulation || exprNode instanceof Exec) {
                    String string = "Can't convert " + this.$thiz.getClass() + " to PartiqlAst.ddl";
                    boolean bl = false;
                    throw (Throwable)new IllegalStateException(string.toString());
                }
                if (exprNode instanceof CreateTable) {
                    statement = $this$build.ddl(PartiqlAst.Builder.DefaultImpls.createTable$default($this$build, ((CreateTable)this.$thiz).getTableName(), null, 2, null), this.$metas);
                } else if (exprNode instanceof CreateIndex) {
                    Collection<PartiqlAst.Expr> collection;
                    void $this$mapTo$iv$iv;
                    void $this$map$iv;
                    Iterable iterable = ((CreateIndex)this.$thiz).getKeys();
                    PartiqlAst.Identifier identifier = PartiqlAst.Builder.DefaultImpls.identifier$default($this$build, ((CreateIndex)this.$thiz).getTableName(), PartiqlAst.Builder.DefaultImpls.caseSensitive$default($this$build, null, 1, null), null, 4, null);
                    PartiqlAst.Builder builder = $this$build;
                    PartiqlAst.Builder builder2 = $this$build;
                    boolean $i$f$map = false;
                    void var5_10 = $this$map$iv;
                    Collection destination$iv$iv = new ArrayList<E>(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
                    boolean $i$f$mapTo = false;
                    for (T item$iv$iv : $this$mapTo$iv$iv) {
                        void it;
                        ExprNode exprNode2 = (ExprNode)item$iv$iv;
                        collection = destination$iv$iv;
                        boolean bl = false;
                        PartiqlAst.Expr expr = ExprNodeToStatementKt.toAstExpr((ExprNode)it);
                        collection.add(expr);
                    }
                    collection = (List)destination$iv$iv;
                    statement = builder2.ddl(PartiqlAst.Builder.DefaultImpls.createIndex$default(builder, identifier, (List)collection, null, 4, null), this.$metas);
                } else if (exprNode instanceof DropIndex) {
                    statement = $this$build.ddl(PartiqlAst.Builder.DefaultImpls.dropIndex$default($this$build, PartiqlAst.Builder.DefaultImpls.identifier$default($this$build, ((DropIndex)this.$thiz).getTableName(), PartiqlAst.Builder.DefaultImpls.caseSensitive$default($this$build, null, 1, null), null, 4, null), PartiqlAst.Builder.DefaultImpls.identifier$default($this$build, ((DropIndex)this.$thiz).getIdentifier().getId(), ExprNodeToStatementKt.access$toAstCaseSensitivity(((DropIndex)this.$thiz).getIdentifier().getCase()), null, 4, null), null, 4, null), this.$metas);
                } else if (exprNode instanceof DropTable) {
                    statement = $this$build.ddl(PartiqlAst.Builder.DefaultImpls.dropTable$default($this$build, PartiqlAst.Builder.DefaultImpls.identifier$default($this$build, ((DropTable)this.$thiz).getTableName(), PartiqlAst.Builder.DefaultImpls.caseSensitive$default($this$build, null, 1, null), null, 4, null), null, 2, null), this.$metas);
                } else {
                    throw new NoWhenBranchMatchedException();
                }
                return statement;
            }
            {
                this.$thiz = exprNode;
                this.$metas = map2;
                super(1);
            }
        });
    }

    private static final PartiqlAst.Statement toAstExec(@NotNull ExprNode $this$toAstExec) {
        ExprNode node = $this$toAstExec;
        Map<String, Object> metas = ExprNodeToStatementKt.toIonElementMetaContainer($this$toAstExec.getMetas());
        return (PartiqlAst.Statement)PartiqlAst.Companion.build((Function1)new Function1<PartiqlAst.Builder, PartiqlAst.Statement>(node, metas){
            final /* synthetic */ ExprNode $node;
            final /* synthetic */ Map $metas;

            /*
             * WARNING - void declaration
             */
            @NotNull
            public final PartiqlAst.Statement invoke(@NotNull PartiqlAst.Builder $this$build) {
                Collection<PartiqlAst.Expr> collection;
                Collection destination$iv$iv;
                PartiqlAst.Builder builder;
                SymbolPrimitive symbolPrimitive;
                Intrinsics.checkParameterIsNotNull($this$build, "$receiver");
                ExprNode exprNode = this.$node;
                if (exprNode instanceof Exec) {
                    void $this$mapTo$iv$iv;
                    void $this$map$iv;
                    Iterable iterable = ((Exec)this.$node).getArgs();
                    symbolPrimitive = ExprNodeToStatementKt.access$toSymbolPrimitive(((Exec)this.$node).getProcedureName());
                    builder = $this$build;
                    boolean $i$f$map = false;
                    void var5_9 = $this$map$iv;
                    destination$iv$iv = new ArrayList<E>(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
                    boolean $i$f$mapTo = false;
                    for (T item$iv$iv : $this$mapTo$iv$iv) {
                        void it;
                        ExprNode exprNode2 = (ExprNode)item$iv$iv;
                        collection = destination$iv$iv;
                        boolean bl = false;
                        PartiqlAst.Expr expr = ExprNodeToStatementKt.toAstExpr((ExprNode)it);
                        collection.add(expr);
                    }
                } else {
                    String string = "Can't convert " + this.$node.getClass() + " to PartiqlAst.Statement.Exec";
                    boolean bl = false;
                    throw (Throwable)new IllegalStateException(string.toString());
                }
                collection = (List)destination$iv$iv;
                return builder.exec_(symbolPrimitive, (List<? extends PartiqlAst.Expr>)collection, (Map<String, ? extends Object>)this.$metas);
            }
            {
                this.$node = exprNode;
                this.$metas = map2;
                super(1);
            }
        });
    }

    @NotNull
    public static final PartiqlAst.Expr toAstExpr(@NotNull ExprNode $this$toAstExpr) {
        Intrinsics.checkParameterIsNotNull($this$toAstExpr, "$this$toAstExpr");
        ThreadInterruptUtilsKt.checkThreadInterrupted();
        ExprNode node = $this$toAstExpr;
        Map<String, Object> metas = ExprNodeToStatementKt.toIonElementMetaContainer($this$toAstExpr.getMetas());
        return (PartiqlAst.Expr)PartiqlAst.Companion.build((Function1)new Function1<PartiqlAst.Builder, PartiqlAst.Expr>(node, metas){
            final /* synthetic */ ExprNode $node;
            final /* synthetic */ Map $metas;

            /*
             * WARNING - void declaration
             * Enabled force condition propagation
             * Lifted jumps to return sites
             */
            @NotNull
            public final PartiqlAst.Expr invoke(@NotNull PartiqlAst.Builder $this$build) {
                PartiqlAst.Expr expr;
                Intrinsics.checkParameterIsNotNull($this$build, "$receiver");
                ExprNode exprNode = this.$node;
                if (exprNode instanceof Literal) {
                    expr = $this$build.lit((IonElement)IonUtils.toIonElement((IonValue)((Literal)this.$node).getIonValue()), this.$metas);
                    return expr;
                }
                if (exprNode instanceof LiteralMissing) {
                    expr = $this$build.missing(this.$metas);
                    return expr;
                }
                if (exprNode instanceof VariableReference) {
                    expr = $this$build.id(((VariableReference)this.$node).getId(), ExprNodeToStatementKt.access$toAstCaseSensitivity(((VariableReference)this.$node).getCase()), ExprNodeToStatementKt.access$toAstScopeQualifier(((VariableReference)this.$node).getScopeQualifier()), this.$metas);
                    return expr;
                }
                if (exprNode instanceof Parameter) {
                    expr = $this$build.parameter(((Parameter)this.$node).getPosition(), this.$metas);
                    return expr;
                }
                if (exprNode instanceof NAry) {
                    Iterable $this$map$iv = ((NAry)this.$node).getArgs();
                    boolean $i$f$map2 = false;
                    Iterable iterable = $this$map$iv;
                    Collection destination$iv$iv = new ArrayList<E>(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
                    boolean $i$f$mapTo = false;
                    for (T item$iv$iv2 : iterable) {
                        void it;
                        ExprNode exprNode2 = (ExprNode)item$iv$iv2;
                        Collection collection = destination$iv$iv;
                        boolean bl = false;
                        PartiqlAst.Expr expr2 = ExprNodeToStatementKt.toAstExpr((ExprNode)it);
                        collection.add(expr2);
                    }
                    List list = (List)destination$iv$iv;
                    switch (ExprNodeToStatementKt$WhenMappings.$EnumSwitchMapping$0[((NAry)this.$node).getOp().ordinal()]) {
                        case 1: {
                            expr = $this$build.plus(list, this.$metas);
                            return expr;
                        }
                        case 2: {
                            expr = $this$build.minus(list, this.$metas);
                            return expr;
                        }
                        case 3: {
                            expr = $this$build.times(list, this.$metas);
                            return expr;
                        }
                        case 4: {
                            expr = $this$build.divide(list, this.$metas);
                            return expr;
                        }
                        case 5: {
                            expr = $this$build.modulo(list, this.$metas);
                            return expr;
                        }
                        case 6: {
                            expr = $this$build.eq(list, this.$metas);
                            return expr;
                        }
                        case 7: {
                            expr = $this$build.lt(list, this.$metas);
                            return expr;
                        }
                        case 8: {
                            expr = $this$build.lte(list, this.$metas);
                            return expr;
                        }
                        case 9: {
                            expr = $this$build.gt(list, this.$metas);
                            return expr;
                        }
                        case 10: {
                            expr = $this$build.gte(list, this.$metas);
                            return expr;
                        }
                        case 11: {
                            expr = $this$build.ne(list, this.$metas);
                            return expr;
                        }
                        case 12: {
                            expr = $this$build.like((PartiqlAst.Expr)list.get(0), (PartiqlAst.Expr)list.get(1), list.size() >= 3 ? (PartiqlAst.Expr)list.get(2) : null, this.$metas);
                            return expr;
                        }
                        case 13: {
                            expr = $this$build.between((PartiqlAst.Expr)list.get(0), (PartiqlAst.Expr)list.get(1), (PartiqlAst.Expr)list.get(2), this.$metas);
                            return expr;
                        }
                        case 14: {
                            expr = $this$build.not((PartiqlAst.Expr)list.get(0), this.$metas);
                            return expr;
                        }
                        case 15: {
                            expr = $this$build.inCollection(list, this.$metas);
                            return expr;
                        }
                        case 16: {
                            expr = $this$build.and(list, this.$metas);
                            return expr;
                        }
                        case 17: {
                            expr = $this$build.or(list, this.$metas);
                            return expr;
                        }
                        case 18: {
                            expr = $this$build.concat(list, this.$metas);
                            return expr;
                        }
                        case 19: {
                            T t = CollectionsKt.first(list);
                            if (!(t instanceof PartiqlAst.Expr.Id)) {
                                t = null;
                            }
                            PartiqlAst.Expr.Id id = (PartiqlAst.Expr.Id)t;
                            if (id == null) {
                                String $i$f$map2 = "First argument of call should be a VariableReference";
                                boolean bl = false;
                                throw (Throwable)new IllegalStateException($i$f$map2.toString());
                            }
                            PartiqlAst.Expr.Id idArg = id;
                            expr = $this$build.call(idArg.getName().getText(), CollectionsKt.drop(list, 1), this.$metas);
                            return expr;
                        }
                        case 20: {
                            expr = $this$build.intersect(PartiqlAst.Builder.DefaultImpls.distinct$default($this$build, null, 1, null), list, this.$metas);
                            return expr;
                        }
                        case 21: {
                            expr = $this$build.intersect(PartiqlAst.Builder.DefaultImpls.all$default($this$build, null, 1, null), list, this.$metas);
                            return expr;
                        }
                        case 22: {
                            expr = $this$build.except(PartiqlAst.Builder.DefaultImpls.distinct$default($this$build, null, 1, null), list, this.$metas);
                            return expr;
                        }
                        case 23: {
                            expr = $this$build.except(PartiqlAst.Builder.DefaultImpls.all$default($this$build, null, 1, null), list, this.$metas);
                            return expr;
                        }
                        case 24: {
                            expr = $this$build.union(PartiqlAst.Builder.DefaultImpls.distinct$default($this$build, null, 1, null), list, this.$metas);
                            return expr;
                        }
                        case 25: {
                            expr = $this$build.union(PartiqlAst.Builder.DefaultImpls.all$default($this$build, null, 1, null), list, this.$metas);
                            return expr;
                        }
                        default: {
                            throw new NoWhenBranchMatchedException();
                        }
                    }
                }
                if (exprNode instanceof CallAgg) {
                    ExprNode exprNode3 = ((CallAgg)this.$node).getFuncExpr();
                    if (!(exprNode3 instanceof VariableReference)) {
                        exprNode3 = null;
                    }
                    VariableReference variableReference = (VariableReference)exprNode3;
                    if (variableReference == null) {
                        String idArg = "Expected CallAgg.funcExpr to be a VariableReference";
                        boolean $i$f$map2 = false;
                        throw (Throwable)new IllegalStateException(idArg.toString());
                    }
                    VariableReference variableReference2 = variableReference;
                    SymbolPrimitive symbol1Primitive = PrimitiveUtilsKt.asPrimitive((String)variableReference2.getId(), ExprNodeToStatementKt.toIonElementMetaContainer(variableReference2.getMetas()));
                    expr = $this$build.callAgg_(ExprNodeToStatementKt.access$toAstSetQuantifier(((CallAgg)this.$node).getSetQuantifier()), symbol1Primitive, ExprNodeToStatementKt.toAstExpr(((CallAgg)this.$node).getArg()), this.$metas);
                    return expr;
                }
                if (exprNode instanceof Typed) {
                    switch (ExprNodeToStatementKt$WhenMappings.$EnumSwitchMapping$1[((Typed)this.$node).getOp().ordinal()]) {
                        case 1: {
                            expr = $this$build.cast(ExprNodeToStatementKt.toAstExpr(((Typed)this.$node).getExpr()), ExprNodeToStatementKt.access$toAstType(((Typed)this.$node).getType()), this.$metas);
                            return expr;
                        }
                        case 2: {
                            expr = $this$build.isType(ExprNodeToStatementKt.toAstExpr(((Typed)this.$node).getExpr()), ExprNodeToStatementKt.access$toAstType(((Typed)this.$node).getType()), this.$metas);
                            return expr;
                        }
                        default: {
                            throw new NoWhenBranchMatchedException();
                        }
                    }
                }
                if (exprNode instanceof Path) {
                    Collection<PartiqlAst.PathStep> collection;
                    void $this$mapTo$iv$iv;
                    Iterable iterable = ((Path)this.$node).getComponents();
                    PartiqlAst.Expr expr3 = ExprNodeToStatementKt.toAstExpr(((Path)this.$node).getRoot());
                    PartiqlAst.Builder builder = $this$build;
                    boolean $i$f$map = false;
                    Iterable $i$f$map2 = iterable;
                    Collection collection2 = new ArrayList<E>(CollectionsKt.collectionSizeOrDefault(iterable, 10));
                    boolean $i$f$mapTo = false;
                    for (T item$iv$iv : $this$mapTo$iv$iv) {
                        void it;
                        PathComponent item$iv$iv2 = (PathComponent)item$iv$iv;
                        collection = collection2;
                        boolean bl = false;
                        PartiqlAst.PathStep pathStep = ExprNodeToStatementKt.access$toAstPathStep((PathComponent)it);
                        collection.add(pathStep);
                    }
                    collection = (List)collection2;
                    expr = builder.path(expr3, (List<? extends PartiqlAst.PathStep>)collection, this.$metas);
                    return expr;
                }
                if (exprNode instanceof SimpleCase) {
                    Collection<PartiqlAst.ExprPair> collection;
                    Iterable iterable = ((SimpleCase)this.$node).getWhenClauses();
                    PartiqlAst.Builder builder = $this$build;
                    PartiqlAst.Expr expr4 = ExprNodeToStatementKt.toAstExpr(((SimpleCase)this.$node).getValueExpr());
                    PartiqlAst.Builder builder2 = $this$build;
                    boolean $i$f$map = false;
                    Iterable $this$mapTo$iv$iv = iterable;
                    Collection collection3 = new ArrayList<E>(CollectionsKt.collectionSizeOrDefault(iterable, 10));
                    boolean $i$f$mapTo = false;
                    for (T item$iv$iv : $this$mapTo$iv$iv) {
                        SimpleCaseWhen it = (SimpleCaseWhen)item$iv$iv;
                        collection = collection3;
                        boolean bl = false;
                        PartiqlAst.ExprPair exprPair = PartiqlAst.Builder.DefaultImpls.exprPair$default($this$build, ExprNodeToStatementKt.toAstExpr(it.getValueExpr()), ExprNodeToStatementKt.toAstExpr(it.getThenExpr()), null, 4, null);
                        collection.add(exprPair);
                    }
                    collection = (List)collection3;
                    ExprNode exprNode4 = ((SimpleCase)this.$node).getElseExpr();
                    expr = builder2.simpleCase(expr4, PartiqlAst.Builder.DefaultImpls.exprPairList$default(builder, (List)collection, null, 2, null), exprNode4 != null ? ExprNodeToStatementKt.toAstExpr(exprNode4) : null, this.$metas);
                    return expr;
                }
                if (exprNode instanceof SearchedCase) {
                    Collection<PartiqlAst.ExprPair> collection;
                    Iterable iterable = ((SearchedCase)this.$node).getWhenClauses();
                    PartiqlAst.Builder builder = $this$build;
                    PartiqlAst.Builder builder3 = $this$build;
                    boolean $i$f$map22 = false;
                    Iterable $this$mapTo$iv$iv = iterable;
                    Collection collection4 = new ArrayList<E>(CollectionsKt.collectionSizeOrDefault(iterable, 10));
                    boolean $i$f$mapTo = false;
                    for (T item$iv$iv : $this$mapTo$iv$iv) {
                        SearchedCaseWhen it = (SearchedCaseWhen)item$iv$iv;
                        collection = collection4;
                        boolean bl = false;
                        PartiqlAst.ExprPair exprPair = PartiqlAst.Builder.DefaultImpls.exprPair$default($this$build, ExprNodeToStatementKt.toAstExpr(it.getCondition()), ExprNodeToStatementKt.toAstExpr(it.getThenExpr()), null, 4, null);
                        collection.add(exprPair);
                    }
                    collection = (List)collection4;
                    ExprNode exprNode5 = ((SearchedCase)this.$node).getElseExpr();
                    expr = builder3.searchedCase(PartiqlAst.Builder.DefaultImpls.exprPairList$default(builder, (List)collection, null, 2, null), exprNode5 != null ? ExprNodeToStatementKt.toAstExpr(exprNode5) : null, this.$metas);
                    return expr;
                }
                if (exprNode instanceof Select) {
                    PartiqlAst.SetQuantifier.Distinct distinct2;
                    SetQuantifier setQuantifier = ((Select)this.$node).getSetQuantifier();
                    PartiqlAst.Builder builder = $this$build;
                    boolean $i$f$map22 = false;
                    boolean $this$mapTo$iv$iv22 = false;
                    SetQuantifier setQuantifier2 = setQuantifier;
                    boolean $i$a$-let-ExprNodeToStatementKt$toAstExpr$1$52 = false;
                    switch (ExprNodeToStatementKt$WhenMappings.$EnumSwitchMapping$2[setQuantifier2.ordinal()]) {
                        case 1: {
                            distinct2 = null;
                            break;
                        }
                        case 2: {
                            distinct2 = PartiqlAst.Builder.DefaultImpls.distinct$default($this$build, null, 1, null);
                            break;
                        }
                        default: {
                            throw new NoWhenBranchMatchedException();
                        }
                    }
                    PartiqlAst.SetQuantifier.Distinct distinct3 = distinct2;
                    LetSource letSource = ((Select)this.$node).getFromLet();
                    ExprNode exprNode6 = ((Select)this.$node).getWhere();
                    OrderBy orderBy = ((Select)this.$node).getOrderBy();
                    GroupBy groupBy2 = ((Select)this.$node).getGroupBy();
                    ExprNode exprNode7 = ((Select)this.$node).getHaving();
                    ExprNode exprNode8 = ((Select)this.$node).getLimit();
                    Map map2 = this.$metas;
                    PartiqlAst.Expr $i$f$map22 = exprNode8 != null ? ExprNodeToStatementKt.toAstExpr(exprNode8) : null;
                    PartiqlAst.Expr $this$mapTo$iv$iv22 = exprNode7 != null ? ExprNodeToStatementKt.toAstExpr(exprNode7) : null;
                    PartiqlAst.GroupBy groupBy3 = groupBy2 != null ? ExprNodeToStatementKt.access$toAstGroupSpec(groupBy2) : null;
                    PartiqlAst.OrderBy $i$a$-let-ExprNodeToStatementKt$toAstExpr$1$52 = orderBy != null ? ExprNodeToStatementKt.access$toAstOrderBySpec(orderBy) : null;
                    expr = builder.select(distinct3, ExprNodeToStatementKt.access$toAstSelectProject(((Select)this.$node).getProjection()), ExprNodeToStatementKt.access$toAstFromSource(((Select)this.$node).getFrom()), letSource != null ? ExprNodeToStatementKt.access$toAstLetSource(letSource) : null, exprNode6 != null ? ExprNodeToStatementKt.toAstExpr(exprNode6) : null, groupBy3, $this$mapTo$iv$iv22, $i$a$-let-ExprNodeToStatementKt$toAstExpr$1$52, $i$f$map22, map2);
                    return expr;
                }
                if (exprNode instanceof Struct) {
                    Collection<PartiqlAst.ExprPair> collection;
                    Iterable iterable = ((Struct)this.$node).getFields();
                    PartiqlAst.Builder builder = $this$build;
                    boolean $i$f$map = false;
                    Iterable $this$mapTo$iv$iv22 = iterable;
                    Collection collection5 = new ArrayList<E>(CollectionsKt.collectionSizeOrDefault(iterable, 10));
                    boolean $i$f$mapTo = false;
                    for (T item$iv$iv : $this$mapTo$iv$iv22) {
                        StructField it = (StructField)item$iv$iv;
                        collection = collection5;
                        boolean bl = false;
                        PartiqlAst.ExprPair exprPair = PartiqlAst.Builder.DefaultImpls.exprPair$default($this$build, ExprNodeToStatementKt.toAstExpr(it.getName()), ExprNodeToStatementKt.toAstExpr(it.getExpr()), null, 4, null);
                        collection.add(exprPair);
                    }
                    collection = (List)collection5;
                    expr = builder.struct((List<PartiqlAst.ExprPair>)collection, (Map<String, ? extends Object>)this.$metas);
                    return expr;
                }
                if (exprNode instanceof Seq) {
                    switch (ExprNodeToStatementKt$WhenMappings.$EnumSwitchMapping$3[((Seq)this.$node).getType().ordinal()]) {
                        case 1: {
                            Collection<PartiqlAst.Expr> collection;
                            Iterable iterable = ((Seq)this.$node).getValues();
                            PartiqlAst.Builder builder = $this$build;
                            boolean $i$f$map = false;
                            Iterable $this$mapTo$iv$iv = iterable;
                            Collection collection6 = new ArrayList<E>(CollectionsKt.collectionSizeOrDefault(iterable, 10));
                            boolean $i$f$mapTo = false;
                            for (T item$iv$iv : $this$mapTo$iv$iv) {
                                ExprNode it = (ExprNode)item$iv$iv;
                                collection = collection6;
                                boolean bl = false;
                                PartiqlAst.Expr expr5 = ExprNodeToStatementKt.toAstExpr(it);
                                collection.add(expr5);
                            }
                            collection = (List)collection6;
                            expr = builder.list((List<? extends PartiqlAst.Expr>)collection, (Map<String, ? extends Object>)this.$metas);
                            return expr;
                        }
                        case 2: {
                            Collection<PartiqlAst.Expr> collection;
                            Iterable iterable = ((Seq)this.$node).getValues();
                            PartiqlAst.Builder builder = $this$build;
                            boolean $i$f$map = false;
                            Iterable $this$mapTo$iv$iv = iterable;
                            Collection collection7 = new ArrayList<E>(CollectionsKt.collectionSizeOrDefault(iterable, 10));
                            boolean $i$f$mapTo = false;
                            for (T item$iv$iv : $this$mapTo$iv$iv) {
                                ExprNode it = (ExprNode)item$iv$iv;
                                collection = collection7;
                                boolean bl = false;
                                PartiqlAst.Expr expr6 = ExprNodeToStatementKt.toAstExpr(it);
                                collection.add(expr6);
                            }
                            collection = (List)collection7;
                            expr = builder.sexp((List<? extends PartiqlAst.Expr>)collection, (Map<String, ? extends Object>)this.$metas);
                            return expr;
                        }
                        case 3: {
                            Collection<PartiqlAst.Expr> collection;
                            Iterable iterable = ((Seq)this.$node).getValues();
                            PartiqlAst.Builder builder = $this$build;
                            boolean $i$f$map = false;
                            Iterable $this$mapTo$iv$iv = iterable;
                            Collection collection8 = new ArrayList<E>(CollectionsKt.collectionSizeOrDefault(iterable, 10));
                            boolean $i$f$mapTo = false;
                            for (T item$iv$iv : $this$mapTo$iv$iv) {
                                ExprNode it = (ExprNode)item$iv$iv;
                                collection = collection8;
                                boolean bl = false;
                                PartiqlAst.Expr expr7 = ExprNodeToStatementKt.toAstExpr(it);
                                collection.add(expr7);
                            }
                            collection = (List)collection8;
                            expr = builder.bag((List<? extends PartiqlAst.Expr>)collection, (Map<String, ? extends Object>)this.$metas);
                            return expr;
                        }
                        default: {
                            throw new NoWhenBranchMatchedException();
                        }
                    }
                }
                if (exprNode instanceof DataManipulation || exprNode instanceof CreateTable || exprNode instanceof CreateIndex || exprNode instanceof DropTable || exprNode instanceof DropIndex || exprNode instanceof Exec) {
                    String string = "Can't transform " + this.$node.getClass() + " to a PartiqlAst.expr }";
                    boolean bl = false;
                    throw (Throwable)new IllegalStateException(string.toString());
                }
                if (!(exprNode instanceof DateTimeType)) throw new NoWhenBranchMatchedException();
                ExprNode exprNode9 = this.$node;
                if (exprNode9 instanceof DateTimeType.Date) {
                    expr = $this$build.date(((DateTimeType.Date)this.$node).getYear(), ((DateTimeType.Date)this.$node).getMonth(), ((DateTimeType.Date)this.$node).getDay(), this.$metas);
                    return expr;
                } else {
                    if (!(exprNode9 instanceof DateTimeType.Time)) throw new NoWhenBranchMatchedException();
                    Integer n = ((DateTimeType.Time)this.$node).getTz_minutes();
                    expr = PartiqlAst.Builder.DefaultImpls.litTime$default($this$build, PartiqlAst.Builder.DefaultImpls.timeValue$default($this$build, ((DateTimeType.Time)this.$node).getHour(), ((DateTimeType.Time)this.$node).getMinute(), ((DateTimeType.Time)this.$node).getSecond(), ((DateTimeType.Time)this.$node).getNano(), ((DateTimeType.Time)this.$node).getPrecision(), n != null ? Long.valueOf(n.intValue()) : null, null, 64, null), null, 2, null);
                }
                return expr;
            }
            {
                this.$node = exprNode;
                this.$metas = map2;
                super(1);
            }
        });
    }

    private static final PartiqlAst.OrderBy toAstOrderBySpec(@NotNull OrderBy $this$toAstOrderBySpec) {
        OrderBy thiz = $this$toAstOrderBySpec;
        return (PartiqlAst.OrderBy)PartiqlAst.Companion.build((Function1)new Function1<PartiqlAst.Builder, PartiqlAst.OrderBy>(thiz){
            final /* synthetic */ OrderBy $thiz;

            /*
             * WARNING - void declaration
             */
            @NotNull
            public final PartiqlAst.OrderBy invoke(@NotNull PartiqlAst.Builder $this$build) {
                Collection<PartiqlAst.SortSpec> collection;
                void $this$mapTo$iv$iv;
                void $this$map$iv;
                Intrinsics.checkParameterIsNotNull($this$build, "$receiver");
                Iterable iterable = this.$thiz.getSortSpecItems();
                PartiqlAst.Builder builder = $this$build;
                boolean $i$f$map = false;
                void var4_5 = $this$map$iv;
                Collection destination$iv$iv = new ArrayList<E>(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
                boolean $i$f$mapTo = false;
                for (T item$iv$iv : $this$mapTo$iv$iv) {
                    void it;
                    SortSpec sortSpec = (SortSpec)item$iv$iv;
                    collection = destination$iv$iv;
                    boolean bl = false;
                    PartiqlAst.SortSpec sortSpec2 = PartiqlAst.Builder.DefaultImpls.sortSpec$default($this$build, ExprNodeToStatementKt.toAstExpr(it.getExpr()), ExprNodeToStatementKt.access$toAstOrderSpec(it.getOrderingSpec()), null, 4, null);
                    collection.add(sortSpec2);
                }
                collection = (List)destination$iv$iv;
                return PartiqlAst.Builder.DefaultImpls.orderBy$default(builder, (List)collection, null, 2, null);
            }
            {
                this.$thiz = orderBy;
                super(1);
            }
        });
    }

    private static final PartiqlAst.OrderingSpec toAstOrderSpec(@Nullable OrderingSpec $this$toAstOrderSpec) {
        return (PartiqlAst.OrderingSpec)PartiqlAst.Companion.build((Function1)new Function1<PartiqlAst.Builder, PartiqlAst.OrderingSpec>($this$toAstOrderSpec){
            final /* synthetic */ OrderingSpec $this_toAstOrderSpec;

            /*
             * Enabled aggressive block sorting
             */
            @NotNull
            public final PartiqlAst.OrderingSpec invoke(@NotNull PartiqlAst.Builder $this$build) {
                PartiqlAst.OrderingSpec orderingSpec;
                Intrinsics.checkParameterIsNotNull($this$build, "$receiver");
                OrderingSpec orderingSpec2 = this.$this_toAstOrderSpec;
                if (orderingSpec2 != null) {
                    switch (ExprNodeToStatementKt$WhenMappings.$EnumSwitchMapping$4[orderingSpec2.ordinal()]) {
                        case 1: {
                            orderingSpec = PartiqlAst.Builder.DefaultImpls.desc$default($this$build, null, 1, null);
                            return orderingSpec;
                        }
                    }
                }
                orderingSpec = PartiqlAst.Builder.DefaultImpls.asc$default($this$build, null, 1, null);
                return orderingSpec;
            }
            {
                this.$this_toAstOrderSpec = orderingSpec;
                super(1);
            }
        });
    }

    private static final PartiqlAst.GroupBy toAstGroupSpec(@NotNull GroupBy $this$toAstGroupSpec) {
        return (PartiqlAst.GroupBy)PartiqlAst.Companion.build((Function1)new Function1<PartiqlAst.Builder, PartiqlAst.GroupBy>($this$toAstGroupSpec){
            final /* synthetic */ GroupBy $this_toAstGroupSpec;

            /*
             * WARNING - void declaration
             */
            @NotNull
            public final PartiqlAst.GroupBy invoke(@NotNull PartiqlAst.Builder $this$build) {
                Object object;
                Collection<PartiqlAst.GroupKey> collection;
                void $this$mapTo$iv$iv;
                void $this$map$iv;
                Intrinsics.checkParameterIsNotNull($this$build, "$receiver");
                Iterable iterable = this.$this_toAstGroupSpec.getGroupByItems();
                PartiqlAst.Builder builder = $this$build;
                PartiqlAst.GroupingStrategy groupingStrategy = ExprNodeToStatementKt.access$toAstGroupStrategy(this.$this_toAstGroupSpec.getGrouping());
                PartiqlAst.Builder builder2 = $this$build;
                boolean $i$f$map = false;
                void var4_7 = $this$map$iv;
                Collection destination$iv$iv = new ArrayList<E>(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
                boolean $i$f$mapTo = false;
                for (T item$iv$iv : $this$mapTo$iv$iv) {
                    Object object2;
                    void it;
                    GroupByItem groupByItem = (GroupByItem)item$iv$iv;
                    collection = destination$iv$iv;
                    boolean bl = false;
                    Object object3 = it.getAsName();
                    if (object3 == null || (object3 = ((SymbolicName)object3).getMetas()) == null || (object3 = ExprNodeToStatementKt.toIonElementMetaContainer((MetaContainer)object3)) == null) {
                        object3 = IonMeta.emptyMetaContainer();
                    }
                    Map<String, Object> keyMetas = object3;
                    PartiqlAst.GroupKey groupKey = PartiqlAst.Builder.DefaultImpls.groupKey_$default($this$build, ExprNodeToStatementKt.toAstExpr(it.getExpr()), (object2 = it.getAsName()) != null && (object2 = ((SymbolicName)object2).getName()) != null ? PrimitiveUtilsKt.asPrimitive((String)object2, keyMetas) : null, null, 4, null);
                    collection.add(groupKey);
                }
                collection = (List)destination$iv$iv;
                return PartiqlAst.Builder.DefaultImpls.groupBy_$default(builder2, groupingStrategy, PartiqlAst.Builder.DefaultImpls.groupKeyList$default(builder, (List)collection, null, 2, null), (object = this.$this_toAstGroupSpec.getGroupName()) != null && (object = ((SymbolicName)object).getName()) != null ? PrimitiveUtilsKt.asPrimitive((String)object, ExprNodeToStatementKt.toIonElementMetaContainer(this.$this_toAstGroupSpec.getGroupName().getMetas())) : null, null, 8, null);
            }
            {
                this.$this_toAstGroupSpec = groupBy2;
                super(1);
            }
        });
    }

    private static final PartiqlAst.GroupingStrategy toAstGroupStrategy(@NotNull GroupingStrategy $this$toAstGroupStrategy) {
        return (PartiqlAst.GroupingStrategy)PartiqlAst.Companion.build((Function1)new Function1<PartiqlAst.Builder, PartiqlAst.GroupingStrategy>($this$toAstGroupStrategy){
            final /* synthetic */ GroupingStrategy $this_toAstGroupStrategy;

            @NotNull
            public final PartiqlAst.GroupingStrategy invoke(@NotNull PartiqlAst.Builder $this$build) {
                PartiqlAst.GroupingStrategy groupingStrategy;
                Intrinsics.checkParameterIsNotNull($this$build, "$receiver");
                switch (ExprNodeToStatementKt$WhenMappings.$EnumSwitchMapping$5[this.$this_toAstGroupStrategy.ordinal()]) {
                    case 1: {
                        groupingStrategy = PartiqlAst.Builder.DefaultImpls.groupFull$default($this$build, null, 1, null);
                        break;
                    }
                    case 2: {
                        groupingStrategy = PartiqlAst.Builder.DefaultImpls.groupPartial$default($this$build, null, 1, null);
                        break;
                    }
                    default: {
                        throw new NoWhenBranchMatchedException();
                    }
                }
                return groupingStrategy;
            }
            {
                this.$this_toAstGroupStrategy = groupingStrategy;
                super(1);
            }
        });
    }

    private static final PartiqlAst.CaseSensitivity toAstCaseSensitivity(@NotNull CaseSensitivity $this$toAstCaseSensitivity) {
        CaseSensitivity thiz = $this$toAstCaseSensitivity;
        return (PartiqlAst.CaseSensitivity)PartiqlAst.Companion.build((Function1)new Function1<PartiqlAst.Builder, PartiqlAst.CaseSensitivity>(thiz){
            final /* synthetic */ CaseSensitivity $thiz;

            @NotNull
            public final PartiqlAst.CaseSensitivity invoke(@NotNull PartiqlAst.Builder $this$build) {
                PartiqlAst.CaseSensitivity caseSensitivity;
                Intrinsics.checkParameterIsNotNull($this$build, "$receiver");
                switch (ExprNodeToStatementKt$WhenMappings.$EnumSwitchMapping$6[this.$thiz.ordinal()]) {
                    case 1: {
                        caseSensitivity = PartiqlAst.Builder.DefaultImpls.caseSensitive$default($this$build, null, 1, null);
                        break;
                    }
                    case 2: {
                        caseSensitivity = PartiqlAst.Builder.DefaultImpls.caseInsensitive$default($this$build, null, 1, null);
                        break;
                    }
                    default: {
                        throw new NoWhenBranchMatchedException();
                    }
                }
                return caseSensitivity;
            }
            {
                this.$thiz = caseSensitivity;
                super(1);
            }
        });
    }

    private static final PartiqlAst.ScopeQualifier toAstScopeQualifier(@NotNull ScopeQualifier $this$toAstScopeQualifier) {
        ScopeQualifier thiz = $this$toAstScopeQualifier;
        return (PartiqlAst.ScopeQualifier)PartiqlAst.Companion.build((Function1)new Function1<PartiqlAst.Builder, PartiqlAst.ScopeQualifier>(thiz){
            final /* synthetic */ ScopeQualifier $thiz;

            @NotNull
            public final PartiqlAst.ScopeQualifier invoke(@NotNull PartiqlAst.Builder $this$build) {
                PartiqlAst.ScopeQualifier scopeQualifier;
                Intrinsics.checkParameterIsNotNull($this$build, "$receiver");
                switch (ExprNodeToStatementKt$WhenMappings.$EnumSwitchMapping$7[this.$thiz.ordinal()]) {
                    case 1: {
                        scopeQualifier = PartiqlAst.Builder.DefaultImpls.localsFirst$default($this$build, null, 1, null);
                        break;
                    }
                    case 2: {
                        scopeQualifier = PartiqlAst.Builder.DefaultImpls.unqualified$default($this$build, null, 1, null);
                        break;
                    }
                    default: {
                        throw new NoWhenBranchMatchedException();
                    }
                }
                return scopeQualifier;
            }
            {
                this.$thiz = scopeQualifier;
                super(1);
            }
        });
    }

    private static final PartiqlAst.SetQuantifier toAstSetQuantifier(@NotNull SetQuantifier $this$toAstSetQuantifier) {
        SetQuantifier thiz = $this$toAstSetQuantifier;
        return (PartiqlAst.SetQuantifier)PartiqlAst.Companion.build((Function1)new Function1<PartiqlAst.Builder, PartiqlAst.SetQuantifier>(thiz){
            final /* synthetic */ SetQuantifier $thiz;

            @NotNull
            public final PartiqlAst.SetQuantifier invoke(@NotNull PartiqlAst.Builder $this$build) {
                PartiqlAst.SetQuantifier setQuantifier;
                Intrinsics.checkParameterIsNotNull($this$build, "$receiver");
                switch (ExprNodeToStatementKt$WhenMappings.$EnumSwitchMapping$8[this.$thiz.ordinal()]) {
                    case 1: {
                        setQuantifier = PartiqlAst.Builder.DefaultImpls.all$default($this$build, null, 1, null);
                        break;
                    }
                    case 2: {
                        setQuantifier = PartiqlAst.Builder.DefaultImpls.distinct$default($this$build, null, 1, null);
                        break;
                    }
                    default: {
                        throw new NoWhenBranchMatchedException();
                    }
                }
                return setQuantifier;
            }
            {
                this.$thiz = setQuantifier;
                super(1);
            }
        });
    }

    private static final PartiqlAst.Projection toAstSelectProject(@NotNull SelectProjection $this$toAstSelectProject) {
        SelectProjection thiz = $this$toAstSelectProject;
        return (PartiqlAst.Projection)PartiqlAst.Companion.build((Function1)new Function1<PartiqlAst.Builder, PartiqlAst.Projection>(thiz){
            final /* synthetic */ SelectProjection $thiz;

            /*
             * WARNING - void declaration
             */
            @NotNull
            public final PartiqlAst.Projection invoke(@NotNull PartiqlAst.Builder $this$build) {
                PartiqlAst.Projection projection;
                Intrinsics.checkParameterIsNotNull($this$build, "$receiver");
                SelectProjection selectProjection = this.$thiz;
                if (selectProjection instanceof SelectProjectionValue) {
                    projection = PartiqlAst.Builder.DefaultImpls.projectValue$default($this$build, ExprNodeToStatementKt.toAstExpr(((SelectProjectionValue)this.$thiz).getExpr()), null, 2, null);
                } else if (selectProjection instanceof SelectProjectionList) {
                    Object metas;
                    Iterator<T> iterator2;
                    boolean bl;
                    boolean $i$f$any;
                    Object $this$any$iv;
                    block19: {
                        $this$any$iv = ((SelectProjectionList)this.$thiz).getItems();
                        $i$f$any = false;
                        if ($this$any$iv instanceof Collection && ((Collection)$this$any$iv).isEmpty()) {
                            bl = false;
                        } else {
                            iterator2 = $this$any$iv.iterator();
                            while (iterator2.hasNext()) {
                                T element$iv = iterator2.next();
                                SelectListItem it = (SelectListItem)element$iv;
                                boolean bl2 = false;
                                if (!(it instanceof SelectListItemStar)) continue;
                                bl = true;
                                break block19;
                            }
                            bl = false;
                        }
                    }
                    if (bl) {
                        if (((SelectProjectionList)this.$thiz).getItems().size() > 1) {
                            $this$any$iv = "More than one select item when SELECT * was present.";
                            $i$f$any = false;
                            throw (Throwable)new IllegalStateException($this$any$iv.toString());
                        }
                        SelectListItem selectListItem = ((SelectProjectionList)this.$thiz).getItems().get(0);
                        if (selectListItem == null) {
                            throw new TypeCastException("null cannot be cast to non-null type org.partiql.lang.ast.SelectListItemStar");
                        }
                        metas = ExprNodeToStatementKt.toIonElementMetaContainer(((SelectListItemStar)selectListItem).getMetas());
                        projection = $this$build.projectStar((Map<String, ? extends Object>)metas);
                    } else {
                        Collection<PartiqlAst.ProjectItem> collection;
                        void $this$mapTo$iv$iv;
                        void $this$map$iv;
                        metas = ((SelectProjectionList)this.$thiz).getItems();
                        PartiqlAst.Builder builder = $this$build;
                        boolean $i$f$map = false;
                        iterator2 = $this$map$iv;
                        Collection destination$iv$iv = new ArrayList<E>(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
                        boolean $i$f$mapTo = false;
                        for (T item$iv$iv : $this$mapTo$iv$iv) {
                            PartiqlAst.ProjectItem projectItem;
                            void it;
                            SelectListItem selectListItem = (SelectListItem)item$iv$iv;
                            collection = destination$iv$iv;
                            boolean bl3 = false;
                            void var12_15 = it;
                            if (var12_15 instanceof SelectListItemExpr) {
                                SymbolicName symbolicName = ((SelectListItemExpr)it).getAsName();
                                projectItem = PartiqlAst.Builder.DefaultImpls.projectExpr_$default($this$build, ExprNodeToStatementKt.toAstExpr(((SelectListItemExpr)it).getExpr()), (SymbolPrimitive)(symbolicName != null ? ExprNodeToStatementKt.access$toPrimitive(symbolicName) : null), null, 4, null);
                            } else if (var12_15 instanceof SelectListItemProjectAll) {
                                projectItem = PartiqlAst.Builder.DefaultImpls.projectAll$default($this$build, ExprNodeToStatementKt.toAstExpr(((SelectListItemProjectAll)it).getExpr()), null, 2, null);
                            } else {
                                if (var12_15 instanceof SelectListItemStar) {
                                    String string = "this should happen due to `when` branch above.";
                                    boolean bl4 = false;
                                    throw (Throwable)new IllegalStateException(string.toString());
                                }
                                throw new NoWhenBranchMatchedException();
                            }
                            PartiqlAst.ProjectItem projectItem2 = projectItem;
                            collection.add(projectItem2);
                        }
                        collection = (List)destination$iv$iv;
                        projection = PartiqlAst.Builder.DefaultImpls.projectList$default(builder, (List)collection, null, 2, null);
                    }
                } else if (selectProjection instanceof SelectProjectionPivot) {
                    projection = PartiqlAst.Builder.DefaultImpls.projectPivot$default($this$build, ExprNodeToStatementKt.toAstExpr(((SelectProjectionPivot)this.$thiz).getNameExpr()), ExprNodeToStatementKt.toAstExpr(((SelectProjectionPivot)this.$thiz).getValueExpr()), null, 4, null);
                } else {
                    throw new NoWhenBranchMatchedException();
                }
                return projection;
            }
            {
                this.$thiz = selectProjection;
                super(1);
            }
        });
    }

    private static final PartiqlAst.FromSource toAstFromSource(@NotNull FromSource $this$toAstFromSource) {
        FromSource thiz = $this$toAstFromSource;
        Map<String, Object> metas = ExprNodeToStatementKt.toIonElementMetaContainer(thiz.metas());
        return (PartiqlAst.FromSource)PartiqlAst.Companion.build((Function1)new Function1<PartiqlAst.Builder, PartiqlAst.FromSource>(thiz, metas){
            final /* synthetic */ FromSource $thiz;
            final /* synthetic */ Map $metas;

            @NotNull
            public final PartiqlAst.FromSource invoke(@NotNull PartiqlAst.Builder $this$build) {
                PartiqlAst.FromSource fromSource;
                Intrinsics.checkParameterIsNotNull($this$build, "$receiver");
                FromSource fromSource2 = this.$thiz;
                if (fromSource2 instanceof FromSourceExpr) {
                    SymbolicName symbolicName = ((FromSourceExpr)this.$thiz).getVariables().getAsName();
                    SymbolicName symbolicName2 = ((FromSourceExpr)this.$thiz).getVariables().getAtName();
                    SymbolicName symbolicName3 = ((FromSourceExpr)this.$thiz).getVariables().getByName();
                    fromSource = $this$build.scan_(ExprNodeToStatementKt.toAstExpr(((FromSourceExpr)this.$thiz).getExpr()), (SymbolPrimitive)(symbolicName != null ? ExprNodeToStatementKt.access$toPrimitive(symbolicName) : null), (SymbolPrimitive)(symbolicName2 != null ? ExprNodeToStatementKt.access$toPrimitive(symbolicName2) : null), (SymbolPrimitive)(symbolicName3 != null ? ExprNodeToStatementKt.access$toPrimitive(symbolicName3) : null), ExprNodeToStatementKt.toIonElementMetaContainer(((FromSourceExpr)this.$thiz).getExpr().getMetas()));
                } else if (fromSource2 instanceof FromSourceJoin) {
                    PartiqlAst.JoinType joinType;
                    switch (ExprNodeToStatementKt$WhenMappings.$EnumSwitchMapping$9[((FromSourceJoin)this.$thiz).getJoinOp().ordinal()]) {
                        case 1: {
                            joinType = PartiqlAst.Builder.DefaultImpls.inner$default($this$build, null, 1, null);
                            break;
                        }
                        case 2: {
                            joinType = PartiqlAst.Builder.DefaultImpls.left$default($this$build, null, 1, null);
                            break;
                        }
                        case 3: {
                            joinType = PartiqlAst.Builder.DefaultImpls.right$default($this$build, null, 1, null);
                            break;
                        }
                        case 4: {
                            joinType = PartiqlAst.Builder.DefaultImpls.full$default($this$build, null, 1, null);
                            break;
                        }
                        default: {
                            throw new NoWhenBranchMatchedException();
                        }
                    }
                    PartiqlAst.JoinType jt = joinType;
                    fromSource = $this$build.join(jt, ExprNodeToStatementKt.access$toAstFromSource(((FromSourceJoin)this.$thiz).getLeftRef()), ExprNodeToStatementKt.access$toAstFromSource(((FromSourceJoin)this.$thiz).getRightRef()), ((FromSourceJoin)this.$thiz).getMetas().hasMeta("$is_implicit_join") ? null : ExprNodeToStatementKt.toAstExpr(((FromSourceJoin)this.$thiz).getCondition()), this.$metas);
                } else if (fromSource2 instanceof FromSourceUnpivot) {
                    SymbolicName symbolicName = ((FromSourceUnpivot)this.$thiz).getVariables().getAsName();
                    SymbolicName symbolicName4 = ((FromSourceUnpivot)this.$thiz).getVariables().getAtName();
                    SymbolicName symbolicName5 = ((FromSourceUnpivot)this.$thiz).getVariables().getByName();
                    fromSource = $this$build.unpivot_(ExprNodeToStatementKt.toAstExpr(((FromSourceUnpivot)this.$thiz).getExpr()), (SymbolPrimitive)(symbolicName != null ? ExprNodeToStatementKt.access$toPrimitive(symbolicName) : null), (SymbolPrimitive)(symbolicName4 != null ? ExprNodeToStatementKt.access$toPrimitive(symbolicName4) : null), (SymbolPrimitive)(symbolicName5 != null ? ExprNodeToStatementKt.access$toPrimitive(symbolicName5) : null), ExprNodeToStatementKt.toIonElementMetaContainer(((FromSourceUnpivot)this.$thiz).getMetas()));
                } else {
                    throw new NoWhenBranchMatchedException();
                }
                return fromSource;
            }
            {
                this.$thiz = fromSource;
                this.$metas = map2;
                super(1);
            }
        });
    }

    private static final PartiqlAst.Let toAstLetSource(@NotNull LetSource $this$toAstLetSource) {
        LetSource thiz = $this$toAstLetSource;
        return (PartiqlAst.Let)PartiqlAst.Companion.build((Function1)new Function1<PartiqlAst.Builder, PartiqlAst.Let>(thiz){
            final /* synthetic */ LetSource $thiz;

            /*
             * WARNING - void declaration
             */
            @NotNull
            public final PartiqlAst.Let invoke(@NotNull PartiqlAst.Builder $this$build) {
                Collection<PartiqlAst.LetBinding> collection;
                void $this$mapTo$iv$iv;
                void $this$map$iv;
                Intrinsics.checkParameterIsNotNull($this$build, "$receiver");
                Iterable iterable = this.$thiz.getBindings();
                PartiqlAst.Builder builder = $this$build;
                boolean $i$f$map = false;
                void var4_5 = $this$map$iv;
                Collection destination$iv$iv = new ArrayList<E>(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
                boolean $i$f$mapTo = false;
                for (T item$iv$iv : $this$mapTo$iv$iv) {
                    void it;
                    LetBinding letBinding = (LetBinding)item$iv$iv;
                    collection = destination$iv$iv;
                    boolean bl = false;
                    PartiqlAst.LetBinding letBinding2 = PartiqlAst.Builder.DefaultImpls.letBinding$default($this$build, ExprNodeToStatementKt.toAstExpr(it.getExpr()), it.getName().getName(), null, 4, null);
                    collection.add(letBinding2);
                }
                collection = (List)destination$iv$iv;
                return PartiqlAst.Builder.DefaultImpls.let$default(builder, (List)collection, null, 2, null);
            }
            {
                this.$thiz = letSource;
                super(1);
            }
        });
    }

    private static final PartiqlAst.PathStep toAstPathStep(@NotNull PathComponent $this$toAstPathStep) {
        PathComponent thiz = $this$toAstPathStep;
        return (PartiqlAst.PathStep)PartiqlAst.Companion.build((Function1)new Function1<PartiqlAst.Builder, PartiqlAst.PathStep>(thiz){
            final /* synthetic */ PathComponent $thiz;

            @NotNull
            public final PartiqlAst.PathStep invoke(@NotNull PartiqlAst.Builder $this$build) {
                PartiqlAst.PathStep pathStep;
                Intrinsics.checkParameterIsNotNull($this$build, "$receiver");
                PathComponent pathComponent = this.$thiz;
                if (pathComponent instanceof PathComponentExpr) {
                    pathStep = PartiqlAst.Builder.DefaultImpls.pathExpr$default($this$build, ExprNodeToStatementKt.toAstExpr(((PathComponentExpr)this.$thiz).getExpr()), ExprNodeToStatementKt.access$toAstCaseSensitivity(((PathComponentExpr)this.$thiz).getCase()), null, 4, null);
                } else if (pathComponent instanceof PathComponentUnpivot) {
                    pathStep = $this$build.pathUnpivot(ExprNodeToStatementKt.toIonElementMetaContainer(((PathComponentUnpivot)this.$thiz).getMetas()));
                } else if (pathComponent instanceof PathComponentWildcard) {
                    pathStep = $this$build.pathWildcard(ExprNodeToStatementKt.toIonElementMetaContainer(((PathComponentWildcard)this.$thiz).getMetas()));
                } else {
                    throw new NoWhenBranchMatchedException();
                }
                return pathStep;
            }
            {
                this.$thiz = pathComponent;
                super(1);
            }
        });
    }

    private static final PartiqlAst.OnConflict toAstOnConflict(@NotNull OnConflict $this$toAstOnConflict) {
        OnConflict thiz = $this$toAstOnConflict;
        return (PartiqlAst.OnConflict)PartiqlAst.Companion.build((Function1)new Function1<PartiqlAst.Builder, PartiqlAst.OnConflict>(thiz){
            final /* synthetic */ OnConflict $thiz;

            @NotNull
            public final PartiqlAst.OnConflict invoke(@NotNull PartiqlAst.Builder $this$build) {
                Intrinsics.checkParameterIsNotNull($this$build, "$receiver");
                switch (ExprNodeToStatementKt$WhenMappings.$EnumSwitchMapping$10[this.$thiz.getConflictAction().ordinal()]) {
                    case 1: {
                        break;
                    }
                    default: {
                        throw new NoWhenBranchMatchedException();
                    }
                }
                return PartiqlAst.Builder.DefaultImpls.onConflict$default($this$build, ExprNodeToStatementKt.toAstExpr(this.$thiz.getCondition()), PartiqlAst.Builder.DefaultImpls.doNothing$default($this$build, null, 1, null), null, 4, null);
            }
            {
                this.$thiz = onConflict;
                super(1);
            }
        });
    }

    private static final PartiqlAst.Statement toAstDml(@NotNull DataManipulation $this$toAstDml) {
        DataManipulation thiz = $this$toAstDml;
        return (PartiqlAst.Statement)PartiqlAst.Companion.build((Function1)new Function1<PartiqlAst.Builder, PartiqlAst.Statement.Dml>(thiz){
            final /* synthetic */ DataManipulation $thiz;

            @NotNull
            public final PartiqlAst.Statement.Dml invoke(@NotNull PartiqlAst.Builder $this$build) {
                Intrinsics.checkParameterIsNotNull($this$build, "$receiver");
                DmlOpList dmlOps = this.$thiz.getDmlOperations();
                PartiqlAst.DmlOpList dmlOps2 = ExprNodeToStatementKt.access$toAstDmlOps(dmlOps, this.$thiz);
                FromSource fromSource = this.$thiz.getFrom();
                ExprNode exprNode = this.$thiz.getWhere();
                ReturningExpr returningExpr = this.$thiz.getReturning();
                return $this$build.dml(dmlOps2, fromSource != null ? ExprNodeToStatementKt.access$toAstFromSource(fromSource) : null, exprNode != null ? ExprNodeToStatementKt.toAstExpr(exprNode) : null, returningExpr != null ? ExprNodeToStatementKt.access$toAstReturningExpr(returningExpr) : null, ExprNodeToStatementKt.toIonElementMetaContainer(this.$thiz.getMetas()));
            }
            {
                this.$thiz = dataManipulation;
                super(1);
            }
        });
    }

    private static final PartiqlAst.DmlOpList toAstDmlOps(@NotNull DmlOpList $this$toAstDmlOps, DataManipulation dml) {
        return (PartiqlAst.DmlOpList)PartiqlAst.Companion.build((Function1)new Function1<PartiqlAst.Builder, PartiqlAst.DmlOpList>($this$toAstDmlOps, dml){
            final /* synthetic */ DmlOpList $this_toAstDmlOps;
            final /* synthetic */ DataManipulation $dml;

            /*
             * WARNING - void declaration
             */
            @NotNull
            public final PartiqlAst.DmlOpList invoke(@NotNull PartiqlAst.Builder $this$build) {
                Collection<PartiqlAst.DmlOp> collection;
                void $this$mapTo$iv$iv;
                void $this$map$iv;
                Intrinsics.checkParameterIsNotNull($this$build, "$receiver");
                Iterable iterable = this.$this_toAstDmlOps.getOps();
                PartiqlAst.Builder builder = $this$build;
                boolean $i$f$map = false;
                void var4_5 = $this$map$iv;
                Collection destination$iv$iv = new ArrayList<E>(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
                boolean $i$f$mapTo = false;
                for (T item$iv$iv : $this$mapTo$iv$iv) {
                    void it;
                    DataManipulationOperation dataManipulationOperation = (DataManipulationOperation)item$iv$iv;
                    collection = destination$iv$iv;
                    boolean bl = false;
                    PartiqlAst.DmlOp dmlOp = ExprNodeToStatementKt.access$toAstDmlOp((DataManipulationOperation)it, this.$dml);
                    collection.add(dmlOp);
                }
                collection = (List)destination$iv$iv;
                return builder.dmlOpList((List<? extends PartiqlAst.DmlOp>)collection, ExprNodeToStatementKt.toIonElementMetaContainer(this.$dml.getMetas()));
            }
            {
                this.$this_toAstDmlOps = dmlOpList;
                this.$dml = dataManipulation;
                super(1);
            }
        });
    }

    private static final PartiqlAst.DmlOp toAstDmlOp(@NotNull DataManipulationOperation $this$toAstDmlOp, DataManipulation dml) {
        return (PartiqlAst.DmlOp)PartiqlAst.Companion.build((Function1)new Function1<PartiqlAst.Builder, PartiqlAst.DmlOp>($this$toAstDmlOp, dml){
            final /* synthetic */ DataManipulationOperation $this_toAstDmlOp;
            final /* synthetic */ DataManipulation $dml;

            @NotNull
            public final PartiqlAst.DmlOp invoke(@NotNull PartiqlAst.Builder $this$build) {
                PartiqlAst.DmlOp dmlOp;
                Intrinsics.checkParameterIsNotNull($this$build, "$receiver");
                DataManipulationOperation thiz = this.$this_toAstDmlOp;
                if (thiz instanceof InsertOp) {
                    dmlOp = PartiqlAst.Builder.DefaultImpls.insert$default($this$build, ExprNodeToStatementKt.toAstExpr(((InsertOp)thiz).getLvalue()), ExprNodeToStatementKt.toAstExpr(((InsertOp)thiz).getValues()), null, 4, null);
                } else if (thiz instanceof InsertValueOp) {
                    ExprNode exprNode = ((InsertValueOp)thiz).getPosition();
                    OnConflict onConflict = ((InsertValueOp)thiz).getOnConflict();
                    dmlOp = $this$build.insertValue(ExprNodeToStatementKt.toAstExpr(((InsertValueOp)thiz).getLvalue()), ExprNodeToStatementKt.toAstExpr(((InsertValueOp)thiz).getValue()), exprNode != null ? ExprNodeToStatementKt.toAstExpr(exprNode) : null, onConflict != null ? ExprNodeToStatementKt.access$toAstOnConflict(onConflict) : null, ExprNodeToStatementKt.toIonElementMetaContainer(this.$dml.getMetas()));
                } else if (thiz instanceof AssignmentOp) {
                    dmlOp = PartiqlAst.Builder.DefaultImpls.set$default($this$build, PartiqlAst.Builder.DefaultImpls.assignment$default($this$build, ExprNodeToStatementKt.toAstExpr(((AssignmentOp)thiz).getAssignment().getLvalue()), ExprNodeToStatementKt.toAstExpr(((AssignmentOp)thiz).getAssignment().getRvalue()), null, 4, null), null, 2, null);
                } else if (thiz instanceof RemoveOp) {
                    dmlOp = PartiqlAst.Builder.DefaultImpls.remove$default($this$build, ExprNodeToStatementKt.toAstExpr(((RemoveOp)thiz).getLvalue()), null, 2, null);
                } else if (Intrinsics.areEqual(thiz, DeleteOp.INSTANCE)) {
                    dmlOp = PartiqlAst.Builder.DefaultImpls.delete$default($this$build, null, 1, null);
                } else {
                    throw new NoWhenBranchMatchedException();
                }
                return dmlOp;
            }
            {
                this.$this_toAstDmlOp = dataManipulationOperation;
                this.$dml = dataManipulation;
                super(1);
            }
        });
    }

    private static final PartiqlAst.ReturningExpr toAstReturningExpr(@NotNull ReturningExpr $this$toAstReturningExpr) {
        ReturningExpr thiz = $this$toAstReturningExpr;
        return (PartiqlAst.ReturningExpr)PartiqlAst.Companion.build((Function1)new Function1<PartiqlAst.Builder, PartiqlAst.ReturningExpr>(thiz){
            final /* synthetic */ ReturningExpr $thiz;

            /*
             * WARNING - void declaration
             */
            @NotNull
            public final PartiqlAst.ReturningExpr invoke(@NotNull PartiqlAst.Builder $this$build) {
                Collection<PartiqlAst.ReturningElem> collection;
                void $this$mapTo$iv$iv;
                void $this$map$iv;
                Intrinsics.checkParameterIsNotNull($this$build, "$receiver");
                Iterable iterable = this.$thiz.getReturningElems();
                PartiqlAst.Builder builder = $this$build;
                boolean $i$f$map = false;
                void var4_5 = $this$map$iv;
                Collection destination$iv$iv = new ArrayList<E>(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
                boolean $i$f$mapTo = false;
                for (T item$iv$iv : $this$mapTo$iv$iv) {
                    void it;
                    ReturningElem returningElem = (ReturningElem)item$iv$iv;
                    collection = destination$iv$iv;
                    boolean bl = false;
                    PartiqlAst.ReturningElem returningElem2 = PartiqlAst.Builder.DefaultImpls.returningElem$default($this$build, ExprNodeToStatementKt.access$toReturningMapping(it.getReturningMapping()), ExprNodeToStatementKt.access$toColumnComponent(it.getColumnComponent()), null, 4, null);
                    collection.add(returningElem2);
                }
                collection = (List)destination$iv$iv;
                return PartiqlAst.Builder.DefaultImpls.returningExpr$default(builder, (List)collection, null, 2, null);
            }
            {
                this.$thiz = returningExpr;
                super(1);
            }
        });
    }

    private static final PartiqlAst.ColumnComponent toColumnComponent(@NotNull ColumnComponent $this$toColumnComponent) {
        return (PartiqlAst.ColumnComponent)PartiqlAst.Companion.build((Function1)new Function1<PartiqlAst.Builder, PartiqlAst.ColumnComponent>($this$toColumnComponent){
            final /* synthetic */ ColumnComponent $this_toColumnComponent;

            @NotNull
            public final PartiqlAst.ColumnComponent invoke(@NotNull PartiqlAst.Builder $this$build) {
                PartiqlAst.ColumnComponent columnComponent;
                Intrinsics.checkParameterIsNotNull($this$build, "$receiver");
                ColumnComponent thiz = this.$this_toColumnComponent;
                if (thiz instanceof ReturningWildcard) {
                    columnComponent = PartiqlAst.Builder.DefaultImpls.returningWildcard$default($this$build, null, 1, null);
                } else if (thiz instanceof ReturningColumn) {
                    columnComponent = PartiqlAst.Builder.DefaultImpls.returningColumn$default($this$build, ExprNodeToStatementKt.toAstExpr(((ReturningColumn)thiz).getColumn()), null, 2, null);
                } else {
                    throw new NoWhenBranchMatchedException();
                }
                return columnComponent;
            }
            {
                this.$this_toColumnComponent = columnComponent;
                super(1);
            }
        });
    }

    private static final PartiqlAst.ReturningMapping toReturningMapping(@NotNull ReturningMapping $this$toReturningMapping) {
        return (PartiqlAst.ReturningMapping)PartiqlAst.Companion.build((Function1)new Function1<PartiqlAst.Builder, PartiqlAst.ReturningMapping>($this$toReturningMapping){
            final /* synthetic */ ReturningMapping $this_toReturningMapping;

            @NotNull
            public final PartiqlAst.ReturningMapping invoke(@NotNull PartiqlAst.Builder $this$build) {
                PartiqlAst.ReturningMapping returningMapping;
                Intrinsics.checkParameterIsNotNull($this$build, "$receiver");
                switch (ExprNodeToStatementKt$WhenMappings.$EnumSwitchMapping$11[this.$this_toReturningMapping.ordinal()]) {
                    case 1: {
                        returningMapping = PartiqlAst.Builder.DefaultImpls.modifiedOld$default($this$build, null, 1, null);
                        break;
                    }
                    case 2: {
                        returningMapping = PartiqlAst.Builder.DefaultImpls.modifiedNew$default($this$build, null, 1, null);
                        break;
                    }
                    case 3: {
                        returningMapping = PartiqlAst.Builder.DefaultImpls.allOld$default($this$build, null, 1, null);
                        break;
                    }
                    case 4: {
                        returningMapping = PartiqlAst.Builder.DefaultImpls.allNew$default($this$build, null, 1, null);
                        break;
                    }
                    default: {
                        throw new NoWhenBranchMatchedException();
                    }
                }
                return returningMapping;
            }
            {
                this.$this_toReturningMapping = returningMapping;
                super(1);
            }
        });
    }

    private static final PartiqlAst.Type toAstType(@NotNull DataType $this$toAstType) {
        DataType thiz = $this$toAstType;
        Map<String, Object> metas = ExprNodeToStatementKt.toIonElementMetaContainer(thiz.getMetas());
        Long l = CollectionsKt.getOrNull(thiz.getArgs(), 0);
        Long arg1 = l != null ? Long.valueOf(l) : null;
        Long l2 = CollectionsKt.getOrNull(thiz.getArgs(), 1);
        Long arg2 = l2 != null ? Long.valueOf(l2) : null;
        return (PartiqlAst.Type)PartiqlAst.Companion.build((Function1)new Function1<PartiqlAst.Builder, PartiqlAst.Type>(thiz, metas, arg1, arg2){
            final /* synthetic */ DataType $thiz;
            final /* synthetic */ Map $metas;
            final /* synthetic */ Long $arg1;
            final /* synthetic */ Long $arg2;

            @NotNull
            public final PartiqlAst.Type invoke(@NotNull PartiqlAst.Builder $this$build) {
                PartiqlAst.Type type;
                Intrinsics.checkParameterIsNotNull($this$build, "$receiver");
                switch (ExprNodeToStatementKt$WhenMappings.$EnumSwitchMapping$12[this.$thiz.getSqlDataType().ordinal()]) {
                    case 1: {
                        type = $this$build.missingType(this.$metas);
                        break;
                    }
                    case 2: {
                        type = $this$build.nullType(this.$metas);
                        break;
                    }
                    case 3: {
                        type = $this$build.booleanType(this.$metas);
                        break;
                    }
                    case 4: {
                        type = $this$build.smallintType(this.$metas);
                        break;
                    }
                    case 5: {
                        type = $this$build.integerType(this.$metas);
                        break;
                    }
                    case 6: {
                        type = $this$build.floatType(this.$arg1, this.$metas);
                        break;
                    }
                    case 7: {
                        type = $this$build.realType(this.$metas);
                        break;
                    }
                    case 8: {
                        type = $this$build.doublePrecisionType(this.$metas);
                        break;
                    }
                    case 9: {
                        type = $this$build.decimalType(this.$arg1, this.$arg2, this.$metas);
                        break;
                    }
                    case 10: {
                        type = $this$build.numericType(this.$arg1, this.$arg2, this.$metas);
                        break;
                    }
                    case 11: {
                        type = $this$build.timestampType(this.$metas);
                        break;
                    }
                    case 12: {
                        type = $this$build.characterType(this.$arg1, this.$metas);
                        break;
                    }
                    case 13: {
                        type = $this$build.characterVaryingType(this.$arg1, this.$metas);
                        break;
                    }
                    case 14: {
                        type = $this$build.stringType(this.$metas);
                        break;
                    }
                    case 15: {
                        type = $this$build.symbolType(this.$metas);
                        break;
                    }
                    case 16: {
                        type = $this$build.clobType(this.$metas);
                        break;
                    }
                    case 17: {
                        type = $this$build.blobType(this.$metas);
                        break;
                    }
                    case 18: {
                        type = $this$build.structType(this.$metas);
                        break;
                    }
                    case 19: {
                        type = $this$build.tupleType(this.$metas);
                        break;
                    }
                    case 20: {
                        type = $this$build.listType(this.$metas);
                        break;
                    }
                    case 21: {
                        type = $this$build.sexpType(this.$metas);
                        break;
                    }
                    case 22: {
                        type = $this$build.bagType(this.$metas);
                        break;
                    }
                    case 23: {
                        type = $this$build.dateType(this.$metas);
                        break;
                    }
                    case 24: {
                        type = $this$build.timeType(this.$arg1, this.$metas);
                        break;
                    }
                    case 25: {
                        type = $this$build.timeWithTimeZoneType(this.$arg1, this.$metas);
                        break;
                    }
                    default: {
                        throw new NoWhenBranchMatchedException();
                    }
                }
                return type;
            }
            {
                this.$thiz = dataType;
                this.$metas = map2;
                this.$arg1 = l;
                this.$arg2 = l2;
                super(1);
            }
        });
    }

    private static final SymbolPrimitive toPrimitive(@NotNull SymbolicName $this$toPrimitive) {
        return new SymbolPrimitive($this$toPrimitive.getName(), ExprNodeToStatementKt.toIonElementMetaContainer($this$toPrimitive.getMetas()));
    }

    public static final /* synthetic */ PartiqlAst.CaseSensitivity access$toAstCaseSensitivity(CaseSensitivity $this$access_u24toAstCaseSensitivity) {
        return ExprNodeToStatementKt.toAstCaseSensitivity($this$access_u24toAstCaseSensitivity);
    }

    public static final /* synthetic */ SymbolPrimitive access$toSymbolPrimitive(SymbolicName $this$access_u24toSymbolPrimitive) {
        return ExprNodeToStatementKt.toSymbolPrimitive($this$access_u24toSymbolPrimitive);
    }

    public static final /* synthetic */ PartiqlAst.ScopeQualifier access$toAstScopeQualifier(ScopeQualifier $this$access_u24toAstScopeQualifier) {
        return ExprNodeToStatementKt.toAstScopeQualifier($this$access_u24toAstScopeQualifier);
    }

    public static final /* synthetic */ PartiqlAst.SetQuantifier access$toAstSetQuantifier(SetQuantifier $this$access_u24toAstSetQuantifier) {
        return ExprNodeToStatementKt.toAstSetQuantifier($this$access_u24toAstSetQuantifier);
    }

    public static final /* synthetic */ PartiqlAst.Type access$toAstType(DataType $this$access_u24toAstType) {
        return ExprNodeToStatementKt.toAstType($this$access_u24toAstType);
    }

    public static final /* synthetic */ PartiqlAst.PathStep access$toAstPathStep(PathComponent $this$access_u24toAstPathStep) {
        return ExprNodeToStatementKt.toAstPathStep($this$access_u24toAstPathStep);
    }

    public static final /* synthetic */ PartiqlAst.Projection access$toAstSelectProject(SelectProjection $this$access_u24toAstSelectProject) {
        return ExprNodeToStatementKt.toAstSelectProject($this$access_u24toAstSelectProject);
    }

    public static final /* synthetic */ PartiqlAst.FromSource access$toAstFromSource(FromSource $this$access_u24toAstFromSource) {
        return ExprNodeToStatementKt.toAstFromSource($this$access_u24toAstFromSource);
    }

    public static final /* synthetic */ PartiqlAst.Let access$toAstLetSource(LetSource $this$access_u24toAstLetSource) {
        return ExprNodeToStatementKt.toAstLetSource($this$access_u24toAstLetSource);
    }

    public static final /* synthetic */ PartiqlAst.OrderBy access$toAstOrderBySpec(OrderBy $this$access_u24toAstOrderBySpec) {
        return ExprNodeToStatementKt.toAstOrderBySpec($this$access_u24toAstOrderBySpec);
    }

    public static final /* synthetic */ PartiqlAst.GroupBy access$toAstGroupSpec(GroupBy $this$access_u24toAstGroupSpec) {
        return ExprNodeToStatementKt.toAstGroupSpec($this$access_u24toAstGroupSpec);
    }

    public static final /* synthetic */ PartiqlAst.OrderingSpec access$toAstOrderSpec(OrderingSpec $this$access_u24toAstOrderSpec) {
        return ExprNodeToStatementKt.toAstOrderSpec($this$access_u24toAstOrderSpec);
    }

    public static final /* synthetic */ PartiqlAst.GroupingStrategy access$toAstGroupStrategy(GroupingStrategy $this$access_u24toAstGroupStrategy) {
        return ExprNodeToStatementKt.toAstGroupStrategy($this$access_u24toAstGroupStrategy);
    }

    public static final /* synthetic */ SymbolPrimitive access$toPrimitive(SymbolicName $this$access_u24toPrimitive) {
        return ExprNodeToStatementKt.toPrimitive($this$access_u24toPrimitive);
    }

    public static final /* synthetic */ PartiqlAst.DmlOpList access$toAstDmlOps(DmlOpList $this$access_u24toAstDmlOps, DataManipulation dml) {
        return ExprNodeToStatementKt.toAstDmlOps($this$access_u24toAstDmlOps, dml);
    }

    public static final /* synthetic */ PartiqlAst.ReturningExpr access$toAstReturningExpr(ReturningExpr $this$access_u24toAstReturningExpr) {
        return ExprNodeToStatementKt.toAstReturningExpr($this$access_u24toAstReturningExpr);
    }

    public static final /* synthetic */ PartiqlAst.DmlOp access$toAstDmlOp(DataManipulationOperation $this$access_u24toAstDmlOp, DataManipulation dml) {
        return ExprNodeToStatementKt.toAstDmlOp($this$access_u24toAstDmlOp, dml);
    }

    public static final /* synthetic */ PartiqlAst.OnConflict access$toAstOnConflict(OnConflict $this$access_u24toAstOnConflict) {
        return ExprNodeToStatementKt.toAstOnConflict($this$access_u24toAstOnConflict);
    }

    public static final /* synthetic */ PartiqlAst.ReturningMapping access$toReturningMapping(ReturningMapping $this$access_u24toReturningMapping) {
        return ExprNodeToStatementKt.toReturningMapping($this$access_u24toReturningMapping);
    }

    public static final /* synthetic */ PartiqlAst.ColumnComponent access$toColumnComponent(ColumnComponent $this$access_u24toColumnComponent) {
        return ExprNodeToStatementKt.toColumnComponent($this$access_u24toColumnComponent);
    }
}

