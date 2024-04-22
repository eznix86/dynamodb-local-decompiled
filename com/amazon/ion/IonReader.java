/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazon.ion;

import com.amazon.ion.Decimal;
import com.amazon.ion.IntegerSize;
import com.amazon.ion.IonType;
import com.amazon.ion.SymbolTable;
import com.amazon.ion.SymbolToken;
import com.amazon.ion.Timestamp;
import com.amazon.ion.facet.Faceted;
import java.io.Closeable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.Iterator;

public interface IonReader
extends Faceted,
Closeable {
    @Deprecated
    public boolean hasNext();

    public IonType next();

    public void stepIn();

    public void stepOut();

    public int getDepth();

    public SymbolTable getSymbolTable();

    public IonType getType();

    public IntegerSize getIntegerSize();

    public String[] getTypeAnnotations();

    public SymbolToken[] getTypeAnnotationSymbols();

    public Iterator<String> iterateTypeAnnotations();

    @Deprecated
    public int getFieldId();

    public String getFieldName();

    public SymbolToken getFieldNameSymbol();

    public boolean isNullValue();

    public boolean isInStruct();

    public boolean booleanValue();

    public int intValue();

    public long longValue();

    public BigInteger bigIntegerValue();

    public double doubleValue();

    public BigDecimal bigDecimalValue();

    public Decimal decimalValue();

    public Date dateValue();

    public Timestamp timestampValue();

    public String stringValue();

    public SymbolToken symbolValue();

    public int byteSize();

    public byte[] newBytes();

    public int getBytes(byte[] var1, int var2, int var3);
}

