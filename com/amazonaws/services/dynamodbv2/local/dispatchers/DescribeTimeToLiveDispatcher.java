/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  com.amazonaws.services.dynamodbv2.model.DescribeTimeToLiveRequest
 */
package com.amazonaws.services.dynamodbv2.local.dispatchers;

import com.amazonaws.services.dynamodbv2.local.dispatchers.Dispatcher;
import com.amazonaws.services.dynamodbv2.local.server.DynamoDBRequestHandler;
import com.amazonaws.services.dynamodbv2.model.DescribeTimeToLiveRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class DescribeTimeToLiveDispatcher
extends Dispatcher<DescribeTimeToLiveRequest> {
    public DescribeTimeToLiveDispatcher(ObjectMapper o2) {
        super(o2);
    }

    @Override
    public byte[] enact(String accessKey, String region, DescribeTimeToLiveRequest r, DynamoDBRequestHandler h) throws JsonProcessingException {
        return this.jsonMapper.writeValueAsBytes(h.describeTimeToLive(accessKey, region, r));
    }
}

