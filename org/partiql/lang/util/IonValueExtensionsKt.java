/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package org.partiql.lang.util;

import com.amazon.ion.Decimal;
import com.amazon.ion.IntegerSize;
import com.amazon.ion.IonBool;
import com.amazon.ion.IonContainer;
import com.amazon.ion.IonDecimal;
import com.amazon.ion.IonFloat;
import com.amazon.ion.IonInt;
import com.amazon.ion.IonLob;
import com.amazon.ion.IonSequence;
import com.amazon.ion.IonSexp;
import com.amazon.ion.IonStruct;
import com.amazon.ion.IonSymbol;
import com.amazon.ion.IonText;
import com.amazon.ion.IonTimestamp;
import com.amazon.ion.IonValue;
import com.amazon.ion.Timestamp;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.JvmName;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.Sequence;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.partiql.lang.util.IonValueExtensionsKt$WhenMappings;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=2, d1={"\u0000\u0086\u0001\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\f\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u0001\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0012\n\u0002\b\u0002\n\u0002\u0010\u0006\n\u0002\b\n\n\u0002\u0010(\n\u0000\n\u0002\u0010\u0004\n\u0000\n\u0002\u0010\t\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u001c\n\u0000\u001a\u0015\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u0018H\u0003\u00a2\u0006\u0002\b\u001e\u001a\n\u0010\u001f\u001a\u00020 *\u00020\u0002\u001a\n\u0010!\u001a\u00020\u0003*\u00020\u0002\u001a\n\u0010\"\u001a\u00020#*\u00020\u0002\u001a\n\u0010$\u001a\u00020%*\u00020\u0002\u001a\u0010\u0010&\u001a\b\u0012\u0004\u0012\u00020\u00020'*\u00020\u0002\u001a\n\u0010(\u001a\u00020)*\u00020\u0002\u001a\f\u0010*\u001a\u0004\u0018\u00010)*\u00020\u0002\u001a\n\u0010+\u001a\u00020\u000b*\u00020\u0002\u001a\u0011\u0010,\u001a\u0004\u0018\u00010\u000b*\u00020\u0002\u00a2\u0006\u0002\u0010-\u001a\n\u0010.\u001a\u00020/*\u00020\u0002\u001a\f\u00100\u001a\u0004\u0018\u00010/*\u00020\u0002\u001a\n\u00101\u001a\u000202*\u00020\u0002\u001a\u0011\u00103\u001a\u0004\u0018\u000102*\u00020\u0002\u00a2\u0006\u0002\u00104\u001a\u0012\u00105\u001a\u00020\u0002*\u00020#2\u0006\u00106\u001a\u00020\u0018\u001a\n\u00107\u001a\u00020\u0002*\u00020\u0003\u001a\u0015\u00108\u001a\u00020\u0002*\u00020\u00022\u0006\u00109\u001a\u00020\u0007H\u0086\u0002\u001a\u0017\u00108\u001a\u0004\u0018\u00010\u0002*\u00020\u00022\u0006\u0010:\u001a\u00020\u0018H\u0086\u0002\u001a\n\u0010;\u001a\u00020\u000b*\u00020\u0002\u001a\u0013\u0010<\u001a\b\u0012\u0004\u0012\u00020\u00020=*\u00020\u0002H\u0086\u0002\u001a\f\u0010>\u001a\u00020?*\u00020 H\u0002\u001a\n\u0010@\u001a\u00020A*\u00020\u0002\u001a\u0011\u0010B\u001a\u0004\u0018\u00010A*\u00020\u0002\u00a2\u0006\u0002\u0010C\u001a\n\u0010D\u001a\u00020?*\u00020\u0002\u001a\f\u0010E\u001a\u0004\u0018\u00010?*\u00020\u0002\u001a\n\u0010F\u001a\u00020\u0002*\u00020\u0002\u001a\u0012\u0010G\u001a\u00020\u0002*\u00020\u00032\u0006\u0010H\u001a\u00020\u0018\u001a\u0014\u0010I\u001a\u0004\u0018\u00010\u0002*\u00020\u00032\u0006\u0010H\u001a\u00020\u0018\u001a\f\u0010J\u001a\u0004\u0018\u00010\u0018*\u00020\u0002\u001a\f\u0010K\u001a\u0004\u0018\u00010\u0018*\u00020\u0002\u001a\n\u0010L\u001a\u00020M*\u00020\u0002\u001a\f\u0010N\u001a\u0004\u0018\u00010M*\u00020\u0002\u001a\u0016\u0010O\u001a\b\u0012\u0004\u0012\u00020\u00030\u0001*\b\u0012\u0004\u0012\u00020\u00020P\"\u001b\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001*\u00020\u00038F\u00a2\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005\"\u0015\u0010\u0006\u001a\u00020\u0007*\u00020\u00038F\u00a2\u0006\u0006\u001a\u0004\b\b\u0010\t\"\u0015\u0010\n\u001a\u00020\u000b*\u00020\u00028F\u00a2\u0006\u0006\u001a\u0004\b\n\u0010\f\"\u0015\u0010\r\u001a\u00020\u000b*\u00020\u00028F\u00a2\u0006\u0006\u001a\u0004\b\r\u0010\f\"\u0015\u0010\u000e\u001a\u00020\u000b*\u00020\u00028F\u00a2\u0006\u0006\u001a\u0004\b\u000e\u0010\f\"\u0015\u0010\u000f\u001a\u00020\u000b*\u00020\u00028F\u00a2\u0006\u0006\u001a\u0004\b\u000f\u0010\f\"\u0015\u0010\u0010\u001a\u00020\u0007*\u00020\u00028F\u00a2\u0006\u0006\u001a\u0004\b\u0011\u0010\u0012\"\u0015\u0010\u0013\u001a\u00020\u0007*\u00020\u00028F\u00a2\u0006\u0006\u001a\u0004\b\u0014\u0010\u0012\"\u0015\u0010\u0015\u001a\u00020\u0007*\u00020\u00028F\u00a2\u0006\u0006\u001a\u0004\b\u0016\u0010\u0012\"\u0015\u0010\u0017\u001a\u00020\u0018*\u00020\u00038F\u00a2\u0006\u0006\u001a\u0004\b\u0019\u0010\u001a\u00a8\u0006Q"}, d2={"args", "", "Lcom/amazon/ion/IonValue;", "Lcom/amazon/ion/IonSexp;", "getArgs", "(Lcom/amazon/ion/IonSexp;)Ljava/util/List;", "arity", "", "getArity", "(Lcom/amazon/ion/IonSexp;)I", "isNonNullText", "", "(Lcom/amazon/ion/IonValue;)Z", "isNumeric", "isText", "isUnsignedInteger", "lastIndex", "getLastIndex", "(Lcom/amazon/ion/IonValue;)I", "ordinal", "getOrdinal", "size", "getSize", "tagText", "", "getTagText", "(Lcom/amazon/ion/IonSexp;)Ljava/lang/String;", "err", "", "message", "IonValueUtils", "asIonInt", "Lcom/amazon/ion/IonInt;", "asIonSexp", "asIonStruct", "Lcom/amazon/ion/IonStruct;", "asIonSymbol", "Lcom/amazon/ion/IonSymbol;", "asSequence", "Lkotlin/sequences/Sequence;", "bigDecimalValue", "Ljava/math/BigDecimal;", "bigDecimalValueOrNull", "booleanValue", "booleanValueOrNull", "(Lcom/amazon/ion/IonValue;)Ljava/lang/Boolean;", "bytesValue", "", "bytesValueOrNull", "doubleValue", "", "doubleValueOrNull", "(Lcom/amazon/ion/IonValue;)Ljava/lang/Double;", "field", "nameOfField", "filterMetaNodes", "get", "index", "name", "isAstLiteral", "iterator", "", "javaValue", "", "longValue", "", "longValueOrNull", "(Lcom/amazon/ion/IonValue;)Ljava/lang/Long;", "numberValue", "numberValueOrNull", "seal", "singleArgWithTag", "tagName", "singleArgWithTagOrNull", "stringValue", "stringValueOrNull", "timestampValue", "Lcom/amazon/ion/Timestamp;", "timestampValueOrNull", "toListOfIonSexp", "", "lang"})
public final class IonValueExtensionsKt {
    @JvmName(name="IonValueUtils")
    private static final Void IonValueUtils(String message) {
        throw (Throwable)new IllegalArgumentException(message);
    }

