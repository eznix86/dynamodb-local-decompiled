/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.collections;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.SinceKotlin;
import kotlin.collections.AbstractCollection;
import kotlin.collections.AbstractSet;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.markers.KMappedMarker;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000D\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\b\u0002\n\u0002\u0010\"\n\u0000\n\u0002\u0010\u001e\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010&\n\u0002\b\b\n\u0002\u0010\u0000\n\u0002\b\u0007\n\u0002\u0010\u000e\n\u0002\b\u0003\b'\u0018\u0000 )*\u0004\b\u0000\u0010\u0001*\u0006\b\u0001\u0010\u0002 \u00012\u000e\u0012\u0004\u0012\u0002H\u0001\u0012\u0004\u0012\u0002H\u00020\u0003:\u0001)B\u0007\b\u0004\u00a2\u0006\u0002\u0010\u0004J\u001f\u0010\u0013\u001a\u00020\u00142\u0010\u0010\u0015\u001a\f\u0012\u0002\b\u0003\u0012\u0002\b\u0003\u0018\u00010\u0016H\u0000\u00a2\u0006\u0002\b\u0017J\u0015\u0010\u0018\u001a\u00020\u00142\u0006\u0010\u0019\u001a\u00028\u0000H\u0016\u00a2\u0006\u0002\u0010\u001aJ\u0015\u0010\u001b\u001a\u00020\u00142\u0006\u0010\u001c\u001a\u00028\u0001H\u0016\u00a2\u0006\u0002\u0010\u001aJ\u0013\u0010\u001d\u001a\u00020\u00142\b\u0010\u001e\u001a\u0004\u0018\u00010\u001fH\u0096\u0002J\u0018\u0010 \u001a\u0004\u0018\u00018\u00012\u0006\u0010\u0019\u001a\u00028\u0000H\u0096\u0002\u00a2\u0006\u0002\u0010!J\b\u0010\"\u001a\u00020\rH\u0016J#\u0010#\u001a\u0010\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u0001\u0018\u00010\u00162\u0006\u0010\u0019\u001a\u00028\u0000H\u0002\u00a2\u0006\u0002\u0010$J\b\u0010%\u001a\u00020\u0014H\u0016J\b\u0010&\u001a\u00020'H\u0016J\u0012\u0010&\u001a\u00020'2\b\u0010(\u001a\u0004\u0018\u00010\u001fH\u0002J\u001c\u0010&\u001a\u00020'2\u0012\u0010\u0015\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u0016H\bR\u0016\u0010\u0005\u001a\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0016\u0010\u0007\u001a\n\u0012\u0004\u0012\u00028\u0001\u0018\u00010\bX\u0088\u000e\u00a2\u0006\u0002\n\u0000R\u001a\u0010\t\u001a\b\u0012\u0004\u0012\u00028\u00000\u00068VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\n\u0010\u000bR\u0014\u0010\f\u001a\u00020\r8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u000e\u0010\u000fR\u001a\u0010\u0010\u001a\b\u0012\u0004\u0012\u00028\u00010\b8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0011\u0010\u0012\u00a8\u0006*"}, d2={"Lkotlin/collections/AbstractMap;", "K", "V", "", "()V", "_keys", "", "_values", "", "keys", "getKeys", "()Ljava/util/Set;", "size", "", "getSize", "()I", "values", "getValues", "()Ljava/util/Collection;", "containsEntry", "", "entry", "", "containsEntry$kotlin_stdlib", "containsKey", "key", "(Ljava/lang/Object;)Z", "containsValue", "value", "equals", "other", "", "get", "(Ljava/lang/Object;)Ljava/lang/Object;", "hashCode", "implFindEntry", "(Ljava/lang/Object;)Ljava/util/Map$Entry;", "isEmpty", "toString", "", "o", "Companion", "kotlin-stdlib"})
@SinceKotlin(version="1.1")
public abstract class AbstractMap<K, V>
implements Map<K, V>,
KMappedMarker {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @Nullable
    private volatile Set<? extends K> _keys;
    @Nullable
    private volatile Collection<? extends V> _values;

    protected AbstractMap() {
    }

    @Override
    public boolean containsKey(Object key) {
        return this.implFindEntry(key) != null;
    }

    @Override
    public boolean containsValue(Object value) {
        boolean bl;
        block3: {
            Iterable $this$any$iv = this.entrySet();
            boolean $i$f$any = false;
            if ($this$any$iv instanceof Collection && ((Collection)$this$any$iv).isEmpty()) {
                bl = false;
            } else {
                for (Object element$iv : $this$any$iv) {
                    Map.Entry it = (Map.Entry)element$iv;
                    boolean bl2 = false;
                    if (!Intrinsics.areEqual(it.getValue(), value)) continue;
                    bl = true;
                    break block3;
                }
                bl = false;
            }
        }
        return bl;
    }

    public final boolean containsEntry$kotlin_stdlib(@Nullable Map.Entry<?, ?> entry) {
        Object ourValue;
        if (entry == null) {
            return false;
        }
        Object key = entry.getKey();
        Object value = entry.getValue();
        if (!Intrinsics.areEqual(value, ourValue = ((Map)this).get(key))) {
            return false;
        }
        return ourValue != null || ((Map)this).containsKey(key);
    }

    @Override
    public boolean equals(@Nullable Object other) {
        boolean bl;
        block6: {
            if (other == this) {
                return true;
            }
            if (!(other instanceof Map)) {
                return false;
            }
            if (this.size() != ((Map)other).size()) {
                return false;
            }
            Iterable $this$all$iv = ((Map)other).entrySet();
            boolean $i$f$all = false;
            if ($this$all$iv instanceof Collection && ((Collection)$this$all$iv).isEmpty()) {
                bl = true;
            } else {
                for (Object element$iv : $this$all$iv) {
                    Map.Entry it = (Map.Entry)element$iv;
                    boolean bl2 = false;
                    if (this.containsEntry$kotlin_stdlib(it)) continue;
                    bl = false;
                    break block6;
                }
                bl = true;
            }
        }
        return bl;
    }

    @Override
    @Nullable
    public V get(Object key) {
        Map.Entry<Object, V> entry = this.implFindEntry(key);
        return (V)(entry != null ? entry.getValue() : null);
    }

    @Override
    public int hashCode() {
        return ((Object)this.entrySet()).hashCode();
    }

    @Override
    public boolean isEmpty() {
        return this.size() == 0;
    }

    public int getSize() {
        return this.entrySet().size();
    }

    @NotNull
    public Set<K> getKeys() {
        if (this._keys == null) {
            this._keys = new AbstractSet<K>(this){
                final /* synthetic */ AbstractMap<K, V> this$0;
                {
                    this.this$0 = $receiver;
                }

                public boolean contains(Object element) {
                    return this.this$0.containsKey(element);
                }

                @NotNull
                public Iterator<K> iterator() {
                    Iterator<Map.Entry<K, V>> entryIterator = this.this$0.entrySet().iterator();
                    return new Iterator<K>(entryIterator){
                        final /* synthetic */ Iterator<Map.Entry<K, V>> $entryIterator;
                        {
                            this.$entryIterator = $entryIterator;
                        }

                        public boolean hasNext() {
                            return this.$entryIterator.hasNext();
                        }

                        public K next() {
                            return this.$entryIterator.next().getKey();
                        }

                        public void remove() {
                            throw new UnsupportedOperationException("Operation is not supported for read-only collection");
                        }
                    };
                }

                public int getSize() {
                    return this.this$0.size();
                }
            };
        }
        Set<? extends K> set2 = this._keys;
        Intrinsics.checkNotNull(set2);
        return set2;
    }

    @NotNull
    public String toString() {
        return CollectionsKt.joinToString$default(this.entrySet(), ", ", "{", "}", 0, null, new Function1<Map.Entry<? extends K, ? extends V>, CharSequence>(this){
            final /* synthetic */ AbstractMap<K, V> this$0;
            {
                this.this$0 = $receiver;
                super(1);
            }

            @NotNull
            public final CharSequence invoke(@NotNull Map.Entry<? extends K, ? extends V> it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return AbstractMap.access$toString(this.this$0, it);
            }
        }, 24, null);
    }

    private final String toString(Map.Entry<? extends K, ? extends V> entry) {
        return this.toString(entry.getKey()) + '=' + this.toString(entry.getValue());
    }

    private final String toString(Object o2) {
        return o2 == this ? "(this Map)" : String.valueOf(o2);
    }

    @NotNull
    public Collection<V> getValues() {
        if (this._values == null) {
            this._values = new AbstractCollection<V>(this){
                final /* synthetic */ AbstractMap<K, V> this$0;
                {
                    this.this$0 = $receiver;
                }

                public boolean contains(Object element) {
                    return this.this$0.containsValue(element);
                }

                @NotNull
                public Iterator<V> iterator() {
                    Iterator<Map.Entry<K, V>> entryIterator = this.this$0.entrySet().iterator();
                    return new Iterator<V>(entryIterator){
                        final /* synthetic */ Iterator<Map.Entry<K, V>> $entryIterator;
                        {
                            this.$entryIterator = $entryIterator;
                        }

                        public boolean hasNext() {
                            return this.$entryIterator.hasNext();
                        }

                        public V next() {
                            return this.$entryIterator.next().getValue();
                        }

                        public void remove() {
                            throw new UnsupportedOperationException("Operation is not supported for read-only collection");
                        }
                    };
                }

                public int getSize() {
                    return this.this$0.size();
                }
            };
        }
        Collection<? extends V> collection = this._values;
        Intrinsics.checkNotNull(collection);
        return collection;
    }

    private final Map.Entry<K, V> implFindEntry(K key) {
        Object v0;
        block1: {
            Iterable $this$firstOrNull$iv = this.entrySet();
            boolean $i$f$firstOrNull = false;
            for (Object element$iv : $this$firstOrNull$iv) {
                Map.Entry it = (Map.Entry)element$iv;
                boolean bl = false;
                if (!Intrinsics.areEqual(it.getKey(), key)) continue;
                v0 = element$iv;
                break block1;
            }
            v0 = null;
        }
        return v0;
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override
    public V put(K key, V value) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> from2) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override
    public V remove(Object key) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    public abstract Set getEntries();

    public static final /* synthetic */ String access$toString(AbstractMap $this, Map.Entry entry) {
        return $this.toString(entry);
    }

    @Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010&\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0080\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J'\u0010\u0003\u001a\u00020\u00042\u000e\u0010\u0005\u001a\n\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u00062\b\u0010\u0007\u001a\u0004\u0018\u00010\u0001H\u0000\u00a2\u0006\u0002\b\bJ\u001d\u0010\t\u001a\u00020\n2\u000e\u0010\u0005\u001a\n\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u0006H\u0000\u00a2\u0006\u0002\b\u000bJ\u001d\u0010\f\u001a\u00020\r2\u000e\u0010\u0005\u001a\n\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u0006H\u0000\u00a2\u0006\u0002\b\u000e\u00a8\u0006\u000f"}, d2={"Lkotlin/collections/AbstractMap$Companion;", "", "()V", "entryEquals", "", "e", "", "other", "entryEquals$kotlin_stdlib", "entryHashCode", "", "entryHashCode$kotlin_stdlib", "entryToString", "", "entryToString$kotlin_stdlib", "kotlin-stdlib"})
    public static final class Companion {
        private Companion() {
        }

        public final int entryHashCode$kotlin_stdlib(@NotNull Map.Entry<?, ?> e) {
            Intrinsics.checkNotNullParameter(e, "e");
            Map.Entry<?, ?> $this$entryHashCode_u24lambda_u2d0 = e;
            boolean bl = false;
            Object obj = $this$entryHashCode_u24lambda_u2d0.getKey();
            Object obj2 = $this$entryHashCode_u24lambda_u2d0.getValue();
            return (obj != null ? obj.hashCode() : 0) ^ (obj2 != null ? obj2.hashCode() : 0);
        }

        @NotNull
        public final String entryToString$kotlin_stdlib(@NotNull Map.Entry<?, ?> e) {
            Intrinsics.checkNotNullParameter(e, "e");
            Map.Entry<?, ?> $this$entryToString_u24lambda_u2d1 = e;
            boolean bl = false;
            return "" + $this$entryToString_u24lambda_u2d1.getKey() + '=' + $this$entryToString_u24lambda_u2d1.getValue();
        }

        public final boolean entryEquals$kotlin_stdlib(@NotNull Map.Entry<?, ?> e, @Nullable Object other) {
            Intrinsics.checkNotNullParameter(e, "e");
            if (!(other instanceof Map.Entry)) {
                return false;
            }
            return Intrinsics.areEqual(e.getKey(), ((Map.Entry)other).getKey()) && Intrinsics.areEqual(e.getValue(), ((Map.Entry)other).getValue());
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

