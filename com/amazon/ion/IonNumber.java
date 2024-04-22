/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazon.ion;

import com.amazon.ion.IonValue;
import java.math.BigDecimal;

public interface IonNumber
extends IonValue {
    public BigDecimal bigDecimalValue();

    public boolean isNumericValue();
}

