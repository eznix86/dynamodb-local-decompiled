/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package kotlin.text;

import java.util.Locale;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 6, 0}, k=2, xi=48, d1={"\u0000\f\n\u0000\n\u0002\u0010\u000e\n\u0002\u0010\f\n\u0000\u001a\f\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u0000\u00a8\u0006\u0003"}, d2={"titlecaseImpl", "", "", "kotlin-stdlib"})
public final class _OneToManyTitlecaseMappingsKt {
    @NotNull
    public static final String titlecaseImpl(char $this$titlecaseImpl) {
        String string = String.valueOf($this$titlecaseImpl).toUpperCase(Locale.ROOT);
        Intrinsics.checkNotNullExpressionValue(string, "this as java.lang.String).toUpperCase(Locale.ROOT)");
        String uppercase = string;
        if (uppercase.length() > 1) {
            String string2;
            if ($this$titlecaseImpl == '\u0149') {
                string2 = uppercase;
            } else {
                char c = uppercase.charAt(0);
                String string3 = uppercase;
                int n = 1;
                String string4 = string3.substring(n);
                Intrinsics.checkNotNullExpressionValue(string4, "this as java.lang.String).substring(startIndex)");
                String string5 = string4.toLowerCase(Locale.ROOT);
                Intrinsics.checkNotNullExpressionValue(string5, "this as java.lang.String).toLowerCase(Locale.ROOT)");
                string3 = string5;
                string2 = c + string3;
            }
            return string2;
        }
        return String.valueOf(Character.toTitleCase($this$titlecaseImpl));
    }
}

