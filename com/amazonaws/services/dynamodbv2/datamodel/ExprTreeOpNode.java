/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazonaws.services.dynamodbv2.datamodel;

import com.amazonaws.services.dynamodbv2.datamodel.DocumentNodeType;
import com.amazonaws.services.dynamodbv2.datamodel.ExprTreeNode;
import com.amazonaws.services.dynamodbv2.datamodel.Operator;
import com.amazonaws.services.dynamodbv2.datamodel.TypeSet;
import java.util.Collection;
import java.util.List;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class ExprTreeOpNode
extends ExprTreeNode {
    private final Operator operator;

    public ExprTreeOpNode(List<ExprTreeNode> children, Operator operator) {
        super(children);
        this.operator = operator;
    }

    @Override
    public boolean isReturnTypeSupported(TypeSet typeSet) {
        return typeSet.containsAny(this.operator.getReturnType());
    }

    @Override
    public Collection<DocumentNodeType> getPossibleReturnTypes() {
        return this.operator.getReturnType().getDocumentNodeTypes();
    }

    public Operator getOperator() {
        return this.operator;
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
        ExprTreeOpNode other = (ExprTreeOpNode)obj;
        return new EqualsBuilder().appendSuper(super.equals(obj)).append((Object)this.operator, (Object)other.operator).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(8191, 127).appendSuper(super.hashCode()).append((Object)this.operator).toHashCode();
    }

    @Override
    public String toString() {
        return "{o=" + this.operator.name() + ", c=" + super.toString() + "}";
    }
}

