/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package org.partiql.lang.eval;

import com.amazon.ion.IonInt;
import com.amazon.ion.IonText;
import com.amazon.ion.IonValue;
import com.amazon.ion.Timestamp;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TypeCastException;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Regex;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.partiql.lang.ast.DataType;
import org.partiql.lang.ast.SourceLocationMeta;
import org.partiql.lang.ast.SqlDataType;
import org.partiql.lang.errors.ErrorCode;
import org.partiql.lang.errors.Property;
import org.partiql.lang.errors.PropertyValueMap;
import org.partiql.lang.eval.Addressed;
import org.partiql.lang.eval.Bindings;
import org.partiql.lang.eval.EvaluationException;
import org.partiql.lang.eval.ExceptionsKt;
import org.partiql.lang.eval.ExprValue;
import org.partiql.lang.eval.ExprValueExtensionsKt;
import org.partiql.lang.eval.ExprValueExtensionsKt$WhenMappings;
import org.partiql.lang.eval.ExprValueFactory;
import org.partiql.lang.eval.ExprValueType;
import org.partiql.lang.eval.Named;
import org.partiql.lang.eval.NaturalExprValueComparators;
import org.partiql.lang.eval.OrderedBindNames;
import org.partiql.lang.eval.OrdinalBindings;
import org.partiql.lang.eval.Scalar;
import org.partiql.lang.eval.time.Time;
import org.partiql.lang.syntax.DatePart;
import org.partiql.lang.syntax.LexerConstantsKt;
import org.partiql.lang.util.ConfigurableExprValueFormatter;
import org.partiql.lang.util.FacetExtensionsKt;
import org.partiql.lang.util.NumberExtensionsKt;
import org.partiql.lang.util.TimeExtensionsKt;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=2, d1={"\u0000\u009e\u0001\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0012\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\t\n\u0002\b\u0004\n\u0002\u0010\u0004\n\u0002\b\u0003\n\u0002\u0010\u001c\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u001a\u0014\u0010\u0015\u001a\u000e\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\u00170\u0016H\u0000\u001a\n\u0010\u0018\u001a\u00020\u0019*\u00020\u000b\u001a\f\u0010\u001a\u001a\u00020\u001b*\u00020\u000bH\u0000\u001a\n\u0010\u001c\u001a\u00020\u0017*\u00020\u000b\u001a\n\u0010\u001d\u001a\u00020\u001e*\u00020\u000b\u001a$\u0010\u001f\u001a\u00020\u000b*\u00020\u000b2\u0006\u0010 \u001a\u00020!2\u0006\u0010\"\u001a\u00020#2\b\u0010$\u001a\u0004\u0018\u00010%\u001a\u0015\u0010&\u001a\u00020'*\u00020\u000b2\u0006\u0010(\u001a\u00020\u000bH\u0086\u0002\u001a\f\u0010)\u001a\u00020**\u00020\u000bH\u0000\u001a\n\u0010+\u001a\u00020,*\u00020\u000b\u001a\u0012\u0010-\u001a\u00020\u0017*\u00020\u000b2\u0006\u0010(\u001a\u00020\u000b\u001a\f\u0010.\u001a\u00020'*\u00020\u000bH\u0000\u001a\u0014\u0010/\u001a\u00020\u0017*\u00020\u000b2\u0006\u0010(\u001a\u00020\u000bH\u0000\u001a\f\u00100\u001a\u00020\u0017*\u00020\u000bH\u0000\u001a\f\u00101\u001a\u00020\u0017*\u00020\u000bH\u0000\u001a\f\u00102\u001a\u000203*\u00020\u000bH\u0000\u001a\u0012\u00104\u001a\u00020\u000b*\u00020\u000b2\u0006\u00105\u001a\u00020\u000b\u001a\f\u00106\u001a\u00020\u0012*\u00020\u0012H\u0002\u001a\n\u00107\u001a\u000208*\u00020\u000b\u001a\u0018\u00109\u001a\u00020\u000b*\u00020\u000b2\f\u0010:\u001a\b\u0012\u0004\u0012\u00020\u00120\u0011\u001a\u0010\u0010;\u001a\b\u0012\u0004\u0012\u00020\u000b0<*\u00020\u000b\u001a\n\u0010=\u001a\u00020\u0012*\u00020\u000b\u001a\n\u0010>\u001a\u00020\u0012*\u00020\u000b\u001a\n\u0010?\u001a\u00020@*\u00020\u000b\u001a\n\u0010A\u001a\u00020B*\u00020\u000b\u001a\u0016\u0010C\u001a\u000203*\u0002082\b\u0010$\u001a\u0004\u0018\u00010%H\u0002\u001a\n\u0010D\u001a\u00020\u000b*\u00020\u000b\"\u0011\u0010\u0000\u001a\u00020\u0001\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0002\u0010\u0003\"\u0014\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000\"\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000\"\u000e\u0010\t\u001a\u00020\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000\"\u0017\u0010\n\u001a\u0004\u0018\u00010\u000b*\u00020\u000b8F\u00a2\u0006\u0006\u001a\u0004\b\f\u0010\r\"\u0017\u0010\u000e\u001a\u0004\u0018\u00010\u000b*\u00020\u000b8F\u00a2\u0006\u0006\u001a\u0004\b\u000f\u0010\r\"\u001d\u0010\u0010\u001a\n\u0012\u0004\u0012\u00020\u0012\u0018\u00010\u0011*\u00020\u000b8F\u00a2\u0006\u0006\u001a\u0004\b\u0013\u0010\u0014\u00a8\u0006E"}, d2={"DEFAULT_COMPARATOR", "Lorg/partiql/lang/eval/NaturalExprValueComparators;", "getDEFAULT_COMPARATOR", "()Lorg/partiql/lang/eval/NaturalExprValueComparators;", "ION_TEXT_STRING_CAST_TYPES", "", "Lorg/partiql/lang/eval/ExprValueType;", "datePatternRegex", "Lkotlin/text/Regex;", "genericTimeRegex", "address", "Lorg/partiql/lang/eval/ExprValue;", "getAddress", "(Lorg/partiql/lang/eval/ExprValue;)Lorg/partiql/lang/eval/ExprValue;", "name", "getName", "orderedNames", "", "", "getOrderedNames", "(Lorg/partiql/lang/eval/ExprValue;)Ljava/util/List;", "createUniqueExprValueFilter", "Lkotlin/Function1;", "", "asNamed", "Lorg/partiql/lang/eval/Named;", "bigDecimalValue", "Ljava/math/BigDecimal;", "booleanValue", "bytesValue", "", "cast", "targetDataType", "Lorg/partiql/lang/ast/DataType;", "valueFactory", "Lorg/partiql/lang/eval/ExprValueFactory;", "locationMeta", "Lorg/partiql/lang/ast/SourceLocationMeta;", "compareTo", "", "other", "datePartValue", "Lorg/partiql/lang/syntax/DatePart;", "dateValue", "Ljava/time/LocalDate;", "exprEquals", "intValue", "isDirectlyComparableTo", "isNotUnknown", "isUnknown", "longValue", "", "namedValue", "nameValue", "normalizeForCastToInt", "numberValue", "", "orderedNamesValue", "names", "rangeOver", "", "stringValue", "stringify", "timeValue", "Lorg/partiql/lang/eval/time/Time;", "timestampValue", "Lcom/amazon/ion/Timestamp;", "toLongFailingOverflow", "unnamedValue", "lang"})
public final class ExprValueExtensionsKt {
    @NotNull
    private static final NaturalExprValueComparators DEFAULT_COMPARATOR = NaturalExprValueComparators.NULLS_FIRST;
    private static final Set<ExprValueType> ION_TEXT_STRING_CAST_TYPES = SetsKt.setOf(ExprValueType.BOOL, ExprValueType.TIMESTAMP);
    private static final Regex datePatternRegex = new Regex("\\d\\d\\d\\d-\\d\\d-\\d\\d");
    private static final Regex genericTimeRegex = new Regex("\\d\\d:\\d\\d:\\d\\d(\\.\\d*)?([+|-]\\d\\d:\\d\\d)?");

