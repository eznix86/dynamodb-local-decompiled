/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  com.amazon.ionelement.api.TextElement
 *  org.jetbrains.annotations.NotNull
 */
package org.partiql.lang.eval;

import com.amazon.ionelement.api.TextElement;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.partiql.lang.domains.PartiqlAst;
import org.partiql.lang.eval.StandardNamesKt;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=2, d1={"\u0000\u0016\n\u0000\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0000\u001a\u0012\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0004\u001a\u0012\u0010\u0000\u001a\u00020\u0001*\u00020\u00052\u0006\u0010\u0003\u001a\u00020\u0004\u00a8\u0006\u0006"}, d2={"extractColumnAlias", "", "Lorg/partiql/lang/domains/PartiqlAst$Expr;", "idx", "", "Lorg/partiql/lang/domains/PartiqlAst$Expr$Path;", "lang"})
public final class PartiqlAstExtensionsKt {
    @NotNull
    public static final String extractColumnAlias(@NotNull PartiqlAst.Expr $this$extractColumnAlias, int idx) {
        Intrinsics.checkParameterIsNotNull($this$extractColumnAlias, "$this$extractColumnAlias");
        PartiqlAst.Expr expr = $this$extractColumnAlias;
        return expr instanceof PartiqlAst.Expr.Id ? ((PartiqlAst.Expr.Id)$this$extractColumnAlias).getName().getText() : (expr instanceof PartiqlAst.Expr.Path ? PartiqlAstExtensionsKt.extractColumnAlias((PartiqlAst.Expr.Path)$this$extractColumnAlias, idx) : StandardNamesKt.syntheticColumnName(idx));
    }

    @NotNull
    public static final String extractColumnAlias(@NotNull PartiqlAst.Expr.Path $this$extractColumnAlias, int idx) {
        String string;
        Intrinsics.checkParameterIsNotNull($this$extractColumnAlias, "$this$extractColumnAlias");
        PartiqlAst.PathStep nameOrigin = CollectionsKt.last($this$extractColumnAlias.getSteps());
        if (nameOrigin instanceof PartiqlAst.PathStep.PathExpr) {
            PartiqlAst.Expr maybeLiteral = ((PartiqlAst.PathStep.PathExpr)nameOrigin).getIndex();
            string = maybeLiteral instanceof PartiqlAst.Expr.Lit && ((PartiqlAst.Expr.Lit)maybeLiteral).getValue() instanceof TextElement ? ((PartiqlAst.Expr.Lit)maybeLiteral).getValue().getTextValue() : StandardNamesKt.syntheticColumnName(idx);
        } else {
            string = StandardNamesKt.syntheticColumnName(idx);
        }
        return string;
    }
}

