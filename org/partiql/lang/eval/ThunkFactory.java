/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package org.partiql.lang.eval;

import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.partiql.lang.ast.Meta;
import org.partiql.lang.ast.MetaContainer;
import org.partiql.lang.ast.SourceLocationMeta;
import org.partiql.lang.errors.Property;
import org.partiql.lang.eval.Environment;
import org.partiql.lang.eval.EvaluationException;
import org.partiql.lang.eval.ExceptionsKt;
import org.partiql.lang.eval.ExprValue;
import org.partiql.lang.eval.ExprValueExtensionsKt;
import org.partiql.lang.eval.ExprValueFactory;
import org.partiql.lang.eval.ThunkOptions;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000Z\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J(\u0010\u0007\u001a\u00020\b2\b\u0010\t\u001a\u0004\u0018\u00010\n2\u000e\b\u0004\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\b0\fH\u0080\b\u00a2\u0006\u0002\b\rJh\u0010\u000e\u001a\u0012\u0012\u0004\u0012\u00020\u0010\u0012\u0004\u0012\u00020\b0\u000fj\u0002`\u00112\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u00152\u001c\u0010\u0016\u001a\u0018\u0012\u0014\u0012\u0012\u0012\u0004\u0012\u00020\u0010\u0012\u0004\u0012\u00020\b0\u000fj\u0002`\u00110\u00172\u001a\b\u0004\u0010\u0018\u001a\u0014\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\u001a0\u0019H\u0080\b\u00a2\u0006\u0002\b\u001bJ@\u0010\u001c\u001a\u0012\u0012\u0004\u0012\u00020\u0010\u0012\u0004\u0012\u00020\b0\u000fj\u0002`\u00112\u0006\u0010\u0014\u001a\u00020\u00152\u0018\b\u0004\u0010\u001d\u001a\u0012\u0012\u0004\u0012\u00020\u0010\u0012\u0004\u0012\u00020\b0\u000fj\u0002`\u0011H\u0080\b\u00a2\u0006\u0002\b\u001eJ^\u0010\u001f\u001a\u001e\u0012\u0004\u0012\u00020\u0010\u0012\u0004\u0012\u0002H \u0012\u0004\u0012\u00020\b0\u0019j\b\u0012\u0004\u0012\u0002H `!\"\u0004\b\u0000\u0010 2\u0006\u0010\u0014\u001a\u00020\u00152$\b\u0004\u0010\u001d\u001a\u001e\u0012\u0004\u0012\u00020\u0010\u0012\u0004\u0012\u0002H \u0012\u0004\u0012\u00020\b0\u0019j\b\u0012\u0004\u0012\u0002H `!H\u0080\b\u00a2\u0006\u0002\b\"Jh\u0010#\u001a\u0012\u0012\u0004\u0012\u00020\u0010\u0012\u0004\u0012\u00020\b0\u000fj\u0002`\u00112\u0006\u0010$\u001a\u00020\b2\u0006\u0010\u0014\u001a\u00020\u00152\u001c\u0010\u0016\u001a\u0018\u0012\u0014\u0012\u0012\u0012\u0004\u0012\u00020\u0010\u0012\u0004\u0012\u00020\b0\u000fj\u0002`\u00110\u00172\u001a\b\u0004\u0010\u0018\u001a\u0014\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\b0\u0019H\u0080\b\u00a2\u0006\u0002\b%R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006&"}, d2={"Lorg/partiql/lang/eval/ThunkFactory;", "", "thunkOptions", "Lorg/partiql/lang/eval/ThunkOptions;", "(Lorg/partiql/lang/eval/ThunkOptions;)V", "getThunkOptions", "()Lorg/partiql/lang/eval/ThunkOptions;", "handleException", "Lorg/partiql/lang/eval/ExprValue;", "sourceLocation", "Lorg/partiql/lang/ast/SourceLocationMeta;", "block", "Lkotlin/Function0;", "handleException$lang", "thunkAndMap", "Lkotlin/Function1;", "Lorg/partiql/lang/eval/Environment;", "Lorg/partiql/lang/eval/ThunkEnv;", "valueFactory", "Lorg/partiql/lang/eval/ExprValueFactory;", "metas", "Lorg/partiql/lang/ast/MetaContainer;", "argThunks", "", "op", "Lkotlin/Function2;", "", "thunkAndMap$lang", "thunkEnv", "t", "thunkEnv$lang", "thunkEnvValue", "T", "Lorg/partiql/lang/eval/ThunkEnvValue;", "thunkEnvValue$lang", "thunkFold", "nullValue", "thunkFold$lang", "lang"})
public final class ThunkFactory {
    @NotNull
    private final ThunkOptions thunkOptions;

