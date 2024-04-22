/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  com.amazonaws.AmazonWebServiceResult
 *  com.amazonaws.ResponseMetadata
 *  software.amazon.awssdk.services.dynamodb.model.DynamoDbResponse
 *  software.amazon.awssdk.services.dynamodb.model.DynamoDbResponse$Builder
 */
package com.amazonaws.services.dynamodbv2.local.shared.access.awssdkv2.converters;

import com.amazonaws.AmazonWebServiceResult;
import com.amazonaws.ResponseMetadata;
import com.amazonaws.services.dynamodbv2.local.google.Function;
import com.amazonaws.services.dynamodbv2.local.shared.access.awssdkv2.converters.ResponseConverter;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.function.Supplier;
import software.amazon.awssdk.services.dynamodb.model.DynamoDbResponse;

public class DynamoDBResponseConverter<DDBRES_SDKV1 extends AmazonWebServiceResult<ResponseMetadata>, DDBRES_SDKV2 extends DynamoDbResponse, DDBRES_SDKV2_BUILDER extends DynamoDbResponse.Builder>
extends ResponseConverter<DDBRES_SDKV1, DDBRES_SDKV2, DDBRES_SDKV2_BUILDER> {
    public DynamoDBResponseConverter(ObjectMapper objectMapper) {
        super(objectMapper);
    }

    @Override
    public DDBRES_SDKV2 toSdkV2(DDBRES_SDKV1 res_sdkv1, Supplier<Class<? extends DDBRES_SDKV2_BUILDER>> builderClass, Function<DDBRES_SDKV2_BUILDER, DDBRES_SDKV2> builderFunction) {
        return (DDBRES_SDKV2)((DynamoDbResponse)super.toSdkV2(res_sdkv1, builderClass, builderFunction));
    }
}

