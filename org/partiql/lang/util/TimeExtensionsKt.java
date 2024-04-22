/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package org.partiql.lang.util;

import java.time.ZoneOffset;
import java.util.regex.Matcher;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Regex;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;
import org.partiql.lang.eval.ExceptionsKt;
import org.partiql.lang.util.PropertyMapHelpersKt;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=2, d1={"\u0000\"\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\b\n\u0002\b\b\n\u0002\u0010\u000e\n\u0002\b\u0002\u001a\u0010\u0010\u0015\u001a\u00020\u000e2\u0006\u0010\u0016\u001a\u00020\u0017H\u0000\u001a\f\u0010\u0018\u001a\u00020\u0017*\u00020\u0005H\u0000\"\u0014\u0010\u0000\u001a\u00020\u0001X\u0080\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0002\u0010\u0003\"\u001c\u0010\u0004\u001a\n \u0006*\u0004\u0018\u00010\u00050\u0005X\u0080\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0014\u0010\t\u001a\u00020\u0001X\u0080\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u0003\"\u0014\u0010\u000b\u001a\u00020\u0001X\u0080\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\u0003\"\u0018\u0010\r\u001a\u00020\u000e*\u00020\u00058@X\u0080\u0004\u00a2\u0006\u0006\u001a\u0004\b\u000f\u0010\u0010\"\u0018\u0010\u0011\u001a\u00020\u000e*\u00020\u00058@X\u0080\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0012\u0010\u0010\"\u0018\u0010\u0013\u001a\u00020\u000e*\u00020\u00058@X\u0080\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0014\u0010\u0010\u00a8\u0006\u0019"}, d2={"DATE_PATTERN_REGEX", "Lkotlin/text/Regex;", "getDATE_PATTERN_REGEX", "()Lkotlin/text/Regex;", "DEFAULT_TIMEZONE_OFFSET", "Ljava/time/ZoneOffset;", "kotlin.jvm.PlatformType", "getDEFAULT_TIMEZONE_OFFSET", "()Ljava/time/ZoneOffset;", "genericTimeRegex", "getGenericTimeRegex", "timeWithoutTimeZoneRegex", "getTimeWithoutTimeZoneRegex", "hour", "", "getHour", "(Ljava/time/ZoneOffset;)I", "minute", "getMinute", "totalMinutes", "getTotalMinutes", "getPrecisionFromTimeString", "timeString", "", "getOffsetHHmm", "lang"})
public final class TimeExtensionsKt {
    @NotNull
    private static final Regex timeWithoutTimeZoneRegex = new Regex("\\d\\d:\\d\\d:\\d\\d(\\.\\d*)?");
    @NotNull
    private static final Regex genericTimeRegex = new Regex("\\d\\d:\\d\\d:\\d\\d(\\.\\d*)?([+|-]\\d\\d:\\d\\d)?");
    @NotNull
    private static final Regex DATE_PATTERN_REGEX = new Regex("\\d\\d\\d\\d-\\d\\d-\\d\\d");
    private static final ZoneOffset DEFAULT_TIMEZONE_OFFSET = ZoneOffset.UTC;

    @NotNull
    public static final Regex getTimeWithoutTimeZoneRegex() {
        return timeWithoutTimeZoneRegex;
    }

    @NotNull
    public static final Regex getGenericTimeRegex() {
        return genericTimeRegex;
    }

    @NotNull
    public static final Regex getDATE_PATTERN_REGEX() {
        return DATE_PATTERN_REGEX;
    }

    public static final ZoneOffset getDEFAULT_TIMEZONE_OFFSET() {
        return DEFAULT_TIMEZONE_OFFSET;
    }

    @NotNull
    public static final String getOffsetHHmm(@NotNull ZoneOffset $this$getOffsetHHmm) {
        Intrinsics.checkParameterIsNotNull($this$getOffsetHHmm, "$this$getOffsetHHmm");
        int n = TimeExtensionsKt.getHour($this$getOffsetHHmm);
        StringBuilder stringBuilder = new StringBuilder().append($this$getOffsetHHmm.getTotalSeconds() >= 0 ? "+" : "-");
        boolean bl = false;
        int n2 = Math.abs(n);
        n = TimeExtensionsKt.getMinute($this$getOffsetHHmm);
        stringBuilder = stringBuilder.append(StringsKt.padStart(String.valueOf(n2), 2, '0')).append(":");
        bl = false;
        n2 = Math.abs(n);
        return stringBuilder.append(StringsKt.padStart(String.valueOf(n2), 2, '0')).toString();
    }

    public static final int getHour(@NotNull ZoneOffset $this$hour) {
        Intrinsics.checkParameterIsNotNull($this$hour, "$this$hour");
        return $this$hour.getTotalSeconds() / 3600;
    }

    public static final int getMinute(@NotNull ZoneOffset $this$minute) {
        Intrinsics.checkParameterIsNotNull($this$minute, "$this$minute");
        return $this$minute.getTotalSeconds() / 60 % 60;
    }

    public static final int getTotalMinutes(@NotNull ZoneOffset $this$totalMinutes) {
        Intrinsics.checkParameterIsNotNull($this$totalMinutes, "$this$totalMinutes");
        return $this$totalMinutes.getTotalSeconds() / 60;
    }

    public static final int getPrecisionFromTimeString(@NotNull String timeString) {
        String fraction;
        Intrinsics.checkParameterIsNotNull(timeString, "timeString");
        Matcher matcher = genericTimeRegex.toPattern().matcher(timeString);
        if (!matcher.find()) {
            Void void_ = ExceptionsKt.err("Time string does not match the format 'HH:MM:SS[.ddd....][+|-HH:MM]'", PropertyMapHelpersKt.propertyValueMapOf(new Pair[0]), false);
            throw null;
        }
        String string = matcher.group(1);
        String string2 = fraction = string != null ? StringsKt.removePrefix(string, (CharSequence)".") : null;
        return string2 != null ? string2.length() : 0;
    }
}

