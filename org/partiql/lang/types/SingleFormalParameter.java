/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package org.partiql.lang.types;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.partiql.lang.types.FormalParameter;
import org.partiql.lang.types.StaticType;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\t\u0010\u0007\u001a\u00020\u0003H\u00c6\u0003J\u0013\u0010\b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u0003H\u00c6\u0001J\u0013\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\fH\u00d6\u0003J\t\u0010\r\u001a\u00020\u000eH\u00d6\u0001J\b\u0010\u000f\u001a\u00020\u0010H\u0016R\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006\u0011"}, d2={"Lorg/partiql/lang/types/SingleFormalParameter;", "Lorg/partiql/lang/types/FormalParameter;", "type", "Lorg/partiql/lang/types/StaticType;", "(Lorg/partiql/lang/types/StaticType;)V", "getType", "()Lorg/partiql/lang/types/StaticType;", "component1", "copy", "equals", "", "other", "", "hashCode", "", "toString", "", "lang"})
public final class SingleFormalParameter
extends FormalParameter {
    @NotNull
    private final StaticType type;

    @NotNull
    public String toString() {
        return this.getType().toString();
    }

    @Override
    @NotNull
    public StaticType getType() {
        return this.type;
    }

    public SingleFormalParameter(@NotNull StaticType type) {
        Intrinsics.checkParameterIsNotNull(type, "type");
        super(null);
        this.type = type;
    }

    @NotNull
    public final StaticType component1() {
        return this.getType();
    }

    @NotNull
    public final SingleFormalParameter copy(@NotNull StaticType type) {
        Intrinsics.checkParameterIsNotNull(type, "type");
        return new SingleFormalParameter(type);
    }

    public static /* synthetic */ SingleFormalParameter copy$default(SingleFormalParameter singleFormalParameter, StaticType staticType, int n, Object object) {
        if ((n & 1) != 0) {
            staticType = singleFormalParameter.getType();
        }
        return singleFormalParameter.copy(staticType);
    }

    public int hashCode() {
        StaticType staticType = this.getType();
        return staticType != null ? staticType.hashCode() : 0;
    }

    public boolean equals(@Nullable Object object) {
        block3: {
            block2: {
                if (this == object) break block2;
                if (!(object instanceof SingleFormalParameter)) break block3;
                SingleFormalParameter singleFormalParameter = (SingleFormalParameter)object;
                if (!Intrinsics.areEqual(this.getType(), singleFormalParameter.getType())) break block3;
            }
            return true;
        }
        return false;
    }
}

