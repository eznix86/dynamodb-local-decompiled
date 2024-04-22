/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package org.partiql.lang.eval;

import com.amazon.ion.IonSequence;
import com.amazon.ion.IonValue;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.JvmField;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.partiql.lang.eval.ExprValue;
import org.partiql.lang.eval.ExprValueFactory;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\bf\u0018\u0000 \u00062\u00020\u0001:\u0001\u0006J\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u00a6\u0002\u0082\u0002\u0007\n\u0005\b\u0091F0\u0001\u00a8\u0006\u0007"}, d2={"Lorg/partiql/lang/eval/OrdinalBindings;", "", "get", "Lorg/partiql/lang/eval/ExprValue;", "index", "", "Companion", "lang"})
public interface OrdinalBindings {
    @JvmField
    @NotNull
    public static final OrdinalBindings EMPTY;
    public static final Companion Companion;

    @Nullable
    public ExprValue get(int var1);

    static {
        Companion = new Companion(null);
        EMPTY = new OrdinalBindings(){

            @Nullable
            public ExprValue get(int index) {
                return null;
            }
        };
    }

    @JvmStatic
    @NotNull
    public static OrdinalBindings ofList(@NotNull List<? extends ExprValue> list) {
        return Companion.ofList(list);
    }

    @JvmStatic
    @NotNull
    public static OrdinalBindings ofIonSequence(@NotNull IonSequence seq2, @NotNull ExprValueFactory valueFactory) {
        return Companion.ofIonSequence(seq2, valueFactory);
    }

    @Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0018\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tH\u0007J\u0016\u0010\n\u001a\u00020\u00042\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\fH\u0007R\u0016\u0010\u0003\u001a\u00020\u00048\u0006X\u0087\u0004\u00f8\u0001\u0000\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0001\u0082\u0002\u0007\n\u0005\b\u0091F0\u0001\u00a8\u0006\u000e"}, d2={"Lorg/partiql/lang/eval/OrdinalBindings$Companion;", "", "()V", "EMPTY", "Lorg/partiql/lang/eval/OrdinalBindings;", "ofIonSequence", "seq", "Lcom/amazon/ion/IonSequence;", "valueFactory", "Lorg/partiql/lang/eval/ExprValueFactory;", "ofList", "list", "", "Lorg/partiql/lang/eval/ExprValue;", "lang"})
    public static final class Companion {
        static final /* synthetic */ Companion $$INSTANCE;

        @JvmStatic
        @NotNull
        public final OrdinalBindings ofList(@NotNull List<? extends ExprValue> list) {
            Intrinsics.checkParameterIsNotNull(list, "list");
            return new OrdinalBindings(list){
                final /* synthetic */ List $list;

                @Nullable
                public ExprValue get(int index) {
                    return (ExprValue)CollectionsKt.getOrNull(this.$list, index);
                }
                {
                    this.$list = $captured_local_variable$0;
                }
            };
        }

        @JvmStatic
        @NotNull
        public final OrdinalBindings ofIonSequence(@NotNull IonSequence seq2, @NotNull ExprValueFactory valueFactory) {
            Intrinsics.checkParameterIsNotNull(seq2, "seq");
            Intrinsics.checkParameterIsNotNull(valueFactory, "valueFactory");
            return new OrdinalBindings(seq2, valueFactory){
                final /* synthetic */ IonSequence $seq;
                final /* synthetic */ ExprValueFactory $valueFactory;

                @Nullable
                public ExprValue get(int index) {
                    ExprValue exprValue2;
                    if (index < 0 || index >= this.$seq.size()) {
                        exprValue2 = null;
                    } else {
                        IonValue ordinalValue;
                        IonValue ionValue2 = ordinalValue = this.$seq.get(index);
                        Intrinsics.checkExpressionValueIsNotNull(ionValue2, "ordinalValue");
                        exprValue2 = this.$valueFactory.newFromIonValue(ionValue2);
                    }
                    return exprValue2;
                }
                {
                    this.$seq = $captured_local_variable$0;
                    this.$valueFactory = $captured_local_variable$1;
                }
            };
        }

        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