    @NotNull
    public static final IonValue seal(@NotNull IonValue $this$seal) {
        Intrinsics.checkParameterIsNotNull($this$seal, "$this$seal");
        IonValue ionValue2 = $this$seal;
        boolean bl = false;
        boolean bl2 = false;
        IonValue $this$apply = ionValue2;
        boolean bl3 = false;
        $this$apply.makeReadOnly();
        return ionValue2;
    }

    @Nullable
    public static final IonValue get(@NotNull IonValue $this$get, @NotNull String name) {
        Intrinsics.checkParameterIsNotNull($this$get, "$this$get");
        Intrinsics.checkParameterIsNotNull(name, "name");
        IonValue ionValue2 = $this$get;
        if (!(ionValue2 instanceof IonStruct)) {
            Void void_ = IonValueExtensionsKt.IonValueUtils("Expected struct: " + $this$get);
            throw null;
        }
        return ((IonStruct)$this$get).get(name);
    }

    public static final int getSize(@NotNull IonValue $this$size) {
        Intrinsics.checkParameterIsNotNull($this$size, "$this$size");
        IonValue ionValue2 = $this$size;
        if (!(ionValue2 instanceof IonContainer)) {
            Void void_ = IonValueExtensionsKt.IonValueUtils("Expected container: " + $this$size);
            throw null;
        }
        return ((IonContainer)$this$size).size();
    }

