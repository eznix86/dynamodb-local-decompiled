/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package org.partiql.lang.ast;

import com.amazon.ion.IonSystem;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.partiql.lang.ast.ExprNode;
import org.partiql.lang.ast.Meta;
import org.partiql.lang.ast.MetaContainer;
import org.partiql.lang.ast.MetaKt;
import org.partiql.lang.ast.SetQuantifier;
import org.partiql.lang.ast.StatementTransformer;
import org.partiql.lang.domains.PartiqlAst;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=2, d1={"\u0000<\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010$\n\u0002\u0010\u000e\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u001a\u0014\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0004H\u0000\u001a\u0012\u0010\u0000\u001a\u00020\u0001*\u00020\u00052\u0006\u0010\u0003\u001a\u00020\u0004\u001a\f\u0010\u0006\u001a\u00020\u0007*\u00020\bH\u0000\u001a \u0010\t\u001a\u00060\nj\u0002`\u000b*\u0012\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\u000e0\fj\u0002`\u000fH\u0000*\u0018\b\u0000\u0010\u0010\"\u0002`\u000f2\u000e\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\u000e0\f*\f\b\u0000\u0010\u0011\"\u00020\n2\u00020\n\u00a8\u0006\u0012"}, d2={"toExprNode", "Lorg/partiql/lang/ast/ExprNode;", "Lorg/partiql/lang/domains/PartiqlAst$Expr;", "ion", "Lcom/amazon/ion/IonSystem;", "Lorg/partiql/lang/domains/PartiqlAst$Statement;", "toExprNodeSetQuantifier", "Lorg/partiql/lang/ast/SetQuantifier;", "Lorg/partiql/lang/domains/PartiqlAst$SetQuantifier;", "toPartiQlMetaContainer", "Lorg/partiql/lang/ast/MetaContainer;", "Lorg/partiql/lang/ast/PartiQlMetaContainer;", "", "", "", "Lcom/amazon/ionelement/api/MetaContainer;", "IonElementMetaContainer", "PartiQlMetaContainer", "lang"})
public final class StatementToExprNodeKt {
    @NotNull
    public static final ExprNode toExprNode(@NotNull PartiqlAst.Statement $this$toExprNode, @NotNull IonSystem ion) {
        Intrinsics.checkParameterIsNotNull($this$toExprNode, "$this$toExprNode");
        Intrinsics.checkParameterIsNotNull(ion, "ion");
        return new StatementTransformer(ion).transform($this$toExprNode);
    }

    @NotNull
    public static final ExprNode toExprNode(@NotNull PartiqlAst.Expr $this$toExprNode, @NotNull IonSystem ion) {
        Intrinsics.checkParameterIsNotNull($this$toExprNode, "$this$toExprNode");
        Intrinsics.checkParameterIsNotNull(ion, "ion");
        return new StatementTransformer(ion).transform($this$toExprNode);
    }

    @NotNull
    public static final SetQuantifier toExprNodeSetQuantifier(@NotNull PartiqlAst.SetQuantifier $this$toExprNodeSetQuantifier) {
        SetQuantifier setQuantifier;
        Intrinsics.checkParameterIsNotNull($this$toExprNodeSetQuantifier, "$this$toExprNodeSetQuantifier");
        PartiqlAst.SetQuantifier setQuantifier2 = $this$toExprNodeSetQuantifier;
        if (setQuantifier2 instanceof PartiqlAst.SetQuantifier.All) {
            setQuantifier = SetQuantifier.ALL;
        } else if (setQuantifier2 instanceof PartiqlAst.SetQuantifier.Distinct) {
            setQuantifier = SetQuantifier.DISTINCT;
        } else {
            throw new NoWhenBranchMatchedException();
        }
        return setQuantifier;
    }

    /*
     * WARNING - void declaration
     */
    @NotNull
    public static final MetaContainer toPartiQlMetaContainer(@NotNull Map<String, ? extends Object> $this$toPartiQlMetaContainer) {
        void $this$mapTo$iv$iv;
        Intrinsics.checkParameterIsNotNull($this$toPartiQlMetaContainer, "$this$toPartiQlMetaContainer");
        Iterable $this$map$iv = $this$toPartiQlMetaContainer.values();
        boolean $i$f$map = false;
        Iterable iterable = $this$map$iv;
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
        boolean $i$f$mapTo = false;
        Iterator iterator2 = $this$mapTo$iv$iv.iterator();
        while (iterator2.hasNext()) {
            Meta partiQlMeta;
            void it;
            Object item$iv$iv;
            Object t = item$iv$iv = iterator2.next();
            Collection collection = destination$iv$iv;
            boolean bl = false;
            Object v0 = it;
            if (!(v0 instanceof Meta)) {
                v0 = null;
            }
            if ((Meta)v0 == null) {
                String string = "The meta was not an instance of Meta.";
                boolean bl2 = false;
                throw (Throwable)new IllegalStateException(string.toString());
            }
            Meta meta = partiQlMeta;
            collection.add(meta);
        }
        List nonLocationMetas = (List)destination$iv$iv;
        return MetaKt.metaContainerOf(nonLocationMetas);
    }
}

