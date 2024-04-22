/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package org.partiql.lang.eval.builtins;

import com.amazon.ion.Timestamp;
import java.time.temporal.ChronoField;
import java.time.temporal.IsoFields;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalField;
import java.time.temporal.UnsupportedTemporalTypeException;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.partiql.lang.eval.builtins.TimestampTemporalAccessorKt;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\b\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0012\u0010\u0007\u001a\u00020\b2\b\u0010\t\u001a\u0004\u0018\u00010\nH\u0016J\u0012\u0010\u000b\u001a\u00020\f2\b\u0010\t\u001a\u0004\u0018\u00010\nH\u0016R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006\r"}, d2={"Lorg/partiql/lang/eval/builtins/TimestampTemporalAccessor;", "Ljava/time/temporal/TemporalAccessor;", "ts", "Lcom/amazon/ion/Timestamp;", "(Lcom/amazon/ion/Timestamp;)V", "getTs", "()Lcom/amazon/ion/Timestamp;", "getLong", "", "field", "Ljava/time/temporal/TemporalField;", "isSupported", "", "lang"})
public final class TimestampTemporalAccessor
implements TemporalAccessor {
    @NotNull
    private final Timestamp ts;

    @Override
    public boolean isSupported(@Nullable TemporalField field) {
        TemporalField temporalField = field;
        return Intrinsics.areEqual(temporalField, IsoFields.QUARTER_OF_YEAR);
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public long getLong(@Nullable TemporalField field) {
        long l;
        if (field == null) {
            throw (Throwable)new IllegalArgumentException("argument 'field' may not be null");
        }
        TemporalField temporalField = field;
        if (temporalField == ChronoField.YEAR_OF_ERA) {
            l = this.ts.getYear();
        } else if (temporalField == ChronoField.MONTH_OF_YEAR) {
            l = this.ts.getMonth();
        } else if (temporalField == ChronoField.DAY_OF_MONTH) {
            l = this.ts.getDay();
        } else if (temporalField == ChronoField.HOUR_OF_DAY) {
            l = this.ts.getHour();
        } else if (temporalField == ChronoField.SECOND_OF_MINUTE) {
            l = this.ts.getSecond();
        } else if (temporalField == ChronoField.MINUTE_OF_HOUR) {
            l = this.ts.getMinute();
        } else if (temporalField == ChronoField.MILLI_OF_SECOND) {
            l = TimestampTemporalAccessorKt.access$getMilliOfSecond$p(this.ts);
        } else if (temporalField == ChronoField.NANO_OF_SECOND) {
            l = TimestampTemporalAccessorKt.access$getNanoOfSecond$p(this.ts);
        } else if (temporalField == ChronoField.AMPM_OF_DAY) {
            l = (long)this.ts.getHour() / 12L;
        } else if (temporalField == ChronoField.CLOCK_HOUR_OF_AMPM) {
            void var3_3;
            long hourOfAmPm = (long)this.ts.getHour() % 12L;
            l = hourOfAmPm == 0L ? 12L : var3_3;
        } else if (temporalField == ChronoField.OFFSET_SECONDS) {
            l = this.ts.getLocalOffset() == null ? 0L : (long)this.ts.getLocalOffset().intValue() * 60L;
        } else {
            throw (Throwable)new UnsupportedTemporalTypeException(field.getClass().getName() + "." + ((Object)field).toString() + " not supported");
        }
        return l;
    }

    @NotNull
    public final Timestamp getTs() {
        return this.ts;
    }

    public TimestampTemporalAccessor(@NotNull Timestamp ts) {
        Intrinsics.checkParameterIsNotNull(ts, "ts");
        this.ts = ts;
    }
}

