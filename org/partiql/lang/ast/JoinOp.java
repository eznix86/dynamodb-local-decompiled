/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.partiql.lang.ast;

import kotlin.Metadata;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0006\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005j\u0002\b\u0006\u00a8\u0006\u0007"}, d2={"Lorg/partiql/lang/ast/JoinOp;", "", "(Ljava/lang/String;I)V", "INNER", "LEFT", "RIGHT", "OUTER", "lang"})
public final class JoinOp
extends Enum<JoinOp> {
    public static final /* enum */ JoinOp INNER;
    public static final /* enum */ JoinOp LEFT;
    public static final /* enum */ JoinOp RIGHT;
    public static final /* enum */ JoinOp OUTER;
    private static final /* synthetic */ JoinOp[] $VALUES;

    static {
        JoinOp[] joinOpArray = new JoinOp[4];
        JoinOp[] joinOpArray2 = joinOpArray;
        joinOpArray[0] = INNER = new JoinOp();
        joinOpArray[1] = LEFT = new JoinOp();
        joinOpArray[2] = RIGHT = new JoinOp();
        joinOpArray[3] = OUTER = new JoinOp();
        $VALUES = joinOpArray;
    }

    public static JoinOp[] values() {
        return (JoinOp[])$VALUES.clone();
    }

    public static JoinOp valueOf(String string) {
        return Enum.valueOf(JoinOp.class, string);
    }
}

