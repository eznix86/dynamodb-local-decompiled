/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazonaws.services.dynamodbv2.dbenv;

public enum DbInternalError {
    SN_NEW_EXPRESSION_MIXED_WITH_OLD_API("When using expression, old API parameters must be null"),
    INDEXKEY_OUT_OF_BOUNDS("Index key out of partition bounds"),
    INVALID_METADATA_VERSION("Invalid metadata version received"),
    NUM_OPERANDS_ERROR("number of operands invalid; RR validation bug"),
    NOT_SUPPORTED_OPERATOR("operator not yet supported; RR validation bug"),
    MAP_TYPE_EXPECTED("a sub document in serialized expression must be a map type"),
    BOOL_TYPE_EXPECTED("a sub document in serialized expression must be boolean type"),
    MAP_KEY_EXPECTED("a sub document serialized expression must be a map and contains a specific key"),
    INT_EXPECTED("a valid integer is expected in serialzed expression"),
    STR_EXPECTED("a string is expected in serialzed expression"),
    NON_EMPTY_MAP_EXPECTED("non-empty map is expected in serialzed expression"),
    ACTION_TYPE_EXPECTED("updateActionType is expected in serialzed expression"),
    OPERATOR_EXPECTED("operator is expected in serialzed expression"),
    LITERAL_EXPECTED("a literal sub document is expected  in serialzed expression"),
    UNEXPECTED_PATH_ELEMENT_TYPE("path element not of type map, list, or dictionary");

    private final String message;

    public String getMessage() {
        return this.message;
    }

    private DbInternalError(String str) {
        this.message = str;
    }
}

