/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazon.ion.impl;

import com.amazon.ion.IonCatalog;
import com.amazon.ion.IonException;
import com.amazon.ion.IonReader;
import com.amazon.ion.IonTextReader;
import com.amazon.ion.IonValue;
import com.amazon.ion.impl.LocalSymbolTable;
import com.amazon.ion.impl._Private_IonConstants;
import com.amazon.ion.impl._Private_IonReaderFactory;
import com.amazon.ion.impl._Private_LocalSymbolTableFactory;
import com.amazon.ion.system.IonReaderBuilder;
import com.amazon.ion.util.IonStreamUtils;
import java.io.ByteArrayInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.zip.GZIPInputStream;

public class _Private_IonReaderBuilder
extends IonReaderBuilder {
    private _Private_LocalSymbolTableFactory lstFactory;
    static final byte[] GZIP_HEADER = new byte[]{31, -117};

    private _Private_IonReaderBuilder() {
        this.lstFactory = LocalSymbolTable.DEFAULT_LST_FACTORY;
    }

    private _Private_IonReaderBuilder(_Private_IonReaderBuilder that) {
        super(that);
        this.lstFactory = that.lstFactory;
    }

    public IonReaderBuilder withLstFactory(_Private_LocalSymbolTableFactory factory) {
        _Private_IonReaderBuilder b = (_Private_IonReaderBuilder)this.mutable();
        b.setLstFactory(factory);
        return b;
    }

    public void setLstFactory(_Private_LocalSymbolTableFactory factory) {
        this.mutationCheck();
        this.lstFactory = factory == null ? LocalSymbolTable.DEFAULT_LST_FACTORY : factory;
    }

    static IonReader buildReader(_Private_IonReaderBuilder builder, byte[] ionData, int offset, int length, IonReaderFromBytesFactoryBinary binary, IonReaderFromBytesFactoryText text) {
        if (IonStreamUtils.isGzip(ionData, offset, length)) {
            try {
                return _Private_IonReaderBuilder.buildReader(builder, new GZIPInputStream(new ByteArrayInputStream(ionData, offset, length)), _Private_IonReaderFactory::makeReaderBinary, _Private_IonReaderFactory::makeReaderText);
            } catch (IOException e) {
                throw new IonException(e);
            }
        }
        if (IonStreamUtils.isIonBinary(ionData, offset, length)) {
            return binary.makeReader(builder, ionData, offset, length);
        }
        return text.makeReader(builder.validateCatalog(), ionData, offset, length, builder.lstFactory);
    }

    @Override
    public IonReader build(byte[] ionData, int offset, int length) {
        return _Private_IonReaderBuilder.buildReader(this, ionData, offset, length, _Private_IonReaderFactory::makeReaderBinary, _Private_IonReaderFactory::makeReaderText);
    }

    private static boolean startsWithIvm(byte[] buffer, int length) {
        if (length >= _Private_IonConstants.BINARY_VERSION_MARKER_SIZE) {
            return buffer[0] == -32 && buffer[3] == -22;
        }
        if (length >= 1) {
            return buffer[0] == -32;
        }
        return true;
    }

    private static boolean startsWithGzipHeader(byte[] buffer, int length) {
        if (length >= GZIP_HEADER.length) {
            return buffer[0] == GZIP_HEADER[0] && buffer[1] == GZIP_HEADER[1];
        }
        return false;
    }

    static IonReader buildReader(_Private_IonReaderBuilder builder, InputStream source, IonReaderFromInputStreamFactoryBinary binary, IonReaderFromInputStreamFactoryText text) {
        int bytesRead;
        if (source == null) {
            throw new NullPointerException("Cannot build a reader from a null InputStream.");
        }
        byte[] possibleIVM = new byte[_Private_IonConstants.BINARY_VERSION_MARKER_SIZE];
        InputStream ionData = source;
        try {
            bytesRead = ionData.read(possibleIVM);
        } catch (IOException e) {
            throw new IonException(e);
        }
        if (_Private_IonReaderBuilder.startsWithGzipHeader(possibleIVM, bytesRead)) {
            try {
                ionData = new GZIPInputStream(new TwoElementSequenceInputStream(new ByteArrayInputStream(possibleIVM, 0, bytesRead), ionData));
                try {
                    bytesRead = ionData.read(possibleIVM);
                } catch (EOFException e) {
                    bytesRead = 0;
                }
            } catch (IOException e) {
                throw new IonException(e);
            }
        }
        if (_Private_IonReaderBuilder.startsWithIvm(possibleIVM, bytesRead)) {
            return binary.makeReader(builder, ionData, possibleIVM, 0, bytesRead);
        }
        InputStream wrapper = bytesRead > 0 ? new TwoElementSequenceInputStream(new ByteArrayInputStream(possibleIVM, 0, bytesRead), ionData) : ionData;
        return text.makeReader(builder.validateCatalog(), wrapper, builder.lstFactory);
    }

    @Override
    public IonReader build(InputStream source) {
        return _Private_IonReaderBuilder.buildReader(this, source, _Private_IonReaderFactory::makeReaderBinary, _Private_IonReaderFactory::makeReaderText);
    }

    @Override
    public IonReader build(Reader ionText) {
        return _Private_IonReaderFactory.makeReaderText(this.validateCatalog(), ionText, this.lstFactory);
    }

    @Override
    public IonReader build(IonValue value) {
        return _Private_IonReaderFactory.makeReader(this.validateCatalog(), value, this.lstFactory);
    }

    @Override
    public IonTextReader build(String ionText) {
        return _Private_IonReaderFactory.makeReaderText(this.validateCatalog(), ionText, this.lstFactory);
    }

    @FunctionalInterface
    static interface IonReaderFromInputStreamFactoryBinary {
        public IonReader makeReader(_Private_IonReaderBuilder var1, InputStream var2, byte[] var3, int var4, int var5);
    }

    @FunctionalInterface
    static interface IonReaderFromInputStreamFactoryText {
        public IonReader makeReader(IonCatalog var1, InputStream var2, _Private_LocalSymbolTableFactory var3);
    }

    @FunctionalInterface
    static interface IonReaderFromBytesFactoryBinary {
        public IonReader makeReader(_Private_IonReaderBuilder var1, byte[] var2, int var3, int var4);
    }

    @FunctionalInterface
    static interface IonReaderFromBytesFactoryText {
        public IonReader makeReader(IonCatalog var1, byte[] var2, int var3, int var4, _Private_LocalSymbolTableFactory var5);
    }

    private static final class TwoElementSequenceInputStream
    extends InputStream {
        private final InputStream first;
        private final InputStream last;
        private InputStream in;

        private TwoElementSequenceInputStream(InputStream first, InputStream last) {
            this.first = first;
            this.last = last;
            this.in = first;
        }

        @Override
        public int available() throws IOException {
            return this.first.available() + this.last.available();
        }

        @Override
        public int read() throws IOException {
            int b = this.in.read();
            if (b < 0 && this.in == this.first) {
                this.in = this.last;
                b = this.in.read();
            }
            return b;
        }

        @Override
        public int read(byte[] b, int off, int len) throws IOException {
            int bytesToRead = len;
            int bytesRead = 0;
            int destinationOffset = off;
            while (true) {
                int bytesReadThisIteration;
                if ((bytesReadThisIteration = this.in.read(b, destinationOffset, bytesToRead)) < 0) {
                    if (this.in != this.first) break;
                    this.in = this.last;
                    continue;
                }
                if ((bytesRead += bytesReadThisIteration) == len || this.in == this.last) break;
                bytesToRead -= bytesReadThisIteration;
                destinationOffset += bytesReadThisIteration;
            }
            if (bytesRead > 0) {
                return bytesRead;
            }
            return -1;
        }

        @Override
        public void close() throws IOException {
            try {
                this.first.close();
            } finally {
                this.last.close();
            }
        }
    }

    public static class Mutable
    extends _Private_IonReaderBuilder {
        public Mutable() {
        }

        public Mutable(IonReaderBuilder that) {
            super((_Private_IonReaderBuilder)that);
        }

        @Override
        public IonReaderBuilder immutable() {
            return new _Private_IonReaderBuilder(this);
        }

        @Override
        public IonReaderBuilder mutable() {
            return this;
        }

        @Override
        protected void mutationCheck() {
        }
    }
}

