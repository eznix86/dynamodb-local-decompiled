/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package org.partiql.lang.ast;

import java.util.Iterator;
import java.util.TreeMap;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.collections.ArraysKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.partiql.lang.ast.Meta;
import org.partiql.lang.ast.MetaContainer;
import org.partiql.lang.ast.MetaContainerImpl;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=2, d1={"\u0000(\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001c\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\u001f\u0010\u0004\u001a\u00020\u00012\u0012\u0010\u0005\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00070\u0006\"\u00020\u0007\u00a2\u0006\u0002\u0010\b\u001a\u0014\u0010\u0004\u001a\u00020\u00012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\t\u001a\u0015\u0010\n\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\u000b\u001a\u00020\u0001H\u0086\u0002\u001a)\u0010\f\u001a\u0012\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u000e\u0012\u0004\u0012\u00020\u00070\r*\u0006\u0012\u0002\b\u00030\u000e2\u0006\u0010\u000f\u001a\u00020\u0007H\u0086\u0004\"\u0011\u0010\u0000\u001a\u00020\u0001\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0002\u0010\u0003\u00a8\u0006\u0010"}, d2={"emptyMetaContainer", "Lorg/partiql/lang/ast/MetaContainer;", "getEmptyMetaContainer", "()Lorg/partiql/lang/ast/MetaContainer;", "metaContainerOf", "metas", "", "Lorg/partiql/lang/ast/Meta;", "([Lorg/partiql/lang/ast/Meta;)Lorg/partiql/lang/ast/MetaContainer;", "", "plus", "other", "to", "Lkotlin/Pair;", "Ljava/lang/Class;", "m", "lang"})
public final class MetaKt {
    @NotNull
    private static final MetaContainer emptyMetaContainer = MetaKt.metaContainerOf(new Meta[0]);

    @NotNull
    public static final MetaContainer metaContainerOf(@NotNull Meta ... metas) {
        Intrinsics.checkParameterIsNotNull(metas, "metas");
        return MetaKt.metaContainerOf(ArraysKt.asIterable(metas));
    }

    @NotNull
    public static final MetaContainer getEmptyMetaContainer() {
        return emptyMetaContainer;
    }

    @NotNull
    public static final MetaContainer metaContainerOf(@NotNull Iterable<? extends Meta> metas) {
        TreeMap<String, Meta> treeMap;
        Intrinsics.checkParameterIsNotNull(metas, "metas");
        TreeMap<String, Meta> treeMap2 = new TreeMap<String, Meta>();
        boolean bl = false;
        boolean bl2 = false;
        TreeMap<String, Meta> $this$apply = treeMap2;
        boolean bl3 = false;
        Iterable<? extends Meta> $this$forEach$iv = metas;
        boolean $i$f$forEach = false;
        Iterator<? extends Meta> iterator2 = $this$forEach$iv.iterator();
        while (iterator2.hasNext()) {
            Meta element$iv;
            Meta it = element$iv = iterator2.next();
            boolean bl4 = false;
            if ($this$apply.containsKey(it.getTag())) {
                new IllegalArgumentException("List of metas contains one or more duplicate s-expression tag: " + it.getTag());
            }
            $this$apply.put(it.getTag(), it);
        }
        TreeMap<String, Meta> treeMap3 = treeMap = treeMap2;
        return new MetaContainerImpl(treeMap3);
    }

    @NotNull
    public static final Pair<Class<?>, Meta> to(@NotNull Class<?> $this$to, @NotNull Meta m) {
        Intrinsics.checkParameterIsNotNull($this$to, "$this$to");
        Intrinsics.checkParameterIsNotNull(m, "m");
        return new Pair($this$to, m);
    }

    @NotNull
    public static final MetaContainer plus(@NotNull MetaContainer $this$plus, @NotNull MetaContainer other) {
        TreeMap<String, Meta> treeMap;
        Meta it;
        Intrinsics.checkParameterIsNotNull($this$plus, "$this$plus");
        Intrinsics.checkParameterIsNotNull(other, "other");
        TreeMap<String, Meta> treeMap2 = new TreeMap<String, Meta>();
        boolean bl = false;
        boolean bl2 = false;
        TreeMap<String, Meta> newMap = treeMap2;
        boolean bl3 = false;
        Iterable $this$forEach$iv = $this$plus;
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            it = (Meta)element$iv;
            boolean bl4 = false;
            newMap.put(it.getTag(), it);
        }
        $this$forEach$iv = other;
        $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            it = (Meta)element$iv;
            boolean bl5 = false;
            newMap.put(it.getTag(), it);
        }
        TreeMap<String, Meta> treeMap3 = treeMap = treeMap2;
        return new MetaContainerImpl(treeMap3);
    }
}

