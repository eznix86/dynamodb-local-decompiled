/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.partiql.lang.eval;

import com.amazon.ion.IonType;
import kotlin.Metadata;
import org.partiql.lang.ast.SqlDataType;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=3)
public final class ExprValueType$Companion$WhenMappings {
    public static final /* synthetic */ int[] $EnumSwitchMapping$0;
    public static final /* synthetic */ int[] $EnumSwitchMapping$1;

    static {
        $EnumSwitchMapping$0 = new int[IonType.values().length];
        ExprValueType$Companion$WhenMappings.$EnumSwitchMapping$0[IonType.DATAGRAM.ordinal()] = 1;
        $EnumSwitchMapping$1 = new int[SqlDataType.values().length];
        ExprValueType$Companion$WhenMappings.$EnumSwitchMapping$1[SqlDataType.BOOLEAN.ordinal()] = 1;
        ExprValueType$Companion$WhenMappings.$EnumSwitchMapping$1[SqlDataType.MISSING.ordinal()] = 2;
        ExprValueType$Companion$WhenMappings.$EnumSwitchMapping$1[SqlDataType.NULL.ordinal()] = 3;
        ExprValueType$Companion$WhenMappings.$EnumSwitchMapping$1[SqlDataType.SMALLINT.ordinal()] = 4;
        ExprValueType$Companion$WhenMappings.$EnumSwitchMapping$1[SqlDataType.INTEGER.ordinal()] = 5;
        ExprValueType$Companion$WhenMappings.$EnumSwitchMapping$1[SqlDataType.FLOAT.ordinal()] = 6;
        ExprValueType$Companion$WhenMappings.$EnumSwitchMapping$1[SqlDataType.REAL.ordinal()] = 7;
        ExprValueType$Companion$WhenMappings.$EnumSwitchMapping$1[SqlDataType.DOUBLE_PRECISION.ordinal()] = 8;
        ExprValueType$Companion$WhenMappings.$EnumSwitchMapping$1[SqlDataType.DECIMAL.ordinal()] = 9;
        ExprValueType$Companion$WhenMappings.$EnumSwitchMapping$1[SqlDataType.NUMERIC.ordinal()] = 10;
        ExprValueType$Companion$WhenMappings.$EnumSwitchMapping$1[SqlDataType.TIMESTAMP.ordinal()] = 11;
        ExprValueType$Companion$WhenMappings.$EnumSwitchMapping$1[SqlDataType.CHARACTER.ordinal()] = 12;
        ExprValueType$Companion$WhenMappings.$EnumSwitchMapping$1[SqlDataType.CHARACTER_VARYING.ordinal()] = 13;
        ExprValueType$Companion$WhenMappings.$EnumSwitchMapping$1[SqlDataType.STRING.ordinal()] = 14;
        ExprValueType$Companion$WhenMappings.$EnumSwitchMapping$1[SqlDataType.SYMBOL.ordinal()] = 15;
        ExprValueType$Companion$WhenMappings.$EnumSwitchMapping$1[SqlDataType.CLOB.ordinal()] = 16;
        ExprValueType$Companion$WhenMappings.$EnumSwitchMapping$1[SqlDataType.BLOB.ordinal()] = 17;
        ExprValueType$Companion$WhenMappings.$EnumSwitchMapping$1[SqlDataType.STRUCT.ordinal()] = 18;
        ExprValueType$Companion$WhenMappings.$EnumSwitchMapping$1[SqlDataType.TUPLE.ordinal()] = 19;
        ExprValueType$Companion$WhenMappings.$EnumSwitchMapping$1[SqlDataType.LIST.ordinal()] = 20;
        ExprValueType$Companion$WhenMappings.$EnumSwitchMapping$1[SqlDataType.SEXP.ordinal()] = 21;
        ExprValueType$Companion$WhenMappings.$EnumSwitchMapping$1[SqlDataType.BAG.ordinal()] = 22;
        ExprValueType$Companion$WhenMappings.$EnumSwitchMapping$1[SqlDataType.DATE.ordinal()] = 23;
        ExprValueType$Companion$WhenMappings.$EnumSwitchMapping$1[SqlDataType.TIME.ordinal()] = 24;
        ExprValueType$Companion$WhenMappings.$EnumSwitchMapping$1[SqlDataType.TIME_WITH_TIME_ZONE.ordinal()] = 25;
    }
}

