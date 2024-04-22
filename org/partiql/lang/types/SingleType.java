/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package org.partiql.lang.types;

import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.SetsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.partiql.lang.eval.ExprValueType;
import org.partiql.lang.types.StaticType;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\"\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\u0010\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0005H\u0016R\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u001a\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00050\nX\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\f\u00a8\u0006\u0010"}, d2={"Lorg/partiql/lang/types/SingleType;", "Lorg/partiql/lang/types/StaticType;", "name", "", "type", "Lorg/partiql/lang/eval/ExprValueType;", "(Ljava/lang/String;Lorg/partiql/lang/eval/ExprValueType;)V", "getType", "()Lorg/partiql/lang/eval/ExprValueType;", "typeDomain", "", "getTypeDomain", "()Ljava/util/Set;", "isOfType", "", "exprValueType", "lang"})
final class SingleType
extends StaticType {
    @NotNull
    private final Set<ExprValueType> typeDomain;
    @NotNull
    private final ExprValueType type;

    @Override
    @NotNull
    public Set<ExprValueType> getTypeDomain() {
        return this.typeDomain;
    }

    @Override
    public boolean isOfType(@NotNull ExprValueType exprValueType) {
        Intrinsics.checkParameterIsNotNull((Object)exprValueType, "exprValueType");
        return this.type == exprValueType;
    }

    @NotNull
    public final ExprValueType getType() {
        return this.type;
    }

    public SingleType(@NotNull String name, @NotNull ExprValueType type) {
        Intrinsics.checkParameterIsNotNull(name, "name");
        Intrinsics.checkParameterIsNotNull((Object)type, "type");
        super(name, null);
        this.type = type;
        this.typeDomain = SetsKt.setOf(this.type);
    }
}

