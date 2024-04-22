/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package org.partiql.lang.types;

import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.partiql.lang.types.FunctionSignature;
import org.partiql.lang.types.StaticType;
import org.partiql.lang.types.UntypedFunctionSignature;
import org.partiql.lang.types.VarargFormalParameter;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\b\u0086\b\u0018\u0000 \u001b2\u00020\u0001:\u0001\u001bB\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\t\u0010\u0012\u001a\u00020\u0003H\u00c6\u0003J\u0013\u0010\u0013\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u0003H\u00c6\u0001J\u0013\u0010\u0014\u001a\u00020\u00152\b\u0010\u0016\u001a\u0004\u0018\u00010\u0017H\u00d6\u0003J\t\u0010\u0018\u001a\u00020\u0019H\u00d6\u0001J\b\u0010\u001a\u001a\u00020\u0003H\u0016R\u001c\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0096\u0004\u00a2\u0006\n\n\u0002\b\n\u001a\u0004\b\b\u0010\tR\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0016\u0010\r\u001a\u00020\u000eX\u0096\u0004\u00a2\u0006\n\n\u0002\b\u0011\u001a\u0004\b\u000f\u0010\u0010\u00a8\u0006\u001c"}, d2={"Lorg/partiql/lang/types/UntypedFunctionSignature;", "Lorg/partiql/lang/types/FunctionSignature;", "name", "", "(Ljava/lang/String;)V", "formalParameters", "", "Lorg/partiql/lang/types/VarargFormalParameter;", "getFormalParameters", "()Ljava/util/List;", "formalParameters$1", "getName", "()Ljava/lang/String;", "returnType", "Lorg/partiql/lang/types/StaticType;", "getReturnType", "()Lorg/partiql/lang/types/StaticType;", "returnType$1", "component1", "copy", "equals", "", "other", "", "hashCode", "", "toString", "Companion", "lang"})
public final class UntypedFunctionSignature
extends FunctionSignature {
    @NotNull
    private final List<VarargFormalParameter> formalParameters$1;
    @NotNull
    private final StaticType returnType$1;
    @NotNull
    private final String name;
    private static final List<VarargFormalParameter> formalParameters;
    private static final StaticType returnType;
    public static final Companion Companion;

    @NotNull
    public List<VarargFormalParameter> getFormalParameters() {
        return this.formalParameters$1;
    }

    @Override
    @NotNull
    public StaticType getReturnType() {
        return this.returnType$1;
    }

    @NotNull
    public String toString() {
        return this.getName() + '(' + CollectionsKt.joinToString$default(this.getFormalParameters(), null, null, null, 0, null, toString.1.INSTANCE, 31, null) + "): " + this.getReturnType();
    }

    @Override
    @NotNull
    public String getName() {
        return this.name;
    }

    public UntypedFunctionSignature(@NotNull String name) {
        Intrinsics.checkParameterIsNotNull(name, "name");
        super(null);
        this.name = name;
        this.formalParameters$1 = formalParameters;
        this.returnType$1 = returnType;
    }

    static {
        Companion = new Companion(null);
        formalParameters = CollectionsKt.listOf(new VarargFormalParameter(StaticType.ANY));
        returnType = StaticType.ANY;
    }

    @NotNull
    public final String component1() {
        return this.getName();
    }

    @NotNull
    public final UntypedFunctionSignature copy(@NotNull String name) {
        Intrinsics.checkParameterIsNotNull(name, "name");
        return new UntypedFunctionSignature(name);
    }

    public static /* synthetic */ UntypedFunctionSignature copy$default(UntypedFunctionSignature untypedFunctionSignature, String string, int n, Object object) {
        if ((n & 1) != 0) {
            string = untypedFunctionSignature.getName();
        }
        return untypedFunctionSignature.copy(string);
    }

    public int hashCode() {
        String string = this.getName();
        return string != null ? string.hashCode() : 0;
    }

    public boolean equals(@Nullable Object object) {
        block3: {
            block2: {
                if (this == object) break block2;
                if (!(object instanceof UntypedFunctionSignature)) break block3;
                UntypedFunctionSignature untypedFunctionSignature = (UntypedFunctionSignature)object;
                if (!Intrinsics.areEqual(this.getName(), untypedFunctionSignature.getName())) break block3;
            }
            return true;
        }
        return false;
    }

    @Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\b"}, d2={"Lorg/partiql/lang/types/UntypedFunctionSignature$Companion;", "", "()V", "formalParameters", "", "Lorg/partiql/lang/types/VarargFormalParameter;", "returnType", "Lorg/partiql/lang/types/StaticType;", "lang"})
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

