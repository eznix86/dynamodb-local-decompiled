/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.partiql.lang.eval;

import kotlin.Metadata;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0004\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004\u00a8\u0006\u0005"}, d2={"Lorg/partiql/lang/eval/StructOrdering;", "", "(Ljava/lang/String;I)V", "UNORDERED", "ORDERED", "lang"})
public final class StructOrdering
extends Enum<StructOrdering> {
    public static final /* enum */ StructOrdering UNORDERED;
    public static final /* enum */ StructOrdering ORDERED;
    private static final /* synthetic */ StructOrdering[] $VALUES;

    static {
        StructOrdering[] structOrderingArray = new StructOrdering[2];
        StructOrdering[] structOrderingArray2 = structOrderingArray;
        structOrderingArray[0] = UNORDERED = new StructOrdering();
        structOrderingArray[1] = ORDERED = new StructOrdering();
        $VALUES = structOrderingArray;
    }

    public static StructOrdering[] values() {
        return (StructOrdering[])$VALUES.clone();
    }

    public static StructOrdering valueOf(String string) {
        return Enum.valueOf(StructOrdering.class, string);
    }
}

