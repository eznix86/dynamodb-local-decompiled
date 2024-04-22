/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package org.partiql.lang.util;

import com.amazon.ion.Decimal;
import com.amazon.ion.IonDecimal;
import com.amazon.ion.IonFloat;
import com.amazon.ion.IonInt;
import com.amazon.ion.IonSystem;
import com.amazon.ion.IonValue;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.TypeCastException;
import kotlin.collections.MapsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;
import org.partiql.lang.eval.ExceptionsKt;
import org.partiql.lang.util.NumberExtensionsKt;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=2, d1={"\u0000\\\n\u0000\n\u0002\u0010$\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\u0010\u0004\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\t\n\u0002\b\u0007\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\u001a\u001a\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00042\b\b\u0002\u0010\u0011\u001a\u00020\bH\u0000\u001a\u001a\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0012\u001a\u00020\u00132\b\b\u0002\u0010\u0011\u001a\u00020\bH\u0000\u001a\"\u0010\u0014\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00040\u00152\u0006\u0010\u0016\u001a\u00020\u00042\u0006\u0010\u0017\u001a\u00020\u0004\u001a\u0014\u0010\u0018\u001a\u00020\u0004*\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u0019H\u0002\u001a\u0014\u0010\u001b\u001a\u00020\u0004*\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u0019H\u0002\u001a\u0014\u0010\u001c\u001a\u00020\u0004*\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u0019H\u0002\u001a\u0014\u0010\u001d\u001a\u00020\u0004*\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u0019H\u0002\u001a\u001a\u0010\u001e\u001a\u00020\u0004*\u00020\u00042\u000e\u0010\u001f\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00040\u0003\u001a\u0015\u0010 \u001a\u00020!*\u00020\u00042\u0006\u0010\u001a\u001a\u00020\u0004H\u0086\u0002\u001a\u0015\u0010\"\u001a\u00020\u0004*\u00020\u00042\u0006\u0010\u001a\u001a\u00020\u0004H\u0086\u0002\u001a\u0012\u0010#\u001a\u00020$*\u00020\u00042\u0006\u0010%\u001a\u00020&\u001a\f\u0010'\u001a\u00020\n*\u00020\u0004H\u0000\u001a\u0015\u0010(\u001a\u00020\u0004*\u00020\u00042\u0006\u0010\u001a\u001a\u00020\u0004H\u0086\u0002\u001a\u0015\u0010)\u001a\u00020\u0004*\u00020\u00042\u0006\u0010\u001a\u001a\u00020\u0004H\u0086\u0002\u001a\u0015\u0010*\u001a\u00020\u0004*\u00020\u00042\u0006\u0010\u001a\u001a\u00020\u0004H\u0086\u0002\u001a\u0015\u0010+\u001a\u00020\u0004*\u00020\u00042\u0006\u0010\u001a\u001a\u00020\u0004H\u0086\u0002\u001a\r\u0010,\u001a\u00020\u0004*\u00020\u0004H\u0086\u0002\",\u0010\u0000\u001a \u0012\u000e\u0012\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00030\u0002\u0012\f\u0012\n\u0012\u0006\b\u0001\u0012\u00020\u00040\u00030\u0001X\u0082\u0004\u00a2\u0006\u0002\n\u0000\"*\u0010\u0005\u001a\u001e\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u0003\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00040\u00060\u0001X\u0082\u0004\u00a2\u0006\u0002\n\u0000\"\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000\"\u0015\u0010\t\u001a\u00020\n*\u00020\u00048F\u00a2\u0006\u0006\u001a\u0004\b\t\u0010\u000b\"\u0015\u0010\f\u001a\u00020\n*\u00020\u00048F\u00a2\u0006\u0006\u001a\u0004\b\f\u0010\u000b\"\u0015\u0010\r\u001a\u00020\n*\u00020\u00048F\u00a2\u0006\u0006\u001a\u0004\b\r\u0010\u000b\u00a8\u0006-"}, d2={"CONVERSION_MAP", "", "", "Ljava/lang/Class;", "", "CONVERTERS", "Lkotlin/Function1;", "MATH_CONTEXT", "Ljava/math/MathContext;", "isNaN", "", "(Ljava/lang/Number;)Z", "isNegInf", "isPosInf", "bigDecimalOf", "Ljava/math/BigDecimal;", "num", "mc", "text", "", "coerceNumbers", "Lkotlin/Pair;", "first", "second", "checkOverflowDivision", "", "other", "checkOverflowMinus", "checkOverflowPlus", "checkOverflowTimes", "coerce", "type", "compareTo", "", "div", "ionValue", "Lcom/amazon/ion/IonValue;", "ion", "Lcom/amazon/ion/IonSystem;", "isZero", "minus", "plus", "rem", "times", "unaryMinus", "lang"})
public final class NumberExtensionsKt {
    private static final MathContext MATH_CONTEXT = new MathContext(38, RoundingMode.HALF_EVEN);
    private static final Map<Set<Class<?>>, Class<? extends Number>> CONVERSION_MAP = MapsKt.mapOf(TuplesKt.to(SetsKt.setOf(Long.class, Long.class), Long.class), TuplesKt.to(SetsKt.setOf(Long.class, Double.class), Double.class), TuplesKt.to(SetsKt.setOf(Long.class, BigDecimal.class), BigDecimal.class), TuplesKt.to(SetsKt.setOf(Double.class, Double.class), Double.class), TuplesKt.to(SetsKt.setOf(Double.class, BigDecimal.class), BigDecimal.class), TuplesKt.to(SetsKt.setOf(BigDecimal.class, BigDecimal.class), BigDecimal.class));
    private static final Map<Class<?>, Function1<Number, Number>> CONVERTERS = MapsKt.mapOf(TuplesKt.to(Long.class, CONVERTERS.1.INSTANCE), TuplesKt.to(Double.class, CONVERTERS.2.INSTANCE), TuplesKt.to(BigDecimal.class, CONVERTERS.3.INSTANCE));

