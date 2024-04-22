/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package org.partiql.lang.eval.visitors;

import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.partiql.lang.domains.PartiqlAst;
import org.partiql.lang.util.ThreadInterruptUtilsKt;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\b\u0002\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0019\u0012\u0012\u0010\u0002\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00010\u0003\"\u00020\u0001\u00a2\u0006\u0002\u0010\u0004J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\bH\u0016R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00010\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\n"}, d2={"Lorg/partiql/lang/eval/visitors/PipelinedVisitorTransform;", "Lorg/partiql/lang/domains/PartiqlAst$VisitorTransform;", "transformers", "", "([Lorg/partiql/lang/domains/PartiqlAst$VisitorTransform;)V", "transformerList", "", "transformStatement", "Lorg/partiql/lang/domains/PartiqlAst$Statement;", "node", "lang"})
public final class PipelinedVisitorTransform
extends PartiqlAst.VisitorTransform {
    private final List<PartiqlAst.VisitorTransform> transformerList;

    /*
     * WARNING - void declaration
     */
    @Override
    @NotNull
    public PartiqlAst.Statement transformStatement(@NotNull PartiqlAst.Statement node) {
        Intrinsics.checkParameterIsNotNull(node, "node");
        List<PartiqlAst.VisitorTransform> $this$interruptibleFold$iv = this.transformerList;
        boolean $i$f$interruptibleFold = false;
        Iterable $this$fold$iv$iv = $this$interruptibleFold$iv;
        boolean $i$f$fold = false;
        PartiqlAst.Statement accumulator$iv$iv = node;
        Iterator iterator2 = $this$fold$iv$iv.iterator();
        while (iterator2.hasNext()) {
            void transformer;
            void curr$iv;
            Object element$iv$iv;
            Object t = element$iv$iv = iterator2.next();
            PartiqlAst.Statement acc$iv = accumulator$iv$iv;
            boolean bl = false;
            ThreadInterruptUtilsKt.checkThreadInterrupted();
            PartiqlAst.VisitorTransform visitorTransform = (PartiqlAst.VisitorTransform)curr$iv;
            PartiqlAst.Statement intermediateNode = acc$iv;
            boolean bl2 = false;
            accumulator$iv$iv = transformer.transformStatement(intermediateNode);
        }
        return accumulator$iv$iv;
    }

    public PipelinedVisitorTransform(@NotNull PartiqlAst.VisitorTransform ... transformers) {
        Intrinsics.checkParameterIsNotNull(transformers, "transformers");
        this.transformerList = ArraysKt.toList(transformers);
    }
}

