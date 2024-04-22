/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  com.amazonaws.AmazonServiceException
 *  com.amazonaws.services.dynamodbv2.model.ConditionalCheckFailedException
 *  com.amazonaws.services.dynamodbv2.model.DuplicateItemException
 *  com.amazonaws.services.dynamodbv2.model.IdempotentParameterMismatchException
 *  com.amazonaws.services.dynamodbv2.model.InternalServerErrorException
 *  com.amazonaws.services.dynamodbv2.model.ItemCollectionSizeLimitExceededException
 *  com.amazonaws.services.dynamodbv2.model.LimitExceededException
 *  com.amazonaws.services.dynamodbv2.model.ProvisionedThroughputExceededException
 *  com.amazonaws.services.dynamodbv2.model.ResourceInUseException
 *  com.amazonaws.services.dynamodbv2.model.ResourceNotFoundException
 *  com.amazonaws.services.dynamodbv2.model.TransactionCanceledException
 *  com.amazonaws.services.dynamodbv2.model.TransactionConflictException
 *  com.amazonaws.services.dynamodbv2.model.TrimmedDataAccessException
 */
package com.amazonaws.services.dynamodbv2.exceptions;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.dynamodbv2.model.ConditionalCheckFailedException;
import com.amazonaws.services.dynamodbv2.model.DuplicateItemException;
import com.amazonaws.services.dynamodbv2.model.IdempotentParameterMismatchException;
import com.amazonaws.services.dynamodbv2.model.InternalServerErrorException;
import com.amazonaws.services.dynamodbv2.model.ItemCollectionSizeLimitExceededException;
import com.amazonaws.services.dynamodbv2.model.LimitExceededException;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughputExceededException;
import com.amazonaws.services.dynamodbv2.model.ResourceInUseException;
import com.amazonaws.services.dynamodbv2.model.ResourceNotFoundException;
import com.amazonaws.services.dynamodbv2.model.TransactionCanceledException;
import com.amazonaws.services.dynamodbv2.model.TransactionConflictException;
import com.amazonaws.services.dynamodbv2.model.TrimmedDataAccessException;
import java.util.HashMap;
import java.util.Map;