    @NotNull
    public static final BigDecimal bigDecimalOf(@NotNull Number num, @NotNull MathContext mc) {
        BigDecimal bigDecimal;
        Intrinsics.checkParameterIsNotNull(num, "num");
        Intrinsics.checkParameterIsNotNull(mc, "mc");
        Number number = num;
        if (number instanceof Decimal) {
            bigDecimal = (BigDecimal)num;
        } else if (number instanceof Integer) {
            bigDecimal = new BigDecimal(num.intValue(), mc);
        } else if (number instanceof Long) {
            bigDecimal = new BigDecimal(num.longValue(), mc);
        } else if (number instanceof Double) {
            bigDecimal = new BigDecimal(num.doubleValue(), mc);
        } else if (number instanceof BigDecimal) {
            bigDecimal = (BigDecimal)num;
        } else {
            throw (Throwable)new IllegalArgumentException("Unsupported number type: " + num + ", " + num.getClass());
        }
        return bigDecimal;
    }

    public static /* synthetic */ BigDecimal bigDecimalOf$default(Number number, MathContext mathContext, int n, Object object) {
        if ((n & 2) != 0) {
            mathContext = MATH_CONTEXT;
        }
        return NumberExtensionsKt.bigDecimalOf(number, mathContext);
    }

    @NotNull
    public static final BigDecimal bigDecimalOf(@NotNull String text, @NotNull MathContext mc) {
        Intrinsics.checkParameterIsNotNull(text, "text");
        Intrinsics.checkParameterIsNotNull(mc, "mc");
        String string = text;
        boolean bl = false;
        String string2 = ((Object)StringsKt.trim((CharSequence)string)).toString();
        MathContext mathContext = mc;
        String string3 = string2;
        return new BigDecimal(string3, mathContext);
    }

    public static /* synthetic */ BigDecimal bigDecimalOf$default(String string, MathContext mathContext, int n, Object object) {
        if ((n & 2) != 0) {
            mathContext = MATH_CONTEXT;
        }
        return NumberExtensionsKt.bigDecimalOf(string, mathContext);
    }

    public static final boolean isZero(@NotNull Number $this$isZero) {
        boolean bl;
        Intrinsics.checkParameterIsNotNull($this$isZero, "$this$isZero");
        Number number = $this$isZero;
        if (number instanceof Long) {
            bl = Intrinsics.areEqual($this$isZero, (Object)0L);
        } else if (number instanceof Double) {
            bl = Intrinsics.areEqual($this$isZero, (Object)0.0) || Intrinsics.areEqual($this$isZero, (Object)-0.0);
        } else if (number instanceof BigDecimal) {
            bl = BigDecimal.ZERO.compareTo((BigDecimal)$this$isZero) == 0;
        } else {
            throw (Throwable)new IllegalStateException();
        }
        return bl;
    }

