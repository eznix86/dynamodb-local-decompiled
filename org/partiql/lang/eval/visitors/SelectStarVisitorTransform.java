/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  com.amazon.ionelement.api.IonMeta
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 *  org.partiql.pig.runtime.SymbolPrimitive
 */
package org.partiql.lang.eval.visitors;

import com.amazon.ionelement.api.IonMeta;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.partiql.lang.ast.UniqueNameMeta;
import org.partiql.lang.domains.PartiqlAst;
import org.partiql.lang.eval.ExceptionsKt;
import org.partiql.lang.eval.visitors.VisitorTransformBase;
import org.partiql.pig.runtime.SymbolPrimitive;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000D\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001:\u0001\u0017B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0018\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0002J\u0010\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0002J\u001a\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\f2\b\b\u0002\u0010\u0010\u001a\u00020\fH\u0002J\u0016\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00130\u00122\u0006\u0010\u0014\u001a\u00020\u0015H\u0002J\u0010\u0010\u0016\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0016\u00a8\u0006\u0018"}, d2={"Lorg/partiql/lang/eval/visitors/SelectStarVisitorTransform;", "Lorg/partiql/lang/eval/visitors/VisitorTransformBase;", "()V", "copyProjectionToSelect", "Lorg/partiql/lang/domains/PartiqlAst$Expr;", "node", "Lorg/partiql/lang/domains/PartiqlAst$Expr$Select;", "newProjection", "Lorg/partiql/lang/domains/PartiqlAst$Projection;", "createProjectAll", "Lorg/partiql/lang/domains/PartiqlAst$ProjectItem$ProjectAll;", "name", "", "createProjectExpr", "Lorg/partiql/lang/domains/PartiqlAst$ProjectItem$ProjectExpr;", "variableName", "asAlias", "extractAliases", "", "Lorg/partiql/lang/eval/visitors/SelectStarVisitorTransform$FromSourceAliases;", "fromSource", "Lorg/partiql/lang/domains/PartiqlAst$FromSource;", "transformExprSelect", "FromSourceAliases", "lang"})
public final class SelectStarVisitorTransform
extends VisitorTransformBase {
    private final PartiqlAst.Expr copyProjectionToSelect(PartiqlAst.Expr.Select node, PartiqlAst.Projection newProjection2) {
        return (PartiqlAst.Expr)PartiqlAst.Companion.build((Function1)new Function1<PartiqlAst.Builder, PartiqlAst.Expr.Select>(node, newProjection2){
            final /* synthetic */ PartiqlAst.Expr.Select $node;
            final /* synthetic */ PartiqlAst.Projection $newProjection;

            @NotNull
            public final PartiqlAst.Expr.Select invoke(@NotNull PartiqlAst.Builder $this$build) {
                Intrinsics.checkParameterIsNotNull($this$build, "$receiver");
                return $this$build.select(this.$node.getSetq(), this.$newProjection, this.$node.getFrom(), this.$node.getFromLet(), this.$node.getWhere(), this.$node.getGroup(), this.$node.getHaving(), this.$node.getOrder(), this.$node.getLimit(), this.$node.getMetas());
            }
            {
                this.$node = select;
                this.$newProjection = projection;
                super(1);
            }
        });
    }

    /*
     * WARNING - void declaration
     */
    @Override
    @NotNull
    public PartiqlAst.Expr transformExprSelect(@NotNull PartiqlAst.Expr.Select node) {
        Intrinsics.checkParameterIsNotNull(node, "node");
        PartiqlAst.Expr expr = super.transformExprSelect(node);
        if (expr == null) {
            throw new TypeCastException("null cannot be cast to non-null type org.partiql.lang.domains.PartiqlAst.Expr.Select");
        }
        PartiqlAst.Expr.Select transformedExpr = (PartiqlAst.Expr.Select)expr;
        PartiqlAst.Projection projection = transformedExpr.getProject();
        if (projection instanceof PartiqlAst.Projection.ProjectStar) {
            void $this$mapTo$iv$iv;
            PartiqlAst.GroupBy groupBy2 = transformedExpr.getGroup();
            if (groupBy2 == null) {
                List<FromSourceAliases> fromSourceAliases = this.extractAliases(transformedExpr.getFrom());
                PartiqlAst.Projection.ProjectList newProjection2 = (PartiqlAst.Projection.ProjectList)PartiqlAst.Companion.build((Function1)new Function1<PartiqlAst.Builder, PartiqlAst.Projection.ProjectList>(this, fromSourceAliases){
                    final /* synthetic */ SelectStarVisitorTransform this$0;
                    final /* synthetic */ List $fromSourceAliases;

                    /*
                     * Unable to fully structure code
                     */
                    @NotNull
                    public final PartiqlAst.Projection.ProjectList invoke(@NotNull PartiqlAst.Builder $this$build) {
                        Intrinsics.checkParameterIsNotNull($this$build, "$receiver");
                        var2_2 = this.$fromSourceAliases;
                        var18_3 = $this$build;
                        $i$f$map = false;
                        var4_5 = $this$map$iv;
                        destination$iv$iv = new ArrayList<E>(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
                        $i$f$mapTo = false;
                        for (T item$iv$iv : $this$mapTo$iv$iv) {
                            var9_10 = (FromSourceAliases)item$iv$iv;
                            var19_19 = destination$iv$iv;
                            $i$a$-map-SelectStarVisitorTransform$transformExprSelect$newProjection$1$1 = false;
                            v0 = CollectionsKt.listOf(SelectStarVisitorTransform.access$createProjectAll(this.this$0, aliases.getAsAlias()));
                            v1 = aliases.getAtAlias();
                            if (v1 == null) ** GOTO lbl-1000
                            var11_12 = v1;
                            var12_13 = v0;
                            var13_14 = false;
                            var14_15 = false;
                            it = var11_12;
                            $i$a$-let-SelectStarVisitorTransform$transformExprSelect$newProjection$1$1$1 = false;
                            var17_18 = CollectionsKt.listOf(SelectStarVisitorTransform.createProjectExpr$default(this.this$0, it, null, 2, null));
                            v0 = var12_13;
                            v1 = var17_18;
                            if (v1 != null) {
                                v2 = v1;
                            } else lbl-1000:
                            // 2 sources

                            {
                                v2 = CollectionsKt.emptyList();
                            }
                            v3 = CollectionsKt.plus(v0, v2);
                            v4 = aliases.getByAlias();
                            if (v4 == null) ** GOTO lbl-1000
                            var11_12 = v4;
                            var12_13 = v3;
                            var13_14 = false;
                            var14_15 = false;
                            it = var11_12;
                            $i$a$-let-SelectStarVisitorTransform$transformExprSelect$newProjection$1$1$2 = false;
                            var17_18 = CollectionsKt.listOf(SelectStarVisitorTransform.createProjectExpr$default(this.this$0, it, null, 2, null));
                            v3 = var12_13;
                            v4 = var17_18;
                            if (v4 != null) {
                                v5 = v4;
                            } else lbl-1000:
                            // 2 sources

                            {
                                v5 = CollectionsKt.emptyList();
                            }
                            var20_20 = CollectionsKt.plus(v3, v5);
                            var19_19.add(var20_20);
                        }
                        var19_19 = (List)destination$iv$iv;
                        return PartiqlAst.Builder.DefaultImpls.projectList$default(var18_3, CollectionsKt.flatten((Iterable)var19_19), null, 2, null);
                    }
                    {
                        this.this$0 = selectStarVisitorTransform;
                        this.$fromSourceAliases = list;
                        super(1);
                    }
                });
                return this.copyProjectionToSelect(transformedExpr, newProjection2);
            }
            Iterable $this$map$iv = transformedExpr.getGroup().getKeyList().getKeys();
            boolean $i$f$map22 = false;
            Iterable iterable = $this$map$iv;
            Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
            boolean $i$f$mapTo = false;
            for (Object item$iv$iv : $this$mapTo$iv$iv) {
                UniqueNameMeta uniqueNameMeta;
                SymbolPrimitive asName;
                void it;
                PartiqlAst.GroupKey groupKey = (PartiqlAst.GroupKey)item$iv$iv;
                Collection collection = destination$iv$iv;
                boolean bl = false;
                if (it.getAsAlias() == null) {
                    Void void_ = ExceptionsKt.errNoContext("GroupByItem has no AS-alias--GroupByItemAliasVisitorTransform must be executed before SelectStarVisitorTransform", true);
                    throw null;
                }
                Object v = asName.getMetas().get("$unique_name");
                if (!(v instanceof UniqueNameMeta)) {
                    v = null;
                }
                if ((UniqueNameMeta)v == null) {
                    String string = "UniqueNameMeta not found--normally, this is added by GroupByItemAliasVisitorTransform";
                    boolean bl2 = false;
                    throw (Throwable)new IllegalStateException(string.toString());
                }
                PartiqlAst.ProjectItem.ProjectExpr projectExpr = this.createProjectExpr(uniqueNameMeta.getUniqueName(), asName.getText());
                collection.add(projectExpr);
            }
            List selectListItemsFromGroupBy = (List)destination$iv$iv;
            SymbolPrimitive symbolPrimitive = transformedExpr.getGroup().getGroupAsAlias();
            String $i$f$map22 = symbolPrimitive != null ? symbolPrimitive.getText() : null;
            boolean bl = false;
            boolean bl3 = false;
            String it = $i$f$map22;
            boolean bl4 = false;
            List groupNameItem = it != null ? CollectionsKt.listOf(SelectStarVisitorTransform.createProjectExpr$default(this, it, null, 2, null)) : CollectionsKt.emptyList();
            PartiqlAst.Projection.ProjectList newProjection3 = (PartiqlAst.Projection.ProjectList)PartiqlAst.Companion.build((Function1)new Function1<PartiqlAst.Builder, PartiqlAst.Projection.ProjectList>(selectListItemsFromGroupBy, groupNameItem){
                final /* synthetic */ List $selectListItemsFromGroupBy;
                final /* synthetic */ List $groupNameItem;

                @NotNull
                public final PartiqlAst.Projection.ProjectList invoke(@NotNull PartiqlAst.Builder $this$build) {
                    Intrinsics.checkParameterIsNotNull($this$build, "$receiver");
                    return PartiqlAst.Builder.DefaultImpls.projectList$default($this$build, CollectionsKt.plus((Collection)this.$selectListItemsFromGroupBy, (Iterable)this.$groupNameItem), null, 2, null);
                }
                {
                    this.$selectListItemsFromGroupBy = list;
                    this.$groupNameItem = list2;
                    super(1);
                }
            });
            return this.copyProjectionToSelect(transformedExpr, newProjection3);
        }
        return transformedExpr;
    }

    private final PartiqlAst.ProjectItem.ProjectAll createProjectAll(String name) {
        return (PartiqlAst.ProjectItem.ProjectAll)PartiqlAst.Companion.build((Function1)new Function1<PartiqlAst.Builder, PartiqlAst.ProjectItem.ProjectAll>(name){
            final /* synthetic */ String $name;

            @NotNull
            public final PartiqlAst.ProjectItem.ProjectAll invoke(@NotNull PartiqlAst.Builder $this$build) {
                Intrinsics.checkParameterIsNotNull($this$build, "$receiver");
                return PartiqlAst.Builder.DefaultImpls.projectAll$default($this$build, $this$build.id(this.$name, PartiqlAst.Builder.DefaultImpls.caseSensitive$default($this$build, null, 1, null), PartiqlAst.Builder.DefaultImpls.unqualified$default($this$build, null, 1, null), IonMeta.emptyMetaContainer()), null, 2, null);
            }
            {
                this.$name = string;
                super(1);
            }
        });
    }

    private final PartiqlAst.ProjectItem.ProjectExpr createProjectExpr(String variableName, String asAlias) {
        return (PartiqlAst.ProjectItem.ProjectExpr)PartiqlAst.Companion.build((Function1)new Function1<PartiqlAst.Builder, PartiqlAst.ProjectItem.ProjectExpr>(variableName, asAlias){
            final /* synthetic */ String $variableName;
            final /* synthetic */ String $asAlias;

            @NotNull
            public final PartiqlAst.ProjectItem.ProjectExpr invoke(@NotNull PartiqlAst.Builder $this$build) {
                Intrinsics.checkParameterIsNotNull($this$build, "$receiver");
                return PartiqlAst.Builder.DefaultImpls.projectExpr$default($this$build, $this$build.id(this.$variableName, PartiqlAst.Builder.DefaultImpls.caseSensitive$default($this$build, null, 1, null), PartiqlAst.Builder.DefaultImpls.unqualified$default($this$build, null, 1, null), IonMeta.emptyMetaContainer()), this.$asAlias, null, 4, null);
            }
            {
                this.$variableName = string;
                this.$asAlias = string2;
                super(1);
            }
        });
    }

    static /* synthetic */ PartiqlAst.ProjectItem.ProjectExpr createProjectExpr$default(SelectStarVisitorTransform selectStarVisitorTransform, String string, String string2, int n, Object object) {
        if ((n & 2) != 0) {
            string2 = string;
        }
        return selectStarVisitorTransform.createProjectExpr(string, string2);
    }

    private final List<FromSourceAliases> extractAliases(PartiqlAst.FromSource fromSource) {
        boolean bl = false;
        List aliases = new ArrayList();
        PartiqlAst.Visitor visitor2 = new PartiqlAst.Visitor(aliases){
            final /* synthetic */ List $aliases;

            protected void visitFromSourceScan(@NotNull PartiqlAst.FromSource.Scan node) {
                Intrinsics.checkParameterIsNotNull(node, "node");
                List list = this.$aliases;
                Object object = node.getAsAlias();
                if (object == null || (object = object.getText()) == null) {
                    String string = "FromSourceAliasVisitorTransform must be executed before SelectStarVisitorTransform";
                    List list2 = list;
                    boolean bl = false;
                    throw (Throwable)new IllegalStateException(string.toString());
                }
                SymbolPrimitive symbolPrimitive = node.getAtAlias();
                SymbolPrimitive symbolPrimitive2 = node.getByAlias();
                String string = symbolPrimitive2 != null ? symbolPrimitive2.getText() : null;
                String string2 = symbolPrimitive != null ? symbolPrimitive.getText() : null;
                Object object2 = object;
                list.add(new FromSourceAliases((String)object2, string2, string));
            }

            protected void visitFromSourceUnpivot(@NotNull PartiqlAst.FromSource.Unpivot node) {
                Intrinsics.checkParameterIsNotNull(node, "node");
                List list = this.$aliases;
                Object object = node.getAsAlias();
                if (object == null || (object = object.getText()) == null) {
                    String string = "FromSourceAliasVisitorTransform must be executed before SelectStarVisitorTransform";
                    List list2 = list;
                    boolean bl = false;
                    throw (Throwable)new IllegalStateException(string.toString());
                }
                SymbolPrimitive symbolPrimitive = node.getAtAlias();
                SymbolPrimitive symbolPrimitive2 = node.getByAlias();
                String string = symbolPrimitive2 != null ? symbolPrimitive2.getText() : null;
                String string2 = symbolPrimitive != null ? symbolPrimitive.getText() : null;
                Object object2 = object;
                list.add(new FromSourceAliases((String)object2, string2, string));
            }

            public void walkExprSelect(@NotNull PartiqlAst.Expr.Select node) {
                Intrinsics.checkParameterIsNotNull(node, "node");
            }
            {
                this.$aliases = $captured_local_variable$0;
            }
        };
        visitor2.walkFromSource(fromSource);
        return aliases;
    }

    public static final /* synthetic */ PartiqlAst.ProjectItem.ProjectAll access$createProjectAll(SelectStarVisitorTransform $this, String name) {
        return $this.createProjectAll(name);
    }

    @Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\b\b\u0002\u0018\u00002\u00020\u0001B!\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\u0002\u0010\u0006R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\bR\u0013\u0010\u0005\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\b\u00a8\u0006\u000b"}, d2={"Lorg/partiql/lang/eval/visitors/SelectStarVisitorTransform$FromSourceAliases;", "", "asAlias", "", "atAlias", "byAlias", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V", "getAsAlias", "()Ljava/lang/String;", "getAtAlias", "getByAlias", "lang"})
    private static final class FromSourceAliases {
        @NotNull
        private final String asAlias;
        @Nullable
        private final String atAlias;
        @Nullable
        private final String byAlias;

        @NotNull
        public final String getAsAlias() {
            return this.asAlias;
        }

        @Nullable
        public final String getAtAlias() {
            return this.atAlias;
        }

        @Nullable
        public final String getByAlias() {
            return this.byAlias;
        }

        public FromSourceAliases(@NotNull String asAlias, @Nullable String atAlias, @Nullable String byAlias) {
            Intrinsics.checkParameterIsNotNull(asAlias, "asAlias");
            this.asAlias = asAlias;
            this.atAlias = atAlias;
            this.byAlias = byAlias;
        }
    }
}

