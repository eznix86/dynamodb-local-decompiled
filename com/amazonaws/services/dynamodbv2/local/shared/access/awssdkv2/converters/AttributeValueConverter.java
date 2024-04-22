/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  com.amazonaws.services.dynamodbv2.model.AttributeValue
 *  org.apache.logging.log4j.Logger
 *  software.amazon.awssdk.services.dynamodb.model.AttributeValue
 *  software.amazon.awssdk.services.dynamodb.model.AttributeValue$Builder
 */
package com.amazonaws.services.dynamodbv2.local.shared.access.awssdkv2.converters;

import com.amazonaws.services.dynamodbv2.local.shared.access.awssdkv2.converters.DynamoDBStreamsResponseConverter;
import com.amazonaws.services.dynamodbv2.local.shared.exceptions.LocalDBClientException;
import com.amazonaws.services.dynamodbv2.local.shared.exceptions.LocalDBClientExceptionType;
import com.amazonaws.services.dynamodbv2.local.shared.helpers.SerializationUtils;
import com.amazonaws.services.dynamodbv2.local.shared.logging.LogManager;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.util.Map;
import org.apache.logging.log4j.Logger;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

public class AttributeValueConverter {
    static final Logger logger = LogManager.getLogger(DynamoDBStreamsResponseConverter.class);
    ObjectMapper objectMapper = new ObjectMapper();

    public AttributeValueConverter() {
        this.objectMapper.registerModule(new JavaTimeModule());
        this.objectMapper.configure(JsonGenerator.Feature.IGNORE_UNKNOWN, true);
        this.objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public software.amazon.awssdk.services.dynamodb.model.AttributeValue toSdkV2(AttributeValue v1Item) {
        software.amazon.awssdk.services.dynamodb.model.AttributeValue skdV2Item;
        try {
            String serializedV1Item = this.objectMapper.writeValueAsString(v1Item);
            Map<String, Object> mappedV1 = this.objectMapper.readValue(serializedV1Item, new TypeReference<Map<String, Object>>(){});
            Map<String, Object> mappedV2 = SerializationUtils.cloneKeyToNewKey(mappedV1, "null", "nul");
            String serializedV2Item = this.objectMapper.writeValueAsString(mappedV2);
            skdV2Item = (software.amazon.awssdk.services.dynamodb.model.AttributeValue)((AttributeValue.Builder)this.objectMapper.readValue(serializedV2Item, software.amazon.awssdk.services.dynamodb.model.AttributeValue.serializableBuilderClass())).build();
        } catch (JsonProcessingException e) {
            logger.error(LocalDBClientExceptionType.DDB_RESPONSE_SERIALIZATION_EXCEPTION.getMessage(), (Throwable)e);
            throw new LocalDBClientException(LocalDBClientExceptionType.DDB_RESPONSE_SERIALIZATION_EXCEPTION, LocalDBClientExceptionType.DDB_RESPONSE_SERIALIZATION_EXCEPTION.getMessage());
        }
        return skdV2Item;
    }
}

