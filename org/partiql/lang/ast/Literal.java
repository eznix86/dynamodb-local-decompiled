/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package org.partiql.lang.ast;

import com.amazon.ion.IonValue;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.partiql.lang.ast.AstNode;
import org.partiql.lang.ast.ExprNode;
import org.partiql.lang.ast.MetaContainer;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\t\u0010\u0010\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0011\u001a\u00020\u0005H\u00c6\u0003J\u001d\u0010\u0012\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0005H\u00c6\u0001J\u0013\u0010\u0013\u001a\u00020\u00142\b\u0010\u0015\u001a\u0004\u0018\u00010\u0016H\u00d6\u0003J\t\u0010\u0017\u001a\u00020\u0018H\u00d6\u0001J\t\u0010\u0019\u001a\u00020\u001aH\u00d6\u0001R\u001a\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bX\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0014\u0010\u0004\u001a\u00020\u0005X\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000f\u00a8\u0006\u001b"}, d2={"Lorg/partiql/lang/ast/Literal;", "Lorg/partiql/lang/ast/ExprNode;", "ionValue", "Lcom/amazon/ion/IonValue;", "metas", "Lorg/partiql/lang/ast/MetaContainer;", "(Lcom/amazon/ion/IonValue;Lorg/partiql/lang/ast/MetaContainer;)V", "children", "", "Lorg/partiql/lang/ast/AstNode;", "getChildren", "()Ljava/util/List;", "getIonValue", "()Lcom/amazon/ion/IonValue;", "getMetas", "()Lorg/partiql/lang/ast/MetaContainer;", "component1", "component2", "copy", "equals", "", "other", "", "hashCode", "", "toString", "", "lang"})
public final class Literal
extends ExprNode {
    @NotNull
    private final List<AstNode> children;
    @NotNull
    private final IonValue ionValue;
    @NotNull
    private final MetaContainer metas;

    @Override
    @NotNull
    public List<AstNode> getChildren() {
        return this.children;
    }

    @NotNull
    public final IonValue getIonValue() {
        return this.ionValue;
    }

    @Override
    @NotNull
    public MetaContainer getMetas() {
        return this.metas;
    }

    public Literal(@NotNull IonValue ionValue2, @NotNull MetaContainer metas) {
        Intrinsics.checkParameterIsNotNull(ionValue2, "ionValue");
        Intrinsics.checkParameterIsNotNull(metas, "metas");
        super(null);
        this.ionValue = ionValue2;
        this.metas = metas;
        this.ionValue.clone().makeReadOnly();
        Literal literal = this;
        boolean bl = false;
        List list = CollectionsKt.emptyList();
        literal.children = list;
    }

    @NotNull
    public final IonValue component1() {
        return this.ionValue;
    }

    @NotNull
    public final MetaContainer component2() {
        return this.getMetas();
    }

    @NotNull
    public final Literal copy(@NotNull IonValue ionValue2, @NotNull MetaContainer metas) {
        Intrinsics.checkParameterIsNotNull(ionValue2, "ionValue");
        Intrinsics.checkParameterIsNotNull(metas, "metas");
        return new Literal(ionValue2, metas);
    }

    public static /* synthetic */ Literal copy$default(Literal literal, IonValue ionValue2, MetaContainer metaContainer, int n, Object object) {
        if ((n & 1) != 0) {
            ionValue2 = literal.ionValue;
        }
        if ((n & 2) != 0) {
            metaContainer = literal.getMetas();
        }
        return literal.copy(ionValue2, metaContainer);
    }

    @NotNull
    public String toString() {
        return "Literal(ionValue=" + this.ionValue + ", metas=" + this.getMetas() + ")";
    }

    public int hashCode() {
        IonValue ionValue2 = this.ionValue;
        MetaContainer metaContainer = this.getMetas();
        return (ionValue2 != null ? ((Object)ionValue2).hashCode() : 0) * 31 + (metaContainer != null ? metaContainer.hashCode() : 0);
    }

    public boolean equals(@Nullable Object object) {
        block3: {
            block2: {
                if (this == object) break block2;
                if (!(object instanceof Literal)) break block3;
                Literal literal = (Literal)object;
                if (!Intrinsics.areEqual(this.ionValue, literal.ionValue) || !Intrinsics.areEqual(this.getMetas(), literal.getMetas())) break block3;
            }
            return true;
        }
        return false;
    }
}

