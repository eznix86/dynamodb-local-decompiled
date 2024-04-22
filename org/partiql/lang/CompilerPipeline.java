/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package org.partiql.lang;

import com.amazon.ion.IonSystem;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import org.jetbrains.annotations.NotNull;
import org.partiql.lang.CompilerPipelineImpl;
import org.partiql.lang.StepContext;
import org.partiql.lang.ast.ExprNode;
import org.partiql.lang.eval.CompileOptions;
import org.partiql.lang.eval.ExprFunction;
import org.partiql.lang.eval.ExprValueFactory;
import org.partiql.lang.eval.Expression;
import org.partiql.lang.eval.builtins.BuiltinFunctionsKt;
import org.partiql.lang.eval.builtins.storedprocedure.StoredProcedure;
import org.partiql.lang.syntax.Parser;
import org.partiql.lang.syntax.SqlParser;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000D\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010$\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\bf\u0018\u0000 \u00192\u00020\u0001:\u0002\u0018\u0019J\u0010\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\bH&J\u0010\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0017H&R\u0012\u0010\u0002\u001a\u00020\u0003X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005R#\u0010\u0006\u001a\u0013\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\t0\u0007\u00a2\u0006\u0002\b\nX\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u000b\u0010\fR#\u0010\r\u001a\u0013\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\u000e0\u0007\u00a2\u0006\u0002\b\nX\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u000f\u0010\fR\u0012\u0010\u0010\u001a\u00020\u0011X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0012\u0010\u0013\u00a8\u0006\u001a"}, d2={"Lorg/partiql/lang/CompilerPipeline;", "", "compileOptions", "Lorg/partiql/lang/eval/CompileOptions;", "getCompileOptions", "()Lorg/partiql/lang/eval/CompileOptions;", "functions", "", "", "Lorg/partiql/lang/eval/ExprFunction;", "Lkotlin/jvm/JvmSuppressWildcards;", "getFunctions", "()Ljava/util/Map;", "procedures", "Lorg/partiql/lang/eval/builtins/storedprocedure/StoredProcedure;", "getProcedures", "valueFactory", "Lorg/partiql/lang/eval/ExprValueFactory;", "getValueFactory", "()Lorg/partiql/lang/eval/ExprValueFactory;", "compile", "Lorg/partiql/lang/eval/Expression;", "query", "Lorg/partiql/lang/ast/ExprNode;", "Builder", "Companion", "lang"})
public interface CompilerPipeline {
    public static final Companion Companion = org.partiql.lang.CompilerPipeline$Companion.$$INSTANCE;

    @NotNull
    public ExprValueFactory getValueFactory();

    @NotNull
    public CompileOptions getCompileOptions();

    @NotNull
    public Map<String, ExprFunction> getFunctions();

    @NotNull
    public Map<String, StoredProcedure> getProcedures();

    @NotNull
    public Expression compile(@NotNull String var1);

    @NotNull
    public Expression compile(@NotNull ExprNode var1);

    @JvmStatic
    @NotNull
    public static Builder builder(@NotNull IonSystem ion) {
        return Companion.builder(ion);
    }

    @JvmStatic
    @NotNull
    public static Builder builder(@NotNull ExprValueFactory valueFactory) {
        return Companion.builder(valueFactory);
    }

    @JvmStatic
    @NotNull
    public static CompilerPipeline standard(@NotNull IonSystem ion) {
        return Companion.standard(ion);
    }

    @JvmStatic
    @NotNull
    public static CompilerPipeline standard(@NotNull ExprValueFactory valueFactory) {
        return Companion.standard(valueFactory);
    }

