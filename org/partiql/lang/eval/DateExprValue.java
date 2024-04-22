/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package org.partiql.lang.eval;

import com.amazon.ion.IonSystem;
import com.amazon.ion.IonTimestamp;
import com.amazon.ion.IonValue;
import com.amazon.ion.Timestamp;
import java.time.LocalDate;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.partiql.lang.errors.ErrorCode;
import org.partiql.lang.eval.ExceptionsKt;
import org.partiql.lang.eval.ExprValueType;
import org.partiql.lang.eval.ScalarExprValue;
import org.partiql.lang.util.IonValueExtensionsKt;
import org.partiql.lang.util.PropertyMapHelpersKt;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0002\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\b\u0010\u0011\u001a\u00020\u0012H\u0002J\n\u0010\u0013\u001a\u0004\u0018\u00010\u0005H\u0016J\b\u0010\u0014\u001a\u00020\u0012H\u0016R\u000e\u0010\u0007\u001a\u00020\bX\u0082D\u00a2\u0006\u0002\n\u0000R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0014\u0010\u000b\u001a\u00020\fX\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010\u00a8\u0006\u0015"}, d2={"Lorg/partiql/lang/eval/DateExprValue;", "Lorg/partiql/lang/eval/ScalarExprValue;", "ion", "Lcom/amazon/ion/IonSystem;", "value", "Ljava/time/LocalDate;", "(Lcom/amazon/ion/IonSystem;Ljava/time/LocalDate;)V", "PARTIQL_DATE_ANNOTATION", "", "getIon", "()Lcom/amazon/ion/IonSystem;", "type", "Lorg/partiql/lang/eval/ExprValueType;", "getType", "()Lorg/partiql/lang/eval/ExprValueType;", "getValue", "()Ljava/time/LocalDate;", "createIonDate", "Lcom/amazon/ion/IonValue;", "dateValue", "ionValueFun", "lang"})
final class DateExprValue
extends ScalarExprValue {
    private final String PARTIQL_DATE_ANNOTATION = "$partiql_date";
    @NotNull
    private final ExprValueType type;
    @NotNull
    private final IonSystem ion;
    @NotNull
    private final LocalDate value;

    private final IonValue createIonDate() {
        IonTimestamp ionTimestamp = this.ion.newTimestamp(Timestamp.forDay(this.value.getYear(), this.value.getMonthValue(), this.value.getDayOfMonth()));
        boolean bl = false;
        boolean bl2 = false;
        IonTimestamp $this$apply = ionTimestamp;
        boolean bl3 = false;
        $this$apply.addTypeAnnotation(this.PARTIQL_DATE_ANNOTATION);
        IonTimestamp ionTimestamp2 = ionTimestamp;
        Intrinsics.checkExpressionValueIsNotNull(ionTimestamp2, "ion.newTimestamp(Timesta\u2026ATE_ANNOTATION)\n        }");
        return IonValueExtensionsKt.seal(ionTimestamp2);
    }

    @Override
    @NotNull
    public ExprValueType getType() {
        return this.type;
    }

    @Override
    @Nullable
    public LocalDate dateValue() {
        return this.value;
    }

    @Override
    @NotNull
    public IonValue ionValueFun() {
        return this.createIonDate();
    }

    @NotNull
    public final IonSystem getIon() {
        return this.ion;
    }

    @NotNull
    public final LocalDate getValue() {
        return this.value;
    }

    public DateExprValue(@NotNull IonSystem ion, @NotNull LocalDate value) {
        Intrinsics.checkParameterIsNotNull(ion, "ion");
        Intrinsics.checkParameterIsNotNull(value, "value");
        this.ion = ion;
        this.value = value;
        if (this.value.getYear() < 0 || this.value.getYear() > 9999) {
            Void void_ = ExceptionsKt.err("Year should be in the range 0 to 9999 inclusive.", ErrorCode.EVALUATOR_DATE_FIELD_OUT_OF_RANGE, PropertyMapHelpersKt.propertyValueMapOf(new Pair[0]), false);
            throw null;
        }
        this.PARTIQL_DATE_ANNOTATION = "$partiql_date";
        this.type = ExprValueType.DATE;
    }
}

