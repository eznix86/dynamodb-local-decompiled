/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package org.partiql.lang.eval;

import java.util.Iterator;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.partiql.lang.eval.Bindings;
import org.partiql.lang.eval.ExprValue;
import org.partiql.lang.eval.ExprValueExtensionsKt;
import org.partiql.lang.eval.OrdinalBindings;
import org.partiql.lang.eval.Scalar;
import org.partiql.lang.util.FacetExtensionsKt;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010(\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b&\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J#\u0010\u000f\u001a\u0004\u0018\u0001H\u0010\"\u0004\b\u0000\u0010\u00102\u000e\u0010\u0011\u001a\n\u0012\u0004\u0012\u0002H\u0010\u0018\u00010\u0012\u00a2\u0006\u0002\u0010\u0013J\u000f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00010\u0015H\u0096\u0002J%\u0010\u0016\u001a\u0004\u0018\u0001H\u0010\"\u0004\b\u0000\u0010\u00102\u000e\u0010\u0011\u001a\n\u0012\u0004\u0012\u0002H\u0010\u0018\u00010\u0012H\u0016\u00a2\u0006\u0002\u0010\u0013J\b\u0010\u0017\u001a\u00020\u0018H\u0016R\u001a\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00010\u00048VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006R\u0014\u0010\u0007\u001a\u00020\b8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\t\u0010\nR\u0014\u0010\u000b\u001a\u00020\f8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\r\u0010\u000e\u00a8\u0006\u0019"}, d2={"Lorg/partiql/lang/eval/BaseExprValue;", "Lorg/partiql/lang/eval/ExprValue;", "()V", "bindings", "Lorg/partiql/lang/eval/Bindings;", "getBindings", "()Lorg/partiql/lang/eval/Bindings;", "ordinalBindings", "Lorg/partiql/lang/eval/OrdinalBindings;", "getOrdinalBindings", "()Lorg/partiql/lang/eval/OrdinalBindings;", "scalar", "Lorg/partiql/lang/eval/Scalar;", "getScalar", "()Lorg/partiql/lang/eval/Scalar;", "asFacet", "T", "type", "Ljava/lang/Class;", "(Ljava/lang/Class;)Ljava/lang/Object;", "iterator", "", "provideFacet", "toString", "", "lang"})
public abstract class BaseExprValue
implements ExprValue {
    @Override
    @NotNull
    public Scalar getScalar() {
        return Scalar.EMPTY;
    }

    @Override
    @NotNull
    public Bindings<ExprValue> getBindings() {
        return Bindings.Companion.empty();
    }

    @Override
    @NotNull
    public OrdinalBindings getOrdinalBindings() {
        return OrdinalBindings.EMPTY;
    }

    @Override
    @NotNull
    public Iterator<ExprValue> iterator() {
        return CollectionsKt.emptyList().iterator();
    }

    @Override
    @Nullable
    public final <T> T asFacet(@Nullable Class<T> type) {
        T t = FacetExtensionsKt.downcast(this, type);
        if (t == null) {
            t = this.provideFacet(type);
        }
        return t;
    }

    @Nullable
    public <T> T provideFacet(@Nullable Class<T> type) {
        return null;
    }

    @NotNull
    public String toString() {
        return ExprValueExtensionsKt.stringify(this);
    }
}

