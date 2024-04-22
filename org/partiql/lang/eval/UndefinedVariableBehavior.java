/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.partiql.lang.eval;

import kotlin.Metadata;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0004\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004\u00a8\u0006\u0005"}, d2={"Lorg/partiql/lang/eval/UndefinedVariableBehavior;", "", "(Ljava/lang/String;I)V", "ERROR", "MISSING", "lang"})
public final class UndefinedVariableBehavior
extends Enum<UndefinedVariableBehavior> {
    public static final /* enum */ UndefinedVariableBehavior ERROR;
    public static final /* enum */ UndefinedVariableBehavior MISSING;
    private static final /* synthetic */ UndefinedVariableBehavior[] $VALUES;

    static {
        UndefinedVariableBehavior[] undefinedVariableBehaviorArray = new UndefinedVariableBehavior[2];
        UndefinedVariableBehavior[] undefinedVariableBehaviorArray2 = undefinedVariableBehaviorArray;
        undefinedVariableBehaviorArray[0] = ERROR = new UndefinedVariableBehavior();
        undefinedVariableBehaviorArray[1] = MISSING = new UndefinedVariableBehavior();
        $VALUES = undefinedVariableBehaviorArray;
    }

    public static UndefinedVariableBehavior[] values() {
        return (UndefinedVariableBehavior[])$VALUES.clone();
    }

    public static UndefinedVariableBehavior valueOf(String string) {
        return Enum.valueOf(UndefinedVariableBehavior.class, string);
    }
}

