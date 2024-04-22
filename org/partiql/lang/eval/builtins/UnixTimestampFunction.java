/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package org.partiql.lang.eval.builtins;

import com.amazon.ion.Timestamp;
import java.math.BigDecimal;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.IntRange;
import org.jetbrains.annotations.NotNull;
import org.partiql.lang.eval.Environment;
import org.partiql.lang.eval.ExprValue;
import org.partiql.lang.eval.ExprValueExtensionsKt;
import org.partiql.lang.eval.ExprValueFactory;
import org.partiql.lang.eval.NullPropagatingExprFunction;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0000\b\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u001e\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\b0\fH\u0016R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\r"}, d2={"Lorg/partiql/lang/eval/builtins/UnixTimestampFunction;", "Lorg/partiql/lang/eval/NullPropagatingExprFunction;", "valueFactory", "Lorg/partiql/lang/eval/ExprValueFactory;", "(Lorg/partiql/lang/eval/ExprValueFactory;)V", "millisPerSecond", "Ljava/math/BigDecimal;", "eval", "Lorg/partiql/lang/eval/ExprValue;", "env", "Lorg/partiql/lang/eval/Environment;", "args", "", "lang"})
public final class UnixTimestampFunction
extends NullPropagatingExprFunction {
    private final BigDecimal millisPerSecond;

    @Override
    @NotNull
    public ExprValue eval(@NotNull Environment env, @NotNull List<? extends ExprValue> args2) {
        Intrinsics.checkParameterIsNotNull(env, "env");
        Intrinsics.checkParameterIsNotNull(args2, "args");
        Timestamp timestamp = args2.isEmpty() ? env.getSession().getNow() : ExprValueExtensionsKt.timestampValue(args2.get(0));
        BigDecimal numMillis = timestamp.getDecimalMillis();
        BigDecimal epochTime = numMillis.divide(this.millisPerSecond);
        if (timestamp.getDecimalSecond().scale() == 0 || args2.isEmpty()) {
            return this.getValueFactory().newInt(epochTime.longValue());
        }
        ExprValueFactory exprValueFactory = this.getValueFactory();
        BigDecimal bigDecimal = epochTime;
        Intrinsics.checkExpressionValueIsNotNull(bigDecimal, "epochTime");
        return exprValueFactory.newDecimal(bigDecimal);
    }

    public UnixTimestampFunction(@NotNull ExprValueFactory valueFactory) {
        Intrinsics.checkParameterIsNotNull(valueFactory, "valueFactory");
        int n = 0;
        super("unix_timestamp", new IntRange(n, 1), valueFactory);
        this.millisPerSecond = new BigDecimal(1000);
    }
}

