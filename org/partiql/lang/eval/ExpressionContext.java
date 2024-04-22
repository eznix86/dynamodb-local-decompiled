/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.partiql.lang.eval;

import kotlin.Metadata;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0005\b\u0082\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005\u00a8\u0006\u0006"}, d2={"Lorg/partiql/lang/eval/ExpressionContext;", "", "(Ljava/lang/String;I)V", "NORMAL", "SELECT_LIST", "AGG_ARG", "lang"})
final class ExpressionContext
extends Enum<ExpressionContext> {
    public static final /* enum */ ExpressionContext NORMAL;
    public static final /* enum */ ExpressionContext SELECT_LIST;
    public static final /* enum */ ExpressionContext AGG_ARG;
    private static final /* synthetic */ ExpressionContext[] $VALUES;

    static {
        ExpressionContext[] expressionContextArray = new ExpressionContext[3];
        ExpressionContext[] expressionContextArray2 = expressionContextArray;
        expressionContextArray[0] = NORMAL = new ExpressionContext();
        expressionContextArray[1] = SELECT_LIST = new ExpressionContext();
        expressionContextArray[2] = AGG_ARG = new ExpressionContext();
        $VALUES = expressionContextArray;
    }

    public static ExpressionContext[] values() {
        return (ExpressionContext[])$VALUES.clone();
    }

    public static ExpressionContext valueOf(String string) {
        return Enum.valueOf(ExpressionContext.class, string);
    }
}

