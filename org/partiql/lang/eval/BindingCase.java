/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package org.partiql.lang.eval;

import com.amazon.ion.IonSymbol;
import com.amazon.ion.IonSystem;
import com.amazon.ion.IonValue;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.partiql.lang.eval.BindingCase$WhenMappings;
import org.partiql.lang.eval.ExceptionsKt;
import org.partiql.lang.util.IonValueExtensionsKt;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0086\u0001\u0018\u0000 \n2\b\u0012\u0004\u0012\u00020\u00000\u0001:\u0001\nB\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0016\u0010\u0003\u001a\n \u0005*\u0004\u0018\u00010\u00040\u00042\u0006\u0010\u0006\u001a\u00020\u0007j\u0002\b\bj\u0002\b\t\u00a8\u0006\u000b"}, d2={"Lorg/partiql/lang/eval/BindingCase;", "", "(Ljava/lang/String;I)V", "toSymbol", "Lcom/amazon/ion/IonSymbol;", "kotlin.jvm.PlatformType", "ions", "Lcom/amazon/ion/IonSystem;", "SENSITIVE", "INSENSITIVE", "Companion", "lang"})
public final class BindingCase
extends Enum<BindingCase> {
    public static final /* enum */ BindingCase SENSITIVE;
    public static final /* enum */ BindingCase INSENSITIVE;
    private static final /* synthetic */ BindingCase[] $VALUES;
    public static final Companion Companion;

    static {
        BindingCase[] bindingCaseArray = new BindingCase[2];
        BindingCase[] bindingCaseArray2 = bindingCaseArray;
        bindingCaseArray[0] = SENSITIVE = new BindingCase();
        bindingCaseArray[1] = INSENSITIVE = new BindingCase();
        $VALUES = bindingCaseArray;
        Companion = new Companion(null);
    }

    public final IonSymbol toSymbol(@NotNull IonSystem ions) {
        String string;
        Intrinsics.checkParameterIsNotNull(ions, "ions");
        switch (BindingCase$WhenMappings.$EnumSwitchMapping$0[this.ordinal()]) {
            case 1: {
                string = "case_sensitive";
                break;
            }
            case 2: {
                string = "case_insensitive";
                break;
            }
            default: {
                throw new NoWhenBranchMatchedException();
            }
        }
        return ions.newSymbol(string);
    }

    public static BindingCase[] values() {
        return (BindingCase[])$VALUES.clone();
    }

    public static BindingCase valueOf(String string) {
        return Enum.valueOf(BindingCase.class, string);
    }

    @Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006\u00a8\u0006\u0007"}, d2={"Lorg/partiql/lang/eval/BindingCase$Companion;", "", "()V", "fromIonValue", "Lorg/partiql/lang/eval/BindingCase;", "sym", "Lcom/amazon/ion/IonValue;", "lang"})
    public static final class Companion {
        /*
         * Unable to fully structure code
         */
        @NotNull
        public final BindingCase fromIonValue(@NotNull IonValue sym) {
            block5: {
                block4: {
                    Intrinsics.checkParameterIsNotNull(sym, "sym");
                    v0 = IonValueExtensionsKt.stringValue(sym);
                    if (v0 == null) break block4;
                    var2_2 = v0;
                    switch (var2_2.hashCode()) {
                        case -672871294: {
                            if (!var2_2.equals("case_insensitive")) ** break;
                            break;
                        }
                        case -1368356793: {
                            if (!var2_2.equals("case_sensitive")) ** break;
                            v1 = BindingCase.SENSITIVE;
                            break block5;
                        }
                    }
                    v1 = BindingCase.INSENSITIVE;
                    break block5;
                }
                v2 = ExceptionsKt.errNoContext("Unable to convert ion value '" + IonValueExtensionsKt.stringValue(sym) + "' to a BindingCase instance", true);
                throw null;
            }
            return v1;
        }

        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

