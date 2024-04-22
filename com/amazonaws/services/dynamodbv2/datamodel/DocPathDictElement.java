/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazonaws.services.dynamodbv2.datamodel;

import com.amazonaws.services.dynamodbv2.datamodel.DocPathElement;
import com.amazonaws.services.dynamodbv2.datamodel.DocumentCollectionType;
import com.amazonaws.services.dynamodbv2.datamodel.DocumentNode;
import com.amazonaws.services.dynamodbv2.datamodel.DocumentNodeType;
import com.amazonaws.services.dynamodbv2.datamodel.TypeSet;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class DocPathDictElement
extends DocPathElement<DocumentNode>
implements Comparable<DocPathDictElement> {
    private final DocumentNode element;

    public DocPathDictElement(DocumentNode node) {
        if (node == null || !TypeSet.DICT_KEY_TYPES.contains(node.getNodeType())) {
            throw new IllegalArgumentException("DocPathDictElement needs a non-null ordered type of node");
        }
        this.element = node;
    }

    @Override
    public String getFieldName() {
        return null;
    }

    @Override
    public Integer getListIndex() {
        return null;
    }

    @Override
    public DocumentNode getElement() {
        return this.element;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(13, 47).append(this.element).toHashCode();
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
        DocPathDictElement e = (DocPathDictElement)obj;
        return this.element.equals(e.getElement());
    }

    @Override
    public String toString() {
        return this.element.toString();
    }

    @Override
    public DocumentNodeType getType() {
        return this.element.getNodeType();
    }

    @Override
    public DocumentCollectionType getCollectionType() {
        return DocumentCollectionType.DICTIONARY;
    }

    @Override
    public boolean isCompatibleWithDocumentNode(DocumentNode documentNode) {
        return DocumentNodeType.DICT.equals((Object)documentNode.getNodeType());
    }

    @Override
    public int compareTo(DocPathDictElement o2) {
        return this.element.compare(o2.getElement());
    }
}

