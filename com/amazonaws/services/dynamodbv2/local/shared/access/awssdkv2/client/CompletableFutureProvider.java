/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  software.amazon.awssdk.core.SdkRequest
 *  software.amazon.awssdk.core.SdkResponse
 */
package com.amazonaws.services.dynamodbv2.local.shared.access.awssdkv2.client;

import com.amazonaws.services.dynamodbv2.exceptions.DynamoDBLocalServiceException;
import com.amazonaws.services.dynamodbv2.local.embedded.DDBExceptionMappingInvocationHandler;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.function.Function;
import software.amazon.awssdk.core.SdkRequest;
import software.amazon.awssdk.core.SdkResponse;

class CompletableFutureProvider {
    CompletableFutureProvider() {
    }

    <T extends SdkRequest, R extends SdkResponse> CompletableFuture<R> completableFuture(T request, Function<T, R> syncClientFunction) {
        return ((CompletableFuture)CompletableFuture.supplyAsync(() -> request).thenApplyAsync(syncClientFunction)).exceptionally(ex -> {
            Throwable cause = ex.getCause();
            if (cause.getClass().equals(DynamoDBLocalServiceException.class)) {
                try {
                    DDBExceptionMappingInvocationHandler.handleDynamoDBLocalServiceException((DynamoDBLocalServiceException)((Object)((Object)cause)), true);
                } catch (Throwable e) {
                    throw new CompletionException(e);
                }
            }
            throw new CompletionException((Throwable)ex);
        });
    }
}