    @NotNull
    public final Function1<Environment, ExprValue> thunkEnv$lang(@NotNull MetaContainer metas, @NotNull Function1<? super Environment, ? extends ExprValue> t) {
        int $i$f$thunkEnv$lang = 0;
        Intrinsics.checkParameterIsNotNull(metas, "metas");
        Intrinsics.checkParameterIsNotNull(t, "t");
        Meta meta = metas.find("$source_location");
        if (!(meta instanceof SourceLocationMeta)) {
            meta = null;
        }
        SourceLocationMeta sourceLocationMeta = (SourceLocationMeta)meta;
        return new Function1<Environment, ExprValue>(this, sourceLocationMeta, t){
            final /* synthetic */ ThunkFactory this$0;
            final /* synthetic */ SourceLocationMeta $sourceLocationMeta;
            final /* synthetic */ Function1 $t;

            /*
             * WARNING - void declaration
             */
            @NotNull
            public final ExprValue invoke(@NotNull Environment env) {
                ExprValue exprValue2;
                Intrinsics.checkParameterIsNotNull(env, "env");
                ThunkFactory thunkFactory = this.this$0;
                SourceLocationMeta sourceLocation$iv = this.$sourceLocationMeta;
                boolean $i$f$handleException$lang = false;
                try {
                    boolean bl = false;
                    exprValue2 = (ExprValue)this.$t.invoke(env);
                } catch (EvaluationException e$iv) {
                    if (e$iv.getErrorContext() == null) {
                        throw (Throwable)new EvaluationException(e$iv.getMessage(), e$iv.getErrorCode(), ExceptionsKt.errorContextFrom(sourceLocation$iv), e$iv, e$iv.getInternal());
                    }
                    if (!e$iv.getErrorContext().hasProperty(Property.LINE_NUMBER)) {
                        SourceLocationMeta sourceLocationMeta = sourceLocation$iv;
                        if (sourceLocationMeta != null) {
                            SourceLocationMeta sourceLocationMeta2 = sourceLocationMeta;
                            boolean bl = false;
                            boolean bl2 = false;
                            SourceLocationMeta it$iv = sourceLocationMeta2;
                            boolean bl3 = false;
                            ExceptionsKt.fillErrorContext(e$iv.getErrorContext(), sourceLocation$iv);
                        }
                    }
                    throw (Throwable)e$iv;
                } catch (Exception e$iv) {
                    void this_$iv;
                    R r = this_$iv.getThunkOptions().getHandleException().invoke(e$iv, sourceLocation$iv);
                    throw null;
                }
                return exprValue2;
            }
            {
                this.this$0 = thunkFactory;
                this.$sourceLocationMeta = sourceLocationMeta;
                this.$t = function1;
                super(1);
            }
        };
    }

    @NotNull
    public final <T> Function2<Environment, T, ExprValue> thunkEnvValue$lang(@NotNull MetaContainer metas, @NotNull Function2<? super Environment, ? super T, ? extends ExprValue> t) {
        int $i$f$thunkEnvValue$lang = 0;
        Intrinsics.checkParameterIsNotNull(metas, "metas");
        Intrinsics.checkParameterIsNotNull(t, "t");
        Meta meta = metas.find("$source_location");
        if (!(meta instanceof SourceLocationMeta)) {
            meta = null;
        }
        SourceLocationMeta sourceLocationMeta = (SourceLocationMeta)meta;
        return new Function2<Environment, T, ExprValue>(this, sourceLocationMeta, t){
            final /* synthetic */ ThunkFactory this$0;
            final /* synthetic */ SourceLocationMeta $sourceLocationMeta;
            final /* synthetic */ Function2 $t;

            /*
             * WARNING - void declaration
             */
            @NotNull
            public final ExprValue invoke(@NotNull Environment env, T arg1) {
                ExprValue exprValue2;
                Intrinsics.checkParameterIsNotNull(env, "env");
                ThunkFactory thunkFactory = this.this$0;
                SourceLocationMeta sourceLocation$iv = this.$sourceLocationMeta;
                boolean $i$f$handleException$lang = false;
                try {
                    boolean bl = false;
                    exprValue2 = (ExprValue)this.$t.invoke(env, arg1);
                } catch (EvaluationException e$iv) {
                    if (e$iv.getErrorContext() == null) {
                        throw (Throwable)new EvaluationException(e$iv.getMessage(), e$iv.getErrorCode(), ExceptionsKt.errorContextFrom(sourceLocation$iv), e$iv, e$iv.getInternal());
                    }
                    if (!e$iv.getErrorContext().hasProperty(Property.LINE_NUMBER)) {
                        SourceLocationMeta sourceLocationMeta = sourceLocation$iv;
                        if (sourceLocationMeta != null) {
                            SourceLocationMeta sourceLocationMeta2 = sourceLocationMeta;
                            boolean bl = false;
                            boolean bl2 = false;
                            SourceLocationMeta it$iv = sourceLocationMeta2;
                            boolean bl3 = false;
                            ExceptionsKt.fillErrorContext(e$iv.getErrorContext(), sourceLocation$iv);
                        }
                    }
                    throw (Throwable)e$iv;
                } catch (Exception e$iv) {
                    void this_$iv;
                    R r = this_$iv.getThunkOptions().getHandleException().invoke(e$iv, sourceLocation$iv);
                    throw null;
                }
                return exprValue2;
            }
            {
                this.this$0 = thunkFactory;
                this.$sourceLocationMeta = sourceLocationMeta;
                this.$t = function2;
                super(2);
            }
        };
    }

