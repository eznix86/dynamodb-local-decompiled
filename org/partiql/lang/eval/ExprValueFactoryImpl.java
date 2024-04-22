/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package org.partiql.lang.eval;

import com.amazon.ion.IonBool;
import com.amazon.ion.IonNull;
import com.amazon.ion.IonReader;
import com.amazon.ion.IonSystem;
import com.amazon.ion.IonValue;
import com.amazon.ion.Timestamp;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;
import org.jetbrains.annotations.NotNull;
import org.partiql.lang.eval.BlobExprValue;
import org.partiql.lang.eval.ClobExprValue;
import org.partiql.lang.eval.DateExprValue;
import org.partiql.lang.eval.DecimalExprValue;
import org.partiql.lang.eval.ExprValue;
import org.partiql.lang.eval.ExprValueExtensionsKt;
import org.partiql.lang.eval.ExprValueFactory;
import org.partiql.lang.eval.ExprValueType;
import org.partiql.lang.eval.FalseBoolExprValue;
import org.partiql.lang.eval.FloatExprValue;
import org.partiql.lang.eval.IntExprValue;
import org.partiql.lang.eval.IonExprValue;
import org.partiql.lang.eval.MissingExprValue;
import org.partiql.lang.eval.NullExprValue;
import org.partiql.lang.eval.SequenceExprValue;
import org.partiql.lang.eval.StringExprValue;
import org.partiql.lang.eval.StructExprValue;
import org.partiql.lang.eval.StructOrdering;
import org.partiql.lang.eval.SymbolExprValue;
import org.partiql.lang.eval.TimeExprValue;
import org.partiql.lang.eval.TimestampExprValue;
import org.partiql.lang.eval.TrueBoolExprValue;
import org.partiql.lang.eval.time.Time;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000\u00aa\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u001c\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0012\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0002\u0018\u0000 G2\u00020\u0001:\u0001GB\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0016\u0010\u001f\u001a\u00020\u00062\f\u0010 \u001a\b\u0012\u0004\u0012\u00020\u00060!H\u0016J\u0016\u0010\u001f\u001a\u00020\u00062\f\u0010 \u001a\b\u0012\u0004\u0012\u00020\u00060\"H\u0016J\u0010\u0010#\u001a\u00020\u00062\u0006\u0010 \u001a\u00020$H\u0016J\u0010\u0010%\u001a\u00020\u00062\u0006\u0010 \u001a\u00020&H\u0016J\u0010\u0010'\u001a\u00020\u00062\u0006\u0010 \u001a\u00020$H\u0016J\u0010\u0010(\u001a\u00020\u00062\u0006\u0010 \u001a\u00020)H\u0016J \u0010(\u001a\u00020\u00062\u0006\u0010*\u001a\u00020+2\u0006\u0010,\u001a\u00020+2\u0006\u0010-\u001a\u00020+H\u0016J\u0010\u0010(\u001a\u00020\u00062\u0006\u0010.\u001a\u00020/H\u0016J\u0010\u00100\u001a\u00020\u00062\u0006\u0010 \u001a\u000201H\u0016J\u0010\u00100\u001a\u00020\u00062\u0006\u0010 \u001a\u00020+H\u0016J\u0010\u00100\u001a\u00020\u00062\u0006\u0010 \u001a\u000202H\u0016J\u0010\u00103\u001a\u00020\u00062\u0006\u0010 \u001a\u000204H\u0016J\u0010\u00105\u001a\u00020\u00062\u0006\u00106\u001a\u000207H\u0016J\u0010\u00108\u001a\u00020\u00062\u0006\u0010 \u001a\u000209H\u0016J\u0010\u0010:\u001a\u00020\u00062\u0006\u0010 \u001a\u00020+H\u0016J\u0010\u0010:\u001a\u00020;2\u0006\u0010 \u001a\u000202H\u0016J\u0016\u0010<\u001a\u00020\u00062\f\u0010 \u001a\b\u0012\u0004\u0012\u00020\u00060!H\u0016J\u0016\u0010<\u001a\u00020\u00062\f\u0010 \u001a\b\u0012\u0004\u0012\u00020\u00060\"H\u0016J\u0016\u0010=\u001a\u00020\u00062\f\u0010 \u001a\b\u0012\u0004\u0012\u00020\u00060!H\u0016J\u0016\u0010=\u001a\u00020\u00062\f\u0010 \u001a\b\u0012\u0004\u0012\u00020\u00060\"H\u0016J\u0010\u0010>\u001a\u00020\u00062\u0006\u0010 \u001a\u00020/H\u0016J\u001e\u0010?\u001a\u00020\u00062\f\u0010 \u001a\b\u0012\u0004\u0012\u00020\u00060!2\u0006\u0010@\u001a\u00020AH\u0016J\u001e\u0010?\u001a\u00020\u00062\f\u0010 \u001a\b\u0012\u0004\u0012\u00020\u00060\"2\u0006\u0010@\u001a\u00020AH\u0016J\u0010\u0010B\u001a\u00020\u00062\u0006\u0010 \u001a\u00020/H\u0016J\u0010\u0010C\u001a\u00020\u00062\u0006\u0010 \u001a\u00020DH\u0016J\u0010\u0010E\u001a\u00020\u00062\u0006\u0010 \u001a\u00020FH\u0016R\u0014\u0010\u0005\u001a\u00020\u0006X\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0014\u0010\t\u001a\u00020\u0006X\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\bR\u0014\u0010\u000b\u001a\u00020\u0006X\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\bR\u000e\u0010\r\u001a\u00020\u000eX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u000f\u001a\u00020\u0006X\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\bR\u000e\u0010\u0011\u001a\u00020\u0012X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u0014\u0010\u0015\u001a\u00020\u0016X\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018R\u0014\u0010\u0019\u001a\u00020\u001aX\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u001cR\u000e\u0010\u001d\u001a\u00020\u001eX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006H"}, d2={"Lorg/partiql/lang/eval/ExprValueFactoryImpl;", "Lorg/partiql/lang/eval/ExprValueFactory;", "ion", "Lcom/amazon/ion/IonSystem;", "(Lcom/amazon/ion/IonSystem;)V", "emptyBag", "Lorg/partiql/lang/eval/ExprValue;", "getEmptyBag", "()Lorg/partiql/lang/eval/ExprValue;", "emptyList", "getEmptyList", "emptySexp", "getEmptySexp", "emptyString", "Lorg/partiql/lang/eval/StringExprValue;", "emptyStruct", "getEmptyStruct", "falseValue", "Lorg/partiql/lang/eval/FalseBoolExprValue;", "getIon", "()Lcom/amazon/ion/IonSystem;", "missingValue", "Lorg/partiql/lang/eval/MissingExprValue;", "getMissingValue", "()Lorg/partiql/lang/eval/MissingExprValue;", "nullValue", "Lorg/partiql/lang/eval/NullExprValue;", "getNullValue", "()Lorg/partiql/lang/eval/NullExprValue;", "trueValue", "Lorg/partiql/lang/eval/TrueBoolExprValue;", "newBag", "value", "", "Lkotlin/sequences/Sequence;", "newBlob", "", "newBoolean", "", "newClob", "newDate", "Ljava/time/LocalDate;", "year", "", "month", "day", "dateString", "", "newDecimal", "Ljava/math/BigDecimal;", "", "newFloat", "", "newFromIonReader", "reader", "Lcom/amazon/ion/IonReader;", "newFromIonValue", "Lcom/amazon/ion/IonValue;", "newInt", "Lorg/partiql/lang/eval/IntExprValue;", "newList", "newSexp", "newString", "newStruct", "ordering", "Lorg/partiql/lang/eval/StructOrdering;", "newSymbol", "newTime", "Lorg/partiql/lang/eval/time/Time;", "newTimestamp", "Lcom/amazon/ion/Timestamp;", "Companion", "lang"})
final class ExprValueFactoryImpl
implements ExprValueFactory {
    @NotNull
    private final MissingExprValue missingValue;
    @NotNull
    private final NullExprValue nullValue;
    private final TrueBoolExprValue trueValue;
    private final FalseBoolExprValue falseValue;
    private final StringExprValue emptyString;
    @NotNull
    private final ExprValue emptyStruct;
    @NotNull
    private final ExprValue emptyList;
    @NotNull
    private final ExprValue emptySexp;
    @NotNull
    private final ExprValue emptyBag;
    @NotNull
    private final IonSystem ion;
    private static final BigInteger MAX_LONG_VALUE;
    private static final BigInteger MIN_LONG_VALUE;
    public static final Companion Companion;

    @Override
    @NotNull
    public MissingExprValue getMissingValue() {
        return this.missingValue;
    }

    @Override
    @NotNull
    public NullExprValue getNullValue() {
        return this.nullValue;
    }

    @Override
    @NotNull
    public ExprValue getEmptyStruct() {
        return this.emptyStruct;
    }

    @Override
    @NotNull
    public ExprValue getEmptyList() {
        return this.emptyList;
    }

    @Override
    @NotNull
    public ExprValue getEmptySexp() {
        return this.emptySexp;
    }

    @Override
    @NotNull
    public ExprValue getEmptyBag() {
        return this.emptyBag;
    }

    @Override
    @NotNull
    public ExprValue newBoolean(boolean value) {
        return value ? (ExprValue)this.trueValue : (ExprValue)this.falseValue;
    }

    @Override
    @NotNull
    public ExprValue newString(@NotNull String value) {
        Intrinsics.checkParameterIsNotNull(value, "value");
        CharSequence charSequence = value;
        boolean bl = false;
        return charSequence.length() == 0 ? (ExprValue)this.emptyString : (ExprValue)new StringExprValue(this.getIon(), value);
    }

    @Override
    @NotNull
    public ExprValue newInt(int value) {
        return new IntExprValue(this.getIon(), value);
    }

    @Override
    @NotNull
    public IntExprValue newInt(long value) {
        return new IntExprValue(this.getIon(), value);
    }

    @Override
    @NotNull
    public ExprValue newFloat(double value) {
        return new FloatExprValue(this.getIon(), value);
    }

    @Override
    @NotNull
    public ExprValue newDecimal(@NotNull BigDecimal value) {
        Intrinsics.checkParameterIsNotNull(value, "value");
        return new DecimalExprValue(this.getIon(), value);
    }

    @Override
    @NotNull
    public ExprValue newDecimal(int value) {
        IonSystem ionSystem = this.getIon();
        BigDecimal bigDecimal = BigDecimal.valueOf(value);
        Intrinsics.checkExpressionValueIsNotNull(bigDecimal, "BigDecimal.valueOf(value.toLong())");
        return new DecimalExprValue(ionSystem, bigDecimal);
    }

    @Override
    @NotNull
    public ExprValue newDecimal(long value) {
        IonSystem ionSystem = this.getIon();
        BigDecimal bigDecimal = BigDecimal.valueOf(value);
        Intrinsics.checkExpressionValueIsNotNull(bigDecimal, "BigDecimal.valueOf(value)");
        return new DecimalExprValue(ionSystem, bigDecimal);
    }

    @Override
    @NotNull
    public ExprValue newDate(@NotNull LocalDate value) {
        Intrinsics.checkParameterIsNotNull(value, "value");
        return new DateExprValue(this.getIon(), value);
    }

    @Override
    @NotNull
    public ExprValue newDate(int year2, int month, int day) {
        LocalDate localDate = LocalDate.of(year2, month, day);
        Intrinsics.checkExpressionValueIsNotNull(localDate, "LocalDate.of(year, month, day)");
        return this.newDate(localDate);
    }

    @Override
    @NotNull
    public ExprValue newDate(@NotNull String dateString) {
        Intrinsics.checkParameterIsNotNull(dateString, "dateString");
        LocalDate localDate = LocalDate.parse(dateString);
        Intrinsics.checkExpressionValueIsNotNull(localDate, "LocalDate.parse(dateString)");
        return this.newDate(localDate);
    }

    @Override
    @NotNull
    public ExprValue newTimestamp(@NotNull Timestamp value) {
        Intrinsics.checkParameterIsNotNull(value, "value");
        return new TimestampExprValue(this.getIon(), value);
    }

    @Override
    @NotNull
    public ExprValue newTime(@NotNull Time value) {
        Intrinsics.checkParameterIsNotNull(value, "value");
        return new TimeExprValue(this.getIon(), value);
    }

    @Override
    @NotNull
    public ExprValue newSymbol(@NotNull String value) {
        Intrinsics.checkParameterIsNotNull(value, "value");
        return new SymbolExprValue(this.getIon(), value);
    }

    @Override
    @NotNull
    public ExprValue newClob(@NotNull byte[] value) {
        Intrinsics.checkParameterIsNotNull(value, "value");
        return new ClobExprValue(this.getIon(), value);
    }

    @Override
    @NotNull
    public ExprValue newBlob(@NotNull byte[] value) {
        Intrinsics.checkParameterIsNotNull(value, "value");
        return new BlobExprValue(this.getIon(), value);
    }

    @Override
    @NotNull
    public ExprValue newFromIonValue(@NotNull IonValue value) {
        Intrinsics.checkParameterIsNotNull(value, "value");
        return new IonExprValue(this, value);
    }

    @Override
    @NotNull
    public ExprValue newFromIonReader(@NotNull IonReader reader) {
        Intrinsics.checkParameterIsNotNull(reader, "reader");
        IonValue ionValue2 = this.getIon().newValue(reader);
        Intrinsics.checkExpressionValueIsNotNull(ionValue2, "ion.newValue(reader)");
        return this.newFromIonValue(ionValue2);
    }

    @Override
    @NotNull
    public ExprValue newStruct(@NotNull Sequence<? extends ExprValue> value, @NotNull StructOrdering ordering) {
        Intrinsics.checkParameterIsNotNull(value, "value");
        Intrinsics.checkParameterIsNotNull((Object)ordering, "ordering");
        return new StructExprValue(this.getIon(), ordering, value);
    }

    @Override
    @NotNull
    public ExprValue newStruct(@NotNull Iterable<? extends ExprValue> value, @NotNull StructOrdering ordering) {
        Intrinsics.checkParameterIsNotNull(value, "value");
        Intrinsics.checkParameterIsNotNull((Object)ordering, "ordering");
        return this.newStruct(CollectionsKt.asSequence(value), ordering);
    }

    @Override
    @NotNull
    public ExprValue newBag(@NotNull Sequence<? extends ExprValue> value) {
        Intrinsics.checkParameterIsNotNull(value, "value");
        return new SequenceExprValue(this.getIon(), ExprValueType.BAG, value);
    }

    @Override
    @NotNull
    public ExprValue newBag(@NotNull Iterable<? extends ExprValue> value) {
        Intrinsics.checkParameterIsNotNull(value, "value");
        return this.newBag(CollectionsKt.asSequence(value));
    }

    @Override
    @NotNull
    public ExprValue newList(@NotNull Sequence<? extends ExprValue> value) {
        Intrinsics.checkParameterIsNotNull(value, "value");
        return new SequenceExprValue(this.getIon(), ExprValueType.LIST, SequencesKt.mapIndexed(value, (Function2)new Function2<Integer, ExprValue, ExprValue>(this){
            final /* synthetic */ ExprValueFactoryImpl this$0;

            @NotNull
            public final ExprValue invoke(int i, @NotNull ExprValue v) {
                Intrinsics.checkParameterIsNotNull(v, "v");
                return ExprValueExtensionsKt.namedValue(v, this.this$0.newInt(i));
            }
            {
                this.this$0 = exprValueFactoryImpl;
                super(2);
            }
        }));
    }

    @Override
    @NotNull
    public ExprValue newList(@NotNull Iterable<? extends ExprValue> value) {
        Intrinsics.checkParameterIsNotNull(value, "value");
        return this.newList(CollectionsKt.asSequence(value));
    }

    @Override
    @NotNull
    public ExprValue newSexp(@NotNull Sequence<? extends ExprValue> value) {
        Intrinsics.checkParameterIsNotNull(value, "value");
        return new SequenceExprValue(this.getIon(), ExprValueType.SEXP, SequencesKt.mapIndexed(value, (Function2)new Function2<Integer, ExprValue, ExprValue>(this){
            final /* synthetic */ ExprValueFactoryImpl this$0;

            @NotNull
            public final ExprValue invoke(int i, @NotNull ExprValue v) {
                Intrinsics.checkParameterIsNotNull(v, "v");
                return ExprValueExtensionsKt.namedValue(v, this.this$0.newInt(i));
            }
            {
                this.this$0 = exprValueFactoryImpl;
                super(2);
            }
        }));
    }

    @Override
    @NotNull
    public ExprValue newSexp(@NotNull Iterable<? extends ExprValue> value) {
        Intrinsics.checkParameterIsNotNull(value, "value");
        return this.newSexp(CollectionsKt.asSequence(value));
    }

    @Override
    @NotNull
    public IonSystem getIon() {
        return this.ion;
    }

    public ExprValueFactoryImpl(@NotNull IonSystem ion) {
        Intrinsics.checkParameterIsNotNull(ion, "ion");
        this.ion = ion;
        IonNull ionNull = this.getIon().newNull();
        Intrinsics.checkExpressionValueIsNotNull(ionNull, "ion.newNull()");
        this.missingValue = new MissingExprValue(ionNull);
        IonNull ionNull2 = this.getIon().newNull();
        Intrinsics.checkExpressionValueIsNotNull(ionNull2, "ion.newNull()");
        this.nullValue = new NullExprValue(ionNull2);
        IonBool ionBool = this.getIon().newBool(true);
        Intrinsics.checkExpressionValueIsNotNull(ionBool, "ion.newBool(true)");
        this.trueValue = new TrueBoolExprValue(ionBool);
        IonBool ionBool2 = this.getIon().newBool(false);
        Intrinsics.checkExpressionValueIsNotNull(ionBool2, "ion.newBool(false)");
        this.falseValue = new FalseBoolExprValue(ionBool2);
        this.emptyString = new StringExprValue(this.getIon(), "");
        this.emptyStruct = this.newStruct(SequencesKt.sequenceOf(new ExprValue[0]), StructOrdering.UNORDERED);
        this.emptyList = this.newList(SequencesKt.sequenceOf(new ExprValue[0]));
        this.emptySexp = this.newSexp(SequencesKt.sequenceOf(new ExprValue[0]));
        this.emptyBag = this.newBag(SequencesKt.sequenceOf(new ExprValue[0]));
    }

    static {
        Companion = new Companion(null);
        MAX_LONG_VALUE = BigInteger.valueOf(Long.MAX_VALUE);
        MIN_LONG_VALUE = BigInteger.valueOf(Long.MIN_VALUE);
    }

    @Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u0016\u0010\u0003\u001a\n \u0005*\u0004\u0018\u00010\u00040\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0016\u0010\u0006\u001a\n \u0005*\u0004\u0018\u00010\u00040\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0007"}, d2={"Lorg/partiql/lang/eval/ExprValueFactoryImpl$Companion;", "", "()V", "MAX_LONG_VALUE", "Ljava/math/BigInteger;", "kotlin.jvm.PlatformType", "MIN_LONG_VALUE", "lang"})
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

