/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazonaws.services.dynamodbv2.datamodel;

import com.amazonaws.services.dynamodbv2.datamodel.DocumentNodeHelper;
import com.amazonaws.services.dynamodbv2.datamodel.DocumentNodeType;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public enum TypeSet {
    ORDERED_TYPES(DocumentNodeType.STRING, DocumentNodeType.NUMBER, DocumentNodeType.BINARY, DocumentNodeType.HELENUS_DECIMAL, DocumentNodeType.DOUBLE, DocumentNodeType.FLOAT, DocumentNodeType.INT, DocumentNodeType.DECIMAL),
    ATOM_TYPES(DocumentNodeType.STRING, DocumentNodeType.NUMBER, DocumentNodeType.BINARY, DocumentNodeType.STRING_SET, DocumentNodeType.NUMBER_SET, DocumentNodeType.BINARY_SET, DocumentNodeType.HELENUS_DECIMAL_SET, DocumentNodeType.FLOAT_SET, DocumentNodeType.DOUBLE_SET, DocumentNodeType.NULL, DocumentNodeType.BOOLEAN, DocumentNodeType.INT, DocumentNodeType.DECIMAL, DocumentNodeType.DOUBLE, DocumentNodeType.FLOAT, DocumentNodeType.INT_SET, DocumentNodeType.DECIMAL_SET),
    ALL_TYPES(DocumentNodeType.STRING, DocumentNodeType.NUMBER, DocumentNodeType.BINARY, DocumentNodeType.STRING_SET, DocumentNodeType.NUMBER_SET, DocumentNodeType.BINARY_SET, DocumentNodeType.HELENUS_DECIMAL_SET, DocumentNodeType.FLOAT_SET, DocumentNodeType.DOUBLE_SET, DocumentNodeType.INT_SET, DocumentNodeType.DECIMAL_SET, DocumentNodeType.NULL, DocumentNodeType.BOOLEAN, DocumentNodeType.LIST, DocumentNodeType.MAP, DocumentNodeType.HELENUS_DECIMAL, DocumentNodeType.FLOAT, DocumentNodeType.DOUBLE, DocumentNodeType.DICT, DocumentNodeType.INT, DocumentNodeType.DECIMAL),
    ALL_TYPES_NO_HELENUS_NO_ION(DocumentNodeType.NUMBER, DocumentNodeType.BINARY_SET, DocumentNodeType.LIST, DocumentNodeType.BINARY, DocumentNodeType.NULL, DocumentNodeType.MAP, DocumentNodeType.STRING, DocumentNodeType.STRING_SET, DocumentNodeType.NUMBER_SET, DocumentNodeType.BOOLEAN),
    ALL_TYPES_NO_HELENUS(DocumentNodeType.NUMBER, DocumentNodeType.BINARY_SET, DocumentNodeType.LIST, DocumentNodeType.BINARY, DocumentNodeType.NULL, DocumentNodeType.MAP, DocumentNodeType.STRING, DocumentNodeType.STRING_SET, DocumentNodeType.NUMBER_SET, DocumentNodeType.BOOLEAN, DocumentNodeType.DECIMAL, DocumentNodeType.INT, DocumentNodeType.DOUBLE, DocumentNodeType.DECIMAL_SET, DocumentNodeType.INT_SET, DocumentNodeType.DOUBLE_SET),
    STRING_BINARY(DocumentNodeType.STRING, DocumentNodeType.BINARY),
    NUMBER(DocumentNodeType.NUMBER),
    BOOLEAN(DocumentNodeType.BOOLEAN),
    LIST(DocumentNodeType.LIST),
    ALLOWED_FOR_ADD_OPERAND(DocumentNodeType.NUMBER, DocumentNodeType.HELENUS_DECIMAL, DocumentNodeType.INT, DocumentNodeType.DECIMAL, DocumentNodeType.FLOAT, DocumentNodeType.DOUBLE, DocumentNodeType.STRING_SET, DocumentNodeType.NUMBER_SET, DocumentNodeType.HELENUS_DECIMAL_SET, DocumentNodeType.BINARY_SET, DocumentNodeType.FLOAT_SET, DocumentNodeType.DOUBLE_SET, DocumentNodeType.INT_SET, DocumentNodeType.DECIMAL_SET, DocumentNodeType.DICT),
    ALLOWED_FOR_DELETE_OPERAND(DocumentNodeType.STRING_SET, DocumentNodeType.NUMBER_SET, DocumentNodeType.BINARY_SET, DocumentNodeType.HELENUS_DECIMAL_SET, DocumentNodeType.FLOAT_SET, DocumentNodeType.DOUBLE_SET, DocumentNodeType.INT_SET, DocumentNodeType.DECIMAL_SET),
    SET(DocumentNodeType.STRING_SET, DocumentNodeType.NUMBER_SET, DocumentNodeType.BINARY_SET, DocumentNodeType.HELENUS_DECIMAL_SET, DocumentNodeType.FLOAT_SET, DocumentNodeType.DOUBLE_SET, DocumentNodeType.INT_SET, DocumentNodeType.DECIMAL_SET),
    ITERABLE(DocumentNodeType.STRING, DocumentNodeType.BINARY, DocumentNodeType.STRING_SET, DocumentNodeType.NUMBER_SET, DocumentNodeType.BINARY_SET, DocumentNodeType.HELENUS_DECIMAL_SET, DocumentNodeType.FLOAT_SET, DocumentNodeType.DOUBLE_SET, DocumentNodeType.INT_SET, DocumentNodeType.DECIMAL_SET, DocumentNodeType.LIST, DocumentNodeType.MAP, DocumentNodeType.DICT),
    NUMERIC(DocumentNodeType.NUMBER, DocumentNodeType.HELENUS_DECIMAL, DocumentNodeType.FLOAT, DocumentNodeType.DOUBLE, DocumentNodeType.INT, DocumentNodeType.DECIMAL),
    DICT_KEY_TYPES(DocumentNodeType.STRING, DocumentNodeType.NUMBER, DocumentNodeType.BINARY, DocumentNodeType.HELENUS_DECIMAL, DocumentNodeType.DOUBLE, DocumentNodeType.FLOAT, DocumentNodeType.BOOLEAN, DocumentNodeType.INT, DocumentNodeType.DECIMAL);

    private final Collection<DocumentNodeType> resultingTypes;
    private final String symbolStr;
    private final Set<String> symbolSet;

    private TypeSet(DocumentNodeType ... types) {
        List<DocumentNodeType> typeList = Arrays.asList(types);
        this.resultingTypes = Collections.unmodifiableSet(new HashSet<DocumentNodeType>(typeList));
        this.symbolStr = DocumentNodeHelper.createTypeSymbolsString(typeList);
        this.symbolSet = DocumentNodeHelper.createTypeSymbolsSet(typeList);
    }

    public String getSymbolStr() {
        return this.symbolStr;
    }

    public Set<String> getSymbolSet() {
        return this.symbolSet;
    }

    public boolean contains(DocumentNodeType type) {
        return this.resultingTypes.contains((Object)type);
    }

    public boolean containsAny(TypeSet typeSet) {
        return !Collections.disjoint(this.resultingTypes, typeSet.getDocumentNodeTypes());
    }

    public Collection<DocumentNodeType> getDocumentNodeTypes() {
        return this.resultingTypes;
    }
}

