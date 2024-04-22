/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 *  org.partiql.pig.runtime.SymbolPrimitive
 */
package org.partiql.lang.eval.visitors;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.partiql.lang.domains.PartiqlAst;
import org.partiql.lang.domains.UtilKt;
import org.partiql.lang.eval.PartiqlAstExtensionsKt;
import org.partiql.lang.eval.visitors.VisitorTransformBase;
import org.partiql.pig.runtime.SymbolPrimitive;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001:\u0001\u0006B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0004H\u0016\u00a8\u0006\u0007"}, d2={"Lorg/partiql/lang/eval/visitors/FromSourceAliasVisitorTransform;", "Lorg/partiql/lang/eval/visitors/VisitorTransformBase;", "()V", "transformFromSource", "Lorg/partiql/lang/domains/PartiqlAst$FromSource;", "node", "InnerFromSourceAliasVisitorTransform", "lang"})
public final class FromSourceAliasVisitorTransform
extends VisitorTransformBase {
    @Override
    @NotNull
    public PartiqlAst.FromSource transformFromSource(@NotNull PartiqlAst.FromSource node) {
        Intrinsics.checkParameterIsNotNull(node, "node");
        return new InnerFromSourceAliasVisitorTransform().transformFromSource(node);
    }

    @Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0002\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0016J\u0012\u0010\t\u001a\u0004\u0018\u00010\n2\u0006\u0010\u0007\u001a\u00020\u000bH\u0016J\u0012\u0010\f\u001a\u0004\u0018\u00010\n2\u0006\u0010\u0007\u001a\u00020\rH\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u000e"}, d2={"Lorg/partiql/lang/eval/visitors/FromSourceAliasVisitorTransform$InnerFromSourceAliasVisitorTransform;", "Lorg/partiql/lang/eval/visitors/VisitorTransformBase;", "()V", "fromSourceCounter", "", "transformExprSelect", "Lorg/partiql/lang/domains/PartiqlAst$Expr;", "node", "Lorg/partiql/lang/domains/PartiqlAst$Expr$Select;", "transformFromSourceScan_asAlias", "Lorg/partiql/pig/runtime/SymbolPrimitive;", "Lorg/partiql/lang/domains/PartiqlAst$FromSource$Scan;", "transformFromSourceUnpivot_asAlias", "Lorg/partiql/lang/domains/PartiqlAst$FromSource$Unpivot;", "lang"})
    private static final class InnerFromSourceAliasVisitorTransform
    extends VisitorTransformBase {
        private int fromSourceCounter;

        @Override
        @Nullable
        public SymbolPrimitive transformFromSourceScan_asAlias(@NotNull PartiqlAst.FromSource.Scan node) {
            Intrinsics.checkParameterIsNotNull(node, "node");
            int n = this.fromSourceCounter;
            this.fromSourceCounter = n + 1;
            int thisFromSourceIndex = n;
            SymbolPrimitive symbolPrimitive = node.getAsAlias();
            if (symbolPrimitive == null) {
                symbolPrimitive = new SymbolPrimitive(PartiqlAstExtensionsKt.extractColumnAlias(node.getExpr(), thisFromSourceIndex), UtilKt.extractSourceLocation(node));
            }
            return symbolPrimitive;
        }

        @Override
        @Nullable
        public SymbolPrimitive transformFromSourceUnpivot_asAlias(@NotNull PartiqlAst.FromSource.Unpivot node) {
            Intrinsics.checkParameterIsNotNull(node, "node");
            int n = this.fromSourceCounter;
            this.fromSourceCounter = n + 1;
            int thisFromSourceIndex = n;
            SymbolPrimitive symbolPrimitive = node.getAsAlias();
            if (symbolPrimitive == null) {
                symbolPrimitive = new SymbolPrimitive(PartiqlAstExtensionsKt.extractColumnAlias(node.getExpr(), thisFromSourceIndex), UtilKt.extractSourceLocation(node));
            }
            return symbolPrimitive;
        }

        @Override
        @NotNull
        public PartiqlAst.Expr transformExprSelect(@NotNull PartiqlAst.Expr.Select node) {
            Intrinsics.checkParameterIsNotNull(node, "node");
            return new FromSourceAliasVisitorTransform().transformExprSelect(node);
        }
    }
}

