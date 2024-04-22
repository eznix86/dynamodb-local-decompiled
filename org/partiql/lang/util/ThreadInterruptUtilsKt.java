/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package org.partiql.lang.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=2, d1={"\u0000 \n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\u001a\b\u0010\u0000\u001a\u00020\u0001H\u0000\u001aH\u0010\u0002\u001a\u0002H\u0003\"\u0004\b\u0000\u0010\u0004\"\u0004\b\u0001\u0010\u0003*\b\u0012\u0004\u0012\u0002H\u00040\u00052\u0006\u0010\u0006\u001a\u0002H\u00032\u001a\b\u0004\u0010\u0007\u001a\u0014\u0012\u0004\u0012\u0002H\u0003\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00030\bH\u0080\b\u00a2\u0006\u0002\u0010\t\u001a;\u0010\n\u001a\b\u0012\u0004\u0012\u0002H\u000b0\u0005\"\u0004\b\u0000\u0010\u0004\"\u0004\b\u0001\u0010\u000b*\b\u0012\u0004\u0012\u0002H\u00040\u00052\u0014\b\u0004\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u000b0\fH\u0080\b\u00a8\u0006\r"}, d2={"checkThreadInterrupted", "", "interruptibleFold", "A", "T", "", "initial", "block", "Lkotlin/Function2;", "(Ljava/util/List;Ljava/lang/Object;Lkotlin/jvm/functions/Function2;)Ljava/lang/Object;", "interruptibleMap", "R", "Lkotlin/Function1;", "lang"})
public final class ThreadInterruptUtilsKt {
    public static final void checkThreadInterrupted() {
        if (Thread.interrupted()) {
            throw (Throwable)new InterruptedException();
        }
    }

    /*
     * WARNING - void declaration
     */
    @NotNull
    public static final <T, R> List<R> interruptibleMap(@NotNull List<? extends T> $this$interruptibleMap, @NotNull Function1<? super T, ? extends R> block) {
        void $this$mapTo$iv$iv;
        int $i$f$interruptibleMap = 0;
        Intrinsics.checkParameterIsNotNull($this$interruptibleMap, "$this$interruptibleMap");
        Intrinsics.checkParameterIsNotNull(block, "block");
        Iterable $this$map$iv = $this$interruptibleMap;
        boolean $i$f$map = false;
        Iterable iterable = $this$map$iv;
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
        boolean $i$f$mapTo = false;
        Iterator iterator2 = $this$mapTo$iv$iv.iterator();
        while (iterator2.hasNext()) {
            void it;
            Object item$iv$iv;
            Object t = item$iv$iv = iterator2.next();
            Collection collection = destination$iv$iv;
            boolean bl = false;
            ThreadInterruptUtilsKt.checkThreadInterrupted();
            R r = block.invoke(it);
            collection.add(r);
        }
        return (List)destination$iv$iv;
    }

    /*
     * WARNING - void declaration
     */
    public static final <T, A> A interruptibleFold(@NotNull List<? extends T> $this$interruptibleFold, A initial, @NotNull Function2<? super A, ? super T, ? extends A> block) {
        int $i$f$interruptibleFold = 0;
        Intrinsics.checkParameterIsNotNull($this$interruptibleFold, "$this$interruptibleFold");
        Intrinsics.checkParameterIsNotNull(block, "block");
        Iterable $this$fold$iv = $this$interruptibleFold;
        boolean $i$f$fold = false;
        A accumulator$iv = initial;
        Iterator iterator2 = $this$fold$iv.iterator();
        while (iterator2.hasNext()) {
            void curr;
            Object element$iv;
            Object t = element$iv = iterator2.next();
            A acc = accumulator$iv;
            boolean bl = false;
            ThreadInterruptUtilsKt.checkThreadInterrupted();
            accumulator$iv = block.invoke(acc, curr);
        }
        return accumulator$iv;
    }
}

