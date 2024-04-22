/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package org.partiql.lang.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.partiql.lang.eval.ExprValue;
import org.partiql.lang.eval.ExprValueExtensionsKt;
import org.partiql.lang.eval.ExprValueType;
import org.partiql.lang.util.CollectionExtensionsKt;
import org.partiql.lang.util.CollectionExtensionsKt$product$;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=2, d1={"\u0000D\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0006\n\u0002\u0010\u001c\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0010(\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0000\u001al\u0010\b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00010\u00020\t\"\u0004\b\u0000\u0010\u0001\"\u0004\b\u0001\u0010\n\"\u0004\b\u0002\u0010\u000b*\b\u0012\u0004\u0012\u0002H\n0\u00022\u0006\u0010\f\u001a\u0002H\u000b2,\b\u0004\u0010\r\u001a&\u0012\u0004\u0012\u0002H\u000b\u0012\u0004\u0012\u0002H\n\u0012\u0016\u0012\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u000b\u0012\u0004\u0012\u0002H\u00010\u00100\u000f0\u000eH\u0086\b\u00a2\u0006\u0002\u0010\u0011\u001a-\u0010\u0012\u001a\u00020\u0013\"\u0004\b\u0000\u0010\u0001*\b\u0012\u0004\u0012\u0002H\u00010\u00022\u0012\u0010\u0014\u001a\u000e\u0012\u0004\u0012\u0002H\u0001\u0012\u0004\u0012\u00020\u00130\u0015H\u0086\b\u001a\u0010\u0010\u0016\u001a\u00020\u0013*\b\u0012\u0004\u0012\u00020\u00170\t\u001a\u0010\u0010\u0018\u001a\u00020\u0013*\b\u0012\u0004\u0012\u00020\u00170\t\u001a\u0010\u0010\u0019\u001a\u00020\u0013*\b\u0012\u0004\u0012\u00020\u00170\t\u001a(\u0010\u001a\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00010\u00020\t\"\u0004\b\u0000\u0010\u0001*\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00010\t0\u0002\u001a&\u0010\u001b\u001a\b\u0012\u0004\u0012\u0002H\u00010\u001c\"\u0004\b\u0000\u0010\u0001*\b\u0012\u0004\u0012\u0002H\u00010\u001c2\u0006\u0010\u001d\u001a\u00020\u001eH\u0000\"$\u0010\u0000\u001a\u0004\u0018\u0001H\u0001\"\u0004\b\u0000\u0010\u0001*\b\u0012\u0004\u0012\u0002H\u00010\u00028\u00c6\u0002\u00a2\u0006\u0006\u001a\u0004\b\u0003\u0010\u0004\"(\u0010\u0005\u001a\b\u0012\u0004\u0012\u0002H\u00010\u0002\"\u0004\b\u0000\u0010\u0001*\b\u0012\u0004\u0012\u0002H\u00010\u00028\u00c6\u0002\u00a2\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007\u00a8\u0006\u001f"}, d2={"head", "T", "", "getHead", "(Ljava/util/List;)Ljava/lang/Object;", "tail", "getTail", "(Ljava/util/List;)Ljava/util/List;", "foldLeftProduct", "", "S", "C", "initialContext", "map", "Lkotlin/Function2;", "", "Lkotlin/Pair;", "(Ljava/util/List;Ljava/lang/Object;Lkotlin/jvm/functions/Function2;)Ljava/lang/Iterable;", "forAll", "", "predicate", "Lkotlin/Function1;", "isAnyMissing", "Lorg/partiql/lang/eval/ExprValue;", "isAnyNull", "isAnyUnknown", "product", "take", "Lkotlin/sequences/Sequence;", "count", "", "lang"})
public final class CollectionExtensionsKt {
    @Nullable
    public static final <T> T getHead(@NotNull List<? extends T> $this$head) {
        int $i$f$getHead = 0;
        Intrinsics.checkParameterIsNotNull($this$head, "$this$head");
        return CollectionsKt.firstOrNull($this$head);
    }

    @NotNull
    public static final <T> List<T> getTail(@NotNull List<? extends T> $this$tail) {
        List<Object> list;
        int $i$f$getTail = 0;
        Intrinsics.checkParameterIsNotNull($this$tail, "$this$tail");
        switch ($this$tail.size()) {
            case 0: 
            case 1: {
                List list2 = Collections.emptyList();
                list = list2;
                Intrinsics.checkExpressionValueIsNotNull(list2, "emptyList()");
                break;
            }
            default: {
                list = $this$tail.subList(1, $this$tail.size());
            }
        }
        return list;
    }

