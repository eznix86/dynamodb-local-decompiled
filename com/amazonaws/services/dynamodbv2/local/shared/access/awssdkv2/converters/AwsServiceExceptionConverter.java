/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  com.amazonaws.AmazonServiceException
 *  com.amazonaws.services.dynamodbv2.model.AttributeValue
 *  com.amazonaws.services.dynamodbv2.model.CancellationReason
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
 *  com.amazonaws.util.CollectionUtils
 *  software.amazon.awssdk.awscore.exception.AwsErrorDetails
 *  software.amazon.awssdk.awscore.exception.AwsServiceException
 *  software.amazon.awssdk.awscore.exception.AwsServiceException$Builder
 *  software.amazon.awssdk.services.dynamodb.model.AttributeValue
 *  software.amazon.awssdk.services.dynamodb.model.CancellationReason
 *  software.amazon.awssdk.services.dynamodb.model.ConditionalCheckFailedException
 *  software.amazon.awssdk.services.dynamodb.model.ConditionalCheckFailedException$Builder
 *  software.amazon.awssdk.services.dynamodb.model.DuplicateItemException
 *  software.amazon.awssdk.services.dynamodb.model.DynamoDbException
 *  software.amazon.awssdk.services.dynamodb.model.IdempotentParameterMismatchException
 *  software.amazon.awssdk.services.dynamodb.model.InternalServerErrorException
 *  software.amazon.awssdk.services.dynamodb.model.ItemCollectionSizeLimitExceededException
 *  software.amazon.awssdk.services.dynamodb.model.LimitExceededException
 *  software.amazon.awssdk.services.dynamodb.model.ProvisionedThroughputExceededException
 *  software.amazon.awssdk.services.dynamodb.model.ResourceInUseException
 *  software.amazon.awssdk.services.dynamodb.model.ResourceNotFoundException
 *  software.amazon.awssdk.services.dynamodb.model.TransactionCanceledException
 *  software.amazon.awssdk.services.dynamodb.model.TransactionCanceledException$Builder
 *  software.amazon.awssdk.services.dynamodb.model.TransactionConflictException
 *  software.amazon.awssdk.services.dynamodb.model.TrimmedDataAccessException
 */
package com.amazonaws.services.dynamodbv2.local.shared.access.awssdkv2.converters;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.dynamodbv2.local.shared.access.awssdkv2.converters.AttributeValueConverter;
import com.amazonaws.services.dynamodbv2.model.CancellationReason;
import com.amazonaws.services.dynamodbv2.model.ConditionalCheckFailedException;
import com.amazonaws.services.dynamodbv2.model.ItemCollectionSizeLimitExceededException;
import com.amazonaws.services.dynamodbv2.model.ResourceInUseException;
import com.amazonaws.services.dynamodbv2.model.ResourceNotFoundException;
import com.amazonaws.util.CollectionUtils;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import software.amazon.awssdk.awscore.exception.AwsErrorDetails;
import software.amazon.awssdk.awscore.exception.AwsServiceException;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.ConditionalCheckFailedException;
import software.amazon.awssdk.services.dynamodb.model.DuplicateItemException;
import software.amazon.awssdk.services.dynamodb.model.DynamoDbException;
import software.amazon.awssdk.services.dynamodb.model.IdempotentParameterMismatchException;
import software.amazon.awssdk.services.dynamodb.model.InternalServerErrorException;
import software.amazon.awssdk.services.dynamodb.model.LimitExceededException;
import software.amazon.awssdk.services.dynamodb.model.ProvisionedThroughputExceededException;
import software.amazon.awssdk.services.dynamodb.model.TransactionCanceledException;
import software.amazon.awssdk.services.dynamodb.model.TransactionConflictException;
import software.amazon.awssdk.services.dynamodb.model.TrimmedDataAccessException;

public class AwsServiceExceptionConverter {
    private static final Map<Class<? extends AmazonServiceException>, AwsServiceException.Builder> builderMapper = new HashMap<Class<? extends AmazonServiceException>, AwsServiceException.Builder>();

