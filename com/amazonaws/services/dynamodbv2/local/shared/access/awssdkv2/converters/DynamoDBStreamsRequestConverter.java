/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  com.amazonaws.AmazonWebServiceRequest
 *  software.amazon.awssdk.services.dynamodb.model.DynamoDbStreamsRequest
 *  software.amazon.awssdk.services.dynamodb.model.DynamoDbStreamsRequest$Builder
 */
package com.amazonaws.services.dynamodbv2.local.shared.access.awssdkv2.converters;

import com.amazonaws.AmazonWebServiceRequest;
import com.amazonaws.services.dynamodbv2.local.google.Function;
import com.amazonaws.services.dynamodbv2.local.shared.access.awssdkv2.converters.RequestConverter;
import com.amazonaws.services.dynamodbv2.local.shared.exceptions.LocalDBClientExceptionType;
import com.fasterxml.jackson.databind.ObjectMapper;
import software.amazon.awssdk.services.dynamodb.model.DynamoDbStreamsRequest;

public class DynamoDBStreamsRequestConverter<DDBSTREAMSREQ_SDKV1 extends AmazonWebServiceRequest, DDBSTREAMSREQ_SDKV2 extends DynamoDbStreamsRequest, DDBSTREAMSREQ_SDKV2_BUILDER extends DynamoDbStreamsRequest.Builder>
extends RequestConverter<DDBSTREAMSREQ_SDKV1, DDBSTREAMSREQ_SDKV2, DDBSTREAMSREQ_SDKV2_BUILDER> {
    public DynamoDBStreamsRequestConverter(ObjectMapper objectMapper) {
        super(objectMapper, LocalDBClientExceptionType.DDB_REQUEST_SERIALIZATION_EXCEPTION);
    }

    @Override
    public DDBSTREAMSREQ_SDKV1 toSdkV1(DDBSTREAMSREQ_SDKV2 ddbstreamsreq_sdkv2, Function<DDBSTREAMSREQ_SDKV2, DDBSTREAMSREQ_SDKV2_BUILDER> builderFunction, Class<DDBSTREAMSREQ_SDKV1> clazz) {
        return (DDBSTREAMSREQ_SDKV1)((AmazonWebServiceRequest)super.toSdkV1(ddbstreamsreq_sdkv2, builderFunction, clazz));
    }
}

