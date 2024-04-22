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
import org.jetbrains.annotations.NotNull;
import org.partiql.lang.eval.Environment;
import org.partiql.lang.eval.ExprValue;
import org.partiql.lang.eval.ExprValueExtensionsKt;
import org.partiql.lang.eval.ExprValueFactory;
import org.partiql.lang.eval.NullPropagatingExprFunction;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0000\b\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u001e\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\b0\fH\u0016R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\r"}, d2={"Lorg/partiql/lang/eval/builtins/FromUnixTimeFunction;", "Lorg/partiql/lang/eval/NullPropagatingExprFunction;", "valueFactory", "Lorg/partiql/lang/eval/ExprValueFactory;", "(Lorg/partiql/lang/eval/ExprValueFactory;)V", "millisPerSecond", "Ljava/math/BigDecimal;", "eval", "Lorg/partiql/lang/eval/ExprValue;", "env", "Lorg/partiql/lang/eval/Environment;", "args", "", "lang"})
public final class FromUnixTimeFunction
extends NullPropagatingExprFunction {
    private final BigDecimal millisPerSecond;

    @Override
    @NotNull
    public ExprValue eval(@NotNull Environment env, @NotNull List<? extends ExprValue> args2) {
        BigDecimal unixTimestamp;
        Intrinsics.checkParameterIsNotNull(env, "env");
        Intrinsics.checkParameterIsNotNull(args2, "args");
        BigDecimal bigDecimal = unixTimestamp = ExprValueExtensionsKt.bigDecimalValue(args2.get(0));
        BigDecimal bigDecimal2 = this.millisPerSecond;
        boolean bl = false;
        BigDecimal bigDecimal3 = bigDecimal.multiply(bigDecimal2);
        Intrinsics.checkExpressionValueIsNotNull(bigDecimal3, "this.multiply(other)");
        BigDecimal numMillis = bigDecimal3.stripTrailingZeros();
        Timestamp timestamp = Timestamp.forMillis(numMillis, null);
        ExprValueFactory exprValueFactory = this.getValueFactory();
        Timestamp timestamp2 = timestamp;
        Intrinsics.checkExpressionValueIsNotNull(timestamp2, "timestamp");
        return exprValueFactory.newTimestamp(timestamp2);
    }

    public FromUnixTimeFunction(@NotNull ExprValueFactory valueFactory) {
        Intrinsics.checkParameterIsNotNull(valueFactory, "valueFactory");
        super("from_unixtime", 1, valueFactory);
        this.millisPerSecond = new BigDecimal(1000);
    }
}

