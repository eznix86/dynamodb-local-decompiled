/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazonaws.services.dynamodbv2.datamodel;

import com.amazon.ion.Decimal;
import com.amazonaws.services.dynamodbv2.datamodel.DocPathDictElement;
import com.amazonaws.services.dynamodbv2.datamodel.DocPathElement;
import com.amazonaws.services.dynamodbv2.datamodel.DocPathListElement;
import com.amazonaws.services.dynamodbv2.datamodel.DocumentFactory;
import com.amazonaws.services.dynamodbv2.datamodel.DocumentNode;
import com.amazonaws.services.dynamodbv2.datamodel.DocumentNodeType;
import com.amazonaws.services.dynamodbv2.datamodel.Operator;
import com.amazonaws.services.dynamodbv2.datamodel.TypeSet;
import com.amazonaws.services.dynamodbv2.datamodel.impl.DocumentNodeRawByteComparator;
import com.amazonaws.services.dynamodbv2.dbenv.DbEnv;
import com.amazonaws.services.dynamodbv2.dbenv.DbExecutionError;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

public class OperatorExecutor {
    protected final DbEnv dbEnv;
    protected final DocumentFactory docFactory;
    protected final DocumentNodeRawByteComparator rawBytesComparator;

    public OperatorExecutor(DbEnv dbEnv, DocumentFactory factory) {
        this.dbEnv = dbEnv;
        this.docFactory = factory;
        this.rawBytesComparator = new DocumentNodeRawByteComparator(this.docFactory);
    }

    public DbEnv getDbEnv() {
        return this.dbEnv;
    }

    public DocumentFactory getDocFactory() {
        return this.docFactory;
    }

    public DocumentNode addition(DocumentNode leftOp, DocumentNode rightOp) {
        return this.numberMath(leftOp, rightOp, Operator.ADDITION);
    }

    private DocumentNode numberMath(DocumentNode leftOp, DocumentNode rightOp, Operator mathOp) {
        String FUNC_NAME = "numberMath";
        this.dbEnv.dbAssert(mathOp == Operator.ADDITION || mathOp == Operator.SUBTRACTION, "numberMath", "bad math op", new Object[0]);
        if (leftOp.getNodeType() != rightOp.getNodeType()) {
            this.dbEnv.throwExecutionError(DbExecutionError.OPERAND_TYPE_MISMATCH, new Object[]{"left type", leftOp.getNodeType(), "right type", rightOp.getNodeType()});
        }
        DocumentNode resultNode = null;
        switch (leftOp.getNodeType()) {
            case NUMBER: {
                BigDecimal left = leftOp.getNValue();
                BigDecimal right = rightOp.getNValue();
                BigDecimal result = mathOp == Operator.ADDITION ? left.add(right) : left.subtract(right);
                resultNode = this.docFactory.makeNumber(result);
                break;
            }
            case HELENUS_DECIMAL: {
                BigDecimal left = leftOp.getHelenusDecimalValue();
                BigDecimal right = rightOp.getHelenusDecimalValue();
                BigDecimal result = mathOp == Operator.ADDITION ? left.add(right) : left.subtract(right);
                resultNode = this.docFactory.makeHelenusDecimal(result);
                break;
            }
            case FLOAT: {
                Float left = leftOp.getFloatValue();
                Float right = rightOp.getFloatValue();
                Float result = Float.valueOf(mathOp == Operator.ADDITION ? left.floatValue() + right.floatValue() : left.floatValue() - right.floatValue());
                resultNode = this.docFactory.makeFloat(result.floatValue());
                break;
            }
            case DOUBLE: {
                Double left = leftOp.getDoubleValue();
                Double right = rightOp.getDoubleValue();
                Double result = mathOp == Operator.ADDITION ? left + right : left - right;
                resultNode = this.docFactory.makeDouble(result);
                break;
            }
            case INT: {
                BigInteger left = leftOp.getIntValue();
                BigInteger right = rightOp.getIntValue();
                BigInteger result = mathOp == Operator.ADDITION ? left.add(right) : left.subtract(right);
                resultNode = this.docFactory.makeInt(result);
                break;
            }
            case DECIMAL: {
                Decimal left = leftOp.getDecimalValue();
                Decimal right = rightOp.getDecimalValue();
                BigDecimal result = mathOp == Operator.ADDITION ? left.add(right) : left.subtract(right);
                resultNode = this.docFactory.makeDecimal(Decimal.valueOf(result));
                break;
            }
            default: {
                this.dbEnv.dbAssert(false, "numberMath", "Unhandled type", new Object[]{"leftOp type", leftOp.getNodeType(), "rightOp type", rightOp.getNodeType()});
            }
        }
        this.dbEnv.dbAssert(resultNode != null, "numberMath", "number doc is null", new Object[0]);
        return resultNode;
    }

