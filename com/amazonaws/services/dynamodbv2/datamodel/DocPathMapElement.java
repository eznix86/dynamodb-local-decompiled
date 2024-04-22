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

public class DocPathMapElement
extends DocPathElement<String>
implements Comparable<DocPathMapElement> {
    private final String fieldName;

    public DocPathMapElement(String fieldName) {
        this.fieldName = fieldName;
    }

    @Override
    public boolean isMap() {
        return true;
    }

    @Override
    public boolean parameterized() {
        return this.fieldName.startsWith("#") || this.fieldName.startsWith(":");
    }

    @Override
    public String getFieldName() {
        return this.getElement();
    }

    @Override
    public String toString() {
        return this.fieldName;
    }

    @Override
    public DocumentNodeType getType() {
        return DocumentNodeType.STRING;
    }

    @Override
    public DocumentCollectionType getCollectionType() {
        return DocumentCollectionType.MAP;
    }

    @Override
    public boolean isCompatibleWithDocumentNode(DocumentNode documentNode) {
        return DocumentNodeType.MAP.equals((Object)documentNode.getNodeType());
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(8191, 127).append(this.fieldName).toHashCode();
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
        DocPathMapElement other = (DocPathMapElement)obj;
        return new EqualsBuilder().append(this.fieldName, other.fieldName).isEquals();
    }

    @Override
    public int compareTo(DocPathMapElement other) {
        return this.fieldName.compareTo(other.getFieldName());
    }

    @Override
    public Integer getListIndex() {
        return null;
    }

    @Override
    public String getElement() {
        return this.fieldName;
    }
}

