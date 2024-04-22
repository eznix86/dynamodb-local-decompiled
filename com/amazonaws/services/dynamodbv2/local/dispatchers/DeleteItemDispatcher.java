/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  com.amazonaws.services.dynamodbv2.model.DeleteItemRequest
 */
package com.amazonaws.services.dynamodbv2.local.dispatchers;

import com.amazonaws.services.dynamodbv2.local.dispatchers.Dispatcher;
import com.amazonaws.services.dynamodbv2.local.server.DynamoDBRequestHandler;
import com.amazonaws.services.dynamodbv2.model.DeleteItemRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class DeleteItemDispatcher
extends Dispatcher<DeleteItemRequest> {
    public DeleteItemDispatcher(ObjectMapper o2) {
        super(o2);
    }

    @Override
    public byte[] enact(String accessKey, String region, DeleteItemRequest r, DynamoDBRequestHandler h) throws JsonProcessingException {
        return this.jsonMapper.writeValueAsBytes(h.deleteItem(accessKey, region, r));
    }
}

