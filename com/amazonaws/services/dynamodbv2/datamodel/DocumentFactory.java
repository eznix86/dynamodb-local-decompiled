/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazonaws.services.dynamodbv2.datamodel;

import com.amazon.ion.Decimal;
import com.amazonaws.services.dynamodbv2.datamodel.DocPathElement;
import com.amazonaws.services.dynamodbv2.datamodel.DocumentCollectionType;
import com.amazonaws.services.dynamodbv2.datamodel.DocumentNode;
import com.amazonaws.services.dynamodbv2.datamodel.DocumentNodeType;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

public interface DocumentFactory {
    public DocumentNode makeMapOrListNode(Map<DocPathElement, DocumentNode> var1, boolean var2);

    default public DocumentNode makeMap(Map<DocPathElement, DocumentNode> nodeMap) {
        return this.makeMapOrListNode(nodeMap, true);
    }

    default public DocumentNode makeList(Map<DocPathElement, DocumentNode> nodeMap) {
        return this.makeMapOrListNode(nodeMap, false);
    }

    default public DocumentNode makeDict(Map<DocPathElement, DocumentNode> nodeMap) {
        throw new UnsupportedOperationException();
    }

    default public DocumentNode makeCollection(DocumentCollectionType type, Map<DocPathElement, DocumentNode> nodeMap) {
        switch (type) {
            case MAP: {
                return this.makeMap(nodeMap);
            }
            case LIST: {
                return this.makeList(nodeMap);
            }
            case DICTIONARY: {
                return this.makeDict(nodeMap);
            }
        }
        return null;
    }

    public DocumentNode makeBoolean(boolean var1);

    public DocumentNode makeNumber(BigDecimal var1);

    public DocumentNode makeString(String var1);

    public DocumentNode makeBinary(byte[] var1);

    public DocumentNode makeNull();

    public DocumentNode makeSet(DocumentNodeType var1, List<byte[]> var2);

    public DocumentNode makeNumberSet(List<BigDecimal> var1);

    public DocumentNode makeStringSet(List<String> var1);

    default public DocumentNode makeIntSet(List<BigInteger> setValues) {
        throw new UnsupportedOperationException();
    }

    default public DocumentNode makeDecimalSet(List<Decimal> setValues) {
        throw new UnsupportedOperationException();
    }

    default public DocumentNode makeFloatSet(List<Float> setValues) {
        throw new UnsupportedOperationException();
    }

    default public DocumentNode makeDoubleSet(List<Double> setValues) {
        throw new UnsupportedOperationException();
    }

    default public DocumentNode makeHelenusDecimal(BigDecimal num) {
        throw new UnsupportedOperationException();
    }

    default public DocumentNode makeInt(BigInteger value) {
        throw new UnsupportedOperationException();
    }

    default public DocumentNode makeDecimal(Decimal value) {
        throw new UnsupportedOperationException();
    }

    default public DocumentNode makeFloat(float num) {
        throw new UnsupportedOperationException();
    }

    default public DocumentNode makeDouble(double num) {
        throw new UnsupportedOperationException();
    }

    default public DocumentNode makeHelenusDecimalSet(List<BigDecimal> setValues) {
        throw new UnsupportedOperationException();
    }
}

