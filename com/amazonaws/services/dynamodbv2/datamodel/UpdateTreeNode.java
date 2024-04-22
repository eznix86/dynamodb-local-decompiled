/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazonaws.services.dynamodbv2.datamodel;

import com.amazonaws.services.dynamodbv2.datamodel.DocPathElement;
import com.amazonaws.services.dynamodbv2.datamodel.ProjectionTreeNode;
import com.amazonaws.services.dynamodbv2.datamodel.UpdateAction;
import java.util.Map;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class UpdateTreeNode
extends ProjectionTreeNode {
    private Map<DocPathElement, UpdateAction> updateMap = null;

    public Map<DocPathElement, UpdateAction> getUpdateMap() {
        return this.updateMap;
    }

    public UpdateTreeNode setUpdateMap(Map<DocPathElement, UpdateAction> updateMap) {
        this.updateMap = updateMap;
        return this;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        UpdateTreeNode other = (UpdateTreeNode)obj;
        return new EqualsBuilder().appendSuper(super.equals(other)).append(this.updateMap, other.updateMap).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(8191, 127).appendSuper(super.hashCode()).append(this.updateMap).toHashCode();
    }

    @Override
    public String toString() {
        return "{U=" + this.updateMap + ", C=" + this.getChildMap() + "}";
    }
}