    public DocumentNode subtraction(DocumentNode leftOp, DocumentNode rightOp) {
        return this.numberMath(leftOp, rightOp, Operator.SUBTRACTION);
    }

    public DocumentNode setUnion(DocumentNode first, DocumentNode second) {
        DocumentNodeType documentNodeType = first.getNodeType();
        boolean isHelenusDecimalSet = DocumentNodeType.HELENUS_DECIMAL_SET.equals((Object)documentNodeType);
        if (isHelenusDecimalSet) {
            return this.setUnionForHelenusDecimalSet(first, second);
        }
        boolean isDecimalSet = DocumentNodeType.DECIMAL_SET.equals((Object)documentNodeType);
        if (isDecimalSet) {
            return this.setUnionForDecimalSet(first, second);
        }
        List<byte[]> oldArray = first.getRawSetValue();
        List<byte[]> newArray = second.getRawSetValue();
        ArrayList<byte[]> resultArray = new ArrayList<byte[]>(oldArray.size() + newArray.size());
        int oldIdx = 0;
        int newIdx = 0;
        boolean anythingNewAdded = false;
        while (true) {
            boolean addNew = false;
            if (oldIdx < oldArray.size()) {
                byte[] baOld = oldArray.get(oldIdx);
                DocumentNode docOld = this.docFactory.makeBinary(baOld);
                boolean addOld = false;
                if (newIdx < newArray.size()) {
                    byte[] baNew = newArray.get(newIdx);
                    DocumentNode docNew = this.docFactory.makeBinary(baNew);
                    int cmp = docOld.compare(docNew);
                    if (cmp == 0) {
                        addOld = true;
                        ++newIdx;
                    } else if (cmp < 0) {
                        addOld = true;
                    } else {
                        addNew = true;
                    }
                } else {
                    if (!anythingNewAdded) break;
                    addOld = true;
                }
                if (addOld) {
                    resultArray.add(baOld);
                    ++oldIdx;
                }
            } else {
                if (newIdx >= newArray.size()) break;
                addNew = true;
            }
            if (!addNew) continue;
            byte[] baNew = newArray.get(newIdx);
            resultArray.add(baNew);
            anythingNewAdded = true;
            ++newIdx;
        }
        if (!anythingNewAdded) {
            return first;
        }
        return this.docFactory.makeSet(first.getNodeType(), resultArray);
    }

    private DocumentNode setUnionForHelenusDecimalSet(DocumentNode first, DocumentNode second) {
        TreeSet<BigDecimal> oldSets = first.getHDSValue();
        TreeSet<BigDecimal> newSets = new TreeSet<BigDecimal>((SortedSet<BigDecimal>)second.getHDSValue());
        newSets.addAll(oldSets);
        return this.docFactory.makeHelenusDecimalSet(new ArrayList<BigDecimal>(newSets));
    }

    private DocumentNode setUnionForDecimalSet(DocumentNode first, DocumentNode second) {
        TreeSet<Decimal> oldSet = new TreeSet<Decimal>(first.getDecimalSetValue());
        oldSet.addAll(second.getDecimalSetValue());
        return this.docFactory.makeDecimalSet(new ArrayList<Decimal>(oldSet));
    }

