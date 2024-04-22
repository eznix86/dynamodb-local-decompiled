/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazonaws.services.dynamodbv2.dbenv;

public enum DbValidationError {
    SYNTAX_ERROR("Syntax error"),
    REDUNDANT_PARENTHESES("The expression has redundant parentheses"),
    RESERVED_KEYWORD_ATTRIBUTE_NAME("Attribute name is a reserved keyword"),
    INVALID_LIST_INDEX("List index is not within the allowable range"),
    INVALID_FUNCTION_NAME("Invalid function name"),
    INVALID_CONDITION_FUNCTION("The function is not allowed in a condition expression"),
    INVALID_VALUE_FUNCTION("The function is not allowed in an update expression"),
    INVALID_FUNCTION_CONTEXT("The function is not allowed to be used this way in an expression"),
    INVALID_ATTRIBUTE_TYPE_NAME("Invalid attribute type name found"),
    DUPLICATE_SET_SECTIONS("The \"SET\" section can only be used once in an update expression"),
    DUPLICATE_ADD_SECTIONS("The \"ADD\" section can only be used once in an update expression"),
    DUPLICATE_DELETE_SECTIONS("The \"DELETE\" section can only be used once in an update expression"),
    DUPLICATE_REMOVE_SECTIONS("The \"REMOVE\" section can only be used once in an update expression"),
    DOC_PATH_EMPTY("The document path has no element(s)"),
    DOC_PATH_NOT_BEGIN_WITH_MAP("The document path does not start with a map element"),
    DOC_PATH_TOO_DEEP("The document path has too many nesting levels"),
    DOC_PATH_CONFLICT("Two document paths conflict with each other; must remove or rewrite one of these paths"),
    DOC_PATH_OVERLAP("Two document paths overlap with each other; must remove or rewrite one of these paths"),
    DOC_PATH_CONTAINS_VALUE_PARAMETER("The document path cannot contain attribute value parameters"),
    DOC_PATH_PARAMETER_MISSING("An expression attribute name used in the document path is not defined"),
    LITERAL_PARAMETER_MISSING("An expression attribute value used in expression is not defined"),
    OPERAND_COUNT_INCORRECT("Incorrect number of operands for operator or function"),
    OPERAND_TYPE_CHECK_FAIL("Incorrect operand type for operator or function"),
    OPERAND_EXPECTED_PATH_TYPE("Operator or function requires a document path"),
    REPEAT_DOC_PATH_NOT_ALLOWED("The first operand must be distinct from the remaining operands for this operator or function"),
    PARAMETER_MAP_NULL_KEY("The expression attribute map contains a null key"),
    PARAMETER_MAP_EMPTY_KEY("The expression attribute map contains an empty key"),
    MAX_OPERATORS_EXCEEDED("The expression contains too many operators"),
    PARAMETER_MAP_KEY_SIZE_EXCEEDED("The expression attribute map contains a key that is too long"),
    EMPTY_EXPRESSION("The expression can not be empty"),
    EXPRESSION_SIZE_EXCEEDED("Expression size has exceeded the maximum allowed size"),
    BETWEEN_LBOUND_BIGGER_THAN_UBOUND("The BETWEEN operator requires upper bound to be greater than or equal to lower bound"),
    BETWEEN_DIFFERENT_TYPE_OPERANDS("The BETWEEN operator requires same data type for lower and upper bounds"),
    TOO_MANY_OPERANDS_FOR_IN("The IN operator is provided with too many operands");

    private final String message;

    public String getMessage() {
        return this.message;
    }

    private DbValidationError(String str) {
        this.message = str;
    }
}

