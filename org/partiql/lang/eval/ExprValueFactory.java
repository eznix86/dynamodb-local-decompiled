/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package org.partiql.lang.eval;

import com.amazon.ion.IonReader;
import com.amazon.ion.IonSystem;
import com.amazon.ion.IonValue;
import com.amazon.ion.Timestamp;
import java.math.BigDecimal;
import java.time.LocalDate;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.Sequence;
import org.jetbrains.annotations.NotNull;
import org.partiql.lang.eval.ExprValue;
import org.partiql.lang.eval.ExprValueFactoryImpl;
import org.partiql.lang.eval.StructOrdering;
import org.partiql.lang.eval.time.Time;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000|\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u001c\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0012\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bf\u0018\u0000 ;2\u00020\u0001:\u0001;J\u0016\u0010\u0014\u001a\u00020\u00032\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00030\u0016H&J\u0016\u0010\u0014\u001a\u00020\u00032\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00030\u0017H&J\u0010\u0010\u0018\u001a\u00020\u00032\u0006\u0010\u0015\u001a\u00020\u0019H&J\u0010\u0010\u001a\u001a\u00020\u00032\u0006\u0010\u0015\u001a\u00020\u001bH&J\u0010\u0010\u001c\u001a\u00020\u00032\u0006\u0010\u0015\u001a\u00020\u0019H&J\u0010\u0010\u001d\u001a\u00020\u00032\u0006\u0010\u0015\u001a\u00020\u001eH&J \u0010\u001d\u001a\u00020\u00032\u0006\u0010\u001f\u001a\u00020 2\u0006\u0010!\u001a\u00020 2\u0006\u0010\"\u001a\u00020 H&J\u0010\u0010\u001d\u001a\u00020\u00032\u0006\u0010#\u001a\u00020$H&J\u0010\u0010%\u001a\u00020\u00032\u0006\u0010\u0015\u001a\u00020&H&J\u0010\u0010%\u001a\u00020\u00032\u0006\u0010\u0015\u001a\u00020 H&J\u0010\u0010%\u001a\u00020\u00032\u0006\u0010\u0015\u001a\u00020'H&J\u0010\u0010(\u001a\u00020\u00032\u0006\u0010\u0015\u001a\u00020)H&J\u0010\u0010*\u001a\u00020\u00032\u0006\u0010+\u001a\u00020,H&J\u0010\u0010-\u001a\u00020\u00032\u0006\u0010\u0015\u001a\u00020.H&J\u0010\u0010/\u001a\u00020\u00032\u0006\u0010\u0015\u001a\u00020 H&J\u0010\u0010/\u001a\u00020\u00032\u0006\u0010\u0015\u001a\u00020'H&J\u0016\u00100\u001a\u00020\u00032\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00030\u0016H&J\u0016\u00100\u001a\u00020\u00032\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00030\u0017H&J\u0016\u00101\u001a\u00020\u00032\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00030\u0016H&J\u0016\u00101\u001a\u00020\u00032\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00030\u0017H&J\u0010\u00102\u001a\u00020\u00032\u0006\u0010\u0015\u001a\u00020$H&J\u001e\u00103\u001a\u00020\u00032\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00030\u00162\u0006\u00104\u001a\u000205H&J\u001e\u00103\u001a\u00020\u00032\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00030\u00172\u0006\u00104\u001a\u000205H&J\u0010\u00106\u001a\u00020\u00032\u0006\u0010\u0015\u001a\u00020$H&J\u0010\u00107\u001a\u00020\u00032\u0006\u0010\u0015\u001a\u000208H&J\u0010\u00109\u001a\u00020\u00032\u0006\u0010\u0015\u001a\u00020:H&R\u0012\u0010\u0002\u001a\u00020\u0003X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005R\u0012\u0010\u0006\u001a\u00020\u0003X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0007\u0010\u0005R\u0012\u0010\b\u001a\u00020\u0003X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\t\u0010\u0005R\u0012\u0010\n\u001a\u00020\u0003X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u000b\u0010\u0005R\u0012\u0010\f\u001a\u00020\rX\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u000e\u0010\u000fR\u0012\u0010\u0010\u001a\u00020\u0003X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0011\u0010\u0005R\u0012\u0010\u0012\u001a\u00020\u0003X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0013\u0010\u0005\u00a8\u0006<"}, d2={"Lorg/partiql/lang/eval/ExprValueFactory;", "", "emptyBag", "Lorg/partiql/lang/eval/ExprValue;", "getEmptyBag", "()Lorg/partiql/lang/eval/ExprValue;", "emptyList", "getEmptyList", "emptySexp", "getEmptySexp", "emptyStruct", "getEmptyStruct", "ion", "Lcom/amazon/ion/IonSystem;", "getIon", "()Lcom/amazon/ion/IonSystem;", "missingValue", "getMissingValue", "nullValue", "getNullValue", "newBag", "value", "", "Lkotlin/sequences/Sequence;", "newBlob", "", "newBoolean", "", "newClob", "newDate", "Ljava/time/LocalDate;", "year", "", "month", "day", "dateString", "", "newDecimal", "Ljava/math/BigDecimal;", "", "newFloat", "", "newFromIonReader", "reader", "Lcom/amazon/ion/IonReader;", "newFromIonValue", "Lcom/amazon/ion/IonValue;", "newInt", "newList", "newSexp", "newString", "newStruct", "ordering", "Lorg/partiql/lang/eval/StructOrdering;", "newSymbol", "newTime", "Lorg/partiql/lang/eval/time/Time;", "newTimestamp", "Lcom/amazon/ion/Timestamp;", "Companion", "lang"})
public interface ExprValueFactory {
    public static final Companion Companion = org.partiql.lang.eval.ExprValueFactory$Companion.$$INSTANCE;

