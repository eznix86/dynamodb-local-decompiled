/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.partiql.lang.ast;

import kotlin.Metadata;
import org.partiql.lang.ast.CaseSensitivity;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=3)
public final class VariableReference$WhenMappings {
    public static final /* synthetic */ int[] $EnumSwitchMapping$0;

    static {
        $EnumSwitchMapping$0 = new int[CaseSensitivity.values().length];
        VariableReference$WhenMappings.$EnumSwitchMapping$0[CaseSensitivity.SENSITIVE.ordinal()] = 1;
        VariableReference$WhenMappings.$EnumSwitchMapping$0[CaseSensitivity.INSENSITIVE.ordinal()] = 2;
    }
}

