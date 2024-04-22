/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package org.partiql.lang.eval.builtins;

import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.SequencesKt;
import org.jetbrains.annotations.NotNull;
import org.partiql.lang.eval.Environment;
import org.partiql.lang.eval.ExceptionsKt;
import org.partiql.lang.eval.ExprFunction;
import org.partiql.lang.eval.ExprValue;
import org.partiql.lang.eval.ExprValueExtensionsKt;
import org.partiql.lang.eval.ExprValueFactory;
import org.partiql.lang.eval.NullPropagatingExprFunction;
import org.partiql.lang.eval.builtins.BuiltinFunctionsKt;
import org.partiql.lang.eval.builtins.CoalesceExprFunction;
import org.partiql.lang.eval.builtins.DateAddExprFunction;
import org.partiql.lang.eval.builtins.DateDiffExprFunction;
import org.partiql.lang.eval.builtins.ExtractExprFunction;
import org.partiql.lang.eval.builtins.FromUnixTimeFunction;
import org.partiql.lang.eval.builtins.MakeDateExprFunction;
import org.partiql.lang.eval.builtins.MakeTimeExprFunction;
import org.partiql.lang.eval.builtins.NullIfExprFunction;
import org.partiql.lang.eval.builtins.SizeExprFunction;
import org.partiql.lang.eval.builtins.SubstringExprFunction;
import org.partiql.lang.eval.builtins.ToStringExprFunction;
import org.partiql.lang.eval.builtins.ToTimestampExprFunction;
import org.partiql.lang.eval.builtins.TrimExprFunction;
import org.partiql.lang.eval.builtins.UnixTimestampFunction;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=2, d1={"\u00005\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002*\u0001\u000f\u001a\u0018\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u0004H\u0002\u001a\u0016\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u00062\u0006\u0010\u0003\u001a\u00020\u0004H\u0000\u001a\u0010\u0010\b\u001a\u00020\u00072\u0006\u0010\u0003\u001a\u00020\u0004H\u0000\u001a\u0010\u0010\t\u001a\u00020\u00072\u0006\u0010\u0003\u001a\u00020\u0004H\u0000\u001a\u0010\u0010\n\u001a\u00020\u00072\u0006\u0010\u0003\u001a\u00020\u0004H\u0000\u001a\u0010\u0010\u000b\u001a\u00020\u00072\u0006\u0010\u0003\u001a\u00020\u0004H\u0000\u001a\u0010\u0010\f\u001a\u00020\u00072\u0006\u0010\u0003\u001a\u00020\u0004H\u0000\u001a\u0010\u0010\r\u001a\u00020\u00072\u0006\u0010\u0003\u001a\u00020\u0004H\u0000\u001a7\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0010\u001a\u00020\u00112\u0018\u0010\u0012\u001a\u0014\u0012\u0004\u0012\u00020\u0014\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u00020\u00010\u0013H\u0002\u00a2\u0006\u0002\u0010\u0015\u00a8\u0006\u0016"}, d2={"charLengthImpl", "Lorg/partiql/lang/eval/ExprValue;", "arg", "valueFactory", "Lorg/partiql/lang/eval/ExprValueFactory;", "createBuiltinFunctions", "", "Lorg/partiql/lang/eval/ExprFunction;", "createCharLength", "createCharacterLength", "createExists", "createLower", "createUpper", "createUtcNow", "makeOneArgExprFunction", "org/partiql/lang/eval/builtins/BuiltinFunctionsKt$makeOneArgExprFunction$1", "funcName", "", "func", "Lkotlin/Function2;", "Lorg/partiql/lang/eval/Environment;", "(Lorg/partiql/lang/eval/ExprValueFactory;Ljava/lang/String;Lkotlin/jvm/functions/Function2;)Lorg/partiql/lang/eval/builtins/BuiltinFunctionsKt$makeOneArgExprFunction$1;", "lang"})
public final class BuiltinFunctionsKt {
    @NotNull
    public static final List<ExprFunction> createBuiltinFunctions(@NotNull ExprValueFactory valueFactory) {
        Intrinsics.checkParameterIsNotNull(valueFactory, "valueFactory");
        return CollectionsKt.listOf(BuiltinFunctionsKt.createUpper(valueFactory), BuiltinFunctionsKt.createLower(valueFactory), BuiltinFunctionsKt.createExists(valueFactory), BuiltinFunctionsKt.createCharLength(valueFactory), BuiltinFunctionsKt.createCharacterLength(valueFactory), BuiltinFunctionsKt.createUtcNow(valueFactory), new CoalesceExprFunction(valueFactory), new DateAddExprFunction(valueFactory), new DateDiffExprFunction(valueFactory), new ExtractExprFunction(valueFactory), new MakeDateExprFunction(valueFactory), new MakeTimeExprFunction(valueFactory), new NullIfExprFunction(valueFactory), new SubstringExprFunction(valueFactory), new TrimExprFunction(valueFactory), new ToStringExprFunction(valueFactory), new ToTimestampExprFunction(valueFactory), new SizeExprFunction(valueFactory), new FromUnixTimeFunction(valueFactory), new UnixTimestampFunction(valueFactory));
    }

