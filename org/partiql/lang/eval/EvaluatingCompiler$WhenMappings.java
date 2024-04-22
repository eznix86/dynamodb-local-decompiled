/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.partiql.lang.eval;

import kotlin.Metadata;
import org.partiql.lang.ast.JoinOp;
import org.partiql.lang.ast.NAryOp;
import org.partiql.lang.ast.ScopeQualifier;
import org.partiql.lang.ast.SeqType;
import org.partiql.lang.ast.SetQuantifier;
import org.partiql.lang.ast.SqlDataType;
import org.partiql.lang.ast.TypedOp;
import org.partiql.lang.eval.ExprValueType;
import org.partiql.lang.eval.ExpressionContext;
import org.partiql.lang.eval.JoinExpansion;
import org.partiql.lang.eval.ProjectionIterationBehavior;
import org.partiql.lang.eval.UndefinedVariableBehavior;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=3)
public final class EvaluatingCompiler$WhenMappings {
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
        EvaluatingCompiler$WhenMappings.$EnumSwitchMapping$0[NAryOp.ADD.ordinal()] = 1;
        EvaluatingCompiler$WhenMappings.$EnumSwitchMapping$0[NAryOp.SUB.ordinal()] = 2;
        EvaluatingCompiler$WhenMappings.$EnumSwitchMapping$0[NAryOp.MUL.ordinal()] = 3;
        EvaluatingCompiler$WhenMappings.$EnumSwitchMapping$0[NAryOp.DIV.ordinal()] = 4;
        EvaluatingCompiler$WhenMappings.$EnumSwitchMapping$0[NAryOp.MOD.ordinal()] = 5;
        EvaluatingCompiler$WhenMappings.$EnumSwitchMapping$0[NAryOp.EQ.ordinal()] = 6;
        EvaluatingCompiler$WhenMappings.$EnumSwitchMapping$0[NAryOp.NE.ordinal()] = 7;
        EvaluatingCompiler$WhenMappings.$EnumSwitchMapping$0[NAryOp.LT.ordinal()] = 8;
        EvaluatingCompiler$WhenMappings.$EnumSwitchMapping$0[NAryOp.LTE.ordinal()] = 9;
        EvaluatingCompiler$WhenMappings.$EnumSwitchMapping$0[NAryOp.GT.ordinal()] = 10;
        EvaluatingCompiler$WhenMappings.$EnumSwitchMapping$0[NAryOp.GTE.ordinal()] = 11;
        EvaluatingCompiler$WhenMappings.$EnumSwitchMapping$0[NAryOp.BETWEEN.ordinal()] = 12;
        EvaluatingCompiler$WhenMappings.$EnumSwitchMapping$0[NAryOp.LIKE.ordinal()] = 13;
        EvaluatingCompiler$WhenMappings.$EnumSwitchMapping$0[NAryOp.IN.ordinal()] = 14;
        EvaluatingCompiler$WhenMappings.$EnumSwitchMapping$0[NAryOp.NOT.ordinal()] = 15;
        EvaluatingCompiler$WhenMappings.$EnumSwitchMapping$0[NAryOp.AND.ordinal()] = 16;
        EvaluatingCompiler$WhenMappings.$EnumSwitchMapping$0[NAryOp.OR.ordinal()] = 17;
        EvaluatingCompiler$WhenMappings.$EnumSwitchMapping$0[NAryOp.STRING_CONCAT.ordinal()] = 18;
        EvaluatingCompiler$WhenMappings.$EnumSwitchMapping$0[NAryOp.CALL.ordinal()] = 19;
        EvaluatingCompiler$WhenMappings.$EnumSwitchMapping$0[NAryOp.INTERSECT.ordinal()] = 20;
        EvaluatingCompiler$WhenMappings.$EnumSwitchMapping$0[NAryOp.INTERSECT_ALL.ordinal()] = 21;
        EvaluatingCompiler$WhenMappings.$EnumSwitchMapping$0[NAryOp.EXCEPT.ordinal()] = 22;
        EvaluatingCompiler$WhenMappings.$EnumSwitchMapping$0[NAryOp.EXCEPT_ALL.ordinal()] = 23;
        EvaluatingCompiler$WhenMappings.$EnumSwitchMapping$0[NAryOp.UNION.ordinal()] = 24;
        EvaluatingCompiler$WhenMappings.$EnumSwitchMapping$0[NAryOp.UNION_ALL.ordinal()] = 25;
        $EnumSwitchMapping$1 = new int[UndefinedVariableBehavior.values().length];
        EvaluatingCompiler$WhenMappings.$EnumSwitchMapping$1[UndefinedVariableBehavior.ERROR.ordinal()] = 1;
        EvaluatingCompiler$WhenMappings.$EnumSwitchMapping$1[UndefinedVariableBehavior.MISSING.ordinal()] = 2;
        $EnumSwitchMapping$2 = new int[ScopeQualifier.values().length];
        EvaluatingCompiler$WhenMappings.$EnumSwitchMapping$2[ScopeQualifier.UNQUALIFIED.ordinal()] = 1;
        EvaluatingCompiler$WhenMappings.$EnumSwitchMapping$2[ScopeQualifier.LEXICAL.ordinal()] = 2;
        $EnumSwitchMapping$3 = new int[SqlDataType.values().length];
        EvaluatingCompiler$WhenMappings.$EnumSwitchMapping$3[SqlDataType.NULL.ordinal()] = 1;
        $EnumSwitchMapping$4 = new int[TypedOp.values().length];
        EvaluatingCompiler$WhenMappings.$EnumSwitchMapping$4[TypedOp.IS.ordinal()] = 1;
        EvaluatingCompiler$WhenMappings.$EnumSwitchMapping$4[TypedOp.CAST.ordinal()] = 2;
        $EnumSwitchMapping$5 = new int[SeqType.values().length];
        EvaluatingCompiler$WhenMappings.$EnumSwitchMapping$5[SeqType.SEXP.ordinal()] = 1;
        EvaluatingCompiler$WhenMappings.$EnumSwitchMapping$5[SeqType.LIST.ordinal()] = 2;
        EvaluatingCompiler$WhenMappings.$EnumSwitchMapping$5[SeqType.BAG.ordinal()] = 3;
        $EnumSwitchMapping$6 = new int[ExprValueType.values().length];
        EvaluatingCompiler$WhenMappings.$EnumSwitchMapping$6[ExprValueType.BAG.ordinal()] = 1;
        $EnumSwitchMapping$7 = new int[SetQuantifier.values().length];
        EvaluatingCompiler$WhenMappings.$EnumSwitchMapping$7[SetQuantifier.DISTINCT.ordinal()] = 1;
        EvaluatingCompiler$WhenMappings.$EnumSwitchMapping$7[SetQuantifier.ALL.ordinal()] = 2;
        $EnumSwitchMapping$8 = new int[ProjectionIterationBehavior.values().length];
        EvaluatingCompiler$WhenMappings.$EnumSwitchMapping$8[ProjectionIterationBehavior.FILTER_MISSING.ordinal()] = 1;
        EvaluatingCompiler$WhenMappings.$EnumSwitchMapping$8[ProjectionIterationBehavior.UNFILTERED.ordinal()] = 2;
        $EnumSwitchMapping$9 = new int[ExpressionContext.values().length];
        EvaluatingCompiler$WhenMappings.$EnumSwitchMapping$9[ExpressionContext.AGG_ARG.ordinal()] = 1;
        EvaluatingCompiler$WhenMappings.$EnumSwitchMapping$9[ExpressionContext.NORMAL.ordinal()] = 2;
        EvaluatingCompiler$WhenMappings.$EnumSwitchMapping$9[ExpressionContext.SELECT_LIST.ordinal()] = 3;
        $EnumSwitchMapping$10 = new int[JoinOp.values().length];
        EvaluatingCompiler$WhenMappings.$EnumSwitchMapping$10[JoinOp.INNER.ordinal()] = 1;
        EvaluatingCompiler$WhenMappings.$EnumSwitchMapping$10[JoinOp.LEFT.ordinal()] = 2;
        EvaluatingCompiler$WhenMappings.$EnumSwitchMapping$10[JoinOp.RIGHT.ordinal()] = 3;
        EvaluatingCompiler$WhenMappings.$EnumSwitchMapping$10[JoinOp.OUTER.ordinal()] = 4;
        $EnumSwitchMapping$11 = new int[JoinExpansion.values().length];
        EvaluatingCompiler$WhenMappings.$EnumSwitchMapping$11[JoinExpansion.OUTER.ordinal()] = 1;
        EvaluatingCompiler$WhenMappings.$EnumSwitchMapping$11[JoinExpansion.INNER.ordinal()] = 2;
        $EnumSwitchMapping$12 = new int[ProjectionIterationBehavior.values().length];
        EvaluatingCompiler$WhenMappings.$EnumSwitchMapping$12[ProjectionIterationBehavior.FILTER_MISSING.ordinal()] = 1;
        EvaluatingCompiler$WhenMappings.$EnumSwitchMapping$12[ProjectionIterationBehavior.UNFILTERED.ordinal()] = 2;
    }
}

