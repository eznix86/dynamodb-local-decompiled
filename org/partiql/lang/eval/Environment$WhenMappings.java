/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.partiql.lang.eval;

import kotlin.Metadata;
import org.partiql.lang.eval.Environment;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=3)
public final class Environment$WhenMappings {
    public static final /* synthetic */ int[] $EnumSwitchMapping$0;

    static {
        $EnumSwitchMapping$0 = new int[Environment.CurrentMode.values().length];
        Environment$WhenMappings.$EnumSwitchMapping$0[Environment.CurrentMode.LOCALS.ordinal()] = 1;
        Environment$WhenMappings.$EnumSwitchMapping$0[Environment.CurrentMode.GLOBALS_THEN_LOCALS.ordinal()] = 2;
    }
}

