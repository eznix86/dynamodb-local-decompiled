/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazonaws.services.dynamodbv2.datamodel;

import com.amazonaws.services.dynamodbv2.datamodel.ExprTreeNode;
import com.amazonaws.services.dynamodbv2.datamodel.UpdateActionType;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class UpdateAction {
    private final UpdateActionType actionType;
    private final ExprTreeNode exprTree;

    public UpdateActionType getActionType() {
        return this.actionType;
    }

    public ExprTreeNode getExprTree() {
        return this.exprTree;
    }

    public UpdateAction(UpdateActionType action, ExprTreeNode exprTree) {
        this.actionType = action;
        this.exprTree = exprTree;
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
        UpdateAction other = (UpdateAction)obj;
        return new EqualsBuilder().append((Object)this.actionType, (Object)other.actionType).append(this.exprTree, other.exprTree).isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder(8191, 127).append((Object)this.actionType).append(this.exprTree).toHashCode();
    }

    public String toString() {
        return "{a:" + this.actionType + ", e:" + this.exprTree + "}";
    }
}