    public static final int getLastIndex(@NotNull IonValue $this$lastIndex) {
        Intrinsics.checkParameterIsNotNull($this$lastIndex, "$this$lastIndex");
        IonValue ionValue2 = $this$lastIndex;
        if (!(ionValue2 instanceof IonSequence)) {
            Void void_ = IonValueExtensionsKt.IonValueUtils("Expected sequence " + $this$lastIndex);
            throw null;
        }
        return ((IonSequence)$this$lastIndex).size() - 1;
    }

    @NotNull
    public static final IonValue get(@NotNull IonValue $this$get, int index) {
        Intrinsics.checkParameterIsNotNull($this$get, "$this$get");
        IonValue ionValue2 = $this$get;
        if (!(ionValue2 instanceof IonSequence)) {
            Void void_ = IonValueExtensionsKt.IonValueUtils("Expected sequence: " + $this$get);
            throw null;
        }
        IonValue ionValue3 = ((IonSequence)$this$get).get(index);
        Intrinsics.checkExpressionValueIsNotNull(ionValue3, "get(index)");
        return ionValue3;
    }

    @NotNull
    public static final Iterator<IonValue> iterator(@NotNull IonValue $this$iterator) {
        Intrinsics.checkParameterIsNotNull($this$iterator, "$this$iterator");
        IonValue ionValue2 = $this$iterator;
        if (!(ionValue2 instanceof IonContainer)) {
            Void void_ = IonValueExtensionsKt.IonValueUtils("Expected container: " + $this$iterator);
            throw null;
        }
        Iterator<IonValue> iterator2 = ((IonContainer)$this$iterator).iterator();
        Intrinsics.checkExpressionValueIsNotNull(iterator2, "iterator()");
        return iterator2;
    }

    @NotNull
    public static final Sequence<IonValue> asSequence(@NotNull IonValue $this$asSequence) {
        Intrinsics.checkParameterIsNotNull($this$asSequence, "$this$asSequence");
        IonValue ionValue2 = $this$asSequence;
        if (!(ionValue2 instanceof IonContainer)) {
            Void void_ = IonValueExtensionsKt.IonValueUtils("Expected container: " + $this$asSequence);
            throw null;
        }
        boolean bl = false;
        return new Sequence<IonValue>($this$asSequence){
            final /* synthetic */ IonValue $this_asSequence$inlined;
            {
                this.$this_asSequence$inlined = ionValue2;
            }

            @NotNull
            public Iterator<IonValue> iterator() {
                boolean bl = false;
                return ((IonContainer)this.$this_asSequence$inlined).iterator();
            }
        };
    }

    /*
     * Enabled aggressive block sorting
     */
    private static final Number javaValue(@NotNull IonInt $this$javaValue) {
        Number number;
        IntegerSize integerSize = $this$javaValue.getIntegerSize();
        if (integerSize != null) {
            switch (IonValueExtensionsKt$WhenMappings.$EnumSwitchMapping$0[integerSize.ordinal()]) {
                case 1: {
                    BigInteger bigInteger = $this$javaValue.bigIntegerValue();
                    Intrinsics.checkExpressionValueIsNotNull(bigInteger, "bigIntegerValue()");
                    number = bigInteger;
                    return number;
                }
            }
        }
        number = $this$javaValue.longValue();
        return number;
    }