    @NotNull
    public static final Number coerce(@NotNull Number $this$coerce, @NotNull Class<? extends Number> type) {
        Intrinsics.checkParameterIsNotNull($this$coerce, "$this$coerce");
        Intrinsics.checkParameterIsNotNull(type, "type");
        Function1<Number, Number> function1 = CONVERTERS.get(type);
        if (function1 == null) {
            throw (Throwable)new IllegalArgumentException("No converter for " + type);
        }
        Function1<Number, Number> conv = function1;
        return conv.invoke($this$coerce);
    }

    @NotNull
    public static final Pair<Number, Number> coerceNumbers(@NotNull Number first, @NotNull Number second) {
        Intrinsics.checkParameterIsNotNull(first, "first");
        Intrinsics.checkParameterIsNotNull(second, "second");
        coerceNumbers.1 $fun$typeFor$1 = coerceNumbers.1.INSTANCE;
        Class<? extends Number> clazz = CONVERSION_MAP.get(SetsKt.setOf($fun$typeFor$1.invoke(first), $fun$typeFor$1.invoke(second)));
        if (clazz == null) {
            throw (Throwable)new IllegalArgumentException("No coercion support for " + TuplesKt.to(first, second));
        }
        Class<? extends Number> type = clazz;
        return new Pair<Number, Number>(NumberExtensionsKt.coerce(first, type), NumberExtensionsKt.coerce(second, type));
    }

    @NotNull
    public static final IonValue ionValue(@NotNull Number $this$ionValue, @NotNull IonSystem ion) {
        IonValue ionValue2;
        Intrinsics.checkParameterIsNotNull($this$ionValue, "$this$ionValue");
        Intrinsics.checkParameterIsNotNull(ion, "ion");
        Number number = $this$ionValue;
        if (number instanceof Long) {
            IonInt ionInt = ion.newInt($this$ionValue.longValue());
            Intrinsics.checkExpressionValueIsNotNull(ionInt, "ion.newInt(this)");
            ionValue2 = ionInt;
        } else if (number instanceof BigInteger) {
            IonInt ionInt = ion.newInt($this$ionValue);
            Intrinsics.checkExpressionValueIsNotNull(ionInt, "ion.newInt(this)");
            ionValue2 = ionInt;
        } else if (number instanceof Double) {
            IonFloat ionFloat = ion.newFloat($this$ionValue.doubleValue());
            Intrinsics.checkExpressionValueIsNotNull(ionFloat, "ion.newFloat(this)");
            ionValue2 = ionFloat;
        } else if (number instanceof BigDecimal) {
            IonDecimal ionDecimal = ion.newDecimal((BigDecimal)$this$ionValue);
            Intrinsics.checkExpressionValueIsNotNull(ionDecimal, "ion.newDecimal(this)");
            ionValue2 = ionDecimal;
        } else {
            throw (Throwable)new IllegalArgumentException("Cannot convert to IonValue: " + $this$ionValue);
        }
        return ionValue2;
    }

    @NotNull
    public static final Number unaryMinus(@NotNull Number $this$unaryMinus) {
        Number number;
        Intrinsics.checkParameterIsNotNull($this$unaryMinus, "$this$unaryMinus");
        Number number2 = $this$unaryMinus;
        if (number2 instanceof Long) {
            number = -$this$unaryMinus.longValue();
        } else if (number2 instanceof BigInteger) {
            BigInteger bigInteger = ((BigInteger)$this$unaryMinus).negate();
            Intrinsics.checkExpressionValueIsNotNull(bigInteger, "this.negate()");
            number = bigInteger;
        } else if (number2 instanceof Double) {
            number = -$this$unaryMinus.doubleValue();
        } else if (number2 instanceof BigDecimal) {
            BigDecimal bigDecimal = NumberExtensionsKt.isZero($this$unaryMinus) ? (BigDecimal)Decimal.negativeZero(((BigDecimal)$this$unaryMinus).scale()) : ((BigDecimal)$this$unaryMinus).negate();
            Intrinsics.checkExpressionValueIsNotNull(bigDecimal, "if(this.isZero()) {\n    \u2026is.negate()\n            }");
            number = bigDecimal;
        } else {
            throw (Throwable)new IllegalStateException();
        }
        return number;
    }

