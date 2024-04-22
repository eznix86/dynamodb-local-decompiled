/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazon.ion;

import com.amazon.ion.IonLob;
import com.amazon.ion.NullValueException;
import com.amazon.ion.UnknownSymbolException;
import java.io.IOException;

public interface IonBlob
extends IonLob {
    public void printBase64(Appendable var1) throws NullValueException, IOException;

    @Override
    public IonBlob clone() throws UnknownSymbolException;
}

