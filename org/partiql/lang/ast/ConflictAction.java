/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.partiql.lang.ast;

import kotlin.Metadata;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0003\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002j\u0002\b\u0003\u00a8\u0006\u0004"}, d2={"Lorg/partiql/lang/ast/ConflictAction;", "", "(Ljava/lang/String;I)V", "DO_NOTHING", "lang"})
public final class ConflictAction
extends Enum<ConflictAction> {
    public static final /* enum */ ConflictAction DO_NOTHING;
    private static final /* synthetic */ ConflictAction[] $VALUES;

    static {
        ConflictAction[] conflictActionArray = new ConflictAction[1];
        ConflictAction[] conflictActionArray2 = conflictActionArray;
        conflictActionArray[0] = DO_NOTHING = new ConflictAction();
        $VALUES = conflictActionArray;
    }

    public static ConflictAction[] values() {
        return (ConflictAction[])$VALUES.clone();
    }

    public static ConflictAction valueOf(String string) {
        return Enum.valueOf(ConflictAction.class, string);
    }
}

