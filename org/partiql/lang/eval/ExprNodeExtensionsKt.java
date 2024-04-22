/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package org.partiql.lang.eval;

import com.amazon.ion.IonString;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.partiql.lang.ast.ExprNode;
import org.partiql.lang.ast.Literal;
import org.partiql.lang.ast.Path;
import org.partiql.lang.ast.PathComponent;
import org.partiql.lang.ast.PathComponentExpr;
import org.partiql.lang.ast.VariableReference;
import org.partiql.lang.eval.StandardNamesKt;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=2, d1={"\u0000\u0016\n\u0000\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0000\u001a\u0012\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0004\u001a\u0012\u0010\u0000\u001a\u00020\u0001*\u00020\u00052\u0006\u0010\u0003\u001a\u00020\u0004\u00a8\u0006\u0006"}, d2={"extractColumnAlias", "", "Lorg/partiql/lang/ast/ExprNode;", "idx", "", "Lorg/partiql/lang/ast/Path;", "lang"})
public final class ExprNodeExtensionsKt {
    /*
     * WARNING - void declaration
     */
    @NotNull
    public static final String extractColumnAlias(@NotNull ExprNode $this$extractColumnAlias, int idx) {
        String string;
        Intrinsics.checkParameterIsNotNull($this$extractColumnAlias, "$this$extractColumnAlias");
        ExprNode exprNode = $this$extractColumnAlias;
        if (exprNode instanceof VariableReference) {
            void var3_4;
            VariableReference variableReference = (VariableReference)$this$extractColumnAlias;
            String name = variableReference.component1();
            string = var3_4;
        } else {
            string = exprNode instanceof Path ? ExprNodeExtensionsKt.extractColumnAlias((Path)$this$extractColumnAlias, idx) : StandardNamesKt.syntheticColumnName(idx);
        }
        return string;
    }

    @NotNull
    public static final String extractColumnAlias(@NotNull Path $this$extractColumnAlias, int idx) {
        String string;
        PathComponent nameOrigin;
        Intrinsics.checkParameterIsNotNull($this$extractColumnAlias, "$this$extractColumnAlias");
        Path path = $this$extractColumnAlias;
        List<PathComponent> components = path.component2();
        PathComponent pathComponent = nameOrigin = CollectionsKt.last(components);
        if (pathComponent instanceof PathComponentExpr) {
            ExprNode maybeLiteral = ((PathComponentExpr)nameOrigin).getExpr();
            String string2 = maybeLiteral instanceof Literal && ((Literal)maybeLiteral).getIonValue() instanceof IonString ? ((IonString)((Literal)maybeLiteral).getIonValue()).stringValue() : StandardNamesKt.syntheticColumnName(idx);
            string = string2;
            Intrinsics.checkExpressionValueIsNotNull(string2, "when {\n                m\u2026mnName(idx)\n            }");
        } else {
            string = StandardNamesKt.syntheticColumnName(idx);
        }
        return string;
    }
}

