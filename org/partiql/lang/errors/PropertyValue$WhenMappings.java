/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.partiql.lang.errors;

import kotlin.Metadata;
import org.partiql.lang.errors.PropertyType;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=3)
public final class PropertyValue$WhenMappings {
    public static final /* synthetic */ int[] $EnumSwitchMapping$0;
    public static final /* synthetic */ int[] $EnumSwitchMapping$1;

    static {
        $EnumSwitchMapping$0 = new int[PropertyType.values().length];
        PropertyValue$WhenMappings.$EnumSwitchMapping$0[PropertyType.LONG_CLASS.ordinal()] = 1;
        PropertyValue$WhenMappings.$EnumSwitchMapping$0[PropertyType.STRING_CLASS.ordinal()] = 2;
        PropertyValue$WhenMappings.$EnumSwitchMapping$0[PropertyType.INTEGER_CLASS.ordinal()] = 3;
        PropertyValue$WhenMappings.$EnumSwitchMapping$0[PropertyType.TOKEN_CLASS.ordinal()] = 4;
        PropertyValue$WhenMappings.$EnumSwitchMapping$0[PropertyType.ION_VALUE_CLASS.ordinal()] = 5;
        $EnumSwitchMapping$1 = new int[PropertyType.values().length];
        PropertyValue$WhenMappings.$EnumSwitchMapping$1[PropertyType.ION_VALUE_CLASS.ordinal()] = 1;
    }
}

