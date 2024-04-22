/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.partiql.lang.eval.visitors;

import kotlin.Metadata;
import org.partiql.lang.eval.visitors.StaticTypeVisitorTransform;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=3)
public final class StaticTypeVisitorTransform$VisitorTransform$WhenMappings {
    public static final /* synthetic */ int[] $EnumSwitchMapping$0;
    public static final /* synthetic */ int[] $EnumSwitchMapping$1;

    static {
        $EnumSwitchMapping$0 = new int[StaticTypeVisitorTransform.ScopeSearchOrder.values().length];
        StaticTypeVisitorTransform$VisitorTransform$WhenMappings.$EnumSwitchMapping$0[StaticTypeVisitorTransform.ScopeSearchOrder.GLOBALS_THEN_LEXICAL.ordinal()] = 1;
        StaticTypeVisitorTransform$VisitorTransform$WhenMappings.$EnumSwitchMapping$0[StaticTypeVisitorTransform.ScopeSearchOrder.LEXICAL.ordinal()] = 2;
        $EnumSwitchMapping$1 = new int[StaticTypeVisitorTransform.BindingScope.values().length];
        StaticTypeVisitorTransform$VisitorTransform$WhenMappings.$EnumSwitchMapping$1[StaticTypeVisitorTransform.BindingScope.LOCAL.ordinal()] = 1;
        StaticTypeVisitorTransform$VisitorTransform$WhenMappings.$EnumSwitchMapping$1[StaticTypeVisitorTransform.BindingScope.LEXICAL.ordinal()] = 2;
        StaticTypeVisitorTransform$VisitorTransform$WhenMappings.$EnumSwitchMapping$1[StaticTypeVisitorTransform.BindingScope.GLOBAL.ordinal()] = 3;
    }
}

