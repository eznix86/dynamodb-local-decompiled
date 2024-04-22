/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazonaws.services.dynamodbv2.local.shared.model;

import com.amazonaws.services.dynamodbv2.local.shared.model.AttributeValue;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class PutRequest
implements Serializable {
    private Map<String, AttributeValue> item;

    public PutRequest() {
    }

    public PutRequest(Map<String, AttributeValue> item) {
        this.setItem(item);
    }

    public Map<String, AttributeValue> getItem() {
        return this.item;
    }

    public void setItem(Map<String, AttributeValue> item) {
        this.item = item;
    }

    public PutRequest withItem(Map<String, AttributeValue> item) {
        this.setItem(item);
        return this;
    }

    public PutRequest addItemEntry(String key, AttributeValue value) {
        if (null == this.item) {
            this.item = new HashMap<String, AttributeValue>();
        }
        if (this.item.containsKey(key)) {
            throw new IllegalArgumentException("Duplicated keys (" + key + ") are provided.");
        }
        this.item.put(key, value);
        return this;
    }

    public PutRequest clearItemEntries() {
        this.item = null;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (this.getItem() != null) {
            sb.append("Item: ").append(this.getItem());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        int prime = 31;
        int hashCode = 1;
        hashCode = 31 * hashCode + (this.getItem() == null ? 0 : this.getItem().hashCode());
        return hashCode;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof PutRequest)) {
            return false;
        }
        PutRequest other = (PutRequest)obj;
        if (other.getItem() == null ^ this.getItem() == null) {
            return false;
        }
        return other.getItem() == null || other.getItem().equals(this.getItem());
    }
}

