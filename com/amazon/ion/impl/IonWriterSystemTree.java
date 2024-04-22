/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazon.ion.impl;

import com.amazon.ion.IonBlob;
import com.amazon.ion.IonBool;
import com.amazon.ion.IonCatalog;
import com.amazon.ion.IonClob;
import com.amazon.ion.IonContainer;
import com.amazon.ion.IonDatagram;
import com.amazon.ion.IonDecimal;
import com.amazon.ion.IonException;
import com.amazon.ion.IonFloat;
import com.amazon.ion.IonInt;
import com.amazon.ion.IonSequence;
import com.amazon.ion.IonString;
import com.amazon.ion.IonStruct;
import com.amazon.ion.IonSymbol;
import com.amazon.ion.IonTimestamp;
import com.amazon.ion.IonType;
import com.amazon.ion.IonValue;
import com.amazon.ion.SymbolTable;
import com.amazon.ion.SymbolToken;
import com.amazon.ion.Timestamp;
import com.amazon.ion.ValueFactory;
import com.amazon.ion.impl.IonWriterSystem;
import com.amazon.ion.impl.LocalSymbolTableAsStruct;
import com.amazon.ion.impl.SymbolTokenImpl;
import com.amazon.ion.impl._Private_IonDatagram;
import com.amazon.ion.impl._Private_IonValue;
import com.amazon.ion.impl._Private_Utils;
import com.amazon.ion.impl._Private_ValueFactory;
import com.amazon.ion.system.IonWriterBuilder;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;

