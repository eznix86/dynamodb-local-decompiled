/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package org.partiql.lang.eval;

import com.amazon.ion.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.SequencesKt;
import org.jetbrains.annotations.NotNull;
import org.partiql.lang.eval.ExceptionsKt;
import org.partiql.lang.eval.ExprValue;
import org.partiql.lang.eval.ExprValueExtensionsKt;
import org.partiql.lang.eval.ExprValueType;
import org.partiql.lang.eval.NaturalExprValueComparators;
import org.partiql.lang.eval.time.Time;
import org.partiql.lang.util.NumberExtensionsKt;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000U\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010\u001c\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006*\u0001\t\b\u0086\u0001\u0018\u0000 $2\b\u0012\u0004\u0012\u00020\u00000\u00012\u0012\u0012\u0004\u0012\u00020\u00030\u0002j\b\u0012\u0004\u0012\u00020\u0003`\u0004:\u0002$%B\u000f\b\u0002\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u00a2\u0006\u0002\u0010\u0007J\u0018\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u00032\u0006\u0010\u000e\u001a\u00020\u0003H\u0016JB\u0010\u000f\u001a\u00020\f\"\u0004\b\u0000\u0010\u00102\f\u0010\r\u001a\b\u0012\u0004\u0012\u0002H\u00100\u00112\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u0002H\u00100\u00112\u0016\u0010\u0012\u001a\u0012\u0012\u0004\u0012\u0002H\u00100\u0002j\b\u0012\u0004\u0012\u0002H\u0010`\u0004H\u0002JB\u0010\u0013\u001a\u00020\f\"\u0004\b\u0000\u0010\u00102\f\u0010\r\u001a\b\u0012\u0004\u0012\u0002H\u00100\u00112\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u0002H\u00100\u00112\u0016\u0010\u0014\u001a\u0012\u0012\u0004\u0012\u0002H\u00100\u0002j\b\u0012\u0004\u0012\u0002H\u0010`\u0004H\u0002J.\u0010\u0015\u001a\u0004\u0018\u00010\f2\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00172\f\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\f0\u001aH\u0082\b\u00a2\u0006\u0002\u0010\u001bJ,\u0010\u001c\u001a\u00020\u001d2\b\u0010\u001e\u001a\u0004\u0018\u00010\f2\u0012\u0010\u001f\u001a\u000e\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\u001d0 H\u0082\b\u00a2\u0006\u0002\u0010!R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010\b\u001a\u00020\tX\u0082\u0004\u00a2\u0006\u0004\n\u0002\u0010\nj\u0002\b\"j\u0002\b#\u00a8\u0006&"}, d2={"Lorg/partiql/lang/eval/NaturalExprValueComparators;", "", "Ljava/util/Comparator;", "Lorg/partiql/lang/eval/ExprValue;", "Lkotlin/Comparator;", "nullOrder", "Lorg/partiql/lang/eval/NaturalExprValueComparators$NullOrder;", "(Ljava/lang/String;ILorg/partiql/lang/eval/NaturalExprValueComparators$NullOrder;)V", "structFieldComparator", "org/partiql/lang/eval/NaturalExprValueComparators$structFieldComparator$1", "Lorg/partiql/lang/eval/NaturalExprValueComparators$structFieldComparator$1;", "compare", "", "left", "right", "compareOrdered", "T", "", "comparator", "compareUnordered", "entityCmp", "handle", "leftTypeCond", "", "rightTypeCond", "sameTypeHandler", "Lkotlin/Function0;", "(ZZLkotlin/jvm/functions/Function0;)Ljava/lang/Integer;", "ifCompared", "", "value", "handler", "Lkotlin/Function1;", "(Ljava/lang/Integer;Lkotlin/jvm/functions/Function1;)V", "NULLS_FIRST", "NULLS_LAST", "Companion", "NullOrder", "lang"})
public final class NaturalExprValueComparators
extends Enum<NaturalExprValueComparators>
implements Comparator<ExprValue> {
    public static final /* enum */ NaturalExprValueComparators NULLS_FIRST;
    public static final /* enum */ NaturalExprValueComparators NULLS_LAST;
    private static final /* synthetic */ NaturalExprValueComparators[] $VALUES;
    private final structFieldComparator.1 structFieldComparator;
    private final NullOrder nullOrder;
    private static final int EQUAL = 0;
    private static final int LESS = -1;
    private static final int MORE = 1;
    public static final Companion Companion;

    static {
        NaturalExprValueComparators[] naturalExprValueComparatorsArray = new NaturalExprValueComparators[2];
        NaturalExprValueComparators[] naturalExprValueComparatorsArray2 = naturalExprValueComparatorsArray;
        naturalExprValueComparatorsArray[0] = NULLS_FIRST = new NaturalExprValueComparators(NullOrder.FIRST);
        naturalExprValueComparatorsArray[1] = NULLS_LAST = new NaturalExprValueComparators(NullOrder.LAST);
        $VALUES = naturalExprValueComparatorsArray;
        Companion = new Companion(null);
    }

    private final Integer handle(boolean leftTypeCond, boolean rightTypeCond, Function0<Integer> sameTypeHandler) {
        int $i$f$handle = 0;
        return leftTypeCond && rightTypeCond ? sameTypeHandler.invoke() : (leftTypeCond ? Integer.valueOf(-1) : (rightTypeCond ? Integer.valueOf(1) : null));
    }

    private final void ifCompared(Integer value, Function1<? super Integer, Unit> handler) {
        int $i$f$ifCompared = 0;
        if (value != null) {
            handler.invoke(value);
        }
    }

    private final <T> int compareOrdered(Iterable<? extends T> left, Iterable<? extends T> right, Comparator<T> comparator) {
        Iterator<T> lIter = left.iterator();
        Iterator<T> rIter = right.iterator();
        while (lIter.hasNext() && rIter.hasNext()) {
            T rChild;
            T lChild = lIter.next();
            int cmp = comparator.compare(lChild, rChild = rIter.next());
            if (cmp == 0) continue;
            return cmp;
        }
        return lIter.hasNext() ? 1 : (rIter.hasNext() ? -1 : 0);
    }

    private final <T> int compareUnordered(Iterable<? extends T> left, Iterable<? extends T> right, Comparator<T> entityCmp) {
        Comparator pairCmp2 = new Comparator<Pair<? extends T, ? extends Integer>>(entityCmp){
            final /* synthetic */ Comparator $entityCmp;

            public int compare(@NotNull Pair<? extends T, Integer> o1, @NotNull Pair<? extends T, Integer> o2) {
                Intrinsics.checkParameterIsNotNull(o1, "o1");
                Intrinsics.checkParameterIsNotNull(o2, "o2");
                int cmp = this.$entityCmp.compare(o1.getFirst(), o2.getFirst());
                if (cmp != 0) {
                    return cmp;
                }
                return ((Number)o2.getSecond()).intValue() - ((Number)o1.getSecond()).intValue();
            }
            {
                this.$entityCmp = $captured_local_variable$0;
            }
        };
        Function1 $fun$sorted$1 = new Function1<Iterable<? extends T>, Iterable<? extends T>>(pairCmp2){
            final /* synthetic */ compareUnordered.pairCmp.1 $pairCmp;

            /*
             * WARNING - void declaration
             */
            @NotNull
            public final Iterable<T> invoke(@NotNull Iterable<? extends T> $this$sorted) {
                void $this$mapIndexedTo$iv$iv;
                Intrinsics.checkParameterIsNotNull($this$sorted, "$this$sorted");
                Iterable<? extends T> $this$mapIndexed$iv = $this$sorted;
                boolean $i$f$mapIndexed = false;
                Iterable<? extends T> iterable = $this$mapIndexed$iv;
                Collection destination$iv$iv = new ArrayList<E>(CollectionsKt.collectionSizeOrDefault($this$mapIndexed$iv, 10));
                boolean $i$f$mapIndexedTo = false;
                int index$iv$iv = 0;
                for (T item$iv$iv : $this$mapIndexedTo$iv$iv) {
                    void i;
                    void e;
                    int n = index$iv$iv++;
                    Collection collection = destination$iv$iv;
                    boolean bl = false;
                    if (n < 0) {
                        CollectionsKt.throwIndexOverflow();
                    }
                    int n2 = n;
                    T t = item$iv$iv;
                    int n3 = n2;
                    Collection collection2 = collection;
                    boolean bl2 = false;
                    Pair<void, Integer> pair = new Pair<void, Integer>(e, (int)i);
                    collection2.add(pair);
                }
                return SequencesKt.asIterable(SequencesKt.map(CollectionsKt.asSequence((Iterable)CollectionsKt.toSortedSet((List)destination$iv$iv, this.$pairCmp)), compareUnordered.2.INSTANCE));
            }
            {
                this.$pairCmp = var1_1;
                super(1);
            }
        };
        return this.compareOrdered($fun$sorted$1.invoke(left), $fun$sorted$1.invoke(right), entityCmp);
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public int compare(@NotNull ExprValue left, @NotNull ExprValue right) {
        Integer n;
        Integer n2;
        Integer n3;
        int it;
        boolean $i$f$ifCompared;
        Object value$iv;
        void leftTypeCond$iv;
        boolean $i$f$handle;
        boolean rightTypeCond$iv;
        Intrinsics.checkParameterIsNotNull(left, "left");
        Intrinsics.checkParameterIsNotNull(right, "right");
        if (left == right) {
            return 0;
        }
        ExprValueType lType = left.getType();
        ExprValueType rType = right.getType();
        if (this.nullOrder == NullOrder.FIRST) {
            Integer n4;
            NaturalExprValueComparators naturalExprValueComparators = this;
            NaturalExprValueComparators naturalExprValueComparators2 = this;
            boolean bl = lType.isUnknown();
            rightTypeCond$iv = rType.isUnknown();
            $i$f$handle = false;
            if (leftTypeCond$iv != false && rightTypeCond$iv) {
                boolean bl2 = false;
                n4 = 0;
            } else {
                n4 = leftTypeCond$iv != false ? Integer.valueOf(-1) : (rightTypeCond$iv ? Integer.valueOf(1) : null);
            }
            value$iv = n4;
            $i$f$ifCompared = false;
            if (value$iv != null) {
                it = ((Number)value$iv).intValue();
                boolean bl3 = false;
                return it;
            }
        }
        NaturalExprValueComparators this_$iv = this;
        value$iv = this;
        $i$f$ifCompared = lType == ExprValueType.BOOL;
        rightTypeCond$iv = rType == ExprValueType.BOOL;
        $i$f$handle = false;
        if (leftTypeCond$iv != false && rightTypeCond$iv) {
            boolean bl = false;
            boolean lVal = ExprValueExtensionsKt.booleanValue(left);
            boolean rVal = ExprValueExtensionsKt.booleanValue(right);
            n3 = lVal == rVal ? 0 : (!lVal ? -1 : 1);
        } else {
            n3 = leftTypeCond$iv != false ? Integer.valueOf(-1) : (rightTypeCond$iv ? Integer.valueOf(1) : null);
        }
        value$iv = n3;
        $i$f$ifCompared = false;
        if (value$iv != null) {
            it = ((Number)value$iv).intValue();
            boolean bl = false;
            return it;
        }
        this_$iv = this;
        value$iv = this;
        $i$f$ifCompared = lType.isNumber();
        rightTypeCond$iv = rType.isNumber();
        $i$f$handle = false;
        if (leftTypeCond$iv != false && rightTypeCond$iv) {
            Integer n5;
            int it2;
            Integer n6;
            void leftTypeCond$iv2;
            boolean bl = false;
            Number lVal = ExprValueExtensionsKt.numberValue(left);
            Number rVal = ExprValueExtensionsKt.numberValue(right);
            NaturalExprValueComparators naturalExprValueComparators = this;
            NaturalExprValueComparators naturalExprValueComparators3 = this;
            boolean bl4 = NumberExtensionsKt.isNaN(lVal);
            boolean rightTypeCond$iv2 = NumberExtensionsKt.isNaN(rVal);
            boolean $i$f$handle2 = false;
            if (leftTypeCond$iv2 != false && rightTypeCond$iv2) {
                boolean bl5 = false;
                n6 = 0;
            } else {
                n6 = leftTypeCond$iv2 != false ? Integer.valueOf(-1) : (rightTypeCond$iv2 ? Integer.valueOf(1) : null);
            }
            Object value$iv2 = n6;
            boolean $i$f$ifCompared2 = false;
            if (value$iv2 != null) {
                it2 = ((Number)value$iv2).intValue();
                boolean bl6 = false;
                return it2;
            }
            NaturalExprValueComparators this_$iv2 = this;
            value$iv2 = this;
            $i$f$ifCompared2 = NumberExtensionsKt.isNegInf(lVal);
            rightTypeCond$iv2 = NumberExtensionsKt.isNegInf(rVal);
            $i$f$handle2 = false;
            if (leftTypeCond$iv2 != false && rightTypeCond$iv2) {
                boolean bl7 = false;
                n5 = 0;
            } else {
                n5 = leftTypeCond$iv2 != false ? Integer.valueOf(-1) : (rightTypeCond$iv2 ? Integer.valueOf(1) : null);
            }
            value$iv2 = n5;
            $i$f$ifCompared2 = false;
            if (value$iv2 != null) {
                it2 = ((Number)value$iv2).intValue();
                boolean bl8 = false;
                return it2;
            }
            if (NumberExtensionsKt.isPosInf(lVal) && NumberExtensionsKt.isPosInf(rVal)) {
                return 0;
            }
            if (NumberExtensionsKt.isPosInf(lVal)) {
                return 1;
            }
            if (NumberExtensionsKt.isPosInf(rVal)) {
                return -1;
            }
            if (NumberExtensionsKt.isZero(lVal) && NumberExtensionsKt.isZero(rVal)) {
                return 0;
            }
            return NumberExtensionsKt.compareTo(lVal, rVal);
        }
        value$iv = leftTypeCond$iv != false ? Integer.valueOf(-1) : (rightTypeCond$iv ? Integer.valueOf(1) : null);
        $i$f$ifCompared = false;
        if (value$iv != null) {
            it = ((Number)value$iv).intValue();
            boolean bl = false;
            return it;
        }
        this_$iv = this;
        value$iv = this;
        $i$f$ifCompared = lType == ExprValueType.DATE;
        rightTypeCond$iv = rType == ExprValueType.DATE;
        $i$f$handle = false;
        if (leftTypeCond$iv != false && rightTypeCond$iv) {
            boolean bl = false;
            LocalDate lVal = ExprValueExtensionsKt.dateValue(left);
            LocalDate rVal = ExprValueExtensionsKt.dateValue(right);
            return lVal.compareTo(rVal);
        }
        value$iv = leftTypeCond$iv != false ? Integer.valueOf(-1) : (rightTypeCond$iv ? Integer.valueOf(1) : null);
        $i$f$ifCompared = false;
        if (value$iv != null) {
            it = ((Number)value$iv).intValue();
            boolean bl = false;
            return it;
        }
        this_$iv = this;
        value$iv = this;
        $i$f$ifCompared = lType == ExprValueType.TIME;
        rightTypeCond$iv = rType == ExprValueType.TIME;
        $i$f$handle = false;
        if (leftTypeCond$iv != false && rightTypeCond$iv) {
            boolean bl = false;
            Time lVal = ExprValueExtensionsKt.timeValue(left);
            Time rVal = ExprValueExtensionsKt.timeValue(right);
            return lVal.naturalOrderCompareTo(rVal);
        }
        value$iv = leftTypeCond$iv != false ? Integer.valueOf(-1) : (rightTypeCond$iv ? Integer.valueOf(1) : null);
        $i$f$ifCompared = false;
        if (value$iv != null) {
            it = ((Number)value$iv).intValue();
            boolean bl = false;
            return it;
        }
        this_$iv = this;
        value$iv = this;
        $i$f$ifCompared = lType == ExprValueType.TIMESTAMP;
        rightTypeCond$iv = rType == ExprValueType.TIMESTAMP;
        $i$f$handle = false;
        if (leftTypeCond$iv != false && rightTypeCond$iv) {
            boolean bl = false;
            Timestamp lVal = ExprValueExtensionsKt.timestampValue(left);
            Timestamp rVal = ExprValueExtensionsKt.timestampValue(right);
            return lVal.compareTo(rVal);
        }
        value$iv = leftTypeCond$iv != false ? Integer.valueOf(-1) : (rightTypeCond$iv ? Integer.valueOf(1) : null);
        $i$f$ifCompared = false;
        if (value$iv != null) {
            it = ((Number)value$iv).intValue();
            boolean bl = false;
            return it;
        }
        this_$iv = this;
        value$iv = this;
        $i$f$ifCompared = lType.isText();
        rightTypeCond$iv = rType.isText();
        $i$f$handle = false;
        if (leftTypeCond$iv != false && rightTypeCond$iv) {
            boolean bl = false;
            String lVal = ExprValueExtensionsKt.stringValue(left);
            String rVal = ExprValueExtensionsKt.stringValue(right);
            return lVal.compareTo(rVal);
        }
        value$iv = leftTypeCond$iv != false ? Integer.valueOf(-1) : (rightTypeCond$iv ? Integer.valueOf(1) : null);
        $i$f$ifCompared = false;
        if (value$iv != null) {
            it = ((Number)value$iv).intValue();
            boolean bl = false;
            return it;
        }
        this_$iv = this;
        value$iv = this;
        $i$f$ifCompared = lType.isLob();
        rightTypeCond$iv = rType.isLob();
        $i$f$handle = false;
        if (leftTypeCond$iv != false && rightTypeCond$iv) {
            boolean bl = false;
            byte[] lVal = ExprValueExtensionsKt.bytesValue(left);
            byte[] rVal = ExprValueExtensionsKt.bytesValue(right);
            int this_$iv3 = lVal.length;
            int n7 = rVal.length;
            boolean $i$f$ifCompared3 = false;
            int commonLen = Math.min(this_$iv3, n7);
            this_$iv3 = 0;
            n7 = commonLen;
            while (this_$iv3 < n7) {
                void i;
                int lOctet = lVal[i] & 0xFF;
                int rOctet = rVal[i] & 0xFF;
                int diff = lOctet - rOctet;
                if (diff != 0) {
                    return diff;
                }
                ++i;
            }
            return lVal.length - rVal.length;
        }
        value$iv = leftTypeCond$iv != false ? Integer.valueOf(-1) : (rightTypeCond$iv ? Integer.valueOf(1) : null);
        $i$f$ifCompared = false;
        if (value$iv != null) {
            it = ((Number)value$iv).intValue();
            boolean bl = false;
            return it;
        }
        this_$iv = this;
        value$iv = this;
        $i$f$ifCompared = lType == ExprValueType.LIST;
        rightTypeCond$iv = rType == ExprValueType.LIST;
        $i$f$handle = false;
        if (leftTypeCond$iv != false && rightTypeCond$iv) {
            boolean bl = false;
            return this.compareOrdered(left, right, this);
        }
        value$iv = leftTypeCond$iv != false ? Integer.valueOf(-1) : (rightTypeCond$iv ? Integer.valueOf(1) : null);
        $i$f$ifCompared = false;
        if (value$iv != null) {
            it = ((Number)value$iv).intValue();
            boolean bl = false;
            return it;
        }
        this_$iv = this;
        value$iv = this;
        $i$f$ifCompared = lType == ExprValueType.SEXP;
        rightTypeCond$iv = rType == ExprValueType.SEXP;
        $i$f$handle = false;
        if (leftTypeCond$iv != false && rightTypeCond$iv) {
            boolean bl = false;
            return this.compareOrdered(left, right, this);
        }
        value$iv = leftTypeCond$iv != false ? Integer.valueOf(-1) : (rightTypeCond$iv ? Integer.valueOf(1) : null);
        $i$f$ifCompared = false;
        if (value$iv != null) {
            it = ((Number)value$iv).intValue();
            boolean bl = false;
            return it;
        }
        this_$iv = this;
        value$iv = this;
        $i$f$ifCompared = lType == ExprValueType.STRUCT;
        rightTypeCond$iv = rType == ExprValueType.STRUCT;
        $i$f$handle = false;
        if (leftTypeCond$iv != false && rightTypeCond$iv) {
            boolean bl = false;
            n2 = this.compareUnordered(left, right, this.structFieldComparator);
        } else {
            n2 = leftTypeCond$iv != false ? Integer.valueOf(-1) : (rightTypeCond$iv ? Integer.valueOf(1) : null);
        }
        value$iv = n2;
        $i$f$ifCompared = false;
        if (value$iv != null) {
            it = ((Number)value$iv).intValue();
            boolean bl = false;
            return it;
        }
        this_$iv = this;
        value$iv = this;
        $i$f$ifCompared = lType == ExprValueType.BAG;
        rightTypeCond$iv = rType == ExprValueType.BAG;
        $i$f$handle = false;
        if (leftTypeCond$iv != false && rightTypeCond$iv) {
            boolean bl = false;
            n = this.compareUnordered(left, right, this);
        } else {
            n = leftTypeCond$iv != false ? Integer.valueOf(-1) : (rightTypeCond$iv ? Integer.valueOf(1) : null);
        }
        value$iv = n;
        $i$f$ifCompared = false;
        if (value$iv != null) {
            it = ((Number)value$iv).intValue();
            boolean bl = false;
            return it;
        }
        if (this.nullOrder == NullOrder.LAST) {
            Integer n8;
            this_$iv = this;
            value$iv = this;
            $i$f$ifCompared = lType.isUnknown();
            rightTypeCond$iv = rType.isUnknown();
            $i$f$handle = false;
            if (leftTypeCond$iv != false && rightTypeCond$iv) {
                boolean bl = false;
                n8 = 0;
            } else {
                n8 = leftTypeCond$iv != false ? Integer.valueOf(-1) : (rightTypeCond$iv ? Integer.valueOf(1) : null);
            }
            value$iv = n8;
            $i$f$ifCompared = false;
            if (value$iv != null) {
                it = ((Number)value$iv).intValue();
                boolean bl = false;
                return it;
            }
        }
        throw (Throwable)new IllegalStateException("Could not compare: " + left + " and " + right);
    }

    private NaturalExprValueComparators(NullOrder nullOrder) {
        this.nullOrder = nullOrder;
        this.structFieldComparator = new Comparator<ExprValue>(this){
            final /* synthetic */ NaturalExprValueComparators this$0;

            public int compare(@NotNull ExprValue left, @NotNull ExprValue right) {
                Intrinsics.checkParameterIsNotNull(left, "left");
                Intrinsics.checkParameterIsNotNull(right, "right");
                ExprValue exprValue2 = ExprValueExtensionsKt.getName(left);
                if (exprValue2 == null) {
                    Void void_ = ExceptionsKt.errNoContext("Internal error: left struct field has no name", true);
                    throw null;
                }
                ExprValue lName = exprValue2;
                ExprValue exprValue3 = ExprValueExtensionsKt.getName(right);
                if (exprValue3 == null) {
                    Void void_ = ExceptionsKt.errNoContext("Internal error: left struct field has no name", true);
                    throw null;
                }
                ExprValue rName = exprValue3;
                int cmp = this.this$0.compare(lName, rName);
                if (cmp != 0) {
                    return cmp;
                }
                return this.this$0.compare(left, right);
            }
            {
                this.this$0 = $outer;
            }
        };
    }

    public static NaturalExprValueComparators[] values() {
        return (NaturalExprValueComparators[])$VALUES.clone();
    }

    public static NaturalExprValueComparators valueOf(String string) {
        return Enum.valueOf(NaturalExprValueComparators.class, string);
    }

    @Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0004\b\u0082\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004\u00a8\u0006\u0005"}, d2={"Lorg/partiql/lang/eval/NaturalExprValueComparators$NullOrder;", "", "(Ljava/lang/String;I)V", "FIRST", "LAST", "lang"})
    private static final class NullOrder
    extends Enum<NullOrder> {
        public static final /* enum */ NullOrder FIRST;
        public static final /* enum */ NullOrder LAST;
        private static final /* synthetic */ NullOrder[] $VALUES;

        static {
            NullOrder[] nullOrderArray = new NullOrder[2];
            NullOrder[] nullOrderArray2 = nullOrderArray;
            nullOrderArray[0] = FIRST = new NullOrder();
            nullOrderArray[1] = LAST = new NullOrder();
            $VALUES = nullOrderArray;
        }

        public static NullOrder[] values() {
            return (NullOrder[])$VALUES.clone();
        }

        public static NullOrder valueOf(String string) {
            return Enum.valueOf(NullOrder.class, string);
        }
    }

    @Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0007"}, d2={"Lorg/partiql/lang/eval/NaturalExprValueComparators$Companion;", "", "()V", "EQUAL", "", "LESS", "MORE", "lang"})
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