    @NotNull
    public final Function1<Environment, ExprValue> thunkFold$lang(@NotNull ExprValue nullValue, @NotNull MetaContainer metas, @NotNull List<? extends Function1<? super Environment, ? extends ExprValue>> argThunks, @NotNull Function2<? super ExprValue, ? super ExprValue, ? extends ExprValue> op) {
        int $i$f$thunkFold$lang = 0;
        Intrinsics.checkParameterIsNotNull(nullValue, "nullValue");
        Intrinsics.checkParameterIsNotNull(metas, "metas");
        Intrinsics.checkParameterIsNotNull(argThunks, "argThunks");
        Intrinsics.checkParameterIsNotNull(op, "op");
        Collection collection = argThunks;
        boolean bl = false;
        boolean bl2 = !collection.isEmpty();
        bl = false;
        boolean bl3 = false;
        if (!bl2) {
            boolean bl4 = false;
            String string = "argThunks must not be empty";
            throw (Throwable)new IllegalArgumentException(string.toString());
        }
        Function1<? super Environment, ? extends ExprValue> firstThunk = CollectionsKt.first(argThunks);
        List otherThunks = CollectionsKt.drop((Iterable)argThunks, 1);
        ThunkFactory this_$iv = this;
        boolean $i$f$thunkEnv$lang = false;
        Meta meta = metas.find("$source_location");
        if (!(meta instanceof SourceLocationMeta)) {
            meta = null;
        }
        SourceLocationMeta sourceLocationMeta$iv = (SourceLocationMeta)meta;
        return new Function1<Environment, ExprValue>(this_$iv, sourceLocationMeta$iv, firstThunk, nullValue, otherThunks, op){
            final /* synthetic */ ThunkFactory this$0;
            final /* synthetic */ SourceLocationMeta $sourceLocationMeta;
            final /* synthetic */ Function1 $firstThunk$inlined;
            final /* synthetic */ ExprValue $nullValue$inlined;
            final /* synthetic */ List $otherThunks$inlined;
            final /* synthetic */ Function2 $op$inlined;
            {
                this.this$0 = thunkFactory;
                this.$sourceLocationMeta = sourceLocationMeta;
                this.$firstThunk$inlined = function1;
                this.$nullValue$inlined = exprValue2;
                this.$otherThunks$inlined = list;
                this.$op$inlined = function2;
                super(1);
            }

            /*
             * WARNING - void declaration
             */
            @NotNull
            public final ExprValue invoke(@NotNull Environment env) {
                ExprValue exprValue2;
                Intrinsics.checkParameterIsNotNull(env, "env");
                ThunkFactory thunkFactory = this.this$0;
                SourceLocationMeta sourceLocation$iv = this.$sourceLocationMeta;
                boolean $i$f$handleException$lang = false;
                try {
                    ExprValue exprValue3;
                    block10: {
                        boolean bl = false;
                        Environment env2 = env;
                        boolean bl2 = false;
                        ExprValue firstValue = (ExprValue)this.$firstThunk$inlined.invoke(env2);
                        if (ExprValueExtensionsKt.isUnknown(firstValue)) {
                            exprValue3 = this.$nullValue$inlined;
                        } else {
                            Iterable $this$fold$iv = this.$otherThunks$inlined;
                            boolean $i$f$fold = false;
                            ExprValue accumulator$iv = firstValue;
                            for (T element$iv : $this$fold$iv) {
                                void curr;
                                Function1 function1 = (Function1)element$iv;
                                ExprValue acc = accumulator$iv;
                                boolean bl3 = false;
                                ExprValue currValue = (ExprValue)curr.invoke(env2);
                                if (currValue.getType().isUnknown()) {
                                    exprValue3 = this.$nullValue$inlined;
                                    break block10;
                                }
                                accumulator$iv = (ExprValue)this.$op$inlined.invoke(acc, currValue);
                            }
                            exprValue3 = accumulator$iv;
                        }
                    }
                    exprValue2 = exprValue3;
                } catch (EvaluationException e$iv) {
                    if (e$iv.getErrorContext() == null) {
                        throw (Throwable)new EvaluationException(e$iv.getMessage(), e$iv.getErrorCode(), ExceptionsKt.errorContextFrom(sourceLocation$iv), e$iv, e$iv.getInternal());
                    }
                    if (!e$iv.getErrorContext().hasProperty(Property.LINE_NUMBER)) {
                        SourceLocationMeta sourceLocationMeta = sourceLocation$iv;
                        if (sourceLocationMeta != null) {
                            SourceLocationMeta sourceLocationMeta2 = sourceLocationMeta;
                            boolean bl = false;
                            boolean bl4 = false;
                            SourceLocationMeta it$iv = sourceLocationMeta2;
                            boolean bl5 = false;
                            ExceptionsKt.fillErrorContext(e$iv.getErrorContext(), sourceLocation$iv);
                        }
                    }
                    throw (Throwable)e$iv;
                } catch (Exception e$iv) {
                    void this_$iv;
                    R r = this_$iv.getThunkOptions().getHandleException().invoke(e$iv, sourceLocation$iv);
                    throw null;
                }
                return exprValue2;
            }
        };
    }

