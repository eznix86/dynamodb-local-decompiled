/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package org.partiql.lang.eval.visitors;

import kotlin.Metadata;
import kotlin.jvm.JvmField;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.partiql.lang.domains.PartiqlAst;
import org.partiql.lang.eval.visitors.AggregateSupportVisitorTransform;
import org.partiql.lang.eval.visitors.FromSourceAliasVisitorTransform;
import org.partiql.lang.eval.visitors.GroupByItemAliasVisitorTransform;
import org.partiql.lang.eval.visitors.GroupByPathExpressionVisitorTransform;
import org.partiql.lang.eval.visitors.PipelinedVisitorTransform;
import org.partiql.lang.eval.visitors.SelectListItemAliasVisitorTransform;
import org.partiql.lang.eval.visitors.SelectStarVisitorTransform;
import org.partiql.lang.eval.visitors.VisitorTransformBase;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=2, d1={"\u0000\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\u0006\u0010\u0002\u001a\u00020\u0003\"\u0010\u0010\u0000\u001a\u00020\u00018\u0000X\u0081\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0004"}, d2={"IDENTITY_VISITOR_TRANSFORM", "Lorg/partiql/lang/domains/PartiqlAst$VisitorTransform;", "basicVisitorTransforms", "Lorg/partiql/lang/eval/visitors/PipelinedVisitorTransform;", "lang"})
public final class VisitorTransformsKt {
    @JvmField
    @NotNull
    public static final PartiqlAst.VisitorTransform IDENTITY_VISITOR_TRANSFORM = new VisitorTransformBase(){

        @NotNull
        public PartiqlAst.Statement transformStatement(@NotNull PartiqlAst.Statement node) {
            Intrinsics.checkParameterIsNotNull(node, "node");
            return node;
        }
    };

    @NotNull
    public static final PipelinedVisitorTransform basicVisitorTransforms() {
        return new PipelinedVisitorTransform(new SelectListItemAliasVisitorTransform(), new FromSourceAliasVisitorTransform(), new GroupByItemAliasVisitorTransform(0, 1, null), new AggregateSupportVisitorTransform(), new GroupByPathExpressionVisitorTransform(null, 1, null), new SelectStarVisitorTransform());
    }
}

