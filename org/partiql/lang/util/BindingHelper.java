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
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.partiql.lang.eval.BindingCase;
import org.partiql.lang.util.BindingHelper$Companion$WhenMappings;
import org.partiql.lang.util.BindingHelpersKt;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\b&\u0018\u0000 \u00032\u00020\u0001:\u0001\u0003B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0004"}, d2={"Lorg/partiql/lang/util/BindingHelper;", "", "()V", "Companion", "lang"})
public abstract class BindingHelper {
    public static final Companion Companion = new Companion(null);

    private BindingHelper() {
    }

    @JvmStatic
    public static final boolean bindingNameEquals(@NotNull String id1, @NotNull String id2, @NotNull BindingCase bindingCase) {
        return Companion.bindingNameEquals(id1, id2, bindingCase);
    }

    @JvmStatic
    @NotNull
    public static final Void throwAmbiguousBindingEvaluationException(@NotNull String bindingName, @NotNull List<String> matchingNames) {
        return Companion.throwAmbiguousBindingEvaluationException(bindingName, matchingNames);
    }

    @Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0001\n\u0002\b\u0002\n\u0002\u0010 \n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J \u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\tH\u0007J\u001e\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u00062\f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00060\u000eH\u0007\u00a8\u0006\u000f"}, d2={"Lorg/partiql/lang/util/BindingHelper$Companion;", "", "()V", "bindingNameEquals", "", "id1", "", "id2", "case", "Lorg/partiql/lang/eval/BindingCase;", "throwAmbiguousBindingEvaluationException", "", "bindingName", "matchingNames", "", "lang"})
    public static final class Companion {
        @JvmStatic
        public final boolean bindingNameEquals(@NotNull String id1, @NotNull String id2, @NotNull BindingCase bindingCase) {
            boolean bl;
            Intrinsics.checkParameterIsNotNull(id1, "id1");
            Intrinsics.checkParameterIsNotNull(id2, "id2");
            Intrinsics.checkParameterIsNotNull((Object)bindingCase, "case");
            switch (BindingHelper$Companion$WhenMappings.$EnumSwitchMapping$0[bindingCase.ordinal()]) {
                case 1: {
                    bl = id1.equals(id2);
                    break;
                }
                case 2: {
                    bl = BindingHelpersKt.caseInsensitiveEquivalent(id1, id2);
                    break;
                }
                default: {
                    throw new NoWhenBranchMatchedException();
                }
            }
            return bl;
        }

        @JvmStatic
        @NotNull
        public final Void throwAmbiguousBindingEvaluationException(@NotNull String bindingName, @NotNull List<String> matchingNames) {
            Intrinsics.checkParameterIsNotNull(bindingName, "bindingName");
            Intrinsics.checkParameterIsNotNull(matchingNames, "matchingNames");
            Void void_ = BindingHelpersKt.errAmbiguousBinding(bindingName, matchingNames);
            throw null;
        }

        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