    @NotNull
    public final Function1<Environment, ExprValue> thunkAndMap$lang(@NotNull ExprValueFactory valueFactory, @NotNull MetaContainer metas, @NotNull List<? extends Function1<? super Environment, ? extends ExprValue>> argThunks, @NotNull Function2<? super ExprValue, ? super ExprValue, Boolean> op) {
        int $i$f$thunkAndMap$lang = 0;
        Intrinsics.checkParameterIsNotNull(valueFactory, "valueFactory");
        Intrinsics.checkParameterIsNotNull(metas, "metas");
        Intrinsics.checkParameterIsNotNull(argThunks, "argThunks");
        Intrinsics.checkParameterIsNotNull(op, "op");
        boolean bl = argThunks.size() >= 2;
        boolean bl2 = false;
        boolean bl3 = false;
        if (!bl) {
            boolean bl4 = false;
            String string = "argThunks must have at least two elements";
            throw (Throwable)new IllegalArgumentException(string.toString());
        }
        Function1<? super Environment, ? extends ExprValue> firstThunk = CollectionsKt.first(argThunks);
        List otherThunks = CollectionsKt.drop((Iterable)argThunks, 1);
        ThunkFactory this_$iv = this;
        boolean $i$f$thunkEnv$lang = false;
        Meta meta = metas.find("$source_location");
        if (!(meta instanceof SourceLocationMeta)) {
            meta = null;
        }
        SourceLocationMeta sourceLocationMeta$iv = (SourceLocationMeta)meta;
        return new Function1<Environment, ExprValue>(this_$iv, sourceLocationMeta$iv, firstThunk, valueFactory, otherThunks, op){
            final /* synthetic */ ThunkFactory this$0;
            final /* synthetic */ SourceLocationMeta $sourceLocationMeta;
            final /* synthetic */ Function1 $firstThunk$inlined;
            final /* synthetic */ ExprValueFactory $valueFactory$inlined;
            final /* synthetic */ List $otherThunks$inlined;
            final /* synthetic */ Function2 $op$inlined;
            {
                this.this$0 = thunkFactory;
                this.$sourceLocationMeta = sourceLocationMeta;
                this.$firstThunk$inlined = function1;
                this.$valueFactory$inlined = exprValueFactory;
                this.$otherThunks$inlined = list;
                this.$op$inlined = function2;
                super(1);
            }

            /*
             * WARNING - void declaration
             */
            @NotNull
            public final ExprValue invoke(@NotNull Environment env) {
                ExprValue exprValue2;
                Intrinsics.checkParameterIsNotNull(env, "env");
                ThunkFactory thunkFactory = this.this$0;
                SourceLocationMeta sourceLocation$iv = this.$sourceLocationMeta;
                boolean $i$f$handleException$lang = false;
                try {
                    ExprValue exprValue3;
                    block11: {
                        boolean bl = false;
                        Environment env2 = env;
                        boolean bl2 = false;
                        ExprValue firstValue = (ExprValue)this.$firstThunk$inlined.invoke(env2);
                        if (ExprValueExtensionsKt.isUnknown(firstValue)) {
                            exprValue3 = this.$valueFactory$inlined.getNullValue();
                        } else {
                            Iterable $this$fold$iv = this.$otherThunks$inlined;
                            boolean $i$f$fold = false;
                            ExprValue accumulator$iv = firstValue;
                            for (T element$iv : $this$fold$iv) {
                                void currentThunk;
                                Function1 function1 = (Function1)element$iv;
                                ExprValue lastValue = accumulator$iv;
                                boolean bl3 = false;
                                ExprValue currentValue = (ExprValue)currentThunk.invoke(env2);
                                if (ExprValueExtensionsKt.isUnknown(currentValue)) {
                                    exprValue3 = this.$valueFactory$inlined.getNullValue();
                                    break block11;
                                }
                                boolean result = (Boolean)this.$op$inlined.invoke(lastValue, currentValue);
                                if (!result) {
                                    exprValue3 = this.$valueFactory$inlined.newBoolean(false);
                                    break block11;
                                }
                                accumulator$iv = currentValue;
                            }
                            exprValue3 = this.$valueFactory$inlined.newBoolean(true);
                        }
                    }
                    exprValue2 = exprValue3;
                } catch (EvaluationException e$iv) {
                    if (e$iv.getErrorContext() == null) {
                        throw (Throwable)new EvaluationException(e$iv.getMessage(), e$iv.getErrorCode(), ExceptionsKt.errorContextFrom(sourceLocation$iv), e$iv, e$iv.getInternal());
                    }
                    if (!e$iv.getErrorContext().hasProperty(Property.LINE_NUMBER)) {
                        SourceLocationMeta sourceLocationMeta = sourceLocation$iv;
                        if (sourceLocationMeta != null) {
                            SourceLocationMeta sourceLocationMeta2 = sourceLocationMeta;
                            boolean bl = false;
                            boolean bl4 = false;
                            SourceLocationMeta it$iv = sourceLocationMeta2;
                            boolean bl5 = false;
                            ExceptionsKt.fillErrorContext(e$iv.getErrorContext(), sourceLocation$iv);
                        }
                    }
                    throw (Throwable)e$iv;
                } catch (Exception e$iv) {
                    void this_$iv;
                    R r = this_$iv.getThunkOptions().getHandleException().invoke(e$iv, sourceLocation$iv);
                    throw null;
                }
                return exprValue2;
            }
        };
    }

