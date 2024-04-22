/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  com.amazonaws.AmazonServiceException
 *  com.amazonaws.services.dynamodbv2.AmazonDynamoDB
 *  com.amazonaws.services.dynamodbv2.AmazonDynamoDBStreams
 *  com.amazonaws.services.dynamodbv2.model.AttributeValue
 *  com.amazonaws.services.dynamodbv2.model.ConditionalCheckFailedException
 *  com.amazonaws.services.dynamodbv2.model.TransactionCanceledException
 *  software.amazon.awssdk.services.dynamodb.DynamoDbAsyncClient
 *  software.amazon.awssdk.services.dynamodb.DynamoDbClient
 *  software.amazon.awssdk.services.dynamodb.streams.DynamoDbStreamsAsyncClient
 *  software.amazon.awssdk.services.dynamodb.streams.DynamoDbStreamsClient
 */
package com.amazonaws.services.dynamodbv2.local.embedded;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBStreams;
import com.amazonaws.services.dynamodbv2.exceptions.AmazonServiceExceptionType;
import com.amazonaws.services.dynamodbv2.exceptions.DynamoDBLocalServiceException;
import com.amazonaws.services.dynamodbv2.local.shared.access.LocalDBClient;
import com.amazonaws.services.dynamodbv2.local.shared.access.awssdkv2.converters.AwsServiceExceptionConverter;
import com.amazonaws.services.dynamodbv2.local.shared.access.sqlite.SQLiteDBAccessUtils;
import com.amazonaws.services.dynamodbv2.local.shared.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ConditionalCheckFailedException;
import com.amazonaws.services.dynamodbv2.model.TransactionCanceledException;
import com.google.common.annotations.VisibleForTesting;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.Collectors;
import software.amazon.awssdk.services.dynamodb.DynamoDbAsyncClient;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.streams.DynamoDbStreamsAsyncClient;
import software.amazon.awssdk.services.dynamodb.streams.DynamoDbStreamsClient;

