/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  kotlin.streams.jdk8.StreamsKt
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package org.partiql.lang.eval.like;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.stream.IntStream;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.streams.jdk8.StreamsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.partiql.lang.eval.like.CheckpointIterator;
import org.partiql.lang.eval.like.CheckpointIteratorImpl;
import org.partiql.lang.eval.like.CodepointCheckpointIterator;
import org.partiql.lang.eval.like.PatternPart;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=2, d1={"\u00000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0005\u001a\u001e\u0010\u0003\u001a\u00020\u00042\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u00062\u0006\u0010\b\u001a\u00020\tH\u0002\u001a\u001e\u0010\n\u001a\u00020\u00042\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00070\f2\u0006\u0010\r\u001a\u00020\u000eH\u0000\u001a\u001e\u0010\n\u001a\u00020\u00042\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u00062\u0006\u0010\b\u001a\u00020\tH\u0002\u001a%\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00070\f2\u0006\u0010\u0010\u001a\u00020\u000e2\b\u0010\u0011\u001a\u0004\u0018\u00010\u0001H\u0000\u00a2\u0006\u0002\u0010\u0012\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0082T\u00a2\u0006\u0002\n\u0000\"\u000e\u0010\u0002\u001a\u00020\u0001X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0013"}, d2={"ANY_ONE_CHAR", "", "ZERO_OR_MORE_OF_ANY_CHAR", "executeOnePart", "", "partsItr", "Lorg/partiql/lang/eval/like/CheckpointIterator;", "Lorg/partiql/lang/eval/like/PatternPart;", "charsItr", "Lorg/partiql/lang/eval/like/CodepointCheckpointIterator;", "executePattern", "parts", "", "str", "", "parsePattern", "pattern", "escapeChar", "(Ljava/lang/String;Ljava/lang/Integer;)Ljava/util/List;", "lang"})
public final class PatternPartKt {
    private static final int ZERO_OR_MORE_OF_ANY_CHAR = 37;
    private static final int ANY_ONE_CHAR = 95;

    @NotNull
    public static final List<PatternPart> parsePattern(@NotNull String pattern, @Nullable Integer escapeChar) {
        Intrinsics.checkParameterIsNotNull(pattern, "pattern");
        IntStream intStream = pattern.codePoints();
        Intrinsics.checkExpressionValueIsNotNull(intStream, "pattern.codePoints()");
        List codepointList = StreamsKt.toList((IntStream)intStream);
        ListIterator codepointsItr = codepointList.listIterator();
        ArrayList<PatternPart> parts = new ArrayList<PatternPart>();
        while (codepointsItr.hasNext()) {
            PatternPart patternPart;
            int c = ((Number)codepointsItr.next()).intValue();
            switch (c) {
                case 95: {
                    patternPart = PatternPart.AnyOneChar.INSTANCE;
                    break;
                }
                case 37: {
                    while (codepointsItr.hasNext() && ((Number)codepointList.get(codepointsItr.nextIndex())).intValue() == 37) {
                        codepointsItr.next();
                    }
                    patternPart = PatternPart.ZeroOrMoreOfAnyChar.INSTANCE;
                    break;
                }
                default: {
                    codepointsItr.previous();
                    ArrayList buffer = new ArrayList();
                    do {
                        int cc = ((Number)codepointsItr.next()).intValue();
                        if (escapeChar != null && cc == escapeChar) {
                            buffer.add(codepointsItr.next());
                            continue;
                        }
                        if (cc == 95 || cc == 37) {
                            codepointsItr.previous();
                            break;
                        }
                        buffer.add(cc);
                    } while (codepointsItr.hasNext());
                    patternPart = new PatternPart.ExactChars(CollectionsKt.toIntArray(buffer));
                }
            }
            parts.add(patternPart);
        }
        return parts;
    }

    public static final boolean executePattern(@NotNull List<? extends PatternPart> parts, @NotNull String str) {
        Intrinsics.checkParameterIsNotNull(parts, "parts");
        Intrinsics.checkParameterIsNotNull(str, "str");
        return PatternPartKt.executePattern((CheckpointIterator<PatternPart>)new CheckpointIteratorImpl<PatternPart>(parts), new CodepointCheckpointIterator(str));
    }

    private static final boolean executePattern(CheckpointIterator<PatternPart> partsItr, CodepointCheckpointIterator charsItr) {
        while (partsItr.hasNext()) {
            if (PatternPartKt.executeOnePart(partsItr, charsItr)) continue;
            return false;
        }
        return !charsItr.hasNext();
    }

    private static final boolean executeOnePart(CheckpointIterator<PatternPart> partsItr, CodepointCheckpointIterator charsItr) {
        PatternPart currentPart = (PatternPart)partsItr.next();
        if (currentPart instanceof PatternPart.AnyOneChar) {
            if (!charsItr.hasNext()) {
                return false;
            }
            charsItr.next();
            return true;
        }
        if (currentPart instanceof PatternPart.ExactChars) {
            int[] $this$forEach$iv = ((PatternPart.ExactChars)currentPart).getCodepoints();
            boolean $i$f$forEach = false;
            int[] nArray = $this$forEach$iv;
            int n = nArray.length;
            for (int i = 0; i < n; ++i) {
                int element$iv;
                int it = element$iv = nArray[i];
                boolean bl = false;
                if (charsItr.hasNext() && charsItr.next() == it) continue;
                return false;
            }
            return true;
        }
        if (Intrinsics.areEqual(currentPart, PatternPart.ZeroOrMoreOfAnyChar.INSTANCE)) {
            if (!partsItr.hasNext()) {
                charsItr.skipToEnd();
                return true;
            }
            while (true) {
                partsItr.saveCheckpoint();
                charsItr.saveCheckpoint();
                if (PatternPartKt.executePattern(partsItr, charsItr)) {
                    partsItr.discardCheckpoint();
                    charsItr.discardCheckpoint();
                    return true;
                }
                partsItr.restoreCheckpoint();
                charsItr.restoreCheckpoint();
                if (!charsItr.hasNext()) {
                    return false;
                }
                charsItr.next();
            }
        }
        return false;
    }
}

