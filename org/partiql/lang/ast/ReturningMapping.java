/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.partiql.lang.ast;

import kotlin.Metadata;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0006\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005j\u0002\b\u0006\u00a8\u0006\u0007"}, d2={"Lorg/partiql/lang/ast/ReturningMapping;", "", "(Ljava/lang/String;I)V", "MODIFIED_NEW", "MODIFIED_OLD", "ALL_NEW", "ALL_OLD", "lang"})
public final class ReturningMapping
extends Enum<ReturningMapping> {
    public static final /* enum */ ReturningMapping MODIFIED_NEW;
    public static final /* enum */ ReturningMapping MODIFIED_OLD;
    public static final /* enum */ ReturningMapping ALL_NEW;
    public static final /* enum */ ReturningMapping ALL_OLD;
    private static final /* synthetic */ ReturningMapping[] $VALUES;

    static {
        ReturningMapping[] returningMappingArray = new ReturningMapping[4];
        ReturningMapping[] returningMappingArray2 = returningMappingArray;
        returningMappingArray[0] = MODIFIED_NEW = new ReturningMapping();
        returningMappingArray[1] = MODIFIED_OLD = new ReturningMapping();
        returningMappingArray[2] = ALL_NEW = new ReturningMapping();
        returningMappingArray[3] = ALL_OLD = new ReturningMapping();
        $VALUES = returningMappingArray;
    }

    public static ReturningMapping[] values() {
        return (ReturningMapping[])$VALUES.clone();
    }

    public static ReturningMapping valueOf(String string) {
        return Enum.valueOf(ReturningMapping.class, string);
    }
}

