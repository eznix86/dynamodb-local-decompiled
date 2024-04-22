/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazonaws.services.dynamodbv2.datamodel;

import com.amazonaws.services.dynamodbv2.datamodel.DocPathElement;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class ProjectionTreeNode {
    private final Map<DocPathElement, ProjectionTreeNode> childMap = new HashMap<DocPathElement, ProjectionTreeNode>();

    public Map<DocPathElement, ProjectionTreeNode> getChildMap() {
        return this.childMap;
    }

    public String toString() {
        return this.childMap.toString();
    }

    public int hashCode() {
        return new HashCodeBuilder(8191, 127).append(this.childMap).toHashCode();
    }

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
        ProjectionTreeNode other = (ProjectionTreeNode)obj;
        return new EqualsBuilder().append(this.childMap, other.childMap).isEquals();
    }
}

