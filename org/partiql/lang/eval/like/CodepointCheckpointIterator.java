/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package org.partiql.lang.eval.like;

import java.util.NoSuchElementException;
import java.util.Stack;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.partiql.lang.eval.like.CheckpointIterator;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0006\b\u0000\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u00a2\u0006\u0002\u0010\u0005J\b\u0010\n\u001a\u00020\u000bH\u0016J\t\u0010\f\u001a\u00020\rH\u0096\u0002J\u000e\u0010\u000e\u001a\u00020\u0002H\u0096\u0002\u00a2\u0006\u0002\u0010\u000fJ\b\u0010\u0010\u001a\u00020\u000bH\u0016J\b\u0010\u0011\u001a\u00020\u000bH\u0016J\u0006\u0010\u0012\u001a\u00020\u000bR\u0014\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0002X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0002X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0013"}, d2={"Lorg/partiql/lang/eval/like/CodepointCheckpointIterator;", "Lorg/partiql/lang/eval/like/CheckpointIterator;", "", "str", "", "(Ljava/lang/String;)V", "checkpointStack", "Ljava/util/Stack;", "codepointCount", "idx", "discardCheckpoint", "", "hasNext", "", "next", "()Ljava/lang/Integer;", "restoreCheckpoint", "saveCheckpoint", "skipToEnd", "lang"})
public final class CodepointCheckpointIterator
implements CheckpointIterator<Integer> {
    private final Stack<Integer> checkpointStack;
    private final int codepointCount;
    private int idx;
    private final String str;

    @Override
    public boolean hasNext() {
        return this.codepointCount - 1 > this.idx;
    }

    @Override
    @NotNull
    public Integer next() {
        if (!this.hasNext()) {
            throw (Throwable)new NoSuchElementException();
        }
        String string = this.str;
        CodepointCheckpointIterator codepointCheckpointIterator = this;
        ++codepointCheckpointIterator.idx;
        int n = codepointCheckpointIterator.idx;
        boolean bl = false;
        String string2 = string;
        if (string2 == null) {
            throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
        }
        return string2.codePointAt(n);
    }

    public final void skipToEnd() {
        this.idx = this.codepointCount;
    }

    @Override
    public void saveCheckpoint() {
        this.checkpointStack.push(this.idx);
    }

    @Override
    public void restoreCheckpoint() {
        Integer n = this.checkpointStack.pop();
        Intrinsics.checkExpressionValueIsNotNull(n, "checkpointStack.pop()");
        this.idx = ((Number)n).intValue();
    }

    @Override
    public void discardCheckpoint() {
        this.checkpointStack.pop();
    }

    public CodepointCheckpointIterator(@NotNull String str) {
        int n;
        Intrinsics.checkParameterIsNotNull(str, "str");
        this.str = str;
        this.checkpointStack = new Stack();
        String string = this.str;
        int n2 = 0;
        int n3 = this.str.length();
        CodepointCheckpointIterator codepointCheckpointIterator = this;
        boolean bl = false;
        String string2 = string;
        if (string2 == null) {
            throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
        }
        codepointCheckpointIterator.codepointCount = n = string2.codePointCount(n2, n3);
        this.idx = -1;
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }
}

