/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package org.partiql.lang.eval;

import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.partiql.lang.ast.CaseSensitivity;
import org.partiql.lang.eval.BindingCase;
import org.partiql.lang.eval.BindingsKt$WhenMappings;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=2, d1={"\u0000\f\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u001a\n\u0010\u0000\u001a\u00020\u0001*\u00020\u0002\u00a8\u0006\u0003"}, d2={"toBindingCase", "Lorg/partiql/lang/eval/BindingCase;", "Lorg/partiql/lang/ast/CaseSensitivity;", "lang"})
public final class BindingsKt {
    @NotNull
    public static final BindingCase toBindingCase(@NotNull CaseSensitivity $this$toBindingCase) {
        BindingCase bindingCase;
        Intrinsics.checkParameterIsNotNull((Object)$this$toBindingCase, "$this$toBindingCase");
        switch (BindingsKt$WhenMappings.$EnumSwitchMapping$0[$this$toBindingCase.ordinal()]) {
            case 1: {
                bindingCase = BindingCase.INSENSITIVE;
                break;
            }
            case 2: {
                bindingCase = BindingCase.SENSITIVE;
                break;
            }
            default: {
                throw new NoWhenBranchMatchedException();
            }
        }
        return bindingCase;
    }
}

