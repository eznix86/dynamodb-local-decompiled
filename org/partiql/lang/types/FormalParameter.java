/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package org.partiql.lang.types;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import org.jetbrains.annotations.NotNull;
import org.partiql.lang.types.StaticType;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u0012\u0010\u0003\u001a\u00020\u0004X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006\u0082\u0001\u0002\u0007\b\u00a8\u0006\t"}, d2={"Lorg/partiql/lang/types/FormalParameter;", "", "()V", "type", "Lorg/partiql/lang/types/StaticType;", "getType", "()Lorg/partiql/lang/types/StaticType;", "Lorg/partiql/lang/types/SingleFormalParameter;", "Lorg/partiql/lang/types/VarargFormalParameter;", "lang"})
public abstract class FormalParameter {
    @NotNull
    public abstract StaticType getType();

    private FormalParameter() {
    }

    public /* synthetic */ FormalParameter(DefaultConstructorMarker $constructor_marker) {
        this();
    }
}

