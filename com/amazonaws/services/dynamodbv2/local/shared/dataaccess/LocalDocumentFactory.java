/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazonaws.services.dynamodbv2.local.shared.dataaccess;

import com.amazonaws.services.dynamodbv2.datamodel.DocPathElement;
import com.amazonaws.services.dynamodbv2.datamodel.DocumentFactory;
import com.amazonaws.services.dynamodbv2.datamodel.DocumentNode;
import com.amazonaws.services.dynamodbv2.datamodel.DocumentNodeType;
import com.amazonaws.services.dynamodbv2.exceptions.AWSExceptionFactory;
import com.amazonaws.services.dynamodbv2.exceptions.AmazonServiceExceptionType;
import com.amazonaws.services.dynamodbv2.local.shared.access.LocalDBUtils;
import com.amazonaws.services.dynamodbv2.local.shared.model.AttributeValue;
import java.math.BigDecimal;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class LocalDocumentFactory
implements DocumentFactory {
    @Override
    public DocumentNode makeMapOrListNode(Map<DocPathElement, DocumentNode> nodeMap, boolean mapIfEmpty) {
        boolean documentLevel = false;
        if (nodeMap.size() == 0) {
            if (mapIfEmpty) {
                return new AttributeValue().withM(new HashMap<String, AttributeValue>());
            }
            return new AttributeValue().withL(new ArrayList<AttributeValue>());
        }
        Map.Entry<DocPathElement, DocumentNode> firstEntry = nodeMap.entrySet().iterator().next();
        return firstEntry.getKey().isMap() ? this.makeMap(nodeMap, 0) : this.makeList(nodeMap, 0);
    }

    private DocumentNode makeList(Map<DocPathElement, DocumentNode> nodeMap, int documentLevel) {
        ArrayList<AttributeValue> attributeList = new ArrayList<AttributeValue>();
        TreeMap<DocPathElement, DocumentNode> sortedMap = new TreeMap<DocPathElement, DocumentNode>(nodeMap);
        for (Map.Entry<DocPathElement, DocumentNode> current : sortedMap.entrySet()) {
            if (current.getKey().isMap()) {
                throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.INTERNAL_SERVER_ERROR, null);
            }
            attributeList.add((AttributeValue)current.getValue());
        }
        return new AttributeValue().withL(attributeList);
    }

    private DocumentNode makeMap(Map<DocPathElement, DocumentNode> nodeMap, int documentLevel) {
        HashMap<String, AttributeValue> attributeMap = new HashMap<String, AttributeValue>();
        for (Map.Entry<DocPathElement, DocumentNode> current : nodeMap.entrySet()) {
            if (!current.getKey().isMap()) {
                throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.INTERNAL_SERVER_ERROR, null);
            }
            attributeMap.put(current.getKey().getFieldName(), (AttributeValue)current.getValue());
        }
        return new AttributeValue().withM(attributeMap);
    }

    @Override
    public DocumentNode makeBoolean(boolean value) {
        return new AttributeValue().withBOOL(value);
    }

    @Override
    public DocumentNode makeNumber(BigDecimal num) {
        return new AttributeValue().withN(num.toPlainString());
    }

    @Override
    public DocumentNode makeString(String s) {
        return new AttributeValue().withS(s);
    }

    @Override
    public DocumentNode makeNull() {
        return new AttributeValue().withNULL(true);
    }

    @Override
    public DocumentNode makeSet(DocumentNodeType type, List<byte[]> setValues) {
        if (type == DocumentNodeType.BINARY_SET) {
            ArrayList<ByteBuffer> byteBufferList = new ArrayList<ByteBuffer>();
            for (byte[] setValue : setValues) {
                byteBufferList.add(ByteBuffer.wrap(setValue));
            }
            return new AttributeValue().withBS(byteBufferList);
        }
        if (type == DocumentNodeType.STRING_SET) {
            ArrayList<String> stringList = new ArrayList<String>();
            for (byte[] setValue : setValues) {
                stringList.add(new String(setValue));
            }
            return new AttributeValue().withSS(stringList);
        }
        if (type == DocumentNodeType.NUMBER_SET) {
            ArrayList<String> numberList = new ArrayList<String>();
            for (byte[] setValue : setValues) {
                numberList.add(LocalDBUtils.decodeBigDecimal(setValue).toPlainString());
            }
            return new AttributeValue().withNS(numberList);
        }
        return null;
    }

    @Override
    public DocumentNode makeBinary(byte[] b) {
        return new AttributeValue().withB(ByteBuffer.wrap(b));
    }

    @Override
    public DocumentNode makeNumberSet(List<BigDecimal> setValues) {
        ArrayList<String> numberStringList = new ArrayList<String>();
        for (BigDecimal number : setValues) {
            numberStringList.add(number.toPlainString());
        }
        AttributeValue numberSet = new AttributeValue();
        numberSet.withNS(numberStringList);
        return numberSet;
    }

    @Override
    public DocumentNode makeStringSet(List<String> setValues) {
        return new AttributeValue().withSS(setValues);
    }
}

