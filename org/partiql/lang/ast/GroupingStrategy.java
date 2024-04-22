/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.partiql.lang.ast;

import kotlin.Metadata;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0004\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004\u00a8\u0006\u0005"}, d2={"Lorg/partiql/lang/ast/GroupingStrategy;", "", "(Ljava/lang/String;I)V", "FULL", "PARTIAL", "lang"})
public final class GroupingStrategy
extends Enum<GroupingStrategy> {
    public static final /* enum */ GroupingStrategy FULL;
    public static final /* enum */ GroupingStrategy PARTIAL;
    private static final /* synthetic */ GroupingStrategy[] $VALUES;

    static {
        GroupingStrategy[] groupingStrategyArray = new GroupingStrategy[2];
        GroupingStrategy[] groupingStrategyArray2 = groupingStrategyArray;
        groupingStrategyArray[0] = FULL = new GroupingStrategy();
        groupingStrategyArray[1] = PARTIAL = new GroupingStrategy();
        $VALUES = groupingStrategyArray;
    }

    public static GroupingStrategy[] values() {
        return (GroupingStrategy[])$VALUES.clone();
    }

    public static GroupingStrategy valueOf(String string) {
        return Enum.valueOf(GroupingStrategy.class, string);
    }
}

