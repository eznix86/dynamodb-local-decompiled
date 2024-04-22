/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  com.amazonaws.AmazonServiceException
 *  com.amazonaws.services.dynamodbv2.model.CancellationReason
 */
package com.amazonaws.services.dynamodbv2.exceptions;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.dynamodbv2.local.shared.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.CancellationReason;
import java.util.List;
import java.util.Map;

public class DynamoDBLocalServiceException
extends AmazonServiceException {
    private final String localMessage;
    private final List<CancellationReason> cancellationReasons;
    private final Map<String, AttributeValue> item;

    public DynamoDBLocalServiceException(String message) {
        this(message, null, null);
    }

    public DynamoDBLocalServiceException(String message, List<CancellationReason> cancellationReasons, Map<String, AttributeValue> item) {
        super(message);
        this.localMessage = message;
        this.cancellationReasons = cancellationReasons;
        this.item = item;
    }

    public String getMessage() {
        return this.localMessage;
    }

    public List<CancellationReason> getCancellationReasons() {
        return this.cancellationReasons;
    }

    public Map<String, AttributeValue> getItem() {
        return this.item;
    }
}