    @NotNull
    public static final Number numberValue(@NotNull IonValue $this$numberValue) {
        Number number;
        Intrinsics.checkParameterIsNotNull($this$numberValue, "$this$numberValue");
        if ($this$numberValue.isNullValue()) {
            Void void_ = IonValueExtensionsKt.IonValueUtils("Expected non-null number: " + $this$numberValue);
            throw null;
        }
        IonValue ionValue2 = $this$numberValue;
        if (ionValue2 instanceof IonInt) {
            number = IonValueExtensionsKt.javaValue((IonInt)$this$numberValue);
        } else if (ionValue2 instanceof IonFloat) {
            number = ((IonFloat)$this$numberValue).doubleValue();
        } else if (ionValue2 instanceof IonDecimal) {
            number = ((IonDecimal)$this$numberValue).decimalValue();
        } else {
            Void void_ = IonValueExtensionsKt.IonValueUtils("Expected number: " + $this$numberValue);
            throw null;
        }
        Intrinsics.checkExpressionValueIsNotNull(number, "when (this) {\n        is\u2026ted number: $this\")\n    }");
        return number;
    }

    public static final long longValue(@NotNull IonValue $this$longValue) {
        long l;
        Number number;
        Intrinsics.checkParameterIsNotNull($this$longValue, "$this$longValue");
        Number number2 = number = IonValueExtensionsKt.numberValue($this$longValue);
        if (number2 instanceof Integer) {
            l = number.longValue();
        } else if (number2 instanceof Long) {
            l = number.longValue();
        } else if (number2 instanceof BigInteger) {
            l = ((BigInteger)number).longValueExact();
        } else {
            Void void_ = IonValueExtensionsKt.IonValueUtils("Number is not a long: " + number);
            throw null;
        }
        return l;
    }

    public static final double doubleValue(@NotNull IonValue $this$doubleValue) {
        Intrinsics.checkParameterIsNotNull($this$doubleValue, "$this$doubleValue");
        if ($this$doubleValue.isNullValue()) {
            Void void_ = IonValueExtensionsKt.IonValueUtils("Expected non-null double: " + $this$doubleValue);
            throw null;
        }
        if (!($this$doubleValue instanceof IonFloat)) {
            Void void_ = IonValueExtensionsKt.IonValueUtils("Expected double: " + $this$doubleValue);
            throw null;
        }
        return ((IonFloat)$this$doubleValue).doubleValue();
    }

    @NotNull
    public static final BigDecimal bigDecimalValue(@NotNull IonValue $this$bigDecimalValue) {
        Intrinsics.checkParameterIsNotNull($this$bigDecimalValue, "$this$bigDecimalValue");
        if ($this$bigDecimalValue.isNullValue()) {
            Void void_ = IonValueExtensionsKt.IonValueUtils("Expected non-null decimal: " + $this$bigDecimalValue);
            throw null;
        }
        if (!($this$bigDecimalValue instanceof IonDecimal)) {
            Void void_ = IonValueExtensionsKt.IonValueUtils("Expected decimal: " + $this$bigDecimalValue);
            throw null;
        }
        Decimal decimal = ((IonDecimal)$this$bigDecimalValue).decimalValue();
        Intrinsics.checkExpressionValueIsNotNull(decimal, "decimalValue()");
        return decimal;
    }

    public static final boolean booleanValue(@NotNull IonValue $this$booleanValue) {
        Intrinsics.checkParameterIsNotNull($this$booleanValue, "$this$booleanValue");
        IonValue ionValue2 = $this$booleanValue;
        if (!(ionValue2 instanceof IonBool)) {
            Void void_ = IonValueExtensionsKt.IonValueUtils("Expected boolean: " + $this$booleanValue);
            throw null;
        }
        return ((IonBool)$this$booleanValue).booleanValue();
    }

    @NotNull
    public static final Timestamp timestampValue(@NotNull IonValue $this$timestampValue) {
        Intrinsics.checkParameterIsNotNull($this$timestampValue, "$this$timestampValue");
        IonValue ionValue2 = $this$timestampValue;
        if (!(ionValue2 instanceof IonTimestamp)) {
            Void void_ = IonValueExtensionsKt.IonValueUtils("Expected timestamp: " + $this$timestampValue);
            throw null;
        }
        Timestamp timestamp = ((IonTimestamp)$this$timestampValue).timestampValue();
        Intrinsics.checkExpressionValueIsNotNull(timestamp, "timestampValue()");
        return timestamp;
    }

    @Nullable
    public static final String stringValue(@NotNull IonValue $this$stringValue) {
        Intrinsics.checkParameterIsNotNull($this$stringValue, "$this$stringValue");
        IonValue ionValue2 = $this$stringValue;
        if (!(ionValue2 instanceof IonText)) {
            Void void_ = IonValueExtensionsKt.IonValueUtils("Expected text: " + $this$stringValue);
            throw null;
        }
        return ((IonText)$this$stringValue).stringValue();
    }

