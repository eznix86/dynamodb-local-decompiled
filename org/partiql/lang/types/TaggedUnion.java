/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package org.partiql.lang.types;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.partiql.lang.eval.ExprValueType;
import org.partiql.lang.types.StaticType;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\"\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0002\b\u0002\u0018\u00002\u00020\u0001B\u001b\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00010\u0005\u00a2\u0006\u0002\u0010\u0006J\u0010\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\bH\u0016R\u001a\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\b0\u0005X\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0017\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00010\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\n\u00a8\u0006\u000f"}, d2={"Lorg/partiql/lang/types/TaggedUnion;", "Lorg/partiql/lang/types/StaticType;", "name", "", "types", "", "(Ljava/lang/String;Ljava/util/Set;)V", "typeDomain", "Lorg/partiql/lang/eval/ExprValueType;", "getTypeDomain", "()Ljava/util/Set;", "getTypes", "isOfType", "", "exprValueType", "lang"})
final class TaggedUnion
extends StaticType {
    @NotNull
    private final Set<ExprValueType> typeDomain;
    @NotNull
    private final Set<StaticType> types;

    @Override
    @NotNull
    public Set<ExprValueType> getTypeDomain() {
        return this.typeDomain;
    }

    @Override
    public boolean isOfType(@NotNull ExprValueType exprValueType) {
        boolean bl;
        block3: {
            Intrinsics.checkParameterIsNotNull((Object)exprValueType, "exprValueType");
            Iterable $this$any$iv = this.types;
            boolean $i$f$any = false;
            if ($this$any$iv instanceof Collection && ((Collection)$this$any$iv).isEmpty()) {
                bl = false;
            } else {
                for (Object element$iv : $this$any$iv) {
                    StaticType it = (StaticType)element$iv;
                    boolean bl2 = false;
                    if (!it.isOfType(exprValueType)) continue;
                    bl = true;
                    break block3;
                }
                bl = false;
            }
        }
        return bl;
    }

    @NotNull
    public final Set<StaticType> getTypes() {
        return this.types;
    }

    /*
     * WARNING - void declaration
     */
    public TaggedUnion(@NotNull String name, @NotNull Set<? extends StaticType> types) {
        void $this$flatMapTo$iv$iv;
        void $this$flatMap$iv;
        Intrinsics.checkParameterIsNotNull(name, "name");
        Intrinsics.checkParameterIsNotNull(types, "types");
        super(name, null);
        this.types = types;
        Iterable iterable = this.types;
        TaggedUnion taggedUnion = this;
        boolean $i$f$flatMap = false;
        void var5_6 = $this$flatMap$iv;
        Collection destination$iv$iv = new ArrayList();
        boolean $i$f$flatMapTo = false;
        for (Object element$iv$iv : $this$flatMapTo$iv$iv) {
            StaticType it = (StaticType)element$iv$iv;
            boolean bl = false;
            Iterable list$iv$iv = it.getTypeDomain();
            CollectionsKt.addAll(destination$iv$iv, list$iv$iv);
        }
        List list = (List)destination$iv$iv;
        taggedUnion.typeDomain = CollectionsKt.toSet(list);
    }
}

