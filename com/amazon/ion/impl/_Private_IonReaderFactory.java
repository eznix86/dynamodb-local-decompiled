/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazon.ion.impl;

import com.amazon.ion.IonCatalog;
import com.amazon.ion.IonException;
import com.amazon.ion.IonReader;
import com.amazon.ion.IonSystem;
import com.amazon.ion.IonTextReader;
import com.amazon.ion.IonValue;
import com.amazon.ion.impl.IonReaderContinuableCoreBinary;
import com.amazon.ion.impl.IonReaderContinuableTopLevelBinary;
import com.amazon.ion.impl.IonReaderNonContinuableSystem;
import com.amazon.ion.impl.IonReaderTextSystemX;
import com.amazon.ion.impl.IonReaderTextUserX;
import com.amazon.ion.impl.IonReaderTreeSystem;
import com.amazon.ion.impl.IonReaderTreeUserX;
import com.amazon.ion.impl.UnifiedInputStreamX;
import com.amazon.ion.impl._Private_IonReaderBuilder;
import com.amazon.ion.impl._Private_LocalSymbolTableFactory;
import com.amazon.ion.system.IonReaderBuilder;
import com.amazon.ion.util.IonStreamUtils;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.zip.GZIPInputStream;

public final class _Private_IonReaderFactory {
    public static IonReader makeSystemReader(byte[] bytes) {
        return _Private_IonReaderFactory.makeSystemReader(bytes, 0, bytes.length);
    }

    public static final IonReader makeReaderText(IonCatalog catalog, byte[] bytes, int offset, int length, _Private_LocalSymbolTableFactory lstFactory) {
        UnifiedInputStreamX uis;
        try {
            uis = _Private_IonReaderFactory.makeUnifiedStream(bytes, offset, length);
        } catch (IOException e) {
            throw new IonException(e);
        }
        return new IonReaderTextUserX(catalog, lstFactory, uis, offset);
    }

    public static IonReader makeSystemReader(byte[] bytes, int offset, int length) {
        return _Private_IonReaderBuilder.buildReader((_Private_IonReaderBuilder)_Private_IonReaderBuilder.standard(), bytes, offset, length, _Private_IonReaderFactory::makeSystemReaderBinary, _Private_IonReaderFactory::makeSystemReaderText);
    }

    public static final IonTextReader makeReaderText(IonCatalog catalog, CharSequence chars, _Private_LocalSymbolTableFactory lstFactory) {
        UnifiedInputStreamX in = UnifiedInputStreamX.makeStream(chars);
        return new IonReaderTextUserX(catalog, lstFactory, in);
    }

    public static final IonReader makeSystemReaderText(CharSequence chars) {
        UnifiedInputStreamX in = UnifiedInputStreamX.makeStream(chars);
        return new IonReaderTextSystemX(in);
    }

    public static final IonReader makeReaderText(IonCatalog catalog, InputStream is, _Private_LocalSymbolTableFactory lstFactory) {
        UnifiedInputStreamX uis;
        try {
            uis = _Private_IonReaderFactory.makeUnifiedStream(is);
        } catch (IOException e) {
            throw new IonException(e);
        }
        return new IonReaderTextUserX(catalog, lstFactory, uis, 0);
    }

    public static IonReader makeSystemReaderText(InputStream is) {
        return _Private_IonReaderBuilder.buildReader((_Private_IonReaderBuilder)_Private_IonReaderBuilder.standard(), is, _Private_IonReaderFactory::makeSystemReaderBinary, _Private_IonReaderFactory::makeSystemReaderText);
    }

    private static IonReader makeSystemReaderText(IonCatalog catalog, InputStream is, _Private_LocalSymbolTableFactory lstFactory) {
        UnifiedInputStreamX uis;
        try {
            uis = _Private_IonReaderFactory.makeUnifiedStream(is);
        } catch (IOException e) {
            throw new IonException(e);
        }
        return new IonReaderTextSystemX(uis);
    }

    private static IonReader makeSystemReaderText(IonCatalog catalog, byte[] bytes, int offset, int length, _Private_LocalSymbolTableFactory lstFactory) {
        UnifiedInputStreamX uis;
        try {
            uis = _Private_IonReaderFactory.makeUnifiedStream(bytes, offset, length);
        } catch (IOException e) {
            throw new IonException(e);
        }
        return new IonReaderTextSystemX(uis);
    }

    public static final IonTextReader makeReaderText(IonCatalog catalog, Reader chars, _Private_LocalSymbolTableFactory lstFactory) {
        try {
            UnifiedInputStreamX in = UnifiedInputStreamX.makeStream(chars);
            return new IonReaderTextUserX(catalog, lstFactory, in);
        } catch (IOException e) {
            throw new IonException(e);
        }
    }

    public static final IonReader makeSystemReaderText(Reader chars) {
        try {
            UnifiedInputStreamX in = UnifiedInputStreamX.makeStream(chars);
            return new IonReaderTextSystemX(in);
        } catch (IOException e) {
            throw new IonException(e);
        }
    }

    public static final IonReader makeReader(IonCatalog catalog, IonValue value, _Private_LocalSymbolTableFactory lstFactory) {
        return new IonReaderTreeUserX(value, catalog, lstFactory);
    }

    public static final IonReader makeSystemReaderText(IonSystem system, IonValue value) {
        if (system != null && system != value.getSystem()) {
            throw new IonException("you can't mix values from different systems");
        }
        return new IonReaderTreeSystem(value);
    }

    public static final IonReader makeReaderBinary(IonReaderBuilder builder, InputStream is, byte[] alreadyRead, int alreadyReadOff, int alreadyReadLen) {
        return new IonReaderContinuableTopLevelBinary(builder, is, alreadyRead, alreadyReadOff, alreadyReadLen);
    }

    public static final IonReader makeSystemReaderBinary(IonReaderBuilder builder, InputStream is, byte[] alreadyRead, int alreadyReadOff, int alreadyReadLen) {
        return new IonReaderNonContinuableSystem(new IonReaderContinuableCoreBinary(builder.getBufferConfiguration(), is, alreadyRead, alreadyReadOff, alreadyReadLen));
    }

    public static final IonReader makeReaderBinary(IonReaderBuilder builder, byte[] buffer, int off, int len) {
        return new IonReaderContinuableTopLevelBinary(builder, buffer, off, len);
    }

    public static final IonReader makeSystemReaderBinary(IonReaderBuilder builder, byte[] buffer, int off, int len) {
        return new IonReaderNonContinuableSystem(new IonReaderContinuableCoreBinary(builder.getBufferConfiguration(), buffer, off, len));
    }

    private static UnifiedInputStreamX makeUnifiedStream(byte[] bytes, int offset, int length) throws IOException {
        UnifiedInputStreamX uis;
        if (IonStreamUtils.isGzip(bytes, offset, length)) {
            ByteArrayInputStream baos = new ByteArrayInputStream(bytes, offset, length);
            GZIPInputStream gzip = new GZIPInputStream(baos);
            uis = UnifiedInputStreamX.makeStream(gzip);
        } else {
            uis = UnifiedInputStreamX.makeStream(bytes, offset, length);
        }
        return uis;
    }

    private static UnifiedInputStreamX makeUnifiedStream(InputStream in) throws IOException {
        in.getClass();
        in = IonStreamUtils.unGzip(in);
        UnifiedInputStreamX uis = UnifiedInputStreamX.makeStream(in);
        return uis;
    }
}

