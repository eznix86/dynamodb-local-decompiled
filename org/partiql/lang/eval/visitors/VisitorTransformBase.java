/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package org.partiql.lang.eval.visitors;

import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.partiql.lang.domains.PartiqlAst;
import org.partiql.lang.util.ThreadInterruptUtilsKt;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b&\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\u0005\u001a\u00020\bH\u0016J\u000e\u0010\t\u001a\u00020\b2\u0006\u0010\u0005\u001a\u00020\n\u00a8\u0006\u000b"}, d2={"Lorg/partiql/lang/eval/visitors/VisitorTransformBase;", "Lorg/partiql/lang/domains/PartiqlAst$VisitorTransform;", "()V", "transformDataManipulationEvaluationOrder", "Lorg/partiql/lang/domains/PartiqlAst$Statement;", "node", "Lorg/partiql/lang/domains/PartiqlAst$Statement$Dml;", "transformExpr", "Lorg/partiql/lang/domains/PartiqlAst$Expr;", "transformExprSelectEvaluationOrder", "Lorg/partiql/lang/domains/PartiqlAst$Expr$Select;", "lang"})
public abstract class VisitorTransformBase
extends PartiqlAst.VisitorTransform {
    @Override
    @NotNull
    public PartiqlAst.Expr transformExpr(@NotNull PartiqlAst.Expr node) {
        Intrinsics.checkParameterIsNotNull(node, "node");
        ThreadInterruptUtilsKt.checkThreadInterrupted();
        return super.transformExpr(node);
    }

    @NotNull
    public final PartiqlAst.Expr transformExprSelectEvaluationOrder(@NotNull PartiqlAst.Expr.Select node) {
        Intrinsics.checkParameterIsNotNull(node, "node");
        PartiqlAst.FromSource from2 = this.transformExprSelect_from(node);
        PartiqlAst.Let fromLet2 = this.transformExprSelect_fromLet(node);
        PartiqlAst.Expr where2 = this.transformExprSelect_where(node);
        PartiqlAst.GroupBy group2 = this.transformExprSelect_group(node);
        PartiqlAst.Expr having2 = this.transformExprSelect_having(node);
        PartiqlAst.SetQuantifier setq2 = this.transformExprSelect_setq(node);
        PartiqlAst.Projection project2 = this.transformExprSelect_project(node);
        PartiqlAst.OrderBy order2 = this.transformExprSelect_order(node);
        PartiqlAst.Expr limit2 = this.transformExprSelect_limit(node);
        Map<String, Object> metas = this.transformExprSelect_metas(node);
        return (PartiqlAst.Expr)PartiqlAst.Companion.build((Function1)new Function1<PartiqlAst.Builder, PartiqlAst.Expr.Select>(setq2, project2, from2, fromLet2, where2, group2, having2, order2, limit2, metas){
            final /* synthetic */ PartiqlAst.SetQuantifier $setq;
            final /* synthetic */ PartiqlAst.Projection $project;
            final /* synthetic */ PartiqlAst.FromSource $from;
            final /* synthetic */ PartiqlAst.Let $fromLet;
            final /* synthetic */ PartiqlAst.Expr $where;
            final /* synthetic */ PartiqlAst.GroupBy $group;
            final /* synthetic */ PartiqlAst.Expr $having;
            final /* synthetic */ PartiqlAst.OrderBy $order;
            final /* synthetic */ PartiqlAst.Expr $limit;
            final /* synthetic */ Map $metas;

            @NotNull
            public final PartiqlAst.Expr.Select invoke(@NotNull PartiqlAst.Builder $this$build) {
                Intrinsics.checkParameterIsNotNull($this$build, "$receiver");
                return new PartiqlAst.Expr.Select(this.$setq, this.$project, this.$from, this.$fromLet, this.$where, this.$group, this.$having, this.$order, this.$limit, this.$metas);
            }
            {
                this.$setq = setQuantifier;
                this.$project = projection;
                this.$from = fromSource;
                this.$fromLet = let2;
                this.$where = expr;
                this.$group = groupBy2;
                this.$having = expr2;
                this.$order = orderBy;
                this.$limit = expr3;
                this.$metas = map2;
                super(1);
            }
        });
    }

    @NotNull
    public final PartiqlAst.Statement transformDataManipulationEvaluationOrder(@NotNull PartiqlAst.Statement.Dml node) {
        PartiqlAst.ReturningExpr returningExpr;
        PartiqlAst.Expr expr;
        PartiqlAst.FromSource fromSource;
        boolean bl;
        Intrinsics.checkParameterIsNotNull(node, "node");
        PartiqlAst.FromSource fromSource2 = node.getFrom();
        if (fromSource2 != null) {
            PartiqlAst.FromSource fromSource3 = fromSource2;
            boolean bl2 = false;
            bl = false;
            PartiqlAst.FromSource it = fromSource3;
            boolean bl3 = false;
            fromSource = this.transformFromSource(it);
        } else {
            fromSource = null;
        }
        PartiqlAst.FromSource from2 = fromSource;
        PartiqlAst.Expr expr2 = node.getWhere();
        if (expr2 != null) {
            PartiqlAst.Expr expr3 = expr2;
            bl = false;
            boolean it = false;
            PartiqlAst.Expr it2 = expr3;
            boolean bl4 = false;
            expr = this.transformStatementDml_where(node);
        } else {
            expr = null;
        }
        PartiqlAst.Expr where2 = expr;
        PartiqlAst.DmlOpList dmlOperations = this.transformDmlOpList(node.getOperations());
        PartiqlAst.ReturningExpr returningExpr2 = node.getReturning();
        if (returningExpr2 != null) {
            PartiqlAst.ReturningExpr it = returningExpr2;
            boolean bl5 = false;
            boolean bl6 = false;
            PartiqlAst.ReturningExpr it3 = it;
            boolean bl7 = false;
            returningExpr = this.transformReturningExpr(it3);
        } else {
            returningExpr = null;
        }
        PartiqlAst.ReturningExpr returning2 = returningExpr;
        Map metas = this.transformMetas(node.getMetas());
        return (PartiqlAst.Statement)PartiqlAst.Companion.build((Function1)new Function1<PartiqlAst.Builder, PartiqlAst.Statement.Dml>(dmlOperations, from2, where2, returning2, metas){
            final /* synthetic */ PartiqlAst.DmlOpList $dmlOperations;
            final /* synthetic */ PartiqlAst.FromSource $from;
            final /* synthetic */ PartiqlAst.Expr $where;
            final /* synthetic */ PartiqlAst.ReturningExpr $returning;
            final /* synthetic */ Map $metas;

            @NotNull
            public final PartiqlAst.Statement.Dml invoke(@NotNull PartiqlAst.Builder $this$build) {
                Intrinsics.checkParameterIsNotNull($this$build, "$receiver");
                return $this$build.dml(this.$dmlOperations, this.$from, this.$where, this.$returning, this.$metas);
            }
            {
                this.$dmlOperations = dmlOpList;
                this.$from = fromSource;
                this.$where = expr;
                this.$returning = returningExpr;
                this.$metas = map2;
                super(1);
            }
        });
    }
}

