/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazon.ion.impl;

import com.amazon.ion.Decimal;
import com.amazon.ion.IntegerSize;
import com.amazon.ion.IonBool;
import com.amazon.ion.IonContainer;
import com.amazon.ion.IonDatagram;
import com.amazon.ion.IonDecimal;
import com.amazon.ion.IonException;
import com.amazon.ion.IonFloat;
import com.amazon.ion.IonInt;
import com.amazon.ion.IonLob;
import com.amazon.ion.IonNull;
import com.amazon.ion.IonReader;
import com.amazon.ion.IonStruct;
import com.amazon.ion.IonSymbol;
import com.amazon.ion.IonSystem;
import com.amazon.ion.IonText;
import com.amazon.ion.IonTimestamp;
import com.amazon.ion.IonType;
import com.amazon.ion.IonValue;
import com.amazon.ion.SymbolTable;
import com.amazon.ion.SymbolToken;
import com.amazon.ion.Timestamp;
import com.amazon.ion.impl._Private_IonContainer;
import com.amazon.ion.impl._Private_IonValue;
import com.amazon.ion.impl._Private_ReaderWriter;
import com.amazon.ion.impl._Private_Utils;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.Iterator;

class IonReaderTreeSystem
implements IonReader,
_Private_ReaderWriter {
    protected final SymbolTable _system_symtab;
    protected Iterator<IonValue> _iter;
    protected IonValue _parent;
    protected _Private_IonValue _next;
    protected _Private_IonValue _curr;
    protected boolean _eof;
    private Object[] _stack = new Object[10];
    protected int _top;
    private final _Private_IonValue.SymbolTableProvider _symbolTableAccessor;

    public IonReaderTreeSystem(IonValue value) {
        if (value == null) {
            this._system_symtab = null;
            this._symbolTableAccessor = null;
        } else {
            this._system_symtab = value.getSystem().getSystemSymbolTable();
            this.re_init(value, false);
            this._symbolTableAccessor = new _Private_IonValue.SymbolTableProvider(){

                @Override
                public SymbolTable getSymbolTable() {
                    return IonReaderTreeSystem.this.getSymbolTable();
                }
            };
        }
    }

    @Override
    public <T> T asFacet(Class<T> facetType) {
        return null;
    }

    void re_init(IonValue value, boolean hoisted) {
        this._curr = null;
        this._eof = false;
        this._top = 0;
        if (value instanceof IonDatagram) {
            assert (value instanceof _Private_IonContainer);
            IonDatagram dg = (IonDatagram)value;
            this._parent = dg;
            this._next = null;
            this._iter = dg.systemIterator();
        } else {
            this._parent = hoisted ? null : value.getContainer();
            this._next = (_Private_IonValue)value;
        }
    }

    @Override
    public void close() {
        this._eof = true;
    }

    private void push() {
        int oldlen = this._stack.length;
        if (this._top + 1 >= oldlen) {
            int newlen = oldlen * 2;
            Object[] temp = new Object[newlen];
            System.arraycopy(this._stack, 0, temp, 0, oldlen);
            this._stack = temp;
        }
        this._stack[this._top++] = this._parent;
        this._stack[this._top++] = this._iter;
    }

    private void pop() {
        assert (this._top >= 2);
        --this._top;
        this._iter = (Iterator)this._stack[this._top];
        this._stack[this._top] = null;
        --this._top;
        this._parent = (IonValue)this._stack[this._top];
        this._stack[this._top] = null;
        this._eof = false;
    }

    @Override
    public boolean hasNext() {
        IonType next_type = this.next_helper_system();
        return next_type != null;
    }

    @Override
    public IonType next() {
        if (this._next == null && !this.hasNext()) {
            this._curr = null;
            return null;
        }
        this._curr = this._next;
        this._next = null;
        return this._curr.getType();
    }

    IonType next_helper_system() {
        if (this._eof) {
            return null;
        }
        if (this._next != null) {
            return this._next.getType();
        }
        if (this._iter != null && this._iter.hasNext()) {
            this._next = (_Private_IonValue)this._iter.next();
        }
        if (this._eof = this._next == null) {
            return null;
        }
        return this._next.getType();
    }

    @Override
    public final void stepIn() {
        if (!(this._curr instanceof IonContainer)) {
            throw new IllegalStateException("current value must be a container");
        }
        this.push();
        this._parent = this._curr;
        this._iter = new Children((IonContainer)((Object)this._curr));
        this._curr = null;
    }

    @Override
    public final void stepOut() {
        if (this._top < 1) {
            throw new IllegalStateException("Cannot stepOut any further, already at top level.");
        }
        this.pop();
        this._curr = null;
    }

    @Override
    public final int getDepth() {
        return this._top / 2;
    }

    @Override
    public SymbolTable getSymbolTable() {
        return this._system_symtab;
    }

    @Override
    public IonType getType() {
        return this._curr == null ? null : this._curr.getType();
    }

    @Override
    public final String[] getTypeAnnotations() {
        if (this._curr == null) {
            throw new IllegalStateException();
        }
        return this._curr.getTypeAnnotations();
    }

    @Override
    public final SymbolToken[] getTypeAnnotationSymbols() {
        if (this._curr == null) {
            throw new IllegalStateException();
        }
        return this._curr.getTypeAnnotationSymbols(this._symbolTableAccessor);
    }

    @Override
    public final Iterator<String> iterateTypeAnnotations() {
        String[] annotations = this.getTypeAnnotations();
        return _Private_Utils.stringIterator(annotations);
    }

    @Override
    public boolean isInStruct() {
        return this.getDepth() > 0 && this._parent instanceof IonStruct;
    }

    @Override
    public boolean isNullValue() {
        if (this._curr instanceof IonNull) {
            return true;
        }
        if (this._curr == null) {
            throw new IllegalStateException("must call next() before isNullValue()");
        }
        return this._curr.isNullValue();
    }

    @Override
    public int getFieldId() {
        return this._curr == null || this._top == 0 ? -1 : this._curr.getFieldId();
    }

    @Override
    public String getFieldName() {
        return this._curr == null || this._top == 0 ? null : this._curr.getFieldName();
    }

    @Override
    public final SymbolToken getFieldNameSymbol() {
        if (this._curr == null || this._top == 0) {
            return null;
        }
        return this._curr.getFieldNameSymbol(this._symbolTableAccessor);
    }

    @Override
    public boolean booleanValue() {
        if (this._curr instanceof IonBool) {
            return ((IonBool)((Object)this._curr)).booleanValue();
        }
        throw new IllegalStateException("current value is not a boolean");
    }

    @Override
    public int intValue() {
        if (this._curr instanceof IonInt) {
            return ((IonInt)((Object)this._curr)).intValue();
        }
        if (this._curr instanceof IonFloat) {
            return (int)((IonFloat)((Object)this._curr)).doubleValue();
        }
        if (this._curr instanceof IonDecimal) {
            return (int)((IonDecimal)((Object)this._curr)).doubleValue();
        }
        throw new IllegalStateException("current value is not an ion int, float, or decimal");
    }

    @Override
    public long longValue() {
        if (this._curr instanceof IonInt) {
            return ((IonInt)((Object)this._curr)).longValue();
        }
        if (this._curr instanceof IonFloat) {
            return (long)((IonFloat)((Object)this._curr)).doubleValue();
        }
        if (this._curr instanceof IonDecimal) {
            return (long)((IonDecimal)((Object)this._curr)).doubleValue();
        }
        throw new IllegalStateException("current value is not an ion int, float, or decimal");
    }

    @Override
    public BigInteger bigIntegerValue() {
        if (this._curr instanceof IonInt) {
            return ((IonInt)((Object)this._curr)).bigIntegerValue();
        }
        if (this._curr instanceof IonFloat) {
            BigDecimal bd = ((IonFloat)((Object)this._curr)).bigDecimalValue();
            return bd == null ? null : bd.toBigInteger();
        }
        throw new IllegalStateException("current value is not an ion int or float");
    }

    @Override
    public double doubleValue() {
        if (this._curr instanceof IonFloat) {
            return ((IonFloat)((Object)this._curr)).doubleValue();
        }
        if (this._curr instanceof IonDecimal) {
            return ((IonDecimal)((Object)this._curr)).doubleValue();
        }
        throw new IllegalStateException("current value is not an ion float or decimal");
    }

    @Override
    public BigDecimal bigDecimalValue() {
        if (this._curr instanceof IonDecimal) {
            return ((IonDecimal)((Object)this._curr)).bigDecimalValue();
        }
        throw new IllegalStateException("current value is not an ion decimal");
    }

    @Override
    public Decimal decimalValue() {
        if (this._curr instanceof IonDecimal) {
            return ((IonDecimal)((Object)this._curr)).decimalValue();
        }
        throw new IllegalStateException("current value is not an ion decimal");
    }

    @Override
    public Timestamp timestampValue() {
        if (this._curr instanceof IonTimestamp) {
            return ((IonTimestamp)((Object)this._curr)).timestampValue();
        }
        throw new IllegalStateException("current value is not a timestamp");
    }

    @Override
    public Date dateValue() {
        if (this._curr instanceof IonTimestamp) {
            return ((IonTimestamp)((Object)this._curr)).dateValue();
        }
        throw new IllegalStateException("current value is not an ion timestamp");
    }

    @Override
    public String stringValue() {
        if (this._curr instanceof IonText) {
            return ((IonText)((Object)this._curr)).stringValue();
        }
        throw new IllegalStateException("current value is not a symbol or string");
    }

    @Override
    public SymbolToken symbolValue() {
        if (!(this._curr instanceof IonSymbol)) {
            throw new IllegalStateException();
        }
        if (this._curr.isNullValue()) {
            return null;
        }
        return ((IonSymbol)((Object)this._curr)).symbolValue();
    }

    @Override
    public int byteSize() {
        if (this._curr instanceof IonLob) {
            IonLob lob = (IonLob)((Object)this._curr);
            return lob.byteSize();
        }
        throw new IllegalStateException("current value is not an ion blob or clob");
    }

    @Override
    public byte[] newBytes() {
        if (this._curr instanceof IonLob) {
            int retlen;
            IonLob lob = (IonLob)((Object)this._curr);
            int loblen = lob.byteSize();
            byte[] buffer = new byte[loblen];
            InputStream is = lob.newInputStream();
            try {
                retlen = _Private_Utils.readFully(is, buffer, 0, loblen);
                is.close();
            } catch (IOException e) {
                throw new IonException(e);
            }
            assert (retlen != -1 ? retlen == loblen : loblen == 0);
            return buffer;
        }
        throw new IllegalStateException("current value is not an ion blob or clob");
    }

    @Override
    public int getBytes(byte[] buffer, int offset, int len) {
        if (this._curr instanceof IonLob) {
            int retlen;
            IonLob lob = (IonLob)((Object)this._curr);
            int loblen = lob.byteSize();
            if (loblen > len) {
                throw new IllegalArgumentException("insufficient space in buffer for this value");
            }
            InputStream is = lob.newInputStream();
            try {
                retlen = _Private_Utils.readFully(is, buffer, 0, loblen);
                is.close();
            } catch (IOException e) {
                throw new IonException(e);
            }
            assert (retlen == loblen);
            return retlen;
        }
        throw new IllegalStateException("current value is not an ion blob or clob");
    }

    public IonValue getIonValue(IonSystem sys) {
        return this._curr;
    }

    public String valueToString() {
        return this._curr == null ? null : this._curr.toString();
    }

    @Override
    public SymbolTable pop_passed_symbol_table() {
        return null;
    }

    @Override
    public IntegerSize getIntegerSize() {
        if (this._curr instanceof IonInt) {
            return ((IonInt)((Object)this._curr)).getIntegerSize();
        }
        return null;
    }

    private static final class Children
    implements Iterator<IonValue> {
        boolean _eof;
        int _next_idx;
        _Private_IonContainer _parent;
        IonValue _curr;

        Children(IonContainer parent) {
            if (parent instanceof _Private_IonContainer) {
                this._parent = (_Private_IonContainer)parent;
                this._next_idx = 0;
                this._curr = null;
                if (this._parent.isNullValue()) {
                    this._eof = true;
                }
            } else {
                throw new UnsupportedOperationException("this only supports IonContainerImpl instances");
            }
        }

        @Override
        public boolean hasNext() {
            if (this._eof) {
                return false;
            }
            int len = this._parent.get_child_count();
            if (this._next_idx > 0) {
                this._next_idx = len;
                for (int ii = this._next_idx - 1; ii < len; ++ii) {
                    if (this._curr != this._parent.get_child(ii)) continue;
                    this._next_idx = ii + 1;
                    break;
                }
            }
            if (this._next_idx >= this._parent.get_child_count()) {
                this._eof = true;
            }
            return !this._eof;
        }

        @Override
        public IonValue next() {
            if (!this.hasNext()) {
                this._curr = null;
            } else {
                this._curr = this._parent.get_child(this._next_idx);
                ++this._next_idx;
            }
            return this._curr;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}

