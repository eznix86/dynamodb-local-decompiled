/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazonaws.services.dynamodbv2.local.shared.validate;

import com.amazonaws.services.dynamodbv2.local.shared.exceptions.LocalDBClientExceptionMessage;

public abstract class ErrorFactory {
    public final ErrorCode INVALID_PARAMETER_VALUE = new ValidationErrorCode("One or more parameter values were invalid");
    public final ErrorCode INVALID_NUMERIC_VALUE = new ValidationErrorCode("The parameter cannot be converted to a numeric value");
    public final ErrorCode EMPTY_ATTRIBUTE_NAME = new ValidationErrorCode("Empty attribute name");
    public final ErrorCode ATTRIBUTE_NAME_TOO_LARGE = new ValidationErrorCode("Attribute name is too large, must be less than 65536 bytes");
    public final ErrorCode EMPTY_ATTRIBUTE_VALUE = new ValidationErrorCode("Supplied AttributeValue is empty, must contain exactly one of the supported datatypes");
    public final ErrorCode MULTI_ATTRIBUTE_VALUE = new ValidationErrorCode("Supplied AttributeValue has more than one datatypes set, must contain exactly one of the supported datatypes");
    public final ErrorCode NULL_ATTRIBUTE_UPDATE_VALUE = new ValidationErrorCode("Supplied AttributeValueUpdate is empty, must contain one of the supported actions");
    public final ErrorCode RICMETRICS_INVALID = new ValidationErrorCode("ReturnItemCollectionMetrics can only be SIZE or NONE");
    public final ErrorCode ITEM_TOO_LARGE = new ValidationErrorCode("Item size has exceeded the maximum allowed size");
    public final ErrorCode ITEM_CONTAINS_NULL_ATTRVALUE = new ValidationErrorCode("The supplied Item contains a null AttributeValue");
    public final ErrorCode NULL_ITEM_KEY = new ValidationErrorCode("Item key cannot be null");
    public final ErrorCode DUPLICATE_ITEM_KEY = new ValidationErrorCode("Provided list of item keys contains duplicates");
    public final ErrorCode ITEM_UPD_TOO_LARGE = new ValidationErrorCode("Item size to update has exceeded the maximum allowed size");
    public final ErrorCode NUMBER_OVERFLOW = new ValidationErrorCode("Number overflow. Attempting to store a number with magnitude larger than supported range");
    public final ErrorCode NUMBER_UNDERFLOW = new ValidationErrorCode("Number underflow. Attempting to store a number with magnitude smaller than supported range");
    public final ErrorCode NUMBER_EXCEEDING_PRECISION = new ValidationErrorCode("Attempting to store more than 38 significant digits in a Number");
    public final ErrorCode TYPE_UPDATE_MISMATCH = new ValidationErrorCode("Type mismatch for attributes specified for update");
    public final ErrorCode UNSUPPORTED_FILTER_OPERATION = new ValidationErrorCode("The attempted filter operation is not supported for the provided type");
    public final ErrorCode UNSUPPORTED_EXPECTED_OPERATION = new ValidationErrorCode("The attempted expected check is not supported for the provided type");
    public final ErrorCode FILTER_VALUES_NOT_HAVING_SAME_TYPE = new ValidationErrorCode("The filter operation is being passed in values with different types");
    public final ErrorCode EXPECTED_VALUES_NOT_HAVING_SAME_TYPE = new ValidationErrorCode("The expected check operation is being passed in values with different types");
    public final ErrorCode LBOUND_OF_BETWEEN_BIGGER_THAN_UBOUND = new ValidationErrorCode("The BETWEEN condition was provided a range where the lower bound is greater than the upper bound");
    public final ErrorCode INVALID_FILTER_ARGUMENT_COUNT = new ValidationErrorCode("The attempted filter operation is not supported for the provided filter argument count");
    public final ErrorCode INVALID_EXPECTED_ARGUMENT_COUNT = new ValidationErrorCode("The attempted expected operation is not supported for the provided expected argument count");
    public final ErrorCode RESOURCE_IN_USE = new ResourceInUseErrorCode("Attempt to change a resource which is still in use");
    public final ErrorCode RESOURCE_NOT_FOUND = new ResourceNotFoundErrorCode("Requested resource not found");
    public final ErrorCode CONDITIONAL_CHECK_FAILED = new ConditionalCheckFailedErrorCode("The conditional request failed");
    public final ErrorCode OBTAINING_COUNT_AND_ATTRIBUTES = new ValidationErrorCode("Cannot specify the AttributesToGet when choosing to get only the Count");
    public final ErrorCode OBTAINING_COUNT_AND_PROJECTIONEXPRESSION = new ValidationErrorCode("Cannot specify the ProjectionExpression when choosing to get only the Count");
    public final ErrorCode INVALID_HASH_KEY_VALUE = new ValidationErrorCode("Only scalar types can be used as Hash Key values");
    public final ErrorCode INVALID_RANGE_KEY_VALUE = new ValidationErrorCode("Only scalar types can be used as Range Key values");
    public final ErrorCode INVALID_START_KEY = new ValidationErrorCode("The provided starting key is invalid");
    public final ErrorCode INVALID_START_KEY_HR = new ValidationErrorCode("The provided starting key must match the HASH,RANGE key schema");
    public final ErrorCode INVALID_START_KEY_EQUALITY = new ValidationErrorCode("The query can return at most one row and cannot be restarted");
    public final ErrorCode INVALID_START_KEY_HASH = new ValidationErrorCode("The provided starting key does not match the hash key value");
    public final ErrorCode START_KEY_OUT_OF_BOUNDS = new ValidationErrorCode("The provided starting key is outside query boundaries based on provided conditions");
    public final ErrorCode SCHEMA_KEY_MISMATCH = new ValidationErrorCode("The provided key element does not match the schema");
    public final ErrorCode RANGE_ON_HASH_KEY = new ValidationErrorCode("Attempting to perform a range scan on a hash key");
    public final ErrorCode QUERY_ON_NON_HASH_RANGE_SCHEMA = new ValidationErrorCode("Query can be performed only on a table with a HASH,RANGE key schema");
    public final ErrorCode GLOBAL_SECONDARY_INDEX_CONSISTENT_READ_NOT_SUPPORTED = new ValidationErrorCode("Consistent reads are not supported on global secondary indexes");
    public final ErrorCode UNSUPPORTED_QUERY_KEY_CONDITION_SEQUENCE = new ValidationErrorCode("Query key condition not supported");
    public final ErrorCode DUPLICATE_KEY_ELEMENT_NAME = new ValidationErrorCode("Both the Hash Key and the Range Key element in the KeySchema have the same name");
    public final ErrorCode KEY_ATTRIBUTE_NAME_TOO_LARGE = new ValidationErrorCode("Attribute name size has exceeded the maximum size");
    public final ErrorCode NON_INDEXABLE_CONDITION = new ValidationErrorCode("Attempted conditional constraint is not an indexable operation");
    public final ErrorCode DUPLICATE_TABLE_NAME = new ValidationErrorCode("Provided list of table names contains duplicates");
    public final ErrorCode SUBSCRIBER_LIMIT_EXCEEDED = new LimitExceededErrorCode("Subscriber limit exceeded");
    public final ErrorCode BATCH_GET_LIMIT_EXCEEDED = new ValidationErrorCode("Too many items requested for the BatchGetItem call");
    public final ErrorCode BATCH_GET_NULL_OR_EMPTY_KAS = new ValidationErrorCode("The list of keys in RequestItems for BatchGetItem is required");
    public final ErrorCode BATCH_GET_KAS_CONTAINS_NULL = new ValidationErrorCode("The list of keys in RequestItems for BatchGetItem may not contain null or empty values");
    public final ErrorCode BATCH_WRITE_LIMIT_EXCEEDED = new ValidationErrorCode("Too many items requested for the BatchWriteItem call");
    public final ErrorCode MISSING_TABLE_NAME = new ValidationErrorCode("The paramater 'TableName' is required but was not present in the request");
    public final ErrorCode INVALID_TABLE_NAME_LENGTH = new ValidationErrorCode("TableName must be at least 3 characters long and at most 255 characters long");
    public final ErrorCode INVALID_INDEX_NAME_LENGTH = new ValidationErrorCode("IndexName must be at least 3 characters long and at most 255 characters long");
    public final ErrorCode REQUEST_ITEMS_REQUIRED = new ValidationErrorCode("The requestItems parameter is required for BatchGetItem");
    public final ErrorCode BATCH_WRITE_REQUEST_ITEMS_REQUIRED = new ValidationErrorCode("The requestItems parameter is required for BatchWriteItem");
    public final ErrorCode INTERNAL_FAILURE = new InternalServerErrorCode("Internal server error");
    public final ErrorCode RANGE_KEY_ACCESS_RESTRICTED = new ValidationErrorCode("The requesting subscriber does not have permission to create a HASH,RANGE key schema");
    public final ErrorCode PROVISIONED_THROUGHPUT_EXCEEDED = new ProvisionedThroughputExceededErrorCode("The level of configured provisioned throughput for the table was exceeded. Consider increasing your provisioning level with the UpdateTable API");
    public final ErrorCode GSI_PROVISIONED_THROUGHPUT_EXCEEDED = new ProvisionedThroughputExceededErrorCode("The level of configured provisioned throughput for one or more global secondary indexes of the table was exceeded. Consider increasing your provisioning level for the under-provisioned global secondary indexes with the UpdateTable API");
    public final ErrorCode CONTROL_PLANE_THROTTLE = new ThrottlingErrorCode("The rate of control plane requests made by this account is too high");
    public final ErrorCode FILTER_CONDITION_CANNOT_BE_NULL = new ValidationErrorCode("The filter condition for an attribute cannot be null");
    public final ErrorCode BATCH_WRITE_REQUEST_NULL = new ValidationErrorCode("The batch write request list for a table cannot be null or empty");
    public final ErrorCode HASH_COLLISION = new ValidationErrorCode("Hash collision detected, primary key mismatch");
    public final ErrorCode KEY_SCHEMA_INVALID = new ValidationErrorCode("Invalid KeySchema");
    public final ErrorCode INVALID_INDEX = new ValidationErrorCode("The request index does not exist");
    public final ErrorCode COLLECTION_SIZE_EXCEEDED = new ItemCollectionSizeLimitExceededErrorCode("Collection size exceeded");
    public final ErrorCode MISSING_TOTALSEGMENTS = new ValidationErrorCode("The TotalSegments parameter is required but was not present in the request when Segment parameter is present");
    public final ErrorCode MISSING_SEGMENT = new ValidationErrorCode("The Segment parameter is required but was not present in the request when parameter TotalSegments is present");
    public final ErrorCode INVALID_SEGMENT = new ValidationErrorCode("The Segment parameter is zero-based and must be less than parameter TotalSegments");
    public final ErrorCode CONCURRENT_CONTROL_PLANE_OPS_EXCEEDED = new ValidationErrorCode("The total request size of all concurrent control plane operations has been exceeded");
    public final ErrorCode COND_OP_WITHOUT_FILTER_OR_EXPECTED = new ValidationErrorCode("ConditionalOperator cannot be used without Filter or Expected");
    public final ErrorCode COND_OP_WITH_ONE_ELEMENT = new ValidationErrorCode("ConditionalOperator can only be used when Filter or Expected has two or more elements");
    public final ErrorCode UNSUPPORTED_INPUT_PARAMETER = new ValidationErrorCode("Unsupported input parameter");
    public final ErrorCode QUERY_FILTER_CONTAINS_KEY_ATTRIBUTE = new ValidationErrorCode("QueryFilter can only contain non-primary key attributes");
    public final ErrorCode ITEM_NESTING_LEVELS_LIMIT_EXCEEDED = new ValidationErrorCode("Nesting Levels have exceeded supported limits");
    public final ErrorCode MISSING_KEY_CONDITIONS_AND_EXPRESSION = new ValidationErrorCode("Either the KeyConditions or KeyConditionExpression parameter must be specified in the request.");
    public final ErrorCode INVALID_KEY_CONDITIONS_SIZE = new ValidationErrorCode("Conditions can be of length 1 or 2 only");
    public final ErrorCode KEY_CONDITIONS_MISSING_KEY = new ValidationErrorCode("Query condition missed key schema element");
    public final ErrorCode INVALID_ACTION_TYPE = new ValidationErrorCode("Member must satisfy enum value set: [ADD, DELETE, PUT]");
    public final ErrorCode RV_COND_FAILURE_INVALID_TYPE = new ValidationErrorCode("ReturnValuesOnConditionCheckFailure value is invalid");
    public final ErrorCode INVALID_COMPARISON = new ValidationErrorCode("Comparison type does not exist in DynamoDB");
    public final ErrorCode CONDITION_EXPRESSION_EXPECTED_ERROR = new ValidationErrorCode("Expected attributes should be null when ConditionExpression is present");
    public final ErrorCode UPDATE_EXPRESSION_EXPECTED_ERROR = new ValidationErrorCode("Expected attributes should be null when UpdateExpression is present");
    public final ErrorCode PARAMETER_MAP_KEY_SIZE_EXCEEDED = new ValidationErrorCode("The expression attribute map contains a key that is too long");
    public final ErrorCode PROJECTION_EXPRESSION_ATTR_TO_GET_ERROR = new ValidationErrorCode("AttributesToGet should be null when ProjectionExpression is present");
    public final ErrorCode FILTER_EXPRESSION_ATTR_TO_GET_ERROR = new ValidationErrorCode("AttributesToGet should be null when FilterExpression is present");
    public final ErrorCode CONDITION_EXPRESSION_ATTR_TO_UPD_ERROR = new ValidationErrorCode("AttributesToUpdate should be null when ConditionExpression is present");
    public final ErrorCode UPDATE_EXPRESSION_ATTR_TO_UPD_ERROR = new ValidationErrorCode("AttributesToUpdate should be null when UpdateExpression is present");
    public final ErrorCode PROJECTION_EXPRESSION_FILTER_ERROR = new ValidationErrorCode("Filter should be null when ProjectionExpression is present");
    public final ErrorCode FILTER_EXPRESSION_FILTER_ERROR = new ValidationErrorCode("Filter should be null when FilterExpression is present");
    public final ErrorCode NULL_PROJECTION_EXPRESSION_NON_NULL_EXPRATTRNAMES = new ValidationErrorCode("ExpressionAttributeNames should be null when ProjectionExpression is null");
    public final ErrorCode NULL_CONDITION_EXPRESSION_NON_NULL_EXPRATTRNAMES = new ValidationErrorCode("ExpressionAttributeNames should be null when ConditionExpression is null");
    public final ErrorCode NULL_FILTER_EXPRESSION_NON_NULL_EXPRATTRNAMES = new ValidationErrorCode("ExpressionAttributeNames should be null when FilterExpression is null");
    public final ErrorCode NULL_UPDATE_EXPRESSION_NON_NULL_EXPRATTRNAMES = new ValidationErrorCode("ExpressionAttributeNames should be null when UpdateExpression is null");
    public final ErrorCode NULL_CONDITION_EXPRESSION_NON_NULL_EXPRATTRVALUES = new ValidationErrorCode("ExpressionAttributeValues should be null when ConditionExpression is null");
    public final ErrorCode NULL_FILTER_EXPRESSION_NON_NULL_EXPRATTRVALUES = new ValidationErrorCode("ExpressionAttributeValues should be null when FilterExpression is null");
    public final ErrorCode NULL_UPDATE_EXPRESSION_NON_NULL_EXPRATTRVALUES = new ValidationErrorCode("ExpressionAttributeValues should be null when UpdateExpression is null");
    public final ErrorCode EMPTY_EXPRESSIONATTRNAMES_MAP = new ValidationErrorCode("ExpressionAttributeNames must not be empty");
    public final ErrorCode EMPTY_EXPRESSIONATTRVALUES_MAP = new ValidationErrorCode("ExpressionAttributeValues must not be empty");
    public final ErrorCode EXPRESSION_ATTRNAMES_INVALID_VALUE = new ValidationErrorCode("ExpressionAttributeNames contains invalid value");
    public final ErrorCode EXPRESSION_ATTRVALUES_INVALID_VALUE = new ValidationErrorCode("ExpressionAttributeValues contains invalid value");
    public final ErrorCode EXPRESSION_SIZE_EXCEEDED_EXPRESSIONATTRIBUTENAMES_MAP = new ValidationErrorCode("Expression size exceeded due to ExpressionAttributeNames");
    public final ErrorCode EXPRESSION_SIZE_EXCEEDED_EXPRATTRNAMES_PLUS_VALUES_MAP = new ValidationErrorCode("Expression size exceeded due to ExpressionAttributeValues plus ExpressionAttributeNames");
    public final ErrorCode EXPR_ATTR_NAMES_PLUS_VALUES_MAPS_SIZE_EXCEEDED = new ValidationErrorCode("Combined size of ExpressionAttributeNames and ExpressionAttributeValues exceeds max size");
    public final ErrorCode DOCUMENT_PATH_INVALID_FOR_UPDATE = new ValidationErrorCode("The document path provided in the update expression is invalid for update");
    public final ErrorCode ATTRIBUTE_NOT_FOUND_IN_ITEM = new ValidationErrorCode("The provided expression refers to an attribute that does not exist in the item");
    public final ErrorCode EMPTY_EXPR_ATTR_NAMES_MAP = new ValidationErrorCode("ExpressionAttributeNames must not be empty");
    public final ErrorCode EMPTY_EXPR_ATTR_VALUES_MAP = new ValidationErrorCode("ExpressionAttributeValues must not be empty");
    public final ErrorCode EXPR_ATTR_NAMES_MAP_INVALID_KEY = new ValidationErrorCode("ExpressionAttributeNames contains invalid key");
    public final ErrorCode EXPR_ATTR_VALUES_MAP_INVALID_KEY = new ValidationErrorCode("ExpressionAttributeValues contains invalid key");
    public final ErrorCode EXPR_ATTR_NAMES_MAP_INVALID_VALUE = new ValidationErrorCode("ExpressionAttributeNames contains invalid value");
    public final ErrorCode EXPR_ATTR_VALUES_MAP_INVALID_VALUE = new ValidationErrorCode("ExpressionAttributeValues contains invalid value");
    public final ErrorCode EXPR_ATTR_NAMES_MAP_UNUSED_VALUE = new ValidationErrorCode("Value provided in ExpressionAttributeNames unused in expressions");
    public final ErrorCode EXPR_ATTR_VALUES_MAP_UNUSED_VALUE = new ValidationErrorCode("Value provided in ExpressionAttributeValues unused in expressions");
    public final ErrorCode EXPR_ATTR_NAMES_MAP_SIZE_EXCEEDED = new ValidationErrorCode("ExpressionAttributeNames exceeds max size");
    public final ErrorCode EXPR_ATTR_VALUES_MAP_SIZE_EXCEEDED = new ValidationErrorCode("ExpressionAttributeValues exceeds max size");
    public final ErrorCode UPDATE_EXPRESSION_TYPE_MISMATCH = new ValidationErrorCode("An operand in the update expression has an incorrect data type");
    public final ErrorCode UPDATING_INDEX_KEY_TYPE_MISMATCH = new ValidationErrorCode("The update expression attempted to update the secondary index key to unsupported type");
    public final ErrorCode INVALID_PROJECTION_EXPRESSION = new ValidationErrorCode("Invalid ProjectionExpression");
    public final ErrorCode INVALID_CONDITION_EXPRESSION = new ValidationErrorCode("Invalid ConditionExpression");
    public final ErrorCode INVALID_FILTER_EXPRESSION = new ValidationErrorCode("Invalid FilterExpression");
    public final ErrorCode INVALID_UPDATE_EXPRESSION = new ValidationErrorCode("Invalid UpdateExpression");
    public final ErrorCode NESTED_ATTRIBUTE_ACCESS_TO_KEY_ATTRIBUTE_IN_EXPRESSION = new ValidationErrorCode("Key attributes must be scalars; list random access '[]' and map lookup '.' are not allowed");
    public final ErrorCode FILTER_EXPRESSION_CONTAINS_KEY_ATTRIBUTE = new ValidationErrorCode("Filter Expression can only contain non-primary key attributes");
    public final ErrorCode EXPRESSIONS_MIXED_WITH_OLD_FIELDS = new ValidationErrorCode("Can not use both expression and non-expression parameters in the same request");
    public final ErrorCode EXPR_ATTR_NAMES_WITHOUT_EXPRESSIONS = new ValidationErrorCode("ExpressionAttributeNames can only be specified when using expressions");
    public final ErrorCode EXPR_ATTR_VALUES_WITHOUT_EXPRESSIONS = new ValidationErrorCode("ExpressionAttributeValues can only be specified when using expressions");
    public final ErrorCode ATTRIBUTE_PART_OF_KEY = new ValidationErrorCode("This attribute is part of the key");
    public final ErrorCode INVALID_KEY_CONDITION_EXPRESSION = new ValidationErrorCode("Invalid KeyConditionExpression");
    public final ErrorCode KEY_CONDITION_EXPRESSION_INVALID_COMPARISON_OPERATOR = new ValidationErrorCode("Invalid operator used in KeyConditionExpression");
    public final ErrorCode KEY_CONDITION_EXPRESSION_INVALID_CONDITION = new ValidationErrorCode("Invalid condition in KeyConditionExpression");
    public final ErrorCode KEY_CONDITION_EXPRESSION_NESTED_OPERATOR = new ValidationErrorCode("KeyConditionExpressions cannot contain nested operations");
    public final ErrorCode KEY_CONDITION_EXPRESSION_NESTED_PATH_FOUND = new ValidationErrorCode("KeyConditionExpressions cannot have conditions on nested attributes");
    public final ErrorCode KEY_CONDITION_EXPRESSION_DUPLICATE_KEY = new ValidationErrorCode("KeyConditionExpressions must only contain one condition per key");