    private static final Number checkOverflowPlus(long $this$checkOverflowPlus, long other) {
        long result;
        boolean overflows = ($this$checkOverflowPlus ^ other) >= 0L & ($this$checkOverflowPlus ^ (result = $this$checkOverflowPlus + other)) < 0L;
        boolean bl = overflows;
        if (bl) {
            Void void_ = ExceptionsKt.errIntOverflow$default(null, 1, null);
            throw null;
        }
        return result;
    }

    private static final Number checkOverflowMinus(long $this$checkOverflowMinus, long other) {
        long result;
        boolean overflows = ($this$checkOverflowMinus ^ other) < 0L & ($this$checkOverflowMinus ^ (result = $this$checkOverflowMinus - other)) < 0L;
        boolean bl = overflows;
        if (bl) {
            Void void_ = ExceptionsKt.errIntOverflow$default(null, 1, null);
            throw null;
        }
        return result;
    }

    private static final Number checkOverflowTimes(long $this$checkOverflowTimes, long other) {
        checkOverflowTimes.1 $fun$numberOfLeadingZeros$1 = checkOverflowTimes.1.INSTANCE;
        int leadingZeros = $fun$numberOfLeadingZeros$1.invoke($this$checkOverflowTimes) + $fun$numberOfLeadingZeros$1.invoke($this$checkOverflowTimes ^ 0xFFFFFFFFFFFFFFFFL) + $fun$numberOfLeadingZeros$1.invoke(other) + $fun$numberOfLeadingZeros$1.invoke(other ^ 0xFFFFFFFFFFFFFFFFL);
        long result = $this$checkOverflowTimes * other;
        int longSize = 64;
        if (leadingZeros >= longSize && $this$checkOverflowTimes >= 0L | other != Long.MIN_VALUE && ($this$checkOverflowTimes == 0L || result / $this$checkOverflowTimes == other)) {
            return result;
        }
        Void void_ = ExceptionsKt.errIntOverflow$default(null, 1, null);
        throw null;
    }

    private static final Number checkOverflowDivision(long $this$checkOverflowDivision, long other) {
        if ($this$checkOverflowDivision == Long.MIN_VALUE && other == -1L) {
            Void void_ = ExceptionsKt.errIntOverflow$default(null, 1, null);
            throw null;
        }
        return $this$checkOverflowDivision / other;
    }

    /*
     * WARNING - void declaration
     */
    @NotNull
    public static final Number plus(@NotNull Number $this$plus, @NotNull Number other) {
        Number number;
        void first;
        Intrinsics.checkParameterIsNotNull($this$plus, "$this$plus");
        Intrinsics.checkParameterIsNotNull(other, "other");
        Pair<Number, Number> pair = NumberExtensionsKt.coerceNumbers($this$plus, other);
        Number number2 = pair.component1();
        Number second = pair.component2();
        pair = first;
        if (pair instanceof Long) {
            Number number3 = second;
            if (number3 == null) {
                throw new TypeCastException("null cannot be cast to non-null type kotlin.Long");
            }
            number = NumberExtensionsKt.checkOverflowPlus(first.longValue(), (Long)number3);
        } else if (pair instanceof Double) {
            Number number4 = second;
            if (number4 == null) {
                throw new TypeCastException("null cannot be cast to non-null type kotlin.Double");
            }
            number = first.doubleValue() + (Double)number4;
        } else if (pair instanceof BigDecimal) {
            Number number5 = second;
            if (number5 == null) {
                throw new TypeCastException("null cannot be cast to non-null type java.math.BigDecimal");
            }
            BigDecimal bigDecimal = ((BigDecimal)first).add((BigDecimal)number5, MATH_CONTEXT);
            Intrinsics.checkExpressionValueIsNotNull(bigDecimal, "first.add(second as BigDecimal, MATH_CONTEXT)");
            number = bigDecimal;
        } else {
            throw (Throwable)new IllegalStateException();
        }
        return number;
    }