    DocumentNode collectionUnion(DocumentNode first, DocumentNode second) {
        if (first == null) {
            return second;
        }
        if (second == null) {
            return first;
        }
        this.dbEnv.dbAssert(DocumentNodeType.DICT.equals((Object)first.getNodeType()) && DocumentNodeType.DICT.equals((Object)second.getNodeType()) || DocumentNodeType.MAP.equals((Object)first.getNodeType()) && DocumentNodeType.MAP.equals((Object)second.getNodeType()), "OperatorExecutor.collectionUnion", "collectionUnion requires both document nodes to be either Dictionary Data Type or Map Data Type", new Object[0]);
        return first.mergeCollection(second);
    }

    public DocumentNode setDiff(DocumentNode oldValue, DocumentNode newValue, boolean returnEmptySet) {
        this.dbEnv.dbAssert(!returnEmptySet, "OperatorExecutor.setDiff", "set_diff not supported yet", new Object[0]);
        if (DocumentNodeType.HELENUS_DECIMAL_SET.equals((Object)oldValue.getNodeType())) {
            return this.setDiffForHelenusDecimalSet(oldValue, newValue);
        }
        List<byte[]> oldArray = oldValue.getRawSetValue();
        List<byte[]> valuesToBeRemoved = newValue.getRawSetValue();
        HashSet<Integer> toBeRemovedIdx = new HashSet<Integer>(valuesToBeRemoved.size());
        for (byte[] ba : valuesToBeRemoved) {
            int idx = Collections.binarySearch(oldArray, ba, this.rawBytesComparator);
            if (idx < 0) continue;
            boolean added = toBeRemovedIdx.add(idx);
            this.dbEnv.dbAssert(added, "OperatorExecutor.setDiff", "duplicate in set", "oldArray", oldArray, "value", ba, "idx", idx);
        }
        if (toBeRemovedIdx.isEmpty()) {
            return oldValue;
        }
        int remaining = oldArray.size() - toBeRemovedIdx.size();
        this.dbEnv.dbAssert(remaining >= 0, "OperatorExecutor.setDiff", "impossible case of removing more values than in the original set", new Object[0]);
        if (remaining == 0) {
            return null;
        }
        ArrayList<byte[]> newArray = new ArrayList<byte[]>(remaining);
        for (int i = 0; i < oldArray.size(); ++i) {
            byte[] ba = oldArray.get(i);
            if (toBeRemovedIdx.contains(i)) continue;
            newArray.add(ba);
        }
        return this.docFactory.makeSet(oldValue.getNodeType(), newArray);
    }

    public DocumentNode collectionRemoval(DocumentNode dictionary, DocumentNode keySet) {
        if (dictionary == null) {
            return null;
        }
        if (keySet == null) {
            return dictionary;
        }
        this.dbEnv.dbAssert(DocumentNodeType.DICT.equals((Object)dictionary.getNodeType()) && TypeSet.SET.contains(keySet.getNodeType()) || DocumentNodeType.MAP.equals((Object)dictionary.getNodeType()) && DocumentNodeType.STRING_SET.equals((Object)keySet.getNodeType()), "OperatorExecutor.collectionRemoval", "collectionRemoval requires one operand to be dictionary type and the other operand to be set type", new Object[0]);
        return dictionary.removeElementsFromCollection(keySet);
    }

    private DocumentNode setDiffForHelenusDecimalSet(DocumentNode oldValueNode, DocumentNode newValueNode) {
        TreeSet<BigDecimal> oldValue = new TreeSet<BigDecimal>((SortedSet<BigDecimal>)oldValueNode.getHDSValue());
        TreeSet<BigDecimal> newValue = newValueNode.getHDSValue();
        oldValue.removeAll(newValue);
        if (oldValue.isEmpty()) {
            return null;
        }
        return this.docFactory.makeHelenusDecimalSet(new ArrayList<BigDecimal>(oldValue));
    }

