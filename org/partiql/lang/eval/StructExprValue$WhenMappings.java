/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.partiql.lang.eval;

import kotlin.Metadata;
import org.partiql.lang.eval.StructOrdering;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=3)
public final class StructExprValue$WhenMappings {
    public static final /* synthetic */ int[] $EnumSwitchMapping$0;
    public static final /* synthetic */ int[] $EnumSwitchMapping$1;

    static {
        $EnumSwitchMapping$0 = new int[StructOrdering.values().length];
        StructExprValue$WhenMappings.$EnumSwitchMapping$0[StructOrdering.ORDERED.ordinal()] = 1;
        StructExprValue$WhenMappings.$EnumSwitchMapping$0[StructOrdering.UNORDERED.ordinal()] = 2;
        $EnumSwitchMapping$1 = new int[StructOrdering.values().length];
        StructExprValue$WhenMappings.$EnumSwitchMapping$1[StructOrdering.ORDERED.ordinal()] = 1;
    }
}

