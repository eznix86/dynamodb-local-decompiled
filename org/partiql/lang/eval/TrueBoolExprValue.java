/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package org.partiql.lang.eval;

import com.amazon.ion.IonBool;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.partiql.lang.eval.BooleanExprValue;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u000f\u0010\u0007\u001a\u0004\u0018\u00010\bH\u0016\u00a2\u0006\u0002\u0010\tR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006\n"}, d2={"Lorg/partiql/lang/eval/TrueBoolExprValue;", "Lorg/partiql/lang/eval/BooleanExprValue;", "value", "Lcom/amazon/ion/IonBool;", "(Lcom/amazon/ion/IonBool;)V", "getValue", "()Lcom/amazon/ion/IonBool;", "booleanValue", "", "()Ljava/lang/Boolean;", "lang"})
final class TrueBoolExprValue
extends BooleanExprValue {
    @NotNull
    private final IonBool value;

    @Override
    @Nullable
    public Boolean booleanValue() {
        return true;
    }

    @NotNull
    public final IonBool getValue() {
        return this.value;
    }

    public TrueBoolExprValue(@NotNull IonBool value) {
        Intrinsics.checkParameterIsNotNull(value, "value");
        super(value);
        this.value = value;
    }
}