    protected abstract RuntimeException newValidationException(String var1);

    protected abstract RuntimeException newConditionalCheckFailedException(String var1);

    protected abstract RuntimeException newLimitExceededException(String var1);

    protected abstract RuntimeException newProvisionedThroughputExceededException(String var1);

    protected abstract RuntimeException newResourceNotFoundException(String var1);

    protected abstract RuntimeException newResourceInUseException(String var1);

    protected abstract RuntimeException newInternalServerError(String var1);

    protected abstract RuntimeException newItemCollectionSizeLimitExceededException(String var1);

    protected abstract RuntimeException newThrottlingException(String var1);

    private class ValidationErrorCode
    extends ErrorCode {
        ValidationErrorCode(String message) {
            super(message);
        }

        @Override
        protected RuntimeException newException(String message) {
            return ErrorFactory.this.newValidationException(message);
        }
    }

    public abstract class ErrorCode {
        protected final RuntimeException exception;
        private final String message;

        ErrorCode(String message) {
            this.message = message;
            this.exception = this.newException(message);
            this.exception.setStackTrace(new StackTraceElement[0]);
        }

        protected abstract RuntimeException newException(String var1);

        public void throwAsException() {
            this.throwAsException((String)null);
        }

        public void throwAsException(LocalDBClientExceptionMessage msg) {
            this.throwAsException(msg.getMessage());
        }

