/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package org.partiql.lang.eval.builtins.timestamp;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import org.jetbrains.annotations.NotNull;
import org.partiql.lang.eval.builtins.timestamp.FormatItem;
import org.partiql.lang.eval.builtins.timestamp.TimestampField;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b0\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u0012\u0010\u0003\u001a\u00020\u0004X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006\u0082\u0001\n\u0007\b\t\n\u000b\f\r\u000e\u000f\u0010\u00a8\u0006\u0011"}, d2={"Lorg/partiql/lang/eval/builtins/timestamp/PatternSymbol;", "Lorg/partiql/lang/eval/builtins/timestamp/FormatItem;", "()V", "field", "Lorg/partiql/lang/eval/builtins/timestamp/TimestampField;", "getField", "()Lorg/partiql/lang/eval/builtins/timestamp/TimestampField;", "Lorg/partiql/lang/eval/builtins/timestamp/YearPatternSymbol;", "Lorg/partiql/lang/eval/builtins/timestamp/MonthPatternSymbol;", "Lorg/partiql/lang/eval/builtins/timestamp/DayOfMonthPatternSymbol;", "Lorg/partiql/lang/eval/builtins/timestamp/HourOfDayPatternSymbol;", "Lorg/partiql/lang/eval/builtins/timestamp/MinuteOfHourPatternSymbol;", "Lorg/partiql/lang/eval/builtins/timestamp/SecondOfMinutePatternPatternSymbol;", "Lorg/partiql/lang/eval/builtins/timestamp/NanoOfSecondPatternSymbol;", "Lorg/partiql/lang/eval/builtins/timestamp/AmPmPatternSymbol;", "Lorg/partiql/lang/eval/builtins/timestamp/FractionOfSecondPatternSymbol;", "Lorg/partiql/lang/eval/builtins/timestamp/OffsetPatternSymbol;", "lang"})
public abstract class PatternSymbol
extends FormatItem {
    @NotNull
    public abstract TimestampField getField();

    private PatternSymbol() {
        super(null);
    }

    public /* synthetic */ PatternSymbol(DefaultConstructorMarker $constructor_marker) {
        this();
    }
}

