/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package org.partiql.lang.ast;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0006\b\u0086\u0001\u0018\u0000 \b2\b\u0012\u0004\u0012\u00020\u00000\u0001:\u0001\bB\u000f\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0006\u0010\u0005\u001a\u00020\u0003R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000j\u0002\b\u0006j\u0002\b\u0007\u00a8\u0006\t"}, d2={"Lorg/partiql/lang/ast/CaseSensitivity;", "", "symbol", "", "(Ljava/lang/String;ILjava/lang/String;)V", "toSymbol", "SENSITIVE", "INSENSITIVE", "Companion", "lang"})
public final class CaseSensitivity
extends Enum<CaseSensitivity> {
    public static final /* enum */ CaseSensitivity SENSITIVE;
    public static final /* enum */ CaseSensitivity INSENSITIVE;
    private static final /* synthetic */ CaseSensitivity[] $VALUES;
    private final String symbol;
    public static final Companion Companion;

    static {
        CaseSensitivity[] caseSensitivityArray = new CaseSensitivity[2];
        CaseSensitivity[] caseSensitivityArray2 = caseSensitivityArray;
        caseSensitivityArray[0] = SENSITIVE = new CaseSensitivity("case_sensitive");
        caseSensitivityArray[1] = INSENSITIVE = new CaseSensitivity("case_insensitive");
        $VALUES = caseSensitivityArray;
        Companion = new Companion(null);
    }

    @NotNull
    public final String toSymbol() {
        return this.symbol;
    }

    private CaseSensitivity(String symbol) {
        this.symbol = symbol;
    }

    public static CaseSensitivity[] values() {
        return (CaseSensitivity[])$VALUES.clone();
    }

    public static CaseSensitivity valueOf(String string) {
        return Enum.valueOf(CaseSensitivity.class, string);
    }

    @Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006\u00a8\u0006\u0007"}, d2={"Lorg/partiql/lang/ast/CaseSensitivity$Companion;", "", "()V", "fromSymbol", "Lorg/partiql/lang/ast/CaseSensitivity;", "s", "", "lang"})
    public static final class Companion {
        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        @NotNull
        public final CaseSensitivity fromSymbol(@NotNull String s) {
            CaseSensitivity caseSensitivity;
            Intrinsics.checkParameterIsNotNull(s, "s");
            String string = s;
            switch (string.hashCode()) {
                case -672871294: {
                    if (!string.equals("case_insensitive")) throw (Throwable)new IllegalArgumentException("Unrecognized CaseSensitivity " + s);
                    break;
                }
                case -1368356793: {
                    if (!string.equals("case_sensitive")) throw (Throwable)new IllegalArgumentException("Unrecognized CaseSensitivity " + s);
                    caseSensitivity = SENSITIVE;
                    return caseSensitivity;
                }
            }
            caseSensitivity = INSENSITIVE;
            return caseSensitivity;
            throw (Throwable)new IllegalArgumentException("Unrecognized CaseSensitivity " + s);
        }

        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

