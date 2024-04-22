/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazon.ion.impl.bin;

import com.amazon.ion.IonBinaryWriter;
import com.amazon.ion.IonReader;
import com.amazon.ion.IonType;
import com.amazon.ion.IonValue;
import com.amazon.ion.IonWriter;
import com.amazon.ion.SymbolTable;
import com.amazon.ion.SymbolToken;
import com.amazon.ion.Timestamp;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

@Deprecated
final class IonBinaryWriterAdapter
implements IonBinaryWriter {
    private final InternalByteArrayOutputStream buffer = new InternalByteArrayOutputStream();
    private final IonWriter delegate;

    public IonBinaryWriterAdapter(Factory factory) throws IOException {
        this.delegate = factory.create(this.buffer);
    }

    IonWriter getDelegate() {
        return this.delegate;
    }

    void reset() {
        this.buffer.reset();
    }

    @Override
    public int byteSize() {
        return this.buffer.size();
    }

    @Override
    public byte[] getBytes() throws IOException {
        return this.buffer.toByteArray();
    }

    @Override
    public int getBytes(byte[] bytes, int offset, int maxlen) throws IOException {
        int amount = Math.min(maxlen, this.buffer.size());
        System.arraycopy(this.buffer.bytes(), 0, bytes, offset, amount);
        return amount;
    }

    @Override
    public int writeBytes(OutputStream userstream) throws IOException {
        this.buffer.writeTo(userstream);
        return this.buffer.size();
    }

    @Override
    public SymbolTable getSymbolTable() {
        return this.delegate.getSymbolTable();
    }

    @Override
    public void flush() throws IOException {
        this.delegate.flush();
    }

    @Override
    public void finish() throws IOException {
        this.delegate.finish();
    }

    @Override
    public void close() throws IOException {
        this.delegate.close();
    }

    @Override
    public void setFieldName(String name) {
        this.delegate.setFieldName(name);
    }

    @Override
    public void setFieldNameSymbol(SymbolToken name) {
        this.delegate.setFieldNameSymbol(name);
    }

    @Override
    public void setTypeAnnotations(String ... annotations) {
        this.delegate.setTypeAnnotations(annotations);
    }

    @Override
    public void setTypeAnnotationSymbols(SymbolToken ... annotations) {
        this.delegate.setTypeAnnotationSymbols(annotations);
    }

    @Override
    public void addTypeAnnotation(String annotation) {
        this.delegate.addTypeAnnotation(annotation);
    }

    @Override
    public void stepIn(IonType containerType) throws IOException {
        this.delegate.stepIn(containerType);
    }

    @Override
    public void stepOut() throws IOException {
        this.delegate.stepOut();
    }

    @Override
    public boolean isInStruct() {
        return this.delegate.isInStruct();
    }

    @Override
    public void writeValue(IonValue value) throws IOException {
        this.delegate.writeValue(value);
    }

    @Override
    public void writeValue(IonReader reader) throws IOException {
        this.delegate.writeValue(reader);
    }

    @Override
    public void writeValues(IonReader reader) throws IOException {
        this.delegate.writeValues(reader);
    }

    @Override
    public void writeNull() throws IOException {
        this.delegate.writeNull();
    }

    @Override
    public void writeNull(IonType type) throws IOException {
        this.delegate.writeNull(type);
    }

    @Override
    public void writeBool(boolean value) throws IOException {
        this.delegate.writeBool(value);
    }

    @Override
    public void writeInt(long value) throws IOException {
        this.delegate.writeInt(value);
    }

    @Override
    public void writeInt(BigInteger value) throws IOException {
        this.delegate.writeInt(value);
    }

    @Override
    public void writeFloat(double value) throws IOException {
        this.delegate.writeFloat(value);
    }

    @Override
    public void writeDecimal(BigDecimal value) throws IOException {
        this.delegate.writeDecimal(value);
    }

    @Override
    public void writeTimestamp(Timestamp value) throws IOException {
        this.delegate.writeTimestamp(value);
    }

    @Override
    public void writeTimestampUTC(Date value) throws IOException {
        this.delegate.writeTimestampUTC(value);
    }

    @Override
    public void writeSymbol(String content) throws IOException {
        this.delegate.writeSymbol(content);
    }

    @Override
    public void writeSymbolToken(SymbolToken content) throws IOException {
        this.delegate.writeSymbolToken(content);
    }

    @Override
    public void writeString(String value) throws IOException {
        this.delegate.writeString(value);
    }

    @Override
    public void writeClob(byte[] value) throws IOException {
        this.delegate.writeClob(value);
    }

    @Override
    public void writeClob(byte[] value, int start, int len) throws IOException {
        this.delegate.writeClob(value, start, len);
    }

    @Override
    public void writeBlob(byte[] value) throws IOException {
        this.delegate.writeBlob(value);
    }

    @Override
    public void writeBlob(byte[] value, int start, int len) throws IOException {
        this.delegate.writeBlob(value, start, len);
    }

    @Override
    public <T> T asFacet(Class<T> facetType) {
        return null;
    }

    private static class InternalByteArrayOutputStream
    extends ByteArrayOutputStream {
        private InternalByteArrayOutputStream() {
        }

        public byte[] bytes() {
            return this.buf;
        }
    }

    public static interface Factory {
        public IonWriter create(OutputStream var1) throws IOException;
    }
}