    @NotNull
    public static final ExprValue orderedNamesValue(@NotNull ExprValue $this$orderedNamesValue, @NotNull List<String> names) {
        Intrinsics.checkParameterIsNotNull($this$orderedNamesValue, "$this$orderedNamesValue");
        Intrinsics.checkParameterIsNotNull(names, "names");
        return new ExprValue($this$orderedNamesValue, names){
            @NotNull
            private final List<String> orderedNames;
            private final /* synthetic */ ExprValue $$delegate_0;
            final /* synthetic */ ExprValue $this_orderedNamesValue;
            final /* synthetic */ List $names;

            @NotNull
            public List<String> getOrderedNames() {
                return this.orderedNames;
            }

            @Nullable
            public <T> T asFacet(@Nullable Class<T> type) {
                T t = FacetExtensionsKt.downcast(this, type);
                if (t == null) {
                    t = this.$this_orderedNamesValue.asFacet(type);
                }
                return t;
            }

            @NotNull
            public String toString() {
                return ExprValueExtensionsKt.stringify(this);
            }
            {
                this.$this_orderedNamesValue = $receiver;
                this.$names = $captured_local_variable$1;
                this.$$delegate_0 = this.$this_orderedNamesValue;
                this.orderedNames = $captured_local_variable$1;
            }

            @NotNull
            public Bindings<ExprValue> getBindings() {
                return this.$$delegate_0.getBindings();
            }

            @NotNull
            public IonValue getIonValue() {
                return this.$$delegate_0.getIonValue();
            }

            @NotNull
            public OrdinalBindings getOrdinalBindings() {
                return this.$$delegate_0.getOrdinalBindings();
            }

            @NotNull
            public Scalar getScalar() {
                return this.$$delegate_0.getScalar();
            }

            @NotNull
            public ExprValueType getType() {
                return this.$$delegate_0.getType();
            }

            @NotNull
            public Iterator<ExprValue> iterator() {
                return this.$$delegate_0.iterator();
            }
        };
    }

    @Nullable
    public static final List<String> getOrderedNames(@NotNull ExprValue $this$orderedNames) {
        Intrinsics.checkParameterIsNotNull($this$orderedNames, "$this$orderedNames");
        OrderedBindNames orderedBindNames2 = $this$orderedNames.asFacet(OrderedBindNames.class);
        return orderedBindNames2 != null ? orderedBindNames2.getOrderedNames() : null;
    }

    @NotNull
    public static final Named asNamed(@NotNull ExprValue $this$asNamed) {
        Intrinsics.checkParameterIsNotNull($this$asNamed, "$this$asNamed");
        return new Named($this$asNamed){
            final /* synthetic */ ExprValue $this_asNamed;

            @NotNull
            public ExprValue getName() {
                return this.$this_asNamed;
            }
            {
                this.$this_asNamed = $receiver;
            }
        };
    }

    @NotNull
    public static final ExprValue namedValue(@NotNull ExprValue $this$namedValue, @NotNull ExprValue nameValue) {
        Intrinsics.checkParameterIsNotNull($this$namedValue, "$this$namedValue");
        Intrinsics.checkParameterIsNotNull(nameValue, "nameValue");
        return new ExprValue($this$namedValue, nameValue){
            @NotNull
            private final ExprValue name;
            private final /* synthetic */ ExprValue $$delegate_0;
            final /* synthetic */ ExprValue $this_namedValue;
            final /* synthetic */ ExprValue $nameValue;

            @NotNull
            public ExprValue getName() {
                return this.name;
            }

            @Nullable
            public <T> T asFacet(@Nullable Class<T> type) {
                T t = FacetExtensionsKt.downcast(this, type);
                if (t == null) {
                    t = this.$this_namedValue.asFacet(type);
                }
                return t;
            }

            @NotNull
            public String toString() {
                return ExprValueExtensionsKt.stringify(this);
            }
            {
                this.$this_namedValue = $receiver;
                this.$nameValue = $captured_local_variable$1;
                this.$$delegate_0 = this.$this_namedValue;
                this.name = $captured_local_variable$1;
            }

            @NotNull
            public Bindings<ExprValue> getBindings() {
                return this.$$delegate_0.getBindings();
            }

            @NotNull
            public IonValue getIonValue() {
                return this.$$delegate_0.getIonValue();
            }

            @NotNull
            public OrdinalBindings getOrdinalBindings() {
                return this.$$delegate_0.getOrdinalBindings();
            }

            @NotNull
            public Scalar getScalar() {
                return this.$$delegate_0.getScalar();
            }

            @NotNull
            public ExprValueType getType() {
                return this.$$delegate_0.getType();
            }

            @NotNull
            public Iterator<ExprValue> iterator() {
                return this.$$delegate_0.iterator();
            }
        };
    }

