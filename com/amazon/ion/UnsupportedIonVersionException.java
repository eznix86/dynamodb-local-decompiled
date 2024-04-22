/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazon.ion;

import com.amazon.ion.IonException;

public class UnsupportedIonVersionException
extends IonException {
    private static final long serialVersionUID = -1166749371823975664L;
    private final String _unsupportedIonVersionId;

    public UnsupportedIonVersionException(String unsupportedIonVersionId) {
        this._unsupportedIonVersionId = unsupportedIonVersionId;
    }

    public String getUnsuportedIonVersionId() {
        return this._unsupportedIonVersionId;
    }

    @Override
    public String getMessage() {
        return "Unsupported Ion version " + this._unsupportedIonVersionId;
    }
}

