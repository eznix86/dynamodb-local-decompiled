/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazonaws.services.dynamodbv2.datamodel;

import com.amazonaws.services.dynamodbv2.datamodel.DocumentCollectionType;
import com.amazonaws.services.dynamodbv2.datamodel.DocumentNode;
import com.amazonaws.services.dynamodbv2.datamodel.DocumentNodeType;

public abstract class DocPathElement<T> {
    public boolean isMap() {
        return DocumentCollectionType.DICTIONARY.equals((Object)this.getCollectionType()) || DocumentCollectionType.MAP.equals((Object)this.getCollectionType());
    }

    public boolean parameterized() {
        return false;
    }

    public abstract String getFieldName();

    public abstract Integer getListIndex();

    public abstract T getElement();

    public abstract int hashCode();

    public abstract boolean equals(Object var1);

    public abstract String toString();

    public abstract DocumentNodeType getType();

    public abstract DocumentCollectionType getCollectionType();

    public abstract boolean isCompatibleWithDocumentNode(DocumentNode var1);
}

