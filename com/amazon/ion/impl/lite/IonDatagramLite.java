/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazon.ion.impl.lite;

import com.amazon.ion.ContainedValueException;
import com.amazon.ion.IonCatalog;
import com.amazon.ion.IonDatagram;
import com.amazon.ion.IonException;
import com.amazon.ion.IonSymbol;
import com.amazon.ion.IonSystem;
import com.amazon.ion.IonType;
import com.amazon.ion.IonValue;
import com.amazon.ion.IonWriter;
import com.amazon.ion.SymbolTable;
import com.amazon.ion.SymbolToken;
import com.amazon.ion.ValueFactory;
import com.amazon.ion.ValueVisitor;
import com.amazon.ion.impl._Private_CurriedValueFactory;
import com.amazon.ion.impl._Private_IonDatagram;
import com.amazon.ion.impl._Private_Utils;
import com.amazon.ion.impl.lite.ContainerlessContext;
import com.amazon.ion.impl.lite.IonContainerLite;
import com.amazon.ion.impl.lite.IonContext;
import com.amazon.ion.impl.lite.IonSequenceLite;
import com.amazon.ion.impl.lite.IonSymbolLite;
import com.amazon.ion.impl.lite.IonSystemLite;
import com.amazon.ion.impl.lite.IonValueLite;
import com.amazon.ion.impl.lite.ReverseBinaryEncoder;
import com.amazon.ion.impl.lite.TopLevelContext;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.util.Collection;
import java.util.ListIterator;
import java.util.NoSuchElementException;

