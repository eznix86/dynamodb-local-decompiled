/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazon.ion.impl.lite;

import com.amazon.ion.ContainedValueException;
import com.amazon.ion.IonContainer;
import com.amazon.ion.IonDatagram;
import com.amazon.ion.IonException;
import com.amazon.ion.IonValue;
import com.amazon.ion.IonWriter;
import com.amazon.ion.NullValueException;
import com.amazon.ion.ReadOnlyValueException;
import com.amazon.ion.SymbolTable;
import com.amazon.ion.ValueVisitor;
import com.amazon.ion.impl._Private_IonContainer;
import com.amazon.ion.impl._Private_IonValue;
import com.amazon.ion.impl._Private_Utils;
import com.amazon.ion.impl.lite.ContainerlessContext;
import com.amazon.ion.impl.lite.IonContext;
import com.amazon.ion.impl.lite.IonDatagramLite;
import com.amazon.ion.impl.lite.IonStructLite;
import com.amazon.ion.impl.lite.IonSystemLite;
import com.amazon.ion.impl.lite.IonValueLite;
import com.amazon.ion.impl.lite.TopLevelContext;
import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

abstract class IonContainerLite
extends IonValueLite
implements _Private_IonContainer,
IonContext {
    protected int _child_count;
    protected IonValueLite[] _children;
    protected int structuralModificationCount;
    protected IonSystemLite ionSystem;
    static final int[] INITIAL_SIZE = IonContainerLite.make_initial_size_array();
    static final int[] NEXT_SIZE = IonContainerLite.make_next_size_array();
    static final int STRUCT_INITIAL_SIZE = 5;

    protected IonContainerLite(ContainerlessContext context, boolean isNull) {
        super(context, isNull);
        this.ionSystem = context.getSystem();
    }

    IonContainerLite(IonContainerLite existing, IonContext context) {
        super(existing, context);
        this.ionSystem = existing.ionSystem;
    }

    @Override
    public IonSystemLite getSystem() {
        return this.ionSystem;
    }

    @Override
    public abstract void accept(ValueVisitor var1) throws Exception;

    private static void setCloneContext(CloneContext cloneContext, IonContainerLite containerOriginal, IonContainerLite containerCopy, boolean needsTopLevelContext) {
        cloneContext.parentOriginal = containerOriginal;
        cloneContext.parentCopy = containerCopy;
        containerCopy._children = new IonValueLite[containerOriginal._children.length];
        containerCopy._child_count = containerOriginal._child_count;
        if (needsTopLevelContext) {
            cloneContext.contextCopy = TopLevelContext.wrap(containerOriginal._context.getContextSymbolTable(), (IonDatagramLite)containerCopy);
            cloneContext.parentIsStruct = false;
        } else {
            cloneContext.contextCopy = containerCopy;
            cloneContext.parentIsStruct = containerCopy instanceof IonStructLite;
        }
        cloneContext.childIndex = 0;
    }

    final IonContainerLite deepClone(boolean isDatagramBeingCloned) {
        ContainerlessContext initialContext;
        ContainerlessContext containerlessContext = initialContext = isDatagramBeingCloned ? null : ContainerlessContext.wrap(this._context.getSystem(), this._context.getContextSymbolTable());
        if (this._children == null) {
            return (IonContainerLite)this.shallowClone(initialContext);
        }
        boolean areSIDsRetained = false;
        CloneContext[] stack = new CloneContext[16];
        int stackIndex = 0;
        CloneContext cloneContext = new CloneContext();
        cloneContext.contextCopy = initialContext;
        stack[stackIndex] = cloneContext;
        IonValueLite original = this;
        while (true) {
            IonValueLite copy;
            if (!(original instanceof IonContainerLite)) {
                copy = original.shallowClone(cloneContext.contextCopy);
                if (cloneContext.parentIsStruct) {
                    copy.copyFieldName(original);
                }
                cloneContext.parentCopy._children[cloneContext.childIndex++] = copy;
            } else {
                copy = original.shallowClone(cloneContext.contextCopy);
                if (cloneContext.parentIsStruct) {
                    copy.copyFieldName(original);
                }
                if (cloneContext.parentCopy != null) {
                    cloneContext.parentCopy._children[cloneContext.childIndex++] = copy;
                }
                IonContainerLite containerOriginal = original;
                if (containerOriginal._children != null) {
                    if (++stackIndex >= stack.length) {
                        stack = Arrays.copyOf(stack, stack.length * 2);
                    }
                    if ((cloneContext = stack[stackIndex]) == null) {
                        stack[stackIndex] = cloneContext = new CloneContext();
                    }
                    IonContainerLite.setCloneContext(cloneContext, containerOriginal, (IonContainerLite)copy, isDatagramBeingCloned && stackIndex == 1);
                }
            }
            areSIDsRetained |= copy._isSymbolIdPresent();
            while (cloneContext.childIndex >= cloneContext.parentOriginal._child_count) {
                copy = cloneContext.parentCopy;
                cloneContext.contextCopy = null;
                cloneContext = stack[--stackIndex];
                if (stackIndex != 0) continue;
                copy._isSymbolIdPresent(areSIDsRetained);
                return (IonContainerLite)copy;
            }
            original = cloneContext.parentOriginal._children[cloneContext.childIndex];
        }
    }

    @Override
    public IonContainer clone() {
        return this.deepClone(false);
    }

    @Override
    public void clear() {
        this.checkForLock();
        if (this._isNullValue()) {
            assert (this._children == null);
            assert (this._child_count == 0);
            this._isNullValue(false);
        } else if (!this.isEmpty()) {
            this.detachAllChildren();
            this._child_count = 0;
            ++this.structuralModificationCount;
        }
    }

    private void detachAllChildren() {
        for (int ii = 0; ii < this._child_count; ++ii) {
            IonValueLite child2 = this._children[ii];
            child2.detachFromContainer();
            this._children[ii] = null;
        }
    }

    @Override
    public boolean isEmpty() throws NullValueException {
        this.validateThisNotNull();
        return this.size() == 0;
    }

    public IonValue get(int index) throws NullValueException {
        this.validateThisNotNull();
        IonValueLite value = this.get_child(index);
        assert (!value._isAutoCreated());
        return value;
    }

    @Override
    public final Iterator<IonValue> iterator() {
        return this.listIterator(0);
    }

    public final ListIterator<IonValue> listIterator() {
        return this.listIterator(0);
    }

    public ListIterator<IonValue> listIterator(int index) {
        if (this.isNullValue()) {
            if (index != 0) {
                throw new IndexOutOfBoundsException();
            }
            return _Private_Utils.emptyIterator();
        }
        return new SequenceContentIterator(index, this.isReadOnly());
    }

    void beforeIteratorRemove(IonValueLite value, int idx) {
    }

    void afterIteratorRemove(IonValueLite value, int idx) {
    }

    @Override
    final int scalarHashCode() {
        throw new IllegalStateException("Not applicable for container values.");
    }

    @Override
    final void writeBodyTo(IonWriter writer, _Private_IonValue.SymbolTableProvider symbolTableProvider) throws IOException {
        throw new IllegalStateException("Not applicable for container values.");
    }

    @Override
    public void makeNull() {
        this.clear();
        this._isNullValue(true);
    }

    @Override
    public boolean remove(IonValue element) {
        this.checkForLock();
        if (element.getContainer() != this) {
            return false;
        }
        IonValueLite concrete = (IonValueLite)element;
        int pos = concrete._elementid();
        IonValueLite child2 = this.get_child(pos);
        if (child2 == concrete) {
            this.remove_child(pos);
            this.patch_elements_helper(pos);
            return true;
        }
        throw new AssertionError((Object)"element's index is not correct");
    }

    @Override
    public int size() {
        if (this.isNullValue()) {
            return 0;
        }
        return this.get_child_count();
    }

    @Override
    public final IonContainerLite getContextContainer() {
        return this;
    }

    @Override
    public final SymbolTable getContextSymbolTable() {
        return null;
    }

    public boolean add(IonValue child2) throws NullPointerException, IllegalArgumentException, ContainedValueException {
        int size = this.get_child_count();
        this.add(size, (IonValueLite)child2);
        return true;
    }

    void validateNewChild(IonValue child2) throws ContainedValueException, NullPointerException, IllegalArgumentException {
        if (child2.getContainer() != null) {
            throw new ContainedValueException();
        }
        if (child2.isReadOnly()) {
            throw new ReadOnlyValueException();
        }
        if (child2 instanceof IonDatagram) {
            String message = "IonDatagram can not be inserted into another IonContainer.";
            throw new IllegalArgumentException(message);
        }
        assert (child2 instanceof IonValueLite) : "Child was not created by the same ValueFactory";
        assert (this.getSystem() == child2.getSystem() || this.getSystem().getClass().equals(child2.getSystem().getClass()));
    }

    void add(int index, IonValueLite child2) throws ContainedValueException, NullPointerException {
        if (index < 0 || index > this.get_child_count()) {
            throw new IndexOutOfBoundsException();
        }
        this.checkForLock();
        this.validateNewChild(child2);
        this.add_child(index, child2);
        this.patch_elements_helper(index + 1);
        assert (index >= 0 && index < this.get_child_count() && child2 == this.get_child(index) && child2._elementid() == index);
    }

    static int[] make_initial_size_array() {
        int[] sizes = new int[17];
        sizes[11] = 1;
        sizes[12] = 4;
        sizes[13] = 5;
        sizes[16] = 3;
        return sizes;
    }

    static int[] make_next_size_array() {
        int[] sizes = new int[17];
        sizes[11] = 4;
        sizes[12] = 8;
        sizes[13] = 8;
        sizes[16] = 10;
        return sizes;
    }

    protected final int initialSize() {
        switch (this.getType()) {
            case LIST: {
                return 1;
            }
            case SEXP: {
                return 4;
            }
            case STRUCT: {
                return 5;
            }
            case DATAGRAM: {
                return 3;
            }
        }
        return 4;
    }

    protected final int nextSize(int current_size, boolean call_transition) {
        int next_size;
        if (current_size == 0) {
            int new_size = this.initialSize();
            return new_size;
        }
        switch (this.getType()) {
            case LIST: {
                next_size = 4;
                break;
            }
            case SEXP: {
                next_size = 8;
                break;
            }
            case STRUCT: {
                next_size = 8;
                break;
            }
            case DATAGRAM: {
                next_size = 10;
                break;
            }
            default: {
                return current_size * 2;
            }
        }
        if (next_size > current_size) {
            if (call_transition) {
                this.transitionToLargeSize(next_size);
            }
        } else {
            next_size = current_size * 2;
        }
        return next_size;
    }

    void transitionToLargeSize(int size) {
    }

    void forceMaterializationOfLazyState() {
    }

    @Override
    public final int get_child_count() {
        return this._child_count;
    }

    @Override
    public final IonValueLite get_child(int idx) {
        if (idx < 0 || idx >= this._child_count) {
            throw new IndexOutOfBoundsException(Integer.toString(idx));
        }
        return this._children[idx];
    }

    final IonValueLite set_child(int idx, IonValueLite child2) {
        if (idx < 0 || idx >= this._child_count) {
            throw new IndexOutOfBoundsException(Integer.toString(idx));
        }
        if (child2 == null) {
            throw new NullPointerException();
        }
        IonValueLite prev = this._children[idx];
        this._children[idx] = child2;
        return prev;
    }

    protected int add_child(int idx, IonValueLite child2) {
        this._isNullValue(false);
        child2.setContext(this.getContextForIndex(child2, idx));
        if (this._children == null || this._child_count >= this._children.length) {
            int old_len = this._children == null ? 0 : this._children.length;
            int new_len = this.nextSize(old_len, true);
            assert (new_len > idx);
            IonValueLite[] temp = new IonValueLite[new_len];
            if (old_len > 0) {
                System.arraycopy(this._children, 0, temp, 0, old_len);
            }
            this._children = temp;
        }
        if (idx < this._child_count) {
            System.arraycopy(this._children, idx, this._children, idx + 1, this._child_count - idx);
        }
        ++this._child_count;
        this._children[idx] = child2;
        ++this.structuralModificationCount;
        child2._elementid(idx);
        if (!this._isSymbolIdPresent() && child2._isSymbolIdPresent()) {
            this.cascadeSIDPresentToContextRoot();
        }
        return idx;
    }

    IonContext getContextForIndex(IonValue element, int index) {
        return this;
    }

    void remove_child(int idx) {
        assert (idx >= 0);
        assert (idx < this.get_child_count());
        assert (this.get_child(idx) != null) : "No child at index " + idx;
        this._children[idx].detachFromContainer();
        int children_to_move = this._child_count - idx - 1;
        if (children_to_move > 0) {
            System.arraycopy(this._children, idx + 1, this._children, idx, children_to_move);
        }
        --this._child_count;
        this._children[this._child_count] = null;
        ++this.structuralModificationCount;
    }

    public final void patch_elements_helper(int lowest_bad_idx) {
        for (int ii = lowest_bad_idx; ii < this.get_child_count(); ++ii) {
            IonValueLite child2 = this.get_child(ii);
            child2._elementid(ii);
        }
    }

    protected class SequenceContentIterator
    implements ListIterator<IonValue> {
        protected final boolean __readOnly;
        protected boolean __lastMoveWasPrevious;
        protected int __pos;
        protected IonValueLite __current;

        public SequenceContentIterator(int index, boolean readOnly) {
            if (IonContainerLite.this._isLocked() && !readOnly) {
                throw new IllegalStateException("you can't open an updatable iterator on a read only value");
            }
            if (index < 0 || index > IonContainerLite.this._child_count) {
                throw new IndexOutOfBoundsException(Integer.toString(index));
            }
            this.__pos = index;
            this.__readOnly = readOnly;
        }

        protected final void force_position_sync() {
            if (this.__pos <= 0 || this.__pos > IonContainerLite.this._child_count) {
                return;
            }
            if (this.__current == null || this.__current == IonContainerLite.this._children[this.__pos - 1]) {
                return;
            }
            this.force_position_sync_helper();
        }

        private final void force_position_sync_helper() {
            int ii;
            if (this.__readOnly) {
                throw new IonException("read only sequence was changed");
            }
            for (ii = this.__pos; ii < IonContainerLite.this._child_count; ++ii) {
                if (IonContainerLite.this._children[ii] != this.__current) continue;
                this.__pos = ii;
                if (!this.__lastMoveWasPrevious) {
                    ++this.__pos;
                }
                return;
            }
            for (ii = this.__pos - 1; ii >= 0; --ii) {
                if (IonContainerLite.this._children[ii] != this.__current) continue;
                this.__pos = ii;
                if (!this.__lastMoveWasPrevious) {
                    ++this.__pos;
                }
                return;
            }
            throw new IonException("current member of iterator has been removed from the containing sequence");
        }

        @Override
        public void add(IonValue element) {
            throw new UnsupportedOperationException();
        }

        @Override
        public final boolean hasNext() {
            return this.nextIndex() < IonContainerLite.this._child_count;
        }

        @Override
        public final boolean hasPrevious() {
            return this.previousIndex() >= 0;
        }

        @Override
        public IonValue next() {
            int next_idx = this.nextIndex();
            if (next_idx >= IonContainerLite.this._child_count) {
                throw new NoSuchElementException();
            }
            this.__current = IonContainerLite.this._children[next_idx];
            this.__pos = next_idx + 1;
            this.__lastMoveWasPrevious = false;
            return this.__current;
        }

        IonValueLite nextOrNull() {
            if (this.__pos >= IonContainerLite.this._child_count) {
                return null;
            }
            this.__current = IonContainerLite.this._children[this.__pos++];
            this.__lastMoveWasPrevious = false;
            return this.__current;
        }

        @Override
        public final int nextIndex() {
            this.force_position_sync();
            if (this.__pos >= IonContainerLite.this._child_count) {
                return IonContainerLite.this._child_count;
            }
            int next_idx = this.__pos;
            return next_idx;
        }

        @Override
        public IonValue previous() {
            this.force_position_sync();
            int prev_idx = this.previousIndex();
            if (prev_idx < 0) {
                throw new NoSuchElementException();
            }
            this.__current = IonContainerLite.this._children[prev_idx];
            this.__pos = prev_idx;
            this.__lastMoveWasPrevious = true;
            return this.__current;
        }

        @Override
        public final int previousIndex() {
            this.force_position_sync();
            int prev_idx = this.__pos - 1;
            if (prev_idx < 0) {
                return -1;
            }
            return prev_idx;
        }

        @Override
        public void remove() {
            if (this.__readOnly) {
                throw new UnsupportedOperationException();
            }
            this.force_position_sync();
            int idx = this.__pos;
            if (!this.__lastMoveWasPrevious) {
                --idx;
            }
            if (idx < 0) {
                throw new ArrayIndexOutOfBoundsException();
            }
            IonValueLite concrete = this.__current;
            int concrete_idx = concrete._elementid();
            assert (concrete_idx == idx);
            IonContainerLite.this.beforeIteratorRemove(concrete, idx);
            IonContainerLite.this.remove_child(idx);
            IonContainerLite.this.patch_elements_helper(concrete_idx);
            if (!this.__lastMoveWasPrevious) {
                --this.__pos;
            }
            this.__current = null;
            IonContainerLite.this.afterIteratorRemove(concrete, idx);
        }

        @Override
        public void set(IonValue element) {
            throw new UnsupportedOperationException();
        }
    }

    private static class CloneContext {
        IonContainerLite parentOriginal = null;
        IonContainerLite parentCopy = null;
        boolean parentIsStruct = false;
        IonContext contextCopy = null;
        int childIndex = 0;

        private CloneContext() {
        }
    }
}

