/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package org.partiql.lang.eval.builtins;

import java.util.List;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Triple;
import kotlin.collections.ArraysKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.IntRange;
import org.jetbrains.annotations.NotNull;
import org.partiql.lang.eval.Environment;
import org.partiql.lang.eval.ExceptionsKt;
import org.partiql.lang.eval.ExprValue;
import org.partiql.lang.eval.ExprValueExtensionsKt;
import org.partiql.lang.eval.ExprValueFactory;
import org.partiql.lang.eval.NullPropagatingExprFunction;
import org.partiql.lang.eval.builtins.TrimExprFunction$WhenMappings;
import org.partiql.lang.eval.builtins.TrimSpecification;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000J\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0015\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0004\b\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u001e\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u000b0\u000fH\u0016J(\u0010\u0010\u001a\u0014\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\b0\u00112\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u000b0\u000fH\u0002J\u0014\u0010\u0012\u001a\n \t*\u0004\u0018\u00010\b0\b*\u00020\u000bH\u0002J\u0014\u0010\u0013\u001a\u00020\u0014*\u00020\b2\u0006\u0010\u0015\u001a\u00020\bH\u0002J\u0014\u0010\u0016\u001a\u00020\u0017*\u00020\b2\u0006\u0010\u0015\u001a\u00020\bH\u0002J\u0014\u0010\u0018\u001a\u00020\u0014*\u00020\b2\u0006\u0010\u0015\u001a\u00020\bH\u0002J\u0014\u0010\u0019\u001a\u00020\u0017*\u00020\b2\u0006\u0010\u0015\u001a\u00020\bH\u0002J\u0014\u0010\u001a\u001a\u00020\u0014*\u00020\b2\u0006\u0010\u0015\u001a\u00020\bH\u0002R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0016\u0010\u0007\u001a\n \t*\u0004\u0018\u00010\b0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001b"}, d2={"Lorg/partiql/lang/eval/builtins/TrimExprFunction;", "Lorg/partiql/lang/eval/NullPropagatingExprFunction;", "valueFactory", "Lorg/partiql/lang/eval/ExprValueFactory;", "(Lorg/partiql/lang/eval/ExprValueFactory;)V", "DEFAULT_SPECIFICATION", "Lorg/partiql/lang/eval/builtins/TrimSpecification;", "DEFAULT_TO_REMOVE", "", "kotlin.jvm.PlatformType", "eval", "Lorg/partiql/lang/eval/ExprValue;", "env", "Lorg/partiql/lang/eval/Environment;", "args", "", "extractArguments", "Lkotlin/Triple;", "codePoints", "leadingTrim", "", "toRemove", "leadingTrimOffset", "", "trailingTrim", "trailingTrimOffSet", "trim", "lang"})
public final class TrimExprFunction
extends NullPropagatingExprFunction {
    private final int[] DEFAULT_TO_REMOVE;
    private final TrimSpecification DEFAULT_SPECIFICATION;

    private final int leadingTrimOffset(@NotNull int[] $this$leadingTrimOffset, int[] toRemove) {
        int offset;
        for (offset = 0; offset < $this$leadingTrimOffset.length && ArraysKt.contains(toRemove, $this$leadingTrimOffset[offset]); ++offset) {
        }
        return offset;
    }

    private final int trailingTrimOffSet(@NotNull int[] $this$trailingTrimOffSet, int[] toRemove) {
        int offset;
        for (offset = 0; offset < $this$trailingTrimOffSet.length && ArraysKt.contains(toRemove, $this$trailingTrimOffSet[$this$trailingTrimOffSet.length - offset - 1]); ++offset) {
        }
        return offset;
    }

    private final String leadingTrim(@NotNull int[] $this$leadingTrim, int[] toRemove) {
        int offset = this.leadingTrimOffset($this$leadingTrim, toRemove);
        int n = $this$leadingTrim.length - offset;
        boolean bl = false;
        return new String($this$leadingTrim, offset, n);
    }

    private final String trailingTrim(@NotNull int[] $this$trailingTrim, int[] toRemove) {
        int n = 0;
        int n2 = $this$trailingTrim.length - this.trailingTrimOffSet($this$trailingTrim, toRemove);
        boolean bl = false;
        return new String($this$trailingTrim, n, n2);
    }

    private final String trim(@NotNull int[] $this$trim, int[] toRemove) {
        int leadingOffset = this.leadingTrimOffset($this$trim, toRemove);
        int trailingOffset = this.trailingTrimOffSet($this$trim, toRemove);
        int length = Math.max(0, $this$trim.length - trailingOffset - leadingOffset);
        boolean bl = false;
        return new String($this$trim, leadingOffset, length);
    }

    /*
     * WARNING - void declaration
     */
    @Override
    @NotNull
    public ExprValue eval(@NotNull Environment env, @NotNull List<? extends ExprValue> args2) {
        ExprValue exprValue2;
        void type;
        Intrinsics.checkParameterIsNotNull(env, "env");
        Intrinsics.checkParameterIsNotNull(args2, "args");
        Triple<TrimSpecification, int[], int[]> triple = this.extractArguments(args2);
        TrimSpecification trimSpecification = triple.component1();
        int[] nArray = triple.component2();
        int[] string = triple.component3();
        switch (TrimExprFunction$WhenMappings.$EnumSwitchMapping$0[type.ordinal()]) {
            case 1: 
            case 2: {
                void toRemove;
                exprValue2 = this.getValueFactory().newString(this.trim(string, (int[])toRemove));
                break;
            }
            case 3: {
                void toRemove;
                exprValue2 = this.getValueFactory().newString(this.leadingTrim(string, (int[])toRemove));
                break;
            }
            case 4: {
                void toRemove;
                exprValue2 = this.getValueFactory().newString(this.trailingTrim(string, (int[])toRemove));
                break;
            }
            default: {
                throw new NoWhenBranchMatchedException();
            }
        }
        return exprValue2;
    }

    private final int[] codePoints(@NotNull ExprValue $this$codePoints) {
        return ExprValueExtensionsKt.stringValue($this$codePoints).codePoints().toArray();
    }

    private final Triple<TrimSpecification, int[], int[]> extractArguments(List<? extends ExprValue> args2) {
        Triple<TrimSpecification, int[], int[]> triple;
        switch (args2.size()) {
            case 1: {
                triple = new Triple<TrimSpecification, int[], int[]>(this.DEFAULT_SPECIFICATION, this.DEFAULT_TO_REMOVE, this.codePoints(args2.get(0)));
                break;
            }
            case 2: {
                int[] nArray;
                if (!args2.get(0).getType().isText()) {
                    Void void_ = ExceptionsKt.errNoContext("with two arguments trim's first argument must be either the specification or a 'to remove' string", false);
                    throw null;
                }
                TrimSpecification specification = TrimSpecification.Companion.from(args2.get(0));
                switch (TrimExprFunction$WhenMappings.$EnumSwitchMapping$1[specification.ordinal()]) {
                    case 1: {
                        nArray = this.codePoints(args2.get(0));
                        break;
                    }
                    default: {
                        nArray = this.DEFAULT_TO_REMOVE;
                    }
                }
                int[] toRemove = nArray;
                triple = new Triple<TrimSpecification, int[], int[]>(specification, toRemove, this.codePoints(args2.get(1)));
                break;
            }
            case 3: {
                TrimSpecification specification = TrimSpecification.Companion.from(args2.get(0));
                if (specification == TrimSpecification.NONE) {
                    Void void_ = ExceptionsKt.errNoContext('\'' + ExprValueExtensionsKt.stringValue(args2.get(0)) + "' is an unknown trim specification, " + "valid vales: " + TrimSpecification.Companion.getValidValues(), false);
                    throw null;
                }
                triple = new Triple<TrimSpecification, int[], int[]>(specification, this.codePoints(args2.get(1)), this.codePoints(args2.get(2)));
                break;
            }
            default: {
                Void void_ = ExceptionsKt.errNoContext("invalid trim arguments, should be unreachable", true);
                throw null;
            }
        }
        return triple;
    }

    public TrimExprFunction(@NotNull ExprValueFactory valueFactory) {
        Intrinsics.checkParameterIsNotNull(valueFactory, "valueFactory");
        int n = 1;
        super("trim", new IntRange(n, 3), valueFactory);
        this.DEFAULT_TO_REMOVE = " ".codePoints().toArray();
        this.DEFAULT_SPECIFICATION = TrimSpecification.BOTH;
    }
}

