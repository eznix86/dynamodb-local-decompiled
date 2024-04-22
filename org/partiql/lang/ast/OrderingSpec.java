/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.partiql.lang.ast;

import kotlin.Metadata;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0004\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004\u00a8\u0006\u0005"}, d2={"Lorg/partiql/lang/ast/OrderingSpec;", "", "(Ljava/lang/String;I)V", "ASC", "DESC", "lang"})
public final class OrderingSpec
extends Enum<OrderingSpec> {
    public static final /* enum */ OrderingSpec ASC;
    public static final /* enum */ OrderingSpec DESC;
    private static final /* synthetic */ OrderingSpec[] $VALUES;

    static {
        OrderingSpec[] orderingSpecArray = new OrderingSpec[2];
        OrderingSpec[] orderingSpecArray2 = orderingSpecArray;
        orderingSpecArray[0] = ASC = new OrderingSpec();
        orderingSpecArray[1] = DESC = new OrderingSpec();
        $VALUES = orderingSpecArray;
    }

    public static OrderingSpec[] values() {
        return (OrderingSpec[])$VALUES.clone();
    }

    public static OrderingSpec valueOf(String string) {
        return Enum.valueOf(OrderingSpec.class, string);
    }
}

