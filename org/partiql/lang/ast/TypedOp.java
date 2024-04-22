/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package org.partiql.lang.ast;

import kotlin.Metadata;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0006\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u000f\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006j\u0002\b\u0007j\u0002\b\b\u00a8\u0006\t"}, d2={"Lorg/partiql/lang/ast/TypedOp;", "", "text", "", "(Ljava/lang/String;ILjava/lang/String;)V", "getText", "()Ljava/lang/String;", "CAST", "IS", "lang"})
public final class TypedOp
extends Enum<TypedOp> {
    public static final /* enum */ TypedOp CAST;
    public static final /* enum */ TypedOp IS;
    private static final /* synthetic */ TypedOp[] $VALUES;
    @NotNull
    private final String text;

    static {
        TypedOp[] typedOpArray = new TypedOp[2];
        TypedOp[] typedOpArray2 = typedOpArray;
        typedOpArray[0] = CAST = new TypedOp("cast");
        typedOpArray[1] = IS = new TypedOp("is");
        $VALUES = typedOpArray;
    }

    @NotNull
    public final String getText() {
        return this.text;
    }

    private TypedOp(String text) {
        this.text = text;
    }

    public static TypedOp[] values() {
        return (TypedOp[])$VALUES.clone();
    }

    public static TypedOp valueOf(String string) {
        return Enum.valueOf(TypedOp.class, string);
    }
}