        public void throwAsException(String additionalMessage) {
            RuntimeException e = additionalMessage != null && additionalMessage.length() != 0 ? this.newException(this.message + ": " + additionalMessage) : this.exception;
            throw e;
        }

        public String getMessage() {
            return this.message;
        }
    }

    private class ResourceInUseErrorCode
    extends ErrorCode {
        ResourceInUseErrorCode(String message) {
            super(message);
        }

        @Override
        protected RuntimeException newException(String message) {
            return ErrorFactory.this.newResourceInUseException(message);
        }
    }

    private class ResourceNotFoundErrorCode
    extends ErrorCode {
        ResourceNotFoundErrorCode(String message) {
            super(message);
        }

        @Override
        protected RuntimeException newException(String message) {
            return ErrorFactory.this.newResourceNotFoundException(message);
        }
    }

    private class ConditionalCheckFailedErrorCode
    extends ErrorCode {
        ConditionalCheckFailedErrorCode(String message) {
            super(message);
        }

        @Override
        protected RuntimeException newException(String message) {
            return ErrorFactory.this.newConditionalCheckFailedException(message);
        }
    }

    private class LimitExceededErrorCode
    extends ErrorCode {
        LimitExceededErrorCode(String message) {
            super(message);
        }

        @Override
        protected RuntimeException newException(String message) {
            return ErrorFactory.this.newLimitExceededException(message);
        }
    }

    private class InternalServerErrorCode
    extends ErrorCode {
        InternalServerErrorCode(String message) {
            super(message);
        }

        @Override
        protected RuntimeException newException(String message) {
            return ErrorFactory.this.newInternalServerError(message);
        }
    }

    private class ProvisionedThroughputExceededErrorCode
    extends ErrorCode {
        ProvisionedThroughputExceededErrorCode(String message) {
            super(message);
        }

        @Override
        protected RuntimeException newException(String message) {
            return ErrorFactory.this.newProvisionedThroughputExceededException(message);
        }
    }

    private class ThrottlingErrorCode
    extends ErrorCode {
        ThrottlingErrorCode(String message) {
            super(message);
        }

        @Override
        protected RuntimeException newException(String message) {
            return ErrorFactory.this.newThrottlingException(message);
        }
    }

    private class ItemCollectionSizeLimitExceededErrorCode
    extends ErrorCode {
        ItemCollectionSizeLimitExceededErrorCode(String message) {
            super(message);
        }

        @Override
        protected RuntimeException newException(String message) {
            return ErrorFactory.this.newItemCollectionSizeLimitExceededException(message);
        }
    }
}

