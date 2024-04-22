/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package org.partiql.lang.eval.builtins.timestamp;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;
import org.partiql.lang.errors.ErrorCode;
import org.partiql.lang.errors.Property;
import org.partiql.lang.eval.EvaluationException;
import org.partiql.lang.eval.builtins.timestamp.AmPmPatternSymbol;
import org.partiql.lang.eval.builtins.timestamp.DayOfMonthPatternSymbol;
import org.partiql.lang.eval.builtins.timestamp.FormatItem;
import org.partiql.lang.eval.builtins.timestamp.FormatPattern;
import org.partiql.lang.eval.builtins.timestamp.FractionOfSecondPatternSymbol;
import org.partiql.lang.eval.builtins.timestamp.HourOfDayFormatFieldFormat;
import org.partiql.lang.eval.builtins.timestamp.HourOfDayPatternSymbol;
import org.partiql.lang.eval.builtins.timestamp.MinuteOfHourPatternSymbol;
import org.partiql.lang.eval.builtins.timestamp.MonthFormat;
import org.partiql.lang.eval.builtins.timestamp.MonthPatternSymbol;
import org.partiql.lang.eval.builtins.timestamp.NanoOfSecondPatternSymbol;
import org.partiql.lang.eval.builtins.timestamp.OffsetFieldFormat;
import org.partiql.lang.eval.builtins.timestamp.OffsetPatternSymbol;
import org.partiql.lang.eval.builtins.timestamp.SecondOfMinutePatternPatternSymbol;
import org.partiql.lang.eval.builtins.timestamp.TextItem;
import org.partiql.lang.eval.builtins.timestamp.TimestampFieldFormat;
import org.partiql.lang.eval.builtins.timestamp.TimestampFormatPatternLexer;
import org.partiql.lang.eval.builtins.timestamp.TimestampFormatPatternParser$WhenMappings;
import org.partiql.lang.eval.builtins.timestamp.Token;
import org.partiql.lang.eval.builtins.timestamp.YearFormat;
import org.partiql.lang.eval.builtins.timestamp.YearPatternSymbol;
import org.partiql.lang.util.PropertyMapHelpersKt;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0000\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u0006H\u0002\u00a8\u0006\n"}, d2={"Lorg/partiql/lang/eval/builtins/timestamp/TimestampFormatPatternParser;", "", "()V", "parse", "Lorg/partiql/lang/eval/builtins/timestamp/FormatPattern;", "formatPatternString", "", "parsePattern", "Lorg/partiql/lang/eval/builtins/timestamp/FormatItem;", "raw", "lang"})
public final class TimestampFormatPatternParser {
    /*
     * WARNING - void declaration
     */
    @NotNull
    public final FormatPattern parse(@NotNull String formatPatternString) {
        void $this$mapTo$iv$iv;
        Intrinsics.checkParameterIsNotNull(formatPatternString, "formatPatternString");
        TimestampFormatPatternLexer lexer = new TimestampFormatPatternLexer();
        List<Token> tokens = lexer.tokenize(formatPatternString);
        int patternCounter = 0;
        Iterable $this$map$iv = tokens;
        boolean $i$f$map = false;
        Iterable iterable = $this$map$iv;
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
        boolean $i$f$mapTo = false;
        for (Object item$iv$iv : $this$mapTo$iv$iv) {
            FormatItem formatItem;
            void token;
            Token token2 = (Token)item$iv$iv;
            Collection collection = destination$iv$iv;
            boolean bl = false;
            switch (TimestampFormatPatternParser$WhenMappings.$EnumSwitchMapping$0[token.getTokenType().ordinal()]) {
                case 1: {
                    formatItem = new TextItem(token.getValue());
                    break;
                }
                case 2: {
                    ++patternCounter;
                    formatItem = this.parsePattern(token.getValue());
                    break;
                }
                default: {
                    throw new NoWhenBranchMatchedException();
                }
            }
            FormatItem formatItem2 = formatItem;
            collection.add(formatItem2);
        }
        List formatItems = (List)destination$iv$iv;
        return new FormatPattern(formatPatternString, formatItems);
    }

