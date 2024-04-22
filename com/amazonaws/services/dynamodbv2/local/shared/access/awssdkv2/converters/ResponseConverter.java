/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.apache.logging.log4j.Logger
 */
package com.amazonaws.services.dynamodbv2.local.shared.access.awssdkv2.converters;

import com.amazonaws.services.dynamodbv2.local.google.Function;
import com.amazonaws.services.dynamodbv2.local.shared.access.awssdkv2.converters.DynamoDBStreamsResponseConverter;
import com.amazonaws.services.dynamodbv2.local.shared.exceptions.LocalDBClientException;
import com.amazonaws.services.dynamodbv2.local.shared.exceptions.LocalDBClientExceptionType;
import com.amazonaws.services.dynamodbv2.local.shared.helpers.SerializationUtils;
import com.amazonaws.services.dynamodbv2.local.shared.logging.LogManager;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;
import java.util.function.Supplier;
import org.apache.logging.log4j.Logger;

public class ResponseConverter<RES_SDKV1, RES_SDKV2, RES_SDKV2_BUILDER> {
    static final Logger logger = LogManager.getLogger(DynamoDBStreamsResponseConverter.class);
    protected final ObjectMapper objectMapper;

    public ResponseConverter(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    protected RES_SDKV2 toSdkV2(RES_SDKV1 res_sdkv1, Supplier<Class<? extends RES_SDKV2_BUILDER>> builderClass, Function<RES_SDKV2_BUILDER, RES_SDKV2> builderFunction) {
        try {
            String serialized = this.objectMapper.writeValueAsString(res_sdkv1);
            Map<String, Object> mappedV1 = this.objectMapper.readValue(serialized, new TypeReference<Map<String, Object>>(){});
            Map<String, Object> mappedV2 = SerializationUtils.cloneKeyToNewKey(mappedV1, "null", "nul");
            String serializedV2 = this.objectMapper.writeValueAsString(mappedV2);
            RES_SDKV2_BUILDER res_sdkv2_builder = this.objectMapper.readValue(serializedV2, builderClass.get());
            RES_SDKV2 ddbReq_sdkv2 = builderFunction.apply(res_sdkv2_builder);
            return ddbReq_sdkv2;
        } catch (JsonProcessingException e) {
            logger.error(LocalDBClientExceptionType.DDB_RESPONSE_SERIALIZATION_EXCEPTION.getMessage(), (Throwable)e);
            throw new LocalDBClientException(LocalDBClientExceptionType.DDB_RESPONSE_SERIALIZATION_EXCEPTION, LocalDBClientExceptionType.DDB_RESPONSE_SERIALIZATION_EXCEPTION.getMessage());
        }
    }
}

