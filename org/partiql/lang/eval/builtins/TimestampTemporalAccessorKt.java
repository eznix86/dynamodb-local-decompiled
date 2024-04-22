/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package org.partiql.lang.eval.builtins;

import com.amazon.ion.Timestamp;
import java.math.BigDecimal;
import kotlin.Metadata;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=2, d1={"\u0000\u0018\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0082D\u00a2\u0006\u0002\n\u0000\"\u0016\u0010\u0002\u001a\n \u0004*\u0004\u0018\u00010\u00030\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\"\u000e\u0010\u0005\u001a\u00020\u0001X\u0082D\u00a2\u0006\u0002\n\u0000\"\u0016\u0010\u0006\u001a\n \u0004*\u0004\u0018\u00010\u00030\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\"\u0018\u0010\u0007\u001a\u00020\u0001*\u00020\b8BX\u0082\u0004\u00a2\u0006\u0006\u001a\u0004\b\t\u0010\n\"\u0018\u0010\u000b\u001a\u00020\u0001*\u00020\b8BX\u0082\u0004\u00a2\u0006\u0006\u001a\u0004\b\f\u0010\n\u00a8\u0006\r"}, d2={"MILLIS_PER_SECOND", "", "MILLIS_PER_SECOND_BD", "Ljava/math/BigDecimal;", "kotlin.jvm.PlatformType", "NANOS_PER_SECOND", "NANOS_PER_SECOND_BD", "milliOfSecond", "Lcom/amazon/ion/Timestamp;", "getMilliOfSecond", "(Lcom/amazon/ion/Timestamp;)J", "nanoOfSecond", "getNanoOfSecond", "lang"})
public final class TimestampTemporalAccessorKt {
    private static final long NANOS_PER_SECOND = 1000000000L;
    private static final long MILLIS_PER_SECOND = 1000L;
    private static final BigDecimal MILLIS_PER_SECOND_BD;
    private static final BigDecimal NANOS_PER_SECOND_BD;

    private static final long getNanoOfSecond(@NotNull Timestamp $this$nanoOfSecond) {
        return $this$nanoOfSecond.getDecimalSecond().multiply(NANOS_PER_SECOND_BD).longValue() % NANOS_PER_SECOND;
    }

    private static final long getMilliOfSecond(@NotNull Timestamp $this$milliOfSecond) {
        return $this$milliOfSecond.getDecimalSecond().multiply(MILLIS_PER_SECOND_BD).longValue() % MILLIS_PER_SECOND;
    }

    static {
        NANOS_PER_SECOND = 1000000000L;
        MILLIS_PER_SECOND = 1000L;
        MILLIS_PER_SECOND_BD = BigDecimal.valueOf(MILLIS_PER_SECOND);
        NANOS_PER_SECOND_BD = BigDecimal.valueOf(NANOS_PER_SECOND);
    }

    public static final /* synthetic */ long access$getMilliOfSecond$p(Timestamp $this$access_u24milliOfSecond_u24p) {
        return TimestampTemporalAccessorKt.getMilliOfSecond($this$access_u24milliOfSecond_u24p);
    }

    public static final /* synthetic */ long access$getNanoOfSecond$p(Timestamp $this$access_u24nanoOfSecond_u24p) {
        return TimestampTemporalAccessorKt.getNanoOfSecond($this$access_u24nanoOfSecond_u24p);
    }
}