    @NotNull
    public static final ExprValue unnamedValue(@NotNull ExprValue $this$unnamedValue) {
        Intrinsics.checkParameterIsNotNull($this$unnamedValue, "$this$unnamedValue");
        Named named = $this$unnamedValue.asFacet(Named.class);
        return named == null ? $this$unnamedValue : (ExprValue)new ExprValue($this$unnamedValue){
            private final /* synthetic */ ExprValue $$delegate_0;
            final /* synthetic */ ExprValue $this_unnamedValue;

            @Nullable
            public <T> T asFacet(@Nullable Class<T> type) {
                Class<T> clazz = type;
                return Intrinsics.areEqual(clazz, Named.class) ? null : (T)this.$this_unnamedValue.asFacet(type);
            }

            @NotNull
            public String toString() {
                return ExprValueExtensionsKt.stringify(this);
            }
            {
                this.$this_unnamedValue = $receiver;
                this.$$delegate_0 = this.$this_unnamedValue;
            }

            @NotNull
            public Bindings<ExprValue> getBindings() {
                return this.$$delegate_0.getBindings();
            }

            @NotNull
            public IonValue getIonValue() {
                return this.$$delegate_0.getIonValue();
            }

            @NotNull
            public OrdinalBindings getOrdinalBindings() {
                return this.$$delegate_0.getOrdinalBindings();
            }

            @NotNull
            public Scalar getScalar() {
                return this.$$delegate_0.getScalar();
            }

            @NotNull
            public ExprValueType getType() {
                return this.$$delegate_0.getType();
            }

            @NotNull
            public Iterator<ExprValue> iterator() {
                return this.$$delegate_0.iterator();
            }
        };
    }

    @Nullable
    public static final ExprValue getName(@NotNull ExprValue $this$name) {
        Intrinsics.checkParameterIsNotNull($this$name, "$this$name");
        Named named = $this$name.asFacet(Named.class);
        return named != null ? named.getName() : null;
    }

    @Nullable
    public static final ExprValue getAddress(@NotNull ExprValue $this$address) {
        Intrinsics.checkParameterIsNotNull($this$address, "$this$address");
        Addressed addressed = $this$address.asFacet(Addressed.class);
        return addressed != null ? addressed.getAddress() : null;
    }

    public static final boolean booleanValue(@NotNull ExprValue $this$booleanValue) {
        Intrinsics.checkParameterIsNotNull($this$booleanValue, "$this$booleanValue");
        Boolean bl = $this$booleanValue.getScalar().booleanValue();
        if (bl == null) {
            Void void_ = ExceptionsKt.errNoContext("Expected boolean: " + $this$booleanValue.getIonValue(), false);
            throw null;
        }
        return bl;
    }

    @NotNull
    public static final Number numberValue(@NotNull ExprValue $this$numberValue) {
        Intrinsics.checkParameterIsNotNull($this$numberValue, "$this$numberValue");
        Number number = $this$numberValue.getScalar().numberValue();
        if (number == null) {
            Void void_ = ExceptionsKt.errNoContext("Expected number: " + $this$numberValue.getIonValue(), false);
            throw null;
        }
        return number;
    }

    @NotNull
    public static final LocalDate dateValue(@NotNull ExprValue $this$dateValue) {
        Intrinsics.checkParameterIsNotNull($this$dateValue, "$this$dateValue");
        LocalDate localDate = $this$dateValue.getScalar().dateValue();
        if (localDate == null) {
            Void void_ = ExceptionsKt.errNoContext("Expected date: " + $this$dateValue.getIonValue(), false);
            throw null;
        }
        return localDate;
    }

    @NotNull
    public static final Time timeValue(@NotNull ExprValue $this$timeValue) {
        Intrinsics.checkParameterIsNotNull($this$timeValue, "$this$timeValue");
        Time time = $this$timeValue.getScalar().timeValue();
        if (time == null) {
            Void void_ = ExceptionsKt.errNoContext("Expected time: " + $this$timeValue.getIonValue(), false);
            throw null;
        }
        return time;
    }

    @NotNull
    public static final Timestamp timestampValue(@NotNull ExprValue $this$timestampValue) {
        Intrinsics.checkParameterIsNotNull($this$timestampValue, "$this$timestampValue");
        Timestamp timestamp = $this$timestampValue.getScalar().timestampValue();
        if (timestamp == null) {
            Void void_ = ExceptionsKt.errNoContext("Expected timestamp: " + $this$timestampValue.getIonValue(), false);
            throw null;
        }
        return timestamp;
    }

    @NotNull
    public static final String stringValue(@NotNull ExprValue $this$stringValue) {
        Intrinsics.checkParameterIsNotNull($this$stringValue, "$this$stringValue");
        String string = $this$stringValue.getScalar().stringValue();
        if (string == null) {
            Void void_ = ExceptionsKt.errNoContext("Expected text: " + $this$stringValue.getIonValue(), false);
            throw null;
        }
        return string;
    }

