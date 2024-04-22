/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package org.partiql.lang.eval.binding;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.partiql.lang.eval.BindingName;
import org.partiql.lang.eval.Bindings;
import org.partiql.lang.eval.ExprValue;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b&\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u001a\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u00042\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00050\u0007J$\u0010\b\u001a\u0016\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\u0007\u0012\u0006\u0012\u0004\u0018\u00010\u00050\t2\u0006\u0010\n\u001a\u00020\u000bH&\u00a8\u0006\f"}, d2={"Lorg/partiql/lang/eval/binding/LocalsBinder;", "", "()V", "bindLocals", "Lorg/partiql/lang/eval/Bindings;", "Lorg/partiql/lang/eval/ExprValue;", "locals", "", "binderForName", "Lkotlin/Function1;", "bindingName", "Lorg/partiql/lang/eval/BindingName;", "lang"})
public abstract class LocalsBinder {
    @NotNull
    public final Bindings<ExprValue> bindLocals(@NotNull List<? extends ExprValue> locals) {
        Intrinsics.checkParameterIsNotNull(locals, "locals");
        return new Bindings<ExprValue>(this, locals){
            final /* synthetic */ LocalsBinder this$0;
            final /* synthetic */ List $locals;

            @Nullable
            public ExprValue get(@NotNull BindingName bindingName) {
                Intrinsics.checkParameterIsNotNull(bindingName, "bindingName");
                return this.this$0.binderForName(bindingName).invoke(this.$locals);
            }
            {
                this.this$0 = $outer;
                this.$locals = $captured_local_variable$1;
            }
        };
    }

    @NotNull
    public abstract Function1<List<? extends ExprValue>, ExprValue> binderForName(@NotNull BindingName var1);
}

