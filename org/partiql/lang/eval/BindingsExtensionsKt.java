/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package org.partiql.lang.eval;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.TypeCastException;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.partiql.lang.eval.BindingName;
import org.partiql.lang.eval.Bindings;
import org.partiql.lang.eval.BindingsExtensionsKt;
import org.partiql.lang.eval.BindingsExtensionsKt$WhenMappings;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=2, d1={"\u0000\u0016\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0004\u001a5\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00012\u0012\u0010\u0003\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00050\u0004\"\u00020\u0005\u00a2\u0006\u0002\u0010\u0006\u001a*\u0010\u0007\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00012\f\u0010\b\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\u00a8\u0006\t"}, d2={"blacklist", "Lorg/partiql/lang/eval/Bindings;", "T", "names", "", "", "(Lorg/partiql/lang/eval/Bindings;[Ljava/lang/String;)Lorg/partiql/lang/eval/Bindings;", "delegate", "fallback", "lang"})
public final class BindingsExtensionsKt {
    @NotNull
    public static final <T> Bindings<T> delegate(@NotNull Bindings<T> $this$delegate, @NotNull Bindings<T> fallback) {
        Intrinsics.checkParameterIsNotNull($this$delegate, "$this$delegate");
        Intrinsics.checkParameterIsNotNull(fallback, "fallback");
        return new Bindings<T>($this$delegate, fallback){
            final /* synthetic */ Bindings $this_delegate;
            final /* synthetic */ Bindings $fallback;

            @Nullable
            public T get(@NotNull BindingName bindingName) {
                Intrinsics.checkParameterIsNotNull(bindingName, "bindingName");
                T binding2 = this.$this_delegate.get(bindingName);
                T t = binding2;
                if (t == null) {
                    t = this.$fallback.get(bindingName);
                }
                return t;
            }
            {
                this.$this_delegate = $receiver;
                this.$fallback = $captured_local_variable$1;
            }
        };
    }

    @NotNull
    public static final <T> Bindings<T> blacklist(@NotNull Bindings<T> $this$blacklist, @NotNull String ... names) {
        Intrinsics.checkParameterIsNotNull($this$blacklist, "$this$blacklist");
        Intrinsics.checkParameterIsNotNull(names, "names");
        return new Bindings<T>(names){
            @NotNull
            private final Set<String> blacklisted;
            @NotNull
            private final Set<String> loweredBlacklisted;
            final /* synthetic */ String[] $names;

            @NotNull
            public final Set<String> getBlacklisted() {
                return this.blacklisted;
            }

            @NotNull
            public final Set<String> getLoweredBlacklisted() {
                return this.loweredBlacklisted;
            }

            @Nullable
            public T get(@NotNull BindingName bindingName) {
                boolean bl;
                Intrinsics.checkParameterIsNotNull(bindingName, "bindingName");
                switch (BindingsExtensionsKt$WhenMappings.$EnumSwitchMapping$0[bindingName.getBindingCase().ordinal()]) {
                    case 1: {
                        bl = this.blacklisted.contains(bindingName.getName());
                        break;
                    }
                    case 2: {
                        bl = this.loweredBlacklisted.contains(bindingName.getLoweredName());
                        break;
                    }
                    default: {
                        throw new NoWhenBranchMatchedException();
                    }
                }
                boolean isBlacklisted = bl;
                return isBlacklisted ? null : (T)this.get(bindingName);
            }
            {
                Collection<String> collection;
                void $this$mapTo$iv$iv;
                void $this$map$iv;
                this.$names = $captured_local_variable$0;
                this.blacklisted = ArraysKt.toSet($captured_local_variable$0);
                String[] stringArray = $captured_local_variable$0;
                blacklist.1 var15_3 = this;
                boolean $i$f$map = false;
                void var4_5 = $this$map$iv;
                Collection destination$iv$iv = new ArrayList<E>(((void)$this$map$iv).length);
                boolean $i$f$mapTo = false;
                void var7_8 = $this$mapTo$iv$iv;
                int n = ((void)var7_8).length;
                for (int i = 0; i < n; ++i) {
                    String string;
                    void it;
                    void item$iv$iv;
                    void var11_12 = item$iv$iv = var7_8[i];
                    collection = destination$iv$iv;
                    boolean bl = false;
                    void var13_14 = it;
                    boolean bl2 = false;
                    void v0 = var13_14;
                    if (v0 == null) {
                        throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                    }
                    Intrinsics.checkExpressionValueIsNotNull(v0.toLowerCase(), "(this as java.lang.String).toLowerCase()");
                    collection.add(string);
                }
                collection = (List)destination$iv$iv;
                var15_3.loweredBlacklisted = CollectionsKt.toSet((Iterable)collection);
            }
        };
    }
}

