/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.partiql.lang.eval.builtins.timestamp;

import kotlin.Metadata;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010\u000b\n\u0002\b\f\b\u0082\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0017\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0005R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0011\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0007j\u0002\b\tj\u0002\b\nj\u0002\b\u000bj\u0002\b\fj\u0002\b\rj\u0002\b\u000e\u00a8\u0006\u000f"}, d2={"Lorg/partiql/lang/eval/builtins/timestamp/StateType;", "", "beginsToken", "", "endsToken", "(Ljava/lang/String;IZZ)V", "getBeginsToken", "()Z", "getEndsToken", "INITIAL", "ERROR", "START", "TERMINAL", "START_AND_TERMINAL", "INCOMPLETE", "lang"})
final class StateType
extends Enum<StateType> {
    public static final /* enum */ StateType INITIAL;
    public static final /* enum */ StateType ERROR;
    public static final /* enum */ StateType START;
    public static final /* enum */ StateType TERMINAL;
    public static final /* enum */ StateType START_AND_TERMINAL;
    public static final /* enum */ StateType INCOMPLETE;
    private static final /* synthetic */ StateType[] $VALUES;
    private final boolean beginsToken;
    private final boolean endsToken;

    static {
        StateType[] stateTypeArray = new StateType[6];
        StateType[] stateTypeArray2 = stateTypeArray;
        stateTypeArray[0] = INITIAL = new StateType(false, false);
        stateTypeArray[1] = ERROR = new StateType(false, false);
        stateTypeArray[2] = START = new StateType(true, false);
        stateTypeArray[3] = TERMINAL = new StateType(false, true);
        stateTypeArray[4] = START_AND_TERMINAL = new StateType(true, true);
        stateTypeArray[5] = INCOMPLETE = new StateType(false, false);
        $VALUES = stateTypeArray;
    }

    public final boolean getBeginsToken() {
        return this.beginsToken;
    }

    public final boolean getEndsToken() {
        return this.endsToken;
    }

    private StateType(boolean beginsToken, boolean endsToken) {
        this.beginsToken = beginsToken;
        this.endsToken = endsToken;
    }

    public static StateType[] values() {
        return (StateType[])$VALUES.clone();
    }

    public static StateType valueOf(String string) {
        return Enum.valueOf(StateType.class, string);
    }
}

