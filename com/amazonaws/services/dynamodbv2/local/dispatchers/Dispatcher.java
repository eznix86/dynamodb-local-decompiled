/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazonaws.services.dynamodbv2.local.dispatchers;

import com.amazonaws.services.dynamodbv2.local.server.DynamoDBRequestHandler;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public abstract class Dispatcher<T> {
    protected final ObjectMapper jsonMapper;

    public Dispatcher(ObjectMapper o2) {
        this.jsonMapper = o2;
    }

    public abstract byte[] enact(String var1, String var2, T var3, DynamoDBRequestHandler var4) throws JsonProcessingException;
}

