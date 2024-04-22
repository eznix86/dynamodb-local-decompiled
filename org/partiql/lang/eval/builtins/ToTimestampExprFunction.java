/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package org.partiql.lang.eval.builtins;

import com.amazon.ion.IonString;
import com.amazon.ion.Timestamp;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.IntRange;
import org.jetbrains.annotations.NotNull;
import org.partiql.lang.errors.ErrorCode;
import org.partiql.lang.errors.PropertyValueMap;
import org.partiql.lang.eval.Environment;
import org.partiql.lang.eval.EvaluationException;
import org.partiql.lang.eval.ExceptionsKt;
import org.partiql.lang.eval.ExprValue;
import org.partiql.lang.eval.ExprValueFactory;
import org.partiql.lang.eval.NullPropagatingExprFunction;
import org.partiql.lang.eval.builtins.TimestampParser;
import org.partiql.lang.util.IonValueExtensionsKt;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0010\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u001e\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00060\nH\u0016J\u0016\u0010\u000b\u001a\u00020\f2\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00060\nH\u0002\u00a8\u0006\r"}, d2={"Lorg/partiql/lang/eval/builtins/ToTimestampExprFunction;", "Lorg/partiql/lang/eval/NullPropagatingExprFunction;", "valueFactory", "Lorg/partiql/lang/eval/ExprValueFactory;", "(Lorg/partiql/lang/eval/ExprValueFactory;)V", "eval", "Lorg/partiql/lang/eval/ExprValue;", "env", "Lorg/partiql/lang/eval/Environment;", "args", "", "validateArguments", "", "lang"})
public final class ToTimestampExprFunction
extends NullPropagatingExprFunction {
    /*
     * WARNING - void declaration
     */
    @Override
    @NotNull
    public ExprValue eval(@NotNull Environment env, @NotNull List<? extends ExprValue> args2) {
        Object object;
        Intrinsics.checkParameterIsNotNull(env, "env");
        Intrinsics.checkParameterIsNotNull(args2, "args");
        this.validateArguments(args2);
        Object object2 = args2;
        ExprValueFactory exprValueFactory = this.getValueFactory();
        boolean bl = false;
        int n = object2.size();
        ExprValueFactory exprValueFactory2 = exprValueFactory;
        switch (n) {
            case 1: {
                exprValueFactory = exprValueFactory2;
                try {
                    exprValueFactory2 = exprValueFactory;
                    object2 = Timestamp.valueOf(IonValueExtensionsKt.stringValue(args2.get(0).getIonValue()));
                } catch (IllegalArgumentException illegalArgumentException) {
                    void ex;
                    ExprValueFactory exprValueFactory3 = exprValueFactory;
                    throw (Throwable)new EvaluationException("Timestamp was not a valid ion timestamp", ErrorCode.EVALUATOR_ION_TIMESTAMP_PARSE_FAILURE, new PropertyValueMap(null, 1, null), (Throwable)ex, true);
                }
                object = object2;
                break;
            }
            default: {
                String string = IonValueExtensionsKt.stringValue(args2.get(0).getIonValue());
                if (string == null) {
                    Intrinsics.throwNpe();
                }
                String string2 = IonValueExtensionsKt.stringValue(args2.get(1).getIonValue());
                if (string2 == null) {
                    Intrinsics.throwNpe();
                }
                object = TimestampParser.Companion.parseTimestamp(string, string2);
            }
        }
        Intrinsics.checkExpressionValueIsNotNull(object, "when (args.count()) {\n  \u2026tringValue()!!)\n        }");
        return exprValueFactory2.newTimestamp((Timestamp)object);
    }

    private final void validateArguments(List<? extends ExprValue> args2) {
        if (!(args2.get(0).getIonValue() instanceof IonString)) {
            Void void_ = ExceptionsKt.errNoContext("First argument of to_timestamp is not a string.", false);
            throw null;
        }
        if (args2.size() == 2 && !(args2.get(1).getIonValue() instanceof IonString)) {
            Void void_ = ExceptionsKt.errNoContext("Second argument of to_timestamp is not a string.", false);
            throw null;
        }
    }

    public ToTimestampExprFunction(@NotNull ExprValueFactory valueFactory) {
        Intrinsics.checkParameterIsNotNull(valueFactory, "valueFactory");
        int n = 1;
        super("to_timestamp", new IntRange(n, 2), valueFactory);
    }
}

