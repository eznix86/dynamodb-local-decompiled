/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.apache.logging.log4j.Logger
 */
package com.amazonaws.services.dynamodbv2.local.shared.access.awssdkv2.converters;

import com.amazonaws.services.dynamodbv2.local.google.Function;
import com.amazonaws.services.dynamodbv2.local.shared.exceptions.LocalDBClientException;
import com.amazonaws.services.dynamodbv2.local.shared.exceptions.LocalDBClientExceptionType;
import com.amazonaws.services.dynamodbv2.local.shared.helpers.SerializationUtils;
import com.amazonaws.services.dynamodbv2.local.shared.logging.LogManager;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;
import org.apache.logging.log4j.Logger;

public abstract class RequestConverter<REQ_SDKV1, REQ_SDKV2, REQ_SDKV2_BUILDER> {
    static final Logger logger = LogManager.getLogger(RequestConverter.class);
    protected final ObjectMapper objectMapper;
    private final LocalDBClientExceptionType serializationException;

    public RequestConverter(ObjectMapper objectMapper, LocalDBClientExceptionType serializationException) {
        this.objectMapper = objectMapper;
        this.serializationException = serializationException;
    }

    protected REQ_SDKV1 toSdkV1(REQ_SDKV2 ddbstreamsreq_sdkv2, Function<REQ_SDKV2, REQ_SDKV2_BUILDER> builderFunction, Class<REQ_SDKV1> clazz) {
        REQ_SDKV1 ddbReq_sdkv1;
        try {
            String serializedString = this.objectMapper.writeValueAsString(builderFunction.apply(ddbstreamsreq_sdkv2));
            Map<String, Object> mappedV2 = this.objectMapper.readValue(serializedString, new TypeReference<Map<String, Object>>(){});
            Map<String, Object> mappedV1 = SerializationUtils.cloneKeyToNewKey(mappedV2, "nul", "null");
            String serializedV1 = this.objectMapper.writeValueAsString(mappedV1);
            ddbReq_sdkv1 = this.objectMapper.readValue(serializedV1, clazz);
        } catch (Exception e) {
            logger.error(this.serializationException.getMessage(), (Throwable)e);
            throw new LocalDBClientException(this.serializationException, this.serializationException.getMessage());
        }
        return ddbReq_sdkv1;
    }
}

