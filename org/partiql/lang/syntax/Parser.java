/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package org.partiql.lang.syntax;

import com.amazon.ion.IonSexp;
import kotlin.Deprecated;
import kotlin.Metadata;
import org.jetbrains.annotations.NotNull;
import org.partiql.lang.ast.ExprNode;
import org.partiql.lang.domains.PartiqlAst;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H'J\u0010\u0010\u0006\u001a\u00020\u00072\u0006\u0010\u0004\u001a\u00020\u0005H&J\u0010\u0010\b\u001a\u00020\t2\u0006\u0010\u0004\u001a\u00020\u0005H&\u00a8\u0006\n"}, d2={"Lorg/partiql/lang/syntax/Parser;", "", "parse", "Lcom/amazon/ion/IonSexp;", "source", "", "parseAstStatement", "Lorg/partiql/lang/domains/PartiqlAst$Statement;", "parseExprNode", "Lorg/partiql/lang/ast/ExprNode;", "lang"})
public interface Parser {
    @NotNull
    public ExprNode parseExprNode(@NotNull String var1);

    @NotNull
    public PartiqlAst.Statement parseAstStatement(@NotNull String var1);

    @Deprecated(message="Please use parseExprNode() instead--the return value can be deserialized to backward-compatible IonSexp.")
    @NotNull
    public IonSexp parse(@NotNull String var1);
}

