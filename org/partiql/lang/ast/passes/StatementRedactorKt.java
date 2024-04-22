/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  com.amazon.ionelement.api.AnyElement
 *  com.amazon.ionelement.api.StringElement
 *  org.jetbrains.annotations.NotNull
 */
package org.partiql.lang.ast.passes;

import com.amazon.ion.IonSystem;
import com.amazon.ion.system.IonSystemBuilder;
import com.amazon.ionelement.api.AnyElement;
import com.amazon.ionelement.api.StringElement;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.MapsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.partiql.lang.ast.ExprNode;
import org.partiql.lang.ast.ExprNodeToStatementKt;
import org.partiql.lang.ast.passes.StatementRedactionVisitor;
import org.partiql.lang.domains.PartiqlAst;
import org.partiql.lang.syntax.SqlParser;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=2, d1={"\u0000D\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\"\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\u001aP\u0010\t\u001a\u00020\u00012\u0006\u0010\n\u001a\u00020\u00012\u000e\b\u0002\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00010\f20\b\u0002\u0010\r\u001a*\u0012\u0004\u0012\u00020\u0001\u0012 \u0012\u001e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00110\u0010\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00110\u00100\u000fj\u0002`\u00120\u000e\u001aX\u0010\t\u001a\u00020\u00012\u0006\u0010\n\u001a\u00020\u00012\u0006\u0010\u0013\u001a\u00020\u00142\u000e\b\u0002\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00010\f20\b\u0002\u0010\r\u001a*\u0012\u0004\u0012\u00020\u0001\u0012 \u0012\u001e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00110\u0010\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00110\u00100\u000fj\u0002`\u00120\u000e\u001a\u001c\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u00112\f\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00010\f\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0086T\u00a2\u0006\u0002\n\u0000\"\u000e\u0010\u0002\u001a\u00020\u0001X\u0086T\u00a2\u0006\u0002\n\u0000\"\u0016\u0010\u0003\u001a\n \u0005*\u0004\u0018\u00010\u00040\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000\"\u000e\u0010\u0006\u001a\u00020\u0001X\u0082T\u00a2\u0006\u0002\n\u0000\"\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000*:\u0010\u0019\"\u001a\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00110\u0010\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00110\u00100\u000f2\u001a\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00110\u0010\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00110\u00100\u000f\u00a8\u0006\u001a"}, d2={"INPUT_AST_STATEMENT_MISMATCH", "", "INVALID_NUM_ARGS", "ion", "Lcom/amazon/ion/IonSystem;", "kotlin.jvm.PlatformType", "maskPattern", "parser", "Lorg/partiql/lang/syntax/SqlParser;", "redact", "statement", "providedSafeFieldNames", "", "userDefinedFunctionRedactionConfig", "", "Lkotlin/Function1;", "", "Lorg/partiql/lang/domains/PartiqlAst$Expr;", "Lorg/partiql/lang/ast/passes/UserDefinedFunctionRedactionLambda;", "ast", "Lorg/partiql/lang/ast/ExprNode;", "skipRedaction", "", "node", "safeFieldNames", "UserDefinedFunctionRedactionLambda", "lang"})
public final class StatementRedactorKt {
    private static final IonSystem ion;
    private static final SqlParser parser;
    private static final String maskPattern = "***(Redacted)";
    @NotNull
    public static final String INVALID_NUM_ARGS = "Invalid number of args in node";
    @NotNull
    public static final String INPUT_AST_STATEMENT_MISMATCH = "Unable to redact the statement. Please check that the input ast is the parsed result of the input statement";

