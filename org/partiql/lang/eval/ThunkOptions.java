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
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.partiql.lang.eval.ThunkKt;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u00008\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0003\n\u0002\u0018\u0002\n\u0002\u0010\u0001\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\b\u0086\b\u0018\u0000 \u00152\u00020\u0001:\u0002\u0014\u0015B)\b\u0002\u0012 \b\u0002\u0010\u0002\u001a\u001a\u0012\u0004\u0012\u00020\u0004\u0012\u0006\u0012\u0004\u0018\u00010\u0005\u0012\u0004\u0012\u00020\u00060\u0003j\u0002`\u0007\u00a2\u0006\u0002\u0010\bJ!\u0010\u000b\u001a\u001a\u0012\u0004\u0012\u00020\u0004\u0012\u0006\u0012\u0004\u0018\u00010\u0005\u0012\u0004\u0012\u00020\u00060\u0003j\u0002`\u0007H\u00c6\u0003J+\u0010\f\u001a\u00020\u00002 \b\u0002\u0010\u0002\u001a\u001a\u0012\u0004\u0012\u00020\u0004\u0012\u0006\u0012\u0004\u0018\u00010\u0005\u0012\u0004\u0012\u00020\u00060\u0003j\u0002`\u0007H\u00c6\u0001J\u0013\u0010\r\u001a\u00020\u000e2\b\u0010\u000f\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u0010\u001a\u00020\u0011H\u00d6\u0001J\t\u0010\u0012\u001a\u00020\u0013H\u00d6\u0001R)\u0010\u0002\u001a\u001a\u0012\u0004\u0012\u00020\u0004\u0012\u0006\u0012\u0004\u0018\u00010\u0005\u0012\u0004\u0012\u00020\u00060\u0003j\u0002`\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n\u00a8\u0006\u0016"}, d2={"Lorg/partiql/lang/eval/ThunkOptions;", "", "handleException", "Lkotlin/Function2;", "", "Lorg/partiql/lang/ast/SourceLocationMeta;", "", "Lorg/partiql/lang/eval/ThunkExceptionHandler;", "(Lkotlin/jvm/functions/Function2;)V", "getHandleException", "()Lkotlin/jvm/functions/Function2;", "component1", "copy", "equals", "", "other", "hashCode", "", "toString", "", "Builder", "Companion", "lang"})
public final class ThunkOptions {
    @NotNull
    private final Function2 handleException;
    public static final Companion Companion = new Companion(null);

    @NotNull
    public final Function2 getHandleException() {
        return this.handleException;
    }

    private ThunkOptions(Function2 handleException) {
        this.handleException = handleException;
    }

    /* synthetic */ ThunkOptions(Function2 function2, int n, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n & 1) != 0) {
            function2 = ThunkKt.getDefaultExceptionHandler();
        }
        this(function2);
    }

    @NotNull
    public final Function2 component1() {
        return this.handleException;
    }

    @NotNull
    public final ThunkOptions copy(@NotNull Function2 handleException) {
        Intrinsics.checkParameterIsNotNull(handleException, "handleException");
        return new ThunkOptions(handleException);
    }

    public static /* synthetic */ ThunkOptions copy$default(ThunkOptions thunkOptions, Function2 function2, int n, Object object) {
        if ((n & 1) != 0) {
            function2 = thunkOptions.handleException;
        }
        return thunkOptions.copy(function2);
    }

    @NotNull
    public String toString() {
        return "ThunkOptions(handleException=" + this.handleException + ")";
    }

    public int hashCode() {
        Function2 function2 = this.handleException;
        return function2 != null ? function2.hashCode() : 0;
    }

    public boolean equals(@Nullable Object object) {
        block3: {
            block2: {
                if (this == object) break block2;
                if (!(object instanceof ThunkOptions)) break block3;
                ThunkOptions thunkOptions = (ThunkOptions)object;
                if (!Intrinsics.areEqual(this.handleException, thunkOptions.handleException)) break block3;
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
    public static final ThunkOptions standard() {
        return Companion.standard();
    }

    @Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010\u0003\n\u0002\u0018\u0002\n\u0002\u0010\u0001\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0006\u0010\u0005\u001a\u00020\u0004J&\u0010\u0006\u001a\u00020\u00002\u001e\u0010\u0007\u001a\u001a\u0012\u0004\u0012\u00020\t\u0012\u0006\u0012\u0004\u0018\u00010\n\u0012\u0004\u0012\u00020\u000b0\bj\u0002`\fJ\"\u0010\r\u001a\u00020\u00002\u0017\u0010\u000e\u001a\u0013\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00040\u000f\u00a2\u0006\u0002\b\u0010H\u0082\bR\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0011"}, d2={"Lorg/partiql/lang/eval/ThunkOptions$Builder;", "", "()V", "options", "Lorg/partiql/lang/eval/ThunkOptions;", "build", "handleException", "value", "Lkotlin/Function2;", "", "Lorg/partiql/lang/ast/SourceLocationMeta;", "", "Lorg/partiql/lang/eval/ThunkExceptionHandler;", "set", "block", "Lkotlin/Function1;", "Lkotlin/ExtensionFunctionType;", "lang"})
    public static final class Builder {
        private ThunkOptions options = new ThunkOptions(null, 1, null);

        /*
         * WARNING - void declaration
         */
        @NotNull
        public final Builder handleException(@NotNull Function2 value) {
            void var2_2;
            void $this$set;
            Intrinsics.checkParameterIsNotNull(value, "value");
            Builder this_$iv = this;
            boolean $i$f$set = false;
            ThunkOptions thunkOptions = this_$iv.options;
            Builder builder = this_$iv;
            boolean bl = false;
            ThunkOptions thunkOptions2 = $this$set.copy(value);
            builder.options = thunkOptions2;
            return var2_2;
        }

        private final Builder set(Function1<? super ThunkOptions, ThunkOptions> block) {
            int $i$f$set = 0;
            this.options = block.invoke(this.options);
            return this;
        }

        @NotNull
        public final ThunkOptions build() {
            return this.options;
        }
    }

    @Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u001f\u0010\u0003\u001a\u00020\u00042\u0017\u0010\u0005\u001a\u0013\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\b0\u0006\u00a2\u0006\u0002\b\tJ\b\u0010\n\u001a\u00020\u0007H\u0007J\b\u0010\u000b\u001a\u00020\u0004H\u0007\u00a8\u0006\f"}, d2={"Lorg/partiql/lang/eval/ThunkOptions$Companion;", "", "()V", "build", "Lorg/partiql/lang/eval/ThunkOptions;", "block", "Lkotlin/Function1;", "Lorg/partiql/lang/eval/ThunkOptions$Builder;", "", "Lkotlin/ExtensionFunctionType;", "builder", "standard", "lang"})
    public static final class Companion {
        @JvmStatic
        @NotNull
        public final Builder builder() {
            return new Builder();
        }

        @NotNull
        public final ThunkOptions build(@NotNull Function1<? super Builder, Unit> block) {
            Intrinsics.checkParameterIsNotNull(block, "block");
            Builder builder = new Builder();
            boolean bl = false;
            boolean bl2 = false;
            block.invoke(builder);
            return builder.build();
        }

        @JvmStatic
        @NotNull
        public final ThunkOptions standard() {
            return new Builder().build();
        }

        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