    @NotNull
    public static final byte[] bytesValue(@NotNull ExprValue $this$bytesValue) {
        Intrinsics.checkParameterIsNotNull($this$bytesValue, "$this$bytesValue");
        byte[] byArray = $this$bytesValue.getScalar().bytesValue();
        if (byArray == null) {
            Void void_ = ExceptionsKt.errNoContext("Expected LOB: " + $this$bytesValue.getIonValue(), false);
            throw null;
        }
        return byArray;
    }

    @NotNull
    public static final DatePart datePartValue(@NotNull ExprValue $this$datePartValue) {
        Object object;
        Intrinsics.checkParameterIsNotNull($this$datePartValue, "$this$datePartValue");
        try {
            object = ExprValueExtensionsKt.stringValue($this$datePartValue);
            boolean bl = false;
            String string = object;
            if (string == null) {
                throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
            }
            String string2 = string.toUpperCase();
            Intrinsics.checkExpressionValueIsNotNull(string2, "(this as java.lang.String).toUpperCase()");
            object = DatePart.valueOf(string2);
        } catch (IllegalArgumentException e) {
            boolean bl = false;
            String string = "invalid date part, valid values: [" + CollectionsKt.joinToString$default(LexerConstantsKt.getDATE_PART_KEYWORDS(), null, null, null, 0, null, null, 63, null) + ']';
            PropertyValueMap propertyValueMap = null;
            ErrorCode errorCode = null;
            Throwable throwable = e;
            throw (Throwable)new EvaluationException(string, errorCode, propertyValueMap, throwable, bl, 6, null);
        }
        return object;
    }

    public static final int intValue(@NotNull ExprValue $this$intValue) {
        Intrinsics.checkParameterIsNotNull($this$intValue, "$this$intValue");
        return ExprValueExtensionsKt.numberValue($this$intValue).intValue();
    }

    public static final long longValue(@NotNull ExprValue $this$longValue) {
        Intrinsics.checkParameterIsNotNull($this$longValue, "$this$longValue");
        return ExprValueExtensionsKt.numberValue($this$longValue).longValue();
    }

    @NotNull
    public static final BigDecimal bigDecimalValue(@NotNull ExprValue $this$bigDecimalValue) {
        Intrinsics.checkParameterIsNotNull($this$bigDecimalValue, "$this$bigDecimalValue");
        String string = ExprValueExtensionsKt.numberValue($this$bigDecimalValue).toString();
        boolean bl = false;
        return new BigDecimal(string);
    }

    @NotNull
    public static final Iterable<ExprValue> rangeOver(@NotNull ExprValue $this$rangeOver) {
        Intrinsics.checkParameterIsNotNull($this$rangeOver, "$this$rangeOver");
        return $this$rangeOver.getType().isRangedFrom() ? (Iterable)$this$rangeOver : (Iterable)CollectionsKt.listOf(ExprValueExtensionsKt.unnamedValue($this$rangeOver));
    }

    @NotNull
    public static final String stringify(@NotNull ExprValue $this$stringify) {
        Intrinsics.checkParameterIsNotNull($this$stringify, "$this$stringify");
        return ConfigurableExprValueFormatter.Companion.getStandard().format($this$stringify);
    }

    @NotNull
    public static final NaturalExprValueComparators getDEFAULT_COMPARATOR() {
        return DEFAULT_COMPARATOR;
    }

    public static final boolean exprEquals(@NotNull ExprValue $this$exprEquals, @NotNull ExprValue other) {
        Intrinsics.checkParameterIsNotNull($this$exprEquals, "$this$exprEquals");
        Intrinsics.checkParameterIsNotNull(other, "other");
        return DEFAULT_COMPARATOR.compare($this$exprEquals, other) == 0;
    }

    public static final int compareTo(@NotNull ExprValue $this$compareTo, @NotNull ExprValue other) {
        Intrinsics.checkParameterIsNotNull($this$compareTo, "$this$compareTo");
        Intrinsics.checkParameterIsNotNull(other, "other");
        if ($this$compareTo.getType().isUnknown() || other.getType().isUnknown()) {
            throw (Throwable)new EvaluationException("Null value cannot be compared: " + $this$compareTo + ", " + other, null, null, null, false, 14, null);
        }
        if (!ExprValueExtensionsKt.isDirectlyComparableTo($this$compareTo, other)) {
            Void void_ = ExceptionsKt.errNoContext("Cannot compare values: " + $this$compareTo + ", " + other, false);
            throw null;
        }
        return DEFAULT_COMPARATOR.compare($this$compareTo, other);
    }

    public static final boolean isDirectlyComparableTo(@NotNull ExprValue $this$isDirectlyComparableTo, @NotNull ExprValue other) {
        Intrinsics.checkParameterIsNotNull($this$isDirectlyComparableTo, "$this$isDirectlyComparableTo");
        Intrinsics.checkParameterIsNotNull(other, "other");
        return $this$isDirectlyComparableTo.getType() == ExprValueType.TIME && other.getType() == ExprValueType.TIME ? ExprValueExtensionsKt.timeValue($this$isDirectlyComparableTo).isDirectlyComparableTo(ExprValueExtensionsKt.timeValue(other)) : $this$isDirectlyComparableTo.getType().isDirectlyComparableTo(other.getType());
    }

