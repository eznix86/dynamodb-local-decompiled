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
import kotlin.SinceKotlin;
import kotlin.collections.Grouping;
import kotlin.collections.GroupingKt__GroupingJVMKt;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.functions.Function4;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 6, 0}, k=5, xi=49, d1={"\u0000@\n\u0000\n\u0002\u0010$\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010%\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\n\u001a\u009e\u0001\u0010\u0000\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0001\"\u0004\b\u0000\u0010\u0004\"\u0004\b\u0001\u0010\u0002\"\u0004\b\u0002\u0010\u0003*\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00020\u00052b\u0010\u0006\u001a^\u0012\u0013\u0012\u0011H\u0002\u00a2\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\n\u0012\u0015\u0012\u0013\u0018\u0001H\u0003\u00a2\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\u000b\u0012\u0013\u0012\u0011H\u0004\u00a2\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\f\u0012\u0013\u0012\u00110\r\u00a2\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\u000e\u0012\u0004\u0012\u0002H\u00030\u0007H\u0087\b\u00f8\u0001\u0000\u001a\u00b7\u0001\u0010\u000f\u001a\u0002H\u0010\"\u0004\b\u0000\u0010\u0004\"\u0004\b\u0001\u0010\u0002\"\u0004\b\u0002\u0010\u0003\"\u0016\b\u0003\u0010\u0010*\u0010\u0012\u0006\b\u0000\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0011*\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00020\u00052\u0006\u0010\u0012\u001a\u0002H\u00102b\u0010\u0006\u001a^\u0012\u0013\u0012\u0011H\u0002\u00a2\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\n\u0012\u0015\u0012\u0013\u0018\u0001H\u0003\u00a2\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\u000b\u0012\u0013\u0012\u0011H\u0004\u00a2\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\f\u0012\u0013\u0012\u00110\r\u00a2\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\u000e\u0012\u0004\u0012\u0002H\u00030\u0007H\u0087\b\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0013\u001aI\u0010\u0014\u001a\u0002H\u0010\"\u0004\b\u0000\u0010\u0004\"\u0004\b\u0001\u0010\u0002\"\u0016\b\u0002\u0010\u0010*\u0010\u0012\u0006\b\u0000\u0012\u0002H\u0002\u0012\u0004\u0012\u00020\u00150\u0011*\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00020\u00052\u0006\u0010\u0012\u001a\u0002H\u0010H\u0007\u00a2\u0006\u0002\u0010\u0016\u001a\u00bf\u0001\u0010\u0017\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0001\"\u0004\b\u0000\u0010\u0004\"\u0004\b\u0001\u0010\u0002\"\u0004\b\u0002\u0010\u0003*\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00020\u000526\u0010\u0018\u001a2\u0012\u0013\u0012\u0011H\u0002\u00a2\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\n\u0012\u0013\u0012\u0011H\u0004\u00a2\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\f\u0012\u0004\u0012\u0002H\u00030\u00192K\u0010\u0006\u001aG\u0012\u0013\u0012\u0011H\u0002\u00a2\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\n\u0012\u0013\u0012\u0011H\u0003\u00a2\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\u000b\u0012\u0013\u0012\u0011H\u0004\u00a2\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\f\u0012\u0004\u0012\u0002H\u00030\u001aH\u0087\b\u00f8\u0001\u0000\u001a\u007f\u0010\u0017\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0001\"\u0004\b\u0000\u0010\u0004\"\u0004\b\u0001\u0010\u0002\"\u0004\b\u0002\u0010\u0003*\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00020\u00052\u0006\u0010\u001b\u001a\u0002H\u000326\u0010\u0006\u001a2\u0012\u0013\u0012\u0011H\u0003\u00a2\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\u000b\u0012\u0013\u0012\u0011H\u0004\u00a2\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\f\u0012\u0004\u0012\u0002H\u00030\u0019H\u0087\b\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u001c\u001a\u00d8\u0001\u0010\u001d\u001a\u0002H\u0010\"\u0004\b\u0000\u0010\u0004\"\u0004\b\u0001\u0010\u0002\"\u0004\b\u0002\u0010\u0003\"\u0016\b\u0003\u0010\u0010*\u0010\u0012\u0006\b\u0000\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0011*\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00020\u00052\u0006\u0010\u0012\u001a\u0002H\u001026\u0010\u0018\u001a2\u0012\u0013\u0012\u0011H\u0002\u00a2\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\n\u0012\u0013\u0012\u0011H\u0004\u00a2\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\f\u0012\u0004\u0012\u0002H\u00030\u00192K\u0010\u0006\u001aG\u0012\u0013\u0012\u0011H\u0002\u00a2\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\n\u0012\u0013\u0012\u0011H\u0003\u00a2\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\u000b\u0012\u0013\u0012\u0011H\u0004\u00a2\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\f\u0012\u0004\u0012\u0002H\u00030\u001aH\u0087\b\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u001e\u001a\u0093\u0001\u0010\u001d\u001a\u0002H\u0010\"\u0004\b\u0000\u0010\u0004\"\u0004\b\u0001\u0010\u0002\"\u0004\b\u0002\u0010\u0003\"\u0016\b\u0003\u0010\u0010*\u0010\u0012\u0006\b\u0000\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0011*\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00020\u00052\u0006\u0010\u0012\u001a\u0002H\u00102\u0006\u0010\u001b\u001a\u0002H\u000326\u0010\u0006\u001a2\u0012\u0013\u0012\u0011H\u0003\u00a2\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\u000b\u0012\u0013\u0012\u0011H\u0004\u00a2\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\f\u0012\u0004\u0012\u0002H\u00030\u0019H\u0087\b\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u001f\u001a\u008b\u0001\u0010 \u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H!0\u0001\"\u0004\b\u0000\u0010!\"\b\b\u0001\u0010\u0004*\u0002H!\"\u0004\b\u0002\u0010\u0002*\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00020\u00052K\u0010\u0006\u001aG\u0012\u0013\u0012\u0011H\u0002\u00a2\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\n\u0012\u0013\u0012\u0011H!\u00a2\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\u000b\u0012\u0013\u0012\u0011H\u0004\u00a2\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\f\u0012\u0004\u0012\u0002H!0\u001aH\u0087\b\u00f8\u0001\u0000\u001a\u00a4\u0001\u0010\"\u001a\u0002H\u0010\"\u0004\b\u0000\u0010!\"\b\b\u0001\u0010\u0004*\u0002H!\"\u0004\b\u0002\u0010\u0002\"\u0016\b\u0003\u0010\u0010*\u0010\u0012\u0006\b\u0000\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H!0\u0011*\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00020\u00052\u0006\u0010\u0012\u001a\u0002H\u00102K\u0010\u0006\u001aG\u0012\u0013\u0012\u0011H\u0002\u00a2\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\n\u0012\u0013\u0012\u0011H!\u00a2\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\u000b\u0012\u0013\u0012\u0011H\u0004\u00a2\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\f\u0012\u0004\u0012\u0002H!0\u001aH\u0087\b\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010#\u0082\u0002\u0007\n\u0005\b\u009920\u0001\u00a8\u0006$"}, d2={"aggregate", "", "K", "R", "T", "Lkotlin/collections/Grouping;", "operation", "Lkotlin/Function4;", "Lkotlin/ParameterName;", "name", "key", "accumulator", "element", "", "first", "aggregateTo", "M", "", "destination", "(Lkotlin/collections/Grouping;Ljava/util/Map;Lkotlin/jvm/functions/Function4;)Ljava/util/Map;", "eachCountTo", "", "(Lkotlin/collections/Grouping;Ljava/util/Map;)Ljava/util/Map;", "fold", "initialValueSelector", "Lkotlin/Function2;", "Lkotlin/Function3;", "initialValue", "(Lkotlin/collections/Grouping;Ljava/lang/Object;Lkotlin/jvm/functions/Function2;)Ljava/util/Map;", "foldTo", "(Lkotlin/collections/Grouping;Ljava/util/Map;Lkotlin/jvm/functions/Function2;Lkotlin/jvm/functions/Function3;)Ljava/util/Map;", "(Lkotlin/collections/Grouping;Ljava/util/Map;Ljava/lang/Object;Lkotlin/jvm/functions/Function2;)Ljava/util/Map;", "reduce", "S", "reduceTo", "(Lkotlin/collections/Grouping;Ljava/util/Map;Lkotlin/jvm/functions/Function3;)Ljava/util/Map;", "kotlin-stdlib"}, xs="kotlin/collections/GroupingKt")
class GroupingKt__GroupingKt
extends GroupingKt__GroupingJVMKt {
    /*
     * WARNING - void declaration
     */
    @SinceKotlin(version="1.1")
    @NotNull
    public static final <T, K, R> Map<K, R> aggregate(@NotNull Grouping<T, ? extends K> $this$aggregate, @NotNull Function4<? super K, ? super R, ? super T, ? super Boolean, ? extends R> operation) {
        void destination$iv;
        void $this$aggregateTo$iv;
        Intrinsics.checkNotNullParameter($this$aggregate, "<this>");
        Intrinsics.checkNotNullParameter(operation, "operation");
        boolean $i$f$aggregate = false;
        Grouping<T, ? extends K> grouping = $this$aggregate;
        Map map2 = new LinkedHashMap();
        boolean $i$f$aggregateTo = false;
        Iterator iterator2 = $this$aggregateTo$iv.sourceIterator();
        while (iterator2.hasNext()) {
            Object e$iv;
            Object accumulator$iv;
            Object key$iv;
            destination$iv.put(key$iv, operation.invoke(key$iv, accumulator$iv, e$iv, (accumulator$iv = destination$iv.get(key$iv = $this$aggregateTo$iv.keyOf(e$iv = iterator2.next()))) == null && !destination$iv.containsKey(key$iv)));
        }
        return destination$iv;
    }

    @SinceKotlin(version="1.1")
    @NotNull
    public static final <T, K, R, M extends Map<? super K, R>> M aggregateTo(@NotNull Grouping<T, ? extends K> $this$aggregateTo, @NotNull M destination, @NotNull Function4<? super K, ? super R, ? super T, ? super Boolean, ? extends R> operation) {
        Intrinsics.checkNotNullParameter($this$aggregateTo, "<this>");
        Intrinsics.checkNotNullParameter(destination, "destination");
        Intrinsics.checkNotNullParameter(operation, "operation");
        boolean $i$f$aggregateTo = false;
        Iterator<T> iterator2 = $this$aggregateTo.sourceIterator();
        while (iterator2.hasNext()) {
            T e;
            R accumulator;
            K key;
            destination.put(key, operation.invoke(key, accumulator, e, (accumulator = destination.get(key = $this$aggregateTo.keyOf(e = iterator2.next()))) == null && !destination.containsKey(key)));
        }
        return destination;
    }

    /*
     * WARNING - void declaration
     */
    @SinceKotlin(version="1.1")
    @NotNull
    public static final <T, K, R> Map<K, R> fold(@NotNull Grouping<T, ? extends K> $this$fold, @NotNull Function2<? super K, ? super T, ? extends R> initialValueSelector, @NotNull Function3<? super K, ? super R, ? super T, ? extends R> operation) {
        void destination$iv$iv;
        void $this$aggregateTo$iv$iv;
        Intrinsics.checkNotNullParameter($this$fold, "<this>");
        Intrinsics.checkNotNullParameter(initialValueSelector, "initialValueSelector");
        Intrinsics.checkNotNullParameter(operation, "operation");
        boolean $i$f$fold = false;
        Grouping<T, ? extends K> $this$aggregate$iv = $this$fold;
        boolean $i$f$aggregate = false;
        Grouping<T, ? extends K> grouping = $this$aggregate$iv;
        Map map2 = new LinkedHashMap();
        boolean $i$f$aggregateTo = false;
        Iterator iterator2 = $this$aggregateTo$iv$iv.sourceIterator();
        while (iterator2.hasNext()) {
            void acc;
            void e;
            void first;
            void key;
            Object e$iv$iv = iterator2.next();
            Object key$iv$iv = $this$aggregateTo$iv$iv.keyOf(e$iv$iv);
            Object accumulator$iv$iv = destination$iv$iv.get(key$iv$iv);
            boolean bl = accumulator$iv$iv == null && !destination$iv$iv.containsKey(key$iv$iv);
            Object t = e$iv$iv;
            Object v = accumulator$iv$iv;
            Object k = key$iv$iv;
            Object k2 = key$iv$iv;
            void var18_18 = destination$iv$iv;
            boolean bl2 = false;
            R r = operation.invoke(key, first != false ? initialValueSelector.invoke(key, e) : acc, e);
            var18_18.put(k2, r);
        }
        return destination$iv$iv;
    }

    /*
     * WARNING - void declaration
     */
    @SinceKotlin(version="1.1")
    @NotNull
    public static final <T, K, R, M extends Map<? super K, R>> M foldTo(@NotNull Grouping<T, ? extends K> $this$foldTo, @NotNull M destination, @NotNull Function2<? super K, ? super T, ? extends R> initialValueSelector, @NotNull Function3<? super K, ? super R, ? super T, ? extends R> operation) {
        Intrinsics.checkNotNullParameter($this$foldTo, "<this>");
        Intrinsics.checkNotNullParameter(destination, "destination");
        Intrinsics.checkNotNullParameter(initialValueSelector, "initialValueSelector");
        Intrinsics.checkNotNullParameter(operation, "operation");
        boolean $i$f$foldTo = false;
        Grouping<T, K> $this$aggregateTo$iv = $this$foldTo;
        boolean $i$f$aggregateTo = false;
        Iterator<T> iterator2 = $this$aggregateTo$iv.sourceIterator();
        while (iterator2.hasNext()) {
            void acc;
            void e;
            void first;
            void key;
            T e$iv = iterator2.next();
            K key$iv = $this$aggregateTo$iv.keyOf(e$iv);
            R accumulator$iv = destination.get(key$iv);
            boolean bl = accumulator$iv == null && !destination.containsKey(key$iv);
            T t = e$iv;
            R r = accumulator$iv;
            K k = key$iv;
            K k2 = key$iv;
            M m = destination;
            boolean bl2 = false;
            R r2 = operation.invoke(key, first != false ? initialValueSelector.invoke(key, e) : acc, e);
            m.put(k2, r2);
        }
        return destination;
    }

    /*
     * WARNING - void declaration
     */
    @SinceKotlin(version="1.1")
    @NotNull
    public static final <T, K, R> Map<K, R> fold(@NotNull Grouping<T, ? extends K> $this$fold, R initialValue, @NotNull Function2<? super R, ? super T, ? extends R> operation) {
        void destination$iv$iv;
        void $this$aggregateTo$iv$iv;
        Intrinsics.checkNotNullParameter($this$fold, "<this>");
        Intrinsics.checkNotNullParameter(operation, "operation");
        boolean $i$f$fold = false;
        Grouping<T, ? extends K> $this$aggregate$iv = $this$fold;
        boolean $i$f$aggregate = false;
        Grouping<T, ? extends K> grouping = $this$aggregate$iv;
        Map map2 = new LinkedHashMap();
        boolean $i$f$aggregateTo = false;
        Iterator iterator2 = $this$aggregateTo$iv$iv.sourceIterator();
        while (iterator2.hasNext()) {
            void e;
            void acc;
            void first;
            Object e$iv$iv = iterator2.next();
            Object key$iv$iv = $this$aggregateTo$iv$iv.keyOf(e$iv$iv);
            Object accumulator$iv$iv = destination$iv$iv.get(key$iv$iv);
            boolean bl = accumulator$iv$iv == null && !destination$iv$iv.containsKey(key$iv$iv);
            Object t = e$iv$iv;
            Object v = accumulator$iv$iv;
            Object k = key$iv$iv;
            void var17_17 = destination$iv$iv;
            boolean bl2 = false;
            R r = operation.invoke(first != false ? initialValue : acc, e);
            var17_17.put(k, r);
        }
        return destination$iv$iv;
    }

    /*
     * WARNING - void declaration
     */
    @SinceKotlin(version="1.1")
    @NotNull
    public static final <T, K, R, M extends Map<? super K, R>> M foldTo(@NotNull Grouping<T, ? extends K> $this$foldTo, @NotNull M destination, R initialValue, @NotNull Function2<? super R, ? super T, ? extends R> operation) {
        Intrinsics.checkNotNullParameter($this$foldTo, "<this>");
        Intrinsics.checkNotNullParameter(destination, "destination");
        Intrinsics.checkNotNullParameter(operation, "operation");
        boolean $i$f$foldTo = false;
        Grouping<T, K> $this$aggregateTo$iv = $this$foldTo;
        boolean $i$f$aggregateTo = false;
        Iterator<T> iterator2 = $this$aggregateTo$iv.sourceIterator();
        while (iterator2.hasNext()) {
            void e;
            void acc;
            void first;
            T e$iv = iterator2.next();
            K key$iv = $this$aggregateTo$iv.keyOf(e$iv);
            R accumulator$iv = destination.get(key$iv);
            boolean bl = accumulator$iv == null && !destination.containsKey(key$iv);
            T t = e$iv;
            R r = accumulator$iv;
            K k = key$iv;
            M m = destination;
            boolean bl2 = false;
            R r2 = operation.invoke(first != false ? initialValue : acc, e);
            m.put(k, r2);
        }
        return destination;
    }

    /*
     * WARNING - void declaration
     */
    @SinceKotlin(version="1.1")
    @NotNull
    public static final <S, T extends S, K> Map<K, S> reduce(@NotNull Grouping<T, ? extends K> $this$reduce, @NotNull Function3<? super K, ? super S, ? super T, ? extends S> operation) {
        void destination$iv$iv;
        void $this$aggregateTo$iv$iv;
        Intrinsics.checkNotNullParameter($this$reduce, "<this>");
        Intrinsics.checkNotNullParameter(operation, "operation");
        boolean $i$f$reduce = false;
        Grouping<T, ? extends K> $this$aggregate$iv = $this$reduce;
        boolean $i$f$aggregate = false;
        Grouping<T, ? extends K> grouping = $this$aggregate$iv;
        Map map2 = new LinkedHashMap();
        boolean $i$f$aggregateTo = false;
        Iterator iterator2 = $this$aggregateTo$iv$iv.sourceIterator();
        while (iterator2.hasNext()) {
            void acc;
            void key;
            void e;
            void first;
            Object e$iv$iv = iterator2.next();
            Object key$iv$iv = $this$aggregateTo$iv$iv.keyOf(e$iv$iv);
            Object accumulator$iv$iv = destination$iv$iv.get(key$iv$iv);
            boolean bl = accumulator$iv$iv == null && !destination$iv$iv.containsKey(key$iv$iv);
            Object t = e$iv$iv;
            Object v = accumulator$iv$iv;
            Object k = key$iv$iv;
            Object k2 = key$iv$iv;
            void var17_17 = destination$iv$iv;
            boolean bl2 = false;
            void var19_19 = first != false ? e : operation.invoke(key, acc, e);
            var17_17.put(k2, var19_19);
        }
        return destination$iv$iv;
    }

    /*
     * WARNING - void declaration
     */
    @SinceKotlin(version="1.1")
    @NotNull
    public static final <S, T extends S, K, M extends Map<? super K, S>> M reduceTo(@NotNull Grouping<T, ? extends K> $this$reduceTo, @NotNull M destination, @NotNull Function3<? super K, ? super S, ? super T, ? extends S> operation) {
        Intrinsics.checkNotNullParameter($this$reduceTo, "<this>");
        Intrinsics.checkNotNullParameter(destination, "destination");
        Intrinsics.checkNotNullParameter(operation, "operation");
        boolean $i$f$reduceTo = false;
        Grouping<T, K> $this$aggregateTo$iv = $this$reduceTo;
        boolean $i$f$aggregateTo = false;
        Iterator<T> iterator2 = $this$aggregateTo$iv.sourceIterator();
        while (iterator2.hasNext()) {
            void acc;
            void key;
            void e;
            void first;
            T e$iv = iterator2.next();
            K key$iv = $this$aggregateTo$iv.keyOf(e$iv);
            S accumulator$iv = destination.get(key$iv);
            boolean bl = accumulator$iv == null && !destination.containsKey(key$iv);
            T t = e$iv;
            S s = accumulator$iv;
            K k = key$iv;
            K k2 = key$iv;
            M m = destination;
            boolean bl2 = false;
            void var17_17 = first != false ? e : operation.invoke(key, acc, e);
            m.put(k2, var17_17);
        }
        return destination;
    }

    /*
     * WARNING - void declaration
     */
    @SinceKotlin(version="1.1")
    @NotNull
    public static final <T, K, M extends Map<? super K, Integer>> M eachCountTo(@NotNull Grouping<T, ? extends K> $this$eachCountTo, @NotNull M destination) {
        void $this$foldTo$iv;
        Intrinsics.checkNotNullParameter($this$eachCountTo, "<this>");
        Intrinsics.checkNotNullParameter(destination, "destination");
        Grouping<T, ? extends K> grouping = $this$eachCountTo;
        Integer initialValue$iv = 0;
        boolean $i$f$foldTo = false;
        void $this$aggregateTo$iv$iv = $this$foldTo$iv;
        boolean $i$f$aggregateTo = false;
        Iterator iterator2 = $this$aggregateTo$iv$iv.sourceIterator();
        while (iterator2.hasNext()) {
            void acc$iv;
            void first$iv;
            Object e$iv$iv = iterator2.next();
            Object key$iv$iv = $this$aggregateTo$iv$iv.keyOf(e$iv$iv);
            Integer accumulator$iv$iv = destination.get(key$iv$iv);
            boolean bl = accumulator$iv$iv == null && !destination.containsKey(key$iv$iv);
            Object t = e$iv$iv;
            Integer n = accumulator$iv$iv;
            Object k = key$iv$iv;
            M m = destination;
            boolean bl2 = false;
            int acc = ((Number)(first$iv != false ? initialValue$iv : acc$iv)).intValue();
            boolean bl3 = false;
            Integer n2 = acc + 1;
            m.put(k, (Integer)n2);
        }
        return destination;
    }
}

