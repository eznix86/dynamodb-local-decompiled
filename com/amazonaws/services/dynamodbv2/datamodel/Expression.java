/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazonaws.services.dynamodbv2.datamodel;

import com.amazonaws.services.dynamodbv2.datamodel.ExprTreeNode;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class Expression {
    private final ExprTreeNode treeRoot;

    public Expression(ExprTreeNode treeRoot) {
        this.treeRoot = treeRoot;
    }

    public ExprTreeNode getExprTree() {
        return this.treeRoot;
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
        Expression other = (Expression)obj;
        return new EqualsBuilder().append(this.treeRoot, other.treeRoot).isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder(8191, 127).append(this.treeRoot).toHashCode();
    }

    public String toString() {
        return this.treeRoot.toString();
    }
}

