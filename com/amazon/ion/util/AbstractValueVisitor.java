/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazon.ion.util;

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
import com.amazon.ion.IonValue;
import com.amazon.ion.ValueVisitor;

public abstract class AbstractValueVisitor
implements ValueVisitor {
    protected void defaultVisit(IonValue value) throws Exception {
        throw new UnsupportedOperationException();
    }

    @Override
    public void visit(IonBlob value) throws Exception {
        this.defaultVisit(value);
    }

    @Override
    public void visit(IonBool value) throws Exception {
        this.defaultVisit(value);
    }

    @Override
    public void visit(IonClob value) throws Exception {
        this.defaultVisit(value);
    }

    @Override
    public void visit(IonDatagram value) throws Exception {
        this.defaultVisit(value);
    }

    @Override
    public void visit(IonDecimal value) throws Exception {
        this.defaultVisit(value);
    }

    @Override
    public void visit(IonFloat value) throws Exception {
        this.defaultVisit(value);
    }

    @Override
    public void visit(IonInt value) throws Exception {
        this.defaultVisit(value);
    }

    @Override
    public void visit(IonList value) throws Exception {
        this.defaultVisit(value);
    }

    @Override
    public void visit(IonNull value) throws Exception {
        this.defaultVisit(value);
    }

    @Override
    public void visit(IonSexp value) throws Exception {
        this.defaultVisit(value);
    }

    @Override
    public void visit(IonString value) throws Exception {
        this.defaultVisit(value);
    }

    @Override
    public void visit(IonStruct value) throws Exception {
        this.defaultVisit(value);
    }

    @Override
    public void visit(IonSymbol value) throws Exception {
        this.defaultVisit(value);
    }

    @Override
    public void visit(IonTimestamp value) throws Exception {
        this.defaultVisit(value);
    }
}

