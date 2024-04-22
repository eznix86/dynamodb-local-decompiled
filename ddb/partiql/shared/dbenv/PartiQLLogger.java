/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package ddb.partiql.shared.dbenv;

public interface PartiQLLogger {
    default public void fatal(String name, String txt, Object ... keyValuePairs) {
        throw new UnsupportedOperationException();
    }
}

