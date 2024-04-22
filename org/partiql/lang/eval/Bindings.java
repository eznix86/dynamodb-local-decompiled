/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package org.partiql.lang.eval;

import com.amazon.ion.IonStruct;
import java.util.HashMap;
import java.util.Map;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.Unit;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.partiql.lang.eval.BindingName;
import org.partiql.lang.eval.Bindings;
import org.partiql.lang.eval.EvaluationException;
import org.partiql.lang.eval.ExprValue;
import org.partiql.lang.eval.ExprValueFactory;
import org.partiql.lang.eval.IonStructBindings;
import org.partiql.lang.eval.LazyBindings;
import org.partiql.lang.eval.MapBindings;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000\u0016\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\bf\u0018\u0000 \u0007*\u0004\b\u0000\u0010\u00012\u00020\u0002:\u0002\u0007\bJ\u0018\u0010\u0003\u001a\u0004\u0018\u00018\u00002\u0006\u0010\u0004\u001a\u00020\u0005H\u00a6\u0002\u00a2\u0006\u0002\u0010\u0006\u00a8\u0006\t"}, d2={"Lorg/partiql/lang/eval/Bindings;", "T", "", "get", "bindingName", "Lorg/partiql/lang/eval/BindingName;", "(Lorg/partiql/lang/eval/BindingName;)Ljava/lang/Object;", "Companion", "LazyBindingBuilder", "lang"})
public interface Bindings<T> {
    public static final Companion Companion = org.partiql.lang.eval.Bindings$Companion.$$INSTANCE;

    @Nullable
    public T get(@NotNull BindingName var1) throws EvaluationException;

    @JvmStatic
    @NotNull
    public static <T> LazyBindingBuilder<T> lazyBindingsBuilder() {
        return Companion.lazyBindingsBuilder();
    }

    @JvmStatic
    @NotNull
    public static <T> Bindings<T> ofMap(@NotNull Map<String, ? extends T> backingMap) {
        return Companion.ofMap(backingMap);
    }

    @JvmStatic
    @NotNull
    public static Bindings<ExprValue> ofIonStruct(@NotNull IonStruct struct, @NotNull ExprValueFactory valueFactory) {
        return Companion.ofIonStruct(struct, valueFactory);
    }

