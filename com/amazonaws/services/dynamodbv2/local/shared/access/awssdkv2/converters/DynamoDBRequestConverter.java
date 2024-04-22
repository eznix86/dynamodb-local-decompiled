/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  com.amazonaws.AmazonWebServiceRequest
 *  software.amazon.awssdk.services.dynamodb.model.DynamoDbRequest
 *  software.amazon.awssdk.services.dynamodb.model.DynamoDbRequest$Builder
 */
package com.amazonaws.services.dynamodbv2.local.shared.access.awssdkv2.converters;

import com.amazonaws.AmazonWebServiceRequest;
import com.amazonaws.services.dynamodbv2.local.google.Function;
import com.amazonaws.services.dynamodbv2.local.shared.access.awssdkv2.converters.RequestConverter;
import com.amazonaws.services.dynamodbv2.local.shared.exceptions.LocalDBClientExceptionType;
import com.fasterxml.jackson.databind.ObjectMapper;
import software.amazon.awssdk.services.dynamodb.model.DynamoDbRequest;

public class DynamoDBRequestConverter<DDBREQ_SDKV1 extends AmazonWebServiceRequest, DDBREQ_SDKV2 extends DynamoDbRequest, DDBREQ_SDKV2_BUILDER extends DynamoDbRequest.Builder>
extends RequestConverter<DDBREQ_SDKV1, DDBREQ_SDKV2, DDBREQ_SDKV2_BUILDER> {
    public DynamoDBRequestConverter(ObjectMapper objectMapper) {
        super(objectMapper, LocalDBClientExceptionType.DDB_REQUEST_SERIALIZATION_EXCEPTION);
    }

    @Override
    public DDBREQ_SDKV1 toSdkV1(DDBREQ_SDKV2 ddbreq_sdkv2, Function<DDBREQ_SDKV2, DDBREQ_SDKV2_BUILDER> builderFunction, Class<DDBREQ_SDKV1> clazz) {
        return (DDBREQ_SDKV1)((AmazonWebServiceRequest)super.toSdkV1(ddbreq_sdkv2, builderFunction, clazz));
    }
}

