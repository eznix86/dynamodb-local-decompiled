/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazon.ion.impl.bin;

import com.amazon.ion.Decimal;
import com.amazon.ion.IonDatagram;
import com.amazon.ion.IonReader;
import com.amazon.ion.IonType;
import com.amazon.ion.IonValue;
import com.amazon.ion.SymbolToken;
import com.amazon.ion.Timestamp;
import com.amazon.ion.impl._Private_ByteTransferReader;
import com.amazon.ion.impl._Private_ByteTransferSink;
import com.amazon.ion.impl._Private_IonWriter;
import com.amazon.ion.impl._Private_SymtabExtendsCache;
import com.amazon.ion.impl._Private_Utils;
import com.amazon.ion.impl.bin._Private_IonManagedWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Date;

abstract class AbstractIonWriter
implements _Private_ByteTransferSink,
_Private_IonWriter {
    private final _Private_SymtabExtendsCache symtabExtendsCache;

    AbstractIonWriter(WriteValueOptimization optimization) {
        this.symtabExtendsCache = optimization == WriteValueOptimization.COPY_OPTIMIZED ? new _Private_SymtabExtendsCache() : null;
    }

    @Override
    public final void writeValue(IonValue value) throws IOException {
        if (value != null) {
            if (value instanceof IonDatagram) {
                this.finish();
            }
            value.writeTo(this);
        }
    }

    @Override
    public final void writeValue(IonReader reader) throws IOException {
        _Private_ByteTransferReader transferReader;
        IonType type = reader.getType();
        if (this.isStreamCopyOptimized() && (transferReader = reader.asFacet(_Private_ByteTransferReader.class)) != null && (_Private_Utils.isNonSymbolScalar(type) || this.symtabExtendsCache.symtabsCompat(this.getSymbolTable(), reader.getSymbolTable()))) {
            transferReader.transferCurrentValue(this);
            return;
        }
        this.writeValueRecursive(reader);
    }

    public final void writeValueRecursive(IonReader reader) throws IOException {
        SymbolToken[] annotations;
        IonType type = reader.getType();
        SymbolToken fieldName = reader.getFieldNameSymbol();
        if (fieldName != null && !this.isFieldNameSet() && this.isInStruct()) {
            this.setFieldNameSymbol(fieldName);
        }
        if ((annotations = reader.getTypeAnnotationSymbols()).length > 0) {
            this.setTypeAnnotationSymbols(annotations);
        }
        if (reader.isNullValue()) {
            this.writeNull(type);
            return;
        }
        block0 : switch (type) {
            case BOOL: {
                boolean booleanValue = reader.booleanValue();
                this.writeBool(booleanValue);
                break;
            }
            case INT: {
                switch (reader.getIntegerSize()) {
                    case INT: {
                        int intValue = reader.intValue();
                        this.writeInt(intValue);
                        break block0;
                    }
                    case LONG: {
                        long longValue = reader.longValue();
                        this.writeInt(longValue);
                        break block0;
                    }
                    case BIG_INTEGER: {
                        BigInteger bigIntegerValue = reader.bigIntegerValue();
                        this.writeInt(bigIntegerValue);
                        break block0;
                    }
                }
                throw new IllegalStateException();
            }
            case FLOAT: {
                double doubleValue = reader.doubleValue();
                this.writeFloat(doubleValue);
                break;
            }
            case DECIMAL: {
                Decimal decimalValue = reader.decimalValue();
                this.writeDecimal(decimalValue);
                break;
            }
            case TIMESTAMP: {
                Timestamp timestampValue = reader.timestampValue();
                this.writeTimestamp(timestampValue);
                break;
            }
            case SYMBOL: {
                SymbolToken symbolToken = reader.symbolValue();
                this.writeSymbolToken(symbolToken);
                break;
            }
            case STRING: {
                String stringValue = reader.stringValue();
                this.writeString(stringValue);
                break;
            }
            case CLOB: {
                byte[] clobValue = reader.newBytes();
                this.writeClob(clobValue);
                break;
            }
            case BLOB: {
                byte[] blobValue = reader.newBytes();
                this.writeBlob(blobValue);
                break;
            }
            case LIST: 
            case SEXP: 
            case STRUCT: {
                reader.stepIn();
                this.stepIn(type);
                while (reader.next() != null) {
                    this.writeValue(reader);
                }
                this.stepOut();
                reader.stepOut();
                break;
            }
            default: {
                throw new IllegalStateException("Unexpected type: " + (Object)((Object)type));
            }
        }
    }

    @Override
    public final void writeValues(IonReader reader) throws IOException {
        if (reader.getType() != null) {
            this.writeValue(reader);
        }
        while (reader.next() != null) {
            this.writeValue(reader);
        }
    }

    @Override
    public final void writeTimestampUTC(Date value) throws IOException {
        this.writeTimestamp(Timestamp.forDateZ(value));
    }

    @Override
    public final boolean isStreamCopyOptimized() {
        return this.symtabExtendsCache != null;
    }

    @Override
    public <T> T asFacet(Class<T> facetType) {
        if (facetType == _Private_IonManagedWriter.class) {
            return facetType.cast(this);
        }
        return null;
    }

    public abstract void writeString(byte[] var1, int var2, int var3) throws IOException;

    static enum WriteValueOptimization {
        NONE,
        COPY_OPTIMIZED;

    }
}

