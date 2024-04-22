/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package org.partiql.lang.eval;

import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.partiql.lang.eval.ExprAggregator;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bf\u0018\u0000 \u00042\u00020\u0001:\u0001\u0004J\b\u0010\u0002\u001a\u00020\u0003H&\u00a8\u0006\u0005"}, d2={"Lorg/partiql/lang/eval/ExprAggregatorFactory;", "", "create", "Lorg/partiql/lang/eval/ExprAggregator;", "Companion", "lang"})
public interface ExprAggregatorFactory {
    public static final Companion Companion = org.partiql.lang.eval.ExprAggregatorFactory$Companion.$$INSTANCE;

    @NotNull
    public ExprAggregator create();

    @Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0014\u0010\u0003\u001a\u00020\u00042\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006\u00a8\u0006\b"}, d2={"Lorg/partiql/lang/eval/ExprAggregatorFactory$Companion;", "", "()V", "over", "Lorg/partiql/lang/eval/ExprAggregatorFactory;", "func", "Lkotlin/Function0;", "Lorg/partiql/lang/eval/ExprAggregator;", "lang"})
    public static final class Companion {
        static final /* synthetic */ Companion $$INSTANCE;

        @NotNull
        public final ExprAggregatorFactory over(@NotNull Function0<? extends ExprAggregator> func) {
            Intrinsics.checkParameterIsNotNull(func, "func");
            return new ExprAggregatorFactory(func){
                final /* synthetic */ Function0 $func;

                @NotNull
                public ExprAggregator create() {
                    return (ExprAggregator)this.$func.invoke();
                }
                {
                    this.$func = $captured_local_variable$0;
                }
            };
        }

        private Companion() {
        }

        static {
            Companion companion;
            $$INSTANCE = companion = new Companion();
        }
    }
}

