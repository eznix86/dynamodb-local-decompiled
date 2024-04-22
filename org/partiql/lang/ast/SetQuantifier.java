/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.partiql.lang.ast;

import kotlin.Metadata;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0004\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004\u00a8\u0006\u0005"}, d2={"Lorg/partiql/lang/ast/SetQuantifier;", "", "(Ljava/lang/String;I)V", "ALL", "DISTINCT", "lang"})
public final class SetQuantifier
extends Enum<SetQuantifier> {
    public static final /* enum */ SetQuantifier ALL;
    public static final /* enum */ SetQuantifier DISTINCT;
    private static final /* synthetic */ SetQuantifier[] $VALUES;

    static {
        SetQuantifier[] setQuantifierArray = new SetQuantifier[2];
        SetQuantifier[] setQuantifierArray2 = setQuantifierArray;
        setQuantifierArray[0] = ALL = new SetQuantifier();
        setQuantifierArray[1] = DISTINCT = new SetQuantifier();
        $VALUES = setQuantifierArray;
    }

    public static SetQuantifier[] values() {
        return (SetQuantifier[])$VALUES.clone();
    }

    public static SetQuantifier valueOf(String string) {
        return Enum.valueOf(SetQuantifier.class, string);
    }
}

