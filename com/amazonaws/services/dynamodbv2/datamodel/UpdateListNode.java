/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazonaws.services.dynamodbv2.datamodel;

import com.amazonaws.services.dynamodbv2.datamodel.DocPath;
import com.amazonaws.services.dynamodbv2.datamodel.ExprTreeNode;
import com.amazonaws.services.dynamodbv2.datamodel.UpdateAction;
import com.amazonaws.services.dynamodbv2.datamodel.UpdateActionType;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class UpdateListNode {
    private final DocPath docPath;
    private final UpdateAction action;

    public UpdateListNode(DocPath docPath, UpdateAction action) {
        this.docPath = docPath;
        this.action = action;
    }

    public UpdateListNode(DocPath docPath, ExprTreeNode value) {
        this(docPath, new UpdateAction(UpdateActionType.SET, value));
    }

    public UpdateListNode(DocPath docPath) {
        this(docPath, new UpdateAction(UpdateActionType.DELETE, null));
    }

    public DocPath getDocPath() {
        return this.docPath;
    }

    public ExprTreeNode getExprTree() {
        return this.action.getExprTree();
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
        UpdateListNode other = (UpdateListNode)obj;
        return new EqualsBuilder().append(this.docPath, other.docPath).append(this.action, other.action).isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder(8191, 127).append(this.docPath).append(this.action).toHashCode();
    }

    public String toString() {
        return "{P=" + this.docPath + ", A=" + this.action + "}";
    }

    public UpdateAction getAction() {
        return this.action;
    }
}