public class DDBExceptionMappingInvocationHandler
implements InvocationHandler {
    private final LocalDBClient impl;
    private final ReentrantReadWriteLock shutdownLock = new ReentrantReadWriteLock();
    private boolean isShutdown = false;

    DDBExceptionMappingInvocationHandler(LocalDBClient impl) {
        this.impl = impl;
    }

    @VisibleForTesting
    static com.amazonaws.services.dynamodbv2.model.AttributeValue convertAttributeValue(AttributeValue sourceAttributeValue) {
        com.amazonaws.services.dynamodbv2.model.AttributeValue targetAttributeValue = new com.amazonaws.services.dynamodbv2.model.AttributeValue();
        switch (sourceAttributeValue.getType()) {
            case S: {
                targetAttributeValue.setS(sourceAttributeValue.getSValue());
                break;
            }
            case N: {
                targetAttributeValue.setN(sourceAttributeValue.getNValue().toString());
                break;
            }
            case B: {
                targetAttributeValue.setB(sourceAttributeValue.getB());
                break;
            }
            case SS: {
                targetAttributeValue.setSS(sourceAttributeValue.getSS());
                break;
            }
            case NS: {
                targetAttributeValue.setNS(sourceAttributeValue.getNS());
                break;
            }
            case BS: {
                targetAttributeValue.setBS(sourceAttributeValue.getBS());
                break;
            }
            case M: {
                Map<String, com.amazonaws.services.dynamodbv2.model.AttributeValue> m = sourceAttributeValue.getM().entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, e -> SQLiteDBAccessUtils.convertLocalAttributeValue((AttributeValue)e.getValue())));
                targetAttributeValue.setM(m);
                break;
            }
            case L: {
                List l = sourceAttributeValue.getL().stream().map(SQLiteDBAccessUtils::convertLocalAttributeValue).collect(Collectors.toList());
                targetAttributeValue.setL(l);
                break;
            }
            case NULL: {
                targetAttributeValue.setNULL(sourceAttributeValue.getNULL());
                break;
            }
            case BOOL: {
                targetAttributeValue.setBOOL(sourceAttributeValue.getBOOL());
                break;
            }
            default: {
                throw new RuntimeException("Unknown type: " + sourceAttributeValue.getType());
            }
        }
        return targetAttributeValue;
    }

    public static void handleDynamoDBLocalServiceException(DynamoDBLocalServiceException localServiceException, boolean isAWSSdkV2) throws Throwable {
        AmazonServiceException amazonServiceException;
        AmazonServiceExceptionType exceptionType = AmazonServiceExceptionType.valueOfErrorCode(localServiceException.getErrorCode());
        Class<? extends AmazonServiceException> clientException = exceptionType.getClientClass();
        if (AmazonServiceException.class.equals(clientException)) {
            amazonServiceException = new AmazonServiceException(localServiceException.getMessage());
        } else if (TransactionCanceledException.class.equals(clientException)) {
            TransactionCanceledException tce = new TransactionCanceledException(localServiceException.getMessage());
            tce.setCancellationReasons(localServiceException.getCancellationReasons());
            amazonServiceException = tce;
        } else if (ConditionalCheckFailedException.class.equals(clientException)) {
            ConditionalCheckFailedException conditionalCheckFailedException = new ConditionalCheckFailedException(localServiceException.getMessage());
            if (localServiceException.getItem() != null) {
                Map<String, com.amazonaws.services.dynamodbv2.model.AttributeValue> items = localServiceException.getItem().entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, e -> DDBExceptionMappingInvocationHandler.convertAttributeValue((AttributeValue)e.getValue())));
                conditionalCheckFailedException.setItem(items);
            }
            amazonServiceException = conditionalCheckFailedException;
        } else {
            Constructor<? extends AmazonServiceException> constructor = clientException.getConstructor(String.class);
            amazonServiceException = constructor.newInstance(localServiceException.getMessage());
        }
        DDBExceptionMappingInvocationHandler.copyAmazonServiceExceptionFields(amazonServiceException, localServiceException);
        throw isAWSSdkV2 ? AwsServiceExceptionConverter.convert(amazonServiceException) : amazonServiceException;
    }

    private static void copyAmazonServiceExceptionFields(AmazonServiceException dst, AmazonServiceException src) {
        if (src == null || dst == null) {
            return;
        }
        dst.setErrorCode(src.getErrorCode());
        dst.setStatusCode(src.getStatusCode());
        dst.setRequestId(src.getRequestId());
        dst.setServiceName(src.getServiceName());
        dst.setErrorType(src.getErrorType());
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args2) throws Throwable {
        if (method.getName().equals("shutdown") || method.getName().equals("shutdownNow") || method.getName().equals("close")) {
            this.shutdownLock.writeLock().lock();
            try {
                List<Runnable> runnables = null;
                if (!this.isShutdown) {
                    if (method.getName().equals("shutdownNow")) {
                        runnables = this.impl.shutdownNow();
                    } else {
                        this.impl.shutdown();
                    }
                    this.isShutdown = true;
                }
                List<Runnable> list = runnables;
                return list;
            } finally {
                this.shutdownLock.writeLock().unlock();
            }
        }
        this.shutdownLock.readLock().lock();
        try {
            if (!this.isShutdown) {
                Object result = null;
                boolean isAWSSdkV2 = false;
                try {
                    if (AmazonDynamoDB.class.equals(method.getDeclaringClass())) {
                        result = method.invoke(this.impl.amazonDynamoDB(), args2);
                    } else if (AmazonDynamoDBStreams.class.equals(method.getDeclaringClass())) {
                        result = method.invoke(this.impl.amazonDynamoDBStreams(), args2);
                    } else if (DynamoDbClient.class.equals(method.getDeclaringClass())) {
                        isAWSSdkV2 = true;
                        result = method.invoke(this.impl.dynamoDbClient(), args2);
                    } else if (DynamoDbAsyncClient.class.equals(method.getDeclaringClass())) {
                        isAWSSdkV2 = true;
                        result = method.invoke(this.impl.dynamoDbAsyncClient(), args2);
                    } else if (DynamoDbStreamsClient.class.equals(method.getDeclaringClass())) {
                        isAWSSdkV2 = true;
                        result = method.invoke(this.impl.dynamoDbStreamsClient(), args2);
                    } else if (DynamoDbStreamsAsyncClient.class.equals(method.getDeclaringClass())) {
                        isAWSSdkV2 = true;
                        result = method.invoke(this.impl.dynamoDbAsyncClient(), args2);
                    } else {
                        result = method.invoke(this.impl, args2);
                    }
                    Object object = result;
                    return object;
                } catch (InvocationTargetException ie) {
                    Throwable e = ie.getTargetException();
                    if (e.getClass().isAssignableFrom(DynamoDBLocalServiceException.class)) {
                        DDBExceptionMappingInvocationHandler.handleDynamoDBLocalServiceException((DynamoDBLocalServiceException)((Object)e), isAWSSdkV2);
                    }
                    throw e;
                }
            }
            throw new AmazonServiceException("Embedded server is shut down");
        } finally {
            this.shutdownLock.readLock().unlock();
        }
    }
}