    @NotNull
    public static final byte[] bytesValue(@NotNull IonValue $this$bytesValue) {
        Intrinsics.checkParameterIsNotNull($this$bytesValue, "$this$bytesValue");
        IonValue ionValue2 = $this$bytesValue;
        if (!(ionValue2 instanceof IonLob)) {
            Void void_ = IonValueExtensionsKt.IonValueUtils("Expected LOB: " + $this$bytesValue);
            throw null;
        }
        byte[] byArray = ((IonLob)$this$bytesValue).getBytes();
        Intrinsics.checkExpressionValueIsNotNull(byArray, "bytes");
        return byArray;
    }

    @Nullable
    public static final Number numberValueOrNull(@NotNull IonValue $this$numberValueOrNull) {
        Intrinsics.checkParameterIsNotNull($this$numberValueOrNull, "$this$numberValueOrNull");
        IonValue ionValue2 = $this$numberValueOrNull;
        return ionValue2 instanceof IonInt ? (Number)IonValueExtensionsKt.javaValue((IonInt)$this$numberValueOrNull) : (Number)(ionValue2 instanceof IonFloat ? (Number)((IonFloat)$this$numberValueOrNull).doubleValue() : (Number)(ionValue2 instanceof IonDecimal ? (Number)((IonDecimal)$this$numberValueOrNull).decimalValue() : null));
    }

    @Nullable
    public static final Long longValueOrNull(@NotNull IonValue $this$longValueOrNull) {
        Number number;
        Intrinsics.checkParameterIsNotNull($this$longValueOrNull, "$this$longValueOrNull");
        Number number2 = number = IonValueExtensionsKt.numberValue($this$longValueOrNull);
        return number2 instanceof Integer ? Long.valueOf(number.longValue()) : (number2 instanceof Long ? (Long)number : (number2 instanceof BigInteger ? Long.valueOf(((BigInteger)number).longValueExact()) : null));
    }

    @Nullable
    public static final Double doubleValueOrNull(@NotNull IonValue $this$doubleValueOrNull) {
        Intrinsics.checkParameterIsNotNull($this$doubleValueOrNull, "$this$doubleValueOrNull");
        return $this$doubleValueOrNull instanceof IonFloat ? Double.valueOf(((IonFloat)$this$doubleValueOrNull).doubleValue()) : null;
    }

    @Nullable
    public static final BigDecimal bigDecimalValueOrNull(@NotNull IonValue $this$bigDecimalValueOrNull) {
        Intrinsics.checkParameterIsNotNull($this$bigDecimalValueOrNull, "$this$bigDecimalValueOrNull");
        return $this$bigDecimalValueOrNull instanceof IonDecimal ? ((IonDecimal)$this$bigDecimalValueOrNull).bigDecimalValue() : null;
    }

    @Nullable
    public static final Boolean booleanValueOrNull(@NotNull IonValue $this$booleanValueOrNull) {
        Intrinsics.checkParameterIsNotNull($this$booleanValueOrNull, "$this$booleanValueOrNull");
        IonValue ionValue2 = $this$booleanValueOrNull;
        return ionValue2 instanceof IonBool ? Boolean.valueOf(((IonBool)$this$booleanValueOrNull).booleanValue()) : null;
    }

    @Nullable
    public static final Timestamp timestampValueOrNull(@NotNull IonValue $this$timestampValueOrNull) {
        Intrinsics.checkParameterIsNotNull($this$timestampValueOrNull, "$this$timestampValueOrNull");
        IonValue ionValue2 = $this$timestampValueOrNull;
        return ionValue2 instanceof IonTimestamp ? ((IonTimestamp)$this$timestampValueOrNull).timestampValue() : null;
    }

    @Nullable
    public static final String stringValueOrNull(@NotNull IonValue $this$stringValueOrNull) {
        Intrinsics.checkParameterIsNotNull($this$stringValueOrNull, "$this$stringValueOrNull");
        IonValue ionValue2 = $this$stringValueOrNull;
        return ionValue2 instanceof IonText ? ((IonText)$this$stringValueOrNull).stringValue() : null;
    }

    @Nullable
    public static final byte[] bytesValueOrNull(@NotNull IonValue $this$bytesValueOrNull) {
        Intrinsics.checkParameterIsNotNull($this$bytesValueOrNull, "$this$bytesValueOrNull");
        IonValue ionValue2 = $this$bytesValueOrNull;
        return ionValue2 instanceof IonLob ? ((IonLob)$this$bytesValueOrNull).getBytes() : null;
    }

