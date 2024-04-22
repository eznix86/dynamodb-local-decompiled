/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package org.partiql.lang.eval.builtins;

import java.time.DateTimeException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.partiql.lang.errors.ErrorCode;
import org.partiql.lang.errors.Property;
import org.partiql.lang.eval.Environment;
import org.partiql.lang.eval.ExceptionsKt;
import org.partiql.lang.eval.ExprValue;
import org.partiql.lang.eval.ExprValueExtensionsKt;
import org.partiql.lang.eval.ExprValueFactory;
import org.partiql.lang.eval.ExprValueType;
import org.partiql.lang.eval.NullPropagatingExprFunction;
import org.partiql.lang.util.PropertyMapHelpersKt;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0000\b\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u001e\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00060\nH\u0016\u00a8\u0006\u000b"}, d2={"Lorg/partiql/lang/eval/builtins/MakeDateExprFunction;", "Lorg/partiql/lang/eval/NullPropagatingExprFunction;", "valueFactory", "Lorg/partiql/lang/eval/ExprValueFactory;", "(Lorg/partiql/lang/eval/ExprValueFactory;)V", "eval", "Lorg/partiql/lang/eval/ExprValue;", "env", "Lorg/partiql/lang/eval/Environment;", "args", "", "lang"})
public final class MakeDateExprFunction
extends NullPropagatingExprFunction {
    /*
     * WARNING - void declaration
     */
    @Override
    @NotNull
    public ExprValue eval(@NotNull Environment env, @NotNull List<? extends ExprValue> args2) {
        void month;
        void year2;
        void $this$mapTo$iv$iv;
        Object object;
        Collection collection;
        Object item$iv$iv2;
        void $this$mapTo$iv$iv2;
        Intrinsics.checkParameterIsNotNull(env, "env");
        Intrinsics.checkParameterIsNotNull(args2, "args");
        Iterable $this$map$iv22 = args2;
        int $i$f$map = 0;
        Iterable iterable = $this$map$iv22;
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv22, 10));
        boolean $i$f$mapTo = false;
        for (Object item$iv$iv2 : $this$mapTo$iv$iv2) {
            void it;
            ExprValue exprValue2 = (ExprValue)item$iv$iv2;
            collection = destination$iv$iv;
            boolean bl = false;
            if (it.getType() != ExprValueType.INT) {
                Void void_ = ExceptionsKt.err("Invalid argument type for make_date", ErrorCode.EVALUATOR_INCORRECT_TYPE_OF_ARGUMENTS_TO_FUNC_CALL, PropertyMapHelpersKt.propertyValueMapOf(TuplesKt.to(Property.EXPECTED_ARGUMENT_TYPES, "INT"), TuplesKt.to(Property.FUNCTION_NAME, "make_date"), TuplesKt.to(Property.ACTUAL_ARGUMENT_TYPES, it.getType().name())), false);
                throw null;
            }
            object = Unit.INSTANCE;
            collection.add(object);
        }
        List cfr_ignored_0 = (List)destination$iv$iv;
        Iterable $this$map$iv = args2;
        boolean $i$f$map2 = false;
        item$iv$iv2 = $this$map$iv;
        Collection destination$iv$iv2 = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
        boolean $i$f$mapTo2 = false;
        for (Object item$iv$iv3 : $this$mapTo$iv$iv) {
            void it;
            ExprValue exprValue3 = (ExprValue)item$iv$iv3;
            collection = destination$iv$iv2;
            boolean bl = false;
            object = ExprValueExtensionsKt.intValue((ExprValue)it);
            collection.add(object);
        }
        Collection collection2 = destination$iv$iv = (List)destination$iv$iv2;
        boolean bl = false;
        int $this$map$iv22 = ((Number)collection2.get(0)).intValue();
        collection2 = destination$iv$iv;
        bl = false;
        $i$f$map = ((Number)collection2.get(1)).intValue();
        collection2 = destination$iv$iv;
        bl = false;
        int day = ((Number)collection2.get(2)).intValue();
        try {
            return this.getValueFactory().newDate((int)year2, (int)month, day);
        } catch (DateTimeException e) {
            Void void_ = ExceptionsKt.err("Date field value out of range. " + (int)year2 + '-' + (int)month + '-' + day, ErrorCode.EVALUATOR_DATE_FIELD_OUT_OF_RANGE, null, false);
            throw null;
        }
    }

    public MakeDateExprFunction(@NotNull ExprValueFactory valueFactory) {
        Intrinsics.checkParameterIsNotNull(valueFactory, "valueFactory");
        super("make_date", 3, valueFactory);
    }
}

