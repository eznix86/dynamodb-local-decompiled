/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package org.partiql.lang.eval.like;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Stack;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.partiql.lang.eval.like.CheckpointIterator;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000.\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0005\b\u0000\u0018\u0000*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u0002B\u0013\u0012\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00028\u00000\u0004\u00a2\u0006\u0002\u0010\u0005J\b\u0010\n\u001a\u00020\u000bH\u0016J\t\u0010\f\u001a\u00020\rH\u0096\u0002J\u000e\u0010\u000e\u001a\u00028\u0000H\u0096\u0002\u00a2\u0006\u0002\u0010\u000fJ\b\u0010\u0010\u001a\u00020\u000bH\u0016J\b\u0010\u0011\u001a\u00020\u000bH\u0016R\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00028\u00000\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\bX\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0012"}, d2={"Lorg/partiql/lang/eval/like/CheckpointIteratorImpl;", "T", "Lorg/partiql/lang/eval/like/CheckpointIterator;", "backingList", "", "(Ljava/util/List;)V", "checkpointStack", "Ljava/util/Stack;", "", "idx", "discardCheckpoint", "", "hasNext", "", "next", "()Ljava/lang/Object;", "restoreCheckpoint", "saveCheckpoint", "lang"})
public final class CheckpointIteratorImpl<T>
implements CheckpointIterator<T> {
    private final Stack<Integer> checkpointStack;
    private int idx;
    private final List<T> backingList;

    @Override
    public boolean hasNext() {
        return this.backingList.size() - 1 > this.idx;
    }

    @Override
    public T next() {
        if (!this.hasNext()) {
            throw (Throwable)new NoSuchElementException();
        }
        CheckpointIteratorImpl checkpointIteratorImpl = this;
        ++checkpointIteratorImpl.idx;
        return this.backingList.get(checkpointIteratorImpl.idx);
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

    public CheckpointIteratorImpl(@NotNull List<? extends T> backingList) {
        Intrinsics.checkParameterIsNotNull(backingList, "backingList");
        this.backingList = backingList;
        this.checkpointStack = new Stack();
        this.idx = -1;
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }
}

