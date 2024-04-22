/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  software.amazon.awssdk.awscore.AwsRequestOverrideConfiguration
 *  software.amazon.awssdk.awscore.AwsRequestOverrideConfiguration$Builder
 *  software.amazon.awssdk.core.ApiName
 *  software.amazon.awssdk.services.dynamodb.model.DynamoDbRequest
 */
package com.amazonaws.services.dynamodbv2.local.shared.access.awssdkv2.client;

import java.util.function.Consumer;
import software.amazon.awssdk.awscore.AwsRequestOverrideConfiguration;
import software.amazon.awssdk.core.ApiName;
import software.amazon.awssdk.services.dynamodb.model.DynamoDbRequest;

public class PaginatorUtils {
    public static <T extends DynamoDbRequest> T applyPaginatorUserAgent(T request) {
        Consumer<AwsRequestOverrideConfiguration.Builder> userAgentApplier = b -> b.addApiName(ApiName.builder().version("2.25.31").name("PAGINATED").build());
        AwsRequestOverrideConfiguration overrideConfiguration = request.overrideConfiguration().map(c -> ((AwsRequestOverrideConfiguration.Builder)c.toBuilder().applyMutation(userAgentApplier)).build()).orElse(((AwsRequestOverrideConfiguration.Builder)AwsRequestOverrideConfiguration.builder().applyMutation(userAgentApplier)).build());
        return (T)((DynamoDbRequest)request.toBuilder().overrideConfiguration(overrideConfiguration).build());
    }
}

