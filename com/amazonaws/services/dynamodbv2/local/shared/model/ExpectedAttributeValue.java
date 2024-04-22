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

public class ExpectedAttributeValue
implements Serializable {
    private AttributeValue value;
    private Boolean exists;
    private String comparisonOperator;
    private ListWithAutoConstructFlag<AttributeValue> attributeValueList;

    public ExpectedAttributeValue() {
    }

    public ExpectedAttributeValue(AttributeValue value) {
        this.setValue(value);
    }

    public ExpectedAttributeValue(Boolean exists) {
        this.setExists(exists);
    }

    public AttributeValue getValue() {
        return this.value;
    }

    public void setValue(AttributeValue value) {
        this.value = value;
    }

    public ExpectedAttributeValue withValue(AttributeValue value) {
        this.value = value;
        return this;
    }

    public Boolean isExists() {
        return this.exists;
    }

    public ExpectedAttributeValue withExists(Boolean exists) {
        this.exists = exists;
        return this;
    }

    public Boolean getExists() {
        return this.exists;
    }

    public void setExists(Boolean exists) {
        this.exists = exists;
    }

    public String getComparisonOperator() {
        return this.comparisonOperator;
    }

    public void setComparisonOperator(String comparisonOperator) {
        this.comparisonOperator = comparisonOperator;
    }

    public void setComparisonOperator(ComparisonOperator comparisonOperator) {
        if (comparisonOperator != null) {
            this.comparisonOperator = comparisonOperator.toString();
        }
    }

    public ExpectedAttributeValue withComparisonOperator(String comparisonOperator) {
        this.comparisonOperator = comparisonOperator;
        return this;
    }

    public ExpectedAttributeValue withComparisonOperator(ComparisonOperator comparisonOperator) {
        if (comparisonOperator != null) {
            this.comparisonOperator = comparisonOperator.toString();
        }
        return this;
    }

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

    public ExpectedAttributeValue withAttributeValueList(AttributeValue ... attributeValueList) {
        if (this.getAttributeValueList() == null) {
            this.setAttributeValueList(new ArrayList<AttributeValue>(attributeValueList.length));
        }
        for (AttributeValue value : attributeValueList) {
            this.getAttributeValueList().add(value);
        }
        return this;
    }

    public ExpectedAttributeValue withAttributeValueList(Collection<AttributeValue> attributeValueList) {
        if (attributeValueList == null) {
            this.attributeValueList = null;
        } else {
            ListWithAutoConstructFlag attributeValueListCopy = new ListWithAutoConstructFlag(attributeValueList.size());
            attributeValueListCopy.addAll(attributeValueList);
            this.attributeValueList = attributeValueListCopy;
        }
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (this.getValue() != null) {
            sb.append("Value: ").append(this.getValue()).append(",");
        }
        if (this.isExists() != null) {
            sb.append("Exists: ").append(this.isExists()).append(",");
        }
        if (this.getComparisonOperator() != null) {
            sb.append("ComparisonOperator: ").append(this.getComparisonOperator()).append(",");
        }
        if (this.getAttributeValueList() != null) {
            sb.append("AttributeValueList: ").append(this.getAttributeValueList());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        int prime = 31;
        int hashCode = 1;
        hashCode = 31 * hashCode + (this.getValue() == null ? 0 : this.getValue().hashCode());
        hashCode = 31 * hashCode + (this.isExists() == null ? 0 : this.isExists().hashCode());
        hashCode = 31 * hashCode + (this.getComparisonOperator() == null ? 0 : this.getComparisonOperator().hashCode());
        hashCode = 31 * hashCode + (this.getAttributeValueList() == null ? 0 : this.getAttributeValueList().hashCode());
        return hashCode;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof ExpectedAttributeValue)) {
            return false;
        }
        ExpectedAttributeValue other = (ExpectedAttributeValue)obj;
        if (other.getValue() == null ^ this.getValue() == null) {
            return false;
        }
        if (other.getValue() != null && !other.getValue().equals(this.getValue())) {
            return false;
        }
        if (other.isExists() == null ^ this.isExists() == null) {
            return false;
        }
        if (other.isExists() != null && !other.isExists().equals(this.isExists())) {
            return false;
        }
        if (other.getComparisonOperator() == null ^ this.getComparisonOperator() == null) {
            return false;
        }
        if (other.getComparisonOperator() != null && !other.getComparisonOperator().equals(this.getComparisonOperator())) {
            return false;
        }
        if (other.getAttributeValueList() == null ^ this.getAttributeValueList() == null) {
            return false;
        }
        return other.getAttributeValueList() == null || other.getAttributeValueList().equals(this.getAttributeValueList());
    }
}

