/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.io;

import java.io.BufferedReader;
import java.util.Iterator;
import java.util.NoSuchElementException;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.Sequence;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010(\n\u0000\b\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u00a2\u0006\u0002\u0010\u0005J\u000f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00020\u0007H\u0096\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\b"}, d2={"Lkotlin/io/LinesSequence;", "Lkotlin/sequences/Sequence;", "", "reader", "Ljava/io/BufferedReader;", "(Ljava/io/BufferedReader;)V", "iterator", "", "kotlin-stdlib"})
final class LinesSequence
implements Sequence<String> {
    @NotNull
    private final BufferedReader reader;

    public LinesSequence(@NotNull BufferedReader reader) {
        Intrinsics.checkNotNullParameter(reader, "reader");
        this.reader = reader;
    }

    @Override
    @NotNull
    public Iterator<String> iterator() {
        return new Iterator<String>(this){
            @Nullable
            private String nextValue;
            private boolean done;
            final /* synthetic */ LinesSequence this$0;
            {
                this.this$0 = $receiver;
            }

            public boolean hasNext() {
                if (this.nextValue == null && !this.done) {
                    this.nextValue = LinesSequence.access$getReader$p(this.this$0).readLine();
                    if (this.nextValue == null) {
                        this.done = true;
                    }
                }
                return this.nextValue != null;
            }

            @NotNull
            public String next() {
                if (!this.hasNext()) {
                    throw new NoSuchElementException();
                }
                String answer = this.nextValue;
                this.nextValue = null;
                String string = answer;
                Intrinsics.checkNotNull(string);
                return string;
            }

            public void remove() {
                throw new UnsupportedOperationException("Operation is not supported for read-only collection");
            }
        };
    }

    public static final /* synthetic */ BufferedReader access$getReader$p(LinesSequence $this) {
        return $this.reader;
    }
}

