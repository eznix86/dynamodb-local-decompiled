/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  com.amazonaws.services.dynamodbv2.model.AttributeAction
 */
package com.amazonaws.services.dynamodbv2.local.shared.model;

import com.amazonaws.services.dynamodbv2.local.shared.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.AttributeAction;
import java.io.Serializable;

public class AttributeValueUpdate
implements Serializable {
    private AttributeValue value;
    private String action;

    public AttributeValueUpdate() {
    }

    public AttributeValueUpdate(AttributeValue value, String action) {
        this.setValue(value);
        this.setAction(action);
    }

    public AttributeValueUpdate(AttributeValue value, AttributeAction action) {
        this.value = value;
        this.action = action.toString();
    }

    public AttributeValue getValue() {
        return this.value;
    }

    public void setValue(AttributeValue value) {
        this.value = value;
    }

    public AttributeValueUpdate withValue(AttributeValue value) {
        this.value = value;
        return this;
    }

    public String getAction() {
        return this.action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public void setAction(AttributeAction action) {
        this.action = action.toString();
    }

    public AttributeValueUpdate withAction(String action) {
        this.action = action;
        return this;
    }

    public AttributeValueUpdate withAction(AttributeAction action) {
        this.action = action.toString();
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (this.getValue() != null) {
            sb.append("Value: ").append(this.getValue()).append(",");
        }
        if (this.getAction() != null) {
            sb.append("Action: ").append(this.getAction());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        int prime = 31;
        int hashCode = 1;
        hashCode = 31 * hashCode + (this.getValue() == null ? 0 : this.getValue().hashCode());
        hashCode = 31 * hashCode + (this.getAction() == null ? 0 : this.getAction().hashCode());
        return hashCode;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof AttributeValueUpdate)) {
            return false;
        }
        AttributeValueUpdate other = (AttributeValueUpdate)obj;
        if (other.getValue() == null ^ this.getValue() == null) {
            return false;
        }
        if (other.getValue() != null && !other.getValue().equals(this.getValue())) {
            return false;
        }
        if (other.getAction() == null ^ this.getAction() == null) {
            return false;
        }
        return other.getAction() == null || other.getAction().equals(this.getAction());
    }
}

