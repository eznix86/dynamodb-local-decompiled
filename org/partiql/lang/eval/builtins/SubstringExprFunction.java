/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package org.partiql.lang.eval.builtins;

import java.util.List;
import kotlin.Metadata;
import kotlin.Triple;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.IntRange;
import org.jetbrains.annotations.NotNull;
import org.partiql.lang.eval.Environment;
import org.partiql.lang.eval.ExceptionsKt;
import org.partiql.lang.eval.ExprValue;
import org.partiql.lang.eval.ExprValueExtensionsKt;
import org.partiql.lang.eval.ExprValueFactory;
import org.partiql.lang.eval.ExprValueType;
import org.partiql.lang.eval.NullPropagatingExprFunction;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0010\b\n\u0000\b\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u001e\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00060\nH\u0016J*\u0010\u000b\u001a\u0016\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\u000e\u0012\u0006\u0012\u0004\u0018\u00010\u000e0\f2\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00060\nH\u0002\u00a8\u0006\u000f"}, d2={"Lorg/partiql/lang/eval/builtins/SubstringExprFunction;", "Lorg/partiql/lang/eval/NullPropagatingExprFunction;", "valueFactory", "Lorg/partiql/lang/eval/ExprValueFactory;", "(Lorg/partiql/lang/eval/ExprValueFactory;)V", "eval", "Lorg/partiql/lang/eval/ExprValue;", "env", "Lorg/partiql/lang/eval/Environment;", "args", "", "extractArguments", "Lkotlin/Triple;", "", "", "lang"})
public final class SubstringExprFunction
extends NullPropagatingExprFunction {
    /*
     * WARNING - void declaration
     */
    @Override
    @NotNull
    public ExprValue eval(@NotNull Environment env, @NotNull List<? extends ExprValue> args2) {
        int adjustedStartPosition;
        void startPosition;
        void target;
        Intrinsics.checkParameterIsNotNull(env, "env");
        Intrinsics.checkParameterIsNotNull(args2, "args");
        Triple<String, Integer, Integer> triple = this.extractArguments(args2);
        String string = triple.component1();
        int n = ((Number)triple.component2()).intValue();
        Integer quantity = triple.component3();
        void var7_8 = target;
        int n2 = 0;
        int n3 = target.length();
        boolean bl = false;
        void v0 = var7_8;
        if (v0 == null) {
            throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
        }
        int codePointCount = v0.codePointCount(n2, n3);
        if (startPosition > codePointCount) {
            return this.getValueFactory().newString("");
        }
        Integer n4 = quantity;
        int endPosition = n4 == null ? codePointCount : Integer.min(codePointCount, (int)(startPosition + quantity - true));
        if (endPosition < (adjustedStartPosition = Integer.max(0, (int)(startPosition - true)))) {
            return this.getValueFactory().newString("");
        }
        void var10_15 = target;
        int n5 = 0;
        int n6 = 0;
        void v1 = var10_15;
        if (v1 == null) {
            throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
        }
        int byteIndexStart = v1.offsetByCodePoints(n5, adjustedStartPosition);
        void var11_18 = target;
        n6 = 0;
        boolean bl2 = false;
        void v2 = var11_18;
        if (v2 == null) {
            throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
        }
        int byteIndexEnd = v2.offsetByCodePoints(n6, endPosition);
        var11_18 = target;
        ExprValueFactory exprValueFactory = this.getValueFactory();
        n6 = 0;
        void v3 = var11_18;
        if (v3 == null) {
            throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
        }
        String string2 = v3.substring(byteIndexStart, byteIndexEnd);
        Intrinsics.checkExpressionValueIsNotNull(string2, "(this as java.lang.Strin\u2026ing(startIndex, endIndex)");
        String string3 = string2;
        return exprValueFactory.newString(string3);
    }

    private final Triple<String, Integer, Integer> extractArguments(List<? extends ExprValue> args2) {
        Integer quantity;
        if (args2.get(0).getType() != ExprValueType.STRING) {
            Void void_ = ExceptionsKt.errNoContext("Argument 1 of substring was not STRING.", false);
            throw null;
        }
        if (args2.get(1).getType() != ExprValueType.INT) {
            Void void_ = ExceptionsKt.errNoContext("Argument 2 of substring was not INT.", false);
            throw null;
        }
        if (args2.size() == 3 && args2.get(2).getType() != ExprValueType.INT) {
            Void void_ = ExceptionsKt.errNoContext("Argument 3 of substring was not INT.", false);
            throw null;
        }
        String target = ExprValueExtensionsKt.stringValue(args2.get(0));
        int startPosition = ExprValueExtensionsKt.intValue(args2.get(1));
        switch (args2.size()) {
            case 3: {
                Integer n = ExprValueExtensionsKt.intValue(args2.get(2));
                break;
            }
            default: {
                Integer n = quantity = null;
            }
        }
        if (quantity != null && quantity < 0) {
            Void void_ = ExceptionsKt.errNoContext("Argument 3 of substring has to be greater than 0.", false);
            throw null;
        }
        return new Triple<String, Integer, Integer>(target, startPosition, quantity);
    }

    public SubstringExprFunction(@NotNull ExprValueFactory valueFactory) {
        Intrinsics.checkParameterIsNotNull(valueFactory, "valueFactory");
        int n = 2;
        super("substring", new IntRange(n, 3), valueFactory);
    }
}

