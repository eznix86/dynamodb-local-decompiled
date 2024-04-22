/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package org.partiql.lang.ast.passes;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.comparisons.ComparisonsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;
import org.partiql.lang.ast.SourceLocationMeta;
import org.partiql.lang.ast.SourceLocationMetaKt;
import org.partiql.lang.ast.passes.StatementRedactorKt;
import org.partiql.lang.domains.PartiqlAst;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000\u0090\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\"\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\b\u0002\u0018\u00002\u00020\u0001BK\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u0005\u0012.\u0010\u0006\u001a*\u0012\u0004\u0012\u00020\u0003\u0012 \u0012\u001e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\t\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\t0\bj\u0002`\u000b0\u0007\u00a2\u0006\u0002\u0010\fJ\u0016\u0010\u0011\u001a\u00020\u00122\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\n0\tH\u0002J\u0006\u0010\u0014\u001a\u00020\u0003J\u0016\u0010\u0015\u001a\u00020\u00122\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\n0\tH\u0002J\u0010\u0010\u0016\u001a\u00020\u00122\u0006\u0010\u0017\u001a\u00020\u0018H\u0002J\u0016\u0010\u0019\u001a\u00020\u00122\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\n0\tH\u0002J\u0010\u0010\u001a\u001a\u00020\u00122\u0006\u0010\u0017\u001a\u00020\nH\u0002J\u0010\u0010\u001b\u001a\u00020\u00122\u0006\u0010\u001c\u001a\u00020\u001dH\u0002J\u0016\u0010\u001e\u001a\u00020\u00122\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\n0\tH\u0002J\u0010\u0010\u001f\u001a\u00020\u00122\u0006\u0010\u0017\u001a\u00020\nH\u0002J\u0016\u0010 \u001a\b\u0012\u0004\u0012\u00020\u00120\t2\u0006\u0010!\u001a\u00020\"H\u0002J\u0016\u0010 \u001a\b\u0012\u0004\u0012\u00020\u00120\t2\u0006\u0010!\u001a\u00020#H\u0002J\u0016\u0010 \u001a\b\u0012\u0004\u0012\u00020\u00120\t2\u0006\u0010!\u001a\u00020$H\u0002J\u0010\u0010%\u001a\u00020\u00122\u0006\u0010&\u001a\u00020'H\u0002J\u0010\u0010(\u001a\u00020\u00122\u0006\u0010&\u001a\u00020'H\u0002J\u0010\u0010)\u001a\u00020\u00122\u0006\u0010*\u001a\u00020+H\u0002J\u0010\u0010,\u001a\u00020\u00122\u0006\u0010\u0017\u001a\u00020-H\u0016J\u0010\u0010.\u001a\u00020\u00122\u0006\u0010\u0017\u001a\u00020/H\u0014J\u0010\u00100\u001a\u00020\u00122\u0006\u0010\u0017\u001a\u000201H\u0014J\u0010\u00102\u001a\u00020\u00122\u0006\u0010\u0017\u001a\u000203H\u0014J\f\u00104\u001a\u000205*\u00020\nH\u0002R\u0014\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001e\u0010\r\u001a\u0012\u0012\u0004\u0012\u00020\u000f0\u000ej\b\u0012\u0004\u0012\u00020\u000f`\u0010X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R6\u0010\u0006\u001a*\u0012\u0004\u0012\u00020\u0003\u0012 \u0012\u001e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\t\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\t0\bj\u0002`\u000b0\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u00066"}, d2={"Lorg/partiql/lang/ast/passes/StatementRedactionVisitor;", "Lorg/partiql/lang/domains/PartiqlAst$Visitor;", "statement", "", "safeFieldNames", "", "userDefinedFunctionRedactionConfig", "", "Lkotlin/Function1;", "", "Lorg/partiql/lang/domains/PartiqlAst$Expr;", "Lorg/partiql/lang/ast/passes/UserDefinedFunctionRedactionLambda;", "(Ljava/lang/String;Ljava/util/Set;Ljava/util/Map;)V", "sourceLocationMetaForRedaction", "Ljava/util/ArrayList;", "Lorg/partiql/lang/ast/SourceLocationMeta;", "Lkotlin/collections/ArrayList;", "arithmeticOpRedaction", "", "args", "getRedactedStatement", "plusMinusRedaction", "redactCall", "node", "Lorg/partiql/lang/domains/PartiqlAst$Expr$Call;", "redactComparisonOp", "redactExpr", "redactLiteral", "literal", "Lorg/partiql/lang/domains/PartiqlAst$Expr$Lit;", "redactLogicalOp", "redactNAry", "redactSeq", "seq", "Lorg/partiql/lang/domains/PartiqlAst$Expr$Bag;", "Lorg/partiql/lang/domains/PartiqlAst$Expr$List;", "Lorg/partiql/lang/domains/PartiqlAst$Expr$Sexp;", "redactStruct", "struct", "Lorg/partiql/lang/domains/PartiqlAst$Expr$Struct;", "redactStructInInsertValueOp", "redactTypes", "typed", "Lorg/partiql/lang/domains/PartiqlAst$Expr$IsType;", "visitAssignment", "Lorg/partiql/lang/domains/PartiqlAst$Assignment;", "visitDmlOpInsertValue", "Lorg/partiql/lang/domains/PartiqlAst$DmlOp$InsertValue;", "visitExprSelect", "Lorg/partiql/lang/domains/PartiqlAst$Expr$Select;", "visitStatementDml", "Lorg/partiql/lang/domains/PartiqlAst$Statement$Dml;", "isNAry", "", "lang"})
final class StatementRedactionVisitor
extends PartiqlAst.Visitor {
    private final ArrayList<SourceLocationMeta> sourceLocationMetaForRedaction;
    private final String statement;
    private final Set<String> safeFieldNames;
    private final Map<String, Function1<List<? extends PartiqlAst.Expr>, List<PartiqlAst.Expr>>> userDefinedFunctionRedactionConfig;

    /*
     * WARNING - void declaration
     */
    @NotNull
    public final String getRedactedStatement() {
        void $this$mapTo$iv$iv;
        List<String> lines = StringsKt.lines(this.statement);
        int[] totalCharactersInPreviousLines = new int[lines.size()];
        int n = 1;
        int n2 = lines.size();
        while (n < n2) {
            void lineNum;
            totalCharactersInPreviousLines[lineNum] = totalCharactersInPreviousLines[lineNum - true] + lines.get((int)(lineNum - true)).length() + 1;
            ++lineNum;
        }
        StringBuilder redactedStatement = new StringBuilder(this.statement);
        int offset = 0;
        Collection<Object> collection = this.sourceLocationMetaForRedaction;
        boolean bl = false;
        Object object = new Comparator<T>(){

            public final int compare(T a, T b) {
                boolean bl = false;
                SourceLocationMeta it = (SourceLocationMeta)a;
                boolean bl2 = false;
                Comparable comparable = Long.valueOf(it.getLineNum());
                it = (SourceLocationMeta)b;
                Comparable comparable2 = comparable;
                bl2 = false;
                Long l = it.getLineNum();
                return ComparisonsKt.compareValues(comparable2, (Comparable)l);
            }
        };
        Comparator comparator = object;
        boolean bl2 = false;
        object = new Comparator<T>(comparator){
            final /* synthetic */ Comparator $this_thenBy;
            {
                this.$this_thenBy = comparator;
            }

            public final int compare(T a, T b) {
                int n;
                int previousCompare = this.$this_thenBy.compare(a, b);
                if (previousCompare != 0) {
                    n = previousCompare;
                } else {
                    boolean bl = false;
                    SourceLocationMeta it = (SourceLocationMeta)a;
                    boolean bl2 = false;
                    Comparable comparable = Long.valueOf(it.getCharOffset());
                    it = (SourceLocationMeta)b;
                    Comparable comparable2 = comparable;
                    bl2 = false;
                    Long l = it.getCharOffset();
                    n = ComparisonsKt.compareValues(comparable2, (Comparable)l);
                }
                return n;
            }
        };
        CollectionsKt.sortWith(collection, object);
        Iterable $this$map$iv = this.sourceLocationMetaForRedaction;
        boolean $i$f$map = false;
        Iterable iterable = $this$map$iv;
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
        boolean $i$f$mapTo = false;
        for (Object item$iv$iv : $this$mapTo$iv$iv) {
            void it;
            SourceLocationMeta sourceLocationMeta = (SourceLocationMeta)item$iv$iv;
            collection = destination$iv$iv;
            boolean bl3 = false;
            int length = (int)it.getLength();
            int lineNum = (int)it.getLineNum();
            if (lineNum < 1 || lineNum > totalCharactersInPreviousLines.length) {
                throw (Throwable)new IllegalArgumentException("Unable to redact the statement. Please check that the input ast is the parsed result of the input statement, line number: " + lineNum);
            }
            int start = totalCharactersInPreviousLines[lineNum - 1] + (int)it.getCharOffset() - 1 + offset;
            if (start < 0 || length < 0 || start >= redactedStatement.length() || start > redactedStatement.length() - length) {
                throw (Throwable)new IllegalArgumentException("Unable to redact the statement. Please check that the input ast is the parsed result of the input statement");
            }
            redactedStatement.replace(start, start + length, "***(Redacted)");
            offset = offset + "***(Redacted)".length() - length;
            object = Unit.INSTANCE;
            collection.add(object);
        }
        List cfr_ignored_0 = (List)destination$iv$iv;
        String string = redactedStatement.toString();
        Intrinsics.checkExpressionValueIsNotNull(string, "redactedStatement.toString()");
        return string;
    }

    @Override
    protected void visitExprSelect(@NotNull PartiqlAst.Expr.Select node) {
        block0: {
            Intrinsics.checkParameterIsNotNull(node, "node");
            PartiqlAst.Expr expr = node.getWhere();
            if (expr == null) break block0;
            PartiqlAst.Expr expr2 = expr;
            boolean bl = false;
            boolean bl2 = false;
            PartiqlAst.Expr it = expr2;
            boolean bl3 = false;
            this.redactExpr(it);
        }
    }

    @Override
    protected void visitStatementDml(@NotNull PartiqlAst.Statement.Dml node) {
        block0: {
            Intrinsics.checkParameterIsNotNull(node, "node");
            PartiqlAst.Expr expr = node.getWhere();
            if (expr == null) break block0;
            PartiqlAst.Expr expr2 = expr;
            boolean bl = false;
            boolean bl2 = false;
            PartiqlAst.Expr it = expr2;
            boolean bl3 = false;
            this.redactExpr(it);
        }
    }

    @Override
    public void visitAssignment(@NotNull PartiqlAst.Assignment node) {
        Intrinsics.checkParameterIsNotNull(node, "node");
        if (!StatementRedactorKt.skipRedaction(node.getTarget(), this.safeFieldNames)) {
            this.redactExpr(node.getValue());
        }
    }

    @Override
    protected void visitDmlOpInsertValue(@NotNull PartiqlAst.DmlOp.InsertValue node) {
        Intrinsics.checkParameterIsNotNull(node, "node");
        PartiqlAst.Expr expr = node.getValue();
        if (expr instanceof PartiqlAst.Expr.Struct) {
            this.redactStructInInsertValueOp((PartiqlAst.Expr.Struct)node.getValue());
        } else {
            this.redactExpr(node.getValue());
        }
    }

    private final void redactExpr(PartiqlAst.Expr node) {
        if (this.isNAry(node)) {
            this.redactNAry(node);
        } else {
            PartiqlAst.Expr expr = node;
            if (expr instanceof PartiqlAst.Expr.Lit) {
                this.redactLiteral((PartiqlAst.Expr.Lit)node);
            } else if (expr instanceof PartiqlAst.Expr.List) {
                this.redactSeq((PartiqlAst.Expr.List)node);
            } else if (expr instanceof PartiqlAst.Expr.Sexp) {
                this.redactSeq((PartiqlAst.Expr.Sexp)node);
            } else if (expr instanceof PartiqlAst.Expr.Bag) {
                this.redactSeq((PartiqlAst.Expr.Bag)node);
            } else if (expr instanceof PartiqlAst.Expr.Struct) {
                this.redactStruct((PartiqlAst.Expr.Struct)node);
            } else if (expr instanceof PartiqlAst.Expr.IsType) {
                this.redactTypes((PartiqlAst.Expr.IsType)node);
            }
        }
    }

    private final void redactLogicalOp(List<? extends PartiqlAst.Expr> args2) {
        Iterable $this$forEach$iv = args2;
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            PartiqlAst.Expr it = (PartiqlAst.Expr)element$iv;
            boolean bl = false;
            this.redactExpr(it);
        }
    }

    private final void redactComparisonOp(List<? extends PartiqlAst.Expr> args2) {
        if (args2.size() != 2) {
            throw (Throwable)new IllegalArgumentException("Invalid number of args in node");
        }
        if (!StatementRedactorKt.skipRedaction(args2.get(0), this.safeFieldNames)) {
            this.redactExpr(args2.get(1));
        }
    }

    private final void plusMinusRedaction(List<? extends PartiqlAst.Expr> args2) {
        switch (args2.size()) {
            case 1: {
                this.redactExpr(args2.get(0));
                break;
            }
            case 2: {
                this.redactExpr(args2.get(0));
                this.redactExpr(args2.get(1));
                break;
            }
            default: {
                throw (Throwable)new IllegalArgumentException("Invalid number of args in node");
            }
        }
    }

    private final void arithmeticOpRedaction(List<? extends PartiqlAst.Expr> args2) {
        if (args2.size() != 2) {
            throw (Throwable)new IllegalArgumentException("Invalid number of args in node");
        }
        this.redactExpr(args2.get(0));
        this.redactExpr(args2.get(1));
    }

    private final void redactNAry(PartiqlAst.Expr node) {
        PartiqlAst.Expr expr = node;
        if (expr instanceof PartiqlAst.Expr.And) {
            this.redactLogicalOp(((PartiqlAst.Expr.And)node).getOperands());
        } else if (expr instanceof PartiqlAst.Expr.Or) {
            this.redactLogicalOp(((PartiqlAst.Expr.Or)node).getOperands());
        } else if (expr instanceof PartiqlAst.Expr.Not) {
            this.redactExpr(((PartiqlAst.Expr.Not)node).getExpr());
        } else if (expr instanceof PartiqlAst.Expr.Eq) {
            this.redactComparisonOp(((PartiqlAst.Expr.Eq)node).getOperands());
        } else if (expr instanceof PartiqlAst.Expr.Ne) {
            this.redactComparisonOp(((PartiqlAst.Expr.Ne)node).getOperands());
        } else if (expr instanceof PartiqlAst.Expr.Gt) {
            this.redactComparisonOp(((PartiqlAst.Expr.Gt)node).getOperands());
        } else if (expr instanceof PartiqlAst.Expr.Gte) {
            this.redactComparisonOp(((PartiqlAst.Expr.Gte)node).getOperands());
        } else if (expr instanceof PartiqlAst.Expr.Lt) {
            this.redactComparisonOp(((PartiqlAst.Expr.Lt)node).getOperands());
        } else if (expr instanceof PartiqlAst.Expr.Lte) {
            this.redactComparisonOp(((PartiqlAst.Expr.Lte)node).getOperands());
        } else if (expr instanceof PartiqlAst.Expr.InCollection) {
            this.redactComparisonOp(((PartiqlAst.Expr.InCollection)node).getOperands());
        } else if (expr instanceof PartiqlAst.Expr.Plus) {
            this.plusMinusRedaction(((PartiqlAst.Expr.Plus)node).getOperands());
        } else if (expr instanceof PartiqlAst.Expr.Minus) {
            this.plusMinusRedaction(((PartiqlAst.Expr.Minus)node).getOperands());
        } else if (expr instanceof PartiqlAst.Expr.Times) {
            this.arithmeticOpRedaction(((PartiqlAst.Expr.Times)node).getOperands());
        } else if (expr instanceof PartiqlAst.Expr.Divide) {
            this.arithmeticOpRedaction(((PartiqlAst.Expr.Divide)node).getOperands());
        } else if (expr instanceof PartiqlAst.Expr.Modulo) {
            this.arithmeticOpRedaction(((PartiqlAst.Expr.Modulo)node).getOperands());
        } else if (expr instanceof PartiqlAst.Expr.Concat) {
            this.arithmeticOpRedaction(((PartiqlAst.Expr.Concat)node).getOperands());
        } else if (expr instanceof PartiqlAst.Expr.Between) {
            if (!StatementRedactorKt.skipRedaction(((PartiqlAst.Expr.Between)node).getValue(), this.safeFieldNames)) {
                this.redactExpr(((PartiqlAst.Expr.Between)node).getFrom());
                this.redactExpr(((PartiqlAst.Expr.Between)node).getTo());
            }
        } else if (expr instanceof PartiqlAst.Expr.Call) {
            this.redactCall((PartiqlAst.Expr.Call)node);
        }
    }

    private final void redactLiteral(PartiqlAst.Expr.Lit literal) {
        SourceLocationMeta sourceLocationMeta = SourceLocationMetaKt.getSourceLocation(literal.getMetas());
        if (sourceLocationMeta == null) {
            String string = "Cannot redact due to missing source location";
            boolean bl = false;
            throw (Throwable)new IllegalStateException(string.toString());
        }
        SourceLocationMeta sourceLocation = sourceLocationMeta;
        this.sourceLocationMetaForRedaction.add(sourceLocation);
    }

    /*
     * WARNING - void declaration
     */
    private final List<Unit> redactSeq(PartiqlAst.Expr.List seq2) {
        void $this$mapTo$iv$iv;
        Iterable $this$map$iv = seq2.getValues();
        boolean $i$f$map = false;
        Iterable iterable = $this$map$iv;
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
        boolean $i$f$mapTo = false;
        for (Object item$iv$iv : $this$mapTo$iv$iv) {
            void it;
            PartiqlAst.Expr expr = (PartiqlAst.Expr)item$iv$iv;
            Collection collection = destination$iv$iv;
            boolean bl = false;
            this.redactExpr((PartiqlAst.Expr)it);
            Unit unit = Unit.INSTANCE;
            collection.add(unit);
        }
        return (List)destination$iv$iv;
    }

    /*
     * WARNING - void declaration
     */
    private final List<Unit> redactSeq(PartiqlAst.Expr.Bag seq2) {
        void $this$mapTo$iv$iv;
        Iterable $this$map$iv = seq2.getValues();
        boolean $i$f$map = false;
        Iterable iterable = $this$map$iv;
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
        boolean $i$f$mapTo = false;
        for (Object item$iv$iv : $this$mapTo$iv$iv) {
            void it;
            PartiqlAst.Expr expr = (PartiqlAst.Expr)item$iv$iv;
            Collection collection = destination$iv$iv;
            boolean bl = false;
            this.redactExpr((PartiqlAst.Expr)it);
            Unit unit = Unit.INSTANCE;
            collection.add(unit);
        }
        return (List)destination$iv$iv;
    }

    /*
     * WARNING - void declaration
     */
    private final List<Unit> redactSeq(PartiqlAst.Expr.Sexp seq2) {
        void $this$mapTo$iv$iv;
        Iterable $this$map$iv = seq2.getValues();
        boolean $i$f$map = false;
        Iterable iterable = $this$map$iv;
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
        boolean $i$f$mapTo = false;
        for (Object item$iv$iv : $this$mapTo$iv$iv) {
            void it;
            PartiqlAst.Expr expr = (PartiqlAst.Expr)item$iv$iv;
            Collection collection = destination$iv$iv;
            boolean bl = false;
            this.redactExpr((PartiqlAst.Expr)it);
            Unit unit = Unit.INSTANCE;
            collection.add(unit);
        }
        return (List)destination$iv$iv;
    }

    /*
     * WARNING - void declaration
     */
    private final void redactStruct(PartiqlAst.Expr.Struct struct) {
        void $this$mapTo$iv$iv;
        Iterable $this$map$iv = struct.getFields();
        boolean $i$f$map = false;
        Iterable iterable = $this$map$iv;
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
        boolean $i$f$mapTo = false;
        for (Object item$iv$iv : $this$mapTo$iv$iv) {
            void it;
            PartiqlAst.ExprPair exprPair = (PartiqlAst.ExprPair)item$iv$iv;
            Collection collection = destination$iv$iv;
            boolean bl = false;
            if (it.getFirst() instanceof PartiqlAst.Expr.Lit) {
                this.redactLiteral((PartiqlAst.Expr.Lit)it.getFirst());
            }
            this.redactExpr(it.getSecond());
            Unit unit = Unit.INSTANCE;
            collection.add(unit);
        }
        List cfr_ignored_0 = (List)destination$iv$iv;
    }

    /*
     * WARNING - void declaration
     */
    private final void redactCall(PartiqlAst.Expr.Call node) {
        String funcName = node.getFuncName().getText();
        Function1<List<? extends PartiqlAst.Expr>, List<PartiqlAst.Expr>> redactionLambda = this.userDefinedFunctionRedactionConfig.get(funcName);
        if (redactionLambda == null) {
            void $this$mapTo$iv$iv;
            Iterable $this$map$iv = node.getArgs();
            boolean $i$f$map = false;
            Iterable iterable = $this$map$iv;
            Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
            boolean $i$f$mapTo = false;
            for (Object item$iv$iv : $this$mapTo$iv$iv) {
                void it;
                PartiqlAst.Expr expr = (PartiqlAst.Expr)item$iv$iv;
                Collection collection = destination$iv$iv;
                boolean bl = false;
                this.redactExpr((PartiqlAst.Expr)it);
                Unit unit = Unit.INSTANCE;
                collection.add(unit);
            }
            List cfr_ignored_0 = (List)destination$iv$iv;
        } else {
            Iterable $this$map$iv = redactionLambda.invoke(node.getArgs());
            boolean $i$f$map = false;
            Iterable $this$mapTo$iv$iv = $this$map$iv;
            Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
            boolean $i$f$mapTo = false;
            for (Object item$iv$iv : $this$mapTo$iv$iv) {
                PartiqlAst.Expr it = (PartiqlAst.Expr)item$iv$iv;
                Collection collection = destination$iv$iv;
                boolean bl = false;
                this.redactExpr(it);
                Unit unit = Unit.INSTANCE;
                collection.add(unit);
            }
            List cfr_ignored_1 = (List)destination$iv$iv;
        }
    }

    private final void redactTypes(PartiqlAst.Expr.IsType typed) {
        if (typed.getValue() instanceof PartiqlAst.Expr.Id && !StatementRedactorKt.skipRedaction(typed.getValue(), this.safeFieldNames)) {
            SourceLocationMeta sourceLocationMeta = SourceLocationMetaKt.getSourceLocation(typed.getType().getMetas());
            if (sourceLocationMeta == null) {
                String string = "Cannot redact due to missing source location";
                boolean bl = false;
                throw (Throwable)new IllegalStateException(string.toString());
            }
            SourceLocationMeta sourceLocation = sourceLocationMeta;
            this.sourceLocationMetaForRedaction.add(sourceLocation);
        }
    }

    /*
     * WARNING - void declaration
     */
    private final void redactStructInInsertValueOp(PartiqlAst.Expr.Struct struct) {
        void $this$mapTo$iv$iv;
        Iterable $this$map$iv = struct.getFields();
        boolean $i$f$map = false;
        Iterable iterable = $this$map$iv;
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
        boolean $i$f$mapTo = false;
        for (Object item$iv$iv : $this$mapTo$iv$iv) {
            void it;
            PartiqlAst.ExprPair exprPair = (PartiqlAst.ExprPair)item$iv$iv;
            Collection collection = destination$iv$iv;
            boolean bl = false;
            PartiqlAst.Expr expr = it.getFirst();
            if (expr instanceof PartiqlAst.Expr.Lit && !StatementRedactorKt.skipRedaction(it.getFirst(), this.safeFieldNames)) {
                this.redactExpr(it.getSecond());
            }
            Unit unit = Unit.INSTANCE;
            collection.add(unit);
        }
        List cfr_ignored_0 = (List)destination$iv$iv;
    }

    private final boolean isNAry(@NotNull PartiqlAst.Expr $this$isNAry) {
        return $this$isNAry instanceof PartiqlAst.Expr.And || $this$isNAry instanceof PartiqlAst.Expr.Or || $this$isNAry instanceof PartiqlAst.Expr.Not || $this$isNAry instanceof PartiqlAst.Expr.Eq || $this$isNAry instanceof PartiqlAst.Expr.Ne || $this$isNAry instanceof PartiqlAst.Expr.Gt || $this$isNAry instanceof PartiqlAst.Expr.Gte || $this$isNAry instanceof PartiqlAst.Expr.Lt || $this$isNAry instanceof PartiqlAst.Expr.Lte || $this$isNAry instanceof PartiqlAst.Expr.InCollection || $this$isNAry instanceof PartiqlAst.Expr.Plus || $this$isNAry instanceof PartiqlAst.Expr.Minus || $this$isNAry instanceof PartiqlAst.Expr.Times || $this$isNAry instanceof PartiqlAst.Expr.Divide || $this$isNAry instanceof PartiqlAst.Expr.Modulo || $this$isNAry instanceof PartiqlAst.Expr.Concat || $this$isNAry instanceof PartiqlAst.Expr.Between || $this$isNAry instanceof PartiqlAst.Expr.Call;
    }

    public StatementRedactionVisitor(@NotNull String statement, @NotNull Set<String> safeFieldNames, @NotNull Map<String, ? extends Function1<? super List<? extends PartiqlAst.Expr>, ? extends List<? extends PartiqlAst.Expr>>> userDefinedFunctionRedactionConfig) {
        Intrinsics.checkParameterIsNotNull(statement, "statement");
        Intrinsics.checkParameterIsNotNull(safeFieldNames, "safeFieldNames");
        Intrinsics.checkParameterIsNotNull(userDefinedFunctionRedactionConfig, "userDefinedFunctionRedactionConfig");
        this.statement = statement;
        this.safeFieldNames = safeFieldNames;
        this.userDefinedFunctionRedactionConfig = userDefinedFunctionRedactionConfig;
        StatementRedactionVisitor statementRedactionVisitor = this;
        boolean bl = false;
        ArrayList arrayList = new ArrayList();
        statementRedactionVisitor.sourceLocationMetaForRedaction = arrayList;
    }
}

