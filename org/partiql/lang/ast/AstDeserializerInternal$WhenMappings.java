/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.partiql.lang.ast;

import kotlin.Metadata;
import org.partiql.lang.ast.AstVersion;
import org.partiql.lang.ast.NodeTag;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=3)
public final class AstDeserializerInternal$WhenMappings {
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
    public static final /* synthetic */ int[] $EnumSwitchMapping$22;
    public static final /* synthetic */ int[] $EnumSwitchMapping$23;
    public static final /* synthetic */ int[] $EnumSwitchMapping$24;
    public static final /* synthetic */ int[] $EnumSwitchMapping$25;
    public static final /* synthetic */ int[] $EnumSwitchMapping$26;
    public static final /* synthetic */ int[] $EnumSwitchMapping$27;
    public static final /* synthetic */ int[] $EnumSwitchMapping$28;

    static {
        $EnumSwitchMapping$0 = new int[NodeTag.values().length];
        AstDeserializerInternal$WhenMappings.$EnumSwitchMapping$0[NodeTag.LIT.ordinal()] = 1;
        AstDeserializerInternal$WhenMappings.$EnumSwitchMapping$0[NodeTag.MISSING.ordinal()] = 2;
        AstDeserializerInternal$WhenMappings.$EnumSwitchMapping$0[NodeTag.ID.ordinal()] = 3;
        AstDeserializerInternal$WhenMappings.$EnumSwitchMapping$0[NodeTag.SCOPE_QUALIFIER.ordinal()] = 4;
        AstDeserializerInternal$WhenMappings.$EnumSwitchMapping$0[NodeTag.SELECT.ordinal()] = 5;
        AstDeserializerInternal$WhenMappings.$EnumSwitchMapping$0[NodeTag.PIVOT.ordinal()] = 6;
        AstDeserializerInternal$WhenMappings.$EnumSwitchMapping$0[NodeTag.DATA_MANIPULATION.ordinal()] = 7;
        AstDeserializerInternal$WhenMappings.$EnumSwitchMapping$0[NodeTag.PATH.ordinal()] = 8;
        AstDeserializerInternal$WhenMappings.$EnumSwitchMapping$0[NodeTag.CALL_AGG.ordinal()] = 9;
        AstDeserializerInternal$WhenMappings.$EnumSwitchMapping$0[NodeTag.CALL_AGG_WILDCARD.ordinal()] = 10;
        AstDeserializerInternal$WhenMappings.$EnumSwitchMapping$0[NodeTag.STRUCT.ordinal()] = 11;
        AstDeserializerInternal$WhenMappings.$EnumSwitchMapping$0[NodeTag.PARAMETER.ordinal()] = 12;
        AstDeserializerInternal$WhenMappings.$EnumSwitchMapping$0[NodeTag.LIST.ordinal()] = 13;
        AstDeserializerInternal$WhenMappings.$EnumSwitchMapping$0[NodeTag.BAG.ordinal()] = 14;
        AstDeserializerInternal$WhenMappings.$EnumSwitchMapping$0[NodeTag.SEXP.ordinal()] = 15;
        AstDeserializerInternal$WhenMappings.$EnumSwitchMapping$0[NodeTag.SIMPLE_CASE.ordinal()] = 16;
        AstDeserializerInternal$WhenMappings.$EnumSwitchMapping$0[NodeTag.SEARCHED_CASE.ordinal()] = 17;
        AstDeserializerInternal$WhenMappings.$EnumSwitchMapping$0[NodeTag.NARY_NOT.ordinal()] = 18;
        AstDeserializerInternal$WhenMappings.$EnumSwitchMapping$0[NodeTag.NARY_ADD.ordinal()] = 19;
        AstDeserializerInternal$WhenMappings.$EnumSwitchMapping$0[NodeTag.NARY_SUB.ordinal()] = 20;
        AstDeserializerInternal$WhenMappings.$EnumSwitchMapping$0[NodeTag.NARY_MUL.ordinal()] = 21;
        AstDeserializerInternal$WhenMappings.$EnumSwitchMapping$0[NodeTag.NARY_DIV.ordinal()] = 22;
        AstDeserializerInternal$WhenMappings.$EnumSwitchMapping$0[NodeTag.NARY_MOD.ordinal()] = 23;
        AstDeserializerInternal$WhenMappings.$EnumSwitchMapping$0[NodeTag.NARY_GT.ordinal()] = 24;
        AstDeserializerInternal$WhenMappings.$EnumSwitchMapping$0[NodeTag.NARY_GTE.ordinal()] = 25;
        AstDeserializerInternal$WhenMappings.$EnumSwitchMapping$0[NodeTag.NARY_LT.ordinal()] = 26;
        AstDeserializerInternal$WhenMappings.$EnumSwitchMapping$0[NodeTag.NARY_LTE.ordinal()] = 27;
        AstDeserializerInternal$WhenMappings.$EnumSwitchMapping$0[NodeTag.NARY_EQ.ordinal()] = 28;
        AstDeserializerInternal$WhenMappings.$EnumSwitchMapping$0[NodeTag.NARY_NE.ordinal()] = 29;
        AstDeserializerInternal$WhenMappings.$EnumSwitchMapping$0[NodeTag.NARY_IN.ordinal()] = 30;
        AstDeserializerInternal$WhenMappings.$EnumSwitchMapping$0[NodeTag.NARY_NOT_IN.ordinal()] = 31;
        AstDeserializerInternal$WhenMappings.$EnumSwitchMapping$0[NodeTag.NARY_AND.ordinal()] = 32;
        AstDeserializerInternal$WhenMappings.$EnumSwitchMapping$0[NodeTag.NARY_OR.ordinal()] = 33;
        AstDeserializerInternal$WhenMappings.$EnumSwitchMapping$0[NodeTag.NARY_LIKE.ordinal()] = 34;
        AstDeserializerInternal$WhenMappings.$EnumSwitchMapping$0[NodeTag.NARY_NOT_LIKE.ordinal()] = 35;
        AstDeserializerInternal$WhenMappings.$EnumSwitchMapping$0[NodeTag.NARY_BETWEEN.ordinal()] = 36;
        AstDeserializerInternal$WhenMappings.$EnumSwitchMapping$0[NodeTag.NARY_NOT_BETWEEN.ordinal()] = 37;
        AstDeserializerInternal$WhenMappings.$EnumSwitchMapping$0[NodeTag.NARY_STRING_CONCAT.ordinal()] = 38;
        AstDeserializerInternal$WhenMappings.$EnumSwitchMapping$0[NodeTag.NARY_CALL.ordinal()] = 39;
        AstDeserializerInternal$WhenMappings.$EnumSwitchMapping$0[NodeTag.NARY_UNION.ordinal()] = 40;
        AstDeserializerInternal$WhenMappings.$EnumSwitchMapping$0[NodeTag.NARY_UNION_ALL.ordinal()] = 41;
        AstDeserializerInternal$WhenMappings.$EnumSwitchMapping$0[NodeTag.NARY_EXCEPT.ordinal()] = 42;
        AstDeserializerInternal$WhenMappings.$EnumSwitchMapping$0[NodeTag.NARY_EXCEPT_ALL.ordinal()] = 43;
        AstDeserializerInternal$WhenMappings.$EnumSwitchMapping$0[NodeTag.NARY_INTERSECT.ordinal()] = 44;
        AstDeserializerInternal$WhenMappings.$EnumSwitchMapping$0[NodeTag.NARY_INTERSECT_ALL.ordinal()] = 45;
        AstDeserializerInternal$WhenMappings.$EnumSwitchMapping$0[NodeTag.TYPED_IS.ordinal()] = 46;
        AstDeserializerInternal$WhenMappings.$EnumSwitchMapping$0[NodeTag.TYPED_IS_NOT.ordinal()] = 47;
        AstDeserializerInternal$WhenMappings.$EnumSwitchMapping$0[NodeTag.TYPED_CAST.ordinal()] = 48;
        AstDeserializerInternal$WhenMappings.$EnumSwitchMapping$0[NodeTag.CREATE.ordinal()] = 49;
        AstDeserializerInternal$WhenMappings.$EnumSwitchMapping$0[NodeTag.DROP_INDEX.ordinal()] = 50;
        AstDeserializerInternal$WhenMappings.$EnumSwitchMapping$0[NodeTag.DROP_TABLE.ordinal()] = 51;
        AstDeserializerInternal$WhenMappings.$EnumSwitchMapping$0[NodeTag.META.ordinal()] = 52;
        AstDeserializerInternal$WhenMappings.$EnumSwitchMapping$0[NodeTag.INDEX.ordinal()] = 53;
        AstDeserializerInternal$WhenMappings.$EnumSwitchMapping$0[NodeTag.TABLE.ordinal()] = 54;
        AstDeserializerInternal$WhenMappings.$EnumSwitchMapping$0[NodeTag.KEYS.ordinal()] = 55;
        AstDeserializerInternal$WhenMappings.$EnumSwitchMapping$0[NodeTag.INSERT_VALUE.ordinal()] = 56;
        AstDeserializerInternal$WhenMappings.$EnumSwitchMapping$0[NodeTag.INSERT.ordinal()] = 57;
        AstDeserializerInternal$WhenMappings.$EnumSwitchMapping$0[NodeTag.ASSIGNMENT.ordinal()] = 58;
        AstDeserializerInternal$WhenMappings.$EnumSwitchMapping$0[NodeTag.REMOVE.ordinal()] = 59;
        AstDeserializerInternal$WhenMappings.$EnumSwitchMapping$0[NodeTag.SET.ordinal()] = 60;
        AstDeserializerInternal$WhenMappings.$EnumSwitchMapping$0[NodeTag.DELETE.ordinal()] = 61;
        AstDeserializerInternal$WhenMappings.$EnumSwitchMapping$0[NodeTag.PROJECT.ordinal()] = 62;
        AstDeserializerInternal$WhenMappings.$EnumSwitchMapping$0[NodeTag.PROJECT_DISTINCT.ordinal()] = 63;
        AstDeserializerInternal$WhenMappings.$EnumSwitchMapping$0[NodeTag.PROJECT_ALL.ordinal()] = 64;
        AstDeserializerInternal$WhenMappings.$EnumSwitchMapping$0[NodeTag.FROM.ordinal()] = 65;
        AstDeserializerInternal$WhenMappings.$EnumSwitchMapping$0[NodeTag.WHERE.ordinal()] = 66;
        AstDeserializerInternal$WhenMappings.$EnumSwitchMapping$0[NodeTag.HAVING.ordinal()] = 67;
        AstDeserializerInternal$WhenMappings.$EnumSwitchMapping$0[NodeTag.GROUP.ordinal()] = 68;
        AstDeserializerInternal$WhenMappings.$EnumSwitchMapping$0[NodeTag.GROUP_PARTIAL.ordinal()] = 69;
        AstDeserializerInternal$WhenMappings.$EnumSwitchMapping$0[NodeTag.BY.ordinal()] = 70;
        AstDeserializerInternal$WhenMappings.$EnumSwitchMapping$0[NodeTag.NAME.ordinal()] = 71;
        AstDeserializerInternal$WhenMappings.$EnumSwitchMapping$0[NodeTag.LIMIT.ordinal()] = 72;
        AstDeserializerInternal$WhenMappings.$EnumSwitchMapping$0[NodeTag.MEMBER.ordinal()] = 73;
        AstDeserializerInternal$WhenMappings.$EnumSwitchMapping$0[NodeTag.AS.ordinal()] = 74;
        AstDeserializerInternal$WhenMappings.$EnumSwitchMapping$0[NodeTag.AT.ordinal()] = 75;
        AstDeserializerInternal$WhenMappings.$EnumSwitchMapping$0[NodeTag.UNPIVOT.ordinal()] = 76;
        AstDeserializerInternal$WhenMappings.$EnumSwitchMapping$0[NodeTag.INNER_JOIN.ordinal()] = 77;
        AstDeserializerInternal$WhenMappings.$EnumSwitchMapping$0[NodeTag.LEFT_JOIN.ordinal()] = 78;
        AstDeserializerInternal$WhenMappings.$EnumSwitchMapping$0[NodeTag.OUTER_JOIN.ordinal()] = 79;
        AstDeserializerInternal$WhenMappings.$EnumSwitchMapping$0[NodeTag.RIGHT_JOIN.ordinal()] = 80;
        AstDeserializerInternal$WhenMappings.$EnumSwitchMapping$0[NodeTag.CASE_INSENSITIVE.ordinal()] = 81;
        AstDeserializerInternal$WhenMappings.$EnumSwitchMapping$0[NodeTag.CASE_SENSITIVE.ordinal()] = 82;
        AstDeserializerInternal$WhenMappings.$EnumSwitchMapping$0[NodeTag.VALUE.ordinal()] = 83;
        AstDeserializerInternal$WhenMappings.$EnumSwitchMapping$0[NodeTag.WHEN.ordinal()] = 84;
        AstDeserializerInternal$WhenMappings.$EnumSwitchMapping$0[NodeTag.ELSE.ordinal()] = 85;
        AstDeserializerInternal$WhenMappings.$EnumSwitchMapping$0[NodeTag.TYPE.ordinal()] = 86;
        $EnumSwitchMapping$1 = new int[AstVersion.values().length];
        AstDeserializerInternal$WhenMappings.$EnumSwitchMapping$1[AstVersion.V0.ordinal()] = 1;
        $EnumSwitchMapping$2 = new int[AstVersion.values().length];
        AstDeserializerInternal$WhenMappings.$EnumSwitchMapping$2[AstVersion.V0.ordinal()] = 1;
        $EnumSwitchMapping$3 = new int[AstVersion.values().length];
        AstDeserializerInternal$WhenMappings.$EnumSwitchMapping$3[AstVersion.V0.ordinal()] = 1;
        $EnumSwitchMapping$4 = new int[AstVersion.values().length];
        AstDeserializerInternal$WhenMappings.$EnumSwitchMapping$4[AstVersion.V0.ordinal()] = 1;
        $EnumSwitchMapping$5 = new int[AstVersion.values().length];
        AstDeserializerInternal$WhenMappings.$EnumSwitchMapping$5[AstVersion.V0.ordinal()] = 1;
        $EnumSwitchMapping$6 = new int[AstVersion.values().length];
        AstDeserializerInternal$WhenMappings.$EnumSwitchMapping$6[AstVersion.V0.ordinal()] = 1;
        $EnumSwitchMapping$7 = new int[AstVersion.values().length];
        AstDeserializerInternal$WhenMappings.$EnumSwitchMapping$7[AstVersion.V0.ordinal()] = 1;
        $EnumSwitchMapping$8 = new int[AstVersion.values().length];
        AstDeserializerInternal$WhenMappings.$EnumSwitchMapping$8[AstVersion.V0.ordinal()] = 1;
        $EnumSwitchMapping$9 = new int[NodeTag.values().length];
        AstDeserializerInternal$WhenMappings.$EnumSwitchMapping$9[NodeTag.TABLE.ordinal()] = 1;
        AstDeserializerInternal$WhenMappings.$EnumSwitchMapping$9[NodeTag.INDEX.ordinal()] = 2;
        $EnumSwitchMapping$10 = new int[NodeTag.values().length];
        $EnumSwitchMapping$11 = new int[AstVersion.values().length];
        AstDeserializerInternal$WhenMappings.$EnumSwitchMapping$11[AstVersion.V0.ordinal()] = 1;
        $EnumSwitchMapping$12 = new int[NodeTag.values().length];
        AstDeserializerInternal$WhenMappings.$EnumSwitchMapping$12[NodeTag.INSERT.ordinal()] = 1;
        AstDeserializerInternal$WhenMappings.$EnumSwitchMapping$12[NodeTag.INSERT_VALUE.ordinal()] = 2;
        AstDeserializerInternal$WhenMappings.$EnumSwitchMapping$12[NodeTag.SET.ordinal()] = 3;
        AstDeserializerInternal$WhenMappings.$EnumSwitchMapping$12[NodeTag.REMOVE.ordinal()] = 4;
        AstDeserializerInternal$WhenMappings.$EnumSwitchMapping$12[NodeTag.DELETE.ordinal()] = 5;
        $EnumSwitchMapping$13 = new int[NodeTag.values().length];
        AstDeserializerInternal$WhenMappings.$EnumSwitchMapping$13[NodeTag.PIVOT.ordinal()] = 1;
        AstDeserializerInternal$WhenMappings.$EnumSwitchMapping$13[NodeTag.SELECT.ordinal()] = 2;
        $EnumSwitchMapping$14 = new int[AstVersion.values().length];
        AstDeserializerInternal$WhenMappings.$EnumSwitchMapping$14[AstVersion.V0.ordinal()] = 1;
        $EnumSwitchMapping$15 = new int[NodeTag.values().length];
        AstDeserializerInternal$WhenMappings.$EnumSwitchMapping$15[NodeTag.GROUP.ordinal()] = 1;
        $EnumSwitchMapping$16 = new int[NodeTag.values().length];
        AstDeserializerInternal$WhenMappings.$EnumSwitchMapping$16[NodeTag.VALUE.ordinal()] = 1;
        AstDeserializerInternal$WhenMappings.$EnumSwitchMapping$16[NodeTag.LIST.ordinal()] = 2;
        $EnumSwitchMapping$17 = new int[NodeTag.values().length];
        AstDeserializerInternal$WhenMappings.$EnumSwitchMapping$17[NodeTag.AS.ordinal()] = 1;
        AstDeserializerInternal$WhenMappings.$EnumSwitchMapping$17[NodeTag.PROJECT_ALL.ordinal()] = 2;
        $EnumSwitchMapping$18 = new int[AstVersion.values().length];
        AstDeserializerInternal$WhenMappings.$EnumSwitchMapping$18[AstVersion.V0.ordinal()] = 1;
        $EnumSwitchMapping$19 = new int[NodeTag.values().length];
        AstDeserializerInternal$WhenMappings.$EnumSwitchMapping$19[NodeTag.AT.ordinal()] = 1;
        AstDeserializerInternal$WhenMappings.$EnumSwitchMapping$19[NodeTag.AS.ordinal()] = 2;
        AstDeserializerInternal$WhenMappings.$EnumSwitchMapping$19[NodeTag.BY.ordinal()] = 3;
        AstDeserializerInternal$WhenMappings.$EnumSwitchMapping$19[NodeTag.UNPIVOT.ordinal()] = 4;
        AstDeserializerInternal$WhenMappings.$EnumSwitchMapping$19[NodeTag.INNER_JOIN.ordinal()] = 5;
        AstDeserializerInternal$WhenMappings.$EnumSwitchMapping$19[NodeTag.LEFT_JOIN.ordinal()] = 6;
        AstDeserializerInternal$WhenMappings.$EnumSwitchMapping$19[NodeTag.RIGHT_JOIN.ordinal()] = 7;
        AstDeserializerInternal$WhenMappings.$EnumSwitchMapping$19[NodeTag.OUTER_JOIN.ordinal()] = 8;
        $EnumSwitchMapping$20 = new int[NodeTag.values().length];
        AstDeserializerInternal$WhenMappings.$EnumSwitchMapping$20[NodeTag.INNER_JOIN.ordinal()] = 1;
        AstDeserializerInternal$WhenMappings.$EnumSwitchMapping$20[NodeTag.LEFT_JOIN.ordinal()] = 2;
        AstDeserializerInternal$WhenMappings.$EnumSwitchMapping$20[NodeTag.RIGHT_JOIN.ordinal()] = 3;
        AstDeserializerInternal$WhenMappings.$EnumSwitchMapping$20[NodeTag.OUTER_JOIN.ordinal()] = 4;
        $EnumSwitchMapping$21 = new int[NodeTag.values().length];
        AstDeserializerInternal$WhenMappings.$EnumSwitchMapping$21[NodeTag.AS.ordinal()] = 1;
        $EnumSwitchMapping$22 = new int[AstVersion.values().length];
        AstDeserializerInternal$WhenMappings.$EnumSwitchMapping$22[AstVersion.V0.ordinal()] = 1;
        $EnumSwitchMapping$23 = new int[AstVersion.values().length];
        AstDeserializerInternal$WhenMappings.$EnumSwitchMapping$23[AstVersion.V0.ordinal()] = 1;
        $EnumSwitchMapping$24 = new int[NodeTag.values().length];
        AstDeserializerInternal$WhenMappings.$EnumSwitchMapping$24[NodeTag.CASE_INSENSITIVE.ordinal()] = 1;
        AstDeserializerInternal$WhenMappings.$EnumSwitchMapping$24[NodeTag.CASE_SENSITIVE.ordinal()] = 2;
        $EnumSwitchMapping$25 = new int[NodeTag.values().length];
        AstDeserializerInternal$WhenMappings.$EnumSwitchMapping$25[NodeTag.NARY_MUL.ordinal()] = 1;
        $EnumSwitchMapping$26 = new int[AstVersion.values().length];
        AstDeserializerInternal$WhenMappings.$EnumSwitchMapping$26[AstVersion.V0.ordinal()] = 1;
        $EnumSwitchMapping$27 = new int[AstVersion.values().length];
        AstDeserializerInternal$WhenMappings.$EnumSwitchMapping$27[AstVersion.V0.ordinal()] = 1;
        $EnumSwitchMapping$28 = new int[NodeTag.values().length];
        AstDeserializerInternal$WhenMappings.$EnumSwitchMapping$28[NodeTag.TYPE.ordinal()] = 1;
    }
}