    public static AwsServiceException convert(AmazonServiceException src) {
        List cancellationReasons;
        AwsServiceException.Builder awsServiceExceptionBuilder = builderMapper.getOrDefault(((Object)((Object)src)).getClass(), (AwsServiceException.Builder)DynamoDbException.builder());
        if (src instanceof com.amazonaws.services.dynamodbv2.model.TransactionCanceledException && !CollectionUtils.isNullOrEmpty((Collection)(cancellationReasons = ((com.amazonaws.services.dynamodbv2.model.TransactionCanceledException)src).getCancellationReasons()))) {
            ((TransactionCanceledException.Builder)awsServiceExceptionBuilder).cancellationReasons(AwsServiceExceptionConverter.buildCancellationReasons(cancellationReasons));
        }
        if (src instanceof ConditionalCheckFailedException) {
            ((ConditionalCheckFailedException.Builder)awsServiceExceptionBuilder).item(AwsServiceExceptionConverter.convertAttributeValueToSdkV2(((ConditionalCheckFailedException)src).getItem()));
        }
        return awsServiceExceptionBuilder.requestId(src.getRequestId()).statusCode(src.getStatusCode()).awsErrorDetails(AwsErrorDetails.builder().errorMessage(src.getErrorMessage()).errorCode(src.getErrorCode()).serviceName(src.getServiceName()).build()).build();
    }

    private static Collection<software.amazon.awssdk.services.dynamodb.model.CancellationReason> buildCancellationReasons(List<CancellationReason> cancellationReasons) {
        return cancellationReasons.stream().map(cancellationReason -> (software.amazon.awssdk.services.dynamodb.model.CancellationReason)software.amazon.awssdk.services.dynamodb.model.CancellationReason.builder().item(AwsServiceExceptionConverter.convertAttributeValueToSdkV2(cancellationReason.getItem())).code(cancellationReason.getCode()).message(cancellationReason.getMessage()).build()).collect(Collectors.toList());
    }

    private static Map<String, AttributeValue> convertAttributeValueToSdkV2(Map<String, com.amazonaws.services.dynamodbv2.model.AttributeValue> v1Item) {
        if (v1Item == null) {
            return null;
        }
        HashMap<String, AttributeValue> newSdkV2Item = new HashMap<String, AttributeValue>();
        if (v1Item.isEmpty()) {
            return newSdkV2Item;
        }
        AttributeValueConverter attributeValueConverter = new AttributeValueConverter();
        Set<Map.Entry<String, com.amazonaws.services.dynamodbv2.model.AttributeValue>> entries = v1Item.entrySet();
        for (Map.Entry<String, com.amazonaws.services.dynamodbv2.model.AttributeValue> entry : entries) {
            newSdkV2Item.put(entry.getKey(), attributeValueConverter.toSdkV2(entry.getValue()));
        }
        return newSdkV2Item;
    }

    static {
        builderMapper.put(ConditionalCheckFailedException.class, (AwsServiceException.Builder)software.amazon.awssdk.services.dynamodb.model.ConditionalCheckFailedException.builder());
        builderMapper.put(com.amazonaws.services.dynamodbv2.model.TransactionCanceledException.class, (AwsServiceException.Builder)TransactionCanceledException.builder());
        builderMapper.put(com.amazonaws.services.dynamodbv2.model.InternalServerErrorException.class, (AwsServiceException.Builder)InternalServerErrorException.builder());
        builderMapper.put(ItemCollectionSizeLimitExceededException.class, (AwsServiceException.Builder)software.amazon.awssdk.services.dynamodb.model.ItemCollectionSizeLimitExceededException.builder());
        builderMapper.put(com.amazonaws.services.dynamodbv2.model.LimitExceededException.class, (AwsServiceException.Builder)LimitExceededException.builder());
        builderMapper.put(com.amazonaws.services.dynamodbv2.model.ProvisionedThroughputExceededException.class, (AwsServiceException.Builder)ProvisionedThroughputExceededException.builder());
        builderMapper.put(ResourceInUseException.class, (AwsServiceException.Builder)software.amazon.awssdk.services.dynamodb.model.ResourceInUseException.builder());
        builderMapper.put(ResourceNotFoundException.class, (AwsServiceException.Builder)software.amazon.awssdk.services.dynamodb.model.ResourceNotFoundException.builder());
        builderMapper.put(com.amazonaws.services.dynamodbv2.model.TransactionConflictException.class, (AwsServiceException.Builder)TransactionConflictException.builder());
        builderMapper.put(com.amazonaws.services.dynamodbv2.model.IdempotentParameterMismatchException.class, (AwsServiceException.Builder)IdempotentParameterMismatchException.builder());
        builderMapper.put(com.amazonaws.services.dynamodbv2.model.TrimmedDataAccessException.class, (AwsServiceException.Builder)TrimmedDataAccessException.builder());
        builderMapper.put(com.amazonaws.services.dynamodbv2.model.DuplicateItemException.class, (AwsServiceException.Builder)DuplicateItemException.builder());
    }
}

