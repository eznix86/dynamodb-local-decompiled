/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package org.partiql.lang.errors;

import com.amazon.ion.IonValue;
import java.util.EnumMap;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.partiql.lang.errors.Property;
import org.partiql.lang.errors.PropertyType;
import org.partiql.lang.errors.PropertyValue;
import org.partiql.lang.syntax.TokenType;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000X\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010#\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u001b\u0012\u0014\b\u0002\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u0003\u00a2\u0006\u0002\u0010\u0006J\u0013\u0010\u0007\u001a\u0004\u0018\u00010\u00052\u0006\u0010\b\u001a\u00020\u0004H\u0086\u0002J\u0014\u0010\t\u001a\u0010\u0012\f\u0012\n \u000b*\u0004\u0018\u00010\u00040\u00040\nJ\u000e\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u0004J\u0019\u0010\u000f\u001a\u00020\u00102\u0006\u0010\b\u001a\u00020\u00042\u0006\u0010\u0011\u001a\u00020\u0012H\u0086\u0002J\u0019\u0010\u000f\u001a\u00020\u00102\u0006\u0010\b\u001a\u00020\u00042\u0006\u0010\u0013\u001a\u00020\u0014H\u0086\u0002J\u0019\u0010\u000f\u001a\u00020\u00102\u0006\u0010\b\u001a\u00020\u00042\u0006\u0010\u0015\u001a\u00020\u0016H\u0086\u0002J\u0019\u0010\u000f\u001a\u00020\u00102\u0006\u0010\b\u001a\u00020\u00042\u0006\u0010\u0017\u001a\u00020\u0018H\u0086\u0002J\u0019\u0010\u000f\u001a\u00020\u00102\u0006\u0010\b\u001a\u00020\u00042\u0006\u0010\u0019\u001a\u00020\u001aH\u0086\u0002J3\u0010\u001b\u001a\u00020\u0010\"\u0004\b\u0000\u0010\u001c2\u0006\u0010\u001d\u001a\u00020\u00042\u0006\u0010\u001e\u001a\u00020\u001f2\u0006\u0010 \u001a\u0002H\u001c2\u0006\u0010!\u001a\u00020\u0005H\u0002\u00a2\u0006\u0002\u0010\"R\u001a\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006#"}, d2={"Lorg/partiql/lang/errors/PropertyValueMap;", "", "map", "Ljava/util/EnumMap;", "Lorg/partiql/lang/errors/Property;", "Lorg/partiql/lang/errors/PropertyValue;", "(Ljava/util/EnumMap;)V", "get", "key", "getProperties", "", "kotlin.jvm.PlatformType", "hasProperty", "", "property", "set", "", "ionValue", "Lcom/amazon/ion/IonValue;", "intValue", "", "longValue", "", "strValue", "", "tokenTypeValue", "Lorg/partiql/lang/syntax/TokenType;", "verifyTypeAndSet", "T", "prop", "expectedType", "Lorg/partiql/lang/errors/PropertyType;", "value", "pValue", "(Lorg/partiql/lang/errors/Property;Lorg/partiql/lang/errors/PropertyType;Ljava/lang/Object;Lorg/partiql/lang/errors/PropertyValue;)V", "lang"})
public final class PropertyValueMap {
    private final EnumMap<Property, PropertyValue> map;

    @Nullable
    public final PropertyValue get(@NotNull Property key) {
        Intrinsics.checkParameterIsNotNull((Object)key, "key");
        return this.map.get((Object)key);
    }

    private final <T> void verifyTypeAndSet(Property prop, PropertyType expectedType, T value, PropertyValue pValue) {
        if (prop.getPropertyType() != expectedType) {
            throw (Throwable)new IllegalArgumentException("Property " + (Object)((Object)prop) + " requires a value of type " + prop.getPropertyType().getType() + " but was given " + value);
        }
        ((Map)this.map).put(prop, pValue);
    }

