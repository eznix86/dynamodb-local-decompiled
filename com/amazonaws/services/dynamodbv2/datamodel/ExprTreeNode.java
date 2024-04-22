/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazonaws.services.dynamodbv2.datamodel;

import com.amazonaws.services.dynamodbv2.datamodel.DocumentNodeType;
import com.amazonaws.services.dynamodbv2.datamodel.TypeSet;
import java.util.Collection;
import java.util.List;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public abstract class ExprTreeNode {
    protected final List<ExprTreeNode> children;

    public List<ExprTreeNode> getChildren() {
        return this.children;
    }

    public ExprTreeNode(List<ExprTreeNode> children) {
        this.children = children;
    }

    public abstract boolean isReturnTypeSupported(TypeSet var1);

    public abstract Collection<DocumentNodeType> getPossibleReturnTypes();

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
        ExprTreeNode other = (ExprTreeNode)obj;
        return new EqualsBuilder().append(this.children, other.children).isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder(8191, 127).append(this.children).toHashCode();
    }

    public String toString() {
        return this.children.toString();
    }
}