    @NotNull
    public static final ExprFunction createExists(@NotNull ExprValueFactory valueFactory) {
        Intrinsics.checkParameterIsNotNull(valueFactory, "valueFactory");
        return new ExprFunction(valueFactory){
            @NotNull
            private final String name = "exists";
            final /* synthetic */ ExprValueFactory $valueFactory;

            @NotNull
            public String getName() {
                return this.name;
            }

            @NotNull
            public ExprValue call(@NotNull Environment env, @NotNull List<? extends ExprValue> args2) {
                Intrinsics.checkParameterIsNotNull(env, "env");
                Intrinsics.checkParameterIsNotNull(args2, "args");
                switch (args2.size()) {
                    case 1: {
                        break;
                    }
                    default: {
                        Void void_ = ExceptionsKt.errNoContext("Expected a single argument for exists but found: " + args2.size(), false);
                        throw null;
                    }
                }
                return this.$valueFactory.newBoolean(SequencesKt.any(CollectionsKt.asSequence(args2.get(0))));
            }
            {
                this.$valueFactory = $captured_local_variable$0;
                this.name = "exists";
            }
        };
    }

    @NotNull
    public static final ExprFunction createUtcNow(@NotNull ExprValueFactory valueFactory) {
        Intrinsics.checkParameterIsNotNull(valueFactory, "valueFactory");
        return new ExprFunction(valueFactory){
            @NotNull
            private final String name = "utcnow";
            final /* synthetic */ ExprValueFactory $valueFactory;

            @NotNull
            public String getName() {
                return this.name;
            }

            @NotNull
            public ExprValue call(@NotNull Environment env, @NotNull List<? extends ExprValue> args2) {
                Intrinsics.checkParameterIsNotNull(env, "env");
                Intrinsics.checkParameterIsNotNull(args2, "args");
                Collection collection = args2;
                boolean bl = false;
                if (!collection.isEmpty()) {
                    Void void_ = ExceptionsKt.errNoContext("utcnow() takes no arguments", false);
                    throw null;
                }
                return this.$valueFactory.newTimestamp(env.getSession().getNow());
            }
            {
                this.$valueFactory = $captured_local_variable$0;
                this.name = "utcnow";
            }
        };
    }

    private static final makeOneArgExprFunction.1 makeOneArgExprFunction(ExprValueFactory valueFactory, String funcName, Function2<? super Environment, ? super ExprValue, ? extends ExprValue> func) {
        return new NullPropagatingExprFunction(func, funcName, valueFactory, funcName, 1, valueFactory){
            @NotNull
            private final String name;
            final /* synthetic */ Function2 $func;
            final /* synthetic */ String $funcName;
            final /* synthetic */ ExprValueFactory $valueFactory;

            @NotNull
            public String getName() {
                return this.name;
            }

            @NotNull
            public ExprValue eval(@NotNull Environment env, @NotNull List<? extends ExprValue> args2) {
                Intrinsics.checkParameterIsNotNull(env, "env");
                Intrinsics.checkParameterIsNotNull(args2, "args");
                return (ExprValue)this.$func.invoke(env, args2.get(0));
            }
            {
                this.$func = $captured_local_variable$0;
                this.$funcName = $captured_local_variable$1;
                this.$valueFactory = $captured_local_variable$2;
                super($super_call_param$3, $super_call_param$4, $super_call_param$5);
                this.name = $captured_local_variable$1;
            }
        };
    }

    @NotNull
    public static final ExprFunction createCharLength(@NotNull ExprValueFactory valueFactory) {
        Intrinsics.checkParameterIsNotNull(valueFactory, "valueFactory");
        return BuiltinFunctionsKt.makeOneArgExprFunction(valueFactory, "char_length", (Function2<? super Environment, ? super ExprValue, ? extends ExprValue>)new Function2<Environment, ExprValue, ExprValue>(valueFactory){
            final /* synthetic */ ExprValueFactory $valueFactory;

            @NotNull
            public final ExprValue invoke(@NotNull Environment $noName_0, @NotNull ExprValue arg) {
                Intrinsics.checkParameterIsNotNull($noName_0, "<anonymous parameter 0>");
                Intrinsics.checkParameterIsNotNull(arg, "arg");
                return BuiltinFunctionsKt.access$charLengthImpl(arg, this.$valueFactory);
            }
            {
                this.$valueFactory = exprValueFactory;
                super(2);
            }
        });
    }

