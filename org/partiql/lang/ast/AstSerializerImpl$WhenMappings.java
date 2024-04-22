/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.partiql.lang.ast;

import kotlin.Metadata;
import org.partiql.lang.ast.AstVersion;
import org.partiql.lang.ast.GroupingStrategy;
import org.partiql.lang.ast.JoinOp;
import org.partiql.lang.ast.NAryOp;
import org.partiql.lang.ast.SetQuantifier;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=3)
public final class AstSerializerImpl$WhenMappings {
    public static final /* synthetic */ int[] $EnumSwitchMapping$0;
    public static final /* synthetic */ int[] $EnumSwitchMapping$1;
    public static final /* synthetic */ int[] $EnumSwitchMapping$2;
    public static final /* synthetic */ int[] $EnumSwitchMapping$3;
    public static final /* synthetic */ int[] $EnumSwitchMapping$4;
    public static final /* synthetic */ int[] $EnumSwitchMapping$5;
    public static final /* synthetic */ int[] $EnumSwitchMapping$6;
    public static final /* synthetic */ int[] $EnumSwitchMapping$7;
    public static final /* synthetic */ int[] $EnumSwitchMapping$8;
    public static final /* synthetic */ int[] $EnumSwitchMapping$9;
    public static final /* synthetic */ int[] $EnumSwitchMapping$10;
    public static final /* synthetic */ int[] $EnumSwitchMapping$11;
    public static final /* synthetic */ int[] $EnumSwitchMapping$12;
    public static final /* synthetic */ int[] $EnumSwitchMapping$13;
    public static final /* synthetic */ int[] $EnumSwitchMapping$14;
    public static final /* synthetic */ int[] $EnumSwitchMapping$15;
    public static final /* synthetic */ int[] $EnumSwitchMapping$16;
    public static final /* synthetic */ int[] $EnumSwitchMapping$17;
    public static final /* synthetic */ int[] $EnumSwitchMapping$18;
    public static final /* synthetic */ int[] $EnumSwitchMapping$19;
    public static final /* synthetic */ int[] $EnumSwitchMapping$20;
    public static final /* synthetic */ int[] $EnumSwitchMapping$21;