    public static final boolean isNumeric(@NotNull IonValue $this$isNumeric) {
        Intrinsics.checkParameterIsNotNull($this$isNumeric, "$this$isNumeric");
        IonValue ionValue2 = $this$isNumeric;
        return ionValue2 instanceof IonInt || ionValue2 instanceof IonFloat || ionValue2 instanceof IonDecimal;
    }

    public static final boolean isUnsignedInteger(@NotNull IonValue $this$isUnsignedInteger) {
        Intrinsics.checkParameterIsNotNull($this$isUnsignedInteger, "$this$isUnsignedInteger");
        IonValue ionValue2 = $this$isUnsignedInteger;
        return ionValue2 instanceof IonInt ? ((IonInt)$this$isUnsignedInteger).longValue() >= 0L : false;
    }

    public static final boolean isNonNullText(@NotNull IonValue $this$isNonNullText) {
        Intrinsics.checkParameterIsNotNull($this$isNonNullText, "$this$isNonNullText");
        IonValue ionValue2 = $this$isNonNullText;
        return ionValue2 instanceof IonText ? !((IonText)$this$isNonNullText).isNullValue() : false;
    }

    public static final int getOrdinal(@NotNull IonValue $this$ordinal) {
        Intrinsics.checkParameterIsNotNull($this$ordinal, "$this$ordinal");
        IonContainer ionContainer = $this$ordinal.getContainer();
        Intrinsics.checkExpressionValueIsNotNull(ionContainer, "container");
        return CollectionsKt.indexOf(ionContainer, $this$ordinal);
    }

    public static final boolean isText(@NotNull IonValue $this$isText) {
        Intrinsics.checkParameterIsNotNull($this$isText, "$this$isText");
        IonValue ionValue2 = $this$isText;
        return ionValue2 instanceof IonText;
    }

    @NotNull
    public static final IonValue filterMetaNodes(@NotNull IonSexp $this$filterMetaNodes) {
        Intrinsics.checkParameterIsNotNull($this$filterMetaNodes, "$this$filterMetaNodes");
        IonSexp target = $this$filterMetaNodes;
        while (true) {
            IonValue ionValue2 = target.get(0);
            Intrinsics.checkExpressionValueIsNotNull(ionValue2, "target[0]");
            if (!Intrinsics.areEqual(IonValueExtensionsKt.stringValue(ionValue2), "meta")) break;
            IonValue tmpTarget = target.get(1);
            if (!(tmpTarget instanceof IonSexp)) {
                IonValue ionValue3 = tmpTarget.clone();
                Intrinsics.checkExpressionValueIsNotNull(ionValue3, "tmpTarget.clone()");
                return ionValue3;
            }
            target = IonValueExtensionsKt.asIonSexp(tmpTarget);
        }
        IonSexp ionSexp = $this$filterMetaNodes.getSystem().newEmptySexp();
        boolean bl = false;
        boolean bl2 = false;
        IonSexp $this$apply = ionSexp;
        boolean bl3 = false;
        IonValue ionValue4 = target.get(0);
        Intrinsics.checkExpressionValueIsNotNull(ionValue4, "target[0]");
        boolean isLiteral = Intrinsics.areEqual(IonValueExtensionsKt.stringValue(ionValue4), "lit");
        for (IonValue child2 : target) {
            IonValue ionValue5;
            if (!isLiteral && child2 instanceof IonSexp) {
                ionValue5 = IonValueExtensionsKt.filterMetaNodes((IonSexp)child2);
            } else {
                IonValue ionValue6 = child2.clone();
                ionValue5 = ionValue6;
                Intrinsics.checkExpressionValueIsNotNull(ionValue6, "child.clone()");
            }
            $this$apply.add(ionValue5);
        }
        IonSexp ionSexp2 = ionSexp;
        Intrinsics.checkExpressionValueIsNotNull(ionSexp2, "system.newEmptySexp().ap\u2026        )\n        }\n    }");
        return ionSexp2;
    }

