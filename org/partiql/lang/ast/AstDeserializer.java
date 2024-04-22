/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package org.partiql.lang.ast;

import com.amazon.ion.IonSexp;
import kotlin.Deprecated;
import kotlin.Metadata;
import org.jetbrains.annotations.NotNull;
import org.partiql.lang.ast.AstVersion;
import org.partiql.lang.ast.ExprNode;

@Deprecated(message="Please use PartiqlAst")
@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bg\u0018\u00002\u00020\u0001J\u0018\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H'\u00a8\u0006\b"}, d2={"Lorg/partiql/lang/ast/AstDeserializer;", "", "deserialize", "Lorg/partiql/lang/ast/ExprNode;", "sexp", "Lcom/amazon/ion/IonSexp;", "astVersion", "Lorg/partiql/lang/ast/AstVersion;", "lang"})
public interface AstDeserializer {
    @Deprecated(message="Please use PartiqlAst")
    @NotNull
    public ExprNode deserialize(@NotNull IonSexp var1, @NotNull AstVersion var2);
}

