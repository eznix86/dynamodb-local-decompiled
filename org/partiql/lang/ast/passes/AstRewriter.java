/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package org.partiql.lang.ast.passes;

import kotlin.Deprecated;
import kotlin.Metadata;
import org.jetbrains.annotations.NotNull;
import org.partiql.lang.ast.ExprNode;

@Deprecated(message="New rewriters should implement PIG's PartiqlAst.VisitorTransform instead")
@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bg\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0003H&\u00a8\u0006\u0005"}, d2={"Lorg/partiql/lang/ast/passes/AstRewriter;", "", "rewriteExprNode", "Lorg/partiql/lang/ast/ExprNode;", "node", "lang"})
public interface AstRewriter {
    @NotNull
    public ExprNode rewriteExprNode(@NotNull ExprNode var1);
}

