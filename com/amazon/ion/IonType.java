/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazon.ion;

public enum IonType {
    NULL,
    BOOL,
    INT,
    FLOAT,
    DECIMAL,
    TIMESTAMP,
    SYMBOL,
    STRING,
    CLOB,
    BLOB,
    LIST,
    SEXP,
    STRUCT,
    DATAGRAM;


    public static boolean isContainer(IonType t) {
        return t != null && t.ordinal() >= LIST.ordinal();
    }

    public static boolean isText(IonType t) {
        return t == STRING || t == SYMBOL;
    }

    public static boolean isLob(IonType t) {
        return t == BLOB || t == CLOB;
    }
}

