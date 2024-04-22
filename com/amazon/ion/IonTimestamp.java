/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazon.ion;

import com.amazon.ion.IonValue;
import com.amazon.ion.NullValueException;
import com.amazon.ion.Timestamp;
import com.amazon.ion.UnknownSymbolException;
import java.math.BigDecimal;
import java.util.Date;

public interface IonTimestamp
extends IonValue {
    public Timestamp timestampValue();

    public Date dateValue();

    public long getMillis() throws NullValueException;

    public BigDecimal getDecimalMillis();

    public void setValue(Timestamp var1);

    public void setValue(BigDecimal var1, Integer var2);

    public void setValue(long var1, Integer var3);

    public void setMillis(long var1);

    public void setDecimalMillis(BigDecimal var1);

    public void setMillisUtc(long var1);

    public Integer getLocalOffset() throws NullValueException;

    public void setTime(Date var1);

    public void setCurrentTime();

    public void setCurrentTimeUtc();

    public void setLocalOffset(int var1) throws NullValueException;

    public void setLocalOffset(Integer var1) throws NullValueException;

    public void makeNull();

    @Override
    public IonTimestamp clone() throws UnknownSymbolException;
}

