/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazonaws.services.dynamodbv2.datamodel;

import com.amazonaws.services.dynamodbv2.datamodel.DocPathElement;
import java.util.List;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class DocPath {
    private final List<DocPathElement> pathElements;

    public List<DocPathElement> getElements() {
        return this.pathElements;
    }

    public DocPath(List<DocPathElement> elements) {
        this.pathElements = elements;
    }

    public String toString() {
        return this.pathElements.toString();
    }

    public int hashCode() {
        return new HashCodeBuilder(8191, 127).append(this.pathElements).toHashCode();
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
        DocPath other = (DocPath)obj;
        return new EqualsBuilder().append(this.pathElements, other.pathElements).isEquals();
    }
}

