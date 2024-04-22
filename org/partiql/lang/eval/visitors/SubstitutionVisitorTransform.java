/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package org.partiql.lang.eval.visitors;

import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.partiql.lang.domains.PartiqlAst;
import org.partiql.lang.domains.UtilKt;
import org.partiql.lang.eval.visitors.SubstitutionPair;
import org.partiql.lang.eval.visitors.VisitorTransformBase;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0016\u0018\u00002\u00020\u0001:\u0001\u000bB\u0019\u0012\u0012\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u0003\u00a2\u0006\u0002\u0010\u0006J\u0010\u0010\t\u001a\u00020\u00042\u0006\u0010\n\u001a\u00020\u0004H\u0016R \u0010\u0002\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u0003X\u0084\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\b\u00a8\u0006\f"}, d2={"Lorg/partiql/lang/eval/visitors/SubstitutionVisitorTransform;", "Lorg/partiql/lang/eval/visitors/VisitorTransformBase;", "substitutions", "", "Lorg/partiql/lang/domains/PartiqlAst$Expr;", "Lorg/partiql/lang/eval/visitors/SubstitutionPair;", "(Ljava/util/Map;)V", "getSubstitutions", "()Ljava/util/Map;", "transformExpr", "node", "MetaVisitorTransform", "lang"})
public class SubstitutionVisitorTransform
extends VisitorTransformBase {
    @NotNull
    private final Map<PartiqlAst.Expr, SubstitutionPair> substitutions;

    @Override
    @NotNull
    public PartiqlAst.Expr transformExpr(@NotNull PartiqlAst.Expr node) {
        Object object;
        block3: {
            block2: {
                Intrinsics.checkParameterIsNotNull(node, "node");
                SubstitutionPair matchingSubstitution = this.substitutions.get(node);
                object = matchingSubstitution;
                if (object == null) break block2;
                SubstitutionPair substitutionPair = object;
                boolean bl = false;
                boolean bl2 = false;
                SubstitutionPair ms = substitutionPair;
                boolean bl3 = false;
                Map<String, Object> map2 = UtilKt.extractSourceLocation(node);
                boolean bl4 = false;
                boolean bl5 = false;
                Map<String, Object> sl = map2;
                boolean bl6 = false;
                PartiqlAst.Expr expr = new MetaVisitorTransform(sl).transformExpr(ms.getReplacement());
                object = expr;
                if (expr != null) break block3;
            }
            object = super.transformExpr(node);
        }
        return object;
    }

    @NotNull
    protected final Map<PartiqlAst.Expr, SubstitutionPair> getSubstitutions() {
        return this.substitutions;
    }

    public SubstitutionVisitorTransform(@NotNull Map<PartiqlAst.Expr, SubstitutionPair> substitutions) {
        Intrinsics.checkParameterIsNotNull(substitutions, "substitutions");
        this.substitutions = substitutions;
    }

    @Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0010\u000e\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0086\u0004\u0018\u00002\u00020\u0001B\u001d\u0012\u0016\u0010\u0002\u001a\u0012\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u0003j\u0002`\u0006\u00a2\u0006\u0002\u0010\u0007J0\u0010\b\u001a\u0012\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u0003j\u0002`\u00062\u0016\u0010\t\u001a\u0012\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u0003j\u0002`\u0006H\u0016R\u001e\u0010\u0002\u001a\u0012\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u0003j\u0002`\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\n"}, d2={"Lorg/partiql/lang/eval/visitors/SubstitutionVisitorTransform$MetaVisitorTransform;", "Lorg/partiql/lang/eval/visitors/VisitorTransformBase;", "newMetas", "", "", "", "Lcom/amazon/ionelement/api/MetaContainer;", "(Lorg/partiql/lang/eval/visitors/SubstitutionVisitorTransform;Ljava/util/Map;)V", "transformMetas", "metas", "lang"})
    public final class MetaVisitorTransform
    extends VisitorTransformBase {
        private final Map<String, Object> newMetas;

        @NotNull
        public Map<String, Object> transformMetas(@NotNull Map<String, ? extends Object> metas) {
            Intrinsics.checkParameterIsNotNull(metas, "metas");
            return this.newMetas;
        }

        public MetaVisitorTransform(Map<String, ? extends Object> newMetas) {
            Intrinsics.checkParameterIsNotNull(newMetas, "newMetas");
            this.newMetas = newMetas;
        }
    }
}

