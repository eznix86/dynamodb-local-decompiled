/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package org.partiql.lang.eval;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.partiql.lang.eval.ProjectionIterationBehavior;
import org.partiql.lang.eval.ThunkOptions;
import org.partiql.lang.eval.UndefinedVariableBehavior;
import org.partiql.lang.eval.VisitorTransformMode;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000:\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000f\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\b\u0086\b\u0018\u0000  2\u00020\u0001:\u0002\u001f B-\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007\u0012\b\b\u0002\u0010\b\u001a\u00020\t\u00a2\u0006\u0002\u0010\nJ\t\u0010\u0013\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0014\u001a\u00020\u0005H\u00c6\u0003J\t\u0010\u0015\u001a\u00020\u0007H\u00c6\u0003J\t\u0010\u0016\u001a\u00020\tH\u00c6\u0003J1\u0010\u0017\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010\b\u001a\u00020\tH\u00c6\u0001J\u0013\u0010\u0018\u001a\u00020\u00192\b\u0010\u001a\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u001b\u001a\u00020\u001cH\u00d6\u0001J\t\u0010\u001d\u001a\u00020\u001eH\u00d6\u0001R\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\b\u001a\u00020\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0011\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012\u00a8\u0006!"}, d2={"Lorg/partiql/lang/eval/CompileOptions;", "", "undefinedVariable", "Lorg/partiql/lang/eval/UndefinedVariableBehavior;", "projectionIteration", "Lorg/partiql/lang/eval/ProjectionIterationBehavior;", "visitorTransformMode", "Lorg/partiql/lang/eval/VisitorTransformMode;", "thunkOptions", "Lorg/partiql/lang/eval/ThunkOptions;", "(Lorg/partiql/lang/eval/UndefinedVariableBehavior;Lorg/partiql/lang/eval/ProjectionIterationBehavior;Lorg/partiql/lang/eval/VisitorTransformMode;Lorg/partiql/lang/eval/ThunkOptions;)V", "getProjectionIteration", "()Lorg/partiql/lang/eval/ProjectionIterationBehavior;", "getThunkOptions", "()Lorg/partiql/lang/eval/ThunkOptions;", "getUndefinedVariable", "()Lorg/partiql/lang/eval/UndefinedVariableBehavior;", "getVisitorTransformMode", "()Lorg/partiql/lang/eval/VisitorTransformMode;", "component1", "component2", "component3", "component4", "copy", "equals", "", "other", "hashCode", "", "toString", "", "Builder", "Companion", "lang"})
public final class CompileOptions {
    @NotNull
    private final UndefinedVariableBehavior undefinedVariable;
    @NotNull
    private final ProjectionIterationBehavior projectionIteration;
    @NotNull
    private final VisitorTransformMode visitorTransformMode;
    @NotNull
    private final ThunkOptions thunkOptions;
    public static final Companion Companion = new Companion(null);

    @NotNull
    public final UndefinedVariableBehavior getUndefinedVariable() {
        return this.undefinedVariable;
    }

    @NotNull
    public final ProjectionIterationBehavior getProjectionIteration() {
        return this.projectionIteration;
    }

    @NotNull
    public final VisitorTransformMode getVisitorTransformMode() {
        return this.visitorTransformMode;
    }

    @NotNull
    public final ThunkOptions getThunkOptions() {
        return this.thunkOptions;
    }

    private CompileOptions(UndefinedVariableBehavior undefinedVariable, ProjectionIterationBehavior projectionIteration, VisitorTransformMode visitorTransformMode, ThunkOptions thunkOptions) {
        this.undefinedVariable = undefinedVariable;
        this.projectionIteration = projectionIteration;
        this.visitorTransformMode = visitorTransformMode;
        this.thunkOptions = thunkOptions;
    }

