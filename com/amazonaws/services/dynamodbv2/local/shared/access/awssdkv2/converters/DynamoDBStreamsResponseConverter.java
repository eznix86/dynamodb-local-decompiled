/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  com.amazonaws.AmazonWebServiceResult
 *  com.amazonaws.ResponseMetadata
 *  software.amazon.awssdk.services.dynamodb.model.DynamoDbStreamsResponse
 *  software.amazon.awssdk.services.dynamodb.model.DynamoDbStreamsResponse$Builder
 */
package com.amazonaws.services.dynamodbv2.local.shared.access.awssdkv2.converters;

import com.amazonaws.AmazonWebServiceResult;
import com.amazonaws.ResponseMetadata;
import com.amazonaws.services.dynamodbv2.local.google.Function;
import com.amazonaws.services.dynamodbv2.local.shared.access.awssdkv2.converters.ResponseConverter;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.function.Supplier;
import software.amazon.awssdk.services.dynamodb.model.DynamoDbStreamsResponse;

public class DynamoDBStreamsResponseConverter<DDBSTREAMSRES_SDKV1 extends AmazonWebServiceResult<ResponseMetadata>, DDBSTREAMSRES_SDKV2 extends DynamoDbStreamsResponse, DDBSTREAMSRES_SDKV2_BUILDER extends DynamoDbStreamsResponse.Builder>
extends ResponseConverter<DDBSTREAMSRES_SDKV1, DDBSTREAMSRES_SDKV2, DDBSTREAMSRES_SDKV2_BUILDER> {
    public DynamoDBStreamsResponseConverter(ObjectMapper objectMapper) {
        super(objectMapper);
    }

    @Override
    public DDBSTREAMSRES_SDKV2 toSdkV2(DDBSTREAMSRES_SDKV1 res_sdkv1, Supplier<Class<? extends DDBSTREAMSRES_SDKV2_BUILDER>> builderClass, Function<DDBSTREAMSRES_SDKV2_BUILDER, DDBSTREAMSRES_SDKV2> builderFunction) {
        return (DDBSTREAMSRES_SDKV2)((DynamoDbStreamsResponse)super.toSdkV2(res_sdkv1, builderClass, builderFunction));
    }
}

