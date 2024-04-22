/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package org.partiql.lang.eval.builtins;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.partiql.lang.eval.ExprValue;
import org.partiql.lang.eval.ExprValueExtensionsKt;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0006\b\u0082\u0001\u0018\u0000 \t2\b\u0012\u0004\u0012\u00020\u00000\u0001:\u0001\tB\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H\u0016j\u0002\b\u0005j\u0002\b\u0006j\u0002\b\u0007j\u0002\b\b\u00a8\u0006\n"}, d2={"Lorg/partiql/lang/eval/builtins/TrimSpecification;", "", "(Ljava/lang/String;I)V", "toString", "", "BOTH", "LEADING", "TRAILING", "NONE", "Companion", "lang"})
final class TrimSpecification
extends Enum<TrimSpecification> {
    public static final /* enum */ TrimSpecification BOTH;
    public static final /* enum */ TrimSpecification LEADING;
    public static final /* enum */ TrimSpecification TRAILING;
    public static final /* enum */ TrimSpecification NONE;
    private static final /* synthetic */ TrimSpecification[] $VALUES;
    @NotNull
    private static final String validValues;
    public static final Companion Companion;

    /*
     * WARNING - void declaration
     */
    static {
        void var3_4;
        void $this$filterTo$iv$iv;
        void $this$filter$iv;
        BOTH = new TrimSpecification();
        LEADING = new TrimSpecification();
        TRAILING = new TrimSpecification();
        NONE = new TrimSpecification();
        $VALUES = new TrimSpecification[]{BOTH, LEADING, TRAILING, NONE};
        Companion = new Companion(null);
        TrimSpecification[] trimSpecificationArray = TrimSpecification.values();
        TrimSpecification[] trimSpecificationArray2 = $VALUES;
        boolean $i$f$filter = false;
        void var2_3 = $this$filter$iv;
        Collection destination$iv$iv = new ArrayList();
        boolean $i$f$filterTo = false;
        void var5_6 = $this$filterTo$iv$iv;
        int n = ((void)var5_6).length;
        for (int i = 0; i < n; ++i) {
            void element$iv$iv;
            void it = element$iv$iv = var5_6[i];
            boolean bl = false;
            if (!(it == NONE)) continue;
            destination$iv$iv.add(element$iv$iv);
        }
        List list = (List)var3_4;
        TrimSpecification[] trimSpecificationArray3 = trimSpecificationArray2;
        validValues = CollectionsKt.joinToString$default(list, null, null, null, 0, null, null, 63, null);
    }

    @NotNull
    public String toString() {
        String string = super.toString();
        boolean bl = false;
        String string2 = string;
        if (string2 == null) {
            throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
        }
        String string3 = string2.toLowerCase();
        Intrinsics.checkExpressionValueIsNotNull(string3, "(this as java.lang.String).toLowerCase()");
        return string3;
    }

    public static TrimSpecification[] values() {
        return (TrimSpecification[])$VALUES.clone();
    }

    public static TrimSpecification valueOf(String string) {
        return Enum.valueOf(TrimSpecification.class, string);
    }

    @Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nR\u0011\u0010\u0003\u001a\u00020\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006\u000b"}, d2={"Lorg/partiql/lang/eval/builtins/TrimSpecification$Companion;", "", "()V", "validValues", "", "getValidValues", "()Ljava/lang/String;", "from", "Lorg/partiql/lang/eval/builtins/TrimSpecification;", "arg", "Lorg/partiql/lang/eval/ExprValue;", "lang"})
    public static final class Companion {
        /*
         * Unable to fully structure code
         */
        @NotNull
        public final TrimSpecification from(@NotNull ExprValue arg) {
            block6: {
                block5: {
                    Intrinsics.checkParameterIsNotNull(arg, "arg");
                    var2_2 = ExprValueExtensionsKt.stringValue(arg);
                    switch (var2_2.hashCode()) {
                        case 1276059676: {
                            if (!var2_2.equals("trailing")) ** break;
                            break block5;
                        }
                        case 50359046: {
                            if (!var2_2.equals("leading")) ** break;
                            break;
                        }
                        case 3029889: {
                            if (!var2_2.equals("both")) ** break;
                            v0 = TrimSpecification.BOTH;
                            break block6;
                        }
                    }
                    v0 = TrimSpecification.LEADING;
                    break block6;
                }
                v0 = TrimSpecification.TRAILING;
                break block6;
                v0 = TrimSpecification.NONE;
            }
            return v0;
        }

        @NotNull
        public final String getValidValues() {
            return validValues;
        }

        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