final class IonWriterSystemTree
extends IonWriterSystem {
    private final LocalSymbolTableAsStruct.Factory _lst_factory;
    private final ValueFactory _factory;
    private final IonCatalog _catalog;
    private final int _initialDepth;
    private boolean _in_struct;
    private IonContainer _current_parent;
    private int _parent_stack_top = 0;
    private IonContainer[] _parent_stack = new IonContainer[10];

    protected IonWriterSystemTree(SymbolTable defaultSystemSymbolTable, IonCatalog catalog, IonContainer rootContainer, IonWriterBuilder.InitialIvmHandling initialIvmHandling) {
        super(defaultSystemSymbolTable, initialIvmHandling, IonWriterBuilder.IvmMinimizing.ADJACENT, true);
        this._factory = rootContainer.getSystem();
        this._lst_factory = (LocalSymbolTableAsStruct.Factory)((_Private_ValueFactory)this._factory).getLstFactory();
        this._catalog = catalog;
        this._current_parent = rootContainer;
        this._in_struct = this._current_parent instanceof IonStruct;
        int depth = 0;
        if (!(rootContainer instanceof IonDatagram)) {
            IonContainer c = rootContainer;
            do {
                ++depth;
            } while ((c = c.getContainer()) != null);
        }
        this._initialDepth = depth;
    }

    @Override
    public int getDepth() {
        return this._parent_stack_top + this._initialDepth;
    }

    protected IonType getContainer() {
        IonType containerType = this._parent_stack_top > 0 ? this._parent_stack[this._parent_stack_top - 1].getType() : IonType.DATAGRAM;
        return containerType;
    }

    @Override
    public boolean isInStruct() {
        return this._in_struct;
    }

    protected IonValue get_root() {
        IonContainer container = this._parent_stack_top > 0 ? this._parent_stack[0] : this._current_parent;
        return container;
    }

    private void pushParent(IonContainer newParent) {
        int oldlen = this._parent_stack.length;
        if (this._parent_stack_top >= oldlen) {
            int newlen = oldlen * 2;
            IonContainer[] temp = new IonContainer[newlen];
            System.arraycopy(this._parent_stack, 0, temp, 0, oldlen);
            this._parent_stack = temp;
        }
        this._parent_stack[this._parent_stack_top++] = this._current_parent;
        this._current_parent = newParent;
        this._in_struct = this._current_parent instanceof IonStruct;
    }

    private void popParent() {
        if (this._parent_stack_top < 1) {
            throw new IllegalStateException("Cannot stepOut any further, already at top level.");
        }
        --this._parent_stack_top;
        this._current_parent = this._parent_stack[this._parent_stack_top];
        this._in_struct = this._current_parent instanceof IonStruct;
    }

    private void append(IonValue value) {
        try {
            super.startValue();
        } catch (IOException e) {
            throw new IonException(e);
        }
        if (this.hasAnnotations()) {
            SymbolToken[] annotations = this.getTypeAnnotationSymbols();
            ((_Private_IonValue)value).setTypeAnnotationSymbols(annotations);
            this.clearAnnotations();
        }
        if (this._in_struct) {
            SymbolToken sym = this.assumeFieldNameSymbol();
            IonStruct struct = (IonStruct)this._current_parent;
            struct.add(sym, value);
            this.clearFieldName();
        } else {
            ((IonSequence)this._current_parent).add(value);
        }
    }

    @Override
    public void stepIn(IonType containerType) throws IOException {
        IonContainer v;
        switch (containerType) {
            case LIST: {
                v = this._factory.newEmptyList();
                break;
            }
            case SEXP: {
                v = this._factory.newEmptySexp();
                break;
            }
            case STRUCT: {
                v = this._factory.newEmptyStruct();
                break;
            }
            default: {
                throw new IllegalArgumentException();
            }
        }
        this.append(v);
        this.pushParent(v);
    }

    @Override
    public void stepOut() throws IOException {
        _Private_IonValue prior = (_Private_IonValue)((Object)this._current_parent);
        this.popParent();
        if (this._current_parent instanceof IonDatagram && _Private_Utils.valueIsLocalSymbolTable(prior)) {
            SymbolTable symbol_table = this._lst_factory.newLocalSymtab(this._catalog, (IonStruct)((Object)prior));
            this.setSymbolTable(symbol_table);
        }
    }

    @Override
    void writeIonVersionMarkerAsIs(SymbolTable systemSymtab) throws IOException {
        this.startValue();
        IonValue root = this.get_root();
        ((_Private_IonDatagram)root).appendTrailingSymbolTable(systemSymtab);
        this.endValue();
    }

    @Override
    void writeLocalSymtab(SymbolTable symtab) throws IOException {
        IonValue root = this.get_root();
        ((_Private_IonDatagram)root).appendTrailingSymbolTable(symtab);
        super.writeLocalSymtab(symtab);
    }

    @Override
    final SymbolTable inject_local_symbol_table() throws IOException {
        return this._lst_factory.newLocalSymtab(this.getSymbolTable(), new SymbolTable[0]);
    }

    @Override
    public void writeNull(IonType type) throws IOException {
        IonValue v = this._factory.newNull(type);
        this.append(v);
    }

    @Override
    public void writeBool(boolean value) throws IOException {
        IonBool v = this._factory.newBool(value);
        this.append(v);
    }

    public void writeInt(int value) throws IOException {
        IonInt v = this._factory.newInt(value);
        this.append(v);
    }

    @Override
    public void writeInt(long value) throws IOException {
        IonInt v = this._factory.newInt(value);
        this.append(v);
    }

    @Override
    public void writeInt(BigInteger value) throws IOException {
        IonInt v = this._factory.newInt(value);
        this.append(v);
    }

    @Override
    public void writeFloat(double value) throws IOException {
        IonFloat v = this._factory.newNullFloat();
        v.setValue(value);
        this.append(v);
    }

    @Override
    public void writeDecimal(BigDecimal value) throws IOException {
        IonDecimal v = this._factory.newNullDecimal();
        v.setValue(value);
        this.append(v);
    }

    @Override
    public void writeTimestamp(Timestamp value) throws IOException {
        IonTimestamp v = this._factory.newTimestamp(value);
        this.append(v);
    }

    @Override
    public void writeString(String value) throws IOException {
        IonString v = this._factory.newString(value);
        this.append(v);
    }

    @Override
    void writeSymbolAsIs(int symbolId) {
        String name = this.getSymbolTable().findKnownSymbol(symbolId);
        SymbolTokenImpl is = new SymbolTokenImpl(name, symbolId);
        IonSymbol v = this._factory.newSymbol(is);
        this.append(v);
    }

    @Override
    public void writeSymbolAsIs(String value) {
        IonSymbol v = this._factory.newSymbol(value);
        this.append(v);
    }

    @Override
    public void writeClob(byte[] value, int start, int len) throws IOException {
        IonClob v = this._factory.newClob(value, start, len);
        this.append(v);
    }

    @Override
    public void writeBlob(byte[] value, int start, int len) throws IOException {
        IonBlob v = this._factory.newBlob(value, start, len);
        this.append(v);
    }

    @Override
    public void flush() {
    }

    @Override
    public void close() {
    }
}