    /* synthetic */ CompileOptions(UndefinedVariableBehavior undefinedVariableBehavior, ProjectionIterationBehavior projectionIterationBehavior, VisitorTransformMode visitorTransformMode, ThunkOptions thunkOptions, int n, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n & 2) != 0) {
            projectionIterationBehavior = ProjectionIterationBehavior.FILTER_MISSING;
        }
        if ((n & 4) != 0) {
            visitorTransformMode = VisitorTransformMode.DEFAULT;
        }
        if ((n & 8) != 0) {
            thunkOptions = ThunkOptions.Companion.standard();
        }
        this(undefinedVariableBehavior, projectionIterationBehavior, visitorTransformMode, thunkOptions);
    }

    @NotNull
    public final UndefinedVariableBehavior component1() {
        return this.undefinedVariable;
    }

    @NotNull
    public final ProjectionIterationBehavior component2() {
        return this.projectionIteration;
    }

    @NotNull
    public final VisitorTransformMode component3() {
        return this.visitorTransformMode;
    }

    @NotNull
    public final ThunkOptions component4() {
        return this.thunkOptions;
    }

    @NotNull
    public final CompileOptions copy(@NotNull UndefinedVariableBehavior undefinedVariable, @NotNull ProjectionIterationBehavior projectionIteration, @NotNull VisitorTransformMode visitorTransformMode, @NotNull ThunkOptions thunkOptions) {
        Intrinsics.checkParameterIsNotNull((Object)undefinedVariable, "undefinedVariable");
        Intrinsics.checkParameterIsNotNull((Object)projectionIteration, "projectionIteration");
        Intrinsics.checkParameterIsNotNull((Object)visitorTransformMode, "visitorTransformMode");
        Intrinsics.checkParameterIsNotNull(thunkOptions, "thunkOptions");
        return new CompileOptions(undefinedVariable, projectionIteration, visitorTransformMode, thunkOptions);
    }

    public static /* synthetic */ CompileOptions copy$default(CompileOptions compileOptions, UndefinedVariableBehavior undefinedVariableBehavior, ProjectionIterationBehavior projectionIterationBehavior, VisitorTransformMode visitorTransformMode, ThunkOptions thunkOptions, int n, Object object) {
        if ((n & 1) != 0) {
            undefinedVariableBehavior = compileOptions.undefinedVariable;
        }
        if ((n & 2) != 0) {
            projectionIterationBehavior = compileOptions.projectionIteration;
        }
        if ((n & 4) != 0) {
            visitorTransformMode = compileOptions.visitorTransformMode;
        }
        if ((n & 8) != 0) {
            thunkOptions = compileOptions.thunkOptions;
        }
        return compileOptions.copy(undefinedVariableBehavior, projectionIterationBehavior, visitorTransformMode, thunkOptions);
    }

    @NotNull
    public String toString() {
        return "CompileOptions(undefinedVariable=" + (Object)((Object)this.undefinedVariable) + ", projectionIteration=" + (Object)((Object)this.projectionIteration) + ", visitorTransformMode=" + (Object)((Object)this.visitorTransformMode) + ", thunkOptions=" + this.thunkOptions + ")";
    }

    public int hashCode() {
        UndefinedVariableBehavior undefinedVariableBehavior = this.undefinedVariable;
        ProjectionIterationBehavior projectionIterationBehavior = this.projectionIteration;
        VisitorTransformMode visitorTransformMode = this.visitorTransformMode;
        ThunkOptions thunkOptions = this.thunkOptions;
        return (((undefinedVariableBehavior != null ? ((Object)((Object)undefinedVariableBehavior)).hashCode() : 0) * 31 + (projectionIterationBehavior != null ? ((Object)((Object)projectionIterationBehavior)).hashCode() : 0)) * 31 + (visitorTransformMode != null ? ((Object)((Object)visitorTransformMode)).hashCode() : 0)) * 31 + (thunkOptions != null ? ((Object)thunkOptions).hashCode() : 0);
    }

    public boolean equals(@Nullable Object object) {
        block3: {
            block2: {
                if (this == object) break block2;
                if (!(object instanceof CompileOptions)) break block3;
                CompileOptions compileOptions = (CompileOptions)object;
                if (!Intrinsics.areEqual((Object)this.undefinedVariable, (Object)compileOptions.undefinedVariable) || !Intrinsics.areEqual((Object)this.projectionIteration, (Object)compileOptions.projectionIteration) || !Intrinsics.areEqual((Object)this.visitorTransformMode, (Object)compileOptions.visitorTransformMode) || !Intrinsics.areEqual(this.thunkOptions, compileOptions.thunkOptions)) break block3;
            }
            return true;
        }
        return false;
    }

    @JvmStatic
    @NotNull
    public static final Builder builder() {
        return Companion.builder();
    }

    @JvmStatic
    @NotNull
    public static final CompileOptions standard() {
        return Companion.standard();
    }

    @Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u00008\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0006\u0010\u0005\u001a\u00020\u0004J\u000e\u0010\u0006\u001a\u00020\u00002\u0006\u0010\u0007\u001a\u00020\bJ\"\u0010\t\u001a\u00020\u00002\u0017\u0010\n\u001a\u0013\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00040\u000b\u00a2\u0006\u0002\b\fH\u0082\bJ\u000e\u0010\r\u001a\u00020\u00002\u0006\u0010\u0007\u001a\u00020\u000eJ\u000e\u0010\u000f\u001a\u00020\u00002\u0006\u0010\u0007\u001a\u00020\u0010J\u000e\u0010\u0011\u001a\u00020\u00002\u0006\u0010\u0007\u001a\u00020\u0012R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0013"}, d2={"Lorg/partiql/lang/eval/CompileOptions$Builder;", "", "()V", "options", "Lorg/partiql/lang/eval/CompileOptions;", "build", "projectionIteration", "value", "Lorg/partiql/lang/eval/ProjectionIterationBehavior;", "set", "block", "Lkotlin/Function1;", "Lkotlin/ExtensionFunctionType;", "thunkOptions", "Lorg/partiql/lang/eval/ThunkOptions;", "undefinedVariable", "Lorg/partiql/lang/eval/UndefinedVariableBehavior;", "visitorTransformMode", "Lorg/partiql/lang/eval/VisitorTransformMode;", "lang"})
    public static final class Builder {
        private CompileOptions options = new CompileOptions(UndefinedVariableBehavior.ERROR, null, null, null, 14, null);

        /*
         * WARNING - void declaration
         */
        @NotNull
        public final Builder undefinedVariable(@NotNull UndefinedVariableBehavior value) {
            void var2_2;
            void $this$set;
            Intrinsics.checkParameterIsNotNull((Object)value, "value");
            Builder this_$iv = this;
            boolean $i$f$set = false;
            CompileOptions compileOptions = this_$iv.options;
            Builder builder = this_$iv;
            boolean bl = false;
            CompileOptions compileOptions2 = CompileOptions.copy$default((CompileOptions)$this$set, value, null, null, null, 14, null);
            builder.options = compileOptions2;
            return var2_2;
        }

        /*
         * WARNING - void declaration
         */
        @NotNull
        public final Builder projectionIteration(@NotNull ProjectionIterationBehavior value) {
            void var2_2;
            void $this$set;
            Intrinsics.checkParameterIsNotNull((Object)value, "value");
            Builder this_$iv = this;
            boolean $i$f$set = false;
            CompileOptions compileOptions = this_$iv.options;
            Builder builder = this_$iv;
            boolean bl = false;
            CompileOptions compileOptions2 = CompileOptions.copy$default((CompileOptions)$this$set, null, value, null, null, 13, null);
            builder.options = compileOptions2;
            return var2_2;
        }

        /*
         * WARNING - void declaration
         */
        @NotNull
        public final Builder visitorTransformMode(@NotNull VisitorTransformMode value) {
            void var2_2;
            void $this$set;
            Intrinsics.checkParameterIsNotNull((Object)value, "value");
            Builder this_$iv = this;
            boolean $i$f$set = false;
            CompileOptions compileOptions = this_$iv.options;
            Builder builder = this_$iv;
            boolean bl = false;
            CompileOptions compileOptions2 = CompileOptions.copy$default((CompileOptions)$this$set, null, null, value, null, 11, null);
            builder.options = compileOptions2;
            return var2_2;
        }

        /*
         * WARNING - void declaration
         */
        @NotNull
        public final Builder thunkOptions(@NotNull ThunkOptions value) {
            void var2_2;
            void $this$set;
            Intrinsics.checkParameterIsNotNull(value, "value");
            Builder this_$iv = this;
            boolean $i$f$set = false;
            CompileOptions compileOptions = this_$iv.options;
            Builder builder = this_$iv;
            boolean bl = false;
            CompileOptions compileOptions2 = CompileOptions.copy$default((CompileOptions)$this$set, null, null, null, value, 7, null);
            builder.options = compileOptions2;
            return var2_2;
        }

        private final Builder set(Function1<? super CompileOptions, CompileOptions> block) {
            int $i$f$set = 0;
            this.options = block.invoke(this.options);
            return this;
        }

        @NotNull
        public final CompileOptions build() {
            return this.options;
        }
    }

    @Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u001f\u0010\u0003\u001a\u00020\u00042\u0017\u0010\u0005\u001a\u0013\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\b0\u0006\u00a2\u0006\u0002\b\tJ\b\u0010\n\u001a\u00020\u0007H\u0007J\b\u0010\u000b\u001a\u00020\u0004H\u0007\u00a8\u0006\f"}, d2={"Lorg/partiql/lang/eval/CompileOptions$Companion;", "", "()V", "build", "Lorg/partiql/lang/eval/CompileOptions;", "block", "Lkotlin/Function1;", "Lorg/partiql/lang/eval/CompileOptions$Builder;", "", "Lkotlin/ExtensionFunctionType;", "builder", "standard", "lang"})
    public static final class Companion {
        @JvmStatic
        @NotNull
        public final Builder builder() {
            return new Builder();
        }

        @NotNull
        public final CompileOptions build(@NotNull Function1<? super Builder, Unit> block) {
            Intrinsics.checkParameterIsNotNull(block, "block");
            Builder builder = new Builder();
            boolean bl = false;
            boolean bl2 = false;
            block.invoke(builder);
            return builder.build();
        }

        @JvmStatic
        @NotNull
        public final CompileOptions standard() {
            return new Builder().build();
        }

        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