    public static final boolean skipRedaction(@NotNull PartiqlAst.Expr node, @NotNull Set<String> safeFieldNames) {
        boolean bl;
        block10: {
            Intrinsics.checkParameterIsNotNull(node, "node");
            Intrinsics.checkParameterIsNotNull(safeFieldNames, "safeFieldNames");
            if (safeFieldNames.isEmpty()) {
                return false;
            }
            PartiqlAst.Expr expr = node;
            if (expr instanceof PartiqlAst.Expr.Id) {
                bl = safeFieldNames.contains(((PartiqlAst.Expr.Id)node).getName().getText());
            } else if (expr instanceof PartiqlAst.Expr.Lit) {
                AnyElement anyElement = ((PartiqlAst.Expr.Lit)node).getValue();
                bl = anyElement instanceof StringElement ? safeFieldNames.contains(((PartiqlAst.Expr.Lit)node).getValue().getStringValue()) : false;
            } else if (expr instanceof PartiqlAst.Expr.Path) {
                Iterable $this$any$iv = ((PartiqlAst.Expr.Path)node).getSteps();
                boolean $i$f$any = false;
                if ($this$any$iv instanceof Collection && ((Collection)$this$any$iv).isEmpty()) {
                    bl = false;
                } else {
                    for (Object element$iv : $this$any$iv) {
                        PartiqlAst.PathStep it = (PartiqlAst.PathStep)element$iv;
                        boolean bl2 = false;
                        PartiqlAst.PathStep pathStep = it;
                        if (!(pathStep instanceof PartiqlAst.PathStep.PathExpr ? StatementRedactorKt.skipRedaction(((PartiqlAst.PathStep.PathExpr)it).getIndex(), safeFieldNames) : false)) continue;
                        bl = true;
                        break block10;
                    }
                    bl = false;
                }
            } else {
                bl = true;
            }
        }
        return bl;
    }

    @NotNull
    public static final String redact(@NotNull String statement, @NotNull Set<String> providedSafeFieldNames, @NotNull Map<String, ? extends Function1<? super List<? extends PartiqlAst.Expr>, ? extends List<? extends PartiqlAst.Expr>>> userDefinedFunctionRedactionConfig) {
        Intrinsics.checkParameterIsNotNull(statement, "statement");
        Intrinsics.checkParameterIsNotNull(providedSafeFieldNames, "providedSafeFieldNames");
        Intrinsics.checkParameterIsNotNull(userDefinedFunctionRedactionConfig, "userDefinedFunctionRedactionConfig");
        return StatementRedactorKt.redact(statement, parser.parseExprNode(statement), providedSafeFieldNames, userDefinedFunctionRedactionConfig);
    }

    public static /* synthetic */ String redact$default(String string, Set set2, Map map2, int n, Object object) {
        if ((n & 2) != 0) {
            set2 = SetsKt.emptySet();
        }
        if ((n & 4) != 0) {
            map2 = MapsKt.emptyMap();
        }
        return StatementRedactorKt.redact(string, set2, map2);
    }

    @NotNull
    public static final String redact(@NotNull String statement, @NotNull ExprNode ast, @NotNull Set<String> providedSafeFieldNames, @NotNull Map<String, ? extends Function1<? super List<? extends PartiqlAst.Expr>, ? extends List<? extends PartiqlAst.Expr>>> userDefinedFunctionRedactionConfig) {
        Intrinsics.checkParameterIsNotNull(statement, "statement");
        Intrinsics.checkParameterIsNotNull(ast, "ast");
        Intrinsics.checkParameterIsNotNull(providedSafeFieldNames, "providedSafeFieldNames");
        Intrinsics.checkParameterIsNotNull(userDefinedFunctionRedactionConfig, "userDefinedFunctionRedactionConfig");
        PartiqlAst.Statement partiqlAst = ExprNodeToStatementKt.toAstStatement(ast);
        StatementRedactionVisitor statementRedactionVisitor = new StatementRedactionVisitor(statement, providedSafeFieldNames, userDefinedFunctionRedactionConfig);
        statementRedactionVisitor.walkStatement(partiqlAst);
        return statementRedactionVisitor.getRedactedStatement();
    }

    public static /* synthetic */ String redact$default(String string, ExprNode exprNode, Set set2, Map map2, int n, Object object) {
        if ((n & 4) != 0) {
            set2 = SetsKt.emptySet();
        }
        if ((n & 8) != 0) {
            map2 = MapsKt.emptyMap();
        }
        return StatementRedactorKt.redact(string, exprNode, set2, map2);
    }

    static {
        IonSystem ionSystem = ion = IonSystemBuilder.standard().build();
        Intrinsics.checkExpressionValueIsNotNull(ionSystem, "ion");
        parser = new SqlParser(ionSystem);
    }
}

