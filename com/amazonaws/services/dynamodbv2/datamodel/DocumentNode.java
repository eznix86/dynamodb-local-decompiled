/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazonaws.services.dynamodbv2.datamodel;

import com.amazon.ion.Decimal;
import com.amazonaws.services.dynamodbv2.datamodel.DocPathElement;
import com.amazonaws.services.dynamodbv2.datamodel.DocumentNodeType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public interface DocumentNode
extends Comparable<DocumentNode> {
    public DocumentNode getChild(DocPathElement var1);

    public List<DocPathElement> getChildren();

    public boolean eq(DocumentNode var1);

    public boolean numericEq(DocumentNode var1);

    public boolean lessThan(DocumentNode var1);

    public boolean greaterThan(DocumentNode var1);

    public int compare(DocumentNode var1);

    public boolean getBooleanValue();

    public BigDecimal getNValue();

    public String getSValue();

    @JsonIgnore
    default public BigDecimal getHelenusDecimalValue() {
        throw new UnsupportedOperationException();
    }

    @JsonIgnore
    default public BigInteger getIntValue() {
        throw new UnsupportedOperationException();
    }

    @JsonIgnore
    default public Decimal getDecimalValue() {
        throw new UnsupportedOperationException();
    }

    @JsonIgnore
    default public Float getFloatValue() {
        throw new UnsupportedOperationException();
    }

    @JsonIgnore
    default public Double getDoubleValue() {
        throw new UnsupportedOperationException();
    }

    @JsonIgnore
    default public Set<BigInteger> getIntegerSetValue() {
        throw new UnsupportedOperationException();
    }

    @JsonIgnore
    default public Set<Decimal> getDecimalSetValue() {
        throw new UnsupportedOperationException();
    }

    @JsonIgnore
    default public Set<String> getSSValue() {
        throw new UnsupportedOperationException();
    }

    @JsonIgnore
    default public Set<BigDecimal> getNSValue() {
        throw new UnsupportedOperationException();
    }

    @JsonIgnore
    default public Set<Float> getFSValue() {
        throw new UnsupportedOperationException();
    }

    @JsonIgnore
    default public Set<Double> getDSValue() {
        throw new UnsupportedOperationException();
    }

    @JsonIgnore
    default public List<byte[]> getBSValue() {
        throw new UnsupportedOperationException();
    }

    @JsonIgnore
    default public TreeSet<BigDecimal> getHDSValue() {
        throw new UnsupportedOperationException();
    }

    public boolean isMap();

    public DocumentNodeType getNodeType();

    public byte[] getRawScalarValue();

    public List<byte[]> getRawSetValue();

    public DocumentNode mergeCollection(DocumentNode var1);

    public DocumentNode removeElementsFromCollection(DocumentNode var1);

    public void setLevel(int var1);

    public int getLevel();
}

