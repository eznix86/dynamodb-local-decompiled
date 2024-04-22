/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazon.ion.impl;

import com.amazon.ion.IonType;
import com.amazon.ion.SymbolTable;
import com.amazon.ion.SymbolToken;
import com.amazon.ion.Timestamp;
import com.amazon.ion.impl.IonWriterSystemText;
import com.amazon.ion.impl._Private_CallbackBuilder;
import com.amazon.ion.impl._Private_IonTextWriterBuilder;
import com.amazon.ion.impl._Private_MarkupCallback;
import com.amazon.ion.util._Private_FastAppendable;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;

class IonWriterSystemTextMarkup
extends IonWriterSystemText {
    private IonType myTypeBeingWritten;
    private final _Private_MarkupCallback myCallback;

    public IonWriterSystemTextMarkup(SymbolTable defaultSystemSymtab, _Private_IonTextWriterBuilder options, _Private_FastAppendable out) {
        this(defaultSystemSymtab, options, out, options.getCallbackBuilder());
    }

    private IonWriterSystemTextMarkup(SymbolTable defaultSystemSymtab, _Private_IonTextWriterBuilder options, _Private_FastAppendable out, _Private_CallbackBuilder builder) {
        this(defaultSystemSymtab, options, builder.build(out));
    }

    private IonWriterSystemTextMarkup(SymbolTable defaultSystemSymtab, _Private_IonTextWriterBuilder options, _Private_MarkupCallback callback) {
        super(defaultSystemSymtab, options, callback.getAppendable());
        this.myCallback = callback;
    }

    @Override
    void startValue() throws IOException {
        super.startValue();
        this.myCallback.beforeValue(this.myTypeBeingWritten);
    }

    @Override
    void closeValue() throws IOException {
        this.myCallback.afterValue(this.myTypeBeingWritten);
        super.closeValue();
    }

    @Override
    protected void writeFieldNameToken(SymbolToken sym) throws IOException {
        this.myCallback.beforeFieldName(this.myTypeBeingWritten, sym);
        super.writeFieldNameToken(sym);
        this.myCallback.afterFieldName(this.myTypeBeingWritten, sym);
    }

    @Override
    protected void writeAnnotations(SymbolToken[] annotations) throws IOException {
        this.myCallback.beforeAnnotations(this.myTypeBeingWritten);
        super.writeAnnotations(annotations);
        this.myCallback.afterAnnotations(this.myTypeBeingWritten);
    }

    @Override
    protected void writeAnnotationToken(SymbolToken sym) throws IOException {
        this.myCallback.beforeEachAnnotation(this.myTypeBeingWritten, sym);
        super.writeAnnotationToken(sym);
        this.myCallback.afterEachAnnotation(this.myTypeBeingWritten, sym);
    }

    @Override
    protected boolean writeSeparator(boolean followingLongString) throws IOException {
        IonType containerType = this.getContainer();
        if (this._pending_separator) {
            this.myCallback.beforeSeparator(containerType);
        }
        followingLongString = super.writeSeparator(followingLongString);
        if (this._pending_separator) {
            this.myCallback.afterSeparator(containerType);
        }
        return followingLongString;
    }

    @Override
    public void stepIn(IonType containerType) throws IOException {
        this.myTypeBeingWritten = containerType;
        super.stepIn(containerType);
        this.myCallback.afterStepIn(containerType);
        this.myTypeBeingWritten = null;
    }

    @Override
    public void stepOut() throws IOException {
        this.myTypeBeingWritten = this.getContainer();
        this.myCallback.beforeStepOut(this.myTypeBeingWritten);
        super.stepOut();
        this.myTypeBeingWritten = null;
    }

    @Override
    public void writeBlob(byte[] value, int start, int len) throws IOException {
        this.myTypeBeingWritten = IonType.BLOB;
        super.writeBlob(value, start, len);
        this.myTypeBeingWritten = null;
    }

    @Override
    public void writeBool(boolean value) throws IOException {
        this.myTypeBeingWritten = IonType.BOOL;
        super.writeBool(value);
        this.myTypeBeingWritten = null;
    }

    @Override
    public void writeClob(byte[] value, int start, int len) throws IOException {
        this.myTypeBeingWritten = IonType.CLOB;
        super.writeClob(value, start, len);
        this.myTypeBeingWritten = null;
    }

    @Override
    public void writeDecimal(BigDecimal value) throws IOException {
        this.myTypeBeingWritten = IonType.DECIMAL;
        super.writeDecimal(value);
        this.myTypeBeingWritten = null;
    }

    @Override
    public void writeFloat(double value) throws IOException {
        this.myTypeBeingWritten = IonType.FLOAT;
        super.writeFloat(value);
        this.myTypeBeingWritten = null;
    }

    @Override
    public void writeInt(long value) throws IOException {
        this.myTypeBeingWritten = IonType.INT;
        super.writeInt(value);
        this.myTypeBeingWritten = null;
    }

    @Override
    public void writeInt(BigInteger value) throws IOException {
        this.myTypeBeingWritten = IonType.INT;
        super.writeInt(value);
        this.myTypeBeingWritten = null;
    }

    @Override
    public void writeNull() throws IOException {
        this.myTypeBeingWritten = IonType.NULL;
        super.writeNull();
        this.myTypeBeingWritten = null;
    }

    @Override
    public void writeNull(IonType type) throws IOException {
        this.myTypeBeingWritten = type;
        super.writeNull(type);
        this.myTypeBeingWritten = null;
    }

    @Override
    public void writeString(String value) throws IOException {
        this.myTypeBeingWritten = IonType.STRING;
        super.writeString(value);
        this.myTypeBeingWritten = null;
    }

    @Override
    public void writeSymbolAsIs(String value) throws IOException {
        this.myTypeBeingWritten = IonType.SYMBOL;
        super.writeSymbolAsIs(value);
        this.myTypeBeingWritten = null;
    }

    @Override
    public void writeSymbolAsIs(int value) throws IOException {
        this.myTypeBeingWritten = IonType.SYMBOL;
        super.writeSymbolAsIs(value);
        this.myTypeBeingWritten = null;
    }

    @Override
    public void writeTimestamp(Timestamp value) throws IOException {
        this.myTypeBeingWritten = IonType.TIMESTAMP;
        super.writeTimestamp(value);
        this.myTypeBeingWritten = null;
    }
}

