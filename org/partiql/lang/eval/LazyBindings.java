/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package org.partiql.lang.eval;

import java.util.Map;
import kotlin.Lazy;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.partiql.lang.eval.BindingName;
import org.partiql.lang.eval.Bindings;
import org.partiql.lang.eval.MapBindings;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000$\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0002\u0018\u0000*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u0002B\u001f\u0012\u0018\u0010\u0003\u001a\u0014\u0012\u0004\u0012\u00020\u0005\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\u00060\u0004\u00a2\u0006\u0002\u0010\u0007J\u0018\u0010\t\u001a\u0004\u0018\u00018\u00002\u0006\u0010\n\u001a\u00020\u000bH\u0096\u0002\u00a2\u0006\u0002\u0010\fR\u001a\u0010\b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\u00060\u0002X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\r"}, d2={"Lorg/partiql/lang/eval/LazyBindings;", "T", "Lorg/partiql/lang/eval/Bindings;", "originalCaseMap", "", "", "Lkotlin/Lazy;", "(Ljava/util/Map;)V", "delegate", "get", "bindingName", "Lorg/partiql/lang/eval/BindingName;", "(Lorg/partiql/lang/eval/BindingName;)Ljava/lang/Object;", "lang"})
final class LazyBindings<T>
implements Bindings<T> {
    private final Bindings<Lazy<T>> delegate;

    @Override
    @Nullable
    public T get(@NotNull BindingName bindingName) {
        Intrinsics.checkParameterIsNotNull(bindingName, "bindingName");
        Lazy<T> lazy = this.delegate.get(bindingName);
        return (T)(lazy != null ? lazy.getValue() : null);
    }

    public LazyBindings(@NotNull Map<String, ? extends Lazy<? extends T>> originalCaseMap) {
        Intrinsics.checkParameterIsNotNull(originalCaseMap, "originalCaseMap");
        this.delegate = new MapBindings<Lazy<? extends T>>(originalCaseMap);
    }
}