    public DocumentNode listDelete(DocumentNode listNode, DocumentNode valueToRemoveNode) {
        if (listNode.getChildren().isEmpty() || valueToRemoveNode.getChildren().isEmpty()) {
            return listNode;
        }
        HashSet<DocumentNode> toRemoveSet = new HashSet<DocumentNode>();
        for (DocPathElement e : valueToRemoveNode.getChildren()) {
            toRemoveSet.add(valueToRemoveNode.getChild(e));
        }
        TreeMap<DocPathElement, DocumentNode> nodeMap = new TreeMap<DocPathElement, DocumentNode>();
        int elements = 0;
        for (DocPathElement e : listNode.getChildren()) {
            if (toRemoveSet.contains(listNode.getChild(e))) continue;
            nodeMap.put(new DocPathListElement(elements), listNode.getChild(e));
            ++elements;
        }
        DocumentNode newList2 = this.docFactory.makeList(nodeMap);
        return newList2;
    }

    public boolean contains(DocumentNode first, DocumentNode second) {
        if (first == null || second == null) {
            return false;
        }
        if (TypeSet.STRING_BINARY.contains(first.getNodeType())) {
            if (first.getNodeType() != second.getNodeType()) {
                return false;
            }
            return OperatorExecutor.indexOf(first.getRawScalarValue(), second.getRawScalarValue(), false) != -1;
        }
        if (first.getNodeType() == DocumentNodeType.LIST) {
            return this.listContains(first, second);
        }
        if (first.getNodeType() == DocumentNodeType.NUMBER_SET && second.getNodeType() == DocumentNodeType.NUMBER || first.getNodeType() == DocumentNodeType.STRING_SET && second.getNodeType() == DocumentNodeType.STRING || first.getNodeType() == DocumentNodeType.BINARY_SET && second.getNodeType() == DocumentNodeType.BINARY || first.getNodeType() == DocumentNodeType.DOUBLE_SET && second.getNodeType() == DocumentNodeType.DOUBLE || first.getNodeType() == DocumentNodeType.FLOAT_SET && second.getNodeType() == DocumentNodeType.FLOAT || first.getNodeType() == DocumentNodeType.INT_SET && second.getNodeType() == DocumentNodeType.INT) {
            return this.setContains(first, second);
        }
        if (first.getNodeType() == DocumentNodeType.HELENUS_DECIMAL_SET && second.getNodeType() == DocumentNodeType.HELENUS_DECIMAL) {
            return this.setContainsForHelenus(first, second);
        }
        if (first.getNodeType() == DocumentNodeType.DECIMAL_SET && second.getNodeType() == DocumentNodeType.DECIMAL) {
            return this.setContainsForDecimal(first, second);
        }
        return false;
    }

    private boolean setContainsForDecimal(DocumentNode setDocumentNode, DocumentNode valueToLookNode) {
        return setDocumentNode.getDecimalSetValue().stream().anyMatch(setElement -> setElement.compareTo(valueToLookNode.getDecimalValue()) == 0);
    }

    private boolean setContains(DocumentNode first, DocumentNode second) {
        int index = Collections.binarySearch(first.getRawSetValue(), second.getRawScalarValue(), this.rawBytesComparator);
        if (index >= 0) {
            this.dbEnv.dbAssert(index < first.getRawSetValue().size(), "OperatorExecutor.setContains", "A positive index implies that the value is contained in the list, however the index exceeds array bounds.", new Object[0]);
            return true;
        }
        return false;
    }

    private boolean setContainsForHelenus(DocumentNode first, DocumentNode second) {
        this.dbEnv.dbAssert(DocumentNodeType.HELENUS_DECIMAL_SET.equals((Object)first.getNodeType()) && DocumentNodeType.HELENUS_DECIMAL.equals((Object)second.getNodeType()), "setContainsForHelenus", "First node must be Helenus Decimal Set and second node must be Helenus Decimal", new Object[0]);
        TreeSet<BigDecimal> helenusDecimalSet = first.getHDSValue();
        BigDecimal helenusDecimal = second.getHelenusDecimalValue();
        return helenusDecimalSet.contains(helenusDecimal);
    }

