/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.partiql.lang.eval;

import kotlin.Metadata;
import org.partiql.lang.eval.BindingCase;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=3)
public final class IonStructBindings$WhenMappings {
    public static final /* synthetic */ int[] $EnumSwitchMapping$0;

    static {
        $EnumSwitchMapping$0 = new int[BindingCase.values().length];
        IonStructBindings$WhenMappings.$EnumSwitchMapping$0[BindingCase.SENSITIVE.ordinal()] = 1;
        IonStructBindings$WhenMappings.$EnumSwitchMapping$0[BindingCase.INSENSITIVE.ordinal()] = 2;
    }
}

