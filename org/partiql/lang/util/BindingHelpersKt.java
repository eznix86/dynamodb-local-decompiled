/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package org.partiql.lang.util;

import java.util.List;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;
import org.partiql.lang.errors.ErrorCode;
import org.partiql.lang.errors.Property;
import org.partiql.lang.eval.BindingCase;
import org.partiql.lang.eval.ExceptionsKt;
import org.partiql.lang.util.BindingHelpersKt$WhenMappings;
import org.partiql.lang.util.PropertyMapHelpersKt;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=2, d1={"\u0000\"\n\u0000\n\u0002\u0010\u0001\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\u001a\u001e\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u0005H\u0000\u001a\u0012\u0010\u0006\u001a\u00020\u0007*\u00020\u00032\u0006\u0010\b\u001a\u00020\u0003\u001a\u001a\u0010\t\u001a\u00020\u0007*\u00020\u00032\u0006\u0010\n\u001a\u00020\u00032\u0006\u0010\u000b\u001a\u00020\f\u00a8\u0006\r"}, d2={"errAmbiguousBinding", "", "bindingName", "", "matchingNames", "", "caseInsensitiveEquivalent", "", "name", "isBindingNameEquivalent", "other", "case", "Lorg/partiql/lang/eval/BindingCase;", "lang"})
public final class BindingHelpersKt {
    @NotNull
    public static final Void errAmbiguousBinding(@NotNull String bindingName, @NotNull List<String> matchingNames) {
        Intrinsics.checkParameterIsNotNull(bindingName, "bindingName");
        Intrinsics.checkParameterIsNotNull(matchingNames, "matchingNames");
        Void void_ = ExceptionsKt.err("Multiple matches were found for the specified identifier", ErrorCode.EVALUATOR_AMBIGUOUS_BINDING, PropertyMapHelpersKt.propertyValueMapOf(PropertyMapHelpersKt.to(Property.BINDING_NAME, bindingName), PropertyMapHelpersKt.to(Property.BINDING_NAME_MATCHES, CollectionsKt.joinToString$default(matchingNames, ", ", null, null, 0, null, null, 62, null))), false);
        throw null;
    }

    public static final boolean isBindingNameEquivalent(@NotNull String $this$isBindingNameEquivalent, @NotNull String other, @NotNull BindingCase bindingCase) {
        boolean bl;
        Intrinsics.checkParameterIsNotNull($this$isBindingNameEquivalent, "$this$isBindingNameEquivalent");
        Intrinsics.checkParameterIsNotNull(other, "other");
        Intrinsics.checkParameterIsNotNull((Object)bindingCase, "case");
        switch (BindingHelpersKt$WhenMappings.$EnumSwitchMapping$0[bindingCase.ordinal()]) {
            case 1: {
                bl = $this$isBindingNameEquivalent.equals(other);
                break;
            }
            case 2: {
                bl = BindingHelpersKt.caseInsensitiveEquivalent($this$isBindingNameEquivalent, other);
                break;
            }
            default: {
                throw new NoWhenBranchMatchedException();
            }
        }
        return bl;
    }

    public static final boolean caseInsensitiveEquivalent(@NotNull String $this$caseInsensitiveEquivalent, @NotNull String name) {
        Intrinsics.checkParameterIsNotNull($this$caseInsensitiveEquivalent, "$this$caseInsensitiveEquivalent");
        Intrinsics.checkParameterIsNotNull(name, "name");
        return StringsKt.equals($this$caseInsensitiveEquivalent, name, true);
    }
}

