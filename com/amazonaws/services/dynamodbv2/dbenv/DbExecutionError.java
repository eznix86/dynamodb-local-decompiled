/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazonaws.services.dynamodbv2.dbenv;

public enum DbExecutionError {
    DOC_PATH_NOT_VALID_FOR_UPDATE("Cannot update item because the document path is invalid"),
    OPERAND_TYPE_MISMATCH("Two operands must have the same data type"),
    DICTIONARY_OPERAND_TYPE_MISMATCH("To delete elements from Dictionary, left operand should be dictionary and right operand should be a set"),
    MAP_OPERAND_TYPE_MISMATCH("To delete elements from Map, left operand should be Map and right operand should be a string set"),
    OPERAND_TYPE_INVALID("The operator requires a valid operand type"),
    ATTRIBUTE_NOT_FOUND("One of the expected operands was not found in expression"),
    DOCUMENT_TOO_DEEP("The document has too many nesting levels");

    private final String message;

    public String getMessage() {
        return this.message;
    }

    private DbExecutionError(String str) {
        this.message = str;
    }
}

