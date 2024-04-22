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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.TypeCastException;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.PropertyReference1Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.partiql.lang.eval.BindingName;
import org.partiql.lang.eval.Bindings;
import org.partiql.lang.eval.MapBindings$WhenMappings;
import org.partiql.lang.util.BindingHelpersKt;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000,\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0010&\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u0000*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u0002B\u0019\u0012\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00028\u00000\u0004\u00a2\u0006\u0002\u0010\u0006J\u0018\u0010\u000f\u001a\u0004\u0018\u00018\u00002\u0006\u0010\u0010\u001a\u00020\u0011H\u0096\u0002\u00a2\u0006\u0002\u0010\u0012R9\u0010\u0007\u001a \u0012\u0004\u0012\u00020\u0005\u0012\u0016\u0012\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00028\u00000\t0\b0\u00048BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\f\u0010\r\u001a\u0004\b\n\u0010\u000bR\u001d\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00028\u00000\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000b\u00a8\u0006\u0013"}, d2={"Lorg/partiql/lang/eval/MapBindings;", "T", "Lorg/partiql/lang/eval/Bindings;", "originalCaseMap", "", "", "(Ljava/util/Map;)V", "loweredCaseMap", "", "", "getLoweredCaseMap", "()Ljava/util/Map;", "loweredCaseMap$delegate", "Lkotlin/Lazy;", "getOriginalCaseMap", "get", "bindingName", "Lorg/partiql/lang/eval/BindingName;", "(Lorg/partiql/lang/eval/BindingName;)Ljava/lang/Object;", "lang"})
public final class MapBindings<T>
implements Bindings<T> {
    static final /* synthetic */ KProperty[] $$delegatedProperties;
    private final Lazy loweredCaseMap$delegate;
    @NotNull
    private final Map<String, T> originalCaseMap;

    static {
        $$delegatedProperties = new KProperty[]{Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(MapBindings.class), "loweredCaseMap", "getLoweredCaseMap()Ljava/util/Map;"))};
    }

    private final Map<String, List<Map.Entry<String, T>>> getLoweredCaseMap() {
        Lazy lazy = this.loweredCaseMap$delegate;
        MapBindings mapBindings = this;
        KProperty kProperty = $$delegatedProperties[0];
        boolean bl = false;
        return (Map)lazy.getValue();
    }

    /*
     * WARNING - void declaration
     */
    @Override
    @Nullable
    public T get(@NotNull BindingName bindingName) {
        T t;
        Intrinsics.checkParameterIsNotNull(bindingName, "bindingName");
        switch (MapBindings$WhenMappings.$EnumSwitchMapping$0[bindingName.getBindingCase().ordinal()]) {
            case 1: {
                t = this.originalCaseMap.get(bindingName.getName());
                break;
            }
            case 2: {
                Collection<String> collection;
                void $this$mapTo$iv$iv;
                void $this$map$iv;
                List<Map.Entry<String, T>> foundBindings = this.getLoweredCaseMap().get(bindingName.getLoweredName());
                if (foundBindings == null) {
                    t = null;
                    break;
                }
                if (foundBindings.size() == 1) {
                    t = CollectionsKt.first(foundBindings).getValue();
                    break;
                }
                Iterable iterable = foundBindings;
                String string = bindingName.getName();
                boolean $i$f$map = false;
                void var5_6 = $this$map$iv;
                Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
                boolean $i$f$mapTo = false;
                for (Object item$iv$iv : $this$mapTo$iv$iv) {
                    void it;
                    Map.Entry entry = (Map.Entry)item$iv$iv;
                    collection = destination$iv$iv;
                    boolean bl = false;
                    String string2 = (String)it.getKey();
                    collection.add(string2);
                }
                collection = (List)destination$iv$iv;
                Void void_ = BindingHelpersKt.errAmbiguousBinding(string, (List<String>)collection);
                throw null;
            }
            default: {
                throw new NoWhenBranchMatchedException();
            }
        }
        return t;
    }

    @NotNull
    public final Map<String, T> getOriginalCaseMap() {
        return this.originalCaseMap;
    }

    public MapBindings(@NotNull Map<String, ? extends T> originalCaseMap) {
        Intrinsics.checkParameterIsNotNull(originalCaseMap, "originalCaseMap");
        this.originalCaseMap = originalCaseMap;
        this.loweredCaseMap$delegate = LazyKt.lazy(new Function0<Map<String, ? extends List<? extends Map.Entry<? extends String, ? extends T>>>>(this){
            final /* synthetic */ MapBindings this$0;

            /*
             * WARNING - void declaration
             */
            @NotNull
            public final Map<String, List<Map.Entry<String, T>>> invoke() {
                void $this$groupByTo$iv$iv;
                Iterable $this$groupBy$iv = this.this$0.getOriginalCaseMap().entrySet();
                boolean $i$f$groupBy = false;
                Iterable iterable = $this$groupBy$iv;
                Map destination$iv$iv = new LinkedHashMap<K, V>();
                boolean $i$f$groupByTo = false;
                for (T element$iv$iv : $this$groupByTo$iv$iv) {
                    Object object;
                    String key$iv$iv;
                    Map.Entry it = (Map.Entry)element$iv$iv;
                    boolean bl = false;
                    String string = (String)it.getKey();
                    boolean bl2 = false;
                    String string2 = string;
                    if (string2 == null) {
                        throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                    }
                    Intrinsics.checkExpressionValueIsNotNull(string2.toLowerCase(), "(this as java.lang.String).toLowerCase()");
                    Map $this$getOrPut$iv$iv$iv = destination$iv$iv;
                    boolean $i$f$getOrPut = false;
                    V value$iv$iv$iv = $this$getOrPut$iv$iv$iv.get(key$iv$iv);
                    if (value$iv$iv$iv == null) {
                        boolean bl3 = false;
                        ArrayList<E> answer$iv$iv$iv = new ArrayList<E>();
                        $this$getOrPut$iv$iv$iv.put(key$iv$iv, answer$iv$iv$iv);
                        object = answer$iv$iv$iv;
                    } else {
                        object = value$iv$iv$iv;
                    }
                    List list$iv$iv = (List)object;
                    list$iv$iv.add(element$iv$iv);
                }
                return destination$iv$iv;
            }
            {
                this.this$0 = mapBindings;
                super(0);
            }
        });
    }
}