public enum AmazonServiceExceptionType {
    INCOMPLETE_SIGNATURE(400, "IncompleteSignature", "The request signature does not conform to AWS standards.", AmazonServiceException.class),
    INTERNAL_FAILURE(500, "InternalFailure", "The request processing has failed because of an unknown error, exception or failure.", AmazonServiceException.class),
    INVALID_ACTION(400, "InvalidAction", "The action or operation requested is invalid. Verify that the action is typed correctly.", AmazonServiceException.class),
    INVALID_CLIENT_TOKEN_ID(400, "InvalidClientTokenId", "The X.509 certificate or AWS access key ID provided does not exist in our records.", AmazonServiceException.class),
    INVALID_PARAMETER_COMBINATION(400, "InvalidParameterCombination", "Parameters that must not be used together were used together.", AmazonServiceException.class),
    INVALID_PARAMETER_VALUE(400, "InvalidParameterValue", "An invalid or out-of-range value was supplied for the input parameter.", AmazonServiceException.class),
    INVALID_QUERY_PARAMETER(400, "InvalidQueryParameter", "AWS query string is malformed, does not adhere to AWS standards.", AmazonServiceException.class),
    INVALID_VERSION(400, "InvalidAction", "DynamoDB Local does not support v1 API.", AmazonServiceException.class),
    MALFORMED_QUERY_STRING(404, "MalformedQueryString", "The query string contains a syntax error.", AmazonServiceException.class),
    MISSING_ACTION(400, "MissingAction", "The request is missing an action or a required parameter.", AmazonServiceException.class),
    MISSING_AUTHENTICATION_TOKEN(400, "MissingAuthenticationToken", "Request must contain either a valid (registered) AWS access key ID or X.509 certificate.", AmazonServiceException.class),
    MISSING_PARAMETER(400, "MissingParameter", "A required parameter for the specified action is not supplied.", AmazonServiceException.class),
    OPT_IN_REQUIRED(400, "OptInRequired", "The AWS access key ID needs a subscription for the service.", AmazonServiceException.class),
    REQUEST_EXPIRED(400, "RequestExpired", "The request reached the service more than 15 minutes after the date stamp on the request or more than 15 minutes after the request expiration date (such as for pre-signed URLs), or the date stamp on the request is more than 15 minutes in the future.", AmazonServiceException.class),
    SERVICE_UNAVAILABLE(500, "ServiceUnavailable", "The request has failed due to a temporary failure of the server.", AmazonServiceException.class),
    THROTTLING(400, "Throttling", "Request was denied due to request throttling.", AmazonServiceException.class),
    ACCESS_DENIED_EXCEPTION(400, "AccessDeniedException", "Access denied.", AmazonServiceException.class),
    CONDITIONAL_CHECK_FAILED_EXCEPTION(400, "ConditionalCheckFailedException", "The conditional request failed.", ConditionalCheckFailedException.class),
    INCOMPLETE_SIGNATURE_EXCEPTION(400, "IncompleteSignatureException", "The request signature does not conform to AWS standards.", AmazonServiceException.class),
    INTERNAL_SERVER_ERROR(500, "InternalServerError", "The server encountered an internal error trying to fulfill the request.", InternalServerErrorException.class),
    ITEM_COLLECTION_SIZE_LIMIT_EXCEEDED_EXCEPTION(400, "ItemCollectionSizeLimitExceededException", "Item size has exceeded the maximum allowed size", ItemCollectionSizeLimitExceededException.class),
    LIMIT_EXCEEDED_EXCEPTION(400, "LimitExceededException", "Too many operations for a given subscriber.", LimitExceededException.class),
    MISSING_AUTHENTICATION_TOKEN_EXCEPTION(400, "MissingAuthenticationTokenException", "Request must contain a valid (registered) AWS Access Key ID.", AmazonServiceException.class),
    PROVISIONED_THROUGHPUT_EXCEEDED_EXCEPTION(400, "ProvisionedThroughputExceededException", "You exceeded your maximum allowed provisioned throughput.", ProvisionedThroughputExceededException.class),
    REQUEST_TOO_LARGE(413, null, "", AmazonServiceException.class),
    RESOURCE_IN_USE_EXCEPTION(400, "ResourceInUseException", "The resource which you are attempting to change is in use.", ResourceInUseException.class),
    RESOURCE_NOT_FOUND_EXCEPTION(400, "ResourceNotFoundException", "The resource which is being requested does not exist.", ResourceNotFoundException.class),
    SERVICE_UNAVAILABLE_EXCEPTION(500, "ServiceUnavailableException", "The service is currently unavailable or busy.", AmazonServiceException.class),
    THROTTLING_EXCEPTION(400, "ThrottlingException", "Rate of requests exceeds the allowed throughput.", AmazonServiceException.class),
    UNRECOGNIZED_CLIENT_EXCEPTION(400, "UnrecognizedClientException", "The Access Key ID or security token is invalid.", AmazonServiceException.class),
    VALIDATION_EXCEPTION(400, "ValidationException", "One or more required parameter values were missing.", AmazonServiceException.class),
    TRANSACTION_CANCELED_EXCEPTION(400, "TransactionCanceledException", "", TransactionCanceledException.class),
    TRANSACTION_CONFLICT_EXCEPTION(400, "TransactionConflictException", "", TransactionConflictException.class),
    IDEMPOTENT_PARAMETER_MISMATCH_EXCEPTION(400, "IdempotentParameterMismatchException", "", IdempotentParameterMismatchException.class),
    DUPLICATE_ITEM_EXCEPTION(400, "DuplicateItem", "Duplicate primary key exists in table", DuplicateItemException.class),
    EXPIRED_SHARD_ITERATOR(400, "ExpiredIteratorException", "", AmazonServiceException.class),
    TRIMMED_DATA_ACCESS_EXCEPTION(400, "TrimmedDataAccessException", "The operation attempted to read past the oldest stream record in a shard.", TrimmedDataAccessException.class),
    UNKNOWN_OPERATION_EXCEPTION(400, "UnknownOperationException", "An unknown operation was requested.", AmazonServiceException.class);

    private static final Map<String, AmazonServiceExceptionType> TYPE_BY_CODE;
    private final int responseStatus;
    private final String errorCode;
    private final String message;
    private final Class<? extends AmazonServiceException> clientClass;

    private <T extends AmazonServiceException> AmazonServiceExceptionType(int responseStatus, String errorCode, String message, Class<T> clientClass) {
        this.responseStatus = responseStatus;
        this.errorCode = errorCode;
        this.message = message;
        this.clientClass = clientClass;
    }

    public static AmazonServiceExceptionType valueOfErrorCode(String errorCode) {
        return TYPE_BY_CODE.get(errorCode);
    }

    public int getResponseStatus() {
        return this.responseStatus;
    }

    public String getErrorCode() {
        return this.errorCode;
    }

    public String getMessage() {
        return this.message;
    }

    public Class<? extends AmazonServiceException> getClientClass() {
        return this.clientClass;
    }

    public String toString() {
        return this.responseStatus + ", " + this.errorCode + ", " + this.message;
    }

    static {
        TYPE_BY_CODE = new HashMap<String, AmazonServiceExceptionType>();
        for (AmazonServiceExceptionType type : AmazonServiceExceptionType.values()) {
            TYPE_BY_CODE.put(type.getErrorCode(), type);
        }
    }
}

