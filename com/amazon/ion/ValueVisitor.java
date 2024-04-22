/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazon.ion;

import com.amazon.ion.IonBlob;
import com.amazon.ion.IonBool;
import com.amazon.ion.IonClob;
import com.amazon.ion.IonDatagram;
import com.amazon.ion.IonDecimal;
import com.amazon.ion.IonFloat;
import com.amazon.ion.IonInt;
import com.amazon.ion.IonList;
import com.amazon.ion.IonNull;
import com.amazon.ion.IonSexp;
import com.amazon.ion.IonString;
import com.amazon.ion.IonStruct;
import com.amazon.ion.IonSymbol;
import com.amazon.ion.IonTimestamp;

public interface ValueVisitor {
    public void visit(IonBlob var1) throws Exception;

    public void visit(IonBool var1) throws Exception;

    public void visit(IonClob var1) throws Exception;

    public void visit(IonDatagram var1) throws Exception;

    public void visit(IonDecimal var1) throws Exception;

    public void visit(IonFloat var1) throws Exception;

    public void visit(IonInt var1) throws Exception;

    public void visit(IonList var1) throws Exception;

    public void visit(IonNull var1) throws Exception;

    public void visit(IonSexp var1) throws Exception;

    public void visit(IonString var1) throws Exception;

    public void visit(IonStruct var1) throws Exception;

    public void visit(IonSymbol var1) throws Exception;

    public void visit(IonTimestamp var1) throws Exception;
}

