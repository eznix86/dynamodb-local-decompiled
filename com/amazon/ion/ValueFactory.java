/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazon.ion;

import com.amazon.ion.ContainedValueException;
import com.amazon.ion.IonBlob;
import com.amazon.ion.IonBool;
import com.amazon.ion.IonClob;
import com.amazon.ion.IonDecimal;
import com.amazon.ion.IonException;
import com.amazon.ion.IonFloat;
import com.amazon.ion.IonInt;
import com.amazon.ion.IonList;
import com.amazon.ion.IonNull;
import com.amazon.ion.IonSequence;
import com.amazon.ion.IonSexp;
import com.amazon.ion.IonString;
import com.amazon.ion.IonStruct;
import com.amazon.ion.IonSymbol;
import com.amazon.ion.IonTimestamp;
import com.amazon.ion.IonType;
import com.amazon.ion.IonValue;
import com.amazon.ion.SymbolToken;
import com.amazon.ion.Timestamp;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collection;

public interface ValueFactory {
    public IonBlob newNullBlob();

    public IonBlob newBlob(byte[] var1);

    public IonBlob newBlob(byte[] var1, int var2, int var3);

    public IonBool newNullBool();

    public IonBool newBool(boolean var1);

    public IonBool newBool(Boolean var1);

    public IonClob newNullClob();

    public IonClob newClob(byte[] var1);

    public IonClob newClob(byte[] var1, int var2, int var3);

    public IonDecimal newNullDecimal();

    public IonDecimal newDecimal(long var1);

    public IonDecimal newDecimal(double var1);

    public IonDecimal newDecimal(BigInteger var1);

    public IonDecimal newDecimal(BigDecimal var1);

    public IonFloat newNullFloat();

    public IonFloat newFloat(long var1);

    public IonFloat newFloat(double var1);

    public IonInt newNullInt();

    public IonInt newInt(int var1);

    public IonInt newInt(long var1);

    public IonInt newInt(Number var1);

    public IonList newNullList();

    public IonList newEmptyList();

    @Deprecated
    public IonList newList(Collection<? extends IonValue> var1) throws ContainedValueException, NullPointerException;

    public IonList newList(IonSequence var1) throws ContainedValueException, NullPointerException;

    public IonList newList(IonValue ... var1) throws ContainedValueException, NullPointerException;

    public IonList newList(int[] var1);

    public IonList newList(long[] var1);

    public IonNull newNull();

    public IonValue newNull(IonType var1);

    public IonSexp newNullSexp();

    public IonSexp newEmptySexp();

    @Deprecated
    public IonSexp newSexp(Collection<? extends IonValue> var1) throws ContainedValueException, NullPointerException;

    public IonSexp newSexp(IonSequence var1) throws ContainedValueException, NullPointerException;

    public IonSexp newSexp(IonValue ... var1) throws ContainedValueException, NullPointerException;

    public IonSexp newSexp(int[] var1);

    public IonSexp newSexp(long[] var1);

    public IonString newNullString();

    public IonString newString(String var1);

    public IonStruct newNullStruct();

    public IonStruct newEmptyStruct();

    public IonSymbol newNullSymbol();

    public IonSymbol newSymbol(String var1);

    public IonSymbol newSymbol(SymbolToken var1);

    public IonTimestamp newNullTimestamp();

    public IonTimestamp newTimestamp(Timestamp var1);

    public <T extends IonValue> T clone(T var1) throws IonException;
}

