/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package org.partiql.lang.eval;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.partiql.lang.eval.ExprAggregator;
import org.partiql.lang.eval.Register;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0011\u0010\t\u001a\u00020\u00072\u0006\u0010\n\u001a\u00020\u0003H\u0086\u0002J\u0019\u0010\u000b\u001a\u00020\f2\u0006\u0010\n\u001a\u00020\u00032\u0006\u0010\r\u001a\u00020\u000eH\u0086\u0002R\u0016\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004\u00a2\u0006\u0004\n\u0002\u0010\b\u00a8\u0006\u000f"}, d2={"Lorg/partiql/lang/eval/RegisterBank;", "", "size", "", "(I)V", "bank", "", "Lorg/partiql/lang/eval/Register;", "[Lorg/partiql/lang/eval/Register;", "get", "index", "set", "", "aggregator", "Lorg/partiql/lang/eval/ExprAggregator;", "lang"})
public final class RegisterBank {
    private final Register[] bank;

    @NotNull
    public final Register get(int index) {
        return this.bank[index];
    }

    public final void set(int index, @NotNull ExprAggregator aggregator) {
        Intrinsics.checkParameterIsNotNull(aggregator, "aggregator");
        this.bank[index] = new Register(aggregator){
            @NotNull
            private final ExprAggregator aggregator;
            final /* synthetic */ ExprAggregator $aggregator;

            @NotNull
            public ExprAggregator getAggregator() {
                return this.aggregator;
            }
            {
                this.$aggregator = $captured_local_variable$0;
                this.aggregator = $captured_local_variable$0;
            }
        };
    }

    public RegisterBank(int size) {
        Register[] registerArray;
        RegisterBank registerBank = this;
        Register[] registerArray2 = new Register[size];
        int n = 0;
        while (n < size) {
            Register register;
            int n2 = n;
            int n3 = n++;
            registerArray = registerArray2;
            boolean bl = false;
            registerArray[n3] = register = Register.Companion.getEMPTY();
        }
        registerArray = registerArray2;
        registerBank.bank = registerArray;
    }
}

