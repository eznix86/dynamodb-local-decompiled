/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package org.partiql.lang.eval;

import com.amazon.ion.IonSequence;
import com.amazon.ion.IonSystem;
import com.amazon.ion.IonValue;
import java.util.Collection;
import java.util.Iterator;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.PropertyReference1Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KProperty;
import kotlin.sequences.Sequence;
import org.jetbrains.annotations.NotNull;
import org.partiql.lang.eval.BaseExprValue;
import org.partiql.lang.eval.ExceptionsKt;
import org.partiql.lang.eval.ExprValue;
import org.partiql.lang.eval.ExprValueType;
import org.partiql.lang.eval.OrdinalBindings;
import org.partiql.lang.eval.SequenceExprValue$WhenMappings;
import org.partiql.lang.eval.StructExprValue;
import org.partiql.lang.util.IonValueExtensionsKt;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010(\n\u0000\b\u0000\u0018\u00002\u00020\u0001B#\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007\u00a2\u0006\u0002\u0010\tJ\u000f\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\b0\u0018H\u0096\u0002R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001b\u0010\n\u001a\u00020\u000b8VX\u0096\u0084\u0002\u00a2\u0006\f\n\u0004\b\u000e\u0010\u000f\u001a\u0004\b\f\u0010\rR\u001b\u0010\u0010\u001a\u00020\u00118VX\u0096\u0084\u0002\u00a2\u0006\f\n\u0004\b\u0014\u0010\u000f\u001a\u0004\b\u0012\u0010\u0013R\u0014\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0004\u001a\u00020\u0005X\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016\u00a8\u0006\u0019"}, d2={"Lorg/partiql/lang/eval/SequenceExprValue;", "Lorg/partiql/lang/eval/BaseExprValue;", "ion", "Lcom/amazon/ion/IonSystem;", "type", "Lorg/partiql/lang/eval/ExprValueType;", "sequence", "Lkotlin/sequences/Sequence;", "Lorg/partiql/lang/eval/ExprValue;", "(Lcom/amazon/ion/IonSystem;Lorg/partiql/lang/eval/ExprValueType;Lkotlin/sequences/Sequence;)V", "ionValue", "Lcom/amazon/ion/IonValue;", "getIonValue", "()Lcom/amazon/ion/IonValue;", "ionValue$delegate", "Lkotlin/Lazy;", "ordinalBindings", "Lorg/partiql/lang/eval/OrdinalBindings;", "getOrdinalBindings", "()Lorg/partiql/lang/eval/OrdinalBindings;", "ordinalBindings$delegate", "getType", "()Lorg/partiql/lang/eval/ExprValueType;", "iterator", "", "lang"})
public final class SequenceExprValue
extends BaseExprValue {
    static final /* synthetic */ KProperty[] $$delegatedProperties;
    @NotNull
    private final Lazy ionValue$delegate;
    @NotNull
    private final Lazy ordinalBindings$delegate;
    private final IonSystem ion;
    @NotNull
    private final ExprValueType type;
    private final Sequence<ExprValue> sequence;

    static {
        $$delegatedProperties = new KProperty[]{Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(SequenceExprValue.class), "ionValue", "getIonValue()Lcom/amazon/ion/IonValue;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(SequenceExprValue.class), "ordinalBindings", "getOrdinalBindings()Lorg/partiql/lang/eval/OrdinalBindings;"))};
    }

    @Override
    @NotNull
    public IonValue getIonValue() {
        Lazy lazy = this.ionValue$delegate;
        SequenceExprValue sequenceExprValue = this;
        KProperty kProperty = $$delegatedProperties[0];
        boolean bl = false;
        return (IonValue)lazy.getValue();
    }

    @Override
    @NotNull
    public OrdinalBindings getOrdinalBindings() {
        Lazy lazy = this.ordinalBindings$delegate;
        SequenceExprValue sequenceExprValue = this;
        KProperty kProperty = $$delegatedProperties[1];
        boolean bl = false;
        return (OrdinalBindings)lazy.getValue();
    }

    @Override
    @NotNull
    public Iterator<ExprValue> iterator() {
        return this.sequence.iterator();
    }

    @Override
    @NotNull
    public ExprValueType getType() {
        return this.type;
    }

    public SequenceExprValue(@NotNull IonSystem ion, @NotNull ExprValueType type, @NotNull Sequence<? extends ExprValue> sequence) {
        Intrinsics.checkParameterIsNotNull(ion, "ion");
        Intrinsics.checkParameterIsNotNull((Object)type, "type");
        Intrinsics.checkParameterIsNotNull(sequence, "sequence");
        this.ion = ion;
        this.type = type;
        this.sequence = sequence;
        if (!this.getType().isSequence()) {
            Void void_ = ExceptionsKt.errNoContext("Cannot bind non-sequence type to sequence: " + (Object)((Object)this.getType()), true);
            throw null;
        }
        this.ionValue$delegate = LazyKt.lazy((Function0)new Function0<IonValue>(this){
            final /* synthetic */ SequenceExprValue this$0;

            /*
             * WARNING - void declaration
             */
            @NotNull
            public final IonValue invoke() {
                void var2_2;
                void $this$mapTo$iv;
                IonSequence ionSequence;
                Sequence sequence = SequenceExprValue.access$getSequence$p(this.this$0);
                switch (SequenceExprValue$WhenMappings.$EnumSwitchMapping$0[this.this$0.getType().ordinal()]) {
                    case 1: 
                    case 2: {
                        ionSequence = SequenceExprValue.access$getIon$p(this.this$0).newEmptyList();
                        break;
                    }
                    case 3: {
                        ionSequence = SequenceExprValue.access$getIon$p(this.this$0).newEmptySexp();
                        break;
                    }
                    default: {
                        throw (Throwable)new IllegalStateException("Invalid type: " + (Object)((Object)this.this$0.getType()));
                    }
                }
                Collection destination$iv = ionSequence;
                boolean $i$f$mapTo = false;
                for (T item$iv : $this$mapTo$iv) {
                    void it;
                    ExprValue exprValue2 = (ExprValue)item$iv;
                    Collection collection = destination$iv;
                    boolean bl = false;
                    IonValue ionValue2 = it instanceof StructExprValue ? (IonValue)((StructExprValue)it).createMutableValue$lang() : it.getIonValue().clone();
                    collection.add(ionValue2);
                }
                void v1 = var2_2;
                Intrinsics.checkExpressionValueIsNotNull(v1, "sequence\n            .ma\u2026lue.clone()\n            }");
                return IonValueExtensionsKt.seal((IonValue)v1);
            }
            {
                this.this$0 = sequenceExprValue;
                super(0);
            }
        });
        this.ordinalBindings$delegate = LazyKt.lazy((Function0)new Function0<OrdinalBindings>(this){
            final /* synthetic */ SequenceExprValue this$0;

            @NotNull
            public final OrdinalBindings invoke() {
                OrdinalBindings ordinalBindings2;
                switch (SequenceExprValue$WhenMappings.$EnumSwitchMapping$1[this.this$0.getType().ordinal()]) {
                    case 1: {
                        ordinalBindings2 = OrdinalBindings.EMPTY;
                        break;
                    }
                    default: {
                        ordinalBindings2 = OrdinalBindings.Companion.ofList(CollectionsKt.toList(this.this$0));
                    }
                }
                return ordinalBindings2;
            }
            {
                this.this$0 = sequenceExprValue;
                super(0);
            }
        });
    }

    public static final /* synthetic */ Sequence access$getSequence$p(SequenceExprValue $this) {
        return $this.sequence;
    }

    public static final /* synthetic */ IonSystem access$getIon$p(SequenceExprValue $this) {
        return $this.ion;
    }
}

