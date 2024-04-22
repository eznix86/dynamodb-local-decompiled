/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazon.ion.impl;

import com.amazon.ion.IonCatalog;
import com.amazon.ion.IonDatagram;
import com.amazon.ion.IonStruct;
import com.amazon.ion.IonSymbol;
import com.amazon.ion.IonType;
import com.amazon.ion.IonValue;
import com.amazon.ion.SeekableReader;
import com.amazon.ion.Span;
import com.amazon.ion.SpanProvider;
import com.amazon.ion.SymbolTable;
import com.amazon.ion.impl.DowncastingFaceted;
import com.amazon.ion.impl.IonReaderTreeSystem;
import com.amazon.ion.impl._Private_LocalSymbolTableFactory;
import com.amazon.ion.impl._Private_ReaderWriter;

final class IonReaderTreeUserX
extends IonReaderTreeSystem
implements _Private_ReaderWriter {
    private final _Private_LocalSymbolTableFactory _lstFactory;
    IonCatalog _catalog;
    private SymbolTable _symbols;
    private int _symbol_table_top = 0;
    private SymbolTable[] _symbol_table_stack = new SymbolTable[3];

    public IonReaderTreeUserX(IonValue value, IonCatalog catalog, _Private_LocalSymbolTableFactory lstFactory) {
        super(value);
        this._catalog = catalog;
        this._lstFactory = lstFactory;
    }

    @Override
    void re_init(IonValue value, boolean hoisted) {
        super.re_init(value, hoisted);
        this._symbols = this._system_symtab;
    }

    @Override
    public SymbolTable getSymbolTable() {
        return this._symbols;
    }

    @Override
    public boolean hasNext() {
        return this.next_helper_user();
    }

    @Override
    public IonType next() {
        if (!this.next_helper_user()) {
            this._curr = null;
            return null;
        }
        this._curr = this._next;
        this._next = null;
        return this._curr.getType();
    }

    boolean next_helper_user() {
        IonType next_type;
        if (this._eof) {
            return false;
        }
        if (this._next != null) {
            return true;
        }
        this.clear_system_value_stack();
        while (true) {
            SymbolTable symtab;
            next_type = this.next_helper_system();
            if (this._top != 0 || !(this._parent instanceof IonDatagram)) break;
            if (IonType.SYMBOL.equals((Object)next_type)) {
                SymbolTable symbols;
                String name;
                assert (this._next instanceof IonSymbol);
                IonSymbol sym = (IonSymbol)((Object)this._next);
                if (sym.isNullValue()) break;
                int sid = sym.getSymbolId();
                if (sid == -1 && (name = sym.stringValue()) != null) {
                    sid = this._system_symtab.findSymbol(name);
                }
                if (sid != 2 || this._next.getTypeAnnotationSymbols().length != 0) break;
                this._symbols = symbols = this._system_symtab;
                this.push_symbol_table(symbols);
                this._next = null;
                continue;
            }
            if (!IonType.STRUCT.equals((Object)next_type) || this._next.findTypeAnnotation("$ion_symbol_table") != 0) break;
            assert (this._next instanceof IonStruct);
            IonReaderTreeUserX reader = new IonReaderTreeUserX(this._next, this._catalog, this._lstFactory);
            reader._symbols = this._symbols;
            this._symbols = symtab = this._lstFactory.newLocalSymtab(this._catalog, reader, false);
            this.push_symbol_table(symtab);
            this._next = null;
        }
        return next_type != null;
    }

    private void clear_system_value_stack() {
        while (this._symbol_table_top > 0) {
            --this._symbol_table_top;
            this._symbol_table_stack[this._symbol_table_top] = null;
        }
    }

    private void push_symbol_table(SymbolTable symbols) {
        assert (symbols != null);
        if (this._symbol_table_top >= this._symbol_table_stack.length) {
            int new_len = this._symbol_table_stack.length * 2;
            SymbolTable[] temp = new SymbolTable[new_len];
            System.arraycopy(this._symbol_table_stack, 0, temp, 0, this._symbol_table_stack.length);
            this._symbol_table_stack = temp;
        }
        this._symbol_table_stack[this._symbol_table_top++] = symbols;
    }

    @Override
    public SymbolTable pop_passed_symbol_table() {
        if (this._symbol_table_top <= 0) {
            return null;
        }
        --this._symbol_table_top;
        SymbolTable symbols = this._symbol_table_stack[this._symbol_table_top];
        this._symbol_table_stack[this._symbol_table_top] = null;
        return symbols;
    }

    private final Span currentSpanImpl() {
        if (this._curr == null) {
            throw new IllegalStateException("Reader has no current value");
        }
        TreeSpan span = new TreeSpan();
        span._value = this._curr;
        return span;
    }

    private void hoistImpl(Span span) {
        if (!(span instanceof TreeSpan)) {
            throw new IllegalArgumentException("Span not appropriate for this reader");
        }
        TreeSpan treeSpan = (TreeSpan)span;
        this.re_init(treeSpan._value, true);
    }

    @Override
    public <T> T asFacet(Class<T> facetType) {
        if (facetType == SeekableReader.class || facetType == SpanProvider.class) {
            return facetType.cast(new SeekableReaderFacet());
        }
        return super.asFacet(facetType);
    }

    private class SeekableReaderFacet
    implements SeekableReader {
        private SeekableReaderFacet() {
        }

        @Override
        public Span currentSpan() {
            return IonReaderTreeUserX.this.currentSpanImpl();
        }

        @Override
        public void hoist(Span span) {
            IonReaderTreeUserX.this.hoistImpl(span);
        }
    }

    private static final class TreeSpan
    extends DowncastingFaceted
    implements Span {
        IonValue _value;

        private TreeSpan() {
        }
    }
}

