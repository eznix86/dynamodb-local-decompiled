/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package org.partiql.lang.eval;

import com.amazon.ion.IonBool;
import com.amazon.ion.Timestamp;
import java.time.LocalDate;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.partiql.lang.eval.BaseExprValue;
import org.partiql.lang.eval.ExprValueType;
import org.partiql.lang.eval.Scalar;
import org.partiql.lang.eval.time.Time;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0003\b\"\u0018\u00002\u00020\u00012\u00020\u0002B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u00a2\u0006\u0002\u0010\u0005R\u0014\u0010\u0006\u001a\u00020\u0004X\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0014\u0010\t\u001a\u00020\u00028VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\n\u0010\u000bR\u0014\u0010\f\u001a\u00020\r8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u000e\u0010\u000f\u00a8\u0006\u0010"}, d2={"Lorg/partiql/lang/eval/BooleanExprValue;", "Lorg/partiql/lang/eval/BaseExprValue;", "Lorg/partiql/lang/eval/Scalar;", "value", "Lcom/amazon/ion/IonBool;", "(Lcom/amazon/ion/IonBool;)V", "ionValue", "getIonValue", "()Lcom/amazon/ion/IonBool;", "scalar", "getScalar", "()Lorg/partiql/lang/eval/Scalar;", "type", "Lorg/partiql/lang/eval/ExprValueType;", "getType", "()Lorg/partiql/lang/eval/ExprValueType;", "lang"})
abstract class BooleanExprValue
extends BaseExprValue
implements Scalar {
    @NotNull
    private final IonBool ionValue;

    @Override
    @NotNull
    public Scalar getScalar() {
        return this;
    }

    @Override
    @NotNull
    public ExprValueType getType() {
        return ExprValueType.BOOL;
    }

    @Override
    @NotNull
    public IonBool getIonValue() {
        return this.ionValue;
    }

    public BooleanExprValue(@NotNull IonBool value) {
        Intrinsics.checkParameterIsNotNull(value, "value");
        this.ionValue = value;
    }

    @Override
    @Nullable
    public Boolean booleanValue() {
        return Scalar.DefaultImpls.booleanValue(this);
    }

    @Override
    @Nullable
    public Number numberValue() {
        return Scalar.DefaultImpls.numberValue(this);
    }

    @Override
    @Nullable
    public Timestamp timestampValue() {
        return Scalar.DefaultImpls.timestampValue(this);
    }

    @Override
    @Nullable
    public LocalDate dateValue() {
        return Scalar.DefaultImpls.dateValue(this);
    }

    @Override
    @Nullable
    public Time timeValue() {
        return Scalar.DefaultImpls.timeValue(this);
    }

    @Override
    @Nullable
    public String stringValue() {
        return Scalar.DefaultImpls.stringValue(this);
    }

    @Override
    @Nullable
    public byte[] bytesValue() {
        return Scalar.DefaultImpls.bytesValue(this);
    }
}

