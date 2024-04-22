/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package org.partiql.lang.errors;

import com.amazon.ion.IonValue;
import kotlin.Metadata;
import org.jetbrains.annotations.NotNull;
import org.partiql.lang.syntax.TokenType;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0013\b\u0002\u0012\n\u0010\u0002\u001a\u0006\u0012\u0002\b\u00030\u0003\u00a2\u0006\u0002\u0010\u0004J\n\u0010\u0005\u001a\u0006\u0012\u0002\b\u00030\u0003R\u0012\u0010\u0002\u001a\u0006\u0012\u0002\b\u00030\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000j\u0002\b\u0006j\u0002\b\u0007j\u0002\b\bj\u0002\b\tj\u0002\b\n\u00a8\u0006\u000b"}, d2={"Lorg/partiql/lang/errors/PropertyType;", "", "type", "Ljava/lang/Class;", "(Ljava/lang/String;ILjava/lang/Class;)V", "getType", "LONG_CLASS", "STRING_CLASS", "INTEGER_CLASS", "TOKEN_CLASS", "ION_VALUE_CLASS", "lang"})
public final class PropertyType
extends Enum<PropertyType> {
    public static final /* enum */ PropertyType LONG_CLASS;
    public static final /* enum */ PropertyType STRING_CLASS;
    public static final /* enum */ PropertyType INTEGER_CLASS;
    public static final /* enum */ PropertyType TOKEN_CLASS;
    public static final /* enum */ PropertyType ION_VALUE_CLASS;
    private static final /* synthetic */ PropertyType[] $VALUES;
    private final Class<?> type;

    static {
        PropertyType[] propertyTypeArray = new PropertyType[5];
        PropertyType[] propertyTypeArray2 = propertyTypeArray;
        propertyTypeArray[0] = LONG_CLASS = new PropertyType(Long.class);
        propertyTypeArray[1] = STRING_CLASS = new PropertyType(String.class);
        propertyTypeArray[2] = INTEGER_CLASS = new PropertyType(Integer.class);
        propertyTypeArray[3] = TOKEN_CLASS = new PropertyType(TokenType.class);
        propertyTypeArray[4] = ION_VALUE_CLASS = new PropertyType(IonValue.class);
        $VALUES = propertyTypeArray;
    }

    @NotNull
    public final Class<?> getType() {
        return this.type;
    }

    private PropertyType(Class<?> type) {
        this.type = type;
    }

    public static PropertyType[] values() {
        return (PropertyType[])$VALUES.clone();
    }

    public static PropertyType valueOf(String string) {
        return Enum.valueOf(PropertyType.class, string);
    }
}