    /*
     * Unable to fully structure code
     */
    @NotNull
    public static final ExprValue cast(@NotNull ExprValue $this$cast, @NotNull DataType targetDataType, @NotNull ExprValueFactory valueFactory, @Nullable SourceLocationMeta locationMeta) {
        Intrinsics.checkParameterIsNotNull($this$cast, "$this$cast");
        Intrinsics.checkParameterIsNotNull(targetDataType, "targetDataType");
        Intrinsics.checkParameterIsNotNull(valueFactory, "valueFactory");
        targetSqlDataType = targetDataType.getSqlDataType();
        targetExprValueType = ExprValueType.Companion.fromSqlDataType(targetSqlDataType);
        $fun$castExceptionContext$1 = new Function0<PropertyValueMap>($this$cast, targetSqlDataType, locationMeta){
            final /* synthetic */ ExprValue $this_cast;
            final /* synthetic */ SqlDataType $targetSqlDataType;
            final /* synthetic */ SourceLocationMeta $locationMeta;

            @NotNull
            public final PropertyValueMap invoke() {
                PropertyValueMap errorContext;
                block0: {
                    Object object = new PropertyValueMap(null, 1, null);
                    boolean bl = false;
                    boolean bl2 = false;
                    Object it = object;
                    boolean bl3 = false;
                    ((PropertyValueMap)it).set(Property.CAST_FROM, this.$this_cast.getType().toString());
                    ((PropertyValueMap)it).set(Property.CAST_TO, this.$targetSqlDataType.toString());
                    errorContext = object;
                    SourceLocationMeta sourceLocationMeta = this.$locationMeta;
                    if (sourceLocationMeta == null) break block0;
                    object = sourceLocationMeta;
                    bl = false;
                    bl2 = false;
                    it = object;
                    boolean bl4 = false;
                    ExceptionsKt.fillErrorContext(errorContext, (SourceLocationMeta)it);
                }
                return errorContext;
            }
            {
                this.$this_cast = exprValue2;
                this.$targetSqlDataType = sqlDataType;
                this.$locationMeta = sourceLocationMeta;
                super(0);
            }
        };
        $fun$castFailedErr$2 = new Function3($fun$castExceptionContext$1, locationMeta){
            final /* synthetic */ cast.1 $castExceptionContext$1;
            final /* synthetic */ SourceLocationMeta $locationMeta;

            @NotNull
            public final Void invoke(@NotNull String message, boolean internal, @Nullable Throwable cause) {
                Intrinsics.checkParameterIsNotNull(message, "message");
                Object errorContext = this.$castExceptionContext$1.invoke();
                ErrorCode errorCode = this.$locationMeta == null ? ErrorCode.EVALUATOR_CAST_FAILED_NO_LOCATION : ErrorCode.EVALUATOR_CAST_FAILED;
                Throwable throwable = cause;
                boolean bl = internal;
                throw (Throwable)new EvaluationException(message, errorCode, (PropertyValueMap)errorContext, throwable, bl);
            }

            public static /* synthetic */ Void invoke$default(cast.2 var0, String string, boolean bl, Throwable throwable, int n, Object object) {
                if ((n & 4) != 0) {
                    throwable = null;
                }
                return var0.invoke(string, bl, throwable);
            }
            {
                this.$castExceptionContext$1 = var1_1;
                this.$locationMeta = sourceLocationMeta;
                super(3);
            }
        };
        $fun$exprValue$3 = new Function1<Number, ExprValue>(valueFactory){
            final /* synthetic */ ExprValueFactory $valueFactory;

            @NotNull
            public final ExprValue invoke(@NotNull Number $this$exprValue) {
                Intrinsics.checkParameterIsNotNull($this$exprValue, "$this$exprValue");
                return this.$valueFactory.newFromIonValue(NumberExtensionsKt.ionValue($this$exprValue, this.$valueFactory.getIon()));
            }
            {
                this.$valueFactory = exprValueFactory;
                super(1);
            }
        };
        $fun$exprValue$4 = new Function2<String, ExprValueType, ExprValue>(valueFactory, $fun$castFailedErr$2){
            final /* synthetic */ ExprValueFactory $valueFactory;
            final /* synthetic */ cast.2 $castFailedErr$2;

            @NotNull
            public final ExprValue invoke(@NotNull String $this$exprValue, @NotNull ExprValueType type) {
                IonText ionText;
                Intrinsics.checkParameterIsNotNull($this$exprValue, "$this$exprValue");
                Intrinsics.checkParameterIsNotNull((Object)((Object)type), "type");
                switch (ExprValueExtensionsKt$WhenMappings.$EnumSwitchMapping$0[type.ordinal()]) {
                    case 1: {
                        ionText = this.$valueFactory.getIon().newString($this$exprValue);
                        break;
                    }
                    case 2: {
                        ionText = this.$valueFactory.getIon().newSymbol($this$exprValue);
                        break;
                    }
                    default: {
                        Void void_ = cast.2.invoke$default(this.$castFailedErr$2, "Invalid type for textual conversion: " + (Object)((Object)type) + " (this code should be unreachable)", true, null, 4, null);
                        throw null;
                    }
                }
                Intrinsics.checkExpressionValueIsNotNull(ionText, "when (type) {\n        ST\u2026\", internal = true)\n    }");
                return this.$valueFactory.newFromIonValue(ionText);
            }
            {
                this.$valueFactory = exprValueFactory;
                this.$castFailedErr$2 = var2_2;
                super(2);
            }
        };
        if ($this$cast.getType().isUnknown() && targetSqlDataType == SqlDataType.MISSING) {
            return valueFactory.getMissingValue();
        }
        if ($this$cast.getType().isUnknown() && targetSqlDataType == SqlDataType.NULL) {
            return valueFactory.getNullValue();
        }
        if ($this$cast.getType().isUnknown() || $this$cast.getType() == targetExprValueType && $this$cast.getType() != ExprValueType.TIME) {
            return $this$cast;
        }
        switch (ExprValueExtensionsKt$WhenMappings.$EnumSwitchMapping$5[targetSqlDataType.ordinal()]) {
            case 1: {
                if ($this$cast.getType().isNumber()) {
                    return NumberExtensionsKt.compareTo(ExprValueExtensionsKt.numberValue($this$cast), 0L) == 0 ? valueFactory.newBoolean(false) : valueFactory.newBoolean(true);
                }
                if (!$this$cast.getType().isText()) break;
                var10_10 = ExprValueExtensionsKt.stringValue($this$cast);
                var11_20 = false;
                v0 = var10_10;
                if (v0 == null) {
                    throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                }
                v1 = v0.toLowerCase();
                Intrinsics.checkExpressionValueIsNotNull(v1, "(this as java.lang.String).toLowerCase()");
                var10_10 = v1;
                tmp = -1;
                switch (var10_10.hashCode()) {
                    case 3569038: {
                        if (!var10_10.equals("true")) break;
                        tmp = 1;
                        break;
                    }
                    case 97196323: {
                        if (!var10_10.equals("false")) break;
                        tmp = 2;
                        break;
                    }
                }
                switch (tmp) {
                    case 1: {
                        v2 = valueFactory.newBoolean(true);
                        break;
                    }
                    case 2: {
                        v2 = valueFactory.newBoolean(false);
                        break;
                    }
                    default: {
                        v3 = cast.2.invoke$default($fun$castFailedErr$2, "can't convert string value to BOOL", false, null, 4, null);
                        throw null;
                    }
                }
                return v2;
            }
            case 2: 
            case 3: {
                if ($this$cast.getType() == ExprValueType.BOOL) {
                    return valueFactory.newInt(ExprValueExtensionsKt.booleanValue($this$cast) != false ? 1L : 0L);
                }
                if ($this$cast.getType().isNumber()) {
                    return valueFactory.newInt(ExprValueExtensionsKt.toLongFailingOverflow(ExprValueExtensionsKt.numberValue($this$cast), locationMeta));
                }
                if (!$this$cast.getType().isText()) break;
                try {
                    normalized = ExprValueExtensionsKt.normalizeForCastToInt(ExprValueExtensionsKt.stringValue($this$cast));
                    v4 = valueFactory.getIon().singleValue((String)normalized);
                    if (v4 == null) {
                        throw new TypeCastException("null cannot be cast to non-null type com.amazon.ion.IonInt");
                    }
                    normalized = (IonInt)v4;
                } catch (Exception e) {
                    v5 = $fun$castFailedErr$2.invoke("can't convert string value to INT", false, e);
                    throw null;
                }
                value = normalized;
                v6 = value.getIntegerSize();
                if (v6 != null) {
                    switch (ExprValueExtensionsKt$WhenMappings.$EnumSwitchMapping$1[v6.ordinal()]) {
                        case 1: {
                            v7 = ExceptionsKt.errIntOverflow(ExceptionsKt.errorContextFrom(locationMeta));
                            throw null;
                        }
                    }
                }
                return $fun$exprValue$3.invoke((Number)value.longValue());
            }
            case 4: 
            case 5: 
            case 6: {
                if ($this$cast.getType() == ExprValueType.BOOL) {
                    return ExprValueExtensionsKt.booleanValue($this$cast) != false ? $fun$exprValue$3.invoke((Number)1.0) : $fun$exprValue$3.invoke((Number)0.0);
                }
                if ($this$cast.getType().isNumber()) {
                    return $fun$exprValue$3.invoke((Number)ExprValueExtensionsKt.numberValue($this$cast).doubleValue());
                }
                if (!$this$cast.getType().isText()) break;
                try {
                    value = ExprValueExtensionsKt.stringValue($this$cast);
                    var20_31 = $fun$exprValue$3;
                    normalized = false;
                    var21_33 = Double.parseDouble(value);
                    return var20_31.invoke((Number)var21_33);
                } catch (NumberFormatException e) {
                    v8 = $fun$castFailedErr$2.invoke("can't convert string value to FLOAT", false, e);
                    throw null;
                }
            }
            case 7: 
            case 8: {
                if ($this$cast.getType() == ExprValueType.BOOL) {
                    if (ExprValueExtensionsKt.booleanValue($this$cast)) {
                        v9 = BigDecimal.ONE;
                        Intrinsics.checkExpressionValueIsNotNull(v9, "BigDecimal.ONE");
                        v10 = $fun$exprValue$3.invoke((Number)v9);
                    } else {
                        v11 = BigDecimal.ZERO;
                        Intrinsics.checkExpressionValueIsNotNull(v11, "BigDecimal.ZERO");
                        v10 = $fun$exprValue$3.invoke((Number)v11);
                    }
                    return v10;
                }
                if ($this$cast.getType().isNumber()) {
                    return $fun$exprValue$3.invoke(NumberExtensionsKt.coerce(ExprValueExtensionsKt.numberValue($this$cast), BigDecimal.class));
                }
                if (!$this$cast.getType().isText()) break;
                try {
                    return $fun$exprValue$3.invoke((Number)NumberExtensionsKt.bigDecimalOf$default(ExprValueExtensionsKt.stringValue($this$cast), null, 2, null));
                } catch (NumberFormatException e) {
                    v12 = $fun$castFailedErr$2.invoke("can't convert string value to DECIMAL", false, e);
                    throw null;
                }
            }
            case 9: {
                if (!$this$cast.getType().isText()) break;
                try {
                    v13 = Timestamp.valueOf(ExprValueExtensionsKt.stringValue($this$cast));
                    Intrinsics.checkExpressionValueIsNotNull(v13, "Timestamp.valueOf(stringValue())");
                    return valueFactory.newTimestamp(v13);
                } catch (IllegalArgumentException e) {
                    v14 = $fun$castFailedErr$2.invoke("can't convert string value to TIMESTAMP", false, e);
                    throw null;
                }
            }
            case 10: {
                if ($this$cast.getType() == ExprValueType.TIMESTAMP) {
                    ts = ExprValueExtensionsKt.timestampValue($this$cast);
                    v15 = LocalDate.of(ts.getYear(), ts.getMonth(), ts.getDay());
                    Intrinsics.checkExpressionValueIsNotNull(v15, "LocalDate.of(ts.year, ts.month, ts.day)");
                    return valueFactory.newDate(v15);
                }
                if (!$this$cast.getType().isText()) break;
                try {
                    if (!ExprValueExtensionsKt.datePatternRegex.matches(ExprValueExtensionsKt.stringValue($this$cast))) {
                        v16 = cast.2.invoke$default($fun$castFailedErr$2, "Can't convert string value to DATE. Expected valid date string and the date format to be YYYY-MM-DD", false, null, 4, null);
                        throw null;
                    }
                    v17 = date = LocalDate.parse(ExprValueExtensionsKt.stringValue($this$cast));
                    Intrinsics.checkExpressionValueIsNotNull(v17, "date");
                    return valueFactory.newDate(v17);
                } catch (DateTimeParseException e) {
                    v18 = $fun$castFailedErr$2.invoke("Can't convert string value to DATE. Expected valid date string and the date format to be YYYY-MM-DD", false, e);
                    throw null;
                }
            }
            case 11: 
            case 12: {
                v19 = CollectionsKt.firstOrNull(targetDataType.getArgs());
                precision = v19 != null ? Integer.valueOf((int)v19.longValue()) : null;
                if ($this$cast.getType() == ExprValueType.TIME) {
                    time = ExprValueExtensionsKt.timeValue($this$cast);
                    switch (ExprValueExtensionsKt$WhenMappings.$EnumSwitchMapping$2[targetSqlDataType.ordinal()]) {
                        case 1: {
                            v20 = time.getZoneOffset();
                            if (v20 != null) break;
                            v20 = TimeExtensionsKt.getDEFAULT_TIMEZONE_OFFSET();
                            break;
                        }
                        default: {
                            v20 = null;
                        }
                    }
                    timeZoneOffset = v20;
                    v21 = precision;
                    return valueFactory.newTime(Time.Companion.of(time.getLocalTime(), v21 != null ? v21.intValue() : time.getPrecision(), timeZoneOffset));
                }
                if ($this$cast.getType() == ExprValueType.TIMESTAMP) {
                    ts = ExprValueExtensionsKt.timestampValue($this$cast);
                    switch (ExprValueExtensionsKt$WhenMappings.$EnumSwitchMapping$3[targetSqlDataType.ordinal()]) {
                        case 1: {
                            v22 = ts.getLocalOffset();
                            if (v22 != null) break;
                            v23 = cast.2.invoke$default($fun$castFailedErr$2, "Can't convert timestamp value with unknown local offset (i.e. -00:00) to TIME WITH TIME ZONE.", false, null, 4, null);
                            throw null;
                        }
                        default: {
                            v22 = null;
                        }
                    }
                    timeZoneOffset = v22;
                    var13_35 = 1000000000;
                    var25_37 = ts.getDecimalSecond().remainder(BigDecimal.ONE);
                    var24_38 = ts.getSecond();
                    var23_39 = ts.getMinute();
                    var22_40 = ts.getHour();
                    var21_34 = Time.Companion;
                    var20_32 = valueFactory;
                    var14_41 = false;
                    v24 = BigDecimal.valueOf(var13_35);
                    Intrinsics.checkExpressionValueIsNotNull(v24, "BigDecimal.valueOf(this.toLong())");
                    var26_43 = v24;
                    v25 = precision;
                    return var20_32.newTime(var21_34.of(var22_40, var23_39, var24_38, var25_37.multiply(var26_43).intValue(), v25 != null ? v25.intValue() : ts.getDecimalSecond().scale(), timeZoneOffset));
                }
                if (!$this$cast.getType().isText()) break;
                try {
                    matcher = ExprValueExtensionsKt.genericTimeRegex.toPattern().matcher(ExprValueExtensionsKt.stringValue($this$cast));
                    if (!matcher.find()) {
                        v26 = cast.2.invoke$default($fun$castFailedErr$2, "Can't convert string value to TIME. Expected valid time string and the time to be of the format HH:MM:SS[.ddddd...][+|-HH:MM]", false, null, 4, null);
                        throw null;
                    }
                    localTime = LocalTime.parse(ExprValueExtensionsKt.stringValue($this$cast), DateTimeFormatter.ISO_TIME);
                    zoneOffsetString = matcher.group(2);
                    v27 = zoneOffsetString;
                    if (v27 == null) ** GOTO lbl206
                    var15_44 = v27;
                    var16_45 = false;
                    var17_46 = false;
                    it = var15_44;
                    $i$a$-let-ExprValueExtensionsKt$cast$zoneOffset$1 = false;
                    v27 = ZoneOffset.of(it);
                    if (v27 != null) ** GOTO lbl207
lbl206:
                    // 2 sources

                    v27 = TimeExtensionsKt.getDEFAULT_TIMEZONE_OFFSET();
lbl207:
                    // 2 sources

                    zoneOffset = v27;
                    v28 = localTime;
                    Intrinsics.checkExpressionValueIsNotNull(v28, "localTime");
                    v29 = precision;
                    v30 = v29 != null ? v29 : TimeExtensionsKt.getPrecisionFromTimeString(ExprValueExtensionsKt.stringValue($this$cast));
                    switch (ExprValueExtensionsKt$WhenMappings.$EnumSwitchMapping$4[targetSqlDataType.ordinal()]) {
                        case 1: {
                            v31 = zoneOffset;
                            break;
                        }
                        default: {
                            v31 = null;
                        }
                    }
                    return valueFactory.newTime(Time.Companion.of(v28, v30, (ZoneOffset)v31));
                } catch (DateTimeParseException e) {
                    v32 = $fun$castFailedErr$2.invoke("Can't convert string value to TIME. Expected valid time string and the time format to be HH:MM:SS[.ddddd...][+|-HH:MM]", false, e);
                    throw null;
                }
            }
            case 13: 
            case 14: 
            case 15: 
            case 16: {
                if ($this$cast.getType().isNumber()) {
                    return $fun$exprValue$4.invoke(ExprValueExtensionsKt.numberValue($this$cast).toString(), targetExprValueType);
                }
                if ($this$cast.getType().isText()) {
                    return $fun$exprValue$4.invoke(ExprValueExtensionsKt.stringValue($this$cast), targetExprValueType);
                }
                if ($this$cast.getType() == ExprValueType.DATE) {
                    v33 = ExprValueExtensionsKt.dateValue($this$cast).toString();
                    Intrinsics.checkExpressionValueIsNotNull(v33, "dateValue().toString()");
                    return $fun$exprValue$4.invoke(v33, targetExprValueType);
                }
                if ($this$cast.getType() == ExprValueType.TIME) {
                    return $fun$exprValue$4.invoke(ExprValueExtensionsKt.timeValue($this$cast).toString(), targetExprValueType);
                }
                if (!ExprValueExtensionsKt.ION_TEXT_STRING_CAST_TYPES.contains((Object)$this$cast.getType())) break;
                return $fun$exprValue$4.invoke($this$cast.getIonValue().toString(), targetExprValueType);
            }
            case 17: {
                if (!$this$cast.getType().isLob()) break;
                return valueFactory.newClob(ExprValueExtensionsKt.bytesValue($this$cast));
            }
            case 18: {
                if (!$this$cast.getType().isLob()) break;
                return valueFactory.newBlob(ExprValueExtensionsKt.bytesValue($this$cast));
            }
            case 19: {
                if (!$this$cast.getType().isSequence()) break;
                return valueFactory.newList(CollectionsKt.asSequence($this$cast));
            }
            case 20: {
                if (!$this$cast.getType().isSequence()) break;
                return valueFactory.newSexp(CollectionsKt.asSequence($this$cast));
            }
            case 21: {
                if (!$this$cast.getType().isSequence()) break;
                return valueFactory.newBag(CollectionsKt.asSequence($this$cast));
            }
        }
        errorCode = locationMeta == null ? ErrorCode.EVALUATOR_INVALID_CAST_NO_LOCATION : ErrorCode.EVALUATOR_INVALID_CAST;
        v34 = ExceptionsKt.err("Cannot convert " + (Object)$this$cast.getType() + " to " + (Object)targetSqlDataType, errorCode, (PropertyValueMap)$fun$castExceptionContext$1.invoke(), false);
        throw null;
    }

