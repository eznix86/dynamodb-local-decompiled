/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package org.partiql.lang.eval;

import com.amazon.ion.IonNull;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.partiql.lang.eval.BaseExprValue;
import org.partiql.lang.eval.ExprValueType;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004R\u0014\u0010\u0005\u001a\u00020\u0003X\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0014\u0010\b\u001a\u00020\t8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\n\u0010\u000b\u00a8\u0006\f"}, d2={"Lorg/partiql/lang/eval/NullExprValue;", "Lorg/partiql/lang/eval/BaseExprValue;", "value", "Lcom/amazon/ion/IonNull;", "(Lcom/amazon/ion/IonNull;)V", "ionValue", "getIonValue", "()Lcom/amazon/ion/IonNull;", "type", "Lorg/partiql/lang/eval/ExprValueType;", "getType", "()Lorg/partiql/lang/eval/ExprValueType;", "lang"})
final class NullExprValue
extends BaseExprValue {
    @NotNull
    private final IonNull ionValue;

    @Override
    @NotNull
    public IonNull getIonValue() {
        return this.ionValue;
    }

    @Override
    @NotNull
    public ExprValueType getType() {
        return ExprValueType.NULL;
    }

    public NullExprValue(@NotNull IonNull value) {
        Intrinsics.checkParameterIsNotNull(value, "value");
        this.ionValue = value;
    }
}

