/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.partiql.lang.eval.io;

import com.amazon.ion.IonType;
import kotlin.Metadata;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=3)
public final class DelimitedValues$WhenMappings {
    public static final /* synthetic */ int[] $EnumSwitchMapping$0;

    static {
        $EnumSwitchMapping$0 = new int[IonType.values().length];
        DelimitedValues$WhenMappings.$EnumSwitchMapping$0[IonType.NULL.ordinal()] = 1;
        DelimitedValues$WhenMappings.$EnumSwitchMapping$0[IonType.BOOL.ordinal()] = 2;
        DelimitedValues$WhenMappings.$EnumSwitchMapping$0[IonType.INT.ordinal()] = 3;
        DelimitedValues$WhenMappings.$EnumSwitchMapping$0[IonType.FLOAT.ordinal()] = 4;
        DelimitedValues$WhenMappings.$EnumSwitchMapping$0[IonType.DECIMAL.ordinal()] = 5;
        DelimitedValues$WhenMappings.$EnumSwitchMapping$0[IonType.TIMESTAMP.ordinal()] = 6;
        DelimitedValues$WhenMappings.$EnumSwitchMapping$0[IonType.SYMBOL.ordinal()] = 7;
        DelimitedValues$WhenMappings.$EnumSwitchMapping$0[IonType.STRING.ordinal()] = 8;
    }
}