    /*
     * WARNING - void declaration
     */
    @NotNull
    public static final IonValue singleArgWithTag(@NotNull IonSexp $this$singleArgWithTag, @NotNull String tagName) {
        Object v0;
        block4: {
            void $this$mapTo$iv$iv;
            Intrinsics.checkParameterIsNotNull($this$singleArgWithTag, "$this$singleArgWithTag");
            Intrinsics.checkParameterIsNotNull(tagName, "tagName");
            Iterable $this$map$iv = IonValueExtensionsKt.getArgs($this$singleArgWithTag);
            boolean $i$f$map = false;
            Iterable iterable = $this$map$iv;
            Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
            boolean $i$f$mapTo = false;
            for (Object item$iv$iv : $this$mapTo$iv$iv) {
                void it;
                IonValue ionValue2 = (IonValue)item$iv$iv;
                Collection collection = destination$iv$iv;
                boolean bl = false;
                IonSexp ionSexp = IonValueExtensionsKt.asIonSexp((IonValue)it);
                collection.add(ionSexp);
            }
            Iterable $this$singleOrNull$iv = (List)destination$iv$iv;
            boolean $i$f$singleOrNull = false;
            Object single$iv = null;
            boolean found$iv = false;
            for (Object element$iv : $this$singleOrNull$iv) {
                IonSexp it = (IonSexp)element$iv;
                boolean bl = false;
                if (!Intrinsics.areEqual(IonValueExtensionsKt.getTagText(it), tagName)) continue;
                if (found$iv) {
                    v0 = null;
                    break block4;
                }
                single$iv = element$iv;
                found$iv = true;
            }
            v0 = !found$iv ? null : single$iv;
        }
        IonSexp ionSexp = v0;
        if (ionSexp == null) {
            Void void_ = IonValueExtensionsKt.IonValueUtils("Could not locate s-exp child with tag " + tagName);
            throw null;
        }
        return ionSexp;
    }

    /*
     * WARNING - void declaration
     */
    @Nullable
    public static final IonValue singleArgWithTagOrNull(@NotNull IonSexp $this$singleArgWithTagOrNull, @NotNull String tagName) {
        Object v0;
        block3: {
            void $this$mapTo$iv$iv;
            Intrinsics.checkParameterIsNotNull($this$singleArgWithTagOrNull, "$this$singleArgWithTagOrNull");
            Intrinsics.checkParameterIsNotNull(tagName, "tagName");
            Iterable $this$map$iv = IonValueExtensionsKt.getArgs($this$singleArgWithTagOrNull);
            boolean $i$f$map = false;
            Iterable iterable = $this$map$iv;
            Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
            boolean $i$f$mapTo = false;
            for (Object item$iv$iv : $this$mapTo$iv$iv) {
                void it;
                IonValue ionValue2 = (IonValue)item$iv$iv;
                Collection collection = destination$iv$iv;
                boolean bl = false;
                IonSexp ionSexp = IonValueExtensionsKt.asIonSexp((IonValue)it);
                collection.add(ionSexp);
            }
            Iterable $this$singleOrNull$iv = (List)destination$iv$iv;
            boolean $i$f$singleOrNull = false;
            Object single$iv = null;
            boolean found$iv = false;
            for (Object element$iv : $this$singleOrNull$iv) {
                IonSexp it = (IonSexp)element$iv;
                boolean bl = false;
                if (!Intrinsics.areEqual(IonValueExtensionsKt.getTagText(it), tagName)) continue;
                if (found$iv) {
                    v0 = null;
                    break block3;
                }
                single$iv = element$iv;
                found$iv = true;
            }
            v0 = !found$iv ? null : single$iv;
        }
        return v0;
    }

    /*
     * WARNING - void declaration
     */
    @NotNull
    public static final List<IonSexp> toListOfIonSexp(@NotNull Iterable<? extends IonValue> $this$toListOfIonSexp) {
        void $this$mapTo$iv$iv;
        Intrinsics.checkParameterIsNotNull($this$toListOfIonSexp, "$this$toListOfIonSexp");
        Iterable<? extends IonValue> $this$map$iv = $this$toListOfIonSexp;
        boolean $i$f$map = false;
        Iterable<? extends IonValue> iterable = $this$map$iv;
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
        boolean $i$f$mapTo = false;
        for (Object item$iv$iv : $this$mapTo$iv$iv) {
            void it;
            IonValue ionValue2 = (IonValue)item$iv$iv;
            Collection collection = destination$iv$iv;
            boolean bl = false;
            IonSexp ionSexp = IonValueExtensionsKt.asIonSexp((IonValue)it);
            collection.add(ionSexp);
        }
        return (List)destination$iv$iv;
    }

    @NotNull
    public static final IonInt asIonInt(@NotNull IonValue $this$asIonInt) {
        Intrinsics.checkParameterIsNotNull($this$asIonInt, "$this$asIonInt");
        IonValue ionValue2 = $this$asIonInt;
        if (!(ionValue2 instanceof IonInt)) {
            ionValue2 = null;
        }
        IonInt ionInt = (IonInt)ionValue2;
        if (ionInt == null) {
            Void void_ = IonValueExtensionsKt.IonValueUtils("Expected an IonInt but found " + (Object)((Object)$this$asIonInt.getType()) + '.');
            throw null;
        }
        return ionInt;
    }

