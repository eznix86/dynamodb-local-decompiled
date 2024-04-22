/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  com.amazonaws.internal.ListWithAutoConstructFlag
 *  com.amazonaws.services.dynamodbv2.model.ComparisonOperator
 */
package com.amazonaws.services.dynamodbv2.local.shared.model;

import com.amazonaws.internal.ListWithAutoConstructFlag;
import com.amazonaws.services.dynamodbv2.local.shared.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ComparisonOperator;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Condition
implements Serializable {
    private ListWithAutoConstructFlag<AttributeValue> attributeValueList;
    private String comparisonOperator;

    public List<AttributeValue> getAttributeValueList() {
        return this.attributeValueList;
    }

    public void setAttributeValueList(Collection<AttributeValue> attributeValueList) {
        if (attributeValueList == null) {
            this.attributeValueList = null;
            return;
        }
        ListWithAutoConstructFlag attributeValueListCopy = new ListWithAutoConstructFlag(attributeValueList.size());
        attributeValueListCopy.addAll(attributeValueList);
        this.attributeValueList = attributeValueListCopy;
    }

    public Condition withAttributeValueList(AttributeValue ... attributeValueList) {
        if (this.getAttributeValueList() == null) {
            this.setAttributeValueList(new ArrayList<AttributeValue>(attributeValueList.length));
        }
        for (AttributeValue value : attributeValueList) {
            this.getAttributeValueList().add(value);
        }
        return this;
    }

    public Condition withAttributeValueList(Collection<AttributeValue> attributeValueList) {
        if (attributeValueList == null) {
            this.attributeValueList = null;
        } else {
            ListWithAutoConstructFlag attributeValueListCopy = new ListWithAutoConstructFlag(attributeValueList.size());
            attributeValueListCopy.addAll(attributeValueList);
            this.attributeValueList = attributeValueListCopy;
        }
        return this;
    }

    public String getComparisonOperator() {
        return this.comparisonOperator;
    }

    public void setComparisonOperator(String comparisonOperator) {
        this.comparisonOperator = comparisonOperator;
    }

    public void setComparisonOperator(ComparisonOperator comparisonOperator) {
        this.comparisonOperator = comparisonOperator.toString();
    }

    public Condition withComparisonOperator(String comparisonOperator) {
        this.comparisonOperator = comparisonOperator;
        return this;
    }

    public Condition withComparisonOperator(ComparisonOperator comparisonOperator) {
        this.comparisonOperator = comparisonOperator.toString();
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (this.getAttributeValueList() != null) {
            sb.append("AttributeValueList: ").append(this.getAttributeValueList()).append(",");
        }
        if (this.getComparisonOperator() != null) {
            sb.append("ComparisonOperator: ").append(this.getComparisonOperator());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        int prime = 31;
        int hashCode = 1;
        hashCode = 31 * hashCode + (this.getAttributeValueList() == null ? 0 : this.getAttributeValueList().hashCode());
        hashCode = 31 * hashCode + (this.getComparisonOperator() == null ? 0 : this.getComparisonOperator().hashCode());
        return hashCode;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Condition)) {
            return false;
        }
        Condition other = (Condition)obj;
        if (other.getAttributeValueList() == null ^ this.getAttributeValueList() == null) {
            return false;
        }
        if (other.getAttributeValueList() != null && !other.getAttributeValueList().equals(this.getAttributeValueList())) {
            return false;
        }
        if (other.getComparisonOperator() == null ^ this.getComparisonOperator() == null) {
            return false;
        }
        return other.getComparisonOperator() == null || other.getComparisonOperator().equals(this.getComparisonOperator());
    }
}

