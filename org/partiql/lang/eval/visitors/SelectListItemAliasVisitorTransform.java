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
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.partiql.lang.domains.PartiqlAst;
import org.partiql.lang.domains.UtilKt;
import org.partiql.lang.eval.PartiqlAstExtensionsKt;
import org.partiql.lang.eval.visitors.VisitorTransformBase;
import org.partiql.pig.runtime.SymbolPrimitive;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0016\u00a8\u0006\u0007"}, d2={"Lorg/partiql/lang/eval/visitors/SelectListItemAliasVisitorTransform;", "Lorg/partiql/lang/eval/visitors/VisitorTransformBase;", "()V", "transformProjectionProjectList", "Lorg/partiql/lang/domains/PartiqlAst$Projection;", "node", "Lorg/partiql/lang/domains/PartiqlAst$Projection$ProjectList;", "lang"})
public final class SelectListItemAliasVisitorTransform
extends VisitorTransformBase {
    @Override
    @NotNull
    public PartiqlAst.Projection transformProjectionProjectList(@NotNull PartiqlAst.Projection.ProjectList node) {
        Intrinsics.checkParameterIsNotNull(node, "node");
        return (PartiqlAst.Projection)PartiqlAst.Companion.build((Function1)new Function1<PartiqlAst.Builder, PartiqlAst.Projection.ProjectList>(this, node){
            final /* synthetic */ SelectListItemAliasVisitorTransform this$0;
            final /* synthetic */ PartiqlAst.Projection.ProjectList $node;

            /*
             * WARNING - void declaration
             */
            @NotNull
            public final PartiqlAst.Projection.ProjectList invoke(@NotNull PartiqlAst.Builder $this$build) {
                Collection<void> collection;
                void $this$mapIndexedTo$iv$iv;
                void $this$mapIndexed$iv;
                Intrinsics.checkParameterIsNotNull($this$build, "$receiver");
                Iterable iterable = this.$node.getProjectItems();
                PartiqlAst.Builder builder = $this$build;
                boolean $i$f$mapIndexed = false;
                void var4_5 = $this$mapIndexed$iv;
                Collection destination$iv$iv = new ArrayList<E>(CollectionsKt.collectionSizeOrDefault($this$mapIndexed$iv, 10));
                boolean $i$f$mapIndexedTo = false;
                int index$iv$iv = 0;
                for (T item$iv$iv : $this$mapIndexedTo$iv$iv) {
                    void idx;
                    SymbolPrimitive symbolPrimitive;
                    void it;
                    int n = index$iv$iv++;
                    Collection collection2 = destination$iv$iv;
                    boolean bl = false;
                    if (n < 0) {
                        CollectionsKt.throwIndexOverflow();
                    }
                    int n2 = n;
                    PartiqlAst.ProjectItem projectItem = (PartiqlAst.ProjectItem)item$iv$iv;
                    int n3 = n2;
                    collection = collection2;
                    boolean bl2 = false;
                    void var17_18 = it;
                    void var21_21 = var17_18 instanceof PartiqlAst.ProjectItem.ProjectExpr ? (PartiqlAst.ProjectItem)((symbolPrimitive = ((PartiqlAst.ProjectItem.ProjectExpr)it).getAsAlias()) == null ? PartiqlAst.Builder.DefaultImpls.projectExpr_$default($this$build, this.this$0.transformExpr(((PartiqlAst.ProjectItem.ProjectExpr)it).getExpr()), new SymbolPrimitive(PartiqlAstExtensionsKt.extractColumnAlias(((PartiqlAst.ProjectItem.ProjectExpr)it).getExpr(), (int)idx), UtilKt.extractSourceLocation(this.$node)), null, 4, null) : PartiqlAst.Builder.DefaultImpls.projectExpr_$default($this$build, this.this$0.transformExpr(((PartiqlAst.ProjectItem.ProjectExpr)it).getExpr()), ((PartiqlAst.ProjectItem.ProjectExpr)it).getAsAlias(), null, 4, null)) : it;
                    collection.add(var21_21);
                }
                collection = (List)destination$iv$iv;
                return PartiqlAst.Builder.DefaultImpls.projectList$default(builder, (List)collection, null, 2, null);
            }
            {
                this.this$0 = selectListItemAliasVisitorTransform;
                this.$node = projectList;
                super(1);
            }
        });
    }
}

