/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package org.partiql.lang.eval.builtins.timestamp;

import kotlin.Metadata;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.partiql.lang.eval.builtins.timestamp.PatternSymbol;
import org.partiql.lang.eval.builtins.timestamp.TimestampField;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\b\u0000\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0013\u0010\u0007\u001a\u00020\b2\b\u0010\t\u001a\u0004\u0018\u00010\nH\u0096\u0002J\b\u0010\u000b\u001a\u00020\fH\u0016R\u0014\u0010\u0003\u001a\u00020\u0004X\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006\r"}, d2={"Lorg/partiql/lang/eval/builtins/timestamp/AmPmPatternSymbol;", "Lorg/partiql/lang/eval/builtins/timestamp/PatternSymbol;", "()V", "field", "Lorg/partiql/lang/eval/builtins/timestamp/TimestampField;", "getField", "()Lorg/partiql/lang/eval/builtins/timestamp/TimestampField;", "equals", "", "other", "", "hashCode", "", "lang"})
public final class AmPmPatternSymbol
extends PatternSymbol {
    @NotNull
    private final TimestampField field = TimestampField.AM_PM;

    @Override
    @NotNull
    public TimestampField getField() {
        return this.field;
    }

    public boolean equals(@Nullable Object other) {
        return this.getClass().isInstance(other);
    }

    public int hashCode() {
        return this.getField().hashCode();
    }

    public AmPmPatternSymbol() {
        super(null);
    }
}

