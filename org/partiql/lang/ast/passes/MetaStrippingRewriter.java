/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package org.partiql.lang.ast.passes;

import kotlin.Deprecated;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.partiql.lang.ast.DataType;
import org.partiql.lang.ast.ExprNode;
import org.partiql.lang.ast.HasMetas;
import org.partiql.lang.ast.Meta;
import org.partiql.lang.ast.MetaContainer;
import org.partiql.lang.ast.MetaKt;
import org.partiql.lang.ast.passes.AstRewriterBase;

@Deprecated(message="Will be removed after existing tests no longer require stripping metas")
@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0007\u0018\u0000 \n2\u00020\u0001:\u0001\nB\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0004H\u0016J\u0010\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tH\u0016\u00a8\u0006\u000b"}, d2={"Lorg/partiql/lang/ast/passes/MetaStrippingRewriter;", "Lorg/partiql/lang/ast/passes/AstRewriterBase;", "()V", "rewriteDataType", "Lorg/partiql/lang/ast/DataType;", "dataType", "rewriteMetas", "Lorg/partiql/lang/ast/MetaContainer;", "itemWithMetas", "Lorg/partiql/lang/ast/HasMetas;", "Companion", "lang"})
public final class MetaStrippingRewriter
extends AstRewriterBase {
    private static final MetaContainer emptyMetas;
    public static final Companion Companion;

    @Override
    @NotNull
    public MetaContainer rewriteMetas(@NotNull HasMetas itemWithMetas) {
        Intrinsics.checkParameterIsNotNull(itemWithMetas, "itemWithMetas");
        return emptyMetas;
    }

    @Override
    @NotNull
    public DataType rewriteDataType(@NotNull DataType dataType) {
        Intrinsics.checkParameterIsNotNull(dataType, "dataType");
        return new DataType(dataType.getSqlDataType(), dataType.getArgs(), emptyMetas);
    }

    static {
        Companion = new Companion(null);
        emptyMetas = MetaKt.metaContainerOf(new Meta[0]);
    }

    @Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u0006R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\b"}, d2={"Lorg/partiql/lang/ast/passes/MetaStrippingRewriter$Companion;", "", "()V", "emptyMetas", "Lorg/partiql/lang/ast/MetaContainer;", "stripMetas", "Lorg/partiql/lang/ast/ExprNode;", "expr", "lang"})
    public static final class Companion {
        @NotNull
        public final ExprNode stripMetas(@NotNull ExprNode expr) {
            Intrinsics.checkParameterIsNotNull(expr, "expr");
            MetaStrippingRewriter rewriter = new MetaStrippingRewriter();
            return rewriter.rewriteExprNode(expr);
        }

        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

