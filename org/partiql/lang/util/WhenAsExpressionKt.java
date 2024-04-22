/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package org.partiql.lang.util;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.partiql.lang.util.WhenAsExpressionHelper;

/*
 * Illegal identifiers - consider using --renameillegalidents true
 */
@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=2, d1={"\u0000\u0012\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\u001a\u0017\u0010\u0000\u001a\u00020\u00012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003H\u0086\b\u00a8\u0006\u0005"}, d2={"case", "Lorg/partiql/lang/util/WhenAsExpressionHelper;", "block", "Lkotlin/Function0;", "", "lang"})
public final class WhenAsExpressionKt {
    @NotNull
    public static final WhenAsExpressionHelper case(@NotNull Function0<Unit> block) {
        int $i$f$case = 0;
        Intrinsics.checkParameterIsNotNull(block, "block");
        block.invoke();
        return WhenAsExpressionHelper.Companion.getInstance();
    }
}

