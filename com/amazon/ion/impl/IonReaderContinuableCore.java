/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazon.ion.impl;

import com.amazon.ion.Decimal;
import com.amazon.ion.IntegerSize;
import com.amazon.ion.IonCursor;
import com.amazon.ion.IonType;
import com.amazon.ion.IvmNotificationConsumer;
import com.amazon.ion.Timestamp;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

interface IonReaderContinuableCore
extends IonCursor {
    public int getDepth();

    public IonType getType();

    public IntegerSize getIntegerSize();

    public boolean isNullValue();

    public boolean isInStruct();

    @Deprecated
    public int getFieldId();

    @Deprecated
    public int[] getAnnotationIds();

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

    @Deprecated
    public int symbolValueId();

    public int byteSize();

    public byte[] newBytes();

    public int getBytes(byte[] var1, int var2, int var3);

    public int getIonMajorVersion();

    public int getIonMinorVersion();

    public void registerIvmNotificationConsumer(IvmNotificationConsumer var1);

    public boolean hasAnnotations();
}