    /*
     * WARNING - void declaration
     */
    @NotNull
    public static final Number minus(@NotNull Number $this$minus, @NotNull Number other) {
        Number number;
        void first;
        Intrinsics.checkParameterIsNotNull($this$minus, "$this$minus");
        Intrinsics.checkParameterIsNotNull(other, "other");
        Pair<Number, Number> pair = NumberExtensionsKt.coerceNumbers($this$minus, other);
        Number number2 = pair.component1();
        Number second = pair.component2();
        pair = first;
        if (pair instanceof Long) {
            Number number3 = second;
            if (number3 == null) {
                throw new TypeCastException("null cannot be cast to non-null type kotlin.Long");
            }
            number = NumberExtensionsKt.checkOverflowMinus(first.longValue(), (Long)number3);
        } else if (pair instanceof Double) {
            Number number4 = second;
            if (number4 == null) {
                throw new TypeCastException("null cannot be cast to non-null type kotlin.Double");
            }
            number = first.doubleValue() - (Double)number4;
        } else if (pair instanceof BigDecimal) {
            Number number5 = second;
            if (number5 == null) {
                throw new TypeCastException("null cannot be cast to non-null type java.math.BigDecimal");
            }
            BigDecimal bigDecimal = ((BigDecimal)first).subtract((BigDecimal)number5, MATH_CONTEXT);
            Intrinsics.checkExpressionValueIsNotNull(bigDecimal, "first.subtract(second as BigDecimal, MATH_CONTEXT)");
            number = bigDecimal;
        } else {
            throw (Throwable)new IllegalStateException();
        }
        return number;
    }

    /*
     * WARNING - void declaration
     */
    @NotNull
    public static final Number times(@NotNull Number $this$times, @NotNull Number other) {
        Number number;
        void first;
        Intrinsics.checkParameterIsNotNull($this$times, "$this$times");
        Intrinsics.checkParameterIsNotNull(other, "other");
        Pair<Number, Number> pair = NumberExtensionsKt.coerceNumbers($this$times, other);
        Number number2 = pair.component1();
        Number second = pair.component2();
        pair = first;
        if (pair instanceof Long) {
            Number number3 = second;
            if (number3 == null) {
                throw new TypeCastException("null cannot be cast to non-null type kotlin.Long");
            }
            number = NumberExtensionsKt.checkOverflowTimes(first.longValue(), (Long)number3);
        } else if (pair instanceof Double) {
            Number number4 = second;
            if (number4 == null) {
                throw new TypeCastException("null cannot be cast to non-null type kotlin.Double");
            }
            number = first.doubleValue() * (Double)number4;
        } else if (pair instanceof BigDecimal) {
            Number number5 = second;
            if (number5 == null) {
                throw new TypeCastException("null cannot be cast to non-null type java.math.BigDecimal");
            }
            BigDecimal bigDecimal = ((BigDecimal)first).multiply((BigDecimal)number5, MATH_CONTEXT);
            Intrinsics.checkExpressionValueIsNotNull(bigDecimal, "first.multiply(second as BigDecimal, MATH_CONTEXT)");
            number = bigDecimal;
        } else {
            throw (Throwable)new IllegalStateException();
        }
        return number;
    }

    /*
     * WARNING - void declaration
     */
    @NotNull
    public static final Number div(@NotNull Number $this$div, @NotNull Number other) {
        Number number;
        void first;
        Intrinsics.checkParameterIsNotNull($this$div, "$this$div");
        Intrinsics.checkParameterIsNotNull(other, "other");
        Pair<Number, Number> pair = NumberExtensionsKt.coerceNumbers($this$div, other);
        Number number2 = pair.component1();
        Number second = pair.component2();
        pair = first;
        if (pair instanceof Long) {
            Number number3 = second;
            if (number3 == null) {
                throw new TypeCastException("null cannot be cast to non-null type kotlin.Long");
            }
            number = NumberExtensionsKt.checkOverflowDivision(first.longValue(), (Long)number3);
        } else if (pair instanceof Double) {
            Number number4 = second;
            if (number4 == null) {
                throw new TypeCastException("null cannot be cast to non-null type kotlin.Double");
            }
            number = first.doubleValue() / (Double)number4;
        } else if (pair instanceof BigDecimal) {
            Number number5 = second;
            if (number5 == null) {
                throw new TypeCastException("null cannot be cast to non-null type java.math.BigDecimal");
            }
            BigDecimal bigDecimal = ((BigDecimal)first).divide((BigDecimal)number5, MATH_CONTEXT);
            Intrinsics.checkExpressionValueIsNotNull(bigDecimal, "first.divide(second as BigDecimal, MATH_CONTEXT)");
            number = bigDecimal;
        } else {
            throw (Throwable)new IllegalStateException();
        }
        return number;
    }

