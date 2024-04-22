/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazonaws.services.dynamodbv2.local.shared.access.awssdkv2.client;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public abstract class ClientSdkV2Base {
    protected final ObjectMapper objectMapper = new ObjectMapper();

    public ClientSdkV2Base() {
        this.objectMapper.registerModule(new JavaTimeModule());
        this.objectMapper.configure(JsonGenerator.Feature.IGNORE_UNKNOWN, true);
        this.objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }
}

