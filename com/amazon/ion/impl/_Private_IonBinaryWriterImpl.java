/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazon.ion.impl;

import com.amazon.ion.IonBinaryWriter;
import com.amazon.ion.IonException;
import com.amazon.ion.impl.BlockedBuffer;
import com.amazon.ion.impl.IonWriterSystemBinary;
import com.amazon.ion.impl.IonWriterUserBinary;
import com.amazon.ion.impl._Private_IonBinaryWriterBuilder;
import java.io.IOException;
import java.io.OutputStream;

@Deprecated
public final class _Private_IonBinaryWriterImpl
extends IonWriterUserBinary
implements IonBinaryWriter {
    _Private_IonBinaryWriterImpl(_Private_IonBinaryWriterBuilder options, IonWriterSystemBinary systemWriter) {
        super(options, systemWriter);
    }

    private BlockedBuffer.BufferedOutputStream getOutputStream() {
        IonWriterSystemBinary systemWriter = (IonWriterSystemBinary)this._system_writer;
        return (BlockedBuffer.BufferedOutputStream)systemWriter.getOutputStream();
    }

    @Override
    public int byteSize() {
        try {
            this.finish();
        } catch (IOException e) {
            throw new IonException(e);
        }
        int size = this.getOutputStream().byteSize();
        return size;
    }

    @Override
    public byte[] getBytes() throws IOException {
        this.finish();
        byte[] bytes = this.getOutputStream().getBytes();
        return bytes;
    }

    @Override
    public int getBytes(byte[] bytes, int offset, int len) throws IOException {
        this.finish();
        int written = this.getOutputStream().getBytes(bytes, offset, len);
        return written;
    }

    @Override
    public int writeBytes(OutputStream userstream) throws IOException {
        this.finish();
        int written = this.getOutputStream().writeBytes(userstream);
        return written;
    }
}