    @NotNull
    public static final IonSexp asIonSexp(@NotNull IonValue $this$asIonSexp) {
        Intrinsics.checkParameterIsNotNull($this$asIonSexp, "$this$asIonSexp");
        IonValue ionValue2 = $this$asIonSexp;
        if (!(ionValue2 instanceof IonSexp)) {
            ionValue2 = null;
        }
        IonSexp ionSexp = (IonSexp)ionValue2;
        if (ionSexp == null) {
            Void void_ = IonValueExtensionsKt.IonValueUtils("Expected an IonSexp but found " + (Object)((Object)$this$asIonSexp.getType()) + '.');
            throw null;
        }
        return ionSexp;
    }

    @NotNull
    public static final IonStruct asIonStruct(@NotNull IonValue $this$asIonStruct) {
        Intrinsics.checkParameterIsNotNull($this$asIonStruct, "$this$asIonStruct");
        IonValue ionValue2 = $this$asIonStruct;
        if (!(ionValue2 instanceof IonStruct)) {
            ionValue2 = null;
        }
        IonStruct ionStruct = (IonStruct)ionValue2;
        if (ionStruct == null) {
            Void void_ = IonValueExtensionsKt.IonValueUtils("Expected an IonStruct but found " + (Object)((Object)$this$asIonStruct.getType()) + '.');
            throw null;
        }
        return ionStruct;
    }

    @NotNull
    public static final IonSymbol asIonSymbol(@NotNull IonValue $this$asIonSymbol) {
        Intrinsics.checkParameterIsNotNull($this$asIonSymbol, "$this$asIonSymbol");
        IonValue ionValue2 = $this$asIonSymbol;
        if (!(ionValue2 instanceof IonSymbol)) {
            ionValue2 = null;
        }
        IonSymbol ionSymbol = (IonSymbol)ionValue2;
        if (ionSymbol == null) {
            Void void_ = IonValueExtensionsKt.IonValueUtils("Expected an IonSymbol but found " + (Object)((Object)$this$asIonSymbol.getType()) + '.');
            throw null;
        }
        return ionSymbol;
    }

    @NotNull
    public static final IonValue field(@NotNull IonStruct $this$field, @NotNull String nameOfField) {
        Intrinsics.checkParameterIsNotNull($this$field, "$this$field");
        Intrinsics.checkParameterIsNotNull(nameOfField, "nameOfField");
        IonValue ionValue2 = $this$field.get(nameOfField);
        if (ionValue2 == null) {
            Void void_ = IonValueExtensionsKt.IonValueUtils("Expected struct field '" + nameOfField + "' was not present.");
            throw null;
        }
        return ionValue2;
    }

    @NotNull
    public static final String getTagText(@NotNull IonSexp $this$tagText) {
        Intrinsics.checkParameterIsNotNull($this$tagText, "$this$tagText");
        if ($this$tagText.isEmpty()) {
            Void void_ = IonValueExtensionsKt.IonValueUtils("IonSexp was empty");
            throw null;
        }
        IonValue ionValue2 = $this$tagText.get(0);
        if (!(ionValue2 instanceof IonSymbol)) {
            ionValue2 = null;
        }
        IonSymbol ionSymbol = (IonSymbol)ionValue2;
        if (ionSymbol == null) {
            Void void_ = IonValueExtensionsKt.IonValueUtils("First element of IonSexp was not a symbol");
            throw null;
        }
        IonSymbol tag = ionSymbol;
        String string = tag.stringValue();
        Intrinsics.checkExpressionValueIsNotNull(string, "tag.stringValue()");
        return string;
    }

    @NotNull
    public static final List<IonValue> getArgs(@NotNull IonSexp $this$args) {
        Intrinsics.checkParameterIsNotNull($this$args, "$this$args");
        return CollectionsKt.drop($this$args, 1);
    }

    public static final int getArity(@NotNull IonSexp $this$arity) {
        Intrinsics.checkParameterIsNotNull($this$arity, "$this$arity");
        return $this$arity.size() - 1;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static final boolean isAstLiteral(@NotNull IonValue $this$isAstLiteral) {
        Intrinsics.checkParameterIsNotNull($this$isAstLiteral, "$this$isAstLiteral");
        if (!($this$isAstLiteral instanceof IonSexp)) return false;
        IonValue ionValue2 = ((IonSexp)$this$isAstLiteral).get(0);
        Intrinsics.checkExpressionValueIsNotNull(ionValue2, "this[0]");
        if (!Intrinsics.areEqual(IonValueExtensionsKt.stringValue(ionValue2), "lit")) return false;
        return true;
    }
}