    @Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000.\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u0000*\u0004\b\u0001\u0010\u00012\u00020\u0002B\u0005\u00a2\u0006\u0002\u0010\u0003J\"\u0010\t\u001a\b\u0012\u0004\u0012\u00028\u00010\u00002\u0006\u0010\n\u001a\u00020\u00062\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00028\u00010\fJ\f\u0010\r\u001a\b\u0012\u0004\u0012\u00028\u00010\u000eR6\u0010\u0004\u001a*\u0012\u0004\u0012\u00020\u0006\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00010\u00070\u0005j\u0014\u0012\u0004\u0012\u00020\u0006\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00010\u0007`\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u000f"}, d2={"Lorg/partiql/lang/eval/Bindings$LazyBindingBuilder;", "T", "", "()V", "bindings", "Ljava/util/HashMap;", "", "Lkotlin/Lazy;", "Lkotlin/collections/HashMap;", "addBinding", "name", "getter", "Lkotlin/Function0;", "build", "Lorg/partiql/lang/eval/Bindings;", "lang"})
    public static final class LazyBindingBuilder<T> {
        private final HashMap<String, Lazy<T>> bindings = new HashMap();

        @NotNull
        public final LazyBindingBuilder<T> addBinding(@NotNull String name, @NotNull Function0<? extends T> getter) {
            Intrinsics.checkParameterIsNotNull(name, "name");
            Intrinsics.checkParameterIsNotNull(getter, "getter");
            LazyBindingBuilder lazyBindingBuilder = this;
            boolean bl = false;
            boolean bl2 = false;
            LazyBindingBuilder $this$apply = lazyBindingBuilder;
            boolean bl3 = false;
            ((Map)$this$apply.bindings).put(name, LazyKt.lazy(getter));
            return lazyBindingBuilder;
        }

        @NotNull
        public final Bindings<T> build() {
            return new LazyBindings((Map)this.bindings);
        }
    }

    @Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000R\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0001\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J1\u0010\u0006\u001a\b\u0012\u0004\u0012\u0002H\u00070\u0004\"\u0004\b\u0001\u0010\u00072\u001d\u0010\b\u001a\u0019\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00070\n\u0012\u0004\u0012\u00020\u000b0\t\u00a2\u0006\u0002\b\fJ\u0012\u0010\r\u001a\b\u0012\u0004\u0012\u0002H\u00070\u0004\"\u0004\b\u0001\u0010\u0007J\u0014\u0010\u000e\u001a\b\u0012\u0004\u0012\u0002H\u00070\n\"\u0004\b\u0001\u0010\u0007H\u0007J\u001e\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00100\u00042\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0014H\u0007J(\u0010\u0015\u001a\b\u0012\u0004\u0012\u0002H\u00070\u0004\"\u0004\b\u0001\u0010\u00072\u0012\u0010\u0016\u001a\u000e\u0012\u0004\u0012\u00020\u0018\u0012\u0004\u0012\u0002H\u00070\u0017H\u0007J(\u0010\u0019\u001a\b\u0012\u0004\u0012\u0002H\u00070\u0004\"\u0004\b\u0001\u0010\u00072\u0014\u0010\u001a\u001a\u0010\u0012\u0004\u0012\u00020\u001b\u0012\u0006\u0012\u0004\u0018\u0001H\u00070\tR\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001c"}, d2={"Lorg/partiql/lang/eval/Bindings$Companion;", "", "()V", "EMPTY", "Lorg/partiql/lang/eval/Bindings;", "", "buildLazyBindings", "T", "block", "Lkotlin/Function1;", "Lorg/partiql/lang/eval/Bindings$LazyBindingBuilder;", "", "Lkotlin/ExtensionFunctionType;", "empty", "lazyBindingsBuilder", "ofIonStruct", "Lorg/partiql/lang/eval/ExprValue;", "struct", "Lcom/amazon/ion/IonStruct;", "valueFactory", "Lorg/partiql/lang/eval/ExprValueFactory;", "ofMap", "backingMap", "", "", "over", "func", "Lorg/partiql/lang/eval/BindingName;", "lang"})
    public static final class Companion {
        private static final Bindings EMPTY;
        static final /* synthetic */ Companion $$INSTANCE;

        @NotNull
        public final <T> Bindings<T> empty() {
            Bindings bindings2 = EMPTY;
            if (bindings2 == null) {
                throw new TypeCastException("null cannot be cast to non-null type org.partiql.lang.eval.Bindings<T>");
            }
            return bindings2;
        }

        @NotNull
        public final <T> Bindings<T> over(@NotNull Function1<? super BindingName, ? extends T> func) {
            Intrinsics.checkParameterIsNotNull(func, "func");
            return new Bindings<T>(func){
                final /* synthetic */ Function1 $func;

                @Nullable
                public T get(@NotNull BindingName bindingName) {
                    Intrinsics.checkParameterIsNotNull(bindingName, "bindingName");
                    return (T)this.$func.invoke(bindingName);
                }
                {
                    this.$func = $captured_local_variable$0;
                }
            };
        }

        @JvmStatic
        @NotNull
        public final <T> LazyBindingBuilder<T> lazyBindingsBuilder() {
            return new LazyBindingBuilder();
        }

        @NotNull
        public final <T> Bindings<T> buildLazyBindings(@NotNull Function1<? super LazyBindingBuilder<T>, Unit> block) {
            Intrinsics.checkParameterIsNotNull(block, "block");
            LazyBindingBuilder lazyBindingBuilder = new LazyBindingBuilder();
            boolean bl = false;
            boolean bl2 = false;
            block.invoke(lazyBindingBuilder);
            return lazyBindingBuilder.build();
        }

        @JvmStatic
        @NotNull
        public final <T> Bindings<T> ofMap(@NotNull Map<String, ? extends T> backingMap) {
            Intrinsics.checkParameterIsNotNull(backingMap, "backingMap");
            return new MapBindings<T>(backingMap);
        }

        @JvmStatic
        @NotNull
        public final Bindings<ExprValue> ofIonStruct(@NotNull IonStruct struct, @NotNull ExprValueFactory valueFactory) {
            Intrinsics.checkParameterIsNotNull(struct, "struct");
            Intrinsics.checkParameterIsNotNull(valueFactory, "valueFactory");
            return new IonStructBindings(valueFactory, struct);
        }

        private Companion() {
        }

        static {
            Companion companion;
            $$INSTANCE = companion = new Companion();
            EMPTY = companion.over(EMPTY.1.INSTANCE);
        }
    }
}

