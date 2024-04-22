/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazon.ion;

import com.amazon.ion.IonLob;
import com.amazon.ion.IonValue;
import com.amazon.ion.UnknownSymbolException;
import java.io.Reader;
import java.nio.charset.Charset;

public interface IonClob
extends IonLob,
IonValue {
    public Reader newReader(Charset var1);

    public String stringValue(Charset var1);

    @Override
    public IonClob clone() throws UnknownSymbolException;
}

