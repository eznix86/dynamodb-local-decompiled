/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package org.partiql.lang.ast;

import kotlin.Metadata;
import kotlin.ranges.IntRange;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0002\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0005R\u0011\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\t\u00a8\u0006\n"}, d2={"Lorg/partiql/lang/ast/SexpValidationRules;", "", "arityFrom", "", "arityTo", "(II)V", "arityRange", "Lkotlin/ranges/IntRange;", "getArityRange", "()Lkotlin/ranges/IntRange;", "lang"})
final class SexpValidationRules {
    @NotNull
    private final IntRange arityRange;

    @NotNull
    public final IntRange getArityRange() {
        return this.arityRange;
    }

    public SexpValidationRules(int arityFrom, int arityTo) {
        this.arityRange = new IntRange(arityFrom, arityTo);
    }
}

