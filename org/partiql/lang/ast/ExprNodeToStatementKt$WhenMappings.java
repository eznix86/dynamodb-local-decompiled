/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.partiql.lang.ast;

import kotlin.Metadata;
import org.partiql.lang.ast.CaseSensitivity;
import org.partiql.lang.ast.ConflictAction;
import org.partiql.lang.ast.GroupingStrategy;
import org.partiql.lang.ast.JoinOp;
import org.partiql.lang.ast.NAryOp;
import org.partiql.lang.ast.OrderingSpec;
import org.partiql.lang.ast.ReturningMapping;
import org.partiql.lang.ast.ScopeQualifier;
import org.partiql.lang.ast.SeqType;
import org.partiql.lang.ast.SetQuantifier;
import org.partiql.lang.ast.SqlDataType;
import org.partiql.lang.ast.TypedOp;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=3)
public final class ExprNodeToStatementKt$WhenMappings {
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

    static {
        $EnumSwitchMapping$0 = new int[NAryOp.values().length];
        ExprNodeToStatementKt$WhenMappings.$EnumSwitchMapping$0[NAryOp.ADD.ordinal()] = 1;
        ExprNodeToStatementKt$WhenMappings.$EnumSwitchMapping$0[NAryOp.SUB.ordinal()] = 2;
        ExprNodeToStatementKt$WhenMappings.$EnumSwitchMapping$0[NAryOp.MUL.ordinal()] = 3;
        ExprNodeToStatementKt$WhenMappings.$EnumSwitchMapping$0[NAryOp.DIV.ordinal()] = 4;
        ExprNodeToStatementKt$WhenMappings.$EnumSwitchMapping$0[NAryOp.MOD.ordinal()] = 5;
        ExprNodeToStatementKt$WhenMappings.$EnumSwitchMapping$0[NAryOp.EQ.ordinal()] = 6;
        ExprNodeToStatementKt$WhenMappings.$EnumSwitchMapping$0[NAryOp.LT.ordinal()] = 7;
        ExprNodeToStatementKt$WhenMappings.$EnumSwitchMapping$0[NAryOp.LTE.ordinal()] = 8;
        ExprNodeToStatementKt$WhenMappings.$EnumSwitchMapping$0[NAryOp.GT.ordinal()] = 9;
        ExprNodeToStatementKt$WhenMappings.$EnumSwitchMapping$0[NAryOp.GTE.ordinal()] = 10;
        ExprNodeToStatementKt$WhenMappings.$EnumSwitchMapping$0[NAryOp.NE.ordinal()] = 11;
        ExprNodeToStatementKt$WhenMappings.$EnumSwitchMapping$0[NAryOp.LIKE.ordinal()] = 12;
        ExprNodeToStatementKt$WhenMappings.$EnumSwitchMapping$0[NAryOp.BETWEEN.ordinal()] = 13;
        ExprNodeToStatementKt$WhenMappings.$EnumSwitchMapping$0[NAryOp.NOT.ordinal()] = 14;
        ExprNodeToStatementKt$WhenMappings.$EnumSwitchMapping$0[NAryOp.IN.ordinal()] = 15;
        ExprNodeToStatementKt$WhenMappings.$EnumSwitchMapping$0[NAryOp.AND.ordinal()] = 16;
        ExprNodeToStatementKt$WhenMappings.$EnumSwitchMapping$0[NAryOp.OR.ordinal()] = 17;
        ExprNodeToStatementKt$WhenMappings.$EnumSwitchMapping$0[NAryOp.STRING_CONCAT.ordinal()] = 18;
        ExprNodeToStatementKt$WhenMappings.$EnumSwitchMapping$0[NAryOp.CALL.ordinal()] = 19;
        ExprNodeToStatementKt$WhenMappings.$EnumSwitchMapping$0[NAryOp.INTERSECT.ordinal()] = 20;
        ExprNodeToStatementKt$WhenMappings.$EnumSwitchMapping$0[NAryOp.INTERSECT_ALL.ordinal()] = 21;
        ExprNodeToStatementKt$WhenMappings.$EnumSwitchMapping$0[NAryOp.EXCEPT.ordinal()] = 22;
        ExprNodeToStatementKt$WhenMappings.$EnumSwitchMapping$0[NAryOp.EXCEPT_ALL.ordinal()] = 23;
        ExprNodeToStatementKt$WhenMappings.$EnumSwitchMapping$0[NAryOp.UNION.ordinal()] = 24;
        ExprNodeToStatementKt$WhenMappings.$EnumSwitchMapping$0[NAryOp.UNION_ALL.ordinal()] = 25;
        $EnumSwitchMapping$1 = new int[TypedOp.values().length];
        ExprNodeToStatementKt$WhenMappings.$EnumSwitchMapping$1[TypedOp.CAST.ordinal()] = 1;
        ExprNodeToStatementKt$WhenMappings.$EnumSwitchMapping$1[TypedOp.IS.ordinal()] = 2;
        $EnumSwitchMapping$2 = new int[SetQuantifier.values().length];
        ExprNodeToStatementKt$WhenMappings.$EnumSwitchMapping$2[SetQuantifier.ALL.ordinal()] = 1;
        ExprNodeToStatementKt$WhenMappings.$EnumSwitchMapping$2[SetQuantifier.DISTINCT.ordinal()] = 2;
        $EnumSwitchMapping$3 = new int[SeqType.values().length];
        ExprNodeToStatementKt$WhenMappings.$EnumSwitchMapping$3[SeqType.LIST.ordinal()] = 1;
        ExprNodeToStatementKt$WhenMappings.$EnumSwitchMapping$3[SeqType.SEXP.ordinal()] = 2;
        ExprNodeToStatementKt$WhenMappings.$EnumSwitchMapping$3[SeqType.BAG.ordinal()] = 3;
        $EnumSwitchMapping$4 = new int[OrderingSpec.values().length];
        ExprNodeToStatementKt$WhenMappings.$EnumSwitchMapping$4[OrderingSpec.DESC.ordinal()] = 1;
        $EnumSwitchMapping$5 = new int[GroupingStrategy.values().length];
        ExprNodeToStatementKt$WhenMappings.$EnumSwitchMapping$5[GroupingStrategy.FULL.ordinal()] = 1;
        ExprNodeToStatementKt$WhenMappings.$EnumSwitchMapping$5[GroupingStrategy.PARTIAL.ordinal()] = 2;
        $EnumSwitchMapping$6 = new int[CaseSensitivity.values().length];
        ExprNodeToStatementKt$WhenMappings.$EnumSwitchMapping$6[CaseSensitivity.SENSITIVE.ordinal()] = 1;
        ExprNodeToStatementKt$WhenMappings.$EnumSwitchMapping$6[CaseSensitivity.INSENSITIVE.ordinal()] = 2;
        $EnumSwitchMapping$7 = new int[ScopeQualifier.values().length];
        ExprNodeToStatementKt$WhenMappings.$EnumSwitchMapping$7[ScopeQualifier.LEXICAL.ordinal()] = 1;
        ExprNodeToStatementKt$WhenMappings.$EnumSwitchMapping$7[ScopeQualifier.UNQUALIFIED.ordinal()] = 2;
        $EnumSwitchMapping$8 = new int[SetQuantifier.values().length];
        ExprNodeToStatementKt$WhenMappings.$EnumSwitchMapping$8[SetQuantifier.ALL.ordinal()] = 1;
        ExprNodeToStatementKt$WhenMappings.$EnumSwitchMapping$8[SetQuantifier.DISTINCT.ordinal()] = 2;
        $EnumSwitchMapping$9 = new int[JoinOp.values().length];
        ExprNodeToStatementKt$WhenMappings.$EnumSwitchMapping$9[JoinOp.INNER.ordinal()] = 1;
        ExprNodeToStatementKt$WhenMappings.$EnumSwitchMapping$9[JoinOp.LEFT.ordinal()] = 2;
        ExprNodeToStatementKt$WhenMappings.$EnumSwitchMapping$9[JoinOp.RIGHT.ordinal()] = 3;
        ExprNodeToStatementKt$WhenMappings.$EnumSwitchMapping$9[JoinOp.OUTER.ordinal()] = 4;
        $EnumSwitchMapping$10 = new int[ConflictAction.values().length];
        ExprNodeToStatementKt$WhenMappings.$EnumSwitchMapping$10[ConflictAction.DO_NOTHING.ordinal()] = 1;
        $EnumSwitchMapping$11 = new int[ReturningMapping.values().length];
        ExprNodeToStatementKt$WhenMappings.$EnumSwitchMapping$11[ReturningMapping.MODIFIED_OLD.ordinal()] = 1;
        ExprNodeToStatementKt$WhenMappings.$EnumSwitchMapping$11[ReturningMapping.MODIFIED_NEW.ordinal()] = 2;
        ExprNodeToStatementKt$WhenMappings.$EnumSwitchMapping$11[ReturningMapping.ALL_OLD.ordinal()] = 3;
        ExprNodeToStatementKt$WhenMappings.$EnumSwitchMapping$11[ReturningMapping.ALL_NEW.ordinal()] = 4;
        $EnumSwitchMapping$12 = new int[SqlDataType.values().length];
        ExprNodeToStatementKt$WhenMappings.$EnumSwitchMapping$12[SqlDataType.MISSING.ordinal()] = 1;
        ExprNodeToStatementKt$WhenMappings.$EnumSwitchMapping$12[SqlDataType.NULL.ordinal()] = 2;
        ExprNodeToStatementKt$WhenMappings.$EnumSwitchMapping$12[SqlDataType.BOOLEAN.ordinal()] = 3;
        ExprNodeToStatementKt$WhenMappings.$EnumSwitchMapping$12[SqlDataType.SMALLINT.ordinal()] = 4;
        ExprNodeToStatementKt$WhenMappings.$EnumSwitchMapping$12[SqlDataType.INTEGER.ordinal()] = 5;
        ExprNodeToStatementKt$WhenMappings.$EnumSwitchMapping$12[SqlDataType.FLOAT.ordinal()] = 6;
        ExprNodeToStatementKt$WhenMappings.$EnumSwitchMapping$12[SqlDataType.REAL.ordinal()] = 7;
        ExprNodeToStatementKt$WhenMappings.$EnumSwitchMapping$12[SqlDataType.DOUBLE_PRECISION.ordinal()] = 8;
        ExprNodeToStatementKt$WhenMappings.$EnumSwitchMapping$12[SqlDataType.DECIMAL.ordinal()] = 9;
        ExprNodeToStatementKt$WhenMappings.$EnumSwitchMapping$12[SqlDataType.NUMERIC.ordinal()] = 10;
        ExprNodeToStatementKt$WhenMappings.$EnumSwitchMapping$12[SqlDataType.TIMESTAMP.ordinal()] = 11;
        ExprNodeToStatementKt$WhenMappings.$EnumSwitchMapping$12[SqlDataType.CHARACTER.ordinal()] = 12;
        ExprNodeToStatementKt$WhenMappings.$EnumSwitchMapping$12[SqlDataType.CHARACTER_VARYING.ordinal()] = 13;
        ExprNodeToStatementKt$WhenMappings.$EnumSwitchMapping$12[SqlDataType.STRING.ordinal()] = 14;
        ExprNodeToStatementKt$WhenMappings.$EnumSwitchMapping$12[SqlDataType.SYMBOL.ordinal()] = 15;
        ExprNodeToStatementKt$WhenMappings.$EnumSwitchMapping$12[SqlDataType.CLOB.ordinal()] = 16;
        ExprNodeToStatementKt$WhenMappings.$EnumSwitchMapping$12[SqlDataType.BLOB.ordinal()] = 17;
        ExprNodeToStatementKt$WhenMappings.$EnumSwitchMapping$12[SqlDataType.STRUCT.ordinal()] = 18;
        ExprNodeToStatementKt$WhenMappings.$EnumSwitchMapping$12[SqlDataType.TUPLE.ordinal()] = 19;
        ExprNodeToStatementKt$WhenMappings.$EnumSwitchMapping$12[SqlDataType.LIST.ordinal()] = 20;
        ExprNodeToStatementKt$WhenMappings.$EnumSwitchMapping$12[SqlDataType.SEXP.ordinal()] = 21;
        ExprNodeToStatementKt$WhenMappings.$EnumSwitchMapping$12[SqlDataType.BAG.ordinal()] = 22;
        ExprNodeToStatementKt$WhenMappings.$EnumSwitchMapping$12[SqlDataType.DATE.ordinal()] = 23;
        ExprNodeToStatementKt$WhenMappings.$EnumSwitchMapping$12[SqlDataType.TIME.ordinal()] = 24;
        ExprNodeToStatementKt$WhenMappings.$EnumSwitchMapping$12[SqlDataType.TIME_WITH_TIME_ZONE.ordinal()] = 25;
    }
}

