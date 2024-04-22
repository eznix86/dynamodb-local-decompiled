/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazonaws.services.dynamodbv2.datamodel;

import com.amazonaws.services.dynamodbv2.datamodel.DocPathElement;
import com.amazonaws.services.dynamodbv2.datamodel.DocumentCollectionType;
import com.amazonaws.services.dynamodbv2.datamodel.DocumentNode;
import com.amazonaws.services.dynamodbv2.datamodel.DocumentNodeType;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class DocPathListElement
extends DocPathElement<Integer>
implements Comparable<DocPathListElement> {
    private final int listIndex;

    public DocPathListElement(int listIndex) {
        this.listIndex = listIndex;
    }

    @Override
    public Integer getListIndex() {
        return this.getElement();
    }

    @Override
    public Integer getElement() {
        return this.listIndex;
    }

    @Override
    public String toString() {
        return "[" + this.listIndex + "]";
    }

    @Override
    public DocumentNodeType getType() {
        return DocumentNodeType.NUMBER;
    }

    @Override
    public DocumentCollectionType getCollectionType() {
        return DocumentCollectionType.LIST;
    }

    @Override
    public boolean isCompatibleWithDocumentNode(DocumentNode documentNode) {
        return DocumentNodeType.LIST.equals((Object)documentNode.getNodeType());
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(8191, 127).append(this.listIndex).toHashCode();
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
        DocPathListElement other = (DocPathListElement)obj;
        return new EqualsBuilder().append(this.listIndex, other.listIndex).isEquals();
    }

    @Override
    public int compareTo(DocPathListElement other) {
        return new Integer(this.getElement()).compareTo(other.getElement());
    }

    @Override
    public String getFieldName() {
        return null;
    }
}

