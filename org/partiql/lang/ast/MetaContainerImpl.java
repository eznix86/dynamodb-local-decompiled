/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package org.partiql.lang.ast;

import com.amazon.ion.IonWriter;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.partiql.lang.ast.Meta;
import org.partiql.lang.ast.MetaContainer;
import org.partiql.lang.ast.MetaContainerImpl$serialize$;
import org.partiql.lang.ast.MetaKt;
import org.partiql.lang.util.IonWriterContext;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000D\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\b\n\u0002\u0010\u0000\n\u0002\b\u0005\n\u0002\u0010\b\n\u0000\n\u0002\u0010(\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0082\b\u0018\u00002\u00020\u0001B\u001d\b\u0000\u0012\u0014\b\u0002\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u0003\u00a2\u0006\u0002\u0010\u0006J\u0010\u0010\u000b\u001a\u00020\u00012\u0006\u0010\f\u001a\u00020\u0005H\u0016J\u0015\u0010\r\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u0003H\u00c2\u0003J\u001f\u0010\u000e\u001a\u00020\u00002\u0014\b\u0002\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u0003H\u00c6\u0001J\u0013\u0010\u000f\u001a\u00020\b2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0011H\u0096\u0002J\u0012\u0010\u0012\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u0013\u001a\u00020\u0004H\u0016J\u0011\u0010\u0014\u001a\u00020\u00052\u0006\u0010\u0013\u001a\u00020\u0004H\u0096\u0002J\u0010\u0010\u0015\u001a\u00020\b2\u0006\u0010\u0013\u001a\u00020\u0004H\u0016J\b\u0010\u0016\u001a\u00020\u0017H\u0016J\u000f\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00050\u0019H\u0096\u0002J\u0010\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u001dH\u0016J\t\u0010\u001e\u001a\u00020\u0004H\u00d6\u0001R\u001a\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0007\u001a\u00020\bX\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n\u00a8\u0006\u001f"}, d2={"Lorg/partiql/lang/ast/MetaContainerImpl;", "Lorg/partiql/lang/ast/MetaContainer;", "metas", "Ljava/util/TreeMap;", "", "Lorg/partiql/lang/ast/Meta;", "(Ljava/util/TreeMap;)V", "shouldSerialize", "", "getShouldSerialize", "()Z", "add", "meta", "component1", "copy", "equals", "other", "", "find", "tagName", "get", "hasMeta", "hashCode", "", "iterator", "", "serialize", "", "writer", "Lcom/amazon/ion/IonWriter;", "toString", "lang"})
final class MetaContainerImpl
implements MetaContainer {
    private final boolean shouldSerialize;
    private final TreeMap<String, Meta> metas;

    @Override
    @NotNull
    public Iterator<Meta> iterator() {
        return this.metas.values().iterator();
    }

    @Override
    @NotNull
    public MetaContainer add(@NotNull Meta meta) {
        Intrinsics.checkParameterIsNotNull(meta, "meta");
        return MetaKt.plus(this, MetaKt.metaContainerOf(meta));
    }

    @Override
    public boolean hasMeta(@NotNull String tagName) {
        Intrinsics.checkParameterIsNotNull(tagName, "tagName");
        return this.metas.containsKey(tagName);
    }

    @Override
    @NotNull
    public Meta get(@NotNull String tagName) {
        Intrinsics.checkParameterIsNotNull(tagName, "tagName");
        Meta meta = this.metas.get(tagName);
        if (meta == null) {
            throw (Throwable)new IllegalArgumentException("Meta with tag '" + tagName + "' is not present in this MetaContainer instance.");
        }
        return meta;
    }

    @Override
    @Nullable
    public Meta find(@NotNull String tagName) {
        Intrinsics.checkParameterIsNotNull(tagName, "tagName");
        return this.metas.get(tagName);
    }

    @Override
    public boolean getShouldSerialize() {
        return this.shouldSerialize;
    }

    @Override
    public void serialize(@NotNull IonWriter writer) {
        Intrinsics.checkParameterIsNotNull(writer, "writer");
        IonWriterContext ionWriterContext = new IonWriterContext(writer);
        boolean bl = false;
        boolean bl2 = false;
        IonWriterContext $this$apply = ionWriterContext;
        boolean bl3 = false;
        Collection<Meta> collection = this.metas.values();
        Intrinsics.checkExpressionValueIsNotNull(collection, "metas.values");
        Iterable $this$forEach$iv = collection;
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            Meta it = (Meta)element$iv;
            boolean bl4 = false;
            $this$apply.sexp((Function1<? super IonWriterContext, Unit>)new Function1<IonWriterContext, Unit>(it, $this$apply, this, writer){
                final /* synthetic */ Meta $it;
                final /* synthetic */ IonWriterContext $this_apply$inlined;
                final /* synthetic */ MetaContainerImpl this$0;
                final /* synthetic */ IonWriter $writer$inlined;
                {
                    this.$it = meta;
                    this.$this_apply$inlined = ionWriterContext;
                    this.this$0 = metaContainerImpl;
                    this.$writer$inlined = ionWriter;
                    super(1);
                }

                public final void invoke(@NotNull IonWriterContext $this$sexp) {
                    Intrinsics.checkParameterIsNotNull($this$sexp, "$receiver");
                    $this$sexp.symbol(this.$it.getTag());
                    $this$sexp.sexp((Function1<? super IonWriterContext, Unit>)new Function1<IonWriterContext, Unit>(this){
                        final /* synthetic */ serialize$$inlined$apply$lambda$1 this$0;
                        {
                            this.this$0 = var1_1;
                            super(1);
                        }

                        public final void invoke(@NotNull IonWriterContext $this$sexp) {
                            Intrinsics.checkParameterIsNotNull($this$sexp, "$receiver");
                            this.this$0.$it.serialize(this.this$0.$writer$inlined);
                        }
                    });
                }
            });
        }
    }

    public int hashCode() {
        Set<String> set2 = this.metas.keySet();
        Intrinsics.checkExpressionValueIsNotNull(set2, "metas.keys");
        Collection collection = CollectionsKt.toList((Iterable)set2);
        Collection<Meta> collection2 = this.metas.values();
        Intrinsics.checkExpressionValueIsNotNull(collection2, "metas.values");
        Collection $this$toTypedArray$iv = CollectionsKt.plus(collection, (Iterable)CollectionsKt.toList((Iterable)collection2));
        boolean $i$f$toTypedArray = false;
        Collection thisCollection$iv = $this$toTypedArray$iv;
        Object[] objectArray = thisCollection$iv.toArray(new Object[0]);
        if (objectArray == null) {
            throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<T>");
        }
        return Arrays.hashCode(objectArray);
    }

    public boolean equals(@Nullable Object other) {
        boolean bl;
        if (this == other) {
            bl = true;
        } else {
            Object object = other;
            if (object == null) {
                bl = false;
            } else if (object instanceof MetaContainerImpl) {
                if (this.metas.size() != ((MetaContainerImpl)other).metas.size()) {
                    bl = false;
                } else {
                    Map $this$forEach$iv = this.metas;
                    boolean $i$f$forEach = false;
                    Map map2 = $this$forEach$iv;
                    boolean bl2 = false;
                    Iterator iterator2 = map2.entrySet().iterator();
                    while (iterator2.hasNext()) {
                        Map.Entry element$iv;
                        Map.Entry it = element$iv = iterator2.next();
                        boolean bl3 = false;
                        Meta otherValue = ((MetaContainerImpl)other).metas.get(it.getKey());
                        Meta meta = otherValue;
                        if (meta == null) {
                            return false;
                        }
                        if (!(Intrinsics.areEqual((Meta)it.getValue(), otherValue) ^ true)) continue;
                        return false;
                    }
                    bl = true;
                }
            } else {
                bl = false;
            }
        }
        return bl;
    }

    public MetaContainerImpl(@NotNull TreeMap<String, Meta> metas) {
        Intrinsics.checkParameterIsNotNull(metas, "metas");
        this.metas = metas;
        this.shouldSerialize = MapsKt.any((Map)this.metas);
    }

    public /* synthetic */ MetaContainerImpl(TreeMap treeMap, int n, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n & 1) != 0) {
            treeMap = new TreeMap();
        }
        this(treeMap);
    }

    public MetaContainerImpl() {
        this(null, 1, null);
    }

    private final TreeMap<String, Meta> component1() {
        return this.metas;
    }

    @NotNull
    public final MetaContainerImpl copy(@NotNull TreeMap<String, Meta> metas) {
        Intrinsics.checkParameterIsNotNull(metas, "metas");
        return new MetaContainerImpl(metas);
    }

    public static /* synthetic */ MetaContainerImpl copy$default(MetaContainerImpl metaContainerImpl, TreeMap treeMap, int n, Object object) {
        if ((n & 1) != 0) {
            treeMap = metaContainerImpl.metas;
        }
        return metaContainerImpl.copy(treeMap);
    }

    @NotNull
    public String toString() {
        return "MetaContainerImpl(metas=" + this.metas + ")";
    }
}

