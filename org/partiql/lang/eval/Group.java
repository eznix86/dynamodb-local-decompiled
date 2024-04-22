/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package org.partiql.lang.eval;

import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.partiql.lang.eval.ExprValue;
import org.partiql.lang.eval.RegisterBank;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\b\u0007\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006R\u0017\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00030\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000e\u00a8\u0006\u000f"}, d2={"Lorg/partiql/lang/eval/Group;", "", "key", "Lorg/partiql/lang/eval/ExprValue;", "registers", "Lorg/partiql/lang/eval/RegisterBank;", "(Lorg/partiql/lang/eval/ExprValue;Lorg/partiql/lang/eval/RegisterBank;)V", "groupValues", "", "getGroupValues", "()Ljava/util/List;", "getKey", "()Lorg/partiql/lang/eval/ExprValue;", "getRegisters", "()Lorg/partiql/lang/eval/RegisterBank;", "lang"})
public final class Group {
    @NotNull
    private final List<ExprValue> groupValues;
    @NotNull
    private final ExprValue key;
    @NotNull
    private final RegisterBank registers;

    @NotNull
    public final List<ExprValue> getGroupValues() {
        return this.groupValues;
    }

    @NotNull
    public final ExprValue getKey() {
        return this.key;
    }

    @NotNull
    public final RegisterBank getRegisters() {
        return this.registers;
    }

    public Group(@NotNull ExprValue key, @NotNull RegisterBank registers) {
        Intrinsics.checkParameterIsNotNull(key, "key");
        Intrinsics.checkParameterIsNotNull(registers, "registers");
        this.key = key;
        this.registers = registers;
        this.groupValues = new ArrayList();
    }
}