    private boolean listContains(DocumentNode first, DocumentNode second) {
        for (DocPathElement e : first.getChildren()) {
            if (!OperatorExecutor.eval_EQ(first.getChild(e), second)) continue;
            return true;
        }
        return false;
    }

    boolean contains_key(DocumentNode first, DocumentNode second) {
        if (first == null || second == null) {
            return false;
        }
        if (first.getNodeType() == DocumentNodeType.DICT && TypeSet.DICT_KEY_TYPES.contains(second.getNodeType())) {
            for (DocPathElement docPathElement : first.getChildren()) {
                if (!docPathElement.getType().equals((Object)second.getNodeType()) || !second.numericEq(((DocPathDictElement)docPathElement).getElement())) continue;
                return true;
            }
        }
        return false;
    }

    boolean contains_value(DocumentNode first, DocumentNode second) {
        if (first == null || second == null) {
            return false;
        }
        if (!DocumentNodeType.DICT.equals((Object)first.getNodeType())) {
            return false;
        }
        for (DocPathElement key : first.getChildren()) {
            DocumentNode valueNode = first.getChild(key);
            if (!OperatorExecutor.typeMatch(valueNode, second) || !valueNode.numericEq(second)) continue;
            return true;
        }
        return false;
    }

    protected static int indexOf(byte[] source, byte[] target, boolean beginsWith) {
        if (target.length > source.length) {
            return -1;
        }
        int scanLength = 0;
        scanLength = beginsWith ? 1 : source.length - target.length + 1;
        for (int i = 0; i < scanLength; ++i) {
            boolean matched = true;
            for (int j = 0; j < target.length; ++j) {
                if (source[i + j] == target[j]) continue;
                matched = false;
                break;
            }
            if (!matched) continue;
            return i;
        }
        return -1;
    }

    public boolean beginsWith(DocumentNode first, DocumentNode second) {
        if (!OperatorExecutor.typeMatch(first, second)) {
            return false;
        }
        return OperatorExecutor.indexOf(first.getRawScalarValue(), second.getRawScalarValue(), true) == 0;
    }

    public DocumentNode listAppend(DocumentNode first, DocumentNode second) {
        DocumentNode prev;
        int i;
        List<DocPathElement> list_1 = first.getChildren();
        List<DocPathElement> list_2 = second.getChildren();
        TreeMap<DocPathElement, DocumentNode> nodeMap = new TreeMap<DocPathElement, DocumentNode>();
        for (i = 0; i < list_1.size(); ++i) {
            prev = nodeMap.put(new DocPathListElement(i), first.getChild(list_1.get(i)));
            this.dbEnv.dbAssert(prev == null, "OperatorExecutor.listAppend", "bad list index 1", new Object[0]);
        }
        for (i = 0; i < list_2.size(); ++i) {
            prev = nodeMap.put(new DocPathListElement(i + list_1.size()), second.getChild(list_2.get(i)));
            this.dbEnv.dbAssert(prev == null, "OperatorExecutor.listAppend", "bad list index 1", new Object[0]);
        }
        return this.docFactory.makeList(nodeMap);
    }

    public DocumentNode ifNotExist(DocumentNode first, DocumentNode second) {
        if (second == null) {
            this.dbEnv.throwExecutionError(DbExecutionError.ATTRIBUTE_NOT_FOUND, new Object[]{"function", Operator.if_not_exists});
        }
        if (first == null) {
            return second;
        }
        return first;
    }

    public static boolean eval_EQ(DocumentNode left, DocumentNode right) {
        if (left != null && right != null && (DocumentNodeType.HELENUS_DECIMAL.equals((Object)left.getNodeType()) || DocumentNodeType.HELENUS_DECIMAL_SET.equals((Object)left.getNodeType()) || DocumentNodeType.DECIMAL.equals((Object)left.getNodeType()))) {
            return left.numericEq(right);
        }
        return left != null && right != null && left.eq(right);
    }

    public static boolean eval_LT(DocumentNode left, DocumentNode right) {
        return OperatorExecutor.typeMatch(left, right) && left.compare(right) < 0;
    }