    /*
     * WARNING - void declaration
     */
    private static final String normalizeForCastToInt(@NotNull String $this$normalizeForCastToInt) {
        String string;
        normalizeForCastToInt.1 $fun$isSign$1 = normalizeForCastToInt.1.INSTANCE;
        normalizeForCastToInt.2 $fun$isHexOrBase2Marker$2 = normalizeForCastToInt.2.INSTANCE;
        normalizeForCastToInt.3 $fun$possiblyHexOrBase2$3 = normalizeForCastToInt.3.INSTANCE;
        if ($this$normalizeForCastToInt.length() == 0) {
            string = $this$normalizeForCastToInt;
        } else if ($fun$possiblyHexOrBase2$3.invoke($this$normalizeForCastToInt)) {
            string = $this$normalizeForCastToInt.charAt(0) == '+' ? StringsKt.drop($this$normalizeForCastToInt, 1) : $this$normalizeForCastToInt;
        } else {
            void isNegative;
            int startIndex;
            int toDrop;
            Pair<Boolean, Integer> pair;
            switch ($this$normalizeForCastToInt.charAt(0)) {
                case '-': {
                    pair = new Pair<Boolean, Integer>(true, 1);
                    break;
                }
                case '+': {
                    pair = new Pair<Boolean, Integer>(false, 1);
                    break;
                }
                default: {
                    pair = new Pair<Boolean, Integer>(false, 0);
                }
            }
            Pair<Boolean, Integer> pair2 = pair;
            boolean bl = pair2.component1();
            for (toDrop = startIndex = ((Number)pair2.component2()).intValue(); toDrop < $this$normalizeForCastToInt.length() && $this$normalizeForCastToInt.charAt(toDrop) == '0'; ++toDrop) {
            }
            if (toDrop == $this$normalizeForCastToInt.length()) {
                string = "0";
            } else if (toDrop == 0) {
                string = $this$normalizeForCastToInt;
            } else if (toDrop == 1 && isNegative != false) {
                string = $this$normalizeForCastToInt;
            } else if (toDrop > 1 && isNegative != false) {
                char c = '-';
                String string2 = StringsKt.drop($this$normalizeForCastToInt, toDrop);
                boolean bl2 = false;
                string = String.valueOf(c) + string2;
            } else {
                string = StringsKt.drop($this$normalizeForCastToInt, toDrop);
            }
        }
        return string;
    }

