/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.partiql.lang.eval.visitors;

import kotlin.Metadata;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0004\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004\u00a8\u0006\u0005"}, d2={"Lorg/partiql/lang/eval/visitors/StaticTypeVisitorTransformConstraints;", "", "(Ljava/lang/String;I)V", "PREVENT_GLOBALS_EXCEPT_IN_FROM", "PREVENT_GLOBALS_IN_NESTED_QUERIES", "lang"})
public final class StaticTypeVisitorTransformConstraints
extends Enum<StaticTypeVisitorTransformConstraints> {
    public static final /* enum */ StaticTypeVisitorTransformConstraints PREVENT_GLOBALS_EXCEPT_IN_FROM;
    public static final /* enum */ StaticTypeVisitorTransformConstraints PREVENT_GLOBALS_IN_NESTED_QUERIES;
    private static final /* synthetic */ StaticTypeVisitorTransformConstraints[] $VALUES;

    static {
        StaticTypeVisitorTransformConstraints[] staticTypeVisitorTransformConstraintsArray = new StaticTypeVisitorTransformConstraints[2];
        StaticTypeVisitorTransformConstraints[] staticTypeVisitorTransformConstraintsArray2 = staticTypeVisitorTransformConstraintsArray;
        staticTypeVisitorTransformConstraintsArray[0] = PREVENT_GLOBALS_EXCEPT_IN_FROM = new StaticTypeVisitorTransformConstraints();
        staticTypeVisitorTransformConstraintsArray[1] = PREVENT_GLOBALS_IN_NESTED_QUERIES = new StaticTypeVisitorTransformConstraints();
        $VALUES = staticTypeVisitorTransformConstraintsArray;
    }

    public static StaticTypeVisitorTransformConstraints[] values() {
        return (StaticTypeVisitorTransformConstraints[])$VALUES.clone();
    }

    public static StaticTypeVisitorTransformConstraints valueOf(String string) {
        return Enum.valueOf(StaticTypeVisitorTransformConstraints.class, string);
    }
}