    private static boolean typeMatch(DocumentNode left, DocumentNode right) {
        return left != null && right != null && left.getNodeType() == right.getNodeType();
    }

    public static boolean eval_LE(DocumentNode left, DocumentNode right) {
        return OperatorExecutor.typeMatch(left, right) && left.compare(right) <= 0;
    }

    public static boolean eval_GT(DocumentNode left, DocumentNode right) {
        return OperatorExecutor.typeMatch(left, right) && left.compare(right) > 0;
    }

    public static boolean eval_GE(DocumentNode left, DocumentNode right) {
        return OperatorExecutor.typeMatch(left, right) && left.compare(right) >= 0;
    }

    public static boolean eval_BETWEEN(DocumentNode left, DocumentNode low, DocumentNode hi) {
        return OperatorExecutor.eval_LE(left, hi) && OperatorExecutor.eval_GE(left, low);
    }

    private boolean getBoolNotNull(DocumentNode b) {
        this.dbEnv.dbAssert(b != null, "OperatorExecutor.getBoolNotNull", "fail", new Object[0]);
        return b.getBooleanValue();
    }

    public boolean eval_NOT(DocumentNode first) {
        return !this.getBoolNotNull(first);
    }

    public boolean eval_AND(DocumentNode left, DocumentNode right) {
        return this.getBoolNotNull(left) && this.getBoolNotNull(right);
    }

    public boolean eval_OR(DocumentNode left, DocumentNode right) {
        return this.getBoolNotNull(left) || this.getBoolNotNull(right);
    }

    public boolean eval_IN(List<DocumentNode> operands) {
        for (int i = 1; i < operands.size(); ++i) {
            if (!OperatorExecutor.eval_EQ(operands.get(0), operands.get(i))) continue;
            return true;
        }
        return false;
    }

    public DocumentNode eval_SIZE(DocumentNode documentNode) {
        if (documentNode == null) {
            return null;
        }
        switch (documentNode.getNodeType()) {
            case STRING: {
                return this.docFactory.makeNumber(new BigDecimal(documentNode.getSValue().length()));
            }
            case BINARY: {
                return this.docFactory.makeNumber(new BigDecimal(documentNode.getRawScalarValue().length));
            }
            case NUMBER_SET: 
            case STRING_SET: 
            case BINARY_SET: 
            case HELENUS_DECIMAL_SET: 
            case DOUBLE_SET: 
            case INT_SET: 
            case DECIMAL_SET: 
            case FLOAT_SET: {
                return this.docFactory.makeNumber(new BigDecimal(documentNode.getRawSetValue().size()));
            }
            case LIST: 
            case MAP: 
            case DICT: {
                return this.docFactory.makeNumber(new BigDecimal(documentNode.getChildren().size()));
            }
            case NUMBER: 
            case HELENUS_DECIMAL: 
            case FLOAT: 
            case DOUBLE: 
            case BOOLEAN: 
            case NULL: {
                return null;
            }
        }
        this.dbEnv.dbAssert(false, "eval_SIZE", "Unexpected document node type", new Object[]{"DocumentNodeType", documentNode.getNodeType()});
        return null;
    }

    public boolean attributeType(DocumentNode attributeValue, DocumentNode typeName) {
        if (attributeValue == null) {
            return false;
        }
        return attributeValue.getNodeType().getAbbrName().equals(typeName.getSValue());
    }

