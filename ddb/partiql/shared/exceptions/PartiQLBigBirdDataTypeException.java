/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package ddb.partiql.shared.exceptions;

import ddb.partiql.shared.exceptions.PartiQLBigBirdDataTypeErrorCode;

public class PartiQLBigBirdDataTypeException
extends RuntimeException {
    private final PartiQLBigBirdDataTypeErrorCode errorCode;

    public PartiQLBigBirdDataTypeException(PartiQLBigBirdDataTypeErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public PartiQLBigBirdDataTypeErrorCode getErrorCode() {
        return this.errorCode;
    }
}