    @NotNull
    public final ExprValue handleException$lang(@Nullable SourceLocationMeta sourceLocation, @NotNull Function0<? extends ExprValue> block) {
        ExprValue exprValue2;
        int $i$f$handleException$lang = 0;
        Intrinsics.checkParameterIsNotNull(block, "block");
        try {
            exprValue2 = block.invoke();
        } catch (EvaluationException e) {
            if (e.getErrorContext() == null) {
                throw (Throwable)new EvaluationException(e.getMessage(), e.getErrorCode(), ExceptionsKt.errorContextFrom(sourceLocation), e, e.getInternal());
            }
            if (!e.getErrorContext().hasProperty(Property.LINE_NUMBER)) {
                SourceLocationMeta sourceLocationMeta = sourceLocation;
                if (sourceLocationMeta != null) {
                    SourceLocationMeta sourceLocationMeta2 = sourceLocationMeta;
                    boolean bl = false;
                    boolean bl2 = false;
                    SourceLocationMeta it = sourceLocationMeta2;
                    boolean bl3 = false;
                    ExceptionsKt.fillErrorContext(e.getErrorContext(), sourceLocation);
                }
            }
            throw (Throwable)e;
        } catch (Exception e) {
            Object r = this.getThunkOptions().getHandleException().invoke(e, sourceLocation);
            throw null;
        }
        return exprValue2;
    }

    @NotNull
    public final ThunkOptions getThunkOptions() {
        return this.thunkOptions;
    }

    public ThunkFactory(@NotNull ThunkOptions thunkOptions) {
        Intrinsics.checkParameterIsNotNull(thunkOptions, "thunkOptions");
        this.thunkOptions = thunkOptions;
    }
}

