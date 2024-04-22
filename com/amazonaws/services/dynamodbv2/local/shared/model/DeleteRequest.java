/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazonaws.services.dynamodbv2.local.shared.model;

import com.amazonaws.services.dynamodbv2.local.shared.model.AttributeValue;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class DeleteRequest
implements Serializable {
    private Map<String, AttributeValue> key;

    public DeleteRequest() {
    }

    public DeleteRequest(Map<String, AttributeValue> key) {
        this.setKey(key);
    }

    public Map<String, AttributeValue> getKey() {
        return this.key;
    }

    public void setKey(Map<String, AttributeValue> key) {
        this.key = key;
    }

    public DeleteRequest withKey(Map<String, AttributeValue> key) {
        this.setKey(key);
        return this;
    }

    public void setKey(Map.Entry<String, AttributeValue> hashKey, Map.Entry<String, AttributeValue> rangeKey) throws IllegalArgumentException {
        HashMap<String, AttributeValue> key = new HashMap<String, AttributeValue>();
        if (hashKey == null) {
            throw new IllegalArgumentException("hashKey must be non-null object.");
        }
        key.put(hashKey.getKey(), hashKey.getValue());
        if (rangeKey != null) {
            key.put(rangeKey.getKey(), rangeKey.getValue());
        }
        this.setKey(key);
    }

    public DeleteRequest withKey(Map.Entry<String, AttributeValue> hashKey, Map.Entry<String, AttributeValue> rangeKey) throws IllegalArgumentException {
        this.setKey(hashKey, rangeKey);
        return this;
    }

    public DeleteRequest addKeyEntry(String key, AttributeValue value) {
        if (null == this.key) {
            this.key = new HashMap<String, AttributeValue>();
        }
        if (this.key.containsKey(key)) {
            throw new IllegalArgumentException("Duplicated keys (" + key + ") are provided.");
        }
        this.key.put(key, value);
        return this;
    }

    public DeleteRequest clearKeyEntries() {
        this.key = null;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (this.getKey() != null) {
            sb.append("Key: ").append(this.getKey());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        int prime = 31;
        int hashCode = 1;
        hashCode = 31 * hashCode + (this.getKey() == null ? 0 : this.getKey().hashCode());
        return hashCode;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof DeleteRequest)) {
            return false;
        }
        DeleteRequest other = (DeleteRequest)obj;
        if (other.getKey() == null ^ this.getKey() == null) {
            return false;
        }
        return other.getKey() == null || other.getKey().equals(this.getKey());
    }
}

