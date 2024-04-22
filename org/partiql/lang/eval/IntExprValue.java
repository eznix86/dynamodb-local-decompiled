/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package org.partiql.lang.eval;

import com.amazon.ion.IonInt;
import com.amazon.ion.IonSystem;
import com.amazon.ion.IonValue;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.partiql.lang.eval.ExprValueType;
import org.partiql.lang.eval.ScalarExprValue;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0002\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\b\u0010\u000f\u001a\u00020\u0010H\u0016J\r\u0010\u0011\u001a\u00020\u0005H\u0016\u00a2\u0006\u0002\u0010\u0012R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0014\u0010\t\u001a\u00020\nX\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000e\u00a8\u0006\u0013"}, d2={"Lorg/partiql/lang/eval/IntExprValue;", "Lorg/partiql/lang/eval/ScalarExprValue;", "ion", "Lcom/amazon/ion/IonSystem;", "value", "", "(Lcom/amazon/ion/IonSystem;J)V", "getIon", "()Lcom/amazon/ion/IonSystem;", "type", "Lorg/partiql/lang/eval/ExprValueType;", "getType", "()Lorg/partiql/lang/eval/ExprValueType;", "getValue", "()J", "ionValueFun", "Lcom/amazon/ion/IonValue;", "numberValue", "()Ljava/lang/Long;", "lang"})
final class IntExprValue
extends ScalarExprValue {
    @NotNull
    private final ExprValueType type;
    @NotNull
    private final IonSystem ion;
    private final long value;

    @Override
    @NotNull
    public ExprValueType getType() {
        return this.type;
    }

    @Override
    @NotNull
    public Long numberValue() {
        return this.value;
    }

    @Override
    @NotNull
    public IonValue ionValueFun() {
        IonInt ionInt = this.ion.newInt(this.value);
        Intrinsics.checkExpressionValueIsNotNull(ionInt, "ion.newInt(value)");
        return ionInt;
    }

    @NotNull
    public final IonSystem getIon() {
        return this.ion;
    }

    public final long getValue() {
        return this.value;
    }

    public IntExprValue(@NotNull IonSystem ion, long value) {
        Intrinsics.checkParameterIsNotNull(ion, "ion");
        this.ion = ion;
        this.value = value;
        this.type = ExprValueType.INT;
    }
}

