/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazon.ion.impl;

import com.amazon.ion.IonReader;
import com.amazon.ion.IonType;
import com.amazon.ion.IonWriter;
import com.amazon.ion.impl.IonWriterSystemBinary;
import com.amazon.ion.impl.IonWriterUser;
import com.amazon.ion.impl._Private_ByteTransferReader;
import com.amazon.ion.impl._Private_ByteTransferSink;
import com.amazon.ion.impl._Private_IonBinaryWriterBuilder;
import com.amazon.ion.impl._Private_ListWriter;
import com.amazon.ion.impl._Private_SymtabExtendsCache;
import com.amazon.ion.impl._Private_Utils;
import com.amazon.ion.util.IonStreamUtils;
import java.io.IOException;

class IonWriterUserBinary
extends IonWriterUser
implements _Private_ListWriter {
    private final _Private_SymtabExtendsCache mySymtabExtendsCache;
    private final _Private_ByteTransferSink myCopySink;

    IonWriterUserBinary(_Private_IonBinaryWriterBuilder options, IonWriterSystemBinary systemWriter) {
        super(options.getCatalog(), options.getSymtabValueFactory(), systemWriter, options.buildContextSymbolTable(), true);
        if (options.isStreamCopyOptimized()) {
            this.mySymtabExtendsCache = new _Private_SymtabExtendsCache();
            this.myCopySink = new _Private_ByteTransferSink(){

                @Override
                public void writeBytes(byte[] data, int off, int len) throws IOException {
                    ((IonWriterSystemBinary)IonWriterUserBinary.this._current_writer).writeRaw(data, off, len);
                }
            };
        } else {
            this.mySymtabExtendsCache = null;
            this.myCopySink = null;
        }
    }

    @Override
    public boolean isStreamCopyOptimized() {
        return this.mySymtabExtendsCache != null;
    }

    @Override
    public void writeValue(IonReader reader) throws IOException {
        _Private_ByteTransferReader transfer;
        IonType type = reader.getType();
        if (this.isStreamCopyOptimized() && this._current_writer instanceof IonWriterSystemBinary && (transfer = reader.asFacet(_Private_ByteTransferReader.class)) != null && (_Private_Utils.isNonSymbolScalar(type) || this.mySymtabExtendsCache.symtabsCompat(this.getSymbolTable(), reader.getSymbolTable()))) {
            transfer.transferCurrentValue(this.myCopySink);
            return;
        }
        this.writeValueRecursively(reader);
    }

    @Override
    public void writeBoolList(boolean[] values2) throws IOException {
        IonStreamUtils.writeBoolList(this._current_writer, values2);
    }

    @Override
    public void writeFloatList(float[] values2) throws IOException {
        IonStreamUtils.writeFloatList((IonWriter)this._current_writer, values2);
    }

    @Override
    public void writeFloatList(double[] values2) throws IOException {
        IonStreamUtils.writeFloatList((IonWriter)this._current_writer, values2);
    }

    @Override
    public void writeIntList(byte[] values2) throws IOException {
        IonStreamUtils.writeIntList((IonWriter)this._current_writer, values2);
    }

    @Override
    public void writeIntList(short[] values2) throws IOException {
        IonStreamUtils.writeIntList((IonWriter)this._current_writer, values2);
    }

    @Override
    public void writeIntList(int[] values2) throws IOException {
        IonStreamUtils.writeIntList((IonWriter)this._current_writer, values2);
    }

    @Override
    public void writeIntList(long[] values2) throws IOException {
        IonStreamUtils.writeIntList((IonWriter)this._current_writer, values2);
    }

    @Override
    public void writeStringList(String[] values2) throws IOException {
        IonStreamUtils.writeStringList(this._current_writer, values2);
    }
}