    public static final boolean isAnyUnknown(@NotNull Iterable<? extends ExprValue> $this$isAnyUnknown) {
        boolean bl;
        block3: {
            Intrinsics.checkParameterIsNotNull($this$isAnyUnknown, "$this$isAnyUnknown");
            Iterable<? extends ExprValue> $this$any$iv = $this$isAnyUnknown;
            boolean $i$f$any = false;
            if ($this$any$iv instanceof Collection && ((Collection)$this$any$iv).isEmpty()) {
                bl = false;
            } else {
                Iterator<? extends ExprValue> iterator2 = $this$any$iv.iterator();
                while (iterator2.hasNext()) {
                    ExprValue element$iv;
                    ExprValue it = element$iv = iterator2.next();
                    boolean bl2 = false;
                    if (!ExprValueExtensionsKt.isUnknown(it)) continue;
                    bl = true;
                    break block3;
                }
                bl = false;
            }
        }
        return bl;
    }

    public static final boolean isAnyNull(@NotNull Iterable<? extends ExprValue> $this$isAnyNull) {
        boolean bl;
        block3: {
            Intrinsics.checkParameterIsNotNull($this$isAnyNull, "$this$isAnyNull");
            Iterable<? extends ExprValue> $this$any$iv = $this$isAnyNull;
            boolean $i$f$any = false;
            if ($this$any$iv instanceof Collection && ((Collection)$this$any$iv).isEmpty()) {
                bl = false;
            } else {
                Iterator<? extends ExprValue> iterator2 = $this$any$iv.iterator();
                while (iterator2.hasNext()) {
                    ExprValue element$iv;
                    ExprValue it = element$iv = iterator2.next();
                    boolean bl2 = false;
                    if (!(it.getType() == ExprValueType.NULL)) continue;
                    bl = true;
                    break block3;
                }
                bl = false;
            }
        }
        return bl;
    }

    public static final boolean isAnyMissing(@NotNull Iterable<? extends ExprValue> $this$isAnyMissing) {
        boolean bl;
        block3: {
            Intrinsics.checkParameterIsNotNull($this$isAnyMissing, "$this$isAnyMissing");
            Iterable<? extends ExprValue> $this$any$iv = $this$isAnyMissing;
            boolean $i$f$any = false;
            if ($this$any$iv instanceof Collection && ((Collection)$this$any$iv).isEmpty()) {
                bl = false;
            } else {
                Iterator<? extends ExprValue> iterator2 = $this$any$iv.iterator();
                while (iterator2.hasNext()) {
                    ExprValue element$iv;
                    ExprValue it = element$iv = iterator2.next();
                    boolean bl2 = false;
                    if (!(it.getType() == ExprValueType.MISSING)) continue;
                    bl = true;
                    break block3;
                }
                bl = false;
            }
        }
        return bl;
    }

    @NotNull
    public static final <T> Sequence<T> take(@NotNull Sequence<? extends T> $this$take, long count) {
        Intrinsics.checkParameterIsNotNull($this$take, "$this$take");
        Sequence<? extends T> wrappedSeq = $this$take;
        return new Sequence<T>(wrappedSeq, count){
            private long remaining;
            final /* synthetic */ Sequence $wrappedSeq;
            final /* synthetic */ long $count;

            public final long getRemaining() {
                return this.remaining;
            }

            public final void setRemaining(long l) {
                this.remaining = l;
            }

            @NotNull
            public Iterator<T> iterator() {
                return new Iterator<T>(this){
                    @NotNull
                    private final Iterator<T> wrappedIterator;
                    final /* synthetic */ take.1 this$0;

                    @NotNull
                    public final Iterator<T> getWrappedIterator() {
                        return this.wrappedIterator;
                    }

                    public boolean hasNext() {
                        return this.wrappedIterator.hasNext() && this.this$0.getRemaining() > 0L;
                    }

                    public T next() {
                        T nextValue = this.wrappedIterator.next();
                        take.1 v0 = this.this$0;
                        long l = v0.getRemaining();
                        v0.setRemaining(l + -1L);
                        return nextValue;
                    }
                    {
                        this.this$0 = $outer;
                        this.wrappedIterator = this.this$0.$wrappedSeq.iterator();
                    }

                    public void remove() {
                        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
                    }
                };
            }
            {
                this.$wrappedSeq = $captured_local_variable$0;
                this.$count = $captured_local_variable$1;
                this.remaining = $captured_local_variable$1;
            }
        };
    }

