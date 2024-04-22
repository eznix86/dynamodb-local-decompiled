/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  com.amazonaws.services.dynamodbv2.model.CancellationReason
 */
package com.amazonaws.services.dynamodbv2.local.exceptions;

import com.amazonaws.services.dynamodbv2.exceptions.AmazonServiceExceptionType;
import com.amazonaws.services.dynamodbv2.local.shared.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.CancellationReason;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import java.util.Map;

@JsonInclude(value=JsonInclude.Include.NON_NULL)
public class ExceptionBean {
    protected static final String AWS_EXCEPTION_TYPE_PREFIX = "com.amazonaws.dynamodb.v20120810#";
    protected static final String AWS_CORAL_EXCEPTION_TYPE_PREFIX = "com.amazon.coral.validate#";
    private String type = null;
    private List<CancellationReason> cancellationReasons = null;
    private String message = null;
    private Map<String, AttributeValue> item;

    public ExceptionBean(String t, String m) {
        this(t, m, null, null);
    }

    public ExceptionBean(String t, String m, List<CancellationReason> cancellationReasons, Map<String, AttributeValue> item) {
        if (t != null) {
            this.type = (t.equals("ValidationException") ? AWS_CORAL_EXCEPTION_TYPE_PREFIX : AWS_EXCEPTION_TYPE_PREFIX) + t;
        }
        this.message = m;
        this.cancellationReasons = cancellationReasons;
        this.item = item;
    }

    public ExceptionBean(AmazonServiceExceptionType t) {
        this(t.getErrorCode(), t.getMessage());
    }

    @JsonProperty(value="__type")
    public String getType() {
        return this.type;
    }

    @JsonProperty(value="__type")
    public void setType(String type) {
        this.type = type;
    }

    @JsonProperty(value="Message")
    public String getMessage() {
        return this.message;
    }

    @JsonProperty(value="Message")
    public void setMessage(String message) {
        this.message = message;
    }

    @JsonProperty(value="CancellationReasons")
    public List<CancellationReason> getCancellationReasons() {
        return this.cancellationReasons;
    }

    @JsonProperty(value="CancellationReasons")
    public void setCancellationReasons(List<CancellationReason> cancellationReasons) {
        this.cancellationReasons = cancellationReasons;
    }

    @JsonProperty(value="Item")
    public Map<String, AttributeValue> getItem() {
        return this.item;
    }

    @JsonProperty(value="Item")
    public void setItem(Map<String, AttributeValue> item) {
        this.item = item;
    }
}

