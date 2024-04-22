/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package org.partiql.lang.util;

import com.amazon.ion.IonValue;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.partiql.lang.errors.Property;
import org.partiql.lang.errors.PropertyValueMap;
import org.partiql.lang.syntax.TokenType;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=2, d1={"\u0000\u001c\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0004\u001a7\u0010\u0000\u001a\u00020\u00012*\u0010\u0002\u001a\u0016\u0012\u0012\b\u0001\u0012\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u00040\u0003\"\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u0004\u00a2\u0006\u0002\u0010\u0007\u001a!\u0010\b\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u0004*\u00020\u00052\u0006\u0010\t\u001a\u00020\u0006H\u0086\u0004\u00a8\u0006\n"}, d2={"propertyValueMapOf", "Lorg/partiql/lang/errors/PropertyValueMap;", "properties", "", "Lkotlin/Pair;", "Lorg/partiql/lang/errors/Property;", "", "([Lkotlin/Pair;)Lorg/partiql/lang/errors/PropertyValueMap;", "to", "that", "lang"})
public final class PropertyMapHelpersKt {
    @NotNull
    public static final PropertyValueMap propertyValueMapOf(@NotNull Pair<? extends Property, ? extends Object> ... properties) {
        Intrinsics.checkParameterIsNotNull(properties, "properties");
        PropertyValueMap pvm = new PropertyValueMap(null, 1, null);
        Pair<? extends Property, ? extends Object>[] $this$forEach$iv = properties;
        boolean $i$f$forEach = false;
        Pair<? extends Property, ? extends Object>[] pairArray = $this$forEach$iv;
        int n = pairArray.length;
        for (int i = 0; i < n; ++i) {
            Pair<? extends Property, ? extends Object> element$iv;
            Pair<? extends Property, ? extends Object> it = element$iv = pairArray[i];
            boolean bl = false;
            if (pvm.hasProperty(it.getFirst())) {
                throw (Throwable)new IllegalArgumentException("Duplicate property: " + it.getFirst().getPropertyName());
            }
            Object object = it.getSecond();
            if (object instanceof Integer) {
                Object object2 = it.getSecond();
                if (object2 == null) {
                    throw new TypeCastException("null cannot be cast to non-null type kotlin.Int");
                }
                pvm.set(it.getFirst(), (Integer)object2);
                continue;
            }
            if (object instanceof Long) {
                Object object3 = it.getSecond();
                if (object3 == null) {
                    throw new TypeCastException("null cannot be cast to non-null type kotlin.Long");
                }
                pvm.set(it.getFirst(), (Long)object3);
                continue;
            }
            if (object instanceof String) {
                Object object4 = it.getSecond();
                if (object4 == null) {
                    throw new TypeCastException("null cannot be cast to non-null type kotlin.String");
                }
                pvm.set(it.getFirst(), (String)object4);
                continue;
            }
            if (object instanceof TokenType) {
                Object object5 = it.getSecond();
                if (object5 == null) {
                    throw new TypeCastException("null cannot be cast to non-null type org.partiql.lang.syntax.TokenType");
                }
                pvm.set(it.getFirst(), (TokenType)((Object)object5));
                continue;
            }
            if (object instanceof IonValue) {
                Object object6 = it.getSecond();
                if (object6 == null) {
                    throw new TypeCastException("null cannot be cast to non-null type com.amazon.ion.IonValue");
                }
                pvm.set(it.getFirst(), (IonValue)object6);
                continue;
            }
            if (object instanceof Enum) {
                pvm.set(it.getFirst(), it.getSecond().toString());
                continue;
            }
            throw (Throwable)new IllegalArgumentException("Cannot convert " + it.getSecond().getClass().getName() + " to PropertyValue");
        }
        return pvm;
    }

    @NotNull
    public static final Pair<Property, Object> to(@NotNull Property $this$to, @NotNull Object that) {
        Intrinsics.checkParameterIsNotNull((Object)$this$to, "$this$to");
        Intrinsics.checkParameterIsNotNull(that, "that");
        return new Pair<Property, Object>($this$to, that);
    }
}

