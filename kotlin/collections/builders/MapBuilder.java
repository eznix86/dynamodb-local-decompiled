/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.collections.builders;

import java.io.NotSerializableException;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.collections.builders.ListBuilderKt;
import kotlin.collections.builders.MapBuilderEntries;
import kotlin.collections.builders.MapBuilderKeys;
import kotlin.collections.builders.MapBuilderValues;
import kotlin.collections.builders.SerializedMap;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.markers.KMutableIterator;
import kotlin.jvm.internal.markers.KMutableMap;
import kotlin.ranges.RangesKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000\u00a8\u0001\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\b\u0002\n\u0002\u0010\u0015\n\u0002\b\b\n\u0002\u0010#\n\u0002\u0010'\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u001f\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010$\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0010\u001e\n\u0002\b\u0003\n\u0002\u0010&\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0018\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\b\u0000\u0018\u0000 {*\u0004\b\u0000\u0010\u0001*\u0004\b\u0001\u0010\u00022\u000e\u0012\u0004\u0012\u0002H\u0001\u0012\u0004\u0012\u0002H\u00020\u00032\u00060\u0004j\u0002`\u0005:\u0007{|}~\u007f\u0080\u0001B\u0007\b\u0016\u00a2\u0006\u0002\u0010\u0006B\u000f\b\u0016\u0012\u0006\u0010\u0007\u001a\u00020\b\u00a2\u0006\u0002\u0010\tBE\b\u0002\u0012\f\u0010\n\u001a\b\u0012\u0004\u0012\u00028\u00000\u000b\u0012\u000e\u0010\f\u001a\n\u0012\u0004\u0012\u00028\u0001\u0018\u00010\u000b\u0012\u0006\u0010\r\u001a\u00020\u000e\u0012\u0006\u0010\u000f\u001a\u00020\u000e\u0012\u0006\u0010\u0010\u001a\u00020\b\u0012\u0006\u0010\u0011\u001a\u00020\b\u00a2\u0006\u0002\u0010\u0012J\u0017\u00102\u001a\u00020\b2\u0006\u00103\u001a\u00028\u0000H\u0000\u00a2\u0006\u0004\b4\u00105J\u0013\u00106\u001a\b\u0012\u0004\u0012\u00028\u00010\u000bH\u0002\u00a2\u0006\u0002\u00107J\u0012\u00108\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u000109J\r\u0010:\u001a\u00020;H\u0000\u00a2\u0006\u0002\b<J\b\u0010=\u001a\u00020;H\u0016J\b\u0010>\u001a\u00020;H\u0002J\u0019\u0010?\u001a\u00020!2\n\u0010@\u001a\u0006\u0012\u0002\b\u00030AH\u0000\u00a2\u0006\u0002\bBJ!\u0010C\u001a\u00020!2\u0012\u0010D\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010EH\u0000\u00a2\u0006\u0002\bFJ\u0015\u0010G\u001a\u00020!2\u0006\u00103\u001a\u00028\u0000H\u0016\u00a2\u0006\u0002\u0010HJ\u0015\u0010I\u001a\u00020!2\u0006\u0010J\u001a\u00028\u0001H\u0016\u00a2\u0006\u0002\u0010HJ\u0018\u0010K\u001a\u00020!2\u000e\u0010L\u001a\n\u0012\u0002\b\u0003\u0012\u0002\b\u000309H\u0002J\u0010\u0010M\u001a\u00020;2\u0006\u0010\u0013\u001a\u00020\bH\u0002J\u0010\u0010N\u001a\u00020;2\u0006\u0010O\u001a\u00020\bH\u0002J\u0019\u0010P\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010QH\u0000\u00a2\u0006\u0002\bRJ\u0013\u0010S\u001a\u00020!2\b\u0010L\u001a\u0004\u0018\u00010TH\u0096\u0002J\u0015\u0010U\u001a\u00020\b2\u0006\u00103\u001a\u00028\u0000H\u0002\u00a2\u0006\u0002\u00105J\u0015\u0010V\u001a\u00020\b2\u0006\u0010J\u001a\u00028\u0001H\u0002\u00a2\u0006\u0002\u00105J\u0018\u0010W\u001a\u0004\u0018\u00018\u00012\u0006\u00103\u001a\u00028\u0000H\u0096\u0002\u00a2\u0006\u0002\u0010XJ\u0015\u0010Y\u001a\u00020\b2\u0006\u00103\u001a\u00028\u0000H\u0002\u00a2\u0006\u0002\u00105J\b\u0010Z\u001a\u00020\bH\u0016J\b\u0010[\u001a\u00020!H\u0016J\u0019\u0010\\\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010]H\u0000\u00a2\u0006\u0002\b^J\u001f\u0010_\u001a\u0004\u0018\u00018\u00012\u0006\u00103\u001a\u00028\u00002\u0006\u0010J\u001a\u00028\u0001H\u0016\u00a2\u0006\u0002\u0010`J\u001e\u0010a\u001a\u00020;2\u0014\u0010b\u001a\u0010\u0012\u0006\b\u0001\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u000109H\u0016J\"\u0010c\u001a\u00020!2\u0018\u0010b\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010E0AH\u0002J\u001c\u0010d\u001a\u00020!2\u0012\u0010D\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010EH\u0002J\u0010\u0010e\u001a\u00020!2\u0006\u0010f\u001a\u00020\bH\u0002J\u0010\u0010g\u001a\u00020;2\u0006\u0010h\u001a\u00020\bH\u0002J\u0017\u0010i\u001a\u0004\u0018\u00018\u00012\u0006\u00103\u001a\u00028\u0000H\u0016\u00a2\u0006\u0002\u0010XJ!\u0010j\u001a\u00020!2\u0012\u0010D\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010EH\u0000\u00a2\u0006\u0002\bkJ\u0010\u0010l\u001a\u00020;2\u0006\u0010m\u001a\u00020\bH\u0002J\u0017\u0010n\u001a\u00020\b2\u0006\u00103\u001a\u00028\u0000H\u0000\u00a2\u0006\u0004\bo\u00105J\u0010\u0010p\u001a\u00020;2\u0006\u0010q\u001a\u00020\bH\u0002J\u0017\u0010r\u001a\u00020!2\u0006\u0010s\u001a\u00028\u0001H\u0000\u00a2\u0006\u0004\bt\u0010HJ\b\u0010u\u001a\u00020vH\u0016J\u0019\u0010w\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010xH\u0000\u00a2\u0006\u0002\byJ\b\u0010z\u001a\u00020TH\u0002R\u0014\u0010\u0013\u001a\u00020\b8BX\u0082\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0014\u0010\u0015R&\u0010\u0016\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u00180\u00178VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0019\u0010\u001aR\u001c\u0010\u001b\u001a\u0010\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u0001\u0018\u00010\u001cX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u000eX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001d\u001a\u00020\bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u001e\u001a\u00020\b8BX\u0082\u0004\u00a2\u0006\u0006\u001a\u0004\b\u001f\u0010\u0015R\u001e\u0010\"\u001a\u00020!2\u0006\u0010 \u001a\u00020!@BX\u0080\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b#\u0010$R\u001a\u0010%\u001a\b\u0012\u0004\u0012\u00028\u00000\u00178VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b&\u0010\u001aR\u0016\u0010\n\u001a\b\u0012\u0004\u0012\u00028\u00000\u000bX\u0082\u000e\u00a2\u0006\u0004\n\u0002\u0010'R\u0016\u0010(\u001a\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010)X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001e\u0010*\u001a\u00020\b2\u0006\u0010 \u001a\u00020\b@RX\u0096\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b+\u0010\u0015R\u001a\u0010,\u001a\b\u0012\u0004\u0012\u00028\u00010-8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b.\u0010/R\u0018\u0010\f\u001a\n\u0012\u0004\u0012\u00028\u0001\u0018\u00010\u000bX\u0082\u000e\u00a2\u0006\u0004\n\u0002\u0010'R\u0016\u00100\u001a\n\u0012\u0004\u0012\u00028\u0001\u0018\u000101X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0081\u0001"}, d2={"Lkotlin/collections/builders/MapBuilder;", "K", "V", "", "Ljava/io/Serializable;", "Lkotlin/io/Serializable;", "()V", "initialCapacity", "", "(I)V", "keysArray", "", "valuesArray", "presenceArray", "", "hashArray", "maxProbeDistance", "length", "([Ljava/lang/Object;[Ljava/lang/Object;[I[III)V", "capacity", "getCapacity", "()I", "entries", "", "", "getEntries", "()Ljava/util/Set;", "entriesView", "Lkotlin/collections/builders/MapBuilderEntries;", "hashShift", "hashSize", "getHashSize", "<set-?>", "", "isReadOnly", "isReadOnly$kotlin_stdlib", "()Z", "keys", "getKeys", "[Ljava/lang/Object;", "keysView", "Lkotlin/collections/builders/MapBuilderKeys;", "size", "getSize", "values", "", "getValues", "()Ljava/util/Collection;", "valuesView", "Lkotlin/collections/builders/MapBuilderValues;", "addKey", "key", "addKey$kotlin_stdlib", "(Ljava/lang/Object;)I", "allocateValuesArray", "()[Ljava/lang/Object;", "build", "", "checkIsMutable", "", "checkIsMutable$kotlin_stdlib", "clear", "compact", "containsAllEntries", "m", "", "containsAllEntries$kotlin_stdlib", "containsEntry", "entry", "", "containsEntry$kotlin_stdlib", "containsKey", "(Ljava/lang/Object;)Z", "containsValue", "value", "contentEquals", "other", "ensureCapacity", "ensureExtraCapacity", "n", "entriesIterator", "Lkotlin/collections/builders/MapBuilder$EntriesItr;", "entriesIterator$kotlin_stdlib", "equals", "", "findKey", "findValue", "get", "(Ljava/lang/Object;)Ljava/lang/Object;", "hash", "hashCode", "isEmpty", "keysIterator", "Lkotlin/collections/builders/MapBuilder$KeysItr;", "keysIterator$kotlin_stdlib", "put", "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;", "putAll", "from", "putAllEntries", "putEntry", "putRehash", "i", "rehash", "newHashSize", "remove", "removeEntry", "removeEntry$kotlin_stdlib", "removeHashAt", "removedHash", "removeKey", "removeKey$kotlin_stdlib", "removeKeyAt", "index", "removeValue", "element", "removeValue$kotlin_stdlib", "toString", "", "valuesIterator", "Lkotlin/collections/builders/MapBuilder$ValuesItr;", "valuesIterator$kotlin_stdlib", "writeReplace", "Companion", "EntriesItr", "EntryRef", "Itr", "KeysItr", "ValuesItr", "kotlin-stdlib"})
public final class MapBuilder<K, V>
implements Map<K, V>,
Serializable,
KMutableMap {
    @NotNull
    private static final Companion Companion = new Companion(null);
    @NotNull
    private K[] keysArray;
    @Nullable
    private V[] valuesArray;
    @NotNull
    private int[] presenceArray;
    @NotNull
    private int[] hashArray;
    private int maxProbeDistance;
    private int length;
    private int hashShift;
    private int size;
    @Nullable
    private MapBuilderKeys<K> keysView;
    @Nullable
    private MapBuilderValues<V> valuesView;
    @Nullable
    private MapBuilderEntries<K, V> entriesView;
    private boolean isReadOnly;
    @Deprecated
    private static final int MAGIC = -1640531527;
    @Deprecated
    private static final int INITIAL_CAPACITY = 8;
    @Deprecated
    private static final int INITIAL_MAX_PROBE_DISTANCE = 2;
    @Deprecated
    private static final int TOMBSTONE = -1;

    private MapBuilder(K[] keysArray, V[] valuesArray, int[] presenceArray, int[] hashArray, int maxProbeDistance, int length) {
        this.keysArray = keysArray;
        this.valuesArray = valuesArray;
        this.presenceArray = presenceArray;
        this.hashArray = hashArray;
        this.maxProbeDistance = maxProbeDistance;
        this.length = length;
        this.hashShift = MapBuilder.Companion.computeShift(this.getHashSize());
    }

    public int getSize() {
        return this.size;
    }

    public final boolean isReadOnly$kotlin_stdlib() {
        return this.isReadOnly;
    }

    public MapBuilder() {
        this(8);
    }

    public MapBuilder(int initialCapacity) {
        this(ListBuilderKt.arrayOfUninitializedElements(initialCapacity), null, new int[initialCapacity], new int[MapBuilder.Companion.computeHashSize(initialCapacity)], 2, 0);
    }

    @NotNull
    public final Map<K, V> build() {
        this.checkIsMutable$kotlin_stdlib();
        this.isReadOnly = true;
        return this;
    }

    private final Object writeReplace() {
        if (!this.isReadOnly) {
            throw new NotSerializableException("The map cannot be serialized while it is being built.");
        }
        return new SerializedMap(this);
    }

    @Override
    public boolean isEmpty() {
        return this.size() == 0;
    }

    @Override
    public boolean containsKey(Object key) {
        return this.findKey(key) >= 0;
    }

    @Override
    public boolean containsValue(Object value) {
        return this.findValue(value) >= 0;
    }

    @Override
    @Nullable
    public V get(Object key) {
        int index = this.findKey(key);
        if (index < 0) {
            return null;
        }
        Intrinsics.checkNotNull(this.valuesArray);
        return this.valuesArray[index];
    }

    @Override
    @Nullable
    public V put(K key, V value) {
        this.checkIsMutable$kotlin_stdlib();
        int index = this.addKey$kotlin_stdlib(key);
        V[] valuesArray = this.allocateValuesArray();
        if (index < 0) {
            V oldValue = valuesArray[-index - 1];
            valuesArray[-index - 1] = value;
            return oldValue;
        }
        valuesArray[index] = value;
        return null;
    }

    @Override
    public void putAll(@NotNull Map<? extends K, ? extends V> from2) {
        Intrinsics.checkNotNullParameter(from2, "from");
        this.checkIsMutable$kotlin_stdlib();
        this.putAllEntries((Collection)from2.entrySet());
    }

    @Override
    @Nullable
    public V remove(Object key) {
        int index = this.removeKey$kotlin_stdlib(key);
        if (index < 0) {
            return null;
        }
        Intrinsics.checkNotNull(this.valuesArray);
        V[] valuesArray = this.valuesArray;
        V oldValue = valuesArray[index];
        ListBuilderKt.resetAt(valuesArray, index);
        return oldValue;
    }

    @Override
    public void clear() {
        this.checkIsMutable$kotlin_stdlib();
        int i = 0;
        int n = this.length - 1;
        if (i <= n) {
            while (true) {
                int hash;
                if ((hash = this.presenceArray[i]) >= 0) {
                    this.hashArray[hash] = 0;
                    this.presenceArray[i] = -1;
                }
                if (i == n) break;
                ++i;
            }
        }
        ListBuilderKt.resetRange(this.keysArray, 0, this.length);
        if (this.valuesArray != null) {
            ListBuilderKt.resetRange(this.valuesArray, 0, this.length);
        }
        this.size = 0;
        this.length = 0;
    }

    /*
     * WARNING - void declaration
     */
    @NotNull
    public Set<K> getKeys() {
        Set set2;
        MapBuilderKeys<K> cur = this.keysView;
        if (cur == null) {
            void var2_2;
            MapBuilderKeys mapBuilderKeys = new MapBuilderKeys(this);
            this.keysView = mapBuilderKeys;
            set2 = (Set)var2_2;
        } else {
            set2 = cur;
        }
        return set2;
    }

    /*
     * WARNING - void declaration
     */
    @NotNull
    public Collection<V> getValues() {
        Collection collection;
        MapBuilderValues<V> cur = this.valuesView;
        if (cur == null) {
            void var2_2;
            MapBuilderValues mapBuilderValues = new MapBuilderValues(this);
            this.valuesView = mapBuilderValues;
            collection = (Collection)var2_2;
        } else {
            collection = cur;
        }
        return collection;
    }

    @NotNull
    public Set<Map.Entry<K, V>> getEntries() {
        MapBuilderEntries<K, V> cur = this.entriesView;
        if (cur == null) {
            MapBuilderEntries mapBuilderEntries = new MapBuilderEntries(this);
            this.entriesView = mapBuilderEntries;
            return mapBuilderEntries;
        }
        return cur;
    }

    @Override
    public boolean equals(@Nullable Object other) {
        return other == this || other instanceof Map && this.contentEquals((Map)other);
    }

    @Override
    public int hashCode() {
        int result = 0;
        EntriesItr<K, V> it = this.entriesIterator$kotlin_stdlib();
        while (it.hasNext()) {
            result += it.nextHashCode$kotlin_stdlib();
        }
        return result;
    }

    @NotNull
    public String toString() {
        StringBuilder sb = new StringBuilder(2 + this.size() * 3);
        sb.append("{");
        int i = 0;
        EntriesItr<K, V> it = this.entriesIterator$kotlin_stdlib();
        while (it.hasNext()) {
            if (i > 0) {
                sb.append(", ");
            }
            it.nextAppendString(sb);
            ++i;
        }
        sb.append("}");
        String string = sb.toString();
        Intrinsics.checkNotNullExpressionValue(string, "sb.toString()");
        return string;
    }

    private final int getCapacity() {
        return this.keysArray.length;
    }

    private final int getHashSize() {
        return this.hashArray.length;
    }

    public final void checkIsMutable$kotlin_stdlib() {
        if (this.isReadOnly) {
            throw new UnsupportedOperationException();
        }
    }

    private final void ensureExtraCapacity(int n) {
        this.ensureCapacity(this.length + n);
    }

    private final void ensureCapacity(int capacity) {
        if (capacity < 0) {
            throw new OutOfMemoryError();
        }
        if (capacity > this.getCapacity()) {
            int newSize = this.getCapacity() * 3 / 2;
            if (capacity > newSize) {
                newSize = capacity;
            }
            this.keysArray = ListBuilderKt.copyOfUninitializedElements(this.keysArray, newSize);
            this.valuesArray = this.valuesArray != null ? ListBuilderKt.copyOfUninitializedElements(this.valuesArray, newSize) : null;
            int[] nArray = Arrays.copyOf(this.presenceArray, newSize);
            Intrinsics.checkNotNullExpressionValue(nArray, "copyOf(this, newSize)");
            this.presenceArray = nArray;
            int newHashSize = MapBuilder.Companion.computeHashSize(newSize);
            if (newHashSize > this.getHashSize()) {
                this.rehash(newHashSize);
            }
        } else if (this.length + capacity - this.size() > this.getCapacity()) {
            this.rehash(this.getHashSize());
        }
    }

    private final V[] allocateValuesArray() {
        V[] curValuesArray = this.valuesArray;
        if (curValuesArray != null) {
            return curValuesArray;
        }
        E[] newValuesArray = ListBuilderKt.arrayOfUninitializedElements(this.getCapacity());
        this.valuesArray = newValuesArray;
        return newValuesArray;
    }

    private final int hash(K key) {
        K k = key;
        return (k != null ? k.hashCode() : 0) * -1640531527 >>> this.hashShift;
    }

    private final void compact() {
        int j = 0;
        V[] valuesArray = this.valuesArray;
        for (int i = 0; i < this.length; ++i) {
            if (this.presenceArray[i] < 0) continue;
            this.keysArray[j] = this.keysArray[i];
            if (valuesArray != null) {
                valuesArray[j] = valuesArray[i];
            }
            ++j;
        }
        ListBuilderKt.resetRange(this.keysArray, j, this.length);
        if (valuesArray != null) {
            ListBuilderKt.resetRange(valuesArray, j, this.length);
        }
        this.length = j;
    }

    private final void rehash(int newHashSize) {
        if (this.length > this.size()) {
            this.compact();
        }
        if (newHashSize != this.getHashSize()) {
            this.hashArray = new int[newHashSize];
            this.hashShift = MapBuilder.Companion.computeShift(newHashSize);
        } else {
            ArraysKt.fill(this.hashArray, 0, 0, this.getHashSize());
        }
        int i = 0;
        while (i < this.length) {
            if (this.putRehash(i++)) continue;
            throw new IllegalStateException("This cannot happen with fixed magic multiplier and grow-only hash array. Have object hashCodes changed?");
        }
    }

    private final boolean putRehash(int i) {
        int hash = this.hash(this.keysArray[i]);
        int probesLeft = this.maxProbeDistance;
        while (true) {
            int index;
            if ((index = this.hashArray[hash]) == 0) {
                this.hashArray[hash] = i + 1;
                this.presenceArray[i] = hash;
                return true;
            }
            if (--probesLeft < 0) {
                return false;
            }
            if (hash-- != 0) continue;
            hash = this.getHashSize() - 1;
        }
    }

    private final int findKey(K key) {
        int hash = this.hash(key);
        int probesLeft = this.maxProbeDistance;
        int index;
        while ((index = this.hashArray[hash]) != 0) {
            if (index > 0 && Intrinsics.areEqual(this.keysArray[index - 1], key)) {
                return index - 1;
            }
            if (--probesLeft < 0) {
                return -1;
            }
            if (hash-- != 0) continue;
            hash = this.getHashSize() - 1;
        }
        return -1;
    }

    private final int findValue(V value) {
        int i = this.length;
        while (--i >= 0) {
            if (this.presenceArray[i] < 0) continue;
            Intrinsics.checkNotNull(this.valuesArray);
            if (!Intrinsics.areEqual(this.valuesArray[i], value)) continue;
            return i;
        }
        return -1;
    }

    public final int addKey$kotlin_stdlib(K key) {
        this.checkIsMutable$kotlin_stdlib();
        block0: while (true) {
            int hash = this.hash(key);
            int tentativeMaxProbeDistance = RangesKt.coerceAtMost(this.maxProbeDistance * 2, this.getHashSize() / 2);
            int probeDistance = 0;
            while (true) {
                int index;
                if ((index = this.hashArray[hash]) <= 0) {
                    if (this.length >= this.getCapacity()) {
                        this.ensureExtraCapacity(1);
                        continue block0;
                    }
                    int n = this.length;
                    this.length = n + 1;
                    int putIndex = n;
                    this.keysArray[putIndex] = key;
                    this.presenceArray[putIndex] = hash;
                    this.hashArray[hash] = putIndex + 1;
                    n = this.size();
                    this.size = n + 1;
                    if (probeDistance > this.maxProbeDistance) {
                        this.maxProbeDistance = probeDistance;
                    }
                    return putIndex;
                }
                if (Intrinsics.areEqual(this.keysArray[index - 1], key)) {
                    return -index;
                }
                if (++probeDistance > tentativeMaxProbeDistance) {
                    this.rehash(this.getHashSize() * 2);
                    continue block0;
                }
                if (hash-- != 0) continue;
                hash = this.getHashSize() - 1;
            }
            break;
        }
    }

    public final int removeKey$kotlin_stdlib(K key) {
        this.checkIsMutable$kotlin_stdlib();
        int index = this.findKey(key);
        if (index < 0) {
            return -1;
        }
        this.removeKeyAt(index);
        return index;
    }

    private final void removeKeyAt(int index) {
        ListBuilderKt.resetAt(this.keysArray, index);
        this.removeHashAt(this.presenceArray[index]);
        this.presenceArray[index] = -1;
        int n = this.size();
        this.size = n + -1;
    }

    private final void removeHashAt(int removedHash) {
        int hash = removedHash;
        int hole = removedHash;
        int probeDistance = 0;
        int patchAttemptsLeft = RangesKt.coerceAtMost(this.maxProbeDistance * 2, this.getHashSize() / 2);
        do {
            if (hash-- == 0) {
                hash = this.getHashSize() - 1;
            }
            if (++probeDistance > this.maxProbeDistance) {
                this.hashArray[hole] = 0;
                return;
            }
            int index = this.hashArray[hash];
            if (index == 0) {
                this.hashArray[hole] = 0;
                return;
            }
            if (index < 0) {
                this.hashArray[hole] = -1;
                hole = hash;
                probeDistance = 0;
                continue;
            }
            int otherHash = this.hash(this.keysArray[index - 1]);
            if ((otherHash - hash & this.getHashSize() - 1) < probeDistance) continue;
            this.hashArray[hole] = index;
            this.presenceArray[index - 1] = hole;
            hole = hash;
            probeDistance = 0;
        } while (--patchAttemptsLeft >= 0);
        this.hashArray[hole] = -1;
    }

    public final boolean containsEntry$kotlin_stdlib(@NotNull Map.Entry<? extends K, ? extends V> entry) {
        Intrinsics.checkNotNullParameter(entry, "entry");
        int index = this.findKey(entry.getKey());
        if (index < 0) {
            return false;
        }
        Intrinsics.checkNotNull(this.valuesArray);
        return Intrinsics.areEqual(this.valuesArray[index], entry.getValue());
    }

    private final boolean contentEquals(Map<?, ?> other) {
        return this.size() == other.size() && this.containsAllEntries$kotlin_stdlib((Collection)other.entrySet());
    }

    public final boolean containsAllEntries$kotlin_stdlib(@NotNull Collection<?> m) {
        Intrinsics.checkNotNullParameter(m, "m");
        for (Object entry : m) {
            try {
                if (entry != null && this.containsEntry$kotlin_stdlib((Map.Entry)entry)) continue;
                return false;
            } catch (ClassCastException e) {
                return false;
            }
        }
        return true;
    }

    private final boolean putEntry(Map.Entry<? extends K, ? extends V> entry) {
        int index = this.addKey$kotlin_stdlib(entry.getKey());
        V[] valuesArray = this.allocateValuesArray();
        if (index >= 0) {
            valuesArray[index] = entry.getValue();
            return true;
        }
        V oldValue = valuesArray[-index - 1];
        if (!Intrinsics.areEqual(entry.getValue(), oldValue)) {
            valuesArray[-index - 1] = entry.getValue();
            return true;
        }
        return false;
    }

    private final boolean putAllEntries(Collection<? extends Map.Entry<? extends K, ? extends V>> from2) {
        if (from2.isEmpty()) {
            return false;
        }
        this.ensureExtraCapacity(from2.size());
        Iterator<Map.Entry<K, V>> it = from2.iterator();
        boolean updated = false;
        while (it.hasNext()) {
            if (!this.putEntry(it.next())) continue;
            updated = true;
        }
        return updated;
    }

    public final boolean removeEntry$kotlin_stdlib(@NotNull Map.Entry<? extends K, ? extends V> entry) {
        Intrinsics.checkNotNullParameter(entry, "entry");
        this.checkIsMutable$kotlin_stdlib();
        int index = this.findKey(entry.getKey());
        if (index < 0) {
            return false;
        }
        Intrinsics.checkNotNull(this.valuesArray);
        if (!Intrinsics.areEqual(this.valuesArray[index], entry.getValue())) {
            return false;
        }
        this.removeKeyAt(index);
        return true;
    }

    public final boolean removeValue$kotlin_stdlib(V element) {
        this.checkIsMutable$kotlin_stdlib();
        int index = this.findValue(element);
        if (index < 0) {
            return false;
        }
        this.removeKeyAt(index);
        return true;
    }

    @NotNull
    public final KeysItr<K, V> keysIterator$kotlin_stdlib() {
        return new KeysItr(this);
    }

    @NotNull
    public final ValuesItr<K, V> valuesIterator$kotlin_stdlib() {
        return new ValuesItr(this);
    }

    @NotNull
    public final EntriesItr<K, V> entriesIterator$kotlin_stdlib() {
        return new EntriesItr(this);
    }

    @Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\b\b\u0082\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\b\u001a\u00020\u00042\u0006\u0010\t\u001a\u00020\u0004H\u0002J\u0010\u0010\n\u001a\u00020\u00042\u0006\u0010\u000b\u001a\u00020\u0004H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\f"}, d2={"Lkotlin/collections/builders/MapBuilder$Companion;", "", "()V", "INITIAL_CAPACITY", "", "INITIAL_MAX_PROBE_DISTANCE", "MAGIC", "TOMBSTONE", "computeHashSize", "capacity", "computeShift", "hashSize", "kotlin-stdlib"})
    private static final class Companion {
        private Companion() {
        }

        private final int computeHashSize(int capacity) {
            return Integer.highestOneBit(RangesKt.coerceAtLeast(capacity, 1) * 3);
        }

        private final int computeShift(int hashSize) {
            return Integer.numberOfLeadingZeros(hashSize) + 1;
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }

    @Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000,\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\n\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\b\u0010\u0018\u0000*\u0004\b\u0002\u0010\u0001*\u0004\b\u0003\u0010\u00022\u00020\u0003B\u0019\u0012\u0012\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00028\u0002\u0012\u0004\u0012\u00028\u00030\u0005\u00a2\u0006\u0002\u0010\u0006J\u0006\u0010\u0012\u001a\u00020\u0013J\r\u0010\u0014\u001a\u00020\u0015H\u0000\u00a2\u0006\u0002\b\u0016J\u0006\u0010\u0017\u001a\u00020\u0015R\u001a\u0010\u0007\u001a\u00020\bX\u0080\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\t\u0010\n\"\u0004\b\u000b\u0010\fR\u001a\u0010\r\u001a\u00020\bX\u0080\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\n\"\u0004\b\u000f\u0010\fR \u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00028\u0002\u0012\u0004\u0012\u00028\u00030\u0005X\u0080\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011\u00a8\u0006\u0018"}, d2={"Lkotlin/collections/builders/MapBuilder$Itr;", "K", "V", "", "map", "Lkotlin/collections/builders/MapBuilder;", "(Lkotlin/collections/builders/MapBuilder;)V", "index", "", "getIndex$kotlin_stdlib", "()I", "setIndex$kotlin_stdlib", "(I)V", "lastIndex", "getLastIndex$kotlin_stdlib", "setLastIndex$kotlin_stdlib", "getMap$kotlin_stdlib", "()Lkotlin/collections/builders/MapBuilder;", "hasNext", "", "initNext", "", "initNext$kotlin_stdlib", "remove", "kotlin-stdlib"})
    public static class Itr<K, V> {
        @NotNull
        private final MapBuilder<K, V> map;
        private int index;
        private int lastIndex;

        public Itr(@NotNull MapBuilder<K, V> map2) {
            Intrinsics.checkNotNullParameter(map2, "map");
            this.map = map2;
            this.lastIndex = -1;
            this.initNext$kotlin_stdlib();
        }

        @NotNull
        public final MapBuilder<K, V> getMap$kotlin_stdlib() {
            return this.map;
        }

        public final int getIndex$kotlin_stdlib() {
            return this.index;
        }

        public final void setIndex$kotlin_stdlib(int n) {
            this.index = n;
        }

        public final int getLastIndex$kotlin_stdlib() {
            return this.lastIndex;
        }

        public final void setLastIndex$kotlin_stdlib(int n) {
            this.lastIndex = n;
        }

        public final void initNext$kotlin_stdlib() {
            while (this.index < ((MapBuilder)this.map).length && ((MapBuilder)this.map).presenceArray[this.index] < 0) {
                int n = this.index;
                this.index = n + 1;
            }
        }

        public final boolean hasNext() {
            return this.index < ((MapBuilder)this.map).length;
        }

        public final void remove() {
            boolean bl;
            boolean bl2 = bl = this.lastIndex != -1;
            if (!bl) {
                boolean bl3 = false;
                String string = "Call next() before removing element from the iterator.";
                throw new IllegalStateException(string.toString());
            }
            this.map.checkIsMutable$kotlin_stdlib();
            ((MapBuilder)this.map).removeKeyAt(this.lastIndex);
            this.lastIndex = -1;
        }
    }

    @Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010)\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0000\u0018\u0000*\u0004\b\u0002\u0010\u0001*\u0004\b\u0003\u0010\u00022\u000e\u0012\u0004\u0012\u0002H\u0001\u0012\u0004\u0012\u0002H\u00020\u00032\b\u0012\u0004\u0012\u0002H\u00010\u0004B\u0019\u0012\u0012\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00028\u0002\u0012\u0004\u0012\u00028\u00030\u0006\u00a2\u0006\u0002\u0010\u0007J\u000e\u0010\b\u001a\u00028\u0002H\u0096\u0002\u00a2\u0006\u0002\u0010\t\u00a8\u0006\n"}, d2={"Lkotlin/collections/builders/MapBuilder$KeysItr;", "K", "V", "Lkotlin/collections/builders/MapBuilder$Itr;", "", "map", "Lkotlin/collections/builders/MapBuilder;", "(Lkotlin/collections/builders/MapBuilder;)V", "next", "()Ljava/lang/Object;", "kotlin-stdlib"})
    public static final class KeysItr<K, V>
    extends Itr<K, V>
    implements Iterator<K>,
    KMutableIterator {
        public KeysItr(@NotNull MapBuilder<K, V> map2) {
            Intrinsics.checkNotNullParameter(map2, "map");
            super(map2);
        }

        @Override
        public K next() {
            if (this.getIndex$kotlin_stdlib() >= this.getMap$kotlin_stdlib().length) {
                throw new NoSuchElementException();
            }
            int n = this.getIndex$kotlin_stdlib();
            this.setIndex$kotlin_stdlib(n + 1);
            this.setLastIndex$kotlin_stdlib(n);
            Object result = this.getMap$kotlin_stdlib().keysArray[this.getLastIndex$kotlin_stdlib()];
            this.initNext$kotlin_stdlib();
            return (K)result;
        }
    }

    @Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010)\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0000\u0018\u0000*\u0004\b\u0002\u0010\u0001*\u0004\b\u0003\u0010\u00022\u000e\u0012\u0004\u0012\u0002H\u0001\u0012\u0004\u0012\u0002H\u00020\u00032\b\u0012\u0004\u0012\u0002H\u00020\u0004B\u0019\u0012\u0012\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00028\u0002\u0012\u0004\u0012\u00028\u00030\u0006\u00a2\u0006\u0002\u0010\u0007J\u000e\u0010\b\u001a\u00028\u0003H\u0096\u0002\u00a2\u0006\u0002\u0010\t\u00a8\u0006\n"}, d2={"Lkotlin/collections/builders/MapBuilder$ValuesItr;", "K", "V", "Lkotlin/collections/builders/MapBuilder$Itr;", "", "map", "Lkotlin/collections/builders/MapBuilder;", "(Lkotlin/collections/builders/MapBuilder;)V", "next", "()Ljava/lang/Object;", "kotlin-stdlib"})
    public static final class ValuesItr<K, V>
    extends Itr<K, V>
    implements Iterator<V>,
    KMutableIterator {
        public ValuesItr(@NotNull MapBuilder<K, V> map2) {
            Intrinsics.checkNotNullParameter(map2, "map");
            super(map2);
        }

        @Override
        public V next() {
            if (this.getIndex$kotlin_stdlib() >= this.getMap$kotlin_stdlib().length) {
                throw new NoSuchElementException();
            }
            int n = this.getIndex$kotlin_stdlib();
            this.setIndex$kotlin_stdlib(n + 1);
            this.setLastIndex$kotlin_stdlib(n);
            Object[] objectArray = this.getMap$kotlin_stdlib().valuesArray;
            Intrinsics.checkNotNull(objectArray);
            Object result = objectArray[this.getLastIndex$kotlin_stdlib()];
            this.initNext$kotlin_stdlib();
            return (V)result;
        }
    }

    @Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000<\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010)\n\u0002\u0010'\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\b\u0000\u0018\u0000*\u0004\b\u0002\u0010\u0001*\u0004\b\u0003\u0010\u00022\u000e\u0012\u0004\u0012\u0002H\u0001\u0012\u0004\u0012\u0002H\u00020\u00032\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0001\u0012\u0004\u0012\u0002H\u00020\u00050\u0004B\u0019\u0012\u0012\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u00028\u0002\u0012\u0004\u0012\u00028\u00030\u0007\u00a2\u0006\u0002\u0010\bJ\u0015\u0010\t\u001a\u000e\u0012\u0004\u0012\u00028\u0002\u0012\u0004\u0012\u00028\u00030\nH\u0096\u0002J\u0012\u0010\u000b\u001a\u00020\f2\n\u0010\r\u001a\u00060\u000ej\u0002`\u000fJ\r\u0010\u0010\u001a\u00020\u0011H\u0000\u00a2\u0006\u0002\b\u0012\u00a8\u0006\u0013"}, d2={"Lkotlin/collections/builders/MapBuilder$EntriesItr;", "K", "V", "Lkotlin/collections/builders/MapBuilder$Itr;", "", "", "map", "Lkotlin/collections/builders/MapBuilder;", "(Lkotlin/collections/builders/MapBuilder;)V", "next", "Lkotlin/collections/builders/MapBuilder$EntryRef;", "nextAppendString", "", "sb", "Ljava/lang/StringBuilder;", "Lkotlin/text/StringBuilder;", "nextHashCode", "", "nextHashCode$kotlin_stdlib", "kotlin-stdlib"})
    public static final class EntriesItr<K, V>
    extends Itr<K, V>
    implements Iterator<Map.Entry<K, V>>,
    KMutableIterator {
        public EntriesItr(@NotNull MapBuilder<K, V> map2) {
            Intrinsics.checkNotNullParameter(map2, "map");
            super(map2);
        }

        @Override
        @NotNull
        public EntryRef<K, V> next() {
            if (this.getIndex$kotlin_stdlib() >= this.getMap$kotlin_stdlib().length) {
                throw new NoSuchElementException();
            }
            int n = this.getIndex$kotlin_stdlib();
            this.setIndex$kotlin_stdlib(n + 1);
            this.setLastIndex$kotlin_stdlib(n);
            EntryRef result = new EntryRef(this.getMap$kotlin_stdlib(), this.getLastIndex$kotlin_stdlib());
            this.initNext$kotlin_stdlib();
            return result;
        }

        public final int nextHashCode$kotlin_stdlib() {
            if (this.getIndex$kotlin_stdlib() >= this.getMap$kotlin_stdlib().length) {
                throw new NoSuchElementException();
            }
            int n = this.getIndex$kotlin_stdlib();
            this.setIndex$kotlin_stdlib(n + 1);
            this.setLastIndex$kotlin_stdlib(n);
            Object object = this.getMap$kotlin_stdlib().keysArray[this.getLastIndex$kotlin_stdlib()];
            int n2 = object != null ? object.hashCode() : 0;
            Object[] objectArray = this.getMap$kotlin_stdlib().valuesArray;
            Intrinsics.checkNotNull(objectArray);
            Object object2 = objectArray[this.getLastIndex$kotlin_stdlib()];
            int result = n2 ^ (object2 != null ? object2.hashCode() : 0);
            this.initNext$kotlin_stdlib();
            return result;
        }

        public final void nextAppendString(@NotNull StringBuilder sb) {
            Intrinsics.checkNotNullParameter(sb, "sb");
            if (this.getIndex$kotlin_stdlib() >= this.getMap$kotlin_stdlib().length) {
                throw new NoSuchElementException();
            }
            int n = this.getIndex$kotlin_stdlib();
            this.setIndex$kotlin_stdlib(n + 1);
            this.setLastIndex$kotlin_stdlib(n);
            Object key = this.getMap$kotlin_stdlib().keysArray[this.getLastIndex$kotlin_stdlib()];
            if (Intrinsics.areEqual(key, this.getMap$kotlin_stdlib())) {
                sb.append("(this Map)");
            } else {
                sb.append(key);
            }
            sb.append('=');
            Object[] objectArray = this.getMap$kotlin_stdlib().valuesArray;
            Intrinsics.checkNotNull(objectArray);
            Object value = objectArray[this.getLastIndex$kotlin_stdlib()];
            if (Intrinsics.areEqual(value, this.getMap$kotlin_stdlib())) {
                sb.append("(this Map)");
            } else {
                sb.append(value);
            }
            this.initNext$kotlin_stdlib();
        }
    }

    @Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u00000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010'\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0000\b\u0000\u0018\u0000*\u0004\b\u0002\u0010\u0001*\u0004\b\u0003\u0010\u00022\u000e\u0012\u0004\u0012\u0002H\u0001\u0012\u0004\u0012\u0002H\u00020\u0003B!\u0012\u0012\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00028\u0002\u0012\u0004\u0012\u00028\u00030\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\bJ\u0013\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0011H\u0096\u0002J\b\u0010\u0012\u001a\u00020\u0007H\u0016J\u0015\u0010\u0013\u001a\u00028\u00032\u0006\u0010\u0014\u001a\u00028\u0003H\u0016\u00a2\u0006\u0002\u0010\u0015J\b\u0010\u0016\u001a\u00020\u0017H\u0016R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\t\u001a\u00028\u00028VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\n\u0010\u000bR\u001a\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00028\u0002\u0012\u0004\u0012\u00028\u00030\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\f\u001a\u00028\u00038VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\r\u0010\u000b\u00a8\u0006\u0018"}, d2={"Lkotlin/collections/builders/MapBuilder$EntryRef;", "K", "V", "", "map", "Lkotlin/collections/builders/MapBuilder;", "index", "", "(Lkotlin/collections/builders/MapBuilder;I)V", "key", "getKey", "()Ljava/lang/Object;", "value", "getValue", "equals", "", "other", "", "hashCode", "setValue", "newValue", "(Ljava/lang/Object;)Ljava/lang/Object;", "toString", "", "kotlin-stdlib"})
    public static final class EntryRef<K, V>
    implements Map.Entry<K, V>,
    KMutableMap.Entry {
        @NotNull
        private final MapBuilder<K, V> map;
        private final int index;

        public EntryRef(@NotNull MapBuilder<K, V> map2, int index) {
            Intrinsics.checkNotNullParameter(map2, "map");
            this.map = map2;
            this.index = index;
        }

        @Override
        public K getKey() {
            return (K)((MapBuilder)this.map).keysArray[this.index];
        }

        @Override
        public V getValue() {
            Object[] objectArray = ((MapBuilder)this.map).valuesArray;
            Intrinsics.checkNotNull(objectArray);
            return (V)objectArray[this.index];
        }

        @Override
        public V setValue(V newValue) {
            this.map.checkIsMutable$kotlin_stdlib();
            Object[] valuesArray = ((MapBuilder)this.map).allocateValuesArray();
            Object oldValue = valuesArray[this.index];
            valuesArray[this.index] = newValue;
            return (V)oldValue;
        }

        @Override
        public boolean equals(@Nullable Object other) {
            return other instanceof Map.Entry && Intrinsics.areEqual(((Map.Entry)other).getKey(), this.getKey()) && Intrinsics.areEqual(((Map.Entry)other).getValue(), this.getValue());
        }

        @Override
        public int hashCode() {
            K k = this.getKey();
            V v = this.getValue();
            return (k != null ? k.hashCode() : 0) ^ (v != null ? v.hashCode() : 0);
        }

        @NotNull
        public String toString() {
            return "" + this.getKey() + '=' + this.getValue();
        }
    }
}

