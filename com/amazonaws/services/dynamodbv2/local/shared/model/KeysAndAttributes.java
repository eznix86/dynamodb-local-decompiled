/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  com.amazonaws.internal.ListWithAutoConstructFlag
 */
package com.amazonaws.services.dynamodbv2.local.shared.model;

import com.amazonaws.internal.ListWithAutoConstructFlag;
import com.amazonaws.services.dynamodbv2.local.shared.model.AttributeValue;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class KeysAndAttributes
implements Serializable {
    private ListWithAutoConstructFlag<Map<String, AttributeValue>> keys;
    private ListWithAutoConstructFlag<String> attributesToGet;
    private Boolean consistentRead;
    private String projectionExpression;
    private Map<String, String> expressionAttributeNames;

    public List<Map<String, AttributeValue>> getKeys() {
        return this.keys;
    }

    public void setKeys(Collection<Map<String, AttributeValue>> keys2) {
        if (keys2 == null) {
            this.keys = null;
            return;
        }
        ListWithAutoConstructFlag keysCopy = new ListWithAutoConstructFlag(keys2.size());
        keysCopy.addAll(keys2);
        this.keys = keysCopy;
    }

    public KeysAndAttributes withKeys(Map<String, AttributeValue> ... keys2) {
        if (this.getKeys() == null) {
            this.setKeys(new ArrayList<Map<String, AttributeValue>>(keys2.length));
        }
        for (Map<String, AttributeValue> value : keys2) {
            this.getKeys().add(value);
        }
        return this;
    }

    public KeysAndAttributes withKeys(Collection<Map<String, AttributeValue>> keys2) {
        if (keys2 == null) {
            this.keys = null;
        } else {
            ListWithAutoConstructFlag keysCopy = new ListWithAutoConstructFlag(keys2.size());
            keysCopy.addAll(keys2);
            this.keys = keysCopy;
        }
        return this;
    }

    public List<String> getAttributesToGet() {
        return this.attributesToGet;
    }

    public void setAttributesToGet(Collection<String> attributesToGet) {
        if (attributesToGet == null) {
            this.attributesToGet = null;
            return;
        }
        ListWithAutoConstructFlag attributesToGetCopy = new ListWithAutoConstructFlag(attributesToGet.size());
        attributesToGetCopy.addAll(attributesToGet);
        this.attributesToGet = attributesToGetCopy;
    }

    public KeysAndAttributes withAttributesToGet(String ... attributesToGet) {
        if (this.getAttributesToGet() == null) {
            this.setAttributesToGet(new ArrayList<String>(attributesToGet.length));
        }
        for (String value : attributesToGet) {
            this.getAttributesToGet().add(value);
        }
        return this;
    }

    public KeysAndAttributes withAttributesToGet(Collection<String> attributesToGet) {
        if (attributesToGet == null) {
            this.attributesToGet = null;
        } else {
            ListWithAutoConstructFlag attributesToGetCopy = new ListWithAutoConstructFlag(attributesToGet.size());
            attributesToGetCopy.addAll(attributesToGet);
            this.attributesToGet = attributesToGetCopy;
        }
        return this;
    }

    public Boolean isConsistentRead() {
        return this.consistentRead;
    }

    public KeysAndAttributes withConsistentRead(Boolean consistentRead) {
        this.consistentRead = consistentRead;
        return this;
    }

    public Boolean getConsistentRead() {
        return this.consistentRead;
    }

    public void setConsistentRead(Boolean consistentRead) {
        this.consistentRead = consistentRead;
    }

    public String getProjectionExpression() {
        return this.projectionExpression;
    }

    public void setProjectionExpression(String projectionExpression) {
        this.projectionExpression = projectionExpression;
    }

    public KeysAndAttributes withProjectionExpression(String projectionExpression) {
        this.projectionExpression = projectionExpression;
        return this;
    }

    public Map<String, String> getExpressionAttributeNames() {
        return this.expressionAttributeNames;
    }

    public void setExpressionAttributeNames(Map<String, String> expressionAttributeNames) {
        this.expressionAttributeNames = expressionAttributeNames;
    }

    public KeysAndAttributes withExpressionAttributeNames(Map<String, String> expressionAttributeNames) {
        this.expressionAttributeNames = expressionAttributeNames;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (this.getKeys() != null) {
            sb.append("Keys: ").append(this.getKeys()).append(",");
        }
        if (this.getAttributesToGet() != null) {
            sb.append("AttributesToGet: ").append(this.getAttributesToGet()).append(",");
        }
        if (this.isConsistentRead() != null) {
            sb.append("ConsistentRead: ").append(this.isConsistentRead());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        int prime = 31;
        int hashCode = 1;
        hashCode = 31 * hashCode + (this.getKeys() == null ? 0 : this.getKeys().hashCode());
        hashCode = 31 * hashCode + (this.getAttributesToGet() == null ? 0 : this.getAttributesToGet().hashCode());
        hashCode = 31 * hashCode + (this.isConsistentRead() == null ? 0 : this.isConsistentRead().hashCode());
        return hashCode;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof KeysAndAttributes)) {
            return false;
        }
        KeysAndAttributes other = (KeysAndAttributes)obj;
        if (other.getKeys() == null ^ this.getKeys() == null) {
            return false;
        }
        if (other.getKeys() != null && !other.getKeys().equals(this.getKeys())) {
            return false;
        }
        if (other.getAttributesToGet() == null ^ this.getAttributesToGet() == null) {
            return false;
        }
        if (other.getAttributesToGet() != null && !other.getAttributesToGet().equals(this.getAttributesToGet())) {
            return false;
        }
        if (other.isConsistentRead() == null ^ this.isConsistentRead() == null) {
            return false;
        }
        return other.isConsistentRead() == null || other.isConsistentRead().equals(this.isConsistentRead());
    }
}