final class IonDatagramLite
extends IonSequenceLite
implements IonDatagram,
_Private_IonDatagram,
IonContext {
    private static final int HASH_SIGNATURE = IonType.DATAGRAM.toString().hashCode();
    private final IonSystemLite _system;
    private final IonCatalog _catalog;
    private SymbolTable _pending_symbol_table;
    private int _pending_symbol_table_idx;
    private IonSymbolLite _ivm;
    private static final int REVERSE_BINARY_ENCODER_INITIAL_SIZE = 32768;

    IonDatagramLite(IonSystemLite system, IonCatalog catalog) {
        super(ContainerlessContext.wrap(system), false);
        this._system = system;
        this._catalog = catalog;
        this._pending_symbol_table_idx = -1;
    }

    IonDatagramLite(IonDatagramLite existing) {
        super(existing, (IonContext)ContainerlessContext.wrap(existing._system));
        this._system = existing._system;
        this._catalog = existing._catalog;
    }

    @Override
    public IonDatagram clone() {
        return (IonDatagram)((Object)this.deepClone(true));
    }

    @Override
    IonValueLite shallowClone(IonContext context) {
        return new IonDatagramLite(this);
    }

    @Override
    public IonValueLite topLevelValue() {
        throw new UnsupportedOperationException();
    }

    @Override
    public SymbolToken[] getTypeAnnotationSymbols() {
        return SymbolToken.EMPTY_ARRAY;
    }

    @Override
    public SymbolToken getFieldNameSymbol() {
        return null;
    }

    @Override
    public void makeReadOnly() {
        if (this._isLocked()) {
            return;
        }
        if (this._children != null) {
            for (int ii = 0; ii < this._child_count; ++ii) {
                IonValueLite child2 = this._children[ii];
                child2.makeReadOnly();
            }
        }
        this._isLocked(true);
    }

    @Override
    public SymbolTable getSymbolTable() {
        throw new UnsupportedOperationException();
    }

    @Override
    public SymbolTable getAssignedSymbolTable() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setSymbolTable(SymbolTable symbols) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void appendTrailingSymbolTable(SymbolTable symtab) {
        assert (symtab.isLocalTable() || symtab.isSystemTable());
        this._pending_symbol_table = symtab;
        this._pending_symbol_table_idx = this.get_child_count();
    }

    @Override
    public boolean add(IonValue child2) throws ContainedValueException, NullPointerException {
        int index = this._child_count;
        this.add(index, child2);
        return true;
    }

    @Override
    public ValueFactory add() {
        return new _Private_CurriedValueFactory(this.getSystem()){

            @Override
            protected void handle(IonValue newValue) {
                IonDatagramLite.this.add(newValue);
            }
        };
    }

    @Override
    public void add(int index, IonValue element) throws ContainedValueException, NullPointerException {
        if (element == null) {
            throw new NullPointerException();
        }
        if (!(element instanceof IonValueLite)) {
            throw new IllegalArgumentException("IonValue implementation can't be mixed");
        }
        IonValueLite concrete = (IonValueLite)element;
        super.add(index, concrete);
        this._pending_symbol_table = null;
        this._pending_symbol_table_idx = -1;
    }

    @Override
    public ValueFactory add(final int index) {
        return new _Private_CurriedValueFactory(this.getSystem()){

            @Override
            protected void handle(IonValue newValue) {
                IonDatagramLite.this.add(index, newValue);
            }
        };
    }

    @Override
    public boolean addAll(Collection<? extends IonValue> c) {
        boolean changed = false;
        for (IonValue ionValue2 : c) {
            changed = this.add(ionValue2) || changed;
        }
        return changed;
    }

    @Override
    public boolean addAll(int index, Collection<? extends IonValue> c) {
        if (index < 0 || index > this.size()) {
            throw new IndexOutOfBoundsException();
        }
        boolean changed = false;
        for (IonValue ionValue2 : c) {
            this.add(index++, ionValue2);
            changed = true;
        }
        if (changed) {
            this.patch_elements_helper(index);
        }
        return changed;
    }

    @Override
    int hashSignature() {
        return HASH_SIGNATURE;
    }

    @Override
    public int hashCode() {
        int prime = 8191;
        int result = HASH_SIGNATURE;
        for (IonValue v : this) {
            result = prime * result + v.hashCode();
            result ^= result << 29 ^ result >> 3;
        }
        return result;
    }

    @Override
    public <T extends IonValue> T[] extract(Class<T> type) {
        if (this.isNullValue()) {
            return null;
        }
        IonValue[] array = (IonValue[])Array.newInstance(type, this.size());
        this.toArray(array);
        this.clear();
        return array;
    }

    @Override
    public ListIterator<IonValue> listIterator(int index) {
        IonContainerLite.SequenceContentIterator iterator2 = new IonContainerLite.SequenceContentIterator(index, this.isReadOnly());
        return iterator2;
    }

    @Override
    public IonValue set(int index, IonValue element) {
        throw new UnsupportedOperationException();
    }

    @Override
    public IonContext getContextForIndex(IonValue element, int index) {
        IonValueLite preceding;
        if (index == this._pending_symbol_table_idx) {
            SymbolTable symbols = this._pending_symbol_table;
            this._pending_symbol_table = null;
            this._pending_symbol_table_idx = -1;
            return TopLevelContext.wrap(symbols, this);
        }
        IonValueLite ionValueLite = preceding = index > 0 ? this.get_child(index - 1) : null;
        if (preceding != null && preceding._context != this) {
            return preceding._context;
        }
        return TopLevelContext.wrap(null, this);
    }

    @Override
    public void accept(ValueVisitor visitor2) throws Exception {
        visitor2.visit(this);
    }

    @Override
    public void addTypeAnnotation(String annotation) {
        String message = "Datagrams do not have annotations";
        throw new UnsupportedOperationException(message);
    }

    @Override
    public IonContainerLite getContainer() {
        return null;
    }

    @Override
    public IonSystemLite getSystem() {
        return this._system;
    }

    @Override
    public IonType getType() {
        return IonType.DATAGRAM;
    }

    @Override
    public final void writeTo(IonWriter writer) {
        if (writer.getSymbolTable().isSystemTable()) {
            try {
                writer.writeSymbol("$ion_1_0");
            } catch (IOException ioe) {
                throw new IonException(ioe);
            }
        }
        for (IonValue iv : this) {
            iv.writeTo(writer);
        }
    }

    @Override
    public int byteSize() throws IonException {
        ReverseBinaryEncoder encoder = new ReverseBinaryEncoder(32768);
        encoder.serialize(this);
        return encoder.byteSize();
    }

    @Override
    public byte[] getBytes() throws IonException {
        ReverseBinaryEncoder encoder = new ReverseBinaryEncoder(32768);
        encoder.serialize(this);
        return encoder.toNewByteArray();
    }

    @Override
    public int getBytes(byte[] dst) throws IonException {
        ReverseBinaryEncoder encoder = new ReverseBinaryEncoder(32768);
        encoder.serialize(this);
        return encoder.toNewByteArray(dst);
    }

    @Override
    public int getBytes(byte[] dst, int offset) throws IonException {
        ReverseBinaryEncoder encoder = new ReverseBinaryEncoder(32768);
        encoder.serialize(this);
        return encoder.toNewByteArray(dst, offset);
    }

    @Override
    public int getBytes(OutputStream out) throws IOException, IonException {
        ReverseBinaryEncoder encoder = new ReverseBinaryEncoder(32768);
        encoder.serialize(this);
        return encoder.writeBytes(out);
    }

    @Override
    public IonValue systemGet(int index) throws IndexOutOfBoundsException {
        ListIterator<IonValue> iterator2 = this.systemIterator();
        IonValue value = null;
        if (index < 0) {
            throw new IndexOutOfBoundsException("" + index);
        }
        for (int ii = 0; ii <= index; ++ii) {
            if (!iterator2.hasNext()) {
                throw new IndexOutOfBoundsException("" + index);
            }
            value = iterator2.next();
        }
        return value;
    }

    @Override
    public ListIterator<IonValue> systemIterator() {
        return new SystemContentIterator(this.isReadOnly());
    }

    @Override
    public int systemSize() {
        int count = 0;
        ListIterator<IonValue> iterator2 = this.systemIterator();
        while (iterator2.hasNext()) {
            IonValue value = iterator2.next();
            ++count;
        }
        return count;
    }

    void setSymbolTableAtIndex(int elementid, SymbolTable symbols) {
        assert (elementid < this.get_child_count());
        TopLevelContext context = TopLevelContext.wrap(symbols, this);
        TopLevelContext startContext = (TopLevelContext)this._children[elementid].getContext();
        while (elementid < this.get_child_count() && this._children[elementid].getContext() == startContext) {
            this._children[elementid++].setContext(context);
        }
    }

    @Override
    public byte[] toBytes() throws IonException {
        return this.getBytes();
    }

    protected synchronized IonSymbolLite get_ivm() {
        if (this._ivm == null) {
            this._ivm = this.getSystem().newSymbol("$ion_1_0");
        }
        return this._ivm;
    }

    static class SystemIteratorPosition {
        protected final SystemContentIterator __iterator;
        protected int __index_adjustment;
        protected int __local_index;
        protected IonValueLite[] __local_values = new IonValueLite[3];
        protected int __local_value_count;
        protected int __user_index;
        protected IonValueLite __current_user_value;
        protected SymbolTable __current_symbols;
        protected int __current_symbols_index;

        SystemIteratorPosition(SystemContentIterator iterator2) {
            this.__iterator = iterator2;
        }

        void load_initial_position() {
            this.__user_index = 0;
            this.__local_index = -1;
            this.__current_symbols_index = -1;
            this.load_updated_position();
        }

        protected int get_external_pos() {
            int user_index = this.__user_index;
            user_index += this.__index_adjustment;
            user_index -= this.__local_value_count;
            return user_index += this.__local_index;
        }

        protected boolean on_system_value() {
            return this.__current_user_value != this.__local_values[0];
        }

        protected boolean has_next() {
            if (this.__local_index + 1 < this.__local_value_count) {
                return true;
            }
            return this.__user_index + 1 < this.__iterator.get_datagram_child_count();
        }

        protected boolean has_prev() {
            if (this.__user_index > 0) {
                return true;
            }
            return this.__local_index > 0;
        }

        protected void copyFrom(SystemIteratorPosition source) {
            this.__index_adjustment = source.__index_adjustment;
            this.__user_index = source.__user_index;
            this.__local_index = source.__local_index;
            this.__current_user_value = source.__current_user_value;
            this.__current_symbols = source.__current_symbols;
            this.__current_symbols_index = source.__current_symbols_index;
            if (source.__local_value_count > 0) {
                if (this.__local_values == null || source.__local_value_count >= this.__local_values.length) {
                    this.__local_values = new IonValueLite[source.__local_values.length];
                }
                System.arraycopy(source.__local_values, 0, this.__local_values, 0, source.__local_value_count);
            }
            this.__local_value_count = source.__local_value_count;
        }

        private void load_updated_position() {
            IonValueLite prev_value = this.__current_user_value;
            if (this.__user_index < 0 || this.__user_index > 0 && this.__user_index >= this.__iterator.get_datagram_child_count()) {
                throw new IonException("attempt to position iterator past end of values");
            }
            if (this.__user_index < this.__iterator.get_datagram_child_count()) {
                this.__current_user_value = this.__iterator.get_datagram_child(this.__user_index);
                assert (this.__current_user_value != null);
            } else {
                assert (this.__user_index == 0 && this.__iterator.get_datagram_child_count() == 0);
                this.__current_user_value = null;
            }
            int old_count = this.__local_value_count;
            this.__local_value_count = 0;
            if (this.__current_user_value != null) {
                this.push_system_value(this.__current_user_value);
            }
            this.load_current_symbol_table(prev_value);
            for (int ii = this.__local_value_count; ii < old_count; ++ii) {
                this.__local_values[ii] = null;
            }
            this.__index_adjustment += this.__local_value_count - 1;
        }

        void load_current_symbol_table(IonValueLite prev_user_value) {
            IonValueLite curr_value = this.__current_user_value;
            int curr_index = this.__user_index;
            IonValueLite prev_value = prev_user_value;
            SymbolTable prev_symtab = this.__current_symbols;
            int prev_index = this.__current_symbols_index;
            this.__current_symbols = null;
            this.__current_symbols_index = curr_index;
            SymbolTable curr_symtab = null;
            if (curr_value != null) {
                this.__current_symbols = curr_symtab = curr_value.getAssignedSymbolTable();
            }
            if (curr_index - 1 != prev_index) {
                prev_index = curr_index - 1;
                prev_symtab = null;
                if (prev_index >= 0 && prev_index < this.__iterator.get_datagram_child_count()) {
                    prev_value = this.__iterator.get_datagram_child(prev_index);
                    prev_symtab = prev_value.getAssignedSymbolTable();
                }
            }
            if (curr_symtab != prev_symtab) {
                SymbolTable new_symbol_table = curr_symtab;
                while (new_symbol_table != null) {
                    IonValue rep;
                    boolean new_symbol_table_is_system = new_symbol_table.isSystemTable();
                    if (new_symbol_table_is_system) {
                        rep = this.__iterator.get_datagram_ivm();
                    } else {
                        IonSystem sys = this.__iterator.get_datagram_system();
                        rep = _Private_Utils.symtabTree(new_symbol_table, sys);
                    }
                    assert (rep != null && this.__iterator.get_datagram_system() == rep.getSystem());
                    if (rep == prev_value || SystemIteratorPosition.is_ivm(curr_value) && new_symbol_table_is_system) {
                        int prev_idx;
                        int n = prev_idx = prev_value == null ? -1 : prev_value._elementid() - 1;
                        prev_value = prev_idx >= 0 ? this.__iterator.get_datagram_child(prev_idx) : null;
                    } else {
                        this.push_system_value((IonValueLite)rep);
                        prev_value = null;
                    }
                    if ((new_symbol_table = rep.getSymbolTable()) != null && !new_symbol_table.isSystemTable()) continue;
                    break;
                }
            }
            if (curr_index == 0 && !SystemIteratorPosition.is_ivm(curr_value)) {
                IonSymbolLite ivm = this.__iterator.get_datagram_ivm();
                this.push_system_value(ivm);
            }
        }

        private static final boolean is_ivm(IonValue value) {
            IonSymbol sym;
            SymbolToken tok;
            return value instanceof IonSymbol && value.getTypeAnnotationSymbols().length == 0 && (tok = (sym = (IonSymbol)value).symbolValue()) != null && "$ion_1_0".equals(tok.getText());
        }

        private void push_system_value(IonValueLite value) {
            if (this.__local_value_count >= this.__local_values.length) {
                int new_size;
                int n = new_size = this.__local_values == null ? 2 : this.__local_values.length * 2;
                assert (new_size > this.__local_value_count);
                IonValueLite[] temp = new IonValueLite[new_size];
                if (this.__local_value_count > 0) {
                    System.arraycopy(this.__local_values, 0, temp, 0, this.__local_value_count);
                }
                this.__local_values = temp;
            }
            this.__local_values[this.__local_value_count++] = value;
        }

        protected IonValueLite load_position() {
            IonValueLite current = null;
            assert (this.__local_index < this.__local_value_count);
            current = this.__local_values[this.__local_value_count - this.__local_index - 1];
            return current;
        }

        private final void force_position_sync_helper() {
            if (!this.__iterator.datagram_contains(this.__current_user_value)) {
                throw new IonException("current user value removed outside this iterator - position lost");
            }
            int old_index = this.__user_index;
            int new_index = this.__current_user_value._elementid();
            if (old_index != new_index) {
                int adjustment = 0;
                SymbolTable prev = null;
                for (int ii = 0; ii < new_index; --ii) {
                    SymbolTable curr = this.__iterator.get_datagram_child(ii).getSymbolTable();
                    if (curr != prev) {
                        IonSystem sys = this.__iterator.getSystem();
                        adjustment += SystemIteratorPosition.count_system_values(sys, prev, curr);
                    }
                    prev = curr;
                }
                this.__index_adjustment = adjustment + this.__local_value_count;
                this.__user_index = new_index;
            }
        }

        private static int count_system_values(IonSystem sys, SymbolTable prev, SymbolTable curr) {
            int count = 0;
            while (curr.isLocalTable()) {
                ++count;
                curr = _Private_Utils.symtabTree(curr, sys).getSymbolTable();
            }
            assert (curr != null);
            if (prev == null || prev.getIonVersionId().equals(curr.getIonVersionId())) {
                ++count;
            }
            return count;
        }
    }

    protected final class SystemContentIterator
    implements ListIterator<IonValue> {
        private final boolean __readOnly;
        private IonValueLite __current;
        private SystemIteratorPosition __pos;
        private SystemIteratorPosition __temp_pos;

        public SystemContentIterator(boolean readOnly) {
            if (IonDatagramLite.this._isLocked() && !readOnly) {
                throw new IllegalStateException("you can't open an updatable iterator on a read only value");
            }
            this.__readOnly = readOnly;
            this.__temp_pos = new SystemIteratorPosition(this);
            this.__pos = new SystemIteratorPosition(this);
            this.__pos.load_initial_position();
        }

        private IonSystem getSystem() {
            return IonDatagramLite.this.getSystem();
        }

        protected IonValueLite set_position(SystemIteratorPosition newPos) {
            this.__temp_pos = this.__pos;
            this.__pos = newPos;
            this.__current = this.__pos.load_position();
            return this.__current;
        }

        private void force_position_sync() {
            int user_index = this.__pos.__user_index;
            if (user_index < 0 || user_index >= IonDatagramLite.this._child_count) {
                return;
            }
            IonValueLite user_value = this.__pos.__current_user_value;
            if (user_value == null || user_value == IonDatagramLite.this._children[user_index]) {
                return;
            }
            if (this.__readOnly) {
                throw new IonException("read only sequence was changed");
            }
            this.__pos.force_position_sync_helper();
        }

        @Override
        public void add(IonValue element) {
            throw new UnsupportedOperationException();
        }

        @Override
        public final boolean hasNext() {
            return this.__pos.has_next();
        }

        @Override
        public IonValue next() {
            SystemIteratorPosition pos = this.next_index_helper();
            if (pos == null) {
                throw new NoSuchElementException();
            }
            IonValueLite current_value = this.set_position(pos);
            assert (current_value == this.__current);
            return current_value;
        }

        @Override
        public final int nextIndex() {
            SystemIteratorPosition pos = this.next_index_helper();
            if (pos == null) {
                return this.__pos.get_external_pos() + 1;
            }
            int idx = pos.get_external_pos();
            return idx;
        }

        private final SystemIteratorPosition next_index_helper() {
            SystemIteratorPosition next = null;
            this.force_position_sync();
            if (!this.__pos.has_next()) {
                return null;
            }
            next = this.__temp_pos;
            assert (next != null && next != this.__pos);
            next.copyFrom(this.__pos);
            ++next.__local_index;
            if (next.__local_index < next.__local_value_count) {
                return next;
            }
            assert (next.__user_index <= IonDatagramLite.this.get_child_count());
            ++next.__user_index;
            next.load_updated_position();
            next.__local_index = 0;
            return next;
        }

        @Override
        public final boolean hasPrevious() {
            return this.__pos.has_prev();
        }

        @Override
        public IonValue previous() {
            SystemIteratorPosition pos = this.previous_index_helper();
            if (pos == null) {
                throw new NoSuchElementException();
            }
            IonValueLite current_value = this.set_position(pos);
            assert (current_value == this.__current);
            return current_value;
        }

        @Override
        public final int previousIndex() {
            SystemIteratorPosition pos = this.previous_index_helper();
            if (pos == null) {
                return -1;
            }
            int idx = pos.get_external_pos();
            return idx;
        }

        private final SystemIteratorPosition previous_index_helper() {
            SystemIteratorPosition prev = null;
            this.force_position_sync();
            if (!this.__pos.has_prev()) {
                return null;
            }
            prev = this.__temp_pos;
            assert (prev != null && prev != this.__pos);
            prev.copyFrom(this.__pos);
            --prev.__local_index;
            if (prev.__local_index >= 0) {
                return prev;
            }
            assert (prev.__user_index > 0);
            prev.__index_adjustment -= prev.__local_value_count;
            --prev.__user_index;
            prev.load_updated_position();
            prev.__local_index = prev.__local_value_count - 1;
            return prev;
        }

        @Override
        public void remove() {
            if (this.__readOnly) {
                throw new UnsupportedOperationException();
            }
            this.force_position_sync();
            if (this.__current == null || this.__pos == null) {
                throw new NoSuchElementException();
            }
            if (this.__pos.on_system_value()) {
                throw new UnsupportedOperationException();
            }
            int idx = this.__pos.__user_index;
            assert (idx >= 0);
            IonValueLite concrete = this.__current;
            int concrete_idx = concrete._elementid();
            assert (concrete_idx == idx);
            IonDatagramLite.this.remove_child(idx);
            IonDatagramLite.this.patch_elements_helper(concrete_idx);
            this.__pos.__index_adjustment -= this.__pos.__local_value_count;
            if (this.__pos.__user_index < IonDatagramLite.this.get_child_count() - 1) {
                this.__pos.load_updated_position();
                this.__pos.__local_index = -1;
            }
            this.__current = null;
        }

        @Override
        public void set(IonValue element) {
            throw new UnsupportedOperationException();
        }

        protected int get_datagram_child_count() {
            return IonDatagramLite.this.get_child_count();
        }

        protected IonValueLite get_datagram_child(int idx) {
            return IonDatagramLite.this.get_child(idx);
        }

        protected IonSystem get_datagram_system() {
            return IonDatagramLite.this._system;
        }

        protected boolean datagram_contains(IonValueLite value) {
            return IonDatagramLite.this.contains(value);
        }

        protected IonSymbolLite get_datagram_ivm() {
            return IonDatagramLite.this.get_ivm();
        }
    }
}

