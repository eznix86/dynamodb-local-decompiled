/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazon.ion;

import com.amazon.ion.IonReader;
import com.amazon.ion.IonType;
import com.amazon.ion.IonValue;
import com.amazon.ion.SymbolTable;
import com.amazon.ion.SymbolToken;
import com.amazon.ion.Timestamp;
import com.amazon.ion.facet.Faceted;
import java.io.Closeable;
import java.io.Flushable;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

public interface IonWriter
extends Faceted,
Closeable,
Flushable {
    public SymbolTable getSymbolTable();

    @Override
    public void flush() throws IOException;

    public void finish() throws IOException;

    @Override
    public void close() throws IOException;

    public void setFieldName(String var1);

    public void setFieldNameSymbol(SymbolToken var1);

    public void setTypeAnnotations(String ... var1);

    public void setTypeAnnotationSymbols(SymbolToken ... var1);

    public void addTypeAnnotation(String var1);

    public void stepIn(IonType var1) throws IOException;

    public void stepOut() throws IOException;

    public boolean isInStruct();

    @Deprecated
    public void writeValue(IonValue var1) throws IOException;

    public void writeValue(IonReader var1) throws IOException;

    public void writeValues(IonReader var1) throws IOException;

    public void writeNull() throws IOException;

    public void writeNull(IonType var1) throws IOException;

    public void writeBool(boolean var1) throws IOException;

    public void writeInt(long var1) throws IOException;

    public void writeInt(BigInteger var1) throws IOException;

    public void writeFloat(double var1) throws IOException;

    public void writeDecimal(BigDecimal var1) throws IOException;

    public void writeTimestamp(Timestamp var1) throws IOException;

    @Deprecated
    public void writeTimestampUTC(Date var1) throws IOException;

    public void writeSymbol(String var1) throws IOException;

    public void writeSymbolToken(SymbolToken var1) throws IOException;

    public void writeString(String var1) throws IOException;

    public void writeClob(byte[] var1) throws IOException;

    public void writeClob(byte[] var1, int var2, int var3) throws IOException;

    public void writeBlob(byte[] var1) throws IOException;

    public void writeBlob(byte[] var1, int var2, int var3) throws IOException;
}

