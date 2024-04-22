/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazonaws.services.dynamodbv2.datamodel;

import com.amazonaws.services.dynamodbv2.datamodel.ProjectionTreeNode;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class ProjectionExpression {
    private final ProjectionTreeNode treeRoot;

    public ProjectionExpression(ProjectionTreeNode root) {
        this.treeRoot = root;
    }

    public ProjectionTreeNode getTreeRoot() {
        return this.treeRoot;
    }

    public String toString() {
        return " [root=" + this.treeRoot + "]";
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
        ProjectionExpression other = (ProjectionExpression)obj;
        return new EqualsBuilder().append(this.treeRoot, other.treeRoot).isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder(8191, 127).append(this.treeRoot).toHashCode();
    }

    public static interface TreeNodeFactory {
        public ProjectionTreeNode newNode();
    }
}

