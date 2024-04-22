/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazonaws.services.dynamodbv2.local.shared.partiql.token;

import com.amazonaws.services.dynamodbv2.local.shared.model.AttributeValue;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.ImmutableMap;
import ddb.partiql.shared.token.ContinuationToken;
import java.util.Date;
import java.util.Map;
import java.util.Objects;

public class SerialContinuationToken
extends ContinuationToken {
    private final Map<Integer, Map<String, AttributeValue>> opIndexToExclusiveNextKey;

    public SerialContinuationToken(@JsonProperty(value="requestHash") String requestHash, @JsonProperty(value="creationTime") Date creationTime, @JsonProperty(value="opIndexToExclusiveNextKey") Map<Integer, Map<String, AttributeValue>> opIndexToExclusiveNextKey) {
        super(null, requestHash, creationTime, ContinuationToken.TokenVersion.V1);
        this.opIndexToExclusiveNextKey = ImmutableMap.copyOf(opIndexToExclusiveNextKey);
    }

    public Map<Integer, Map<String, AttributeValue>> getOpIndexToExclusiveNextKey() {
        return this.opIndexToExclusiveNextKey;
    }

    @Override
    public boolean equals(Object o2) {
        if (this == o2) {
            return true;
        }
        if (o2 == null || this.getClass() != o2.getClass()) {
            return false;
        }
        if (!super.equals(o2)) {
            return false;
        }
        SerialContinuationToken that = (SerialContinuationToken)o2;
        return Objects.equals(this.opIndexToExclusiveNextKey, that.opIndexToExclusiveNextKey) && super.equals(that);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), this.opIndexToExclusiveNextKey);
    }
}

