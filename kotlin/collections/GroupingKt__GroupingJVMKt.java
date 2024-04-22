/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package kotlin.collections;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.Metadata;
import kotlin.PublishedApi;
import kotlin.SinceKotlin;
import kotlin.collections.Grouping;
import kotlin.internal.InlineOnly;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import kotlin.jvm.internal.TypeIntrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 6, 0}, k=5, xi=49, d1={"\u0000&\n\u0000\n\u0002\u0010$\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010%\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010&\n\u0000\u001a0\u0010\u0000\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u00020\u00030\u0001\"\u0004\b\u0000\u0010\u0004\"\u0004\b\u0001\u0010\u0002*\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00020\u0005H\u0007\u001aZ\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\b0\u0007\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\t\"\u0004\b\u0002\u0010\b*\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\t0\u00072\u001e\u0010\n\u001a\u001a\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\t0\f\u0012\u0004\u0012\u0002H\b0\u000bH\u0081\b\u00f8\u0001\u0000\u0082\u0002\u0007\n\u0005\b\u009920\u0001\u00a8\u0006\r"}, d2={"eachCount", "", "K", "", "T", "Lkotlin/collections/Grouping;", "mapValuesInPlace", "", "R", "V", "f", "Lkotlin/Function1;", "", "kotlin-stdlib"}, xs="kotlin/collections/GroupingKt")
class GroupingKt__GroupingJVMKt {
    /*
     * WARNING - void declaration
     */
    @SinceKotlin(version="1.1")
    @NotNull
    public static final <T, K> Map<K, Integer> eachCount(@NotNull Grouping<T, ? extends K> $this$eachCount) {
        Map.Entry entry;
        void $this$foldTo$iv;
        Intrinsics.checkNotNullParameter($this$eachCount, "<this>");
        Object object = $this$eachCount;
        Map destination$iv = new LinkedHashMap();
        boolean $i$f$foldTo = false;
        void $this$aggregateTo$iv$iv = $this$foldTo$iv;
        boolean $i$f$aggregateTo2 = false;
        Iterator iterator2 = $this$aggregateTo$iv$iv.sourceIterator();
        while (iterator2.hasNext()) {
            void acc;
            void var21_24;
            Ref.IntRef intRef;
            void first$iv;
            void key$iv;
            Object e$iv$iv = iterator2.next();
            Object key$iv$iv = $this$aggregateTo$iv$iv.keyOf(e$iv$iv);
            Object accumulator$iv$iv = destination$iv.get(key$iv$iv);
            boolean bl = accumulator$iv$iv == null && !destination$iv.containsKey(key$iv$iv);
            Object t = e$iv$iv;
            Object v = accumulator$iv$iv;
            Object k = key$iv$iv;
            Object k2 = key$iv$iv;
            Map map2 = destination$iv;
            boolean bl2 = false;
            Map.Entry entry2 = key$iv;
            if (first$iv != false) {
                entry = entry2;
                boolean bl3 = false;
                intRef = new Ref.IntRef();
                entry2 = entry;
            } else {
                void acc$iv;
                intRef = acc$iv;
            }
            Ref.IntRef intRef2 = intRef;
            boolean bl4 = false;
            void $this$eachCount_u24lambda_u2d2_u24lambda_u2d1 = var21_24 = acc;
            boolean bl5 = false;
            ++$this$eachCount_u24lambda_u2d2_u24lambda_u2d1.element;
            void var18_21 = var21_24;
            map2.put(k2, var18_21);
        }
        object = destination$iv;
        Iterable iterable = object.entrySet();
        Iterator iterator3 = iterable.iterator();
        while (iterator3.hasNext()) {
            void it;
            Map.Entry entry3;
            Map.Entry $i$f$aggregateTo2 = entry3 = (Map.Entry)iterator3.next();
            entry = TypeIntrinsics.asMutableMapEntry(entry3);
            boolean bl = false;
            entry.setValue(((Ref.IntRef)it.getValue()).element);
        }
        return TypeIntrinsics.asMutableMap(object);
    }

    @PublishedApi
    @InlineOnly
    private static final <K, V, R> Map<K, R> mapValuesInPlace(Map<K, V> $this$mapValuesInPlace, Function1<? super Map.Entry<? extends K, ? extends V>, ? extends R> f) {
        Intrinsics.checkNotNullParameter($this$mapValuesInPlace, "<this>");
        Intrinsics.checkNotNullParameter(f, "f");
        Iterable $this$forEach$iv = $this$mapValuesInPlace.entrySet();
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            Map.Entry it = (Map.Entry)element$iv;
            boolean bl = false;
            TypeIntrinsics.asMutableMapEntry(it).setValue(f.invoke(it));
        }
        return TypeIntrinsics.asMutableMap($this$mapValuesInPlace);
    }
}

