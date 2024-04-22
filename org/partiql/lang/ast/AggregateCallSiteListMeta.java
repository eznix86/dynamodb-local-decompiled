/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package org.partiql.lang.ast;

import com.amazon.ion.IonWriter;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.partiql.lang.ast.Meta;
import org.partiql.lang.domains.PartiqlAst;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\b\u0018\u0000 \u00192\u00020\u0001:\u0001\u0019B\u0013\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u00a2\u0006\u0002\u0010\u0005J\u000f\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003H\u00c6\u0003J\u0019\u0010\r\u001a\u00020\u00002\u000e\b\u0002\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003H\u00c6\u0001J\u0013\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0011H\u00d6\u0003J\t\u0010\u0012\u001a\u00020\u0013H\u00d6\u0001J\u0010\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0017H\u0016J\t\u0010\u0018\u001a\u00020\tH\u00d6\u0001R\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0014\u0010\b\u001a\u00020\tX\u0096D\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b\u00a8\u0006\u001a"}, d2={"Lorg/partiql/lang/ast/AggregateCallSiteListMeta;", "Lorg/partiql/lang/ast/Meta;", "aggregateCallSites", "", "Lorg/partiql/lang/domains/PartiqlAst$Expr$CallAgg;", "(Ljava/util/List;)V", "getAggregateCallSites", "()Ljava/util/List;", "tag", "", "getTag", "()Ljava/lang/String;", "component1", "copy", "equals", "", "other", "", "hashCode", "", "serialize", "", "writer", "Lcom/amazon/ion/IonWriter;", "toString", "Companion", "lang"})
public final class AggregateCallSiteListMeta
implements Meta {
    @NotNull
    private final String tag = "$aggregate_call_sites";
    @NotNull
    private final List<PartiqlAst.Expr.CallAgg> aggregateCallSites;
    @NotNull
    public static final String TAG = "$aggregate_call_sites";
    public static final Companion Companion = new Companion(null);

    @Override
    @NotNull
    public String getTag() {
        return this.tag;
    }

    @Override
    public void serialize(@NotNull IonWriter writer) {
        Intrinsics.checkParameterIsNotNull(writer, "writer");
        throw (Throwable)new UnsupportedOperationException("AggregateCallSiteListMeta meant for internal use only and cannot be serialized.");
    }

    @NotNull
    public final List<PartiqlAst.Expr.CallAgg> getAggregateCallSites() {
        return this.aggregateCallSites;
    }

    public AggregateCallSiteListMeta(@NotNull List<PartiqlAst.Expr.CallAgg> aggregateCallSites) {
        Intrinsics.checkParameterIsNotNull(aggregateCallSites, "aggregateCallSites");
        this.aggregateCallSites = aggregateCallSites;
        this.tag = TAG;
    }

    @NotNull
    public final List<PartiqlAst.Expr.CallAgg> component1() {
        return this.aggregateCallSites;
    }

    @NotNull
    public final AggregateCallSiteListMeta copy(@NotNull List<PartiqlAst.Expr.CallAgg> aggregateCallSites) {
        Intrinsics.checkParameterIsNotNull(aggregateCallSites, "aggregateCallSites");
        return new AggregateCallSiteListMeta(aggregateCallSites);
    }

    public static /* synthetic */ AggregateCallSiteListMeta copy$default(AggregateCallSiteListMeta aggregateCallSiteListMeta, List list, int n, Object object) {
        if ((n & 1) != 0) {
            list = aggregateCallSiteListMeta.aggregateCallSites;
        }
        return aggregateCallSiteListMeta.copy(list);
    }

    @NotNull
    public String toString() {
        return "AggregateCallSiteListMeta(aggregateCallSites=" + this.aggregateCallSites + ")";
    }

    public int hashCode() {
        List<PartiqlAst.Expr.CallAgg> list = this.aggregateCallSites;
        return list != null ? ((Object)list).hashCode() : 0;
    }

    public boolean equals(@Nullable Object object) {
        block3: {
            block2: {
                if (this == object) break block2;
                if (!(object instanceof AggregateCallSiteListMeta)) break block3;
                AggregateCallSiteListMeta aggregateCallSiteListMeta = (AggregateCallSiteListMeta)object;
                if (!Intrinsics.areEqual(this.aggregateCallSites, aggregateCallSiteListMeta.aggregateCallSites)) break block3;
            }
            return true;
        }
        return false;
    }

    @Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0005"}, d2={"Lorg/partiql/lang/ast/AggregateCallSiteListMeta$Companion;", "", "()V", "TAG", "", "lang"})
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

