/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package org.partiql.lang.eval.builtins.storedprocedure;

import java.util.List;
import kotlin.Metadata;
import org.jetbrains.annotations.NotNull;
import org.partiql.lang.eval.EvaluationSession;
import org.partiql.lang.eval.ExprValue;
import org.partiql.lang.eval.builtins.storedprocedure.StoredProcedureSignature;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0000\bf\u0018\u00002\u00020\u0001J\u001e\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00070\u000bH&R\u0012\u0010\u0002\u001a\u00020\u0003X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005\u00a8\u0006\f"}, d2={"Lorg/partiql/lang/eval/builtins/storedprocedure/StoredProcedure;", "", "signature", "Lorg/partiql/lang/eval/builtins/storedprocedure/StoredProcedureSignature;", "getSignature", "()Lorg/partiql/lang/eval/builtins/storedprocedure/StoredProcedureSignature;", "call", "Lorg/partiql/lang/eval/ExprValue;", "session", "Lorg/partiql/lang/eval/EvaluationSession;", "args", "", "lang"})
public interface StoredProcedure {
    @NotNull
    public StoredProcedureSignature getSignature();

    @NotNull
    public ExprValue call(@NotNull EvaluationSession var1, @NotNull List<? extends ExprValue> var2);
}

