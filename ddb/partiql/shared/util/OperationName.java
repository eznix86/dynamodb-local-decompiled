/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package ddb.partiql.shared.util;

public enum OperationName {
    CHECK_ITEM("PartiQLSelect", "CheckItem"),
    SELECT("PartiQLSelect", "Select"),
    INSERT("PartiQLInsert", "Insert"),
    UPDATE("PartiQLUpdate", "Update"),
    DELETE("PartiQLDelete", "Delete"),
    BATCH_READ(null, "Read"),
    BATCH_WRITE(null, "Write"),
    TRANSACT_READ(null, "Read"),
    TRANSACT_WRITE(null, "Write");

    public final String value;
    public final String verb;

    private OperationName(String value, String verb) {
        this.value = value;
        this.verb = verb;
    }
}

