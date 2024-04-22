/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package org.partiql.lang.eval.like;

import java.util.Arrays;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b0\u0018\u00002\u00020\u0001:\u0003\u0003\u0004\u0005B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u0082\u0001\u0003\u0006\u0007\b\u00a8\u0006\t"}, d2={"Lorg/partiql/lang/eval/like/PatternPart;", "", "()V", "AnyOneChar", "ExactChars", "ZeroOrMoreOfAnyChar", "Lorg/partiql/lang/eval/like/PatternPart$AnyOneChar;", "Lorg/partiql/lang/eval/like/PatternPart$ZeroOrMoreOfAnyChar;", "Lorg/partiql/lang/eval/like/PatternPart$ExactChars;", "lang"})
public abstract class PatternPart {
    private PatternPart() {
    }

    public /* synthetic */ PatternPart(DefaultConstructorMarker $constructor_marker) {
        this();
    }

    @Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2={"Lorg/partiql/lang/eval/like/PatternPart$AnyOneChar;", "Lorg/partiql/lang/eval/like/PatternPart;", "()V", "lang"})
    public static final class AnyOneChar
    extends PatternPart {
        public static final AnyOneChar INSTANCE;

        private AnyOneChar() {
            super(null);
        }

        static {
            AnyOneChar anyOneChar;
            INSTANCE = anyOneChar = new AnyOneChar();
        }
    }

    @Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2={"Lorg/partiql/lang/eval/like/PatternPart$ZeroOrMoreOfAnyChar;", "Lorg/partiql/lang/eval/like/PatternPart;", "()V", "lang"})
    public static final class ZeroOrMoreOfAnyChar
    extends PatternPart {
        public static final ZeroOrMoreOfAnyChar INSTANCE;

        private ZeroOrMoreOfAnyChar() {
            super(null);
        }

        static {
            ZeroOrMoreOfAnyChar zeroOrMoreOfAnyChar;
            INSTANCE = zeroOrMoreOfAnyChar = new ZeroOrMoreOfAnyChar();
        }
    }

    @Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0015\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\t\u0010\u0007\u001a\u00020\u0003H\u00c6\u0003J\u0013\u0010\b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u0003H\u00c6\u0001J\u0013\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\fH\u0096\u0002J\b\u0010\r\u001a\u00020\u000eH\u0016J\t\u0010\u000f\u001a\u00020\u0010H\u00d6\u0001R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006\u0011"}, d2={"Lorg/partiql/lang/eval/like/PatternPart$ExactChars;", "Lorg/partiql/lang/eval/like/PatternPart;", "codepoints", "", "([I)V", "getCodepoints", "()[I", "component1", "copy", "equals", "", "other", "", "hashCode", "", "toString", "", "lang"})
    public static final class ExactChars
    extends PatternPart {
        @NotNull
        private final int[] codepoints;

        public boolean equals(@Nullable Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof ExactChars)) {
                return false;
            }
            int[] nArray = this.codepoints;
            int[] nArray2 = ((ExactChars)other).codepoints;
            boolean bl = false;
            return Arrays.equals(nArray, nArray2);
        }

        public int hashCode() {
            int[] nArray = this.codepoints;
            boolean bl = false;
            return Arrays.hashCode(nArray);
        }

        @NotNull
        public final int[] getCodepoints() {
            return this.codepoints;
        }

        public ExactChars(@NotNull int[] codepoints) {
            Intrinsics.checkParameterIsNotNull(codepoints, "codepoints");
            super(null);
            this.codepoints = codepoints;
        }

        @NotNull
        public final int[] component1() {
            return this.codepoints;
        }

        @NotNull
        public final ExactChars copy(@NotNull int[] codepoints) {
            Intrinsics.checkParameterIsNotNull(codepoints, "codepoints");
            return new ExactChars(codepoints);
        }

        public static /* synthetic */ ExactChars copy$default(ExactChars exactChars, int[] nArray, int n, Object object) {
            if ((n & 1) != 0) {
                nArray = exactChars.codepoints;
            }
            return exactChars.copy(nArray);
        }

        @NotNull
        public String toString() {
            return "ExactChars(codepoints=" + Arrays.toString(this.codepoints) + ")";
        }
    }
}