    public final void set(@NotNull Property key, @NotNull String strValue) {
        Intrinsics.checkParameterIsNotNull((Object)key, "key");
        Intrinsics.checkParameterIsNotNull(strValue, "strValue");
        PropertyValue o2 = new PropertyValue(strValue, PropertyType.STRING_CLASS){
            final /* synthetic */ String $strValue;

            @NotNull
            public String stringValue() {
                return this.$strValue;
            }
            {
                this.$strValue = $captured_local_variable$0;
                super($super_call_param$1);
            }
        };
        this.verifyTypeAndSet(key, PropertyType.STRING_CLASS, strValue, o2);
    }

    public final void set(@NotNull Property key, long longValue) {
        Intrinsics.checkParameterIsNotNull((Object)key, "key");
        PropertyValue o2 = new PropertyValue(longValue, PropertyType.LONG_CLASS){
            final /* synthetic */ long $longValue;

            public long longValue() {
                return this.$longValue;
            }
            {
                this.$longValue = $captured_local_variable$0;
                super($super_call_param$1);
            }
        };
        this.verifyTypeAndSet(key, PropertyType.LONG_CLASS, longValue, o2);
    }

    public final void set(@NotNull Property key, int intValue) {
        Intrinsics.checkParameterIsNotNull((Object)key, "key");
        PropertyValue o2 = new PropertyValue(intValue, PropertyType.INTEGER_CLASS){
            final /* synthetic */ int $intValue;

            public int integerValue() {
                return this.$intValue;
            }
            {
                this.$intValue = $captured_local_variable$0;
                super($super_call_param$1);
            }
        };
        this.verifyTypeAndSet(key, PropertyType.INTEGER_CLASS, intValue, o2);
    }

    public final void set(@NotNull Property key, @NotNull IonValue ionValue2) {
        Intrinsics.checkParameterIsNotNull((Object)key, "key");
        Intrinsics.checkParameterIsNotNull(ionValue2, "ionValue");
        PropertyValue o2 = new PropertyValue(ionValue2, PropertyType.ION_VALUE_CLASS){
            final /* synthetic */ IonValue $ionValue;

            @NotNull
            public IonValue ionValue() {
                return this.$ionValue;
            }
            {
                this.$ionValue = $captured_local_variable$0;
                super($super_call_param$1);
            }
        };
        this.verifyTypeAndSet(key, PropertyType.ION_VALUE_CLASS, ionValue2, o2);
    }

    public final void set(@NotNull Property key, @NotNull TokenType tokenTypeValue) {
        Intrinsics.checkParameterIsNotNull((Object)key, "key");
        Intrinsics.checkParameterIsNotNull((Object)tokenTypeValue, "tokenTypeValue");
        PropertyValue o2 = new PropertyValue(tokenTypeValue, PropertyType.TOKEN_CLASS){
            final /* synthetic */ TokenType $tokenTypeValue;

            @NotNull
            public TokenType tokenTypeValue() {
                return this.$tokenTypeValue;
            }
            {
                this.$tokenTypeValue = $captured_local_variable$0;
                super($super_call_param$1);
            }
        };
        this.verifyTypeAndSet(key, PropertyType.TOKEN_CLASS, tokenTypeValue, o2);
    }

    public final boolean hasProperty(@NotNull Property property) {
        Intrinsics.checkParameterIsNotNull((Object)property, "property");
        return this.map.containsKey((Object)property);
    }

    @NotNull
    public final Set<Property> getProperties() {
        Set<Property> set2 = this.map.keySet();
        Intrinsics.checkExpressionValueIsNotNull(set2, "this.map.keys");
        return set2;
    }

    public PropertyValueMap(@NotNull EnumMap<Property, PropertyValue> map2) {
        Intrinsics.checkParameterIsNotNull(map2, "map");
        this.map = map2;
    }

    public /* synthetic */ PropertyValueMap(EnumMap enumMap, int n, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n & 1) != 0) {
            enumMap = new EnumMap(Property.class);
        }
        this(enumMap);
    }

    public PropertyValueMap() {
        this(null, 1, null);
    }
}

