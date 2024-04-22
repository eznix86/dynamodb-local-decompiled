/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package org.partiql.lang.eval;

import com.amazon.ion.IonStruct;
import com.amazon.ion.IonSystem;
import com.amazon.ion.IonValue;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.PropertyReference1Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KProperty;
import kotlin.sequences.Sequence;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.partiql.lang.eval.BaseExprValue;
import org.partiql.lang.eval.Bindings;
import org.partiql.lang.eval.ExceptionsKt;
import org.partiql.lang.eval.ExprValue;
import org.partiql.lang.eval.ExprValueExtensionsKt;
import org.partiql.lang.eval.ExprValueType;
import org.partiql.lang.eval.OrderedBindNames;
import org.partiql.lang.eval.OrdinalBindings;
import org.partiql.lang.eval.StructExprValue$WhenMappings;
import org.partiql.lang.eval.StructOrdering;
import org.partiql.lang.util.IonValueExtensionsKt;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010(\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0010\u0018\u00002\u00020\u0001:\u0001*B#\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007\u00a2\u0006\u0002\u0010\tJ\r\u0010!\u001a\u00020\"H\u0000\u00a2\u0006\u0002\b#J\u000f\u0010$\u001a\b\u0012\u0004\u0012\u00020\b0%H\u0096\u0002J%\u0010&\u001a\u0004\u0018\u0001H'\"\u0004\b\u0000\u0010'2\u000e\u0010\u001d\u001a\n\u0012\u0004\u0012\u0002H'\u0018\u00010(H\u0016\u00a2\u0006\u0002\u0010)R\u001a\u0010\n\u001a\b\u0012\u0004\u0012\u00020\b0\u000b8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\f\u0010\rR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001b\u0010\u000e\u001a\u00020\u000f8VX\u0096\u0084\u0002\u00a2\u0006\f\n\u0004\b\u0012\u0010\u0013\u001a\u0004\b\u0010\u0010\u0011R\u001b\u0010\u0014\u001a\u00020\u00158BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\u0018\u0010\u0013\u001a\u0004\b\u0016\u0010\u0017R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0019\u001a\u00020\u001a8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u001b\u0010\u001cR\u0014\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u001d\u001a\u00020\u001eX\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010 \u00a8\u0006+"}, d2={"Lorg/partiql/lang/eval/StructExprValue;", "Lorg/partiql/lang/eval/BaseExprValue;", "ion", "Lcom/amazon/ion/IonSystem;", "ordering", "Lorg/partiql/lang/eval/StructOrdering;", "sequence", "Lkotlin/sequences/Sequence;", "Lorg/partiql/lang/eval/ExprValue;", "(Lcom/amazon/ion/IonSystem;Lorg/partiql/lang/eval/StructOrdering;Lkotlin/sequences/Sequence;)V", "bindings", "Lorg/partiql/lang/eval/Bindings;", "getBindings", "()Lorg/partiql/lang/eval/Bindings;", "ionValue", "Lcom/amazon/ion/IonValue;", "getIonValue", "()Lcom/amazon/ion/IonValue;", "ionValue$delegate", "Lkotlin/Lazy;", "materialized", "Lorg/partiql/lang/eval/StructExprValue$Materialized;", "getMaterialized", "()Lorg/partiql/lang/eval/StructExprValue$Materialized;", "materialized$delegate", "ordinalBindings", "Lorg/partiql/lang/eval/OrdinalBindings;", "getOrdinalBindings", "()Lorg/partiql/lang/eval/OrdinalBindings;", "type", "Lorg/partiql/lang/eval/ExprValueType;", "getType", "()Lorg/partiql/lang/eval/ExprValueType;", "createMutableValue", "Lcom/amazon/ion/IonStruct;", "createMutableValue$lang", "iterator", "", "provideFacet", "T", "Ljava/lang/Class;", "(Ljava/lang/Class;)Ljava/lang/Object;", "Materialized", "lang"})
public class StructExprValue
extends BaseExprValue {
    static final /* synthetic */ KProperty[] $$delegatedProperties;
    @NotNull
    private final ExprValueType type;
    @NotNull
    private final Lazy ionValue$delegate;
    private final Lazy materialized$delegate;
    private final IonSystem ion;
    private final StructOrdering ordering;
    private final Sequence<ExprValue> sequence;

    static {
        $$delegatedProperties = new KProperty[]{Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(StructExprValue.class), "ionValue", "getIonValue()Lcom/amazon/ion/IonValue;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(StructExprValue.class), "materialized", "getMaterialized()Lorg/partiql/lang/eval/StructExprValue$Materialized;"))};
    }

    @Override
    @NotNull
    public ExprValueType getType() {
        return this.type;
    }

    @Override
    @NotNull
    public IonValue getIonValue() {
        Lazy lazy = this.ionValue$delegate;
        StructExprValue structExprValue = this;
        KProperty kProperty = $$delegatedProperties[0];
        boolean bl = false;
        return (IonValue)lazy.getValue();
    }

    @NotNull
    public final IonStruct createMutableValue$lang() {
        IonStruct ionStruct = this.ion.newEmptyStruct();
        boolean bl = false;
        boolean bl2 = false;
        IonStruct $this$apply = ionStruct;
        boolean bl3 = false;
        Sequence<ExprValue> $this$forEach$iv = this.sequence;
        boolean $i$f$forEach = false;
        Iterator<ExprValue> iterator2 = $this$forEach$iv.iterator();
        while (iterator2.hasNext()) {
            ExprValue element$iv;
            ExprValue it = element$iv = iterator2.next();
            boolean bl4 = false;
            ExprValue nameVal = ExprValueExtensionsKt.getName(it);
            if (nameVal == null || !nameVal.getType().isText() || it.getType() == ExprValueType.MISSING) continue;
            String name = ExprValueExtensionsKt.stringValue(nameVal);
            $this$apply.add(name, it.getIonValue().clone());
        }
        IonStruct ionStruct2 = ionStruct;
        Intrinsics.checkExpressionValueIsNotNull(ionStruct2, "ion.newEmptyStruct().app\u2026}\n            }\n        }");
        return ionStruct2;
    }

    private final Materialized getMaterialized() {
        Lazy lazy = this.materialized$delegate;
        StructExprValue structExprValue = this;
        KProperty kProperty = $$delegatedProperties[1];
        boolean bl = false;
        return (Materialized)lazy.getValue();
    }

    @Override
    @NotNull
    public Bindings<ExprValue> getBindings() {
        return this.getMaterialized().getBindings();
    }

    @Override
    @NotNull
    public OrdinalBindings getOrdinalBindings() {
        return this.getMaterialized().getOrdinalBindings();
    }

    @Override
    @Nullable
    public <T> T provideFacet(@Nullable Class<T> type) {
        Object object;
        Class<T> clazz = type;
        if (Intrinsics.areEqual(clazz, OrderedBindNames.class)) {
            OrderedBindNames orderedBindNames2;
            switch (StructExprValue$WhenMappings.$EnumSwitchMapping$1[this.ordering.ordinal()]) {
                case 1: {
                    orderedBindNames2 = this.getMaterialized().getOrderedBindNames();
                    break;
                }
                default: {
                    orderedBindNames2 = null;
                }
            }
            object = orderedBindNames2;
        } else {
            object = null;
        }
        return (T)object;
    }

    @Override
    @NotNull
    public Iterator<ExprValue> iterator() {
        return this.sequence.iterator();
    }

    public StructExprValue(@NotNull IonSystem ion, @NotNull StructOrdering ordering, @NotNull Sequence<? extends ExprValue> sequence) {
        Intrinsics.checkParameterIsNotNull(ion, "ion");
        Intrinsics.checkParameterIsNotNull((Object)ordering, "ordering");
        Intrinsics.checkParameterIsNotNull(sequence, "sequence");
        this.ion = ion;
        this.ordering = ordering;
        this.sequence = sequence;
        this.type = ExprValueType.STRUCT;
        this.ionValue$delegate = LazyKt.lazy((Function0)new Function0<IonValue>(this){
            final /* synthetic */ StructExprValue this$0;

            @NotNull
            public final IonValue invoke() {
                return IonValueExtensionsKt.seal(this.this$0.createMutableValue$lang());
            }
            {
                this.this$0 = structExprValue;
                super(0);
            }
        });
        this.materialized$delegate = LazyKt.lazy((Function0)new Function0<Materialized>(this){
            final /* synthetic */ StructExprValue this$0;

            @NotNull
            public final Materialized invoke() {
                OrderedBindNames orderedBindNames2;
                HashMap<Object, ExprValue> bindMap = new HashMap<Object, ExprValue>();
                ArrayList<ExprValue> bindList = new ArrayList<ExprValue>();
                ArrayList<Object> bindNames = new ArrayList<Object>();
                Sequence $this$forEach$iv = StructExprValue.access$getSequence$p(this.this$0);
                boolean $i$f$forEach = false;
                Iterator<T> iterator2 = $this$forEach$iv.iterator();
                while (iterator2.hasNext()) {
                    T element$iv = iterator2.next();
                    ExprValue it = (ExprValue)element$iv;
                    boolean bl = false;
                    Object object = ExprValueExtensionsKt.getName(it);
                    if (object == null || (object = ExprValueExtensionsKt.stringValue((ExprValue)object)) == null) {
                        Void void_ = ExceptionsKt.errNoContext("Expected non-null name for lazy struct", false);
                        throw null;
                    }
                    Object name = object;
                    bindMap.putIfAbsent(name, it);
                    if (StructExprValue.access$getOrdering$p(this.this$0) != StructOrdering.ORDERED) continue;
                    bindList.add(it);
                    bindNames.add(name);
                }
                Bindings<ExprValue> bindings2 = Bindings.Companion.ofMap((Map)bindMap);
                OrdinalBindings ordinalBindings2 = OrdinalBindings.Companion.ofList((List<? extends ExprValue>)bindList);
                switch (StructExprValue$WhenMappings.$EnumSwitchMapping$0[StructExprValue.access$getOrdering$p(this.this$0).ordinal()]) {
                    case 1: {
                        orderedBindNames2 = new OrderedBindNames(bindNames){
                            @NotNull
                            private final ArrayList<String> orderedNames;
                            final /* synthetic */ ArrayList $bindNames;

                            @NotNull
                            public ArrayList<String> getOrderedNames() {
                                return this.orderedNames;
                            }
                            {
                                this.$bindNames = $captured_local_variable$0;
                                this.orderedNames = $captured_local_variable$0;
                            }
                        };
                        break;
                    }
                    case 2: {
                        orderedBindNames2 = null;
                        break;
                    }
                    default: {
                        throw new NoWhenBranchMatchedException();
                    }
                }
                OrderedBindNames orderedBindNames3 = orderedBindNames2;
                return new Materialized(bindings2, ordinalBindings2, orderedBindNames3);
            }
            {
                this.this$0 = structExprValue;
                super(0);
            }
        });
    }

    public static final /* synthetic */ Sequence access$getSequence$p(StructExprValue $this) {
        return $this.sequence;
    }

    public static final /* synthetic */ StructOrdering access$getOrdering$p(StructExprValue $this) {
        return $this.ordering;
    }

    @Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0082\b\u0018\u00002\u00020\u0001B%\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\b\u00a2\u0006\u0002\u0010\tJ\u000f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003H\u00c6\u0003J\t\u0010\u0011\u001a\u00020\u0006H\u00c6\u0003J\u000b\u0010\u0012\u001a\u0004\u0018\u00010\bH\u00c6\u0003J/\u0010\u0013\u001a\u00020\u00002\u000e\b\u0002\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00062\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\bH\u00c6\u0001J\u0013\u0010\u0014\u001a\u00020\u00152\b\u0010\u0016\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u0017\u001a\u00020\u0018H\u00d6\u0001J\t\u0010\u0019\u001a\u00020\u001aH\u00d6\u0001R\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0013\u0010\u0007\u001a\u0004\u0018\u00010\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0011\u0010\u0005\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000f\u00a8\u0006\u001b"}, d2={"Lorg/partiql/lang/eval/StructExprValue$Materialized;", "", "bindings", "Lorg/partiql/lang/eval/Bindings;", "Lorg/partiql/lang/eval/ExprValue;", "ordinalBindings", "Lorg/partiql/lang/eval/OrdinalBindings;", "orderedBindNames", "Lorg/partiql/lang/eval/OrderedBindNames;", "(Lorg/partiql/lang/eval/Bindings;Lorg/partiql/lang/eval/OrdinalBindings;Lorg/partiql/lang/eval/OrderedBindNames;)V", "getBindings", "()Lorg/partiql/lang/eval/Bindings;", "getOrderedBindNames", "()Lorg/partiql/lang/eval/OrderedBindNames;", "getOrdinalBindings", "()Lorg/partiql/lang/eval/OrdinalBindings;", "component1", "component2", "component3", "copy", "equals", "", "other", "hashCode", "", "toString", "", "lang"})
    private static final class Materialized {
        @NotNull
        private final Bindings<ExprValue> bindings;
        @NotNull
        private final OrdinalBindings ordinalBindings;
        @Nullable
        private final OrderedBindNames orderedBindNames;

        @NotNull
        public final Bindings<ExprValue> getBindings() {
            return this.bindings;
        }

        @NotNull
        public final OrdinalBindings getOrdinalBindings() {
            return this.ordinalBindings;
        }

        @Nullable
        public final OrderedBindNames getOrderedBindNames() {
            return this.orderedBindNames;
        }

        public Materialized(@NotNull Bindings<ExprValue> bindings2, @NotNull OrdinalBindings ordinalBindings2, @Nullable OrderedBindNames orderedBindNames2) {
            Intrinsics.checkParameterIsNotNull(bindings2, "bindings");
            Intrinsics.checkParameterIsNotNull(ordinalBindings2, "ordinalBindings");
            this.bindings = bindings2;
            this.ordinalBindings = ordinalBindings2;
            this.orderedBindNames = orderedBindNames2;
        }

        @NotNull
        public final Bindings<ExprValue> component1() {
            return this.bindings;
        }

        @NotNull
        public final OrdinalBindings component2() {
            return this.ordinalBindings;
        }

        @Nullable
        public final OrderedBindNames component3() {
            return this.orderedBindNames;
        }

        @NotNull
        public final Materialized copy(@NotNull Bindings<ExprValue> bindings2, @NotNull OrdinalBindings ordinalBindings2, @Nullable OrderedBindNames orderedBindNames2) {
            Intrinsics.checkParameterIsNotNull(bindings2, "bindings");
            Intrinsics.checkParameterIsNotNull(ordinalBindings2, "ordinalBindings");
            return new Materialized(bindings2, ordinalBindings2, orderedBindNames2);
        }

        public static /* synthetic */ Materialized copy$default(Materialized materialized2, Bindings bindings2, OrdinalBindings ordinalBindings2, OrderedBindNames orderedBindNames2, int n, Object object) {
            if ((n & 1) != 0) {
                bindings2 = materialized2.bindings;
            }
            if ((n & 2) != 0) {
                ordinalBindings2 = materialized2.ordinalBindings;
            }
            if ((n & 4) != 0) {
                orderedBindNames2 = materialized2.orderedBindNames;
            }
            return materialized2.copy(bindings2, ordinalBindings2, orderedBindNames2);
        }

        @NotNull
        public String toString() {
            return "Materialized(bindings=" + this.bindings + ", ordinalBindings=" + this.ordinalBindings + ", orderedBindNames=" + this.orderedBindNames + ")";
        }

        public int hashCode() {
            Bindings<ExprValue> bindings2 = this.bindings;
            OrdinalBindings ordinalBindings2 = this.ordinalBindings;
            OrderedBindNames orderedBindNames2 = this.orderedBindNames;
            return ((bindings2 != null ? bindings2.hashCode() : 0) * 31 + (ordinalBindings2 != null ? ordinalBindings2.hashCode() : 0)) * 31 + (orderedBindNames2 != null ? orderedBindNames2.hashCode() : 0);
        }

        public boolean equals(@Nullable Object object) {
            block3: {
                block2: {
                    if (this == object) break block2;
                    if (!(object instanceof Materialized)) break block3;
                    Materialized materialized2 = (Materialized)object;
                    if (!Intrinsics.areEqual(this.bindings, materialized2.bindings) || !Intrinsics.areEqual(this.ordinalBindings, materialized2.ordinalBindings) || !Intrinsics.areEqual(this.orderedBindNames, materialized2.orderedBindNames)) break block3;
                }
                return true;
            }
            return false;
        }
    }
}

