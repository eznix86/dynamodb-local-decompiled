/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazon.ion;

import com.amazon.ion.IonCatalog;
import com.amazon.ion.IonDatagram;
import com.amazon.ion.IonException;
import com.amazon.ion.IonReader;
import com.amazon.ion.IonSystem;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

public interface IonLoader {
    public IonSystem getSystem();

    public IonCatalog getCatalog();

    public IonDatagram load(File var1) throws IonException, IOException;

    public IonDatagram load(String var1) throws IonException;

    public IonDatagram load(Reader var1) throws IonException, IOException;

    public IonDatagram load(byte[] var1) throws IonException;

    @Deprecated
    public IonDatagram load(InputStream var1) throws IonException, IOException;

    public IonDatagram load(IonReader var1) throws IonException;
}

