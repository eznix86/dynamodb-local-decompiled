/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  com.amazon.ionelement.api.IonMeta
 *  org.jetbrains.annotations.NotNull
 */
package org.partiql.lang.eval.visitors;

import com.amazon.ionelement.api.IonMeta;
import java.util.ArrayList;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.partiql.lang.ast.AggregateCallSiteListMeta;
import org.partiql.lang.ast.AggregateRegisterIdMeta;
import org.partiql.lang.domains.PartiqlAst;
import org.partiql.lang.eval.visitors.VisitorTransformBase;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0010\u000e\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u0005H\u0016J\u0010\u0010\n\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u000bH\u0016J \u0010\f\u001a\u0012\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\u000f0\rj\u0002`\u00102\u0006\u0010\t\u001a\u00020\u000bH\u0016J\u0010\u0010\u0011\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u0012H\u0016R\u001e\u0010\u0003\u001a\u0012\u0012\u0004\u0012\u00020\u00050\u0004j\b\u0012\u0004\u0012\u00020\u0005`\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0013"}, d2={"Lorg/partiql/lang/eval/visitors/AggregateSupportVisitorTransform;", "Lorg/partiql/lang/eval/visitors/VisitorTransformBase;", "()V", "aggregateCallSites", "Ljava/util/ArrayList;", "Lorg/partiql/lang/domains/PartiqlAst$Expr$CallAgg;", "Lkotlin/collections/ArrayList;", "transformExprCallAgg", "Lorg/partiql/lang/domains/PartiqlAst$Expr;", "node", "transformExprSelect", "Lorg/partiql/lang/domains/PartiqlAst$Expr$Select;", "transformExprSelect_metas", "", "", "", "Lcom/amazon/ionelement/api/MetaContainer;", "transformProjectionProjectValue_value", "Lorg/partiql/lang/domains/PartiqlAst$Projection$ProjectValue;", "lang"})
public final class AggregateSupportVisitorTransform
extends VisitorTransformBase {
    private final ArrayList<PartiqlAst.Expr.CallAgg> aggregateCallSites = new ArrayList();

    @Override
    @NotNull
    public PartiqlAst.Expr transformExprSelect(@NotNull PartiqlAst.Expr.Select node) {
        Intrinsics.checkParameterIsNotNull(node, "node");
        return new AggregateSupportVisitorTransform().transformExprSelectEvaluationOrder(node);
    }

    @Override
    @NotNull
    public PartiqlAst.Expr transformExprCallAgg(@NotNull PartiqlAst.Expr.CallAgg node) {
        Intrinsics.checkParameterIsNotNull(node, "node");
        PartiqlAst.Expr.CallAgg transformedCallAgg2 = (PartiqlAst.Expr.CallAgg)PartiqlAst.Companion.build((Function1)new Function1<PartiqlAst.Builder, PartiqlAst.Expr.CallAgg>(this, node){
            final /* synthetic */ AggregateSupportVisitorTransform this$0;
            final /* synthetic */ PartiqlAst.Expr.CallAgg $node;

            @NotNull
            public final PartiqlAst.Expr.CallAgg invoke(@NotNull PartiqlAst.Builder $this$build) {
                Intrinsics.checkParameterIsNotNull($this$build, "$receiver");
                return $this$build.callAgg_(this.$node.getSetq(), this.$node.getFuncName(), this.this$0.transformExpr(this.$node.getArg()), MapsKt.plus(this.this$0.transformMetas(this.$node.getMetas()), IonMeta.metaContainerOf((Pair[])new Pair[]{TuplesKt.to("$aggregate_register_id", new AggregateRegisterIdMeta(AggregateSupportVisitorTransform.access$getAggregateCallSites$p(this.this$0).size()))})));
            }
            {
                this.this$0 = aggregateSupportVisitorTransform;
                this.$node = callAgg;
                super(1);
            }
        });
        this.aggregateCallSites.add(transformedCallAgg2);
        return transformedCallAgg2;
    }

    @Override
    @NotNull
    public PartiqlAst.Expr transformProjectionProjectValue_value(@NotNull PartiqlAst.Projection.ProjectValue node) {
        Intrinsics.checkParameterIsNotNull(node, "node");
        return new AggregateSupportVisitorTransform().transformExpr(node.getValue());
    }

    @Override
    @NotNull
    public Map<String, Object> transformExprSelect_metas(@NotNull PartiqlAst.Expr.Select node) {
        Intrinsics.checkParameterIsNotNull(node, "node");
        return MapsKt.plus(this.transformMetas(node.getMetas()), IonMeta.metaContainerOf((Pair[])new Pair[]{TuplesKt.to("$aggregate_call_sites", new AggregateCallSiteListMeta(CollectionsKt.toList((Iterable)this.aggregateCallSites)))}));
    }

    public static final /* synthetic */ ArrayList access$getAggregateCallSites$p(AggregateSupportVisitorTransform $this) {
        return $this.aggregateCallSites;
    }
}