    private final FormatItem parsePattern(String raw) {
        FormatItem formatItem;
        switch (raw) {
            case "y": {
                formatItem = new YearPatternSymbol(YearFormat.FOUR_DIGIT);
                break;
            }
            case "yy": {
                formatItem = new YearPatternSymbol(YearFormat.TWO_DIGIT);
                break;
            }
            case "yyy": 
            case "yyyy": {
                formatItem = new YearPatternSymbol(YearFormat.FOUR_DIGIT_ZERO_PADDED);
                break;
            }
            case "M": {
                formatItem = new MonthPatternSymbol(MonthFormat.MONTH_NUMBER);
                break;
            }
            case "MM": {
                formatItem = new MonthPatternSymbol(MonthFormat.MONTH_NUMBER_ZERO_PADDED);
                break;
            }
            case "MMM": {
                formatItem = new MonthPatternSymbol(MonthFormat.ABBREVIATED_MONTH_NAME);
                break;
            }
            case "MMMM": {
                formatItem = new MonthPatternSymbol(MonthFormat.FULL_MONTH_NAME);
                break;
            }
            case "MMMMM": {
                formatItem = new MonthPatternSymbol(MonthFormat.FIRST_LETTER_OF_MONTH_NAME);
                break;
            }
            case "d": {
                formatItem = new DayOfMonthPatternSymbol(TimestampFieldFormat.NUMBER);
                break;
            }
            case "dd": {
                formatItem = new DayOfMonthPatternSymbol(TimestampFieldFormat.ZERO_PADDED_NUMBER);
                break;
            }
            case "H": {
                formatItem = new HourOfDayPatternSymbol(HourOfDayFormatFieldFormat.NUMBER_24_HOUR);
                break;
            }
            case "HH": {
                formatItem = new HourOfDayPatternSymbol(HourOfDayFormatFieldFormat.ZERO_PADDED_NUMBER_24_HOUR);
                break;
            }
            case "h": {
                formatItem = new HourOfDayPatternSymbol(HourOfDayFormatFieldFormat.NUMBER_12_HOUR);
                break;
            }
            case "hh": {
                formatItem = new HourOfDayPatternSymbol(HourOfDayFormatFieldFormat.ZERO_PADDED_NUMBER_12_HOUR);
                break;
            }
            case "a": {
                formatItem = new AmPmPatternSymbol();
                break;
            }
            case "m": {
                formatItem = new MinuteOfHourPatternSymbol(TimestampFieldFormat.NUMBER);
                break;
            }
            case "mm": {
                formatItem = new MinuteOfHourPatternSymbol(TimestampFieldFormat.ZERO_PADDED_NUMBER);
                break;
            }
            case "s": {
                formatItem = new SecondOfMinutePatternPatternSymbol(TimestampFieldFormat.NUMBER);
                break;
            }
            case "ss": {
                formatItem = new SecondOfMinutePatternPatternSymbol(TimestampFieldFormat.ZERO_PADDED_NUMBER);
                break;
            }
            case "n": {
                formatItem = new NanoOfSecondPatternSymbol();
                break;
            }
            case "X": {
                formatItem = new OffsetPatternSymbol(OffsetFieldFormat.ZERO_PADDED_HOUR_OR_Z);
                break;
            }
            case "XX": 
            case "XXXX": {
                formatItem = new OffsetPatternSymbol(OffsetFieldFormat.ZERO_PADDED_HOUR_MINUTE_OR_Z);
                break;
            }
            case "XXX": 
            case "XXXXX": {
                formatItem = new OffsetPatternSymbol(OffsetFieldFormat.ZERO_PADDED_HOUR_COLON_MINUTE_OR_Z);
                break;
            }
            case "x": {
                formatItem = new OffsetPatternSymbol(OffsetFieldFormat.ZERO_PADDED_HOUR);
                break;
            }
            case "xx": 
            case "xxxx": {
                formatItem = new OffsetPatternSymbol(OffsetFieldFormat.ZERO_PADDED_HOUR_MINUTE);
                break;
            }
            case "xxxxx": 
            case "xxx": {
                formatItem = new OffsetPatternSymbol(OffsetFieldFormat.ZERO_PADDED_HOUR_COLON_MINUTE);
                break;
            }
            default: {
                if (StringsKt.first(raw) != 'S') {
                    throw (Throwable)new EvaluationException("Invalid symbol in timestamp format pattern", ErrorCode.EVALUATOR_INVALID_TIMESTAMP_FORMAT_PATTERN_SYMBOL, PropertyMapHelpersKt.propertyValueMapOf(PropertyMapHelpersKt.to(Property.TIMESTAMP_FORMAT_PATTERN, raw)), null, false, 8, null);
                }
                formatItem = new FractionOfSecondPatternSymbol(raw.length());
            }
        }
        return formatItem;
    }
}

