/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazonaws.services.dynamodbv2.datamodel;

public enum DocumentNodeType {
    STRING("S"),
    STRING_SET("SS"),
    NUMBER("N"),
    NUMBER_SET("NS"),
    BINARY("B"),
    BINARY_SET("BS"),
    BOOLEAN("BOOL"),
    NULL("NULL"),
    LIST("L"),
    MAP("M"),
    HELENUS_DECIMAL("HD"),
    DOUBLE("DOUBLE"),
    FLOAT("FLOAT"),
    HELENUS_DECIMAL_SET("HDS"),
    FLOAT_SET("FS"),
    DOUBLE_SET("DOUBLESET"),
    DICT("DICT"),
    INT("INT"),
    DECIMAL("DECIMAL"),
    INT_SET("INTSET"),
    DECIMAL_SET("DECIMALSET");

    private final String abbrName;

    private DocumentNodeType(String abbrName) {
        this.abbrName = abbrName;
    }

    public String getAbbrName() {
        return this.abbrName;
    }

    public static DocumentNodeType toDocumentNodeType(String abbrName) {
        for (DocumentNodeType nodeType : DocumentNodeType.values()) {
            if (!nodeType.abbrName.equals(abbrName)) continue;
            return nodeType;
        }
        return null;
    }
}