    @NotNull
    public static final ExprFunction createCharacterLength(@NotNull ExprValueFactory valueFactory) {
        Intrinsics.checkParameterIsNotNull(valueFactory, "valueFactory");
        return BuiltinFunctionsKt.makeOneArgExprFunction(valueFactory, "character_length", (Function2<? super Environment, ? super ExprValue, ? extends ExprValue>)new Function2<Environment, ExprValue, ExprValue>(valueFactory){
            final /* synthetic */ ExprValueFactory $valueFactory;

            @NotNull
            public final ExprValue invoke(@NotNull Environment $noName_0, @NotNull ExprValue arg) {
                Intrinsics.checkParameterIsNotNull($noName_0, "<anonymous parameter 0>");
                Intrinsics.checkParameterIsNotNull(arg, "arg");
                return BuiltinFunctionsKt.access$charLengthImpl(arg, this.$valueFactory);
            }
            {
                this.$valueFactory = exprValueFactory;
                super(2);
            }
        });
    }

    private static final ExprValue charLengthImpl(ExprValue arg, ExprValueFactory valueFactory) {
        String s;
        String string = s = ExprValueExtensionsKt.stringValue(arg);
        int n = 0;
        int n2 = s.length();
        ExprValueFactory exprValueFactory = valueFactory;
        boolean bl = false;
        String string2 = string;
        if (string2 == null) {
            throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
        }
        int n3 = string2.codePointCount(n, n2);
        return exprValueFactory.newInt(n3);
    }

    @NotNull
    public static final ExprFunction createUpper(@NotNull ExprValueFactory valueFactory) {
        Intrinsics.checkParameterIsNotNull(valueFactory, "valueFactory");
        return BuiltinFunctionsKt.makeOneArgExprFunction(valueFactory, "upper", (Function2<? super Environment, ? super ExprValue, ? extends ExprValue>)new Function2<Environment, ExprValue, ExprValue>(valueFactory){
            final /* synthetic */ ExprValueFactory $valueFactory;

            @NotNull
            public final ExprValue invoke(@NotNull Environment $noName_0, @NotNull ExprValue arg) {
                Intrinsics.checkParameterIsNotNull($noName_0, "<anonymous parameter 0>");
                Intrinsics.checkParameterIsNotNull(arg, "arg");
                String string = ExprValueExtensionsKt.stringValue(arg);
                ExprValueFactory exprValueFactory = this.$valueFactory;
                boolean bl = false;
                String string2 = string;
                if (string2 == null) {
                    throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                }
                String string3 = string2.toUpperCase();
                Intrinsics.checkExpressionValueIsNotNull(string3, "(this as java.lang.String).toUpperCase()");
                String string4 = string3;
                return exprValueFactory.newString(string4);
            }
            {
                this.$valueFactory = exprValueFactory;
                super(2);
            }
        });
    }

    @NotNull
    public static final ExprFunction createLower(@NotNull ExprValueFactory valueFactory) {
        Intrinsics.checkParameterIsNotNull(valueFactory, "valueFactory");
        return BuiltinFunctionsKt.makeOneArgExprFunction(valueFactory, "lower", (Function2<? super Environment, ? super ExprValue, ? extends ExprValue>)new Function2<Environment, ExprValue, ExprValue>(valueFactory){
            final /* synthetic */ ExprValueFactory $valueFactory;

            @NotNull
            public final ExprValue invoke(@NotNull Environment $noName_0, @NotNull ExprValue arg) {
                Intrinsics.checkParameterIsNotNull($noName_0, "<anonymous parameter 0>");
                Intrinsics.checkParameterIsNotNull(arg, "arg");
                String string = ExprValueExtensionsKt.stringValue(arg);
                ExprValueFactory exprValueFactory = this.$valueFactory;
                boolean bl = false;
                String string2 = string;
                if (string2 == null) {
                    throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                }
                String string3 = string2.toLowerCase();
                Intrinsics.checkExpressionValueIsNotNull(string3, "(this as java.lang.String).toLowerCase()");
                String string4 = string3;
                return exprValueFactory.newString(string4);
            }
            {
                this.$valueFactory = exprValueFactory;
                super(2);
            }
        });
    }

    public static final /* synthetic */ ExprValue access$charLengthImpl(ExprValue arg, ExprValueFactory valueFactory) {
        return BuiltinFunctionsKt.charLengthImpl(arg, valueFactory);
    }
}

