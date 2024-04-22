/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.sequences;

import java.util.Iterator;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.Sequence;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010(\n\u0000\b\u0000\u0018\u0000*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u0002B'\u0012\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00028\u00000\u0002\u0012\u0012\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00020\u00060\u0005\u00a2\u0006\u0002\u0010\u0007J\u000f\u0010\b\u001a\b\u0012\u0004\u0012\u00028\u00000\tH\u0096\u0002R\u001a\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00020\u00060\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00028\u00000\u0002X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\n"}, d2={"Lkotlin/sequences/DropWhileSequence;", "T", "Lkotlin/sequences/Sequence;", "sequence", "predicate", "Lkotlin/Function1;", "", "(Lkotlin/sequences/Sequence;Lkotlin/jvm/functions/Function1;)V", "iterator", "", "kotlin-stdlib"})
public final class DropWhileSequence<T>
implements Sequence<T> {
    @NotNull
    private final Sequence<T> sequence;
    @NotNull
    private final Function1<T, Boolean> predicate;

    public DropWhileSequence(@NotNull Sequence<? extends T> sequence, @NotNull Function1<? super T, Boolean> predicate) {
        Intrinsics.checkNotNullParameter(sequence, "sequence");
        Intrinsics.checkNotNullParameter(predicate, "predicate");
        this.sequence = sequence;
        this.predicate = predicate;
    }

    @Override
    @NotNull
    public Iterator<T> iterator() {
        return new Iterator<T>(this){
            @NotNull
            private final Iterator<T> iterator;
            private int dropState;
            @Nullable
            private T nextItem;
            final /* synthetic */ DropWhileSequence<T> this$0;
            {
                this.this$0 = $receiver;
                this.iterator = DropWhileSequence.access$getSequence$p($receiver).iterator();
                this.dropState = -1;
            }

            @NotNull
            public final Iterator<T> getIterator() {
                return this.iterator;
            }

            public final int getDropState() {
                return this.dropState;
            }

            public final void setDropState(int n) {
                this.dropState = n;
            }

            @Nullable
            public final T getNextItem() {
                return this.nextItem;
            }

            public final void setNextItem(@Nullable T t) {
                this.nextItem = t;
            }

            private final void drop() {
                while (this.iterator.hasNext()) {
                    T item = this.iterator.next();
                    if (((Boolean)DropWhileSequence.access$getPredicate$p(this.this$0).invoke(item)).booleanValue()) continue;
                    this.nextItem = item;
                    this.dropState = 1;
                    return;
                }
                this.dropState = 0;
            }

            public T next() {
                if (this.dropState == -1) {
                    this.drop();
                }
                if (this.dropState == 1) {
                    T result = this.nextItem;
                    this.nextItem = null;
                    this.dropState = 0;
                    return result;
                }
                return this.iterator.next();
            }

            public boolean hasNext() {
                if (this.dropState == -1) {
                    this.drop();
                }
                return this.dropState == 1 || this.iterator.hasNext();
            }

            public void remove() {
                throw new UnsupportedOperationException("Operation is not supported for read-only collection");
            }
        };
    }

    public static final /* synthetic */ Sequence access$getSequence$p(DropWhileSequence $this) {
        return $this.sequence;
    }

    public static final /* synthetic */ Function1 access$getPredicate$p(DropWhileSequence $this) {
        return $this.predicate;
    }
}