    @Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010%\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u000e\u0010\u0017\u001a\u00020\u00002\u0006\u0010\u0018\u001a\u00020\nJ$\u0010\u0019\u001a\u00020\u00002\u001c\u0010\u001a\u001a\u0018\u0012\u0004\u0012\u00020\u0012\u0012\u0004\u0012\u00020\u0013\u0012\u0004\u0012\u00020\u00120\u0011j\u0002`\u0014J\u000e\u0010\u001b\u001a\u00020\u00002\u0006\u0010\u001c\u001a\u00020\fJ\u0006\u0010\u001d\u001a\u00020\u001eJ\u000e\u0010\u0005\u001a\u00020\u00002\u0006\u0010\u001f\u001a\u00020\u0006J\u001f\u0010\u0005\u001a\u00020\u00002\u0017\u0010 \u001a\u0013\u0012\u0004\u0012\u00020\"\u0012\u0004\u0012\u00020#0!\u00a2\u0006\u0002\b$J\u000e\u0010%\u001a\u00020\u00002\u0006\u0010&\u001a\u00020\u000eR\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\n0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u000b\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\f0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010\r\u001a\u0004\u0018\u00010\u000eX\u0082\u000e\u00a2\u0006\u0002\n\u0000R*\u0010\u000f\u001a\u001e\u0012\u001a\u0012\u0018\u0012\u0004\u0012\u00020\u0012\u0012\u0004\u0012\u00020\u0013\u0012\u0004\u0012\u00020\u00120\u0011j\u0002`\u00140\u0010X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016\u00a8\u0006'"}, d2={"Lorg/partiql/lang/CompilerPipeline$Builder;", "", "valueFactory", "Lorg/partiql/lang/eval/ExprValueFactory;", "(Lorg/partiql/lang/eval/ExprValueFactory;)V", "compileOptions", "Lorg/partiql/lang/eval/CompileOptions;", "customFunctions", "", "", "Lorg/partiql/lang/eval/ExprFunction;", "customProcedures", "Lorg/partiql/lang/eval/builtins/storedprocedure/StoredProcedure;", "parser", "Lorg/partiql/lang/syntax/Parser;", "preProcessingSteps", "", "Lkotlin/Function2;", "Lorg/partiql/lang/ast/ExprNode;", "Lorg/partiql/lang/StepContext;", "Lorg/partiql/lang/ProcessingStep;", "getValueFactory", "()Lorg/partiql/lang/eval/ExprValueFactory;", "addFunction", "function", "addPreprocessingStep", "step", "addProcedure", "procedure", "build", "Lorg/partiql/lang/CompilerPipeline;", "options", "block", "Lkotlin/Function1;", "Lorg/partiql/lang/eval/CompileOptions$Builder;", "", "Lkotlin/ExtensionFunctionType;", "sqlParser", "p", "lang"})
    public static final class Builder {
        private Parser parser;
        private CompileOptions compileOptions;
        private final Map<String, ExprFunction> customFunctions;
        private final Map<String, StoredProcedure> customProcedures;
        private final List<Function2<ExprNode, StepContext, ExprNode>> preProcessingSteps;
        @NotNull
        private final ExprValueFactory valueFactory;

        @NotNull
        public final Builder sqlParser(@NotNull Parser p) {
            Intrinsics.checkParameterIsNotNull(p, "p");
            Builder builder = this;
            boolean bl = false;
            boolean bl2 = false;
            Builder $this$apply = builder;
            boolean bl3 = false;
            $this$apply.parser = p;
            return builder;
        }

        @NotNull
        public final Builder compileOptions(@NotNull CompileOptions options) {
            Intrinsics.checkParameterIsNotNull(options, "options");
            Builder builder = this;
            boolean bl = false;
            boolean bl2 = false;
            Builder $this$apply = builder;
            boolean bl3 = false;
            $this$apply.compileOptions = options;
            return builder;
        }

        @NotNull
        public final Builder compileOptions(@NotNull Function1<? super CompileOptions.Builder, Unit> block) {
            Intrinsics.checkParameterIsNotNull(block, "block");
            return this.compileOptions(CompileOptions.Companion.build(block));
        }

        @NotNull
        public final Builder addFunction(@NotNull ExprFunction function) {
            Intrinsics.checkParameterIsNotNull(function, "function");
            Builder builder = this;
            boolean bl = false;
            boolean bl2 = false;
            Builder $this$apply = builder;
            boolean bl3 = false;
            $this$apply.customFunctions.put(function.getName(), function);
            return builder;
        }

        @NotNull
        public final Builder addProcedure(@NotNull StoredProcedure procedure) {
            Intrinsics.checkParameterIsNotNull(procedure, "procedure");
            Builder builder = this;
            boolean bl = false;
            boolean bl2 = false;
            Builder $this$apply = builder;
            boolean bl3 = false;
            $this$apply.customProcedures.put(procedure.getSignature().getName(), procedure);
            return builder;
        }

        @NotNull
        public final Builder addPreprocessingStep(@NotNull Function2<? super ExprNode, ? super StepContext, ? extends ExprNode> step) {
            Intrinsics.checkParameterIsNotNull(step, "step");
            Builder builder = this;
            boolean bl = false;
            boolean bl2 = false;
            Builder $this$apply = builder;
            boolean bl3 = false;
            $this$apply.preProcessingSteps.add(step);
            return builder;
        }

