/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package org.partiql.lang.errors;

import kotlin.Metadata;
import org.jetbrains.annotations.NotNull;
import org.partiql.lang.errors.PropertyType;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b$\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0017\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nj\u0002\b\u000bj\u0002\b\fj\u0002\b\rj\u0002\b\u000ej\u0002\b\u000fj\u0002\b\u0010j\u0002\b\u0011j\u0002\b\u0012j\u0002\b\u0013j\u0002\b\u0014j\u0002\b\u0015j\u0002\b\u0016j\u0002\b\u0017j\u0002\b\u0018j\u0002\b\u0019j\u0002\b\u001aj\u0002\b\u001bj\u0002\b\u001cj\u0002\b\u001dj\u0002\b\u001ej\u0002\b\u001fj\u0002\b j\u0002\b!j\u0002\b\"j\u0002\b#j\u0002\b$j\u0002\b%j\u0002\b&j\u0002\b'j\u0002\b(\u00a8\u0006)"}, d2={"Lorg/partiql/lang/errors/Property;", "", "propertyName", "", "propertyType", "Lorg/partiql/lang/errors/PropertyType;", "(Ljava/lang/String;ILjava/lang/String;Lorg/partiql/lang/errors/PropertyType;)V", "getPropertyName", "()Ljava/lang/String;", "getPropertyType", "()Lorg/partiql/lang/errors/PropertyType;", "LINE_NUMBER", "COLUMN_NUMBER", "TOKEN_STRING", "CAST_TO", "CAST_FROM", "KEYWORD", "TOKEN_TYPE", "EXPECTED_TOKEN_TYPE", "EXPECTED_TOKEN_TYPE_1_OF_2", "EXPECTED_TOKEN_TYPE_2_OF_2", "TOKEN_VALUE", "EXPECTED_ARITY_MIN", "EXPECTED_ARITY_MAX", "ACTUAL_ARITY", "EXPECTED_PARAMETER_ORDINAL", "BOUND_PARAMETER_COUNT", "TIMESTAMP_FORMAT_PATTERN", "TIMESTAMP_FORMAT_PATTERN_FIELDS", "TIMESTAMP_STRING", "BINDING_NAME", "BINDING_NAME_MATCHES", "LIKE_VALUE", "LIKE_PATTERN", "LIKE_ESCAPE", "FUNCTION_NAME", "PROCEDURE_NAME", "EXPECTED_ARGUMENT_TYPES", "ACTUAL_ARGUMENT_TYPES", "FEATURE_NAME", "ACTUAL_TYPE", "lang"})
public final class Property
extends Enum<Property> {
    public static final /* enum */ Property LINE_NUMBER;
    public static final /* enum */ Property COLUMN_NUMBER;
    public static final /* enum */ Property TOKEN_STRING;
    public static final /* enum */ Property CAST_TO;
    public static final /* enum */ Property CAST_FROM;
    public static final /* enum */ Property KEYWORD;
    public static final /* enum */ Property TOKEN_TYPE;
    public static final /* enum */ Property EXPECTED_TOKEN_TYPE;
    public static final /* enum */ Property EXPECTED_TOKEN_TYPE_1_OF_2;
    public static final /* enum */ Property EXPECTED_TOKEN_TYPE_2_OF_2;
    public static final /* enum */ Property TOKEN_VALUE;
    public static final /* enum */ Property EXPECTED_ARITY_MIN;
    public static final /* enum */ Property EXPECTED_ARITY_MAX;
    public static final /* enum */ Property ACTUAL_ARITY;
    public static final /* enum */ Property EXPECTED_PARAMETER_ORDINAL;
    public static final /* enum */ Property BOUND_PARAMETER_COUNT;
    public static final /* enum */ Property TIMESTAMP_FORMAT_PATTERN;
    public static final /* enum */ Property TIMESTAMP_FORMAT_PATTERN_FIELDS;
    public static final /* enum */ Property TIMESTAMP_STRING;
    public static final /* enum */ Property BINDING_NAME;
    public static final /* enum */ Property BINDING_NAME_MATCHES;
    public static final /* enum */ Property LIKE_VALUE;
    public static final /* enum */ Property LIKE_PATTERN;
    public static final /* enum */ Property LIKE_ESCAPE;
    public static final /* enum */ Property FUNCTION_NAME;
    public static final /* enum */ Property PROCEDURE_NAME;
    public static final /* enum */ Property EXPECTED_ARGUMENT_TYPES;
    public static final /* enum */ Property ACTUAL_ARGUMENT_TYPES;
    public static final /* enum */ Property FEATURE_NAME;
    public static final /* enum */ Property ACTUAL_TYPE;
    private static final /* synthetic */ Property[] $VALUES;
    @NotNull
    private final String propertyName;
    @NotNull
    private final PropertyType propertyType;

    static {
        Property[] propertyArray = new Property[30];
        Property[] propertyArray2 = propertyArray;
        propertyArray[0] = LINE_NUMBER = new Property("line_no", PropertyType.LONG_CLASS);
        propertyArray[1] = COLUMN_NUMBER = new Property("column_no", PropertyType.LONG_CLASS);
        propertyArray[2] = TOKEN_STRING = new Property("token_string", PropertyType.STRING_CLASS);
        propertyArray[3] = CAST_TO = new Property("cast_to", PropertyType.STRING_CLASS);
        propertyArray[4] = CAST_FROM = new Property("cast_from", PropertyType.STRING_CLASS);
        propertyArray[5] = KEYWORD = new Property("keyword", PropertyType.STRING_CLASS);
        propertyArray[6] = TOKEN_TYPE = new Property("token_type", PropertyType.TOKEN_CLASS);
        propertyArray[7] = EXPECTED_TOKEN_TYPE = new Property("expected_token_type", PropertyType.TOKEN_CLASS);
        propertyArray[8] = EXPECTED_TOKEN_TYPE_1_OF_2 = new Property("expected_token_type_1_of_2", PropertyType.TOKEN_CLASS);
        propertyArray[9] = EXPECTED_TOKEN_TYPE_2_OF_2 = new Property("expected_token_type_2_of_2", PropertyType.TOKEN_CLASS);
        propertyArray[10] = TOKEN_VALUE = new Property("token_value", PropertyType.ION_VALUE_CLASS);
        propertyArray[11] = EXPECTED_ARITY_MIN = new Property("arity_min", PropertyType.INTEGER_CLASS);
        propertyArray[12] = EXPECTED_ARITY_MAX = new Property("arity_max", PropertyType.INTEGER_CLASS);
        propertyArray[13] = ACTUAL_ARITY = new Property("actual_arity", PropertyType.INTEGER_CLASS);
        propertyArray[14] = EXPECTED_PARAMETER_ORDINAL = new Property("expected_parameter_ordinal", PropertyType.INTEGER_CLASS);
        propertyArray[15] = BOUND_PARAMETER_COUNT = new Property("bound_parameter_count", PropertyType.INTEGER_CLASS);
        propertyArray[16] = TIMESTAMP_FORMAT_PATTERN = new Property("timestamp_format_pattern", PropertyType.STRING_CLASS);
        propertyArray[17] = TIMESTAMP_FORMAT_PATTERN_FIELDS = new Property("timestamp_format_pattern_fields", PropertyType.STRING_CLASS);
        propertyArray[18] = TIMESTAMP_STRING = new Property("timestamp_string", PropertyType.STRING_CLASS);
        propertyArray[19] = BINDING_NAME = new Property("binding_name", PropertyType.STRING_CLASS);
        propertyArray[20] = BINDING_NAME_MATCHES = new Property("binding_name_matches", PropertyType.STRING_CLASS);
        propertyArray[21] = LIKE_VALUE = new Property("value_to_match", PropertyType.STRING_CLASS);
        propertyArray[22] = LIKE_PATTERN = new Property("pattern", PropertyType.STRING_CLASS);
        propertyArray[23] = LIKE_ESCAPE = new Property("escape_char", PropertyType.STRING_CLASS);
        propertyArray[24] = FUNCTION_NAME = new Property("function_name", PropertyType.STRING_CLASS);
        propertyArray[25] = PROCEDURE_NAME = new Property("procedure_name", PropertyType.STRING_CLASS);
        propertyArray[26] = EXPECTED_ARGUMENT_TYPES = new Property("expected_types", PropertyType.STRING_CLASS);
        propertyArray[27] = ACTUAL_ARGUMENT_TYPES = new Property("actual_types", PropertyType.STRING_CLASS);
        propertyArray[28] = FEATURE_NAME = new Property("FEATURE_NAME", PropertyType.STRING_CLASS);
        propertyArray[29] = ACTUAL_TYPE = new Property("ACTUAL_TYPE", PropertyType.STRING_CLASS);
        $VALUES = propertyArray;
    }

    @NotNull
    public final String getPropertyName() {
        return this.propertyName;
    }

    @NotNull
    public final PropertyType getPropertyType() {
        return this.propertyType;
    }

    private Property(String propertyName, PropertyType propertyType) {
        this.propertyName = propertyName;
        this.propertyType = propertyType;
    }

    public static Property[] values() {
        return (Property[])$VALUES.clone();
    }

    public static Property valueOf(String string) {
        return Enum.valueOf(Property.class, string);
    }
}

