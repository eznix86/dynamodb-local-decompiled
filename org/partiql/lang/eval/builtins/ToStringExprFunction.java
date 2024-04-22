/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package org.partiql.lang.eval.builtins;

import com.amazon.ion.IonText;
import com.amazon.ion.IonTimestamp;
import com.amazon.ion.Timestamp;
import java.time.DateTimeException;
import java.time.format.DateTimeFormatter;
import java.time.temporal.UnsupportedTemporalTypeException;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.partiql.lang.errors.ErrorCode;
import org.partiql.lang.errors.Property;
import org.partiql.lang.errors.PropertyValueMap;
import org.partiql.lang.eval.Environment;
import org.partiql.lang.eval.EvaluationException;
import org.partiql.lang.eval.ExceptionsKt;
import org.partiql.lang.eval.ExprValue;
import org.partiql.lang.eval.ExprValueExtensionsKt;
import org.partiql.lang.eval.ExprValueFactory;
import org.partiql.lang.eval.NullPropagatingExprFunction;
import org.partiql.lang.eval.builtins.TimestampTemporalAccessor;
import org.partiql.lang.util.IonValueExtensionsKt;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0001\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0010\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u001c\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\n\u0010\t\u001a\u00060\nj\u0002`\u000bH\u0002J\u001e\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\r0\u0011H\u0016J\u0016\u0010\u0012\u001a\u00020\u00132\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\r0\u0011H\u0002\u00a8\u0006\u0014"}, d2={"Lorg/partiql/lang/eval/builtins/ToStringExprFunction;", "Lorg/partiql/lang/eval/NullPropagatingExprFunction;", "valueFactory", "Lorg/partiql/lang/eval/ExprValueFactory;", "(Lorg/partiql/lang/eval/ExprValueFactory;)V", "errInvalidFormatPattern", "", "pattern", "", "cause", "Ljava/lang/Exception;", "Lkotlin/Exception;", "eval", "Lorg/partiql/lang/eval/ExprValue;", "env", "Lorg/partiql/lang/eval/Environment;", "args", "", "validateArguments", "", "lang"})
public final class ToStringExprFunction
extends NullPropagatingExprFunction {
    @Override
    @NotNull
    public ExprValue eval(@NotNull Environment env, @NotNull List<? extends ExprValue> args2) {
        DateTimeFormatter dateTimeFormatter;
        Intrinsics.checkParameterIsNotNull(env, "env");
        Intrinsics.checkParameterIsNotNull(args2, "args");
        this.validateArguments(args2);
        String string = IonValueExtensionsKt.stringValue(args2.get(1).getIonValue());
        if (string == null) {
            Intrinsics.throwNpe();
        }
        String pattern = string;
        try {
            DateTimeFormatter dateTimeFormatter2 = DateTimeFormatter.ofPattern(pattern);
            Intrinsics.checkExpressionValueIsNotNull(dateTimeFormatter2, "DateTimeFormatter.ofPattern(pattern)");
            dateTimeFormatter = dateTimeFormatter2;
        } catch (IllegalArgumentException ex) {
            Void void_ = this.errInvalidFormatPattern(pattern, ex);
            throw null;
        }
        DateTimeFormatter formatter = dateTimeFormatter;
        Timestamp timestamp = ExprValueExtensionsKt.timestampValue(args2.get(0));
        TimestampTemporalAccessor temporalAccessor = new TimestampTemporalAccessor(timestamp);
        try {
            ExprValueFactory exprValueFactory = this.getValueFactory();
            String string2 = formatter.format(temporalAccessor);
            Intrinsics.checkExpressionValueIsNotNull(string2, "formatter.format(temporalAccessor)");
            return exprValueFactory.newString(string2);
        } catch (UnsupportedTemporalTypeException ex) {
            Void void_ = this.errInvalidFormatPattern(pattern, ex);
            throw null;
        } catch (DateTimeException ex) {
            Void void_ = this.errInvalidFormatPattern(pattern, ex);
            throw null;
        }
    }

    private final void validateArguments(List<? extends ExprValue> args2) {
        if (!(args2.get(0).getIonValue() instanceof IonTimestamp)) {
            Void void_ = ExceptionsKt.errNoContext("First argument of to_string is not a timestamp.", false);
            throw null;
        }
        if (!(args2.get(1).getIonValue() instanceof IonText)) {
            Void void_ = ExceptionsKt.errNoContext("Second argument of to_string is not a string.", false);
            throw null;
        }
    }

    private final Void errInvalidFormatPattern(String pattern, Exception cause) {
        PropertyValueMap pvmap = new PropertyValueMap(null, 1, null);
        pvmap.set(Property.TIMESTAMP_FORMAT_PATTERN, pattern);
        throw (Throwable)new EvaluationException("Invalid DateTime format pattern", ErrorCode.EVALUATOR_INVALID_TIMESTAMP_FORMAT_PATTERN, pvmap, cause, false);
    }

    public ToStringExprFunction(@NotNull ExprValueFactory valueFactory) {
        Intrinsics.checkParameterIsNotNull(valueFactory, "valueFactory");
        super("to_string", 2, valueFactory);
    }
}

