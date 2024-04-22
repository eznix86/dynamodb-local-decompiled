/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package org.partiql.lang.eval.builtins.timestamp;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.Grouping;
import kotlin.collections.GroupingKt;
import kotlin.collections.MapsKt;
import kotlin.comparisons.ComparisonsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.PropertyReference1Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KProperty;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.partiql.lang.errors.ErrorCode;
import org.partiql.lang.errors.Property;
import org.partiql.lang.eval.EvaluationException;
import org.partiql.lang.eval.builtins.timestamp.AmPmPatternSymbol;
import org.partiql.lang.eval.builtins.timestamp.FormatItem;
import org.partiql.lang.eval.builtins.timestamp.FormatPattern;
import org.partiql.lang.eval.builtins.timestamp.FormatPattern$WhenMappings;
import org.partiql.lang.eval.builtins.timestamp.HourOfDayPatternSymbol;
import org.partiql.lang.eval.builtins.timestamp.MonthFormat;
import org.partiql.lang.eval.builtins.timestamp.MonthPatternSymbol;
import org.partiql.lang.eval.builtins.timestamp.OffsetPatternSymbol;
import org.partiql.lang.eval.builtins.timestamp.PatternSymbol;
import org.partiql.lang.eval.builtins.timestamp.TimestampField;
import org.partiql.lang.eval.builtins.timestamp.TimestampFormatPatternParser;
import org.partiql.lang.eval.builtins.timestamp.YearFormat;
import org.partiql.lang.eval.builtins.timestamp.YearPatternSymbol;
import org.partiql.lang.util.PropertyMapHelpersKt;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000<\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0006\b\u0000\u0018\u0000 '2\u00020\u0001:\u0001'B\u001b\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u00a2\u0006\u0002\u0010\u0007J\b\u0010!\u001a\u00020\"H\u0002J\b\u0010#\u001a\u00020\"H\u0002J\b\u0010$\u001a\u00020\"H\u0002J\b\u0010%\u001a\u00020\"H\u0002J\u0006\u0010&\u001a\u00020\"R\u0017\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR!\u0010\f\u001a\b\u0012\u0004\u0012\u00020\r0\u00058FX\u0086\u0084\u0002\u00a2\u0006\f\n\u0004\b\u000f\u0010\u0010\u001a\u0004\b\u000e\u0010\tR\u001b\u0010\u0011\u001a\u00020\u00128FX\u0086\u0084\u0002\u00a2\u0006\f\n\u0004\b\u0015\u0010\u0010\u001a\u0004\b\u0013\u0010\u0014R\u001b\u0010\u0016\u001a\u00020\u00128FX\u0086\u0084\u0002\u00a2\u0006\f\n\u0004\b\u0018\u0010\u0010\u001a\u0004\b\u0017\u0010\u0014R\u001b\u0010\u0019\u001a\u00020\u00128FX\u0086\u0084\u0002\u00a2\u0006\f\n\u0004\b\u001b\u0010\u0010\u001a\u0004\b\u001a\u0010\u0014R\u001d\u0010\u001c\u001a\u0004\u0018\u00010\u001d8FX\u0086\u0084\u0002\u00a2\u0006\f\n\u0004\b \u0010\u0010\u001a\u0004\b\u001e\u0010\u001f\u00a8\u0006("}, d2={"Lorg/partiql/lang/eval/builtins/timestamp/FormatPattern;", "", "formatPatternString", "", "formatItems", "", "Lorg/partiql/lang/eval/builtins/timestamp/FormatItem;", "(Ljava/lang/String;Ljava/util/List;)V", "getFormatItems", "()Ljava/util/List;", "getFormatPatternString", "()Ljava/lang/String;", "formatSymbols", "Lorg/partiql/lang/eval/builtins/timestamp/PatternSymbol;", "getFormatSymbols", "formatSymbols$delegate", "Lkotlin/Lazy;", "has2DigitYear", "", "getHas2DigitYear", "()Z", "has2DigitYear$delegate", "hasAmPm", "getHasAmPm", "hasAmPm$delegate", "hasOffset", "getHasOffset", "hasOffset$delegate", "leastSignificantField", "Lorg/partiql/lang/eval/builtins/timestamp/TimestampField;", "getLeastSignificantField", "()Lorg/partiql/lang/eval/builtins/timestamp/TimestampField;", "leastSignificantField$delegate", "checkAmPmMismatch", "", "checkDuplicatefields", "checkFieldCombination", "checkForFieldsNotValidForParsing", "validateForTimestampParsing", "Companion", "lang"})
public final class FormatPattern {
    static final /* synthetic */ KProperty[] $$delegatedProperties;
    @Nullable
    private final Lazy leastSignificantField$delegate;
    @NotNull
    private final Lazy formatSymbols$delegate;
    @NotNull
    private final Lazy has2DigitYear$delegate;
    @NotNull
    private final Lazy hasOffset$delegate;
    @NotNull
    private final Lazy hasAmPm$delegate;
    @NotNull
    private final String formatPatternString;
    @NotNull
    private final List<FormatItem> formatItems;
    public static final Companion Companion;

