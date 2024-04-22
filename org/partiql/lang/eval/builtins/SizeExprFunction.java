/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package org.partiql.lang.eval.builtins;

import com.amazon.ion.IonContainer;
import com.amazon.ion.IonValue;
import java.util.List;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.partiql.lang.errors.ErrorCode;
import org.partiql.lang.errors.Property;
import org.partiql.lang.errors.PropertyValueMap;
import org.partiql.lang.eval.Environment;
import org.partiql.lang.eval.ExceptionsKt;
import org.partiql.lang.eval.ExprValue;
import org.partiql.lang.eval.ExprValueFactory;
import org.partiql.lang.eval.NullPropagatingExprFunction;
import org.partiql.lang.eval.builtins.SizeExprFunction$WhenMappings;
import org.partiql.lang.util.IonValueExtensionsKt;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0000\b\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u001e\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00060\nH\u0016\u00a8\u0006\u000b"}, d2={"Lorg/partiql/lang/eval/builtins/SizeExprFunction;", "Lorg/partiql/lang/eval/NullPropagatingExprFunction;", "valueFactory", "Lorg/partiql/lang/eval/ExprValueFactory;", "(Lorg/partiql/lang/eval/ExprValueFactory;)V", "eval", "Lorg/partiql/lang/eval/ExprValue;", "env", "Lorg/partiql/lang/eval/Environment;", "args", "", "lang"})
public final class SizeExprFunction
extends NullPropagatingExprFunction {
    @Override
    @NotNull
    public ExprValue eval(@NotNull Environment env, @NotNull List<? extends ExprValue> args2) {
        IonContainer ionCol;
        Intrinsics.checkParameterIsNotNull(env, "env");
        Intrinsics.checkParameterIsNotNull(args2, "args");
        ExprValue collection = CollectionsKt.first(args2);
        switch (SizeExprFunction$WhenMappings.$EnumSwitchMapping$0[collection.getType().ordinal()]) {
            case 1: 
            case 2: 
            case 3: 
            case 4: {
                IonValue ionValue2 = collection.getIonValue();
                if (ionValue2 == null) {
                    throw new TypeCastException("null cannot be cast to non-null type com.amazon.ion.IonContainer");
                }
                ionCol = (IonContainer)ionValue2;
                break;
            }
            default: {
                PropertyValueMap errorContext = new PropertyValueMap(null, 1, null);
                errorContext.set(Property.EXPECTED_ARGUMENT_TYPES, "LIST or BAG or STRUCT");
                errorContext.set(Property.ACTUAL_ARGUMENT_TYPES, collection.getType().name());
                errorContext.set(Property.FUNCTION_NAME, "size");
                Void void_ = ExceptionsKt.err("invalid argument type for size", ErrorCode.EVALUATOR_INCORRECT_TYPE_OF_ARGUMENTS_TO_FUNC_CALL, errorContext, false);
                throw null;
            }
        }
        return this.getValueFactory().newInt(IonValueExtensionsKt.getSize(ionCol));
    }

    public SizeExprFunction(@NotNull ExprValueFactory valueFactory) {
        Intrinsics.checkParameterIsNotNull(valueFactory, "valueFactory");
        super("size", 1, valueFactory);
    }
}

