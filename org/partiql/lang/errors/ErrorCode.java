/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package org.partiql.lang.errors;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.partiql.lang.errors.ErrorCategory;
import org.partiql.lang.errors.ErrorCodeKt;
import org.partiql.lang.errors.Property;
import org.partiql.lang.errors.PropertyValue;
import org.partiql.lang.errors.PropertyValueMap;
import org.partiql.lang.eval.ExprValueType;
import org.partiql.lang.syntax.LexerConstantsKt;
import org.partiql.lang.syntax.TokenType;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\bo\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B%\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u0012\u0006\u0010\u0007\u001a\u00020\b\u00a2\u0006\u0002\u0010\tJ\b\u0010\n\u001a\u00020\bH\u0014J\u0012\u0010\u000b\u001a\u00020\b2\b\u0010\f\u001a\u0004\u0018\u00010\rH\u0014J\u0006\u0010\u000e\u001a\u00020\bJ\u0012\u0010\u000f\u001a\u00020\b2\b\u0010\f\u001a\u0004\u0018\u00010\rH\u0016J\u0012\u0010\u0010\u001a\u00020\b2\b\u0010\f\u001a\u0004\u0018\u00010\rH\u0004J\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005J\u0012\u0010\u0012\u001a\u00020\b2\b\u0010\f\u001a\u0004\u0018\u00010\rH\u0004J\u0012\u0010\u0013\u001a\u00020\b2\b\u0010\f\u001a\u0004\u0018\u00010\rH\u0004J\u0012\u0010\u0014\u001a\u00020\b2\b\u0010\f\u001a\u0004\u0018\u00010\rH\u0004J\u0012\u0010\u0015\u001a\u00020\b2\b\u0010\f\u001a\u0004\u0018\u00010\rH\u0004R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000j\u0002\b\u0016j\u0002\b\u0017j\u0002\b\u0018j\u0002\b\u0019j\u0002\b\u001aj\u0002\b\u001bj\u0002\b\u001cj\u0002\b\u001dj\u0002\b\u001ej\u0002\b\u001fj\u0002\b j\u0002\b!j\u0002\b\"j\u0002\b#j\u0002\b$j\u0002\b%j\u0002\b&j\u0002\b'j\u0002\b(j\u0002\b)j\u0002\b*j\u0002\b+j\u0002\b,j\u0002\b-j\u0002\b.j\u0002\b/j\u0002\b0j\u0002\b1j\u0002\b2j\u0002\b3j\u0002\b4j\u0002\b5j\u0002\b6j\u0002\b7j\u0002\b8j\u0002\b9j\u0002\b:j\u0002\b;j\u0002\b<j\u0002\b=j\u0002\b>j\u0002\b?j\u0002\b@j\u0002\bAj\u0002\bBj\u0002\bCj\u0002\bDj\u0002\bEj\u0002\bFj\u0002\bGj\u0002\bHj\u0002\bIj\u0002\bJj\u0002\bKj\u0002\bLj\u0002\bMj\u0002\bNj\u0002\bOj\u0002\bPj\u0002\bQj\u0002\bRj\u0002\bSj\u0002\bTj\u0002\bUj\u0002\bVj\u0002\bWj\u0002\bXj\u0002\bYj\u0002\bZj\u0002\b[j\u0002\b\\j\u0002\b]j\u0002\b^j\u0002\b_j\u0002\b`j\u0002\baj\u0002\bbj\u0002\bcj\u0002\bdj\u0002\bej\u0002\bfj\u0002\bgj\u0002\bhj\u0002\bij\u0002\bjj\u0002\bkj\u0002\blj\u0002\bmj\u0002\bnj\u0002\boj\u0002\bpj\u0002\bqj\u0002\brj\u0002\bsj\u0002\btj\u0002\buj\u0002\bvj\u0002\bwj\u0002\bxj\u0002\byj\u0002\bzj\u0002\b{\u00a8\u0006|"}, d2={"Lorg/partiql/lang/errors/ErrorCode;", "", "category", "Lorg/partiql/lang/errors/ErrorCategory;", "properties", "", "Lorg/partiql/lang/errors/Property;", "messagePrefix", "", "(Ljava/lang/String;ILorg/partiql/lang/errors/ErrorCategory;Ljava/util/Set;Ljava/lang/String;)V", "detailMessagePrefix", "detailMessageSuffix", "errorContext", "Lorg/partiql/lang/errors/PropertyValueMap;", "errorCategory", "getErrorMessage", "getKeyword", "getProperties", "getTokenString", "getTokenType", "getTokenTypeAndTokenValue", "getTokenValue", "LEXER_INVALID_CHAR", "LEXER_INVALID_OPERATOR", "LEXER_INVALID_LITERAL", "LEXER_INVALID_ION_LITERAL", "PARSE_MALFORMED_PARSE_TREE", "PARSE_EXPECTED_KEYWORD", "PARSE_EXPECTED_TOKEN_TYPE", "PARSE_EXPECTED_2_TOKEN_TYPES", "PARSE_EXPECTED_NUMBER", "PARSE_EXPECTED_TYPE_NAME", "PARSE_EXPECTED_WHEN_CLAUSE", "PARSE_EXPECTED_WHERE_CLAUSE", "PARSE_EXPECTED_CONFLICT_ACTION", "PARSE_EXPECTED_RETURNING_CLAUSE", "PARSE_UNSUPPORTED_RETURNING_CLAUSE_SYNTAX", "PARSE_UNSUPPORTED_TOKEN", "PARSE_UNSUPPORTED_LITERALS_GROUPBY", "PARSE_EXPECTED_MEMBER", "PARSE_EXPECTED_DATE_PART", "PARSE_UNSUPPORTED_SELECT", "PARSE_UNSUPPORTED_CASE", "PARSE_UNSUPPORTED_CASE_CLAUSE", "PARSE_UNSUPPORTED_ALIAS", "PARSE_UNSUPPORTED_SYNTAX", "PARSE_UNKNOWN_OPERATOR", "PARSE_INVALID_PATH_COMPONENT", "PARSE_MISSING_IDENT_AFTER_AT", "PARSE_UNEXPECTED_OPERATOR", "PARSE_UNEXPECTED_TERM", "PARSE_UNEXPECTED_TOKEN", "PARSE_UNEXPECTED_KEYWORD", "PARSE_EXPECTED_EXPRESSION", "PARSE_EXPECTED_LEFT_PAREN_AFTER_CAST", "PARSE_EXPECTED_LEFT_PAREN_VALUE_CONSTRUCTOR", "PARSE_EXPECTED_LEFT_PAREN_BUILTIN_FUNCTION_CALL", "PARSE_EXPECTED_RIGHT_PAREN_BUILTIN_FUNCTION_CALL", "PARSE_EXPECTED_ARGUMENT_DELIMITER", "PARSE_CAST_ARITY", "PARSE_INVALID_TYPE_PARAM", "PARSE_INVALID_PRECISION_FOR_TIME", "PARSE_INVALID_DATE_STRING", "PARSE_INVALID_TIME_STRING", "PARSE_EMPTY_SELECT", "PARSE_SELECT_MISSING_FROM", "PARSE_MISSING_OPERATION", "PARSE_MISSING_SET_ASSIGNMENT", "PARSE_EXPECTED_IDENT_FOR_GROUP_NAME", "PARSE_EXPECTED_IDENT_FOR_ALIAS", "PARSE_EXPECTED_AS_FOR_LET", "PARSE_UNSUPPORTED_CALL_WITH_STAR", "PARSE_NON_UNARY_AGREGATE_FUNCTION_CALL", "PARSE_NO_STORED_PROCEDURE_PROVIDED", "PARSE_MALFORMED_JOIN", "PARSE_EXPECTED_IDENT_FOR_AT", "PARSE_INVALID_CONTEXT_FOR_WILDCARD_IN_SELECT_LIST", "PARSE_CANNOT_MIX_SQB_AND_WILDCARD_IN_SELECT_LIST", "PARSE_ASTERISK_IS_NOT_ALONE_IN_SELECT_LIST", "EVALUATOR_FEATURE_NOT_SUPPORTED_YET", "EVALUATOR_COUNT_DISTINCT_STAR", "EVALUATOR_BINDING_DOES_NOT_EXIST", "EVALUATOR_VARIABLE_NOT_INCLUDED_IN_GROUP_BY", "EVALUATOR_UNBOUND_PARAMETER", "EVALUATOR_INVALID_CAST", "EVALUATOR_INVALID_CAST_NO_LOCATION", "EVALUATOR_CAST_FAILED", "EVALUATOR_CAST_FAILED_NO_LOCATION", "EVALUATOR_NO_SUCH_FUNCTION", "EVALUATOR_NO_SUCH_PROCEDURE", "EVALUATOR_INCORRECT_NUMBER_OF_ARGUMENTS_TO_FUNC_CALL", "EVALUATOR_INCORRECT_NUMBER_OF_ARGUMENTS_TO_PROCEDURE_CALL", "EVALUATOR_DATE_FIELD_OUT_OF_RANGE", "EVALUATOR_INCORRECT_TYPE_OF_ARGUMENTS_TO_FUNC_CALL", "EVALUATOR_INCORRECT_TYPE_OF_ARGUMENTS_TO_PROCEDURE_CALL", "EVALUATOR_CONCAT_FAILED_DUE_TO_INCOMPATIBLE_TYPE", "EVALUATOR_INVALID_PRECISION_FOR_TIME", "EVALUATOR_TIME_FIELD_OUT_OF_RANGE", "EVALUATOR_INVALID_TIMESTAMP_FORMAT_PATTERN", "EVALUATOR_INVALID_TIMESTAMP_FORMAT_PATTERN_TOKEN", "EVALUATOR_INVALID_TIMESTAMP_FORMAT_PATTERN_SYMBOL", "EVALUATOR_UNTERMINATED_TIMESTAMP_FORMAT_PATTERN_TOKEN", "EVALUATOR_INCOMPLETE_TIMESTAMP_FORMAT_PATTERN", "EVALUATOR_TIMESTAMP_FORMAT_PATTERN_DUPLICATE_FIELDS", "EVALUATOR_TIMESTAMP_FORMAT_PATTERN_HOUR_CLOCK_AM_PM_MISMATCH", "EVALUATOR_INVALID_TIMESTAMP_FORMAT_PATTERN_SYMBOL_FOR_PARSING", "EVALUATOR_ION_TIMESTAMP_PARSE_FAILURE", "EVALUATOR_CUSTOM_TIMESTAMP_PARSE_FAILURE", "EVALUATOR_PRECISION_LOSS_WHEN_PARSING_TIMESTAMP", "EVALUATOR_INTEGER_OVERFLOW", "EVALUATOR_AMBIGUOUS_BINDING", "EVALUATOR_LIKE_INVALID_INPUTS", "EVALUATOR_LIKE_PATTERN_INVALID_ESCAPE_SEQUENCE", "EVALUATOR_NON_INT_LIMIT_VALUE", "EVALUATOR_NEGATIVE_LIMIT", "EVALUATOR_DIVIDE_BY_ZERO", "EVALUATOR_MODULO_BY_ZERO", "SEMANTIC_ILLEGAL_GLOBAL_VARIABLE_ACCESS", "SEMANTIC_UNBOUND_BINDING", "SEMANTIC_AMBIGUOUS_BINDING", "SEMANTIC_INCORRECT_NODE_ARITY", "SEMANTIC_HAVING_USED_WITHOUT_GROUP_BY", "SEMANTIC_ASTERISK_USED_WITH_OTHER_ITEMS", "UNIMPLEMENTED_FEATURE", "lang"})
public class ErrorCode
extends Enum<ErrorCode> {
    public static final /* enum */ ErrorCode LEXER_INVALID_CHAR;
    public static final /* enum */ ErrorCode LEXER_INVALID_OPERATOR;
    public static final /* enum */ ErrorCode LEXER_INVALID_LITERAL;
    public static final /* enum */ ErrorCode LEXER_INVALID_ION_LITERAL;
    public static final /* enum */ ErrorCode PARSE_MALFORMED_PARSE_TREE;
    public static final /* enum */ ErrorCode PARSE_EXPECTED_KEYWORD;
    public static final /* enum */ ErrorCode PARSE_EXPECTED_TOKEN_TYPE;
    public static final /* enum */ ErrorCode PARSE_EXPECTED_2_TOKEN_TYPES;
    public static final /* enum */ ErrorCode PARSE_EXPECTED_NUMBER;
    public static final /* enum */ ErrorCode PARSE_EXPECTED_TYPE_NAME;
    public static final /* enum */ ErrorCode PARSE_EXPECTED_WHEN_CLAUSE;
    public static final /* enum */ ErrorCode PARSE_EXPECTED_WHERE_CLAUSE;
    public static final /* enum */ ErrorCode PARSE_EXPECTED_CONFLICT_ACTION;
    public static final /* enum */ ErrorCode PARSE_EXPECTED_RETURNING_CLAUSE;
    public static final /* enum */ ErrorCode PARSE_UNSUPPORTED_RETURNING_CLAUSE_SYNTAX;
    public static final /* enum */ ErrorCode PARSE_UNSUPPORTED_TOKEN;
    public static final /* enum */ ErrorCode PARSE_UNSUPPORTED_LITERALS_GROUPBY;
    public static final /* enum */ ErrorCode PARSE_EXPECTED_MEMBER;
    public static final /* enum */ ErrorCode PARSE_EXPECTED_DATE_PART;
    public static final /* enum */ ErrorCode PARSE_UNSUPPORTED_SELECT;
    public static final /* enum */ ErrorCode PARSE_UNSUPPORTED_CASE;
    public static final /* enum */ ErrorCode PARSE_UNSUPPORTED_CASE_CLAUSE;
    public static final /* enum */ ErrorCode PARSE_UNSUPPORTED_ALIAS;
    public static final /* enum */ ErrorCode PARSE_UNSUPPORTED_SYNTAX;
    public static final /* enum */ ErrorCode PARSE_UNKNOWN_OPERATOR;
    public static final /* enum */ ErrorCode PARSE_INVALID_PATH_COMPONENT;
    public static final /* enum */ ErrorCode PARSE_MISSING_IDENT_AFTER_AT;
    public static final /* enum */ ErrorCode PARSE_UNEXPECTED_OPERATOR;
    public static final /* enum */ ErrorCode PARSE_UNEXPECTED_TERM;
    public static final /* enum */ ErrorCode PARSE_UNEXPECTED_TOKEN;
    public static final /* enum */ ErrorCode PARSE_UNEXPECTED_KEYWORD;
    public static final /* enum */ ErrorCode PARSE_EXPECTED_EXPRESSION;
    public static final /* enum */ ErrorCode PARSE_EXPECTED_LEFT_PAREN_AFTER_CAST;
    public static final /* enum */ ErrorCode PARSE_EXPECTED_LEFT_PAREN_VALUE_CONSTRUCTOR;
    public static final /* enum */ ErrorCode PARSE_EXPECTED_LEFT_PAREN_BUILTIN_FUNCTION_CALL;
    public static final /* enum */ ErrorCode PARSE_EXPECTED_RIGHT_PAREN_BUILTIN_FUNCTION_CALL;
    public static final /* enum */ ErrorCode PARSE_EXPECTED_ARGUMENT_DELIMITER;
    public static final /* enum */ ErrorCode PARSE_CAST_ARITY;
    public static final /* enum */ ErrorCode PARSE_INVALID_TYPE_PARAM;
    public static final /* enum */ ErrorCode PARSE_INVALID_PRECISION_FOR_TIME;
    public static final /* enum */ ErrorCode PARSE_INVALID_DATE_STRING;
    public static final /* enum */ ErrorCode PARSE_INVALID_TIME_STRING;
    public static final /* enum */ ErrorCode PARSE_EMPTY_SELECT;
    public static final /* enum */ ErrorCode PARSE_SELECT_MISSING_FROM;
    public static final /* enum */ ErrorCode PARSE_MISSING_OPERATION;
    public static final /* enum */ ErrorCode PARSE_MISSING_SET_ASSIGNMENT;
    public static final /* enum */ ErrorCode PARSE_EXPECTED_IDENT_FOR_GROUP_NAME;
    public static final /* enum */ ErrorCode PARSE_EXPECTED_IDENT_FOR_ALIAS;
    public static final /* enum */ ErrorCode PARSE_EXPECTED_AS_FOR_LET;
    public static final /* enum */ ErrorCode PARSE_UNSUPPORTED_CALL_WITH_STAR;
    public static final /* enum */ ErrorCode PARSE_NON_UNARY_AGREGATE_FUNCTION_CALL;
    public static final /* enum */ ErrorCode PARSE_NO_STORED_PROCEDURE_PROVIDED;
    public static final /* enum */ ErrorCode PARSE_MALFORMED_JOIN;
    public static final /* enum */ ErrorCode PARSE_EXPECTED_IDENT_FOR_AT;
    public static final /* enum */ ErrorCode PARSE_INVALID_CONTEXT_FOR_WILDCARD_IN_SELECT_LIST;
    public static final /* enum */ ErrorCode PARSE_CANNOT_MIX_SQB_AND_WILDCARD_IN_SELECT_LIST;
    public static final /* enum */ ErrorCode PARSE_ASTERISK_IS_NOT_ALONE_IN_SELECT_LIST;
    public static final /* enum */ ErrorCode EVALUATOR_FEATURE_NOT_SUPPORTED_YET;
    public static final /* enum */ ErrorCode EVALUATOR_COUNT_DISTINCT_STAR;
    public static final /* enum */ ErrorCode EVALUATOR_BINDING_DOES_NOT_EXIST;
    public static final /* enum */ ErrorCode EVALUATOR_VARIABLE_NOT_INCLUDED_IN_GROUP_BY;
    public static final /* enum */ ErrorCode EVALUATOR_UNBOUND_PARAMETER;
    public static final /* enum */ ErrorCode EVALUATOR_INVALID_CAST;
    public static final /* enum */ ErrorCode EVALUATOR_INVALID_CAST_NO_LOCATION;
    public static final /* enum */ ErrorCode EVALUATOR_CAST_FAILED;
    public static final /* enum */ ErrorCode EVALUATOR_CAST_FAILED_NO_LOCATION;
    public static final /* enum */ ErrorCode EVALUATOR_NO_SUCH_FUNCTION;
    public static final /* enum */ ErrorCode EVALUATOR_NO_SUCH_PROCEDURE;
    public static final /* enum */ ErrorCode EVALUATOR_INCORRECT_NUMBER_OF_ARGUMENTS_TO_FUNC_CALL;
    public static final /* enum */ ErrorCode EVALUATOR_INCORRECT_NUMBER_OF_ARGUMENTS_TO_PROCEDURE_CALL;
    public static final /* enum */ ErrorCode EVALUATOR_DATE_FIELD_OUT_OF_RANGE;
    public static final /* enum */ ErrorCode EVALUATOR_INCORRECT_TYPE_OF_ARGUMENTS_TO_FUNC_CALL;
    public static final /* enum */ ErrorCode EVALUATOR_INCORRECT_TYPE_OF_ARGUMENTS_TO_PROCEDURE_CALL;
    public static final /* enum */ ErrorCode EVALUATOR_CONCAT_FAILED_DUE_TO_INCOMPATIBLE_TYPE;
    public static final /* enum */ ErrorCode EVALUATOR_INVALID_PRECISION_FOR_TIME;
    public static final /* enum */ ErrorCode EVALUATOR_TIME_FIELD_OUT_OF_RANGE;
    public static final /* enum */ ErrorCode EVALUATOR_INVALID_TIMESTAMP_FORMAT_PATTERN;
    public static final /* enum */ ErrorCode EVALUATOR_INVALID_TIMESTAMP_FORMAT_PATTERN_TOKEN;
    public static final /* enum */ ErrorCode EVALUATOR_INVALID_TIMESTAMP_FORMAT_PATTERN_SYMBOL;
    public static final /* enum */ ErrorCode EVALUATOR_UNTERMINATED_TIMESTAMP_FORMAT_PATTERN_TOKEN;
    public static final /* enum */ ErrorCode EVALUATOR_INCOMPLETE_TIMESTAMP_FORMAT_PATTERN;
    public static final /* enum */ ErrorCode EVALUATOR_TIMESTAMP_FORMAT_PATTERN_DUPLICATE_FIELDS;
    public static final /* enum */ ErrorCode EVALUATOR_TIMESTAMP_FORMAT_PATTERN_HOUR_CLOCK_AM_PM_MISMATCH;
    public static final /* enum */ ErrorCode EVALUATOR_INVALID_TIMESTAMP_FORMAT_PATTERN_SYMBOL_FOR_PARSING;
    public static final /* enum */ ErrorCode EVALUATOR_ION_TIMESTAMP_PARSE_FAILURE;
    public static final /* enum */ ErrorCode EVALUATOR_CUSTOM_TIMESTAMP_PARSE_FAILURE;
    public static final /* enum */ ErrorCode EVALUATOR_PRECISION_LOSS_WHEN_PARSING_TIMESTAMP;
    public static final /* enum */ ErrorCode EVALUATOR_INTEGER_OVERFLOW;
    public static final /* enum */ ErrorCode EVALUATOR_AMBIGUOUS_BINDING;
    public static final /* enum */ ErrorCode EVALUATOR_LIKE_INVALID_INPUTS;
    public static final /* enum */ ErrorCode EVALUATOR_LIKE_PATTERN_INVALID_ESCAPE_SEQUENCE;
    public static final /* enum */ ErrorCode EVALUATOR_NON_INT_LIMIT_VALUE;
    public static final /* enum */ ErrorCode EVALUATOR_NEGATIVE_LIMIT;
    public static final /* enum */ ErrorCode EVALUATOR_DIVIDE_BY_ZERO;
    public static final /* enum */ ErrorCode EVALUATOR_MODULO_BY_ZERO;
    public static final /* enum */ ErrorCode SEMANTIC_ILLEGAL_GLOBAL_VARIABLE_ACCESS;
    public static final /* enum */ ErrorCode SEMANTIC_UNBOUND_BINDING;
    public static final /* enum */ ErrorCode SEMANTIC_AMBIGUOUS_BINDING;
    public static final /* enum */ ErrorCode SEMANTIC_INCORRECT_NODE_ARITY;
    public static final /* enum */ ErrorCode SEMANTIC_HAVING_USED_WITHOUT_GROUP_BY;
    public static final /* enum */ ErrorCode SEMANTIC_ASTERISK_USED_WITH_OTHER_ITEMS;
    public static final /* enum */ ErrorCode UNIMPLEMENTED_FEATURE;
    private static final /* synthetic */ ErrorCode[] $VALUES;
    private final ErrorCategory category;
    private final Set<Property> properties;
    private final String messagePrefix;

    static {
        ErrorCode[] errorCodeArray = new ErrorCode[102];
        ErrorCode[] errorCodeArray2 = errorCodeArray;
        errorCodeArray[0] = LEXER_INVALID_CHAR = new LEXER_INVALID_CHAR("LEXER_INVALID_CHAR", 0);
        errorCodeArray[1] = LEXER_INVALID_OPERATOR = new LEXER_INVALID_OPERATOR("LEXER_INVALID_OPERATOR", 1);
        errorCodeArray[2] = LEXER_INVALID_LITERAL = new LEXER_INVALID_LITERAL("LEXER_INVALID_LITERAL", 2);
        errorCodeArray[3] = LEXER_INVALID_ION_LITERAL = new LEXER_INVALID_ION_LITERAL("LEXER_INVALID_ION_LITERAL", 3);
        errorCodeArray[4] = PARSE_MALFORMED_PARSE_TREE = new ErrorCode(ErrorCategory.PARSER, ErrorCodeKt.access$getLOC_TOKEN$p(), "Internal error - malformed parse tree detected");
        errorCodeArray[5] = PARSE_EXPECTED_KEYWORD = new PARSE_EXPECTED_KEYWORD("PARSE_EXPECTED_KEYWORD", 5);
        errorCodeArray[6] = PARSE_EXPECTED_TOKEN_TYPE = new PARSE_EXPECTED_TOKEN_TYPE("PARSE_EXPECTED_TOKEN_TYPE", 6);
        errorCodeArray[7] = PARSE_EXPECTED_2_TOKEN_TYPES = new PARSE_EXPECTED_2_TOKEN_TYPES("PARSE_EXPECTED_2_TOKEN_TYPES", 7);
        errorCodeArray[8] = PARSE_EXPECTED_NUMBER = new PARSE_EXPECTED_NUMBER("PARSE_EXPECTED_NUMBER", 8);
        errorCodeArray[9] = PARSE_EXPECTED_TYPE_NAME = new ErrorCode(ErrorCategory.PARSER, ErrorCodeKt.access$getLOC_TOKEN$p(), "expected type name, found");
        errorCodeArray[10] = PARSE_EXPECTED_WHEN_CLAUSE = new ErrorCode(ErrorCategory.PARSER, ErrorCodeKt.access$getLOC_TOKEN$p(), "expected WHEN clause in CASE");
        errorCodeArray[11] = PARSE_EXPECTED_WHERE_CLAUSE = new ErrorCode(ErrorCategory.PARSER, ErrorCodeKt.access$getLOC_TOKEN$p(), "expected WHERE clause");
        errorCodeArray[12] = PARSE_EXPECTED_CONFLICT_ACTION = new ErrorCode(ErrorCategory.PARSER, ErrorCodeKt.access$getLOC_TOKEN$p(), "expected <conflict action>");
        errorCodeArray[13] = PARSE_EXPECTED_RETURNING_CLAUSE = new ErrorCode(ErrorCategory.PARSER, ErrorCodeKt.access$getLOC_TOKEN$p(), "expected <returning mapping>");
        errorCodeArray[14] = PARSE_UNSUPPORTED_RETURNING_CLAUSE_SYNTAX = new ErrorCode(ErrorCategory.PARSER, ErrorCodeKt.access$getLOC_TOKEN$p(), "unsupported syntax in RETURNING clause");
        errorCodeArray[15] = PARSE_UNSUPPORTED_TOKEN = new ErrorCode(ErrorCategory.PARSER, ErrorCodeKt.access$getLOC_TOKEN$p(), "Unexpected token");
        errorCodeArray[16] = PARSE_UNSUPPORTED_LITERALS_GROUPBY = new ErrorCode(ErrorCategory.PARSER, ErrorCodeKt.access$getLOC_TOKEN$p(), "unsupported literal in GROUP BY");
        errorCodeArray[17] = PARSE_EXPECTED_MEMBER = new ErrorCode(ErrorCategory.PARSER, ErrorCodeKt.access$getLOC_TOKEN$p(), "expected MEMBER node");
        errorCodeArray[18] = PARSE_EXPECTED_DATE_PART = new ErrorCode(ErrorCategory.PARSER, ErrorCodeKt.access$getLOC_TOKEN$p(), "expected one of: [" + CollectionsKt.joinToString$default(LexerConstantsKt.getDATE_PART_KEYWORDS(), null, null, null, 0, null, null, 63, null) + ']');
        errorCodeArray[19] = PARSE_UNSUPPORTED_SELECT = new ErrorCode(ErrorCategory.PARSER, ErrorCodeKt.access$getLOC_TOKEN$p(), "unsupported use of SELECT");
        errorCodeArray[20] = PARSE_UNSUPPORTED_CASE = new ErrorCode(ErrorCategory.PARSER, ErrorCodeKt.access$getLOC_TOKEN$p(), "unsupported use of CASE");
        errorCodeArray[21] = PARSE_UNSUPPORTED_CASE_CLAUSE = new ErrorCode(ErrorCategory.PARSER, ErrorCodeKt.access$getLOC_TOKEN$p(), "Unsupported use of CASE statement");
        errorCodeArray[22] = PARSE_UNSUPPORTED_ALIAS = new ErrorCode(ErrorCategory.PARSER, ErrorCodeKt.access$getLOC_TOKEN$p(), "unsupported syntax for alias, `at` and `as` are supported");
        errorCodeArray[23] = PARSE_UNSUPPORTED_SYNTAX = new ErrorCode(ErrorCategory.PARSER, ErrorCodeKt.access$getLOC_TOKEN$p(), "unsupported Syntax");
        errorCodeArray[24] = PARSE_UNKNOWN_OPERATOR = new ErrorCode(ErrorCategory.PARSER, ErrorCodeKt.access$getLOC_TOKEN$p(), "unsupported operator");
        errorCodeArray[25] = PARSE_INVALID_PATH_COMPONENT = new PARSE_INVALID_PATH_COMPONENT("PARSE_INVALID_PATH_COMPONENT", 25);
        errorCodeArray[26] = PARSE_MISSING_IDENT_AFTER_AT = new ErrorCode(ErrorCategory.PARSER, ErrorCodeKt.access$getLOC_TOKEN$p(), "identifier expected after `@` symbol");
        errorCodeArray[27] = PARSE_UNEXPECTED_OPERATOR = new ErrorCode(ErrorCategory.PARSER, ErrorCodeKt.access$getLOC_TOKEN$p(), "unexpected operator");
        errorCodeArray[28] = PARSE_UNEXPECTED_TERM = new ErrorCode(ErrorCategory.PARSER, ErrorCodeKt.access$getLOC_TOKEN$p(), "unexpected term found");
        errorCodeArray[29] = PARSE_UNEXPECTED_TOKEN = new ErrorCode(ErrorCategory.PARSER, ErrorCodeKt.access$getLOC_TOKEN$p(), "unexpected token found");
        errorCodeArray[30] = PARSE_UNEXPECTED_KEYWORD = new ErrorCode(ErrorCategory.PARSER, ErrorCodeKt.access$getLOC_TOKEN$p(), "unexpected keyword found");
        errorCodeArray[31] = PARSE_EXPECTED_EXPRESSION = new ErrorCode(ErrorCategory.PARSER, ErrorCodeKt.access$getLOC_TOKEN$p(), "expected expression");
        errorCodeArray[32] = PARSE_EXPECTED_LEFT_PAREN_AFTER_CAST = new ErrorCode(ErrorCategory.PARSER, ErrorCodeKt.access$getLOC_TOKEN$p(), "expected left parenthesis after CAST");
        errorCodeArray[33] = PARSE_EXPECTED_LEFT_PAREN_VALUE_CONSTRUCTOR = new ErrorCode(ErrorCategory.PARSER, ErrorCodeKt.access$getLOC_TOKEN$p(), "expected left parenthesis");
        errorCodeArray[34] = PARSE_EXPECTED_LEFT_PAREN_BUILTIN_FUNCTION_CALL = new ErrorCode(ErrorCategory.PARSER, ErrorCodeKt.access$getLOC_TOKEN$p(), "expected left parenthesis");
        errorCodeArray[35] = PARSE_EXPECTED_RIGHT_PAREN_BUILTIN_FUNCTION_CALL = new ErrorCode(ErrorCategory.PARSER, ErrorCodeKt.access$getLOC_TOKEN$p(), "expected right parenthesis");
        errorCodeArray[36] = PARSE_EXPECTED_ARGUMENT_DELIMITER = new ErrorCode(ErrorCategory.PARSER, ErrorCodeKt.access$getLOC_TOKEN$p(), "expected argument delimiter");
        errorCodeArray[37] = PARSE_CAST_ARITY = new PARSE_CAST_ARITY("PARSE_CAST_ARITY", 37);
        errorCodeArray[38] = PARSE_INVALID_TYPE_PARAM = new ErrorCode(ErrorCategory.PARSER, ErrorCodeKt.access$getLOC_TOKEN$p(), "invalid value used for type parameter");
        errorCodeArray[39] = PARSE_INVALID_PRECISION_FOR_TIME = new ErrorCode(ErrorCategory.PARSER, ErrorCodeKt.access$getLOC_TOKEN$p(), "invalid precision used for TIME type");
        errorCodeArray[40] = PARSE_INVALID_DATE_STRING = new ErrorCode(ErrorCategory.PARSER, ErrorCodeKt.access$getLOC_TOKEN$p(), "expected date string to be of the format YYYY-MM-DD");
        errorCodeArray[41] = PARSE_INVALID_TIME_STRING = new ErrorCode(ErrorCategory.PARSER, ErrorCodeKt.access$getLOC_TOKEN$p(), "expected time string to be of the format HH:MM:SS[.dddd...][+|-HH:MM]");
        errorCodeArray[42] = PARSE_EMPTY_SELECT = new ErrorCode(ErrorCategory.PARSER, ErrorCodeKt.access$getLOC_TOKEN$p(), "found empty SELECT list");
        errorCodeArray[43] = PARSE_SELECT_MISSING_FROM = new ErrorCode(ErrorCategory.PARSER, ErrorCodeKt.access$getLOC_TOKEN$p(), "missing FROM after SELECT list");
        errorCodeArray[44] = PARSE_MISSING_OPERATION = new ErrorCode(ErrorCategory.PARSER, ErrorCodeKt.access$getLOC_TOKEN$p(), "expected DML or SELECT operation after FROM");
        errorCodeArray[45] = PARSE_MISSING_SET_ASSIGNMENT = new ErrorCode(ErrorCategory.PARSER, ErrorCodeKt.access$getLOC_TOKEN$p(), "expected assignment for SET");
        errorCodeArray[46] = PARSE_EXPECTED_IDENT_FOR_GROUP_NAME = new ErrorCode(ErrorCategory.PARSER, ErrorCodeKt.access$getLOC_TOKEN$p(), "expected identifier for GROUP name");
        errorCodeArray[47] = PARSE_EXPECTED_IDENT_FOR_ALIAS = new ErrorCode(ErrorCategory.PARSER, ErrorCodeKt.access$getLOC_TOKEN$p(), "expected identifier for alias");
        errorCodeArray[48] = PARSE_EXPECTED_AS_FOR_LET = new ErrorCode(ErrorCategory.PARSER, ErrorCodeKt.access$getLOC_TOKEN$p(), "expected AS for LET clause");
        errorCodeArray[49] = PARSE_UNSUPPORTED_CALL_WITH_STAR = new ErrorCode(ErrorCategory.PARSER, ErrorCodeKt.access$getLOC_TOKEN$p(), "function call, other than COUNT, with (*) as parameter is not supported");
        errorCodeArray[50] = PARSE_NON_UNARY_AGREGATE_FUNCTION_CALL = new ErrorCode(ErrorCategory.PARSER, ErrorCodeKt.access$getLOC_TOKEN$p(), "Aggregate function calls take 1 argument only");
        errorCodeArray[51] = PARSE_NO_STORED_PROCEDURE_PROVIDED = new ErrorCode(ErrorCategory.PARSER, ErrorCodeKt.access$getLOC_TOKEN$p(), "No stored procedure provided");
        errorCodeArray[52] = PARSE_MALFORMED_JOIN = new ErrorCode(ErrorCategory.PARSER, ErrorCodeKt.access$getLOC_TOKEN$p(), "malformed use of FROM with JOIN");
        errorCodeArray[53] = PARSE_EXPECTED_IDENT_FOR_AT = new ErrorCode(ErrorCategory.PARSER, ErrorCodeKt.access$getLOC_TOKEN$p(), "expected identifier for AT name");
        errorCodeArray[54] = PARSE_INVALID_CONTEXT_FOR_WILDCARD_IN_SELECT_LIST = new ErrorCode(ErrorCategory.PARSER, ErrorCodeKt.access$getLOC_TOKEN$p(), "Invalid use of * in select list");
        errorCodeArray[55] = PARSE_CANNOT_MIX_SQB_AND_WILDCARD_IN_SELECT_LIST = new ErrorCode(ErrorCategory.PARSER, ErrorCodeKt.access$getLOC_TOKEN$p(), "Cannot mix [] and * in the same expression in a select list");
        errorCodeArray[56] = PARSE_ASTERISK_IS_NOT_ALONE_IN_SELECT_LIST = new ErrorCode(ErrorCategory.PARSER, ErrorCodeKt.access$getLOCATION$p(), "Other expressions may not be present in the select list when '*' is used without dot notation.");
        errorCodeArray[57] = EVALUATOR_FEATURE_NOT_SUPPORTED_YET = new EVALUATOR_FEATURE_NOT_SUPPORTED_YET("EVALUATOR_FEATURE_NOT_SUPPORTED_YET", 57);
        errorCodeArray[58] = EVALUATOR_COUNT_DISTINCT_STAR = new EVALUATOR_COUNT_DISTINCT_STAR("EVALUATOR_COUNT_DISTINCT_STAR", 58);
        errorCodeArray[59] = EVALUATOR_BINDING_DOES_NOT_EXIST = new EVALUATOR_BINDING_DOES_NOT_EXIST("EVALUATOR_BINDING_DOES_NOT_EXIST", 59);
        errorCodeArray[60] = EVALUATOR_VARIABLE_NOT_INCLUDED_IN_GROUP_BY = new EVALUATOR_VARIABLE_NOT_INCLUDED_IN_GROUP_BY("EVALUATOR_VARIABLE_NOT_INCLUDED_IN_GROUP_BY", 60);
        errorCodeArray[61] = EVALUATOR_UNBOUND_PARAMETER = new ErrorCode(ErrorCategory.EVALUATOR, SetsKt.plus(ErrorCodeKt.access$getLOCATION$p(), (Iterable)SetsKt.setOf(Property.EXPECTED_PARAMETER_ORDINAL, Property.BOUND_PARAMETER_COUNT)), "No parameter bound for position!");
        errorCodeArray[62] = EVALUATOR_INVALID_CAST = new EVALUATOR_INVALID_CAST("EVALUATOR_INVALID_CAST", 62);
        errorCodeArray[63] = EVALUATOR_INVALID_CAST_NO_LOCATION = new EVALUATOR_INVALID_CAST_NO_LOCATION("EVALUATOR_INVALID_CAST_NO_LOCATION", 63);
        errorCodeArray[64] = EVALUATOR_CAST_FAILED = new EVALUATOR_CAST_FAILED("EVALUATOR_CAST_FAILED", 64);
        errorCodeArray[65] = EVALUATOR_CAST_FAILED_NO_LOCATION = new EVALUATOR_CAST_FAILED_NO_LOCATION("EVALUATOR_CAST_FAILED_NO_LOCATION", 65);
        errorCodeArray[66] = EVALUATOR_NO_SUCH_FUNCTION = new EVALUATOR_NO_SUCH_FUNCTION("EVALUATOR_NO_SUCH_FUNCTION", 66);
        errorCodeArray[67] = EVALUATOR_NO_SUCH_PROCEDURE = new EVALUATOR_NO_SUCH_PROCEDURE("EVALUATOR_NO_SUCH_PROCEDURE", 67);
        errorCodeArray[68] = EVALUATOR_INCORRECT_NUMBER_OF_ARGUMENTS_TO_FUNC_CALL = new ErrorCode(ErrorCategory.EVALUATOR, SetsKt.plus(ErrorCodeKt.access$getLOCATION$p(), (Iterable)SetsKt.setOf(Property.EXPECTED_ARITY_MIN, Property.EXPECTED_ARITY_MAX)), "Incorrect number of arguments to function call");
        errorCodeArray[69] = EVALUATOR_INCORRECT_NUMBER_OF_ARGUMENTS_TO_PROCEDURE_CALL = new ErrorCode(ErrorCategory.EVALUATOR, SetsKt.plus(ErrorCodeKt.access$getLOCATION$p(), (Iterable)SetsKt.setOf(Property.EXPECTED_ARITY_MIN, Property.EXPECTED_ARITY_MAX)), "Incorrect number of arguments to procedure call");
        errorCodeArray[70] = EVALUATOR_DATE_FIELD_OUT_OF_RANGE = new ErrorCode(ErrorCategory.EVALUATOR, ErrorCodeKt.access$getLOCATION$p(), "Date field out of range.");
        errorCodeArray[71] = EVALUATOR_INCORRECT_TYPE_OF_ARGUMENTS_TO_FUNC_CALL = new EVALUATOR_INCORRECT_TYPE_OF_ARGUMENTS_TO_FUNC_CALL("EVALUATOR_INCORRECT_TYPE_OF_ARGUMENTS_TO_FUNC_CALL", 71);
        errorCodeArray[72] = EVALUATOR_INCORRECT_TYPE_OF_ARGUMENTS_TO_PROCEDURE_CALL = new EVALUATOR_INCORRECT_TYPE_OF_ARGUMENTS_TO_PROCEDURE_CALL("EVALUATOR_INCORRECT_TYPE_OF_ARGUMENTS_TO_PROCEDURE_CALL", 72);
        errorCodeArray[73] = EVALUATOR_CONCAT_FAILED_DUE_TO_INCOMPATIBLE_TYPE = new EVALUATOR_CONCAT_FAILED_DUE_TO_INCOMPATIBLE_TYPE("EVALUATOR_CONCAT_FAILED_DUE_TO_INCOMPATIBLE_TYPE", 73);
        errorCodeArray[74] = EVALUATOR_INVALID_PRECISION_FOR_TIME = new ErrorCode(ErrorCategory.EVALUATOR, ErrorCodeKt.access$getLOCATION$p(), "invalid precision used for TIME type");
        errorCodeArray[75] = EVALUATOR_TIME_FIELD_OUT_OF_RANGE = new ErrorCode(ErrorCategory.EVALUATOR, ErrorCodeKt.access$getLOCATION$p(), "Invalid value for TIME type");
        errorCodeArray[76] = EVALUATOR_INVALID_TIMESTAMP_FORMAT_PATTERN = new EVALUATOR_INVALID_TIMESTAMP_FORMAT_PATTERN("EVALUATOR_INVALID_TIMESTAMP_FORMAT_PATTERN", 76);
        errorCodeArray[77] = EVALUATOR_INVALID_TIMESTAMP_FORMAT_PATTERN_TOKEN = new EVALUATOR_INVALID_TIMESTAMP_FORMAT_PATTERN_TOKEN("EVALUATOR_INVALID_TIMESTAMP_FORMAT_PATTERN_TOKEN", 77);
        errorCodeArray[78] = EVALUATOR_INVALID_TIMESTAMP_FORMAT_PATTERN_SYMBOL = new EVALUATOR_INVALID_TIMESTAMP_FORMAT_PATTERN_SYMBOL("EVALUATOR_INVALID_TIMESTAMP_FORMAT_PATTERN_SYMBOL", 78);
        errorCodeArray[79] = EVALUATOR_UNTERMINATED_TIMESTAMP_FORMAT_PATTERN_TOKEN = new EVALUATOR_UNTERMINATED_TIMESTAMP_FORMAT_PATTERN_TOKEN("EVALUATOR_UNTERMINATED_TIMESTAMP_FORMAT_PATTERN_TOKEN", 79);
        errorCodeArray[80] = EVALUATOR_INCOMPLETE_TIMESTAMP_FORMAT_PATTERN = new EVALUATOR_INCOMPLETE_TIMESTAMP_FORMAT_PATTERN("EVALUATOR_INCOMPLETE_TIMESTAMP_FORMAT_PATTERN", 80);
        errorCodeArray[81] = EVALUATOR_TIMESTAMP_FORMAT_PATTERN_DUPLICATE_FIELDS = new EVALUATOR_TIMESTAMP_FORMAT_PATTERN_DUPLICATE_FIELDS("EVALUATOR_TIMESTAMP_FORMAT_PATTERN_DUPLICATE_FIELDS", 81);
        errorCodeArray[82] = EVALUATOR_TIMESTAMP_FORMAT_PATTERN_HOUR_CLOCK_AM_PM_MISMATCH = new EVALUATOR_TIMESTAMP_FORMAT_PATTERN_HOUR_CLOCK_AM_PM_MISMATCH("EVALUATOR_TIMESTAMP_FORMAT_PATTERN_HOUR_CLOCK_AM_PM_MISMATCH", 82);
        errorCodeArray[83] = EVALUATOR_INVALID_TIMESTAMP_FORMAT_PATTERN_SYMBOL_FOR_PARSING = new EVALUATOR_INVALID_TIMESTAMP_FORMAT_PATTERN_SYMBOL_FOR_PARSING("EVALUATOR_INVALID_TIMESTAMP_FORMAT_PATTERN_SYMBOL_FOR_PARSING", 83);
        errorCodeArray[84] = EVALUATOR_ION_TIMESTAMP_PARSE_FAILURE = new ErrorCode(ErrorCategory.EVALUATOR, ErrorCodeKt.access$getLOCATION$p(), "Failed to parse Ion timestamp");
        errorCodeArray[85] = EVALUATOR_CUSTOM_TIMESTAMP_PARSE_FAILURE = new ErrorCode(ErrorCategory.EVALUATOR, SetsKt.plus(ErrorCodeKt.access$getLOCATION$p(), (Iterable)SetsKt.setOf(Property.TIMESTAMP_FORMAT_PATTERN)), "Failed to parse custom timestamp using the specified format pattern");
        errorCodeArray[86] = EVALUATOR_PRECISION_LOSS_WHEN_PARSING_TIMESTAMP = new ErrorCode(ErrorCategory.EVALUATOR, ErrorCodeKt.access$getLOCATION$p(), "loss of precision when parsing timestamp");
        errorCodeArray[87] = EVALUATOR_INTEGER_OVERFLOW = new ErrorCode(ErrorCategory.EVALUATOR, ErrorCodeKt.access$getLOCATION$p(), "Int overflow or underflow");
        errorCodeArray[88] = EVALUATOR_AMBIGUOUS_BINDING = new EVALUATOR_AMBIGUOUS_BINDING("EVALUATOR_AMBIGUOUS_BINDING", 88);
        errorCodeArray[89] = EVALUATOR_LIKE_INVALID_INPUTS = new EVALUATOR_LIKE_INVALID_INPUTS("EVALUATOR_LIKE_INVALID_INPUTS", 89);
        errorCodeArray[90] = EVALUATOR_LIKE_PATTERN_INVALID_ESCAPE_SEQUENCE = new EVALUATOR_LIKE_PATTERN_INVALID_ESCAPE_SEQUENCE("EVALUATOR_LIKE_PATTERN_INVALID_ESCAPE_SEQUENCE", 90);
        errorCodeArray[91] = EVALUATOR_NON_INT_LIMIT_VALUE = new EVALUATOR_NON_INT_LIMIT_VALUE("EVALUATOR_NON_INT_LIMIT_VALUE", 91);
        errorCodeArray[92] = EVALUATOR_NEGATIVE_LIMIT = new ErrorCode(ErrorCategory.EVALUATOR, ErrorCodeKt.access$getLOCATION$p(), "LIMIT must not be negative");
        errorCodeArray[93] = EVALUATOR_DIVIDE_BY_ZERO = new ErrorCode(ErrorCategory.EVALUATOR, ErrorCodeKt.access$getLOCATION$p(), "/ by zero");
        errorCodeArray[94] = EVALUATOR_MODULO_BY_ZERO = new ErrorCode(ErrorCategory.EVALUATOR, ErrorCodeKt.access$getLOCATION$p(), "% by zero");
        errorCodeArray[95] = SEMANTIC_ILLEGAL_GLOBAL_VARIABLE_ACCESS = new SEMANTIC_ILLEGAL_GLOBAL_VARIABLE_ACCESS("SEMANTIC_ILLEGAL_GLOBAL_VARIABLE_ACCESS", 95);
        errorCodeArray[96] = SEMANTIC_UNBOUND_BINDING = new SEMANTIC_UNBOUND_BINDING("SEMANTIC_UNBOUND_BINDING", 96);
        errorCodeArray[97] = SEMANTIC_AMBIGUOUS_BINDING = new SEMANTIC_AMBIGUOUS_BINDING("SEMANTIC_AMBIGUOUS_BINDING", 97);
        errorCodeArray[98] = SEMANTIC_INCORRECT_NODE_ARITY = new SEMANTIC_INCORRECT_NODE_ARITY("SEMANTIC_INCORRECT_NODE_ARITY", 98);
        errorCodeArray[99] = SEMANTIC_HAVING_USED_WITHOUT_GROUP_BY = new ErrorCode(ErrorCategory.EVALUATOR, ErrorCodeKt.access$getLOCATION$p(), "HAVING cannot be used without GROUP BY or GROUP ALL");
        errorCodeArray[100] = SEMANTIC_ASTERISK_USED_WITH_OTHER_ITEMS = new ErrorCode(ErrorCategory.EVALUATOR, ErrorCodeKt.access$getLOCATION$p(), "`*` may not be used with other items in a select list");
        errorCodeArray[101] = UNIMPLEMENTED_FEATURE = new UNIMPLEMENTED_FEATURE("UNIMPLEMENTED_FEATURE", 101);
        $VALUES = errorCodeArray;
    }

    @NotNull
    protected final String getTokenString(@Nullable PropertyValueMap errorContext) {
        Object object = errorContext;
        if (object == null || (object = ((PropertyValueMap)object).get(Property.TOKEN_STRING)) == null || (object = ((PropertyValue)object).stringValue()) == null) {
            object = "<UNKNOWN>";
        }
        return object;
    }

    @NotNull
    protected final String getTokenValue(@Nullable PropertyValueMap errorContext) {
        Object object = errorContext;
        if (object == null || (object = ((PropertyValueMap)object).get(Property.TOKEN_VALUE)) == null || (object = ((PropertyValue)object).ionValue()) == null || (object = object.toString()) == null) {
            object = "<UNKNOWN>";
        }
        return object;
    }

    @NotNull
    protected final String getTokenType(@Nullable PropertyValueMap errorContext) {
        Object object = errorContext;
        if (object == null || (object = ((PropertyValueMap)object).get(Property.TOKEN_TYPE)) == null || (object = ((PropertyValue)object).tokenTypeValue()) == null || (object = ((Enum)object).toString()) == null) {
            object = "<UNKNOWN>";
        }
        return object;
    }

    @NotNull
    protected final String getKeyword(@Nullable PropertyValueMap errorContext) {
        Object object = errorContext;
        if (object == null || (object = ((PropertyValueMap)object).get(Property.KEYWORD)) == null || (object = ((PropertyValue)object).stringValue()) == null) {
            object = "<UNKNOWN>";
        }
        return object;
    }

    @NotNull
    protected final String getTokenTypeAndTokenValue(@Nullable PropertyValueMap errorContext) {
        return this.getTokenType(errorContext) + " : " + this.getTokenValue(errorContext);
    }

    @NotNull
    protected String detailMessagePrefix() {
        return this.messagePrefix;
    }

    @NotNull
    protected String detailMessageSuffix(@Nullable PropertyValueMap errorContext) {
        return this.getTokenTypeAndTokenValue(errorContext);
    }

    @NotNull
    public String getErrorMessage(@Nullable PropertyValueMap errorContext) {
        return this.detailMessagePrefix() + ", " + this.detailMessageSuffix(errorContext);
    }

    @NotNull
    public final String errorCategory() {
        return this.category.toString();
    }

    @NotNull
    public final Set<Property> getProperties() {
        return this.properties;
    }

    private ErrorCode(ErrorCategory category, Set<? extends Property> properties, String messagePrefix) {
        this.category = category;
        this.properties = properties;
        this.messagePrefix = messagePrefix;
    }

    public /* synthetic */ ErrorCode(String $enum_name_or_ordinal$0, int $enum_name_or_ordinal$1, ErrorCategory category, Set properties, String messagePrefix, DefaultConstructorMarker $constructor_marker) {
        this(category, properties, messagePrefix);
    }

    public static ErrorCode[] values() {
        return (ErrorCode[])$VALUES.clone();
    }

    public static ErrorCode valueOf(String string) {
        return Enum.valueOf(ErrorCode.class, string);
    }

    @Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u00c6\u0001\u0018\u00002\u00020\u0001J\u0012\u0010\u0002\u001a\u00020\u00032\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005H\u0014\u00a8\u0006\u0006"}, d2={"Lorg/partiql/lang/errors/ErrorCode$LEXER_INVALID_CHAR;", "Lorg/partiql/lang/errors/ErrorCode;", "detailMessageSuffix", "", "errorContext", "Lorg/partiql/lang/errors/PropertyValueMap;", "lang"})
    static final class LEXER_INVALID_CHAR
    extends ErrorCode {
        @Override
        @NotNull
        protected String detailMessageSuffix(@Nullable PropertyValueMap errorContext) {
            return this.getTokenString(errorContext);
        }

        /*
         * WARNING - void declaration
         */
        LEXER_INVALID_CHAR() {
            void var1_1;
        }
    }

    @Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u00c6\u0001\u0018\u00002\u00020\u0001J\u0012\u0010\u0002\u001a\u00020\u00032\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005H\u0014\u00a8\u0006\u0006"}, d2={"Lorg/partiql/lang/errors/ErrorCode$LEXER_INVALID_OPERATOR;", "Lorg/partiql/lang/errors/ErrorCode;", "detailMessageSuffix", "", "errorContext", "Lorg/partiql/lang/errors/PropertyValueMap;", "lang"})
    static final class LEXER_INVALID_OPERATOR
    extends ErrorCode {
        @Override
        @NotNull
        protected String detailMessageSuffix(@Nullable PropertyValueMap errorContext) {
            return this.getTokenString(errorContext);
        }

        /*
         * WARNING - void declaration
         */
        LEXER_INVALID_OPERATOR() {
            void var1_1;
        }
    }

    @Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u00c6\u0001\u0018\u00002\u00020\u0001J\u0012\u0010\u0002\u001a\u00020\u00032\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005H\u0014\u00a8\u0006\u0006"}, d2={"Lorg/partiql/lang/errors/ErrorCode$LEXER_INVALID_LITERAL;", "Lorg/partiql/lang/errors/ErrorCode;", "detailMessageSuffix", "", "errorContext", "Lorg/partiql/lang/errors/PropertyValueMap;", "lang"})
    static final class LEXER_INVALID_LITERAL
    extends ErrorCode {
        @Override
        @NotNull
        protected String detailMessageSuffix(@Nullable PropertyValueMap errorContext) {
            return this.getTokenString(errorContext);
        }

        /*
         * WARNING - void declaration
         */
        LEXER_INVALID_LITERAL() {
            void var1_1;
        }
    }

    @Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u00c6\u0001\u0018\u00002\u00020\u0001J\u0012\u0010\u0002\u001a\u00020\u00032\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005H\u0014\u00a8\u0006\u0006"}, d2={"Lorg/partiql/lang/errors/ErrorCode$LEXER_INVALID_ION_LITERAL;", "Lorg/partiql/lang/errors/ErrorCode;", "detailMessageSuffix", "", "errorContext", "Lorg/partiql/lang/errors/PropertyValueMap;", "lang"})
    static final class LEXER_INVALID_ION_LITERAL
    extends ErrorCode {
        @Override
        @NotNull
        protected String detailMessageSuffix(@Nullable PropertyValueMap errorContext) {
            return this.getTokenString(errorContext);
        }

        /*
         * WARNING - void declaration
         */
        LEXER_INVALID_ION_LITERAL() {
            void var1_1;
        }
    }

    @Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u00c6\u0001\u0018\u00002\u00020\u0001J\u0012\u0010\u0002\u001a\u00020\u00032\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005H\u0014\u00a8\u0006\u0006"}, d2={"Lorg/partiql/lang/errors/ErrorCode$PARSE_EXPECTED_KEYWORD;", "Lorg/partiql/lang/errors/ErrorCode;", "detailMessageSuffix", "", "errorContext", "Lorg/partiql/lang/errors/PropertyValueMap;", "lang"})
    static final class PARSE_EXPECTED_KEYWORD
    extends ErrorCode {
        @Override
        @NotNull
        protected String detailMessageSuffix(@Nullable PropertyValueMap errorContext) {
            return this.getKeyword(errorContext);
        }

        /*
         * WARNING - void declaration
         */
        PARSE_EXPECTED_KEYWORD() {
            void var1_1;
        }
    }

    @Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u00c6\u0001\u0018\u00002\u00020\u0001J\u0012\u0010\u0002\u001a\u00020\u00032\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005H\u0014\u00a8\u0006\u0006"}, d2={"Lorg/partiql/lang/errors/ErrorCode$PARSE_EXPECTED_TOKEN_TYPE;", "Lorg/partiql/lang/errors/ErrorCode;", "detailMessageSuffix", "", "errorContext", "Lorg/partiql/lang/errors/PropertyValueMap;", "lang"})
    static final class PARSE_EXPECTED_TOKEN_TYPE
    extends ErrorCode {
        @Override
        @NotNull
        protected String detailMessageSuffix(@Nullable PropertyValueMap errorContext) {
            Object object = errorContext;
            if (object == null || (object = ((PropertyValueMap)object).get(Property.EXPECTED_TOKEN_TYPE)) == null || (object = ((PropertyValue)object).tokenTypeValue()) == null || (object = ((Enum)object).toString()) == null) {
                object = "<UNKNOWN>" + "found " + this.getTokenType(errorContext);
            }
            return object;
        }

        /*
         * WARNING - void declaration
         */
        PARSE_EXPECTED_TOKEN_TYPE() {
            void var1_1;
        }
    }

    @Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u00c6\u0001\u0018\u00002\u00020\u0001J\u0012\u0010\u0002\u001a\u00020\u00032\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005H\u0014\u00a8\u0006\u0006"}, d2={"Lorg/partiql/lang/errors/ErrorCode$PARSE_EXPECTED_2_TOKEN_TYPES;", "Lorg/partiql/lang/errors/ErrorCode;", "detailMessageSuffix", "", "errorContext", "Lorg/partiql/lang/errors/PropertyValueMap;", "lang"})
    static final class PARSE_EXPECTED_2_TOKEN_TYPES
    extends ErrorCode {
        @Override
        @NotNull
        protected String detailMessageSuffix(@Nullable PropertyValueMap errorContext) {
            PropertyValueMap propertyValueMap = errorContext;
            PropertyValueMap propertyValueMap2 = errorContext;
            return "expected " + (propertyValueMap != null ? ErrorCodeKt.access$getAsString(propertyValueMap, Property.EXPECTED_TOKEN_TYPE_1_OF_2, "<UNKNOWN>") : null) + " or " + (propertyValueMap2 != null ? ErrorCodeKt.access$getAsString(propertyValueMap2, Property.EXPECTED_TOKEN_TYPE_2_OF_2, "<UNKNOWN>") : null) + " but found " + this.getTokenType(errorContext);
        }

        /*
         * WARNING - void declaration
         */
        PARSE_EXPECTED_2_TOKEN_TYPES() {
            void var1_1;
        }
    }

    @Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u00c6\u0001\u0018\u00002\u00020\u0001J\u0012\u0010\u0002\u001a\u00020\u00032\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005H\u0014\u00a8\u0006\u0006"}, d2={"Lorg/partiql/lang/errors/ErrorCode$PARSE_EXPECTED_NUMBER;", "Lorg/partiql/lang/errors/ErrorCode;", "detailMessageSuffix", "", "errorContext", "Lorg/partiql/lang/errors/PropertyValueMap;", "lang"})
    static final class PARSE_EXPECTED_NUMBER
    extends ErrorCode {
        @Override
        @NotNull
        protected String detailMessageSuffix(@Nullable PropertyValueMap errorContext) {
            return this.getTokenValue(errorContext);
        }

        /*
         * WARNING - void declaration
         */
        PARSE_EXPECTED_NUMBER() {
            void var1_1;
        }
    }

    @Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u00c6\u0001\u0018\u00002\u00020\u0001J\u0012\u0010\u0002\u001a\u00020\u00032\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005H\u0016\u00a8\u0006\u0006"}, d2={"Lorg/partiql/lang/errors/ErrorCode$PARSE_INVALID_PATH_COMPONENT;", "Lorg/partiql/lang/errors/ErrorCode;", "getErrorMessage", "", "errorContext", "Lorg/partiql/lang/errors/PropertyValueMap;", "lang"})
    static final class PARSE_INVALID_PATH_COMPONENT
    extends ErrorCode {
        @Override
        @NotNull
        public String getErrorMessage(@Nullable PropertyValueMap errorContext) {
            StringBuilder stringBuilder = new StringBuilder().append("Invalid path component, expecting either an ").append((Object)TokenType.IDENTIFIER).append(" or ").append((Object)TokenType.STAR).append(", ").append("got: ");
            Object object = errorContext;
            if (object == null || (object = ((PropertyValueMap)object).get(Property.TOKEN_TYPE)) == null) {
                object = "<UNKNOWN>";
            }
            StringBuilder stringBuilder2 = stringBuilder.append(object).append(' ').append("with value: ");
            Object object2 = errorContext;
            if (object2 == null || (object2 = ((PropertyValueMap)object2).get(Property.TOKEN_VALUE)) == null) {
                object2 = "<UNKNOWN>";
            }
            return stringBuilder2.append(object2).toString();
        }

        /*
         * WARNING - void declaration
         */
        PARSE_INVALID_PATH_COMPONENT() {
            void var1_1;
        }
    }

    @Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u00c6\u0001\u0018\u00002\u00020\u0001J\u0012\u0010\u0002\u001a\u00020\u00032\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005H\u0016\u00a8\u0006\u0006"}, d2={"Lorg/partiql/lang/errors/ErrorCode$PARSE_CAST_ARITY;", "Lorg/partiql/lang/errors/ErrorCode;", "getErrorMessage", "", "errorContext", "Lorg/partiql/lang/errors/PropertyValueMap;", "lang"})
    static final class PARSE_CAST_ARITY
    extends ErrorCode {
        @Override
        @NotNull
        public String getErrorMessage(@Nullable PropertyValueMap errorContext) {
            Object object;
            Object object2;
            StringBuilder stringBuilder = new StringBuilder().append("Cast to type ");
            Object object3 = errorContext;
            if (object3 == null || (object3 = ((PropertyValueMap)object3).get(Property.CAST_TO)) == null || (object3 = ((PropertyValue)object3).stringValue()) == null) {
                object3 = "<UNKNOWN>";
            }
            return stringBuilder.append((String)object3).append(" has incorrect arity.").append("Correct arity is ").append((object2 = errorContext) != null && (object2 = ((PropertyValueMap)object2).get(Property.EXPECTED_ARITY_MIN)) != null ? Integer.valueOf(((PropertyValue)object2).integerValue()) : "<UNKNOWN>").append("..").append((object = errorContext) != null && (object = ((PropertyValueMap)object).get(Property.EXPECTED_ARITY_MAX)) != null ? Integer.valueOf(((PropertyValue)object).integerValue()) : "<UNKNOWN>").toString();
        }

        /*
         * WARNING - void declaration
         */
        PARSE_CAST_ARITY() {
            void var1_1;
        }
    }

    @Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u00c6\u0001\u0018\u00002\u00020\u0001J\u0012\u0010\u0002\u001a\u00020\u00032\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005H\u0016\u00a8\u0006\u0006"}, d2={"Lorg/partiql/lang/errors/ErrorCode$EVALUATOR_FEATURE_NOT_SUPPORTED_YET;", "Lorg/partiql/lang/errors/ErrorCode;", "getErrorMessage", "", "errorContext", "Lorg/partiql/lang/errors/PropertyValueMap;", "lang"})
    static final class EVALUATOR_FEATURE_NOT_SUPPORTED_YET
    extends ErrorCode {
        @Override
        @NotNull
        public String getErrorMessage(@Nullable PropertyValueMap errorContext) {
            StringBuilder stringBuilder = new StringBuilder().append("Feature '");
            Object object = errorContext;
            if (object == null || (object = ((PropertyValueMap)object).get(Property.FEATURE_NAME)) == null || (object = ((PropertyValue)object).stringValue()) == null) {
                object = "<UNKNOWN>";
            }
            return stringBuilder.append((String)object).append("' not supported yet").toString();
        }

        /*
         * WARNING - void declaration
         */
        EVALUATOR_FEATURE_NOT_SUPPORTED_YET() {
            void var1_1;
        }
    }

    @Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u00c6\u0001\u0018\u00002\u00020\u0001J\u0012\u0010\u0002\u001a\u00020\u00032\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005H\u0016\u00a8\u0006\u0006"}, d2={"Lorg/partiql/lang/errors/ErrorCode$EVALUATOR_COUNT_DISTINCT_STAR;", "Lorg/partiql/lang/errors/ErrorCode;", "getErrorMessage", "", "errorContext", "Lorg/partiql/lang/errors/PropertyValueMap;", "lang"})
    static final class EVALUATOR_COUNT_DISTINCT_STAR
    extends ErrorCode {
        @Override
        @NotNull
        public String getErrorMessage(@Nullable PropertyValueMap errorContext) {
            return "COUNT(DISTINCT *) is not supported";
        }

        /*
         * WARNING - void declaration
         */
        EVALUATOR_COUNT_DISTINCT_STAR() {
            void var1_1;
        }
    }

    @Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u00c6\u0001\u0018\u00002\u00020\u0001J\u0012\u0010\u0002\u001a\u00020\u00032\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005H\u0016\u00a8\u0006\u0006"}, d2={"Lorg/partiql/lang/errors/ErrorCode$EVALUATOR_BINDING_DOES_NOT_EXIST;", "Lorg/partiql/lang/errors/ErrorCode;", "getErrorMessage", "", "errorContext", "Lorg/partiql/lang/errors/PropertyValueMap;", "lang"})
    static final class EVALUATOR_BINDING_DOES_NOT_EXIST
    extends ErrorCode {
        @Override
        @NotNull
        public String getErrorMessage(@Nullable PropertyValueMap errorContext) {
            StringBuilder stringBuilder = new StringBuilder().append("Binding '");
            Object object = errorContext;
            if (object == null || (object = ((PropertyValueMap)object).get(Property.BINDING_NAME)) == null || (object = ((PropertyValue)object).stringValue()) == null) {
                object = "<UNKNOWN>";
            }
            return stringBuilder.append((String)object).append("' does not exist").toString();
        }

        /*
         * WARNING - void declaration
         */
        EVALUATOR_BINDING_DOES_NOT_EXIST() {
            void var1_1;
        }
    }

    @Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u00c6\u0001\u0018\u00002\u00020\u0001J\u0012\u0010\u0002\u001a\u00020\u00032\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005H\u0016\u00a8\u0006\u0006"}, d2={"Lorg/partiql/lang/errors/ErrorCode$EVALUATOR_VARIABLE_NOT_INCLUDED_IN_GROUP_BY;", "Lorg/partiql/lang/errors/ErrorCode;", "getErrorMessage", "", "errorContext", "Lorg/partiql/lang/errors/PropertyValueMap;", "lang"})
    static final class EVALUATOR_VARIABLE_NOT_INCLUDED_IN_GROUP_BY
    extends ErrorCode {
        @Override
        @NotNull
        public String getErrorMessage(@Nullable PropertyValueMap errorContext) {
            StringBuilder stringBuilder = new StringBuilder().append("Variable '");
            Object object = errorContext;
            if (object == null || (object = ((PropertyValueMap)object).get(Property.BINDING_NAME)) == null || (object = ((PropertyValue)object).stringValue()) == null) {
                object = "<UNKNOWN>";
            }
            return stringBuilder.append((String)object).append("' ").append("must appear in the GROUP BY clause or be used in an aggregation function").toString();
        }

        /*
         * WARNING - void declaration
         */
        EVALUATOR_VARIABLE_NOT_INCLUDED_IN_GROUP_BY() {
            void var1_1;
        }
    }

    @Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u00c6\u0001\u0018\u00002\u00020\u0001J\u0012\u0010\u0002\u001a\u00020\u00032\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005H\u0016\u00a8\u0006\u0006"}, d2={"Lorg/partiql/lang/errors/ErrorCode$EVALUATOR_INVALID_CAST;", "Lorg/partiql/lang/errors/ErrorCode;", "getErrorMessage", "", "errorContext", "Lorg/partiql/lang/errors/PropertyValueMap;", "lang"})
    static final class EVALUATOR_INVALID_CAST
    extends ErrorCode {
        @Override
        @NotNull
        public String getErrorMessage(@Nullable PropertyValueMap errorContext) {
            StringBuilder stringBuilder = new StringBuilder().append("Cannot convert ");
            Object object = errorContext;
            if (object == null || (object = ((PropertyValueMap)object).get(Property.CAST_FROM)) == null || (object = ((PropertyValue)object).stringValue()) == null) {
                object = "<UNKNOWN>";
            }
            StringBuilder stringBuilder2 = stringBuilder.append((String)object).append(' ').append("to ");
            Object object2 = errorContext;
            if (object2 == null || (object2 = ((PropertyValueMap)object2).get(Property.CAST_TO)) == null || (object2 = ((PropertyValue)object2).stringValue()) == null) {
                object2 = "<UNKNOWN>";
            }
            return stringBuilder2.append((String)object2).toString();
        }

        /*
         * WARNING - void declaration
         */
        EVALUATOR_INVALID_CAST() {
            void var1_1;
        }
    }

    @Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u00c6\u0001\u0018\u00002\u00020\u0001J\u0012\u0010\u0002\u001a\u00020\u00032\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005H\u0016\u00a8\u0006\u0006"}, d2={"Lorg/partiql/lang/errors/ErrorCode$EVALUATOR_INVALID_CAST_NO_LOCATION;", "Lorg/partiql/lang/errors/ErrorCode;", "getErrorMessage", "", "errorContext", "Lorg/partiql/lang/errors/PropertyValueMap;", "lang"})
    static final class EVALUATOR_INVALID_CAST_NO_LOCATION
    extends ErrorCode {
        @Override
        @NotNull
        public String getErrorMessage(@Nullable PropertyValueMap errorContext) {
            StringBuilder stringBuilder = new StringBuilder().append("Cannot convert ");
            Object object = errorContext;
            if (object == null || (object = ((PropertyValueMap)object).get(Property.CAST_FROM)) == null || (object = ((PropertyValue)object).stringValue()) == null) {
                object = "<UNKNOWN>";
            }
            StringBuilder stringBuilder2 = stringBuilder.append((String)object).append(' ').append("to ");
            Object object2 = errorContext;
            if (object2 == null || (object2 = ((PropertyValueMap)object2).get(Property.CAST_TO)) == null || (object2 = ((PropertyValue)object2).stringValue()) == null) {
                object2 = "<UNKNOWN>";
            }
            return stringBuilder2.append((String)object2).toString();
        }

        /*
         * WARNING - void declaration
         */
        EVALUATOR_INVALID_CAST_NO_LOCATION() {
            void var1_1;
        }
    }

    @Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u00c6\u0001\u0018\u00002\u00020\u0001J\u0012\u0010\u0002\u001a\u00020\u00032\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005H\u0016\u00a8\u0006\u0006"}, d2={"Lorg/partiql/lang/errors/ErrorCode$EVALUATOR_CAST_FAILED;", "Lorg/partiql/lang/errors/ErrorCode;", "getErrorMessage", "", "errorContext", "Lorg/partiql/lang/errors/PropertyValueMap;", "lang"})
    static final class EVALUATOR_CAST_FAILED
    extends ErrorCode {
        @Override
        @NotNull
        public String getErrorMessage(@Nullable PropertyValueMap errorContext) {
            StringBuilder stringBuilder = new StringBuilder().append("Failed to convert ");
            Object object = errorContext;
            if (object == null || (object = ((PropertyValueMap)object).get(Property.CAST_FROM)) == null || (object = ((PropertyValue)object).stringValue()) == null) {
                object = "<UNKNOWN>";
            }
            StringBuilder stringBuilder2 = stringBuilder.append((String)object).append(' ').append("to ");
            Object object2 = errorContext;
            if (object2 == null || (object2 = ((PropertyValueMap)object2).get(Property.CAST_TO)) == null || (object2 = ((PropertyValue)object2).stringValue()) == null) {
                object2 = "<UNKNOWN>";
            }
            return stringBuilder2.append((String)object2).toString();
        }

        /*
         * WARNING - void declaration
         */
        EVALUATOR_CAST_FAILED() {
            void var1_1;
        }
    }

    @Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u00c6\u0001\u0018\u00002\u00020\u0001J\u0012\u0010\u0002\u001a\u00020\u00032\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005H\u0016\u00a8\u0006\u0006"}, d2={"Lorg/partiql/lang/errors/ErrorCode$EVALUATOR_CAST_FAILED_NO_LOCATION;", "Lorg/partiql/lang/errors/ErrorCode;", "getErrorMessage", "", "errorContext", "Lorg/partiql/lang/errors/PropertyValueMap;", "lang"})
    static final class EVALUATOR_CAST_FAILED_NO_LOCATION
    extends ErrorCode {
        @Override
        @NotNull
        public String getErrorMessage(@Nullable PropertyValueMap errorContext) {
            StringBuilder stringBuilder = new StringBuilder().append("Failed to convert ");
            Object object = errorContext;
            if (object == null || (object = ((PropertyValueMap)object).get(Property.CAST_FROM)) == null || (object = ((PropertyValue)object).stringValue()) == null) {
                object = "<UNKNOWN>";
            }
            StringBuilder stringBuilder2 = stringBuilder.append((String)object).append(' ').append("to ");
            Object object2 = errorContext;
            if (object2 == null || (object2 = ((PropertyValueMap)object2).get(Property.CAST_TO)) == null || (object2 = ((PropertyValue)object2).stringValue()) == null) {
                object2 = "<UNKNOWN>";
            }
            return stringBuilder2.append((String)object2).toString();
        }

        /*
         * WARNING - void declaration
         */
        EVALUATOR_CAST_FAILED_NO_LOCATION() {
            void var1_1;
        }
    }

    @Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u00c6\u0001\u0018\u00002\u00020\u0001J\u0012\u0010\u0002\u001a\u00020\u00032\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005H\u0016\u00a8\u0006\u0006"}, d2={"Lorg/partiql/lang/errors/ErrorCode$EVALUATOR_NO_SUCH_FUNCTION;", "Lorg/partiql/lang/errors/ErrorCode;", "getErrorMessage", "", "errorContext", "Lorg/partiql/lang/errors/PropertyValueMap;", "lang"})
    static final class EVALUATOR_NO_SUCH_FUNCTION
    extends ErrorCode {
        @Override
        @NotNull
        public String getErrorMessage(@Nullable PropertyValueMap errorContext) {
            StringBuilder stringBuilder = new StringBuilder().append("No such function: ");
            Object object = errorContext;
            if (object == null || (object = ((PropertyValueMap)object).get(Property.FUNCTION_NAME)) == null || (object = ((PropertyValue)object).stringValue()) == null) {
                object = "<UNKNOWN>";
            }
            return stringBuilder.append((String)object).append(' ').toString();
        }

        /*
         * WARNING - void declaration
         */
        EVALUATOR_NO_SUCH_FUNCTION() {
            void var1_1;
        }
    }

    @Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u00c6\u0001\u0018\u00002\u00020\u0001J\u0012\u0010\u0002\u001a\u00020\u00032\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005H\u0016\u00a8\u0006\u0006"}, d2={"Lorg/partiql/lang/errors/ErrorCode$EVALUATOR_NO_SUCH_PROCEDURE;", "Lorg/partiql/lang/errors/ErrorCode;", "getErrorMessage", "", "errorContext", "Lorg/partiql/lang/errors/PropertyValueMap;", "lang"})
    static final class EVALUATOR_NO_SUCH_PROCEDURE
    extends ErrorCode {
        @Override
        @NotNull
        public String getErrorMessage(@Nullable PropertyValueMap errorContext) {
            StringBuilder stringBuilder = new StringBuilder().append("No such stored procedure: ");
            Object object = errorContext;
            if (object == null || (object = ((PropertyValueMap)object).get(Property.PROCEDURE_NAME)) == null || (object = ((PropertyValue)object).stringValue()) == null) {
                object = "<UNKNOWN>";
            }
            return stringBuilder.append((String)object).append(' ').toString();
        }

        /*
         * WARNING - void declaration
         */
        EVALUATOR_NO_SUCH_PROCEDURE() {
            void var1_1;
        }
    }

    @Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u00c6\u0001\u0018\u00002\u00020\u0001J\u0012\u0010\u0002\u001a\u00020\u00032\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005H\u0016\u00a8\u0006\u0006"}, d2={"Lorg/partiql/lang/errors/ErrorCode$EVALUATOR_INCORRECT_TYPE_OF_ARGUMENTS_TO_FUNC_CALL;", "Lorg/partiql/lang/errors/ErrorCode;", "getErrorMessage", "", "errorContext", "Lorg/partiql/lang/errors/PropertyValueMap;", "lang"})
    static final class EVALUATOR_INCORRECT_TYPE_OF_ARGUMENTS_TO_FUNC_CALL
    extends ErrorCode {
        @Override
        @NotNull
        public String getErrorMessage(@Nullable PropertyValueMap errorContext) {
            StringBuilder stringBuilder = new StringBuilder().append("Invalid argument types for ");
            Object object = errorContext;
            if (object == null || (object = ((PropertyValueMap)object).get(Property.FUNCTION_NAME)) == null) {
                object = "<UNKNOWN>";
            }
            StringBuilder stringBuilder2 = stringBuilder.append(object).append(", ").append("expected: ");
            Object object2 = errorContext;
            if (object2 == null || (object2 = ((PropertyValueMap)object2).get(Property.EXPECTED_ARGUMENT_TYPES)) == null) {
                object2 = "<UNKNOWN>";
            }
            StringBuilder stringBuilder3 = stringBuilder2.append(object2).append(' ').append("got: ");
            Object object3 = errorContext;
            if (object3 == null || (object3 = ((PropertyValueMap)object3).get(Property.ACTUAL_ARGUMENT_TYPES)) == null) {
                object3 = "<UNKNOWN>";
            }
            return stringBuilder3.append(object3).toString();
        }

        /*
         * WARNING - void declaration
         */
        EVALUATOR_INCORRECT_TYPE_OF_ARGUMENTS_TO_FUNC_CALL() {
            void var1_1;
        }
    }

    @Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u00c6\u0001\u0018\u00002\u00020\u0001J\u0012\u0010\u0002\u001a\u00020\u00032\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005H\u0016\u00a8\u0006\u0006"}, d2={"Lorg/partiql/lang/errors/ErrorCode$EVALUATOR_INCORRECT_TYPE_OF_ARGUMENTS_TO_PROCEDURE_CALL;", "Lorg/partiql/lang/errors/ErrorCode;", "getErrorMessage", "", "errorContext", "Lorg/partiql/lang/errors/PropertyValueMap;", "lang"})
    static final class EVALUATOR_INCORRECT_TYPE_OF_ARGUMENTS_TO_PROCEDURE_CALL
    extends ErrorCode {
        @Override
        @NotNull
        public String getErrorMessage(@Nullable PropertyValueMap errorContext) {
            StringBuilder stringBuilder = new StringBuilder().append("Invalid argument types for ");
            Object object = errorContext;
            if (object == null || (object = ((PropertyValueMap)object).get(Property.FUNCTION_NAME)) == null) {
                object = "<UNKNOWN>";
            }
            StringBuilder stringBuilder2 = stringBuilder.append(object).append(", ").append("expected: ");
            Object object2 = errorContext;
            if (object2 == null || (object2 = ((PropertyValueMap)object2).get(Property.EXPECTED_ARGUMENT_TYPES)) == null) {
                object2 = "<UNKNOWN>";
            }
            StringBuilder stringBuilder3 = stringBuilder2.append(object2).append(' ').append("got: ");
            Object object3 = errorContext;
            if (object3 == null || (object3 = ((PropertyValueMap)object3).get(Property.ACTUAL_ARGUMENT_TYPES)) == null) {
                object3 = "<UNKNOWN>";
            }
            return stringBuilder3.append(object3).toString();
        }

        /*
         * WARNING - void declaration
         */
        EVALUATOR_INCORRECT_TYPE_OF_ARGUMENTS_TO_PROCEDURE_CALL() {
            void var1_1;
        }
    }

    @Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u00c6\u0001\u0018\u00002\u00020\u0001J\u0012\u0010\u0002\u001a\u00020\u00032\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005H\u0016\u00a8\u0006\u0006"}, d2={"Lorg/partiql/lang/errors/ErrorCode$EVALUATOR_CONCAT_FAILED_DUE_TO_INCOMPATIBLE_TYPE;", "Lorg/partiql/lang/errors/ErrorCode;", "getErrorMessage", "", "errorContext", "Lorg/partiql/lang/errors/PropertyValueMap;", "lang"})
    static final class EVALUATOR_CONCAT_FAILED_DUE_TO_INCOMPATIBLE_TYPE
    extends ErrorCode {
        /*
         * WARNING - void declaration
         */
        @Override
        @NotNull
        public String getErrorMessage(@Nullable PropertyValueMap errorContext) {
            void $this$filterTo$iv$iv;
            void $this$filter$iv;
            ExprValueType[] exprValueTypeArray = ExprValueType.values();
            StringBuilder stringBuilder = new StringBuilder().append("Incorrect type of arguments for operator '||', ").append("expected one of ");
            boolean $i$f$filter = false;
            void var4_5 = $this$filter$iv;
            Collection destination$iv$iv = new ArrayList();
            boolean $i$f$filterTo = false;
            void var7_8 = $this$filterTo$iv$iv;
            int n = ((void)var7_8).length;
            for (int i = 0; i < n; ++i) {
                void element$iv$iv;
                void it = element$iv$iv = var7_8[i];
                boolean bl = false;
                if (!it.isText()) continue;
                destination$iv$iv.add(element$iv$iv);
            }
            List list = (List)destination$iv$iv;
            PropertyValueMap propertyValueMap = errorContext;
            return stringBuilder.append(list).append(' ').append("got ").append(propertyValueMap != null ? propertyValueMap.get(Property.ACTUAL_ARGUMENT_TYPES) : null).toString();
        }

        /*
         * WARNING - void declaration
         */
        EVALUATOR_CONCAT_FAILED_DUE_TO_INCOMPATIBLE_TYPE() {
            void var1_1;
        }
    }

    @Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u00c6\u0001\u0018\u00002\u00020\u0001J\u0012\u0010\u0002\u001a\u00020\u00032\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005H\u0016\u00a8\u0006\u0006"}, d2={"Lorg/partiql/lang/errors/ErrorCode$EVALUATOR_INVALID_TIMESTAMP_FORMAT_PATTERN;", "Lorg/partiql/lang/errors/ErrorCode;", "getErrorMessage", "", "errorContext", "Lorg/partiql/lang/errors/PropertyValueMap;", "lang"})
    static final class EVALUATOR_INVALID_TIMESTAMP_FORMAT_PATTERN
    extends ErrorCode {
        @Override
        @NotNull
        public String getErrorMessage(@Nullable PropertyValueMap errorContext) {
            PropertyValueMap propertyValueMap = errorContext;
            return "Invalid timestamp format pattern: '" + (propertyValueMap != null ? propertyValueMap.get(Property.TIMESTAMP_FORMAT_PATTERN) : null) + "'.";
        }

        /*
         * WARNING - void declaration
         */
        EVALUATOR_INVALID_TIMESTAMP_FORMAT_PATTERN() {
            void var1_1;
        }
    }

    @Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u00c6\u0001\u0018\u00002\u00020\u0001J\u0012\u0010\u0002\u001a\u00020\u00032\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005H\u0016\u00a8\u0006\u0006"}, d2={"Lorg/partiql/lang/errors/ErrorCode$EVALUATOR_INVALID_TIMESTAMP_FORMAT_PATTERN_TOKEN;", "Lorg/partiql/lang/errors/ErrorCode;", "getErrorMessage", "", "errorContext", "Lorg/partiql/lang/errors/PropertyValueMap;", "lang"})
    static final class EVALUATOR_INVALID_TIMESTAMP_FORMAT_PATTERN_TOKEN
    extends ErrorCode {
        @Override
        @NotNull
        public String getErrorMessage(@Nullable PropertyValueMap errorContext) {
            PropertyValueMap propertyValueMap = errorContext;
            return "Timestamp format pattern contains invalid token: '" + (propertyValueMap != null ? propertyValueMap.get(Property.TIMESTAMP_FORMAT_PATTERN) : null) + "'.";
        }

        /*
         * WARNING - void declaration
         */
        EVALUATOR_INVALID_TIMESTAMP_FORMAT_PATTERN_TOKEN() {
            void var1_1;
        }
    }

    @Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u00c6\u0001\u0018\u00002\u00020\u0001J\u0012\u0010\u0002\u001a\u00020\u00032\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005H\u0016\u00a8\u0006\u0006"}, d2={"Lorg/partiql/lang/errors/ErrorCode$EVALUATOR_INVALID_TIMESTAMP_FORMAT_PATTERN_SYMBOL;", "Lorg/partiql/lang/errors/ErrorCode;", "getErrorMessage", "", "errorContext", "Lorg/partiql/lang/errors/PropertyValueMap;", "lang"})
    static final class EVALUATOR_INVALID_TIMESTAMP_FORMAT_PATTERN_SYMBOL
    extends ErrorCode {
        @Override
        @NotNull
        public String getErrorMessage(@Nullable PropertyValueMap errorContext) {
            PropertyValueMap propertyValueMap = errorContext;
            return "Timestamp format pattern contains invalid symbol: '" + (propertyValueMap != null ? propertyValueMap.get(Property.TIMESTAMP_FORMAT_PATTERN) : null) + "'.";
        }

        /*
         * WARNING - void declaration
         */
        EVALUATOR_INVALID_TIMESTAMP_FORMAT_PATTERN_SYMBOL() {
            void var1_1;
        }
    }

    @Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u00c6\u0001\u0018\u00002\u00020\u0001J\u0012\u0010\u0002\u001a\u00020\u00032\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005H\u0016\u00a8\u0006\u0006"}, d2={"Lorg/partiql/lang/errors/ErrorCode$EVALUATOR_UNTERMINATED_TIMESTAMP_FORMAT_PATTERN_TOKEN;", "Lorg/partiql/lang/errors/ErrorCode;", "getErrorMessage", "", "errorContext", "Lorg/partiql/lang/errors/PropertyValueMap;", "lang"})
    static final class EVALUATOR_UNTERMINATED_TIMESTAMP_FORMAT_PATTERN_TOKEN
    extends ErrorCode {
        @Override
        @NotNull
        public String getErrorMessage(@Nullable PropertyValueMap errorContext) {
            PropertyValueMap propertyValueMap = errorContext;
            return "Timestamp format pattern contains unterminated token: '" + (propertyValueMap != null ? propertyValueMap.get(Property.TIMESTAMP_FORMAT_PATTERN) : null) + "'.";
        }

        /*
         * WARNING - void declaration
         */
        EVALUATOR_UNTERMINATED_TIMESTAMP_FORMAT_PATTERN_TOKEN() {
            void var1_1;
        }
    }

    @Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u00c6\u0001\u0018\u00002\u00020\u0001J\u0012\u0010\u0002\u001a\u00020\u00032\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005H\u0016\u00a8\u0006\u0006"}, d2={"Lorg/partiql/lang/errors/ErrorCode$EVALUATOR_INCOMPLETE_TIMESTAMP_FORMAT_PATTERN;", "Lorg/partiql/lang/errors/ErrorCode;", "getErrorMessage", "", "errorContext", "Lorg/partiql/lang/errors/PropertyValueMap;", "lang"})
    static final class EVALUATOR_INCOMPLETE_TIMESTAMP_FORMAT_PATTERN
    extends ErrorCode {
        @Override
        @NotNull
        public String getErrorMessage(@Nullable PropertyValueMap errorContext) {
            PropertyValueMap propertyValueMap = errorContext;
            PropertyValueMap propertyValueMap2 = errorContext;
            return "Timestamp format pattern '" + (propertyValueMap != null ? propertyValueMap.get(Property.TIMESTAMP_FORMAT_PATTERN) : null) + "' " + "requires additional fields '" + (propertyValueMap2 != null ? propertyValueMap2.get(Property.TIMESTAMP_FORMAT_PATTERN_FIELDS) : null) + "'.";
        }

        /*
         * WARNING - void declaration
         */
        EVALUATOR_INCOMPLETE_TIMESTAMP_FORMAT_PATTERN() {
            void var1_1;
        }
    }

    @Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u00c6\u0001\u0018\u00002\u00020\u0001J\u0012\u0010\u0002\u001a\u00020\u00032\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005H\u0016\u00a8\u0006\u0006"}, d2={"Lorg/partiql/lang/errors/ErrorCode$EVALUATOR_TIMESTAMP_FORMAT_PATTERN_DUPLICATE_FIELDS;", "Lorg/partiql/lang/errors/ErrorCode;", "getErrorMessage", "", "errorContext", "Lorg/partiql/lang/errors/PropertyValueMap;", "lang"})
    static final class EVALUATOR_TIMESTAMP_FORMAT_PATTERN_DUPLICATE_FIELDS
    extends ErrorCode {
        @Override
        @NotNull
        public String getErrorMessage(@Nullable PropertyValueMap errorContext) {
            PropertyValueMap propertyValueMap = errorContext;
            PropertyValueMap propertyValueMap2 = errorContext;
            return "The format pattern '" + (propertyValueMap != null ? propertyValueMap.get(Property.TIMESTAMP_FORMAT_PATTERN) : null) + "' contains multiple format " + "specifiers representing the timestamp field '" + (propertyValueMap2 != null ? propertyValueMap2.get(Property.TIMESTAMP_FORMAT_PATTERN_FIELDS) : null) + "'.";
        }

        /*
         * WARNING - void declaration
         */
        EVALUATOR_TIMESTAMP_FORMAT_PATTERN_DUPLICATE_FIELDS() {
            void var1_1;
        }
    }

    @Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u00c6\u0001\u0018\u00002\u00020\u0001J\u0012\u0010\u0002\u001a\u00020\u00032\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005H\u0016\u00a8\u0006\u0006"}, d2={"Lorg/partiql/lang/errors/ErrorCode$EVALUATOR_TIMESTAMP_FORMAT_PATTERN_HOUR_CLOCK_AM_PM_MISMATCH;", "Lorg/partiql/lang/errors/ErrorCode;", "getErrorMessage", "", "errorContext", "Lorg/partiql/lang/errors/PropertyValueMap;", "lang"})
    static final class EVALUATOR_TIMESTAMP_FORMAT_PATTERN_HOUR_CLOCK_AM_PM_MISMATCH
    extends ErrorCode {
        @Override
        @NotNull
        public String getErrorMessage(@Nullable PropertyValueMap errorContext) {
            PropertyValueMap propertyValueMap = errorContext;
            return "The format pattern '" + (propertyValueMap != null ? propertyValueMap.get(Property.TIMESTAMP_FORMAT_PATTERN) : null) + "' contains a 12-hour hour of " + "day format symbol but doesn't also contain an AM/PM field, or it contains a 24-hour hour of day format " + "specifier and contains an AM/PM field.";
        }

        /*
         * WARNING - void declaration
         */
        EVALUATOR_TIMESTAMP_FORMAT_PATTERN_HOUR_CLOCK_AM_PM_MISMATCH() {
            void var1_1;
        }
    }

    @Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u00c6\u0001\u0018\u00002\u00020\u0001J\u0012\u0010\u0002\u001a\u00020\u00032\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005H\u0016\u00a8\u0006\u0006"}, d2={"Lorg/partiql/lang/errors/ErrorCode$EVALUATOR_INVALID_TIMESTAMP_FORMAT_PATTERN_SYMBOL_FOR_PARSING;", "Lorg/partiql/lang/errors/ErrorCode;", "getErrorMessage", "", "errorContext", "Lorg/partiql/lang/errors/PropertyValueMap;", "lang"})
    static final class EVALUATOR_INVALID_TIMESTAMP_FORMAT_PATTERN_SYMBOL_FOR_PARSING
    extends ErrorCode {
        @Override
        @NotNull
        public String getErrorMessage(@Nullable PropertyValueMap errorContext) {
            PropertyValueMap propertyValueMap = errorContext;
            return "The format pattern '" + (propertyValueMap != null ? propertyValueMap.get(Property.TIMESTAMP_FORMAT_PATTERN) : null) + "' contains a valid format " + "symbol that cannot be applied to timestamp parsing.";
        }

        /*
         * WARNING - void declaration
         */
        EVALUATOR_INVALID_TIMESTAMP_FORMAT_PATTERN_SYMBOL_FOR_PARSING() {
            void var1_1;
        }
    }

    @Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u00c6\u0001\u0018\u00002\u00020\u0001J\u0012\u0010\u0002\u001a\u00020\u00032\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005H\u0016\u00a8\u0006\u0006"}, d2={"Lorg/partiql/lang/errors/ErrorCode$EVALUATOR_AMBIGUOUS_BINDING;", "Lorg/partiql/lang/errors/ErrorCode;", "getErrorMessage", "", "errorContext", "Lorg/partiql/lang/errors/PropertyValueMap;", "lang"})
    static final class EVALUATOR_AMBIGUOUS_BINDING
    extends ErrorCode {
        @Override
        @NotNull
        public String getErrorMessage(@Nullable PropertyValueMap errorContext) {
            PropertyValueMap propertyValueMap = errorContext;
            return "Binding name was '" + (propertyValueMap != null ? propertyValueMap.get(Property.BINDING_NAME) : null) + '\'';
        }

        /*
         * WARNING - void declaration
         */
        EVALUATOR_AMBIGUOUS_BINDING() {
            void var1_1;
        }
    }

    @Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u00c6\u0001\u0018\u00002\u00020\u0001J\u0012\u0010\u0002\u001a\u00020\u00032\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005H\u0016\u00a8\u0006\u0006"}, d2={"Lorg/partiql/lang/errors/ErrorCode$EVALUATOR_LIKE_INVALID_INPUTS;", "Lorg/partiql/lang/errors/ErrorCode;", "getErrorMessage", "", "errorContext", "Lorg/partiql/lang/errors/PropertyValueMap;", "lang"})
    static final class EVALUATOR_LIKE_INVALID_INPUTS
    extends ErrorCode {
        @Override
        @NotNull
        public String getErrorMessage(@Nullable PropertyValueMap errorContext) {
            StringBuilder stringBuilder = new StringBuilder().append("Given: ").append("value = ");
            Object object = errorContext;
            if (object == null || (object = ((PropertyValueMap)object).get(Property.LIKE_VALUE)) == null || (object = ((PropertyValue)object).stringValue()) == null) {
                object = "<UNKNOWN>";
            }
            StringBuilder stringBuilder2 = stringBuilder.append((String)object).append(", ").append("pattern =  ");
            Object object2 = errorContext;
            if (object2 == null || (object2 = ((PropertyValueMap)object2).get(Property.LIKE_PATTERN)) == null || (object2 = ((PropertyValue)object2).stringValue()) == null) {
                object2 = "<UNKNOWN>";
            }
            StringBuilder stringBuilder3 = stringBuilder2.append((String)object2).append(", ").append("escape char = ");
            Object object3 = errorContext;
            if (object3 == null || (object3 = ((PropertyValueMap)object3).get(Property.LIKE_ESCAPE)) == null || (object3 = ((PropertyValue)object3).stringValue()) == null) {
                object3 = "none given";
            }
            return stringBuilder3.append((String)object3).toString();
        }

        /*
         * WARNING - void declaration
         */
        EVALUATOR_LIKE_INVALID_INPUTS() {
            void var1_1;
        }
    }

    @Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u00c6\u0001\u0018\u00002\u00020\u0001J\u0012\u0010\u0002\u001a\u00020\u00032\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005H\u0016\u00a8\u0006\u0006"}, d2={"Lorg/partiql/lang/errors/ErrorCode$EVALUATOR_LIKE_PATTERN_INVALID_ESCAPE_SEQUENCE;", "Lorg/partiql/lang/errors/ErrorCode;", "getErrorMessage", "", "errorContext", "Lorg/partiql/lang/errors/PropertyValueMap;", "lang"})
    static final class EVALUATOR_LIKE_PATTERN_INVALID_ESCAPE_SEQUENCE
    extends ErrorCode {
        @Override
        @NotNull
        public String getErrorMessage(@Nullable PropertyValueMap errorContext) {
            StringBuilder stringBuilder = new StringBuilder().append("Given: ").append("pattern =  ");
            Object object = errorContext;
            if (object == null || (object = ((PropertyValueMap)object).get(Property.LIKE_PATTERN)) == null || (object = ((PropertyValue)object).stringValue()) == null) {
                object = "<UNKNOWN>";
            }
            StringBuilder stringBuilder2 = stringBuilder.append((String)object).append(", ").append("escape char = ");
            Object object2 = errorContext;
            if (object2 == null || (object2 = ((PropertyValueMap)object2).get(Property.LIKE_ESCAPE)) == null || (object2 = ((PropertyValue)object2).stringValue()) == null) {
                object2 = "none given";
            }
            return stringBuilder2.append((String)object2).toString();
        }

        /*
         * WARNING - void declaration
         */
        EVALUATOR_LIKE_PATTERN_INVALID_ESCAPE_SEQUENCE() {
            void var1_1;
        }
    }

    @Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u00c6\u0001\u0018\u00002\u00020\u0001J\u0012\u0010\u0002\u001a\u00020\u00032\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005H\u0016\u00a8\u0006\u0006"}, d2={"Lorg/partiql/lang/errors/ErrorCode$EVALUATOR_NON_INT_LIMIT_VALUE;", "Lorg/partiql/lang/errors/ErrorCode;", "getErrorMessage", "", "errorContext", "Lorg/partiql/lang/errors/PropertyValueMap;", "lang"})
    static final class EVALUATOR_NON_INT_LIMIT_VALUE
    extends ErrorCode {
        @Override
        @NotNull
        public String getErrorMessage(@Nullable PropertyValueMap errorContext) {
            return "LIMIT value must be an integer but found " + ErrorCodeKt.access$getProperty(errorContext, Property.ACTUAL_TYPE) + '}';
        }

        /*
         * WARNING - void declaration
         */
        EVALUATOR_NON_INT_LIMIT_VALUE() {
            void var1_1;
        }
    }

    @Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u00c6\u0001\u0018\u00002\u00020\u0001J\u0012\u0010\u0002\u001a\u00020\u00032\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005H\u0016\u00a8\u0006\u0006"}, d2={"Lorg/partiql/lang/errors/ErrorCode$SEMANTIC_ILLEGAL_GLOBAL_VARIABLE_ACCESS;", "Lorg/partiql/lang/errors/ErrorCode;", "getErrorMessage", "", "errorContext", "Lorg/partiql/lang/errors/PropertyValueMap;", "lang"})
    static final class SEMANTIC_ILLEGAL_GLOBAL_VARIABLE_ACCESS
    extends ErrorCode {
        @Override
        @NotNull
        public String getErrorMessage(@Nullable PropertyValueMap errorContext) {
            return "Global variable access is illegal in this context, variable name: '" + ErrorCodeKt.access$getProperty(errorContext, Property.BINDING_NAME) + '\'';
        }

        /*
         * WARNING - void declaration
         */
        SEMANTIC_ILLEGAL_GLOBAL_VARIABLE_ACCESS() {
            void var1_1;
        }
    }

    @Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u00c6\u0001\u0018\u00002\u00020\u0001J\u0012\u0010\u0002\u001a\u00020\u00032\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005H\u0016\u00a8\u0006\u0006"}, d2={"Lorg/partiql/lang/errors/ErrorCode$SEMANTIC_UNBOUND_BINDING;", "Lorg/partiql/lang/errors/ErrorCode;", "getErrorMessage", "", "errorContext", "Lorg/partiql/lang/errors/PropertyValueMap;", "lang"})
    static final class SEMANTIC_UNBOUND_BINDING
    extends ErrorCode {
        @Override
        @NotNull
        public String getErrorMessage(@Nullable PropertyValueMap errorContext) {
            return "No such variable named '" + ErrorCodeKt.access$getProperty(errorContext, Property.BINDING_NAME) + '\'';
        }

        /*
         * WARNING - void declaration
         */
        SEMANTIC_UNBOUND_BINDING() {
            void var1_1;
        }
    }

    @Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u00c6\u0001\u0018\u00002\u00020\u0001J\u0012\u0010\u0002\u001a\u00020\u00032\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005H\u0016\u00a8\u0006\u0006"}, d2={"Lorg/partiql/lang/errors/ErrorCode$SEMANTIC_AMBIGUOUS_BINDING;", "Lorg/partiql/lang/errors/ErrorCode;", "getErrorMessage", "", "errorContext", "Lorg/partiql/lang/errors/PropertyValueMap;", "lang"})
    static final class SEMANTIC_AMBIGUOUS_BINDING
    extends ErrorCode {
        @Override
        @NotNull
        public String getErrorMessage(@Nullable PropertyValueMap errorContext) {
            return "A variable named '" + ErrorCodeKt.access$getProperty(errorContext, Property.BINDING_NAME) + "' was already defined in this scope";
        }

        /*
         * WARNING - void declaration
         */
        SEMANTIC_AMBIGUOUS_BINDING() {
            void var1_1;
        }
    }

    @Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u00c6\u0001\u0018\u00002\u00020\u0001J\u0012\u0010\u0002\u001a\u00020\u00032\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005H\u0016\u00a8\u0006\u0006"}, d2={"Lorg/partiql/lang/errors/ErrorCode$SEMANTIC_INCORRECT_NODE_ARITY;", "Lorg/partiql/lang/errors/ErrorCode;", "getErrorMessage", "", "errorContext", "Lorg/partiql/lang/errors/PropertyValueMap;", "lang"})
    static final class SEMANTIC_INCORRECT_NODE_ARITY
    extends ErrorCode {
        @Override
        @NotNull
        public String getErrorMessage(@Nullable PropertyValueMap errorContext) {
            return "Incorrect number of arguments supplied to `" + ErrorCodeKt.access$getProperty(errorContext, Property.FUNCTION_NAME) + "`. " + "Min = " + ErrorCodeKt.access$getProperty(errorContext, Property.EXPECTED_ARITY_MIN) + ", max = " + ErrorCodeKt.access$getProperty(errorContext, Property.EXPECTED_ARITY_MAX) + ' ' + "Actual = " + ErrorCodeKt.access$getProperty(errorContext, Property.ACTUAL_ARITY);
        }

        /*
         * WARNING - void declaration
         */
        SEMANTIC_INCORRECT_NODE_ARITY() {
            void var1_1;
        }
    }

    @Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u00c6\u0001\u0018\u00002\u00020\u0001J\u0012\u0010\u0002\u001a\u00020\u00032\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005H\u0016\u00a8\u0006\u0006"}, d2={"Lorg/partiql/lang/errors/ErrorCode$UNIMPLEMENTED_FEATURE;", "Lorg/partiql/lang/errors/ErrorCode;", "getErrorMessage", "", "errorContext", "Lorg/partiql/lang/errors/PropertyValueMap;", "lang"})
    static final class UNIMPLEMENTED_FEATURE
    extends ErrorCode {
        @Override
        @NotNull
        public String getErrorMessage(@Nullable PropertyValueMap errorContext) {
            StringBuilder stringBuilder = new StringBuilder().append("Feature '");
            Object object = errorContext;
            if (object == null || (object = ((PropertyValueMap)object).get(Property.FEATURE_NAME)) == null || (object = ((PropertyValue)object).stringValue()) == null) {
                object = "<UNKNOWN>";
            }
            return stringBuilder.append((String)object).append("' not implemented yet").toString();
        }

        /*
         * WARNING - void declaration
         */
        UNIMPLEMENTED_FEATURE() {
            void var1_1;
        }
    }
}

