/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.partiql.pig.runtime.SymbolPrimitive
 */
package org.partiql.lang.eval.visitors;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;
import org.partiql.lang.domains.PartiqlAst;
import org.partiql.lang.eval.ExceptionsKt;
import org.partiql.lang.eval.visitors.GroupByPathExpressionVisitorTransform;
import org.partiql.lang.eval.visitors.SubstitutionPair;
import org.partiql.lang.eval.visitors.SubstitutionVisitorTransform;
import org.partiql.pig.runtime.SymbolPrimitive;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u0000 \u00122\u00020\u0001:\u0001\u0012B\u001b\u0012\u0014\b\u0002\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u0003\u00a2\u0006\u0002\u0010\u0006J\"\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u00032\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\tH\u0002J\u001c\u0010\u000b\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u00032\u0006\u0010\f\u001a\u00020\rH\u0002J\u0010\u0010\u000e\u001a\u00020\u00042\u0006\u0010\u000f\u001a\u00020\u0010H\u0016J\u0010\u0010\u0011\u001a\u00020\u00042\u0006\u0010\u000f\u001a\u00020\rH\u0016\u00a8\u0006\u0013"}, d2={"Lorg/partiql/lang/eval/visitors/GroupByPathExpressionVisitorTransform;", "Lorg/partiql/lang/eval/visitors/SubstitutionVisitorTransform;", "parentSubstitutions", "", "Lorg/partiql/lang/domains/PartiqlAst$Expr;", "Lorg/partiql/lang/eval/visitors/SubstitutionPair;", "(Ljava/util/Map;)V", "getSubstitutionsExceptFor", "fromSourceAliases", "", "", "getSubstitutionsForSelect", "selectExpr", "Lorg/partiql/lang/domains/PartiqlAst$Expr$Select;", "transformExprCallAgg", "node", "Lorg/partiql/lang/domains/PartiqlAst$Expr$CallAgg;", "transformExprSelect", "Companion", "lang"})
public final class GroupByPathExpressionVisitorTransform
extends SubstitutionVisitorTransform {
    public static final Companion Companion = new Companion(null);

    @Override
    @NotNull
    public PartiqlAst.Expr transformExprSelect(@NotNull PartiqlAst.Expr.Select node) {
        Intrinsics.checkParameterIsNotNull(node, "node");
        List<String> fromSourceAliases = Companion.collectAliases(node.getFrom());
        Map<PartiqlAst.Expr, SubstitutionPair> unshadowedSubstitutions = this.getSubstitutionsExceptFor(fromSourceAliases);
        GroupByPathExpressionVisitorTransform unshadowedTransformer = new GroupByPathExpressionVisitorTransform(unshadowedSubstitutions);
        Map<PartiqlAst.Expr, SubstitutionPair> currentSubstitutions = this.getSubstitutionsForSelect(node);
        GroupByPathExpressionVisitorTransform currentAndUnshadowedTransformer = new GroupByPathExpressionVisitorTransform(MapsKt.plus(unshadowedSubstitutions, currentSubstitutions));
        PartiqlAst.Projection projection = currentAndUnshadowedTransformer.transformExprSelect_project(node);
        PartiqlAst.FromSource from2 = this.transformExprSelect_from(node);
        PartiqlAst.Let fromLet2 = unshadowedTransformer.transformExprSelect_fromLet(node);
        PartiqlAst.Expr where2 = unshadowedTransformer.transformExprSelect_where(node);
        PartiqlAst.GroupBy groupBy2 = unshadowedTransformer.transformExprSelect_group(node);
        PartiqlAst.Expr having2 = currentAndUnshadowedTransformer.transformExprSelect_having(node);
        PartiqlAst.OrderBy order2 = currentAndUnshadowedTransformer.transformExprSelect_order(node);
        PartiqlAst.Expr limit2 = unshadowedTransformer.transformExprSelect_limit(node);
        Map<String, Object> metas = unshadowedTransformer.transformExprSelect_metas(node);
        return (PartiqlAst.Expr)PartiqlAst.Companion.build((Function1)new Function1<PartiqlAst.Builder, PartiqlAst.Expr.Select>(node, projection, from2, fromLet2, where2, groupBy2, having2, order2, limit2, metas){
            final /* synthetic */ PartiqlAst.Expr.Select $node;
            final /* synthetic */ PartiqlAst.Projection $projection;
            final /* synthetic */ PartiqlAst.FromSource $from;
            final /* synthetic */ PartiqlAst.Let $fromLet;
            final /* synthetic */ PartiqlAst.Expr $where;
            final /* synthetic */ PartiqlAst.GroupBy $groupBy;
            final /* synthetic */ PartiqlAst.Expr $having;
            final /* synthetic */ PartiqlAst.OrderBy $order;
            final /* synthetic */ PartiqlAst.Expr $limit;
            final /* synthetic */ Map $metas;

            @NotNull
            public final PartiqlAst.Expr.Select invoke(@NotNull PartiqlAst.Builder $this$build) {
                Intrinsics.checkParameterIsNotNull($this$build, "$receiver");
                return new PartiqlAst.Expr.Select(this.$node.getSetq(), this.$projection, this.$from, this.$fromLet, this.$where, this.$groupBy, this.$having, this.$order, this.$limit, this.$metas);
            }
            {
                this.$node = select;
                this.$projection = projection;
                this.$from = fromSource;
                this.$fromLet = let2;
                this.$where = expr;
                this.$groupBy = groupBy2;
                this.$having = expr2;
                this.$order = orderBy;
                this.$limit = expr3;
                this.$metas = map2;
                super(1);
            }
        });
    }

    /*
     * WARNING - void declaration
     */
    private final Map<PartiqlAst.Expr, SubstitutionPair> getSubstitutionsForSelect(PartiqlAst.Expr.Select selectExpr) {
        void $this$associateByTo$iv$iv;
        Object object = selectExpr.getGroup();
        if (object == null || (object = ((PartiqlAst.GroupBy)object).getKeyList()) == null || (object = ((PartiqlAst.GroupKeyList)object).getKeys()) == null) {
            boolean bl = false;
            object = CollectionsKt.emptyList();
        }
        Sequence $this$associateBy$iv = SequencesKt.map(SequencesKt.filter(CollectionsKt.asSequence((Iterable)object), getSubstitutionsForSelect.1.INSTANCE), getSubstitutionsForSelect.2.INSTANCE);
        boolean $i$f$associateBy = false;
        Sequence sequence = $this$associateBy$iv;
        Map destination$iv$iv = new LinkedHashMap();
        boolean $i$f$associateByTo = false;
        for (Object element$iv$iv : $this$associateByTo$iv$iv) {
            void it;
            SubstitutionPair substitutionPair = (SubstitutionPair)element$iv$iv;
            Map map2 = destination$iv$iv;
            boolean bl = false;
            PartiqlAst.Expr expr = it.getTarget();
            map2.put(expr, element$iv$iv);
        }
        return destination$iv$iv;
    }

    /*
     * WARNING - void declaration
     */
    private final Map<PartiqlAst.Expr, SubstitutionPair> getSubstitutionsExceptFor(List<String> fromSourceAliases) {
        void $this$associateByTo$iv$iv;
        void $this$filterTo$iv$iv;
        Iterable $this$filter$iv = super.getSubstitutions().values();
        boolean $i$f$filter = false;
        Iterable iterable = $this$filter$iv;
        Iterable destination$iv$iv = new ArrayList();
        boolean $i$f$filterTo = false;
        for (Object element$iv$iv : $this$filterTo$iv$iv) {
            boolean bl;
            block9: {
                PartiqlAst.Expr.Id targetRootVarRef;
                PartiqlAst.Expr.Id id;
                SubstitutionPair it = (SubstitutionPair)element$iv$iv;
                boolean bl2 = false;
                PartiqlAst.Expr expr = it.getTarget();
                if (!(expr instanceof PartiqlAst.Expr.Path)) {
                    expr = null;
                }
                PartiqlAst.Expr.Path path = (PartiqlAst.Expr.Path)expr;
                PartiqlAst.Expr expr2 = path != null ? path.getRoot() : null;
                if (!(expr2 instanceof PartiqlAst.Expr.Id)) {
                    expr2 = null;
                }
                if ((id = (targetRootVarRef = (PartiqlAst.Expr.Id)expr2)) == null) {
                    bl = true;
                } else {
                    boolean ignoreCase = targetRootVarRef.getCase() instanceof PartiqlAst.CaseSensitivity.CaseInsensitive;
                    Iterable $this$all$iv = fromSourceAliases;
                    boolean $i$f$all = false;
                    if ($this$all$iv instanceof Collection && ((Collection)$this$all$iv).isEmpty()) {
                        bl = true;
                    } else {
                        for (Object element$iv : $this$all$iv) {
                            String alias = (String)element$iv;
                            boolean bl3 = false;
                            PartiqlAst.Expr.Id id2 = targetRootVarRef;
                            if (id2 == null ? true : StringsKt.compareTo(targetRootVarRef.getName().getText(), alias, ignoreCase) != 0) continue;
                            bl = false;
                            break block9;
                        }
                        bl = true;
                    }
                }
            }
            if (!bl) continue;
            destination$iv$iv.add(element$iv$iv);
        }
        Iterable $this$associateBy$iv = (List)destination$iv$iv;
        boolean $i$f$associateBy = false;
        int capacity$iv = RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault($this$associateBy$iv, 10)), 16);
        destination$iv$iv = $this$associateBy$iv;
        Map destination$iv$iv2 = new LinkedHashMap(capacity$iv);
        boolean $i$f$associateByTo = false;
        for (Object element$iv$iv : $this$associateByTo$iv$iv) {
            void it;
            SubstitutionPair bl2 = (SubstitutionPair)element$iv$iv;
            Map map2 = destination$iv$iv2;
            boolean bl = false;
            PartiqlAst.Expr expr = it.getTarget();
            map2.put(expr, element$iv$iv);
        }
        return destination$iv$iv2;
    }

    @Override
    @NotNull
    public PartiqlAst.Expr transformExprCallAgg(@NotNull PartiqlAst.Expr.CallAgg node) {
        Intrinsics.checkParameterIsNotNull(node, "node");
        return node;
    }

    public GroupByPathExpressionVisitorTransform(@NotNull Map<PartiqlAst.Expr, SubstitutionPair> parentSubstitutions) {
        Intrinsics.checkParameterIsNotNull(parentSubstitutions, "parentSubstitutions");
        super(parentSubstitutions);
    }

    public /* synthetic */ GroupByPathExpressionVisitorTransform(Map map2, int n, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n & 1) != 0) {
            boolean bl = false;
            map2 = MapsKt.emptyMap();
        }
        this(map2);
    }

    public GroupByPathExpressionVisitorTransform() {
        this(null, 1, null);
    }

    @Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006J\u0014\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b2\u0006\u0010\n\u001a\u00020\u000b\u00a8\u0006\f"}, d2={"Lorg/partiql/lang/eval/visitors/GroupByPathExpressionVisitorTransform$Companion;", "", "()V", "canBeSubstituted", "", "groupKey", "Lorg/partiql/lang/domains/PartiqlAst$GroupKey;", "collectAliases", "", "", "fromSource", "Lorg/partiql/lang/domains/PartiqlAst$FromSource;", "lang"})
    public static final class Companion {
        public final boolean canBeSubstituted(@NotNull PartiqlAst.GroupKey groupKey) {
            Intrinsics.checkParameterIsNotNull(groupKey, "groupKey");
            PartiqlAst.Expr expr = groupKey.getExpr();
            SymbolPrimitive asName = groupKey.getAsAlias();
            if (asName == null) {
                throw (Throwable)new IllegalStateException("GroupByItem.asName must be specified for this transform to work");
            }
            return !asName.getMetas().containsKey("$is_synthetic_name") ? false : (!(expr instanceof PartiqlAst.Expr.Path) ? false : ((PartiqlAst.Expr.Path)expr).getSteps().size() == 1);
        }

        @NotNull
        public final List<String> collectAliases(@NotNull PartiqlAst.FromSource fromSource) {
            List<Object> list;
            Intrinsics.checkParameterIsNotNull(fromSource, "fromSource");
            PartiqlAst.FromSource fromSource2 = fromSource;
            if (fromSource2 instanceof PartiqlAst.FromSource.Scan) {
                Object object = ((PartiqlAst.FromSource.Scan)fromSource).getAsAlias();
                if (object == null || (object = object.getText()) == null) {
                    Void void_ = ExceptionsKt.errNoContext("FromSourceItem.variables.asName must be specified for this transform to work", true);
                    throw null;
                }
                list = CollectionsKt.listOf(object);
            } else if (fromSource2 instanceof PartiqlAst.FromSource.Join) {
                list = CollectionsKt.plus((Collection)this.collectAliases(((PartiqlAst.FromSource.Join)fromSource).getLeft()), (Iterable)this.collectAliases(((PartiqlAst.FromSource.Join)fromSource).getRight()));
            } else if (fromSource2 instanceof PartiqlAst.FromSource.Unpivot) {
                String[] stringArray = new String[2];
                SymbolPrimitive symbolPrimitive = ((PartiqlAst.FromSource.Unpivot)fromSource).getAsAlias();
                stringArray[0] = symbolPrimitive != null ? symbolPrimitive.getText() : null;
                SymbolPrimitive symbolPrimitive2 = ((PartiqlAst.FromSource.Unpivot)fromSource).getAtAlias();
                stringArray[1] = symbolPrimitive2 != null ? symbolPrimitive2.getText() : null;
                list = CollectionsKt.listOfNotNull(stringArray);
            } else {
                throw new NoWhenBranchMatchedException();
            }
            return list;
        }

        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

