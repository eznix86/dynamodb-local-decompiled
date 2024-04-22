/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package org.partiql.lang.eval;

import com.amazon.ion.IonContainer;
import com.amazon.ion.IonSequence;
import com.amazon.ion.IonStruct;
import com.amazon.ion.IonType;
import com.amazon.ion.IonValue;
import com.amazon.ion.Timestamp;
import java.time.LocalDate;
import java.util.Iterator;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.PropertyReference1Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KProperty;
import kotlin.sequences.SequencesKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.partiql.lang.eval.BaseExprValue;
import org.partiql.lang.eval.Bindings;
import org.partiql.lang.eval.ExprValue;
import org.partiql.lang.eval.ExprValueExtensionsKt;
import org.partiql.lang.eval.ExprValueFactory;
import org.partiql.lang.eval.ExprValueType;
import org.partiql.lang.eval.IonExprValue;
import org.partiql.lang.eval.IonStructBindings;
import org.partiql.lang.eval.Named;
import org.partiql.lang.eval.OrdinalBindings;
import org.partiql.lang.eval.Scalar;
import org.partiql.lang.eval.time.Time;
import org.partiql.lang.util.IonValueExtensionsKt;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000R\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010(\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0000\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\u000f\u0010 \u001a\b\u0012\u0004\u0012\u00020\t0!H\u0096\u0002J%\u0010\"\u001a\u0004\u0018\u0001H#\"\u0004\b\u0000\u0010#2\u000e\u0010\u001c\u001a\n\u0012\u0004\u0012\u0002H#\u0018\u00010$H\u0016\u00a2\u0006\u0002\u0010%R!\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b8VX\u0096\u0084\u0002\u00a2\u0006\f\n\u0004\b\f\u0010\r\u001a\u0004\b\n\u0010\u000bR\u0014\u0010\u0004\u001a\u00020\u0005X\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0010\u0010\u0010\u001a\u0004\u0018\u00010\u0011X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001b\u0010\u0012\u001a\u00020\u00138VX\u0096\u0084\u0002\u00a2\u0006\f\n\u0004\b\u0016\u0010\r\u001a\u0004\b\u0014\u0010\u0015R\u001b\u0010\u0017\u001a\u00020\u00188VX\u0096\u0084\u0002\u00a2\u0006\f\n\u0004\b\u001b\u0010\r\u001a\u0004\b\u0019\u0010\u001aR\u0014\u0010\u001c\u001a\u00020\u001dX\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u001fR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006&"}, d2={"Lorg/partiql/lang/eval/IonExprValue;", "Lorg/partiql/lang/eval/BaseExprValue;", "valueFactory", "Lorg/partiql/lang/eval/ExprValueFactory;", "ionValue", "Lcom/amazon/ion/IonValue;", "(Lorg/partiql/lang/eval/ExprValueFactory;Lcom/amazon/ion/IonValue;)V", "bindings", "Lorg/partiql/lang/eval/Bindings;", "Lorg/partiql/lang/eval/ExprValue;", "getBindings", "()Lorg/partiql/lang/eval/Bindings;", "bindings$delegate", "Lkotlin/Lazy;", "getIonValue", "()Lcom/amazon/ion/IonValue;", "namedFacet", "Lorg/partiql/lang/eval/Named;", "ordinalBindings", "Lorg/partiql/lang/eval/OrdinalBindings;", "getOrdinalBindings", "()Lorg/partiql/lang/eval/OrdinalBindings;", "ordinalBindings$delegate", "scalar", "Lorg/partiql/lang/eval/Scalar;", "getScalar", "()Lorg/partiql/lang/eval/Scalar;", "scalar$delegate", "type", "Lorg/partiql/lang/eval/ExprValueType;", "getType", "()Lorg/partiql/lang/eval/ExprValueType;", "iterator", "", "provideFacet", "T", "Ljava/lang/Class;", "(Ljava/lang/Class;)Ljava/lang/Object;", "lang"})
public final class IonExprValue
extends BaseExprValue {
    static final /* synthetic */ KProperty[] $$delegatedProperties;
    private final Named namedFacet;
    @NotNull
    private final ExprValueType type;
    @NotNull
    private final Lazy scalar$delegate;
    @NotNull
    private final Lazy bindings$delegate;
    @NotNull
    private final Lazy ordinalBindings$delegate;
    private final ExprValueFactory valueFactory;
    @NotNull
    private final IonValue ionValue;

    static {
        $$delegatedProperties = new KProperty[]{Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(IonExprValue.class), "scalar", "getScalar()Lorg/partiql/lang/eval/Scalar;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(IonExprValue.class), "bindings", "getBindings()Lorg/partiql/lang/eval/Bindings;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(IonExprValue.class), "ordinalBindings", "getOrdinalBindings()Lorg/partiql/lang/eval/OrdinalBindings;"))};
    }

    @Override
    @NotNull
    public ExprValueType getType() {
        return this.type;
    }

    @Override
    @NotNull
    public Scalar getScalar() {
        Lazy lazy = this.scalar$delegate;
        IonExprValue ionExprValue = this;
        KProperty kProperty = $$delegatedProperties[0];
        boolean bl = false;
        return (Scalar)lazy.getValue();
    }

    @Override
    @NotNull
    public Bindings<ExprValue> getBindings() {
        Lazy lazy = this.bindings$delegate;
        IonExprValue ionExprValue = this;
        KProperty kProperty = $$delegatedProperties[1];
        boolean bl = false;
        return (Bindings)lazy.getValue();
    }

    @Override
    @NotNull
    public OrdinalBindings getOrdinalBindings() {
        Lazy lazy = this.ordinalBindings$delegate;
        IonExprValue ionExprValue = this;
        KProperty kProperty = $$delegatedProperties[2];
        boolean bl = false;
        return (OrdinalBindings)lazy.getValue();
    }

    @Override
    @NotNull
    public Iterator<ExprValue> iterator() {
        IonValue ionValue2 = this.getIonValue();
        return ionValue2 instanceof IonContainer ? SequencesKt.map(IonValueExtensionsKt.asSequence(this.getIonValue()), (Function1)new Function1<IonValue, ExprValue>(this){
            final /* synthetic */ IonExprValue this$0;

            @NotNull
            public final ExprValue invoke(@NotNull IonValue v) {
                Intrinsics.checkParameterIsNotNull(v, "v");
                return IonExprValue.access$getValueFactory$p(this.this$0).newFromIonValue(v);
            }
            {
                this.this$0 = ionExprValue;
                super(1);
            }
        }).iterator() : CollectionsKt.emptyList().iterator();
    }

    @Override
    @Nullable
    public <T> T provideFacet(@Nullable Class<T> type) {
        Class<T> clazz = type;
        return (T)(Intrinsics.areEqual(clazz, Named.class) ? this.namedFacet : null);
    }

    @Override
    @NotNull
    public IonValue getIonValue() {
        return this.ionValue;
    }

    public IonExprValue(@NotNull ExprValueFactory valueFactory, @NotNull IonValue ionValue2) {
        ExprValueType exprValueType;
        Named named;
        Intrinsics.checkParameterIsNotNull(valueFactory, "valueFactory");
        Intrinsics.checkParameterIsNotNull(ionValue2, "ionValue");
        this.valueFactory = valueFactory;
        this.ionValue = ionValue2;
        if (this.valueFactory.getIon() != this.getIonValue().getSystem()) {
            throw (Throwable)new IllegalArgumentException("valueFactory must have the same instance of IonSystem as ionValue");
        }
        if (this.getIonValue().getFieldName() != null) {
            String string = this.getIonValue().getFieldName();
            Intrinsics.checkExpressionValueIsNotNull(string, "ionValue.fieldName");
            named = ExprValueExtensionsKt.asNamed(this.valueFactory.newString(string));
        } else {
            named = this.namedFacet = this.getIonValue().getType() != IonType.DATAGRAM && this.getIonValue().getContainer() != null && IonValueExtensionsKt.getOrdinal(this.getIonValue()) >= 0 ? ExprValueExtensionsKt.asNamed(this.valueFactory.newInt(IonValueExtensionsKt.getOrdinal(this.getIonValue()))) : null;
        }
        if (this.getIonValue().isNullValue()) {
            exprValueType = ExprValueType.NULL;
        } else {
            IonType ionType = this.getIonValue().getType();
            Intrinsics.checkExpressionValueIsNotNull((Object)ionType, "ionValue.type");
            exprValueType = ExprValueType.Companion.fromIonType(ionType);
        }
        this.type = exprValueType;
        this.scalar$delegate = LazyKt.lazy((Function0)new Function0<scalar.1>(this){
            final /* synthetic */ IonExprValue this$0;

            @NotNull
            public final scalar.1 invoke() {
                return new Scalar(this){
                    final /* synthetic */ scalar.2 this$0;

                    @Nullable
                    public Boolean booleanValue() {
                        return IonValueExtensionsKt.booleanValueOrNull(this.this$0.this$0.getIonValue());
                    }

                    @Nullable
                    public Number numberValue() {
                        return IonValueExtensionsKt.numberValueOrNull(this.this$0.this$0.getIonValue());
                    }

                    @Nullable
                    public Timestamp timestampValue() {
                        return IonValueExtensionsKt.timestampValueOrNull(this.this$0.this$0.getIonValue());
                    }

                    @Nullable
                    public String stringValue() {
                        return IonValueExtensionsKt.stringValueOrNull(this.this$0.this$0.getIonValue());
                    }

                    @Nullable
                    public byte[] bytesValue() {
                        return IonValueExtensionsKt.bytesValueOrNull(this.this$0.this$0.getIonValue());
                    }
                    {
                        this.this$0 = $outer;
                    }

                    @Nullable
                    public LocalDate dateValue() {
                        return Scalar.DefaultImpls.dateValue(this);
                    }

                    @Nullable
                    public Time timeValue() {
                        return Scalar.DefaultImpls.timeValue(this);
                    }
                };
            }
            {
                this.this$0 = ionExprValue;
                super(0);
            }
        });
        this.bindings$delegate = LazyKt.lazy((Function0)new Function0<Bindings<ExprValue>>(this){
            final /* synthetic */ IonExprValue this$0;

            @NotNull
            public final Bindings<ExprValue> invoke() {
                return this.this$0.getIonValue() instanceof IonStruct ? (Bindings)new IonStructBindings(IonExprValue.access$getValueFactory$p(this.this$0), (IonStruct)this.this$0.getIonValue()) : Bindings.Companion.empty();
            }
            {
                this.this$0 = ionExprValue;
                super(0);
            }
        });
        this.ordinalBindings$delegate = LazyKt.lazy((Function0)new Function0<ordinalBindings.1>(this){
            final /* synthetic */ IonExprValue this$0;

            @NotNull
            public final ordinalBindings.1 invoke() {
                return new OrdinalBindings(this){
                    final /* synthetic */ ordinalBindings.2 this$0;

                    @Nullable
                    public ExprValue get(int index) {
                        ExprValue exprValue2;
                        IonValue ionValue2 = this.this$0.this$0.getIonValue();
                        if (ionValue2 instanceof IonSequence) {
                            if (index < 0) {
                                exprValue2 = null;
                            } else if (index >= ((IonSequence)this.this$0.this$0.getIonValue()).size()) {
                                exprValue2 = null;
                            } else {
                                ExprValueFactory exprValueFactory = IonExprValue.access$getValueFactory$p(this.this$0.this$0);
                                IonValue ionValue3 = ((IonSequence)this.this$0.this$0.getIonValue()).get(index);
                                Intrinsics.checkExpressionValueIsNotNull(ionValue3, "ionValue[index]");
                                exprValue2 = ExprValueExtensionsKt.namedValue(exprValueFactory.newFromIonValue(ionValue3), IonExprValue.access$getValueFactory$p(this.this$0.this$0).newInt(index));
                            }
                        } else {
                            exprValue2 = null;
                        }
                        return exprValue2;
                    }
                    {
                        this.this$0 = $outer;
                    }
                };
            }
            {
                this.this$0 = ionExprValue;
                super(0);
            }
        });
    }

    public static final /* synthetic */ ExprValueFactory access$getValueFactory$p(IonExprValue $this) {
        return $this.valueFactory;
    }
}