    @NotNull
    public IonSystem getIon();

    @NotNull
    public ExprValue getMissingValue();

    @NotNull
    public ExprValue getNullValue();

    @NotNull
    public ExprValue getEmptyStruct();

    @NotNull
    public ExprValue getEmptyList();

    @NotNull
    public ExprValue getEmptySexp();

    @NotNull
    public ExprValue getEmptyBag();

    @NotNull
    public ExprValue newBoolean(boolean var1);

    @NotNull
    public ExprValue newString(@NotNull String var1);

    @NotNull
    public ExprValue newInt(int var1);

    @NotNull
    public ExprValue newInt(long var1);

    @NotNull
    public ExprValue newFloat(double var1);

    @NotNull
    public ExprValue newDecimal(int var1);

    @NotNull
    public ExprValue newDecimal(long var1);

    @NotNull
    public ExprValue newDecimal(@NotNull BigDecimal var1);

    @NotNull
    public ExprValue newDate(@NotNull LocalDate var1);

    @NotNull
    public ExprValue newDate(int var1, int var2, int var3);

    @NotNull
    public ExprValue newDate(@NotNull String var1);

    @NotNull
    public ExprValue newTimestamp(@NotNull Timestamp var1);

    @NotNull
    public ExprValue newTime(@NotNull Time var1);

    @NotNull
    public ExprValue newSymbol(@NotNull String var1);

    @NotNull
    public ExprValue newClob(@NotNull byte[] var1);

    @NotNull
    public ExprValue newBlob(@NotNull byte[] var1);

    @NotNull
    public ExprValue newStruct(@NotNull Sequence<? extends ExprValue> var1, @NotNull StructOrdering var2);

    @NotNull
    public ExprValue newStruct(@NotNull Iterable<? extends ExprValue> var1, @NotNull StructOrdering var2);

    @NotNull
    public ExprValue newBag(@NotNull Sequence<? extends ExprValue> var1);

    @NotNull
    public ExprValue newBag(@NotNull Iterable<? extends ExprValue> var1);

    @NotNull
    public ExprValue newList(@NotNull Sequence<? extends ExprValue> var1);

    @NotNull
    public ExprValue newList(@NotNull Iterable<? extends ExprValue> var1);

    @NotNull
    public ExprValue newSexp(@NotNull Sequence<? extends ExprValue> var1);

    @NotNull
    public ExprValue newSexp(@NotNull Iterable<? extends ExprValue> var1);

    @NotNull
    public ExprValue newFromIonValue(@NotNull IonValue var1);

    @NotNull
    public ExprValue newFromIonReader(@NotNull IonReader var1);

    @JvmStatic
    @NotNull
    public static ExprValueFactory standard(@NotNull IonSystem ion) {
        return Companion.standard(ion);
    }

    @Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0007\u00a8\u0006\u0007"}, d2={"Lorg/partiql/lang/eval/ExprValueFactory$Companion;", "", "()V", "standard", "Lorg/partiql/lang/eval/ExprValueFactory;", "ion", "Lcom/amazon/ion/IonSystem;", "lang"})
    public static final class Companion {
        static final /* synthetic */ Companion $$INSTANCE;

        @JvmStatic
        @NotNull
        public final ExprValueFactory standard(@NotNull IonSystem ion) {
            Intrinsics.checkParameterIsNotNull(ion, "ion");
            return new ExprValueFactoryImpl(ion);
        }

        private Companion() {
        }

        static {
            Companion companion;
            $$INSTANCE = companion = new Companion();
        }
    }
}

