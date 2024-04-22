/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazon.ion.impl.lite;

import com.amazon.ion.IonCatalog;
import com.amazon.ion.IonCursor;
import com.amazon.ion.IonDatagram;
import com.amazon.ion.IonException;
import com.amazon.ion.IonLoader;
import com.amazon.ion.IonReader;
import com.amazon.ion.IonSystem;
import com.amazon.ion.IonTextReader;
import com.amazon.ion.IonWriter;
import com.amazon.ion.impl._Private_IonWriterFactory;
import com.amazon.ion.impl.lite.IonDatagramLite;
import com.amazon.ion.impl.lite.IonSystemLite;
import com.amazon.ion.system.IonReaderBuilder;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

final class IonLoaderLite
implements IonLoader {
    private final IonSystemLite _system;
    private final IonCatalog _catalog;
    private final IonReaderBuilder _readerBuilder;

    public IonLoaderLite(IonSystemLite system, IonCatalog catalog) {
        assert (system != null);
        assert (catalog != null);
        this._system = system;
        this._catalog = catalog;
        this._readerBuilder = catalog == system.getCatalog() ? system.getReaderBuilder() : system.getReaderBuilder().withCatalog(catalog).immutable();
    }

    @Override
    public IonSystem getSystem() {
        return this._system;
    }

    @Override
    public IonCatalog getCatalog() {
        return this._catalog;
    }

    private IonDatagramLite load_helper(IonReader reader) throws IOException {
        IonDatagramLite datagram = new IonDatagramLite(this._system, this._catalog);
        IonWriter writer = _Private_IonWriterFactory.makeWriter(datagram);
        writer.writeValues(reader);
        if (this._readerBuilder.isIncrementalReadingEnabled() && reader instanceof IonCursor && ((IonCursor)((Object)reader)).endStream() != IonCursor.Event.NEEDS_DATA) {
            writer.writeValue(reader);
        }
        return datagram;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public IonDatagram load(File ionFile) throws IonException, IOException {
        try (FileInputStream ionData = new FileInputStream(ionFile);){
            IonDatagram datagram;
            IonDatagram ionDatagram = datagram = this.load(ionData);
            return ionDatagram;
        }
    }

    @Override
    public IonDatagram load(String ionText) throws IonException {
        try {
            IonTextReader reader = this._readerBuilder.build(ionText);
            IonDatagramLite datagram = this.load_helper(reader);
            return datagram;
        } catch (IOException e) {
            throw new IonException(e);
        }
    }

    @Override
    public IonDatagram load(Reader ionText) throws IonException, IOException {
        try {
            IonReader reader = this._readerBuilder.build(ionText);
            IonDatagramLite datagram = this.load_helper(reader);
            return datagram;
        } catch (IonException e) {
            IOException io = e.causeOfType(IOException.class);
            if (io != null) {
                throw io;
            }
            throw e;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public IonDatagram load(byte[] ionData) throws IonException {
        IonReader reader = this._readerBuilder.build(ionData, 0, ionData.length);
        try {
            IonDatagram ionDatagram = this.load(reader);
            return ionDatagram;
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                throw new IonException(e);
            }
        }
    }

    @Override
    public IonDatagram load(InputStream ionData) throws IonException, IOException {
        try {
            return this.load(this._readerBuilder.build(ionData));
        } catch (IonException e) {
            IOException io = e.causeOfType(IOException.class);
            if (io != null) {
                throw io;
            }
            throw e;
        }
    }

    @Override
    public IonDatagram load(IonReader reader) throws IonException {
        try {
            IonDatagramLite datagram = this.load_helper(reader);
            return datagram;
        } catch (IOException e) {
            throw new IonException(e);
        }
    }
}

