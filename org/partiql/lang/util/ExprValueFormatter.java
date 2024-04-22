/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package org.partiql.lang.util;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.partiql.lang.eval.ExprValue;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u0016J\u001c\u0010\u0006\u001a\u00020\u00072\u0006\u0010\u0004\u001a\u00020\u00052\n\u0010\b\u001a\u00060\tj\u0002`\nH&\u00a8\u0006\u000b"}, d2={"Lorg/partiql/lang/util/ExprValueFormatter;", "", "format", "", "value", "Lorg/partiql/lang/eval/ExprValue;", "formatTo", "", "out", "Ljava/lang/Appendable;", "Lkotlin/text/Appendable;", "lang"})
public interface ExprValueFormatter {
    public void formatTo(@NotNull ExprValue var1, @NotNull Appendable var2);

    @NotNull
    public String format(@NotNull ExprValue var1);

    @Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=3)
    public static final class DefaultImpls {
        @NotNull
        public static String format(ExprValueFormatter $this, @NotNull ExprValue value) {
            Intrinsics.checkParameterIsNotNull(value, "value");
            StringBuilder sb = new StringBuilder();
            $this.formatTo(value, sb);
            String string = sb.toString();
            Intrinsics.checkExpressionValueIsNotNull(string, "sb.toString()");
            return string;
        }
    }
}

