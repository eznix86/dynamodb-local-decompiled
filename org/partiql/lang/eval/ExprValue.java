/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package org.partiql.lang.eval;

import com.amazon.ion.IonValue;
import com.amazon.ion.facet.Faceted;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.jvm.internal.markers.KMappedMarker;
import org.jetbrains.annotations.NotNull;
import org.partiql.lang.eval.Bindings;
import org.partiql.lang.eval.ExprValueType;
import org.partiql.lang.eval.OrdinalBindings;
import org.partiql.lang.eval.Scalar;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000<\n\u0002\u0018\u0002\n\u0002\u0010\u001c\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010(\n\u0000\bf\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u00012\u00020\u0002J\u000f\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00000\u0018H\u00a6\u0002R\u0018\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00000\u0004X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006R\u0012\u0010\u0007\u001a\u00020\bX\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\t\u0010\nR\u0012\u0010\u000b\u001a\u00020\fX\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\r\u0010\u000eR\u0012\u0010\u000f\u001a\u00020\u0010X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0011\u0010\u0012R\u0012\u0010\u0013\u001a\u00020\u0014X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0015\u0010\u0016\u00a8\u0006\u0019"}, d2={"Lorg/partiql/lang/eval/ExprValue;", "", "Lcom/amazon/ion/facet/Faceted;", "bindings", "Lorg/partiql/lang/eval/Bindings;", "getBindings", "()Lorg/partiql/lang/eval/Bindings;", "ionValue", "Lcom/amazon/ion/IonValue;", "getIonValue", "()Lcom/amazon/ion/IonValue;", "ordinalBindings", "Lorg/partiql/lang/eval/OrdinalBindings;", "getOrdinalBindings", "()Lorg/partiql/lang/eval/OrdinalBindings;", "scalar", "Lorg/partiql/lang/eval/Scalar;", "getScalar", "()Lorg/partiql/lang/eval/Scalar;", "type", "Lorg/partiql/lang/eval/ExprValueType;", "getType", "()Lorg/partiql/lang/eval/ExprValueType;", "iterator", "", "lang"})
public interface ExprValue
extends Iterable<ExprValue>,
Faceted,
KMappedMarker {
    @NotNull
    public ExprValueType getType();

    @NotNull
    public IonValue getIonValue();

    @NotNull
    public Scalar getScalar();

    @NotNull
    public Bindings<ExprValue> getBindings();

    @NotNull
    public OrdinalBindings getOrdinalBindings();

    @Override
    @NotNull
    public Iterator<ExprValue> iterator();
}

