/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  com.amazonaws.AmazonServiceException
 *  com.amazonaws.services.dynamodbv2.model.CancellationReason
 *  org.apache.logging.log4j.Logger
 */
package com.amazonaws.services.dynamodbv2.exceptions;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.dynamodbv2.exceptions.AmazonServiceExceptionType;
import com.amazonaws.services.dynamodbv2.exceptions.DynamoDBLocalServiceException;
import com.amazonaws.services.dynamodbv2.local.shared.logging.LogManager;
import com.amazonaws.services.dynamodbv2.local.shared.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.local.shared.validate.ErrorFactory;
import com.amazonaws.services.dynamodbv2.model.CancellationReason;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.logging.log4j.Logger;

public class AWSExceptionFactory
extends ErrorFactory {
    static Logger logger = LogManager.getLogger(AWSExceptionFactory.class);

    public static DynamoDBLocalServiceException buildInternalServerException(String msg) {
        return AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.INTERNAL_SERVER_ERROR, msg);
    }

    public static DynamoDBLocalServiceException buildAWSException(AmazonServiceExceptionType type) {
        return AWSExceptionFactory.buildAWSException(type, null);
    }

    public static DynamoDBLocalServiceException buildAWSException(AmazonServiceExceptionType type, String msg, Map<String, AttributeValue> attributes) {
        return AWSExceptionFactory.buildLocalServiceException(type, msg, null, attributes);
    }

    public static DynamoDBLocalServiceException buildAWSException(AmazonServiceExceptionType type, String msg) {
        return AWSExceptionFactory.buildLocalServiceException(type, msg, null, null);
    }

    private static String join(List<String> strings, String separator) {
        StringBuilder sb = new StringBuilder();
        sb.append(strings.get(0));
        for (int i = 1; i < strings.size(); ++i) {
            sb.append(separator).append(strings.get(i));
        }
        return sb.toString();
    }

    public static DynamoDBLocalServiceException buildTransactionCanceledException(List<CancellationReason> cancellationReasons) {
        ArrayList<String> cancellationCodes = new ArrayList<String>(cancellationReasons.size());
        for (CancellationReason reason : cancellationReasons) {
            cancellationCodes.add(reason.getCode());
        }
        String msg = "Transaction cancelled, please refer cancellation reasons for specific reasons [" + AWSExceptionFactory.join(cancellationCodes, ", ") + "]";
        return AWSExceptionFactory.buildLocalServiceException(AmazonServiceExceptionType.TRANSACTION_CANCELED_EXCEPTION, msg, cancellationReasons, null);
    }

    private static DynamoDBLocalServiceException buildLocalServiceException(AmazonServiceExceptionType type, String msg, List<CancellationReason> cancellationReasons, Map<String, AttributeValue> item) {
        String errorMessage;
        String string = errorMessage = msg == null ? type.getMessage() : msg;
        if (cancellationReasons != null && type != AmazonServiceExceptionType.TRANSACTION_CANCELED_EXCEPTION) {
            throw new IllegalArgumentException("Cannot supply a cancellation reason for exception types other than TransactionCanceledException.");
        }
        DynamoDBLocalServiceException ret = new DynamoDBLocalServiceException(errorMessage, cancellationReasons, item);
        ret.setStatusCode(type.getResponseStatus());
        ret.setErrorCode(type.getErrorCode());
        return ret;
    }

    public static AmazonServiceException buildCoralValidationException(String value, String memberName, String errorDetails) {
        String msg = "Value " + value + " at '" + memberName + "' failed to satisfy constraint: " + errorDetails;
        return AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, msg);
    }

    @Override
    protected RuntimeException newConditionalCheckFailedException(String message) {
        return AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.CONDITIONAL_CHECK_FAILED_EXCEPTION, message);
    }

    @Override
    protected RuntimeException newInternalServerError(String message) {
        return AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.INTERNAL_SERVER_ERROR, message);
    }

    @Override
    protected RuntimeException newItemCollectionSizeLimitExceededException(String message) {
        return AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.ITEM_COLLECTION_SIZE_LIMIT_EXCEEDED_EXCEPTION, message);
    }

    @Override
    protected RuntimeException newLimitExceededException(String message) {
        return AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.LIMIT_EXCEEDED_EXCEPTION, message);
    }

    @Override
    protected RuntimeException newProvisionedThroughputExceededException(String message) {
        return AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.PROVISIONED_THROUGHPUT_EXCEEDED_EXCEPTION, message);
    }

    @Override
    protected RuntimeException newResourceInUseException(String message) {
        return AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.RESOURCE_IN_USE_EXCEPTION, message);
    }

    @Override
    protected RuntimeException newResourceNotFoundException(String message) {
        return AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.RESOURCE_NOT_FOUND_EXCEPTION, message);
    }

    @Override
    protected RuntimeException newThrottlingException(String message) {
        return AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.THROTTLING_EXCEPTION, message);
    }

    @Override
    protected RuntimeException newValidationException(String message) {
        return AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, message);
    }
}

