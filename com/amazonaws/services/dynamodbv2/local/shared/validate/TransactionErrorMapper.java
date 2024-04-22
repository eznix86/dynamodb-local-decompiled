/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  com.amazonaws.AmazonServiceException
 *  com.amazonaws.services.dynamodbv2.model.CancellationReason
 *  com.amazonaws.services.dynamodbv2.model.TransactWriteItem
 */
package com.amazonaws.services.dynamodbv2.local.shared.validate;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.dynamodbv2.exceptions.AWSExceptionFactory;
import com.amazonaws.services.dynamodbv2.exceptions.AmazonServiceExceptionType;
import com.amazonaws.services.dynamodbv2.exceptions.DynamoDBLocalServiceException;
import com.amazonaws.services.dynamodbv2.local.google.Lists;
import com.amazonaws.services.dynamodbv2.local.shared.access.api.dp.TransactWriteItemsFunction;
import com.amazonaws.services.dynamodbv2.local.shared.exceptions.LocalDBClientExceptionMessage;
import com.amazonaws.services.dynamodbv2.model.CancellationReason;
import com.amazonaws.services.dynamodbv2.model.TransactWriteItem;
import java.util.List;

public class TransactionErrorMapper {
    public static final String VALIDATION_ERROR_CANCELLATION_CODE = "ValidationError";
    public static final String CONDITION_CHECK_FAILED_CODE = "ConditionalCheckFailed";
    private final AWSExceptionFactory awsExceptionFactory;

    public TransactionErrorMapper(AWSExceptionFactory awsExceptionFactory) {
        this.awsExceptionFactory = awsExceptionFactory;
    }

    public CancellationReason getEmptyCancellationReason() {
        return new CancellationReason().withCode("None");
    }

    public CancellationReason mapToCancellationReasonWhenGettingExistingItem(DynamoDBLocalServiceException e) {
        if (AmazonServiceExceptionType.VALIDATION_EXCEPTION.getErrorCode().equals(e.getErrorCode())) {
            String msg = e.getMessage();
            if (LocalDBClientExceptionMessage.ITEM_TOO_BIG.getMessage().equals(msg)) {
                return new CancellationReason().withCode(VALIDATION_ERROR_CANCELLATION_CODE).withMessage(e.getMessage());
            }
        }
        throw e;
    }

    public CancellationReason mapToCancellationReasonWhenHandlingTransactionOperation(DynamoDBLocalServiceException e, TransactWriteItem item, int memberPosition) {
        if (AmazonServiceExceptionType.DUPLICATE_ITEM_EXCEPTION.getErrorCode().equals(e.getErrorCode()) || AmazonServiceExceptionType.VALIDATION_EXCEPTION.getErrorCode().equals(e.getErrorCode())) {
            String msg = e.getMessage();
            if (LocalDBClientExceptionMessage.ITEM_CONTAINS_NULL_ATTRVALUE.getMessage().equals(msg)) {
                throw this.buildCoralValidationException("null", item, memberPosition, "key", LocalDBClientExceptionMessage.MEMBER_NULL.getMessage());
            }
            if (LocalDBClientExceptionMessage.INCONSISTENT_INDEX_KEY_TYPES.getMessage().equals(msg) || LocalDBClientExceptionMessage.INCONSISTENT_PRIMARY_KEY_TYPES.getMessage().equals(msg) || LocalDBClientExceptionMessage.KEY_VALUE_TOO_BIG.getMessage().equals(msg) || LocalDBClientExceptionMessage.ITEM_UPD_TOO_LARGE.getMessage().equals(msg) || LocalDBClientExceptionMessage.INCONSISTENT_TYPES.getMessage().equals(msg)) {
                return new CancellationReason().withCode(VALIDATION_ERROR_CANCELLATION_CODE).withMessage(msg);
            }
        }
        if (AmazonServiceExceptionType.CONDITIONAL_CHECK_FAILED_EXCEPTION.getErrorCode().equals(e.getErrorCode())) {
            return new CancellationReason().withCode(CONDITION_CHECK_FAILED_CODE).withMessage(e.getMessage());
        }
        throw e;
    }

    public DynamoDBLocalServiceException mapToCorrectExceptionForPutRequest(DynamoDBLocalServiceException e, TransactWriteItem item, int itemPosition) {
        if (LocalDBClientExceptionMessage.INVALID_PUT_NULL.getMessage().equals(e.getMessage())) {
            throw this.buildCoralValidationException("null", item, itemPosition + 1, "item", LocalDBClientExceptionMessage.MEMBER_NULL.getMessage());
        }
        throw e;
    }

    public DynamoDBLocalServiceException mapToCorrectExceptionForKeyedRequest(DynamoDBLocalServiceException e, TransactWriteItem item, int itemPosition) {
        if (LocalDBClientExceptionMessage.MISSING_KEY.getMessage().equals(e.getMessage())) {
            throw this.buildCoralValidationException("null", item, itemPosition + 1, "key", LocalDBClientExceptionMessage.MEMBER_NULL.getMessage());
        }
        throw e;
    }

    public DynamoDBLocalServiceException mapToCorrectExceptionForUpdateRequest(DynamoDBLocalServiceException e, TransactWriteItem item, int itemPosition) {
        if (e.getMessage().startsWith(this.awsExceptionFactory.EXPR_ATTR_VALUES_WITHOUT_EXPRESSIONS.getMessage())) {
            throw this.buildCoralValidationException("null", item, itemPosition + 1, "updateExpression", LocalDBClientExceptionMessage.MEMBER_NULL.getMessage());
        }
        if (e.getMessage().contains(LocalDBClientExceptionMessage.CANNOT_UPDATE_ATTRIBUTE.getMessage()) && e.getMessage().contains(this.awsExceptionFactory.ATTRIBUTE_PART_OF_KEY.getMessage())) {
            List<CancellationReason> cancellationReasons = Lists.newArrayList(new CancellationReason[0]);
            CancellationReason cancellationReason = new CancellationReason().withCode(VALIDATION_ERROR_CANCELLATION_CODE).withMessage(e.getMessage());
            cancellationReasons.add(cancellationReason);
            throw AWSExceptionFactory.buildTransactionCanceledException(cancellationReasons);
        }
        return this.mapToCorrectExceptionForKeyedRequest(e, item, itemPosition);
    }

    public AmazonServiceException buildCoralValidationException(String value, TransactWriteItem writeItem, int writeItemPosition, String fieldName, String errorDetails) {
        TransactWriteItemsFunction.OperationType operationType = TransactWriteItemsFunction.OperationType.get(writeItem);
        String member = String.format("transactItems.%d.member.%s.%s", writeItemPosition, operationType.getApiName(), fieldName);
        return AWSExceptionFactory.buildCoralValidationException(value, member, errorDetails);
    }
}