    static {
        $EnumSwitchMapping$0 = new int[AstVersion.values().length];
        AstSerializerImpl$WhenMappings.$EnumSwitchMapping$0[AstVersion.V0.ordinal()] = 1;
        $EnumSwitchMapping$1 = new int[AstVersion.values().length];
        AstSerializerImpl$WhenMappings.$EnumSwitchMapping$1[AstVersion.V0.ordinal()] = 1;
        $EnumSwitchMapping$2 = new int[AstVersion.values().length];
        AstSerializerImpl$WhenMappings.$EnumSwitchMapping$2[AstVersion.V0.ordinal()] = 1;
        $EnumSwitchMapping$3 = new int[AstVersion.values().length];
        AstSerializerImpl$WhenMappings.$EnumSwitchMapping$3[AstVersion.V0.ordinal()] = 1;
        $EnumSwitchMapping$4 = new int[GroupingStrategy.values().length];
        AstSerializerImpl$WhenMappings.$EnumSwitchMapping$4[GroupingStrategy.FULL.ordinal()] = 1;
        AstSerializerImpl$WhenMappings.$EnumSwitchMapping$4[GroupingStrategy.PARTIAL.ordinal()] = 2;
        $EnumSwitchMapping$5 = new int[AstVersion.values().length];
        AstSerializerImpl$WhenMappings.$EnumSwitchMapping$5[AstVersion.V0.ordinal()] = 1;
        $EnumSwitchMapping$6 = new int[AstVersion.values().length];
        AstSerializerImpl$WhenMappings.$EnumSwitchMapping$6[AstVersion.V0.ordinal()] = 1;
        $EnumSwitchMapping$7 = new int[AstVersion.values().length];
        AstSerializerImpl$WhenMappings.$EnumSwitchMapping$7[AstVersion.V0.ordinal()] = 1;
        $EnumSwitchMapping$8 = new int[SetQuantifier.values().length];
        AstSerializerImpl$WhenMappings.$EnumSwitchMapping$8[SetQuantifier.ALL.ordinal()] = 1;
        AstSerializerImpl$WhenMappings.$EnumSwitchMapping$8[SetQuantifier.DISTINCT.ordinal()] = 2;
        $EnumSwitchMapping$9 = new int[SetQuantifier.values().length];
        AstSerializerImpl$WhenMappings.$EnumSwitchMapping$9[SetQuantifier.ALL.ordinal()] = 1;
        AstSerializerImpl$WhenMappings.$EnumSwitchMapping$9[SetQuantifier.DISTINCT.ordinal()] = 2;
        $EnumSwitchMapping$10 = new int[AstVersion.values().length];
        AstSerializerImpl$WhenMappings.$EnumSwitchMapping$10[AstVersion.V0.ordinal()] = 1;
        $EnumSwitchMapping$11 = new int[JoinOp.values().length];
        AstSerializerImpl$WhenMappings.$EnumSwitchMapping$11[JoinOp.INNER.ordinal()] = 1;
        AstSerializerImpl$WhenMappings.$EnumSwitchMapping$11[JoinOp.LEFT.ordinal()] = 2;
        AstSerializerImpl$WhenMappings.$EnumSwitchMapping$11[JoinOp.RIGHT.ordinal()] = 3;
        AstSerializerImpl$WhenMappings.$EnumSwitchMapping$11[JoinOp.OUTER.ordinal()] = 4;
        $EnumSwitchMapping$12 = new int[AstVersion.values().length];
        AstSerializerImpl$WhenMappings.$EnumSwitchMapping$12[AstVersion.V0.ordinal()] = 1;
        $EnumSwitchMapping$13 = new int[AstVersion.values().length];
        AstSerializerImpl$WhenMappings.$EnumSwitchMapping$13[AstVersion.V0.ordinal()] = 1;
        $EnumSwitchMapping$14 = new int[AstVersion.values().length];
        AstSerializerImpl$WhenMappings.$EnumSwitchMapping$14[AstVersion.V0.ordinal()] = 1;
        $EnumSwitchMapping$15 = new int[NAryOp.values().length];
        AstSerializerImpl$WhenMappings.$EnumSwitchMapping$15[NAryOp.BETWEEN.ordinal()] = 1;
        AstSerializerImpl$WhenMappings.$EnumSwitchMapping$15[NAryOp.LIKE.ordinal()] = 2;
        AstSerializerImpl$WhenMappings.$EnumSwitchMapping$15[NAryOp.IN.ordinal()] = 3;
        $EnumSwitchMapping$16 = new int[NAryOp.values().length];
        AstSerializerImpl$WhenMappings.$EnumSwitchMapping$16[NAryOp.CALL.ordinal()] = 1;
        AstSerializerImpl$WhenMappings.$EnumSwitchMapping$16[NAryOp.UNION.ordinal()] = 2;
        AstSerializerImpl$WhenMappings.$EnumSwitchMapping$16[NAryOp.EXCEPT.ordinal()] = 3;
        AstSerializerImpl$WhenMappings.$EnumSwitchMapping$16[NAryOp.INTERSECT.ordinal()] = 4;
        AstSerializerImpl$WhenMappings.$EnumSwitchMapping$16[NAryOp.UNION_ALL.ordinal()] = 5;
        AstSerializerImpl$WhenMappings.$EnumSwitchMapping$16[NAryOp.EXCEPT_ALL.ordinal()] = 6;
        AstSerializerImpl$WhenMappings.$EnumSwitchMapping$16[NAryOp.INTERSECT_ALL.ordinal()] = 7;
        AstSerializerImpl$WhenMappings.$EnumSwitchMapping$16[NAryOp.LIKE.ordinal()] = 8;
        $EnumSwitchMapping$17 = new int[AstVersion.values().length];
        AstSerializerImpl$WhenMappings.$EnumSwitchMapping$17[AstVersion.V0.ordinal()] = 1;
        $EnumSwitchMapping$18 = new int[AstVersion.values().length];
        AstSerializerImpl$WhenMappings.$EnumSwitchMapping$18[AstVersion.V0.ordinal()] = 1;
        $EnumSwitchMapping$19 = new int[AstVersion.values().length];
        AstSerializerImpl$WhenMappings.$EnumSwitchMapping$19[AstVersion.V0.ordinal()] = 1;
        $EnumSwitchMapping$20 = new int[AstVersion.values().length];
        AstSerializerImpl$WhenMappings.$EnumSwitchMapping$20[AstVersion.V0.ordinal()] = 1;
        $EnumSwitchMapping$21 = new int[AstVersion.values().length];
        AstSerializerImpl$WhenMappings.$EnumSwitchMapping$21[AstVersion.V0.ordinal()] = 1;
    }
}

