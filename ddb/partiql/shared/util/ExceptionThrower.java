/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package ddb.partiql.shared.util;

@FunctionalInterface
public interface ExceptionThrower {
    public void throwException() throws Throwable;
}