    public DocumentNode convert(DocumentNode documentNode, DocumentNode typeName) {
        DocumentNodeType sourceType = documentNode.getNodeType();
        DocumentNodeType targetType = DocumentNodeType.toDocumentNodeType(typeName.getSValue());
        switch (sourceType) {
            case NUMBER: {
                BigDecimal num = documentNode.getNValue();
                switch (targetType) {
                    case INT: {
                        return this.docFactory.makeInt(num.toBigInteger());
                    }
                    case FLOAT: {
                        return this.docFactory.makeFloat(num.floatValue());
                    }
                    case DOUBLE: {
                        return this.docFactory.makeDouble(num.doubleValue());
                    }
                    case DECIMAL: {
                        return this.docFactory.makeDecimal(Decimal.valueOf(num));
                    }
                    case HELENUS_DECIMAL: {
                        return this.docFactory.makeHelenusDecimal(num);
                    }
                    case NUMBER: {
                        return documentNode;
                    }
                }
                break;
            }
            case HELENUS_DECIMAL: {
                BigDecimal num = documentNode.getHelenusDecimalValue();
                switch (targetType) {
                    case INT: {
                        return this.docFactory.makeInt(num.toBigInteger());
                    }
                    case FLOAT: {
                        return this.docFactory.makeFloat(num.floatValue());
                    }
                    case DOUBLE: {
                        return this.docFactory.makeDouble(num.doubleValue());
                    }
                    case DECIMAL: {
                        return this.docFactory.makeDecimal(Decimal.valueOf(num));
                    }
                    case HELENUS_DECIMAL: {
                        return documentNode;
                    }
                    case NUMBER: {
                        return this.docFactory.makeNumber(num);
                    }
                }
                break;
            }
            case DECIMAL: {
                Decimal num = documentNode.getDecimalValue();
                switch (targetType) {
                    case INT: {
                        return this.docFactory.makeInt(num.toBigInteger());
                    }
                    case FLOAT: {
                        return this.docFactory.makeFloat(num.floatValue());
                    }
                    case DOUBLE: {
                        return this.docFactory.makeDouble(num.doubleValue());
                    }
                    case HELENUS_DECIMAL: {
                        return this.docFactory.makeHelenusDecimal(num);
                    }
                    case DECIMAL: {
                        return documentNode;
                    }
                    case NUMBER: {
                        return this.docFactory.makeNumber(num);
                    }
                }
                break;
            }
            case FLOAT: {
                Float num = documentNode.getFloatValue();
                switch (targetType) {
                    case INT: {
                        return this.docFactory.makeInt(BigDecimal.valueOf(num.floatValue()).toBigInteger());
                    }
                    case FLOAT: {
                        return documentNode;
                    }
                    case DOUBLE: {
                        return this.docFactory.makeDouble(num.floatValue());
                    }
                    case DECIMAL: {
                        return this.docFactory.makeDecimal(Decimal.valueOf(num.floatValue()));
                    }
                    case HELENUS_DECIMAL: {
                        return this.docFactory.makeHelenusDecimal(new BigDecimal(num.floatValue()));
                    }
                    case NUMBER: {
                        return this.docFactory.makeNumber(new BigDecimal(num.floatValue()));
                    }
                }
                break;
            }
            case DOUBLE: {
                Double num = documentNode.getDoubleValue();
                switch (targetType) {
                    case INT: {
                        return this.docFactory.makeInt(BigDecimal.valueOf(num).toBigInteger());
                    }
                    case FLOAT: {
                        return this.docFactory.makeFloat(num.floatValue());
                    }
                    case DOUBLE: {
                        return documentNode;
                    }
                    case DECIMAL: {
                        return this.docFactory.makeDecimal(Decimal.valueOf(num));
                    }
                    case HELENUS_DECIMAL: {
                        return this.docFactory.makeHelenusDecimal(new BigDecimal(num));
                    }
                    case NUMBER: {
                        return this.docFactory.makeNumber(new BigDecimal(num));
                    }
                }
                break;
            }
            case INT: {
                BigInteger num = documentNode.getIntValue();
                switch (targetType) {
                    case INT: {
                        return documentNode;
                    }
                    case FLOAT: {
                        return this.docFactory.makeFloat(num.floatValue());
                    }
                    case DOUBLE: {
                        return this.docFactory.makeDouble(num.doubleValue());
                    }
                    case DECIMAL: {
                        return this.docFactory.makeDecimal(Decimal.valueOf(num));
                    }
                    case HELENUS_DECIMAL: {
                        return this.docFactory.makeHelenusDecimal(new BigDecimal(num));
                    }
                    case NUMBER: {
                        return this.docFactory.makeNumber(new BigDecimal(num));
                    }
                }
            }
        }
        return null;
    }
}

