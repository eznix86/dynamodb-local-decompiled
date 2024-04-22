/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazonaws.services.dynamodbv2.datamodel;

import com.amazonaws.services.dynamodbv2.datamodel.DocumentFactory;
import com.amazonaws.services.dynamodbv2.datamodel.DocumentNode;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class ParameterMap {
    private final Map<String, DocumentNode> map;

    public ParameterMap(Map<String, DocumentNode> literalParameters) {
        this.map = literalParameters;
    }

    public ParameterMap(Map<String, String> attributeNameParameters, DocumentFactory factory) {
        this.map = new HashMap<String, DocumentNode>();
        if (attributeNameParameters != null) {
            for (Map.Entry<String, String> entry : attributeNameParameters.entrySet()) {
                this.map.put(entry.getKey(), factory.makeString(entry.getValue()));
            }
        }
    }

    public ParameterMap(Map<String, String> attributeNameParameters, Map<String, DocumentNode> literalParameters, DocumentFactory factory) {
        this.map = new HashMap<String, DocumentNode>();
        if (literalParameters != null) {
            this.map.putAll(literalParameters);
        }
        if (attributeNameParameters != null) {
            for (Map.Entry<String, String> entry : attributeNameParameters.entrySet()) {
                this.map.put(entry.getKey(), factory.makeString(entry.getValue()));
            }
        }
    }

    public Map<String, DocumentNode> getMap() {
        return this.map;
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
        ParameterMap other = (ParameterMap)obj;
        return new EqualsBuilder().append(this.map, other.map).isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder(8191, 127).append(this.map).toHashCode();
    }

    public String toString() {
        return "[" + this.map + "]";
    }
}

