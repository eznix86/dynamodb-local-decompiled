/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.partiql.lang.syntax;

import kotlin.Metadata;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010\b\n\u0002\b\r\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u000f\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006j\u0002\b\u0007j\u0002\b\bj\u0002\b\tj\u0002\b\nj\u0002\b\u000bj\u0002\b\fj\u0002\b\rj\u0002\b\u000ej\u0002\b\u000f\u00a8\u0006\u0010"}, d2={"Lorg/partiql/lang/syntax/OperatorPrecedenceGroups;", "", "precedence", "", "(Ljava/lang/String;II)V", "getPrecedence", "()I", "SET", "SELECT", "LOGICAL_OR", "LOGICAL_AND", "LOGICAL_NOT", "EQUITY", "COMPARISON", "ADDITION", "MULTIPLY", "lang"})
public final class OperatorPrecedenceGroups
extends Enum<OperatorPrecedenceGroups> {
    public static final /* enum */ OperatorPrecedenceGroups SET;
    public static final /* enum */ OperatorPrecedenceGroups SELECT;
    public static final /* enum */ OperatorPrecedenceGroups LOGICAL_OR;
    public static final /* enum */ OperatorPrecedenceGroups LOGICAL_AND;
    public static final /* enum */ OperatorPrecedenceGroups LOGICAL_NOT;
    public static final /* enum */ OperatorPrecedenceGroups EQUITY;
    public static final /* enum */ OperatorPrecedenceGroups COMPARISON;
    public static final /* enum */ OperatorPrecedenceGroups ADDITION;
    public static final /* enum */ OperatorPrecedenceGroups MULTIPLY;
    private static final /* synthetic */ OperatorPrecedenceGroups[] $VALUES;
    private final int precedence;

    static {
        OperatorPrecedenceGroups[] operatorPrecedenceGroupsArray = new OperatorPrecedenceGroups[9];
        OperatorPrecedenceGroups[] operatorPrecedenceGroupsArray2 = operatorPrecedenceGroupsArray;
        operatorPrecedenceGroupsArray[0] = SET = new OperatorPrecedenceGroups(5);
        operatorPrecedenceGroupsArray[1] = SELECT = new OperatorPrecedenceGroups(6);
        operatorPrecedenceGroupsArray[2] = LOGICAL_OR = new OperatorPrecedenceGroups(10);
        operatorPrecedenceGroupsArray[3] = LOGICAL_AND = new OperatorPrecedenceGroups(20);
        operatorPrecedenceGroupsArray[4] = LOGICAL_NOT = new OperatorPrecedenceGroups(30);
        operatorPrecedenceGroupsArray[5] = EQUITY = new OperatorPrecedenceGroups(40);
        operatorPrecedenceGroupsArray[6] = COMPARISON = new OperatorPrecedenceGroups(50);
        operatorPrecedenceGroupsArray[7] = ADDITION = new OperatorPrecedenceGroups(60);
        operatorPrecedenceGroupsArray[8] = MULTIPLY = new OperatorPrecedenceGroups(70);
        $VALUES = operatorPrecedenceGroupsArray;
    }

    public final int getPrecedence() {
        return this.precedence;
    }

    private OperatorPrecedenceGroups(int precedence) {
        this.precedence = precedence;
    }

    public static OperatorPrecedenceGroups[] values() {
        return (OperatorPrecedenceGroups[])$VALUES.clone();
    }

    public static OperatorPrecedenceGroups valueOf(String string) {
        return Enum.valueOf(OperatorPrecedenceGroups.class, string);
    }
}

