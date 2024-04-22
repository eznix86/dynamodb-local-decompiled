/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.partiql.lang.eval;

import com.amazon.ion.IntegerSize;
import kotlin.Metadata;
import org.partiql.lang.ast.SqlDataType;
import org.partiql.lang.eval.ExprValueType;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=3)
public final class ExprValueExtensionsKt$WhenMappings {
    public static final /* synthetic */ int[] $EnumSwitchMapping$0;
    public static final /* synthetic */ int[] $EnumSwitchMapping$1;
    public static final /* synthetic */ int[] $EnumSwitchMapping$2;
    public static final /* synthetic */ int[] $EnumSwitchMapping$3;
    public static final /* synthetic */ int[] $EnumSwitchMapping$4;
    public static final /* synthetic */ int[] $EnumSwitchMapping$5;

    static {
        $EnumSwitchMapping$0 = new int[ExprValueType.values().length];
        ExprValueExtensionsKt$WhenMappings.$EnumSwitchMapping$0[ExprValueType.STRING.ordinal()] = 1;
        ExprValueExtensionsKt$WhenMappings.$EnumSwitchMapping$0[ExprValueType.SYMBOL.ordinal()] = 2;
        $EnumSwitchMapping$1 = new int[IntegerSize.values().length];
        ExprValueExtensionsKt$WhenMappings.$EnumSwitchMapping$1[IntegerSize.BIG_INTEGER.ordinal()] = 1;
        $EnumSwitchMapping$2 = new int[SqlDataType.values().length];
        ExprValueExtensionsKt$WhenMappings.$EnumSwitchMapping$2[SqlDataType.TIME_WITH_TIME_ZONE.ordinal()] = 1;
        $EnumSwitchMapping$3 = new int[SqlDataType.values().length];
        ExprValueExtensionsKt$WhenMappings.$EnumSwitchMapping$3[SqlDataType.TIME_WITH_TIME_ZONE.ordinal()] = 1;
        $EnumSwitchMapping$4 = new int[SqlDataType.values().length];
        ExprValueExtensionsKt$WhenMappings.$EnumSwitchMapping$4[SqlDataType.TIME_WITH_TIME_ZONE.ordinal()] = 1;
        $EnumSwitchMapping$5 = new int[SqlDataType.values().length];
        ExprValueExtensionsKt$WhenMappings.$EnumSwitchMapping$5[SqlDataType.BOOLEAN.ordinal()] = 1;
        ExprValueExtensionsKt$WhenMappings.$EnumSwitchMapping$5[SqlDataType.SMALLINT.ordinal()] = 2;
        ExprValueExtensionsKt$WhenMappings.$EnumSwitchMapping$5[SqlDataType.INTEGER.ordinal()] = 3;
        ExprValueExtensionsKt$WhenMappings.$EnumSwitchMapping$5[SqlDataType.FLOAT.ordinal()] = 4;
        ExprValueExtensionsKt$WhenMappings.$EnumSwitchMapping$5[SqlDataType.REAL.ordinal()] = 5;
        ExprValueExtensionsKt$WhenMappings.$EnumSwitchMapping$5[SqlDataType.DOUBLE_PRECISION.ordinal()] = 6;
        ExprValueExtensionsKt$WhenMappings.$EnumSwitchMapping$5[SqlDataType.DECIMAL.ordinal()] = 7;
        ExprValueExtensionsKt$WhenMappings.$EnumSwitchMapping$5[SqlDataType.NUMERIC.ordinal()] = 8;
        ExprValueExtensionsKt$WhenMappings.$EnumSwitchMapping$5[SqlDataType.TIMESTAMP.ordinal()] = 9;
        ExprValueExtensionsKt$WhenMappings.$EnumSwitchMapping$5[SqlDataType.DATE.ordinal()] = 10;
        ExprValueExtensionsKt$WhenMappings.$EnumSwitchMapping$5[SqlDataType.TIME.ordinal()] = 11;
        ExprValueExtensionsKt$WhenMappings.$EnumSwitchMapping$5[SqlDataType.TIME_WITH_TIME_ZONE.ordinal()] = 12;
        ExprValueExtensionsKt$WhenMappings.$EnumSwitchMapping$5[SqlDataType.CHARACTER.ordinal()] = 13;
        ExprValueExtensionsKt$WhenMappings.$EnumSwitchMapping$5[SqlDataType.CHARACTER_VARYING.ordinal()] = 14;
        ExprValueExtensionsKt$WhenMappings.$EnumSwitchMapping$5[SqlDataType.STRING.ordinal()] = 15;
        ExprValueExtensionsKt$WhenMappings.$EnumSwitchMapping$5[SqlDataType.SYMBOL.ordinal()] = 16;
        ExprValueExtensionsKt$WhenMappings.$EnumSwitchMapping$5[SqlDataType.CLOB.ordinal()] = 17;
        ExprValueExtensionsKt$WhenMappings.$EnumSwitchMapping$5[SqlDataType.BLOB.ordinal()] = 18;
        ExprValueExtensionsKt$WhenMappings.$EnumSwitchMapping$5[SqlDataType.LIST.ordinal()] = 19;
        ExprValueExtensionsKt$WhenMappings.$EnumSwitchMapping$5[SqlDataType.SEXP.ordinal()] = 20;
        ExprValueExtensionsKt$WhenMappings.$EnumSwitchMapping$5[SqlDataType.BAG.ordinal()] = 21;
    }
}

