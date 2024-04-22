/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  com.amazon.ionelement.api.IonMeta
 *  org.jetbrains.annotations.NotNull
 *  org.partiql.pig.runtime.SymbolPrimitive
 */
package org.partiql.lang.eval.visitors;

import com.amazon.ionelement.api.IonMeta;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.partiql.lang.ast.IsSyntheticNameMeta;
import org.partiql.lang.ast.UniqueNameMeta;
import org.partiql.lang.domains.PartiqlAst;
import org.partiql.lang.eval.PartiqlAstExtensionsKt;
import org.partiql.lang.eval.visitors.VisitorTransformBase;
import org.partiql.pig.runtime.SymbolPrimitive;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u000f\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0010\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016J\u0010\u0010\f\u001a\u00020\r2\u0006\u0010\n\u001a\u00020\rH\u0016R\u001a\u0010\u0002\u001a\u00020\u0003X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\u0004\u00a8\u0006\u000e"}, d2={"Lorg/partiql/lang/eval/visitors/GroupByItemAliasVisitorTransform;", "Lorg/partiql/lang/eval/visitors/VisitorTransformBase;", "nestLevel", "", "(I)V", "getNestLevel", "()I", "setNestLevel", "transformExprSelect", "Lorg/partiql/lang/domains/PartiqlAst$Expr;", "node", "Lorg/partiql/lang/domains/PartiqlAst$Expr$Select;", "transformGroupBy", "Lorg/partiql/lang/domains/PartiqlAst$GroupBy;", "lang"})
public final class GroupByItemAliasVisitorTransform
extends VisitorTransformBase {
    private int nestLevel;

    @Override
    @NotNull
    public PartiqlAst.GroupBy transformGroupBy(@NotNull PartiqlAst.GroupBy node) {
        Intrinsics.checkParameterIsNotNull(node, "node");
        return (PartiqlAst.GroupBy)PartiqlAst.Companion.build((Function1)new Function1<PartiqlAst.Builder, PartiqlAst.GroupBy>(this, node){
            final /* synthetic */ GroupByItemAliasVisitorTransform this$0;
            final /* synthetic */ PartiqlAst.GroupBy $node;

            /*
             * WARNING - void declaration
             */
            @NotNull
            public final PartiqlAst.GroupBy invoke(@NotNull PartiqlAst.Builder $this$build) {
                SymbolPrimitive symbolPrimitive;
                Collection<PartiqlAst.GroupKey> collection;
                void $this$mapIndexedTo$iv$iv;
                void $this$mapIndexed$iv;
                Intrinsics.checkParameterIsNotNull($this$build, "$receiver");
                Iterable iterable = this.$node.getKeyList().getKeys();
                PartiqlAst.GroupingStrategy groupingStrategy = this.$node.getStrategy();
                PartiqlAst.Builder builder = $this$build;
                boolean $i$f$mapIndexed = false;
                void var4_6 = $this$mapIndexed$iv;
                Collection destination$iv$iv = new ArrayList<E>(CollectionsKt.collectionSizeOrDefault($this$mapIndexed$iv, 10));
                boolean $i$f$mapIndexedTo = false;
                int index$iv$iv = 0;
                for (T item$iv$iv : $this$mapIndexedTo$iv$iv) {
                    void index;
                    void it;
                    int n = index$iv$iv++;
                    Collection collection2 = destination$iv$iv;
                    boolean bl = false;
                    if (n < 0) {
                        CollectionsKt.throwIndexOverflow();
                    }
                    int n2 = n;
                    PartiqlAst.GroupKey groupKey = (PartiqlAst.GroupKey)item$iv$iv;
                    int n3 = n2;
                    collection = collection2;
                    boolean bl2 = false;
                    Object object = it.getAsAlias();
                    if (object == null || (object = object.getText()) == null) {
                        object = PartiqlAstExtensionsKt.extractColumnAlias(it.getExpr(), (int)index);
                    }
                    Object aliasText = object;
                    Map<String, Object> metas = MapsKt.plus(it.getExpr().getMetas(), IonMeta.metaContainerOf((Pair[])new Pair[]{TuplesKt.to("$unique_name", new UniqueNameMeta("$__partiql__group_by_" + this.this$0.getNestLevel() + "_item_" + (int)index))}));
                    if (it.getAsAlias() == null) {
                        metas = MapsKt.plus(metas, IonMeta.metaContainerOf((Pair[])new Pair[]{TuplesKt.to("$is_synthetic_name", IsSyntheticNameMeta.Companion.getInstance())}));
                    }
                    SymbolPrimitive alias = new SymbolPrimitive((String)aliasText, metas);
                    PartiqlAst.GroupKey groupKey2 = $this$build.groupKey_(this.this$0.transformExpr(it.getExpr()), alias, alias.getMetas());
                    collection.add(groupKey2);
                }
                collection = (List)destination$iv$iv;
                PartiqlAst.Builder builder2 = builder;
                PartiqlAst.GroupingStrategy groupingStrategy2 = groupingStrategy;
                Map<String, Object> map2 = this.$node.getKeyList().getMetas();
                List list = collection;
                PartiqlAst.GroupKeyList groupKeyList = new PartiqlAst.GroupKeyList(list, map2);
                SymbolPrimitive symbolPrimitive2 = this.$node.getGroupAsAlias();
                if (symbolPrimitive2 != null) {
                    iterable = symbolPrimitive2;
                    PartiqlAst.GroupKeyList groupKeyList2 = groupKeyList;
                    groupingStrategy = groupingStrategy2;
                    builder = builder2;
                    boolean bl = false;
                    boolean bl3 = false;
                    Iterable it = iterable;
                    boolean bl4 = false;
                    SymbolPrimitive symbolPrimitive3 = this.this$0.transformSymbolPrimitive((SymbolPrimitive)it);
                    builder2 = builder;
                    groupingStrategy2 = groupingStrategy;
                    groupKeyList = groupKeyList2;
                    symbolPrimitive = symbolPrimitive3;
                } else {
                    symbolPrimitive = null;
                }
                return builder2.groupBy_(groupingStrategy2, groupKeyList, symbolPrimitive, this.$node.getMetas());
            }
            {
                this.this$0 = groupByItemAliasVisitorTransform;
                this.$node = groupBy2;
                super(1);
            }
        });
    }

    @Override
    @NotNull
    public PartiqlAst.Expr transformExprSelect(@NotNull PartiqlAst.Expr.Select node) {
        Intrinsics.checkParameterIsNotNull(node, "node");
        int n = this.nestLevel;
        this.nestLevel = n + 1;
        PartiqlAst.Expr expr = super.transformExprSelect(node);
        boolean bl = false;
        boolean bl2 = false;
        PartiqlAst.Expr it = expr;
        boolean bl3 = false;
        int n2 = this.nestLevel;
        this.nestLevel = n2 + -1;
        return expr;
    }

    public final int getNestLevel() {
        return this.nestLevel;
    }

    public final void setNestLevel(int n) {
        this.nestLevel = n;
    }

    public GroupByItemAliasVisitorTransform(int nestLevel) {
        this.nestLevel = nestLevel;
    }

    public /* synthetic */ GroupByItemAliasVisitorTransform(int n, int n2, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n2 & 1) != 0) {
            n = 0;
        }
        this(n);
    }

    public GroupByItemAliasVisitorTransform() {
        this(0, 1, null);
    }
}

