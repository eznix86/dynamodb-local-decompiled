/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package org.partiql.lang.eval;

import com.amazon.ion.IonStruct;
import com.amazon.ion.IonSystem;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.partiql.lang.eval.ExprValueType;
import org.partiql.lang.eval.ScalarExprValue;
import org.partiql.lang.eval.time.Time;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\b\u0010\u000f\u001a\u00020\u0010H\u0016J\b\u0010\u0011\u001a\u00020\u0005H\u0016R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0014\u0010\t\u001a\u00020\nX\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000e\u00a8\u0006\u0012"}, d2={"Lorg/partiql/lang/eval/TimeExprValue;", "Lorg/partiql/lang/eval/ScalarExprValue;", "ion", "Lcom/amazon/ion/IonSystem;", "value", "Lorg/partiql/lang/eval/time/Time;", "(Lcom/amazon/ion/IonSystem;Lorg/partiql/lang/eval/time/Time;)V", "getIon", "()Lcom/amazon/ion/IonSystem;", "type", "Lorg/partiql/lang/eval/ExprValueType;", "getType", "()Lorg/partiql/lang/eval/ExprValueType;", "getValue", "()Lorg/partiql/lang/eval/time/Time;", "ionValueFun", "Lcom/amazon/ion/IonStruct;", "timeValue", "lang"})
final class TimeExprValue
extends ScalarExprValue {
    @NotNull
    private final ExprValueType type;
    @NotNull
    private final IonSystem ion;
    @NotNull
    private final Time value;

    @Override
    @NotNull
    public ExprValueType getType() {
        return this.type;
    }

    @Override
    @NotNull
    public Time timeValue() {
        return this.value;
    }

    @Override
    @NotNull
    public IonStruct ionValueFun() {
        return this.value.toIonValue(this.ion);
    }

    @NotNull
    public final IonSystem getIon() {
        return this.ion;
    }

    @NotNull
    public final Time getValue() {
        return this.value;
    }

    public TimeExprValue(@NotNull IonSystem ion, @NotNull Time value) {
        Intrinsics.checkParameterIsNotNull(ion, "ion");
        Intrinsics.checkParameterIsNotNull(value, "value");
        this.ion = ion;
        this.value = value;
        this.type = ExprValueType.TIME;
    }
}