        /*
         * WARNING - void declaration
         */
        @NotNull
        public final CompilerPipeline build() {
            CompileOptions compileOptions;
            void $this$associateByTo$iv$iv;
            Iterable $this$associateBy$iv = BuiltinFunctionsKt.createBuiltinFunctions(this.valueFactory);
            boolean $i$f$associateBy = false;
            int capacity$iv = RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault($this$associateBy$iv, 10)), 16);
            Iterable iterable = $this$associateBy$iv;
            Map destination$iv$iv = new LinkedHashMap(capacity$iv);
            boolean $i$f$associateByTo = false;
            for (Object element$iv$iv : $this$associateByTo$iv$iv) {
                void it;
                ExprFunction exprFunction = (ExprFunction)element$iv$iv;
                Map map2 = destination$iv$iv;
                boolean bl = false;
                String string = it.getName();
                map2.put(string, element$iv$iv);
            }
            Map builtinFunctions = destination$iv$iv;
            Map<String, ExprFunction> allFunctions = MapsKt.plus(builtinFunctions, this.customFunctions);
            Parser parser = this.parser;
            if (parser == null) {
                parser = new SqlParser(this.valueFactory.getIon());
            }
            if ((compileOptions = this.compileOptions) == null) {
                compileOptions = CompileOptions.Companion.standard();
            }
            return new CompilerPipelineImpl(this.valueFactory, parser, compileOptions, allFunctions, this.customProcedures, this.preProcessingSteps);
        }

        @NotNull
        public final ExprValueFactory getValueFactory() {
            return this.valueFactory;
        }

        public Builder(@NotNull ExprValueFactory valueFactory) {
            Intrinsics.checkParameterIsNotNull(valueFactory, "valueFactory");
            this.valueFactory = valueFactory;
            this.customFunctions = new HashMap();
            this.customProcedures = new HashMap();
            this.preProcessingSteps = new ArrayList();
        }
    }

    @Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J'\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0017\u0010\u0007\u001a\u0013\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\n0\b\u00a2\u0006\u0002\b\u000bJ'\u0010\u0003\u001a\u00020\u00042\u0006\u0010\f\u001a\u00020\r2\u0017\u0010\u0007\u001a\u0013\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\n0\b\u00a2\u0006\u0002\b\u000bJ\u0010\u0010\u000e\u001a\u00020\t2\u0006\u0010\u0005\u001a\u00020\u0006H\u0007J\u0010\u0010\u000e\u001a\u00020\t2\u0006\u0010\f\u001a\u00020\rH\u0007J\u0010\u0010\u000f\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0007J\u0010\u0010\u000f\u001a\u00020\u00042\u0006\u0010\f\u001a\u00020\rH\u0007\u00a8\u0006\u0010"}, d2={"Lorg/partiql/lang/CompilerPipeline$Companion;", "", "()V", "build", "Lorg/partiql/lang/CompilerPipeline;", "ion", "Lcom/amazon/ion/IonSystem;", "block", "Lkotlin/Function1;", "Lorg/partiql/lang/CompilerPipeline$Builder;", "", "Lkotlin/ExtensionFunctionType;", "valueFactory", "Lorg/partiql/lang/eval/ExprValueFactory;", "builder", "standard", "lang"})
    public static final class Companion {
        static final /* synthetic */ Companion $$INSTANCE;

        @NotNull
        public final CompilerPipeline build(@NotNull IonSystem ion, @NotNull Function1<? super Builder, Unit> block) {
            Intrinsics.checkParameterIsNotNull(ion, "ion");
            Intrinsics.checkParameterIsNotNull(block, "block");
            return this.build(ExprValueFactory.Companion.standard(ion), block);
        }

        @NotNull
        public final CompilerPipeline build(@NotNull ExprValueFactory valueFactory, @NotNull Function1<? super Builder, Unit> block) {
            Intrinsics.checkParameterIsNotNull(valueFactory, "valueFactory");
            Intrinsics.checkParameterIsNotNull(block, "block");
            Builder builder = new Builder(valueFactory);
            boolean bl = false;
            boolean bl2 = false;
            block.invoke(builder);
            return builder.build();
        }

        @JvmStatic
        @NotNull
        public final Builder builder(@NotNull IonSystem ion) {
            Intrinsics.checkParameterIsNotNull(ion, "ion");
            return this.builder(ExprValueFactory.Companion.standard(ion));
        }

        @JvmStatic
        @NotNull
        public final Builder builder(@NotNull ExprValueFactory valueFactory) {
            Intrinsics.checkParameterIsNotNull(valueFactory, "valueFactory");
            return new Builder(valueFactory);
        }

        @JvmStatic
        @NotNull
        public final CompilerPipeline standard(@NotNull IonSystem ion) {
            Intrinsics.checkParameterIsNotNull(ion, "ion");
            return this.standard(ExprValueFactory.Companion.standard(ion));
        }

        @JvmStatic
        @NotNull
        public final CompilerPipeline standard(@NotNull ExprValueFactory valueFactory) {
            Intrinsics.checkParameterIsNotNull(valueFactory, "valueFactory");
            return this.builder(valueFactory).build();
        }

        private Companion() {
        }

        static {
            Companion companion;
            $$INSTANCE = companion = new Companion();
        }
    }
}