    private static final long toLongFailingOverflow(@NotNull Number $this$toLongFailingOverflow, SourceLocationMeta locationMeta) {
        if (NumberExtensionsKt.compareTo(Long.MIN_VALUE, $this$toLongFailingOverflow) > 0 || NumberExtensionsKt.compareTo(Long.MAX_VALUE, $this$toLongFailingOverflow) < 0) {
            Void void_ = ExceptionsKt.errIntOverflow(ExceptionsKt.errorContextFrom(locationMeta));
            throw null;
        }
        return $this$toLongFailingOverflow instanceof BigDecimal ? ((BigDecimal)$this$toLongFailingOverflow).divideToIntegralValue(BigDecimal.ONE).longValue() : $this$toLongFailingOverflow.longValue();
    }

    public static final boolean isUnknown(@NotNull ExprValue $this$isUnknown) {
        Intrinsics.checkParameterIsNotNull($this$isUnknown, "$this$isUnknown");
        return $this$isUnknown.getType().isUnknown();
    }

    public static final boolean isNotUnknown(@NotNull ExprValue $this$isNotUnknown) {
        Intrinsics.checkParameterIsNotNull($this$isNotUnknown, "$this$isNotUnknown");
        return !$this$isNotUnknown.getType().isUnknown();
    }

    @NotNull
    public static final Function1<ExprValue, Boolean> createUniqueExprValueFilter() {
        TreeSet seen = new TreeSet(DEFAULT_COMPARATOR);
        return new Function1<ExprValue, Boolean>(seen){
            final /* synthetic */ TreeSet $seen;

            public final boolean invoke(@NotNull ExprValue exprValue2) {
                Intrinsics.checkParameterIsNotNull(exprValue2, "exprValue");
                return this.$seen.add(exprValue2);
            }
            {
                this.$seen = treeSet;
                super(1);
            }
        };
    }
}

