/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazon.ion.impl;

import com.amazon.ion.Decimal;
import com.amazon.ion.IntegerSize;
import com.amazon.ion.IonCursor;
import com.amazon.ion.IonException;
import com.amazon.ion.IonReader;
import com.amazon.ion.IonType;
import com.amazon.ion.SymbolTable;
import com.amazon.ion.SymbolToken;
import com.amazon.ion.Timestamp;
import com.amazon.ion.UnknownSymbolException;
import com.amazon.ion.impl.IonReaderContinuableCore;
import com.amazon.ion.impl.SharedSymbolTable;
import com.amazon.ion.impl.SymbolTokenImpl;
import com.amazon.ion.impl._Private_Utils;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayDeque;
import java.util.Date;
import java.util.Iterator;
import java.util.Queue;

final class IonReaderNonContinuableSystem
implements IonReader {
    private final IonReaderContinuableCore reader;
    private IonType type = null;
    private IonType typeAfterIvm = null;
    private final Queue<Integer> pendingIvmSids = new ArrayDeque<Integer>(1);
    private int pendingIvmSid = -1;

    IonReaderNonContinuableSystem(IonReaderContinuableCore reader) {
        this.reader = reader;
        reader.registerIvmNotificationConsumer((x, y) -> this.pendingIvmSids.add(2));
    }

    @Override
    public boolean hasNext() {
        throw new UnsupportedOperationException("Not implemented -- use `next() != null`");
    }

    private boolean handleIvm() {
        Integer ivmSid = this.pendingIvmSids.poll();
        if (ivmSid != null) {
            if (this.typeAfterIvm == null) {
                this.typeAfterIvm = this.type;
            }
            this.type = IonType.SYMBOL;
            this.pendingIvmSid = ivmSid;
            return true;
        }
        if (this.pendingIvmSid != -1) {
            this.pendingIvmSid = -1;
            this.type = this.typeAfterIvm;
            this.typeAfterIvm = null;
            return true;
        }
        return false;
    }

    @Override
    public IonType next() {
        if (this.handleIvm()) {
            return this.type;
        }
        if (this.reader.nextValue() == IonCursor.Event.NEEDS_DATA) {
            if (this.handleIvm()) {
                return this.type;
            }
            this.reader.endStream();
            this.type = null;
        } else {
            this.type = this.reader.getType();
            this.handleIvm();
        }
        return this.type;
    }

    @Override
    public void stepIn() {
        this.reader.stepIntoContainer();
        this.type = null;
    }

    @Override
    public void stepOut() {
        this.reader.stepOutOfContainer();
        this.type = null;
    }

    @Override
    public int getDepth() {
        return this.reader.getDepth();
    }

    @Override
    public IonType getType() {
        return this.type;
    }

    private void prepareScalar() {
        IonCursor.Event event = this.reader.getCurrentEvent();
        if (event == IonCursor.Event.VALUE_READY) {
            return;
        }
        if (event != IonCursor.Event.START_SCALAR) {
            throw new IllegalStateException("Reader is not positioned on a scalar value.");
        }
        if (this.reader.fillValue() != IonCursor.Event.VALUE_READY) {
            throw new IonException("Unexpected EOF.");
        }
    }

    @Override
    public IntegerSize getIntegerSize() {
        if (this.getType() != IonType.INT) {
            return null;
        }
        this.prepareScalar();
        return this.reader.getIntegerSize();
    }

    @Override
    public boolean isNullValue() {
        return this.pendingIvmSid == -1 && this.reader.isNullValue();
    }

    @Override
    public boolean isInStruct() {
        return this.reader.isInStruct();
    }

    @Override
    public boolean booleanValue() {
        this.prepareScalar();
        return this.reader.booleanValue();
    }

    @Override
    public int intValue() {
        this.prepareScalar();
        return this.reader.intValue();
    }

    @Override
    public long longValue() {
        this.prepareScalar();
        return this.reader.longValue();
    }

    @Override
    public BigInteger bigIntegerValue() {
        this.prepareScalar();
        return this.reader.bigIntegerValue();
    }

    @Override
    public double doubleValue() {
        this.prepareScalar();
        return this.reader.doubleValue();
    }

    @Override
    public BigDecimal bigDecimalValue() {
        this.prepareScalar();
        return this.reader.bigDecimalValue();
    }

    @Override
    public Decimal decimalValue() {
        this.prepareScalar();
        return this.reader.decimalValue();
    }

    @Override
    public Date dateValue() {
        this.prepareScalar();
        return this.reader.dateValue();
    }

    @Override
    public Timestamp timestampValue() {
        this.prepareScalar();
        return this.reader.timestampValue();
    }

    @Override
    public String stringValue() {
        String value;
        if (this.pendingIvmSid != -1) {
            return this.getSymbolTable().findKnownSymbol(this.pendingIvmSid);
        }
        this.prepareScalar();
        if (this.type == IonType.SYMBOL) {
            int sid = this.reader.symbolValueId();
            value = this.getSymbolTable().findKnownSymbol(sid);
            if (value == null) {
                throw new UnknownSymbolException(sid);
            }
        } else {
            value = this.reader.stringValue();
        }
        return value;
    }

    @Override
    public int byteSize() {
        this.prepareScalar();
        return this.reader.byteSize();
    }

    @Override
    public byte[] newBytes() {
        this.prepareScalar();
        return this.reader.newBytes();
    }

    @Override
    public int getBytes(byte[] buffer, int offset, int len) {
        this.prepareScalar();
        return this.reader.getBytes(buffer, offset, len);
    }

    @Override
    public <T> T asFacet(Class<T> facetType) {
        return null;
    }

    @Override
    public SymbolTable getSymbolTable() {
        return SharedSymbolTable.getSystemSymbolTable(this.reader.getIonMajorVersion());
    }

    @Override
    public String[] getTypeAnnotations() {
        if (this.pendingIvmSid != -1 || !this.reader.hasAnnotations()) {
            return _Private_Utils.EMPTY_STRING_ARRAY;
        }
        int[] annotationIds = this.reader.getAnnotationIds();
        String[] annotations = new String[annotationIds.length];
        SymbolTable symbolTable = this.getSymbolTable();
        for (int i = 0; i < annotationIds.length; ++i) {
            int sid = annotationIds[i];
            String annotation = symbolTable.findKnownSymbol(sid);
            if (annotation == null) {
                throw new UnknownSymbolException(sid);
            }
            annotations[i] = annotation;
        }
        return annotations;
    }

    @Override
    public SymbolToken[] getTypeAnnotationSymbols() {
        if (this.pendingIvmSid != -1 || !this.reader.hasAnnotations()) {
            return SymbolToken.EMPTY_ARRAY;
        }
        int[] annotationIds = this.reader.getAnnotationIds();
        SymbolToken[] annotationSymbolTokens = new SymbolToken[annotationIds.length];
        SymbolTable symbolTable = this.getSymbolTable();
        for (int i = 0; i < annotationIds.length; ++i) {
            int sid = annotationIds[i];
            annotationSymbolTokens[i] = new SymbolTokenImpl(symbolTable.findKnownSymbol(sid), sid);
        }
        return annotationSymbolTokens;
    }

    @Override
    public Iterator<String> iterateTypeAnnotations() {
        if (this.pendingIvmSid != -1 || !this.reader.hasAnnotations()) {
            return _Private_Utils.emptyIterator();
        }
        return _Private_Utils.stringIterator(this.getTypeAnnotations());
    }

    @Override
    public int getFieldId() {
        return this.reader.getFieldId();
    }

    @Override
    public String getFieldName() {
        int sid = this.reader.getFieldId();
        if (sid < 0) {
            return null;
        }
        String name = this.getSymbolTable().findKnownSymbol(sid);
        if (name == null) {
            throw new UnknownSymbolException(sid);
        }
        return name;
    }

    @Override
    public SymbolToken getFieldNameSymbol() {
        int sid = this.reader.getFieldId();
        if (sid < 0) {
            return null;
        }
        return new SymbolTokenImpl(this.getSymbolTable().findKnownSymbol(sid), sid);
    }

    @Override
    public SymbolToken symbolValue() {
        int sid;
        if (this.pendingIvmSid != -1) {
            sid = this.pendingIvmSid;
        } else {
            this.prepareScalar();
            sid = this.reader.symbolValueId();
        }
        return new SymbolTokenImpl(this.getSymbolTable().findKnownSymbol(sid), sid);
    }

    @Override
    public void close() throws IOException {
        this.reader.close();
    }
}

