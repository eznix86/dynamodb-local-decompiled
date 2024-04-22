/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package org.partiql.lang;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.partiql.lang.CompilerPipeline;
import org.partiql.lang.StepContext;
import org.partiql.lang.ast.ExprNode;
import org.partiql.lang.eval.CompileOptions;
import org.partiql.lang.eval.EvaluatingCompiler;
import org.partiql.lang.eval.ExprFunction;
import org.partiql.lang.eval.ExprValueFactory;
import org.partiql.lang.eval.Expression;
import org.partiql.lang.eval.builtins.storedprocedure.StoredProcedure;
import org.partiql.lang.syntax.Parser;
import org.partiql.lang.util.ThreadInterruptUtilsKt;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000X\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0000\u0018\u00002\u00020\u0001Bi\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0012\u0010\b\u001a\u000e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u000b0\t\u0012\u0012\u0010\f\u001a\u000e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\r0\t\u0012\"\u0010\u000e\u001a\u001e\u0012\u001a\u0012\u0018\u0012\u0004\u0012\u00020\u0011\u0012\u0004\u0012\u00020\u0012\u0012\u0004\u0012\u00020\u00110\u0010j\u0002`\u00130\u000f\u00a2\u0006\u0002\u0010\u0014J\u0010\u0010\u001e\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020\nH\u0016J\u0010\u0010\u001e\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020\u0011H\u0016J\u001d\u0010!\u001a\u00020\u00112\u0006\u0010 \u001a\u00020\u00112\u0006\u0010\"\u001a\u00020\u0012H\u0000\u00a2\u0006\u0002\b#R\u0014\u0010\u0006\u001a\u00020\u0007X\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u000e\u0010\u0017\u001a\u00020\u0018X\u0082\u0004\u00a2\u0006\u0002\n\u0000R \u0010\b\u001a\u000e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u000b0\tX\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u001aR\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R*\u0010\u000e\u001a\u001e\u0012\u001a\u0012\u0018\u0012\u0004\u0012\u00020\u0011\u0012\u0004\u0012\u00020\u0012\u0012\u0004\u0012\u00020\u00110\u0010j\u0002`\u00130\u000fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R \u0010\f\u001a\u000e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\r0\tX\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u001aR\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u001d\u00a8\u0006$"}, d2={"Lorg/partiql/lang/CompilerPipelineImpl;", "Lorg/partiql/lang/CompilerPipeline;", "valueFactory", "Lorg/partiql/lang/eval/ExprValueFactory;", "parser", "Lorg/partiql/lang/syntax/Parser;", "compileOptions", "Lorg/partiql/lang/eval/CompileOptions;", "functions", "", "", "Lorg/partiql/lang/eval/ExprFunction;", "procedures", "Lorg/partiql/lang/eval/builtins/storedprocedure/StoredProcedure;", "preProcessingSteps", "", "Lkotlin/Function2;", "Lorg/partiql/lang/ast/ExprNode;", "Lorg/partiql/lang/StepContext;", "Lorg/partiql/lang/ProcessingStep;", "(Lorg/partiql/lang/eval/ExprValueFactory;Lorg/partiql/lang/syntax/Parser;Lorg/partiql/lang/eval/CompileOptions;Ljava/util/Map;Ljava/util/Map;Ljava/util/List;)V", "getCompileOptions", "()Lorg/partiql/lang/eval/CompileOptions;", "compiler", "Lorg/partiql/lang/eval/EvaluatingCompiler;", "getFunctions", "()Ljava/util/Map;", "getProcedures", "getValueFactory", "()Lorg/partiql/lang/eval/ExprValueFactory;", "compile", "Lorg/partiql/lang/eval/Expression;", "query", "executePreProcessingSteps", "context", "executePreProcessingSteps$lang", "lang"})
public final class CompilerPipelineImpl
implements CompilerPipeline {
    private final EvaluatingCompiler compiler;
    @NotNull
    private final ExprValueFactory valueFactory;
    private final Parser parser;
    @NotNull
    private final CompileOptions compileOptions;
    @NotNull
    private final Map<String, ExprFunction> functions;
    @NotNull
    private final Map<String, StoredProcedure> procedures;
    private final List<Function2<ExprNode, StepContext, ExprNode>> preProcessingSteps;

    @Override
    @NotNull
    public Expression compile(@NotNull String query) {
        Intrinsics.checkParameterIsNotNull(query, "query");
        return this.compile(this.parser.parseExprNode(query));
    }

    @Override
    @NotNull
    public Expression compile(@NotNull ExprNode query) {
        Intrinsics.checkParameterIsNotNull(query, "query");
        StepContext context = new StepContext(this.getValueFactory(), this.getCompileOptions(), this.getFunctions(), this.getProcedures());
        ExprNode preProcessedQuery = this.executePreProcessingSteps$lang(query, context);
        return this.compiler.compile(preProcessedQuery);
    }

    /*
     * WARNING - void declaration
     */
    @NotNull
    public final ExprNode executePreProcessingSteps$lang(@NotNull ExprNode query, @NotNull StepContext context) {
        Intrinsics.checkParameterIsNotNull(query, "query");
        Intrinsics.checkParameterIsNotNull(context, "context");
        List<Function2<ExprNode, StepContext, ExprNode>> $this$interruptibleFold$iv = this.preProcessingSteps;
        boolean $i$f$interruptibleFold = false;
        Iterable $this$fold$iv$iv = $this$interruptibleFold$iv;
        boolean $i$f$fold = false;
        ExprNode accumulator$iv$iv = query;
        Iterator iterator2 = $this$fold$iv$iv.iterator();
        while (iterator2.hasNext()) {
            void step;
            void curr$iv;
            Object element$iv$iv;
            Object t = element$iv$iv = iterator2.next();
            ExprNode acc$iv = accumulator$iv$iv;
            boolean bl = false;
            ThreadInterruptUtilsKt.checkThreadInterrupted();
            Function2 function2 = (Function2)curr$iv;
            ExprNode currentExprNode = acc$iv;
            boolean bl2 = false;
            accumulator$iv$iv = (ExprNode)step.invoke(currentExprNode, context);
        }
        return accumulator$iv$iv;
    }

    @Override
    @NotNull
    public ExprValueFactory getValueFactory() {
        return this.valueFactory;
    }

    @Override
    @NotNull
    public CompileOptions getCompileOptions() {
        return this.compileOptions;
    }

    @Override
    @NotNull
    public Map<String, ExprFunction> getFunctions() {
        return this.functions;
    }

    @Override
    @NotNull
    public Map<String, StoredProcedure> getProcedures() {
        return this.procedures;
    }

    public CompilerPipelineImpl(@NotNull ExprValueFactory valueFactory, @NotNull Parser parser, @NotNull CompileOptions compileOptions, @NotNull Map<String, ? extends ExprFunction> functions, @NotNull Map<String, ? extends StoredProcedure> procedures, @NotNull List<? extends Function2<? super ExprNode, ? super StepContext, ? extends ExprNode>> preProcessingSteps) {
        Intrinsics.checkParameterIsNotNull(valueFactory, "valueFactory");
        Intrinsics.checkParameterIsNotNull(parser, "parser");
        Intrinsics.checkParameterIsNotNull(compileOptions, "compileOptions");
        Intrinsics.checkParameterIsNotNull(functions, "functions");
        Intrinsics.checkParameterIsNotNull(procedures, "procedures");
        Intrinsics.checkParameterIsNotNull(preProcessingSteps, "preProcessingSteps");
        this.valueFactory = valueFactory;
        this.parser = parser;
        this.compileOptions = compileOptions;
        this.functions = functions;
        this.procedures = procedures;
        this.preProcessingSteps = preProcessingSteps;
        this.compiler = new EvaluatingCompiler(this.getValueFactory(), this.getFunctions(), this.getProcedures(), this.getCompileOptions());
    }
}

