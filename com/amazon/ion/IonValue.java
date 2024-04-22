/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazon.ion;

import com.amazon.ion.IonContainer;
import com.amazon.ion.IonSystem;
import com.amazon.ion.IonType;
import com.amazon.ion.IonWriter;
import com.amazon.ion.SymbolTable;
import com.amazon.ion.SymbolToken;
import com.amazon.ion.UnknownSymbolException;
import com.amazon.ion.ValueVisitor;
import com.amazon.ion.system.IonTextWriterBuilder;

public interface IonValue
extends Cloneable {
    public static final IonValue[] EMPTY_ARRAY = new IonValue[0];

    public IonType getType();

    public boolean isNullValue();

    public boolean isReadOnly();

    public SymbolTable getSymbolTable();

    public String getFieldName();

    public SymbolToken getFieldNameSymbol();

    @Deprecated
    public int getFieldId();

    public IonContainer getContainer();

    public boolean removeFromContainer();

    public IonValue topLevelValue();

    public String[] getTypeAnnotations();

    public SymbolToken[] getTypeAnnotationSymbols();

    public boolean hasTypeAnnotation(String var1);

    public void setTypeAnnotations(String ... var1);

    public void setTypeAnnotationSymbols(SymbolToken ... var1);

    public void clearTypeAnnotations();

    public void addTypeAnnotation(String var1);

    public void removeTypeAnnotation(String var1);

    public void writeTo(IonWriter var1);

    public void accept(ValueVisitor var1) throws Exception;

    public void makeReadOnly();

    public IonSystem getSystem();

    public IonValue clone() throws UnknownSymbolException;

    public String toString();

    public String toPrettyString();

    public String toString(IonTextWriterBuilder var1);

    public boolean equals(Object var1);

    public int hashCode();
}

