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

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0080\b\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\t\u0010\u000b\u001a\u00020\u0003H\u00c6\u0003J\u0013\u0010\f\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u0003H\u00c6\u0001J\u0013\u0010\r\u001a\u00020\u000e2\b\u0010\u000f\u001a\u0004\u0018\u00010\u0010H\u00d6\u0003J\t\u0010\u0011\u001a\u00020\u0003H\u00d6\u0001J\t\u0010\u0012\u001a\u00020\u0013H\u00d6\u0001R\u0014\u0010\u0005\u001a\u00020\u0006X\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n\u00a8\u0006\u0014"}, d2={"Lorg/partiql/lang/eval/builtins/timestamp/FractionOfSecondPatternSymbol;", "Lorg/partiql/lang/eval/builtins/timestamp/PatternSymbol;", "precision", "", "(I)V", "field", "Lorg/partiql/lang/eval/builtins/timestamp/TimestampField;", "getField", "()Lorg/partiql/lang/eval/builtins/timestamp/TimestampField;", "getPrecision", "()I", "component1", "copy", "equals", "", "other", "", "hashCode", "toString", "", "lang"})
public final class FractionOfSecondPatternSymbol
extends PatternSymbol {
    @NotNull
    private final TimestampField field;
    private final int precision;

    @Override
    @NotNull
    public TimestampField getField() {
        return this.field;
    }

    public final int getPrecision() {
        return this.precision;
    }

    public FractionOfSecondPatternSymbol(int precision) {
        super(null);
        this.precision = precision;
        this.field = TimestampField.FRACTION_OF_SECOND;
    }

    public final int component1() {
        return this.precision;
    }

    @NotNull
    public final FractionOfSecondPatternSymbol copy(int precision) {
        return new FractionOfSecondPatternSymbol(precision);
    }

    public static /* synthetic */ FractionOfSecondPatternSymbol copy$default(FractionOfSecondPatternSymbol fractionOfSecondPatternSymbol, int n, int n2, Object object) {
        if ((n2 & 1) != 0) {
            n = fractionOfSecondPatternSymbol.precision;
        }
        return fractionOfSecondPatternSymbol.copy(n);
    }

    @NotNull
    public String toString() {
        return "FractionOfSecondPatternSymbol(precision=" + this.precision + ")";
    }

    public int hashCode() {
        return Integer.hashCode(this.precision);
    }

    public boolean equals(@Nullable Object object) {
        block3: {
            block2: {
                if (this == object) break block2;
                if (!(object instanceof FractionOfSecondPatternSymbol)) break block3;
                FractionOfSecondPatternSymbol fractionOfSecondPatternSymbol = (FractionOfSecondPatternSymbol)object;
                if (this.precision != fractionOfSecondPatternSymbol.precision) break block3;
            }
            return true;
        }
        return false;
    }
}

