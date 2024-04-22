/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package org.partiql.lang.errors;

import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.SetsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.partiql.lang.errors.Property;
import org.partiql.lang.errors.PropertyValue;
import org.partiql.lang.errors.PropertyValueMap;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=2, d1={"\u0000\u001a\n\u0000\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\b\u0005\u001a\u001c\u0010\u0006\u001a\u00020\u0007*\u00020\b2\u0006\u0010\t\u001a\u00020\u00022\u0006\u0010\n\u001a\u00020\u0007H\u0002\u001a\u0016\u0010\u000b\u001a\u00020\u0007*\u0004\u0018\u00010\b2\u0006\u0010\f\u001a\u00020\u0002H\u0002\"\u0014\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001X\u0082\u0004\u00a2\u0006\u0002\n\u0000\"\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001X\u0082\u0004\u00a2\u0006\u0002\n\u0000\"\u0014\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001X\u0082\u0004\u00a2\u0006\u0002\n\u0000\"\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\r"}, d2={"LOCATION", "", "Lorg/partiql/lang/errors/Property;", "LOC_TOKEN", "LOC_TOKEN_STR", "TOKEN_INFO", "getAsString", "", "Lorg/partiql/lang/errors/PropertyValueMap;", "key", "defaultValue", "getProperty", "prop", "lang"})
public final class ErrorCodeKt {
    private static final Set<Property> LOCATION = SetsKt.setOf(Property.LINE_NUMBER, Property.COLUMN_NUMBER);
    private static final Set<Property> TOKEN_INFO = SetsKt.setOf(Property.TOKEN_TYPE, Property.TOKEN_VALUE);
    private static final Set<Property> LOC_TOKEN = SetsKt.plus(LOCATION, (Iterable)TOKEN_INFO);
    private static final Set<Property> LOC_TOKEN_STR = SetsKt.plus(LOCATION, (Iterable)SetsKt.setOf(Property.TOKEN_STRING));

    private static final String getAsString(@NotNull PropertyValueMap $this$getAsString, Property key, String defaultValue) {
        Object object = $this$getAsString.get(key);
        if (object == null || (object = ((PropertyValue)object).toString()) == null) {
            object = defaultValue;
        }
        return object;
    }

    private static final String getProperty(@Nullable PropertyValueMap $this$getProperty, Property prop) {
        Object object = $this$getProperty;
        if (object == null || (object = ((PropertyValueMap)object).get(prop)) == null || (object = ((PropertyValue)object).toString()) == null) {
            object = "<UNKNOWN>";
        }
        return object;
    }

    public static final /* synthetic */ Set access$getLOC_TOKEN$p() {
        return LOC_TOKEN;
    }

    public static final /* synthetic */ Set access$getLOCATION$p() {
        return LOCATION;
    }

    public static final /* synthetic */ Set access$getLOC_TOKEN_STR$p() {
        return LOC_TOKEN_STR;
    }

    public static final /* synthetic */ String access$getAsString(PropertyValueMap $this$access_u24getAsString, Property key, String defaultValue) {
        return ErrorCodeKt.getAsString($this$access_u24getAsString, key, defaultValue);
    }

    public static final /* synthetic */ String access$getProperty(PropertyValueMap $this$access_u24getProperty, Property prop) {
        return ErrorCodeKt.getProperty($this$access_u24getProperty, prop);
    }
}

