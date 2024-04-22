/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package ddb.partiql.shared.dbenv;

import com.amazonaws.services.dynamodbv2.dbenv.DbEnv;
import ddb.partiql.shared.dbenv.PartiQLLogger;
import java.util.function.BiFunction;

public interface PartiQLDbEnv
extends DbEnv {
    default public void dbPqlAssert(boolean check, String origin, String message) {
        throw new UnsupportedOperationException();
    }

    default public void dbPqlAssert(boolean check, String origin, String message, Object object1) {
        throw new UnsupportedOperationException();
    }

    default public void dbPqlAssert(boolean check, String origin, String message, Object object1, Object object2) {
        throw new UnsupportedOperationException();
    }

    default public PartiQLLogger createPartiQLLogger() {
        throw new UnsupportedOperationException();
    }

    default public RuntimeException createValidationError(String msg, BiFunction<String, Object[], String> messageBuilderFunction, Object ... keyValuePairs) {
        throw new UnsupportedOperationException();
    }

    default public RuntimeException createValidationError(String msg) {
        throw new UnsupportedOperationException();
    }

    default public RuntimeException createInternalServerError(String msg) {
        throw new UnsupportedOperationException();
    }

    default public RuntimeException createInternalServerError(String msg, String specialMessage) {
        throw new UnsupportedOperationException();
    }
}

