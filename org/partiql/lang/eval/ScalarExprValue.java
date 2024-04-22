/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package org.partiql.lang.eval;

import com.amazon.ion.IonValue;
import com.amazon.ion.Timestamp;
import java.time.LocalDate;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.LazyThreadSafetyMode;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.PropertyReference1Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.partiql.lang.eval.BaseExprValue;
import org.partiql.lang.eval.Scalar;
import org.partiql.lang.eval.time.Time;
import org.partiql.lang.util.IonValueExtensionsKt;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\t\b\"\u0018\u00002\u00020\u00012\u00020\u0002B\u0005\u00a2\u0006\u0002\u0010\u0003J\b\u0010\r\u001a\u00020\u0005H&R\u001b\u0010\u0004\u001a\u00020\u00058VX\u0096\u0084\u0002\u00a2\u0006\f\n\u0004\b\b\u0010\t\u001a\u0004\b\u0006\u0010\u0007R\u0014\u0010\n\u001a\u00020\u00028VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u000b\u0010\f\u00a8\u0006\u000e"}, d2={"Lorg/partiql/lang/eval/ScalarExprValue;", "Lorg/partiql/lang/eval/BaseExprValue;", "Lorg/partiql/lang/eval/Scalar;", "()V", "ionValue", "Lcom/amazon/ion/IonValue;", "getIonValue", "()Lcom/amazon/ion/IonValue;", "ionValue$delegate", "Lkotlin/Lazy;", "scalar", "getScalar", "()Lorg/partiql/lang/eval/Scalar;", "ionValueFun", "lang"})
abstract class ScalarExprValue
extends BaseExprValue
implements Scalar {
    static final /* synthetic */ KProperty[] $$delegatedProperties;
    @NotNull
    private final Lazy ionValue$delegate = LazyKt.lazy(LazyThreadSafetyMode.PUBLICATION, (Function0)new Function0<IonValue>(this){
        final /* synthetic */ ScalarExprValue this$0;

        @NotNull
        public final IonValue invoke() {
            return IonValueExtensionsKt.seal(this.this$0.ionValueFun());
        }
        {
            this.this$0 = scalarExprValue;
            super(0);
        }
    });

    static {
        $$delegatedProperties = new KProperty[]{Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(ScalarExprValue.class), "ionValue", "getIonValue()Lcom/amazon/ion/IonValue;"))};
    }

    @Override
    @NotNull
    public Scalar getScalar() {
        return this;
    }

    @NotNull
    public abstract IonValue ionValueFun();

    @Override
    @NotNull
    public IonValue getIonValue() {
        Lazy lazy = this.ionValue$delegate;
        ScalarExprValue scalarExprValue = this;
        KProperty kProperty = $$delegatedProperties[0];
        boolean bl = false;
        return (IonValue)lazy.getValue();
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

