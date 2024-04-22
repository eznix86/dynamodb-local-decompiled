/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazonaws.services.dynamodbv2.datamodel;

import com.amazonaws.services.dynamodbv2.datamodel.DocPath;
import com.amazonaws.services.dynamodbv2.datamodel.DocumentNodeType;
import com.amazonaws.services.dynamodbv2.datamodel.ExprTreeNode;
import com.amazonaws.services.dynamodbv2.datamodel.TypeSet;
import java.util.Collection;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class ExprTreePathNode
extends ExprTreeNode {
    private final DocPath docPath;

    public ExprTreePathNode(DocPath docPath) {
        super(null);
        this.docPath = docPath;
    }

    public DocPath getDocPath() {
        return this.docPath;
    }

    @Override
    public boolean isReturnTypeSupported(TypeSet typeSet) {
        return true;
    }

    @Override
    public Collection<DocumentNodeType> getPossibleReturnTypes() {
        return TypeSet.ALL_TYPES.getDocumentNodeTypes();
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
        ExprTreePathNode other = (ExprTreePathNode)obj;
        return new EqualsBuilder().appendSuper(super.equals(obj)).append(this.docPath, other.docPath).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(8191, 127).appendSuper(super.hashCode()).append(this.docPath).toHashCode();
    }

    @Override
    public String toString() {
        return "{p=" + this.docPath + "}";
    }
}

