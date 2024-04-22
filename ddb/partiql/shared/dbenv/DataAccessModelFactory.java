/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package ddb.partiql.shared.dbenv;

public interface DataAccessModelFactory {
    default public <N> N makeAttributeName(String name) {
        throw new UnsupportedOperationException();
    }
}

