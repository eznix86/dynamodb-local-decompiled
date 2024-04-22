/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package org.partiql.lang.types;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import org.jetbrains.annotations.NotNull;
import org.partiql.lang.types.FormalParameter;
import org.partiql.lang.types.StaticType;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u0018\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007R\u0012\u0010\b\u001a\u00020\tX\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\n\u0010\u000bR\u0012\u0010\f\u001a\u00020\rX\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u000e\u0010\u000f\u0082\u0001\u0001\u0010\u00a8\u0006\u0011"}, d2={"Lorg/partiql/lang/types/FunctionSignature;", "", "()V", "formalParameters", "", "Lorg/partiql/lang/types/FormalParameter;", "getFormalParameters", "()Ljava/util/List;", "name", "", "getName", "()Ljava/lang/String;", "returnType", "Lorg/partiql/lang/types/StaticType;", "getReturnType", "()Lorg/partiql/lang/types/StaticType;", "Lorg/partiql/lang/types/UntypedFunctionSignature;", "lang"})
public abstract class FunctionSignature {
    @NotNull
    public abstract String getName();

    @NotNull
    public abstract List<FormalParameter> getFormalParameters();

    @NotNull
    public abstract StaticType getReturnType();

    private FunctionSignature() {
    }

    public /* synthetic */ FunctionSignature(DefaultConstructorMarker $constructor_marker) {
        this();
    }
}

