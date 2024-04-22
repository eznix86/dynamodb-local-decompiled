/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package ddb.partiql.shared.exceptions;

public final class ValidationExceptionMessages {
    public static final String SELECT_PROJECTION_DOES_NOT_SUPPORT_VALUE_OR_PIVOT = "Select projection doesn't support value or pivot keywords";
    public static final String CHANGING_ITEM_HIERARCHY_WHILE_READING_NOT_SUPPORTED = "Changing item's hierarchy while reading isn't supported";
    public static final String NEGATIVE_ZERO_DECIMAL_NOT_ALLOWED = "Negative zero decimal is not supported under key %s";
    public static final String ION_VALUE_CONTAINS_TYPED_NULL = "Value contains a typed null";
    public static final String INCOMPLETE_KEY_IN_WHERE_CLAUSE = "Where clause does not contain a mandatory equality on all key attributes";
    public static final String UNEXPECTED_PATH_COMPONENT = "Unexpected path component";
    public static final String OPERATION_NOT_SUPPORTED_ON_INDEX = "This operation is not supported on an index";
    public static final String UNEXPECTED_FROM_SOURCE = "Unexpected from source";
    public static final String CONSISTENT_READ_ON_GSI = "Strongly consistent read is not supported on Global Secondary Indexes";
    public static final String PATH_COMPONENT_CANNOT_BE_EMPTY = "Path component cannot be an empty string";
    public static final String CONT_TOKEN_SID_MISMATCH = "Given NextToken is invalid for this account";
    public static final String CONT_TOKEN_HASH_MISMATCH = "NextToken does not match request";
    public static final String CONT_TOKEN_EXPIRED = "Given NextToken has already expired";
    public static final String INVALID_NEXT_TOKEN = "Invalid NextToken";
    public static final String ALIAS_UNSUPPORTED = "Aliasing is not supported";
    public static final String UNSUPPORTED_UPDATE_ACTION = "Only SET and REMOVE are supported in update expressions at this time";
    public static final String AT_UNSUPPORTED = "AT is not supported in insert statements.";
    public static final String UNSUPPORTED_ITEM_SYNTAX = "Unsupported format in item expression. Please use struct format {...} to express item values for insert statement.";
    public static final String EMPTY_BAG = "Empty bags are not supported";
    public static final String SELECT_MULTIPLE_FROM = "Only select from a single table or index is supported.";
    public static final String DML_INVALID_FROM = "FROM clause may only contain a single table name in data manipulation statements";
    public static final String OP_LIMIT_EXCEEDED = "Too many decomposed read operations for a given query.";
    public static final String INVALID_CONFLICT_ACTION = "Invalid conflict action. Only \"DO NOTHING\" action is supported.";
    public static final String UNSUPPORTED_MULTIPLE_RETURNING_ELEMENTS = "Invalid syntax with multiple returning elements in RETURNING clause";
    public static final String UNSUPPORTED_RETURNING_SYNTAX = "Expected '*' after ( MODIFIED | ALL ) ( NEW | OLD ) in RETURNING clause";
    public static final String INVALID_IN_OPERATOR = "IN operator must have a left hand argument of type Variable Reference and right hand argument of type Seq with at least one member";
    public static final String UNSUPPORTED_CONDITION_CHECK_CLAUSE_FOR_NON_TX_REQUEST = "EXISTS can only be used in ExecuteTransaction write requests.";
    public static final String LITERAL_MISSING_INVALID = "MISSING must be compared using IS operator";
    public static final String IS_OPERATOR_INCORRECT_OPERAND = "The left hand side argument to IS must be a Variable Reference, Literal, or Path";
    public static final String IS_IN_UPDATE = "IS operator is not supported in update expressions";
    public static final String INSERT_OP_NOT_SUPPORTED = "Unsupported operation: Inserting multiple items in a single statement is not supported, use \"INSERT INTO tableName VALUE item\" instead";
    public static final String UNSUPPORTED_FROM_WHERE_IN_INSERT = "Unsupported FROM ... WHERE ... clause in INSERT statement.";
    public static final String DUPLICATE_ITEM_IN_TABLE = "Duplicate primary key exists in table";
    public static final String NOT_OPERATOR_INCORRECT_OPERAND = "The argument to NOT must be a Type or Expression";
    public static final String AND_OR_OPERATOR_INCORRECT_OPERAND = "The arguments to AND/OR must be Expressions";
    public static final String UNSUPPORTED_WHERE_CLAUSE = "WHERE clause must contain an expression that resolves to a boolean";
    public static final String ION_VALUE_CONTAINS_NULL = "Value contains null under %s";
    public static final String UNKNOWN_KEY_IN_ION_VALUE = "Unknown key present under %s";
    public static final String MULTIPLE_CONDITIONS_ON_SAME_KEY = "Multiple conditions on same key %s. Only single item Update/Insert/Delete are supported";
    public static final String INVALID_CONDITIONS_ON_HASH_KEY = "Only single equality condition should be present on hash key %s";
    public static final String UNPIVOT_PATH_COMPONENTS_NOT_SUPPORTED = "Unpivot path component like a.*.b are not supported";
    public static final String WILDCARD_PATH_COMPONENTS_NOT_SUPPORTED = "Wildcard path component like a[*].b are not supported";
    public static final String LIST_INDEX_OUTSIDE_RANGE = "List index is not within the allowable range; index: [%s]";
    public static final String PATH_CANNOT_START_WITH_LIST_INDEX = "A path cannot start with a list index";
    public static final String KEY_ATTRIBUTE_NESTED_PATH_FOUND = "Key attributes must be scalars; list random access '[]' and map lookup '.' are not allowed: Key %s";
    public static final String KEY_ATTRIBUTE_ABSENT = "Key attribute should be present in the item: Key %s";
    public static final String SCHEMA_KEY_MISMATCH = "Key attribute's data type should match its data type in table's schema: Key %s";
    public static final String SET_OPERATION_INCORRECT_NUM_ARGS = "SET_%s must have exactly two arguments";
    public static final String SET_OPERATION_INCORRECT_FIRST_ARG = "The first argument to SET_%s must equal the assignment value";
    public static final String SET_OPERATION_INCORRECT_SECOND_ARG = "The second argument to SET_%s must be a value with type SET";
    public static final String UNSUPPORTED_CLAUSE = "Unsupported clause: %s";
    public static final String UNSUPPORTED_OP = "Unsupported operation: %s";
    public static final String UNRECOGNIZED_FUNCTION = "Unrecognized function: %s";
    public static final String UNRECOGNIZED_TOKEN = "Unsupported token in expression: %s";
    public static final String UNSUPPORTED_CONDITION_OP = "Unsupported operator in Condition Expression. Operator: %s";
    public static final String UNSUPPORTED_UPDATE_OP = "Unsupported operator in Update Expression. Operator: %s";
    public static final String UNRECOGNIZED_ASSIGNMENT_TARGET = "Unrecognized assignment target: %s";
    public static final String UNSUPPORTED_OVERLAP_CONDITION = "Overlapping conditions with range keys are not supported in where clause";
    public static final String UNSUPPORTED_DATA_TYPE_IN_BAG = "Unsupported data type in Bag. DynamoDB only supports either numbers or strings in bags";
    public static final String DUPLICATE_NAMES_IN_SELECT_CLAUSE = "Duplicate identifiers in select clause: %s";
    public static final String UNSUPPORTED_DATA_TYPE_IS = "Unsupported data type for IS operator: %s. Supported data types are [MISSING, NULL, BOOLEAN, BLOB, STRING, NUMERIC, STRUCT, TUPLE, LIST]";
    public static final String INVALID_RV_INSERT = "RETURNING is not supported in INSERT";
    public static final String INVALID_RV_DELETE = "Invalid returning clause: RETURNING %s. Only RETURNING ALL OLD * is allowed in DELETE statements.";
    public static final String DUPLICATE_KEYS_IN_TUPLE = "Duplicate keys in tuple. Key: %s under %s";
    public static final String UNSUPPORTED_KEY_TYPE_IN_STRUCT = "Tuple keys must be of type String. Actual type: %s under %s";
    public static final String UNSUPPORTED_DATA_TYPE = "Unsupported data type: %s under key %s";
    public static final String ION_LITERAL_UNSUPPORTED = "Ion Literals are not supported: %s under key %s";
    public static final String UNEXPECTED_DATA_TYPE_IN_NUMBER_SET = "Unexpected data type %s in Number Set under key %s";
    public static final String UNEXPECTED_DATA_TYPE_IN_STRING_SET = "Unexpected data type %s in String Set under key %s";
    public static final String UNEXPECTED_DATA_TYPE_IN_BINARY_SET = "Unexpected data type %s in Binary Set under key %s";
    public static final String UNEXPECTED_DATA_TYPE_IN_DECIMAL_SET = "Unexpected data type %s in Decimal Set under key %s";
    public static final String UNEXPECTED_DATA_TYPE_IN_DOUBLE_SET = "Unexpected data type %s in Double Set under key %s";
    public static final String UNEXPECTED_DATA_TYPE_IN_INT_SET = "Unexpected data type %s in Int Set under key %s";
    public static final String INVALID_CONDITIONS_ON_RANGE_KEY = "Condition on range key: %s, operator: %s is not valid condition";
    public static final String INVALID_KEY_TYPE = "Key value must be of type S, N, or B. Key name: %s, Key type: %s";
    public static final String PARAMETERS_IN_STATEMENT_AND_PARAMETERS_IN_REQUEST = "Number of parameters in request and statement don't match.";
    public static final String STAR_AND_OTHER_ATTRIBUTES = "Other attributes should not be present in the projection when '*' is used.";
    public static final String INVALID_SOURCE_WITH_DOT = "Invalid path dot component in this operation, expecting either an IDENTIFIER or STAR.";
    public static final String MULTIPLE_OPERATIONS_IN_ONE_STATEMENT = "Only one operation is allowed in a statement.";
    public static final String INVALID_STATEMENT = "Statement wasn't well formed, can't be processed: %s";
    public static final String INVALID_TABLE_NAME = "Table name should be within the length [3, 255] and only contain 'a-z', 'A-Z', '0-9', '-', '_', '.'.";
    public static final String INVALID_SOURCE_AS_LIST = "Invalid list expression in source.";
    public static final String INVALID_PATH_ELEMENTS = "A path may contain at most 2 components in the FROM clause";
    public static final String INVALID_KEYS_FOR_TGI = "Select statements within ExecuteTransaction must specify the primary key in the where clause.";
    public static final String INVALID_INDICES_FOR_TGI = "Reads on indices are not supported within transactions.";
    public static final String INVALID_KEYS_FOR_TWI_CONDITION_CHECK = "EXISTS() must contain a single item read with additional condition";
    public static final String INVALID_INDICES_FOR_TWI_CONDITION_CHECK = "Operations on indices are not supported in transactions";
    public static final String INVALID_NARY_IN_TRANSACTION = "Unsupported operation in transaction.";
    public static final String MISSING_WHERE_CLAUSE_FOR_ORDER_BY = "Must have WHERE clause in the statement when using ORDER BY clause.";
    public static final String INCOMPLETE_KEY_IN_WHERE_CLAUSE_FOR_ORDER_BY = "Must have at least one non-optional hash key condition in WHERE clause when using ORDER BY clause.";
    public static final String INVALID_ATTRIBUTES_IN_ORDER_BY = "Variable reference %s in ORDER BY clause must be part of the primary key";
    public static final String UNEXPECTED_ORDERING_KEY_EXPR = "Argument to ORDER BY must be of type VariableReference. Type: %s";
    public static final String MISSING_HASHKEY_WHEN_MULTIPLE_HASHKEY_CONDITIONS_IN_WHERE = "Must have hash key in ORDER BY clause when more than one hash key condition specified in WHERE clause.";
    public static final String GENERIC_VALIDATION_EXCEPTION_FOR_MULTIPLE_REQUESTS = "Validation failed in statements[%s]: %s";

    private ValidationExceptionMessages() {
    }
}

