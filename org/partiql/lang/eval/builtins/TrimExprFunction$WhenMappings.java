/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.partiql.lang.eval.builtins;

import kotlin.Metadata;
import org.partiql.lang.eval.builtins.TrimSpecification;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=3)
public final class TrimExprFunction$WhenMappings {
    public static final /* synthetic */ int[] $EnumSwitchMapping$0;
    public static final /* synthetic */ int[] $EnumSwitchMapping$1;

    static {
        $EnumSwitchMapping$0 = new int[TrimSpecification.values().length];
        TrimExprFunction$WhenMappings.$EnumSwitchMapping$0[TrimSpecification.BOTH.ordinal()] = 1;
        TrimExprFunction$WhenMappings.$EnumSwitchMapping$0[TrimSpecification.NONE.ordinal()] = 2;
        TrimExprFunction$WhenMappings.$EnumSwitchMapping$0[TrimSpecification.LEADING.ordinal()] = 3;
        TrimExprFunction$WhenMappings.$EnumSwitchMapping$0[TrimSpecification.TRAILING.ordinal()] = 4;
        $EnumSwitchMapping$1 = new int[TrimSpecification.values().length];
        TrimExprFunction$WhenMappings.$EnumSwitchMapping$1[TrimSpecification.NONE.ordinal()] = 1;
    }
}

