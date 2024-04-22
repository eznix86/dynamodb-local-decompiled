/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazonaws.services.dynamodbv2.datamodel;

import com.amazonaws.services.dynamodbv2.datamodel.DocumentNode;
import com.amazonaws.services.dynamodbv2.datamodel.DocumentNodeType;
import com.amazonaws.services.dynamodbv2.datamodel.ExprTreeNode;
import com.amazonaws.services.dynamodbv2.datamodel.TypeSet;
import java.util.Arrays;
import java.util.Collection;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class ExprTreeValueNode
extends ExprTreeNode {
    private DocumentNode value = null;
    private final String literalSub;

    public ExprTreeValueNode(DocumentNode value) {
        super(null);
        this.value = value;
        this.literalSub = null;
    }

    public ExprTreeValueNode(String literalSub) {
        super(null);
        this.literalSub = literalSub;
    }

    @Override
    public boolean isReturnTypeSupported(TypeSet typeSet) {
        return typeSet.contains(this.value.getNodeType());
    }

    @Override
    public Collection<DocumentNodeType> getPossibleReturnTypes() {
        return Arrays.asList(this.value.getNodeType());
    }

    public DocumentNode getValue() {
        return this.value;
    }

    public void setValue(DocumentNode value) {
        this.value = value;
    }

    public String getLiteralSub() {
        return this.literalSub;
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
        if (!super.equals(obj)) {
            return false;
        }
        ExprTreeValueNode other = (ExprTreeValueNode)obj;
        return new EqualsBuilder().appendSuper(super.equals(other)).append(this.value, other.value).append(this.literalSub, other.literalSub).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(8191, 127).appendSuper(super.hashCode()).append(this.value).append(this.literalSub).toHashCode();
    }

    @Override
    public String toString() {
        if (this.value != null) {
            return "{v=" + this.value + ", s=" + this.literalSub + "}";
        }
        return "{s=" + this.literalSub + "}";
    }
}

