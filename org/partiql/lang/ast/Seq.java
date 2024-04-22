/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package org.partiql.lang.ast;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.partiql.lang.ast.AstNode;
import org.partiql.lang.ast.ExprNode;
import org.partiql.lang.ast.MetaContainer;
import org.partiql.lang.ast.SeqType;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B#\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00010\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\bJ\t\u0010\u0012\u001a\u00020\u0003H\u00c6\u0003J\u000f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00010\u0005H\u00c6\u0003J\t\u0010\u0014\u001a\u00020\u0007H\u00c6\u0003J-\u0010\u0015\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\u000e\b\u0002\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u0007H\u00c6\u0001J\u0013\u0010\u0016\u001a\u00020\u00172\b\u0010\u0018\u001a\u0004\u0018\u00010\u0019H\u00d6\u0003J\t\u0010\u001a\u001a\u00020\u001bH\u00d6\u0001J\t\u0010\u001c\u001a\u00020\u001dH\u00d6\u0001R\u001a\u0010\t\u001a\b\u0012\u0004\u0012\u00020\n0\u00058VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u000b\u0010\fR\u0014\u0010\u0006\u001a\u00020\u0007X\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0017\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00010\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\f\u00a8\u0006\u001e"}, d2={"Lorg/partiql/lang/ast/Seq;", "Lorg/partiql/lang/ast/ExprNode;", "type", "Lorg/partiql/lang/ast/SeqType;", "values", "", "metas", "Lorg/partiql/lang/ast/MetaContainer;", "(Lorg/partiql/lang/ast/SeqType;Ljava/util/List;Lorg/partiql/lang/ast/MetaContainer;)V", "children", "Lorg/partiql/lang/ast/AstNode;", "getChildren", "()Ljava/util/List;", "getMetas", "()Lorg/partiql/lang/ast/MetaContainer;", "getType", "()Lorg/partiql/lang/ast/SeqType;", "getValues", "component1", "component2", "component3", "copy", "equals", "", "other", "", "hashCode", "", "toString", "", "lang"})
public final class Seq
extends ExprNode {
    @NotNull
    private final SeqType type;
    @NotNull
    private final List<ExprNode> values;
    @NotNull
    private final MetaContainer metas;

    @Override
    @NotNull
    public List<AstNode> getChildren() {
        return this.values;
    }

    @NotNull
    public final SeqType getType() {
        return this.type;
    }

    @NotNull
    public final List<ExprNode> getValues() {
        return this.values;
    }

    @Override
    @NotNull
    public MetaContainer getMetas() {
        return this.metas;
    }

    public Seq(@NotNull SeqType type, @NotNull List<? extends ExprNode> values2, @NotNull MetaContainer metas) {
        Intrinsics.checkParameterIsNotNull((Object)type, "type");
        Intrinsics.checkParameterIsNotNull(values2, "values");
        Intrinsics.checkParameterIsNotNull(metas, "metas");
        super(null);
        this.type = type;
        this.values = values2;
        this.metas = metas;
    }

    @NotNull
    public final SeqType component1() {
        return this.type;
    }

    @NotNull
    public final List<ExprNode> component2() {
        return this.values;
    }

    @NotNull
    public final MetaContainer component3() {
        return this.getMetas();
    }

    @NotNull
    public final Seq copy(@NotNull SeqType type, @NotNull List<? extends ExprNode> values2, @NotNull MetaContainer metas) {
        Intrinsics.checkParameterIsNotNull((Object)type, "type");
        Intrinsics.checkParameterIsNotNull(values2, "values");
        Intrinsics.checkParameterIsNotNull(metas, "metas");
        return new Seq(type, values2, metas);
    }

    public static /* synthetic */ Seq copy$default(Seq seq2, SeqType seqType, List list, MetaContainer metaContainer, int n, Object object) {
        if ((n & 1) != 0) {
            seqType = seq2.type;
        }
        if ((n & 2) != 0) {
            list = seq2.values;
        }
        if ((n & 4) != 0) {
            metaContainer = seq2.getMetas();
        }
        return seq2.copy(seqType, list, metaContainer);
    }

    @NotNull
    public String toString() {
        return "Seq(type=" + (Object)((Object)this.type) + ", values=" + this.values + ", metas=" + this.getMetas() + ")";
    }

    public int hashCode() {
        SeqType seqType = this.type;
        List<ExprNode> list = this.values;
        MetaContainer metaContainer = this.getMetas();
        return ((seqType != null ? ((Object)((Object)seqType)).hashCode() : 0) * 31 + (list != null ? ((Object)list).hashCode() : 0)) * 31 + (metaContainer != null ? metaContainer.hashCode() : 0);
    }

    public boolean equals(@Nullable Object object) {
        block3: {
            block2: {
                if (this == object) break block2;
                if (!(object instanceof Seq)) break block3;
                Seq seq2 = (Seq)object;
                if (!Intrinsics.areEqual((Object)this.type, (Object)seq2.type) || !Intrinsics.areEqual(this.values, seq2.values) || !Intrinsics.areEqual(this.getMetas(), seq2.getMetas())) break block3;
            }
            return true;
        }
        return false;
    }
}