    public static final <T> boolean forAll(@NotNull List<? extends T> $this$forAll, @NotNull Function1<? super T, Boolean> predicate) {
        Object v0;
        block1: {
            int $i$f$forAll = 0;
            Intrinsics.checkParameterIsNotNull($this$forAll, "$this$forAll");
            Intrinsics.checkParameterIsNotNull(predicate, "predicate");
            Iterable iterable = $this$forAll;
            boolean bl = false;
            Iterable iterable2 = iterable;
            boolean bl2 = false;
            Iterator iterator2 = iterable2.iterator();
            while (iterator2.hasNext()) {
                Object t;
                Object x = t = iterator2.next();
                boolean bl3 = false;
                if (!(predicate.invoke(x) == false)) continue;
                v0 = t;
                break block1;
            }
            v0 = null;
        }
        return v0 == null;
    }

    /*
     * WARNING - void declaration
     */
    @NotNull
    public static final <T> Iterable<List<T>> product(@NotNull List<? extends Iterable<? extends T>> $this$product) {
        void $this$foldLeftProduct$iv;
        Intrinsics.checkParameterIsNotNull($this$product, "$this$product");
        List<? extends Iterable<? extends T>> list = $this$product;
        Unit initialContext$iv = Unit.INSTANCE;
        boolean $i$f$foldLeftProduct = false;
        return new Iterable<List<? extends T>>((List)$this$foldLeftProduct$iv, initialContext$iv){
            final /* synthetic */ List $this_foldLeftProduct;
            final /* synthetic */ Object $initialContext;
            {
                this.$this_foldLeftProduct = $receiver;
                this.$initialContext = $captured_local_variable$2;
            }

            /*
             * WARNING - void declaration
             */
            @NotNull
            public Iterator<List<T>> iterator() {
                void ctx;
                void iterable;
                void var3_3;
                void $this$mapTo$iv;
                List sources = this.$this_foldLeftProduct;
                Iterable iterable2 = sources;
                Collection destination$iv = new ArrayList<E>();
                boolean $i$f$mapTo = false;
                Iterator<T> iterator2 = $this$mapTo$iv.iterator();
                while (iterator2.hasNext()) {
                    T item$iv;
                    T t = item$iv = iterator2.next();
                    Collection collection = destination$iv;
                    boolean bl = false;
                    Iterator<T> iterator3 = Collections.emptyList().iterator();
                    collection.add(iterator3);
                }
                List iterators = (List)var3_3;
                Iterable iterable3 = (Iterable)sources.get(0);
                Unit unit = (Unit)this.$initialContext;
                int n = 0;
                List list = iterators;
                boolean bl = false;
                Iterator<R> iterator4 = SequencesKt.map(CollectionsKt.asSequence(iterable), (Function1)new Function1<T, Pair<? extends Unit, ? extends T>>((Unit)ctx){
                    final /* synthetic */ Unit $ctx;
                    {
                        this.$ctx = unit;
                        super(1);
                    }

                    @NotNull
                    public final Pair<Unit, T> invoke(T it) {
                        return new Pair<Unit, T>(this.$ctx, it);
                    }
                }).iterator();
                list.set(n, iterator4);
                return new Iterator<List<? extends T>>(this, iterators, sources){
                    private boolean fetched;
                    private final ArrayList<Pair<Unit, T>> curr;
                    final /* synthetic */ product$$inlined$foldLeftProduct$1 this$0;
                    final /* synthetic */ List $iterators;
                    final /* synthetic */ List $sources;
                    {
                        void var11_11;
                        void destination$iv;
                        void $this$mapTo$iv;
                        this.this$0 = $outer;
                        this.$iterators = $captured_local_variable$1;
                        this.$sources = $captured_local_variable$2;
                        Iterable iterable = $captured_local_variable$2;
                        Collection collection = new ArrayList<E>();
                        product$$inlined$foldLeftProduct$1$1 var6_6 = this;
                        boolean $i$f$mapTo = false;
                        Iterator<T> iterator2 = $this$mapTo$iv.iterator();
                        while (iterator2.hasNext()) {
                            T item$iv;
                            T t = item$iv = iterator2.next();
                            var11_11 = destination$iv;
                            boolean bl = false;
                            E e = null;
                            var11_11.add(e);
                        }
                        var11_11 = destination$iv;
                        var6_6.curr = (ArrayList)var11_11;
                    }

                    /*
                     * WARNING - void declaration
                     */
                    public final boolean fetchIfNeeded() {
                        block0: while (!this.fetched) {
                            Iterator iter;
                            int idx;
                            boolean bl;
                            block7: {
                                Iterable $this$any$iv = this.$iterators;
                                boolean $i$f$any = false;
                                if ($this$any$iv instanceof Collection && ((Collection)$this$any$iv).isEmpty()) {
                                    bl = false;
                                } else {
                                    for (T element$iv : $this$any$iv) {
                                        Iterator it = (Iterator)element$iv;
                                        boolean bl2 = false;
                                        if (!it.hasNext()) continue;
                                        bl = true;
                                        break block7;
                                    }
                                    bl = false;
                                }
                            }
                            if (!bl) break;
                            for (idx = this.$iterators.size() - 1; idx >= 0 && !(iter = (Iterator)this.$iterators.get(idx)).hasNext(); --idx) {
                            }
                            this.curr.set(idx, (Pair<Unit, T>)((Iterator)this.$iterators.get(idx)).next());
                            ++idx;
                            while (idx < this.$iterators.size()) {
                                void iterable;
                                Pair<Unit, T> pair = this.curr.get(idx - 1);
                                if (pair == null) {
                                    Intrinsics.throwNpe();
                                }
                                Unit ctx = pair.getFirst();
                                Iterable iterable2 = (Iterable)this.$sources.get(idx);
                                Unit ctx2 = ctx;
                                boolean bl3 = false;
                                Iterator<R> iter2 = SequencesKt.map(CollectionsKt.asSequence(iterable), (Function1)new Function1<T, Pair<? extends Unit, ? extends T>>(ctx2){
                                    final /* synthetic */ Unit $ctx;
                                    {
                                        this.$ctx = unit;
                                        super(1);
                                    }

                                    @NotNull
                                    public final Pair<Unit, T> invoke(T it) {
                                        return new Pair<Unit, T>(this.$ctx, it);
                                    }
                                }).iterator();
                                this.$iterators.set(idx, iter2);
                                if (!iter2.hasNext()) continue block0;
                                this.curr.set(idx, (Pair<Unit, T>)iter2.next());
                                ++idx;
                            }
                            this.fetched = true;
                        }
                        return this.fetched;
                    }

                    public boolean hasNext() {
                        return this.fetchIfNeeded();
                    }

                    /*
                     * WARNING - void declaration
                     */
                    @NotNull
                    public List<T> next() {
                        void $this$mapTo$iv$iv;
                        if (!this.fetchIfNeeded()) {
                            throw (Throwable)new NoSuchElementException("Exhausted iterator");
                        }
                        this.fetched = false;
                        Iterable $this$map$iv = this.curr;
                        boolean $i$f$map = false;
                        Iterable iterable = $this$map$iv;
                        Collection destination$iv$iv = new ArrayList<E>(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
                        boolean $i$f$mapTo = false;
                        for (T item$iv$iv : $this$mapTo$iv$iv) {
                            void it;
                            Pair pair = (Pair)item$iv$iv;
                            Collection collection = destination$iv$iv;
                            boolean bl = false;
                            void v0 = it;
                            if (v0 == null) {
                                Intrinsics.throwNpe();
                            }
                            B b = v0.getSecond();
                            collection.add(b);
                        }
                        return (List)destination$iv$iv;
                    }

                    public void remove() {
                        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
                    }
                };
            }
        };
    }

    @NotNull
    public static final <T, S, C> Iterable<List<T>> foldLeftProduct(@NotNull List<? extends S> $this$foldLeftProduct, C initialContext, @NotNull Function2<? super C, ? super S, ? extends Iterator<? extends Pair<? extends C, ? extends T>>> map2) {
        int $i$f$foldLeftProduct = 0;
        Intrinsics.checkParameterIsNotNull($this$foldLeftProduct, "$this$foldLeftProduct");
        Intrinsics.checkParameterIsNotNull(map2, "map");
        return new Iterable<List<? extends T>>($this$foldLeftProduct, map2, initialContext){
            final /* synthetic */ List $this_foldLeftProduct;
            final /* synthetic */ Function2 $map;
            final /* synthetic */ Object $initialContext;

            /*
             * WARNING - void declaration
             */
            @NotNull
            public Iterator<List<T>> iterator() {
                void $this$mapTo$iv;
                List sources = this.$this_foldLeftProduct;
                Iterable iterable = sources;
                Collection destination$iv = new ArrayList<E>();
                boolean $i$f$mapTo = false;
                Iterator<T> iterator2 = $this$mapTo$iv.iterator();
                while (iterator2.hasNext()) {
                    T item$iv;
                    T t = item$iv = iterator2.next();
                    Collection collection = destination$iv;
                    boolean bl = false;
                    Iterator<T> iterator3 = Collections.emptyList().iterator();
                    collection.add(iterator3);
                }
                List iterators = (List)destination$iv;
                iterators.set(0, this.$map.invoke(this.$initialContext, sources.get(0)));
                return new Iterator<List<? extends T>>(this, iterators, sources){
                    private boolean fetched;
                    private final ArrayList<Pair<C, T>> curr;
                    final /* synthetic */ foldLeftProduct.1 this$0;
                    final /* synthetic */ List $iterators;
                    final /* synthetic */ List $sources;

                    public final boolean fetchIfNeeded() {
                        block0: while (!this.fetched) {
                            Iterator iter;
                            int idx;
                            boolean bl;
                            block7: {
                                Iterable $this$any$iv = this.$iterators;
                                boolean $i$f$any = false;
                                if ($this$any$iv instanceof Collection && ((Collection)$this$any$iv).isEmpty()) {
                                    bl = false;
                                } else {
                                    for (T element$iv : $this$any$iv) {
                                        Iterator it = (Iterator)element$iv;
                                        boolean bl2 = false;
                                        if (!it.hasNext()) continue;
                                        bl = true;
                                        break block7;
                                    }
                                    bl = false;
                                }
                            }
                            if (!bl) break;
                            for (idx = this.$iterators.size() - 1; idx >= 0 && !(iter = (Iterator)this.$iterators.get(idx)).hasNext(); --idx) {
                            }
                            this.curr.set(idx, (Pair<C, T>)((Iterator)this.$iterators.get(idx)).next());
                            ++idx;
                            while (idx < this.$iterators.size()) {
                                Pair<C, T> pair = this.curr.get(idx - 1);
                                if (pair == null) {
                                    Intrinsics.throwNpe();
                                }
                                C ctx = pair.getFirst();
                                Iterator iter2 = (Iterator)this.this$0.$map.invoke(ctx, this.$sources.get(idx));
                                this.$iterators.set(idx, iter2);
                                if (!iter2.hasNext()) continue block0;
                                this.curr.set(idx, (Pair<C, T>)iter2.next());
                                ++idx;
                            }
                            this.fetched = true;
                        }
                        return this.fetched;
                    }

                    public boolean hasNext() {
                        return this.fetchIfNeeded();
                    }

                    /*
                     * WARNING - void declaration
                     */
                    @NotNull
                    public List<T> next() {
                        void $this$mapTo$iv$iv;
                        if (!this.fetchIfNeeded()) {
                            throw (Throwable)new NoSuchElementException("Exhausted iterator");
                        }
                        this.fetched = false;
                        Iterable $this$map$iv = this.curr;
                        boolean $i$f$map = false;
                        Iterable iterable = $this$map$iv;
                        Collection destination$iv$iv = new ArrayList<E>(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
                        boolean $i$f$mapTo = false;
                        for (T item$iv$iv : $this$mapTo$iv$iv) {
                            void it;
                            Pair pair = (Pair)item$iv$iv;
                            Collection collection = destination$iv$iv;
                            boolean bl = false;
                            void v0 = it;
                            if (v0 == null) {
                                Intrinsics.throwNpe();
                            }
                            B b = v0.getSecond();
                            collection.add(b);
                        }
                        return (List)destination$iv$iv;
                    }
                    {
                        void var12_12;
                        void destination$iv;
                        void $this$mapTo$iv;
                        this.this$0 = $outer;
                        this.$iterators = $captured_local_variable$1;
                        this.$sources = $captured_local_variable$2;
                        Iterable iterable = $captured_local_variable$2;
                        Collection collection = new ArrayList<E>();
                        foldLeftProduct.iterator.1 var11_6 = this;
                        boolean $i$f$mapTo = false;
                        Iterator<T> iterator2 = $this$mapTo$iv.iterator();
                        while (iterator2.hasNext()) {
                            T item$iv;
                            T t = item$iv = iterator2.next();
                            var12_12 = destination$iv;
                            boolean bl = false;
                            E e = null;
                            var12_12.add(e);
                        }
                        var12_12 = destination$iv;
                        var11_6.curr = (ArrayList)var12_12;
                    }

                    public void remove() {
                        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
                    }
                };
            }
            {
                this.$this_foldLeftProduct = $receiver;
                this.$map = $captured_local_variable$1;
                this.$initialContext = $captured_local_variable$2;
            }
        };
    }
}

