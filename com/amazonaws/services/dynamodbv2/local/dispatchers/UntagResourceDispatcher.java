/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  com.amazonaws.services.dynamodbv2.model.UntagResourceRequest
 */
package com.amazonaws.services.dynamodbv2.local.dispatchers;

import com.amazonaws.services.dynamodbv2.local.dispatchers.Dispatcher;
import com.amazonaws.services.dynamodbv2.local.server.DynamoDBRequestHandler;
import com.amazonaws.services.dynamodbv2.model.UntagResourceRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class UntagResourceDispatcher
extends Dispatcher<UntagResourceRequest> {
    public UntagResourceDispatcher(ObjectMapper o2) {
        super(o2);
    }

    @Override
    public byte[] enact(String accessKey, String region, UntagResourceRequest r, DynamoDBRequestHandler h) throws JsonProcessingException {
        return this.jsonMapper.writeValueAsBytes(h.untagResource(accessKey, region, r));
    }
}