    static {
        $$delegatedProperties = new KProperty[]{Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(FormatPattern.class), "leastSignificantField", "getLeastSignificantField()Lorg/partiql/lang/eval/builtins/timestamp/TimestampField;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(FormatPattern.class), "formatSymbols", "getFormatSymbols()Ljava/util/List;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(FormatPattern.class), "has2DigitYear", "getHas2DigitYear()Z")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(FormatPattern.class), "hasOffset", "getHasOffset()Z")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(FormatPattern.class), "hasAmPm", "getHasAmPm()Z"))};
        Companion = new Companion(null);
    }

    @Nullable
    public final TimestampField getLeastSignificantField() {
        Lazy lazy = this.leastSignificantField$delegate;
        FormatPattern formatPattern = this;
        KProperty kProperty = $$delegatedProperties[0];
        boolean bl = false;
        return (TimestampField)((Object)lazy.getValue());
    }

    @NotNull
    public final List<PatternSymbol> getFormatSymbols() {
        Lazy lazy = this.formatSymbols$delegate;
        FormatPattern formatPattern = this;
        KProperty kProperty = $$delegatedProperties[1];
        boolean bl = false;
        return (List)lazy.getValue();
    }

    public final boolean getHas2DigitYear() {
        Lazy lazy = this.has2DigitYear$delegate;
        FormatPattern formatPattern = this;
        KProperty kProperty = $$delegatedProperties[2];
        boolean bl = false;
        return (Boolean)lazy.getValue();
    }

    public final boolean getHasOffset() {
        Lazy lazy = this.hasOffset$delegate;
        FormatPattern formatPattern = this;
        KProperty kProperty = $$delegatedProperties[3];
        boolean bl = false;
        return (Boolean)lazy.getValue();
    }

    public final boolean getHasAmPm() {
        Lazy lazy = this.hasAmPm$delegate;
        FormatPattern formatPattern = this;
        KProperty kProperty = $$delegatedProperties[4];
        boolean bl = false;
        return (Boolean)lazy.getValue();
    }

    public final void validateForTimestampParsing() {
        this.checkForFieldsNotValidForParsing();
        this.checkDuplicatefields();
        this.checkFieldCombination();
        this.checkAmPmMismatch();
    }

    /*
     * WARNING - void declaration
     */
    private final void checkDuplicatefields() {
        void $this$filterTo$iv$iv;
        Iterable $this$groupingBy$iv = this.getFormatSymbols();
        boolean $i$f$groupingBy = false;
        Map $this$filter$iv = GroupingKt.eachCount((Grouping)new Grouping<PatternSymbol, TimestampField>($this$groupingBy$iv){
            final /* synthetic */ Iterable $this_groupingBy;
            {
                this.$this_groupingBy = $receiver;
            }

            @NotNull
            public Iterator<PatternSymbol> sourceIterator() {
                return this.$this_groupingBy.iterator();
            }

            /*
             * Ignored method signature, as it can't be verified against descriptor
             */
            public Object keyOf(Object element) {
                PatternSymbol it = (PatternSymbol)element;
                boolean bl = false;
                return it.getField();
            }
        });
        boolean $i$f$filter = false;
        Object object = $this$filter$iv;
        Map destination$iv$iv = new LinkedHashMap();
        boolean $i$f$filterTo = false;
        void var7_8 = $this$filterTo$iv$iv;
        boolean bl = false;
        Iterator iterator2 = var7_8.entrySet().iterator();
        while (iterator2.hasNext()) {
            Map.Entry element$iv$iv;
            Map.Entry it = element$iv$iv = iterator2.next();
            boolean bl2 = false;
            if (!(((Number)it.getValue()).intValue() > 1)) continue;
            destination$iv$iv.put(element$iv$iv.getKey(), element$iv$iv.getValue());
        }
        Sequence $this$sortedByDescending$iv = MapsKt.asSequence(destination$iv$iv);
        boolean $i$f$sortedByDescending = false;
        object = $this$sortedByDescending$iv;
        boolean bl3 = false;
        Comparator comparator = new Comparator<T>(){

            public final int compare(T a, T b) {
                boolean bl = false;
                Map.Entry it = (Map.Entry)b;
                boolean bl2 = false;
                Comparable comparable = Integer.valueOf(((Number)it.getValue()).intValue());
                it = (Map.Entry)a;
                Comparable comparable2 = comparable;
                bl2 = false;
                Integer n = ((Number)it.getValue()).intValue();
                return ComparisonsKt.compareValues(comparable2, (Comparable)n);
            }
        };
        Map.Entry duplicatedField = (Map.Entry)SequencesKt.firstOrNull(SequencesKt.sortedWith(object, comparator));
        if (duplicatedField != null) {
            throw (Throwable)new EvaluationException("timestamp format pattern duplicate fields", ErrorCode.EVALUATOR_TIMESTAMP_FORMAT_PATTERN_DUPLICATE_FIELDS, PropertyMapHelpersKt.propertyValueMapOf(PropertyMapHelpersKt.to(Property.TIMESTAMP_FORMAT_PATTERN, this.formatPatternString), PropertyMapHelpersKt.to(Property.TIMESTAMP_FORMAT_PATTERN_FIELDS, duplicatedField.getKey())), null, false, 8, null);
        }
    }

    /*
     * WARNING - void declaration
     */
    private final void checkAmPmMismatch() {
        block6: {
            void $this$filterIsInstanceTo$iv$iv;
            void $this$filterIsInstanceTo$iv$iv2;
            Iterable $this$filterIsInstance$iv = this.getFormatSymbols();
            boolean $i$f$filterIsInstance = false;
            Iterable iterable = $this$filterIsInstance$iv;
            Collection destination$iv$iv = new ArrayList();
            boolean $i$f$filterIsInstanceTo = false;
            for (Object element$iv$iv : $this$filterIsInstanceTo$iv$iv2) {
                if (!(element$iv$iv instanceof HourOfDayPatternSymbol)) continue;
                destination$iv$iv.add(element$iv$iv);
            }
            HourOfDayPatternSymbol hourOfDayPatternSymbol = (HourOfDayPatternSymbol)CollectionsKt.firstOrNull((List)destination$iv$iv);
            if (hourOfDayPatternSymbol == null) break block6;
            HourOfDayPatternSymbol hourOfDayPatternSymbol2 = hourOfDayPatternSymbol;
            boolean bl = false;
            boolean bl2 = false;
            HourOfDayPatternSymbol it = hourOfDayPatternSymbol2;
            boolean bl3 = false;
            Iterable $this$filterIsInstance$iv2 = this.getFormatSymbols();
            boolean $i$f$filterIsInstance2 = false;
            Iterable iterable2 = $this$filterIsInstance$iv2;
            Collection destination$iv$iv2 = new ArrayList();
            boolean $i$f$filterIsInstanceTo2 = false;
            for (Object element$iv$iv : $this$filterIsInstanceTo$iv$iv) {
                if (!(element$iv$iv instanceof AmPmPatternSymbol)) continue;
                destination$iv$iv2.add(element$iv$iv);
            }
            boolean hasAmPm2 = CollectionsKt.any((List)destination$iv$iv2);
            switch (FormatPattern$WhenMappings.$EnumSwitchMapping$0[it.getFormat().getClock().ordinal()]) {
                case 1: {
                    if (hasAmPm2) break;
                    throw (Throwable)new EvaluationException("timestamp format pattern contains 12-hour hour of day field but doesn't contain an am/pm field.", ErrorCode.EVALUATOR_TIMESTAMP_FORMAT_PATTERN_HOUR_CLOCK_AM_PM_MISMATCH, PropertyMapHelpersKt.propertyValueMapOf(PropertyMapHelpersKt.to(Property.TIMESTAMP_FORMAT_PATTERN, this.formatPatternString)), null, false, 8, null);
                }
                case 2: {
                    if (!hasAmPm2) break;
                    throw (Throwable)new EvaluationException("timestamp format pattern contains 24-hour hour of day field and also contains an am/pm field.", ErrorCode.EVALUATOR_TIMESTAMP_FORMAT_PATTERN_HOUR_CLOCK_AM_PM_MISMATCH, PropertyMapHelpersKt.propertyValueMapOf(PropertyMapHelpersKt.to(Property.TIMESTAMP_FORMAT_PATTERN, this.formatPatternString)), null, false, 8, null);
                }
            }
        }
    }

    private final void checkFieldCombination() {
        Function1 $fun$err$1 = new Function1(this){
            final /* synthetic */ FormatPattern this$0;

            @NotNull
            public final Void invoke(@NotNull String missingFields) {
                Intrinsics.checkParameterIsNotNull(missingFields, "missingFields");
                throw (Throwable)new EvaluationException("timestamp format pattern missing fields", ErrorCode.EVALUATOR_INCOMPLETE_TIMESTAMP_FORMAT_PATTERN, PropertyMapHelpersKt.propertyValueMapOf(PropertyMapHelpersKt.to(Property.TIMESTAMP_FORMAT_PATTERN, this.this$0.getFormatPatternString()), PropertyMapHelpersKt.to(Property.TIMESTAMP_FORMAT_PATTERN_FIELDS, missingFields)), null, false, 8, null);
            }
            {
                this.this$0 = formatPattern;
                super(1);
            }
        };
        Function1<TimestampField[], Unit> $fun$errIfMissingTimestampFields$2 = new Function1<TimestampField[], Unit>(this, $fun$err$1){
            final /* synthetic */ FormatPattern this$0;
            final /* synthetic */ checkFieldCombination.1 $err$1;

            /*
             * WARNING - void declaration
             */
            public final void invoke(@NotNull TimestampField ... fields) {
                void $this$filterTo$iv$iv;
                Intrinsics.checkParameterIsNotNull(fields, "fields");
                TimestampField[] $this$filter$iv = fields;
                boolean $i$f$filter = false;
                TimestampField[] timestampFieldArray = $this$filter$iv;
                Collection destination$iv$iv = new ArrayList<E>();
                boolean $i$f$filterTo = false;
                void var8_7 = $this$filterTo$iv$iv;
                int n = ((void)var8_7).length;
                for (int i = 0; i < n; ++i) {
                    boolean bl;
                    void element$iv$iv;
                    block5: {
                        void requiredField = element$iv$iv = var8_7[i];
                        boolean bl2 = false;
                        Iterable $this$all$iv = this.this$0.getFormatSymbols();
                        boolean $i$f$all = false;
                        if ($this$all$iv instanceof Collection && ((Collection)$this$all$iv).isEmpty()) {
                            bl = true;
                        } else {
                            for (T element$iv : $this$all$iv) {
                                PatternSymbol it = (PatternSymbol)element$iv;
                                boolean bl3 = false;
                                if (it.getField() != requiredField) continue;
                                bl = false;
                                break block5;
                            }
                            bl = true;
                        }
                    }
                    if (!bl) continue;
                    destination$iv$iv.add(element$iv$iv);
                }
                List missingFields = (List)destination$iv$iv;
                if (CollectionsKt.any(missingFields)) {
                    Void void_ = this.$err$1.invoke(SequencesKt.joinToString$default(CollectionsKt.asSequence(missingFields), ", ", null, null, 0, null, null, 62, null));
                    throw null;
                }
            }
            {
                this.this$0 = formatPattern;
                this.$err$1 = var2_2;
                super(1);
            }
        };
        if (this.getHasOffset() || this.getHasAmPm()) {
            $fun$errIfMissingTimestampFields$2.invoke(TimestampField.YEAR, TimestampField.MONTH_OF_YEAR, TimestampField.DAY_OF_MONTH, TimestampField.HOUR_OF_DAY);
        }
        TimestampField timestampField = this.getLeastSignificantField();
        if (timestampField == null) {
            Void void_ = $fun$err$1.invoke("YEAR");
            throw null;
        }
        switch (FormatPattern$WhenMappings.$EnumSwitchMapping$1[timestampField.ordinal()]) {
            case 1: {
                break;
            }
            case 2: {
                $fun$errIfMissingTimestampFields$2.invoke(TimestampField.YEAR);
                break;
            }
            case 3: {
                $fun$errIfMissingTimestampFields$2.invoke(TimestampField.YEAR, TimestampField.MONTH_OF_YEAR);
                break;
            }
            case 4: {
                $fun$errIfMissingTimestampFields$2.invoke(TimestampField.YEAR, TimestampField.MONTH_OF_YEAR, TimestampField.DAY_OF_MONTH);
                break;
            }
            case 5: {
                $fun$errIfMissingTimestampFields$2.invoke(TimestampField.YEAR, TimestampField.MONTH_OF_YEAR, TimestampField.DAY_OF_MONTH, TimestampField.HOUR_OF_DAY);
                break;
            }
            case 6: {
                $fun$errIfMissingTimestampFields$2.invoke(TimestampField.YEAR, TimestampField.MONTH_OF_YEAR, TimestampField.DAY_OF_MONTH, TimestampField.HOUR_OF_DAY, TimestampField.MINUTE_OF_HOUR);
                break;
            }
            case 7: {
                $fun$errIfMissingTimestampFields$2.invoke(TimestampField.YEAR, TimestampField.MONTH_OF_YEAR, TimestampField.DAY_OF_MONTH, TimestampField.HOUR_OF_DAY, TimestampField.MINUTE_OF_HOUR, TimestampField.SECOND_OF_MINUTE);
                break;
            }
            case 8: 
            case 9: {
                throw (Throwable)new IllegalStateException("OFFSET, AM_PM should never be the least significant field!");
            }
        }
    }

    /*
     * WARNING - void declaration
     */
    private final void checkForFieldsNotValidForParsing() {
        boolean bl;
        block5: {
            void $this$filterIsInstanceTo$iv$iv;
            Iterable $this$filterIsInstance$iv = this.getFormatSymbols();
            boolean $i$f$filterIsInstance = false;
            Iterable iterable = $this$filterIsInstance$iv;
            Collection destination$iv$iv = new ArrayList();
            boolean $i$f$filterIsInstanceTo = false;
            for (Object element$iv$iv : $this$filterIsInstanceTo$iv$iv) {
                if (!(element$iv$iv instanceof MonthPatternSymbol)) continue;
                destination$iv$iv.add(element$iv$iv);
            }
            Iterable $this$any$iv = (List)destination$iv$iv;
            boolean $i$f$any = false;
            if ($this$any$iv instanceof Collection && ((Collection)$this$any$iv).isEmpty()) {
                bl = false;
            } else {
                for (Object element$iv : $this$any$iv) {
                    MonthPatternSymbol it = (MonthPatternSymbol)element$iv;
                    boolean bl2 = false;
                    if (!(it.getFormat() == MonthFormat.FIRST_LETTER_OF_MONTH_NAME)) continue;
                    bl = true;
                    break block5;
                }
                bl = false;
            }
        }
        if (bl) {
            throw (Throwable)new EvaluationException("timestamp format pattern missing fields", ErrorCode.EVALUATOR_INVALID_TIMESTAMP_FORMAT_PATTERN_SYMBOL_FOR_PARSING, PropertyMapHelpersKt.propertyValueMapOf(PropertyMapHelpersKt.to(Property.TIMESTAMP_FORMAT_PATTERN, this.formatPatternString)), null, false, 8, null);
        }
    }

    @NotNull
    public final String getFormatPatternString() {
        return this.formatPatternString;
    }

    @NotNull
    public final List<FormatItem> getFormatItems() {
        return this.formatItems;
    }

    public FormatPattern(@NotNull String formatPatternString, @NotNull List<? extends FormatItem> formatItems) {
        Intrinsics.checkParameterIsNotNull(formatPatternString, "formatPatternString");
        Intrinsics.checkParameterIsNotNull(formatItems, "formatItems");
        this.formatPatternString = formatPatternString;
        this.formatItems = formatItems;
        this.leastSignificantField$delegate = LazyKt.lazy((Function0)new Function0<TimestampField>(this){
            final /* synthetic */ FormatPattern this$0;

            /*
             * WARNING - void declaration
             */
            @Nullable
            public final TimestampField invoke() {
                void $this$filterTo$iv$iv;
                Iterable $this$filter$iv = this.this$0.getFormatSymbols();
                boolean $i$f$filter = false;
                Iterable iterable = $this$filter$iv;
                Collection destination$iv$iv = new ArrayList<E>();
                boolean $i$f$filterTo = false;
                for (T element$iv$iv : $this$filterTo$iv$iv) {
                    PatternSymbol it = (PatternSymbol)element$iv$iv;
                    boolean bl = false;
                    if (!(it.getField().getPrecisionRank() != null)) continue;
                    destination$iv$iv.add(element$iv$iv);
                }
                Iterable $this$sortedByDescending$iv = (List)destination$iv$iv;
                boolean $i$f$sortedByDescending = false;
                iterable = $this$sortedByDescending$iv;
                boolean bl = false;
                Comparator comparator = new Comparator<T>(){

                    public final int compare(T a, T b) {
                        boolean bl = false;
                        PatternSymbol it = (PatternSymbol)b;
                        boolean bl2 = false;
                        Comparable comparable = it.getField().getPrecisionRank();
                        it = (PatternSymbol)a;
                        Comparable comparable2 = comparable;
                        bl2 = false;
                        Integer n = it.getField().getPrecisionRank();
                        return ComparisonsKt.compareValues(comparable2, (Comparable)n);
                    }
                };
                PatternSymbol patternSymbol = (PatternSymbol)CollectionsKt.firstOrNull(CollectionsKt.sortedWith(iterable, comparator));
                return patternSymbol != null ? patternSymbol.getField() : null;
            }
            {
                this.this$0 = formatPattern;
                super(0);
            }
        });
        this.formatSymbols$delegate = LazyKt.lazy((Function0)new Function0<List<? extends PatternSymbol>>(this){
            final /* synthetic */ FormatPattern this$0;

            /*
             * WARNING - void declaration
             */
            @NotNull
            public final List<PatternSymbol> invoke() {
                void $this$filterIsInstanceTo$iv$iv;
                Iterable $this$filterIsInstance$iv = this.this$0.getFormatItems();
                boolean $i$f$filterIsInstance = false;
                Iterable iterable = $this$filterIsInstance$iv;
                Collection destination$iv$iv = new ArrayList<E>();
                boolean $i$f$filterIsInstanceTo = false;
                for (T element$iv$iv : $this$filterIsInstanceTo$iv$iv) {
                    if (!(element$iv$iv instanceof PatternSymbol)) continue;
                    destination$iv$iv.add(element$iv$iv);
                }
                return (List)destination$iv$iv;
            }
            {
                this.this$0 = formatPattern;
                super(0);
            }
        });
        this.has2DigitYear$delegate = LazyKt.lazy((Function0)new Function0<Boolean>(this){
            final /* synthetic */ FormatPattern this$0;

            /*
             * WARNING - void declaration
             */
            public final boolean invoke() {
                boolean bl;
                block4: {
                    void $this$filterIsInstanceTo$iv$iv;
                    Iterable $this$filterIsInstance$iv = this.this$0.getFormatSymbols();
                    boolean $i$f$filterIsInstance = false;
                    Iterable iterable = $this$filterIsInstance$iv;
                    Collection destination$iv$iv = new ArrayList<E>();
                    boolean $i$f$filterIsInstanceTo = false;
                    for (T element$iv$iv : $this$filterIsInstanceTo$iv$iv) {
                        if (!(element$iv$iv instanceof YearPatternSymbol)) continue;
                        destination$iv$iv.add(element$iv$iv);
                    }
                    Iterable $this$any$iv = (List)destination$iv$iv;
                    boolean $i$f$any = false;
                    if ($this$any$iv instanceof Collection && ((Collection)$this$any$iv).isEmpty()) {
                        bl = false;
                    } else {
                        for (E element$iv : $this$any$iv) {
                            YearPatternSymbol it = (YearPatternSymbol)element$iv;
                            boolean bl2 = false;
                            if (!(it.getFormat() == YearFormat.TWO_DIGIT)) continue;
                            bl = true;
                            break block4;
                        }
                        bl = false;
                    }
                }
                return bl;
            }
            {
                this.this$0 = formatPattern;
                super(0);
            }
        });
        this.hasOffset$delegate = LazyKt.lazy((Function0)new Function0<Boolean>(this){
            final /* synthetic */ FormatPattern this$0;

            /*
             * WARNING - void declaration
             */
            public final boolean invoke() {
                void $this$filterIsInstanceTo$iv$iv;
                Iterable $this$filterIsInstance$iv = this.this$0.getFormatSymbols();
                boolean $i$f$filterIsInstance = false;
                Iterable iterable = $this$filterIsInstance$iv;
                Collection destination$iv$iv = new ArrayList<E>();
                boolean $i$f$filterIsInstanceTo = false;
                for (T element$iv$iv : $this$filterIsInstanceTo$iv$iv) {
                    if (!(element$iv$iv instanceof OffsetPatternSymbol)) continue;
                    destination$iv$iv.add(element$iv$iv);
                }
                return CollectionsKt.any((List)destination$iv$iv);
            }
            {
                this.this$0 = formatPattern;
                super(0);
            }
        });
        this.hasAmPm$delegate = LazyKt.lazy((Function0)new Function0<Boolean>(this){
            final /* synthetic */ FormatPattern this$0;

            /*
             * WARNING - void declaration
             */
            public final boolean invoke() {
                void $this$filterIsInstanceTo$iv$iv;
                Iterable $this$filterIsInstance$iv = this.this$0.getFormatSymbols();
                boolean $i$f$filterIsInstance = false;
                Iterable iterable = $this$filterIsInstance$iv;
                Collection destination$iv$iv = new ArrayList<E>();
                boolean $i$f$filterIsInstanceTo = false;
                for (T element$iv$iv : $this$filterIsInstanceTo$iv$iv) {
                    if (!(element$iv$iv instanceof AmPmPatternSymbol)) continue;
                    destination$iv$iv.add(element$iv$iv);
                }
                return CollectionsKt.any((List)destination$iv$iv);
            }
            {
                this.this$0 = formatPattern;
                super(0);
            }
        });
    }

    @Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006\u00a8\u0006\u0007"}, d2={"Lorg/partiql/lang/eval/builtins/timestamp/FormatPattern$Companion;", "", "()V", "fromString", "Lorg/partiql/lang/eval/builtins/timestamp/FormatPattern;", "pattern", "", "lang"})
    public static final class Companion {
        @NotNull
        public final FormatPattern fromString(@NotNull String pattern) {
            Intrinsics.checkParameterIsNotNull(pattern, "pattern");
            return new TimestampFormatPatternParser().parse(pattern);
        }

        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

