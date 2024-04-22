/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.partiql.lang.eval;

import kotlin.Metadata;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0004\b\u0082\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004\u00a8\u0006\u0005"}, d2={"Lorg/partiql/lang/eval/JoinExpansion;", "", "(Ljava/lang/String;I)V", "INNER", "OUTER", "lang"})
final class JoinExpansion
extends Enum<JoinExpansion> {
    public static final /* enum */ JoinExpansion INNER;
    public static final /* enum */ JoinExpansion OUTER;
    private static final /* synthetic */ JoinExpansion[] $VALUES;

    static {
        JoinExpansion[] joinExpansionArray = new JoinExpansion[2];
        JoinExpansion[] joinExpansionArray2 = joinExpansionArray;
        joinExpansionArray[0] = INNER = new JoinExpansion();
        joinExpansionArray[1] = OUTER = new JoinExpansion();
        $VALUES = joinExpansionArray;
    }

    public static JoinExpansion[] values() {
        return (JoinExpansion[])$VALUES.clone();
    }

    public static JoinExpansion valueOf(String string) {
        return Enum.valueOf(JoinExpansion.class, string);
    }
}

