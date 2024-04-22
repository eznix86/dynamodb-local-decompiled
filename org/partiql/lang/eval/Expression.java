/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package org.partiql.lang.eval;

import kotlin.Metadata;
import org.jetbrains.annotations.NotNull;
import org.partiql.lang.eval.EvaluationSession;
import org.partiql.lang.eval.ExprValue;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&\u00a8\u0006\u0006"}, d2={"Lorg/partiql/lang/eval/Expression;", "", "eval", "Lorg/partiql/lang/eval/ExprValue;", "session", "Lorg/partiql/lang/eval/EvaluationSession;", "lang"})
public interface Expression {
    @NotNull
    public ExprValue eval(@NotNull EvaluationSession var1);
}

