/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package org.partiql.lang.eval.builtins.timestamp;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.partiql.lang.eval.builtins.timestamp.MonthFormat;
import org.partiql.lang.eval.builtins.timestamp.PatternSymbol;
import org.partiql.lang.eval.builtins.timestamp.TimestampField;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0080\b\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\t\u0010\u000b\u001a\u00020\u0003H\u00c6\u0003J\u0013\u0010\f\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u0003H\u00c6\u0001J\u0013\u0010\r\u001a\u00020\u000e2\b\u0010\u000f\u001a\u0004\u0018\u00010\u0010H\u00d6\u0003J\t\u0010\u0011\u001a\u00020\u0012H\u00d6\u0001J\t\u0010\u0013\u001a\u00020\u0014H\u00d6\u0001R\u0014\u0010\u0005\u001a\u00020\u0006X\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n\u00a8\u0006\u0015"}, d2={"Lorg/partiql/lang/eval/builtins/timestamp/MonthPatternSymbol;", "Lorg/partiql/lang/eval/builtins/timestamp/PatternSymbol;", "format", "Lorg/partiql/lang/eval/builtins/timestamp/MonthFormat;", "(Lorg/partiql/lang/eval/builtins/timestamp/MonthFormat;)V", "field", "Lorg/partiql/lang/eval/builtins/timestamp/TimestampField;", "getField", "()Lorg/partiql/lang/eval/builtins/timestamp/TimestampField;", "getFormat", "()Lorg/partiql/lang/eval/builtins/timestamp/MonthFormat;", "component1", "copy", "equals", "", "other", "", "hashCode", "", "toString", "", "lang"})
public final class MonthPatternSymbol
extends PatternSymbol {
    @NotNull
    private final TimestampField field;
    @NotNull
    private final MonthFormat format;

    @Override
    @NotNull
    public TimestampField getField() {
        return this.field;
    }

    @NotNull
    public final MonthFormat getFormat() {
        return this.format;
    }

    public MonthPatternSymbol(@NotNull MonthFormat format) {
        Intrinsics.checkParameterIsNotNull((Object)format, "format");
        super(null);
        this.format = format;
        this.field = TimestampField.MONTH_OF_YEAR;
    }

    @NotNull
    public final MonthFormat component1() {
        return this.format;
    }

    @NotNull
    public final MonthPatternSymbol copy(@NotNull MonthFormat format) {
        Intrinsics.checkParameterIsNotNull((Object)format, "format");
        return new MonthPatternSymbol(format);
    }

    public static /* synthetic */ MonthPatternSymbol copy$default(MonthPatternSymbol monthPatternSymbol, MonthFormat monthFormat, int n, Object object) {
        if ((n & 1) != 0) {
            monthFormat = monthPatternSymbol.format;
        }
        return monthPatternSymbol.copy(monthFormat);
    }

    @NotNull
    public String toString() {
        return "MonthPatternSymbol(format=" + (Object)((Object)this.format) + ")";
    }

    public int hashCode() {
        MonthFormat monthFormat = this.format;
        return monthFormat != null ? ((Object)((Object)monthFormat)).hashCode() : 0;
    }

    public boolean equals(@Nullable Object object) {
        block3: {
            block2: {
                if (this == object) break block2;
                if (!(object instanceof MonthPatternSymbol)) break block3;
                MonthPatternSymbol monthPatternSymbol = (MonthPatternSymbol)object;
                if (!Intrinsics.areEqual((Object)this.format, (Object)monthPatternSymbol.format)) break block3;
            }
            return true;
        }
        return false;
    }
}

