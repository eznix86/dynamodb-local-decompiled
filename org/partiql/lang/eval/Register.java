/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package org.partiql.lang.eval;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import org.jetbrains.annotations.NotNull;
import org.partiql.lang.eval.ExprAggregator;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\b&\u0018\u0000 \u00072\u00020\u0001:\u0001\u0007B\u0005\u00a2\u0006\u0002\u0010\u0002R\u0012\u0010\u0003\u001a\u00020\u0004X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006\b"}, d2={"Lorg/partiql/lang/eval/Register;", "", "()V", "aggregator", "Lorg/partiql/lang/eval/ExprAggregator;", "getAggregator", "()Lorg/partiql/lang/eval/ExprAggregator;", "Companion", "lang"})
public abstract class Register {
    @NotNull
    private static final Register EMPTY;
    public static final Companion Companion;

    @NotNull
    public abstract ExprAggregator getAggregator();

    static {
        Companion = new Companion(null);
        EMPTY = new Register(){

            @NotNull
            public ExprAggregator getAggregator() {
                throw (Throwable)new UnsupportedOperationException("Register is not an aggregator");
            }
        };
    }

    @Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u0011\u0010\u0003\u001a\u00020\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006\u0007"}, d2={"Lorg/partiql/lang/eval/Register$Companion;", "", "()V", "EMPTY", "Lorg/partiql/lang/eval/Register;", "getEMPTY", "()Lorg/partiql/lang/eval/Register;", "lang"})
    public static final class Companion {
        @NotNull
        public final Register getEMPTY() {
            return EMPTY;
        }

        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