    /*
     * WARNING - void declaration
     */
    @NotNull
    public static final Number rem(@NotNull Number $this$rem, @NotNull Number other) {
        Number number;
        void first;
        Intrinsics.checkParameterIsNotNull($this$rem, "$this$rem");
        Intrinsics.checkParameterIsNotNull(other, "other");
        Pair<Number, Number> pair = NumberExtensionsKt.coerceNumbers($this$rem, other);
        Number number2 = pair.component1();
        Number second = pair.component2();
        pair = first;
        if (pair instanceof Long) {
            Number number3 = second;
            if (number3 == null) {
                throw new TypeCastException("null cannot be cast to non-null type kotlin.Long");
            }
            number = first.longValue() % (Long)number3;
        } else if (pair instanceof Double) {
            Number number4 = second;
            if (number4 == null) {
                throw new TypeCastException("null cannot be cast to non-null type kotlin.Double");
            }
            number = first.doubleValue() % (Double)number4;
        } else if (pair instanceof BigDecimal) {
            Number number5 = second;
            if (number5 == null) {
                throw new TypeCastException("null cannot be cast to non-null type java.math.BigDecimal");
            }
            BigDecimal bigDecimal = ((BigDecimal)first).remainder((BigDecimal)number5, MATH_CONTEXT);
            Intrinsics.checkExpressionValueIsNotNull(bigDecimal, "first.remainder(second a\u2026BigDecimal, MATH_CONTEXT)");
            number = bigDecimal;
        } else {
            throw (Throwable)new IllegalStateException();
        }
        return number;
    }

    /*
     * WARNING - void declaration
     */
    public static final int compareTo(@NotNull Number $this$compareTo, @NotNull Number other) {
        long l;
        void first;
        Intrinsics.checkParameterIsNotNull($this$compareTo, "$this$compareTo");
        Intrinsics.checkParameterIsNotNull(other, "other");
        Pair<Number, Number> pair = NumberExtensionsKt.coerceNumbers($this$compareTo, other);
        Number number = pair.component1();
        Number second = pair.component2();
        pair = first;
        if (pair instanceof Long) {
            Number number2 = second;
            if (number2 == null) {
                throw new TypeCastException("null cannot be cast to non-null type kotlin.Long");
            }
            long l2 = first.longValue() - (Long)number2;
            l = l2 == 0L ? 0 : (l2 < 0L ? -1 : 1);
        } else if (pair instanceof Double) {
            Number number3 = second;
            if (number3 == null) {
                throw new TypeCastException("null cannot be cast to non-null type kotlin.Double");
            }
            l = Double.compare(first.doubleValue(), (Double)number3);
        } else if (pair instanceof BigDecimal) {
            Number number4 = second;
            if (number4 == null) {
                throw new TypeCastException("null cannot be cast to non-null type java.math.BigDecimal");
            }
            l = ((BigDecimal)first).compareTo((BigDecimal)number4);
        } else {
            throw (Throwable)new IllegalStateException();
        }
        return (int)l;
    }

    public static final boolean isNaN(@NotNull Number $this$isNaN) {
        boolean bl;
        Intrinsics.checkParameterIsNotNull($this$isNaN, "$this$isNaN");
        Number number = $this$isNaN;
        if (number instanceof Double) {
            double d = $this$isNaN.doubleValue();
            boolean bl2 = false;
            bl = Double.isNaN(d);
        } else {
            bl = false;
        }
        return bl;
    }

    public static final boolean isNegInf(@NotNull Number $this$isNegInf) {
        boolean bl;
        Intrinsics.checkParameterIsNotNull($this$isNegInf, "$this$isNegInf");
        Number number = $this$isNegInf;
        if (number instanceof Double) {
            double d = $this$isNegInf.doubleValue();
            boolean bl2 = false;
            bl = Double.isInfinite(d) && $this$isNegInf.doubleValue() < 0.0;
        } else {
            bl = false;
        }
        return bl;
    }

    public static final boolean isPosInf(@NotNull Number $this$isPosInf) {
        boolean bl;
        Intrinsics.checkParameterIsNotNull($this$isPosInf, "$this$isPosInf");
        Number number = $this$isPosInf;
        if (number instanceof Double) {
            double d = $this$isPosInf.doubleValue();
            boolean bl2 = false;
            bl = Double.isInfinite(d) && $this$isPosInf.doubleValue() > 0.0;
        } else {
            bl = false;
        }
        return bl;
    }
}

